package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.OWDStatefulJob;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/11/13
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class MarineEssentialsPartialShipJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) throws Exception {

        try {
            fixOrder(9045494);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //run();
    }

    @Override
    public void internalExecute() {

        try {

            ResultSet rs = HibernateSession.getResultSet("select order_id from owd_order (NOLOCK) join owd_line_item (NOLOCK) on order_id=order_fkey and inventory_num='MD3-B60' where datediff(minute,post_date,getdate())>5 " +
                    " and order_status='At Warehouse' and client_fkey=494 and salesperson<>'D3P' and order_type not like 'SEARS%' group by order_id having sum(quantity_actual)>2");
            while (rs.next()) {


                try {
                    fixOrder(rs.getInt(1));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    static void fixOrder(int orderId) throws Exception {

        OrderStatus status = new OrderStatus("" + orderId);


            status.unpostOrder();
            boolean hasLetter = false;
            for (int parentindex=0;parentindex<status.items.size();parentindex++) {
                if(((LineItem)(status.items.get(parentindex))).client_part_no.equals("INS-LETTER"))
                {
                    hasLetter = true;
                }
            }
              if(!hasLetter)
              {
                OrderStatus.addLineItemToExistingOrder(orderId, 494, "INS-LETTER", "", 1, 0.00f, 0.00f);
              }

            status = new OrderStatus("" + orderId);
        List<Integer> parentLineIdList = new ArrayList<Integer>();

            //loop through each line item
            for (int parentindex=0;parentindex<status.items.size();parentindex++) {

                LineItem line = (LineItem)(status.items.get(parentindex));

                //if is ESF-B30, record kit parent line id
                if ("MD3-B60".equals(line.client_part_no)) {
                    if(!(parentLineIdList.contains(line.parent_line)))
                    {
                        parentLineIdList.add(line.parent_line);
                    }
                }
            }

        //loop again, clearing affected parents and clearing parent reference from affected components
        for (int parentindex=0;parentindex<status.items.size();parentindex++) {

            LineItem line = (LineItem)(status.items.get(parentindex));

            if(parentLineIdList.contains(line.parent_line))
            {
             line.parent_line=null;
            }
            if(parentLineIdList.contains(Integer.parseInt(line.line_item_id)))
            {
                line.description = "(" + line.client_part_no + ") " + line.description;
                line.client_part_no = "KITITEM";
                line.inventory_fkey = "189758";
                line.is_parent = 0;

            }
            line.dbupdate(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
        }


        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());

        List<String> esfLines = new ArrayList<String>();
        int esfQty = 0;

        //loop through each line item  and locate all ESF-B30 lines, and keep running total of quantity
        for (int parentindex=0;parentindex<status.items.size();parentindex++) {

            LineItem line = (LineItem)(status.items.get(parentindex));

            //if is ESF-B30, record kit parent line id
            if ("MD3-B60".equals(line.client_part_no)) {
               esfQty += line.quantity_request;
               esfLines.add(line.line_item_id);
            }
        }
        if(esfQty<3)
        {
            throw new Exception("Insufficient units requested for MD3-B60")    ;
        }

        List<LineItem> deletedLines = new ArrayList<LineItem>();
        boolean gotFinalLine = false;

        //loop through each line item  and consolidate ESF-B30 lines into 1 line
        for (int parentindex=0;parentindex<status.items.size();parentindex++) {

            LineItem line = (LineItem)(status.items.get(parentindex));

            //if is ESF-B30, record kit parent line id
            if (esfLines.contains(line.line_item_id)) {
                if(gotFinalLine==true)
                {
                   deletedLines.add(line);

                }else
                {
                   line.quantity_request=esfQty;
                    line.force_backorder_quantity = true;
                    line.quantity_backordered = line.quantity_request - 2 ;
                    line.quantity_request = 2;
                    gotFinalLine=true;

                }
            }
        }

        for(LineItem line:deletedLines)
        {
            log.debug( "deleting line id "+line.line_item_id+" : "+line.client_part_no);
            status.items.remove(line);
            status.removeLineItem(line.line_item_id);
        }

        //status = new OrderStatus("" + orderId);


        OrderUtilities.updateLineItemsForAvailability(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), status.items, OrderXMLDoc.kPartialShip, false, FacilitiesManager.getFacilityForCode(status.shipLocation).getId());


            int unitsShipping = 0;
            String letterLineId=null;
            for (int i = 0; i < status.items.size(); i++) {
                if("INS-LETTER".equals(((LineItem) status.items.elementAt(i)).client_part_no))
                {
                      letterLineId=((LineItem) status.items.elementAt(i)).line_item_id;
                } else
                {
                    unitsShipping += ((LineItem) status.items.elementAt(i)).quantity_actual;

                }
            }

            if (unitsShipping > 0) {
                String backorder = OrderUtilities.shipExistingOrder(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), status);
                //put backorder on hold
                //mark order with our special po_num
                if (backorder != null) {
                    PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_order set is_future_ship=1 where order_num='" + backorder + "';");
                    ps.executeUpdate();
                    ps.close();
                }
                PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_order set salesperson='D3P' where order_id=" + orderId + ";");
                ps.executeUpdate();
                ps.close();
                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());
            } else {
                //nothing to ship except possibly the letter, so remove it.
                if(letterLineId!=null)
                {
                    removeLineItem(status,letterLineId);
                }
                //never mind - just mark order as seen and put it on hold.
                PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_order set is_future_ship=1 where order_id=" + orderId + ";");
                ps.executeUpdate();
                ps.close();

                ps = HibernateSession.getPreparedStatement("update owd_order set salesperson='D3P' where order_id=" + orderId + ";");
                ps.executeUpdate();
                ps.close();
                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());
            }


    }

    private static void removeLineItem( OrderStatus status1, String itemIDStr) throws Exception {
        int itemID = 0;


        log.debug("1");

        if(status1.is_posted || status1.is_shipped || status1.is_void)
        {
            throw new Exception("Order status "+status1.getStatusString()+" cannot be edited. Order must be unposted before editing - item cannot be removed");
        }

        log.debug("1");
        try
        {
            itemID = Integer.parseInt(itemIDStr);
        }   catch(Exception itemex)
        {
            throw new Exception("Item ID "+itemIDStr+" not valid - item cannot be removed");
        }

        log.debug("1");
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.decode(status1.order_id));


        log.debug("1");
        List<OwdLineItem> itemlist = new ArrayList<OwdLineItem>();
        for(OwdLineItem lineitem: order.getLineitems())
        {
            if(itemID == lineitem.getLineItemId())
            {
                itemlist.add(lineitem);
            } else if(itemID == (lineitem.getParentKey()==null?-1:lineitem.getParentKey()))
            {
                itemlist.add(lineitem);
            }
        }

        log.debug("1");
        if(itemlist.size()==0)
        {
            throw new Exception("Item ID "+itemIDStr+" not found in order - item cannot be removed");
        }

        log.debug("1");
        if(itemlist.size()==order.getLineitems().size())
        {
            throw new Exception("You cannot remove the last item from an order - add a new item before removing this one");
        }

        log.debug("1");
        //got valid item
        order.getLineitems().removeAll(itemlist);
        for(OwdLineItem lineitem:itemlist)
        {
            HibernateSession.currentSession().delete(lineitem);

        }
        log.debug("1");
        OrderStatus.applyNewInvoiceTotalsToOwdOrder(order);

        HibernateSession.currentSession().save(order);
        log.debug("1");
        HibUtils.commit(HibernateSession.currentSession());
    }

}

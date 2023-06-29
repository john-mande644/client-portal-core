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
public class MarineEssentialsEssentialFloraPartialShipJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    static {
    }

    public static void main(String[] args) throws Exception {

        try {
            fixOrder(6984252);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
     //   run();
    }

    @Override
    public void internalExecute() {

        try {

            ResultSet rs = HibernateSession.getResultSet("select order_id, case when created_date>='2013-2-12' then 1 else 0 end as 'tue' from owd_order (NOLOCK) where datediff(minute,post_date,getdate())>5 and order_type='InfusionSoft Download' and order_status='At Warehouse' and client_fkey=494 and salesperson<>'D3P'");
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

        boolean ok = false;
        //is qualified?
        for (LineItem line : (Vector<LineItem>) (status.items)) {
            if ("ESF-B30".equals(line.client_part_no)) {
                if(status.getRequestQuantityForSKU("ESF-B30")>=3)
                {
                    ok = true;
                }
            }
        }
        if (ok) {
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

            //loop through all line items
            for (int parentindex=0;parentindex<status.items.size();parentindex++) {
                LineItem line = (LineItem)(status.items.get(parentindex));

                //if line is affected item
                if ("ESF-B30".equals(line.client_part_no)) {
                    //get parent line item id from item line
                    Integer parentKey = Integer.parseInt(line.line_item_id);

                    //again loop through all items
                    for (int lineindex=0;lineindex<status.items.size();lineindex++) {
                        LineItem lineitem = (LineItem)(status.items.get(lineindex));
                        //if we find an item with a parent line ID that matches our search...
                        if (lineitem.parent_line != null && parentKey.intValue() == lineitem.parent_line) {
                            //clear parent line id
                            lineitem.parent_line = null;

                            //if it is our item
                            if (lineitem.client_part_no.equals("ESF-B30")) {
                                //force backorder qty to original-2
                                lineitem.force_backorder_quantity = true;
                                lineitem.quantity_backordered = lineitem.quantity_request - 2 ;
                                lineitem.quantity_request = 2 ;
                            }
                        }
                    }
                    line.description = "(" + line.client_part_no + ") " + line.description;
                    line.client_part_no = "KITITEM";
                    line.inventory_fkey = "189758";
                    line.is_parent = 0;
                }
            }


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

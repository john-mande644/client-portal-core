package com.owd.intranet.adjustments.returns;

import com.owd.core.OWDUtilities;
import com.owd.core.managers.LotManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;

import com.owd.intranet.forms.returnForm;
import com.owd.intranet.adjustments.beans.returnItemBean;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdInventorySerial;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 9, 2006
 * Time: 9:14:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class loadReturnOrderAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        String ornum=null;
        String orid;

        try {

         ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            returnForm rf = (returnForm) form;
            ornum=rf.getOrderNum()+"";
            orid=rf.getOid()+"";

            log.debug("Ordernum:"+ornum);
            log.debug("Orderid:"+orid);
            if(!(OWDUtilities.isInteger(orid)))
            {
                orid = null;
            }

            rf.setStatus(null);
            List orders;

            if(orid == null)
            {
                log.debug("returnForm values:"+rf.toString());
                 log.debug("startsWith="+rf.isStartsWith());
                if(rf.isStartsWith())
                {
                    orders =  HibernateSession.currentSession()
                            .createQuery("from OwdOrder where shippedDate is not null and (orderRefnum like ? or orderNum like ?) and client.isActive=1 order by client.companyName, orderNum")
                            .setString(0,ornum+"%")
                            .setString(1,ornum+"%")
                            .setMaxResults(100)
                            .list();
                }    else
                {
                    orders =  HibernateSession.currentSession()
                            .createQuery("from OwdOrder where shippedDate is not null and (orderRefnum=? or orderNum=?) order by client.companyName, orderNum")
                            .setString(0,ornum)
                            .setString(1,ornum)
                            .list();
                }

            }else
            {
                orders =  HibernateSession.currentSession()
                        .createQuery("from OwdOrder where shippedDate is not null and (orderId=?) order by client.companyName, orderNum")
                        .setInteger(0, Integer.parseInt(orid))
                        .list();
            }

            log.debug("orders found: "+orders.size());
            if(orders.size()<1)
            {
                orders =  HibernateSession.currentSession()
                                            .createSQLQuery("select distinct o.* from owd_inventory_serial ois " +
                                                    " join owd_line_serial_link join owd_line_item join owd_order o on order_id=order_fkey on line_fkey=line_item_id" +
                                                    " on serial_fkey=ois.id" +
                                                    " where shipped_on is not null and serial_number=:serial ")
                                            .addEntity(OwdOrder.class)
                                            .setString("serial",ornum)

                                            .list();


                if(orders.size()<1)
                {
                    throw new Exception("OWD order reference "+ornum+" not found!");
                }
            }

            if(orders.size()>1)
            {
               List<OrderChoice> orderNums = new ArrayList<OrderChoice>();
               for(OwdOrder o:(List<OwdOrder>)orders)
               {
                   OrderChoice oc = new OrderChoice();
                   oc.setOrderNum(o.getOrderNum());
                   oc.setOid(""+o.getOrderId());
                   oc.setDescription(o.getClient().getCompanyName() + "/" + o.getOrderNum() + "/" + o.getOrderRefnum() + "/" + o.getShipinfo().getShipLastName() + "/" + o.getShipinfo().getShipCompanyName());
                   orderNums.add(oc);
               }
               request.setAttribute("orderList",orderNums);
                throw new Exception();
            }

            OwdOrder order = ((List<OwdOrder>)orders).get(0);
            rf = new returnForm();
            rf.setOrderId(order.getOrderId()+"");
            rf.setOid("");
            rf.getStatus();
            rf.setOrderName(order.getShipinfo().getShipFirstName() + " "+ order.getShipinfo().getShipLastName());
            log.debug("This is what we are setting for orderName");
            log.debug(rf.getOrderName());
            rf.setReceiveUser(request.getRemoteUser());
            rf.setReceiveDate(sdf.format(cal.getTime()));
            rf.setTimeIn(sdf.format(cal.getTime()));
            log.debug("This is the rf timein");
            log.debug(rf.getTimeIn());
            //log.debug("Setting stuff");
            rf.setCarrier("UPS Ground");
            rf.setClientFkey(order.getClientFkey()+"");
            rf.setRefNum(order.getOrderNum());
            rf.setCurrentLocation(order.getFacilityCode());
            returnItemBean rib[] = new returnItemBean[order.getLineitems().size()];
            Iterator<OwdLineItem> it = order.getLineitems().iterator();
            int i=0;
            while(it.hasNext()){
                OwdLineItem ol= it.next();
                returnItemBean rb = new returnItemBean();
                 rb.setCreatedBy(request.getRemoteUser());
            rb.setCreatedDate(cal.getTime().toString());
                rb.setReasonList();
                rb.setItemStatus("Returned");
                if(ol.getOwdInventory().getRequireSerialNumbers()==1)
                {
                   rb.setSerialRequired(true);
                   rb.setSerial(""+((ol.getSerials().size() == 1)?((OwdInventorySerial)ol.getSerials().toArray()[0]).getSerialNumber():""));
                }   else
                {
                    rb.setSerial("");
                }
                rb.setInventoryNum(ol.getInventoryNum());
                rb.setInventoryId(ol.getOwdInventory().getInventoryId().toString());
                rb.setDescription(ol.getDescription());
              //  rb.setInvOnHand(ol.getOwdInventory().getOwdInventoryOh().getQtyOnHand() + "");
                rb.setInvOnHand(""+ OrderUtilities.getAvailableInventory(ol.getOwdInventory().getInventoryId() + "", FacilitiesManager.getFacilityForCode(rf.getCurrentLocation()).getId()));
                rb.setLotControlled(LotManager.isInventoryIdLotControlled(ol.getOwdInventory().getInventoryId()));
                log.debug("This is our lot Controlled for this item: "+ rb.isLotControlled());
                if(rb.isLotControlled()){
                    rb.setLotList(LotManager.getLotValuesForInventoryId(ol.getOwdInventory().getInventoryId()));
                    log.debug(rb.getLotList().size());
                }
                rib[i]=rb;
                i++;
            }
            rf.setOrderNum("");
             rf.setFormItems(rib);
            request.getSession(true).setAttribute("returnForm",rf);
            saveToken(request);
            return (mapping.findForward("success"));
        } catch (ObjectNotFoundException ofn){
            request.setAttribute("errors", "Order "+ornum+" not found");
            return mapping.findForward("error");
        }
        catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }

    public static void main(String[] args) throws Exception
    {

       String ornum="PJ-NDC-72ILVP";

       List orders =  HibernateSession.currentSession()
                .createQuery("from OwdOrder where shippedDate is not null and (orderRefnum like ? or orderNum like ?)  and client.isActive=1  order by client.companyName, orderNum")
                .setString(0,ornum+"%")
                .setString(1,ornum+"%")
                .list();
        System.out.println("found: "+orders.size());

    }
    public class OrderChoice
    {
        String orderNum;
        String description;
        String oid;

        public String getOid() {
            return oid;
        }

        public void setOid(String orderId) {
            this.oid = orderId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

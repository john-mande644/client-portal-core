package com.owd.dc.picking;

import com.owd.WMSUtils;
import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.core.business.EventFeeds;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.OrderFactory;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.*;
import com.owd.dc.setup.emailList;
import com.owd.hibernate.generated.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 21, 2005
 * Time: 9:56:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class WarehouseHoldUtilities {

    public static void setPostedOrderOnHold(OrderPickStatus pstat, HttpServletRequest request) throws Exception {
        try {

            OwdOrderShipHold holder = new OwdOrderShipHold();
            holder = OrderFactory.getNewOwdOrderShipHold();
            OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(pstat.getOrderNum());
            holder.setWhHoldReason(pstat.getHoldReason());
            holder.setResNotifyAM("1");
            holder.setNotifyAM("1");
            holder.setNeedsReview(new Integer(1));
            holder.setHoldLocation(pstat.getHoldLocation());
            Iterator it = pstat.getOrderPickItems().iterator();

            
            //Session sess = HibernateSession.currentSession();
            /*System.out.println("3");
              Criteria crit = HibernateSession.currentSession().createCriteria(OrderPickItem.class);
            crit.setMaxResults(1000);
            crit.add(Expression.eq("dcHold", new Integer(1)));
                System.out.println(pstat.getId());
            crit.add(Expression.eq("orderPickStatu",pstat));
            System.out.println("4");
            List pickList = crit.list();*/
            //  System.out.println("Got items on hold");

            if (order.getHoldinfo() != null) {
                HibernateSession.currentSession().saveOrUpdate(order);
                HibernateSession.currentSession().saveOrUpdate(order.getHoldinfo());
                HibernateSession.currentSession().delete(order.getHoldinfo());
                HibernateSession.currentSession().flush();
            }
            //get info from request and update or create OwdOrderShipHold object
            //  holder.setWhHoldReason(HomeServlet.getStringParam(req,"wh_hold_reason",""));
            StringBuffer notes = new StringBuffer();
            while (it.hasNext()) {

                OrderPickItem pickItem = (OrderPickItem) it.next();
                if (pickItem.getDcHold() == 1) {
                    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(pickItem.getItemBarcode()));

                    notes.append("Sku: " + inv.getInventoryNum() + "\r\n Inventory Id: " + inv.getInventoryId());
                    notes.append("\r\nis Reported out of stock in the following locations;\r\n");
                    notes.append(pickItem.getLocList() + "\r\n");
                    notes.append(pickItem.getQtyPicked() + " out of " + pickItem.getQtyToPick() + " units were picked\r\n");
                    notes.append("The system says we have " + OrderUtilities.getAvailableInventory(inv.getInventoryId() + "", FacilitiesManager.getFacilityForCode(WMSUtils.getFacilityFromRequest(request)).getId()) + " on Hand\r\n\r\n");

                }
            }
            holder.setWhHoldNotes(notes.toString());

            // holder.setResNotifyUser(HomeServlet.getStringParam(req,"resNotifySelf",""));
            //  if("1".equals(holder.getResNotifyUser())) { holder.setResNotifyUser(user.getEmail());}

            //  holder.setResNotifyPhone(HomeServlet.getStringParam(req,"resNotifyPhone",""));
            //  if("1".equals(holder.getResNotifyPhone())) { holder.setResNotifyPhone(HomeServlet.getStringParam(req,"resNotifyPhoneText",""));}
            //     else
            // {
            holder.setResNotifyPhone("");
            // }

            // holder.setResNotifyEmail(HomeServlet.getStringParam(req,"resNotifyEmail",""));
            // if("1".equals(holder.getResNotifyEmail())) { holder.setResNotifyEmail(HomeServlet.getStringParam(req,"resNotifyEmailAddress",""));}
            //      else
            // {
            holder.setResNotifyEmail("");
            // }


            //  holder.setResNotifyAM(HomeServlet.getStringParam(req,"resNotifyAM",""));
            if ("1".equals(holder.getResNotifyAM())) {
                holder.setResNotifyAM(order.getClient().getAmEmail());
            }

            //  holder.setNotifyIT(HomeServlet.getStringParam(req,"notifyIT",""));

            // holder.setNotifyEmail(HomeServlet.getStringParam(req,"notifyEmail",""));
            //  if("1".equals(holder.getNotifyEmail())) { holder.setNotifyEmail(HomeServlet.getStringParam(req,"notifyEmailAddress",""));}
            //     else
            // {
            holder.setNotifyEmail(emailList.getInstance().getHoldEmail());
            //  }



           /* holder.setNotifyUser(HomeServlet.getStringParam(req,"notifyUser",""));
            if("1".equals(holder.getNotifyUser())) { holder.setNotifyUser(user.getEmail());}

            holder.setNotifyAM(HomeServlet.getStringParam(req,"notifyAM",""));*/
            if ("1".equals(holder.getNotifyAM())) {
                holder.setNotifyAM(order.getClient().getAmEmail());
            }

            holder.setCreatedBy((String) request.getAttribute("loginName"));
            holder.setCreatedDate(Calendar.getInstance().getTime());


            //if invalid entry, throw Exception
            if (holder.getWhHoldReason().length() < 1) {
                throw new Exception("Please choose a reason for placing this order on hold before submitting it!");
            }

            if (holder.getResNotifyEmail().length() > 0) {
                try {
                    MailAddressValidator.validate(holder.getResNotifyEmail());
                } catch (Exception ex) {
                    throw new Exception("The email address entered to notify when the hold is resolved is incorrect. Please edit and resubmit.");
                }
            }
            if (holder.getNotifyEmail().length() > 0) {
                try {
                    MailAddressValidator.validate(holder.getNotifyEmail());
                } catch (Exception ex) {
                    throw new Exception("The email address entered to notify of the hold being placed is incorrect. Please edit and resubmit.");
                }
            }
            //save to db

            holder.setIsOnWhHold(new Integer("1"));
            holder.setOrder(order);
            order.setHoldinfo(holder);
            HibernateSession.currentSession().saveOrUpdate(order);
            HibernateSession.currentSession().saveOrUpdate(holder);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

            //OK, everything is saved and updated. Process notifications...
            StringBuffer message = new StringBuffer();
            message.append("\r\nThe warehouse has placed a shipping hold on order " + order.getOrderNum() + " (" + order.getOrderRefnum() + ")");
            message.append("\r\n\r\nClient: " + order.getClient().getCompanyName());
            message.append("\r\nBarcode: " + order.getOrderNumBarcode());
            message.append("\r\nHold placed by: " + holder.getCreatedBy());

            message.append("\r\n\r\nReason for hold: " + holder.getWhHoldReason());
            message.append("\r\n\r\n" + holder.getWhHoldNotes());

            message.append("\r\n\r\nThe following addresses have been notified of this event:");
            if (holder.getNotifyAM().length() > 1) message.append("\r\n * AM: " + holder.getNotifyAM());
            if (holder.getNotifyIT().length() > 0) message.append("\r\n * IT: casetracker@owd.com");
            if (holder.getNotifyEmail().length() > 1) message.append("\r\n * Email: " + holder.getNotifyEmail());
            if (holder.getNotifyUser().length() > 1) message.append("\r\n * User: " + holder.getNotifyUser());

            if (holder.getResNotifyPhone().length() > 1) {
                message.append("\r\n\r\nThe warehouse has requested that the following person be notified personally when this issue has been resolved:");
                message.append("\r\n * " + holder.getResNotifyPhone());
            }
            message.append("\r\n\r\nYou can resolve this issue and clear the hold at the following URL:\r\nhttp://internal.owd.com:8080/internal/warehouse/admin/holds/resolvehold.jsp?owdref=" + order.getOrderNum());
            message.append("\r\n\r\nPlease direct any inquiries or suggestions to improve this system to casetracker@owd.com!");


            if (holder.getNotifyAM().length() > 1) {
                try {
                    MailAddressValidator.validate(holder.getNotifyAM());

                    if (order.getClient().getClientId() == 304) {
                        Mailer.sendMail("Warehouse Order Hold Alert!", message.toString(), holder.getNotifyAM(), "do-not-reply@owd.com");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (holder.getNotifyEmail().length() > 1) {
                try {
                    MailAddressValidator.validate(holder.getNotifyEmail());

                    Mailer.sendMail("Warehouse Order Hold Alert!", message.toString(), holder.getNotifyEmail(), "do-not-reply@owd.com");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (holder.getNotifyIT().length() > 0) {
                try {
                    Mailer.sendMail("Warehouse Order Hold Alert!", message.toString(), "casetracker@owd.com", holder.getNotifyUser());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (holder.getNotifyUser().length() > 1) {
                try {
                    MailAddressValidator.validate(holder.getNotifyUser());

                    Mailer.sendMail("Warehouse Order Hold Alert!", message.toString(), holder.getNotifyUser(), "do-not-reply@owd.com");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


            //update order with event information
            System.out.println("saving event with user " + request.getAttribute("loginName"));

            Event.addOrderEvent(order.getOrderId().intValue(), Event.kEventTypeHandling, "Order placed on warehouse hold due to " + holder.getWhHoldReason() +
                    "\r\n" + notes, (String) request.getAttribute("loginName"));
            EventFeeds.reportOrderStockout(order.getOrderId(), order.getClientFkey(), EventFeeds.kManualSourceType, "Order placed on warehouse hold due to " + holder.getWhHoldReason() + "\r\n" + holder.getWhHoldNotes());
//Done!
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}





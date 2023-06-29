package com.owd.core.business.order.WarehouseHold;

import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.core.business.EventFeeds;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.OrderFactory;
import com.owd.core.business.order.OrderUtilities;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipHold;
import com.owd.hibernate.generated.OwdUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 10/3/13
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class holdUtilities {
private final static Logger log =  LogManager.getLogger();



    public static void resolveHeldOrder(holdInfoBean info, OwdUser user, OwdOrder order,OwdOrderShipHold holder) throws Exception
    {

        try
        {

            Session sess = HibernateSession.currentSession();


            //get info from request and update or create OwdOrderShipHold object
            holder.setResolutionType(info.getResolutionType());

            if (holder.getResolutionType().length()<1)
            {
                throw new Exception("Please choose a resolution for this hold before submitting it!");
            }

            holder.setResNote(info.getResolutionNote());

            if (holder.getResolutionType().equalsIgnoreCase("Other") && holder.getResNote().length()<3)
            {
                throw new Exception("Please add notes about how this hold was resolved submitting it!");
            }
            holder.setResolutionDate(Calendar.getInstance().getTime());
            holder.setClearedBy(user.getLogin());
            holder.setClearedDate(Calendar.getInstance().getTime());



            holder.setIsOnWhHold(new Integer("0"));
            holder.setOrder(order);
            order.setHoldinfo(holder);
            sess.saveOrUpdate(order);
            sess.saveOrUpdate(holder);
            sess.flush();
            HibUtils.commit(sess);;

            //OK, everything is saved and updated. Process notifications...
            StringBuffer message = new StringBuffer();
            message.append("\r\nThe shipping hold on order "+order.getOrderNum()+" ("+order.getOrderRefnum()+") has been cleared!");
            message.append("\r\n\r\nClient: "+order.getClient().getCompanyName());
            message.append("\r\nHold cleared by: "+holder.getClearedBy());

            message.append("\r\n\r\nReason for hold: "+holder.getWhHoldReason());
            message.append("\r\n\r\nAction taken to resolve problem: "+holder.getResolutionType());
            message.append("\r\n\r\nNotes: "+holder.getResNote());

            message.append("\r\n\r\nThe following addresses have been notified of this event:");
            if(holder.getResNotifyAM().length()>1) message.append("\r\n * AM: "+holder.getResNotifyAM());
            if(holder.getResNotifyEmail().length()>1) message.append("\r\n * Email: "+holder.getResNotifyEmail());
            if(holder.getResNotifyUser().length()>1) message.append("\r\n * User: "+holder.getResNotifyUser());

            if(holder.getResNotifyPhone().length()>1)
            {
                message.append("\r\n\r\nThe warehouse has requested that the following person be notified personally when this issue has been resolved:");
                message.append("\r\n * "+holder.getResNotifyPhone());
            }
            message.append("\r\n\r\nPlease direct any inquiries or suggestions to improve this system to casetracker@owd.com!");


            if(holder.getResNotifyAM().length()>1)
            {
                try
                {
                    MailAddressValidator.validate(holder.getResNotifyAM());


                    Mailer.sendMail("Warehouse Order Hold Cleared!", message.toString(), holder.getResNotifyAM(), "do-not-reply@owd.com");
                }catch(Exception ex){ex.printStackTrace();}
            }
            if(holder.getResNotifyEmail().length()>1)
            {
                try
                {MailAddressValidator.validate(holder.getResNotifyEmail());

                    Mailer.sendMail("Warehouse Order Hold Cleared!",message.toString(),holder.getResNotifyEmail(),"do-not-reply@owd.com");
                }catch(Exception ex){ex.printStackTrace();}
            }
            if(holder.getResNotifyUser().length()>1)
            {
                try
                {MailAddressValidator.validate(holder.getResNotifyUser());

                    Mailer.sendMail("Warehouse Order Hold Cleared!",message.toString(),holder.getResNotifyUser(),"do-not-reply@owd.com");
                }catch(Exception ex){ex.printStackTrace();}
            }


            //update order with event information
            //log.debug("saving event with user "+user.getLogin());

            Event.addOrderEvent(order.getOrderId().intValue(), Event.kEventTypeHandling, "Order warehouse hold cleared with action: " + holder.getResolutionType(), user.getLogin());
            //Done!
        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }   finally
        {
            HibernateSession.closeSession();
        }
    }
    public static void setPostedOrderOnHold(holdInfoBean info, OwdUser user, OwdOrder order,OwdOrderShipHold holder,String createdBy) throws Exception
    {
        //log.debug("y");
        try
        {

            Session sess = HibernateSession.currentSession();
            //log.debug("x");
            if(order.getHoldinfo()!=null && holder != null)
            {

                //log.debug("xo="+order);
                //log.debug("xh="+holder);
                //log.debug("xoh="+order.getHoldinfo());
                if(order.getHoldinfo().getId().intValue() != (holder.getId()==null?0:holder.getId().intValue()) && order.getHoldinfo().getId().intValue()>0)
                {

                    sess.saveOrUpdate(order);
                    sess.saveOrUpdate(order.getHoldinfo());
                    sess.delete(order.getHoldinfo());
                    sess.flush();
                }
            }
            //log.debug("xx");
            //  if(null==holder.getNeedsReview()){ holder.setNeedsReview(new Integer(0)); }

            //get info from request and update or create OwdOrderShipHold object
            holder.setWhHoldReason(info.getHoldReason());
            holder.setWhHoldNotes(info.getNotes());
            //log.debug("xxx");
            holder.setResNotifyUser(info.getResolutionNotifySelf());
            if("1".equals(holder.getResNotifyUser())) { holder.setResNotifyUser(user.getEmail());}
            //log.debug("x");
            holder.setResNotifyPhone(info.getResolutionNotifyPhone());
            if("1".equals(holder.getResNotifyPhone())) { holder.setResNotifyPhone(info.getResolutionNotifyPhoneNumber());}
            else
            {
                holder.setResNotifyPhone("");
            }

            holder.setResNotifyEmail(info.getResolutionNotifyEmail());
            if("1".equals(holder.getResNotifyEmail())) { holder.setResNotifyEmail(info.getResolutionNotifyEmailAddress());}
            else
            {
                holder.setResNotifyEmail("");
            }


            holder.setResNotifyAM(info.getResolutionNotifyAM());
            if("1".equals(holder.getResNotifyAM())) { holder.setResNotifyAM(order.getClient().getAmEmail());}

            holder.setNotifyIT(info.getNotifyIT());

            holder.setNotifyEmail(info.getNotifyEmail());
            if("1".equals(holder.getNotifyEmail())) { holder.setNotifyEmail(info.getNotifyEmailAddress());}
            else
            {
                holder.setNotifyEmail("");
            }



            holder.setNotifyUser(info.getNotifyUser());
            if("1".equals(holder.getNotifyUser())) { holder.setNotifyUser(user.getEmail());}

            holder.setNotifyAM(info.getNotifyAM());
            if("1".equals(holder.getNotifyAM())) { holder.setNotifyAM(order.getClient().getAmEmail());}

            holder.setCreatedBy(createdBy);
            holder.setCreatedDate(Calendar.getInstance().getTime());





            //if invalid entry, throw Exception
            if (holder.getWhHoldReason().length()<1)
            {
                throw new Exception("Please choose a reason for placing this order on hold before submitting it!");
            }

            if(holder.getResNotifyEmail().length()>0)
            {
                try
                {
                    MailAddressValidator.validate(holder.getResNotifyEmail());
                }catch(Exception ex)
                {
                    throw new Exception("The email address entered to notify when the hold is resolved is incorrect. Please edit and resubmit.");
                }
            }
            if(holder.getNotifyEmail().length()>0)
            {
                try
                {
                    MailAddressValidator.validate(holder.getNotifyEmail());
                }catch(Exception ex)
                {
                    throw new Exception("The email address entered to notify of the hold being placed is incorrect. Please edit and resubmit.");
                }
            }
            //save to db

            holder.setHoldLocation(0);
            holder.setIsOnWhHold(new Integer("1"));
            holder.setOrder(order);
            order.setHoldinfo(holder);
            sess.saveOrUpdate(order);
            sess.saveOrUpdate(holder);
            sess.flush();
            HibUtils.commit(sess);


            if(holder.getNeedsReview()!=1)
            {
                //OK, everything is saved and updated. Process notifications...
                StringBuffer message = new StringBuffer();
                message.append("\r\nThe warehouse has placed a shipping hold on order "+order.getOrderNum()+" ("+order.getOrderRefnum()+")");
                message.append("\r\n\r\nClient: "+order.getClient().getCompanyName());
                message.append("\r\nBarcode: "+order.getOrderNumBarcode());
                message.append("\r\nHold placed by: "+holder.getCreatedBy());

                message.append("\r\n\r\nReason for hold: "+holder.getWhHoldReason());
                message.append("\r\n\r\n"+holder.getWhHoldNotes());

                message.append("\r\n\r\nThe following addresses have been notified of this event:");
                if(holder.getNotifyAM().length()>1) message.append("\r\n * AM: "+holder.getNotifyAM());
                if(holder.getNotifyIT().length()>0) message.append("\r\n * IT: casetracker@owd.com");
                if(holder.getNotifyEmail().length()>1) message.append("\r\n * Email: "+holder.getNotifyEmail());
                if(holder.getNotifyUser().length()>1) message.append("\r\n * User: "+holder.getNotifyUser());

                if(holder.getResNotifyPhone().length()>1)
                {
                    message.append("\r\n\r\nThe warehouse has requested that the following person be notified personally when this issue has been resolved:");
                    message.append("\r\n * "+holder.getResNotifyPhone());
                }
                message.append("\r\n\r\nYou can resolve this issue and clear the hold at the following URL:\r\nhttp://internal.owd.com:8080/internal/warehouse/admin/holds/resolvehold.jsp?owdref="+order.getOrderNum());
                message.append("\r\n\r\nPlease direct any inquiries or suggestions to improve this system to casetracker@owd.com!");


                if(holder.getNotifyAM().length()>1)
                {
                    try
                    {
                        MailAddressValidator.validate(holder.getNotifyAM());


                        Mailer.sendMail("Warehouse Order Hold Alert!",message.toString(),holder.getNotifyAM(),"do-not-reply@owd.com");
                    }catch(Exception ex){ex.printStackTrace();}
                }
                if(holder.getNotifyEmail().length()>1)
                {
                    try
                    {MailAddressValidator.validate(holder.getNotifyEmail());

                        Mailer.sendMail("Warehouse Order Hold Alert!",message.toString(),holder.getNotifyEmail(),"do-not-reply@owd.com");
                    }catch(Exception ex){ex.printStackTrace();}
                }
                if(holder.getNotifyIT().length()>0)
                {
                    try
                    { Mailer.sendMail("Warehouse Order Hold Alert!",message.toString(),"casetracker@owd.com",holder.getNotifyUser());
                    }catch(Exception ex){ex.printStackTrace();}
                }
                if(holder.getNotifyUser().length()>1)
                {
                    try
                    {MailAddressValidator.validate(holder.getNotifyUser());

                        Mailer.sendMail("Warehouse Order Hold Alert!",message.toString(),holder.getNotifyUser(),"do-not-reply@owd.com");
                    }catch(Exception ex){ex.printStackTrace();}
                }

            }
            //update order with event information
            //log.debug("saving event with user "+user.getLogin());
           if(user!=null){
            Event.addOrderEvent(order.getOrderId().intValue(),Event.kEventTypeHandling,"Order placed on warehouse hold due to "+holder.getWhHoldReason()+"\r\n"+holder.getWhHoldNotes(),user.getLogin());
           }else{
               Event.addOrderEvent(order.getOrderId().intValue(),Event.kEventTypeHandling,"Order placed on warehouse hold due to "+holder.getWhHoldReason()+"\r\n"+holder.getWhHoldNotes(),createdBy);

           }

               EventFeeds.reportOrderStockout(order.getOrderId(), order.getClientFkey(), EventFeeds.kManualSourceType, "Order placed on warehouse hold due to " + holder.getWhHoldReason() + "\r\n" + holder.getWhHoldNotes());

            //Done!
        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }   finally
        {
            HibernateSession.closeSession();
        }
    }

    public static String setOrderShippingDCHold(String orderNum, String name, String error){

        String success = "Bad";
        try{

              OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(orderNum);
            OwdOrderShipHold holder = order.getHoldinfo();
            if(OrderUtilities.isShipping(order.getOrderId()+"")){
                //We need to void the WV shipment so we don't have issues down the road
                String s = voidWVShipment(order.getOrderId()+"");
                if(s.startsWith("Error")){
                    Mailer.sendMail("Error voiding WV shipment! ID: "+ order.getOrderId(),s,"casetracker@owd.com","do-not-reply@owd.com");

                    return "Unable to void WV shipment. Please contact IT";
                }

            }
         //   log.debug("la ti da");
            if(null == holder){
                holder = OrderFactory.getNewOwdOrderShipHold();
            }
            if (holder.getIsOnWhHold().intValue() == 1) {
               return "Already on Hold";
            }
            log.debug("la l ala ti da");
            holdInfoBean info = new holdInfoBean();
             info.setNotes(error);
            info.setNotifyAM("1");
              if(info.getNotes().contains("Invalid package weight")||info.getNotes().contains("Must equal or exceed the minimums for Length, Height, or Length+Girth.")
                      ||info.getNotes().contains("Invalid package weight")||info.getNotes().contains("allowed weight")) {
                  info.setHoldReason("Invalid Ship Method (for package)");
              }  else if(info.getNotes().contains("Invalid postal code or postal code not provided for Consignee")
                      || info.getNotes().contains("City name not provided for Consignee")){

                  info.setHoldReason("Invalid Address");
              }  else if(info.getNotes().contains("Destination not served")||info.getNotes().contains("Destination country not served")){

                  info.setHoldReason("Invalid Ship Method (for address)");
              }else{
                  info.setHoldReason("Manifesting Error");
              }
            info.setNotifyEmail("1");
           // info.setNotifyEmailAddress("dnickels@owd.com");
             log.debug("going to set it on hoold now");
            setPostedOrderOnHold(info,null,order,holder,name);

          success = "Good";
        } catch(Exception e){
            e.printStackTrace();
            success = "Error" +e.getMessage();
        }


        return success;


    }
    public static String setWorkOrderDCHold(String orderNum, String name, String workOrder){

        String success = "Bad";
        try{
            log.debug("1");
            OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(orderNum);
            log.debug("2");
            OwdOrderShipHold holder = order.getHoldinfo();
            log.debug("3");
            if (null==holder){
                holder = OrderFactory.getNewOwdOrderShipHold();
            }
            if (holder.getIsOnWhHold().intValue() == 1) {
                return "Already on Hold";
            } else {

            }
            log.debug("4");
            holdInfoBean info = new holdInfoBean();
            info.setNotes("Order has been placed on hold because of work order "+workOrder);
            info.setNotifyAM("1");

                info.setHoldReason("Work Order");

            //info.setNotifyEmail("1");
            //info.setNotifyEmailAddress("dnickels@owd.com");

            setPostedOrderOnHold(info,null,order,holder,name);

            success = "Good";
        } catch(Exception e){
            e.printStackTrace();
            success = "Error " +e.getMessage();
        }


        return success;


    }
    public static void main(String args[]){
          // log.debug(setOrderShippingDCHold("10844295","Danny","Package #1: Invalid weight for parcels, to this destination country  SHM5-A (2)"));
       // log.debug(setWorkOrderDCHold("14220401","Danny","276474"));
log.debug(voidWVShipment("1234"));

    }
    private static String voidWVShipment(String orderId)  {
        String status ="Error:";

        try {
            String url = "http://it.owd.com/wms/abShipment/voidShip.action";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "orderId=" + orderId;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            log.debug("\nSending 'POST' request to URL : " + url);
            log.debug("Post parameters : " + urlParameters);
            log.debug("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            log.debug(response.toString());
            return (response.toString());
        }catch(Exception e){
            e.printStackTrace();
            status += e.getMessage();

        }
        return status;
    }
}

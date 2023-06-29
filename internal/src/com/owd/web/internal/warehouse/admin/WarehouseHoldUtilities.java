package com.owd.web.internal.warehouse.admin;

import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipHold;
import com.owd.hibernate.generated.OwdUser;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.EventFeeds;
import com.owd.hibernate.*;
import com.owd.web.internal.servlet.HomeServlet;
import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.core.business.order.Event;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 22, 2005
 * Time: 10:59:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class WarehouseHoldUtilities {
private final static Logger log =  LogManager.getLogger();

    public static void resolveHeldOrder(HttpServletRequest req, OwdUser user, OwdOrder order,OwdOrderShipHold holder) throws Exception
    {

        try
        {

        Session sess = HibernateSession.currentSession();


        //get info from request and update or create OwdOrderShipHold object
        holder.setResolutionType(HomeServlet.getStringParam(req,"resolution_type",""));

             if (holder.getResolutionType().length()<1)
       {
           throw new Exception("Please choose a resolution for this hold before submitting it!");
       }

        holder.setResNote(HomeServlet.getStringParam(req,"res_note",""));
            holder.setWhHoldNotes(HomeServlet.getStringParam(req, "notes", ""));

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


               Mailer.sendMail("Warehouse Order Hold Cleared!",message.toString(),holder.getResNotifyAM(),"do-not-reply@owd.com");
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
        Event.addOrderEvent(order.getOrderId().intValue(),Event.kEventTypeHandling,"Order warehouse hold cleared with action: "+holder.getResolutionType(),user.getLogin());
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
    public static void setPostedOrderOnHold(HttpServletRequest req, OwdUser user, OwdOrder order,OwdOrderShipHold holder) throws Exception
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
        holder.setWhHoldReason(HomeServlet.getStringParam(req,"wh_hold_reason",""));
        holder.setWhHoldNotes(HomeServlet.getStringParam(req,"notes",""));
         //log.debug("xxx");
        holder.setResNotifyUser(HomeServlet.getStringParam(req,"resNotifySelf",""));
        if("1".equals(holder.getResNotifyUser())) { holder.setResNotifyUser(user.getEmail());}
              //log.debug("x");
        holder.setResNotifyPhone(HomeServlet.getStringParam(req,"resNotifyPhone",""));
        if("1".equals(holder.getResNotifyPhone())) { holder.setResNotifyPhone(HomeServlet.getStringParam(req,"resNotifyPhoneText",""));}
            else
        {
            holder.setResNotifyPhone("");
        }

        holder.setResNotifyEmail(HomeServlet.getStringParam(req,"resNotifyEmail",""));
        if("1".equals(holder.getResNotifyEmail())) { holder.setResNotifyEmail(HomeServlet.getStringParam(req,"resNotifyEmailAddress",""));}
            else
        {
            holder.setResNotifyEmail("");
        }


        holder.setResNotifyAM(HomeServlet.getStringParam(req,"resNotifyAM",""));
        if("1".equals(holder.getResNotifyAM())) { holder.setResNotifyAM(order.getClient().getAmEmail());}

        holder.setNotifyIT(HomeServlet.getStringParam(req,"notifyIT",""));

        holder.setNotifyEmail(HomeServlet.getStringParam(req,"notifyEmail",""));
        if("1".equals(holder.getNotifyEmail())) { holder.setNotifyEmail(HomeServlet.getStringParam(req,"notifyEmailAddress",""));}
            else
        {
            holder.setNotifyEmail("");
        }



        holder.setNotifyUser(HomeServlet.getStringParam(req,"notifyUser",""));
        if("1".equals(holder.getNotifyUser())) { holder.setNotifyUser(user.getEmail());}

        holder.setNotifyAM(HomeServlet.getStringParam(req,"notifyAM",""));
        if("1".equals(holder.getNotifyAM())) { holder.setNotifyAM(order.getClient().getAmEmail());}

        holder.setCreatedBy(req.getRemoteUser());
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

        Event.addOrderEvent(order.getOrderId().intValue(),Event.kEventTypeHandling,"Order placed on warehouse hold due to "+holder.getWhHoldReason()+"\r\n"+holder.getWhHoldNotes(),user.getLogin());
        EventFeeds.reportOrderStockout(order.getOrderId(),order.getClientFkey(),EventFeeds.kManualSourceType,"Order placed on warehouse hold due to "+holder.getWhHoldReason()+"\r\n"+holder.getWhHoldNotes());

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
}

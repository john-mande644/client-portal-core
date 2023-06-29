package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class DCHoldNotifyJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public DCHoldNotifyJob() {
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Interface.
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * <p/>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * @throws org.quartz.JobExecutionException
     *          if there is an exception while executing the job.
     */

    public void internalExecute() {
        try {
            Session sess = HibernateSession.currentSession();
            DateFormat fd = new SimpleDateFormat("yyyy-mm-dd hh:mm");

            ResultSet rs = HibernateSession.getResultSet("select am_email,company_name,order_num,post_date,order_status,wh_hold_reason,wh_hold_notes,datediff(day,s.created_date,getdate()) as 'Age' from owd_order_ship_holds s join owd_order join owd_client on client_id=client_fkey on order_id=order_fkey \n" +
                    "where is_on_wh_hold=1 and needs_review=0 order by am_email ,Age desc");

            String oldam = "";
            StringBuffer message = null;

           while(rs.next())
           {

              if(oldam.equals(rs.getString(1)))
              {
                  //add to message
                  message.append("\r\n=== Order "+rs.getString(3)+" : "+rs.getString(2)+" === "+rs.getString(8)+" "+(rs.getInt(8)==1?"Day":"Days")+" Old ===\r\n");
                  message.append("Status: "+rs.getString(5)+(rs.getDate(4)==null?"":"  Post Date: "+fd.format(rs.getDate(4)))+"\r\n");
                  message.append("Hold Reason: "+rs.getString(6)+"\r\n"+rs.getString(7)+"=== === ===\r\n\r\n");


              }                   else
              {
                  //start new message and send old one if it exists
                  if(message != null)
                  {
                     Mailer.sendMail("DC Hold Aging Report ",message.toString(),oldam,"robots@owd.com");
                  }
                  oldam=rs.getString(1);
                  message = new StringBuffer();

                  message.append("\r\n=== Order "+rs.getString(3)+" : "+rs.getString(2)+" === "+rs.getString(8)+" "+(rs.getInt(8)==1?"Day":"Days")+" Old ===\r\n");
                  message.append("Status: "+rs.getString(5)+(rs.getDate(4)==null?"":"  Post Date: "+fd.format(rs.getDate(4)))+"\r\n");
                  message.append("Hold Reason: "+rs.getString(6)+"\r\n"+rs.getString(7)+"=== === ===\r\n\r\n");

              }


           }
             if(message != null)
                  {
                     Mailer.sendMail("DC Hold Aging Report ",message.toString(),oldam,"robots@owd.com");
                  }

        } catch (Exception ex) {
              ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }




}

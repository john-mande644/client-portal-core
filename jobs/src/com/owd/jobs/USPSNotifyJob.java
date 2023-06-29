package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class USPSNotifyJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public USPSNotifyJob() {
    }


      public static void main(String[] args) {
        run();
       //   updateInventoryStockLevels();

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
     *
     * @throws org.quartz.JobExecutionException
     *          if there is an exception while executing the job.
     */

    public void internalExecute() {
        try {
            DateFormat fd = new SimpleDateFormat("yyyy-mm-dd hh:mm");

            ResultSet rs = HibernateSession.getResultSet("select * from vw_uspsdailyorders order by ShipMethod,Orders desc");


            //
//select * from vw_uspsdailyorders
//select * from vw_yesterdayevsorders
            StringBuffer message = new StringBuffer();
            message.append("\r\nPending Orders "+ fd.format(Calendar.getInstance().getTime())+"\r\n\r\n");


           while(rs.next())
           {

                message.append(OWDUtilities.padRight(rs.getString("Orders"),8)+OWDUtilities.padRight(rs.getString("ZipCode"),8)+rs.getString("ShipMethod")+"\r\n");

           }

            HibernateSession.closeSession();

            rs = HibernateSession.getResultSet("select * from vw_yesterdayevsorders order by Packages,ShipMethod desc");


            message.append("\r\n\r\n\r\nYesterday's eVS Packages \r\n\r\n");


                     while(rs.next())
                     {

                          message.append(OWDUtilities.padRight(rs.getString("Packages"),8)+rs.getString("ShipMethod")+"\r\n");

                     }

                      HibernateSession.closeSession();


            log.debug(message);


             if(message != null)
                  {
                      Mailer.sendMail("OWD Daily Volume Report",message.toString(),"Joseph.M.Carmody@usps.gov","robots@owd.com");
                      Mailer.sendMail("OWD Daily Volume Report",message.toString(),"Timothy.M.Oconnor@usps.gov","robots@owd.com");
                      Mailer.sendMail("OWD Daily Volume Report",message.toString(),"Cindy.L.Maier@usps.gov");
                      Mailer.sendMail("OWD Daily Volume Report",message.toString(),"Jolyn.M.Hardcastle@usps.gov","robots@owd.com");
                      Mailer.sendMail("OWD Daily Volume Report",message.toString(),"owditadmin@owd.com","robots@owd.com");
                      Mailer.sendMail("OWD Daily Volume Report",message.toString(),"kyle@owd.com","robots@owd.com");
                      Mailer.sendMail("OWD Daily Volume Report",message.toString(),"Lori.D.Larson@usps.gov","robots@owd.com");


                  }

        } catch (Exception ex) {
              ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }




}

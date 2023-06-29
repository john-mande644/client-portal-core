package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;

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
public class OriliNotifyJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public OriliNotifyJob() {
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
            log.debug("starting OriliNotifyJob");
            DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");

            ResultSet rs = HibernateSession.getResultSet("select * from vw_orili_complete where datediff(week,[sold out date],getdate())<1 order by [ASN Reference],SKU");


            //
//select * from vw_uspsdailyorders
//select * from vw_yesterdayevsorders
            StringBuffer message = new StringBuffer();
            message.append("\r\n>>>Receives Sold Out This Week: \r\n\r\n");
            String asn = "xx";

           while(rs.next())
           {
               String newAsn=rs.getString("ASN Reference");
               if(!(asn.equals(newAsn)))
               {
                   message.append("\r\nRef: "+newAsn+"\r\n\r\n");
                   asn = newAsn;
               }
               message.append("      SKU: "+OWDUtilities.padRight(rs.getString("SKU"), 8) + "Sold Out: "+fd.format(rs.getDate("SOld Out Date")) + "\r\n");

           }

            HibernateSession.closeSession();

            rs = HibernateSession.getResultSet("select * from vw_orili_incomplete order by [pct sold] desc,[ASN Reference] asc,SKU asc");

            asn = "xx";
            message.append("\r\n\r\n\r\n>>>Incomplete Receives: \r\n\r\n");


                     while(rs.next())
                     {


                         String newAsn=rs.getString("ASN Reference");
                         if(!(asn.equals(newAsn)))
                         {
                             message.append("\r\nRef: "+newAsn+"\r\n\r\n");
                             asn = newAsn;
                         }
                         message.append("      SKU: "+OWDUtilities.padRight(rs.getString("SKU"), 32) + "Percent Sold: "+OWDUtilities.roundFloat(rs.getFloat("Pct Sold")*100.00f,0) + "% ("+rs.getInt("Units Remaining")+" left)\r\n");
                     }

                      HibernateSession.closeSession();


            log.debug(message);


             if(message != null)
                  {
                    // Mailer.sendMail("Weekly Item Sold Status Report",message.toString(),"Udi Tirosh <udi@uditirosh.com>","robots@owd.com");
                    //    Mailer.sendMail("Weekly Item Sold Status Report",message.toString(),"owditadmin@owd.com","robots@owd.com");
                    //    Mailer.sendMail("Weekly Item Sold Status Report",message.toString(),"glindskov@owd.com","robots@owd.com");
                  }

        } catch (Exception ex) {
              ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }




}

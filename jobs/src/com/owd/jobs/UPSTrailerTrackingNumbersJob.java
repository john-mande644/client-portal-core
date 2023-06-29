package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 17, 2006
 * Time: 12:58:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class UPSTrailerTrackingNumbersJob   extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    static String sweepSQL = "select distinct tracking_no from owd_order_track t (NOLOCK) join owd_order o (NOLOCK) on o.order_id=t.order_fkey where t.created_date>=? and \n" +
            "t.created_date <= ? and t.tracking_no like '1Z%' and ISNULL(o.is_shipping,0)=0";

       public static void main(String[] args) {

        run();
    }

    public void internalExecute() {

                //log.debug("start sweep UPS trailer");
            try
            {

                Session sess = HibernateSession.currentSession();

                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY,18);
                today.set(Calendar.MINUTE,0);
                today.set(Calendar.SECOND,0);
                today.set(Calendar.MILLISECOND,0);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

                //log.debug(df.format(today.getTime()));

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY,18);
                startTime.set(Calendar.MINUTE,0);
                startTime.set(Calendar.SECOND,0);
                startTime.set(Calendar.MILLISECOND,1);

                if(today.get(Calendar.DAY_OF_WEEK)==7 || today.get(Calendar.DAY_OF_WEEK)==1)
                {
                    return; //is a non-UPS pickup date
                }

                if(today.get(Calendar.DAY_OF_WEEK)==2)
                {
                    //Monday, so pull from previous Friday
                    startTime.add(Calendar.DAY_OF_WEEK,-3);

                }   else
                {
                    //normal, pull from previous day
                    startTime.add(Calendar.DAY_OF_WEEK,-1);
                }

                //log.debug(df.format(startTime.getTime()));


                PreparedStatement ps = HibernateSession.getPreparedStatement(sweepSQL);
                ps.setString(1,df.format(startTime.getTime()));
                ps.setString(2,df.format(today.getTime()));

                ResultSet rs = ps.executeQuery();
                int tracks = 0;
                StringBuffer sb = new StringBuffer();
                while(rs.next())
                {
                   sb.append(rs.getString(1)+"\r\n");
                    tracks++;
                }

                //log.debug("Got "+tracks+" tracking numbers");




               /* Mailer.sendMailWithAttachment("One World Direct Trailer Tracking "+df2.format(today.getTime()),
                        "Attached are tracking numbers for the trailer picked up on "+df2.format(today.getTime())+". Please contact 605-845-7172 or 605-845-5540 for assistance with any problems.",
                        "jweisz@ups.com",
                        sb.toString().getBytes(),
                        "OWDTracks"+df2.format(today.getTime()),"text/csv");*/

                Mailer.sendMailWithAttachment("One World Direct Trailer Tracking "+df2.format(today.getTime()),
                        "Attached are tracking numbers for the trailer picked up on "+df2.format(today.getTime())+". Please contact 605-845-7172 or 605-845-5540 for assistance with any problems.",
                        "cpodoll@ups.com",
                        sb.toString().getBytes(),
                        "OWDTracks"+df2.format(today.getTime()),"text/csv");

               //  Mailer.sendMail("test test","my text","owditadmin@owd.com","do-not-reply@owd.com");
             /*    Mailer.sendMailWithAttachment("One World Direct Trailer Tracking "+df2.format(today.getTime()),
                        "Attached are tracking numbers for the trailer picked up on "+df2.format(today.getTime())+". Please contact 605-845-7172 or 605-845-5540 for assistance with any problems.",
                        "sbuskirk@mac.com",
                        sb.toString().getBytes(),
                        "OWDTracks"+df2.format(today.getTime()),"text/csv");
*/
            }catch(Throwable ex)
            {
                ex.printStackTrace();
                Mailer.postMailMessage("Error during UPS tracking number sweep",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","alerts@owd.com");
            }finally{
               HibernateSession.closeSession();
            }

                //log.debug("end sweep UPS trailer");

        }



}

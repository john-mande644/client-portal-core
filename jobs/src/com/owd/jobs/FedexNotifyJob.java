package com.owd.jobs;

import com.owd.core.managers.FacilitiesManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class FedexNotifyJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public FedexNotifyJob() {
    }


      public static void main(String[] args) {
        run();

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
            DateFormat fd = new SimpleDateFormat("yyyy-MM-dd hh:mm");

            ResultSet rs = HibernateSession.getResultSet("select * from vw_fedexdailyorders order by Location,ShipMethod,Orders desc");



            Map<String,List<String>> fedexOrdersMap = new HashMap<String,List<String>>();

           while(rs.next())
           {
                 String loc = rs.getString("Location");

               if(!(fedexOrdersMap.containsKey(loc)))
               {
                   fedexOrdersMap.put(loc,new ArrayList<String>());
               }
               fedexOrdersMap.get(loc).add(OWDUtilities.padRight(rs.getString("Orders"),10)+rs.getString("ShipMethod"));
           }

            HibernateSession.closeSession();



for(String loc:fedexOrdersMap.keySet())
{


    StringBuilder message = new StringBuilder("\r\nPending "+ FacilitiesManager.getFacilityDisplayLabelForCode(loc)+" OWD Fedex Orders "+ fd.format(Calendar.getInstance().getTime())+"\r\n\r\n");
    List<String> methods = fedexOrdersMap.get(loc);
    for(String method:methods){
        message.append(method+"\r\n");
    }

    if("DC1".equals(loc))
    {
        Mailer.sendMail("OWD Daily Volume Report - "+ FacilitiesManager.getFacilityDisplayLabelForCode(loc),message.toString(),"sjmclean@fedex.com","donotreply@owd.com");
        Mailer.sendMail("OWD Daily Volume Report - "+ FacilitiesManager.getFacilityDisplayLabelForCode(loc),message.toString(),"steven.alling@fedex.com","donotreply@owd.com");
        Mailer.sendMail("OWD Daily Volume Report - "+ FacilitiesManager.getFacilityDisplayLabelForCode(loc),message.toString(),"owditadmin@owd.com","donotreply@owd.com");
        Mailer.sendMail("OWD Daily Volume Report - "+ FacilitiesManager.getFacilityDisplayLabelForCode(loc),message.toString(),"kyle@owd.com","donotreply@owd.com");
    } else if("DC6".equals(loc)) {

        Mailer.sendMail("OWD Daily Volume Report - "+ FacilitiesManager.getFacilityDisplayLabelForCode(loc),message.toString(),"nikki.heier@fedex.com","donotreply@owd.com");
        Mailer.sendMail("OWD Daily Volume Report - "+ FacilitiesManager.getFacilityDisplayLabelForCode(loc),message.toString(),"mlsupervisors@owd.com","donotreply@owd.com");
    }


}


        } catch (Exception ex) {
              ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }




}

package com.owd.jobs

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession
import com.owd.LogableException

import java.sql.ResultSet

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 11/30/12
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
class DeadmanSwitchJob extends OWDStatefulJob {


    public static void main (String[] args) throws Exception
    {
        run()
    }
    @Override
    void internalExecute() {
        //To change body of implemented methods use File | Settings | File Templates.

        //get list of events to send

        //iterate, process and delete on success
       // BabelandOneStopCheck()
        PrintserverActivityCheck()

    }

    private void BabelandOneStopCheck() {
        try {
            ResultSet rs = HibernateSession.getResultSet("select name,datediff(minute,max(event_time),getdate()) 'Minutes Since Last Start' from owd_jobs\n" +
                    "where event_type='START'\n" +
                    "group by name\n" +
                    "order by datediff(minute,max(event_time),getdate()) desc");

            while (rs.next()) {
                if (rs.getString(1).contains("onestop.ImportOrders")) {
                    if (rs.getInt(2) > 90) {
                        throw new LogableException(new Exception(), "No onestop run for 90 minutes - restart jobs webapp!", "TS:" + Calendar.getInstance().getTimeInMillis(), "482", "OneStop", LogableException.errorTypes.ORDER_IMPORT)

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

    private void PrintserverActivityCheck() {
        try {
            ResultSet rs = HibernateSession.getResultSet("select value,variable,display, datediff(minute,CONVERT(DATETIME,display, 0),getdate()) from app_data  WHERE project = 'wms' and description = 'printserver' and variable in('download','printCheck')");

            StringBuilder errorReport = new StringBuilder()
            while (rs.next()) {
                int delayMinutes = rs.getInt(4);

                if (delayMinutes>=15) {
                    errorReport.append(rs.getString(1)+" printserver "+rs.getString(2)+" function has not run for "+delayMinutes+" minutes\n")


                }
            }
            if(errorReport.toString().length()>1)
            {
                throw new LogableException(errorReport.toString(), "No printserver activity for 15 minutes!", "TS:" + Calendar.getInstance().getTimeInMillis(), "55", "Printserver Deadman Switch", LogableException.errorTypes.INTERNAL)

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }


}

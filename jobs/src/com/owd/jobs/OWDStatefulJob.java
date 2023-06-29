package com.owd.jobs;

import groovy.util.logging.Log4j2;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.*;
import com.owd.jobs.jobobjects.JobThreadInfo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.sql.PreparedStatement;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 23, 2007
 * Time: 10:33:33 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class OWDStatefulJob implements StatefulJob {
    public final static Logger alog =  LogManager.getLogger();
    public final static int OWD_CLIENT_ID = 55;
     public JobThreadInfo threadInfo = new JobThreadInfo();



      /**
     * Only run from concrete subclass of this abstract class - call from main method of subclass to auto-run internalExecute method
     */
    public static void run()
    {
        String className="";

        try
          {
          Throwable stack = new Throwable();
              stack.printStackTrace();
          //    log.debug(""+stack.getStackTrace());

              for(int i=0;i<stack.getStackTrace().length;i++)
              {
                  if(stack.getStackTrace()[i].getClassName().startsWith("com.owd.jobs."))
                  {
                      className =  stack.getStackTrace()[i].getClassName();
                  }
               //  log.debug(i+":"+stack.getStackTrace()[i].getClassName());
              }
          stack.fillInStackTrace();
              Class myClass = (Class.forName(className));
            //  log.debug(""+myClass.getCanonicalName());
          myClass.getMethod("internalExecute",new Class[0]).invoke(myClass.newInstance());
          }catch(Exception ex)
          {


              ex.printStackTrace();
          }
    }

    JobExecutionContext jobContext = null;

    public JobExecutionContext getJobContext() {
        return jobContext;
    }

    public void setJobContext(JobExecutionContext jobContext) {
        this.jobContext = jobContext;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try
        {

         setJobContext(jobExecutionContext);
        //log.debug("---" + jobExecutionContext.getJobDetail().getFullName()
        //        + " executing.[" + new Date() + "]");

            PreparedStatement ps = HibernateSession.getPreparedStatement("insert into owd_jobs (name,event_time,event_type) VALUES (?,?,?) ");
            ps.setString(1,jobExecutionContext.getJobDetail().getFullName());
            ps.setString(2, OWDUtilities.getSQLDateTimeForToday());
            ps.setString(3,"START");
            ps.executeUpdate();

            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

            threadInfo.jobThread = Thread.currentThread();

        internalExecute();

            ps = HibernateSession.getPreparedStatement("insert into owd_jobs (name,event_time,event_type) VALUES (?,?,?) ");
            ps.setString(1,jobExecutionContext.getJobDetail().getFullName());
            ps.setString(2, OWDUtilities.getSQLDateTimeForToday());
            ps.setString(3,"STOP");
            ps.executeUpdate();

                 HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            
            ps = HibernateSession.getPreparedStatement("delete from owd_jobs where abs(datediff(day,getdate(),event_time))>7");

            ps.executeUpdate();

            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

        alog.debug("---" + jobExecutionContext.getJobDetail().getFullName()
                + " complete.[" + new Date() + "]");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new JobExecutionException(ex);
        }        finally
        {
            HibernateSession.closeSession();
         //   HibernateGalapagosSession.closeSession();
         //   HibernateBeerBellySession.closeSession();
            HibernateAdHocSession.closeSession();
          //  HibernateBillingSession.closeSession();
            HibernateBoxworksSession.closeSession();
            //HibernateFogbugzSession.closeSession();
            HibernateClientReportsSession.closeSession();
          //  HibernateTimeForceSession.closeSession();

        }

    }

    abstract public void internalExecute();

     //optional - override in job for custom notification
     public void notifyClient()
     {

     }
}

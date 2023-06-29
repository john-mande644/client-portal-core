package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;

import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class OldASNCheckJob  extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();




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


            PreparedStatement stmt = HibernateSession.getPreparedStatement("update asn set status=1 where status in (0,2)  and ((datediff(day,getdate(),expect_date)<-90)  and not (datediff(day,getdate(),isnull(last_receive_date,expect_date))>-90))");

            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());


        } catch (Exception ex) {

        } finally {
            HibernateSession.closeSession();
        }
    }

    public static void main(String[] args) {
     //   OldASNCheckJob.internalExecute();

        run();
    }


}

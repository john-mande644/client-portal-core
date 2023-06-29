package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class shipmentUpdateTempJob   extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public shipmentUpdateTempJob() {
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

            int iter = 0;
            List orderList = new ArrayList();

            for (int i=0;i<40;i++)
            {
            ResultSet rs = HibernateSession.getResultSet("set ROWCOUNT 5000;select distinct order_id from owd_order o (NOLOCK) " +
                    "join owd_order_track t (NOLOCK) on order_fkey=order_id and t.is_void=0 " +
                    "where post_date is not null and isnull(o.is_shipping,0)=0 and " +
                    "(shipped_units < 1 or shipped_units is null) and o.is_void=0 order by order_id desc;set ROWCOUNT 0;");

            while(rs.next())
            {
                orderList.add(new Integer(rs.getInt(1)));
            }

            HibernateSession.closeStatement();
                HibernateSession.closeSession();

            Iterator it = orderList.iterator();

            while(it.hasNext())
            {
                iter++;
                PreparedStatement stmt = HibernateSession.getPreparedStatement("exec update_order_shipment_info "+it.next());

            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
                HibernateSession.closePreparedStatement();

                //log.debug("Iter:"+iter);
            }
            }

        } catch (Exception ex) {
              ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

    public static void main(String[] args) {
      //  shipmentUpdateTempJob.internalExecute();

    }


}

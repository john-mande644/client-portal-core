package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateInventoryMovementsJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public UpdateInventoryMovementsJob() {
    }


      public static void main(String[] args) {
        //run();
       //   updateInventoryStockLevels();
        updateRecords();
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

            PreparedStatement ps = HibernateSession.getPreparedStatement("\n" +
                    "delete from owd_inventory_daily_movement where transactiondate>=convert(datetime,convert(varchar,dateadd(day,-7,getdate()),101));\n" +
                    "\n" +
                    "insert into owd_inventory_daily_movement (inventory_id,inventory_num,client_fkey,received,adjusted,returned,shipped,transactiondate,damaged) select inventory_id,inventory_num,client_fkey,received,adjusted,returned,shipped,transactiondate,damaged \n" +
                    "from vw_owd_inv_daily_movement_test where transactiondate>=convert(datetime,convert(varchar,dateadd(day,-7,getdate()),101))\n" +
                    "and transactiondate<convert(datetime,convert(varchar,getdate(),101));\n" +
                    "");

ps.execute();
            ps.close();
            HibUtils.commit(HibernateSession.currentSession());

             ps = HibernateSession.getPreparedStatement("\n" +
                    "delete from owd_inventory_daily_movement_facility where transactiondate>=convert(datetime,convert(varchar,dateadd(day,-7,getdate()),101));\n" +
                    "\n" +
                    "insert into owd_inventory_daily_movement_facility (inventory_id,inventory_num,client_fkey,received,adjusted,returned,shipped,transactiondate,damaged,facility_code) select inventory_id,inventory_num,client_fkey,received,adjusted,returned,shipped,transactiondate,damaged,facility_code \n" +
                    "from vw_owd_inv_daily_movement_facility_test where transactiondate>=convert(datetime,convert(varchar,dateadd(day,-7,getdate()),101))\n" +
                    "and transactiondate<convert(datetime,convert(varchar,getdate(),101));\n" +
                    "");

            ps.execute();
            ps.close();
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {
              ex.printStackTrace();
            throw new LogableException(ex,"Error updating daily inventory movement tables","TS:"+ Calendar.getInstance().getTimeInMillis(), "55", "UpdateInventoryMovementsJob", LogableException.errorTypes.INTERNAL);
        } finally {
            HibernateSession.closeSession();
        }
    }


    public static void updateRecords(){
        LocalDate d = LocalDate.of(2019,1,1);
        LocalDate today = LocalDate.now();
        System.out.println(d);
        while(d.isBefore(today)){
            System.out.println(d);
            updateSpecificDay(d);
            d = d.plusDays(1);
        }
    }

    public static void updateSpecificDay(LocalDate d) {
        try {
            PreparedStatement ps = HibernateSession.getPreparedStatement("\n" +
                    "delete from owd_inventory_daily_movement where transactiondate='" + d + "';\n" +
                    "\n" +
                    "insert into owd_inventory_daily_movement (inventory_id,inventory_num,client_fkey,received,adjusted,returned,shipped,transactiondate,damaged)\n" +
                    "select inventory_id,inventory_num,client_fkey,received,adjusted,returned,shipped,transactiondate,damaged \n" +
                    "from vw_owd_inv_daily_movement_test where transactiondate='" + d + "'\n" +
                    ";\n" +
                    "");
            ps.execute();
            ps.close();
            HibUtils.commit(HibernateSession.currentSession());

            ps = HibernateSession.getPreparedStatement("\n" +
                    "delete from owd_inventory_daily_movement_facility where transactiondate='" + d + "';\n" +
                    "\n" +
                    "insert into owd_inventory_daily_movement_facility (inventory_id,inventory_num,client_fkey,received,adjusted,returned,shipped,transactiondate,damaged,facility_code) select inventory_id,inventory_num,client_fkey,received,adjusted,returned,shipped,transactiondate,damaged,facility_code \n" +
                    "from vw_owd_inv_daily_movement_facility_test where transactiondate='" + d + "'\n" +
                    ";\n" +
                    "");

            ps.execute();
            ps.close();
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new LogableException(ex,"Error updating daily inventory movement tables","TS:"+ Calendar.getInstance().getTimeInMillis(), "55", "UpdateInventoryMovementsJob", LogableException.errorTypes.INTERNAL);
        } finally {
            HibernateSession.closeSession();
        }
    }
}

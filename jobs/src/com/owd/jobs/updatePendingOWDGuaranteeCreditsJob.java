package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class updatePendingOWDGuaranteeCreditsJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }

    public void internalExecute() {

        try {

            PreparedStatement stmt = HibernateSession.getPreparedStatement("update owd_order_charges set charge_date=convert(datetime,convert(varchar,getdate(),101)), \n" +
                    "picks_qty=(case when boxes=1 then shipped_units else 0 end),\n" +
                    "packs_qty=(case when boxes=1 then ship_packs else 0 end),\n" +
                    "ship_bill_cost=(case when ship_actual_cost=1 then (-1.00*shipped_cost) else 0 end),\n" +
                    "boxes=0,\n" +
                    "ship_actual_cost=0.00\n" +
                    " from owd_order_charges oc join owd_order o on oc.order_fkey=order_id and owd_guarantee_flag=1 and charge_date is null and shipped_on is not null and\n" +
                    "(boxes>0 or ship_actual_cost>0) and oc.is_void=0 and o.is_void=0");

            log.debug("update owd_order_charges set charge_date=convert(datetime,convert(varchar,getdate(),101)), \n" +
                    "picks_qty=(case when boxes=1 then shipped_units else 0 end),\n" +
                    "packs_qty=(case when boxes=1 then ship_packs else 0 end),\n" +
                    "ship_bill_cost=(case when ship_actual_cost=1 then (-1.00*shipped_cost) else 0 end),\n" +
                    "boxes=0,\n" +
                    "ship_actual_cost=0.00\n" +
                    " from owd_order_charges oc join owd_order o on oc.order_fkey=order_id and owd_guarantee_flag=1 and charge_date is null and shipped_on is not null and\n" +
                    "(boxes>0 or ship_actual_cost>0) and oc.is_void=0 and o.is_void=0");
                    stmt.executeUpdate();



            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {
            try {
                HibUtils.rollback(HibernateSession.currentSession());
            } catch (Exception exx) {
                exx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {

            HibernateSession.closeSession();
        }
    }

}

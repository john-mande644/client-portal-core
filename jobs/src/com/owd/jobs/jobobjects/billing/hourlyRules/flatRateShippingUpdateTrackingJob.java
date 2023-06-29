package com.owd.jobs.jobobjects.billing.hourlyRules;

import com.owd.LogableException;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

/**
 * Created by danny on 9/24/2018.
 */
public class flatRateShippingUpdateTrackingJob extends OWDStatefulJob{

    private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args){
        run();
    }
    public void internalExecute(){

        log.debug("Starting FlatRate Tracking Billing update: ");

        String sql = "update owd_order_track set total_billed = dbo.udf_owd_service_level_flaterate_getPriceForTrackingId(dbo.owd_order_track.order_track_id) where order_track_id in (\n" +
                "\n" +
                "\n" +
                "SELECT\n" +
                " order_track_id\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order_track\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_track.order_fkey)\n" +
                "WHERE\n" +
                " dbo.owd_order_ship_info.carr_service_ref_num LIKE 'COM_OWD_FLATRATE%'\n" +
                "AND dbo.owd_order_track.is_void = 0 and total_billed <= .08\n" +
                ")\n" +
                ";";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            int i = q.executeUpdate();
            log.debug("FlatRate Billing Updated: "+i);
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }
        }catch (Exception e){
            e.printStackTrace();
            new LogableException(e,e.getMessage(),"Billing Job Error","55","Hourly Flat Rate Billing update", LogableException.errorTypes.INTERNAL);
        }





    }
}

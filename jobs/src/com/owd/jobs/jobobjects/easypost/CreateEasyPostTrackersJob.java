package com.owd.jobs.jobobjects.easypost;

import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by danny on 6/11/2018.
 */
public class CreateEasyPostTrackersJob extends OWDStatefulJob{
    private final static Logger log =  LogManager.getLogger();


    private String sql = "select order_track_id from owd_order_track WHERE ship_date >= '2020-03-01'  and carrier_code <> 'OWD.BPD' and external_id not like 'passport%' and tracking_id is null and created_by <> 'Manual' and is_void = 0 ";

    public static void main(String[] args){
        run();
    }


    public void internalExecute(){
        ExecutorService exec = Executors.newFixedThreadPool(4);
        try{

            Query q = HibernateSession.currentSession().createSQLQuery(sql);


            for(Object o: q.list()){
                exec.submit(new trackingCreateTask(Integer.parseInt(o.toString())));
                log.debug(Integer.parseInt(o.toString()));

            }
            exec.shutdown();
            log.debug("New mark");
            log.debug("Waiting Executor");
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            log.debug("done");


        }catch (Exception e){
            e.printStackTrace();

        }


    }
}

package com.owd.jobs;

import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.utilities.OWDShippingTask;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by danny on 9/18/2017.
 */
public class ShipWeighedOrdersJob extends OWDStatefulJob{


  public static void main(String[] args){


      run();
  }



    public void internalExecute(){
        try {

        ExecutorService exec = Executors.newFixedThreadPool(3);


         String sql = "execute dbo.sp_getPreWeightOrdersReadyToShip";
         Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List results = q.list();

            System.out.println("# of pre-weighed orders to ship " + results.size());
            for (Object row : results) {

                Map info = (Map) row;
               exec.submit(new OWDShippingTask(info.get("order_id").toString(),info.get("boxType").toString(),info.get("pre_ship_weight").toString()));


            }
            exec.shutdown();
            System.out.println("waiting executor");
            exec.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("done");


       }catch (Exception e){
            e.printStackTrace();
        }






    }


}

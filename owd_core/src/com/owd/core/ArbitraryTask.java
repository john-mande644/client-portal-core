package com.owd.core;

import com.owd.core.business.order.OrderStatus;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateClientReportsSession;
import com.owd.hibernate.HibernateFogbugzSession;
import com.owd.hibernate.HibernateSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by stewartbuskirk1 on 1/16/15.
 */

public class ArbitraryTask implements Runnable {
    private int orderId;
    private int qty;
    private int type;


    public static void main(String[] args) {
            try {
                //   OrderStatus.addLineItemToExistingOrder(5762282,345,"844923051028","",1,0,0);OrderUtilities.shipExistingOrder(new OrderStatus("5762282"));

                ExecutorService exec = Executors.newFixedThreadPool(10); // 5 threads
             //   exec.submit(new ArbitraryTask(9915981,1));
             //   exec.submit(new ArbitraryTask(9915980,1));
             //   exec.submit(new ArbitraryTask(9915979,1));

                exec.shutdown();
                exec.awaitTermination(3, TimeUnit.HOURS);


            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // HibernateSession.closeSession();
            }


    }
    public ArbitraryTask(int orderId, int qty) {
        this.orderId = orderId;
        this.qty = qty;
    }

    public void run() {
        try {
            System.out.println( "running ArbitraryTask "+orderId );
            OrderStatus.addLineItemToExistingOrder(orderId, 158, "720124", "", 1,0.00f, 0.00f);




        } catch (Exception ex) {
         //   log.debug("SYNCSKU EPIC FAIL!!!!");
            ex.printStackTrace();
        } finally {
            //log.debug("*** Unable to verify or correct: "+ex.getMessage()+" for order id "+id);
            try {
                HibUtils.rollback(HibernateSession.currentSession());
            } catch (Exception exx) {
                exx.printStackTrace();
            }
            HibernateSession.closeSession();
            HibernateFogbugzSession.closeSession();
            HibernateClientReportsSession.closeSession();
        }
    }

}

package com.owd.jobs.jobobjects

import com.owd.core.business.order.OrderStatus
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateClientReportsSession
import com.owd.hibernate.HibernateFogbugzSession
import com.owd.hibernate.HibernateSession
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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

                ExecutorService exec = Executors.newFixedThreadPool(5); // 5 threads

                new ArbitraryTask(9869521,1,1).run()
              //  exec.submit(new ArbitraryTask(9869521,1,1));
     /*           exec.submit(new ArbitraryTask(9870048,1,1));
                exec.submit(new ArbitraryTask(9870049,1,1));
                exec.submit(new ArbitraryTask(9870319,1,1));
                exec.submit(new ArbitraryTask(9870320,1,1));
                exec.submit(new ArbitraryTask(9870625,1,1));
                exec.submit(new ArbitraryTask(9870640,1,1));
                exec.submit(new ArbitraryTask(9870641,1,1));
                exec.submit(new ArbitraryTask(9870882,1,1));
                exec.submit(new ArbitraryTask(9870892,1,1));
                exec.submit(new ArbitraryTask(9871094,1,1));
                exec.submit(new ArbitraryTask(9872197,1,1));
                exec.submit(new ArbitraryTask(9872652,1,1));
                exec.submit(new ArbitraryTask(9873065,1,1));
                exec.submit(new ArbitraryTask(9873240,1,1));
                exec.submit(new ArbitraryTask(9873721,1,1));
                exec.submit(new ArbitraryTask(9873728,1,1));
                exec.submit(new ArbitraryTask(9874003,1,1));
                exec.submit(new ArbitraryTask(9874009,1,1));
                exec.submit(new ArbitraryTask(9874488,1,1));
                exec.submit(new ArbitraryTask(9874742,1,1));
                exec.submit(new ArbitraryTask(9874760,1,1));
                exec.submit(new ArbitraryTask(9875622,1,1));
                exec.submit(new ArbitraryTask(9875624,1,1));
                exec.submit(new ArbitraryTask(9876669,1,1));
                exec.submit(new ArbitraryTask(9876674,1,1));
                exec.submit(new ArbitraryTask(9877417,1,1));
                exec.submit(new ArbitraryTask(9877620,1,1));
                exec.submit(new ArbitraryTask(9878418,1,1));
                exec.submit(new ArbitraryTask(9879027,1,1));
                exec.submit(new ArbitraryTask(9879035,1,1));
                exec.submit(new ArbitraryTask(9879043,1,1));
                exec.submit(new ArbitraryTask(9879044,1,1));
                exec.submit(new ArbitraryTask(9879761,1,1));
                exec.submit(new ArbitraryTask(9879774,1,1));
                exec.submit(new ArbitraryTask(9879776,1,1));
                exec.submit(new ArbitraryTask(9880496,1,1));
                exec.submit(new ArbitraryTask(9880506,1,1));
                exec.submit(new ArbitraryTask(9880511,1,1));
                exec.submit(new ArbitraryTask(9880519,1,1));
                exec.submit(new ArbitraryTask(9880530,1,1));
                exec.submit(new ArbitraryTask(9880534,1,1));
                exec.submit(new ArbitraryTask(9880537,1,1));
                exec.submit(new ArbitraryTask(9880792,1,1));
                exec.submit(new ArbitraryTask(9880901,1,1));
                exec.submit(new ArbitraryTask(9880916,1,1));
                exec.submit(new ArbitraryTask(9880920,1,1));
                exec.submit(new ArbitraryTask(9882206,1,1));
                exec.submit(new ArbitraryTask(9883073,1,1));
                exec.submit(new ArbitraryTask(9884301,1,1));
                exec.submit(new ArbitraryTask(9884313,1,1));
                exec.submit(new ArbitraryTask(9885178,1,1));
                exec.submit(new ArbitraryTask(9885187,1,1));
                exec.submit(new ArbitraryTask(9886082,1,1));
                exec.submit(new ArbitraryTask(9887137,1,1));
                exec.submit(new ArbitraryTask(9887148,1,1));
                exec.submit(new ArbitraryTask(9887150,1,1));
                exec.submit(new ArbitraryTask(9887167,1,1));
                exec.submit(new ArbitraryTask(9887170,1,1));
                exec.submit(new ArbitraryTask(9887196,1,1));
                exec.submit(new ArbitraryTask(9888079,1,1));
                exec.submit(new ArbitraryTask(9888092,1,1));
                exec.submit(new ArbitraryTask(9888101,1,1));
                exec.submit(new ArbitraryTask(9888129,1,1));
                exec.submit(new ArbitraryTask(9888135,1,1));
                exec.submit(new ArbitraryTask(9888138,1,1));
                exec.submit(new ArbitraryTask(9888140,1,1));
                exec.submit(new ArbitraryTask(9888146,1,1));
                exec.submit(new ArbitraryTask(9888148,1,1));
                exec.submit(new ArbitraryTask(9888155,1,1));
                exec.submit(new ArbitraryTask(9889192,1,1));
                exec.submit(new ArbitraryTask(9889202,1,1));
                exec.submit(new ArbitraryTask(9889210,2,1));
                exec.submit(new ArbitraryTask(9889211,1,1));
                exec.submit(new ArbitraryTask(9889214,1,1));
                exec.submit(new ArbitraryTask(9889228,1,1));*/
                exec.shutdown();
                exec.awaitTermination(3, TimeUnit.HOURS);


            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // HibernateSession.closeSession();
            }


    }
    public ArbitraryTask(int orderId, int qty, int type) {
        this.orderId = orderId;
        this.qty = qty;
        this.type=type
    }

    public void run() {
        try {
            println "running ArbitraryTask "+orderId+" "+type
            if(type==1)
            {
                println "found type="+type
                tempadd(orderId,qty);
                println "done type="+type
            }  else if (type==3) {
                tempadd3(orderId,qty);
            }                         else if (type==4) {
                tempadd4(orderId,qty)
            }



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

    public static void tempadd3(int orderID, int boxQty) throws Exception {
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "DSMU-0914-X714-BLK-0", "", 3 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "DSMU-0914-X714-WHT-0", "", 3 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "U0315-H101-BK-0", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0315-H121-BK-0", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0615-H101-TL-0", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0215-T113-BW-3", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0315-T132-BK-3", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "U0315-J200-BK-3", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0315-X100-GY-0", "", 1 * boxQty, 0.00f, 0.00f);
    }

    public static void tempadd4(int orderID, int boxQty) throws Exception {
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "DSMU-0914-X714-BLK-0", "", 3 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "DSMU-0914-X714-WHT-0", "", 3 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "U0315-H101-BK-0", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0315-H121-BK-0", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0215-T113-BW-4", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0215-T111-BKR-4", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0615-T101-WT-4", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0615-T110-RS-4", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0415-P653-BY-4", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0914-J200-BU-4", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0415-T447-BK-4", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0215-X100-WT-0", "", 1 * boxQty, 0.00f, 0.00f);
    }

    public  void tempadd(int orderID, int boxQty) throws Exception {
        println "in tempadd"
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "DSMU-0914-X714-BLK-0", "", 3 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "DSMU-0914-X714-WHT-0", "", 3 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "U0315-H101-BK-0", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0315-H121-BK-0", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0815-H105-TRG-0", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0215-T113-BW-5", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0215-T112-BW-5", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0615-T110-RS-5", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0215-T479-BK-5", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0415-P653-BY-5", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0914-J200-BU-5", "", 1 * boxQty, 0.00f, 0.00f);
        OrderStatus.addLineItemToExistingOrder(orderID, 551, "D0415-X100-YL-0", "", 1 * boxQty, 0.00f, 0.00f);
        println "done tempadd"
    }
}

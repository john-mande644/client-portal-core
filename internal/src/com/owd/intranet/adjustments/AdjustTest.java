package com.owd.intranet.adjustments;

import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryHistory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import junit.framework.TestCase;
import com.owd.hibernate.*;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.Connection;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 24, 2009
 * Time: 1:43:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdjustTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

    Session sess = null;

    public AdjustTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

        sess = HibernateSession.currentSession();


    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {
        HibernateSession.closeSession();
    }

    public void testSimultaneousAdjustments() throws Exception {

        OwdInventory i = (OwdInventory) sess.load(OwdInventory.class, 35392);
InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(i.getInventoryId(), i.getOwdClient().getClientId(), 100, OWDUtilities.getSQLDateNoTimeForToday(), "TEST" + Calendar.getInstance().getTimeInMillis(), "DC1", HibernateSession.currentSession());
        HibUtils.commit(sess);
        sess.evict(i.getOwdInventoryOh());
        sess.evict(i);

        adjustThread t = new adjustThread();
        t.threadName=1;
        adjustThread t2 = new adjustThread();
         t2.threadName=2;
        adjustThread t3 = new adjustThread();
         t3.threadName=3;
        adjustThread t4 = new adjustThread();
         t4.threadName=4;
        t.start();
        t2.start();
        t3.start();
        t4.start();

        while((t.done+t2.done+t3.done+t4.done)<4)
        {

        }
    }


    public class adjustThread implements Runnable {

        int done=0;
        int threadName=0;

        public void start() {
            Thread t = new Thread(this, "New thread");
            t.start();

        }

        public void run() {

            try {

                Session sess = HibernateSession.currentSession();
                 ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                log.debug("got session");
                int adjust = 1;

                for (int k = 0; k < 1000; k++) {

                  //  log.debug("in loop");
                    OwdInventory i = (OwdInventory) sess.load(OwdInventory.class, 35392);
                 //   log.debug("loaded "+i);
                 //   log.debug(i);

                    //old code
                  /*  OwdInventoryOh invOh = i.getOwdInventoryOh();
                    log.debug("got oh "+invOh.getQtyOnHand());
                    if ((invOh.getQtyOnHand().intValue() + adjust) < 0) {
                        throw new Exception("Can Not Adjust Inventory below 0 for ");
                    }
                    log.debug("setting qty");
                    invOh.setQtyOnHand(new Integer(invOh.getQtyOnHand().intValue() + adjust));*/

                    //new code
                    OwdInventoryHistory invOh = new OwdInventoryHistory();
                    invOh.setInventoryFkey(i.getInventoryId());
                    invOh.setQtyChange(adjust);
                    invOh.setNote("test");
                    invOh.setFacility(FacilitiesManager.getFacilityForCode("DC1"));

                    sess.save(invOh);
                           HibUtils.commit(sess);
                    sess.evict(i);
                    sess.evict(invOh);
                    if (adjust == 1) {
                     //   adjust = -1;
                    } else {
                        adjust = 1;
                    }
                  //  log.debug(threadName+":"+k);
                }

                done=1;
                } catch (Throwable ext) {
                ext.printStackTrace();
            }
        }
    }

}

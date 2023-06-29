package com.owd.core.business.autoship;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderAuto;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 29, 2007
 * Time: 10:36:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class AutoShipTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

     Session sess = null;

      public AutoShipTest(String test) {
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

        // HibernateSession.closeSession();
    }

    public static TestSuite suite()
        {
            TestSuite suite = new TestSuite(AutoShipTest.class);
            return suite;
        }



    public  void testLoadSubscription() throws Exception {

        OwdOrderAuto auto = (OwdOrderAuto)
                sess.load(OwdOrderAuto.class,new Integer(3042));

        log.debug("Name:"+auto.getShipName());
        log.debug("Message: "+AutoShipManager.shipSubscriptionOrder(auto));
        HibUtils.commit(HibernateSession.currentSession());


    }


}

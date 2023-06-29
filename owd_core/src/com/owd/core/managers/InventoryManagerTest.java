package com.owd.core.managers;


import com.owd.hibernate.HibernateSession;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 27, 2007
 * Time: 10:00:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryManagerTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

     Session sess = null;

      public InventoryManagerTest(String test) {
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
            TestSuite suite = new TestSuite(InventoryManagerTest.class);
            return suite;
        }


      public  void testCreateLocationWithNoStock() throws Exception {
/*
          WLocation loc = new WLocation(8, "test Location", 10);
          HibernateSession.currentSession().save(loc);
          OwdInventory item = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, 12341);
          assert (item.getOwdInventoryOh().getQtyOnHand() > 10);

          WLocInventory stock = new WLocInventory();
          stock.setLocation(loc);
          stock.setInventory(item);
          stock.setQuantity(5);
          HibernateSession.currentSession().save(stock);

          HibernateSession.currentSession().evict(stock);
          HibernateSession.currentSession().evict(loc);
          HibernateSession.currentSession().evict(item);

          item = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, 12341);

          assert (item.getStockLocations().size() == 1);
          assert (((WLocInventory[]) (item.getStockLocations().toArray()))[0].getLocation().getLocationName().equals("test Location"));
          assert (((WLocInventory[]) (item.getStockLocations().toArray()))[0].getQuantity() == 5);*/
      }


}

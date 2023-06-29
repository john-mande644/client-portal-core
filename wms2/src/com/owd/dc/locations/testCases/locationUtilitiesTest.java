package com.owd.dc.locations.testCases;

import com.owd.dc.locations.locationUtilities;
import com.owd.hibernate.HibernateSession;
import junit.framework.TestCase;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Sep 17, 2008
 * Time: 2:06:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationUtilitiesTest extends TestCase {

    public void setUp() {


    }

    //  @After
    public void tearDown() {

    }

    //  @Test
    public void testValidParentWithLocationTypes() {

        try {
            Session sess = HibernateSession.currentSession();
            assertTrue(com.owd.dc.locations.locationUtilities.isValidParent(sess, "2", "1"));
            assertTrue(locationUtilities.isValidParent(sess, "3", "2"));
            assertTrue(com.owd.dc.locations.locationUtilities.isValidParent(sess, "4", "3"));
            assertTrue(com.owd.dc.locations.locationUtilities.isValidParent(sess, "8", "7"));
            assertTrue(com.owd.dc.locations.locationUtilities.isValidParent(sess, "10", "14"));
            assertTrue(com.owd.dc.locations.locationUtilities.isValidParent(sess, "16", "9"));
            assertFalse(com.owd.dc.locations.locationUtilities.isValidParent(sess, "8", "12"));
            assertFalse(com.owd.dc.locations.locationUtilities.isValidParent(sess, "2", "7"));
            assertTrue(com.owd.dc.locations.locationUtilities.isValidParent(sess, "14", "17"));


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void testValidAParentWithParentId() {
        try {
            Session sess = HibernateSession.currentSession();
            assertTrue(com.owd.dc.locations.locationUtilities.isValidParent(sess, "8", 24386));
            assertFalse(com.owd.dc.locations.locationUtilities.isValidParent(sess, "1", 24386));


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void testHasInventoryGreaterThanZero() {
        try {
            Session sess = HibernateSession.currentSession();
            assertTrue(com.owd.dc.locations.locationUtilities.hasInventoryGreaterThanZero(sess, "24386"));
            assertFalse(com.owd.dc.locations.locationUtilities.hasInventoryGreaterThanZero(sess, 26061));


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void testgetPackageOrderId() {
            /*  try{
            assertTrue(packageBarcodeLookup.getPackageOrderId(goodBarcode).equals("2467"));
            assertTrue(packageBarcodeLookup.getPackageOrderId(orderNum).equals("2467"));
                    assertTrue(packageBarcodeLookup.getPackageOrderId(orderNumR).equals("2467"));
                    assertTrue(packageBarcodeLookup.getPackageOrderId("1").equals("2467"));
              }catch (Exception e){
                assertTrue(e.getMessage().contains("No package ID found for"));
              }*/
    }


}

package com.owd.dc.locations.testCases;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Sep 23, 2008
 * Time: 10:44:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class removeLocationTest extends TestCase {

    public void setUp() {


    }

    //  @After
    public void tearDown() {

    }

    //  @Test
    public void testIsRemovable() {

        try {

            assertFalse(com.owd.dc.locations.removeLocation.isRemovable("24386"));
            assertTrue(com.owd.dc.locations.removeLocation.isRemovable("26061"));


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
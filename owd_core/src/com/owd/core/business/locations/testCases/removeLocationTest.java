package com.owd.core.business.locations.testCases;

import com.owd.core.business.locations.removeLocation;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Sep 23, 2008
 * Time: 10:44:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class removeLocationTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

    public void setUp() {


            }

          //  @After
            public void tearDown() {

            }

          //  @Test
     public void testIsRemovable(){

              try {

              assertFalse(removeLocation.isRemovable("24386"));
                  assertTrue(removeLocation.isRemovable("26061"));



             } catch (Exception e) {
                  e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
              }
          }


           public void testgetPackageOrderId(){
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

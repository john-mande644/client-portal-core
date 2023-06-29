package com.owd.core.managers;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 9/19/13
 * Time: 8:58 AM
 * To change this template use File | Settings | File Templates.
 */

import com.owd.core.Mailer;
import com.owd.core.business.order.OrderStatus;
import groovy.util.GroovyTestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 8/30/13
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataSourceManagerTest extends GroovyTestCase {
private final static Logger log =  LogManager.getLogger();


    @Test
    public void testSetMasterEnvironment() throws Exception {

        log.debug("testSetMasterEnvironment");

        DataSourceManager.setMasterEnvironment("production");

        assertTrue("production".equals(DataSourceManager.getMasterEnvironment()));


        DataSourceManager.setMasterEnvironment("test");

        assertTrue("test".equals(DataSourceManager.getMasterEnvironment()));


        DataSourceManager dataSourceManager = DataSourceManager.getDataSource("OWD");

        assertNotNull(dataSourceManager);

        log.debug("DataSourceManager found: " + dataSourceManager.getDescription());

        assertTrue("test".equals(dataSourceManager.getEnvironment()));


        dataSourceManager = DataSourceManager.getDataSource("OWD");

        assertTrue("test".equals(dataSourceManager.getEnvironment()));

    }


    @Test
    public void testGetMail() throws Exception {
        log.debug("testGetMail");

        try {
            Mailer.sendMail("Test", "JUnit test message: Testing mailer server property.", "jholtman@owd.com");

        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            fail("Error testGetMail");
        }
    }


    @Test
    public void testGetInstance() throws Exception {

        log.debug("testGetInstance");

        DataSourceManager dataSourceManager = DataSourceManager.getDataSource("OWD");

        assertNotNull(dataSourceManager);

        log.debug("DataSourceManager found: " + dataSourceManager.getDescription());

        log.debug("Dialect: " + dataSourceManager.getDialect());

        assertTrue(dataSourceManager.getDialect().equals("org.hibernate.dialect.SQLServerDialect"));


    }

    @Test
    public void testVerifyInventory() throws Exception {

        log.debug("testVerifyInventory");

        OrderStatus orderStatus = new OrderStatus("6284374");
        log.debug("Order_id ====>>> " + orderStatus.order_id);
        log.debug("===>>>: " + orderStatus.getStatusString());

        assertNotNull(orderStatus.getStatusString());
    }

    @Test
    public void testOwdConnection() {
        log.debug("testOwdConnection");

        Connection connection = null;

        try {
            connection = ConnectionManager.getSpecifiedConnection("OWD");

            assertNotNull(connection);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed creating OWD connection.");

        } finally {
            try {
                if (connection != null) {
                    ConnectionManager.freeConnection(connection);
                }
            } catch (Exception ex) {
            }
        }
    }

    @Test
    public void testAdhocConnection() {
        log.debug("testAdhocConnection");

        Connection connection = null;

        try {
            connection = ConnectionManager.getSpecifiedConnection("Adhoc");

            assertNotNull(connection);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed creating Adhoc connection.");

        } finally {
            try {
                if (connection != null) {
                    ConnectionManager.freeConnection(connection);
                }
            } catch (Exception ex) {
            }
        }
    }


    @Test
    public void testAQAudioConnection() {
        log.debug("testAQAudioConnection");

        Connection connection = null;

        try {
            connection = ConnectionManager.getSpecifiedConnection("AQAudio");

            assertNotNull(connection);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed creating AQAudio connection.");

        } finally {
            try {
                if (connection != null) {
                    ConnectionManager.freeConnection(connection);
                }
            } catch (Exception ex) {
            }
        }
    }

    @Test
    public void testClientReportsConnection() {
        log.debug("testClientReportsConnection");

        Connection connection = null;

        try {
            connection = ConnectionManager.getSpecifiedConnection("ClientReports");

            assertNotNull(connection);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed creating ClientReports connection.");

        } finally {
            try {
                if (connection != null) {
                    ConnectionManager.freeConnection(connection);
                }
            } catch (Exception ex) {
            }
        }
    }

    @Test
    public void testFogBugzConnection() {
        log.debug("testFogBugzConnection");

        Connection connection = null;

        try {
            connection = ConnectionManager.getSpecifiedConnection("FogBugz");

            assertNotNull(connection);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed creating FogBugz connection.");

        } finally {
            try {
                if (connection != null) {
                    ConnectionManager.freeConnection(connection);
                }
            } catch (Exception ex) {
            }
        }
    }

    @Test
    public void testTimeForceConnection() {
        log.debug("testTimeForceConnection");

        Connection connection = null;

        try {
            connection = ConnectionManager.getSpecifiedConnection("TimeForce");

            assertNotNull(connection);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed creating TimeForce connection.");

        } finally {
            try {
                if (connection != null) {
                    ConnectionManager.freeConnection(connection);
                }
            } catch (Exception ex) {
            }
        }
    }


//      TODO: OWDBackup
//    @Test
//    public void testOWDBackupConnection() {
//        log.debug("testOWDBackupConnection");
//
//        Connection connection = null;
//
//        try {
//            connection = ConnectionManager.getSpecifiedConnection(ConnectionManager.owdDriverClass,
//                    "jdbc:"+ ConnectionManager.owdLoggingDriver+"jtds:sqlserver://172.16.2.254/OWDBACKUP","sa","wahoo",
//                    "test-hibernate", "SELECT COUNT(*) FROM OWD.dbo.pooltester");
//
//            connection = ConnectionManager.getSpecifiedConnection("OWDBackup");
//
//            assertNotNull(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Failed creating OWDBackup connection.");
//
//        } finally {
//            try {
//                if (connection != null) {
//                    ConnectionManager.freeConnection(connection);
//                }
//            } catch (Exception ex) {
//            }
//        }
//    }

//    TODO: Galapagos
//    @Test
//    public void testGalapagosConnection() {
//        log.debug("testGalapagosConnection");
//
//        Connection connection = null;
//
//        try {
//            connection =ConnectionManager.getSpecifiedConnection(ConnectionManager.owdDriverClass,
//                    "jdbc:"+ ConnectionManager.owdLoggingDriver+"jtds:sqlserver://64.49.216.240:1433;DatabaseName=4659",
//                    "xgVv3oD4YH3D","cxwtgfsYcg52","galapagoswebcart-hibernate","SELECT COUNT(*) FROM Flags");
//
//            assertNotNull(connection);
//
//
//            connection = ConnectionManager.getSpecifiedConnection("Galapagos");
//
//            assertNotNull(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Failed creating Galapagos connection.");
//
//        } finally {
//            try {
//                if (connection != null) {
//                    ConnectionManager.freeConnection(connection);
//                }
//            } catch (Exception ex) {
//            }
//        }
//    }

//    TODO:    Dacor
//    @Test
//    public void testDacorStoreConnection() {
//        log.debug("testDacorStoreConnection");
//
//        Connection connection = null;
//
//        try {
//            connection = ConnectionManager.getSpecifiedConnection("com.mysql.jdbc.Driver",
//                    "jdbc:mysql://www.thedacorstore.com:3306/thedacorstore_mm5","thedacorstore_mm","TnM9hq","DacorStore-Miva DB", "SELECT COUNT(*) FROM Modules");
//
//            assertNotNull(connection);
//
//            connection = ConnectionManager.getSpecifiedConnection("DacorStore");
//
//            assertNotNull(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Failed creating Adhoc connection.");
//
//        } finally {
//            try {
//                if (connection != null) {
//                    ConnectionManager.freeConnection(connection);
//                }
//            } catch (Exception ex) {
//            }
//        }
//    }

//    TODO:     Dogeared
//    @Test
//    public void testDogearedConnection() {
//        log.debug("testDogearedConnection");
//
//        Connection connection = null;
//
//        try {
//            connection = ConnectionManager.getSpecifiedConnection("Dogeared");
//
//            assertNotNull(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Failed creating Adhoc connection.");
//
//        } finally {
//            try {
//                if (connection != null) {
//                    ConnectionManager.freeConnection(connection);
//                }
//            } catch (Exception ex) {
//            }
//        }
//    }

//      TODO: Beerbelly
//    @Test
//    public void testBeerBellyConnection() {
//        log.debug("testBeerBellyConnection");
//
//        Connection connection = null;
//
//        try {
//            connection = ConnectionManager.getSpecifiedConnection("BeerBelly");
//
//            assertNotNull(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Failed creating BeerBelly connection.");
//
//        } finally {
//            try {
//                if (connection != null) {
//                    ConnectionManager.freeConnection(connection);
//                }
//            } catch (Exception ex) {
//            }
//        }
//    }

//    TODO: Billing
//    @Test
//    public void testBillingConnection() {
//        log.debug("testBillingConnection");
//
//        Connection connection = null;
//
//        try {
//            connection = ConnectionManager.getSpecifiedConnection("Billing");
//
//            assertNotNull(connection);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Failed creating Billing connection.");
//
//        } finally {
//            try {
//                if (connection != null) {
//                    ConnectionManager.freeConnection(connection);
//                }
//            } catch (Exception ex) {
//            }
//        }
//    }

}

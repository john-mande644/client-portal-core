package com.owd.web.auth;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 1, 2004
 * Time: 10:20:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class LogiXMLAuthUtilitiesTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

    public LogiXMLAuthUtilitiesTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {
        log.debug("testSetup");


    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {


    }

    public void testParseAuthString() throws Exception {
        log.debug(LogiXMLAuthUtilities.getLogiXMLResponseFromLogiXMLAuthString(OWDUtilities.getLogiXMLAuthString("sbuskirk", "skalarno", "0")));
        log.debug(LogiXMLAuthUtilities.getLogiXMLResponseFromLogiXMLAuthString("+6z51aIh358BBsl2wdhmrAjZYHcD7SiAMAgOdH57oX7GbAM="));

    }

    public void testAddUser() {
        try {
            //    assertEquals(false,LogiXMLAuthUtilities.isUserInAdHocDB("testclientname"));
            LogiXMLAuthUtilities.addOrUpdateUserToAdHocDB("testclientname", "testclientpassword");
            LogiXMLAuthUtilities.addOrUpdateUserToAdHocDB("testclientname", "testclientpassword2");
            assertEquals(true, LogiXMLAuthUtilities.isUserInAdHocDB("testclientname"));

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    public void testUserExistenceCheck() {
        try {
            assertEquals(true, LogiXMLAuthUtilities.isUserInAdHocDB("admin"));

            assertEquals(false, LogiXMLAuthUtilities.isUserInAdHocDB("admivn"));
            assertEquals(false, LogiXMLAuthUtilities.isUserInAdHocDB(null));

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }


}

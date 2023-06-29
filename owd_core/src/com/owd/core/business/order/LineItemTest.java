package com.owd.core.business.order;

import groovy.util.GroovyTestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 8/30/13
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class LineItemTest extends GroovyTestCase {
private final static Logger log =  LogManager.getLogger();


    public void testResultManager() throws Exception {

        log.debug("testVerifyInventory");

        OrderStatus orderStatus = new OrderStatus("6284374");
        log.debug("Order_id ====>>> " + orderStatus.order_id);
        log.debug("===>>>: " + orderStatus.getStatusString());

        assertFalse(orderStatus.getStatusString() == null);

        try {
            orderStatus.addLineItemToExistingOrder(6284374, 112, "104-80-4410", "Test", 1, 12.95f, 0);
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            fail("Error adding line item.");
        }
    }


    @Test
    public void testVerifyInventory() throws Exception {

        log.debug("testVerifyInventory");

        OrderStatus orderStatus = new OrderStatus("6284374");
        log.debug("Order_id ====>>> " + orderStatus.order_id);
        log.debug("===>>>: " + orderStatus.getStatusString());

        assertFalse(orderStatus.getStatusString() == null);

        int lineCount = orderStatus.getLineCount();

        try {
            orderStatus.addLineItemToExistingOrder(6284374, 112, "12'2", "Test", 1, 12.95f, 0);
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            assertTrue(e.getLocalizedMessage().equals("Inventory SKU 12'2 is not valid"));
        }

        assertTrue(lineCount == orderStatus.getLineCount());

        try {
            orderStatus.addLineItemToExistingOrder(6284374, 112, "104-80-4410", "Test", 1, 12.95f, 0);
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            fail("Error adding line item.");
        }
    }

    @Test
    public void testGetDescriptionForSKU() throws Exception {

        log.debug("testGetDescriptionForSKU");

        try {
            String ret = LineItem.getDescriptionForSKU("12'2", "112");
            assertTrue("".equals(ret));
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            assertTrue(e.getLocalizedMessage().equals("Inventory SKU 12'2 is not valid"));
        }

        try {
            String ret = LineItem.getDescriptionForSKU("104-80-4410", "112");
            assertFalse("".equals(ret));
            log.debug(ret);
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            fail("Error adding line item.");
        }
    }

    @Test
    public void testGetBarcodeForSKU() throws Exception {

        log.debug("testGetBarcodeForSKU");

        try {
            String ret = LineItem.getBarcodeForSKU("12'2", "112");

            assertNull(ret);

        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            assertTrue(e.getLocalizedMessage().equals("Inventory SKU 12'2 is not valid"));
        }

        try {
            String ret = LineItem.getBarcodeForSKU("104-80-4410", "112");
            assertFalse(ret == null);
            log.debug(ret);
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage());
            fail("Error adding line item.");
        }
    }
}

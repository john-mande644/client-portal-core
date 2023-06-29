package com.owd.core.ruleAdapters;

import com.owd.core.managers.KnowledgeBaseManager;
import groovy.util.GroovyTestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 12/3/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderRuleAdapterTest extends GroovyTestCase {
private final static Logger log =  LogManager.getLogger();

    public OrderRuleAdapterTest() {
        log.debug("OrderRuleAdapterTest");

        try {
//            testVHIOrderPackage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void testVHIOrderPackage () {

        boolean success = false;

        assertFalse(success);

        try {
            log.debug("vHIOrderPackageTest");

            OrderRuleAdapterTestImpl orderRuleAdapterImpl = new OrderRuleAdapterTestImpl();

            orderRuleAdapterImpl.setRecurrenceCount(4);
            KnowledgeBaseManager.executeRule(orderRuleAdapterImpl, "OrderInsert");

//            orderRuleAdapterImpl = new OrderRuleAdapterTestImpl();

//            orderRuleAdapterImpl.setRecurrenceCount(1);
//            KnowledgeBaseManager.executeRule(orderRuleAdapterImpl, "OrderInsert");
            success = true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        assertTrue(success);
    }

}

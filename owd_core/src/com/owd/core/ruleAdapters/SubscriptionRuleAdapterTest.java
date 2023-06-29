package com.owd.core.ruleAdapters;

import com.owd.core.Mailer;
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
public class SubscriptionRuleAdapterTest extends GroovyTestCase {
private final static Logger log =  LogManager.getLogger();

    public SubscriptionRuleAdapterTest() {
        log.debug("SubscriptionRuleAdapterTest");

        try {
            testSubscriptionPackage();
            testGetMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void testSubscriptionPackage () {

        boolean success = false;

        assertFalse(success);

        try {
            log.debug("SubscriptionPackageTest");

            SubscriptionRuleAdapterTestImpl subscriptionRuleAdapterTestImpl = new SubscriptionRuleAdapterTestImpl();

            KnowledgeBaseManager.executeRule(subscriptionRuleAdapterTestImpl, "LifeSpanSubscriptionPackage");

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(success);
    }


    @Test
    public void testGetMail() throws Exception {
        log.debug("Executing OrderRuleAdapterTest");

        try {
            Mailer.sendMail("Test", "JUnit test message: Testing OrderRuleAdapterTest.", "jholtman@owd.com");

        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            fail("Error testGetMail");
        }
    }
}

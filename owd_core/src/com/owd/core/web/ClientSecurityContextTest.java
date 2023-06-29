package com.owd.core.web;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jun 20, 2008
 * Time: 8:35:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientSecurityContextTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

    public ClientSecurityContextTest(String testName)
	{
		super(testName);
	}

	public static Test suite()
	{
		TestSuite suite = new TestSuite(ClientSecurityContextTest.class);
		return suite;
	}

    public static void testMonthMap()
    {
        log.debug(new ClientSecurityContext().getCcMonthMap());
    }

    public static void testYearMap()
    {
        log.debug(new ClientSecurityContext().getCcYearMap());
    }
}

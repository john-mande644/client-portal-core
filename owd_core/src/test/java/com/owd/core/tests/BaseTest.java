package com.owd.core.tests;

import com.owd.hibernate.HibernateSession;
import org.junit.After;
import org.junit.Before;

public class BaseTest {

    @Before
    public  void setUp()
    {
        System.setProperty("com.owd.environment", "dev");
        //To prevent bugs from previously executed tests without closing session
        HibernateSession.closeSession();
    }

    @After
    public  void tearDown()
    {
        //To prevent bugs for the next tests
        HibernateSession.closeSession();
    }
}

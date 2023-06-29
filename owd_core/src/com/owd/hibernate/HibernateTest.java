package com.owd.hibernate;

import com.owd.hibernate.clientreports.generated.CcClContact;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 10/13/13
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

    public HibernateTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {

    }

    public static TestSuite suite()
    {
        TestSuite suite = new TestSuite(HibernateTest.class);
        return suite;
    }



    public  void testHibernateSessionMappingConfig() throws Exception {

        Session owd = HibernateSession.currentSession();
        Session adhoc = HibernateClientReportsSession.currentSession();

        CcClContact c = new CcClContact();

        c.setContactId("test");
        c.setIsInternal(0);
        c.setNotes("");
        adhoc.save(c);
        log.debug(c.getId());

        try{
        owd.save(c);
        }catch(Exception ex)
        {
            assert(ex.getMessage().contains("Unknown entity"));
        }

    }



    }


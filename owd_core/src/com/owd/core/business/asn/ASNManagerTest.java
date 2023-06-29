package com.owd.core.business.asn;

import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.Receive;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 18, 2004
 * Time: 8:57:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ASNManagerTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

    public ASNManagerTest(String test) {
        super(test);
    }
     protected void setUp() throws Exception {
                     //log.debug("testSetup");

         Session sess = HibernateSession.currentSession();

           try{

                       //    sess.delete("from ReceiveItem");

                        //   sess.delete("from Receive");

                        //   sess.delete("from AsnItem");

                        //   sess.delete("from Asn");

                           //log.debug("Delete Done");


          //  sess.delete("from ReceiveItem");

          //  sess.delete("from Receive");

          //  sess.delete("from AsnItem");
          //       sess.delete("from Asn");



            sess.flush();
          HibUtils.commit(sess);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            // HibernateSession.closeSession();
        }

     }

     protected void tearDown() throws Exception {



    }

    public static Test suite()
		{
			TestSuite suite = new TestSuite(ASNManagerTest.class);
			return suite;
		}
    public void testCreatePendingReceive()
    {
        try
        {
            //log.debug("getting new rcv");
        Receive rcv= ASNFactory.getNewPendingReceive(
                (Asn)HibernateSession.currentSession().load(Asn.class,new Integer("177")));
            //log.debug("saving rcv");
         ASNManager.savePendingReceive(HibernateSession.currentSession(),rcv);
            //log.debug("committing changes");
         HibernateSession.currentSession().flush();
           HibUtils.commit(HibernateSession.currentSession());

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

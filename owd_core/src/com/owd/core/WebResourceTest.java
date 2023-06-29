package com.owd.core;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 26, 2007
 * Time: 2:42:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebResourceTest extends TestCase {
private final static Logger log =  LogManager.getLogger();
    public WebResourceTest(String test) {
           super(test);
       }

          /**
        * The fixture set up called before every test method.
        */
       protected void setUp() throws Exception {

          // sess = HibernateSession.currentSession();
       }

       /**
        * The fixture clean up called after every test method.
        */
       protected void tearDown() throws Exception {

          // // HibernateSession.closeSession();
       }

       public static TestSuite suite()
           {
               TestSuite suite = new TestSuite(WebResourceTest.class);
               return suite;
           }



       public  void testSimpleFitnessSecureURL() throws Exception {


       //    WebResource server = new WebResource("https://www.linkpointcart.net/orders/143737/OnlineOrder_26_12_2007.db",WebResource.kGETMethod,"143737","sfitss1");

           //log.debug(OWDUtilities.parseISToString(server.getResourceAsInputStream(false)));
           
           OwdOrder passwdOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,new Integer(2250439));

                         WebResource cookiePage = new WebResource("https://www.linkpointcart.net/orders/143737/OnlineOrder_26_12_2007.db",WebResource.kPOSTMethod);
                          cookiePage.addParameter("myUsername","143737");
                          cookiePage.addParameter("myPassword",passwdOrder.getBillAddressTwo().trim());
                          cookiePage.addParameter("Login","Login");
           log.debug("***Response***");
                          log.debug(OWDUtilities.parseISToString(cookiePage.getResourceAsInputStream(false)));
                          String cookie = cookiePage.getCookie();
                          log.debug("Cookie="+cookie);


       //    WebResource server = new WebResource("https://www.linkpointcart.net/orders/143737/OnlineOrder_26_12_2007.db",WebResource.kGETMethod,"143737","sfitss1");

           //log.debug(OWDUtilities.parseISToString(server.getResourceAsInputStream(false)));
       }
}

package com.owd.test.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.web.api.APIResponse;
import com.owd.web.api.SubscriptionStatusResponse;
import junit.framework.TestCase;
import org.hibernate.Session;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.ChoiceListManager;
import com.owd.core.CountryNames;
import com.owd.core.OWDUtilities;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 7, 2008
 * Time: 1:47:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionStatusTest extends TestCase {
private final static Logger log =  LogManager.getLogger();


    Session sess = null;


    public SubscriptionStatusTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

        sess = HibernateSession.currentSession();


    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {
        HibernateSession.closeSession();
    }

      public void testAllSubStatusResponse() throws Exception {

          SubscriptionStatusResponse ssr = new SubscriptionStatusResponse(1.0);
          ssr.setSubscriptionList(HibernateSession.currentSession().createQuery("from OwdOrderAuto where status=0 and clientFkey=320").setMaxResults(100).list());
          log.debug(ssr.getXML());

      }

     public void testByDateSubStatusResponse() throws Exception {

          String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.9\" " +
                  "client_authorization=\""+ OWDUtilities.encryptData("320") +"\" client_id=\"320\" testing=\"TRUE\">" +
                  "<OWD_SUBSCRIPTION_STATUS_REQUEST>" +
                 "<FILTER type=\"hello\" ><TYPE>daysSinceCreated</TYPE><VALUE>30</VALUE></FILTER>" +
                  "</OWD_SUBSCRIPTION_STATUS_REQUEST>" +
                  "</OWD_API_REQUEST>";

         SubscriptionStatusResponse ssr = (SubscriptionStatusResponse) subStatusAPI(xmlString);
          log.debug(ssr.getXML());

      }

    static APIResponse subStatusAPI(String request) throws Exception
    {
        log.debug("xmlString = " + request);

                org.w3c.dom.Document doc = null;

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setNamespaceAware(false);
                    factory.setValidating(false);
                    javax.xml.parsers.DocumentBuilder  builder = factory.newDocumentBuilder();

                    doc = builder.parse(new java.io.ByteArrayInputStream(request.getBytes()));

                    return com.owd.web.api.APIRequest.handle(doc);




    }
}

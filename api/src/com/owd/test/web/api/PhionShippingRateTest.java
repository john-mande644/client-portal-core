package com.owd.test.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.web.api.APIResponse;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 28, 2010
 * Time: 11:02:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhionShippingRateTest extends TestCase
{
private final static Logger log =  LogManager.getLogger();


    Session sess = null;


    public PhionShippingRateTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

//        sess = HibernateSession.currentSession();


    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {
      //  HibernateSession.closeSession();
    }

      public void testAllOKRequest() throws Exception {

          String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.9\" " +
                  "client_authorization=\""+ OWDUtilities.encryptData("179") +"\" client_id=\"179\" testing=\"TRUE\">" +
                  "<PHION_SHIPPING_RATE_REQUEST>\n" +
                  "\t<SHIPPING_ADDRESS>\n" +
                  "\t\t<ADDRESS_1>10 1st ave e</ADDRESS_1>\n" +
                  "\t\t<ADDRESS_2></ADDRESS_2>\n" +
                  "\t\t<CITY>Mobridgee</CITY>\n" +
                  "\t\t<REGION>South Dakota</REGION>\n" +
                  "\t\t<POSTCODE>57601</POSTCODE>\n" +
                  "\t\t<COUNTRYCODE>MX</COUNTRYCODE>\n" +
                  "\t</SHIPPING_ADDRESS>\n" +
                  "\t<VALUE>101.00</VALUE>\n" +
                  "\t<ITEMS>\n" +
                  "\t\t<ITEM sku=\"DPHTSTRIPS-01\" qty=\"1\" />\n" +
                //  "\t\t<ITEM sku=\"TNTUCK-01\" qty=\"2\" />\n" +
                  "\t</ITEMS>\n" +
                  "</PHION_SHIPPING_RATE_REQUEST>" +
                  "</OWD_API_REQUEST>";

          APIResponse ssr = (APIResponse) rateRequestAPI(xmlString);
          log.debug("getting response "+ssr);
         assert(ssr.getXML().contains("PHION_SHIPPING_RATE_RESPONSE"));

      }

    static void liveRequestAPI(String request) throws Exception
    {
        log.debug("xmlString = " + request);

            Document response =      ApiTestSuiteHarness.getLiveAPIResponse(request);
        ApiTestSuiteHarness.verifyAPIResponse((Document) response);

        ApiTestSuiteHarness.prettyPrint(response.getDocumentElement(), System.out);




    }

    static APIResponse rateRequestAPI(String request) throws Exception
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

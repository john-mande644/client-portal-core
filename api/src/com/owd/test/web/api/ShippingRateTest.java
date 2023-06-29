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
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 28, 2010
 * Time: 11:02:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShippingRateTest extends ApiTestSuiteHarness
{
private final static Logger log =  LogManager.getLogger();


    Session sess = null;


    public ShippingRateTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

      //  sess = HibernateSession.currentSession();


    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {
        HibernateSession.closeSession();
    }

      public void testAllOKRequest() throws Exception {

          String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.0\" " +
                  "client_authorization=\""+ OWDUtilities.encryptData("489") +"\" client_id=\"489\" testing=\"TRUE\">" +
                  "<OWD_SHIPPING_RATE_REQUEST packaging_lbs=\"0.0\">\n" +
                  "\t<SHIPPING_ADDRESS>\n" +
                  "\t\t<ADDRESS_1>10 1st ave e</ADDRESS_1>\n" +
                  "\t\t<ADDRESS_2></ADDRESS_2>\n" +
                  "\t\t<CITY>Mobridgee</CITY>\n" +
                  "\t\t<REGION>South Dakota</REGION>\n" +
                  "\t\t<POSTCODE>57601</POSTCODE>\n" +
                  "\t\t<COUNTRYCODE>US</COUNTRYCODE>\n" +
                  "\t</SHIPPING_ADDRESS>\n" +
                  "\t<ITEMS>\n" +
                  "\t\t<ITEM sku=\"P189964\" qty=\"1\" />\n" +
                  "\t</ITEMS>\n" +
                  "\t<SHIPMETHODS>\n" +
                  "\t\t<CODE>OWD.1ST.PRIORITY</CODE>" +
                  "<CODE>OWD.1ST.LETTER</CODE>\n" +
                  "\t\t<CODE>UPS.GND</CODE>\n" +
                  "\t\t<CODE>TANDATA_FEDEXFSMS.FEDEX.SP_PS</CODE>\n" +
                  "\t\t<CODE>TANDATA_FEDEXFSMS.FEDEX.SP_STD</CODE>\n" +
                  "\t</SHIPMETHODS>\n" +   "<FACILITY_CODE>DC6</FACILITY_CODE>\n" +
                  "</OWD_SHIPPING_RATE_REQUEST>" +
                  "</OWD_API_REQUEST>";


          log.debug(xmlString);

          Document response;

          long start = Calendar.getInstance().getTimeInMillis();
          response = getInternalAPIResponse(xmlString);
          long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
          log.debug(secs+" secs");
          log.debug(""+response);
          prettyPrint(response.getDocumentElement(), System.out);

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

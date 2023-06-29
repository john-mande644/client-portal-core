package com.owd.test.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.web.api.APIResponse;
import com.owd.web.api.SubscriptionStatusResponse;
import org.hibernate.Session;
import com.owd.hibernate.HibernateSession;
import com.owd.core.OWDUtilities;

import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 7, 2008
 * Time: 1:47:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionUpdateTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

     Session sess = null;


    public SubscriptionUpdateTest(String test) {
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

     public void testUpdateStatusResponse() throws Exception {

          String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.9\" " +
                  "client_authorization=\""+ OWDUtilities.encryptData("320") +"\" client_id=\"320\" testing=\"TRUE\">" +
                  "<OWD_SUBSCRIPTION_UPDATE_REQUEST>" +
                 "<OWD_SUBSCRIPTION_UPDATE autoShipId=\"3369\">\n" +
                  "<billName>Karen MageeSauer</billName>\n" +
                  "\t\t<billAddressOne>\n" +
                  "\t\t\t4321 Trophy Drive\n" +
                  "\t\t</billAddressOne>\n" +
                  "\t\t<billAddressTwo>\n" +
                  "\t\t</billAddressTwo>\n" +
                  "\t\t<billCity>\n" +
                  "\t\t\tBoothwyn\n" +
                  "\t\t</billCity>\n" +
                  "\t\t<billState>\n" +
                  "\t\t\tPA\n" +
                  "\t\t</billState>\n" +
                  "\t\t<billZip>\n" +
                  "\t\t\t19061\n" +
                  "\t\t</billZip>\n" +
                  "\t\t<billCountry>\n" +
                  "\t\t\tUSA\n" +
                  "\t\t</billCountry>\n" +
                  "\t\t<ccNum>\n" +
                  "\t\t\t4308517233263825\n" +
                  "\t\t</ccNum>\n" +
                  "\t\t<ccExpMon>\n" +
                  "\t\t\t3\n" +
                  "\t\t</ccExpMon>\n" +
                  "\t\t<ccExpYear>\n" +
                  "\t\t\t2009\n" +
                  "\t\t</ccExpYear>"+
                  "<ITEM><sku>EE</sku><quantity>1</quantity><unitPrice>27.95</unitPrice></ITEM>"+
                  "</OWD_SUBSCRIPTION_UPDATE></OWD_SUBSCRIPTION_UPDATE_REQUEST></OWD_API_REQUEST>";

         try
         {
         APIResponse ssr = subStatusAPI(xmlString);
          log.debug(ssr.getNode());
         }catch(Exception ex)
         {
             log.debug("error:"+ex.getMessage());
             ex.printStackTrace();
         }

      }

    static APIResponse subStatusAPI(String request) throws Exception
    {
      //  log.debug("xmlString = " + request);

                org.w3c.dom.Document doc = null;

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setNamespaceAware(false);
                    factory.setValidating(false);
                    javax.xml.parsers.DocumentBuilder  builder = factory.newDocumentBuilder();

                    doc = builder.parse(new java.io.ByteArrayInputStream(request.getBytes()));
        log.debug("here");
                    return com.owd.web.api.APIRequest.handle(doc);




    }
}

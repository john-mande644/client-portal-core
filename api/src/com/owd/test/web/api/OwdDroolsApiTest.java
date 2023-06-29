/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 2, 2007
 * Time: 11:17:11 AM
 * To change this template use File | Settings | File Templates.
 */

package com.owd.test.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.OrderUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;
import org.apache.xpath.XPathAPI;
import junit.framework.Assert;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * Assumes that the API web app is running on localhost:8080
 *
 * Copied from APITestSuite
 */
public class OwdDroolsApiTest extends ApiTestSuiteHarness {
private final static Logger log =  LogManager.getLogger();


    Session sess = null;


    public OwdDroolsApiTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

       /* sess = HibernateSession.currentSession();
        Inventory item = Inventory.dbloadByPart("TestItemOWD0001", "112"); //standard test com.owd.api.client, ACME BBQ
        item.setAvailability(0);
        item.is_active = 1;
        item.is_auto_inventory = 0;
        item.description = "Test SKU 0001";
        item.dbupdate( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection());
        HibUtils.commit(sess);*/

    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {
/*
        HibernateSession.closeSession();
*/
    }

    public void testOrderCreateRequestG2024Supgradedself() throws Exception{

//        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"FALSE\">" +
//                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G190Sspirithoods\">" +
//                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
//                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P69439\" description=\"Item: 2353270 | SupplierSKU: PA01 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G2024Supgradedself\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P86343\" description=\"Item: 2353270 | SupplierSKU: P86343 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getInternalAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }

    public void testOrderCreateRequestvintagemarqueelights() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"vintagemarqueelights\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P86343\" description=\"Item: 2353270 | SupplierSKU: P86343 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }
    public void testArbitraryRequest() throws Exception
    {
         // System.setProperty("com.owd.environment","test");
        Document response;


       /* String xmlString =//"<?xml version=\"1.0\"?>" +
                "<OWD_API_REQUEST client_id=\"576\" api_version=\"2.0\" client_authorization=\"vQNBRUBtFhlZ6eAQa83O2wM=\" testing=\"false\">\n" +
                        "    <OWD_ORDER_CREATE_REQUEST order_reference=\"1111-1111-1111\" order_source=\"Intranet\" is_gift=\"NO\" backorder_rule=\"BACKORDER\" facility_rule=\"DC6\">\n" +
                        "        <SHIPPING_INFO last_name=\"Woosley\" first_name=\"Ben\" address_one=\"221 11th St\" address_two=\"\" state=\"CA\" city=\"San Francisco\" country=\"US\" zip=\"94103\" ship_type=\"OWD.1ST.LETTER\" />\n" +
                        "        <BILLING_INFO last_name=\"Woosley\" first_name=\"Ben\" address_one=\"221 11th St\" address_two=\"\" state=\"CA\" city=\"San Francisco\" country=\"US\" zip=\"94103\" payment_type=\"CLIENT\" />\n" +
                        "        <LINE_ITEM part_reference=\"SPRINT10\" description=\"Sprint\" requested=\"1\" cost=\"100\" />\n" +
                        "    </OWD_ORDER_CREATE_REQUEST>\n" +
                        "</OWD_API_REQUEST>";*/
        String xmlString =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.6\" client_id=\"266\" client_authorization=\"I9Hjz/Tl782AT+LriRzi5gM=\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST order_reference=\"123\" order_source=\"API\" backorder_rule=\"BACKORDER\"><BILLING_INFO last_name=\"Lutz\" first_name=\"Leo\" company_name=\"Awesome Coding\" address_one=\"123 Rainbow St\" address_two=\"Line Two\" city=\"Nowhere\" state=\"AA\" zip=\"00000\" country=\"United States\" phone=\"555-555-5555\" email=\"skeemer+test@gmail.com\"/><SHIPPING_INFO last_name=\"Lutz\" first_name=\"Leo\" company_name=\"Awesome Coding\" address_one=\"123 Rainbow St\" address_two=\"Line Two\" city=\"Nowhere\" state=\"AA\" zip=\"00000\" country=\"United States\" ship_cost=\"0.00\" ship_type=\"BWY\"><BEST_WAY><CARRIER><![CDATA[FDX.STD]]></CARRIER><CARRIER><![CDATA[UPS.GND]]></CARRIER><CARRIER><![CDATA[OWD.1ST.Parcel]]></CARRIER></BEST_WAY></SHIPPING_INFO><LINE_ITEM part_reference=\"Bone Builder 12 Month\" description=\"something awesome\" requested=\"3\" cost=\"3.40\" backordered=\"0\"/><COMMENTS><![CDATA[]]></COMMENTS><MESSAGE><![CDATA[]]></MESSAGE><WAREHOUSENOTES><![CDATA[]]></WAREHOUSENOTES></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        try {
           // response = getLiveAPIResponse(xmlString);

          //  prettyPrint(response, System.out);
          //  // verifyAPIResponse(response);
            response = getInternalAPIResponse(xmlString,"");
            //response = getTestAPIResponse(xmlString);
            prettyPrint(response,System.out);
            assertEquals(1,1);
        }catch(Exception ex){
            ex.printStackTrace();
        }

      //  response = getTestAPIResponse(xmlString) ;

        //prettyPrint(response,System.out);
        //  verifyAPIResponse(response);


    }
    public void testCreateOrderRequestOWD() throws Exception{
       /* String xmlString =  "<OWD_API_REQUEST api_version=\"2.0\" client_authorization=\"vQNBRUBtFhlZ6eAQa83O2wM=\" client_id=\"576\" testing=\"false\">\n" +
                "  <OWD_ORDER_CREATE_REQUEST backorder_rule=\"BACKORDER\" facility_rule=\"DC6\" is_gift=\"NO\" order_reference=\"76362-58548-40687vvvvv\" order_source=\"Intranet\">\n" +
                "    <SHIPPING_INFO address_one=\"11407 SW Amu St.\" address_two=\"Suite #AW021\" city=\"Tualatin\" country=\"US\" first_name=\"Christopher\" last_name=\"Ian Jones\" ship_type=\"OWD.1ST.LETTER\" state=\"Oregon\" zip=\"97062\"/>\n" +
                "    <BILLING_INFO address_one=\"11407 SW Amu St.\" address_two=\"Suite #AW021\" city=\"Tualatin\" country=\"US\" first_name=\"Christopher\" last_name=\"Ian Jones\" payment_type=\"CLIENT\" state=\"Oregon\" zip=\"97062\"/>\n" +
                "    <LINE_ITEM cost=\"25.0\" description=\"The Full Stack (1x RISE, 1x SPRINT, 1x YAWN)\" part_reference=\"RISE\" requested=\"0\" backordered=\"1\"/>\n" +
                "    </OWD_ORDER_CREATE_REQUEST>\n" +
                "</OWD_API_REQUEST>";*/

         String xmlString =  "<OWD_API_REQUEST api_version=\"2.0\" client_authorization=\"D9JohVlG4pRrAPfrUj90pgM=\" client_id=\"488\" testing=\"false\">\n" +
                "  <OWD_ORDER_CREATE_REQUEST backorder_rule=\"HOLDORDER\" facility_rule=\"DC6\" is_gift=\"NO\" order_reference=\"DHLtest7\" order_source=\"Intranet\">\n" +
                "    <SHIPPING_INFO address_one=\"10 1st Ave E\" address_two=\"\" city=\"Groton\" country=\"US\" first_name=\"Testing\" last_name=\"Throw\"  state=\"SD\" zip=\"57445\" ship_type=\"OWD.1ST.LETTER\"></SHIPPING_INFO>\n" +
                "    <BILLING_INFO address_one=\"11407 SW Amu St.\" address_two=\"Suite #AW021\" city=\"Tualatin\" country=\"US\" first_name=\"Christopher\" last_name=\"Ian Jones\" payment_type=\"CLIENT\" state=\"Oregon\" zip=\"97062\"/>\n" +
                "    <LINE_ITEM cost=\"25.0\" description=\"DC6-Fragile-Stickers\" part_reference=\"DC6-Fragile-Stickers\" requested=\"1\" backordered=\"1\"/>\n" +
                "    </OWD_ORDER_CREATE_REQUEST>\n" +
                "</OWD_API_REQUEST>";

            String xmlRelease="<OWD_API_REQUEST\tapi_version=\"2.0\"\n" +
                    "             client_id=\"488\"\n" +
                    "              client_authorization=\"D9JohVlG4pRrAPfrUj90pgM=\"\n" +
                    "              testing=\"FALSE\" >\n" +
                    "<OWD_ORDER_RELEASE_REQUEST clientOrderId=\"DHLtest7\" partialShipAllowed=\"BACKORDER\"/>\n" +
                    "</OWD_API_REQUEST>";

//System.out.println(xmlString);

        log.debug(xmlRelease);
        Document response;
        response = getLiveAPIResponse(xmlRelease) ;

        prettyPrint(response,System.out);
        assertEquals(1,1);

    }

    public void testOrderCreateRequestG189V1155() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G189V1155\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P85804\" description=\"Item: 2353270 | SupplierSKU: P85804 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }


    public void testOrderCreateRequestG198Swallcandyarts() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G198Swallcandyarts\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P85804\" description=\"Item: 2353270 | SupplierSKU: P85804 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }


    public void testOrderCreateRequestG213Sbrooklyngrooming() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G213Sbrooklyngrooming\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P85804\" description=\"Item: 2353270 | SupplierSKU: P85804 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }

    public void testOrderCreateRequestG216Snakedsportsgear() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G216Snakedsportsgear\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P85804\" description=\"Item: 2353270 | SupplierSKU: P85804 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }

    public void testOrderCreateRequestG302V2036() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G302V2036\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P85804\" description=\"Item: 2353270 | SupplierSKU: P85804 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }

    public void testOrderCreateRequestG52Ssoydelicious() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G52Ssoydelicious\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P85804\" description=\"Item: 2353270 | SupplierSKU: P85804 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }

    public void testOrderCreateRequestInsertP70053() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G52Ssoydelicious\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"5006 Erskine Way SW\" address_two=\"\" " +
                "city=\"Seattle\" state=\"WA\" zip=\"98106\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P85804\" description=\"Item: 2353270 | SupplierSKU: P85804 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);
    }

    public void testOrderCreateRequestStickers() throws Exception{

        String xmlString =  "<OWD_API_REQUEST api_version=\"1.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\">" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"707885.1\" allow_duplicates=\"true\" backorder_rule=\"NOBACKORDER\" group_name=\"G52Ssoydelicious\">" +
                "<SHIPPING_INFO first_name=\"Joshua\" last_name=\"Low\" address_one=\"79 New Montgomery Street\" address_two=\"\" " +
                "city=\"San Francisco\" state=\"CA\" zip=\"94105\" country=\"USA\" ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.STD</CARRIER><CARRIER>UPS.NDA</CARRIER><CARRIER>UPS.NAM</CARRIER><CARRIER>UPS.NDS</CARRIER><CARRIER>UPS.WEXP</CARRIER><CARRIER>TANDATA_UPS.UPS.WEXPPLS</CARRIER><CARRIER>FDX.PRI</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"2353270\" part_reference=\"P86352\" description=\"Item: 2353270 | SupplierSKU: P86352 | Title: 1 Core Panda | \" requested=\"1\" declared_value=\"0\" customs_desc=\"\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);
    }





}

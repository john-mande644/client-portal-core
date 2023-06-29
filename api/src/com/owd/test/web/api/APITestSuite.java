/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 2, 2007
 * Time: 11:17:11 AM
 * To change this template use File | Settings | File Templates.
 */

package com.owd.test.web.api;

import com.owd.test.web.api.ApiTestSuiteHarness;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;
import com.owd.web.api.AsnCreateRequest;
import junit.framework.Assert;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import java.util.*;
import java.util.concurrent.*;

/*
 * Assumes that the API web app is running on localhost:8080
 */
public class APITestSuite extends ApiTestSuiteHarness {
private final static Logger log =  LogManager.getLogger();


    Session sess = null;


    public APITestSuite(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

       /* sess = HibernateSession.currentSession();
        Inventory item = Inventory.dbloadByPart("TestItemOWD0001", "112"); //standard test com.owd.api.client, ACME BBQ
        item.setAvailability(0);yes
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


    public void testOrderCancelRequest() throws Exception {

            String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.9\" client_id=\"367\" client_authorization=\"Y++Lvv+p1AbOrny2KvXh5gM=\" testing=\"TRUE\"><OWD_ORDER_CANCEL_REQUEST clientOrderId=\"xxx\" /></OWD_API_REQUEST>";

        Document response;

      // response = getInternalAPIResponse(xmlString);
        //verifyAPIResponse(response);
   /*

   response = getLiveAPIResponse(xmlString);
        assertTrue("bad order id must fail",XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("error_response").getNodeValue().equals("Order ID not recognized"));

        xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.9\" client_id=\"367\" client_authorization=\"Y++Lvv+p1AbOrny2KvXh5gM=\" testing=\"TRUE\"><OWD_ORDER_CANCEL_REQUEST clientOrderId=\"11689\" /></OWD_API_REQUEST>";

        response = getLiveAPIResponse(xmlString);
        assertTrue("multiple copy order id must fail",XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("error_response").getNodeValue().equals("Multiple orders returned by search; an unambiguous order reference must be used instead"));

        xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.9\" client_id=\"367\" client_authorization=\"Y++Lvv+p1AbOrny2KvXh5gM=\" testing=\"TRUE\"><OWD_ORDER_CANCEL_REQUEST clientOrderId=\"7870702\" /></OWD_API_REQUEST>";

               response = getLiveAPIResponse(xmlString);
               assertTrue("multiple copy order id must fail",XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("error_response").getNodeValue().equals("This order has already been shipped"));
*/
        xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST backorder_rule=\"NOBACKORDER\" facility_rule=\"DC6\" group_name=\"chilitechnology\" order_reference=\"11078184\"><SHIPPING_INFO address_one=\"1 INNOVATION WAY\" address_two=\"\" city=\"MERRIMACK\" company_name=\".\" first_name=\"SEAN\" last_name=\"CONDON\" ship_type=\"EDI\" state=\"NH\" zip=\"03054\"/><BILLING_INFO paid=\"YES\" payment_type=\"CLIENT\" po_number=\"843494470001\"/><LINE_ITEM customs_desc=\"Electronic Heating and Cooling Mattress\" declared_value=\"0.01\" description=\"Title: ChiliPad™ twin xl (split king 38&quot;x80&quot;) - single zone | Item: 13588274 | SupplierSKU: CP502 | \" line_number=\"13588274\" part_reference=\"P141390\" requested=\"1\"/><MESSAGE/></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";
        response = getLiveAPIResponse(xmlString);

        prettyPrint(response.getDocumentElement(), System.out);
        verifyAPIResponse(response);

        }
   

    public void testReportRequest() throws Exception
    {
        String xmlString = "<?xml version=\"1.0\"?> <OWD_API_REQUEST api_version=\"2.1\" client_authorization=\""+ OWDUtilities.encryptData("489")+"\" client_id=\"489\"><OWD_INVENTORY_COUNT_REQUEST show_backorders=\"1\" part_reference=\"P188675\" /></OWD_API_REQUEST>";
        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getLiveAPIResponse(xmlString);
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
    }

    public void testInsertedFalse() throws Exception{
        Vector skuList = new Vector();
        skuList.add(new LineItem("",0,0.0f,0.0f,"","",""));
        ((LineItem)skuList.get(0)).insertedItem=false;

        for (LineItem line : (Vector<LineItem>) skuList) {
            if (line.insertedItem==true) {
                log.debug("Line is true");
            }                                   else
            {
                log.debug("line is false");
            }
        }

        int inserts =0;
        for (LineItem line : (Vector<LineItem>) skuList) {
            if (line.insertedItem==true) {
                inserts++;
            }
        }
        log.debug(inserts);

    }

    public void testJbrandOrderCreateRequest() throws Exception {


        String xmlString = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" " +
                " testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST backorder_rule=\"HOLDORDER\" facility_rule=\"DC6\" " +
                "order_reference=\"testing12danny\" group_name=\"kettleandfire\">" +
                "<BILLING_INFO po_number=\"12921994\" address_one=\"block 1C #41-35 Cantonment Road\" address_two=\"Pinnacle @Duxton\" city=\"singapore\" company_name=\"PJM Distributions Inc\" " +
                "country=\"singapore\" first_name=\"David\" last_name=\"Peterson\" phone=\"800-530-3930\" state=\"\" zip=\"O85301\" paid=\"YES\" payment_type=\"CLIENT\" />" +
                "<SHIPPING_INFO address_one=\"#114 – 6951 – 72nd Street\" address_two=\"\" city=\"singapore\" company_name=\"PJM Distributions Inc\" " +
                "country=\"singapore\" first_name=\"David\" last_name=\"Peterson\" phone=\"800-530-3930\" ship_type=\"BWY\" state=\"\" zip=\"O85301\">" +
                "<BEST_WAY><CARRIER>OWD.1ST.LETTER</CARRIER><CARRIER>OWD_USPS_I_FIRST</CARRIER></BEST_WAY></SHIPPING_INFO>" +
                "<BILLING_INFO paid=\"YES\" payment_type=\"CLIENT\"/>" +
                "<LINE_ITEM customs_desc=\"Testing\" declared_value=\"0.01\" description=\"testing\" part_reference=\"P404352\" requested=\"1\"/>" +
                "<MESSAGE/>" +
                "</OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        //response = getTestAPIResponse(xmlString);
        response = getLiveAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis() - start) / 1000;
        log.debug(secs + " secs");
        log.debug("" + response);
        prettyPrint(response.getDocumentElement(), System.out);
    }
    public void testOrderCreateRequest() throws Exception{


        String xmlString =   "<OWD_INVENTORY_REPORT_REQUEST><FILTER><TYPE>startDate</TYPE><VALUE>20191009</VALUE></FILTER><FILTER><TYPE>endDate</TYPE><VALUE>20191010</VALUE></FILTER><FILTER><TYPE>facilityCode</TYPE><VALUE>DC6</VALUE></FILTER></OWD_INVENTORY_REPORT_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getInternalAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }

    public void testOrderCreateRequestGroup() throws Exception{


        int i = 1;

        while(i<60) {
            String xmlString = "<OWD_API_REQUEST api_version=\"2.6\" client_authorization=\"8e+cR+IoL5M6rXO9zI5LZwI=\" client_id=\"55\" testing=\"false\">" +
                    "<OWD_INVENTORY_STATUS_REQUEST >" +
                    "<FILTER>" +
                    "<TYPE>sku</TYPE>" +
                    "<VALUE>ebay items</VALUE>" +
                    "</FILTER>" +
                    "</OWD_INVENTORY_STATUS_REQUEST>" +
                    "</OWD_API_REQUEST>";

            log.debug(xmlString);

            Document response;

            long start = Calendar.getInstance().getTimeInMillis();
            response = getLiveAPIResponse(xmlString);
            long secs = (Calendar.getInstance().getTimeInMillis() - start) / 1000;
            log.debug(secs + " secs");
            log.debug("" + response);
            prettyPrint(response.getDocumentElement(), System.out);
            i++;
        }

    }
    public void testPackSlipView() throws Exception{
        System.setProperty("com.owd.environment","test");
        String xmlString = "<?xml version=\"1.0\"?><OWD_API_REQUEST api_version=\"1.0\" client_id=\"112\" client_authorization=\"00pIsMTGFZAr4nwEp+pwggM=\" testing=\"false\"><OWD_INVENTORY_CREATE_REQUEST><SKU>xg2030</SKU><TYPE>PHYSICAL</TYPE></OWD_INVENTORY_CREATE_REQUEST></OWD_API_REQUEST>";
        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getInternalAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);
    }
    public void testSSCC() throws Exception{
        String xmlString = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"/JOUl8rqjOekOtigrMM2kwM=\" client_id=\"577\" testing=\"false\">" +
                "<OWD_ORDER_STATUS_REQUEST clientOrderId=\"44592\"/>" +
                "</OWD_API_REQUEST>";
        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getInternalAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
       // log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);
    }
    public void testOrderCreateRequestRules() throws Exception{


        String xmlString =   "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                " testing=\"true\"><OWD_ORDER_CREATE_REQUEST backorder_rule=\"HOLDORDER\" facility_rule=\"DC1\" " +
                "order_reference=\"ruleship21\">" +
                "<SHIPPING_INFO  address_one=\"#114 – 6951 – 72nd Street\" address_two=\"\" city=\"Delta\" company_name=\"PJM Distributions Inc\" " +
                "country=\"US\" first_name=\"David\" last_name=\"Peterson\" phone=\"800-530-3930\" ship_type=\"UPS.2DA\" state=\"NY\" zip=\"90185\">" +
                "</SHIPPING_INFO>" +
                "<BILLING_INFO paid=\"YES\" payment_type=\"CLIENT\"/>" +
                "<LINE_ITEM customs_desc=\"Testing\" declared_value=\"0.01\" description=\"testing\" part_reference=\"Ebay Items\" requested=\"1\" backordered=\"1\"/>" +
                "<MESSAGE/>" +
                "</OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getLiveAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }
    public void testOrderCreateRequestRulesPopWhite() throws Exception{


        String xmlString =   "<?xml version=\"1.0\"?>\n" +
                "<OWD_API_REQUEST api_version=\"1.0\" client_id=\"608\" client_authorization=\"f0Zph/hWzB09OoeoeP6NygM=\" >\n" +
                "<OWD_ORDER_CREATE_REQUEST order_reference=\"34aOWDTEST12\" order_source=\"Web Order\" is_gift=\"NO\" backorder_rule=\"NOBACKORDER\">\n" +
                "<SHIPPING_INFO last_name=\"Demo2\" first_name=\"Demo1\" company_name=\"ABC\" address_one=\"New york\" address_two=\"\" city=\"New york\" state=\"CA\" zip=\"10001\" country=\"US\" phone=\"\" email=\"\" ship_type=\"UPS.GND\" ship_cost=\"0.00\" />\n" +
                "<BILLING_INFO last_name=\"Demo2\" first_name=\"Demo1\" company_name=\"ABC\" address_one=\"New york\" address_two=\"\" city=\"New york\" state=\"CA\" zip=\"10001\" country=\"US\" phone=\"1234567890\" email=\"elango@rjtcompuquest.com\" payment_type=\"CLIENT\" order_discount=\"0.00\" paid=\"YES\"\n" +
                " />\n" +
                " <LINE_ITEM part_reference=\"1001\" description=\"Test Prodcut 3\" requested=\"1\" cost=\"380.00\" />\n" +
                " <LINE_ITEM part_reference=\"1002\" description=\"Test Product2\" requested=\"1\" cost=\"90.00\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getLiveAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }

    public void testThreadingTask() throws Exception{
        ExecutorService exec = Executors.newFixedThreadPool(9);
        ArrayList<String> rl = new ArrayList<>();

        rl.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\"><OWD_INVENTORY_COUNT_REQUEST part_reference=\"P204077\" show_backorders=\"1\"/></OWD_API_REQUEST>\";");
        rl.add("<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"BP4XJ3OA248L1AF+dId0dwM=\" client_id=\"507\"" +
                " testing=\"false\"> <OWD_ORDER_STATUS_REQUEST clientOrderId=\"1832435\" prefixSearch=\"false\" summary=\"false\"/></OWD_API_REQUEST>");
        rl.add("<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                " testing=\"false\"> <OWD_ORDER_UPDATE_REQUEST clientOrderId=\"12560851v\">" +
                "<LINE_ITEMS>" +
                "<LINE_ITEM part_reference=\"BenefitPacket-Rancho\" requested=\"0\" backordered=\"1\" cost=\"0\"/>" +
                "</LINE_ITEMS></OWD_ORDER_UPDATE_REQUEST></OWD_API_REQUEST>");
        rl.add("<OWD_API_REQUEST api_version=\"2.2\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                " testing=\"false\"> <OWD_ORDER_STATUS_REQUEST clientOrderId=\"118995084\" prefixSearch=\"false\" summary=\"false\"/></OWD_API_REQUEST>");
        rl.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_id=\"616\" client_authorization=\"+F+rRJoiOgV32l6PBTOc/gM=\" testing=\"FALSE\">\n" +
                "  <OWD_INVENTORY_STATUS_REQUEST/>\n" +
                "</OWD_API_REQUEST>");
        rl.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_id=\"628\" client_authorization=\"rWGZkjKmWKrNl2FmtQHQPwM=\" testing=\"FALSE\">\n" +
                "  <OWD_INVENTORY_STATUS_REQUEST/>\n" +
                "</OWD_API_REQUEST>");
        rl.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_id=\"551\" client_authorization=\"d3MGkl7UVn/W91JQPSVBcgM=\" testing=\"FALSE\">\n" +
                "  <OWD_INVENTORY_STATUS_REQUEST/>\n" +
                "</OWD_API_REQUEST>");
        rl.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_id=\"529\" client_authorization=\"DpMElJY0PDJ3FidnfoOOSgM=\" testing=\"FALSE\">\n" +
                "  <OWD_INVENTORY_STATUS_REQUEST/>\n" +
                "</OWD_API_REQUEST>");
        int i = 0;

        while(i<200){
            exec.submit(new apiThreadingTask(rl.get(i%rl.size())));
            i++;
        }

        exec.shutdown();
        System.out.println("wating executor");
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("done");
    }
    public void testMultiThread() throws Exception{
        String xmlCount1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\"><OWD_INVENTORY_COUNT_REQUEST part_reference=\"P204077\" show_backorders=\"1\"/></OWD_API_REQUEST>\";";
        String xmlStatus2 = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"BP4XJ3OA248L1AF+dId0dwM=\" client_id=\"507\"" +
                " testing=\"false\"> <OWD_ORDER_STATUS_REQUEST clientOrderId=\"1832435\" prefixSearch=\"false\" summary=\"false\"/></OWD_API_REQUEST>";
        String xmlString = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                " testing=\"false\"> <OWD_ORDER_UPDATE_REQUEST clientOrderId=\"12560851v\">" +
                "<LINE_ITEMS>" +
                "<LINE_ITEM part_reference=\"BenefitPacket-Rancho\" requested=\"0\" backordered=\"1\" cost=\"0\"/>" +
                "</LINE_ITEMS></OWD_ORDER_UPDATE_REQUEST></OWD_API_REQUEST>";

        APIThreadingTest t1 = new APIThreadingTest(50,"ONE",xmlCount1);
        APIThreadingTest t2 = new APIThreadingTest(50,"TWO",xmlStatus2);
        APIThreadingTest t3 = new APIThreadingTest(50,"three",xmlString);
      /*  APIThreadingTest t4 = new APIThreadingTest(50,"four");
        APIThreadingTest t5 = new APIThreadingTest(50,"ONE");
        APIThreadingTest t6 = new APIThreadingTest(50,"TWO");
        APIThreadingTest t7 = new APIThreadingTest(50,"three");
        APIThreadingTest t8 = new APIThreadingTest(50,"four");*/
        t1.start();
        t2.start();
        t3.start();
       /* t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();*/
    }

    public void testMultiThreadHold() throws Exception{
        String xmlCount1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.2\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\"><OWD_INVENTORY_COUNT_REQUEST part_reference=\"P204077\" show_backorders=\"1\"/></OWD_API_REQUEST>\";";
        String xmlStatus2 = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"BP4XJ3OA248L1AF+dId0dwM=\" client_id=\"507\"" +
                " testing=\"false\"> <OWD_ORDER_STATUS_REQUEST clientOrderId=\"1832435\" prefixSearch=\"false\" summary=\"false\"/></OWD_API_REQUEST>";
        String xmlString = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                " testing=\"false\"> <OWD_ORDER_UPDATE_REQUEST clientOrderId=\"12560851v\">" +
                "<LINE_ITEMS>" +
                "<LINE_ITEM part_reference=\"BenefitPacket-Rancho\" requested=\"0\" backordered=\"1\" cost=\"0\"/>" +
                "</LINE_ITEMS></OWD_ORDER_UPDATE_REQUEST></OWD_API_REQUEST>";

        APIThreadingTestMulti t1 = new APIThreadingTestMulti(21,"ONE","1");
        APIThreadingTestMulti t2 = new APIThreadingTestMulti(41,"TWO","21");
        APIThreadingTestMulti t3 = new APIThreadingTestMulti(60,"three","41");
      /*  APIThreadingTest t4 = new APIThreadingTest(50,"four");
        APIThreadingTest t5 = new APIThreadingTest(50,"ONE");
        APIThreadingTest t6 = new APIThreadingTest(50,"TWO");
        APIThreadingTest t7 = new APIThreadingTest(50,"three");
        APIThreadingTest t8 = new APIThreadingTest(50,"four");*/
        t1.start();
        t2.start();
        t3.start();
       /* t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();*/
    }

     public void testInventoryCountRequest() throws Exception{

        int i = 0;
        while(i<1) {
           // String xmlString =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.2\" client_authorization=3AbkCyn3yBXRHOkwFW9lkwI= client_id=\"55\"><?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_INVENTORY_COUNT_REQUEST part_reference=\"testkit\" show_backorders=\"1\"/></OWD_API_REQUEST>";
            String xmlString = "<?xml version='1.0' encoding='UTF-8'?><OWD_API_REQUEST api_version=\"2.0\" client_id=\"529\" client_authorization=\"OVFO6Qlmc3Lkhp+N7Sp5MAM=\" testing=\"FALSE\"><OWD_ORDER_CREATE_REQUEST backorder_rule=\"BACKORDER\" facility_rule=\"DC6\" order_reference=\"942482178391473090\"><SHIPPING_INFO address_one=\"9038 paseo grande\" city=\"San Antonio\" first_name=\"Alexamder\" last_name=\"Martínez maya\" ship_cost=\"0.00\" ship_type=\"OWD.1ST.LETTER\" state=\"TX\" zip=\"78245\" /><BILLING_INFO payment_type=\"CLIENT\" /><LINE_ITEM cost=\"0.00\" description=\"New Driver Kit(New Driver Kit 1.0)\" part_reference=\"LYFT093\" requested=\"1\" /></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>" ;


            /* String xmlString = "<?xml version=\"1.0\"?>" +
                    "<OWD_API_REQUEST api_version=\"2.0\" client_id=\"529\" client_authorization=\"OVFO6Qlmc3Lkhp+N7Sp5MAM=\" testing=\"TRUE\">" +

                    "<OWD_ORDER_CREATE_REQUEST " +
                    "backorder_rule=\"BACKORDER\" facility_rule=\"DC6\" order_reference=\"" +
                    "936576670006638142\"><SHIPPING_INFO address_one=\"185 Berry St Suite 5000\" " +
                    "city=\"San Francisco\" email=\"ppeterson@lyft.com\" first_name=\"Polly\" " +
                    "last_name=\"Peterson\" ship_cost=\"0.00\" ship_type=\"TANDATA_FEDEXFSMS.FEDEX.SP_PS\" " +
                    "state=\"CA\" zip=\"94107\" /><BILLING_INFO payment_type=\"CLIENT\" /><LINE_ITEM " +
                    "cost=\"0.00\" description=\"1KClub Jacket MS(1KClub Jacket MS)\" " +
                    "part_reference=\"LYFT126-KITMS\" requested=\"1\" /></OWD_ORDER_CREATE_REQUEST><" +
                    "/OWD_API_REQUEST>";*/
            log.debug(xmlString);

            Document response;

            long start = Calendar.getInstance().getTimeInMillis();
           // response = getServerAPIResponse(xmlString, "http://danny.owd.com:8080/api/api.jsp");
            response = getTestAPIResponse(xmlString);

            long secs = (Calendar.getInstance().getTimeInMillis() - start) / 1000;
            log.debug(secs + " secs");
            log.debug("" + response);

            prettyPrint(response.getDocumentElement(), System.out);
            i++;

        }

    }



    public void testFredericksUPdate() throws Exception{

        int i = 0;
        while(i<1) {
            // String xmlString =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.2\" client_authorization=3AbkCyn3yBXRHOkwFW9lkwI= client_id=\"55\"><?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_INVENTORY_COUNT_REQUEST part_reference=\"testkit\" show_backorders=\"1\"/></OWD_API_REQUEST>";

            String xmlString = "<?xml version=\"1.0\"?> " +
                    "<OWD_API_REQUEST api_version=\"2.2\" " +
                    "client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" " +
                    "client_id=\"489\"><OWD_INVENTORY_UPDATE_REQUEST " +
                    "sku=\"P478027\"><SKU>P478027</SKU><TITLE>Jenna Quilted Satin And Lace " +
                    "Thong-Clematis " +
                    "Blue-Black-S</TITLE><TYPE>PHYSICAL</TYPE><SUPPLIER>Fredericks</SUPPLIER><SUPPLIER_SKU>X37-1588.CBBK.S</SUPPLIER_SKU><COLOR>x37-1588-cbbk-clematis " +
                    "blue-black-url:clematis " +
                    "blue-black</COLOR><SIZE>s</SIZE><CUSTOMS_DESC></CUSTOMS_DESC><CASE_QTY>0</CASE_QTY><MASTER_CASE_QTY>0</MASTER_CASE_QTY><GROUPNAME>G_fredericksV2217</GROUPNAME><IMAGE_URL> " +
                    "http://d2wqfpkilprt19.cloudfront.net/images/sites/fredericks/1473371399327_7808726283148080214.1200w.jpg" +
                    "</IMAGE_URL><THUMB_URL> " +
                    "http://d2wqfpkilprt19.cloudfront.net/images/sites/fredericks/1473371399327_7808726283148080214.375w.jpg" +
                    "</THUMB_URL></OWD_INVENTORY_UPDATE_REQUEST></OWD_API_REQUEST>";
            log.debug(xmlString);

            Document response;

            long start = Calendar.getInstance().getTimeInMillis();
            response = getLiveAPIResponse(xmlString);
            long secs = (Calendar.getInstance().getTimeInMillis() - start) / 1000;
            log.debug(secs + " secs");
            log.debug("" + response);
            prettyPrint(response.getDocumentElement(), System.out);
            i++;

        }

    }
    public void testOrderstatusRequest() throws Exception{

        int i = 0;
        while(i<1) {
           /* String xmlString = "<OWD_API_REQUEST api_version=\"2.6\" client_authorization=\"mnwe4hgM8uOfJoy86EfMFAM=\" client_id=\"489\" testing=\"false\">" +
                    "<OWD_ORDER_STATUS_REQUEST clientOrderId=\"17623667\"/>" +
                    "</OWD_API_REQUEST>";*/

            String xmlString = "<OWD_API_REQUEST api_version=\"2.9\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                    " testing=\"false\"><OWD_INVENTORY_COUNT_REQUEST show_backorders=\"1\" part_reference=\"ebay items\"/>" +
                    "</OWD_API_REQUEST>";

            log.debug(xmlString);

            Document response;

            long start = Calendar.getInstance().getTimeInMillis();
            response = getInternalAPIResponse(xmlString);
            long secs = (Calendar.getInstance().getTimeInMillis() - start) / 1000;
            log.debug(secs + " secs");
            log.debug("" + response);
            prettyPrint(response.getDocumentElement(), System.out);
            i++;

        }

    }
    public void testUpdateOrderstatusRequest() throws Exception{
        System.setProperty("com.owd.environment", "test");

        int i = 0;
        while(i<1) {
            String xmlString = "<OWD_API_REQUEST api_version=\"2.2\" client_id=\"634\" client_authorization=\"/zZudFWzJyNffwIJ26vxyAM=\" testing=\"false\">" +
                    "<OWD_ORDER_CREATE_REQUEST order_reference=\"SO89050owddtestagain\" order_source=\"27\" is_gift=\"NO\" backorder_rule=\"PARTIALSHIP\" group_name=\"JOOR\" facility_rule=\"DC6\">" +
                    "<SHIPPING_INFO last_name=\"John Foesch\" first_name=\"\" address_one=\"1475 E 15th Ave\" address_two=\"Unit 66316\" city=\"Eugene\" state=\"OR\" zip=\"97403\" country=\"USA\" phone=\"\" email=\"\" ship_type=\"COM_OWD_FLATRATE_GROUND\" ship_cost=\"0.00\" />" +
                    "<WAREHOUSENOTES />" +
                    "<BILLING_INFO last_name=\"Dogeared Personal Repairs\" first_name=\"\" address_one=\"\" address_two=\"\" city=\"\" state=\"\" zip=\"\" country=\"\" phone=\"\" email=\"\" po_number=\"00811964\" paid=\"YES\" payment_type=\"CLIENT\" order_discount=\"0.00\" />" +
                    "<LINE_ITEM part_reference=\"879485000283\" description=\"Original Karma Necklace, 16&quot; w/2&quot; extender- SS\" requested=\"1\" cost=\"27.00\" customs_desc=\"Jewelry - Original Karma Necklace, 16&quot; w/2&quot; extender- SS\" declared_value=\"27.00\" line_number=\"10000\" />" +
                    "<CUSTOM_VALUE name=\"test\">hello</CUSTOM_VALUE>" +
                    "<CUSTOM_VALUE name=\"testing\"/>" +
                    "<MESSAGE />" +
                    "</OWD_ORDER_CREATE_REQUEST>" +
                    "</OWD_API_REQUEST>";

            log.debug(xmlString);

            Document response;

            long start = Calendar.getInstance().getTimeInMillis();
            response = getInternalAPIResponse(xmlString);
            long secs = (Calendar.getInstance().getTimeInMillis() - start) / 1000;
            log.debug(secs + " secs");
            log.debug("" + response);
            prettyPrint(response.getDocumentElement(), System.out);
            i++;

        }

    }

    public void testReleaseOrderstatusRequest() throws Exception{

        int i = 1;
        while(i<60) {
            String xmlString = "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                    " testing=\"false\"> <OWD_ORDER_RELEASE_REQUEST clientOrderId=\"zzGrouptesting100"+i+"\" partialShipAllowed=\"false\"/>" +
                    "</OWD_API_REQUEST>";

            log.debug(xmlString);

            Document response;

            long start = Calendar.getInstance().getTimeInMillis();
            response = getTestAPIResponse(xmlString);
            long secs = (Calendar.getInstance().getTimeInMillis() - start) / 1000;
            log.debug(secs + " secs");
            log.debug("" + response);
            prettyPrint(response.getDocumentElement(), System.out);
            i++;

        }

    }

    public void testOrderCreateBestWayExceptionRequest() throws Exception{


        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST backorder_rule=\"NOBACKORDER\" facility_rule=\"DC6\" group_name=\"bumbleride\" order_reference=\"12560851\"><SHIPPING_INFO address_one=\"#114 – 6951 – 72nd Street\" address_two=\"\" city=\"Delta\" company_name=\"PJM Distributions Inc\" country=\"Canada\" first_name=\"David\" last_name=\"Peterson\" phone=\"800-530-3930\" ship_type=\"BWY\" state=\"BC\" zip=\"V4G 0A2\"><BEST_WAY><CARRIER>OWD.1ST.LETTER</CARRIER><CARRIER>OWD_USPS_I_FIRST</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO paid=\"YES\" payment_type=\"CLIENT\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: Indie Twin Handle | Item: 21328426 | SupplierSKU: RP-IT-128 | \" line_number=\"21328426\" part_reference=\"P223139\" requested=\"4\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: 2009-2013 Indie/ Indie Twin Front Wheel (hook washer) | Item: 21328387 | SupplierSKU: RP-IIT-22 | \" line_number=\"21328387\" part_reference=\"P222190\" requested=\"10\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: Flite Adapter Bar with Belt | Item: 21328354 | SupplierSKU: RP-FLT-18 | \" line_number=\"21328354\" part_reference=\"P222009\" requested=\"5\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: Indie Twin Universal Car Seat Adapter | Item: 21328357 | SupplierSKU: ITU-11 | \" line_number=\"21328357\" part_reference=\"P222201\" requested=\"15\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: 2009-2015 Indie/ Indie Twin Rear Wheel | Item: 21328392 | SupplierSKU: RP-IIT-30 | \" line_number=\"21328392\" part_reference=\"P222012\" requested=\"5\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: 2014-2015 Indie/ Indie Twin Front Wheel | Item: 21328383 | SupplierSKU: RP-IIT-31 | \" line_number=\"21328383\" part_reference=\"P222013\" requested=\"10\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: Car Seat Strap (Snaps) | Item: 21328416 | SupplierSKU: RP-SA-11 | \" line_number=\"21328416\" part_reference=\"P222026\" requested=\"5\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: Indie Universal Car Seat Adapter | Item: 21328349 | SupplierSKU: IU-10 | \" line_number=\"21328349\" part_reference=\"P222082\" requested=\"15\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: 2013 Flite Chassis | Item: 21328329 | SupplierSKU: FT-500 | \" line_number=\"21328329\" part_reference=\"P222130\" requested=\"3\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: Indie 4 Handle | Item: 21328430 | SupplierSKU: RP-I4-07 | \" line_number=\"21328430\" part_reference=\"P223140\" requested=\"3\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: 2011 Flite Wheel - Rear | Item: 21328367 | SupplierSKU: RP-FLT-20 | \" line_number=\"21328367\" part_reference=\"P222055\" requested=\"7\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: Parent Pack Console | Item: 21328406 | SupplierSKU: PP-88 | \" line_number=\"21328406\" part_reference=\"P395943\" requested=\"25\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: 2013 Indie Chassis | Item: 21328324 | SupplierSKU: I-600 | \" line_number=\"21328324\" part_reference=\"P222008\" requested=\"5\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: Front Wheel Retrofit Kit | Item: 21328338 | SupplierSKU: RP-SA-63 | \" line_number=\"21328338\" part_reference=\"P222200\" requested=\"50\"/><LINE_ITEM customs_desc=\"Juvenile Products, Strollers and Accessories\" declared_value=\"0.01\" description=\"Title: 2011 Flite Wheel - Front | Item: 21328364 | SupplierSKU: RP-FLT-19 | \" line_number=\"21328364\" part_reference=\"P222179\" requested=\"7\"/><MESSAGE/><CUSTOM_VALUE name=\"ordertype\">wholesale</CUSTOM_VALUE></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);

        Document response;

        long start = Calendar.getInstance().getTimeInMillis();
        response = getTestAPIResponse(xmlString);
        long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
        log.debug(secs+" secs");
        log.debug(""+response);
        prettyPrint(response.getDocumentElement(), System.out);

    }


    public void testOrderCreateRequestPerf() throws Exception{

        ExecutorService executorService =
                new ThreadPoolExecutor(
                        1, // core thread pool size
                        10, // maximum thread pool size
                        1, // time to wait before resizing pool
                        TimeUnit.HOURS,
                        new ArrayBlockingQueue<Runnable>(100, true),
                        new ThreadPoolExecutor.AbortPolicy());


        // scanning loop: fake scanning
        for (int t=0;t<10;t++) {

                executorService.submit(new Runnable() {

                    public void run() {
                        log.debug("start "+Thread.currentThread().getId());
                        try {
                            Map<Long,Integer> timers ;
                            Map<Long,Integer> timersEver = new TreeMap<Long, Integer>();
                            Map<Long,Integer> timerstest = new TreeMap<Long, Integer>();

                            boolean flipper = true;
                            for(int i=0;i<100;i++)
                            {
                                String xmlString = "<OWD_API_REQUEST api_version=\"1.0\" client_id=\"481\" client_authorization=\""+OWDUtilities.encryptData("481")+"\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST order_reference=\"TEST"+Thread.currentThread().getId()+"\" order_source=\"Internet\" is_gift=\"NO\" backorder_rule=\"BACKORDER\" allow_duplicate=\"true\">" +
                                        "<SHIPPING_INFO last_name=\"van de Ruit\" first_name=\"Felicity\" company_name=\"Felicity van de Ruit\" address_one=\"Office 19, Mon Repos Building\" address_two=\"Newlands Shopping Centre\" city=\"Mount Vernon\" state=\"WA\" zip=\"98274\" country=\"USA\" phone=\"+263-4-746678\" email=\"test@test.com\" ship_type=\"BWY\" ship_cost=\"56.1000\"/><BILLING_INFO last_name=\"van de Ruit\" first_name=\"Felicity\" company_name=\"Felicity van de Ruit\" address_one=\"Office 19, Mon Repos Building\" address_two=\"Newlands Shopping Centre\" city=\"Mount Vernon\" state=\"WA\" zip=\"98274\" country=\"USA\" phone=\"+263-4-746678\" email=\"test@test.com\" tax_pct=\"\" po_number=\"\" paid=\"YES\" paid_date=\"2011-12-09 05:32:52\" payment_type=\"CLIENT\"/>" +
                                        "<BEST_WAY>\n" +
                                        "\t\t\t\t<CARRIER>OWD.1ST.LETTER</CARRIER>\n" +
                                        "\t\t\t\t<CARRIER>OWD.1ST.PRIORITY</CARRIER>\n" +
                                        "\t\t\t\t<CARRIER>UPS.GND</CARRIER>\n" +
                                        "\t\t\t</BEST_WAY><LINE_ITEM part_reference=\"gorkmorgk\" description=\"test\" requested=\"2\" cost=\"5.15\"/><COMMENTS/></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

                                String xmlString2 = "<OWD_API_REQUEST api_version=\"1.0\" client_id=\"481\" client_authorization=\""+OWDUtilities.encryptData("481")+"\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST order_reference=\"TEST"+Thread.currentThread().getId()+"\" order_source=\"Internet\" is_gift=\"NO\" backorder_rule=\"BACKORDER\" allow_duplicate=\"true\">" +
                                        "<SHIPPING_INFO last_name=\"van de Ruit\" first_name=\"Felicity\" company_name=\"Felicity van de Ruit\" address_one=\"Office 19, Mon Repos Building\" address_two=\"Newlands Shopping Centre\" city=\"Mount Vernon\" state=\"WA\" zip=\"98274\" country=\"USA\" phone=\"+263-4-746678\" email=\"test@test.com\" ship_type=\"BWY\" ship_cost=\"56.1000\"/><BILLING_INFO last_name=\"van de Ruit\" first_name=\"Felicity\" company_name=\"Felicity van de Ruit\" address_one=\"Office 19, Mon Repos Building\" address_two=\"Newlands Shopping Centre\" city=\"Mount Vernon\" state=\"WA\" zip=\"98274\" country=\"USA\" phone=\"+263-4-746678\" email=\"test@test.com\" tax_pct=\"\" po_number=\"\" paid=\"YES\" paid_date=\"2011-12-09 05:32:52\" payment_type=\"CLIENT\"/>" +
                                        "<BEST_WAY>\n" +
                                        "\t\t\t\t<CARRIER>OWD.1ST.LETTER</CARRIER>\n" +
                                        "\t\t\t\t<CARRIER>OWD.1ST.PRIORITY</CARRIER>\n" +
                                        "\t\t\t\t<CARRIER>UPS.GND</CARRIER>\n" +
                                        "\t\t\t</BEST_WAY><LINE_ITEM part_reference=\"gorkmorgk\" description=\"test\" requested=\"2\" cost=\"5.15\"/><COMMENTS/></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

                                Document response;

                                long start = Calendar.getInstance().getTimeInMillis();

                                response = getInternalAPIResponse(flipper?xmlString:xmlString2);
                                log.debug("mid "+Thread.currentThread().getId());

                                long secs = (Calendar.getInstance().getTimeInMillis()-start)/1000;
                              //  verifyAPIResponse( response);
                                prettyPrint(response.getDocumentElement(), System.out);

                                if(flipper) {timers = timersEver; }else{timers=timerstest;}
                                if(timers.containsKey(secs))
                                {
                                    timers.put(secs,timers.get(secs)+1);
                                }   else
                                {
                                    timers.put(secs,1);
                                }
                                if(flipper) {flipper= false;}else{flipper=true;}
                            }
                            log.debug("Ever:"+timersEver);
                            log.debug("Test:"+timerstest);
                            log.debug("end "+Thread.currentThread().getId());

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }


// ...

// wait for all of the executor threads to finish
            executorService.shutdown();
        log.debug("shutdown");
            try {
                if (!executorService.awaitTermination(60, TimeUnit.MINUTES)) {
                    // pool didn't terminate after the first try
                    executorService.shutdownNow();
                }


                if (!executorService.awaitTermination(60, TimeUnit.MINUTES)) {
                    // pool didn't terminate after the second try
                }
            } catch (InterruptedException ex) {
                log.debug("sdsdf");
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }


    }

    static void runTowelLogic(int bathtowels, int handtowels, int washtowels, int mailers, int smalls, int larges)
    {

        boolean match = false;

        Map<String, Integer> packmap = new HashMap<String, Integer>();
        packmap.put("P235257",0);
        packmap.put("P235256",0);
        packmap.put("P235258",0);
        packmap.put("P249819",0);
        packmap.put("P250693",0);
        packmap.put("P99494",0);
        packmap.put("P252860",0);

        int $bathtowels = bathtowels ;
        int $handtowels = handtowels;
        int $washtowels= washtowels;

        int $sections = (9*$bathtowels)+(7*$handtowels)+(3*$washtowels);

        while($sections>=31) {
            packmap.put("P235257", 1+ packmap.get("P235257"));
            packmap.put("P235256", 1+ packmap.get("P235256"));
            packmap.put("P252860", 1+ packmap.get("P252860"));
            $sections = $sections-56;
        }
        while($sections>=10) {
            packmap.put("P235258", 1+ packmap.get("P235258"));
            packmap.put("P249819", 1+ packmap.get("P249819"));
            packmap.put("P252860", 1+ packmap.get("P252860"));
            $sections = $sections-30;
        }
        while($sections>0) {
            packmap.put("P250693", 1+ packmap.get("P250693"));
            packmap.put("P99494", 2+ packmap.get("P99494"));
            packmap.put("P252860", 1+ packmap.get("P252860"));
            $sections = $sections-9;
        }

        if(mailers == packmap.get("P250693") && smalls == packmap.get("P235258") && larges == packmap.get("P235257"))
        {
            match = true;
        }
        System.out.println("*** "+bathtowels+" bath towels, "+handtowels+" hand towels, "+washtowels+" washcloths : "+(match?"PASS":"FAIL"));
if(!match){
    System.out.println("Mailers: "+packmap.get("P250693")+" (Expected "+mailers+")");

        System.out.println("Small Boxes: "+packmap.get("P235258")+" (Expected "+smalls+")");
        System.out.println("Large Boxes: "+packmap.get("P235257")+" (Expected "+larges+")");
}
    }

    public void testTowelLogic()
    {
        runTowelLogic(1,1,1,0,1,0);
        runTowelLogic(1,4,2,0,0,1);
        runTowelLogic(2,2,2,0,0,1);
        runTowelLogic(2,4,3,0,0,1);
        runTowelLogic(3,1,2,0,0,1);
        runTowelLogic(3,2,2,0,0,1);
        runTowelLogic(4,2,2,0,0,1);
        runTowelLogic(4,3,1,1,0,1);
        runTowelLogic(4,4,4,0,1,1);
        runTowelLogic(5,0,4,1,0,1);
        runTowelLogic(5,2,2,1,0,1);
        runTowelLogic(6,4,4,0,0,2);
        runTowelLogic(7,2,0,0,1,1);
        runTowelLogic(6,3,3,0,1,1);
        runTowelLogic(7,4,3,0,0,2);
        runTowelLogic(7,0,0,1,0,1);
        runTowelLogic(6,0,0,0,0,1);
        runTowelLogic(8,0,0,0,1,1);
        runTowelLogic(8,4,4,0,0,2);
        runTowelLogic(0,0,1,1,0,0);
        runTowelLogic(0,1,0,1,0,0);
        runTowelLogic(2,0,0,0,1,0);
        runTowelLogic(0,2,0,0,1,0);
        runTowelLogic(0,0,2,1,0,0);
        runTowelLogic(1,0,0,1,0,0);
        runTowelLogic(2,0,0,0,1,0);
        runTowelLogic(2,1,1,0,1,0);
        runTowelLogic(0,0,3,1,0,0);

    }

    public void testAllOKRequest() throws Exception {

            String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<OWD_API_REQUEST api_version=\"2.1\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\">" +
                    "<OWD_INVENTORY_REPORT_REQUEST>" +
                    "        <FILTER>" +
                    "            <TYPE>startDate</TYPE>" +
                    "            <VALUE>20160217</VALUE>" +
                    "        </FILTER>" +
                    "        <FILTER>" +
                    "            <TYPE>endDate</TYPE>" +
                    "            <VALUE>20160218</VALUE>" +
                    "        </FILTER>" +
                    "        <FILTER>" +
                    "            <TYPE>type</TYPE>" +
                    "            <VALUE>ALL</VALUE>" +
                    "        </FILTER>" +
                    "</OWD_INVENTORY_REPORT_REQUEST>" +
                    "</OWD_API_REQUEST>";

        //  xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
      //          "<OWD_API_REQUEST api_version=\"1.9\" client_id=\"457\" client_authorization=\"+kv+drc4hSI6znTmY90UMAM=\" testing=\"TRUE\"><OWD_SHIPPING_RATE_REQUEST><SHIPPING_ADDRESS><ADDRESS_1><![CDATA[30 West 100 South]]></ADDRESS_1><ADDRESS_2><![CDATA[]]></ADDRESS_2><CITY><![CDATA[Salem]]></CITY><REGION><![CDATA[UT]]></REGION><POSTCODE><![CDATA[423-2770?]]></POSTCODE><COUNTRYCODE><![CDATA[USA]]></COUNTRYCODE></SHIPPING_ADDRESS><ITEMS><ITEM sku=\"REGU-000-P-06\" qty=\"10\"/><ITEM sku=\"COND-000-F-01\" qty=\"24\"/></ITEMS><SHIPMETHODS><CODE><![CDATA[UPS.NDA]]></CODE><CODE><![CDATA[UPS.2DA]]></CODE><CODE><![CDATA[UPS.3DA]]></CODE><CODE><![CDATA[UPS.GND]]></CODE><CODE><![CDATA[OWD.1ST.PRIORITY]]></CODE><CODE><![CDATA[FDX.GND]]></CODE></SHIPMETHODS></OWD_SHIPPING_RATE_REQUEST></OWD_API_REQUEST>";


        log.debug(""+xmlString);
        Document response;

      // response = getInternalAPIResponse(xmlString);
        //verifyAPIResponse(response);
        response = getLiveAPIResponse(xmlString);
        verifyAPIResponse(response);
        prettyPrint(response.getDocumentElement(), System.out);


    }

    public void testForceBackorderRequest() throws Exception {

        String xmlString = "<?xml version=\"1.0\"?>\n" +
                "<OWD_API_REQUEST api_version=\"2.1\" " +
                "client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" " +
                "client_id=\"489\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST order_reference=\"118146xxx57\" " +
                "backorder_rule=\"NOBACKORDER\" group_name=\"fijiwater\" " +
                "facility_rule=\"DC6\"><SHIPPING_INFO first_name=\"AUBREY\" last_name=\"" +
                "GRAHAM\" address_one=\"5841 Round Meadow Road\" address_two=\"Gate Code " +
                "9988\" city=\"Hidden Hills\" state=\"CA\" zip=\"91302\" country=\"United " +
                "States\" phone=\"8186052115\" " +
                "ship_type=\"BWY\"><BEST_WAY><CARRIER>FDX.GND</CARRIER><CARRIER>FDX.HD</CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO " +
                "payment_type=\"CLIENT\" paid=\"YES\" /><LINE_ITEM line_number=\"17614968\" " +
                "part_reference=\"P206029\" description=\"Title: FIJI Water 330mL (Pack of " +
                "36) | Size:330ml (pack of 36)Item: 17614968 | SupplierSKU:" +
                "FIJI-Water-330mL | \" requested=\"18\" declared_value=\"219.00\" " +
                "customs_desc=\"unavailable\" /><LINE_ITEM line_number=\"17614969\" " +
                "part_reference=\"P206030\" description=\"Title: FIJI Water 500mL (Pack of " +
                "24) | Size:500ml (pack of 24)Item: 17614969 | SupplierSKU:" +
                "FIJI-Water-500mL | \" requested=\"16\" declared_value=\"165.00\" " +
                "customs_desc=\"unavailable\"" +
                "/><MESSAGE></MESSAGE></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


        log.debug(""+xmlString);
        Document response;

        // response = getInternalAPIResponse(xmlString);
        //verifyAPIResponse(response);
        response = getInternalAPIResponse(xmlString);
        verifyAPIResponse(response);

        prettyPrint(response.getDocumentElement(), System.out);

    }





     public void testOrderStatusByCreatedDate() throws Exception {

        Document response;
        String xmlString;

        xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"2.1\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("489") + "\" client_id=\"489\" " +
                "testing=\"FALSE\">" +
                "<OWD_ORDER_STATUS_REQUEST  clientOrderId=\"10192156xxxxxxxx\" />" +
                "</OWD_API_REQUEST>";

        response = getInternalAPIResponse(xmlString);
         log.debug(""+response);
        verifyAPIResponse(response);
         XPath xpath = XPathFactory.newInstance().newXPath();

         DOMSource source = new DOMSource(response);
                  StreamResult result = new StreamResult(System.out);
                  TransformerFactory tf = TransformerFactory.newInstance();
                  Transformer t = tf.newTransformer();
                //uncomment to print raw XML repsonse to console
                     t.transform(source, result);

         NodeList nodes = (NodeList) xpath.evaluate ("//SHIPMENT", response.getDocumentElement(),
                                   XPathConstants.NODESET);
         log.debug(""+response.getDocumentElement().getNodeName());
         log.debug("nodes:"+response.getChildNodes().getLength());
         log.debug("nodes:"+response.getChildNodes().item(0).getNodeName());
         log.debug("nodes:"+response.getChildNodes().getLength());
         log.debug("Got order count = "+nodes.getLength());
//         log.debug("Got order count2 = "+nodes.item(0).getChildNodes().getLength());
         log.debug(""+nodes.item(0).getNodeName());
       //  log.debug(""+nodes.item(1).getNodeName());

    }

    public void testOrderStatusRequest() throws Exception {

        Document response;
        String xmlString;

        xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\"><OWD_INVENTORY_UPDATE_REQUEST sku=\"P81116\"><MASTER_CASE_QTY>20</MASTER_CASE_QTY><CASE_QTY>10</CASE_QTY></OWD_INVENTORY_UPDATE_REQUEST></OWD_API_REQUEST>";

        log.debug(xmlString);
        response = getInternalAPIResponse(xmlString);
        log.debug(""+response);
        verifyAPIResponse(response);
        XPath xpath = XPathFactory.newInstance().newXPath();

        DOMSource source = new DOMSource(response);
        StreamResult result = new StreamResult(System.out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        //uncomment to print raw XML repsonse to console
        t.transform(source, result);

    }


    public void testOrderStatusWithNonUniqueOrderReference() throws Exception {

           Document response;
           String xmlString;

           xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                   "<OWD_API_REQUEST api_version=\"1.9\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\" testing=\"TRUE\"><OWD_ASN_CREATE_REQUEST><FACILITY_CODE>DC1</FACILITY_CODE><REFERENCE>1594</REFERENCE><PO_REFERENCE>1594</PO_REFERENCE><SHIPPER>SpiritHoods LLC</SHIPPER><CARRIER>Unknown</CARRIER><EXPECTED_DATE>20141107</EXPECTED_DATE><AUTORELEASE>1</AUTORELEASE><ASNITEMS><ASNITEM><ASNITEM_SKU>P69441</ASNITEM_SKU><ASNITEM_EXPECTED>311</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: 1 Core Red Wolf | SupplierSKU: RW01 | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P69451</ASNITEM_SKU><ASNITEM_EXPECTED>29</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: 3 Half Hoods Black Wolf | SupplierSKU: BW02 | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P69487</ASNITEM_SKU><ASNITEM_EXPECTED>6</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: 5 KIDS HOODS Kids Grey Wolf | SupplierSKU: K-GW01 | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P69488</ASNITEM_SKU><ASNITEM_EXPECTED>1</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: 5 KIDS HOODS Kids Leopard | SupplierSKU: K-LE01 | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P69489</ASNITEM_SKU><ASNITEM_EXPECTED>2</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: 5 KIDS HOODS Kids Snow Leopard Glitter | SupplierSKU: K-SL01-PGP | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P71259</ASNITEM_SKU><ASNITEM_EXPECTED>300</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: 1 Core Husky | SupplierSKU: HU01-P | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P75618</ASNITEM_SKU><ASNITEM_EXPECTED>10</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Kids Spicy Monkey | SupplierSKU: HK001U-0265-O | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P98726</ASNITEM_SKU><ASNITEM_EXPECTED>300</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Black Wolf Warrior | SupplierSKU: BW01-W | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P187136</ASNITEM_SKU><ASNITEM_EXPECTED>1</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Grey Wolf Jacket l | Size:lSupplierSKU: AA015U-0314-L | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P187137</ASNITEM_SKU><ASNITEM_EXPECTED>2</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Grey Wolf Jacket m | Size:mSupplierSKU: AA015U-0313-M | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P187138</ASNITEM_SKU><ASNITEM_EXPECTED>8</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Grey Wolf Jacket s | Size:sSupplierSKU: AA015U-0312-S | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P187144</ASNITEM_SKU><ASNITEM_EXPECTED>2</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Leopard Jacket m | Size:mSupplierSKU: AA015U-0310-M | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P187150</ASNITEM_SKU><ASNITEM_EXPECTED>2</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Panda Jacket l | Size:lSupplierSKU: AA015U-0308-L | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P187152</ASNITEM_SKU><ASNITEM_EXPECTED>3</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Panda Jacket s | Size:sSupplierSKU: AA015U-0306-S | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P187245</ASNITEM_SKU><ASNITEM_EXPECTED>9</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Black Cat | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P189047</ASNITEM_SKU><ASNITEM_EXPECTED>2</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Raccoon | SupplierSKU: HA001U-307-O | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P189063</ASNITEM_SKU><ASNITEM_EXPECTED>60</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Red Wolf Shawl | SupplierSKU: HA001U-309-O | </ASNITEM_DESCRIPTION></ASNITEM><ASNITEM><ASNITEM_SKU>P193508</ASNITEM_SKU><ASNITEM_EXPECTED>50</ASNITEM_EXPECTED><ASNITEM_DESCRIPTION>Title: Polar Bear | SupplierSKU: PB01 | </ASNITEM_DESCRIPTION></ASNITEM></ASNITEMS><NOTES></NOTES><GROUPNAME>G190V1977</GROUPNAME></OWD_ASN_CREATE_REQUEST></OWD_API_REQUEST>";

               response = getLiveAPIResponse(xmlString);
            log.debug(""+response);
           verifyAPIResponse(response);
            XPath xpath = XPathFactory.newInstance().newXPath();

            DOMSource source = new DOMSource(response);
                     StreamResult result = new StreamResult(System.out);
                     TransformerFactory tf = TransformerFactory.newInstance();
                     Transformer t = tf.newTransformer();
                   //uncomment to print raw XML repsonse to console
                        t.transform(source, result);

       }


    public void testInventoryCountAPI_1_0() throws Exception {


        Inventory item = Inventory.dbloadByPart("TestItemOWD0001", "112");
        item.addToAvailabilityAtFacility(10, FacilitiesManager.getLocationCodeForClient(112));
        Document response;
        Node itemNode;

        String xmlString;

        xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"1.0\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("112") + "\" client_id=\"112\" " +
                "testing=\"FALSE\">" +
                "<OWD_INVENTORY_COUNT_REQUEST " +
                " />" +
                "</OWD_API_REQUEST>";

        response = getAPIResponse(xmlString);
        verifyAPIResponse(response);
        itemNode = XPathAPI.selectSingleNode(response, "/OWD_API_RESPONSE/OWD_INVENTORY_COUNT_RESPONSE/COUNT[@part_reference='TestItemOWD0001']");
        Assert.assertNotNull("Test item not found!", itemNode);
        Assert.assertTrue("Quantity found did not match expected!", itemNode.getAttributes().getNamedItem("quantity_on_hand").getNodeValue().equals("10"));
        Assert.assertTrue("Attribute size != 2", itemNode.getAttributes().getLength() == 2);

        xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"1.0\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("112") + "\" client_id=\"112\" " +
                "testing=\"FALSE\">" +
                "<OWD_INVENTORY_COUNT_REQUEST part_reference=\"TestItemOWD0001\" " +
                ////    "show_active=\"0\" " +
                //     "show_backorders=\"1\"" +
                " />" +
                "</OWD_API_REQUEST>";

        response = getAPIResponse(xmlString);
        verifyAPIResponse(response);
        itemNode = XPathAPI.selectSingleNode(response, "/OWD_API_RESPONSE/OWD_INVENTORY_COUNT_RESPONSE/COUNT[@part_reference='TestItemOWD0001']");
        Assert.assertNotNull("Test item not found!", itemNode);
        Assert.assertTrue("Quantity found did not match expected!", itemNode.getAttributes().getNamedItem("quantity_on_hand").getNodeValue().equals("10"));
        Assert.assertTrue("Attribute size != 2", itemNode.getAttributes().getLength() == 2);


    }

    public void testInventoryCountAPI_2() throws Exception {

        Inventory item = Inventory.dbloadByPart("TestItemOWD0001", "112");
        item.addToAvailabilityAtFacility(10, FacilitiesManager.getLocationCodeForClient(112));

        item.is_auto_inventory = 1;
        item.dbupdate( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
        HibUtils.commit(HibernateSession.currentSession());

        Document response;
        Node itemNode;

        String xmlString;
        xmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"1.0\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("266") + "\" client_id=\"266\" " +
                "testing=\"FALSE\">" +
                "<OWD_INVENTORY_COUNT_REQUEST " +
                "show_active=\"1\" " +
                     "show_backorders=\"1\"" +
                " />" +
                "</OWD_API_REQUEST>";

        response = getInternalAPIResponse(xmlString);
        prettyPrint(response,System.out);

        verifyAPIResponse(response);

        xmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"2.0\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("266") + "\" client_id=\"266\" " +
                "testing=\"FALSE\">" +
                "<OWD_INVENTORY_COUNT_REQUEST " +
                "show_active=\"1\" " +
                     "show_backorders=\"1\"" +
                " />" +
                "</OWD_API_REQUEST>";

        response = getInternalAPIResponse(xmlString);
        prettyPrint(response,System.out);

        verifyAPIResponse(response);
      /*  itemNode = XPathAPI.selectSingleNode(response, "/OWD_API_RESPONSE/OWD_INVENTORY_COUNT_RESPONSE/COUNT[@part_reference='TestItemOWD0001']");
        Assert.assertNotNull("Test item not found!", itemNode);
        Assert.assertTrue("Quantity found did not match expected!", itemNode.getAttributes().getNamedItem("quantity_on_hand").getNodeValue().equals("99999"));
        Assert.assertTrue("Attribute size != 3", itemNode.getAttributes().getLength() == 2);
*/


    }

    public void testInventoryCountAPI_1_1() throws Exception {

        Inventory item = Inventory.dbloadByPart("TestItemOWD0001", "112");
        item.addToAvailabilityAtFacility(10, FacilitiesManager.getLocationCodeForClient(112));

        item.is_auto_inventory = 1;
        item.dbupdate( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection());
      HibUtils.commit(sess);

        Document response;
        Node itemNode;

        String xmlString;
        xmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"1.0\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("112") + "\" client_id=\"112\" " +
                "testing=\"FALSE\">" +
                "<OWD_INVENTORY_COUNT_REQUEST " +
                    "show_active=\"1\" " +
                //     "show_backorders=\"1\"" +
                " />" +
                "</OWD_API_REQUEST>";

        response = getAPIResponse(xmlString);
        verifyAPIResponse(response);
        itemNode = XPathAPI.selectSingleNode(response, "/OWD_API_RESPONSE/OWD_INVENTORY_COUNT_RESPONSE/COUNT[@part_reference='TestItemOWD0001']");
        Assert.assertNotNull("Test item not found!", itemNode);
        Assert.assertTrue("Quantity found did not match expected!", itemNode.getAttributes().getNamedItem("quantity_on_hand").getNodeValue().equals("99999"));
        Assert.assertTrue("Attribute size != 3", itemNode.getAttributes().getLength() == 2);

        xmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" +
                      "<OWD_API_REQUEST api_version=\"1.1\" " +
                      "client_authorization=\"" + OWDUtilities.encryptData("112") + "\" client_id=\"112\" " +
                      "testing=\"FALSE\">" +
                      "<OWD_INVENTORY_COUNT_REQUEST " +
                          "show_active=\"1\" " +
                           "show_backorders=\"1\"" +
                      " />" +
                      "</OWD_API_REQUEST>";

              response = getAPIResponse(xmlString);

              verifyAPIResponse(response);
              itemNode = XPathAPI.selectSingleNode(response, "/OWD_API_RESPONSE/OWD_INVENTORY_COUNT_RESPONSE/COUNT[@part_reference='TestItemOWD0001']");
              Assert.assertNotNull("Test item not found!", itemNode);
              Assert.assertTrue("Quantity found did not match expected!", itemNode.getAttributes().getNamedItem("quantity_on_hand").getNodeValue().equals("99999"));
              Assert.assertTrue("Attribute size != 4", itemNode.getAttributes().getLength() == 4);

    }

    public void testArbitraryRequest() throws Exception
    {
        
        Document response;

        Address shipAddress = new Address();

        log.debug(CountryNames.exists("China"));
        log.debug(CountryNames.exists("CN"));
        log.debug(CountryNames.exists(CountryNames.getCountryName("CHINA")));
        log.debug(CountryNames.getCountryName("CHINA"));
        log.debug(OrderUtilities.getCountryList().getRefForValue("CHINA"));

        String xmlString ="<?xml version=\"1.0\"?>" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_authorization=\"RiFbAUV9sGMyxS9qpMmPmgM=\" client_id=\"489\">" +
                "<OWD_ORDER_STATUS_REQUEST clientOrderId=\"545558.1\" summary=\"false\"        prefixSearch=\"false\" /></OWD_API_REQUEST>" +
                "";
        response = getInternalAPIResponse(xmlString);

        prettyPrint(response,System.out);
              verifyAPIResponse(response);

    }

      public void testPhotojojoOrderReleaseRequest() throws Exception
    {

        Document response;
          String xmlString;
        xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_id=\"494\" client_authorization=\""+OWDUtilities.encryptData("494")+"\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST order_reference=\"40391test\" order_source=\"Web\" facility_policy=\"DC1\" backorder_rule=\"BACKORDER\" hold_for_release=\"\" group_name=\"diamondcandles\"><BILLING_INFO last_name=\"Neff\" first_name=\"Jeremy\" company_name=\"\" address_one=\"238 W. 2nd N.\" address_two=\"\" city=\"Snowflake\" state=\"AZ\" zip=\"85937\" country=\"United States\" phone=\"9282435553\" email=\"wmes.jeremy@gmail.com\" tax_amount=\"7.18\" order_discount=\"0\" paid=\"YES\" po_number=\"\" paid_date=\"20140908\" payment_type=\"CLIENT\"/><SHIPPING_INFO last_name=\"Neff\" first_name=\"Jeremy\" company_name=\"\" address_one=\"238 W. 2nd N.\" address_two=\"\" city=\"Snowflake\" state=\"AZ\" zip=\"85937\" country=\"United States\" ship_cost=\"5.95\" insure_amount=\"\" declared_value=\"\" ship_type=\"OWD.1ST.PRIORITY\"/>" +
                "<LINE_ITEM part_reference=\"SLP-SAMP\" description=\"Extreme Endurance\" requested=\"2\" cost=\"42.95\" backordered=\"\" line_number=\"\"/><COMMENTS><![CDATA[]]></COMMENTS><MESSAGE><![CDATA[]]></MESSAGE><WAREHOUSENOTES><![CDATA[]]></WAREHOUSENOTES></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST> ";
        log.debug("REQUEST: "+xmlString);

            response = getInternalAPIResponse(xmlString);
        log.debug(""+response);
        verifyAPIResponse(response);
        prettyPrint(response,System.out);


    }

    public void testPhotojojoInventoryLevel() throws Exception
    {

        Document response;
          String xmlString;
        xmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"2.0\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("489") + "\" client_id=\"489\" " +
                "testing=\"FALSE\">" +
                "<OWD_INVENTORY_COUNT_REQUEST part_reference=\"P66226\" " +
                "show_active=\"1\" " +
                     "show_backorders=\"1\"" +
                " />" +
                "</OWD_API_REQUEST>";
        log.debug("REQUEST: "+xmlString);

            response = getLiveAPIResponse(xmlString);
        prettyPrint(response,System.out);
        verifyAPIResponse(response);


    }
    public void testInventoryCountAPI_1_2() throws Exception {

          Inventory item = Inventory.dbloadByPart("TestItemOWD0001", "112");
        item.addToAvailabilityAtFacility(10, FacilitiesManager.getLocationCodeForClient(112));


        Document response;
        Node itemNode;


        String xmlString;
        xmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"2.0\" " +
                "client_authorization=\""+OWDUtilities.encryptData("420")+"\" client_id=\"420\" " +
                "testing=\"FALSE\">" +
                "<OWD_INVENTORY_COUNT_REQUEST " +
                    "show_active=\"1\" " +
                     "show_backorders=\"1\" part_reference=\"P74704\"" +
                " />" +
                "</OWD_API_REQUEST>";
     //   log.debug(""+xmlString);
        response = getLiveAPIResponse(xmlString);
        log.debug(""+response);
        verifyAPIResponse(response);
        prettyPrint(response,System.out);
       /* itemNode = XPathAPI.selectSingleNode(response, "/OWD_API_RESPONSE/OWD_INVENTORY_COUNT_RESPONSE/COUNT[@part_reference='TestItemOWD0001']");
        Assert.assertNotNull("Test item not found!", itemNode);
        Assert.assertTrue("Quantity found did not match expected!", itemNode.getAttributes().getNamedItem("quantity_on_hand").getNodeValue().equals("10"));
        Assert.assertTrue("Attribute size != 8 :: "+itemNode.getAttributes().getLength(), itemNode.getAttributes().getLength() == 8);

           xmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>" +
                "<OWD_API_REQUEST api_version=\"1.2\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("112") + "\" client_id=\"112\" " +
                "testing=\"FALSE\">" +
                "<OWD_INVENTORY_COUNT_REQUEST part_reference=\"TestItemOWD0001\" " +
                    "show_active=\"1\" " +
                  //   "show_backorders=\"1\"" +
                " />" +
                "</OWD_API_REQUEST>";

        response = getLiveAPIResponse(xmlString);
        verifyAPIResponse(response);
        itemNode = XPathAPI.selectSingleNode(response, "/OWD_API_RESPONSE/OWD_INVENTORY_COUNT_RESPONSE/COUNT[@part_reference='TestItemOWD0001']");
        Assert.assertNotNull("Test item not found!", itemNode);
        Assert.assertTrue("Quantity found did not match expected!", itemNode.getAttributes().getNamedItem("quantity_on_hand").getNodeValue().equals("10"));
        Assert.assertTrue("Attribute size != 3", itemNode.getAttributes().getLength() == 3);*/
    }


    public void testNanBackorderItemRequest() throws Exception{
        String xmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?>"+
                "<OWD_API_REQUEST api_version=\"1.2\" " +
                "client_authorization=\"" + OWDUtilities.encryptData("489") + "\" client_id=\"489\" " +
                "testing=\"TRUE\">" +
                "<OWD_INVENTORY_CREATE_REQUEST><SKU>P99845</SKU><TITLE>Packing Tape</TITLE><TYPE>PHYSICAL</TYPE><SUPPLIER>missme</SUPPLIER><SUPPLIER_SKU>TAPEMISSME</SUPPLIER_SKU><CUSTOMS_DESC>unavailable</CUSTOMS_DESC><CASE_QTY>1</CASE_QTY><MASTER_CASE_QTY>1</MASTER_CASE_QTY><REORDER_ALERT_QTY>0</REORDER_ALERT_QTY><GROUPNAME>G309V2046</GROUPNAME><DEFAULT_FACILITY_CODE>DC6</DEFAULT_FACILITY_CODE></OWD_INVENTORY_CREATE_REQUEST></OWD_API_REQUEST>";
        Document response;

         response = getLiveAPIResponse(xmlString);
        //verifyAPIResponse(response);
        //response = getLiveAPIResponse(xmlString);
        verifyAPIResponse(response);
        prettyPrint(response,System.out);

    }

    public void testBarcodes() throws Exception {


        Document response;

        // response = getInternalAPIResponse(xmlString);
        //verifyAPIResponse(response);

        String  xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.7\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"><OWD_INVENTORY_UPDATE_REQUEST sku=\"Test Sku\"><SKU>Test Sku</SKU><INVENTORY_BARCODES>" +
                "<BARCODE>" +
                "<TYPE>UPC</TYPE>" +
                "<VALUE>052000707786</VALUE>" +
                "</BARCODE>" +
                "<BARCODE>" +
                "<TYPE>DSKU</TYPE>" +
                "<VALUE>D12345680293</VALUE>" +
                "</BARCODE>" +
                "</INVENTORY_BARCODES></OWD_INVENTORY_UPDATE_REQUEST></OWD_API_REQUEST>";

        response = getInternalAPIResponse(xmlString);

        prettyPrint(response.getDocumentElement(), System.out);
        verifyAPIResponse(response);

    }

    public void testDimensions() throws Exception {


        Document response;

        // response = getInternalAPIResponse(xmlString);
        //verifyAPIResponse(response);

        String  xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"2.7\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"><OWD_INVENTORY_UPDATE_REQUEST sku=\"Test Sku\"><SKU>Test Sku</SKU>" +
                "<LENGTH>" + 11 + "</LENGTH>" +
                "<WIDTH>" + 12 + "</WIDTH>" +
                "<HEIGHT>" + 10 + "</HEIGHT>" +
                "</OWD_INVENTORY_UPDATE_REQUEST></OWD_API_REQUEST>";

        response = getInternalAPIResponse(xmlString);

        prettyPrint(response.getDocumentElement(), System.out);
        verifyAPIResponse(response);

    }

}

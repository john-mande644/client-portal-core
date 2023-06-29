package com.owd.test.web.api

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.core.OWDUtilities
import org.apache.xpath.XPathAPI
import org.w3c.dom.Document
import org.w3c.dom.NodeList

import javax.xml.parsers.DocumentBuilderFactory

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/6/11
 * Time: 9:19 AM
 * To change this template use File | Settings | File Templates.
 */
class OwdOrderStatusApiTest extends ApiTestSuiteHarness {

      public OwdOrderStatusApiTest(String test) {
            super(test);
        }

        @Override
        protected void setUp() {
            System.setProperty("com.owd.environment","dev");
           // log.debug("setup");
            super.setUp()    //To change body of overridden methods use File | Settings | File Templates.

            HibUtils.commit(HibernateSession.currentSession());
        }

        @Override
        protected void tearDown() {

            //  clearSKU('112',testKitSKU)
            HibUtils.commit(HibernateSession.currentSession());

            super.tearDown()    //To change body of overridden methods use File | Settings | File Templates.
        }


    def void testShippedOrderStatus() throws Exception {


        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def getOrderStatusRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version: '2.6', client_authorization: OWDUtilities.encryptData("477"), client_id: '477', testing: 'FALSE') {
                        OWD_ORDER_STATUS_REQUEST(clientOrderId:'18409734') {
                        }
                    }
                }


        println "sending"
        println builder.bind(getOrderStatusRequest).toString()

        Document response = getTestAPIResponse(builder.bind(getOrderStatusRequest).toString());
        verifyAPIResponse((Document) response);

        prettyPrint(response.getDocumentElement(), System.out);

    }

    void testPagedRequestReturnsPagedResponse() throws Exception {


        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def getOrderStatusRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version: '2.6', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                        OWD_ORDER_STATUS_PAGED_REQUEST( shippedDate:'20200813', pageSize : '2', pageIndex : '1') {
                        }
                    }
                }


        println "sending"
        println builder.bind(getOrderStatusRequest).toString()

        Document response = getInternalAPIResponse(builder.bind(getOrderStatusRequest).toString());
        verifyAPIResponse((Document) response);

        validateOrdersCount(response, 2);
        validateDocumentTotalCount(response, 4,2,1);
        prettyPrint(response.getDocumentElement(), System.out);

    }

    void testOutOfRangePageIndexReturnsErrorsContainsPagingInfo() throws Exception {


        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def byShipDateRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version: '2.6', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                        OWD_ORDER_STATUS_PAGED_REQUEST( shippedDate:'20200813', pageSize : '2', pageIndex : '12') {
                        }
                    }
                }


        println "sending"
        println builder.bind(byShipDateRequest).toString()

        Document response = getInternalAPIResponse(builder.bind(byShipDateRequest).toString());
        validateResponseErrorContainsPagingInfo(response,4,2,12);
        prettyPrint(response.getDocumentElement(), System.out);
    }

    void testOutOfRangePageWithCreatedDateIndexReturnsErrorsContainsPagingInfo() throws Exception {


        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def byShipDateRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version: '2.6', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                        OWD_ORDER_STATUS_PAGED_REQUEST( createdDate:'20200813', pageSize : '7', pageIndex : '72') {
                        }
                    }
                }


        println "sending"
        println builder.bind(byShipDateRequest).toString()

        Document response = getInternalAPIResponse(builder.bind(byShipDateRequest).toString());
        validateResponseErrorContainsPagingInfo(response,4,7,72);
        prettyPrint(response.getDocumentElement(), System.out);
    }

    void testOutOfRangePageWithOrderIdReturnsErrorsContainsPagingInfo() throws Exception {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def byShipDateRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version: '2.6', client_authorization: OWDUtilities.encryptData("112"), client_id: '112', testing: 'FALSE') {
                        OWD_ORDER_STATUS_PAGED_REQUEST( clientOrderId: '129746088', pageSize : '5', pageIndex : '3') {
                        }
                    }
                }


        println "sending"
        println builder.bind(byShipDateRequest).toString()

        Document response = getInternalAPIResponse(builder.bind(byShipDateRequest).toString());
        validateResponseErrorContainsPagingInfo(response,1,5,3);
        prettyPrint(response.getDocumentElement(), System.out);
    }

    void  testXML() throws  Exception {
        Document response = getResponse();

        validateDocumentTotalCount(response, 10,5,1);
        validateOrdersCount(response, 4);
    }


    Document getResponse() {
        org.w3c.dom.Document doc = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        javax.xml.parsers.DocumentBuilder  builder = factory.newDocumentBuilder();

        String xmlText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_RESPONSE results=\"SUCCESS\"  totalCount=\"10\" pageSize=\"5\"  pageIndex=\"1\">\n" +
                "  <OWD_ORDER_STATUS_RESPONSE clientOrderId=\"129746088\" groupName=\"\" orderId=\"29746088\" packslip_template=\"\" paymentStatus=\"CLIENTMANAGED\" shipFromPolicy=\"DC1\" shipMethod=\"Economy\" shipMethodCode=\"COM_OWD_FLATRATE_ECONOMY\" status=\"BACKORDER\">\n" +
                "    <LINE assigned=\"0\" backordered=\"0\" clientSKU=\"\" cost=\"0.0\" line_number=\"\" requested=\"1\"/>\n" +
                "    <SHIPMENT shipDate=\"20200813\" shipVia=\"Economy\" shipViaCode=\"COM_OWD_FLATRATE_ECONOMY\" shippedFromFacilityCode=\"DC1\">\n" +
                "      <PACKAGE depth_in=\"6\" height_in=\"1\" packageNumber=\"1\" ratedCost=\"0.08\" weightLbs=\"212.0\" width_in=\"6\">\n" +
                "        <ITEM clientSKU=\"\" quantity=\"0\"/>\n" +
                "      </PACKAGE>\n" +
                "    </SHIPMENT>\n" +
                "    <FIELDS>\n" +
                "      <BILL_ADDRESS_ONE>Test2</BILL_ADDRESS_ONE>\n" +
                "      <BILL_ADDRESS_TWO>Test3</BILL_ADDRESS_TWO>\n" +
                "      <BILL_CITY>Test4</BILL_CITY>\n" +
                "      <BILL_COMPANY>Test1</BILL_COMPANY>\n" +
                "      <BILL_COUNTRY>USA</BILL_COUNTRY>\n" +
                "      <BILL_EMAIL/>\n" +
                "      <BILL_NAME>Test</BILL_NAME>\n" +
                "      <BILL_PHONE/>\n" +
                "      <BILL_PO/>\n" +
                "      <BILL_POSTCODE/>\n" +
                "      <BILL_STATE/>\n" +
                "      <FACILITY_RULE/>\n" +
                "      <GROUP_NAME/>\n" +
                "      <ORDER_COMMENT/>\n" +
                "      <ORDER_WAREHOUSENOTES/>\n" +
                "      <SHIP_ADDRESS_ONE>Test2</SHIP_ADDRESS_ONE>\n" +
                "      <SHIP_ADDRESS_TWO>Test3</SHIP_ADDRESS_TWO>\n" +
                "      <SHIP_CITY>Test4</SHIP_CITY>\n" +
                "      <SHIP_COMPANY>Test1</SHIP_COMPANY>\n" +
                "      <SHIP_COUNTRY>USA</SHIP_COUNTRY>\n" +
                "      <SHIP_EMAIL/>\n" +
                "      <SHIP_NAME>Test</SHIP_NAME>\n" +
                "      <SHIP_PHONE/>\n" +
                "      <SHIP_POSTCODE/>\n" +
                "      <SHIP_STATE/>\n" +
                "      <SHIPPING_METHOD>Economy</SHIPPING_METHOD>\n" +
                "      <TOTAL_DISCOUNT>0.0</TOTAL_DISCOUNT>\n" +
                "      <TOTAL_SHIPHANDLING>0.0</TOTAL_SHIPHANDLING>\n" +
                "      <TOTAL_TAX>0.0</TOTAL_TAX>\n" +
                "      <TOTAL_COST>0.0</TOTAL_COST>\n" +
                "      <PACKING_INSTRUCTIONS/>\n" +
                "      <ORDER_VIEW_URL><![CDATA[https://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&mgr=Orders&ordermgr=edit&oid=20799107]]></ORDER_VIEW_URL>\n" +
                "    </FIELDS>\n" +
                "  </OWD_ORDER_STATUS_RESPONSE>\n" +
                "  <OWD_ORDER_STATUS_RESPONSE clientOrderId=\"129746089\" groupName=\"\" orderId=\"29746089\" packslip_template=\"\" paymentStatus=\"CLIENTMANAGED\" shipFromPolicy=\"DC1\" shipMethod=\"UPS Ground\" shipMethodCode=\"UPS.GND\" status=\"BACKORDER\">\n" +
                "    <LINE assigned=\"0\" backordered=\"0\" clientSKU=\"0.0ojiyyr6unn\" cost=\"0.0\" line_number=\"\" requested=\"1\"/>\n" +
                "    <SHIPMENT shipDate=\"20200813\" shipVia=\"UPS Ground\" shipViaCode=\"UPS.GND\" shippedFromFacilityCode=\"DC1\">\n" +
                "      <PACKAGE depth_in=\"6\" height_in=\"1\" packageNumber=\"1\" ratedCost=\"0.08\" sscc=\"008590390046591636\" trackingNumber=\"000000000000000\" weightLbs=\"2.0\" width_in=\"6\">\n" +
                "        <ITEM clientSKU=\"0.0ojiyyr6unn\" quantity=\"0\"/>\n" +
                "      </PACKAGE>\n" +
                "    </SHIPMENT>\n" +
                "    <FIELDS>\n" +
                "      <BILL_ADDRESS_ONE>Ground Test</BILL_ADDRESS_ONE>\n" +
                "      <BILL_ADDRESS_TWO>Ground Test</BILL_ADDRESS_TWO>\n" +
                "      <BILL_CITY>Ground Test</BILL_CITY>\n" +
                "      <BILL_COMPANY>Ground Test</BILL_COMPANY>\n" +
                "      <BILL_COUNTRY>USA</BILL_COUNTRY>\n" +
                "      <BILL_EMAIL/>\n" +
                "      <BILL_NAME>Ground Test</BILL_NAME>\n" +
                "      <BILL_PHONE>rrrr</BILL_PHONE>\n" +
                "      <BILL_PO/>\n" +
                "      <BILL_POSTCODE>2222</BILL_POSTCODE>\n" +
                "      <BILL_STATE/>\n" +
                "      <FACILITY_RULE/>\n" +
                "      <GROUP_NAME/>\n" +
                "      <ORDER_COMMENT/>\n" +
                "      <ORDER_WAREHOUSENOTES/>\n" +
                "      <SHIP_ADDRESS_ONE>Ground Test</SHIP_ADDRESS_ONE>\n" +
                "      <SHIP_ADDRESS_TWO>Ground Test</SHIP_ADDRESS_TWO>\n" +
                "      <SHIP_CITY>Ground Test</SHIP_CITY>\n" +
                "      <SHIP_COMPANY>Ground Test</SHIP_COMPANY>\n" +
                "      <SHIP_COUNTRY>USA</SHIP_COUNTRY>\n" +
                "      <SHIP_EMAIL/>\n" +
                "      <SHIP_NAME>Ground Test</SHIP_NAME>\n" +
                "      <SHIP_PHONE>rrrr</SHIP_PHONE>\n" +
                "      <SHIP_POSTCODE>2222</SHIP_POSTCODE>\n" +
                "      <SHIP_STATE/>\n" +
                "      <SHIPPING_METHOD>UPS Ground</SHIPPING_METHOD>\n" +
                "      <TOTAL_DISCOUNT>0.0</TOTAL_DISCOUNT>\n" +
                "      <TOTAL_SHIPHANDLING>0.0</TOTAL_SHIPHANDLING>\n" +
                "      <TOTAL_TAX>0.0</TOTAL_TAX>\n" +
                "      <TOTAL_COST>0.0</TOTAL_COST>\n" +
                "      <PACKING_INSTRUCTIONS/>\n" +
                "      <ORDER_VIEW_URL><![CDATA[https://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&mgr=Orders&ordermgr=edit&oid=20799108]]></ORDER_VIEW_URL>\n" +
                "    </FIELDS>\n" +
                "  </OWD_ORDER_STATUS_RESPONSE>\n" +
                "  <OWD_ORDER_STATUS_RESPONSE clientOrderId=\"129746096\" groupName=\"\" orderId=\"29746096\" packslip_template=\"\" paymentStatus=\"CLIENTMANAGED\" shipFromPolicy=\"DC1\" shipMethod=\"Ecomony\" shipMethodCode=\"COM_OWD_FLATRATE_ECONOMY\" status=\"BACKORDER\">\n" +
                "    <LINE assigned=\"0\" backordered=\"0\" clientSKU=\"0.0ojiyyr6unn\" cost=\"0.0\" line_number=\"\" requested=\"1\"/>\n" +
                "    <SHIPMENT shipDate=\"20200813\" shipVia=\"Ecomony\" shipViaCode=\"COM_OWD_FLATRATE_ECONOMY\" shippedFromFacilityCode=\"DC1\">\n" +
                "      <PACKAGE depth_in=\"6\" height_in=\"1\" packageNumber=\"1\" ratedCost=\"0.08\" weightLbs=\"212.0\" width_in=\"6\">\n" +
                "        <ITEM clientSKU=\"0.0ojiyyr6unn\" quantity=\"0\"/>\n" +
                "      </PACKAGE>\n" +
                "    </SHIPMENT>\n" +
                "    <FIELDS>\n" +
                "      <BILL_ADDRESS_ONE>test</BILL_ADDRESS_ONE>\n" +
                "      <BILL_ADDRESS_TWO>test</BILL_ADDRESS_TWO>\n" +
                "      <BILL_CITY>test</BILL_CITY>\n" +
                "      <BILL_COMPANY>test</BILL_COMPANY>\n" +
                "      <BILL_COUNTRY>USA</BILL_COUNTRY>\n" +
                "      <BILL_EMAIL/>\n" +
                "      <BILL_NAME>test</BILL_NAME>\n" +
                "      <BILL_PHONE/>\n" +
                "      <BILL_PO/>\n" +
                "      <BILL_POSTCODE>111</BILL_POSTCODE>\n" +
                "      <BILL_STATE/>\n" +
                "      <FACILITY_RULE/>\n" +
                "      <GROUP_NAME/>\n" +
                "      <ORDER_COMMENT/>\n" +
                "      <ORDER_WAREHOUSENOTES/>\n" +
                "      <SHIP_ADDRESS_ONE>test</SHIP_ADDRESS_ONE>\n" +
                "      <SHIP_ADDRESS_TWO>test</SHIP_ADDRESS_TWO>\n" +
                "      <SHIP_CITY>test</SHIP_CITY>\n" +
                "      <SHIP_COMPANY>test</SHIP_COMPANY>\n" +
                "      <SHIP_COUNTRY>USA</SHIP_COUNTRY>\n" +
                "      <SHIP_EMAIL/>\n" +
                "      <SHIP_NAME>test</SHIP_NAME>\n" +
                "      <SHIP_PHONE/>\n" +
                "      <SHIP_POSTCODE>111</SHIP_POSTCODE>\n" +
                "      <SHIP_STATE/>\n" +
                "      <SHIPPING_METHOD>Ecomony</SHIPPING_METHOD>\n" +
                "      <TOTAL_DISCOUNT>0.0</TOTAL_DISCOUNT>\n" +
                "      <TOTAL_SHIPHANDLING>0.0</TOTAL_SHIPHANDLING>\n" +
                "      <TOTAL_TAX>0.0</TOTAL_TAX>\n" +
                "      <TOTAL_COST>0.0</TOTAL_COST>\n" +
                "      <PACKING_INSTRUCTIONS/>\n" +
                "      <ORDER_VIEW_URL><![CDATA[https://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&mgr=Orders&ordermgr=edit&oid=20799115]]></ORDER_VIEW_URL>\n" +
                "    </FIELDS>\n" +
                "  </OWD_ORDER_STATUS_RESPONSE>\n" +
                "  <OWD_ORDER_STATUS_RESPONSE clientOrderId=\"129746097\" groupName=\"\" orderId=\"29746097\" packslip_template=\"\" paymentStatus=\"CLIENTMANAGED\" shipFromPolicy=\"DC1\" shipMethod=\"USPS Media Mail Single-Piece\" shipMethodCode=\"OWD.4TH.BOOK\" status=\"BACKORDER\">\n" +
                "    <LINE assigned=\"0\" backordered=\"0\" clientSKU=\"0.0t81gasx6f3g\" cost=\"0.0\" line_number=\"\" requested=\"1\"/>\n" +
                "    <SHIPMENT shipDate=\"20200813\" shipVia=\"USPS Media Mail Single-Piece\" shipViaCode=\"OWD.4TH.BOOK\" shippedFromFacilityCode=\"DC1\">\n" +
                "      <PACKAGE depth_in=\"6\" height_in=\"1\" packageNumber=\"1\" ratedCost=\"0.08\" weightLbs=\"212.0\" width_in=\"6\">\n" +
                "        <ITEM clientSKU=\"0.0t81gasx6f3g\" quantity=\"0\"/>\n" +
                "      </PACKAGE>\n" +
                "    </SHIPMENT>\n" +
                "    <FIELDS>\n" +
                "      <BILL_ADDRESS_ONE>Test USPS Media Mail</BILL_ADDRESS_ONE>\n" +
                "      <BILL_ADDRESS_TWO>Test USPS Media Mail</BILL_ADDRESS_TWO>\n" +
                "      <BILL_CITY>Test USPS Media Mail</BILL_CITY>\n" +
                "      <BILL_COMPANY>Test USPS Media Mail</BILL_COMPANY>\n" +
                "      <BILL_COUNTRY>USA</BILL_COUNTRY>\n" +
                "      <BILL_EMAIL/>\n" +
                "      <BILL_NAME>Test USPS Media Mail</BILL_NAME>\n" +
                "      <BILL_PHONE/>\n" +
                "      <BILL_PO/>\n" +
                "      <BILL_POSTCODE>3242</BILL_POSTCODE>\n" +
                "      <BILL_STATE/>\n" +
                "      <FACILITY_RULE/>\n" +
                "      <GROUP_NAME/>\n" +
                "      <ORDER_COMMENT/>\n" +
                "      <ORDER_WAREHOUSENOTES/>\n" +
                "      <SHIP_ADDRESS_ONE>Test USPS Media Mail</SHIP_ADDRESS_ONE>\n" +
                "      <SHIP_ADDRESS_TWO>Test USPS Media Mail</SHIP_ADDRESS_TWO>\n" +
                "      <SHIP_CITY>Test USPS Media Mail</SHIP_CITY>\n" +
                "      <SHIP_COMPANY>Test USPS Media Mail</SHIP_COMPANY>\n" +
                "      <SHIP_COUNTRY>USA</SHIP_COUNTRY>\n" +
                "      <SHIP_EMAIL/>\n" +
                "      <SHIP_NAME>Test USPS Media Mail</SHIP_NAME>\n" +
                "      <SHIP_PHONE/>\n" +
                "      <SHIP_POSTCODE>3242</SHIP_POSTCODE>\n" +
                "      <SHIP_STATE/>\n" +
                "      <SHIPPING_METHOD>USPS Media Mail Single-Piece</SHIPPING_METHOD>\n" +
                "      <TOTAL_DISCOUNT>0.0</TOTAL_DISCOUNT>\n" +
                "      <TOTAL_SHIPHANDLING>0.0</TOTAL_SHIPHANDLING>\n" +
                "      <TOTAL_TAX>0.0</TOTAL_TAX>\n" +
                "      <TOTAL_COST>0.0</TOTAL_COST>\n" +
                "      <PACKING_INSTRUCTIONS/>\n" +
                "      <ORDER_VIEW_URL><![CDATA[https://service.owd.com/webapps/extranet/extranet.jsp?act=cngMgr&mgr=Orders&ordermgr=edit&oid=20799116]]></ORDER_VIEW_URL>\n" +
                "    </FIELDS>\n" +
                "  </OWD_ORDER_STATUS_RESPONSE>\n" +
                "</OWD_API_RESPONSE>";

        doc = builder.parse(new java.io.ByteArrayInputStream(xmlText.getBytes("UTF-8")));
        return  doc;
    }

    void validateDocumentTotalCount(Document response, int totalCount, int pageSize, int pageIndex) {
        String actualTotalCount;
        String actualPageSize;
        String actualPageIndex;
        try{
            actualTotalCount = ""+ XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("totalCount").getNodeValue();
            actualPageSize = ""+ XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("pageSize").getNodeValue();
            actualPageIndex = ""+ XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("pageIndex").getNodeValue();
        }catch (Exception ex)
        {
        }
        assertEquals(totalCount.toString(),actualTotalCount);
        assertEquals(pageSize.toString(),actualPageSize);
        assertEquals(pageIndex.toString(),actualPageIndex);
    }

    void validateOrdersCount(Document document, int ordersCount) {
        int nodesCount = 0;
        try{
            nodesCount = XPathAPI.selectNodeList(document, "OWD_API_RESPONSE/OWD_ORDER_STATUS_RESPONSE").length;
        }catch (Exception ex)
        {
        }
        assertEquals(ordersCount,nodesCount);
    }

    void validateResponseErrorContainsString(Document document, String s) throws  Exception {
        String errorMessage = XPathAPI.selectSingleNode(document, "OWD_API_RESPONSE")
                .getAttributes().getNamedItem("error_response");
        boolean  containsString = errorMessage.contains(s);

        assertEquals(true, containsString);
    }

    void validateResponseErrorContainsPagingInfo(Document document, int totalCount, int pageSize, int pageIndex) {

        String errorMessage = XPathAPI.selectSingleNode(document, "OWD_API_RESPONSE")
                .getAttributes().getNamedItem("error_response");

        boolean  containsPageSize = errorMessage.contains("pageSize " + pageSize);
        boolean  containsPageIndex = errorMessage.contains("pageIndex " + pageIndex);
        boolean  containsTotalCount = errorMessage.contains("totalCount " + totalCount);
        assertEquals(true, containsPageSize);
        assertEquals(true, containsPageIndex);
        assertEquals(true, containsTotalCount);
    }
}

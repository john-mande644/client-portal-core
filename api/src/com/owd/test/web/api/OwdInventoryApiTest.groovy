package com.owd.test.web.api

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.w3c.dom.Document
import com.owd.core.OWDUtilities
import com.owd.core.managers.InventoryManager
import com.owd.core.business.order.LineItem
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.HibUtils

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/28/11
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
class OwdInventoryApiTest  extends ApiTestSuiteHarness {
    private final static Logger log =  LogManager.getLogger();

    public OwdInventoryApiTest(String test) {
          super(test);
      }


    private void clearSKU(String clientId, String Sku)
    {
          while(LineItem.SKUExists(clientId,Sku))
        {
            InventoryManager.deleteOwdInventory(InventoryManager.getOwdInventoryForClientAndSku(clientId,Sku))
        }
    }

    @Override
    protected void setUp() {
        log.debug("setup");
        super.setUp()    //To change body of overridden methods use File | Settings | File Templates.
     /*   clearSKU('112','TEST_OWD_CREATE_REQUEST2VK')
         clearSKU('112','TEST_OWD_CREATE_REQUESTV')
         clearSKU('112','TEST_OWD_CREATE_REQUESTPS')
         clearSKU('112','TEST_OWD_CREATE_REQUESTP')
         clearSKU('112',testKitSKU)
        HibUtils.commit(HibernateSession.currentSession()) ;*/
    }

    @Override
    protected void tearDown() {
       /*  clearSKU('112','TEST_OWD_CREATE_REQUEST2VK')
         clearSKU('112','TEST_OWD_CREATE_REQUESTV')
         clearSKU('112','TEST_OWD_CREATE_REQUESTPS')
         clearSKU('112','TEST_OWD_CREATE_REQUESTP')*/
       //  clearSKU('112',testKitSKU)
        HibUtils.commit(HibernateSession.currentSession()) ;

        super.tearDown()    //To change body of overridden methods use File | Settings | File Templates.
    }


    def void testCreateSKUs() throws Exception
    {


         def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("369"), client_id:'369', testing:'TRUE') {
                        OWD_INVENTORY_CREATE_REQUEST() {
                            SKU('0101NT-ES3Px')
                            TYPE('KIT')
                            TITLE('Mastering Leadership Series - Spanish Bundle: Volumes 1-3')
                           // IMAGE_URL('http://www.owd.com/images/logo1.png')
                           // THUMB_URL('http://www.owd.com/images/logo1.png')
                           // WEB_URL('http://www.owd.com/')
                          //  GROUPNAME('candletime')
                             UNIT_PRICE('59.99')
                            UNIT_COST('0.01')
                             SUPPLIER('SMV Media')
                            WEIGHT('0.6')
                           // DEFAULT_FACILITY_CODE('DC6')
                            DESCRIPTION('Includes Volume 1: People Training for Dogs, Volume 2: Be The Pack Leader, and Volume 3: Your New Dog from Cesar Millan\'s Mastering Leadership Series. Available in English and Spanish.')
                            COMPONENTS()
                                    {
                                        COMPONENT()
                                                {
                                                    COMPONENT_SKU("0101NT-PTD-ES")
                                                    COMPONENT_QTY("1")
                                                }
                                        COMPONENT()
                                                {
                                                    COMPONENT_SKU("0101NT-BPL-ES")
                                                    COMPONENT_QTY("1")
                                                }
                                        COMPONENT()
                                                {
                                                    COMPONENT_SKU("0101NT-YND-ES")
                                                    COMPONENT_QTY("1")
                                                }
                                    }
                        }
            }
        }

        println "sending"
        println builder.bind(kitItemCreateRequest).toString()

        Document  response = getInternalAPIResponse(builder.bind(kitItemCreateRequest).toString());
        verifyAPIResponse((Document)response);

     /*   def physicalItemCreateRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("112"), client_id:'112', testing:'FALSE') {
                        OWD_INVENTORY_CREATE_REQUEST() {
                            SKU('TEST_OWD_CREATE_REQUESTP')
                            TYPE('PHYSICAL')
                            TITLE('Rock, Paper, Scissors, Lizard, Spock: A Strategy Guide')
                            SUPPLIER('SUPPLIER')
                            SUPPLIER_SKU('SUPPLIER_SKU')
                            UNIT_PRICE('29.95')
                            UNIT_COST('0.01')
                            DESCRIPTION('DESCRIPTION')
                            COLOR('COLOR')
                            SIZE('SIZE')
                            REORDER_ALERT_QTY('3')
                            WEIGHT('3.21')
                            CUSTOMS_DESC('CUSTOMS_DESC')
                            KEYWORD('TAG STUFF')
                            CUSTOMS_VALUE('3.32')
                            PACKING_INSTRUCTIONS('PACKING_INSTRUCTIONS')
                            NOTES('NOTES')
                            ACTIVE('0')

                        }
            }
        }


        println "sending"
        println builder.bind(physicalItemCreateRequest).toString()

        response = getLiveAPIResponse(builder.bind(physicalItemCreateRequest).toString());
        verifyAPIResponse((Document)response);*/
        prettyPrint(response.getDocumentElement(),System.out);

    }

      def void testViewAllSKUs() throws Exception
    {

        System.setProperty("com.owd.environment","test");
         def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def generalStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'2.0', client_authorization:OWDUtilities.encryptData("55"), client_id:'55', testing:'FALSE') {
                        OWD_INVENTORY_STATUS_REQUEST() {
                        }
            }
        }

        println "sending"
        println builder.bind(generalStatusRequest).toString()

        Document  response = getInternalAPIResponse(builder.bind(generalStatusRequest).toString());
        verifyAPIResponse((Document)response);
          prettyPrint(response.getDocumentElement(),System.out);
    }

       def void testViewFilterOneSKU() throws Exception
    {

      //  createTestKitSKU()

         def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def generalStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'2.0', client_authorization:OWDUtilities.encryptData("489"), client_id:'489', testing:'FALSE') {
                        OWD_INVENTORY_REPORT_REQUEST() {
                            FILTER(){
                                TYPE('type')
                                VALUE('RETURN')
                            }
                            FILTER(){
                                TYPE('startDate')
                                VALUE('20151216')
                            }
                        }
            }
        }

        println "sending"
        println builder.bind(generalStatusRequest).toString()

        Document  response = getInternalAPIResponse(builder.bind(generalStatusRequest).toString());

          prettyPrint(response.getDocumentElement(),System.out);
        verifyAPIResponse((Document)response);

     //   assertEquals("Returned Count != 1",1,new Integer(new XmlParser().parseText(printNodeToString(response.getDocumentElement())).OWD_INVENTORY_STATUS_RESPONSE.COUNT.text()))

    }

         def void testViewFilterSkuContains() throws Exception
    {

         createTestKitSKU()

         def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def generalStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("112"), client_id:'112', testing:'FALSE') {
                        OWD_INVENTORY_STATUS_REQUEST() {
                            FILTER(){
                                TYPE('skuContains')
                                VALUE('TEST')
                            }
                            FILTER(){
                                TYPE('containsComponent')
                                VALUE(testKitSKU)
                            }
                        }
            }
        }

        println "sending"
        println builder.bind(generalStatusRequest).toString()

        Document  response = getInternalAPIResponse(builder.bind(generalStatusRequest).toString());
      //  prettyPrint(response.getDocumentElement(),System.out);
      verifyAPIResponse((Document)response);

      assertTrue("Returned Count <2",1<new Integer(new XmlParser().parseText(printNodeToString(response.getDocumentElement())).OWD_INVENTORY_STATUS_RESPONSE.COUNT.text()))

    }
        def void testInventoryUpdateColor() throws Exception
    {

        // createTestKitSKU()

         def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def generalStatusRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("112"), client_id:'112', testing:'FALSE') {
                        OWD_INVENTORY_UPDATE_REQUEST(sku:testKitSKU) {
                            TITLE('NEWTITLE')
                            DEFAULT_FACILITY_CODE('DC1')
                           /*  COMPONENTS()
                                    {
                                        COMPONENT()
                                                {
                                                    COMPONENT_SKU("TestItemOWD0001")
                                                    COMPONENT_QTY("2")
                                                }
                                        COMPONENT()
                                                {
                                                    COMPONENT_SKU("TestItemOWD0002")
                                                    COMPONENT_QTY("4")
                                                }
                                    }*/
                        }
            }
        }

        println "sending"
        println builder.bind(generalStatusRequest).toString()

        Document  response = getLiveAPIResponse(builder.bind(generalStatusRequest).toString());
      //  prettyPrint(response.getDocumentElement(),System.out);
      verifyAPIResponse((Document)response);

       def resp = new XmlParser().parseText(printNodeToString(response.getDocumentElement()));
        println "XXX:"+ resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].DEFAULT_FACILITY_CODE.text()

      assertTrue("Returned Count <> 1",1==new Integer(resp.OWD_INVENTORY_STATUS_RESPONSE.COUNT.text()))
        assertTrue("Update not verified","NEWTITLE".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].TITLE.text()))  
        assertTrue("Update not verified","NEWSUPPLIER".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].SUPPLIER.text()))
        assertTrue("Update not verified","NEWSUPPLIER_SKU".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].SUPPLIER_SKU.text()))
        assertTrue("Update not verified","9.99".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].UNIT_PRICE.text()))
        assertTrue("Update not verified","9.99".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].UNIT_COST.text()))
        assertTrue("Update not verified","NEWDESCRIPTION".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].DESCRIPTION.text()))
        assertTrue("Update not verified","NEWCOLOR".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COLOR.text()))
        assertTrue("Update not verified","NEWSIZE".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].SIZE.text()))
        assertTrue("Update not verified","9".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].REORDER_ALERT_QTY.text())) 
        assertTrue("Update not verified","9.99".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].WEIGHT.text()))
        assertTrue("Update not verified","NEWCUSTOMS_DESC".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].CUSTOMS_DESC.text()))   
        assertTrue("Update not verified","NEWTAG STUFF".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].KEYWORD.text()))
        assertTrue("Update not verified","9.99".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].CUSTOMS_VALUE.text()))
        assertTrue("Update not verified","NEWPACKING_INSTRUCTIONS".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].PACKING_INSTRUCTIONS.text()))
        assertTrue("Update not verified","NEWNOTES".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].NOTES.text()))
        assertTrue("Update not verified","0".equals(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].ACTIVE.text()))

        assertEquals("Update not verified",1,(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COMPONENTS.COMPONENT.findAll{ it.COMPONENT_SKU.text().equals('TestItemOWD0001') }.size()))
        assertEquals("Update not verified",1,(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COMPONENTS.COMPONENT.findAll{ it.COMPONENT_SKU.text().equals('TestItemOWD0002') }.size()))
        assertEquals("Update not verified","2",(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COMPONENTS.COMPONENT.findAll{ it.COMPONENT_SKU.text().equals('TestItemOWD0001') }[0].COMPONENT_QTY.text()))
        assertEquals("Update not verified","4",(resp.OWD_INVENTORY_STATUS_RESPONSE.OWD_INVENTORY_STATUS[0].COMPONENTS.COMPONENT.findAll{ it.COMPONENT_SKU.text().equals('TestItemOWD0002') }[0].COMPONENT_QTY.text()))
    }


    static final String testKitSKU = "TEST_OWD_KIT_SKU"

    def void testCreateTestKitSKU()
    {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
            {
                mkp.xmlDeclaration()
                OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("503"), client_id:'503', testing:'FALSE') {
                    OWD_INVENTORY_CREATE_REQUEST() {
                        SKU('m002')
                        TYPE('KIT')
                        TITLE('m002')
                        DESCRIPTION('1-Cured Original Flavor Bacon Jam \n' +
                                '1-Bourbon Barrel Foods-Worchistire \n' +
                                '1-Bourbon Blueberry Jam \n' +
                                '1-Bourbon Barrel Syrup \n' +
                                '1-Pancake Mix  \n' +
                                '1-Wooden Crates \n' +
                                '1-October Manual')
                        PACKING_INSTRUCTIONS('Please make sure all glass bottles are wrapped in bubble wrap and then in tissue paper')
                        COMPONENTS()
                                {
                                    COMPONENT()
                                            {
                                                COMPONENT_SKU("Cured Original Flavor Bacon Jam")
                                                COMPONENT_QTY("1")
                                            }
                                    COMPONENT()
                                            {
                                                COMPONENT_SKU("Bourbon Barrel Foods-Worchistire")
                                                COMPONENT_QTY("1")
                                            }
                                    COMPONENT()
                                            {
                                                COMPONENT_SKU("Bourbon Blueberry Jam")
                                                COMPONENT_QTY("1")
                                            }
                                    COMPONENT()
                                            {
                                                COMPONENT_SKU("Bourbon Barrel Syrup")
                                                COMPONENT_QTY("1")
                                            }
                                    COMPONENT()
                                            {
                                                COMPONENT_SKU("Pancake Mix")
                                                COMPONENT_QTY("1")
                                            }
                                    COMPONENT()
                                            {
                                                COMPONENT_SKU("Wooden Crates")
                                                COMPONENT_QTY("1")
                                            }
                                    COMPONENT()
                                            {
                                                COMPONENT_SKU("October Manual")
                                                COMPONENT_QTY("1")
                                            }
                                }
                    }
                }
            }

        Document  response = getLiveAPIResponse(builder.bind(kitItemCreateRequest).toString());
        verifyAPIResponse((Document)response);
    }

     def void testUpdateCount(){
         String xml = "<?xml version=\"1.0\"?>" +
                 "<OWD_API_REQUEST api_version='1.0' " +
                 "client_authorization='YsWueAi1SNJg6O8Afq8U4gM=' client_id='591' " +
                 "testing='TRUE'>" +
                 "    <OWD_TEST_INVENTORY_SETCOUNT_REQUEST>" +
                 "        <SKU>MISC</SKU>" +
                 "        <TYPE>ADJUST</TYPE>" +
                 "        <VALUE>5</VALUE>" +
                 "        <FACILITY_CODE>DC6</FACILITY_CODE>" +
                 "    </OWD_TEST_INVENTORY_SETCOUNT_REQUEST>" +
                 "</OWD_API_REQUEST>"
         Document  response = getTestAPIResponse(xml);
         //verifyAPIResponse((Document)response);
         prettyPrint(response.getDocumentElement(), System.out);
     }
    def void testUpdateRequest()
    {

        System.setProperty("com.owd.environment","test");
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_authorization=\"tBt4CbZE+Ih5SaB6PjVcnAI=\" client_id=\"55\" testing=\"FALSE\">" +
                "<OWD_INVENTORY_UPDATE_REQUEST sku=\"Ebay Items\">"+
                "<RECORDED_PERCENT>900</RECORDED_PERCENT></OWD_INVENTORY_UPDATE_REQUEST></OWD_API_REQUEST>";

        Document  response = getInternalAPIResponse(xml);
        verifyAPIResponse((Document)response);
        prettyPrint(response.getDocumentElement(), System.out);

    }
    def void testCreateRequestWithRecordedPercent()
    {

        System.setProperty("com.owd.environment","test");
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<OWD_API_REQUEST api_version=\"2.0\" client_authorization=\"tBt4CbZE+Ih5SaB6PjVcnAI=\" client_id=\"55\" testing=\"FALSE\">" +
                "<OWD_INVENTORY_CREATE_REQUEST><SKU>testrecord6</SKU><TYPE>PHYSICAL</TYPE>"+
                "<RECORDED_PERCENT>150</RECORDED_PERCENT></OWD_INVENTORY_CREATE_REQUEST></OWD_API_REQUEST>";

        Document  response = getInternalAPIResponse(xml);
        verifyAPIResponse((Document)response);
        prettyPrint(response.getDocumentElement(), System.out);

    }
    def void createTestKitSKU()
    {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("112"), client_id:'112', testing:'FALSE') {
                        OWD_INVENTORY_CREATE_REQUEST() {
                            SKU(testKitSKU)
                            TYPE('KIT')
                            TITLE('Rock, Paper, Scissors, Lizard, Spock: A Strategy Guide')
                            COMPONENTS()
                                    {
                                        COMPONENT()
                                                {
                                                    COMPONENT_SKU("SKUTest1")
                                                    COMPONENT_QTY("1")
                                                }
                                        COMPONENT()
                                                {
                                                    COMPONENT_SKU("SKUTest2")
                                                    COMPONENT_QTY("3")
                                                }
                                    }
                        }
            }
        }

        Document  response = getLiveAPIResponse(builder.bind(kitItemCreateRequest).toString());
        verifyAPIResponse((Document)response);
    }

     def void testBen()
    {
          def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("112"), client_id:'112', testing:'FALSE') {
                        OWD_INVENTORY_CREATE_REQUEST() {
                            SKU('1')
                            TYPE('PHYSICAL')

                        }
            }
        }

        println "sending"
        println builder.bind(kitItemCreateRequest).toString()

       Document  response = getLiveAPIResponse(builder.bind(kitItemCreateRequest).toString());
        verifyAPIResponse((Document)response);
    }


    def void testSetInventoryCount()
    {

           def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
        {
            mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("112"), client_id:'112', testing:'FALSE') {
                        OWD_TEST_INVENTORY_SETCOUNT_REQUEST() {
                            SKU('BPC-GU-MLBUNIV')
                            TYPE('SET')
                            VALUE('666')
                        }
            }
        }

        println "sending"
        println builder.bind(kitItemCreateRequest).toString()

        Document  response = getLiveAPIResponse(builder.bind(kitItemCreateRequest).toString());
        verifyAPIResponse((Document)response);
        prettyPrint(response.getDocumentElement(), System.out);
    }

    def void testOrderReleaseApi()
    {
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
            {
                mkp.xmlDeclaration()
                OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("489"), client_id:'489', testing:'FALSE') {
                    OWD_TEST_ORDER_RELEASE_REQUEST() {
                        ORDER('343698.2012-10-18T19:19:25.451Z')
                        TYPE('PDF')
                        delegate.URL('http://partner.sneakpeeq.com/fulfill')
                    }
                }
            }

        println "sending"
        println builder.bind(kitItemCreateRequest).toString()

        String tester = "<OWD_ORDER_APPROVAL_REQUEST><PACK_SLIP_ONLY>1</PACK_SLIP_ONLY><OWD_ORDER_REF>string</OWD_ORDER_REF><CLIENT_ORDER_REF>308367</CLIENT_ORDER_REF><ORDER_BARCODE>string</ORDER_BARCODE><SHIP_METHOD>string</SHIP_METHOD><DISCOUNT_AMOUNT>1000.00</DISCOUNT_AMOUNT><TAX_AMOUNT>1000.00</TAX_AMOUNT><SHIPPING_AMOUNT>1000.00</SHIPPING_AMOUNT><BILLING_ADDRESS><NAME>string</NAME><COMPANY>string</COMPANY><ADDRESS_1>string</ADDRESS_1><ADDRESS_2>string</ADDRESS_2><CITY>string</CITY><REGION>string</REGION><POSTAL_CODE>string</POSTAL_CODE><COUNTRY>string</COUNTRY><PHONE>string</PHONE><EMAIL>string</EMAIL></BILLING_ADDRESS><SHIPPING_ADDRESS><NAME>string</NAME><COMPANY>string</COMPANY><ADDRESS_1>string</ADDRESS_1><ADDRESS_2>string</ADDRESS_2><CITY>string</CITY><REGION>string</REGION><POSTAL_CODE>string</POSTAL_CODE><COUNTRY>string</COUNTRY><PHONE>string</PHONE><EMAIL>string</EMAIL></SHIPPING_ADDRESS><LINE_ITEMS><LINE><SKU>string</SKU><QUANTITY_SHIPPED>100</QUANTITY_SHIPPED><QUANTITY_BACKORDERED>100</QUANTITY_BACKORDERED><TITLE>string</TITLE><UNIT_PRICE>1000.00</UNIT_PRICE><COLOR>string</COLOR><SIZE>string</SIZE></LINE></LINE_ITEMS></OWD_ORDER_APPROVAL_REQUEST>";
        Document  response = getLiveAPIResponse(builder.bind(kitItemCreateRequest).toString());
        prettyPrint(response.getDocumentElement(), System.out);
        verifyAPIResponse((Document)response);
    }

    def void testInventoryUpdate()
    {
          def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
        {
                mkp.xmlDeclaration()
            OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData("517"), client_id:'517', testing:'FALSE') {
                        OWD_INVENTORY_UPDATE_REQUEST(sku:'bare-love-face') {
                            TITLE('Bare Love Face')
                            UNIT_PRICE('78')
                            UNIT_COST('39')
                            SUPPLIER('Bare Love')
                            SIZE('1.67 oz')
                            SUPPLIER_SKU('2BareLove')

                        }
            }
        }

        println "sending"
        println builder.bind(kitItemCreateRequest).toString()

        Document  response = getLiveAPIResponse(builder.bind(kitItemCreateRequest).toString());
        prettyPrint(response.getDocumentElement(), System.out);

        verifyAPIResponse((Document)response);
    }
}

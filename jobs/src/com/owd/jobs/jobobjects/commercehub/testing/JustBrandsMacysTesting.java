package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsMacysCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsQVCCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter;
import com.owd.jobs.jobobjects.commercehub.invoicing.CommerceHubInvoiceBase;
import groovy.util.XmlSlurper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by danny on 6/19/2019.
 */
public class JustBrandsMacysTesting {
    private final static Logger log = LogManager.getLogger();

        @Test
        public void QVCInventoryTest(){
                System.setProperty("com.owd.environment","test");
                JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);
                log.debug(api.getInventoryFile());
                api.pushInventoryFile();



        }


    @Test
    public  void macysTestCase1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121106809\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0016944649</sendersIdForReceiver>\n" +
                "  <orderId>121106809a</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>00136366504a</poNumber>\n" +
                "  <orderDate>20190820</orderDate>\n" +
                "  <tax>12.53</tax>\n" +
                "  <shipping>5.00</shipping>\n" +
                "  <handling>2.00</handling>\n" +
                "  <shipTo personPlaceID=\"PP3008103025\" />\n" +
                "  <billTo personPlaceID=\"PP3008103026\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536002_121106809\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008103027\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>11</salesDivision>\n" +
                "  <balanceDue>386.28</balanceDue>\n" +
                "  <custOrderNumber>0136366505a</custOrderNumber>\n" +
                "  <apVendor>1234</apVendor>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<discountTerms discTypeCode=\"05\" discPercent=\"5.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                "    \n" +
                "\t<cancelAfterDate>20190827</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0136366505</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<giftMessage>Happy Birthday</giftMessage>\n" +
                "    \n" +
                "\t<vendorMessage>IF YOU HAVE ANY PROBLEMS WITH THIS SHIPMENT PLEASE CALL 1-800-111-1111</vendorMessage>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121212814</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>00001234567811</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>122.2500</unitPrice>\n" +
                "    <unitCost>28.4900</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20190823</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <giftRegistry>123456</giftRegistry>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008103025\">\n" +
                "    <name1>Mary Smith</name1>\n" +
                "    <address1>1 Stewart St</address1>\n" +
                "    <city>Ithaca</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>14850</postalCode>\n" +
                "    <email>msmith@test.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <attnLine>Programming Dept.</attnLine>\n" +
                "      <ReceiptID>111111111</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103027\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>111111111</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103026\">\n" +
                "    <name1>Mary Smith</name1>\n" +
                "    <address1>1 Stewart St</address1>\n" +
                "    <city>Itahca</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>14850</postalCode>\n" +
                "    <personPlaceData>\n" +
                "      <attnLine>Programming Dept.</attnLine>\n" +
                "      <ReceiptID>111111111</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103536002_121106809\">\n" +
                "    <partnerLocationId>NM</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);
       // api.setGroupName("macys");
       // api.setPackingSlipTemplate("iqcv");
       // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003626072", false);
           /* PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


            log.debug(orderId);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);

                CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                //api.setInvoiceDiscDaysDue(5);
                api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

                System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));
*/


        }catch (Exception e){
            fail();
        }








    }
    @Test
    public  void macysTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121106810\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0016944649</sendersIdForReceiver>\n" +
                "  <orderId>121106810</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>00136366509</poNumber>\n" +
                "  <orderDate>20190820</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3008103028\" />\n" +
                "  <billTo personPlaceID=\"PP3008103029\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536003_121106810\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008103030\" />\n" +
                "  <shippingCode>UPSN_ND</shippingCode>\n" +
                "  <salesDivision>23</salesDivision>\n" +
                "  <balanceDue>99.98</balanceDue>\n" +
                "  <custOrderNumber>0136366510</custOrderNumber>\n" +
                "  <apVendor>12345</apVendor>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20190827</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0136366510</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121212815</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>00001234567821</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>49.9900</unitPrice>\n" +
                "    <unitCost>30.0000</unitCost>\n" +
                "    <shippingCode>UPSN_ND</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20190823</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008103028\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <address2>Apartment 2b</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <postalCode>08837-9998</postalCode>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>222222222</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103029\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <address2>Apartment 2b</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <postalCode>08837-9998</postalCode>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>222222222</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103030\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>222222222</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103536003_121106810\">\n" +
                "    <partnerLocationId>NX</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);
        // api.setGroupName("macys");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003626072", false);
           // PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


           // log.debug(orderId);
/*
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setBatchRef("3003626072");
            confirmFileSource.setOrderSrc(data);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());*/
           // api.confirmOrderShipment(os);


        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
            fail();
        }








    }

    @Test
    public  void macysTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121106811\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0016944649</sendersIdForReceiver>\n" +
                "  <orderId>121106811</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00136366514</poNumber>\n" +
                "  <orderDate>20190820</orderDate>\n" +
                "  <tax>4.99</tax>\n" +
                "  <shipping>5.00</shipping>\n" +
                "  <handling>2.00</handling>\n" +
                "  <shipTo personPlaceID=\"PP3008103031\" />\n" +
                "  <billTo personPlaceID=\"PP3008103032\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536004_121106811\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008103033\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>11</salesDivision>\n" +
                "  <balanceDue>132.96</balanceDue>\n" +
                "  <custOrderNumber>0136366515</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20190827</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0136366515</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121212816</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>00001234567831</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <unitPrice>42.9900</unitPrice>\n" +
                "    <unitCost>30.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20190823</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121212817</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>00001234567832</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <unitPrice>34.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20190823</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <giftRegistry>264892</giftRegistry>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008103033\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>333333333</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103031\">\n" +
                "    <name1>Tom Smith</name1>\n" +
                "    <address1>3 Westerre Parkway</address1>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>23233</postalCode>\n" +
                "    <email>tomsmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <attnLine>MACY'S/BOSS/WESTFIELD MAINPLAC</attnLine>\n" +
                "      <ReceiptID>333333333</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103032\">\n" +
                "    <name1>Bill Johnson</name1>\n" +
                "    <address1>3 Falke Plaza</address1>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>20166</postalCode>\n" +
                "    <email>billjohnson@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>333333333</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103536004_121106811\">\n" +
                "    <partnerLocationId>SV</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);
        // api.setGroupName("macys");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003626072", false);
            PackingManager.packAndShip(orderId,true, 2, 0.00, "1Z0000000000000000", false);


            log.debug(orderId);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);
                CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                //api.setInvoiceDiscDaysDue(5);
                api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

                System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));



        }catch (Exception e){
            fail();
        }








    }

    @Test
    public  void macysTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121106812\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0016944649</sendersIdForReceiver>\n" +
                "  <orderId>121106812</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00136366520</poNumber>\n" +
                "  <orderDate>20190820</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3008103034\" />\n" +
                "  <billTo personPlaceID=\"PP3008103035\" />\n" +
                "  <shipFrom vendorShipID=\"VS2000021_121106812\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008103036\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>15</salesDivision>\n" +
                "  <balanceDue>249.94</balanceDue>\n" +
                "  <custOrderNumber>0136366521</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20190827</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0136366521</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121212818</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>00001234567841</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>42.9900</unitPrice>\n" +
                "    <unitCost>30.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20190823</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121212819</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>00001234567842</UPC>\n" +
                "    <description>Sample Item Description for Line 2</description>\n" +
                "    <unitPrice>49.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20190823</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008103036\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>444444444</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103035\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <address1>4 La Jolla Village Dr</address1>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <postalCode>92122-</postalCode>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>444444444</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103034\">\n" +
                "    <name1>Joe Sample</name1>\n" +
                "    <address1>4 Jamboree Road</address1>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <postalCode>92660-</postalCode>\n" +
                "    <email>joesample@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <attnLine>Care of: Jane Sample</attnLine>\n" +
                "      <ReceiptID>444444444</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS2000021_121106812\" />\n" +
                "</hubOrder>";

        JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);
        // api.setGroupName("macys");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003626072", false);
            // PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


            // log.debug(orderId);
/*
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setBatchRef("3003626072");
            confirmFileSource.setOrderSrc(data);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());*/
            // api.confirmOrderShipment(os);


        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
            fail();
        }








    }

    @Test
    public  void macysTestCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121106813\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0016944649</sendersIdForReceiver>\n" +
                "  <orderId>121106813</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00136366526</poNumber>\n" +
                "  <orderDate>20190820</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3008103037\" />\n" +
                "  <billTo personPlaceID=\"PP3008103038\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536002_121106813\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008103039\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>21</salesDivision>\n" +
                "  <balanceDue>375.00</balanceDue>\n" +
                "  <custOrderNumber>0136366527</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20190827</cancelAfterDate>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmEmail>cs@abc.com</rmEmail>\n" +
                "      \n" +
                "\t\t<rmPhone1>555-555-5555</rmPhone1>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0136366527</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<URL type=\"customer-service\">https://www.url.com/contactus</URL>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121212820</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>00001234567971</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>75.0000</unitPrice>\n" +
                "    <unitCost>50.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20190823</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121212821</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>00001234567972</UPC>\n" +
                "    <description>Sample Item Description for Line 2</description>\n" +
                "    <unitPrice>49.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20190823</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008103038\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>5 Busch Blvd</address1>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>555555555</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103037\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>5 Busch Blvd</address1>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <attnLine>Attn:  Engineering Dept.</attnLine>\n" +
                "      <ReceiptID>555555555</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008103039\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>555555555</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103536002_121106813\">\n" +
                "    <partnerLocationId>NM</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);
        // api.setGroupName("macys");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003626072", false);
            PackingManager.packAndShip(orderId, 2, 0.00, "1Z0000000000000000", false);


            log.debug(orderId);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);

                CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                //api.setInvoiceDiscDaysDue(5);
                api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

                System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));



        }catch (Exception e){
            fail();
        }








    }

        @Test
        public  void macysTestCase6(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"121106814\">\n" +
                        "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                        "  <sendersIdForReceiver>0016944649</sendersIdForReceiver>\n" +
                        "  <orderId>121106814</orderId>\n" +
                        "  <lineCount>2</lineCount>\n" +
                        "  <poNumber>00136366532</poNumber>\n" +
                        "  <orderDate>20190820</orderDate>\n" +
                        "  <tax>12.53</tax>\n" +
                        "  <shipping>5.00</shipping>\n" +
                        "  <handling>2.00</handling>\n" +
                        "  <shipTo personPlaceID=\"PP3008103040\" />\n" +
                        "  <billTo personPlaceID=\"PP3008103041\" />\n" +
                        "  <shipFrom vendorShipID=\"VS103536003_121106814\" />\n" +
                        "  <invoiceTo personPlaceID=\"PP3008103042\" />\n" +
                        "  <shippingCode>UPSN_CG</shippingCode>\n" +
                        "  <salesDivision>16</salesDivision>\n" +
                        "  <balanceDue>630.78</balanceDue>\n" +
                        "  <custOrderNumber>0136366533</custOrderNumber>\n" +
                        "  <apVendor>12345</apVendor>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<discountTerms discTypeCode=\"05\" discPercent=\"2.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                        "    \n" +
                        "\t<cancelAfterDate>20190827</cancelAfterDate>\n" +
                        "    \n" +
                        "\t<custOrderNumber>0136366533</custOrderNumber>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\t<giftMessage>Happy Birthday</giftMessage>\n" +
                        "    \n" +
                        "\t<offerCurrency>USD</offerCurrency>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212822</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567912</UPC>\n" +
                        "    <description>Sample Item Description for Line 1</description>\n" +
                        "    <unitPrice>122.2500</unitPrice>\n" +
                        "    <unitCost>28.4900</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>2234567</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <giftRegistry>123456</giftRegistry>\n" +
                        "      <merchDept>2234567</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212823</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567842</UPC>\n" +
                        "    <description>Sample Item Description for Line 2</description>\n" +
                        "    <unitPrice>34.9900</unitPrice>\n" +
                        "    <unitCost>25.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>7654321</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>7654321</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3008103041\">\n" +
                        "    <name1>Mary Doe</name1>\n" +
                        "    <address1>6 Quorum Drive</address1>\n" +
                        "    <city>Dallas</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <postalCode>75240</postalCode>\n" +
                        "    <personPlaceData>\n" +
                        "      <ReceiptID>111111111</ReceiptID>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3008103042\">\n" +
                        "    <name1>Albany Store</name1>\n" +
                        "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                        "    <personPlaceData>\n" +
                        "      <ReceiptID>111111111</ReceiptID>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3008103040\">\n" +
                        "    <name1>Lisa Smith</name1>\n" +
                        "    <address1>6 West Loop South</address1>\n" +
                        "    <city>Houston</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <postalCode>77027</postalCode>\n" +
                        "    <email>lisasmith@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <ReceiptID>111111111</ReceiptID>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS103536003_121106814\">\n" +
                        "    <partnerLocationId>NX</partnerLocationId>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);
                // api.setGroupName("macys");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003626072", false);
                        PackingManager.packAndShip(orderId, 2, 0.00, "1Z0000000000000000", false);


                        log.debug(orderId);

                        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                        OrderStatus os = new OrderStatus(orderId+"");
                        confirmFileSource.setCurrentOrderStatus(os);
                        confirmFileSource.setClientId(api.getClientId());
                        System.out.println(confirmFileSource.getFileData());
                        //api.confirmOrderShipment(os);



                }catch (Exception e){
                        fail();
                }








        }

        @Test
        public  void macysTestCase6SendDocs() {
                System.setProperty("com.owd.environment", "test");

                JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);

                CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                OrderStatus os = new OrderStatus(18023275+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);
                CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                //api.setInvoiceDiscDaysDue(5);
                api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

                System.out.println(api.sendInvoiceToCommerceHub(18023275, invoice));


                try {
                         PackingManager.packAndShip(18023276, 2, 0.00, "1Z0000000000000000", false);
                }catch (Exception e){
                        e.printStackTrace();

                }
              confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                 os = new OrderStatus(18023276+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);
                System.out.println(api.sendInvoiceToCommerceHub(18023276, invoice));

                try {
                       // PackingManager.packAndShip(18022037, 2, 0.00, "1Z0000000000000000", false);
                }catch (Exception e){
                        e.printStackTrace();

                }





        }


        @Test
        public  void macysTestCase7(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"121106815\">\n" +
                        "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                        "  <sendersIdForReceiver>0016944649</sendersIdForReceiver>\n" +
                        "  <orderId>121106815</orderId>\n" +
                        "  <lineCount>10</lineCount>\n" +
                        "  <poNumber>00136366538</poNumber>\n" +
                        "  <orderDate>20190820</orderDate>\n" +
                        "  <tax>12.53</tax>\n" +
                        "  <shipping>5.00</shipping>\n" +
                        "  <handling>2.00</handling>\n" +
                        "  <shipTo personPlaceID=\"PP3008103043\" />\n" +
                        "  <billTo personPlaceID=\"PP3008103044\" />\n" +
                        "  <shipFrom vendorShipID=\"VS103536004_121106815\" />\n" +
                        "  <invoiceTo personPlaceID=\"PP3008103045\" />\n" +
                        "  <shippingCode>UPSN_CG</shippingCode>\n" +
                        "  <salesDivision>11</salesDivision>\n" +
                        "  <balanceDue>257.53</balanceDue>\n" +
                        "  <custOrderNumber>0136366539</custOrderNumber>\n" +
                        "  <apVendor>12345</apVendor>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<discountTerms discTypeCode=\"05\" discPercent=\"2.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                        "    \n" +
                        "\t<cancelAfterDate>20190827</cancelAfterDate>\n" +
                        "    \n" +
                        "\t<custOrderNumber>0136366539</custOrderNumber>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\t<giftMessage>Happy Birthday</giftMessage>\n" +
                        "    \n" +
                        "\t<vendorMessage>IF YOU HAVE ANY PROBLEMS WITH THIS SHIPMENT PLEASE CALL 1-800-111-1111</vendorMessage>\n" +
                        "    \n" +
                        "\t<offerCurrency>USD</offerCurrency>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212824</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>10</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567971</UPC>\n" +
                        "    <description>Sample Item Description for Line 1</description>\n" +
                        "    <unitPrice>2.5000</unitPrice>\n" +
                        "    <unitCost>2.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>1234567</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>1234567</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212825</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>9</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567972</UPC>\n" +
                        "    <description>Sample Item Description for Line 2</description>\n" +
                        "    <unitPrice>3.0000</unitPrice>\n" +
                        "    <unitCost>2.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>7654321</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>7654321</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212826</lineItemId>\n" +
                        "    <orderLineNumber>3</orderLineNumber>\n" +
                        "    <merchantLineNumber>3</merchantLineNumber>\n" +
                        "    <qtyOrdered>8</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567973</UPC>\n" +
                        "    <description>Sample Item Description for Line 3</description>\n" +
                        "    <unitPrice>4.0000</unitPrice>\n" +
                        "    <unitCost>3.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>1234567</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>1234567</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212827</lineItemId>\n" +
                        "    <orderLineNumber>4</orderLineNumber>\n" +
                        "    <merchantLineNumber>4</merchantLineNumber>\n" +
                        "    <qtyOrdered>7</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567974</UPC>\n" +
                        "    <description>Sample Item Description for Line 4</description>\n" +
                        "    <unitPrice>4.5000</unitPrice>\n" +
                        "    <unitCost>4.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>7654321</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>7654321</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212828</lineItemId>\n" +
                        "    <orderLineNumber>5</orderLineNumber>\n" +
                        "    <merchantLineNumber>5</merchantLineNumber>\n" +
                        "    <qtyOrdered>6</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567975</UPC>\n" +
                        "    <description>Sample Item Description for Line 5</description>\n" +
                        "    <unitPrice>5.0000</unitPrice>\n" +
                        "    <unitCost>4.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>1234567</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>1234567</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212829</lineItemId>\n" +
                        "    <orderLineNumber>6</orderLineNumber>\n" +
                        "    <merchantLineNumber>6</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567976</UPC>\n" +
                        "    <description>Sample Item Description for Line 6</description>\n" +
                        "    <unitPrice>5.5000</unitPrice>\n" +
                        "    <unitCost>5.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>7654321</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>7654321</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212830</lineItemId>\n" +
                        "    <orderLineNumber>7</orderLineNumber>\n" +
                        "    <merchantLineNumber>7</merchantLineNumber>\n" +
                        "    <qtyOrdered>4</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567977</UPC>\n" +
                        "    <description>Sample Item Description for Line 7</description>\n" +
                        "    <unitPrice>6.0000</unitPrice>\n" +
                        "    <unitCost>5.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>1234567</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>1234567</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212831</lineItemId>\n" +
                        "    <orderLineNumber>8</orderLineNumber>\n" +
                        "    <merchantLineNumber>8</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567978</UPC>\n" +
                        "    <description>Sample Item Description for Line 8</description>\n" +
                        "    <unitPrice>6.5000</unitPrice>\n" +
                        "    <unitCost>6.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>7654321</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>7654321</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212832</lineItemId>\n" +
                        "    <orderLineNumber>9</orderLineNumber>\n" +
                        "    <merchantLineNumber>9</merchantLineNumber>\n" +
                        "    <qtyOrdered>2</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567979</UPC>\n" +
                        "    <description>Sample Item Description for Line 9</description>\n" +
                        "    <unitPrice>7.0000</unitPrice>\n" +
                        "    <unitCost>6.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>1234567</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>1234567</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121212833</lineItemId>\n" +
                        "    <orderLineNumber>10</orderLineNumber>\n" +
                        "    <merchantLineNumber>10</merchantLineNumber>\n" +
                        "    <qtyOrdered>1</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>00001234567970</UPC>\n" +
                        "    <description>Sample Item Description for Line 10</description>\n" +
                        "    <unitPrice>7.5000</unitPrice>\n" +
                        "    <unitCost>7.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <merchDept>7654321</merchDept>\n" +
                        "    <expectedShipDate>20190823</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <merchDept>7654321</merchDept>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3008103044\">\n" +
                        "    <name1>Paula Jones</name1>\n" +
                        "    <address1>Apt 9</address1>\n" +
                        "    <address2>9 Main St</address2>\n" +
                        "    <city>Moab</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>84532</postalCode>\n" +
                        "    <email>pjones@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <ReceiptID>111111111</ReceiptID>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3008103045\">\n" +
                        "    <name1>Albany Store</name1>\n" +
                        "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                        "    <personPlaceData>\n" +
                        "      <ReceiptID>111111111</ReceiptID>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3008103043\">\n" +
                        "    <name1>Mike Jones</name1>\n" +
                        "    <address1>Suite 9 - High Towers</address1>\n" +
                        "    <address2>9 Wakara Way</address2>\n" +
                        "    <city>Salt Lake City</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <postalCode>84108</postalCode>\n" +
                        "    <email>mikejones@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <ReceiptID>111111111</ReceiptID>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS103536004_121106815\">\n" +
                        "    <partnerLocationId>SV</partnerLocationId>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","macys","Just Brand Limited","macys",626);
                // api.setGroupName("macys");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003626072", false);
                        PackingManager.packAndShip(orderId, 2, 0.00, "1Z0000000000000000", false);


                        log.debug(orderId);

                        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                        OrderStatus os = new OrderStatus(orderId+"");
                        confirmFileSource.setCurrentOrderStatus(os);
                        confirmFileSource.setClientId(api.getClientId());
                        System.out.println(confirmFileSource.getFileData());
                        api.confirmOrderShipment(os);
                        CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                        //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                        //api.setInvoiceDiscDaysDue(5);
                        api.setInvoiceNetDaysDue(30);
                        //api.setInvoiceDiscPercent(new BigDecimal("10"));

                        System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));



                }catch (Exception e){
                        fail();
                }








        }
}

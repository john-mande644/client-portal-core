package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.SokoMacysCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter;
import com.owd.jobs.jobobjects.commercehub.invoicing.CommerceHubInvoiceBase;
import groovy.util.XmlSlurper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.fail;

public class SokoMacysTesting {
    private final static Logger log = LogManager.getLogger();
    private final static int clientID = 636;
    private final static String vendorName = "Soko";

    // Ship entire order
    // Send Invoice
    // Return qty 1 of line 1
    // Sent 03.01.23
    @Test
    public void TestCase1() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3002061026\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0000018050</sendersIdForReceiver>\n" +
                "  <orderId>3002061026</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>00149116504</poNumber>\n" +
                "  <orderDate>20230208</orderDate>\n" +
                "  <tax>12.53</tax>\n" +
                "  <shipping>5.00</shipping>\n" +
                "  <handling>2.00</handling>\n" +
                "  <shipTo personPlaceID=\"PP3013273026\" />\n" +
                "  <billTo personPlaceID=\"PP3013273027\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536002_3002061026\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3013273028\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>11</salesDivision>\n" +
                "  <balanceDue>386.28</balanceDue>\n" +
                "  <custOrderNumber>0149116505</custOrderNumber>\n" +
                "  <apVendor>1234</apVendor>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<discountTerms discTypeCode=\"05\" discPercent=\"5.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                "    \n" +
                "\t<cancelAfterDate>20230215</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0149116505</custOrderNumber>\n" +
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
                "    <lineItemId>3002078058</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567811</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>122.2500</unitPrice>\n" +
                "    <unitCost>28.4900</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <giftRegistry>123456</giftRegistry>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3013273026\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013273028\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>111111111</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3013273027\">\n" +
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
                "  <vendorShipInfo vendorShipID=\"VS103536002_3002061026\">\n" +
                "    <partnerLocationId>NM</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
        api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            int orderId = api.importOrderNode(data, "3000916015", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", true);

            CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId + "");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            // Confirm shipment
            api.confirmOrderShipment(os);

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Soko", "vendor", "soko");
            api.setInvoiceNetDaysDue(30);

            // Send invoice
            api.sendInvoiceToCommerceHub(orderId, invoice);

            // Return item
            // This requires manually manipulating the returns XML to adjust the quantity returned
            api.pushReturnFile(data, os);
        } catch (Exception e) {
            fail();
        }
    }

    // Cancel single-line order
    // Sent 03.01.23
    @Test
    public void TestCase2() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3002061027\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0000018050</sendersIdForReceiver>\n" +
                "  <orderId>3002061027</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>00149116509</poNumber>\n" +
                "  <orderDate>20230208</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3013273029\" />\n" +
                "  <billTo personPlaceID=\"PP3013273030\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536003_3002061027\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3013273031\" />\n" +
                "  <shippingCode>UPSN_ND</shippingCode>\n" +
                "  <salesDivision>23</salesDivision>\n" +
                "  <balanceDue>99.98</balanceDue>\n" +
                "  <custOrderNumber>0149116510</custOrderNumber>\n" +
                "  <apVendor>12345</apVendor>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20230215</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0149116510</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078059</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567821</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>49.9900</unitPrice>\n" +
                "    <unitCost>30.0000</unitCost>\n" +
                "    <shippingCode>UPSN_ND</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3013273029\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013273030\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013273031\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>222222222</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103536003_3002061027\">\n" +
                "    <partnerLocationId>NX</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
        api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            String fileData = api.rejectOrder(data,"3000916016");

            System.out.println(fileData);
        } catch (Exception e) {
            fail();
        }
    }

    // Ship entire order
    // Invoice entire order
    // Receive remittance advice
    // Sent 03.01.23
    @Test
    public void TestCase3() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3002061028\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0000018050</sendersIdForReceiver>\n" +
                "  <orderId>3002061028</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00149116514</poNumber>\n" +
                "  <orderDate>20230208</orderDate>\n" +
                "  <tax>4.99</tax>\n" +
                "  <shipping>5.00</shipping>\n" +
                "  <handling>2.00</handling>\n" +
                "  <shipTo personPlaceID=\"PP3013273032\" />\n" +
                "  <billTo personPlaceID=\"PP3013273033\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536004_3002061028\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3013273034\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>11</salesDivision>\n" +
                "  <balanceDue>132.96</balanceDue>\n" +
                "  <custOrderNumber>0149116515</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20230215</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0149116515</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078060</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567831</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <unitPrice>42.9900</unitPrice>\n" +
                "    <unitCost>30.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078061</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567832</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <unitPrice>34.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <giftRegistry>264892</giftRegistry>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3013273034\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>333333333</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3013273032\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013273033\">\n" +
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
                "  <vendorShipInfo vendorShipID=\"VS103536004_3002061028\">\n" +
                "    <partnerLocationId>SV</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
        api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            int orderId = api.importOrderNode(data, "3000916017", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

            CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId + "");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Soko", "vendor", "soko");
            api.setInvoiceNetDaysDue(30);

            System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));
        } catch (Exception e) {
            fail();
        }
    }

    // Cancel multi-line order
    // Sent 03.01.23
    @Test
    public void TestCase4() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3002061029\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0000018050</sendersIdForReceiver>\n" +
                "  <orderId>3002061029</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00149116520</poNumber>\n" +
                "  <orderDate>20230208</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3013273035\" />\n" +
                "  <billTo personPlaceID=\"PP3013273036\" />\n" +
                "  <shipFrom vendorShipID=\"VS2000021_3002061029\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3013273037\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>15</salesDivision>\n" +
                "  <balanceDue>249.94</balanceDue>\n" +
                "  <custOrderNumber>0149116521</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20230215</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0149116521</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078062</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567841</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>42.9900</unitPrice>\n" +
                "    <unitCost>30.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078063</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567842</UPC>\n" +
                "    <description>Sample Item Description for Line 2</description>\n" +
                "    <unitPrice>49.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3013273035\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013273036\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <address1>4 La Jolla Village Dr</address1>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <postalCode>92122-</postalCode>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>444444444</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3013273037\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>444444444</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS2000021_3002061029\" />\n" +
                "</hubOrder>";

        SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
        api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            String fileData = api.rejectOrder(data,"3000916016");

            System.out.println(fileData);
        } catch (Exception e) {
            fail();
        }
    }

    // Cancel line 4 | Ship line 1 | Invoice line 1
    // Create inventory for all items
    // Ensure line 4 is out of stock and back order rule is set to reject
    // Sent 03.01.23
    @Test
    public void TestCase5() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3002061030\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0000018050</sendersIdForReceiver>\n" +
                "  <orderId>3002061030</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00149116526</poNumber>\n" +
                "  <orderDate>20230208</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3013273038\" />\n" +
                "  <billTo personPlaceID=\"PP3013273039\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536002_3002061030\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3013273040\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>21</salesDivision>\n" +
                "  <balanceDue>375.00</balanceDue>\n" +
                "  <custOrderNumber>0149116527</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20230215</cancelAfterDate>\n" +
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
                "\t<custOrderNumber>0149116527</custOrderNumber>\n" +
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
                "    <lineItemId>3002078064</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567971</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>75.0000</unitPrice>\n" +
                "    <unitCost>50.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078065</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567972</UPC>\n" +
                "    <description>Sample Item Description for Line 2</description>\n" +
                "    <unitPrice>49.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3013273038\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013273040\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>555555555</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3013273039\">\n" +
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
                "  <vendorShipInfo vendorShipID=\"VS103536002_3002061030\">\n" +
                "    <partnerLocationId>NM</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
        api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            int orderId = api.importOrderNode(data, "3011322519", false);


            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

            CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId + "");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Soko", "vendor", "soko");
            api.setInvoiceNetDaysDue(30);

            System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));
        } catch (Exception e) {
            fail();
        }
    }

    // Ship line 1
    // Ship line 2
    // Invoice order
    // Create inventory for all items
    // Sent 03.01.23
    @Test
    public void TestCase6() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3002061031\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0000018050</sendersIdForReceiver>\n" +
                "  <orderId>3002061031</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00149116532</poNumber>\n" +
                "  <orderDate>20230208</orderDate>\n" +
                "  <tax>12.53</tax>\n" +
                "  <shipping>5.00</shipping>\n" +
                "  <handling>2.00</handling>\n" +
                "  <shipTo personPlaceID=\"PP3013273041\" />\n" +
                "  <billTo personPlaceID=\"PP3013273042\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536003_3002061031\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3013273043\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>16</salesDivision>\n" +
                "  <balanceDue>630.78</balanceDue>\n" +
                "  <custOrderNumber>0149116533</custOrderNumber>\n" +
                "  <apVendor>12345</apVendor>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<discountTerms discTypeCode=\"05\" discPercent=\"2.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                "    \n" +
                "\t<cancelAfterDate>20230215</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0149116533</custOrderNumber>\n" +
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
                "    <lineItemId>3002078066</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567912</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>122.2500</unitPrice>\n" +
                "    <unitCost>28.4900</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>2234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <giftRegistry>123456</giftRegistry>\n" +
                "      <merchDept>2234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078067</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567842</UPC>\n" +
                "    <description>Sample Item Description for Line 2</description>\n" +
                "    <unitPrice>34.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3013273042\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>6 Quorum Drive</address1>\n" +
                "    <city>Dallas</city>\n" +
                "    <state>TX</state>\n" +
                "    <postalCode>75240</postalCode>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>111111111</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3013273043\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>111111111</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3013273041\">\n" +
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
                "  <vendorShipInfo vendorShipID=\"VS103536003_3002061031\">\n" +
                "    <partnerLocationId>NX</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
        api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            int orderId = api.importOrderNode(data, "3000916020", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

            CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId + "");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Soko", "vendor", "soko");
            api.setInvoiceNetDaysDue(30);

            System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));
        } catch (Exception e) {
            fail();
        }
    }

    // Cancel line 10 | Ship and Invoice 1 - 9
    // Create inventory for all items
    // Ensure line 10 is out of stock and back order rule is set to reject
    @Test
    public void TestCase7() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3002061032\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0000018050</sendersIdForReceiver>\n" +
                "  <orderId>3002061032</orderId>\n" +
                "  <lineCount>10</lineCount>\n" +
                "  <poNumber>00149116538</poNumber>\n" +
                "  <orderDate>20230208</orderDate>\n" +
                "  <tax>12.53</tax>\n" +
                "  <shipping>5.00</shipping>\n" +
                "  <handling>2.00</handling>\n" +
                "  <shipTo personPlaceID=\"PP3013273044\" />\n" +
                "  <billTo personPlaceID=\"PP3013273045\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536004_3002061032\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3013273046\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>11</salesDivision>\n" +
                "  <balanceDue>257.53</balanceDue>\n" +
                "  <custOrderNumber>0149116539</custOrderNumber>\n" +
                "  <apVendor>12345</apVendor>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<discountTerms discTypeCode=\"05\" discPercent=\"2.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                "    \n" +
                "\t<cancelAfterDate>20230215</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0149116539</custOrderNumber>\n" +
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
                "    <lineItemId>3002078068</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>10</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567971</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>2.5000</unitPrice>\n" +
                "    <unitCost>2.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078069</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>9</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567972</UPC>\n" +
                "    <description>Sample Item Description for Line 2</description>\n" +
                "    <unitPrice>3.0000</unitPrice>\n" +
                "    <unitCost>2.5000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078070</lineItemId>\n" +
                "    <orderLineNumber>3</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>8</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567973</UPC>\n" +
                "    <description>Sample Item Description for Line 3</description>\n" +
                "    <unitPrice>4.0000</unitPrice>\n" +
                "    <unitCost>3.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078071</lineItemId>\n" +
                "    <orderLineNumber>4</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>7</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567974</UPC>\n" +
                "    <description>Sample Item Description for Line 4</description>\n" +
                "    <unitPrice>4.5000</unitPrice>\n" +
                "    <unitCost>4.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078072</lineItemId>\n" +
                "    <orderLineNumber>5</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>6</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567975</UPC>\n" +
                "    <description>Sample Item Description for Line 5</description>\n" +
                "    <unitPrice>5.0000</unitPrice>\n" +
                "    <unitCost>4.5000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078073</lineItemId>\n" +
                "    <orderLineNumber>6</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567976</UPC>\n" +
                "    <description>Sample Item Description for Line 6</description>\n" +
                "    <unitPrice>5.5000</unitPrice>\n" +
                "    <unitCost>5.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078074</lineItemId>\n" +
                "    <orderLineNumber>7</orderLineNumber>\n" +
                "    <merchantLineNumber>7</merchantLineNumber>\n" +
                "    <qtyOrdered>4</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567977</UPC>\n" +
                "    <description>Sample Item Description for Line 7</description>\n" +
                "    <unitPrice>6.0000</unitPrice>\n" +
                "    <unitCost>5.5000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078075</lineItemId>\n" +
                "    <orderLineNumber>8</orderLineNumber>\n" +
                "    <merchantLineNumber>8</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567978</UPC>\n" +
                "    <description>Sample Item Description for Line 8</description>\n" +
                "    <unitPrice>6.5000</unitPrice>\n" +
                "    <unitCost>6.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078076</lineItemId>\n" +
                "    <orderLineNumber>9</orderLineNumber>\n" +
                "    <merchantLineNumber>9</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567979</UPC>\n" +
                "    <description>Sample Item Description for Line 9</description>\n" +
                "    <unitPrice>7.0000</unitPrice>\n" +
                "    <unitCost>6.5000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002078077</lineItemId>\n" +
                "    <orderLineNumber>10</orderLineNumber>\n" +
                "    <merchantLineNumber>10</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567970</UPC>\n" +
                "    <description>Sample Item Description for Line 10</description>\n" +
                "    <unitPrice>7.5000</unitPrice>\n" +
                "    <unitCost>7.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <merchDept>7654321</merchDept>\n" +
                "    <expectedShipDate>20230211</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>7654321</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3013273045\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013273044\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013273046\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>111111111</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103536004_3002061032\">\n" +
                "    <partnerLocationId>SV</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
        api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            int orderId = api.importOrderNode(data, "3011322519", false);


            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

            CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId + "");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Soko", "vendor", "soko");
            api.setInvoiceNetDaysDue(30);

            System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));


        } catch (Exception e) {
            fail();
        }
    }

    // Upload inventory | Discontinue an item
    @Test
    public void TestCase8() {
        System.setProperty("com.owd.environment", "test");
        try {
            SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
            api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
            api.pushInventoryFile();
        }
        catch(Exception x)
        {
            fail();
        }
    }

    @Test
    public void TestCase9() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3002128016\">\n" +
                "  <participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "  <sendersIdForReceiver>0000018050</sendersIdForReceiver>\n" +
                "  <orderId>3002128016</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>00149358004</poNumber>\n" +
                "  <orderDate>20230309</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3013357057\" />\n" +
                "  <billTo personPlaceID=\"PP3013357058\" />\n" +
                "  <shipFrom vendorShipID=\"VS103536003_3002128016\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3013357059\" />\n" +
                "  <shippingCode>UPSN_ND</shippingCode>\n" +
                "  <salesDivision>23</salesDivision>\n" +
                "  <balanceDue>99.98</balanceDue>\n" +
                "  <custOrderNumber>0149358005</custOrderNumber>\n" +
                "  <apVendor>12345</apVendor>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20230316</cancelAfterDate>\n" +
                "    \n" +
                "\t<custOrderNumber>0149358005</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3002147041</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>0001234567821</UPC>\n" +
                "    <description>Sample Item Description for Line 1</description>\n" +
                "    <unitPrice>49.9900</unitPrice>\n" +
                "    <unitCost>30.0000</unitCost>\n" +
                "    <shippingCode>UPSN_ND</shippingCode>\n" +
                "    <merchDept>1234567</merchDept>\n" +
                "    <expectedShipDate>20230312</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <merchDept>1234567</merchDept>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3013357059\">\n" +
                "    <name1>Albany Store</name1>\n" +
                "    <partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      <ReceiptID>222222222</ReceiptID>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3013357058\">\n" +
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
                "  <personPlace personPlaceID=\"PP3013357057\">\n" +
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
                "  <vendorShipInfo vendorShipID=\"VS103536003_3002128016\">\n" +
                "    <partnerLocationId>NX</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
        api.configure("soko.sftp-test.commercehub.com", "soko", "Touri$t7Money3Or6anize", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            int orderId = api.importOrderNode(data, "3000916015", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000001", true);

            CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId + "");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            // Confirm shipment
            api.confirmOrderShipment(os);

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Soko", "vendor", "soko");
            api.setInvoiceNetDaysDue(30);

            // Send invoice
            api.sendInvoiceToCommerceHub(orderId, invoice);
        } catch (Exception e) {
            fail();
        }

    }
}

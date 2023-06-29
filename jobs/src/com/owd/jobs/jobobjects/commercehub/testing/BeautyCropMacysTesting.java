package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.BeautyCropMacysCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter;
import com.owd.jobs.jobobjects.commercehub.invoicing.CommerceHubInvoiceBase;
import groovy.util.XmlSlurper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.fail;

public class BeautyCropMacysTesting {
    private final static Logger log = LogManager.getLogger();
    private final static int clientID = 696;
    private final static String vendorName = "BeautyCrop";

    // Ship entire order
    // Create inventory for all items
    // Complete 12/31/21
    @Test
    public void BeautyCropTestCase1() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3000916015\">\n" +
                "\t\t<participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "\t\t<sendersIdForReceiver>0000018438</sendersIdForReceiver>\n" +
                "\t\t<orderId>3000916015</orderId>\n" +
                "\t\t<lineCount>1</lineCount>\n" +
                "\t\t<poNumber>00145930504</poNumber>\n" +
                "\t\t<orderDate>20211130</orderDate>\n" +
                "\t\t<tax>12.53</tax>\n" +
                "\t\t<shipping>5.00</shipping>\n" +
                "\t\t<handling>2.00</handling>\n" +
                "\t\t<shipTo personPlaceID=\"PP3011784043\"/>\n" +
                "\t\t<billTo personPlaceID=\"PP3011784044\"/>\n" +
                "\t\t<shipFrom vendorShipID=\"VS103536002_3000916015\"/>\n" +
                "\t\t<invoiceTo personPlaceID=\"PP3011784045\"/>\n" +
                "\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t<salesDivision>11</salesDivision>\n" +
                "\t\t<balanceDue>386.28</balanceDue>\n" +
                "\t\t<custOrderNumber>0145930505</custOrderNumber>\n" +
                "\t\t<apVendor>1234</apVendor>\n" +
                "\t\t<poHdrData>\n" +
                "\t\t\t<discountTerms discTypeCode=\"05\" discPercent=\"5.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                "\t\t\t<cancelAfterDate>20211207</cancelAfterDate>\n" +
                "\t\t\t<custOrderNumber>0145930505</custOrderNumber>\n" +
                "\t\t\t<poTypeCode>issue</poTypeCode>\n" +
                "\t\t\t<giftMessage>Happy Birthday</giftMessage>\n" +
                "\t\t\t<vendorMessage>IF YOU HAVE ANY PROBLEMS WITH THIS SHIPMENT PLEASE CALL 1-800-111-1111</vendorMessage>\n" +
                "\t\t\t<offerCurrency>USD</offerCurrency>\n" +
                "\t\t</poHdrData>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935035</lineItemId>\n" +
                "\t\t\t<orderLineNumber>1</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>1</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>3</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567811</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 1</description>\n" +
                "\t\t\t<unitPrice>122.2500</unitPrice>\n" +
                "\t\t\t<unitCost>28.4900</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<giftRegistry>123456</giftRegistry>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784045\">\n" +
                "\t\t\t<name1>Albany Store</name1>\n" +
                "\t\t\t<partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784043\">\n" +
                "\t\t\t<name1>Mary Smith</name1>\n" +
                "\t\t\t<address1>1 Stewart St</address1>\n" +
                "\t\t\t<city>Ithaca</city>\n" +
                "\t\t\t<state>NY</state>\n" +
                "\t\t\t<country>US</country>\n" +
                "\t\t\t<postalCode>14850</postalCode>\n" +
                "\t\t\t<email>msmith@test.com</email>\n" +
                "\t\t\t<dayPhone>555-555-5555</dayPhone>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<attnLine>Programming Dept.</attnLine>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784044\">\n" +
                "\t\t\t<name1>Mary Smith</name1>\n" +
                "\t\t\t<address1>1 Stewart St</address1>\n" +
                "\t\t\t<city>Itahca</city>\n" +
                "\t\t\t<state>NY</state>\n" +
                "\t\t\t<country>US</country>\n" +
                "\t\t\t<postalCode>14850</postalCode>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<attnLine>Programming Dept.</attnLine>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<vendorShipInfo vendorShipID=\"VS103536002_3000916015\">\n" +
                "\t\t\t<partnerLocationId>NM</partnerLocationId>\n" +
                "\t\t</vendorShipInfo>\n" +
                "\t</hubOrder>";

        BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
        api.configure("cropbeauty.sftp-test.commercehub.com", "cropbeauty", "Perfectly$Entrance2Foll0w5", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            int orderId = api.importOrderNode(data, "3000916015", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

            CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId + "");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Beautycrop", "vendor", "beautycrop");
            api.setInvoiceNetDaysDue(30);

            System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));


        } catch (Exception e) {
            fail();
        }
    }

    // Cancel entire order
    // Sent 01/19/22
    @Test
    public void BeautyCropTestCase2() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3000916016\">\n" +
                "\t\t<participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "\t\t<sendersIdForReceiver>0000018438</sendersIdForReceiver>\n" +
                "\t\t<orderId>3000916016</orderId>\n" +
                "\t\t<lineCount>1</lineCount>\n" +
                "\t\t<poNumber>00145930509</poNumber>\n" +
                "\t\t<orderDate>20211130</orderDate>\n" +
                "\t\t<shipTo personPlaceID=\"PP3011784046\"/>\n" +
                "\t\t<billTo personPlaceID=\"PP3011784047\"/>\n" +
                "\t\t<shipFrom vendorShipID=\"VS103536003_3000916016\"/>\n" +
                "\t\t<invoiceTo personPlaceID=\"PP3011784048\"/>\n" +
                "\t\t<shippingCode>UPSN_ND</shippingCode>\n" +
                "\t\t<salesDivision>23</salesDivision>\n" +
                "\t\t<balanceDue>99.98</balanceDue>\n" +
                "\t\t<custOrderNumber>0145930510</custOrderNumber>\n" +
                "\t\t<apVendor>12345</apVendor>\n" +
                "\t\t<poHdrData>\n" +
                "\t\t\t<cancelAfterDate>20211207</cancelAfterDate>\n" +
                "\t\t\t<custOrderNumber>0145930510</custOrderNumber>\n" +
                "\t\t\t<poTypeCode>issue</poTypeCode>\n" +
                "\t\t\t<offerCurrency>USD</offerCurrency>\n" +
                "\t\t</poHdrData>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935036</lineItemId>\n" +
                "\t\t\t<orderLineNumber>1</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>6</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>2</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567821</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 1</description>\n" +
                "\t\t\t<unitPrice>49.9900</unitPrice>\n" +
                "\t\t\t<unitCost>30.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_ND</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784046\">\n" +
                "\t\t\t<name1>Jane Smith</name1>\n" +
                "\t\t\t<address1>2 Woodbridge Ave</address1>\n" +
                "\t\t\t<address2>Apartment 2b</address2>\n" +
                "\t\t\t<city>Edison</city>\n" +
                "\t\t\t<state>NJ</state>\n" +
                "\t\t\t<postalCode>08837-9998</postalCode>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>222222222</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784048\">\n" +
                "\t\t\t<name1>Albany Store</name1>\n" +
                "\t\t\t<partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>222222222</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784047\">\n" +
                "\t\t\t<name1>Jane Smith</name1>\n" +
                "\t\t\t<address1>2 Woodbridge Ave</address1>\n" +
                "\t\t\t<address2>Apartment 2b</address2>\n" +
                "\t\t\t<city>Edison</city>\n" +
                "\t\t\t<state>NJ</state>\n" +
                "\t\t\t<postalCode>08837-9998</postalCode>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>222222222</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<vendorShipInfo vendorShipID=\"VS103536003_3000916016\">\n" +
                "\t\t\t<partnerLocationId>NX</partnerLocationId>\n" +
                "\t\t</vendorShipInfo>\n" +
                "\t</hubOrder>";

        BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
        api.configure("cropbeauty.sftp-test.commercehub.com", "cropbeauty", "Perfectly$Entrance2Foll0w5", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            String fileData = api.rejectOrder(data,"3000916016");

            System.out.println(fileData);
        } catch (Exception e) {
            fail();
        }
    }

    // Ship lines 3, 5 | Invoice Entire Order
    // Create inventory for all items
    // Sent 01/19/22
    @Test
    public void BeautyCropTestCase3() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3000916017\">\n" +
                "\t\t<participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "\t\t<sendersIdForReceiver>0000018438</sendersIdForReceiver>\n" +
                "\t\t<orderId>3000916017</orderId>\n" +
                "\t\t<lineCount>2</lineCount>\n" +
                "\t\t<poNumber>00145930514</poNumber>\n" +
                "\t\t<orderDate>20211130</orderDate>\n" +
                "\t\t<tax>4.99</tax>\n" +
                "\t\t<shipping>5.00</shipping>\n" +
                "\t\t<handling>2.00</handling>\n" +
                "\t\t<shipTo personPlaceID=\"PP3011784049\"/>\n" +
                "\t\t<billTo personPlaceID=\"PP3011784050\"/>\n" +
                "\t\t<shipFrom vendorShipID=\"VS103536004_3000916017\"/>\n" +
                "\t\t<invoiceTo personPlaceID=\"PP3011784051\"/>\n" +
                "\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t<salesDivision>11</salesDivision>\n" +
                "\t\t<balanceDue>132.96</balanceDue>\n" +
                "\t\t<custOrderNumber>0145930515</custOrderNumber>\n" +
                "\t\t<poHdrData>\n" +
                "\t\t\t<cancelAfterDate>20211207</cancelAfterDate>\n" +
                "\t\t\t<custOrderNumber>0145930515</custOrderNumber>\n" +
                "\t\t\t<poTypeCode>issue</poTypeCode>\n" +
                "\t\t\t<offerCurrency>USD</offerCurrency>\n" +
                "\t\t</poHdrData>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935037</lineItemId>\n" +
                "\t\t\t<orderLineNumber>1</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>3</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>2</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567831</UPC>\n" +
                "\t\t\t<description>Sample item description for line 1</description>\n" +
                "\t\t\t<unitPrice>42.9900</unitPrice>\n" +
                "\t\t\t<unitCost>30.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935038</lineItemId>\n" +
                "\t\t\t<orderLineNumber>2</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>5</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>1</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567832</UPC>\n" +
                "\t\t\t<description>Sample item description for line 2</description>\n" +
                "\t\t\t<unitPrice>34.9900</unitPrice>\n" +
                "\t\t\t<unitCost>25.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<giftRegistry>264892</giftRegistry>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784050\">\n" +
                "\t\t\t<name1>Bill Johnson</name1>\n" +
                "\t\t\t<address1>3 Falke Plaza</address1>\n" +
                "\t\t\t<city>Sterling</city>\n" +
                "\t\t\t<state>VA</state>\n" +
                "\t\t\t<postalCode>20166</postalCode>\n" +
                "\t\t\t<email>billjohnson@sample.com</email>\n" +
                "\t\t\t<dayPhone>555-555-5555</dayPhone>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>333333333</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784049\">\n" +
                "\t\t\t<name1>Tom Smith</name1>\n" +
                "\t\t\t<address1>3 Westerre Parkway</address1>\n" +
                "\t\t\t<city>Richmond</city>\n" +
                "\t\t\t<state>VA</state>\n" +
                "\t\t\t<postalCode>23233</postalCode>\n" +
                "\t\t\t<email>tomsmith@sample.com</email>\n" +
                "\t\t\t<dayPhone>555-555-5555</dayPhone>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<attnLine>MACY'S/BOSS/WESTFIELD MAINPLAC</attnLine>\n" +
                "\t\t\t\t<ReceiptID>333333333</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784051\">\n" +
                "\t\t\t<name1>Albany Store</name1>\n" +
                "\t\t\t<partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>333333333</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<vendorShipInfo vendorShipID=\"VS103536004_3000916017\">\n" +
                "\t\t\t<partnerLocationId>SV</partnerLocationId>\n" +
                "\t\t</vendorShipInfo>\n" +
                "\t</hubOrder>";

        BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
        api.configure("cropbeauty.sftp-test.commercehub.com", "cropbeauty", "Perfectly$Entrance2Foll0w5", "macys", vendorName, "macys", clientID);
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

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Beautycrop", "vendor", "beautycrop");
            api.setInvoiceNetDaysDue(30);

            System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));
        } catch (Exception e) {
            fail();
        }
    }

    // Cancel entire order
    // Sent 01/19/22
    @Test
    public void BeautyCropTestCase4() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3000916018\">\n" +
                "\t\t<participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "\t\t<sendersIdForReceiver>0000018438</sendersIdForReceiver>\n" +
                "\t\t<orderId>3000916018</orderId>\n" +
                "\t\t<lineCount>2</lineCount>\n" +
                "\t\t<poNumber>00145930520</poNumber>\n" +
                "\t\t<orderDate>20211130</orderDate>\n" +
                "\t\t<shipTo personPlaceID=\"PP3011784052\"/>\n" +
                "\t\t<billTo personPlaceID=\"PP3011784053\"/>\n" +
                "\t\t<shipFrom vendorShipID=\"VS2000021_3000916018\"/>\n" +
                "\t\t<invoiceTo personPlaceID=\"PP3011784054\"/>\n" +
                "\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t<salesDivision>15</salesDivision>\n" +
                "\t\t<balanceDue>249.94</balanceDue>\n" +
                "\t\t<custOrderNumber>0145930521</custOrderNumber>\n" +
                "\t\t<poHdrData>\n" +
                "\t\t\t<cancelAfterDate>20211207</cancelAfterDate>\n" +
                "\t\t\t<custOrderNumber>0145930521</custOrderNumber>\n" +
                "\t\t\t<poTypeCode>issue</poTypeCode>\n" +
                "\t\t\t<offerCurrency>USD</offerCurrency>\n" +
                "\t\t</poHdrData>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935039</lineItemId>\n" +
                "\t\t\t<orderLineNumber>1</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>1</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>5</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567841</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 1</description>\n" +
                "\t\t\t<unitPrice>42.9900</unitPrice>\n" +
                "\t\t\t<unitCost>30.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935040</lineItemId>\n" +
                "\t\t\t<orderLineNumber>2</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>2</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>1</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567842</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 2</description>\n" +
                "\t\t\t<unitPrice>49.9900</unitPrice>\n" +
                "\t\t\t<unitCost>25.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784053\">\n" +
                "\t\t\t<name1>Mike Jones</name1>\n" +
                "\t\t\t<address1>4 La Jolla Village Dr</address1>\n" +
                "\t\t\t<city>San Diego</city>\n" +
                "\t\t\t<state>CA</state>\n" +
                "\t\t\t<postalCode>92122-</postalCode>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>444444444</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784052\">\n" +
                "\t\t\t<name1>Joe Sample</name1>\n" +
                "\t\t\t<address1>4 Jamboree Road</address1>\n" +
                "\t\t\t<city>Newport Beach</city>\n" +
                "\t\t\t<state>CA</state>\n" +
                "\t\t\t<postalCode>92660-</postalCode>\n" +
                "\t\t\t<email>joesample@sample.com</email>\n" +
                "\t\t\t<dayPhone>555-555-5555</dayPhone>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<attnLine>Care of: Jane Sample</attnLine>\n" +
                "\t\t\t\t<ReceiptID>444444444</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784054\">\n" +
                "\t\t\t<name1>Albany Store</name1>\n" +
                "\t\t\t<partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>444444444</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<vendorShipInfo vendorShipID=\"VS2000021_3000916018\"/>\n" +
                "\t</hubOrder>";

        BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
        api.configure("cropbeauty.sftp-test.commercehub.com", "cropbeauty", "Perfectly$Entrance2Foll0w5", "macys", vendorName, "macys", clientID);
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
    // Sent 01/19/22
    @Test
    public void BeautyCropTestCase5() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3000916019\">\n" +
                "\t\t<participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "\t\t<sendersIdForReceiver>0000018438</sendersIdForReceiver>\n" +
                "\t\t<orderId>3000916019</orderId>\n" +
                "\t\t<lineCount>2</lineCount>\n" +
                "\t\t<poNumber>00145930526</poNumber>\n" +
                "\t\t<orderDate>20211130</orderDate>\n" +
                "\t\t<shipTo personPlaceID=\"PP3011784055\"/>\n" +
                "\t\t<billTo personPlaceID=\"PP3011784056\"/>\n" +
                "\t\t<shipFrom vendorShipID=\"VS103536002_3000916019\"/>\n" +
                "\t\t<invoiceTo personPlaceID=\"PP3011784057\"/>\n" +
                "\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t<salesDivision>21</salesDivision>\n" +
                "\t\t<balanceDue>375.00</balanceDue>\n" +
                "\t\t<custOrderNumber>0145930527</custOrderNumber>\n" +
                "\t\t<poHdrData>\n" +
                "\t\t\t<cancelAfterDate>20211207</cancelAfterDate>\n" +
                "\t\t\t<packListData>\n" +
                "\t\t\t\t<rmEmail>cs@abc.com</rmEmail>\n" +
                "\t\t\t\t<rmPhone1>555-555-5555</rmPhone1>\n" +
                "\t\t\t</packListData>\n" +
                "\t\t\t<custOrderNumber>0145930527</custOrderNumber>\n" +
                "\t\t\t<poTypeCode>issue</poTypeCode>\n" +
                "\t\t\t<URL type=\"customer-service\">https://www.url.com/contactus</URL>\n" +
                "\t\t\t<offerCurrency>USD</offerCurrency>\n" +
                "\t\t</poHdrData>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935041</lineItemId>\n" +
                "\t\t\t<orderLineNumber>1</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>1</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>5</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567971</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 1</description>\n" +
                "\t\t\t<unitPrice>75.0000</unitPrice>\n" +
                "\t\t\t<unitCost>50.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935042</lineItemId>\n" +
                "\t\t\t<orderLineNumber>2</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>4</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>2</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567972</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 2</description>\n" +
                "\t\t\t<unitPrice>49.9900</unitPrice>\n" +
                "\t\t\t<unitCost>25.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784056\">\n" +
                "\t\t\t<name1>Mary Doe</name1>\n" +
                "\t\t\t<address1>5 Busch Blvd</address1>\n" +
                "\t\t\t<city>Tampa</city>\n" +
                "\t\t\t<state>FL</state>\n" +
                "\t\t\t<country>US</country>\n" +
                "\t\t\t<postalCode>33612</postalCode>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>555555555</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784055\">\n" +
                "\t\t\t<name1>Mary Doe</name1>\n" +
                "\t\t\t<address1>5 Busch Blvd</address1>\n" +
                "\t\t\t<city>Tampa</city>\n" +
                "\t\t\t<state>FL</state>\n" +
                "\t\t\t<country>US</country>\n" +
                "\t\t\t<postalCode>33612</postalCode>\n" +
                "\t\t\t<dayPhone>555-555-5555</dayPhone>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<attnLine>Attn: Engineering Dept.</attnLine>\n" +
                "\t\t\t\t<ReceiptID>555555555</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784057\">\n" +
                "\t\t\t<name1>Albany Store</name1>\n" +
                "\t\t\t<partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>555555555</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<vendorShipInfo vendorShipID=\"VS103536002_3000916019\">\n" +
                "\t\t\t<partnerLocationId>NM</partnerLocationId>\n" +
                "\t\t</vendorShipInfo>\n" +
                "\t</hubOrder>";

        BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
        api.configure("cropbeauty.sftp-test.commercehub.com", "cropbeauty", "Perfectly$Entrance2Foll0w5", "macys", vendorName, "macys", clientID);
        api.setUseUPCLookup(true);

        try {
            Object data = new XmlSlurper().parseText(poData);
            String fileData = api.rejectOrder(data,"3000916016");

            System.out.println(fileData);
        } catch (Exception e) {
            fail();
        }
    }

    // Ship line 1, 2 | Invoice entire order
    // Create inventory for all items
    // Sent 01/19/22
    @Test
    public void BeautyCropTestCase6() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3000916020\">\n" +
                "\t\t<participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "\t\t<sendersIdForReceiver>0000018438</sendersIdForReceiver>\n" +
                "\t\t<orderId>3000916020</orderId>\n" +
                "\t\t<lineCount>2</lineCount>\n" +
                "\t\t<poNumber>00145930532</poNumber>\n" +
                "\t\t<orderDate>20211130</orderDate>\n" +
                "\t\t<tax>12.53</tax>\n" +
                "\t\t<shipping>5.00</shipping>\n" +
                "\t\t<handling>2.00</handling>\n" +
                "\t\t<shipTo personPlaceID=\"PP3011784058\"/>\n" +
                "\t\t<billTo personPlaceID=\"PP3011784059\"/>\n" +
                "\t\t<shipFrom vendorShipID=\"VS103536003_3000916020\"/>\n" +
                "\t\t<invoiceTo personPlaceID=\"PP3011784060\"/>\n" +
                "\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t<salesDivision>16</salesDivision>\n" +
                "\t\t<balanceDue>630.78</balanceDue>\n" +
                "\t\t<custOrderNumber>0145930533</custOrderNumber>\n" +
                "\t\t<apVendor>12345</apVendor>\n" +
                "\t\t<poHdrData>\n" +
                "\t\t\t<discountTerms discTypeCode=\"05\" discPercent=\"2.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                "\t\t\t<cancelAfterDate>20211207</cancelAfterDate>\n" +
                "\t\t\t<custOrderNumber>0145930533</custOrderNumber>\n" +
                "\t\t\t<poTypeCode>issue</poTypeCode>\n" +
                "\t\t\t<giftMessage>Happy Birthday</giftMessage>\n" +
                "\t\t\t<offerCurrency>USD</offerCurrency>\n" +
                "\t\t</poHdrData>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935043</lineItemId>\n" +
                "\t\t\t<orderLineNumber>1</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>1</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>5</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567912</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 1</description>\n" +
                "\t\t\t<unitPrice>122.2500</unitPrice>\n" +
                "\t\t\t<unitCost>28.4900</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>2234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<giftRegistry>123456</giftRegistry>\n" +
                "\t\t\t\t<merchDept>2234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935044</lineItemId>\n" +
                "\t\t\t<orderLineNumber>2</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>2</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>3</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567842</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 2</description>\n" +
                "\t\t\t<unitPrice>34.9900</unitPrice>\n" +
                "\t\t\t<unitCost>25.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784058\">\n" +
                "\t\t\t<name1>Lisa Smith</name1>\n" +
                "\t\t\t<address1>6 West Loop South</address1>\n" +
                "\t\t\t<city>Houston</city>\n" +
                "\t\t\t<state>TX</state>\n" +
                "\t\t\t<postalCode>77027</postalCode>\n" +
                "\t\t\t<email>lisasmith@sample.com</email>\n" +
                "\t\t\t<dayPhone>555-555-5555</dayPhone>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784060\">\n" +
                "\t\t\t<name1>Albany Store</name1>\n" +
                "\t\t\t<partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784059\">\n" +
                "\t\t\t<name1>Mary Doe</name1>\n" +
                "\t\t\t<address1>6 Quorum Drive</address1>\n" +
                "\t\t\t<city>Dallas</city>\n" +
                "\t\t\t<state>TX</state>\n" +
                "\t\t\t<postalCode>75240</postalCode>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<vendorShipInfo vendorShipID=\"VS103536003_3000916020\">\n" +
                "\t\t\t<partnerLocationId>NX</partnerLocationId>\n" +
                "\t\t</vendorShipInfo>\n" +
                "\t</hubOrder>";

        BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
        api.configure("cropbeauty.sftp-test.commercehub.com", "cropbeauty", "Perfectly$Entrance2Foll0w5", "macys", vendorName, "macys", clientID);
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

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Beautycrop", "vendor", "beautycrop");
            api.setInvoiceNetDaysDue(30);

            System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));
        } catch (Exception e) {
            fail();
        }
    }

    // Cancel line 10 | Ship and Invoice 1 - 9
    // Create inventory for all items
    // Ensure line 10 is out of stock and back order rule is set to reject
    // Sent 01/19/22
    @Test
    public void BeautyCropTestCase7() {
        System.setProperty("com.owd.environment", "test");
        String poData = "<hubOrder transactionID=\"3000916021\">\n" +
                "\t\t<participatingParty name=\"Macy's\" roleType=\"merchant\" participationCode=\"From:\">macys</participatingParty>\n" +
                "\t\t<sendersIdForReceiver>0000018438</sendersIdForReceiver>\n" +
                "\t\t<orderId>3000916021</orderId>\n" +
                "\t\t<lineCount>10</lineCount>\n" +
                "\t\t<poNumber>00145930538</poNumber>\n" +
                "\t\t<orderDate>20211130</orderDate>\n" +
                "\t\t<tax>12.53</tax>\n" +
                "\t\t<shipping>5.00</shipping>\n" +
                "\t\t<handling>2.00</handling>\n" +
                "\t\t<shipTo personPlaceID=\"PP3011784061\"/>\n" +
                "\t\t<billTo personPlaceID=\"PP3011784062\"/>\n" +
                "\t\t<shipFrom vendorShipID=\"VS103536004_3000916021\"/>\n" +
                "\t\t<invoiceTo personPlaceID=\"PP3011784063\"/>\n" +
                "\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t<salesDivision>11</salesDivision>\n" +
                "\t\t<balanceDue>257.53</balanceDue>\n" +
                "\t\t<custOrderNumber>0145930539</custOrderNumber>\n" +
                "\t\t<apVendor>12345</apVendor>\n" +
                "\t\t<poHdrData>\n" +
                "\t\t\t<discountTerms discTypeCode=\"05\" discPercent=\"2.0\" discDaysDue=\"15\" netDaysDue=\"30\">1.0</discountTerms>\n" +
                "\t\t\t<cancelAfterDate>20211207</cancelAfterDate>\n" +
                "\t\t\t<custOrderNumber>0145930539</custOrderNumber>\n" +
                "\t\t\t<poTypeCode>issue</poTypeCode>\n" +
                "\t\t\t<giftMessage>Happy Birthday</giftMessage>\n" +
                "\t\t\t<vendorMessage>IF YOU HAVE ANY PROBLEMS WITH THIS SHIPMENT PLEASE CALL 1-800-111-1111</vendorMessage>\n" +
                "\t\t\t<offerCurrency>USD</offerCurrency>\n" +
                "\t\t</poHdrData>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935045</lineItemId>\n" +
                "\t\t\t<orderLineNumber>1</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>1</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>10</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567971</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 1</description>\n" +
                "\t\t\t<unitPrice>2.5000</unitPrice>\n" +
                "\t\t\t<unitCost>2.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935046</lineItemId>\n" +
                "\t\t\t<orderLineNumber>2</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>2</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>9</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567972</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 2</description>\n" +
                "\t\t\t<unitPrice>3.0000</unitPrice>\n" +
                "\t\t\t<unitCost>2.5000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935047</lineItemId>\n" +
                "\t\t\t<orderLineNumber>3</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>3</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>8</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567973</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 3</description>\n" +
                "\t\t\t<unitPrice>4.0000</unitPrice>\n" +
                "\t\t\t<unitCost>3.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935048</lineItemId>\n" +
                "\t\t\t<orderLineNumber>4</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>4</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>7</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567974</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 4</description>\n" +
                "\t\t\t<unitPrice>4.5000</unitPrice>\n" +
                "\t\t\t<unitCost>4.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935049</lineItemId>\n" +
                "\t\t\t<orderLineNumber>5</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>5</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>6</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567975</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 5</description>\n" +
                "\t\t\t<unitPrice>5.0000</unitPrice>\n" +
                "\t\t\t<unitCost>4.5000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935050</lineItemId>\n" +
                "\t\t\t<orderLineNumber>6</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>6</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>5</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567976</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 6</description>\n" +
                "\t\t\t<unitPrice>5.5000</unitPrice>\n" +
                "\t\t\t<unitCost>5.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935051</lineItemId>\n" +
                "\t\t\t<orderLineNumber>7</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>7</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>4</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567977</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 7</description>\n" +
                "\t\t\t<unitPrice>6.0000</unitPrice>\n" +
                "\t\t\t<unitCost>5.5000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935052</lineItemId>\n" +
                "\t\t\t<orderLineNumber>8</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>8</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>3</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567978</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 8</description>\n" +
                "\t\t\t<unitPrice>6.5000</unitPrice>\n" +
                "\t\t\t<unitCost>6.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935053</lineItemId>\n" +
                "\t\t\t<orderLineNumber>9</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>9</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>2</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567979</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 9</description>\n" +
                "\t\t\t<unitPrice>7.0000</unitPrice>\n" +
                "\t\t\t<unitCost>6.5000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>1234567</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<lineItem>\n" +
                "\t\t\t<lineItemId>3000935054</lineItemId>\n" +
                "\t\t\t<orderLineNumber>10</orderLineNumber>\n" +
                "\t\t\t<merchantLineNumber>10</merchantLineNumber>\n" +
                "\t\t\t<qtyOrdered>1</qtyOrdered>\n" +
                "\t\t\t<unitOfMeasure>EA</unitOfMeasure>\n" +
                "\t\t\t<UPC>0001234567970</UPC>\n" +
                "\t\t\t<description>Sample Item Description for Line 10</description>\n" +
                "\t\t\t<unitPrice>7.5000</unitPrice>\n" +
                "\t\t\t<unitCost>7.0000</unitCost>\n" +
                "\t\t\t<shippingCode>UPSN_CG</shippingCode>\n" +
                "\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t<expectedShipDate>20211203</expectedShipDate>\n" +
                "\t\t\t<poLineData>\n" +
                "\t\t\t\t<merchDept>7654321</merchDept>\n" +
                "\t\t\t</poLineData>\n" +
                "\t\t</lineItem>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784062\">\n" +
                "\t\t\t<name1>Paula Jones</name1>\n" +
                "\t\t\t<address1>Apt 9</address1>\n" +
                "\t\t\t<address2>9 Main St</address2>\n" +
                "\t\t\t<city>Moab</city>\n" +
                "\t\t\t<state>UT</state>\n" +
                "\t\t\t<country>US</country>\n" +
                "\t\t\t<postalCode>84532</postalCode>\n" +
                "\t\t\t<email>pjones@sample.com</email>\n" +
                "\t\t\t<dayPhone>555-555-5555</dayPhone>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784061\">\n" +
                "\t\t\t<name1>Mike Jones</name1>\n" +
                "\t\t\t<address1>Suite 9 - High Towers</address1>\n" +
                "\t\t\t<address2>9 Wakara Way</address2>\n" +
                "\t\t\t<city>Salt Lake City</city>\n" +
                "\t\t\t<state>UT</state>\n" +
                "\t\t\t<postalCode>84108</postalCode>\n" +
                "\t\t\t<email>mikejones@sample.com</email>\n" +
                "\t\t\t<dayPhone>555-555-5555</dayPhone>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<personPlace personPlaceID=\"PP3011784063\">\n" +
                "\t\t\t<name1>Albany Store</name1>\n" +
                "\t\t\t<partnerPersonPlaceId>1234</partnerPersonPlaceId>\n" +
                "\t\t\t<personPlaceData>\n" +
                "\t\t\t\t<ReceiptID>111111111</ReceiptID>\n" +
                "\t\t\t</personPlaceData>\n" +
                "\t\t</personPlace>\n" +
                "\t\t<vendorShipInfo vendorShipID=\"VS103536004_3000916021\">\n" +
                "\t\t\t<partnerLocationId>SV</partnerLocationId>\n" +
                "\t\t</vendorShipInfo>\n" +
                "\t</hubOrder>";

        BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
        api.configure("cropbeauty.sftp-test.commercehub.com", "cropbeauty", "Perfectly$Entrance2Foll0w5", "macys", vendorName, "macys", clientID);
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

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("Beautycrop", "vendor", "beautycrop");
            api.setInvoiceNetDaysDue(30);

            System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));


        } catch (Exception e) {
            fail();
        }
    }

    // Upload inventory | Discontinue an item
    @Test
    public void BeautyCropTestCase8() {
        System.setProperty("com.owd.environment", "test");
        try {
            BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
            api.configure("cropbeauty.sftp-test.commercehub.com", "cropbeauty", "Perfectly$Entrance2Foll0w5", "macys", vendorName, "macys", clientID);
            api.pushInventoryFile();
        }
        catch(Exception x)
        {
            fail();
        }
    }
}

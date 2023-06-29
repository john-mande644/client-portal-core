package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsBedBathCommerceHubAPI;
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
public class JustBrandsBedBathTesting {
    private final static Logger log = LogManager.getLogger();


        @Test
        public void QVCInventoryTest(){
                System.setProperty("com.owd.environment","test");
                JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
                log.debug(api.getInventoryFile());
                api.pushInventoryFile();



        }

    @Test
    public  void BedBathBeyondTestCase1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120898859\">\n" +
                "  <participatingParty name=\"Bed Bath &amp; Beyond\" roleType=\"merchant\" participationCode=\"From:\">bedbath</participatingParty>\n" +
                "  <sendersIdForReceiver>68946</sendersIdForReceiver>\n" +
                "  <orderId>120898859</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>AB35420503</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>1234#5678</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007810195\" />\n" +
                "  <billTo personPlaceID=\"PP3007810196\" />\n" +
                "  <shipFrom vendorShipID=\"VS100669004_120898859\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>BBB</salesDivision>\n" +
                "  <vendorWarehouseId>12345</vendorWarehouseId>\n" +
                "  <custOrderNumber>CCC255731</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <buyingContract>123</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>Thank you for shopping online.[.] Delimiter and new line test.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<warehouseID>12345</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255731</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<giftMessage>Happy Holidays</giftMessage>\n" +
                "    \n" +
                "\t<merchDivision>659</merchDivision>\n" +
                "    \n" +
                "\t<customerPaymentType>CC#GC</customerPaymentType>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121007929</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990101</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>99914146393</merchantSKU>\n" +
                "    <vendorSKU>14146393</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20190624</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <prodColor>Blue</prodColor>\n" +
                "      <prodSize>King Set</prodSize>\n" +
                "      <lineNote1>Line level packslip message.[.]Delimiter and new line test.</lineNote1>\n" +
                "      <giftRegistry>123456789</giftRegistry>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007810195\">\n" +
                "    <name1>John</name1>\n" +
                "    <name2>Doe</name2>\n" +
                "    <address1>1 Fuller Rd</address1>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <email>johndoe@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>ABC Company Name</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007810196\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>1 Stewart St</address1>\n" +
                "    <city>Ithaca</city>\n" +
                "    <state>NY</state>\n" +
                "    <postalCode>14850</postalCode>\n" +
                "    <email>marysmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>XYZ Company</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100669004_120898859\">\n" +
                "    <address1>1001 W. Middlesex Road</address1>\n" +
                "    <city>Port Reading</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>07064</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
       // api.setGroupName("bedbath");
       // api.setPackingSlipTemplate("iqcv");
       // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003178217", false);
            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


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



        }catch (Exception e){
            fail();
        }








    }
    @Test
    public  void BedBathBeyondTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120898860\">\n" +
                "  <participatingParty name=\"Bed Bath &amp; Beyond\" roleType=\"merchant\" participationCode=\"From:\">bedbath</participatingParty>\n" +
                "  <sendersIdForReceiver>68946</sendersIdForReceiver>\n" +
                "  <orderId>120898860</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>AB35420506a</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>GC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007810197\" />\n" +
                "  <billTo personPlaceID=\"PP3007810198\" />\n" +
                "  <shipFrom vendorShipID=\"VS100669004_120898860\" />\n" +
                "  <shippingCode>UPSN_SE</shippingCode>\n" +
                "  <salesDivision>BAB</salesDivision>\n" +
                "  <custOrderNumber>CCC255732</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <buyingContract>123</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>Thank you for shopping online.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255732</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<merchDivision>659</merchDivision>\n" +
                "    \n" +
                "\t<customerPaymentType>GC</customerPaymentType>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121007930</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990201</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>99910000025</merchantSKU>\n" +
                "    <vendorSKU>10000025</vendorSKU>\n" +
                "    <unitPrice>17.9900</unitPrice>\n" +
                "    <unitCost>14.3900</unitCost>\n" +
                "    <shippingCode>UPSN_SE</shippingCode>\n" +
                "    <poLineData>\n" +
                "      <lineNote1>Hope these match everything!</lineNote1>\n" +
                "      <giftRegistry>13579</giftRegistry>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007810198\">\n" +
                "    <name1>Jane</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <email>janesmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>XYZ Company</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007810197\">\n" +
                "    <name1>Jane</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <email>janesmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>ABC Company Name</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100669004_120898860\">\n" +
                "    <address1>1001 W. Middlesex Road</address1>\n" +
                "    <city>Port Reading</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>07064</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
        // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003178217", false);
           // PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


           // log.debug(orderId);
/*
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setBatchRef("3003178217");
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
    public  void BedBathBeyondTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120898861\">\n" +
                "  <participatingParty name=\"Bed Bath &amp; Beyond\" roleType=\"merchant\" participationCode=\"From:\">bedbath</participatingParty>\n" +
                "  <sendersIdForReceiver>68946</sendersIdForReceiver>\n" +
                "  <orderId>120898861</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>AB35420509</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>GC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007810199\" />\n" +
                "  <billTo personPlaceID=\"PP3007810200\" />\n" +
                "  <shipFrom vendorShipID=\"VS100669004_120898861\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>OKL</salesDivision>\n" +
                "  <custOrderNumber>CCC255733</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <buyingContract>123</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>Thank you for shopping online.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255733</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<giftMessage>Happy Holidays</giftMessage>\n" +
                "    \n" +
                "\t<merchDivision>659</merchDivision>\n" +
                "    \n" +
                "\t<customerPaymentType>GC</customerPaymentType>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121007931</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990101</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>99914146393</merchantSKU>\n" +
                "    <vendorSKU>14146393</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20190624</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <prodColor>Blue</prodColor>\n" +
                "      <prodSize>King Set</prodSize>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121007932</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990201</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>99910000025</merchantSKU>\n" +
                "    <vendorSKU>10000025</vendorSKU>\n" +
                "    <unitPrice>100.0000</unitPrice>\n" +
                "    <unitCost>80.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20190626</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007810200\">\n" +
                "    <name1>Bill</name1>\n" +
                "    <name2>Johnson</name2>\n" +
                "    <address1>3 Falke Plaza</address1>\n" +
                "    <address2>Apartment 3B</address2>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>20166</postalCode>\n" +
                "    <email>billjohnson@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>XYZ Company</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007810199\">\n" +
                "    <name1>Tom</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>3 Westerre Parkway</address1>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <email>tomsmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>ABC Company Name</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100669004_120898861\">\n" +
                "    <address1>1001 W. Middlesex Road</address1>\n" +
                "    <city>Port Reading</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>07064</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
        // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003178217", false);
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
        public void sendINvoice3(){
                System.setProperty("com.owd.environment","test");

                JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);

                CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                //api.setInvoiceDiscDaysDue(5);
                api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

                System.out.println(api.sendInvoiceToCommerceHub(18023255, invoice));
        }

    @Test
    public  void BedBathBeyondTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120898862\">\n" +
                "  <participatingParty name=\"Bed Bath &amp; Beyond\" roleType=\"merchant\" participationCode=\"From:\">bedbath</participatingParty>\n" +
                "  <sendersIdForReceiver>68946</sendersIdForReceiver>\n" +
                "  <orderId>120898862</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>AB35420512</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>CC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007810201\" />\n" +
                "  <billTo personPlaceID=\"PP3007810202\" />\n" +
                "  <shipFrom vendorShipID=\"VS100669004_120898862\" />\n" +
                "  <shippingCode>UPSN_SE</shippingCode>\n" +
                "  <salesDivision>BBB</salesDivision>\n" +
                "  <vendorWarehouseId>12345</vendorWarehouseId>\n" +
                "  <custOrderNumber>123CAN255734</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <buyingContract>123</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>Thank you for shopping online.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<warehouseID>12345</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>123CAN255734</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<merchDivision>659</merchDivision>\n" +
                "    \n" +
                "\t<customerPaymentType>CC</customerPaymentType>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121007933</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990101</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>99914146393</merchantSKU>\n" +
                "    <vendorSKU>14146393</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <shippingCode>UPSN_SE</shippingCode>\n" +
                "    <poLineData>\n" +
                "      <prodColor>Blue</prodColor>\n" +
                "      <prodSize>King Set</prodSize>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121007934</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990201</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>99910000025</merchantSKU>\n" +
                "    <vendorSKU>10000025</vendorSKU>\n" +
                "    <unitPrice>9.9900</unitPrice>\n" +
                "    <unitCost>7.9900</unitCost>\n" +
                "    <shippingCode>UPSN_SE</shippingCode>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007810202\">\n" +
                "    <name1>Mike</name1>\n" +
                "    <name2>Jones</name2>\n" +
                "    <address1>4 200th St</address1>\n" +
                "    <city>Langley</city>\n" +
                "    <state>BC</state>\n" +
                "    <country>CA</country>\n" +
                "    <postalCode>V2Y1A2</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007810201\">\n" +
                "    <name1>Joe</name1>\n" +
                "    <name2>Sample</name2>\n" +
                "    <address1>4 Westmere Rd</address1>\n" +
                "    <city>Harrisville</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>13648</postalCode>\n" +
                "    <email>joesample@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>ABC Company</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100669004_120898862\">\n" +
                "    <address1>1001 W. Middlesex Road</address1>\n" +
                "    <city>Port Reading</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>07064</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
        // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003178217", false);
            // PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


            // log.debug(orderId);
/*
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setBatchRef("3003178217");
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
    public  void BedBathBeyondTestCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120898863\">\n" +
                "  <participatingParty name=\"Bed Bath &amp; Beyond\" roleType=\"merchant\" participationCode=\"From:\">bedbath</participatingParty>\n" +
                "  <sendersIdForReceiver>68946</sendersIdForReceiver>\n" +
                "  <orderId>120898863</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>AB35420515</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>CC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007810203\" />\n" +
                "  <billTo personPlaceID=\"PP3007810204\" />\n" +
                "  <shipFrom vendorShipID=\"VS100669004_120898863\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>BAB</salesDivision>\n" +
                "  <custOrderNumber>CCC255735</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <buyingContract>123</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>Thank you for shopping online.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255735</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<giftMessage>Happy Holidays</giftMessage>\n" +
                "    \n" +
                "\t<merchDivision>659</merchDivision>\n" +
                "    \n" +
                "\t<customerPaymentType>CC</customerPaymentType>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121007935</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990201</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>99910000025</merchantSKU>\n" +
                "    <vendorSKU>10000025</vendorSKU>\n" +
                "    <unitPrice>148.0000</unitPrice>\n" +
                "    <unitCost>118.4000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121007936</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990101</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>99914146393</merchantSKU>\n" +
                "    <vendorSKU>14146393</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <poLineData>\n" +
                "      <prodColor>Blue</prodColor>\n" +
                "      <prodSize>King Set</prodSize>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007810204\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Doe</name2>\n" +
                "    <address1>5 Busch Blvd</address1>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <email>marydoe@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>XYZ Company</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007810203\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Doe</name2>\n" +
                "    <address1>5 Busch Blvd</address1>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <email>jeffferguson@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      <companyName>ABC Company Name</companyName>\n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100669004_120898863\">\n" +
                "    <address1>1001 W. Middlesex Road</address1>\n" +
                "    <city>Port Reading</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>07064</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
        // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003178217", false);
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
        public  void BedBathBeyondTestCase6(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120898864\">\n" +
                        "  <participatingParty name=\"Bed Bath &amp; Beyond\" roleType=\"merchant\" participationCode=\"From:\">bedbath</participatingParty>\n" +
                        "  <sendersIdForReceiver>68946</sendersIdForReceiver>\n" +
                        "  <orderId>120898864</orderId>\n" +
                        "  <lineCount>2</lineCount>\n" +
                        "  <poNumber>AB35420518</poNumber>\n" +
                        "  <orderDate>20190621</orderDate>\n" +
                        "  <paymentMethod>GC</paymentMethod>\n" +
                        "  <shipTo personPlaceID=\"PP3007810205\" />\n" +
                        "  <billTo personPlaceID=\"PP3007810206\" />\n" +
                        "  <shipFrom vendorShipID=\"VS100669004_120898864\" />\n" +
                        "  <shippingCode>UPSN_CG</shippingCode>\n" +
                        "  <salesDivision>BBB</salesDivision>\n" +
                        "  <custOrderNumber>CCC255736</custOrderNumber>\n" +
                        "  <custOrderDate>20190621</custOrderDate>\n" +
                        "  <buyingContract>123</buyingContract>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<packListData>\n" +
                        "      \n" +
                        "\t\t<packslipMessage>Thank you for shopping online.</packslipMessage>\n" +
                        "      \n" +
                        "\t\n" +
                        "    </packListData>\n" +
                        "    \n" +
                        "\t<custOrderNumber>CCC255736</custOrderNumber>\n" +
                        "    \n" +
                        "\t<custOrderDate>20190621</custOrderDate>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\t<merchDivision>659</merchDivision>\n" +
                        "    \n" +
                        "\t<customerPaymentType>GC</customerPaymentType>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007937</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990101</UPC>\n" +
                        "    <description>Sample item description for line 2</description>\n" +
                        "    <merchantSKU>99914146393</merchantSKU>\n" +
                        "    <vendorSKU>14146393</vendorSKU>\n" +
                        "    <unitPrice>59.9900</unitPrice>\n" +
                        "    <unitCost>47.9900</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <poLineData>\n" +
                        "      <prodColor>Blue</prodColor>\n" +
                        "      <prodSize>King Set</prodSize>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007938</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990201</UPC>\n" +
                        "    <description>Sample item description for line 2</description>\n" +
                        "    <merchantSKU>99910000025</merchantSKU>\n" +
                        "    <vendorSKU>10000025</vendorSKU>\n" +
                        "    <unitPrice>29.9900</unitPrice>\n" +
                        "    <unitCost>23.9900</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007810205\">\n" +
                        "    <name1>Lisa</name1>\n" +
                        "    <name2>Smith</name2>\n" +
                        "    <address1>6 West Loop South</address1>\n" +
                        "    <city>Houston</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <postalCode>77027</postalCode>\n" +
                        "    <email>lisasmith@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <companyName>ABC Company Name</companyName>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007810206\">\n" +
                        "    <name1>Mary</name1>\n" +
                        "    <name2>Doe</name2>\n" +
                        "    <address1>6 Quorum Drive</address1>\n" +
                        "    <city>Dallas</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <postalCode>75240</postalCode>\n" +
                        "    <email>marydoe@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <companyName>XYZ Company</companyName>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS100669004_120898864\">\n" +
                        "    <address1>1001 W. Middlesex Road</address1>\n" +
                        "    <city>Port Reading</city>\n" +
                        "    <state>NJ</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>07064</postalCode>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
                // api.setGroupName("bedbath");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003178217", false);
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
        public  void BedBathBeyondTestCase6SendDocs() {
                System.setProperty("com.owd.environment", "test");

                JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);

                CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                OrderStatus os = new OrderStatus(18023258+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());

                api.confirmOrderShipment(os);


                CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                //api.setInvoiceDiscDaysDue(5);
                api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

                System.out.println(api.sendInvoiceToCommerceHub(18023258, invoice));


                try {
                         PackingManager.packAndShip(18023259, 2, 0.00, "1Z0000000000000000", false);
                }catch (Exception e){
                        e.printStackTrace();

                }

              confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                 os = new OrderStatus(18023259+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);


                //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                //api.setInvoiceDiscDaysDue(5);
                api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

                System.out.println(api.sendInvoiceToCommerceHub(18023259, invoice));






        }


        @Test
        public  void BedBathBeyondTestCase7(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120898865\">\n" +
                        "  <participatingParty name=\"Bed Bath &amp; Beyond\" roleType=\"merchant\" participationCode=\"From:\">bedbath</participatingParty>\n" +
                        "  <sendersIdForReceiver>68946</sendersIdForReceiver>\n" +
                        "  <orderId>120898865</orderId>\n" +
                        "  <lineCount>10</lineCount>\n" +
                        "  <poNumber>AB35420521</poNumber>\n" +
                        "  <orderDate>20190621</orderDate>\n" +
                        "  <paymentMethod>GC#AMEX</paymentMethod>\n" +
                        "  <shipTo personPlaceID=\"PP3007810207\" />\n" +
                        "  <billTo personPlaceID=\"PP3007810208\" />\n" +
                        "  <shipFrom vendorShipID=\"VS100669004_120898865\" />\n" +
                        "  <shippingCode>FXSP</shippingCode>\n" +
                        "  <salesDivision>BBB</salesDivision>\n" +
                        "  <custOrderNumber>CCC255737</custOrderNumber>\n" +
                        "  <custOrderDate>20190621</custOrderDate>\n" +
                        "  <buyingContract>123</buyingContract>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<packListData>\n" +
                        "      \n" +
                        "\t\t<packslipMessage>Thank you for shopping online.</packslipMessage>\n" +
                        "      \n" +
                        "\t\n" +
                        "    </packListData>\n" +
                        "    \n" +
                        "\t<custOrderNumber>CCC255737</custOrderNumber>\n" +
                        "    \n" +
                        "\t<custOrderDate>20190621</custOrderDate>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\t<giftMessage>Happy housewarming! Hope you are settling nicely into your new house! Congratulations and best of luck to you both! Love, Mom and Dad! This message will test a gift message of over 200 characters and spaces.</giftMessage>\n" +
                        "    \n" +
                        "\t<merchDivision>659</merchDivision>\n" +
                        "    \n" +
                        "\t<customerPaymentType>GC#CC</customerPaymentType>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007939</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>10</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990201</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>99910000025</merchantSKU>\n" +
                        "    <vendorSKU>10000025</vendorSKU>\n" +
                        "    <unitPrice>3.9900</unitPrice>\n" +
                        "    <unitCost>3.1900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007940</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>9</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990902</UPC>\n" +
                        "    <description>Sample item description for line 2</description>\n" +
                        "    <merchantSKU>99910000026</merchantSKU>\n" +
                        "    <vendorSKU>10000026</vendorSKU>\n" +
                        "    <unitPrice>4.9900</unitPrice>\n" +
                        "    <unitCost>3.9900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007941</lineItemId>\n" +
                        "    <orderLineNumber>3</orderLineNumber>\n" +
                        "    <merchantLineNumber>3</merchantLineNumber>\n" +
                        "    <qtyOrdered>8</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990903</UPC>\n" +
                        "    <description>Sample item description for line 3</description>\n" +
                        "    <merchantSKU>99910000027</merchantSKU>\n" +
                        "    <vendorSKU>10000027</vendorSKU>\n" +
                        "    <unitPrice>5.9900</unitPrice>\n" +
                        "    <unitCost>4.7900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007942</lineItemId>\n" +
                        "    <orderLineNumber>4</orderLineNumber>\n" +
                        "    <merchantLineNumber>4</merchantLineNumber>\n" +
                        "    <qtyOrdered>7</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990904</UPC>\n" +
                        "    <description>Sample item description for line 4</description>\n" +
                        "    <merchantSKU>99914146391</merchantSKU>\n" +
                        "    <vendorSKU>14146391</vendorSKU>\n" +
                        "    <unitPrice>6.9900</unitPrice>\n" +
                        "    <unitCost>5.5900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007943</lineItemId>\n" +
                        "    <orderLineNumber>5</orderLineNumber>\n" +
                        "    <merchantLineNumber>5</merchantLineNumber>\n" +
                        "    <qtyOrdered>6</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990905</UPC>\n" +
                        "    <description>Sample item description for line 5</description>\n" +
                        "    <merchantSKU>99914146392</merchantSKU>\n" +
                        "    <vendorSKU>14146392</vendorSKU>\n" +
                        "    <unitPrice>7.9900</unitPrice>\n" +
                        "    <unitCost>6.3900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007944</lineItemId>\n" +
                        "    <orderLineNumber>6</orderLineNumber>\n" +
                        "    <merchantLineNumber>6</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990101</UPC>\n" +
                        "    <description>Sample item description for line 6</description>\n" +
                        "    <merchantSKU>99914146393</merchantSKU>\n" +
                        "    <vendorSKU>14146393</vendorSKU>\n" +
                        "    <unitPrice>59.9900</unitPrice>\n" +
                        "    <unitCost>47.9900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData>\n" +
                        "      <prodColor>Blue</prodColor>\n" +
                        "      <prodSize>King Set</prodSize>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007945</lineItemId>\n" +
                        "    <orderLineNumber>7</orderLineNumber>\n" +
                        "    <merchantLineNumber>7</merchantLineNumber>\n" +
                        "    <qtyOrdered>4</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990907</UPC>\n" +
                        "    <description>Sample item description for line 7</description>\n" +
                        "    <merchantSKU>99914146394</merchantSKU>\n" +
                        "    <vendorSKU>14146394</vendorSKU>\n" +
                        "    <unitPrice>9.9900</unitPrice>\n" +
                        "    <unitCost>7.9900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007946</lineItemId>\n" +
                        "    <orderLineNumber>8</orderLineNumber>\n" +
                        "    <merchantLineNumber>8</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990908</UPC>\n" +
                        "    <description>Sample item description for line 8</description>\n" +
                        "    <merchantSKU>99914146395</merchantSKU>\n" +
                        "    <vendorSKU>14146395</vendorSKU>\n" +
                        "    <unitPrice>10.9900</unitPrice>\n" +
                        "    <unitCost>8.7900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007947</lineItemId>\n" +
                        "    <orderLineNumber>9</orderLineNumber>\n" +
                        "    <merchantLineNumber>9</merchantLineNumber>\n" +
                        "    <qtyOrdered>2</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990909</UPC>\n" +
                        "    <description>Sample item description for line 9</description>\n" +
                        "    <merchantSKU>99914146396</merchantSKU>\n" +
                        "    <vendorSKU>14146396</vendorSKU>\n" +
                        "    <unitPrice>11.9900</unitPrice>\n" +
                        "    <unitCost>9.5900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007948</lineItemId>\n" +
                        "    <orderLineNumber>10</orderLineNumber>\n" +
                        "    <merchantLineNumber>10</merchantLineNumber>\n" +
                        "    <qtyOrdered>1</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990910</UPC>\n" +
                        "    <description>Sample item description for line 10</description>\n" +
                        "    <merchantSKU>99914146397</merchantSKU>\n" +
                        "    <vendorSKU>14146397</vendorSKU>\n" +
                        "    <unitPrice>12.9900</unitPrice>\n" +
                        "    <unitCost>10.3900</unitCost>\n" +
                        "    <shippingCode>FXSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007810207\">\n" +
                        "    <name1>John</name1>\n" +
                        "    <name2>Smith</name2>\n" +
                        "    <address1>PSC 901 BOX 50</address1>\n" +
                        "    <city>FPO</city>\n" +
                        "    <state>AE</state>\n" +
                        "    <postalCode>09805</postalCode>\n" +
                        "    <email>Lsmith@commercehub.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <companyName>ABC Company Name</companyName>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007810208\">\n" +
                        "    <name1>Paula</name1>\n" +
                        "    <name2>Jones</name2>\n" +
                        "    <address1>9 Main St</address1>\n" +
                        "    <address2>Apt 9</address2>\n" +
                        "    <city>Moab</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <postalCode>84532</postalCode>\n" +
                        "    <email>pjones@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <companyName>XYZ Company</companyName>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS100669004_120898865\">\n" +
                        "    <address1>1001 W. Middlesex Road</address1>\n" +
                        "    <city>Port Reading</city>\n" +
                        "    <state>NJ</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>07064</postalCode>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
                // api.setGroupName("bedbath");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003178217", false);
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
        public  void BedBathBeyondPackTemplate(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120898859\">\n" +
                        "  <participatingParty name=\"Bed Bath &amp; Beyond\" roleType=\"merchant\" participationCode=\"From:\">bedbath</participatingParty>\n" +
                        "  <sendersIdForReceiver>68946</sendersIdForReceiver>\n" +
                        "  <orderId>120898859</orderId>\n" +
                        "  <lineCount>1</lineCount>\n" +
                        "  <poNumber>AB35420503</poNumber>\n" +
                        "  <orderDate>20190621</orderDate>\n" +
                        "  <paymentMethod>1234#5678</paymentMethod>\n" +
                        "  <shipTo personPlaceID=\"PP3007810195\" />\n" +
                        "  <billTo personPlaceID=\"PP3007810196\" />\n" +
                        "  <shipFrom vendorShipID=\"VS100669004_120898859\" />\n" +
                        "  <shippingCode>UPSN_CG</shippingCode>\n" +
                        "  <salesDivision>BBB</salesDivision>\n" +
                        "  <vendorWarehouseId>12345</vendorWarehouseId>\n" +
                        "  <custOrderNumber>CCC255731</custOrderNumber>\n" +
                        "  <custOrderDate>20190621</custOrderDate>\n" +
                        "  <buyingContract>123</buyingContract>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<packListData>\n" +
                        "      \n" +
                        "\t\t<packslipMessage>Thank you for shopping online.[.] Delimiter and new line test.</packslipMessage>\n" +
                        "      \n" +
                        "\t\n" +
                        "    </packListData>\n" +
                        "    \n" +
                        "\t<warehouseID>12345</warehouseID>\n" +
                        "    \n" +
                        "\t<custOrderNumber>CCC255731</custOrderNumber>\n" +
                        "    \n" +
                        "\t<custOrderDate>20190621</custOrderDate>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\t<giftMessage>Happy Holidays</giftMessage>\n" +
                        "    \n" +
                        "\t<merchDivision>659</merchDivision>\n" +
                        "    \n" +
                        "\t<customerPaymentType>CC#GC</customerPaymentType>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121007929</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990101</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>99914146393</merchantSKU>\n" +
                        "    <vendorSKU>14146393</vendorSKU>\n" +
                        "    <unitPrice>59.9900</unitPrice>\n" +
                        "    <unitCost>47.9900</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190624</expectedShipDate>\n" +
                        "    <poLineData>\n" +
                        "      <prodColor>Blue</prodColor>\n" +
                        "      <prodSize>King Set</prodSize>\n" +
                        "      <lineNote1>Line level packslip message.[.]Delimiter and new line test.</lineNote1>\n" +
                        "      <giftRegistry>123456789</giftRegistry>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007810195\">\n" +
                        "    <name1>John</name1>\n" +
                        "    <name2>Doe</name2>\n" +
                        "    <address1>1 Fuller Rd</address1>\n" +
                        "    <city>Albany</city>\n" +
                        "    <state>NY</state>\n" +
                        "    <postalCode>12203</postalCode>\n" +
                        "    <email>johndoe@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <companyName>ABC Company Name</companyName>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007810196\">\n" +
                        "    <name1>Mary</name1>\n" +
                        "    <name2>Smith</name2>\n" +
                        "    <address1>1 Stewart St</address1>\n" +
                        "    <city>Ithaca</city>\n" +
                        "    <state>NY</state>\n" +
                        "    <postalCode>14850</postalCode>\n" +
                        "    <email>marysmith@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <personPlaceData>\n" +
                        "      <companyName>XYZ Company</companyName>\n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS100669004_120898859\">\n" +
                        "    <address1>1001 W. Middlesex Road</address1>\n" +
                        "    <city>Port Reading</city>\n" +
                        "    <state>NJ</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>07064</postalCode>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","bedbath","Just Brand Limited","bedbath",626);
                // api.setGroupName("bedbath");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);
                        System.out.println(api.getPackingSlipFromTemplateFields(data));






                }catch (Exception e){
                        fail();
                }








        }
}

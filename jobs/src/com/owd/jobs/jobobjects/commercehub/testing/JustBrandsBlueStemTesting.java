package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsBedBathCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsBlueStemCommerceHubAPI;
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
public class JustBrandsBlueStemTesting {
    private final static Logger log = LogManager.getLogger();


        @Test
        public void QVCInventoryTest(){
               // System.setProperty("com.owd.environment","test");
                JustBrandsBlueStemCommerceHubAPI api = new JustBrandsBlueStemCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","bluestem","Just Brand Limited","bluestem",626);
                log.debug(api.getInventoryFile());
                api.pushInventoryFile();



        }

        @Test
        public  void BlueStemTestCase1(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"121686802\">\n" +
                        "  <participatingParty name=\"Bluestem\" roleType=\"merchant\" participationCode=\"From:\">bluestem</participatingParty>\n" +
                        "  <sendersIdForReceiver>1276448</sendersIdForReceiver>\n" +
                        "  <orderId>121686802</orderId>\n" +
                        "  <lineCount>1</lineCount>\n" +
                        "  <poNumber>PO000138757503</poNumber>\n" +
                        "  <orderDate>20200213</orderDate>\n" +
                        "  <merchandise>35.00</merchandise>\n" +
                        "  <merchandiseCost>35.00</merchandiseCost>\n" +
                        "  <tax>2.00</tax>\n" +
                        "  <shipTo personPlaceID=\"PP3008906049\" />\n" +
                        "  <billTo personPlaceID=\"PP3008906051\" />\n" +
                        "  <shipFrom vendorShipID=\"VS103852002_121686802\" />\n" +
                        "  <invoiceTo personPlaceID=\"PP3008906050\" />\n" +
                        "  <shippingCode>UNSP</shippingCode>\n" +
                        "  <salesDivision>Fingerhut</salesDivision>\n" +
                        "  <vendorWarehouseId>12356</vendorWarehouseId>\n" +
                        "  <custOrderNumber>0005525101</custOrderNumber>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<discountTerms discPercent=\"2.0\">0.0</discountTerms>\n" +
                        "    \n" +
                        "\t<cancelAfterDate>20200218</cancelAfterDate>\n" +
                        "    \n" +
                        "\t<reqShipDate>20200215</reqShipDate>\n" +
                        "    \n" +
                        "\t<packListData>\n" +
                        "      \n" +
                        "\t\t<packslipTemplate>EN</packslipTemplate>\n" +
                        "      \n" +
                        "\t\n" +
                        "    </packListData>\n" +
                        "    \n" +
                        "\t<warehouseID>12356</warehouseID>\n" +
                        "    \n" +
                        "\t<custOrderNumber>0005525101</custOrderNumber>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\t<vendorMessage>Drop by back door</vendorMessage>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790803</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>2</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>12345678999912</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330101</merchantSKU>\n" +
                        "    <vendorSKU>ABC111</vendorSKU>\n" +
                        "    <unitCost>1.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>50.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData>\n" +
                        "      <prodColor>Blue</prodColor>\n" +
                        "      <prodSize>6</prodSize>\n" +
                        "      <personalizationData>\n" +
                        "        <nameValuePair name=\"Line 1\">Initials = RD</nameValuePair>\n" +
                        "        <nameValuePair name=\"Line 2\">Stone = Onyx</nameValuePair>\n" +
                        "        <nameValuePair name=\"Line 3\">text = Bob and Betty</nameValuePair>\n" +
                        "        <nameValuePair name=\"Line 4\">text = 25 years</nameValuePair>\n" +
                        "      </personalizationData>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3008906051\">\n" +
                        "    <name1>John Doe</name1>\n" +
                        "    <address1>1 Fuller Rd</address1>\n" +
                        "    <city>Albany</city>\n" +
                        "    <state>NY</state>\n" +
                        "    <postalCode>12203</postalCode>\n" +
                        "    <email>johndoe@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3008906050\">\n" +
                        "    <name1>Mary Smith</name1>\n" +
                        "    <address1>1 Stewart St</address1>\n" +
                        "    <city>Ithaca</city>\n" +
                        "    <state>Ny</state>\n" +
                        "    <postalCode>14850</postalCode>\n" +
                        "    <email>marysmith@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3008906049\">\n" +
                        "    <name1>John Doe</name1>\n" +
                        "    <address1>1 Fuller Rd</address1>\n" +
                        "    <city>Albany</city>\n" +
                        "    <state>NY</state>\n" +
                        "    <postalCode>12203</postalCode>\n" +
                        "    <email>johndoe@sample.com</email>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS103852002_121686802\">\n" +
                        "    <name1>FingerHut Returns</name1>\n" +
                        "    <address1>8051 Eastgate Blvd.</address1>\n" +
                        "    <address2>Attn: Fingerhut &amp; Bluestem Partner</address2>\n" +
                        "    <city>Mt. Juliet</city>\n" +
                        "    <state>TN</state>\n" +
                        "    <postalCode>37122</postalCode>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsBlueStemCommerceHubAPI api = new JustBrandsBlueStemCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","bluestem","Just Brand Limited","bluestem",626);
                // api.setGroupName("bedbath");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3004980982", false);
                        PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


                        log.debug(orderId);

                        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                        confirmFileSource.setkIncludeShipFrom(true);
                        OrderStatus os = new OrderStatus(orderId+"");
                        confirmFileSource.setCurrentOrderStatus(os);
                        confirmFileSource.setClientId(api.getClientId());
                        System.out.println(confirmFileSource.getFileData());
                        api.confirmOrderShipment(os);

                        //   CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                        //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                        //api.setInvoiceDiscDaysDue(5);
                        //    api.setInvoiceNetDaysDue(30);
                        //api.setInvoiceDiscPercent(new BigDecimal("10"));

                        //    System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));



                }catch (Exception e){
                        fail();
                }








        }
    @Test
    public  void BedBathBeyondTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121686803\">\n" +
                "  <participatingParty name=\"Bluestem\" roleType=\"merchant\" participationCode=\"From:\">bluestem</participatingParty>\n" +
                "  <sendersIdForReceiver>1276448</sendersIdForReceiver>\n" +
                "  <orderId>121686803</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>PO000138757506</poNumber>\n" +
                "  <orderDate>20200213</orderDate>\n" +
                "  <merchandise>35.00</merchandise>\n" +
                "  <merchandiseCost>35.00</merchandiseCost>\n" +
                "  <shipping>1.00</shipping>\n" +
                "  <total>38.28</total>\n" +
                "  <shipTo personPlaceID=\"PP3008906052\" />\n" +
                "  <billTo personPlaceID=\"PP3008906054\" />\n" +
                "  <shipFrom vendorShipID=\"VS103852003_121686803\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008906053\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <salesDivision>Gettington</salesDivision>\n" +
                "  <custOrderNumber>0005525102</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20200218</cancelAfterDate>\n" +
                "    \n" +
                "\t<reqShipDate>20200215</reqShipDate>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipTemplate>EN</packslipTemplate>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0005525102</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121790804</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>12345678999913</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>000330201</merchantSKU>\n" +
                "    <vendorSKU>ABC333</vendorSKU>\n" +
                "    <unitCost>50.0000</unitCost>\n" +
                "    <lineMerchandiseCost>35.00</lineMerchandiseCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008906054\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008906053\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008906052\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103852003_121686803\">\n" +
                "    <name1>Gettington Returns</name1>\n" +
                "    <address1>8051 Eastgate Blvd.</address1>\n" +
                "    <address2>Attn: Gettington &amp; Bluestem Partner</address2>\n" +
                "    <city>Mt. Juliet</city>\n" +
                "    <state>TN</state>\n" +
                "    <postalCode>37122</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

            JustBrandsBlueStemCommerceHubAPI api = new JustBrandsBlueStemCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","bluestem","Just Brand Limited","bluestem",626);
            // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3004980982", false);
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
    public  void BlueStemTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121686804\">\n" +
                "  <participatingParty name=\"Bluestem\" roleType=\"merchant\" participationCode=\"From:\">bluestem</participatingParty>\n" +
                "  <sendersIdForReceiver>1276448</sendersIdForReceiver>\n" +
                "  <orderId>121686804</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>PO000138757509</poNumber>\n" +
                "  <orderDate>20200213</orderDate>\n" +
                "  <merchandise>30.00</merchandise>\n" +
                "  <merchandiseCost>30.00</merchandiseCost>\n" +
                "  <tax>2.00</tax>\n" +
                "  <shipping>1.00</shipping>\n" +
                "  <total>31.00</total>\n" +
                "  <shipTo personPlaceID=\"PP3008906055\" />\n" +
                "  <billTo personPlaceID=\"PP3008906057\" />\n" +
                "  <shipFrom vendorShipID=\"VS103852002_121686804\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008906056\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <salesDivision>Fingerhut</salesDivision>\n" +
                "  <custOrderNumber>0005525103</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<discountTerms discPercent=\"3.0\">0.0</discountTerms>\n" +
                "    \n" +
                "\t<cancelAfterDate>20200218</cancelAfterDate>\n" +
                "    \n" +
                "\t<reqShipDate>20200215</reqShipDate>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipTemplate>EN</packslipTemplate>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0005525103</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<vendorMessage>Drop by back door</vendorMessage>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121790805</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>12345678999914</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>000330301</merchantSKU>\n" +
                "    <unitCost>50.0000</unitCost>\n" +
                "    <lineMerchandiseCost>30.00</lineMerchandiseCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <poLineData>\n" +
                "      <prodColor>Red</prodColor>\n" +
                "      <prodSize>7</prodSize>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121790806</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>12345678999915</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>000330302</merchantSKU>\n" +
                "    <vendorSKU>ABC334</vendorSKU>\n" +
                "    <unitCost>50.0000</unitCost>\n" +
                "    <lineMerchandiseCost>10.00</lineMerchandiseCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <poLineData>\n" +
                "      <prodColor>Black</prodColor>\n" +
                "      <prodSize>12</prodSize>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008906056\">\n" +
                "    <name1>Bill Johnson</name1>\n" +
                "    <address1>Apartment 3B</address1>\n" +
                "    <address2>3 Falke Plaza</address2>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>20166</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008906055\">\n" +
                "    <name1>Tom Smith</name1>\n" +
                "    <address1>3 Westerre Parkway</address1>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>23233</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008906057\">\n" +
                "    <name1>Tom Smith</name1>\n" +
                "    <address1>3 Westerre Parkway</address1>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>23233</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103852002_121686804\">\n" +
                "    <name1>FingerHut Returns</name1>\n" +
                "    <address1>8051 Eastgate Blvd.</address1>\n" +
                "    <address2>Attn: Fingerhut &amp; Bluestem Partner</address2>\n" +
                "    <city>Mt. Juliet</city>\n" +
                "    <state>TN</state>\n" +
                "    <postalCode>37122</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

            JustBrandsBlueStemCommerceHubAPI api = new JustBrandsBlueStemCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","bluestem","Just Brand Limited","bluestem",626);
            // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3004980982", false);
            PackingManager.packAndShip(orderId,false, 2, 0.00, "1Z0000000000000000", false);


            log.debug(orderId);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);
             //   CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                //api.setInvoiceDiscDaysDue(5);
              //  api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

              //  System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));



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
    public  void BlueStemTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121686805\">\n" +
                "  <participatingParty name=\"Bluestem\" roleType=\"merchant\" participationCode=\"From:\">bluestem</participatingParty>\n" +
                "  <sendersIdForReceiver>1276448</sendersIdForReceiver>\n" +
                "  <orderId>121686805</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>PO000138757513</poNumber>\n" +
                "  <orderDate>20200213</orderDate>\n" +
                "  <merchandise>20.00</merchandise>\n" +
                "  <merchandiseCost>20.00</merchandiseCost>\n" +
                "  <shipping>1.00</shipping>\n" +
                "  <total>21.00</total>\n" +
                "  <shipTo personPlaceID=\"PP3008906058\" />\n" +
                "  <billTo personPlaceID=\"PP3008906060\" />\n" +
                "  <shipFrom vendorShipID=\"VS103852002_121686805\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008906059\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <salesDivision>Fingerhut</salesDivision>\n" +
                "  <vendorWarehouseId>12456</vendorWarehouseId>\n" +
                "  <custOrderNumber>0005525104</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<discountTerms discPercent=\"2.0\">0.0</discountTerms>\n" +
                "    \n" +
                "\t<cancelAfterDate>20200218</cancelAfterDate>\n" +
                "    \n" +
                "\t<reqShipDate>20200215</reqShipDate>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipTemplate>EN</packslipTemplate>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<warehouseID>12456</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>0005525104</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121790807</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>12345678999916</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>000330401</merchantSKU>\n" +
                "    <unitCost>50.0000</unitCost>\n" +
                "    <lineMerchandiseCost>20.00</lineMerchandiseCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121790808</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>12345678999917</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>000330402</merchantSKU>\n" +
                "    <unitCost>50.0000</unitCost>\n" +
                "    <lineMerchandiseCost>75.00</lineMerchandiseCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008906059\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <address1>4 La Jolla Vilage Dr</address1>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <postalCode>92122</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008906060\">\n" +
                "    <name1>Joe Sample</name1>\n" +
                "    <address1>4 Jamboree Road</address1>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <postalCode>92660</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008906058\">\n" +
                "    <name1>Joe Sample</name1>\n" +
                "    <address1>4 Jamboree Road</address1>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <postalCode>92660</postalCode>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103852002_121686805\">\n" +
                "    <name1>FingerHut Returns</name1>\n" +
                "    <address1>8051 Eastgate Blvd.</address1>\n" +
                "    <address2>Attn: Fingerhut &amp; Bluestem Partner</address2>\n" +
                "    <city>Mt. Juliet</city>\n" +
                "    <state>TN</state>\n" +
                "    <postalCode>37122</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

            JustBrandsBlueStemCommerceHubAPI api = new JustBrandsBlueStemCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","bluestem","Just Brand Limited","bluestem",626);
            // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3004980982", false);
             PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


            // log.debug(orderId);
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            OrderStatus os = new OrderStatus(orderId+"");

             api.confirmOrderShipment(os);


        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
            fail();
        }








    }

    @Test
    public  void BlueStemTestCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121686806\">\n" +
                "  <participatingParty name=\"Bluestem\" roleType=\"merchant\" participationCode=\"From:\">bluestem</participatingParty>\n" +
                "  <sendersIdForReceiver>1276448</sendersIdForReceiver>\n" +
                "  <orderId>121686806</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>PO000138757517</poNumber>\n" +
                "  <orderDate>20200213</orderDate>\n" +
                "  <merchandise>75.00</merchandise>\n" +
                "  <merchandiseCost>75.00</merchandiseCost>\n" +
                "  <shipping>1.00</shipping>\n" +
                "  <total>76.00</total>\n" +
                "  <shipTo personPlaceID=\"PP3008906061\" />\n" +
                "  <billTo personPlaceID=\"PP3008906063\" />\n" +
                "  <shipFrom vendorShipID=\"VS103852002_121686806\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3008906062\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <salesDivision>Fingerhut</salesDivision>\n" +
                "  <custOrderNumber>0005525106</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<cancelAfterDate>20200218</cancelAfterDate>\n" +
                "    \n" +
                "\t<reqShipDate>20200215</reqShipDate>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipTemplate>EN</packslipTemplate>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0005525106</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121790809</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>12345678999918</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>000330601</merchantSKU>\n" +
                "    <vendorSKU>ABC337</vendorSKU>\n" +
                "    <unitCost>50.0000</unitCost>\n" +
                "    <lineMerchandiseCost>75.00</lineMerchandiseCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3008906063\">\n" +
                "    <name1>Lisa Smith</name1>\n" +
                "    <address1>6 West Loop South</address1>\n" +
                "    <city>Houston</city>\n" +
                "    <state>TX</state>\n" +
                "    <postalCode>77027</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008906061\">\n" +
                "    <name1>Lisa Smith</name1>\n" +
                "    <address1>6 West Loop South</address1>\n" +
                "    <city>Houston</city>\n" +
                "    <state>TX</state>\n" +
                "    <postalCode>77027</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3008906062\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>6 Quorum Drive</address1>\n" +
                "    <city>Dallas</city>\n" +
                "    <state>TX</state>\n" +
                "    <postalCode>75240</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS103852002_121686806\">\n" +
                "    <name1>FingerHut Returns</name1>\n" +
                "    <address1>8051 Eastgate Blvd.</address1>\n" +
                "    <address2>Attn: Fingerhut &amp; Bluestem Partner</address2>\n" +
                "    <city>Mt. Juliet</city>\n" +
                "    <state>TN</state>\n" +
                "    <postalCode>37122</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

            JustBrandsBlueStemCommerceHubAPI api = new JustBrandsBlueStemCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","bluestem","Just Brand Limited","bluestem",626);
            // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3004980982", false);
          /*  PackingManager.packAndShip(orderId, 2, 0.00, "1Z0000000000000000", false);


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

                System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));*/



        }catch (Exception e){
            fail();
        }








    }

        @Test
        public  void BedBathBeyondTestCase6(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"121686807\">\n" +
                        "  <participatingParty name=\"Bluestem\" roleType=\"merchant\" participationCode=\"From:\">bluestem</participatingParty>\n" +
                        "  <sendersIdForReceiver>1276448</sendersIdForReceiver>\n" +
                        "  <orderId>121686807</orderId>\n" +
                        "  <lineCount>10</lineCount>\n" +
                        "  <poNumber>PO000138757520</poNumber>\n" +
                        "  <orderDate>20200213</orderDate>\n" +
                        "  <merchandise>50.00</merchandise>\n" +
                        "  <merchandiseCost>50.00</merchandiseCost>\n" +
                        "  <shipping>1.00</shipping>\n" +
                        "  <total>51.00</total>\n" +
                        "  <shipTo personPlaceID=\"PP3008906064\" />\n" +
                        "  <billTo personPlaceID=\"PP3008906066\" />\n" +
                        "  <shipFrom vendorShipID=\"VS103852002_121686807\" />\n" +
                        "  <invoiceTo personPlaceID=\"PP3008906065\" />\n" +
                        "  <shippingCode>UNSP</shippingCode>\n" +
                        "  <salesDivision>Fingerhut</salesDivision>\n" +
                        "  <custOrderNumber>0005525107</custOrderNumber>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<cancelAfterDate>20200218</cancelAfterDate>\n" +
                        "    \n" +
                        "\t<reqShipDate>20200215</reqShipDate>\n" +
                        "    \n" +
                        "\t<packListData>\n" +
                        "      \n" +
                        "\t\t<packslipTemplate>EN</packslipTemplate>\n" +
                        "      \n" +
                        "\t\n" +
                        "    </packListData>\n" +
                        "    \n" +
                        "\t<custOrderNumber>0005525107</custOrderNumber>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790810</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>10</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>12345678999919</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330701</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>50.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790811</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>9</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>22345678999916</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330702</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>45.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790812</lineItemId>\n" +
                        "    <orderLineNumber>3</orderLineNumber>\n" +
                        "    <merchantLineNumber>3</merchantLineNumber>\n" +
                        "    <qtyOrdered>8</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>32345678999916</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330703</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>40.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790813</lineItemId>\n" +
                        "    <orderLineNumber>4</orderLineNumber>\n" +
                        "    <merchantLineNumber>4</merchantLineNumber>\n" +
                        "    <qtyOrdered>7</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>42345678999916</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330704</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>35.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790814</lineItemId>\n" +
                        "    <orderLineNumber>5</orderLineNumber>\n" +
                        "    <merchantLineNumber>5</merchantLineNumber>\n" +
                        "    <qtyOrdered>6</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>52345678999916</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330705</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>30.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790815</lineItemId>\n" +
                        "    <orderLineNumber>6</orderLineNumber>\n" +
                        "    <merchantLineNumber>6</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>12345676567876</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330706</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>25.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790816</lineItemId>\n" +
                        "    <orderLineNumber>7</orderLineNumber>\n" +
                        "    <merchantLineNumber>7</merchantLineNumber>\n" +
                        "    <qtyOrdered>4</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>987847362532</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330707</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>20.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790817</lineItemId>\n" +
                        "    <orderLineNumber>8</orderLineNumber>\n" +
                        "    <merchantLineNumber>8</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>32438887569485</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330708</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>15.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790818</lineItemId>\n" +
                        "    <orderLineNumber>9</orderLineNumber>\n" +
                        "    <merchantLineNumber>9</merchantLineNumber>\n" +
                        "    <qtyOrdered>2</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>98927364321322</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330709</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>10.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121790819</lineItemId>\n" +
                        "    <orderLineNumber>10</orderLineNumber>\n" +
                        "    <merchantLineNumber>10</merchantLineNumber>\n" +
                        "    <qtyOrdered>1</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>66473621347564</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>000330710</merchantSKU>\n" +
                        "    <unitCost>50.0000</unitCost>\n" +
                        "    <lineMerchandiseCost>5.00</lineMerchandiseCost>\n" +
                        "    <shippingCode>UNSP</shippingCode>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3008906066\">\n" +
                        "    <name1>Mike Jones</name1>\n" +
                        "    <address1>Suite 9 - High Towers</address1>\n" +
                        "    <address2>9 Wakara Way</address2>\n" +
                        "    <city>Salt Lake City</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>84108</postalCode>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3008906064\">\n" +
                        "    <name1>Mike Jones</name1>\n" +
                        "    <address1>Suite 9 - High Towers</address1>\n" +
                        "    <address2>9 Wakara Way</address2>\n" +
                        "    <city>Salt Lake City</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>84108</postalCode>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3008906065\">\n" +
                        "    <name1>Paula Jones</name1>\n" +
                        "    <address1>Apt 9</address1>\n" +
                        "    <address2>9 Main St</address2>\n" +
                        "    <city>Moab</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>84532</postalCode>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS103852002_121686807\">\n" +
                        "    <name1>FingerHut Returns</name1>\n" +
                        "    <address1>8051 Eastgate Blvd.</address1>\n" +
                        "    <address2>Attn: Fingerhut &amp; Bluestem Partner</address2>\n" +
                        "    <city>Mt. Juliet</city>\n" +
                        "    <state>TN</state>\n" +
                        "    <postalCode>37122</postalCode>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsBlueStemCommerceHubAPI api = new JustBrandsBlueStemCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","bluestem","Just Brand Limited","bluestem",626);
                // api.setGroupName("bedbath");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3004983635", false);
                        PackingManager.packAndShip(orderId, 2, 0.00, "1Z0000000000000000", false);


                        log.debug(orderId);

                        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                        OrderStatus os = new OrderStatus(orderId+"");
                        confirmFileSource.setCurrentOrderStatus(os);
                        confirmFileSource.setClientId(api.getClientId());
                        System.out.println(confirmFileSource.getFileData());
                        api.confirmOrderShipment(os);



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

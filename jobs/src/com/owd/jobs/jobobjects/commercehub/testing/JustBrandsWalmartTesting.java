package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsBedBathCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsWalmartCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter;
import com.owd.jobs.jobobjects.commercehub.invoicing.CommerceHubInvoiceBase;
import groovy.util.XmlSlurper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by danny on 6/19/2019.
 */
public class JustBrandsWalmartTesting {
    private final static Logger log = LogManager.getLogger();


        @Test
        public void QVCInventoryTest(){
               // System.setProperty("com.owd.environment","test");
                JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","walmart","Just Brand Limited","walmart",626);
                log.debug(api.getInventoryFile());
                api.pushInventoryFile();



        }

        @Test
        public  void WalmartTestCase1(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"121953821\">\n" +
                        "  <participatingParty name=\"Walmart.com\" roleType=\"merchant\" participationCode=\"From:\">walmart</participatingParty>\n" +
                        "  <sendersIdForReceiver>68904</sendersIdForReceiver>\n" +
                        "  <orderId>121953821</orderId>\n" +
                        "  <lineCount>1</lineCount>\n" +
                        "  <poNumber>139987003</poNumber>\n" +
                        "  <orderDate>20200504</orderDate>\n" +
                        "  <paymentMethod>CC</paymentMethod>\n" +
                        "  <shipTo personPlaceID=\"PP3009255125\" />\n" +
                        "  <billTo personPlaceID=\"PP3009255126\" />\n" +
                        "  <shippingCode>UPSN_FC</shippingCode>\n" +
                        "  <controlNumber>100</controlNumber>\n" +
                        "  <custOrderNumber>CCC255731</custOrderNumber>\n" +
                        "  <custOrderDate>20200504</custOrderDate>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<custOrderNumber>CCC255731</custOrderNumber>\n" +
                        "    \n" +
                        "\t<custOrderDate>20200504</custOrderDate>\n" +
                        "    \n" +
                        "\t<poTypeCode>00</poTypeCode>\n" +
                        "    \n" +

                        "    \n" +
                        "\t<sigRequiredFlag>y</sigRequiredFlag>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>122057851</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990101</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>9990670101</merchantSKU>\n" +
                        "    <vendorSKU>0670101</vendorSKU>\n" +
                        "    <unitCost>500.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_FC</shippingCode>\n" +
                        "    <expectedShipDate>20200505</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3009255126\">\n" +
                        "    <name1>Mary Smith</name1>\n" +
                        "    <address1>XYZ Company</address1>\n" +
                        "    <address2>1 Stewart St</address2>\n" +
                        "    <city>Itahca</city>\n" +
                        "    <state>NY</state>\n" +
                        "    <country>USA</country>\n" +
                        "    <postalCode>14850</postalCode>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3009255125\">\n" +
                        "    <name1>Joe Smith</name1>\n" +
                        "    <address1>WAL-MART #02105</address1>\n" +
                        "    <address2>13307 MIDWAY ROAD</address2>\n" +
                        "    <city>FARMERS BRANCH</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <country>USA</country>\n" +
                        "    <postalCode>75244</postalCode>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "    <partnerPersonPlaceId>2187</partnerPersonPlaceId>\n" +
                        "    <personPlaceData>\n" +
                        "      \n" +
                        "        <ReceiptID>40216714489938967245</ReceiptID>\n" +
                        "      \n" +
                        "      \n" +
                        "    </personPlaceData>\n" +
                        "  </personPlace>\n" +
                        "</hubOrder>";

                JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","walmart","Just Brand Limited","walmart",626);
                // api.setGroupName("bedbath");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3005563481", false);
                        PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", true);


                        log.debug(orderId);

                        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                        confirmFileSource.setkIncludeShipFrom(true);
                        OrderStatus os = new OrderStatus(orderId+"");
                        confirmFileSource.setCurrentOrderStatus(os);
                        confirmFileSource.setClientId(api.getClientId());
                        System.out.println(confirmFileSource.getFileData());
                      //  api.confirmOrderShipment(os);

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
    public  void WalmartTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121953822\">\n" +
                "  <participatingParty name=\"Walmart.com\" roleType=\"merchant\" participationCode=\"From:\">walmart</participatingParty>\n" +
                "  <sendersIdForReceiver>68904</sendersIdForReceiver>\n" +
                "  <orderId>121953822</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>139987005</poNumber>\n" +
                "  <orderDate>20200504</orderDate>\n" +
                "  <paymentMethod>GC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3009255127\" />\n" +
                "  <billTo personPlaceID=\"PP3009255128\" />\n" +
                "  <shippingCode>USPS_ST</shippingCode>\n" +
                "  <custOrderNumber>CCC255732</custOrderNumber>\n" +
                "  <custOrderDate>20200504</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255732</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20200504</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057852</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990201</UPC>\n" +
                "    <description>A Gift for you</description>\n" +
                "    <merchantSKU>9990670201</merchantSKU>\n" +
                "    <vendorSKU>0670201</vendorSKU>\n" +
                "    <unitCost>7.5000</unitCost>\n" +
                "    <shippingCode>USPS_ST</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <requestedArrivalDate>20200507</requestedArrivalDate>\n" +
                "    <poLineData>\n" +
                "      <giftMessage>Happy Birthday Jane![.]Hope it's terrific</giftMessage>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3009255128\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>2 Woodbridge Ave</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>088337</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3009255127\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>2 Woodbridge Ave</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <email>janesmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      \n" +
                "        <ReceiptID>40216714489938967245</ReceiptID>\n" +
                "      \n" +
                "      \n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

            JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","walmart","Just Brand Limited","walmart",626);
            // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3005563481", false);
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
    public  void WalmartTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121953823\">\n" +
                "  <participatingParty name=\"Walmart.com\" roleType=\"merchant\" participationCode=\"From:\">walmart</participatingParty>\n" +
                "  <sendersIdForReceiver>68904</sendersIdForReceiver>\n" +
                "  <orderId>121953823</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>139987007</poNumber>\n" +
                "  <orderDate>20200504</orderDate>\n" +
                "  <paymentMethod>GC</paymentMethod>\n" +
                "  <total>10.99</total>\n" +
                "  <shipTo personPlaceID=\"PP3009255129\" />\n" +
                "  <billTo personPlaceID=\"PP3009255130\" />\n" +
                "  <shippingCode>UR_R5</shippingCode>\n" +
                "  <custOrderNumber>CCC255733</custOrderNumber>\n" +
                "  <custOrderDate>20200504</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255733</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20200504</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057853</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990301</UPC>\n" +
                "    <description>Sample item description for line 3</description>\n" +
                "    <merchantSKU>9990670301</merchantSKU>\n" +
                "    <vendorSKU>0670301</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <lineTax>4.79</lineTax>\n" +
                "    <shippingCode>UR_R5</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <requestedArrivalDate>20200506</requestedArrivalDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057854</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990302</UPC>\n" +
                "    <description>Sample item description for line 5</description>\n" +
                "    <merchantSKU>9990670302</merchantSKU>\n" +
                "    <vendorSKU>0670302</vendorSKU>\n" +
                "    <unitPrice>100.0000</unitPrice>\n" +
                "    <unitCost>80.0000</unitCost>\n" +
                "    <lineTax>8.00</lineTax>\n" +
                "    <shippingCode>UR_R5</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3009255129\">\n" +
                "    <name1>Bill Johnson</name1>\n" +
                "    <address1>WAL-MART #02105</address1>\n" +
                "    <address2>13307 MIDWAY ROAD</address2>\n" +
                "    <city>FARMERS BRANCH</city>\n" +
                "    <state>TX</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>75244</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <partnerPersonPlaceId>2187</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      \n" +
                "        <ReceiptID>40216714489938967245</ReceiptID>\n" +
                "      \n" +
                "      \n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3009255130\">\n" +
                "    <name1>Bill Johnson</name1>\n" +
                "    <address1>Apartment 3B</address1>\n" +
                "    <address2>3 Falke Plaza</address2>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>20166</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

            JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","walmart","Just Brand Limited","walmart",626);
            // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3005563481", false);
            PackingManager.packAndShip(orderId,true, 2, 0.00, "1Z0000000000000000", true);


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
    public  void WalmartTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121953824\">\n" +
                "  <participatingParty name=\"Walmart.com\" roleType=\"merchant\" participationCode=\"From:\">walmart</participatingParty>\n" +
                "  <sendersIdForReceiver>68904</sendersIdForReceiver>\n" +
                "  <orderId>121953824</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>139987009</poNumber>\n" +
                "  <orderDate>20200504</orderDate>\n" +
                "  <paymentMethod>CC</paymentMethod>\n" +
                "  <total>25.00</total>\n" +
                "  <shipTo personPlaceID=\"PP3009255131\" />\n" +
                "  <billTo personPlaceID=\"PP3009255132\" />\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <controlNumber>100</controlNumber>\n" +
                "  <custOrderNumber>CCC255734</custOrderNumber>\n" +
                "  <custOrderDate>20200504</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255734</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20200504</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<sigRequiredFlag>y</sigRequiredFlag>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057855</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990401</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>9990670401</merchantSKU>\n" +
                "    <vendorSKU>0670401</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <lineShipping>1.75</lineShipping>\n" +
                "    <lineTax>4.79</lineTax>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057856</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990402</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>9990670402</merchantSKU>\n" +
                "    <vendorSKU>0670402</vendorSKU>\n" +
                "    <unitPrice>9.9900</unitPrice>\n" +
                "    <unitCost>7.9900</unitCost>\n" +
                "    <lineShipping>2.25</lineShipping>\n" +
                "    <lineTax>0.79</lineTax>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3009255132\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <address1>4 La Holla Village Dr</address1>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>92122</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3009255131\">\n" +
                "    <name1>Joe Sample</name1>\n" +
                "    <address1>Care of: Jane Sample</address1>\n" +
                "    <address2>4 Jamboree Rd</address2>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>92660</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      \n" +
                "        <ReceiptID>40216714489938967245</ReceiptID>\n" +
                "      \n" +
                "      \n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

            JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","walmart","Just Brand Limited","walmart",626);
            // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3005563481", false);
           //  PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


            // log.debug(orderId);
          //  CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
         //   OrderStatus os = new OrderStatus(orderId+"");

            // api.confirmOrderShipment(os);


        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
            fail();
        }








    }

    @Test
    public  void WalmartCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121953825\">\n" +
                "  <participatingParty name=\"Walmart.com\" roleType=\"merchant\" participationCode=\"From:\">walmart</participatingParty>\n" +
                "  <sendersIdForReceiver>68904</sendersIdForReceiver>\n" +
                "  <orderId>121953825</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>139987011</poNumber>\n" +
                "  <orderDate>20200504</orderDate>\n" +
                "  <paymentMethod>CC</paymentMethod>\n" +
                "  <total>25.00</total>\n" +
                "  <shipTo personPlaceID=\"PP3009255133\" />\n" +
                "  <billTo personPlaceID=\"PP3009255134\" />\n" +
                "  <shippingCode>FEDX_NM_R5</shippingCode>\n" +
                "  <custOrderNumber>CCC255735</custOrderNumber>\n" +
                "  <custOrderDate>20200504</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255735</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20200504</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<sigRequiredFlag>y</sigRequiredFlag>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057857</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990501</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>9990670501</merchantSKU>\n" +
                "    <vendorSKU>0670501</vendorSKU>\n" +
                "    <unitPrice>148.0000</unitPrice>\n" +
                "    <unitCost>118.4000</unitCost>\n" +
                "    <lineTax>11.84</lineTax>\n" +
                "    <shippingCode>FEDX_NM_R5</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057858</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990502</UPC>\n" +
                "    <description>Sample item description for line 4</description>\n" +
                "    <merchantSKU>9990670502</merchantSKU>\n" +
                "    <vendorSKU>0670502</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <lineTax>4.79</lineTax>\n" +
                "    <shippingCode>FEDX_NM_R5</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3009255133\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>WAL-MART #02105</address1>\n" +
                "    <address2>13307 MIDWAY ROAD</address2>\n" +
                "    <city>FARMERS BRANCH</city>\n" +
                "    <state>TX</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>75244</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <partnerPersonPlaceId>2187</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      \n" +
                "        <ReceiptID>40216714489938967245</ReceiptID>\n" +
                "      \n" +
                "      \n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3009255134\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>5 Busch Blvd</address2>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

            JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","walmart","Just Brand Limited","walmart",626);
            // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3005563481", false);
            List<PackingManager.packOutInfo> pois = new ArrayList<>();
            pois.add(new PackingManager.packOutInfo(2,0.00,"test8",3, "000000000000000"));
            pois.add(new PackingManager.packOutInfo(2,0.00,"test8",2, "000000000000000"));

            PackingManager.packAndShipMap(orderId, pois, true);


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
             //   api.setInvoiceNetDaysDue(30);
                //api.setInvoiceDiscPercent(new BigDecimal("10"));

             //   System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));



        }catch (Exception e){
            fail();
        }








    }

    @Test
    public  void WalmartCase6(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121953826\">\n" +
                "  <participatingParty name=\"Walmart.com\" roleType=\"merchant\" participationCode=\"From:\">walmart</participatingParty>\n" +
                "  <sendersIdForReceiver>68904</sendersIdForReceiver>\n" +
                "  <orderId>121953826</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>139987013</poNumber>\n" +
                "  <orderDate>20200504</orderDate>\n" +
                "  <paymentMethod>GC</paymentMethod>\n" +
                "  <total>25.00</total>\n" +
                "  <shipTo personPlaceID=\"PP3009255135\" />\n" +
                "  <billTo personPlaceID=\"PP3009255136\" />\n" +
                "  <shippingCode>FXSP</shippingCode>\n" +
                "  <custOrderNumber>CCC255736</custOrderNumber>\n" +
                "  <custOrderDate>20200504</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255736</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20200504</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057859</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990601</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>9990670601</merchantSKU>\n" +
                "    <vendorSKU>0670601</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <lineShipping>2.75</lineShipping>\n" +
                "    <lineTax>4.79</lineTax>\n" +
                "    <shippingCode>FXSP</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057860</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990602</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>9990670602</merchantSKU>\n" +
                "    <vendorSKU>0670602</vendorSKU>\n" +
                "    <unitPrice>29.9900</unitPrice>\n" +
                "    <unitCost>23.9900</unitCost>\n" +
                "    <lineShipping>7.89</lineShipping>\n" +
                "    <lineTax>2.39</lineTax>\n" +
                "    <shippingCode>FXSP</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3009255135\">\n" +
                "    <name1>Lisa Smith</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>6 West Loop South</address2>\n" +
                "    <city>Houston</city>\n" +
                "    <state>TX</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>77027</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <personPlaceData>\n" +
                "      \n" +
                "        <ReceiptID>40216714489938967245</ReceiptID>\n" +
                "      \n" +
                "      \n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3009255136\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>6 Quorum Drive</address2>\n" +
                "    <city>Dallas</city>\n" +
                "    <state>TX</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>75240</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","walmart","Just Brand Limited","walmart",626);
        // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3005563481", false);
            List<PackingManager.packOutInfo> pois = new ArrayList<>();
            pois.add(new PackingManager.packOutInfo(2,0.00,"test10",5, "000000000000000"));
            pois.add(new PackingManager.packOutInfo(2,0.00,"test11",3, "000000000000000"));

            PackingManager.packAndShipMap(orderId, pois, true);


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
            //   api.setInvoiceNetDaysDue(30);
            //api.setInvoiceDiscPercent(new BigDecimal("10"));

            //   System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));



        }catch (Exception e){
            fail();
        }



        }




    @Test
    public  void WalmartTestCase7(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"121953827\">\n" +
                "  <participatingParty name=\"Walmart.com\" roleType=\"merchant\" participationCode=\"From:\">walmart</participatingParty>\n" +
                "  <sendersIdForReceiver>68904</sendersIdForReceiver>\n" +
                "  <orderId>121953827</orderId>\n" +
                "  <lineCount>10</lineCount>\n" +
                "  <poNumber>139987015</poNumber>\n" +
                "  <orderDate>20200504</orderDate>\n" +
                "  <paymentMethod>CC</paymentMethod>\n" +
                "  <total>35.00</total>\n" +
                "  <shipTo personPlaceID=\"PP3009255137\" />\n" +
                "  <billTo personPlaceID=\"PP3009255138\" />\n" +
                "  <shippingCode>FEDX_CG</shippingCode>\n" +
                "  <custOrderNumber>CCC255737</custOrderNumber>\n" +
                "  <custOrderDate>20200504</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>CCC255737</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20200504</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057861</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>10</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990901</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>9990670901</merchantSKU>\n" +
                "    <vendorSKU>0670901</vendorSKU>\n" +
                "    <unitPrice>3.9900</unitPrice>\n" +
                "    <unitCost>3.1900</unitCost>\n" +
                "    <lineTax>0.31</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <requestedArrivalDate>20200506</requestedArrivalDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057862</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>9</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990902</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>9990670902</merchantSKU>\n" +
                "    <vendorSKU>0670902</vendorSKU>\n" +
                "    <unitPrice>4.9900</unitPrice>\n" +
                "    <unitCost>3.9900</unitCost>\n" +
                "    <lineTax>0.39</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057863</lineItemId>\n" +
                "    <orderLineNumber>3</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>8</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990903</UPC>\n" +
                "    <description>Sample item description for line 3</description>\n" +
                "    <merchantSKU>9990670903</merchantSKU>\n" +
                "    <vendorSKU>0670903</vendorSKU>\n" +
                "    <unitPrice>5.9900</unitPrice>\n" +
                "    <unitCost>4.7900</unitCost>\n" +
                "    <lineTax>0.47</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057864</lineItemId>\n" +
                "    <orderLineNumber>4</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>7</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990904</UPC>\n" +
                "    <description>Sample item description for line 4</description>\n" +
                "    <merchantSKU>9990670904</merchantSKU>\n" +
                "    <vendorSKU>0670904</vendorSKU>\n" +
                "    <unitPrice>6.9900</unitPrice>\n" +
                "    <unitCost>5.5900</unitCost>\n" +
                "    <lineTax>0.55</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057865</lineItemId>\n" +
                "    <orderLineNumber>5</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>6</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990905</UPC>\n" +
                "    <description>Sample item description for line 5</description>\n" +
                "    <merchantSKU>9990670905</merchantSKU>\n" +
                "    <vendorSKU>0670905</vendorSKU>\n" +
                "    <unitPrice>7.9900</unitPrice>\n" +
                "    <unitCost>6.3900</unitCost>\n" +
                "    <lineTax>0.63</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057866</lineItemId>\n" +
                "    <orderLineNumber>6</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990906</UPC>\n" +
                "    <description>Sample item description for line 6</description>\n" +
                "    <merchantSKU>9990670906</merchantSKU>\n" +
                "    <vendorSKU>0670906</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>47.9900</unitCost>\n" +
                "    <lineTax>4.79</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057867</lineItemId>\n" +
                "    <orderLineNumber>7</orderLineNumber>\n" +
                "    <merchantLineNumber>7</merchantLineNumber>\n" +
                "    <qtyOrdered>4</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990907</UPC>\n" +
                "    <description>Sample item description for line 7</description>\n" +
                "    <merchantSKU>9990670907</merchantSKU>\n" +
                "    <vendorSKU>0670907</vendorSKU>\n" +
                "    <unitPrice>9.9900</unitPrice>\n" +
                "    <unitCost>7.9900</unitCost>\n" +
                "    <lineTax>0.79</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057868</lineItemId>\n" +
                "    <orderLineNumber>8</orderLineNumber>\n" +
                "    <merchantLineNumber>8</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990908</UPC>\n" +
                "    <description>Sample item description for line 8</description>\n" +
                "    <merchantSKU>9990670908</merchantSKU>\n" +
                "    <vendorSKU>0670908</vendorSKU>\n" +
                "    <unitPrice>10.9900</unitPrice>\n" +
                "    <unitCost>8.7900</unitCost>\n" +
                "    <lineTax>0.87</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057869</lineItemId>\n" +
                "    <orderLineNumber>9</orderLineNumber>\n" +
                "    <merchantLineNumber>9</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990909</UPC>\n" +
                "    <description>Sample item description for line 9</description>\n" +
                "    <merchantSKU>9990670909</merchantSKU>\n" +
                "    <vendorSKU>0670909</vendorSKU>\n" +
                "    <unitPrice>11.9900</unitPrice>\n" +
                "    <unitCost>9.5900</unitCost>\n" +
                "    <lineTax>0.95</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>122057870</lineItemId>\n" +
                "    <orderLineNumber>10</orderLineNumber>\n" +
                "    <merchantLineNumber>10</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990910</UPC>\n" +
                "    <description>Sample item description for line 10</description>\n" +
                "    <merchantSKU>9990670910</merchantSKU>\n" +
                "    <vendorSKU>0670910</vendorSKU>\n" +
                "    <unitPrice>12.9900</unitPrice>\n" +
                "    <unitCost>10.3900</unitCost>\n" +
                "    <lineTax>1.03</lineTax>\n" +
                "    <shippingCode>FEDX_CG</shippingCode>\n" +
                "    <expectedShipDate>20200505</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3009255137\">\n" +
                "    <name1>Paula Jones</name1>\n" +
                "    <address1>WAL-MART #02105</address1>\n" +
                "    <address2>13307 MIDWAY ROAD</address2>\n" +
                "    <city>FARMERS BRANCH</city>\n" +
                "    <state>TX</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>75244</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <partnerPersonPlaceId>2187</partnerPersonPlaceId>\n" +
                "    <personPlaceData>\n" +
                "      \n" +
                "        <ReceiptID>40216714489938967245</ReceiptID>\n" +
                "      \n" +
                "      \n" +
                "    </personPlaceData>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3009255138\">\n" +
                "    <name1>Paula Jones</name1>\n" +
                "    <address1>Apt 9</address1>\n" +
                "    <address2>9 Main St</address2>\n" +
                "    <city>Moab</city>\n" +
                "    <state>UT</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>84532</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Already5G4in!Replace3","walmart","Just Brand Limited","walmart",626);
        // api.setGroupName("bedbath");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3005563481", false);
            PackingManager.packAndShip(orderId,false, 2, 0.00, "000000000000000", true);


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

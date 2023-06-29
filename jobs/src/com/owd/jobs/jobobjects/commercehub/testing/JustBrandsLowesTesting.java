package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsHomeDepotCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsLowesCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsQVCCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase;
import com.owd.jobs.jobobjects.commercehub.LowesCommerceHubXmlFileFormatter;
import com.owd.jobs.jobobjects.commercehub.invoicing.CommerceHubInvoiceBase;
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsHomeDepotEDI;
import groovy.util.XmlSlurper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.fail;


public class JustBrandsLowesTesting {
    //private final static Logger log = LogManager.getLogger();

    private final static String chSftpHost = "justbrand.sftp-test.commercehub.com";
    private final static String chUserName ="justbrand";
    private final static String chPassword = "Fully4Help9Admit1$";
    private final static String chRemoteFolder = "lowes";
    private final static String vendorName ="Just Brand Limited";
    private final static String merchantName = "lowes";
    private final static int clientAccountId = 626;


    @Test
    public void InventoryTest(){
        try {
            System.setProperty("com.owd.environment", "test");
            JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
            api.configure(chSftpHost, chUserName, chPassword, chRemoteFolder, vendorName, merchantName, clientAccountId);
            String inventoryFile = api.getInventoryFile();
            System.out.println(inventoryFile);
            api.pushInventoryFile();
        }
        catch(Exception x)
        {
            System.out.println(x.getMessage());
        }
    }


    @Test
    public  void LowesTestCase1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000049017\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000049017</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>43778003</poNumber>\n" +
                "  <orderDate>20210316</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010611035\" />\n" +
                "  <billTo personPlaceID=\"PP3010611037\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010611036\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <salesDivision>ABCD</salesDivision>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>143778004</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>Please drop package off at the side door next to the garage.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>143778004</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2C</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<buyer>5551112222</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<freightPaymentTermsCode>CC</freightPaymentTermsCode>\n" +
                "    \n" +
                "\t<salesAgent>Smith, John</salesAgent>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069042</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>PK</unitOfMeasure>\n" +
                "    <UPC>123456789022</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>111111111</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402A</vendorSKU>\n" +
                "    <unitCost>8.0000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010611035\">\n" +
                "    <name1>John Doe</name1>\n" +
                "    <addressRateClass>1</addressRateClass>\n" +
                "    <address1>1 Fuller Rd</address1>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611037\">\n" +
                "    <name1>John Doe</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611036\">\n" +
                "    <partnerPersonPlaceId>00907</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            //int orderId = api.importOrderNode(data, "3000049017", false);
            int orderId = 22094865;
            //PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", true);




               /* System.out.println("Sending acknowledgement");
                OrderStatus aos = new OrderStatus(orderId+"");
                String ack = api.acknowledgeOrder(aos,"3000049017");

                System.out.println(ack);*/

                //System.out.println(api.acknowledgeOrder(aos,"3000049017"));



            LowesCommerceHubXmlFileFormatter confirmFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());

            String fileData = api.confirmOrderShipment(os);

            System.out.println(fileData);


        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public  void LowesTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000049018\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000049018</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>43778007</poNumber>\n" +
                "  <orderDate>20210316</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010611038\" />\n" +
                "  <billTo personPlaceID=\"PP3010611040\" />\n" +
                "  <customer personPlaceID=\"PP3010611041\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010611039\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <salesDivision>ABCD</salesDivision>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>143778008</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>143778008</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<buyer>5551112222</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<freightPaymentTermsCode>CC</freightPaymentTermsCode>\n" +
                "    \n" +
                "\t<salesAgent>Smith, John</salesAgent>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069043</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>222222222</merchantSKU>\n" +
                "    <vendorSKU>V1122</vendorSKU>\n" +
                "    <unitCost>12.2500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010611041\">\n" +
                "    <name1>Doe, Mary</name1>\n" +
                "    <email>mdoe@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <nightPhone>555-555-5556</nightPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611040\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611039\">\n" +
                "    <partnerPersonPlaceId>00807</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611038\">\n" +
                "    <name1>Lowe's</name1>\n" +
                "    <addressRateClass>2</addressRateClass>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

//            int orderId = api.importOrderNode(data, "3000049018", false);
 //           PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", true);

            int orderId =22094881;

                /*System.out.println("Sending acknowledgement");
                OrderStatus aos = new OrderStatus(orderId+"");
                String ack = api.acknowledgeOrder(aos,"3000049017");

                System.out.println(ack);*/

            //System.out.println(api.acknowledgeOrder(aos,"3000049017"));



            LowesCommerceHubXmlFileFormatter confirmFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());

            String fileData = api.confirmOrderShipment(os);

            System.out.println(fileData);

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public  void LowesTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000049019\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000049019</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>43778011</poNumber>\n" +
                "  <orderDate>20210316</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010611042\" />\n" +
                "  <billTo personPlaceID=\"PP3010611044\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010611043\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <controlNumber>100</controlNumber>\n" +
                "  <salesDivision>ABCD</salesDivision>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>143778012</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>Please check-in with building security upon arrival. Delivery will be coorindated with them. Call 555-5555 if any issues arise.</packslipMessage>\n" +
                "      \n" +
                "\t\t<packslipTemplate>GS1-128</packslipTemplate>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>143778012</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<busRuleCode>LFK</busRuleCode>\n" +
                "    \n" +
                "\t<buyer>8005559999</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<freightPaymentTermsCode>PP</freightPaymentTermsCode>\n" +
                "    \n" +
                "\t<salesAgent>NAME NOT SUPPLIED</salesAgent>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069044</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>CA</unitOfMeasure>\n" +
                "    <UPC>2345676789076</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>333333333</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402D</vendorSKU>\n" +
                "    <unitCost>20.1000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069045</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>123433234567</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>444444444</merchantSKU>\n" +
                "    <vendorSKU>V2234</vendorSKU>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010611044\">\n" +
                "    <name1>George Washington</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611043\">\n" +
                "    <partnerPersonPlaceId>00912</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611042\">\n" +
                "    <name1>Lowe's</name1>\n" +
                "    <addressRateClass>2</addressRateClass>\n" +
                "    <address1>3 Westerre Parkway</address1>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>23233</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <partnerPersonPlaceId>00912</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            //int orderId = api.importOrderNode(data, "3000049019", false);
            //PackingManager.packAndShip(orderId, 5, 0.00, "1Z0000000000000000", true);

            int orderId = 22094882;

            LowesCommerceHubXmlFileFormatter confirmFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            String fileData = api.confirmOrderShipment(os);

            System.out.println(fileData);



        }catch (Exception e){
            fail();
        }
    }

    @Test
    public  void LowesTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000049020\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000049020</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>43778015</poNumber>\n" +
                "  <orderDate>20210316</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010611045\" />\n" +
                "  <billTo personPlaceID=\"PP3010611047\" />\n" +
                "  <customer personPlaceID=\"PP3010611048\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010611046\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>143778016</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>143778016</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<buyer>000123458</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<freightPaymentTermsCode>CC</freightPaymentTermsCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069046</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>123433234572</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>23157101</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402F</vendorSKU>\n" +
                "    <unitCost>34.7500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069047</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>123433234570</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>666666666</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402G</vendorSKU>\n" +
                "    <unitCost>12.4000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010611048\">\n" +
                "    <name1>Jones, Mike</name1>\n" +
                "    <email>mjones@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <nightPhone>555-555-5556</nightPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611045\">\n" +
                "    <name1>Lowe's</name1>\n" +
                "    <addressRateClass>2</addressRateClass>\n" +
                "    <address1>4 Jamboree Road</address1>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92660</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611047\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611046\">\n" +
                "    <partnerPersonPlaceId>00920</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000049020", false);
            PackingManager.packAndShip(orderId, 5, 0.00, "1Z0000000000000000", true);

            LowesCommerceHubXmlFileFormatter confirmFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            String fileData = api.confirmOrderShipment(os);

            System.out.println(fileData);

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public  void LowesTestCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000049021\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000049021</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>43778019</poNumber>\n" +
                "  <orderDate>20210316</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010611049\" />\n" +
                "  <billTo personPlaceID=\"PP3010611051\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010611050\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <salesDivision>ABCD</salesDivision>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>143778020</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>143778020</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>X2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<buyer>000123457/8005555555</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<freightPaymentTermsCode>CC</freightPaymentTermsCode>\n" +
                "    \n" +
                "\t<salesAgent>Jones, Mike</salesAgent>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069048</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>123433234570</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>888888888</merchantSKU>\n" +
                "    <vendorSKU>V1166</vendorSKU>\n" +
                "    <unitCost>12.2500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010611050\">\n" +
                "    <partnerPersonPlaceId>00965</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611049\">\n" +
                "    <name1>Lowe's Hilton Head</name1>\n" +
                "    <addressRateClass>2</addressRateClass>\n" +
                "    <address1>8 Waterside Drive</address1>\n" +
                "    <city>Hilton Head</city>\n" +
                "    <state>SC</state>\n" +
                "    <postalCode>29928</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <partnerPersonPlaceId>12345</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611051\">\n" +
                "    <name1>Jim Smith</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000049021", false);
            PackingManager.packAndShip(orderId, 5, 0.00, "1Z0000000000000000", true);

            LowesCommerceHubXmlFileFormatter confirmFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            String fileData = api.confirmOrderShipment(os);

            System.out.println(fileData);

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public  void LowesTestCase6(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000049022\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000049022</orderId>\n" +
                "  <lineCount>10</lineCount>\n" +
                "  <poNumber>43778023</poNumber>\n" +
                "  <orderDate>20210316</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010611053\" />\n" +
                "  <billTo personPlaceID=\"PP3010611055\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010611054\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <controlNumber>100</controlNumber>\n" +
                "  <salesDivision>ABCD</salesDivision>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>143778024</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>143778024</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2C</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<buyer>8005559999</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<salesAgent>NAME NOT SUPPLIED</salesAgent>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069049</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>10</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>12345678999919</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>789789789</merchantSKU>\n" +
                "    <vendorSKU>HEWCB401H</vendorSKU>\n" +
                "    <unitCost>8.0000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069050</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>9</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>22345678999916</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>147258369</merchantSKU>\n" +
                "    <vendorSKU>HEWCB422I</vendorSKU>\n" +
                "    <unitCost>12.2500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069051</lineItemId>\n" +
                "    <orderLineNumber>3</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>8</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>32345678999916</UPC>\n" +
                "    <description>Sample item description for line 3</description>\n" +
                "    <merchantSKU>741852963</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402J</vendorSKU>\n" +
                "    <unitCost>20.1000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069052</lineItemId>\n" +
                "    <orderLineNumber>4</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>7</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>42345678999916</UPC>\n" +
                "    <description>Sample item description for line 4</description>\n" +
                "    <merchantSKU>112233445</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402K</vendorSKU>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069053</lineItemId>\n" +
                "    <orderLineNumber>5</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>6</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>52345678999916</UPC>\n" +
                "    <description>Sample item description for line 5</description>\n" +
                "    <merchantSKU>223344556</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402L</vendorSKU>\n" +
                "    <unitCost>34.7500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069054</lineItemId>\n" +
                "    <orderLineNumber>6</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>12345676567876</UPC>\n" +
                "    <description>Sample item description for line 6</description>\n" +
                "    <merchantSKU>334455667</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402M</vendorSKU>\n" +
                "    <unitCost>12.4000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069055</lineItemId>\n" +
                "    <orderLineNumber>7</orderLineNumber>\n" +
                "    <merchantLineNumber>7</merchantLineNumber>\n" +
                "    <qtyOrdered>4</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>987847362532</UPC>\n" +
                "    <description>Sample item description for line 7</description>\n" +
                "    <merchantSKU>445566778</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402N</vendorSKU>\n" +
                "    <unitCost>20.1000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069056</lineItemId>\n" +
                "    <orderLineNumber>8</orderLineNumber>\n" +
                "    <merchantLineNumber>8</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>32438887569485</UPC>\n" +
                "    <description>Sample item description for line 8</description>\n" +
                "    <merchantSKU>556677889</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402O</vendorSKU>\n" +
                "    <unitCost>12.2500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069057</lineItemId>\n" +
                "    <orderLineNumber>9</orderLineNumber>\n" +
                "    <merchantLineNumber>9</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>98927364321322</UPC>\n" +
                "    <description>Sample item description for line 9</description>\n" +
                "    <merchantSKU>667788991</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402P</vendorSKU>\n" +
                "    <unitCost>34.7500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069058</lineItemId>\n" +
                "    <orderLineNumber>10</orderLineNumber>\n" +
                "    <merchantLineNumber>10</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>66473621347564</UPC>\n" +
                "    <description>Sample item description for line 10</description>\n" +
                "    <merchantSKU>778899112</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402Q</vendorSKU>\n" +
                "    <unitCost>12.4000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010611054\">\n" +
                "    <partnerPersonPlaceId>00946</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611053\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <addressRateClass>1</addressRateClass>\n" +
                "    <address1>9 Wakara Way</address1>\n" +
                "    <city>Salt Lake City</city>\n" +
                "    <state>UT</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>84108</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611055\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000049022", false);
            PackingManager.packAndShip(orderId, 5, 0.00, "1Z0000000000000000", true);

            LowesCommerceHubXmlFileFormatter confirmFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            String fileData = api.confirmOrderShipment(os);

            System.out.println(fileData);

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public  void LowesTestCase7(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000049023\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000049023</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>43778027</poNumber>\n" +
                "  <orderDate>20210316</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010611056\" />\n" +
                "  <billTo personPlaceID=\"PP3010611058\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010611057\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>143778028</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>143778028</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<vendorMessage>3.INSTALL SALE</vendorMessage>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2C</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<FileIdentifier>http://test.com/img.jpg</FileIdentifier>\n" +
                "    \n" +
                "\t<busRuleCode>LFK</busRuleCode>\n" +
                "    \n" +
                "\t<buyer>5552226666</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<freightPaymentTermsCode>CC</freightPaymentTermsCode>\n" +
                "    \n" +
                "\t<salesAgent>Doe, James</salesAgent>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000069059</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>PK</unitOfMeasure>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>223344556</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402L</vendorSKU>\n" +
                "    <unitCost>34.7500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210318</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <prodDescription3>PRODUCT CODE: SAMPLE PRODUCT CODEnCOMPOSITE ID: 00000000000000000000000000000000000000000000000000000000000000000000000000000000nQUESTION: DIVISIONnANSWER: TESTnQUESTION: PRODUCTnANSWER: WINDOWSnQUESTION: TYPEnANSWER: BAY</prodDescription3>\n" +
                "      <lineReqDelvDate>20210321</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010611056\">\n" +
                "    <name1>Lisa Smith</name1>\n" +
                "    <addressRateClass>1</addressRateClass>\n" +
                "    <address1>15 West Loop South</address1>\n" +
                "    <city>Houston</city>\n" +
                "    <state>TX</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>77027</postalCode>\n" +
                "    <email>lisa@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611058\">\n" +
                "    <name1>Lisa Smith</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010611057\">\n" +
                "    <partnerPersonPlaceId>00827</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000049023", false);
            PackingManager.packAndShip(orderId, 5, 0.00, "1Z0000000000000000", true);

            LowesCommerceHubXmlFileFormatter confirmFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            String fileData = api.confirmOrderShipment(os);

            System.out.println(fileData);

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public  void LowesTestCaseReject1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000186053\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000186053</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>44187523</poNumber>\n" +
                "  <orderDate>20210426</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010797126\" />\n" +
                "  <billTo personPlaceID=\"PP3010797128\" />\n" +
                "  <customer personPlaceID=\"PP3010797129\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010797127\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <salesDivision>ABCD</salesDivision>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>144187524</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>144187524</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<buyer>5551112222</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<freightPaymentTermsCode>CC</freightPaymentTermsCode>\n" +
                "    \n" +
                "\t<salesAgent>Smith, John</salesAgent>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000206134</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>222222222</merchantSKU>\n" +
                "    <vendorSKU>V1122</vendorSKU>\n" +
                "    <unitCost>12.2500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210428</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210501</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010797127\">\n" +
                "    <partnerPersonPlaceId>00807</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010797126\">\n" +
                "    <name1>Lowe's</name1>\n" +
                "    <addressRateClass>2</addressRateClass>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010797129\">\n" +
                "    <name1>Doe, Mary</name1>\n" +
                "    <email>mdoe@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <nightPhone>555-555-5556</nightPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010797128\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = 22094887; //api.importOrderNode(data, "3000186053", false);
            //PackingManager.packAndShip(orderId, 5, 0.00, "1Z0000000000000000", true);

            //LowesCommerceHubXmlFileFormatter rejectFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //rejectFileSource.setkIncludeShipFrom(true);
            //OrderStatus os = new OrderStatus(orderId+"");
            //rejectFileSource.setCurrentOrderStatus(os);
            //rejectFileSource.setClientId(api.getClientId());
            String fileData = api.rejectOrder(data,"3000186053");

            System.out.println(fileData);

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public  void LowesTestCaseReject2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000185031\">\n" +
                "  <participatingParty name=\"Lowe's\" roleType=\"merchant\" participationCode=\"From:\">lowes</participatingParty>\n" +
                "  <sendersIdForReceiver>110630</sendersIdForReceiver>\n" +
                "  <orderId>3000185031</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>44187533</poNumber>\n" +
                "  <orderDate>20210426</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3010795114\" />\n" +
                "  <billTo personPlaceID=\"PP3010795116\" />\n" +
                "  <customer personPlaceID=\"PP3010795117\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3010795115\" />\n" +
                "  <shippingCode>UNSP</shippingCode>\n" +
                "  <vendorWarehouseId>Warehouse123</vendorWarehouseId>\n" +
                "  <custOrderNumber>144187534</custOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<warehouseID>Warehouse123</warehouseID>\n" +
                "    \n" +
                "\t<custOrderNumber>144187534</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<merchandiseTypeCode>D2S</merchandiseTypeCode>\n" +
                "    \n" +
                "\t<buyer>000123458</buyer>\n" +
                "    \n" +
                "\t<offerCurrency>USD</offerCurrency>\n" +
                "    \n" +
                "\t<freightPaymentTermsCode>CC</freightPaymentTermsCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000205079</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>123433234572</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>23157101</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402F</vendorSKU>\n" +
                "    <unitCost>34.7500</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210428</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210501</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000205080</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>123433234570</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>666666666</merchantSKU>\n" +
                "    <vendorSKU>HEWCB402G</vendorSKU>\n" +
                "    <unitCost>12.4000</unitCost>\n" +
                "    <shippingCode>UNSP</shippingCode>\n" +
                "    <expectedShipDate>20210428</expectedShipDate>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20210501</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010795117\">\n" +
                "    <name1>Jones, Mike</name1>\n" +
                "    <email>mjones@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <nightPhone>555-555-5556</nightPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010795116\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010795114\">\n" +
                "    <name1>Lowe's</name1>\n" +
                "    <addressRateClass>2</addressRateClass>\n" +
                "    <address1>4 Jamboree Road</address1>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92660</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010795115\">\n" +
                "    <partnerPersonPlaceId>00920</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";


        JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = 22094887; //api.importOrderNode(data, "3000186053", false);
            //PackingManager.packAndShip(orderId, 5, 0.00, "1Z0000000000000000", true);

            //LowesCommerceHubXmlFileFormatter rejectFileSource =  new LowesCommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), LowesCommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //rejectFileSource.setkIncludeShipFrom(true);
            //OrderStatus os = new OrderStatus(orderId+"");
            //rejectFileSource.setCurrentOrderStatus(os);
            //rejectFileSource.setClientId(api.getClientId());
            String fileData = api.rejectOrder(data,"3000185031");

            System.out.println(fileData);

        }catch (Exception e){
            fail();
        }
    }
}

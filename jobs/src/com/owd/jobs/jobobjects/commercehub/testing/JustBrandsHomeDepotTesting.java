package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsHomeDepotCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsQVCCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.CommerceHubApiBase;
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter;
import com.owd.jobs.jobobjects.commercehub.invoicing.CommerceHubInvoiceBase;
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsHomeDepotEDI;
import groovy.util.XmlSlurper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.fail;

/**
 * Created by danny on 6/19/2019.
 */
public class JustBrandsHomeDepotTesting {
    private final static Logger log = LogManager.getLogger();


    @Test
    public void QVCInventoryTest(){
       // System.setProperty("com.owd.environment","test");
        JustBrandsHomeDepotCommerceHubAPI api = new  JustBrandsHomeDepotCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
        log.debug(api.getInventoryFile());
      //  api.pushInventoryFile();



    }


    @Test
    public  void HomeDepotTestCase1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120900855\">\n" +
                "  <participatingParty name=\"The Home Depot Inc\" roleType=\"merchant\" participationCode=\"From:\">thehomedepot</participatingParty>\n" +
                "  <sendersIdForReceiver>60006755</sendersIdForReceiver>\n" +
                "  <orderId>120900855</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>35420591</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3007806200\" />\n" +
                "  <billTo personPlaceID=\"PP3007806200\" />\n" +
                "  <customer personPlaceID=\"PP3007806202\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3007806201\" />\n" +
                "  <shippingCode>UNSP_CG</shippingCode>\n" +
                "  <controlNumber>100</controlNumber>\n" +
                "  <salesDivision>8119</salesDivision>\n" +
                "  <custOrderNumber>W135420592</custOrderNumber>\n" +
                "  <buyingContract>60006755</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchDept>27</merchDept>\n" +
                "    \n" +
                "\t<reqShipDate>20190621</reqShipDate>\n" +
                "    \n" +
                "\t<custOrderNumber>W135420592</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\t<giftMessage>Character@ .°¢;&lt;\"'Test</giftMessage>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121009971</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990101</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>123456</merchantSKU>\n" +
                "    <vendorSKU>88880101</vendorSKU>\n" +
                "    <shoppingCartSKU>100590101</shoppingCartSKU>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190621</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007806200\">\n" +
                "    <name1>John Doe</name1>\n" +
                "    <address1>1 Fuller Road</address1>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806201\">\n" +
                "    <name1>Home Depot</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806202\">\n" +
                "    <name1>Mary Smith</name1>\n" +
                "    <address1>123 THD Lane</address1>\n" +
                "    <city>Atlanta</city>\n" +
                "    <state>GA</state>\n" +
                "    <postalCode>30339</postalCode>\n" +
                "    <email>sample@email.com</email>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
       // api.setGroupName("thehomedepot");
       // api.setPackingSlipTemplate("iqcv");
       // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003177183", false);
            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


            log.debug(orderId);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                confirmFileSource.setkIncludeShipFrom(true);
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
    public  void HomeDepotTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120900856\">\n" +
                "  <participatingParty name=\"The Home Depot Inc\" roleType=\"merchant\" participationCode=\"From:\">thehomedepot</participatingParty>\n" +
                "  <sendersIdForReceiver>60006755</sendersIdForReceiver>\n" +
                "  <orderId>120900856</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>35420624</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3007806203\" />\n" +
                "  <billTo personPlaceID=\"PP3007806203\" />\n" +
                "  <customer personPlaceID=\"PP3007806205\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3007806204\" />\n" +
                "  <shippingCode>UPSN</shippingCode>\n" +
                "  <salesDivision>8119</salesDivision>\n" +
                "  <custOrderNumber>W135420625</custOrderNumber>\n" +
                "  <buyingContract>60006755</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchDept>25</merchDept>\n" +
                "    \n" +
                "\t<reqShipDate>20190621</reqShipDate>\n" +
                "    \n" +
                "\t<custOrderNumber>W135420625</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121009972</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990201</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>123456</merchantSKU>\n" +
                "    <vendorSKU>88880201</vendorSKU>\n" +
                "    <shoppingCartSKU>100590201</shoppingCartSKU>\n" +
                "    <unitCost>9.0000</unitCost>\n" +
                "    <shippingCode>UPSN</shippingCode>\n" +
                "    <expectedShipDate>20190621</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007806205\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806203\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806204\">\n" +
                "    <name1>Home Depot</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
        // api.setGroupName("thehomedepot");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003136566", false);
           // PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


           // log.debug(orderId);
/*
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setBatchRef("3003136566");
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
    public  void HomeDepotTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120900857\">\n" +
                "  <participatingParty name=\"The Home Depot Inc\" roleType=\"merchant\" participationCode=\"From:\">thehomedepot</participatingParty>\n" +
                "  <sendersIdForReceiver>60006755</sendersIdForReceiver>\n" +
                "  <orderId>120900857</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>35420654</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3007806206\" />\n" +
                "  <billTo personPlaceID=\"PP3007806206\" />\n" +
                "  <customer personPlaceID=\"PP3007806208\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3007806207\" />\n" +
                "  <shippingCode>UPSN</shippingCode>\n" +
                "  <salesDivision>8119</salesDivision>\n" +
                "  <custOrderNumber>W135420655</custOrderNumber>\n" +
                "  <buyingContract>60006755</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchDept>25</merchDept>\n" +
                "    \n" +
                "\t<reqShipDate>20190621</reqShipDate>\n" +
                "    \n" +
                "\t<custOrderNumber>W135420655</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121009973</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990301</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>123456</merchantSKU>\n" +
                "    <vendorSKU>88880301</vendorSKU>\n" +
                "    <shoppingCartSKU>100590301</shoppingCartSKU>\n" +
                "    <unitCost>8.0000</unitCost>\n" +
                "    <shippingCode>UPSN</shippingCode>\n" +
                "    <expectedShipDate>20190621</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121009974</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990302</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>123456</merchantSKU>\n" +
                "    <vendorSKU>88880302</vendorSKU>\n" +
                "    <shoppingCartSKU>100590302</shoppingCartSKU>\n" +
                "    <unitCost>3.0000</unitCost>\n" +
                "    <shippingCode>UPSN</shippingCode>\n" +
                "    <expectedShipDate>20190621</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007806208\">\n" +
                "    <name1>Bill Johnson</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806207\">\n" +
                "    <name1>Home Depot</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806206\">\n" +
                "    <name1>Tom Smith</name1>\n" +
                "    <address1>C/O THD Ship to Store #9301</address1>\n" +
                "    <address2>3 Westerre Parkway</address2>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>23233</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "    <partnerPersonPlaceId>9301</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";



        JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
        // api.setGroupName("thehomedepot");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003177183", false);
            PackingManager.packAndShip(orderId,true, 2, 0.00, "1Z0000000000000000", false);


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
        public void invoiceTest3(){
                try {
                        System.setProperty("com.owd.environment", "test");

                        JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
                        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5", "thehomedepot", "Just Brand Limited", "thehomedepot", 626);
                        CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                        api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                        api.setInvoiceDiscDaysDue(5);
                        api.setInvoiceNetDaysDue(30);
                        api.setInvoiceDiscPercent(new BigDecimal("10"));

                       System.out.println(api.sendInvoiceToCommerceHub(18023209, invoice));

                }catch (Exception e){
                        e.printStackTrace();
                }

        }

    @Test
    public  void HomeDepotTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120900858\">\n" +
                "  <participatingParty name=\"The Home Depot Inc\" roleType=\"merchant\" participationCode=\"From:\">thehomedepot</participatingParty>\n" +
                "  <sendersIdForReceiver>60006755</sendersIdForReceiver>\n" +
                "  <orderId>120900858</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>35420696</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3007806209\" />\n" +
                "  <billTo personPlaceID=\"PP3007806209\" />\n" +
                "  <customer personPlaceID=\"PP3007806211\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3007806210\" />\n" +
                "  <shippingCode>MCC</shippingCode>\n" +
                "  <controlNumber>100</controlNumber>\n" +
                "  <salesDivision>8119</salesDivision>\n" +
                "  <custOrderNumber>C135420697</custOrderNumber>\n" +
                "  <buyingContract>60006755</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchDept>25</merchDept>\n" +
                "    \n" +
                "\t<reqShipDate>20190621</reqShipDate>\n" +
                "    \n" +
                "\t<custOrderNumber>C135420697</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121009975</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990401</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>123456</merchantSKU>\n" +
                "    <vendorSKU>88880401</vendorSKU>\n" +
                "    <shoppingCartSKU>100590401</shoppingCartSKU>\n" +
                "    <unitCost>10.0000</unitCost>\n" +
                "    <shippingCode>MCC</shippingCode>\n" +
                "    <expectedShipDate>20190621</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121009976</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990402</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>123456</merchantSKU>\n" +
                "    <vendorSKU>88880402</vendorSKU>\n" +
                "    <shoppingCartSKU>100590402</shoppingCartSKU>\n" +
                "    <unitCost>5.0000</unitCost>\n" +
                "    <shippingCode>MCC</shippingCode>\n" +
                "    <expectedShipDate>20190621</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007806210\">\n" +
                "    <name1>Home Depot</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806211\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806209\">\n" +
                "    <name1>Joe Sample</name1>\n" +
                "    <address1>Care of: Jane Sample</address1>\n" +
                "    <address2>4 Jamboree Road</address2>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <postalCode>92660</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
        // api.setGroupName("thehomedepot");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003177183", false);
            // PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


            // log.debug(orderId);
/*
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setBatchRef("3003136566");
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
        public void HomeDepotInvoiceCase5(){
                System.setProperty("com.owd.environment","test");
                JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
                // api.setGroupName("thehomedepot");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                       /* Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003177183", false);
                        PackingManager.packAndShip(orderId, 2, 0.00, "1Z0000000000000000", false);


                        log.debug(orderId);

                        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                        OrderStatus os = new OrderStatus(orderId+"");
                        confirmFileSource.setCurrentOrderStatus(os);
                        confirmFileSource.setClientId(api.getClientId());
                        System.out.println(confirmFileSource.getFileData());
                        api.confirmOrderShipment(os);*/

                        CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
                        //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
                        //api.setInvoiceDiscDaysDue(5);
                        api.setInvoiceNetDaysDue(30);
                        //api.setInvoiceDiscPercent(new BigDecimal("10"));

                        System.out.println(api.sendInvoiceToCommerceHub(18023213, invoice));



                }catch (Exception e){
                        fail();
                }
        }

    @Test
    public  void HomeDepotTestCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120900859\">\n" +
                "  <participatingParty name=\"The Home Depot Inc\" roleType=\"merchant\" participationCode=\"From:\">thehomedepot</participatingParty>\n" +
                "  <sendersIdForReceiver>60006755</sendersIdForReceiver>\n" +
                "  <orderId>120900859</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>35420738</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <shipTo personPlaceID=\"PP3007806212\" />\n" +
                "  <billTo personPlaceID=\"PP3007806212\" />\n" +
                "  <customer personPlaceID=\"PP3007806214\" />\n" +
                "  <invoiceTo personPlaceID=\"PP3007806213\" />\n" +
                "  <shippingCode>UPSN</shippingCode>\n" +
                "  <salesDivision>8119</salesDivision>\n" +
                "  <custOrderNumber>C135420739</custOrderNumber>\n" +
                "  <buyingContract>60006755</buyingContract>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchDept>25</merchDept>\n" +
                "    \n" +
                "\t<reqShipDate>20190621</reqShipDate>\n" +
                "    \n" +
                "\t<custOrderNumber>C135420739</custOrderNumber>\n" +
                "    \n" +
                "\t<poTypeCode>00</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121009977</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990501</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>123456</merchantSKU>\n" +
                "    <vendorSKU>88880501</vendorSKU>\n" +
                "    <shoppingCartSKU>100590501</shoppingCartSKU>\n" +
                "    <unitCost>75.0000</unitCost>\n" +
                "    <shippingCode>UPSN</shippingCode>\n" +
                "    <expectedShipDate>20190621</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121009978</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990502</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>123456</merchantSKU>\n" +
                "    <vendorSKU>88880502</vendorSKU>\n" +
                "    <shoppingCartSKU>100590502</shoppingCartSKU>\n" +
                "    <unitCost>12.5000</unitCost>\n" +
                "    <shippingCode>UPSN</shippingCode>\n" +
                "    <expectedShipDate>20190621</expectedShipDate>\n" +
                "    <poLineData />\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007806213\">\n" +
                "    <name1>Home Depot</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806212\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>5 Busch Blvd</address1>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007806214\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
        // api.setGroupName("thehomedepot");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003177183", false);
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
        public  void HomeDepotTestCase6(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120900860\">\n" +
                        "  <participatingParty name=\"The Home Depot Inc\" roleType=\"merchant\" participationCode=\"From:\">thehomedepot</participatingParty>\n" +
                        "  <sendersIdForReceiver>60006755</sendersIdForReceiver>\n" +
                        "  <orderId>120900860</orderId>\n" +
                        "  <lineCount>2</lineCount>\n" +
                        "  <poNumber>35420780</poNumber>\n" +
                        "  <orderDate>20190621</orderDate>\n" +
                        "  <shipTo personPlaceID=\"PP3007806215\" />\n" +
                        "  <billTo personPlaceID=\"PP3007806215\" />\n" +
                        "  <customer personPlaceID=\"PP3007806217\" />\n" +
                        "  <invoiceTo personPlaceID=\"PP3007806216\" />\n" +
                        "  <shippingCode>UPSN</shippingCode>\n" +
                        "  <salesDivision>8119</salesDivision>\n" +
                        "  <custOrderNumber>W135420781</custOrderNumber>\n" +
                        "  <buyingContract>60006755</buyingContract>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<merchDept>25</merchDept>\n" +
                        "    \n" +
                        "\t<reqShipDate>20190621</reqShipDate>\n" +
                        "    \n" +
                        "\t<custOrderNumber>W135420781</custOrderNumber>\n" +
                        "    \n" +
                        "\t<poTypeCode>00</poTypeCode>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009979</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990601</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880601</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590601</shoppingCartSKU>\n" +
                        "    <unitCost>3.0000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009980</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990602</UPC>\n" +
                        "    <description>Sample item description for line 2</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880602</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590602</shoppingCartSKU>\n" +
                        "    <unitCost>35.0000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007806217\">\n" +
                        "    <name1>Mary Doe</name1>\n" +
                        "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007806216\">\n" +
                        "    <name1>Home Depot</name1>\n" +
                        "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007806215\">\n" +
                        "    <name1>Lisa Smith</name1>\n" +
                        "    <address1>6 West Loop South</address1>\n" +
                        "    <city>Houston</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <postalCode>77027</postalCode>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "</hubOrder>";

                JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
                // api.setGroupName("thehomedepot");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003177183", false);
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
        public  void HomeDepotTestCase6SendDocs() {
                System.setProperty("com.owd.environment", "test");




          /*  try {
                PackingManager.packAndShip(18023253, 2, 0.00, "1Z0000000000000000", false);
            }catch (Exception e){
                e.printStackTrace();

            }*/

                JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);

            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
            //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
            //api.setInvoiceDiscDaysDue(5);
            api.setInvoiceNetDaysDue(30);
            //api.setInvoiceDiscPercent(new BigDecimal("10"));

            System.out.println(api.sendInvoiceToCommerceHub(18023253, invoice));
            System.out.println(api.sendInvoiceToCommerceHub(18023252, invoice));

                /*CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                OrderStatus os = new OrderStatus(18023252+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);

              confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                 os = new OrderStatus(18023253+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);*/







        }


        @Test
        public  void HomeDepotTestCase7(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120900861\">\n" +
                        "  <participatingParty name=\"The Home Depot Inc\" roleType=\"merchant\" participationCode=\"From:\">thehomedepot</participatingParty>\n" +
                        "  <sendersIdForReceiver>60006755</sendersIdForReceiver>\n" +
                        "  <orderId>120900861</orderId>\n" +
                        "  <lineCount>10</lineCount>\n" +
                        "  <poNumber>35420822</poNumber>\n" +
                        "  <orderDate>20190621</orderDate>\n" +
                        "  <shipTo personPlaceID=\"PP3007806218\" />\n" +
                        "  <billTo personPlaceID=\"PP3007806218\" />\n" +
                        "  <customer personPlaceID=\"PP3007806220\" />\n" +
                        "  <invoiceTo personPlaceID=\"PP3007806219\" />\n" +
                        "  <shippingCode>UPSN</shippingCode>\n" +
                        "  <salesDivision>8119</salesDivision>\n" +
                        "  <custOrderNumber>W135420823</custOrderNumber>\n" +
                        "  <buyingContract>60006755</buyingContract>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<merchDept>25</merchDept>\n" +
                        "    \n" +
                        "\t<reqShipDate>20190621</reqShipDate>\n" +
                        "    \n" +
                        "\t<custOrderNumber>W135420823</custOrderNumber>\n" +
                        "    \n" +
                        "\t<poTypeCode>00</poTypeCode>\n" +
                        "    \n" +
                        "\t<giftMessage>Sample Order Level Message</giftMessage>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009981</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>10</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990901</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880901</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590901</shoppingCartSKU>\n" +
                        "    <unitCost>2.0000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009982</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>9</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990902</UPC>\n" +
                        "    <description>Sample item description for line 2</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880902</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590902</shoppingCartSKU>\n" +
                        "    <unitCost>2.5000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009983</lineItemId>\n" +
                        "    <orderLineNumber>3</orderLineNumber>\n" +
                        "    <merchantLineNumber>3</merchantLineNumber>\n" +
                        "    <qtyOrdered>8</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990903</UPC>\n" +
                        "    <description>Sample item description for line 3</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880903</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590903</shoppingCartSKU>\n" +
                        "    <unitCost>3.0000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009984</lineItemId>\n" +
                        "    <orderLineNumber>4</orderLineNumber>\n" +
                        "    <merchantLineNumber>4</merchantLineNumber>\n" +
                        "    <qtyOrdered>7</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990904</UPC>\n" +
                        "    <description>Sample item description for line 4, that is unusually long, and very verbose.</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880904</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590904</shoppingCartSKU>\n" +
                        "    <unitCost>3.5000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009985</lineItemId>\n" +
                        "    <orderLineNumber>5</orderLineNumber>\n" +
                        "    <merchantLineNumber>5</merchantLineNumber>\n" +
                        "    <qtyOrdered>6</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990905</UPC>\n" +
                        "    <description>Sample item description for line 5</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880905</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590905</shoppingCartSKU>\n" +
                        "    <unitCost>4.0000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009986</lineItemId>\n" +
                        "    <orderLineNumber>6</orderLineNumber>\n" +
                        "    <merchantLineNumber>6</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990906</UPC>\n" +
                        "    <description>Sample item description for line 6</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880906</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590906</shoppingCartSKU>\n" +
                        "    <unitCost>4.5000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009987</lineItemId>\n" +
                        "    <orderLineNumber>7</orderLineNumber>\n" +
                        "    <merchantLineNumber>7</merchantLineNumber>\n" +
                        "    <qtyOrdered>4</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990907</UPC>\n" +
                        "    <description>Sample item description for line 7</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880907</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590907</shoppingCartSKU>\n" +
                        "    <unitCost>5.0000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009988</lineItemId>\n" +
                        "    <orderLineNumber>8</orderLineNumber>\n" +
                        "    <merchantLineNumber>8</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990908</UPC>\n" +
                        "    <description>Sample item description for line 8, that is unusually long, and very verbose.</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880908</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590908</shoppingCartSKU>\n" +
                        "    <unitCost>5.5000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009989</lineItemId>\n" +
                        "    <orderLineNumber>9</orderLineNumber>\n" +
                        "    <merchantLineNumber>9</merchantLineNumber>\n" +
                        "    <qtyOrdered>2</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990909</UPC>\n" +
                        "    <description>Sample item description for line 9</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880909</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590909</shoppingCartSKU>\n" +
                        "    <unitCost>6.0000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121009990</lineItemId>\n" +
                        "    <orderLineNumber>10</orderLineNumber>\n" +
                        "    <merchantLineNumber>10</merchantLineNumber>\n" +
                        "    <qtyOrdered>1</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990910</UPC>\n" +
                        "    <description>Sample item description for line 10</description>\n" +
                        "    <merchantSKU>123456</merchantSKU>\n" +
                        "    <vendorSKU>88880910</vendorSKU>\n" +
                        "    <shoppingCartSKU>100590910</shoppingCartSKU>\n" +
                        "    <unitCost>6.5000</unitCost>\n" +
                        "    <shippingCode>UPSN</shippingCode>\n" +
                        "    <expectedShipDate>20190621</expectedShipDate>\n" +
                        "    <poLineData />\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007806218\">\n" +
                        "    <name1>Mike Jones</name1>\n" +
                        "    <address1>ABC Company Name</address1>\n" +
                        "    <address2>9 Wakara Way</address2>\n" +
                        "    <city>Salt Lake City</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <postalCode>84108</postalCode>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007806220\">\n" +
                        "    <name1>Paula Jones</name1>\n" +
                        "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007806219\">\n" +
                        "    <name1>Home Depot</name1>\n" +
                        "    <partnerPersonPlaceId>8119</partnerPersonPlaceId>\n" +
                        "  </personPlace>\n" +
                        "</hubOrder>";

                JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","thehomedepot","Just Brand Limited","thehomedepot",626);
                // api.setGroupName("thehomedepot");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003177183", false);
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

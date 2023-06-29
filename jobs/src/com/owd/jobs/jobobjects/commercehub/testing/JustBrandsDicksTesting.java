package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsDicksCommerceHubAPI;
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
public class JustBrandsDicksTesting {
    private final static Logger log = LogManager.getLogger();

    @Test
    public void QVCInventoryTest(){
        try {
            System.setProperty("com.owd.environment", "test");
            JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
            api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5", "dsg", "Just Brand Limited", "dsg", 626);

            String file = api.getInventoryFile();

            log.debug(file);
            api.pushInventoryFile();
        }
        catch(Exception x)
        {
            System.out.println(x.getMessage());
        }



    }


    @Test
    public  void DicksTestCase1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120899874\">\n" +
                "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                "  <orderId>120899874</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>00135420547</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>Visa</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007808152\" />\n" +
                "  <billTo personPlaceID=\"PP3007808153\" />\n" +
                "  <shippingCode>UNSP_CG</shippingCode>\n" +
                "  <salesDivision>DSG_ECOM</salesDivision>\n" +
                "  <custOrderNumber>VDCORDER00003AK</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <ERPCustOrderNumber>100000015540</ERPCustOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>VDCORDER00003AK</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\t<giftMessage>Happy Birthday</giftMessage>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008923</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <UPC>800000000001</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575553</merchantSKU>\n" +
                "    <vendorSKU>88880101</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <vendorColorDesc>Blue</vendorColorDesc>\n" +
                "      <vendorSizeDesc>L</vendorSizeDesc>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "      <personalizationData>\n" +
                "        <nameValuePair name=\"Attribute\">J. Smith</nameValuePair>\n" +
                "      </personalizationData>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007808153\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>1 Stewart St</address1>\n" +
                "    <city>Ithaca</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>14850</postalCode>\n" +
                "    <email>marysmith@sample.com</email>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007808152\">\n" +
                "    <name1>John</name1>\n" +
                "    <name2>Doe</name2>\n" +
                "    <address1>1 Fuller Rd</address1>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>12222</postalCode>\n" +
                "    <email>johndoe@sample.com</email>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
       // api.setGroupName("dsg");
       // api.setPackingSlipTemplate("iqcv");
       // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003179221", false);
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
    public  void DicksTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120899875\">\n" +
                "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                "  <orderId>120899875</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>00135420549</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>MC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007808154\" />\n" +
                "  <billTo personPlaceID=\"PP3007808155\" />\n" +
                "  <memberNumber>222222222222</memberNumber>\n" +
                "  <shippingCode>UNSP_CG</shippingCode>\n" +
                "  <salesDivision>FS_ECOM</salesDivision>\n" +
                "  <custOrderNumber>VDCORDER00002AK</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <ERPCustOrderNumber>100000015541</ERPCustOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>VDCORDER00002AK</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008924</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <UPC>800000000002</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575554</merchantSKU>\n" +
                "    <vendorSKU>88880201</vendorSKU>\n" +
                "    <unitPrice>17.9900</unitPrice>\n" +
                "    <unitCost>9.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <vendorColorDesc>Red</vendorColorDesc>\n" +
                "      <vendorSizeDesc>XLT</vendorSizeDesc>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007808154\">\n" +
                "    <name1>Jane</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>2 Woodbridge Ave</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <email>janesmith@sample.com</email>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007808155\">\n" +
                "    <name1>Jane</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>2 Woodbridge Ave</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <email>janesmith@sample.com</email>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
        // api.setGroupName("dsg");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003179221", false);
           // PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


           // log.debug(orderId);
/*
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setBatchRef("3003179221");
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
    public  void DicksTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120899876\">\n" +
                "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                "  <orderId>120899876</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00135420551</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>Visa</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007808156\" />\n" +
                "  <billTo personPlaceID=\"PP3007808157\" />\n" +
                "  <memberNumber>333333333333</memberNumber>\n" +
                "  <shippingCode>UNSP_SE</shippingCode>\n" +
                "  <salesDivision>GG_ECOM</salesDivision>\n" +
                "  <custOrderNumber>VDCORDER00004AK</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <ERPCustOrderNumber>100000015542</ERPCustOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>VDCORDER00004AK</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008925</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <UPC>800000000003</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575555</merchantSKU>\n" +
                "    <unitPrice>14.9900</unitPrice>\n" +
                "    <unitCost>8.0000</unitCost>\n" +
                "    <shippingCode>UNSP_SE</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008926</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <UPC>800000000004</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575556</merchantSKU>\n" +
                "    <unitPrice>6.6600</unitPrice>\n" +
                "    <unitCost>3.0000</unitCost>\n" +
                "    <shippingCode>UNSP_SE</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007808157\">\n" +
                "    <name1>Bill</name1>\n" +
                "    <name2>Johnson</name2>\n" +
                "    <address1>3 Falke Plaza</address1>\n" +
                "    <address2>Apartment 3B</address2>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>20166</postalCode>\n" +
                "    <email>billjohnson@sample.com</email>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007808156\">\n" +
                "    <name1>Tom</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>3 Western Parkway</address1>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <postalCode>23233</postalCode>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
        // api.setGroupName("dsg");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003179221", false);
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
    public  void DicksTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120899877\">\n" +
                "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                "  <orderId>120899877</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00135420553</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>MC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007808158\" />\n" +
                "  <billTo personPlaceID=\"PP3007808159\" />\n" +
                "  <memberNumber>444444444444</memberNumber>\n" +
                "  <shippingCode>UNSP_ND</shippingCode>\n" +
                "  <salesDivision>DSG_ECOM</salesDivision>\n" +
                "  <custOrderNumber>VDCORDER00005AK</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <ERPCustOrderNumber>100000015543</ERPCustOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>VDCORDER00005AK</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008927</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <UPC>800000000005</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575557</merchantSKU>\n" +
                "    <vendorSKU>88880301</vendorSKU>\n" +
                "    <unitPrice>19.9900</unitPrice>\n" +
                "    <unitCost>10.0000</unitCost>\n" +
                "    <shippingCode>UNSP_ND</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "      <personalizationData>\n" +
                "        <nameValuePair name=\"Attribute\">J. Smith</nameValuePair>\n" +
                "      </personalizationData>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008928</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <UPC>9999999990402</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575558</merchantSKU>\n" +
                "    <vendorSKU>88880401</vendorSKU>\n" +
                "    <unitPrice>9.9900</unitPrice>\n" +
                "    <unitCost>5.0000</unitCost>\n" +
                "    <shippingCode>UNSP_ND</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "      <personalizationData>\n" +
                "        <nameValuePair name=\"Attribute\">12345</nameValuePair>\n" +
                "      </personalizationData>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007808158\">\n" +
                "    <name1>Mike</name1>\n" +
                "    <name2>Jones</name2>\n" +
                "    <address1>6929 S. Memorial</address1>\n" +
                "    <city>Tulsa</city>\n" +
                "    <state>OK</state>\n" +
                "    <postalCode>12345</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007808159\">\n" +
                "    <name1>Mike</name1>\n" +
                "    <name2>Jones</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>4 La Jolla Village Dr</address2>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <postalCode>92122</postalCode>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
        // api.setGroupName("dsg");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003179221", false);
            // PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


            // log.debug(orderId);
/*
            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_REJECTED);
            //OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setBatchRef("3003179221");
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
    public  void DicksTestCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120899878\">\n" +
                "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                "  <orderId>120899878</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>00135420555</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>MC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007808160\" />\n" +
                "  <billTo personPlaceID=\"PP3007808161\" />\n" +
                "  <shippingCode>UNSP_SE</shippingCode>\n" +
                "  <salesDivision>CL_ECOM</salesDivision>\n" +
                "  <custOrderNumber>VDCORDER00006AK</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <ERPCustOrderNumber>100000015544</ERPCustOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>VDCORDER00006AK</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008929</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <UPC>800000000006</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575559</merchantSKU>\n" +
                "    <unitPrice>148.0000</unitPrice>\n" +
                "    <unitCost>75.0000</unitCost>\n" +
                "    <shippingCode>UNSP_SE</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008930</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <UPC>800000000007</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>802575550</merchantSKU>\n" +
                "    <unitPrice>24.9900</unitPrice>\n" +
                "    <unitCost>12.5000</unitCost>\n" +
                "    <shippingCode>UNSP_SE</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007808160\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Doe</name2>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>5 Busch Blvd</address2>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007808161\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Doe</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>5 Busch Blvd</address2>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
        // api.setGroupName("dsg");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003135529", false);
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
        public  void DicksTestCase6(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120899879\">\n" +
                        "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                        "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                        "  <orderId>120899879</orderId>\n" +
                        "  <lineCount>2</lineCount>\n" +
                        "  <poNumber>00135420557</poNumber>\n" +
                        "  <orderDate>20190621</orderDate>\n" +
                        "  <paymentMethod>Visa</paymentMethod>\n" +
                        "  <shipTo personPlaceID=\"PP3007808162\" />\n" +
                        "  <billTo personPlaceID=\"PP3007808163\" />\n" +
                        "  <shippingCode>UNSP_ND</shippingCode>\n" +
                        "  <salesDivision>DSG_ECOM</salesDivision>\n" +
                        "  <custOrderNumber>VDCORDER00007AK</custOrderNumber>\n" +
                        "  <custOrderDate>20190621</custOrderDate>\n" +
                        "  <ERPCustOrderNumber>100000015545</ERPCustOrderNumber>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<custOrderNumber>VDCORDER00007AK</custOrderNumber>\n" +
                        "    \n" +
                        "\t<custOrderDate>20190621</custOrderDate>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121008931</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <UPC>800000000008</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>902575553</merchantSKU>\n" +
                        "    <unitPrice>6.9900</unitPrice>\n" +
                        "    <unitCost>3.0000</unitCost>\n" +
                        "    <shippingCode>UNSP_ND</shippingCode>\n" +
                        "    <expectedShipDate>20190622</expectedShipDate>\n" +
                        "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121008932</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <UPC>800000000009</UPC>\n" +
                        "    <description>Sample item description for line 2</description>\n" +
                        "    <merchantSKU>802575560</merchantSKU>\n" +
                        "    <unitPrice>79.9900</unitPrice>\n" +
                        "    <unitCost>35.0000</unitCost>\n" +
                        "    <shippingCode>UNSP_ND</shippingCode>\n" +
                        "    <expectedShipDate>20190622</expectedShipDate>\n" +
                        "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007808162\">\n" +
                        "    <name1>Lisa</name1>\n" +
                        "    <name2>Smith</name2>\n" +
                        "    <address1>ABC Company Name</address1>\n" +
                        "    <address2>6 West Loop South</address2>\n" +
                        "    <city>Houston</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <postalCode>77027</postalCode>\n" +
                        "    <dayPhone>5555555555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007808163\">\n" +
                        "    <name1>Mary</name1>\n" +
                        "    <name2>Doe</name2>\n" +
                        "    <address1>XYZ Company</address1>\n" +
                        "    <address2>6 Quorum Drive</address2>\n" +
                        "    <city>Dallas</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <postalCode>75240</postalCode>\n" +
                        "    <dayPhone>5555555555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "</hubOrder>";

                JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
                // api.setGroupName("dsg");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003135529", false);
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
        public  void DicksTestCase6SendDocs() {
                System.setProperty("com.owd.environment", "test");
                try {
                    PackingManager.packAndShip(18023238, 2, 0.00, "1Z0000000000000000", false);
                }catch (Exception e){
                    e.printStackTrace();
                }

            JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);

                CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                OrderStatus os = new OrderStatus(18023237+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);
            CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
            //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
            //api.setInvoiceDiscDaysDue(5);
            api.setInvoiceNetDaysDue(30);
            //api.setInvoiceDiscPercent(new BigDecimal("10"));

            System.out.println(api.sendInvoiceToCommerceHub(18023237, invoice));

              confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                 os = new OrderStatus(18023238+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);
            System.out.println(api.sendInvoiceToCommerceHub(18023238, invoice));

                try {
                       // PackingManager.packAndShip(18022037, 2, 0.00, "1Z0000000000000000", false);
                }catch (Exception e){
                        e.printStackTrace();

                }





        }


        @Test
        public  void DicksTestCase7(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120899880\">\n" +
                        "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                        "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                        "  <orderId>120899880</orderId>\n" +
                        "  <lineCount>1</lineCount>\n" +
                        "  <poNumber>00135420559</poNumber>\n" +
                        "  <orderDate>20190621</orderDate>\n" +
                        "  <paymentMethod>MC</paymentMethod>\n" +
                        "  <shipTo personPlaceID=\"PP3007808164\" />\n" +
                        "  <billTo personPlaceID=\"PP3007808165\" />\n" +
                        "  <shippingCode>UNSP_CG</shippingCode>\n" +
                        "  <salesDivision>DSG_ECOM</salesDivision>\n" +
                        "  <custOrderNumber>VDCORDER00008AK</custOrderNumber>\n" +
                        "  <custOrderDate>20190621</custOrderDate>\n" +
                        "  <ERPCustOrderNumber>100000015546</ERPCustOrderNumber>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<custOrderNumber>VDCORDER00008AK</custOrderNumber>\n" +
                        "    \n" +
                        "\t<custOrderDate>20190621</custOrderDate>\n" +
                        "    \n" +
                        "\t<poTypeCode>issue</poTypeCode>\n" +
                        "    \n" +
                        "\t<giftMessage>Get Well Soon</giftMessage>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>121008933</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <UPC>800000000001</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantSKU>802575561</merchantSKU>\n" +
                        "    <unitPrice>29.9900</unitPrice>\n" +
                        "    <unitCost>15.0000</unitCost>\n" +
                        "    <shippingCode>UNSP_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190622</expectedShipDate>\n" +
                        "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007808164\">\n" +
                        "    <name1>Mary</name1>\n" +
                        "    <name2>Johnson</name2>\n" +
                        "    <address1>ABC Company Name</address1>\n" +
                        "    <address2>7 Ocean Ave</address2>\n" +
                        "    <city>Kennebunkport</city>\n" +
                        "    <state>ME</state>\n" +
                        "    <postalCode>04046</postalCode>\n" +
                        "    <email>maryjohnson@sample.com</email>\n" +
                        "    <dayPhone>5555555555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007808165\">\n" +
                        "    <name1>John</name1>\n" +
                        "    <name2>Doe</name2>\n" +
                        "    <address1>XYZ Company</address1>\n" +
                        "    <address2>7 Eden Street</address2>\n" +
                        "    <city>Bar Harbor</city>\n" +
                        "    <state>ME</state>\n" +
                        "    <postalCode>04609</postalCode>\n" +
                        "    <email>johndoe@sample.com</email>\n" +
                        "    <dayPhone>5555555555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "</hubOrder>";

                JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
                // api.setGroupName("dsg");
                // api.setPackingSlipTemplate("iqcv");
                // api.setUseUPCLookup(true);
                try {
                        Object data = new XmlSlurper().parseText(poData);

                        int orderId = api.importOrderNode(data, "3003179221", false);
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
    public  void DicksTestCase8(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120899881\">\n" +
                "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                "  <orderId>120899881</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>00135420561</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>MC</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007808166\" />\n" +
                "  <billTo personPlaceID=\"PP3007808167\" />\n" +
                "  <shippingCode>UNSP_CG</shippingCode>\n" +
                "  <salesDivision>DSG_ECOM</salesDivision>\n" +
                "  <custOrderNumber>VDCORDER00009AK</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <ERPCustOrderNumber>100000015547</ERPCustOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>VDCORDER00009AK</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008934</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <UPC>800000000011</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575562</merchantSKU>\n" +
                "    <unitPrice>149.0000</unitPrice>\n" +
                "    <unitCost>75.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007808167\">\n" +
                "    <name1>John</name1>\n" +
                "    <name2>Doe</name2>\n" +
                "    <address1>8 Stoneridge Drive</address1>\n" +
                "    <address2>Suite 8 Luxury Apts</address2>\n" +
                "    <city>Columbia</city>\n" +
                "    <state>SC</state>\n" +
                "    <postalCode>29210</postalCode>\n" +
                "    <email>johndoe@sample.com</email>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007808166\">\n" +
                "    <name1>Jim</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>8 Waterside Drive</address1>\n" +
                "    <address2>Apartment 8</address2>\n" +
                "    <city>Hilton Head</city>\n" +
                "    <state>SC</state>\n" +
                "    <postalCode>29928</postalCode>\n" +
                "    <email>jimsmith@sample.com</email>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
        // api.setGroupName("dsg");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003179221", false);
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
    public  void DicksTestCase8SendDocs() {
        System.setProperty("com.owd.environment", "test");
        /*try {
            PackingManager.packAndShip(18023242, 2, 0.00, "1Z0000000000000000", false);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);

        CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
        OrderStatus os = new OrderStatus(18023241+"");
        confirmFileSource.setCurrentOrderStatus(os);
        confirmFileSource.setClientId(api.getClientId());
        System.out.println(confirmFileSource.getFileData());
        api.confirmOrderShipment(os);
        CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
        //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
        //api.setInvoiceDiscDaysDue(5);
        api.setInvoiceNetDaysDue(30);
        //api.setInvoiceDiscPercent(new BigDecimal("10"));

        System.out.println(api.sendInvoiceToCommerceHub(18023241, invoice));

        confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
        os = new OrderStatus(18023242+"");
        confirmFileSource.setCurrentOrderStatus(os);
        confirmFileSource.setClientId(api.getClientId());
        System.out.println(confirmFileSource.getFileData());
        api.confirmOrderShipment(os);
        System.out.println(api.sendInvoiceToCommerceHub(18023242, invoice));

        try {
            // PackingManager.packAndShip(18022037, 2, 0.00, "1Z0000000000000000", false);
        }catch (Exception e){
            e.printStackTrace();

        }





    }

    @Test
    public  void DicksTestCase9(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120899882\">\n" +
                "  <participatingParty name=\"DICK'S Sporting Goods\" roleType=\"merchant\" participationCode=\"From:\">dsg</participatingParty>\n" +
                "  <sendersIdForReceiver>057811</sendersIdForReceiver>\n" +
                "  <orderId>120899882</orderId>\n" +
                "  <lineCount>10</lineCount>\n" +
                "  <poNumber>001354205632</poNumber>\n" +
                "  <orderDate>20190621</orderDate>\n" +
                "  <paymentMethod>Visa</paymentMethod>\n" +
                "  <shipTo personPlaceID=\"PP3007808168\" />\n" +
                "  <billTo personPlaceID=\"PP3007808169\" />\n" +
                "  <shippingCode>UNSP_CG</shippingCode>\n" +
                "  <salesDivision>DSG_ECOM</salesDivision>\n" +
                "  <custOrderNumber>VDCORDER00000AK</custOrderNumber>\n" +
                "  <custOrderDate>20190621</custOrderDate>\n" +
                "  <ERPCustOrderNumber>100000015548</ERPCustOrderNumber>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>VDCORDER00000AK</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190621</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>issue</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008935</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>10</qtyOrdered>\n" +
                "    <UPC>800000000021</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantSKU>802575563</merchantSKU>\n" +
                "    <unitPrice>3.9900</unitPrice>\n" +
                "    <unitCost>2.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008936</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>9</qtyOrdered>\n" +
                "    <UPC>800000000031</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantSKU>802575564</merchantSKU>\n" +
                "    <unitPrice>4.9900</unitPrice>\n" +
                "    <unitCost>2.5000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008937</lineItemId>\n" +
                "    <orderLineNumber>3</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>8</qtyOrdered>\n" +
                "    <UPC>800000000041</UPC>\n" +
                "    <description>Sample item description for line 3</description>\n" +
                "    <merchantSKU>802575565</merchantSKU>\n" +
                "    <unitPrice>5.9900</unitPrice>\n" +
                "    <unitCost>3.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008938</lineItemId>\n" +
                "    <orderLineNumber>4</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>7</qtyOrdered>\n" +
                "    <UPC>800000000051</UPC>\n" +
                "    <description>Sample item description for line 4</description>\n" +
                "    <merchantSKU>802575566</merchantSKU>\n" +
                "    <unitPrice>6.9900</unitPrice>\n" +
                "    <unitCost>3.5000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008939</lineItemId>\n" +
                "    <orderLineNumber>5</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>6</qtyOrdered>\n" +
                "    <UPC>800000000061</UPC>\n" +
                "    <description>Sample item description for line 5</description>\n" +
                "    <merchantSKU>802575567</merchantSKU>\n" +
                "    <unitPrice>7.9900</unitPrice>\n" +
                "    <unitCost>4.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008940</lineItemId>\n" +
                "    <orderLineNumber>6</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <UPC>800000000071</UPC>\n" +
                "    <description>Sample item description for line 6</description>\n" +
                "    <merchantSKU>802575568</merchantSKU>\n" +
                "    <unitPrice>8.9900</unitPrice>\n" +
                "    <unitCost>4.5000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008941</lineItemId>\n" +
                "    <orderLineNumber>7</orderLineNumber>\n" +
                "    <merchantLineNumber>7</merchantLineNumber>\n" +
                "    <qtyOrdered>4</qtyOrdered>\n" +
                "    <UPC>800000000081</UPC>\n" +
                "    <description>Sample item description for line 7</description>\n" +
                "    <merchantSKU>802575569</merchantSKU>\n" +
                "    <unitPrice>9.9900</unitPrice>\n" +
                "    <unitCost>5.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008942</lineItemId>\n" +
                "    <orderLineNumber>8</orderLineNumber>\n" +
                "    <merchantLineNumber>8</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <UPC>800000000091</UPC>\n" +
                "    <description>Sample item description for line 8</description>\n" +
                "    <merchantSKU>802575570</merchantSKU>\n" +
                "    <unitPrice>10.9900</unitPrice>\n" +
                "    <unitCost>5.5000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008943</lineItemId>\n" +
                "    <orderLineNumber>9</orderLineNumber>\n" +
                "    <merchantLineNumber>9</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <UPC>800000000012</UPC>\n" +
                "    <description>Sample item description for line 9</description>\n" +
                "    <merchantSKU>802575571</merchantSKU>\n" +
                "    <unitPrice>11.9900</unitPrice>\n" +
                "    <unitCost>6.0000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>121008944</lineItemId>\n" +
                "    <orderLineNumber>10</orderLineNumber>\n" +
                "    <merchantLineNumber>10</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <UPC>800000000013</UPC>\n" +
                "    <description>Sample item description for line 10</description>\n" +
                "    <merchantSKU>802575572</merchantSKU>\n" +
                "    <unitPrice>12.9900</unitPrice>\n" +
                "    <unitCost>6.5000</unitCost>\n" +
                "    <shippingCode>UNSP_CG</shippingCode>\n" +
                "    <expectedShipDate>20190622</expectedShipDate>\n" +
                "    <unitCostAdjustmentAllowed>true</unitCostAdjustmentAllowed>\n" +
                "    <poLineData>\n" +
                "      <lineReqDelvDate>20190624</lineReqDelvDate>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007808168\">\n" +
                "    <name1>Mike</name1>\n" +
                "    <name2>Jones</name2>\n" +
                "    <address1>9 Wakara Way</address1>\n" +
                "    <address2>Suite 9 - High Towers</address2>\n" +
                "    <city>Salt Lake City</city>\n" +
                "    <state>UT</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>84108</postalCode>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007808169\">\n" +
                "    <name1>Paula</name1>\n" +
                "    <name2>Jones</name2>\n" +
                "    <address1>9 Main St</address1>\n" +
                "    <address2>Apt 9</address2>\n" +
                "    <city>Moab</city>\n" +
                "    <state>UT</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>84532</postalCode>\n" +
                "    <dayPhone>5555555555</dayPhone>\n" +
                "  </personPlace>\n" +
                "</hubOrder>";

        JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","dsg","Just Brand Limited","dsg",626);
        // api.setGroupName("dsg");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003179221", false);
            PackingManager.packAndShip(orderId, 2, 0.00, "1Z0000000000000000", false);


            log.debug(orderId);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
          //  api.confirmOrderShipment(os);
           // CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
            //api.setInvoiceTaxPercent(new BigDecimal("4.5"));
            //api.setInvoiceDiscDaysDue(5);
         //   api.setInvoiceNetDaysDue(30);
            //api.setInvoiceDiscPercent(new BigDecimal("10"));

           // System.out.println(api.sendInvoiceToCommerceHub(orderId, invoice));



        }catch (Exception e){
            fail();
        }








    }
}

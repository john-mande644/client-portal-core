package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsHomeDepotCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsLowesCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsQVCCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.LawlessQVCCommerceHubAPI;
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


public class LawlessQVCTesting {
    //private final static Logger log = LogManager.getLogger();

    //private final static String chSftpHost = "lawless.sftp-test.commercehub.com";
    private final static String chSftpHost = "lawless.sftp.commercehub.com";
    private final static String chUserName ="lawless";
    //private final static String chPassword = "Here.Muscle11esson#";
    private final static String chPassword = "Effectively38utton9Officer6$";
    private final static String chRemoteFolder = "qvc";
    private final static String vendorName ="Lawless";
    private final static String merchantName = "iqvc";
    private final static int clientAccountId = 651;


    @Test
    public void InventoryTest(){
        try {
            System.setProperty("com.owd.environment", "test");
            LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
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
    public  void LawlessTestCase1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000000071\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000000071</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000143630930</poNumber>\n" +
                "  <orderDate>20210303</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <credits>5.00</credits>\n" +
                "  <total>184.97</total>\n" +
                "  <shipTo personPlaceID=\"PP3010545248\" />\n" +
                "  <billTo personPlaceID=\"PP3010545249\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000000071\" />\n" +
                "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143630931</custOrderNumber>\n" +
                "  <custOrderDate>20210303</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchBatchNum>9999</merchBatchNum>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                "      \n" +
                "\t\t<packslipMessage>Character@ï¿½.ï¿½ï¿½ï¿½ï¿½ï¿½-ï¿½&lt;\"'Test</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143630931</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210303</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020167</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990101</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>M0670101</merchantProductId>\n" +
                "    <merchantSKU>9990670101</merchantSKU>\n" +
                "    <vendorSKU>0670101</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <lineShipping>10.00</lineShipping>\n" +
                "    <lineSubtotal>189.97</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <shippingHub>999</shippingHub>\n" +
                "    <lineCredits>5.00</lineCredits>\n" +
                "    <vendorWarehouseId>12345</vendorWarehouseId>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690002\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">18.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">12.50</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">9.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">14.50</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690003\">\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>2</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">18.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">12.50</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">9.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">14.50</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <unitShippingWeight weightUnit=\"OZ\">193</unitShippingWeight>\n" +
                "      <lineDiscAmount>0.0</lineDiscAmount>\n" +
                "      <creditAmount>5.0</creditAmount>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <vendorWarehouseId>12345</vendorWarehouseId>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690002\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690003\">2.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010545249\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>1 Stewart St</address2>\n" +
                "    <city>Ithaca</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>14850</postalCode>\n" +
                "    <email>msmith@xyzCompany.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010545248\">\n" +
                "    <name1>John Doe</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>1 Fuller Rd</address2>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000000071\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103690002\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <grossWeight weightUnit=\"OZ\">18.00</grossWeight>\n" +
                "    <heightDimension dimensionUnit=\"IN\">12.50</heightDimension>\n" +
                "    <widthDimension dimensionUnit=\"IN\">9.00</widthDimension>\n" +
                "    <depthDimension dimensionUnit=\"IN\">14.50</depthDimension>\n" +
                "    <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690003\">\n" +
                "    <packageIndex>02</packageIndex>\n" +
                "    <grossWeight weightUnit=\"OZ\">18.00</grossWeight>\n" +
                "    <heightDimension dimensionUnit=\"IN\">12.50</heightDimension>\n" +
                "    <widthDimension dimensionUnit=\"IN\">9.00</widthDimension>\n" +
                "    <depthDimension dimensionUnit=\"IN\">14.50</depthDimension>\n" +
                "    <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000071", false);
            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000000072\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000000072</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000143630934</poNumber>\n" +
                "  <orderDate>20210303</orderDate>\n" +
                "  <paymentMethod>MC</paymentMethod>\n" +
                "  <total>35.98</total>\n" +
                "  <shipTo personPlaceID=\"PP3010545250\" />\n" +
                "  <billTo personPlaceID=\"PP3010545251\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000000072\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143630935</custOrderNumber>\n" +
                "  <custOrderDate>20210303</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143630935</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210303</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020168</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990201</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>M0670201</merchantProductId>\n" +
                "    <merchantSKU>9990670201</merchantSKU>\n" +
                "    <vendorSKU>0670201</vendorSKU>\n" +
                "    <unitPrice>17.9900</unitPrice>\n" +
                "    <unitCost>9.0000</unitCost>\n" +
                "    <lineSubtotal>35.98</lineSubtotal>\n" +
                "    <shippingCode>UPSN_PM</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690004\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>2</packageQty>\n" +
                "      <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>850</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <prodCSdesc>Sample Product Note1</prodCSdesc>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690004\">2.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010545250\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>2 Woodbridge Ave</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010545251\">\n" +
                "    <name1>Jane</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>2 Woodbridge Ave</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <email>janesmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000000072\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103690004\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000072", false);

            //int orderId = 22094868;

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000000073\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000000073</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>000000143630938</poNumber>\n" +
                "  <orderDate>20210303</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <credits>15.00</credits>\n" +
                "  <total>28.64</total>\n" +
                "  <shipTo personPlaceID=\"PP3010545252\" />\n" +
                "  <billTo personPlaceID=\"PP3010545253\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000000073\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143630939</custOrderNumber>\n" +
                "  <custOrderDate>20210303</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143630939</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210303</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020169</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990301</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>M0670301</merchantProductId>\n" +
                "    <merchantSKU>9990670301</merchantSKU>\n" +
                "    <vendorSKU>0670301</vendorSKU>\n" +
                "    <unitPrice>14.9900</unitPrice>\n" +
                "    <unitCost>8.0000</unitCost>\n" +
                "    <lineShipping>5.00</lineShipping>\n" +
                "    <lineSubtotal>34.98</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <lineCredits>15.00</lineCredits>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690005\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630941</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690006\">\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630942</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <creditAmount>15.0</creditAmount>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690005\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690006\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020170</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990302</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantProductId>M0670302</merchantProductId>\n" +
                "    <merchantSKU>9990670302</merchantSKU>\n" +
                "    <vendorSKU>0670302</vendorSKU>\n" +
                "    <unitPrice>6.6600</unitPrice>\n" +
                "    <unitCost>3.0000</unitCost>\n" +
                "    <lineShipping>2.00</lineShipping>\n" +
                "    <lineSubtotal>8.66</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690007\">\n" +
                "      <boxNumber>03</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630943</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>03</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690007\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010545253\">\n" +
                "    <name1>Bill</name1>\n" +
                "    <name2>Johnson</name2>\n" +
                "    <address1>Apartment 3B</address1>\n" +
                "    <address2>3 Falke Plaza</address2>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>20166</postalCode>\n" +
                "    <email>billjohnson@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010545252\">\n" +
                "    <name1>Tom Smith</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>3 Westerre Parkway</address2>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>23233</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000000073\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103690006\">\n" +
                "    <packageIndex>02</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630942</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690007\">\n" +
                "    <packageIndex>03</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630943</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690005\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630941</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000073", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000000074\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000000074</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>000000143630945</poNumber>\n" +
                "  <orderDate>20210303</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <tax>8.57</tax>\n" +
                "  <total>95.54</total>\n" +
                "  <shipTo personPlaceID=\"PP3010545254\" />\n" +
                "  <billTo personPlaceID=\"PP3010545255\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000000074\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143630946</custOrderNumber>\n" +
                "  <custOrderDate>20210303</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>This is a sample order message to illustrate how QVC Drop Ship will deliver text messages with their order message. Note there can be up to two hundred and sixty four characters in each note. These notes are printed on each of the customer packing slips produced.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143630946</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210303</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020171</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990401</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>M0670401</merchantProductId>\n" +
                "    <merchantSKU>9990670401</merchantSKU>\n" +
                "    <vendorSKU>0670401</vendorSKU>\n" +
                "    <unitPrice>19.9900</unitPrice>\n" +
                "    <unitCost>10.0000</unitCost>\n" +
                "    <lineShipping>15.00</lineShipping>\n" +
                "    <lineTax>7.42</lineTax>\n" +
                "    <lineSubtotal>74.97</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690008\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630948</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690009\">\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630949</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690010\">\n" +
                "      <boxNumber>03</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630950</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "      <boxNumber3>03</boxNumber3>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690008\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690009\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690010\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020172</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990402</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantProductId>M0670402</merchantProductId>\n" +
                "    <merchantSKU>9990670402</merchantSKU>\n" +
                "    <vendorSKU>0670402</vendorSKU>\n" +
                "    <unitPrice>9.9900</unitPrice>\n" +
                "    <unitCost>5.0000</unitCost>\n" +
                "    <lineShipping>2.00</lineShipping>\n" +
                "    <lineTax>1.15</lineTax>\n" +
                "    <lineSubtotal>11.99</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690011\">\n" +
                "      <boxNumber>04</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630951</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>04</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690011\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010545254\">\n" +
                "    <name1>Joe Sample</name1>\n" +
                "    <address1>Care of: Jane Sample</address1>\n" +
                "    <address2>4 Jamboree Rd</address2>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92260</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010545255\">\n" +
                "    <name1>Mike</name1>\n" +
                "    <name2>Jones</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>4 La Jolla Village Drive</address2>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92122</postalCode>\n" +
                "    <email>mikejones@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000000074\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103690010\">\n" +
                "    <packageIndex>03</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630950</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690011\">\n" +
                "    <packageIndex>04</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630951</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690008\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630948</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690009\">\n" +
                "    <packageIndex>02</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630949</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000074", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000000075\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000000075</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>000000143630953</poNumber>\n" +
                "  <orderDate>20210303</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <tax>73.43</tax>\n" +
                "  <total>913.40</total>\n" +
                "  <shipTo personPlaceID=\"PP3010545256\" />\n" +
                "  <billTo personPlaceID=\"PP3010545257\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000000075\" />\n" +
                "  <salesDivision>IQ</salesDivision>\n" +
                "  <custOrderNumber>0143630954</custOrderNumber>\n" +
                "  <custOrderDate>20210303</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143630954</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210303</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020173</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990501</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>M0670501</merchantProductId>\n" +
                "    <merchantSKU>9990670501</merchantSKU>\n" +
                "    <vendorSKU>0670501</vendorSKU>\n" +
                "    <unitPrice>148.0000</unitPrice>\n" +
                "    <unitCost>75.0000</unitCost>\n" +
                "    <lineShipping>45.00</lineShipping>\n" +
                "    <lineTax>68.48</lineTax>\n" +
                "    <lineSubtotal>785.00</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690012\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630956</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690013\">\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630957</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690014\">\n" +
                "      <boxNumber>03</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630958</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690015\">\n" +
                "      <boxNumber>04</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630959</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690016\">\n" +
                "      <boxNumber>05</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630960</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "      <boxNumber3>03</boxNumber3>\n" +
                "      <boxNumber4>04</boxNumber4>\n" +
                "      <boxNumber5>05</boxNumber5>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690012\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690013\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690014\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690015\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690016\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020174</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990502</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantProductId>M0670502</merchantProductId>\n" +
                "    <merchantSKU>9990670502</merchantSKU>\n" +
                "    <vendorSKU>0670502</vendorSKU>\n" +
                "    <unitPrice>24.9900</unitPrice>\n" +
                "    <unitCost>12.5000</unitCost>\n" +
                "    <lineShipping>5.00</lineShipping>\n" +
                "    <lineTax>4.95</lineTax>\n" +
                "    <lineSubtotal>54.98</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690017\">\n" +
                "      <boxNumber>06</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630961</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690018\">\n" +
                "      <boxNumber>07</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630962</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>06</boxNumber1>\n" +
                "      <boxNumber2>07</boxNumber2>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690017\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103690018\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010545257\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Doe</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>5 Busch Blvd</address2>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <email>marydoe@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010545256\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>5 Busch Blvd</address2>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>33612</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000000075\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103690013\">\n" +
                "    <packageIndex>02</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630957</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690017\">\n" +
                "    <packageIndex>06</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630961</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690018\">\n" +
                "    <packageIndex>07</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630962</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690012\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630956</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690016\">\n" +
                "    <packageIndex>05</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630960</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690015\">\n" +
                "    <packageIndex>04</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630959</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103690014\">\n" +
                "    <packageIndex>03</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630958</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000075", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase6() {
         System.setProperty("com.owd.environment", "test");
         String poData = "<hubOrder transactionID=\"3000000076\">\n" +
                 "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                 "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                 "  <orderId>3000000076</orderId>\n" +
                 "  <lineCount>3</lineCount>\n" +
                 "  <poNumber>000000143630964</poNumber>\n" +
                 "  <orderDate>20210303</orderDate>\n" +
                 "  <paymentMethod>AX</paymentMethod>\n" +
                 "  <tax>22.68</tax>\n" +
                 "  <total>297.60</total>\n" +
                 "  <shipTo personPlaceID=\"PP3010545258\" />\n" +
                 "  <billTo personPlaceID=\"PP3010545259\" />\n" +
                 "  <shipFrom vendorShipID=\"VS100672002_3000000076\" />\n" +
                 "  <salesDivision>QVC</salesDivision>\n" +
                 "  <custOrderNumber>0143630965</custOrderNumber>\n" +
                 "  <custOrderDate>20210303</custOrderDate>\n" +
                 "  <poHdrData>\n" +
                 "    \n" +
                 "\t<custOrderNumber>0143630965</custOrderNumber>\n" +
                 "    \n" +
                 "\t<custOrderDate>20210303</custOrderDate>\n" +
                 "    \n" +
                 "\n" +
                 "  </poHdrData>\n" +
                 "  <lineItem>\n" +
                 "    <lineItemId>3000020175</lineItemId>\n" +
                 "    <orderLineNumber>1</orderLineNumber>\n" +
                 "    <merchantLineNumber>1</merchantLineNumber>\n" +
                 "    <qtyOrdered>5</qtyOrdered>\n" +
                 "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                 "    <UPC>9999999990601</UPC>\n" +
                 "    <description>Sample item description for line 1</description>\n" +
                 "    <merchantProductId>M0670601</merchantProductId>\n" +
                 "    <merchantSKU>9990670601</merchantSKU>\n" +
                 "    <vendorSKU>0670601</vendorSKU>\n" +
                 "    <unitPrice>6.9900</unitPrice>\n" +
                 "    <unitCost>3.0000</unitCost>\n" +
                 "    <lineTax>2.88</lineTax>\n" +
                 "    <lineSubtotal>34.95</lineSubtotal>\n" +
                 "    <shippingCode>UPSN_CG</shippingCode>\n" +
                 "    <expectedShipDate>20210308</expectedShipDate>\n" +
                 "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                 "    <shippingLabel ShippingLabelId=\"SL103690019\">\n" +
                 "      <boxNumber>01</boxNumber>\n" +
                 "      <packageQty>5</packageQty>\n" +
                 "      <returnShipMethod permit-number=\"77002\" />\n" +
                 "      <returnTrackingNumber>000000000143630967</returnTrackingNumber>\n" +
                 "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                 "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                 "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                 "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                 "    </shippingLabel>\n" +
                 "    <poLineData>\n" +
                 "      <prodColor>850</prodColor>\n" +
                 "      <prodSize>000</prodSize>\n" +
                 "      <prodCSdesc>Sample Product Note1</prodCSdesc>\n" +
                 "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                 "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                 "      <boxNumber1>01</boxNumber1>\n" +
                 "    </poLineData>\n" +
                 "    <packageQty package-label-id=\"PD103690019\">5.000000</packageQty>\n" +
                 "  </lineItem>\n" +
                 "  <lineItem>\n" +
                 "    <lineItemId>3000020176</lineItemId>\n" +
                 "    <orderLineNumber>2</orderLineNumber>\n" +
                 "    <merchantLineNumber>2</merchantLineNumber>\n" +
                 "    <qtyOrdered>3</qtyOrdered>\n" +
                 "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                 "    <UPC>9999999990602</UPC>\n" +
                 "    <description>Sample item description for line 2</description>\n" +
                 "    <merchantProductId>M0670602</merchantProductId>\n" +
                 "    <merchantSKU>9990670602</merchantSKU>\n" +
                 "    <vendorSKU>0670602</vendorSKU>\n" +
                 "    <unitPrice>79.9900</unitPrice>\n" +
                 "    <unitCost>35.0000</unitCost>\n" +
                 "    <lineTax>19.80</lineTax>\n" +
                 "    <lineSubtotal>239.97</lineSubtotal>\n" +
                 "    <shippingCode>UPSN_CG</shippingCode>\n" +
                 "    <expectedShipDate>20210308</expectedShipDate>\n" +
                 "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                 "    <shippingLabel ShippingLabelId=\"SL103690020\">\n" +
                 "      <boxNumber>02</boxNumber>\n" +
                 "      <packageQty>4</packageQty>\n" +
                 "      <returnShipMethod permit-number=\"77002\">US</returnShipMethod>\n" +
                 "      <returnTrackingNumber>000000000143630968</returnTrackingNumber>\n" +
                 "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                 "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                 "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                 "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                 "    </shippingLabel>\n" +
                 "    <poLineData>\n" +
                 "      <prodColor>851</prodColor>\n" +
                 "      <prodSize>000</prodSize>\n" +
                 "      <prodCSdesc>Sample Product Note2</prodCSdesc>\n" +
                 "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                 "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                 "      <boxNumber1>02</boxNumber1>\n" +
                 "    </poLineData>\n" +
                 "    <packageQty package-label-id=\"PD103690020\">3.000000</packageQty>\n" +
                 "  </lineItem>\n" +
                 "  <lineItem>\n" +
                 "    <lineItemId>3000020177</lineItemId>\n" +
                 "    <orderLineNumber>3</orderLineNumber>\n" +
                 "    <merchantLineNumber>3</merchantLineNumber>\n" +
                 "    <qtyOrdered>1</qtyOrdered>\n" +
                 "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                 "    <UPC>9999999990603</UPC>\n" +
                 "    <description>Sample item description for line 3</description>\n" +
                 "    <merchantProductId>M0670602</merchantProductId>\n" +
                 "    <merchantSKU>9990670603</merchantSKU>\n" +
                 "    <vendorSKU>0670603</vendorSKU>\n" +
                 "    <unitPrice>33.0000</unitPrice>\n" +
                 "    <unitCost>24.0000</unitCost>\n" +
                 "    <lineTax>2.80</lineTax>\n" +
                 "    <lineSubtotal>35.80</lineSubtotal>\n" +
                 "    <shippingCode>UPSN_CG</shippingCode>\n" +
                 "    <expectedShipDate>20210308</expectedShipDate>\n" +
                 "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                 "    <shippingLabel ShippingLabelId=\"SL103690020\">\n" +
                 "      <boxNumber>02</boxNumber>\n" +
                 "      <packageQty>4</packageQty>\n" +
                 "      <returnShipMethod permit-number=\"77002\">US</returnShipMethod>\n" +
                 "      <returnTrackingNumber>000000000143630968</returnTrackingNumber>\n" +
                 "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                 "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                 "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                 "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                 "    </shippingLabel>\n" +
                 "    <poLineData>\n" +
                 "      <prodColor>851</prodColor>\n" +
                 "      <prodSize>000</prodSize>\n" +
                 "      <prodCSdesc>Sample Product Note2</prodCSdesc>\n" +
                 "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                 "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                 "      <boxNumber1>02</boxNumber1>\n" +
                 "    </poLineData>\n" +
                 "    <packageQty package-label-id=\"PD103690020\">1.000000</packageQty>\n" +
                 "  </lineItem>\n" +
                 "  <personPlace personPlaceID=\"PP3010545259\">\n" +
                 "    <name1>Mary</name1>\n" +
                 "    <name2>Doe</name2>\n" +
                 "    <address1>XYZ Company Name</address1>\n" +
                 "    <address2>6 Quorum Drive</address2>\n" +
                 "    <city>Dallas</city>\n" +
                 "    <state>TX</state>\n" +
                 "    <country>US</country>\n" +
                 "    <postalCode>75240</postalCode>\n" +
                 "    <email>marydoe@sample.com</email>\n" +
                 "    <dayPhone>555-555-5555</dayPhone>\n" +
                 "  </personPlace>\n" +
                 "  <personPlace personPlaceID=\"PP3010545258\">\n" +
                 "    <name1>Lisa Smith</name1>\n" +
                 "    <address1>ABC Company Name</address1>\n" +
                 "    <address2>6 West Loop South</address2>\n" +
                 "    <city>Houston</city>\n" +
                 "    <state>TX</state>\n" +
                 "    <country>US</country>\n" +
                 "    <postalCode>77027</postalCode>\n" +
                 "    <dayPhone>555-555-5555</dayPhone>\n" +
                 "  </personPlace>\n" +
                 "  <vendorShipInfo vendorShipID=\"VS100672002_3000000076\">\n" +
                 "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                 "    <address1>100 QVC BOULEVARD</address1>\n" +
                 "    <city>ROCKY MOUNT</city>\n" +
                 "    <state>NC</state>\n" +
                 "    <country>USA</country>\n" +
                 "    <postalCode>27801</postalCode>\n" +
                 "    <partnerLocationId>0450</partnerLocationId>\n" +
                 "  </vendorShipInfo>\n" +
                 "  <packageLabel package-label-id=\"PD103690020\">\n" +
                 "    <packageIndex>02</packageIndex>\n" +
                 "    <returnShipMethod permit-number=\"77002\">US</returnShipMethod>\n" +
                 "    <returnTrackingNumber>000000000143630968</returnTrackingNumber>\n" +
                 "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                 "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                 "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                 "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                 "  </packageLabel>\n" +
                 "  <packageLabel package-label-id=\"PD103690019\">\n" +
                 "    <packageIndex>01</packageIndex>\n" +
                 "    <returnShipMethod permit-number=\"77002\" />\n" +
                 "    <returnTrackingNumber>000000000143630967</returnTrackingNumber>\n" +
                 "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                 "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                 "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                 "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                 "  </packageLabel>\n" +
                 "</hubOrder>";


         LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
         api.configure(chSftpHost, chUserName, chPassword, chRemoteFolder, vendorName, merchantName, clientAccountId);

         try {
             Object data = new XmlSlurper().parseText(poData);

             int orderId = api.importOrderNode(data, "3000000076", false);

             PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

             CommerceHubXmlFileFormatter confirmFileSource = new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
             confirmFileSource.setkIncludeShipFrom(true);
             OrderStatus os = new OrderStatus(orderId + "");
             confirmFileSource.setCurrentOrderStatus(os);
             confirmFileSource.setClientId(api.getClientId());
             System.out.println(confirmFileSource.getFileData());
             api.confirmOrderShipment(os);

         } catch (Exception e) {
             fail();
         }
     }

    @Test
    public  void LawlessTestCase7(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000000077\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000000077</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000143630970</poNumber>\n" +
                "  <orderDate>20210303</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <total>84.99</total>\n" +
                "  <shipTo personPlaceID=\"PP3010545260\" />\n" +
                "  <billTo personPlaceID=\"PP3010545261\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000000077\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143630971</custOrderNumber>\n" +
                "  <custOrderDate>20210303</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143630971</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210303</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020178</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999991101</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>M0671101</merchantProductId>\n" +
                "    <merchantSKU>9990671101</merchantSKU>\n" +
                "    <vendorSKU>0671101</vendorSKU>\n" +
                "    <unitPrice>69.9900</unitPrice>\n" +
                "    <unitCost>35.0000</unitCost>\n" +
                "    <lineShipping>15.00</lineShipping>\n" +
                "    <lineSubtotal>84.99</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690021\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630973</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690021\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010545260\">\n" +
                "    <name1>Lee Smith</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>11 Exeter St</address2>\n" +
                "    <city>Boston</city>\n" +
                "    <state>MA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>02116</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010545261\">\n" +
                "    <name1>Tom</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>11 Main St</address2>\n" +
                "    <city>Hyannis</city>\n" +
                "    <state>MA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>02601</postalCode>\n" +
                "    <email>tomsmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000000077\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103690021\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630973</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000077", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase8(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000000078\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000000078</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000143630975</poNumber>\n" +
                "  <orderDate>20210303</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <total>84.99</total>\n" +
                "  <shipTo personPlaceID=\"PP3010545262\" />\n" +
                "  <billTo personPlaceID=\"PP3010545263\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000000078\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143630976</custOrderNumber>\n" +
                "  <custOrderDate>20210303</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143630976</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210303</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020179</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999991301</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>M0671301</merchantProductId>\n" +
                "    <merchantSKU>9990671301</merchantSKU>\n" +
                "    <vendorSKU>0671301</vendorSKU>\n" +
                "    <unitPrice>69.9900</unitPrice>\n" +
                "    <unitCost>35.0000</unitCost>\n" +
                "    <lineShipping>15.00</lineShipping>\n" +
                "    <lineSubtotal>84.99</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690022\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690022\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010545262\">\n" +
                "    <name1>Samual Smith</name1>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>13 Fuller Rd</address2>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <dayPhone>518-810-0700</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010545263\">\n" +
                "    <name1>Samual</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>13 Fuller Rd</address2>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <email>SamSmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000000078\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103690022\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000078", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase9(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000000079\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000000079</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000143630979</poNumber>\n" +
                "  <orderDate>20210303</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <total>84.99</total>\n" +
                "  <shipTo personPlaceID=\"PP3010545264\" />\n" +
                "  <billTo personPlaceID=\"PP3010545265\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000000079\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143630980</custOrderNumber>\n" +
                "  <custOrderDate>20210303</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143630980</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210303</custOrderDate>\n" +
                "    \n" +
                "\t<poTypeCode>R</poTypeCode>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000020180</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999991401</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>M0671401</merchantProductId>\n" +
                "    <merchantSKU>9990671401</merchantSKU>\n" +
                "    <vendorSKU>0671401</vendorSKU>\n" +
                "    <unitPrice>69.9900</unitPrice>\n" +
                "    <unitCost>35.0000</unitCost>\n" +
                "    <lineShipping>15.00</lineShipping>\n" +
                "    <lineSubtotal>84.99</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210308</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210308</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103690023\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"LB\">10.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">10.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">5.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">10.00</depthDimension>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143630982</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210308</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210308</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103690023\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010545265\">\n" +
                "    <name1>Samual</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>13 Stewart St</address1>\n" +
                "    <address2>XYZ Company</address2>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <email>SamSmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010545264\">\n" +
                "    <name1>Samual Smith</name1>\n" +
                "    <address1>13 Fuller Rd</address1>\n" +
                "    <address2>XYZ Company</address2>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000000079\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103690023\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <grossWeight weightUnit=\"LB\">10.00</grossWeight>\n" +
                "    <heightDimension dimensionUnit=\"IN\">10.00</heightDimension>\n" +
                "    <widthDimension dimensionUnit=\"IN\">5.00</widthDimension>\n" +
                "    <depthDimension dimensionUnit=\"IN\">10.00</depthDimension>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143630982</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000079", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase10(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000108010\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000108010</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000143973558</poNumber>\n" +
                "  <orderDate>20210402</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <credits>5.00</credits>\n" +
                "  <total>184.97</total>\n" +
                "  <shipTo personPlaceID=\"PP3010690044\" />\n" +
                "  <billTo personPlaceID=\"PP3010690045\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000108010\" />\n" +
                "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143973559</custOrderNumber>\n" +
                "  <custOrderDate>20210402</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchBatchNum>9999</merchBatchNum>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                "      \n" +
                "\t\t<packslipMessage>Character@ï¿½.ï¿½ï¿½ï¿½ï¿½ï¿½-ï¿½&lt;\"'Test</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143973559</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210402</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000128030</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>810024519002</UPC>\n" +
                "    <merchantProductId>M0670101</merchantProductId>\n" +
                "    <merchantSKU>A456896 849 000</merchantSKU>\n" +
                "    <vendorSKU>Eyeshadow Palette 1</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <lineShipping>10.00</lineShipping>\n" +
                "    <lineSubtotal>189.97</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <shippingHub>999</shippingHub>\n" +
                "    <lineCredits>5.00</lineCredits>\n" +
                "    <vendorWarehouseId>One World Direct</vendorWarehouseId>\n" +
                "    <expectedShipDate>20210407</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210407</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730023\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">18.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">12.50</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">9.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">14.50</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730024\">\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>2</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">18.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">12.50</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">9.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">14.50</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <unitShippingWeight weightUnit=\"OZ\">193</unitShippingWeight>\n" +
                "      <lineDiscAmount>0.0</lineDiscAmount>\n" +
                "      <creditAmount>5.0</creditAmount>\n" +
                "      <lineReqShipDate>20210407</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210407</lineReqDelvDate>\n" +
                "      <vendorWarehouseId>One World Direct</vendorWarehouseId>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103730023\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103730024\">2.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010690045\">\n" +
                "    <name1>Mary</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>1 Stewart St</address2>\n" +
                "    <city>Ithaca</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>14850</postalCode>\n" +
                "    <email>msmith@xyzCompany.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010690044\">\n" +
                "    <name1>John Doe</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>1 Fuller Rd</address2>\n" +
                "    <city>Albany</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>12203</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000108010\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103730023\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <grossWeight weightUnit=\"OZ\">18.00</grossWeight>\n" +
                "    <heightDimension dimensionUnit=\"IN\">12.50</heightDimension>\n" +
                "    <widthDimension dimensionUnit=\"IN\">9.00</widthDimension>\n" +
                "    <depthDimension dimensionUnit=\"IN\">14.50</depthDimension>\n" +
                "    <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103730024\">\n" +
                "    <packageIndex>02</packageIndex>\n" +
                "    <grossWeight weightUnit=\"OZ\">18.00</grossWeight>\n" +
                "    <heightDimension dimensionUnit=\"IN\">12.50</heightDimension>\n" +
                "    <widthDimension dimensionUnit=\"IN\">9.00</widthDimension>\n" +
                "    <depthDimension dimensionUnit=\"IN\">14.50</depthDimension>\n" +
                "    <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000108010", false);
            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase11(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000108011\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000108011</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000143973562</poNumber>\n" +
                "  <orderDate>20210402</orderDate>\n" +
                "  <paymentMethod>MC</paymentMethod>\n" +
                "  <total>35.98</total>\n" +
                "  <shipTo personPlaceID=\"PP3010690046\" />\n" +
                "  <billTo personPlaceID=\"PP3010690047\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000108011\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143973563</custOrderNumber>\n" +
                "  <custOrderDate>20210402</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143973563</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210402</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000128031</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>810024519019</UPC>\n" +
                "    <merchantProductId>M0670201</merchantProductId>\n" +
                "    <merchantSKU>A456896 848 000</merchantSKU>\n" +
                "    <vendorSKU>Eyeshadow Palette 2</vendorSKU>\n" +
                "    <unitPrice>17.9900</unitPrice>\n" +
                "    <unitCost>9.0000</unitCost>\n" +
                "    <lineSubtotal>35.98</lineSubtotal>\n" +
                "    <shippingCode>UPSN_PM</shippingCode>\n" +
                "    <expectedShipDate>20210407</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210407</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730025\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>2</packageQty>\n" +
                "      <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>850</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <prodCSdesc>Sample Product Note1</prodCSdesc>\n" +
                "      <lineReqShipDate>20210407</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210407</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103730025\">2.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010690047\">\n" +
                "    <name1>Jane</name1>\n" +
                "    <name2>Smith</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>2 Woodbridge Ave</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <email>janesmith@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010690046\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>2 Woodbridge Ave</address2>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000108011\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103730025\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnRelatedMessage>To return this item please contact QVC Customer Service at 18003679444</returnRelatedMessage>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000108011", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase12(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000108012\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000108012</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>000000143973566</poNumber>\n" +
                "  <orderDate>20210402</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <credits>15.00</credits>\n" +
                "  <total>28.64</total>\n" +
                "  <shipTo personPlaceID=\"PP3010690048\" />\n" +
                "  <billTo personPlaceID=\"PP3010690049\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000108012\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143973567</custOrderNumber>\n" +
                "  <custOrderDate>20210402</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143973567</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210402</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000128032</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>810024519033</UPC>\n" +
                "    <merchantProductId>M0670301</merchantProductId>\n" +
                "    <merchantSKU>A457212 848 000</merchantSKU>\n" +
                "    <vendorSKU>Bronzer Blush Duo 1</vendorSKU>\n" +
                "    <unitPrice>14.9900</unitPrice>\n" +
                "    <unitCost>8.0000</unitCost>\n" +
                "    <lineShipping>5.00</lineShipping>\n" +
                "    <lineSubtotal>34.98</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <lineCredits>15.00</lineCredits>\n" +
                "    <expectedShipDate>20210407</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210407</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730026\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143973569</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730027\">\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143973570</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <creditAmount>15.0</creditAmount>\n" +
                "      <lineReqShipDate>20210407</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210407</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103730026\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103730027\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000128033</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>810024519040</UPC>\n" +
                "    <merchantProductId>M0670302</merchantProductId>\n" +
                "    <merchantSKU>A457212 849 000</merchantSKU>\n" +
                "    <vendorSKU>Bronzer Blush Duo 2</vendorSKU>\n" +
                "    <unitPrice>6.6600</unitPrice>\n" +
                "    <unitCost>3.0000</unitCost>\n" +
                "    <lineShipping>2.00</lineShipping>\n" +
                "    <lineSubtotal>8.66</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210407</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210407</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730028\">\n" +
                "      <boxNumber>03</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143973571</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210407</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210407</lineReqDelvDate>\n" +
                "      <boxNumber1>03</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103730028\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010690048\">\n" +
                "    <name1>Tom Smith</name1>\n" +
                "    <address1>ABC Company Name</address1>\n" +
                "    <address2>3 Westerre Parkway</address2>\n" +
                "    <city>Richmond</city>\n" +
                "    <state>VA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>23233</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010690049\">\n" +
                "    <name1>Bill</name1>\n" +
                "    <name2>Johnson</name2>\n" +
                "    <address1>Apartment 3B</address1>\n" +
                "    <address2>3 Falke Plaza</address2>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>20166</postalCode>\n" +
                "    <email>billjohnson@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000108012\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103730028\">\n" +
                "    <packageIndex>03</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143973571</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103730027\">\n" +
                "    <packageIndex>02</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143973570</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103730026\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143973569</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000108012", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCase13(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3000108013\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3000108013</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>000000143973573</poNumber>\n" +
                "  <orderDate>20210402</orderDate>\n" +
                "  <paymentMethod>AX</paymentMethod>\n" +
                "  <tax>8.57</tax>\n" +
                "  <total>95.54</total>\n" +
                "  <shipTo personPlaceID=\"PP3010690050\" />\n" +
                "  <billTo personPlaceID=\"PP3010690051\" />\n" +
                "  <shipFrom vendorShipID=\"VS100672002_3000108013\" />\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0143973574</custOrderNumber>\n" +
                "  <custOrderDate>20210402</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<packslipMessage>This is a sample order message to illustrate how QVC Drop Ship will deliver text messages with their order message. Note there can be up to two hundred and sixty four characters in each note. These notes are printed on each of the customer packing slips produced.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0143973574</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210402</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000128034</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>810024519064</UPC>\n" +
                "    <merchantProductId>M0670401</merchantProductId>\n" +
                "    <merchantSKU>A456893 849 000</merchantSKU>\n" +
                "    <vendorSKU>Lip Plumper Lipstick Duo 1</vendorSKU>\n" +
                "    <unitPrice>19.9900</unitPrice>\n" +
                "    <unitCost>10.0000</unitCost>\n" +
                "    <lineShipping>15.00</lineShipping>\n" +
                "    <lineTax>7.42</lineTax>\n" +
                "    <lineSubtotal>74.97</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210407</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210407</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730029\">\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143973576</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730030\">\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143973577</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730031\">\n" +
                "      <boxNumber>03</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143973578</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210407</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210407</lineReqDelvDate>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "      <boxNumber3>03</boxNumber3>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103730029\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103730030\">1.000000</packageQty>\n" +
                "    <packageQty package-label-id=\"PD103730031\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3000128035</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>810024519071</UPC>\n" +
                "    <merchantProductId>M0670402</merchantProductId>\n" +
                "    <merchantSKU>A456893 848 000</merchantSKU>\n" +
                "    <vendorSKU>Lip Plumper Lipstick Duo 2</vendorSKU>\n" +
                "    <unitPrice>9.9900</unitPrice>\n" +
                "    <unitCost>5.0000</unitCost>\n" +
                "    <lineShipping>2.00</lineShipping>\n" +
                "    <lineTax>1.15</lineTax>\n" +
                "    <lineSubtotal>11.99</lineSubtotal>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20210407</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210407</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL103730032\">\n" +
                "      <boxNumber>04</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnShipMethod permit-number=\"77002\" />\n" +
                "      <returnTrackingNumber>000000000143973579</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "      <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>000</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <lineReqShipDate>20210407</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210407</lineReqDelvDate>\n" +
                "      <boxNumber1>04</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD103730032\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3010690051\">\n" +
                "    <name1>Mike</name1>\n" +
                "    <name2>Jones</name2>\n" +
                "    <address1>XYZ Company</address1>\n" +
                "    <address2>4 La Jolla Village Drive</address2>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92122</postalCode>\n" +
                "    <email>mikejones@sample.com</email>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3010690050\">\n" +
                "    <name1>Joe Sample</name1>\n" +
                "    <address1>Care of: Jane Sample</address1>\n" +
                "    <address2>4 Jamboree Rd</address2>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92260</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS100672002_3000108013\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "    <partnerLocationId>0450</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD103730029\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143973576</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103730030\">\n" +
                "    <packageIndex>02</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143973577</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103730031\">\n" +
                "    <packageIndex>03</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143973578</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "  <packageLabel package-label-id=\"PD103730032\">\n" +
                "    <packageIndex>04</packageIndex>\n" +
                "    <returnShipMethod permit-number=\"77002\" />\n" +
                "    <returnTrackingNumber>000000000143973579</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>PA 175 9-73</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56902</returnServicePostalCode>\n" +
                "    <returnDeliveryConfirmationNumber>9158901003037000003393</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000108013", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    public  void LawlessTestCaseDropShip1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3025288854\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3025288854</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>546521021100001</poNumber>\n" +
                "  <orderDate>20210423</orderDate>\n" +
                "  <paymentMethod>Credits</paymentMethod>\n" +
                "  <merchandise>200.00</merchandise>\n" +
                "  <tax>12.21</tax>\n" +
                "  <credits>215.71</credits>\n" +
                "  <shipping>3.50</shipping>\n" +
                "  <total>215.71</total>\n" +
                "  <shipTo personPlaceID=\"PP5071350016\" />\n" +
                "  <billTo personPlaceID=\"PP5071350015\" />\n" +
                "  <shipFrom vendorShipID=\"VS136812763_3025288854\" />\n" +
                "  <memberNumber>XXXXXX2433</memberNumber>\n" +
                "  <salesDivision>IQVC</salesDivision>\n" +
                "  <custOrderNumber>5465210211</custOrderNumber>\n" +
                "  <custOrderDate>20210423</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchBatchNum>17758963</merchBatchNum>\n" +
                "    \n" +
                "\t<discountTerms>0.0</discountTerms>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>8003451515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>8003451212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>8003679444</rmPhone3>\n" +
                "      \n" +
                "\t\t<packslipMessage>In May, shop top beauty brands and empower cancer survivors when you join QVC, HSN and Cosmetic Executive Women in support of Beauty with Benefits, a Multimedia Event benefitting Cancer and Careers, all month long at QVC.com and HSN.com.</packslipMessage>\n" +
                "      \n" +
                "\t\t<packslipTemplate>KF</packslipTemplate>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>5465210211</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210423</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3031178748</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>810024514441</UPC>\n" +
                "    <description>Lawless Beauty Satin Luxe Classic Cream Lipstic k</description>\n" +
                "    <merchantProductId>A457744</merchantProductId>\n" +
                "    <merchantSKU>A457745</merchantSKU>\n" +
                "    <vendorSKU>Fawn</vendorSKU>\n" +
                "    <unitPrice>200.0000</unitPrice>\n" +
                "    <unitCost>14.2800</unitCost>\n" +
                "    <lineShipping>3.50</lineShipping>\n" +
                "    <lineTax>12.21</lineTax>\n" +
                "    <lineSubtotal>203.50</lineSubtotal>\n" +
                "    <shippingCode>USPSB_FC</shippingCode>\n" +
                "    <shippingHub>021</shippingHub>\n" +
                "    <lineCredits>215.71</lineCredits>\n" +
                "    <vendorWarehouseId>4676</vendorWarehouseId>\n" +
                "    <expectedShipDate>20210427</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210504</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL262888511\">\n" +
                "      <shippingCode description=\"USPS FIRST-CLASS MAIL\">USPSB_FC</shippingCode>\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"LB\">1.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">8.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">4.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">8.00</depthDimension>\n" +
                "      <returnShipMethod description=\"NEWGISTICS PREPAID LABEL\" permit-number=\"77000\">N1</returnShipMethod>\n" +
                "      <returnTrackingNumber>725001938002440002000426518018</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>V440</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "      <returnDeliveryConfirmationNumber>92023901007602000138748097</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>371</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <prodCSdesc>Fawn</prodCSdesc>\n" +
                "      <unitShippingWeight weightUnit=\"OZ\">6.8</unitShippingWeight>\n" +
                "      <lineDiscAmount>0.0</lineDiscAmount>\n" +
                "      <creditAmount>215.71</creditAmount>\n" +
                "      <lineReqShipDate>20210427</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210504</lineReqDelvDate>\n" +
                "      <vendorWarehouseId>4676</vendorWarehouseId>\n" +
                "      <permitIssuingCity>Mobridge</permitIssuingCity>\n" +
                "      <permitIssuingState>SD</permitIssuingState>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD262888511\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP5071350016\">\n" +
                "    <name1>QVC Drop Ship Test Order</name1>\n" +
                "    <address1>1200 Wilson Dr</address1>\n" +
                "    <city>West Chester</city>\n" +
                "    <state>PA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>19380-4262</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP5071350015\">\n" +
                "    <name1>QVC Drop Ship</name1>\n" +
                "    <name2>Test Order</name2>\n" +
                "    <address1>1200 Wilson Dr</address1>\n" +
                "    <city>West Chester</city>\n" +
                "    <state>PA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>19380-4262</postalCode>\n" +
                "    <dayPhone>4847011670</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS136812763_3025288854\">\n" +
                "    <name1>RM RETURNS</name1>\n" +
                "    <address1>100 QVC Boulevard</address1>\n" +
                "    <city>Rocky Mount</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>27815-</postalCode>\n" +
                "    <partnerLocationId>0440</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD262888511\" consignment-reference-number=\"80352573\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <shipMethod description=\"USPS FIRST-CLASS MAIL\">USPSB_FC</shipMethod>\n" +
                "    <grossWeight weightUnit=\"LB\">1.00</grossWeight>\n" +
                "    <heightDimension dimensionUnit=\"IN\">8.00</heightDimension>\n" +
                "    <widthDimension dimensionUnit=\"IN\">4.00</widthDimension>\n" +
                "    <depthDimension dimensionUnit=\"IN\">8.00</depthDimension>\n" +
                "    <returnShipMethod description=\"NEWGISTICS PREPAID LABEL\" permit-number=\"77000\">N1</returnShipMethod>\n" +
                "    <returnTrackingNumber>725001938002440002000426518018</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>V440</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "    <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    <returnDeliveryConfirmationNumber>92023901007602000138748097</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = 22367147;//api.importOrderNode(data, "3025288854", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            String fileData = confirmFileSource.getFileData();
            System.out.println(fileData);
            api.confirmOrderShipment(os);

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public  void LawlessTestCaseDropShip2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"3028191986\">\n" +
                "  <participatingParty name=\"qvc drop ship\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FZ31</sendersIdForReceiver>\n" +
                "  <orderId>3028191986</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>546679173200001</poNumber>\n" +
                "  <orderDate>20210429</orderDate>\n" +
                "  <paymentMethod>Credits</paymentMethod>\n" +
                "  <merchandise>200.00</merchandise>\n" +
                "  <tax>12.21</tax>\n" +
                "  <credits>215.71</credits>\n" +
                "  <shipping>3.50</shipping>\n" +
                "  <total>215.71</total>\n" +
                "  <shipTo personPlaceID=\"PP5080066018\" />\n" +
                "  <billTo personPlaceID=\"PP5080066017\" />\n" +
                "  <shipFrom vendorShipID=\"VS136812763_3028191986\" />\n" +
                "  <memberNumber>XXXXXX2433</memberNumber>\n" +
                "  <salesDivision>IQVC</salesDivision>\n" +
                "  <custOrderNumber>5466791732</custOrderNumber>\n" +
                "  <custOrderDate>20210429</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<merchBatchNum>17800109</merchBatchNum>\n" +
                "    \n" +
                "\t<discountTerms>0.0</discountTerms>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>8003451515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>8003451212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>8003679444</rmPhone3>\n" +
                "      \n" +
                "\t\t<packslipMessage>In May, shop top beauty brands and empower cancer survivors when you join QVC, HSN and Cosmetic Executive Women in support of Beauty with Benefits, a Multimedia Event benefitting Cancer and Careers, all month long at QVC.com and HSN.com.</packslipMessage>\n" +
                "      \n" +
                "\t\t<packslipTemplate>KF</packslipTemplate>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>5466791732</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20210429</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>3034421989</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>810024514441</UPC>\n" +
                "    <description>Lawless Beauty Satin Luxe Classic Cream Lipstic k</description>\n" +
                "    <merchantProductId>A457744</merchantProductId>\n" +
                "    <merchantSKU>A457745</merchantSKU>\n" +
                "    <vendorSKU>444</vendorSKU>\n" +
                "    <unitPrice>200.0000</unitPrice>\n" +
                "    <unitCost>14.2800</unitCost>\n" +
                "    <lineShipping>3.50</lineShipping>\n" +
                "    <lineTax>12.21</lineTax>\n" +
                "    <lineSubtotal>203.50</lineSubtotal>\n" +
                "    <shippingCode>USPSB_FC</shippingCode>\n" +
                "    <shippingHub>021</shippingHub>\n" +
                "    <lineCredits>215.71</lineCredits>\n" +
                "    <vendorWarehouseId>4676</vendorWarehouseId>\n" +
                "    <expectedShipDate>20210503</expectedShipDate>\n" +
                "    <requestedArrivalDate>20210510</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL263471140\">\n" +
                "      <shippingCode description=\"USPS FIRST-CLASS MAIL\">USPSB_FC</shippingCode>\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"LB\">1.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">8.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">4.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">8.00</depthDimension>\n" +
                "      <returnShipMethod description=\"NEWGISTICS PREPAID LABEL\" permit-number=\"77000\">N1</returnShipMethod>\n" +
                "      <returnTrackingNumber>725001938002440002100415229045</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>V440</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "      <returnDeliveryConfirmationNumber>92023901007602100137500334</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>371</prodColor>\n" +
                "      <prodSize>000</prodSize>\n" +
                "      <prodCSdesc>Fawn</prodCSdesc>\n" +
                "      <unitShippingWeight weightUnit=\"OZ\">6.8</unitShippingWeight>\n" +
                "      <lineDiscAmount>0.0</lineDiscAmount>\n" +
                "      <creditAmount>215.71</creditAmount>\n" +
                "      <lineReqShipDate>20210503</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20210510</lineReqDelvDate>\n" +
                "      <vendorWarehouseId>4676</vendorWarehouseId>\n" +
                "      <permitIssuingCity>Mobridge</permitIssuingCity>\n" +
                "      <permitIssuingState>SD</permitIssuingState>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "    <packageQty package-label-id=\"PD263471140\">1.000000</packageQty>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP5080066017\">\n" +
                "    <name1>QVC Drop Ship</name1>\n" +
                "    <name2>Test Order</name2>\n" +
                "    <address1>1200 Wilson Dr</address1>\n" +
                "    <city>West Chester</city>\n" +
                "    <state>PA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>19380-4262</postalCode>\n" +
                "    <dayPhone>4847011670</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP5080066018\">\n" +
                "    <name1>QVC Drop Ship Test Order</name1>\n" +
                "    <address1>1200 Wilson Dr</address1>\n" +
                "    <city>West Chester</city>\n" +
                "    <state>PA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>19380-4262</postalCode>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS136812763_3028191986\">\n" +
                "    <name1>RM RETURNS</name1>\n" +
                "    <address1>100 QVC Boulevard</address1>\n" +
                "    <city>Rocky Mount</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>27815-</postalCode>\n" +
                "    <partnerLocationId>0440</partnerLocationId>\n" +
                "  </vendorShipInfo>\n" +
                "  <packageLabel package-label-id=\"PD263471140\" consignment-reference-number=\"82380370\">\n" +
                "    <packageIndex>01</packageIndex>\n" +
                "    <shipMethod description=\"USPS FIRST-CLASS MAIL\">USPSB_FC</shipMethod>\n" +
                "    <grossWeight weightUnit=\"LB\">1.00</grossWeight>\n" +
                "    <heightDimension dimensionUnit=\"IN\">8.00</heightDimension>\n" +
                "    <widthDimension dimensionUnit=\"IN\">4.00</widthDimension>\n" +
                "    <depthDimension dimensionUnit=\"IN\">8.00</depthDimension>\n" +
                "    <returnShipMethod description=\"NEWGISTICS PREPAID LABEL\" permit-number=\"77000\">N1</returnShipMethod>\n" +
                "    <returnTrackingNumber>725001938002440002100415229045</returnTrackingNumber>\n" +
                "    <returnRoutingNumber>V440</returnRoutingNumber>\n" +
                "    <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "    <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    <returnDeliveryConfirmationNumber>92023901007602100137500334</returnDeliveryConfirmationNumber>\n" +
                "    <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "  </packageLabel>\n" +
                "</hubOrder>";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3028191986", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            confirmFileSource.setkIncludeShipFrom(true);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            String fileData = confirmFileSource.getFileData();
            System.out.println(fileData);
            api.confirmOrderShipment(os);

        }catch (Exception e){
            fail();
        }
    }


    /* @Test
    public  void LawlessTestCase10(){
        System.setProperty("com.owd.environment","test");
        String poData = "";


        LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
        api.configure(chSftpHost, chUserName, chPassword,chRemoteFolder,vendorName, merchantName, clientAccountId);

        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3000000079", false);

            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);

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
    }*/
}

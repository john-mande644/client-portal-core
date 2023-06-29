package com.owd.jobs.jobobjects.commercehub.testing;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.PackingManager;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.JustBrandsQVCCommerceHubAPI;
import com.owd.jobs.jobobjects.commercehub.CommerceHubXmlFileFormatter;
import groovy.util.XmlSlurper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by danny on 6/19/2019.
 */
public class JustBrandsQVCTesting {
    private final static Logger log = LogManager.getLogger();


        @Test
        public void QVCInventoryTest(){
                System.setProperty("com.owd.environment","test");
                JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","QVC","Just Brand Limited","QVC",626);
                log.debug(api.getInventoryFile());
                api.pushInventoryFile();



        }

        @Test
        public void FinalPackingListTest(){
                System.setProperty("com.owd.environment","test");
                JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","QVC","Just Brand Limited","QVC",626);
                api.processOrderFiles();
        }

    @Test
    public  void QVCTestCase1(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120883804\">\n" +
                "  <participatingParty name=\"QVC Online\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FC08</sendersIdForReceiver>\n" +
                "  <orderId>120883804</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000135330003</poNumber>\n" +
                "  <orderDate>20190614</orderDate>\n" +
                "  <paymentMethod>MASTERCARD</paymentMethod>\n" +
                "  <merchandise>179.97</merchandise>\n" +
                "  <tax>15.67</tax>\n" +
                "  <credits>5.00</credits>\n" +
                "  <shipping>10.00</shipping>\n" +
                "  <total>200.64</total>\n" +
                "  <shipTo personPlaceID=\"PP3007782060\" />\n" +
                "  <billTo personPlaceID=\"PP3007782061\" />\n" +
                "  <customer personPlaceID=\"PP3007782062\" />\n" +
                "  <shipFrom vendorShipID=\"VS54127388_120883804\" />\n" +
                "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>QVC</salesDivision>\n" +
                "  <custOrderNumber>0135330004</custOrderNumber>\n" +
                "  <custOrderDate>20190614</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                "      \n" +
                "\t\t<packslipMessage>Character@�.�����-�&lt;\"'Test</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0135330004</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190614</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>120993821</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990101</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>E123456</merchantProductId>\n" +
                "    <merchantSKU>9990101</merchantSKU>\n" +
                "    <vendorSKU>0670101</vendorSKU>\n" +
                "    <unitPrice>59.9900</unitPrice>\n" +
                "    <unitCost>25.0000</unitCost>\n" +
                "    <lineShipping>10.00</lineShipping>\n" +
                "    <lineTax>15.67</lineTax>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <lineCredits>5.00</lineCredits>\n" +
                "    <expectedShipDate>20190619</expectedShipDate>\n" +
                "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865001\">\n" +
                "      <shippingLabelIndicia>PS LIGHTWEIGHT[.]US POSTAGE PAID[.]UPS MI[.]eVS</shippingLabelIndicia>\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">16.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">6.516</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">3.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">6.516</depthDimension>\n" +
                "      <returnShipMethod permit-number=\"77000\">N1</returnShipMethod>\n" +
                "      <returnTrackingNumber>7250073096013000010709950721</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>V304</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "      <returnDeliveryConfirmationNumber>92023901007013001036711048</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865002\">\n" +
                "      <shippingLabelIndicia>PS LIGHTWEIGHT[.]US POSTAGE PAID[.]UPS MI[.]eVS</shippingLabelIndicia>\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>2</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">16.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">6.516</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">3.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">6.516</depthDimension>\n" +
                "      <returnShipMethod permit-number=\"77000\">N1</returnShipMethod>\n" +
                "      <returnTrackingNumber>7250073096013000010709950722</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>V304</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "      <returnDeliveryConfirmationNumber>92023901007013001036711048</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <prodColor>123</prodColor>\n" +
                "      <prodSize>321</prodSize>\n" +
                "      <prodCSdesc>Color and Size Description</prodCSdesc>\n" +
                "      <creditAmount>5.0</creditAmount>\n" +
                "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                "      <permitNumber>017196</permitNumber>\n" +
                "      <permitIssuingCity>Fairfield</permitIssuingCity>\n" +
                "      <permitIssuingState>OH</permitIssuingState>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007782062\">\n" +
                "    <name1>Mary Smith</name1>\n" +
                "    <address1>1 Stewart St.</address1>\n" +
                "    <city>Itahca</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>14850-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782060\">\n" +
                "    <name1>John Doe</name1>\n" +
                "    <address1>1 Fuller Rd</address1>\n" +
                "    <city>Weatherford</city>\n" +
                "    <state>OK</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>73096-7502</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782061\">\n" +
                "    <name1>Mary Smith</name1>\n" +
                "    <address1>1 Stewart St.</address1>\n" +
                "    <city>Itahca</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>14850-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS54127388_120883804\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","QVC","Just Brand Limited","QVC",626);
       // api.setGroupName("qvc");
       // api.setPackingSlipTemplate("iqcv");
       // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003136566", false);
            PackingManager.packAndShip(orderId, 4, 0.00, "1Z0000000000000000", false);


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
    public  void QVCTestCase2(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120883805\">\n" +
                "  <participatingParty name=\"QVC Online\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FC08</sendersIdForReceiver>\n" +
                "  <orderId>120883805</orderId>\n" +
                "  <lineCount>1</lineCount>\n" +
                "  <poNumber>000000135330007</poNumber>\n" +
                "  <orderDate>20190614</orderDate>\n" +
                "  <paymentMethod>MASTERCARD</paymentMethod>\n" +
                "  <merchandise>35.98</merchandise>\n" +
                "  <total>35.98</total>\n" +
                "  <shipTo personPlaceID=\"PP3007782063\" />\n" +
                "  <billTo personPlaceID=\"PP3007782064\" />\n" +
                "  <customer personPlaceID=\"PP3007782065\" />\n" +
                "  <shipFrom vendorShipID=\"VS54127388_120883805\" />\n" +
                "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>qvc</salesDivision>\n" +
                "  <custOrderNumber>0135330008</custOrderNumber>\n" +
                "  <custOrderDate>20190614</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0135330008</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190614</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>120993822</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>6</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990201</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>E123456</merchantProductId>\n" +
                "    <merchantSKU>9990201</merchantSKU>\n" +
                "    <vendorSKU>0670201</vendorSKU>\n" +
                "    <unitPrice>17.9900</unitPrice>\n" +
                "    <unitCost>9.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <vendorWarehouseId>1005</vendorWarehouseId>\n" +
                "    <expectedShipDate>20190619</expectedShipDate>\n" +
                "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865003\">\n" +
                "      <shippingCode description=\"UPS SUREPOST\">UPSN_ST_MW</shippingCode>\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <carrierRoutingCode>VA 222 9-11</carrierRoutingCode>\n" +
                "      <packageQty>2</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">17.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">8.2097</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">4.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">8.2097</depthDimension>\n" +
                "      <returnShipMethod permit-number=\"77000\">N1</returnShipMethod>\n" +
                "      <returnTrackingNumber>7250022202013000010709955396</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>V304</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "      <returnDeliveryConfirmationNumber>92023901007013001036715718</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                "      <vendorWarehouseId>1005</vendorWarehouseId>\n" +
                "      <permitNumber>015461</permitNumber>\n" +
                "      <permitIssuingCity>Fairfield</permitIssuingCity>\n" +
                "      <permitIssuingState>OH</permitIssuingState>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007782064\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782063\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NY</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837-1234</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782065\">\n" +
                "    <name1>Jane Smith</name1>\n" +
                "    <address1>2 Woodbridge Ave</address1>\n" +
                "    <city>Edison</city>\n" +
                "    <state>NJ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>08837-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS54127388_120883805\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","qvc","Just Brand Limited","qvc",626);
        // api.setGroupName("qvc");
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
    public  void QVCTestCase3(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120883806\">\n" +
                "  <participatingParty name=\"QVC Online\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FC08</sendersIdForReceiver>\n" +
                "  <orderId>120883806</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>000000135330011</poNumber>\n" +
                "  <orderDate>20190614</orderDate>\n" +
                "  <paymentMethod>MASTERCARD</paymentMethod>\n" +
                "  <merchandise>29.98</merchandise>\n" +
                "  <credits>15.00</credits>\n" +
                "  <shipping>5.00</shipping>\n" +
                "  <total>26.64</total>\n" +
                "  <shipTo personPlaceID=\"PP3007782066\" />\n" +
                "  <billTo personPlaceID=\"PP3007782067\" />\n" +
                "  <customer personPlaceID=\"PP3007782068\" />\n" +
                "  <shipFrom vendorShipID=\"VS54127388_120883806\" />\n" +
                "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>qvc</salesDivision>\n" +
                "  <custOrderNumber>0135330012</custOrderNumber>\n" +
                "  <custOrderDate>20190614</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0135330012</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190614</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>120993823</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>3</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990301</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>E123456</merchantProductId>\n" +
                "    <merchantSKU>9990301</merchantSKU>\n" +
                "    <vendorSKU>0670301</vendorSKU>\n" +
                "    <unitPrice>14.9900</unitPrice>\n" +
                "    <unitCost>8.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20190619</expectedShipDate>\n" +
                "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865004\">\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">16.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">6.516</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">3.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">6.516</depthDimension>\n" +
                "      <returnShipMethod permit-number=\"77000\">N1</returnShipMethod>\n" +
                "      <returnTrackingNumber>7250085207013000010709972935</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>V304</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "      <returnDeliveryConfirmationNumber>92023901007013001036733255</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865005\">\n" +
                "      <shippingLabelIndicia>PS LIGHTWEIGHT[.]US POSTAGE PAID[.]UPS MI[.]eVS</shippingLabelIndicia>\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">16.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">6.516</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">3.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">6.516</depthDimension>\n" +
                "      <returnShipMethod permit-number=\"77000\">N1</returnShipMethod>\n" +
                "      <returnTrackingNumber>7250085207013000010709972936</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>V304</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "      <returnDeliveryConfirmationNumber>92023901007013001036733255</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                "      <permitNumber>017196</permitNumber>\n" +
                "      <permitIssuingCity>Fairfield</permitIssuingCity>\n" +
                "      <permitIssuingState>OH</permitIssuingState>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>120993824</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>5</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990302</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantProductId>E123456</merchantProductId>\n" +
                "    <merchantSKU>9990302</merchantSKU>\n" +
                "    <vendorSKU>0670302</vendorSKU>\n" +
                "    <unitPrice>6.6600</unitPrice>\n" +
                "    <unitCost>3.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <vendorWarehouseId>2005</vendorWarehouseId>\n" +
                "    <expectedShipDate>20190619</expectedShipDate>\n" +
                "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865006\">\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">16.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">6.516</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">3.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">6.516</depthDimension>\n" +
                "      <returnShipMethod permit-number=\"77000\">N1</returnShipMethod>\n" +
                "      <returnTrackingNumber>7250085207013000010709972942</returnTrackingNumber>\n" +
                "      <returnRoutingNumber>V304</returnRoutingNumber>\n" +
                "      <returnServicePostalCode>56901</returnServicePostalCode>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "      <returnDeliveryConfirmationNumber>92023901007013001036733262</returnDeliveryConfirmationNumber>\n" +
                "      <returnShipping description=\"RETURN SHIPPING AMOUNT\">6.95</returnShipping>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                "      <vendorWarehouseId>2005</vendorWarehouseId>\n" +
                "      <permitNumber>017196</permitNumber>\n" +
                "      <permitIssuingCity>Fairfield</permitIssuingCity>\n" +
                "      <permitIssuingState>OH</permitIssuingState>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007782067\">\n" +
                "    <name1>Bill Johnson</name1>\n" +
                "    <address1>Apartment 3B</address1>\n" +
                "    <address2>3 Falke Plaza</address2>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>20166-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782068\">\n" +
                "    <name1>Bill Johnson</name1>\n" +
                "    <address1>Apartment 3B</address1>\n" +
                "    <address2>3 Falke Plaza</address2>\n" +
                "    <city>Sterling</city>\n" +
                "    <state>VA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>20166-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782066\">\n" +
                "    <name1>Tom Smith</name1>\n" +
                "    <address1>3 Westerre Parkway</address1>\n" +
                "    <city>Mesa</city>\n" +
                "    <state>AZ</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>85207-8116</postalCode>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS54127388_120883806\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","qvc","Just Brand Limited","qvc",626);
        // api.setGroupName("qvc");
        // api.setPackingSlipTemplate("iqcv");
        // api.setUseUPCLookup(true);
        try {
            Object data = new XmlSlurper().parseText(poData);

            int orderId = api.importOrderNode(data, "3003136566", false);
          /*  PackingManager.packAndShip(orderId,true, 2, 0.00, "1Z0000000000000000", false);


            log.debug(orderId);

            CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
            OrderStatus os = new OrderStatus(orderId+"");
            confirmFileSource.setCurrentOrderStatus(os);
            confirmFileSource.setClientId(api.getClientId());
            System.out.println(confirmFileSource.getFileData());
            api.confirmOrderShipment(os);

*/

        }catch (Exception e){
            fail();
        }








    }

    @Test
    public  void QVCTestCase4(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120883807\">\n" +
                "  <participatingParty name=\"QVC Online\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FC08</sendersIdForReceiver>\n" +
                "  <orderId>120883807</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>000000135330015</poNumber>\n" +
                "  <orderDate>20190614</orderDate>\n" +
                "  <paymentMethod>MASTERCARD</paymentMethod>\n" +
                "  <merchandise>59.97</merchandise>\n" +
                "  <tax>7.42</tax>\n" +
                "  <shipping>20.00</shipping>\n" +
                "  <total>97.38</total>\n" +
                "  <shipTo personPlaceID=\"PP3007782069\" />\n" +
                "  <billTo personPlaceID=\"PP3007782070\" />\n" +
                "  <customer personPlaceID=\"PP3007782071\" />\n" +
                "  <shipFrom vendorShipID=\"VS54127388_120883807\" />\n" +
                "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>qvc</salesDivision>\n" +
                "  <custOrderNumber>0135330016</custOrderNumber>\n" +
                "  <custOrderDate>20190614</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                "      \n" +
                "\t\t<packslipMessage>This is a sample order message to illustrate how QVC Drop Ship will deliver text messages with their order message. Note there can be up to two hundred and sixty four characters in each note. These notes are printed on each of the customer packing slips produced.</packslipMessage>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0135330016</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190614</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>120993825</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>3</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990401</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>E123456</merchantProductId>\n" +
                "    <merchantSKU>9990401</merchantSKU>\n" +
                "    <vendorSKU>0670401</vendorSKU>\n" +
                "    <unitPrice>19.9900</unitPrice>\n" +
                "    <unitCost>10.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20190619</expectedShipDate>\n" +
                "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865007\">\n" +
                "      <shippingLabelIndicia>PS LIGHTWEIGHT[.]US POSTAGE PAID[.]UPS MI[.]eVS</shippingLabelIndicia>\n" +
                "      <trackingNumberBanner>USPS TRACKING # eVS</trackingNumberBanner>\n" +
                "      <trackingNumber>1Z000000000135330019</trackingNumber>\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">4.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">2.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">2.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">2.00</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865008\">\n" +
                "      <shippingLabelIndicia>PS LIGHTWEIGHT[.]US POSTAGE PAID[.]UPS MI[.]eVS</shippingLabelIndicia>\n" +
                "      <trackingNumberBanner>USPS TRACKING # eVS</trackingNumberBanner>\n" +
                "      <trackingNumber>1Z000000000135330021</trackingNumber>\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>02</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">4.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">2.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">2.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">2.00</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865009\">\n" +
                "      <shippingLabelIndicia>PS LIGHTWEIGHT[.]US POSTAGE PAID[.]UPS MI[.]eVS</shippingLabelIndicia>\n" +
                "      <trackingNumberBanner>USPS TRACKING # eVS</trackingNumberBanner>\n" +
                "      <trackingNumber>1Z000000000135330023</trackingNumber>\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>03</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">4.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">2.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">2.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">2.00</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                "      <permitNumber>100946</permitNumber>\n" +
                "      <permitIssuingCity>KANSAS CITY</permitIssuingCity>\n" +
                "      <permitIssuingState>ks</permitIssuingState>\n" +
                "      <UPSTrackingNumber1>1Z000000000135330019</UPSTrackingNumber1>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "      <UPSTrackingNumber2>1Z000000000135330021</UPSTrackingNumber2>\n" +
                "      <boxNumber2>02</boxNumber2>\n" +
                "      <UPSTrackingNumber3>1Z000000000135330023</UPSTrackingNumber3>\n" +
                "      <boxNumber3>03</boxNumber3>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>120993826</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>2</merchantLineNumber>\n" +
                "    <qtyOrdered>1</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990402</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantProductId>E123456</merchantProductId>\n" +
                "    <merchantSKU>9990402</merchantSKU>\n" +
                "    <vendorSKU>0670402</vendorSKU>\n" +
                "    <unitPrice>9.9900</unitPrice>\n" +
                "    <unitCost>5.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20190619</expectedShipDate>\n" +
                "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865010\">\n" +
                "      <shippingLabelIndicia>PS LIGHTWEIGHT[.]US POSTAGE PAID[.]UPS MI[.]eVS</shippingLabelIndicia>\n" +
                "      <trackingNumberBanner>USPS TRACKING # eVS</trackingNumberBanner>\n" +
                "      <trackingNumber>1Z000000000135330025</trackingNumber>\n" +
                "      <shippingCode description=\"UPS MAIL INNOVATIONS\">UPSN_FC</shippingCode>\n" +
                "      <boxNumber>01</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">4.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">2.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">2.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">2.00</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                "      <permitNumber>100946</permitNumber>\n" +
                "      <permitIssuingCity>KANSAS CITY</permitIssuingCity>\n" +
                "      <permitIssuingState>ks</permitIssuingState>\n" +
                "      <UPSTrackingNumber1>1Z000000000135330025</UPSTrackingNumber1>\n" +
                "      <boxNumber1>01</boxNumber1>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007782071\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <address1>4 La Jolla Village Dr</address1>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92122-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782069\">\n" +
                "    <name1>Joe Sample</name1>\n" +
                "    <address1>Care of: Jane Sample</address1>\n" +
                "    <address2>4 Jamboree Road</address2>\n" +
                "    <city>Newport Beach</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92660-1234</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782070\">\n" +
                "    <name1>Mike Jones</name1>\n" +
                "    <address1>4 La Jolla Village Dr</address1>\n" +
                "    <city>San Diego</city>\n" +
                "    <state>CA</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>92122-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS54127388_120883807\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","qvc","Just Brand Limited","qvc",626);
        // api.setGroupName("qvc");
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
    public  void QVCTestCase5(){
        System.setProperty("com.owd.environment","test");
        String poData = "<hubOrder transactionID=\"120883808\">\n" +
                "  <participatingParty name=\"QVC Online\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                "  <sendersIdForReceiver>FC08</sendersIdForReceiver>\n" +
                "  <orderId>120883808</orderId>\n" +
                "  <lineCount>2</lineCount>\n" +
                "  <poNumber>000000135330027</poNumber>\n" +
                "  <orderDate>20190614</orderDate>\n" +
                "  <paymentMethod>MASTERCARD</paymentMethod>\n" +
                "  <merchandise>789.98</merchandise>\n" +
                "  <tax>4.12</tax>\n" +
                "  <shipping>45.00</shipping>\n" +
                "  <total>839.10</total>\n" +
                "  <shipTo personPlaceID=\"PP3007782072\" />\n" +
                "  <billTo personPlaceID=\"PP3007782073\" />\n" +
                "  <customer personPlaceID=\"PP3007782074\" />\n" +
                "  <shipFrom vendorShipID=\"VS54127388_120883808\" />\n" +
                "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                "  <shippingCode>UPSN_CG</shippingCode>\n" +
                "  <salesDivision>qvc</salesDivision>\n" +
                "  <custOrderNumber>0135330028</custOrderNumber>\n" +
                "  <custOrderDate>20190614</custOrderDate>\n" +
                "  <poHdrData>\n" +
                "    \n" +
                "\t<packListData>\n" +
                "      \n" +
                "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                "      \n" +
                "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                "      \n" +
                "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                "      \n" +
                "\t\n" +
                "    </packListData>\n" +
                "    \n" +
                "\t<custOrderNumber>0135330028</custOrderNumber>\n" +
                "    \n" +
                "\t<custOrderDate>20190614</custOrderDate>\n" +
                "    \n" +
                "\n" +
                "  </poHdrData>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>120993827</lineItemId>\n" +
                "    <orderLineNumber>1</orderLineNumber>\n" +
                "    <merchantLineNumber>1</merchantLineNumber>\n" +
                "    <qtyOrdered>5</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990501</UPC>\n" +
                "    <description>Sample item description for line 1</description>\n" +
                "    <merchantProductId>E123456</merchantProductId>\n" +
                "    <merchantSKU>9990501</merchantSKU>\n" +
                "    <vendorSKU>0670501</vendorSKU>\n" +
                "    <unitPrice>148.0000</unitPrice>\n" +
                "    <unitCost>75.0000</unitCost>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <expectedShipDate>20190619</expectedShipDate>\n" +
                "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865011\">\n" +
                "      <boxNumber>1</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865012\">\n" +
                "      <boxNumber>2</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865013\">\n" +
                "      <boxNumber>3</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865014\">\n" +
                "      <boxNumber>4</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865015\">\n" +
                "      <boxNumber>5</boxNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                "      <boxNumber1>1</boxNumber1>\n" +
                "      <boxNumber2>2</boxNumber2>\n" +
                "      <boxNumber3>3</boxNumber3>\n" +
                "      <boxNumber4>4</boxNumber4>\n" +
                "      <boxNumber5>5</boxNumber5>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <lineItem>\n" +
                "    <lineItemId>120993828</lineItemId>\n" +
                "    <orderLineNumber>2</orderLineNumber>\n" +
                "    <merchantLineNumber>4</merchantLineNumber>\n" +
                "    <qtyOrdered>2</qtyOrdered>\n" +
                "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                "    <UPC>9999999990502</UPC>\n" +
                "    <description>Sample item description for line 2</description>\n" +
                "    <merchantProductId>E123456</merchantProductId>\n" +
                "    <merchantSKU>9990502</merchantSKU>\n" +
                "    <vendorSKU>0670502</vendorSKU>\n" +
                "    <unitPrice>24.9900</unitPrice>\n" +
                "    <unitCost>12.5000</unitCost>\n" +
                "    <lineShipping>5.00</lineShipping>\n" +
                "    <shippingCode>UPSN_CG</shippingCode>\n" +
                "    <lineCredits>15.00</lineCredits>\n" +
                "    <vendorWarehouseId>3005</vendorWarehouseId>\n" +
                "    <expectedShipDate>20190619</expectedShipDate>\n" +
                "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865016\">\n" +
                "      <shippingLabelIndicia>PARCEL SELECT[.]US POSTAGE PAID[.]UPS[.]eVS</shippingLabelIndicia>\n" +
                "      <trackingNumberBanner>USPS TRACKING # eVS</trackingNumberBanner>\n" +
                "      <trackingNumber>1Z000000000135330031</trackingNumber>\n" +
                "      <shippingCode description=\"UPS SUREPOST\">UPSN_ST</shippingCode>\n" +
                "      <boxNumber>1</boxNumber>\n" +
                "      <deliveryConfirmationBanner>USPS TRACKING # eVS</deliveryConfirmationBanner>\n" +
                "      <deliveryConfirmationNumber>92612904851703581000000555</deliveryConfirmationNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">90.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">5.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">7.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">10.00</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <shippingLabel ShippingLabelId=\"SL102865017\">\n" +
                "      <shippingLabelIndicia>PARCEL SELECT[.]US POSTAGE PAID[.]UPS[.]eVS</shippingLabelIndicia>\n" +
                "      <trackingNumberBanner>USPS TRACKING # eVS</trackingNumberBanner>\n" +
                "      <trackingNumber>1Z000000000135330033</trackingNumber>\n" +
                "      <shippingCode description=\"UPS SUREPOST\">UPSN_ST</shippingCode>\n" +
                "      <boxNumber>2</boxNumber>\n" +
                "      <deliveryConfirmationBanner>USPS TRACKING # eVS</deliveryConfirmationBanner>\n" +
                "      <deliveryConfirmationNumber>92612904851703581000000555</deliveryConfirmationNumber>\n" +
                "      <packageQty>1</packageQty>\n" +
                "      <grossWeight weightUnit=\"OZ\">90.00</grossWeight>\n" +
                "      <heightDimension dimensionUnit=\"IN\">5.00</heightDimension>\n" +
                "      <widthDimension dimensionUnit=\"IN\">7.00</widthDimension>\n" +
                "      <depthDimension dimensionUnit=\"IN\">10.00</depthDimension>\n" +
                "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                "    </shippingLabel>\n" +
                "    <poLineData>\n" +
                "      <creditAmount>15.0</creditAmount>\n" +
                "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                "      <vendorWarehouseId>3005</vendorWarehouseId>\n" +
                "      <permitNumber>016060</permitNumber>\n" +
                "      <permitIssuingCity>KANSAS CITY</permitIssuingCity>\n" +
                "      <permitIssuingState>ks</permitIssuingState>\n" +
                "      <UPSTrackingNumber1>1Z000000000135330031</UPSTrackingNumber1>\n" +
                "      <boxNumber1>1</boxNumber1>\n" +
                "      <UPSTrackingNumber2>1Z000000000135330033</UPSTrackingNumber2>\n" +
                "      <boxNumber2>2</boxNumber2>\n" +
                "    </poLineData>\n" +
                "  </lineItem>\n" +
                "  <personPlace personPlaceID=\"PP3007782072\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>5 Busch Blvd</address1>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>33612-1234</postalCode>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782073\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>5 Busch Blvd</address1>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>33612-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <personPlace personPlaceID=\"PP3007782074\">\n" +
                "    <name1>Mary Doe</name1>\n" +
                "    <address1>5 Busch Blvd</address1>\n" +
                "    <city>Tampa</city>\n" +
                "    <state>FL</state>\n" +
                "    <country>US</country>\n" +
                "    <postalCode>33612-1234</postalCode>\n" +
                "    <dayPhone>555-555-5555</dayPhone>\n" +
                "  </personPlace>\n" +
                "  <vendorShipInfo vendorShipID=\"VS54127388_120883808\">\n" +
                "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                "    <address1>100 QVC BOULEVARD</address1>\n" +
                "    <city>ROCKY MOUNT</city>\n" +
                "    <state>NC</state>\n" +
                "    <country>USA</country>\n" +
                "    <postalCode>27801</postalCode>\n" +
                "  </vendorShipInfo>\n" +
                "</hubOrder>";

        JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
        api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","qvc","Just Brand Limited","qvc",626);
        // api.setGroupName("qvc");
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



        }catch (Exception e){
            fail();
        }








    }

        @Test
        public  void QVCTestCase6(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120883809\">\n" +
                        "  <participatingParty name=\"QVC Online\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                        "  <sendersIdForReceiver>FC08</sendersIdForReceiver>\n" +
                        "  <orderId>120883809</orderId>\n" +
                        "  <lineCount>2</lineCount>\n" +
                        "  <poNumber>000000135330035</poNumber>\n" +
                        "  <orderDate>20190614</orderDate>\n" +
                        "  <paymentMethod>MASTERCARD</paymentMethod>\n" +
                        "  <merchandise>273.00</merchandise>\n" +
                        "  <tax>22.68</tax>\n" +
                        "  <total>297.60</total>\n" +
                        "  <shipTo personPlaceID=\"PP3007782075\" />\n" +
                        "  <billTo personPlaceID=\"PP3007782076\" />\n" +
                        "  <customer personPlaceID=\"PP3007782077\" />\n" +
                        "  <shipFrom vendorShipID=\"VS54127388_120883809\" />\n" +
                        "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                        "  <shippingCode>UPSN_CG</shippingCode>\n" +
                        "  <salesDivision>qvc</salesDivision>\n" +
                        "  <custOrderNumber>0135330036</custOrderNumber>\n" +
                        "  <custOrderDate>20190614</custOrderDate>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<packListData>\n" +
                        "      \n" +
                        "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                        "      \n" +
                        "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                        "      \n" +
                        "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                        "      \n" +
                        "\t\n" +
                        "    </packListData>\n" +
                        "    \n" +
                        "\t<custOrderNumber>0135330036</custOrderNumber>\n" +
                        "    \n" +
                        "\t<custOrderDate>20190614</custOrderDate>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993829</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990601</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990601</merchantSKU>\n" +
                        "    <vendorSKU>0670601</vendorSKU>\n" +
                        "    <unitPrice>6.9900</unitPrice>\n" +
                        "    <unitCost>3.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865018\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865019\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865020\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865021\">\n" +
                        "      <boxNumber>4</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865022\">\n" +
                        "      <boxNumber>5</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "      <boxNumber4>4</boxNumber4>\n" +
                        "      <boxNumber5>5</boxNumber5>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993830</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990602</UPC>\n" +
                        "    <description>Sample item description for line 2</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990602</merchantSKU>\n" +
                        "    <vendorSKU>0670602</vendorSKU>\n" +
                        "    <unitPrice>79.9900</unitPrice>\n" +
                        "    <unitCost>35.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865023\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865024\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865025\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007782075\">\n" +
                        "    <name1>Lisa Smith</name1>\n" +
                        "    <address1>6 West Loop South</address1>\n" +
                        "    <city>Houston</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>77027-1234</postalCode>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007782076\">\n" +
                        "    <name1>Mary Doe</name1>\n" +
                        "    <address1>6 Quorum Drive</address1>\n" +
                        "    <city>Dallas</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>75240-1234</postalCode>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007782077\">\n" +
                        "    <name1>Mary Doe</name1>\n" +
                        "    <address1>6 Quorum Drive</address1>\n" +
                        "    <city>Dallas</city>\n" +
                        "    <state>TX</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>75240-1234</postalCode>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS54127388_120883809\">\n" +
                        "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                        "    <address1>100 QVC BOULEVARD</address1>\n" +
                        "    <city>ROCKY MOUNT</city>\n" +
                        "    <state>NC</state>\n" +
                        "    <country>USA</country>\n" +
                        "    <postalCode>27801</postalCode>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","qvc","Just Brand Limited","qvc",626);
                // api.setGroupName("qvc");
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
        public  void QVCTestCase6SendDocs() {
                System.setProperty("com.owd.environment", "test");

                JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","qvc","Just Brand Limited","qvc",626);

                CommerceHubXmlFileFormatter confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                OrderStatus os = new OrderStatus(18022036+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);

              confirmFileSource =  new CommerceHubXmlFileFormatter(api.getSftpUser(), api.getsFtpRemoteFolder(), CommerceHubXmlFileFormatter.CommerceHubXmlFileType.STATUS_SHIPPED);
                 os = new OrderStatus(18022037+"");
                confirmFileSource.setCurrentOrderStatus(os);
                confirmFileSource.setClientId(api.getClientId());
                System.out.println(confirmFileSource.getFileData());
                api.confirmOrderShipment(os);

                try {
                       // PackingManager.packAndShip(18022037, 2, 0.00, "1Z0000000000000000", false);
                }catch (Exception e){
                        e.printStackTrace();

                }





        }


        @Test
        public  void QVCTestCase7(){
                System.setProperty("com.owd.environment","test");
                String poData = "<hubOrder transactionID=\"120883810\">\n" +
                        "  <participatingParty name=\"QVC Online\" roleType=\"merchant\" participationCode=\"From:\">qvc</participatingParty>\n" +
                        "  <sendersIdForReceiver>FC08</sendersIdForReceiver>\n" +
                        "  <orderId>120883810</orderId>\n" +
                        "  <lineCount>10</lineCount>\n" +
                        "  <poNumber>000000135330039</poNumber>\n" +
                        "  <orderDate>20190614</orderDate>\n" +
                        "  <paymentMethod>MASTERCARD</paymentMethod>\n" +
                        "  <merchandise>440.00</merchandise>\n" +
                        "  <tax>36.25</tax>\n" +
                        "  <credits>25.00</credits>\n" +
                        "  <shipping>55.00</shipping>\n" +
                        "  <total>450.70</total>\n" +
                        "  <shipTo personPlaceID=\"PP3007782078\" />\n" +
                        "  <billTo personPlaceID=\"PP3007782079\" />\n" +
                        "  <customer personPlaceID=\"PP3007782080\" />\n" +
                        "  <shipFrom vendorShipID=\"VS54127388_120883810\" />\n" +
                        "  <memberNumber>XXXXXX7890</memberNumber>\n" +
                        "  <shippingCode>UPSN_CG</shippingCode>\n" +
                        "  <salesDivision>qvc</salesDivision>\n" +
                        "  <custOrderNumber>0135330040</custOrderNumber>\n" +
                        "  <custOrderDate>20190614</custOrderDate>\n" +
                        "  <poHdrData>\n" +
                        "    \n" +
                        "\t<packListData>\n" +
                        "      \n" +
                        "\t\t<rmPhone1>1-800-345-1515</rmPhone1>\n" +
                        "      \n" +
                        "\t\t<rmPhone2>1-800-345-1212</rmPhone2>\n" +
                        "      \n" +
                        "\t\t<rmPhone3>1-800-367-9444</rmPhone3>\n" +
                        "      \n" +
                        "\t\t<packslipMessage>Test Message 1 of 4 Test Message 2 of 4 Test Message 3 of 4 Test Message 4 of 4</packslipMessage>\n" +
                        "      \n" +
                        "\t\n" +
                        "    </packListData>\n" +
                        "    \n" +
                        "\t<custOrderNumber>0135330040</custOrderNumber>\n" +
                        "    \n" +
                        "\t<custOrderDate>20190614</custOrderDate>\n" +
                        "    \n" +
                        "\n" +
                        "  </poHdrData>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993831</lineItemId>\n" +
                        "    <orderLineNumber>1</orderLineNumber>\n" +
                        "    <merchantLineNumber>1</merchantLineNumber>\n" +
                        "    <qtyOrdered>10</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990901</UPC>\n" +
                        "    <description>Sample item description for line 1</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990901</merchantSKU>\n" +
                        "    <vendorSKU>0670901</vendorSKU>\n" +
                        "    <unitPrice>3.9900</unitPrice>\n" +
                        "    <unitCost>2.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865026\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865027\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865028\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865029\">\n" +
                        "      <boxNumber>4</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865030\">\n" +
                        "      <boxNumber>5</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865031\">\n" +
                        "      <boxNumber>6</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865032\">\n" +
                        "      <boxNumber>7</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865033\">\n" +
                        "      <boxNumber>8</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865034\">\n" +
                        "      <boxNumber>9</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865035\">\n" +
                        "      <boxNumber>10</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "      <boxNumber4>4</boxNumber4>\n" +
                        "      <boxNumber5>5</boxNumber5>\n" +
                        "      <boxNumber6>6</boxNumber6>\n" +
                        "      <boxNumber7>7</boxNumber7>\n" +
                        "      <boxNumber8>8</boxNumber8>\n" +
                        "      <boxNumber9>9</boxNumber9>\n" +
                        "      <boxNumber10>10</boxNumber10>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993832</lineItemId>\n" +
                        "    <orderLineNumber>2</orderLineNumber>\n" +
                        "    <merchantLineNumber>2</merchantLineNumber>\n" +
                        "    <qtyOrdered>9</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990902</UPC>\n" +
                        "    <description>Sample item description for line 2</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990902</merchantSKU>\n" +
                        "    <vendorSKU>0670902</vendorSKU>\n" +
                        "    <unitPrice>4.9900</unitPrice>\n" +
                        "    <unitCost>2.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865036\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865037\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865038\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865039\">\n" +
                        "      <boxNumber>4</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865040\">\n" +
                        "      <boxNumber>5</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865041\">\n" +
                        "      <boxNumber>6</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865042\">\n" +
                        "      <boxNumber>7</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865043\">\n" +
                        "      <boxNumber>8</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865044\">\n" +
                        "      <boxNumber>9</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "      <boxNumber4>4</boxNumber4>\n" +
                        "      <boxNumber5>5</boxNumber5>\n" +
                        "      <boxNumber6>6</boxNumber6>\n" +
                        "      <boxNumber7>7</boxNumber7>\n" +
                        "      <boxNumber8>8</boxNumber8>\n" +
                        "      <boxNumber9>9</boxNumber9>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993833</lineItemId>\n" +
                        "    <orderLineNumber>3</orderLineNumber>\n" +
                        "    <merchantLineNumber>3</merchantLineNumber>\n" +
                        "    <qtyOrdered>8</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990903</UPC>\n" +
                        "    <description>Sample item description for line 3</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990903</merchantSKU>\n" +
                        "    <vendorSKU>0670903</vendorSKU>\n" +
                        "    <unitPrice>5.9900</unitPrice>\n" +
                        "    <unitCost>3.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865045\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865046\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865047\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865048\">\n" +
                        "      <boxNumber>4</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865049\">\n" +
                        "      <boxNumber>5</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865050\">\n" +
                        "      <boxNumber>6</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865051\">\n" +
                        "      <boxNumber>7</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865052\">\n" +
                        "      <boxNumber>8</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "      <boxNumber4>4</boxNumber4>\n" +
                        "      <boxNumber5>5</boxNumber5>\n" +
                        "      <boxNumber6>6</boxNumber6>\n" +
                        "      <boxNumber7>7</boxNumber7>\n" +
                        "      <boxNumber8>8</boxNumber8>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993834</lineItemId>\n" +
                        "    <orderLineNumber>4</orderLineNumber>\n" +
                        "    <merchantLineNumber>4</merchantLineNumber>\n" +
                        "    <qtyOrdered>7</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990904</UPC>\n" +
                        "    <description>Sample item description for line 4</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990904</merchantSKU>\n" +
                        "    <vendorSKU>0670904</vendorSKU>\n" +
                        "    <unitPrice>6.9900</unitPrice>\n" +
                        "    <unitCost>3.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865053\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865054\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865055\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865056\">\n" +
                        "      <boxNumber>4</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865057\">\n" +
                        "      <boxNumber>5</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865058\">\n" +
                        "      <boxNumber>6</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865059\">\n" +
                        "      <boxNumber>7</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "      <boxNumber4>4</boxNumber4>\n" +
                        "      <boxNumber5>5</boxNumber5>\n" +
                        "      <boxNumber6>6</boxNumber6>\n" +
                        "      <boxNumber7>7</boxNumber7>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993835</lineItemId>\n" +
                        "    <orderLineNumber>5</orderLineNumber>\n" +
                        "    <merchantLineNumber>5</merchantLineNumber>\n" +
                        "    <qtyOrdered>6</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990905</UPC>\n" +
                        "    <description>Sample item description for line 5</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990905</merchantSKU>\n" +
                        "    <vendorSKU>0670905</vendorSKU>\n" +
                        "    <unitPrice>7.9900</unitPrice>\n" +
                        "    <unitCost>4.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865060\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865061\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865062\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865063\">\n" +
                        "      <boxNumber>4</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865064\">\n" +
                        "      <boxNumber>5</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865065\">\n" +
                        "      <boxNumber>6</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "      <boxNumber4>4</boxNumber4>\n" +
                        "      <boxNumber5>5</boxNumber5>\n" +
                        "      <boxNumber6>6</boxNumber6>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993836</lineItemId>\n" +
                        "    <orderLineNumber>6</orderLineNumber>\n" +
                        "    <merchantLineNumber>6</merchantLineNumber>\n" +
                        "    <qtyOrdered>5</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990906</UPC>\n" +
                        "    <description>Sample item description for line 6</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990906</merchantSKU>\n" +
                        "    <vendorSKU>0670906</vendorSKU>\n" +
                        "    <unitPrice>8.9900</unitPrice>\n" +
                        "    <unitCost>4.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865066\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865067\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865068\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865069\">\n" +
                        "      <boxNumber>4</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865070\">\n" +
                        "      <boxNumber>5</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "      <boxNumber4>4</boxNumber4>\n" +
                        "      <boxNumber5>5</boxNumber5>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993837</lineItemId>\n" +
                        "    <orderLineNumber>7</orderLineNumber>\n" +
                        "    <merchantLineNumber>7</merchantLineNumber>\n" +
                        "    <qtyOrdered>4</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990907</UPC>\n" +
                        "    <description>Sample item description for line 7</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990907</merchantSKU>\n" +
                        "    <vendorSKU>0670907</vendorSKU>\n" +
                        "    <unitPrice>9.9900</unitPrice>\n" +
                        "    <unitCost>5.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865071\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865072\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865073\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865074\">\n" +
                        "      <boxNumber>4</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "      <boxNumber4>4</boxNumber4>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993838</lineItemId>\n" +
                        "    <orderLineNumber>8</orderLineNumber>\n" +
                        "    <merchantLineNumber>8</merchantLineNumber>\n" +
                        "    <qtyOrdered>3</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990908</UPC>\n" +
                        "    <description>Sample item description for line 8</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990908</merchantSKU>\n" +
                        "    <vendorSKU>0670908</vendorSKU>\n" +
                        "    <unitPrice>10.9900</unitPrice>\n" +
                        "    <unitCost>5.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865075\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865076\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865077\">\n" +
                        "      <boxNumber>3</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <prodColor>123</prodColor>\n" +
                        "      <prodSize>321</prodSize>\n" +
                        "      <prodCSdesc>Color and Size Description</prodCSdesc>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "      <boxNumber3>3</boxNumber3>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993839</lineItemId>\n" +
                        "    <orderLineNumber>9</orderLineNumber>\n" +
                        "    <merchantLineNumber>9</merchantLineNumber>\n" +
                        "    <qtyOrdered>2</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990909</UPC>\n" +
                        "    <description>Sample item description for line 9</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990909</merchantSKU>\n" +
                        "    <vendorSKU>0670909</vendorSKU>\n" +
                        "    <unitPrice>11.9900</unitPrice>\n" +
                        "    <unitCost>6.0000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865078\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865079\">\n" +
                        "      <boxNumber>2</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <prodColor>123</prodColor>\n" +
                        "      <prodSize>321</prodSize>\n" +
                        "      <prodCSdesc>Color and Size Description</prodCSdesc>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "      <boxNumber2>2</boxNumber2>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <lineItem>\n" +
                        "    <lineItemId>120993840</lineItemId>\n" +
                        "    <orderLineNumber>10</orderLineNumber>\n" +
                        "    <merchantLineNumber>10</merchantLineNumber>\n" +
                        "    <qtyOrdered>1</qtyOrdered>\n" +
                        "    <unitOfMeasure>EA</unitOfMeasure>\n" +
                        "    <UPC>9999999990910</UPC>\n" +
                        "    <description>Sample item description for line 10</description>\n" +
                        "    <merchantProductId>E123456</merchantProductId>\n" +
                        "    <merchantSKU>9990910</merchantSKU>\n" +
                        "    <vendorSKU>0670910</vendorSKU>\n" +
                        "    <unitPrice>12.9900</unitPrice>\n" +
                        "    <unitCost>6.5000</unitCost>\n" +
                        "    <shippingCode>UPSN_CG</shippingCode>\n" +
                        "    <expectedShipDate>20190619</expectedShipDate>\n" +
                        "    <requestedArrivalDate>20190619</requestedArrivalDate>\n" +
                        "    <shippingLabel ShippingLabelId=\"SL102865080\">\n" +
                        "      <boxNumber>1</boxNumber>\n" +
                        "      <packageQty>1</packageQty>\n" +
                        "      <returnRelatedMessage>To return this item use the small RETURN YOUR WAY LABEL or visit QVC.com Order Status, select your order to print a Q return label.</returnRelatedMessage>\n" +
                        "    </shippingLabel>\n" +
                        "    <poLineData>\n" +
                        "      <lineReqShipDate>20190619</lineReqShipDate>\n" +
                        "      <lineReqDelvDate>20190619</lineReqDelvDate>\n" +
                        "      <boxNumber1>1</boxNumber1>\n" +
                        "    </poLineData>\n" +
                        "  </lineItem>\n" +
                        "  <personPlace personPlaceID=\"PP3007782080\">\n" +
                        "    <name1>Paula Jones</name1>\n" +
                        "    <address1>Apt 9</address1>\n" +
                        "    <address2>9 Main St.</address2>\n" +
                        "    <city>Moab</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>84532-1234</postalCode>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007782079\">\n" +
                        "    <name1>Paula Jones</name1>\n" +
                        "    <address1>Apt 9</address1>\n" +
                        "    <address2>9 Main St.</address2>\n" +
                        "    <city>Moab</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>84532-1234</postalCode>\n" +
                        "    <dayPhone>555-555-5555</dayPhone>\n" +
                        "  </personPlace>\n" +
                        "  <personPlace personPlaceID=\"PP3007782078\">\n" +
                        "    <name1>Mike Jones</name1>\n" +
                        "    <address1>Suite 9 - High Towers</address1>\n" +
                        "    <address2>9 Wakara Way</address2>\n" +
                        "    <city>Salt Lake City</city>\n" +
                        "    <state>UT</state>\n" +
                        "    <country>US</country>\n" +
                        "    <postalCode>84108-1234</postalCode>\n" +
                        "  </personPlace>\n" +
                        "  <vendorShipInfo vendorShipID=\"VS54127388_120883810\">\n" +
                        "    <name1>RETURN PROCESSING CENTER (QVC)</name1>\n" +
                        "    <address1>100 QVC BOULEVARD</address1>\n" +
                        "    <city>ROCKY MOUNT</city>\n" +
                        "    <state>NC</state>\n" +
                        "    <country>USA</country>\n" +
                        "    <postalCode>27801</postalCode>\n" +
                        "  </vendorShipInfo>\n" +
                        "</hubOrder>";

                JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
                api.configure("justbrand.sftp-test.commercehub.com", "justbrand", "Suddenly3Repair#Survive5","qvc","Just Brand Limited","qvc",626);
                // api.setGroupName("qvc");
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



                }catch (Exception e){
                        fail();
                }








        }
}

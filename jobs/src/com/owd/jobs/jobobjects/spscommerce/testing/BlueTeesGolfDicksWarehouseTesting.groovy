package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.BlueTeesGolfDicksWarehouseEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class BlueTeesGolfDicksWarehouseTesting extends GroovyTestCase {

    @Test
    void testviewASN() {
        println("hello");
        System.setProperty("com.owd.environment", "test");
        BlueTeesGolfDicksWarehouseEDI edi = new BlueTeesGolfDicksWarehouseEDI();
        String asnData = edi.generateASN(15740312,626);


        println asnData;
    }

    @Test
    void testLoadPendingASns() {
        System.setProperty("com.owd.environment", "test");
        SPSCommerceBaseClient.processPendingPos();

    }


    @Test
    void test1shipOrderFromPoDicks() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <DocumentControlIdentifier>850</DocumentControlIdentifier>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>501FWDBLUETEESG</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>946023930</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2021-01-22</PurchaseOrderDate>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Department>158</Department>\n" +
                "            <Vendor>78369</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDiscountPercentage>2</TermsDiscountPercentage>\n" +
                "            <TermsDiscountDueDays>20</TermsDiscountDueDays>\n" +
                "            <TermsNetDueDays>60</TermsNetDueDays>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                "            <Date1>2021-01-29</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2021-01-22</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>TARA STOLARSKI</ContactName>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>851</AddressLocationNumber>\n" +
                "            <AddressName>Dicks Sporting Goods (851)</AddressName>\n" +
                "            <Address1>4651 North Cotton Ln</Address1>\n" +
                "            <City>Goodyear</City>\n" +
                "            <State>AZ</State>\n" +
                "            <PostalCode>85395</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>19</ReferenceQual>\n" +
                "            <Description>GOLF ELECTRONICS        G</Description>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceID>DSG</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AH</ReferenceQual>\n" +
                "            <ReferenceID>1</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Notes>\n" +
                "            <NoteInformationField>PLEASE SPLIT TO MFCS</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>21786722</BuyerPartNumber>\n" +
                "                <VendorPartNumber>SERIES 2 PRO</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>728488906231</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>SERIES2PRO</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>30</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>109.99</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>NO SIZE</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Navy/White</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>199.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Series2Pro/NY/WH/NSIZE/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>30</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>851</Location>\n" +
                "                    <Qty>30</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            BlueTeesGolfDicksWarehouseEDI edi = new BlueTeesGolfDicksWarehouseEDI();

            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po = parser.parseText(poData)
            String account = po.Header.OrderHeader.TradingPartnerId.text()
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfileamazon.xml", account)

            EdiSpsConfigdata config = edi.getEdiConfigData(po)
            Order order = edi.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");

            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if (null != config.vendorComplianceFkey) {
                order.addTag(TagUtilities.kVendorComplianceIDReference, config.getVendorComplianceFkey() + "");

                order.addTag(TagUtilities.kEDIDicksASIDN, "ASIDN-TEST12345"); // Approved Shipment ID Number

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment = false
            order.bill_cc_type = "CK";
            // order.setShippingMethodName("LTL");
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            /*   order.facilityCode='DC1'
               order.facilityPolicy='DC1'
               order.setThirdPartyBilling("9V3W92");
               order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
   */

            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
            if (order.shipMethodName.contains("FedEx")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000", true);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000", true);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211", true);
            }

            //we have a shipped order, now create the ASN file for it


            String asnData = edi.generateASN(Integer.parseInt(order.orderID), config.clientFkey)

            println asnData
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }

    @Test
    public void testASNGeneration() {



        //SPSCommerceBaseClient.submitASN(16854092, 626);
        List<Integer> l = new ArrayList<>();
      /*  l.add(16854092);
        l.add(16854093);
        l.add(16854094);*/
      /*  l.add(16854096);
        l.add(16854095);
        l.add(16854097);
        l.add(16854098);
        l.add(16854102);
        l.add(16854099);
        l.add(16854104);
        l.add(16854112);
        l.add(16854100);
        l.add(16854101);
        l.add(16856248);
        l.add(16856249);
        l.add(16856245);
        l.add(16856247);
        l.add(16856251);
        l.add(16860307);
        l.add(16856246);
        l.add(16856252);
        l.add(16856250);

*/
        for(Integer i:l){
            SPSCommerceBaseClient.submitASN(i, 626);
        }



    }


}

package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsRetailConceptsEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class JustBrandsRetailConceptsTesting extends GroovyTestCase {

    @Test
    void testviewASN() {
        println("hello");
        System.setProperty("com.owd.environment", "test");
    /*    JustBrandsDicksWarehouseEDI edi = new JustBrandsDicksWarehouseEDI();
        String asnData = edi.generateASN(15740312,626);*/


        println asnData;
    }

    @Test
    void testLoadPendingASns() {
        System.setProperty("com.owd.environment", "test");
       // SPSCommerceBaseClient.processPendingPos();

    }


    @Test
    void test1shipOrderFromPoCamping() {
        System.setProperty("com.owd.environment", "test");

        String poData =
                "<Order>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000000045</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>45</GroupControlNumber>\n" +
                "        <DocumentControlNumber>000000022</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>SUNANDSKI</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>88840619YK</InterchangeReceiverID>\n" +
                "        <GroupSenderID>SUNANDSKI</GroupSenderID>\n" +
                "        <GroupReceiverID>88840619YK</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>5P1ALLJUSTBRAND</TradingPartnerId>0\n" +
                "            <PurchaseOrderNumber>0000120641</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2019-02-27</PurchaseOrderDate>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>14</TermsType>\n" +
                "            <TermsBasisDateCode>15</TermsBasisDateCode>\n" +
                "            <TermsDescription>NET 60 DAYS</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2019-09-01</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                "            <Date1>2019-09-30</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>00001</AddressLocationNumber>\n" +
                "            <AddressName>DISTRIBUTION CENTER</AddressName>\n" +
                "            <Address1>10560 BISSONNET STREET</Address1>\n" +
                "            <Address2>SUITE 100</Address2>\n" +
                "            <City>HOUSTON</City>\n" +
                "            <State>TX</State>\n" +
                "            <PostalCode>77099</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>RETAIL CONCEPTS, INC.</AddressName>\n" +
                "            <Address1>10560 BISSONNET STRE</Address1>\n" +
                "            <Address2>SUITE 100</Address2>\n" +
                "            <City>HOUSTON</City>\n" +
                "            <State>TX</State>\n" +
                "            <PostalCode>77099</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationQualifier>DE</FOBLocationQualifier>\n" +
                "            <FOBLocationDescription>HOUSTON, TX</FOBLocationDescription>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>SEE ROUTING GUIDE</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>VN</ReferenceQual>\n" +
                "            <ReferenceID>02565</ReferenceID>\n" +
                "            <Description>JUST BRAND LIMITED</Description>\n" +
                "        </Reference>\n" +
                "        <Notes>\n" +
                "            <NoteInformationField>PACK BY STORE</NoteInformationField>\n" +
                "        </Notes>\n" +
                "        <Notes>\n" +
                "            <NoteInformationField>PRICES NET PO</NoteInformationField>\n" +
                "        </Notes>\n" +
                "        <Notes>\n" +
                "            <NoteInformationField>FOR RCI TERMS, CONDITIONS, COMPLIANCE, AND ROUTING INSTRUCTIONS REFER TO RCI GUIDE FOUND AT WWW.RETAILCONCEPTS.CC.</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1225-02565-1800-001-9003</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SG-5V-1-BM-S</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595808319</ConsumerPackageCode>\n" +
                "                <OrderQty>30</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>SMALL</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ALPHAHEAT 5V HEATED PREMI</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>30</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1225-02565-1800-001-9004</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SG-5V-1-BM-M</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595808326</ConsumerPackageCode>\n" +
                "                <OrderQty>52</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>MEDIUM</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ALPHAHEAT 5V HEATED PREMI</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>52</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1225-02565-1800-001-9007</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SG-5V-1-BM-L</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595808333</ConsumerPackageCode>\n" +
                "                <OrderQty>48</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>LARGE</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ALPHAHEAT 5V HEATED PREMI</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>48</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1225-02565-1800-001-9008</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SG-5V-1-BM-X</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595808340</ConsumerPackageCode>\n" +
                "                <OrderQty>29</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>X-LARGE</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ALPHAHEAT 5V HEATED PREMI</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>29</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>5</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1245-02565-2000-001-9003</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SG-5V-1-BW-S</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595808272</ConsumerPackageCode>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>SMALL</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>W ALPHAHEAT 5V HEATD PREM</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>10</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>6</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1245-02565-2000-001-9004</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SG-5V-1-BW-M</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595808289</ConsumerPackageCode>\n" +
                "                <OrderQty>15</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>MEDIUM</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>W ALPHAHEAT 5V HEATD PREM</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>15</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>7</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1245-02565-2000-001-9007</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SG-5V-1-BW-L</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595808296</ConsumerPackageCode>\n" +
                "                <OrderQty>9</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>LARGE</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>W ALPHAHEAT 5V HEATD PREM</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>9</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>8</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>0076-02565-1800-001-9014</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-5V-W-S-M</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807978</ConsumerPackageCode>\n" +
                "                <OrderQty>32</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>SMALL/MED</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ACTIONHEAT WOOL 5V HEATED</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>32</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>9</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>0076-02565-1800-001-9016</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-5V-W-L-XL</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807985</ConsumerPackageCode>\n" +
                "                <OrderQty>26</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <ProductSizeCode>LRG/XLARGE</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ACTIONHEAT WOOL 5V HEATED</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>26</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>10</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>0076-02565-1805-001-9014</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-AA-03-S-M</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807442</ConsumerPackageCode>\n" +
                "                <OrderQty>100</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>23.75</PurchasePrice>\n" +
                "                <ProductSizeCode>SMALL/MED</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ACTIONHEAT WOOL AA BATTER</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>100</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>11</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>0076-02565-1805-001-9016</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-AA-03-L-X</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807459</ConsumerPackageCode>\n" +
                "                <OrderQty>184</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>23.75</PurchasePrice>\n" +
                "                <ProductSizeCode>LRG/XLARGE</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ACTIONHEAT WOOL AA BATTER</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>00001</Location>\n" +
                "                    <Qty>184</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>11</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n" ;


        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))
        try {
            JustBrandsRetailConceptsEDI edi = new JustBrandsRetailConceptsEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfilecamping.xml", account)

            EdiSpsConfigdata config = edi.getEdiConfigData(po)
            Order order = edi.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");

            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if (null != config.vendorComplianceFkey) {
                order.addTag(TagUtilities.kVendorComplianceIDReference, config.getVendorComplianceFkey() + "");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment = false
            order.bill_cc_type = "CK";
            order.setShippingMethodName("FedEx Ground");
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
    void test2shipOrderFromPoCamping() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>089ALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>220000003</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>KN</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-09-11</PurchaseOrderDate>\n" +
                "            <SalesRequirementCode>BK</SalesRequirementCode>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Vendor>118620</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Net 30</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "            <Date1>2020-09-18</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>Contact Name</ContactName>\n" +
                "            <PrimaryEmail>contact@email.com</PrimaryEmail>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>CAMPING WORLD, INC.</AddressName>\n" +
                "            <Address1>ATTENTION: ACCOUNTS PAYABLE</Address1>\n" +
                "            <Address2>P.O. BOX 90018</Address2>\n" +
                "            <City>BOWLING GREEN</City>\n" +
                "            <State>KY</State>\n" +
                "            <PostalCode>42102-9018</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>RE</ContactTypeCode>\n" +
                "                <PrimaryPhone>2345678901</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>99999</AddressLocationNumber>\n" +
                "            <AddressName>CAMPING WORLD WC</AddressName>\n" +
                "            <Address1>3830 SACO RD.</Address1>\n" +
                "            <City>BAKERSFIELD</City>\n" +
                "            <State>CA</State>\n" +
                "            <PostalCode>93308</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>JUSTBRAND LTD 118620</AddressName>\n" +
                "            <Address1>3791 Main Street</Address1>\n" +
                "            <City>Philadelphia</City>\n" +
                "            <State>PA</State>\n" +
                "            <PostalCode>19127</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Notes>\n" +
                "            <NoteCode>GEN</NoteCode>\n" +
                "            <NoteInformationField>Order notes sent here.</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277330 BLCK SM</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN111</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579178</ConsumerPackageCode>\n" +
                "                <OrderQty>216</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>15.29</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 AA HEATED SOCKS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>614</Location>\n" +
                "                    <Qty>108</Qty>\n" +
                "                </LocationQuantity>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>639</Location>\n" +
                "                    <Qty>108</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277330 BLCK LXL</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN222</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579161</ConsumerPackageCode>\n" +
                "                <OrderQty>288</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>7.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 AA HEATED SOCKS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>614</Location>\n" +
                "                    <Qty>144</Qty>\n" +
                "                </LocationQuantity>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>639</Location>\n" +
                "                    <Qty>144</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277332 BLCK M</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN333</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579109</ConsumerPackageCode>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>8.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 M HEATED GLOVE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>614</Location>\n" +
                "                    <Qty>5</Qty>\n" +
                "                </LocationQuantity>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>639</Location>\n" +
                "                    <Qty>5</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277332 BLCK L</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN444</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579093</ConsumerPackageCode>\n" +
                "                <OrderQty>20</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>11.49</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 M HEATED GLOVE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>614</Location>\n" +
                "                    <Qty>10</Qty>\n" +
                "                </LocationQuantity>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>639</Location>\n" +
                "                    <Qty>10</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalAmount>14850</TotalAmount>\n" +
                "        <TotalLineItemNumber>4</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsRetailConceptsEDI edi = new JustBrandsRetailConceptsEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfilecamping.xml", account)

            EdiSpsConfigdata config = edi.getEdiConfigData(po)
            Order order = edi.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");

            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if (null != config.vendorComplianceFkey) {
                order.addTag(TagUtilities.kVendorComplianceIDReference, config.getVendorComplianceFkey() + "");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment = false
            order.bill_cc_type = "CK";
            order.setShippingMethodName("FedEx Ground");
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
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "000000000000000", true);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), true,4, 0.00, "1Z0000000000000000", true);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), true,4, 0.00, "1234567891211", true);
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
    void test3shipOrderFromPoCamping() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>089ALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>110000002</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-09-11</PurchaseOrderDate>\n" +
                "            <SalesRequirementCode>BK</SalesRequirementCode>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Vendor>118620</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Net 30</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "            <Date1>2020-09-18</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>Contact Name</ContactName>\n" +
                "            <PrimaryEmail>contact@email.com</PrimaryEmail>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>CAMPING WORLD, INC.</AddressName>\n" +
                "            <Address1>ATTENTION: ACCOUNTS PAYABLE</Address1>\n" +
                "            <Address2>P.O. BOX 90018</Address2>\n" +
                "            <City>BOWLING GREEN</City>\n" +
                "            <State>KY</State>\n" +
                "            <PostalCode>42102-9018</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>RE</ContactTypeCode>\n" +
                "                <PrimaryPhone>2345678901</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>020</AddressLocationNumber>\n" +
                "            <AddressName>CAMPING WORLD WC</AddressName>\n" +
                "            <Address1>3830 SACO RD.</Address1>\n" +
                "            <City>BAKERSFIELD</City>\n" +
                "            <State>CA</State>\n" +
                "            <PostalCode>93308</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>JUSTBRAND LTD 118620</AddressName>\n" +
                "            <Address1>3791 Main Street</Address1>\n" +
                "            <City>Philadelphia</City>\n" +
                "            <State>PA</State>\n" +
                "            <PostalCode>19127</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Notes>\n" +
                "            <NoteCode>GEN</NoteCode>\n" +
                "            <NoteInformationField>Order notes sent here.</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277330 BLCK SM</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN111</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579178</ConsumerPackageCode>\n" +
                "                <OrderQty>108</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>15.29</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 AA HEATED SOCKS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277330 BLCK LXL</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN222</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579161</ConsumerPackageCode>\n" +
                "                <OrderQty>144</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>7.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 AA HEATED SOCKS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277332 BLCK M</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN333</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579109</ConsumerPackageCode>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>8.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 M HEATED GLOVE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277332 BLCK L</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN444</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579093</ConsumerPackageCode>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>11.49</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 M HEATED GLOVE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalAmount>7425</TotalAmount>\n" +
                "        <TotalLineItemNumber>4</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsRetailConceptsEDI edi = new JustBrandsRetailConceptsEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfilecamping.xml", account)

            EdiSpsConfigdata config = edi.getEdiConfigData(po)
            Order order = edi.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");

            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if (null != config.vendorComplianceFkey) {
                order.addTag(TagUtilities.kVendorComplianceIDReference, config.getVendorComplianceFkey() + "");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment = false
            order.bill_cc_type = "CK";
            order.setShippingMethodName("FedEx Ground");
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
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "000000000000000", true);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), true,4, 0.00, "1Z0000000000000000", true);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), true,4, 0.00, "1234567891211", true);
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
    void test4shipOrderFromPoCamping() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>089ALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>110000003</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>KN</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-09-11</PurchaseOrderDate>\n" +
                "            <SalesRequirementCode>BK</SalesRequirementCode>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Vendor>118620</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Net 30</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "            <Date1>2020-09-18</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>Contact Name</ContactName>\n" +
                "            <PrimaryEmail>contact@email.com</PrimaryEmail>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>CAMPING WORLD, INC.</AddressName>\n" +
                "            <Address1>ATTENTION: ACCOUNTS PAYABLE</Address1>\n" +
                "            <Address2>P.O. BOX 90018</Address2>\n" +
                "            <City>BOWLING GREEN</City>\n" +
                "            <State>KY</State>\n" +
                "            <PostalCode>42102-9018</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>RE</ContactTypeCode>\n" +
                "                <PrimaryPhone>2345678901</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>99999</AddressLocationNumber>\n" +
                "            <AddressName>CAMPING WORLD WC</AddressName>\n" +
                "            <Address1>3830 SACO RD.</Address1>\n" +
                "            <City>BAKERSFIELD</City>\n" +
                "            <State>CA</State>\n" +
                "            <PostalCode>93308</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>JUSTBRAND LTD 118620</AddressName>\n" +
                "            <Address1>3791 Main Street</Address1>\n" +
                "            <City>Philadelphia</City>\n" +
                "            <State>PA</State>\n" +
                "            <PostalCode>19127</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Notes>\n" +
                "            <NoteCode>GEN</NoteCode>\n" +
                "            <NoteInformationField>Order notes sent here.</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277330 BLCK SM</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN111</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579178</ConsumerPackageCode>\n" +
                "                <OrderQty>216</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>15.29</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 AA HEATED SOCKS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>020</Location>\n" +
                "                    <Qty>108</Qty>\n" +
                "                </LocationQuantity>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>021</Location>\n" +
                "                    <Qty>108</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277330 BLCK LXL</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN222</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579161</ConsumerPackageCode>\n" +
                "                <OrderQty>288</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>7.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 AA HEATED SOCKS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>020</Location>\n" +
                "                    <Qty>144</Qty>\n" +
                "                </LocationQuantity>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>021</Location>\n" +
                "                    <Qty>144</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277332 BLCK M</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN333</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579109</ConsumerPackageCode>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>8.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 M HEATED GLOVE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>020</Location>\n" +
                "                    <Qty>5</Qty>\n" +
                "                </LocationQuantity>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>021</Location>\n" +
                "                    <Qty>5</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277332 BLCK L</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN444</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579093</ConsumerPackageCode>\n" +
                "                <OrderQty>20</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>11.49</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 M HEATED GLOVE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>020</Location>\n" +
                "                    <Qty>10</Qty>\n" +
                "                </LocationQuantity>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>021</Location>\n" +
                "                    <Qty>10</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalAmount>14850</TotalAmount>\n" +
                "        <TotalLineItemNumber>4</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsRetailConceptsEDI edi = new JustBrandsRetailConceptsEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfilecamping.xml", account)

            EdiSpsConfigdata config = edi.getEdiConfigData(po)
            Order order = edi.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");

            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if (null != config.vendorComplianceFkey) {
                order.addTag(TagUtilities.kVendorComplianceIDReference, config.getVendorComplianceFkey() + "");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment = false
            order.bill_cc_type = "CK";
            order.setShippingMethodName("FedEx Ground");
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
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "000000000000000", true);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), true,4, 0.00, "1Z0000000000000000", true);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), true,4, 0.00, "1234567891211", true);
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
    void testDropShipFlag() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>089ALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>110000001</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-09-11</PurchaseOrderDate>\n" +
                "            <SalesRequirementCode>BK</SalesRequirementCode>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Vendor>118620</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Net 30</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "            <Date1>2020-09-18</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>Contact Name</ContactName>\n" +
                "            <PrimaryEmail>contact@email.com</PrimaryEmail>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>CAMPING WORLD, INC.</AddressName>\n" +
                "            <Address1>ATTENTION: ACCOUNTS PAYABLE</Address1>\n" +
                "            <Address2>P.O. BOX 90018</Address2>\n" +
                "            <City>BOWLING GREEN</City>\n" +
                "            <State>KY</State>\n" +
                "            <PostalCode>42102-9018</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>RE</ContactTypeCode>\n" +
                "                <PrimaryPhone>2345678901</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>99999</AddressLocationNumber>\n" +
                "            <AddressName>CAMPING WORLD WC</AddressName>\n" +
                "            <Address1>3830 SACO RD.</Address1>\n" +
                "            <City>BAKERSFIELD</City>\n" +
                "            <State>CA</State>\n" +
                "            <PostalCode>93308</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>JUSTBRAND LTD 118620</AddressName>\n" +
                "            <Address1>3791 Main Street</Address1>\n" +
                "            <City>Philadelphia</City>\n" +
                "            <State>PA</State>\n" +
                "            <PostalCode>19127</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Notes>\n" +
                "            <NoteCode>GEN</NoteCode>\n" +
                "            <NoteInformationField>Order notes sent here.</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277330 BLCK SM</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN111</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579178</ConsumerPackageCode>\n" +
                "                <OrderQty>108</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>15.29</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 AA HEATED SOCKS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277330 BLCK LXL</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN222</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579161</ConsumerPackageCode>\n" +
                "                <OrderQty>144</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>7.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 AA HEATED SOCKS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277332 BLCK M</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN333</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579109</ConsumerPackageCode>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>8.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 M HEATED GLOVE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>277332 BLCK L</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VPN444</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>783515579093</ConsumerPackageCode>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-09-18</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>10</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>11.49</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>T360 M HEATED GLOVE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalAmount>7425</TotalAmount>\n" +
                "        <TotalLineItemNumber>4</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsRetailConceptsEDI edi = new JustBrandsRetailConceptsEDI();

            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po = parser.parseText(poData)
            edi.setDropShipFlag(po);
            assertTrue(edi.isDropShipOrder());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}

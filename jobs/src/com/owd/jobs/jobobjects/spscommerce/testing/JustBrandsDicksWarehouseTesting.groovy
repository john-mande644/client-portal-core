package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsDicksEDI
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsDicksWarehouseEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class JustBrandsDicksWarehouseTesting extends GroovyTestCase {

    @Test
    void testviewASN() {
        println("hello");
        System.setProperty("com.owd.environment", "test");
        JustBrandsDicksWarehouseEDI edi = new JustBrandsDicksWarehouseEDI();
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
                "        <InterchangeControlNumber>000000008</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>000000008</GroupControlNumber>\n" +
                "        <DocumentControlIdentifier>850</DocumentControlIdentifier>\n" +
                "        <DocumentControlNumber>0021</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>157322272</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>41476389YK</InterchangeReceiverID>\n" +
                "        <GroupSenderID>157322272</GroupSenderID>\n" +
                "        <GroupReceiverID>41476389YK</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>501ALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>TEST_979000093</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2018-12-04</PurchaseOrderDate>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Department>312</Department>\n" +
                "            <DepartmentDescription>GENACCESS-FOOTWEAR      T</DepartmentDescription>\n" +
                "            <Vendor>57811</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsNetDueDays>60</TermsNetDueDays>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                "            <Date1>2018-12-14</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2018-12-05</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>JULIE SMITH</ContactName>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>351</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>8M</ReferenceQual>\n" +
                "            <ReferenceID>FS</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AH</ReferenceQual>\n" +
                "            <ReferenceID>1</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Notes>\n" +
                "            <NoteInformationField>ACTIONHEAT BATTERY HEATED SOCK</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>17385721</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BLK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807381</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-SK-LI-WOOL</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>13</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>65</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>S/M</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>129.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>ACTIONHEAT/BLACK/S/M/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>12</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>13</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>17385722</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BLK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807398</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-SK-LI-WOOL</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>13</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>65</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>L/XL</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>129.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>ACTIONHEAT/BLACK/L/XL/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>12</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>13</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>17385728</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BLK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807442</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-SK-AA-WOOL</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>18</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>S/M</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>49.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>WOOLAABATT/BLACK/S/M/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>12</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>18</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>17385729</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BLK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807459</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-SK-AA-WOOL</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>18</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>L/XL</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>49.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>WOOLAABATT/BLACK/L/XL/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>12</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>18</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>5</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>17385730</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BLK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807466</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-SK-AA-WOOL</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>6</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>XXL</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>49.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>WOOLAABATT/BLACK/XXL/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>12</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>6</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>6</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>18207349</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-INS-LI-01</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807572</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-INS-LI-01</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>65</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>S/M</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>99.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>3.7VOLTREC/BLACK/S/M/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>3</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>7</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>18207350</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-INS-LI-01</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807589</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-INS-LI-01</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>65</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>L/XL</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>99.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>3.7VOLTREC/BLACK/L/XL/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>5</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>8</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>18207345</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-5V-WOOL</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807978</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-SK-5V-WOOL</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>100</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>S/M</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>149.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>5VOLTHEATE/BLACK/S/M/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>12</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>3</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>9</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>18207346</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-5V-WOOL</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807985</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>AH-SK-5V-WOOL</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>100</PurchasePrice>\n" +
                "                <PurchasePriceBasis>QT</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>L/XL</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>149.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>5VOLTHEATE/BLACK/L/XL/NDIMN</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>12</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>5524</Location>\n" +
                "                    <Qty>2</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>9</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsDicksWarehouseEDI edi = new JustBrandsDicksWarehouseEDI();

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

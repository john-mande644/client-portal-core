package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.clients.BlueTeesGolfAcademySportRadialStockingEDI
import org.junit.Test

class BlueTeesGolfAcademySportRadialStockingTesting extends GroovyTestCase {





    @Test
    void test1AcadaySportRadial() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>502FWDBLUETEES1</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>9229292TEST</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2023-02-06</PurchaseOrderDate>\n" +
                "            <Department>3</Department>\n" +
                "            <Vendor>49354</Vendor>\n" +
                "            <Division>2</Division>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>NET 90 DAYS</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2023-02-22</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "            <Date1>2023-02-24</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>90</ContactName>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>895</AddressLocationNumber>\n" +
                "            <AddressName>ACADEMY LTD</AddressName>\n" +
                "            <Address1>1549 Primewest Pkwy</Address1>\n" +
                "            <City>Katy</City>\n" +
                "            <State>TX</State>\n" +
                "            <PostalCode>77449</PostalCode>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>CT</AddressTypeCode>\n" +
                "            <AddressName>COUNTRY OF ORIGIN</AddressName>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                "            <FOBLocationDescription>USILN, WILMINGTON, OH</FOBLocationDescription>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>CB</ReferenceQual>\n" +
                "            <ReferenceID>N</ReferenceID>\n" +
                "            <Description>Single location</Description>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>L1</ReferenceQual>\n" +
                "            <ReferenceID>GEN</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>L1</ReferenceQual>\n" +
                "            <ReferenceID>GEN</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>131917978</BuyerPartNumber>\n" +
                "                <VendorPartNumber>RF-G-S3M-BK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>00728488906316</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>ZZ</PartNumberQual>\n" +
                "                    <PartNumber>N</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>KL</PartNumberQual>\n" +
                "                    <PartNumber>FLAT</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>110</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>149.99</PurchasePrice>\n" +
                "                <ProductColorDescription>Black/Grey</ProductColorDescription>\n" +
                "                <ProductMaterialDescription>HAND: Ambidextrous</ProductMaterialDescription>\n" +
                "                <Class>1550</Class>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>BLUE TEES GOLF SERIES 3 MAX RANGEFINDER:BLACK GREY:AMBIDEXTROUS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "                <PackSize>1</PackSize>\n" +
                "                <PackUOM>UN</PackUOM>\n" +
                "            </PhysicalDetails>\n" +
                "            <Reference>\n" +
                "                <ReferenceQual>PG</ReferenceQual>\n" +
                "                <ReferenceID>1550</ReferenceID>\n" +
                "            </Reference>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>137111959</BuyerPartNumber>\n" +
                "                <VendorPartNumber>RF-G-S2PL-BK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>00850029812586</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>ZZ</PartNumberQual>\n" +
                "                    <PartNumber>N</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>KL</PartNumberQual>\n" +
                "                    <PartNumber>FLAT</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>138</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>139.99</PurchasePrice>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "                <Class>1550</Class>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>BLUE TEES SERIES 2 PRO PLUS RANGEFINDER:BLACK</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "                <PackSize>1</PackSize>\n" +
                "                <PackUOM>UN</PackUOM>\n" +
                "            </PhysicalDetails>\n" +
                "            <Reference>\n" +
                "                <ReferenceQual>PG</ReferenceQual>\n" +
                "                <ReferenceID>1550</ReferenceID>\n" +
                "            </Reference>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>138792590</BuyerPartNumber>\n" +
                "                <VendorPartNumber>HH-GPS-TR-BK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>00850029812708</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>ZZ</PartNumberQual>\n" +
                "                    <PartNumber>N</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>KL</PartNumberQual>\n" +
                "                    <PartNumber>FLAT</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>39</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>124.99</PurchasePrice>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "                <Class>1550</Class>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>BLUE TEES THE RINGER GPS:BLACK</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "                <PackSize>3</PackSize>\n" +
                "                <PackUOM>UN</PackUOM>\n" +
                "            </PhysicalDetails>\n" +
                "            <Reference>\n" +
                "                <ReferenceQual>PG</ReferenceQual>\n" +
                "                <ReferenceID>1550</ReferenceID>\n" +
                "            </Reference>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>138792653</BuyerPartNumber>\n" +
                "                <VendorPartNumber>SK-GPS-TP-BK</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>00850029812722</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>ZZ</PartNumberQual>\n" +
                "                    <PartNumber>N</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>KL</PartNumberQual>\n" +
                "                    <PartNumber>FLAT</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>105</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>124.99</PurchasePrice>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "                <Class>1550</Class>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>BLUE TEES THE PLAYER  GPS SPEAKER:BLACK</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "                <PackSize>3</PackSize>\n" +
                "                <PackUOM>UN</PackUOM>\n" +
                "            </PhysicalDetails>\n" +
                "            <Reference>\n" +
                "                <ReferenceQual>PG</ReferenceQual>\n" +
                "                <ReferenceID>1550</ReferenceID>\n" +
                "            </Reference>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>4</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            BlueTeesGolfAcademySportRadialStockingEDI edi = new BlueTeesGolfAcademySportRadialStockingEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfiletarget.xml", account)

            EdiSpsConfigdata config = edi.getEdiConfigData(po)
            Order order = edi.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");

            String method = po.LineItems.LineItem[0].CarrierInformation.CarrierRouting.text();
            if(null == method || method.length()==0){
                po.Header.CarrierInformation.CarrierRouting.text();
            }

            order.setShippingMethodName(edi.loadShippingMethod(order, method, po.Header.CarrierInformation))


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
//            //  println("This is after the print asn")
//            //   println "loading owd order";
//            //     OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
//            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
//            //    String ack = edi.generateACK(config,po,626,oorder)
//            //   println ack
//            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");
//
//            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
//            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
//            //   ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }
}

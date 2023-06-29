package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.BumbleRideMagicBeansEDI
import org.junit.Test

class BumbleRideMagicBeansTesting extends GroovyTestCase {
    @Test
    void test1shipOrderFromPoTarget() {
        System.setProperty("com.owd.environment", "test");

        String poData =
                "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>APOFWDBUMBLERID</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>56047</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2022-06-15</PurchaseOrderDate>\n" +
                "            <Vendor>294</Vendor>\n" +
                "            <CustomerOrderNumber>#514943 - 4443962179655</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDiscountPercentage>0</TermsDiscountPercentage>\n" +
                "            <TermsDiscountDueDays>0</TermsDiscountDueDays>\n" +
                "            <TermsNetDueDays>0</TermsNetDueDays>\n" +
                "            <TermsDescription>Manual</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>99</AddressLocationNumber>\n" +
                "            <AddressName>MAGIC BEANS</AddressName>\n" +
                "            <Address1>1 WESTINGHOUSE PLAZA</Address1>\n" +
                "            <Address2>SUITE K1</Address2>\n" +
                "            <City>HYDE PARK</City>\n" +
                "            <State>MA</State>\n" +
                "            <PostalCode>02136</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <PrimaryPhone>6172642326</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>BUMBLERIDE</AddressName>\n" +
                "            <Address1>2345 KETTNER BLVD</Address1>\n" +
                "            <Address2>#B</Address2>\n" +
                "            <City>SAN DIEGO</City>\n" +
                "            <State>CA</State>\n" +
                "            <PostalCode>92101</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <PrimaryPhone>6175276448</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>SO</AddressTypeCode>\n" +
                "            <AddressName>Howard Wright</AddressName>\n" +
                "            <Address1>607 Station Way</Address1>\n" +
                "            <City>Huntington Station</City>\n" +
                "            <State>NY</State>\n" +
                "            <PostalCode>11746</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>Howard Wright</ContactName>\n" +
                "                <PrimaryPhone>(631)7966349</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Howard Wright</AddressName>\n" +
                "            <Address1>607 Station Way</Address1>\n" +
                "            <City>Huntington Station</City>\n" +
                "            <State>NY</State>\n" +
                "            <PostalCode>11746</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>Howard Wright</ContactName>\n" +
                "                <PrimaryPhone>(631)7966349</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>364188</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>IT-980MB</BuyerPartNumber>\n" +
                "                <VendorPartNumber>IT-980MB</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>812812018780</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>500</PurchasePrice>\n" +
                "                <Department>STROLLERS</Department>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>DM INDIE TWIN 2020 MARITIME BLUE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalAmount>500</TotalAmount>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n";

        try {
            BumbleRideMagicBeansEDI edi = new BumbleRideMagicBeansEDI();

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
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000", false);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000", false);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211", false);
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
            //  ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)

        } catch (Exception ex) {

        }
    }
}

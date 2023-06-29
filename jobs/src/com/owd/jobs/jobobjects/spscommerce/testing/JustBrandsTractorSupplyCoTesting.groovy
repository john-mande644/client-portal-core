package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsTractorSupplyCoEDI
import org.junit.Test

class JustBrandsTractorSupplyCoTesting extends GroovyTestCase {
    @Test
    void test1shipOrderFromPo() {
        System.setProperty("com.owd.environment", "test")

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>DJNALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>9005008139</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2016-11-02</PurchaseOrderDate>\n" +
                "            <ShipCompleteCode>N</ShipCompleteCode>\n" +
                "            <Vendor>TestVN</Vendor>\n" +
                "            <CustomerOrderNumber>1010438037</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>05</TermsType>\n" +
                "            <TermsNetDueDays>60</TermsNetDueDays>\n" +
                "            <TermsDescription>NET 60</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                "            <Date1>2016-11-03</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>9999</AddressLocationNumber>\n" +
                "            <AddressName>SETH MARCH</AddressName>\n" +
                "            <Address1>5401 VIRGINIA WAY</Address1>\n" +
                "            <City>BRENTWOOD</City>\n" +
                "            <State>TN</State>\n" +
                "            <PostalCode>37027-7536</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>RE</ContactTypeCode>\n" +
                "                <ContactName>SETH MARCH</ContactName>\n" +
                "                <PrimaryPhone>615-440-4037</PrimaryPhone>\n" +
                "                <PrimaryEmail>CUSTOMERSERVICE@TRACTORSUPPLY.COM</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>Z7</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>9999</AddressLocationNumber>\n" +
                "            <AddressName>SETH MARCH</AddressName>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>5509</AddressLocationNumber>\n" +
                "            <AddressName>TSC</AddressName>\n" +
                "            <Address1>PO BOX 7000</Address1>\n" +
                "            <City>BRENTWOOD</City>\n" +
                "            <State>TN</State>\n" +
                "            <PostalCode>37027</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>TP</FOBPayCode>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                "            <CarrierAlphaCode>UPSN</CarrierAlphaCode>\n" +
                "            <RoutingSequenceCode>O</RoutingSequenceCode>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>SC</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>10</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1214659</BuyerPartNumber>\n" +
                "                <VendorPartNumber>ORCCR/WH026</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>814481020002</ConsumerPackageCode>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>160.55</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>269.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>DSV - TEST Vendor- 1214659 - DO NOT USE THIS SKU</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n"

        try {
            JustBrandsTractorSupplyCoEDI edi = new JustBrandsTractorSupplyCoEDI()

            println poData
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po = parser.parseText(poData)
            String account = po.Header.OrderHeader.TradingPartnerId.text()
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfilemodells.xml", account)

            EdiSpsConfigdata config = edi.getEdiConfigData(po)
            Order order = edi.importPo(po, config.clientFkey)

            println("Loaded the order xxxxxx")

            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData)
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName())
            if (null != config.vendorComplianceFkey) {
                order.addTag(TagUtilities.kVendorComplianceIDReference, config.getVendorComplianceFkey() + "")

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:"
            order.forcePayment = false
            order.bill_cc_type = "CK"
            order.setShippingMethodName("UPS Ground")
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged

            edi.loadThirdPartyBillingInfo(order);

            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false)
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
            if (order.shipMethodName.contains("FedEx")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000", true)
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211", true)
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
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net")

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }
}

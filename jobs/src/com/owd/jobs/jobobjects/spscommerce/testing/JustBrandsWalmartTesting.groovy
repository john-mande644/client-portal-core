package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderFactory
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsDicksEDI
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsWalmartEDI
import org.junit.Test


class JustBrandsWalmartTesting extends GroovyTestCase {

    @Test
    void testAsn1() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>529ALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>7423161384</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2021-04-21</PurchaseOrderDate>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Department>00009</Department>\n" +
                "            <Vendor>068904090</Vendor>\n" +
                "            <PromotionDealNumber>SEASBUWK15</PromotionDealNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>08</TermsType>\n" +
                "            <TermsBasisDateCode>15</TermsBasisDateCode>\n" +
                "            <TermsDiscountPercentage>1</TermsDiscountPercentage>\n" +
                "            <TermsDiscountDueDays>90</TermsDiscountDueDays>\n" +
                "            <TermsNetDueDays>120</TermsNetDueDays>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "            <Date1>2021-04-25</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2021-04-25</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>063</DateTimeQualifier1>\n" +
                "            <Date1>2021-04-29</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>UL</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0078742035239</AddressLocationNumber>\n" +
                "            <AddressName>WAL-MART DC 6094G-GENERAL</AddressName>\n" +
                "            <AddressAlternateName>6094</AddressAlternateName>\n" +
                "            <Address1>5801 SW REGIONAL AIRPORT BLVD</Address1>\n" +
                "            <City>BENTONVILLE</City>\n" +
                "            <State>AR</State>\n" +
                "            <PostalCode>72712</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>JUSTBRAND LIMITED</AddressName>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                "            <FOBLocationDescription>VARIOUS                     ZZ</FOBLocationDescription>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>CALL4792734300#</CarrierRouting>\n" +
                "            <RoutingSequenceCode>O</RoutingSequenceCode>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>MR</ReferenceQual>\n" +
                "            <ReferenceID>0003</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Notes>\n" +
                "            <NoteCode>GEN</NoteCode>\n" +
                "            <NoteInformationField>SPECIAL INSTRUCTIONS : NO PRETICKET</NoteInformationField>\n" +
                "        </Notes>\n" +
                "        <MonetaryAmounts>\n" +
                "            <MonetaryAmountCode>GV</MonetaryAmountCode>\n" +
                "            <MonetaryAmount>442.56</MonetaryAmount>\n" +
                "        </MonetaryAmounts>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>001</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>594981491</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-INS-LI-01-XL</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807589</ConsumerPackageCode>\n" +
                "                <GTIN>00675595807589</GTIN>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePriceType>CAT</PurchasePriceType>\n" +
                "                <PurchasePrice>70.95</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ExtendedItemTotal>283.8</ExtendedItemTotal>\n" +
                "                <ProductSizeCode>L/XL</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>4</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>002</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>594981499</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-AA-03-S-M</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807442</ConsumerPackageCode>\n" +
                "                <GTIN>00675595807442</GTIN>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePriceType>CAT</PurchasePriceType>\n" +
                "                <PurchasePrice>26.46</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ExtendedItemTotal>79.38</ExtendedItemTotal>\n" +
                "                <ProductSizeCode>S/M</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>3</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>003</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>594981544</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-GV-AA-01-M</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595808258</ConsumerPackageCode>\n" +
                "                <GTIN>00675595808258</GTIN>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePriceType>CAT</PurchasePriceType>\n" +
                "                <PurchasePrice>26.46</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ExtendedItemTotal>79.38</ExtendedItemTotal>\n" +
                "                <ProductSizeCode>S/M</ProductSizeCode>\n" +
                "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                "            </OrderLine>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>3</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n"


        try {
            JustBrandsWalmartEDI edi = new JustBrandsWalmartEDI();

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
}

package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsRetailConceptsDropShipEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class JustBrandsRetailConceptsDropShipTesting extends GroovyTestCase {



    


    @Test
    void test1shipOrderretailDropship() {
        System.setProperty("com.owd.environment", "test");

        String poData =
                "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>3ANALLJUSTBRAND</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>0000129964</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2020-12-16</PurchaseOrderDate>\n" +
                        "            <Vendor>02565DS</Vendor>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsType>14</TermsType>\n" +
                        "            <TermsBasisDateCode>15</TermsBasisDateCode>\n" +
                        "            <TermsDescription>NET 30 DAYS</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                        "            <Date1>2020-12-16</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                        "            <Date1>2020-12-21</Date1>\n" +
                        "        </Date>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                        "            <AddressLocationNumber>99999</AddressLocationNumber>\n" +
                        "            <AddressName>DAVE TAURINSKAS</AddressName>\n" +
                        "            <Address1>3929 ARTHUR ST NE</Address1>\n" +
                        "            <City>COLUMBIA HTS</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>55421</PostalCode>\n" +
                        "            <Country>US</Country>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>PP</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>DE</FOBLocationQualifier>\n" +
                        "            <FOBLocationDescription>COLUMBIA HTS, MN</FOBLocationDescription>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierAlphaCode>FDEG</CarrierAlphaCode>\n" +
                        "            <CarrierRouting>SEE ROUTING GUIDE</CarrierRouting>\n" +
                        "        </CarrierInformation>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>3465-002565-0002110-0001-00000</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-5V-6000-S</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595811555</ConsumerPackageCode>\n" +
                        "                <OrderQty>2</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>12.5</PurchasePrice>\n" +
                        "                <ProductSizeCode>NO SIZE</ProductSizeCode>\n" +
                        "                <ProductColorCode>BLACK</ProductColorCode>\n" +
                        "            </OrderLine>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                        "                <ProductDescription>ACTIONHEAT 5V 6000MAH POW</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                        "    </Summary>\n" +
                        "</Order>\n" ;


        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))
        try {
            JustBrandsRetailConceptsDropShipEDI edi = new JustBrandsRetailConceptsDropShipEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfileRetailDrop.xml", account)

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








}

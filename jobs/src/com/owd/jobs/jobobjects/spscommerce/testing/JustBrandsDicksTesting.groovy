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
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class JustBrandsDicksTesting extends GroovyTestCase {

    @Test
    void viewASN() {
        println("hello");
        String asnData = SpsCommerceUtilities.generateASN(12052072, 471);

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
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>0XRALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>9863870011_TEST</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2018-11-08</PurchaseOrderDate>\n" +
                "            <Vendor>057811</Vendor>\n" +
                "            <Division>DSG_ECOM</Division>\n" +
                "            <CustomerAccountNumber>989787308014</CustomerAccountNumber>\n" +
                "            <CustomerOrderNumber>10130469459</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Master Card</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2018-11-08</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Chris Kowatch</AddressName>\n" +
                "            <AddressAlternateName>Chris</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Kowatch</AddressAlternateName2>\n" +
                "            <Address1>1213 Hampshire Dr</Address1>\n" +
                "            <City>Saint Johns</City>\n" +
                "            <State>MI</State>\n" +
                "            <PostalCode>48879-2473</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>9892271652</PrimaryPhone>\n" +
                "                <PrimaryEmail>cmkowatch@gmail.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Chris Kowatch</AddressName>\n" +
                "            <AddressAlternateName>Chris</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Kowatch</AddressAlternateName2>\n" +
                "            <Address1>1213 Hampshire Dr</Address1>\n" +
                "            <City>Saint Johns</City>\n" +
                "            <State>MI</State>\n" +
                "            <PostalCode>48879-2473</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>9892271652</PrimaryPhone>\n" +
                "                <PrimaryEmail>cmkowatch@gmail.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>06</ReferenceQual>\n" +
                "            <ReferenceID>675173233</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>100035728564</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>01</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>017385723</BuyerPartNumber>\n" +
                "                <VendorPartNumber>675595807411</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807411</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <PurchasePriceType>CON</PurchasePriceType>\n" +
                "                <PurchasePrice>20</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2018-11-16</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2018-11-09</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>39.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ActionHeat Cotton AA Battery Heated Socks</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteCode>GEN</NoteCode>\n" +
                "                <NoteInformationField>Color=Black</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <Notes>\n" +
                "                <NoteCode>GEN</NoteCode>\n" +
                "                <NoteInformationField>Sock Size=S/M</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>02</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>017385725</BuyerPartNumber>\n" +
                "                <VendorPartNumber>675595807428</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807428</ConsumerPackageCode>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <PurchasePriceType>CON</PurchasePriceType>\n" +
                "                <PurchasePrice>20</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2018-11-16</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2018-11-09</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>79.98</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ActionHeat Cotton AA Battery Heated Socks</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteCode>GEN</NoteCode>\n" +
                "                <NoteInformationField>Color=Black</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <Notes>\n" +
                "                <NoteCode>GEN</NoteCode>\n" +
                "                <NoteInformationField>Sock Size=L/XL</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsDicksEDI edi = new JustBrandsDicksEDI();

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
        l.add(16854096);
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


        for(Integer i:l){
            SPSCommerceBaseClient.submitASN(i, 626);
        }



    }


}

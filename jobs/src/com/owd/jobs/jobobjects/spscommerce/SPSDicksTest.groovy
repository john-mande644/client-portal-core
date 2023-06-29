package com.owd.jobs.jobobjects.spscommerce

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderFactory
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.hibernate.generated.OwdOrder
import org.junit.Test

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by danny on 1/30/2017.
 */
class SPSDicksTest extends GroovyTestCase{

        @Test
        void viewASN(){
                println("hello");
                String asnData = SpsCommerceUtilities.generateASN(12052072,471);

                println asnData;
        }


    @Test
    void test1shipOrderFromPoDicksGildan() {
        System.setProperty("com.owd.environment","test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>00121371503</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2017-01-25</PurchaseOrderDate>\n" +
                "            <Vendor>008337</Vendor>\n" +
                "            <Division>DSG_ECOM</Division>\n" +
                "            <CustomerOrderNumber>VDCORDER00003AK</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Visa</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2017-01-25</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Mary Smith</AddressName>\n" +
                "            <AddressAlternateName>Mary</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Smith</AddressAlternateName2>\n" +
                "            <Address1>1 Stewart St</Address1>\n" +
                "            <City>Ithaca</City>\n" +
                "            <State>NY</State>\n" +
                "            <PostalCode>14850</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "                <PrimaryEmail>marysmith@sample.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>John Doe</AddressName>\n" +
                "            <AddressAlternateName>John</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Doe</AddressAlternateName2>\n" +
                "            <Address1>1 Fuller Rd</Address1>\n" +
                "            <City>Albany</City>\n" +
                "            <State>NY</State>\n" +
                "            <PostalCode>12222</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "                <PrimaryEmail>johndoe@sample.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>06</ReferenceQual>\n" +
                "            <ReferenceID>119445803</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>100000015540</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Notes>\n" +
                "            <NoteCode>GFT</NoteCode>\n" +
                "            <NoteInformationField>Happy Birthday</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>01</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575553</BuyerPartNumber>\n" +
                "                <VendorPartNumber>88880101</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>800000000001</ConsumerPackageCode>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>L</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Blue</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>59.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteCode>GEN</NoteCode>\n" +
                "                <NoteInformationField>Attribute=J. Smith</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceDicksSportingGoodsUtilities.getEdiConfigData(po)
            Order order = SpsCommerceDicksSportingGoodsUtilities.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");


            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if(null != config.vendorComplianceFkey){
                order.addTag(TagUtilities.kVendorComplianceIDReference,config.getVendorComplianceFkey()+"");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment=false
            order.bill_cc_type = "CK";
            // order.setShippingMethodName("LTL");
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            order.facilityCode='DC1'
            order.facilityPolicy='DC1'
            order.setThirdPartyBilling("9V3W92");
            order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");


            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                if(order.shipMethodName.contains("FedEx")) {
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000",true);
                }  else if (order.shipMethodName.contains("UPS")){
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000",true);
                }else{
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211",true);
                }

            //we have a shipped order, now create the ASN file for it

            String asnData = SpsCommerceUtilities.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

            println asnData
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
           // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }

    @Test
    void test2OrderFromPoDicksGildan() {
        System.setProperty("com.owd.environment","test");

         String poData = "<Order>\n" +
                 "    <Header>\n" +
                 "        <OrderHeader>\n" +
                 "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                 "            <PurchaseOrderNumber>00121371505</PurchaseOrderNumber>\n" +
                 "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                 "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                 "            <PurchaseOrderDate>2017-01-25</PurchaseOrderDate>\n" +
                 "            <Vendor>008337</Vendor>\n" +
                 "            <Division>FS_ECOM</Division>\n" +
                 "            <CustomerAccountNumber>222222222222</CustomerAccountNumber>\n" +
                 "            <CustomerOrderNumber>VDCORDER00002AK</CustomerOrderNumber>\n" +
                 "        </OrderHeader>\n" +
                 "        <PaymentTerms>\n" +
                 "            <TermsDescription>MC</TermsDescription>\n" +
                 "        </PaymentTerms>\n" +
                 "        <Date>\n" +
                 "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                 "            <Date1>2017-01-25</Date1>\n" +
                 "        </Date>\n" +
                 "        <Address>\n" +
                 "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                 "            <AddressName>Jane Smith</AddressName>\n" +
                 "            <AddressAlternateName>Jane</AddressAlternateName>\n" +
                 "            <AddressAlternateName2>Smith</AddressAlternateName2>\n" +
                 "            <Address1>XYZ Company</Address1>\n" +
                 "            <Address2>2 Woodbridge Ave</Address2>\n" +
                 "            <City>Edison</City>\n" +
                 "            <State>NJ</State>\n" +
                 "            <PostalCode>08837</PostalCode>\n" +
                 "            <Country>USA</Country>\n" +
                 "            <Contact>\n" +
                 "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                 "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                 "                <PrimaryEmail>janesmith@sample.com</PrimaryEmail>\n" +
                 "            </Contact>\n" +
                 "        </Address>\n" +
                 "        <Address>\n" +
                 "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                 "            <AddressName>Jane Smith</AddressName>\n" +
                 "            <AddressAlternateName>Jane</AddressAlternateName>\n" +
                 "            <AddressAlternateName2>Smith</AddressAlternateName2>\n" +
                 "            <Address1>ABC Company Name</Address1>\n" +
                 "            <Address2>2 Woodbridge Ave</Address2>\n" +
                 "            <City>Edison</City>\n" +
                 "            <State>NJ</State>\n" +
                 "            <PostalCode>08837</PostalCode>\n" +
                 "            <Country>USA</Country>\n" +
                 "            <Contact>\n" +
                 "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                 "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                 "                <PrimaryEmail>janesmith@sample.com</PrimaryEmail>\n" +
                 "            </Contact>\n" +
                 "        </Address>\n" +
                 "        <CarrierInformation>\n" +
                 "            <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                 "        </CarrierInformation>\n" +
                 "        <Reference>\n" +
                 "            <ReferenceQual>06</ReferenceQual>\n" +
                 "            <ReferenceID>119445804</ReferenceID>\n" +
                 "        </Reference>\n" +
                 "        <Reference>\n" +
                 "            <ReferenceQual>AN</ReferenceQual>\n" +
                 "            <ReferenceID>100000015541</ReferenceID>\n" +
                 "        </Reference>\n" +
                 "    </Header>\n" +
                 "    <LineItems>\n" +
                 "        <LineItem>\n" +
                 "            <OrderLine>\n" +
                 "                <LineSequenceNumber>06</LineSequenceNumber>\n" +
                 "                <BuyerPartNumber>802575554</BuyerPartNumber>\n" +
                 "                <VendorPartNumber>88880201</VendorPartNumber>\n" +
                 "                <ConsumerPackageCode>800000000002</ConsumerPackageCode>\n" +
                 "                <OrderQty>2</OrderQty>\n" +
                 "                <PurchasePrice>9</PurchasePrice>\n" +
                 "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                 "                <ProductSizeDescription>XLT</ProductSizeDescription>\n" +
                 "                <ProductColorDescription>Red</ProductColorDescription>\n" +
                 "            </OrderLine>\n" +
                 "            <Date>\n" +
                 "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                 "                <Date1>2017-01-28</Date1>\n" +
                 "            </Date>\n" +
                 "            <Date>\n" +
                 "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                 "                <Date1>2017-01-26</Date1>\n" +
                 "            </Date>\n" +
                 "            <PriceInformation>\n" +
                 "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                 "                <UnitPrice>17.99</UnitPrice>\n" +
                 "            </PriceInformation>\n" +
                 "            <ProductOrItemDescription>\n" +
                 "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                 "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                 "            </ProductOrItemDescription>\n" +
                 "            <CarrierInformation>\n" +
                 "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                 "            </CarrierInformation>\n" +
                 "        </LineItem>\n" +
                 "    </LineItems>\n" +
                 "    <Summary>\n" +
                 "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                 "    </Summary>\n" +
                 "</Order>"

       // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)


            Node po =       parser.parseText(poData)

            String cancelAsn = SpsCommerceDicksSportingGoodsUtilities.generateCancelAsn(po,471)




            println cancelAsn
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, cancelAsn.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }

    @Test
    void test3Ship2boxesloadOrderFromPoDicksGildan2() {
        System.setProperty("com.owd.environment","test");

        String poData = "" +
                "" +
                "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>00121371507</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2017-01-25</PurchaseOrderDate>\n" +
                "            <Vendor>008337</Vendor>\n" +
                "            <Division>GG_ECOM</Division>\n" +
                "            <CustomerAccountNumber>333333333333</CustomerAccountNumber>\n" +
                "            <CustomerOrderNumber>VDCORDER00004AK</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Visa</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2017-01-25</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Bill Johnson</AddressName>\n" +
                "            <AddressAlternateName>Bill</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Johnson</AddressAlternateName2>\n" +
                "            <Address1>3 Falke Plaza</Address1>\n" +
                "            <Address2>Apartment 3B</Address2>\n" +
                "            <City>Sterling</City>\n" +
                "            <State>VA</State>\n" +
                "            <PostalCode>20166</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "                <PrimaryEmail>billjohnson@sample.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Tom Smith</AddressName>\n" +
                "            <AddressAlternateName>Tom</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Smith</AddressAlternateName2>\n" +
                "            <Address1>3 Western Parkway</Address1>\n" +
                "            <City>Richmond</City>\n" +
                "            <State>VA</State>\n" +
                "            <PostalCode>23233</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_SE</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>06</ReferenceQual>\n" +
                "            <ReferenceID>119445811</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>100000015542</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>03</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575555</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000003</ConsumerPackageCode>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <PurchasePrice>8</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>14.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_SE</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>05</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575556</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000004</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <PurchasePrice>3</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>6.66</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_SE</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n" +
                ""

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceDicksSportingGoodsUtilities.getEdiConfigData(po)
            Order order = SpsCommerceDicksSportingGoodsUtilities.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");


            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if(null != config.vendorComplianceFkey){
                order.addTag(TagUtilities.kVendorComplianceIDReference,config.getVendorComplianceFkey()+"");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment=false
            order.bill_cc_type = "CK";
            // order.setShippingMethodName("LTL");
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            order.facilityCode='DC1'
            order.facilityPolicy='DC1'

            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                if(order.shipMethodName.contains("FedEx")) {
                        PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "000000000000000",true);
                }  else if (order.shipMethodName.contains("UPS")){
                        PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1Z0000000000000000",true);
                }else{
                        PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1234567891211",true);
                }
            //we have a shipped order, now create the ASN file for it

            String asnData = SpsCommerceDicksSportingGoodsUtilities.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

            println asnData
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }

    @Test
    void test4cancelOrderFromPoDicksGildan() {
        System.setProperty("com.owd.environment","test");

        String poData =
                "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>00121371509</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2017-01-25</PurchaseOrderDate>\n" +
                "            <Vendor>008337</Vendor>\n" +
                "            <Division>TR_ECOM</Division>\n" +
                "            <CustomerAccountNumber>444444444444</CustomerAccountNumber>\n" +
                "            <CustomerOrderNumber>VDCORDER00005AK</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>MC</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2017-01-25</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Mike Jones</AddressName>\n" +
                "            <AddressAlternateName>Mike</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Jones</AddressAlternateName2>\n" +
                "            <Address1>XYZ Company</Address1>\n" +
                "            <Address2>4 La Jolla Village Dr</Address2>\n" +
                "            <City>San Diego</City>\n" +
                "            <State>CA</State>\n" +
                "            <PostalCode>92122</PostalCode>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Mike Jones</AddressName>\n" +
                "            <AddressAlternateName>Mike</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Jones</AddressAlternateName2>\n" +
                "            <Address1>6929 S. Memorial</Address1>\n" +
                "            <City>Tulsa</City>\n" +
                "            <State>OK</State>\n" +
                "            <PostalCode>12345</PostalCode>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_ND</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>06</ReferenceQual>\n" +
                "            <ReferenceID>119445805</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>100000015543</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>01</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575557</BuyerPartNumber>\n" +
                "                <VendorPartNumber>88880301</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>800000000005</ConsumerPackageCode>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <PurchasePrice>10</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>19.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteCode>GEN</NoteCode>\n" +
                "                <NoteInformationField>Attribute=J. Smith</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_ND</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>02</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575558</BuyerPartNumber>\n" +
                "                <VendorPartNumber>88880401</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>9999999990402</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <PurchasePrice>5</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>9.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Notes>\n" +
                "                <NoteCode>GEN</NoteCode>\n" +
                "                <NoteInformationField>Attribute=12345</NoteInformationField>\n" +
                "            </Notes>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_ND</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)


            Node po =       parser.parseText(poData)

            String cancelAsn = SpsCommerceDicksSportingGoodsUtilities.generateCancelAsn(po,471)




            println cancelAsn
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, cancelAsn.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }


    @Test
    void test5loadOrderFromPoDicksGildan2() {
        System.setProperty("com.owd.environment","test");

        String poData =
                "<Order>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>00121667075</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2017-02-16</PurchaseOrderDate>\n" +
                        "            <Vendor>008337</Vendor>\n" +
                        "            <Division>CL_ECOM</Division>\n" +
                        "            <CustomerOrderNumber>VDCORDER00006AK</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsDescription>MC</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                        "            <Date1>2017-02-16</Date1>\n" +
                        "        </Date>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Mary Doe</AddressName>\n" +
                        "            <AddressAlternateName>Mary</AddressAlternateName>\n" +
                        "            <AddressAlternateName2>Doe</AddressAlternateName2>\n" +
                        "            <Address1>XYZ Company</Address1>\n" +
                        "            <Address2>5 Busch Blvd</Address2>\n" +
                        "            <City>Tampa</City>\n" +
                        "            <State>FL</State>\n" +
                        "            <PostalCode>33612</PostalCode>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>Mary Doe</AddressName>\n" +
                        "            <AddressAlternateName>Mary</AddressAlternateName>\n" +
                        "            <AddressAlternateName2>Doe</AddressAlternateName2>\n" +
                        "            <Address1>ABC Company Name</Address1>\n" +
                        "            <Address2>5 Busch Blvd</Address2>\n" +
                        "            <City>Tampa</City>\n" +
                        "            <State>FL</State>\n" +
                        "            <PostalCode>33612</PostalCode>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierRouting>UNSP_SE</CarrierRouting>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>06</ReferenceQual>\n" +
                        "            <ReferenceID>119459932</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>AN</ReferenceQual>\n" +
                        "            <ReferenceID>100000015544</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>01</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>802575559</BuyerPartNumber>\n" +
                        "                <ConsumerPackageCode>800000000006</ConsumerPackageCode>\n" +
                        "                <OrderQty>5</OrderQty>\n" +
                        "                <PurchasePrice>75</PurchasePrice>\n" +
                        "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                        "                <Date1>2017-02-19</Date1>\n" +
                        "            </Date>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                        "                <Date1>2017-02-17</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>148</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                        "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <CarrierInformation>\n" +
                        "                <CarrierRouting>UNSP_SE</CarrierRouting>\n" +
                        "            </CarrierInformation>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>04</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>802575550</BuyerPartNumber>\n" +
                        "                <ConsumerPackageCode>800000000007</ConsumerPackageCode>\n" +
                        "                <OrderQty>2</OrderQty>\n" +
                        "                <PurchasePrice>12.5</PurchasePrice>\n" +
                        "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                        "                <Date1>2017-02-19</Date1>\n" +
                        "            </Date>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                        "                <Date1>2017-02-17</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>24.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                        "                <ProductDescription>Sample item description for line 2</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <CarrierInformation>\n" +
                        "                <CarrierRouting>UNSP_SE</CarrierRouting>\n" +
                        "            </CarrierInformation>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                        "    </Summary>\n" +
                        "</Order>"


        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceDicksSportingGoodsUtilities.getEdiConfigData(po)
            Order order = SpsCommerceDicksSportingGoodsUtilities.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");


            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if(null != config.vendorComplianceFkey){
                order.addTag(TagUtilities.kVendorComplianceIDReference,config.getVendorComplianceFkey()+"");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment=false
            order.bill_cc_type = "CK";
            // order.setShippingMethodName("LTL");
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            order.facilityCode='DC1'
            order.facilityPolicy='DC1'

            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                if(order.shipMethodName.contains("FedEx")) {
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000",true);
                }  else if (order.shipMethodName.contains("UPS")){
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000",true);
                }else{
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211",true);
                }

            //we have a shipped order, now create the ASN file for it

            String asnData = SpsCommerceDicksSportingGoodsUtilities.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

            println asnData
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }


    @Test
    void test6loadOrderFromPoDicksGildan2() {
        System.setProperty("com.owd.environment","test");

        String poData =
                "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>00121371513</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2017-01-25</PurchaseOrderDate>\n" +
                "            <Vendor>008337</Vendor>\n" +
                "            <Division>DSG_ECOM</Division>\n" +
                "            <CustomerOrderNumber>VDCORDER00007AK</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Visa</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2017-01-25</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Mary Doe</AddressName>\n" +
                "            <AddressAlternateName>Mary</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Doe</AddressAlternateName2>\n" +
                "            <Address1>XYZ Company</Address1>\n" +
                "            <Address2>6 Quorum Drive</Address2>\n" +
                "            <City>Dallas</City>\n" +
                "            <State>TX</State>\n" +
                "            <PostalCode>75240</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Lisa Smith</AddressName>\n" +
                "            <AddressAlternateName>Lisa</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Smith</AddressAlternateName2>\n" +
                "            <Address1>ABC Company Name</Address1>\n" +
                "            <Address2>6 West Loop South</Address2>\n" +
                "            <City>Houston</City>\n" +
                "            <State>TX</State>\n" +
                "            <PostalCode>77027</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_ND</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>06</ReferenceQual>\n" +
                "            <ReferenceID>119445807</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>100000015545</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>01</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>902575553</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000008</ConsumerPackageCode>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <PurchasePrice>3</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>6.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_ND</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>02</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575560</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000009</ConsumerPackageCode>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <PurchasePrice>35</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>79.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 2</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_ND</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)


            Node po =       parser.parseText(poData)

            String cancelAsn = SpsCommerceDicksSportingGoodsUtilities.generateCancelAsn(po,471)




            println cancelAsn
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, cancelAsn.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }

    @Test
    void test7loadOrderFromPoDicksGildan2() {
        System.setProperty("com.owd.environment","test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>00121371515</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2017-01-25</PurchaseOrderDate>\n" +
                "            <Vendor>008337</Vendor>\n" +
                "            <Division>DSG_ECOM</Division>\n" +
                "            <CustomerOrderNumber>VDCORDER00008AK</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>MC</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2017-01-25</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>John Doe</AddressName>\n" +
                "            <AddressAlternateName>John</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Doe</AddressAlternateName2>\n" +
                "            <Address1>XYZ Company</Address1>\n" +
                "            <Address2>7 Eden Street</Address2>\n" +
                "            <City>Bar Harbor</City>\n" +
                "            <State>ME</State>\n" +
                "            <PostalCode>04609</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "                <PrimaryEmail>johndoe@sample.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Mary Johnson</AddressName>\n" +
                "            <AddressAlternateName>Mary</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Johnson</AddressAlternateName2>\n" +
                "            <Address1>ABC Company Name</Address1>\n" +
                "            <Address2>7 Ocean Ave</Address2>\n" +
                "            <City>Kennebunkport</City>\n" +
                "            <State>ME</State>\n" +
                "            <PostalCode>04046</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "                <PrimaryEmail>maryjohnson@sample.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>06</ReferenceQual>\n" +
                "            <ReferenceID>119445808</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>100000015546</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Notes>\n" +
                "            <NoteCode>GFT</NoteCode>\n" +
                "            <NoteInformationField>Get Well Soon</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>01</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575561</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000001</ConsumerPackageCode>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <PurchasePrice>15</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>29.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceDicksSportingGoodsUtilities.getEdiConfigData(po)
            Order order = SpsCommerceDicksSportingGoodsUtilities.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");


            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if(null != config.vendorComplianceFkey){
                order.addTag(TagUtilities.kVendorComplianceIDReference,config.getVendorComplianceFkey()+"");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment=false
            order.bill_cc_type = "CK";
            // order.setShippingMethodName("LTL");
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            order.facilityCode='DC1'
            order.facilityPolicy='DC1'

            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                if(order.shipMethodName.contains("FedEx")) {
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000",true);
                }  else if (order.shipMethodName.contains("UPS")){
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000",true);
                }else{
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211",true);
                }
            //we have a shipped order, now create the ASN file for it

            String asnData = SpsCommerceDicksSportingGoodsUtilities.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

            println asnData
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }
    @Test
    void test8loadOrderFromPoDicksGildan2() {
        System.setProperty("com.owd.environment","test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>00121371517</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2017-01-25</PurchaseOrderDate>\n" +
                "            <Vendor>008337</Vendor>\n" +
                "            <Division>DSG_ECOM</Division>\n" +
                "            <CustomerOrderNumber>VDCORDER00009AK</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>MC</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2017-01-25</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>John Doe</AddressName>\n" +
                "            <AddressAlternateName>John</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Doe</AddressAlternateName2>\n" +
                "            <Address1>8 Stoneridge Drive</Address1>\n" +
                "            <Address2>Suite 8 Luxury Apts</Address2>\n" +
                "            <City>Columbia</City>\n" +
                "            <State>SC</State>\n" +
                "            <PostalCode>29210</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "                <PrimaryEmail>johndoe@sample.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Jim Smith</AddressName>\n" +
                "            <AddressAlternateName>Jim</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Smith</AddressAlternateName2>\n" +
                "            <Address1>8 Waterside Drive</Address1>\n" +
                "            <Address2>Apartment 8</Address2>\n" +
                "            <City>Hilton Head</City>\n" +
                "            <State>SC</State>\n" +
                "            <PostalCode>29928</PostalCode>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "                <PrimaryEmail>jimsmith@sample.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>06</ReferenceQual>\n" +
                "            <ReferenceID>119445809</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>100000015547</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>01</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575562</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000011</ConsumerPackageCode>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <PurchasePrice>75</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-01-26</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>149</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)


            Node po =       parser.parseText(poData)

            String cancelAsn = SpsCommerceDicksSportingGoodsUtilities.generateCancelAsn(po,471)




            println cancelAsn
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, cancelAsn.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }

    @Test
    void test9loadOrderFromPoDicksGildan2() {
        System.setProperty("com.owd.environment","test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>00121667081</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2017-02-16</PurchaseOrderDate>\n" +
                "            <Vendor>008337</Vendor>\n" +
                "            <Division>DSG_ECOM</Division>\n" +
                "            <CustomerOrderNumber>VDCORDER00000AK</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Visa</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2017-02-16</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Paula Jones</AddressName>\n" +
                "            <AddressAlternateName>Paula</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Jones</AddressAlternateName2>\n" +
                "            <Address1>9 Main St</Address1>\n" +
                "            <Address2>Apt 9</Address2>\n" +
                "            <City>Moab</City>\n" +
                "            <State>UT</State>\n" +
                "            <PostalCode>84532</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Mike Jones</AddressName>\n" +
                "            <AddressAlternateName>Mike</AddressAlternateName>\n" +
                "            <AddressAlternateName2>Jones</AddressAlternateName2>\n" +
                "            <Address1>9 Wakara Way</Address1>\n" +
                "            <Address2>Suite 9 - High Towers</Address2>\n" +
                "            <City>Salt Lake City</City>\n" +
                "            <State>UT</State>\n" +
                "            <PostalCode>84108</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>BD</ContactTypeCode>\n" +
                "                <PrimaryPhone>5555555555</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>06</ReferenceQual>\n" +
                "            <ReferenceID>119458944</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>100000015548</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>01</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575563</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000021</ConsumerPackageCode>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <PurchasePrice>2</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>3.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 1</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>02</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575564</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000031</ConsumerPackageCode>\n" +
                "                <OrderQty>9</OrderQty>\n" +
                "                <PurchasePrice>2.5</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>4.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 2</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>03</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575565</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000041</ConsumerPackageCode>\n" +
                "                <OrderQty>8</OrderQty>\n" +
                "                <PurchasePrice>3</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>5.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 3</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>04</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575566</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000051</ConsumerPackageCode>\n" +
                "                <OrderQty>7</OrderQty>\n" +
                "                <PurchasePrice>3.5</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>6.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 4</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>05</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575567</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000061</ConsumerPackageCode>\n" +
                "                <OrderQty>6</OrderQty>\n" +
                "                <PurchasePrice>4</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>7.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 5</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>06</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575568</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000071</ConsumerPackageCode>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <PurchasePrice>4.5</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>8.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 6</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>07</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575569</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000081</ConsumerPackageCode>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <PurchasePrice>5</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>9.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 7</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>08</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575570</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000091</ConsumerPackageCode>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <PurchasePrice>5.5</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>10.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 8</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>09</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575571</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000012</ConsumerPackageCode>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <PurchasePrice>6</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>11.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 9</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>10</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>802575572</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>800000000013</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <PurchasePrice>6.5</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-19</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2017-02-17</Date1>\n" +
                "            </Date>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>12.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Sample item description for line 10</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <CarrierInformation>\n" +
                "                <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "            </CarrierInformation>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>10</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceDicksSportingGoodsUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceDicksSportingGoodsUtilities.getEdiConfigData(po)
            Order order = SpsCommerceDicksSportingGoodsUtilities.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");


            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            if(null != config.vendorComplianceFkey){
                order.addTag(TagUtilities.kVendorComplianceIDReference,config.getVendorComplianceFkey()+"");

            }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment=false
            order.bill_cc_type = "CK";
            // order.setShippingMethodName("LTL");
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            order.facilityCode='DC1'
            order.facilityPolicy='DC1'

            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

             if(order.shipMethodName.contains("FedEx")) {
                     PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000",true);
             }  else if (order.shipMethodName.contains("UPS")){
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000",true);
                }else{
                     PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211",true);
             }

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information


            //we have a shipped order, now create the ASN file for it

            String asnData = SpsCommerceDicksSportingGoodsUtilities.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

            println asnData
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }



    @Test
    void postInventoryLevels(){

         try {
             String inv = SpsCommerceDicksSportingGoodsUtilities.generate846Inventory(SpsCommerceDicksSportingGoodsUtilities.getEdiConfigDataSPSAccount("0XRTSTGILDANUSA"), 471)
             println(inv)

             SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

             //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
             ftp.putDataFile(SPSCommerceRemoteFTP.fileType.IN, inv.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
             assertEquals(1,1)


         }catch (Exception e){
             e.printStackTrace()
         }

    }
}

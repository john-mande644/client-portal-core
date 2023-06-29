package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsBassProEDI
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsTargetEDI
import com.owd.jobs.jobobjects.spscommerce.clients.RovrBassProEDI
import org.junit.Test

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by danny on 1/30/2017.
 */
class RovrBassProTesting extends GroovyTestCase {



        @Test
        void testLoadingDropShip() {
                System.setProperty("com.owd.environment", "test");

                String poData = "<Order>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>563ALLROVRPRODU</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>8419571</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2020-11-01</PurchaseOrderDate>\n" +
                        "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                        "            <Department>AB1</Department>\n" +
                        "            <Vendor>226452</Vendor>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsType>14</TermsType>\n" +
                        "            <TermsBasisDateCode>15</TermsBasisDateCode>\n" +
                        "            <TermsDescription>2% 60 NET 61</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2020-11-06</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                        "            <Date1>2020-11-06</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                        "            <Date1>2020-11-06</Date1>\n" +
                        "        </Date>\n" +
                        "        <Contact>\n" +
                        "            <ContactTypeCode>OC</ContactTypeCode>\n" +
                        "            <ContactName>Drop Ship Cust Serv</ContactName>\n" +
                        "            <PrimaryPhone>4178735000</PrimaryPhone>\n" +
                        "        </Contact>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                        "            <AddressLocationNumber>DR</AddressLocationNumber>\n" +
                        "            <AddressName>Matthew     Kelly</AddressName>\n" +
                        "            <Address1>28455 Redwood Canyon Pl</Address1>\n" +
                        "            <City>SANTA CLARITA</City>\n" +
                        "            <State>CA</State>\n" +
                        "            <PostalCode>913905725</PostalCode>\n" +
                        "            <Country>US</Country>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>OC</ContactTypeCode>\n" +
                        "                <PrimaryPhone>4178735000</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>DF</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>DE</FOBLocationQualifier>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierRouting>Vendor Guide</CarrierRouting>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Notes>\n" +
                        "            <NoteCode>SHP</NoteCode>\n" +
                        "            <NoteInformationField>PO NOTE:DROP SHIP</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <Notes>\n" +
                        "            <NoteCode>SHP</NoteCode>\n" +
                        "            <NoteInformationField>PO NOTE:SOLD TO: Matthew      Kelly</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <Notes>\n" +
                        "            <NoteCode>SHP</NoteCode>\n" +
                        "            <NoteInformationField>PO NOTE:01/19/70      ORDER NUMBER  W120988791     000001</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <ChargesAllowances>\n" +
                        "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                        "            <AllowChrgCode>A260</AllowChrgCode>\n" +
                        "            <AllowChrgPercentQual>3</AllowChrgPercentQual>\n" +
                        "            <AllowChrgPercent>4</AllowChrgPercent>\n" +
                        "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                        "        </ChargesAllowances>\n" +
                        "        <ChargesAllowances>\n" +
                        "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                        "            <AllowChrgCode>C000</AllowChrgCode>\n" +
                        "            <AllowChrgPercentQual>3</AllowChrgPercentQual>\n" +
                        "            <AllowChrgPercent>1</AllowChrgPercent>\n" +
                        "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                        "        </ChargesAllowances>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <ConsumerPackageCode>852490007690</ConsumerPackageCode>\n" +
                        "                <ProductID>\n" +
                        "                    <PartNumberQual>MN</PartNumberQual>\n" +
                        "                    <PartNumber>60PROLLRW</PartNumber>\n" +
                        "                </ProductID>\n" +
                        "                <ProductID>\n" +
                        "                    <PartNumberQual>SK</PartNumberQual>\n" +
                        "                    <PartNumber>5073661</PartNumber>\n" +
                        "                </ProductID>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>240</PurchasePrice>\n" +
                        "                <ProductSizeCode>NONE</ProductSizeCode>\n" +
                        "                <ProductColorCode>WHITE</ProductColorCode>\n" +
                        "                <Department>361</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                        "                <ProductDescription>ROLLR 60- POWDER</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <PhysicalDetails>\n" +
                        "                <PackQualifier>OU</PackQualifier>\n" +
                        "                <PackValue>1</PackValue>\n" +
                        "            </PhysicalDetails>\n" +
                        "            <PhysicalDetails>\n" +
                        "                <PackQualifier>IN</PackQualifier>\n" +
                        "                <PackValue>1</PackValue>\n" +
                        "            </PhysicalDetails>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>1</TotalQuantity>\n" +
                        "    </Summary>\n" +
                        "</Order>"

                // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

                try {
                        RovrBassProEDI edi = new  RovrBassProEDI();

                       // println poData


                        // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
                        // needed translation from the EDI SKU to the OWD SKU.
                        // Currently, we support either looking SKUs up from the upc_code field or
                        // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
                        // for rows connected to the relevant edi_sps_configdata entry
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po = parser.parseText(poData)
                        edi.setDropShipFlag(po);
                        assertTrue(edi.dropShipOrder);
                        println("Hi")
                        println edi.getShipByDate(po);
                        println("bye")


                } catch (Exception ex) {
                        ex.printStackTrace()
                }

        }

        @Test
        void test1WarehouseFromPoBassPro() {
                System.setProperty("com.owd.environment", "test");



            String poData = "<Order>\n" +
                    "    <Header>\n" +
                    "        <OrderHeader>\n" +
                    "            <TradingPartnerId>563ALLROVRPRODU</TradingPartnerId>\n" +
                    "            <PurchaseOrderNumber>8419571</PurchaseOrderNumber>\n" +
                    "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                    "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                    "            <PurchaseOrderDate>2020-11-01</PurchaseOrderDate>\n" +
                    "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                    "            <Department>AB1</Department>\n" +
                    "            <Vendor>226452</Vendor>\n" +
                    "        </OrderHeader>\n" +
                    "        <PaymentTerms>\n" +
                    "            <TermsType>14</TermsType>\n" +
                    "            <TermsBasisDateCode>15</TermsBasisDateCode>\n" +
                    "            <TermsDescription>2% 60 NET 61</TermsDescription>\n" +
                    "        </PaymentTerms>\n" +
                    "        <Date>\n" +
                    "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                    "            <Date1>2020-11-06</Date1>\n" +
                    "        </Date>\n" +
                    "        <Date>\n" +
                    "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                    "            <Date1>2020-11-06</Date1>\n" +
                    "        </Date>\n" +
                    "        <Date>\n" +
                    "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                    "            <Date1>2020-11-06</Date1>\n" +
                    "        </Date>\n" +
                    "        <Contact>\n" +
                    "            <ContactTypeCode>OC</ContactTypeCode>\n" +
                    "            <ContactName>Drop Ship Cust Serv</ContactName>\n" +
                    "            <PrimaryPhone>4178735000</PrimaryPhone>\n" +
                    "        </Contact>\n" +
                    "        <Address>\n" +
                    "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                    "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                    "            <AddressLocationNumber>DR</AddressLocationNumber>\n" +
                    "            <AddressName>Matthew     Kelly</AddressName>\n" +
                    "            <Address1>28455 Redwood Canyon Pl</Address1>\n" +
                    "            <City>SANTA CLARITA</City>\n" +
                    "            <State>CA</State>\n" +
                    "            <PostalCode>913905725</PostalCode>\n" +
                    "            <Country>US</Country>\n" +
                    "            <Contact>\n" +
                    "                <ContactTypeCode>OC</ContactTypeCode>\n" +
                    "                <PrimaryPhone>4178735000</PrimaryPhone>\n" +
                    "            </Contact>\n" +
                    "        </Address>\n" +
                    "        <FOBRelatedInstruction>\n" +
                    "            <FOBPayCode>DF</FOBPayCode>\n" +
                    "            <FOBLocationQualifier>DE</FOBLocationQualifier>\n" +
                    "        </FOBRelatedInstruction>\n" +
                    "        <CarrierInformation>\n" +
                    "            <CarrierRouting>Vendor Guide</CarrierRouting>\n" +
                    "        </CarrierInformation>\n" +
                    "        <Notes>\n" +
                    "            <NoteCode>SHP</NoteCode>\n" +
                    "            <NoteInformationField>PO NOTE:DROP SHIP</NoteInformationField>\n" +
                    "        </Notes>\n" +
                    "        <Notes>\n" +
                    "            <NoteCode>SHP</NoteCode>\n" +
                    "            <NoteInformationField>PO NOTE:SOLD TO: Matthew      Kelly</NoteInformationField>\n" +
                    "        </Notes>\n" +
                    "        <Notes>\n" +
                    "            <NoteCode>SHP</NoteCode>\n" +
                    "            <NoteInformationField>PO NOTE:01/19/70      ORDER NUMBER  W120988791     000001</NoteInformationField>\n" +
                    "        </Notes>\n" +
                    "        <ChargesAllowances>\n" +
                    "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                    "            <AllowChrgCode>A260</AllowChrgCode>\n" +
                    "            <AllowChrgPercentQual>3</AllowChrgPercentQual>\n" +
                    "            <AllowChrgPercent>4</AllowChrgPercent>\n" +
                    "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                    "        </ChargesAllowances>\n" +
                    "        <ChargesAllowances>\n" +
                    "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                    "            <AllowChrgCode>C000</AllowChrgCode>\n" +
                    "            <AllowChrgPercentQual>3</AllowChrgPercentQual>\n" +
                    "            <AllowChrgPercent>1</AllowChrgPercent>\n" +
                    "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                    "        </ChargesAllowances>\n" +
                    "    </Header>\n" +
                    "    <LineItems>\n" +
                    "        <LineItem>\n" +
                    "            <OrderLine>\n" +
                    "                <ConsumerPackageCode>852490007690</ConsumerPackageCode>\n" +
                    "                <ProductID>\n" +
                    "                    <PartNumberQual>MN</PartNumberQual>\n" +
                    "                    <PartNumber>60PROLLRW</PartNumber>\n" +
                    "                </ProductID>\n" +
                    "                <ProductID>\n" +
                    "                    <PartNumberQual>SK</PartNumberQual>\n" +
                    "                    <PartNumber>5073661</PartNumber>\n" +
                    "                </ProductID>\n" +
                    "                <OrderQty>1</OrderQty>\n" +
                    "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                    "                <PurchasePrice>240</PurchasePrice>\n" +
                    "                <ProductSizeCode>NONE</ProductSizeCode>\n" +
                    "                <ProductColorCode>WHITE</ProductColorCode>\n" +
                    "                <Department>361</Department>\n" +
                    "            </OrderLine>\n" +
                    "            <ProductOrItemDescription>\n" +
                    "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                    "                <ProductDescription>ROLLR 60- POWDER</ProductDescription>\n" +
                    "            </ProductOrItemDescription>\n" +
                    "            <PhysicalDetails>\n" +
                    "                <PackQualifier>OU</PackQualifier>\n" +
                    "                <PackValue>1</PackValue>\n" +
                    "            </PhysicalDetails>\n" +
                    "            <PhysicalDetails>\n" +
                    "                <PackQualifier>IN</PackQualifier>\n" +
                    "                <PackValue>1</PackValue>\n" +
                    "            </PhysicalDetails>\n" +
                    "        </LineItem>\n" +
                    "    </LineItems>\n" +
                    "    <Summary>\n" +
                    "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                    "        <TotalQuantity>1</TotalQuantity>\n" +
                    "    </Summary>\n" +
                    "</Order>"
        try {
            RovrBassProEDI edi = new  RovrBassProEDI();

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

            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
                try {
                        int vComplainceId = edi.getVendorComplianceId(config,po);
                        if (vComplainceId > 0) {
                                order.addTag(TagUtilities.kVendorComplianceIDReference, vComplainceId + "");
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
            order.backorder_rule = OrderXMLDoc.kRejectBackOrder
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment = false
            order.bill_cc_type = "CK";
            // order.setShippingMethodName("LTL");
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                String method = po.LineItems.LineItem[0].CarrierInformation.CarrierRouting.text();
                if(null == method || method.length()==0){
                        method =  po.Header.CarrierInformation.CarrierRouting.text();
                }
                order.setShippingMethodName(edi.loadShippingMethod(order, method, po.Header.CarrierInformation))
                edi.loadThirdPartyBillingInfo(order);
                edi.loadOrderTemplate(order);
                edi.loadBusinessOrConsumerOrder(order);

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
          //   ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }

    @Test
    void test2shipOrderFromPoBassPro() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>563ALLROVRPRODU</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>8486115</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-11-23</PurchaseOrderDate>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Department>AB1</Department>\n" +
                "            <Vendor>226452</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>14</TermsType>\n" +
                "            <TermsBasisDateCode>15</TermsBasisDateCode>\n" +
                "            <TermsDescription>2% 60 NET 61</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                "            <Date1>2020-11-28</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                "            <Date1>2020-11-28</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "            <Date1>2020-11-28</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>OC</ContactTypeCode>\n" +
                "            <ContactName>Drop Ship Cust Serv</ContactName>\n" +
                "            <PrimaryPhone>4178735000</PrimaryPhone>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>DR</AddressLocationNumber>\n" +
                "            <AddressName>ANDREW      KWANG</AddressName>\n" +
                "            <Address1>3013 WINDRIFT LN</Address1>\n" +
                "            <City>BERTHOUD</City>\n" +
                "            <State>CO</State>\n" +
                "            <PostalCode>805138487</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>OC</ContactTypeCode>\n" +
                "                <PrimaryPhone>4178735000</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>DF</FOBPayCode>\n" +
                "            <FOBLocationQualifier>DE</FOBLocationQualifier>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>Vendor Guide</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Notes>\n" +
                "            <NoteCode>SHP</NoteCode>\n" +
                "            <NoteInformationField>PO NOTE:DROP SHIP</NoteInformationField>\n" +
                "        </Notes>\n" +
                "        <Notes>\n" +
                "            <NoteCode>SHP</NoteCode>\n" +
                "            <NoteInformationField>PO NOTE:SOLD TO: ANDREW       KWANG</NoteInformationField>\n" +
                "        </Notes>\n" +
                "        <Notes>\n" +
                "            <NoteCode>SHP</NoteCode>\n" +
                "            <NoteInformationField>PO NOTE:01/19/70      ORDER NUMBER  Y101055771     000001</NoteInformationField>\n" +
                "        </Notes>\n" +
                "        <ChargesAllowances>\n" +
                "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                "            <AllowChrgCode>A260</AllowChrgCode>\n" +
                "            <AllowChrgPercentQual>3</AllowChrgPercentQual>\n" +
                "            <AllowChrgPercent>4</AllowChrgPercent>\n" +
                "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                "        </ChargesAllowances>\n" +
                "        <ChargesAllowances>\n" +
                "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                "            <AllowChrgCode>C000</AllowChrgCode>\n" +
                "            <AllowChrgPercentQual>3</AllowChrgPercentQual>\n" +
                "            <AllowChrgPercent>1</AllowChrgPercent>\n" +
                "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                "        </ChargesAllowances>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <ConsumerPackageCode>852490007713</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>MN</PartNumberQual>\n" +
                "                    <PartNumber>60MROLLRW</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>SK</PartNumberQual>\n" +
                "                    <PartNumber>5073662</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>240</PurchasePrice>\n" +
                "                <ProductSizeCode>NONE</ProductSizeCode>\n" +
                "                <ProductColorCode>GREEN</ProductColorCode>\n" +
                "                <Department>361</Department>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ROLLR 60- MOSS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>IN</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "        <TotalQuantity>1</TotalQuantity>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            RovrBassProEDI edi = new RovrBassProEDI();

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
    void test3shipOrderFromPoTarget() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "        <InterchangeControlNumber>000000001</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>1</GroupControlNumber>\n" +
                "        <DocumentControlNumber>530002952</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>TGTDVS</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>8884061984</InterchangeReceiverID>\n" +
                "        <GroupSenderID>TGTDVS</GroupSenderID>\n" +
                "        <GroupReceiverID>8884061984</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>95OALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>1206327199</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2016-08-10</PurchaseOrderDate>\n" +
                "            <Vendor>202886</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                "            <Date1>2016-08-12</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                "            <Date1>2016-08-10</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>017</DateTimeQualifier1>\n" +
                "            <Date1>2016-08-19</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Target.com Accounts Payable</AddressName>\n" +
                "            <Address1>TNC 3110</Address1>\n" +
                "            <Address2>PO Box 1296</Address2>\n" +
                "            <City>Minneapolis</City>\n" +
                "            <State>MN</State>\n" +
                "            <PostalCode>55440</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>EDI Help Desk</AddressName>\n" +
                "            <Address1>7000 Target Parkway, NCC-03620</Address1>\n" +
                "            <City>Brooklyn Park</City>\n" +
                "            <State>MN</State>\n" +
                "            <PostalCode>55445</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>EDI Help Desk</ContactName>\n" +
                "                <PrimaryPhone>612-304-3310</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>SO</AddressTypeCode>\n" +
                "            <AddressName>EDI Help Desk</AddressName>\n" +
                "            <Address1>7000 Target Parkway, NCC-03620</Address1>\n" +
                "            <City>Brooklyn Park</City>\n" +
                "            <State>MN</State>\n" +
                "            <PostalCode>55445</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierAlphaCode>FDEG</CarrierAlphaCode>\n" +
                "            <CarrierRouting>NS</CarrierRouting>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>G2</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>CT</ReferenceQual>\n" +
                "            <ReferenceID>9859</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>OQ</ReferenceQual>\n" +
                "            <ReferenceID>1016085998540</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>D7</ReferenceQual>\n" +
                "            <ReferenceID>36</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>WS</ReferenceQual>\n" +
                "            <ReferenceID>D840</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>KK</ReferenceQual>\n" +
                "            <ReferenceID>N</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>L1</ReferenceQual>\n" +
                "            <ReferenceID>MESSAGE</ReferenceID>\n" +
                "            <Description>Thank you for your purchase.  If you ordered</Description>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>L1</ReferenceQual>\n" +
                "            <ReferenceID>MESSAGE</ReferenceID>\n" +
                "            <Description>additional items they will arrive separately.</Description>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>50428657</BuyerPartNumber>\n" +
                "                <VendorPartNumber>083622553529</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>083622553529</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>CB</PartNumberQual>\n" +
                "                    <PartNumber>042-00-0611</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>3</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>12.13</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>WR CARGO SHO 38 BLK SOLID</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Reference>\n" +
                "                <ReferenceQual>L1</ReferenceQual>\n" +
                "                <ReferenceID>Message</ReferenceID>\n" +
                "                <Description>This item must be returned within 90 days of</Description>\n" +
                "            </Reference>\n" +
                "            <Reference>\n" +
                "                <ReferenceQual>L1</ReferenceQual>\n" +
                "                <ReferenceID>Message</ReferenceID>\n" +
                "                <Description>the ship date.</Description>\n" +
                "            </Reference>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Mail In or Store</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>50428658</BuyerPartNumber>\n" +
                "                <VendorPartNumber>083622553530</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>083622553530</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>CB</PartNumberQual>\n" +
                "                    <PartNumber>042-00-0611</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>12.131</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>WR CARGO SHO 39 BLK SOLID</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <Reference>\n" +
                "                <ReferenceQual>L1</ReferenceQual>\n" +
                "                <ReferenceID>Message</ReferenceID>\n" +
                "                <Description>This item must be returned within 90 days of</Description>\n" +
                "            </Reference>\n" +
                "            <Reference>\n" +
                "                <ReferenceQual>L1</ReferenceQual>\n" +
                "                <ReferenceID>Message</ReferenceID>\n" +
                "                <Description>the ship date.</Description>\n" +
                "            </Reference>\n" +
                "            <Notes>\n" +
                "                <NoteInformationField>Mail In or Store</NoteInformationField>\n" +
                "            </Notes>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "        <TotalQuantity>7</TotalQuantity>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsTargetEDI edi = new JustBrandsTargetEDI();

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
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }

        @Test
        void test4shipOrderFromPoTarget() {
                System.setProperty("com.owd.environment", "test");

                String poData = "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "        <InterchangeControlNumber>000000001</InterchangeControlNumber>\n" +
                        "        <GroupControlNumber>1</GroupControlNumber>\n" +
                        "        <DocumentControlNumber>530002950</DocumentControlNumber>\n" +
                        "        <InterchangeSenderID>TGTDVS</InterchangeSenderID>\n" +
                        "        <InterchangeReceiverID>8884061984</InterchangeReceiverID>\n" +
                        "        <GroupSenderID>TGTDVS</GroupSenderID>\n" +
                        "        <GroupReceiverID>8884061984</GroupReceiverID>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>95OALLJUSTBRAND</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>1206327198</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2016-08-10</PurchaseOrderDate>\n" +
                        "            <Vendor>202886</Vendor>\n" +
                        "        </OrderHeader>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                        "            <Date1>2016-08-12</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>006</DateTimeQualifier1>\n" +
                        "            <Date1>2016-08-10</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>017</DateTimeQualifier1>\n" +
                        "            <Date1>2016-08-19</Date1>\n" +
                        "        </Date>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Target.com Accounts Payable</AddressName>\n" +
                        "            <Address1>TNC 3110</Address1>\n" +
                        "            <Address2>PO Box 1296</Address2>\n" +
                        "            <City>Minneapolis</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>55440</PostalCode>\n" +
                        "            <Country>US</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>EDI Help Desk</AddressName>\n" +
                        "            <Address1>7000 Target Parkway, NCC-03620</Address1>\n" +
                        "            <City>Brooklyn Park</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>55445</PostalCode>\n" +
                        "            <Country>US</Country>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                        "                <ContactName>EDI Help Desk</ContactName>\n" +
                        "                <PrimaryPhone>612-304-3310</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>SO</AddressTypeCode>\n" +
                        "            <AddressName>EDI Help Desk</AddressName>\n" +
                        "            <Address1>7000 Target Parkway, NCC-03620</Address1>\n" +
                        "            <City>Brooklyn Park</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>55445</PostalCode>\n" +
                        "            <Country>US</Country>\n" +
                        "        </Address>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierAlphaCode>UPSN</CarrierAlphaCode>\n" +
                        "            <CarrierRouting>NS</CarrierRouting>\n" +
                        "            <ServiceLevelCodes>\n" +
                        "                <ServiceLevelCode>G2</ServiceLevelCode>\n" +
                        "            </ServiceLevelCodes>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>CT</ReferenceQual>\n" +
                        "            <ReferenceID>9859</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>OQ</ReferenceQual>\n" +
                        "            <ReferenceID>1016085998540</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>D7</ReferenceQual>\n" +
                        "            <ReferenceID>36</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>WS</ReferenceQual>\n" +
                        "            <ReferenceID>D840</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>KK</ReferenceQual>\n" +
                        "            <ReferenceID>N</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>L1</ReferenceQual>\n" +
                        "            <ReferenceID>MESSAGE</ReferenceID>\n" +
                        "            <Description>Thank you for your purchase.  If you ordered</Description>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>L1</ReferenceQual>\n" +
                        "            <ReferenceID>MESSAGE</ReferenceID>\n" +
                        "            <Description>additional items they will arrive separately.</Description>\n" +
                        "        </Reference>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>50428657</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>083622553529</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>083622553529</ConsumerPackageCode>\n" +
                        "                <ProductID>\n" +
                        "                    <PartNumberQual>CB</PartNumberQual>\n" +
                        "                    <PartNumber>042-00-0611</PartNumber>\n" +
                        "                </ProductID>\n" +
                        "                <OrderQty>3</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>12.13</PurchasePrice>\n" +
                        "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                        "            </OrderLine>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                        "                <ProductDescription>WR CARGO SHO 38 BLK SOLID</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>L1</ReferenceQual>\n" +
                        "                <ReferenceID>Message</ReferenceID>\n" +
                        "                <Description>This item must be returned within 90 days of</Description>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>L1</ReferenceQual>\n" +
                        "                <ReferenceID>Message</ReferenceID>\n" +
                        "                <Description>the ship date.</Description>\n" +
                        "            </Reference>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Mail In or Store</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>50428658</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>083622553530</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>083622553530</ConsumerPackageCode>\n" +
                        "                <ProductID>\n" +
                        "                    <PartNumberQual>CB</PartNumberQual>\n" +
                        "                    <PartNumber>042-00-0611</PartNumber>\n" +
                        "                </ProductID>\n" +
                        "                <OrderQty>4</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>12.131</PurchasePrice>\n" +
                        "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                        "            </OrderLine>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                        "                <ProductDescription>WR CARGO SHO 39 BLK SOLID</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>L1</ReferenceQual>\n" +
                        "                <ReferenceID>Message</ReferenceID>\n" +
                        "                <Description>This item must be returned within 90 days of</Description>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>L1</ReferenceQual>\n" +
                        "                <ReferenceID>Message</ReferenceID>\n" +
                        "                <Description>the ship date.</Description>\n" +
                        "            </Reference>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Mail In or Store</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>7</TotalQuantity>\n" +
                        "    </Summary>\n" +
                        "</Order>"

                // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

                try {
                        JustBrandsTargetEDI edi = new JustBrandsTargetEDI();

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
                        ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                        // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


                } catch (Exception ex) {
                        ex.printStackTrace()
                }

        }




}

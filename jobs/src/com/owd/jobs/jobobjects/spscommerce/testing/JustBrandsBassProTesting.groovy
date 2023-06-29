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
import org.junit.Test

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by danny on 1/30/2017.
 */
class JustBrandsBassProTesting extends GroovyTestCase {



        @Test
        void testLoadingDropShip() {
                String poData = "<Order>\n" +
                        "  <Header>\n" +
                        "    <OrderHeader>\n" +
                        "      <TradingPartnerId>\n" +
                        "        563ALLJUSTBRAND\n" +
                        "      </TradingPartnerId>\n" +
                        "      <PurchaseOrderNumber>\n" +
                        "        7534059\n" +
                        "      </PurchaseOrderNumber>\n" +
                        "      <TsetPurposeCode>\n" +
                        "        00\n" +
                        "      </TsetPurposeCode>\n" +
                        "      <PurchaseOrderTypeCode>\n" +
                        "        SA\n" +
                        "      </PurchaseOrderTypeCode>\n" +
                        "      <PurchaseOrderDate>\n" +
                        "        2019-07-17\n" +
                        "      </PurchaseOrderDate>\n" +
                        "      <BuyersCurrency>\n" +
                        "        USD\n" +
                        "      </BuyersCurrency>\n" +
                        "      <Department>\n" +
                        "        AB1\n" +
                        "      </Department>\n" +
                        "      <Vendor>\n" +
                        "        225332\n" +
                        "      </Vendor>\n" +
                        "    </OrderHeader>\n" +
                        "    <PaymentTerms>\n" +
                        "      <TermsType>\n" +
                        "        14\n" +
                        "      </TermsType>\n" +
                        "      <TermsBasisDateCode>\n" +
                        "        15\n" +
                        "      </TermsBasisDateCode>\n" +
                        "      <TermsDescription>\n" +
                        "        2% 15 NET 60\n" +
                        "      </TermsDescription>\n" +
                        "    </PaymentTerms>\n" +
                        "    <Date>\n" +
                        "      <DateTimeQualifier1>\n" +
                        "        010\n" +
                        "      </DateTimeQualifier1>\n" +
                        "      <Date1>\n" +
                        "        2019-07-22\n" +
                        "      </Date1>\n" +
                        "    </Date>\n" +
                        "    <Date>\n" +
                        "      <DateTimeQualifier1>\n" +
                        "        001\n" +
                        "      </DateTimeQualifier1>\n" +
                        "      <Date1>\n" +
                        "        2019-07-22\n" +
                        "      </Date1>\n" +
                        "    </Date>\n" +
                        "    <Date>\n" +
                        "      <DateTimeQualifier1>\n" +
                        "        002\n" +
                        "      </DateTimeQualifier1>\n" +
                        "      <Date1>\n" +
                        "        2019-07-22\n" +
                        "      </Date1>\n" +
                        "    </Date>\n" +
                        "    <Contact>\n" +
                        "      <ContactTypeCode>\n" +
                        "        OC\n" +
                        "      </ContactTypeCode>\n" +
                        "      <ContactName>\n" +
                        "        Drop Ship Cust Serv\n" +
                        "      </ContactName>\n" +
                        "      <PrimaryPhone>\n" +
                        "        4178735000\n" +
                        "      </PrimaryPhone>\n" +
                        "    </Contact>\n" +
                        "    <Address>\n" +
                        "      <AddressTypeCode>\n" +
                        "        ST\n" +
                        "      </AddressTypeCode>\n" +
                        "      <LocationCodeQualifier>\n" +
                        "        92\n" +
                        "      </LocationCodeQualifier>\n" +
                        "      <AddressLocationNumber>\n" +
                        "        DR\n" +
                        "      </AddressLocationNumber>\n" +
                        "      <AddressName>\n" +
                        "        MICHAEL     LANG\n" +
                        "      </AddressName>\n" +
                        "      <Address1>\n" +
                        "        1228 30TH ST NW APT 306\n" +
                        "      </Address1>\n" +
                        "      <City>\n" +
                        "        BEMIDJI\n" +
                        "      </City>\n" +
                        "      <State>\n" +
                        "        MN\n" +
                        "      </State>\n" +
                        "      <PostalCode>\n" +
                        "        56601\n" +
                        "      </PostalCode>\n" +
                        "      <Country>\n" +
                        "        US\n" +
                        "      </Country>\n" +
                        "      <Contact>\n" +
                        "        <ContactTypeCode>\n" +
                        "          IC\n" +
                        "        </ContactTypeCode>\n" +
                        "        <PrimaryPhone>\n" +
                        "          6308812649\n" +
                        "        </PrimaryPhone>\n" +
                        "      </Contact>\n" +
                        "    </Address>\n" +
                        "    <FOBRelatedInstruction>\n" +
                        "      <FOBPayCode>\n" +
                        "        DF\n" +
                        "      </FOBPayCode>\n" +
                        "      <FOBLocationQualifier>\n" +
                        "        DE\n" +
                        "      </FOBLocationQualifier>\n" +
                        "    </FOBRelatedInstruction>\n" +
                        "    <CarrierInformation>\n" +
                        "      <CarrierRouting>\n" +
                        "        Vendor Guide\n" +
                        "      </CarrierRouting>\n" +
                        "    </CarrierInformation>\n" +
                        "    <Reference>\n" +
                        "      <ReferenceQual>\n" +
                        "        AH\n" +
                        "      </ReferenceQual>\n" +
                        "      <ReferenceID>\n" +
                        "        PO NOTE\n" +
                        "      </ReferenceID>\n" +
                        "    </Reference>\n" +
                        "    <Notes>\n" +
                        "      <NoteInformationField>\n" +
                        "        DROP SHIP\n" +
                        "      </NoteInformationField>\n" +
                        "    </Notes>\n" +
                        "    <Notes>\n" +
                        "      <NoteInformationField>\n" +
                        "        SOLD TO: MICHAEL      LANG\n" +
                        "      </NoteInformationField>\n" +
                        "    </Notes>\n" +
                        "    <Notes>\n" +
                        "      <NoteInformationField>\n" +
                        "        07/16/19      ORDER NUMBER  4983009 0003\n" +
                        "      </NoteInformationField>\n" +
                        "    </Notes>\n" +
                        "    <ChargesAllowances>\n" +
                        "      <AllowChrgIndicator>\n" +
                        "        A\n" +
                        "      </AllowChrgIndicator>\n" +
                        "      <AllowChrgCode>\n" +
                        "        A260\n" +
                        "      </AllowChrgCode>\n" +
                        "      <AllowChrgPercentQual>\n" +
                        "        3\n" +
                        "      </AllowChrgPercentQual>\n" +
                        "      <AllowChrgPercent>\n" +
                        "        3\n" +
                        "      </AllowChrgPercent>\n" +
                        "      <AllowChrgHandlingCode>\n" +
                        "        02\n" +
                        "      </AllowChrgHandlingCode>\n" +
                        "    </ChargesAllowances>\n" +
                        "    <ChargesAllowances>\n" +
                        "      <AllowChrgIndicator>\n" +
                        "        A\n" +
                        "      </AllowChrgIndicator>\n" +
                        "      <AllowChrgCode>\n" +
                        "        C000\n" +
                        "      </AllowChrgCode>\n" +
                        "      <AllowChrgPercentQual>\n" +
                        "        3\n" +
                        "      </AllowChrgPercentQual>\n" +
                        "      <AllowChrgPercent>\n" +
                        "        1\n" +
                        "      </AllowChrgPercent>\n" +
                        "      <AllowChrgHandlingCode>\n" +
                        "        02\n" +
                        "      </AllowChrgHandlingCode>\n" +
                        "    </ChargesAllowances>\n" +
                        "  </Header>\n" +
                        "  <LineItems>\n" +
                        "    <LineItem>\n" +
                        "      <OrderLine>\n" +
                        "        <BuyerPartNumber>\n" +
                        "          2727758\n" +
                        "        </BuyerPartNumber>\n" +
                        "        <VendorPartNumber>\n" +
                        "          AH-MIT-5V-M-L-X\n" +
                        "        </VendorPartNumber>\n" +
                        "        <ConsumerPackageCode>\n" +
                        "          675595810367\n" +
                        "        </ConsumerPackageCode>\n" +
                        "        <ProductID>\n" +
                        "          <PartNumberQual>\n" +
                        "            SZ\n" +
                        "          </PartNumberQual>\n" +
                        "          <PartNumber>\n" +
                        "            L/XL\n" +
                        "          </PartNumber>\n" +
                        "        </ProductID>\n" +
                        "        <ProductID>\n" +
                        "          <PartNumberQual>\n" +
                        "            CL\n" +
                        "          </PartNumberQual>\n" +
                        "          <PartNumber>\n" +
                        "            BLACK\n" +
                        "          </PartNumber>\n" +
                        "        </ProductID>\n" +
                        "        <ProductID>\n" +
                        "          <PartNumberQual>\n" +
                        "            TP\n" +
                        "          </PartNumberQual>\n" +
                        "          <PartNumber>\n" +
                        "            628\n" +
                        "          </PartNumber>\n" +
                        "        </ProductID>\n" +
                        "        <OrderQty>\n" +
                        "          1\n" +
                        "        </OrderQty>\n" +
                        "        <OrderQtyUOM>\n" +
                        "          EA\n" +
                        "        </OrderQtyUOM>\n" +
                        "        <PurchasePrice>\n" +
                        "          65\n" +
                        "        </PurchasePrice>\n" +
                        "      </OrderLine>\n" +
                        "      <ProductOrItemDescription>\n" +
                        "        <ItemDescriptionType>\n" +
                        "          08\n" +
                        "        </ItemDescriptionType>\n" +
                        "        <ProductDescription>\n" +
                        "          ACTIONHEAT/BLACK/LXL/\n" +
                        "        </ProductDescription>\n" +
                        "      </ProductOrItemDescription>\n" +
                        "      <PhysicalDetails>\n" +
                        "        <PackQualifier>\n" +
                        "          OU\n" +
                        "        </PackQualifier>\n" +
                        "        <PackValue>\n" +
                        "          20\n" +
                        "        </PackValue>\n" +
                        "      </PhysicalDetails>\n" +
                        "      <PhysicalDetails>\n" +
                        "        <PackQualifier>\n" +
                        "          IN\n" +
                        "        </PackQualifier>\n" +
                        "        <PackValue>\n" +
                        "          1\n" +
                        "        </PackValue>\n" +
                        "      </PhysicalDetails>\n" +
                        "    </LineItem>\n" +
                        "  </LineItems>\n" +
                        "  <Summary>\n" +
                        "    <TotalLineItemNumber>\n" +
                        "      1\n" +
                        "    </TotalLineItemNumber>\n" +
                        "    <TotalQuantity>\n" +
                        "      1\n" +
                        "    </TotalQuantity>\n" +
                        "  </Summary>\n" +
                        "</Order>"

                // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

                try {
                        JustBrandsBassProEDI edi = new JustBrandsBassProEDI();

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



         String poData = new String(Files.readAllBytes(Paths.get("src\\com\\owd\\jobs\\jobobjects\\spscommerce\\testing\\files","bassProWarehouse.xml")))

        try {
            JustBrandsBassProEDI edi = new JustBrandsBassProEDI();

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
          /*  if (order.shipMethodName.contains("FedEx")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000", false);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000", false);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211", false);
            }*/

            //we have a shipped order, now create the ASN file for it


          //  String asnData = edi.generateASN(Integer.parseInt(order.orderID), config.clientFkey)

         //   println asnData
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
          //  SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
          //  ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
          //   ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }

    @Test
    void test2shipOrderFromPoTarget() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "        <InterchangeControlNumber>000000001</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>1</GroupControlNumber>\n" +
                "        <DocumentControlNumber>530002951</DocumentControlNumber>\n" +
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

                try {
                    JustBrandsBassProEDI edi = new JustBrandsBassProEDI();
                    String asn = edi.generateASN(21999393, 626)

                    println(asn)
                } catch (Exception ex) {
                        ex.printStackTrace()
                }

        }




}

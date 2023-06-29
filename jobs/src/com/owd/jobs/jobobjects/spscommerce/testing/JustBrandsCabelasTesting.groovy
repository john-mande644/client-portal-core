package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderFactory
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.EdiDocs
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.spscommerce.JustBrandsCabelasEDI
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceDicksSportingGoodsUtilities
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsCabelasEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class JustBrandsCabelasTesting extends GroovyTestCase{

        @Test
        void viewASN(){
                println("hello");
                String asnData = SpsCommerceUtilities.generateASN(12052072,471);

                println asnData;
        }
        @Test
        void testLoadPendingASns(){
                System.setProperty("com.owd.environment","test");
                SPSCommerceBaseClient.processPendingPos();

        }
        @Test
        void testLoadAccount(){
                try{

                        System.setProperty("com.owd.environment","test");
                        List<EdiDocs> docsToProcess = HibernateSession.currentSession().createQuery("from EdiDocs where docStatus=2 and account<>''").list()
                        println(docsToProcess.size());

                }catch (Exception e){
                        e.printStackTrace()
                }
        }

        @Test
        void testGenerateAsn(){

                SPSCommerceBaseClient.submitASN(16823197,626)
                SPSCommerceBaseClient.submitASN(16823198,626)
                SPSCommerceBaseClient.submitASN(16823199,626)
                SPSCommerceBaseClient.submitASN(16823200,626)
                SPSCommerceBaseClient.submitASN(16824828,626)
                SPSCommerceBaseClient.submitASN(16824829,626)
                SPSCommerceBaseClient.submitASN(16824830,626)


        }

        @Test
        void testAcknowledgement(){


                List<Integer> l = new ArrayList<>();
                l.add(16823197);
                l.add(16823198);
                l.add(16823199);
                l.add(16823200);
                l.add(16824828);
                l.add(16824829);
                l.add(16824830);
                l.add(16845252);
                l.add(16845253);
                l.add(16845254);
                l.add(16845255);
                l.add(16845256);
                l.add(16845257);
                l.add(16845258);
                l.add(16845259);
                l.add(16845260);
                l.add(16845261);
                l.add(16845262);
                l.add(16845263);
                l.add(16845264);
                l.add(16845265);
                l.add(16845266);


                for(Integer i: l) {
                        try {
                                OwdOrder order = OrderFactory.getOwdOrderFromOrderID(i, 626, true)
                                Map<String, String> tagMap = TagUtilities.getTagMap("ORDER", order.getOrderId())



                                println "asn tagmap: " + tagMap
                                String poNode = tagMap.get(TagUtilities.kEDIPurchaseOrder.toUpperCase())
                                XmlParser parser = new XmlParser()
                                parser.setTrimWhitespace(true)


                                def poData = parser.parseText(poNode)
                                JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                                SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                                EdiSpsConfigdata configdata = edi.getEdiConfigData(poData)

                                String ack = edi.generateACK(configdata, poData, 626, order);

                                println(ack)
                                ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)
                                edi.addTagToOrder(order.orderId+"", TagUtilities.kEDIPurchaseOrderAcknowledgement, ack);

                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }




        }


        @Test
        void testImportPOWithAccount() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>093ALLKADENASPO</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>6269590_TEST</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2018-10-08</PurchaseOrderDate>\n" +
                        "            <ShipCompleteCode>AI</ShipCompleteCode>\n" +
                        "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                        "            <ExchangeRate>0</ExchangeRate>\n" +
                        "            <DepartmentDescription>MENS APPAREL</DepartmentDescription>\n" +
                        "            <Vendor>102711</Vendor>\n" +
                        "            <Division>001</Division>\n" +
                        "            <CustomerOrderNumber>00252609165</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                        "            <TermsDescription>NET 60 DAYS</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-10</Date1>\n" +
                        "        </Date>\n" +
                        "        <Contact>\n" +
                        "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "            <PrimaryPhone>(218) 850-8953</PrimaryPhone>\n" +
                        "        </Contact>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Cabela's LLC Accounts Payable</AddressName>\n" +
                        "            <Address1>One Cabela Drive</Address1>\n" +
                        "            <City>Sidney</City>\n" +
                        "            <State>NE</State>\n" +
                        "            <PostalCode>691608888</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                        "            <AddressName>JUSTBRAND LTD-WARMING STORE-(DS)</AddressName>\n" +
                        "            <Address1>3791 MAIN ST</Address1>\n" +
                        "            <City>PHILADELPHIA</City>\n" +
                        "            <State>PA</State>\n" +
                        "            <PostalCode>19127</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>KELVIN WOLD</AddressName>\n" +
                        "            <Address1>17405 W LONG LAKE RD</Address1>\n" +
                        "            <City>DETROIT LAKES</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>56501-0000</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "            <Contact>\n" +
                        "                <PrimaryPhone>(218) 850-8953</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                        "                <PrimaryPhone>(218) 850-8953</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>SO</AddressTypeCode>\n" +
                        "            <AddressName>KELVIN WOLD</AddressName>\n" +
                        "            <Address1>17405 W LONG LAKE RD</Address1>\n" +
                        "            <City>DETROIT LAKES</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>56501-7931</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "            <Contact>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>CC</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierRouting>BEST WAY</CarrierRouting>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>JD</ReferenceQual>\n" +
                        "            <ReferenceID>1271</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>YD</ReferenceQual>\n" +
                        "            <ReferenceID>2818</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04858418</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AHVST5V1-BM-L</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807718</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807718</GTIN>\n" +
                        "                <ProductID>\n" +
                        "                    <PartNumberQual>RS</PartNumberQual>\n" +
                        "                    <PartNumber>986646</PartNumber>\n" +
                        "                </ProductID>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>81</PurchasePrice>\n" +
                        "                <ProductSizeDescription>LARGE</ProductSizeDescription>\n" +
                        "                <ProductColorDescription>BLACK</ProductColorDescription>\n" +
                        "                <Department>2</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-10</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>179.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ACTIONHEAT 5V HTD VEST/BLK/L</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>THE WARMING STORE ACTION HEAT 5V HEATED VEST</ProductDescription>\n" +
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
                        "        <TotalAmount>81</TotalAmount>\n" +
                        "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>1</TotalQuantity>\n" +
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
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        String account = po.Header.OrderHeader.TradingPartnerId.text()
                        int docId = JustBrandsCabelasEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml",account)
                        assertTrue(docId>0);

                        println(docId);





                    /*    EdiSpsConfigdata config = JustBrandsCabelasEDI.getEdiConfigData(po)
                        Order order = JustBrandsCabelasEDI.importPo(po, config.clientFkey)
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
                        *//*   order.facilityCode='DC1'
                           order.facilityPolicy='DC1'
                           order.setThirdPartyBilling("9V3W92");
                           order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
               *//*

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

                        JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                        String asnData = edi.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

                        println asnData
                        //  println("This is after the print asn")
                        //   println "loading owd order";
                        //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
                        //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                        //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
                        //   println ack
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");


                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                        ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                        // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)

*/
                }catch (Exception ex)
                {
                        ex.printStackTrace()
                }

        }
        @Test
        void test1shipOrderFromPoDicksGildan() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>093ALLKADENASPO</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>6269590_TEST</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2018-10-08</PurchaseOrderDate>\n" +
                        "            <ShipCompleteCode>AI</ShipCompleteCode>\n" +
                        "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                        "            <ExchangeRate>0</ExchangeRate>\n" +
                        "            <DepartmentDescription>MENS APPAREL</DepartmentDescription>\n" +
                        "            <Vendor>102711</Vendor>\n" +
                        "            <Division>001</Division>\n" +
                        "            <CustomerOrderNumber>00252609165</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                        "            <TermsDescription>NET 60 DAYS</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-10</Date1>\n" +
                        "        </Date>\n" +
                        "        <Contact>\n" +
                        "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "            <PrimaryPhone>(218) 850-8953</PrimaryPhone>\n" +
                        "        </Contact>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Cabela's LLC Accounts Payable</AddressName>\n" +
                        "            <Address1>One Cabela Drive</Address1>\n" +
                        "            <City>Sidney</City>\n" +
                        "            <State>NE</State>\n" +
                        "            <PostalCode>691608888</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                        "            <AddressName>JUSTBRAND LTD-WARMING STORE-(DS)</AddressName>\n" +
                        "            <Address1>3791 MAIN ST</Address1>\n" +
                        "            <City>PHILADELPHIA</City>\n" +
                        "            <State>PA</State>\n" +
                        "            <PostalCode>19127</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>KELVIN WOLD</AddressName>\n" +
                        "            <Address1>17405 W LONG LAKE RD</Address1>\n" +
                        "            <City>DETROIT LAKES</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>56501-0000</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "            <Contact>\n" +
                        "                <PrimaryPhone>(218) 850-8953</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                        "                <PrimaryPhone>(218) 850-8953</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>SO</AddressTypeCode>\n" +
                        "            <AddressName>KELVIN WOLD</AddressName>\n" +
                        "            <Address1>17405 W LONG LAKE RD</Address1>\n" +
                        "            <City>DETROIT LAKES</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>56501-7931</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "            <Contact>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>CC</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierRouting>BEST WAY</CarrierRouting>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>JD</ReferenceQual>\n" +
                        "            <ReferenceID>1271</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>YD</ReferenceQual>\n" +
                        "            <ReferenceID>2818</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04858418</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AHVST5V1-BM-L</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807718</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807718</GTIN>\n" +
                        "                <ProductID>\n" +
                        "                    <PartNumberQual>RS</PartNumberQual>\n" +
                        "                    <PartNumber>986646</PartNumber>\n" +
                        "                </ProductID>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>81</PurchasePrice>\n" +
                        "                <ProductSizeDescription>LARGE</ProductSizeDescription>\n" +
                        "                <ProductColorDescription>BLACK</ProductColorDescription>\n" +
                        "                <Department>2</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-10</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>179.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ACTIONHEAT 5V HTD VEST/BLK/L</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>THE WARMING STORE ACTION HEAT 5V HEATED VEST</ProductDescription>\n" +
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
                        "        <TotalAmount>81</TotalAmount>\n" +
                        "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>1</TotalQuantity>\n" +
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
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        String account = po.Header.OrderHeader.TradingPartnerId.text()
                        int docId = JustBrandsCabelasEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml",account)
                        JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                        EdiSpsConfigdata config = JustBrandsCabelasEDI.getEdiConfigData(po)
                        Order order = edi.importPo(po, config.clientFkey)
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
                        /*   order.facilityCode='DC1'
                           order.facilityPolicy='DC1'
                           order.setThirdPartyBilling("9V3W92");
                           order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
               */

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


                      //  String asnData = edi.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

                       // println asnData
                        //  println("This is after the print asn")
                        //   println "loading owd order";
                            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
                        //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                          String ack = edi.generateACK(config,po,471,oorder)
                           println ack
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");


                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                       //  ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                         ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


                }catch (Exception ex)
                {
                        ex.printStackTrace()
                }

        }
        @Test
        void test2shipOrderCabelasWarehouse() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>093ALLKADENASPO</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>778_TEST</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2018-10-18</PurchaseOrderDate>\n" +
                        "            <Vendor>102711</Vendor>\n" +
                        "            <Division>004</Division>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                        "            <TermsDescription>Net 60 Days</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Contact>\n" +
                        "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "            <ContactName>Contact Name</ContactName>\n" +
                        "            <PrimaryPhone>(123) 456-7890</PrimaryPhone>\n" +
                        "        </Contact>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Cabela's Inc. Accounts Payable</AddressName>\n" +
                        "            <Address1>One Cabela Drive</Address1>\n" +
                        "            <City>Sidney</City>\n" +
                        "            <State>NE</State>\n" +
                        "            <PostalCode>691608888</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                        "            <AddressLocationNumber>801</AddressLocationNumber>\n" +
                        "            <AddressName>Cabela's Inc.</AddressName>\n" +
                        "            <Address1>3200 Road 101</Address1>\n" +
                        "            <City>Sidney</City>\n" +
                        "            <State>NE</State>\n" +
                        "            <PostalCode>69160-0001</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                        "            <AddressName>JUSTBRAND LTD-WARMING STORE-(DS)</AddressName>\n" +
                        "            <Address1>3791 MAIN ST</Address1>\n" +
                        "            <City>PHILADELPHIA</City>\n" +
                        "            <State>PA</State>\n" +
                        "            <PostalCode>19127</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>CC</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                        "            <FOBLocationDescription>Point of Origin</FOBLocationDescription>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                        "            <CarrierAlphaCode>SCAC</CarrierAlphaCode>\n" +
                        "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                        "            <ServiceLevelCodes>\n" +
                        "                <ServiceLevelCode>PB</ServiceLevelCode>\n" +
                        "            </ServiceLevelCodes>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Notes>\n" +
                        "            <NoteInformationField>Header note sent here.</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <ChargesAllowances>\n" +
                        "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                        "            <AllowChrgCode>A260</AllowChrgCode>\n" +
                        "            <AllowChrgAmt>308</AllowChrgAmt>\n" +
                        "            <AllowChrgPercentQual>7</AllowChrgPercentQual>\n" +
                        "            <AllowChrgPercent>3</AllowChrgPercent>\n" +
                        "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                        "            <AllowChrgHandlingDescription>Ad Allowance</AllowChrgHandlingDescription>\n" +
                        "        </ChargesAllowances>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04858418</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AHVST5V1-BM-L</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807718</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807718</GTIN>\n" +
                        "                <OrderQty>7</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>81</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>179.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION1</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717863</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-L-X</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807459</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807459</GTIN>\n" +
                        "                <OrderQty>3</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>21.5</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION2</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717864</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-XXL</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807466</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807466</GTIN>\n" +
                        "                <OrderQty>4</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION3</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalAmount>711.5</TotalAmount>\n" +
                        "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>14</TotalQuantity>\n" +
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
                        int docId = JustBrandsCabelasEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        EdiSpsConfigdata config = JustBrandsCabelasEDI.getEdiConfigData(po)
                        Order order = JustBrandsCabelasEDI.importPo(po, config.clientFkey)
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
                        /*   order.facilityCode='DC1'
                           order.facilityPolicy='DC1'
                           order.setThirdPartyBilling("9V3W92");
                           order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
               */

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
                        JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                        String asnData = edi.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

                        println asnData
                        //  println("This is after the print asn")
                        //   println "loading owd order";
                        //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
                        //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                        //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
                        //   println ack
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");


                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                         ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                        // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


                }catch (Exception ex)
                {
                        ex.printStackTrace()
                }

        }
    @Test
    void test3shipOrderCabelasWarehouse() {
        System.setProperty("com.owd.environment","test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>093ALLKADENASPO</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>5818036_TEST</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2018-03-27</PurchaseOrderDate>\n" +
                "            <ShipCompleteCode>AI</ShipCompleteCode>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <ExchangeRate>0</ExchangeRate>\n" +
                "            <DepartmentDescription>FOOTWEAR</DepartmentDescription>\n" +
                "            <Vendor>102711</Vendor>\n" +
                "            <Division>001</Division>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                "            <TermsDiscountPercentage>2</TermsDiscountPercentage>\n" +
                "            <TermsDiscountDueDays>60</TermsDiscountDueDays>\n" +
                "            <TermsDescription>2% 60 NET 61</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                "            <Date1>2018-09-19</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2018-09-19</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "            <Date1>2018-09-19</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>Jennifer.douglas</ContactName>\n" +
                "            <PrimaryPhone>308-255-2466</PrimaryPhone>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>804</AddressLocationNumber>\n" +
                "            <AddressName>Cabela's Inc.</AddressName>\n" +
                "            <Address1>501 Cliffhaven Rd.</Address1>\n" +
                "            <City>Prairie Du Chien</City>\n" +
                "            <State>WI</State>\n" +
                "            <PostalCode>53821-1130</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Cabela's LLC Accounts Payable</AddressName>\n" +
                "            <Address1>One Cabela Drive</Address1>\n" +
                "            <City>Sidney</City>\n" +
                "            <State>NE</State>\n" +
                "            <PostalCode>691608888</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>WARMING STORE</AddressName>\n" +
                "            <Address1>3791 MAIN ST</Address1>\n" +
                "            <City>PHILADELPHIA</City>\n" +
                "            <State>PA</State>\n" +
                "            <PostalCode>19127</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>JD</ReferenceQual>\n" +
                "            <ReferenceID>2466</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <ChargesAllowances>\n" +
                "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                "            <AllowChrgCode>SUIP</AllowChrgCode>\n" +
                "            <AllowChrgAmt>527.04</AllowChrgAmt>\n" +
                "            <AllowChrgPercentQual>7</AllowChrgPercentQual>\n" +
                "            <AllowChrgPercent>3</AllowChrgPercent>\n" +
                "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                "            <AllowChrgHandlingDescription>MARKETING FUNDS</AllowChrgHandlingDescription>\n" +
                "        </ChargesAllowances>\n" +
                "        <ChargesAllowances>\n" +
                "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                "            <AllowChrgCode>SUIP</AllowChrgCode>\n" +
                "            <AllowChrgAmt>170.41</AllowChrgAmt>\n" +
                "            <AllowChrgPercentQual>7</AllowChrgPercentQual>\n" +
                "            <AllowChrgPercent>1</AllowChrgPercent>\n" +
                "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                "            <AllowChrgHandlingDescription>RETURNS ALLOWANCE</AllowChrgHandlingDescription>\n" +
                "        </ChargesAllowances>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <BuyerPartNumber>04717850</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-LI-02-S-M</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807350</ConsumerPackageCode>\n" +
                "                <GTIN>00675595807350</GTIN>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>RS</PartNumberQual>\n" +
                "                    <PartNumber>833171</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>36</OrderQty>\n" +
                "                <OrderQtyUOM>PR</OrderQtyUOM>\n" +
                "                <PurchasePrice>48</PurchasePrice>\n" +
                "                <ProductSizeDescription>SMALL/MEDIUM</ProductSizeDescription>\n" +
                "                <ProductColorDescription>BLACK</ProductColorDescription>\n" +
                "                <Department>6</Department>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>119.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>ACTIONHEAT COTTON RECH-BL-S/M</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>ACTIONHEAT COTTON RECHARCHABLE HEATED SOCKS*DS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>36</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>IN</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <BuyerPartNumber>04717851</BuyerPartNumber>\n" +
                "                <VendorPartNumber>AH-SK-LI-02-L-X</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>675595807367</ConsumerPackageCode>\n" +
                "                <GTIN>00675595807367</GTIN>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>RS</PartNumberQual>\n" +
                "                    <PartNumber>833171</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>36</OrderQty>\n" +
                "                <OrderQtyUOM>PR</OrderQtyUOM>\n" +
                "                <PurchasePrice>48</PurchasePrice>\n" +
                "                <ProductSizeDescription>LARGE/X-LARGE</ProductSizeDescription>\n" +
                "                <ProductColorDescription>BLACK</ProductColorDescription>\n" +
                "                <Department>6</Department>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>119.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>ACTIONHEAT COTTON RECH-BL-L/XL</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>ACTIONHEAT COTTON RECHARCHABLE HEATED SOCKS*DS</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>OU</PackQualifier>\n" +
                "                <PackValue>36</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "            <PhysicalDetails>\n" +
                "                <PackQualifier>IN</PackQualifier>\n" +
                "                <PackValue>1</PackValue>\n" +
                "            </PhysicalDetails>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalAmount>16870.55</TotalAmount>\n" +
                "        <TotalLineItemNumber>13</TotalLineItemNumber>\n" +
                "        <TotalQuantity>468</TotalQuantity>\n" +
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
            int docId = JustBrandsCabelasEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = JustBrandsCabelasEDI.getEdiConfigData(po)
            Order order = JustBrandsCabelasEDI.importPo(po, config.clientFkey)
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
            /*   order.facilityCode='DC1'
               order.facilityPolicy='DC1'
               order.setThirdPartyBilling("9V3W92");
               order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
   */

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

            String asnData = JustBrandsCabelasEDI.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

            println asnData
            //  println("This is after the print asn")
            //   println "loading owd order";
            //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            //   println ack
                SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");

                //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
             ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }
        @Test
        void test2shipOrderFromPoCabelas() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>093ALLKADENASPO</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>445_TEST</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2018-10-18</PurchaseOrderDate>\n" +
                        "            <Vendor>102711</Vendor>\n" +
                        "            <Division>004</Division>\n" +
                        "            <CustomerOrderNumber>00338833488</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                        "            <TermsDescription>Net 60 Days</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Contact>\n" +
                        "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "            <ContactName>Contact Name</ContactName>\n" +
                        "            <PrimaryPhone>(123) 456-7890</PrimaryPhone>\n" +
                        "        </Contact>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Cabela's Inc. Accounts Payable</AddressName>\n" +
                        "            <Address1>One Cabela Drive</Address1>\n" +
                        "            <City>Sidney</City>\n" +
                        "            <State>NE</State>\n" +
                        "            <PostalCode>691608888</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>John Doe</AddressName>\n" +
                        "            <Address1>123 Water St</Address1>\n" +
                        "            <City>Eau Clair</City>\n" +
                        "            <State>WI</State>\n" +
                        "            <PostalCode>54703</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                        "            <AddressName>JUSTBRAND LTD-WARMING STORE-(DS)</AddressName>\n" +
                        "            <Address1>3791 MAIN ST</Address1>\n" +
                        "            <City>PHILADELPHIA</City>\n" +
                        "            <State>PA</State>\n" +
                        "            <PostalCode>19127</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>CC</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                        "            <FOBLocationDescription>Point of Origin</FOBLocationDescription>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                        "            <CarrierAlphaCode>SCAC</CarrierAlphaCode>\n" +
                        "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                        "            <ServiceLevelCodes>\n" +
                        "                <ServiceLevelCode>PB</ServiceLevelCode>\n" +
                        "            </ServiceLevelCodes>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Notes>\n" +
                        "            <NoteInformationField>Header note sent here.</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <ChargesAllowances>\n" +
                        "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                        "            <AllowChrgCode>A260</AllowChrgCode>\n" +
                        "            <AllowChrgAmt>308</AllowChrgAmt>\n" +
                        "            <AllowChrgPercentQual>7</AllowChrgPercentQual>\n" +
                        "            <AllowChrgPercent>3</AllowChrgPercent>\n" +
                        "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                        "            <AllowChrgHandlingDescription>Ad Allowance</AllowChrgHandlingDescription>\n" +
                        "        </ChargesAllowances>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04858418</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AHVST5V1-BM-L</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807718</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807718</GTIN>\n" +
                        "                <OrderQty>2</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>81</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>179.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION1</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717863</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-L-X</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807459</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807459</GTIN>\n" +
                        "                <OrderQty>3</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION2</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717864</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-XXL</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807466</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807466</GTIN>\n" +
                        "                <OrderQty>4</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION3</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalAmount>302</TotalAmount>\n" +
                        "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>9</TotalQuantity>\n" +
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
                        int docId = JustBrandsCabelasEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        EdiSpsConfigdata config = JustBrandsCabelasEDI.getEdiConfigData(po)
                        Order order = JustBrandsCabelasEDI.importPo(po, config.clientFkey)
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
                        /*   order.facilityCode='DC1'
                           order.facilityPolicy='DC1'
                           order.setThirdPartyBilling("9V3W92");
                           order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
               */

                        // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
                        // but for testing this doesn't work so we will simulate what happens to the order in that process.
                        // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
                        //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
                        String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                        println reference

                        // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                        if(order.shipMethodName.contains("FedEx")) {
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000",false);
                        }  else if (order.shipMethodName.contains("UPS")){
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000",false);
                        }else{
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211",false);
                        }

                        //we have a shipped order, now create the ASN file for it
                        JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                        String asnData = edi.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

                        println asnData
                        //  println("This is after the print asn")
                        //   println "loading owd order";
                        //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
                        //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                        //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
                        //   println ack
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");

                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                         ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                        // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


                }catch (Exception ex)
                {
                        ex.printStackTrace()
                }

        }

        @Test
        void test3shipOrderFromPoCabelas() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>093ALLKADENASPO</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>112_TEST</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2018-10-18</PurchaseOrderDate>\n" +
                        "            <Vendor>102711</Vendor>\n" +
                        "            <Division>004</Division>\n" +
                        "            <CustomerOrderNumber>00229922599</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                        "            <TermsDescription>Net 60 Days</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Contact>\n" +
                        "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "            <ContactName>Contact Name</ContactName>\n" +
                        "            <PrimaryPhone>(123) 456-7890</PrimaryPhone>\n" +
                        "        </Contact>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Cabela's Inc. Accounts Payable</AddressName>\n" +
                        "            <Address1>One Cabela Drive</Address1>\n" +
                        "            <City>Sidney</City>\n" +
                        "            <State>NE</State>\n" +
                        "            <PostalCode>691608888</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>Jane Doe</AddressName>\n" +
                        "            <Address1>333 S 7th St</Address1>\n" +
                        "            <City>Minneapolis</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>55402</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                        "            <AddressName>JUSTBRAND LTD-WARMING STORE-(DS)</AddressName>\n" +
                        "            <Address1>3791 MAIN ST</Address1>\n" +
                        "            <City>PHILADELPHIA</City>\n" +
                        "            <State>PA</State>\n" +
                        "            <PostalCode>19127</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>CC</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                        "            <FOBLocationDescription>Point of Origin</FOBLocationDescription>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                        "            <CarrierAlphaCode>SCAC</CarrierAlphaCode>\n" +
                        "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                        "            <ServiceLevelCodes>\n" +
                        "                <ServiceLevelCode>PB</ServiceLevelCode>\n" +
                        "            </ServiceLevelCodes>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Notes>\n" +
                        "            <NoteInformationField>Header note sent here.</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <ChargesAllowances>\n" +
                        "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                        "            <AllowChrgCode>A260</AllowChrgCode>\n" +
                        "            <AllowChrgAmt>308</AllowChrgAmt>\n" +
                        "            <AllowChrgPercentQual>7</AllowChrgPercentQual>\n" +
                        "            <AllowChrgPercent>3</AllowChrgPercent>\n" +
                        "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                        "            <AllowChrgHandlingDescription>Ad Allowance</AllowChrgHandlingDescription>\n" +
                        "        </ChargesAllowances>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04858418</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AHVST5V1-BM-L</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807718</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807718</GTIN>\n" +
                        "                <OrderQty>2</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>81</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>179.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION1</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717863</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-L-X</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807459</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807459</GTIN>\n" +
                        "                <OrderQty>3</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION2</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717864</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-XXL</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807466</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807466</GTIN>\n" +
                        "                <OrderQty>4</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION3</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalAmount>302</TotalAmount>\n" +
                        "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>9</TotalQuantity>\n" +
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
                        int docId = JustBrandsCabelasEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        EdiSpsConfigdata config = JustBrandsCabelasEDI.getEdiConfigData(po)
                        Order order = JustBrandsCabelasEDI.importPo(po, config.clientFkey)
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
                        /*   order.facilityCode='DC1'
                           order.facilityPolicy='DC1'
                           order.setThirdPartyBilling("9V3W92");
                           order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
               */

                        // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
                        // but for testing this doesn't work so we will simulate what happens to the order in that process.
                        // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
                        //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
                        String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                        println reference

                        // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                        if(order.shipMethodName.contains("FedEx")) {
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000",false);
                        }  else if (order.shipMethodName.contains("UPS")){
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000",false);
                        }else{
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211",false);
                        }

                        //we have a shipped order, now create the ASN file for it
                        JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                        String asnData = edi.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

                        println asnData
                        //  println("This is after the print asn")
                        //   println "loading owd order";
                        //    OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
                        //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                        //  String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
                        //   println ack
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");
                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                         ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                        // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


                }catch (Exception ex)
                {
                        ex.printStackTrace()
                }

        }
        @Test
        void testAcknowledgementPoCabelas() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>093ALLKADENASPO</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>6778788</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2018-10-18</PurchaseOrderDate>\n" +
                        "            <Vendor>102711</Vendor>\n" +
                        "            <Division>004</Division>\n" +
                        "            <CustomerOrderNumber>00338833488</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                        "            <TermsDescription>Net 60 Days</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Contact>\n" +
                        "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "            <ContactName>Contact Name</ContactName>\n" +
                        "            <PrimaryPhone>(123) 456-7890</PrimaryPhone>\n" +
                        "        </Contact>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Cabela's Inc. Accounts Payable</AddressName>\n" +
                        "            <Address1>One Cabela Drive</Address1>\n" +
                        "            <City>Sidney</City>\n" +
                        "            <State>NE</State>\n" +
                        "            <PostalCode>691608888</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>John Doe</AddressName>\n" +
                        "            <Address1>123 Water St</Address1>\n" +
                        "            <City>Eau Clair</City>\n" +
                        "            <State>WI</State>\n" +
                        "            <PostalCode>54703</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                        "            <AddressName>JUSTBRAND LTD-WARMING STORE-(DS)</AddressName>\n" +
                        "            <Address1>3791 MAIN ST</Address1>\n" +
                        "            <City>PHILADELPHIA</City>\n" +
                        "            <State>PA</State>\n" +
                        "            <PostalCode>19127</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>CC</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                        "            <FOBLocationDescription>Point of Origin</FOBLocationDescription>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                        "            <CarrierAlphaCode>SCAC</CarrierAlphaCode>\n" +
                        "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                        "            <ServiceLevelCodes>\n" +
                        "                <ServiceLevelCode>PB</ServiceLevelCode>\n" +
                        "            </ServiceLevelCodes>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Notes>\n" +
                        "            <NoteInformationField>Header note sent here.</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <ChargesAllowances>\n" +
                        "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                        "            <AllowChrgCode>A260</AllowChrgCode>\n" +
                        "            <AllowChrgAmt>308</AllowChrgAmt>\n" +
                        "            <AllowChrgPercentQual>7</AllowChrgPercentQual>\n" +
                        "            <AllowChrgPercent>3</AllowChrgPercent>\n" +
                        "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                        "            <AllowChrgHandlingDescription>Ad Allowance</AllowChrgHandlingDescription>\n" +
                        "        </ChargesAllowances>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04858418</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AHVST5V1-BM-L</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807718</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807718</GTIN>\n" +
                        "                <OrderQty>2</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>81</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>179.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION1</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717863</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-L-X</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807459</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807459</GTIN>\n" +
                        "                <OrderQty>3</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION2</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717864</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-XXL</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807466</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807466</GTIN>\n" +
                        "                <OrderQty>4</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION3</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalAmount>302</TotalAmount>\n" +
                        "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>9</TotalQuantity>\n" +
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
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        String account = po.Header.OrderHeader.TradingPartnerId.text()
                        int docId = JustBrandsCabelasEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml",account)
                        JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                        EdiSpsConfigdata config = JustBrandsCabelasEDI.getEdiConfigData(po)
                        Order order = edi.importPo(po, config.clientFkey)
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
                        /*   order.facilityCode='DC1'
                           order.facilityPolicy='DC1'
                           order.setThirdPartyBilling("9V3W92");
                           order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
               */

                        // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
                        // but for testing this doesn't work so we will simulate what happens to the order in that process.
                        // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
                        //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
                        String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                        println reference

                        // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                        if(order.shipMethodName.contains("FedEx")) {
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000",false);
                        }  else if (order.shipMethodName.contains("UPS")){
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000",false);
                        }else{
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211",false);
                        }

                        //we have a shipped order, now create the ASN file for it

                      /*  String asnData = edi.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

                        println asnData*/
                        //  println("This is after the print asn")
                        //   println "loading owd order";
                           OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
                        //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                          String ack = edi.generateACK(config,po,471,oorder)
                           println ack
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");

                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                      //  ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                         ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


                }catch (Exception ex)
                {
                        ex.printStackTrace()
                }

        }
        @Test
        void test2AcknowledgementPoCabelas() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>093ALLKADENASPO</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>6778788</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2018-10-18</PurchaseOrderDate>\n" +
                        "            <Vendor>102711</Vendor>\n" +
                        "            <Division>004</Division>\n" +
                        "            <CustomerOrderNumber>00229922599</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                        "            <TermsDescription>Net 60 Days</TermsDescription>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                        "            <Date1>2018-10-23</Date1>\n" +
                        "        </Date>\n" +
                        "        <Contact>\n" +
                        "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                        "            <ContactName>Contact Name</ContactName>\n" +
                        "            <PrimaryPhone>(123) 456-7890</PrimaryPhone>\n" +
                        "        </Contact>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Cabela's Inc. Accounts Payable</AddressName>\n" +
                        "            <Address1>One Cabela Drive</Address1>\n" +
                        "            <City>Sidney</City>\n" +
                        "            <State>NE</State>\n" +
                        "            <PostalCode>691608888</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>Jane Doe</AddressName>\n" +
                        "            <Address1>333 S 7th St</Address1>\n" +
                        "            <City>Minneapolis</City>\n" +
                        "            <State>MN</State>\n" +
                        "            <PostalCode>55402</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                        "            <AddressName>JUSTBRAND LTD-WARMING STORE-(DS)</AddressName>\n" +
                        "            <Address1>3791 MAIN ST</Address1>\n" +
                        "            <City>PHILADELPHIA</City>\n" +
                        "            <State>PA</State>\n" +
                        "            <PostalCode>19127</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <FOBRelatedInstruction>\n" +
                        "            <FOBPayCode>CC</FOBPayCode>\n" +
                        "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                        "            <FOBLocationDescription>Point of Origin</FOBLocationDescription>\n" +
                        "        </FOBRelatedInstruction>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                        "            <CarrierAlphaCode>SCAC</CarrierAlphaCode>\n" +
                        "            <CarrierRouting>Carrier Routing</CarrierRouting>\n" +
                        "            <ServiceLevelCodes>\n" +
                        "                <ServiceLevelCode>PB</ServiceLevelCode>\n" +
                        "            </ServiceLevelCodes>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Notes>\n" +
                        "            <NoteInformationField>Header note sent here.</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <ChargesAllowances>\n" +
                        "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                        "            <AllowChrgCode>A260</AllowChrgCode>\n" +
                        "            <AllowChrgAmt>308</AllowChrgAmt>\n" +
                        "            <AllowChrgPercentQual>7</AllowChrgPercentQual>\n" +
                        "            <AllowChrgPercent>3</AllowChrgPercent>\n" +
                        "            <AllowChrgHandlingCode>02</AllowChrgHandlingCode>\n" +
                        "            <AllowChrgHandlingDescription>Ad Allowance</AllowChrgHandlingDescription>\n" +
                        "        </ChargesAllowances>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04858418</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AHVST5V1-BM-L</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807718</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807718</GTIN>\n" +
                        "                <OrderQty>2</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>81</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>179.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION1</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717863</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-L-X</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807459</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807459</GTIN>\n" +
                        "                <OrderQty>3</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION2</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>04717864</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>AH-SK-AA-03-XXL</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>675595807466</ConsumerPackageCode>\n" +
                        "                <GTIN>00675595807466</GTIN>\n" +
                        "                <OrderQty>4</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>20</PurchasePrice>\n" +
                        "                <Department>20</Department>\n" +
                        "            </OrderLine>\n" +
                        "            <Date>\n" +
                        "                <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "                <Date1>2018-10-23</Date1>\n" +
                        "            </Date>\n" +
                        "            <PriceInformation>\n" +
                        "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                        "                <UnitPrice>49.99</UnitPrice>\n" +
                        "            </PriceInformation>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>ITEMDESCRIPTION3</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "            <Notes>\n" +
                        "                <NoteInformationField>Line item notes sent here.</NoteInformationField>\n" +
                        "            </Notes>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalAmount>302</TotalAmount>\n" +
                        "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>9</TotalQuantity>\n" +
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
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        String account = po.Header.OrderHeader.TradingPartnerId.text()
                        int docId = JustBrandsCabelasEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml",account)
                        JustBrandsCabelasEDI edi = new JustBrandsCabelasEDI();
                        EdiSpsConfigdata config = JustBrandsCabelasEDI.getEdiConfigData(po)
                        Order order = edi.importPo(po, config.clientFkey)
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
                        /*   order.facilityCode='DC1'
                           order.facilityPolicy='DC1'
                           order.setThirdPartyBilling("9V3W92");
                           order.setThirdPartyBillingContact("DICK'S Sporting Goods","DICK'S Sporting Goods","7603 Trade Port Drive","","Louisville","KY","40258","6058457172");
               */

                        // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
                        // but for testing this doesn't work so we will simulate what happens to the order in that process.
                        // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
                        //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
                        String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                        println reference

                        // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                        if(order.shipMethodName.contains("FedEx")) {
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "000000000000000",false);
                        }  else if (order.shipMethodName.contains("UPS")){
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1Z0000000000000000",false);
                        }else{
                                PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211",false);
                        }

                        //we have a shipped order, now create the ASN file for it

                        /*  String asnData = edi.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

                          println asnData*/
                        //  println("This is after the print asn")
                        //   println "loading owd order";
                        OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
                        //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                        String ack = edi.generateACK(config,po,471,oorder)
                        println ack
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");

                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                        //  ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                        ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


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

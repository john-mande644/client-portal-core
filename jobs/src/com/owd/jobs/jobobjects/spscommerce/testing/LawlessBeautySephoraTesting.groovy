package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.LawlessBeautySephoraEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class LawlessBeautySephoraTesting extends GroovyTestCase {




    @Test
    void testingASNLoad(){
        System.setProperty("com.owd.environment", "test");

        LawlessBeautySephoraEDI edi = new LawlessBeautySephoraEDI();
        int orderId = 22095855
        int clientId = 651

        try {
            String asn = edi.generateASN(orderId, clientId)
            System.out.print(asn)
        } catch (Exception ex) {
            System.out.println(ex.message)
        }
    }
    @Test
    void test1shipOrderFromPoSephora() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000000005</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>3</GroupControlNumber>\n" +
                "        <DocumentControlNumber>0001</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>4159772900</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>60282106YK</InterchangeReceiverID>\n" +
                "        <GroupSenderID>4159772900</GroupSenderID>\n" +
                "        <GroupReceiverID>60282106YK</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>77WALLLAWLESSBE</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>0300324787</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-03-16</PurchaseOrderDate>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Department>10</Department>\n" +
                "            <Vendor>000534099</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>14</TermsType>\n" +
                "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                "            <TermsNetDueDays>30</TermsNetDueDays>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2020-03-23</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "            <Date1>2020-03-27</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>OB</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0001</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0701</AddressLocationNumber>\n" +
                "            <AddressName>SEPHORA PERRYMAN DC</AddressName>\n" +
                "            <AddressAlternateName>531 CHELSEA ROAD</AddressAlternateName>\n" +
                "            <City>PERRYMAN</City>\n" +
                "            <State>MD</State>\n" +
                "            <PostalCode>21130</PostalCode>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>FW</AddressTypeCode>\n" +
                "            <AddressName>DFSL SEPHORA USA</AddressName>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>91</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>2127360041</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>7612294001774</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationDescription>FOB</FOBLocationDescription>\n" +
                "            <FOBTitlePassageLocation>FOB VENDOR WAREHO</FOBTitlePassageLocation>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                "            <ServiceLevelCodes>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>REG-030-1389332</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002266245</BuyerPartNumber>\n" +
                "                <VendorPartNumber>GOLDENHR</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024513307</ConsumerPackageCode>\n" +
                "                <OrderQty>77</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>16.72</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS SMMR SKN BRNZR GH</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>0701</Location>\n" +
                "                    <Qty>77</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002294544</BuyerPartNumber>\n" +
                "                <VendorPartNumber>EYE SHADOW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024515011</ConsumerPackageCode>\n" +
                "                <OrderQty>80</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>11</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS THE LITTLE ONE</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>0701</Location>\n" +
                "                    <Qty>80</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002297745</BuyerPartNumber>\n" +
                "                <VendorPartNumber>GLAZED</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024514083</ConsumerPackageCode>\n" +
                "                <OrderQty>20</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>11</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS LP SHNE GLSS GLZD</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>0701</Location>\n" +
                "                    <Qty>20</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            LawlessBeautySephoraEDI edi = new LawlessBeautySephoraEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfileSephora.xml", account)

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
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "000000000000000", true);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1Z0000000000000000", true);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1234567891211", true);
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
    void test1shipOrderFromPoSephora2() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000000005</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>3</GroupControlNumber>\n" +
                "        <DocumentControlNumber>0003</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>4159772900</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>60282106YK</InterchangeReceiverID>\n" +
                "        <GroupSenderID>4159772900</GroupSenderID>\n" +
                "        <GroupReceiverID>60282106YK</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>77WALLLAWLESSBE</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>0300324789</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-03-16</PurchaseOrderDate>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Department>10</Department>\n" +
                "            <Vendor>000534099</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>14</TermsType>\n" +
                "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                "            <TermsNetDueDays>30</TermsNetDueDays>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2020-03-23</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "            <Date1>2020-03-27</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>OB</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0001</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>1001</AddressLocationNumber>\n" +
                "            <AddressName>SEPHORA SOUTHEAST DC</AddressName>\n" +
                "            <AddressAlternateName>8500 NAIL ROAD</AddressAlternateName>\n" +
                "            <City>OLIVE BRANCH</City>\n" +
                "            <State>MS</State>\n" +
                "            <PostalCode>38654</PostalCode>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>FW</AddressTypeCode>\n" +
                "            <AddressName>DFSL SEPHORA USA</AddressName>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>91</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>2127360041</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>7612294001774</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationDescription>FOB</FOBLocationDescription>\n" +
                "            <FOBTitlePassageLocation>FOB VENDOR WAREHO</FOBTitlePassageLocation>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                "            <ServiceLevelCodes>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>REG-030-1389334</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002065258</BuyerPartNumber>\n" +
                "                <VendorPartNumber>RYAN</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>851642008073</ConsumerPackageCode>\n" +
                "                <OrderQty>20</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>11</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS L/L RYAN</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1001</Location>\n" +
                "                    <Qty>20</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002266237</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BLAZED</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024513314</ConsumerPackageCode>\n" +
                "                <OrderQty>9</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>16.72</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS SMMR SKN BRNZR BL</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1001</Location>\n" +
                "                    <Qty>9</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002266245</BuyerPartNumber>\n" +
                "                <VendorPartNumber>GOLDENHR</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024513307</ConsumerPackageCode>\n" +
                "                <OrderQty>78</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>16.72</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS SMMR SKN BRNZR GH</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1001</Location>\n" +
                "                    <Qty>78</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002271682</BuyerPartNumber>\n" +
                "                <VendorPartNumber>MINI POWDER</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>851642008554</ConsumerPackageCode>\n" +
                "                <OrderQty>22</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>10.12</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS MN SL THE DL PWDR</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1001</Location>\n" +
                "                    <Qty>22</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>5</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002294544</BuyerPartNumber>\n" +
                "                <VendorPartNumber>EYE SHADOW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024515011</ConsumerPackageCode>\n" +
                "                <OrderQty>80</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>11</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS THE LITTLE ONE</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1001</Location>\n" +
                "                    <Qty>80</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>6</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002297794</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BABE</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024514137</ConsumerPackageCode>\n" +
                "                <OrderQty>20</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>11</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS LP SHNE GLSS BABE</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1001</Location>\n" +
                "                    <Qty>20</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>6</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            LawlessBeautySephoraEDI edi = new LawlessBeautySephoraEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfileSephora.xml", account)

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
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "000000000000000", true);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1Z0000000000000000", true);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1234567891211", true);
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
    void test1shipOrderFromPoSephora3() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000000005</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>3</GroupControlNumber>\n" +
                "        <DocumentControlNumber>0004</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>4159772900</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>60282106YK</InterchangeReceiverID>\n" +
                "        <GroupSenderID>4159772900</GroupSenderID>\n" +
                "        <GroupReceiverID>60282106YK</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>77WALLLAWLESSBE</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>0300324790</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-03-16</PurchaseOrderDate>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Department>10</Department>\n" +
                "            <Vendor>000534099</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>14</TermsType>\n" +
                "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                "            <TermsNetDueDays>30</TermsNetDueDays>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2020-03-23</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "            <Date1>2020-03-27</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>OB</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0001</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>1021</AddressLocationNumber>\n" +
                "            <AddressName>SEPHORA WDC</AddressName>\n" +
                "            <AddressAlternateName>6260 E ANN ROAD</AddressAlternateName>\n" +
                "            <City>NORTH LAS VEGAS</City>\n" +
                "            <State>NV</State>\n" +
                "            <PostalCode>89115</PostalCode>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>FW</AddressTypeCode>\n" +
                "            <AddressName>DFSL SEPHORA USA</AddressName>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>91</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>2127360041</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>7612294001774</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationDescription>FOB</FOBLocationDescription>\n" +
                "            <FOBTitlePassageLocation>FOB VENDOR WAREHO</FOBTitlePassageLocation>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                "            <ServiceLevelCodes>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>REG-030-1389335</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002065225</BuyerPartNumber>\n" +
                "                <VendorPartNumber>CAMERON</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>851642008011</ConsumerPackageCode>\n" +
                "                <OrderQty>20</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>11</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS L/L CAMERON</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1021</Location>\n" +
                "                    <Qty>20</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002266245</BuyerPartNumber>\n" +
                "                <VendorPartNumber>GOLDENHR</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024513307</ConsumerPackageCode>\n" +
                "                <OrderQty>51</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>16.72</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS SMMR SKN BRNZR GH</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1021</Location>\n" +
                "                    <Qty>51</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002271682</BuyerPartNumber>\n" +
                "                <VendorPartNumber>MINI POWDER</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>851642008554</ConsumerPackageCode>\n" +
                "                <OrderQty>16</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>10.12</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS MN SL THE DL PWDR</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1021</Location>\n" +
                "                    <Qty>16</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002294494</BuyerPartNumber>\n" +
                "                <VendorPartNumber>WEDDING DAY</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024514434</ConsumerPackageCode>\n" +
                "                <OrderQty>12</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>12.32</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS BLT LP STK WDG DY</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1021</Location>\n" +
                "                    <Qty>12</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>5</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002294544</BuyerPartNumber>\n" +
                "                <VendorPartNumber>EYE SHADOW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024515011</ConsumerPackageCode>\n" +
                "                <OrderQty>60</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>11</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS THE LITTLE ONE</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>1021</Location>\n" +
                "                    <Qty>60</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>5</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            LawlessBeautySephoraEDI edi = new LawlessBeautySephoraEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfileSephora.xml", account)

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
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "000000000000000", true);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1Z0000000000000000", true);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1234567891211", true);
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
    void test1shipOrderFromPoSephora4() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000000005</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>3</GroupControlNumber>\n" +
                "        <DocumentControlNumber>0002</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>4159772900</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>60282106YK</InterchangeReceiverID>\n" +
                "        <GroupSenderID>4159772900</GroupSenderID>\n" +
                "        <GroupReceiverID>60282106YK</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>77WALLLAWLESSBE</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>0300324788</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-03-16</PurchaseOrderDate>\n" +
                "            <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            <Department>10</Department>\n" +
                "            <Vendor>000534099</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>14</TermsType>\n" +
                "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                "            <TermsNetDueDays>30</TermsNetDueDays>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2020-03-23</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "            <Date1>2020-03-27</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>OB</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0001</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0801</AddressLocationNumber>\n" +
                "            <AddressName>SEPHORA UTAH DC</AddressName>\n" +
                "            <AddressAlternateName>6075 WEST, 300 SOUTH</AddressAlternateName>\n" +
                "            <City>SALT LAKE CITY</City>\n" +
                "            <State>UT</State>\n" +
                "            <PostalCode>84104</PostalCode>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>FW</AddressTypeCode>\n" +
                "            <AddressName>DFSL SEPHORA USA</AddressName>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>91</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>2127360041</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>7612294001774</AddressLocationNumber>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationDescription>FOB</FOBLocationDescription>\n" +
                "            <FOBTitlePassageLocation>FOB VENDOR WAREHO</FOBTitlePassageLocation>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierTransMethodCode>M</CarrierTransMethodCode>\n" +
                "            <ServiceLevelCodes>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>AN</ReferenceQual>\n" +
                "            <ReferenceID>REG-030-1389333</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002266245</BuyerPartNumber>\n" +
                "                <VendorPartNumber>GOLDENHR</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024513307</ConsumerPackageCode>\n" +
                "                <OrderQty>26</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>16.72</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS SMMR SKN BRNZR GH</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>0801</Location>\n" +
                "                    <Qty>26</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002294494</BuyerPartNumber>\n" +
                "                <VendorPartNumber>WEDDING DAY</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024514434</ConsumerPackageCode>\n" +
                "                <OrderQty>12</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>12.32</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS BLT LP STK WDG DY</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>0801</Location>\n" +
                "                    <Qty>12</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>002294544</BuyerPartNumber>\n" +
                "                <VendorPartNumber>EYE SHADOW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>810024515011</ConsumerPackageCode>\n" +
                "                <OrderQty>40</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>11</PurchasePrice>\n" +
                "                <NRFStandardColorAndSize>\n" +
                "                    <NRFColorCode>X</NRFColorCode>\n" +
                "                    <ColorCategoryName>08</ColorCategoryName>\n" +
                "                    <SizePrimaryDescription>LAWLESS THE LITTLE ONE</SizePrimaryDescription>\n" +
                "                </NRFStandardColorAndSize>\n" +
                "            </OrderLine>\n" +
                "            <QuantitiesSchedulesLocations>\n" +
                "                <TotalQtyUOM>EA</TotalQtyUOM>\n" +
                "                <LocationQuantity>\n" +
                "                    <Location>0801</Location>\n" +
                "                    <Qty>40</Qty>\n" +
                "                </LocationQuantity>\n" +
                "            </QuantitiesSchedulesLocations>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            LawlessBeautySephoraEDI edi = new LawlessBeautySephoraEDI();

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
            int docId = edi.saveIncomingEdiDocForTesting(poData, "POtestfileSephora.xml", account)

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
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "000000000000000", true);
            } else if (order.shipMethodName.contains("UPS")) {
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1Z0000000000000000", true);
            } else {
                PackingManager.packAndShip(Integer.parseInt(order.orderID),true, 4, 0.00, "1234567891211", true);
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

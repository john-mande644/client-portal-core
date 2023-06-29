package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderFactory
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.BumbleRideWilliamsSanomaEDI
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsTargetEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class BumbleRideWilliamsSanomaTesting extends GroovyTestCase {




    @Test
    void testingASNLoad(){
        System.setProperty("com.owd.environment", "test");
        BumbleRideWilliamsSanomaEDI edi = new BumbleRideWilliamsSanomaEDI();
        System.out.println(edi.generateASN(18024832,575))
        //System.out.println(edi.generateASN(18024835,575))
    }
    @Test
    void test1shipOrderFromPoTarget() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>61YFWDBUMBLONEW</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>111873666</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2019-09-08</PurchaseOrderDate>\n" +
                "            <Vendor>2977</Vendor>\n" +
                "            <Division>PK</Division>\n" +
                "            <CustomerOrderNumber>392512556458</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Kristen Devick</AddressName>\n" +
                "            <Address1>5313 Indio Dr Apt C</Address1>\n" +
                "            <City>Austin</City>\n" +
                "            <State>TX</State>\n" +
                "            <PostalCode>78745</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <PrimaryPhone>8505703384</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>POTTERY BARN KIDS</AddressName>\n" +
                "            <Address1>4300 CONCORDE ROAD</Address1>\n" +
                "            <City>MEMPHIS</City>\n" +
                "            <State>TN</State>\n" +
                "            <PostalCode>38118</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>SO</AddressTypeCode>\n" +
                "            <AddressName>Kristen Devick</AddressName>\n" +
                "            <Address1>5313 Indio Dr Apt C</Address1>\n" +
                "            <City>Austin</City>\n" +
                "            <State>TX</State>\n" +
                "            <PostalCode>78745</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierTransMethodCode>U</CarrierTransMethodCode>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>CG</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>3611687</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BUMBLERIDE INDI</VendorPartNumber>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>329</PurchasePrice>\n" +
                "                <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2019-09-23</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2019-09-16</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>22</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>549</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Bumbleride Indie Stroller:Dawn Grey Coral</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>7880281</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BR SINGLE ADAPT</VendorPartNumber>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "                <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2019-09-23</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2019-09-16</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>2</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>45</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Bumbleride Single Car Seat Adaptor</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            BumbleRideWilliamsSanomaEDI edi = new BumbleRideWilliamsSanomaEDI();

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
            ex.printStackTrace()
        }

    }

    @Test
    void test2shipOrderFromPoTarget() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>61YFWDBUMBLONEW</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>112007121</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2019-10-14</PurchaseOrderDate>\n" +
                "            <Vendor>2977</Vendor>\n" +
                "            <Division>PK</Division>\n" +
                "            <CustomerOrderNumber>392873161146</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Renae Rountree</AddressName>\n" +
                "            <Address1>1923 S Gary Pl</Address1>\n" +
                "            <City>Tulsa</City>\n" +
                "            <State>OK</State>\n" +
                "            <PostalCode>74104</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <PrimaryPhone>9182615072</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>POTTERY BARN KIDS</AddressName>\n" +
                "            <Address1>4300 CONCORDE ROAD</Address1>\n" +
                "            <City>MEMPHIS</City>\n" +
                "            <State>TN</State>\n" +
                "            <PostalCode>38118</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>SO</AddressTypeCode>\n" +
                "            <AddressName>Renae Rountree</AddressName>\n" +
                "            <Address1>1923 S Gary Pl</Address1>\n" +
                "            <City>Tulsa</City>\n" +
                "            <State>OK</State>\n" +
                "            <PostalCode>74104</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierTransMethodCode>U</CarrierTransMethodCode>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>CG</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>7880281</BuyerPartNumber>\n" +
                "                <VendorPartNumber>BR SINGLE ADAPT</VendorPartNumber>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>25</PurchasePrice>\n" +
                "                <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2019-10-28</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2019-10-21</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>2</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>45</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Bumbleride Single Car Seat Adaptor</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            BumbleRideWilliamsSanomaEDI edi = new BumbleRideWilliamsSanomaEDI();

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
            ex.printStackTrace()
        }

    }

    @Test
    void test3shipOrderFromPoWilliamSonoma() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>61YFWDBUMBLONEW</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>112617216</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-01-21</PurchaseOrderDate>\n" +
                "            <Vendor>2977</Vendor>\n" +
                "            <Division>PK</Division>\n" +
                "            <CustomerOrderNumber>300213336823</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Celeste Pennachio</AddressName>\n" +
                "            <Address1>5906 Yeats Manor Dr</Address1>\n" +
                "            <City>Tampa</City>\n" +
                "            <State>FL</State>\n" +
                "            <PostalCode>33616</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <PrimaryPhone>8638386247</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>POTTERY BARN KIDS</AddressName>\n" +
                "            <Address1>4300 CONCORDE ROAD</Address1>\n" +
                "            <City>MEMPHIS</City>\n" +
                "            <State>TN</State>\n" +
                "            <PostalCode>38118</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>SO</AddressTypeCode>\n" +
                "            <AddressName>Clara Panesso</AddressName>\n" +
                "            <Address1>3507 Bayshore Blvd</Address1>\n" +
                "            <City>Tampa</City>\n" +
                "            <State>FL</State>\n" +
                "            <PostalCode>33629</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierTransMethodCode>U</CarrierTransMethodCode>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>CG</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>L1</ReferenceQual>\n" +
                "            <ReferenceID>GFT</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>6627009</BuyerPartNumber>\n" +
                "                <VendorPartNumber>I-825DGM</VendorPartNumber>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>329</PurchasePrice>\n" +
                "                <BuyersCurrency>USD</BuyersCurrency>\n" +
                "            </OrderLine>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "                <Date1>2020-02-04</Date1>\n" +
                "            </Date>\n" +
                "            <Date>\n" +
                "                <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "                <Date1>2020-01-28</Date1>\n" +
                "            </Date>\n" +
                "            <Measurements>\n" +
                "                <MeasurementQualifier>WT</MeasurementQualifier>\n" +
                "                <MeasurementValue>22</MeasurementValue>\n" +
                "                <CompositeUOM>LB</CompositeUOM>\n" +
                "            </Measurements>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Bumbleride Indie Stroller:Dawn Grey Mint</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            BumbleRideWilliamsSanomaEDI edi = new BumbleRideWilliamsSanomaEDI();

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
            ex.printStackTrace()
        }

    }

}

package com.owd.jobs.jobobjects.spscommerce

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderFactory
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiDocs
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.symphony.SymphonyAPI
import org.junit.Before
import org.junit.Test

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by stewartbuskirk1 on 5/27/15.
 */
class SPSCommerceTest extends GroovyTestCase {


    @Before
    void setup() {
        System.setProperty('com.owd.environment', 'test')

        def things = ['thing', 'thing2']
    }

   /* @Test
    void testFtpPoLoad() {
        System.setProperty('com.owd.environment', 'test')

        List orders = SpsCommerceUtilities.processRemotePOs(SPSCommerceRemoteFTP.FolderPath.receiveDirTestPath.getPath(), 489)

    }*/
/*

    @Test
    void testProcessPendingPOs() {
       // System.setProperty('com.owd.environment', 'test')

        SpsCommerceUtilities.processPendingPos(489)
    }
*/

/*

    @Test
    void testAsn() {
        //   System.setProperty('com.owd.environment','test')

        println SpsCommerceUtilities.generateASN(93969621, 489)
    }
*/

 /*   @Test
    void testDeliverAsnProduction() {
          System.setProperty('com.owd.environment','test')

        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP()
        String asnData = SpsCommerceUtilities.generateASN(10060903, 489)
        println asnData
        ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
    }*/

/*    @Test
    void testloadOrderFromPoWayfair() {

        String poData = "<Order xmlns=\"http://www.spscommerce.com/RSX\">\n" +
                "  <Meta>\n" +
                "    <IsDropShip>\n" +
                "      true\n" +
                "    </IsDropShip>\n" +
                "  </Meta>\n" +
                "  <Header>\n" +
                "    <OrderHeader>\n" +
                "      <TradingPartnerId>\n" +
                "        69RFWDCHILITECH\n" +
                "      </TradingPartnerId>\n" +
                "      <PurchaseOrderNumber>\n" +
                "        CS36655975\n" +
                "      </PurchaseOrderNumber>\n" +
                "      <TsetPurposeCode>\n" +
                "        00\n" +
                "      </TsetPurposeCode>\n" +
                "      <PurchaseOrderTypeCode>\n" +
                "        DS\n" +
                "      </PurchaseOrderTypeCode>\n" +
                "      <PurchaseOrderDate>\n" +
                "        2015-05-29\n" +
                "      </PurchaseOrderDate>\n" +
                "      <Vendor>\n" +
                "        2624\n" +
                "      </Vendor>\n" +
                "    </OrderHeader>\n" +
                "    <PaymentTerms>\n" +
                "      <TermsType>\n" +
                "        01\n" +
                "      </TermsType>\n" +
                "      <TermsDescription>\n" +
                "        3% 10 Net 60\n" +
                "      </TermsDescription>\n" +
                "    </PaymentTerms>\n" +
                "    <Date>\n" +
                "      <DateTimeQualifier1>\n" +
                "        010\n" +
                "      </DateTimeQualifier1>\n" +
                "      <Date1>\n" +
                "        2015-06-02\n" +
                "      </Date1>\n" +
                "    </Date>\n" +
                "    <Contact>\n" +
                "      <ContactTypeCode>\n" +
                "        IC\n" +
                "      </ContactTypeCode>\n" +
                "      <ContactName>\n" +
                "        Chili Technology LLC\n" +
                "      </ContactName>\n" +
                "      <PrimaryPhone>\n" +
                "        7042356831  122\n" +
                "      </PrimaryPhone>\n" +
                "      <PrimaryFax>\n" +
                "        (704) 973-7728\n" +
                "      </PrimaryFax>\n" +
                "    </Contact>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        BT\n" +
                "      </AddressTypeCode>\n" +
                "      <AddressName>\n" +
                "        Wayfair\n" +
                "      </AddressName>\n" +
                "      <Address1>\n" +
                "        4 Copley Place, Floor 7\n" +
                "      </Address1>\n" +
                "      <City>\n" +
                "        Boston\n" +
                "      </City>\n" +
                "      <State>\n" +
                "        MA\n" +
                "      </State>\n" +
                "      <PostalCode>\n" +
                "        02116\n" +
                "      </PostalCode>\n" +
                "      <Country>\n" +
                "        US\n" +
                "      </Country>\n" +
                "      <Contact>\n" +
                "        <ContactName>\n" +
                "          BT\n" +
                "        </ContactName>\n" +
                "        <PrimaryPhone>\n" +
                "          6175326815\n" +
                "        </PrimaryPhone>\n" +
                "        <PrimaryFax>\n" +
                "          617-502-7798\n" +
                "        </PrimaryFax>\n" +
                "        <PrimaryEmail>\n" +
                "          WayfairOps1@wayfair.com\n" +
                "        </PrimaryEmail>\n" +
                "      </Contact>\n" +
                "    </Address>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        ST\n" +
                "      </AddressTypeCode>\n" +
                "      <AddressName>\n" +
                "        Elizabeth Carling\n" +
                "      </AddressName>\n" +
                "      <Address1>\n" +
                "        2885 Sanford Ave SWX27275\n" +
                "      </Address1>\n" +
                "      <Address4>\n" +
                "        R\n" +
                "      </Address4>\n" +
                "      <City>\n" +
                "        Grandville\n" +
                "      </City>\n" +
                "      <State>\n" +
                "        MI\n" +
                "      </State>\n" +
                "      <PostalCode>\n" +
                "        49418\n" +
                "      </PostalCode>\n" +
                "      <Country>\n" +
                "        US\n" +
                "      </Country>\n" +
                "      <Contact>\n" +
                "        <ContactName>\n" +
                "          ST\n" +
                "        </ContactName>\n" +
                "        <PrimaryPhone>\n" +
                "          7625851984\n" +
                "        </PrimaryPhone>\n" +
                "        <PrimaryEmail>\n" +
                "          sgoldstein2384@gmail.com\n" +
                "        </PrimaryEmail>\n" +
                "      </Contact>\n" +
                "    </Address>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        VN\n" +
                "      </AddressTypeCode>\n" +
                "      <AddressName>\n" +
                "        Elizabeth Carling\n" +
                "      </AddressName>\n" +
                "      <Address1>\n" +
                "        2885 Sanford Ave SWX27275\n" +
                "      </Address1>\n" +
                "      <City>\n" +
                "        Grandville\n" +
                "      </City>\n" +
                "      <State>\n" +
                "        MI\n" +
                "      </State>\n" +
                "      <PostalCode>\n" +
                "        49418\n" +
                "      </PostalCode>\n" +
                "      <Country>\n" +
                "        US\n" +
                "      </Country>\n" +
                "    </Address>\n" +
                "    <CarrierInformation>\n" +
                "      <CarrierTransMethodCode>\n" +
                "        M\n" +
                "      </CarrierTransMethodCode>\n" +
                "      <CarrierAlphaCode>\n" +
                "        FDEG\n" +
                "      </CarrierAlphaCode>\n" +
                "      <CarrierRouting>\n" +
                "        GR\n" +
                "      </CarrierRouting>\n" +
                "      <ServiceLevelCodes>\n" +
                "        <ServiceLevelCode>\n" +
                "          CG\n" +
                "        </ServiceLevelCode>\n" +
                "      </ServiceLevelCodes>\n" +
                "    </CarrierInformation>\n" +
                "    <Reference>\n" +
                "      <ReferenceQual>\n" +
                "        VN\n" +
                "      </ReferenceQual>\n" +
                "      <ReferenceID>\n" +
                "        2624\n" +
                "      </ReferenceID>\n" +
                "      <Description>\n" +
                "        28117\n" +
                "      </Description>\n" +
                "    </Reference>\n" +
                "    <Reference>\n" +
                "      <ReferenceQual>\n" +
                "        TPF\n" +
                "      </ReferenceQual>\n" +
                "      <ReferenceID>\n" +
                "        346593300\n" +
                "      </ReferenceID>\n" +
                "    </Reference>\n" +
                "  </Header>\n" +
                "  <LineItems>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          1\n" +
                "        </LineSequenceNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          CP503\n" +
                "        </VendorPartNumber>\n" +
                "        <OrderQty>\n" +
                "          1\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          305\n" +
                "        </PurchasePrice>\n" +
                "        <PurchasePriceBasis>\n" +
                "          UM\n" +
                "        </PurchasePriceBasis>\n" +
                "      </OrderLine>\n" +
                "      <ProductOrItemDescription>\n" +
                "        <ProductDescription>\n" +
                "          ChiliPad Cube 1 1 Cooling and Heating Mattress PadSize:Full\n" +
                "        </ProductDescription>\n" +
                "      </ProductOrItemDescription>\n" +
                "    </LineItem>\n" +
                "  </LineItems>\n" +
                "  <Summary>\n" +
                "    <TotalAmount>\n" +
                "      305\n" +
                "    </TotalAmount>\n" +
                "    <TotalLineItemNumber>\n" +
                "      1\n" +
                "    </TotalLineItemNumber>\n" +
                "  </Summary>\n" +
                "</Order>"

        try {
            println poData
            int docId = SpsCommerceUtilities.saveIncomingEdiDoc(poData, "POtestfile.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po = parser.parseText(poData)
            Order order = SpsCommerceUtilities.importPo(po, 491)

            println SymphonyAPI.createOrder(order, docId)
        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }*/

/*    @Test
    void testloadOrderFromPoAmazon() {
           System.setProperty("com.owd.environment","test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>080FWDCHILITECH</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>6IGYWG1W</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderDate>2016-02-08</PurchaseOrderDate>\n" +
                "            <ShipCompleteCode>Y</ShipCompleteCode>\n" +
                "            <Vendor>CHKBW</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>064</DateTimeQualifier1>\n" +
                "            <Date1>2016-02-09</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>063</DateTimeQualifier1>\n" +
                "            <Date1>2016-02-11</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>15</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>8025304</AddressLocationNumber>\n" +
                "            <AddressName>Golden State FC LLC</AddressName>\n" +
                "            <Address1>24208 San Michele Road</Address1>\n" +
                "            <City>Moreno Valley</City>\n" +
                "            <State>CA</State>\n" +
                "            <PostalCode>92551</PostalCode>\n" +
                "        </Address>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <VendorPartNumber>CG-B</VendorPartNumber>\n" +
                "                <OrderQty>6</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>28</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "        <TotalQuantity>6</TotalQuantity>\n" +
                "    </Summary>\n" +
                "</Order>"

        try {
            println poData
            int docId = SpsCommerceUtilities.saveIncomingEdiDoc(poData, "POtestfile.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po = parser.parseText(poData)
            Order order = SpsCommerceUtilities.importPo(po, 489)

            order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println SymphonyAPI.postOrderToSymphony(order, docId, po, false)
        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }*/
        @Test
        void testASN(){
            System.setProperty("com.owd.environment","test");
            String asnData = SpsCommerceUtilities.generateASN(11004833, 471);

            println asnData

        }


    @Test
    void testloadOrderFromPoAmazonNootrobox() {
        System.setProperty("com.owd.environment","test");

         String poData = "<Order>\n" +
                 "    <Meta>\n" +
                 "        <InterchangeControlNumber>000000001</InterchangeControlNumber>\n" +
                 "        <GroupControlNumber>1</GroupControlNumber>\n" +
                 "        <DocumentControlNumber>0001</DocumentControlNumber>\n" +
                 "        <InterchangeSenderID>AMAZON</InterchangeSenderID>\n" +
                 "        <InterchangeReceiverID>5124238026</InterchangeReceiverID>\n" +
                 "        <GroupSenderID>AMAZON</GroupSenderID>\n" +
                 "        <GroupReceiverID>5124238026</GroupReceiverID>\n" +
                 "    </Meta>\n" +
                 "    <Header>\n" +
                 "        <OrderHeader>\n" +
                 "            <TradingPartnerId>FYAALLHVMNINC00</TradingPartnerId>\n" +
                 "            <PurchaseOrderNumber>TST00003</PurchaseOrderNumber>\n" +
                 "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                 "            <PurchaseOrderTypeCode>NE</PurchaseOrderTypeCode>\n" +
                 "            <PurchaseOrderDate>2017-09-14</PurchaseOrderDate>\n" +
                 "            <ShipCompleteCode>N</ShipCompleteCode>\n" +
                 "        </OrderHeader>\n" +
                 "        <Date>\n" +
                 "            <DateTimeQualifier1>064</DateTimeQualifier1>\n" +
                 "            <Date1>2017-09-14</Date1>\n" +
                 "        </Date>\n" +
                 "        <Date>\n" +
                 "            <DateTimeQualifier1>063</DateTimeQualifier1>\n" +
                 "            <Date1>2017-09-21</Date1>\n" +
                 "        </Date>\n" +
                 "        <Address>\n" +
                 "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                 "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                 "            <AddressLocationNumber>RNO1</AddressLocationNumber>\n" +
                 "        </Address>\n" +
                 "        <Reference>\n" +
                 "            <ReferenceQual>CR</ReferenceQual>\n" +
                 "            <ReferenceID>NOQ9X</ReferenceID>\n" +
                 "        </Reference>\n" +
                 "    </Header>\n" +
                 "    <LineItems>\n" +
                 "        <LineItem>\n" +
                 "            <OrderLine>\n" +
                 "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                 "                <BuyerPartNumber>B06W54MW2G</BuyerPartNumber>\n" +
                 "                <OrderQty>6</OrderQty>\n" +
                 "                <OrderQtyUOM>CA</OrderQtyUOM>\n" +
                 "                <PurchasePrice>12</PurchasePrice>\n" +
                 "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                 "            </OrderLine>\n" +
                 "        </LineItem>\n" +
                 "        <LineItem>\n" +
                 "            <OrderLine>\n" +
                 "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                 "                <BuyerPartNumber>B06W2JL9D8</BuyerPartNumber>\n" +
                 "                <OrderQty>6</OrderQty>\n" +
                 "                <OrderQtyUOM>CA</OrderQtyUOM>\n" +
                 "                <PurchasePrice>13.02</PurchasePrice>\n" +
                 "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                 "            </OrderLine>\n" +
                 "        </LineItem>\n" +
                 "    </LineItems>\n" +
                 "    <Summary>\n" +
                 "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                 "        <TotalQuantity>6</TotalQuantity>\n" +
                 "    </Summary>\n" +
                 "</Order>" +
                 ""

        //String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceUtilities.getEdiConfigData(po)
            Order order = SpsCommerceHVMNAmazonUtilities.importPo(po, config.clientFkey)
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
            order.setShippingMethodName("LTL");
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            order.facilityCode='DC6'
            order.facilityPolicy='DC6'

            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
            PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "12345678912",true);

            //we have a shipped order, now create the ASN file for it

            String asnData = SpsCommerceHVMNAmazonUtilities.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

            println asnData
            println("This is after the print asn")
            println "loading owd order";
            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            String ack = SpsCommerceHVMNAmazonUtilities.generateACK(config,po,576,oorder)
            println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            //   ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }

        @Test
    void testloadOrderFromPoAmazonGildan() {
        System.setProperty("com.owd.environment","test");

        /* String poData = "<Order xmlns=\"http://www.spscommerce.com/RSX\">\n" +
                 "  <Meta>\n" +
                 "    <InterchangeControlNumber>\n" +
                 "      000000031\n" +
                 "    </InterchangeControlNumber>\n" +
                 "    <GroupControlNumber>\n" +
                 "      31\n" +
                 "    </GroupControlNumber>\n" +
                 "    <DocumentControlNumber>\n" +
                 "      0001\n" +
                 "    </DocumentControlNumber>\n" +
                 "    <InterchangeSenderID>\n" +
                 "      AMAZON\n" +
                 "    </InterchangeSenderID>\n" +
                 "    <InterchangeReceiverID>\n" +
                 "      GILDANEDI\n" +
                 "    </InterchangeReceiverID>\n" +
                 "    <GroupSenderID>\n" +
                 "      AMAZON\n" +
                 "    </GroupSenderID>\n" +
                 "    <GroupReceiverID>\n" +
                 "      GILDANEDI\n" +
                 "    </GroupReceiverID>\n" +
                 "  </Meta>\n" +
                 "  <Header>\n" +
                 "    <OrderHeader>\n" +
                 "      <TradingPartnerId>\n" +
                 "        080ALLGILDANUSA\n" +
                 "      </TradingPartnerId>\n" +
                 "      <PurchaseOrderNumber>\n" +
                 "        6N6JXM7F\n" +
                 "      </PurchaseOrderNumber>\n" +
                 "      <TsetPurposeCode>\n" +
                 "        00\n" +
                 "      </TsetPurposeCode>\n" +
                 "      <PurchaseOrderTypeCode>\n" +
                 "        NE\n" +
                 "      </PurchaseOrderTypeCode>\n" +
                 "      <PurchaseOrderDate>\n" +
                 "        2016-11-15\n" +
                 "      </PurchaseOrderDate>\n" +
                 "      <ShipCompleteCode>\n" +
                 "        N\n" +
                 "      </ShipCompleteCode>\n" +
                 "    </OrderHeader>\n" +
                 "    <Date>\n" +
                 "      <DateTimeQualifier1>\n" +
                 "        064\n" +
                 "      </DateTimeQualifier1>\n" +
                 "      <Date1>\n" +
                 "        2016-11-28\n" +
                 "      </Date1>\n" +
                 "    </Date>\n" +
                 "    <Date>\n" +
                 "      <DateTimeQualifier1>\n" +
                 "        063\n" +
                 "      </DateTimeQualifier1>\n" +
                 "      <Date1>\n" +
                 "        2016-12-06\n" +
                 "      </Date1>\n" +
                 "    </Date>\n" +
                 "    <Address>\n" +
                 "      <AddressTypeCode>\n" +
                 "        ST\n" +
                 "      </AddressTypeCode>\n" +
                 "      <LocationCodeQualifier>\n" +
                 "        92\n" +
                 "      </LocationCodeQualifier>\n" +
                 "      <AddressLocationNumber>\n" +
                 "        ABE3\n" +
                 "      </AddressLocationNumber>\n" +
                 "    </Address>\n" +
                 "    <Reference>\n" +
                 "      <ReferenceQual>\n" +
                 "        IT\n" +
                 "      </ReferenceQual>\n" +
                 "      <ReferenceID>\n" +
                 "        GIME3\n" +
                 "      </ReferenceID>\n" +
                 "    </Reference>\n" +
                 "  </Header>\n" +
                 "  <LineItems>\n" +
                 "    <LineItem>\n" +
                 "      <OrderLine>\n" +
                 "        <LineSequenceNumber>\n" +
                 "          1\n" +
                 "        </LineSequenceNumber>\n" +
                 "        <ConsumerPackageCode>\n" +
                 "          883096146827\n" +
                 "        </ConsumerPackageCode>\n" +
                 "        <OrderQty>\n" +
                 "          1\n" +
                 "        </OrderQty>\n" +
                 "        <OrderQtyUOM>\n" +
                 "          EA\n" +
                 "        </OrderQtyUOM>\n" +
                 "        <PurchasePrice>\n" +
                 "          7\n" +
                 "        </PurchasePrice>\n" +
                 "        <PurchasePriceBasis>\n" +
                 "          PE\n" +
                 "        </PurchasePriceBasis>\n" +
                 "      </OrderLine>\n" +
                 "    </LineItem>\n" +
                 "    <LineItem>\n" +
                 "      <OrderLine>\n" +
                 "        <LineSequenceNumber>\n" +
                 "          2\n" +
                 "        </LineSequenceNumber>\n" +
                 "        <ConsumerPackageCode>\n" +
                 "          883096146858\n" +
                 "        </ConsumerPackageCode>\n" +
                 "        <OrderQty>\n" +
                 "          4\n" +
                 "        </OrderQty>\n" +
                 "        <OrderQtyUOM>\n" +
                 "          EA\n" +
                 "        </OrderQtyUOM>\n" +
                 "        <PurchasePrice>\n" +
                 "          7.18\n" +
                 "        </PurchasePrice>\n" +
                 "        <PurchasePriceBasis>\n" +
                 "          PE\n" +
                 "        </PurchasePriceBasis>\n" +
                 "      </OrderLine>\n" +
                 "    </LineItem>\n" +
                 "    <LineItem>\n" +
                 "      <OrderLine>\n" +
                 "        <LineSequenceNumber>\n" +
                 "          3\n" +
                 "        </LineSequenceNumber>\n" +
                 "        <ConsumerPackageCode>\n" +
                 "          883096146865\n" +
                 "        </ConsumerPackageCode>\n" +
                 "        <OrderQty>\n" +
                 "          2\n" +
                 "        </OrderQty>\n" +
                 "        <OrderQtyUOM>\n" +
                 "          EA\n" +
                 "        </OrderQtyUOM>\n" +
                 "        <PurchasePrice>\n" +
                 "          7.18\n" +
                 "        </PurchasePrice>\n" +
                 "        <PurchasePriceBasis>\n" +
                 "          PE\n" +
                 "        </PurchasePriceBasis>\n" +
                 "      </OrderLine>\n" +
                 "    </LineItem>\n" +
                 "    <LineItem>\n" +
                 "      <OrderLine>\n" +
                 "        <LineSequenceNumber>\n" +
                 "          4\n" +
                 "        </LineSequenceNumber>\n" +
                 "        <ConsumerPackageCode>\n" +
                 "          883096146841\n" +
                 "        </ConsumerPackageCode>\n" +
                 "        <OrderQty>\n" +
                 "          5\n" +
                 "        </OrderQty>\n" +
                 "        <OrderQtyUOM>\n" +
                 "          EA\n" +
                 "        </OrderQtyUOM>\n" +
                 "        <PurchasePrice>\n" +
                 "          7.18\n" +
                 "        </PurchasePrice>\n" +
                 "        <PurchasePriceBasis>\n" +
                 "          PE\n" +
                 "        </PurchasePriceBasis>\n" +
                 "      </OrderLine>\n" +
                 "    </LineItem>\n" +
                 "    <LineItem>\n" +
                 "      <OrderLine>\n" +
                 "        <LineSequenceNumber>\n" +
                 "          5\n" +
                 "        </LineSequenceNumber>\n" +
                 "        <ConsumerPackageCode>\n" +
                 "          883096146834\n" +
                 "        </ConsumerPackageCode>\n" +
                 "        <OrderQty>\n" +
                 "          1\n" +
                 "        </OrderQty>\n" +
                 "        <OrderQtyUOM>\n" +
                 "          EA\n" +
                 "        </OrderQtyUOM>\n" +
                 "        <PurchasePrice>\n" +
                 "          7.18\n" +
                 "        </PurchasePrice>\n" +
                 "        <PurchasePriceBasis>\n" +
                 "          PE\n" +
                 "        </PurchasePriceBasis>\n" +
                 "      </OrderLine>\n" +
                 "    </LineItem>\n" +
                 "  </LineItems>\n" +
                 "  <Summary>\n" +
                 "    <TotalLineItemNumber>\n" +
                 "      5\n" +
                 "    </TotalLineItemNumber>\n" +
                 "    <TotalQuantity>\n" +
                 "      13\n" +
          >I       "    </TotalQuantity>\n" +
                 "  </Summary>\n" +
                 "</Order>"*/

        String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceUtilities.getEdiConfigData(po)
            Order order = SpsCommerceUtilities.importPo(po, config.clientFkey)
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
            order.setShippingMethodName("LTL");
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
            PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "12345678912",true);

            //we have a shipped order, now create the ASN file for it

            String asnData = SpsCommerceUtilities.generateASN(Integer.parseInt(order.orderID),config.clientFkey)

            println asnData
            println("This is after the print asn")
            println "loading owd order";
            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            String ack = SpsCommerceUtilities.generateACK(config,po,471,oorder)
            println ack
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            //   ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }
    @Test
    void testloadOrderFromPobuybuyBabyBumble() {
        System.setProperty("com.owd.environment","test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>832FWSBUMBLERID</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>AV9M5NE</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2016-12-09</PurchaseOrderDate>\n" +
                "            <Vendor>044214</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                "            <Date1>2016-12-11</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>3057</AddressLocationNumber>\n" +
                "            <AddressName>buybuyBaby Store #3057</AddressName>\n" +
                "            <Address1>12055 Metcalf Avenue</Address1>\n" +
                "            <City>Overland Park</City>\n" +
                "            <State>KS</State>\n" +
                "            <PostalCode>66213-1121</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <VendorPartNumber>I-800SVB</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>812812014898</ConsumerPackageCode>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>318</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>" +
                ""

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceUtilities.getEdiConfigData(po)
            Order order = SpsCommerceUtilities.importPo(po, 489)


            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment=false
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            order.facilityCode='DC6'
            order.facilityPolicy='DC6'
            order.setShippingMethodName("LTL")


            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
            PackingManager.packAndShip(Integer.parseInt(order.orderID), 1.2, 0.00, "030143972708176",true);

            //we have a shipped order, now create the ASN file for it
            String asnData = SpsCommerceUtilities.generateASN(Integer.parseInt(order.orderID),489)
            println asnData
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
             ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }
        @Test
        void testloadOrderFromPoAmazonBumble() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "  <Header>\n" +
                        "    <OrderHeader>\n" +
                        "      <TradingPartnerId>\n" +
                        "        080FWSBUMBLSYMP\n" +
                        "      </TradingPartnerId>\n" +
                        "      <PurchaseOrderNumber>\n" +
                        "        3WXUIZBT\n" +
                        "      </PurchaseOrderNumber>\n" +
                        "      <TsetPurposeCode>\n" +
                        "        00\n" +
                        "      </TsetPurposeCode>\n" +
                        "      <PurchaseOrderTypeCode>\n" +
                        "        SA\n" +
                        "      </PurchaseOrderTypeCode>\n" +
                        "      <PurchaseOrderDate>\n" +
                        "        2016-12-19\n" +
                        "      </PurchaseOrderDate>\n" +
                        "      <ShipCompleteCode>\n" +
                        "        Y\n" +
                        "      </ShipCompleteCode>\n" +
                        "      <Vendor>\n" +
                        "        BUMAQ\n" +
                        "      </Vendor>\n" +
                        "    </OrderHeader>\n" +
                        "    <Date>\n" +
                        "      <DateTimeQualifier1>\n" +
                        "        064\n" +
                        "      </DateTimeQualifier1>\n" +
                        "      <Date1>\n" +
                        "        2016-12-19\n" +
                        "      </Date1>\n" +
                        "    </Date>\n" +
                        "    <Date>\n" +
                        "      <DateTimeQualifier1>\n" +
                        "        063\n" +
                        "      </DateTimeQualifier1>\n" +
                        "      <Date1>\n" +
                        "        2016-12-28\n" +
                        "      </Date1>\n" +
                        "    </Date>\n" +
                        "    <Address>\n" +
                        "      <AddressTypeCode>\n" +
                        "        ST\n" +
                        "      </AddressTypeCode>\n" +
                        "      <LocationCodeQualifier>\n" +
                        "        15\n" +
                        "      </LocationCodeQualifier>\n" +
                        "      <AddressLocationNumber>\n" +
                        "        8001774\n" +
                        "      </AddressLocationNumber>\n" +
                        "      <AddressName>\n" +
                        "        Amazon.com\n" +
                        "      </AddressName>\n" +
                        "      <Address1>\n" +
                        "        21 Roadway Dr\n" +
                        "      </Address1>\n" +
                        "      <City>\n" +
                        "        Carlisle\n" +
                        "      </City>\n" +
                        "      <State>\n" +
                        "        PA\n" +
                        "      </State>\n" +
                        "      <PostalCode>\n" +
                        "        17015\n" +
                        "      </PostalCode>\n" +
                        "      <Country>\n" +
                        "        US\n" +
                        "      </Country>\n" +
                        "    </Address>\n" +
                        "  </Header>\n" +
                        "  <LineItems>\n" +
                        "    <LineItem>\n" +
                        "      <OrderLine>\n" +
                        "        <LineSequenceNumber>\n" +
                        "          1\n" +
                        "        </LineSequenceNumber>\n" +
                        "        <ConsumerPackageCode>\n" +
                        "          812812014881\n" +
                        "        </ConsumerPackageCode>\n" +
                        "        <OrderQty>\n" +
                        "          1\n" +
                        "        </OrderQty>\n" +
                        "        <OrderQtyUOM>\n" +
                        "          EA\n" +
                        "        </OrderQtyUOM>\n" +
                        "        <PurchasePrice>\n" +
                        "          374\n" +
                        "        </PurchasePrice>\n" +
                        "        <PurchasePriceBasis>\n" +
                        "          PE\n" +
                        "        </PurchasePriceBasis>\n" +
                        "      </OrderLine>\n" +
                        "    </LineItem>\n" +
                        "    <LineItem>\n" +
                        "      <OrderLine>\n" +
                        "        <LineSequenceNumber>\n" +
                        "          3\n" +
                        "        </LineSequenceNumber>\n" +
                        "        <ConsumerPackageCode>\n" +
                        "          812812014898\n" +
                        "        </ConsumerPackageCode>\n" +
                        "        <OrderQty>\n" +
                        "          1\n" +
                        "        </OrderQty>\n" +
                        "        <OrderQtyUOM>\n" +
                        "          EA\n" +
                        "        </OrderQtyUOM>\n" +
                        "        <PurchasePrice>\n" +
                        "          360\n" +
                        "        </PurchasePrice>\n" +
                        "        <PurchasePriceBasis>\n" +
                        "          PE\n" +
                        "        </PurchasePriceBasis>\n" +
                        "      </OrderLine>\n" +
                        "    </LineItem>\n" +
                        "  </LineItems>\n" +
                        "  <Summary>\n" +
                        "    <TotalLineItemNumber>\n" +
                        "      2\n" +
                        "    </TotalLineItemNumber>\n" +
                        "    <TotalQuantity>\n" +
                        "      2\n" +
                        "    </TotalQuantity>\n" +
                        "  </Summary>\n" +
                        "</Order>" +
                        ""

                try {
                        println poData
                        // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
                        // needed translation from the EDI SKU to the OWD SKU.
                        // Currently, we support either looking SKUs up from the upc_code field or
                        // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
                        // for rows connected to the relevant edi_sps_configdata entry
                        int docId = SpsCommerceUtilities.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml")
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        EdiSpsConfigdata config = SpsCommerceUtilities.getEdiConfigData(po)
                        Order order = SpsCommerceUtilities.importPo(po, 489)


                        //next three lines normally happen in API when Symphony posts order
                        order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
                        order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
                        order.order_type = order.order_type + ":EDI:";
                        order.forcePayment=false
                        order.is_paid = 1
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

                        //this must match the facility that your test SKU has inventory in
                        order.facilityCode='DC6'
                        order.facilityPolicy='DC6'

                        // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
                        // but for testing this doesn't work so we will simulate what happens to the order in that process.
                        // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
                        //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
                        String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                        println reference

                        // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
                        PackingManager.packAndShip(Integer.parseInt(order.orderID), 1.2, 0.00, "030143972708176",true);

                        //we have a shipped order, now create the ASN file for it
                        String asnData = SpsCommerceUtilities.generateASN(Integer.parseInt(order.orderID),489)
                        println asnData
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                         // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


                }catch (Exception ex)
                {
                        ex.printStackTrace()
                }

        }
    @Test
    void testloadOrderFromPoToysrus() {
          System.setProperty("com.owd.environment","test");

        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>7F6FWSBUMBLSYMP</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>1004312237350015</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2016-04-03</PurchaseOrderDate>\n" +
                "            <Vendor>TRZL</Vendor>\n" +
                "            <CustomerAccountNumber>15081</CustomerAccountNumber>\n" +
                "            <CustomerOrderNumber>3539290818</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>Nick Grewal</AddressName>\n" +
                "            <Address1>306 Edgewater Dr</Address1>\n" +
                "            <City>Gilford</City>\n" +
                "            <State>NH</State>\n" +
                "            <PostalCode>03249-6301</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <PrimaryPhone>6038921444</PrimaryPhone>\n" +
                "                <PrimaryEmail>nsgrewal@mac.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Beverley Raymond</AddressName>\n" +
                "            <Address1>7 Cornell Rd</Address1>\n" +
                "            <City>Haverhill</City>\n" +
                "            <State>MA</State>\n" +
                "            <PostalCode>01832-3762</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <Contact>\n" +
                "                <PrimaryPhone>9786972385</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>SF</AddressTypeCode>\n" +
                "            <AddressName>TRZL</AddressName>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>YD</ReferenceQual>\n" +
                "            <ReferenceID>438552827</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>BB</ReferenceQual>\n" +
                "            <ReferenceID>84010</ReferenceID>\n" +
                "        </Reference>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>60</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>940271</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>812812011859</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumber>C8E61011</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>145</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ProductDescription>BUMBLERIDE FLITE AQUAMARINE</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        try {
            println poData
            // first, set up a SKU with inventory in the staging database under the Symphony account to reflect the
            // needed translation from the EDI SKU to the OWD SKU.
            // Currently, we support either looking SKUs up from the upc_code field or
            // checking the VendorPartNumber EDI element against a lookup in the edi_sps_configdata_skus table
            // for rows connected to the relevant edi_sps_configdata entry
            int docId = SpsCommerceUtilities.saveIncomingEdiDocForTesting(poData,"POtestfiletoysrus.xml")
            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po =       parser.parseText(poData)
            EdiSpsConfigdata config = SpsCommerceUtilities.getEdiConfigData(po)
            Order order = SpsCommerceUtilities.importPo(po, 489)


            //next three lines normally happen in API when Symphony posts order
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.addTag(TagUtilities.kVendorComplianceReference, config.getName());
            order.order_type = order.order_type + ":EDI:";
            order.forcePayment=false
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            //this must match the facility that your test SKU has inventory in
            order.facilityCode='DC6'
            order.facilityPolicy='DC6'

            // Next line is what happens in production - order is posted to Symphony, Symphony posts order to API, returns OK if all successful
            // but for testing this doesn't work so we will simulate what happens to the order in that process.
            // We are not simulating the assignment of shipping accounts - this happens in the API project in EDIUtilities.applyEdiShippingMethod()
            //  println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
            String  reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference

            // ship it. This will need to be changed to support SSCC barcodes or lot or serial information
            PackingManager.packAndShip(Integer.parseInt(order.orderID), 1.2, 0.00, "030143972708176");

            //we have a shipped order, now create the ASN file for it
            String asnData = SpsCommerceUtilities.generateASN(Integer.parseInt(order.orderID),489)
            println asnData
             SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
         //   ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        }catch (Exception ex)
        {
            ex.printStackTrace()
        }

    }

 /*       @Test
        void testloadOrderFromPoBrookstone() {
             //   System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>8S3FWDCHILITECH</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>843494470001</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2015-05-15</PurchaseOrderDate>\n" +
                        "            <Vendor>D28823</Vendor>\n" +
                        "        </OrderHeader>\n" +
                        "        <PaymentTerms>\n" +
                        "            <TermsType>14</TermsType>\n" +
                        "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                        "        </PaymentTerms>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>SEAN CONDON</AddressName>\n" +
                        "            <AddressAlternateName>BROOKSTONE</AddressAlternateName>\n" +
                        "            <Address1>1 INNOVATION WAY</Address1>\n" +
                        "            <City>MERRIMACK</City>\n" +
                        "            <State>NH</State>\n" +
                        "            <PostalCode>03054</PostalCode>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>OC</ContactTypeCode>\n" +
                        "                <ContactName>SEAN CONDON</ContactName>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>SEAN CONDON</AddressName>\n" +
                        "            <AddressAlternateName>BROOKSTONE</AddressAlternateName>\n" +
                        "            <Address1>1 INNOVATION WAY</Address1>\n" +
                        "            <City>MERRIMACK</City>\n" +
                        "            <State>NH</State>\n" +
                        "            <PostalCode>03054</PostalCode>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>OC</ContactTypeCode>\n" +
                        "                <ContactName>SEAN CONDON</ContactName>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierRouting>02</CarrierRouting>\n" +
                        "        </CarrierInformation>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <BuyerPartNumber>892859</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>CP502</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>000000000000</ConsumerPackageCode>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>285</PurchasePrice>\n" +
                        "            </OrderLine>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                        "                <ProductDescription>CHILIPAD CUBE SNG ZN TWX</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                        "    </Summary>\n" +
                        "</Order>"

                try {
                        println poData
                        int docId = SpsCommerceUtilities.saveIncomingEdiDoc(poData,"POtestfile.xml")
                        XmlParser parser = new XmlParser()
                        parser.setTrimWhitespace(true)
                        Node po =       parser.parseText(poData)
                        Order order = SpsCommerceUtilities.importPo(po, 489)


                        println SymphonyAPI.postOrderToSymphony(order,docId, po,true)
                }catch (Exception ex)
                {
                        ex.printStackTrace()
                }

        }*/

}

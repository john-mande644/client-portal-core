package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.DogEaredZapposEDI
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsTargetEDI
import com.owd.jobs.jobobjects.spscommerce.clients.ROVRMooseJawEDI
import com.owd.jobs.jobobjects.spscommerce.clients.RovrBassProEDI
import com.owd.jobs.jobobjects.spscommerce.clients.RovrREIEDI
import org.junit.Test


class RovrMooseJawTesting extends GroovyTestCase {

    @Test
    void test1() {
        System.setProperty("com.owd.environment", "test");



        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>DGTALLROVRPRODU</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>RVRXRVR20210401IAR</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <POTypeDescription>Fill in Order/Ship at Once</POTypeDescription>\n" +
                "            <PurchaseOrderDate>2021-01-21</PurchaseOrderDate>\n" +
                "            <Vendor>RVR</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Net 30</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "            <Date1>2021-04-01</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                "            <Date1>2021-04-15</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>004</AddressLocationNumber>\n" +
                "            <AddressName>Moosejaw - Internet</AddressName>\n" +
                "            <Address1>32200 North Avis</Address1>\n" +
                "            <Address2>Suite 100</Address2>\n" +
                "            <City>Madison Heights</City>\n" +
                "            <State>MI</State>\n" +
                "            <PostalCode>48071</PostalCode>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>004</AddressLocationNumber>\n" +
                "            <AddressName>Moosejaw - Internet</AddressName>\n" +
                "            <Address1>32200 North Avis</Address1>\n" +
                "            <Address2>Suite 100</Address2>\n" +
                "            <City>Madison Heights</City>\n" +
                "            <State>MI</State>\n" +
                "            <PostalCode>48071</PostalCode>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>CG</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>6837769</BuyerPartNumber>\n" +
                "                <VendorPartNumber>IC25-DW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>854404008571</ConsumerPackageCode>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>100</PurchasePrice>\n" +
                "                <ProductSizeDescription>25qt</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Deepwater</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ICE CHEST 25 S21</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>6837771</BuyerPartNumber>\n" +
                "                <VendorPartNumber>IC35-DW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>854404008595</ConsumerPackageCode>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>120</PurchasePrice>\n" +
                "                <ProductSizeDescription>35qt</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Deepwater</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ICE CHEST 35 S21</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalAmount>880</TotalAmount>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "        <TotalQuantity>8</TotalQuantity>\n" +
                "    </Summary>\n" +
                "</Order>\n";

        try {
            ROVRMooseJawEDI edi = new ROVRMooseJawEDI();

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

                    //order.addTag(TagUtilities.kEDIZapposDN, "X12345");

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

            int a = 0;

        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }

    @Test
    void test2() {
        System.setProperty("com.owd.environment", "test");



        String poData = "<Order>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>DGTALLROVRPRODU</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>RVRXRVR20201015IAR</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <POTypeDescription>Fill in Order/Ship at Once</POTypeDescription>\n" +
                "            <PurchaseOrderDate>2020-10-15</PurchaseOrderDate>\n" +
                "            <Vendor>RVR</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDescription>Net 30</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>002</DateTimeQualifier1>\n" +
                "            <Date1>2020-10-15</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                "            <Date1>2020-10-29</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>004</AddressLocationNumber>\n" +
                "            <AddressName>Moosejaw - Internet</AddressName>\n" +
                "            <Address1>32200 North Avis</Address1>\n" +
                "            <Address2>Suite 100</Address2>\n" +
                "            <City>Madison Heights</City>\n" +
                "            <State>MI</State>\n" +
                "            <PostalCode>48071</PostalCode>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>004</AddressLocationNumber>\n" +
                "            <AddressName>Moosejaw - Internet</AddressName>\n" +
                "            <Address1>32200 North Avis</Address1>\n" +
                "            <Address2>Suite 100</Address2>\n" +
                "            <City>Madison Heights</City>\n" +
                "            <State>MI</State>\n" +
                "            <PostalCode>48071</PostalCode>\n" +
                "        </Address>\n" +
                "        <CarrierInformation>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>CG</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>5412238</BuyerPartNumber>\n" +
                "                <VendorPartNumber>45DROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007744</ConsumerPackageCode>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>222</PurchasePrice>\n" +
                "                <ProductSizeDescription>45qt</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Desert</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ROLLR 45 COOLR W/ WAGN BIN S19</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>5412241</BuyerPartNumber>\n" +
                "                <VendorPartNumber>45NBROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007737</ConsumerPackageCode>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>222</PurchasePrice>\n" +
                "                <ProductSizeDescription>45qt</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Narwhal Blue</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ROLLR 45 COOLR W/ WAGN BIN S19</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>5412242</BuyerPartNumber>\n" +
                "                <VendorPartNumber>60DROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007706</ConsumerPackageCode>\n" +
                "                <OrderQty>7</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>240</PurchasePrice>\n" +
                "                <ProductSizeDescription>60qt</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Desert</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ROLLR 60 COOLR W/ WAGN BIN S19</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>5412243</BuyerPartNumber>\n" +
                "                <VendorPartNumber>60MROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007713</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>240</PurchasePrice>\n" +
                "                <ProductSizeDescription>60qt</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Moss</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ROLLR 60 COOLR W/ WAGN BIN S19</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>5</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>5412244</BuyerPartNumber>\n" +
                "                <VendorPartNumber>60PROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007690</ConsumerPackageCode>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>240</PurchasePrice>\n" +
                "                <ProductSizeDescription>60qt</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Powder</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ROLLR 60 COOLR W/ WAGN BIN S19</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>6</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>5412247</BuyerPartNumber>\n" +
                "                <VendorPartNumber>80PROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007782</ConsumerPackageCode>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>270</PurchasePrice>\n" +
                "                <ProductSizeDescription>80qt</ProductSizeDescription>\n" +
                "                <ProductColorDescription>Powder</ProductColorDescription>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ROLLR 80 COOLR W/ WAGN BIN S19</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalAmount>5082</TotalAmount>\n" +
                "        <TotalLineItemNumber>6</TotalLineItemNumber>\n" +
                "        <TotalQuantity>21</TotalQuantity>\n" +
                "    </Summary>\n" +
                "</Order>\n";

        try {
            ROVRMooseJawEDI edi = new ROVRMooseJawEDI();

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

                    //order.addTag(TagUtilities.kEDIZapposDN, "X12345");

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

            int a = 0;

        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }








}

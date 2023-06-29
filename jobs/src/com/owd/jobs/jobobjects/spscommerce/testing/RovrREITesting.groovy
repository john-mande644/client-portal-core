package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsTargetEDI
import com.owd.jobs.jobobjects.spscommerce.clients.RovrBassProEDI
import com.owd.jobs.jobobjects.spscommerce.clients.RovrREIEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class RovrREITesting extends GroovyTestCase {





        @Test
        void test1WarehouseFromPoREI() {
                System.setProperty("com.owd.environment", "test");



            String poData = "<Order>\n" +
                    "    <Meta>\n" +
                    "        <InterchangeControlNumber>000000554</InterchangeControlNumber>\n" +
                    "        <GroupControlNumber>111</GroupControlNumber>\n" +
                    "        <DocumentControlNumber>0154</DocumentControlNumber>\n" +
                    "        <InterchangeSenderID>009483355</InterchangeSenderID>\n" +
                    "        <InterchangeReceiverID>40149195YK</InterchangeReceiverID>\n" +
                    "        <GroupSenderID>009483355</GroupSenderID>\n" +
                    "        <GroupReceiverID>40149195YK</GroupReceiverID>\n" +
                    "    </Meta>\n" +
                    "    <Header>\n" +
                    "        <OrderHeader>\n" +
                    "            <TradingPartnerId>031ALLROVRPRODU</TradingPartnerId>\n" +
                    "            <PurchaseOrderNumber>4511982876</PurchaseOrderNumber>\n" +
                    "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                    "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                    "            <PurchaseOrderDate>2020-10-16</PurchaseOrderDate>\n" +
                    "            <Vendor>0000046144</Vendor>\n" +
                    "            <PromotionDealDescription>Standard Merch PO</PromotionDealDescription>\n" +
                    "        </OrderHeader>\n" +
                    "        <PaymentTerms>\n" +
                    "            <TermsDiscountPercentage>0</TermsDiscountPercentage>\n" +
                    "            <TermsDiscountDueDays>60</TermsDiscountDueDays>\n" +
                    "            <TermsDescription>Payable Net in 60 days</TermsDescription>\n" +
                    "        </PaymentTerms>\n" +
                    "        <Date>\n" +
                    "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                    "            <Date1>2020-10-19</Date1>\n" +
                    "        </Date>\n" +
                    "        <Date>\n" +
                    "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                    "            <Date1>2020-10-21</Date1>\n" +
                    "        </Date>\n" +
                    "        <Contact>\n" +
                    "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                    "            <ContactName>Camping Gear 1</ContactName>\n" +
                    "        </Contact>\n" +
                    "        <Address>\n" +
                    "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                    "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                    "            <AddressLocationNumber>0003</AddressLocationNumber>\n" +
                    "            <AddressName>REI GOODYEAR DISTRIBUTION CENTER</AddressName>\n" +
                    "            <Address1>4877 N. Cotton Lane</Address1>\n" +
                    "            <City>Goodyear</City>\n" +
                    "            <State>AZ</State>\n" +
                    "            <PostalCode>85395</PostalCode>\n" +
                    "        </Address>\n" +
                    "        <FOBRelatedInstruction>\n" +
                    "            <FOBPayCode>CC</FOBPayCode>\n" +
                    "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                    "            <FOBTitlePassageLocation>Freight Collect</FOBTitlePassageLocation>\n" +
                    "        </FOBRelatedInstruction>\n" +
                    "        <CarrierInformation>\n" +
                    "            <CarrierRouting>REI Vendor Guide</CarrierRouting>\n" +
                    "        </CarrierInformation>\n" +
                    "        <Notes>\n" +
                    "            <NoteInformationField>THIS P.O. IS EXPRESSLY LIMITED TO AND CONDITIONED ON YOUR ACCEPTANCE OF ITS TERMS AND REI'S \"PURCHASE ORDER TERMS AND CONDITIONS (for Product Resale)\" AT https://partners.rei.com/vendor_guide/standard_terms.pdf. REI OBJECTS TO ANY DIFFERENT OR ADDITIONAL TERMS.</NoteInformationField>\n" +
                    "        </Notes>\n" +
                    "        <Notes>\n" +
                    "            <NoteInformationField>Payable Net in 60 days</NoteInformationField>\n" +
                    "        </Notes>\n" +
                    "    </Header>\n" +
                    "    <LineItems>\n" +
                    "        <LineItem>\n" +
                    "            <OrderLine>\n" +
                    "                <LineSequenceNumber>00010</LineSequenceNumber>\n" +
                    "                <BuyerPartNumber>1498030001</BuyerPartNumber>\n" +
                    "                <VendorPartNumber>60DROLLRW</VendorPartNumber>\n" +
                    "                <ConsumerPackageCode>852490007706</ConsumerPackageCode>\n" +
                    "                <OrderQty>43</OrderQty>\n" +
                    "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                    "                <PurchasePrice>230.4</PurchasePrice>\n" +
                    "            </OrderLine>\n" +
                    "            <ProductOrItemDescription>\n" +
                    "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                    "                <ProductDescription>RollR 60 Cooler, DESERT, No Size</ProductDescription>\n" +
                    "            </ProductOrItemDescription>\n" +
                    "        </LineItem>\n" +
                    "        <LineItem>\n" +
                    "            <OrderLine>\n" +
                    "                <LineSequenceNumber>00020</LineSequenceNumber>\n" +
                    "                <BuyerPartNumber>1498030003</BuyerPartNumber>\n" +
                    "                <VendorPartNumber>60PROLLRW</VendorPartNumber>\n" +
                    "                <ConsumerPackageCode>852490007690</ConsumerPackageCode>\n" +
                    "                <OrderQty>22</OrderQty>\n" +
                    "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                    "                <PurchasePrice>230.4</PurchasePrice>\n" +
                    "            </OrderLine>\n" +
                    "            <ProductOrItemDescription>\n" +
                    "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                    "                <ProductDescription>RollR 60 Cooler, POWDER, No Size</ProductDescription>\n" +
                    "            </ProductOrItemDescription>\n" +
                    "        </LineItem>\n" +
                    "    </LineItems>\n" +
                    "    <Summary>\n" +
                    "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                    "    </Summary>\n" +
                    "</Order>"
        try {
            RovrREIEDI edi = new  RovrREIEDI();

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
          //  ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
          //   ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)


        } catch (Exception ex) {
            ex.printStackTrace()
        }

    }

    @Test
    void test2WarehouseFromREI() {
        System.setProperty("com.owd.environment", "test");



        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000000552</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>109</GroupControlNumber>\n" +
                "        <DocumentControlNumber>0152</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>009483355</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>40149195YK</InterchangeReceiverID>\n" +
                "        <GroupSenderID>009483355</GroupSenderID>\n" +
                "        <GroupReceiverID>40149195YK</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>031ALLROVRPRODU</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>4512002760</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-10-16</PurchaseOrderDate>\n" +
                "            <Vendor>0000046144</Vendor>\n" +
                "            <PromotionDealDescription>Standard Merch PO</PromotionDealDescription>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsDiscountPercentage>0</TermsDiscountPercentage>\n" +
                "            <TermsDiscountDueDays>60</TermsDiscountDueDays>\n" +
                "            <TermsDescription>Payable Net in 60 days</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>037</DateTimeQualifier1>\n" +
                "            <Date1>2020-10-19</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                "            <Date1>2020-10-23</Date1>\n" +
                "        </Date>\n" +
                "        <Contact>\n" +
                "            <ContactTypeCode>BD</ContactTypeCode>\n" +
                "            <ContactName>Camping Gear 1</ContactName>\n" +
                "        </Contact>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0003</AddressLocationNumber>\n" +
                "            <AddressName>REI GOODYEAR DISTRIBUTION CENTER</AddressName>\n" +
                "            <Address1>4877 N. Cotton Lane</Address1>\n" +
                "            <City>Goodyear</City>\n" +
                "            <State>AZ</State>\n" +
                "            <PostalCode>85395</PostalCode>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>CC</FOBPayCode>\n" +
                "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                "            <FOBTitlePassageLocation>Freight Collect</FOBTitlePassageLocation>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <CarrierRouting>REI Vendor Guide</CarrierRouting>\n" +
                "        </CarrierInformation>\n" +
                "        <Notes>\n" +
                "            <NoteInformationField>THIS P.O. IS EXPRESSLY LIMITED TO AND CONDITIONED ON YOUR ACCEPTANCE OF ITS TERMS AND REI'S \"PURCHASE ORDER TERMS AND CONDITIONS (for Product Resale)\" AT https://partners.rei.com/vendor_guide/standard_terms.pdf. REI OBJECTS TO ANY DIFFERENT OR ADDITIONAL TERMS.</NoteInformationField>\n" +
                "        </Notes>\n" +
                "        <Notes>\n" +
                "            <NoteInformationField>Payable Net in 60 days</NoteInformationField>\n" +
                "        </Notes>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>00010</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1498030001</BuyerPartNumber>\n" +
                "                <VendorPartNumber>60DROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007706</ConsumerPackageCode>\n" +
                "                <OrderQty>14</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>230.4</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>RollR 60 Cooler, DESERT, No Size</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>00020</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1498030002</BuyerPartNumber>\n" +
                "                <VendorPartNumber>60MROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007713</ConsumerPackageCode>\n" +
                "                <OrderQty>9</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>230.4</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>RollR 60 Cooler, MOSS, No Size</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>00030</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>1498030003</BuyerPartNumber>\n" +
                "                <VendorPartNumber>60PROLLRW</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>852490007690</ConsumerPackageCode>\n" +
                "                <OrderQty>36</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>230.4</PurchasePrice>\n" +
                "            </OrderLine>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>RollR 60 Cooler, POWDER, No Size</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>\n"
        try {
            RovrREIEDI edi = new  RovrREIEDI();

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








}

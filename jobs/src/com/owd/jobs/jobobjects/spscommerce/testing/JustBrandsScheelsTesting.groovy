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
import com.owd.jobs.jobobjects.spscommerce.clients.JustBrandsScheelsEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class JustBrandsScheelsTesting extends GroovyTestCase {





    @Test
    void test1Scheels() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>BPRALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>Web-20953959</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2019-09-09</PurchaseOrderDate>\n" +
                "            <Vendor>675595</Vendor>\n" +
                "            <CustomerAccountNumber>1</CustomerAccountNumber>\n" +
                "            <CustomerOrderNumber>2</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Amanda LaPlante</AddressName>\n" +
                "            <Address1>1513 6th Avenue Northwest</Address1>\n" +
                "            <City>East Grand Forks</City>\n" +
                "            <State>MN</State>\n" +
                "            <PostalCode>56721</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>Amanda LaPlante</ContactName>\n" +
                "                <PrimaryPhone>7017402456</PrimaryPhone>\n" +
                "                <PrimaryEmail>amanda_laplante11@hotmail.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>066</AddressLocationNumber>\n" +
                "            <AddressName>Fargo Scheels All Sport</AddressName>\n" +
                "            <Address1>1551 45th St S</Address1>\n" +
                "            <City>Fargo</City>\n" +
                "            <State>ND</State>\n" +
                "            <PostalCode>58103</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>ContactName</ContactName>\n" +
                "                <PrimaryPhone>7015555555</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>PP</FOBPayCode>\n" +
                "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>SG</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>675595807848</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>675595807848</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>100</PurchasePrice>\n" +
                "                <Department>48</Department>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>199.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Women's ActionHeat 5V Battery Heated Jacket</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>AH</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>675595807671</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>675595807671</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>100</PurchasePrice>\n" +
                "                <Department>48</Department>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>199.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Men's ActionHeat 5V Battery Heated Jacket</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>AH</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>2</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsScheelsEDI edi = new JustBrandsScheelsEDI();

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
           //     OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID),Integer.parseInt(order.getClientID()), true)
            //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
          //    String ack = edi.generateACK(config,po,626,oorder)
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
    void test2Scheels() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>BPRALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>Web-120769086</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2019-09-06</PurchaseOrderDate>\n" +
                "            <Vendor>675595</Vendor>\n" +
                "            <CustomerAccountNumber>1</CustomerAccountNumber>\n" +
                "            <CustomerOrderNumber>2</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Ehrin Mckaig</AddressName>\n" +
                "            <Address1>4550 15TH AVE S</Address1>\n" +
                "            <City>FARGO</City>\n" +
                "            <State>ND</State>\n" +
                "            <PostalCode>58103</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>Ehrin Mckaig</ContactName>\n" +
                "                <PrimaryPhone>7012326834</PrimaryPhone>\n" +
                "                <PrimaryEmail>emckaig@scheels.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>066</AddressLocationNumber>\n" +
                "            <AddressName>Fargo Scheels All Sport</AddressName>\n" +
                "            <Address1>1551 45th St S</Address1>\n" +
                "            <City>Fargo</City>\n" +
                "            <State>ND</State>\n" +
                "            <PostalCode>58103</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>ContactName</ContactName>\n" +
                "                <PrimaryPhone>7015555555</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>PP</FOBPayCode>\n" +
                "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>SG</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>675595809804</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>675595809804</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>95</PurchasePrice>\n" +
                "                <Department>48</Department>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>95</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>Womens ActionHeat 5V Battery Heated Puffer Vest</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>AH</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsScheelsEDI edi = new JustBrandsScheelsEDI();

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
    void test3Sheels() {
        System.setProperty("com.owd.environment", "test");

        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <IsDropShip>true</IsDropShip>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>BPRALLJUSTBRAND</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>Web-20945823</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>DS</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2019-09-09</PurchaseOrderDate>\n" +
                "            <Vendor>675595</Vendor>\n" +
                "            <CustomerAccountNumber>1</CustomerAccountNumber>\n" +
                "            <CustomerOrderNumber>2</CustomerOrderNumber>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>Michael Smith</AddressName>\n" +
                "            <Address1>13407 W 72nd St</Address1>\n" +
                "            <City>Shawnee</City>\n" +
                "            <State>KS</State>\n" +
                "            <PostalCode>66216</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>Michael Smith</ContactName>\n" +
                "                <PrimaryPhone>9132064868</PrimaryPhone>\n" +
                "                <PrimaryEmail>smithmw99@yahoo.com</PrimaryEmail>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>066</AddressLocationNumber>\n" +
                "            <AddressName>Fargo Scheels All Sport</AddressName>\n" +
                "            <Address1>1551 45th St S</Address1>\n" +
                "            <City>Fargo</City>\n" +
                "            <State>ND</State>\n" +
                "            <PostalCode>58103</PostalCode>\n" +
                "            <Country>USA</Country>\n" +
                "            <Contact>\n" +
                "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                "                <ContactName>ContactName</ContactName>\n" +
                "                <PrimaryPhone>7015555555</PrimaryPhone>\n" +
                "            </Contact>\n" +
                "        </Address>\n" +
                "        <FOBRelatedInstruction>\n" +
                "            <FOBPayCode>PP</FOBPayCode>\n" +
                "            <FOBLocationQualifier>OR</FOBLocationQualifier>\n" +
                "        </FOBRelatedInstruction>\n" +
                "        <CarrierInformation>\n" +
                "            <ServiceLevelCodes>\n" +
                "                <ServiceLevelCode>SG</ServiceLevelCode>\n" +
                "            </ServiceLevelCodes>\n" +
                "        </CarrierInformation>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>675595810398</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>675595810398</ConsumerPackageCode>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>40</PurchasePrice>\n" +
                "                <Department>48</Department>\n" +
                "            </OrderLine>\n" +
                "            <PriceInformation>\n" +
                "                <PriceTypeIDCode>RTL</PriceTypeIDCode>\n" +
                "                <UnitPrice>79.99</UnitPrice>\n" +
                "            </PriceInformation>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>ActionHeat 5V Battery Heated Seat Cushion</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "            <ProductOrItemDescription>\n" +
                "                <ItemDescriptionType>08</ItemDescriptionType>\n" +
                "                <ProductDescription>AH</ProductDescription>\n" +
                "            </ProductOrItemDescription>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>"

        // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\jobs\\src\\com\\owd\\jobs\\jobobjects\\spscommerce","testorder.xml")))

        try {
            JustBrandsScheelsEDI edi = new JustBrandsScheelsEDI();

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
                        JustBrandsScheelsEDI edi = new JustBrandsScheelsEDI();

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
        void testAcknowledgementPoTarget() {
               // System.setProperty("com.owd.environment", "test");

                String poData = "<Order>\n" +
                        "  <Meta>\n" +
                        "    <IsDropShip>\n" +
                        "      true\n" +
                        "    </IsDropShip>\n" +
                        "    <InterchangeControlNumber>\n" +
                        "      000000068\n" +
                        "    </InterchangeControlNumber>\n" +
                        "    <GroupControlNumber>\n" +
                        "      6\n" +
                        "    </GroupControlNumber>\n" +
                        "    <DocumentControlNumber>\n" +
                        "      419900272\n" +
                        "    </DocumentControlNumber>\n" +
                        "    <InterchangeSenderID>\n" +
                        "      TGTDVS\n" +
                        "    </InterchangeSenderID>\n" +
                        "    <InterchangeReceiverID>\n" +
                        "      8884061984\n" +
                        "    </InterchangeReceiverID>\n" +
                        "    <GroupSenderID>\n" +
                        "      TGTDVS\n" +
                        "    </GroupSenderID>\n" +
                        "    <GroupReceiverID>\n" +
                        "      8884061984\n" +
                        "    </GroupReceiverID>\n" +
                        "  </Meta>\n" +
                        "  <Header>\n" +
                        "    <OrderHeader>\n" +
                        "      <TradingPartnerId>\n" +
                        "        95OALLJUSTBRAND\n" +
                        "      </TradingPartnerId>\n" +
                        "      <PurchaseOrderNumber>\n" +
                        "        6102616657\n" +
                        "      </PurchaseOrderNumber>\n" +
                        "      <TsetPurposeCode>\n" +
                        "        00\n" +
                        "      </TsetPurposeCode>\n" +
                        "      <PurchaseOrderTypeCode>\n" +
                        "        DS\n" +
                        "      </PurchaseOrderTypeCode>\n" +
                        "      <ReleaseNumber>\n" +
                        "        6102616657\n" +
                        "      </ReleaseNumber>\n" +
                        "      <PurchaseOrderDate>\n" +
                        "        2019-08-18\n" +
                        "      </PurchaseOrderDate>\n" +
                        "      <Vendor>\n" +
                        "        202886\n" +
                        "      </Vendor>\n" +
                        "    </OrderHeader>\n" +
                        "    <Date>\n" +
                        "      <DateTimeQualifier1>\n" +
                        "        001\n" +
                        "      </DateTimeQualifier1>\n" +
                        "      <Date1>\n" +
                        "        2019-08-20\n" +
                        "      </Date1>\n" +
                        "    </Date>\n" +
                        "    <Date>\n" +
                        "      <DateTimeQualifier1>\n" +
                        "        006\n" +
                        "      </DateTimeQualifier1>\n" +
                        "      <Date1>\n" +
                        "        2019-08-18\n" +
                        "      </Date1>\n" +
                        "    </Date>\n" +
                        "    <Date>\n" +
                        "      <DateTimeQualifier1>\n" +
                        "        017\n" +
                        "      </DateTimeQualifier1>\n" +
                        "      <Date1>\n" +
                        "        2019-08-23\n" +
                        "      </Date1>\n" +
                        "    </Date>\n" +
                        "    <Address>\n" +
                        "      <AddressTypeCode>\n" +
                        "        BT\n" +
                        "      </AddressTypeCode>\n" +
                        "      <AddressName>\n" +
                        "        Target.com Accounts Payable\n" +
                        "      </AddressName>\n" +
                        "      <Address1>\n" +
                        "        TNC 3110\n" +
                        "      </Address1>\n" +
                        "      <Address2>\n" +
                        "        PO Box 1296\n" +
                        "      </Address2>\n" +
                        "      <City>\n" +
                        "        Minneapolis\n" +
                        "      </City>\n" +
                        "      <State>\n" +
                        "        MN\n" +
                        "      </State>\n" +
                        "      <PostalCode>\n" +
                        "        55440\n" +
                        "      </PostalCode>\n" +
                        "      <Country>\n" +
                        "        US\n" +
                        "      </Country>\n" +
                        "    </Address>\n" +
                        "    <Address>\n" +
                        "      <AddressTypeCode>\n" +
                        "        SO\n" +
                        "      </AddressTypeCode>\n" +
                        "      <AddressName>\n" +
                        "        Brenda Carlson\n" +
                        "      </AddressName>\n" +
                        "      <Address1>\n" +
                        "        66 Hazel Clark Rd\n" +
                        "      </Address1>\n" +
                        "      <City>\n" +
                        "        Center Barnstead\n" +
                        "      </City>\n" +
                        "      <State>\n" +
                        "        NH\n" +
                        "      </State>\n" +
                        "      <PostalCode>\n" +
                        "        03225-3419\n" +
                        "      </PostalCode>\n" +
                        "      <Country>\n" +
                        "        US\n" +
                        "      </Country>\n" +
                        "    </Address>\n" +
                        "    <Address>\n" +
                        "      <AddressTypeCode>\n" +
                        "        ST\n" +
                        "      </AddressTypeCode>\n" +
                        "      <AddressName>\n" +
                        "        Brenda Carlson\n" +
                        "      </AddressName>\n" +
                        "      <Address1>\n" +
                        "        66 Hazel Clark Rd\n" +
                        "      </Address1>\n" +
                        "      <City>\n" +
                        "        Center Barnstead\n" +
                        "      </City>\n" +
                        "      <State>\n" +
                        "        NH\n" +
                        "      </State>\n" +
                        "      <PostalCode>\n" +
                        "        03225-3419\n" +
                        "      </PostalCode>\n" +
                        "      <Country>\n" +
                        "        US\n" +
                        "      </Country>\n" +
                        "      <Contact>\n" +
                        "        <ContactTypeCode>\n" +
                        "          IC\n" +
                        "        </ContactTypeCode>\n" +
                        "        <ContactName>\n" +
                        "          Brenda Carlson\n" +
                        "        </ContactName>\n" +
                        "        <PrimaryPhone>\n" +
                        "          (508) 208-4115\n" +
                        "        </PrimaryPhone>\n" +
                        "        <PrimaryEmail>\n" +
                        "          brendacarlson@verizon.net\n" +
                        "        </PrimaryEmail>\n" +
                        "      </Contact>\n" +
                        "    </Address>\n" +
                        "    <CarrierInformation>\n" +
                        "      <CarrierAlphaCode>\n" +
                        "        UPSN\n" +
                        "      </CarrierAlphaCode>\n" +
                        "      <CarrierRouting>\n" +
                        "        NS\n" +
                        "      </CarrierRouting>\n" +
                        "      <ServiceLevelCodes>\n" +
                        "        <ServiceLevelCode>\n" +
                        "          G2\n" +
                        "        </ServiceLevelCode>\n" +
                        "      </ServiceLevelCodes>\n" +
                        "    </CarrierInformation>\n" +
                        "    <Reference>\n" +
                        "      <ReferenceQual>\n" +
                        "        CT\n" +
                        "      </ReferenceQual>\n" +
                        "      <ReferenceID>\n" +
                        "        9859\n" +
                        "      </ReferenceID>\n" +
                        "    </Reference>\n" +
                        "    <Reference>\n" +
                        "      <ReferenceQual>\n" +
                        "        OQ\n" +
                        "      </ReferenceQual>\n" +
                        "      <ReferenceID>\n" +
                        "        9053743836662\n" +
                        "      </ReferenceID>\n" +
                        "    </Reference>\n" +
                        "    <Reference>\n" +
                        "      <ReferenceQual>\n" +
                        "        D7\n" +
                        "      </ReferenceQual>\n" +
                        "      <ReferenceID>\n" +
                        "        36\n" +
                        "      </ReferenceID>\n" +
                        "    </Reference>\n" +
                        "    <Reference>\n" +
                        "      <ReferenceQual>\n" +
                        "        WS\n" +
                        "      </ReferenceQual>\n" +
                        "      <ReferenceID>\n" +
                        "        D840\n" +
                        "      </ReferenceID>\n" +
                        "    </Reference>\n" +
                        "    <Reference>\n" +
                        "      <ReferenceQual>\n" +
                        "        KK\n" +
                        "      </ReferenceQual>\n" +
                        "      <ReferenceID>\n" +
                        "        N\n" +
                        "      </ReferenceID>\n" +
                        "    </Reference>\n" +
                        "    <Reference>\n" +
                        "      <ReferenceQual>\n" +
                        "        L1\n" +
                        "      </ReferenceQual>\n" +
                        "      <ReferenceID>\n" +
                        "        MESSAGE\n" +
                        "      </ReferenceID>\n" +
                        "      <Description>\n" +
                        "        Thank you for your purchase. If you ordered a\n" +
                        "      </Description>\n" +
                        "    </Reference>\n" +
                        "    <Reference>\n" +
                        "      <ReferenceQual>\n" +
                        "        L1\n" +
                        "      </ReferenceQual>\n" +
                        "      <ReferenceID>\n" +
                        "        MESSAGE\n" +
                        "      </ReferenceID>\n" +
                        "      <Description>\n" +
                        "        dditional items they will arrive separately.\n" +
                        "      </Description>\n" +
                        "    </Reference>\n" +
                        "  </Header>\n" +
                        "  <LineItems>\n" +
                        "    <LineItem>\n" +
                        "      <OrderLine>\n" +
                        "        <LineSequenceNumber>\n" +
                        "          4\n" +
                        "        </LineSequenceNumber>\n" +
                        "        <BuyerPartNumber>\n" +
                        "          77061938\n" +
                        "        </BuyerPartNumber>\n" +
                        "        <VendorPartNumber>\n" +
                        "          AH-GV-AA-01-W\n" +
                        "        </VendorPartNumber>\n" +
                        "        <ConsumerPackageCode>\n" +
                        "          616245678494\n" +
                        "        </ConsumerPackageCode>\n" +
                        "        <ProductID>\n" +
                        "          <PartNumberQual>\n" +
                        "            CB\n" +
                        "          </PartNumberQual>\n" +
                        "          <PartNumber>\n" +
                        "            337-04-6179\n" +
                        "          </PartNumber>\n" +
                        "        </ProductID>\n" +
                        "        <OrderQty>\n" +
                        "          1\n" +
                        "        </OrderQty>\n" +
                        "        <OrderQtyUOM>\n" +
                        "          EA\n" +
                        "        </OrderQtyUOM>\n" +
                        "        <PurchasePrice>\n" +
                        "          27\n" +
                        "        </PurchasePrice>\n" +
                        "        <PurchasePriceBasis>\n" +
                        "          PE\n" +
                        "        </PurchasePriceBasis>\n" +
                        "      </OrderLine>\n" +
                        "      <ProductOrItemDescription>\n" +
                        "        <ItemDescriptionType>\n" +
                        "          08\n" +
                        "        </ItemDescriptionType>\n" +
                        "        <ProductDescription>\n" +
                        "          mtts nd glvs ActionHeat\n" +
                        "        </ProductDescription>\n" +
                        "      </ProductOrItemDescription>\n" +
                        "      <Reference>\n" +
                        "        <ReferenceQual>\n" +
                        "          L1\n" +
                        "        </ReferenceQual>\n" +
                        "        <ReferenceID>\n" +
                        "          MESSAGE\n" +
                        "        </ReferenceID>\n" +
                        "        <Description>\n" +
                        "          This item must be returned within 90 days of\n" +
                        "        </Description>\n" +
                        "      </Reference>\n" +
                        "      <Reference>\n" +
                        "        <ReferenceQual>\n" +
                        "          L1\n" +
                        "        </ReferenceQual>\n" +
                        "        <ReferenceID>\n" +
                        "          MESSAGE\n" +
                        "        </ReferenceID>\n" +
                        "        <Description>\n" +
                        "          the ship date\n" +
                        "        </Description>\n" +
                        "      </Reference>\n" +
                        "      <Notes>\n" +
                        "        <NoteInformationField>\n" +
                        "          Mail in or Store\n" +
                        "        </NoteInformationField>\n" +
                        "      </Notes>\n" +
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
                        JustBrandsScheelsEDI edi = new JustBrandsScheelsEDI();

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
                        EdiSpsConfigdata config = edi.getEdiConfigData(po)




                        //we have a shipped order, now create the ASN file for it



                        //  println("This is after the print asn")
                        //   println "loading owd order";
                            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(18839061,626, true)
                        //    println "This is the aknowledgmentXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                          String ack = edi.generateACK(config,po,626,oorder)
                           println ack
                        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

                        //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                       // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
                         ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirPath)


                } catch (Exception ex) {
                        ex.printStackTrace()
                }

        }

}

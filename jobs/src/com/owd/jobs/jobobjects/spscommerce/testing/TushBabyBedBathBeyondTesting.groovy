package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceDicksSportingGoodsUtilities
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities
import com.owd.jobs.jobobjects.spscommerce.clients.TushBabyBedBathBeyondEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class TushBabyBedBathBeyondTesting extends GroovyTestCase{

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
        void test1shipOrderFromPoBedBath() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>832FWDTUSHBABYI</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>FF6Z8BX</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2020-07-01</PurchaseOrderDate>\n" +
                        "            <Vendor>070181</Vendor>\n" +
                        "        </OrderHeader>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2020-07-03</Date1>\n" +
                        "        </Date>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                        "            <AddressLocationNumber>0655</AddressLocationNumber>\n" +
                        "            <AddressName>Bed Bath &amp; Beyond #0655</AddressName>\n" +
                        "            <Address1>5835 E Ann Road</Address1>\n" +
                        "            <City>North Las Vegas</City>\n" +
                        "            <State>NV</State>\n" +
                        "            <PostalCode>89115</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <VendorPartNumber>TB-Grey-V2</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>850006525003</ConsumerPackageCode>\n" +
                        "                <OrderQty>2</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>39.99</PurchasePrice>\n" +
                        "            </OrderLine>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <VendorPartNumber>TB-Black-V2</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>850006525010</ConsumerPackageCode>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>39.99</PurchasePrice>\n" +
                        "            </OrderLine>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <VendorPartNumber>TB-BlackGold-V2</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>850006525027</ConsumerPackageCode>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>39.99</PurchasePrice>\n" +
                        "            </OrderLine>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
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
                        int docId = TushBabyBedBathBeyondEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml",account)
                        TushBabyBedBathBeyondEDI edi = new TushBabyBedBathBeyondEDI();
                        EdiSpsConfigdata config = TushBabyBedBathBeyondEDI.getEdiConfigData(po)
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
        void test2shipOrderFromPoBedBath() {
                System.setProperty("com.owd.environment","test");

                String poData = "<Order>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>832FWDTUSHBABYI</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>FF6Z8BY</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2020-07-01</PurchaseOrderDate>\n" +
                        "            <Vendor>070181</Vendor>\n" +
                        "        </OrderHeader>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2020-07-03</Date1>\n" +
                        "        </Date>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                        "            <AddressLocationNumber>0657</AddressLocationNumber>\n" +
                        "            <AddressName>Bed Bath &amp; Beyond #0657</AddressName>\n" +
                        "            <Address1>860 John B Brooks Road</Address1>\n" +
                        "            <City>Pendergrass</City>\n" +
                        "            <State>GA</State>\n" +
                        "            <PostalCode>30567</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <VendorPartNumber>TB-Grey-V2</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>850006525003</ConsumerPackageCode>\n" +
                        "                <OrderQty>2</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>39.99</PurchasePrice>\n" +
                        "            </OrderLine>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <VendorPartNumber>TB-Black-V2</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>850006525010</ConsumerPackageCode>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>39.99</PurchasePrice>\n" +
                        "            </OrderLine>\n" +
                        "        </LineItem>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <VendorPartNumber>TB-BlackGold-V2</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>850006525027</ConsumerPackageCode>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePrice>39.99</PurchasePrice>\n" +
                        "            </OrderLine>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
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
                        int docId = TushBabyBedBathBeyondEDI.saveIncomingEdiDocForTesting(poData,"POtestfileamazon.xml",account)
                        TushBabyBedBathBeyondEDI edi = new TushBabyBedBathBeyondEDI();
                        EdiSpsConfigdata config = TushBabyBedBathBeyondEDI.getEdiConfigData(po)
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
}

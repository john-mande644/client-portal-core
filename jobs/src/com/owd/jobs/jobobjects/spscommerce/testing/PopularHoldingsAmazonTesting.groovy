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
import com.owd.jobs.jobobjects.spscommerce.clients.PopularHoldingsAmazonEDI
import org.junit.Test

class PopularHoldingsAmazonTesting extends GroovyTestCase {
    @Test
    void test1shipOrderFromPoTarget() {
        System.setProperty("com.owd.environment", "test");

        String poData =
                "<Order>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>080ALLPOPULARBO</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>4BH1U15H</PurchaseOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2022-11-28</PurchaseOrderDate>\n" +
                        "            <ShipCompleteCode>Y</ShipCompleteCode>\n" +
                        "            <Vendor>V86VF</Vendor>\n" +
                        "            <Division>V86VF</Division>\n" +
                        "            <CustomerOrderNumber>4BH1U15H</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>064</DateTimeQualifier1>\n" +
                        "            <Date1>2022-11-28</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>063</DateTimeQualifier1>\n" +
                        "            <Date1>2022-12-02</Date1>\n" +
                        "        </Date>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <LocationCodeQualifier>15</LocationCodeQualifier>\n" +
                        "            <AddressLocationNumber>9914900</AddressLocationNumber>\n" +
                        "            <AddressName>Amazon.com Services LLC</AddressName>\n" +
                        "            <Address1>601 Randolph Road</Address1>\n" +
                        "            <City>Somerset</City>\n" +
                        "            <State>NJ</State>\n" +
                        "            <PostalCode>08873</PostalCode>\n" +
                        "            <Country>US</Country>\n" +
                        "        </Address>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>IT</ReferenceQual>\n" +
                        "            <ReferenceID>V86VF</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <ConsumerPackageCode>9781942830542</ConsumerPackageCode>\n" +
                        "                <EAN>9781942830542</EAN>\n" +
                        "                <OrderQty>3</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "                <PurchasePriceType>UCP</PurchasePriceType>\n" +
                        "                <PurchasePrice>8.98</PurchasePrice>\n" +
                        "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                        "            </OrderLine>\n" +
                        "            <ProductOrItemDescription>\n" +
                        "                <ProductDescription>9781942830542</ProductDescription>\n" +
                        "            </ProductOrItemDescription>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "    <Summary>\n" +
                        "        <TotalLineItemNumber>1</TotalLineItemNumber>\n" +
                        "        <TotalQuantity>3</TotalQuantity>\n" +
                        "    </Summary>\n" +
                        "</Order>\n";

        try {
            PopularHoldingsAmazonEDI edi = new PopularHoldingsAmazonEDI();

            XmlParser parser = new XmlParser()
            parser.setTrimWhitespace(true)
            Node po = parser.parseText(poData)
            String account = po.Header.OrderHeader.TradingPartnerId.text()

            EdiSpsConfigdata config = edi.getEdiConfigData(po)
            Order order = edi.importPo(po, config.clientFkey)
            println("Loaded the order xxxxxx");

            String method = po.LineItems.LineItem[0].CarrierInformation.CarrierRouting.text();
            if(null == method || method.length()==0){
                po.Header.CarrierInformation.CarrierRouting.text();
            }

            order.setShippingMethodName(edi.loadShippingMethod(order, method, po.Header.CarrierInformation))
            order.addTag(TagUtilities.kEDIPurchaseOrder, poData);
            order.backorder_rule = edi.loadBackorderRule()
            order.is_paid = 1
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
            println reference


            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(order.orderID), Integer.parseInt(order.getClientID()), true)
            String ack = edi.generateACK(config, po, Integer.parseInt(order.clientID), oorder)
            System.out.println(ack);

            PackingManager.packAndShip(Integer.parseInt(order.orderID), 4, 0.00, "1234567891211", true);


            //we have a shipped order, now create the ASN file for it
            String asnData = edi.generateASN(Integer.parseInt(order.orderID), config.clientFkey)
            println asnData

            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir", "tFdfCT7ms2", "ftp.spscommerce.net");

            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            //  ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)
            // ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PR, ack.bytes, SPSCommerceRemoteFTP.FolderPath.sendDirTestPath)

        } catch (Exception ex) {

        }
    }
}

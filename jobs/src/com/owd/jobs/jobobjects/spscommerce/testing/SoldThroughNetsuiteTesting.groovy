package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.SoldThroughNetsuiteEDI
import org.junit.Test

class SoldThroughNetsuiteTesting extends GroovyTestCase {
    @Test
    void test1shipOrderFromPoTarget() {
        System.setProperty("com.owd.environment", "test");

        String poData =
                "<Order>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>FYASOLDTHROUGH0</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>8523698</PurchaseOrderNumber>\n" +
                        "            <DepositorOrderNumber>SO20</DepositorOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2022-09-12</PurchaseOrderDate>\n" +
                        "            <Vendor>14975</Vendor>\n" +
                        "            <CustomerOrderNumber>405703891</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2022-10-10</Date1>\n" +
                        "        </Date>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>038</DateTimeQualifier1>\n" +
                        "            <Date1>2022-09-15</Date1>\n" +
                        "        </Date>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>Solange Castellar</AddressName>\n" +
                        "            <Address1>275 Madison Avenue, 3rd Floor</Address1>\n" +
                        "            <City>New York</City>\n" +
                        "            <State>NY</State>\n" +
                        "            <PostalCode>10016</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierRouting>Domestic</CarrierRouting>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>SO</ReferenceQual>\n" +
                        "            <ReferenceID>SO20</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>WH</ReferenceQual>\n" +
                        "            <ReferenceID>OWD - CA</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>11</ReferenceQual>\n" +
                        "            <ReferenceID>A6LALLSOLDTHROU</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Notes>\n" +
                        "            <NoteCode>SHP</NoteCode>\n" +
                        "            <NoteInformationField>TEST ORDER FOR SMOKE TEST - DO NOT SHIP!</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "        <Notes>\n" +
                        "            <NoteCode>SPE</NoteCode>\n" +
                        "            <NoteInformationField>Is a signature required for delivery?N</NoteInformationField>\n" +
                        "        </Notes>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <ApplicationId>2910</ApplicationId>\n" +
                        "                <BuyerPartNumber>5059892577355</BuyerPartNumber>\n" +
                        "                <VendorPartNumber>Airlie Floral Print Sweat : Airlie Floral Print Sweat Purple2</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>5059892577355</ConsumerPackageCode>\n" +
                        "                <EAN>5059892577355</EAN>\n" +
                        "                <ProductID>\n" +
                        "                    <PartNumberQual>MG</PartNumberQual>\n" +
                        "                    <PartNumber>2910</PartNumber>\n" +
                        "                </ProductID>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                        "            </OrderLine>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>LI</ReferenceQual>\n" +
                        "                <ReferenceID>0001</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>BP</ReferenceQual>\n" +
                        "                <ReferenceID>5059892577355</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>UP</ReferenceQual>\n" +
                        "                <ReferenceID>5059892577355</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>QT</ReferenceQual>\n" +
                        "                <ReferenceID>0.0</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>UN</ReferenceQual>\n" +
                        "                <ReferenceID>EA</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "</Order>\n";

        try {
            SoldThroughNetsuiteEDI edi = new SoldThroughNetsuiteEDI();

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

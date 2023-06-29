package com.owd.jobs.jobobjects.spscommerce.testing

import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.business.order.OrderUtilities
import com.owd.core.managers.PackingManager
import com.owd.core.xml.OrderXMLDoc
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP
import com.owd.jobs.jobobjects.spscommerce.clients.SoldThroughBelkEDI

import org.junit.Test

class SoldThroughBelkTesting extends GroovyTestCase {
    @Test
    void test1shipOrderFromPo() {
        System.setProperty("com.owd.environment", "test");

        String poData =
                "<Order>\n" +
                        "    <Meta>\n" +
                        "        <IsDropShip>true</IsDropShip>\n" +
                        "    </Meta>\n" +
                        "    <Header>\n" +
                        "        <OrderHeader>\n" +
                        "            <TradingPartnerId>DNTALLSOLDTHROU</TradingPartnerId>\n" +
                        "            <PurchaseOrderNumber>0006078909_TEST</PurchaseOrderNumber>\n" +
                        "            <DepositorOrderNumber>SO40</DepositorOrderNumber>\n" +
                        "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                        "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                        "            <PurchaseOrderDate>2022-10-26</PurchaseOrderDate>\n" +
                        "            <DepartmentDescription>51.0</DepartmentDescription>\n" +
                        "            <Vendor>062456448</Vendor>\n" +
                        "            <CustomerOrderNumber>1032324028</CustomerOrderNumber>\n" +
                        "        </OrderHeader>\n" +
                        "        <Date>\n" +
                        "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                        "            <Date1>2022-10-27</Date1>\n" +
                        "        </Date>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                        "            <AddressName>Maria Goldschmidt</AddressName>\n" +
                        "            <Address1>2600 Arlington Ave S</Address1>\n" +
                        "            <Address2>Apt 44</Address2>\n" +
                        "            <City>Birmingham</City>\n" +
                        "            <State>AL</State>\n" +
                        "            <PostalCode>35205-4158</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "            <Contact>\n" +
                        "                <ContactTypeCode>IC</ContactTypeCode>\n" +
                        "                <PrimaryPhone>(205) 602-3638</PrimaryPhone>\n" +
                        "            </Contact>\n" +
                        "        </Address>\n" +
                        "        <Address>\n" +
                        "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                        "            <AddressName>Maria Goldschmidt</AddressName>\n" +
                        "            <Address1>2600 Arlington Ave S</Address1>\n" +
                        "            <Address2>Apt 44</Address2>\n" +
                        "            <City>Birmingham</City>\n" +
                        "            <State>AL</State>\n" +
                        "            <PostalCode>35205-4158</PostalCode>\n" +
                        "            <Country>USA</Country>\n" +
                        "        </Address>\n" +
                        "        <CarrierInformation>\n" +
                        "            <CarrierRouting>UNSP_CG</CarrierRouting>\n" +
                        "        </CarrierInformation>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>SO</ReferenceQual>\n" +
                        "            <ReferenceID>SO40</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>WH</ReferenceQual>\n" +
                        "            <ReferenceID>OWD - CA</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "        <Reference>\n" +
                        "            <ReferenceQual>11</ReferenceQual>\n" +
                        "            <ReferenceID>DNTALLSOLDTHROU</ReferenceID>\n" +
                        "        </Reference>\n" +
                        "    </Header>\n" +
                        "    <LineItems>\n" +
                        "        <LineItem>\n" +
                        "            <OrderLine>\n" +
                        "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                        "                <ApplicationId>3636</ApplicationId>\n" +
                        "                <VendorPartNumber>5059892616030</VendorPartNumber>\n" +
                        "                <ConsumerPackageCode>5059892616030</ConsumerPackageCode>\n" +
                        "                <ProductID>\n" +
                        "                    <PartNumberQual>MG</PartNumberQual>\n" +
                        "                    <PartNumber>3636</PartNumber>\n" +
                        "                </ProductID>\n" +
                        "                <OrderQty>1</OrderQty>\n" +
                        "                <PurchasePrice>51</PurchasePrice>\n" +
                        "                <ProductSizeDescription>20</ProductSizeDescription>\n" +
                        "                <ProductColorDescription>Black</ProductColorDescription>\n" +
                        "            </OrderLine>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>LI</ReferenceQual>\n" +
                        "                <ReferenceID>01</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>VP</ReferenceQual>\n" +
                        "                <ReferenceID>5059892616030</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>UP</ReferenceQual>\n" +
                        "                <ReferenceID>5059892616030</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "            <Reference>\n" +
                        "                <ReferenceQual>QT</ReferenceQual>\n" +
                        "                <ReferenceID>1.0</ReferenceID>\n" +
                        "            </Reference>\n" +
                        "        </LineItem>\n" +
                        "    </LineItems>\n" +
                        "</Order>\n";

        try {
            SoldThroughBelkEDI edi = new SoldThroughBelkEDI();

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

package com.owd.jobs.jobobjects.tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderFactory;
import com.owd.core.managers.PackingManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP;
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities;
import junit.framework.Assert;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 5, 2010
 * Time: 3:09:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SPSCommerceTest
{
private final static Logger log =  LogManager.getLogger();

    static SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("ballparkcl","k4Dr27C6x?","ftp.spscommerce.net");
    public void setUp() throws Exception
    {


        PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_order set order_refnum=order_refnum+'v' where (order_refnum='14046413' or is_void=1) and order_refnum not like '%v' and client_fkey=112");
        ps.execute();
        HibUtils.commit(HibernateSession.currentSession());

        clearDataDirectory("/testout/owdtest");
        clearDataDirectory("/testin/owdtest");

    }

    void clearDataDirectory(String path)
            throws Exception
    {
        List<String> filenames = ftp.listFiles(path);
        for (String filename : filenames)
        {
            ftp.deleteDataFile(filename, path);

        }
        filenames = ftp.listFiles(path);
        Assert.assertTrue(filenames.size() == 0);
    }

    public void tearDown() throws Exception
    {
    }

    public void testInventoryReport()
               throws Exception
       {

           generateTestInventoryReport(null);
       }

    public void testInventoryReportForSPSReview()
               throws Exception
       {

           generateTestInventoryReport("/testin");
       }
    public String generateTestInventoryReport(String deliveryPath)
               throws Exception
       {
           String report = null;
           List filenames = null;

           report = SpsCommerceUtilities.generateStockLevels(456);
           if (deliveryPath == null)
           {
               deliveryPath = "/testin/owdtest";
           }

               String repFilename = ftp.putDataFile(SPSCommerceRemoteFTP.fileType.IB, report.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirPath).getFileName();
               log.debug(new String(ftp.getDataFile(repFilename, deliveryPath)));
               filenames = ftp.listFiles(deliveryPath);
               Assert.assertTrue(filenames.contains(repFilename));


           return report;
       }

    public void testImportPOAndGenerateASNAndInvoiceForSPSReview() throws Exception
    {

        String testData = "<PurchaseOrder>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000000001</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>0001</GroupControlNumber>\n" +
                "        <DocumentControlNumber>00010001</DocumentControlNumber>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>TEST</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>14046413</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2010-04-01</PurchaseOrderDate>\n" +
                "            <Vendor>17903</Vendor>\n" +
                "            <CarrierRouting>UPSGround</CarrierRouting>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>Ball Park Classics</AddressName>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>John Smith</AddressName>\n" +
                "            <Address1>2459 215th Avenue SE</Address1>\n" +
                "            <City>Sammamish</City>\n" +
                "            <State>WA</State>\n" +
                "            <PostalCode>98303</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <ContactPhone>(555) 55551212</ContactPhone>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>John Smith</AddressName>\n" +
                "            <Address1>2459 215th Avenue SE</Address1>\n" +
                "            <City>Sammamish</City>\n" +
                "            <State>WA</State>\n" +
                "            <PostalCode>98303</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <ContactPhone>(555) 55551212</ContactPhone>\n" +
                "        </Address>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>IT</ReferenceQual>\n" +
                "            <ReferenceID>8602069156</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>SD</ReferenceQual>\n" +
                "            <ReferenceID>185</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>ST</ReferenceQual>\n" +
                "            <ReferenceID>125</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Miscellaneous>\n" +
                "            <Description1>INTERCHANGE</Description1>\n" +
                "            <Description2>000000001</Description2>\n" +
                "        </Miscellaneous>\n" +
                "        <Miscellaneous>\n" +
                "            <Description1>FUNCTIONALGROUP</Description1>\n" +
                "            <Description2>0001</Description2>\n" +
                "        </Miscellaneous>\n" +
                "        <Miscellaneous>\n" +
                "            <Description1>TRANSACTION</Description1>\n" +
                "            <Description2>00010001</Description2>\n" +
                "        </Miscellaneous>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>10</LineSequenceNumber>\n" +
                "                <VendorPartNumber>BPC-GU-MLBUNIV</VendorPartNumber>\n" +
                "                <BuyerPartNumber>10000010</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>0812030010047</ConsumerPackageCode>\n" +
                "                <PartDescription1>Ballpark Classics MLB Baseball Game</PartDescription1>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <UnitPrice>229</UnitPrice>\n" +
                "                <UnitPriceBasis>TE</UnitPriceBasis>\n" +
                "                <RetailUnitPrice>229.00</RetailUnitPrice>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>40</LineSequenceNumber>\n" +
                "                <VendorPartNumber>BPC-GU-YANKEES</VendorPartNumber>\n" +
                "                <BuyerPartNumber>10000011</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>0812030010061</ConsumerPackageCode>\n" +
                "                <PartDescription1>NY Yankees Yankee Stadium MLB Baseball Game</PartDescription1>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <UnitPrice>229</UnitPrice>\n" +
                "                <UnitPriceBasis>TE</UnitPriceBasis>\n" +
                "                <RetailUnitPrice>229.00</RetailUnitPrice>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>50</LineSequenceNumber>\n" +
                "                <VendorPartNumber>BPC-GU-REDSOX</VendorPartNumber>\n" +
                "                <BuyerPartNumber>10000012</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>0812030010054</ConsumerPackageCode>\n" +
                "                <PartDescription1>Boston Red Sox Fenway Park MLB Baseball Game</PartDescription1>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <UnitPrice>229</UnitPrice>\n" +
                "                <UnitPriceBasis>TE</UnitPriceBasis>\n" +
                "                <RetailUnitPrice>229.00</RetailUnitPrice>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</PurchaseOrder>";

        String asnData = generateTestASNFromTestPO(testData.getBytes(), "/testin");
    }

    public void testInternalImportPOAndGenerateASNAndInvoice() throws Exception
    {
        String testData = "<PurchaseOrder>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000000001</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>0001</GroupControlNumber>\n" +
                "        <DocumentControlNumber>00010001</DocumentControlNumber>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>TEST</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>14046413</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2010-04-01</PurchaseOrderDate>\n" +
                "            <Vendor>17903</Vendor>\n" +
                "            <CarrierRouting>UPSGround</CarrierRouting>\n" +
                "        </OrderHeader>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>VN</AddressTypeCode>\n" +
                "            <AddressName>Ball Park Classics</AddressName>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <AddressName>John Smith</AddressName>\n" +
                "            <Address1>2459 215th Avenue SE</Address1>\n" +
                "            <City>Sammamish</City>\n" +
                "            <State>WA</State>\n" +
                "            <PostalCode>98303</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <ContactPhone>(555) 55551212</ContactPhone>\n" +
                "        </Address>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>BT</AddressTypeCode>\n" +
                "            <AddressName>John Smith</AddressName>\n" +
                "            <Address1>2459 215th Avenue SE</Address1>\n" +
                "            <City>Sammamish</City>\n" +
                "            <State>WA</State>\n" +
                "            <PostalCode>98303</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "            <ContactPhone>(555) 55551212</ContactPhone>\n" +
                "        </Address>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>IT</ReferenceQual>\n" +
                "            <ReferenceID>8602069156</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Reference>\n" +
                "            <ReferenceQual>SD</ReferenceQual>\n" +
                "            <ReferenceID>185</ReferenceID>\n" +
                "        </Reference>\n" +
                "        <Miscellaneous>\n" +
                "            <Description1>INTERCHANGE</Description1>\n" +
                "            <Description2>000000001</Description2>\n" +
                "        </Miscellaneous>\n" +
                "        <Miscellaneous>\n" +
                "            <Description1>FUNCTIONALGROUP</Description1>\n" +
                "            <Description2>0001</Description2>\n" +
                "        </Miscellaneous>\n" +
                "        <Miscellaneous>\n" +
                "            <Description1>TRANSACTION</Description1>\n" +
                "            <Description2>00010001</Description2>\n" +
                "        </Miscellaneous>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>10</LineSequenceNumber>\n" +
                "                <VendorPartNumber>BPC-GU-MLBUNIV</VendorPartNumber>\n" +
                "                <BuyerPartNumber>10000010</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>0812030010047</ConsumerPackageCode>\n" +
                "                <PartDescription1>Ballpark Classics MLB Baseball Game</PartDescription1>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <UnitPrice>229</UnitPrice>\n" +
                "                <UnitPriceBasis>TE</UnitPriceBasis>\n" +
                "                <RetailUnitPrice>229.00</RetailUnitPrice>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>40</LineSequenceNumber>\n" +
                "                <VendorPartNumber>BPC-GU-YANKEES</VendorPartNumber>\n" +
                "                <BuyerPartNumber>10000011</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>0812030010061</ConsumerPackageCode>\n" +
                "                <PartDescription1>NY Yankees Yankee Stadium MLB Baseball Game</PartDescription1>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <UnitPrice>229</UnitPrice>\n" +
                "                <UnitPriceBasis>TE</UnitPriceBasis>\n" +
                "                <RetailUnitPrice>229.00</RetailUnitPrice>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>50</LineSequenceNumber>\n" +
                "                <VendorPartNumber>BPC-GU-REDSOX</VendorPartNumber>\n" +
                "                <BuyerPartNumber>10000012</BuyerPartNumber>\n" +
                "                <ConsumerPackageCode>0812030010054</ConsumerPackageCode>\n" +
                "                <PartDescription1>Boston Red Sox Fenway Park MLB Baseball Game</PartDescription1>\n" +
                "                <OrderQty>1</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <UnitPrice>229</UnitPrice>\n" +
                "                <UnitPriceBasis>TE</UnitPriceBasis>\n" +
                "                <RetailUnitPrice>229.00</RetailUnitPrice>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>3</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</PurchaseOrder>";

        String asnData = generateTestASNFromTestPO(testData.getBytes(), null);

    }

    private String generateTestASNFromTestPO(byte[] testFileData, String deliveryPath)
            throws Exception
    {
        String asn = null;
        String invoice = null;

        String filename = ftp.putDataFile(SPSCommerceRemoteFTP.fileType.PO, testFileData, SPSCommerceRemoteFTP.FolderPath.receiveDirTestPath).getFileName();
        List filenames = ftp.listFiles("/testout/owdtest");
        Assert.assertTrue(filenames.contains(filename));
        List<Order> orders = SpsCommerceUtilities.processRemotePOs(ftp,"/testout/owdtest", 112);
        Assert.assertTrue(orders.size() == 1);

        OwdOrder order = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(orders.get(0).orderID), Integer.parseInt(orders.get(0).clientID), true);
        Assert.assertTrue(order != null);
        Assert.assertTrue("At Warehouse".equals(order.getOrderStatus()));
        log.debug("" + order.getOrderStatus());

        //force shipment of order
        PackingManager.packAndShip(order.getOrderId(), true);
        HibernateSession.currentSession().evict(order);
        order = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(orders.get(0).orderID), Integer.parseInt(orders.get(0).clientID), true);
        Assert.assertTrue("Shipped".equals(order.getOrderStatus()));

        HibernateSession.currentSession().evict(order);

        asn = SpsCommerceUtilities.generateASN(Integer.parseInt(orders.get(0).orderID), Integer.parseInt(orders.get(0).clientID));

        invoice = SpsCommerceUtilities.generateInvoice(Integer.parseInt(orders.get(0).orderID), Integer.parseInt(orders.get(0).clientID));

        if (deliveryPath != null)
        {
            String asnFilename = ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asn.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirPath).getFileName();
            log.debug(new String(ftp.getDataFile(asnFilename, deliveryPath)));
            String invoiceFilename = ftp.putDataFile(SPSCommerceRemoteFTP.fileType.IN, invoice.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirPath).getFileName();
            log.debug(new String(ftp.getDataFile(invoiceFilename, deliveryPath)));
            filenames = ftp.listFiles(deliveryPath);
            Assert.assertTrue(filenames.contains(asnFilename));
            Assert.assertTrue(filenames.contains(invoiceFilename));
        } else
        {
            String asnFilename = ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asn.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirPath).getFileName();
            log.debug(new String(ftp.getDataFile(asnFilename, "/testin/owdtest")));

            String invoiceFilename = ftp.putDataFile(SPSCommerceRemoteFTP.fileType.IN, invoice.getBytes(),SPSCommerceRemoteFTP.FolderPath.sendDirPath).getFileName();
            log.debug(new String(ftp.getDataFile(invoiceFilename, "/testin/owdtest")));
            filenames = ftp.listFiles("/testin/owdtest");
            Assert.assertTrue(filenames.contains(asnFilename));
            Assert.assertTrue(filenames.contains(invoiceFilename));
            ftp.deleteDataFile(asnFilename, "/testin/owdtest");
            ftp.deleteDataFile(invoiceFilename, "/testin/owdtest");
            filenames = ftp.listFiles("/testin/owdtest");
            Assert.assertTrue(filenames.size() == 0);

        }
        filenames = ftp.listFiles("/testout/owdtest");
        Assert.assertTrue(filenames.size() == 0);

        return asn;
    }


}

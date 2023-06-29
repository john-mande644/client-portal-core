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
import com.owd.jobs.jobobjects.spscommerce.clients.RovrBassProEDI
import com.owd.jobs.jobobjects.spscommerce.clients.RovrREIEDI
import org.junit.Test

/**
 * Created by danny on 1/30/2017.
 */
class DogEaredZapposTesting extends GroovyTestCase {

    @Test
    void test1WarehouseFromDogEared() {
        System.setProperty("com.owd.environment", "test");



        String poData = "<Order>\n" +
                "    <Meta>\n" +
                "        <InterchangeControlNumber>000210945</InterchangeControlNumber>\n" +
                "        <GroupControlNumber>210945</GroupControlNumber>\n" +
                "        <DocumentControlNumber>0001</DocumentControlNumber>\n" +
                "        <InterchangeSenderID>ZAPPOSKY</InterchangeSenderID>\n" +
                "        <InterchangeReceiverID>3108464448</InterchangeReceiverID>\n" +
                "        <GroupSenderID>ZAPPOSKY</GroupSenderID>\n" +
                "        <GroupReceiverID>3108464448</GroupReceiverID>\n" +
                "    </Meta>\n" +
                "    <Header>\n" +
                "        <OrderHeader>\n" +
                "            <TradingPartnerId>61FALLDOGEAREDJ</TradingPartnerId>\n" +
                "            <PurchaseOrderNumber>EXNULAO4547325</PurchaseOrderNumber>\n" +
                "            <TsetPurposeCode>00</TsetPurposeCode>\n" +
                "            <PurchaseOrderTypeCode>SA</PurchaseOrderTypeCode>\n" +
                "            <PurchaseOrderDate>2020-12-07</PurchaseOrderDate>\n" +
                "            <Department>AC</Department>\n" +
                "            <Vendor>5032</Vendor>\n" +
                "        </OrderHeader>\n" +
                "        <PaymentTerms>\n" +
                "            <TermsType>14</TermsType>\n" +
                "            <TermsBasisDateCode>3</TermsBasisDateCode>\n" +
                "            <TermsNetDueDays>60</TermsNetDueDays>\n" +
                "            <TermsDescription>NET</TermsDescription>\n" +
                "        </PaymentTerms>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>001</DateTimeQualifier1>\n" +
                "            <Date1>2020-12-11</Date1>\n" +
                "        </Date>\n" +
                "        <Date>\n" +
                "            <DateTimeQualifier1>010</DateTimeQualifier1>\n" +
                "            <Date1>2020-12-05</Date1>\n" +
                "        </Date>\n" +
                "        <Address>\n" +
                "            <AddressTypeCode>ST</AddressTypeCode>\n" +
                "            <LocationCodeQualifier>92</LocationCodeQualifier>\n" +
                "            <AddressLocationNumber>0003</AddressLocationNumber>\n" +
                "            <AddressName>ZAPPOS.COM LLC.</AddressName>\n" +
                "            <AddressAlternateName>C/O AMAZON.COM SERVICES, INC.</AddressAlternateName>\n" +
                "            <Address1>900 PATROL RD</Address1>\n" +
                "            <City>JEFFERSONVILLE</City>\n" +
                "            <State>IN</State>\n" +
                "            <PostalCode>47130</PostalCode>\n" +
                "            <Country>US</Country>\n" +
                "        </Address>\n" +
                "        <ChargesAllowances>\n" +
                "            <AllowChrgIndicator>A</AllowChrgIndicator>\n" +
                "            <AllowChrgCode>D500</AllowChrgCode>\n" +
                "            <AllowChrgAmt>0</AllowChrgAmt>\n" +
                "            <AllowChrgHandlingDescription>HANDLING</AllowChrgHandlingDescription>\n" +
                "        </ChargesAllowances>\n" +
                "    </Header>\n" +
                "    <LineItems>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>1</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288439</BuyerPartNumber>\n" +
                "                <VendorPartNumber>MRGG100105804</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065129336</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>GOLD</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>26.04</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>2</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288435</BuyerPartNumber>\n" +
                "                <VendorPartNumber>1G2513</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065118170</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>GOLD PLATED</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>33.48</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>3</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288436</BuyerPartNumber>\n" +
                "                <VendorPartNumber>1S2513</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065118163</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>STERLING SILVER</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>30.69</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>4</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288440</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VS11443-040</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065122894</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>STERLING SILVER</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>41.85</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>5</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288450</BuyerPartNumber>\n" +
                "                <VendorPartNumber>MG1377-962</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065129428</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>GOLD</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>26.04</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>6</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288444</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VG1987</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065101721</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>GOLD DIPPED</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>31.16</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>7</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288437</BuyerPartNumber>\n" +
                "                <VendorPartNumber>1S2385-01</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065128865</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>STERLING SILVER</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>18.6</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>8</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288443</BuyerPartNumber>\n" +
                "                <VendorPartNumber>MRSS100513304</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065129367</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>STERLING SILVER</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>21.39</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>9</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288449</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VS11252</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065114806</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>STERLING SILVER</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>2</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>26.04</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>10</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288446</BuyerPartNumber>\n" +
                "                <VendorPartNumber>P10-G100-081004</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>845918010334</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>GOLD</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>6</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>19.53</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>11</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288438</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VSB1223</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065114141</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>STERLING SILVER</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>19.53</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>12</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288448</BuyerPartNumber>\n" +
                "                <VendorPartNumber>MRGG100803304</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065129305</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>GOLD</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>5</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>26.04</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>13</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288447</BuyerPartNumber>\n" +
                "                <VendorPartNumber>MRSS100803304</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065129299</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>SILVER</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>10</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>21.39</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>14</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288441</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VS11209</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065113632</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>STERLING SILVER</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>32.55</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>15</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288442</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VG11207</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065113601</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>GOLD</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>12</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>30.69</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "        <LineItem>\n" +
                "            <OrderLine>\n" +
                "                <LineSequenceNumber>16</LineSequenceNumber>\n" +
                "                <BuyerPartNumber>EXNULAO135288445</BuyerPartNumber>\n" +
                "                <VendorPartNumber>VG11212</VendorPartNumber>\n" +
                "                <ConsumerPackageCode>843065113700</ConsumerPackageCode>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>VE</PartNumberQual>\n" +
                "                    <PartNumber>GOLD</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <ProductID>\n" +
                "                    <PartNumberQual>IZ</PartNumberQual>\n" +
                "                    <PartNumber>ONE SIZE</PartNumber>\n" +
                "                </ProductID>\n" +
                "                <OrderQty>4</OrderQty>\n" +
                "                <OrderQtyUOM>EA</OrderQtyUOM>\n" +
                "                <PurchasePrice>44.64</PurchasePrice>\n" +
                "                <PurchasePriceBasis>PE</PurchasePriceBasis>\n" +
                "                <ProductSizeDescription>ONE SIZE</ProductSizeDescription>\n" +
                "            </OrderLine>\n" +
                "        </LineItem>\n" +
                "    </LineItems>\n" +
                "    <Summary>\n" +
                "        <TotalLineItemNumber>16</TotalLineItemNumber>\n" +
                "    </Summary>\n" +
                "</Order>";

        try {
            DogEaredZapposEDI edi = new DogEaredZapposEDI();

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

                    order.addTag(TagUtilities.kEDIZapposDN, "X12345");

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

package com.owd.alittlePlaying.XPath;

import com.owd.core.UpcBarcodeUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.OrderStatus;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryAdditionalIds;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.Date;
import java.util.Map;

public class xpathiteration {
    public static void main(String[] argv){
        commerceTest();
    }


    public static void commerceTest(){
        String s ="<Order>\n" +
                "  <Meta>\n" +
                "    <InterchangeControlNumber>\n" +
                "      000000032\n" +
                "    </InterchangeControlNumber>\n" +
                "    <GroupControlNumber>\n" +
                "      5\n" +
                "    </GroupControlNumber>\n" +
                "    <DocumentControlNumber>\n" +
                "      0002\n" +
                "    </DocumentControlNumber>\n" +
                "    <InterchangeSenderID>\n" +
                "      4159772900\n" +
                "    </InterchangeSenderID>\n" +
                "    <InterchangeReceiverID>\n" +
                "      60282106YK\n" +
                "    </InterchangeReceiverID>\n" +
                "    <GroupSenderID>\n" +
                "      4159772900\n" +
                "    </GroupSenderID>\n" +
                "    <GroupReceiverID>\n" +
                "      60282106YK\n" +
                "    </GroupReceiverID>\n" +
                "  </Meta>\n" +
                "  <Header>\n" +
                "    <OrderHeader>\n" +
                "      <TradingPartnerId>\n" +
                "        77WALLLAWLESSBE\n" +
                "      </TradingPartnerId>\n" +
                "      <PurchaseOrderNumber>\n" +
                "        0300327354\n" +
                "      </PurchaseOrderNumber>\n" +
                "      <TsetPurposeCode>\n" +
                "        00\n" +
                "      </TsetPurposeCode>\n" +
                "      <PurchaseOrderTypeCode>\n" +
                "        SA\n" +
                "      </PurchaseOrderTypeCode>\n" +
                "      <PurchaseOrderDate>\n" +
                "        2020-03-31\n" +
                "      </PurchaseOrderDate>\n" +
                "      <BuyersCurrency>\n" +
                "        USD\n" +
                "      </BuyersCurrency>\n" +
                "      <Department>\n" +
                "        10\n" +
                "      </Department>\n" +
                "      <Vendor>\n" +
                "        000534099\n" +
                "      </Vendor>\n" +
                "    </OrderHeader>\n" +
                "    <PaymentTerms>\n" +
                "      <TermsType>\n" +
                "        14\n" +
                "      </TermsType>\n" +
                "      <TermsBasisDateCode>\n" +
                "        3\n" +
                "      </TermsBasisDateCode>\n" +
                "      <TermsNetDueDays>\n" +
                "        30\n" +
                "      </TermsNetDueDays>\n" +
                "    </PaymentTerms>\n" +
                "    <Date>\n" +
                "      <DateTimeQualifier1>\n" +
                "        037\n" +
                "      </DateTimeQualifier1>\n" +
                "      <Date1>\n" +
                "        2020-04-06\n" +
                "      </Date1>\n" +
                "    </Date>\n" +
                "    <Date>\n" +
                "      <DateTimeQualifier1>\n" +
                "        038\n" +
                "      </DateTimeQualifier1>\n" +
                "      <Date1>\n" +
                "        2020-04-10\n" +
                "      </Date1>\n" +
                "    </Date>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        BY\n" +
                "      </AddressTypeCode>\n" +
                "      <LocationCodeQualifier>\n" +
                "        92\n" +
                "      </LocationCodeQualifier>\n" +
                "      <AddressLocationNumber>\n" +
                "        0001\n" +
                "      </AddressLocationNumber>\n" +
                "    </Address>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        ST" +
                "      </AddressTypeCode>\n" +
                "      <LocationCodeQualifier>\n" +
                "        92\n" +
                "      </LocationCodeQualifier>\n" +
                "      <AddressLocationNumber>\n" +
                "        0801\n" +
                "      </AddressLocationNumber>\n" +
                "      <AddressName>\n" +
                "        SEPHORA UTAH DC\n" +
                "      </AddressName>\n" +
                "      <AddressAlternateName>\n" +
                "        6075 WEST, 300 SOUTH\n" +
                "      </AddressAlternateName>\n" +
                "      <City>\n" +
                "        SALT LAKE CITY\n" +
                "      </City>\n" +
                "      <State>\n" +
                "        UT\n" +
                "      </State>\n" +
                "      <PostalCode>\n" +
                "        84104\n" +
                "      </PostalCode>\n" +
                "    </Address>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        FW\n" +
                "      </AddressTypeCode>\n" +
                "      <AddressName>\n" +
                "        DFSL SEPHORA USA\n" +
                "      </AddressName>\n" +
                "    </Address>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        SU\n" +
                "      </AddressTypeCode>\n" +
                "      <LocationCodeQualifier>\n" +
                "        91\n" +
                "      </LocationCodeQualifier>\n" +
                "      <AddressLocationNumber>\n" +
                "        2127360041\n" +
                "      </AddressLocationNumber>\n" +
                "    </Address>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        BT\n" +
                "      </AddressTypeCode>\n" +
                "      <LocationCodeQualifier>\n" +
                "        92\n" +
                "      </LocationCodeQualifier>\n" +
                "      <AddressLocationNumber>\n" +
                "        7612294001774\n" +
                "      </AddressLocationNumber>\n" +
                "    </Address>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        Z7\n" +
                "      </AddressTypeCode>\n" +
                "      <AddressLocationNumber>\n" +
                "        0801\n" +
                "      </AddressLocationNumber>\n" +
                "    </Address>\n" +
                "    <FOBRelatedInstruction>\n" +
                "      <FOBPayCode>\n" +
                "        CC\n" +
                "      </FOBPayCode>\n" +
                "      <FOBLocationQualifier>\n" +
                "        01\n" +
                "      </FOBLocationQualifier>\n" +
                "      <FOBLocationDescription>\n" +
                "        FOB\n" +
                "      </FOBLocationDescription>\n" +
                "      <FOBTitlePassageCode>\n" +
                "        ZZ\n" +
                "      </FOBTitlePassageCode>\n" +
                "      <FOBTitlePassageLocation>\n" +
                "        FOB VENDOR WAREHO\n" +
                "      </FOBTitlePassageLocation>\n" +
                "    </FOBRelatedInstruction>\n" +
                "    <CarrierInformation>\n" +
                "      <CarrierTransMethodCode>\n" +
                "        M\n" +
                "      </CarrierTransMethodCode>\n" +
                "      <ServiceLevelCodes>\n" +
                "        <ServiceLevelCode>\n" +
                "          B\n" +
                "        </ServiceLevelCode>\n" +
                "      </ServiceLevelCodes>\n" +
                "    </CarrierInformation>\n" +
                "    <Reference>\n" +
                "      <ReferenceQual>\n" +
                "        CR\n" +
                "      </ReferenceQual>\n" +
                "      <ReferenceID>\n" +
                "        REG-030-1392789\n" +
                "      </ReferenceID>\n" +
                "    </Reference>\n" +
                "  </Header>\n" +
                "  <LineItems>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          1\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002065241\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          GEORGE\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          851642008035\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          20\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          11\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS L/L GEORGE\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          2\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002266245\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          GOLDENHR\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024513307\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          67\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          16.72\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS SMMR SKN BRNZR GH\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          3\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002294460\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          PLATINUM\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024514403\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          12\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          12.32\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS BLT LP STK PLTNM\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          4\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002294494\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          WEDDING DAY\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024514434\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          36\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          12.32\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS BLT LP STK WDG DY\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          5\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002294502\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          FAWN\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024514441\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          12\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          12.32\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS BLT LP STK FAWN\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          6\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002294528\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          90S\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024514465\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          12\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          12.32\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS BLT LP STK 90S\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          7\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002294544\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          EYE SHADOW\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024515011\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          100\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          11\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS THE LITTLE ONE\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          8\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002297752\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          ANNIE\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024514090\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          20\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          11\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS LP SHN GLSS ANNIE\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          9\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002297778\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          MANUKA\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024514113\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          20\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          11\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS LP SHNE GLSS MNKA\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          10\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          002297794\n" +
                "        </BuyerPartNumber>\n" +
                "        <VendorPartNumber>\n" +
                "          BABE\n" +
                "        </VendorPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          810024514137\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          20\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          11\n" +
                "        </PurchasePrice>\n" +
                "        <NRFStandardColorAndSize>\n" +
                "          <NRFColorCode>\n" +
                "            X\n" +
                "          </NRFColorCode>\n" +
                "          <ColorCategoryName>\n" +
                "            08\n" +
                "          </ColorCategoryName>\n" +
                "          <SizePrimaryDescription>\n" +
                "            LAWLESS LP SHNE GLSS BABE\n" +
                "          </SizePrimaryDescription>\n" +
                "        </NRFStandardColorAndSize>\n" +
                "      </OrderLine>\n" +
                "    </LineItem>\n" +
                "  </LineItems>\n" +
                "  <Summary>\n" +
                "    <TotalLineItemNumber>\n" +
                "      10\n" +
                "    </TotalLineItemNumber>\n" +
                "  </Summary>\n" +
                "</Order>";

      /*  OrderStatus order = new OrderStatus("" + 18538081);
        Map<String, String> tagmap = order.getTagMap();
        String xml = (tagmap.get("COMMERCEHUB_PO_XML"));*/
        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(s.getBytes()));
            XPath xPath = XPathFactory.newInstance().newXPath();
          // System.out.println(xPath.compile("/OrderLine/ProductID/PartNumberQual[contains(.,'SZ')]/parent::ProductID/PartNumber").evaluate(document));
            System.out.println(xPath.compile("/Order/Header/Address/AddressTypeCode[contains(.,'ST')]/parent::Address/AddressLocationNumber").evaluate(document));

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void barcodeiteration(){
        Client client = new Client();
        String xml = "<INVENTORY_BARCODES><BARCODE><TYPE>UPC</TYPE><VALUE>1234567890</VALUE></BARCODE><BARCODE><TYPE>DSKU</TYPE><VALUE>D12345680293</VALUE></BARCODE></INVENTORY_BARCODES>";
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        OwdInventory item = new OwdInventory();
        InputSource source = new InputSource(new StringReader(xml));
        try {
            Document doc = (Document) xpath.evaluate("/", source, XPathConstants.NODE);
            System.out.println("made doc: " + doc.getNodeName());
            System.out.println("made doc2: " + doc.getNodeValue());
            System.out.println("made node: " + xpath.evaluate("/INVENTORY_BARCODES/BARCODE/TYPE",doc));
            System.out.println("made node: " + xpath.evaluate("/INVENTORY_BARCODES/BARCODE/VALUE",doc));
            Node barcodeList = (Node) xpath.evaluate("/INVENTORY_BARCODES",doc,XPathConstants.NODE);
            Node bc;
            NodeIterator bc_iter = XPathAPI.selectNodeIterator(barcodeList,"./BARCODE");
            while((bc=bc_iter.nextNode())!=null){
                String bc_type = "", bc_value ="";
                System.out.println("Has next");
                NodeList children = bc.getChildNodes();
                for(int x = 0; x < children.getLength(); x++){
                    Node child = children.item(x);
                    if("TYPE".equals(child.getNodeName())){
                        bc_type = child.getTextContent();
                    }
                    if("VALUE".equals(child.getNodeName())){
                        bc_value = child.getTextContent();
                    }
                }
                if(!bc_type.equals("UPC") && !bc_type.equals("ISBN")){
                    OwdInventoryAdditionalIds id = new OwdInventoryAdditionalIds();
                    id.setClientFkey(Integer.parseInt(client.client_id));
                    id.setInventoryFkey(item.getInventoryId());
                    id.setValue(bc_value);
                }else if(bc_type.equals("UPC")){
                    if(UpcBarcodeUtilities.barcodeCheck(bc_value).equals("UPC")){
                        item.setUpcCode(bc_value);
                    }
                }else if(bc_type.equals("ISBN")){
                    if(UpcBarcodeUtilities.barcodeCheck((bc_value)).equals("ISBN")){
                        item.setIsbnCode(bc_value);
                    }
                }
            }
        }catch  (Exception ex){
            ex.printStackTrace();
        }
    }
}

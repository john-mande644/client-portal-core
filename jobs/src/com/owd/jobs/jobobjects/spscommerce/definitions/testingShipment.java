package com.owd.jobs.jobobjects.spscommerce.definitions;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by danny on 2/7/2017.
 */
public class testingShipment {


    private static String s = "<Shipments xmlns='http://www.spscommerce.com/RSX'>\n" +
            "  <Shipment>\n" +
            "    <Header>\n" +
            "      <ShipmentHeader>\n" +
            "        <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
            "        <ShipmentIdentification>18890256</ShipmentIdentification>\n" +
            "        <ShipDate>2017-02-03</ShipDate>\n" +
            "        <TsetPurposeCode>00</TsetPurposeCode>\n" +
            "        <ShipNoticeDate>2017-02-03</ShipNoticeDate>\n" +
            "        <ShipNoticeTime>14:55:19</ShipNoticeTime>\n" +
            "        <ASNStructureCode>0001</ASNStructureCode>\n" +
            "        <BillOfLadingNumber>1234567891211</BillOfLadingNumber>\n" +
            "        <CarrierProNumber></CarrierProNumber>\n" +
            "        <CurrentScheduledDeliveryDate>2017-02-06</CurrentScheduledDeliveryDate>\n" +
            "      </ShipmentHeader>\n" +
            "      <Reference>\n" +
            "        <ReferenceQual>06</ReferenceQual>\n" +
            "        <ReferenceID>119445809</ReferenceID>\n" +
            "      </Reference>\n" +
            "      <Reference>\n" +
            "        <ReferenceQual>AN</ReferenceQual>\n" +
            "        <ReferenceID>100000015547</ReferenceID>\n" +
            "      </Reference>\n" +
            "      <Address>\n" +
            "        <AddressTypeCode>ST</AddressTypeCode>\n" +
            "        <AddressName>Jim Smith</AddressName>\n" +
            "        <AddressAlternateName>.</AddressAlternateName>\n" +
            "        <Address1>8 Waterside Drive</Address1>\n" +
            "        <Address2>Apartment 8</Address2>\n" +
            "        <City>Hilton Head</City>\n" +
            "        <State>SC</State>\n" +
            "        <PostalCode>29928</PostalCode>\n" +
            "        <Country></Country>\n" +
            "        " +
            "        " +
            "      </Address>\n" +
            "      <Address>\n" +
            "        <AddressTypeCode>SF</AddressTypeCode>\n" +
            "        <AddressName>Fulfillment Operations</AddressName>\n" +
            "        <AddressAlternateName>One World Direct</AddressAlternateName>\n" +
            "        <Address1>1915 10th Ave W</Address1>\n" +
            "        <Address2></Address2>\n" +
            "        <City>Mobridge</City>\n" +
            "        <State>SD</State>\n" +
            "        <PostalCode>57601</PostalCode>\n" +
            "        <Country>US</Country>\n" +

            "      </Address>\n" +
            "      <CarrierInformation>\n" +
            "        <StatusCode>CL</StatusCode>\n" +
            "        <CarrierTransMethodCode>E</CarrierTransMethodCode>\n" +
            "        <CarrierAlphaCode>UPSN</CarrierAlphaCode>\n" +
            "        \n" +
            "        <CarrierRouting>UPS Ground</CarrierRouting>" +
            "<RoutingSequenceCode>B</RoutingSequenceCode>" +
            "   \n" +
            "      </CarrierInformation>\n" +
            "      <QuantityAndWeight>\n" +
            "        <PackingMedium>CTN</PackingMedium>\n" +
            "        <LadingQuantity>1</LadingQuantity>\n" +
            "        <WeightQualifier>G</WeightQualifier>\n" +
            "        <Weight>4.0000</Weight>\n" +
            "        <WeightUOM>LB</WeightUOM>\n" +
            "      </QuantityAndWeight>\n" +
            "      <FOBRelatedInstruction>\n" +
            "        <FOBPayCode>CC</FOBPayCode>\n" +
            "      </FOBRelatedInstruction>\n" +
            "    </Header>\n" +
            "    <OrderLevel>\n" +
            "      <OrderHeader>\n" +
            "        <InternalOrderNumber>118890256</InternalOrderNumber>\n" +
            "        <InvoiceNumber>18890256</InvoiceNumber>\n" +
            "        <PurchaseOrderNumber>00121371517</PurchaseOrderNumber>\n" +
            "        <ReleaseNumber></ReleaseNumber>\n" +
            "        <PurchaseOrderDate>2017-01-25</PurchaseOrderDate>\n" +
            "        <Vendor>008337</Vendor>\n" +
            "        <CustomerOrderNumber>VDCORDER00009AK</CustomerOrderNumber>\n" +
            "      </OrderHeader>\n" +
            "      <QuantityAndWeight>\n" +
            "        <PackingMedium>CTN</PackingMedium>\n" +
            "        <LadingQuantity>1</LadingQuantity>\n" +
            "        <WeightQualifier>G</WeightQualifier>\n" +
            "        <Weight>4.0000</Weight>\n" +
            "        <WeightUOM>LB</WeightUOM>\n" +
            "      </QuantityAndWeight>\n" +
            "      <Reference>\n" +
            "        <ReferenceQual>06</ReferenceQual>\n" +
            "        <ReferenceID>119445809</ReferenceID>\n" +
            "      </Reference>\n" +
            "      <Reference>\n" +
            "        <ReferenceQual>AN</ReferenceQual>\n" +
            "        <ReferenceID>100000015547</ReferenceID>\n" +
            "      </Reference>\n" +
            "      <Address>\n" +
            "        <AddressTypeCode>BT</AddressTypeCode>\n" +
            "        <AddressName>John Doe</AddressName>\n" +
            "        <AddressAlternateName>John</AddressAlternateName>\n" +
            "        <Address1>8 Stoneridge Drive</Address1>\n" +
            "        <Address2>Suite 8 Luxury Apts</Address2>\n" +
            "        <City>Columbia</City>\n" +
            "        <State>SC</State>\n" +
            "        <PostalCode>29210</PostalCode>\n" +
            "        <Country></Country>\n" +
            "        \n" +
            "      </Address>\n" +
            "      <Address>\n" +
            "        <AddressTypeCode>ST</AddressTypeCode>\n" +
            "        <AddressName>Jim Smith</AddressName>\n" +
            "        <AddressAlternateName>Jim</AddressAlternateName>\n" +
            "        <Address1>8 Waterside Drive</Address1>\n" +
            "        <Address2>Apartment 8</Address2>\n" +
            "        <City>Hilton Head</City>\n" +
            "        <State>SC</State>\n" +
            "        <PostalCode>29928</PostalCode>\n" +
            "        <Country></Country>\n" +
            "        \n" +
            "      </Address>\n" +
            "      <PackLevel>\n" +
            "        <Pack>\n" +
            "          <PackLevelType>P</PackLevelType>\n" +
            "          <CarrierPackageID>1234567891211</CarrierPackageID>\n" +
            "        </Pack>\n" +
            "        <PhysicalDetails><PackSize>1</PackSize>" +
            "<PackUOM>EA</PackUOM>\n" +
            "          <PackingMedium>CTN</PackingMedium>\n" +
            "          <WeightQualifier>G</WeightQualifier>\n" +
            "          <PackWeight>4.0</PackWeight>\n" +
            "          <PackWeightUOM>LB</PackWeightUOM>\n" +
            "          \n" +
            "          \n" +
            "        </PhysicalDetails>\n" +
            "        <ItemLevel>\n" +
            "          <ShipmentLine>\n" +
            "            <LineSequenceNumber>01</LineSequenceNumber>\n" +
            "            <BuyerPartNumber>802575562</BuyerPartNumber>\n" +
            "            <VendorPartNumber>88880211</VendorPartNumber>\n" +
            "            <ConsumerPackageCode>800000000011</ConsumerPackageCode>" +
            "<ItemStatusCode>AC</ItemStatusCode>\n" +
            "            <ShipQty>5.0</ShipQty>\n" +
            "            <ShipQtyUOM>EA</ShipQtyUOM>\n" +
            "            \n" +
            "          </ShipmentLine>\n" +
            "        </ItemLevel>\n" +
            "      </PackLevel>\n" +
            "    </OrderLevel>" +     "  <Summary>\n" +
            "    <TotalLineItems>1</TotalLineItems>\n" +
            "    <TotalQuantity>5</TotalQuantity>\n" +
            "  </Summary>\n" +
            "\n" +
            "  </Shipment>\n" +

            "</Shipments>"     ;

    public static void main(String[] args){

        try{
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new File("src/com/owd/jobs/jobobjects/spscommerce/definitions/Shipments.xsd")));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(trim(s))));

        }   catch (Exception e){
            e.printStackTrace();
        }


    }

    public static String trim(String input) {
        BufferedReader reader = new BufferedReader(new StringReader(input));
        StringBuffer result = new StringBuffer();
        try {
            String line;
            while ( (line = reader.readLine() ) != null)
                result.append(line.trim());
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

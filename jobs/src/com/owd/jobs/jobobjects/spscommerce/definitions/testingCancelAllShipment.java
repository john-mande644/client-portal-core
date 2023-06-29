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
public class testingCancelAllShipment {


    private static String s = "<Shipments xmlns='http://www.spscommerce.com/RSX'>\n" +
            "  <Shipment>\n" +
            "    <Header>\n" +
            "      <ShipmentHeader>\n" +
            "        <TradingPartnerId>0XRTSTGILDANUSA</TradingPartnerId>\n" +
            "        <ShipmentIdentification>00121371505</ShipmentIdentification>\n" +
            "        <TsetPurposeCode>01</TsetPurposeCode>\n" +
            "        <ShipNoticeDate>2017-02-07</ShipNoticeDate>\n" +
            "        <ShipNoticeTime>23:26:17</ShipNoticeTime>\n" +
            "        <ASNStructureCode>0001</ASNStructureCode>\n" +
            "        <CurrentScheduledDeliveryDate>2017-02-10</CurrentScheduledDeliveryDate>\n" +
            "      </ShipmentHeader>\n" +
            "      <Reference>\n" +
            "        <ReferenceQual>06</ReferenceQual>\n" +
            "        <ReferenceID>119445804</ReferenceID>\n" +
            "      </Reference>\n" +
            "      <Reference>\n" +
            "        <ReferenceQual>AN</ReferenceQual>\n" +
            "        <ReferenceID>100000015541</ReferenceID>\n" +
            "      </Reference>\n" +
            "    </Header>\n" +
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

package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;



import groovy.util.Node;
import groovy.util.XmlNodePrinter;
import groovy.util.XmlParser;
import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;

/**
 * Created by danny on 12/14/2018.
 */
public class BassProWarehousePackageLabel extends vendorCompliancePackageLabelBase {

    String department = "";
    String departmentName ="";
    String distributionCenter = "";
    String storeNumber = "";


    public void loadDataFrom850(){

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {

          //  builderFactory.setIgnoringElementContentWhitespace(true);
            builderFactory.setIgnoringElementContentWhitespace(true);

            builder = builderFactory.newDocumentBuilder();


            BufferedReader reader = new BufferedReader(new StringReader(edi850));
            String line;
            StringBuilder sb = new StringBuilder();

            while((line=reader.readLine())!=null){
                sb.append(line.trim());
            }



            Document document = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));


          /*  TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource("src\\com\\owd\\dc\\warehouse\\vendorCompliance\\labelsMaker\\packageLabels\\strip-space.xsl"));
            DOMSource source = new DOMSource(document);
            StringWriter outWriter = new StringWriter();
            StreamResult result = new StreamResult(outWriter);

            transformer.transform(source,result);*/
            XPath xPath =  XPathFactory.newInstance().newXPath();
           /* String departmentExp = "/Order/Header/OrderHeader/Department";
            String departmentNameExp = "/Order/Header/OrderHeader/DepartmentDescription";
            department = xPath.compile(departmentExp).evaluate(document);
            departmentName = xPath.compile(departmentNameExp).evaluate(document);
*/
         //  document = builder.parse(result.getWriter().toString());

            System.out.println(xPath.compile("/Order/Header/Address/AddressLocationNumber").evaluate(document));
            String distrbutionCenterEXP = "/Order/Header/Address[AddressTypeCode='ST']/AddressLocationNumber";
            NodeList nodeList = (NodeList) xPath.compile(distrbutionCenterEXP).evaluate(document, XPathConstants.NODESET);
            System.out.println(nodeList.getLength());
            if(nodeList.getLength()>0){
                distributionCenter = nodeList.item(0).getFirstChild().getNodeValue();
            }

         /*  String storeNumberExp = "/Order/LineItems/LineItem[1]/QuantitiesSchedulesLocations/LocationQuantity/Location";
            storeNumber = xPath.compile(storeNumberExp).evaluate(document);*/



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception{
        StringBuilder sb = new StringBuilder();
        loadDataFrom850();
        try {

/*
            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,209^FT94,1119^BCN,,Y,Y,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCC());
            sb.append("^FS");
            sb.append("^FO12,138^GB797,0,4^FS");
            sb.append("^FO9,601^GB796,0,4^FS");
            sb.append("^FO7,332^GB796,0,4^FS");
            sb.append("^FO8,824^GB797,0,4^FS");
            //Add below line if we pull DC name from Database
            //sb.append("^FT524,729^A0N,28,28^FH\\^FD^FS");
            sb.append("^FT572,688^A0N,23,31^FH\\^FD");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^FT29,585^A0N,23,24^FH\\^FDCarton ");
            sb.append(labelData.getCartonNum());
            sb.append(" of ");
            sb.append(labelData.getCartonOf());
            sb.append("^FS");
            if(labelData.getMultiSku()){
                sb.append("^FT26,545^A0N,23,24^FH\\^FDUPC: Mixed^FS");
            }else{
                sb.append("^FT26,545^A0N,23,24^FH\\^FDUPC: "+ labelData.getUpc()+"^FS");
            }

            sb.append("^FT161,492^A0N,39,38^FH\\^FD");
            sb.append(departmentName);
            sb.append("^FS");
            sb.append("^FT24,486^A0N,23,24^FH\\^FDDept Name:^FS");
            sb.append("^FT95,442^A0N,39,38^FH\\^FD");
            sb.append(department);
            sb.append("^FS");
            sb.append("^FT24,434^A0N,23,24^FH\\^FDDept#^FS");
            sb.append("^FT25,386^A0N,23,24^FH\\^FDPO: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT426,177^A0N,23,24^FH\\^FDCARRIER:");
            sb.append(labelData.getCarrierName());
            sb.append("^FS");
            sb.append("^FT464,117^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCity());
            sb.append(", ");
            sb.append(labelData.getShipToState());
            sb.append(" ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT464,45^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT464,82^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress());
            sb.append("^FS");
            sb.append("^FT76,121^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromCity());
            sb.append(", ");
            sb.append(labelData.getShipFromState());
            sb.append(" ");
            sb.append(labelData.getShipFromZip());
            sb.append("^FS");
            sb.append("^FT78,86^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromAddress());
            sb.append("^FS");
            sb.append("^FT81,49^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromName());
            sb.append("^FS");
            sb.append("^BY4,3,110^FT38,315^BCN,,N,N");
            sb.append("^FD>;420");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT120,182^A0N,23,24^FH\\^FD(420)  ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FO400,7^GB0,325,5^FS");
            sb.append("^BY4,3,106^FT47,787^BCN,,Y,Y");
            sb.append("^FD>;91");
            sb.append(storeNumber);
            sb.append("^FS");
            sb.append("^FO401,606^GB0,213,5^FS");
            sb.append("^PQ1,0,1,Y^XZ");*/

            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,209^FT94,1119^BCN,,Y,Y,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCC());
            sb.append("^FS");
            sb.append("^FO6,187^GB797,0,4^FS");
            sb.append("^FO9,601^GB796,0,4^FS");
            sb.append("^FO3,380^GB796,0,4^FS");
            sb.append("^FO8,824^GB797,0,4^FS");
            sb.append("^FT482,726^A0N,42,43^FH\\^FD");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^FT24,464^A0N,23,24^FH\\^FDCarton ");
            sb.append(labelData.getCartonNum());
            sb.append(" of ");
            sb.append(labelData.getCartonOf());
            sb.append("^FS");
           // sb.append("^FT343,412^A0N,23,24^FH\\^FDModel: 123456^FS");
            sb.append("^FT344,443^A0N,23,24^FH\\^FDCarton Qty: ");
            sb.append(labelData.getQty());
            sb.append("^FS");
            if(labelData.getMultiSku()) {
                sb.append("^FT342,475^A0N,23,24^FH\\^FD Mixed^FS");
            }
            sb.append("^FT22,419^A0N,23,24^FH\\^FDPO#: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT406,292^A0N,23,24^FH\\^FDPRO# ");
            sb.append(labelData.getTrackingNumber());
            sb.append("^FS");
           // sb.append("^FT406,222^A0N,23,24^FH\\^FDSCAC: ^FS");
            sb.append("^FT406,256^A0N,23,24^FH\\^FDCARRIER: ");
            sb.append(labelData.getCarrierName());
            sb.append("^FS");
            sb.append("^FT317,177^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToState());
            sb.append(", ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT326,138^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCity());
            sb.append("^FS");
            sb.append("^FT322,101^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress2());
            sb.append("^FS");
            sb.append("^FT319,31^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT322,70^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress());
            sb.append("^FS");
            sb.append("^FT33,117^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromCity());
            sb.append(", ");
            sb.append(labelData.getShipFromState());
            sb.append(" ");
            sb.append(labelData.getShipFromZip());
            sb.append("^FS");
            sb.append("^FT35,82^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromAddress());
            sb.append("^FS");
            sb.append("^FT38,45^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromName());
            sb.append("^FS");
            sb.append("^BY4,3,110^FT28,357^BCN,,N,N");
            sb.append("^FD>;420");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT135,661^A0N,23,24^FH\\^FD(91)  ");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^FT117,239^A0N,23,24^FH\\^FD(420)  ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FO331,383^GB0,216,2^FS");
            sb.append("^FO284,4^GB0,183,3^FS");
            sb.append("^FO390,195^GB0,179,5^FS");
            sb.append("^BY3,3,106^FT23,791^BCN,,N,N");
            sb.append("^FD>;>691");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^FO401,606^GB0,213,5^FS");
            if(!labelData.getMultiSku()) {
                sb.append("^BY3,2,100^FT429,562^BUN,,Y,N");
                sb.append("^FD");
                sb.append(labelData.getUpc());
                sb.append("^FS");
            }
            sb.append("^PQ1,0,1,Y^XZ");


        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

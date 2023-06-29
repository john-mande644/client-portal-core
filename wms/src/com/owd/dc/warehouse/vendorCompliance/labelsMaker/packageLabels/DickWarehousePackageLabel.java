package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
/**
 * Created by danny on 12/14/2018.
 */
public class DickWarehousePackageLabel extends vendorCompliancePackageLabelBase {

    String department = "";
    String departmentName ="";
    String distributionCenter = "";
    String storeNumber = "";

    public static void main(String[] args){

        DickWarehousePackageLabel label = new DickWarehousePackageLabel();
        try {
            label.loadFromOrderId("22213183");
            String sLabelData = label.getLabelZPL(label.labelData.get(0));
            System.out.println(sLabelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDataFrom850(int clientId){

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(edi850.getBytes()));
            XPath xPath =  XPathFactory.newInstance().newXPath();
            String departmentExp = "/Order/Header/OrderHeader/Department";
            String departmentNameExp = "/Order/Header/OrderHeader/DepartmentDescription";
            if(clientId == 680){
                departmentNameExp = "/Order/Header/Reference[ReferenceQual[contains(text(),'19')]]/Description";

            }



            department = xPath.compile(departmentExp).evaluate(document).trim();
            departmentName = xPath.compile(departmentNameExp).evaluate(document).trim();


            String distrbutionCenterEXP = "/Order/Header/Address[AddressTypeCode[contains(text(),'ST')]]/AddressLocationNumber";
            NodeList nodeList = (NodeList) xPath.compile(distrbutionCenterEXP).evaluate(document, XPathConstants.NODESET);

            if(nodeList.getLength()>0){
                distributionCenter = nodeList.item(0).getFirstChild().getNodeValue().trim();

            }

           String storeNumberExp = "/Order/LineItems/LineItem[1]/QuantitiesSchedulesLocations/LocationQuantity/Location";
            storeNumber = xPath.compile(storeNumberExp).evaluate(document).trim();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception{
        StringBuilder sb = new StringBuilder();
        loadDataFrom850(labelData.getClientId());
        try {


            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,209^FT94,1119^BCN,,Y,Y,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^FO12,138^GB797,0,4^FS");
            sb.append("^FO9,601^GB796,0,4^FS");
            sb.append("^FO7,332^GB796,0,4^FS");
            sb.append("^FO8,824^GB797,0,4^FS");
            //Add below line if we pull DC name from Database
            sb.append("^FT524,729^A0N,28,28^FH\\^FD");
            sb.append(labelData.getShipToName().replace(" DC",""));
            sb.append("^FS");
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
            sb.append("^FT146,633^A0N,32,31^FH\\^FD(91)");
            sb.append(StringUtils.leftPad(distributionCenter,4,"0"));
            sb.append("^FS");
            sb.append("^BY4,3,160^FT42,810^BCN,,N,N");
            sb.append("^FD>;>891");
            sb.append(StringUtils.leftPad(distributionCenter,4,"0"));
            sb.append("^FS");

            sb.append("^FO401,606^GB0,213,5^FS");
            sb.append("^PQ1,0,1,Y^XZ");


        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;

/**
 * Created by danny on 12/14/2018.
 */
public class SephoraPackageLabel extends vendorCompliancePackageLabelBase {

    String department = "";
    String departmentName ="";
    String distributionCenter = "";
    String storeNumber = "";


    public void loadDataFrom850(){

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(edi850.getBytes()));
            XPath xPath =  XPathFactory.newInstance().newXPath();
            String departmentExp = "/Order/Header/OrderHeader/Department";
           // String departmentNameExp = "/Order/Header/OrderHeader/DepartmentDescription";
            department = xPath.compile(departmentExp).evaluate(document).trim();
         //   departmentName = xPath.compile(departmentNameExp).evaluate(document).trim();


            String distrbutionCenterEXP = "/Order/Header/Address/AddressTypeCode[contains(.,'ST')]/parent::Address/AddressLocationNumber";
           // NodeList nodeList = (NodeList) xPath.compile(distrbutionCenterEXP).evaluate(document, XPathConstants.NODESET);


                distributionCenter = xPath.compile(distrbutionCenterEXP).evaluate(document).trim();



         //  String storeNumberExp = "/Order/LineItems/LineItem[1]/QuantitiesSchedulesLocations/LocationQuantity/Location";
         //   storeNumber = xPath.compile(storeNumberExp).evaluate(document).trim();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception{
        StringBuilder sb = new StringBuilder();
        loadDataFrom850();
        try {


            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^FT193,195^A0N,32,31^FH\\^FD(420)");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^BY4,3,105^FT54,318^BCN,,N,N");
            sb.append("^FD>;>8420");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT150,616^A0N,32,31^FH\\^FD(91)");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^BY4,3,160^FT58,793^BCN,,N,N");
            sb.append("^FD>;>891");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^BY4,3,209^FT94,1119^BCN,,Y,Y,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^FO12,138^GB797,0,4^FS");
            sb.append("^FO5,514^GB797,0,3^FS");
            sb.append("^FO13,351^GB797,0,3^FS");
            sb.append("^FO8,824^GB797,0,4^FS");
            sb.append("^FT526,754^A0N,99,124^FH\\^FD");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^FT533,564^A0N,23,31^FH\\^FDFOR^FS");
            sb.append("^FT31,502^A0N,39,38^FH^FDCarton ");
            sb.append(labelData.getCartonNum());
            sb.append(" of ");
            sb.append(labelData.getCartonOf());
            sb.append("^FS");
            sb.append("^FT31,450^A0N,39,38^FH\\^FDDEPT ");
            sb.append(department);
            sb.append("^FS");
            sb.append("^FT31,402^A0N,39,38^FH\\^FDPO: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
                    //^FT567,181^A0N,23,24^FH\^FDCARRIER#^FS
            sb.append("^FT323,126^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCityStateZip());
            sb.append("^FS");
            sb.append("^FT323,61^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT323,93^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress());
            sb.append("^FS");
            sb.append("^FT17,131^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromCityStateZip());
            sb.append("^FS");
            sb.append("^FT17,96^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromAddress());
            sb.append("^FS");
            sb.append("^FT17,59^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromName());
            sb.append("^FS");
            sb.append("^FO494,149^GB0,198,5^FS");
            sb.append("^FO296,7^GB0,132,5^FS");
            sb.append("^FO502,528^GB0,294,5^FS");
            sb.append("^FT14,168^A0N,23,24^FH\\^FDSHIP TO POST^FS");
            sb.append("^FT327,29^A0N,23,24^FH\\^FDTO^FS");
            sb.append("^FT17,28^A0N,23,24^FH\\^FDFROM^FS");
            sb.append("^PQ1,0,1,Y^XZ");


        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

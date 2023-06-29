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
public class CampingWorldPackageLabel extends vendorCompliancePackageLabelBase {

    String department = "";
    String departmentName = "";
    String distributionCenter = "";
    String storeNumber = "";

    public static void main(String[] args) {
        System.setProperty("com.owd.environment", "test");
        CampingWorldPackageLabel label = new CampingWorldPackageLabel();

        try {
            label.loadFromOrderId("21062948");
            String sLabelData = label.getLabelZPL(label.labelData.get(0));
            System.out.println(sLabelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CampingWorldPackageLabel() {
        itemsSQL = "SELECT\n" +
                "    dbo.package_line.pack_quantity,\n" +
                "    dbo.owd_inventory.upc_code as upc, owd_inventory.description\n" +
                "FROM\n" +
                "    dbo.package_line\n" +
                "INNER JOIN\n" +
                "    dbo.owd_line_item\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_line.owd_line_item_fkey = dbo.owd_line_item.line_item_id)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\n" +
                "WHERE\n" +
                "    dbo.package_line.package_fkey = :packageFkey ;";
    }

    public void loadDataFrom850() {

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(edi850.getBytes()));
            XPath xPath = XPathFactory.newInstance().newXPath();
            /*String departmentExp = "/Order/Header/OrderHeader/Department";
            String departmentNameExp = "/Order/Header/OrderHeader/DepartmentDescription";
            department = xPath.compile(departmentExp).evaluate(document).trim();
            departmentName = xPath.compile(departmentNameExp).evaluate(document).trim();*/


            String distrbutionCenterEXP = "/Order/Header/Address[AddressTypeCode='ST']/AddressLocationNumber";
            NodeList nodeList = (NodeList) xPath.compile(distrbutionCenterEXP).evaluate(document, XPathConstants.NODESET);

            if (nodeList.getLength() > 0) {
                distributionCenter = nodeList.item(0).getFirstChild().getNodeValue().trim();

            }

         /*  String storeNumberExp = "/Order/LineItems/LineItem[1]/QuantitiesSchedulesLocations/LocationQuantity/Location";
            storeNumber = xPath.compile(storeNumberExp).evaluate(document).trim();*/


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception {
        StringBuilder sb = new StringBuilder();
        loadDataFrom850();
        try {

            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR4,4~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,209^FT94,1119^BCN,,Y,Y,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^FO6,193^GB797,0,4^FS");
            sb.append("^FO9,601^GB796,0,4^FS");
            sb.append("^FO3,445^GB796,0,4^FS");
            sb.append("^FO3,380^GB796,0,4^FS");
            sb.append("^FO8,824^GB797,0,4^FS");
            sb.append("^FT164,859^A0N,28,33^FH\\^FDSERIAL SHIPPING CONTAINER CODE^FS");
            sb.append("^FT422,642^A0N,28,28^FH\\^FDFINAL DESTINATION / STORE :^FS");
            sb.append("^FT21,639^A0N,28,28^FH\\^FDFINAL DESTINATION / STORE :^FS");
            sb.append("^FT517,692^A0N,34,33^FH\\^FD# ");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^FT7,581^A0N,23,24^FH\\^FDPackage:");
            sb.append(labelData.getCartonNum());
            sb.append(" of ");
            sb.append(labelData.getCartonOf());

            sb.append("^FS");

            if (labelData.getMultiSku()) {
                sb.append("^FT7,479^A0N,23,24^FH\\^FDSKU Number: MIXED^FS");
            } else {
                /*sb.append("^FT7,479^A0N,23,24^FH\\^FDSKU Number: ");
                sb.append(labelData.gets);
                sb.append("^FS");*/
                sb.append("^FT7,513^A0N,23,24^FH\\^FDUPC: ");
                sb.append(labelData.getUpc());
                sb.append("^FS");
            }

            sb.append("^FT7,547^A0N,23,24^FH\\^FDQuantity: ");
            sb.append(labelData.getQty());
            sb.append("^FS");
            sb.append("^FT338,275^A0N,23,24^FH\\^FDTRACKING/PRO#: ");
            sb.append(labelData.getTrackingNumber());
            sb.append("^FS");
            sb.append("^FT15,433^A0N,39,38^FH\\^FDPO Number: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT338,229^A0N,23,24^FH\\^FDSHIPPING CARRIER:  ");

            sb.append("^FS");
            sb.append("^FT29,233^A0N,23,24^FH\\^FDPOSTAL CODE:^FS");
            sb.append("^FT326,177^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCityStateZip());
            sb.append("^FS");
            //sb.append("^FT326,148^A0N,23,24^FH\\^FDMobridge to^FS");
            //sb.append("^FT326,113^A0N,23,24^FH\\^FDAddress 2 to^FS");
            sb.append("^FT326,27^A0N,23,24^FH\\^FDTO:^FS");
            sb.append("^FT326,56^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT326,84^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress());
            sb.append("^FS");
            sb.append("^FT33,137^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromCityStateZip());
            sb.append("^FS");
            sb.append("^FT36,34^A0N,23,24^FH\\^FDFROM:^FS");
            sb.append("^FT35,102^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromAddress());
            sb.append("^FS");
            sb.append("^FT38,65^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromName());
            sb.append("^FS");
            sb.append("^BY3,3,62^FT47,366^BCN,,N,N");
            sb.append("^FD>;420");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT143,682^A0N,23,24^FH\\^FD(92)  ");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^FT101,291^A0N,23,24^FH\\^FD(420)  ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FO313,4^GB0,377,5^FS");
            sb.append("^BY4,3,78^FT58,776^BCN,,N,N");
            sb.append("^FD>;92");
            sb.append(distributionCenter);
            sb.append("^FS");
            sb.append("^FO401,606^GB0,213,5^FS");
            sb.append("^FT560,230^A0N,23,24^FH\\^FD");
            sb.append(labelData.getCarrierName());
            sb.append("^FS");
            sb.append("^PQ1,0,1,Y^XZ");

          /*  sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
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
            sb.append(storeNumber);
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
            sb.append(StringUtils.leftPad(storeNumber,4,"0"));
            sb.append("^FS");
            sb.append("^BY4,3,160^FT42,810^BCN,,N,N");
            sb.append("^FD>;>891");
            sb.append(StringUtils.leftPad(storeNumber,4,"0"));
            sb.append("^FS");

            sb.append("^FO401,606^GB0,213,5^FS");
            sb.append("^PQ1,0,1,Y^XZ");*/


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

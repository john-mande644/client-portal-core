package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;


public class REIPackageLabel extends vendorCompliancePackageLabelBase {

    String department = "";
    String departmentName = "";
    String distributionCenter = "";
    String storeNumber = "";
    String vendor="";
    String casemark="";
    String eventCode="";
    String reiSku="";
    String reiDescription="";

    public static void main(String[] args) {
       // System.setProperty("com.owd.environment", "test");
        REIPackageLabel label = new REIPackageLabel();

        try {
            label.loadFromOrderId("22130672");
            String sLabelData = label.getLabelZPL(label.labelData.get(0));
            System.out.println(sLabelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public REIPackageLabel() {
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

    public void loadDataFrom850(packageLabelDataBean labelData) {

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(edi850.getBytes()));
            XPath xPath = XPathFactory.newInstance().newXPath();
            String eventCodeExp = "/Order/Header/OrderHeader/PromotionDealDescription";
            String departmentNameExp = "/Order/Header/OrderHeader/DepartmentDescription";
            eventCode = xPath.compile(eventCodeExp).evaluate(document).trim();
            //departmentName = xPath.compile(departmentNameExp).evaluate(document).trim();
            if(labelData.getMultiSku()){
                casemark = "Multi...";
                reiSku = "Multi...";
            }else{
                String reiSkuEXP = "/Order/LineItems/LineItem/OrderLine[contains(ConsumerPackageCode,'"+labelData.getUpc()+"')]/BuyerPartNumber";
                String casemarkEXP = "/Order/LineItems/LineItem/OrderLine[contains(ConsumerPackageCode,'"+labelData.getUpc()+"')]/VendorPartNumber";

                     casemark = xPath.compile(casemarkEXP).evaluate(document).trim();
                    reiSku = xPath.compile(reiSkuEXP).evaluate(document).trim();
                    //casemark = nodeList.item(2).getFirstChild().getNodeValue().trim();

                }


/*
            String distrbutionCenterEXP = "/Order/Header/Address[AddressTypeCode='ST']/AddressLocationNumber";
            NodeList nodeList = (NodeList) xPath.compile(distrbutionCenterEXP).evaluate(document, XPathConstants.NODESET);

            if (nodeList.getLength() > 0) {
                distributionCenter = nodeList.item(0).getFirstChild().getNodeValue().trim();

            }*/



         /*  String storeNumberExp = "/Order/LineItems/LineItem[1]/QuantitiesSchedulesLocations/LocationQuantity/Location";
            storeNumber = xPath.compile(storeNumberExp).evaluate(document).trim();*/


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception {
        StringBuilder sb = new StringBuilder();
        loadDataFrom850(labelData);

        try {
            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,209^FT92,1132^BCN,,Y,Y,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^FO6,193^GB797,0,4^FS");
            sb.append("^FO9,570^GB796,0,4^FS");
            sb.append("^FO6,261^GB797,0,4^FS");
            sb.append("^FO7,632^GB797,0,4^FS");
            sb.append("^FO8,824^GB797,0,4^FS");
            sb.append("^FT425,610^A0N,23,24^FH\\^FDof: ");
            sb.append(labelData.getCartonOf());
            sb.append("^FS");
            sb.append("^FT31,618^A0N,23,24^FH\\^FDBox: ");
            sb.append(labelData.getCartonNum());
            sb.append("^FS");
            sb.append("^FT27,752^A0N,23,24^FH\\^FDBOL# ");
            sb.append(labelData.getTrackingNumber());
            sb.append("^FS");
            sb.append("^FT29,686^A0N,23,24^FH\\^FDFree Form Vendor Information:^FS");
            sb.append("^FT17,532^A0N,23,24^FH\\^FDVendor Casemark: ");
            sb.append(casemark);
            sb.append("^FS");
           // sb.append("^FT15,483^A0N,23,24^FH\\^FDSize: M^FS");
           // sb.append("^FT15,441^A0N,23,24^FH\\^FDColor: BLACK^FS");
           // sb.append("^FT15,398^A0N,23,24^FH\\^FDREI description: cooler^FS");
           // sb.append("^FT15,353^A0N,23,24^FH\\^FDCasepack Qty: 20^FS");
            sb.append("^FT15,311^A0N,23,24^FH\\^FDREI Sku: ");
            sb.append(reiSku);
            sb.append("^FS");
            sb.append("^FT16,235^A0N,23,24^FH\\^FDPO NUM: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT427,238^A0N,28,28^FH\\^FDEVENT CODE: ");
            sb.append(eventCode);
            sb.append("^FS");

            sb.append("^FT442,148^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCityStateZip());
            sb.append("^FS");

            sb.append("^FT442,27^A0N,23,24^FH\\^FDTO:^FS");
            sb.append("^FT442,56^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT442,84^A0N,23,24^FH\\^FD");
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
            sb.append("^FO402,194^GB0,66,5^FS");
            sb.append("^FO402,4^GB0,195,7^FS");
            sb.append("^FO397,571^GB0,56,7^FS");
            sb.append("^PQ1,0,1,Y^XZ");

            /*sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR4,4~SD15^JUS^LRN^CI0^XZ");
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
                *//*sb.append("^FT7,479^A0N,23,24^FH\\^FDSKU Number: ");
                sb.append(labelData.gets);
                sb.append("^FS");*//*
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
            sb.append("^PQ1,0,1,Y^XZ");*/




        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

/**
 * Created by danny on 12/14/2018.
 */
public class BackCountryPackageLabel extends vendorCompliancePackageLabelBase {

    String department = "";
    String departmentName = "";
    String distributionCenter = "";
    String vendorSku = "";
    String backcountrySku="";

    public static void main(String[] args) {
       // System.setProperty("com.owd.environment", "test");
        BackCountryPackageLabel label = new BackCountryPackageLabel();

        try {
            label.loadFromOrderId("22227665");
            String sLabelData = label.getLabelZPL(label.labelData.get(0));
            System.out.println(sLabelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BackCountryPackageLabel() {
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

            String backcountrySkuEXP = "/Order/LineItems/LineItem/OrderLine[contains(ConsumerPackageCode,'"+labelData.getUpc()+"')]/BuyerPartNumber";
            String casemarkEXP = "/Order/LineItems/LineItem/OrderLine[contains(ConsumerPackageCode,'"+labelData.getUpc()+"')]/VendorPartNumber";

            vendorSku = xPath.compile(casemarkEXP).evaluate(document).trim();
            backcountrySku = xPath.compile(backcountrySkuEXP).evaluate(document).trim();







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
            sb.append("^BY4,3,285^FT92,1144^BCN,,Y,N,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^FO6,193^GB797,0,4^FS");
            sb.append("^FO1,504^GB796,0,4^FS");
            sb.append("^FO8,772^GB797,0,4^FS");
            sb.append("^FT406,275^A0N,23,24^FH\\^FDB/L: ");
            sb.append(labelData.getTrackingNumber());
            sb.append("^FS");
            sb.append("^FT405,334^A0N,23,24^FH\\^FDNumber of cartons:  ");
            sb.append(labelData.getCartonNum());
            sb.append("of ");
            sb.append(labelData.getCartonOf());
            sb.append("^FS");
            sb.append("^FT420,668^A0N,23,24^FH\\^FDVendor Part #: ");
            sb.append(vendorSku);
            sb.append("^FS");
            sb.append("^FT54,719^A0N,23,24^FH\\^FDCarton Quantity: ");
            sb.append(labelData.getCartonNum());
            sb.append("^FS");
            sb.append("^FT420,628^A0N,23,24^FH\\^FDColor:^FS");
            sb.append("^FT417,552^A0N,23,24^FH\\^FDCarton Weight: ");
            sb.append(labelData.getWeight());
            sb.append("^FS");
            sb.append("^FT54,671^A0N,23,24^FH\\^FDSKU#: ");
            sb.append(backcountrySku);
            sb.append("^FS");
            sb.append("^FT419,590^A0N,23,24^FH\\^FDSize:^FS");
            sb.append("^FT54,629^A0N,23,24^FH\\^FDUPC#: ");
            sb.append(labelData.getUpc());
            sb.append("^FS");
            sb.append("^FT59,588^A0N,23,24^FH\\^FDPO Number: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT47,550^A0N,23,24^FH\\^FDContents:^FS");
            sb.append("^FT49,819^A0N,23,24^FH\\^FDSerialized Shipping Container Barcode^FS");
            sb.append("^FT26,233^A0N,23,24^FH\\^FDShip To Postal Code:^FS");
            sb.append("^FT407,232^A0N,23,24^FH\\^FDCARRIER: ");
            sb.append(labelData.getCarrierName());
            sb.append("^FS");
            sb.append("^FT540,146^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToState());
            sb.append(", ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT403,148^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCity());
            sb.append("^FS");
            sb.append("^FT403,113^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress2());
            sb.append("^FS");
            sb.append("^FT403,27^A0N,23,24^FH\\^FDShip To:^FS");
            sb.append("^FT403,56^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT403,84^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress());
            sb.append("^FS");
            sb.append("^FT33,137^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromCityStateZip());
            sb.append("^FS");
            sb.append("^FT36,34^A0N,23,24^FH\\^FDShip From:^FS");
            sb.append("^FT35,102^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromAddress());
            sb.append("^FS");
            sb.append("^FT38,65^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromName());
            sb.append("^FS");
            sb.append("^FO388,194^GB0,302,8^FS");
            sb.append("^FO389,4^GB0,195,7^FS");
            sb.append("^FT118,288^A0N,43,31^FH\\^FD(420)");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^BY3,3,168^FT62,472^BCN,,N,N");
            sb.append("^FD>;>8420");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
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

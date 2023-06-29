package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import org.drools.common.BeliefSet;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;

/**
 * Created by tony on 08/04/2020.
 */
public class TushBabyBedBathBeyondLabel extends vendorCompliancePackageLabelBase {

    public TushBabyBedBathBeyondLabel(){

        itemsSQL = "SELECT\n" +
                "    dbo.package_line.pack_quantity,\n" +
                "    dbo.owd_inventory.upc_code as upc,\n" +
                "    dbo.owd_inventory.description as description\n" +
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

    String customerOrderNumber="";
    public static void main(String args[]){

        System.setProperty("com.owd.environment","test");
        TushBabyBedBathBeyondLabel label = new TushBabyBedBathBeyondLabel();

        try {
            label.loadFromOrderId("20799026");
            String sLabelData = label.getLabelZPL(label.labelData.get(0));
            System.out.println(sLabelData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

     String vendor = "";
     String store = "";
     // String distributionCenter = "";

    public void loadDataFrom850(){

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(edi850.getBytes()));
            XPath xPath =  XPathFactory.newInstance().newXPath();
            String vendorExp = "/Order/Header/OrderHeader/Vendor";
            vendor = xPath.compile(vendorExp).evaluate(document).trim();

            String storeEXP = "/Order/Header/Address/AddressTypeCode[contains(.,'ST')]/parent::Address/AddressLocationNumber";
            store = xPath.compile(storeEXP).evaluate(document).trim();


            //String ProductColorDescription = "/Order/LineItems/LineItem[1]/OrderLine[1]/ProductColorDescription";
            //color = xPath.compile(ProductColorDescription).evaluate(document).trim();

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

            sb.append("^FT15,47^A0N,28,28^FH\\^FDFROM:^FS");
            sb.append("^FT17,100^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromName());
            sb.append("^FS");
            sb.append("^FT14,136^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromAddress());
            sb.append("^FS");
            sb.append("^FT12,172^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromCity());
            sb.append("^FS");
            sb.append("^FT260,172^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromState());
            sb.append("^FS");
            sb.append("^FT14,211^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromZip());
            sb.append("^FS");

            sb.append("^FT360,47^A0N,28,28^FH\\^FDTO:^FS");
            sb.append("^FT375,100^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT375,136^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress());
            sb.append("^FS");
            sb.append("^FT375,172^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCity());
            sb.append("^FS");
            sb.append("^FT700,172^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToState());
            sb.append("^FS");
            sb.append("^FT375,211^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");

            sb.append("^FT14,300^A0N,28,28^FH\\^FDCARRIER INFO:^FS");
            sb.append("^FT20,350^A0N,28,28^FH\\^FDB/L: ");
            // sb.append(labelData.getBOLNumber());
            sb.append("^FS");
            sb.append("^FT20,380^A0N,28,28^FH\\^FDSCAC: ");
//            sb.append(labelData.scacCode());
            sb.append("^FS");
            sb.append("^FT20,410^A0N,28,28^FH\\^FDPRO: ");
            sb.append(labelData.getTrackingNumber());
            sb.append("^FS");

            sb.append("^FT505,300^A0N,28,28^FH\\^FDShip to Postal Code:^FS");
            sb.append("^FT505,330^A0N,28,28^FH\\^FD(420) ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^BY3,3,120^FT520,470^BCN,,N,N^FD>;420");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");

            sb.append("^FT22,525^A0N,28,28^FH\\^FDPO Number: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT360,525^A0N,28,28^FH\\^FDBBB Vendor Number: ");
            sb.append(vendor);
            sb.append("^FS");
            sb.append("^BY4,3,80^FT30,620^B3N,,N,N^FD>;");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");

            sb.append("^FT22,675^A0N,28,28^FH\\^FDQTY: ");
            sb.append(labelData.getQty());
            sb.append("^FS");

            if(!labelData.getMultiSku()) {
                sb.append("^FT22,705^A0N,28,28^FH\\^FDUPC: ");
                sb.append(labelData.getUpc());
                sb.append("^FS");
                sb.append("^BY4,3,40^FT340,700^BCN,,N,N^FD>;");
                sb.append(labelData.getUpc());
                sb.append("^FS");
                sb.append("^FT22,740^A0N,28,28^FH\\^FDDescription: ");
                sb.append(labelData.getDescription());
                sb.append("^FS");
            }
            else{
                sb.append("^FT22,705^A0N,28,28^FH\\^FDUPC: ");
                sb.append("^FS");
                sb.append("^FT22,740^A0N,28,28^FH\\^FDDescription: ");
                sb.append("Pick and Pack");
                sb.append("^FS");
            }

            sb.append("^FT510,800^A0N,28,28^FH\\^FDStore:^FS");
            sb.append("^FT510,870^A0N,60,60^FH\\^FD");
            sb.append(store);
            sb.append("^FS");

            sb.append("^FT22,927^A0N,23,24^FH\\^FDSSCC-18^FS");
            sb.append("^BY4,3,160^FT94,1130^BCN,,Y,N,Y,U^FD(00) ");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");

            sb.append("^FX***LINES***^");
            sb.append("^FO347,8^GB0,246,1^FS");
            sb.append("^FO12,256^GB797,0,4^FS");
            sb.append("^FO493,260^GB0,246,1^FS");
            sb.append("^FO14,493^GB797,0,4^FS");
            sb.append("^FO8,640^GB797,0,4^FS");

            sb.append("^FO8,755^GB797,0,4^FS");

            sb.append("^FO8,900^GB797,0,4^FS");
            sb.append("^FO493,760^GB0,145,1^FS");

            sb.append("^PQ1,0,1,Y^XZ");

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

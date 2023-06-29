package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;

/**
 * Created by danny on 10/21/2016.
 */
public class WalmartD2SPackageLabel extends vendorCompliancePackageLabelBase {


    String customerOrderNumber="";
    public static void main(String args[]){
        System.setProperty("com.owd.environment","test");
        WalmartD2SPackageLabel label = new WalmartD2SPackageLabel();

        try {
            label.loadFromOrderId("20799008");
            System.out.println(label.getLabelZPL(label.labelData.get(0)));
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public void loadDataFromCommerceHub(){

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(commereHubData.getBytes()));
            XPath xPath =  XPathFactory.newInstance().newXPath();
            String customerOrderNumberExp = "/hubOrder/custOrderNumber";
            // String departmentNameExp = "/Order/Header/OrderHeader/DepartmentDescription";
            customerOrderNumber = xPath.compile(customerOrderNumberExp).evaluate(document).trim();
            //   departmentName = xPath.compile(departmentNameExp).evaluate(document).trim();






        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public WalmartD2SPackageLabel(){

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


    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception{
        loadDataFromCommerceHub();
        StringBuilder sb = new StringBuilder();

        try {


            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,209^FT80,932^BCN,,Y,Y,Y,U");
            sb.append("^FD");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^FO15,187^GB771,0,4^FS");
            sb.append("^FO9,601^GB775,0,4^FS");
            sb.append("^FO12,308^GB774,0,4^FS");
            sb.append("^FT233,583^A0N,23,24^FH\\^FDWalMart Associate Scan ASN Below^FS");
            sb.append("^FT208,533^A0N,23,24^FH\\^FD");
            sb.append(labelData.getProcessingDate());
            sb.append("^FS");
            sb.append("^FT25,533^A0N,23,24^FH\\^FDProcessing Date: ^FS");
            sb.append("^FT181,485^A0N,23,24^FH\\^FD");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT26,484^A0N,23,24^FH\\^FDWM.com PO#:^FS");
            sb.append("^FT221,425^A0N,23,24^FH\\^FD");
            sb.append(customerOrderNumber);
            sb.append("^FS");
            sb.append("^FO8,16^GB781,1170,4^FS");
            sb.append("^FT24,425^A0N,23,24^FH\\^FDCustomer Order #:^FS");
            sb.append("^FT24,360^A0N,39,38^FH\\^FDCustomer: ");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT461,160^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCityStateZip());
            sb.append("^FS");
            sb.append("^FT461,88^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT461,125^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress());
            sb.append("^FS");
            sb.append("^FT67,164^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromCityStateZip());
            sb.append("^FS");
            sb.append("^FT69,129^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromAddress());
            sb.append("^FS");
            sb.append("^FT464,53^A0N,28,28^FH\\^FDSHIP TO^FS");
            sb.append("^FT68,54^A0N,28,28^FH\\^FDSHIP FROM^FS");
            sb.append("^FT67,92^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromName());
            sb.append("^FS");
            sb.append("^FO395,23^GB0,157,5^FS");
            sb.append("^PQ1,0,1,Y^XZ");



        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

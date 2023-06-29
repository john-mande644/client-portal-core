package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import com.owd.core.managers.ManifestingManager;
import com.owd.core.managers.PackingManager;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;

/**
 * Created by danny on 10/21/2016.
 */
public class ScheelsPackageLabel extends vendorCompliancePackageLabelBase {


    String customerOrderNumber="";
    public static void main(String args[]){
        //System.setProperty("com.owd.environment","test");
        ScheelsPackageLabel label = new ScheelsPackageLabel();

        try {
            label.loadFromOrderId("20963165");
            System.out.println(label.getLabelZPL(label.labelData.get(0)));
           // ManifestingManager.getSSCCBarcode();
           // ManifestingManager.getSSCCBarcode();
           // ManifestingManager.getSSCCBarcode();
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

    public ScheelsPackageLabel(){

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


    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception{

        StringBuilder sb = new StringBuilder();

        try {


          sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR4,4~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,209^FT94,1119^BCN,,Y,Y,Y,U");
            sb.append("^FD");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^FO6,193^GB797,0,4^FS");
            sb.append("^FO9,601^GB796,0,4^FS");
            sb.append("^FO3,380^GB796,0,4^FS");
            sb.append("^FO8,824^GB797,0,4^FS");
                    //sb.append("^FT482,726^A0N,42,43^FH\^FDStore Name^FS");
            sb.append("^FT412,344^A0N,23,24^FH\\^FD");
            sb.append(labelData.getCartonNum());
            sb.append(" of ");
            sb.append(labelData.getCartonOf());
            sb.append("^FS");
            sb.append("^FT409,308^A0N,23,24^FH\\^FDNumber of Cartons:^FS");
            if(labelData.getMultiSku()){
                sb.append("^FT17,576^A0N,23,24^FH\\^FDUPC: MIXED^FS");
            }else{
                sb.append("^FT17,576^A0N,23,24^FH\\^FDUPC: 123456789789^FS");
            }

            //Current orders don't have this info, may need to pull from 850 later
            //sb.append("^FT17,541^A0N,23,24^FH\^FDCR #: '456'^FS");
           // sb.append("^FT15,506^A0N,23,24^FH\^FDCO #: '123'^FS");
            sb.append("^FT17,430^A0N,23,24^FH\\^FDContents:^FS");
            sb.append("^FT15,469^A0N,23,24^FH\\^FDPO Number: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT406,229^A0N,23,24^FH\\^FDCARRIER:^FS");
            sb.append("^FT326,177^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToState());
            sb.append(",");
            sb.append(labelData.getShipToZip());
        sb.append("^FS");
            sb.append("^FT326,148^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCity());
            sb.append("^FS");
            sb.append("^FT326,113^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress2());
            sb.append("^FS");
            sb.append("^FT326,27^A0N,23,24^FH\\^FDShip To:^FS");
            sb.append("^FT326,56^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT326,84^A0N,23,24^FH\\^FD");
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
            sb.append("^BY4,3,110^FT28,357^BCN,,N,N");
            sb.append("^FD>;420");

            sb.append("^FS");
            //sb.append("^FT135,661^A0N,23,24^FH\\^FD(91)  90210^FS");
            sb.append("^FT117,239^A0N,23,24^FH\\^FD(420)  ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");

            sb.append("^FO284,4^GB0,183,3^FS");
            sb.append("^FO390,195^GB0,179,5^FS");
           // sb.append("^BY4,3,106^FT58,776^BCN,,N,N");
            //sb.append("^FD>;910306^FS");
            sb.append("^FO401,606^GB0,213,5^FS");
            sb.append("^FT514,228^A0N,23,24^FH\\^FD");
            sb.append(labelData.getCarrierName());
            sb.append("^FS");
            sb.append("^PQ1,0,1,Y^XZ");


           /* sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
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
            sb.append("^PQ1,0,1,Y^XZ");*/



        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

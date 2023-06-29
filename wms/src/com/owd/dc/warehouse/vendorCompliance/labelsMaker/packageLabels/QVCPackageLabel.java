package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;


public class QVCPackageLabel extends vendorCompliancePackageLabelBase {

    String department = "";
    String departmentName = "";
    String distributionCenter = "";
    String storeNumber = "";
    String vendor="";
    String vendorId="";
    String casemark="";
    String eventCode="";
    String reiSku="";
    String reiDescription="";

    public static void main(String[] args) {
       // System.setProperty("com.owd.environment", "test");
        QVCPackageLabel label = new QVCPackageLabel();

        try {
            label.loadFromOrderId("22130672");
            String sLabelData = label.getLabelZPL(label.labelData.get(0));
            System.out.println(sLabelData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QVCPackageLabel() {
        itemsSQL = "SELECT\\n" +
                "    dbo.package_line.pack_quantity,\\n" +
                "    dbo.owd_inventory.upc_code as upc, owd_inventory.description\\n" +
                "FROM\\n" +
                "    dbo.package_line\\n" +
                "INNER JOIN\\n" +
                "    dbo.owd_line_item\\n" +
                "ON\\n" +
                "    (\\n" +
                "        dbo.package_line.owd_line_item_fkey = dbo.owd_line_item.line_item_id)\\n" +
                "INNER JOIN\\n" +
                "    dbo.owd_inventory\\n" +
                "ON\\n" +
                "    (\\n" +
                "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\\n" +
                "WHERE\\n" +
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
        if(labelData.getClientId()==651){
            vendor = "Lawless Beauty Inc.";
            vendorId = "FZ31";
        }

        try {

            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^FO9,808^GB522,0,7^FS");
            sb.append("^FO177,400^GB631,0,3^FS");
            sb.append("^FO181,9^GB0,1204,6^FS");
            sb.append("^FO342,8^GB0,1208,6^FS");
            sb.append("^FO526,8^GB0,1198,5^FS");
            sb.append("^FO474,814^GB0,396,6^FS");
            sb.append("^FO660,5^GB0,395,6^FS");
            sb.append("^BY3,3,65^FT677,80^BCR,,N,N");
            sb.append("^FD>:");
            sb.append(labelData.getQty());
            sb.append("^FS");
            sb.append("^FT784,75^A0R,23,24^FB101,1,0,C^FH\\^FDCARTON^FS");
            sb.append("^FT756,75^A0R,23,24^FB101,1,0,C^FH\\^FDQUANTITY^FS");
            sb.append("^FT762,210^A0R,39,38^FH\\^FD");
            sb.append(labelData.getQty());
            sb.append("^FS");
            sb.append("^FT67,879^A0R,39,48^FH\\^FD");
            sb.append(labelData.getCartonNum());
            sb.append("  OF  ");
            sb.append(labelData.getCartonOf());
            sb.append("^FS");
            sb.append("^FT383,921^A0R,39,48^FH\\^FD");
            //todo Country of Origin
            sb.append("^FS");
            if(!labelData.getMultiSku()){
            sb.append("^BY2,3,160^FT603,500^BCR,,Y,N");
                sb.append("^FD>:Sku Barcode^FS");
            }

            sb.append("^FT136,964^A0R,20,19^FH\\^FDCARTON^FS");
            sb.append("^FT440,896^A0R,20,19^FH\\^FDCOUNTRY OF ORIGIN:^FS");
            sb.append("^FT413,424^A0R,39,38^FH\\^FD");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT471,425^A0R,23,24^FH\\^FDP.O. NUMBER:^FS");
            sb.append("^FT156,20^A0R,17,16^FH\\^FDSerialized Shipping Container Code^FS");
            sb.append("^FT259,27^A0R,17,16^FH\\^FD");
            sb.append(vendor);
            sb.append("^FS");
            //todo vin and description
            sb.append("^FT444,26^A0R,20,19^FH\\^FDVin: 5342345 Color: Black^FS");
            sb.append("^FT301,28^A0R,17,16^FH\\^FDFROM:  ");
            sb.append(vendorId);
            sb.append("^FS");
            sb.append("^FT487,18^A0R,23,24^FH\\^FDVendor Item # and/or Description^FS");
            //todo ship to DC
            sb.append("^FT586,18^A0R,23,24^FH\\^FDDC 92, Piney Flats, TN^FS");
            sb.append("^FT628,18^A0R,23,24^FH\\^FDSHIP TO DC#^FS");
            sb.append("^BY4,3,82^FT14,104^BCR,,Y,Y,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^PQ1,0,1,Y^XZ");

            if(labelData.getMultiSku()){
                sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
                sb.append("^XA");
                sb.append("^MMT");
                sb.append("^PW812");
                sb.append("^LL1218");
                sb.append("^LS0");
                sb.append("^FO346,808^GB461,0,4^FS");
                sb.append("^FO339,398^GB469,0,5^FS");
                sb.append("^FO342,8^GB0,1208,6^FS");
                sb.append("^FO549,8^GB0,1198,5^FS");
                sb.append("^FT481,873^A0R,23,24^FB102,1,0,C^FH\\^FDCARTON^FS");
                sb.append("^FT453,873^A0R,23,24^FB102,1,0,C^FH\\^FDQUANTITY^FS");
                sb.append("^FT459,1008^A0R,39,38^FH\\^FD");
                sb.append(labelData.getQty());
                sb.append("^FS");
                sb.append("^FT407,464^A0R,39,48^FH\\^FD");
                sb.append(labelData.getCartonNum());
                sb.append("  OF  ");
                sb.append(labelData.getCartonOf());
                sb.append("^FS");
                sb.append("^BY2,3,160^FT595,835^BCR,,Y,N");
                sb.append("^FD>:");
                sb.append(labelData.getPurchaseOrder());
                sb.append("^FS");
                sb.append("^FT476,549^A0R,20,19^FH\\^FDCARTON^FS");
                sb.append("^FT775,951^A0R,23,24^FH\\^FDP.O. NUMBER:^FS");
                sb.append("^FT644,39^A0R,23,24^FH\\^FD");
                sb.append(labelData.getShipFromCityStateZip());
                sb.append("^FS");
                sb.append("^FT683,42^A0R,23,24^FH\\^FD");
                sb.append(labelData.getShipFromAddress());
                sb.append("^FS");
                sb.append("^FT399,26^A0R,23,24^FH\\^FD");
                sb.append(labelData.getShipToCityStateZip());
                sb.append("^FS");
                sb.append("^FT721,41^A0R,23,24^FH\\^FD");
                sb.append(labelData.getShipFromName());
                sb.append("^FS");
                sb.append("^FT437,29^A0R,23,24^FH\\^FD");
                sb.append(labelData.getShipToAddress());
                sb.append("^FS");
                sb.append("^FT761,74^A0R,23,24^FH\\^FDSHIPPED FROM:^FS");
                sb.append("^FT475,27^A0R,23,24^FH\\^FD");
                sb.append(labelData.getShipToName());
                sb.append("^FS");
                //todo get sku's and look through for sku and qty
                      /*  ^FT53,970^A0R,23,24^FH\\^FDQTY:12^FS
                        ^FT53,625^A0R,23,24^FH\\^FDHSN ITEM #12^FS
                        ^FT52,371^A0R,23,24^FH\\^FDQTY:6^FS
                        ^FT52,27^A0R,23,24^FH\\^FDHSN ITEM #6^FS
                        ^FT137,622^A0R,23,24^FH\\^FDHSN ITEM #10^FS
                        ^FT96,622^A0R,23,24^FH\\^FDHSN ITEM #11^FS
                        ^FT96,966^A0R,23,24^FH\\^FDQTY:11^FS
                        ^FT95,368^A0R,23,24^FH\\^FDQTY:5^FS
                        ^FT136,24^A0R,23,24^FH\\^FDHSN ITEM #4^FS
                        ^FT95,24^A0R,23,24^FH\\^FDHSN ITEM #5^FS
                        ^FT178,622^A0R,23,24^FH\\^FDHSN ITEM #9^FS
                        ^FT137,966^A0R,23,24^FH\\^FDQTY:10^FS
                        ^FT136,368^A0R,23,24^FH\\^FDQTY:4^FS
                        ^FT218,624^A0R,23,24^FH\\^FDHSN ITEM #8^FS
                        ^FT178,966^A0R,23,24^FH\\^FDQTY:9^FS
                        ^FT177,368^A0R,23,24^FH\\^FDQTY:3^FS
                        ^FT256,625^A0R,23,24^FH\\^FDHSN ITEM #7^FS
                        ^FT177,24^A0R,23,24^FH\\^FDHSN ITEM #3^FS
                        ^FT218,968^A0R,23,24^FH\\^FDQTY:8^FS
                        ^FT217,370^A0R,23,24^FH\\^FDQTY:2^FS
                        ^FT217,26^A0R,23,24^FH\\^FDHSN ITEM #2^FS
                        ^FT256,969^A0R,23,24^FH\\^FDQTY:7^FS
                        ^FT255,371^A0R,23,24^FH\\^FDQTY:1^FS
                        ^FT255,27^A0R,23,24^FH\\^FDHSN ITEM #1^FS
                        ^FT292,947^A0R,23,24^FH\\^FDITEM QTY:^FS
                        ^FT292,718^A0R,23,24^FH\\^FDHSN ITEM #^FS
                        ^FT292,344^A0R,23,24^FH\\^FDITEM QTY:^FS
                        ^FT292,115^A0R,23,24^FH\\^FDHSN ITEM #^FS
                        ^FT516,60^A0R,23,24^FH\\^FDSHIPPED TO:^FS*/
                sb.append("^PQ1,0,1,Y^XZ");

            }




        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

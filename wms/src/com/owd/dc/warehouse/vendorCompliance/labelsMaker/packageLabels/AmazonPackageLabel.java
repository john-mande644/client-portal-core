package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

/**
 * Created by danny on 10/21/2016.
 */
public class AmazonPackageLabel extends vendorCompliancePackageLabelBase {

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception{
        StringBuilder sb = new StringBuilder();

        try {


            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ:");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,160^FT94,1110^BCN,,Y,N,Y,U");
            sb.append("^FD(00) ");
            sb.append(labelData.getSSCCNoCheckDigit());
            sb.append("^FS");
            sb.append("^FO12,256^GB797,0,4^FS");
            sb.append("^FO14,493^GB797,0,4^FS");
            sb.append("^FO8,831^GB797,0,4^FS");
            sb.append("^FO493,260^GB0,246,1^FS");
            sb.append("^FO347,8^GB0,246,1^FS");
            sb.append("^BY2,3,94^FT400,693^BCN,,N,N");
            sb.append("^FD>:");
            sb.append(labelData.getPurchaseOrder());
            sb.append(">77774^FS");
            sb.append("^FT22,778^A0N,28,28^FH\\^FDCARTON#: ");
            sb.append(labelData.getCartonNum());
            sb.append(" OF ");
            sb.append(labelData.getCartonOf());
            sb.append("^FS");
            sb.append("^FT22,703^A0N,28,28^FH\\^FDQTY: ");
            sb.append(labelData.getQty());
            sb.append("^FS");
            if(labelData.getMultiSku()){
                sb.append("^FT22,637^A0N,28,28^FH\\^FDMULTI SKU ");

            }else{
                sb.append("^FT22,637^A0N,28,28^FH\\^FDUPC: ");
                sb.append(labelData.getUpc());
            }

            sb.append("^FS");
            sb.append("^FT22,561^A0N,28,28^FH\\^FDPURCHASE ORDERS: ");
            sb.append(labelData.getPurchaseOrder());
            sb.append("^FS");
            sb.append("^FT360,42^A0N,28,28^FH\\^FDSHIP TO:^FS");
            sb.append("^FT15,47^A0N,28,28^FH\\^FDSHIP FROM:^FS");
            sb.append("^FT523,416^A0N,23,24^FH\\^FDBOL#^FS");
            sb.append("^FT521,369^A0N,23,24^FH\\^FDPRO#: ");
            sb.append(labelData.getTrackingNumber());
            sb.append("^FS");
            sb.append("^FT519,330^A0N,23,24^FH\\^FD");
            if(labelData.getCarrierName().equals("LTL")){
                sb.append("CALL FOR ROUTING");
            }else{
                sb.append(labelData.getCarrierName());
            }

            sb.append("^FS");
            sb.append("^FT375,202^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT375,163^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCity());
            sb.append(", ");
            sb.append(labelData.getShipToState());
            sb.append("^FS");
            sb.append("^FT14,211^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromZip());
            sb.append("^FS");
            sb.append("^FT375,91^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT375,127^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToAddress());
            sb.append("^FS");
            sb.append("^FT12,172^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromCity());
            sb.append(", ");
            sb.append(labelData.getShipFromState());
            sb.append("^FS");
            sb.append("^FT14,136^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromAddress());
            sb.append("^FS");
            sb.append("^FT17,100^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromName());
            sb.append("^FS");
            sb.append("^FT217,927^A0N,23,24^FH\\^FDSerial Shipping Container Code (SSCC)^FS");

            sb.append("^BY4,3,128^FT91,418^BCN,,N,N");
            sb.append("^FD>;420");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT168,463^A0N,28,28^FH\\^FD(420)  ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^PQ1,0,1,Y^XZ");


        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }
}

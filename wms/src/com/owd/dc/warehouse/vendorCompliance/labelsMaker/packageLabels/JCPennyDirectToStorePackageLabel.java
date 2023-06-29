package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

/**
 * Created by danny on 5/1/2017.
 */
public class JCPennyDirectToStorePackageLabel extends vendorCompliancePackageLabelBase {

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception {
        StringBuilder sb = new StringBuilder();

        try {

                    sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
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
                    sb.append("^FO10,590^GB797,0,3^FS");
            sb.append("^FO8,831^GB797,0,4^FS");
            sb.append("^FO406,10^GB0,568,3^FS");

            sb.append("^FT32,734^A0N,28,28^FH\\^FDScan Label^FS");
            sb.append("^FT30,682^A0N,28,28^FH\\^FD");

            sb.append(jcpenneyD2SORderId(labelData.getShipToAddress2()));
            sb.append("^FS");
            sb.append("^FT30,638^A0N,28,28^FH\\^FD");
            sb.append(jcpenneyD2Sname(labelData.getShipToAddress2()));

            sb.append("^FS");


            sb.append("^FT433,54^A0N,28,28^FH\\^FDTO:^FS");
            sb.append("^FT15,47^A0N,28,28^FH\\^FDFROM:^FS");


            sb.append("^FT433,412^A0N,23,24^FH\\^FDSUB: ");
            sb.append(getSubPO(labelData.getPurchaseOrder()));
            sb.append("^FS");
            sb.append("^FT433,365^A0N,23,24^FH\\^FDPO#: ");
            sb.append(getBeginPO(labelData.getPurchaseOrder()));
            sb.append("^FS");
            sb.append("^FT433,326^A0N,23,24^FH\\^FDSupplier#^FS");
            sb.append("^FT433,214^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT433,175^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToCity());
            sb.append(", ");
            sb.append(labelData.getShipToState());

            sb.append("^FS");

            sb.append("^FT14,211^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipFromZip());
            sb.append("^FS");
            sb.append("^FT433,103^A0N,23,24^FH\\^FD");
            sb.append(labelData.getShipToName());
            sb.append("^FS");
            sb.append("^FT433,139^A0N,23,24^FH\\^FD");
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
            sb.append("^BY4,3,128^FT38,523^BCN,,N,N");

            sb.append("^FD>;420");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");

            sb.append("^FT111,366^A0N,28,28^FH\\^FD(420)  ");
            sb.append(labelData.getShipToZip());
            sb.append("^FS");
            sb.append("^FT57,312^A0N,28,28^FH\\^FDSHIP TO POSTAL CODE^FS");
            sb.append("^PQ1,0,1,Y^XZ");





        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to load zpl");
        }

        return sb.toString();
    }

    public static String jcpenneyD2Sname(String s){
        return s.substring(0,s.indexOf("       "));



    }
    public static String jcpenneyD2SORderId(String s){
        return s.substring(s.lastIndexOf("             "), s.indexOf("SCAN")).trim();
    }

    public static String getBeginPO(String s){
        return s.substring(0,s.indexOf("_"));
    }
    public static String getSubPO(String s){
        return s.substring(s.indexOf("_")+1);
    }
}
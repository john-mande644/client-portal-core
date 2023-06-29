package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

/**
 * Created by danny on 10/21/2016.
 */
public class WalmartS2SPackageLabel extends vendorCompliancePackageLabelBase {

    @Override
    public String getLabelZPL(packageLabelDataBean labelData) throws Exception{
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
            sb.append(labelData.getOrderReference());
            sb.append("^FS");
            sb.append("^FO8,16^GB781,1170,4^FS");
            sb.append("^FT24,425^A0N,23,24^FH\\^FDCustomer Order #:^FS");
            sb.append("^FT24,360^A0N,39,38^FH\\^FDCustomer: PICKUP AT STORE^FS");
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

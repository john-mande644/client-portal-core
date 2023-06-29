package com.owd.dc.manifest.OSMWorldwide;

import com.owd.connectship.soap.NameAddress;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPMENT;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.owd.dc.packing.AutoBatchPrinting.PackingABUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/23/14
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class OSMUtils {
    public static void main(String[] args){
        try{
            //      System.out.println(calculateCheckDigit("920559014040801000002"));
            //System.out.println(getPackageId("p5806713*14912599*b1"));
         //   LABELDATA ld = reprintLabelFromPackageBarcode("p10471472*22106643*b1");
          //  System.out.println(ld.getValue());

            System.out.println(OSMUnserveable("23456"));
            System.out.println(OSMUnserveable("00678"));
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public static String calculateCheckDigit(String number) throws Exception{
         String numberWithCheckDigit="";
         if(number.length()!=21) throw new Exception("Invalid lenght for this operation");
        int[] digits = new int[21];
        int i =0;
        for(i=0;i<21;i++){
            digits[i] = Integer.parseInt(String.valueOf(number.charAt(i)));
        }
        System.out.println(Arrays.toString(digits));
        int one = digits[0]+digits[2]+digits[4]+digits[6]+digits[8]+digits[10]+digits[12]+digits[14]+digits[16]+digits[18]+digits[20];
        System.out.println(one);
        one = one*3;
        System.out.println(one);
        int two = digits[1]+digits[3]+digits[5]+digits[7]+digits[9]+digits[11]+digits[13]+digits[15]+digits[17]+digits[19];
        System.out.println(two);
        int three = one+two;
        System.out.println(three);
        int four = ((three+9)/10)*10;
        int check = four - three;
        System.out.println(four);
        System.out.println(check);
        numberWithCheckDigit = number+ check;
        return numberWithCheckDigit;
    }
    public static OSMOrderInfo loadOrderInfo(AMPConnectShipShipment ship) throws Exception{
        OSMOrderInfo orderInfo = new OSMOrderInfo();
       System.out.println("Loading return");
        NameAddress clientnew = ship.getReturnNA() ;
        orderInfo.setOrderId(ship.getValueAsString("OWDORDERID"));
        orderInfo.setFacilityCode(ship.getValueAsString("CURRENT_FACILITY"));
        orderInfo.setReturnCompany(clientnew.getCompany());
        orderInfo.setReturnName(clientnew.getContact());
        orderInfo.setReturnAddress(clientnew.getAddress1());
        orderInfo.setReturnCity(clientnew.getCity());
        orderInfo.setReturnState(clientnew.getStateProvince());
        orderInfo.setRerutnZip(clientnew.getPostalCode());

        orderInfo.setShipperRef(ship.getValueAsString("SHIPPER_REFERENCE"));
        orderInfo.setConsigneeRef(ship.getValueAsString("CONSIGNEE_REFERENCE"));
       System.out.println("Loading OSM shipto");
        NameAddress customer = ship.getConsigneeNA();
        orderInfo.setShipName(customer.getContact());
        orderInfo.setShipCompany(customer.getCompany());
        orderInfo.setShipAddress1(customer.getAddress1());
        orderInfo.setShipAddress2(customer.getAddress2());
        orderInfo.setShipCity(customer.getCity());
        orderInfo.setShipState(customer.getStateProvince());
        orderInfo.setShipZip(customer.getPostalCode());
        orderInfo.setShipCountry(customer.getCountrySymbol());
        orderInfo.setShipMethod(ship.getAssignedServiceCode());


        return orderInfo;
    }

    public static boolean OSMUnserveable(String zip){
        boolean unserveable = false;
        List<String> whitezone = new ArrayList<>();
        whitezone.add("006");
        whitezone.add("007");
        whitezone.add("008");
        whitezone.add("009");
        whitezone.add("055");
        whitezone.add("090");
        whitezone.add("091");
        whitezone.add("092");
        whitezone.add("093");
        whitezone.add("094");
        whitezone.add("095");
        whitezone.add("096");
        whitezone.add("097");
        whitezone.add("098");
        whitezone.add("099");
        whitezone.add("771");
        whitezone.add("962");
        whitezone.add("963");
        whitezone.add("964");
        whitezone.add("965");
        whitezone.add("966");
        whitezone.add("967");
        whitezone.add("968");
        whitezone.add("969");
        whitezone.add("974");
        whitezone.add("975");
        whitezone.add("976");
        whitezone.add("988");
        whitezone.add("989");
        whitezone.add("995");
        whitezone.add("996");
        whitezone.add("997");
        whitezone.add("998");
        whitezone.add("999");

        if(whitezone.contains(zip.substring(0,3))) return true;

        return unserveable;
    }

    public static OSMPackage loadPackageInfo(AMPConnectShipShipment ship,int packageIndex) throws Exception{
        OSMPackage pkg = new OSMPackage();

           pkg.setPackageBarcode(ship.getValueAsString("PACKAGE_BARCODE"));
           pkg.setPackageWeight(Double.parseDouble(ship.getValueAsString("WEIGHT")));
        pkg.setPackageId(getPackageId(pkg.getPackageBarcode()));
        pkg.setPackageIndex(packageIndex);


        return pkg;
    }

    private static String getSerivceCode(OSMPackage pkg, OSMOrderInfo orderInfo){

        String service="";
        if(orderInfo.getShipMethod().equals("OWD.1ST.LETTER")||orderInfo.getShipMethod().equals("TANDATA_USPS.USPS.FIRST")||orderInfo.getShipMethod().equals("USPS First-Class Mail")){

            service = "001";

        }else {
            if (pkg.getPackageWeight() < 1) {
                service = "748";
            } else {
                service = "612";
            }
        }
        return service;

    }

    public static void calculateTracking(OSMPackage pkg, OSMOrderInfo orderInfo,String mailerId) throws Exception{
         if(orderInfo.getShipMethod().equals("OWD.OSM.INTL")){
             System.out.println("We have an international package");
             //todo check epakaet stuff. figure out barcode etc..
             pkg.setPostTracking(false);
             pkg.setLabelBarcode(pkg.getPackageBarcode().replace("*","-"));
             pkg.setTrackingNumber(pkg.getLabelBarcode());

         } else{

             pkg.setPostTracking(true);
            System.out.println("We have domestic");
             String begin = "420";
             System.out.println(orderInfo.getShipZip());

             String service= getSerivceCode(pkg,orderInfo);

             //String serial=pkg.getPackageBarcode().replace("p","").split("\\*")[0];
             String serial = getPackageId(pkg.getPackageBarcode());
             serial = serial.substring(serial.length()-7);
             String tracking = "92"+service+mailerId+serial;
             tracking = calculateCheckDigit(tracking);
             pkg.setTrackingNumber(tracking);
             pkg.setLabelBarcode(begin+orderInfo.getShipZip().substring(0, 5)+tracking);
         }

    }
    public static String getPackageId(String barcode) throws Exception{
          String sql = "select id from package where pack_barcode = :barcode";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("barcode",barcode);
        List results = q.list();
        if(results.size()>0){
           return results.get(0).toString();
        } else{
            throw new Exception("unable to locate id for barcode: " + barcode);
        }

    }
    public static String lookupOSMMailerIdByClientId(int clientId){
      //todo actual lookup if needed for multiple clients
        return "902175263";

    }

    public static SHIPMENT getLabelData(OSMPackage pkg, OSMOrderInfo orderInfo)throws Exception{
        SHIPMENT shipment = new SHIPMENT();
        LABELDATA ld = new LABELDATA();
        ld.setCopies_Needed("1");
        String service = getSerivceCode(pkg,orderInfo);
        if(orderInfo.getShipMethod().equals("OWD.OSM.INTL")){
            ld.setValue(getInternationalLabelDataBase64(pkg,orderInfo,service));
        } else{

           ld.setValue(getDomesticLabelDataBAse64(pkg,orderInfo,service));
        }
        shipment.getLABELDATA().add(ld);

       return shipment;
    }
    public static LABELDATA reprintLabelFromPackageBarcode(String barcode) throws Exception{
        LABELDATA ld = new LABELDATA();
        String sql = "select label_string from package where pack_barcode = :barcode";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("barcode",barcode);
        List l = q.list();
        if(l.size()>0){
            Clob clob = (Clob) l.get(0);
            ld.setCopies_Needed("1");
            ld.setValue(PackingABUtils.convertClobToString(clob));


        }else{
            throw new Exception("No info found for Package Barcode: "+barcode);
        }


        return  ld;
    }
    private static String getInternationalLabelDataBase64(OSMPackage pkg, OSMOrderInfo orderInfo, String service)throws Exception{
        StringBuffer data = new StringBuffer();
               data.append("~SD20\n" +
                       "^XA\n" +
                       "\n" +
                       "^FT20,298^A0N,25,25^FD");
        data.append(orderInfo.getReturnName());

                data.append("^FS\n" +
                       "^FT20,322^A0N,25,25^FD");
        data.append(orderInfo.getReturnAddress());
                data.append("^FS\n" +
                       "^FT20,347^A0N,25,25^FD");

                data.append(orderInfo.getReturnCity()+", "+orderInfo.getReturnState()+"  "+orderInfo.getRerutnZip());
                data.append("^FS\n" +
                       "^FT20,371^A0N,25,25^FD^FS\n" +
                       "^FT20,395^A0N,25,25^FD^FS\n" +
                       "\n" +
                       "^FT538,323^A0N,25,25^FD");
        data.append(pkg.getPackageWeight());
        data.append(" LBS ^FS\n" +
                       "^FT538,352^A0N,30,30^FD^FS\n" +
                       "^FT93,433^A0N,35,35^FDSHIP^FS\n" +
                       "^FT98,473^A0N,35,35^FD TO:^FS\n" +
                       "^FT203,427^A0N,33,33^FD");
       data.append(orderInfo.getShipCompany());
                data.append("^FS\n" +
                       "^FT203,457^A0N,33,33^FD");
        data.append(orderInfo.getShipName());

                data.append("^FS\n" +
                       "^FT203,487^A0N,33,33^FD");
                     data.append(orderInfo.getShipAddress1());
        data.append("^FS\n" +
                       "^FT203,520^A0N,38,38^FD");

         data.append(orderInfo.getShipCity()+", "+orderInfo.getShipState()+"  "+orderInfo.getShipZip());

                data.append("^FS\n" +
                       "^FT203,555^A0N,38,38^FD");
       data.append(orderInfo.getShipCountry());
                data.append("^FS\n" +
                       "^BY2,,156^FT73,860^BCN,,N^FD");
                     data.append(pkg.getLabelBarcode());
        data.append("^FS\n" +
                       "^FT194,922^A0N,35,35^FD");
                     data.append(pkg.getTrackingNumber());
        data.append("^FS\n" +
                       "^FT20,995^A0N,25,25^FDSHIPPER REF: ");
        data.append(orderInfo.getShipperRef());

                data.append("^FS\n" +
                       "^FT20,1025^A0N,25,25^FDCONSIGNEE REF: ");
        data.append(orderInfo.getConsigneeRef());

                data.append("^FS\n" +
                       "^FT20,1055^A0N,25,25^FDISAL^FS\n" +
                       "^XZ");

                return org.apache.commons.codec.binary.Base64.encodeBase64String(data.toString().getBytes());
    }

    private static String getDomesticLabelDataBAse64(OSMPackage pkg, OSMOrderInfo orderInfo, String service) throws Exception{
        StringBuffer data = new StringBuffer();
        if(orderInfo.getShipMethod().contains("1ST")||orderInfo.getShipMethod().toUpperCase().contains("FIRST")){

            data.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            data.append("^XA");
            data.append("^MMT");
            data.append("^PW812");
            data.append("^LL1218");
            data.append("^LS0");
            data.append("^FO4,619^GB807,0,13^FS");
            data.append("^FO3,940^GB807,0,12^FS");
            data.append("^FT13,1025^A0N,28,28^FH\\^FDCONSIGNEE REF: ");
            data.append(orderInfo.getConsigneeRef());
            data.append("^FS");
            data.append("^FT13,986^A0N,28,28^FH\\^FDSHIPPER REF: ");
            data.append(orderInfo.getShipperRef());
            data.append("^FS");

            data.append("^BY3,3,160^FT76,856^BCN,,N,N");
            data.append("^FD>;");
            data.append(pkg.getLabelBarcode());
            data.append("^FS");
            StringBuilder b = new StringBuilder();
            b.append(pkg.getTrackingNumber());
            int i = 4;

            while (i < b.length()){
                b.insert(i," ");
                i = i + 5;
            }

            data.append("^FT168,917^A0N,34,33^FH\\^FD");
            data.append(b.toString());
            data.append("^FS");
            data.append("^FT233,670^A0N,34,36^FH\\^FDUSPS TRACKING # eVS^FS");
            data.append("^FO3,194^GB803,0,4^FS");
            data.append("^FO6,267^GB802,0,4^FS");
            data.append("^FO194,2^GB0,193,7^FS");
            data.append("^FO437,14^GB343,162,4^FS");
            data.append("^FT55,153^A0N,141,194^FH\\^FDF^FS");
            data.append("^FT121,248^A0N,45,50^FH\\^FDUSPS FIRST-CLASS PKG^FS");
            data.append("^FT463,60^A0N,23,24^FH\\^FDFIRST-CLASS PKG^FS");
            data.append("^FT463,88^A0N,23,24^FH\\^FDU.S. POSTAGE & FEES PAID^FS");
            data.append("^FT463,116^A0N,23,24^FH\\^FDOSM^FS");
            data.append("^FT463,144^A0N,23,24^FH\\^FDE-VS^FS");
            data.append("^FT29,304^A0N,18,24^FH\\^FD");
            data.append(orderInfo.getReturnCompany());
            data.append("^FS");
            data.append("^FT29,326^A0N,18,24^FH\\^FD");
            data.append(orderInfo.getReturnName());
            data.append("^FS");
            data.append("^FT29,348^A0N,18,24^FH\\^FD");
            data.append(orderInfo.getReturnAddress());
            data.append("^FS");
            data.append("^FT29,370^A0N,18,24^FH\\^FD");
            data.append(orderInfo.getReturnCity());
            data.append(",");
            data.append(orderInfo.getReturnState());
            data.append(" ");
            data.append(orderInfo.getRerutnZip());

            data.append("^FS");
            data.append("^FT209,439^A0N,25,28^FH\\^FD");
            data.append(orderInfo.getShipCompany());
            data.append("^FS");
            data.append("^FT209,470^A0N,25,28^FH\\^FD");
            data.append(orderInfo.getShipName());
            data.append("^FS");
            data.append("^FT209,501^A0N,25,28^FH\\^FD");
            data.append(orderInfo.getShipAddress1());
            data.append("^FS");
            data.append("^FT209,532^A0N,25,28^FH\\^FD");
            data.append(orderInfo.getShipAddress2());
            data.append("^FS");
            data.append("^FT207,584^A0N,29,38^FH\\^FD");
            data.append(orderInfo.getShipCity());
            data.append(",");
            data.append(orderInfo.getShipState());
            data.append(" ");
            data.append(orderInfo.getShipZip());
           data.append("^FS");
            data.append("^PQ1,0,1,Y^XZ");



        }else {


            data.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            data.append("^XA");
            data.append("^MMT");
            data.append("^PW812");
            data.append("^LL1218");
            data.append("^LS0");
            data.append("^FO4,619^GB807,0,13^FS");
            data.append("^FO3,940^GB807,0,12^FS");
            data.append("^FT13,1025^A0N,28,28^FH\\^FDCONSIGNEE REF: ");
            data.append(orderInfo.getConsigneeRef());
            data.append("^FS");
            data.append("^FT13,986^A0N,28,28^FH\\^FDSHIPPER REF: ");
            data.append(orderInfo.getShipperRef());
            data.append("^FS");

            data.append("^BY3,3,160^FT76,856^BCN,,N,N");
            data.append("^FD>;");
            data.append(pkg.getLabelBarcode());
            data.append("^FS");
            StringBuilder b = new StringBuilder();
            b.append(pkg.getTrackingNumber());
            int i = 4;

            while (i < b.length()){
                b.insert(i," ");
                i = i + 5;
            }

            data.append("^FT168,917^A0N,34,33^FH\\^FD");
            data.append(b.toString());
            data.append("^FS");
            data.append("^FT233,670^A0N,34,36^FH\\^FDUSPS TRACKING # eVS^FS");
            data.append("^FO3,194^GB803,0,4^FS");
            data.append("^FO6,267^GB802,0,4^FS");
          //  data.append("^FO194,2^GB0,193,7^FS"); vertical line
          //  data.append("^FO36,15^GB343,161,4^FS");box for sort codes
           // data.append("^FO437,14^GB343,162,4^FS"); box for endecia
          //  data.append("^FT55,153^A0N,141,194^FH\\^FDF^FS"); F first class
            data.append("^FT121,248^A0N,45,50^FH\\^FD^FS");//Bar under indicia
         /*   if(service.equals("612")) {
                data.append("^FT463,60^A0N,23,24^FH\\^FDPARCEL SELECT^FS");
            }
            if(service.equals("748")){
                data.append("^FT463,60^A0N,23,24^FH\\^FDPS LIGHTWEIGHT^FS");
            }

            data.append("^FT463,88^A0N,23,24^FH\\^FDU.S. POSTAGE & FEES PAID^FS");
            data.append("^FT463,116^A0N,23,24^FH\\^FDOSM^FS");
            data.append("^FT463,144^A0N,23,24^FH\\^FDE-VS^FS");*/
            data.append("^FT29,304^A0N,18,24^FH\\^FD");
            data.append(orderInfo.getReturnCompany());
            data.append("^FS");
            data.append("^FT29,326^A0N,18,24^FH\\^FD");
            data.append(orderInfo.getReturnName());
            data.append("^FS");
            data.append("^FT29,348^A0N,18,24^FH\\^FD");
            data.append(orderInfo.getReturnAddress());
            data.append("^FS");
            data.append("^FT29,370^A0N,18,24^FH\\^FD");
            data.append(orderInfo.getReturnCity());
            data.append(",");
            data.append(orderInfo.getReturnState());
            data.append(" ");
            data.append(orderInfo.getRerutnZip());

            data.append("^FS");
            data.append("^FT209,439^A0N,25,28^FH\\^FD");
            data.append(orderInfo.getShipCompany());
            data.append("^FS");
            data.append("^FT209,470^A0N,25,28^FH\\^FD");
            data.append(orderInfo.getShipName());
            data.append("^FS");
            data.append("^FT209,501^A0N,25,28^FH\\^FD");
            data.append(orderInfo.getShipAddress1());
            data.append("^FS");
            data.append("^FT209,532^A0N,25,28^FH\\^FD");
            data.append(orderInfo.getShipAddress2());
            data.append("^FS");
            data.append("^FT207,584^A0N,29,38^FH\\^FD");
            data.append(orderInfo.getShipCity());
            data.append(",");
            data.append(orderInfo.getShipState());
            data.append(" ");
            data.append(orderInfo.getShipZip());
            data.append("^FS");
           /* data.append("^FO40,97^GB336,0,4^FS");
            data.append("^FO210,18^GB0,152,4^FS");
            data.append("^FT262,147^A0N,28,28^FH\\^FDSC4^FS");
            data.append("^FT94,146^A0N,28,28^FH\\^FDSC3^FS");
            data.append("^FT261,66^A0N,28,28^FH\\^FDSC2^FS");
            data.append("^FT90,65^A0N,28,28^FH\\^FDSC1^FS");*/  //box data
            data.append("^PQ1,0,1,Y^XZ");


        }

        return org.apache.commons.codec.binary.Base64.encodeBase64String(data.toString().getBytes());

    }

}

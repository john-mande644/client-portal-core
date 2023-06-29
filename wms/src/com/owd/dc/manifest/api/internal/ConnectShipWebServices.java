package com.owd.dc.manifest.api.internal;

import com.owd.connectship.services.CSVoidShipmentService;
import com.owd.connectship.services.ConnectShipCommunications;
import com.owd.connectship.xml.api.OWDShippingRequest.DOCREQUEST;
import com.owd.core.WebResource;
import com.owd.core.TabReader;
import com.owd.core.managers.JasperReportPrintManager;
import com.owd.hibernate.HibernateSession;

import java.util.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

import inship.PackageDetail;
import inship.USPSAccount;
import inship.Uspsshipintl;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2008
 * Time: 12:17:05 PM
 * To change this template use File | Settings | File Templates.
 */

public class ConnectShipWebServices {


    protected static void voidShipment(String carrier, String shipper, List msn) throws Exception {

        CSVoidShipmentService.voidShipment(ShipConfig.csComm, shipper, carrier, msn);

    }

    public static void postVoidShipmentForShippedPackage(String packBarcode, String MSN) throws Exception {

    try
    {

              // ////System.out.println("voiding shipment msn "+getValueAsString(ShipConfig.MSN));

              WebResource loader = new WebResource("http://" + ShipConfig.kNetServicesServer, WebResource.kGETMethod);


              loader.addParameter("fn", "vs");
              loader.addParameter("msn", MSN);

             // loader.addParameter("order_num", getValueAsString(ShipConfig.SHIPPER_REFERENCE));

           //   loader.addParameter("order_fkey", getValueAsString("OWDORDERID"));

              loader.addParameter("package_barcode", packBarcode);

              //System.out.println("voiding package barcode " + getValueAsString("PACKAGE_BARCODE"));

              BufferedReader reader = loader.getResource();

              // ////System.out.println("saving shipment");

              TabReader responseData = new TabReader(reader, false);


              if (responseData.getRowCount() < 1)

                  throw new Exception("Server not updated - Server did not respond. Notify office.");

              if (responseData.getRowSize(0) < 1)

                  throw new Exception("Server not updated - Server responded incorrectly. Notify office.");

              String response = ((String) responseData.getStrValue(0, 0, "ERROR")).trim();

              if (response.equals("ERROR"))

                  throw new Exception((String) responseData.getStrValue(0, 1, "Unknown") + " Notify office.");

          } catch (Exception ex) {

              ex.printStackTrace();

              throw new Exception("Server not updated - Server did not respond. Notify office.");


          }

      }


    private static String uspsProductionID = "815OWD001839";

    /*static protected List<byte[]> printUSPSIntlDocAsPDF(AMPConnectShipShipment shipment) throws Exception {
        Uspsshipintl labelMaker = new Uspsshipintl();
     //     System.out.println("lic:"+labelMaker.getLicenseNumber());
    //      labelMaker.setLicenseNumber("BUJ2VA-1SUBRA1SUB3S8A2245");
        labelMaker.setRuntimeLicense(
                OWDUtilities.getIPWorksInshipRuntimeLicense()
        );

       //  labelMaker.config("CERTIFY=TRUE");

          DecimalFormat df = new DecimalFormat("###,###.##");
        USPSAccount acc = new USPSAccount();
        acc.setServer("http://production.shippingapis.com/ShippingAPI.dll");
        acc.setUserId(uspsProductionID);
          labelMaker.setUSPSAccount(acc);
          labelMaker.config("NetCharges="+(df.format(new Double(shipment.getValueAsString(ShipConfig.TOTAL))));

          labelMaker.setContents(Uspsshipintl.ctOther);
          labelMaker.config("OtherContents=Merchandise");
       //   labelMaker.setComments("testing");
     //     labelMaker.setHSTariffNumber("123.123.123.123");


          String[] splitClientName = shipment.getClientNA().getContact().split(" ");
          labelMaker.setFromFirstName(splitClientName[0]);
          labelMaker.setFromLastName(splitClientName[1]);
       //   ShipConfig.Location shipperLocation = ShipConfig.getConfig().getLocationMap().get(shipment.getValueAsString("CURRENT_FACILITY"));
          labelMaker.setFromFirm("One World Direct");
          labelMaker.setFromAddress2("1915 10th St W");
          labelMaker.setFromCity("Mobridge");
          labelMaker.setFromState("SD");
          labelMaker.setFromZipCode("57601");
          labelMaker.setFromPhone("8662091413");



          labelMaker.setToAddress1(StringEscapeUtils.escapeXml(shipment.getConsigneeNA().getContact()));
          labelMaker.setToAddress2(StringEscapeUtils.escapeXml(shipment.getConsigneeNA().getAddress1()));
          labelMaker.setToAddress3(StringEscapeUtils.escapeXml(shipment.getConsigneeNA().getAddress2()));
          labelMaker.setToAddress4(StringEscapeUtils.escapeXml(shipment.getConsigneeNA().getCity() + ", " + shipment.getConsigneeNA().getStateProvince() + " " + shipment.getConsigneeNA().getPostalCode()));
          labelMaker.setToCity(StringEscapeUtils.escapeXml(shipment.getConsigneeNA().getCity()));
        labelMaker.setToCountry(StringEscapeUtils.escapeXml(shipment.getConsigneeNA().getCountrySymbol().replaceAll("_"," ")
                      .replaceAll("UNITED KINGDOM","UNITED KINGDOM (GREAT BRITAIN)")
                      .replaceAll("TRINIDAD TOBAGO","TRINIDAD AND TOBAGO")
              .replaceAll("MACEDONIA","MACEDONIA, REPUBLIC OF")
                      .replaceAll("ANGUILLA ISLANDS","ANGUILLA")
                  .replaceAll("KYRGHYZSTAN","KYRGYZSTAN")
                      .replaceAll("SERBIA","SERBIA, REPUBLIC OF")
                      .replaceAll("BRUNEI","BRUNEI DARUSSALAM")
                      .replaceAll("ST LUCIA","SAINT LUCIA")
                .replaceAll("IVORY COAST",StringEscapeUtils.escapeXml(
"Cote d Ivoire (Ivory Coast)".toUpperCase()))
                                        .replaceAll("ST KITTS","Saint Kitts (St. Christopher and Nevis)")
                                        .replaceAll("ST VINCENT GRENADINES","Saint Vincent and the Grenadines")
                                .replaceAll("KOREA SOUTH","Korea, Republic of (South Korea)")));
                                if( shipment.getConsigneeNA().getCountrySymbol().equals("CONGO")){
                                    labelMaker.setToCountry("CONGO, DEMOCRATIC REPUBLIC OF THE");

              }

              System.out.println("Using USPS country:"+labelMaker.getToCountry()+" for CS country:"+shipment.getConsigneeNA().getCountrySymbol().replaceAll("_"," "));


          labelMaker.setToPhone(StringEscapeUtils.escapeXml(shipment.getConsigneeNA().getPhone()));
          labelMaker.setToPostalCode(StringEscapeUtils.escapeXml(shipment.getConsigneeNA().getPostalCode()));
          if (shipment.isAPOFPO()) {
              labelMaker.setToAPOFPOZip(shipment.getConsigneeNA().getPostalCode().replaceAll("-","").substring(0,5));
              labelMaker.setToCountry("");

          labelMaker.setContents(Uspsshipintl.ctGift);
          labelMaker.config("OtherContents=Gift");

          }
          System.out.println("add2:" + labelMaker.getToAddress1());
                   System.out.println("add2:" + labelMaker.getToAddress2());
                   System.out.println("add2:" + labelMaker.getToAddress3());
                   System.out.println("add2:" + labelMaker.getToAddress4());
                   System.out.println("add2:" + labelMaker.getToAddress5());
                   System.out.println("add2:" + labelMaker.getToAddress6());
                   System.out.println("add2:" + labelMaker.getToCity());
                   System.out.println("add2:" + labelMaker.getToPostalCode());
                   System.out.println("add2:" + labelMaker.getToCountry());


          labelMaker.setLabelImageType(Uspsshipintl.sitGIF); //PDF

          labelMaker.setNonDeliveryOption(Uspsshipintl.ctReturn);
          labelMaker.setRecipientCustomsReference(shipment.getValueAsString(ShipConfig.SHIPPER_REFERENCE));
          labelMaker.setInvoiceNumber(shipment.getValueAsString(ShipConfig.CONSIGNEE_REFERENCE));

          labelMaker.setDeliveryType(Uspsshipintl.dtAirmail);
          String grossWeight = shipment.getValueAsString(ShipConfig.WEIGHT);
          //System.out.println("Gross Weight=" + grossWeight);
          if (grossWeight.indexOf(".") < 1) {
              throw new Exception("Weight value invalid");
          }

        PackageDetail pd = new PackageDetail();
        pd.setWeight(shipment.getValueAsString(ShipConfig.WEIGHT)) ;
        pd.setNetCharge((df.format(new Double(shipment.getValueAsString(ShipConfig.TOTAL)))));


          int numberOfItems = shipment.getItemData()[0].length;
          System.out.println("numitems="+numberOfItems);
          labelMaker.setItemCount(numberOfItems>5?5:numberOfItems);
          //     labelMaker.setContents(Customslabels.ctMerchandise);
System.out.println(shipment.getItemData());
          //Assigns values to item arrays
          for (int i = 0; i < (numberOfItems>5?5:numberOfItems); i++) {

              System.out.println(shipment.getItemData()[ConnectShipShipment.kTDItemDesc][i]+":"+shipment.getItemData()[ConnectShipShipment.kTDItemPrice][i]);
              labelMaker.setItemDescription(i, StringEscapeUtils.escapeXml(shipment.getItemData()[ConnectShipShipment.kTDItemDesc][i]));
              labelMaker.setItemQuantity(i, Integer.parseInt(shipment.getItemData()[ConnectShipShipment.kTDItemQty][i]));
              labelMaker.setItemValue(i, shipment.getItemData()[ConnectShipShipment.kTDItemPrice][i]);

          }


           
          //System.out.println();
          labelMaker.setCustomsFormFile(System.getProperty("java.io.tmpdir")+shipment.getValue("SHIPMENTBARCODE")+".gif");
          labelMaker.getFormCP72();
          List<byte[]> labelList = new ArrayList<byte[]>();

       // if(labelMaker.getCP72PageCount()<1)
       // {
            throw new Exception ("USPS Customer Forms call returned zero pages");
       // }
        labelMaker.getFormCP72();

          labelList.add();
          return labelList;

      }
*/


    static protected byte[] printCommercialInvoice(String orderNum) throws Exception {

        return JasperReportPrintManager.getCommInvoicePdf(orderNum);
      /* System.out.println("trying to do the USPS ya ya  ya");
        Map parameterMap = new HashMap();
        parameterMap.put("ORDER_NUM", orderNum);

        JasperReport jr = OWDJasperReports.getInstance().getUpsCustoms();
        net.sf.jasperreports.engine.JasperPrint print = net.sf.jasperreports.engine.JasperFillManager.fillReport(jr,
                parameterMap, ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
        System.out.println("Done loading");
        return net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf(print);*/
    }

    
    public static List<byte[]> printIntlDocPDF(AMPConnectShipShipment css) throws Exception {
        List<byte[]> docList = new ArrayList<byte[]>();





        if ("true".equalsIgnoreCase("" + css.getValue(ShipConfig.RETURN_DELIVERY))
                ||
                ("TRUE".equalsIgnoreCase((String) css.getValue(ShipConfig.CALLTAG))))

        {
            //System.out.println("no label print for calltag");
            return docList;
        }
        System.out.println(css.isShipped());
        System.out.println(css.getValue(ShipConfig.WEIGHT))  ;
        if (false) {

            throw new Exception("Can't print unshipped package...");


        } else {
            //System.out.println("sequence #:" + css.getValueAsString(ShipConfig.NOFN_SEQUENCE));
            if (css.getAssignedCarrierCode().indexOf("USPS") >= 0) {
                //System.out.println("getting USPS customs doc");
               // throw new Exception("Call to defunct usps intl doc method!");
               // docList.addAll(printUSPSIntlDocAsPDF(css));
            } else if ("1".equals(css.getValueAsString(ShipConfig.NOFN_SEQUENCE))) {
                //System.out.println("getting commercial invoice");
                docList.add(printCommercialInvoice(css.getValueAsString(ShipConfig.SHIPPER_REFERENCE)));
                System.out.println("done sending yeah yeah yah");
            }
        }
        return docList;

    }

    public static List<byte[]> printThermalLabels(String packBarcode, String labelPrinterCode, boolean isInternational) throws Exception {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String sqlQuery = "execute sp_loadPackageShipLabelData ?";
        HibernateSession.currentSession().flush();
        PreparedStatement  ps = HibernateSession.getPreparedStatement(sqlQuery);
        ps.setString(1, packBarcode);
        ResultSet rs = ps.executeQuery();
        StringBuilder xmlString = new StringBuilder();
        List<byte[]> additionalLabels = new ArrayList<byte[]>();



        if (rs.next()) {
            if(rs.getString("service_code").contains("UPS.SP")&& isInternational){
                additionalLabels = AmpLabelUtilites.getUSPSCN22ForSurePost(rs);
            }


            ////System.out.println("printlabelargs="+msn+","+bundleID+","+serviceCode+","+carrierCode+","+labelCode+","+package_count+","+package_number);
        String labelcode = rs.getString("label_code");

            if(rs.getString("carrier_code").equals("CONNECTSHIP_DHLGLOBALMAIL.DHL")){
                if(rs.getString("ship_city").equalsIgnoreCase("APO")||rs.getString("ship_city").equalsIgnoreCase("FPO")){
                    labelcode = "CONNECTSHIP_DHLGLOBALMAIL_APOFPO_LABEL.STANDARD";
                }
            }

           if (labelcode.equalsIgnoreCase("CONNECTSHIP_UPS_MAXICODE_US_DOMESTIC.STANDARD") && isInternational) {

                labelcode = "CONNECTSHIP_UPS_MAXICODE_US_INTL.STANDARD";
            }

            xmlString.append("<?xml version=\"1.0\"?>\n" +
                     "<LABELREQUEST>\n" +

                    "<LOGIN>\n" +
                    "<LOGINID>test</LOGINID>\n" +
                    "<PASSWORD>test</PASSWORD>\n" +
                    "</LOGIN>\n" +
                    "<PACKAGELABELS>\n" +
                    "<PKG>\n" +
                    "<REFERENCE>\n" +
                    "<SHIPPERREFERENCE>" + rs.getString("order_num") + "</SHIPPERREFERENCE>");

                    if(!rs.getString("service_code").contains("DHL")){
                xmlString.append("<NOFN_SEQUENCE>" + rs.getString("pack_index") + "</NOFN_SEQUENCE>\n" +
                        "<NOFN_TOTAL>" + rs.getString("ship_packs") + "</NOFN_TOTAL>\n" );

            }


                   xmlString.append("</REFERENCE>\n" +
                    "<SHIPPERINFO>\n" +
                    "<SHIPPER>" + rs.getString("shipper_acct") + "</SHIPPER>\n" +
                    "</SHIPPERINFO>\n" +
                    "<SCS>" + rs.getString("service_code") + "</SCS>\n" +
                    "<SC>" + rs.getString("carrier_code") + "</SC>\n" +
                    "<MSN>" + rs.getString("msn") + "</MSN>\n" +
                    (rs.getString("bundle_id").length() > 1 ? "<BUNDLEID>" + rs.getString("bundle_id") + "</BUNDLEID>" : "") +
                    "<LABELFORMAT>" + labelcode + "</LABELFORMAT>\n" +
                    "<SHIPDATE>" + formatter.format(rs.getDate("ship_date")) + "</SHIPDATE>\n" +
                    "<LABELPRINT>\n" +
                    "<PORT>STRING</PORT>\n" +
                    "<STOCKSYMBOL>THERMAL_LABEL_8</STOCKSYMBOL>\n" +
                    "<PRINTERMODELSYMBOL>" + labelPrinterCode + "</PRINTERMODELSYMBOL>\n" +
                    "<ENCODE>BASE64</ENCODE></LABELPRINT>\n" +
                     "<ONE_OR_ALL>ONE</ONE_OR_ALL>\n" +
                    "</PKG>\n" +
                    "</PACKAGELABELS>\n" +
                    "</LABELREQUEST>");
        } else {
            throw new Exception("No ship records found for pack barcode " + packBarcode);
        }
        rs.close();
        if (xmlString == null) {
            throw new Exception("No ship records found for pack barcode " + packBarcode);
        }
        System.out.println("label Requestttttttttttttttttttttttttttttttttttttttttttt: ");
        System.out.println(xmlString);
        StringBuffer sb = new StringBuffer();
        WebResource server = new WebResource(ConnectShipCommunications.liveServerURL, "POST");
        server.setParameters(xmlString.toString());
        //   //System.out.println(ostr.toString());
        server.contentType = "text/xml";
        BufferedReader response = server.getResource();
        int line = 0;
        line = (int) response.read();
        while (line != -1) {


            sb.append((char) line);

            //	sbprintable.append(new Character((char)(line<32?32:line)));

            line = response.read();
        }

        String bob = sb.toString();


        System.out.println(bob);
        List<byte[]> labelList = new ArrayList<byte[]>();

        int startLabel = -1;
        int endLabel = -1;


        startLabel = bob.indexOf("<LABELSTRING>");
        endLabel = bob.indexOf("</LABELSTRING>");

        String label = "";
        while (startLabel >= 0 & endLabel > startLabel) {
            label = bob.substring(startLabel + 13, endLabel);
            //	//System.out.println("64Label:"+label+"\n\n");
            //	//System.out.println("64Rawlabel:"+sbprintable+"\n\n");
               System.out.print(label);
            labelList.add(Base64.decodeBase64(label));
             System.out.println(Base64.decodeBase64(label));
            bob = bob.substring(endLabel + 5);

            startLabel = bob.indexOf("<LABELSTRING>");
            endLabel = bob.indexOf("</LABELSTRING>");

        }
        if(additionalLabels.size()>0){
            labelList.addAll(additionalLabels);

        }


        return labelList;

    }


    public static void main(String[] args) {
        //(String msn,String bundleID,String serviceCode,String carrierCode,String labelCode)
        //System.out.println(args);
        //main3(args);

        try{
           /* String sqlQuery = "execute sp_loadPackageShipLabelData ?";
            HibernateSession.currentSession().flush();
            PreparedStatement  ps = HibernateSession.getPreparedStatement(sqlQuery);
            ps.setString(1, "p8796056*19120897*b1");
            ResultSet rs = ps.executeQuery();*/

            System.out.println(printThermalLabels("p9608391*20518714*b1","Zebra.ZebraZ4Mplus",true));

        }catch(Exception e){
            e.printStackTrace();
        }
      /*  try{
       printThermalLabels("p1653228*7903373*b1", "Zebra.ZebraZ4Mplus", true);
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    public static void main2(String[] args){
        try {
        List<String> ll = new ArrayList();

ll.add("p795897*6572637*b8");

            for(String ss : ll){



            
    List<byte[]> l =    printThermalLabels(ss,"Datamax.ProdigyMax", false);
            //testprint.printZebra2746eTest("COM1",l.get(0));

            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main3(String[] args)

    {

        //get valid return fields for shipper/carrier combos
        try {

            String xmlString = "<?xml version=\"1.0\"?>\n" +
                    "<LABELREQUEST>\n" +
                    "<LOGIN>\n" +
                    "<LOGINID>test</LOGINID>\n" +
                    "<PASSWORD>test</PASSWORD>\n" +
                    "</LOGIN>\n" +
                    "<PACKAGELABELS>\n" +
                    "<PKG>\n" +
                    "<REFERENCE>\n" +
                    "<SHIPPERREFERENCE>18806913</SHIPPERREFERENCE>\n" +
                    "</REFERENCE>\n" +
                    "<SHIPPERINFO>\n" +
                    "<SHIPPER>OWD_PROD_DC6                    </SHIPPER>\n" +
                    "</SHIPPERINFO>\n" +
                    "<SCS>CONNECTSHIP_DHL.DHL.WPX</SCS>\n" +
                    "<SC>CONNECTSHIP_DHL.DHL</SC>\n" +
                    "<MSN>13288813</MSN>\n" +
                    "<LABELFORMAT>CONNECTSHIP_DHL_LABEL.STANDARD</LABELFORMAT>\n" +
                    "<SHIPDATE>08/25/2016</SHIPDATE>\n" +
                    "<LABELPRINT>\n" +
                    "<PORT>STRING</PORT>\n" +
                    "<STOCKSYMBOL>THERMAL_LABEL_8</STOCKSYMBOL>\n" +
                    "<PRINTERMODELSYMBOL>Zebra.ZebraZ4Mplus</PRINTERMODELSYMBOL>\n" +
                    "<ENCODE>BASE64</ENCODE></LABELPRINT>\n" +
                    "<ONE_OR_ALL>ONE</ONE_OR_ALL>\n" +
                    "</PKG>\n" +
                    "</PACKAGELABELS>\n" +
                    "</LABELREQUEST>";

            //  ret.toXml(System.out,"   ",true);

System.out.println("ello");
            StringBuffer sb = new StringBuffer();
            WebResource server = new WebResource(ConnectShipCommunications.liveServerURL, "POST");
            server.setParameters(xmlString);
            //   //System.out.println(ostr.toString());
            server.contentType = "text/xml";
            BufferedReader response = server.getResource();
            int line = 0;
            line = (int) response.read();
            while (line != -1) {


                sb.append((char) line);

                //	sbprintable.append(new Character((char)(line<32?32:line)));

                line = response.read();
            }

            String bob = sb.toString();


            System.out.println(bob);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }


}

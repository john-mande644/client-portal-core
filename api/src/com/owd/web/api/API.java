package com.owd.web.api;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;


public class API {
private final static Logger log =  LogManager.getLogger();


    private static final String majorVersion = "1";

    private static final String minorVersion = "0";


    static public String getVersionString() {

        return getMajorVersion() + "." + getMinorVersion();

    }


    static public String getMajorVersion() {

        return majorVersion;

    }


    static public String getMinorVersion() {

        return minorVersion;

    }


    static public String buildAttribute(String name, String val) {
        if (val == null) val = "";

        return name+"=\""+ StringEscapeUtils.escapeXml(val)+"\"";

    }

    public static boolean isValidURL(String urlStr) {
        try {
            URL url = new URL(urlStr);
            return true;
        }
        catch (MalformedURLException e) {
            return false;
        }
    }


    public static String escapeXML(String string) {
        StringBuffer sb = new StringBuffer();
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    static public String buildTextElement(String name, String val) {
        if (val == null) val = "";


            return "<"+name + ">"+StringEscapeUtils.escapeXml(val)+"</"+name+">";



    }


    static public void main(String[] args) {


        /*

        //Note that the OWD_API_REQUEST testing attribute is FALSE -> orders are actually being created, but since the inventory level is zero for the test item

        //and since we know on an operations level that you're not "live" yet, that's safe to do at the moment.

        // You can view and void orders by visiting the extranet after creating them here.



        //also note the client_id and client_authorization attribute values - these are the actual values you can use for your interactions with the server

        //array of OWD XML API requests

        String[] payload = {"<?xml version=\"1.0\"?><OWD_API_REQUEST api_version=\"1.0\" client_id=\"137\" client_authorization=\"ggnIfNH++3TRI74tM/YLOAM=\" "+

        " testing=\"FALSE\"><OWD_ORDER_CREATE_REQUEST order_reference=\""+gzOrderReference+"\" order_source=\"GroundZero\" is_gift=\"NO\" "+

        "backorder_rule=\"PARTIALSHIP\" hold_for_release=\"YES\"><SHIPPING_INFO last_name=\"MAHONEY\" first_name=\"MATT\" "+

        "company_name=\"HAL Inc.\" address_one=\"3075 SANDERS RD\" address_two=\"H2A\" city=\"NORTHBROOK\" state=\"IL\" zip=\"60062\" "+

        "country=\"US\" phone=\"847-402-2036\" email=\"\" ship_cost=\"6.50\" ship_type=\"OWD.1ST.LETTER\" insure_amount=\"0\">"+

        "</SHIPPING_INFO><BILLING_INFO last_name=\"MAHONEY\" first_name=\"MATT\" company_name=\"ALLSTATE\" address_one=\"3075 SANDERS RD\" "+

        "address_two=\"\" city=\"NORTHBROOK\" state=\"IL\" zip=\"60062\" country=\"US\" phone=\"847-402-2036\" email=\"\" tax_pct=\"\" po_number=\"\" "+

        "paid=\"YES\" payment_type=\"CLIENT\"></BILLING_INFO><LINE_ITEM part_reference=\"TESTSKU-1\" description=\"Virtual SKU for testing only\" requested=\"1\" "+

        "cost=\"6.98\"></LINE_ITEM>"+

        "</OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>", //end order creation request -- begin order status request

        */


        /*
            5223684
            String[] payload = {"<?xml version=\"1.0\"?><OWD_API_REQUEST api_version=\"1.3\" client_id=\"160\" "

            +"client_authorization=\""+com.owd.OWDUtilities.encryptData("160")+"\" testing=\"TRUE\"><OWD_ORDER_STATUS_REQUEST clientOrderId=\"48061\" />"

            +"</OWD_API_REQUEST>"};





            StringBuffer sb;



            //loop through each request in the payload array, send it, and print the results to stdout

            for(int i = 0; i<payload.length;i++)

            {

                sb = new StringBuffer();

                try{

                    //should be https if you have SSL support installed for your JRE - either http or https works, but obviously it should be secure

                    WebResource server = new WebResource("http://secure.owd.com/webapps/api/api.jsp","POST");

                    server.setParameters(payload[i]);



                log.debug(":::API REQUEST BEGIN:::");

                log.debug(payload[i]);

                log.debug(":::API REQUEST END:::");



                    server.contentType = "text/xml";

                    BufferedReader response = server.getResource();

                    int line = 0;

                    line = (int)response.read();

                    while (line != -1)

                    {

                        //Uncomment the next block if displaying result in HTML



                        if((char)line == '>')

                            sb.append("&gt;\n");

                        else if ((char)line == '<')

                            sb.append("&lt;");

                        else



                        sb.append((char)line);



                        line = response.read();

                    }

                }catch(Exception ex)

                {

                    ex.printStackTrace();

                }

                    log.debug(":::API RESPONSE BEGIN:::");

                    log.debug(sb.toString());

                    log.debug(":::API RESPONSE END:::");

                }

            }
            */



        try {

       //     log.debug("Inside testOWD.jsp");
       //     log.debug(CountryNames.getCountryName("PS"));




//String xmlString="<?xml version=\"1.0\"?><!DOCTYPE ADDRESSVALIDATEREQUEST SYSTEM \"http:\\\\localhost\\Progistics\\XML_Processor\\DTD\\AddressValidateRequest.dtd\"><ADDRESSVALIDATEREQUEST><LOGIN><LOGINID>test</LOGINID><PASSWORD>test</PASSWORD></LOGIN><ADDRESS_DATA><ADDRESSREFERENCE>CONS2</ADDRESSREFERENCE><COMPANY>ConnectShip, Inc"
//+"</COMPANY><ADDRESS1>8282 South Memorial</ADDRESS1><ADDRESS2>Suite 400</ADDRESS2><CITY>Tulsa</CITY><STATEPROVINCE>OK</STATEPROVINCE><POSTALCODE>74133</POSTALCODE><PHONE>918 499-2800 X123</PHONE><COUNTRYSYMBOL>UNITED_STATES</COUNTRYSYMBOL></ADDRESS_DATA></ADDRESSVALIDATEREQUEST>";


//"<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.6\" client_id=\"160\" client_authorization=\"O11TFC7ZkoFEfAcfXUR4KQM=\" testing=\"TRUE\"><OWD_ORDER_STATUS_REQUEST clientOrderId=\"160854\" summary=\"TRUE\"/></OWD_API_REQUEST>"


           // String xmlString = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?><OWD_API_REQUEST api_version=\"1.3\" client_authorization=\"3bzi4TMn0a1u0wbJjDI1dgM=\" client_id=\"160\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST backorder_rule=\"BACKORDER\" is_gift=\"NO\" order_reference=\"349952\" order_source=\"Internet\"><SHIPPING_INFO address_one=\"4960 Iroquois Ave. #3\" address_two=\"\" city=\"San Diego\" company_name=\"\" country=\"US\" email=\"hollydec@earthlink.net\" first_name=\"Holly\" last_name=\"Roark\" phone=\"6192760386\" ship_cost=\"0.00\" ship_type=\"BWY\" state=\"CA\" zip=\"92117\"><BEST_WAY><CARRIER><![CDATA[OWD.1ST.PRIORITY]]></CARRIER><CARRIER><![CDATA[UPS.GND]]></CARRIER><CARRIER><![CDATA[OWD.1ST.LETTER]]></CARRIER><CARRIER><![CDATA[POS.GPM]]></CARRIER><CARRIER><![CDATA[OWD.AIR.SMP]]></CARRIER><CARRIER><![CDATA[UPS.STDCAMX]]></CARRIER><CARRIER><![CDATA[OWD.AIR.PARCELPOST]]></CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO address_one=\"4960 Iroquois Ave. #3\" address_two=\"\" city=\"San Diego\" company_name=\"\" country=\"US\" email=\"hollydec@earthlink.net\" first_name=\"Holly\" last_name=\"Roark\" order_discount=\"0.00\" paid=\"YES\" payment_type=\"CLIENT\" phone=\"6192760386\" state=\"CA\" zip=\"92117\"></BILLING_INFO><LINE_ITEM cost=\"0.00\" customs_desc=\"Complimentary No Spin Lapel Pin with Thank you Card - renewal membership\" declared_value=\"0.00\" description=\"Complimentary No Spin Lapel Pin with Thank you Card - renewal membership\" part_reference=\"18949\" requested=\"1\"></LINE_ITEM></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


                // 5223684
         //   String xmlString ="<?xml version=\"1.0\" encoding=\"iso-8859-1\"?><OWD_API_REQUEST api_version=\"1\" client_id=\"139\" client_authorization=\"8CWzBcVVR9AAGsjwH0Y5YAM=\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST order_reference=\"OWD19\" order_source=\"Intranet\" is_gift=\"NO\" hold_for_release=\"YES\" backorder_rule=\"PARTIALSHIP\"><SHIPPING_INFO last_name=\"\" first_name=\"Dave Sweet\" address_one=\"P.O. Box 4286\" city=\"Malibu\" state=\"California\" zip=\"90264\" country=\"US\" phone=\"310-317-1613\" email=\"malibudave@yahoo.com\" ship_type=\"OWD.1ST.LETTER\" ship_cost=\"1.1100\"></SHIPPING_INFO><BILLING_INFO last_name=\"\" first_name=\"Dave Sweet\" address_one=\"P.O. Box 4286\" city=\"Malibu\" state=\"California\" zip=\"90264\" country=\"US\" phone=\"310-317-1613\" email=\"malibudave@yahoo.com\" payment_type=\"authorizenet\" order_discount=\"0\" tax_pct=\"0.50\" paid=\"yes\"></BILLING_INFO><LINE_ITEM part_reference=\"C1049EN1\" description=\"CD - Prem Rawat at Australia's Parliament House \" requested=\"1\" cost=\"6.0000\"></LINE_ITEM></OWD_ORDER_CREATE_REQUEST><OWD_ORDER_CREATE_REQUEST order_reference=\"OWD20\" order_source=\"Intranet\" is_gift=\"NO\" hold_for_release=\"YES\" backorder_rule=\"PARTIALSHIP\"><SHIPPING_INFO last_name=\"\" first_name=\"Dave Sweet\" address_one=\"P.O. Box 4286\" city=\"Malibu\" state=\"California\" zip=\"90264\" country=\"US\" phone=\"310-317-1613\" email=\"malibudave@yahoo.com\" ship_type=\"OWD.1ST.LETTER\" ship_cost=\"1.3500\"></SHIPPING_INFO><BILLING_INFO last_name=\"\" first_name=\"Dave Sweet\" address_one=\"P.O. Box 4286\" city=\"Malibu\" state=\"California\" zip=\"90264\" country=\"US\" phone=\"310-317-1613\" email=\"malibudave@yahoo.com\" payment_type=\"GV/DC\" order_discount=\"0\" tax_pct=\"0.66\" paid=\"yes\"></BILLING_INFO><LINE_ITEM part_reference=\"D1053EN1\" description=\"DVD - More Than Words - Maharaji in Melbourne \" requested=\"1\" cost=\"8.0000\"></LINE_ITEM></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";


//String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.3\" client_authorization=\"3bzi4TMn0a1u0wbJjDI1dgM=\" client_id=\"160\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST backorder_rule=\"BACKORDER\" is_gift=\"NO\" order_reference=\"649090xxx\" order_source=\"Internet\"><SHIPPING_INFO address_one=\"2 NEW STREET\" address_two=\"\" city=\"SANTA RITA\" company_name=\"\" country=\"US\" email=\"giddyjenn@yahoo.com\" first_name=\"Jennetta\" last_name=\"Southard\" phone=\"808-454-2544\" ship_cost=\"7.98\" ship_type=\"BWY\" state=\"GU\" zip=\"96915\"><BEST_WAY><CARRIER><![CDATA[OWD.1ST.PRIORITY]]></CARRIER><CARRIER><![CDATA[OWD_USPS_I_PRIORITY]]></CARRIER><CARRIER><![CDATA[OWD_USPS_I_FIRST]]></CARRIER><CARRIER><![CDATA[UPS.STDCAMX]]></CARRIER><CARRIER><![CDATA[OWD.1ST.LETTER]]></CARRIER></BEST_WAY></SHIPPING_INFO><BILLING_INFO address_one=\"2 NEW STREET\" address_two=\"\" city=\"SANTA RITA\" company_name=\"\" country=\"US\" email=\"giddyjenn@yahoo.com\" first_name=\"Jennetta\" last_name=\"Southard\" order_discount=\"0.00\" paid=\"YES\" payment_type=\"CLIENT\" phone=\"808-454-2544\" state=\"GU\" zip=\"96915\"></BILLING_INFO><LINE_ITEM cost=\"0.00\" customs_desc=\"Bill O&#39;Reilly&#39;s Autograph on Bookplate\" declared_value=\"0.00\" description=\"Bill O&#39;Reilly&#39;s Autograph on Bookplate\" part_reference=\"10023\" requested=\"1\"></LINE_ITEM><LINE_ITEM cost=\"19.95\" customs_desc=\"Don&#39;t Be a Pinhead T-Shirt\" declared_value=\"19.95\" description=\"Don&#39;t Be a Pinhead T-Shirt\" part_reference=\"22886\" requested=\"1\"></LINE_ITEM><LINE_ITEM cost=\"14.95\" customs_desc=\"Don&#39;t Be a Pinhead Women&#39;s T-Shirt\" declared_value=\"14.95\" description=\"Don&#39;t Be a Pinhead Women&#39;s T-Shirt\" part_reference=\"23317\" requested=\"1\"></LINE_ITEM><LINE_ITEM cost=\"31.00\" customs_desc=\"A Bold Fresh Piece of Humanity - Autographed\" declared_value=\"31.00\" description=\"A Bold Fresh Piece of Humanity - Autographed\" part_reference=\"23652\" requested=\"1\"></LINE_ITEM><LINE_ITEM cost=\"0.00\" customs_desc=\"We Say Merry Christmas Bumper Sticker - free with purchase over $19.95\" declared_value=\"0.00\" description=\"We Say Merry Christmas Bumper Sticker - free with purchase over $19.95\" part_reference=\"24329\" requested=\"1\"></LINE_ITEM></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";
String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<OWD_API_REQUEST api_version=\"1.6\" client_id=\"382\" client_authorization=\"zU1leeS1VW2hpOwWX/K2TQM=\" testing=\"TRUE\"><OWD_ORDER_CREATE_REQUEST order_reference=\"19744xx\" order_source=\"Web\" backorder_rule=\"BACKORDER\"><BILLING_INFO last_name=\"Jones\" first_name=\"Paul\" company_name=\"\" address_one=\"Bourne house\" address_two=\"\" city=\"oswestry\" state=\"Shropshire\" zip=\"SY109NR\" country=\"GB\" phone=\"\" email=\"uffern@tiscali.co.uk\" tax_pct=\"0\" paid=\"YES\" paid_date=\"2010-10-01 15:55:54\" payment_type=\"CLIENT\"/><SHIPPING_INFO last_name=\"Jones\" first_name=\"Paul\" company_name=\"\" address_one=\"Bourne house\" address_two=\"\" city=\"oswestry\" state=\"Shropshire\" zip=\"SY109NR\" country=\"GB\" ship_type=\"BWY\" ship_cost=\"11.9900\" terms=\"SHIPPER\"><BEST_WAY><CARRIER><![CDATA[OWD.1ST.PRIORITY]]></CARRIER><CARRIER><![CDATA[OWD_USPS_I_FIRST]]></CARRIER><CARRIER><![CDATA[OWD_USPS_I_PRIORITY]]></CARRIER><CARRIER><![CDATA[OWD.4TH.BOOK]]></CARRIER></BEST_WAY></SHIPPING_INFO><LINE_ITEM part_reference=\"OWT1\" description=\"The One World Tour (motorcycle DVD) - NEW!!\" requested=\"1\" cost=\"22.9500\" declared_value=\"22.9500\" customs_desc=\"\" backordered=\"\" line_number=\"1\"/></OWD_ORDER_CREATE_REQUEST></OWD_API_REQUEST>";

    URL testUrl = new URL("http://service.owd.com:8080/apiy/api.jsp");
//URL testUrl = new URL("http://abweb1.internal.owd.com/Progistics/XML_Processor/Server/XMLProcDLL.asp");
            HttpURLConnection testConnection = (HttpURLConnection) testUrl.openConnection();
          //  log.debug("Name1:"+ CountryNames.getCountryName("VI"));
          //  log.debug("Country:"+ ChoiceListManager.getChoiceListManager().getList("Country ID").getRefForValue("VIRGIN ISLANDS, U.S."));
            log.debug("xmlString = " + xmlString);
            testConnection.setRequestProperty("Content-Type","content/xml");
            testConnection.setRequestMethod("POST");
            testConnection.setDoOutput(true);
            PrintWriter p_out = new PrintWriter(testConnection.getOutputStream());
            //xmlString="";
            //log.debug(URLEncoder.encode("&"));
            p_out.println(xmlString);
            p_out.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));


            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                log.debug(URLDecoder.decode(inputLine,"UTF-8"));
            }


            in.close();





           /*
 */

log.debug("xmlString = " + xmlString);
	log.debug("in API post2");
		APIResponse responder = new APIResponse(1.6);
		log.debug("in API post2");
		org.w3c.dom.Document doc = null;

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(false);
			factory.setValidating(false);
			javax.xml.parsers.DocumentBuilder  builder = factory.newDocumentBuilder();
			log.debug("in API post2");
			doc = builder.parse(new java.io.ByteArrayInputStream(xmlString.getBytes()));
			log.debug("in API post2");
			responder = com.owd.web.api.APIRequest.handle(doc);
			log.debug("in API post2 response");
			log.debug(responder.getNode());





log.debug("testOWD.jsp finished!");





        } catch (Exception e) {

         //   log.debug("Error in testOWD.jsp!");

         //   log.debug(e.getMessage());
            e.printStackTrace();

        }


    }

}

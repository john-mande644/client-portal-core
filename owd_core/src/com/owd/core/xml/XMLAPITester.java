package com.owd.core.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 10, 2005
 * Time: 10:58:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class XMLAPITester {
private final static Logger log =  LogManager.getLogger();


    static public void main(String[] args) {
        try
        {
            testIntacctAPI();
        }              catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static void testIntacctAPI(){


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



                //log.debug(":::API REQUEST BEGIN:::");

                //log.debug(payload[i]);

                //log.debug(":::API REQUEST END:::");



                    server.contentType = "text/xml";

                    BufferedReader response = server.getResource();

                    int line = 0;

                    line = (int)response.read();

                    while (line != -1)

                    {

                        //Uncomment the next block if displaying result in HTML



                        if((char)line == '>')

                            sb.append("&gt;");

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

                    //log.debug(":::API RESPONSE BEGIN:::");

                    //log.debug(sb.toString());

                    //log.debug(":::API RESPONSE END:::");

                }

            }
            */



        try {

            //log.debug("Inside testOWD.jsp");





//String xmlString="<?xml version=\"1.0\"?><!DOCTYPE ADDRESSVALIDATEREQUEST SYSTEM \"http:\\\\localhost\\Progistics\\XML_Processor\\DTD\\AddressValidateRequest.dtd\"><ADDRESSVALIDATEREQUEST><LOGIN><LOGINID>test</LOGINID><PASSWORD>test</PASSWORD></LOGIN><ADDRESS_DATA><ADDRESSREFERENCE>CONS2</ADDRESSREFERENCE><COMPANY>ConnectShip, Inc"
//+"</COMPANY><ADDRESS1>8282 South Memorial</ADDRESS1><ADDRESS2>Suite 400</ADDRESS2><CITY>Tulsa</CITY><STATEPROVINCE>OK</STATEPROVINCE><POSTALCODE>74133</POSTALCODE><PHONE>918 499-2800 X123</PHONE><COUNTRYSYMBOL>UNITED_STATES</COUNTRYSYMBOL></ADDRESS_DATA></ADDRESSVALIDATEREQUEST>";


//"<?xml version=\"1.0\" encoding=\"UTF-8\"?><OWD_API_REQUEST api_version=\"1.6\" client_id=\"160\" client_authorization=\"O11TFC7ZkoFEfAcfXUR4KQM=\" testing=\"TRUE\"><OWD_ORDER_STATUS_REQUEST clientOrderId=\"160854\" summary=\"TRUE\"/></OWD_API_REQUEST>"


            String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><control><senderid>owd</senderid><password>THouSwLa</password><controlid>get_list</controlid><uniqueid>false</uniqueid><dtdversion>2.1</dtdversion></control><operation><authentication><login><userid>sbuskirk</userid><companyid>OWD</companyid><password>skalarno</password></login></authentication><content>" +
                    "" +
                    "<function controlid=\"1\"><get_list object=\"aradjustment\"><filter><logical logical_operator=\"and\"><expression><field>customerid</field><operator>=</operator><value>ABC_1</value></expression><expression><field>datecreated</field><operator>&gt;=</operator><value>01/01/2005</value></expression></logical></filter>\n" +
                    "<sorts><sortfield order=\"desc\">datecreated</sortfield></sorts>" +

                   // "<fields><field>datecreated</field><field>adjustmentno</field><field>totalamount</field><field>totalpaid</field><field>description</field></fields>" +
                    "</get_list></function>" +
                    "" +
                    "<function controlid=\"2\"><get_list object=\"invoice\">" +
                    "<filter><logical logical_operator=\"and\"><expression><field>customerid</field><operator>=</operator><value>ABC_1</value></expression>" +
                    "<expression><field>datecreated</field><operator>&gt;=</operator><value>01/01/2005</value></expression></logical></filter>" +
                    "<sorts><sortfield order=\"desc\">datecreated</sortfield></sorts>" +

                    "<fields><field>datecreated</field><field>invoiceno</field><field>totalamount</field><field>totalpaid</field><field>description</field></fields>" +
                    "</get_list></function>" +

                    "<function controlid=\"3\"><get_list object=\"arpayment\">" +
                    "<filter><logical logical_operator=\"and\"><expression><field>customerid</field><operator>=</operator><value>ABC_1</value></expression><expression><field>datereceived</field><operator>&gt;=</operator><value>01/01/2005</value></expression>" +

                    "<expression><field>datereceived</field><operator>&lt;=</operator><value>12/31/2005</value></expression></logical></filter>" +
                    "<sorts><sortfield order=\"desc\">datereceived</sortfield></sorts>" +

                  //  "<fields><field>datereceived</field><field>refid</field><field>paymentamount</field><field>paymentmethod</field></fields>" +
                    "</get_list></function></content></operation></request>";

                   /*

                           "  <content> " +
                    "   <function controlid=\"f4\"> " +
                       "    <get_list object=\"arpayment\" >" +
                    "<filter><expression><field>customerid</field><operator>=</operator><value>ABC_1</value></expression></filter> " +
                    "" +

                    "    </get_list> " +

                    "   </function> " +
                      "   <function controlid=\"f4\"> " +
                       "    <get_list object=\"arpayment\" >" +
                    "<filter><expression><field>customerid</field><operator>=</operator><value>ABC_1</value></expression></filter> " +
                    "" +

                    "    </get_list> " +

                    "   </function> " +
                    "  </content> " +
            */
            /*

                              "    <get_accountbalances ><startdate><year>900</year><month>1</month><day>1</day></startdate><enddate><year>2006</year><month>1</month><day>1</day></enddate> " +
                    "     <fields> " +
                    "      <field>title</field> " +
                    "      <field>normalbalance</field> " +
                    "     </fields> " +
                    "    </get_accountbalances> " +

                            */
            URL testUrl = new URL("https://www.intacct.com/ia/xml/xmlgw.phtml");
            HttpURLConnection testConnection = (HttpURLConnection) testUrl.openConnection();

        //    //log.debug("xmlString = " + xmlString);
            testConnection.setRequestProperty("Content-Type","x-intacct-xml-request");
            testConnection.setRequestMethod("POST");
            testConnection.setDoOutput(true);
            PrintWriter p_out = new PrintWriter(testConnection.getOutputStream());
            //xmlString="";
            ////log.debug(URLEncoder.encode("&"));
            p_out.println(xmlString);
            p_out.close();


           // BufferedReader in = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));



            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(testConnection.getInputStream());
            doc.getDocumentElement().normalize();

      //      String str = XPathAPI.eval(doc, "/response/operation/result//data/*").toString();
     // //log.debug("=>" + str + "<=\n");

         /*   Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");
*/
      // Use the simple XPath API to select a nodeIterator.
      //log.debug("\nPrinting subtree under xpath =>/response/operation/result//data<=");
      NodeIterator nl = XPathAPI.selectNodeIterator(doc, "/response/operation/result//data/*");

      Node n;
      while ((n = nl.nextNode()) != null) {
        // Serialize the found nodes to System.out

        if(n.getNodeName().equals("invoice"))
        {


            //log.debug("Invoice:Number="+XPathAPI.eval(n, "key").toString()+":"+XPathAPI.eval(n, "invoiceno").toString());
            //log.debug("Invoice:Description="+XPathAPI.eval(n, "description").toString());
            //log.debug("Invoice:Paid="+XPathAPI.eval(n, "totalpaid").toString());
            //log.debug("Invoice:Total="+XPathAPI.eval(n, "totalamount").toString());
            //log.debug("Invoice:Created="+XPathAPI.eval(n, "datecreated/month").toString()+"/"+XPathAPI.eval(n, "datecreated/day").toString()+"/"+XPathAPI.eval(n, "datecreated/year").toString());

/*
        serializer.transform(
            new DOMSource(n),
            new StreamResult(System.out));*/
        }else if(n.getNodeName().equals("aradjustment"))
        {
            //log.debug("Adjust:Number="+XPathAPI.eval(n, "key").toString()+":"+XPathAPI.eval(n, "adjustmentno").toString());
                       //log.debug("Adjust:Description="+XPathAPI.eval(n, "description").toString());
                       //log.debug("Adjust:Paid="+XPathAPI.eval(n, "totalpaid").toString());
                       //log.debug("Adjust:Total="+XPathAPI.eval(n, "totalamount").toString());
                       //log.debug("Adjust:Created="+XPathAPI.eval(n, "datecreated/month").toString()+"/"+XPathAPI.eval(n, "datecreated/day").toString()+"/"+XPathAPI.eval(n, "datecreated/year").toString());


        }else if(n.getNodeName().equals("arpayment"))
        {



            //log.debug("Payment:Number="+XPathAPI.eval(n, "key").toString()+":"+XPathAPI.eval(n, "adjustmentno").toString());
                                   //log.debug("Payment:Description="+XPathAPI.eval(n, "batchtitle").toString());
                                   //log.debug("Payment:Applied="+XPathAPI.eval(n, "paymentapplied").toString());
                                   //log.debug("Payment:Via="+XPathAPI.eval(n, "paymentmethod").toString());
                                   //log.debug("Payment:Total="+XPathAPI.eval(n, "paymentamount").toString());
                                   //log.debug("Payment:Received="+XPathAPI.eval(n, "datereceived/month").toString()+"/"+XPathAPI.eval(n, "datereceived/day").toString()+"/"+XPathAPI.eval(n, "datereceived/year").toString());


        }
      }


         //   String inputLine;


           // while ((inputLine = in.readLine()) != null) {
           //     //log.debug(inputLine);
           // }


           // in.close();



/*

//log.debug("xmlString = " + xmlString);
	//log.debug("in API post2");
		APIResponse responder = new APIResponse(1.6);
		//log.debug("in API post2");
		org.w3c.dom.Document doc = null;

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(false);
			factory.setValidating(false);
			javax.xml.parsers.DocumentBuilder  builder = factory.newDocumentBuilder();
			//log.debug("in API post2");
			doc = builder.parse(new java.io.ByteArrayInputStream(xmlString.getBytes()));
			//log.debug("in API post2");
			responder = com.owd.web.api.APIRequest.handle(doc);
			//log.debug("in API post2 response");
			//log.debug(responder.getNode());
*/
//log.debug("testOWD.jsp finished!");




        } catch (Exception e) {

            //log.debug("Error in testOWD.jsp!");

            //log.debug(e.getMessage());
            e.printStackTrace();

        }


    }
}

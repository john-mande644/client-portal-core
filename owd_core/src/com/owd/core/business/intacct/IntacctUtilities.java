package com.owd.core.business.intacct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 31, 2006
 * Time: 9:56:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntacctUtilities {
private final static Logger log =  LogManager.getLogger();

    public static final String kMainAccountID = "MAINCHK";
    public static final String kShippingAccountID = "SHIPCHK";

       public static String getCurrentPaymentBatchID(String accountID) {

              String currentID = null;
           int maxKey = 0;
        try {
            List<Map> batches = getOpenARPaymentBatches(accountID);

            for(Map batch:batches)
            {
                log.debug(batch);
                if("active".equals(batch.get("status")))
                {
                    if(batch.get("batchtitle").toString().indexOf(":")==-1 && batch.get("batchtitle").toString().startsWith("20"))
                    {
                        int key = Integer.parseInt(batch.get("key").toString());
                        if(key>maxKey)
                        {
                            maxKey = key;
                            currentID = ""+key;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

           return currentID;

    }

      public static List getOpenARPaymentBatches(String accountID)
    {
        List list = new ArrayList();
              try {

                 /* Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");
*/
            IntacctRequest req = new IntacctRequest();


            req.addFunction(IntacctUtilities.getOpenARPaymentBatchRequestXML(accountID));


            IntacctResponse resp = IntacctUtilities.intacctAPI(req);
          for(int i=0;i<resp.getResponseData().size();i++) {

        Node n = (Node)resp.getResponseData().get(i);


           Map map = new HashMap();

              map.put("key",XPathAPI.eval(n, "key").toString());
             map.put("datecreated",XPathAPI.eval(n, "datecreated/year").toString()+"-"+XPathAPI.eval(n, "datecreated/month").toString()+"-"+XPathAPI.eval(n, "datecreated/day").toString()+" 00:00:00.0");
              map.put("batchtitle",XPathAPI.eval(n, "batchtitle").toString());
               map.put("status",XPathAPI.eval(n, "status").toString());
              list.add(map);




          }
        } catch (Exception ex) {




        }

        return list;



    }

      public static void main(String[] args)
      {
          try
          {
             /* DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


              List xmlstr = ShippingAccountUtilities.getNewARLedgerData("AV",false,0);
              log.debug(""+xmlstr);
              log.debug("intacct_id\ttotal_amount");
              for(Map map:(List<Map>)xmlstr)
              {
                  log.debug(map.get("intacct_id")+"\t"+map.get("total_amount"));
              }*/
              



            /*  String xmlstr= getNewARActivityPaymentsRequestXML("AV",0);
              //"<get_list object=\"invoice\"><filter><logical logical_operator=\"and\">" +
              //      "<expression><field>customerid</field><operator>=</operator><value>INSTACHARGE_1</value></expression>" +
              //      "<expression><field>key</field><operator>&gt;</operator><value>0</value></expression>" +
               //     "</logical></filter></get_list>";

              IntacctRequest req = new IntacctRequest();
              req.addFunction(xmlstr);
              IntacctResponse resp = intacctAPI(req);
              Document doc = resp.getRawResponse();

                Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");

                serializer.transform(
            new DOMSource(doc),
            new StreamResult(System.out));*/

          }   catch(Exception ex)
          {
              ex.printStackTrace();
          }
      }
            /*

              "    <get_accountbalances ><startdate><year>900</year><month>1</month><day>1</day></startdate><enddate><year>2006</year><month>1</month><day>1</day></enddate> " +
    "     <fields> " +
    "      <field>title</field> " +
    "      <field>normalbalance</field> " +
    "     </fields> " +
    "    </get_accountbalances> " +

            */
     public static String getARCustomerDataXML(String clientIntacctID)
    {
            return  "<get_list object=\"customer\">" +
                    "<filter><logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical></filter>" +
                       "     <fields> " +
    "      <field>customerid</field> " +
    "      <field>name</field> " +
    "      <field>creditlimit</field> " +
    "      <field>totaldue</field> " +        
    "     </fields> " +
                    "</get_list>";
    }
    public static String getARCustomerDataXML()
    {
            return  "<get_list object=\"customer\">" +
                       "     <fields> " +
    "      <field>customerid</field> " +
    "      <field>name</field> " +
    "      <field>creditlimit</field> " +
    "      <field>totaldue</field> " +
    "      <field>customerid</field> " +
    "     </fields> " +
                    "</get_list>";
    }

     public static String getARAccountDataXML()
    {
            return  "<get_list object=\"araccountlabel\">"+
                    "<filter><expression><field>status</field><operator>=</operator><value>active</value></expression></filter>" +
                    "</get_list>";
    }

     public static String getDeleteInvoiceXML(String intacctKey)
    {
            return  "<delete_invoice key=\""+intacctKey+"\"/>";
    }

     public static String getNewARShippingPaymentsRequestXML(String clientIntacctID, int maxExistingIntacctID)
    {
        return getNewARGenericDataRequestXML(clientIntacctID+"_1",maxExistingIntacctID,"arpayment");

    }

     public static String getNewARShippingAdjustmentsRequestXML(String clientIntacctID, int maxExistingIntacctID)
    {
        return getNewARGenericDataRequestXML(clientIntacctID+"_1",maxExistingIntacctID,"aradjustment");

    }

      public static String getNewARShippingInvoicesRequestXML(String clientIntacctID, int maxExistingIntacctID)
    {
        return getNewARGenericDataRequestXML(clientIntacctID+"_1",maxExistingIntacctID,"invoice");

    }

       public static String getNewARActivityPaymentsRequestXML(String clientIntacctID, int maxExistingIntacctID)
    {
        return getNewARGenericDataRequestXML(clientIntacctID,maxExistingIntacctID,"arpayment");

    }

     public static String getNewARActivityAdjustmentsRequestXML(String clientIntacctID, int maxExistingIntacctID)
    {
        return getNewARGenericDataRequestXML(clientIntacctID,maxExistingIntacctID,"aradjustment");

    }

      public static String getNewARActivityInvoicesRequestXML(String clientIntacctID, int maxExistingIntacctID)
    {
        return getNewARGenericDataRequestXML(clientIntacctID,maxExistingIntacctID,"invoice");

    }

    private static String getNewARGenericDataRequestXML(String clientIntacctID, int maxExistingIntacctID, String arDataType)
    {
        return "<get_list object=\""+arDataType+"\"><filter><logical logical_operator=\"and\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>key</field><operator>&gt;</operator><value>"+maxExistingIntacctID+"</value></expression>" +
                    "</logical></filter></get_list>";
    }

    public static String getOpenARPaymentBatchRequestXML(String accountID)
    {
        return "<get_list object=\"arpaymentbatch\"><filter><logical logical_operator=\"and\">" +
                    "<expression><field>open</field><operator>=</operator><value>true</value></expression>" +
                    "<expression><field>bankaccountid</field><operator>=</operator><value>"+accountID+"</value></expression>" +
                    "</logical></filter>" +
                 "     <fields> " +
    "      <field>key</field> " +
    "      <field>batchtitle</field> " +
    "      <field>datecreated</field> " +
    "      <field>status</field> " +
    "     </fields> " +
                "</get_list>";
    }

    private static String getEditedARGenericDataRequestXML(String clientIntacctID, String arDataType)
    {
        return "<get_list object=\""+arDataType+"\"><filter><logical logical_operator=\"and\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>whenmodified</field><operator>&gt;</operator><value>05/01/2008</value></expression>" +
                    "</logical></filter></get_list>";
    }

    public static String getARPaymentsRequestByDateXML(String clientIntacctID, Date startDate, Date endDate)
    {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        StringBuffer xml = new StringBuffer();

            xml.append(  "<get_list object=\"arpayment\">");
        if(startDate==null)
        {
             if(endDate==null)
             {
                   xml.append("<filter><logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical></filter>");
             }   else
             {
                   xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datereceived</field><operator>&lt;=</operator><value>"+df.format(endDate)+"</value></expression>" +
                    "</logical></filter>");
             }
        }   else if (endDate==null)
        {
               xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datereceived</field><operator>&gt;=</operator><value>"+df.format(startDate)+"</value></expression>" +
                    "</logical></filter>");
        }   else
        {
           xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datereceived</field><operator>&gt;=</operator><value>"+df.format(startDate)+"</value></expression>" +
                    "<expression><field>datereceived</field><operator>&lt;=</operator><value>"+df.format(endDate)+"</value></expression>" +
                    "</logical></filter>");
        }

                    xml.append("</get_list>");

        return xml.toString();

    }

    public static String getARAdjustmentsRequestByDateXML(String clientIntacctID, Date startDate, Date endDate)
    {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


        StringBuffer xml = new StringBuffer();

            xml.append(  "<get_list object=\"aradjustment\">");
        if(startDate==null)
        {
             if(endDate==null)
             {
                   xml.append("<filter><logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical></filter>");
             }   else
             {
                   xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datecreated</field><operator>&lt;=</operator><value>"+df.format(endDate)+"</value></expression>" +
                    "</logical></filter>");
             }
        }   else if (endDate==null)
        {
               xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datecreated</field><operator>&gt;=</operator><value>"+df.format(startDate)+"</value></expression>" +
                    "</logical></filter>");
        }   else
        {
           xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datecreated</field><operator>&gt;=</operator><value>"+df.format(startDate)+"</value></expression>" +
                    "<expression><field>datecreated</field><operator>&lt;=</operator><value>"+df.format(endDate)+"</value></expression>" +
                    "</logical></filter>");
        }

                    xml.append("</get_list>");

        return xml.toString();

    }

    public static String getARInvoicesRequestByDateXML(String clientIntacctID, Date startDate, Date endDate)
    {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            StringBuffer xml = new StringBuffer();

            xml.append(  "<get_list object=\"invoice\">");
        if(startDate==null)
        {
             if(endDate==null)
             {
                   xml.append("<filter><logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical></filter>");
             }   else
             {
                   xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datecreated</field><operator>&lt;=</operator><value>"+df.format(endDate)+"</value></expression>" +
                    "</logical></filter>");
             }
        }   else if (endDate==null)
        {
               xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datecreated</field><operator>&gt;=</operator><value>"+df.format(startDate)+"</value></expression>" +
                    "</logical></filter>");
        }   else
        {
           xml.append("<filter><logical logical_operator=\"and\">" +"<logical logical_operator=\"or\">" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"</value></expression>" +
                    "<expression><field>customerid</field><operator>=</operator><value>"+clientIntacctID+"_1</value></expression>" +
                    "</logical>"+
                    "<expression><field>datecreated</field><operator>&gt;=</operator><value>"+df.format(startDate)+"</value></expression>" +
                    "<expression><field>datecreated</field><operator>&lt;=</operator><value>"+df.format(endDate)+"</value></expression>" +
                    "</logical></filter>");
        }

                    xml.append("</get_list>");

        return xml.toString();

    }

     public static void deleteInvoice(String intacctKey)
     {
         try {

                      /* Transformer serializer =
               TransformerFactory.newInstance().newTransformer();
           serializer.setOutputProperty(
                 OutputKeys.OMIT_XML_DECLARATION, "yes");
     */
                 IntacctRequest req = new IntacctRequest();


                 req.addFunction(IntacctUtilities.getDeleteInvoiceXML(intacctKey));


                 IntacctResponse resp = IntacctUtilities.intacctAPI(req);
              if(resp.isOk())
              {
                  log.debug("Success deleting invoice key "+intacctKey);
              }   else
              {
                  log.debug("Error deleting invoice key "+intacctKey+": "+resp.getErrorDescription());
              }
             } catch (Exception ex) {


                 ex.printStackTrace();


             }



     }
     public static IntacctResponse intacctAPI(IntacctRequest ireq){

          IntacctResponse response = new IntacctResponse();
         response.ok=false;

        try {

               //     Iterator it = ireq.getFunctionMap().keySet().iterator();

            StringBuffer xmlPreamble = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><control><senderid>owd</senderid><password>THouSwLa</password>" +
                    "<controlid>get_list</controlid><uniqueid>"+(ireq.isUseControlID()?"true":"false")+"</uniqueid><dtdversion>2.1</dtdversion></control><operation><authentication><login><userid>ktorevell</userid><companyid>OWD</companyid><password>rowdy1</password></login></authentication><content>" +
                    "");


            for(int i=0;i<ireq.getFunctionList().size();i++)
            {

                xmlPreamble.append("<function controlid=\""+(ireq.isUseControlID()?ireq.getControlID():(""+i))+"\">"+ireq.getFunctionList().get(i)+"</function>");
            }

            xmlPreamble.append("</content></operation></request>");

           log.debug(xmlPreamble);

            URL testUrl = new URL("https://www.intacct.com/ia/xml/xmlgw.phtml");
            HttpURLConnection testConnection = (HttpURLConnection) testUrl.openConnection();

            testConnection.setRequestProperty("Content-Type","x-intacct-xml-request");
            testConnection.setRequestMethod("POST");
            testConnection.setDoOutput(true);
           
            PrintWriter p_out = new PrintWriter(testConnection.getOutputStream());
            p_out.println(xmlPreamble.toString());
            p_out.close();


            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


            InputStream is = testConnection.getInputStream();

            log.debug("got intacct API response");
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();




/*


            Transformer serializer =
          TransformerFactory.newInstance().newTransformer();
      serializer.setOutputProperty(
            OutputKeys.OMIT_XML_DECLARATION, "yes");


              serializer.transform(
            new DOMSource(doc),
            new StreamResult(System.out));


*/



            response.setRawResponse(doc);


            //check for success

       response.setOk(true);
      // Use the simple XPath API to select a nodeIterator.

            NodeIterator nlstatus = XPathAPI.selectNodeIterator(doc, "/response/operation/result/status");
             Node ns;
      while ((ns = nlstatus.nextNode()) != null) {
        // Serialize the found nodes to System.out


          if(!("success".equals((XPathAPI.eval(ns,".").toString()).trim())))
          {
              //log.debug("Bad response?"+XPathAPI.eval(ns,"."));
              response.setOk(false);
              response.setErrorDescription((XPathAPI.eval(ns,"/response/operation/result/errormessage/error/description").toString())
              +(XPathAPI.eval(ns,"/response/operation/result/errormessage/error/description2").toString()));
          }

      }

      NodeIterator nl = XPathAPI.selectNodeIterator(doc, "/response/operation/result//data/*");

      Node n;
      while ((n = nl.nextNode()) != null) {
        // Serialize the found nodes to System.out
        response.getResponseData().add(n);

      }



        } catch (Exception e) {

            //log.debug("Error in testOWD.jsp!");

            //log.debug(e.getMessage());
            e.printStackTrace();

        }

      return response;
    }
}

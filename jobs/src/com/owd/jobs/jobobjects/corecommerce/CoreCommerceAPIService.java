package com.owd.jobs.jobobjects.corecommerce;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.WebResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Apr 30, 2010
 * Time: 4:53:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoreCommerceAPIService
{
private final static Logger log =  LogManager.getLogger();
   public static final String CORE_ORDER_STATUS_APPROVED = "APPROVED";
    
    public static List getOrderList(String login, String password, String store, String key, String orderType, Date startDate, Date endDate) throws Exception
    {
        WebResource server = new WebResource("https://store.brazillivecoral.com/admin/_callback.php", WebResource.kPOSTMethod);
        
        String xmlRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<Request version=\"1.0\">" +
                "<Authentication>" +
                "<Username><![CDATA["+login+"]]></Username>" +
                "<Password><![CDATA["+password+"]]></Password>" +
                "<StoreName><![CDATA["+store+"]]></StoreName>" +
                "<XmlKey><![CDATA["+key+"]]></XmlKey>" +
                "</Authentication>" +
                "<Action>ACTION_TYPE_ORDER_LIST</Action>" +
                "<Limit>100</Limit>" +
                "<Skip>0</Skip>" +
               "<SearchCriteria type=\"Exact\">" +
                "<DateRanges>" +
                "<DateRange>" +
                "<Day>27</Day>" +
                "<Month>05</Month>" +
                "<Year>2010</Year>" +
                "</DateRange>" +
                "</DateRanges>" +
                "<Statuses>" +
                "<Status>Approved</Status>" +
                "</Statuses>" +
                "</SearchCriteria>" +
                "</Request>";

        URL testUrl = new URL("https://store.brazillivecoral.com/admin/_callback.php");
//URL testUrl = new URL("http://abweb1.internal.owd.com/Progistics/XML_Processor/Server/XMLProcDLL.asp");
                   HttpsURLConnection testConnection = (HttpsURLConnection) testUrl.openConnection();
                 //  log.debug("Name1:"+ CountryNames.getCountryName("VI"));
                 //  log.debug("Country:"+ ChoiceListManager.getChoiceListManager().getList("Country ID").getRefForValue("VIRGIN ISLANDS, U.S."));
      //  log.debug(""+xmlRequest);
                   testConnection.setRequestProperty("Content-Type","text/xml;charset=UTF-8");
                   testConnection.setRequestMethod("POST");
                   testConnection.setDoOutput(true);
                   PrintWriter p_out = new PrintWriter(testConnection.getOutputStream());
                   //xmlString="";
                   //log.debug(URLEncoder.encode("&"));
                   p_out.println(xmlRequest);
                   p_out.close();

        testConnection.connect();


                   BufferedReader in = null;
           try{
        in =new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
           }catch(IOException ex)
           {
               log.debug("ERROR: "+ex.getMessage());
               Map headers = testConnection.getHeaderFields();
               for(String akey:((Map<String,String>)headers).keySet())
               {
                   log.debug(akey+":"+headers.get(akey));
               }
               in =new BufferedReader(new InputStreamReader(testConnection.getErrorStream()));

           }

        System.out.println ( "Reading stream" ) ;
        String line="";
        String resultStr="";
        char testchar = new Character('<').charValue();

/*
        int i=0;
        int c=0;
        while((c = in.read()) != -1)
        {
            if(c == '<')
            {
               break; 
            }   else
            {
                in.mark(i++);
            }
        }
        in.reset();
*/

     /*   do  {

            try  {
          line =  ( String )  in.readLine (  ) ;
             }  catch  ( IOException e )   {
           System.out.println ( "IO Exception on Buffered Read" ) ;
             } ;
            resultStr += line + "\r\n"; 
          }  while  ( line != null ) ;

         System.out.println ("result:"+resultStr ) ;

        */

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

        log.debug(""+xmlRequest);
        Document doc = domBuilder.parse(new InputSource(in));
          Transformer serializer =
                    TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(
                    OutputKeys.OMIT_XML_DECLARATION, "yes");
            serializer.setOutputProperty(
                    OutputKeys.INDENT, "yes");


            serializer.transform(
                    new DOMSource(doc),
                    new StreamResult(System.out));
       return null;
    }

    public static void main(String[] args) throws Exception
    {
        // mrhoades: changed per cae 1039640
        // List orderList = getOrderList("oneworld","1979159a","algaecalinc893","1979159a","APPROVED",null,null);

        List orderList = getOrderList("oneworld","1979159a","brazilliveco154","1979159a","APPROVED",null,null);
    }
}

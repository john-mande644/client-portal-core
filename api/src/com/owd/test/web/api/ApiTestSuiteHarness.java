package com.owd.test.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.web.api.APIResponse;
import org.apache.xpath.XPathAPI;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/28/11
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApiTestSuiteHarness extends TestCase {
private final static Logger log =  LogManager.getLogger();


    public static boolean testLive = true;


    public ApiTestSuiteHarness(String test) {
        super(test);
    }



public static final void prettyPrint(Node xml, OutputStream out)
    throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
    Transformer tf = TransformerFactory.newInstance().newTransformer();
   // tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    tf.setOutputProperty(OutputKeys.INDENT, "yes");
    tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

    tf.transform(new DOMSource(xml), new StreamResult(out));
}

    public static final String printNodeToString(Node xml) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException,IOException
    {
        Transformer t = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
t.transform(new DOMSource(xml), new StreamResult(writer));
writer.close();
String xmls = writer.toString();
        //   t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        return xmls;
    }
      static Document getInternalAPIResponse(String apiRequest) throws Exception
    {
        log.debug(""+apiRequest);
        APIResponse responder = new APIResponse(1.6);
            log.debug("in API post2");
            org.w3c.dom.Document doc = null;

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(false);
                factory.setValidating(false);
                javax.xml.parsers.DocumentBuilder  builder = factory.newDocumentBuilder();
                log.debug("in API post2");
          log.debug("checking bytes");

                doc = builder.parse(new java.io.ByteArrayInputStream(apiRequest.getBytes("UTF-8")));
log.debug("hello");

        String xsl = "<xsl:stylesheet version=\"1.0\"\n" +
                         "   xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                         "\n" +
                         "<xsl:output method=\"xml\" omit-xml-declaration=\"no\"/>\n" +
                         "\n" +
                         "  <xsl:strip-space elements=\"*\"/>\n" +
                         "\n" +
                         "  <xsl:template match=\"@*|node()\">\n" +
                         "   <xsl:copy>\n" +
                         "    <xsl:apply-templates select=\"@*|node()\"/>\n" +
                         "   </xsl:copy>\n" +
                         "  </xsl:template>\n" +
                         "\n" +
                         "</xsl:stylesheet>";
                log.debug("TRANSFORMING");

                DOMSource source = new DOMSource(doc);
        log.debug("DOMSOURCED");
                DOMResult result = new DOMResult();
        log.debug("DOMRESULT");

        log.debug("PROCESS");
                process(source,new StreamSource(OWDUtilities.parseStringToIS(xsl, "UTF-8")),result);
                log.debug("PROCESSED");

                log.debug("in API post2");
                responder = com.owd.web.api.APIRequest.handle(doc);//(Document) (result.getNode()));
                log.debug("in API post2 response");
                log.debug(responder.getNode());

        Document doc2 = factory.newDocumentBuilder().parse(new ByteArrayInputStream(responder.getNode().getBytes("UTF-8")));
        doc2.getDocumentElement().normalize();


        return doc2;

    }

    static TransformerFactory factory = TransformerFactory.newInstance();


    static public void process(Source xml, Source xsl, Result result)
                  throws TransformerException {
      try {
        Templates template = factory.newTemplates(xsl);
        Transformer transformer = template.newTransformer();
        transformer.transform(xml, result);
      } catch(TransformerConfigurationException tce) {
          throw new TransformerException(
                      tce.getMessageAndLocation());
      } catch (TransformerException te) {
        throw new TransformerException(
                    te.getMessageAndLocation());
      }
    }

     static  public void verifyAPIResponse(Document response) throws TransformerException {
        String xmlBack = "";
         try{
         xmlBack = ""+XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("error_response").getNodeValue();
         }catch (Exception ex)
         {
         }
        Assert.assertTrue("Server returned error "+xmlBack,
                XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("results").getNodeValue().equals("SUCCESS"));
    }
     static Document getAPIResponse(String apiRequest) throws Exception {

        if(testLive)
        {
           return getLiveAPIResponse(apiRequest);
        }   else
        {
            try
            {
                return getTestAPIResponse(apiRequest);
            }   catch(Exception ex)
            {
                log.debug(ex.getMessage());
               if(ex.getMessage().contains("InputStream cannot be null")
                   )
               {
                   return getInternalAPIResponse(apiRequest);
               }   else
               {
                   throw ex;
               }
            }
        }

    }


    static Document getLocalhostAPIResponse(String apiRequest) throws Exception {
           return getServerAPIResponse(apiRequest,"http://localhost:8080/api/api.jsp");

       }

     static Document getLiveAPIResponse(String apiRequest) throws Exception {
           return getServerAPIResponse(apiRequest,"http://172.16.1.119:8080/api/api.jsp");
        // return getServerAPIResponse(apiRequest,"http://172.16.1.120:8080/api/api.jsp");
       }

    static Document getTestAPIResponse(String apiRequest) throws Exception {
      //  return getServerAPIResponse(apiRequest,"http://172.16.1.120:8081/api/api.jsp");
        return getServerAPIResponse(apiRequest,"http://172.16.1.113:8081/api/api.jsp");
    }

     static Document getServerAPIResponse(String apiRequest, String url) throws Exception {
           WebResource catalog = new WebResource(url, WebResource.kPOSTMethod);
           catalog.setContent(apiRequest);
           catalog.contentType = "text/xml";

           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           factory.setNamespaceAware(false);
           factory.setValidating(false);
           javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();



            WebResource server = new WebResource(url,WebResource.kPOSTMethod);
         server.setContent(apiRequest);
         server.contentType="text/xml";
         Document doc = builder.parse(server.getResourceAsInputStream(true));
         for(String s:server.returnHeaders.keySet()){
             log.debug(s = ":  " + server.returnHeaders.get(s));
         }
/*URL testUrl = new URL(url);
            HttpURLConnection testConnection = (HttpURLConnection) testUrl.openConnection();

            testConnection.setRequestProperty("Content-Type","text/xml");
            testConnection.setRequestMethod("POST");
            testConnection.setDoOutput(true);
            testConnection.getOutputStream().write(apiRequest.getBytes("UTF-8"));
            testConnection.getOutputStream().close();


           Document doc = builder.parse(testConnection.getInputStream());*/
           doc.getDocumentElement().normalize();


           return doc;

       }

          static Document getInternalAPIResponse(String apiRequest, String url) throws Exception {


           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           factory.setNamespaceAware(false);
           factory.setValidating(false);
           javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


           Document inDoc = builder.parse(new java.io.ByteArrayInputStream(apiRequest.getBytes("UTF-8")));
           APIResponse resp = com.owd.web.api.APIRequest.handle(inDoc);
           Document doc = builder.parse(new java.io.ByteArrayInputStream(resp.getXML().getBytes("UTF-8")));
           doc.getDocumentElement().normalize();



           return doc;

       }
}

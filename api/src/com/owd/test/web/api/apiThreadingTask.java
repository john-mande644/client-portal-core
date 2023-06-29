package com.owd.test.web.api;

import com.owd.core.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by danny on 9/7/2016.
 */
public class apiThreadingTask implements Runnable{
    private String xmlString;
    private final static Logger log =  LogManager.getLogger();
    private boolean testing = false;

    apiThreadingTask(String xml){
        xmlString = xml;
    }
    apiThreadingTask(String xml,boolean test){
        xmlString = xml;
        testing =test;
    }
    public void run(){
        try{
            Document response;
            log.debug("Sending the request: ");
            log.debug(xmlString);
            log.debug("*******************************************");
            if (testing){
                response = getTestAPIResponse(xmlString);
            }else{
                response = getLiveAPIResponse(xmlString);
            }


           // ApiTestSuiteHarness.prettyPrint(response.getDocumentElement(), System.out);
        }catch (Exception e){

        }


    }



    static Document getLiveAPIResponse(String apiRequest) throws Exception {
       // return getServerAPIResponse(apiRequest,"https://secure.owd.com/api/api.jsp");
        return getServerAPIResponse(apiRequest,"https://apibalancer.owd.com/api/api.jsp");
    }
    static Document getTestAPIResponse(String apiRequest) throws Exception {
        // return getServerAPIResponse(apiRequest,"https://secure.owd.com/test/api/api.jsp");
        //return getServerAPIResponse(apiRequest,"http://172.16.1.119:8081/api/api.jsp");
        return getServerAPIResponse(apiRequest,"https://apibalancer.owd.com/test/api/api.jsp");

    }

    static Document getServerAPIResponse(String apiRequest, String url) throws Exception {
        WebResource catalog = new WebResource(url, WebResource.kPOSTMethod);
        catalog.setContent(apiRequest);
        catalog.contentType = "text/xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


        log.debug("Create server");
        WebResource server = new WebResource(url,WebResource.kPOSTMethod);
        server.setContent(apiRequest);
        server.contentType="text/xml";
        log.debug("sending to server!!");
        Document doc = builder.parse(server.getResourceAsInputStream(true));
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
}

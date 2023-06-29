package com.owd.test.web.api;

import com.owd.core.WebResource;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Calendar;

/**
 * Created by danny on 9/7/2016.
 */
public class APIThreadingTestMulti extends Thread {


        private Thread t;
        private int max;
    private String xmlString;
    private String threadName;
    APIThreadingTestMulti(int howlong, String name, String xml){
        max = howlong;
        threadName = name;
        xmlString = xml;
        System.out.println("Creating " +  threadName );

    }
    public void run(){


        int i = 0;
        while(i<max) {
           /* String xmlStringSend = "<OWD_API_REQUEST api_version=\"2.2\" client_authorization=\"3AbkCyn3yBXRHOkwFW9lkwI=\" client_id=\"55\"" +
                    " testing=\"false\"><OWD_ORDER_HOLD_REQUEST clientOrderId=\"zzGrouptesting100"+i+"\"/></OWD_API_REQUEST>";
            System.out.println(xmlStringSend);*/

            Document response;
        try {
            long start = Calendar.getInstance().getTimeInMillis();
            response = getTestAPIResponse(xmlString);
            long secs = (Calendar.getInstance().getTimeInMillis() - start) / 1000;
           // log.debug(secs + " secs");
           // log.debug("" + response);
           // prettyPrint(response.getDocumentElement(), System.out);
            i++;
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }



    public void start ()
    {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this);
            t.start ();
            t.run();
        }
    }

    static Document getLiveAPIResponse(String apiRequest) throws Exception {
        return getServerAPIResponse(apiRequest,"https://secure.owd.com/api/api.jsp");

    }
    static Document getTestAPIResponse(String apiRequest) throws Exception {
        return getServerAPIResponse(apiRequest,"https://secure.owd.com/test/api/api.jsp");

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

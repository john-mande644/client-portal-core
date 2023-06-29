//revised on June 12, 2002
package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

public class OneWorldAPIServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    //edit to limit error reports to listed com.owd.api.client IDs. Client ID zero ("0") means to also send messages
    //for requests where the com.owd.api.client could not be identified.
    static public ThreadLocal webRequest = new ThreadLocal();

    protected Vector monitoredClients = new Vector();
    //   protected Vector debugClients = new Vector();

    { //static initializer for error reports IDs
        //   monitoredClients.addElement("117");
        //   debugClients.addElement("302");
         //  debugClients.addElement("109");
        // debugClients.addElement("260");
        //    monitoredClients.addElement("301");
        //   monitoredClients.addElement("127");
        //     monitoredClients.addElement("460");
      //  monitoredClients.addElement("146");
       // monitoredClients.addElement("529");
      //  monitoredClients.addElement("55");
      //  monitoredClients.addElement("0");

    }

    TransformerFactory factory = TransformerFactory.newInstance();


    public void init(ServletConfig config)
            throws ServletException {
        ////log.debug("in API init");
        super.init(config);

    }

    public void destroy() {
        ////log.debug("in API destroy");
        super.destroy();

    }

    //GET requests not supported
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {


        APIResponse apiResponse = new APIResponse(1.0);
        apiResponse.setError(APIResponse.kErrProcess, "HTTP GET requests not supported");
        resp.setContentType("text/xml");
        try {
            resp.setCharacterEncoding("UTF-8");
            resp.getOutputStream().write(apiResponse.getNode().getBytes("UTF-8"));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    //all requests should be POST
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        webRequest.set(req);
        try {
            req.setCharacterEncoding("UTF-8");

        } catch (Exception exxx) {
            exxx.printStackTrace();
        }
        //    log.debug("in API post2");
        APIResponse responder = new APIResponse(1.0);
        myServletInputStream myFilter = null;
        Document doc = null;
        String requestData="";

        try {
            // log.debug("in API post "+ req.getRemoteAddr()+":"+req.getHeader("x-forwarded-for"));
            if (req.getContentLength() > (1024*1024*32)  )
                throw new APIFormatException("Requests cannot exceed (1024*1024*32) characters in length");

            DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
            docfactory.setNamespaceAware(false);
            docfactory.setValidating(false);
            javax.xml.parsers.DocumentBuilder builder = docfactory.newDocumentBuilder();
            // log.debug("got API factory");
            String ctHeader = req.getHeader("content-type");
            if (ctHeader == null) ctHeader = req.getHeader("Content-Type");
            if (ctHeader == null) ctHeader = "text/xml";

            if (ctHeader.indexOf("x-www-form-urlencoded") >= 0 && (!(ctHeader.indexOf("text/xml") >= 0))) {
                // log.debug("running as url encoding POST data");
                StringBuffer headerInfo = new StringBuffer();
                Enumeration hNames = req.getHeaderNames();
                if (hNames != null) {
                    while (hNames.hasMoreElements()) {
                        String hName = (String) hNames.nextElement();
                        Enumeration hValues = req.getHeaders(hName);
                        if (hValues != null) {
                            while (hValues.hasMoreElements()) {
                                headerInfo.append(hName + ":" + ((String) hValues.nextElement()) + "\n");
                            }
                        }
                    }
                }
                Enumeration params = req.getParameterNames();
                if (params != null) {
                    while (params.hasMoreElements()) {
                        String pName = (String) params.nextElement();
                        String[] pValues = req.getParameterValues(pName);
                        if (pValues != null) {
                            for (int p = 0; p < pValues.length; p++) {
                                headerInfo.append("POST PARAMETER::" + pName + ":" + pValues[p] + ":\n");
                            }
                        }
                    }
                }
                log.debug("APIData:" + headerInfo);
                com.owd.core.Mailer.postMailMessage("API Test Data (POST FORM)", headerInfo + "\n\n", "owditadmin@owd.com", "api@owd.com");
                if (req.getParameterValues("xmlb64") != null) {
                    if (req.getParameterValues("xmlb64").length > 0) {


                        myFilter = new myServletInputStream(new ByteArrayInputStream(Base64.decodeBase64(req.getParameterValues("xmlb64")[0])));
                    }
                }
                if (myFilter == null) {
                    throw new APIContentException("No valid data to process was found. Requests posted with an application/x-www-form-urlencoded content-type must use a single POST parameter with the name xml for sending request XML data, or use the parameter xmlb64 to send base64-encoded XML data");
                }
            } else {
                myFilter = new myServletInputStream();
            }

            if (myFilter == null) {
                throw new APIContentException("No valid data to process was found.");
            }

            // doc = builder.parse(new InputSource(new InputStreamReader(myFilter,"UTF-8")));
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
            //  log.debug("TRANSFORMING");
            doc = builder.parse(new InputSource(new InputStreamReader(req.getInputStream(), "UTF-8")));

            DOMSource source = new DOMSource(doc);
            DOMResult result = new DOMResult();

             process(source,new StreamSource(OWDUtilities.parseStringToIS(xsl,"UTF-8")),result);

            Transformer t = factory.newTransformer();
            StringWriter writer = new StringWriter();
            Result sresult = new StreamResult(writer);
            t.transform(source, sresult);
            writer.close();
            requestData = writer.toString();
            myFilter.data = new StringBuffer(requestData);


            log.debug("******DATA:" + requestData);

            //test(doc);
            //  log.debug("API Request for ID " + getClientIDFromAPIRequest(doc));
            responder = APIRequest.handle((Document) (result.getNode()), req);
            if (myFilter != null) {

                StringBuffer headerInfo = new StringBuffer();

                Enumeration hNames = req.getHeaderNames();
                while (hNames.hasMoreElements()) {
                    String hName = (String) hNames.nextElement();
                    Enumeration hValues = req.getHeaders(hName);
                    while (hValues.hasMoreElements()) {
                        headerInfo.append(hName + ":" + ((String) hValues.nextElement()) + "\n");
                    }
                }

                String clientID = getClientIDFromAPIRequest(doc);
                if (monitoredClients.contains(clientID)) {

                    com.owd.core.Mailer.postMailMessage("API Data (" + clientID + ")", headerInfo + "\n\n" + requestData + "\n\nBytes:\n\n" + myFilter.dataCodes.toString() + "\n\nRESPONSE\n\n" + responder.getNode(), "owditadmin@owd.com", "donotreply@owd.com");


                }

            }

            if (responder.results.equals(APIResponse.kStatusCodeError)) {
                try {
                    String id = getClientIDFromAPIRequest(doc);
                    if ("146".equals(id)) //VH
                    {
                        Mailer.postMailMessage("OWD API Error Data from OrderMotion attempt", "REQUEST\n\n" + requestData+ "\n\nRESPONSE\n\n" + responder.getNode(), "apialerts@vitaminhealthbrands.com", "api-do-not-reply@owd.com");

                    }
                    if ("367".equals(id)) //Cass labs
                    {
                        Mailer.postMailMessage("OWD API Error Data from attempt", "REQUEST\n\n" + requestData + "\n\nRESPONSE\n\n" + responder.getNode(), "apialerts@casslabs.com", "api-do-not-reply@owd.com");

                    }
                } catch (Exception exvc) {
                }

                try {
                    String type = getRequestTypeFromAPIRequest(doc);
                    if (type.equals(OrderCreateRequest.kRootTag))
                    {
                       // Mailer.postMailMessage("OWD API Error Data from order creation", "REQUEST\n\n" + requestData + "\n\nRESPONSE\n\n" + responder.getNode(), "owditadmin@owd.com", "donotreply@owd.com");
                    }

                } catch (Exception exvc) {
                }

               //  Mailer.postMailMessage("API Error Data (" + getClientIDFromAPIRequest(doc) + ")", myFilter.data.toString() + "\n\nRESPONSE\n\n" + responder.getNode(), "owditadmin@owd.com", "checkit@owd.com");
            }

        } catch (Throwable ex) {
            ex.printStackTrace();

            responder.setError(APIResponse.kErrProcess, ex.getMessage());
            if (myFilter != null) {
                StringBuffer headerInfo = new StringBuffer();
                Enumeration hNames = req.getHeaderNames();
                while (hNames.hasMoreElements()) {
                    String hName = (String) hNames.nextElement();
                    Enumeration hValues = req.getHeaders(hName);
                    while (hValues.hasMoreElements()) {
                        headerInfo.append(hName + ":" + ((String) hValues.nextElement()) + "\n");
                    }
                }
                Enumeration params = req.getParameterNames();
                while (params.hasMoreElements()) {
                    String pName = (String) params.nextElement();
                    String[] pValues = req.getParameterValues(pName);
                    for (int p = 0; p < pValues.length; p++) {
                        headerInfo.append("POST PARAMETER::" + pName + ":" + pValues[p] + ":\n");
                    }
                }

                String clientID = getClientIDFromAPIRequest(doc);
                if (monitoredClients.contains(clientID)) {
                    com.owd.core.Mailer.postMailMessage(" API Error (" + getClientIDFromAPIRequest(doc) + ")", headerInfo + "\n\n" + requestData + "\n\nBytes:\n\n" + myFilter.dataCodes.toString(), "owditadmin@owd.com", "apimonitor@owd.com");
                }

                if (clientID.equals("146")) {
                    com.owd.core.Mailer.postMailMessage("OWD API Error (" + getClientIDFromAPIRequest(doc) + ")", headerInfo + "\n\n" + requestData + "\n\nBytes:\n\n" + myFilter.dataCodes.toString(), "apialerts@vitaminhealthbrands.com", "checkit@owd.com");
                    //  com.owd.core.Mailer.postMailMessage("OWD API Request Error (" + getClientIDFromAPIRequest(doc) + ")", headerInfo + "\n\n" + myFilter.data.toString() + "\n\nBytes:\n\n" + myFilter.dataCodes.toString(), "owditadmin@owd.com", "checkit@owd.com");

                }
            }
        }

        resp.setCharacterEncoding("UTF-8");

        resp.setContentType("text/xml;charset=UTF-8");
        try {

            resp.setCharacterEncoding("UTF-8");
            byte[] respBytes = responder.getNode().getBytes("UTF-8");

            resp.getOutputStream().write(respBytes);
        } catch (Throwable ex) {
            ex.printStackTrace();
            com.owd.core.Mailer.postMailMessage(" API Write Response Failure:" + ex.getMessage(), requestData + "\n\nBytes:\n\n" +OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "apimonitor@owd.com");
            APIResponse apiResponse = new APIResponse(1.0);
            apiResponse.setError(APIResponse.kErrProcess, "Internal error; please check your inputs or try again");
            try {
                resp.getWriter().write(apiResponse.getNode());
            } catch (Throwable exx) {
                exx.printStackTrace();
                com.owd.core.Mailer.postMailMessage(" API Write Response Failure:" + exx.getMessage(), requestData + "\n\nBytes:\n\n" +OWDUtilities.getStackTraceAsString(exx), "owditadmin@owd.com", "apimonitor@owd.com");
            }
        }

    }


    public void process(Source xml, Source xsl, Result result)
            throws TransformerException {
        try {
            Templates template = factory.newTemplates(xsl);
            Transformer transformer = template.newTransformer();
            transformer.transform(xml, result);
        } catch (TransformerConfigurationException tce) {
            throw new TransformerException(
                    tce.getMessageAndLocation());
        } catch (TransformerException te) {
            throw new TransformerException(
                    te.getMessageAndLocation());
        }
    }

    public String getServletInfo() {
        return "One World API Server v" + API.getVersionString();
    }

    class myServletInputStream extends InputStream {

        public StringBuffer data = new StringBuffer();
        public StringBuffer dataCodes = new StringBuffer();

        private InputStream in = null;

        public myServletInputStream() {
            in = null;
        }

        public myServletInputStream(InputStream ins) {
            in = ins;
        }

        public int read() throws IOException {

            int b = in.read();
            dataCodes.append("<" + b + ">");
            data.append(new Character((char) b));
            int times = 0;
            while (b == -1 && times < 10) {
                b = in.read();
                dataCodes.append("<" + b + ">");
                data.append(new Character((char) b));
                times++;
            }

            return b;
        }

        public int readLine(byte[] b, int off, int len) throws IOException {

            int r = in.read(b, off, len);
            data.append(b);
            return r;
        }

    }

    public static void main(String[] args) {
        try {

        } catch (Exception ex) {
            //ex.printStackTrace();
        }

    }

    public static void test(Document doc) {

        try {
            ////log.debug("in doc test");
            Element root = doc.getDocumentElement();
            root.normalize();
            NodeList nodes = root.getChildNodes();
            ////log.debug("got root list");
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    String reqName = nodes.item(0).getNodeName();
                    if (reqName.equals(OrderCreateRequest.kRootTag)) {
                        Element reqRoot = (Element) nodes.item(i);
                        ////log.debug("got req element");
                        NodeList shipNodes = reqRoot.getElementsByTagName(OrderCreateRequest.kShipNodeName);
                        ////log.debug("got ship nodes");
                        String country = ((Element) shipNodes.item(0)).getAttribute(OrderCreateRequest.kShipCountry);
                        ////log.debug("got ship country:"+country);
                        String cdesc = ((Element) shipNodes.item(0)).getAttribute(OrderCreateRequest.kShipCustomsDesc);
                        ////log.debug("got ship desc:"+cdesc);
                        NamedNodeMap attmap = ((Element) shipNodes.item(0)).getAttributes();
                        ////log.debug("got "+attmap.getLength()+" atts");
                        for (int k = 0; k < attmap.getLength(); k++) {
                            String val = attmap.item(k).getNodeName();
                            if (val == null) val = "null";
                            ////log.debug("N"+k+" type "+attmap.item(k).getNodeType()+" value "+val);
                        }

                        Attr att = ((Element) shipNodes.item(0)).getAttributeNode(OrderCreateRequest.kShipCustomsDesc);
                        if (att == null) {
                            ////log.debug("att node null");
                        } else {
                            if (att.hasChildNodes()) {
                                ////log.debug("has Child nodes");
                                NodeList attNodes = att.getChildNodes();
                                for (int j = 0; j < attNodes.getLength(); j++) {
                                    String val = attNodes.item(j).getNodeValue();
                                    if (val == null) val = "null";
                                    ////log.debug("N"+j+" type "+attNodes.item(j).getNodeType()+" value "+val);
                                }
                            } else {
                                ////log.debug("has no Child nodes");

                            }
                        }


                    } else if (reqName.equals(AsnCreateRequest.kRootTag)) {

                    } else if (reqName.equals(OrderStatusRequest.kRootTag)) {

                    } else if (reqName.equals(InventoryCountRequest.kRootTag)) {

                    }
                }
            }


        } catch (Exception e) {
            //e.printStackTrace();
        }


    }

    public static String test(String cid, String submission) {
        StringBuffer sb = new StringBuffer();

        try {
            //log.debug("Contacting server");
            WebResource server = new WebResource("http://secure.owd.com/webapps/api/api.jsp", "POST");
            server.setParameters("<?csXml version=\"1.0\"?><OWD_API_REQUEST api_version=\"1.0\" client_id=\"" + cid + "\" client_authorization=\"" + com.owd.core.OWDUtilities.encryptData(cid) + "\" testing=\"FALSE\">" + submission + "</OWD_API_REQUEST>");
            server.contentType = "text/csXml";
            //log.debug("Getting response");
            BufferedReader response = server.
                    getResource();
            int line = 0;
            line = (int) response.read();
            while (line != -1) {
                if ((char) line == '>')
                    sb.append("&gt;\n");
                else if ((char) line == '<')
                    sb.append("&lt;");
                else
                    sb.append((char) line);

                line = response.read();
                //log.debug(sb.toString());
            }
        } catch (Exception ex) {
            //log.debug("RESP EX");
            //ex.printStackTrace();
        }
        return sb.toString();

    }

    public static String getClientIDFromAPIRequest(Document doc) {

        String client_id = "0";

        try {

            Element root = doc.getDocumentElement();

            root.normalize();

            if (root.getTagName().equals(APIRequest.kTagAPIRequest)) {
                client_id = root.getAttribute(APIRequest.kAttClientID).trim();
            }

            if (client_id == null)
                client_id = "0";


        } catch (Throwable th) {
            client_id = "0";
        }

        if (client_id.length() < 1)
            client_id = "0";

        try {
            int testID = new Integer(client_id).intValue();
        } catch (Exception ex) {
            client_id = "0";
        }

        return client_id;
    }

    public static String getRequestTypeFromAPIRequest(Document doc) {

        String type = "";

        try {

            Element root = doc.getDocumentElement();

            root.normalize();

            if (root.getTagName().equals(APIRequest.kTagAPIRequest)) {

                NodeList nodes = root.getChildNodes();

                if(nodes != null)
                {
                    for(int  i=0;i<nodes.getLength();i++)

                    {

                        if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE)
                        {
                            String reqName = nodes.item(0).getNodeName();
                            if(reqName!=null)
                            {
                                log.debug("reqname="+reqName);
                                if(reqName.toUpperCase().contains("REQUEST"))
                                {
                                    type=reqName;
                                }
                            }
                        }

                    }

            }
            }

            if (type == null)
                type = "";


        } catch (Throwable th) {

        }



        return type;
    }


}

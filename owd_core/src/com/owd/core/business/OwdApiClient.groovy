package com.owd.core.business

import com.owd.core.OWDUtilities
import com.owd.core.WebResource
import org.apache.commons.lang.StringEscapeUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.xpath.XPathAPI
import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.*
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Created by stewartbuskirk1 on 8/26/14.
 */
class OwdApiClient {

    private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args)
    {


        try{
        //    println "Creating SKU D0815-T618-BKW-7";createPhysicalSku(551, "D0815-T618-BKW-7","Patched Plaid Button Up-BKW-XXXL", 88f, "Fantex",0.75f, "100% cotton/3205201016/Made in China/Apparel",44f,"Top", "BKW", "XXXL", false);
     }catch(Exception ex)
        {
            println "ERROR:"+ex.getMessage()

        }

    }


    public static void createPhysicalSku(int clientId, String sku, String desc, float unitPrice, String supplier,
                                         float weight, String customsDesc, float customsValue,
                                         String keyword, String color, String size, boolean testFlag)  throws Exception
    {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version:'99.99', client_authorization:OWDUtilities.encryptData(""+clientId), client_id:clientId, testing:testFlag?'TRUE':'FALSE') {
                        OWD_INVENTORY_CREATE_REQUEST() {
                            SKU(StringEscapeUtils.escapeXml(sku))
                            TYPE('PHYSICAL')
                            TITLE(StringEscapeUtils.escapeXml(desc))
                            // IMAGE_URL('http://www.owd.com/images/logo1.png')
                            // THUMB_URL('http://www.owd.com/images/logo1.png')
                            // WEB_URL('http://www.owd.com/')
                            //  GROUPNAME('candletime')
                            UNIT_PRICE(""+OWDUtilities.roundFloat(unitPrice))
                            UNIT_COST('0.00')
                            SUPPLIER(supplier)
                            WEIGHT(""+OWDUtilities.roundFloat(weight))
                            DESCRIPTION('')
                            KEYWORD(StringEscapeUtils.escapeXml(keyword) )
                            COLOR(StringEscapeUtils.escapeXml(color))
                            SIZE(StringEscapeUtils.escapeXml(size) )
                            CUSTOMS_DESC(StringEscapeUtils.escapeXml(customsDesc) )
                            CUSTOMS_VALUE(""+OWDUtilities.roundFloat(customsValue))

                        }
                    }
                }

        println "sending"
        println builder.bind(kitItemCreateRequest).toString()

        Document  response = getServerAPIResponse(builder.bind(kitItemCreateRequest).toString(), "http://service2.owd.com:8080/api/api.jsp");
        // verifyAPIResponse((Document) response, response.getDocumentElement());
        prettyPrint((org.w3c.dom.Node) (response.getDocumentElement()), System.out);
        if(!(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("results").getNodeValue().equals("SUCCESS") ))
        {
            throw new Exception(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("error_response").getNodeValue())
        }



    }


    public static void createVirtualSku(int clientId, String kitSku, String kitDesc, float unitPrice, boolean testFlag)  throws Exception
    {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData(""+clientId), client_id:clientId, testing:testFlag?'TRUE':'FALSE') {
                        OWD_INVENTORY_CREATE_REQUEST() {
                            SKU(kitSku)
                            TYPE('VIRTUAL')
                            TITLE(kitDesc)
                            // IMAGE_URL('http://www.owd.com/images/logo1.png')
                            // THUMB_URL('http://www.owd.com/images/logo1.png')
                            // WEB_URL('http://www.owd.com/')
                            //  GROUPNAME('candletime')
                            UNIT_PRICE(""+OWDUtilities.roundFloat(unitPrice))
                            UNIT_COST('0.00')
                            SUPPLIER('')
                            WEIGHT('0')
                            // DEFAULT_FACILITY_CODE('DC6')
                            DESCRIPTION('')

                        }
                    }
                }

        println "sending"
        println builder.bind(kitItemCreateRequest).toString()

        Document  response = getServerAPIResponse(builder.bind(kitItemCreateRequest).toString(), "http://service2.owd.com:8080/api/api.jsp");
        // verifyAPIResponse((Document) response, response.getDocumentElement());
        prettyPrint((org.w3c.dom.Node) (response.getDocumentElement()), System.out);
        if(!(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("results").getNodeValue().equals("SUCCESS") ))
        {
            throw new Exception(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("error_response").getNodeValue())
        }



    }

    public static void createKitSkuWithComponents(int clientId, String kitSku, String kitDesc, Map<String, Integer> components, boolean testFlag)  throws Exception
    {

        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def kitItemCreateRequest =
                {
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version:'1.0', client_authorization:OWDUtilities.encryptData(""+clientId), client_id:clientId, testing:testFlag?'TRUE':'FALSE') {
                        OWD_INVENTORY_CREATE_REQUEST() {
                            SKU(kitSku)
                            TYPE('KIT')
                            TITLE(kitDesc)
                            // IMAGE_URL('http://www.owd.com/images/logo1.png')
                            // THUMB_URL('http://www.owd.com/images/logo1.png')
                            // WEB_URL('http://www.owd.com/')
                            //  GROUPNAME('candletime')
                            UNIT_PRICE('0.00')
                            UNIT_COST('0.00')
                            SUPPLIER('')
                            WEIGHT('0.6')
                            // DEFAULT_FACILITY_CODE('DC6')
                            DESCRIPTION('')
                            COMPONENTS()
                                    {
                                        components.keySet().each()
                                        {   key ->
                                        COMPONENT()
                                                {
                                                    COMPONENT_SKU(key)
                                                    COMPONENT_QTY(""+components.get(key))
                                                }
                                        }
                                    }
                        }
                    }
                }

        println "sending"
        println builder.bind(kitItemCreateRequest).toString()

        Document  response = getServerAPIResponse(builder.bind(kitItemCreateRequest).toString(), "http://service2.owd.com:8080/api/api.jsp");
       // verifyAPIResponse((Document) response, response.getDocumentElement());
        prettyPrint((org.w3c.dom.Node) (response.getDocumentElement()), System.out);
       if(!(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("results").getNodeValue().equals("SUCCESS") ))
               {
                  throw new Exception(XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("error_response").getNodeValue())
               }



    }


    static Document getServerAPIResponse(String apiRequest, String url) throws Exception {
        WebResource catalog = new WebResource(url, WebResource.kPOSTMethod);
        catalog.setContent(apiRequest);
        catalog.contentType = "text/xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();




        URL testUrl = new URL(url);
        HttpURLConnection testConnection = (HttpURLConnection) testUrl.openConnection();

        testConnection.setRequestProperty("Content-Type", "text/xml");
        testConnection.setRequestMethod("POST");
        testConnection.setDoOutput(true);
        testConnection.getOutputStream().write(apiRequest.getBytes("UTF-8"));
        testConnection.getOutputStream().close();


        Document doc = builder.parse(testConnection.getInputStream());
        doc.getDocumentElement().normalize();


        return doc;

    }

    static public void verifyAPIResponse(Document response, Element request) throws TransformerException {

        try{
            throw new Exception("fixme");

            //  Assert.assertTrue("Server returned error",
            //          XPathAPI.selectSingleNode(response, "OWD_API_RESPONSE").getAttributes().getNamedItem("results").getNodeValue().equals("SUCCESS"));
            log.debug("BAD REQUEST");
        }catch(Exception ex)
        {
            prettyPrint((org.w3c.dom.Node) request, System.out)
            throw ex;
        }
    }


    public static final void prettyPrint(org.w3c.dom.Node xml, OutputStream out)
            throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        // tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        tf.transform(new DOMSource(xml), new StreamResult(out));
    }

}

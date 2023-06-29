package com.owd.dc.manifest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

import com.owd.connectship.xml.api.OWDShippingRequest.PACKAGE;
import com.owd.connectship.xml.api.OWDShippingRequest.PACKAGELIST;
import com.owd.connectship.xml.api.OWDShippingRequest.SHIPREQUEST;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPRESPONSE;
import com.owd.core.OWDUtilities;
import com.owd.core.Mailer;
import com.owd.core.csXml.SHIPMENTREQUEST;
import com.owd.dc.manifest.api.*;
import com.owd.connectship.xml.api.OWDShippingRequest.OWDSHIPPINGREQUEST;
import com.owd.connectship.xml.api.OWDShippingResponse.OWDSHIPPINGRESPONSE;
import com.owd.connectship.services.ConnectShipCommunications;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShipServerServlet extends HttpServlet {

    static Unmarshaller OWDShippingRequestUnmarshaller;

    static {
        try {
            OWDShippingRequestUnmarshaller = JAXBContext.newInstance("com.owd.connectship.xml.api.OWDShippingRequest").createUnmarshaller();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);

    }

    public void destroy() {
        super.destroy();

    }

    //GET requests not supported
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {


        OWDSHIPPINGRESPONSE responder = new OWDSHIPPINGRESPONSE();
        responder.setError_Type("FORMAT");
        responder.setError_Response("GET HTTP requests not supported - use POST type instead");

        resp.setContentType("text/xml");
        try {
            Marshaller m = ConnectShipCommunications.getMarshaller("com.owd.connectship.xml.api.OWDShippingResponse");
            m.marshal(responder, resp.getWriter());
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    //all requests should be POST
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        //    System.out.println("in API post2");
        OWDSHIPPINGRESPONSE responder = new OWDSHIPPINGRESPONSE();
        responder.setError_Type("SUCCESS");
        responder.setError_Response("");

        myServletInputStream myFilter = null;


        try {
            // System.out.println("in API post "+ req.getRemoteAddr()+":"+req.getHeader("x-forwarded-for"));
            if (req.getContentLength() > 102400)
                throw new ShipAPIFormatException("Requests cannot exceed 102400 characters in length");

            // System.out.println("got API factory");
            String ctHeader = req.getHeader("content-type");
            if (ctHeader == null) ctHeader = req.getHeader("Content-Type");
            if (ctHeader == null) ctHeader = "text/xml";


            myFilter = new myServletInputStream(req.getInputStream());


            if (myFilter == null) {
                throw new ShipAPIContentException("No valid data to process was found.");
            }
            OWDSHIPPINGREQUEST request;
             synchronized(this){
             request = (OWDSHIPPINGREQUEST) OWDShippingRequestUnmarshaller.unmarshal(myFilter);
             }

            handleOWDSHIPPINGREQUEST(responder, request);

        } catch (Throwable ex) {

            ex.printStackTrace();
              //Mailer.postMailMessage("Big Shipping Error",ex.getMessage()+"\r\n"+req.getQueryString(),"dnickels@owd.com","dnickels@owd.com");
            responder.setError_Type("PROCESS");
            responder.setError_Response(ex.getMessage());
        }

        resp.setContentType("text/xml");
        try {
            Marshaller m = ConnectShipCommunications.getMarshaller("com.owd.connectship.xml.api.OWDShippingResponse");
            m.marshal(responder, resp.getWriter());
        } catch (Throwable ex) {
            ex.printStackTrace();
            com.owd.core.Mailer.postMailMessage(" API Write Response Failure:" + ex.getMessage(), OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "checkit@owd.com");
        }

    }

    public static void handleOWDSHIPPINGREQUEST(OWDSHIPPINGRESPONSE responder, OWDSHIPPINGREQUEST request) throws Exception {
        if (request.getDOCREQUEST() != null) {
            responder.setDOCRESPONSE(DocRequest.handle(request.getDOCREQUEST(), "TRUE".equalsIgnoreCase(request.getTesting()), Double.parseDouble(request.getApi_Version())));

        } else if (request.getVOIDREQUEST() != null) {
            responder.setVOIDRESPONSE(VoidRequest.handle(request.getVOIDREQUEST(), "TRUE".equalsIgnoreCase(request.getTesting()), Double.parseDouble(request.getApi_Version())));

        } else if (request.getLISTREQUEST() != null) {
            responder.setLISTRESPONSE(ListRequest.handle(request.getLISTREQUEST(), "TRUE".equalsIgnoreCase(request.getTesting()), Double.parseDouble(request.getApi_Version())));

        } else if (request.getSHIPREQUEST() != null) {
            responder.setSHIPRESPONSE(ShipRequest.handle(request.getSHIPREQUEST(), "TRUE".equalsIgnoreCase(request.getTesting()), Double.parseDouble(request.getApi_Version()),request.getClient_Authorization()));

        } else {

            responder.setError_Type("FORMAT");
            responder.setError_Response("Unable to locate valid request type element");
        }
    }

    public String getServletInfo() {
        return "One World API Server v1.0";
    }

    class myServletInputStream extends InputStream {

        public StringBuffer data = new StringBuffer();
        public StringBuffer dataCodes = new StringBuffer();

        private InputStream in = null;

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
         //   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          //  factory.setNamespaceAware(false);
          //  factory.setValidating(false);
            //	////System.out.println("got factory");

           // String inText = "<OWD_ORDER_CREATE_REQUEST backorder_rule=\"PARTIALSHIP\" is_gift=\"NO\" order_reference=\"OWD2m883994\" order_source=\"Intranet\"><SHIPPING_INFO address_one=\"321 77th Pl\" city=\"Los Angeles\" country=\"USA\" email=\"\" first_name=\"Bob Jr\" "
         //           + "last_name=\"Cratchit\" phone=\"425-193-2293@@@@\" ship_cost=\"4.50\" ship_type=\"OWD.1ST.PRIORITY\" state=\"CA\" zip=\"90045\"/>    <BILLING_INFO address_one=\"321 77th Pl\" city=\"Los Angeles\" country=\"USA\" email=\"\" first_name=\"Bob Jr\" last_name=\"Cratchit\" " +
          //          "phone=\"425-193-2293@@@@\" state=\"CA\" zip=\"90045\" payment_type=\"CLIENT\" /><LINE_ITEM cost=\"4.95\" description=\"Big Red. -  Hat\" part_reference=\"SK0001\" requested=\"1\"/></OWD_ORDER_CREATE_REQUEST> ";
            //InputStream inStr = new StringBufferInputStream(inText);
            //javax.csXml.parsers.DocumentBuilder  builder = factory.newDocumentBuilder();
            //	org.w3c.dom.Document doc = builder.parse(inStr);
            SHIPREQUEST request = new SHIPREQUEST();
            request.setLABELPRINTER("Zebra.ZebraZ4Mplus");
            request.setSHIPPER("OWD");
            request.setLOCATIONCODE("DC1");
            PACKAGELIST pl = new PACKAGELIST();
            PACKAGE p  = new PACKAGE();

            p.setBARCODE("p14300331*29004302*b1");
            p.setWEIGHT_LBS("0.59");



            pl.getPACKAGE().add(p);
            request.setPACKAGELIST(pl);


            SHIPRESPONSE response = ShipRequest.handle(request,false,1,"51");
            System.out.println(response.getSHIPMENT().get(0).getMSN());

            //test("139", inText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    static final public String kServletName = "ShipServerServlet";


}

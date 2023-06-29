package com.owd.dc.manifest;

import com.owd.connectship.services.ConnectShipCommunications;
import com.owd.connectship.xml.api.OWDShippingRequest.*;
import com.owd.connectship.xml.api.OWDShippingResponse.LISTRESPONSE;
import com.owd.connectship.xml.api.OWDShippingResponse.LISTVALUE;
import com.owd.connectship.xml.api.OWDShippingResponse.OWDSHIPPINGRESPONSE;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.dc.manifest.api.ShippingAPITest;
import com.owd.dc.manifest.api.internal.OWDJasperReports;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.jasperreports.engine.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 9, 2008
 * Time: 2:46:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShipServerServletTest extends TestCase {

   static Map<String,Map<String,String>> masterShipperMap = new TreeMap<String, Map<String, String>>();

    static
    {
        Map<String,String> shipperMap = new TreeMap<String, String>();
        shipperMap.put("LIVE","OWD");
        shipperMap.put("RATE","SNY");
        masterShipperMap.put("DC1",shipperMap);
        Map<String,String> shipperMap2 = new TreeMap<String, String>();
        shipperMap2.put("LIVE","OWD_PROD_DC6");
        shipperMap2.put("RATE","OWD_PROD_DC6");
        masterShipperMap.put("DC6",shipperMap2);
    }

    public static String getLiveShipperForLocation(String loc)  throws Exception
    {
        if(masterShipperMap.get(loc)!=null)
        {
            return masterShipperMap.get(loc).get("LIVE");
        } else
        {
            throw new Exception("Unable to locate live shipper for location "+loc);
        }
    }

    public ShipServerServletTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

        //  sess = HibernateSession.currentSession();
    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {

        // HibernateSession.closeSession();
    }

    public static TestSuite suite() {
        TestSuite suite = new TestSuite(ShippingAPITest.class);
        return suite;
    }

    public void testOWDJasperReports() throws Exception {
      long i = Calendar.getInstance().getTimeInMillis();

    JasperReport js = OWDJasperReports.getInstance().getUpsCustoms();
     System.out.println(Calendar.getInstance().getTimeInMillis() - i);
        i = Calendar.getInstance().getTimeInMillis();
        JasperReport ja = OWDJasperReports.getInstance().getUpsCustoms();
       System.out.println(Calendar.getInstance().getTimeInMillis() - i);
       Map parameterMap = new HashMap();
        parameterMap.put("ORDER_NUM","6473537");
         Connection cxn = ConnectionManager.getConnection();
        JasperPrint print = JasperFillManager.fillReport(js,
                       parameterMap, cxn);
                 System.out.println("ssssssss");
              byte[] doc =  JasperExportManager.exportReportToPdf(print);
               System.out.println(doc.length);

}
   public void testJasper() throws Exception {
       Map parameterMap = new HashMap();
        parameterMap.put("ORDER_NUM","6473537");
       System.out.println(JRPropertiesMap.class.getPackage().getImplementationVersion());
       InputStream is = getClass().getClassLoader().getResourceAsStream("comminvoice.jrxml");

       JasperReport js = JasperCompileManager.compileReport(is);
       Connection cxn = ConnectionManager.getConnection();
       System.out.println("loaded is");
        JasperPrint print = JasperFillManager.fillReport(js,
                parameterMap, cxn);
          System.out.println("ssssssss");
       byte[] doc =  JasperExportManager.exportReportToPdf(print);
        System.out.println(doc.length);
       

     //   net.sf.jasperreports.engine.JasperPrint print = net.sf.jasperreports.engine.JasperFillManager.fillReport(DOCREQUEST.class.getClassLoader().getResourceAsStream("comminvoice.jasper"),
      //          parameterMap, ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

      //  return net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf(print);

   }

    public void testCalltagShipment() throws Exception
    {
       /* OrderStatus oldstatus = new OrderStatus("4042442");
        System.out.println("adding pack");
        PreparedStatement ckup = HibernateSession.getPreparedStatement("exec dbo.sp_virtualPackCalltag '" + oldstatus.orderBarcode.trim() + "'");
        System.out.println("executing pack");
               ckup.execute();
        System.out.println("calltag test");*/

        OrderStatus oldstatus = new OrderStatus("0000");

                Address pickupAddress = oldstatus.shipping.shipAddress;

                Contact pickupContact = oldstatus.shipping.shipContact;

                if(!(OWDUtilities.isValidEmailAddress(pickupContact.email)) )
                {
                    if(OWDUtilities.isValidEmailAddress(oldstatus.billContact.email))
                    {
                       pickupContact.email = oldstatus.billContact.email;
                    }   else
                    {
                       //don't throw because user may provide email
                    }
                }
            //    pickupContact.email = "owditadmin@owd.com";

        System.out.println("creating new order");
        Order order = oldstatus.createCallTagEmailLabelOrder(pickupAddress, pickupContact, "UPS Ground", 1.00f);
        System.out.println("saving order");
                String reference = order.saveNewOrder(OrderXMLDoc.kPaymentStatusClientManaged, false);
        System.out.println("getting orderstatus");

                    OrderStatus calltag = new OrderStatus(order.orderID);
        System.out.println("calltag order status: "+calltag.getStatusString());

        System.out.println("shiping call tag order");

      OrderStatus status = shipCallTagOrder(calltag,1.00f,oldstatus.orderReference) ;
    }




     public  OrderStatus shipCallTagOrder(OrderStatus order, float packageWeight, String tagReference) throws Exception {


    OWDSHIPPINGREQUEST osReq = new OWDSHIPPINGREQUEST();
        osReq.setApi_Version("1.0"); //does this ever change?



        PreparedStatement locstmt = HibernateSession.getPreparedStatement("sp_updateOrderFacility " + order.order_id + ";");
                    locstmt.executeUpdate();
                    HibUtils.commit(HibernateSession.currentSession());
                    locstmt.close();
        order = new OrderStatus(order.order_id);

        //could be cached, but not thread safe if so
        Unmarshaller um = JAXBContext.newInstance("com.owd.connectship.xml.api.OWDShippingResponse").createUnmarshaller();

        SHIPREQUEST sr = new SHIPREQUEST();
        osReq.setSHIPREQUEST(sr);
        sr.setLOCATIONCODE(order.getLocation()); //all returns currently go to DC. Multiple return destinations will require multiple shippers with different origin locations.
                                    // If multiple shippers were used (see method parameter), location code would be looked up from shipper or vice-versa
        sr.setSHIPPER(getLiveShipperForLocation(order.getLocation()));  //all returns currently go to DC. Multiple return destinations will require multiple shippers with different origin locations
        sr.setLABELPRINTER("Zebra.ZebraZ4Mplus"); //placeholder. Not used for calltag orders.
        PACKAGELIST pl = new PACKAGELIST();
        PACKAGE pack = new PACKAGE();
        pack.setBARCODE(order.orderBarcode.replaceAll("\\*", ""));
        pack.setWEIGHT_LBS("" + packageWeight);
        pl.getPACKAGE().add(pack);
        sr.setPACKAGELIST(pl);

           OWDSHIPPINGRESPONSE osr = new OWDSHIPPINGRESPONSE();
        osr.setError_Type("SUCCESS");
        osr.setError_Response("");
            //     String responseXMLa = doRequest("http://it.owd.com:8081/wms/manifesting/api", osReq, "com.owd.connectship.xml.api.OWDShippingRequest").toString();

        ShipServerServlet.handleOWDSHIPPINGREQUEST(osr,osReq);


        if(osr.getError_Type().equals("SUCCESS"))
        {
            return new OrderStatus(order.order_id);  //rather not load this again, but it will validate the shipped status and provide tracking to caller.
        } else
        {
           throw new Exception(osr.getError_Response());
        }
    }

    public void testListRequest() throws Exception {
        OWDSHIPPINGREQUEST osReq = new OWDSHIPPINGREQUEST();
        osReq.setApi_Version("1.0");
       // osReq.setClient_Authorization("hi");
       // osReq.setClient_Id("55");

        Unmarshaller um = JAXBContext.newInstance("com.owd.connectship.xml.api.OWDShippingResponse").createUnmarshaller();

        LISTREQUEST lr = new LISTREQUEST();
        osReq.setLISTREQUEST(lr);

        lr.setList_Name("xxx");


        String responseXMLa = doRequest("http://it.owd.com:8081/wms/manifesting/api", osReq, "com.owd.connectship.xml.api.OWDShippingRequest").toString();

        //   um.setValidating(false);
        OWDSHIPPINGRESPONSE osr = ((OWDSHIPPINGRESPONSE) um.unmarshal(new StreamSource(new StringReader(responseXMLa))));


        assertTrue("Error not returned correctly for bad list name request", !osr.getError_Type().equals("SUCCESS"));


        lr.setList_Name("LISTNAMES");
        osReq.setLISTREQUEST(lr);
        String responseXML = doRequest("http://it.owd.com:8081/wms/manifesting/api", osReq, "com.owd.connectship.xml.api.OWDShippingRequest").toString();

        //   um.setValidating(false);
        LISTRESPONSE lrx = ((OWDSHIPPINGRESPONSE) um.unmarshal(new StreamSource(new StringReader(responseXML)))).getLISTRESPONSE();


        assertEquals("Count of list names incorrect", lrx.getDATALIST().getLISTVALUE().size(), 3);

        for (LISTVALUE listChoice : lrx.getDATALIST().getLISTVALUE()) {
            System.out.println("testing " + listChoice.getAPIVALUE());
            lr.setList_Name(listChoice.getAPIVALUE());
            osReq.setLISTREQUEST(lr);
            responseXML = doRequest("http://it.owd.com:8081/wms/manifesting/api", osReq, "com.owd.connectship.xml.api.OWDShippingRequest").toString();
            //   um.setValidating(false);
            LISTRESPONSE lrxt = ((OWDSHIPPINGRESPONSE) um.unmarshal(new StreamSource(new StringReader(responseXML)))).getLISTRESPONSE();


            assertTrue("No values returned for list name " + listChoice.getAPIVALUE(), lrxt.getDATALIST().getLISTVALUE().size() > 0);

            assertTrue("Empty code value returned for " + listChoice.getAPIVALUE(), ((String) lrxt.getDATALIST().getLISTVALUE().get(0).getAPIVALUE()).length() > 0);
            assertTrue("Empty display value returned for " + listChoice.getAPIVALUE(), ((String) lrxt.getDATALIST().getLISTVALUE().get(0).getDISPLAYNAME()).length() > 0);

        }

        // Marshaller m = ConnectShipCommunications.getMarshaller("com.owd.connectship.xml.api.OWDShippingResponse");
        // m.marshal(lrx,System.out);


    }

    public StringBuffer doRequest(String url, Object request, String packagePath) throws Exception {

        WebResource server = new WebResource(url, "POST");
        ByteArrayOutputStream ostr = new ByteArrayOutputStream();

        Marshaller m = ConnectShipCommunications.getMarshaller(packagePath);


        m.marshal(request, new PrintStream(ostr));


        m.marshal(request, System.out);

        server.setParameters(ostr.toString());
        server.contentType = "text/xml";

        BufferedReader response = server.getResource();

        String thisLine;
        StringBuffer responder = new StringBuffer();

        while ((thisLine = response.readLine()) != null) {
            System.out.println(thisLine);
            responder.append(thisLine);
        }

        return responder;//.replaceAll("VOIDREQUESTED><ERRORCODE>0</ERRORCODE><ERRORDESCRIPTION>No Error</ERRORDESCRIPTION>","VOIDREQUESTED>"));


    }
}

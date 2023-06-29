package com.owd.core.managers;

import com.owd.connectship.services.ConnectShipCommunications;
import com.owd.connectship.xml.api.OWDShippingRequest.OWDSHIPPINGREQUEST;
import com.owd.connectship.xml.api.OWDShippingRequest.PACKAGE;
import com.owd.connectship.xml.api.OWDShippingRequest.PACKAGELIST;
import com.owd.connectship.xml.api.OWDShippingRequest.SHIPREQUEST;
import com.owd.connectship.xml.api.OWDShippingResponse.OWDSHIPPINGRESPONSE;
import com.owd.core.WebResource;
import com.owd.core.business.order.OrderStatus;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/26/12
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallTagManager {
private final static Logger log =  LogManager.getLogger();

   
    public static OrderStatus shipCallTagOrder(OrderStatus order, float packageWeight, String tagReference, String locationCode) throws Exception {

        OWDSHIPPINGREQUEST osReq = new OWDSHIPPINGREQUEST();
        osReq.setApi_Version("1.0"); //does this ever change?


        PreparedStatement locstmt = HibernateSession.getPreparedStatement("update owd_order set facility_code=? where order_id=?;");
                locstmt.setString(1,locationCode);
        locstmt.setInt(2,Integer.parseInt(order.order_id));
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
        sr.setSHIPPER(ManifestingManager.getLiveShipperForLocation(order.getLocation()));  //all returns currently go to DC. Multiple return destinations will require multiple shippers with different origin locations
        sr.setLABELPRINTER("Zebra.ZebraZ4Mplus"); //placeholder. Not used for calltag orders.
        PACKAGELIST pl = new PACKAGELIST();
        PACKAGE pack = new PACKAGE();
        pack.setBARCODE(order.orderBarcode.replaceAll("\\*", ""));
        pack.setWEIGHT_LBS("" + packageWeight);
        pl.getPACKAGE().add(pack);
        sr.setPACKAGELIST(pl);

        //wish the endpoint URL was configurable and changeable, maybe in db...
        String responseXMLa = doRequest("http://it.owd.com:8081/wms/manifesting/api", osReq, "com.owd.connectship.xml.api.OWDShippingRequest").toString();

        log.debug(responseXMLa);
        //um.setValidating(false);
        OWDSHIPPINGRESPONSE osr = ((OWDSHIPPINGRESPONSE) um.unmarshal(new StreamSource(new StringReader(responseXMLa))));

        if(osr.getError_Type().equals("SUCCESS"))
        {
            return new OrderStatus(order.order_id);  //rather not load this again, but it will validate the shipped status and provide tracking to caller.
        } else
        {
           throw new Exception(osr.getError_Response());
        }
    }


    public static void main(String[] args) throws Exception {


        String testData = "2014-06-11 08:01:27,411 INFO org.apache.hadoop.ipc.RpcServer: RpcServer.responder: starting";
        Pattern p = Pattern.compile("\\.([^.]*)\\:");
        Matcher m = p.matcher(testData);

        if(m.find())
        {
            log.debug("Match!");
            log.debug(m.group(1));
        }

       /* log.debug("loading order");
        OrderStatus oldstatus = new OrderStatus("4042442");

        log.debug("calltag test");
        OrderStatus status = shipCallTagOrder(oldstatus, 1.25f, oldstatus.orderReference,"DC1");*/
    }

    public static StringBuffer doRequest(String url, Object request, String packagePath) throws Exception {

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
          //  log.debug(thisLine);
            responder.append(thisLine);
        }

        return responder;//.replaceAll("VOIDREQUESTED><ERRORCODE>0</ERRORCODE><ERRORDESCRIPTION>No Error</ERRORDESCRIPTION>","VOIDREQUESTED>"));


    }
}

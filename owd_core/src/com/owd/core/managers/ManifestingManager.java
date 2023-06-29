package com.owd.core.managers;

import com.owd.connectship.services.ConnectShipCommunications;
import com.owd.connectship.soap.*;
import com.owd.connectship.soap.test.MyHandlerResolver;
import com.owd.connectship.xml.api.OWDShippingRequest.OWDSHIPPINGREQUEST;
import com.owd.connectship.xml.api.OWDShippingRequest.PACKAGE;
import com.owd.connectship.xml.api.OWDShippingRequest.PACKAGELIST;
import com.owd.connectship.xml.api.OWDShippingRequest.SHIPREQUEST;
import com.owd.connectship.xml.api.OWDShippingResponse.OWDSHIPPINGRESPONSE;
import com.owd.core.ConnectshipManifest;
import com.owd.core.ConnectshipTransmissionFile;
import com.owd.core.OWDUtilities;
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
import javax.xml.ws.handler.HandlerResolver;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 16, 2003
 * Time: 1:28:07 PM
 * To change this template use Options | File Templates.
 */
public class ManifestingManager {
    private final static Logger log = LogManager.getLogger();

    static public Map<String, Map<String, String>> masterShipperMap = new TreeMap<String, Map<String, String>>();
    static CoreXmlPort amp;

    static {
        Map<String, String> shipperMap = new TreeMap<String, String>();
        shipperMap.put("LIVE", "OWD");
        shipperMap.put("RATE", "SNY");
        masterShipperMap.put("DC1", shipperMap);
        Map<String, String> shipperMap2 = new TreeMap<String, String>();
        shipperMap2.put("LIVE", "OWD_PROD_DC6");
        shipperMap2.put("RATE", "OWD_PROD_DC6");
        masterShipperMap.put("DC6", shipperMap2);
        Map<String, String> shipperMap3 = new TreeMap<String, String>();
        shipperMap3.put("LIVE", "OWD_PROD_DC7");
        shipperMap3.put("RATE", "OWD_PROD_DC7");
        masterShipperMap.put("DC7", shipperMap3);
        /*Map<String, String> shipperMap4 = new TreeMap<String, String>();
        shipperMap4.put("LIVE", "LYFT");
        shipperMap4.put("RATE", "LYFT");
        masterShipperMap.put("TEST", shipperMap4);*/


        AMPServices smp = new AMPServices();
        // Following two
        HandlerResolver myHandlerResolver = new MyHandlerResolver();

        //uncomment next line to echo SOAP requests/responses to console
        //   smp.setHandlerResolver(myHandlerResolver);
        //   log.debug("myHandlerResolver has been set.");

        amp = smp.getAMPSoapService();
    }

    public static String getLiveShipperForLocation(String loc, int clientId, int boxType,String shipMethod, String groupName) throws Exception {
        log.debug(shipMethod);
        log.debug(groupName);

       if(clientId == 471 && groupName.equalsIgnoreCase("walmartdsv")&&shipMethod.contains("FEDEX.SP")){
           return "TEST";
       }
        if(clientId == 529 &&shipMethod.contains("FEDEX.SP")){
            return "LYFT";
        }
        if(clientId == 626 && groupName.equalsIgnoreCase("qvc")&&shipMethod.contains("UPS")){
            return "DC7_QVC_JB";
        }





        if (masterShipperMap.get(loc) != null) {
            if (loc.equals("DC1") & (clientId == 489 || clientId == 55) & boxType == 202) {
                log.debug("XXXXXXXX We are sending a non EVS shiper XXXXXXXXXXXXX");
                return ("OWD_NON_EVS");
            }
            return masterShipperMap.get(loc).get("LIVE");
        } else {
            throw new Exception("Unable to locate live shipper for location " + loc);
        }


    }

    public static String getLiveShipperForLocation(String loc) throws Exception {
        if (masterShipperMap.get(loc) != null) {
            return masterShipperMap.get(loc).get("LIVE");
        } else {
            throw new Exception("Unable to locate live shipper for location " + loc);
        }
    }

    public static String getRatingShipperForLocation(String loc) throws Exception {
        if (masterShipperMap.get(loc) != null) {
            return masterShipperMap.get(loc).get("RATE");
        } else {
            throw new Exception("Unable to locate rating shipper for location " + loc);
        }
    }


    public static OrderStatus shipCallTagOrder(OrderStatus order, float packageWeight, String tagReference, String locationCode) throws Exception {

        OWDSHIPPINGREQUEST osReq = new OWDSHIPPINGREQUEST();
        osReq.setApi_Version("1.0"); //does this ever change?


        PreparedStatement locstmt = HibernateSession.getPreparedStatement("update owd_order set facility_code=? where order_id=?;");
        locstmt.setString(1, locationCode);
        locstmt.setInt(2, Integer.parseInt(order.order_id));
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

        if (osr.getError_Type().equals("SUCCESS")) {
            return new OrderStatus(order.order_id);  //rather not load this again, but it will validate the shipped status and provide tracking to caller.
        } else {
            throw new Exception(osr.getError_Response());
        }
    }

    final static String GS1NonUPCPrefix = "0859039004"; //GS1 OWD prefix

    public static String getSSCCBarcode() throws Exception {
        String ssccid = getNextSsccId();
        String testid = ssccid.toCharArray()[0] + GS1NonUPCPrefix + ssccid.substring(1);
        log.debug(testid + getSSCCCheckDigit(testid));
        return testid + getSSCCCheckDigit(testid);
    }

    public static String getWalmartSSCCBarcodeWithVendorId(String vendorId) throws Exception {
        String ssccid = getNextSsccId();
        ssccid = "" + Long.parseLong(ssccid);
        int totalLenDesired = 16 - vendorId.length();
        if (ssccid.length() > totalLenDesired) {
            ssccid = ssccid.substring(ssccid.length() - totalLenDesired);
        } else if (ssccid.length() < totalLenDesired) {
            int toadd = totalLenDesired - ssccid.length();
            for (int i = 0; i < toadd; i++)
                ssccid = "0" + ssccid;
        }
        String testid = "00" + ssccid.toCharArray()[0] + "0" + vendorId + (ssccid.substring(1));
        log.debug(testid + getSSCCCheckDigit(testid));
        return testid + getSSCCCheckDigit(testid);
    }

    public static List<String> getSSCCBarcodes(int quantity) throws Exception {
        if (quantity < 1) {
            throw new Exception("SSCC requested quantity must be greater than 0");
        }
        List<String> codes = new ArrayList<String>(quantity);


        for (int i = 0; i < quantity; i++) {
            String ssccid = getNextSsccId();
            String testid = ssccid.toCharArray()[0] + GS1NonUPCPrefix + ssccid.substring(1);
            codes.add(testid + getSSCCCheckDigit(testid));
        }
        return codes;

    }

    public static void main(String[] args) {
        try {
            // System.setProperty("com.owd.environment", "test");

            //   log.debug(getSSCCBarcodes(10));

            //   String rString = "123456789";
            //  log.debug(rString.substring(rString.length() - 7)) ;
            //  shipOrder(new OrderStatus("9192925"),1.5f);
            //  PackingManager.packAndShip(9192925, 1.2, 0.00, "030143972708176");
          //  System.out.println(getConnectshipOpenManifestsForToday());
           // System.out.println(getConnectshipUntransmittedForToday());
           // System.out.println(getSSCCBarcode());

          System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());
            System.out.println(getSSCCBarcode());



        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

    }


    public static void shipOrder(OrderStatus order, float packageWeight) throws Exception {

        OWDSHIPPINGREQUEST osReq = new OWDSHIPPINGREQUEST();
        osReq.setApi_Version("1.0"); //does this ever change?

        //order = new OrderStatus(order.order_id);
        log.debug("LOCATION:" + order.getLocation() + ":");
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

        if (!(osr.getError_Type().equals("SUCCESS"))) {
            throw new Exception(osr.getError_Response());
        }
    }


    public static StringBuffer doRequest(String url, Object request, String packagePath) throws Exception {

        WebResource server = new WebResource(url, "POST");
        ByteArrayOutputStream ostr = new ByteArrayOutputStream();

        Marshaller m = ConnectShipCommunications.getMarshaller(packagePath);

        m.marshal(request, new PrintStream(ostr));

        //   m.marshal(request, System.out);

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

    private static String getNextSsccId() throws Exception {
        String rString = null;

        Connection cxn = ConnectionManager.getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            //log.debug("getting id for " + module);
            stmt = cxn.createStatement();
            String esql = "exec get_next_id \'SSCC\'";

            rs = stmt.executeQuery(esql);

            if (rs.next()) {
                //log.debug("gotnext");
                rString = rs.getString(1);
            }

            cxn.commit();
        } catch (Exception ex) {
            throw ex;
        } finally {


            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }

        }
        if (rString != null) {
            if (rString.length() < 7) {
                int startLen = rString.length();
                for (int i = 0; i < (7 - startLen); i++)
                    rString = "0" + rString;
            } else if (rString.length() > 7) {
                rString = rString.substring(rString.length() - 7);
            }

        }
        return rString;


    }

    //calculate moc-10 check digit for SSCC container barcodes
    public static int getSSCCCheckDigit(String idWithoutCheckdigit) throws Exception {

// allowable characters within identifier
        String validChars = "0123456789";


// remove leading or trailing whitespace, convert to uppercase
        idWithoutCheckdigit = idWithoutCheckdigit.trim().toUpperCase();

// this will be a running total
        int sum = 0;

// loop through digits from right to left
        for (int i = 0; i < idWithoutCheckdigit.length(); i++) {

//set ch to "current" character to be processed
            char ch = idWithoutCheckdigit
                    .charAt(idWithoutCheckdigit.length() - i - 1);

// throw exception for invalid characters
            if (validChars.indexOf(ch) == -1) {

                throw new Exception(
                        "\"" + ch + "\" is an invalid character");

            }
// our "digit" is calculated using ASCII value - 48
            int digit = Integer.parseInt("" + ch);
            // log.debug(digit);
// weight will be the current digit's contribution to
// the running total
            int weight;
            if (i % 2 == 0) {

                // for alternating digits starting with the rightmost, we
                // use our formula this is the same as multiplying x 2 and
                // adding digits together for values 0 to 9.  Using the
                // following formula allows us to gracefully calculate a
                // weight for non-numeric "digits" as well (from their
                // ASCII value - 48).
                weight = (3 * digit);


            } else {

                // even-positioned digits just contribute their actual
                // value
                weight = digit;

            }

// keep a running total of weights
            sum += weight;

        }
// avoid sum less than 10 (if characters below "0" allowed,
// this could happen)

// check digit is amount needed to reach next number
// divisible by ten
        sum = 10 - (sum % 10);
        if (sum > 9) {
            sum = 0;
        }
        return sum;

    }

    public static Map<String, List<ConnectshipManifest>> getConnectshipOpenManifestsForToday(String location)
    {
        try {
            Map<String, Map<String, List<ConnectshipManifest>>> manis = getConnectshipOpenManifestsForToday();
            if (manis.get(location) != null) {
                return manis.get(location);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new HashMap<String, List<ConnectshipManifest>>();
    }

    public static Map<String, Map<String, List<ConnectshipManifest>>> getConnectshipOpenManifestsForToday()  throws Exception
    {


        Map<String, Map<String, List<ConnectshipManifest>>> responseMap = new HashMap<String, Map<String, List<ConnectshipManifest>>>();

        Map<String,String> carriers = new HashMap<String,String>();

        ListCarriersRequest servReq = new ListCarriersRequest();

        ListCarriersResponse services = amp.listCarriers(servReq);
        ServiceList serviceList = new ServiceList();
        for(Identity service:services.getResult().getResultData().getItem())
        {
            log.debug(service.getName()+":"+service.getSymbol());
            carriers.put(service.getSymbol(),service.getName());
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        for(String location:masterShipperMap.keySet()) {

            log.debug("LOCATION:"+location);
            ListCloseOutItemsRequest servReq2 = new ListCloseOutItemsRequest();
            servReq2.setShipper(ManifestingManager.masterShipperMap.get(location).get("LIVE"));

            for(String carrierCode:carriers.keySet()) {
                servReq2.setCarrier(carrierCode);
                ListCloseOutItemsResponse closers = amp.listCloseOutItems(servReq2);
                log.debug("CODE:"+closers.getResult().getCode());
                for (Identity service : closers.getResult().getResultData().getItem()) {
                    String symbol = service.getSymbol();
                    symbol = symbol.substring(symbol.indexOf("_")+1);
                    Date shipDate = OWDUtilities.setTimeToMidnight(df.parse(symbol));
                    Date nowDate = OWDUtilities.setTimeToMidnight(Calendar.getInstance().getTime());

                    if(shipDate.getTime()<=nowDate.getTime()) {
                        log.debug(service.getName() + ":" + service.getSymbol());
                         if(responseMap.get(location)==null)
                         {
                             responseMap.put(location,new HashMap<String, List<ConnectshipManifest>>()) ;
                         }
                        if(responseMap.get(location).get(carriers.get(carrierCode))==null){
                            responseMap.get(location).put(carriers.get(carrierCode),new ArrayList<ConnectshipManifest>());
                        }
                        responseMap.get(location).get(carriers.get(carrierCode)).add(new ConnectshipManifest(carriers.get(carrierCode),carrierCode,service.getName(),service.getSymbol())) ;
                    }
                }
            }
        }

        return responseMap;
    }

    public static Map<String, List<ConnectshipTransmissionFile>> getConnectshipUntransmittedForToday(String location)
    {
        try {
            Map<String, Map<String, List<ConnectshipTransmissionFile>>> manis = getConnectshipUntransmittedForToday();
            if (manis.get(location) != null) {
                return manis.get(location);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new HashMap<String, List<ConnectshipTransmissionFile>>();
    }
    public static Map<String, Map<String, List<ConnectshipTransmissionFile>>> getConnectshipUntransmittedForToday()  throws Exception
    {


        Map<String, Map<String, List<ConnectshipTransmissionFile>>> responseMap = new HashMap<String, Map<String, List<ConnectshipTransmissionFile>>>();

        Map<String,String> carriers = new HashMap<String,String>();

        ListCarriersRequest servReq = new ListCarriersRequest();

        ListCarriersResponse services = amp.listCarriers(servReq);
        ServiceList serviceList = new ServiceList();
        for(Identity service:services.getResult().getResultData().getItem())
        {
            log.debug(service.getName()+":"+service.getSymbol());
            carriers.put(service.getSymbol(),service.getName());
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        for(String location:masterShipperMap.keySet()) {

            log.debug("LOCATION:"+location);
            ListTransmitItemsRequest servReq2 = new ListTransmitItemsRequest();
            servReq2.setShipper(ManifestingManager.masterShipperMap.get(location).get("LIVE"));

            for(String carrierCode:carriers.keySet()) {
                servReq2.setCarrier(carrierCode);
                ListTransmitItemsResponse closers = amp.listTransmitItems(servReq2);
                log.debug("CODE:"+closers.getResult().getCode());
                for (TransmitItem service : closers.getResult().getResultData().getItem()) {

                    String symbol = service.getSymbol();
                    symbol = symbol.substring(symbol.indexOf("_")+1);
                    symbol = symbol.substring(symbol.indexOf("_")+1);

                    symbol = symbol.substring(0,symbol.indexOf("_"));
                    log.debug(symbol);
                    Date shipDate = OWDUtilities.setTimeToMidnight(df.parse(symbol));
                    Calendar todayCal = Calendar.getInstance();
                    todayCal.add(Calendar.DAY_OF_MONTH,-14);
                    Date nowDate = OWDUtilities.setTimeToMidnight(todayCal.getTime());

                    if(nowDate.getTime()<=shipDate.getTime() && service.getStatus()!=2 && !(service.getName().contains("(EMM)") || service.getName().contains("(DelConf)"))) {

                        if(responseMap.get(location)==null)
                        {
                            responseMap.put(location,new HashMap<String, List<ConnectshipTransmissionFile>>()) ;
                        }
                        if(responseMap.get(location).get(carriers.get(carrierCode))==null){
                            responseMap.get(location).put(carriers.get(carrierCode),new ArrayList<ConnectshipTransmissionFile>());
                        }
                        ConnectshipTransmissionFile transmission = new ConnectshipTransmissionFile(service.getSymbol(),service.getName(),service.getSequence(),service.getStatus());
                           transmission.setFiles(service.getFiles().getItem());


                        responseMap.get(location).get(carriers.get(carrierCode)).add(transmission) ;
                    }
                }
            }
        }

        return responseMap;
    }


}

package com.owd.jobs.jobobjects.commercev3;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.amazonaws.util.IOUtils;
import com.owd.core.OWDUtilities;
import ipworksssl.Soaps;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 22, 2008
 * Time: 12:03:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommerceV3Utilities {
private final static Logger log =  LogManager.getLogger();
    public static Map shipMethods  = null;

    public static Document commerceV3Communicator(String requestXml, boolean after,String user,String password,String serviceID) throws Exception
    {
         

      String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
          "<CV3Data version=\"2.0\">\n" +
          "  <request>\n" +
          "    <authenticate>\n" +
          "      <user>" + user + "</user>\n" +
          "      <pass>" + password + "</pass>\n" +
          "      <serviceID>" + serviceID + "</serviceID>\n" +
          "    </authenticate>\n" +
          "    \n" +  (after?"":requestXml)+
          "  </request>\n" +
                (after?requestXml:"")+
          "</CV3Data>";

        log.debug(request);
      String encodedString = Base64.encodeBase64String(request.getBytes());
        log.debug("encoded");
      Soaps con = new Soaps();
            log.debug("created");
      con.setRuntimeLicense(OWDUtilities.getIPWorksSSSLRuntimeLicense());
        log.debug("licensed");
      con.setMethod("CV3Data");
      con.setMethodURI("CV3data");
      con.addParam("data",encodedString);
        log.debug("paramed");
      con.setURL("https://service.commercev3.com");
        log.debug("sending request");
      con.sendRequest();
        log.debug(con.getFaultString()+":"+con.getFaultCode()
        );
        log.debug("sent request");
      String reply = new String(org.apache.commons.codec.binary.Base64.decodeBase64((con.getReturnValue())));
        log.debug(reply);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder doc = factory.newDocumentBuilder();
      Document xml = doc.parse(new ByteArrayInputStream(reply.getBytes()));
      xml.getDocumentElement().normalize();

        return xml;
    }

      public static String getTranslatedShipMethod(String method){

          log.debug(method);
    return CommerceV3Utilities.getShippingMap().get(method).toString();

  }

    protected static synchronized Map getShippingMap()
    {

     if(shipMethods == null)
     {


    CommerceV3Utilities.shipMethods = new TreeMap();
    CommerceV3Utilities.shipMethods.put("OWD.LTL.ABF","ABF");
    CommerceV3Utilities.shipMethods.put("TANDATA_AIRBORNE.AIRBORNE.EXP","Airborne Express");
    CommerceV3Utilities.shipMethods.put("AIRBORNE.INTLF","Airborne Int'l Freight");
    CommerceV3Utilities.shipMethods.put("OWD.AMBER","Amber Air Int'l");
    CommerceV3Utilities.shipMethods.put("OWD.BAX","BAX Global");
    CommerceV3Utilities.shipMethods.put("OWD.CIRCLE","Circle Air Int'l");
    CommerceV3Utilities.shipMethods.put("OWD.LTL.CONSOLIDATED","Consolidated Freight");
    CommerceV3Utilities.shipMethods.put("OWD.LTL.CROSSCOUNTRY","Cross Country Courier");
    CommerceV3Utilities.shipMethods.put("OWD.LTL.DANZAS","Danzas Air");
    CommerceV3Utilities.shipMethods.put("DHL","DHL");
    CommerceV3Utilities.shipMethods.put("TANDATA_DHL.DHL.WPX","DHL Worldwide Express");
    CommerceV3Utilities.shipMethods.put("OWD.NOBOX.EMAIL","Email");
    CommerceV3Utilities.shipMethods.put("OWD.EMERY","Emery");
    CommerceV3Utilities.shipMethods.put("TANDATA_FEDEXBOOK.FEDEX.FR1","FedEx 1Day Freight");
    CommerceV3Utilities.shipMethods.put("FDX.ECO","FedEx 2Day");
    CommerceV3Utilities.shipMethods.put("FDX.FR2","FedEx 2Day Freight");
    CommerceV3Utilities.shipMethods.put("TANDATA_FEDEXBOOK.FEDEX.FR3","FedEx 3Day Freight");
    CommerceV3Utilities.shipMethods.put("FDX.ESP","FedEx Express Saver");
    CommerceV3Utilities.shipMethods.put("TANDATA_FEDEXFSMS.FEDEX.GND","FedEx Ground");
    CommerceV3Utilities.shipMethods.put("TANDATA_FEDEXGROUND.FEDEX.CAN","FedEx Ground U.S. to Canada");
    CommerceV3Utilities.shipMethods.put("FDX.IEC","FedEx International Economy");
    CommerceV3Utilities.shipMethods.put("TANDATA_FEDEXBOOK.FEDEX.IFR2","FedEx International Economy Freight");
    CommerceV3Utilities.shipMethods.put("TANDATA_FEDEXBOOK.FEDEX.IFO","FedEx International First");
    CommerceV3Utilities.shipMethods.put("FDX.IPR","FedEx International Priority");
    CommerceV3Utilities.shipMethods.put("TANDATA_FEDEXBOOK.FEDEX.IFR1","FedEx International Priority Freight");
    CommerceV3Utilities.shipMethods.put("FDX.PRI","FedEx Priority Overnight");
    CommerceV3Utilities.shipMethods.put("FDX.STD","FedEx Standard Overnight");
    CommerceV3Utilities.shipMethods.put("OWD.LTL.LAKEVILLE","Lakeville Motor Express");
    CommerceV3Utilities.shipMethods.put("OWD.LEWIS","Lewis Trucking");
    CommerceV3Utilities.shipMethods.put("TANDATA_LTL.LTL.LTL","LTL");
    CommerceV3Utilities.shipMethods.put("OWD.NOBOX.PICKEDUP","Picked Up");
    CommerceV3Utilities.shipMethods.put("OWD.LTL.ROADWAY","Roadway Express");
    CommerceV3Utilities.shipMethods.put("OWD.SATURN","Saturn");
    CommerceV3Utilities.shipMethods.put("TEAMDEPOT_PERS","TeamDepot Personalization");
    CommerceV3Utilities.shipMethods.put("UPS.2DA","UPS 2nd Day Air");
    CommerceV3Utilities.shipMethods.put("UPS.2AM","UPS 2nd Day Air A.M.");
    CommerceV3Utilities.shipMethods.put("UPS.3DA","UPS 3 Day Select");
    CommerceV3Utilities.shipMethods.put("UPS.GND","UPS Ground");
    CommerceV3Utilities.shipMethods.put("UPS.NDA","UPS Next Day Air");
    CommerceV3Utilities.shipMethods.put("UPS.NAM","UPS Next Day Air A.M.");
    CommerceV3Utilities.shipMethods.put("UPS.NDS","UPS Next Day Air Saver");
    CommerceV3Utilities.shipMethods.put("UPS.STDCAMX","UPS Standard Canada");
    CommerceV3Utilities.shipMethods.put("UPS.WEPD","UPS Worldwide Expedited");
    CommerceV3Utilities.shipMethods.put("UPS.WEXP","UPS Worldwide Express");
    CommerceV3Utilities.shipMethods.put("CONNECTSHIP_UPS.UPS.EXPPLS","UPS Worldwide Express Plus");
    CommerceV3Utilities.shipMethods.put("CONNECTSHIP_UPS.UPS.EXPSVR","UPS Worldwide Express Saver");
    CommerceV3Utilities.shipMethods.put("TANDATA_USPS.USPS.BPM","USPS Bnd Prt Mtr Single Piece");
    CommerceV3Utilities.shipMethods.put("POS.EXP","USPS Priority Mail Express");
    CommerceV3Utilities.shipMethods.put("OWD_USPS_I_EXP_DMND","USPS Priority Mail Express International");
    CommerceV3Utilities.shipMethods.put("OWD.1ST.LETTER","USPS First-Class Mail");
    CommerceV3Utilities.shipMethods.put("OWD_USPS_I_FIRST","USPS First-Class Mail International");
    CommerceV3Utilities.shipMethods.put("TANDATA_USPS.USPS.LIBRARY","USPS Library Mail Single-Piece");
    CommerceV3Utilities.shipMethods.put("OWD.4TH.BOOK","USPS Media Mail Single-Piece");
    CommerceV3Utilities.shipMethods.put("OWD.4TH.PARCEL","USPS Parcel Select Nonpresort");
    CommerceV3Utilities.shipMethods.put("OWD.1ST.PRIORITY","USPS Priority Mail");
    CommerceV3Utilities.shipMethods.put("OWD_USPS_I_PRIORITY","USPS Priority Mail International");
    CommerceV3Utilities.shipMethods.put("OWD.LTL.YELLOW","Yellow Freight");
    }


        return shipMethods;
}


}

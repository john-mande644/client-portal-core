package com.owd.dc.manifest.api.internal;


/**

 * Title:        TanData Shipping Client

 * Description:

 * Copyright:    Copyright (c) 2001

 * Company:

 * @author

 * @version 1.0

 */

import com.owd.connectship.soap.*;
import com.owd.connectship.soap.test.MyHandlerResolver;
import com.owd.connectship.xml.ShipmentRequest.LABELPRINT;
import com.owd.connectship.xml.ShipmentRequest.RETURNDELIVERY;
import com.owd.core.OWDUtilities;
import com.owd.core.TabReader;
import com.owd.core.TimeStamp;
import com.owd.core.WebResource;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.packing.DimensionalWeight;
import com.owd.core.business.order.zoneUtilities.FlatRateShipping;
import com.owd.core.business.order.zoneUtilities.ZoneLookup;
import com.owd.core.business.order.zoneUtilities.shipMethodUpdate;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.ManifestingManager;
import com.owd.dc.manifest.api.MultiPackageShipment;
import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OWDPackage;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdClientInv;
import com.thoughtworks.xstream.XStream;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.handler.HandlerResolver;
import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class models an actual "shipment". It encapsulates all the information
 * that is needed to be submitted to an instance of the ConnectShip server to be evaluated, recorded
 * and eventually electronically manifested with UPS at the end of a day. It
 * exposes operations covering the most common scenarios when conducting such a
 * shipment transaction, such as loading information from OWD internal system, rating
 * a package, submitting the information of a package to the ConnectShip server for manifesting
 * (and obtaining shipment tracking information such as Tracking Number for UPS orders or
 * Delivery Confirmation for USPS orders when activated), voiding a shipped pacakge that has been
 * recorded on the ConnectShip server, etc. This class encapsulates the core functionalities
 * of the OWD Shipping Application. And from a MVC perspective, it is the model of this Swing application.
 *
 * @author Stewart Buskirk, modified by Liheng Qiao
 * @version 1.69 08/15/03
 */


public class AMPConnectShipShipment {

    static CoreXmlPort amp;
    static DateFormat dformat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    protected final static Logger log = LogManager.getLogger();


    static {

        AMPServices smp = new AMPServices();

        HandlerResolver myHandlerResolver = new MyHandlerResolver();

        //uncomment next line to echo SOAP requests/responses to console
        //   smp.setHandlerResolver(myHandlerResolver);
        //   System.out.println("myHandlerResolver has been set.");

        amp = smp.getAMPSoapService();

        // updateServiceMaps();
    }

    public int getClientID() {
        if (map.get("OWDCLIENTID") == null) {
            setClientID(0);
        }

        return ((Integer) map.get("OWDCLIENTID")).intValue();
    }

    public void setClientID(int clientID) {
        map.put("OWDCLIENTID", new Integer(clientID));
    }

    //  int clientID = 0;
    // public int fedexLabel=0;

    public static final int kTDItemSKU = 0;

    public static final int kTDItemQty = 1;

    public static final int kTDItemPrice = 2;

    public static final int kTDItemDesc = 3;
    public static final int kTDItemCountryOrigin =5;


    public static final int kOIItemSKU = 0;

    public static final int kOIItemQty = 1;

    public static final int kOIItemPrice = 2;

    public static final int kOIItemDesc = 3;

    public static final int kOIItemCustDesc = 4;

    public static final int kOIItemCustValue = 5;

    public static final int KOTOriginCountry = 14;

    public static final int kOIItemBarcode = 13;




    private static final int kOIShipToName = 0;

    private static final int kOIShipToCompany = 1;

    private static final int kOIShipToAdd1 = 2;

    private static final int kOIShipToAdd2 = 3;

    private static final int kOIShipToCity = 4;

    private static final int kOIShipToState = 5;

    private static final int kOIShipToZip = 6;

    private static final int kOIShipToCountry = 7;

    private static final int kOIShipToPhone = 8;


    private static final int kOIReturnName = 9;

    private static final int kOIReturnCompany = 10;

    private static final int kOIReturnAdd1 = 11;

    private static final int kOIReturnAdd2 = 12;

    private static final int kOIReturnCity = 13;

    private static final int kOIReturnState = 14;

    private static final int kOIReturnZip = 15;

    private static final int kOIReturnCountry = 16;

    private static final int kOIReturnPhone = 17;


    private static final int kOI3PartyName = 18;

    private static final int kOI3PartyCompany = 19;

    private static final int kOI3PartyAdd1 = 20;

    private static final int kOI3PartyAdd2 = 21;

    private static final int kOI3PartyCity = 22;

    private static final int kOI3PartyState = 23;

    private static final int kOI3PartyZip = 24;

    private static final int kOI3PartyCountry = 25;

    private static final int kOI3PartyPhone = 26;

    private static final int kOI3PartyAccount = 27;


    private static final int kOIInsuredValue = 28;

    private static final int kOISaturdayDelivery = 29;

    private static final int kOIDeclaredValue = 30;

    private static final int kOICustomsDesc = 31;

    private static final int kOIShipperRef1 = 32;

    private static final int kOIShipperRef2 = 33;


    private static final int kOIShipMethodCode = 34;

    private static final int kOIBillingMethodCode = 35;


    private static final int kOIDefaultCustomsDesc = 36;


    private static final int kOIOrderID = 37;

    private static final int kOIUPSAcct = 38;

    private static final int kOIFedExAcct = 39;


    private static final int kOICOD = 40;

    private static final int kOICODAmt = 41;

    private static final int kOICallTag = 42;

    private static final int kOICallTagNum = 43;


    private static final int kOIClientID = 44;

//added on Nov. 5, 2002

    private static final int kOIRetAddr1 = 45;

    private static final int kOIRetAddr2 = 46;

    private static final int kOIUseDC1stClass = 47;


    private static final int kOIOrderTotal = 48;

    private static final int kOISignatureRequested = 49;

    private static final int kOIPkgTypeO = 50;

    private static final int kOIPkgNumberO = 51;

    private static final int kOIPkgCountO = 52;

    private static final int kOIDepthO = 53;

    private static final int kOIHeightO = 54;

    private static final int kOIWidthO = 55;

    private static final int kOIPackageBarcodeO = 56;
    private static final int kOIBoxId = 57;
    private static final int kOIFedExCustomsAccount = 58;
    private static final int kOIPoNumber = 59;
    private static final int kGroupName = 59;
    private static final int kExtneralPackage = 60;
    private static final int kExternalApiKey = 61;


    private static final int kShipToEmail = 62;


    private static final int kDTPFlag = 63;
    private static final int kOrderDiscount = 64;

    //This number will need to be incremented if you are adding to the tab reader
    private static final int kReferenceSuffix = 65;



    //  private boolean suppressDC = false;

    // private static final String kOIOrderRefTag = "or";


    private static ShipConfig shipper = null;


    private HashMap<String, Object> map = new HashMap<String, Object>();

    // NameAddress consigneeNA = null;

    // NameAddress returnNA = null;

    // NameAddress thirdPartyNA = null;

    // NameAddress clientNA = null;

    //  private SHIPMENTREQUEST request = null;


    public void setFedexLabel(int fedexLabel) {
        map.put("OWDFEDEXLABEL", new Integer(fedexLabel));
    }

    public void setSuppressDC(boolean suppressDC) {
        map.put("OWDSUPPRESSDC", new Boolean(suppressDC));
    }

    public void setConsigneeNA(NameAddress consigneeNA) {
        map.put("OWDCONSIGNEENA", consigneeNA);
    }

    public void setServiceCode(String serviceCode) {
        if (serviceCode.equalsIgnoreCase("TANDATA_USPS.USPS.EXPR_ADDR")) {
            serviceCode = "TANDATA_USPS.USPS.EXPR";
        }
        map.put("OWDSERVICECODE", serviceCode);
    }

    public void setRequest(ShipRequest request) {
        System.out.println("We are settiung the shipment request in map");
        map.put("OWDSHIPMENTREQUEST", request);
    }

    public void setClientNA(NameAddress clientNA) {
        map.put("OWDCLIENTNA", clientNA);
    }

    public void setThirdPartyNA(NameAddress thirdPartyNA) {
        map.put("OWDTHIRDPARTYNA", thirdPartyNA);
    }

    public void setReturnNA(NameAddress returnNA) {
        map.put("OWDRETURNNA", returnNA);
    }

    public int getFedexLabel() {
        if (map.get("OWDFEDEXLABEL") == null) {
            setFedexLabel(0);
        }

        return ((Integer) map.get("OWDFEDEXLABEL")).intValue();
    }

    public boolean isSuppressDC() {
        if (map.get("OWDSUPPRESSDC") == null) {
            setSuppressDC(false);
        }

        return ((Boolean) map.get("OWDSUPPRESSDC")).booleanValue();
    }

    public NameAddress getThirdPartyNA() {
        if (map.get("OWDTHIRDPARTYNA") == null) {
            setThirdPartyNA(new NameAddress());
        }

        return ((NameAddress) map.get("OWDTHIRDPARTYNA"));
    }

    public ShipRequest getRequest() {

        if (map.get("OWDSHIPMENTREQUEST") == null) {
            System.out.println("we have a null request");
            setRequest(null);
        }

        return ((ShipRequest) map.get("OWDSHIPMENTREQUEST"));
    }

    public String getServiceCode() {
        return ((String) map.get("OWDSERVICECODE"));
    }

    public HashMap getMap() {
        return map;
    }

    // String serviceCode = "";

//changed on Nov. 8, 2002, use the above map to set/get these values

//private String package_count = "1";

//private String package_number = "1";

    //load the design
    static JasperReport invoice = null;


    /**
     * Returns true if the order is to be shipped through DP
     *
     * @return
     */


    public NameAddress getConsigneeNA() throws Exception {

        if (map.get("OWDCONSIGNEENA") == null) {
            setConsigneeNA(new NameAddress());
        }

        return ((NameAddress) map.get("OWDCONSIGNEENA"));

    }


    public NameAddress getClientNA() throws Exception {

        if (map.get("OWDCLIENTNA") == null) {
            setClientNA(new NameAddress());
        }

        return ((NameAddress) map.get("OWDCLIENTNA"));

    }


    public NameAddress getReturnNA() throws Exception {

        if (map.get("OWDRETURNNA") == null) {
            setReturnNA(new NameAddress());
        }

        return ((NameAddress) map.get("OWDRETURNNA"));

    }


    public NameAddress getBillingNA() throws Exception {

        if (map.get("OWDBILLINGNA") == null) {
            setBillingNA(new NameAddress());
        }

        return ((NameAddress) map.get("OWDBILLINGNA"));

    }

    public void setBillingNA(NameAddress na) {
        map.put("OWDBILLINGNA", na);
    }


    /**
     * Creates a new instance of ConnectShipShipment and
     * does all initialization work
     */

    public AMPConnectShipShipment() throws Exception {

        shipper = ShipConfig.getConfig();

        //  consigneeNA = new NameAddress();

        clearAddress(getConsigneeNA());

        // returnNA = new NameAddress();

        clearAddress(getReturnNA());

        // thirdPartyNA = new NameAddress();

        clearAddress(getThirdPartyNA());

        // clientNA = new NameAddress();

        clearAddress(getClientNA());
        setClientID(0);


        map.put("CLIENTID", new Integer(0));
        map.put(ShipConfig.WEIGHT, new Double(0.0));

        map.put(ShipConfig.CONSIGNEE_REFERENCE, "");

        map.put(ShipConfig.PACKAGING, "CUSTOM");

        //map.put(ShipConfig.SHIPPER,ShipConfig.kShipperOWD);

        map.put(ShipConfig.TERMS, "SHIPPER");

        map.put(ShipConfig.SHIPDATE, AMPConnectShipShipment.getTanDataDateForToday(0, "",""));

        map.put(ShipConfig.SATURDAY_DELIVERY, "false");

        map.put(ShipConfig.OVERSIZE, "false");

        map.put(ShipConfig.RETURN_DELIVERY, "false");

        map.put(ShipConfig.COD_AMOUNT, "0.00");

        map.put(ShipConfig.THIRD_PARTY_BILLING, "false");

        //map.put(ShipConfig.THIRD_PARTY_BILLING_ACCOUNT,"");

        map.put(ShipConfig.NOFN_SEQUENCE, "1");

        map.put(ShipConfig.NOFN_TOTAL, "1");

        // ////System.out.println("getting current svc codes");System.out.flush();

        if (shipper.getServiceCodes().size() > 0)

            setServiceCode((String) shipper.getServiceCodes().get(0));

        else

            setServiceCode("");

        // ////System.out.println("got current svc codes");System.out.flush();


    }


    /**
     * Resets consignee information that's stored in a <code>NameAddress</code> object
     */

    public void clearAddress(NameAddress cons) {


        cons.setContact("");

        cons.setCompany("");

        cons.setAddress1("");

        cons.setAddress2("");

        cons.setCity("");

        cons.setStateProvince("");

        cons.setPostalCode("");

        cons.setPhone("");

        cons.setResidential(true);

        cons.setCountrySymbol("UNITED_STATES");


    }


    public String getAssignedServiceName() {

        //System.out.println("finding service name for code "+getServiceCode());

        return shipper.getServiceName(getServiceCode());

    }


    public String getAssignedServiceCode() {

        return getServiceCode();

    }

    public String getAssignedCarrierCode() {
        if(!getServiceCode().contains(".")){
            return getServiceCode();
        }
        return getServiceCode().substring(0, getServiceCode().lastIndexOf("."));
    }


    public void setAssignedServiceName(String name) {

        setServiceCode(shipper.getServiceCode(name));

    }


    public void setAssignedServiceCode(String code) {

        setServiceCode(code);

    }

    public void setOriginalAssignedServiceCode(String code) {

        setValue("OWD_ORIG_SERVICE_CODE", code);

    }

    public String getOriginalAssignedServiceCode() {

        return (String) getValue("OWD_ORIG_SERVICE_CODE");

    }


    public String getOriginalAssignedServiceName() {

        //////System.out.println("finding service name for code "+serviceCode);

        return shipper.getServiceName(getOriginalAssignedServiceCode());

    }

    public void setValue(String key, Object val) {

        if (val == null) {

            map.remove(key);

        } else {

            map.put(key, val);

        }

    }


    public Object getValue(String key) {

        return map.get(key);


    }


    public void clearValue(String key) {

        map.remove(key);

    }

    /**
     * Prints out the current key-value pairs stored for the curent shipment
     */

    public void printDictionaryValues() {


        List<String> keylist = new ArrayList<String>(map.keySet());

        Iterator iter = keylist.iterator();

        System.out.println(":::Current dictionary values:::");

        while (iter.hasNext()) {

            String key = (String) iter.next();


            try {

                System.out.println(key + ":" + getValueAsString(key));


            } catch (Exception ex) {

                System.out.println("Exception getting value for " + key);

            }

        }

        System.out.println(":::End dictionary:::");


    }


    public String getValueAsString(String key) throws Exception {

        Object value = map.get(key);


        if (value == null) {

            //////System.out.println("got null for "+key);

            return "";
        }

//	if(value instanceof NameAddress)

//	{

//		return ((NameAddress)value).toTokenString();

//	}

        return map.get(key).toString();

    }


    public static String convertNullToEmptyString(String value) {


        if (value == null) {

            return "";
        }

        return value;

    }


    public boolean isShipped() {

        if (map.get(ShipConfig.MSN) == null || "".equals(map.get(ShipConfig.MSN))) {

            return false;

        } else {

            return true;

        }

    }


    public boolean isInternational() throws Exception {

        String country_symbol = ShipConfig.getConfig().getCountryName(getConsigneeNA().getCountrySymbol()).toUpperCase();

        if (isAPOFPO() && (getAssignedServiceCode().contains("USPS.")|| getAssignedServiceCode().contains("UPS.SP")|| getAssignedServiceCode().contains("CONNECTSHIP_DHLGLOBALMAIL.DHL.P"))) {
            return true;
        }
        if (country_symbol.equals("UNITED STATES") || country_symbol.equals("UNITED_STATES") || country_symbol.equals("USA") || country_symbol.length() < 1) {
            return false;

        } else {
            return true;
        }
    }


    /**
     * Returns true if the order is to be insured,
     * determined by check if the declared value is greater then zero.
     */

    public boolean isInsured() throws Exception {

        String decStr = (String) getValue(ShipConfig.DECLARED_VALUE_AMOUNT);

        if (decStr == null) decStr = "0.00";

        double decval = 0.00;

        try {

            decval = new Double(decStr).doubleValue();

        } catch (Exception exd) {

            decval = 0.0;

        }


        if (decval > 0.0)

            return true;

        else {

            clearValue(ShipConfig.DECLARED_VALUE_AMOUNT);

            return false;

        }

    }


    /**
     * Returns true if order is to a PO box or APO/FPO order
     */

    public boolean isPOAPOFPO() throws Exception{
        if(isAPOFPO()){
            return true;
        }

        Pattern p = Pattern.compile("[Pp](\\.*?)\\s{0,2}[Oo]\\.*?\\s{0,2}[Bb][Oo][Xx]");
        Matcher m = p.matcher(getConsigneeNA().getAddress1());
        if (m.find()) {

            return true;
        }
        m = p.matcher(getConsigneeNA().getAddress2());
        if (m.find()) {

            return true;
        }


        return false;

    }
    /**
     * Returns true if the order is an APO/FPO order
     */

    public boolean isAPOFPO() throws Exception {

        String stater = getConsigneeNA().getStateProvince();

        String city = getConsigneeNA().getCity();
        String country = getConsigneeNA().getCountrySymbol();
        System.out.println("In is apo this is country: " + country);
        System.out.println(stater);
        System.out.println(city);


        if (country.equalsIgnoreCase("USA") || country.equalsIgnoreCase("UNITED_STATES")) {
            //////System.out.println("got cs::"+city+"::"+stater+"::");

            if (stater.equalsIgnoreCase("AE") || stater.equalsIgnoreCase("AA") || stater.equalsIgnoreCase("AP") || city.equalsIgnoreCase("APO") || city.equalsIgnoreCase("FPO") || city.equalsIgnoreCase("MPO") || city.equalsIgnoreCase("DPO")) {


                return true;

            } else {

                return false;

            }
        }
        return false;

    }


    public String getClientName() {

        return "client";

    }


    /**
     * Loads the information of the package shipped back from the ConnectShip Server
     */

    public void loadOrderFromTanData(int lastShipmentIndex) throws Exception {

        map = (HashMap<String, Object>) shipmentList.get(lastShipmentIndex);


        setConsigneeNA((NameAddress) getValue(ShipConfig.CONSIGNEE));

        setThirdPartyNA((NameAddress) getValue(ShipConfig.THIRD_PARTY_BILLING_ADDRESS));

        setReturnNA((NameAddress) getValue("OWDRETURNADDR"));
        setClientID(((Integer) getValue("CLIENTID") == null ? new Integer(0) : (Integer) getValue("CLIENTID")).intValue());
        setValue("CLIENTID", new Integer(getClientID()));
        setAssignedServiceCode((String) getValue("OWDSERVICE"));
        setOriginalAssignedServiceCode(getAssignedServiceCode());
        setItemData((String[][]) getValue(ShipConfig.COMMODITY_CONTENTS));
        setOrderItemData((String[][]) getValue(ShipConfig.COMMODITY_CONTENTS + "_ORDER"));

        /*  ISimpleList items = (ISimpleList) getValue(ShipConfig.COMMODITY_CONTENTS);

      //System.out.println("got items back " + items);

      if (items != null) {

          //COMMODITY_CONTENTS

          if (items.getCount() > 0) {

              //System.out.println(items.getCount() + " items found...");

              itemData = new String[4][items.getCount()];

              for (int i = 0; i < items.getCount(); i++) {

                  IDictionary item = new IDictionaryProxy(items.getItem(i));


                  itemData[0][i] = (String) item.getValue(ShipConfig.PRODUCT_CODE);

                  itemData[1][i] = (String) item.getValue(ShipConfig.QUANTITY);

                  itemData[2][i] = (String) item.getValue(ShipConfig.UNIT_VALUE);

                  itemData[3][i] = (String) item.getValue(ShipConfig.DESCRIPTION);


              }

          } else {

              ////System.out.println("no items found...");

          }

      }  */


    }

    protected String _loadOrder(String orderBarcode, boolean allowShipped) throws Exception {
        ResultSet rs = null;

        //  TimeStamp tsx = new TimeStamp("_loadOrder2 Proc Timer");
        TimeStamp ts = new TimeStamp("loadorder");

        try {

            StringBuffer results = new StringBuffer();

            if (orderBarcode == null || orderBarcode.length() < 1) {
                throw new Exception("Blank barcode");
            }

            orderBarcode = orderBarcode.replace('?', ' ').trim();

            String orderNum = null;
            String revision = "1";


            String boxID = "1";
            String packageBarcode = "";

            if (orderBarcode.startsWith("p") || orderBarcode.startsWith("P")) {
                //is a package barcode
                packageBarcode = orderBarcode.toUpperCase();
                orderBarcode = packageBarcode.substring(packageBarcode.indexOf("*") + 1);
                orderBarcode = orderBarcode.substring(0, orderBarcode.indexOf("*"));
                boxID = packageBarcode.substring(packageBarcode.indexOf("B") + 1);

                if (orderBarcode.toUpperCase().indexOf("R") > 0) {
                    orderNum = orderBarcode.substring(0, orderBarcode.toUpperCase().indexOf("R"));
                    revision = orderBarcode.substring(orderBarcode.toUpperCase().indexOf("R") + 1);

                } else {
                    orderNum = orderBarcode;
                }

            } else {

                if (orderBarcode.toUpperCase().indexOf("R") > 0) {
                    orderNum = orderBarcode.substring(0, orderBarcode.toUpperCase().indexOf("R"));
                    revision = orderBarcode.substring(orderBarcode.toUpperCase().indexOf("R") + 1);
                } else {
                    orderNum = orderBarcode;
                }
            }

            orderBarcode = '*' + orderBarcode + '*';

            //System.out.println("orderBarcode=" + orderBarcode);
            //System.out.println("packageBarcode=" + packageBarcode);
            //System.out.println("orderNum=" + orderNum);
            //System.out.println("revision=" + revision);
            //System.out.println("boxID=" + boxID);
            ts.print("loadorder-lo query start");

            //  String sql2 = "exec sp_loadConnectShipOrderData '" + orderBarcode.trim() + "'";
            PreparedStatement ckup = HibernateSession.getPreparedStatement("exec dbo.sp_virtualPackCalltag '" + orderBarcode.trim() + "'");
            ckup.execute();

            if (packageBarcode.length() > 0) {
                System.out.println("Doing from Package");
                System.out.println(packageBarcode);
                System.out.println(orderBarcode);
                boolean badPack = checkAndFixNoPackageLineItems(orderNum);
                if (!badPack)
                    throw new Exception("There was an error during the pack process. Please contact IT to see if they can fix or Repack the shipment");

                rs = HibernateSession.getResultSet("exec sp_loadConnectShipDataFromPackage '" + packageBarcode.trim() + "','" + orderBarcode.trim() + "'");

                setValue("SHIPMENTBARCODE",packageBarcode.replaceAll("\\*",""));
            } else {
                System.out.println("Doing from Order");

                System.out.println(orderBarcode);
                rs = HibernateSession.getResultSet("exec sp_loadConnectShipDataFromOrder '" + orderBarcode.trim() + "'");

                setValue("SHIPMENTBARCODE",orderBarcode.replaceAll("\\*",""));
            }

            setValue("SHIPMENTBARCODE",packageBarcode);
            ts.print("loadorder-lo query done");


            //note - results is filled in with data on successful return
            int skus = loadShipmentData(allowShipped, rs, results, packageBarcode);

            //Recheck with the database to determine more detail about why no shippable items were found.
            //The stored procedure used above already checks for previous shipments and shipping hold status
            //The if-block below should check with the server and return a different error message/exception
            //for an order that has been replaced with a new version/barcode or an order that has been voided.
            //Orders that get here but don't fall into one of those two categories should get the generic error message
            //currently used below


            if (skus < 1) {
                ResultSet rs1 = HibernateSession.getResultSet("select is_void,order_num_barcode from owd_order  (NOLOCK) where order_num='" + orderNum + "' and is_void=1");
                if (rs1.next()) {
                    int is_void = rs1.getInt(1);
                    String ref = rs1.getString(2);



                    if (is_void == 1) {
                        throw new Exception("Order ref " + orderNum + " has been voided. Return package for putaway back to stock");
                    }  else {
                        throw new Exception("Order ref " + orderNum + " contains no shippable SKUs! Place on DC Hold for review.");
                    }
                } else {
                     rs1 = HibernateSession.getResultSet("select is_void,order_num_barcode,carr_service from owd_order, owd_order_ship_info  (NOLOCK) where order_fkey = order_id and order_num='" + orderNum + "' and is_void=0");
                    if(rs1.next()) {
                        String ref = rs1.getString(2);
                        String currRevision = "1";
                        if (ref.toUpperCase().indexOf("R") > 0 && ref.toUpperCase().indexOf("R") < ref.length()) {
                            currRevision = ref.substring(ref.toUpperCase().indexOf("R") + 1);
                        } else {
                            orderNum = orderBarcode;
                        }
                        if(rs1.getString(3).equals("LTL")){
                            throw new Exception("This is an LTL order. Please follow standard procedures");
                        }else if (!(revision.equals(currRevision))) {
                            throw new Exception("Order ref " + orderNum + " has been revised and has a new packing slip! Place on DC Hold for review.");
                        }else  {
                            throw new Exception("Order ref " + orderNum + " contains no shippable SKUs! Place on DC Hold for review.");
                        }

                    }
                    System.out.println("Well something is going on here. What could it bee?");

                    throw new Exception("Order ref " + orderNum + " not valid! Check order number/barcode or rescan.");
                }


            }

            ts.print("loadorder-lo items done");

            return "SUCCESS\r\n" + results.toString();
//   tsx.print("_loadOrder2 Proc Timer");
        } catch (Throwable th) {


            th.printStackTrace();

            throw new Exception(th);
        } finally {
            try {
                HibernateSession.closeSession();
            } catch (Exception ex) {
            }
        }

        // tsx.print("_loadOrder2 Proc Timer");


    }

    //refactored into own method for testing
    private int loadShipmentData(boolean allowShipped, ResultSet rs, StringBuffer results, String packageBarcode) throws Exception {
        int skus = 0;
        StringBuffer liresults = new StringBuffer();


        while (rs.next()) {

        System.out.println("shipped_packs="+rs.getInt(63));
            if (!(allowShipped)) {
                if ((packageBarcode.length() == 0 && rs.getInt(63) != 0) || (rs.getInt(63) >= rs.getInt(58))) {
                    throw new Exception("Order has already been shipped! Verify order number and barcode. Find and void previous shipments in Search tab if needed.");
                }
                if (rs.getInt(75) != 0 && packageBarcode.equals(rs.getString(74))) {
                    throw new Exception("This package has already been shipped! Verify package ID and barcode. ");

                }

            }
            if (rs.getInt(64) != 0) {
                throw new Exception("This client is on shipping hold and cannot ship! Place in shipping hold area.");
            }
            if (rs.getInt(66) != 0) {

                throw new Exception("This order is on DC Hold - DC Hold must be cleared before shipping!");

            }
            System.out.println("before if 0");
            if (skus == 0) {
                System.out.println("after if 0 ");
                results.append(removeAccents(rs.getString(1).replaceAll("\"", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "")));

                for (int i = 2; i <= 48; i++) {
                    System.out.println(i + " : " + rs.getString(i));
                    results.append(removeAccents("\t" + rs.getString(i).replaceAll("\"", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "")));

                }
                checkForSuffix(getClientID()+"",rs.getString(49));

                results.append("\t" + OWDUtilities.roundFloat(rs.getFloat(65), 2));
                results.append("\t" + rs.getInt(67));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                results.append("\t" + rs.getString(68));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                results.append("\t" + rs.getString(69));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                results.append("\t" + rs.getString(70));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                results.append("\t" + rs.getString(71));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                results.append("\t" + rs.getString(72));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                results.append("\t" + rs.getString(73));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                results.append("\t" + rs.getString(74));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                results.append("\t" + rs.getString(79));     //Box Id
                results.append("\t" + rs.getString(80));     //  Fed Ex Customs account number
                results.append("\t" + rs.getString(82));//  groiupname
                results.append("\t" + rs.getString(83));
                results.append("\t" + rs.getString(84)); //External api key
                results.append("\t" + rs.getString(85)); // shipt to email
                results.append("\t" + rs.getString(87));// DTP flag
                System.out.println("79 : " + rs.getString(79));
                System.out.println("83 : " + rs.getString(83));
                System.out.println("82 : " + rs.getString(82));
                System.out.println("85 : " + rs.getString(85));
                System.out.println("86 : " + rs.getString(86));

                //NOTE: referenceweight call returns two columns - min and max weights, with a tab in between the two values. Unfounf refeerences return 0.00 for both values
                results.append("\t" + "0.00\t0.00");

                results.append("\r\n");
            }


            skus++;
            String sku = rs.getString(49);
            checkForSuffix(getClientID()+"",rs.getString(49));
            System.out.println("sku: " + sku);
            if (sku != null && sku.trim().length() > 0) {
                liresults.append(rs.getString(49));
                for (int i = 50; i < 63; i++) {
                    System.out.println(i + " : " + rs.getString(i));
                    liresults.append(removeAccents("\t" + rs.getString(i).replaceAll("\"", " ").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", " ")));
                }

                liresults.append("\t" + rs.getString(86));
                liresults.append("\r\n");
            }


        }
        rs.close();
        results.append(liresults);
        String[] test = results.toString().split("\t");
        return skus;
    }

    public static String removeAccents(String text) {
        return text == null ? null :
                Normalizer.normalize(text, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static void main(String args[]) {
        try {

           /* AMPConnectShipShipment ccs = new AMPConnectShipShipment();

            String orderBarcode = "15463044";
            StringBuffer results = new StringBuffer();

            ResultSet rs = HibernateSession.getResultSet("exec sp_loadConnectShipDataFromOrder '" + orderBarcode.trim() + "'");

            int skus = ccs.loadShipmentData(false, rs, results, orderBarcode);

            System.out.println("skus:"+skus);
            System.out.println("data:"+results);*/

           /* String test = "BSBS\"aksjkjasdjNÿdam ";
            System.out.println(test);
            test = removeAccents(test);
            System.out.println(test);*/
           ///        AMPConnectShipShipment ccs = new AMPConnectShipShipment();
            //ccs._loadOrder("18530169R1",false);

            //System.out.println(checkAndFixNoPackageLineItems("19120897"));
              AMPConnectShipShipment ccs = new AMPConnectShipShipment();
              ccs.loadOrderFromOrderReference("10995778",true,"DC1");
                System.out.println(ccs.getValue(ShipConfig.THIRD_PARTY_BILLING));
                        System.out.println(ccs.getItemData()[0].length);
                         System.out.println(ccs.getItemData()[ConnectShipShipment.kTDItemDesc][0]);
              System.out.println(ccs.getItemData()[ConnectShipShipment.kTDItemDesc][1]);
        /*    AMPConnectShipShipment shipment = new AMPConnectShipShipment();
            List<AMPConnectShipShipment> packlist = new ArrayList<AMPConnectShipShipment>();
            shipment.loadOrderFromOrderReference("p5365189*14169277*b1", true,"DC1");

           // shipRequest.setSHIPPER(ManifestingManager.getLiveShipperForLocation(facility,shipment.getClientID(),Integer.parseInt((String)shipment.getValue("BOX_ID"))));

            shipment.setValue("CURRENT_FACILITY", "DC1");
            shipment.setValue(ShipConfig.CURRENT_SHIPPER, "OWD");
            shipment.setValue(ShipConfig.SHIPPER, "OWD");
            shipment.setValue("LABELPRINTER_CODE", "ZM4PLUS");
            shipment.setValue(ShipConfig.WEIGHT, new Double(2));
            shipment.setValue(ShipConfig.NOFN_SEQUENCE, ((String)shipment.getValue("PACKAGE_BARCODE")).substring(((String)shipment.getValue("PACKAGE_BARCODE")).indexOf("b") + 1));
            shipment.setValue(ShipConfig.NOFN_TOTAL, 2);
            packlist.add(shipment);
            shipment = new AMPConnectShipShipment();

            shipment.loadOrderFromOrderReference("p5365189*14169277*b2", true,"DC1");

            // shipRequest.setSHIPPER(ManifestingManager.getLiveShipperForLocation(facility,shipment.getClientID(),Integer.parseInt((String)shipment.getValue("BOX_ID"))));

            shipment.setValue("CURRENT_FACILITY", "DC1");
            shipment.setValue(ShipConfig.CURRENT_SHIPPER, "OWD");
            shipment.setValue(ShipConfig.SHIPPER, "OWD");
            shipment.setValue("LABELPRINTER_CODE", "ZM4PLUS");
            shipment.setValue(ShipConfig.WEIGHT, new Double(2));
            shipment.setValue(ShipConfig.NOFN_SEQUENCE, ((String)shipment.getValue("PACKAGE_BARCODE")).substring(((String)shipment.getValue("PACKAGE_BARCODE")).indexOf("b") + 1));
            shipment.setValue(ShipConfig.NOFN_TOTAL, 2);
            packlist.add(shipment);
             shipment.ship(1,2,packlist,"OWD","DC1");
            shipment.*/
           // System.out.println(checkAndFixNoPackageLineItems("12196410"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadOrderFromOrderReference(String orderBarcode, boolean allowShipped, String LocationCode) throws Exception {

        System.out.println("!");
        orderBarcode = orderBarcode.trim();
        TabReader orderInfo = null;
        returnAddressBean defaultReturnAddress = getDefaultReturnAddress(LocationCode, HibernateSession.currentSession());
        ////System.out.println("loading order reference "+ShipConfig.kNetServicesServer+" -> "+orderReference);


        orderInfo = new TabReader(new BufferedReader(new StringReader(_loadOrder(orderBarcode, allowShipped))), false);

        for (int i = 0; i < orderInfo.getRowCount(); i++) {
            for (int j = 0; j < orderInfo.getRowSize(i); j++) {
                System.out.print(orderInfo.getStrValue(j, i, "") + "|(" + j + "," + i + "):");
            }
            //System.out.println("\n");
        }

        System.out.println("2");
        if (orderInfo.getRowCount() < 2)

            throw new Exception("Order info not found: rowcount=" + orderInfo.getRowCount());

        if (orderInfo.getRowSize(0) > 1 || orderInfo.getRowSize(0) < 1)

            throw new Exception("Order info not found: rowsize=" + orderInfo.getRowSize(0));

        String response = orderInfo.getStrValue(0, 0, "ERROR");

        ////System.out.println(response==null?"no response":response);


        if (response.equals("ERROR"))

            throw new Exception(orderInfo.getStrValue(0, 1, "Unknown"));

        //added on Nov. 5, 2002
        setValue("USEDC_FIRSTCLASS", new Boolean(orderInfo.getIntValue(kOIUseDC1stClass, 1, 0) != 0));
        setValue("BOX_ID", orderInfo.getStrValue(kOIBoxId, 1, "1"));
        setValue(ShipConfig.FED_EX_CUSTOMS_ACCOUNT, orderInfo.getStrValue(kOIFedExCustomsAccount, 1, ""));
        System.out.println("WEll did the fedex stuff get set");
        System.out.println(orderInfo.getStrValue(kOIFedExCustomsAccount, 1, ""));
        System.out.println(getValue(ShipConfig.FED_EX_CUSTOMS_ACCOUNT));
        ////System.out.println("useDC_1stClass: "+useDC_1stClass);
        setValue(ShipConfig.GROUP_NAME,orderInfo.getStrValue(kGroupName,1,""));
        setValue(ShipConfig.EXTERNAL_PACKAGE,orderInfo.getStrValue(kExtneralPackage,1,""));
        setValue(ShipConfig.EXTERNAL_API_KEY,orderInfo.getStrValue(kExternalApiKey,1,""));
        setValue(ShipConfig.SHIP_TO_EMAIL,orderInfo.getStrValue(kShipToEmail,1,""));
        setValue(ShipConfig.SHIP_DTP_FLAG,orderInfo.getStrValue(kDTPFlag,1,""));
        setValue(ShipConfig.DISCOUNT,orderInfo.getStrValue(kOrderDiscount,1,""));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Discount Value: " + getValue(ShipConfig.DISCOUNT));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println("Group name: " + getValue(ShipConfig.GROUP_NAME));
        System.out.println("3");
        System.out.println("External Pakcagte" + getValue(ShipConfig.EXTERNAL_PACKAGE));

        setValue(ShipConfig.OWD_ORDER_TOTAL, new Float(orderInfo.getFloatValue(kOIOrderTotal, 1, 0)));
        setValue(ShipConfig.OWD_SIG_REQUESTED, new Boolean(orderInfo.getIntValue(kOISignatureRequested, 1, 0) == 1));
        //setValue(ShipConfig.OWD_PO_NUMBER,orderInfo.getStrValue(kOIPoNumber,1,""));

        //System.out.println("kOIOrderTotal = " + orderInfo.getStrValue(kOIOrderTotal, 1, "bad"));

        //System.out.println("kOISignatureRequested = " + orderInfo.getStrValue(kOISignatureRequested, 1, "bad"));

        //System.out.println("kOIPkgTypeO = " + orderInfo.getStrValue(kOIPkgTypeO, 1, "bad"));

        //System.out.println("kOIPkgNumberO = " + orderInfo.getStrValue(kOIPkgNumberO, 1, "bad"));

        //System.out.println("kOIPkgCountO = " + orderInfo.getStrValue(kOIPkgCountO, 1, "bad"));

        //System.out.println("kOIDepthO = " + orderInfo.getStrValue(kOIDepthO, 1, "bad"));

        //System.out.println("kOIHeightO = " + orderInfo.getStrValue(kOIHeightO, 1, "bad"));

        //System.out.println("kOIWidthO = " + orderInfo.getStrValue(kOIWidthO, 1, "bad"));
        System.out.println("What is the package barcode??");
        System.out.println("kOIPackageBarcodeO = " + orderInfo.getStrValue(kOIPackageBarcodeO, 1, "bad"));
        if (orderBarcode.contains("p")) {
            System.out.println("Setting PACKAGE_BARCODE to: " + orderBarcode);
            setValue("PACKAGE_BARCODE", orderBarcode);
        } else {
            System.out.println("not a package barcode");

            setValue("PACKAGE_BARCODE", orderInfo.getStrValue(kOIPackageBarcodeO, 1, "bad"));
        }
        setValue(ShipConfig.PACKAGING, orderInfo.getStrValue(kOIPkgTypeO, 1, "CUSTOM"));
        setValue(ShipConfig.NOFN_SEQUENCE, orderInfo.getStrValue(kOIPkgNumberO, 1, "1"));
        setValue(ShipConfig.NOFN_TOTAL, orderInfo.getStrValue(kOIPkgCountO, 1, "1"));


        setValue("OWD_CUSTOMS_DOCS", "0");

        // setValue("OWD_REF_LBS", orderInfo.getFloatValue(57, 1, 0.00f));
        setValue("OWD_REF_LBS_MIN", orderInfo.getFloatValue(57, 1, 0.00f));
        setValue("OWD_REF_LBS_MAX", orderInfo.getFloatValue(58, 1, 0.00f));
        //System.out.println("Got REFERENCE WEIGHT MIN " + getValueAsString("OWD_REF_LBS_MIN"));
        //System.out.println("Got REFERENCE WEIGHT MAX " + getValueAsString("OWD_REF_LBS_MAX"));
        System.out.println("4");
        if (new Integer((String) getValue(ShipConfig.NOFN_TOTAL)).intValue() > 1 && !orderBarcode.toUpperCase().startsWith("P")) {
            throw new Exception("This package is part of a multi-package shipment. You must scan the white package barcode to ship it.");
        } else {
            System.out.println("la ti da ti da");
            setValue(ShipConfig.NOFN_SEQUENCE, getValueAsString("PACKAGE_BARCODE").substring(getValueAsString("PACKAGE_BARCODE").indexOf("b") + 1));
        }
        System.out.println("5");
        if (new Integer((String) getValue(ShipConfig.NOFN_TOTAL)).intValue() > 1 && orderBarcode.toUpperCase().startsWith("P")) {

            //multipackage shipment scanned with package barcode
            //get sequence number from package barcode
            System.out.println("????????????????");
            // throw new Exception();
            //  setValue(ShipConfig.NOFN_SEQUENCE, "" + ShipperApp.getNextPackageSequenceNumber(orderInfo.getStrValue(kOIShipperRef1, 1, "")));
        }

        //System.out.println("Read package sequence number:"+getValue(ShipConfig.NOFN_SEQUENCE));
        //System.out.println("Read package count total number:"+getValue(ShipConfig.NOFN_TOTAL));
        String old_code = orderInfo.getStrValue(kOIShipMethodCode, 1, "CONNECTSHIP_UPS.UPS.GND");
        //System.out.println("got old code="+old_code);
        //System.out.println("got translated code "+ShipConfig.getConfig().getTranslatedServiceCode(old_code));
        setAssignedServiceCode(ShipConfig.getConfig().getTranslatedServiceCode(old_code));
        setOriginalAssignedServiceCode(getAssignedServiceCode());

        System.out.println("6");

        if (getAssignedServiceCode().toUpperCase().indexOf("FEDEX") >= 0 || getAssignedServiceCode().toUpperCase().indexOf("FDX") >= 0) {
            //correct fedex to 2" minimum dimension measurements per Fedex

            float depth = orderInfo.getFloatValue(kOIDepthO, 1, 0.00f);
            float width = orderInfo.getFloatValue(kOIWidthO, 1, 0.00f);
            float height = orderInfo.getFloatValue(kOIHeightO, 1, 0.00f);
            if (depth < 2.00f) {
                depth = 2.00f;
            }
            if (width < 2.00f) {
                width = 2.00f;
            }
            if (height < 2.00f) {
                height = 2.00f;
            }

            setValue(ShipConfig.DIMENSION, "" + depth +
                    'x' + height + 'x' +
                    width);

            setValue("WIDTH_INCHES", width + "");
            setValue("DEPTH_INCHES", depth + "");
            setValue("HEIGHT_INCHES", height + "");
        } else {
            setValue(ShipConfig.DIMENSION, orderInfo.getStrValue(kOIDepthO, 1, "0.00") +
                    'x' + orderInfo.getStrValue(kOIHeightO, 1, "0.00") + 'x' +
                    orderInfo.getStrValue(kOIWidthO, 1, "0.00"));

            setValue("WIDTH_INCHES", new Double(orderInfo.getStrValue(kOIWidthO, 1, "0.00")));
            setValue("DEPTH_INCHES", new Double(orderInfo.getStrValue(kOIDepthO, 1, "0.00")));
            setValue("HEIGHT_INCHES", new Double(orderInfo.getStrValue(kOIHeightO, 1, "0.00")));

        }



        String state = orderInfo.getStrValue(kOIShipToState, 1, "");

        //System.out.println("OG country=" + orderInfo.getStrValue(kOIShipToCountry, 1, ""));

        System.out.println("12");
        String country = shipper.getCountryCode(orderInfo.getStrValue(kOIShipToCountry, 1, "").toUpperCase());
        System.out.println("13");
        //System.out.println("translated country=" + country);

        if (state.equalsIgnoreCase("PR")) {

            if (country.equalsIgnoreCase("USA") || country.equalsIgnoreCase("UNITED_STATES") || country.equalsIgnoreCase("UNITED STATES")) {

                country = "PUERTO_RICO";

            }

        }

        System.out.println("14");
        if (state.equalsIgnoreCase("GU")) {

            if (country.equalsIgnoreCase("USA") || country.equalsIgnoreCase("UNITED_STATES") || country.equalsIgnoreCase("UNITED STATES")) {

                country = "GUAM";

            }

        }


        if (state.equalsIgnoreCase("VI")) {

            if (country.equalsIgnoreCase("USA") || country.equalsIgnoreCase("UNITED_STATES") || country.equalsIgnoreCase("UNITED STATES")) {

                country = "US_VIRGIN_ISLANDS";

            }

        }

        //Fill our NameAddress with the consignee values from the fields

        getConsigneeNA().setContact(orderInfo.getStrValue(kOIShipToName, 1, ""));

        getConsigneeNA().setCompany(orderInfo.getStrValue(kOIShipToCompany, 1, ""));

        getConsigneeNA().setAddress1(orderInfo.getStrValue(kOIShipToAdd1, 1, ""));

        getConsigneeNA().setAddress2(orderInfo.getStrValue(kOIShipToAdd2, 1, ""));

        getConsigneeNA().setCity(orderInfo.getStrValue(kOIShipToCity, 1, ""));

        getConsigneeNA().setStateProvince(state);

        // String ozip = orderInfo.getStrValue(kOIShipToZip, 1, "");

        System.out.println("14");
        try {

            getConsigneeNA().setCountrySymbol(country);

        } catch (Exception ex) {

            throw new Exception("Country code \"" + country + "\" not valid for consignee");

        }

        //added on Nov. 8 to adapt


        getConsigneeNA().setPostalCode(orderInfo.getStrValue(kOIShipToZip, 1, ""));

        //////System.out.println("zip="+getConsigneeNA().getPostalCode());

        getConsigneeNA().setResidential((((String) getConsigneeNA().getCompany()).equals("") || ((String) getConsigneeNA().getCompany()).equals(".") || ((String) getConsigneeNA().getContact()).equals((String) getConsigneeNA().getCompany()) ? true : false));

        getConsigneeNA().setPhone(orderInfo.getStrValue(kOIShipToPhone, 1, ""));


        if (!(isInternational())) {

            if (getConsigneeNA().getPhone().length() < 10) {

                getConsigneeNA().setPhone("8662091413");

            }

        } else if (getConsigneeNA().getPhone().trim().length() < 1) {

            getConsigneeNA().setPhone("18662091413");


        }

        getConsigneeNA().setPhone(getConsigneeNA().getPhone().replace('.', ' '));

        getConsigneeNA().setPhone(getConsigneeNA().getPhone().replace('+', ' '));


        getReturnNA().setContact(orderInfo.getStrValue(kOIReturnName, 1, ""));

        getReturnNA().setCompany(orderInfo.getStrValue(kOIReturnCompany, 1, ""));

        //***** changed on Nov. 5, 2002

        ////System.out.println("custom return: "+orderInfo.getStrValue(kOIRetAddr1,1,"")+":"+orderInfo.getStrValue(kOIRetAddr2,1,""));
        ////System.out.println("custom return: "+orderInfo.getStrValue(kOIReturnName,1,"")+":"+getReturnNA().getContact());
        ////System.out.println("custom return: "+orderInfo.getStrValue(kOIReturnCompany,1,"")+":"+getReturnNA().getCompany());

        System.out.println("15");
        if (orderInfo.getStrValue(kOIRetAddr1, 1, "").length() > 0 && orderInfo.getStrValue(kOIRetAddr2, 1, "").length() > 0) {
            ////System.out.println("using client's return address");
               /*
                  getReturnNA().setAddress1(orderInfo.getStrValue(kOIRetAddr1,1,""));
                  getReturnNA().setAddress1(orderInfo.getStrValue(kOIRetAddr2,1,""));
                  getReturnNA().setCity(orderInfo.getStrValue(kOIReturnCity,1,""));
              getReturnNA().setStateProvince(orderInfo.getStrValue(kOIReturnState,1,""));
                  getReturnNA().setCountrySymbol(orderInfo.getStrValue(kOIReturnCountry,1,""));
                  getReturnNA().setPostalCode(orderInfo.getStrValue(kOIReturnZip,1,""));
                  getReturnNA().setResidential("FALSE") ;
                   **/


            getReturnNA().setAddress1(orderInfo.getStrValue(kOIRetAddr1, 1, ""));

            getReturnNA().setAddress2("");

            String cszStr = orderInfo.getStrValue(kOIRetAddr2, 1, "");
            System.out.println(cszStr);
            System.out.println("15A");
            String city = cszStr.substring(0, cszStr.indexOf(","));
            System.out.println("15b");
            cszStr = cszStr.substring(cszStr.indexOf(",") + 1).trim();
            System.out.println("15c");
            String rstate = cszStr.substring(0, cszStr.indexOf(" "));
            System.out.println("15d");
            cszStr = cszStr.substring(cszStr.indexOf(" ") + 1).trim(); //zip
            System.out.println("15e");
            getReturnNA().setCity(city);

            getReturnNA().setStateProvince(rstate);

            try {

                getReturnNA().setCountrySymbol("UNITED_STATES");

            } catch (Exception ex) {

                throw new Exception("Country code \"" + orderInfo.getStrValue(kOIShipToCountry, 1, "") + "\" not valid for client address");

            }

            getReturnNA().setPostalCode(cszStr);

            getReturnNA().setResidential(false);
            System.out.println("16");

        } else {

            ////System.out.println("using one world's address");
            getReturnNA().setAddress1(defaultReturnAddress.getAddress1());

            getReturnNA().setAddress2("");

            getReturnNA().setCity(defaultReturnAddress.getCity());

            getReturnNA().setStateProvince(defaultReturnAddress.getState());
            getReturnNA().setPhone(defaultReturnAddress.getPhone());

            try {

                getReturnNA().setCountrySymbol("UNITED_STATES");

            } catch (Exception ex) {

                throw new Exception("Country code \"" + orderInfo.getStrValue(kOIShipToCountry, 1, "") + "\" not valid for client address");

            }

            getReturnNA().setPostalCode(defaultReturnAddress.getZip());

            getReturnNA().setResidential(false);

        }
        System.out.println("lalala");
        // ******


        if(getValue(ShipConfig.GROUP_NAME).toString().equalsIgnoreCase("iqvc")){
            getReturnNA().setContact(orderInfo.getStrValue(kOIReturnName,1,""));
            getReturnNA().setAddress1(orderInfo.getStrValue(kOIReturnAdd1,1,""));
            getReturnNA().setCity(orderInfo.getStrValue(kOIReturnCity,1,""));
            getReturnNA().setPostalCode(orderInfo.getStrValue(kOIReturnZip,1,""));
            getReturnNA().setStateProvince(orderInfo.getStrValue(kOIReturnState,1,""));
        }
        getReturnNA().setPhone(orderInfo.getStrValue(kOIReturnPhone, 1, ""));

        //Fill client Consignee

        getClientNA().setContact(orderInfo.getStrValue(kOIReturnName, 1, ""));

        getClientNA().setCompany(orderInfo.getStrValue(kOIReturnCompany, 1, ""));

        getClientNA().setAddress1(orderInfo.getStrValue(kOIReturnAdd1, 1, ""));

        getClientNA().setAddress2(orderInfo.getStrValue(kOIReturnAdd2, 1, ""));

        getClientNA().setCity(orderInfo.getStrValue(kOIReturnCity, 1, ""));

        getClientNA().setStateProvince(orderInfo.getStrValue(kOIReturnState, 1, ""));

        try {

            getClientNA().setCountrySymbol(shipper.getCountryCode(orderInfo.getStrValue(kOIReturnCountry, 1, "").toUpperCase()));

        } catch (Exception ex) {

            throw new Exception("Country code \"" + orderInfo.getStrValue(kOIReturnCountry, 1, "") + "\" not valid for client billing address");

        }

        getClientNA().setPostalCode(orderInfo.getStrValue(kOIReturnZip, 1, ""));

        getClientNA().setResidential(false);

        getClientNA().setPhone(orderInfo.getStrValue(kOIReturnPhone, 1, ""));

        //Fill our NameAddress with the consignee values from the fields

/*

    if(orderInfo.getStrValue(kOI3PartyZip,1,"").trim().length()>0)

    {

 */
        System.out.println("17");

        String name = (String) orderInfo.getStrValue(kOI3PartyName, 1, "");

        getThirdPartyNA().setContact(name);

        getThirdPartyNA().setCompany(orderInfo.getStrValue(kOI3PartyCompany, 1, ""));

        getThirdPartyNA().setAddress1(orderInfo.getStrValue(kOI3PartyAdd1, 1, ""));

        getThirdPartyNA().setAddress2(orderInfo.getStrValue(kOI3PartyAdd2, 1, ""));

        getThirdPartyNA().setCity(orderInfo.getStrValue(kOI3PartyCity, 1, ""));

        getThirdPartyNA().setStateProvince(orderInfo.getStrValue(kOI3PartyState, 1, ""));


        try {

            getThirdPartyNA().setCountrySymbol(shipper.getCountryCode(orderInfo.getStrValue(kOI3PartyCountry, 1, "").toUpperCase()));

        } catch (Exception ex) {

            throw new Exception("Country code \"" + orderInfo.getStrValue(kOI3PartyCountry, 1, "") + "\" not valid for third party billing address");

        }

        getThirdPartyNA().setPostalCode(orderInfo.getStrValue(kOI3PartyZip, 1, ""));

        getThirdPartyNA().setResidential((((String) getThirdPartyNA().getCompany()).equals("") || ((String) getThirdPartyNA().getCompany()).equals(".") || ((String) getThirdPartyNA().getCompany()).equals((String) getThirdPartyNA().getContact()) ? true : false));

        getThirdPartyNA().setPhone(orderInfo.getStrValue(kOI3PartyPhone, 1, ""));


        System.out.println("18");
        if ("".equals(getThirdPartyNA().getContact())) {

            getThirdPartyNA().setContact(getThirdPartyNA().getCompany());

        }


        if ("".equals(getThirdPartyNA().getPhone())) {

            getThirdPartyNA().setPhone("8662091413");

        }

           /*testing

               ////System.out.println("in loadOrderForReference...\n");

               ////System.out.println(getThirdPartyNA().getContact()) ;

                ////System.out.println(getThirdPartyNA().getCompany()) ;

               ////System.out.println(getThirdPartyNA().getAddress1());

               ////System.out.println(getThirdPartyNA().getAddress2());

               ////System.out.println(getThirdPartyNA().getCity());

               ////System.out.println(getThirdPartyNA().getStateProvince());

               ////System.out.println(getThirdPartyNA().getCountrySymbol());

               ////System.out.println(getThirdPartyNA().getPostalCode()) ;

               ////System.out.println(getThirdPartyNA().getRESIDENTIAL()) ;

               ////System.out.println(getThirdPartyNA().getPhone());

           */


        setValue(ShipConfig.DECLARED_VALUE_AMOUNT, orderInfo.getStrValue(kOIInsuredValue, 1, "0.00"));

////System.out.println("got declared value "+getValue(ShipConfig.DECLARED_VALUE_AMOUNT));

        if (getValue(ShipConfig.DECLARED_VALUE_AMOUNT).equals("0.00")) {

            clearValue(ShipConfig.DECLARED_VALUE_AMOUNT);

        }

        String test = orderInfo.getStrValue(kOISaturdayDelivery, 1, "");
        setValue(ShipConfig.SATURDAY_DELIVERY, ("1".equalsIgnoreCase(orderInfo.getStrValue(kOISaturdayDelivery, 1, "")) ? "true" : "false"));

        System.out.println("19");
        if ("1".equalsIgnoreCase(orderInfo.getStrValue(kOICOD, 1, "")) || "TRUE".equalsIgnoreCase(orderInfo.getStrValue(kOICOD, 1, ""))) {

            setValue(ShipConfig.IS_COD, "TRUE");

            setValue(ShipConfig.COD_AMOUNT, orderInfo.getStrValue(kOICODAmt, 1, "100000.00"));

            setValue(ShipConfig.COD_PAYMENT_METHOD, "" + Constants.cpmPersonalCheck);

        }

//setValue(ShipConfig.RETURN_DELIVERY,(1==orderInfo.getIntValue(kOICallTag,1,0)?"true":"false"));

        if ("1".equalsIgnoreCase(orderInfo.getStrValue(kOICallTag, 1, "")) || "TRUE".equalsIgnoreCase(orderInfo.getStrValue(kOICallTag, 1, ""))) {

            //    setValue(ShipConfig.RETURN_DELIVERY,"true");
            setValue(ShipConfig.CALLTAG, "TRUE");
            //setValue(ShipConfig.CALLTAG_NUMBER,);
            String description = orderInfo.getStrValue(kOICallTagNum, 1, "");
            setValue(ShipConfig.CALLTAG_DESCRIPTION, description.equals("") ? "call tag" : description);

        }


        if (isInternational()) {

            setValue(ShipConfig.DECLARED_VALUE_CUSTOMS, orderInfo.getStrValue(kOIDeclaredValue, 1, "0.00"));

        }


        setValue(ShipConfig.SHIPPER_REFERENCE, orderInfo.getStrValue(kOIShipperRef1, 1, "")+getValueAsString(ShipConfig.REFERENCE_SUFFIX));
        setValue(ShipConfig.OWD_ORDER_NUM, orderInfo.getStrValue(kOIShipperRef1, 1, ""));


        setValue(ShipConfig.CONSIGNEE_REFERENCE, orderInfo.getStrValue(kOIShipperRef2, 1, ""));


        setValue(ShipConfig.TERMS, orderInfo.getStrValue(kOIBillingMethodCode, 1, "SHIPPER"));


        if (orderInfo.getStrValue(kOIBillingMethodCode, 1, "SHIPPER").equals(".")) {

            setValue(ShipConfig.TERMS, "SHIPPER");

        }


        if (isInternational() && (getAssignedServiceCode().indexOf("UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX.") >= 0) && getValue(ShipConfig.TERMS).equals("SHIPPER") && !getAssignedServiceCode().contains("UPS.SP")) {

            String country_symbol = ShipConfig.getConfig().getCountryName(getConsigneeNA().getCountrySymbol()).toUpperCase();

            //System.out.println("Country symbol before set payment terms to DDU: " + country_symbol);

            if (country_symbol.equalsIgnoreCase("PUERTO_RICO") || country_symbol.equalsIgnoreCase("PUERTO RICO") || country_symbol.equalsIgnoreCase("US_VIRGIN_ISLANDS") || country_symbol.equalsIgnoreCase("US VIRGIN ISLANDS")) {

                if(!getAssignedServiceCode().contains("UPS")){
                    setValue(ShipConfig.TERMS, "DDU");
                }


            } else {

                setValue(ShipConfig.TERMS, "DDU");

            }

        }

        if (getAssignedServiceCode().indexOf("FEDEX.") >= 0 && (getConsigneeNA().getCountrySymbol().equalsIgnoreCase("PUERTO_RICO"))) {
            //System.out.println("setting USA dest for ship method " + getAssignedServiceCode());
            //       getConsigneeNA().setCountrySymbol("UNITED_STATES");
            // setValue(ShipConfig.TERMS, "DDU");
        }


        String cdesc = orderInfo.getStrValue(kOICustomsDesc, 1, "");

        String dcdesc = orderInfo.getStrValue(kOIDefaultCustomsDesc, 1, "");

        setValue(ShipConfig.DEFAULT_CUSTOMS_DESCRIPTION, dcdesc);

        setValue(ShipConfig.DESCRIPTION, (cdesc.length() > 0 ? cdesc : dcdesc));


        if (((String) getValue(ShipConfig.DESCRIPTION)).length() < 1)

            setValue(ShipConfig.DESCRIPTION, "Merchandise");


        String upsAcct = orderInfo.getStrValue(kOIUPSAcct, 1, "");

        String fedexAcct = orderInfo.getStrValue(kOIFedExAcct, 1, "");

        String acct3rdParty = orderInfo.getStrValue(kOI3PartyAccount, 1, "").trim();

       System.out.println("third party account No.: "+orderInfo.getStrValue(kOI3PartyAccount,1,"").trim());

        System.out.println("ups account No.: "+upsAcct);

        System.out.println("fedex account No.: "+fedexAcct);

        System.out.println("20");
        if (acct3rdParty.length() > 0 && acct3rdParty.toUpperCase().indexOf("NULL") < 0) {
            //check if it's "freight collect", but it's definitely not "third party billing"
            if (getValue(ShipConfig.TERMS).equals("NameAddress")) {
                //It is "freight collect", collect corresponding values and mark so
                setValue(ShipConfig.CONSIGNEE_ACCOUNT, orderInfo.getStrValue(kOI3PartyAccount, 1, ""));

                setValue(ShipConfig.FREIGHT_COLLECT, "true");

                setValue(ShipConfig.THIRD_PARTY_BILLING, "false");

                clearValue(ShipConfig.THIRD_PARTY_BILLING_ACCOUNT);


            } else {

                setValue(ShipConfig.THIRD_PARTY_BILLING, "TRUE");
                setValue(ShipConfig.THIRD_PARTY_BILLING_ACCOUNT, orderInfo.getStrValue(kOI3PartyAccount, 1, ""));
                //check if this is third party billing
                if (acct3rdParty.equals(upsAcct) || acct3rdParty.equals(fedexAcct)) {

                    setThirdPartyNA(getClientNA());
                    if (getThirdPartyNA().getCompany().length() < 2) {

                        throw new Exception("Client name empty didn't get pulled through!"); //use client info

                    }

                } else {

                    if (getThirdPartyNA() == null || getThirdPartyNA().getCompany().length() < 1 || getThirdPartyNA().getCompany().indexOf("NULL") >= 0 || getThirdPartyNA().getCompany().indexOf("null") >= 0) {
                        setThirdPartyNA(getConsigneeNA());
                    }
                    if (getThirdPartyNA().getCompany().length() < 1) {

                        throw new Exception("No company name supplied for the third party billing");

                    }


                }
            }

        } else {

            if (getValue(ShipConfig.TERMS).equals("NameAddress")) {

                setValue(ShipConfig.CONSIGNEE_BILLING, "TRUE");

                setValue(ShipConfig.THIRD_PARTY_BILLING, "false");
                getConsigneeNA().setResidential(false);

                clearValue(ShipConfig.THIRD_PARTY_BILLING_ACCOUNT);


            } else {
                if (getAssignedServiceCode().indexOf("UPS.") >= 0 && upsAcct.length() > 0 && (!(upsAcct.trim().toUpperCase().equals("NULL")))) {

                    //UPS shipment

                    setValue(ShipConfig.THIRD_PARTY_BILLING, "true");

                    ////System.out.println("setting third party billing account");

                    setValue(ShipConfig.THIRD_PARTY_BILLING_ACCOUNT, upsAcct);

                    setThirdPartyNA(getClientNA()); //use client info

                } else if ((getAssignedServiceCode().indexOf("FEDEX.") >= 0 || getAssignedServiceCode().indexOf("FDX.") >= 0) && fedexAcct.length() > 0 && (!(fedexAcct.trim().toUpperCase().equals("NULL"))) && getAssignedServiceCode().indexOf("FEDEX.SP") == -1) {

                    //Fedex shipment

                    setValue(ShipConfig.THIRD_PARTY_BILLING, "true");

                    setValue(ShipConfig.THIRD_PARTY_BILLING_ACCOUNT, fedexAcct);

                    setThirdPartyNA(getClientNA()); //use client info

                }
            }

        }

        // setValue(ShipConfig.THIRD_PARTY_BILLING, "false");
        setValue(ShipConfig.OWD_ORDER_ID, orderInfo.getStrValue(kOIOrderID, 1, "1"));

        ////System.out.println("rows1=" + orderInfo.getRowCount());

        setClientID(orderInfo.getIntValue(kOIClientID, 1, 0));
        setValue("CLIENTID", new Integer(getClientID()));
        ////System.out.println("Loading from OWD, clientID=" + getValue("CLIENTID") + ":" + getClientID());

        // //System.out.println("rows2=" + orderInfo.getRowCount());


        if (orderInfo.getRowCount() > 2) {
            ArrayList<ArrayList> items = new ArrayList<ArrayList>();
            //     ArrayList orderItems = new ArrayList();

            float customsValue = (float) 0.00;

            ////System.out.println("Evaluating "+(orderInfo.getRowCount()-2)+" items for customs value");

            //  Hashtable pkg_ship_info = new Hashtable();

            ////System.out.println("number of rows in orderInfo: "+orderInfo.getRowCount());

            for (int i = 2; i < orderInfo.getRowCount(); i++) {

                //todo
                if (isInternational() || isAPOFPO()) {
                    float itemPrice = new Float("0" + orderInfo.getStrValue(kOIItemPrice, i, "0.00")).floatValue();
                    float itemValue = roundFloat(new Float("0" + orderInfo.getStrValue(kOIItemCustValue, i, "0.00")).floatValue(), 2);
                    int itemCount = orderInfo.getIntValue(kOIItemQty, i, 0);

                    ArrayList<String> anItem = new ArrayList<String>();
                    anItem.add(orderInfo.getStrValue(kOIItemSKU, i, "N/A"));
                    anItem.add(itemCount + "");
                    ////System.out.println("itemCount: "+itemCount);
                    float lineTotal = (itemValue * itemCount);


                    if (lineTotal < 1.00) {

                        lineTotal = (itemPrice * itemCount);

                        if (lineTotal < 1.00f) {

                            itemPrice = roundFloat(((float) 0.01) + (((float) 1.00) / itemCount), 2);

                            //////System.out.println("got price "+itemPrice);

                            if (itemPrice < 0.01) itemPrice = (float) 0.01;

                            if (itemPrice == ((float) 1.01)) itemPrice = (float) 1.00;

                            lineTotal = (itemPrice * itemCount);

                            //////System.out.println("final price "+itemPrice);

                        }

                    } else {

                        itemPrice = itemValue;

                    }

                    ////System.out.println("final price "+itemPrice);

                    anItem.add("" + itemPrice);

                    customsValue += lineTotal;

                    ////System.out.println("cstoms value = "+customsValue);

                    String custDesc = orderInfo.getStrValue(kOIItemCustDesc, i, "");
                    ////System.out.println("item description: "+orderInfo.getStrValue(kOIItemDesc,i,""));

                    if (custDesc.length() < 1 && isInternational() || isAPOFPO()) {
                        custDesc = orderInfo.getStrValue(kOIItemDesc, i, "");

                        if (custDesc.length() < 1) {
                            //custDesc = (String)getValue(ShipConfig.DESCRIPTION);
                            throw new Exception("Item description required for international orders!");
                        }
                    }

                    anItem.add(custDesc);

                    //System.out.println("adding package barcode for item:" + orderInfo.getStrValue(kOIItemBarcode, i, ""));
                    anItem.add(orderInfo.getStrValue(kOIItemBarcode, i, "")); //package barcode for the package this item is in

                    anItem.add(orderInfo.getStrValue(KOTOriginCountry, i, "United States"));// line item country origin
                    items.add(anItem);
                }

            }
            //todo nbn

            int itemListSize = 0;
            //System.out.println("order barcode:" + getValue("PACKAGE_BARCODE"));

            for (int j = 0; j < items.size(); j++) {
                ArrayList currItem = ((ArrayList) items.get(j));

                if (currItem.contains(getValue("PACKAGE_BARCODE"))) {
                    itemListSize++;
                }
            }

            setItemData(new String[6][itemListSize]); //only care about 6 columns
            setOrderItemData(new String[6][items.size()]); //only care about 6 columns
            for (int i = 0; i < 6; i++) {
                itemListSize = 0;

                for (int j = 0; j < items.size(); j++) {
                    if (((ArrayList) items.get(j)).contains(getValue("PACKAGE_BARCODE"))) {
                        getItemData()[i][itemListSize++] = (String) ((ArrayList) items.get(j)).get(i);

                    }
                    getOrderItemData()[i][j] = (String) ((ArrayList) items.get(j)).get(i);
                }
            }
            //     setValue(ShipConfig.PKG_SHIP_INFO, pkg_ship_info);

            setValue(ShipConfig.DECLARED_VALUE_CUSTOMS, "" + ((int) customsValue));

            //if(isInternational() && (getAssignedServiceCode().indexOf("USPS.") >= 0) )

            //    {

//		itemData = new String[0][0];

//		clearValue(ShipConfig.DECLARED_VALUE_CUSTOMS);

            //     }


        } else {

            ////System.out.println("No SKUs found...");


        }

        System.out.println("22a");
        try {
            if (getAssignedServiceCode().contains("UPS.") || getAssignedCarrierCode().contains("DHL")) {
                updateCompanyNameFieldForUPS(getConsigneeNA());
                updateCompanyNameFieldForUPS(getReturnNA());
                updateCompanyNameFieldForUPS(getThirdPartyNA());
                updateCompanyNameFieldForUPS(getClientNA());

                System.out.println("22aaa");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        setValue(ShipConfig.THIRD_PARTY_BILLING_ADDRESS, getThirdPartyNA());

        System.out.println("22aaaaaaaa");
        System.out.println(getAssignedServiceCode());
        System.out.println(getAssignedServiceName());
        System.out.println(country);
        System.out.println(getClientID());
        System.out.println("23a");
        if ((!(getAssignedServiceName().equalsIgnoreCase("USPS Priority Mail International"))) &&
                country.equalsIgnoreCase("MEXICO")
                && getClientID() == 333) {
            System.out.println("in the if");
            float depth = orderInfo.getFloatValue(kOIDepthO, 1, 0.00f);
            float width = orderInfo.getFloatValue(kOIWidthO, 1, 0.00f);
            float height = orderInfo.getFloatValue(kOIHeightO, 1, 0.00f);

            float dimensions = height + (2.00f * depth) + (2.00f * width);

            if (depth >= height && depth >= width) {
                //depth
                dimensions = depth + (2.00f * height) + (2.00f * width);
            } else if (width >= height && width >= depth) {
                //width
                dimensions = width + (2.00f * height) + (2.00f * depth);
            }

            if (dimensions <= 108.00f) {
                clearValue(ShipConfig.DECLARED_VALUE_AMOUNT);
                setAssignedServiceCode("TANDATA_USPS.USPS.I_PRIORITY");
                setAssignedServiceName("USPS Priority Mail International");
            }


        }

        System.out.println("23");
    }


    /**
     * Informs the OWD system that a shipment has been voided
     */

    public void postVoidShipment() throws Exception {

        try {

            // ////System.out.println("voiding shipment msn "+getValueAsString(ShipConfig.MSN));

            WebResource loader = new WebResource("http://" + ShipConfig.kNetServicesServer, WebResource.kGETMethod);


            loader.addParameter("fn", "vs");
            loader.addParameter("msn", getValueAsString(ShipConfig.MSN));

            loader.addParameter("order_num", getValueAsString(ShipConfig.SHIPPER_REFERENCE));

            loader.addParameter("order_fkey", getValueAsString("OWDORDERID"));

            loader.addParameter("package_barcode", getValueAsString("PACKAGE_BARCODE"));

            //System.out.println("voiding package barcode " + getValueAsString("PACKAGE_BARCODE"));

            BufferedReader reader = loader.getResource();

            // ////System.out.println("saving shipment");

            TabReader responseData = new TabReader(reader, false);


            if (responseData.getRowCount() < 1)

                throw new Exception("Server not updated - Server did not respond. Notify office.");

            if (responseData.getRowSize(0) < 1)

                throw new Exception("Server not updated - Server responded incorrectly. Notify office.");

            String response = ((String) responseData.getStrValue(0, 0, "ERROR")).trim();

            if (response.equals("ERROR"))

                throw new Exception((String) responseData.getStrValue(0, 1, "Unknown") + " Notify office.");

        } catch (Exception ex) {

            ex.printStackTrace();

            throw new Exception("Server not updated - Server did not respond. Notify office.");


        }

    }


    /**
     * Voids a shipment.
     *
     * @see com.owd.dc.manifest.api.internal.ConnectShipWebServices#voidShipment
     */

    public void voidShipment() throws Exception {

        if (isShipped()) {

            List<String> msnList = new ArrayList<String>();

            msnList.add(getValueAsString("MSN"));

            ConnectShipWebServices.voidShipment(ShipConfig.getConfig().getCarrierInfo((String) getValue("OWDSERVICE")).carrierName, (String) getValue(ShipConfig.SHIPPER), msnList);


            postVoidShipment();


        } else {

            throw new Exception("Could not void package because it has not been shipped");

        }


    }


    /**
     * Loads the information of the order to be shipped
     */


    protected void updateCompanyNameFieldForUPS(NameAddress na) {
        if (na.getCompany().trim().length() < 1 || na.getCompany().trim().equals(".")) {
            na.setCompany(na.getContact());
        }
        if (na.getCompany().trim().length() < 1 || na.getCompany().trim().equals(".")) {
            na.setCompany("None");
        }

    }

    private boolean paperless = true;

    public boolean isPaperless() {
        return paperless;
    }

    public void setPaperless(boolean paperless) {
        this.paperless = paperless;
    }

    public String[][] getItemData() {

        if (null == getValue("ITEMDATA")) {
            setValue("ITEMDATA", new String[0][0]);
        }
        return (String[][]) getValue("ITEMDATA");

    }

    public String[][] getOrderItemData() {

        if (null == getValue("ORDERITEMDATA")) {
            setValue("ORDERITEMDATA", new String[0][0]);
        }
        return (String[][]) getValue("ORDERITEMDATA");

    }

    public void setOrderItemData(String[][] data) {
        map.put("ORDERITEMDATA", data);
    }

    public void setItemData(String[][] data) {
        map.put("ITEMDATA", data);
    }

    static public float roundFloat(float f, int decimals) {

        return (float) ((Math.round((f * (float) Math.pow(10, decimals))))) / ((float) Math.pow(10, decimals));

    }


    //Modified on Dec. 27, 2002 to record the actual shipping charges for 3rd party billing order


    public void setOnDCHold(String notes) throws Exception {

        //    map.put(ShipConfig.COMMODITY_CONTENTS, getItemData());
        //   map.put(ShipConfig.COMMODITY_CONTENTS + "_ORDER", getOrderItemData());

        //  addToShipmentList(map);

        String id = (String) getValueAsString(ShipConfig.OWD_ORDER_ID);


        try {

            ////System.out.println("saving shipment nofn "+packageCount);

            //System.out.println("Posting to URL: " + "http://" + ShipConfig.kNetServicesServer);
            WebResource loader = new WebResource("http://" + ShipConfig.kNetServicesServer, WebResource.kGETMethod);

            //System.out.println("OID=" + id);
            if (id == null) return;

            if (id.length() < 1) return;

            if (id.equals("999")) return;


            loader.addParameter("fn", "dh");

            loader.addParameter("order_fkey", (String) getValueAsString("OWDORDERID"));

            loader.addParameter("weight", (String) getValueAsString(ShipConfig.WEIGHT));
            loader.addParameter("ref_weight_min", (String) getValueAsString("OWD_REF_LBS_MIN"));
            loader.addParameter("ref_weight_max", (String) getValueAsString("OWD_REF_LBS_MAX"));
            //  loader.addParameter("type",type+"");
            loader.addParameter("weight_notes", notes);

            BufferedReader reader = loader.getResource();

            //System.out.println("creating DC Hold");

            TabReader responseData = new TabReader(reader, false);

            //System.out.println("DC Hold response");

            String response = ((String) responseData.getStrValue(0, 0, "ERROR")).trim();

            if (response.equals("ERROR"))
                throw new Exception((String) responseData.getStrValue(0, 1, "Unknown"));


        } catch (Exception ex) {

            ex.printStackTrace();

            throw new Exception("Server not updated - Server did not respond. Void package and reship.");


        }


    }

    /**
     * Adds the current shipment to the shipmentList.
     * Posts shipment information to OWD system unless the order is an "walk-in" order.
     * If the order is an international order and shipped through UPS,
     * 4 copies of commercial invoice will be printed out.
     *
     * @param packageCount       the current package number among all packages shipped
     *                           for the current order
     * @param packagesInShipment the number of total packages for the current order
     */

    public void postShipment(int packageCount, int packagesInShipment, String user) throws Exception {
        final int packages = packagesInShipment;

        map.put(ShipConfig.COMMODITY_CONTENTS, getItemData());
        map.put(ShipConfig.COMMODITY_CONTENTS + "_ORDER", getOrderItemData());

        addToShipmentList(map);

        String id = (String) getValueAsString(ShipConfig.OWD_ORDER_ID);

        if (id.equalsIgnoreCase("WALKIN")) {
            return;
        }


        if (packageCount == 1) {
            setValue(ShipConfig.BILL_OF_LADING, getValue(ShipConfig.TRACKING_NUMBER));
        }

        try {

            //   printCommercialInvoice(packageCount, packages);

            ////System.out.println("saving shipment nofn "+packageCount);

            //System.out.println("Posting to URL: " + "http://" + ShipConfig.kNetServicesServer);
           // WebResource loader = new WebResource("http://" + ShipConfig.kNetServicesServer, WebResource.kGETMethod);
            //  WebResource loader = new WebResource("http://" + "danny.owd.com:8080/wms/manifesting/ship", WebResource.kGETMethod);
            //System.out.println("OID=" + id);
            if (id == null) return;

            if (id.length() < 1) return;

            if (id.equals("999")) return;
            postShipmentBean psb = new postShipmentBean();

            //loader.addParameter("fn", "ss");

            psb.setOrderFkey(getValueAsString("OWDORDERID"));
            System.out.println(getValueAsString("OWDORDERID"));
           psb.setLineIndex("" + packageCount);

            System.out.println("index=" + packageCount);
            System.out.println("all=" + packagesInShipment);
            psb.setTracking(getValueAsString(ShipConfig.TRACKING_NUMBER));

            psb.setWeight(getValueAsString(ShipConfig.WEIGHT));

            //Billed to client
            if(getOriginalAssignedServiceCode().contains("COM_OWD_FLATRATE")){
                psb.setTotalBilled("0");
            }else{
                psb.setTotalBilled(getValueAsString(ShipConfig.TOTAL));
            }
            if(getValueAsString(ShipConfig.THIRD_PARTY_BILLING).equalsIgnoreCase("true") && !getValue(ShipConfig.SHIPPER).toString().equalsIgnoreCase("DC7_QVC_JB")){
                //set flag to mark tracking as third party billing to facilitate proper billing
                psb.setIsThirdPartyBilling(true);
            }


            //OWD's cost
           psb.setCostOfGoods(getValueAsString(ShipConfig.ACTUAL_TOTAL));

            psb.setMsn(getValueAsString(ShipConfig.MSN));

           psb.setShipper(getValueAsString(ShipConfig.CURRENT_SHIPPER));


            psb.setFacility(getValueAsString("CURRENT_FACILITY"));
            psb.setUser(user);
            System.out.println("This is the user passed around " + user);
           psb.setIpAddress(ShipConfig.myIPAddress);

            psb.setPackageBarcode(getValueAsString("PACKAGE_BARCODE"));

            System.out.println("post barcode=" + getValueAsString("PACKAGE_BARCODE"));
            if (((String) getValueAsString("OWDINSURANCEVALUE")).length() == 0) {
                System.out.println("Setting defualt insurance Value");
                psb.setInsValue ("0.0");
            } else {
                psb.setInsValue(getValueAsString("OWDINSURANCEVALUE"));
            }

            System.out.println("INSurance value: " + getValueAsString("OWDINSURANCEVALUE"));
            psb.setInsCost(getValueAsString("OWDINSURANCECOST"));
            System.out.println("Insureance Cost: " + getValueAsString("OWDINSURANCECOST"));
            psb.setWeightNotes(getValueAsString("OWD_WEIGHT_NOTES"));
           psb.setCustomsDocs(getValueAsString("OWD_CUSTOMS_DOCS"));
            System.out.println(getAssignedServiceCode());
            System.out.println(getAssignedServiceName());
            System.out.println(getOriginalAssignedServiceCode());
            if (getAssignedServiceCode().equals("TANDATA_USPS.USPS.EXPR")) {
                setAssignedServiceCode("TANDATA_USPS.USPS.EXPR_ADDR");
            }
            if (getOriginalAssignedServiceCode().equals(getAssignedServiceCode())) {
               psb.setShipMethod( "SAME");
            } else {
                psb.setShipMethod(getAssignedServiceCode());
               psb.setShipMethodName(getAssignedServiceName());
               psb.setShipMethodNameOriginal(shipper.getServiceName(getOriginalAssignedServiceCode()));
                psb.setSerivceCodeOriginal(getOriginalAssignedServiceCode());

            }
            System.out.println("After checking shipmethod");
            System.out.println("Hello2");
            System.out.println(getAssignedServiceCode());
            if (getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.IPRI")) {
                psb.setLabelCode("TANDATA_FEDEXFSMS_LABEL.STANDARD");
            } else {
                System.out.println("in the else");
                if(getAssignedServiceCode().equals("TANDATA_USPS.USPS.PRIORITY")){
                    System.out.println("This is priority");
                    psb.setLabelCode("TANDATA_USPS_LABEL.MMS");
                } else if(getAssignedServiceCode().contains("CONNECTSHIP_UPS.UPS.")&&isInternational()){
                    psb.setLabelCode("CONNECTSHIP_UPS_MAXICODE_SHIPMENT_LABEL.STANDARD");


                }else if(isAPOFPO()&&getAssignedServiceCode().contains("DHLGLOBALMAIL")){
                    psb.setLabelCode("CONNECTSHIP_DHLGLOBALMAIL_APOFPO_LABEL.STANDARD");
                    }else{
                        psb.setLabelCode( ShipConfig.getConfig().getCarrierInfo(getAssignedServiceCode()).labelDocCode);
                    }

            }
            System.out.println("2");
           psb.setCarrierCode(getAssignedCarrierCode());
            System.out.println("3");
            psb.setServiceCode( getAssignedServiceCode());
            System.out.println("4");


            System.out.println("saving shipment");

                _postShipment(psb);




            System.out.println("Post shipment response");



        } catch (Exception ex) {

            ex.printStackTrace();

            throw new Exception("Server not updated - Server did not respond. Void package and reship.");


        }


    }


    /**
     * Ships a package by sending a SHIPMENTREQUEST to the ConnectShip server.
     * actual shipping stuff
     */
    public List<AMPConnectShipShipment> ship(int packageCount, int packagesInShipment, List<AMPConnectShipShipment> thePackages, String Shipper, String LocationCode) throws Exception {

        System.out.println("This is the pack index that was sent: " + packageCount);

        List<AMPConnectShipShipment> shipList = new ArrayList<AMPConnectShipShipment>();

        boolean correctionTried = false;
        // //System.out.println("in new ship");
        try {
            shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, true, LocationCode);
        } catch (Exception ex) {

            //System.out.println("in catching bad ship");
            //System.out.println("weight=" + ((Double) getValue(ShipConfig.WEIGHT)).doubleValue());
            //System.out.println("service code=" + getAssignedServiceCode());
            if (ex.getMessage().indexOf("Paperless Invoice") >= 0|| ex.getMessage().contains("Paperless Clearance")||ex.getMessage().contains("Commercial Invoice Method value")) {
                System.out.println("We we are in the paper less trying again");
                if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                    //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                    //  removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(),pendingShipmentsMap);

                }
                shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, false, LocationCode);

            } else if (ex.getMessage().indexOf("please utilize the FedEx Home Delivery service") >= 0) {

                correctionTried = true;
                if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                    //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                    //removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(),pendingShipmentsMap);

                }
                setAssignedServiceCode("TANDATA_FEDEXFSMS.FEDEX.FHD");
                shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, false, LocationCode);

            } else
                //System.out.println(ex.getMessage());
                if (ex.getMessage().indexOf("First-Class Commercial Plus Pricing is supported with Machinable Parcel only") >= 0 && !correctionTried) {
                    correctionTried = true;

                    tryCorrectingShipment(ex.getMessage());
                    if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                        //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                        // removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(), thePackages);

                    }
                    shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, true, LocationCode);
                } else if ((ex.getMessage().indexOf("Weight must be less") >= 0 || ex.getMessage().contains("Weight exceeds maximum (4 lbs)"))&& !correctionTried) {
                    correctionTried = true;
                    System.out.println("Over weight, correcting");
                    tryCorrectingShipment(ex.getMessage());
                    if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                        //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                        // removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(),pendingShipmentsMap);

                    }
                    shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, true, LocationCode);
                } else if (ex.getMessage().indexOf("Invalid billing terms for the destination") >= 0
                        && !correctionTried) {
                    //mark
                    //System.out.println("doing invalid billing");
                    String country_symbol = ShipConfig.getConfig().getCountryName(getConsigneeNA().getCountrySymbol()).toUpperCase();
                    if ((country_symbol.equalsIgnoreCase("PUERTO_RICO") || country_symbol.equalsIgnoreCase("PUERTO RICO"))) {

                        //System.out.println(getValue(ShipConfig.TERMS));
                        if (getValue(ShipConfig.TERMS).equals("DDU")) {
                            setValue(ShipConfig.TERMS, "SHIPPER");
                        } else {
                            System.out.println("SEtting ddu1");
                            setValue(ShipConfig.TERMS, "DDU");
                        }

                        correctionTried = true;
                        tryCorrectingShipment(ex.getMessage());
                        if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                            //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                            // removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(),pendingShipmentsMap);

                        }
                        shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, true, LocationCode);
                    } else {
                        throw ex;
                    }


                } else if ((ex.getMessage().indexOf("Delivery Confirmation is not") >= 0
                        ||
                        ex.getMessage().indexOf("Invalid signature service option") >= 0
                        ||
                        ex.getMessage().indexOf("Signature Confirmation is not allowed") >= 0
                        ||
                        (ex.getMessage().indexOf("service is not valid") >= 0
                                &&
                                ex.getMessage().indexOf("Delivery Confirmation - Signature Required") >= 0

                        )
                )
                        && !correctionTried) {
                    correctionTried = true;
                    setSuppressDC(true);
                    if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                        //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                        // removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(),pendingShipmentsMap);

                    }
                    shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, true, LocationCode);
                } else if (ex.getMessage().indexOf("phone number") >= 0 && !correctionTried) {
                    correctionTried = true;
                    getConsigneeNA().setPhone("605-845-4299");

                    if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                        //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                        System.out.print(getValueAsString("PACKAGE_BARCODE"));
                        //  pendingShipmentsMap = new TreeMap<String, MultiPackageShipment>();
                        // removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(),pendingShipmentsMap);
                    }
                    shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, true, LocationCode);
                } else if (ex.getMessage().indexOf("Saturday Delivery") >= 0 && !correctionTried) {
                    correctionTried = true;
                    setValue(ShipConfig.SATURDAY_DELIVERY, ("false"));

                    if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                        //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                        //removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(),pendingShipmentsMap);

                    }
                    shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, true, LocationCode);
                } else if (ex.getMessage().indexOf("Weight exceeds maximum") >= 0
                        && ((Double) getValue(ShipConfig.WEIGHT)).doubleValue() > 150.0d
                        && ((Double) getValue(ShipConfig.WEIGHT)).doubleValue() <= 160.0d
                        &&
                        getAssignedServiceCode().toUpperCase().equals("CONNECTSHIP_UPS.UPS.GND") && !correctionTried) {
                    correctionTried = true;
                    setValue(ShipConfig.WEIGHT, new Double(150.00));
                    if (1 < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                        //ShipperApp.removePackageFromShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"));
                        // removeMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE),getValue("PACKAGE_BARCODE").toString(),pendingShipmentsMap);

                    }
                    shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, true, LocationCode);
                } else if(ex.getMessage().contains("Priority Mail Cubic Tier Pricing Mailpieces must measure 0.5 cubic foot or less")||
                        ex.getMessage().contains("Dimensions exceeds maximum (0.5 cubic feet) for USPS Priority Mail Cubic Pricing")){
                    setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                    shipList = ship(packageCount, packagesInShipment, getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode), true, thePackages, Shipper, false, LocationCode);

                }else if(ex.getMessage().contains("package weight must be less than (1 lb)")&&getAssignedServiceCode().contains("DHL")){
                    //Package is over 1 lb change to heavier method
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND")){
                        setAssignedServiceCode("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_GND");
                    }
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP")){
                        setAssignedServiceCode("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP");
                    }
                    if(getAssignedServiceCode().equalsIgnoreCase("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP_MAX")){
                        setAssignedServiceCode("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP_MAX");

                    }

                  //  int packageCount, int packagesInShipment, List<AMPConnectShipShipment> thePackages, String Shipper, String LocationCode
                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);

                }else if(ex.getMessage().contains("The weight is less than the minimum (1 lb) required for service")
                        && getAssignedServiceCode().contains("DHL")){
                    //Weight is under 1lb change to lighter method
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP")){
                        setAssignedServiceCode("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP");

                    }
                    if(getAssignedServiceCode().equalsIgnoreCase("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP_MAX")){
                        setAssignedServiceCode("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP_MAX");

                    }

                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);


                }else if(ex.getMessage().contains("not served by DHL SmartMail")){
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP_MAX")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");


                    }
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP_MAX")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                    }

                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);


                }else if(ex.getMessage().contains("The girth is greater than the maximum (50 in)")&&getAssignedServiceCode().contains("DHL")){

                    log.debug("In the debug");
                    log.debug(getAssignedCarrierCode());
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP")){
                        setAssignedServiceCode("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND");
                    }
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP")){
                        setAssignedServiceCode("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_GND");
                    }
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP_MAX")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");


                    }
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP_MAX")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");


                    }
                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);



                }else if(ex.getMessage().contains("The girth is greater than the maximum (84 in)")&&getAssignedServiceCode().contains("DHL")){

                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                    }
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_GND")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                    }
                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);



                }else if(ex.getMessage().contains("The dimension is greater than the maximum (22x17x17 in")){

                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP_MAX")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");


                    }
                    if(getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP_MAX")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");

                    }
                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);

                } else if(ex.getMessage().contains(" The dimension is greater than the maximum (27x17x17 ")){
                    if(null != getOriginalAssignedServiceCode() && getOriginalAssignedServiceCode().contains("COM_OWD")){
                        log.debug("Lookup up new method for oversize OWD Flat Rate");
                        shipMethodUpdate smu = FlatRateShipping.getOversizeShipMethod(ZoneLookup.lookupZoneFromOrderId(getValueAsString("OWDORDERID")),
                                (int) Math.ceil(Double.parseDouble(thePackages.get(0).getValue(ShipConfig.WEIGHT).toString())),isAPOFPO());
                        // take new flat rate method and check for new dimensional weight

                        int dimWeight = DimensionalWeight.getDimensionalWeight(
                                getValueAsString("OWDORDERID"),
                                Double.parseDouble(getValueAsString("HEIGHT_INCHES")),
                                Double.parseDouble(getValueAsString("WIDTH_INCHES")),
                                Double.parseDouble(getValueAsString("DEPTH_INCHES")),
                                smu.getCode()
                        );
                        if(dimWeight > Math.ceil(Double.parseDouble(getValueAsString(ShipConfig.WEIGHT)))){
                            Criteria crit = HibernateSession.currentSession().createCriteria(OWDPackage.class).add(Restrictions.eq("packBarcode",getValueAsString("SHIPMENTBARCODE")));
                            Object obj = crit.uniqueResult();
                            if(obj == null) throw new Exception("Unable to find package in database to update dim_weight_lbs");
                            OWDPackage pack = (OWDPackage) obj;
                            pack.setDimWeightLbs(new BigDecimal(dimWeight));
                            HibernateSession.currentSession().saveOrUpdate(pack);
                            HibUtils.commit(HibernateSession.currentSession());
                        }


                        setAssignedServiceCode(smu.getCode());
                        shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);

                    }

                }else if(ex.getMessage().contains("Invalid service type")&&getAssignedServiceCode().equalsIgnoreCase("TANDATA_FEDEXFSMS.FEDEX.STD")){
                    setAssignedServiceCode("TANDATA_FEDEXFSMS.FEDEX.PRI");
                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);

                }else if(ex.getMessage().contains("UPS World Wide Commit Validation - Data could not be found for the specified service and origin/destination")&&getAssignedServiceCode().equalsIgnoreCase("CONNECTSHIP_UPS.UPS.NDS")){
                    setAssignedServiceCode("CONNECTSHIP_UPS.UPS.NDA");
                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);

                }else if(ex.getMessage().contains("Ship failed because: Package #1: Error in UPS World Wide Service Validation - UPS Next Day Air Saver is not supported with origin (United States) and destination (United States)")&&getAssignedServiceCode().equalsIgnoreCase("CONNECTSHIP_UPS.UPS.NDS")){
                    setAssignedServiceCode("CONNECTSHIP_UPS.UPS.NDA");
                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);

                }else if(ex.getMessage().contains("UPS Next Day Air Saver is not supported with destination (Puerto Rico)")&&getAssignedServiceCode().equalsIgnoreCase("CONNECTSHIP_UPS.UPS.NDS")){
                    setAssignedServiceCode("CONNECTSHIP_UPS.UPS.NDA");
                    shipList = ship(packageCount, packagesInShipment, thePackages, Shipper,  LocationCode);
                }

                else {
                    System.out.println("This is the last else.  So nothing we could fix");
                    throw ex;
                }
        }

        return shipList;

    }

    public void tryCorrectingShipment(String tandataMessage) {

        System.out.println("Trying to correct Shipments");
        if (tandataMessage.indexOf("Weight must be less than") >= 0 && ((Double) getValue(ShipConfig.WEIGHT)).doubleValue() > 0.5d
                && getAssignedServiceCode().toUpperCase().equals("TANDATA_USPS.USPS.FIRST")) {
            setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
        }
        if (tandataMessage.indexOf("First-Class Commercial Plus Pricing is supported with Machinable Parcel only") >= 0 && getAssignedServiceCode().toUpperCase().equals("TANDATA_USPS.USPS.FIRST")) {
            setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
        }

        if (tandataMessage.indexOf("Weight exceeds maximum") >= 0 && ((Double) getValue(ShipConfig.WEIGHT)).doubleValue() > 0.5d
                && getAssignedServiceCode().toUpperCase().equals("TANDATA_USPS.USPS.I_FIRST")) {
            setAssignedServiceCode("TANDATA_USPS.USPS.I_PRIORITY");
        }

    }
    //This actually does the shipping

    public List<AMPConnectShipShipment> ship(int packageCount, int packagesInShipment, String shipDate, boolean printLabel, List<AMPConnectShipShipment> thePackages, String Shipper, boolean paperlessinvoice, String LocationCode) throws Exception {
        //unhide this for paperless errors and redeploy
        //paperlessinvoice = false;
        //Main Ship Method
        List<AMPConnectShipShipment> shipList = new ArrayList<AMPConnectShipShipment>();
        double privateInsuranceCost = 0.00;
        try {

            returnAddressBean defaultReturnAddress = getDefaultReturnAddress(LocationCode, HibernateSession.currentSession());

            //   if (packageCount == 1) {
            System.out.println("Going to set the request");
            setRequest(new ShipRequest());

            if (null == getRequest().getDefaults()) {
                System.out.println("we have null defaults");
                getRequest().setDefaults(new DataDictionary());
            }
            //System.out.println("package_count = " + packageCount);
            //System.out.println("packagesInShipment = " + packagesInShipment);

            //package_number = ""+packagesInShipment+"";


            setValue(ShipConfig.SHIPDATE, shipDate);

/*

      if(isInternational() && (getAssignedServiceCode().indexOf("UPS.") >= 0) && getValue(ShipConfig.TERMS).equals("SHIPPER"))

      {

        setValue(ShipConfig.TERMS,"DDU");

      }

*/
            paperless = paperlessinvoice;
            if (((isAPOFPO() && getAssignedServiceCode().indexOf("USPS.") >= 0)
                    || "TANDATA_USPS.USPS.I_FIRST".equalsIgnoreCase(getAssignedServiceCode())
                    || "TANDATA_USPS.USPS.I_PRIORITY".equalsIgnoreCase(getAssignedServiceCode())
                    || "TANDATA_USPS.USPS.I_EXP_DMND".equalsIgnoreCase(getAssignedServiceCode())) || (isInternational())) {

                setValue("OWD_CUSTOMS_DOCS", "1");


            }
            if (getConsigneeNA().getCountrySymbol().equals("CANADA")) {
                getConsigneeNA().setPostalCode(getConsigneeNA().getPostalCode().toUpperCase());
            }


            //System.out.println("country symbol=" + getConsigneeNA().getCountrySymbol());

            if (isInternational() && (getAssignedServiceCode().indexOf("UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX.") >= 0) && getValue(ShipConfig.TERMS).equals("SHIPPER") && !getAssignedServiceCode().contains("UPS.SP")) {

                String country_symbol = ShipConfig.getConfig().getCountryName(getConsigneeNA().getCountrySymbol()).toUpperCase();

                ////System.out.println("Country symbol before set payment terms to DDU: "+country_symbol);

                if (country_symbol.equalsIgnoreCase("PUERTO_RICO") || country_symbol.equalsIgnoreCase("PUERTO RICO") || country_symbol.equalsIgnoreCase("US_VIRGIN_ISLANDS") || country_symbol.equalsIgnoreCase("US VIRGIN ISLANDS")) {

                    ;//don't change billing method to DDU for those countries
                    if(getAssignedServiceCode().contains("FEDEX.SP")){
                        getRequest().getDefaults().setTerms("SHIPPER");
                    }

                } else {
                    System.out.println("SEtting ddu2");
                    getRequest().getDefaults().setTerms("DDU");

                }

            }


            if (isInternational()) {


                setValue(ShipConfig.SED_METHOD, "" + Constants.sedmNotRequired);
                setValue(ShipConfig.SED_EXEMPTION_NUMBER, "30.55(h)");
            }

/*

    if(getAssignedServiceCode().indexOf("USPS.") >= 0 && isInsured())

    {

        setValue(ShipConfig.SUPPRESS_MMS,"true");

    }else

    {

        clearValue(ShipConfig.SUPPRESS_MMS);

    }

*/

            //modified on Nov. 5, 2002

            String s_code = getAssignedServiceCode().toUpperCase();

            ////System.out.println("s_code: "+s_code);

            String country_symbol = ShipConfig.getConfig().getCountryName(getConsigneeNA().getCountrySymbol());
            if (s_code.indexOf("FEDEX.SP") >= 0) {
                System.out.println("We have a smarty post shipy thingy");
                setSuppressDC(false);
                setValue(ShipConfig.PROOF, "TRUE");
                setValue(ShipConfig.PROOF_REQUIRE_SIGNATURE, "FALSE");

                //Check for client id for translation of 3rd party billing for smart post.
                if(getClientID()==626){
                    getReturnNA().setCompany("SP Returns");
                    getReturnNA().setContact("JustBrand Limited");

                }

            }

            if(s_code.contains("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS")||s_code.contains("CONNECTSHIP_DHLGLOBALMAIL.DHL.PP")){
                setValue(ShipConfig.PROOF, "TRUE");
                setValue(ShipConfig.PROOF_REQUIRE_SIGNATURE, "FALSE");
            }
            if (!isSuppressDC() && s_code.indexOf("USPS.") >= 0 && (s_code.indexOf("PRIORITY") >= 0 || s_code.indexOf("FIRST") >= 0 || s_code.indexOf("NONPRESORT") >= 0) && !isInternational()) {
                //changes to rules as of January first class delivery confirmation is free
                System.out.println("Set First proof");
                setValue(ShipConfig.PROOF, "TRUE");
                System.out.println("We just se the proof");
                if (getValue(ShipConfig.SHIPPER).equals("OWD_NON_EVS")) {
                    System.out.println("Setting proof false don't ya know");
                    setValue(ShipConfig.PROOF, "FALSE");
                }
                System.out.println("after the iff");
            } else if (!isSuppressDC() && s_code.indexOf("USPS.") >= 0 && s_code.indexOf("PRIORITY") >= 0 && isInternational() && country_symbol.equalsIgnoreCase("PUERTO_RICO") && country_symbol.equalsIgnoreCase("PUERTO RICO") && country_symbol.equalsIgnoreCase("US_VIRGIN_ISLANDS") && country_symbol.equalsIgnoreCase("US VIRGIN ISLANDS")) {

                setValue(ShipConfig.PROOF, "TRUE");

            } else if (!isSuppressDC() && (s_code.indexOf("USPS.SPCL") > 0 || s_code.indexOf("USPS.BPM") > 0
                    || s_code.indexOf("USPS.FIRST") > 0) && ((Boolean) getValue("USEDC_FIRSTCLASS")).booleanValue()) {

                //check for height of package for first class mail - skip if too short...
                if (!(s_code.equals("TANDATA_USPS.USPS.FIRST")) || ((Double) getValue("HEIGHT_INCHES")).doubleValue() >= 0.25) {


                    if (!(((Float) getValue(ShipConfig.OWD_ORDER_TOTAL)).floatValue() < 10.00f && getClientID() == 160)) //BOR check
                    {
                        setValue(ShipConfig.PROOF, "TRUE");
                    } else {
                        System.out.println("Setting proof to falseb");
                        setValue(ShipConfig.PROOF, "FALSE");
                    }

                } else {
                    System.out.println("Setting proof to false here1");
                    setValue(ShipConfig.PROOF, "FALSE");
                }
            } else {

                if (!s_code.contains("FEDEX")) {
                    System.out.println("Setting proof to false here2");
                    setValue(ShipConfig.PROOF, "FALSE");
                }

            }
            try {
                if (null == getValue(ShipConfig.PROOF)) {
                    System.out.println("We ahve a null proof set. Make it false");
                    setValue(ShipConfig.PROOF, "FALSE");
                }
            } catch (Exception e) {
                System.out.println("We ahve a null proof set. Make it false");
                setValue(ShipConfig.PROOF, "FALSE");
            }

           /* getRequest().setLOGIN(new LOGIN());
            getRequest().getLOGIN().setLOGINID("test");
            getRequest().getLOGIN().setPASSWORD("test");*/


            GregorianCalendar c = new GregorianCalendar();
            c.setTime(dformat.parse(shipDate));
            getRequest().getDefaults().setShipdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
            System.out.println("set ship date");
            System.out.println(getRequest().getDefaults().getShipdate());
            getRequest().getDefaults().setShipper((String) getValue(ShipConfig.SHIPPER));
            System.out.println("outside the proof isfs");
            // DEFATTRIBUTES defatt = new DEFATTRIBUTES();


            //  defatt.setSHIPPERINFO(new SHIPPERINFO());

            // defatt.getSHIPPERINFO().setSHIPPER((String) getValue(ShipConfig.SHIPPER));


            //defatt.setTERMS((String) getValue(ShipConfig.TERMS));
            getRequest().getDefaults().setTerms((String) getValue(ShipConfig.TERMS));
            System.out.println("outside the proof isfs2");

            //  defatt.setCURRENCYCODE("USD");


            // SHIPMENTSERVICEOPTIONS opts = new SHIPMENTSERVICEOPTIONS();

            /* if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.CONSIGNEE_BILLING))) {
                //System.out.println("assigning consignee billing");
                opts.setCONSIGNEE_BILLING_ID("TRUE");
            }*/

            if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.SATURDAY_DELIVERY))) {
                //System.out.println("assigning sat delivery");
                getRequest().getDefaults().setSaturdayDelivery(true);
            }

//sat delivery
            System.out.println("outside the proof isfs saturday");
//COD

            // defatt.setSHIPDATE(shipDate);

            // defatt.setSHIPMENTSERVICEOPTIONS(opts);

            // getRequest().setDEFATTRIBUTES(defatt);

            DataDictionaryList pkgs = new DataDictionaryList();

            //  PACKAGES pkgs = new PACKAGES();

            getRequest().setPackages(pkgs);

            //   }

            //calculate the total weight
            double cw = 0;
            try {
                cw = ((Double) getValue(ShipConfig.WEIGHT)).doubleValue();
            } catch (NumberFormatException nfe) {
                cw = 0;
            }
            double tw = 0;
            try {
                Object o = getValue(ShipConfig.TOTAL_WEIGHT);
                if (o != null) {
                    tw = ((Double) o).doubleValue();
                }
            } catch (NumberFormatException nfe) {
                tw = 0;
            }
            tw += cw;

            ////System.out.println("total weight is: "+tw);

            setValue(ShipConfig.TOTAL_WEIGHT, new Double(tw));

            //added on Jan 9, 2002 for shipping with pre-stored weight and dimension values
            //Hashtable pkg_ship_info = (Hashtable) getValue(ShipConfig.PKG_SHIP_INFO);
            //if(pkg_ship_info.
            //todo this is where we need to loop through the packages to add stuff pull out defaults.
            packageCount = 1;
            for(AMPConnectShipShipment aship:thePackages){

            DataDictionary pkg = new DataDictionary();
            //System.out.println("Dimension ck = " + getValueAsString(ShipConfig.DIMENSION));
            Dimensions dim = new Dimensions();
            dim.setUnit("IN");
            Double height = Double.parseDouble(aship.getValueAsString("HEIGHT_INCHES"));
            Double width = Double.parseDouble(aship.getValueAsString("WIDTH_INCHES"));
            Double depth = Double.parseDouble(aship.getValueAsString("DEPTH_INCHES"));

                dim.setValue(height+"x"+width+"x"+depth);
                System.out.println(dim.getValue());

           /* if (depth > width & depth > height) {
                System.out.println("Normal dimmensions");
                dim.setHeight(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("HEIGHT_INCHES"))));
                dim.setWidth(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("WIDTH_INCHES"))));
                dim.setLength(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("DEPTH_INCHES"))));
            }
            if (width > depth & width > height) {
                System.out.println("width is now the length");
                dim.setHeight(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("HEIGHT_INCHES"))));
                dim.setLength(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("WIDTH_INCHES"))));
                dim.setWidth(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("DEPTH_INCHES"))));
            }
            if (height > width & height > depth) {
                System.out.println("height is now the length");
                dim.setLength(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("HEIGHT_INCHES"))));
                dim.setWidth(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("WIDTH_INCHES"))));
                dim.setHeight(BigDecimal.valueOf(Double.parseDouble(aship.getValueAsString("DEPTH_INCHES"))));
            }*/
            //  dim.setDIMVALUE(getValueAsString(ShipConfig.DIMENSION));
            //  dim.setDIMUNITS("IN");
            pkg.setDimension(dim);


           // pkg.setTerms((String) getValue(ShipConfig.TERMS));

            LABELPRINT lblPrint = new LABELPRINT();


            lblPrint.setPORT("STRING");

            lblPrint.setPRINTERMODELSYMBOL("Datamax.ProdigyMax");

            lblPrint.setSTOCKSYMBOL("THERMAL_LABEL_8");

            /* if(!printLabel)
             {
                 pkg.setLABELPRINT(lblPrint);
             }*/

            //  REFERENCE ref = new REFERENCE();

            pkg.setNofnSequence(packageCount);
            pkg.setNofnTotal(packagesInShipment);
            // ref.setNOFN_SEQUENCE(packageCount + "");

            // ref.setNOFN_TOTAL(packagesInShipment + "");
            //todo
            System.out.println("This is the reference");
            System.out.println(aship.getValueAsString("PACKAGE_BARCODE"));
            pkg.setShipperReference(packagesInShipment > 1 ? aship.getValueAsString("PACKAGE_BARCODE") : aship.getValueAsString(ShipConfig.SHIPPER_REFERENCE));

            System.out.println(pkg.getShipperReference());
            pkg.setExternalKey(aship.getValueAsString("PACKAGE_BARCODE"));
            pkg.setConsigneeReference(aship.getValueAsString(ShipConfig.CONSIGNEE_REFERENCE));
            pkg.setConsigneeEmail(aship.getValueAsString(ShipConfig.SHIP_TO_EMAIL));


            Money insure = new Money();
            insure.setAmount(new BigDecimal(Double.parseDouble("0.00")));
            insure.setCurrency("USD");

            //    PACKAGESERVICEOPTIONS pkgOpts = new PACKAGESERVICEOPTIONS();

            //cubic pricing adjustments
                if (getAssignedServiceCode().equals("TANDATA_USPS.USPS.PRIORITY_CUBIC")){
                    if(getClientID() == 596){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                    }

                    if(height>18 || width>18 ||depth>18){
                        System.out.println("Too long for cubic switch over to priority");
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                    }
                    if(tw>20){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                    }
                    System.out.println((getValueAsString("PACKAGING")));
                    if(getValueAsString("PACKAGING").contains("FLAT")){
                        setAssignedServiceCode("TANDATA_USPS.USPS.PRIORITY");
                    }
                }

                System.out.println("Checking for Surepost proper weight");

                if(getAssignedServiceCode().contains("UPS.SP")){

                    if ((Double.parseDouble(aship.getValue(ShipConfig.WEIGHT)+"")) < 1d) {
                       if(getAssignedServiceCode().equals("CONNECTSHIP_UPS.UPS.SPPS")){
                           System.out.println("Need to change to under a lb");
                           setAssignedServiceCode("CONNECTSHIP_UPS.UPS.SPSTD");
                       }
                    }else{

                        if(getAssignedServiceCode().equals("CONNECTSHIP_UPS.UPS.SPSTD")){
                            System.out.println("We have an over");
                            setAssignedServiceCode("CONNECTSHIP_UPS.UPS.SPPS");
                        }
                    }

                }

//smart post stuff
                        System.out.println("Now checking smartpost");
                System.out.println(getAssignedServiceCode());
            if (getAssignedServiceCode().indexOf("FEDEX.SP") >= 1) {
                System.out.println((Double) aship.getValue(ShipConfig.WEIGHT));

                if ((Double.parseDouble(aship.getValue(ShipConfig.WEIGHT)+"")) < 1d) {
                    System.out.println("We have a light package");
                    if (getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.SP_PS")) {
                        System.out.println("We have parcel Select, we need to change");
                        if (aship.getValueAsString("PACKAGE_BARCODE").endsWith("b1") & packagesInShipment == 1) {
                            setAssignedServiceCode("TANDATA_FEDEXFSMS.FEDEX.SP_STD");
                        } else {
                            System.out.println("Checking for multibox different shipmethods");
                            //if this is a sp_ps shipment and box one greater than one pound and
                            // box 2 is under one pound, we need to change weight of second box to one pount
                            aship.setValue(ShipConfig.WEIGHT, 1);
                        }
                    }

                        pkg.setProof(false);
                        // DELIVERYOPTIONS delOpts = new DELIVERYOPTIONS();
                        // delOpts.setDELIVERY_METHOD("0");
                        // delOpts.setDISPOSITION_METHOD("1");
                        //   pkgOpts.setDELIVERYOPTIONS(delOpts);
                        pkg.setDeliveryMethod("0");
                        if(getValueAsString("GROUPNAME").equals("walmartdsv")){
                            pkg.setDispositionMethod("3");
                            pkg.setAddressChangeNotification(true);
                        }else {
                            pkg.setDispositionMethod("1");
                        }



                } else {
                    System.out.println("The else of the weight");
                    if (getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.SP_STD")) {
                        setAssignedServiceCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                    }
                    System.out.println("Setting delOpts to zeross");
                    pkg.setDeliveryMethod("0");
                    if(getValueAsString("GROUPNAME").equals("walmartdsv")){
                        pkg.setDispositionMethod("3");
                        pkg.setAddressChangeNotification(true);
                    }else {
                        pkg.setDispositionMethod("1");
                    }

                }
            }

                if(getAssignedServiceCode().contains("DHLGLOBALMAIL.DHL.PS")||getAssignedServiceCode().contains("DHLGLOBALMAIL.DHL.PP")){
                    pkg.setDispositionMethod("1");
                }
            System.out.println(getValue(ShipConfig.SHIPPER) + " This be the shipper>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            if (getValue(ShipConfig.SHIPPER).equals("OWD_NON_EVS")) {
                System.out.println("Setting nonmahcine");
                //   pkgOpts.setUSPS_NONMACHINABLEMAIL("true");
                pkg.setNonmachinableMail(true);
            }
            //  PROOF proof = new PROOF();

            System.out.println("Setting proofflas from ship config :   " + getValue(ShipConfig.PROOF));
            //  proof.setPROOFFLAG((String) getValue(ShipConfig.PROOF));
            pkg.setProof(((String) getValue(ShipConfig.PROOF)).equalsIgnoreCase("TRUE"));
            if (!isInternational()) {
                System.out.println("Not international");
                if (isInsured() && (getAssignedServiceCode().indexOf(".UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX") >= 0) && getClientID() == 146) {

                    //    proof.setPROOFFLAG("TRUE");
                    //    proof.setPROOFSIGNATURE("TRUE");
                    pkg.setProof(true);
                    setValue(ShipConfig.PROOF, "TRUE");
                    pkg.setProofRequireSignature(true);


                }

                if ((getAssignedServiceCode().indexOf(".UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX") >= 0) && getClientID() == 260) {


                    setValue(ShipConfig.PROOF, "TRUE");

                    pkg.setProof(true);
                    pkg.setProofRequireSignature(true);


                }

                if ((getAssignedServiceCode().indexOf(".UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX") >= 0) && getClientID() == 315) {

                    setValue(ShipConfig.PROOF, "TRUE");

                    pkg.setProof(true);
                    pkg.setProofRequireSignature(true);


                }
                if (getAssignedServiceCode().indexOf("FEDEX.SP") >= 0) {

                    System.out.println("Well this is the sedond thingy");
                    if (getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.SP_STD")) {
                        pkg.setProof(false);
                        setValue(ShipConfig.PROOF, "FALSE");

                    } else {
                        setValue(ShipConfig.PROOF, "TRUE");

                        pkg.setProof(true);
                    }
                    pkg.setProofRequireSignature(false);
                }

            }
            System.out.println("After the is international ");
            Float ordertotal = (Float) getValue(ShipConfig.OWD_ORDER_TOTAL);
            if (ordertotal == null) ordertotal = new Float(0.00f);
            ////System.out.println("proof test");
            if ((getAssignedServiceCode().indexOf("USPS.PRIORITY") >= 0) && 20.00f <= ((Float) ordertotal).floatValue() && getClientID() == 139) {
                System.out.println("Well setting them true here priofity ify thingsy");
                pkg.setProof(true);
                setValue(ShipConfig.PROOF, "TRUE");

                pkg.setProofRequireSignature(true);


            }

            //todo
            if ((getAssignedServiceCode().indexOf(".UPS.") >= 0
                    || getAssignedServiceCode().indexOf("FEDEX") >= 0
                    || ((getAssignedServiceCode().indexOf("USPS.STD") < 0) &&
                    (getAssignedServiceCode().indexOf("USPS.EXPR") < 0) &&
                    (getAssignedServiceCode().indexOf("USPS.I_") < 0)))
                    && (((Boolean) getValue(ShipConfig.OWD_SIG_REQUESTED)).booleanValue()
                    && !isSuppressDC())
                    ) {
                System.out.println("WE are setting both to true hereaa");
                setValue(ShipConfig.PROOF, "TRUE");
                if((getClientID()==489)&& getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.GND")){
                    System.out.println("WE are setting direct sig");
                    pkg.setProof(true);
                    pkg.setProofRequireSignatureConsignee(true);
                }else {

                    if(getAssignedServiceCode().contains("CONNECTSHIP_DHLGLOBALMAIL.DHL")){
                        pkg.setProof(false);
                        pkg.setProofRequireSignature(true);
                    }else {
                        pkg.setProof(true);
                        pkg.setProofRequireSignature(true);
                    }
                }
            }
            if (getConsigneeNA().getCountrySymbol().equalsIgnoreCase("PALAU")) {
                System.out.println("PALAUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
                pkg.setProof(false);
                setValue(ShipConfig.PROOF, "FALSE");

                pkg.setProofRequireSignature(false);
            }
            ////System.out.println("proof test done");
            if (isSuppressDC()) {
                System.out.println("Actually setting the prood ");
                pkg.setProof(false);
                setValue(ShipConfig.PROOF, "FALSE");

                pkg.setProofRequireSignature(false);
            }


            //  System.out.println("signature proofy=" + pkg.getSi());


            String decValue = aship.getValueAsString(ShipConfig.DECLARED_VALUE_AMOUNT);
            double insValue = 0.00;
            if (isInsured()) {


                try {
                    insValue = Double.parseDouble(decValue) / (1.00 * packagesInShipment);
                    //     decValue=""+roundFloat((float)insValue,2);
                } catch (Exception ex) {
                    decValue = "0.00";
                }

                ////System.out.println("Insured value is not zero");
                /*  if( getAssignedServiceCode().toUpperCase().indexOf("I_LETTER_AIR")>=0)
                {
                      decValue="0.00";
                    insValue=0.00;
                }*/


                if (packagesInShipment > 1) {

                    insure.setAmount(new BigDecimal(Double.parseDouble("" + roundFloat((float) insValue, 2))));

                } else {

                    insure.setAmount(new BigDecimal(Double.parseDouble(decValue)));


                }

                setValue("OWDINSURANCEVALUE", "" + insure.getAmount().doubleValue());

                if (getAssignedServiceCode().indexOf("USPS.") >= 0) {
                    if (isInsured()
                            ) {
                        double insuredAt = insure.getAmount().doubleValue();
                        while (insuredAt >= 0.00) {
                            privateInsuranceCost += getUSPSPrivateInsuranceCost();
                            insuredAt -= 100.00;
                        }


                    }
                } else {
                    pkg.setDeclaredValueAmount(insure);
                }
            }
            System.out.println("This is the package options we are using");
            //  System.out.println(pkgOpts);
            //  PROOF p = pkgOpts.getPROOF();
           /* System.out.println(p.getPROOFFLAG());
            System.out.println(p.getPROOF_SIGNATURE_WAIVER());
            System.out.println(p.getPROOFADULTSIGNATURE());
            System.out.println(p.getPROOFALTERNATE());
            System.out.println(p.getPROOFCONSIGNEESIGNATURE());
            System.out.println(p.getPROOFNUMBER());
            System.out.println(p.getPROOFSIGNATURE());
            System.out.println(p.getSIGNATURE_RELEASE());*/


//THIRD PARTY BILLING

//modified on Dec. 30, 2002 to allow reporting actual total

////System.out.println("Third Party Billing"+getValue(ShipConfig.THIRD_PARTY_BILLING));


            if (((String) getValue(ShipConfig.THIRD_PARTY_BILLING)).toUpperCase().equals("TRUE")&& !getValue(ShipConfig.SHIPPER).toString().equalsIgnoreCase("DC7_QVC_JB")) {

                ////System.out.println("set Third Party Information\n");

                NameAddress third = new NameAddress();


                third.setContact(getThirdPartyNA().getContact());

                third.setCompany(getThirdPartyNA().getCompany());

                third.setAddress1(getThirdPartyNA().getAddress1());

                third.setAddress2(getThirdPartyNA().getAddress2());

                third.setCity(getThirdPartyNA().getCity());

                third.setStateProvince(getThirdPartyNA().getStateProvince());

                third.setCountrySymbol(getThirdPartyNA().getCountrySymbol());

                third.setPostalCode(getThirdPartyNA().getPostalCode());

                third.setPhone(getThirdPartyNA().getPhone());

                // pkgOpts.setBILLTHIRDPARTY(third);
                pkg.setThirdPartyBilling(true);
                pkg.setThirdPartyBillingAccount((String) getValue(ShipConfig.THIRD_PARTY_BILLING_ACCOUNT));
                pkg.setThirdPartyBillingAddress(third);

              //  pkg.setTerms("SHIPPER");

                ////System.out.println("third party contact: "+getBillingNA().getCompany()+"\n");


            }

//INTL

            ////System.out.println("Is Iternataional  "+isInternational() );

            if (isInternational()) {

                //   int item_count = getItemData()[0].length;

                //  INTL intl = new INTL();

                if (getAssignedServiceCode().indexOf("UPS.") >= 0||getAssignedServiceCode().indexOf("DHL.") >= 0) {
                    if (getConsigneeNA().getCountrySymbol().equalsIgnoreCase("TRINIDAD_TOBAGO")&getAssignedServiceCode().contains("UPS.")) {
                        paperlessinvoice = false;
                    }
                    if (paperlessinvoice && !getAssignedServiceCode().contains("UPS.SP")) {
                        pkg.setCommercialInvoiceMethod("1");
                        //  intl.setCOMMERCIAL_INVOICE_METHOD("1"); //"0" for paper, "1" for paperless, only for UPS...
                    } else {
                        //   intl.setCOMMERCIAL_INVOICE_METHOD("0");
                        pkg.setCommercialInvoiceMethod("0");

                    }
                }
                System.out.println("This is the invoice method" + pkg.getCommercialInvoiceMethod());
                // intl.setINTLDESCRIPTION(convertNullToEmptyString((String) getValue(ShipConfig.DESCRIPTION)));
                pkg.setDescription(convertNullToEmptyString((String) getValue(ShipConfig.DESCRIPTION)));
                pkg.setSedMethod("0");
                if(!getAssignedServiceCode().contains("USPS") && !getAssignedServiceCode().contains("UPS.SP") ) {
                    if (getConsigneeNA().getCountrySymbol().equalsIgnoreCase("CANADA")) {
                        pkg.setSedExemptionNumber("NO EEI 30.36");
                    } else {
                        pkg.setSedExemptionNumber("NO EEI 30.37 (a)");
                    }
                }
               // pkg.setSedExemptionNumber("X20150624441818");
                System.out.println(pkg.getSedExemptionNumber() + "      CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                pkg.setInvoiceFreight(getMoneyFromCurrencyString("1.00"));          //for fedex...
                pkg.setInvoiceInsuranceFee(getMoneyFromCurrencyString("1.00"));        //for fedex...
                pkg.setInvoiceOtherFee(getMoneyFromCurrencyString("0.00"));
                pkg.setInvoiceDiscount(getMoneyFromCurrencyString("0.00"));
                pkg.setExporterTaxId("46-0458580");
                pkg.setUltimateDestinationCountry(getConsigneeNA().getCountrySymbol());
                System.out.println("1");
                pkg.setDocumentsOnly(false);
                System.out.println("2 the terms");
                System.out.println(pkg.getTerms());
                System.out.println(country_symbol);


                if ("DDU".equals(pkg.getTerms()) || "CONSIGNEE".equals(pkg.getTerms())||country_symbol.equalsIgnoreCase("PUERTO RICO")) {
                    System.out.println(getAssignedCarrierCode());
                    System.out.println(country_symbol);
                    if (getAssignedServiceCode().indexOf("FEDEX.SP") >= 0 && (country_symbol.equalsIgnoreCase("GUAM")||country_symbol.equalsIgnoreCase("PUERTO RICO"))) {
                        System.out.println("Settin shipper cause guam");
                        pkg.setTermsOfSale("SHIPPER");
                        setValue(ShipConfig.TERMS, "SHIPPER");
                        pkg.setTerms("SHIPPER");
                    } else {
                        System.out.println("SEtting ddu3");
                        pkg.setTermsOfSale("DDU");
                    }
                }
                System.out.println("3");
                if (getAssignedServiceCode().indexOf("FEDEX") >= 0) {
                    if (getValueAsString(ShipConfig.FED_EX_CUSTOMS_ACCOUNT).length() > 0) {
                        System.out.println("We have a fedex account number");
                        // BILLCONSIGNEETHIRDPARTY thirdConsignee = new BILLCONSIGNEETHIRDPARTY();
                        // thirdConsignee.setCONSIGNEETPACCOUNT(getValueAsString(ShipConfig.FED_EX_CUSTOMS_ACCOUNT));
                        pkg.setConsigneeThirdPartyBillingAccount(getValueAsString(ShipConfig.FED_EX_CUSTOMS_ACCOUNT));
                        pkg.setConsigneeThirdPartyBilling(true);
                        pkg.setTerms("DDU");
                    }
                }

                if (getAssignedServiceCode().indexOf("UPS.") >= 0 && (getClientID() == 414)) {
                    pkg.setTerms("SHIPPER");
                    pkg.setTermsOfSale("SHIPPER");
                }
                if(getAssignedServiceCode().equals("CONNECTSHIP_DHL.DHL.WPX")
                        ||getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PC_PRI")
                        ||getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PKT_PRI")
                        ||getAssignedServiceCode().equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PKT_GND")){
                    pkg.setExportReason("P");
                    if(getValueAsString(ShipConfig.SHIP_DTP_FLAG).equalsIgnoreCase("1")){
                        pkg.setTerms("SHIPPER");
                    }else{
                        pkg.setTerms("DDU");
                    }

                }
                // if (packagesInShipment < item_count) {
                //     if (packageCount == packagesInShipment) {
                CommodityList contents = new CommodityList();
                System.out.println("4");
                if (aship.getItemData().length > 0) {
                    float dvalue = 0;
                    for (int i = 0; i < aship.getItemData()[0].length; i++) {

                        Commodity item = new Commodity();

                        item.setProductCode(aship.getItemData()[kTDItemSKU][i]);

                        float unitPrice = 0;

                        try {

                            unitPrice = new Float(aship.getItemData()[kTDItemPrice][i]).floatValue();

                        } catch (NumberFormatException nfex) {

                            unitPrice = (float) 0.00;

                        }

                        if (unitPrice < 0.01) unitPrice = (float) 1.00;

                        int unitQty = 1;

                        try {

                            unitQty = new Integer(aship.getItemData()[kTDItemQty][i]).intValue();

                        } catch (NumberFormatException nfex) {

                            unitQty = 1;

                        }

                        dvalue += (unitQty * unitPrice);

                        item.setQuantity(unitQty);

                        item.setUnitValue(getMoneyFromCurrencyString("" + unitPrice));

                        item.setDescription(aship.getItemData()[kTDItemDesc][i].replaceAll("[^A-Za-z0-9 ]", ""));

                        if (pkg.getDescription().equals("") || pkg.getDescription().equalsIgnoreCase("Merchandise")) {
                            pkg.setDescription(aship.getItemData()[kTDItemDesc][i]);
                        }
                        item.setQuantityUnitMeasure("PC");

                        Weight uwgt = new Weight();
                        uwgt.setAmount(new BigDecimal(Double.parseDouble((((Double) aship.getValue(ShipConfig.WEIGHT)).doubleValue() / aship.getItemData()[0].length) + "")));
                        uwgt.setUnit("LB");
                        item.setUnitWeight(uwgt);
                        System.out.println("===========");
                        System.out.println("===========");
                        System.out.println(aship.getItemData()[kTDItemSKU][i]);
                        System.out.println(aship.getItemData()[kTDItemQty][i]);
                        System.out.println(aship.getItemData()[kTDItemPrice][i]);
                        System.out.println(aship.getItemData()[kTDItemDesc][i]);
                        System.out.println(aship.getItemData()[kTDItemCountryOrigin][i]);
                        System.out.println("===========");
                        System.out.println("===========");
                        item.setOriginCountry(ShipConfig.getConfig().getCountryCode(aship.getItemData()[kTDItemCountryOrigin][i]));
                        //QUANTITY_UNIT_MEASURE


                        contents.getItem().add(item);

                    }


                    pkg.setDeclaredValueCustoms(getMoneyFromCurrencyString("" + ((int) dvalue)));


                } else {
                    throw new Exception("No line items found for this shipment");
                }


                pkg.setCommodityContents(contents);

                System.out.println("5");
                setValue("OWDCUSTOMSSHIPMENTVALUE", pkg.getDeclaredValueCustoms().getAmount().doubleValue());
                System.out.println("6");
                // pkgOpts.setINTL(intl);

                if (isInsured()) {
                    System.out.println("7");
                    System.out.println(getValueAsString("OWDINSURANCEVALUE"));
                    double insuredValue = new Double(getValueAsString("OWDINSURANCEVALUE")).doubleValue();
                    System.out.println("8");
                    double customsValue = pkg.getDeclaredValueCustoms().getAmount().doubleValue();
                      System.out.println("9");
                    if (insuredValue > customsValue) {
                        System.out.println("10");
                        // pkgOpts.getDECLAREDVALUE().setMONETARYVALUE("" + customsValue);
                        setValue("OWDINSURANCEVALUE", "" + customsValue);
                        privateInsuranceCost = 0.00;

                        if (getAssignedServiceCode().indexOf("USPS.") >= 0) {
                            if (isInsured()
                                    ) {
                                double insuredAt = insure.getAmount().doubleValue();
                                while (insuredAt >= 0.00) {
                                    privateInsuranceCost += getUSPSPrivateInsuranceCost();
                                    insuredAt -= 100.00;
                                }


                            }
                        } else {
                            pkg.getDeclaredValueAmount().setAmount(new BigDecimal(Double.parseDouble("" + customsValue)));
                        }


                    }


                }


            }
                System.out.println("6outer");

//OVERSIZE

            //      pkgOpts.setOVERSIZE(((String) getValue(ShipConfig.OVERSIZE)).toUpperCase());

//RETURNDELIVERY

            RETURNDELIVERY ret = new RETURNDELIVERY();
            // ret.setRETURNDELIVERYFLAG("FALSE");
            //  ret.setRETURNDELIVERYDESCRIPTION("Return Address");
            pkg.setReturnDelivery(false);

            NameAddress retaddr = new NameAddress();

            if ("true".equals("" + getValue(ShipConfig.RETURN_DELIVERY))) {

                pkg.setReturnDelivery(true);

                retaddr.setContact("OWD");

                retaddr.setCompany(getReturnNA().getCompany());

                retaddr.setAddress1(defaultReturnAddress.getAddress1());
                //
                retaddr.setAddress2("");

                retaddr.setCity(defaultReturnAddress.getCity());

                retaddr.setStateProvince(defaultReturnAddress.getState());

                retaddr.setPostalCode(defaultReturnAddress.getZip());

                retaddr.setCountrySymbol("UNITED_STATES");

                retaddr.setPhone("8662091413");

                pkg.setReturnAddress(retaddr);

            } else {
                pkg.setReturnDelivery(false);

                String test = getReturnNA().getPostalCode();

                ////System.out.println("postal code in returnNA: "+test);
                if (test != null && test.length() > 0) {

                    if(getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.GND")&getConsigneeNA().getCountrySymbol().equals("CANADA")){
                        //do not use return address for ground to canada
                    }else {
                        retaddr.setContact(getReturnNA().getContact().trim().length() < 1 ? "Returns" : getReturnNA().getContact());

                        retaddr.setCompany(getReturnNA().getCompany());

                        retaddr.setAddress1(getReturnNA().getAddress1());

                        retaddr.setAddress2(getReturnNA().getAddress2());

                        retaddr.setCity(getReturnNA().getCity());

                        retaddr.setStateProvince(getReturnNA().getStateProvince());

                        retaddr.setPostalCode(getReturnNA().getPostalCode());

                        retaddr.setCountrySymbol(getReturnNA().getCountrySymbol());

                        retaddr.setPhone(getReturnNA().getPhone());

                        pkg.setReturnAddress(retaddr);
                    }
                } else {

                    retaddr.setContact("OWD");

                    retaddr.setCompany("One World Distribution");

                    retaddr.setAddress1(defaultReturnAddress.getAddress1());
                    //
                    retaddr.setAddress2("");

                    retaddr.setCity(defaultReturnAddress.getCity());

                    retaddr.setStateProvince(defaultReturnAddress.getState());

                    retaddr.setPostalCode(defaultReturnAddress.getZip());

                    retaddr.setCountrySymbol("UNITED_STATES");

                    retaddr.setPhone("8662091413");

                    // retaddr.setRT
                    pkg.setReturnAddress(retaddr);

                }

            }


            //  pkgOpts.setRETURNDELIVERY(ret);


            if (isInsured()) {
                //  pkgOpts.setUSPS_SUPPRESSMMS("true");
            }


            if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.CALLTAG))) {

                //   ret = new RETURNDELIVERY();
                //    ret.setRETURNDELIVERYFLAG("TRUE");
                pkg.setReturnDelivery(true);
                getRequest().getDefaults().setReturnDeliveryNotificationDescription("Returned Items");
                getRequest().getDefaults().setDescription("Returned Items");
                retaddr = new NameAddress();
                pkg.setReturnDeliveryMethod("2");  //calltag, three pickups

                if (("" + getValue(ShipConfig.CALLTAG_DESCRIPTION)).contains("@")) {
                    pkg.setReturnDeliveryAddressEmail("" + getValue(ShipConfig.CALLTAG_DESCRIPTION));
                    pkg.setReturnDeliveryMethod("4");  //email label
                }

                String test = getReturnNA().getPostalCode();

                ////System.out.println("postal code in returnNA: "+test);
                if (test != null && test.length() > 0) {


                    retaddr.setContact("Customer Returns");

                    retaddr.setCompany(getReturnNA().getCompany());

                    retaddr.setAddress1(getReturnNA().getAddress1());

                    retaddr.setAddress2(getReturnNA().getAddress2());

                    retaddr.setCity(getReturnNA().getCity());

                    retaddr.setStateProvince(getReturnNA().getStateProvince());

                    retaddr.setPostalCode(getReturnNA().getPostalCode());

                    retaddr.setCountrySymbol(getReturnNA().getCountrySymbol());

                    retaddr.setPhone(getReturnNA().getPhone());

                    pkg.setReturnAddress(retaddr);
                }
                // pkg.setReturnAddress(ret);

/*

                CALLTAG calltag = new CALLTAG();
                calltag.setCALLTAGFLAG("TRUE");
                calltag.setCALLTAGDESCRIPTION((String) getValue(ShipConfig.CALLTAG_DESCRIPTION));
                calltag.setCALLTAGNUMBER((String) getValue(ShipConfig.CALLTAG_NUMBER));
                pkgOpts.setCALLTAG(calltag);
*/


                pkg.setProof(false);
                pkg.setProofRequireSignature(false);
            }


            Weight wgt = new Weight();

            wgt.setAmount(new BigDecimal("" + aship.getValue(ShipConfig.WEIGHT)));

            wgt.setUnit("LB");
            System.out.println("This is the weights!!!!!!!!!!"+wgt.getAmount());
            pkg.setWeight(wgt);

            //System.out.println("ck packaging " + getValue(ShipConfig.PACKAGING));
            pkg.setPackaging("" + aship.getValue(ShipConfig.PACKAGING));


            // pkg.setPackagingDescription("WGT");

            //System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            // SHIPPERINFO shipper_info = new SHIPPERINFO();
            System.out.println("This is the shipper XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println(Shipper);


            getRequest().getDefaults().setShipper(Shipper);
            setValue(ShipConfig.SHIPPER, Shipper);
         /*shipper_info.setSHIPPER(ShipConfig.kShipperOWD);
            setValue(ShipConfig.SHIPPER, ShipConfig.kShipperOWD);


            if (getAssignedServiceCode().indexOf("FEDEX.") >= 0 && getClientID() == 127) {
                shipper_info.setSHIPPER(ShipConfig.kShipperSNY);
                setValue(ShipConfig.SHIPPER, ShipConfig.kShipperSNY);
                setValue(ShipConfig.CURRENT_SHIPPER, ShipConfig.kShipperSNY);
            }*/


            pkg.setShipper(Shipper);

            NameAddress cons = getConsigneeNA();

            //changed on Nov. 25

            ////System.out.println("Company name: "+cons.getCompany());
            //System.out.println("lllllllllllllllllllllllllllllllllllllllllllllll");
            if (cons.getCompany().length() < 2 || cons.getCompany().equalsIgnoreCase(cons.getContact()))

                cons.setResidential(true);

            else if (((String) cons.getContact()).indexOf(" ") >= 0 && cons.getCompany().equalsIgnoreCase(((String) cons.getContact()).substring(((String) cons.getContact()).indexOf(" "))))

                cons.setResidential(true);

            else

                cons.setResidential(false);
                if(getClientID()==489 || getClientID() == 622){
                    System.out.println("We have a sSymphony client");
                    System.out.println(getAssignedServiceCode());
                   if(getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.FHD")){
                       cons.setResidential(true);
                   }
                    if(getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.GND")){
                        cons.setResidential(false);
                    }

                }

            pkg.setConsignee(cons);


            getRequest().getDefaults().setConsignee(cons);

            Money money = new Money();
            money.setAmount(BigDecimal.valueOf(Double.valueOf(getValueAsString(ShipConfig.DISCOUNT))));
            money.setCurrency("USD");
            money.setValue(getValueAsString(ShipConfig.DISCOUNT));
            getRequest().getDefaults().setInvoiceDiscount(money);

            if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.FREIGHT_COLLECT))
                    ) {
                pkg.setConsigneeAccount((String) getValue(ShipConfig.CONSIGNEE_ACCOUNT));
                cons.setResidential(false);
            }

            if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.CALLTAG))) {
                cons.setResidential(false);
            }

            //todo separate out multiship logic from one-package logic...
            setValue("OWDSERVICE", getAssignedServiceCode());
            setValue("OWDSERVICENAME", getAssignedServiceName());
            setValue("OWDRETURNADDR", getReturnNA());
                aship.setValue("OWDINSURANCECOST", "" + privateInsuranceCost);
            //System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqr");
            System.out.println("Package Count" + packageCount);
            System.out.println("Packages in Shipment" + packagesInShipment);
            System.out.println("Assigned ServiceCode" + getAssignedServiceCode());
            System.out.println("Barcode " + aship.getValueAsString("PACKAGE_BARCODE"));
           /* if (packageCount < packagesInShipment && getAssignedServiceCode().indexOf("USPS.") == -1) {
                System.out.println("We are in here, this is the first if");
                // addMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"), pkg, this, packageCount, packagesInShipment,pendingShipmentsMap);
                //todo??
                getRequest().getPackages().getItem().add(pkg);
                getRequest().setCloseOutMode("0");

            } else {
                System.out.println("We are in here now, this is the else");
                if (packagesInShipment > 1 && getAssignedServiceCode().indexOf("USPS.") == -1) {
                    System.out.println("Non usps so we have a multipiece ");
                    //we're at the last of a multipackage shipment - so ship it!
                    // addMultiPackageShipmentPackage(getValueAsString(ShipConfig.SHIPPER_REFERENCE), getValueAsString("PACKAGE_BARCODE"), pkg, this, packageCount, packagesInShipment,pendingShipmentsMap);
                    //todo??
                    //loop through all previous packages   and add to list
                   *//* for (MultiPackageShipment.PackageData item : getPackagesForShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE),pendingShipmentsMap)) {


                        getRequest().getPackages().getItem().add(pkg);
                    }*//*

                } else {
                    //always add current package
                    //    System.out.println("Apparently this is what is being done?!?!?!?!?!");
                    getRequest().getPackages().getItem().add(pkg);
                }
                //  System.out.println("Setting Close out mode");



            }*/
                getRequest().getPackages().getItem().add(pkg);
                getRequest().setCloseOutMode("1");
                packageCount ++;



            getRequest().setService(getAssignedServiceCode());

        }

            //System.out.println(packageCount);
            //System.out.println(packagesInShipment);

                XStream x = new XStream();
                 System.out.println(x.toXML(getRequest()));
                System.out.println(getRequest().getPackages().getItem().size());
                ShipResponse response = amp.ship(getRequest());

               System.out.println(x.toXML(response));

                Boolean DHL3rdParty = false;



                //  System.out.println("sssssssssssssssssssssssssssssssssssssssssssss");
                //  System.out.println(response.getRESPONSE().getRESPONSESTATUSCODE());

                if (response.getResult().getCode() != 0) {

                    throw new Exception("Ship failed because: " + response.getResult().getMessage() + " (" + response.getResult().getCode() + ")");


                } else {
                    /*if (getAssignedServiceCode().indexOf("USPS.") >= 0) {
                        System.out.println("in the usps");
                        List<PackageResult> packages = response.getResult().getPackageResults().getItem();
                        for (PackageResult rs : packages) {

                            System.out.println(rs.getResultData().getShipperReference());
                            // ConnectShipShipment shipRec = this;
                            // shipRec.printDictionaryValues();
                          *//*  if (packages.size() > 1) {
                                MultiPackageShipment.PackageData currPack = getMultiPackShipment(getValueAsString(ShipConfig.SHIPPER_REFERENCE),pendingShipmentsMap).getPackageForBarcode(rs.getREFERENCE().getSHIPPERREFERENCE());
                                this = currPack.shipment;
                            }*//*
                            System.out.println("we are getting this MSN " + convertNullToEmptyString("" + rs.getResultData().getMsn()) + "for " + getValueAsString("PACKAGE_BARCODE"));

                            System.out.println(convertNullToEmptyString("" + rs.getResultData().getApportionedBase().getValue()));
                            System.out.println(convertNullToEmptyString("" + rs.getResultData().getApportionedDiscount().getValue()));
                            System.out.println(convertNullToEmptyString("" + rs.getResultData().getApportionedTotal().getValue()));
                            System.out.println(convertNullToEmptyString("" + rs.getResultData().getApportionedTotal().getAmount()));

                            setValue(ShipConfig.MSN, convertNullToEmptyString("" + rs.getResultData().getMsn()));

                            setValue(ShipConfig.BUNDLE_ID, convertNullToEmptyString("" + rs.getResultData().getBundleId()));

                            System.out.println("Original totalcharge=" +
                                    (
                                            rs.getResultData().getApportionedTotal().getAmount()
                                    )
                            );
                            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, getClientID());
                            boolean doUSPSRateStuff = true;
                            if (client.getUspsAddedMarginPct().compareTo(BigDecimal.ZERO) == 1 && getAssignedServiceCode().contains("USPS")) {
                                doUSPSRateStuff = false;
                                Double fakePrice = getTotalForTempShipper(this, ManifestingManager.getRatingShipperForLocation(LocationCode));
                                System.out.println("We have the temp price");
                                Double realPrice = rs.getResultData().getApportionedTotal().getAmount().doubleValue();
                                System.out.println("We have the real price");
                                BigDecimal percent = client.getUspsAddedMarginPct();
                                System.out.println("The percent: " + percent);

                                Double price = fakePrice + (fakePrice * percent.doubleValue());
                                System.out.println("The Real price: " + realPrice);

                                System.out.println("This is what we are recording for the add to pct :" + OWDUtilities.roundFloat(price.floatValue()));

                                setValue(ShipConfig.TOTAL, "" + (new Double(getValueAsString("OWDINSURANCECOST")).doubleValue() + OWDUtilities.roundFloat(price.floatValue())));

                            }


                            if (doUSPSRateStuff) {
                                System.out.println("Doing USPS stuff ");
                                System.out.println(getAssignedServiceCode());

                                if (getAssignedServiceCode().equals("TANDATA_USPS.USPS.PRIORITY") || getAssignedServiceCode().equals("TANDATA_USPS.USPS.FIRST") || getAssignedServiceCode().equals("TANDATA_USPS.USPS.EXPR_ADDR")) {
                                    System.out.println(getValueAsString("PACKAGING"));
                                    if (getValueAsString("PACKAGING").equalsIgnoreCase("USPS_PRIORITY_MAIL_FLAT_RATE_PADDED_ENV")) {
                                        //VH and OWD get Commercial Plus rate  and midco
                                        System.out.println("Doing +++++++++++++++++++++ Rates");
                                        setValue(ShipConfig.TOTAL, "" + (new Double(getValueAsString("OWDINSURANCECOST")).doubleValue() + rs.getResultData().getApportionedTotal().getAmount().doubleValue()));
                                    } else {
                                        //get commercial base rate
                                        Double fakePrice = getTotalForTempShipper(this, ManifestingManager.getRatingShipperForLocation(LocationCode));
                                        Double realPrice = rs.getResultData().getApportionedTotal().getAmount().doubleValue();
                                        System.out.println(rs.getResultData().getApportionedSpecial().getValue());
                                        System.out.println(rs.getResultData().getApportionedTotal().getValue());


                                        BigDecimal percent = client.getUspsPriorityDiscountSharePct();
                                        if (fakePrice > realPrice & (percent.compareTo(BigDecimal.ZERO) == 1)) {
                                            System.out.println("The percentage of Discount Share is " + percent);
                                            System.out.println("Real Ship Price: " + realPrice);
                                            System.out.println("Fake ship price" + fakePrice);
                                            Double price = fakePrice - ((fakePrice - realPrice) * percent.doubleValue());
                                            System.out.println("This is our new price not rounded: " + price);
                                            System.out.println("This is what we will record: " + OWDUtilities.roundFloat(price.floatValue()));
                                            setValue(ShipConfig.TOTAL, "" + (new Double(getValueAsString("OWDINSURANCECOST")).doubleValue() + OWDUtilities.roundFloat(price.floatValue())));
                                        } else {


                                            setValue(ShipConfig.TOTAL, "" + (new Double(getValueAsString("OWDINSURANCECOST")).doubleValue() + fakePrice));
                                        }
                                    }
                                } else {
                                    setValue(ShipConfig.TOTAL, "" + (new Double(getValueAsString("OWDINSURANCECOST")).doubleValue() + rs.getResultData().getApportionedTotal().getAmount().doubleValue()));
                                }
                            }
                            if (getValue(ShipConfig.BUNDLE_ID).equals("NONE"))
                                setValue(ShipConfig.BUNDLE_ID, "");
                            if ((getAssignedServiceCode().contains("FEDEX.SP")) & convertNullToEmptyString(rs.getResultData().getTrackingNumber2()).length() > 0) {
                                System.out.println("Smarty post gotta get a different tracking number");
                                System.out.println(convertNullToEmptyString(rs.getResultData().getTrackingNumber2()));
                                System.out.println(convertNullToEmptyString(rs.getResultData().getTrackingNumber()));
                                setValue(ShipConfig.TRACKING_NUMBER, convertNullToEmptyString(rs.getResultData().getTrackingNumber2()));
                            } else {
                                setValue(ShipConfig.TRACKING_NUMBER, convertNullToEmptyString(rs.getResultData().getTrackingNumber()));
                                System.out.println("here is our Tracking info: " + convertNullToEmptyString(rs.getResultData().getTrackingNumber()));
                                System.out.println(getValue(ShipConfig.TRACKING_NUMBER));
                            }
                            //     setValue(ShipConfig.SHIPPED_PKGS, packages);

                            getConsigneeNA().setContact(StringEscapeUtils.unescapeXml(getConsigneeNA().getContact()));
                            getConsigneeNA().setAddress1(StringEscapeUtils.unescapeXml(getConsigneeNA().getAddress1()));
                            getConsigneeNA().setAddress2(StringEscapeUtils.unescapeXml(getConsigneeNA().getAddress2()));
                            getConsigneeNA().setCity(StringEscapeUtils.unescapeXml(getConsigneeNA().getCity()));
                            getConsigneeNA().setCompany(StringEscapeUtils.unescapeXml(getConsigneeNA().getCompany()));
                            setValue(ShipConfig.CONSIGNEE, getConsigneeNA());
                            //  shipList.add(shipRec);
                        }*/

                        System.out.println("In the other stuff");
                        List<PackageResult> packages = response.getResult().getPackageResults().getItem();

                        System.out.println("going to loop the results");
                        int packListKey = 0;
                        for (PackageResult rs : packages) {


                            AMPConnectShipShipment shipRec = thePackages.get(packListKey);
                            // shipRec.printDictionaryValues();
                            System.out.println("Hello");
                            System.out.println(rs.getExternalKey());
                            System.out.println(rs.getResultData().getShipperReference());
                            System.out.println(rs.getResultData().getExternalKey());
                            System.out.println(rs.getResultData().getBarCode2());
                            System.out.println(rs.getResultData().getBarCode3());
                            System.out.println(rs.getResultData().getPackageListId());
                            System.out.println("REf:");
                            System.out.println(getValueAsString(ShipConfig.SHIPPER_REFERENCE));
                            System.out.println("Barcode");
                            System.out.println(rs.getResultData().getBarCode());
                            if (!rs.getExternalKey().toString().equals(shipRec.getValueAsString("PACKAGE_BARCODE"))) {
                                System.out.println("Well something is silly but i don't know");
                                    //todo double check and add lookup for proper shiprec
                            }
                            System.out.println("MSN");
                            shipRec.setValue(ShipConfig.MSN, convertNullToEmptyString(rs.getResultData().getMsn() + ""));
                            System.out.println("BundleID");
                            shipRec.setValue(ShipConfig.BUNDLE_ID, convertNullToEmptyString(rs.getResultData().getBundleId() + ""));
                            try {
                                System.out.println("Original totalcharge=" +
                                                (
                                                        rs.getResultData().getApportionedTotal().getAmount()
                                                )


                                );
                                System.out.println(rs.getResultData().getApportionedBase().getAmount());
                                System.out.println(rs.getResultData().getApportionedSpecial().getAmount());
                            }catch(Exception e){
                                if(shipRec.getAssignedServiceCode().equals("CONNECTSHIP_DHL.DHL.WPX")){
                                    DHL3rdParty = true;
                                }
                                System.out.println("No amounts, probaby DHL 3rd party");
                            }

                          try{
                              System.out.println(rs.getResultData().getApportionedDiscount().getAmount());
                          }catch(Exception e){
                              System.out.println("no discount");
                          }

                            try {
                                System.out.println(rs.getResultData().getAppointmentDeliveryFee().getAmount());
                            } catch (Exception e) {
                                System.out.println("No deliveryFee");
                            }
                            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, shipRec.getClientID());
                            boolean doUSPSRateStuff = true;
                          /*
                          Removed 7-12-15 for surchare addition

                          if (client.getUspsAddedMarginPct().compareTo(BigDecimal.ZERO) == 1 && shipRec.getAssignedServiceCode().contains("USPS")) {
                                doUSPSRateStuff = false;
                                //Double fakePrice = getTotalForTempShipper(shipRec, ManifestingManager.getRatingShipperForLocation(LocationCode));
                                Double fakePrice = AMPgetTotalForTempShipper(thePackages.get(0), ManifestingManager.getRatingShipperForLocation(LocationCode), packListKey,LocationCode);
                                Double realPrice = rs.getResultData().getApportionedTotal().getAmount().doubleValue();
                                BigDecimal percent = client.getUspsAddedMarginPct();
                                System.out.println("The percent: " + percent);

                                Double price = fakePrice + (fakePrice * percent.doubleValue());
                                System.out.println("The Real price: " + realPrice);

                                System.out.println("This is what we are recording for the add to pct :" + OWDUtilities.roundFloat(price.floatValue()));

                                shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + OWDUtilities.roundFloat(price.floatValue())));

                            }*/


                            if (doUSPSRateStuff) {
                                System.out.println("Doing USPS stuff ");
                                System.out.println(shipRec.getAssignedServiceCode());
                                System.out.println(client.getUspsPriorityExpressPackFee());
                                if (shipRec.getAssignedServiceCode().equals("TANDATA_USPS.USPS.PRIORITY_CUBIC") ||shipRec.getAssignedServiceCode().equals("TANDATA_USPS.USPS.PRIORITY") || shipRec.getAssignedServiceCode().equals("TANDATA_USPS.USPS.EXPR_ADDR")) {


                                    System.out.println(shipRec.getValueAsString("PACKAGING"));
                                    if (shipRec.getValueAsString("PACKAGING").equalsIgnoreCase("USPS_PRIORITY_MAIL_FLAT_RATE_PADDED_ENV")) {
                                        //VH and OWD get Commercial Plus rate  and midco
                                        System.out.println("Doing +++++++++++++++++++++ Rates");
                                        shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + rs.getResultData().getApportionedTotal().getAmount().doubleValue()));
                                    } else {
                                        //get commercial base rate
                                       // Double fakePrice = getTotalForTempShipper(shipRec, ManifestingManager.getRatingShipperForLocation(LocationCode));
                                       /*
                                        removed 7-12-15 for surcharge

                                        Double fakePrice = AMPgetTotalForTempShipper(thePackages.get(0), ManifestingManager.getRatingShipperForLocation(LocationCode),packListKey,LocationCode);
                                        Double realPrice = rs.getResultData().getApportionedTotal().getAmount().doubleValue();
                                        System.out.println(rs.getResultData().getApportionedSpecial().getValue());
                                        System.out.println(rs.getResultData().getApportionedTotal().getValue());


                                        BigDecimal percent = client.getUspsPriorityDiscountSharePct();
                                        if (fakePrice > realPrice & (percent.compareTo(BigDecimal.ZERO) == 1)) {
                                            System.out.println("The percentage of Discount Share is " + percent);
                                            System.out.println("Real Ship Price: " + realPrice);
                                            System.out.println("Fake ship price" + fakePrice);
                                            Double price = fakePrice - ((fakePrice - realPrice) * percent.doubleValue());
                                            System.out.println("This is our new price not rounded: " + price);
                                            System.out.println("This is what we will record: " + OWDUtilities.roundFloat(price.floatValue()));
                                            shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + OWDUtilities.roundFloat(price.floatValue())));
                                        } else {


                                            shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + fakePrice));
                                        }*/

                                        //Do the added surcharge
                                        System.out.println(rs.getResultData().getApportionedSpecial().getValue());
                                        System.out.println(rs.getResultData().getApportionedTotal().getValue());
                                        System.out.println(rs.getResultData().getApportionedBase().getAmount().doubleValue());
                                        String sql = "select dbo.udf_getapirategroup(client_fkey,'USPS_ADD_PERCENT',facility_code,group_name) from owd_order where order_id = :orderId";

                                        System.out.println(getValueAsString("OWDORDERID"));
                                        Query q = HibernateSession.currentSession().createSQLQuery(sql);
                                        q.setParameter("orderId",getValueAsString("OWDORDERID"));
                                        List l = q.list();
                                        Double USPSAddPer = 0.0d;
                                        if(l.size()>0){
                                            USPSAddPer =Double.parseDouble(l.get(0).toString());
                                        }
                                            if(USPSAddPer>0d){
                                                System.out.println("Doing adding");
                                                USPSAddPer = USPSAddPer + 1d;
                                                System.out.println(rs.getResultData().getApportionedBase().getAmount().doubleValue()*USPSAddPer);
                                                System.out.println(Double.parseDouble(shipRec.getValueAsString("OWDINSURANCECOST")) + (rs.getResultData().getApportionedBase().getAmount().doubleValue()*USPSAddPer) + rs.getResultData().getApportionedSpecial().getAmount().doubleValue() );


                                                shipRec.setValue(ShipConfig.TOTAL, "" + new Double(Double.parseDouble(shipRec.getValueAsString("OWDINSURANCECOST")) + rs.getResultData().getApportionedBase().getAmount().doubleValue()*USPSAddPer + rs.getResultData().getApportionedSpecial().getAmount().doubleValue()));

                                            }else {

                                                if (client.getUspsPriorityExpressPackFee().compareTo(BigDecimal.ZERO) == 1) {
                                                    System.out.println("Doing fee");
                                                    System.out.println(Double.parseDouble(shipRec.getValueAsString("OWDINSURANCECOST")) + rs.getResultData().getApportionedBase().getAmount().doubleValue() + rs.getResultData().getApportionedSpecial().getAmount().doubleValue() + client.getUspsPriorityExpressPackFee().doubleValue());


                                                    shipRec.setValue(ShipConfig.TOTAL, "" + new Double(Double.parseDouble(shipRec.getValueAsString("OWDINSURANCECOST")) + rs.getResultData().getApportionedBase().getAmount().doubleValue() + rs.getResultData().getApportionedSpecial().getAmount().doubleValue() + client.getUspsPriorityExpressPackFee().doubleValue()));
                                                } else {
                                                    shipRec.setValue(ShipConfig.TOTAL, "" + new Double(Double.parseDouble(shipRec.getValueAsString("OWDINSURANCECOST")) + rs.getResultData().getApportionedBase().getAmount().doubleValue() + rs.getResultData().getApportionedSpecial().getAmount().doubleValue()));

                                                }
                                            }
                                    }
                                    System.out.println("This is what we recorded");
                                    System.out.println(shipRec.getValueAsString(ShipConfig.TOTAL));
                                } else {
                                    System.out.println("not postal");
                                    if(DHL3rdParty){
                                        shipRec.setValue(ShipConfig.TOTAL,"0.0");
                                    }else {
                                        if(null==rs.getResultData().getApportionedBase()){

                                            Double base;
                                            if(null == rs.getResultData().getBase()){
                                              base = 0.0d;
                                            }else{
                                                base = rs.getResultData().getBase().getAmount().doubleValue();
                                            }
                                            Double special;
                                            if(null == rs.getResultData().getSpecial()){
                                                special = 0.0d;
                                            }else{
                                                special = rs.getResultData().getSpecial().getAmount().doubleValue();
                                            }

                                            shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + base + special));

                                        }else{
                                            if(null==rs.getResultData().getApportionedSpecial()) {
                                                System.out.println("No special, just regular");
                                                shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + rs.getResultData().getApportionedBase().getAmount().doubleValue()));
                                            }else{
                                                shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + rs.getResultData().getApportionedBase().getAmount().doubleValue() + rs.getResultData().getApportionedSpecial().getAmount().doubleValue()));

                                            }
                                        }

                                    }
                                }
                            }
                            System.out.println("77");
                            if (shipRec.getValue(ShipConfig.BUNDLE_ID).equals("NONE"))
                                shipRec.setValue(ShipConfig.BUNDLE_ID, "");
                            if ((shipRec.getAssignedServiceCode().contains("FEDEX.SP")) & convertNullToEmptyString(rs.getResultData().getTrackingNumber2()).length() > 0) {
                                System.out.println("Smarty post gotta get a different tracking number");
                                System.out.println(convertNullToEmptyString(rs.getResultData().getTrackingNumber2()));
                                System.out.println(convertNullToEmptyString(rs.getResultData().getTrackingNumber()));
                                shipRec.setValue(ShipConfig.TRACKING_NUMBER, convertNullToEmptyString(rs.getResultData().getTrackingNumber2()));
                            } else {
                                shipRec.setValue(ShipConfig.TRACKING_NUMBER, convertNullToEmptyString(rs.getResultData().getTrackingNumber()));
                            }
                            //     setValue(ShipConfig.SHIPPED_PKGS, packages);

                            getConsigneeNA().setContact(StringEscapeUtils.unescapeXml(getConsigneeNA().getContact()));
                            getConsigneeNA().setAddress1(StringEscapeUtils.unescapeXml(getConsigneeNA().getAddress1()));
                            getConsigneeNA().setAddress2(StringEscapeUtils.unescapeXml(getConsigneeNA().getAddress2()));
                            getConsigneeNA().setCity(StringEscapeUtils.unescapeXml(getConsigneeNA().getCity()));
                            getConsigneeNA().setCompany(StringEscapeUtils.unescapeXml(getConsigneeNA().getCompany()));
                            shipRec.setValue(ShipConfig.CONSIGNEE, getConsigneeNA());
                            shipList.add(shipRec);
                            packListKey++;
                        }
                    }


                //moved to postShipment method



            //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            return shipList;

        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {


        }

    }

    public double getTTEmp(String LocationCode) throws Exception {
        addPackageForRating(1, 1, ManifestingManager.getRatingShipperForLocation(LocationCode), LocationCode);
        if (this.getAssignedServiceCode().equalsIgnoreCase("TANDATA_USPS.USPS.EXPR_ADDR")) {
            System.out.println("resting addressee just because it is being goofy");
            this.setAssignedServiceCode("TANDATA_USPS.USPS.EXPR");
        }

        return getTotalForTempShipper(this, ManifestingManager.getRatingShipperForLocation(LocationCode), LocationCode);

    }
    private Map<Integer,Double> tempPrices = new HashMap<Integer, Double>();
    private double AMPgetTotalForTempShipper(AMPConnectShipShipment shipRec, String shipper,int packageKey,String LocationCode) throws Exception {
        double totalCharge = 0.00;
        boolean cubicBack = false;
         if(shipRec.tempPrices.size()==0){
             System.out.println("We have no temps get some!!");
        // shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + (new Float(convertNullToEmptyString(rs.getPACKAGEBASECHARGES().getMONETARYVALUE()).replaceAll(",", "")).floatValue() + new Float(convertNullToEmptyString(rs.getPACKAGESERVICEOPTIONSCHARGES().getMONETARYVALUE()).replaceAll(",", "")).floatValue())));
        System.out.println(shipRec.getRequest().getPackages().getItem().get(0).getDimension().getHeight());
        System.out.println(shipRec.getRequest().getPackages().getItem().get(0).getDimension().getLength());
        System.out.println(shipRec.getRequest().getPackages().getItem().get(0).getDimension().getWidth());
        System.out.println(shipRec.getRequest().getPackages().getItem().get(0).getDimension().getUnit());

        String oldShipper = shipRec.getRequest().getPackages().getItem().get(0).getShipper();
        System.out.println("oldShipper=" + oldShipper);
             System.out.println("Packages in temp shipment=" + shipRec.getRequest().getPackages().getItem().size());

        //set new shipper
        shipRec.getRequest().getPackages().getItem().get(0).setShipper(shipper);
        //set closeoutmode
        shipRec.getRequest().setCloseOutMode("0");
             System.out.println(shipRec.getAssignedServiceCode());
             System.out.println(shipRec.getRequest().getService());

             if(shipRec.getRequest().getService().contains("CUBIC")){
                 shipRec.getRequest().setService("TANDATA_USPS.USPS.PRIORITY");
                 cubicBack = true;
             }
        System.out.println(shipRec.getValue("SHIPDATE"));
        System.out.println(shipRec.getRequest().getDefaults().getShipdate());
        if (null == shipRec.getRequest().getDefaults().getShipdate()) {
            System.out.println("We are in the ifffffyyyyyy");
            String shipDate = getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode);
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(dformat.parse(shipDate));
            shipRec.getRequest().getDefaults().setShipdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
        }
        try {
            ShipResponse response = shipRec.getCSShipmentResponse();

            //restore modified request values
            shipRec.getRequest().getPackages().getItem().get(0).setShipper(oldShipper);
            shipRec.getRequest().setCloseOutMode("1");
            if (response.getResult().getCode() != 0) {

                throw new Exception("Ship failed because: " + response.getResult().getMessage() + " (" + response.getResult().getCode() + ")");


            } else {

                List<PackageResult> packages = response.getResult().getPackageResults().getItem();
                int packageCounter = 0;
               for(PackageResult rs:packages){
                   System.out.println(packageCounter);
                   System.out.println(rs.getResultData().getApportionedTotal().getAmount().doubleValue());
                   System.out.println("Settting the map");
                   if (rs.getResultData().getApportionedTotal().getAmount().doubleValue() < 0.01) {
                       throw new Exception("Unable to obtain commercial rate for package!");
                   }
                   shipRec.tempPrices.put(packageCounter,rs.getResultData().getApportionedTotal().getAmount().doubleValue());
                   packageCounter++;
               }
               /* System.out.println("list of rated packages size=" + packages.size());
                PackageResult rs = packages.get(0);


                totalCharge = (rs.getResultData().getApportionedTotal().getAmount().doubleValue());
                System.out.println("returning new total charge:" + totalCharge);*/

            }



        } catch (Exception ex) {

            shipRec.getRequest().getPackages().getItem().get(0).setShipper(oldShipper);
            shipRec.getRequest().setCloseOutMode("1");
            throw ex;
        }
         }
        System.out.println("We are returning prices for packageKey" + packageKey);
if (cubicBack && getClientID() != 596){
    shipRec.getRequest().setService("TANDATA_USPS.USPS.PRIORITY_CUBIC");
}
        return shipRec.tempPrices.get(packageKey);
    }

    private double getTotalForTempShipper(AMPConnectShipShipment shipRec, String shipper,String LocationCode) throws Exception {
        double totalCharge = 0.00;

        // shipRec.setValue(ShipConfig.TOTAL, "" + (new Double(shipRec.getValueAsString("OWDINSURANCECOST")).doubleValue() + (new Float(convertNullToEmptyString(rs.getPACKAGEBASECHARGES().getMONETARYVALUE()).replaceAll(",", "")).floatValue() + new Float(convertNullToEmptyString(rs.getPACKAGESERVICEOPTIONSCHARGES().getMONETARYVALUE()).replaceAll(",", "")).floatValue())));
        System.out.println(shipRec.getRequest().getPackages().getItem().get(0).getDimension().getHeight());
        System.out.println(shipRec.getRequest().getPackages().getItem().get(0).getDimension().getLength());
        System.out.println(shipRec.getRequest().getPackages().getItem().get(0).getDimension().getWidth());
        System.out.println(shipRec.getRequest().getPackages().getItem().get(0).getDimension().getUnit());

        String oldShipper = shipRec.getRequest().getPackages().getItem().get(0).getShipper();
        System.out.println("oldShipper=" + oldShipper);
        System.out.println("Packages in temp shipment=" + shipRec.getRequest().getPackages().getItem().size());

        //set new shipper
        shipRec.getRequest().getPackages().getItem().get(0).setShipper(shipper);
        //set closeoutmode
        shipRec.getRequest().setCloseOutMode("0");
        System.out.println(shipRec.getValue("SHIPDATE"));
        System.out.println(shipRec.getRequest().getDefaults().getShipdate());
        if (null == shipRec.getRequest().getDefaults().getShipdate()) {
            System.out.println("We are in the ifffffyyyyyy");
            String shipDate = getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode);
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(dformat.parse(shipDate));
            shipRec.getRequest().getDefaults().setShipdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
        }
        try {
            XStream x = new XStream();
            System.out.println(x.toXML(shipRec.getRequest()));
            ShipResponse response = shipRec.getCSShipmentResponse();

            //restore modified request values
            shipRec.getRequest().getPackages().getItem().get(0).setShipper(oldShipper);
            shipRec.getRequest().setCloseOutMode("1");
            if (response.getResult().getCode() != 0) {

                throw new Exception("Ship failed because: " + response.getResult().getMessage() + " (" + response.getResult().getCode() + ")");


            } else {

                List<PackageResult> packages = response.getResult().getPackageResults().getItem();

                System.out.println("list of rated packages size=" + packages.size());
                PackageResult rs = packages.get(0);


                totalCharge = (rs.getResultData().getApportionedTotal().getAmount().doubleValue());


            }

            if (totalCharge < 0.01) {
                throw new Exception("Unable to obtain commercial rate for package!");
            }

        } catch (Exception ex) {

            shipRec.getRequest().getPackages().getItem().get(0).setShipper(oldShipper);
            shipRec.getRequest().setCloseOutMode("1");
            throw ex;
        }

        System.out.println("returning new total charge:" + totalCharge);
        return totalCharge;
    }

    private ShipResponse getCSShipmentResponse(ShipRequest sreq) throws Exception {
        //   System.out.flush();
        ShipResponse resp = amp.ship(sreq);


        if (resp == null) {//&& bob != null) {
            throw new Exception("Server not updated, " +
                    "error happened when reading the ConnectShip response. \n" +
                    "Please restart the shipping application and search and " +
                    "void the shipment. Thank you!");

        }

        return resp;
    }

    private ShipResponse getCSShipmentResponse() throws Exception {
        //   System.out.flush();

        System.out.println(getRequest().getDefaults().getShipdate());
        ShipResponse resp = amp.ship(getRequest());


        if (resp == null) {//&& bob != null) {
            throw new Exception("Server not updated, " +
                    "error happened when reading the ConnectShip response. \n" +
                    "Please restart the shipping application and search and " +
                    "void the shipment. Thank you!");

        }

        return resp;
    }

    private double getUSPSPrivateInsuranceCost() throws Exception {
        return (isInternational() ? 1.20 : ("TRUE".equals("" + getValue(ShipConfig.PROOF))) ? .65 : .85);
    }


    /**
     * Clears all information of the last shipped package
     */


    public static List<Map<String, Object>> shipmentList = new ArrayList<Map<String, Object>>();


    /**
     * add current shipment information to shipmentList
     */

    private void addToShipmentList(Map<String, Object> lastmap) {

        HashMap<String, Object> amap = new HashMap<String, Object>();

        amap.putAll(lastmap);

        shipmentList.add(0, amap);

    }


    public static String padRight(String data, int len) {


        if (data == null)

            return "";

        if (data.length() >= len)

            return data.substring(0, len);


        StringBuffer sb = new StringBuffer();

        sb.append(data);

        int diff = (len - data.length());


        for (int i = 0; i < diff; i++) {

            sb.append(" ");

        }

        return sb.toString();

    }


    public static String padLeft(String data, int len) {


        if (data == null)

            return "";

        if (data.length() >= len)

            return data.substring(0, len);


        StringBuffer sb = new StringBuffer();


        int diff = (len - data.length());


        for (int i = 0; i < diff; i++) {

            sb.append(" ");

        }

        sb.append(data);

        return sb.toString();

    }

    static public Money getMoneyFromCurrencyString(String curr) {
        Money money = new Money();
        money.setCurrency("USD");
        money.setAmount(new BigDecimal(Double.parseDouble(curr)));
        return money;
    }

    static public String getTanDataDateForToday(int offsetDays, String shipMethodCode, String facility) {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        now.add(Calendar.DATE, offsetDays);

        if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            //DC6 second day stuff
            if(facility.equals("DC6")){
                System.out.println("DC6 time");
                System.out.println(shipMethodCode);

                // FDX standard or 2nd day
                if(shipMethodCode.contains("FEDEX.STD")||shipMethodCode.contains("FEDEX.2DA")){

                    //      4 - 4:30 CT --> same day ?
                    //      4:31 - 4:59 pm CT --> same day ?
                    //cutoff is 2:30 on Saturday make sure to account for timezone so looking before 16
                    if(now.get(Calendar.HOUR_OF_DAY)<17){
                        if(now.get(Calendar.HOUR_OF_DAY)==16){

                            if(now.get(Calendar.MINUTE)<31){
                                return formatter.format(now.getTime());
                            }
                        }else{
                            return formatter.format(now.getTime());
                        }
                    }
                }
            }
            // all facilities' SAT FDX
            if(shipMethodCode.contains("FEDEX")){
                //return saturday shipday for all fedex
                return formatter.format(now.getTime());
            }
            // The rest SAT --> Monday
            now.add(Calendar.DATE, 2);

        // SUN --> Monday
        } else if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            now.add(Calendar.DATE, 1);

        // UPS > 6 pm CT --> next day
        } else if (now.get(Calendar.HOUR_OF_DAY) > 18 && shipMethodCode.indexOf("UPS.") >= 0) {
            now.add(Calendar.DATE, 1);

        // UPS > 5 pm CT ??? --> next day
        } else if (now.get(Calendar.HOUR_OF_DAY) > 17 && shipMethodCode.indexOf("UPS.") < 0) {

            // Sean created on 5/20/2019
            // DC6 DHL > 7 pm PST (21pm CT) --> Next day. Else same day
            if(facility.equals("DC6") && shipMethodCode.contains("DHL")) {
                if (now.get(Calendar.HOUR_OF_DAY) > 21) {
                    now.add(Calendar.DATE, 1);
                }
            } else {
                now.add(Calendar.DATE, 1);
            }

        }

        // SAT --> MON
        //Check the time ofter we do next day for after 5 shipping
        if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            now.add(Calendar.DATE, 2);
        // SUN --> MON
        } else if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            now.add(Calendar.DATE, 1);
        }

        return formatter.format(now.getTime());

    }

    public static void removeMultiPackageShipmentPackage(String orderNum, String packageBarcode, Map<String, MultiPackageShipment> pendingShipmentsMap) throws Exception {

        getMultiPackShipment(orderNum, pendingShipmentsMap).removePackageElement(packageBarcode);
    }


    /*public static void addMultiPackageShipmentPackage(String orderNum, String packageBarcode, DataDictionary pkg, AMPConnectShipShipment shipper, int packageSequence, int packageTotalCount,Map<String, MultiPackageShipment> pendingShipmentsMap)


               throws Exception {
                System.out.println("orderNum: "+orderNum);
       System.out.println("packageBarcode: "+packageBarcode );

           if (!pendingShipmentsMap.containsKey(orderNum)) {
               addNewMultiShipment(orderNum, packageTotalCount, pendingShipmentsMap);

           }

           getMultiPackShipment(orderNum, pendingShipmentsMap).addNewPackageElement(packageBarcode, pkg, packageSequence, shipper);
       }*/
    public static void addNewMultiShipment(String orderNum, int packageTotalCount, Map<String, MultiPackageShipment> pendingShipmentsMap) throws Exception {
        if (pendingShipmentsMap.containsKey(orderNum)) {
            throw new Exception("Error attempting to start new, identical multiship");
        } else {
            if (pendingShipmentsMap.size() > 0) {
                throw new Exception("Error attempting to start new multiship - finish current one first");
            }
            MultiPackageShipment shipRecord = new MultiPackageShipment(orderNum, packageTotalCount);
            pendingShipmentsMap.put(orderNum, shipRecord);
        }

    }

    public static MultiPackageShipment getMultiPackShipment(String orderNum, Map<String, MultiPackageShipment> pendingShipmentsMap) {
        return pendingShipmentsMap.get(orderNum);
    }

    public static Collection<MultiPackageShipment.PackageData> getPackagesForShipment(String orderNum, Map<String, MultiPackageShipment> pendingShipmentsMap) throws Exception {
        System.out.println("Trying to get packages for order nUm " + orderNum);
        System.out.println(pendingShipmentsMap);

        if (pendingShipmentsMap.containsKey(orderNum)) {
            return ((MultiPackageShipment) pendingShipmentsMap.get(orderNum)).getPackageList();
        } else throw new Exception("Order " + orderNum + " not found in pending shipments list");
    }

    public void addPackageForRating(int packageCount, int packagesInShipment, String Shipper, String LocationCode) throws Exception {

        System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");


        double privateInsuranceCost = 0.00;
        try {


            String shipDate = getTanDataDateForToday(0, getAssignedServiceCode(),LocationCode);
            setRequest(new ShipRequest());
            DataDictionary defaults = new DataDictionary();
            getRequest().setDefaults(defaults);

            setValue(ShipConfig.SHIPDATE, shipDate);

            if (getConsigneeNA().getCountrySymbol().equals("CANADA")) {
                getConsigneeNA().setPostalCode(getConsigneeNA().getPostalCode().toUpperCase());
            }

            //System.out.println("country symbol=" + getConsigneeNA().getCountrySymbol());

            if (isInternational() && (getAssignedServiceCode().indexOf("UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX.") >= 0) && getValue(ShipConfig.TERMS).equals("SHIPPER")) {

                String country_symbol = ShipConfig.getConfig().getCountryName(getConsigneeNA().getCountrySymbol()).toUpperCase();

                ////System.out.println("Country symbol before set payment terms to DDU: "+country_symbol);

                if (country_symbol.equalsIgnoreCase("PUERTO_RICO") || country_symbol.equalsIgnoreCase("PUERTO RICO") || country_symbol.equalsIgnoreCase("US_VIRGIN_ISLANDS") || country_symbol.equalsIgnoreCase("US VIRGIN ISLANDS")) {

                    ;//don't change billing method to DDU for those countries

                } else {
                    if (getAssignedServiceCode().indexOf("FEDEX.SP") >= 0 && country_symbol.equalsIgnoreCase("GUAM")) {
                        getRequest().getDefaults().setTerms("SHIPPER");
                    } else {
                        System.out.println("SEtting ddu4");
                        getRequest().getDefaults().setTerms("DDU");
                    }

                }

            }


            if (isInternational()) {

                setValue(ShipConfig.SED_METHOD, "" + Constants.sedmNotRequired);
                setValue(ShipConfig.SED_EXEMPTION_NUMBER, "30.55(h)");
            }

            String s_code = getAssignedServiceCode().toUpperCase();

            String country_symbol = ShipConfig.getConfig().getCountryName(getConsigneeNA().getCountrySymbol());

            if (!isSuppressDC() && s_code.indexOf("USPS.") >= 0 && s_code.indexOf("PRIORITY") >= 0 && !isInternational()) {

                setValue(ShipConfig.PROOF, "TRUE");

            } else if (!isSuppressDC() && s_code.indexOf("USPS.") >= 0 && s_code.indexOf("PRIORITY") >= 0 && isInternational() && country_symbol.equalsIgnoreCase("PUERTO_RICO") && country_symbol.equalsIgnoreCase("PUERTO RICO") && country_symbol.equalsIgnoreCase("US_VIRGIN_ISLANDS") && country_symbol.equalsIgnoreCase("US VIRGIN ISLANDS")) {

                setValue(ShipConfig.PROOF, "TRUE");

            } else if (!isSuppressDC() && (s_code.indexOf("USPS.SPCL") > 0 || s_code.indexOf("USPS.BPM") > 0
                    || s_code.indexOf("USPS.FIRST") > 0) && ((Boolean) getValue("USEDC_FIRSTCLASS")).booleanValue()) {

                //check for height of package for first class mail - skip if too short...
                if (!(s_code.equals("TANDATA_USPS.USPS.FIRST")) || ((Double) getValue("HEIGHT_INCHES")).doubleValue() >= 0.25) {


                    if (!(((Float) getValue(ShipConfig.OWD_ORDER_TOTAL)).floatValue() < 10.00f && getClientID() == 160)) //BOR check
                    {
                        setValue(ShipConfig.PROOF, "TRUE");
                    } else {
                        System.out.println("Setting proof to falsec");
                        setValue(ShipConfig.PROOF, "FALSE");
                    }

                } else {
                    System.out.println("Setting proof to falsed");
                    setValue(ShipConfig.PROOF, "FALSE");
                }
            } else {
                System.out.println("Setting proof to falsee");
                setValue(ShipConfig.PROOF, "FALSE");

            }


            returnAddressBean defaultReturnAddress = getDefaultReturnAddress(LocationCode, HibernateSession.currentSession());


            getRequest().getDefaults().setShipper((String) getValue(ShipConfig.SHIPPER));


            getRequest().getDefaults().setTerms((String) getValue(ShipConfig.TERMS));


            // defatt.setCURRENCYCODE("USD");


            //  SHIPMENTSERVICEOPTIONS opts = new SHIPMENTSERVICEOPTIONS();

            if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.SATURDAY_DELIVERY))) {
                //System.out.println("assigning sat delivery");
                getRequest().getDefaults().setSaturdayDelivery(true);
            }


            DataDictionaryList pkgs = new DataDictionaryList();

            getRequest().setPackages(pkgs);

            //   }

            //calculate the total weight
            double cw = 0;
            try {
                cw = ((Double) getValue(ShipConfig.WEIGHT)).doubleValue();
            } catch (NumberFormatException nfe) {
                cw = 0;
            }
            double tw = 0;
            try {
                Object o = getValue(ShipConfig.TOTAL_WEIGHT);
                if (o != null) {
                    tw = ((Double) o).doubleValue();
                }
            } catch (NumberFormatException nfe) {
                tw = 0;
            }
            tw += cw;
            setValue(ShipConfig.TOTAL_WEIGHT, new Double(tw));
             System.out.println("Total Weight:  "+new Double(tw));
            //added on Jan 9, 2002 for shipping with pre-stored weight and dimension values
            //Hashtable pkg_ship_info = (Hashtable) getValue(ShipConfig.PKG_SHIP_INFO);
            //if(pkg_ship_info.


            DataDictionary pkg = new DataDictionary();
            //System.out.println("Dimension ck = " + getValueAsString(ShipConfig.DIMENSION));
            Dimensions dim = new Dimensions();
            dim.setUnit("IN");
            Double height = Double.parseDouble(getValueAsString("HEIGHT_INCHES"));
            Double width = Double.parseDouble(getValueAsString("WIDTH_INCHES"));
            Double depth = Double.parseDouble(getValueAsString("DEPTH_INCHES"));
             dim.setValue(height+"x"+width+"x"+depth);
            System.out.println(dim.getValue());
           /* if (depth > width & depth > height) {
                System.out.println("Normal dimmensions");
                dim.setHeight(BigDecimal.valueOf(Double.parseDouble(getValueAsString("HEIGHT_INCHES"))));
                dim.setWidth(BigDecimal.valueOf(Double.parseDouble(getValueAsString("WIDTH_INCHES"))));
                dim.setLength(BigDecimal.valueOf(Double.parseDouble(getValueAsString("DEPTH_INCHES"))));
            }
            if (width > depth & width > height) {
                System.out.println("width is now the length");
                dim.setHeight(BigDecimal.valueOf(Double.parseDouble(getValueAsString("HEIGHT_INCHES"))));
                dim.setLength(BigDecimal.valueOf(Double.parseDouble(getValueAsString("WIDTH_INCHES"))));
                dim.setWidth(BigDecimal.valueOf(Double.parseDouble(getValueAsString("DEPTH_INCHES"))));
            }
            if (height > width & height > depth) {
                System.out.println("height is now the length");
                dim.setLength(BigDecimal.valueOf(Double.parseDouble(getValueAsString("HEIGHT_INCHES"))));
                dim.setWidth(BigDecimal.valueOf(Double.parseDouble(getValueAsString("WIDTH_INCHES"))));
                dim.setHeight(BigDecimal.valueOf(Double.parseDouble(getValueAsString("DEPTH_INCHES"))));
            }*/
            //  dim.setDIMVALUE(getValueAsString(ShipConfig.DIMENSION));
            //  dim.setDIMUNITS("IN");
            pkg.setDimension(dim);

            //pkg.setTerms((String) getValue(ShipConfig.TERMS));

            pkg.setNofnSequence(packageCount);
            pkg.setNofnTotal(packagesInShipment);
            // ref.setNOFN_SEQUENCE(packageCount + "");

            // ref.setNOFN_TOTAL(packagesInShipment + "");
            //todo
            System.out.println("This is the reference");
            System.out.println(getValueAsString("PACKAGE_BARCODE"));
            pkg.setShipperReference(packagesInShipment > 1 ? getValueAsString("PACKAGE_BARCODE") : getValueAsString(ShipConfig.SHIPPER_REFERENCE));
            pkg.setExternalKey(getValueAsString("PACKAGE_BARCODE"));
            System.out.println(pkg.getShipperReference());
            //  ref.setSHIPPERREFERENCE(packagesInShipment > 1 ? getValueAsString("PACKAGE_BARCODE") : getValueAsString(ShipConfig.SHIPPER_REFERENCE));

            pkg.setConsigneeReference(getValueAsString(ShipConfig.CONSIGNEE_REFERENCE));
            pkg.setConsigneeEmail(getValueAsString(ShipConfig.SHIP_TO_EMAIL));

            //  ref.setCONSIGNEEREFERENCE(getValueAsString(ShipConfig.CONSIGNEE_REFERENCE));

            //  pkg.setREFERENCE(ref);
            Money insure = new Money();
            insure.setAmount(new BigDecimal(Double.parseDouble("0.00")));
            insure.setCurrency("USD");


            pkg.setProof(((String) getValue(ShipConfig.PROOF)).equalsIgnoreCase("TRUE"));
            if (!isInternational()) {
                if (isInsured() && (getAssignedServiceCode().indexOf(".UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX") >= 0) && getClientID() == 146) {

                    pkg.setProof(true);
                    setValue(ShipConfig.PROOF, "TRUE");
                    pkg.setProofRequireSignature(true);

                }

                if ((getAssignedServiceCode().indexOf(".UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX") >= 0) && getClientID() == 260) {

                    pkg.setProof(true);
                    setValue(ShipConfig.PROOF, "TRUE");
                    pkg.setProofRequireSignature(true);

                }

                if ((getAssignedServiceCode().indexOf(".UPS.") >= 0 || getAssignedServiceCode().indexOf("FEDEX") >= 0) && getClientID() == 315) {

                    pkg.setProof(true);
                    setValue(ShipConfig.PROOF, "TRUE");
                    pkg.setProofRequireSignature(true);

                }
                if(getAssignedServiceCode().contains("CONNECTSHIP_DHLGLOBALMAIL.DHL")){
                    pkg.setProof(false);
                    pkg.setProofRequireSignature(true);
                }
            }
            System.out.println("890");
            Float ordertotal = (Float) getValue(ShipConfig.OWD_ORDER_TOTAL);
            if (ordertotal == null) ordertotal = new Float(0.00f);
            System.out.println("891");
            ////System.out.println("proof test");
            if ((getAssignedServiceCode().indexOf("USPS.PRIORITY") >= 0) && 20.00f <= ((Float) ordertotal).floatValue() && getClientID() == 139) {

                pkg.setProof(true);
                setValue(ShipConfig.PROOF, "TRUE");
                pkg.setProofRequireSignature(true);

            }
            System.out.println("892");
            //todo
            if ((getAssignedServiceCode().indexOf(".UPS.") >= 0
                    || getAssignedServiceCode().indexOf("FEDEX") >= 0
                    || ((getAssignedServiceCode().indexOf("USPS.STD") < 0) &&
                    (getAssignedServiceCode().indexOf("USPS.EXPR") < 0) &&
                    (getAssignedServiceCode().indexOf("USPS.I_") < 0)))
                    && (((Boolean) getValue(ShipConfig.OWD_SIG_REQUESTED)).booleanValue()
                    && !isSuppressDC())
                    ) {
                setValue(ShipConfig.PROOF, "TRUE");

                pkg.setProof(true);
                pkg.setProofRequireSignature(true);
            }
            if (getConsigneeNA().getCountrySymbol().equalsIgnoreCase("PALAU")) {
                System.out.println("PALAUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
                pkg.setProof(false);
                setValue(ShipConfig.PROOF, "FALSE");

                pkg.setProofRequireSignature(false);
            }
            ////System.out.println("proof test done");
            if (isSuppressDC()) {
                System.out.println("Actually setting the prood ");
                pkg.setProof(false);
                setValue(ShipConfig.PROOF, "FALSE");

                pkg.setProofRequireSignature(false);
            }//


            //    System.out.println("signature proof=" + proof.getPROOFSIGNATURE());

            System.out.println("893");
            String decValue = getValueAsString(ShipConfig.DECLARED_VALUE_AMOUNT);
            double insValue = 0.00;
            System.out.println(decValue);
            System.out.println("894");
            if (isInsured()) {


                try {
                    insValue = Double.parseDouble(decValue) / (1.00 * packagesInShipment);
                    System.out.println(insValue);
                    //     decValue=""+roundFloat((float)insValue,2);
                } catch (Exception ex) {
                    decValue = "0.00";
                }
                System.out.println("701");
                if (packagesInShipment > 1) {

                    insure.setAmount(new BigDecimal(Double.parseDouble("" + roundFloat((float) insValue, 2))));

                } else {
                    System.out.println("702");
                    insure.setAmount(new BigDecimal(Double.parseDouble(decValue)));


                }


                setValue("OWDINSURANCEVALUE", insure.getAmount().doubleValue() + "");
                System.out.println("703");
                if (getAssignedServiceCode().indexOf("USPS.") >= 0) {
                    System.out.println("704");
                    if (isInsured()
                            ) {  System.out.println("705");
                        double insuredAt = insure.getAmount().doubleValue();
                        System.out.println("706");
                        while (insuredAt >= 0.00) {
                            System.out.println("707");
                            privateInsuranceCost += getUSPSPrivateInsuranceCost();
                            System.out.println("708");
                            insuredAt -= 100.00;
                            System.out.println("709");
                        }


                    }
                } else {
                    pkg.setDeclaredValueAmount(insure);
                }
            }
            System.out.println("895");
//THIRD PARTY BILLING

//modified on Dec. 30, 2002 to allow reporting actual total

////System.out.println("Third Party Billing"+getValue(ShipConfig.THIRD_PARTY_BILLING));


            if (((String) getValue(ShipConfig.THIRD_PARTY_BILLING)).toUpperCase().equals("TRUE") && !getValue(ShipConfig.SHIPPER).toString().equalsIgnoreCase("DC7_QVC_JB")) {

                ////System.out.println("set Third Party Information\n");

                NameAddress third = new NameAddress();


                third.setContact(getThirdPartyNA().getContact());

                third.setCompany(getThirdPartyNA().getCompany());

                third.setAddress1(getThirdPartyNA().getAddress1());

                third.setAddress2(getThirdPartyNA().getAddress2());

                third.setCity(getThirdPartyNA().getCity());

                third.setStateProvince(getThirdPartyNA().getStateProvince());

                third.setCountrySymbol(getThirdPartyNA().getCountrySymbol());

                third.setPostalCode(getThirdPartyNA().getPostalCode());

                third.setPhone(getThirdPartyNA().getPhone());

                // pkgOpts.setBILLTHIRDPARTY(third);
                pkg.setThirdPartyBilling(true);
                pkg.setThirdPartyBillingAccount((String) getValue(ShipConfig.THIRD_PARTY_BILLING_ACCOUNT));
                pkg.setThirdPartyBillingAddress(third);

                ////System.out.println("third party contact: "+getBillingNA().getCompany()+"\n");


            }

            System.out.println("896");
            //todo add in billconsigneethirdparty
//INTL

            ////System.out.println("Is Iternataional  "+isInternational() );

            if (isInternational()) {
                System.out.println("We are in international");

                if (getAssignedServiceCode().indexOf("FEDEX") >= 0) {
                    if (getValueAsString(ShipConfig.FED_EX_CUSTOMS_ACCOUNT).length() > 0) {
                        System.out.println("We have a fedex account number");
                        // BILLCONSIGNEETHIRDPARTY thirdConsignee = new BILLCONSIGNEETHIRDPARTY();
                        // thirdConsignee.setCONSIGNEETPACCOUNT(getValueAsString(ShipConfig.FED_EX_CUSTOMS_ACCOUNT));
                        pkg.setConsigneeThirdPartyBillingAccount(getValueAsString(ShipConfig.FED_EX_CUSTOMS_ACCOUNT));
                        pkg.setConsigneeThirdPartyBilling(true);
                    }
                }

                //   int item_count = getItemData()[0].length;


                if (getAssignedServiceCode().indexOf("UPS.") >= 0) {

                    //   intl.setCOMMERCIAL_INVOICE_METHOD("0");
                    pkg.setCommercialInvoiceMethod("0");


                }
                System.out.println("897");
                System.out.println("This is the invoice method" + pkg.getCommercialInvoiceMethod());
                // intl.setINTLDESCRIPTION(convertNullToEmptyString((String) getValue(ShipConfig.DESCRIPTION)));
                pkg.setDescription(convertNullToEmptyString((String) getValue(ShipConfig.DESCRIPTION)));
                pkg.setSedMethod("0");
                if(getAssignedServiceCode().contains("USPS")==false) {
                    if (getConsigneeNA().getCountrySymbol().equalsIgnoreCase("CANADA")) {
                        pkg.setSedExemptionNumber("NO EEI 30.36");
                    } else {
                        pkg.setSedExemptionNumber("NO EEI 30.37 (a)");
                    }
                    System.out.println(pkg.getSedExemptionNumber() + "      CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                }
                pkg.setInvoiceFreight(getMoneyFromCurrencyString("1.00"));          //for fedex...
                System.out.println("1");
                pkg.setInvoiceInsuranceFee(getMoneyFromCurrencyString("1.00"));        //for fedex...
                System.out.println("1");
                pkg.setInvoiceOtherFee(getMoneyFromCurrencyString("0.00"));
                System.out.println("1");
                pkg.setInvoiceDiscount(getMoneyFromCurrencyString("0.00"));
                System.out.println("1");
                pkg.setExporterTaxId("46-0458580");
                System.out.println("1");
                pkg.setUltimateDestinationCountry(getConsigneeNA().getCountrySymbol());
                System.out.println("1");
                pkg.setDocumentsOnly(false);
                System.out.println("899");
                ////System.out.println(pkg.getTERMS());
                if ("DDU".equals(pkg.getTerms()) || "CONSIGNEE".equals(pkg.getTerms())) {
                    System.out.println(getAssignedCarrierCode());
                    System.out.println(country_symbol);
                    if (getAssignedServiceCode().indexOf("FEDEX.SP") >= 0 && country_symbol.equalsIgnoreCase("GUAM")) {
                        System.out.println("Settin shipper cause guam");
                        pkg.setTermsOfSale("SHIPPER");
                        setValue(ShipConfig.TERMS, "SHIPPER");
                        pkg.setTerms("SHIPPER");
                    } else {
                        System.out.println("SEtting ddu3");
                        pkg.setTermsOfSale("DDU");
                    }
                }
                System.out.println("900");
                if (getAssignedServiceCode().indexOf("UPS.") >= 0 && (getClientID() == 414)) {
                    pkg.setTerms("SHIPPER");
                    pkg.setTermsOfSale("SHIPPER");
                }

                // if (packagesInShipment < item_count) {
                //     if (packageCount == packagesInShipment) {
                CommodityList contents = new CommodityList();

                if (getItemData().length > 0) {
                    float dvalue = 0;
                    for (int i = 0; i < getItemData()[0].length; i++) {

                        Commodity item = new Commodity();

                        item.setProductCode(getItemData()[kTDItemSKU][i]);

                        float unitPrice = 0;

                        try {

                            unitPrice = new Float(getItemData()[kTDItemPrice][i]).floatValue();

                        } catch (NumberFormatException nfex) {

                            unitPrice = (float) 0.00;

                        }

                        if (unitPrice < 0.01) unitPrice = (float) 1.00;

                        int unitQty = 1;

                        try {

                            unitQty = new Integer(getItemData()[kTDItemQty][i]).intValue();

                        } catch (NumberFormatException nfex) {

                            unitQty = 1;

                        }

                        dvalue += (unitQty * unitPrice);

                        item.setQuantity(unitQty);

                        item.setUnitValue(getMoneyFromCurrencyString("" + unitPrice));

                        item.setDescription(getItemData()[kTDItemDesc][i].replaceAll("[^A-Za-z0-9 ]", ""));

                        if (pkg.getDescription().equals("") || pkg.getDescription().equalsIgnoreCase("Merchandise")) {
                            pkg.setDescription(getItemData()[kTDItemDesc][i]);
                        }
                        item.setQuantityUnitMeasure("PC");

                        Weight uwgt = new Weight();
                        uwgt.setAmount(new BigDecimal(Double.parseDouble((((Double) getValue(ShipConfig.WEIGHT)).doubleValue() / getItemData()[0].length) + "")));
                        uwgt.setUnit("LB");
                        item.setUnitWeight(uwgt);

                        item.setOriginCountry(getItemData()[kTDItemCountryOrigin][i]);

                        //QUANTITY_UNIT_MEASURE


                        contents.getItem().add(item);

                    }


                    pkg.setDeclaredValueCustoms(getMoneyFromCurrencyString("" + ((int) dvalue)));


                } else {
                    throw new Exception("No line items found for this shipment");
                }


                pkg.setCommodityContents(contents);


                setValue("OWDCUSTOMSSHIPMENTVALUE", pkg.getDeclaredValueCustoms().getAmount().doubleValue());


                if (isInsured()) {

                    double insuredValue = new Double(getValueAsString("OWDINSURANCEVALUE")).doubleValue();
                    double customsValue = pkg.getDeclaredValueCustoms().getAmount().doubleValue();

                    if (insuredValue > customsValue) {
                        // pkgOpts.getDECLAREDVALUE().setMONETARYVALUE("" + customsValue);
                        setValue("OWDINSURANCEVALUE", "" + customsValue);
                        privateInsuranceCost = 0.00;

                        if (getAssignedServiceCode().indexOf("USPS.") >= 0) {
                            if (isInsured()
                                    ) {
                                double insuredAt = insure.getAmount().doubleValue();
                                while (insuredAt >= 0.00) {
                                    privateInsuranceCost += getUSPSPrivateInsuranceCost();
                                    insuredAt -= 100.00;
                                }


                            }
                        } else {
                            pkg.getDeclaredValueAmount().setAmount(new BigDecimal(Double.parseDouble("" + customsValue)));
                        }


                    }


                }


            }

//OVERSIZE

            //      pkgOpts.setOVERSIZE(((String) getValue(ShipConfig.OVERSIZE)).toUpperCase());

//RETURNDELIVERY

            RETURNDELIVERY ret = new RETURNDELIVERY();
            // ret.setRETURNDELIVERYFLAG("FALSE");
            //  ret.setRETURNDELIVERYDESCRIPTION("Return Address");
            pkg.setReturnDelivery(false);

            NameAddress retaddr = new NameAddress();

            if ("true".equals("" + getValue(ShipConfig.RETURN_DELIVERY))) {

                pkg.setReturnDelivery(true);

                retaddr.setContact("OWD");

                retaddr.setCompany(getReturnNA().getCompany());

                retaddr.setAddress1(defaultReturnAddress.getAddress1());
                //
                retaddr.setAddress2("");

                retaddr.setCity(defaultReturnAddress.getCity());

                retaddr.setStateProvince(defaultReturnAddress.getState());

                retaddr.setPostalCode(defaultReturnAddress.getZip());

                retaddr.setCountrySymbol("UNITED_STATES");

                retaddr.setPhone("8662091413");

                pkg.setReturnAddress(retaddr);

            } else {
                pkg.setReturnDelivery(false);

                String test = getReturnNA().getPostalCode();

                ////System.out.println("postal code in returnNA: "+test);
                if (test != null && test.length() > 0) {


                    retaddr.setContact(getReturnNA().getContact().trim().length() < 1 ? "Returns" : getReturnNA().getContact());

                    retaddr.setCompany(getReturnNA().getCompany());

                    retaddr.setAddress1(getReturnNA().getAddress1());

                    retaddr.setAddress2(getReturnNA().getAddress2());

                    retaddr.setCity(getReturnNA().getCity());

                    retaddr.setStateProvince(getReturnNA().getStateProvince());

                    retaddr.setPostalCode(getReturnNA().getPostalCode());

                    retaddr.setCountrySymbol(getReturnNA().getCountrySymbol());

                    retaddr.setPhone(getReturnNA().getPhone());

                    pkg.setReturnAddress(retaddr);
                } else {

                    retaddr.setContact("OWD");

                    retaddr.setCompany("One World Distribution");

                    retaddr.setAddress1(defaultReturnAddress.getAddress1());
                    //
                    retaddr.setAddress2("");

                    retaddr.setCity(defaultReturnAddress.getCity());

                    retaddr.setStateProvince(defaultReturnAddress.getState());

                    retaddr.setPostalCode(defaultReturnAddress.getZip());

                    retaddr.setCountrySymbol("UNITED_STATES");

                    retaddr.setPhone("8662091413");

                    // retaddr.setRT
                    pkg.setReturnAddress(retaddr);

                }

            }


            if (isInsured()) {
                //  pkgOpts.setUSPS_SUPPRESSMMS("true");
            }


            if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.CALLTAG))) {


                //   ret = new RETURNDELIVERY();
                //    ret.setRETURNDELIVERYFLAG("TRUE");
                pkg.setReturnDelivery(true);
                getRequest().getDefaults().setReturnDeliveryNotificationDescription("Returned Items");
                retaddr = new NameAddress();
                pkg.setReturnDeliveryMethod("2");  //calltag, three pickups

                if (("" + getValue(ShipConfig.CALLTAG_DESCRIPTION)).contains("@")) {
                    pkg.setReturnDeliveryAddressEmail("" + getValue(ShipConfig.CALLTAG_DESCRIPTION));
                    pkg.setReturnDeliveryMethod("4");  //email label
                }

                String test = getReturnNA().getPostalCode();

                ////System.out.println("postal code in returnNA: "+test);
                if (test != null && test.length() > 0) {


                    retaddr.setContact("Customer Returns");

                    retaddr.setCompany(getReturnNA().getCompany());

                    retaddr.setAddress1(getReturnNA().getAddress1());

                    retaddr.setAddress2(getReturnNA().getAddress2());

                    retaddr.setCity(getReturnNA().getCity());

                    retaddr.setStateProvince(getReturnNA().getStateProvince());

                    retaddr.setPostalCode(getReturnNA().getPostalCode());

                    retaddr.setCountrySymbol(getReturnNA().getCountrySymbol());

                    retaddr.setPhone(getReturnNA().getPhone());

                    pkg.setReturnAddress(retaddr);
                }
                // pkg.setReturnAddress(ret);

/*

                CALLTAG calltag = new CALLTAG();
                calltag.setCALLTAGFLAG("TRUE");
                calltag.setCALLTAGDESCRIPTION((String) getValue(ShipConfig.CALLTAG_DESCRIPTION));
                calltag.setCALLTAGNUMBER((String) getValue(ShipConfig.CALLTAG_NUMBER));
                pkgOpts.setCALLTAG(calltag);
*/


                pkg.setProof(false);
                pkg.setProofRequireSignature(false);


            }


            Weight wgt = new Weight();

            wgt.setAmount(new BigDecimal(Double.parseDouble("" + getValue(ShipConfig.WEIGHT))));

            wgt.setUnit("LB");

            pkg.setWeight(wgt);
              System.out.println("Setting package weight 2222: "+wgt.getAmount());
            //System.out.println("ck packaging " + getValue(ShipConfig.PACKAGING));
            pkg.setPackaging("" + getValue(ShipConfig.PACKAGING));


            getRequest().getDefaults().setShipper(Shipper);
            setValue(ShipConfig.SHIPPER, Shipper);
            NameAddress cons = getConsigneeNA();

            //changed on Nov. 25

            ////System.out.println("Company name: "+cons.getCompany());
            //System.out.println("lllllllllllllllllllllllllllllllllllllllllllllll");
            if (cons.getCompany().length() < 2 || cons.getCompany().equalsIgnoreCase(cons.getContact()))

                cons.setResidential(true);

            else if (((String) cons.getContact()).indexOf(" ") >= 0 && cons.getCompany().equalsIgnoreCase(((String) cons.getContact()).substring(((String) cons.getContact()).indexOf(" "))))

                cons.setResidential(true);

            else

                cons.setResidential(false);

            pkg.setConsignee(cons);

            getRequest().getDefaults().setConsignee(cons);

            if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.FREIGHT_COLLECT))
                    ) {
                pkg.setConsigneeAccount((String) getValue(ShipConfig.CONSIGNEE_ACCOUNT));
                cons.setResidential(false);
            }

            if ("TRUE".equalsIgnoreCase((String) getValue(ShipConfig.CALLTAG))) {
                cons.setResidential(false);
            }
            //todo separate out multiship logic from one-package logic...
            setValue("OWDSERVICE", getAssignedServiceCode());
            setValue("OWDSERVICENAME", getAssignedServiceName());
            setValue("OWDRETURNADDR", getReturnNA());
            setValue("OWDINSURANCECOST", "" + privateInsuranceCost);
            //System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqr");
            System.out.println("Package Count" + packageCount);
            System.out.println("Packages in Shipment" + packagesInShipment);
            System.out.println("Assigned ServiceCode" + getAssignedServiceCode());
            System.out.println("Barcode " + getValueAsString("PACKAGE_BARCODE"));

            //always add current package
            //    System.out.println("Apparently this is what is being done?!?!?!?!?!");

            getRequest().getPackages().getItem().add(pkg);
            getRequest().setCloseOutMode("0");
            //  System.out.println("Setting Close out mode");


            getRequest().setService(getAssignedServiceCode());


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Bad Fake shipping numbers" + e.getMessage());
        }

    }

    private returnAddressBean getDefaultReturnAddress(String shipper, Session sess) throws Exception {
        returnAddressBean rb = new returnAddressBean();
        String sql = "select address, city,state,zip from owd_facilities where loc_code = :loc";
        try {
            Query q = sess.createSQLQuery(sql);
            q.setParameter("loc", shipper);
            List results = q.list();
            if (results.size() > 0) {
                Object row = results.get(0);
                Object[] data = (Object[]) row;
                rb.setAddress1(data[0].toString());
                rb.setCity(data[1].toString());
                rb.setState(data[2].toString());
                rb.setZip(data[3].toString());
                rb.setPhone("6058457172");

            } else {
                throw new Exception("No records for return");
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("unable to fill default return address");
        }


        return rb;
    }

    public static boolean checkAndFixNoPackageLineItems(String orderNum) throws Exception {
        boolean goodToShip = true;
        System.out.println("This is the order number we are dealing with" + orderNum);

        String sql = "execute sp_checkAndFixNoPackLines :orderNum ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setString("orderNum", orderNum);
        List results = q.list();
        boolean single = false;
        boolean zeroItems = false;
        String packageId = "";
        System.out.println("This is the result size " + results.size());
        if (results.size() > 0) {

            if (results.size() == 1) {
                System.out.println("INside single");
                single = true;
            }
            for (Object row : results) {
                Object[] data = (Object[]) row;
                if (data[2].toString().equals("0")) {
                    zeroItems = true;
                    packageId = data[1].toString();
                }

            }
            if (single & zeroItems) {
                //we need to try and fix
                System.out.println("We are going to attempt to fix somethings :)");
                String sql2 = "insert into package_line (package_fkey, owd_line_item_fkey,pack_quantity)\n" +
                        "\n" +
                        "select :packageId, line_item_id, quantity_actual from owd_line_item where order_fkey = (select order_id from owd_order where order_num = :orderNum) and is_parent = 0";
                Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
                qq.setParameter("packageId", packageId);
                qq.setParameter("orderNum", orderNum);
                int i = qq.executeUpdate();
                if (i > 0) {
                    System.out.println("looks like it worked ship away!!");
                    HibUtils.commit(HibernateSession.currentSession());
                    goodToShip = true;
                    return goodToShip;
                }
            }
            if (!zeroItems) {
                goodToShip = true;
            }


        }


        return goodToShip;
    }


    private static boolean _postShipment(postShipmentBean psb) throws Exception{
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TimeStamp ts = new TimeStamp("savepackage");



        try {
            cxn = ConnectionManager.getConnection();

//System.out.println("initializing pack: "+getStringParam(req,"order_fkey","")+":"+getStringParam(req,"line_index","")+":"+getStringParam(req,"msn",""));

            ts.print("savepackage-lo query start"  +psb.getPackageBarcode());
            System.out.println(":Insurance Cost:" + psb.getInsCost());
            System.out.println(":Insurance Value:" + psb.getInsValue());
            System.out.println("MSN: "+psb.getMsn());
            System.out.println("1");
            System.out.println("Line index: "+psb.getLineIndex());
            if(null==psb.getBundleId()){
                psb.setBundleId("0");
            }
            System.out.println("Bundel Id: "+psb.getBundleId());
            com.owd.core.business.order.Package pack = new com.owd.core.business.order.Package("0",psb.getOrderFkey(), psb.getLineIndex(),
                   psb.getTracking(), psb.getWeight(), psb.getShipper().equals("ROW") ? "0.00" : psb.getTotalBilled(),
                    psb.getCostOfGoods(), "", psb.getMsn(), "0", "0", "", "", "", "",
                    psb.getBundleId(),psb.getCarrierCode(),psb.getServiceCode(),psb.getLabelCode());
            if(psb.isThirdPartyBilling()){
                pack.isThirdParty = "1";
            }
            //System.out.println("initialized pack: "+getStringParam(req,"order_fkey","")+":"+getStringParam(req,"line_index","")+":"+getStringParam(req,"msn",""));

            System.out.println("2");

            //System.out.println("save pack: "+getStringParam(req,"order_fkey","")+":"+getStringParam(req,"line_index","")+":"+getStringParam(req,"msn",""));
            pack.dbsave(cxn, psb.getUser(), psb.getShipper());
            ts.print("savepackage-lo pack.dbsave done " + psb.getPackageBarcode());
            double insuredAmount = 0.00;
            double insuranceCost = 0.00;

            try {
                insuredAmount = new Double(psb.getInsValue()).doubleValue();
            } catch (Exception ex) {

            }
            try {
                insuranceCost = new Double(psb.getInsCost()).doubleValue();
            } catch (Exception ex) {

            }
            System.out.println("ordertrack" + pack.order_track_id);
            System.out.println("order_fkey" + psb.getOrderFkey());
            System.out.println("msn" + psb.getMsn());
            System.out.println("packBarcode" + psb.getPackageBarcode());
            if (psb.getPackageBarcode().length() > 0) {
                /*stmt = cxn.prepareStatement("update package set ship_time=getdate(),order_track_fkey=?, ip_address=?," +
                        "owd_insurance_cost=?,insured_amount=?,weight_verify=?,customs_docs=? " +
                        "where pack_barcode=? ");
                */
                stmt = cxn.prepareStatement("exec sp_update_package_shipapp_with_weight ?,?,?,?,?,?,?,?;");
                stmt.setInt(1, new Integer(pack.order_track_id).intValue());
                stmt.setString(2, psb.getIpAddress());
                stmt.setDouble(3, insuranceCost);
                stmt.setDouble(4, insuredAmount);
                stmt.setString(5, psb.getWeightNotes());
                stmt.setInt(6, Integer.parseInt(psb.getCustomsDocs()));
                stmt.setString(7,psb.getPackageBarcode());
                System.out.println("The Weight: " + psb.getWeight());
                stmt.setFloat(8,Float.parseFloat(psb.getWeight()));
                stmt.executeUpdate();
                ts.print("savepackage-lo updateting package" + psb.getPackageBarcode());
                /*   stmt=cxn.prepareStatement("update package set owd_insurance_cost=?,insured_amount=? " +
                          "where pack_barcode=?");
                stmt.setDouble(1,insuranceCost);
stmt.setDouble(2,insuredAmount);
                stmt.setString(3,packBarcode);
                stmt.executeUpdate();*/


                stmt =cxn.prepareStatement("update package_order set packs_shipped=(packs_shipped+1) where owd_order_fkey=? and is_void=0");
                stmt.setInt(1, new Integer(psb.getOrderFkey()).intValue());
                stmt.executeUpdate();
                ts.print("savepackage-lo updateting package_order " +psb.getPackageBarcode());

            } else {
                stmt =cxn.prepareStatement("update package set ship_time=getdate(),order_track_fkey=?, ip_address=?," +
                        "owd_insurance_cost=?,insured_amount=?,weight_verify=?,customs_docs=? " +
                        " from package\n" +
                        "join package_order po on po.id=package.package_order_fkey and po.is_void=0 and owd_order_fkey=?\n" +
                        "join owd_order_track t on t.order_track_id=order_track_fkey and msn=?");


                stmt.setInt(1, new Integer(pack.order_track_id).intValue());
                stmt.setString(2, psb.getIpAddress());
                stmt.setDouble(3, insuranceCost);
                stmt.setDouble(4, insuredAmount);
                stmt.setString(5, psb.getWeightNotes());
                stmt.setInt(6, Integer.parseInt(psb.getCustomsDocs()));

                stmt.setInt(7, new Integer(psb.getOrderFkey()).intValue());
                stmt.setString(8, psb.getMsn());
                stmt.executeUpdate();
                ts.print("savepackage-lo updateting package with barcode" + psb.getPackageBarcode());

                stmt = cxn.prepareStatement("update package_order set packs_shipped=(packs_shipped+1) where owd_order_fkey=? and is_void=0");
                stmt.setInt(1, new Integer(psb.getOrderFkey()).intValue());
                stmt.executeUpdate();


                ts.print("savepackage-lo update backage_order with barcode " + psb.getPackageBarcode());

            }
            System.out.println(psb.getShipMethod());

            System.out.println(psb.getShipMethodName());
            System.out.println(psb.getShipMethodNameOriginal());

            String shipMethod = psb.getShipMethod();
            if (!(shipMethod.equals("SAME"))&&!psb.getSerivceCodeOriginal().contains("COM_OWD_FLATRATE")) {
                String shipMethodName = psb.getShipMethodName();
                String originalShipMethodName = psb.getShipMethodNameOriginal();

                Event.addOrderEvent(cxn, new Integer(psb.getOrderFkey()), Event.kEventTypeHandling, "Manifesting auto-switched ship method from " + originalShipMethodName + " to " + shipMethodName, "Manifesting Server");

                ts.print("Added Event Auto Switch " + psb.getPackageBarcode());
                String newShipName=null;
                try
                {
                    newShipName = (String) OrderRater.getRateableServicesMap().get(shipMethod);
                }         catch(Exception exsn)
                {
                    exsn.printStackTrace();
                }
                if(newShipName!=null)
                {
                    stmt = cxn.prepareStatement("update owd_order_ship_info set carr_service=?, carr_service_ref_num=?" +
                            " where order_fkey=?");

                    stmt.setString(1,newShipName );
                    stmt.setString(2, shipMethod);
                    stmt.setInt(3, new Integer(psb.getOrderFkey()).intValue());
                    stmt.executeUpdate();
                }

            }
            cxn.commit();
            ts.print("savepackage-lo insert done" + psb.getPackageBarcode());

            //todo look for weight verified order and not process this line
            System.out.println("WV??");
            System.out.println(ABUtils.isWeightVerifiedPacked(psb.getOrderFkey()));
            if(!ABUtils.isWeightVerifiedPacked(psb.getOrderFkey())) {
                System.out.println("We are updateing the order to shipped etc........");

                String esql = "exec update_order_shipment_info " + new Integer(psb.getOrderFkey()).intValue();

                Statement astmt = cxn.createStatement();

                astmt.executeUpdate(esql);

                //   astmt.executeUpdate("");


                cxn.commit();
            }
            ts.print("savepackage-lo update done"  +psb.getPackageBarcode());
            //getStringParam(req, "order_fkey", "")

            return true;

        } catch (Throwable th) {

            try {
                cxn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            throw new Exception("Server error: "+th.getMessage());
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

    }

    private void checkForSuffix(String clientId, String sku){



            //1k
            List<String> oneK = new ArrayList<>(Arrays.asList("LYFT126-KITM4XL","LYFT126-KITM3XL","LYFT126-KITM2XL",
                    "LYFT126-KITMXL",
                    "LYFT126-KITML",
                    "LYFT126-KITMM",
                    "LYFT126-KITMS",
                    "LYFT126-KITW2XL",
                    "LYFT126-KITWXL",
                    "LYFT126-KITWL",
                    "LYFT126-KITWM",
                    "LYFT126-KITWS"));
            if(oneK.contains(sku)){
                setValue(ShipConfig.REFERENCE_SUFFIX, "-1KJkt");
            }
            if(sku.equalsIgnoreCase("LYFT150")){
                setValue(ShipConfig.REFERENCE_SUFFIX,"-AMP");
            }



            if(sku.equalsIgnoreCase("Ebay Items")){
                setValue(ShipConfig.REFERENCE_SUFFIX,"-Ebay");
            }

    }
}




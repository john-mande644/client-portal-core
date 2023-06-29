package com.owd.core.managers;

import com.owd.OWDShippingAPI.AddressValidationUtilities;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.order.Event;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderShipInfo2;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import inship.AddressDetail;
import inship.InShipException;
import inship.USPSAccount;
import inship.Uspsaddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 28, 2008
 * Time: 9:09:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddressManager {
    private final static BigDecimal AES_FREE_MAX_DECLARED_VALUE = new BigDecimal(2500.0);

    private final static Logger log =  LogManager.getLogger();

    private static final String uspsProductionID = "815OWD001839";

    static final Pattern apoPattern = Pattern.compile("([AFaf][Pp][oO]),? ([a-zA-Z]{2})");

    public static Map<String, String> states = new HashMap<String, String>();


    // private static String uspsProductionPass = "owd7172";
    private static Map<String, String> stateMap = new HashMap<String, String>();

    static {
        stateMap.put("FM", "MICRONESIA");
        stateMap.put("GU", "GUAM");
        stateMap.put("MH", "MARSHALL ISLANDS");
        stateMap.put("MP", "NORTHERN MARIANA ISLANDS");
        stateMap.put("PW", "PALAU");
        stateMap.put("PR", "PUERTO RICO");
        stateMap.put("VI", "VIRGIN ISLANDS, U.S.");
        stateMap.put("AA", "USA");
        stateMap.put("AE", "USA");
        stateMap.put("AP", "USA");

        stateMap.put("MICRONESIA", "MICRONESIA");
        stateMap.put("GUAM", "GUAM");
        stateMap.put("MARSHALL ISLANDS", "MARSHALL ISLANDS");
        stateMap.put("NORTHERN MARIANA ISLANDS", "NORTHERN MARIANA ISLANDS");
        stateMap.put("PALAU", "PALAU");
        stateMap.put("PUERTO RICO", "PUERTO RICO");
        stateMap.put("VIRGIN ISLANDS, U.S.", "VIRGIN ISLANDS, U.S.");
        stateMap.put("VIRGIN ISLANDS", "VIRGIN ISLANDS, U.S.");
        stateMap.put("Armed Forces Americas", "USA");
        stateMap.put("Armed Forces Europe", "USA");
        stateMap.put("Armed Forces Pacific", "USA");
        stateMap.put("Armed Forces Africa", "USA");
        stateMap.put("Armed Forces Canada", "USA");
        stateMap.put("Armed Forces Middle East", "USA");

        states.put("ALABAMA","AL");
        states.put("ALASKA","AK");
        states.put("ALBERTA","AB");
        states.put("AMERICAN SAMOA","AS");
        states.put("ARIZONA","AZ");
        states.put("ARKANSAS","AR");
        states.put("ARMED FORCES (AE)","AE");
        states.put("ARMED FORCES AMERICAS","AA");
        states.put("ARMED FORCES PACIFIC","AP");
        states.put("BRITISH COLUMBIA","BC");
        states.put("CALIFORNIA","CA");
        states.put("COLORADO","CO");
        states.put("CONNECTICUT","CT");
        states.put("DELAWARE","DE");
        states.put("DISTRICT OF COLUMBIA","DC");
        states.put("FLORIDA","FL");
        states.put("GEORGIA","GA");
        states.put("GUAM","GU");
        states.put("HAWAII","HI");
        states.put("IDAHO","ID");
        states.put("ILLINOIS","IL");
        states.put("INDIANA","IN");
        states.put("IOWA","IA");
        states.put("KANSAS","KS");
        states.put("KENTUCKY","KY");
        states.put("LOUISIANA","LA");
        states.put("MAINE","ME");
        states.put("MANITOBA","MB");
        states.put("MARYLAND","MD");
        states.put("MASSACHUSETTS","MA");
        states.put("MICHIGAN","MI");
        states.put("MINNESOTA","MN");
        states.put("MISSISSIPPI","MS");
        states.put("MISSOURI","MO");
        states.put("MONTANA","MT");
        states.put("NEBRASKA","NE");
        states.put("NEVADA","NV");
        states.put("NEW BRUNSWICK","NB");
        states.put("NEW HAMPSHIRE","NH");
        states.put("NEW JERSEY","NJ");
        states.put("NEW MEXICO","NM");
        states.put("NEW YORK","NY");
        states.put("NEWFOUNDLAND","NF");
        states.put("NORTH CAROLINA","NC");
        states.put("NORTH DAKOTA","ND");
        states.put("NORTHWEST TERRITORIES","NT");
        states.put("NOVA SCOTIA","NS");
        states.put("NUNAVUT","NU");
        states.put("OHIO","OH");
        states.put("OKLAHOMA","OK");
        states.put("ONTARIO","ON");
        states.put("OREGON","OR");
        states.put("PENNSYLVANIA","PA");
        states.put("PRINCE EDWARD ISLAND","PE");
        states.put("PUERTO RICO","PR");
        states.put("QUEBEC","PQ");
        states.put("RHODE ISLAND","RI");
        states.put("SASKATCHEWAN","SK");
        states.put("SOUTH CAROLINA","SC");
        states.put("SOUTH DAKOTA","SD");
        states.put("TENNESSEE","TN");
        states.put("TEXAS","TX");
        states.put("UTAH","UT");
        states.put("VERMONT","VT");
        states.put("VIRGIN ISLANDS","VI");
        states.put("VIRGINIA","VA");
        states.put("WASHINGTON","WA");
        states.put("WEST VIRGINIA","WV");
        states.put("WISCONSIN","WI");
        states.put("WYOMING","WY");
        states.put("YUKON TERRITORY","YT");

    }

    public static Map<String, String> getUSTerritoryStateCodeCountryMap() {
        Map<String, String> copyMap = new HashMap<String, String>();
        stateMap.putAll(copyMap);
        return copyMap;
    }

    public static String getCorrectCountryForUSTerritoryTwoLetterCode(String stateCode, String originalCountry) {
         log.debug(stateMap);
          log.debug(stateCode+":"+originalCountry);
        if (originalCountry == null) originalCountry = "USA";

        if (stateCode == null) return originalCountry.trim();

        String countryName = stateMap.get(stateCode.trim().toUpperCase());

        if (countryName == null) return originalCountry.trim();

        return countryName;
    }

    public static String getCorrectCountryForUSTerritoryNameOrTwoLetterCode(String stateValue, String originalCountry) {
         log.debug(stateMap);

          log.debug(stateValue+":"+originalCountry);
        if (originalCountry == null) originalCountry = "USA";

        if (stateValue == null) return originalCountry.trim();

        String countryName = stateMap.get(stateValue.trim().toUpperCase());

        if (countryName == null) return originalCountry.trim();

        return countryName;
    }

    public static void validate(Address address) throws Exception {

    //    address.address_one = OWDUtilities.multiLineToOneLine(address.address_one);
    //    address.address_two = OWDUtilities.multiLineToOneLine(address.address_two);


        //correct for ebay state/city errors where one field combines both values, like "APO AE"
            fixEbayApo(address);


                address.country = getCorrectCountryForUSTerritoryNameOrTwoLetterCode(address.state, address.country);

                    try {
                        updateCompleteAddress(address);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        if(!(ex.getMessage().contains("non-US")))
                        {
                            throw new Exception("Unable to correct address! : " + address + "\n\nError: " + address.verificationError);
                        }

                    }
                    

        }

    public static void checkFlatRateDomesticVsInternational(OwdOrderShipInfo info) throws Exception {

        //ONly look if it is a flat rate method
        if (info.getCarrServiceRefNum().startsWith("COM_OWD_FLATRATE")) {
            //Check international shipment
            if (!isUS(info)) {
                //International flat rate contain INTL, if the method does not map to one that does.
                if (!info.getCarrServiceRefNum().contains("INTL")) {
                    updateDomestincFlatRateMethodToInternational(info);
                }

            } else { //Check domestic shipment
                //International flat rate contain INTL.
                if (info.getCarrServiceRefNum().contains("INTL")) {
                    updateInternationalFlatRateMethodToDomestic(info);
                }
            }

            HibernateSession.currentSession().saveOrUpdate(info);
            HibUtils.commit(HibernateSession.currentSession());
        }
    }

    public static boolean isUS(OwdOrderShipInfo info) {
        if (info.getShipCountry().equalsIgnoreCase("USA")
                || info.getShipCountry().equalsIgnoreCase("US") ||
                info.getShipCountry().equalsIgnoreCase("UNITED STATES") ||
                info.getShipCountry().equalsIgnoreCase("UNITED_STATES"))
            return true;
        else
            return false;

    }
    public static void checkAndCorrectAddress(Address address, String shipMethodCode) throws Exception {
        checkAndCorrectAddressForLocation(address,shipMethodCode,"DC1");
    }
    public static void checkAndCorrectAddressForLocation(Address address, String shipMethodCode, String location)throws Exception{
        checkAndCorrectAddressForLocation(address,shipMethodCode,location,0);
    }
    public static void checkAndCorrectAddressForLocation(Address address, String shipMethodCode, String location,Integer clientId) throws Exception {

        if (address.address_one == null) address.address_one = "";
        if (address.address_one.indexOf("\n") >= 0) {
            address.address_one = address.address_one.substring(0, address.address_one.indexOf("\n"));
        }
        if (address.address_one.indexOf("\r") >= 0) {
            address.address_one = address.address_one.substring(0, address.address_one.indexOf("\r"));
        }



        //correct for ebay state/city errors where one field combines both values, like "APO AE"
        fixEbayApo(address);
//        log.debug(address);

        // Sean created on 5/16/2019
        // check if in US with valid states
        if(address.isUS()){
            if(!isAddressStateValid(address.state.trim())){
//                log.debug(isAddressStateValid(address.state));
//                log.debug(address.country);
//                log.debug(address.state);
                throw new Exception("Invalid State! : " + address + "\n\nError: " + address.verificationError);
            }
        }

        try {
               updateCompleteAddressOwdApi(address,clientId);
            //    updateCompleteAddress(address);
            } catch (Exception ex) {
            if(!ex.getMessage().startsWith("Cannot correct non-US addresses")){
                throw new Exception("Unable to correct address! : " + address + "\nShipping Address\nERROR: " + ex.getMessage());
            }
                ex.printStackTrace();

            }
            log.debug(address);



        if (!isAddressValidForLocation(address, shipMethodCode, location)) {
            log.debug("Unable to correct address! : " + address + "\n\nError: " + address.verificationError);

            if (address.isInternational()) {
                throw new Exception("Ship method not accepted for this address; cannot correct non-US addresses : " + address);
            }


            address.country = getCorrectCountryForUSTerritoryNameOrTwoLetterCode(address.state, address.country);

            if (!isAddressValidForLocation(address, shipMethodCode,location)) {
                log.debug("Unable to correct address! : " + address + "\n\nError: " + address.verificationError);

                    throw new Exception("Unable to correct address! : " + address + "\n\nError: " + address.verificationError);
                }
        }

    }


    /**
     *  Sean created on 5/16/2019
     */
    static boolean isAddressStateValid(String AddressState){
        String [] States = new String []{
                "AL", "AK", "AZ", "AR", "CA",
                "Alabama", "Alaska","Arizona", "Arkansas", "California",

                "CO", "CT", "DC", "DE", "FL", "GA",
                "Colorado", "Connecticut","Delaware","District of Columbia","Florida","Georgia",

                "HI", "ID", "IL", "IN", "IA",
                "Hawaii","Idaho","Illinois","Indiana","Iowa",

                "KS", "KY", "LA", "ME", "MD",
                "Kansas","Kentucky","Louisiana","Maine","Maryland",

                "MA", "MI", "MN", "MS", "MO",
                "Massachusetts","Michigan","Minnesota","Mississippi","Missouri",



                "MT", "NE", "NV", "NH", "NJ",
                "Montana","Nebraska","Nevada","New Hampshire","New Jersey",

                "NM", "NY", "NC", "ND", "OH",
                "New Mexico","New York","North Carolina","North Dakota","Ohio",

                "OK", "OR", "PA", "RI", "SC",
                "Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina",

                "SD", "TN", "TX", "UT", "VT",
                "South Dakota","Tennessee","Texas","Utah",

                "VA", "WA", "WV", "WI", "WY",
                "Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming",

                // Overseas territories
                "AS","GU","MH","FM","MP","PW","PR","VI",
                "American Samoa","Guam","Marshall Islands","Micronesia","Northern Marianas","Palau","Puerto Rico","Virgin Islands",

                // Overseas military addr
                "AA", "AP", "AE"
        };

        boolean isValidState = false;
        for (String state: States){
            if ( state.equalsIgnoreCase( AddressState)) {
                isValidState = true;
            }
        }
        return isValidState;
    }

    private static void fixEbayApo(Address address) {
        if(!(address.isInternational()))
        {
        Matcher match = apoPattern.matcher(address.state);

        if (match.find()) {
            address.city = match.group(1);
            address.state = match.group(2);
        }

        match = apoPattern.matcher(address.city);

        if (match.find()) {
            address.city = match.group(1);
            address.state = match.group(2);
        }
        }
    }

    public static boolean isAddressValid(Address address) {
        return isAddressValid(address, null);
    }

    public static boolean isAddressValid(Address address, String shipMethodCode) {
        return isAddressValidForLocation(address,shipMethodCode,"DC1");
    }

    public static boolean isAddressValidForLocation(Address address, String shipMethodCode, String location) {

        log.debug("isAddressValid:"+address);
        try {
            log.debug("OR check addr calling");
            OrderRater.checkAddress(address, shipMethodCode, location);

        } catch (Exception ex) {
            log.debug("hello");
            log.debug("setting verify error: " + (ex.getMessage()==null?"null value":ex.getMessage()));
            if("rater service unavailable".equalsIgnoreCase(ex.getMessage()))
            {
                log.debug("Connectship down!");
                try{
                Mailer.sendMail("Connectship rater down","Connectship rater down, returning null pointer","servicerequests@owd.com");
                  } catch (Exception exm) {exm.printStackTrace();}
                return true;
            }
            ex.printStackTrace();
            address.verificationError = ex.getMessage();
            return false;

        }

        return true;

    }


    public static void correctCityStateZip(Address address) throws Exception {

        if (address.isInternational()) throw new Exception("Cannot correct non-US addresses");

        Uspsaddress shipper = new Uspsaddress();

        shipper.setRuntimeLicense(
                OWDUtilities.getIPWorksInshipRuntimeLicense()
        );

        USPSAccount acc = new USPSAccount();
        acc.setServer("http://production.shippingapis.com/ShippingAPI.dll");
        acc.setUserId(uspsProductionID);
        shipper.setUSPSAccount(acc);

        try {

                    address.state = (states.keySet().contains(address.state.trim().toUpperCase())?states.get(address.state.trim().toUpperCase()):address.state);

            AddressDetail addressinfo = new AddressDetail();
            //	System.out.print("USPS account user id:");
            //System.out.print("\nUSPS account password:");
            //	addressinfo.setPassword(uspsProductionPass);
            //	log.debug("\nLooking up zip for : "+address.city+"/"+address.state);
            addressinfo.setCity(address.city);
            addressinfo.setState(address.state);
            addressinfo.setAddress1(address.address_one);
            addressinfo.setZipCode(address.zip);
            shipper.setAddress(addressinfo);
            //	log.debug("SUCCESS\nCity: " + addressinfo.getCity() + "\nState: " + addressinfo.getState() + "\nZip: " + addressinfo.getZipCode());
            //   address.city = addressinfo.getCity();
            //   address.state = addressinfo.getState();
            address.zip = shipper.getMatches().get(0).getZipCode();
            //   address.address_one = addressinfo.getAddressLine2();
            address.isCorrected = true;
        }
        catch (Exception ex) {
            //  ex.printStackTrace();
            if (!address.isCorrected) {
                try {
                    AddressDetail addressinfo = new AddressDetail();
                      addressinfo.setCity(address.city);
                    addressinfo.setState(address.state);
                    addressinfo.setZipCode(address.zip);
                    addressinfo.setAddress1(address.address_one);
                    shipper.setAddress(addressinfo);
                    shipper.validateAddress();
                    //	log.debug("SUCCESS\nCity: " + addressinfo.getCity() + "\nState: " + addressinfo.getState() + "\nZip: " + addressinfo.getZipCode());
                    address.city = shipper.getMatches().get(0).getCity();
            address.state = shipper.getMatches().get(0).getState();
                    address.zip = shipper.getMatches().get(0).getZipCode();
                    //  address.address_one = addressinfo.getAddressLine2();
                    address.isCorrected = true;
                    log.debug(address);
                } catch (InShipException exx) {
                    throw new Exception("USPS ERROR: InShip exception thrown: " + exx.getCode() + " [" + exx.getMessage() + "].");
                }
                catch (Exception exx) {
                    throw exx;
                }
            }
        }


    }

    /**
     *
     * @param add com.owd.buisness.address Address to be checked for invalid characters that will cause shipping issues
     * @throws Exception details which field triggered the exception
     */

    public static void invalidCharacterCheckForAddress(Address add) throws Exception{

        if(add.getCompany_name().contains("??")){
            throw new Exception("Invalid Company Name. Please update and re-release");

        }
        if(add.getAddress_one().contains("??")){
            throw new Exception("Invalid Address Line One Name. Please update and re-release");
        }
        if(add.getAddress_two().contains("??")){
            throw new Exception("Invalid Address Line Two Name. Please update and re-release");
        }
        if(add.getCity().contains("??")){
            throw new Exception("Invalid City. Please update and re-release");
        }
        if(add.getState().contains("??")){
            throw new Exception("Invalid State. Please update and re-release");
        }


    }

    public static void updateCompleteAddressOwdApi(Address add,Integer clientId) throws Exception{
        if (add.isInternational()) throw new Exception("Cannot correct non-US addresses");
        AddressValidationUtilities avu = new AddressValidationUtilities();

         avu.getValidatedAddress(add,clientId);

    }


    public static void updateCompleteAddress(Address sadd) throws Exception {

        Uspsaddress shipper = new Uspsaddress();

        shipper.setRuntimeLicense(
                OWDUtilities.getIPWorksInshipRuntimeLicense()
        );

        if (sadd.isInternational()) throw new Exception("Cannot correct non-US addresses");
        try {

            sadd.state = (states.keySet().contains(sadd.state.trim().toUpperCase())?states.get(sadd.state.trim().toUpperCase()):sadd.state);

            USPSAccount acc = new USPSAccount();
            acc.setServer("http://production.shippingapis.com/ShippingAPI.dll");
            acc.setUserId(uspsProductionID);
            shipper.setUSPSAccount(acc);
            //	System.out.print("USPS account user id:");
            //  addressinfo.setUserId(uspsProductionID);
            //		System.out.print("\nUSPS account password:");
            //	addressinfo.setPassword(uspsProductionPass);
            //	log.debug("\nCorrecting address for: "+address);
            AddressDetail ad = new AddressDetail();

            //    ad.setCity(sadd.city);
            //  ad.setState(sadd.state);
            shipper.setCompany(sadd.getCompany_name());
            ad.setZipCode(sadd.zip);

            ad.setAddress1(sadd.address_one);
            //  ad.set(address.company_name);
            ad.setAddress2(sadd.address_two);

            shipper.setAddress(ad);
            // log.debug(""+addressinfo.config("AcceptEncoding=UTF-8"));
            shipper.validateAddress();

            log.debug(""+shipper.config("FullRequest"));
            log.debug(""+shipper.config("FullResponse"));
            //	log.debug("SUCCESS\nCompany: " + addressinfo.getFirmName() + "\nAdd1: " + addressinfo.getAddressLine2()  +
            //            "\nAdd2: " + addressinfo.getAddressLine1() +
            //            "\nCity: " + addressinfo.getCity() + "\nState: " + addressinfo.getState() + "\nZip: " + addressinfo.getZipCode());
            if(shipper.getMatches().size()>0)
            {
            sadd.city = shipper.getMatches().get(0).getCity();
            sadd.state =shipper.getMatches().get(0).getState();
            sadd.zip = shipper.getMatches().get(0).getZipCode();
            sadd.address_one = shipper.getMatches().get(0).getAddress1() + " " + shipper.getMatches().get(0).getAddress2();
            log.debug(sadd.city);
        }else{
            throw new Exception("address not correctable");
        }
            //   address.address_two = ;
            // address.company_name = addressinfo.getFirmName();
            sadd.isCorrected = true;
        } catch (InShipException ex) {
            throw new Exception("USPS ERROR: Inship exception thrown: " + ex.getCode() + " [" + ex.getMessage() + "].");
        }
        catch (Exception ex) {
            throw ex;
        }   finally
        {
        }

    }

    public static void main(String[] args) {
        log.debug("Testing address correction utilities");

        try {

            Uspsaddress shipper = new Uspsaddress();

            shipper.setRuntimeLicense(
                    OWDUtilities.getIPWorksInshipRuntimeLicense()
            );
          //  log.debug(shipper.getRuntimeLicense());
               Address sadd = new Address();
            sadd.state="NC";
            sadd.city= "Charlotte";
            sadd.address_one= "P.O. Box 6";
            sadd.address_two="";
            sadd.zip="28211";
            sadd.country="USA";


            log.debug(sadd);

            Address a = new Address();
            a.state = sadd.state;
            a.city = sadd.city;
            a.address_one = sadd.address_one;
            a.zip = sadd.zip;
            a.country = sadd.country;

            checkAndCorrectAddressForLocation(a,"CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP","DC1");
          // updateCompleteAddress(sadd);
            log.debug(a);





        } catch (InShipException ex) {
            log.debug("USPS ERROR: Inship exception thrown: " + ex.getCode() + " [" + ex.getMessage() + "].");


        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        } finally {
            HibernateSession.closeSession();
        }





    }


    public static void updateCompleteAddressViwRawURL(Address theAddress)
    {

      try{

        URL testUrl = new URL("http://production.shippingapis.com/ShippingAPI.dll?API=Verify&XML="+ URLEncoder.encode("<AddressValidateRequest USERID=\""+uspsProductionID+"\">" +
                "<Address ID=\"1\"><Address1>"+theAddress.address_two.trim()+"</Address1>" +
                "<Address2>"+theAddress.address_one.trim()+"</Address2>" +
                "<City>"+theAddress.city.trim()+"</City><State>" +
                theAddress.state.trim()+"</State>" +
                "<Zip5>"+theAddress.zip+"</Zip5>" +
                "<Zip4></Zip4>" +
                "</Address></AddressValidateRequest>","UTF-8"));
        HttpURLConnection testConnection = (HttpURLConnection) testUrl.openConnection();
testConnection.setConnectTimeout(120000);
          testConnection.setReadTimeout(120000);
        testConnection.setRequestMethod("GET");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                   factory.setNamespaceAware(false);
                   factory.setValidating(false);
                   javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


          Document doc = builder.parse(new InputSource(new InputStreamReader(testConnection.getInputStream(), "UTF-8")));
     //  Document doc = builder.parse(catalog.getResourceAsInputStream(false), "UTF-8");
       doc.getDocumentElement().normalize();



        DOMSource source = new DOMSource(doc);
       TransformerFactory tf = TransformerFactory.newInstance();
       Transformer t = tf.newTransformer();
StringWriter writer = new StringWriter();
    Result result = new StreamResult(writer);
t.transform(source, result);
writer.close();
String xml = writer.toString();
    log.debug("X:"+xml);
        

      }
        catch (Exception ex) {
            log.debug(ex.getMessage());
        } finally {
            HibernateSession.closeSession();
        }
    }


    private static void updateInternationalFlatRateMethodToDomestic(OwdOrderShipInfo info) throws Exception {
        switch (info.getCarrServiceRefNum()){
            case "COM_OWD_FLATRATE_INTL_ECONOMY":
                info.setCarrServiceRefNum("COM_OWD_FLATRATE_ECONOMY");
                info.setCarrService("Economy");
                Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, "Ship method Changed from International Economy to Economy", "System");

                break;
            case "COM_OWD_FLATRATE_INTL_STND":
                info.setCarrServiceRefNum("COM_OWD_FLATRATE_GROUND");
                info.setCarrService("Ground");
                Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, "Ship method Changed from International Standard to Ground", "System");

                break;
            case "COM_OWD_FLATRATE_INTL_EXPD":
                info.setCarrServiceRefNum("COM_OWD_FLATRATE_STANDARD_GROUND");
                info.setCarrService("Standard Priority");
                Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, "Ship method Changed from International Expedited  to Standard Priority", "System");

                break;
            case "COM_OWD_FLATRATE_INTL_PRIDDP":
            case "COM_OWD_FLATRATE_INTL_PRIDDU":
                info.setCarrServiceRefNum("COM_OWD_FLATRATE_GROUND");
                info.setCarrService("Ground");
                Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, "Ship method Changed from International Expedited to 2Day", "System");

                break;

        }
    }

    private static void updateDomestincFlatRateMethodToInternational(OwdOrderShipInfo info) throws Exception {
        switch (info.getCarrServiceRefNum()){
            case "COM_OWD_FLATRATE_ECONOMY":
                info.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_ECONOMY");
                info.setCarrService("International Economy");
                Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, "Ship method Changed from Economy to International Economy", "System");

                break;
            case "COM_OWD_FLATRATE_GROUND":
                info.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_STND");
                info.setCarrService("International Standard");
                Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, "Ship method Changed from Ground to International Standard", "System");

                break;
            case "COM_OWD_FLATRATE_STANDARD_GROUND":
                info.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_EXPD");
                info.setCarrService("International Expedited");
                Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, "Ship method Changed from Standard Priority  to International Expedited", "System");

                break;
            case "COM_OWD_FLATRATE_2DA":
                info.setCarrServiceRefNum("COM_OWD_FLATRATE_INTL_EXPD");
                info.setCarrService("International Expedited");
                Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, "Ship method Changed from 2Day to International Expedited", "System");

                break;
            case "COM_OWD_FLATRATE_NDA":
                throw new Exception("Overnight is not supported for International Addresses");

        }
    }

    public static Boolean doesAESRequired(OwdOrder order) throws  Exception {
        OrderShipInfo2 info2 = order.getShipInfo2();
        OwdOrderShipInfo info = order.getShipinfo();
        BigDecimal orderLineItemsSumm = getOrderLineItemsSumm(order);
        Boolean doesAesRequired = !isUS(info) && orderLineItemsSumm.compareTo(AES_FREE_MAX_DECLARED_VALUE)> 0
                && (info2.getAesItn() == null || info2.getAesItn().trim().equals(""));

        return  doesAesRequired;
    }

    private static BigDecimal getOrderLineItemsSumm(OwdOrder order) {
        BigDecimal lineItemsTotalSumm = new BigDecimal(0);
        Iterator it = order.getLineitems().iterator();

        while (it.hasNext()) {
            OwdLineItem item = (OwdLineItem) it.next();
            lineItemsTotalSumm = lineItemsTotalSumm.add(item.getTotalPrice());
        }

        return lineItemsTotalSumm;
    }

    public static Boolean checkAndClearIncorrectShippingPhoneNumber(OwdOrder order) throws Exception {
        OwdOrderShipInfo info = order.getShipinfo();

        if(!isUS(info)) {
            return  true;
        }

        // If a phone number starts with 1 and is 10 digits, it is invalid
        String phoneToCheck = info.getShipPhoneNum().replaceAll("-|\\s|\\(|\\)|\\[|\\]|\\.?\\+","");
        Boolean matches = phoneToCheck.matches("[0-9]+") && ((phoneToCheck.length() == 10) && !phoneToCheck.startsWith("1")
                || (phoneToCheck.length()==11 && phoneToCheck.startsWith("1")));

        if (!matches) {
            info.setShipPhoneNum("");
            HibernateSession.currentSession().saveOrUpdate(info);
            HibUtils.commit(HibernateSession.currentSession());
        }

        return matches;
    }
}

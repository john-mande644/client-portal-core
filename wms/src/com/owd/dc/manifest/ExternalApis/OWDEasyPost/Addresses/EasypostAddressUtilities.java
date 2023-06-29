package com.owd.dc.manifest.ExternalApis.OWDEasyPost.Addresses;

import com.owd.connectship.soap.NameAddress;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 9/23/2017.
 */
public class EasypostAddressUtilities {
    protected final static Logger log = LogManager.getLogger();





    public static Map<String, Object> loadFromAddress(AMPConnectShipShipment ship, Map<String,Object> carriers,OwdClient client ){
        Map<String, Object> fromAddressMap = new HashMap<String, Object>();
        try {
            if(client.getClientId()==575 && carriers.get("carrier").equals("Purolator")){
                log.debug("bumleride purolator, changing from address");
                fromAddressMap.put("name", "Shipping Department");
                fromAddressMap.put("street1", "3700 Jericho Rd.");
                fromAddressMap.put("company","BumbleRide");
                fromAddressMap.put("city", "Richmond");
                fromAddressMap.put("state", "BC");
                fromAddressMap.put("zip", "V7B 1M5");
                fromAddressMap.put("country","CA");
                fromAddressMap.put("phone", "1-800-530-3930");
            }else {

                NameAddress from = ship.getReturnNA();
                fromAddressMap.put("name", from.getContact());
                fromAddressMap.put("street1", from.getAddress1());
                fromAddressMap.put("street2", from.getAddress2());
                fromAddressMap.put("company", from.getCompany());
                fromAddressMap.put("city", from.getCity());
                fromAddressMap.put("state", from.getStateProvince());
                fromAddressMap.put("zip", from.getPostalCode());
                fromAddressMap.put("phone", from.getPhone());
                fromAddressMap.put("country", getIANACountry(from.getCountrySymbol(),from.getCity()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        return fromAddressMap;
    }

    public static Map<String, Object> loadToAddress(AMPConnectShipShipment ship, Map<String,Object> carriers ){
        Map<String, Object> toAddressMap = new HashMap<String, Object>();

       try {
           NameAddress customer = ship.getConsigneeNA();


           toAddressMap.put("name", customer.getContact());
           if(carriers.get("carrier").equals("Purolator")){
               if(customer.getCompany().length()>30){
                   toAddressMap.put("company", customer.getCompany().substring(0,30));
               }else{
                   toAddressMap.put("company", customer.getCompany());
               }

           }else{
               toAddressMap.put("company", customer.getCompany());
           }

           toAddressMap.put("street1", customer.getAddress1());
           toAddressMap.put("street2", customer.getAddress2());
           toAddressMap.put("city", customer.getCity());
           toAddressMap.put("state", customer.getStateProvince());
           toAddressMap.put("zip", customer.getPostalCode());
           toAddressMap.put("phone", customer.getPhone());


           toAddressMap.put("country", getIANACountry(customer.getCountrySymbol(), customer.getCity()));
       } catch (Exception e){
           e.printStackTrace();
       }

        return toAddressMap;


    }

    public static Map<String, Object> loadReturnAddress(AMPConnectShipShipment ship, Map<String,Object> carriers,OwdClient client ){


        Map<String, Object> ReturnAddressMap = new HashMap<String, Object>();

        if(client.getClientId()==575 && carriers.get("carrier").equals("Purolator")) {
            ReturnAddressMap.put("name", "Shipping Department");
            ReturnAddressMap.put("street1", "1149 Martin Grove Rd.");
            ReturnAddressMap.put("company", "BumbleRide");
            ReturnAddressMap.put("city", "Etobicoke");
            ReturnAddressMap.put("state", "ON");
            ReturnAddressMap.put("zip", "M9W 4W7");
            ReturnAddressMap.put("country", "CA");
            ReturnAddressMap.put("phone", "1-800-530-3930");
        }


        return ReturnAddressMap;
    }

    public static String getIANACountry(String country,String city) throws Exception{
        System.out.println("getting countires For "+country);
        //fix funky country stuff
        if(country.equals("KYRGHYZSTAN")) country = "KYRGYZSTAN";
        if(country.equalsIgnoreCase("NETHERLANDS_ANTILLES")){
            if(city.equalsIgnoreCase("Curacao")){
                return "CW";
            }
        }

        String sql = " select country_code from countries where connectship = :name";
        try{

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setString("name",country.toUpperCase());
            List results = q.list();
            if (results.size()>0){

                country =  (String) results.get(0);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if (country.length()>2) throw new Exception("Unable to find 2 digit country code please contact IT");

        return country;

    }
}

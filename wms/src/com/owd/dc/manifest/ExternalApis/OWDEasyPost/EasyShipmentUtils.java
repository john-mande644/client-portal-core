package com.owd.dc.manifest.ExternalApis.OWDEasyPost;

import com.easypost.EasyPost;
import com.easypost.exception.EasyPostException;
import com.easypost.model.*;
import com.easypost.model.Order;
import com.owd.connectship.soap.ObjectFactory;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.core.ExcelUtils;
import com.owd.core.business.order.*;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 3/14/2017.
 */
public class EasyShipmentUtils {
    protected final static Logger log = LogManager.getLogger();


    public static void main(String args[]){

      // viewTestShipment();
      //  viewTestMultipieceShipment();
       // rateLyftReturnShipment();

     //   bumblePurolatorTesting();
      //  refundShipment( EasyPostAccountsAndUsers.xBumblePurolatorLiveKey,"shp_f469d25f896e47e99194aea0ca9b72c2");
     //   LocalDate date = getNextOccuraceOfDayOfWeek(DateTimeConstants.FRIDAY);
     //   System.out.println(date.toString());
     //   EasyPost.apiKey = "SzsyZlqNNQ629HH5tgxzZw";
        try {
            EasyPost.apiKey = EasyPostAccountsAndUsers.xBumblePurolatorLiveKey;



            reprintShipmentLable("shp_4a91147753ea4e8f8653fbde1e4fabee",EasyPostAccountsAndUsers.xBumblePurolatorLiveKey);
              Shipment ship = Shipment.retrieve("shp_4a91147753ea4e8f8653fbde1e4fabee");
             System.out.println(ship.prettyPrint());

         //   System.out.println(ship.prettyPrint());
           /* Tracker track = Tracker.retrieve("trk_d8f4c5fd8fde4bd190b45a265b7b32fb");
            System.out.println(track.prettyPrint());*/
          //  voidOrderShipmentsByPackBarcode("p10563351*22268333*b1");

        }catch (Exception e){
            e.printStackTrace();
        }

try {
   // List<LABELDATA> l = reprintShipmentLable("shp_4ab42d4e7e0944bdb12576b725390759", "hl0OMczdmtkyyCHY8dRxtQ");
  //  System.out.println(l.size());
  //  System.out.println(getEasyPostShipment("UPS", "UPS Ground"));
}catch (Exception e){
    e.printStackTrace();
}

    }

    public static boolean voidOrderShipmentsByPackBarcode(String barcode) throws Exception{
        //lookup all shipments from pack barcode and refund through easypost and void in OWD systems

        boolean success = false;
        String sql = "SELECT\n" +
                "    dbo.package.order_track_fkey,\n" +
                "    dbo.package.external_id,\n" +
                "    dbo.order_ship_info2.external_api_key\n" +
                "FROM\n" +
                "    dbo.package_order\n" +
                "INNER JOIN\n" +
                "    dbo.package\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                "INNER JOIN\n" +
                "    dbo.order_ship_info2\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.owd_order_fkey = dbo.order_ship_info2.order_fkey)\n" +
                "WHERE\n" +
                "    dbo.package_order.id = :id";
        String id = barcode.substring(barcode.indexOf("p")+1, barcode.indexOf("*"));
        log.debug(id);
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id",id);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);


        List results = q.list();

        for (Object row : results) {
            Map data = (Map) row;
            String trackFkey = data.get("order_track_fkey").toString();
            String externalId = data.get("external_id").toString();
            String easypostApiKey = data.get("external_api_key").toString();
            log.debug(trackFkey);
            log.debug(externalId);
            log.debug(easypostApiKey);
            refundShipment(easypostApiKey,externalId);
            com.owd.core.business.order.Package.voidPackageShipment(Integer.parseInt(trackFkey), "EasypostVoid");

        }




        return success;

    }

    public static boolean refundShipment(String apiKey, String shipmentId){
        boolean callsucceded = false;

        try{

            EasyPost.apiKey = apiKey;
            Shipment ship = Shipment.retrieve(shipmentId);
            ship.refund();
            callsucceded = true;
        }catch (Exception e){
            e.printStackTrace();
        }


        return callsucceded;
    }





    public static  List<LABELDATA> reprintShipmentLable(String shipmentId, String key) throws Exception{

        System.out.println("In Easypost reprint using key: " + key);
        EasyPost.apiKey = key;

        List<LABELDATA> labels = new ArrayList<LABELDATA>();
        Shipment ship = Shipment.retrieve(shipmentId);


        LABELDATA ld = new LABELDATA();
        ld.setCopies_Needed("1");
        if(ship.getPostageLabel().getLabelUrl().endsWith(".png")){
            if(null==ship.getPostageLabel().getLabelZplUrl()||ship.getPostageLabel().getLabelZplUrl().length()==0){
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("file_format", "ZPL");

                ship.label(params);
            }
            ld.setValue(ship.getPostageLabel().getLabelZplUrl());
        }else{
            ld.setValue(ship.getPostageLabel().getLabelUrl());
        }

        labels.add(ld);




        return labels;
    }

    public static void bumblePurolatorTesting(){
        try{
            System.out.println("Start");

            EasyPost.apiKey = EasyPostAccountsAndUsers.xBumblePurolatorLiveKey;

            Map<String, Object> fromAddressMap = new HashMap<String, Object>();
            fromAddressMap.put("name", "Shipping Department");
            fromAddressMap.put("street1", "3700 Jericho Rd.");
            fromAddressMap.put("company","BumbleRide");
            fromAddressMap.put("city", "Richmond");
            fromAddressMap.put("state", "BC");
            fromAddressMap.put("zip", "V7B 1M5");
            fromAddressMap.put("country","CA");
            fromAddressMap.put("phone", "1-800-530-3930");


            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(13092102, 489, true);
            OwdOrderShipInfo shipInfo = oorder.getShipinfo();
            Map<String, Object> toAddressMap = new HashMap<String, Object>();
            toAddressMap.put("name", shipInfo.getShipFirstName()+ " " + shipInfo.getShipLastName());
            toAddressMap.put("company",shipInfo.getShipCompanyName());
            toAddressMap.put("street1", shipInfo.getShipAddressOne());
            toAddressMap.put("street2", shipInfo.getShipAddressTwo());
            toAddressMap.put("city", shipInfo.getShipCity());
            toAddressMap.put("state", shipInfo.getShipState());
            toAddressMap.put("zip", shipInfo.getShipZip());
            toAddressMap.put("phone", shipInfo.getShipPhoneNum());
            toAddressMap.put("country", shipInfo.getShipCountry());

            Map<String, Object> ReturnAddressMap = new HashMap<String, Object>();
            ReturnAddressMap.put("name", "Shipping Department");
            ReturnAddressMap.put("street1", "1149 Martin Grove Rd.");
            ReturnAddressMap.put("company","BumbleRide");
            ReturnAddressMap.put("city", "Etobicoke");
            ReturnAddressMap.put("state", "ON");
            ReturnAddressMap.put("zip", "M9W 4W7");
            ReturnAddressMap.put("country","CA");
            ReturnAddressMap.put("phone", "1-800-530-3930");

            System.out.println("Setting addresses");
            Address fromAddress = Address.create(fromAddressMap);
            Address toAddress = Address.create(toAddressMap);
            Address returnAddress = Address.create(ReturnAddressMap);


            Map<String, Object> optionsMap = new HashMap<String, Object>();
            optionsMap.put("label_format", "PNG");
            optionsMap.put("label_size", "4x6");
            optionsMap.put("label_date","2017-09-14");

            Map<String, Object> parcelMap = new HashMap<String, Object>();
            parcelMap.put("weight", 240);
            parcelMap.put("height", 12);
            parcelMap.put("width", 12);
            parcelMap.put("length", 24);
            System.out.println("Creating parcels1");
            Parcel p1 = Parcel.create(parcelMap);





            System.out.println("Creating parcels");
            List<Map<String, Object>> shipments = new ArrayList<Map<String, Object>>();




            /////////////////////////////////////////////


            Map<String, Object> shipmentMap = new HashMap<String, Object>();

            shipmentMap.put("parcel", p1);

            shipmentMap.put("options",optionsMap);
            // shipmentMap.put("is_return",true);

            System.out.println("Creating shipments1");




            shipments.add(shipmentMap);
            // shipments.setShipments(ship);








            Map<String, Object> orderMap = new HashMap<String, Object>();
            // orderMap.put("reference","testOrder");
            orderMap.put("to_address", toAddress);
            orderMap.put("from_address",fromAddress);
            orderMap.put("return_address",returnAddress);
            orderMap.put("shipments",shipments);

            Map<String,String> accounts = new HashMap<String, String>();
            accounts.put("id","ca_3989ea69598240ecaf83ce707a100ed6");
            orderMap.put("carrier_accounts",accounts);

            //orderMap.put("is_return",true);

            System.out.println("Creating order");
            Order order = Order.create(orderMap);
            System.out.println("Created order");

            Map<String,Object> carriers = getEasyPostCarrier("Purolator","Ground");


            System.out.println(order.prettyPrint());

           // order.getShipments().get(0).getUspsZone();
          //  List<Rate> theRates = order.getRates();
         //   for(Rate r:theRates){
               // r.prettyPrint();
                /*if(r.getService().equals("Priority")){
                    System.out.println(r.getRate());
                    String sql = "update zzLyftDuplicates set singleRate = :rate, zone = :zone where orderFkey = :orderId";
                    Query q = HibernateSession.currentSession().createSQLQuery(sql);
                    q.setParameter("rate",r.getRate());
                    q.setParameter("orderId",orderId);
                    q.setParameter("zone",order.getShipments().get(0).getUspsZone());
                    int i = q.executeUpdate();
                    if(i>0){
                        HibUtils.commit(HibernateSession.currentSession());
                    }else{
                        //bad.add(orderId);
                    }
                }*/

            //}

            order.buy(carriers);
            System.out.println("********************************");
            System.out.print(order.prettyPrint());
            System.out.println(order.getShipments().get(0).getPostageLabel().getLabelUrl());



        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public static Map<String, Object> loadShipmentMapFromAmpPackList(List<AMPConnectShipShipment> packlist, OwdClient client,String facility) throws Exception{
        Map<String, Object> ShipmentMap = new HashMap<String, Object>();

        Map<String, Object> fromAddressMap = new HashMap<String, Object>();
        fromAddressMap.put("name", "Simpler Postage Inc");
        fromAddressMap.put("street1", "388 Townsend St");
        fromAddressMap.put("street2", "Apt 20");
        fromAddressMap.put("city", "San Francisco");
        fromAddressMap.put("state", "CA");
        fromAddressMap.put("zip", "94107");
        fromAddressMap.put("phone", "415-456-7890");

        Map<String, Object> toAddressMap = new HashMap<String, Object>();
        toAddressMap.put("name", "Sawyer Bateman");
        toAddressMap.put("street1", "1A Larkspur Cres");
        toAddressMap.put("street2", "");
        toAddressMap.put("city", "St. Albert");
        toAddressMap.put("state", "AB");
        toAddressMap.put("zip", "T8N2M4");
        toAddressMap.put("phone", "780-483-2746");
        toAddressMap.put("country", "CA");






        return ShipmentMap;
    }

    public static  Map<String,Object> getEasyPostCarrier(String carrier, String service) throws Exception{

        log.debug("This is the service:  " + service);

        Map<String,Object> carriers = new HashMap<String, Object>();
        if(carrier.contains("UPS")) {
            String method = "";
            if (service.equalsIgnoreCase("UPS 2nd Day Air")) method = "2ndDayAir";
            if (service.equalsIgnoreCase("UPS 2nd Day Air A.M.")) method = "2ndDayAirAM";
            if (service.equalsIgnoreCase("UPS 3 Day Select")) method = "3DaySelect";
            if (service.equalsIgnoreCase("UPS Ground")) method = "Ground";
            if (service.equalsIgnoreCase("UPS Next Day Air")) method = "NextDayAir";
            if (service.equalsIgnoreCase("UPS Next Day Air A.M.")) method = "NextDayAirEarlyAM";
            if (service.equalsIgnoreCase("UPS Next Day Air Saver")) method = "NextDayAirSaver";

            if (service.equalsIgnoreCase("UPS Worldwide Expedited")) method = "Expedited";
            if (service.equalsIgnoreCase("UPS Worldwide Express")) method = "Express";
            if (service.equalsIgnoreCase("UPS Worldwide Express Plus")) method = "ExpressPlus";
            if (service.equalsIgnoreCase("UPS Worldwide Express Saver")) method = "UPSSaver";
            if (service.equalsIgnoreCase("UPS Expedited")) method = "Expedited";
            if (service.equalsIgnoreCase("UPS EXPRESS")) method = "Express";
            if (service.equalsIgnoreCase("UPS Express Plus")) method = "ExpressPlus";
            if (service.equalsIgnoreCase("UPS Express Saver")) method = "UPSSaver";


            if (service.equalsIgnoreCase("UPS GROUND (CONSOLIDATED)")) method = "Ground";
            if (service.equalsIgnoreCase("UPS STANDARD (CONSOLIDATED)")) method = "UPSStandard";
            if (service.equalsIgnoreCase("UPS 2nd Day Air (CONSOLIDATED)")) method = "2ndDayAir";
            if (service.equalsIgnoreCase("UPS 2nd Day Air A.M. (CONSOLIDATED)")) method = "2ndDayAirAM";
            if (service.equalsIgnoreCase("UPS 3 Day Select (CONSOLIDATED)")) method = "3DaySelect";

            if (service.equalsIgnoreCase("UPS Next Day Air (CONSOLIDATED)")) method = "NextDayAir";
            if (service.equalsIgnoreCase("UPS Next Day Air A.M. (CONSOLIDATED)")) method = "NextDayAirEarlyAM";
            if (service.equalsIgnoreCase("UPS Next Day Air Saver (CONSOLIDATED)")) method = "NextDayAirSaver";

            if (service.equalsIgnoreCase("UPS GROUND")) method = "Ground";
            if (service.equalsIgnoreCase("UPS STANDARD")) method = "UPSStandard";
            if (service.equalsIgnoreCase("UPS 2nd Day Air")) method = "2ndDayAir";
            if (service.equalsIgnoreCase("UPS 2nd Day Air A.M.")) method = "2ndDayAirAM";
            if (service.equalsIgnoreCase("UPS 3 Day Select")) method = "3DaySelect";

            if (service.equalsIgnoreCase("UPS Next Day Air")) method = "NextDayAir";
            if (service.equalsIgnoreCase("UPS Next Day Air A.M.")) method = "NextDayAirEarlyAM";
            if (service.equalsIgnoreCase("UPS Next Day Air Saver")) method = "NextDayAirSaver";
            if (service.equalsIgnoreCase("UPS Standard to Canada")) method = "UPSStandard";
            if (method.length() > 0) {

                log.debug("This is the method we are setting: "+method);
                carriers.put("carrier", "UPS");
                carriers.put("service", method);
                return carriers;
            }
        }
            if(carrier.contains("USPS")){
                String method = "";
                if(service.equals("Priority")) method =  "Priority";
                if(service.equals("Priority Mail International")) method = 	"Priority Mail International";
                if(service.equalsIgnoreCase("USPS PRIORITY MAIL CUBIC PRICING")) method =  "Priority";
                if(service.equals("UPS Ground")) method = 	"Ground";
                if(service.equals("UPS Next Day Air")) method =  "NextDayAir";
                if(service.equals("UPS Next Day Air A.M.")) method =  "NextDayAirEarlyAM";
                if(service.equals("UPS Next Day Air Saver")) method =  "NextDayAirSaver";

                if(service.equals("UPS Worldwide Expedited")) method =  "Expedited";
                if(service.equals("UPS Worldwide Express")) method =  "Express";
                if(service.equals("UPS Worldwide Express Plus")) method = 	"ExpressPlus";
                if(service.equals("UPS Worldwide Express Saver")) method =  "UPSSaver";
                if(method.length()>0){

                    carriers.put("carrier","USPS");
                    carriers.put("service",method);
                    return carriers;
                }

        }
        if(carrier.contains("PUROLATOR")){
            String method = "";
            method = "PurolatorGround";
          //  if(service.contains("Ground")) method = "PurolatorGround";
           // if(service.contains("Express")) method = "PurolatorExpress";
            if(method.length()>0){

                carriers.put("carrier","Purolator");
                carriers.put("service",method);
                return carriers;
            }
        }
        throw new Exception("Unable to map values " + carrier + " : " + service+". Please update shipping maps or remove Easypost key");

    }

    public static void viewTestMultipieceShipment(){
        try{
            System.out.println("Start");

            EasyPost.apiKey = "hl0OMczdmtkyyCHY8dRxtQ";

            Map<String, Object> fromAddressMap = new HashMap<String, Object>();
            fromAddressMap.put("name", "Simpler Postage Inc");
            fromAddressMap.put("street1", "1915 10th Ave W");

            fromAddressMap.put("city", "Mobridge");
            fromAddressMap.put("state", "SD");
            fromAddressMap.put("zip", "57601");
            fromAddressMap.put("phone", "415-456-7890");

            Map<String, Object> toAddressMap = new HashMap<String, Object>();
            toAddressMap.put("name", "Sawyer Bateman");
            toAddressMap.put("street1", "10 1st Ave E");
            toAddressMap.put("street2", "");
            toAddressMap.put("city", "Mobridge");
            toAddressMap.put("state", "SD");
            toAddressMap.put("zip", "57601");
            toAddressMap.put("phone", "780-483-2746");
            toAddressMap.put("county","US");

            System.out.println("Setting addresses");
            Address fromAddress = Address.create(fromAddressMap);
            Address toAddress = Address.create(toAddressMap);


            Map<String, Object> optionsMap = new HashMap<String, Object>();
            optionsMap.put("label_format", "PNG");
            optionsMap.put("label_size", "4x6");
            optionsMap.put("print_custom_1","custom1");
            optionsMap.put("print_custom_2","custom2");
            optionsMap.put("print_custom_3","custom3");
            optionsMap.put("invoice_number","invoice");

            Map<String, Object> parcelMap = new HashMap<String, Object>();
            parcelMap.put("weight", 22.9);
            parcelMap.put("height", 12.1);
            parcelMap.put("width", 8);
            parcelMap.put("length", 19.8);
            System.out.println("Creating parcels1");
            Parcel p1 = Parcel.create(parcelMap);



            Map<String, Object> parcelMap2 = new HashMap<String, Object>();
            parcelMap2.put("weight", 22.9);
            parcelMap2.put("height", 12.1);
            parcelMap2.put("width", 14);
            parcelMap2.put("length", 19.8);
            Parcel p2 = Parcel.create(parcelMap2);


            System.out.println("Creating parcels");
            List<Map<String, Object>> shipments = new ArrayList<Map<String, Object>>();




            /////////////////////////////////////////////


            Map<String, Object> shipmentMap = new HashMap<String, Object>();

            shipmentMap.put("parcel", p1);

            shipmentMap.put("options",optionsMap);

            System.out.println("Creating shipments1");


            Map<String, Object> shipmentMap2 = new HashMap<String, Object>();

            shipmentMap2.put("parcel", p2);

            shipmentMap2.put("options",optionsMap);

            System.out.println("Creating shipments2");

           shipments.add(shipmentMap);
            shipments.add(shipmentMap2);



           // shipments.setShipments(ship);








            Map<String, Object> orderMap = new HashMap<String, Object>();
           // orderMap.put("reference","testOrder");
            orderMap.put("to_address", toAddress);
            orderMap.put("from_address",fromAddress);
            orderMap.put("shipments",shipments);

            System.out.println("Creating order");
            Order order = Order.create(orderMap);
            System.out.println("Created order");
            List<String> buyCarriers = new ArrayList<String>();
            buyCarriers.add("UPS");
            List<String> buyServices = new ArrayList<String>();
         //   buyServices.add(getEasyPostShipment("UPS","UPS Ground"));
            Map<String,Object> carriers =getEasyPostCarrier("USPS","Priority");


            System.out.println(order.prettyPrint());

            order.buy(carriers);


            System.out.println(order.prettyPrint());







        }catch (Exception e){
            e.printStackTrace();
        }








    }

    public static List<String> viewTestShipment(){
        List<String> labels = new ArrayList<String>();

          try{
              EasyPost.apiKey = "fZYWVmoq0OsLErY2vyVx8g";

              Map<String, Object> fromAddressMap = new HashMap<String, Object>();
              fromAddressMap.put("name", "Shipping Department");
              fromAddressMap.put("street1", "3325 Manitou Court");
              fromAddressMap.put("company","Lyft, Inc.");
              fromAddressMap.put("city", "Mira Loma");
              fromAddressMap.put("state", "CA");
              fromAddressMap.put("zip", "91752");
              fromAddressMap.put("phone", "415-456-7890");

              Map<String, Object> toAddressMap = new HashMap<String, Object>();
              toAddressMap.put("name", "KElly Rowe");
              toAddressMap.put("street1", "2012 Balearic dr");
              toAddressMap.put("street2", "");
              toAddressMap.put("city", "Costa Mesa");
              toAddressMap.put("state", "CA");
              toAddressMap.put("zip", "92626");
              toAddressMap.put("phone", "780-483-2746");
              toAddressMap.put("country", "US");

              Map<String, Object> parcelMap = new HashMap<String, Object>();
              parcelMap.put("weight",7);
              parcelMap.put("height", 7);
              parcelMap.put("width", 18);
              parcelMap.put("length", 11);



              try {
                  Address fromAddress = Address.create(fromAddressMap);
                  Address toAddress = Address.create(toAddressMap);
                  Parcel parcel = Parcel.create(parcelMap);

                  // Address verified = to_address.verify();

                  // customs




                  //other options
                  Map<String, Object> optionsMap = new HashMap<String, Object>();
                  optionsMap.put("label_format", "ZPL");
                  optionsMap.put("label_size", "4x6");
                  // create shipment
                  Map<String, Object> shipmentMap = new HashMap<String, Object>();
                  shipmentMap.put("to_address", toAddress);
                  shipmentMap.put("from_address", fromAddress);
                  shipmentMap.put("parcel", parcel);
                 // shipmentMap.put("customs_info", customsInfo);
                  shipmentMap.put("options",optionsMap);

                  Shipment shipment = Shipment.create(shipmentMap);



                  Map<String,Object> carriers = new HashMap<String, Object>();
                  carriers.put("carrier","USPS");
                  carriers.put("service","Priority");
                  shipmentMap.put("carrier_accounts",carriers);


                //  shipment = shipment.buy(shipment.lowestRate(buyCarriers, buyServices));

                  System.out.println(shipment.prettyPrint());

              } catch (EasyPostException e) {
                  e.printStackTrace();
              }




          } catch (Exception e){
              e.printStackTrace();
          }






        return labels;
    }

    public static Map<String,Object> loadOptionsForShipment(AMPConnectShipShipment ship,Map<String,Object> carriers){
        Map<String, Object> optionsMap = new HashMap<String, Object>();
try {
    if( !carriers.get("carrier").equals("Purolator")) {
        optionsMap.put("label_format", "ZPL");
    }

    optionsMap.put("label_size", "4x6");
    optionsMap.put("invoice_number", ship.getValueAsString("OWDORDERID"));
    optionsMap.put("print_custom_1", ship.getValueAsString("SHIPPER_REFERENCE"));
    optionsMap.put("print_custom_2", ship.getValueAsString("CONSIGNEE_REFERENCE"));
    optionsMap.put("print_custom_3", ship.getValueAsString("PACKAGE_BARCODE"));
    log.debug("Checking for future ship date");
    Map<String, Object> optionsMap2 =  checkForFutureShipDate(ship,carriers);
            if(optionsMap2.size()>0){
                optionsMap.putAll(optionsMap2);
            }


}catch (Exception e){
    e.printStackTrace();
}
        return optionsMap;
    }

    public static Map<String,Object> checkForFutureShipDate(AMPConnectShipShipment ship,Map<String,Object> carriers){
        Map<String, Object> optionsMap = new HashMap<String, Object>();
        if( carriers.get("carrier").equals("Purolator")) {
            LocalDate date = getNextOccuraceOfDayOfWeek(DateTimeConstants.THURSDAY);
            optionsMap.put("label_date",date.toString());
        }

        return optionsMap;
    }
    

    public static LocalDate getNextOccuraceOfDayOfWeek(int day){

        LocalDate date = new LocalDate();
        System.out.println(date.getDayOfWeek());
        System.out.println(date.withDayOfWeek(day));
        if(date.withDayOfWeek(day).isBefore(date)||date.withDayOfWeek(day).equals(date)){
            return (date.withDayOfWeek(day).plusDays(7));
        }else{
           return (date.withDayOfWeek(day));
        }
    }

    public static void checkForAllowedShipMethod(AMPConnectShipShipment ship,Map<String,Object> carriers, String facility, int clientId)throws Exception{
        if(carriers.get("carrier").equals("Purolator")){
            log.debug("We have PUrolaotor");
            if(clientId!=575 ){
                throw new Exception("Invalid ship method client combo");
            }

        }

    }






}

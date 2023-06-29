package com.owd.core.business.order.zoneUtilities;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by danny on 4/30/2018.
 */
public class ZoneLookup {
    protected final static Logger log = LogManager.getLogger();

    public static void main(String[] args){

        System.out.println(lookupZoneFromOrderId(17740655));
       // System.out.println(getMethodForZoneLookup(""));
     /*   shipMethodUpdate smu = checkForShipMethodUpdateByZone(15817856,607,8.0f,true);
        log.debug(smu.getCode());*/
      /*  smu = checkForShipMethodUpdateByZone(15752559, 607, 12.0f,true);
        log.debug(smu.getCode());*/
      /*  smu = checkForShipMethodUpdateByZone(15752559, 607, 6.0f,true);
        log.debug(smu.getCode());*/


    }

    private static String getMethodForZoneLookup(String shipMethod){
        String method = "USPS";

        if(shipMethod.contains("USPS") || shipMethod.contains("DHL")){
            method = "USPS";
        }
        if(shipMethod.contains("FEDEX")){
            method = "FEDEX";
        }
        if(shipMethod.contains("UPS")){
            method = "UPS";
        }



        return method;
    }


    public static int lookupZoneFromOrderId(String orderId){
        return lookupZoneFromOrderId(Integer.parseInt(orderId));
    }


    public static shipMethodUpdate checkForShipMethodUpdateByZone(String orderId, int clientId, String weight,boolean isAPO){
        return checkForShipMethodUpdateByZone(Integer.parseInt(orderId),clientId,Float.parseFloat(weight),isAPO);
    }
    public static shipMethodUpdate checkForShipMethodUpdateByZone(Integer orderId, int clientId,float weight,boolean isAPO){
        shipMethodUpdate smu = new shipMethodUpdate();
        smu.setChange(false);
        if(clientId == 607|| clientId == 488){
           int zone = lookupZoneFromOrderId(orderId);
            log.debug(zone);

           switch (zone){
               case 1:
               case 2:
                   if(weight>=6.0f && weight <= 10.0f){
                       smu.setChange(true);
                       smu.setMethod("USPS Priority Mail");
                       smu.setCode("TANDATA_USPS.USPS.PRIORITY");
                   }
                   if(weight>=17.0f){
                       if(!isAPO) {
                           smu.setChange(true);
                           smu.setMethod("UPS Ground");
                           smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                       }
                   }
                   break;
               case 3:
                   if(weight>=6.0f && weight <= 10.0f){
                       smu.setChange(true);
                       smu.setMethod("USPS Priority Mail");
                       smu.setCode("TANDATA_USPS.USPS.PRIORITY");
                   }
                   if(weight>=13.0f){
                       if(!isAPO) {
                           smu.setChange(true);
                           smu.setMethod("UPS Ground");
                           smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                       }
                   }
                   break;
               case 4:
                   if(weight>=7.0f && weight <= 10.0f){
                       smu.setChange(true);
                       smu.setMethod("USPS Priority Mail");
                       smu.setCode("TANDATA_USPS.USPS.PRIORITY");
                   }
                   if(weight>=13.0f){
                       if(!isAPO) {
                           smu.setChange(true);
                           smu.setMethod("UPS Ground");
                           smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                       }
                   }
                   break;
               case 5:
                   if(weight>=8.0f && weight <= 9.0f){
                       smu.setChange(true);
                       smu.setMethod("FedEx SmartPost Parcel Select");
                       smu.setCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                   }
                   if(weight>=10.0f){
                       if(!isAPO) {
                           smu.setChange(true);
                           smu.setMethod("UPS Ground");
                           smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                       }
                   }
                   break;
               case 6:
                   if(weight>=7.0f && weight <= 9.0f){
                       smu.setChange(true);
                       smu.setMethod("FedEx SmartPost Parcel Select");
                       smu.setCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                   }
                   if(weight>=10.0f){
                       if(!isAPO) {
                           smu.setChange(true);
                           smu.setMethod("UPS Ground");
                           smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                       }
                   }
                   break;
               case 7:
                   if(weight>=6.0f && weight <= 9.0f){
                       smu.setChange(true);
                       smu.setMethod("FedEx SmartPost Parcel Select");
                       smu.setCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                   }
                   if(weight>=10.0f){
                       if(!isAPO) {
                           smu.setChange(true);
                           smu.setMethod("UPS Ground");
                           smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                       }
                   }
                   break;
               case 8:
                   if(weight>=6.0f && weight <= 9.0f){
                       smu.setChange(true);
                       smu.setMethod("FedEx SmartPost Parcel Select");
                       smu.setCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                   }
                   if(weight>=10.0f){
                       if(!isAPO) {
                           smu.setChange(true);
                           smu.setMethod("UPS Ground");
                           smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                       }
                   }
                   break;



           }







        }



        return smu;
    }

    public static int lookupZoneFromOrderId(Integer orderId){

        try {
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
            String zip = order.getShipinfo().getShipZip().substring(0,3);
            log.debug("Zip code: "+zip);

            String lookup = getMethodForZoneLookup(order.getShipinfo().getCarrService());
            log.debug("List lookup:" + lookup);

            String sql = "select zone from owd_facilities_zone_map where zip = :zip and method = :method and facility = :facility";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("zip",zip);
            q.setParameter("method", lookup);
            q.setParameter("facility",order.getFacilityCode());
            List l = q.list();

            if(l.size()>0){
                return Integer.parseInt(l.get(0).toString());
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;


    }
}

package com.owd.core.business.order.zoneUtilities;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdShipMethod;
import com.owd.hibernate.generated.OwdShipServiceFlatrateMap;
import com.owd.hibernate.generated.OwdShipServiceLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by danny on 9/11/2018.
 */
public class FlatRateShipping {

    protected final static Logger log = LogManager.getLogger();
    private static long updated = 0;

    private static ConcurrentHashMap<String,Map<Integer,Map<Integer,Map<Integer,Integer>>>> shippingMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,shipMethodUpdate> methodMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,Integer> serviceLevelMap = new ConcurrentHashMap<>();



    /**
     * Translates service level code into ID
     * @param code Service Level code
     * @return int ID for the service level code
     */

    public static int getServiceLevelIdFromCode(String code){
        if(serviceLevelMap.size()==0){
            loadServiceLevelMap();
        }

        return serviceLevelMap.get(code);
    }
    /**
     * Loads service level info from database into serviceLevelMap. Does not clear the map first just adds.
     */
    private static void loadServiceLevelMap(){
        try{
            Criteria crit = HibernateSession.currentSession().createCriteria(OwdShipServiceLevel.class);

            List<OwdShipServiceLevel> methods = crit.list();
            for(OwdShipServiceLevel level : methods){
                serviceLevelMap.put(level.getLevelCode(),level.getId());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Resest maps to empty so they will be filled new when called again
     */
    public static void resetMaps(){
        log.debug("We are resetting the maps");
        shippingMap = new ConcurrentHashMap<>();
        methodMap = new ConcurrentHashMap<>();

    }



    public static shipMethodUpdate getShipMethodToUse(String clientId, String groupName, String facility, int zone, int weight,int serviceLevel,boolean isAPO) throws Exception {

        shipMethodUpdate smu = new shipMethodUpdate();
        try {
            smu = getShipMethodToUseLogic(clientId, groupName, facility, zone, weight, serviceLevel, isAPO);

        }catch (Exception e){
            if(e.getMessage().contains("Error loading service map")&&groupName.length()>0){
                //retry without group
                smu = getShipMethodToUseLogic(clientId, "", facility, zone, weight, serviceLevel, isAPO);
            }
        }

        return smu;
    }

        /**
         * Get Ship method to use for Flat Rate shipping
         * @param clientId client id of shipment
         * @param groupName groupName of shipment
         * @param facility facility of shipment
         * @param zone zone of shipment
         * @param weight weight of shipment
         * @param serviceLevel service level we are getting method for
         * @return shipMethodUpdate that return ship method name and ship method code
         * @throws Exception
         */
    private static shipMethodUpdate getShipMethodToUseLogic(String clientId, String groupName, String facility, int zone, int weight,int serviceLevel,boolean isAPO) throws Exception{
        log.debug("Getting method for "+clientId);
        shipMethodUpdate smu = new shipMethodUpdate();
        smu.setChange(true);
        String combinedId = getCombinedId(clientId,groupName,facility);
        if(!shippingMap.containsKey(combinedId)){
            loadLookupForClientIdGroupFacility(clientId,groupName,facility);
        }

        int methodId = shippingMap.get(combinedId).get(serviceLevel).get(zone).get(weight);
        if(methodMap.size()==0){
           loadShipMethodMap();
        }
        if(methodMap.containsKey(methodId + "")) {
            smu = getMethodUpdate(methodId+"");
        }else{
            smu.setChange(false);
        }

        if(isAPO && smu.isChange()){
            if(!smu.getCode().contains("USPS")&&!smu.getCode().contains("DHL")){
                switch(serviceLevel){
                    case 1:
                        smu.setMethod("USPS First-Class Mail");
                        smu.setCode("TANDATA_USPS.USPS.FIRST");
                    case 2:
                    case 8:
                        smu.setMethod("USPS Priority Mail");
                        smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
                        break;
                    case 3:
                    case 4:
                    case 9:
                        smu.setMethod("USPS Priority Mail International");
                        smu.setCode("TANDATA_USPS.USPS.I_PRIORITY");
                        break;
                    case 5:
                    case 6:
                        smu.setMethod("USPS Priority Mail Express");
                        smu.setCode("TANDATA_USPS.USPS.EXPR");
                        break;

                }

            }

        }


        return smu;
    }
    private static shipMethodUpdate getMethodUpdate(String methodId){
        shipMethodUpdate smu = new shipMethodUpdate();
        smu.setChange(true);
        smu.setCode(methodMap.get(methodId).getCode());
        smu.setMethod(methodMap.get(methodId).getMethod());
        return smu;
    }
    public static int getShippingMapSize(){
        return shippingMap.size();
    }
    public static int getMethodMapSize(){
        return methodMap.size();
    }

    /**
     * Loads all ship methods from owd_ship_methods into methodMap
     */
    public static void loadShipMethodMap(){
        methodMap = new ConcurrentHashMap<>();
        try{
            Criteria crit = HibernateSession.currentSession().createCriteria(OwdShipMethod.class);

            List<OwdShipMethod> methods = crit.list();

            for(OwdShipMethod method: methods){
                shipMethodUpdate smu = new shipMethodUpdate();
                smu.setChange(true);
                smu.setMethod(method.getMethodName());
                smu.setCode(method.getMethodCode());
                methodMap.put(method.getShipMethodId()+"",smu);

            }

        }catch (Exception e){
            log.debug(e);
        }



    }

    /**
     * Loads the data for the unique combination of parameters into the shippingMap
     * @param clientId ID for the Client of the shipment
     * @param groupName GroupName for the shipment
     * @param facility Location of the shipment
     * @throws Exception when no data is loaded or error loading data
     */
    private static  void loadLookupForClientIdGroupFacility(String clientId, String groupName, String facility) throws Exception{
        String combinedId = getCombinedId(clientId,groupName,facility);
        log.debug("Loading data for "+ combinedId);

        try {
          //Load data from database
            Criteria crit = HibernateSession.currentSession().createCriteria(OwdShipServiceFlatrateMap.class);
            crit.add(Restrictions.eq("clientId",Integer.parseInt(clientId)));
            crit.add(Restrictions.eq("facility",facility));
            crit.add(Restrictions.eq("groupName",groupName));
            crit.addOrder(Order.asc("serviceLevelId"));
            crit.addOrder(Order.asc("zone"));
            crit.addOrder(Order.asc("startWeight"));


            List<OwdShipServiceFlatrateMap> methods = crit.list();

            Map<Integer, Map<Integer, Map<Integer, Integer>>> services = new HashMap<>();

            if(methods.size()==0){
                throw new Exception("Error loading service map for " + combinedId);
            }

            for(OwdShipServiceFlatrateMap method: methods){

                int i = method.getStartWeight();
                while (i<= method.getEndWeight()){
                    if(!services.containsKey(method.getServiceLevelId())){
                        log.debug("Adding serviceLevel Id into map : "+ method.getServiceLevelId());
                        services.put(method.getServiceLevelId(),new HashMap<Integer, Map<Integer, Integer>>());
                    }
                    if(!services.get(method.getServiceLevelId()).containsKey(method.getZone())){
                        log.debug("Addding Zone into Map: "+method.getZone());
                        services.get(method.getServiceLevelId()).put(method.getZone(),new HashMap<Integer,Integer>());
                    }

                    log.debug("Adding weight " + i + " with method id "+method.getShipMethodId() + " to zone " + method.getZone()+" for level "+ method.getServiceLevelId());
                    services.get(method.getServiceLevelId()).get(method.getZone()).put(i,method.getShipMethodId());


                    i++;
                }


            }

          /*  Map<Integer, Integer> weightMethod = new HashMap<>();
            weightMethod.put(1, 1);
            weightMethod.put(2, 2);
            weightMethod.put(3, 3);
            weightMethod.put(4, 1);
            weightMethod.put(5, 2);
            weightMethod.put(6, 3);
            weightMethod.put(7, 1);
            weightMethod.put(8, 2);
            weightMethod.put(9, 3);
            Map<Integer, Map<Integer, Integer>> zones = new HashMap<>();
            zones.put(1, weightMethod);
            zones.put(2, weightMethod);
            zones.put(3, weightMethod);
            zones.put(4, weightMethod);
            zones.put(5, weightMethod);

            Map<Integer, Map<Integer, Map<Integer, Integer>>> services = new HashMap<>();
            services.put(1, zones);
            services.put(2, zones);
            services.put(3, zones);*/

            shippingMap.put(combinedId, services);
        }catch (Exception e){
            log.debug(e);
            throw new Exception("Error loading service map for " + combinedId);
        }



    }



    private static String getCombinedId(String clientId, String groupName, String facility){
        return clientId+"-"+groupName+"-"+facility;
    }

    private static ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();

    static {
        s.scheduleAtFixedRate(new Runnable() {
            public void run() {
                resetMaps();
            }
        }, getHoursUntilTarget(0), 24, TimeUnit.HOURS);
        log.debug("Ran the static block.");

    }

    public static int getHoursUntilTarget(int targetHour) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour < targetHour ? targetHour - hour : targetHour - hour + 24;
    }

    public static shipMethodUpdate getOversizeShipMethod(int zone, int weight, boolean isApoFpo){
        shipMethodUpdate smu = new shipMethodUpdate();
        smu.setChange(true);

        switch (zone){
            case 1:
            case 2:
                if(weight<6){
                    smu.setMethod("FedEx SmartPost Parcel Select");
                    smu.setCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");

                }
                if(weight>=6 && weight < 11){
                    smu.setMethod("USPS Priority Mail");
                    smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
                }
                if(weight>=11 && weight < 16){
                    smu.setMethod("FedEx SmartPost Parcel Select");
                    smu.setCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                }
                if(weight>=16){
                    smu.setMethod("UPS Ground");
                    smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                }
                break;
            case 3:
                if(weight<5){
                    smu.setMethod("FedEx SmartPost Parcel Select");
                    smu.setCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                }
                if(weight>=5 && weight < 11){
                    smu.setMethod("USPS Priority Mail");
                    smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
                }
                if(weight>=11 && weight < 16){
                    smu.setMethod("FedEx SmartPost Parcel Select");
                    smu.setCode("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                }
                if(weight>=16){
                    smu.setMethod("UPS Ground");
                    smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                }
                break;
            case 4:

                if(weight < 11){
                    smu.setMethod("USPS Priority Mail");
                    smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
                }
                if(weight>=11){
                    smu.setMethod("UPS Ground");
                    smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                }
               break;
            case 5:
                if(weight < 10){
                    smu.setMethod("USPS Priority Mail");
                    smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
                }
                if(weight>=10){
                    smu.setMethod("UPS Ground");
                    smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                }
                break;
            case 6:
                if(weight < 9){
                    smu.setMethod("USPS Priority Mail");
                    smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
                }
                if(weight>=9){
                    smu.setMethod("UPS Ground");
                    smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                }
                break;
            case 7:
                if(weight < 5){
                    smu.setMethod("USPS Priority Mail");
                    smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
                }
                if(weight>=5){
                    smu.setMethod("UPS Ground");
                    smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                }
                break;

            case 8:
                if(weight < 3){
                    smu.setMethod("USPS Priority Mail");
                    smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
                }
                if(weight>=3){
                    smu.setMethod("UPS Ground");
                    smu.setCode("CONNECTSHIP_UPS.UPS.GND");
                }
                break;
        }

        if(isApoFpo){
            smu.setMethod("USPS Priority Mail");
            smu.setCode("TANDATA_USPS.USPS.PRIORITY_CUBIC");
        }

        return smu;
    }

    public static ArrayList<OwdShipMethod> getFlatRateShipMethodCodesForClient(int clientId)throws Exception{

            String query = "SELECT DISTINCT dbo.owd_ship_service_flatrate_map.ship_method_id FROM OWD.dbo.owd_ship_service_flatrate_map WHERE client_id=:clientId";
            Query q = HibernateSession.currentSession().createSQLQuery(query);
            q.setInteger("clientId",clientId);
            List<Integer> rates = q.list();
            for(Integer rate: rates){
                System.out.println(rate);
            }
            Criteria crit1 = HibernateSession.currentSession().createCriteria(OwdShipServiceFlatrateMap.class).setProjection(Projections.distinct(Projections.property("shipMethodId"))).add(Restrictions.eq("clientId",clientId));
            List<OwdShipServiceFlatrateMap> map = crit1.list();
            Criteria crit = HibernateSession.currentSession().createCriteria(OwdShipMethod.class).add(Restrictions.in("shipMethodId",rates));
            List results = crit.list();
            ArrayList<OwdShipMethod> methods = new ArrayList<>();
            for(Object obj: results){
                System.out.println(obj);
                OwdShipMethod method = (OwdShipMethod) obj;
                methods.add(method);
            }
            return methods;
    }


    public static void main(String[] args){

        try {
            System.out.println(getShipMethodToUse("375", "", "DC1", 5, 2, 2, false).getMethod());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

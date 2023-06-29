package com.owd.alittlePlaying.concurrentMapPlay;

import com.owd.core.business.order.zoneUtilities.shipMethodUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by danny on 8/24/2018.
 */
public class FlatRateShippingTest {
    protected final static Logger log = LogManager.getLogger();
    private static long updated = 0;

    private static ConcurrentHashMap<String,Map<Integer,Map<Integer,Map<Integer,Integer>>>> shippingMap = new ConcurrentHashMap<>();



    public static shipMethodUpdate getShipMethodToUse(String clientId, String groupName, String facility, int zone, int weight,int serviceLevel){
        log.debug("Getting method for "+clientId);
        shipMethodUpdate smu = new shipMethodUpdate();
        String combinedId = getCombinedId(clientId,groupName,facility);
        if(!shippingMap.containsKey(combinedId)){
            loadLookupForClientIdGroupFacility(clientId,groupName,facility);
        }

        int methodId = shippingMap.get(combinedId).get(serviceLevel).get(zone).get(weight);
        smu.setCode(methodId+"");
        smu.setMethod(methodId+"Method");


        return smu;
    }
    public static int getMapSize(){
        return shippingMap.size();
    }

    public static  void loadLookupForClientIdGroupFacility(String clientId, String groupName, String facility){
        String combinedId = getCombinedId(clientId,groupName,facility);
        log.debug("Loading data for "+ combinedId);
        Map<Integer,Integer> weightMethod = new HashMap<>();
        weightMethod.put(1,1);
        weightMethod.put(2,2);
        weightMethod.put(3,3);
        weightMethod.put(4,1);
        weightMethod.put(5,2);
        weightMethod.put(6,3);
        weightMethod.put(7,1);
        weightMethod.put(8,2);
        weightMethod.put(9,3);
        Map<Integer,Map<Integer,Integer>>  zones = new HashMap<>();
        zones.put(1,weightMethod);
        zones.put(2,weightMethod);
        zones.put(3,weightMethod);
        zones.put(4,weightMethod);
        zones.put(5,weightMethod);

        Map<Integer,Map<Integer,Map<Integer,Integer>>> services = new HashMap<>();
        services.put(1,zones);
        services.put(2,zones);
        services.put(3,zones);

        shippingMap.put(combinedId,services);



    }



    private static String getCombinedId(String clientId, String groupName, String facility){
        return clientId+"-"+groupName+"-"+facility;
    }
}

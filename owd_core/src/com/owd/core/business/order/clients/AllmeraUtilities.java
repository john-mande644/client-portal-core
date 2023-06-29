package com.owd.core.business.order.clients;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 19, 2005
 * Time: 2:37:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AllmeraUtilities extends KitItemUtilities {
private final static Logger log =  LogManager.getLogger();

    public Map getKitMap() {
        return kitMap;

    }

    private static AllmeraUtilities me;

    public synchronized static AllmeraUtilities getInstance() {
        if (me == null) {

            me = new AllmeraUtilities();
            me.removeDuplicates = true;
        }
        return me;
    }

    public AllmeraUtilities() {
        if (kitMap == null || kitMap.size() < 1) {
            kitMap = new TreeMap();

       
            addKitDefinition("PROSAM", "PROSAM", 1);
            addKitDefinition("PROSAM", "PRO102", 1);
            addKitDefinition("PROSAM", "PRO403", 1);

           addKitDefinition("PROSAM", "PRO701", 2);
            addKitDefinition("PROSAM", "PRO801", 2);

        }

    }

    protected Map kitMap = null;


    public static void main(String[] args) {
        AllmeraUtilities pu = new AllmeraUtilities();

        log.debug(pu.getKitMap());
    }
}

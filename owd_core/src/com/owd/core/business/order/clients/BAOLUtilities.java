package com.owd.core.business.order.clients;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 8, 2006
 * Time: 9:56:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class BAOLUtilities extends KitItemUtilities
{
private final static Logger log =  LogManager.getLogger();

         public Map getKitMap() {
        return kitMap;

    }

    private static BAOLUtilities me;

    public synchronized static BAOLUtilities getInstance() {
        if (me == null) {
            me = new BAOLUtilities();
        }
        return me;
    }

    public BAOLUtilities() {
        if (kitMap == null || kitMap.size() < 1) {
            kitMap = new TreeMap();

            addKitDefinition("BUN-S-215", "DFL-114", 1);
            addKitDefinition("BUN-S-215", "DFL2-208", 1);

            /*Sku S-215
Sku's to bundle  DFL-114 Flings
                              DFL2-208 Flings 2
                              */




        }

    }

    protected Map kitMap = null;


    public static void main(String[] args) {
        BAOLUtilities pu = new BAOLUtilities();

        //log.debug(pu.getKitMap());
    }
}

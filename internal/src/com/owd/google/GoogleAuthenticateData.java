package com.owd.google;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;

import java.util.TreeMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 30, 2006
 * Time: 4:54:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoogleAuthenticateData {
private final static Logger log =  LogManager.getLogger();

    public static final ThreadLocal datamap = new ThreadLocal();


    public static final void reset()
    {
          datamap.set(new TreeMap());
    }
    public static Map getDatamap()
     {


     TreeMap s = (TreeMap) datamap.get();
        if (s == null) {
            datamap.set(new TreeMap());
            s = (TreeMap) datamap.get();
        }

         return s;
        }

}

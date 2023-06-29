package com.owd.core.managers;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 12/27/13.
 */
public class MappingManager {
private final static Logger log =  LogManager.getLogger();

    private static MappingManager me = null;

    protected static final Map<String, Map<String,String>> listMap = new HashMap<String, Map<String,String>>();

    private static Date lastRefresh=null;

    public static final String kCountryNameType="COUNTRYNAME";
    public static final String kCountryCodeType="COUNTRYCODE";
    public static final String kShipMethodNameType="SHIPMETHODNAME";
    public static final String kShipMethodCodeType="SHIPMETHODCODE";
    public static final String kUSPSShipMethodNameType="USPSSHIPMETHODNAME";


    private static void refresh() throws Exception
    {
        synchronized(listMap) {
            listMap.clear();
            java.util.Date lastUpdate = new Date();
            lastUpdate.setTime(0L);
            List<OwdMapping> data = (List<OwdMapping>) HibernateSession.currentSession().createQuery("from OwdMapping").list();
            for(OwdMapping item:data)
            {
               if(lastUpdate.getTime()<item.getModifiedDate().getTime())
               {
                   lastUpdate=item.getModifiedDate();
               }
               if(!(listMap.containsKey(item.getType())))
               {
                   listMap.put(item.getType(),new HashMap<String,String>());
               }
               listMap.get(item.getType()).put(item.getKey(),item.getValue());
            }

           lastRefresh = lastUpdate;
        }
    }


    public static Set<String> getTypes()
    {
        synchronized(listMap) {
            updateIfNeeded();
            return listMap.keySet();
        }
    }

    public static Map<String,String> getMapForType(String type)
    {
        synchronized(listMap) {
            updateIfNeeded();
            if(listMap.containsKey(type))
            {
                return listMap.get(type);
            }
            else
            {
                return new HashMap<String,String>();
            }
        }
    }

    public static String getMappedValue(String type, String key)
    {
        synchronized(listMap) {
            updateIfNeeded();
            if(listMap.containsKey(type))
            {
                if( listMap.get(type).containsKey(key.toUpperCase()))
                {
                    return  listMap.get(type).get(key.toUpperCase());
                }  else
                {
                    return key;
                }
            }
            else
            {
                return key;
            }
        }
    }

    private static void updateIfNeeded()
    {
        try{
        ResultSet rs = HibernateSession.getResultSet("select max(lastupdate) from owd_mapping");
        if(rs.next())
        {
            Date lastDate = rs.getDate(1);
            if(lastDate.getTime()>lastRefresh.getTime())
            {
                refresh();
            }
        }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }


}

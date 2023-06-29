package com.owd.core.managers;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdFacility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 8/15/13
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class FacilitiesManager
{
private final static Logger log =  LogManager.getLogger();
    private static List<OwdFacility> allFacilities;
    private static List<OwdFacility> activeFacilities;
    private static List<String> activeFacilityCodes;
    private static List<OwdFacility> activePublicFacilities;
    private static List<String> activePublicFacilityCodes;
    private static Object lockObject = new Object();


    private static long lastUpdate = Calendar.getInstance().getTimeInMillis();
    private static long updatePeriod = 1000L*60L*2L; //two minutes

    public static List<OwdFacility> getActivePublicFacilities() throws Exception
    {
        synchronized(lockObject)
        {
            if(activeFacilities==null || (Calendar.getInstance().getTimeInMillis()-lastUpdate)>updatePeriod)
            {
                activeFacilities = HibernateSession.currentSession().createCriteria(OwdFacility.class).add(Restrictions.eq("isActive", 1)).addOrder(Order.asc("facilityCode")).list();
                lastUpdate = Calendar.getInstance().getTimeInMillis();
            }
        }
        return activeFacilities;
    }




    public static int getFacilityIdForLocationId(int locationId)  throws Exception
    {
        Integer fid = null;
        ResultSet rs = HibernateSession.getResultSet("select f.id from owd_facilities f join (select t.ixParent as Id  from w_location_tree as t, w_location as loc \n" +
                "                  where t.ixParent = loc.ixNode and  t.ixNode="+locationId+"  and ixLocType=5 ) as l on l.Id=f.wloc_node_fkey")  ;

        while(rs.next())
        {
           if(fid!=null)
           {
               throw new Exception("getFacilityIdForLocationId: Multiple facility parents returned for location id "+locationId);
           }
            fid = rs.getInt(1);
        }

        rs.close();

        if(fid==null)
        {
            throw new Exception("getFacilityIdForLocationId: No facility parent returned for location id "+locationId);
        }


        return fid;


    }

    public static OwdFacility getFacilityForLocationId(int locationId) throws Exception
    {
         int facilityId = getFacilityIdForLocationId(locationId);
         OwdFacility f = (OwdFacility) HibernateSession.currentSession().load(OwdFacility.class,facilityId);
         return f;

    }


    public static List<OwdFacility> getActiveFacilities() throws Exception
    {
       synchronized(lockObject)
       {
           if(activeFacilities==null || (Calendar.getInstance().getTimeInMillis()-lastUpdate)>updatePeriod)
           {
               activeFacilities = HibernateSession.currentSession().createCriteria(OwdFacility.class).add(Restrictions.eq("isActive", 1)).addOrder(Order.asc("facilityCode")).list();

               lastUpdate = Calendar.getInstance().getTimeInMillis(); }
       }
        return activeFacilities;
    }
    public static List<OwdFacility> getAllFacilities() throws Exception
    {
        synchronized(lockObject)
        {
            if(allFacilities==null || (Calendar.getInstance().getTimeInMillis()-lastUpdate)>updatePeriod)
            {
                allFacilities = HibernateSession.currentSession().createCriteria(OwdFacility.class).list();

                lastUpdate = Calendar.getInstance().getTimeInMillis(); }
        }
        return allFacilities;
    }

    public static List<String> getActiveFacilityCodes() throws Exception
    {
        synchronized(lockObject)
        {
            if(activeFacilityCodes==null || (Calendar.getInstance().getTimeInMillis()-lastUpdate)>updatePeriod)
            {
                activeFacilityCodes = new ArrayList<String>();

                for(OwdFacility fac:getActiveFacilities())
                {
                    activeFacilityCodes.add(fac.getFacilityCode());

                    lastUpdate = Calendar.getInstance().getTimeInMillis();  }
            }
        }
        return activeFacilityCodes;
    }

    public static List<String> getActivePublicFacilityCodes() throws Exception
    {
        synchronized(lockObject)
        {
            if(activeFacilityCodes==null || (Calendar.getInstance().getTimeInMillis()-lastUpdate)>updatePeriod)
            {
                activeFacilityCodes = new ArrayList<String>();

                for(OwdFacility fac:getActivePublicFacilities())
                {
                    activeFacilityCodes.add(fac.getFacilityCode());
                }
            }

            lastUpdate = Calendar.getInstance().getTimeInMillis();
        }
        return activeFacilityCodes;
    }

    public static OwdFacility getFacilityForCode(String locCode) throws Exception
    {
        return (OwdFacility) HibernateSession.currentSession().createQuery("from OwdFacility where facilityCode='"+locCode+"'").list().get(0);
    }
    public static void main(String[] args)  throws Exception
    {
        log.debug(isClientMultiFacility(489));
    }

    public static String getLocationCodeForClient(int clientID)  throws Exception
    {
        OwdClient oc = (OwdClient)HibernateSession.currentSession().load(OwdClient.class,clientID);
        return oc.getDefaultFacilityCode();
    }

    public static String getHTMLSelectOptions(String currentFacilityCode)
    {
        StringBuffer sb = new StringBuffer();

        try {
            for (OwdFacility f : getActivePublicFacilities()) {
               sb.append("<OPTION VALUE=\""+f.getFacilityCode()+"\" "+(f.getFacilityCode().equals(currentFacilityCode)?"SELECTED":"")+">"+f.getMetroArea()+"\r\n");
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return sb.toString();
    }
    public static String getFacilityDisplayLabel(OwdFacility facility)
    {
        return facility.getFacilityCode()+" - "+facility.getMetroArea();
    }

    public static String getFacilityDisplayLabelForCode(String code)
    {
              try {
                  OwdFacility facility = getFacilityForCode(code);
                  return facility.getFacilityCode() + " - " + facility.getMetroArea();
              }catch(Exception ex)
              {
                  ex.printStackTrace();
              }
        return "";
    }

    public static boolean isClientMultiFacility(int clientID)  throws Exception
    {
        OwdClient oc = (OwdClient)HibernateSession.currentSession().load(OwdClient.class,clientID);
        log.debug(oc.getCompanyName());
        log.debug(getActiveFacilityCodes());
        return "ALL".equals(oc.getDefaultFacilityCode());
    }

    public static int getTimezoneHoursOffsetForFacilityCode(String code)
    {
        try {
            OwdFacility facility = getFacilityForCode(code);
            return facility.getTimezoneOffset();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
}

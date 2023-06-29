package com.owd.core.web;

import com.owd.core.IANACountries;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.type.StringType;

import java.text.DateFormatSymbols;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jun 6, 2008
 * Time: 12:09:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientSecurityContext {
private final static Logger log =  LogManager.getLogger();

    static List<OwdClient> clients;
    OwdClient currentClient;
    boolean isInternalUser;

    public List<OwdClient> getClients() {

        if(clients==null)
        {
            try {
                clients = HibernateSession.currentSession().createQuery("from OwdClient as client where client.isActive=1 order by client.companyName").list();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return clients;
    }

    public void setClients(List<OwdClient> clients) {
        this.clients = clients;
    }

    public OwdClient getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(OwdClient currentClient) {
        this.currentClient = currentClient;
    }

    public boolean getInternalUser() {
        return isInternalUser;
    }

    public void setInternalUser(boolean internalUser) {
        isInternalUser = internalUser;
    }

     public  Map<Integer,String> getCcYearMap() {
        Calendar cal = Calendar.getInstance();
        int thisYear = cal.get(Calendar.YEAR);
        Map<Integer,String> yMap = new TreeMap<Integer,String>();
        for (int i = 0; i < 15; i++) {
            String yStr = ("" + thisYear);

            yMap.put(Integer.parseInt(yStr.substring(yStr.length() - 2)),yStr);
            thisYear++;
        }
        return yMap;
    }

    public static Map<String,String> getStateProvinceMap()  {

        Map<String,String> stateMap = new LinkedHashMap();

        try{


        List<Object[]> states = HibernateSession.currentSession().createSQLQuery("select code,name from ref_statecodes (NOLOCK) order by name").addScalar("code", StringType.INSTANCE).addScalar("name",StringType.INSTANCE).list();
        for(Object[] state:states)
        {
            stateMap.put((String)state[0],(String)state[1]);
        }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return stateMap;


    }

    public static Map<String,String> getCountryMap() {
        return IANACountries.getNations();
    }

    public  Map<Integer,String> getCcMonthMap() {

        Map<Integer,String> yList = new TreeMap<Integer,String>();
        //todo alternative method with passed locale to get locale-specific month names
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] monthNames = symbols.getMonths();

        for (int i = 0; i <= 11; i++) {

            yList.put(i,monthNames[i]);

        }
        return yList;
    }

    public String toString() {
        return "ClientSecurityContext{" +
                "clients=" + clients +
                ", currentClient=" + currentClient +
                ", isInternalUser=" + isInternalUser +
                '}';
    }

    public static void main(String[] args)
    {
        log.debug(getStateProvinceMap());
    }
}

package com.owd.core.business.billing.storage;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by danny on 3/21/2020.
 */
public class cubicStorageUtilities {

    private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args){
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR,2);
            log.debug(cal.get(Calendar.WEEK_OF_YEAR)-1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertStorageRecords() throws Exception{
        List<Integer> clientIds = getClientIds();

        for(Integer id:clientIds){
            insertStorageForClientId(id);
        }




    }

    /**
     * Insert storage records into billing table for provided client id
     * Uses stored procedure billing_getCubicBillingForClientId
     * @param clientId client_id of OWD client to process
     * @throws Exception
     */

    private static void insertStorageForClientId(Integer clientId) throws Exception{
        log.debug("Inserting Records for "+clientId);
        String sql = "insert into sp_bill_cubic_item_storage execute billing_getCubicBillingForClientId :clientId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId",clientId);
        q.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());
    }

    /***
     * Gets list of client id's that have the cubic storage flag set
     * @return
     */

    private static List<Integer> getClientIds() throws Exception{
       String sql = "select client_id from owd_client where bill_cubic_storage = 1";
        Query q  = HibernateSession.currentSession().createSQLQuery(sql);
        List l = q.list();
        List<Integer> ids = new ArrayList<>();

        for(Object data:l){
            ids.add(Integer.parseInt(data.toString()));
        }

        return ids;
    }
}

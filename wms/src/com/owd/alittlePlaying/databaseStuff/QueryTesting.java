package com.owd.alittlePlaying.databaseStuff;

import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by danny on 5/30/2017.
 */
public class QueryTesting {
    private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args){

        try{
         HibernateSession.currentSession();
            ResultSet rs = HibernateSession.getResultSet("select distinct auto_ship_id,ship_company,case when ship_company like 'ExpDr%' then 0 else 1 end from owd_order_auto where status=0 and client_fkey=529 and next_shipment_date<='2018-08-23'\n" +
                    "order by case when ship_company like 'ExpDr%' then 0 else 1 end");
            while (rs.next()) {
                log.debug(rs.getString(1));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        log.debug("before");

        testQuery("p8669356*18841252*b2");
        log.debug("after");

    }

    public static void testQuery(String option1){

        try{
            Query q = HibernateSession.currentSession().createSQLQuery("execute sp_SelectPackageIdFromPackBarcode :barcode");
            q.setParameter("barcode", option1);
            List results = q.list();

        }catch (Exception e){
            e.printStackTrace();
        }



    }
}

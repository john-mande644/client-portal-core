package com.owd.alittlePlaying;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 7/8/2016.
 */
public class setupClientProperties {


    public static List<String> l = new ArrayList<String>();


    public static void main(String[] args){
        l.add("API_BILL_STORAGE_FLAG");
                l.add("API_BILL_RECEIVING_FLAG");
        l.add("API_BILL_SHIPPING_FLAG");
                l.add("API_BILL_RETURNS_FLAG");
        l.add("API_BILL_PICKPACK_FLAG");


        List<String> m = new ArrayList<String>();
        m.add("552");
        m.add("583");
        m.add("587");
        m.add("590");
        m.add("591");
        m.add("592");
        for(String s:m){
            insertForId(s);
        }
    }

    public static void insertForId(String clientid){


    for (String method : l) {
        try {
        String sql = "insert into owd_client_properties (client_fkey,code,[value],subtype) values (:clientId,:code,1,'')";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientid);
            q.setParameter("code",method);
            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        }catch(Exception e){
            e.printStackTrace();
        }

    }






    }
}

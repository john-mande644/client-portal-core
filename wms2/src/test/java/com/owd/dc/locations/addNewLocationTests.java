package com.owd.dc.locations;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Created by danny on 10/22/2018.
 */
public class addNewLocationTests {


    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("com.owd.environment", "test");

    }


    @Test
    public void addNewMobileLocationTreeTest(){

        //addNewMobileLocations(Session sess, String parentId, String newLocationType, String user, String ipAddressOfUser, int qty)
       try{
           List<String> results = addNewLocation.addNewMobileLocations(HibernateSession.currentSession(), "8", "10", "testing", "0.0.0.0", 3);
           HibUtils.commit(HibernateSession.currentSession());
           assertEquals(results.size(), 3);
           System.out.println(results);
           String sql = "select count(*) from w_location_tree where ixNode = :ixNode";

           for(String ixNode:results){
               Query q = HibernateSession.currentSession().createSQLQuery(sql);
               q.setParameter("ixNode",ixNode);
               List l = q.list();
               System.out.println(l.get(0).toString());
               assertTrue("This is the size: "+Integer.parseInt(l.get(0).toString()) +" for id: "+ixNode,Integer.parseInt(l.get(0).toString())==6);
           }


       }catch(Exception e){
           e.printStackTrace();
            }
    }
}

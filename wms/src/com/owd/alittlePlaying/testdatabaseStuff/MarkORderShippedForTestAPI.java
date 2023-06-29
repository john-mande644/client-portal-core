package com.owd.alittlePlaying.testdatabaseStuff;

import com.owd.core.managers.PackingManager;
import com.owd.dc.HandheldUtilities;
import com.owd.hibernate.HibernateSession;

/**
 * Created by danny on 4/11/2017.
 */
public class MarkORderShippedForTestAPI {


    public static void main(String[] args){
        System.setProperty("com.owd.environment","test");
        markShipped("11006506");

    }

    public static void markShipped(String orderID){

        try{

            HibernateSession.currentSession();
            PackingManager.packAndShip(Integer.parseInt(orderID),true, 20, 10.00, "900000000000007", false);


        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

package com.owd.alittlePlaying.orderthings;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderAuto;

/**
 * Created by danny on 10/16/2017.
 */
public class autoOrdersTesting {


    public static void main(String[] args){

        try{
            OwdOrderAuto auto = (OwdOrderAuto) HibernateSession.currentSession().load(OwdOrderAuto.class,38510);
            System.out.println(auto.getBillCompany());
           // System.out.println(auto.getBillCompany().isEmpty());
            if(null != auto.getBillCompany()){
                System.out.println("nuller");

            }

        }catch (Exception e){
            e.printStackTrace();


        }

    }
}

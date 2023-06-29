package com.owd.alittlePlaying.serial;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 10/16/2017.
 */
public class AdditionalInfoTesting {


    public static void main(String[] args){

        List<String> l = new ArrayList<String>();

        l.add("11666000000118189360");
        l.add("11666000000124888446");
        l.add("11666000000118151717");
        l.add("11666000000124857812");
        l.add("11666000000125982203");
        l.add("11666000000125972362");
        l.add("11666000000118152954");
        l.add("11666000000124892917");
        l.add("11666000000125975566");
        l.add("11666000000124922819");
        l.add("11666000000125969011");
        l.add("11666000000124901530");
        l.add("11666000000124882743");
        l.add("11666000000125987203");
        l.add("11666000000078026390");
        l.add("11666000000118133418");
        l.add("11666000000124857784");
        l.add("11666000000125980416");
        l.add("11666000000124882214");
        l.add("11666000000124901821");
        l.add("11666000000124868518");
        l.add("11666000000124924589");
        l.add("11666000000125968544");
        l.add("11666000000125970423");
        l.add("11666000000125976841");
        l.add("11666000000124901861");
        l.add("11666000000124910655");
        l.add("11666000000125967839");
        l.add("11666000000125973570");
        l.add("11666000000124889341");
        l.add("11666000000124878945");
        l.add("11666000000118189003");
        l.add("11666000000124898347");
        l.add("11666000000125968128");
        l.add("11666000000124878273");
        l.add("11666000000124882928");
        l.add("11666000000124919731");
        l.add("11666000000125976399");
        l.add("11666000000124875793");
        l.add("11666000000125976476");
        l.add("11666000000125967374");
        l.add("11666000000124878156");
        l.add("11666000000118164602");
        l.add("11666000000125986821");
        l.add("11666000000124856467");
        l.add("11666000000118177311");
        l.add("11666000000124873808");
        l.add("11666000000125981626");
        l.add("11666000000125980380");
        l.add("11666000000125974246");
        l.add("11666000000078064130");
        l.add("11666000000125987059");
        l.add("11666000000124852197");
        l.add("11666000000124904180");
        l.add("11666000000125978209");
        l.add("11666000000125986769");
        l.add("11666000000125973081");
        l.add("11666000000125970078");
        l.add("11666000000124902827");
        l.add("11666000000125980458");
        l.add("11666000000125974686");
        l.add("11666000000125971496");
        l.add("11666000000125980213");
        l.add("11666000000125973627");
        l.add("11666000000118153126");
        l.add("11666000000124885831");
        l.add("11666000000125976895");
        l.add("11666000000125973354");
        try{
            processList(l);
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    public static void processList(List<String> l)throws Exception{

        List<String> notFound = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();

        for(String s: l){
            Query q = HibernateSession.currentSession().createSQLQuery("select * from owd_inventory_serial where serial_number like :serial");
            q.setParameter("serial","%"+s);
            List results = q.list();
            if(results.size()>0){
                for(Object row:results) {
                    Object[] data = (Object[])row;
                    System.out.println(s + ": " + data[2].toString());
                    ids.add(data[0].toString());
                }
            }else{
                notFound.add(s);
            }




        }
        System.out.println("Not Found");
        for(String s: notFound){
            System.out.println(s);
        }

        System.out.println("ids");
        for(String s: ids){
            System.out.println(s);
        }






    }
}

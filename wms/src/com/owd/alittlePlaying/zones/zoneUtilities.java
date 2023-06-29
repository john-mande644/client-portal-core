package com.owd.alittlePlaying.zones;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.commons.io.FileUtils;
import org.hibernate.Query;

import java.io.File;
import java.util.List;

/**
 * Created by danny on 9/2/2017.
 */
public class zoneUtilities {


    public static void main(String[] args){

       // String s = "005\t6" ;
     /*   String range = "006---009\t8";

        doPostalLine(s,facility);
        doPostalLine(range,facility);*/

        /*String facility = "DC1";
        try{

            List<String> l = FileUtils.readLines(FileUtils.getFile("src/com/owd/alittlePlaying/zones/usps.txt"));
            for(String ss:l){
                doPostalLine(ss,facility);
            }

        }catch (Exception e){
            e.printStackTrace();
        }*/

        String facility = "DC7";
        try{

            List<String> l = FileUtils.readLines(FileUtils.getFile("src/com/owd/alittlePlaying/zones/USPSdc7.txt"));
            for(String ss:l){
                doPostalLine(ss,facility);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public static void doPostalLine(String s, String facility){
        String method = "USPS";

        //Split String should have a tab charcter.
        String ss[] = s.split("\t");
        System.out.println(ss.length);

        System.out.println(ss[0]);

        if (ss[0].contains("---")) {
            String parts[] = ss[0].split("---");
            int begin = Integer.parseInt(parts[0]);
            System.out.println(String.format("%03d", begin));
            int end = Integer.parseInt(parts[1]);
            System.out.println(end);

            while (begin<=end){
                System.out.println(String.format("%03d", begin));
                insertZoneRecord(String.format("%03d", begin),ss[1],method,facility);
                begin++;

            }


        }else{
            insertZoneRecord( ss[0],ss[1],method,facility);
        }




    }

    public static void insertZoneRecord(String zip, String zone, String method, String facility){

        try{

           String sql = "INSERT\n" +
                   "INTO\n" +
                   "    owd_facilities_zone_map\n" +
                   "    (\n" +
                   "        \n" +
                   "        zip,\n" +
                   "        method,\n" +
                   "        facility,\n" +
                   "        zone\n" +
                   "    )\n" +
                   "    VALUES\n" +
                   "    (\n" +
                   "        :zip,\n" +
                   "        :method,\n" +
                   "        :facility,\n" +
                   "        :zone\n" +
                   "    );" ;
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("zip",zip);
            q.setParameter("method",method);
            q.setParameter("facility",facility);
            q.setParameter("zone",zone);

            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

        }catch (Exception e){
            e.printStackTrace();
        }


    }







}

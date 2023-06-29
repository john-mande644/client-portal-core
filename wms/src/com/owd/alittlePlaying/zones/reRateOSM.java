package com.owd.alittlePlaying.zones;

import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 12/22/2017.
 */
public class reRateOSM {
    protected final static Logger log = LogManager.getLogger();

    public static List<String> badOrders = new ArrayList<String>();



    public static void main(String[] args){

     process();


        log.debug("Badd");
        for(String s:badOrders){
            System.out.println(s);
        }




    }

    public static void process(){
        String sql = "SELECT\n" +
                "    dbo.owd_order.order_id,\n" +
                "    dbo.owd_order_ship_info.ship_zip,\n" +
                "    dbo.owd_order_track.weight,\n" +
                "    dbo.owd_order.shipped_on\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order_track\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_track.order_fkey)\n" +
                "WHERE\n" +
                "    dbo.owd_order_ship_info.carr_service = 'OSM Domestic'\n" +
                "AND dbo.owd_order.client_fkey = 607\n" +
                "AND dbo.owd_order_track.is_void = 0\n" +
                "AND dbo.owd_order.shipped_on < '2017-12-01' ;";
        try {

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();

            StringBuilder sb = new StringBuilder();

            for(Object row: l){
                Object[] data = (Object[]) row;
                String s = getRate(data[0].toString(), data[1].toString(),data[2].toString());
                if(s.length()>0){
                    sb.append(s+"\r\n");
                }
            }

            File file = new File("mnml.csv");
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(file.getName(),true);

            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.append(sb.toString());

            bufferWritter.close();


            System.out.println(file.getAbsolutePath());



        }catch (Exception e){
            e.printStackTrace();
        }

    }




    public static String getRate(String orderId, String zip, String weight){

       Float w = Float.parseFloat(weight);
        Double roundedWeight = 0d;
        boolean nozone = false;
        String zone = "";
        if(w<1){
            nozone = true;

            roundedWeight =  Math.ceil((w*16));
            log.debug(roundedWeight);
            log.debug(roundedWeight.intValue());

        }else{
            roundedWeight = Math.ceil(w);
            log.debug(roundedWeight.intValue());

        }

        log.debug(zip.substring(0,3));

        // get Zone
        if(!nozone) {
         try {
             String zonesql = "select zone from owd_facilities_zone_map where zip = :zip and facility = 'DC6'";
             Query zq = HibernateSession.currentSession().createSQLQuery(zonesql);
             zq.setParameter("zip",zip.substring(0,3));
             List l = zq.list();
             if(l.size()>0){

                 zone = l.get(0).toString();

                 String sql = "select rate from owd_rate_table where shipMethod = 'USPS Priority Mail' and weight = :weight and zone = :zone";
                 Query q = HibernateSession.currentSession().createSQLQuery(sql);
                 q.setParameter("weight",roundedWeight.intValue());
                 q.setParameter("zone",zone);
                 List ll = q.list();
                 if(ll.size()==1){
                     return orderId +"," + weight +","+zone+","+ ll.get(0).toString();
                 }else {
                    badOrders.add(orderId);
                 }


             }else{
                badOrders.add(orderId);
             }

             log.debug("Zone: "+ zone);



         }catch (Exception e){

         }
        }else{

            try{

                String sql = "select rate from owd_rate_table where shipMethod = 'USPS First-Class Mail' and weight = :weight";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("weight",roundedWeight.intValue());

                List ll = q.list();
                if(ll.size()==1){
                    return orderId +"," + weight +","+zone+","+ ll.get(0).toString();
                }else {
                    badOrders.add(orderId);
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }




        return "";

    }

}

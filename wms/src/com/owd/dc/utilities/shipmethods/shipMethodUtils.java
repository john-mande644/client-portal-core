package com.owd.dc.utilities.shipmethods;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Query;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/18/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class shipMethodUtils {
    public static Map<String,ArrayList<String>> redMap = new HashMap<String, ArrayList<String>>();
    public static Map<String,ArrayList<String>> fedexPlaneMap = new HashMap<String, ArrayList<String>>();
    public static long updated = 0;

    public static boolean shouldWeUpdate(){

        if (null == redMap || updated == 0)return true;
        if (Calendar.getInstance().getTimeInMillis()-updated>900000) return true;
        return false;
    }
    public static boolean isRedOrder(Integer orderId,String location){
        return isRedOrder(orderId+"", location);
    }
    public static boolean isRedOrder(String orderId,String location){
        boolean red = false;
             try{
                 OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));
                  red = isRedShipMethod(order.getShipinfo().getCarrService(),location);
             } catch (Exception e){
                 e.printStackTrace();
             }

        return red;
    }
    public static boolean isFedexPlaneShipMethod(String shipMethod,String location){
        boolean red = false;
        if (shouldWeUpdate()){
            updateMap();
        }
        if(fedexPlaneMap.containsKey(location)){
            if(fedexPlaneMap.get(location).contains(shipMethod)){
                red = true;
            }

        }
        return red;
    }
    public static boolean isRedShipMethod(String shipMethod,String location){
        boolean red = false;
        if (shouldWeUpdate()){
            updateMap();
        }
        if(redMap.containsKey(location)){
            if(redMap.get(location).contains(shipMethod)){
                red = true;
            }

        }
        return red;
    }
    private static void updateMap(){
        try{
            updated = Calendar.getInstance().getTimeInMillis();
              String sql = "select  facility_code, display from dbo.app_data where project = 'global' and description = 'shipMethod' and variable = 'reds' ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List results = q.list();

            redMap = new HashMap<String, ArrayList<String>>();
            for(Object row:results){
                Object[] data = (Object[]) row;
               if(!redMap.containsKey(data[0].toString())){
                   System.out.printf("Adding facility: %s%n",data[0].toString());
                   redMap.put(data[0].toString(),new ArrayList<String>() );
               }

                redMap.get(data[0].toString()).add(data[1].toString());
                System.out.printf("Added ship method : %s, to Array : %s%n",data[1].toString(), data[0].toString());


            }
            //udpate fedexPlaneMap

            String sql2 = "select  facility_code, display from dbo.app_data where project = 'global' and description = 'shipMethod' and variable = 'fedexPlane' ";
            Query q2 = HibernateSession.currentSession().createSQLQuery(sql2);
            List results2 = q2.list();

            fedexPlaneMap = new HashMap<String, ArrayList<String>>();
            for(Object row:results2){
                Object[] data = (Object[]) row;
                if(!fedexPlaneMap.containsKey(data[0].toString())){
                    System.out.printf("Adding facility: %s%n",data[0].toString());
                    fedexPlaneMap.put(data[0].toString(),new ArrayList<String>() );
                }

                fedexPlaneMap.get(data[0].toString()).add(data[1].toString());
                System.out.printf("Added ship method : %s, to Plane : %s%n",data[1].toString(), data[0].toString());


            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
      /*       System.out.println(isRedShipMethod("UPS 2nd Day Air", "DC1"));
        System.out.println(isRedShipMethod("UPS 2nd Day Air","DC1"));
        System.out.println(isRedShipMethod("UPS 2nd Day Air","DC1"));
        System.out.println(isRedShipMethod("UPS 2nd Day Air","DC1"));System.out.println(isRedShipMethod("UPS 2nd Day Air","DC1"));System.out.println(isRedShipMethod("UPS 2nd Day Air","DC1"));
        System.out.println(isRedShipMethod("UPS 2nd Day Air","DC1"));
        System.out.println(isRedShipMethod("UPS 2nd Day Air","DC1"));

        System.out.println(isRedOrder("6626601","DC1"));*/
        System.out.println(isFedexPlaneShipMethod("FedEx 2Day","DC1"));

    }
}

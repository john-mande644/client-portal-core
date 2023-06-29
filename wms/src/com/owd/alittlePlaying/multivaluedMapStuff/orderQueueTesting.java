package com.owd.alittlePlaying.multivaluedMapStuff;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

/**
 * Created by danny on 6/7/2019.
 */
public class orderQueueTesting {


   private static  MultiValuedMap<String,String> orderMap = new ArrayListValuedHashMap<>();
    public static void main(String[] args){

      /*  fillMap();

        System.out.println(orderMap);
        orderMap.removeMapping("1","b");
        System.out.println(orderMap);*/

       final orderQueueSingleton ss = orderQueueSingleton.getInstance();

        Thread t = new Thread(){
            public void run(){
                System.out.println("t: "+ss.getMapValue("1"));
                System.out.println("t: "+ss.getMapValue("1"));
                System.out.println("t: "+ss.getMapValue("2"));
                System.out.println("t: "+ss.getMapValue("2"));
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                System.out.println(ss.getMapValue("1"));
                System.out.println(ss.getMapValue("1"));
                System.out.println(ss.getMapValue("2"));
                System.out.println(ss.getMapValue("2"));
            }
        };
        t.start();
        t2.start();









    }



    private static void fillMap(){
        orderMap.put("1","a");
        orderMap.put("1","b");
        orderMap.put("1","c");
        orderMap.put("1","d");
        orderMap.put("2","e");
        orderMap.put("2","f");
        orderMap.put("2","g");
        orderMap.put("2","h");
    }
}

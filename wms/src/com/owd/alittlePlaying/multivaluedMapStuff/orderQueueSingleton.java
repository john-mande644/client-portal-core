package com.owd.alittlePlaying.multivaluedMapStuff;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.Iterator;

/**
 * Created by danny on 6/7/2019.
 */
public class orderQueueSingleton {


    private static final orderQueueSingleton instance = new orderQueueSingleton();

    protected orderQueueSingleton(){
        fillMap();
    }

    public static orderQueueSingleton getInstance(){
        return instance;

    }

    public String getMapValue(String id){
        String s =  "";
        synchronized (this) {
            Iterator<String> it = orderMap.get(id).iterator();

            s = it.next();
            orderMap.removeMapping(id, s);
        }

        return s;
    }


    private  MultiValuedMap<String,String> orderMap = new ArrayListValuedHashMap<>();
    private  void fillMap(){
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

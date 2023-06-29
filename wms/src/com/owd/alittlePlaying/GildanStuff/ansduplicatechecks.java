package com.owd.alittlePlaying.GildanStuff;

import com.owd.core.business.order.*;
import com.owd.core.business.order.Package;

import java.util.List;
import java.util.Map;

/**
 * Created by danny on 11/28/2017.
 */
public class ansduplicatechecks {


    public static void main(String[] args){

        try{
            OrderStatus order = new OrderStatus("13955157");
            System.out.println(order.packages.size());


         /* List<Package> packs =  com.owd.core.business.order.Package.getPackagesForOrderId(13793809);

            System.out.println(packs.size());

            for(Package p : packs){
             List<Map> items = p.getEDILineItemsForPackage(p.order_track_id);
               for(Map m:items){
                   System.out.println(m.get("SKU")+"   "+p.order_track_id);
               }
            }
*/



        }catch(Exception e){
            e.printStackTrace();
        }


    }
}

package com.owd.dc.picking;

import com.owd.hibernate.generated.OrderPickItem;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 11/1/11
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderPickItemComparator implements Comparator<OrderPickItem>{
       public int compare(OrderPickItem o1, OrderPickItem o2){
           if(o1.getSort()==null){
               o1.setSort("z");
           }
           if(o2.getSort()==null){
                        o2.setSort("z");
                    }
           int i =  o1.getSort().compareTo(o2.getSort());
           if (i!=0) return i;

           return o1.getId().compareTo(o2.getId());

       }
}

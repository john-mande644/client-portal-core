package com.owd.dc.picking;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 11/30/11
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationListBeanComparator implements Comparator<locationListBean>{

           public int compare(locationListBean o1, locationListBean o2){
               int i =  o1.getSort().compareTo(o2.getSort());
               if (i!=0) return i;

               return o1.getLocation().compareTo(o2.getLocation());

           }


}

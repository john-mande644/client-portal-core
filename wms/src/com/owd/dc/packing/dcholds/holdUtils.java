package com.owd.dc.packing.dcholds;

import com.owd.WMSUtils;
import com.owd.core.business.order.WarehouseHold.holdUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 10/3/13
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class holdUtils {

    public static String setShippingHoldForOrderNum(String orderNum,String name,String error){
        System.out.println(orderNum + "gggggggggggggggggggggggggggg");
        System.out.println("This is our hold info");
        System.out.println(orderNum);
        System.out.println(name);
        System.out.println(error);

       orderNum = WMSUtils.getOrderNumberFromString(orderNum);

        return holdUtilities.setOrderShippingDCHold(orderNum,name,error);
    }
}

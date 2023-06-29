package com.owd.core.business.order.packing;

import com.owd.core.business.order.zoneUtilities.ZoneLookup;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdBoxtypes;
import com.owd.hibernate.generated.OwdShipMethod;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DimensionalWeight {
    public static final int USPS = 194;
    public static final int UPS = 194;
    public static final int DHL = 139;
    public static final int DHL_ECOM = 166;
    public static final int FEDEX = 139;
    private static final int USPS_BALLOON_RATE_MIN = 84;
    private static final int USPS_BALLOON_RATE_MAX = 108;

    public static int getDimensionalWeight(String orderId,  BigDecimal height, BigDecimal width, BigDecimal depth, OwdShipMethod shipMethod){
        return getDimensionalWeight(Integer.parseInt(orderId),height,width,depth,shipMethod);
    }

    public static int getDimensionalWeight(String orderId,  BigDecimal height, BigDecimal width, BigDecimal depth, String method_code){
        return getDimensionalWeight(orderId,  height.doubleValue(), width.doubleValue(), depth.doubleValue(), method_code);
    }

    public static int getDimensionalWeight(String orderId, Double height, Double width, Double depth, String method_code){
        return getDimensionalWeight(Integer.parseInt(orderId), height, width, depth, method_code);
    }

    public static int getDimensionalWeight(int orderId,  Double height, Double width, Double depth, String method_code){
        try{
        Criteria crit = HibernateSession.currentSession().createCriteria(OwdShipMethod.class).add(Restrictions.eq("methodCode",method_code));
        Object obj = crit.uniqueResult();
        OwdShipMethod shipMethod = (OwdShipMethod) obj;
        if(shipMethod == null){
            throw new Exception("Database returned 0 results for method_code: " + method_code);
        }
            return getDimensionalWeight(orderId,height,width,depth,shipMethod);
        }catch(Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    public static int getDimensionalWeight(int orderId, BigDecimal height, BigDecimal width, BigDecimal depth, OwdShipMethod shipMethod) {
        return getDimensionalWeight(orderId,height.doubleValue(), width.doubleValue(), depth.doubleValue(), shipMethod);
    }

    public static int getDimensionalWeight(int orderId, double height, double width, double depth, OwdShipMethod shipMethod){
        // we have a divisor check if the rate is USPS and if balloon rate applies
        if(shipMethod.getMethodCode().contains("USPS")){
            int zone = ZoneLookup.lookupZoneFromOrderId(orderId);
            if(checkForBalloonRate(height, width, depth, zone)){
                return 20;
            }
        }

        // check if the ship method has a divisor set if not return 0 instead of trying to divide by 0
        if(shipMethod.getDivisor()==null || shipMethod.getDivisor()==0) return 0;
        //  we have a divisor, it isn't usps or the balloon rate doesn't apply so lets return the dim weight

        return (int) Math.ceil((height * width * depth)/shipMethod.getDivisor());
    }

    public static boolean checkForBalloonRate( double height, double width, double depth, int zone){
        if(zone > 0 && zone < 5){
            ArrayList<Double> sides = new ArrayList<>();
            sides.add(width);
            sides.add(depth);
            sides.add(height);
            Collections.sort(sides,Collections.<Double>reverseOrder());
            double size = (sides.get(0) + ((sides.get(1) + sides.get(2)) *2));
            return (size > USPS_BALLOON_RATE_MIN && size < USPS_BALLOON_RATE_MAX);
        }
        return false;
    }

    public static boolean checkForDimEligability(Double weight, String methodCode, double dimWeight){
        if (dimWeight < Math.ceil(weight)) return false;
        if (weight < 1){
            if(methodCode.equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP")) return false;
            if(methodCode.equals("CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND")) return false;
        }
        return true;
    }

}

package com.owd.alittlePlaying;

import com.owd.core.CountryNames;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.ShippingInfo;
import com.owd.core.csXml.OrderRater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danny on 8/10/2016.
 */
public class OrderRaterBS {

    public static void main(String[] args){


        try{
           System.out.println(OrderRater.getRateableServicesMapByLocation("DC1"));
            try {
                Order order = new Order("55");
                //order.setSignatureRequired(true);
                ShippingInfo shi = new ShippingInfo();

                order.getShippingAddress().address_one = "10 1st aVe E";
                order.getShippingAddress().city="Mobridge";
                order.getShippingAddress().zip="57601";
                order.getShippingAddress().state="SD";
                order.getShippingAddress().country= CountryNames.getCountryName("United States");

                order.getBillingAddress().address_one = "10 1st aVe E";
                order.getBillingAddress().city="Mobridge";
                order.getBillingAddress().zip="57601";
                order.getBillingAddress().state="SD";
                order.getBillingAddress().country=CountryNames.getCountryName("United States");




                order.addLineItem("Ebay Items", 1, 0.00f, 0.00f, "test", "", "");

                List<String> codes = new ArrayList<>();
                codes.add("UPS.2DA");
                OrderRater rater = new OrderRater(order);
                rater.setForcePackagingWeight(1.2f);
                rater.setForcePackageZeroWeight(true);
                rater.setOriginCode("DC1");
                rater.rate(codes);
                System.out.println(rater.theResponse);
                System.out.println(rater.theResponse.get(0).finalRate);


                // System.out.println(RateShopper.rateShop(order, Arrays.asList("UPS.2DA")));
            }catch(Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

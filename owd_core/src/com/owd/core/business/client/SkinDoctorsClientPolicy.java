package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import com.owd.core.csXml.OrderRater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 24, 2008
 * Time: 3:51:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SkinDoctorsClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();


    public boolean allowUnitPriceEditing(Order order) {
        return true;    //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean allowManualDiscountEntry(Order order) {
        return true;    //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean allowShipPriceEditing(Order order) {
        return true;    //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean allowSalesTaxEditing(Order order) {
        return false;    //To change body of overridden methods use File | Settings | File Templates.
    }

    public float getSalesTaxValue(Order order) throws Exception {

        if ("CA".equalsIgnoreCase(order.getShippingAddress().state) || "California".equalsIgnoreCase(order.getShippingAddress().state)) {
            //order.tax_pct = (float) 0.0775;
            return (float) 0.0925 * (order.total_shipping_cost + order.total_product_cost - (Math.abs(order.discount)));
        } else if ("SD".equalsIgnoreCase(order.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(order.getShippingAddress().state)) {
            //  order.tax_pct = (float) 0.060000;
            return (float) 0.060000 * (order.total_shipping_cost + order.total_product_cost - (Math.abs(order.discount)));
        } else {
            return 0.00f;
        }
    }



    @Override
    public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order) {
        if (remoteSourceKey.equalsIgnoreCase("magento")) {
            if (remoteMethod.toUpperCase().contains("STANDARD") || remoteMethod.toUpperCase().contains("FREE")) {
                try {
                    OrderRater rater = new OrderRater(order);
                    rater.setCalculateKitWeights(true);
                    List<String> alternateShipMethodList = new ArrayList<String>();
                    alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                    alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.STD");
                    alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.GND");
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");

                    return rater.selectBestWay(alternateShipMethodList);

                } catch (Exception ex) {
                    return "No valid rate returned";
                }
            }
        }
        return remoteMethod;
    }







    public void saveNewOrder(Order order, boolean testing) throws Exception {


        super.saveNewOrder(order, testing);    //To change body of overridden methods use File | Settings | File Templates.
    }
}

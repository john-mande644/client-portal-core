package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import com.owd.core.csXml.OrderRater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 24, 2008
 * Time: 3:51:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class DrCherryClientPolicy extends DefaultClientPolicy {
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
            return (float) 0.0875 * (order.total_shipping_cost + order.total_product_cost - (Math.abs(order.discount)));
        } else if ("SD".equalsIgnoreCase(order.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(order.getShippingAddress().state)) {
            //  order.tax_pct = (float) 0.060000;
            return (float) 0.060000 * (order.total_shipping_cost + order.total_product_cost - (Math.abs(order.discount)));
        } else {
            return 0.00f;
        }
    }


    public float getShippingCost(Order order, String shipType, List shipOptions) throws Exception {
        return getShippingCost(order, getShipOptionForType(shipType, shipOptions));

    }

    public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {
        float shipCost = 0.0000f;

        order.recalculateBalance();


        if (order.total_product_cost < 25.00f) {
            shipCost = 6.95f;
        } else if (order.total_product_cost < 50.00f) {
            shipCost = 7.95f;
        } else if (order.total_product_cost < 100.00f) {
            shipCost = 8.95f;
        } else if (order.total_product_cost < 200.00f) {
            shipCost = 9.95f;
        } else {
            shipCost = 0.00f;
        }

        if (shipOption.code.equals("CONNECTSHIP_UPS.UPS.NDS")) {
            shipCost += 20.00;
        } else if (shipOption.code.equals("CONNECTSHIP_UPS.UPS.2DA")) {
            shipCost += 10.00;

        } else if (shipOption.code.equals("CONNECTSHIP_UPS.UPS.STD")) {

            shipCost += 10.00;
        }
        return shipCost;
    }

    private float getShipCcost(Order order) throws Exception {
        float shipCost = 6.9500f;

        order.recalculateBalance();

        if (!order.getShippingAddress().isUS()) {

            if (order.total_product_cost < 25.00f) {
                shipCost = 21.95f;
            } else if (order.total_product_cost < 50.00f) {
                shipCost = 22.95f;
            } else if (order.total_product_cost < 100.00f) {
                shipCost = 23.95f;
            } else if (order.total_product_cost < 200.00f) {
                shipCost = 24.95f;
            } else {
                shipCost = 15.00f;
            }
        }

        return shipCost;
    }


    public static void main(String args[]) throws Exception {

        DrCherryClientPolicy pol = new DrCherryClientPolicy();

        Order order = new Order("402");
        order.getShippingAddress().address_one = "20305 6th St NE";
        order.getShippingAddress().city = "Snohomish";
        order.getShippingAddress().state = "Washington";
        order.getShippingAddress().zip = "98290";

        log.debug(pol.getOWDShipMethodForExternalShipMethodName("magento", "Free Shipping - Free", order));
    }

    @Override
    public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order) {
        if (remoteSourceKey.equalsIgnoreCase("magento")) {
            if (remoteMethod.toUpperCase().contains("STANDARD SHIPPING") || remoteMethod.toUpperCase().contains("FREE")) {
                try {
                    OrderRater rater = new OrderRater(order);
                    rater.setClientId(order.getClientID());

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

    public List getShipOptions(Order order, float defaultCost) {


        List options = new ArrayList();
        try {
            ShippingOption so = new ShippingOption();
            so.desc = "";
            if (order.getShippingAddress().isUS()) {
                so.name = "USPS Priority Mail";
                so.code = "TANDATA_USPS.USPS.PRIORITY";
                so.ratedCost = getShippingCost(order, so);
                so.customerCost = getShippingCost(order, so);
                options.add(so);

                so = new ShippingOption();
                so.desc = "";
                so.name = "UPS 2nd Day Air";
                so.code = "CONNECTSHIP_UPS.UPS.2DA";
                so.ratedCost = getShippingCost(order, so);
                so.customerCost = getShippingCost(order, so);
                options.add(so);

                so = new ShippingOption();
                so.desc = "";
                so.name = "UPS Next Day Air Saver";
                so.code = "CONNECTSHIP_UPS.UPS.NDS";
                so.ratedCost = getShippingCost(order, so);
                so.customerCost = getShippingCost(order, so);
                options.add(so);


            } else {
                so.name = "UPS Standard to Canada";
                so.code = "CONNECTSHIP_UPS.UPS.STD";
                so.ratedCost = getShippingCost(order, so);
                so.customerCost = getShippingCost(order, so);


                options.add(so);
            }


        } catch (Exception exx) {
            exx.printStackTrace();
        }

        return options;

    }

    public Map getPaymentOptions() throws Exception {
        return super.getPaymentOptions();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static long getdaysSinceGivenDate(Calendar calpast) {

        Calendar cal1 = Calendar.getInstance();

        long pastmillis = calpast.getTimeInMillis();
        long nowmillis = cal1.getTimeInMillis();
        if (pastmillis >= nowmillis) {
            // return 0;
        }

        long diff = nowmillis - pastmillis;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;

    }

    public void saveNewOrder(Order order, boolean testing) throws Exception {


        order.deleteLineItemForSKU("BB-14PTH");
        order.addInsertItemIfAvailable("BB-14PTH", 1);


        if (order.containsSKU("MMS60")) {
            order.deleteLineItemForSKU("ANN-MMS");
            order.addInsertItemIfAvailable("ANN-MMS", 1);
        }


        if (order.containsSKU("BSS120")) {
            order.deleteLineItemForSKU("BSSANN");
            order.addInsertItemIfAvailable("BSSANN", 1);
        }

        if (order.containsSKU("VS60")) {
            order.deleteLineItemForSKU("ANN-VS60");
            order.addInsertItemIfAvailable("ANN-VS60", 1);
        }

        if (order.containsSKU("MMS6MO")) {
            order.addInsertItemIfAvailable("BKBHS", 1);
        }

        if (order.containsSKU("MMS3MO")) {
            order.addInsertItemIfAvailable("BKBHS", 1);
        }

        if (order.containsSKU("VS60")) {
            order.deleteLineItemForSKU("ANN-VS60");
            order.addInsertItemIfAvailable("ANN-VS60", 1);
        }

        // ANN-HTDT  to each order containing JS120 and IS120
        if (order.containsSKU("JS120") || order.containsSKU("IS120")) {
            order.deleteLineItemForSKU("ANN-HTDT");
            order.addInsertItemIfAvailable("ANN-HTDT", 1);
        }

        if (order.coupon_code.equalsIgnoreCase("MD611")) {
            order.addInsertItemIfAvailable("BKVIT", 1);
        }

        if (order.containsSKU("BNS60")) {
            order.deleteLineItemForSKU("BB-WBNS");
            order.addInsertItemIfAvailable("BB-WBNS", 1);
        }

        if (!(order.containsSKU("BNS60"))) {
            order.deleteLineItemForSKU("BB-WOBN2");
            order.addInsertItemIfAvailable("BB-WOBN2", 1);


        }


        order.deleteLineItemForSKU("BB-SSPS");
        order.addInsertItemIfAvailable("BB-SSPS", 1);

        /*
        1.)  If the order DOES contain <BNS60>, insert 1 unit of <BB-WBNS> per order.

2.)  If the order DOES NOT contain <BNS60>, insert 1 unit of <BB-WOBN> per order.

3.)  All orders, insert 1 unit of <BB-HTVS> per order.
         */

        super.saveNewOrder(order, testing);    //To change body of overridden methods use File | Settings | File Templates.
    }
}

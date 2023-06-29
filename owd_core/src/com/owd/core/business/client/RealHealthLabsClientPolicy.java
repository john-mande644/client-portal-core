package com.owd.core.business.client;

import com.owd.core.business.order.Event;
import com.owd.core.business.order.Order;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.CaseTrackerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 24, 2008
 * Time: 3:51:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class RealHealthLabsClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    public String getShipOptionName(String optionType, List shipOptions) {

        if (shipOptions.size() > 0) {
            return (((ShippingOption) shipOptions.get(0)).name);


        } else {
            return "No ship method found for code " + optionType;
        }

    }

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
            return (float) 0.0725 * (order.total_shipping_cost + order.total_product_cost - (Math.abs(order.discount)));
        } else
        if ("SD".equalsIgnoreCase(order.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(order.getShippingAddress().state)) {
            //  order.tax_pct = (float) 0.060000;
            return (float) 0.060000 * (order.total_shipping_cost + order.total_product_cost - (Math.abs(order.discount)));
        } else {
            return 0.00f;
        }
    }


    public String getShippingMethod(Order order, String shipType, List shipOptions) throws Exception {
        return getShipOptionForType(shipType, shipOptions).name;
    }

    public ShippingOption getShipOptionForType(String shipType, List shipOptions) throws Exception {
        if (shipOptions.size() > 0) {
            return ((ShippingOption) shipOptions.get(0));


        } else {
            throw new Exception("Shipping Option " + shipType + " not found!");

        }


    }

    public float getShippingCost(Order order, String shipType, List shipOptions) throws Exception {
        return getShipCost(order);

    }

    public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {
        return getShipCost(order);
    }

    private float getShipCost(Order order) throws Exception {
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

    public List getShipOptions(Order order, float defaultCost) {


        List options = new ArrayList();

        ShippingOption so = new ShippingOption();
        so.desc = "";
        if (order.getShippingAddress().isUS()) {
            so.name = "USPS Priority Mail";
            so.code = "TANDATA_USPS.USPS.PRIORITY";

        } else {
            so.name = "USPS Priority Mail International";
            so.code = "TANDATA_USPS.USPS.I_PRIORITY";
        }

        try {
            so.ratedCost = getShippingCost(order, so);
            so.customerCost = getShippingCost(order, so);


            options.add(so);
        } catch (Exception exx) {
            exx.printStackTrace();
        }

        return options;

    }

    public Map getPaymentOptions() throws Exception {
        return super.getPaymentOptions();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void saveNewOrder(Order order, boolean testing) throws Exception {

        if(order.getShippingAddress().country.equalsIgnoreCase("ITALY") || order.getShippingAddress().country.equalsIgnoreCase("UNITED KINGDOM") || order.total_product_cost>=500.00)
        {
           order.is_future_ship=1;
        }
        super.saveNewOrder(order, testing);    //To change body of overridden methods use File | Settings | File Templates.                                        kerManagerrrrrrrrrr

        if(order.getShippingAddress().country.equalsIgnoreCase("ITALY") || order.getShippingAddress().country.equalsIgnoreCase("UNITED KINGDOM") || order.total_product_cost>=500.00)
        {
    Event.addOrderEvent(Integer.parseInt(order.orderID), Event.kEventTypeHandling, "Order held due to order fraud check rule", "ORDER RELEASE CHECK");

        //    Mailer.sendMail("Canadian Order Placed On Hold (OWD Ref:" + order.orderNum + ")", "Order held at OWD due to blocked product shipping to Canada", "sales@ezultrasound.com", "donotreply@owd.com");

            CaseTrackerManager.createNewCaseForClient("Real Health Labs Order Placed On Hold (OWD Ref:" + order.orderNum + ")",
                    "Order held due to order fraud check rule - verify for fraud before releasing", order.clientID, null, CaseTrackerManager.getLoginToken());

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
}

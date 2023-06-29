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
 * Date: 1/28/12
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class TruePotentialClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

       public void saveNewOrder(Order order, boolean testing) throws Exception {

        if(!(order.containsSKU("thank you")))
        {
            order.addInsertItemIfAvailable("thank you",1);
        }

           if(!(order.containsSKU("thank you")))
           {
               order.addInsertItemIfAvailable("thank you",1);
           }


        super.saveNewOrder(order, testing);    //To change body of overridden methods use File | Settings | File Templates.


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

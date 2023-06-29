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
 * Date: 3/22/12
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class KathySmithClientPolicy extends DefaultClientPolicy  {
private final static Logger log =  LogManager.getLogger();

    @Override
    public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity) throws Exception {

        log.debug("checking SKU "+sku);
           if(sku.toUpperCase().startsWith("DS-"))
           {
               sku =  "SHIP-SEPARATE";
               description = description+" (Shipped Separately)";
           }
        log.debug("checked SKU "+sku);

        super.addLineItemToOrder(order, sku, description, price, quantity);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void saveNewOrder(Order order, boolean testing) throws Exception {

        if(order.containsPhysicalItem())
        {
        if(!(order.containsSKU("insert")))
        {
        order.addInsertItemIfAvailable("insert",1);
        }

        super.saveNewOrder(order, testing);    //To change body of overridden methods use File | Settings | File Templates.
    }
    }

      public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order) {
        if (remoteSourceKey.equalsIgnoreCase("magento")) {
            if (remoteMethod.toUpperCase().contains("STANDARD") || remoteMethod.toUpperCase().contains("FREE")  || remoteMethod.toUpperCase().contains("INTERNATIONAL")) {
                try {
                    OrderRater rater = new OrderRater(order);
                    rater.setCalculateKitWeights(true);
                    List<String> alternateShipMethodList = new ArrayList<String>();
                    alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                    alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                    alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.GND");
                  //  alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
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

package com.owd.core.business.client;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 2, 2009
 * Time: 2:31:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class JimScharfClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

     public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {

           if(order.containsSKU("OFFER2REFILLANDFREESHIP") && (shipOption.name.equalsIgnoreCase("USPS Priority Mail")
           || shipOption.name.equalsIgnoreCase("USPS First-Class Mail")))
           {
                return OWDUtilities.roundFloat(0.00f);
           }
         else
           {
                return OWDUtilities.roundFloat(shipOption.ratedCost);
           }

    }
}

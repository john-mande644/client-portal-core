package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import com.owd.core.business.order.clients.BAOLUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 7, 2006
 * Time: 11:06:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class BAOLClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity) throws Exception {

        BAOLUtilities.getInstance().addItem(order, sku, quantity,price,"0.00",description, "","");    //To change body of overridden methods use File | Settings | File Templates.
    }

    public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {

         /*

Below are the shipping rules for BAOL.  Can these be added for the
CSR's when placing orders?

Below are our standard shipping charges for orders:

Shipping and Handling (all orders must add in)
    - $7.50 for first item, $2.00 for each additional item

FedEx Second Day Air, add $10.00 to above amount

FedEx Overnight, add $25.00 to above amount

Fed Ex Saturday Delivery, add $31.00 to above amount   (N/A, SB)

UPS 2nd Day Air, add $12.00 to above amount

International Orders, add $20 to above amount


*/

        double sh = 0.00d;

        long units = order.getTotalUnitQuantity();

        sh = 7.50d;

        if(units>1)
        {
        sh  += ((units-1)*(2.00d));
        }

        if(order.getShippingAddress().isInternational())
        {
            sh += 20.00d;
        }else
        {
            if("FedEx 2Day".equalsIgnoreCase(shipOption.name))

            {
             sh += 10.00d;

            }  else if("UPS 2nd Day Air".equalsIgnoreCase(shipOption.name))

            {
             sh += 12.00d;

            } else if( "FedEx Priority Overnight".equalsIgnoreCase(shipOption.name) ||
                        "FedEx Standard Overnight".equalsIgnoreCase(shipOption.name))

            {
             sh += 25.00d;

            } else
            {
                //nothing
            }
        }




           return new Double(sh).floatValue();


    }

}

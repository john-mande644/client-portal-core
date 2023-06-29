package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 7, 2006
 * Time: 11:06:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class EastCoastTradingClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    public boolean allowUnitPriceEditing(Order order) {
return true;    }

    public boolean allowManualDiscountEntry(Order order) {
        return true;     //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean allowShipPriceEditing(Order order) {
        return true;     //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean allowSalesTaxEditing(Order order) {
        return true;     //To change body of overridden methods use File | Settings | File Templates.
    }


    public void setSignatureRequired(Order order) throws Exception
    {
        order.recalculateBalance();
        if(order.total_product_cost>=250.00)
        {
           order.getShippingInfo().ss_proof_delivery = "1";
            
            if(order.getShippingInfo().carr_service.indexOf("First-Class")>=0
                    || order.getShippingInfo().carr_service.indexOf("Parcel Post")>=0)
            {
                if(order.getShippingInfo().shipAddress.isInternational())
                {
                    order.getShippingInfo().setShipOptions("USPS Priority Mail International","Prepaid","");
                }   else
                {
                order.getShippingInfo().setShipOptions("USPS Priority Mail","Prepaid","");
            }
            }
        }
        
        order.getShippingInfo().ss_proof_delivery = "0";
    }
}

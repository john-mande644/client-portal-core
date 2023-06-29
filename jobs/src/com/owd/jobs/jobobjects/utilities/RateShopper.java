package com.owd.jobs.jobobjects.utilities;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 9/21/12
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class RateShopper {
private final static Logger log =  LogManager.getLogger();

    public static String rateShop(Order order, List<String> realMethods){
        return rateShop(order,realMethods,null,false);
    }
    public static String rateShop(Order order, List<String> realMethods,boolean signatureRequired)
    {
        return rateShop(order,realMethods,null,signatureRequired);
    }
    public static String rateShop(Order order, List<String> realMethods, String originCode,boolean signatureRequired)
    {
        try{
        if(order.getTotalPhysicalUnitQuantity()>0)
        {
        try{

            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.decode(order.clientID));

            OrderRater rater = new OrderRater(order);

            rater.setOriginCode(originCode==null?client.getDefaultFacilityCode():originCode);

            rater.setCalculateKitWeights(true);
            if(client.getClientId()==551)
            {
                rater.setFedexDiscountRate(0.35);
            }

            return rater.selectBestWay(realMethods,signatureRequired);

        }  catch(Exception ex)
        {
            order.is_future_ship=1;
            if(!(OrderUtilities.orderRefnumExists(order.order_refnum,order.clientID)))
            {
            CasetrackerAPI.createCasetrackerCase("Client ID (" + order.clientID + ") order " + order.order_refnum + " received on hold", "Unable to determine ship method for this order; address or items invalid for available ship method options - attempt to correct address and assign to IT Work Orders if needed.", Integer.parseInt(order.clientID));
            }
                order.hold_reason = "Unable to determine ship method for this order; address or items invalid for available ship method options - attempt to correct address and assign to IT Work Orders if needed.";

        }
        }
        }  catch(Exception ex)
        {}

        return "USPS Priority Mail";
    }
}

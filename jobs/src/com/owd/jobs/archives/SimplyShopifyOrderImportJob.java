package com.owd.jobs.archives;

import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Vector;

/**
 * Created by stewartbuskirk1 on 8/16/15.
 */
public class SimplyShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        run();

    }


/*


API Key
0474d0a7680a35fd6eedd3b7d61f36d2
Password
3753d79b1a9b4cbe4d35ba5b42114082
Shared Secret
f8f7c5a63377766809aef45d2d58c5cd
URL Format
https://apikey:password@hostname/admin/resource.json
Example URL
https://0474d0a7680a35fd6eedd3b7d61f36d2:3753d79b1a9b4cbe4d35ba5b42114082@thesimplyco.myshopify.com/admin/orders.json


 */


    @Override
    public void internalExecute() {

        log.debug("starting");

        ShopifyAPI api = new ShopifyAPI("0474d0a7680a35fd6eedd3b7d61f36d2",
                "3753d79b1a9b4cbe4d35ba5b42114082", "thesimplyco.myshopify.com","") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {
                order.template="simplyretail";
                for(LineItem line:(Vector<LineItem>)order.skuList){
                    if(line.getInventory().description.toUpperCase().contains("WHOLESALE")) {
                        order.template = "simplywholesale";
                    }
                }
            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                if(oldMethod.toUpperCase().contains("INTERNATIONAL FIRST CLASS MAIL"))
                {
                     return "USPS First-Class Mail International";
                }  else if(oldMethod.toUpperCase().contains("PRIORITY") && !(order.getShippingAddress().isUS()))
                {
                    return "USPS Priority Mail International";
                }  else if(oldMethod.toUpperCase().contains("INTERNATIONAL SHIPPING"))
                {

                    return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.I_PRIORITY",
                            "TANDATA_USPS.USPS.I_FIRST"));
                }   else if(oldMethod.toUpperCase().contains("EXPEDITED 2ND DAY"))
                {
                    return RateShopper.rateShop(order, Arrays.asList("CONNECTSHIP_UPS.UPS.2DA",
                            "TANDATA_FEDEXFSMS.FEDEX.2DA"));
                }  else if(oldMethod.toUpperCase().contains("USPS FIRST CLASS MAIL"))
                {
                    return "USPS First-Class Mail";
                }  else if(oldMethod.toUpperCase().contains("PRIORITY") && (order.getShippingAddress().isUS()))
                {
                    return "USPS Priority Mail";
                }   else {

                    return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                            "TANDATA_USPS.USPS.PRIORITY_CUBIC","TANDATA_USPS.USPS.PRIORITY","CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD"));
                }

            }
        };

        api.setClientInfo(601, null);
        api.setFirstOrderId(1538);
        api.setOrderReferencePrefix("S");
        api.setOrderType("Shopify:Simply") ;

        //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid, false);
        api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);


        //off for now due to OOS
        try {
            api.updateInventoryLevels();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

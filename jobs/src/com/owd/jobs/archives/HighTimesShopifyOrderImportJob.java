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
 * Created by dnickels on 12/6/16.
 */
public class HighTimesShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
      //  run();

        ShopifyAPI api = new ShopifyAPI("585ee8dc7245c2637b390a78243e0a0f",
                "26ed3eb1dc574e8664480a8a0d8d1c9c", "shop-hightimes-com.myshopify.com","");
        api.printListOfLocations();
//        api.getProductCount();

    }


/*


API key
585ee8dc7245c2637b390a78243e0a0f
Password
26ed3eb1dc574e8664480a8a0d8d1c9c
Shared secret
7b3c6c2977257b72a84666d0eaa847ea
URL format
https://apikey:password@hostname/admin/resource.json
Example URL
https://585ee8dc7245c2637b390a78243e0a0f:26ed3eb1dc574e8664480a8a0d8d1c9c@shop-hightimes-com.myshopify.com/admin/orders.json


 */


    @Override
    public void internalExecute() {

        log.debug("starting");

        ShopifyAPI api = new ShopifyAPI("585ee8dc7245c2637b390a78243e0a0f",
                "26ed3eb1dc574e8664480a8a0d8d1c9c", "shop-hightimes-com.myshopify.com","") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {
                order.bill_cc_type = "CK";
                order.is_paid = 1;

                /*order.template="simplyretail";
                for(LineItem line:(Vector<LineItem>)order.skuList){
                    if(line.getInventory().description.toUpperCase().contains("WHOLESALE")) {
                        order.template = "simplywholesale";
                    }
                }*/
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

        api.setClientInfo(463, null);
        api.setFirstOrderId(1);
        api.setOrderReferencePrefix("S");
        api.setOrderType("Shopify:HighTimes") ;
        api.setIgnoreUnrecognizedSKUs(true);

        //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid, false);
       // api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);


        //off for now due to OOS
        try {
            api.updateInventoryLevels();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

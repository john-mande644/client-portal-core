package com.owd.jobs.archives;

import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by stewartbuskirk1 on 8/16/15.
 */
public class EightOunceCoffeePorlexShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        run();

    }


/*



API Key
ec042383a45c4a5f351e2833649a045c
Password
c2202572f98df8123026dbbe10c9cee7
Shared Secret
742a529f9cb98d5ba1bbed5db1145a2a
URL Format
https://apikey:password@hostname/admin/resource.json
Example URL
https://ec042383a45c4a5f351e2833649a045c:c2202572f98df8123026dbbe10c9cee7@porlex-grinders.myshopify.com/admin/orders.json



 */


    @Override
    public void internalExecute() {

        log.debug("starting");

        ShopifyAPI api = new ShopifyAPI("ec042383a45c4a5f351e2833649a045c",
                "c2202572f98df8123026dbbe10c9cee7", "porlex-grinders.myshopify.com","") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {

            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                if(oldMethod.toUpperCase().contains("INTERNATIONAL FIRST CLASS MAIL"))
                {
                     return "USPS First-Class Mail International";
                }  else if(oldMethod.toUpperCase().contains("GLOBAL PRIORITY MAIL"))
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
                }  else if(oldMethod.toUpperCase().contains("UPS SECOND DAY AIR"))
                {
                    return "UPS 2nd Day Air";
                }  else if(oldMethod.toUpperCase().contains("USPS FIRST CLASS MAIL"))
                {
                    return "USPS First-Class Mail";
                }  else if(oldMethod.toUpperCase().contains("UPS THREE-DAY SELECT"))
                {
                    return "UPS 3 Day Select";
                }  else if(oldMethod.toUpperCase().contains("USPS PRIORITY MAIL"))
                {
                    return "USPS Priority Mail";
                }   else {

                    return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                            "TANDATA_USPS.USPS.PRIORITY_CUBIC","TANDATA_USPS.USPS.PRIORITY","CONNECTSHIP_UPS.UPS.GND","TANDATA_USPS.USPS.I_PRIORITY",
                            "TANDATA_USPS.USPS.I_FIRST","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD"));
                }

            }
        };

        api.setClientInfo(584, null);
      //  api.setFirstOrderId(1138);



     //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");

        api.setOrderType("SHOPIFY-PORLEX");
        api.setOrderReferencePrefix("P");
        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);
        api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);


        //off for now due to OOS
       /* try {
            api.updateInventoryLevels();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }*/
    }
}

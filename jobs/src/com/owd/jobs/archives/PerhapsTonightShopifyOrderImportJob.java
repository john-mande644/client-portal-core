package com.owd.jobs.archives;

import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by stewartbuskirk1 on 8/16/15.
 */
public class PerhapsTonightShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        run();

    }


    @Override
    public void internalExecute() {

        log.debug("starting PerhapsTonightShopifyOrderImportJob");

        ShopifyAPI api = new ShopifyAPI("97d2fcf21fc7e998607c2ce0571c72d0",
                "3c87fe2c44d73803ca8d50f9e19462ae", "perhapstonight.myshopify.com","") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {

            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                if(oldMethod.toUpperCase().contains("RUSH"))
                {
                     return "UPS 2nd Day Air";
                }   else {
                      return "USPS First-Class Mail";
                }

            }
        };

        api.setClientInfo(571, null);
        api.setFirstOrderId(1);


     //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


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

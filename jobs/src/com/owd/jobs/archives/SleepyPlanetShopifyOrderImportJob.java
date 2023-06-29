package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.apparelmagic.ApparelMagicApi;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by stewartbuskirk1 on 8/16/15.
 */
public class SleepyPlanetShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        run();
    }

    @Override
    public void internalExecute() {

        log.debug("starting SleepyPlanetShopifyOrderImportJob");

        ShopifyAPI api = new ShopifyAPI("afa0a776274dd33b749784acde454981",
                "c9c8962b5f9f6af3b5bc3fc0cfecc8a4", "sleepy-planet-store.myshopify.com","") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {


              //  order.template="shopify";
            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                List<String> currMethods;


                if (shipMethodMap.containsKey(oldMethod.toUpperCase())) {
                    return shipMethodMap.get(oldMethod.toUpperCase()).get(0);
                }
                currMethods  = Arrays.asList("TANDATA_USPS.USPS.PRIORITY",
                         "TANDATA_USPS.USPS.I_PRIORITY","TANDATA_USPS.USPS.SPCL");



                return RateShopper.rateShop(order, currMethods);

            }
        };

        api.setClientInfo(356, null);
        api.setFirstOrderId(1);
        api.shipMethodMap = new TreeMap<String, List<String>>();
     /*   api.shipMethodMap.put("FedEx 2nd Day Air".toUpperCase(), Arrays.asList("FedEx 2Day"));
        api.shipMethodMap.put("FedEx Standard Overnight".toUpperCase(), Arrays.asList("FedEx Standard Overnight"));
        api.shipMethodMap.put("Standard Shipping".toUpperCase(),Arrays.asList("USPS First-Class Mail","USPS Priority Mail","FedEx SmartPost Parcel Select","FedEx SmartPost Standard Mail","FedEx Home Delivery","FedEx Ground","UPS Ground"));
        api.shipMethodMap.put("USPS First-Class Package International Service".toUpperCase(),Arrays.asList("USPS First-Class Mail International"));
        api.shipMethodMap.put("USPS Priority Mail International".toUpperCase(),Arrays.asList("USPS Priority Mail International"));

        api.shipMethodMap.put("Expedited Int'l (4-8 days, delivery confirmation)".toUpperCase(), Arrays.asList("USPS Priority Mail Express International"));
        api.shipMethodMap.put("Priority Int'l (6-12 days, delivery confirmation)".toUpperCase(), Arrays.asList("USPS Priority Mail International"));
        api.shipMethodMap.put("USPS Priority".toUpperCase(), Arrays.asList("USPS Priority Mail"));

        api.shipMethodMap.put("FedEx International Economy".toUpperCase(), Arrays.asList("FedEx International Economy"));

*/

        api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);
        api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);


        try {
            api.updateInventoryLevels();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

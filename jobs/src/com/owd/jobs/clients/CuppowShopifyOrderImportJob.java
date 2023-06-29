package com.owd.jobs.clients;

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
public class CuppowShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
       // run();
        ShopifyAPI api = new ShopifyAPI("3e6f411ebf5c38fa29fd6a0b2fcc35d4",
                "adc8a78295430bfa14adcd4bb0b5cb87", "cuppow.myshopify.com","11202052");
        api.printListOfLocations();

    }


/*


API Key
86ff279464b4a2f1cb8416d62b1f36ae
Password
2d11f30947a72e976c63144161bb5bf4
Shared Secret
ebb2f20399d4a1b62ada7b814bf0cb52
URL Format
https://apikey:password@hostname/admin/resource.json
Example URL
https://86ff279464b4a2f1cb8416d62b1f36ae:2d11f30947a72e976c63144161bb5bf4@acme-co-usa.myshopify.com/admin/orders.json


 */


    @Override
    public void internalExecute() {

        log.debug("starting");

        ShopifyAPI api = new ShopifyAPI("3e6f411ebf5c38fa29fd6a0b2fcc35d4",
                "adc8a78295430bfa14adcd4bb0b5cb87", "cuppow.myshopify.com","11202052") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {
                  order.template="cuppowretail";
                for(LineItem line:(Vector<LineItem>)order.skuList){
                    if(line.getInventory().description.toUpperCase().contains("WHOLESALE")) {
                        order.template = "cuppowwholesale";
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

        api.setClientInfo(577, null);
        api.setFirstOrderId(1);



     //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);
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

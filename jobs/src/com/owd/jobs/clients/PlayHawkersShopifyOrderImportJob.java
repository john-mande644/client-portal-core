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
public class PlayHawkersShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        ShopifyAPI api = new ShopifyAPI("0c10b577b24fba4649ccbe87fd15c7a3",
                "da158862b24bbac668d256e25e8f2e7e", "hawkers-usa.myshopify.com","18535374");
        api.printListOfLocations();
       /* ShopifyAPI api = new ShopifyAPI("0c10b577b24fba4649ccbe87fd15c7a3",
                "da158862b24bbac668d256e25e8f2e7e", "hawkers-usa.myshopify.com");
        api.printListOfShipmethods();*/


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

        ShopifyAPI api = new ShopifyAPI("0c10b577b24fba4649ccbe87fd15c7a3",
                "da158862b24bbac668d256e25e8f2e7e", "hawkers-usa.myshopify.com","18535374") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {

            }
            @Override
            public boolean checkFulfillmentStatus(String status){

                return (status.equals("fulfilled"));
            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                if(oldMethod.toUpperCase().contains("CA"))
                {
                     return "USPS First-Class Mail International";
                }  else if(oldMethod.toUpperCase().contains("US"))
                {
                    return "USPS First-Class Mail";
                }   else {

                    return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                            "TANDATA_USPS.USPS.PRIORITY_CUBIC","TANDATA_USPS.USPS.PRIORITY","TANDATA_USPS.USPS.I_FIRST"));
                }

            }
        };
        api.setOrderReferencePrefix("#HAWKERSUSA");
        api.setClientInfo(579, null);
        api.setFirstOrderId(13987);

        api.getTagToGroupNameMap().put("vipsaldum","VIP");

     //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);
        //api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);


        //off for now due to OOS
        try {
        //    api.updateInventoryLevels();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

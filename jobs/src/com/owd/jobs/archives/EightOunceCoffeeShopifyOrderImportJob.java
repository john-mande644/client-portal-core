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
public class EightOunceCoffeeShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        run();

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

        ShopifyAPI api = new ShopifyAPI("86ff279464b4a2f1cb8416d62b1f36ae",
                "2d11f30947a72e976c63144161bb5bf4", "acme-co-usa.myshopify.com","") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {

            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {
                if(oldMethod.equals("UPS Next Day Air Saver")){
                    return oldMethod;
                }
                if(oldMethod.contains("UPS Next Day Air")){
                    if(oldMethod.contains("Early")){
                        return "UPS Next Day Air A.M.";
                    }
                    return "UPS Next Day Air";
                }
                if(oldMethod.equals("UPS 2nd Day Air")){
                    return oldMethod;
                }
                if(oldMethod.contains("UPS 2nd Day Air")){
                    return "UPS 2nd Day Air A.M.";
                }
                if(oldMethod.contains("UPS Express")){
                    if(oldMethod.contains("Early")){
                        return "UPS Worldwide Express Plus";
                    }
                    if(oldMethod.contains("Saver")){
                       return "UPS Worldwide Express Saver";
                    }
                    return "UPS Worldwide Express";
                }
                if(oldMethod.contains("UPS Expedited")){
                    return "UPS Worldwide Expedited";
                }
                if(oldMethod.contains("UPS Standard")){
                    if(order.getShippingAddress().isUS()){
                        return "UPS Ground";
                    }
                    return "UPS Standard Canada";
                }

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
                }  else if(oldMethod.toUpperCase().contains("USPS FIRST CLASS MAIL"))
                {
                    return "USPS First-Class Mail";
                } else if(oldMethod.toUpperCase().contains("UPS SECOND DAY AIR"))
                {
                    return "UPS 2nd Day Air";
                  } else if(oldMethod.toUpperCase().contains("UPS THREE-DAY SELECT")||oldMethod.toUpperCase().contains("UPS 3 DAY SELECT"))
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
        api.setFirstOrderId(1138);



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

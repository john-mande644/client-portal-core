package com.owd.jobs.archives;

import com.owd.core.business.order.Order;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by dnickels on 12/6/16.
 */
public class GymnasticBodiesShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        ShopifyAPI api = new ShopifyAPI("fbd7e9550e00e135586ab29478bb3a7f",
                "16c3d259e4ee888038cdd7374294ee1a", "gymnasticbodies.myshopify.com","");
        api.printListOfLocations();

    }


/*


Here are the credentials for the new private app.
>
> API key
>
> fbd7e9550e00e135586ab29478bb3a7f
>
> Password
>
> 16c3d259e4ee888038cdd7374294ee1a
>
> Shared secret
>
> 2d1d8d969485dec361820c92ea91acb1
>
> URL format
>
> https://apikey:password@hostname/admin/resource.json
>
> Example URL
>
> https://fbd7e9550e00e135586ab29478bb3a7f:16c3d259e4ee888038cdd7374294ee1a@gymnasticbodies.myshopify.com/admin/orders.json

 */


    @Override
    public void internalExecute() {

        log.debug("starting");

        ShopifyAPI api = new ShopifyAPI("fbd7e9550e00e135586ab29478bb3a7f",
                "16c3d259e4ee888038cdd7374294ee1a", "gymnasticbodies.myshopify.com","") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {



            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                if(oldMethod.toUpperCase().contains("FEDEX STANDARD OVERNIGHT"))
                {
                    return "FedEx Standard Overnight";
                }  else if(oldMethod.toUpperCase().contains("FEDEX 2ND DAY AIR")||oldMethod.toUpperCase().contains("FEDEX 2"))
                {
                    return "FedEx 2Day";

                }  else if(oldMethod.toUpperCase().contains("FEDEX PRIORITY OVERNIGHT"))
                {
                    return "FedEx Priority Overnight";

                }else if(oldMethod.toUpperCase().contains("FEDEX GROUND"))
                {
                    return "FedEx Ground";

                }else if(oldMethod.toUpperCase().contains("FIRST-CLASS PACKAGE INTERNATIONAL"))
                {
                    return "USPS First-Class Mail International";

                } else if(oldMethod.toUpperCase().contains("PRIORITY MAIL INTERNATIONAL"))
                {
                    return "USPS Priority Mail International";

                }  else if(oldMethod.toUpperCase().contains("FEDEX INTERNATIONAL ECONOMY"))
                {
                    return "FedEx International Economy";
                    //Express Shipping
                }  else if(oldMethod.toUpperCase().contains("EXPRESS SHIPPING"))
                {
                    return RateShopper.rateShop(order, Arrays.asList(
                            "TANDATA_USPS.USPS.I_PRIORITY","CONNECTSHIP_DHL.DHL.WPX"));

                }  else if(oldMethod.toUpperCase().contains("SIGNATURE CONFIRMATION")){
                    //Standard w/ Signature Confirmation
                    order.setSignatureRequired(true);
                    return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                            "TANDATA_USPS.USPS.PRIORITY","CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD",
                            "TANDATA_USPS.USPS.I_PRIORITY","TANDATA_FEDEXFSMS.FEDEX.SP_PS","TANDATA_FEDEXFSMS.FEDEX.SP_STD"),true);
                }else {

                    return RateShopper.rateShop(order, Arrays.asList(
                            "TANDATA_USPS.USPS.PRIORITY",
                            "TANDATA_USPS.USPS.I_PRIORITY"));
                }

            }
        };

        api.setClientInfo(411, null);
        api.setFirstOrderId(1);
        api.setOrderReferencePrefix("S");
        api.setOrderType("Shopify") ;
        api.setIgnoreUnrecognizedSKUs(true);

        //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid, false);
        api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);


        //off for now due to OOS
        try {
        //    api.updateInventoryLevels();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

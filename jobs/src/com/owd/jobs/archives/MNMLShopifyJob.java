package com.owd.jobs.archives;

import com.owd.LogableException;
import com.owd.core.business.order.Order;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.amazon.AmazonAPI;
import com.owd.jobs.jobobjects.amazon.AmazonConfig;
import com.owd.jobs.jobobjects.amazon.AmazonFeeds;
import com.owd.jobs.jobobjects.apparelmagic.ApparelMagicApi;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Created by stewartbuskirk1 on 8/16/15.
 */
public class MNMLShopifyJob extends OWDStatefulJob {
    protected final static Logger log = LogManager.getLogger();


    public static void main(String[] args) {

        run();
/*
        ShopifyAPI api = new ShopifyAPI("809b621e9c2e2c0fd2d3845e1be8ed36",
                "f4d70bdec198d2ddbbf3a138e7767538", "mnml-4.myshopify.com","693108762");
        api.printListOfLocations();*/

     /*   ShopifyAPI api = new ShopifyAPI("809b621e9c2e2c0fd2d3845e1be8ed36",
                "f4d70bdec198d2ddbbf3a138e7767538", "mnml-4.myshopify.com");
        api.printListOfShipmethods();*/


        //  println   api.getHashedRequest(null)


       // String[] filter = {"M","L"};
       // amApi.syncAmSkusToOwd(true, filter,true);
    }

    @Override
    public void internalExecute() {

        log.debug("starting MNMLShopifyJob");

        ShopifyAPI api = new ShopifyAPI("809b621e9c2e2c0fd2d3845e1be8ed36",
                "f4d70bdec198d2ddbbf3a138e7767538", "mnml-4.myshopify.com","693108762") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order) throws Exception {

                // order.addInsertItemIfAvailable("DSMU-0914-X714-BLK-0",1);
                // order.addInsertItemIfAvailable("DSMU-0914-X714-WHT-0",1);
               /* if(order.getShipMethodName().equals("USPS Priority Mail")){
                    order.addTag("com.owd.shipping.AllInclusive","1");
                }*/
                order.template = "mnmlshopify";
                order.companyOverride = "mnml";
                order.backorder_rule="IGNOREBACKORDER";
              /*  if(order.getShipMethodName().contains("DHL Express")){
                    order.setThirdPartyBilling("848437059");
                    order.setThirdPartyBillingContact("Third Estate LLC","Third Estate LLC","112 W 9th St Suite 726","","Los Angeles","CA","90015","6058457172");
                }*/

            }


            @Override
            public boolean isShippedSeparately(String sku) {
                if (sku.startsWith("*")) {
                    return true;
                }

                return false;
            }

            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                //force first class for karmaloop orders
                if(oldMethod.toUpperCase().contains("EXPRESS SHIPPING")&&!oldMethod.toUpperCase().contains("DHL EXPRESS SHIPPING")){
                    return "Ground";
                }

                if (oldMethod.toUpperCase().contains("BOO.ST")) {
                    return "Ground";
                }
                if (oldMethod.toUpperCase().contains("DHL EXPRESS")) {
                    return "DHL Express Worldwide nondoc";
                }

                if (oldMethod.toUpperCase().contains("FEDEX STANDARD OVERNIGHT")) {
                    return "Overnight";
                } else if (oldMethod.toUpperCase().contains("FEDEX 2ND DAY AIR") || oldMethod.toUpperCase().contains("FEDEX 2")) {
                    return "2 Day";

                } else if (oldMethod.toUpperCase().contains("FEDEX PRIORITY OVERNIGHT")) {
                    return "Overnight";

                } else if (oldMethod.toUpperCase().contains("FEDEX GROUND")) {
                    return "Ground";

                } else if (oldMethod.toUpperCase().contains("FIRST-CLASS PACKAGE INTERNATIONAL")) {
                    //return "USPS First-Class Mail International";
                    return "DHL Express Worldwide nondoc";

                } else if (oldMethod.toUpperCase().contains("PRIORITY MAIL INTERNATIONAL")) {
                    //return "USPS Priority Mail International";
                    return "DHL Express Worldwide nondoc";

                } else if (oldMethod.toUpperCase().contains("FEDEX INTERNATIONAL ECONOMY")) {
                 //   return "FedEx International Economy";
                    return "DHL Express Worldwide nondoc";
                    //Express Shipping
                } else if (oldMethod.toUpperCase().contains("DHL EXPRESS SHIPPING")) {
                    return "DHL Express Worldwide nondoc";

                } else if (oldMethod.toUpperCase().contains("SIGNATURE CONFIRMATION")) {
                    //Standard w/ Signature Confirmation
                   // order.setSignatureRequired(true);
                  /*  return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                            "TANDATA_USPS.USPS.PRIORITY", "CONNECTSHIP_UPS.UPS.GND", "TANDATA_FEDEXFSMS.FEDEX.GND", "TANDATA_FEDEXFSMS.FEDEX.FHD",
                            "TANDATA_USPS.USPS.I_PRIORITY", "TANDATA_FEDEXFSMS.FEDEX.SP_PS", "TANDATA_FEDEXFSMS.FEDEX.SP_STD"), true);*/
                    if(order.getShippingAddress().isUS()){
                        return "Ground";
                    }else{
                        return "DHL Express Worldwide nondoc";
                    }
                } else {

                  /*  return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                            "TANDATA_USPS.USPS.PRIORITY", "CONNECTSHIP_UPS.UPS.GND", "TANDATA_FEDEXFSMS.FEDEX.GND", "TANDATA_FEDEXFSMS.FEDEX.FHD",
                            "TANDATA_USPS.USPS.I_PRIORITY", "TANDATA_USPS.USPS.I_FIRST", "CONNECTSHIP_DHL.DHL.WPX"));*/
                    if(order.getShippingAddress().isUS()){
                        return "Ground";
                    }else{
                        return "DHL Express Worldwide nondoc";
                    }

                }

            }
        };

        api.setClientInfo(607, null);
        api.setFirstOrderId(270718);

        api.setOrderType("MNML Cart");
        api.setOrderReferencePrefix("M");
        api.setOtherFulfillmentSku("SEPARATE");
        api.setIgnoreUnrecognizedSKUs(true);
        api.setUseOrderName(true);
        // api.setFulfillmentServiceCode("stewart");
        // ApparelMagicApi amApi = new ApparelMagicApi(551,"https://dope.app.apparelmagic.com","42a98c9cf1dffbd86f907689dd768b1f92208d1c","81fcb72f5d182afbd70cbf4438e22cb62ce3dbac");
       // ApparelMagicApi amApi = new ApparelMagicApi(607, "https://mnml.app.apparelmagic.com", "4fd33e4ed613620701716fabd3b158e03490a106", "e2862efb5efe8eaa9161f4ee189c414bd478f28d");


        //  println   api.getHashedRequest(null)
       /* amApi.setShopify(api);

        String[] filter = {"M","L"};
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        try {
        if(hour == 8||hour == 18){

            amApi.syncAmSkusToOwd(true, filter,false);


        }else {
            log.debug("Not updating existing sku's");
            amApi.syncAmSkusToOwd(false, filter,false);

        }*/



        try {
            api.importCurrentOrders(ShopifyAPI.kStatusTypePaid, false);
            api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund, false);


        } catch (Exception ex) {
            ex.printStackTrace();
            Exception exl = new LogableException(ex, "MNML:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + 607, "MNML Hourly Sync", LogableException.errorTypes.ORDER_IMPORT);

        }

        //     Map<String, Long> shopifyOrderedItemsMap = api.getImportedItemMap();


        try {
            api.updateInventoryLevels();

        } catch (Exception ex) {
            ex.printStackTrace();
            Exception exl = new LogableException(ex, "ErrorMNML Stock Sync:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "" + 607, "MNML Hourly Sync", LogableException.errorTypes.ORDER_IMPORT);

        }


    }


}

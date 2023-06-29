package com.owd.jobs.clients;

import com.owd.core.business.order.Order;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dnickels on 12/6/16.
 */
public class ElevenHonoreShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
 /*       ShopifyAPI api = new ShopifyAPI("70ceff34de1447fa3e6d3ba2c40b2c3c",
                "89f51d678f851fa79975b91f1048880a", "the-new-parallel.myshopify.com","22442824");
      //  api.printListOfLocations();
        api.setSendLocationWithInventory(true);

        try {
            api.setForceAllSkuSync(true);
            api.updateInventoryLevels();

          //  api.updateInventoryLevelForItemAndLocation("12900649238591", 0);

        }catch (Exception e){
            e.printStackTrace();
        }*/

       run();

    }


/*


API key
70ceff34de1447fa3e6d3ba2c40b2c3c
Password
89f51d678f851fa79975b91f1048880a
Shared secret
2cd410ea1b8ae65b3a11b4d96e5d0f0b
URL format
https://apikey:password@hostname/admin/resource.json
Example URL
https://70ceff34de1447fa3e6d3ba2c40b2c3c:89f51d678f851fa79975b91f1048880a@the-new-parallel.myshopify.com/admin/orders.json

 */


    @Override
    public void internalExecute() {

        log.debug("starting");

        ShopifyAPI api = new ShopifyAPI("70ceff34de1447fa3e6d3ba2c40b2c3c",
                "89f51d678f851fa79975b91f1048880a", "the-new-parallel.myshopify.com","22442824") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
            {
                    order.addInsertItemIfAvailable("GIP-ENVL-8.5X5.5-DRGRY",1);
                    order.addInsertItemIfAvailable("GIP-ENCLNOTE-8.5 x 3.67-LVD",1);
                  //  order.addInsertItemIfAvailable("GIP-STCK-11x8.5-LVD",1);
                    order.backorder_rule = OrderXMLDoc.kPartialShip;
                order.recalculateBalance();
                if(order.total_product_cost>800f){
                    order.setSignatureRequired(true);
                }

            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {


                if(!order.getShippingAddress().isUS()){
                    return ("DHL Express Worldwide nondoc");

                }
                if(oldMethod.toUpperCase().contains("GROUND SHIPPING") || oldMethod.toUpperCase().contains("GROUND")){

                    if(order.getShippingAddress().isPOAPONew()){

                        return "USPS Priority Mail";
                    }else{
                        return "FedEx Ground";
                    }
                }else if(oldMethod.toUpperCase().contains("NEXT DAY")){


                    if(oldMethod.equals("UPS-Next-Day-Air-Early-A.M.")){
                       return "FedEx Standard Overnight";

                    }
                    if(oldMethod.equals("UPS-Next-Day-Air-Saver")){
                        return "FedEx Standard Overnight";
                    }
                    return "FedEx Standard Overnight";


                }else if(oldMethod.toUpperCase().contains("SECOND")){

                    if(oldMethod.equals("UPS-Second-Day-Air-A.M.")){
                        return "FedEx 2Day";
                    }
                    return "FedEx 2Day";

                }else if(oldMethod.toUpperCase().contains("THREE")){

                    return "UPS 3 Day Select";




                }else if(oldMethod.toUpperCase().contains("WORLDWIDE")){


                    if(oldMethod.equals("UPS-Worldwide-Express-Plus")){
                        return "UPS Worldwide Express Plus";
                    }



                    return "UPS Worldwide Expedited";


                }else if(oldMethod.equalsIgnoreCase("2 Day Shipping")){

                    return "FedEx 2Day";




                }else {

                    return RateShopper.rateShop(order, Arrays.asList("FDX.GND","CONNECTSHIP_DHL.DHL.WPX"));
                }













               /* if(oldMethod.toUpperCase().contains("FEDEX STANDARD OVERNIGHT"))
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

                    return RateShopper.rateShop(order, Arrays.asList("TANDATA_USPS.USPS.FIRST",
                            "TANDATA_USPS.USPS.PRIORITY","CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD",
                            "TANDATA_USPS.USPS.I_PRIORITY","TANDATA_USPS.USPS.I_FIRST","CONNECTSHIP_DHL.DHL.WPX"));
                }*/

            }
        };

        api.setClientInfo(610, null);
        api.setFirstOrderId(2878);
        api.setOrderReferencePrefix("S");
        api.setOrderType("Shopify") ;
       // api.setIgnoreUnrecognizedSKUs(true);


        //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid, false);
        api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);


        //off for now due to OOS
        try {
            //select all sku's with presale in keyword and add them to presale list
            String sql = "select inventory_num from owd_inventory where client_fkey = 610 and keyword like '%preorder%'";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();

            for(Object data : l){
                api.getPreSaleSkus().add(data.toString());
            }
            System.out.println(api.getPreSaleSkus().size());

            api.setSendLocationWithInventory(true);
            api.updateInventoryLevels();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

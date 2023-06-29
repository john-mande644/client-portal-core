package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/1/12
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class StorqWarehouseImportJob extends OWDStatefulJob {

    private final static Logger log =  LogManager.getLogger();




    public static void main(String[] args) {

        ShopifyAPI api = new ShopifyAPI("c72a6bb5ccfed17fa6626419aa149f49 ",
                "63112309e73ee8dfbb68677d4344f0d9", "storq-warehouse.myshopify.com","19927372");
//        api.printListOfLocations();

        // ========= One time inventory sync (Sean 01/07/2020 case 747760)=========
        /*api.setClientInfo(531, null);
        try{
            api.updateInventoryLevels();  // push to run once
        }catch (Exception e){
        }*/
        //============================================

    }



    public void internalExecute() {

        try {
            log.debug("starting StorqWarehouseImportJob");
            ShopifyAPI api = new ShopifyAPI("c72a6bb5ccfed17fa6626419aa149f49 ",
                    "63112309e73ee8dfbb68677d4344f0d9", "storq-warehouse.myshopify.com","19927372") {

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
                {

               //     order.addInsertItemIfAvailable("XXXTY",1);

                //    long  xxxtpUnits1 = order.getRequestedUnitsofSkusInList(new ArrayList<String>(
                //            Arrays.asList("000LW","000PW","000PD","000PB","000PR","000SO","000SU","000DE")));
                  /*   if(xxxtpUnits1>4 && xxxtpUnits1<11)
                     {
                         order.addInsertItemIfAvailable("XXXTP",2);
                     }
                    if(xxxtpUnits1>0 && xxxtpUnits1<5)
                    {
                        order.addInsertItemIfAvailable("XXXTP",1);
                    }
*/
               //     long  xxxtpUnits2 = order.getRequestedUnitsofSkusInList(new ArrayList<String>(
                  //          Arrays.asList("00PBA","001BA","002BA","003BA","004BA","000BO")));
                 /*   if(xxxtpUnits2>0 )
                    {
                        order.addInsertItemIfAvailable("XXXTP",2);
                    }
*/
                   // order.addInsertItemIfAvailable("XXXBE",1);
                    order.prt_pick_reqd=1 ;  // Searchs packing slip from warehouse (Sean)
                    order.prt_pack_reqd=0 ;
                    order.order_type="SHOPIFYWAREHOUSE";
                    order.group_name="SHOPIFYWAREHOUSE";// (Sean)
                }


                @Override
                public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                    List<String> currMethods;


                        if (shipMethodMap.containsKey(oldMethod.toUpperCase())) {
                            return shipMethodMap.get(oldMethod.toUpperCase()).get(0);
                        }
                      currMethods  = Arrays.asList( "TANDATA_USPS.USPS.PRIORITY",
                            "CONNECTSHIP_UPS.UPS.GND", "TANDATA_USPS.USPS.I_PRIORITY");

                     if(order.containsAnySKU(new ArrayList<String>(
                             Arrays.asList("000PD", "000PB", "000PR"))))
                     {
                         return "UPS Ground";
                     }




                    return RateShopper.rateShop(order, currMethods);

                }
            };

            api.setClientInfo(531, null);
            api.setOrderReferencePrefix("SW-");
            api.setFirstOrderId(0);
            api.shipMethodMap = new TreeMap<String, List<String>>();
            api.shipMethodMap.put("Free Shipping! (Approx. 3 - 6 Business Days)".toUpperCase(), Arrays.asList("USPS Priority Mail", "UPS Ground"));
            api.shipMethodMap.put("Standard Ground (Approx. 3 - 6 Business Days)".toUpperCase(), Arrays.asList("USPS Priority Mail", "UPS Ground"));
            api.shipMethodMap.put("International Shipping (Approx. 6-13 Business Days)".toUpperCase(), Arrays.asList("USPS Priority Mail International"));
            api.shipMethodMap.put("Expedited Shipping (UPS 2-Day Delivery For Orders Placed Before 1pm EST)".toUpperCase(),Arrays.asList("UPS 2nd Day Air"));

            api.setOtherFulfillmentSku("SEPARATE");
           // api.setFulfillmentServiceCode("stewart");
            api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);
            api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);

         //   api.updateInventoryLevels();


        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }

    }




}

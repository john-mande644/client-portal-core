package com.owd.jobs.clients;

import com.owd.core.xml.OrderXMLDoc;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import ipworksssh.IPWorksSSHException;
import ipworksssh.IPWorksSSHV9LicenseInstaller;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/1/12
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class StorqImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();




    public static void main(String[] args) {

      /*  ShopifyAPI api = new ShopifyAPI("9dc3b9c6384438674ba4093360f47632",
                "24038afa1d01578629bb3e0c4690474b", "storq.myshopify.com","35007241");
        api.printListOfLocations();*/
     /*   ShopifyAPI api = new ShopifyAPI("9dc3b9c6384438674ba4093360f47632",
                "24038afa1d01578629bb3e0c4690474b", "storq.myshopify.com");
        api.setClientInfo(531, null);
        try {

        }catch (Exception e){
            e.printStackTrace();
        }*/
       // api.reportShipment(10620002,"CB109560570US",true);
        run();
    }



    public void internalExecute() {

        try {
            List<String> preSaleSkus = new ArrayList<>();
          //  preSaleSkus.add("001SPB");
           // preSaleSkus.add("002SPB");
           // preSaleSkus.add("003SPB");
           // preSaleSkus.add("004SPB");
            /*preSaleSkus.add("023CM");
            preSaleSkus.add("045CM");
            */
          //  log.debug("starting SleepyPlanetShopifyOrderImportJob");
            ShopifyAPI api = new ShopifyAPI("9dc3b9c6384438674ba4093360f47632",
                    "24038afa1d01578629bb3e0c4690474b", "storq.myshopify.com","35007241") {

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
                {

                            if(order.getShippingAddress().country.equals("USA")){
                                order.backorder_rule = OrderXMLDoc.kPartialShip;
                            }else{
                                order.backorder_rule = OrderXMLDoc.kBackOrderAll;

                        }
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



                    /* ===========================================
                     * Date : 11-28-2018
                     * Section editor: Sean Chen
                     * Description : turn XXXGC to XXXEPMS, add shipping method to USPS first class mail
                     */
                    List<String> giftcards = new ArrayList<>();

                    giftcards.add("050GC");
                    giftcards.add("100GC");
                    giftcards.add("150GC");

                    if (order.containsAnySKU(giftcards)){
                        boolean onlyGiftCard = true;
                        for (int i = 0; i < order.skuList.size(); i++) {
                            if (!giftcards.contains(((LineItem) order.skuList.elementAt(i)).client_part_no)) {
                                onlyGiftCard = false;

                            }
                        }
                        if(onlyGiftCard){
                            order.addInsertItemIfAvailable("XXXEPMS", 1 );
                            order.setShippingMethodName("USPS First-Class Mail");
                        }

                    }
                    // =============================================


                    order.addInsertItemIfAvailable("XXXPS",1);
                    order.setTemplate("storqpack");
                }



                @Override
                public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                    List<String> currMethods;


                        if (shipMethodMap.containsKey(oldMethod.toUpperCase())) {
                            return shipMethodMap.get(oldMethod.toUpperCase()).get(0);
                        }

                    if(order.getShippingAddress().isUS()){
                        return "USPS Priority Mail";
                    }else{
                        return "USPS Priority Mail International";
                    }




                    /* if(order.containsAnySKU(new ArrayList<String>(
                             Arrays.asList("000PD", "000PB", "000PR"))))
                     {
                         return "UPS Ground";
                     }



                    return RateShopper.rateShop(order, currMethods);*/

                }
            };
            api.setPreSaleSkus(preSaleSkus);
            api.setClientInfo(531, null);
            api.setFirstOrderId(0);
            api.shipMethodMap = new TreeMap<String, List<String>>();



            api.shipMethodMap.put("Standard (Approx. 3 - 6 Business Days)".toUpperCase(), Arrays.asList("USPS Priority Mail"));
            api.shipMethodMap.put("International Shipping (Approx. 6-13 Business Days)".toUpperCase(), Arrays.asList("USPS Priority Mail International"));
            api.shipMethodMap.put("Expedited Shipping (UPS 2-Day Delivery For Orders Placed Before 1pm EST Mon-Fri)".toUpperCase(),Arrays.asList("UPS 2nd Day Air"));
            api.shipMethodMap.put("Expedited Shipping (UPS 2-Day Delivery For Orders Placed Before 1pm EST)".toUpperCase(),Arrays.asList("UPS 2nd Day Air"));
            api.shipMethodMap.put("Expedited Shipping (UPS 2nd Day Air For Orders Placed Before 1pm EST. No Weekend Delivery.)".toUpperCase(),Arrays.asList("UPS 2nd Day Air"));




            api.setOtherFulfillmentSku("SEPARATE");
           // api.setFulfillmentServiceCode("stewart");
            api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);
            api.importCurrentOrders(ShopifyAPI.kStatusTypePartialRefund,false);

            api.updateInventoryLevels();


        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {

            HibernateSession.closeSession();
        }

    }




}

package com.owd.jobs.clients;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Order;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.owd.hibernate.generated.WInvRequest;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
//import com.sun.xml.internal.ws.handler.HandlerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stewartbuskirk1 on 8/16/15.
 */
public class GensavisNovaShopifyOrderImportJob extends OWDStatefulJob  {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {
       // run();

        ShopifyAPI api = new ShopifyAPI("f5d959a3d205a8d4bf8102c72b38b0fe",
                "678f5f957a643e2acabf4f5f073ceb05", "novaferrumcom.myshopify.com","20855364");
        api.printListOfLocations();

       /* try{
            ShopifyAPI api = new ShopifyAPI("f5d959a3d205a8d4bf8102c72b38b0fe",
                    "678f5f957a643e2acabf4f5f073ceb05", "novaferrumcom.myshopify.com");
            api.printListOfShipmethods();
        }catch(Exception e){
            e.printStackTrace();
        }*/

    }

    /*


/*


API key
f5d959a3d205a8d4bf8102c72b38b0fe
Password
678f5f957a643e2acabf4f5f073ceb05
Shared secret
5481b04f84723b8f1880fc16970c66c5
URL format
https://apikey:password@hostname/admin/resource.json
Example URL
https://f5d959a3d205a8d4bf8102c72b38b0fe:678f5f957a643e2acabf4f5f073ceb05@novaferrumcom.myshopify.com/admin/orders.json



 */


    @Override
    public void internalExecute() {

        log.debug("starting");

        ShopifyAPI api = new ShopifyAPI("f5d959a3d205a8d4bf8102c72b38b0fe",
                "678f5f957a643e2acabf4f5f073ceb05", "novaferrumcom.myshopify.com","20855364") {

            @Override
            public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception {
//                order.template="simplyretail";

                // ==============================================================
                // begin of rule: only give a free item when never receive before.

                String client_id = order.clientID;
                String fullName = order.getShippingContact().getName();
                String addr1 = order.getShippingAddress().getAddress_one();
                String city = order.getShippingAddress().getCity();
                boolean purchasedBefore = isPurchasedBefore(client_id, fullName, addr1, city);

                // case 947601
                order.addInsertItemIfAvailable("NovaAdult Chewable Samples",1);

                order.addInsertItemIfAvailable("Package Insert",1);

                // Sean 926693 free masks
                order.addInsertItemIfAvailable("FaceMask-Awesome 32oz Liquid Daily",1);

                // Sean Chen 06/04/2019 case 705550 commented out
                /*ArrayList<String> skus = new ArrayList<>();
                skus.add("NovaFerrum Liquid Iron (Chocolate) Pediatric Drops SINGLE");
                skus.add("V1-E5VV-2C9V");

                if(order.containsAnySKU(skus)){
                    OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku("596", "NovaMV SAMPLES");
                    long oh = item.getOwdInventoryOh().getQtyOnHand();
                    if(oh > 0) {
                        order.addLineItem("NovaMV SAMPLES", 1, 0, 0, "NovaMV Sample", "", "");
                    }
                }*/

                // Sean Chen 09/12/2019 case 715257
                // Free sample if qualified SKUs get purchased. Expired when stock out
                ArrayList<String> skus_3 = new ArrayList<>();
                skus_3.add("NovaFerrum Multivitamin With Iron (Plant-Based D-3) Pediatric Drops SINGLE");
                skus_3.add("NovaFerrum Liquid Iron (Chocolate) Pediatric Drops SINGLE");
                skus_3.add("V1-E5VV-2C9V");
                skus_3.add("HJ-E0NE-GLMP");
                skus_3.add("AX-11MI-Z70P");
                if(order.containsAnySKU(skus_3)){
                    OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku("596", "NovaMVI Bubble Gum Samples");
                    long oh = item.getOwdInventoryOh().getQtyOnHand();
                    if(oh > 0) {
                        order.addLineItem("NovaMVI Bubble Gum Samples", 1, 0, 0, "NovaMV Sample", "", "");
                    }
                }

                // Sean Chen 06/04/2019 case 705550
                // Free sample to all new orders of any purchase of the 4 skus
                ArrayList<String> skus_2 = new ArrayList<>();
                skus_2.add("HJ-E0NE-GLMP");
                skus_2.add("AX-11MI-Z70P");
                skus_2.add("V1-E5VV-2C9V");
                skus_2.add("NovaFerrum Liquid Iron (Chocolate) Pediatric Drops SINGLE");
                if(order.containsAnySKU(skus_2)){
                    OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku("596", "NovaMV SAMPLES");
                    long oh = item.getOwdInventoryOh().getQtyOnHand();
                    if(oh > 0) {
                        order.addLineItem("NovaMV SAMPLES", 1, 0, 0, "NovaMV Sample", "", "");
                    }
                }

                // Sean Chen 01/29/2019 case 649567
                if(!purchasedBefore){

                    order.addInsertItemIfAvailable("Tote Bag",1);

                    /*ArrayList<String> skus_401948 = new ArrayList<>();
                    skus_401948.add("HJ-E0NE-GLMP");
                    skus_401948.add("AX-11MI-Z70P");
                    skus_401948.add("V1-E5VV-2C9V");
                    skus_401948.add("NovaFerrum Liquid Iron (Chocolate) Pediatric Drops SINGLE");

                    if(order.containsAnySKU(skus_401948)){
                        OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku("596", "Bibs");
                        long onHand = item.getOwdInventoryOh().getQtyOnHand();
                        if(onHand > 0) {
                            order.addLineItem("Bibs", 1, 0, 0, "Bibs", "", "");
                        }
                    }*/
                }

                //==============================================================
                // Sean Chen on 10/23/2019 for ticket 729791
                // Free item for any purchase. No matter new or old customers
                ArrayList<String> cadidateListFor_636401 = new ArrayList<>();
                cadidateListFor_636401.add("V1-E5VV-2C9V");
                getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_636401, "NovaIron Ped Chocolate Samples");



                //==============================================================
                // Sean Chen on 10/23/2019 for ticket 720624
                // Free items give away only once of each. Available for both new and old customers

                String gift_632701 = "Tag-Wow! Adult 125";
                boolean isReceived_632701 = isReceivedAnItenBefore(client_id, fullName, addr1, city, gift_632701);
                if (!isReceived_632701){
                    ArrayList<String> cadidateListFor_632701 = new ArrayList<>();
                    cadidateListFor_632701.add("AX-11MI-Z70P");
                    getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_632701, gift_632701);
                }

                String gift_632705 = "Tag-MMM! MV";
                boolean isReceived_632705 = isReceivedAnItenBefore(client_id, fullName, addr1, city, gift_632705);
                if (!isReceived_632705){
                    ArrayList<String> cadidateListFor_632705 = new ArrayList<>();
                    cadidateListFor_632705.add("AX-11MI-Z70P");
                    getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_632705, gift_632705);
                }

                String gift_632704 = "Tag-Awesome!";
                boolean isReceived_632704 = isReceivedAnItenBefore(client_id, fullName, addr1, city, gift_632704);
                if (!isReceived_632704){
                    ArrayList<String> cadidateListFor_632704 = new ArrayList<>();
                    cadidateListFor_632704.add("V1-E5VV-2C9V");
                    cadidateListFor_632704.add("NovaFerrum Liquid Iron (Chocolate) Pediatric Drops SINGLE");
                    getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_632704, gift_632704);
                }

                String gift_632703 = "Tag-Delish! MVI Plant";
                boolean isReceived_632703 = isReceivedAnItenBefore(client_id, fullName, addr1, city, gift_632703);
                if (!isReceived_632703){
                    ArrayList<String> cadidateListFor_632703 = new ArrayList<>();
                    cadidateListFor_632703.add("NovaFerrum Multivitamin With Iron (Plant-Based D-3) Pediatric Drops SINGLE");
                    getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_632703, gift_632703);
                }

                // disable due to out of stock
                /*String gift_632702 = "Tag-Yum! MVI Grape";
                boolean isReceived_632702 = isReceivedAnItenBefore(client_id, fullName, addr1, city, gift_632702);
                if (!isReceived_632702){
                    ArrayList<String> cadidateListFor_632702 = new ArrayList<>();
                    cadidateListFor_632702.add("HJ-E0NE-GLMP");
                    getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_632702, gift_632702);
                }

                String gift_632699 = "Tag-Pow! Adult Chewable";
                boolean isReceived_632699 = isReceivedAnItenBefore(client_id, fullName, addr1, city, gift_632699);
                if (!isReceived_632699){
                    ArrayList<String> cadidateListFor_632699 = new ArrayList<>();
                    cadidateListFor_632699.add("HJ-E0NE-GLMP");
                    getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_632699, gift_632699);
                }*/

                String gift_632700 = "Tag-Yay! Children Chewable";
                boolean isReceived_632700 = isReceivedAnItenBefore(client_id, fullName, addr1, city, gift_632700);
                if (!isReceived_632700){
                    ArrayList<String> cadidateListFor_632700 = new ArrayList<>();
                    cadidateListFor_632700.add("NovaFerrum Iron Childrens Chewable Tablets SINGLE");
                    getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_632700, gift_632700);
                }

                String gift_632698 = "Tag-All Good! Capsules";
                boolean isReceived_632698 = isReceivedAnItenBefore(client_id, fullName, addr1, city, gift_632698);
                if (!isReceived_632698){
                    ArrayList<String> cadidateListFor_632698 = new ArrayList<>();
                    cadidateListFor_632698.add("H5-BD0Z-9HZR");
                    getFreeItemIfPurchaseOneOfSkus(order, cadidateListFor_632698, gift_632698);
                }

                //=================================================================================
                // Sean 02/18/2020 case 760136
                String companyName = order.getShippingAddress().company_name;
                if (companyName.toUpperCase().contains("Amazon")){
                    order.setOnHold();
                }
            }

            /**
             * Sean Chen created on 10/24/2019 if any gets puchased from a cadidate list, then add a give-awsy item
             * @param order
             * @param cadidateSkuList
             * @param giveAwaySku
             * @throws Exception
             */
            public void getFreeItemIfPurchaseOneOfSkus (Order order, ArrayList<String> cadidateSkuList, String giveAwaySku) throws Exception {
                if(order.containsAnySKU(cadidateSkuList)){
                    OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku("596", giveAwaySku);
                    long oh = item.getOwdInventoryOh().getQtyOnHand();
                    if(oh > 0) {
                        order.addLineItem(giveAwaySku, 1, 0, 0, giveAwaySku, "", "");
                    }
                }
            }

            /**
             * Sean Chen created on 10/25/2019 if a customer received one certain free item before, then return true
             * @param client_id
             * @param fullName
             * @param addr1
             * @param city
             * @param lineitem
             * @return
             * @throws Exception
             */
            public boolean isReceivedAnItenBefore(String client_id, String fullName, String addr1, String city,
                                                  String lineitem) throws Exception {
                try {
                    String firstName = OWDUtilities.getFirstNameFromWholeName(fullName);
                    String lastName = OWDUtilities.getLastNameFromWholeName(fullName);
                    String query =("exec sp_checkPeopleReceiveItemBefore :client_id, :firstName,:lastName, :addr1, :city, :lineitem");
                    Query q = HibernateSession.currentSession().createSQLQuery(query);

                    q.setParameter("client_id", client_id);
                    q.setParameter("firstName", firstName);
                    q.setParameter("lastName",lastName);
                    q.setParameter("addr1",addr1);
                    q.setParameter("city", city);
                    q.setParameter("lineitem", lineitem);

                    if (q.list().size() > 0) {
                        return true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }

            /**
             * Sean Chen created on 01/29/2019 to check if a customer ever purchased at lease once
             * @param client_id
             * @param fullName
             * @param addr1
             * @param city
             */
            public boolean isPurchasedBefore(String client_id, String fullName, String addr1, String city) throws Exception {

                try {
                    Session sess = HibernateSession.currentSession();

                    String firstName = OWDUtilities.getFirstNameFromWholeName(fullName);
                    String lastName = OWDUtilities.getLastNameFromWholeName(fullName);

                    WInvRequest wq = (WInvRequest) sess.load(WInvRequest.class, Integer.valueOf(client_id));

                    // invoice location
                    Criteria crit = sess.createCriteria(OwdOrderShipInfo.class);
                    crit.setMaxResults(1);
                    System.out.println("1");

                    crit.add(Restrictions.eq("shipFirstName", firstName));
                    System.out.println("2");

                    crit.add(Restrictions.eq("shipLastName", lastName));
                    System.out.println("3");

                    crit.add(Restrictions.eq("shipAddressOne", addr1));
                    System.out.println("4");

                    crit.add(Restrictions.eq("shipCity", city));
                    System.out.println("5");

                    List<String> orderHistory = crit.list();
                    System.out.println(orderHistory.size());

                    if (crit.list().size() > 0) {
                        return true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }


            @Override
            public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                List<String> caseSkus = new ArrayList<String>();

                caseSkus.add("NovaFerrum Multivitamin With Iron Pediatric Drops CASE");
                caseSkus.add("NovaFerrum 125 Liquid Iron CASE");
                caseSkus.add("NovaFerrum Liquid Iron Pediatric Drops CASE");
                caseSkus.add("NovaFerrum 50 MG Iron Capsules CASE");
                caseSkus.add("NovaMV Multivitamin Pediatric Drops CASE");


                if(order.containsAnySKU(caseSkus)){
                    order.setSignatureRequired(true);
                    return "UPS Ground";
                }

                if(oldMethod.toUpperCase().equals("FREE SHIPPING")){
                    return "USPS Priority Mail";
                }

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

        api.setClientInfo(596, null);
        api.setFirstOrderId(0);
        api.setOrderReferencePrefix("S");
        api.setOrderType("Shopify") ;

        //   api.setOtherFulfillmentSku("SEPARATE");
       // api.setIgnoreUnrecognizedSKUs(true);
        // api.setFulfillmentServiceCode("stewart");


        api.importCurrentOrders(ShopifyAPI.kStatusTypePaid, false);
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

package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.business.Client;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Order;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.MagentoImportOrderCustomizer;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class GildanMagentoOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {
        run();
        try{
          //  MagentoRemoteService service = new MagentoRemoteService("http://gildanonlinetest.owd.com/index.php/api/index/index","owd","owd7172",471);//438);

         /*  List<String> skus= service.getAllSKUs();
            System.out.println(skus.size());
            System.out.println(skus);*/

           // updateGildanInventoryLevels(service);
           // service.sendAdvancedInventoryDiscrepancyReport("dnickels@owd.com");
        }catch(Exception e){
            e.printStackTrace();
        }

       // updateGildanInventoryLevels(service);

    }

    public void internalExecute() {

        try {

            //step one get a result set of clients from SQLserver
            MagentoRemoteService service = new MagentoRemoteService("https://basicwear.com/index.php/api/index/index","owd","owd7172",471);//438);
            service.setProcessingOrders(true);
           // service.setProcessingOrdersInvoiced(true);
            List<String> phoneList = new ArrayList<>();
            phoneList.add("4242456064");
            phoneList.add("2103389166");
            phoneList.add("3232290681");
            service.setFraudPhoneList(phoneList);
            service.setPendingOrders(true);
            service.setPendingOrdersInvoiced(true);
            service.setPendingCheckOrdersInvoiced(false);
            service.setOrderCustomizer(getGildanImportOrderCustomizer());
            service.importMagentoOrders(200000000d,-30);


            updateGildanInventoryLevels(service);

        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }
    }



      public GildanImportOrderCustomizer getGildanImportOrderCustomizer()
    {
        return new GildanImportOrderCustomizer();
    }

    public class GildanImportOrderCustomizer extends MagentoImportOrderCustomizer
    {


        @Override
        public void customizeOrder(MagentoRemoteService service, Order currOrder, MagentoRemoteService.MagentoOrder mOrder) throws Exception {
                 if (currOrder.total_order_cost > 200) {
            currOrder.is_future_ship = 1;
            currOrder.forcePayment = true;
            currOrder.backorder_rule = com.owd.core.xml.OrderXMLDoc.kBackOrderAll;
            currOrder.hold_reason = currOrder.hold_reason + ": " + "Order total over 200.00 - held for fraud review. Do not release without positive contact with cardholder using listed phone number.";
        }

            if("2".equals(mOrder.order.get("store_id")))
            {
                //Basicwear
                currOrder.prt_pack_reqd=0;
                currOrder.prt_pick_reqd=1;
                currOrder.bill_cc_type="CK";
                currOrder.is_paid=1;
                currOrder.paid_amount= currOrder.total_order_cost;

            }


            if(currOrder.coupon_code.equalsIgnoreCase("presskit"))
            {
                currOrder.is_future_ship = 1;
                currOrder.forcePayment = true;
                currOrder.backorder_rule = com.owd.core.xml.OrderXMLDoc.kBackOrderAll;
                currOrder.hold_reason = currOrder.hold_reason + ": " + "Held due to presskit rule";
                if (!currOrder.orderRefnumExists(currOrder.getClientOrderReference())) {
                    String[] skuemails = new String[1];
                    skuemails[0] = ("sstafford@gildan.com");

                    Mailer.sendMail("ALERT: Gildan online store PRESSKIT order received", "Received as order reference "+currOrder.order_refnum, skuemails, "donotreply@owd.com");

                }

            }

        }

        @Override
        public void handleLineItemWithOptions(MagentoRemoteService service, Order order, MagentoRemoteService.MagentoOrder mo, ClientPolicy policy, String realSku, Map item) throws Exception {
            log.debug("in special options item handler");

        Map options = (Map) ((Map)item.get("options")).get("options");
            log.debug("OptionsMap="+options);
        for(Map optionMap:(Collection<Map>) options.values())
        {

        }
        }


    }

     public static void updateGildanInventoryLevels(MagentoRemoteService service) throws Exception
    {


        HashMap<String,Integer> updateMap = new HashMap<String,Integer>();

        List<HashMap<String,String>> storeSkus = service.getAllInventoryInfo();

        HashMap<String,Integer> owdStockMap = new HashMap<String,Integer>();

         ResultSet rs = HibernateSession.getResultSet("select inventory_num,qty_on_hand,is_active from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey=471");

            while (rs.next()) {

                owdStockMap.put(rs.getString(1),(1==rs.getInt(3)?rs.getInt(2):0) );

            }

         for(HashMap<String,String> storeSkuMap:storeSkus)
         {
           //  log.debug(storeSkuMap);
             String sku = storeSkuMap.get("sku");
             String type = storeSkuMap.get("type")+"";

          //   log.debug("checking SKU "+sku);
             if(owdStockMap.containsKey(sku))
             {
                // log.debug("getting qty for SKU "+sku);
                 updateMap.put(sku,owdStockMap.get(sku));
             }                       else
             {
                 if(type.equals("simple"))
                 {
                 log.debug("no owd entry found for sku "+sku);
                 }
             }

         }



        log.debug("updateMap:"+updateMap);

        service.setMagentoInventoryLevelMultiCall(updateMap,true);
    }






}

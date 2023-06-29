package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Order;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.MagentoImportOrderCustomizer;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/21/11
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PMConceptsMagentoImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception{

        run();

    }

    public void internalExecute() {

        try {
            //step one get a result set of clients from SQLserver
            MagentoRemoteService service = new MagentoRemoteService(
                    "https://amatalife.com/index.php/api/"
                    , "oneworld"
                    , "oneworld123"
                    , 528);
            service.setProcessingOrders(true);
            service.setPendingOrders(false);
            service.setPendingCheckOrdersInvoiced(false);
            service.setAllowFreeItems(true);
            service.setOrderCustomizer(getPmConceptsImportOrderCustomizer());


            service.importMagentoOrders(100000001,-60);

            updateInventoryLevels(service);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }



    public void updateInventoryLevels(MagentoRemoteService service) throws Exception {


        HashMap<String, Integer> updateMap = new HashMap<String, Integer>();

        List<HashMap<String, String>> storeSkus = service.getAllInventoryInfo();

        HashMap<String, Integer> owdStockMap = new HashMap<String, Integer>();

        ResultSet rs = HibernateSession.getResultSet("select inventory_num,qty_on_hand,is_active from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey=528");

        while (rs.next()) {

            owdStockMap.put(rs.getString(1), (1 == rs.getInt(3) ? rs.getInt(2) : 0));

        }

        for (HashMap<String, String> storeSkuMap : storeSkus) {
            log.debug(storeSkuMap);
            String sku = storeSkuMap.get("sku");
            log.debug("checking SKU " + sku);
            if (owdStockMap.containsKey(sku)) {
                log.debug("getting qty for SKU " + sku);
                updateMap.put(sku, owdStockMap.get(sku));
            } else {
                log.debug("no owd entry found for sku " + sku);
            }

        }


        log.debug("updateMap:" + updateMap);

        service.setMagentoInventoryLevel(updateMap, false);
    }

    public PmConceptsImportOrderCustomizer getPmConceptsImportOrderCustomizer()
    {
        return new PmConceptsImportOrderCustomizer();
    }

    public class PmConceptsImportOrderCustomizer extends MagentoImportOrderCustomizer
    {


        @Override
        public void customizeOrder(MagentoRemoteService service, Order currOrder, MagentoRemoteService.MagentoOrder mOrder) throws Exception {


            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            Date today = new Date();

            if(today.getTime()>sdf.parse("20150218").getTime())
            {
                if (!(currOrder.containsAnySKU(Arrays.asList("50-100","50-101")))) {
                    currOrder.addInsertItemIfAvailable("70-101", 1);

                }
            }

                if (!currOrder.containsSKU("20-400")) {
                    currOrder.addInsertItemIfAvailable("70-100", 1);

                }


           String giftValue = mOrder.order.get("giftwrap_amount");
            if(giftValue==null)giftValue="0.00";
            try
            {
              float giftprice = Float.parseFloat(giftValue);
               if(giftprice>0)
               {
                   currOrder.addLineItem("80-162", "1", "" + OWDUtilities.roundFloat(giftprice),"0.00","","","");
               }
            }   catch(Exception ex)
            {

            }

            if(currOrder.withinDateRange("2019-12-13","2019-12-24")) {
                if (currOrder.getCountByLineItemInventoryField("inventory_num", "20-", true) > 0) {
                    currOrder.addInsertItemIfAvailable("40-102", 1);
                }

            }
            //do not run if the order contains auto skus
            //removed 8-30-18
         /*   if(currOrder.getCountByLineItemInventoryField("inventory_num","auto",true)==0) {
                //Free Gift Rule for Joe
                float cost = 0f;
                if (currOrder.containsSKU("80-162")) {
                    cost = currOrder.total_product_cost - 9.95f;
                } else {
                    cost = currOrder.total_product_cost;
                }

                if (cost > 60f) {
                    currOrder.addLineItem("20-200", "1", "0.00", "0,00", "Amata Facial Cream Free Gift", "", "");
                    currOrder.gift_message = "Thank you very much for your order.  You've been chosen to receive a free bottle of our Premium Facial Cream which is included.  We hope that you'll consider trying it and telling your friends.   With Gratitude!";
                }
            }*/


            currOrder.backorder_rule = OrderXMLDoc.kPartialShip;
            currOrder.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
        }

        @Override
        public void handleLineItemWithOptions(MagentoRemoteService service, Order order, MagentoRemoteService.MagentoOrder mo, ClientPolicy policy, String realSku, Map item) throws Exception {
            /*log.debug("in special options item handler");

            Map options = (Map) ((Map)item.get("options")).get("options");
            log.debug("OptionsMap="+options);
            for(Map optionMap:(Collection<Map>) options.values())
            {

            }*/
        }


    }

}

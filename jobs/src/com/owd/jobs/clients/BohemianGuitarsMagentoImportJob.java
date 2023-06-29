package com.owd.jobs.clients;

import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.MagentoImportOrderCustomizer;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BohemianGuitarsMagentoImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {

        run();


    }

    public void internalExecute() {

        try {
            //step one get a result set of clients from SQLserver
            MagentoRemoteService service = new MagentoRemoteService(
                    "https://www.bohemianguitars.com/index.php/api"
                    , "owd"
                    , "owdowd"
                    , 586);


            service.setProcessingOrders(true);
            service.setPendingOrders(true);
            service.setPendingCheckOrdersInvoiced(false);

            service.setAllowFreeItems(true);

         //   service.sendInventoryDiscrepancyReport("stewart@owd.com");


            service.importMagentoOrders(1,-45);




        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

    public static void updateInventoryLevels(MagentoRemoteService service) throws Exception
    {


        HashMap<String,Integer> updateMap = new HashMap<String,Integer>();

        List<HashMap<String,String>> storeSkus = service.getAllInventoryInfo();

        HashMap<String,Integer> owdStockMap = new HashMap<String,Integer>();

        ResultSet rs = HibernateSession.getResultSet("select inventory_num,qty_on_hand,is_active from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey=586");

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

        service.setMagentoInventoryLevel(updateMap,true);
    }
    public class BMRImportOrderCustomizer extends MagentoImportOrderCustomizer
    {


        @Override
        public void customizeOrder(MagentoRemoteService service, Order currOrder, MagentoRemoteService.MagentoOrder mOrder) throws Exception {


            if(service.getStoreId()==6)    //feminal store pack slip
            {
                currOrder.prt_pick_reqd=1;
                currOrder.prt_pack_reqd=0;
            }
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

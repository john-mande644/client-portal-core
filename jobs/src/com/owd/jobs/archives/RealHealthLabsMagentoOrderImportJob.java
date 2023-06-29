package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.MagentoSubscriptionCreator;
import com.owd.jobs.jobobjects.magento.owd.MagentoCustomerAPI;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class RealHealthLabsMagentoOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {

      run();
      //  DrCherryMagentoOrderImportJob job = new DrCherryMagentoOrderImportJob();
      //  job.

    }

    public void internalExecute() {

        try {
            //step one get a result set of clients from SQLserver
            MagentoRemoteService service = new MagentoRemoteService("http://www.realhealthlabs.com/store/index.php/api", "OWD", "one7172world", 401);
            service.setProcessingOrders(true);
            service.setPendingOrders(false);
            service.setPendingCheckOrdersInvoiced(true);
            service.setAllowFreeItems(true);
            service.setAutoShipManager(new RhlShipManager());
            service.importMagentoOrders(100000000, -45);

            //  updateInventoryLevels(service);
           // updatePDSCustomerMembershipFromOWDForRecentChanges(5);


        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }


    public class RhlShipManager extends MagentoSubscriptionCreator {


        public float getAutoUnitCost(MagentoRemoteService.MagentoOrder mo, Order order, String originalSKU, String autoSKU, float originalCost, int qty) throws Exception {

            Inventory item = Inventory.dbloadByPart(autoSKU,"401");


            return OWDUtilities.roundFloat(originalCost);
        }

        public float getShipCost(OwdOrderAuto auto, Order o) {

            return 0.00f;
        }

        public void setSalesTaxAmount(OwdOrderAuto auto, Order o) {

            //noop
            if (auto.getShipState().equalsIgnoreCase("CA") || auto.getShipState().equalsIgnoreCase("CALIFORNIA")) {
                auto.setSalesTax(new BigDecimal(OWDUtilities.roundFloat(new BigDecimal(0.07750d * (getSubtotal(auto))).floatValue())));
            } else if (auto.getShipState().equalsIgnoreCase("SD") || auto.getShipState().equalsIgnoreCase("SOUTH DAKOTA")) {
                auto.setSalesTax(new BigDecimal(OWDUtilities.roundFloat(new BigDecimal(0.0600d * (getSubtotal(auto))).floatValue())));
            } else {
                auto.setSalesTax(new BigDecimal(0.00));
            }
        }

        public void addInsertItems(Session sess, OwdOrderAuto auto, Order o) throws Exception {


        }

        public void setShipMethodName(OwdOrderAuto auto, Order o) throws Exception {
            if (o.getShippingAddress().isInternational()) {
                if (getPredictedWeight(auto) >= 3.75) {
                    auto.setShipMethod("USPS Priority Mail International");
                } else {
                    auto.setShipMethod("USPS First-Class Mail International");
                }
            } else {
                if (getPredictedWeight(auto) >= 0.75) {
                    auto.setShipMethod("USPS Priority Mail");
                } else {
                    auto.setShipMethod("USPS First-Class Mail");
                }
            }
        }


    }

    public void updateInventoryLevels(MagentoRemoteService service) throws Exception {


        HashMap<String, Integer> updateMap = new HashMap<String, Integer>();

        List<HashMap<String, String>> storeSkus = service.getAllInventoryInfo();

        HashMap<String, Integer> owdStockMap = new HashMap<String, Integer>();

        ResultSet rs = HibernateSession.getResultSet("select inventory_num,qty_on_hand,is_active from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey=401");

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


    public void updatePDSCustomerMembershipFromOWDForRecentChanges(int daysBack)  throws Exception
    {

        MagentoRemoteService service = new MagentoRemoteService("http://www.realhealthlabs.com/store/index.php/api","OWD","one7172world",401);
        MagentoCustomerAPI api = new MagentoCustomerAPI(service);


        Map<String,String> pdsMap = new TreeMap<String,String>();

        ResultSet rs = HibernateSession.getResultSet("select bill_email as email, max(auto_ship_id) as id from owd_order_auto where  datediff(day,modified,getdate())<"+daysBack+"  and client_fkey=401 and bill_email like '%@%'\n" +
                "group by bill_email");
        while(rs.next())
        {
           pdsMap.put(rs.getString("email"), rs.getString("id"));
        }
        rs.close();
        HibernateSession.closeSession();




        for(String email:pdsMap.keySet())
        {
            String groupID = "1";
            OwdOrderAuto auto = (OwdOrderAuto) HibernateSession.currentSession().load(OwdOrderAuto.class,Integer.parseInt(pdsMap.get(email)));
            try
            {
                if(auto.getStatus()==0)
                {
                  groupID = "4";
                }

                Map map = api.getCustomerDataMapForEmail(email);

            if(map ==  null)
            {
                //magento customer record does not exist
                api.createMagentoCustomer(auto,groupID,"0","1");

            }   else
            {
                //magento customer record does exist
                //log.debug(map);
                api.setMagentoCustomerGroupId((String)map.get("customer_id"),groupID);

            }
            }catch(Exception ex)
            {
              ex.printStackTrace();
            }
        }

    }

}

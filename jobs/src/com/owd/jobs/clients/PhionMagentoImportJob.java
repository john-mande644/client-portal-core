package com.owd.jobs.clients;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Order;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.MagentoImportOrderCustomizer;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class PhionMagentoImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();
    static List<String> holdNames = new ArrayList<String>();

  static  {

        holdNames.add("HPC-Grocery Invoices");
        holdNames.add("Potential Dynamix");
        holdNames.add("Biovorur");
        //   holdNames.add("Select Nutrition");
        //  holdNames.add("Palko Distributing Co");
        holdNames.add("Tree Of Life");
        holdNames.add("Lotus Light Enterprises");
        //   holdNames.add("Nutri-Books");
        holdNames.add("PRINCE OCHI");
        holdNames.add("UK LIMITED");
        holdNames.add("YUEN MI MING");
        holdNames.add("AMERIMARK");
        holdNames.add("Earth Fare");
        holdNames.add("Whole Foods");
        //  holdNames.add("Down to Earth");
        holdNames.add("Sunflower Market");
        //  holdNames.add("Wr Group");
        holdNames.add("Vitamin Shoppe");
        holdNames.add("accra");
        holdNames.add("World of Wellness");
        holdNames.add("Fruitful Yield");
        holdNames.add("Sprouts Farmers Market");
        holdNames.add("DrugStore");
        holdNames.add("drugstore.com");
        holdNames.add("Lucky Vitamin");
      holdNames.add("ALKAWAY");
      holdNames.add("All Care Products");
      holdNames.add("Super Natural Distributors");
      holdNames.add("Iherb");
      holdNames.add("Palko Distributing Co.");
      holdNames.add("Palko Distributing");
      holdNames.add("Avocado Ninja");

    }

    public static void main(String[] args) throws Exception{

        run();

    }

    public void internalExecute() {

        try {
            //step one get a result set of clients from SQLserver
            MagentoRemoteService service = new MagentoRemoteService(
                    "https://phionbalance.com/api"
                    , "oneworld"
                    , "oneworld123"
                    , 179) ;
            service.setProcessingOrders(true);
            service.setPendingOrders(false);
            service.setPendingCheckOrdersInvoiced(false);
            service.setAllowFreeItems(true);
            service.setOrderCustomizer(getPhionImportOrderCustomizer());


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

        ResultSet rs = HibernateSession.getResultSet("select inventory_num,qty_on_hand,is_active from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey=179");

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

    static void putAddresseeOnHold(Order order)
            throws Exception {
        String holdName = null;
        for (String testname : holdNames) {
            String cappedtestName = testname.toUpperCase();

            if (holdName == null) {
                if (order.getBillingContact().getName().toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getBillingAddress().company_name.toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getShippingContact().getName().toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getShippingAddress().company_name.toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getShippingAddress().city.toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getShippingAddress().state.toUpperCase().indexOf(cappedtestName) >= 0
                        ) {
                    holdName = testname;
                }
            }
        }
        if (holdName != null) {
            order.is_future_ship = 1;
            Mailer.sendMail("Phion " + holdName + " order received on hold", "Phion order " + order.order_refnum + " was received and placed on hold per the \"" + holdName + "\" rule", "lroberts@owd.com", "do-not-reply@owd.com");
        }




        }

    public PhionImportOrderCustomizer getPhionImportOrderCustomizer()
    {
        return new PhionImportOrderCustomizer();
    }

    class PhionImportOrderCustomizer extends MagentoImportOrderCustomizer
    {


        @Override
        public void customizeOrder(MagentoRemoteService service, Order order, MagentoRemoteService.MagentoOrder mo) throws Exception {
            super.customizeOrder(service, order, mo);
            putAddresseeOnHold(order);
        }
    }

}

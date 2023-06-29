package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class HighTimesDailyDropshipJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {
        run();
    }

    public void internalExecute() {

        try {
            //step one get a result set of clients from SQLserver
            PreparedStatement ps = HibernateSession.getPreparedStatement("");
            ps.setString(1, OWDUtilities.getSQLDateTimeForAdjustedDate(-1));

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                log.debug(rs.getString(1));
            }



         //   updateGildanInventoryLevels(service);

        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }
    }


     public void updateInventoryLevels(MagentoRemoteService service) throws Exception
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
             log.debug(storeSkuMap);
             String sku = storeSkuMap.get("sku");
             log.debug("checking SKU "+sku);
             if(owdStockMap.containsKey(sku))
             {
                 log.debug("getting qty for SKU "+sku);
                 updateMap.put(sku,owdStockMap.get(sku));
             }                       else
             {
                 log.debug("no owd entry found for sku "+sku);
             }

         }



        log.debug("updateMap:"+updateMap);

        service.setMagentoInventoryLevel(updateMap,false);
    }
}

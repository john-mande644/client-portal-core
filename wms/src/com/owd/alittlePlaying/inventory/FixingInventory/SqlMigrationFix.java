package com.owd.alittlePlaying.inventory.FixingInventory;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by danny on 3/11/2019.
 */
public class SqlMigrationFix {


    static List<Integer>  bad = new ArrayList<>();

    protected final static Logger log = LogManager.getLogger();
    public static void main(String[] args){

        Map<Integer,Integer> m = new TreeMap<>();



      









        int facilityId = 6;
        for(Integer inventoryId:m.keySet()){
            makeUpdate(inventoryId,m.get(inventoryId),facilityId);
        }



        log.debug("Bad....");
        for(Integer i:bad){
            log.debug(i);
        }


    }




    public static boolean makeUpdate(Integer inventoryId, int qty,  int facilityId){
        boolean success = false;
        qty = qty *-1;
        log.debug("Doing id: "+ inventoryId + " by : "+qty);

        String sql = "update owd_inventory_facility set qty = qty + :qty where facility_fkey = :facilityId and inventory_fkey = :inventoryId";
        try{
            Query  q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("qty",qty);
            q.setParameter("facilityId",facilityId);
            q.setParameter("inventoryId",inventoryId);
           int i =  q.executeUpdate();
            if(i>0){
                String sql2 = "update owd_inventory_oh set qty_on_hand = qty_on_hand + :qty where inventory_fkey = :inventoryId";
                Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
                qq.setParameter("qty",qty);
                qq.setParameter("inventoryId",inventoryId);
                i = qq.executeUpdate();
                if(i>0){
                    HibUtils.commit(HibernateSession.currentSession());
                    success = true;
                }else{
                    bad.add(inventoryId);
                }
            }else{
                bad.add(inventoryId);
            }

        }catch (Exception e){
            e.printStackTrace();
            bad.add(inventoryId);
        }



        return success;
    }
}

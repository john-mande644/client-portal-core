package com.owd.alittlePlaying.inventory;

import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 4/2/2017.
 */
public class moveLocationsToNewSku {



    public static void main(String[] args){
    Integer newClientId = 640;

        List<Integer> l = new ArrayList<Integer>();

        String sql = "select inventory_fkey, newFkey from clientTransferData where newClientFkey = :id and clientTransferData.inventory_fkey in (select inventory_fkey from owd_inventory_locations);";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("id",newClientId);
            List li = q.list();
            System.out.println(li.size());
            for(Object row:li){
                try {
                    Object[] data = (Object[]) row;
                    swapLoationsWithId(Integer.parseInt(data[0].toString()), Integer.parseInt(data[1].toString()));
                    swapHistoryLoationsWithId(Integer.parseInt(data[0].toString()), Integer.parseInt(data[1].toString()));

                }catch (Exception e){
                    e.printStackTrace();
                }
               // swapTags(Integer.parseInt(data[0].toString()), Integer.parseInt(data[1].toString()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }






/*
        for(int i:l){
            boolean success = swapLoations(i,newClient);
        }*/


    }

    public static void updateUpcWithIds(Integer oldId, Integer newId){

        try{
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,oldId);
            OwdInventory newInv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,newId);
            newInv.setUpcCode(inv.getUpcCode());
            newInv.setIsbnCode(inv.getIsbnCode());
            HibernateSession.currentSession().saveOrUpdate(newInv);
            HibUtils.commit(HibernateSession.currentSession());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean swapTags(Integer inventoryID, Integer newId){
        boolean success = false;
        try{

          /*  OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,inventoryID);
            System.out.println("Sku: "+ inv.getInventoryNum());

            OwdInventory newSku = InventoryManager.getOwdInventoryForClientAndSku(newClientId+"",inv.getInventoryNum());*/


            String sql = "update owd_tags set external_id = :newId where external_id = :oldId";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("newId",newId);
            q.setParameter("oldId",inventoryID);
            int i = q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
            success = true;




        }catch (Exception e){
            e.printStackTrace();


        }



        return success;
    }

    public static boolean swapLoationsWithId(Integer inventoryID, Integer newId) {
        boolean success = false;
        try {

          /*  OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,inventoryID);
            System.out.println("Sku: "+ inv.getInventoryNum());

            OwdInventory newSku = InventoryManager.getOwdInventoryForClientAndSku(newClientId+"",inv.getInventoryNum());*/


            String sql = "update owd_inventory_locations set inventory_fkey = :newId where inventory_fkey = :oldId";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("newId", newId);
            q.setParameter("oldId", inventoryID);
            int i = q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
            success = true;


        } catch (Exception e) {
            e.printStackTrace();


        }
        return success;
    }

    public static boolean swapHistoryLoationsWithId(Integer inventoryID, Integer newId){
        boolean success = false;
        try{

          /*  OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,inventoryID);
            System.out.println("Sku: "+ inv.getInventoryNum());

            OwdInventory newSku = InventoryManager.getOwdInventoryForClientAndSku(newClientId+"",inv.getInventoryNum());*/


            String sql = "update zz_deleted_locations set inventory_fkey = :newId where inventory_fkey = :oldId";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("newId",newId);
            q.setParameter("oldId",inventoryID);
            int i = q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
            success = true;




        }catch (Exception e){
            e.printStackTrace();


        }



        return success;
    }


    public static boolean swapLoations(Integer inventoryID, Integer newClientId){
        boolean success = false;
        try{

            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,inventoryID);
            System.out.println("Sku: "+ inv.getInventoryNum());

            OwdInventory newSku = InventoryManager.getOwdInventoryForClientAndSku(newClientId+"",inv.getInventoryNum());

            if(newSku.getInventoryNum().length()>0){
                System.out.println("We have a good lookup");
                System.out.println(newSku.getInventoryId());
                String sql = "update owd_inventory_locations set inventory_fkey = :newId where inventory_fkey = :oldId";

                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("newId",newSku.getInventoryId());
                q.setParameter("oldId",inventoryID);
                int i = q.executeUpdate();
                HibUtils.commit(HibernateSession.currentSession());
                success = true;

            }


        }catch (Exception e){
            e.printStackTrace();


        }



        return success;
    }


}

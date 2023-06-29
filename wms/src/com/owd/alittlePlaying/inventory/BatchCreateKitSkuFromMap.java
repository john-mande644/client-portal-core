package com.owd.alittlePlaying.inventory;

import com.owd.core.business.order.Inventory;
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
 * Created by danny on 3/22/2019.
 */
public class BatchCreateKitSkuFromMap {

    static List<String> nonExistenItem = new ArrayList<>();
    static List<String> errorCreating = new ArrayList<>();

    public static void main(String[] args){

        Map<String,String> map = new HashMap<>();
        map.put("PGD0014","PGD0014");
        map.put("17933C7E-A14364C8","PGD0014");
        map.put("AB6299C2-E21AD88B","PGD0014");




        for(String s:map.keySet()){
            createVirtualSkuAndPointToItemSku(s,map.get(s),"633");
        }


        if(nonExistenItem.size()>0){
            System.out.println("Non Existent Items in our System");
            for(String s : nonExistenItem){
                System.out.println(s);
            }
            System.out.println("------------------------------------------------------\r\n");
        }
        if(errorCreating.size()>0){
            System.out.println("Error Items ");
            for(String s : errorCreating){
                System.out.println(s);
            }
            System.out.println("------------------------------------------------------");
        }
    }




    public static void createVirtualSkuAndPointToItemSku(String virtualSku, String itemSku, String clientId) {
        if (!virtualSku.equalsIgnoreCase(itemSku)) {
            try {
                OwdInventory kitItem = InventoryManager.getOwdInventoryForClientAndSku(clientId,itemSku);
                System.out.println("Doing: " + kitItem.getDescription());
                String desc = kitItem.getDescription();
                if (virtualSku.contains("Personal")) {
                    desc = desc + " (Personal)";
                }
                if (virtualSku.contains("Educator")) {
                    desc = desc + " (Educator)";
                }
                Inventory newItem = InventoryManager.createInventoryItemForClient(clientId, virtualSku, desc, "0.0", true, "", "", "");
                System.out.println("Created id: " + newItem.inventory_id);
                if(!virtualSku.contains("Digital")) {
                    String sql = "insert into owd_inventory_required_skus (inventory_fkey, req_inventory_fkey, req_inventory_quant) values (:inventoryId, :inventoryFkey, :qty)";

                    Query q = HibernateSession.currentSession().createSQLQuery(sql);
                    q.setParameter("inventoryId", newItem.inventory_id);
                    q.setParameter("inventoryFkey", kitItem.getInventoryId());
                    q.setParameter("qty", 1);
                    q.executeUpdate();
                    HibUtils.commit(HibernateSession.currentSession());
                }
            } catch (Exception e) {
                if (e.getMessage().contains("SKU values are case-sensitive.")) {
                   if(!nonExistenItem.contains(itemSku)) {
                       nonExistenItem.add(itemSku);
                   }
                } else {
                    errorCreating.add(virtualSku);
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("Skipping: "+ virtualSku);
        }
    }
}

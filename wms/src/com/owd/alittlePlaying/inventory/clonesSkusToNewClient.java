package com.owd.alittlePlaying.inventory;

import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by danny on 4/2/2017.
 */
public class clonesSkusToNewClient {
    private final static Logger log =  LogManager.getLogger();




    public static void main(String[] args){
        ExecutorService exec = Executors.newFixedThreadPool(8);
       // System.setProperty("com.owd.environment","test");
        try{
           Integer newClientId = 640;
            List<Integer> l = new ArrayList<Integer>();



            List<Integer> bad = new ArrayList<Integer>();

          String sql = "select inventory_fkey, newSku from clientTransferData where newClientFkey = :id and created = 0 and newSku <> 'unknown'" +
                  "and newsku is not null";
            try{
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("id",newClientId);
                List li = q.list();
                for(Object row:li){
                    Object[] data = (Object[]) row;
                   exec.submit(new cloningTask(Integer.parseInt(data[0].toString()), newClientId, data[1].toString()));
                   /* if(!success){
                        bad.add(Integer.parseInt(data[0].toString()));
                    }*/
                }
                exec.shutdown();
                log.debug("Waiting Executor");
                exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
                log.debug("done");

            }catch (Exception e){
                e.printStackTrace();
            }








          /*  for(Integer id:l){
                boolean success = cloneTheSku(id,newClientId);
                if(!success){
                    bad.add(id);
                }
            }*/


            if(bad.size()>0){
                System.out.println(bad);
                for(Integer i:bad){
                    System.out.println(i);
                }
            }




        }catch (Exception e){
            e.printStackTrace();
        }
    }
 public static boolean cloneTheSkuNewSku(Integer inventoryId, Integer newClientId,String newsku){
  boolean success = false;

     log.debug("Doing: "+newsku );
  try {

      OwdInventory test = InventoryManager.getOwdInventoryForClientAndSku(newClientId+"",newsku);
      OwdInventory inv2;
      if(test.getInventoryNum().length()>0){
          inv2 = test;
      }else{
         inv2 = new OwdInventory();
      }

   OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, inventoryId);


   OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, newClientId);
   inv2.setOwdClient(client);
   inv2.setInventoryNum(newsku);
   inv2.setMfrPartNum(inv.getMfrPartNum());
   inv2.setDescription(inv.getDescription());
   inv2.setKeyword(inv.getKeyword());
   inv2.setPrice(inv.getPrice());
   inv2.setIsActive(inv.isIsActive());
   inv2.setBarcodeNo(inv.getBarcodeNo());
   inv2.setFrontLocation(inv.getFrontLocation());
   inv2.setQtyReorder(inv.getQtyReorder());
   inv2.setQtyHighAlert(inv.getQtyHighAlert());
   inv2.setIsInsurable(inv.isIsInsurable());
   inv2.setNotes(inv.getNotes());
   inv2.setCreatedBy(inv.getCreatedBy());
   inv2.setItemColor(inv.getItemColor());
   inv2.setItemSize(inv.getItemSize());
   inv2.setLongDesc(inv.getLongDesc());
   inv2.setSuppCost(inv.getSuppCost());
   inv2.setHarmCode(inv.getHarmCode());
   inv2.setWeightLbs(inv.getWeightLbs());
   inv2.setCustomsDesc(inv.getCustomsDesc());
   inv2.setCustomsValue(inv.getCustomsValue());
   inv2.setUpcCode(inv.getUpcCode());
   inv2.setIsbnCode(inv.getIsbnCode());
   inv2.setCreatedDate(inv.getCreatedDate());
   inv2.setModifiedDate(inv.getModifiedDate());
   inv2.setModifiedBy(inv.getModifiedBy());
   inv2.setClientItemKey(inv.getClientItemKey());
   inv2.setSuppFkey(inv.getSuppFkey());
   inv2.setImageThumbUrl(inv.getImageThumbUrl());
   inv2.setImageUrl(inv.getImageUrl());
   inv2.setDefaultFacilityCode(inv.getDefaultFacilityCode());
   inv2.setMasterCaseQty(inv.getMasterCaseQty());
   inv2.setCaseQty(inv.getCaseQty());
   inv2.setScanNotRequired(inv.getScanNotRequired());
   inv2.setIsAutoInventory(inv.getIsAutoInventory());
   inv2.setGroupName(inv.getGroupName());
   inv2.setRequireSerialNumbers(inv.getRequireSerialNumbers());
   inv2.setQtyUnusable(inv.getQtyUnusable());
   inv2.setItemCost(inv.getItemCost());
   inv2.setItemShipCost(inv.getItemShipCost());
   inv2.setCatalogUrl(inv.getCatalogUrl());
   inv2.setBackLocation(inv.getBackLocation());
   inv2.setUnusableLocation(inv.getUnusableLocation());
      inv2.setIsBulkyPack(inv.getIsBulkyPack());
      inv2.setIsBulkyPick(inv.getIsBulkyPick());
      inv2.setBulkyPackFeeOverride(inv.getBulkyPackFeeOverride());
      inv2.setBulkyPickFeeOverride(inv.getBulkyPickFeeOverride());
      inv2.setBulkyPickOverride(inv.getBulkyPickOverride());
      inv2.setBulkyPackOverride(inv.getBulkyPackOverride());
    inv2.setIsOwdDimensions(inv.getIsOwdDimensions());
      inv2.setReportedPercent(inv.getReportedPercent());


   HibernateSession.currentSession().save(inv2);
   HibUtils.commit(HibernateSession.currentSession());



      // check for Inventory On Hond record

      String ohSelect = "select inventory_fkey from owd_inventory_oh where inventory_fkey = :inventoryFkey";
      Query ohSelectQuery = HibernateSession.currentSession().createSQLQuery(ohSelect);
      ohSelectQuery.setParameter("inventoryFkey",inv2.getInventoryId());
      List l = ohSelectQuery.list();
      if(l.size()==0) {
          String sqlOH = "insert into owd_inventory_oh (inventory_fkey,qty_on_hand)  VALUES (:inventoryFkey,0)";
          Query ohQuery = HibernateSession.currentSession().createSQLQuery(sqlOH);
          ohQuery.setParameter("inventoryFkey", inv2.getInventoryId());
          ohQuery.executeUpdate();
      }




      /*String sqlIn = "insert into special_instructions (instructions, activity_type, url, external_id) \n" +
              "\n" +
              "select instructions, activity_type, url, :newId from special_instructions where external_id = :fkey";

      Query q3 = HibernateSession.currentSession().createSQLQuery(sqlIn);
      q3.setParameter("fkey",inventoryId);
      q3.setParameter("newId", inv2.getInventoryId());



      q3.executeUpdate();
      HibUtils.commit(HibernateSession.currentSession());*/

      String sql = "update clientTransferData set created = 1, newFkey = :newId where inventory_fkey = :fkey";
      Query q = HibernateSession.currentSession().createSQLQuery(sql);
      q.setParameter("fkey",inventoryId);
      q.setParameter("newId", inv2.getInventoryId());



      q.executeUpdate();
      HibUtils.commit(HibernateSession.currentSession());
   success = true;

  }catch (Exception e){
      try {
          HibernateSession.closeSession();
      }catch(Exception ex){

      }
   e.printStackTrace();
  }


  return success;
 }




    public static boolean cloneTheSku(Integer inventoryId, Integer newClientId){
        boolean success = false;

        try {
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, inventoryId);

            OwdInventory inv2 = new OwdInventory();
            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, newClientId);
            inv2.setOwdClient(client);
            inv2.setInventoryNum(inv.getInventoryNum());
            inv2.setMfrPartNum(inv.getMfrPartNum());
            inv2.setDescription(inv.getDescription());
            inv2.setKeyword(inv.getKeyword());
            inv2.setPrice(inv.getPrice());
            inv2.setIsActive(inv.isIsActive());
            inv2.setBarcodeNo(inv.getBarcodeNo());
            inv2.setFrontLocation(inv.getFrontLocation());
            inv2.setQtyReorder(inv.getQtyReorder());
            inv2.setQtyHighAlert(inv.getQtyHighAlert());
            inv2.setIsInsurable(inv.isIsInsurable());
            inv2.setNotes(inv.getNotes());
            inv2.setCreatedBy(inv.getCreatedBy());
            inv2.setItemColor(inv.getItemColor());
            inv2.setItemSize(inv.getItemSize());
            inv2.setLongDesc(inv.getLongDesc());
            inv2.setSuppCost(inv.getSuppCost());
            inv2.setHarmCode(inv.getHarmCode());
            inv2.setWeightLbs(inv.getWeightLbs());
            inv2.setCustomsDesc(inv.getCustomsDesc());
            inv2.setCustomsValue(inv.getCustomsValue());
            inv2.setUpcCode(inv.getUpcCode());
            inv2.setIsbnCode(inv.getIsbnCode());
            inv2.setCreatedDate(inv.getCreatedDate());
            inv2.setModifiedDate(inv.getModifiedDate());
            inv2.setModifiedBy(inv.getModifiedBy());
            inv2.setClientItemKey(inv.getClientItemKey());
            inv2.setSuppFkey(inv.getSuppFkey());
            inv2.setImageThumbUrl(inv.getImageThumbUrl());
            inv2.setImageUrl(inv.getImageUrl());
            inv2.setDefaultFacilityCode(inv.getDefaultFacilityCode());
            inv2.setMasterCaseQty(inv.getMasterCaseQty());
            inv2.setCaseQty(inv.getCaseQty());
            inv2.setScanNotRequired(inv.getScanNotRequired());
            inv2.setIsAutoInventory(inv.getIsAutoInventory());
            inv2.setGroupName(inv.getGroupName());
            inv2.setRequireSerialNumbers(inv.getRequireSerialNumbers());
            inv2.setQtyUnusable(inv.getQtyUnusable());
            inv2.setItemCost(inv.getItemCost());
            inv2.setItemShipCost(inv.getItemShipCost());
            inv2.setCatalogUrl(inv.getCatalogUrl());
            inv2.setBackLocation(inv.getBackLocation());
            inv2.setUnusableLocation(inv.getUnusableLocation());


            HibernateSession.currentSession().save(inv2);
            HibUtils.commit(HibernateSession.currentSession());
            success = true;

        }catch (Exception e){
            e.printStackTrace();
        }


        return success;
    }
}

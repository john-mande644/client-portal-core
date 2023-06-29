package com.owd.alittlePlaying.inventory;

import com.owd.core.dbUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by danny on 9/7/2016.
 */
public class postExternalCountData {


    public static List<String> l = new ArrayList<String>();







    public static void main(String[] args){



       Map<String,Integer> m = new HashMap<String,Integer>();

        String sql = "select newSku, qty from clientTransferData where qty>0 and newClientFkey = :newId and complete = 0;";

        int newClientId = 640;
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("newId",newClientId);
            List l = q.list();

            for(Object row:l){
                Object[] data = (Object[]) row;
                m.put(data[0].toString(),Integer.parseInt(data[1].toString()));
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(m);
        System.out.println(m.size());


       // verifyAdjustments(m,"489","DC6");
       wipeOutTheStuff(m,newClientId+"","DC7");
    }

    public static void verifyAdjustments(Map<String,Integer> items,String clientId, String facility){
try {
    List<String> bad = new ArrayList<String>();

    for (String sku : items.keySet()) {
        if (!l.contains(sku)) {
            if (items.get(sku) < 0) {
                OwdInventory inv = InventoryManager.getOwdInventoryForClientAndSku(clientId, sku);
                int stock = InventoryManager.getStockLevelForFacility(inv.getInventoryId(), facility);
                if (stock + items.get(sku) < 0) {
                    bad.add(sku + "," + stock + "," + items.get(sku));
                }

            }

        }else{
            System.out.println("Skipping");
        }
    }

    for(String s:bad){
        System.out.println(s);
    }
}catch (Exception e){
    e.printStackTrace();
}
    }

    public static void wipeOutTheStuff(Map<String,Integer> items,String clientId, String facility){
        try{
            List<String> errors = new ArrayList<String>();

            OwdReceive rcv = new OwdReceive();
            Calendar cal = Calendar.getInstance();
            rcv.setCarrier("UPS Ground");
            rcv.setCreatedBy("Inventory Count");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:MM:ss");
            rcv.setCreatedDate(cal.getTime());
            rcv.setDriver("");
            rcv.setIsVoid(false);
            rcv.setNotes("Client Move");
            rcv.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.valueOf(clientId)));
            rcv.setReceiveDate(cal.getTime());
            rcv.setReceiveUser("danny");
            rcv.setRefNum("ClientMove");
            rcv.setTimeIn(cal.getTime());
            rcv.setTimeOut(cal.getTime());
            rcv.setType("Adjustment");
            rcv.setFacilityCode(facility);
            HibernateSession.currentSession().save(rcv);

            for(String sku:items.keySet()){
                if(!l.contains(sku)) {
                    OwdInventory inv = InventoryManager.getOwdInventoryForClientAndSku(clientId, sku);


                    OwdReceiveItem ri = new OwdReceiveItem();

                    ri.setCreatedBy("danny");
                    ri.setCreatedDate(cal.getTime());
                    ri.setDescription(inv.getDescription());
                    ri.setInventoryNum(inv.getInventoryNum());
                    ri.setItemStatus("New");
                    ri.setOwdInventory(inv);
                    ri.setIsVoid(0);
                    ri.setQuantity(items.get(sku));
                    ri.setOwdReceive(rcv);
                    HibernateSession.currentSession().save(ri);


                    System.out.println("Doing: " + sku);

                    OwdInventoryHistory ohh = new OwdInventoryHistory();
                    ohh.setInventoryFkey(inv.getInventoryId());
                    ohh.setReceiveItemFkey(ri.getReceiveItemId());
                    ohh.setQtyChange(items.get(sku));
                    ohh.setNote("custom.ClientMove");
                    ohh.setFacility(FacilitiesManager.getFacilityForCode(facility));
                    HibernateSession.currentSession().save(ohh);


                }

            }
            rcv.setTransactionNum("OWDADJ-"+ dbUtilities.getNextID(((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), "Receive"));

            HibernateSession.currentSession().save(rcv);
            HibUtils.commit(HibernateSession.currentSession());



            for(String s: errors){
                System.out.println(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}

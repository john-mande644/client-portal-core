package com.owd.jobs.clients;

import com.owd.core.dbUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import com.owd.jobs.OWDStatefulJob;
import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.*;

public class FOHReturnInventoryTransferJob extends OWDStatefulJob {


    public static void main(String[] args){

        run();
    }


    Map<Integer,Integer> decrementMap = new HashMap<>();
    Map<Integer,Integer> increaseMap = new HashMap<>();

    public void internalExecute() {

        try{
            loadMaps();
            System.out.println(decrementMap);
            System.out.println(increaseMap);
            adjustTheStuff(decrementMap,"489","DC6");
            adjustTheStuff(increaseMap,"640","DC6");


        }catch (Exception e){
            e.printStackTrace();
            //todo add loggable Exception
        }


    }

    private void loadMaps() throws Exception{
        String sql = "SELECT\n" +
                "    dbo.clientTransferData.inventory_fkey,\n" +
                "    dbo.clientTransferData.newFkey,\n" +
                "    dbo.owd_inventory_oh.qty_on_hand\n" +
                "FROM\n" +
                "    dbo.clientTransferData\n" +
                "INNER JOIN\n" +
                "    dbo.owd_inventory_oh\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.clientTransferData.inventory_fkey = dbo.owd_inventory_oh.inventory_fkey)\n" +
                "WHERE\n" +
                "    dbo.clientTransferData.newClientFkey = 640\n" +
                "AND dbo.owd_inventory_oh.qty_on_hand > 0 ;";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List l = q.list();

        for(Object row : l){
            Object[] data = (Object[]) row;
            decrementMap.put(Integer.parseInt(data[0].toString()), Integer.parseInt(data[2].toString())*-1);
            increaseMap.put(Integer.parseInt(data[1].toString()),Integer.parseInt(data[2].toString()));


        }


    }

    public static void adjustTheStuff(Map<Integer,Integer> items,String clientId, String facility){
        try{
            List<String> errors = new ArrayList<String>();

            OwdReceive rcv = new OwdReceive();
            Calendar cal = Calendar.getInstance();
            rcv.setCarrier("UPS Ground");
            rcv.setCreatedBy("Return Sync");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:MM:ss");
            rcv.setCreatedDate(cal.getTime());
            rcv.setDriver("");
            rcv.setIsVoid(false);
            rcv.setNotes("Return Sync Client Move");
            rcv.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.valueOf(clientId)));
            rcv.setReceiveDate(cal.getTime());
            rcv.setReceiveUser("danny");
            rcv.setRefNum("ClientMove");
            rcv.setTimeIn(cal.getTime());
            rcv.setTimeOut(cal.getTime());
            rcv.setType("Adjustment");
            rcv.setFacilityCode(facility);
            HibernateSession.currentSession().save(rcv);

            for(Integer invFkey:items.keySet()){

                    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,invFkey);


                    OwdReceiveItem ri = new OwdReceiveItem();

                    ri.setCreatedBy("danny");
                    ri.setCreatedDate(cal.getTime());
                    ri.setDescription(inv.getDescription());
                    ri.setInventoryNum(inv.getInventoryNum());
                    ri.setItemStatus("New");
                    ri.setOwdInventory(inv);
                    ri.setIsVoid(0);
                    ri.setQuantity(items.get(invFkey));
                    ri.setOwdReceive(rcv);
                    HibernateSession.currentSession().save(ri);


                    System.out.println("Doing: " + inv.getInventoryNum());

                    OwdInventoryHistory ohh = new OwdInventoryHistory();
                    ohh.setInventoryFkey(inv.getInventoryId());
                    ohh.setReceiveItemFkey(ri.getReceiveItemId());
                    ohh.setQtyChange(items.get(invFkey));
                    ohh.setNote("custom.ClientMove");
                    ohh.setFacility(FacilitiesManager.getFacilityForCode(facility));
                    HibernateSession.currentSession().save(ohh);


                }


            rcv.setTransactionNum("OWDADJ-"+ dbUtilities.getNextID(((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), "Receive"));

            HibernateSession.currentSession().save(rcv);
            HibUtils.commit(HibernateSession.currentSession());




        }catch (Exception e){
            e.printStackTrace();
        }



    }

}

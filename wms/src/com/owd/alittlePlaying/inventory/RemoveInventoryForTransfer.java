package com.owd.alittlePlaying.inventory;

import com.owd.core.dbUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdReceive;
import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RemoveInventoryForTransfer {


    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:MM:ss");

    //Create receive
    public static void main(String[] args){
        try{

           // System.setProperty("com.owd.environment","test");

            Integer receiveId = CreateReceive(640,"DC6");
            System.out.println(receiveId);
            createReceiveItems(640,receiveId);
            createInventoryHistory(receiveId,640,"DC6");
            HibUtils.commit(HibernateSession.currentSession());




        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static Integer CreateReceive(Integer clientId, String facility) throws Exception{
        OwdReceive rcv = new OwdReceive();
        Calendar cal = Calendar.getInstance();
        rcv.setCarrier("UPS Ground");
        rcv.setCreatedBy("Inventory Transfer");

        rcv.setCreatedDate(cal.getTime());
        rcv.setDriver("");
        rcv.setIsVoid(false);
        rcv.setNotes("Inventory Transfer");
        rcv.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, clientId));
        rcv.setReceiveDate(cal.getTime());
        rcv.setReceiveUser("IT");
        rcv.setRefNum("Inventory Transfer IT");
        rcv.setTimeIn(cal.getTime());
        rcv.setTimeOut(cal.getTime());
        rcv.setType("Adjustment");
        rcv.setFacilityCode(facility);
        rcv.setTransactionNum("OWDADJ-"+ dbUtilities.getNextID(((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), "Receive"));

        HibernateSession.currentSession().save(rcv);
        HibernateSession.currentSession().refresh(rcv);
        return rcv.getReceiveId();

    }


    // Create receive items from Table
    private static void createReceiveItems(Integer clientId,Integer receiveId) throws Exception{

        /*ri.setCreatedBy("danny");
        ri.setCreatedDate(cal.getTime());
        ri.setDescription(inv.getDescription());
        ri.setInventoryNum(inv.getInventoryNum());
        ri.setItemStatus("New");
        ri.setOwdInventory(inv);
        ri.setIsVoid(0);
        ri.setQuantity(items.get(sku));
        ri.setOwdReceive(rcv);*/

        String sql = "insert into owd_receive_item  (created_by,created_date, description, inventory_num,item_status,inventory_id, is_void,quantity,receive_fkey) " +
                "SELECT \n" +
                "    'IT', \n" +
                "    GETDATE(), \n" +
                "    dbo.owd_inventory.description, \n" +
                "    dbo.clientTransferData.oldSku, \n" +
                "    'New', \n" +
                "    dbo.clientTransferData.inventory_fkey, \n" +
                "    0, \n" +
                "    dbo.owd_inventory_facility.qty * -1, \n" +
                "    :receiveId \n" +
                "FROM \n" +
                "    dbo.clientTransferData \n" +
                "INNER JOIN \n" +
                "    dbo.owd_inventory \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.clientTransferData.inventory_fkey = dbo.owd_inventory.inventory_id) \n" +
                "INNER JOIN " +
                "dbo.owd_inventory_facility \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_facility.inventory_fkey and facility_fkey = 6 )  \n" +
                "WHERE \n" +
                "    dbo.clientTransferData.newClientFkey = :clientId \n" +
                "AND dbo.clientTransferData.complete = 0 \n" +
                "AND  dbo.owd_inventory_facility.qty > 0 \n" +
                ";";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId",clientId);
        q.setParameter("receiveId",receiveId);
        int results = q.executeUpdate();
        System.out.println(results);


    }

    private static void createInventoryHistory(Integer receiveId,Integer clientId, String facility) throws Exception{

       /* ohh.setInventoryFkey(inv.getInventoryId());
        ohh.setReceiveItemFkey(ri.getReceiveItemId());
        ohh.setQtyChange(items.get(sku));
        ohh.setNote("custom.ClientMove");
        ohh.setFacility(FacilitiesManager.getFacilityForCode(facility));*/
       String sql = "insert into owd_inventory_history  (inventory_fkey, receive_item_fkey, qty_change, note, facility_fkey) " +
               "SELECT \n" +
               "    dbo.owd_receive_item.inventory_id, \n" +
               "    dbo.owd_receive_item.receive_item_id, \n" +
               "    dbo.owd_receive_item.quantity, \n" +
               "    'custom.InventoryTransfer' , \n" +
               "    :facilityId\n" +
               "    \n" +
               "FROM \n" +
               "    dbo.owd_receive_item \n" +
               "WHERE \n" +
               "    dbo.owd_receive_item.receive_fkey = :receiveId ;";

       Query q = HibernateSession.currentSession().createSQLQuery(sql);
       q.setParameter("receiveId",receiveId);
       q.setParameter("facilityId",FacilitiesManager.getFacilityForCode(facility).getId());
       int i = q.executeUpdate();
       System.out.println(i);
    }

    // Create History from Receive ITem

}

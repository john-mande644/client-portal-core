package com.owd.alittlePlaying;

import com.owd.core.dbUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by danny on 9/7/2016.
 */
public class wipeOutInventory {





    public static void main(String[] args){


        List<String> l = new ArrayList<String>();


        try{
            String sql = "select newSku from clientTransferData where newClientFkey = 640 and qty >0";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List results = q.list();

            for(Object data: results){
                l.add(data.toString());
            }


        //System.out.println(l);

            wipeOutTheStuff(l,"489","DC6");

        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public static void wipeOutTheStuff(List<String> items,String clientId, String facility){
        try{
            List<String> errors = new ArrayList<String>();

            OwdReceive rcv = new OwdReceive();
            Calendar cal = Calendar.getInstance();
            rcv.setCarrier("UPS Ground");
            rcv.setCreatedBy("Inventory Move");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:MM:ss");
            rcv.setCreatedDate(cal.getTime());
            rcv.setDriver("");
            rcv.setIsVoid(false);
            rcv.setNotes("Client Transfer");
            rcv.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.valueOf(clientId)));
            rcv.setReceiveDate(cal.getTime());
            rcv.setReceiveUser("danny");
            rcv.setRefNum("Client Transfer");
            rcv.setTimeIn(cal.getTime());
            rcv.setTimeOut(cal.getTime());
            rcv.setType("Adjustment");
            rcv.setFacilityCode(facility);
            HibernateSession.currentSession().save(rcv);

            for(String sku:items){
                OwdInventory inv = InventoryManager.getOwdInventoryForClientAndSku(clientId,sku);
                int stock = InventoryManager.getStockLevelForFacility(inv.getInventoryId(),facility);
                if(stock>0){

                    OwdReceiveItem ri = new OwdReceiveItem();

                    ri.setCreatedBy("danny");
                    ri.setCreatedDate(cal.getTime());
                    ri.setDescription(inv.getDescription());
                    ri.setInventoryNum(inv.getInventoryNum());
                    ri.setItemStatus("New");
                    ri.setOwdInventory(inv);
                    ri.setIsVoid(0);
                    ri.setQuantity(stock*-1);
                    ri.setOwdReceive(rcv);
                    HibernateSession.currentSession().save(ri);


                    System.out.println("Doing: "+sku);

                    OwdInventoryHistory ohh = new OwdInventoryHistory();
                    ohh.setInventoryFkey(inv.getInventoryId());
                    ohh.setReceiveItemFkey(ri.getReceiveItemId());
                    ohh.setQtyChange(stock*-1);
                    ohh.setNote("custom.warehouseTransfer");
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

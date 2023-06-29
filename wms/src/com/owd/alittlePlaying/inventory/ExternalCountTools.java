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
 * Created by danny on 3/19/2018.
 */
public class ExternalCountTools {




    public static void main(String[] args){

       // updateCountWithSkuAndOwd("489", "G_halstonV2224","DC6");
       // calculateAdjustments("489", "G_halstonV2224");

        Map<String,Integer> m = new HashMap<String, Integer>();
        m.put("P471246",-1);
        m.put("P479485",-1);
        m.put("P494888",-1);
        m.put("P536965",-1);
        m.put("P590468",-1);
        m.put("P589279",-1);
        m.put("P619380",-1);
        m.put("P536888",-2);
        m.put("P478842",-3);
        m.put("P536765",1);
        m.put("P536574",1);
        m.put("P536536",1);
        m.put("P536929",1);
        m.put("P589738",1);
        m.put("P589569",1);
        m.put("P589570",1);
        m.put("P590313",1);
        m.put("P589592",1);
        m.put("P590362",1);
        m.put("P589864",1);
        m.put("P590083",-1);
        m.put("P590369",1);
        m.put("P590424",1);
        m.put("P590432",-1);
        m.put("P590449",2);
        m.put("P618991",-1);
        m.put("P618947",4);
        m.put("P646314",1);
        m.put("P646688",1);
        m.put("P647150",1);
        m.put("P647167",-1);
        m.put("P646831",-1);
        m.put("P478619",1);
        m.put("P478649",1);
        m.put("P478570",2);
        m.put("P481682",1);
        m.put("P481688",1);
        m.put("P477454",2);
        m.put("P477456",1);
        m.put("P477473",1);
        m.put("P477476",1);
        m.put("P477485",3);
        m.put("P477487",1);
        m.put("P472508",1);
        m.put("P472509",1);
        m.put("P472431",1);
        m.put("P478613",1);
        m.put("P478658",2);
        m.put("P478680",1);
        m.put("P493416",1);
        m.put("P481668",1);
        m.put("P477655",1);
        m.put("P481600",1);
        m.put("P481676",1);
        m.put("P481610",1);
        m.put("P486345",1);
        m.put("P486376",1);
        m.put("P478698",1);
        m.put("P472541",1);
        m.put("P477339",1);
        m.put("P488738",1);
        m.put("P488739",1);
        m.put("P486295",2);
        m.put("P477539",1);
        m.put("P477550",1);
        m.put("P472764",1);
        m.put("P472709",1);
        m.put("P472622",1);
        m.put("P478790",1);
        m.put("P472492",1);
        m.put("P472496",1);
        m.put("P488747",1);
        m.put("P486394",1);
        m.put("P488842",1);
        m.put("P488838",1);
        m.put("P488871",-1);
        m.put("P486521",2);
        m.put("P488760",2);
        m.put("P486422",1);
        m.put("P488766",1);
        m.put("P488767",1);
        m.put("P486513",1);
        m.put("P486517",1);
        m.put("P486414",1);
        m.put("P481823",1);
        m.put("P488798",1);
        m.put("P481728",1);
        m.put("P481788",1);
        m.put("P481745",1);
        m.put("P481746",1);
        m.put("P486467",1);
        m.put("P486472",1);
        m.put("P493372",1);
        m.put("P494552",-1);
        m.put("P493999",-1);
        m.put("P494599",3);
        m.put("P494080",-2);
        m.put("P493653",-1);
        m.put("P493657",2);
        m.put("P494605",1);
        m.put("P494199",1);
        m.put("P494486",-1);
        m.put("P472781",1);
        m.put("P494086",1);
        m.put("P493775",1);
        m.put("P493565",1);
        m.put("P493704",1);
        m.put("P477685",1);
        m.put("P494828",1);
        m.put("P497142",1);
        m.put("P494717",1);
        m.put("P493492",2);
        m.put("P493507",1);
        m.put("P493381",1);
        m.put("P478871",1);
        m.put("P478916",2);
        m.put("P477730",1);
        m.put("P479550",1);
        m.put("P471966",1);
        m.put("P470028",1);
        m.put("P471855",2);
        m.put("P470936",1);
        m.put("P470942",1);
        m.put("P471038",1);
        m.put("P471214",1);
        m.put("P471251",1);
        m.put("P471252",1);
        m.put("P471910",1);
        m.put("P471929",1);
        m.put("P470047",1);
        m.put("P470082",1);
        m.put("P470884",1);
        m.put("P470573",-1);
        m.put("P472027",1);
        m.put("P470159",2);
        m.put("P470905",1);
        m.put("P471234",-1);
        m.put("P471235",-2);
        m.put("P471344",1);
        m.put("P471410",1);
        m.put("P471876",1);
        m.put("P471996",1);
        m.put("P472006",3);
        m.put("P470324",1);
        m.put("P470513",1);
        m.put("P470522",1);
        m.put("P471730",1);
        PostAdjustments(m,"489","DC6");

    }



    public static void calculateAdjustments(String clientFkey, String groupName){

        try{
            String sql = "update w_external_inv_counts set adjustment = (owd_oh - external_count) * -1  where client_fkey = :clientFkey and group_name = :groupName";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientFkey",clientFkey);
            q.setParameter("groupName",groupName);

            System.out.println(q.executeUpdate());
            HibUtils.commit(HibernateSession.currentSession());



        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public static void updateCountWithSkuAndOwd(String clientFkey, String groupName, String facility){

        String sql = "select upc from w_external_inv_counts where client_fkey = :clientFkey and group_name = :groupName";

        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientFkey",clientFkey);
            q.setParameter("groupName",groupName);
            List l = q.list();

            for(Object row: l){
                String sql2 = "select inventory_num, dbo.totalInventoryOhFacility(dbo.owd_inventory.inventory_id,:facility) " +
                        "from owd_inventory where upc_code = :upcCode and client_fkey = :clientFkey and group_name = :groupName";
                Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
                qq.setParameter("facility",facility);
                qq.setParameter("upcCode",row.toString());
                qq.setParameter("clientFkey",clientFkey);
                qq.setParameter("groupName",groupName);

                List ll = qq.list();

                if(ll.size()>0){
                    String sql3 = "update w_external_inv_counts set sku = :sku, owd_oh = :qty " +
                            "where upc = :upcCode and client_fkey = :clientFkey and group_name = :groupName ";
                    Query update = HibernateSession.currentSession().createSQLQuery(sql3);
                    Object row2 = ll.get(0);
                    Object[] data = (Object[]) row2;
                    System.out.println("Setting: "+data[0].toString());
                    update.setParameter("sku",data[0].toString());
                    update.setParameter("qty",data[1].toString());
                    update.setParameter("upcCode",row.toString());
                    update.setParameter("clientFkey",clientFkey);
                    update.setParameter("groupName",groupName);
                    update.executeUpdate();
                    HibUtils.commit(HibernateSession.currentSession());
                }


            }



        }catch (Exception e){
            e.printStackTrace();
        }






    }





    public static void PostAdjustments(Map<String,Integer> items,String clientId, String facility){
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
            rcv.setNotes("Inventory Count");
            rcv.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.valueOf(clientId)));
            rcv.setReceiveDate(cal.getTime());
            rcv.setReceiveUser("danny");
            rcv.setRefNum("Inventory Count");
            rcv.setTimeIn(cal.getTime());
            rcv.setTimeOut(cal.getTime());
            rcv.setType("Adjustment");
            rcv.setFacilityCode(facility);
            HibernateSession.currentSession().save(rcv);

            for(String sku:items.keySet()){

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
                    ohh.setNote("custom.ExternalInventoryCount");
                    ohh.setFacility(FacilitiesManager.getFacilityForCode(facility));
                    HibernateSession.currentSession().save(ohh);




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

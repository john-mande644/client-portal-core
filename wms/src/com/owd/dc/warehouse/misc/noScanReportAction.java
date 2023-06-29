package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.inventory.inventoryUtilities;
import com.owd.dc.setup.selectList;
import com.owd.dc.warehouse.misc.beans.noScanReportBean;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 2/6/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class noScanReportAction extends ActionSupport {

   private List<selectList> clientList;
   private String client;
   private List<noScanReportBean> inventory;

    public  String execute(){
        try{
          clientList = inventoryUtilities.getClientList();

        }catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    public String selectClient(){

        System.out.println(client);
        try{
        inventory = getCurrentNoScanItems(client, HibernateSession.currentSession());
            clientList = inventoryUtilities.getClientList();
        }catch (Exception e){
            e.printStackTrace();

        }

        return "success";
    }

    public String saveChanges(){
     try{
         clientList = inventoryUtilities.getClientList();
       for(noScanReportBean sb : inventory){
           System.out.println(sb.getInventoryId() +": "+sb.getNoScan());
          if(!sb.getNoScan()){
             System.out.println(unSetNoScan(sb.getInventoryId(), HibernateSession.currentSession()));

          }
       }
         HibUtils.commit(HibernateSession.currentSession());
     }catch(Exception e){
         e.printStackTrace();
     }
        inventory = null;


        return "success";
    }


    public List getClientList() {
        return clientList;
    }

    public void setClientList(List clientList) {
        this.clientList = clientList;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<noScanReportBean> getInventory() {
        return inventory;
    }

    public void setInventory(List<noScanReportBean> inventory) {
        this.inventory = inventory;
    }

    private  List<noScanReportBean> getCurrentNoScanItems(String clientId, Session sess) throws Exception{
            String sql = "select inventory_id, inventory_num, description, no_scan from owd_inventory where client_fkey = :clientId and no_scan = 1 and is_active=1 order by inventory_id";
          List<noScanReportBean> inv = new ArrayList<noScanReportBean>();
            Query q = sess.createSQLQuery(sql);
        q.setParameter("clientId",clientId);
        List results = q.list();
        for(Object row:results){
            Object[] data = (Object[]) row;
            noScanReportBean scanb = new noScanReportBean();
            scanb.setInventoryId(data[0].toString());
            scanb.setInventoryNum(data[1].toString());
            scanb.setDescription(data[2].toString());
            if(data[3].toString().equals("1")){
             scanb.setNoScan(true);
            } else{
                scanb.setNoScan(false);
            }

            inv.add(scanb);
        }

        return inv;

    }
    private String unSetNoScan(String id,Session sess) throws Exception{
        String s = "fail";
        String sql = "update owd_inventory set no_scan = 0 where inventory_id = :id";
        Query q = sess.createSQLQuery(sql);
        q.setParameter("id",id);
        int i = q.executeUpdate();
        if (i>=1){
           s="success";
        }


       return s;
    }
}

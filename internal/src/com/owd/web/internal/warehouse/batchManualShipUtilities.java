package com.owd.web.internal.warehouse;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 7/13/2016.
 */
public class batchManualShipUtilities {


    public static void main(String[] args){

        List<String> l = new ArrayList<>();





        processList(529,l,"10/06/17");

    }

    private static void processList(int clientId,List<String> orders,String addToTracking){

        List<String> bad = new ArrayList<>();
        for(String s:orders){
            if(!markAsShipped(clientId,s,addToTracking)){
                bad.add(s);
            }
        }

        System.out.println(bad);
        System.out.println(bad.size());
    }
    public static boolean addGroupName(int clientId,String orderRef, String groupName){
        boolean success = false;
        String sql = "update owd_order set group_name = :groupName where client_fkey = :clientId and order_refnum = :orderRef";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("orderRef",orderRef);
            q.setParameter("groupName",groupName);
            int ii = q.executeUpdate();
            if(ii>0) {
                HibUtils.commit(HibernateSession.currentSession());
                success = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }
    public static boolean markAsShipped(int clientId,String orderRef,String addToTracking){
        boolean success = false;
        String updateSQL = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                + "msn,is_void,reported,email_sent) VALUES (:orderId,1,:tracking,:weight,:billed,:cost,convert(datetime,convert(varchar,getdate(),101)),getdate(),\'Manual\',getdate(),\'Manual\',0,0, 0,0)";
        String orderSql = "select order_id from owd_order where client_fkey = :clientId and order_refnum = :orderRef";

        try {
            Query q = HibernateSession.currentSession().createSQLQuery(orderSql);
            q.setParameter("clientId",clientId);
            q.setParameter("orderRef",orderRef);
            List l = q.list();

            if(l.size()>0){
                String orderId = l.get(0).toString();
                System.out.println("WE have this order Id" + orderId);
                Query qq = HibernateSession.currentSession().createSQLQuery(updateSQL);
                qq.setParameter("orderId",orderId);
                qq.setParameter("tracking",orderId+" "+addToTracking);
                qq.setParameter("weight",0);
                qq.setParameter("billed",0);
                qq.setParameter("cost",0);
                int i = qq.executeUpdate();

                if(i>0){
                    String sql = "exec update_order_shipment_info :orderId";
                    Query qqq = HibernateSession.currentSession().createSQLQuery(sql);
                    qqq.setParameter("orderId",orderId);
                    int ii = qqq.executeUpdate();
                    HibUtils.commit(HibernateSession.currentSession());
                    success = true;
                }


            }else{
                System.out.println("Not found");
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return success;
    }




}

package com.owd.alittlePlaying;

import org.hibernate.Query;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by danny on 6/7/2016.
 */
public class resetShippedToWarehouse {






    /**
     * Created by danny on 4/30/2015.
     */


        public static void main(String args[]){

            try{


                System.out.println(resetOrderViaOrderNumber(30238535));



            }catch (Exception e){
                e.printStackTrace();
            }


        }


    public static boolean resetOrderViaOrderNumber(Integer orderNum) throws Exception{
         return resetOrder(getOrderId(orderNum+""));

    }


        public static boolean resetOrder(String orderId) throws Exception{
            boolean success = false;

            System.out.println(orderId);
            resetTracking(orderId);
            resetPackage(orderId);
            String sql = "update owd_order set order_status = 'At Warehouse', shipped_on = null, ship_packs = null, tracking_nums = '', shipped_cost = null, shipped_weight = null, shipped_lines = null, shipped_units = null where order_id = :orderId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            int i=   q.executeUpdate();
            if(i>0) {
                success = true;
                HibUtils.commit(HibernateSession.currentSession());
            }
            return success;
        }
        public static String getOrderId(String orderNum) throws Exception{
            String sql = "select order_id from owd_order where order_num = :orderNum";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderNum",orderNum);


            return q.list().get(0).toString();
        }

        public static void resetTracking(String orderFkey) throws Exception{
            String sql = "update owd_order_track set is_void = 1 where is_void = 0 and order_fkey = :orderFkey";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderFkey",orderFkey);
            q.executeUpdate();



        }
        public static void resetPackage(String orderFkey) throws Exception{
            String sql = "update package_order set packs_shipped = 0 where owd_order_fkey = :orderFkey and is_void = 0";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderFkey",orderFkey);
            q.executeUpdate();

        }


}

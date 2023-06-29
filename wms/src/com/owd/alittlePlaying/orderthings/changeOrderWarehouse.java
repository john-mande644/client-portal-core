package com.owd.alittlePlaying.orderthings;


import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

public class changeOrderWarehouse {




    public static void main(String[] args){
        swapOrder(21596117,"DC1");
    }




    public static boolean swapOrder(int orderId,String dc){
        boolean success = false;
        try{
            unPostOrder(orderId);
            updateFacility(orderId,dc);
            releaseExistingOrder.release(orderId);
            success = true;
        }catch (Exception e){
            e.printStackTrace();
        }



        return success;

    }

    public static void unPostOrder(int orderId) throws Exception{
        String sql = "execute unpost_order :orderId ";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);

        q.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void updateFacility(int orderId, String dc) throws Exception{

        String sql = "update owd_order set facility_policy = :dc, facility_code = :dc where order_id = :orderId ";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("dc",dc);
        q.setParameter("orderId",orderId);

        q.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());


    }
}

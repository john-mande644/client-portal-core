package com.owd.alittlePlaying.orderthings;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * Created by danny on 5/18/2017.
 */
public class voidingList {


    static List<String> bad = new ArrayList<String>();

    public static void main(String[] args) {

        List<Integer> l = Arrays.asList(
                19055257,
                19055258,
                19055259,
                19055260,
                19055261,
                19055262        );
        System.out.println("size " + l.size());
        for(Integer s: l){
            System.out.println(s);
        }
        processOrderIdList(l,"634");
        for (String s : bad) {
            System.out.println("bad: " + s);
        }

    }


    public static void processOrderIdList(List<Integer> order_ids, String clientId) {

        for (Integer s : order_ids) {
            try {
                String sql = "select order_id, order_status from owd_order where order_id = :ref and client_fkey = :clientId";
                Session session = HibernateSession.currentSession();
                Query q = session.createSQLQuery(sql);
                q.setParameter("ref", s);
                q.setParameter("clientId", clientId);

                List l = q.list();
                System.out.println();
                processResult(s.toString(),l);
            } catch (Exception e) {
                bad.add(s.toString());
            }
        }
    }

    public static void processList(List<String> refNums, String clientId) {

        for (String s : refNums) {
            try {
                String sql = "select order_id, order_status from owd_order where order_refnum = :ref and client_fkey = :clientId";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);

                q.setParameter("ref", s);
                q.setParameter("clientId", clientId);

                List l = q.list();
                processResult(s,l);
            } catch (Exception e) {
                bad.add(s);
            }
        }
    }

    public static void processOrderNumList(List<String> orderNum, String clientId) {

        for (String s : orderNum) {
            try {
                String sql = "select order_id, order_status from owd_order where order_num = :ref and client_fkey = :clientId";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);

                q.setParameter("ref", s);
                q.setParameter("clientId", clientId);

                List l =  q.list();

                processResult(s, l);
            } catch (Exception ex) {
                bad.add(s);
            }
        }
    }

    public static void processResult(String s, List<Object[]> l) {
        try {

            if (l.size() > 0) {
                Object[] data = (Object[]) l.get(0);
                System.out.println(s + ": " + data[1]);

                if (data[1].toString().equals("At Warehouse")) {
                    Query qq = HibernateSession.currentSession().createSQLQuery("execute unpost_order :orderId");
                    qq.setParameter("orderId", data[0]);
                    int i = qq.executeUpdate();
                    if (i > 0) {
                        HibUtils.commit(HibernateSession.currentSession());
                    }

                }
                Query qq = HibernateSession.currentSession().createSQLQuery("execute void_order :orderId");
                qq.setParameter("orderId", data[0]);
                int i = qq.executeUpdate();
                if (i > 0) {
                    HibUtils.commit(HibernateSession.currentSession());
                } else {
                    bad.add(s);
                }
            } else {
                bad.add(s);
            }
        } catch (Exception e) {
            bad.add(s);
        }
    }
}

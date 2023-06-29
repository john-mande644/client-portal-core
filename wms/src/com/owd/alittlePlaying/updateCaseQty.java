package com.owd.alittlePlaying;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by danny on 7/6/2016.
 */
public class updateCaseQty {


    public static void main(String[] args){
        Map<String,Integer> m = new TreeMap<String, Integer>();

        m.put("21A-R01-Y-S",400);
        m.put("21A-R01-Y-A",400);
        m.put("21A-E18-Y",400);
        m.put("21A-E19-Y",400);
        m.put("21A-R01-Y-M",200);
        m.put("21A-E31-20-Y",200);
        m.put("21A-E12-W",200);
        m.put("21A-R01-Y-D",200);
        m.put("21A-E04-R",200);
        m.put("21A-R06-R6",100);
        m.put("21A-E54-Y-Q",50);
        m.put("21A-E54-Y-U",50);

        processMap(m);

        try{

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void processMap(Map<String,Integer> skus){
        List bad = new ArrayList();
        List bad2 = new ArrayList();
        for(String s:skus.keySet()){
           String sql = "select inventory_id from owd_inventory where inventory_num = :invNum and client_fkey = 489";
          try {
              Query q = HibernateSession.currentSession().createSQLQuery(sql);
              q.setParameter("invNum",s);
              List results = q.list();
              if(results.size()==0){
                  bad.add(s);
              }else{
                  System.out.println(s);
                 String sqlu = "update owd_inventory set case_qty = :qty where inventory_num = :invNum and client_fkey = 489";
                 Query qu = HibernateSession.currentSession().createSQLQuery(sqlu);
                  qu.setParameter("qty",skus.get(s));
                  qu.setParameter("invNum",s);
                  int i = qu.executeUpdate();
                  if(i==0){
                      bad2.add(s);
                  }
              }
          }catch (Exception e){
              e.printStackTrace();

          }

        }
        try {
            HibUtils.commit(HibernateSession.currentSession());
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(bad2);
        System.out.println(bad.size());
            System.out.println(bad);

    }

}

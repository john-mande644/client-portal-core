package com.owd.jobs.VerificationJobs;

import com.owd.core.Mailer;
import com.owd.core.business.order.Event;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.OWDStatefulJob;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by danny on 6/4/2019.
 */
public class CheckAndUnpostDuplicateOrders extends OWDStatefulJob {


    public static void main(String[] args){
        run();
    }
    Map<String,ArrayList<String>> holdMap = new TreeMap<>();

    public void internalExecute(){

        String sql = "select o.client_fkey, order_refnum, count(order_id) as orderCount from owd_order o left join w_order_attributes on order_id = order_fkey where actual_order_date > CONVERT(date, getdate()) and is_void = 0 and order_status = 'At Warehouse'\n" +
                "group by o.client_fkey, order_refnum, original_order_num,fingerprint\n" +
                "having count(order_id)>1";

        // 2nd query for back orders to notify FS to void...

        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);

            q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List l = q.list();
            if(l.size()>0){
                for(Object data: l){
                    Map map = (Map) data;
                    unpostOrdersAddToMap(map.get("order_refnum").toString(),Integer.parseInt(map.get("orderCount").toString()),map.get("client_fkey").toString());
                }
            }

            if(holdMap.size()>0){
                StringBuilder sb = new StringBuilder();
                for(String client: holdMap.keySet()){
                    sb.append(client);
                    sb.append("\r\n");
                    for(String s : holdMap.get(client)){
                        sb.append("\t");
                        sb.append(s);
                        sb.append("\r\n");
                    }


                }
                System.out.println(sb.toString());

                Mailer.sendMail("URGENT!! Duplicate Orders Put on Hold",sb.toString(),"client_support@owd.com");
            }





        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private  void unpostOrdersAddToMap(String orderRefnum, int totalOrders, String clientId){

        try{
            Criteria cr = HibernateSession.currentSession().createCriteria(OwdOrder.class);
            cr.add(Restrictions.eq("orderRefnum", orderRefnum));
            cr.add(Restrictions.eq("clientFkey",Integer.parseInt(clientId)));
            cr.add(Restrictions.eq("orderStatus", "At Warehouse"));
            cr.setMaxResults(totalOrders-1);

            List<OwdOrder> results = cr.list();

            for(OwdOrder order : results){
               String sql = "execute unpost_order :orderFkey";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("orderFkey",order.getOrderId());
                int i = q.executeUpdate();
                if(i>0){
                    System.out.println("Put Owd Order on HOld: "+ order.getOrderNum());
                    Event.addOrderEvent(order.getOrderId(), Event.kEventTypeHandling, "Order UN-Posted due to " + "Duplicate Check", "Duplicate Check");

                    if(!holdMap.containsKey(order.getClient().getCompanyName())){
                        holdMap.put(order.getClient().getCompanyName(),new ArrayList<String>());
                    }

                    holdMap.get(order.getClient().getCompanyName()).add("OWD order: "+order.getOrderNum()+", Client Reference: "+order.getOrderRefnum());

                    HibUtils.commit(HibernateSession.currentSession());

                }


            }


        }catch (Exception e){

            e.printStackTrace();
        }



    }
}

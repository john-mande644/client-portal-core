package com.owd.jobs.VerificationJobs;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdReceive;
import com.owd.hibernate.generated.OwdReceiveItem;
import com.owd.hibernate.generated.Receive;
import com.owd.hibernate.generated.ReceiveItem;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by danny on 10/4/2017.
 */
public class ReceivingNumbers {









    public static void main(String[] args){

        cylceThrough("2017-07-01", "2017-09-01");

    }





    public static void cylceThrough(String dateString,String endDate){

        String sql = "select id from receive where is_posted = 1 and created_on > :dateString and created_on < :endDate";

        List<String> errors = new ArrayList<>();
        try{

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("dateString", dateString);
            q.setParameter("endDate",endDate);
            List l = q.list();

            for(Object row:l){
                Receive r = (Receive) HibernateSession.currentSession().load(Receive.class,Integer.parseInt(row.toString()));
               Collection<ReceiveItem> items =  r.getReceiveItems();
                //lets find the owd_receive
                String orsql = "select receive_id from owd_receive where transaction_num = :transaction and is_void = 0";
                Query qq = HibernateSession.currentSession().createSQLQuery(orsql);
                qq.setParameter("transaction","OWDRCV-"+r.getId());

                OwdReceive owdReceive = (OwdReceive) HibernateSession.currentSession().load(OwdReceive.class,Integer.parseInt(qq.list().get(0).toString()));
                System.out.println(owdReceive.getReceiveId());

                for(OwdReceiveItem oItem : owdReceive.getOwdReceiveItems()){
                   System.out.println("Item from Owd Receive: "+ oItem.getInventoryNum());
                    if(oItem.getItemStatus().equals("New")) {
                        items.stream().forEach((rItem) ->{
                            if (oItem.getInventoryNum().equals(rItem.getAsnItem().getInventoryNum())) {
                                if (oItem.getQuantity().equals(rItem.getQtyReceive())) {

                                } else {
                                    errors.add("Receive id: " + r.getId() + ".   For sku " + oItem.getInventoryNum());
                                }
                            }
                        });
                    }



                }


            }
            System.out.println("We are done");
            for(String s:errors){
                System.out.println(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }




    }
}

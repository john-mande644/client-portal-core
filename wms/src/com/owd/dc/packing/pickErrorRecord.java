package com.owd.dc.packing;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/4/12
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickErrorRecord {



    public static String recordError(String orderFkey, String packer, String inventoryId, String inventoryNum, String qty){
        String s = "Error";
        String sql = "INSERT\n" +
                "INTO\n" +
                "    dbo.w_pick_error\n" +
                "    (\n" +
                "        order_fkey,\n" +
                "        packer,\n" +
                "        picker,\n" +
                "        inventory_id,\n" +
                "        inventory_num,\n" +
                "        qty\n" +
                "    )\n" +
                "    VALUES\n" +
                "    (:orderFkey, :packer, :picker, :invId, :invNum, :qty  )";
        try{
         Session sess = HibernateSession.currentSession();
            OwdOrder order = (OwdOrder) sess.load(OwdOrder.class,Integer.parseInt(orderFkey));
            System.out.println("This is who picked ed it wrongly:  "+ order.getPickBy());
            Query q = sess.createSQLQuery(sql);
            q.setParameter("orderFkey",orderFkey);
            q.setParameter("packer",packer);
            q.setParameter("picker",order.getPickBy());
            q.setParameter("invId",inventoryId);
            q.setParameter("invNum",inventoryNum);
            q.setParameter("qty",qty);
         int rows = q.executeUpdate();

          if(rows == 1){
              s = "Success";
              HibUtils.commit(sess);
          } else{
              s = "Error: Something went wrong please contact IT";
          }

        }catch (Exception e){
            e.printStackTrace();
            s = e.getMessage();

        }
        return s;
    }
     public static void main(String[] args){
        System.out.println(recordError("5519507", "Danny", "12345", "54321", "1"));

     }
}

package com.owd.jobs.jobobjects.billing.BulkySkuBilling;

import com.owd.LogableException;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by danny on 4/14/2018.
 */
public class BulkySkuBillingUtilities {


    public static void main(String[] args){


        processDailyBulkySkus("2018-04-13");


    }

    private final static Logger log = LogManager.getLogger();


    /**
     * Function to process bulky sku billing for the shipped date provided.
     * Current bulky sku orders are looked up and the relevant billing line is updated with info.
     * @param shippedOn String representation of date '2018-04-14'
     * */

    public static void processDailyBulkySkus(String shippedOn ){


        String sql = "SELECT\n" +
                "    dbo.sp_bill_pickpack.id,\n" +
                "    dbo.sp_bill_pickpack.oid\n" +
                "FROM\n" +
                "    dbo.sp_bill_pickpack\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.sp_bill_pickpack.oid = dbo.owd_order.order_id)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_line_item\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_line_item.order_fkey)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\n" +
                "WHERE\n" +
                "dbo.sp_bill_pickpack.shipped_on = :shippedOn \n" +
                "group by dbo.sp_bill_pickpack.id,\n" +
                "    dbo.sp_bill_pickpack.oid\n" +
                "\n" +
                "Having Max(CASE when  dbo.owd_inventory.bulky_pick_override = 0 AND dbo.owd_inventory.is_bulky_pick = 1 then 1 else 0 end) = 1\n" +
                "or Max(CASE when  dbo.owd_inventory.bulky_pack_override = 0 AND dbo.owd_inventory.is_bulky_pack = 1 then 1 else 0 end) = 1";

        try{
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("shippedOn",shippedOn);
            List l = q.list();
            log.debug("Size  "+l.size());
            for(Object row:l){
                Object[] data = (Object[]) row;
                processOrderRecord(data[0].toString(), data[1].toString());
            }

        }catch (Exception e){

            e.printStackTrace();

        }





    }

    public static void processOrderRecord(String billingId, String orderId){

        try{
            String sql = "execute getBulkyUnitsAndFeesForOrderId :orderId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
            log.debug("Getting data for order id"+ orderId);
            List l = q.list();
            if(l.size()>0) {
                Object[] data = (Object[]) l.get(0);
                updateRecord(billingId,data[0].toString(), data[1].toString(),data[2].toString(),data[3].toString());


            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public static boolean updateRecord(String billingId, String pickUnits, String pickFees, String packUnits, String packFees) throws Exception{
        boolean success = false;
        String sql = "update sp_bill_pickpack set bulky_pick_units = :pickUnits, bulky_pick_fees = :pickFees, bulky_pack_units = :packUnits, bulky_pack_fees = :packFees where id = :billingId";
       try{
         Query q = HibernateSession.currentSession().createSQLQuery(sql);
           q.setParameter("pickUnits",pickUnits);
           q.setParameter("pickFees",pickFees);
           q.setParameter("packUnits",packUnits);
           q.setParameter("packFees",packFees);
           q.setParameter("billingId",billingId);
           int i = q.executeUpdate();
           log.debug("Updated : " + i);
           HibUtils.commit(HibernateSession.currentSession());

       }catch (Exception e){
           e.printStackTrace();
       }


        return success;

    }

}

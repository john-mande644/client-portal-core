package com.owd.alittlePlaying.inventory.FixingInventory;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

public class getBoxType {

    public static void main(String[] args){
        try {
            long start = System.currentTimeMillis();

            String sql = "select inventory_id from owd_inventory where is_active=1 and client_fkey in (\n" +
                    "    select client_id from owd_client where owd_client.is_active=1\n" +
                    ")";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List inventory = q.list();
            int total = inventory.size();
            int count = 1;
            for(Object num_obj: inventory){
                System.out.println(count++ + " of " + total + "(" + (count / total)*1000 + "%) in " +
                        (Float) ((System.currentTimeMillis() - start) / 1000f) + " Seconds" );
                Integer num = (Integer) num_obj;
                System.out.println(num);
                String sql2 = "SELECT top 1 packaging_type, description\n" +
                        "FROM (\n" +
                        "         SELECT count(order_id) AS line_count,\n" +
                        "                dbo.owd_order.client_fkey,\n" +
                        "                order_id,\n" +
                        "                dbo.owd_line_item.inventory_id,\n" +
                        "                owd_order.created_date,\n" +
                        "                owd_boxtypes.packaging_type,\n" +
                        "                convert(varchar(200),owd_boxtypes.description) as description\n" +
                        "         FROM dbo.owd_order\n" +
                        "                  JOIN dbo.owd_line_item\n" +
                        "                       ON dbo.owd_order.order_id = dbo.owd_line_item.order_fkey\n" +
                        "                JOIN dbo.package_order\n" +
                        "                    ON owd_order.order_id = package_order.owd_order_fkey\n" +
                        "                join dbo.package\n" +
                        "                    ON package.package_order_fkey = package_order.id\n" +
                        "                join owd_boxtypes\n" +
                        "                    on package.owd_boxtypes_fkey = owd_boxtypes.id\n" +
                        "         WHERE dbo.owd_order.order_status = 'Shipped'\n" +
                        "           AND dbo.owd_line_item.quantity_actual = 1\n" +
                        "           and owd_line_item.inventory_id = :inventoryId\n" +
                        "         GROUP BY order_id, dbo.owd_order.client_fkey, dbo.owd_line_item.inventory_id,\n" +
                        "                  owd_order.created_date, packaging_type, convert(varchar(200),owd_boxtypes.description)\n" +
                        "     ) s\n" +
                        "WHERE s.line_count = 1\n" +
                        "order by created_date desc";
                Query q2 = HibernateSession.currentSession().createSQLQuery(sql2).addEntity(ResultObj.class);
                q2.setParameter("inventoryId", num);
                List result = (List<ResultObj>) q2.list();
                for(Object item: result){
                    System.out.println(item);
                }


            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class ResultObj extends Object{
    String packaging_type = "";
    String description = "";

    ResultObj(Object data){

    }
}
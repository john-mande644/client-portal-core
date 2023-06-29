package com.owd.dc.warehouse.misc.warehouseInfo;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/20/14
 * Time: 9:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class warehouseInfoUtils {

    public static void main(String[] args){
        try{
           /*   List<slaBreakDownBean> slas = loadSlaListForFacility("489","DC6");
            for(slaBreakDownBean sb :slas){
                System.out.println(sb.getClient() + " " + sb.getCount() + " " + sb.getSla());
            }

            Map<String,String> clients = getClientsWithOrdersAtWarehouse("DC1");
            for(String s :clients.keySet()){
                System.out.println(s);
            }*/
            System.out.println(getGroupNamesWithOrdersAtWarehouse("DC1"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static List<skuInfoBean> getSkuInfoForClientAtFacility(String facility, String clientId, String groupName, String caseQty, String palletQty) throws Exception{
        List<skuInfoBean> skus = new ArrayList<skuInfoBean>();
           StringBuffer sql = new StringBuffer();
        sql.append("SELECT\n" +
                "    owd_line_item.inventory_id,\n" +
                "    owd_line_item.inventory_num,\n" +
                "owd_inventory.description,\n" +
                "    SUM(quantity_actual)        AS units,\n" +
                "    COUNT(DISTINCT(order_id))   AS orders,\n" +
                "    SUM(quantity_actual)/:palletQty AS pallets,\n" +
                "    SUM(quantity_actual)/:caseQty     AS cases\n" +
                "FROM\n" +
                "    owd_order\n" +
                "JOIN owd_line_item\n" +
                "ON\n" +
                "    order_id=order_fkey\n" +
                "join owd_inventory\n" +
                "on owd_line_item.inventory_id = owd_inventory.inventory_id\n" +
                "AND order_id IN\n" +
                "    (\n" +
                "        SELECT DISTINCT\n" +
                "            order_id \n" +
                "        FROM\n" +
                "            owd_order o\n" +
                "        JOIN owd_line_item l\n" +
                "        ON\n" +
                "            order_id=order_fkey\n" +
                "        WHERE\n" +
                "            ");
                if(groupName.length()>1){
                    sql.append("group_name=:groupName and");
                }
        sql.append(" client_fkey=:clientId\n" +
                "        AND order_status='At Warehouse' and pick_status = 0\n" +
                "        AND order_id IN\n" +
                "            (\n" +
                "                SELECT\n" +
                "                    order_id\n" +
                "                FROM\n" +
                "                    owd_order\n" +
                "                JOIN owd_line_item\n" +
                "                ON\n" +
                "                    order_id=order_fkey\n" +
                "                WHERE\n" +
                "                    order_status='At Warehouse'\n" +
                "                AND facility_code=:facility\n" +
                "                AND pick_status=0\n" +
                "            )\n" +
                "    )\n" +
                "GROUP BY\n" +
                "    owd_line_item.inventory_id,\n" +
                "    owd_line_item.inventory_num,\n" +
                "owd_inventory.description order by units desc");
          Query q = HibernateSession.currentSession().createSQLQuery(sql.toString());
        q.setParameter("palletQty",palletQty);
        q.setParameter("caseQty",caseQty);
        q.setParameter("clientId",clientId);
        q.setParameter("facility",facility);
        if(groupName.length()>1){
            q.setParameter("groupName",groupName);
        }
        List results = q.list();
        for(Object row:results){
            Object[] data = (Object[]) row;
            skuInfoBean sib = new skuInfoBean();
            sib.setInventoryId(data[0].toString());
            sib.setInventoryNum(data[1].toString());
            sib.setDescription(data[2].toString());
            sib.setUnits(data[3].toString());
            sib.setOrders(data[4].toString());
            sib.setPallets(data[5].toString());
            sib.setCases(data[6].toString());
           skus.add(sib);
        }



        return skus;
    }

    public static List<String> getGroupNamesWithOrdersAtWarehouse(String facility) throws Exception{
        List<String> groups = new ArrayList<String>();
              String sql = "select group_name\n" +
                      "from owd_order where order_status = 'At warehouse' and is_void = 0\n" +
                      "and facility_code = :facility and shipped_on IS NULL and group_name is not null\n" +
                      "group by group_name\n";
              Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        groups = q.list();


        return groups;
    }
    public static Map<String,String> getClientsWithOrdersAtWarehouse(String facility) throws Exception{
        Map<String,String> clients = new TreeMap<String, String>();
             String sql = "SELECT\n" +
                     "    dbo.owd_client.client_id,\n" +
                     "    dbo.owd_client.company_name\n" +
                     "FROM\n" +
                     "    dbo.owd_order\n" +
                     "INNER JOIN dbo.owd_client\n" +
                     "ON\n" +
                     "    (\n" +
                     "        dbo.owd_order.client_fkey = dbo.owd_client.client_id\n" +
                     "    )\n" +
                     "WHERE\n" +
                     "    dbo.owd_order.order_status = 'At warehouse'\n" +
                     "AND dbo.owd_order.is_void = 0\n" +
                     "AND facility_code = :facility\n" +
                     "AND dbo.owd_order.shipped_on IS NULL \n" +
                     "group by client_id, company_name order by company_name;" ;
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        List results = q.list();
        for(Object row:results){
            Object[] data = (Object[]) row;
            clients.put(data[1].toString(),data[0].toString());
        }
        return clients;
    }
    public static List<slaBreakDownBean> loadSlaListForFacility(String clientId,String facility) throws Exception{
       List<slaBreakDownBean> slas = new ArrayList<slaBreakDownBean>();
       String sql = "WITH subs (orderId,subclient,sla) as (SELECT \n" +
               "\n" +
               "             dbo.owd_order.order_id,\n" +
               "               Max(dbo.owd_inventory.mfr_part_num) as subclient,\n" +
               "            RIGHT(customer_vendor_no,19) as sla\n" +
               "            \n" +
               "            FROM\n" +
               "                dbo.owd_order\n" +
               "            INNER JOIN dbo.owd_line_item\n" +
               "            ON\n" +
               "                (\n" +
               "                    dbo.owd_order.order_id = dbo.owd_line_item.order_fkey\n" +
               "                )\n" +
               "            INNER JOIN dbo.owd_inventory\n" +
               "            ON\n" +
               "                (\n" +
               "                    dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id\n" +
               "                )\n" +
               "            WHERE\n" +
               "                dbo.owd_order.order_status = 'At Warehouse'\n" +
               "            AND dbo.owd_order.client_fkey = :clientId and facility_code = :facility \n" +
               "            group by order_id, RIGHT(customer_vendor_no,19) )\n" +
               "            select count(orderId), subclient,sla from subs group by subclient,sla\n" +
               "            order by subclient,sla";
         Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        q.setParameter("clientId",clientId);
        List results = q.list();
        for(Object row:results){
            slaBreakDownBean sb = new slaBreakDownBean();
            Object[] data = (Object[]) row;
            sb.setCount(data[0].toString());
            sb.setClient(data[1].toString());
            sb.setSla(data[2].toString());
            slas.add(sb);
        }





        return slas;
    }
    public static List<slaBreakDownBean> loadUnpickedSlaListForFacility(String clientId,String facility) throws Exception{
        List<slaBreakDownBean> slas = new ArrayList<slaBreakDownBean>();
        String sql = "WITH subs (orderId,subclient,sla) as (SELECT \n" +
                "\n" +
                "             dbo.owd_order.order_id,\n" +
                "               Max(dbo.owd_inventory.mfr_part_num) as subclient,\n" +
                "            RIGHT(customer_vendor_no,19) as sla\n" +
                "            \n" +
                "            FROM\n" +
                "                dbo.owd_order\n" +
                "            INNER JOIN dbo.owd_line_item\n" +
                "            ON\n" +
                "                (\n" +
                "                    dbo.owd_order.order_id = dbo.owd_line_item.order_fkey\n" +
                "                )\n" +
                "            INNER JOIN dbo.owd_inventory\n" +
                "            ON\n" +
                "                (\n" +
                "                    dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id\n" +
                "                )\n" +
                "            WHERE\n" +
                "                dbo.owd_order.order_status = 'At Warehouse'\n" +
                "            AND dbo.owd_order.client_fkey = :clientId and facility_code = :facility and pick_status = 0\n" +
                "            group by order_id, RIGHT(customer_vendor_no,19) )\n" +
                "            select count(orderId), subclient,sla from subs group by subclient,sla\n" +
                "            order by subclient,sla";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        q.setParameter("clientId",clientId);
        List results = q.list();
        for(Object row:results){
            slaBreakDownBean sb = new slaBreakDownBean();
            Object[] data = (Object[]) row;
            sb.setCount(data[0].toString());
            sb.setClient(data[1].toString());
            sb.setSla(data[2].toString());
            slas.add(sb);
        }





        return slas;
    }
}

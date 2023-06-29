package com.owd.dc.warehouse.vendorCompliance.labelsMaker;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by danny on 10/19/2016.
 */
public class labelMakerUtils {

    public static void main(String args[]){
        try{
            System.out.println(loadVendors());
            System.out.println(loadClients("1"));
            System.out.println(getPrinterMapForFacilitySize("",""));
            System.out.println(jsonUtilties.getJsonFromObject(getSkusForClientAndVendor("489","1")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Map loadClients(String vendorId) throws Exception{
        Map<String,Integer> clients = new TreeMap<String,Integer>();
        String sql = "SELECT\n" +
                "    MAX(dbo.owd_inventory_sku_maps.client_fkey) as id,\n" +
                "    case when len(owd_inventory_sku_maps.group_name) > 0  then\n" +
                "    dbo.owd_client.company_name +'/'+owd_inventory_sku_maps.group_name\n" +
                "    else\n" +
                "    \n" +
                "    dbo.owd_client.company_name\n" +
                "    end\n" +
                "FROM\n" +
                "    dbo.owd_inventory_sku_maps\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory_sku_maps.client_fkey = dbo.owd_client.client_id)\n" +
                "WHERE\n" +
                "    dbo.owd_inventory_sku_maps.vendor_compliance_fkey = :vendorId\n" +
                "GROUP BY\n" +
                "    dbo.owd_client.company_name,owd_inventory_sku_maps.group_name ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("vendorId",vendorId);
        List l = q.list();
        if(l.size()>0){
            for(Object row:l){
                Object[] data = (Object[]) row;
                clients.put(data[1].toString(),Integer.parseInt(data[0].toString()));
            }
        }


        return clients;
    }
    public static Map loadVendors() {
        Map<String,Integer> vendors = new TreeMap<String,Integer>();

        try {
            String sql = "SELECT\n" +
                    "    MAX(dbo.owd_order_vendor_compliance.id),\n" +
                    "    dbo.owd_order_vendor_compliance.vc_name\n" +
                    "FROM\n" +
                    "    dbo.owd_order_vendor_compliance\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_inventory_sku_maps\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order_vendor_compliance.id = dbo.owd_inventory_sku_maps.vendor_compliance_fkey)\n" +
                    "GROUP BY\n" +
                    "    dbo.owd_order_vendor_compliance.vc_name ;";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();

            if (l.size() > 0) {
                for (Object row : l) {
                    Object[] data = (Object[]) row;
                    vendors.put(data[1].toString(), Integer.parseInt(data[0].toString()));
                    System.out.println("Added: " + data[1].toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return vendors;
    }
    public static List<skuBean> getSkusForClientAndVendor(String clientId, String vendorComplianceId){
        List<skuBean> skus = new ArrayList<skuBean>();
        try{
            String sql = "SELECT\n" +
                    "    dbo.owd_inventory.inventory_id,\n" +
                    "    dbo.owd_inventory_sku_maps.id,\n" +
                    "    dbo.owd_inventory.inventory_num,\n" +
                    "    dbo.owd_inventory_sku_maps.foreign_sku,\n" +
                    "    dbo.owd_inventory_sku_maps.foreign_desc\n" +
                    "FROM\n" +
                    "    dbo.owd_inventory_sku_maps\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_inventory\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_inventory_sku_maps.client_fkey = dbo.owd_inventory.client_fkey)\n" +
                    "AND (\n" +
                    "        dbo.owd_inventory_sku_maps.owd_sku = dbo.owd_inventory.inventory_num)\n" +
                    "WHERE\n" +
                    "    dbo.owd_inventory_sku_maps.client_fkey = :clientId\n" +
                    "AND dbo.owd_inventory_sku_maps.vendor_compliance_fkey = :vendorComplianceId ;";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("vendorComplianceId",vendorComplianceId);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            List results = q.list();
            for (Object row : results) {
                Map data = (Map) row;
                skuBean s = new skuBean();
                s.setOwdId(data.get("inventory_id").toString());
                s.setMapId(data.get("id").toString());
                s.setOwdSku(data.get("inventory_num").toString());
                s.setVendorSku(data.get("foreign_sku").toString());
                s.setVendorDesc(data.get("foreign_desc").toString());
                skus.add(s);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return skus;
    }
    public static Map<String,String> getPrinterMapForFacilitySize(String facility, String size){

        Map<String,String> printers = new TreeMap<String, String>();

        try{
        String sql = "select display,value from app_data where project = 'wms' and description = 'labelMaker' and variable = 'printer' and (value like '%dc5%' or value like '%dc6%') and display like '%small%'";
         Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            if (l.size() > 0) {
                for (Object row : l) {
                    Object[] data = (Object[]) row;
                    printers.put(data[1].toString(), data[0].toString());
                    System.out.println("Added: " + data[1].toString());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return printers;



    }

}

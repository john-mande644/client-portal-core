package com.owd.dc.warehouse.cycleCountSheets;

import com.owd.WMSUtils;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.LotManager;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 6/16/14
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class cycleCountSheetUtils {

    public static void main(String[] args){
        try{
            //  cycleCountSheetData data = loadSheetDataClientDc("626","DC7","","","",new ArrayList<String>());
           // cycleCountSheetData data = loadSheetDataInventoryNum("55", "DC6", "Ebay Items", new ArrayList<String>());
             cycleCountSheetData data = loadSheetDataGroup("55", "DC1", "");
          //  cycleCountSheetData data = loadSheetDataInventoryId("55", "DC1", "121672");
           /* System.out.println(data.getClient());
            System.out.println(data.getInventory().size());
            for(cycleCountInv inv:data.getInventory().values()){
                System.out.print(inv.getInvNumber()+"\t");
                System.out.print(inv.getDesc() +"\t");
                System.out.print(inv.getQty()+"\t");
                System.out.println(inv.getLocation());
                for(String s : inv.getLots().keySet()){
                    System.out.println(s +": " +inv.getLots().get(s));
                }
            }*/
           // System.out.println(getGroups("489","DC6"));
         /*   List<cycleCountInv> l = getSkus("489", "DC1");
            System.out.println("lala");
            System.out.println(l.size());
            System.out.println(getSkuString(l));*/

          //  System.out.println(seachSkus("55","DC1","y"));
        }catch(Exception e){
            e.printStackTrace();
        }



    }
    public static List<String> seachSkus(String clientId, String facility, String searchString) throws Exception{
          String sql = "select inventory_num from owd_inventory where client_fkey = :clientId  and inventory_num like :searchString and is_active = 1";
      List<String> l = new ArrayList<String>();
         Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId",clientId);

        q.setParameter("searchString","%"+searchString+"%");

       List resutls = q.list();
        for(Object data:resutls){
            l.add(data.toString());
        }

       return l;
    }
    public static List<String> getGroups(String clientId, String facility) throws Exception{
        List<String> groups = new ArrayList<String>();
        String sql =  "select group_name from owd_inventory where is_active = 1 and client_fkey = :clientId  group by group_name ";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId",clientId);

        List results = q.list();
        for(Object row:results){
         try{
             if(row.toString().length()==0){
              groups.add("None");
             }  else{
           groups.add(row.toString());
             }
         }catch (Exception e){

         }
        }
        return groups;
    }
    public static String getSkuString(List<cycleCountInv> skus){
        StringBuilder s = new StringBuilder();
        int i = 1;
        for (cycleCountInv inv:skus){
            s.append("\"");
            s.append(inv.getInvNumber().replace("\"","\\\""));
            s.append("\"");
            if(i<skus.size()){
                s.append(",");
            }
            i++;

        }
        return s.toString();
    }

    public static List<cycleCountInv> getSkus(String clientId, String facility) throws Exception{
        List<cycleCountInv> skus = new ArrayList<cycleCountInv>();
          String sql =  "select inventory_num, inventory_id, description from owd_inventory where is_active = 1 and client_fkey = :clientId  order by inventory_num";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId",clientId);

        List results = q.list();
        for(Object row:results){
               cycleCountInv cci = new cycleCountInv();
            Object [] data = (Object[]) row;
            cci.setInvNumber(data[0].toString());
            cci.setId(data[1].toString());
            cci.setDesc(data[2].toString());

            skus.add(cci);
        }
       return skus;
    }
    public static List<String> getFacilites() throws Exception{
        List<String> facilities = new ArrayList<String>();
                          String sql = "select loc_code from owd_facilities where is_active = 1 and loc_name <>'TEST'";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List results = q.list();
        for(Object row:results){

            facilities.add(row.toString());
        }
        return facilities;

    }
    public static Map<String,String> getClients() throws Exception{
                Map<String,String> clients = new TreeMap<String, String>();
               String sql = "select client_id, company_name from owd_client where is_active = 1";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List results = q.list();
        for(Object row:results){
            Object[] data = (Object[])row;
            clients.put(data[0].toString(),data[1].toString());
        }

        return clients;
    }
    private static String getDateLoadedString(){
        SimpleDateFormat dff = new SimpleDateFormat("HH:mm MM-dd-yyyy");
        Calendar cal = Calendar.getInstance();
        return dff.format(cal.getTime());
    }

    public static cycleCountSheetData loadSheetDataClientDc(String clientId, String facility,String group,String inventoryNumList, String inventoryIdList, List<String> inventoryNumMulti) throws Exception {
            cycleCountSheetData data = new cycleCountSheetData();
                    if(inventoryIdList.equals("")& inventoryNumList.equals("")&inventoryNumMulti.size()==0){
                       data = loadSheetDataGroup(clientId,facility,group);
                    }
        if(inventoryNumList.length()>0||inventoryNumMulti.size()>0){
                   System.out.println("Doing num list");
           data =  loadSheetDataInventoryNum(clientId,facility,inventoryNumList,inventoryNumMulti);
        }
        if(inventoryIdList.length()>0){
            System.out.println("Doing id list");
            data =  loadSheetDataInventoryId(clientId, facility, inventoryIdList);
        }


        return data;
    }
   public static cycleCountSheetData loadSheetDataGroup(String clientId, String facility,String group) throws Exception {
          System.out.println("Doing cycle count");
         cycleCountSheetData data = new cycleCountSheetData();
       data.setDate(getDateLoadedString());
         String sql = "select company_name from owd_client where client_id = :id";
       Query q = HibernateSession.currentSession().createSQLQuery(sql);
       q.setParameter("id",clientId);
       List results = q.list();
       if(results.size()>0){
            data.setClient(results.get(0).toString());
       } else{
           throw new Exception("Client not found, that is silly");
       }
       String sql2 = "SELECT\n" +
                   "dbo.totalInventoryOhFacility(dbo.owd_inventory.inventory_id,:fac), \n" +
                   "    dbo.owd_inventory.inventory_id as theId,\n" +
                   "    dbo.owd_inventory.inventory_num,\n" +
                   "    dbo.owd_inventory.description,\n" +
                   "ISNULL(dbo.owd_inventory.harm_code,'') as harm,isnull(upc_code,'')as upc"+
                   "\n" +
                   "\n" +
                   "FROM\n" +
                   "    dbo.owd_inventory\n" +

                   "WHERE\n";
                 if(group.length()==0||group.equals("None")){
                     sql2 = sql2+"    client_fkey = :id and is_active = 1 \n";
                 } else{
                     sql2 = sql2+"    client_fkey = :id and is_active = 1  and group_name = :group\n";
                 }

         sql2 = sql2 +          "ORDER BY\n" +
                   "    dbo.owd_inventory.inventory_num ASC\n" ;


           Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
           qq.setParameter("id",clientId);
           qq.setParameter("fac",facility);
       if(group.length()>0){
           qq.setParameter("group",group);
       }
           List theresults = qq.list();
       System.out.println("We got this many: "+theresults.size());
           if(theresults.size()>0){

               loadResultsIntoCycleCountSheetData(data,theresults,facility);


       } else{
           throw new Exception("No sku's found to count");
       }

       return data;
   }
    public static cycleCountSheetData loadSheetDataInventoryNum(String clientId, String facility,String inventoryNum,List<String> inventoryNumMulti) throws Exception {
        System.out.println("Doing cycle count with Nums");
        List nums = new ArrayList();
        if(inventoryNumMulti.size()>0){
           nums = inventoryNumMulti;
        } else{
            nums = WMSUtils.splitStringIntoList(inventoryNum);
        }
        String ixParent = FacilitiesManager.getFacilityForCode(facility).getWlocNodeFkey()+"";
        cycleCountSheetData data = new cycleCountSheetData();
        data.setDate(getDateLoadedString());
        String sql = "select company_name from owd_client where client_id = :id";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id",clientId);
        List results = q.list();
        if(results.size()>0){
            data.setClient(results.get(0).toString());
            System.out.println(data.getClient());
        } else{
            throw new Exception("Client not found, that is silly");
        }
        String sql2 = "SELECT\n" +
                "dbo.totalInventoryOhFacility(dbo.owd_inventory.inventory_id,:fac), \n" +
                "    dbo.owd_inventory.inventory_id as theId,\n" +
                "    dbo.owd_inventory.inventory_num,\n" +
                "    dbo.owd_inventory.description,\n" +
                "dbo.owd_inventory.harm_code,isnull(upc_code,'')as upc \n" +
                "FROM\n" +
                "    dbo.owd_inventory\n" +
                "\n" +
                "WHERE";

            sql2 = sql2+"    client_fkey = :id and is_active = 1 and inventory_num in ( :inventory ) ";


        sql2 = sql2 +          "ORDER BY\n" +
                "    dbo.owd_inventory.inventory_num ASC\n";


        Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
        qq.setParameter("id",clientId);
        qq.setParameter("fac",facility);
        qq.setParameterList("inventory",nums);
        List theresults = qq.list();
        System.out.println("We got this many: "+theresults.size());
        if(theresults.size()>0){

            loadResultsIntoCycleCountSheetData(data,theresults,facility);


        } else{
            throw new Exception("No sku's found to count");
        }

        return data;
    }
    public static cycleCountSheetData loadSheetDataInventoryId(String clientId, String facility,String inventoryId) throws Exception {
        System.out.println("Doing cycle count with ID");
        List nums = WMSUtils.splitStringIntoList(inventoryId);
        cycleCountSheetData data = new cycleCountSheetData();
        data.setDate(getDateLoadedString());
        String sql = "select company_name from owd_client where client_id = :id";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id",clientId);
        List results = q.list();
        if(results.size()>0){
            data.setClient(results.get(0).toString());
            System.out.println(data.getClient());
        } else{
            throw new Exception("Client not found, that is silly");
        }
        String sql2 = "SELECT\n" +
                "dbo.totalInventoryOhFacility(dbo.owd_inventory.inventory_id,:facility), \n" +
                "    dbo.owd_inventory.inventory_id as theId,\n" +
                "    dbo.owd_inventory.inventory_num,\n" +
                "    dbo.owd_inventory.description,\n" +
                "dbo.owd_inventory.harm_code,isnull(upc_code,'')as upc"+
                "\n" +
                "\n" +
                "FROM\n" +
                "    dbo.owd_inventory\n" +

                "WHERE\n";

        sql2 = sql2+"    client_fkey = :id and is_active = 1  and inventory_id in ( :inventory )\n";


        sql2 = sql2 +          "ORDER BY\n" +
                "    dbo.owd_inventory.inventory_num ASC";

        Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
        qq.setParameter("id",clientId);
        qq.setParameter("facility",facility);

        qq.setParameterList("inventory",nums);
        List theresults = qq.list();
        System.out.println("We got this many: "+theresults.size());
        if(theresults.size()>0){

            loadResultsIntoCycleCountSheetData(data,theresults,facility);


        } else{
            throw new Exception("No sku's found to count");
        }

        return data;
    }
    public static List<String> getLocationPickStringsForInventoryIdAndFacility(String inventoryId,String facilityCode) throws Exception{
        List<String> locs = new ArrayList<String>();
           String sql = "SELECT\n" +
                   "    dbo.w_location.pickString\n" +
                   "FROM\n" +
                   "    dbo.owd_inventory_locations\n" +
                   "LEFT OUTER JOIN dbo.w_location\n" +
                   "ON\n" +
                   "    (\n" +
                   "        CASE\n" +
                   "\n" +
                   "                    when location = 'UNKNOWN' then 1\n" +
                   "                          else CAST(Replace(dbo.owd_inventory_locations.location,'//','') as int)\n" +
                   "                    end = dbo.w_location.ixNode\n" +
                   "    )\n" +
                   "INNER JOIN dbo.w_location_tree\n" +
                   "ON\n" +
                   "    (\n" +
                   "        dbo.w_location.ixNode = dbo.w_location_tree.ixNode\n" +
                   "    )\n" +
                   "WHERE\n" +
                   "    dbo.w_location_tree.ixParent = :locNode\n" +
                   "AND dbo.owd_inventory_locations.inventory_fkey = :inventoryId  order by sortString ;";
        String locNode = FacilitiesManager.getFacilityForCode(facilityCode).getWlocNodeFkey()+"";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("locNode",locNode);
        q.setParameter("inventoryId",inventoryId);
        List results = q.list();
        for(Object row:results){
            locs.add(row.toString().replace("<br>"," "));
        }
       if(locs.size()==0){
           locs.add("No Locations Found");
       }
        return locs;
    }
    public static void loadResultsIntoCycleCountSheetData(cycleCountSheetData data, List theresults,String facilityCode){
        for(Object row:theresults){
            Object[] inv = (Object[]) row;
            //System.out.println("Start");
           // System.out.println(inv[2].toString());




                cycleCountInv cInv = new cycleCountInv();

                cInv.setId(inv[1].toString());

                cInv.setInvNumber(inv[2].toString());
               // System.out.println("3");
                cInv.setDesc(inv[3].toString());
               // System.out.println("4");
                cInv.setSupplier(inv[4].toString());
               // System.out.println("5");
                cInv.setUpc(inv[5].toString());


                try{
                 // System.out.println("getting locations");
                  List<String> locs = getLocationPickStringsForInventoryIdAndFacility(cInv.getId(),facilityCode);
                    cInv.getLocation().addAll(locs);
                }catch(Exception e){
                    e.printStackTrace();
                    cInv.getLocation().add("No Known Locations");
                }

              //  System.out.println("6");
                cInv.setQty(inv[0].toString());
            try {
                cInv.setLots(LotManager.getLotValuesAndQuantitiesForInventoryId(Integer.parseInt(cInv.getId()), FacilitiesManager.getFacilityForCode(facilityCode).getId(),true));
            }catch (Exception e){
                e.printStackTrace();
            }
                //System.out.println("7");
           /* if(Integer.parseInt(cInv.getQty())>0 && !cInv.getLocation().get(0).equals("No Locations Found")) {
                data.getInventory().put(cInv.getInvNumber(), cInv);
            }*/
            data.getInventory().put(cInv.getInvNumber(), cInv);



        }
    }
}

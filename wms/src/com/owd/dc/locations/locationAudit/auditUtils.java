package com.owd.dc.locations.locationAudit;

import com.owd.core.managers.LotManager;
import com.owd.dc.HandheldUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/6/14
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class auditUtils {


    public static List<String> getInventoryIdsInLocation(String locationId) throws Exception{
        List<String> ids = new ArrayList<String>();
        String sql = "select inventory_fkey from owd_inventory_locations where location = :location group by inventory_fkey";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("location",locationId);
        List results = q.list();
        for(Object row:results){
            ids.add(row.toString());
        }

      return ids;
    }

    public static boolean assignInventoryToLocation(String ixNode,String employeeId,String inventoryId, String facilityCode) throws Exception{
        Boolean worked = false;
        System.out.println("Doing assign");
        if(LotManager.isInventoryIdLotControlled(new Integer(inventoryId))){
            throw new Exception("You cannot assign lot controlled items using this function");
        }
        HandheldUtilities.assignLocation("//"+ixNode, Integer.parseInt(inventoryId), "locationAudit", "","");
        System.out.println("Inset audit record");
        insertAuditRecordAdd(ixNode, employeeId, inventoryId, facilityCode);


        return worked;
    }
public static boolean removeAssignedLocation(String ixNode,String emplyeeId, String inventoryId,String facilityCode) throws Exception{

    Boolean worked = false;
    String sql = "delete from owd_inventory_locations where location = :location and inventory_fkey = :inventoryId";
    Query q = HibernateSession.currentSession().createSQLQuery(sql);
    q.setParameter("location", "//" + ixNode);
    q.setParameter("inventoryId", inventoryId);
    int rows = q.executeUpdate();
    if (rows>0){
        worked = true;
        System.out.println("insert removed record");

        insertAuditRecordRemove(ixNode, emplyeeId, inventoryId, facilityCode);

    }


    return worked;
}
    public static boolean insertAuditRecordNeutral(String ixNode, String employeeId, String inventoryId, String facilityCode) throws Exception{
          Boolean worked = false;
              try{
             insertAuditRecord(ixNode,employeeId,inventoryId,"0",facilityCode);
              } catch(Exception e){
                  e.printStackTrace();
              }

        return worked;
    }
    public static boolean insertAuditRecordAdd(String ixNode, String employeeId, String inventoryId, String facilityCode) throws Exception{
        Boolean worked = false;
        try{
            insertAuditRecord(ixNode,employeeId,inventoryId,"1",facilityCode);
        } catch(Exception e){
            e.printStackTrace();
        }

        return worked;
    }
    public static boolean insertAuditRecordRemove(String ixNode, String employeeId, String inventoryId, String facilityCode) throws Exception{
        Boolean worked = false;
        try{
            insertAuditRecord(ixNode,employeeId,inventoryId,"2",facilityCode);
        } catch(Exception e){
            e.printStackTrace();
        }

        return worked;
    }

        private static void insertAuditRecord(String ixNode, String employeeId, String inventoryId, String status, String facilityCode) throws Exception{
       Query q;
        if(null== inventoryId || "".equals(inventoryId)){
            String sql = "INSERT INTO\n" +
                    "    dbo.w_location_audits\n" +
                    "    (\n" +
                    "       \n" +
                    "        w_loc_ixNode,\n" +
                    "        employeeId,\n" +

                    "        status,\n" +
                    "        facilityCode\n" +
                    "    )\n" +
                    "    VALUES\n" +
                    "    (\n" +
                    "        \n" +
                    "       :ixNode,\n" +
                    "        :employeeId,\n" +

                    "        :status,\n" +
                    "        :facilityCode\n" +
                    "    )";
                 q= HibernateSession.currentSession().createSQLQuery(sql);

        } else{
           String sql2 = "INSERT INTO\n" +
                   "    dbo.w_location_audits\n" +
                   "    (\n" +
                   "       \n" +
                   "        w_loc_ixNode,\n" +
                   "        employeeId,\n" +
                   "        inventoryId,\n" +
                   "        status,\n" +
                   "        facilityCode\n" +
                   "    )\n" +
                   "    VALUES\n" +
                   "    (\n" +
                   "        \n" +
                   "       :ixNode,\n" +
                   "        :employeeId,\n" +
                   "        :inventoryId,\n" +
                   "        :status,\n" +
                   "        :facilityCode\n" +
                   "    )";
            q = HibernateSession.currentSession().createSQLQuery(sql2);
            q.setParameter("inventoryId",inventoryId);
        }
        q.setParameter("ixNode",ixNode);
        q.setParameter("employeeId",employeeId);
        q.setParameter("status",status);
        q.setParameter("facilityCode",facilityCode);
        q.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());


    }
public static String getLocationIdFromFacilityString(String facility) throws Exception{
    String sql ="select wloc_node_fkey from owd_facilities where loc_code = :facility";
    Query q = HibernateSession.currentSession().createSQLQuery(sql);
    q.setParameter("facility", facility);
    List results = q.list();
    if(results.size()>0){
        return results.get(0).toString();
    }else{
        throw new Exception("Unable to load the proper id for facility string "+facility);
    }


}

    public static Map<String,String> loadLocationDirectChildren(String parentId,boolean facility) throws Exception{
       Map<String,String> children = new TreeMap<String, String>();
        String sql;
        if(facility) {
            sql = "select ixNode, locname from w_location where ixParent = :parentId and ixLocType <> 10 order by locname";
        }else{
            sql = "select ixNode, locname from w_location where ixParent = :parentId order by locname";
        }
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("parentId",parentId);
        List results = q.list();
        for(Object row : results){
            Object[] data = (Object[]) row;
            children.put(data[1].toString(),data[0].toString());
        }

       return children;
    }
    public static String getNumberOfAssignedItemsInLocationTree(String ixNode) throws Exception{
      String s;
        String sql = "select count(id) from owd_inventory_locations \n" +
                "where location in(\n" +
                "select '//'+ convert(varchar(15),ixNode)\n" +
                " from w_location_tree where ixParent = :ixNode)";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("ixNode", ixNode);
        List results = q.list();
        if(results.size()>0){
            s = results.get(0).toString();
        }else{
            s = "0";
        }
return s;
    }


    public static String deleteAssignedInventoryFromAllChildren(String parentId)throws Exception{
        String sql = "delete from owd_inventory_locations where location in" +
                "(select '//' + convert(varchar(20),ixNode)" +
                "from w_location_tree where ixParent = :parentId) or location = '//' + :loc";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("parentId",parentId);
        q.setParameter("loc",parentId);
        int i = q.executeUpdate();
         HibUtils.commit(HibernateSession.currentSession());



        return i+"";

    }

    public static String getLastLocationAuditForFacility(String facility) throws Exception{
       String sql = "select top 1 w_loc_ixNode from w_location_audits where facilityCode = :facility" +
               " order by audit_time DESC";
        String s = "nadaa";

         Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        List results = q.list();
        if (results.size()>0){
            String id = results.get(0).toString();
            WLocation loc = (WLocation) HibernateSession.currentSession().load(WLocation.class,Integer.parseInt(id));
            s = loc.getPickString();

        }


        return s;
    }
    public static String getLastLocationAuditForFacilityAndID(String facility,String employeeId) throws Exception{
        String sql = "select top 1 w_loc_ixNode from w_location_audits where facilityCode = :facility" +
                " and employeeId = :employeeId order by audit_time DESC";
        String s = "nadaa";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        q.setParameter("employeeId",employeeId);
        List results = q.list();
        if (results.size()>0){
            String id = results.get(0).toString();
            WLocation loc = (WLocation) HibernateSession.currentSession().load(WLocation.class,Integer.parseInt(id));
            s = loc.getPickString();

        }


        return s;
    }
public static String getFacilityIxNodeFromLocation(String locationId) throws Exception{
    String sql = "SELECT\n" +
            "   \n" +
            "    dbo.w_location.ixParent\n" +
            "   \n" +
            "FROM\n" +
            "    dbo.w_location_tree\n" +
            "INNER JOIN\n" +
            "    dbo.w_location\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.w_location_tree.ixParent = dbo.w_location.ixNode)\n" +
            "WHERE\n" +
            "    dbo.w_location_tree.ixNode = :locationId\n" +
            "AND dbo.w_location.ixLocType = 6 ;";
    Query q = HibernateSession.currentSession().createSQLQuery(sql);
    q.setParameter("locationId",locationId);
    List results = q.list();
    if(results.size()>0){
        return results.get(0).toString();
    }
    throw new Exception("Unable to find proper Facility location id");
}

    public static void updateBinLocationParentIx(String ixNode, String newParentId)throws Exception{
       String sql = "update w_location set ixParent = :newParentId, pickString = null, formatedPickString = null, sortString = null" +
               " where ixNode in (" +
               "SELECT\n" +
               "    dbo.w_location.ixNode\n" +
               "   \n" +
               "FROM\n" +
               "    dbo.w_location_tree\n" +
               "INNER JOIN\n" +
               "    dbo.w_location\n" +
               "ON\n" +
               "    (\n" +
               "        dbo.w_location_tree.ixNode = dbo.w_location.ixNode)\n" +
               "WHERE\n" +
               "    dbo.w_location_tree.ixParent = :ixNode\n" +
               "AND dbo.w_location.ixLocType = 10" +
               ")" ;
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("ixNode",ixNode);
        q.setParameter("newParentId",newParentId);
        int i = q.executeUpdate();
                System.out.println(i +" is what we changed");
    }
    private static void locationTreeCleanup(String ixNode)throws Exception{
        String sql = "delete from w_location_tree where ixNode = :ixNode";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("ixNode",ixNode);
        int i = q.executeUpdate();
        System.out.println(i +" is what we changed");
    }
    public static void deleteLocationAndChildren(String ixNode)throws Exception{
        //first re-assign all bins to parent in child location to DC
        String facilityLocationId = getFacilityIxNodeFromLocation(ixNode);
        System.out.println("Facility locaiton Id: "+ facilityLocationId);
updateBinLocationParentIx(ixNode,facilityLocationId);

String sql = "delete from w_location where ixNode in ( " +
        "select ixNode from w_location_tree WHERE ixParent = :ixNode or (ixNode = :ixNode and ixParent is null)" +
        ")";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("ixNode",ixNode);
        int i = q.executeUpdate();
        System.out.println(i +" is what we changed");
        HibUtils.commit(HibernateSession.currentSession());
        locationTreeCleanup(ixNode);
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static List<auditDataBean> loadAuditNumbers(String date) throws Exception {
        List<auditDataBean> data = new ArrayList<auditDataBean>();
        String sql = "SELECT facilityCode,Count(status) as total,\n" +
                "    SUM(\n" +
                "        CASE\n" +
                "            WHEN status =0\n" +
                "            THEN 1\n" +
                "            ELSE 0 end\n" +
                "            ) as \"Good Scans\",\n" +
                "            SUM(\n" +
                "        CASE\n" +
                "            WHEN status =1\n" +
                "            THEN 1\n" +
                "            ELSE 0 end\n" +
                "            ) as \"Added\",\n" +
                "            SUM(\n" +
                "        CASE\n" +
                "            WHEN status =2\n" +
                "            THEN 1\n" +
                "            ELSE 0 end\n" +
                "            ) as \"Removed\"\n" +
                "FROM\n" +
                "    dbo.w_location_audits where audit_time > :time\n" +
                "    group by facilityCode;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("time",date);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List results = q.list();
        System.out.println("11");
        System.out.println(results.size());
        for (Object row : results) {
            Map info = (Map) row;
auditDataBean abean = new auditDataBean();
            abean.setFacility(info.get("facilityCode").toString());
            abean.setAdded(Integer.parseInt(info.get("Added").toString()));
            abean.setGood(Integer.parseInt(info.get("Good Scans").toString()));
            abean.setRemoved(Integer.parseInt(info.get("Removed").toString()));
            abean.setTotal(Integer.parseInt(info.get("total").toString()));

            data.add(abean);
        }





        return data;


    }
    public static List<auditDataBean> todaysNumbers() throws Exception{
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dayPart = formatter.format(Calendar.getInstance().getTime());
        System.out.print(dayPart);
        return loadAuditNumbers(dayPart);
    }
    public static List<auditDataBean> weeksNumbers() throws Exception{
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String dayPart = formatter.format(cal.getTime());
        System.out.print(dayPart);
        return loadAuditNumbers(dayPart);
    }
    public static List<auditDataBean> monthNumbers() throws Exception{
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-30);
        String dayPart = formatter.format(cal.getTime());
        System.out.print(dayPart);
        return loadAuditNumbers(dayPart);
    }
    public static void main(String[] args){

        try{
         //   deleteLocationAndChildren("44881");
            List<auditDataBean> numbers = monthNumbers();
            System.out.println(numbers.get(0).getTotal());
            System.out.println(numbers.get(0).getGood());
            System.out.println(numbers.get(0).getGoodPercent());
//System.out.println(loadAuditNumbers("2015-05-01"));
// insertAuditRecordNeutral("55378", "51", "36351", "DC1");
//System.out.println(getLocationIdFromFacilityString("DC1"));
  //          System.out.println(loadLocationDirectChildren("8",true));
    //System.out.println(getNumberOfAssignedItemsInLocationTree("36432"));
           // System.out.println(deleteAssignedInventoryFromAllChildren("55378"));
           //System.out.println(getInventoryIdsInLocation("//55378"));
           // System.out.println(getLastLocationAuditForFacility("DC1"));
            // System.out.println(getLastLocationAuditForFacilityAndID("DC1","437"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

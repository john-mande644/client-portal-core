package com.owd.dc.locations;


import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Sep 16, 2008
 * Time: 2:11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class newLocationUtilities {

    static String allowedParentssql = "select * from w_location_allowed_parents where ixLocType = :LocType and Allowed_Parent = :parent";
    static String allowedParentsIdsql = "select * from w_location_allowed_parents where ixLocType = :LocType and Allowed_Parent = (select ixLocType from w_location where ixNode = :parentId)";
    static String uniqueLocNamesql = "select * from w_location where ixParent = :parent and locname = :name and ixLocType = :type";
    static String inventoryGreaterThanZero = "exec dbo.inventoryInLocationGreaterThanZero :parent";
    static String getParentLocationListsql = "select t.ixParent as Id, locname, ixLocType  from w_location_tree as t, w_location as loc  where t.ixParent = loc.ixNode and  t.ixNode= :nodeId order by ixLevel  DESC";
    static String hasDirectInventorysql = "select * from w_location_inventory where ixnode= :ixNode";
    static String getDescriptionForLocType = "select description from w_location_type where ixLocType = :locType";
    static String checkIsMobileSql = "select is_mobile from w_location_type w, w_location wl where w.ixLocType = wl.ixLocType and wl.ixNode = :ixNode";
    static String getlocNameFromId = "select locname from w_location where ixNode = :ixNode";
    static String getDirectChildNoMobileSql = "select ixNode, locname from w_location w, w_location_type t where w.ixLocType = t.ixLocType and is_mobile = 0 and ixParent =  :nodeId";
    static String getChildrenTreeSql = "exec dbo.get_childLocations :id";
    static String moveLocationSql = "update w_location set ixParent = :ixParent where ixNode = :ixNode";

    public static boolean moveLocation(Session sess,locationInfoBean location, locationInfoBean newParent) throws Exception{
        boolean updategood = false;
          System.out.println("We are in the moveLocaiton stuff");
        if(newParent.getAllowedChildren().containsKey(location.getLocationType().toString())){
            System.out.println("Good mapping");
           Query q = sess.createSQLQuery(moveLocationSql);
            q.setInteger("ixParent",newParent.getId());
            q.setInteger("ixNode", location.getId());
            int results = q.executeUpdate();

            if (results==1){
                updategood= true;
            }

        } else{
            throw new Exception("Not correct Parent Child pair for move");
        }
        return updategood;
    }
    public static boolean updateParentId(Session sess, String childLocation, String newParentLocation) throws Exception{
            boolean updategood = false;
                  if(isValidParentIds(sess, childLocation, newParentLocation)){
                      WLocation w = (WLocation) sess.load(WLocation.class, Integer.decode(childLocation));
                      System.out.println("Setting "+w.getLocationName() + " parent to id: "+newParentLocation);
                           w.setParentId(Integer.decode(newParentLocation));
                      sess.saveOrUpdate(w);
                      updategood= true;
                      HibUtils.commit(sess);
                      Thread.sleep(200);
                     updatePickStrings(sess, Integer.decode(childLocation));
                  }   else{

                      throw new Exception("Not a valid parent Id for location");
                  }

           return updategood;
    }

    public static List<String> getChildTreeList(Session sess, String locationId) throws Exception {
        List<String> locs = new ArrayList<String>();

        Query q = sess.createSQLQuery(getChildrenTreeSql);
        q.setParameter("id", locationId);
        List result = q.list();
        if (result.size() > 0) {
            for (Object o : result) {

                Object[] row = (Object[]) o;

                locs.add(row[0].toString().trim());
            }
        }
        return locs;
    }

    /**
     *
      * @param sess Hibernate Session to use for lookup
     * @param locationName  Name of the location to be looked up
     * @param locationType  The type of location that you are looking up
     * @return returns String of the Description of the location Type with location name.
     * @throws Exception  of anything
     */
    public static String getLocationName(Session sess, String locationName, String locationType) throws Exception {
        System.out.println(locationName);
        Query q = sess.createSQLQuery(getDescriptionForLocType);
        q.setParameter("locType", locationType);
        List result = q.list();
        if (result.size() == 0) throw new Exception("Error loading LocType Description for " + locationType);
        return locationName = result.get(0).toString() + " " + locationName;

    }

    public static String getLocNameForMobile(String id, Session sess) throws Exception {
        String s = "";
        try {
            Query q = sess.createSQLQuery(getlocNameFromId);
            q.setParameter("ixNode", id);
            s = q.list().get(0).toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error loading location: " + e.getMessage());
        }

        return s;
    }

    /**
     * Return a map of parents sorted from top down of the locations
     *
     * @param sess   HibernateSession to be used
     * @param nodeId id of location to get a list for
     * @return Map Map of parent locations, id then location name
     * @throws Exception
     */
    public static Map<String, String> getParentMapForId(Session sess, int nodeId) {
        return getParentMapForId(sess, nodeId + "");
    }

    public static Map<String, String> getParentMapForId(Session sess, String nodeId) {
        Map<String, String> locations = new LinkedHashMap<String, String>();

        Query q = sess.createSQLQuery(getParentLocationListsql);
        q.setParameter("nodeId", nodeId);
        List result = q.list();
        if (result.size() > 0) {
            for (Object o : result) {

                Object[] row = (Object[]) o;
                System.out.println(row[0].toString());
                locations.put(row[0].toString(), row[1].toString());
            }
        }

        return locations;
    }

    public static Map<String, String> getDirectChildMapForIdNoMobile(Session sess, int nodeId) {
        return getDirectChildMapForIdNoMobile(sess, nodeId + "");
    }

    public static Map<String, String> getDirectChildMapForIdNoMobile(Session sess, String nodeId) {
        Map<String, String> locs = new LinkedHashMap<String, String>();

         List<Object[]> result =    (List<Object[]>)
                 sess.createSQLQuery(getDirectChildNoMobileSql)
                 .setParameter("nodeId", nodeId)
                 .list();

        if (result.size() > 0) {
            for (Object[] o : result) {
                System.out.println(o[0].toString());
                locs.put(o[0].toString(), o[1].toString());
            }
        }

        return locs;

    }

    public static List<String> getLocationTreeForIdFilterByType(Session sess, String nodeId, int typeFilter) {
        List<String> locs = new ArrayList<String>();

        Query q = sess.createSQLQuery(getParentLocationListsql);
        q.setParameter("nodeId", nodeId);
        List result = q.list();
        if (result.size() > 0) {
            for (Object o : result) {

                Object[] row = (Object[]) o;
                if (Integer.parseInt(row[2].toString()) > typeFilter) {
                    System.out.println(row[0].toString());
                    locs.add(row[1].toString());
                }
            }

        }
        q = sess.createSQLQuery(getlocNameFromId);
        q.setParameter("ixNode", nodeId);
        result = q.list();
        if (result.size() > 0) {
            locs.add(result.get(0).toString());
        }
        return locs;
    }

    public static boolean locationHasDirectInventory(Session sess, String ixNode) {
        boolean b = false;
        try {
            Query q = sess.createSQLQuery(hasDirectInventorysql);
            q.setParameter("ixNode", ixNode);
            b = q.list().size() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;

    }
    public static boolean isValidParentIds(Session sess, String childId, String parentId) throws Exception{
        boolean valid = false;
        locationInfoBean lib = new locationInfoBean(childId,sess);
            valid = isValidParent(sess,lib.getLocationType().toString(),Integer.parseInt(parentId));
          return valid;
    }
    /**
     * Checkes if the location type passed is a valid for the parent Id passed
     *
     * @param sess     Hibernate Session to use for Query
     * @param LocType  Location Type to be checked
     * @param parentId Id of parent location to check if it is a valid parent of the LocType
     * @return boolean
     */
    public static boolean isValidParent(Session sess, String LocType, int parentId) {
        boolean valid = false;
             System.out.println("This is our LocType for parent check " + LocType);

        try {
            if (!(LocType == null || parentId == 0)) {
                //Integer.parseInt(newLocType);
                //  Integer.parseInt(parentType);

                Query q = sess.createSQLQuery(allowedParentsIdsql);
                q.setParameter("LocType", LocType);
                q.setParameter("parentId", parentId);

                // System.out.println(q.getQueryString());
                List result = q.list();

                System.out.println("Allowed parent lookup with parent id");
                if (result.size() > 0) {

                    /*   Object[] row = (Object[])result.get(0);
                   System.out.println(row[0]);
                    System.out.println(row[1]);*/
                    valid = true;
                }
                System.out.println("4321");


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return valid;
    }

    /**
     * @param sess       Hibernate Session to use for Query
     * @param LocType    Location Type to be checked
     * @param parentType location Type to check if it is a valid parent of the LocType
     * @return boolean
     */
    public static boolean isValidParent(Session sess, String LocType, String parentType) {
        boolean valid = false;


        try {
            if (!(LocType == null || parentType == null)) {
                //Integer.parseInt(locType);
                //  Integer.parseInt(parentType);

                Query q = sess.createSQLQuery(allowedParentssql);
                q.setParameter("LocType", LocType);
                q.setParameter("parent", parentType);

                System.out.println(q.getQueryString());
                List result = q.list();

                System.out.println("1234");
                if (result.size() > 0) {

                    /* Object[] row = (Object[])result.get(0);
                   System.out.println(row[0]);
                    System.out.println(row[1]);*/
                    valid = true;
                }
                System.out.println("4321");


            }

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return valid;
    }
     public static String getSortString(Map<String,String> parentMap,String name,Boolean mobile){
         System.out.println("Getting Sort String");
       //  System.out.println(parentMap);
       //  System.out.println(name);
       //  System.out.println(mobile);
         if(null==parentMap){
             return name;
         }
     Boolean use = false;
         StringBuffer sb = new StringBuffer();
       for(Object p:parentMap.values()) {
             String parent = p.toString();
             if (parent.startsWith("Zone")){
                 use = true;
             }
             if (use){
                 sb.append(splitLocationAndPad(parent));
                }

         }
          if (!mobile){
              sb.append(splitLocationAndPad(name));
          }

        return sb.toString();
    }
    /** Return pick String for location from parent tree map.  Will display zone on down.
     *
     * @param parentMap Map of parent id's
     * @param location Name
     * @return Pick String
     *
     */
     public static String getPickString(Map parentMap, String location){
         StringBuffer sb = new StringBuffer();
             boolean use = false;
         int i = 1;
         if(null==parentMap){
             return location;
         }
         for(Object p:parentMap.values()) {
             String parent = p.toString();
             if (parent.startsWith("Zone")){
                 use = true;
             }
             if (use){
                       if(parent.startsWith("Rack")||parent.startsWith("Shelf")){
                                 sb.append(removeFirstWordPrefixFirstLetter(parent));
                       }   else{
                                 sb.append(removeFirstWord(parent));
                       }
                 if(i<parentMap.size()){
                     sb.append("-");
                 }


             }
            i++;
         }
              sb.append("<br>");
              sb.append(location);
         return sb.toString();
     }
    private static String splitLocationAndPad(String s){
        System.out.println("This is the split location and Pad "+ s);
        String[] ss = s.split(" ",2);
        if(ss.length==1){
            if(s.contains("DC")){
                String sss = s.replace("DC","");
                if(sss.length()==1){
                    return "0"+sss;
                }else{
                    return sss;
                }
            }
        }
        if (ss[1].length()==1){
            return "0"+ss[1];
        }
        return  ss[1];
    }
private static String removeFirstWordPrefixFirstLetter(String s){
    String[] ss = s.split(" ",2);
          return s.substring(0,1)+ss[1];

}
    private static String removeFirstWord(String s){
        System.out.println("First word remove on String "+s);
        String[] ss = s.split(" ",2);
        if(ss.length==1){
            if(s.contains("DC")){
                return s.replace("DC","");
            }else{
                return s;
            }
        }
              return ss[1];

    }
    /**
     * Checks if the locName is unique for the locType for the parent Id
     *
     * @param sess     Hibernate Session to use for Query
     * @param parentId the id of the parent location to check
     * @param locName  name to check if it is unique to the the parent
     * @param locType  location type id of the location to be check for uniqueness
     * @return boolean
     */
    public static boolean isUniqueName(Session sess, String parentId, String locName, String locType) {
        boolean unique = true;
        try {
            if (!(parentId == null || locName == null || locType == null)) {

                Query q = sess.createSQLQuery(uniqueLocNamesql);
                q.setParameter("parent", parentId);
                q.setParameter("name", locName);
                q.setParameter("type", locType);
                List result = q.list();

                if (result.size() > 0) {
                    unique = false;
                }
            }

        } catch (Exception e) {
            unique = false;
            e.printStackTrace();
        }


        return unique;

    }

    /**
     *
     * @param sess  Hibernate Session for queryies
     * @param id   Location id to update
     * @return    boolean if this worked or not. Saves are included
     */
    public static boolean updatePickStrings(Session sess,int id){
        boolean good = false;
             try{
                 WLocation wloc = new WLocation();
                wloc = (WLocation) sess.load(WLocation.class,id);
                System.out.println(wloc.getLocationName());
                 Map<String,String>  parents = newLocationUtilities.getParentMapForId(sess,id);

                   wloc.setPickString(getPickString(parents,wloc.getLocationName()));
                   wloc.setFormatedPickString(getFormatedPickString(wloc.getPickString(),isMobile(id,sess)));
                   wloc.setSortString(getSortString(parents,wloc.getLocationName(),isMobile(id,sess)));
                 sess.save(wloc);
                 HibUtils.commit(sess);
                 good = true;
             } catch(Exception e){
                 e.printStackTrace();
             }


        return good;
    }
    /**
     * Function to check weather a location and all of its child locations contain any inventory
     * with a quantity greater than zero
     *
     * @param sess     Hibernate Session to use for Query
     * @param parentId Id of location to check for Inventory
     * @return boolean Returns true if Location id passed in or any of the children contain inventory greater than Zero.
     */
    public static boolean hasInventoryGreaterThanZero(Session sess, int parentId) {
        return hasInventoryGreaterThanZero(sess, "" + parentId);
    }

    public static boolean hasInventoryGreaterThanZero(Session sess, String parentId) {
        boolean inventory = false;
        try {
            if (!(parentId == null)) {

                Query q = sess.createSQLQuery(inventoryGreaterThanZero);
                q.setParameter("parent", parentId);

                List result = q.list();

                if (result.size() > 0) {
                    System.out.println("Inventory more that zero: " + result.size());
                    inventory = true;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return inventory;

    }
    public static boolean isMobile(int id,Session sess) throws Exception {
        return isMobile(id+"",sess);
    }
    public static boolean isMobile(String id, Session sess) throws Exception {
        System.out.println("checking mobile for " + id);
        boolean mobile = false;
        Query q = sess.createSQLQuery(checkIsMobileSql);
        q.setParameter("ixNode", id);
        //System.out.println(q.getQueryString());
        List r = q.list();
       // System.out.println("Result size: " + r.size());
        if (r.size() > 0) {
            if (r.get(0).toString().equals("1")) mobile = true;
        } else {
            throw new Exception("Location may not Exist: Can't find record for Is Mobile lookup");
        }

        return mobile;
    }
    public static boolean swapSectionType(Session sess, Integer locId) throws Exception{
        boolean success = false;
        WLocation wloc = (WLocation) sess.load(WLocation.class,locId);
        if(wloc.getLocationType()==11){
            System.out.println("Switching to pallet type");
            wloc.setLocationType(13);
            wloc.setLocationName(wloc.getLocationName().replace("Section", "Pallet"));
            sess.save(wloc);
            HibUtils.commit(sess);
            updatePickStrings(sess, locId);

            success=true;

        }else if(wloc.getLocationType()==13){
            System.out.println("Switching to shelft type");
            wloc.setLocationType(11);
            wloc.setLocationName(wloc.getLocationName().replace("Pallet", "Section"));
            sess.save(wloc);
            HibUtils.commit(sess);
            updatePickStrings(sess, locId);
            success=true;
        }else{
          throw new Exception("Not a valid location Type to swap");
        }



        return success;
    }

    private static void fixTreeByUpdatingParentIdtoSame(List<Integer> locs){

        for(Integer id : locs){
            try{
                WLocation wloc = (WLocation) HibernateSession.currentSession().load(WLocation.class, id);
                System.out.println("Doing: "+wloc.getLocationName()+ " "+ wloc.getId());
                String sql = "update w_location set ixParent = :parent where ixNode = :id";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("parent",wloc.getParentId());
                q.setParameter("id",wloc.getId());
                q.executeUpdate();
                HibUtils.commit(HibernateSession.currentSession());

                //updateParentId(HibernateSession.currentSession(), id + "", wloc.getParentId() + "");
              //  Thread.sleep(100);

            }catch (Exception e){
                e.printStackTrace();
            }


        }



    }


    public static void fixAisleLocationTree(Integer Aisle){
        String sql = "SELECT\n" +
                "    dbo.w_location.ixNode\n" +

                "FROM\n" +
                "    dbo.w_location_tree\n" +
                "INNER JOIN\n" +
                "    dbo.w_location\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.w_location_tree.ixNode = dbo.w_location.ixNode)\n" +
                "INNER JOIN\n" +
                "    dbo.w_location_type\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.w_location.ixLocType = dbo.w_location_type.ixLocType)\n" +
                "WHERE\n" +
                "    dbo.w_location_tree.ixParent = :Aisle and description = :type ";
        List<String> types = new ArrayList<>();
        types.add("Rack");
        types.add("Shelf");
        types.add("Section");
        types.add("LongHanging");
        types.add("ShortHanging");
        types.add("Pallet");
        types.add("Bin");

        for(String type:types){
            System.out.println("Resetting "+type);
            try {
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("Aisle",Aisle);
                q.setParameter("type",type);
                List l = q.list();
                System.out.println(l.size());
                List<Integer> locs = new ArrayList<>();
                for(Object row:l){
                  locs.add(Integer.parseInt(row.toString()));
                }
                fixTreeByUpdatingParentIdtoSame(locs);

            }catch (Exception e){
                e.printStackTrace();
            }

        }



    }

    public static void fixZoneLocationTree(Integer Zone){
        String sql = "SELECT\n" +
                "    dbo.w_location.ixNode\n" +

                "FROM\n" +
                "    dbo.w_location_tree\n" +
                "INNER JOIN\n" +
                "    dbo.w_location\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.w_location_tree.ixNode = dbo.w_location.ixNode)\n" +
                "INNER JOIN\n" +
                "    dbo.w_location_type\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.w_location.ixLocType = dbo.w_location_type.ixLocType)\n" +
                "WHERE\n" +
                "    dbo.w_location_tree.ixParent = :Aisle and description = :type ";
        List<String> types = new ArrayList<>();
        types.add("Aisle");
        types.add("Rack");

        for(String type:types){
            System.out.println("Resetting "+type);
            try {
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("Aisle",Zone);
                q.setParameter("type",type);
                List l = q.list();
                System.out.println(l.size());
                List<Integer> locs = new ArrayList<>();
                for(Object row:l){
                    locs.clear();
                    locs.add(Integer.parseInt(row.toString()));
                    fixTreeByUpdatingParentIdtoSame(locs);
                    fixAisleLocationTree(Integer.parseInt(row.toString()));
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }



    }

    public static void updatePickStringViaPickStringLookup(String pickString){
        try{
            String sql = "select ixNode from w_location where pickString like :pickString";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("pickString",pickString);
            List l = q.list();

            for(Object row:l){
                updatePickStrings(HibernateSession.currentSession(),Integer.parseInt(row.toString()));
                Thread.sleep(400);
            }

        }catch (Exception e){

            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
          /*  String sql = "select top 2000 ixNode from w_location  WHERE ixLocType = 9 and len(sortSTring) > 8";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();

            for(Object row:l){
                updatePickStrings(HibernateSession.currentSession(),Integer.parseInt(row.toString()));
                Thread.sleep(700);
            }*/
           // updatePickStrings(HibernateSession.currentSession(),176249);
            List<Integer> l = new ArrayList<>();
           // l.add(8);
          //  l.add(36433);
           /* fixAisleLocationTree(67550);
            fixAisleLocationTree(67551);
            fixAisleLocationTree(67552);
            fixAisleLocationTree(67553);
            fixAisleLocationTree(67554);
            fixAisleLocationTree(67555);*/
         //  fixAisleLocationTree(36786);
         //   fixAisleLocationTree(39465);
          //  fixAisleLocationTree(66730);
          //  fixZoneLocationTree(59913);
         //   fixZoneLocationTree(62529);
          //  fixZoneLocationTree(36524);
            updatePickStringViaPickStringLookup("C3-R%");
          //  fixZoneLocationTree(48151);

          /*  String sql = "select ixNode from w_location where ixLocType = 17";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List data = q.list();

            for(Object row:data){
                l.add(Integer.parseInt(row.toString()));
            }
*/

        /*    l.add(170971);
            fixTreeByUpdatingParentIdtoSame(l);*/
          /*  for( Integer i:l){
            updatePickStrings(HibernateSession.currentSession(),i);
                Thread.sleep(600);
            }*/
        } catch (Exception ex) {
            System.out.println("Somthing is bad");
            ex.printStackTrace();
        }

    }
    public static String getFormatedPickString(String pick,boolean mobile){
        if (mobile){
           int i = pick.length();

            return pick.substring(0,i-3)+"<span class=\"formatedpick\">"+pick.substring(i-3,i)+"</span>";

        }   else {
            return pick;
        }

    }
}

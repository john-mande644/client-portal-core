package com.owd.core.business.locations;


import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class locationUtilities {
private final static Logger log =  LogManager.getLogger();

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


    public static List<String> getChildTreeList(Session sess, String id) throws Exception {
        List<String> locs = new ArrayList<String>();

        Query q = sess.createSQLQuery(getChildrenTreeSql);
        q.setParameter("id", id);
        List result = q.list();
        if (result.size() > 0) {
            for (Object o : result) {

                Object[] row = (Object[]) o;

                locs.add(row[0].toString().trim());
            }
        }
        return locs;
    }


    public static String getLocationName(Session sess, String name, String locType) throws Exception {
        log.debug(name);
        Query q = sess.createSQLQuery(getDescriptionForLocType);
        q.setParameter("locType", locType);
        List result = q.list();
        if (result.size() == 0) throw new Exception("Error loading LocType Description for " + locType);
        return name = result.get(0).toString() + " " + name;

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
        Map<String, String> locs = new LinkedHashMap<String, String>();

        Query q = sess.createSQLQuery(getParentLocationListsql);
        q.setParameter("nodeId", nodeId);
        List result = q.list();
        if (result.size() > 0) {
            for (Object o : result) {

                Object[] row = (Object[]) o;
                log.debug(row[0].toString());
                locs.put(row[0].toString(), row[1].toString());
            }
        }

        return locs;
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
                log.debug(o[0].toString());
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
                    log.debug(row[0].toString());
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


        try {
            if (!(LocType == null || parentId == 0)) {
                //Integer.parseInt(newLocType);
                //  Integer.parseInt(parentType);

                Query q = sess.createSQLQuery(allowedParentsIdsql);
                q.setParameter("LocType", LocType);
                q.setParameter("parentId", parentId);

                // log.debug(q.getQueryString());
                List result = q.list();

                log.debug("Allowed parent lookup with parent id");
                if (result.size() > 0) {

                    /*   Object[] row = (Object[])result.get(0);
                   log.debug(row[0]);
                    log.debug(row[1]);*/
                    valid = true;
                }
                log.debug("4321");


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

                log.debug(q.getQueryString());
                List result = q.list();

                log.debug("1234");
                if (result.size() > 0) {

                    /* Object[] row = (Object[])result.get(0);
                   log.debug(row[0]);
                    log.debug(row[1]);*/
                    valid = true;
                }
                log.debug("4321");


            }

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return valid;
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
                    log.debug("Inventory more that zero: " + result.size());
                    inventory = true;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return inventory;

    }

    public static boolean isMobile(String id, Session sess) throws Exception {
        log.debug("checking mobile for " + id);
        boolean mobile = false;
        Query q = sess.createSQLQuery(checkIsMobileSql);
        q.setParameter("ixNode", id);
        log.debug(q.getQueryString());
        List r = q.list();
        log.debug("Result size: " + r.size());
        if (r.size() > 0) {
            if (r.get(0).toString().equals("1")) mobile = true;
        } else {
            throw new Exception("Location may not Exist: Can't find record for Is Mobile lookup");
        }

        return mobile;
    }

    public static void main(String[] args) {
        try {
            // log.debug(hasInventoryGreaterThanZero(HibernateSession.currentSession(),"24386"));
            // log.debug(getParentMapForId(HibernateSession.currentSession(),"36433"));
            //log.debug(locationHasDirectInventory(HibernateSession.currentSession(),"36433"));
            //  String name = "1";

            //  log.debug( getLocationName(HibernateSession.currentSession(),name,"10"));
            //  log.debug(isMobile("36443",HibernateSession.currentSession()));
            getChildTreeList(HibernateSession.currentSession(), "8");
            //   log.debug(isValidParent(HibernateSession.currentSession(),"2","1"));
            // log.debug(isValidParent(HibernateSession.currentSession(),"10","15"));
            // log.debug(isValidParent(HibernateSession.currentSession(),"10","1"));
            //log.debug(isUniqueName(HibernateSession.currentSession(),"24386","Row C","8"));
            // log.debug(isUniqueName(HibernateSession.currentSession(),"24386","Row Z","8"));
        } catch (Exception ex) {
            log.debug("Somthing is bad");
            ex.printStackTrace();
        }

    }
}

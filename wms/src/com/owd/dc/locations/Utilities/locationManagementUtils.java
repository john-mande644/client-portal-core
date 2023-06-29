package com.owd.dc.locations.Utilities;

import com.owd.core.managers.LotManager;
import com.owd.dc.locations.treeBean;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLotValue;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 5, 2009
 * Time: 11:41:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class locationManagementUtils {
    private static final String defaultTreeSql = "select ixNode as id, locname, ( dbo.udf_getChildLocationCount( ixNode)) as Children from w_location where ixParent is null order by locname";
    private static final String loadTreeNodeSql = "select ixNode as id, locname, ( dbo.udf_getChildLocationCount( ixNode)) as Children from w_location where ixParent = :id order by locname";
    public static final int assignGood = 0;
    public static final int assignAlready = 1;
    public static final int assignBad = 2;

    /**
     * @param sess
     * @param nodeId
     * @return
     */
    public static ArrayList<treeBean> loadLocationTreeNode(Session sess, String nodeId) {
        //  Logger log = Logger.getLogger();

        ArrayList<treeBean> locs = new ArrayList<treeBean>();
        Query q;
        if (null == nodeId) {
            q = sess.createSQLQuery(defaultTreeSql);
        } else {
            q = sess.createSQLQuery(loadTreeNodeSql);
            q.setParameter("id", nodeId);
        }
        List results = q.list();
        // if (results.size()==0) log.debug("No items for nodeId : %s",nodeId);

        for (Object row : results.toArray()) {

            Object[] data = (Object[]) row;
            //   log.debug("Adding location for %s",data[0]);

            locs.add(new treeBean(!data[2].toString().equals("0"), data[0].toString(), data[1].toString()));


        }

        //  log.info("Returning %s locations for id %s",locs.size(),nodeId);
        return locs;

    }

    public static void getChildrenMapForParent(Session sess, Map<String, String> children, String nodeId) throws Exception {

        Query q = sess.createSQLQuery(loadTreeNodeSql);

        q.setParameter("id", nodeId);
        List results = q.list();
        if (results.size() > 0) {
            for (Object row : results) {
                //should be id, then name, then has children
                Object[] data = (Object[]) row;
                children.put(data[0].toString(), data[1].toString());
            }
        }

    }


    public static int canWeAssignThisLotIdToThisLocation(String lotId, int inventoryId, String locationBarcode) throws Exception {

        return canWeAssignThisLotToThisLocation(lotId.toString(), inventoryId, locationBarcode);

    }

    public static int canWeAssignThisLotValueToThisLocation(String lotValue, int inventoryId, String locationBarcode) throws Exception {
         System.out.println(lotValue);
        System.out.println(inventoryId);
        System.out.println(locationBarcode);

        OwdLotValue OwdLot = LotManager.getExistingOwdLotValueForString(lotValue, inventoryId);

        return canWeAssignThisLotToThisLocation(OwdLot.getId().toString(), inventoryId, locationBarcode);

    }

    private static int canWeAssignThisLotToThisLocation(String lotId, int inventoryId, String locationBarcode) throws Exception {

        String sql = "select id, inventory_fkey, case when lot_fkey is null then '' else lot_fkey end from owd_inventory_locations where location = :location ";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("location", locationBarcode);

        List alreadyAssigned = q.list();

        if (alreadyAssigned.size() > 0) {

            if (alreadyAssigned.size() == 1) {
                //check to see if this is already assigned with this lot key
                Object[] data = (Object[]) alreadyAssigned.get(0);
                if (data[2].toString().equals(lotId) & data[1].toString().equals(inventoryId)) {
                    return assignAlready;
                }
            }


        } else {
            return assignGood;
        }
        return assignBad;
    }


}

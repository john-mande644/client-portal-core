package com.owd.dc.warehouse.locationManagement.Utilities;

import com.owd.dc.beans.treeBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final String loadTreeNodeSql = "SELECT\n" +
            "  ixNode as id, locname, ( dbo.udf_getChildLocationCount( ixNode)) as Children\n" +
            "FROM\n" +
            "    dbo.w_location\n" +
            "INNER JOIN dbo.w_location_type\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.w_location.ixLocType = dbo.w_location_type.ixLocType\n" +
            "    ) \n" +
            "where ixParent = :id and is_mobile = 0";

    /**
     * @param sess
     * @param nodeId
     * @return
     */
    public static ArrayList<treeBean> loadLocationTreeNode(Session sess, String nodeId) {
        Logger log = LogManager.getLogger();

        ArrayList<treeBean> locs = new ArrayList<treeBean>();
        Query q;
        if (null == nodeId) {
            q = sess.createSQLQuery(defaultTreeSql);
        } else {
            q = sess.createSQLQuery(loadTreeNodeSql);
            q.setParameter("id", nodeId);
        }
        List results = q.list();
        if (results.size() == 0) log.debug("No items for nodeId : %s", nodeId);

        for (Object row : results.toArray()) {

            Object[] data = (Object[]) row;
            log.debug("Adding location for %s", data[0]);

            locs.add(new treeBean(!data[2].toString().equals("0"), data[0].toString(), data[1].toString()));


        }

        log.info("Returning %s locations for id %s", locs.size(), nodeId);
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
}

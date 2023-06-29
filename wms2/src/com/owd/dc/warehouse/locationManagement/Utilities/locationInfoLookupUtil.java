package com.owd.dc.warehouse.locationManagement.Utilities;

import com.owd.dc.beans.locAttributeBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 17, 2009
 * Time: 4:28:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationInfoLookupUtil {
    private static String attributesSql = "SELECT      w_location_attributes.att_id, w_location_attributes.name, w_location_attributes.catagory, ISNULL(w_location_attribute_values.[value], '') AS [values]\n" +
            "FROM         w_location_attributes LEFT OUTER JOIN\n" +
            "                      w_location_attribute_values ON w_location_attributes.att_id = w_location_attribute_values.att_id AND w_location_attribute_values.ix_Node = :ixNode\n" +
            "ORDER BY w_location_attributes.name";

    public static String getAllowedChildrensql = "SELECT     w_location_type.ixLocType, w_location_type.description\n" +
            "FROM         w_location_type INNER JOIN\n" +
            "                      w_location_allowed_parents ON w_location_type.ixLocType = w_location_allowed_parents.ixLocType\n" +
            "WHERE     (w_location_allowed_parents.Allowed_Parent = :locType)";

    public static void loadAllowedChildren(Session sess, Map<String, String> children, String locType) throws Exception {

        Query q = sess.createSQLQuery(getAllowedChildrensql);
        q.setParameter("locType", locType);
        List result = q.list();
        if (result.size() > 0) {
            for (Object row : result) {
                Object[] data = (Object[]) row;
                //data should be  loc type, description
                children.put(data[0].toString(), data[1].toString());

            }

        }


    }

    public static void loadAttributes(Session sess, List<locAttributeBean> locAttribs, List<locAttributeBean> invAttribs, String nodeId) throws Exception {

        Logger log = LogManager.getLogger();

        try {
            log.debug("Starting load of all attributes");

            Query q = sess.createSQLQuery(attributesSql);
            q.setParameter("ixNode", nodeId);
            List results = q.list();
            System.out.println(results.size());
            if (results.size() > 0) {
                for (Object row : results) {
                    Object[] data = (Object[]) row;
                    // should be attId, name, catagory, value
                    locAttributeBean b = new locAttributeBean(data[0].toString(), data[3].toString(), data[1].toString());
                    if (data[2].toString().equals("1")) {
                        locAttribs.add(b);
                        log.debug("added to locAttribs");
                    } else {
                        invAttribs.add(b);
                        log.debug("aded to invAttribs");
                    }


                }
            } else {

                log.warn("didn't get anything back for loading %s", nodeId);
                throw new Exception("no results");
            }


        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e.getMessage());
            throw new Exception("error loading attributes");
        }


    }


}

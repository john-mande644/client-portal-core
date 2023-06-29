package com.owd.dc.locations;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.locations.beans.locationsBean;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 12, 2006
 * Time: 11:32:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationUtilities {

    public static void clearShelf(String loc) throws Exception {

        Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        String getinvid = "select DISTINCT inventory_fkey from dbo.owd_inventory_locations  (NOLOCK) where location = '" + loc + "'";
        List inventoryid = new ArrayList();
        ResultSet rs = HibernateSession.getResultSet(getinvid);
        while (rs.next()) {
            System.out.println(rs.getString(1));
            inventoryid.add(rs.getString(1));
        }
        String sql = "delete from owd_inventory_locations where location = ?";
        PreparedStatement stmt = cxn.prepareStatement(sql);
        stmt.setString(1, loc.trim());
        System.out.println("delete from owd_inventory_locations where location = " + loc.trim());

        int rowsUpdated = stmt.executeUpdate();
        System.out.println(rowsUpdated);

        cxn.commit();

        checkMultipleDeletedLocations(inventoryid, loc);


    }
    public static void clearShelf(String loc, String clientFkey) throws Exception {
        System.out.println("Clearing Shelf for "+clientFkey);
        Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        String getinvid = "SELECT DISTINCT owd_inventory_locations.inventory_fkey\n" +
                "FROM         owd_inventory  (NOLOCK) INNER JOIN\n" +
                "                      owd_inventory_locations  (NOLOCK) ON owd_inventory.inventory_id = owd_inventory_locations.inventory_fkey\n" +
                "WHERE     (owd_inventory_locations.location = '"+loc+"') AND (owd_inventory.client_fkey = "+clientFkey+")";
        List inventoryid = new ArrayList();
        ResultSet rs = HibernateSession.getResultSet(getinvid);
        while (rs.next()) {
            System.out.println(rs.getString(1));
            inventoryid.add(rs.getString(1));
        }
        Iterator it = inventoryid.iterator();
        String sql = "delete from owd_inventory_locations where location = ? and inventory_fkey=?";
        PreparedStatement stmt = cxn.prepareStatement(sql);
        while (it.hasNext()){
         String invId = (String) it.next();

        stmt.setString(1, loc.trim());
            stmt.setString(2,invId);
       // System.out.println("delete from owd_inventory_locations where location = " + loc.trim());

        int rowsUpdated = stmt.executeUpdate();
        System.out.println(rowsUpdated);

        }


        cxn.commit();

        checkMultipleDeletedLocations(inventoryid, loc);


    }
    public static void checkMultipleDeletedLocations(List inventoryid, String loc) throws Exception {
        for (int i = 0; i < inventoryid.size(); i++) {
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(inventoryid.get(i).toString()));
            String location = null;
            System.out.println("before iff");
            if (inv.getFrontLocation().equals(loc)) {
                System.out.println("in Iff = front");
                String locationsql = "select location, assign_date from owd_inventory_locations  (NOLOCK) where inventory_fkey = '" + inventoryid.get(i).toString() + "' ORDER BY assign_date DESC";

                ResultSet rs = HibernateSession.getResultSet(locationsql);

                if (rs.next()) {
                    location = rs.getString(1);
                    System.out.println("locaitonfound");
                } else {
                    location = "UNKNOWN";
                    System.out.println("unknown");
                }

                Connection cxn1 = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
                String sql = "update owd_inventory set front_location = ?"
                        + ", modified_by = 'System' where inventory_id =?";
                PreparedStatement stmt1 = cxn1.prepareStatement(sql);

                stmt1.setString(1, location);
                stmt1.setInt(2, Integer.decode(inventoryid.get(i).toString()).intValue());

                int rowsUpdated1 = stmt1.executeUpdate();
                cxn1.commit();
            }
        }
    }
    public static List loadLocationsBean(String loc) throws Exception{
        ResultSet rs = HibernateSession.getResultSet("SELECT     owd_client.client_id, owd_client.company_name\n" +
                "FROM         owd_client  (NOLOCK) INNER JOIN\n" +
                "                      owd_inventory  (NOLOCK) ON owd_client.client_id = owd_inventory.client_fkey INNER JOIN\n" +
                "                      owd_inventory_locations  (NOLOCK) ON owd_inventory.inventory_id = owd_inventory_locations.inventory_fkey\n" +
                "WHERE     (owd_inventory_locations.location = '"+loc+"')\n" +
                "GROUP BY owd_client.client_id, owd_client.company_name");

         List lb = new ArrayList();
        while (rs.next()){
            System.out.println("5");
            locationsBean locb =new locationsBean();
            locb.setId(rs.getString(1));
            System.out.println("6");
            locb.setName(rs.getString(2));
            System.out.println("7");
            lb.add(locb);

        }

        locationsBean lob = new locationsBean();
        lob.setId("ALL");
        System.out.println("10");
        lob.setName("ALL");
        lb.add(lob);
        return lb;

    }

}

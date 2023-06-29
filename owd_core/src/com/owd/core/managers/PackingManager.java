package com.owd.core.managers;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.owd.hibernate.generated.OwdShipMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2007
 * Time: 8:45:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class PackingManager {
    private final static Logger log = LogManager.getLogger();


    static public void main(String[] args) throws Exception {
        try {
            System.setProperty("com.owd.environment", "test");
            PackingManager.packAndShip(22095852, 1.4, 0.00, "123456789123");
            // PackingManager.packAndShip(4482043, 1.4, 0.00, "1Z12345E0205271688");
            //   PackingManager.packAndShip(4228623);
            //   PackingManager.packAndShip(4228424);

            //List<String> barcodes = PackingManager.packAndGetBarcodesForOrders(488, "HTRemovalOrder");

            //for(int i=0; i<barcodes.size(); ++i)
            //{
             //   System.out.println(barcodes.get(i));
            //}

            int a = 0;

        } catch (Exception x) {
            int a = 0;
        }

    }

    // packAndGetBarcodesForOrders is a method used to generate package barcodes for a warehouse labeling test
    private static List<String> packAndGetBarcodesForOrders(int clientId, String orderRefrenceStartsWith) throws Exception
    {
        List<String> barcodes = new ArrayList();

        Session sess = HibernateSession.currentSession();

        ResultSet rs = HibernateSession.getResultSet(sess, "select order_id from owd_order (NOLOCK) where order_refnum like '"+orderRefrenceStartsWith + "%' order by order_id");

        List<Integer> orderIds = new ArrayList();

        while (rs.next()) {
            int orderId = rs.getInt((1));
            orderIds.add(orderId);
        }

        sess.close();

        for(int i=0; i<orderIds.size(); ++i)
        {
            List<String> packageBarcodes = pack(orderIds.get(i), false, 1.0, 1.0, "1Z12345E0205271688", false );

            for(int j=0; j<packageBarcodes.size(); ++j)
                barcodes.add(packageBarcodes.get(j));
        }

        return barcodes;
    }

    public static String packAndShip(int orderID) throws Exception {
        return packAndShip(orderID, false, 0.00, 0.00, "", false);

    }

    public static String packAndShip(int orderID, boolean packagePerLine) throws Exception {
        return packAndShip(orderID, packagePerLine, 0.00, 0.00, "", false);

    }

    public static String packAndShip(int orderID, double weightLbs, double postageCost, String trackNum) throws Exception {
        return packAndShip(orderID, false, weightLbs, postageCost, trackNum, false);

    }

    public static String packAndShip(int orderID, double weightLbs, double postageCost, String trackNum, boolean doSSCC) throws Exception {
        return packAndShip(orderID, false, weightLbs, postageCost, trackNum, doSSCC);

    }

    public static String packAndShip(int orderID, boolean packagePerLine, double weightLbs, double postageCost, String trackNum, boolean doSSCC) throws Exception {


        Session sess = HibernateSession.currentSession();

        log.debug("ready to insert");
        ResultSet rs;
        Statement stmt;
        String tracker = "";
        String ltlCarrier = "";
        int packageOId = 0;

        OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, orderID);
        OwdShipMethod owdShipMethod = getOrderShipMethod(orderID);

        String ordersql = "insert into dbo.package_order (owd_order_fkey, packer_ref, pack_start, pack_end,  num_packs) values (" +
                "?,?,getdate(),getdate(),?)";

        PreparedStatement ps = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement(ordersql);
        ps.setInt(1, orderID);
        ps.setString(2, "TEST");
        ps.setInt(3, packagePerLine ? order.getLineitems().size() : 1);
        int rows = ps.executeUpdate();
        rs = HibernateSession.getResultSet(sess, "select id from package_order  (NOLOCK) where is_void =0 and owd_order_fkey =" + orderID);
        if (rs.next()) {
            packageOId = rs.getInt((1));
            log.debug("package_order.id=" + packageOId);
        }

        int maxIndex = 1;

        for (int packIndex = 1; packIndex <= (packagePerLine ? order.getLineitems().size() : 1); packIndex++) {


            stmt = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().createStatement();
            if (trackNum.equals("")) {
                tracker = "1ZNONE" + packIndex;
            } else {
                tracker = trackNum;
            }

            String updateSQL = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                    + "msn,is_void,reported,email_sent, carrier_code, service_code) VALUES (" + orderID + "," + (packIndex) + ",\'" + (ltlCarrier.length() > 0 ? ltlCarrier + tracker : tracker) + "\'," +
                    "" + (weightLbs == 0.00 ? 0.5 : weightLbs) + "," + (postageCost) + ",0.00,convert(varchar,getdate(),101),getdate(),\'Manual\',getdate(),\'Manual\',0,0, 0,0"
                    + ",\'" + owdShipMethod.getCarrierCode() + "\',\'" + owdShipMethod.getMethodCode() + "\')";

            log.debug(updateSQL);
            int rowsUpdated = stmt.executeUpdate(updateSQL);


            stmt.close();

            rs = HibernateSession.getResultSet(sess, "select max(order_track_id) from owd_order_track  (NOLOCK) where is_void =0 and order_fkey =" + orderID);
            int orderTrackId = 0;
            if (rs.next()) {
                orderTrackId = rs.getInt(1);
                log.debug("new tracker=" + orderTrackId);

                rs.close();
            }

            PreparedStatement pst;
            //found idnow insert box
            if (doSSCC) {
                pst = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                        "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode,order_track_fkey,SSCC )values" +
                        "(?,?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, 0);
                pst.setInt(2, packageOId);
                pst.setFloat(3, 0.01f);
                pst.setFloat(4, 1);
                pst.setFloat(5, 6);
                pst.setFloat(6, 6);
                pst.setDouble(7, weightLbs);
                pst.setString(8, "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex);
                pst.setInt(9, orderTrackId);
                pst.setString(10, ManifestingManager.getSSCCBarcode());
            } else {
                pst = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                        "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode,order_track_fkey )values" +
                        "(?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, 0);
                pst.setInt(2, packageOId);
                pst.setFloat(3, 0.01f);
                pst.setFloat(4, 1);
                pst.setFloat(5, 6);
                pst.setFloat(6, 6);
                pst.setDouble(7, weightLbs);
                pst.setString(8, "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex);
                pst.setInt(9, orderTrackId);
            }
            int rows2 = pst.executeUpdate();

            String barcode = "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex;

            ResultSet rs89 = HibernateSession.getResultSet(sess, "select id from package (NOLOCK) where pack_barcode = '" + barcode + "'");
            if (rs89.next()) {
                String packageId = rs89.getString(1);
                if (packagePerLine) {
                    OwdLineItem item = (OwdLineItem) order.getLineitems().toArray()[packIndex - 1];

                    PreparedStatement pre = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                            "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                    pre.setString(1, packageId);
                    pre.setString(2, item.getLineItemId().toString());
                    pre.setString(3, item.getQuantityActual().toString());
                    int rw = pre.executeUpdate();

                } else {
                    for (OwdLineItem item : order.getLineitems()) {

                        PreparedStatement pre = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                                "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                        pre.setString(1, packageId);
                        pre.setString(2, item.getLineItemId().toString());
                        pre.setString(3, item.getQuantityActual().toString());
                        int rw = pre.executeUpdate();
                    }
                }
            }


        }

        sess.flush();
        com.owd.hibernate.HibUtils.commit(sess);

        sess.evict(order);


        String esql = "exec update_order_shipment_info " + orderID;

        Statement astmt = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().createStatement();

        astmt.executeUpdate(esql);

        HibUtils.commit(sess);


        return packageOId + "";
    }

    public static String packAndShipMap(int orderID, List<packOutInfo> poi, boolean doSSCC) throws Exception {


        Session sess = HibernateSession.currentSession();

        log.debug("ready to insert");
        ResultSet rs;
        Statement stmt;
        String tracker = "";
        String ltlCarrier = "";
        int packageOId = 0;

        OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, orderID);
        OwdShipMethod owdShipMethod = getOrderShipMethod(orderID);

        String ordersql = "insert into dbo.package_order (owd_order_fkey, packer_ref, pack_start, pack_end,  num_packs) values (" +
                "?,?,getdate(),getdate(),?)";

        PreparedStatement ps = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement(ordersql);
        ps.setInt(1, orderID);
        ps.setString(2, "TEST");
        ps.setInt(3, poi.size());
        int rows = ps.executeUpdate();
        rs = HibernateSession.getResultSet(sess, "select id from package_order  (NOLOCK) where is_void =0 and owd_order_fkey =" + orderID);
        if (rs.next()) {
            packageOId = rs.getInt((1));
            log.debug("package_order.id=" + packageOId);
        }

        int maxIndex = 1;

        for (int packIndex = 1; packIndex <= poi.size(); packIndex++) {
            packOutInfo pInfo = poi.get(packIndex - 1);
            stmt = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().createStatement();

            tracker = pInfo.trackNum;


            String updateSQL = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                    + "msn,is_void,reported,email_sent, carrier_code, service_code) VALUES (" + orderID + "," + (packIndex) + ",\'" + (ltlCarrier.length() > 0 ? ltlCarrier + tracker : tracker) + "\'," +
                    "" + (pInfo.weightLbs == 0.00 ? 0.5 : pInfo.weightLbs) + "," + (pInfo.postageCost) + ",0.00,convert(varchar,getdate(),101),getdate(),\'Manual\',getdate(),\'Manual\',0,0, 0,0"
                    + ",\'" + owdShipMethod.getCarrierCode() + "\',\'" + owdShipMethod.getMethodCode() + "\')";
            log.debug(updateSQL);
            int rowsUpdated = stmt.executeUpdate(updateSQL);


            stmt.close();

            rs = HibernateSession.getResultSet(sess, "select max(order_track_id) from owd_order_track  (NOLOCK) where is_void =0 and order_fkey =" + orderID);
            int orderTrackId = 0;
            if (rs.next()) {
                orderTrackId = rs.getInt(1);
                log.debug("new tracker=" + orderTrackId);

                rs.close();
            }

            PreparedStatement pst;
            //found idnow insert box
            if (doSSCC) {
                pst = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                        "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode,order_track_fkey,SSCC )values" +
                        "(?,?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, 0);
                pst.setInt(2, packageOId);
                pst.setFloat(3, 0.01f);
                pst.setFloat(4, 1);
                pst.setFloat(5, 6);
                pst.setFloat(6, 6);
                pst.setDouble(7, pInfo.weightLbs);
                pst.setString(8, "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex);
                pst.setInt(9, orderTrackId);
                pst.setString(10, ManifestingManager.getSSCCBarcode());
            } else {
                pst = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                        "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode,order_track_fkey )values" +
                        "(?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, 0);
                pst.setInt(2, packageOId);
                pst.setFloat(3, 0.01f);
                pst.setFloat(4, 1);
                pst.setFloat(5, 6);
                pst.setFloat(6, 6);
                pst.setDouble(7, pInfo.weightLbs);
                pst.setString(8, "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex);
                pst.setInt(9, orderTrackId);
            }
            int rows2 = pst.executeUpdate();

            String barcode = "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex;

            ResultSet rs89 = HibernateSession.getResultSet(sess, "select id from package (NOLOCK) where pack_barcode = '" + barcode + "'");
            if (rs89.next()) {
                String packageId = rs89.getString(1);

                for (OwdLineItem item : order.getLineitems()) {

                    if (item.getInventoryNum().equalsIgnoreCase(pInfo.sku)) {
                        PreparedStatement pre = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                                "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                        pre.setString(1, packageId);
                        pre.setString(2, item.getLineItemId().toString());
                        pre.setString(3, pInfo.qty + "");
                        int rw = pre.executeUpdate();
                    }
                }

            }


        }

        sess.flush();
        com.owd.hibernate.HibUtils.commit(sess);

        sess.evict(order);


        String esql = "exec update_order_shipment_info " + orderID;

        Statement astmt = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().createStatement();

        astmt.executeUpdate(esql);

        HibUtils.commit(sess);


        return packageOId + "";
    }

    public static class packOutInfo {
        double weightLbs;
        double postageCost;
        String sku;
        int qty;
        String trackNum;

        public packOutInfo(double weightLbs, double postageCost, String sku, int qty, String trackNum) {
            this.weightLbs = weightLbs;
            this.postageCost = postageCost;
            this.sku = sku;
            this.qty = qty;
            this.trackNum = trackNum;
        }
    }


    private static OwdShipMethod getOrderShipMethod(Integer orderId) throws Exception {
        String shipMethodName = getShipMethodName(orderId);
        Criteria crit = HibernateSession.currentSession().createCriteria(OwdShipMethod.class);
        crit.add(Restrictions.eq("methodName", shipMethodName));
        List<OwdShipMethod> owdShipMethods = crit.list();

        if (owdShipMethods.size() == 1) {
            return owdShipMethods.get(0);
        }

        return new OwdShipMethod();//TO PREVENT NULL REFERENCE EXCEPTION IF SHIPPING TYPE NOT FOUND
    }

    private static String getShipMethodName(Integer orderId) throws Exception {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        OwdOrderShipInfo shipInfo = order.getShipinfo();

        String result = shipInfo.getCarrService();

        if (result.equals("Economy")) {
            return "Ecomony";
        }

        return result;
    }


    // the pack method packs but does not ship. Returns package barcodes
    public static List<String> pack(int orderID, boolean packagePerLine, double weightLbs, double postageCost, String trackNum, boolean doSSCC) throws Exception {

        List<String> barcodes = new ArrayList();
        Session sess = HibernateSession.currentSession();

        log.debug("ready to insert");
        ResultSet rs;
        Statement stmt;
        String tracker = "";
        String ltlCarrier = "";
        int packageOId = 0;

        OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, orderID);
        OwdShipMethod owdShipMethod = getOrderShipMethod(orderID);

        String ordersql = "insert into dbo.package_order (owd_order_fkey, packer_ref, pack_start, pack_end,  num_packs) values (" +
                "?,?,getdate(),getdate(),?)";

        PreparedStatement ps = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement(ordersql);
        ps.setInt(1, orderID);
        ps.setString(2, "TEST");
        ps.setInt(3, packagePerLine ? order.getLineitems().size() : 1);
        int rows = ps.executeUpdate();
        rs = HibernateSession.getResultSet(sess, "select id from package_order  (NOLOCK) where is_void =0 and owd_order_fkey =" + orderID);
        if (rs.next()) {
            packageOId = rs.getInt((1));
            log.debug("package_order.id=" + packageOId);
        }

        int maxIndex = 1;

        for (int packIndex = 1; packIndex <= (packagePerLine ? order.getLineitems().size() : 1); packIndex++) {


            stmt = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().createStatement();
            if (trackNum.equals("")) {
                tracker = "1ZNONE" + packIndex;
            } else {
                tracker = trackNum;
            }

            String updateSQL = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                    + "msn,is_void,reported,email_sent, carrier_code, service_code) VALUES (" + orderID + "," + (packIndex) + ",\'" + (ltlCarrier.length() > 0 ? ltlCarrier + tracker : tracker) + "\'," +
                    "" + (weightLbs == 0.00 ? 0.5 : weightLbs) + "," + (postageCost) + ",0.00,convert(varchar,getdate(),101),getdate(),\'Manual\',getdate(),\'Manual\',0,0, 0,0"
                    + ",\'" + owdShipMethod.getCarrierCode() + "\',\'" + owdShipMethod.getMethodCode() + "\')";

            log.debug(updateSQL);
            int rowsUpdated = stmt.executeUpdate(updateSQL);


            stmt.close();

            rs = HibernateSession.getResultSet(sess, "select max(order_track_id) from owd_order_track  (NOLOCK) where is_void =0 and order_fkey =" + orderID);
            int orderTrackId = 0;
            if (rs.next()) {
                orderTrackId = rs.getInt(1);
                log.debug("new tracker=" + orderTrackId);

                rs.close();
            }

            PreparedStatement pst;
            //found idnow insert box
            if (doSSCC) {
                pst = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                        "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode,order_track_fkey,SSCC )values" +
                        "(?,?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, 0);
                pst.setInt(2, packageOId);
                pst.setFloat(3, 0.01f);
                pst.setFloat(4, 1);
                pst.setFloat(5, 6);
                pst.setFloat(6, 6);
                pst.setDouble(7, weightLbs);
                pst.setString(8, "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex);
                pst.setInt(9, orderTrackId);
                pst.setString(10, ManifestingManager.getSSCCBarcode());
            } else {
                pst = ((SessionImplementor) sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package " +
                        "(owd_boxtypes_fkey, package_order_fkey,  curr_cost, depth, width, height,weight_lbs,pack_barcode,order_track_fkey )values" +
                        "(?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, 0);
                pst.setInt(2, packageOId);
                pst.setFloat(3, 0.01f);
                pst.setFloat(4, 1);
                pst.setFloat(5, 6);
                pst.setFloat(6, 6);
                pst.setDouble(7, weightLbs);
                pst.setString(8, "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex);
                pst.setInt(9, orderTrackId);
            }
            int rows2 = pst.executeUpdate();

            String barcode = "p" + packageOId + order.getOrderNumBarcode() + "b" + packIndex;
            barcodes.add(barcode);

            ResultSet rs89 = HibernateSession.getResultSet(sess, "select id from package (NOLOCK) where pack_barcode = '" + barcode + "'");
            if (rs89.next()) {
                String packageId = rs89.getString(1);
                if (packagePerLine) {
                    OwdLineItem item = (OwdLineItem) order.getLineitems().toArray()[packIndex - 1];

                    PreparedStatement pre = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                            "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                    pre.setString(1, packageId);
                    pre.setString(2, item.getLineItemId().toString());
                    pre.setString(3, item.getQuantityActual().toString());
                    int rw = pre.executeUpdate();

                } else {
                    for (OwdLineItem item : order.getLineitems()) {

                        PreparedStatement pre = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("insert into dbo.package_line" +
                                "(package_fkey, owd_line_item_fkey, pack_quantity) values (?,?,?)");
                        pre.setString(1, packageId);
                        pre.setString(2, item.getLineItemId().toString());
                        pre.setString(3, item.getQuantityActual().toString());
                        int rw = pre.executeUpdate();
                    }
                }
            }
        }

        sess.flush();
        com.owd.hibernate.HibUtils.commit(sess);

        sess.evict(order);

        return barcodes;
    }
}

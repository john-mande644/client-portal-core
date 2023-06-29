package com.owd.dc.packing;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.generated.OwdOrder;
import com.thoughtworks.xstream.XStream;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.packing.beans.OrderPackagesBean;
import com.owd.dc.packing.beans.PackageBoxBean;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 11, 2009
 * Time: 10:31:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class voidUtil {
    private static String getOrderNumFromPackage(String packagebcode) {

        return packagebcode.substring(packagebcode.indexOf("*") + 1, packagebcode.lastIndexOf("*"));

    }

    public static String getOrderNumFromTrackNumber(String track, Session sess) throws Exception {
        String sql = "SELECT\n" +
                "    dbo.owd_order.order_num\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN dbo.owd_order_track\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_track.order_fkey\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_order_track.tracking_no = :track ";
        Query q = sess.createSQLQuery(sql);
        q.setString("track", track);

        List l = q.list();
        if (l.size() > 0) {
            return l.get(0).toString();
        } else {
            throw new Exception("Unable to find Order Number Via tracking number");
        }

    }

    public static String getOrderPackagesXml(Session sess, String orderNum) {
        OrderPackagesBean opb = new OrderPackagesBean();

        try {

            if (orderNum.toUpperCase().contains("P")) {
                orderNum = getOrderNumFromPackage(orderNum);

            } else if (orderNum.length() > 9) {
                System.out.println("we are doing the tracking number stuff");
                orderNum = getOrderNumFromTrackNumber(orderNum, sess);

            }
            orderNum = orderNum.replaceAll("\\*|[rR].*", "");
            opb.setPackages(getPackagesList(sess, orderNum));
            opb.getResults().setMessage("Success");
            opb.setOrderNum(orderNum);

        } catch (Exception e) {
            e.printStackTrace();
            opb.getResults().setMessage("Error");
            opb.getResults().setError(e.getMessage());

        }


        XStream x = new XStream();

        x.alias("OrderPackages", opb.getClass());
        x.alias("Box", PackageBoxBean.class);

        return xmlCreate.xmlHead + x.toXML(opb);

    }

    public static List<PackageBoxBean> getPackagesListOrderId(Session sess, String orderId) throws Exception {
        try {
            OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, Integer.parseInt(orderId));
            return getPackagesList(sess, order.getOrderNum());
        } catch (Exception e) {
            e.printStackTrace();

        }

        throw new Exception("Unable to load package barcodes properly");
    }

    private static List<PackageBoxBean> getPackagesList(Session sess, String orderNum) throws Exception {
        String sql = "Select package.pack_barcode, owd_order.shipped_on, package.id, package.package_order_fkey, order_track_fkey, client_fkey " +
                "From owd_order,  package_order, package " +
                "WHERE owd_order.order_id = package_order.owd_order_fkey and " +
                " package_order.id = package.package_order_fkey and " +
                "(owd_order.order_num = :orderNum) AND ((owd_order.shipped_on is not NULL) or owd_order.is_shipping = 1) and (package_order.is_void = 0)";
        List<PackageBoxBean> l = new ArrayList<PackageBoxBean>();
        try {


            Query q = sess.createSQLQuery(sql);

            q.setString("orderNum", orderNum);
            List results = q.list();
            if (results.size() > 0) {
                for (Object row : results) {

                    Object[] data = (Object[]) row;
                    if(data[5].toString().equalsIgnoreCase("622")) throw new Exception("Cannot Void shipments for Deliverr, please have supervisor inform FS of issue.");
                    PackageBoxBean pb = new PackageBoxBean();
                    pb.setBarcode(data[0].toString());
                    pb.setId(data[2].toString());
                    pb.setPackageOrderFkey(data[3].toString());
                    pb.setOrderTrackId(data[4].toString());
                    l.add(pb);
                }

            } else {
                throw new Exception("No Shipments Found");
            }

        } catch (Exception e) {
            System.out.println("Error getting packages for VOID");
            e.getMessage();
            if(e.getMessage().startsWith("Cannot Void shipments for")) throw new Exception(e.getMessage());
            throw new Exception("Error getting packages for VOID:  " + e.getMessage());
        }
        return l;

    }

    public static void main(String[] args) {
        try {
            // System.out.println(getOrderNumFromPackage("P1753259*8012256*B3"));
            // System.out.println(getOrderNumFromTrackNumber("1ZE587156831126307",HibernateSession.currentSession()));
            //  System.out.println(getOrderPackagesXml(HibernateSession.currentSession(),"1ZE587156831126307"));
            System.out.println(getPackagesList(HibernateSession.currentSession(), "16816974"));
        } catch (Exception e) {
            e.printStackTrace();

        }


    }


    public static void voidOrderPackage(PackageBoxBean pbb, String oid) throws Exception

    {


        System.out.println("Voiding order package ID " + pbb.getId() + " for order id " + oid);


        //validated

        String sql = "update owd_order_track set is_void=1  where order_track_id= :trackId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("trackId", pbb.getOrderTrackId());
        int i = q.executeUpdate();


        if (i > 0) {


            sql = "update package_order set packs_shipped=(packs_shipped-1) where id = :id";
            q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("id", pbb.getPackageOrderFkey());

            i = q.executeUpdate();
            if (i > 0) {
                sql = "update package set ship_time=null,order_track_fkey=null where id = :id";
                q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("id", pbb.getId());
            } else {
                throw new Exception("Unable to update package_order");
            }


            String esql = "exec update_order_shipment_info " + oid;

            q = HibernateSession.currentSession().createSQLQuery(esql);
            q.executeUpdate();

            HibUtils.commit(HibernateSession.currentSession());


        } else {
            throw new Exception("Unable to void tracking Number");
        }


    }
}

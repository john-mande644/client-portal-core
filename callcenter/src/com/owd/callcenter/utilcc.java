package com.owd.callcenter;

import com.owd.hibernate.*;
import com.owd.callcenter.beans.selectList;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.*;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 2, 2006
 * Time: 3:40:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class utilcc {
    public static String printerName = "Main Office 4050";

    public static OwdInventory getInvFromSku(Session sess, String sku, String clientFkey) throws Exception {
        Criteria crit = sess.createCriteria(OwdInventory.class);
        crit.setMaxResults(100);
        crit.add(Restrictions.eq("inventoryNum", sku))
                .add(Restrictions.eq("owdClient", sess.load(OwdClient.class, Integer.valueOf(clientFkey))));
        List list = crit.list();
        if (list.size() < 1) {
            throw new Exception(sku + " Not Found");
        }
        System.out.println("found sku's" + list.size());
        return (OwdInventory) list.get(0);

    }

    public static OwdOrder getOrderFromNum(Session sess, String orderNum) throws Exception {
        try{
               Integer.parseInt(orderNum);
            }catch (NumberFormatException nf){
               throw new Exception("Invalid order num");
            }
                ResultSet rs = HibernateSession.getResultSet(sess,"select order_id from owd_order where order_num = '"+orderNum+"'");
               rs.next();

                       OwdOrder o = (OwdOrder)sess.load(OwdOrder.class, Integer.valueOf(rs.getString(1)));

        return o;
    }

    public static void printInvoice(OwdOrder order, Session sess, String printer) throws Exception {


        String sql = "insert into dbo.owd_print_queue_sl (order_id, client_id, printer_name) values (" + order.getOrderId() + "," + order.getClientFkey() + ",'" + printer + "')";

        int rowsUpdated = sess.createSQLQuery(sql).executeUpdate();
        System.out.println(rowsUpdated);
        if (rowsUpdated == 1) {
            HibUtils.commit(sess);
        }

    }

    public static List loadStatus() {
        List l = new ArrayList();
        List stat = new ArrayList();
        l.add("New");
        l.add("Returned");
        l.add("Damaged");
        l.add("Opened");
        l.add("Kitting");
        l.add("Kitting 1");
        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }
        System.out.println("Done Setting Status");

        return stat;
    }

    public static List loadPrinters() {
        Map m = new TreeMap();
        List stat = new ArrayList();
        m.put("main_office","Office Samsung(plain paper");
        m.put("Main Office 4050", "Main Office 4040(plain paper)");
        m.put("Warehouse Xerox", "Warehouse Xerox(plain paper)");
        m.put("Admin DocuLabel", "Admin DocuLabel(labels)");
        Iterator it = m.keySet().iterator();
        int i=0;
        while(it.hasNext()){
            String key = (String) it.next();
            selectList sl = new selectList();
            sl.setAction(key);
            sl.setDisplay(m.get(key).toString());
            stat.add(i, sl);
            i++;
        }

        System.out.println("Done Setting Printers");

        return stat;
    }
    public static List loadViewFields() {
        List l = new ArrayList();
        List stat = new ArrayList();
        l.add("All");
        l.add("Receive Id");
        l.add("Transaction");
        l.add( "Inventory Id");
        l.add("Inventory Num");
        l.add( "User");


        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }
        System.out.println("Done Setting Fields");

        return stat;
    }
     public static List loadViewTypes() {
        List l = new ArrayList();
        List stat = new ArrayList();
         l.add( "contains");
         l.add( "is");



        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }
        System.out.println("Done Setting Fields");

        return stat;
    }
    public static List loadReturnReasons() {
        System.out.println("Starting retur Reasons");
        List l = new ArrayList();
        List stat = new ArrayList();
        l.add("Unknown");
        l.add("Defective");
        l.add("Duplicate Order");
        l.add("Fit too small or large");
        l.add("Insufficient Address");
        l.add("Item Damaged");
        l.add("Not in on 3 Attempts");
        l.add("Not what was wanted");
        l.add("Not as expected");
        l.add("Moved");
        l.add("Refused");
        l.add("Unclaimed");
        l.add("Undeliverable");
        l.add("Wrong item delivered");
        l.add("other");
        l.add("Mispick");
        l.add("Miskey");
        l.add("Fit to small");
        l.add("Fit to large");
        l.add("Unable to forward");

        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }
        System.out.println("Done Setting reasones");

        return stat;
    }
    public static List loadResolutions() {
        System.out.println("Start resolutions");
        List l = new ArrayList();
        List stat = new ArrayList();
        l.add("None Taken");
        l.add("Exchanged");
        l.add("Reshipped");
        l.add("Credit Given");
        l.add("Forwarded to Client");
        l.add("Added to report");


        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }

         System.out.println("done resolution");
        return stat;
    }
     public static List loadCredits() {
         System.out.println("start credits");
        List l = new ArrayList();
        List stat = new ArrayList();
        l.add("None Taken");
        l.add("CC-OWD");
        l.add("CC-Client System");
        l.add("Check mailed");
        l.add("Forwarded to client");
        l.add("Coupon-OWD");
        l.add("Coupon-other");

        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }

         System.out.println("done credit");
        return stat;
    }
    public static String lookupField(String field) {
           String value = (String) getFeilds().get(field);


           return value;


       }

       static Map fields = null;

       public static Map getFeilds() {
           Map m = fields;
           if (m == null) {
               m = new TreeMap();
               System.out.println("setting fields");
               m.put("Receive Id", "receive_id");
               m.put("Transaction", "transaction_num");
               m.put("Inventory Id", "inventory_id");
               m.put("Inventory Num", "inventory_num");
               m.put("User", "receive_user");
           }
           return m;
       }

}

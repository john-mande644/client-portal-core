package com.owd.intranet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.HibernateSession;
import com.owd.intranet.beans.selectList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 2, 2006
 * Time: 3:40:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class util {
private final static Logger log =  LogManager.getLogger();
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
        log.debug("found skus" + list.size());
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

        Connection cxn =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection();
        String sql = "insert into dbo.owd_print_queue_sl (order_id, client_id, printer_name) values (" + order.getOrderId() + "," + order.getClientFkey() + ",'" + printer + "')";
        PreparedStatement stmt = cxn.prepareStatement(sql);
        int rowsUpdated = stmt.executeUpdate();
        //log.debug(rowsUpdated);
        if (rowsUpdated == 1) {
            cxn.commit();
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
        l.add("Inventory Transfer");
        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }
        //log.debug("Done Setting Status");

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
        
        //log.debug("Done Setting Printers");

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
        //log.debug("Done Setting Fields");

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
        //log.debug("Done Setting Fields");

        return stat;
    }
    public static List loadReturnReasons() {
        //log.debug("Starting retur Reasons");
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
        l.add("Exchange");
        l.add("Mispick");
        l.add("Miskey");
        l.add("Fit to small");
        l.add("Fit to large");
        l.add("Unable to forward");
        l.add("No longer need");
        l.add("No such Number or Street");
        l.add("Changed Mind");
        l.add("Color not Pictured");
        l.add("Material not as expected");
        l.add("Ordered multiple sizes/colors");
        l.add("FS - Too Small");
        l.add("FL - Too Large");
        l.add("FF - Do Not Like the Fit");
        l.add("DF - Defective");
        l.add("CM - Changed Mind");
        l.add("ID - Item Not As Depicted");
        l.add("R2 - Arrived Too Late");
        l.add("WR - Wrong Product Received");
        l.add("OT - Other");
        l.add("RG - Returning A Gift");
        l.add("Ex - Exchange");
        l.add("MS - Ordered Multiple Sizes");
        l.add("Delivered to customer damaged.");

        for (int i = 0; i < l.size(); i++) {
            selectList sl = new selectList();
            sl.setAction(l.get(i).toString());
            sl.setDisplay(l.get(i).toString());
            stat.add(i, sl);
        }
        //log.debug("Done Setting reasones");

        return stat;
    }
    public static List loadResolutions() {
        //log.debug("Start resolutions");
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

         //log.debug("done resolution");
        return stat;
    }
     public static List loadCredits() {
         //log.debug("start credits");
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

         //log.debug("done credit");
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
               //log.debug("setting fields");
               m.put("Receive Id", "receive_id");
               m.put("Transaction", "transaction_num");
               m.put("Inventory Id", "inventory_id");
               m.put("Inventory Num", "inventory_num");
               m.put("User", "receive_user");
           }
           return m;
       }

}

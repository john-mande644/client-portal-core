package com.owd.web.internal.warehouse.admin.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2003
 * Time: 11:31:39 AM
 * To change this template use Options | File Templates.
 */
public class PrintQueueDisplayServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public static final String sql = "(select inventory_num,description,0,1,carr_service, quantity_actual, inventory_id,count(distinct(order_id))  from owd_print_queue_sl" +
            " (NOLOCK) join owd_line_item l (NOLOCK) on order_id=l.order_fkey join owd_order_ship_info s (NOLOCK) on s.order_fkey=order_id\n" +
            "where inventory_id in (99796,99798,100554,100555) and ship_state in ('AE','AP','AA')\n" +
            "group by carr_service, inventory_num,description,inventory_id,quantity_actual\n" +
            ")\n" +
            "union\n" +
            "(select inventory_num,description,0,0,carr_service, quantity_actual,inventory_id,count(distinct(order_id))  from owd_print_queue_sl (NOLOCK)" +
            " join owd_line_item l (NOLOCK) on order_id=l.order_fkey join owd_order_ship_info s (NOLOCK) on s.order_fkey=order_id\n" +
            "where inventory_id in (99796,99798,100554,100555)  and ship_state not in ('AE','AP','AA')\n" +
            "group by carr_service, inventory_num,description,inventory_id,quantity_actual\n" +
            ")\n" +
            "order by inventory_num,carr_service";

    public void init(ServletConfig config)

            throws ServletException {

        super.init(config);


    }

    public void destroy() {
        super.destroy();
    }

    //GET requests not supported

    public void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        doPost(req, resp);

    }

    //all requests should be POST

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {
        //log.debug("dopost");
        String skuID = OWDUtilities.getStringParam(req, "sku", "");
        //log.debug("dopost sku " + skuID);

        try {
            if (skuID.length() > 0) {
                int isAPO = OWDUtilities.getIntegerParam(req, "isAPO", 0);
                int qty = OWDUtilities.getIntegerParam(req, "qty", 0);
                String ship_method = OWDUtilities.getStringParam(req, "shipMethod", "");

                String mysql = "select distinct(pq.order_id) from owd_print_queue_sl pq (NOLOCK) "
                        + "join owd_order_ship_info s (NOLOCK) on s.order_fkey=pq.order_id ";
                if (isAPO == 1) {
                    mysql = mysql + " and ship_state in ('AE','AP','AA')";

                } else {
                    mysql = mysql + " and ship_state not in ('AE','AP','AA')";

                }
                mysql = mysql + "  and carr_service = \'" + ship_method + "\'";


                mysql = mysql + " and pq.order_id  in (select order_fkey from owd_line_item where quantity_actual=" + qty + " and inventory_id = " + skuID + ")";


                //log.debug(mysql);
                Vector move_queue = moveOrderToNewQueue(mysql, req);
                req.setAttribute("print_queue", getPrintQueue(sql, req));
                req.getRequestDispatcher("/warehouse/admin/printing/index.jsp").forward(req, resp);
            } else {

                req.setAttribute("print_queue", getPrintQueue(sql, req));
                req.getRequestDispatcher("/warehouse/admin/printing/index.jsp").forward(req, resp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    public Vector moveOrderToNewQueue(String sqlStmt, HttpServletRequest req) {
        Connection cxn = null;
        PreparedStatement stmt = null;
        PreparedStatement delstmt = null;
        ResultSet rs = null;
        int total = 0;
        Vector move_queue = new Vector();

        //log.debug("moving items");
        try {
            cxn = ConnectionManager.getConnection();
            //log.debug("got cxn");
            stmt = cxn.prepareStatement(sqlStmt);
            //log.debug("prep");
            rs = stmt.executeQuery();
            //log.debug("query done");
            while (rs.next()) {
                //log.debug("moving item to q");
                total += 1;
                move_queue.add(rs.getString(1));
            }
            //log.debug("rs done");
            rs.close();
            stmt.close();
            stmt = cxn.prepareStatement("insert into owd_print_queue (order_id,client_id) VALUES (?,?)");
            delstmt = cxn.prepareStatement("delete from owd_print_queue_sl where order_id = ?");

            for (int i = 0; i < move_queue.size(); i++) {
                stmt.setInt(1, new Integer((String) move_queue.elementAt(i)).intValue());
                stmt.setInt(2, 160);
                //stmt.setInt(3,printer_id);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    delstmt.setInt(1, new Integer((String) move_queue.elementAt(i)).intValue());

                    rowsUpdated = delstmt.executeUpdate();
                }
            }

            delstmt.close();
            stmt.close();

            cxn.commit();
        } catch (Exception e) {
            try {
                cxn.rollback();
            } catch (Exception exc) {
            }
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                delstmt.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }
            try {
                cxn.close();
            } catch (Exception exc) {
            }
        }
        req.setAttribute("move_total", new Integer(total));
        return move_queue;
    }

    public Vector getPrintQueue(String sqlStmt, HttpServletRequest req) {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int total = 0;
        Vector print_queue = new Vector();
        try {
            cxn = ConnectionManager.getConnection();
            cxn.setReadOnly(true);

            stmt = cxn.createStatement();
            rs = stmt.executeQuery(sqlStmt);
            while (rs.next()) {
                Vector aVector = new Vector();
                aVector.add(rs.getString(1));
                aVector.add(rs.getString(2));
                aVector.add(rs.getString(3));
                aVector.add(rs.getString(4));
                aVector.add(rs.getString(5));
                aVector.add(rs.getString(6));
                aVector.add(rs.getString(7));
                aVector.add(rs.getString(8));
                total += rs.getInt(8);
                print_queue.add(aVector);
            }
            cxn.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }
            try {
                cxn.close();
            } catch (Exception exc) {
            }
        }
        req.setAttribute("print_total", new Integer(total));
        return print_queue;
    }


    public void startup(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {


    }

    protected void doAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {


    }

}


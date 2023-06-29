package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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


public class PrintQueueDisplayServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();

    public static final String sql = "(select \'SWGBT-HG-SW0203-SWGB03-BTCD3\',35124,0,carr_service,MAX(ship_country), count(distinct(order_id)) from " +
            "owd_print_queue_sl2 sl2 join owd_order_ship_info si on si.order_fkey =  " +
            "sl2.order_id where sl2.order_id not in (select order_fkey from " +
            "owd_line_item where inventory_id <> 35124) group by carr_service) " +
            "UNION " +
            "(select \'SWGBT-HG-SW0203-SWGB03-BTCD3+\',35124,1,carr_service,MAX(ship_country) , count(distinct(order_id)) from " +
            "owd_print_queue_sl2 sl2 join owd_order_ship_info si on si.order_fkey =  " +
            "sl2.order_id where sl2.order_id  in (select order_fkey from " +
            "owd_line_item where inventory_id = 35124) and sl2.order_id  in (select order_fkey from " +
            "owd_line_item where inventory_id <> 35124) group by carr_service)";

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
        log.debug("dopost");
        int skuID = com.owd.core.OWDUtilities.getIntegerParam(req, "skuID", 0);
        log.debug("dopost sku " + skuID);

        try {
            if (skuID > 0) {
                int isMixed = com.owd.core.OWDUtilities.getIntegerParam(req, "isMixed", 0);
                String ship_method = com.owd.core.OWDUtilities.getStringParam(req, "shipMethod", "");
                int printer_id = com.owd.core.OWDUtilities.getIntegerParam(req, "printer_id", 0);

                String mysql = "select distinct(pq.order_id) from owd_print_queue_sl2 pq "
                        + "where pq.order_id in (select order_id from owd_order_ship_info s where s.order_fkey "
                        + " = pq.order_id and carr_service = \'" + ship_method + "\')";

                if (isMixed == 1) {
                    mysql = mysql + " and pq.order_id in (select order_fkey from owd_line_item where inventory_id = " + skuID
                            + ") and pq.order_id in (select order_fkey from owd_line_item where inventory_id <> " + skuID + ")";


                } else {
                    mysql = mysql + " and pq.order_id not in (select order_fkey from owd_line_item where inventory_id <> " + skuID + ")";

                }

                log.debug(mysql);
                Vector move_queue = moveOrderToNewQueue(mysql, req, printer_id);
                req.setAttribute("print_queue", getPrintQueue(sql, req));
                req.getRequestDispatcher("/printdisplay.jsp").forward(req, resp);
            } else {

                req.setAttribute("print_queue", getPrintQueue(sql, req));
                req.getRequestDispatcher("/printdisplay.jsp").forward(req, resp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    public Vector moveOrderToNewQueue(String sqlStmt, HttpServletRequest req, int printer_id) {
        Connection cxn = null;
        PreparedStatement stmt = null;
        PreparedStatement delstmt = null;
        ResultSet rs = null;
        int total = 0;
        Vector move_queue = new Vector();

        log.debug("moving items");
        try {
            cxn = ConnectionManager.getConnection();
            log.debug("got cxn");
            stmt = cxn.prepareStatement(sqlStmt);
            log.debug("prep");
            rs = stmt.executeQuery();
            log.debug("query done");
            while (rs.next()) {
                log.debug("moving item to q");
                total += 1;
                move_queue.add(rs.getString(1));
            }
            log.debug("rs done");
            rs.close();
            stmt.close();
            stmt = cxn.prepareStatement("insert into owd_print_queue (order_id,client_id) VALUES (?,?)");
            delstmt = cxn.prepareStatement("delete from owd_print_queue_sl2 where order_id = ?");

            for (int i = 0; i < move_queue.size(); i++) {
                stmt.setInt(1, new Integer((String) move_queue.elementAt(i)).intValue());
                stmt.setInt(2, 127);
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
            stmt = cxn.createStatement();
            rs = stmt.executeQuery(sqlStmt);
            String main_cat = null;
            String previous_cat = "";
            while (rs.next()) {
                Vector aVector = new Vector();
                aVector.add(rs.getString(1));
                aVector.add(rs.getString(2));
                aVector.add(rs.getString(3));
                aVector.add(rs.getString(4));
                aVector.add(rs.getString(5));
                aVector.add(rs.getString(6));
                total += rs.getInt(6);
                print_queue.add(aVector);
            }
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

package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ConnectionManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;


public class RFServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public static final String kMoveItemAction = "moveitem";
    public static final String kFindItemAction = "finditem";
    public static final String kStartPickAction = "startpick";
    public static final String kEndPickAction = "endpick";
    public static final String kCancelPickAction = "cancelpick";

    public static final String kActionTypeTag = "fxn";
    public static final String kUserNameTag = "usr";
    public static final String kTermIPTag = "ip";
    public static final String kItemBarcodeTag = "sku";
    public static final String kLocationBarcodeTag = "loc";

    public static final String kOrderBarcodeTag = "ord";
    public static final String kOverrideTag = "mgr";

    static final int kStatusPicking = 1;
    static final int kStatusPicked = 2;
    static final int kStatusUnpicked = 0;

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
        try {
            if (userIdentified(req)) {
                doAction(req, resp);
            } else {

            }
        } catch (Exception ex) {


        }
    }

    protected boolean userIdentified(HttpServletRequest req) {

        return true;
    }

    protected void doAction(HttpServletRequest req, HttpServletResponse resp) {
        String action = getStringParam(req, kActionTypeTag, "");
        String ip = getStringParam(req, kTermIPTag, "");
        String user = getStringParam(req, kUserNameTag, "");

        try {
            FileWriter out = new FileWriter("Handheld.log", true);
            java.util.Date date_time = Calendar.getInstance().getTime();
            String message = date_time.toString() + "\t" + user + " is doing " + action + " from " + ip + "\r\n";
            out.write(message);
            out.write("\r\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            com.owd.core.Mailer.postMailMessage("can't log handheld", e.getMessage(), "danny@owd.com", "support@owd.com");
        }

        if (action.equals(kFindItemAction)) {
            _findItem(req, resp);
        } else if (action.equals(kMoveItemAction)) {
            _moveItem(req, resp);
        } else if (action.equals(kStartPickAction)) {
            _startPick(req, resp);
        } else if (action.equals(kEndPickAction)) {
            _endPick(req, resp);
        } else if (action.equals(kCancelPickAction)) {
            _cancelPick(req, resp);
        } else {
            try {
                sendRFError(resp, "Request not recognized.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    protected void _findItem(HttpServletRequest req, HttpServletResponse resp) {

        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String results = "";
            String user = getStringParam(req, kUserNameTag, "");
            String barcode = getStringParam(req, kItemBarcodeTag, "");

            if (barcode.length() < 1) throw new Exception(".Item barcode not provided");

            //	if(user.startsWith("TEST") || user.equals(""))
            //		cxn = ConnectionManager.getTestConnection();
            //	else
            cxn = ConnectionManager.getConnection();

            String sql = "select front_location,company_name,inventory_num,description "
                    + " from owd_inventory, owd_client where client_id = client_fkey and inventory_id = " + barcode.trim();

            stmt = cxn.createStatement();

            stmt.executeQuery(sql);

            rs = stmt.getResultSet();

            if (rs.next()) {
                results = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4);
            } else {
                rs.close();
                stmt.close();

                sql = "select front_location,company_name,inventory_num,description "
                        + " from owd_inventory, owd_client where client_id = client_fkey and barcode_no = " + barcode.trim();
                stmt = cxn.createStatement();

                stmt.executeQuery(sql);

                rs = stmt.getResultSet();

                if (rs.next()) {
                    results = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4);
                } else {
                    throw new Exception(".Item not found. Please notify manager.");
                }
            }

            sendRFSuccess(resp, results);
            cxn.rollback();

        } catch (Throwable th) {

            try {
                cxn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }

    }

    protected void _moveItem(HttpServletRequest req, HttpServletResponse resp) {

        Connection cxn = null;
        Statement stmt = null;
        try {

            String user = getStringParam(req, kUserNameTag, "");
            String barcode = getStringParam(req, kItemBarcodeTag, "");
            String location = getStringParam(req, kLocationBarcodeTag, "");

            if (barcode.length() < 1) throw new Exception(".Item barcode not provided");
            if (location.length() < 1) throw new Exception(".Location was not provided");

            if (!(location.startsWith("/"))) throw new Exception(".Location code not valid");

            //	if(user.startsWith("TEST") || user.equals(""))
            //	{
            //		user = "TEST";
            //		cxn = ConnectionManager.getTestConnection();
            //	}
            //	else
            //	{
            cxn = ConnectionManager.getConnection();
            //	}

            String sql = "update owd_inventory set front_location = \"" + location.trim()
                    + "\", modified_by = \"" + user + "\" where inventory_id = " + barcode.trim();

            stmt = cxn.createStatement();

            int rowsUpdated = stmt.executeUpdate(sql);

            if (rowsUpdated != 1)
                throw new Exception(".Item not found. Please notify manager.");

            cxn.commit();

            sendRFSuccess(resp, "");

        } catch (Throwable th) {

            try {
                cxn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                ConnectionManager.freeConnection(cxn);
            } catch (Exception ex) {
            }
        }

    }

    protected void _endPick(HttpServletRequest req, HttpServletResponse resp) {
        StringBuffer sb = new StringBuffer();
        StringBuffer hdr = new StringBuffer();
        int itemCount = 0;

        String user = getStringParam(req, kUserNameTag, "");
        String orderNum = getStringParam(req, kOrderBarcodeTag, "");


        java.sql.Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer selector = new StringBuffer();


        try {
            //	if(user.startsWith("TEST") || user.equals(""))
            //	{
            //		user = "TEST";
            //		conn = ConnectionManager.getTestConnection();
            //	}
            //	else
            //	{
            conn = ConnectionManager.getConnection();
            //	}

            stmt = conn.createStatement();

            String sqlQuery = "select o.is_void, ISNULL(o.pick_status,0), ISNULL(o.pick_by,\"\") from owd_order o where  "
                    + "o.order_num = \"" + orderNum + "\" ";

            rs = stmt.executeQuery(sqlQuery);


            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    throw new Exception(".Order has been voided!");
                } else if (rs.getInt(2) == kStatusUnpicked) {
                    throw new Exception(".Order pick has not begun - start over");
                } else if (rs.getInt(2) == kStatusPicked) {
                    throw new Exception(".Order has already been picked by " + rs.getString(3));
                }
            }

            rs.close();
            stmt.close();


            stmt = conn.createStatement();
            sqlQuery = "update owd_order set pick_by = \"" + user + "\", pick_status=" + kStatusPicked + " where order_num = \"" + orderNum + "\"";

            int rows = stmt.executeUpdate(sqlQuery);
            if (rows < 1)
                throw new Exception(".Problem with server - Please try again.");

            stmt.close();
            stmt = conn.createStatement();
            sqlQuery = "insert into pick_events (picker,pick_end,order_num) values ( \"" + user + "\",1,\"" + orderNum + "\")";

            rows = stmt.executeUpdate(sqlQuery);
            stmt.close();

            conn.commit();
            sendRFSuccess(resp, "");

        } catch (Throwable th) {

            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                ConnectionManager.freeConnection(conn);
            } catch (Exception ex) {
            }
        }
    }

    protected void _cancelPick(HttpServletRequest req, HttpServletResponse resp) {
        StringBuffer sb = new StringBuffer();
        StringBuffer hdr = new StringBuffer();
        int itemCount = 0;

        String user = getStringParam(req, kUserNameTag, "");
        String orderNum = getStringParam(req, kOrderBarcodeTag, "");


        java.sql.Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer selector = new StringBuffer();


        try {
            //if(user.startsWith("TEST") || user.equals(""))
            //{
            //	user = "TEST";
            //	conn = ConnectionManager.getTestConnection();
            //}
            ////else
            //{
            conn = ConnectionManager.getConnection();
            //}

            stmt = conn.createStatement();

            String sqlQuery = "select o.is_void  from owd_order o where  "
                    + "o.order_num = \"" + orderNum + "\" ";

            rs = stmt.executeQuery(sqlQuery);


            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    throw new Exception(".Order has been voided!");
                }
            } else {
                throw new Exception(".Order could not be found!");
            }

            rs.close();
            stmt.close();


            stmt = conn.createStatement();
            sqlQuery = "update owd_order set pick_by = \"\", pick_status=" + kStatusUnpicked + " where order_num = \"" + orderNum + "\"";

            int rows = stmt.executeUpdate(sqlQuery);
            if (rows < 1)
                throw new Exception(".Problem with server - Please try again.");

            stmt.close();
            stmt = conn.createStatement();
            sqlQuery = "insert into pick_events (picker,pick_cancel,order_num) values ( \"" + user + "\",1,\"" + orderNum + "\")";

            rows = stmt.executeUpdate(sqlQuery);
            stmt.close();

            conn.commit();
            sendRFSuccess(resp, "");

        } catch (Throwable th) {

            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                ConnectionManager.freeConnection(conn);
            } catch (Exception ex) {
            }
        }
    }

    protected void _startPick(HttpServletRequest req, HttpServletResponse resp) {

        StringBuffer sb = new StringBuffer();
        StringBuffer hdr = new StringBuffer();
        int itemCount = 0;

        String user = getStringParam(req, kUserNameTag, "");
        String orderNum = getStringParam(req, kOrderBarcodeTag, "");


        java.sql.Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer selector = new StringBuffer();


        try {
            //	if(user.startsWith("TEST") || user.equals(""))
            //	{
            //		user = "TEST";
            //		conn = ConnectionManager.getTestConnection();
            //	}
            //	else
            //{
            conn = ConnectionManager.getConnection();
            //}

            stmt = conn.createStatement();

            String sqlQuery = "select o.is_void, ISNULL(o.pick_status,0), ISNULL(o.pick_by,\"\"),i.inventory_id, ISNULL(i.front_location,\"UNKNOWN\") as location, "
                    + "ISNULL(l.quantity_actual,0), l.inventory_num,i.description+\" \"+ISNULL(i.item_color,\"\")+\" \"+ISNULL(i.item_size,\"\") as descr "
                    + "from owd_order o (NOLOCK) inner join owd_line_item l (NOLOCK) left outer join owd_inventory i (NOLOCK) on i.inventory_id = l.inventory_id"
                    + " on o.order_id = l.order_fkey where  l.quantity_actual > 0 and  "
                    + "o.order_num = \"" + orderNum + "\" order by location, l.inventory_num";

            stmt.executeQuery(sqlQuery);

            rs = stmt.getResultSet();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    throw new Exception(".Order has been voided!");
                } else if (rs.getInt(2) == kStatusPicking) {
                    throw new Exception(".Order is being picked by " + rs.getString(3));
                } else if (rs.getInt(2) == kStatusPicked) {
                    throw new Exception(".Order has already been picked by " + rs.getString(3));
                }
                if (!(rs.getString(7).endsWith("-KIT"))) {
                    String skuCode = rs.getString(7);
                    ////log.debug("got SKU2 "+skuCode);
                    if (skuCode.length() > 16)
                        skuCode = rs.getString(4);

                    sb.append(rs.getString(4) + "\t" + rs.getString(6) + "\t" + rs.getString(5) + "\t" + skuCode + "\t" + rs.getString(8) + "\n");
                    itemCount++;
                }
            }

            rs.close();
            stmt.close();

            if (itemCount < 1) {
                com.owd.core.Mailer.postMailMessage("Error for RFServlet startPick cnt=" + itemCount, sqlQuery, "owditadmin@owd.com", "owditadmin@owd.com");

                throw new Exception(".No pickable items in order");
            }

            stmt = conn.createStatement();
            sqlQuery = "update owd_order set pick_by = \"" + user + "\", pick_status=" + kStatusPicking + " where order_num = \"" + orderNum + "\"";

            int rows = stmt.executeUpdate(sqlQuery);
            if (rows < 1)
                throw new Exception(".Problem with server - Please try again.");

            stmt.close();

            stmt = conn.createStatement();
            sqlQuery = "insert into pick_events (picker,pick_start,order_num) values ( \"" + user + "\",1,\"" + orderNum + "\")";

            rows = stmt.executeUpdate(sqlQuery);
            stmt.close();

            conn.commit();
            sendRFSuccess(resp, sb.toString());

        } catch (Throwable th) {

            try {
                conn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                ConnectionManager.freeConnection(conn);
            } catch (Exception ex) {
            }
        }


    }


    protected String getStringParam(HttpServletRequest req, String paramName, String defaultValue) {
        String param = req.getParameter(paramName);

        if (param == null)
            param = defaultValue;

        return param;

    }

    protected int getIntegerParam(HttpServletRequest req, String paramName, int defaultValue) {
        int param = 0;

        try {
            param = new Integer(req.getParameter(paramName)).intValue();
        } catch (Exception ex) {
            param = defaultValue;
        }

        return param;

    }


    static final public String kServletName = "RFAppManager";

    public String getServletInfo() {
        return "One World RF Applications Manager ";
    }

    private void sendRFSuccess(HttpServletResponse resp, String data) throws Exception {
        resp.getWriter().write("SUCCESS\n");

        if (data == null) data = "";

        resp.getWriter().write(data);
    }

    private void sendRFError(HttpServletResponse resp, String err) throws Exception {
        if (err == null) err = "";
        resp.getWriter().write("ERROR\n");
        int linesSent = 1;
        while (linesSent < 4 && err.length() > 19) {
            resp.getWriter().write(err.substring(0, 19));
            resp.getWriter().write("\t");
            err = err.substring(19);
            linesSent++;

        }

        resp.getWriter().write(err);
    }


}

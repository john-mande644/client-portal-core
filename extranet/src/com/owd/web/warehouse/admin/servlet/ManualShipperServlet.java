package com.owd.web.warehouse.admin.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderStatus;
import com.owd.web.servlet.ExtranetServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ManualShipperServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();

    public static final int kShipTypeLTL = 1;
    public static final int kShipTypeExpress = 2;
    public static final int kShipTypePickedUp = 3;


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

        String error = "";
        String ordernum = ExtranetServlet.getStringParam(req, "ordernum", "");
        int doShipmentFlag = ExtranetServlet.getIntegerParam(req, "doshipment", 0);
        OrderStatus order = null;
        try {
            if (doShipmentFlag == 0) {
                handleOrderLookup(req, resp, ordernum);
            } else {
                Connection cxn = null;
                Statement stmt = null;
                 Statement astmt = null;
                ResultSet rs = null;
                try {
                    String tracker = ExtranetServlet.getStringParam(req, "tracker", "");
                    String weight = ExtranetServlet.getStringParam(req, "weight", "");
                    String billed = ExtranetServlet.getStringParam(req, "billed", "");
                    String cost = ExtranetServlet.getStringParam(req, "cost", "");
                    String date = ExtranetServlet.getStringParam(req, "shipdate", "");
                    String orderid = ExtranetServlet.getStringParam(req, "orderid", "");
                    String ltlCarrier = ExtranetServlet.getStringParam(req, "LTLCarrier", "");


                    req.setAttribute("tracker", tracker);
                    req.setAttribute("weight", weight);
                    req.setAttribute("billed", billed);
                    req.setAttribute("tracker", tracker);

                    req.setAttribute("cost", cost);
                    req.setAttribute("shipdate", date);
                    req.setAttribute("LTLCarrier", ltlCarrier);


                    try {
                        float tester = new Float(weight).floatValue();
                        if (tester <= 0)
                            throw new Exception("Weight must be a number greater than zero");
                    } catch (NumberFormatException ex) {
                        throw new Exception("Weight must be a number greater than zero");
                    }
                    try {
                        float tester = new Float(billed).floatValue();
                        if (tester < 0)
                            throw new Exception("Billed Amount must be a number greater than or equal to zero");
                    } catch (NumberFormatException ex) {
                        throw new Exception("Billed Amount must be a number greater than or equal to zero");
                    }
                    try {
                        float tester = new Float(cost).floatValue();
                        if (tester < 0)
                            throw new Exception("Actual Cost must be a number greater than or equal to zero");
                    } catch (NumberFormatException ex) {
                        throw new Exception("Actual Cost must be a number greater than or equal to zero");
                    }

                    if ("".equals(date))
                        throw new Exception("At least the date must be supplied to save the record");
                    int maxIndex = 0;


                    cxn = com.owd.core.managers.ConnectionManager.getConnection();
                    Vector ids = new Vector();
                    maxIndex = 0;
                    int startIndex = 1;

                    while (maxIndex == 0) {
                        stmt = cxn.createStatement();
                        stmt.execute("select max(ISNULL(line_index,0)) from owd_order_track where is_void=0 and line_index=" + startIndex + " and order_fkey =" + orderid);
                        rs = stmt.getResultSet();

                        if (rs.next()) {
                            if (rs.getInt(1) == 0) {
                                maxIndex = startIndex;
                            }
                        }
                        startIndex++;

                        rs.close();
                        stmt.close();
                    }
                    stmt = cxn.createStatement();
                    if (tracker.equals("")) tracker = "NONE";
                    String updateSQL = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                            + "msn,is_void,reported,email_sent) VALUES (" + orderid + "," + (maxIndex) + ",\'" + (ltlCarrier.length() > 0 ? ltlCarrier + tracker : tracker) + "\'," + weight + "," + billed + "," + cost + ",\'" + date + "\',\'" + date + "\',\'Manual\',getdate(),\'Manual\',0,0, 0,0)";
                    log.debug(updateSQL);
                    int rowsUpdated = stmt.executeUpdate(updateSQL);





                    if (rowsUpdated > 0) {
                        cxn.commit();
                        error = "Success!";
                        req.setAttribute("errormessage", error);
                        req.getSession(true).setAttribute("orderstatus", null);
                        req.getSession(true).setAttribute("MSS.shipmentType", null);

                        String esql = "exec update_order_shipment_info " + orderid;

            astmt = cxn.createStatement();

            astmt.executeUpdate(esql);

            cxn.commit();

                        req.getRequestDispatcher("/warehouse/admin/shipments/index.jsp").forward(req, resp);

                    } else {
                        cxn.rollback();
                        error = "Can't save this record - try again";
                        req.setAttribute("errormessage", error);
                        try {
                            req.getRequestDispatcher("/warehouse/admin/shipments/confirm.jsp").forward(req, resp);
                        } catch (Exception exe) {
                            exe.printStackTrace();
                        }


                    }
                } catch (Throwable ex) {
                    error = ex.getMessage();
                    req.setAttribute("errormessage", ex.getMessage());
                    log.debug("caught manual shipment exception");
                    ex.printStackTrace();
                    try {
                        req.getRequestDispatcher("/warehouse/admin/shipments/confirm.jsp").forward(req, resp);
                    } catch (Exception exe) {
                        log.debug("Error forwarding");
                        exe.printStackTrace();
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
                        astmt.close();
                    } catch (Exception ex) {
                    }
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }
                }

            }

        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();

            try {
                req.getRequestDispatcher("/warehouse/admin/shipments/index.jsp").forward(req, resp);
            } catch (Exception exe) {
                exe.printStackTrace();
            }
        }


    }


    void handleOrderLookup(HttpServletRequest req, HttpServletResponse resp, String ordernum) throws Exception {
//initial page
        try {
            OrderStatus order = null;
            Vector orders = OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderRef, ordernum, null);
            if (orders.size() > 0) {
                order = (OrderStatus) orders.elementAt(0);
            }
            if (order == null) {
                throw new Exception("Order reference " + ordernum + " was not found");
            }
            if (!order.is_posted)
                throw new Exception("Order reference " + ordernum + " has not been posted to the warehouse yet!");
            req.getSession(true).setAttribute("orderstatus", order);
            req.getSession(true).setAttribute("MSS.shipmentType", ExtranetServlet.getStringParam(req, "SUBMIT", ""));
            req.getRequestDispatcher("/warehouse/admin/shipments/confirm.jsp").forward(req, resp);
        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();

            req.getRequestDispatcher("/warehouse/admin/shipments/index.jsp").forward(req, resp);
        }
    }


}

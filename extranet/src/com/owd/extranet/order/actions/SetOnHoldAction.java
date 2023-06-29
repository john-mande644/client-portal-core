package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.EventFeeds;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.ConnectionManager;
import com.owd.extranet.servlet.ExtranetServlet;
import com.owd.extranet.servlet.OrdersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 1, 2007
 * Time: 2:29:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class SetOnHoldAction {
private final static Logger log =  LogManager.getLogger();
    public static void action(HttpServletRequest req, HttpServletResponse resp) {
        String error = null;
        try {

            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(OrdersManager.kCurrentOrder,
                    status);
            Connection cxn = null;
            Statement stmt = null;

            try {


                cxn = ConnectionManager.getConnection();

                String esql = "update owd_order set  is_future_ship = 1  where order_id = " + status.order_id+ " and post_date is null";
                stmt = cxn.createStatement();

                int rowsUpdated = stmt.executeUpdate(esql);

                if (rowsUpdated < 1)
                    throw new Exception("Order not updated; could not set on hold");

                cxn.commit();
                Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "Order set on hold by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

                EventFeeds.reportOrderHeld(new Integer(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, "")).intValue(),Integer.parseInt(status.client_id),
                        EventFeeds.kManualSourceType,"Order set on hold by " + req.getUserPrincipal().getName());

                req.setAttribute(OrdersManager.kCurrentOrder,
                        new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));

                req.getRequestDispatcher("editorder.jsp").include(req, resp);

            } catch (Exception ex) {
                cxn.rollback();
                ex.printStackTrace();
                error = "Error processing order - please report to noop@owd.com<BR>Error: " + ex.getMessage();
                req.setAttribute("formerror", error);
                req.getRequestDispatcher("editorder.jsp").include(req, resp);
            } finally {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
                try {
                    cxn.close();
                } catch (Exception ex) {
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

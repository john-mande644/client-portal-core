package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.extranet.servlet.ExtranetServlet;
import com.owd.extranet.servlet.OrdersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 1, 2007
 * Time: 2:29:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class CloneToNewOrderAction {
private final static Logger log =  LogManager.getLogger();
    public static void action(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String error = null;
            try {
                OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                ClientPolicy policy = Client.getClientForID(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).getClientPolicy();

                req.setAttribute(OrdersManager.kCurrentOrder,
                        status);

                Order order = status.createOrderFromOrderStatus(policy.createInitializedOrder(), true);
                req.getSession(true).setAttribute("client_id", ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                req.getSession(true).setAttribute("currorder", order);
                order.contactID = "";
                order.order_type = "OWD Order Entry";

                req.getSession(true).setAttribute("customFields", policy.getCustomOrderFields(order));
                req.getSession(true).setAttribute("shipoptions", null);

                Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "Order cloned by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

                resp.sendRedirect(req.getContextPath() + "/callcenter/orderentry/orderentry.jsp");

            } catch (Exception ex) {
                ex.printStackTrace();
                error = "Error processing order - please report to servicerequests@owd.com<BR>Error: " + ex.getMessage();
                req.setAttribute("formerror", error);
                req.getRequestDispatcher("editorder.jsp").include(req, resp);
            } finally {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


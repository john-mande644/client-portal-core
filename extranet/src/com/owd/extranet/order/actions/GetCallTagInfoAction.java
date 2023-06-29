package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.client.ClientPolicy;
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
public class GetCallTagInfoAction {
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

                req.setAttribute("ct_name",status.shipping.shipContact.getName())  ;
                req.setAttribute("ct_company_name",status.shipping.shipAddress.company_name)  ;
                req.setAttribute("ct_address_one",status.shipping.shipAddress.address_one)  ;
                req.setAttribute("ct_address_two",status.shipping.shipAddress.address_two)  ;
                req.setAttribute("ct_city",status.shipping.shipAddress.city)  ;
                req.setAttribute("ct_state",status.shipping.shipAddress.state)  ;
                req.setAttribute("ct_zip",status.shipping.shipAddress.zip)  ;
                req.setAttribute("ct_email",status.shipping.shipContact.email)  ;
                req.setAttribute("ct_phone",status.shipping.shipContact.phone)  ;
                req.setAttribute("ct_declared_value","100.00")  ;
                req.setAttribute("ct_package_weight",status.getTotalShipmentWeight()+"")  ;


                req.getRequestDispatcher("requestcalltag.jsp").include(req, resp);

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

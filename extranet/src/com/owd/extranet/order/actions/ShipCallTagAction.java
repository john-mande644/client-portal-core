package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Address;
import com.owd.core.business.Client;
import com.owd.core.business.Contact;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.ManifestingManager;
import com.owd.core.xml.OrderXMLDoc;
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
public class ShipCallTagAction {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
        log.debug( (new OrderStatus("7999730",
                "489")).getLocation());
    }
    public static void action(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String error = null;
            try {
                OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                ClientPolicy policy = Client.getClientForID(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).getClientPolicy();

                req.setAttribute(OrdersManager.kCurrentOrder,
                        status);

                float packageWeight = -1f;
               /* String shipperCode = ExtranetServlet.getStringParam(req, "ct_shipper_code", "OWD");
                if (shipperCode.length() != 3) {
                    throw new Exception("Shipper code " + shipperCode + " is invalid");
                }*/

                try {
                    packageWeight = Float.parseFloat(ExtranetServlet.getStringParam(req, "ct_package_weight", "0.00"));
                    if (packageWeight <= 0.01) {
                        throw new Exception("insufficient weight value for calltag creation");
                    }
                } catch (Exception ex) {
                    throw new Exception("Package weight must be a decimal number greater than zero");
                }

                Address pickupAddress = new Address();
                Contact pickupContact = new Contact();

                pickupContact.setName(ExtranetServlet.getStringParam(req, "ct_name", ""));
                pickupAddress.company_name = ExtranetServlet.getStringParam(req, "ct_company_name", "");
                pickupAddress.address_one = ExtranetServlet.getStringParam(req, "ct_address_one", "");
                pickupAddress.address_two = ExtranetServlet.getStringParam(req, "ct_address_two", "");
                pickupAddress.city = ExtranetServlet.getStringParam(req, "ct_city", "");
                pickupAddress.state = ExtranetServlet.getStringParam(req, "ct_state", "");
                pickupAddress.zip = ExtranetServlet.getStringParam(req, "ct_zip", "");
                pickupContact.phone = ExtranetServlet.getStringParam(req, "ct_phone", "");
                pickupContact.email = ExtranetServlet.getStringParam(req, "ct_email", "");

                Order order = status.createCallTagOrder(pickupAddress, pickupContact, "UPS Ground", packageWeight);

                order.getShippingInfo().declared_value = ExtranetServlet.getStringParam(req, "ct_declared_value", "100.00");
                //verify address

                req.getSession(true).setAttribute("currorder", order);

                String reference = order.saveNewOrder(OrderXMLDoc.kPaymentStatusClientManaged, false);
                if (reference == null) {
                    throw new Exception("Unable to save calltag order : " + order.last_error);
                } else {
                    OrderStatus calltag = new OrderStatus(order.orderID);
                    log.debug("calltag order status: "+calltag.getStatusString());
                    try {
                        if (calltag.getStatusString().toUpperCase().contains("WAREHOUSE")) {
                            Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "Calltag order " + reference + " created by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());
                            Event.addOrderEvent(new Integer(order.orderID).intValue(), Event.kEventTypeHandling, "Calltag order created from " + status.OWDorderReference + " by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

                            calltag = ManifestingManager.shipCallTagOrder(calltag, packageWeight, calltag.orderReference, status.getLocation());

                            req.getSession(true).setAttribute("currorder", calltag);
                            resp.sendRedirect(req.getContextPath() + "/extranet/extranet.jsp?ordermgr=edit&oid="+calltag.order_id);
                        } else {
                            throw new Exception("Unable to confirm released order status for calltag order");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        try {
                                 if (calltag.is_posted) {
                                     calltag.unpostOrder();
                                 } else {
                                     throw new Exception("Order is not currently in posted status; cannot unpost this order.");
                                 }
                             }
                             catch (Exception exx) {
                                 exx.printStackTrace();
                             } finally {

                             }
                        calltag.voidOrder();
                        req.setAttribute(OrdersManager.kCurrentOrder, status);
                        throw new Exception(ex.getMessage());
                    }
                }

                req.getRequestDispatcher("editorder.jsp").include(req, resp);

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

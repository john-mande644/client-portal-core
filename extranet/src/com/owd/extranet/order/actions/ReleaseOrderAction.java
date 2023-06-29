package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.extranet.servlet.ExtranetServlet;
import com.owd.extranet.servlet.OrdersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 1, 2007
 * Time: 2:26:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReleaseOrderAction {
private final static Logger log =  LogManager.getLogger();
    public static void action(HttpServletRequest req, HttpServletResponse resp) {
        String error = null;
        try {

            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(OrdersManager.kCurrentOrder,
                    status);
            Connection cxn = null;

            try {
                cxn = ConnectionManager.getConnection();
                int units = 0;

                if ("Ship Checked Items Only".equals(ExtranetServlet.getStringParam(req, "shipOrder", ""))) {

                    for (int i = 0; i < status.items.size(); i++) {
                        //update line item quantities
                        LineItem item = (LineItem) status.items.elementAt(i);


                        if (item.parent_line==null && !(ExtranetServlet.getIntegerParam(req, "ship_item_" + item.line_item_id, 0) == 1)) {
                            item.force_backorder_quantity = true;

                            item.quantity_backordered = item.quantity_request;
                            item.quantity_request=0;


                        }

                    }
                }

                Map skuMap = OrderUtilities.updateLineItemsForAvailability(cxn, status.items, OrderXMLDoc.kPartialShip, true, FacilitiesManager.getFacilityForCode(status.shipLocation).getId());
                for (int i = 0; i < status.items.size(); i++) {
                    //update line item quantities
                    LineItem item = (LineItem) status.items.elementAt(i);
                        if(!item.insertedItem)
                        {
                    units += item.quantity_actual;
                        }
                }


                if (units < 1)
                    throw new Exception("No shippable items found - ship request cancelled");

                cxn.rollback();


                req.getSession(true).setAttribute(OrdersManager.kCurrentOrder, status);

                req.getRequestDispatcher("shipmethod.jsp").include(req, resp);

            } catch (Exception ex) {
                cxn.rollback();
                ex.printStackTrace();
                error = "Error processing order - please report to casetracker@owd.com";
                req.setAttribute("formerror", error);
                try {
                    req.getSession(true).removeAttribute(OrdersManager.kCurrentOrder);
                } catch (Throwable th) {
                }

                req.getRequestDispatcher("editorder.jsp").include(req, resp);
            } finally {
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

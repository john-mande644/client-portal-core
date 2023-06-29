package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderStatus;
import com.owd.extranet.servlet.ExtranetServlet;
import com.owd.extranet.servlet.OrdersManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 1, 2007
 * Time: 2:26:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddItemAction {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) throws Exception
    {

    }
    public static void action(HttpServletRequest req, HttpServletResponse resp) {
        String error = null;
        try {
            log.debug("1");
            OrderStatus status1 = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(OrdersManager.kCurrentOrder,
                    status1);

            String skuStr = ExtranetServlet.getStringParam(req, "newsku", "");
            String qtyStr = ExtranetServlet.getStringParam(req, "newqty", "0");
            String priceStr = ExtranetServlet.getStringParam(req, "newprice", "0.00");
            String descStr = ExtranetServlet.getStringParam(req, "newdesc", "").trim();

            try {

            log.debug("1");

                  if(status1.is_posted || status1.is_shipped || status1.is_void)
                {
                    throw new Exception("Orders in status "+status1.getStatusString()+" cannot be edited. Order must be unposted before editing - item cannot be added");
                }

            log.debug("1");
                try
                {
                     int qty = Integer.parseInt(qtyStr);
                    if(qty<1)
                    {
                       throw new Exception();
                    }
                }   catch(Exception itemex)
                {
                    throw new Exception("Quantity value "+qtyStr+" not valid - quantity must be a whole number greater than zero");
                }
              try
                {
                      double price = Double.parseDouble(priceStr);
                    if(price<0.00)
                    {
                       throw new Exception();

                    }
                }   catch(Exception itemex)
                {
                    throw new Exception("Price value "+qtyStr+" not valid - price must be a decimal number greater or equal to zero");
                }
            log.debug("1");

                OrderStatus.addLineItemToExistingOrder(ExtranetServlet.getIntegerParam(req, OrdersManager.kparamOrderID, 0),
                        Integer.parseInt(status1.client_id),skuStr,descStr.length()>0?descStr:"",Integer.parseInt(qtyStr),Float.parseFloat(priceStr),0.00f);

            log.debug("1");
                status1 = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

            log.debug("1");
              req.setAttribute(OrdersManager.kCurrentOrder,
                    status1);

                req.getRequestDispatcher("editorder.jsp").include(req, resp);

            } catch (Exception ex) {
                HibUtils.rollback(HibernateSession.currentSession());
                ex.printStackTrace();
                error = "ERROR: "+ex.getMessage();
                req.setAttribute("formerror", error);
                try {
                    req.getSession(true).removeAttribute(OrdersManager.kCurrentOrder);
                } catch (Throwable th) {
                }

                req.getRequestDispatcher("editorder.jsp").include(req, resp);
            } finally {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderStatus;
import com.owd.extranet.servlet.ExtranetServlet;
import com.owd.extranet.servlet.OrdersManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 1, 2007
 * Time: 2:26:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateTotalsAction {
private final static Logger log =  LogManager.getLogger();
    public static void action(HttpServletRequest req, HttpServletResponse resp) {
        String error = null;
        try {

            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(OrdersManager.kCurrentOrder,
                    status);


            try {
                String taxValueStr = ExtranetServlet.getStringParam(req,"taxValue","0.00");
                String discountValueStr = ExtranetServlet.getStringParam(req,"discountValue","0.00");
                String shipValueStr = ExtranetServlet.getStringParam(req,"shipValue","0.00");

                if(status.is_posted)
                {
                    throw new Exception("Cannot update totals - order has already been released to ship");
                }
                  if(status.is_void)
                {
                    throw new Exception("Cannot update totals - order has been voided");
                }

                double taxValue = 0.00;
                double discountValue = 0.00;
                double shipValue = 0.00;

                 try{
                     taxValue=new Double(taxValueStr.length()==0?"0.00":taxValueStr);
                 }   catch(Exception ex)
                 {
                     throw new Exception("Invalid tax value "+taxValueStr+" - use a valid number like 2.99 and try again.");
                 }
                 try{
                     shipValue=new Double(shipValueStr.length()==0?"0.00":shipValueStr);
                 }   catch(Exception ex)
                 {
                     throw new Exception("Invalid shipping value "+shipValueStr+" - use a valid number like 2.99 and try again.");
                 }
                 try{
                     discountValue=new Double(discountValueStr.length()==0?"0.00":discountValueStr);
                 }   catch(Exception ex)
                 {
                     throw new Exception("Invalid discount value "+discountValueStr+" - use a valid number like 2.99 and try again.");
                 }

                if(discountValue<0.00 || shipValue <0.00 || taxValue<0.00)
                {
                    throw new Exception("Tax, shipping and discount values must be 0.00 or more - negative numbers are not allowed. Please re-enter your values and try again.");
                }
                OwdOrder order = (OwdOrder)HibernateSession.currentSession().load(OwdOrder.class,ExtranetServlet.getIntegerParam(req, OrdersManager.kparamOrderID, 0));

                order.setTaxAmount(new BigDecimal(taxValue));
                order.setShipHandlingFee(new BigDecimal(shipValue));
                order.setDiscount(new BigDecimal(discountValue*-1.00));

                    log.debug("1");
                OrderStatus.applyNewInvoiceTotalsToOwdOrder(order);

               HibernateSession.currentSession().save(order);
            log.debug("1");
                HibUtils.commit(HibernateSession.currentSession());

            log.debug("1");
                status = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

            log.debug("1");
              req.setAttribute(OrdersManager.kCurrentOrder,
                    status);

                req.getRequestDispatcher("editorder.jsp").include(req, resp);
            } catch (Exception ex) {

                ex.printStackTrace();
                HibUtils.rollback(HibernateSession.currentSession());
                error = ex.getMessage();
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

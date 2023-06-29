package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderStatus;
import com.owd.extranet.servlet.ExtranetServlet;
import com.owd.extranet.servlet.OrdersManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 1, 2007
 * Time: 2:26:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveItemAction {
private final static Logger log =  LogManager.getLogger();
    public static void action(HttpServletRequest req, HttpServletResponse resp) {
        String error = null;
        try {
            log.debug("1");
            OrderStatus status1 = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(OrdersManager.kCurrentOrder,
                    status1);

            String itemIDStr = ExtranetServlet.getStringParam(req, "lineid", "0");
            try {

                removeLineItem( status1, itemIDStr);

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

    private static void removeLineItem( OrderStatus status1, String itemIDStr) throws Exception {
        int itemID = 0;


        log.debug("1");

        if(status1.is_posted || status1.is_shipped || status1.is_void)
      {
          throw new Exception("Order status "+status1.getStatusString()+" cannot be edited. Order must be unposted before editing - item cannot be removed");
      }

        log.debug("1");
        try
        {
             itemID = Integer.parseInt(itemIDStr);
        }   catch(Exception itemex)
        {
            throw new Exception("Item ID "+itemIDStr+" not valid - item cannot be removed");
        }

        log.debug("1");
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.decode(status1.order_id));


        log.debug("1");
        List<OwdLineItem> itemlist = new ArrayList<OwdLineItem>();
        for(OwdLineItem lineitem: order.getLineitems())
        {
            if(itemID == lineitem.getLineItemId())
            {
                itemlist.add(lineitem);
            } else if(itemID == (lineitem.getParentKey()==null?-1:lineitem.getParentKey()))
            {
                itemlist.add(lineitem);
            }
        }

        log.debug("1");
        if(itemlist.size()==0)
        {
            throw new Exception("Item ID "+itemIDStr+" not found in order - item cannot be removed");
        }

        log.debug("1");
        if(itemlist.size()==order.getLineitems().size())
        {
            throw new Exception("You cannot remove the last item from an order - add a new item before removing this one");
        }

        log.debug("1");
        //got valid item
        order.getLineitems().removeAll(itemlist);
        for(OwdLineItem lineitem:itemlist)
        {
            HibernateSession.currentSession().delete(lineitem);

        }
        log.debug("1");
        OrderStatus.applyNewInvoiceTotalsToOwdOrder(order);

        HibernateSession.currentSession().save(order);
        log.debug("1");
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void main(String[] args) throws Exception{


          }
}

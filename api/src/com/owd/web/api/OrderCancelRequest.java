package com.owd.web.api;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.w3c.dom.Element;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;


public class OrderCancelRequest implements APIRequestHandler
{
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_ORDER_CANCEL_REQUEST";


    //root node attributes

    public static final String kOrderRef = "clientOrderId";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception
    {

        OrderStatusResponse response = new OrderStatusResponse(api_version);

        List orderIDList = ConnectionManager.getOrderKeyForClientID(root.getAttribute(kOrderRef), client.client_id, "FALSE");

        log.debug("got first list size=" + orderIDList.size() + " for " + root.getAttribute(kOrderRef) + " and " + client.client_id);
        if (orderIDList.size() < 1)
        {

            String orderID = ConnectionManager.getOrderKey(root.getAttribute(kOrderRef), client.client_id);
            log.debug("got order ID " + orderID + " for " + root.getAttribute(kOrderRef) + " and " + client.client_id);

            if (orderID != null)
            {
                orderIDList.add(orderID);
            } else
            {
                throw new APIContentException("Order ID not recognized");
            }
        }
        log.debug("got orderlist size=" + orderIDList.size());
        if (orderIDList.size() > 1)
        {
            throw new APIContentException("Multiple orders returned by search; an unambiguous order reference must be used instead");
        }
        String orderID = (String) orderIDList.get(0);


        if (orderID == null)
        {
            orderID = ConnectionManager.getOrderKey(root.getAttribute(kOrderRef), client.client_id);
        }


        if (orderID == null)

            throw new APIContentException("Order ID not recognized");


        OrderStatus order = new OrderStatus(orderID);
        if ((order.is_void))
        {
        } else
        {
            if (order.is_shipped || order.isShipping)
            {
                throw new APIContentException("This order has already been shipped");
            } else
            {
                if (order.is_unpicked)
                {
                    if (order.is_posted)
                    {

                        OwdOrder hOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(order.order_id));
                        Collection scanDocs = hOrder.getScanDocs();
                        if (hOrder.getScanDocs() != null)
                        {
                            //we may have recorded prints, let's verify
                            if (hOrder.getScanDocs().size() > 0)
                            {
                                int inQueue = 0;

                                ResultSet rs = HibernateSession.getResultSet("select count(*) from owd_print_queue3 (NOLOCK) where order_id=" + order.order_id);
                                if (rs.next())
                                {
                                    inQueue += rs.getInt(1);
                                }
                                rs.close();
                                rs = HibernateSession.getResultSet("select count(*) from owd_print_queue_sl (NOLOCK) where order_id=" + order.order_id);
                                if (rs.next())
                                {
                                    inQueue += rs.getInt(1);
                                }
                                if (inQueue > 0)
                                {
                                    //we have a print request pending, so allow it
                                    voidOrder(order);
                                } else
                                {
                                    //error - order appears to have been successfully printed or sent to print table
                                    throw new APIContentException("This order has already been released and begun fulfillment. Please contact OWD directly if you need to modify or cancel this order.");
                                }

                            } else
                            {
                                //we don't have any recorded prints for this order, allow void
                                voidOrder(order);
                            }
                        } else
                        {
                            //we don't have any recorded prints for this order, allow void
                            voidOrder(order);
                        }
                        HibernateSession.currentSession().evict(hOrder);
                        HibUtils.commit(HibernateSession.currentSession());
                    } else
                    {

                        if (testing)
                        {
                            order.is_void = true;
                        } else
                        {
                            voidOrder(order);
                        }
                    }
                } else
                {
                    throw new APIContentException("This order has already been picked for shipping. Please contact OWD directly if you need to modify or cancel this order.");
                }
            }

        }

        order = new OrderStatus(orderID);
        response.buildFromOrderStatus(order);


        return response;

    }

    private void voidOrder(OrderStatus order) throws Exception
    {
        if (!order.is_unpicked)
        {
            order.unpickOrder();
        }
        if (order.is_posted)
        {
            order.unpostOrder();
        }
        order.voidOrder();


    }

}

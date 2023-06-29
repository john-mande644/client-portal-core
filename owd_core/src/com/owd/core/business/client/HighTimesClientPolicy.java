package com.owd.core.business.client;

import com.owd.core.Mailer;
import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: 2/3/11
 * Time: 8:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class HighTimesClientPolicy extends com.owd.core.business.client.DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    @Override
    public Order createInitializedOrder() {
        Order order = super.createInitializedOrder();    //To change body of overridden methods use File | Settings | File Templates.
        order.noVirtualOnlyOrders = false;
        return order;
    }

    @Override
    public void saveNewOrder(Order order, boolean b) throws Exception {

        if(order.getShippingAddress().isInternational() && order.getBillingAddress().isUS() && order.total_order_cost>50.00f)
        {
            order.is_future_ship=1;

        }
        super.saveNewOrder(order, b);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void sendNotificationMessage(Order order, String s, String s1) {
        if(order.getShippingAddress().isInternational() && order.getBillingAddress().isUS() && order.total_order_cost>50.00f)
        {

            try
            {
            Mailer.sendMail("Fraud Check - Order Ref "+order.order_refnum+"/"+order.orderNum,"This order has been held to review for possible fraud.","transhighcorp@owd.com","donotreply@owd.com");
            }catch (Exception ex)      {
                ex.printStackTrace();
            }

        }
        super.sendNotificationMessage(order, s, s1);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void sendCustomerEmailConfirmation(Order order) {
        super.sendCustomerEmailConfirmation(order);    //To change body of overridden methods use File | Settings | File Templates.
    }
}

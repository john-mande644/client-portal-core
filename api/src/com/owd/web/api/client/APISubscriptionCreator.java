package com.owd.web.api.client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderUtilities;import com.owd.core.business.order.Order;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 29, 2007
 * Time: 3:31:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface APISubscriptionCreator {

    public void createSubscription(Order o) throws Exception;
    public List getSubscriptionItems();


}

package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by stewartbuskirk1 on 2/20/14.
 */
public class SneakpeeqClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    @Override
    public void saveNewOrder(Order order, boolean testing) throws Exception {


        super.saveNewOrder(order, testing);
    }

    @Override
    public void handleCustomOrderFields(Order order, List fields) {
        order.noduplicates=false;
        super.handleCustomOrderFields(order, fields);
    }
}

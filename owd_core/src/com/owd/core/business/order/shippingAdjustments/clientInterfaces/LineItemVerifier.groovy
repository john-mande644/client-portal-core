package com.owd.core.business.order.shippingAdjustments.clientInterfaces

import com.owd.hibernate.generated.OwdLineItem
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 8/30/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class LineItemVerifier {
private final static Logger log =  LogManager.getLogger();

    abstract public boolean includeLineItemInRequest(OwdLineItem line);
}

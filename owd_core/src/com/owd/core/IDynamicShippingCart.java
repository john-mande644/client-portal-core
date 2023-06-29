package com.owd.core;

import com.owd.core.business.order.Order;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Nov 9, 2005
 * Time: 11:40:09 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IDynamicShippingCart {

    public List getMethods();
    public Order getOrder();
    public void addShipping(String name, String code, String total_cost);
    public List getShipCosts();

}

package com.owd.dc.warehouse.personalized;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 12, 2008
 * Time: 9:33:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class borOrderInfo {
    private String orderNum;
    private String orderId;
    private String info;
    private Map items;
    private String clientId;
    private String orderRefnum;

    public String getOrderRefnum() {
        return orderRefnum;
    }

    public void setOrderRefnum(String orderRefnum) {
        this.orderRefnum = orderRefnum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Map getItems() {
        return items;
    }

    public void setItems(Map items) {
        this.items = items;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

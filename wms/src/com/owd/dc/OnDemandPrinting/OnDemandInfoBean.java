package com.owd.dc.OnDemandPrinting;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 7/13/14
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class OnDemandInfoBean {
    private String pickMessage="";
    private String packMessage="";
    private boolean onDemand;
    private String orderId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPickMessage() {
        return pickMessage;
    }

    public void setPickMessage(String pickMessage) {
        this.pickMessage = pickMessage;
    }

    public String getPackMessage() {
        return packMessage;
    }

    public void setPackMessage(String packMessage) {
        this.packMessage = packMessage;
    }

    public boolean isOnDemand() {
        return onDemand;
    }

    public void setOnDemand(boolean onDemand) {
        this.onDemand = onDemand;
    }
}

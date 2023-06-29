package com.owd.jobs.jobobjects.utilities;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 16, 2007
 * Time: 10:04:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderScanInfoBean extends ScanInfoBean {

      private String orderId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFileName(){
        return  getOrderId()+ "_" + getDate() + "_" + getTime() + ".pdf";
    }

}

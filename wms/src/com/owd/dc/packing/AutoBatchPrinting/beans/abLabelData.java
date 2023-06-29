package com.owd.dc.packing.AutoBatchPrinting.beans;

/**
 * Created by danny on 2/6/2017.
 */
public class abLabelData {


    private String orderId;
    private String packageId;
    private String name;
    private String labelString;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelString() {
        return labelString;
    }

    public void setLabelString(String labelString) {
        this.labelString = labelString;
    }
}

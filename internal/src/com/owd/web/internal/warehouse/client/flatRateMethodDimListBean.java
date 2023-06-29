package com.owd.web.internal.warehouse.client;

public class flatRateMethodDimListBean {

    private int id;
    private int threshold;
    private String flatRateMethod;
    private String shipMethod;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getFlatRateMethod() {
        return flatRateMethod;
    }

    public void setFlatRateMethod(String flatRateMethod) {
        this.flatRateMethod = flatRateMethod;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }
}

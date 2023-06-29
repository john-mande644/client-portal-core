package com.owd.dc.warehouse.packSlipRelease;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 2/11/14
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class detailedReprintBean {

    private String orderId;
    private String orderNum;
    private String client;
    private String shipMethod;
    private String SLA;
    private String fingerprint;
    private String groupName;
    private String packed;
    private String units;
    private String description;
    private String aisles;
    private String zones;
    private String boxType;

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

    public String getAisles() {
        return aisles;
    }

    public void setAisles(String aisles) {
        this.aisles = aisles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public String getSLA() {
        return SLA;
    }

    public void setSLA(String SLA) {
        this.SLA = SLA;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getPacked() {
        return packed;
    }

    public void setPacked(String packed) {
        if(packed.equals("0")){
            this.packed = "";
        } else{
            this.packed = "Packed";
        }


    }
}

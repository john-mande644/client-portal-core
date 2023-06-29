package com.owd.dc.manifest.api.internal;

/**
 * Created by danny on 9/24/2015.
 */
public class postShipmentBean {
private String orderFkey;
    private String lineIndex;
    private String tracking;
    private String weight;
    private String totalBilled;
    private String costOfGoods;
    private String msn;
    private String shipper;
    private String facility;
    private String user;
    private String ipAddress;
    private String packageBarcode;
    private String insValue;
    private String insCost;
    private String weightNotes;
    private String customsDocs;
    private String shipMethod;
    private String shipMethodName;
    private String shipMethodNameOriginal;
    private String serivceCodeOriginal;
    private String labelCode;
    private String carrierCode;
    private String serviceCode;
    private String bundleId;
    private boolean isThirdPartyBilling = false;

    public boolean isThirdPartyBilling() {
        return isThirdPartyBilling;
    }

    public void setIsThirdPartyBilling(boolean isThirdPartyBilling) {
        this.isThirdPartyBilling = isThirdPartyBilling;
    }

    public String getSerivceCodeOriginal() {
        return serivceCodeOriginal;
    }

    public void setSerivceCodeOriginal(String serivceCodeOriginal) {
        this.serivceCodeOriginal = serivceCodeOriginal;
    }

    public String getShipMethodName() {
        return shipMethodName;
    }

    public void setShipMethodName(String shipMethodName) {
        this.shipMethodName = shipMethodName;
    }

    public String getShipMethodNameOriginal() {
        return shipMethodNameOriginal;
    }

    public void setShipMethodNameOriginal(String shipMethodNameOriginal) {
        this.shipMethodNameOriginal = shipMethodNameOriginal;
    }

    public String getOrderFkey() {
        return orderFkey;
    }

    public void setOrderFkey(String orderFkey) {
        this.orderFkey = orderFkey;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTotalBilled() {
        return totalBilled;
    }

    public void setTotalBilled(String totalBilled) {
        this.totalBilled = totalBilled;
    }

    public String getCostOfGoods() {
        return costOfGoods;
    }

    public void setCostOfGoods(String costOfGoods) {
        this.costOfGoods = costOfGoods;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPackageBarcode() {
        return packageBarcode;
    }

    public void setPackageBarcode(String packageBarcode) {
        this.packageBarcode = packageBarcode;
    }

    public String getInsValue() {
        return insValue;
    }

    public void setInsValue(String insValue) {
        this.insValue = insValue;
    }

    public String getInsCost() {
        return insCost;
    }

    public void setInsCost(String insCost) {
        this.insCost = insCost;
    }

    public String getWeightNotes() {
        return weightNotes;
    }

    public void setWeightNotes(String weightNotes) {
        this.weightNotes = weightNotes;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getCustomsDocs() {
        return customsDocs;
    }

    public void setCustomsDocs(String customsDocs) {
        this.customsDocs = customsDocs;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}

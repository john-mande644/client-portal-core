package com.owd.OWDShippingAPI.tracking;


public class TrackingBean {
    private int orderFkey;
    private int lineIndex;
    private String trackingNumber;
    private String externalId;
    private double weight;
    private double totalBilled;
    private String location;
    private String carrierCode;
    private String serviceCode;
    private String packBarcode;
    private String label;
    private double billedWeight = 0;
    private String insuredAmount;
    private String insuranceCost;
    private String detailedPricing;
    private String thirdParty;
    private int customs ;
    private String isSaturday;
    private String isSignature ;
    private String isExpressPO ;


    public String getIsSaturday() {
        return isSaturday;
    }

    public void setIsSaturday(String isSaturday) {
        this.isSaturday = isSaturday;
    }

    public String getIsSignature() {
        return isSignature;
    }

    public void setIsSignature(String isSignature) {
        this.isSignature = isSignature;
    }

    public String getIsExpressPO() {
        return isExpressPO;
    }

    public void setIsExpressPO(String isExpressPO) {
        this.isExpressPO = isExpressPO;
    }

    public int getCustoms() {
        return customs;
    }

    public void setCustoms(int customs) {
        this.customs = customs;
    }

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public String getDetailedPricing() {
        return detailedPricing;
    }

    public void setDetailedPricing(String detailedPricing) {
        this.detailedPricing = detailedPricing;
    }

    public String getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(String insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public String getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(String insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public double getBilledWeight() {
        return billedWeight;
    }

    public void setBilledWeight(double billedWeight) {
        this.billedWeight = billedWeight;
    }

    public int getOrderFkey() {
        return orderFkey;
    }

    public void setOrderFkey(int orderFkey) {
        this.orderFkey = orderFkey;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTotalBilled() {
        return totalBilled;
    }

    public void setTotalBilled(double totalBilled) {
        this.totalBilled = totalBilled;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPackBarcode() {
        return packBarcode;
    }

    public void setPackBarcode(String packBarcode) {
        this.packBarcode = packBarcode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}

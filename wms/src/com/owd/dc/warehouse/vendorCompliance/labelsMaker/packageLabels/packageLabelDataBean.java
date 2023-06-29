package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

/**
 * Created by danny on 10/20/2016.
 */
public class packageLabelDataBean {
    
    
    private String shipFromName;
    private String shipFromAddress;
    private String shipFromCity;
    private String shipFromState;
    private String shipFromZip;

    private String shipToName;
    private String shipToAddress;
    private String shipToCity;
    private String shipToState;
    private String shipToZip;
    private String shipToAddress2;

    private String carrierName;
    private String trackingNumber="";
    private String purchaseOrder;
    private String upc;
    private String buyerPartNumber;
    private String qty;
    private String cartonNum;
    private String cartonOf;
    private String SSCC;
    private Boolean multiSku = false;
    private String edi850 = "";
    private String processingDate = "";
    private String orderReference ="";
    private int clientId =0;
    private String weight = "";

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    private String description = "";

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }

    public String getEdi850() {
        return edi850;
    }

    public void setEdi850(String edi850) {
        this.edi850 = edi850;
    }

    public String getShipToAddress2() {
        return shipToAddress2;
    }

    public void setShipToAddress2(String shipToAddress2) {
        this.shipToAddress2 = shipToAddress2;
    }

    public Boolean getMultiSku() {
        return multiSku;
    }

    public void setMultiSku(Boolean multiSku) {
        this.multiSku = multiSku;
    }

    public String getShipFromName() {
        return shipFromName;
    }

    public void setShipFromName(String shipFromName) {
        this.shipFromName = shipFromName;
    }

    public String getShipFromAddress() {
        return shipFromAddress;
    }

    public void setShipFromAddress(String shipFromAddress) {
        this.shipFromAddress = shipFromAddress;
    }

    public String getShipFromCity() {
        return shipFromCity;
    }

    public void setShipFromCity(String shipFromCity) {
        this.shipFromCity = shipFromCity;
    }

    public String getShipFromState() {
        return shipFromState;
    }

    public void setShipFromState(String shipFromState) {
        this.shipFromState = shipFromState;
    }

    public String getShipFromZip() {
        return shipFromZip;
    }

    public void setShipFromZip(String shipFromZip) {
        this.shipFromZip = shipFromZip;
    }

    public String getShipToName() {
        return shipToName;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    public String getShipToState() {
        return shipToState;
    }

    public void setShipToState(String shipToState) {
        this.shipToState = shipToState;
    }

    public String getShipToZip() {
        return shipToZip;
    }

    public void setShipToZip(String shipToZip) {
        this.shipToZip = shipToZip;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getBuyerPartNumber() { return buyerPartNumber; }

    public void setBuyerPartNumber(String buyerPartNumber) { this.buyerPartNumber = buyerPartNumber; }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCartonNum() {
        return cartonNum;
    }

    public void setCartonNum(String cartonNum) {
        this.cartonNum = cartonNum;
    }

    public String getCartonOf() {
        return cartonOf;
    }

    public void setCartonOf(String cartonOf) {
        this.cartonOf = cartonOf;
    }

    public String getSSCC() {
        return SSCC;
    }

    public void setSSCC(String SSCC) {
        this.SSCC = SSCC;
    }

    public String getSSCCNoCheckDigit(){
        if(SSCC.length()==18){
            return SSCC.substring(0,17);
        }
        return SSCC;
    }
    public String getShipToCityStateZip(){
        return shipToCity+", "+ shipToState + " " + shipToZip;
    }
    public String getShipFromCityStateZip(){
        return shipFromCity + ", " + shipFromState + " " + shipFromZip;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}

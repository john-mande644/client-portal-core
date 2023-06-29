package com.owd.dc.manifest.tracking;

import com.owd.dc.manifest.ExternalApis.deliverr.DeliverrApi;
import com.owd.dc.manifest.ExternalApis.deliverr.classes.DeliverrResponse;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;

public class TrackingBean {
    private int orderFkey;
    private int lineIndex;
    private String trackingNumber;
    private String extrnalId;
    private double weight;
    private double totalBilled;
    private String location;
    private String carrierCode;
    private String serviceCode;
    private String packBarcode;
    private String label;

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

    public String getExtrnalId() {
        return extrnalId;
    }

    public void setExtrnalId(String extrnalId) {
        this.extrnalId = extrnalId;
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

    public TrackingBean(AMPConnectShipShipment ship, DeliverrResponse resp, int index, String location) {
        try {
            this.orderFkey = Integer.parseInt(ship.getValueAsString(ShipConfig.OWD_ORDER_ID));
            this.lineIndex = index;
            this.extrnalId = resp.getId();
            this.trackingNumber = resp.getTrackingCode();
            this.weight = Double.parseDouble(ship.getValueAsString(ShipConfig.WEIGHT));
            this.totalBilled = 0.0;
            this.location = location;
            this.carrierCode = ship.getAssignedCarrierCode();
            this.serviceCode = ship.getAssignedServiceCode();
            this.packBarcode = ship.getValueAsString("PACKAGE_BARCODE");
            this.label = DeliverrApi.convert(resp.getContents(),resp.getCarrier().equalsIgnoreCase("usps"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

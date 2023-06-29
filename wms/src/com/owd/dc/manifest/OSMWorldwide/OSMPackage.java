package com.owd.dc.manifest.OSMWorldwide;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/23/14
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class OSMPackage {
    private String packageId;
    private String packageBarcode;
    private String labelBarcode;
    private String trackingNumber;
    private boolean postTracking;
    private double packageWeight;
    private double shipCost = 0.0d;
    private double length;
    private double width;
    private double height;
    private int packageIndex;

    public int getPackageIndex() {
        return packageIndex;
    }

    public void setPackageIndex(int packageIndex) {
        this.packageIndex = packageIndex;
    }

    public String getLabelBarcode() {
        return labelBarcode;
    }

    public void setLabelBarcode(String labelBarcode) {
        this.labelBarcode = labelBarcode;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageBarcode() {
        return packageBarcode;
    }

    public void setPackageBarcode(String packageBarcode) {
        this.packageBarcode = packageBarcode;
    }

    public double getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(double packageWeight) {
        this.packageWeight = packageWeight;
    }

    public double getShipCost() {
        return shipCost;
    }

    public void setShipCost(double shipCost) {
        this.shipCost = shipCost;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public boolean isPostTracking() {
        return postTracking;
    }

    public void setPostTracking(boolean postTracking) {
        this.postTracking = postTracking;
    }
}

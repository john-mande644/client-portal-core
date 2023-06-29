package com.owd.OWDShippingAPI.Models;

import java.util.List;

/**
 * Created by danny on 11/14/2019.
 */
public class Package {


    private String packagingType;
    private Double weight;
    private Double billedWeight;
    private String packageReference;
    private String dimension;
    private Double insuranceAmount;
    private boolean isSignatureRequested;


    private List<LineItemInfo> lineInfo;

    private String trackingNumber;
    private List<String> label;
    private String voidPackageId;
    public PackageCosts pricing;


    public boolean isSignatureRequested() {
        return isSignatureRequested;
    }

    public void setIsSignatureRequested(boolean isSignatureRequested) {
        this.isSignatureRequested = isSignatureRequested;
    }

    public Double getBilledWeight() {
        return billedWeight;
    }

    public void setBilledWeight(Double billedWeight) {
        this.billedWeight = billedWeight;
    }

    public Double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public List<LineItemInfo> getLineInfo() {
        return lineInfo;
    }

    public void setLineInfo(List<LineItemInfo> lineInfo) {
        this.lineInfo = lineInfo;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getVoidPackageId() {
        return voidPackageId;
    }

    public void setVoidPackageId(String voidPackageId) {
        this.voidPackageId = voidPackageId;
    }

    public PackageCosts getPricing() {
        return pricing;
    }

    public void setPricing(PackageCosts pricing) {
        this.pricing = pricing;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getPackageReference() {
        return packageReference;
    }

    public void setPackageReference(String packageReference) {
        this.packageReference = packageReference;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}

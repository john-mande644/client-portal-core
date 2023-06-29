package com.owd.OWDShippingAPI.Models;

/**
 * Created by danny on 11/14/2019.
 */
public class PackageCosts {

    private String total ;
    private String basePrice ;
    private String surcharges ;
    private String insuranceCost ;
    private DetailedPackageCosts detailedPricing;

    public DetailedPackageCosts getDetailedPricing() {
        return detailedPricing;
    }

    public void setDetailedPricing(DetailedPackageCosts detailedPricing) {
        this.detailedPricing = detailedPricing;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getSurcharges() {
        return surcharges;
    }

    public void setSurcharges(String surcharges) {
        this.surcharges = surcharges;
    }

    public String getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(String insuranceCost) {
        this.insuranceCost = insuranceCost;
    }
}

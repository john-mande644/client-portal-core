package com.owd.OWDShippingAPI.Models;

/**
 * Created by danny on 11/14/2019.
 */
public class LineItemInfo {

    public String description ;
    public String sku ;
    public String countryOfOrigin ;
    public Double singleUnitValue ;
    public Double singleUnitWeight ;
    public String quantityUnitMeasure ;
    public int quantity ;
    public String harmonizedCode ;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Double getSingleUnitValue() {
        return singleUnitValue;
    }

    public void setSingleUnitValue(Double singleUnitValue) {
        this.singleUnitValue = singleUnitValue;
    }

    public Double getSingleUnitWeight() {
        return singleUnitWeight;
    }

    public void setSingleUnitWeight(Double singleUnitWeight) {
        this.singleUnitWeight = singleUnitWeight;
    }

    public String getQuantityUnitMeasure() {
        return quantityUnitMeasure;
    }

    public void setQuantityUnitMeasure(String quantityUnitMeasure) {
        this.quantityUnitMeasure = quantityUnitMeasure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getHarmonizedCode() {
        return harmonizedCode;
    }

    public void setHarmonizedCode(String harmonizedCode) {
        this.harmonizedCode = harmonizedCode;
    }
}

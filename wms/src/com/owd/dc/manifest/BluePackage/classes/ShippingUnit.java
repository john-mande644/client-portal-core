
package com.owd.dc.manifest.BluePackage.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingUnit {

    @SerializedName("ShippingUnitType")
    @Expose
    private Integer shippingUnitType;
    @SerializedName("ShippingUnitQuantity")
    @Expose
    private String shippingUnitQuantity;
    @SerializedName("ShippingUnitQuantityUnit")
    @Expose
    private Integer shippingUnitQuantityUnit;
    @SerializedName("UnitQuantityPerShippingUnit")
    @Expose
    private Integer unitQuantityPerShippingUnit;
    @SerializedName("HandlingUnitsPerShippingUnit")
    @Expose
    private Integer handlingUnitsPerShippingUnit;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("CommercialInvoiceCommodityDescription")
    @Expose
    private String commercialInvoiceCommodityDescription;
    @SerializedName("Weight")
    @Expose
    private Integer weight;
    @SerializedName("WeightUnit")
    @Expose
    private String weightUnit;
    @SerializedName("NmfcClassCode")
    @Expose
    private String nmfcClassCode;
    @SerializedName("NmfcItemCode")
    @Expose
    private String nmfcItemCode;
    @SerializedName("CountryOfManufacture")
    @Expose
    private String countryOfManufacture;
    @SerializedName("HarmonizedCode")
    @Expose
    private String harmonizedCode;
    @SerializedName("Length")
    @Expose
    private Integer length;
    @SerializedName("Width")
    @Expose
    private Integer width;
    @SerializedName("Height")
    @Expose
    private Integer height;
    @SerializedName("DimUnit")
    @Expose
    private String dimUnit;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("DeclaredValue")
    @Expose
    private Integer declaredValue;
    @SerializedName("InsuranceAmount")
    @Expose
    private Integer insuranceAmount;
    @SerializedName("CommodityWeight")
    @Expose
    private Integer commodityWeight;
    @SerializedName("CommodityPieces")
    @Expose
    private String commodityPieces;

    public Integer getShippingUnitType() {
        return shippingUnitType;
    }

    public void setShippingUnitType(Integer shippingUnitType) {
        this.shippingUnitType = shippingUnitType;
    }

    public String getShippingUnitQuantity() {
        return shippingUnitQuantity;
    }

    public void setShippingUnitQuantity(String shippingUnitQuantity) {
        this.shippingUnitQuantity = shippingUnitQuantity;
    }

    public Integer getShippingUnitQuantityUnit() {
        return shippingUnitQuantityUnit;
    }

    public void setShippingUnitQuantityUnit(Integer shippingUnitQuantityUnit) {
        this.shippingUnitQuantityUnit = shippingUnitQuantityUnit;
    }

    public Integer getUnitQuantityPerShippingUnit() {
        return unitQuantityPerShippingUnit;
    }

    public void setUnitQuantityPerShippingUnit(Integer unitQuantityPerShippingUnit) {
        this.unitQuantityPerShippingUnit = unitQuantityPerShippingUnit;
    }

    public Integer getHandlingUnitsPerShippingUnit() {
        return handlingUnitsPerShippingUnit;
    }

    public void setHandlingUnitsPerShippingUnit(Integer handlingUnitsPerShippingUnit) {
        this.handlingUnitsPerShippingUnit = handlingUnitsPerShippingUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommercialInvoiceCommodityDescription() {
        return commercialInvoiceCommodityDescription;
    }

    public void setCommercialInvoiceCommodityDescription(String commercialInvoiceCommodityDescription) {
        this.commercialInvoiceCommodityDescription = commercialInvoiceCommodityDescription;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getNmfcClassCode() {
        return nmfcClassCode;
    }

    public void setNmfcClassCode(String nmfcClassCode) {
        this.nmfcClassCode = nmfcClassCode;
    }

    public String getNmfcItemCode() {
        return nmfcItemCode;
    }

    public void setNmfcItemCode(String nmfcItemCode) {
        this.nmfcItemCode = nmfcItemCode;
    }

    public String getCountryOfManufacture() {
        return countryOfManufacture;
    }

    public void setCountryOfManufacture(String countryOfManufacture) {
        this.countryOfManufacture = countryOfManufacture;
    }

    public String getHarmonizedCode() {
        return harmonizedCode;
    }

    public void setHarmonizedCode(String harmonizedCode) {
        this.harmonizedCode = harmonizedCode;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getDimUnit() {
        return dimUnit;
    }

    public void setDimUnit(String dimUnit) {
        this.dimUnit = dimUnit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(Integer declaredValue) {
        this.declaredValue = declaredValue;
    }

    public Integer getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Integer insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Integer getCommodityWeight() {
        return commodityWeight;
    }

    public void setCommodityWeight(Integer commodityWeight) {
        this.commodityWeight = commodityWeight;
    }

    public String getCommodityPieces() {
        return commodityPieces;
    }

    public void setCommodityPieces(String commodityPieces) {
        this.commodityPieces = commodityPieces;
    }

}

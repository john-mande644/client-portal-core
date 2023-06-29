
package com.owd.dc.manifest.BluePackage.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HandlingUnit {

    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("Weight")
    @Expose
    private Integer weight;
    @SerializedName("WeightUnit")
    @Expose
    private String weightUnit;
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
    @SerializedName("DeclaredValue")
    @Expose
    private Integer declaredValue;
    @SerializedName("InsuranceAmount")
    @Expose
    private Integer insuranceAmount;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

}


package com.owd.dc.manifest.BluePackage.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreightShipment {

    @SerializedName("DeliveryInstructions")
    @Expose
    private String deliveryInstructions;
    @SerializedName("PickupInstructions")
    @Expose
    private String pickupInstructions;
    @SerializedName("BillOfLadingNumber")
    @Expose
    private String billOfLadingNumber;
    @SerializedName("TotalHandlingUnitsCount")
    @Expose
    private Integer totalHandlingUnitsCount;
    @SerializedName("CollectTermsType")
    @Expose
    private Integer collectTermsType;
    @SerializedName("DeclaredValueUnits")
    @Expose
    private String declaredValueUnits;
    @SerializedName("FreightBrokerAddress")
    @Expose
    private FreightBrokerAddress freightBrokerAddress;
    @SerializedName("BillingContactAddress")
    @Expose
    private BillingContactAddress billingContactAddress;
    @SerializedName("ShippingUnits")
    @Expose
    private List<ShippingUnit> shippingUnits = null;
    @SerializedName("HandlingUnits")
    @Expose
    private List<HandlingUnit> handlingUnits = null;

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getPickupInstructions() {
        return pickupInstructions;
    }

    public void setPickupInstructions(String pickupInstructions) {
        this.pickupInstructions = pickupInstructions;
    }

    public String getBillOfLadingNumber() {
        return billOfLadingNumber;
    }

    public void setBillOfLadingNumber(String billOfLadingNumber) {
        this.billOfLadingNumber = billOfLadingNumber;
    }

    public Integer getTotalHandlingUnitsCount() {
        return totalHandlingUnitsCount;
    }

    public void setTotalHandlingUnitsCount(Integer totalHandlingUnitsCount) {
        this.totalHandlingUnitsCount = totalHandlingUnitsCount;
    }

    public Integer getCollectTermsType() {
        return collectTermsType;
    }

    public void setCollectTermsType(Integer collectTermsType) {
        this.collectTermsType = collectTermsType;
    }

    public String getDeclaredValueUnits() {
        return declaredValueUnits;
    }

    public void setDeclaredValueUnits(String declaredValueUnits) {
        this.declaredValueUnits = declaredValueUnits;
    }

    public FreightBrokerAddress getFreightBrokerAddress() {
        return freightBrokerAddress;
    }

    public void setFreightBrokerAddress(FreightBrokerAddress freightBrokerAddress) {
        this.freightBrokerAddress = freightBrokerAddress;
    }

    public BillingContactAddress getBillingContactAddress() {
        return billingContactAddress;
    }

    public void setBillingContactAddress(BillingContactAddress billingContactAddress) {
        this.billingContactAddress = billingContactAddress;
    }

    public List<ShippingUnit> getShippingUnits() {
        return shippingUnits;
    }

    public void setShippingUnits(List<ShippingUnit> shippingUnits) {
        this.shippingUnits = shippingUnits;
    }

    public List<HandlingUnit> getHandlingUnits() {
        return handlingUnits;
    }

    public void setHandlingUnits(List<HandlingUnit> handlingUnits) {
        this.handlingUnits = handlingUnits;
    }

}


package com.owd.jobs.jobobjects.easypost.tracking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Result {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("tracking_code")
    @Expose
    private String trackingCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_detail")
    @Expose
    private String statusDetail;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("signed_by")
    @Expose
    private String signedBy;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("est_delivery_date")
    @Expose
    private String estDeliveryDate;
    @SerializedName("shipment_id")
    @Expose
    private String shipmentId;
    @SerializedName("carrier")
    @Expose
    private String carrier;
    @SerializedName("tracking_details")
    @Expose
    private List<TrackingDetail> trackingDetails = null;
    @SerializedName("carrier_detail")
    @Expose
    private CarrierDetail carrierDetail;
    @SerializedName("finalized")
    @Expose
    private Boolean finalized;
    @SerializedName("is_return")
    @Expose
    private Boolean isReturn;
    @SerializedName("public_url")
    @Expose
    private String publicUrl;
    @SerializedName("fees")
    @Expose
    private List<Fee> fees = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getEstDeliveryDate() {
        return estDeliveryDate;
    }

    public void setEstDeliveryDate(String estDeliveryDate) {
        this.estDeliveryDate = estDeliveryDate;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public List<TrackingDetail> getTrackingDetails() {
        return trackingDetails;
    }

    public void setTrackingDetails(List<TrackingDetail> trackingDetails) {
        this.trackingDetails = trackingDetails;
    }

    public CarrierDetail getCarrierDetail() {
        return carrierDetail;
    }

    public void setCarrierDetail(CarrierDetail carrierDetail) {
        this.carrierDetail = carrierDetail;
    }

    public Boolean getFinalized() {
        return finalized;
    }

    public void setFinalized(Boolean finalized) {
        this.finalized = finalized;
    }

    public Boolean getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Boolean isReturn) {
        this.isReturn = isReturn;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("object", object).append("mode", mode).append("trackingCode", trackingCode).append("status", status).append("statusDetail", statusDetail).append("createdAt", createdAt).append("updatedAt", updatedAt).append("signedBy", signedBy).append("weight", weight).append("estDeliveryDate", estDeliveryDate).append("shipmentId", shipmentId).append("carrier", carrier).append("trackingDetails", trackingDetails).append("carrierDetail", carrierDetail).append("finalized", finalized).append("isReturn", isReturn).append("publicUrl", publicUrl).append("fees", fees).toString();
    }

}

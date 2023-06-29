
package com.owd.jobs.jobobjects.easypost.tracking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TrackingDetail {

    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_detail")
    @Expose
    private String statusDetail;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("carrier_code")
    @Expose
    private String carrierCode;
    @SerializedName("tracking_location")
    @Expose
    private TrackingLocation trackingLocation;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public TrackingLocation getTrackingLocation() {
        return trackingLocation;
    }

    public void setTrackingLocation(TrackingLocation trackingLocation) {
        this.trackingLocation = trackingLocation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("object", object).append("message", message).append("description", description).append("status", status).append("statusDetail", statusDetail).append("datetime", datetime).append("source", source).append("carrierCode", carrierCode).append("trackingLocation", trackingLocation).toString();
    }

}

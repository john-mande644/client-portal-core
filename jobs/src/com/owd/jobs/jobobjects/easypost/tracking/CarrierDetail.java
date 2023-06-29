
package com.owd.jobs.jobobjects.easypost.tracking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CarrierDetail {

    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("service")
    @Expose
    private Object service;
    @SerializedName("container_type")
    @Expose
    private Object containerType;
    @SerializedName("est_delivery_date_local")
    @Expose
    private Object estDeliveryDateLocal;
    @SerializedName("est_delivery_time_local")
    @Expose
    private Object estDeliveryTimeLocal;
    @SerializedName("origin_location")
    @Expose
    private String originLocation;
    @SerializedName("origin_tracking_location")
    @Expose
    private OriginTrackingLocation originTrackingLocation;
    @SerializedName("destination_location")
    @Expose
    private String destinationLocation;
    @SerializedName("destination_tracking_location")
    @Expose
    private DestinationTrackingLocation destinationTrackingLocation;
    @SerializedName("guaranteed_delivery_date")
    @Expose
    private Object guaranteedDeliveryDate;
    @SerializedName("alternate_identifier")
    @Expose
    private Object alternateIdentifier;
    @SerializedName("initial_delivery_attempt")
    @Expose
    private String initialDeliveryAttempt;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public Object getContainerType() {
        return containerType;
    }

    public void setContainerType(Object containerType) {
        this.containerType = containerType;
    }

    public Object getEstDeliveryDateLocal() {
        return estDeliveryDateLocal;
    }

    public void setEstDeliveryDateLocal(Object estDeliveryDateLocal) {
        this.estDeliveryDateLocal = estDeliveryDateLocal;
    }

    public Object getEstDeliveryTimeLocal() {
        return estDeliveryTimeLocal;
    }

    public void setEstDeliveryTimeLocal(Object estDeliveryTimeLocal) {
        this.estDeliveryTimeLocal = estDeliveryTimeLocal;
    }

    public String getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(String originLocation) {
        this.originLocation = originLocation;
    }

    public OriginTrackingLocation getOriginTrackingLocation() {
        return originTrackingLocation;
    }

    public void setOriginTrackingLocation(OriginTrackingLocation originTrackingLocation) {
        this.originTrackingLocation = originTrackingLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public DestinationTrackingLocation getDestinationTrackingLocation() {
        return destinationTrackingLocation;
    }

    public void setDestinationTrackingLocation(DestinationTrackingLocation destinationTrackingLocation) {
        this.destinationTrackingLocation = destinationTrackingLocation;
    }

    public Object getGuaranteedDeliveryDate() {
        return guaranteedDeliveryDate;
    }

    public void setGuaranteedDeliveryDate(Object guaranteedDeliveryDate) {
        this.guaranteedDeliveryDate = guaranteedDeliveryDate;
    }

    public Object getAlternateIdentifier() {
        return alternateIdentifier;
    }

    public void setAlternateIdentifier(Object alternateIdentifier) {
        this.alternateIdentifier = alternateIdentifier;
    }

    public String getInitialDeliveryAttempt() {
        return initialDeliveryAttempt;
    }

    public void setInitialDeliveryAttempt(String initialDeliveryAttempt) {
        this.initialDeliveryAttempt = initialDeliveryAttempt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("object", object).append("service", service).append("containerType", containerType).append("estDeliveryDateLocal", estDeliveryDateLocal).append("estDeliveryTimeLocal", estDeliveryTimeLocal).append("originLocation", originLocation).append("originTrackingLocation", originTrackingLocation).append("destinationLocation", destinationLocation).append("destinationTrackingLocation", destinationTrackingLocation).append("guaranteedDeliveryDate", guaranteedDeliveryDate).append("alternateIdentifier", alternateIdentifier).append("initialDeliveryAttempt", initialDeliveryAttempt).toString();
    }

}

package com.owd.OWDShippingAPI.Models;

/**
 * Created by danny on 11/14/2019.
 */
public class DeliveryOptions {

    private boolean saturdayDelivery;
    private boolean signatureRequired;
    private boolean callTag;

    public boolean isCallTag() {
        return callTag;
    }

    public void setCallTag(boolean callTag) {
        this.callTag = callTag;
    }

    public boolean isSaturdayDelivery() {
        return saturdayDelivery;
    }

    public void setSaturdayDelivery(boolean saturdayDelivery) {
        this.saturdayDelivery = saturdayDelivery;
    }

    public boolean isSignatureRequired() {
        return signatureRequired;
    }

    public void setSignatureRequired(boolean signatureRequired) {
        this.signatureRequired = signatureRequired;
    }
}

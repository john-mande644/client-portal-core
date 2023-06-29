package com.owd.OWDShippingAPI.Returns;

/**
 * Created by danny on 3/10/2020.
 */
public class ReturnResponse {

    private String tracking;
    private byte[] label;


    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public byte[] getLabel() {
        return label;
    }

    public void setLabel(byte[] label) {
        this.label = label;
    }
}

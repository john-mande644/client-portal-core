package com.owd.OWDShippingAPI.Models;

/**
 * Created by danny on 11/21/2019.
 */
public class shipServiceModel {

    private String carrierCode;

    private String methodCode;

    public shipServiceModel(String method, String carrier){
        this.carrierCode = carrier;
        this.methodCode = method;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }
}

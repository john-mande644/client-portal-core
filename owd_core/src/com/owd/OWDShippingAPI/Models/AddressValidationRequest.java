package com.owd.OWDShippingAPI.Models;

/**
 * Created by danny on 12/19/2019.
 */
public class AddressValidationRequest {

    private String inputContact;
    private String inputAddress1;
    private String inputAddress2;
    private String inputCity;
    private String inputState;
    private String inputPostalCode;
    private String inputCountry;
    private String serviceEngine;

    public String getInputContact() {
        return inputContact;
    }

    public void setInputContact(String inputContact) {
        this.inputContact = inputContact;
    }

    public String getInputAddress1() {
        return inputAddress1;
    }

    public void setInputAddress1(String inputAddress1) {
        this.inputAddress1 = inputAddress1;
    }

    public String getInputAddress2() {
        return inputAddress2;
    }

    public void setInputAddress2(String inputAddress2) {
        this.inputAddress2 = inputAddress2;
    }

    public String getInputCity() {
        return inputCity;
    }

    public void setInputCity(String inputCity) {
        this.inputCity = inputCity;
    }

    public String getInputState() {
        return inputState;
    }

    public void setInputState(String inputState) {
        this.inputState = inputState;
    }

    public String getInputPostalCode() {
        return inputPostalCode;
    }

    public void setInputPostalCode(String inputPostalCode) {
        this.inputPostalCode = inputPostalCode;
    }

    public String getInputCountry() {
        return inputCountry;
    }

    public void setInputCountry(String inputCountry) {
        this.inputCountry = inputCountry;
    }

    public String getServiceEngine() {
        return serviceEngine;
    }

    public void setServiceEngine(String serviceEngine) {
        this.serviceEngine = serviceEngine;
    }
}

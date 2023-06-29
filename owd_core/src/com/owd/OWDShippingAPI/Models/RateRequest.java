package com.owd.OWDShippingAPI.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by danny on 11/14/2019.
 */
public class RateRequest {

    private String shipTerms ;
    private String shipDate ;
    private String shipperReference ;
    private String clientReference ;
    private String shippingAccountName ;
    private String shipToContact ;
    private String shipToCompany ;
    private String shipToAddress1 ;
    private String shipToCity ;
    private String shipToState ;
    private String shipToPostalCode ;
    private String shipToCountry ;
    private String shipToPhone ;
    private boolean residentialAddress ;
    private String[] shipService ;
    @SerializedName("package")
    private Package aPackage;

    public String getShipTerms() {
        return shipTerms;
    }

    public void setShipTerms(String shipTerms) {
        this.shipTerms = shipTerms;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getShipperReference() {
        return shipperReference;
    }

    public void setShipperReference(String shipperReference) {
        this.shipperReference = shipperReference;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public String getShippingAccountName() {
        return shippingAccountName;
    }

    public void setShippingAccountName(String shippingAccountName) {
        this.shippingAccountName = shippingAccountName;
    }

    public String getShipToContact() {
        return shipToContact;
    }

    public void setShipToContact(String shipToContact) {
        this.shipToContact = shipToContact;
    }

    public String getShipToCompany() {
        return shipToCompany;
    }

    public void setShipToCompany(String shipToCompany) {
        this.shipToCompany = shipToCompany;
    }

    public String getShipToAddress1() {
        return shipToAddress1;
    }

    public void setShipToAddress1(String shipToAddress1) {
        this.shipToAddress1 = shipToAddress1;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    public String getShipToState() {
        return shipToState;
    }

    public void setShipToState(String shipToState) {
        this.shipToState = shipToState;
    }

    public String getShipToPostalCode() {
        return shipToPostalCode;
    }

    public void setShipToPostalCode(String shipToPostalCode) {
        this.shipToPostalCode = shipToPostalCode;
    }

    public String getShipToCountry() {
        return shipToCountry;
    }

    public void setShipToCountry(String shipToCountry) {
        this.shipToCountry = shipToCountry;
    }

    public String getShipToPhone() {
        return shipToPhone;
    }

    public void setShipToPhone(String shipToPhone) {
        this.shipToPhone = shipToPhone;
    }

    public boolean isResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(boolean residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String[] getShipService() {
        return shipService;
    }

    public void setShipService(String[] shipService) {
        this.shipService = shipService;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }
}

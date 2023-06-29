package com.owd.OWDShippingAPI.Models;

import java.util.List;

/**
 * Created by danny on 11/14/2019.
 */
public class ShipShipmentResponse {

    public String shipTerms;

    public String shipDate;
    public String shipperReference;
    public String clientReference;
    public String shippingAccountName;

    public String shipToContact;
    public String shipToCompany;

    public String shipToAddress1;
    public String shipToAddress2;
    public String shipToCity;
    public String shipToPostalCode;
    public String shipToState;
    public String shipToCountry;
    public String shipToPhone;
    public String returnContact;
    public String returnCompany;
    public String returnAddress1;
    public String returnAddress2;
    public String returnCity;
    public String returnState;
    public String returnPostalCode;
    public String returnCountry;

    public String returnPhone;
    public String shipService;
    public String shipMethod;
    public String shipError;
    public int electronicCommercialInvoice;
    public String electronicCommercialInvoiceStatus;
    public List<Package> packages;
    public boolean isSaturdayDelivery;


    private transient String groupName;
    private transient String facility;
    private transient String clientId;
    private transient String orderId;


    public boolean isSaturdayDelivery() {
        return isSaturdayDelivery;
    }

    public void setIsSaturdayDelivery(boolean isSaturdayDelivery) {
        this.isSaturdayDelivery = isSaturdayDelivery;
    }

    public String getElectronicCommercialInvoiceStatus() {
        return electronicCommercialInvoiceStatus;
    }

    public void setElectronicCommercialInvoiceStatus(String electronicCommercialInvoiceStatus) {
        this.electronicCommercialInvoiceStatus = electronicCommercialInvoiceStatus;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShipError() {
        return shipError;
    }

    public void setShipError(String shipError) {
        this.shipError = shipError;
    }

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

    public String getShipToAddress2() {
        return shipToAddress2;
    }

    public void setShipToAddress2(String shipToAddress2) {
        this.shipToAddress2 = shipToAddress2;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    public String getShipToPostalCode() {
        return shipToPostalCode;
    }

    public void setShipToPostalCode(String shipToPostalCode) {
        this.shipToPostalCode = shipToPostalCode;
    }

    public String getShipToState() {
        return shipToState;
    }

    public void setShipToState(String shipToState) {
        this.shipToState = shipToState;
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

    public String getReturnContact() {
        return returnContact;
    }

    public void setReturnContact(String returnContact) {
        this.returnContact = returnContact;
    }

    public String getReturnCompany() {
        return returnCompany;
    }

    public void setReturnCompany(String returnCompany) {
        this.returnCompany = returnCompany;
    }

    public String getReturnAddress1() {
        return returnAddress1;
    }

    public void setReturnAddress1(String returnAddress1) {
        this.returnAddress1 = returnAddress1;
    }

    public String getReturnAddress2() {
        return returnAddress2;
    }

    public void setReturnAddress2(String returnAddress2) {
        this.returnAddress2 = returnAddress2;
    }

    public String getReturnCity() {
        return returnCity;
    }

    public void setReturnCity(String returnCity) {
        this.returnCity = returnCity;
    }

    public String getReturnState() {
        return returnState;
    }

    public void setReturnState(String returnState) {
        this.returnState = returnState;
    }

    public String getReturnPostalCode() {
        return returnPostalCode;
    }

    public void setReturnPostalCode(String returnPostalCode) {
        this.returnPostalCode = returnPostalCode;
    }

    public String getReturnCountry() {
        return returnCountry;
    }

    public void setReturnCountry(String returnCountry) {
        this.returnCountry = returnCountry;
    }

    public String getReturnPhone() {
        return returnPhone;
    }

    public void setReturnPhone(String returnPhone) {
        this.returnPhone = returnPhone;
    }

    public String getShipService() {
        return shipService;
    }

    public void setShipService(String shipService) {
        this.shipService = shipService;
    }

    public int getElectronicCommercialInvoice() {
        return electronicCommercialInvoice;
    }

    public void setElectronicCommercialInvoice(int electronicCommercialInvoice) {
        this.electronicCommercialInvoice = electronicCommercialInvoice;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}

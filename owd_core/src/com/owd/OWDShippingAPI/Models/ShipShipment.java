package com.owd.OWDShippingAPI.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 11/14/2019.
 */
public class ShipShipment {

    //region values needed to send to API for Shipment
    private String shipTerms;
    private String shipDate;
    private String shipperReference;
    private String clientReference;
    private String shippingAccountName;
    private String shipToContact;
    private String shipToCompany;
    private String shipToAddress1;
    private String shipToAddress2;
    private String shipToCity;
    private String shipToPostalCode;
    private String shipToState;
    private String shipToCountry;
    private String shipToPhone;
    private String shipToEmail;
    private boolean residential;
    private String returnContact;
    private String returnCompany;
    private String returnAddress1;
    private String returnAddress2;
    private String returnCity;
    private String returnState;
    private String returnPostalCode;
    private String returnCountry;
    private String returnPhone;
    private String shipMethod;
    private String shipService;
    private int electronicCommercialInvoice;
    private List<Package> packages;
    private ThirdPartyBilling thirdPartyBilling;
    private DutiesAndTaxes dutiesAndTaxes;
    private DeliveryOptions deliveryOptions;
    private String groupName;
    private String facility;
    private String clientId;
    private String aesTransactionNumber;
    private String shipmentDescription;
    private String labelFormat;
    private String poNumber;
    //endregion
    
    //region Addition values tracked
    //transient variables will not be serialized to server




    private transient String orderId;
    private transient boolean dcHold;
    private transient boolean shippingHold;
    private transient boolean flatRate;

    private transient int shippedPacks;
    private transient String orderTrackFkey;
    private transient List<String> events = new ArrayList<>();

    
    
    //endregion


    public String getAesTransactionNumber() {
        return aesTransactionNumber;
    }

    public void setAesTransactionNumber(String aesTransactionNumber) {
        this.aesTransactionNumber = aesTransactionNumber;
    }

    public boolean isFlatRate() {
        return flatRate;
    }

    public void setFlatRate(boolean flatRate) {
        this.flatRate = flatRate;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public boolean isResidential() {
        return residential;
    }

    public void setResidential(boolean residential) {
        this.residential = residential;
    }

    public int getShippedPacks() {
        return shippedPacks;
    }

    public void setShippedPacks(int shippedPacks) {
        this.shippedPacks = shippedPacks;
    }

    public String getOrderTrackFkey() {
        return orderTrackFkey;
    }

    public void setOrderTrackFkey(String orderTrackFkey) {
        this.orderTrackFkey = orderTrackFkey;
    }



    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public boolean isDcHold() {
        return dcHold;
    }

    public void setDcHold(boolean dcHold) {
        this.dcHold = dcHold;
    }

    public boolean isShippingHold() {
        return shippingHold;
    }

    public void setShippingHold(boolean shippingHold) {
        this.shippingHold = shippingHold;
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

    public String getShipToEmail() {
        return shipToEmail;
    }

    public void setShipToEmail(String shipToEmail) {
        this.shipToEmail = shipToEmail;
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

    public ThirdPartyBilling getThirdPartyBilling() {
        return thirdPartyBilling;
    }

    public void setThirdPartyBilling(ThirdPartyBilling thirdPartyBilling) {
        this.thirdPartyBilling = thirdPartyBilling;
    }

    public DutiesAndTaxes getDutiesAndTaxes() {
        return dutiesAndTaxes;
    }

    public void setDutiesAndTaxes(DutiesAndTaxes dutiesAndTaxes) {
        this.dutiesAndTaxes = dutiesAndTaxes;
    }

    public DeliveryOptions getDeliveryOptions() {
        return deliveryOptions;
    }

    public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public String getShipmentDescription() {
        return shipmentDescription;
    }

    public void setShipmentDescription(String shipmentDescription) {
        this.shipmentDescription = shipmentDescription;
    }

    public String getLabelFormat() {
        return labelFormat;
    }

    public void setLabelFormat(String labelFormat) {
        this.labelFormat = labelFormat;
    }

    public String getPoNumber() { return poNumber; }

    public void setPoNumber(String poNumber) { this.poNumber = poNumber; }
}

package com.owd.jobs.jobobjects.spscommerce.beans;

/**
 * Created by danny on 12/12/2018.
 */
public class AddressInfo {

    private String locationCodeQualifier = "";
    private String addressLocationNumber = "";
    private String addressTypeCode = "";

    public String getAddressTypeCode() {
        return addressTypeCode;
    }

    public void setAddressTypeCode(String addressTypeCode) {
        this.addressTypeCode = addressTypeCode;
    }

    public String getLocationCodeQualifier() {
        return locationCodeQualifier;
    }

    public void setLocationCodeQualifier(String locationCodeQualifier) {
        this.locationCodeQualifier = locationCodeQualifier;
    }

    public String getAddressLocationNumber() {
        return addressLocationNumber;
    }

    public void setAddressLocationNumber(String addressLocationNumber) {
        this.addressLocationNumber = addressLocationNumber;
    }
}

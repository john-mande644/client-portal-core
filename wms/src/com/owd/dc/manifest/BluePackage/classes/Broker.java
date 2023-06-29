
package com.owd.dc.manifest.BluePackage.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Broker {

    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("PhoneAreaCode")
    @Expose
    private String phoneAreaCode;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("ResidentialFlag")
    @Expose
    private Boolean residentialFlag;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("Attn")
    @Expose
    private String attn;
    @SerializedName("AddressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("AddressLine2")
    @Expose
    private String addressLine2;
    @SerializedName("AddressLine3")
    @Expose
    private String addressLine3;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("StateCode")
    @Expose
    private String stateCode;
    @SerializedName("Zip")
    @Expose
    private String zip;
    @SerializedName("Zip4")
    @Expose
    private String zip4;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("CountryName")
    @Expose
    private String countryName;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("PhoneExt")
    @Expose
    private String phoneExt;
    @SerializedName("Fax")
    @Expose
    private String fax;
    @SerializedName("EMail")
    @Expose
    private String eMail;
    @SerializedName("Reference")
    @Expose
    private String reference;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean getResidentialFlag() {
        return residentialFlag;
    }

    public void setResidentialFlag(Boolean residentialFlag) {
        this.residentialFlag = residentialFlag;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAttn() {
        return attn;
    }

    public void setAttn(String attn) {
        this.attn = attn;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip4() {
        return zip4;
    }

    public void setZip4(String zip4) {
        this.zip4 = zip4;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

}

package com.owd.OWDShippingAPI.Models;

/**
 * Created by danny on 1/7/2020.
 */
public class DetailedPackageCosts {

    private String passportDuties;
    private String passportTaxes;


    public String getPassportDuties() {
        return passportDuties;
    }

    public void setPassportDuties(String passportDuties) {
        this.passportDuties = passportDuties;
    }

    public String getPassportTaxes() {
        return passportTaxes;
    }

    public void setPassportTaxes(String passportTaxes) {
        this.passportTaxes = passportTaxes;
    }
}

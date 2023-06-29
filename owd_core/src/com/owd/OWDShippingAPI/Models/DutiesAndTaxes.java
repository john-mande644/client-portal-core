package com.owd.OWDShippingAPI.Models;

/**
 * Created by danny on 11/14/2019.
 */
public class DutiesAndTaxes {

    private String terms;

    private ThirdPartyBilling thirdPartyBilling;


    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public ThirdPartyBilling getThirdPartyBilling() {
        return thirdPartyBilling;
    }

    public void setThirdPartyBilling(ThirdPartyBilling thirdPartyBilling) {
        this.thirdPartyBilling = thirdPartyBilling;
    }
}

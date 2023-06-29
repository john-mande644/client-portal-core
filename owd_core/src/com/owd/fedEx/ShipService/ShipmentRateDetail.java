/*
 * ShipmentRateDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Data for a shipment's total/summary rates, as calculated per a
 * specific rate type. The "total..." fields may differ from the sum
 * of corresponding package data for Multiweight or Express MPS.
 */
public class ShipmentRateDetail  implements java.io.Serializable {
    /** The "PAYOR..." rates are expressed in the currency identified in
 * the payor's rate table(s). The "RATED..." rates are expressed in the
 * currency of the origin country. Former "...COUNTER..." values have
 * become "...RETAIL..." values, except for PAYOR_COUNTER and RATED_COUNTER,
 * which have been removed. */
    private com.owd.fedEx.ShipService.ReturnedRateType rateType;
    private java.lang.String rateScale;
    private java.lang.String rateZone;
    private com.owd.fedEx.ShipService.PricingCodeType pricingCode;
    private com.owd.fedEx.ShipService.RatedWeightMethod ratedWeightMethod;
    private com.owd.fedEx.ShipService.MinimumChargeType minimumChargeType;
    private com.owd.fedEx.ShipService.CurrencyExchangeRate currencyExchangeRate;
    /** Indicates which special rating cases applied to this shipment. */
    private com.owd.fedEx.ShipService.SpecialRatingAppliedType[] specialRatingApplied;
    private org.apache.axis.types.NonNegativeInteger dimDivisor;
    /** Indicates the reason that a dim divisor value was chose. */
    private com.owd.fedEx.ShipService.RateDimensionalDivisorType dimDivisorType;
    private java.math.BigDecimal fuelSurchargePercent;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight totalBillingWeight;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight totalDimWeight;
    private com.owd.fedEx.ShipService.Money totalBaseCharge;
    private com.owd.fedEx.ShipService.Money totalFreightDiscounts;
    private com.owd.fedEx.ShipService.Money totalNetFreight;
    private com.owd.fedEx.ShipService.Money totalSurcharges;
    private com.owd.fedEx.ShipService.Money totalNetFedExCharge;
    private com.owd.fedEx.ShipService.Money totalTaxes;
    private com.owd.fedEx.ShipService.Money totalNetCharge;
    private com.owd.fedEx.ShipService.Money totalRebates;
    private com.owd.fedEx.ShipService.Money totalDutiesAndTaxes;
    private com.owd.fedEx.ShipService.Money totalAncillaryFeesAndTaxes;
    private com.owd.fedEx.ShipService.Money totalDutiesTaxesAndFees;
    private com.owd.fedEx.ShipService.Money totalNetChargeWithDutiesAndTaxes;
    /** Identifies the Rate Details per each leg in a Freight Shipment */
    private com.owd.fedEx.ShipService.ShipmentLegRateDetail[] shipmentLegRateDetails;
    /** Rate data specific to FedEx Freight or FedEx National Freight services. */
    private com.owd.fedEx.ShipService.FreightRateDetail freightRateDetail;
    /** All rate discounts that apply to this package. */
    private com.owd.fedEx.ShipService.RateDiscount[] freightDiscounts;
    /** All rebates that apply to this package. */
    private com.owd.fedEx.ShipService.Rebate[] rebates;
    /** All surcharges that apply to this package (either because of characteristics
 * of the package itself, or because it is carrying per-shipment surcharges
 * for the shipment of which it is a part). */
    private com.owd.fedEx.ShipService.Surcharge[] surcharges;
    /** All taxes applicable (or distributed to) this package. */
    private com.owd.fedEx.ShipService.Tax[] taxes;
    /** All commodity-based duties and taxes applicable to this shipment. */
    private com.owd.fedEx.ShipService.EdtCommodityTax[] dutiesAndTaxes;
    /** Identifies the shipment-level fees and taxes that are not based
 * on transportation charges or commodity-level estimated duties and
 * taxes. */
    private com.owd.fedEx.ShipService.AncillaryFeeAndTax[] ancillaryFeesAndTaxes;
    private com.owd.fedEx.ShipService.VariableHandlingCharges variableHandlingCharges;
    private com.owd.fedEx.ShipService.VariableHandlingCharges totalVariableHandlingCharges;

    public ShipmentRateDetail() {
    }


    /**
     * Gets the rateType value for this ShipmentRateDetail.
     * 
     * @return rateType The "PAYOR..." rates are expressed in the currency identified in
 * the payor's rate table(s). The "RATED..." rates are expressed in the
 * currency of the origin country. Former "...COUNTER..." values have
 * become "...RETAIL..." values, except for PAYOR_COUNTER and RATED_COUNTER,
 * which have been removed.
     */
    public com.owd.fedEx.ShipService.ReturnedRateType getRateType() {
        return rateType;
    }


    /**
     * Sets the rateType value for this ShipmentRateDetail.
     * 
     * @param rateType The "PAYOR..." rates are expressed in the currency identified in
 * the payor's rate table(s). The "RATED..." rates are expressed in the
 * currency of the origin country. Former "...COUNTER..." values have
 * become "...RETAIL..." values, except for PAYOR_COUNTER and RATED_COUNTER,
 * which have been removed.
     */
    public void setRateType(com.owd.fedEx.ShipService.ReturnedRateType rateType) {
        this.rateType = rateType;
    }


    /**
     * Gets the rateScale value for this ShipmentRateDetail.
     * 
     * @return rateScale
     */
    public java.lang.String getRateScale() {
        return rateScale;
    }


    /**
     * Sets the rateScale value for this ShipmentRateDetail.
     * 
     * @param rateScale
     */
    public void setRateScale(java.lang.String rateScale) {
        this.rateScale = rateScale;
    }


    /**
     * Gets the rateZone value for this ShipmentRateDetail.
     * 
     * @return rateZone
     */
    public java.lang.String getRateZone() {
        return rateZone;
    }


    /**
     * Sets the rateZone value for this ShipmentRateDetail.
     * 
     * @param rateZone
     */
    public void setRateZone(java.lang.String rateZone) {
        this.rateZone = rateZone;
    }


    /**
     * Gets the pricingCode value for this ShipmentRateDetail.
     * 
     * @return pricingCode
     */
    public com.owd.fedEx.ShipService.PricingCodeType getPricingCode() {
        return pricingCode;
    }


    /**
     * Sets the pricingCode value for this ShipmentRateDetail.
     * 
     * @param pricingCode
     */
    public void setPricingCode(com.owd.fedEx.ShipService.PricingCodeType pricingCode) {
        this.pricingCode = pricingCode;
    }


    /**
     * Gets the ratedWeightMethod value for this ShipmentRateDetail.
     * 
     * @return ratedWeightMethod
     */
    public com.owd.fedEx.ShipService.RatedWeightMethod getRatedWeightMethod() {
        return ratedWeightMethod;
    }


    /**
     * Sets the ratedWeightMethod value for this ShipmentRateDetail.
     * 
     * @param ratedWeightMethod
     */
    public void setRatedWeightMethod(com.owd.fedEx.ShipService.RatedWeightMethod ratedWeightMethod) {
        this.ratedWeightMethod = ratedWeightMethod;
    }


    /**
     * Gets the minimumChargeType value for this ShipmentRateDetail.
     * 
     * @return minimumChargeType
     */
    public com.owd.fedEx.ShipService.MinimumChargeType getMinimumChargeType() {
        return minimumChargeType;
    }


    /**
     * Sets the minimumChargeType value for this ShipmentRateDetail.
     * 
     * @param minimumChargeType
     */
    public void setMinimumChargeType(com.owd.fedEx.ShipService.MinimumChargeType minimumChargeType) {
        this.minimumChargeType = minimumChargeType;
    }


    /**
     * Gets the currencyExchangeRate value for this ShipmentRateDetail.
     * 
     * @return currencyExchangeRate
     */
    public com.owd.fedEx.ShipService.CurrencyExchangeRate getCurrencyExchangeRate() {
        return currencyExchangeRate;
    }


    /**
     * Sets the currencyExchangeRate value for this ShipmentRateDetail.
     * 
     * @param currencyExchangeRate
     */
    public void setCurrencyExchangeRate(com.owd.fedEx.ShipService.CurrencyExchangeRate currencyExchangeRate) {
        this.currencyExchangeRate = currencyExchangeRate;
    }


    /**
     * Gets the specialRatingApplied value for this ShipmentRateDetail.
     * 
     * @return specialRatingApplied Indicates which special rating cases applied to this shipment.
     */
    public com.owd.fedEx.ShipService.SpecialRatingAppliedType[] getSpecialRatingApplied() {
        return specialRatingApplied;
    }


    /**
     * Sets the specialRatingApplied value for this ShipmentRateDetail.
     * 
     * @param specialRatingApplied Indicates which special rating cases applied to this shipment.
     */
    public void setSpecialRatingApplied(com.owd.fedEx.ShipService.SpecialRatingAppliedType[] specialRatingApplied) {
        this.specialRatingApplied = specialRatingApplied;
    }

    public com.owd.fedEx.ShipService.SpecialRatingAppliedType getSpecialRatingApplied(int i) {
        return specialRatingApplied[i];
    }

    public void setSpecialRatingApplied(int i, com.owd.fedEx.ShipService.SpecialRatingAppliedType value) {
        this.specialRatingApplied[i] = value;
    }


    /**
     * Gets the dimDivisor value for this ShipmentRateDetail.
     * 
     * @return dimDivisor
     */
    public org.apache.axis.types.NonNegativeInteger getDimDivisor() {
        return dimDivisor;
    }


    /**
     * Sets the dimDivisor value for this ShipmentRateDetail.
     * 
     * @param dimDivisor
     */
    public void setDimDivisor(org.apache.axis.types.NonNegativeInteger dimDivisor) {
        this.dimDivisor = dimDivisor;
    }


    /**
     * Gets the dimDivisorType value for this ShipmentRateDetail.
     * 
     * @return dimDivisorType Indicates the reason that a dim divisor value was chose.
     */
    public com.owd.fedEx.ShipService.RateDimensionalDivisorType getDimDivisorType() {
        return dimDivisorType;
    }


    /**
     * Sets the dimDivisorType value for this ShipmentRateDetail.
     * 
     * @param dimDivisorType Indicates the reason that a dim divisor value was chose.
     */
    public void setDimDivisorType(com.owd.fedEx.ShipService.RateDimensionalDivisorType dimDivisorType) {
        this.dimDivisorType = dimDivisorType;
    }


    /**
     * Gets the fuelSurchargePercent value for this ShipmentRateDetail.
     * 
     * @return fuelSurchargePercent
     */
    public java.math.BigDecimal getFuelSurchargePercent() {
        return fuelSurchargePercent;
    }


    /**
     * Sets the fuelSurchargePercent value for this ShipmentRateDetail.
     * 
     * @param fuelSurchargePercent
     */
    public void setFuelSurchargePercent(java.math.BigDecimal fuelSurchargePercent) {
        this.fuelSurchargePercent = fuelSurchargePercent;
    }


    /**
     * Gets the totalBillingWeight value for this ShipmentRateDetail.
     * 
     * @return totalBillingWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getTotalBillingWeight() {
        return totalBillingWeight;
    }


    /**
     * Sets the totalBillingWeight value for this ShipmentRateDetail.
     * 
     * @param totalBillingWeight The descriptive data for the heaviness of an object.
     */
    public void setTotalBillingWeight(com.owd.fedEx.ShipService.Weight totalBillingWeight) {
        this.totalBillingWeight = totalBillingWeight;
    }


    /**
     * Gets the totalDimWeight value for this ShipmentRateDetail.
     * 
     * @return totalDimWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getTotalDimWeight() {
        return totalDimWeight;
    }


    /**
     * Sets the totalDimWeight value for this ShipmentRateDetail.
     * 
     * @param totalDimWeight The descriptive data for the heaviness of an object.
     */
    public void setTotalDimWeight(com.owd.fedEx.ShipService.Weight totalDimWeight) {
        this.totalDimWeight = totalDimWeight;
    }


    /**
     * Gets the totalBaseCharge value for this ShipmentRateDetail.
     * 
     * @return totalBaseCharge
     */
    public com.owd.fedEx.ShipService.Money getTotalBaseCharge() {
        return totalBaseCharge;
    }


    /**
     * Sets the totalBaseCharge value for this ShipmentRateDetail.
     * 
     * @param totalBaseCharge
     */
    public void setTotalBaseCharge(com.owd.fedEx.ShipService.Money totalBaseCharge) {
        this.totalBaseCharge = totalBaseCharge;
    }


    /**
     * Gets the totalFreightDiscounts value for this ShipmentRateDetail.
     * 
     * @return totalFreightDiscounts
     */
    public com.owd.fedEx.ShipService.Money getTotalFreightDiscounts() {
        return totalFreightDiscounts;
    }


    /**
     * Sets the totalFreightDiscounts value for this ShipmentRateDetail.
     * 
     * @param totalFreightDiscounts
     */
    public void setTotalFreightDiscounts(com.owd.fedEx.ShipService.Money totalFreightDiscounts) {
        this.totalFreightDiscounts = totalFreightDiscounts;
    }


    /**
     * Gets the totalNetFreight value for this ShipmentRateDetail.
     * 
     * @return totalNetFreight
     */
    public com.owd.fedEx.ShipService.Money getTotalNetFreight() {
        return totalNetFreight;
    }


    /**
     * Sets the totalNetFreight value for this ShipmentRateDetail.
     * 
     * @param totalNetFreight
     */
    public void setTotalNetFreight(com.owd.fedEx.ShipService.Money totalNetFreight) {
        this.totalNetFreight = totalNetFreight;
    }


    /**
     * Gets the totalSurcharges value for this ShipmentRateDetail.
     * 
     * @return totalSurcharges
     */
    public com.owd.fedEx.ShipService.Money getTotalSurcharges() {
        return totalSurcharges;
    }


    /**
     * Sets the totalSurcharges value for this ShipmentRateDetail.
     * 
     * @param totalSurcharges
     */
    public void setTotalSurcharges(com.owd.fedEx.ShipService.Money totalSurcharges) {
        this.totalSurcharges = totalSurcharges;
    }


    /**
     * Gets the totalNetFedExCharge value for this ShipmentRateDetail.
     * 
     * @return totalNetFedExCharge
     */
    public com.owd.fedEx.ShipService.Money getTotalNetFedExCharge() {
        return totalNetFedExCharge;
    }


    /**
     * Sets the totalNetFedExCharge value for this ShipmentRateDetail.
     * 
     * @param totalNetFedExCharge
     */
    public void setTotalNetFedExCharge(com.owd.fedEx.ShipService.Money totalNetFedExCharge) {
        this.totalNetFedExCharge = totalNetFedExCharge;
    }


    /**
     * Gets the totalTaxes value for this ShipmentRateDetail.
     * 
     * @return totalTaxes
     */
    public com.owd.fedEx.ShipService.Money getTotalTaxes() {
        return totalTaxes;
    }


    /**
     * Sets the totalTaxes value for this ShipmentRateDetail.
     * 
     * @param totalTaxes
     */
    public void setTotalTaxes(com.owd.fedEx.ShipService.Money totalTaxes) {
        this.totalTaxes = totalTaxes;
    }


    /**
     * Gets the totalNetCharge value for this ShipmentRateDetail.
     * 
     * @return totalNetCharge
     */
    public com.owd.fedEx.ShipService.Money getTotalNetCharge() {
        return totalNetCharge;
    }


    /**
     * Sets the totalNetCharge value for this ShipmentRateDetail.
     * 
     * @param totalNetCharge
     */
    public void setTotalNetCharge(com.owd.fedEx.ShipService.Money totalNetCharge) {
        this.totalNetCharge = totalNetCharge;
    }


    /**
     * Gets the totalRebates value for this ShipmentRateDetail.
     * 
     * @return totalRebates
     */
    public com.owd.fedEx.ShipService.Money getTotalRebates() {
        return totalRebates;
    }


    /**
     * Sets the totalRebates value for this ShipmentRateDetail.
     * 
     * @param totalRebates
     */
    public void setTotalRebates(com.owd.fedEx.ShipService.Money totalRebates) {
        this.totalRebates = totalRebates;
    }


    /**
     * Gets the totalDutiesAndTaxes value for this ShipmentRateDetail.
     * 
     * @return totalDutiesAndTaxes
     */
    public com.owd.fedEx.ShipService.Money getTotalDutiesAndTaxes() {
        return totalDutiesAndTaxes;
    }


    /**
     * Sets the totalDutiesAndTaxes value for this ShipmentRateDetail.
     * 
     * @param totalDutiesAndTaxes
     */
    public void setTotalDutiesAndTaxes(com.owd.fedEx.ShipService.Money totalDutiesAndTaxes) {
        this.totalDutiesAndTaxes = totalDutiesAndTaxes;
    }


    /**
     * Gets the totalAncillaryFeesAndTaxes value for this ShipmentRateDetail.
     * 
     * @return totalAncillaryFeesAndTaxes
     */
    public com.owd.fedEx.ShipService.Money getTotalAncillaryFeesAndTaxes() {
        return totalAncillaryFeesAndTaxes;
    }


    /**
     * Sets the totalAncillaryFeesAndTaxes value for this ShipmentRateDetail.
     * 
     * @param totalAncillaryFeesAndTaxes
     */
    public void setTotalAncillaryFeesAndTaxes(com.owd.fedEx.ShipService.Money totalAncillaryFeesAndTaxes) {
        this.totalAncillaryFeesAndTaxes = totalAncillaryFeesAndTaxes;
    }


    /**
     * Gets the totalDutiesTaxesAndFees value for this ShipmentRateDetail.
     * 
     * @return totalDutiesTaxesAndFees
     */
    public com.owd.fedEx.ShipService.Money getTotalDutiesTaxesAndFees() {
        return totalDutiesTaxesAndFees;
    }


    /**
     * Sets the totalDutiesTaxesAndFees value for this ShipmentRateDetail.
     * 
     * @param totalDutiesTaxesAndFees
     */
    public void setTotalDutiesTaxesAndFees(com.owd.fedEx.ShipService.Money totalDutiesTaxesAndFees) {
        this.totalDutiesTaxesAndFees = totalDutiesTaxesAndFees;
    }


    /**
     * Gets the totalNetChargeWithDutiesAndTaxes value for this ShipmentRateDetail.
     * 
     * @return totalNetChargeWithDutiesAndTaxes
     */
    public com.owd.fedEx.ShipService.Money getTotalNetChargeWithDutiesAndTaxes() {
        return totalNetChargeWithDutiesAndTaxes;
    }


    /**
     * Sets the totalNetChargeWithDutiesAndTaxes value for this ShipmentRateDetail.
     * 
     * @param totalNetChargeWithDutiesAndTaxes
     */
    public void setTotalNetChargeWithDutiesAndTaxes(com.owd.fedEx.ShipService.Money totalNetChargeWithDutiesAndTaxes) {
        this.totalNetChargeWithDutiesAndTaxes = totalNetChargeWithDutiesAndTaxes;
    }


    /**
     * Gets the shipmentLegRateDetails value for this ShipmentRateDetail.
     * 
     * @return shipmentLegRateDetails Identifies the Rate Details per each leg in a Freight Shipment
     */
    public com.owd.fedEx.ShipService.ShipmentLegRateDetail[] getShipmentLegRateDetails() {
        return shipmentLegRateDetails;
    }


    /**
     * Sets the shipmentLegRateDetails value for this ShipmentRateDetail.
     * 
     * @param shipmentLegRateDetails Identifies the Rate Details per each leg in a Freight Shipment
     */
    public void setShipmentLegRateDetails(com.owd.fedEx.ShipService.ShipmentLegRateDetail[] shipmentLegRateDetails) {
        this.shipmentLegRateDetails = shipmentLegRateDetails;
    }

    public com.owd.fedEx.ShipService.ShipmentLegRateDetail getShipmentLegRateDetails(int i) {
        return shipmentLegRateDetails[i];
    }

    public void setShipmentLegRateDetails(int i, com.owd.fedEx.ShipService.ShipmentLegRateDetail value) {
        this.shipmentLegRateDetails[i] = value;
    }


    /**
     * Gets the freightRateDetail value for this ShipmentRateDetail.
     * 
     * @return freightRateDetail Rate data specific to FedEx Freight or FedEx National Freight services.
     */
    public com.owd.fedEx.ShipService.FreightRateDetail getFreightRateDetail() {
        return freightRateDetail;
    }


    /**
     * Sets the freightRateDetail value for this ShipmentRateDetail.
     * 
     * @param freightRateDetail Rate data specific to FedEx Freight or FedEx National Freight services.
     */
    public void setFreightRateDetail(com.owd.fedEx.ShipService.FreightRateDetail freightRateDetail) {
        this.freightRateDetail = freightRateDetail;
    }


    /**
     * Gets the freightDiscounts value for this ShipmentRateDetail.
     * 
     * @return freightDiscounts All rate discounts that apply to this package.
     */
    public com.owd.fedEx.ShipService.RateDiscount[] getFreightDiscounts() {
        return freightDiscounts;
    }


    /**
     * Sets the freightDiscounts value for this ShipmentRateDetail.
     * 
     * @param freightDiscounts All rate discounts that apply to this package.
     */
    public void setFreightDiscounts(com.owd.fedEx.ShipService.RateDiscount[] freightDiscounts) {
        this.freightDiscounts = freightDiscounts;
    }

    public com.owd.fedEx.ShipService.RateDiscount getFreightDiscounts(int i) {
        return freightDiscounts[i];
    }

    public void setFreightDiscounts(int i, com.owd.fedEx.ShipService.RateDiscount value) {
        this.freightDiscounts[i] = value;
    }


    /**
     * Gets the rebates value for this ShipmentRateDetail.
     * 
     * @return rebates All rebates that apply to this package.
     */
    public com.owd.fedEx.ShipService.Rebate[] getRebates() {
        return rebates;
    }


    /**
     * Sets the rebates value for this ShipmentRateDetail.
     * 
     * @param rebates All rebates that apply to this package.
     */
    public void setRebates(com.owd.fedEx.ShipService.Rebate[] rebates) {
        this.rebates = rebates;
    }

    public com.owd.fedEx.ShipService.Rebate getRebates(int i) {
        return rebates[i];
    }

    public void setRebates(int i, com.owd.fedEx.ShipService.Rebate value) {
        this.rebates[i] = value;
    }


    /**
     * Gets the surcharges value for this ShipmentRateDetail.
     * 
     * @return surcharges All surcharges that apply to this package (either because of characteristics
 * of the package itself, or because it is carrying per-shipment surcharges
 * for the shipment of which it is a part).
     */
    public com.owd.fedEx.ShipService.Surcharge[] getSurcharges() {
        return surcharges;
    }


    /**
     * Sets the surcharges value for this ShipmentRateDetail.
     * 
     * @param surcharges All surcharges that apply to this package (either because of characteristics
 * of the package itself, or because it is carrying per-shipment surcharges
 * for the shipment of which it is a part).
     */
    public void setSurcharges(com.owd.fedEx.ShipService.Surcharge[] surcharges) {
        this.surcharges = surcharges;
    }

    public com.owd.fedEx.ShipService.Surcharge getSurcharges(int i) {
        return surcharges[i];
    }

    public void setSurcharges(int i, com.owd.fedEx.ShipService.Surcharge value) {
        this.surcharges[i] = value;
    }


    /**
     * Gets the taxes value for this ShipmentRateDetail.
     * 
     * @return taxes All taxes applicable (or distributed to) this package.
     */
    public com.owd.fedEx.ShipService.Tax[] getTaxes() {
        return taxes;
    }


    /**
     * Sets the taxes value for this ShipmentRateDetail.
     * 
     * @param taxes All taxes applicable (or distributed to) this package.
     */
    public void setTaxes(com.owd.fedEx.ShipService.Tax[] taxes) {
        this.taxes = taxes;
    }

    public com.owd.fedEx.ShipService.Tax getTaxes(int i) {
        return taxes[i];
    }

    public void setTaxes(int i, com.owd.fedEx.ShipService.Tax value) {
        this.taxes[i] = value;
    }


    /**
     * Gets the dutiesAndTaxes value for this ShipmentRateDetail.
     * 
     * @return dutiesAndTaxes All commodity-based duties and taxes applicable to this shipment.
     */
    public com.owd.fedEx.ShipService.EdtCommodityTax[] getDutiesAndTaxes() {
        return dutiesAndTaxes;
    }


    /**
     * Sets the dutiesAndTaxes value for this ShipmentRateDetail.
     * 
     * @param dutiesAndTaxes All commodity-based duties and taxes applicable to this shipment.
     */
    public void setDutiesAndTaxes(com.owd.fedEx.ShipService.EdtCommodityTax[] dutiesAndTaxes) {
        this.dutiesAndTaxes = dutiesAndTaxes;
    }

    public com.owd.fedEx.ShipService.EdtCommodityTax getDutiesAndTaxes(int i) {
        return dutiesAndTaxes[i];
    }

    public void setDutiesAndTaxes(int i, com.owd.fedEx.ShipService.EdtCommodityTax value) {
        this.dutiesAndTaxes[i] = value;
    }


    /**
     * Gets the ancillaryFeesAndTaxes value for this ShipmentRateDetail.
     * 
     * @return ancillaryFeesAndTaxes Identifies the shipment-level fees and taxes that are not based
 * on transportation charges or commodity-level estimated duties and
 * taxes.
     */
    public com.owd.fedEx.ShipService.AncillaryFeeAndTax[] getAncillaryFeesAndTaxes() {
        return ancillaryFeesAndTaxes;
    }


    /**
     * Sets the ancillaryFeesAndTaxes value for this ShipmentRateDetail.
     * 
     * @param ancillaryFeesAndTaxes Identifies the shipment-level fees and taxes that are not based
 * on transportation charges or commodity-level estimated duties and
 * taxes.
     */
    public void setAncillaryFeesAndTaxes(com.owd.fedEx.ShipService.AncillaryFeeAndTax[] ancillaryFeesAndTaxes) {
        this.ancillaryFeesAndTaxes = ancillaryFeesAndTaxes;
    }

    public com.owd.fedEx.ShipService.AncillaryFeeAndTax getAncillaryFeesAndTaxes(int i) {
        return ancillaryFeesAndTaxes[i];
    }

    public void setAncillaryFeesAndTaxes(int i, com.owd.fedEx.ShipService.AncillaryFeeAndTax value) {
        this.ancillaryFeesAndTaxes[i] = value;
    }


    /**
     * Gets the variableHandlingCharges value for this ShipmentRateDetail.
     * 
     * @return variableHandlingCharges
     */
    public com.owd.fedEx.ShipService.VariableHandlingCharges getVariableHandlingCharges() {
        return variableHandlingCharges;
    }


    /**
     * Sets the variableHandlingCharges value for this ShipmentRateDetail.
     * 
     * @param variableHandlingCharges
     */
    public void setVariableHandlingCharges(com.owd.fedEx.ShipService.VariableHandlingCharges variableHandlingCharges) {
        this.variableHandlingCharges = variableHandlingCharges;
    }


    /**
     * Gets the totalVariableHandlingCharges value for this ShipmentRateDetail.
     * 
     * @return totalVariableHandlingCharges
     */
    public com.owd.fedEx.ShipService.VariableHandlingCharges getTotalVariableHandlingCharges() {
        return totalVariableHandlingCharges;
    }


    /**
     * Sets the totalVariableHandlingCharges value for this ShipmentRateDetail.
     * 
     * @param totalVariableHandlingCharges
     */
    public void setTotalVariableHandlingCharges(com.owd.fedEx.ShipService.VariableHandlingCharges totalVariableHandlingCharges) {
        this.totalVariableHandlingCharges = totalVariableHandlingCharges;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentRateDetail)) return false;
        ShipmentRateDetail other = (ShipmentRateDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rateType==null && other.getRateType()==null) || 
             (this.rateType!=null &&
              this.rateType.equals(other.getRateType()))) &&
            ((this.rateScale==null && other.getRateScale()==null) || 
             (this.rateScale!=null &&
              this.rateScale.equals(other.getRateScale()))) &&
            ((this.rateZone==null && other.getRateZone()==null) || 
             (this.rateZone!=null &&
              this.rateZone.equals(other.getRateZone()))) &&
            ((this.pricingCode==null && other.getPricingCode()==null) || 
             (this.pricingCode!=null &&
              this.pricingCode.equals(other.getPricingCode()))) &&
            ((this.ratedWeightMethod==null && other.getRatedWeightMethod()==null) || 
             (this.ratedWeightMethod!=null &&
              this.ratedWeightMethod.equals(other.getRatedWeightMethod()))) &&
            ((this.minimumChargeType==null && other.getMinimumChargeType()==null) || 
             (this.minimumChargeType!=null &&
              this.minimumChargeType.equals(other.getMinimumChargeType()))) &&
            ((this.currencyExchangeRate==null && other.getCurrencyExchangeRate()==null) || 
             (this.currencyExchangeRate!=null &&
              this.currencyExchangeRate.equals(other.getCurrencyExchangeRate()))) &&
            ((this.specialRatingApplied==null && other.getSpecialRatingApplied()==null) || 
             (this.specialRatingApplied!=null &&
              java.util.Arrays.equals(this.specialRatingApplied, other.getSpecialRatingApplied()))) &&
            ((this.dimDivisor==null && other.getDimDivisor()==null) || 
             (this.dimDivisor!=null &&
              this.dimDivisor.equals(other.getDimDivisor()))) &&
            ((this.dimDivisorType==null && other.getDimDivisorType()==null) || 
             (this.dimDivisorType!=null &&
              this.dimDivisorType.equals(other.getDimDivisorType()))) &&
            ((this.fuelSurchargePercent==null && other.getFuelSurchargePercent()==null) || 
             (this.fuelSurchargePercent!=null &&
              this.fuelSurchargePercent.equals(other.getFuelSurchargePercent()))) &&
            ((this.totalBillingWeight==null && other.getTotalBillingWeight()==null) || 
             (this.totalBillingWeight!=null &&
              this.totalBillingWeight.equals(other.getTotalBillingWeight()))) &&
            ((this.totalDimWeight==null && other.getTotalDimWeight()==null) || 
             (this.totalDimWeight!=null &&
              this.totalDimWeight.equals(other.getTotalDimWeight()))) &&
            ((this.totalBaseCharge==null && other.getTotalBaseCharge()==null) || 
             (this.totalBaseCharge!=null &&
              this.totalBaseCharge.equals(other.getTotalBaseCharge()))) &&
            ((this.totalFreightDiscounts==null && other.getTotalFreightDiscounts()==null) || 
             (this.totalFreightDiscounts!=null &&
              this.totalFreightDiscounts.equals(other.getTotalFreightDiscounts()))) &&
            ((this.totalNetFreight==null && other.getTotalNetFreight()==null) || 
             (this.totalNetFreight!=null &&
              this.totalNetFreight.equals(other.getTotalNetFreight()))) &&
            ((this.totalSurcharges==null && other.getTotalSurcharges()==null) || 
             (this.totalSurcharges!=null &&
              this.totalSurcharges.equals(other.getTotalSurcharges()))) &&
            ((this.totalNetFedExCharge==null && other.getTotalNetFedExCharge()==null) || 
             (this.totalNetFedExCharge!=null &&
              this.totalNetFedExCharge.equals(other.getTotalNetFedExCharge()))) &&
            ((this.totalTaxes==null && other.getTotalTaxes()==null) || 
             (this.totalTaxes!=null &&
              this.totalTaxes.equals(other.getTotalTaxes()))) &&
            ((this.totalNetCharge==null && other.getTotalNetCharge()==null) || 
             (this.totalNetCharge!=null &&
              this.totalNetCharge.equals(other.getTotalNetCharge()))) &&
            ((this.totalRebates==null && other.getTotalRebates()==null) || 
             (this.totalRebates!=null &&
              this.totalRebates.equals(other.getTotalRebates()))) &&
            ((this.totalDutiesAndTaxes==null && other.getTotalDutiesAndTaxes()==null) || 
             (this.totalDutiesAndTaxes!=null &&
              this.totalDutiesAndTaxes.equals(other.getTotalDutiesAndTaxes()))) &&
            ((this.totalAncillaryFeesAndTaxes==null && other.getTotalAncillaryFeesAndTaxes()==null) || 
             (this.totalAncillaryFeesAndTaxes!=null &&
              this.totalAncillaryFeesAndTaxes.equals(other.getTotalAncillaryFeesAndTaxes()))) &&
            ((this.totalDutiesTaxesAndFees==null && other.getTotalDutiesTaxesAndFees()==null) || 
             (this.totalDutiesTaxesAndFees!=null &&
              this.totalDutiesTaxesAndFees.equals(other.getTotalDutiesTaxesAndFees()))) &&
            ((this.totalNetChargeWithDutiesAndTaxes==null && other.getTotalNetChargeWithDutiesAndTaxes()==null) || 
             (this.totalNetChargeWithDutiesAndTaxes!=null &&
              this.totalNetChargeWithDutiesAndTaxes.equals(other.getTotalNetChargeWithDutiesAndTaxes()))) &&
            ((this.shipmentLegRateDetails==null && other.getShipmentLegRateDetails()==null) || 
             (this.shipmentLegRateDetails!=null &&
              java.util.Arrays.equals(this.shipmentLegRateDetails, other.getShipmentLegRateDetails()))) &&
            ((this.freightRateDetail==null && other.getFreightRateDetail()==null) || 
             (this.freightRateDetail!=null &&
              this.freightRateDetail.equals(other.getFreightRateDetail()))) &&
            ((this.freightDiscounts==null && other.getFreightDiscounts()==null) || 
             (this.freightDiscounts!=null &&
              java.util.Arrays.equals(this.freightDiscounts, other.getFreightDiscounts()))) &&
            ((this.rebates==null && other.getRebates()==null) || 
             (this.rebates!=null &&
              java.util.Arrays.equals(this.rebates, other.getRebates()))) &&
            ((this.surcharges==null && other.getSurcharges()==null) || 
             (this.surcharges!=null &&
              java.util.Arrays.equals(this.surcharges, other.getSurcharges()))) &&
            ((this.taxes==null && other.getTaxes()==null) || 
             (this.taxes!=null &&
              java.util.Arrays.equals(this.taxes, other.getTaxes()))) &&
            ((this.dutiesAndTaxes==null && other.getDutiesAndTaxes()==null) || 
             (this.dutiesAndTaxes!=null &&
              java.util.Arrays.equals(this.dutiesAndTaxes, other.getDutiesAndTaxes()))) &&
            ((this.ancillaryFeesAndTaxes==null && other.getAncillaryFeesAndTaxes()==null) || 
             (this.ancillaryFeesAndTaxes!=null &&
              java.util.Arrays.equals(this.ancillaryFeesAndTaxes, other.getAncillaryFeesAndTaxes()))) &&
            ((this.variableHandlingCharges==null && other.getVariableHandlingCharges()==null) || 
             (this.variableHandlingCharges!=null &&
              this.variableHandlingCharges.equals(other.getVariableHandlingCharges()))) &&
            ((this.totalVariableHandlingCharges==null && other.getTotalVariableHandlingCharges()==null) || 
             (this.totalVariableHandlingCharges!=null &&
              this.totalVariableHandlingCharges.equals(other.getTotalVariableHandlingCharges())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRateType() != null) {
            _hashCode += getRateType().hashCode();
        }
        if (getRateScale() != null) {
            _hashCode += getRateScale().hashCode();
        }
        if (getRateZone() != null) {
            _hashCode += getRateZone().hashCode();
        }
        if (getPricingCode() != null) {
            _hashCode += getPricingCode().hashCode();
        }
        if (getRatedWeightMethod() != null) {
            _hashCode += getRatedWeightMethod().hashCode();
        }
        if (getMinimumChargeType() != null) {
            _hashCode += getMinimumChargeType().hashCode();
        }
        if (getCurrencyExchangeRate() != null) {
            _hashCode += getCurrencyExchangeRate().hashCode();
        }
        if (getSpecialRatingApplied() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSpecialRatingApplied());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSpecialRatingApplied(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDimDivisor() != null) {
            _hashCode += getDimDivisor().hashCode();
        }
        if (getDimDivisorType() != null) {
            _hashCode += getDimDivisorType().hashCode();
        }
        if (getFuelSurchargePercent() != null) {
            _hashCode += getFuelSurchargePercent().hashCode();
        }
        if (getTotalBillingWeight() != null) {
            _hashCode += getTotalBillingWeight().hashCode();
        }
        if (getTotalDimWeight() != null) {
            _hashCode += getTotalDimWeight().hashCode();
        }
        if (getTotalBaseCharge() != null) {
            _hashCode += getTotalBaseCharge().hashCode();
        }
        if (getTotalFreightDiscounts() != null) {
            _hashCode += getTotalFreightDiscounts().hashCode();
        }
        if (getTotalNetFreight() != null) {
            _hashCode += getTotalNetFreight().hashCode();
        }
        if (getTotalSurcharges() != null) {
            _hashCode += getTotalSurcharges().hashCode();
        }
        if (getTotalNetFedExCharge() != null) {
            _hashCode += getTotalNetFedExCharge().hashCode();
        }
        if (getTotalTaxes() != null) {
            _hashCode += getTotalTaxes().hashCode();
        }
        if (getTotalNetCharge() != null) {
            _hashCode += getTotalNetCharge().hashCode();
        }
        if (getTotalRebates() != null) {
            _hashCode += getTotalRebates().hashCode();
        }
        if (getTotalDutiesAndTaxes() != null) {
            _hashCode += getTotalDutiesAndTaxes().hashCode();
        }
        if (getTotalAncillaryFeesAndTaxes() != null) {
            _hashCode += getTotalAncillaryFeesAndTaxes().hashCode();
        }
        if (getTotalDutiesTaxesAndFees() != null) {
            _hashCode += getTotalDutiesTaxesAndFees().hashCode();
        }
        if (getTotalNetChargeWithDutiesAndTaxes() != null) {
            _hashCode += getTotalNetChargeWithDutiesAndTaxes().hashCode();
        }
        if (getShipmentLegRateDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShipmentLegRateDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShipmentLegRateDetails(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFreightRateDetail() != null) {
            _hashCode += getFreightRateDetail().hashCode();
        }
        if (getFreightDiscounts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFreightDiscounts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFreightDiscounts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRebates() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRebates());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRebates(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSurcharges() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSurcharges());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSurcharges(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTaxes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTaxes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTaxes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDutiesAndTaxes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDutiesAndTaxes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDutiesAndTaxes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAncillaryFeesAndTaxes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAncillaryFeesAndTaxes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAncillaryFeesAndTaxes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVariableHandlingCharges() != null) {
            _hashCode += getVariableHandlingCharges().hashCode();
        }
        if (getTotalVariableHandlingCharges() != null) {
            _hashCode += getTotalVariableHandlingCharges().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentRateDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentRateDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnedRateType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateScale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateScale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateZone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateZone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pricingCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PricingCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PricingCodeType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratedWeightMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RatedWeightMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RatedWeightMethod"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minimumChargeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MinimumChargeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MinimumChargeType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyExchangeRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CurrencyExchangeRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CurrencyExchangeRate"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialRatingApplied");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialRatingApplied"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialRatingAppliedType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimDivisor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DimDivisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimDivisorType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DimDivisorType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateDimensionalDivisorType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fuelSurchargePercent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FuelSurchargePercent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalBillingWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalBillingWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalDimWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalDimWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalBaseCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalBaseCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalFreightDiscounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalFreightDiscounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalNetFreight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalNetFreight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSurcharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalSurcharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalNetFedExCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalNetFedExCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalTaxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalTaxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalNetCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalNetCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRebates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalRebates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalDutiesAndTaxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalDutiesAndTaxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalAncillaryFeesAndTaxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalAncillaryFeesAndTaxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalDutiesTaxesAndFees");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalDutiesTaxesAndFees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalNetChargeWithDutiesAndTaxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalNetChargeWithDutiesAndTaxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentLegRateDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentLegRateDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentLegRateDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightRateDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightRateDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightRateDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightDiscounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightDiscounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateDiscount"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rebates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Rebates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Rebate"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surcharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Surcharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Surcharge"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Taxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Tax"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dutiesAndTaxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DutiesAndTaxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EdtCommodityTax"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ancillaryFeesAndTaxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AncillaryFeesAndTaxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "AncillaryFeeAndTax"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variableHandlingCharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingCharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingCharges"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalVariableHandlingCharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalVariableHandlingCharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingCharges"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

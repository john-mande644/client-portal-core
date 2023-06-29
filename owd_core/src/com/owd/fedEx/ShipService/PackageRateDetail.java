/*
 * PackageRateDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;


/**
 * Data for a package's rates, as calculated per a specific rate type.
 */
public class PackageRateDetail  implements java.io.Serializable {
    /** The "PAYOR..." rates are expressed in the currency identified in
 * the payor's rate table(s). The "RATED..." rates are expressed in the
 * currency of the origin country. Former "...COUNTER..." values have
 * become "...RETAIL..." values, except for PAYOR_COUNTER and RATED_COUNTER,
 * which have been removed. */
    private com.owd.fedEx.ShipService.ReturnedRateType rateType;
    private com.owd.fedEx.ShipService.RatedWeightMethod ratedWeightMethod;
    private com.owd.fedEx.ShipService.MinimumChargeType minimumChargeType;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight billingWeight;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight dimWeight;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight oversizeWeight;
    private com.owd.fedEx.ShipService.Money baseCharge;
    private com.owd.fedEx.ShipService.Money totalFreightDiscounts;
    private com.owd.fedEx.ShipService.Money netFreight;
    private com.owd.fedEx.ShipService.Money totalSurcharges;
    private com.owd.fedEx.ShipService.Money netFedExCharge;
    private com.owd.fedEx.ShipService.Money totalTaxes;
    private com.owd.fedEx.ShipService.Money netCharge;
    private com.owd.fedEx.ShipService.Money totalRebates;
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
    private com.owd.fedEx.ShipService.VariableHandlingCharges variableHandlingCharges;

    public PackageRateDetail() {
    }


    /**
     * Gets the rateType value for this PackageRateDetail.
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
     * Sets the rateType value for this PackageRateDetail.
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
     * Gets the ratedWeightMethod value for this PackageRateDetail.
     * 
     * @return ratedWeightMethod
     */
    public com.owd.fedEx.ShipService.RatedWeightMethod getRatedWeightMethod() {
        return ratedWeightMethod;
    }


    /**
     * Sets the ratedWeightMethod value for this PackageRateDetail.
     * 
     * @param ratedWeightMethod
     */
    public void setRatedWeightMethod(com.owd.fedEx.ShipService.RatedWeightMethod ratedWeightMethod) {
        this.ratedWeightMethod = ratedWeightMethod;
    }


    /**
     * Gets the minimumChargeType value for this PackageRateDetail.
     * 
     * @return minimumChargeType
     */
    public com.owd.fedEx.ShipService.MinimumChargeType getMinimumChargeType() {
        return minimumChargeType;
    }


    /**
     * Sets the minimumChargeType value for this PackageRateDetail.
     * 
     * @param minimumChargeType
     */
    public void setMinimumChargeType(com.owd.fedEx.ShipService.MinimumChargeType minimumChargeType) {
        this.minimumChargeType = minimumChargeType;
    }


    /**
     * Gets the billingWeight value for this PackageRateDetail.
     * 
     * @return billingWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getBillingWeight() {
        return billingWeight;
    }


    /**
     * Sets the billingWeight value for this PackageRateDetail.
     * 
     * @param billingWeight The descriptive data for the heaviness of an object.
     */
    public void setBillingWeight(com.owd.fedEx.ShipService.Weight billingWeight) {
        this.billingWeight = billingWeight;
    }


    /**
     * Gets the dimWeight value for this PackageRateDetail.
     * 
     * @return dimWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getDimWeight() {
        return dimWeight;
    }


    /**
     * Sets the dimWeight value for this PackageRateDetail.
     * 
     * @param dimWeight The descriptive data for the heaviness of an object.
     */
    public void setDimWeight(com.owd.fedEx.ShipService.Weight dimWeight) {
        this.dimWeight = dimWeight;
    }


    /**
     * Gets the oversizeWeight value for this PackageRateDetail.
     * 
     * @return oversizeWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getOversizeWeight() {
        return oversizeWeight;
    }


    /**
     * Sets the oversizeWeight value for this PackageRateDetail.
     * 
     * @param oversizeWeight The descriptive data for the heaviness of an object.
     */
    public void setOversizeWeight(com.owd.fedEx.ShipService.Weight oversizeWeight) {
        this.oversizeWeight = oversizeWeight;
    }


    /**
     * Gets the baseCharge value for this PackageRateDetail.
     * 
     * @return baseCharge
     */
    public com.owd.fedEx.ShipService.Money getBaseCharge() {
        return baseCharge;
    }


    /**
     * Sets the baseCharge value for this PackageRateDetail.
     * 
     * @param baseCharge
     */
    public void setBaseCharge(com.owd.fedEx.ShipService.Money baseCharge) {
        this.baseCharge = baseCharge;
    }


    /**
     * Gets the totalFreightDiscounts value for this PackageRateDetail.
     * 
     * @return totalFreightDiscounts
     */
    public com.owd.fedEx.ShipService.Money getTotalFreightDiscounts() {
        return totalFreightDiscounts;
    }


    /**
     * Sets the totalFreightDiscounts value for this PackageRateDetail.
     * 
     * @param totalFreightDiscounts
     */
    public void setTotalFreightDiscounts(com.owd.fedEx.ShipService.Money totalFreightDiscounts) {
        this.totalFreightDiscounts = totalFreightDiscounts;
    }


    /**
     * Gets the netFreight value for this PackageRateDetail.
     * 
     * @return netFreight
     */
    public com.owd.fedEx.ShipService.Money getNetFreight() {
        return netFreight;
    }


    /**
     * Sets the netFreight value for this PackageRateDetail.
     * 
     * @param netFreight
     */
    public void setNetFreight(com.owd.fedEx.ShipService.Money netFreight) {
        this.netFreight = netFreight;
    }


    /**
     * Gets the totalSurcharges value for this PackageRateDetail.
     * 
     * @return totalSurcharges
     */
    public com.owd.fedEx.ShipService.Money getTotalSurcharges() {
        return totalSurcharges;
    }


    /**
     * Sets the totalSurcharges value for this PackageRateDetail.
     * 
     * @param totalSurcharges
     */
    public void setTotalSurcharges(com.owd.fedEx.ShipService.Money totalSurcharges) {
        this.totalSurcharges = totalSurcharges;
    }


    /**
     * Gets the netFedExCharge value for this PackageRateDetail.
     * 
     * @return netFedExCharge
     */
    public com.owd.fedEx.ShipService.Money getNetFedExCharge() {
        return netFedExCharge;
    }


    /**
     * Sets the netFedExCharge value for this PackageRateDetail.
     * 
     * @param netFedExCharge
     */
    public void setNetFedExCharge(com.owd.fedEx.ShipService.Money netFedExCharge) {
        this.netFedExCharge = netFedExCharge;
    }


    /**
     * Gets the totalTaxes value for this PackageRateDetail.
     * 
     * @return totalTaxes
     */
    public com.owd.fedEx.ShipService.Money getTotalTaxes() {
        return totalTaxes;
    }


    /**
     * Sets the totalTaxes value for this PackageRateDetail.
     * 
     * @param totalTaxes
     */
    public void setTotalTaxes(com.owd.fedEx.ShipService.Money totalTaxes) {
        this.totalTaxes = totalTaxes;
    }


    /**
     * Gets the netCharge value for this PackageRateDetail.
     * 
     * @return netCharge
     */
    public com.owd.fedEx.ShipService.Money getNetCharge() {
        return netCharge;
    }


    /**
     * Sets the netCharge value for this PackageRateDetail.
     * 
     * @param netCharge
     */
    public void setNetCharge(com.owd.fedEx.ShipService.Money netCharge) {
        this.netCharge = netCharge;
    }


    /**
     * Gets the totalRebates value for this PackageRateDetail.
     * 
     * @return totalRebates
     */
    public com.owd.fedEx.ShipService.Money getTotalRebates() {
        return totalRebates;
    }


    /**
     * Sets the totalRebates value for this PackageRateDetail.
     * 
     * @param totalRebates
     */
    public void setTotalRebates(com.owd.fedEx.ShipService.Money totalRebates) {
        this.totalRebates = totalRebates;
    }


    /**
     * Gets the freightDiscounts value for this PackageRateDetail.
     * 
     * @return freightDiscounts All rate discounts that apply to this package.
     */
    public com.owd.fedEx.ShipService.RateDiscount[] getFreightDiscounts() {
        return freightDiscounts;
    }


    /**
     * Sets the freightDiscounts value for this PackageRateDetail.
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
     * Gets the rebates value for this PackageRateDetail.
     * 
     * @return rebates All rebates that apply to this package.
     */
    public com.owd.fedEx.ShipService.Rebate[] getRebates() {
        return rebates;
    }


    /**
     * Sets the rebates value for this PackageRateDetail.
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
     * Gets the surcharges value for this PackageRateDetail.
     * 
     * @return surcharges All surcharges that apply to this package (either because of characteristics
 * of the package itself, or because it is carrying per-shipment surcharges
 * for the shipment of which it is a part).
     */
    public com.owd.fedEx.ShipService.Surcharge[] getSurcharges() {
        return surcharges;
    }


    /**
     * Sets the surcharges value for this PackageRateDetail.
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
     * Gets the taxes value for this PackageRateDetail.
     * 
     * @return taxes All taxes applicable (or distributed to) this package.
     */
    public com.owd.fedEx.ShipService.Tax[] getTaxes() {
        return taxes;
    }


    /**
     * Sets the taxes value for this PackageRateDetail.
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
     * Gets the variableHandlingCharges value for this PackageRateDetail.
     * 
     * @return variableHandlingCharges
     */
    public com.owd.fedEx.ShipService.VariableHandlingCharges getVariableHandlingCharges() {
        return variableHandlingCharges;
    }


    /**
     * Sets the variableHandlingCharges value for this PackageRateDetail.
     * 
     * @param variableHandlingCharges
     */
    public void setVariableHandlingCharges(com.owd.fedEx.ShipService.VariableHandlingCharges variableHandlingCharges) {
        this.variableHandlingCharges = variableHandlingCharges;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PackageRateDetail)) return false;
        PackageRateDetail other = (PackageRateDetail) obj;
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
            ((this.ratedWeightMethod==null && other.getRatedWeightMethod()==null) || 
             (this.ratedWeightMethod!=null &&
              this.ratedWeightMethod.equals(other.getRatedWeightMethod()))) &&
            ((this.minimumChargeType==null && other.getMinimumChargeType()==null) || 
             (this.minimumChargeType!=null &&
              this.minimumChargeType.equals(other.getMinimumChargeType()))) &&
            ((this.billingWeight==null && other.getBillingWeight()==null) || 
             (this.billingWeight!=null &&
              this.billingWeight.equals(other.getBillingWeight()))) &&
            ((this.dimWeight==null && other.getDimWeight()==null) || 
             (this.dimWeight!=null &&
              this.dimWeight.equals(other.getDimWeight()))) &&
            ((this.oversizeWeight==null && other.getOversizeWeight()==null) || 
             (this.oversizeWeight!=null &&
              this.oversizeWeight.equals(other.getOversizeWeight()))) &&
            ((this.baseCharge==null && other.getBaseCharge()==null) || 
             (this.baseCharge!=null &&
              this.baseCharge.equals(other.getBaseCharge()))) &&
            ((this.totalFreightDiscounts==null && other.getTotalFreightDiscounts()==null) || 
             (this.totalFreightDiscounts!=null &&
              this.totalFreightDiscounts.equals(other.getTotalFreightDiscounts()))) &&
            ((this.netFreight==null && other.getNetFreight()==null) || 
             (this.netFreight!=null &&
              this.netFreight.equals(other.getNetFreight()))) &&
            ((this.totalSurcharges==null && other.getTotalSurcharges()==null) || 
             (this.totalSurcharges!=null &&
              this.totalSurcharges.equals(other.getTotalSurcharges()))) &&
            ((this.netFedExCharge==null && other.getNetFedExCharge()==null) || 
             (this.netFedExCharge!=null &&
              this.netFedExCharge.equals(other.getNetFedExCharge()))) &&
            ((this.totalTaxes==null && other.getTotalTaxes()==null) || 
             (this.totalTaxes!=null &&
              this.totalTaxes.equals(other.getTotalTaxes()))) &&
            ((this.netCharge==null && other.getNetCharge()==null) || 
             (this.netCharge!=null &&
              this.netCharge.equals(other.getNetCharge()))) &&
            ((this.totalRebates==null && other.getTotalRebates()==null) || 
             (this.totalRebates!=null &&
              this.totalRebates.equals(other.getTotalRebates()))) &&
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
            ((this.variableHandlingCharges==null && other.getVariableHandlingCharges()==null) || 
             (this.variableHandlingCharges!=null &&
              this.variableHandlingCharges.equals(other.getVariableHandlingCharges())));
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
        if (getRatedWeightMethod() != null) {
            _hashCode += getRatedWeightMethod().hashCode();
        }
        if (getMinimumChargeType() != null) {
            _hashCode += getMinimumChargeType().hashCode();
        }
        if (getBillingWeight() != null) {
            _hashCode += getBillingWeight().hashCode();
        }
        if (getDimWeight() != null) {
            _hashCode += getDimWeight().hashCode();
        }
        if (getOversizeWeight() != null) {
            _hashCode += getOversizeWeight().hashCode();
        }
        if (getBaseCharge() != null) {
            _hashCode += getBaseCharge().hashCode();
        }
        if (getTotalFreightDiscounts() != null) {
            _hashCode += getTotalFreightDiscounts().hashCode();
        }
        if (getNetFreight() != null) {
            _hashCode += getNetFreight().hashCode();
        }
        if (getTotalSurcharges() != null) {
            _hashCode += getTotalSurcharges().hashCode();
        }
        if (getNetFedExCharge() != null) {
            _hashCode += getNetFedExCharge().hashCode();
        }
        if (getTotalTaxes() != null) {
            _hashCode += getTotalTaxes().hashCode();
        }
        if (getNetCharge() != null) {
            _hashCode += getNetCharge().hashCode();
        }
        if (getTotalRebates() != null) {
            _hashCode += getTotalRebates().hashCode();
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
        if (getVariableHandlingCharges() != null) {
            _hashCode += getVariableHandlingCharges().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PackageRateDetail.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageRateDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ReturnedRateType"));
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
        elemField.setFieldName("billingWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BillingWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DimWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oversizeWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "OversizeWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BaseCharge"));
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
        elemField.setFieldName("netFreight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetFreight"));
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
        elemField.setFieldName("netFedExCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetFedExCharge"));
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
        elemField.setFieldName("netCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NetCharge"));
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
        elemField.setFieldName("variableHandlingCharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingCharges"));
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

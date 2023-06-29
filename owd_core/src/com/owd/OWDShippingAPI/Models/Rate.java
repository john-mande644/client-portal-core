package com.owd.OWDShippingAPI.Models;

/**
 * Created by danny on 11/14/2019.
 */
public class Rate {

   private String shipService;
    private Double total;
    private Double listTotal ;
    private int commitmentTime ;
    private String error;
    private DetailedRate detailedRate;



    public String getShipService() {
        return shipService;
    }

    public void setShipService(String shipService) {
        this.shipService = shipService;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getListTotal() {
        return listTotal;
    }

    public void setListTotal(Double listTotal) {
        this.listTotal = listTotal;
    }

    public int getCommitmentTime() {
        return commitmentTime;
    }

    public void setCommitmentTime(int commitmentTime) {
        this.commitmentTime = commitmentTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public DetailedRate getDetailedRate() {
        return detailedRate;
    }

    public void setDetailedRate(DetailedRate detailedRate) {
        this.detailedRate = detailedRate;
    }

    public class DetailedRate{
        public Double basePrice ;
        public Double baseNet ;
        public Double total ;
        public Double discount ;
        public Double special ;
        public Double specialNet ;
        public String zone ;
        public String zoneNet ;
        public Double billedWeight ;
        public Double ratedWeight ;
        public Double declaredValueFee ;
        public Double declaredValueFeeNet ;
        public Double extendedAreaFee ;
        public Double extendedAreaFeeNet ;
        public String extendedAreaType ;
        public String xaOptions ;
        public Double fuelSurcharge ;
        public Double fuelSurchargeNet ;
        public Double environmentalSurcharge ;
        public Double environmentalSurchargeNet ;
        public Double returnsSurcharge ;
        public Double returnsSurchargeNet ;
        public Double notificationFee ;
        public Double notificationsFeeNet ;
        public Double codFee ;
        public Double codFeeNet ;

        public Double oversizeFee ;
        public Double oversizeFeeNet ;
        public Double additionalHandlingFee ;
        public Double additionalHandlingFeeNet ;
        public Double hazmatFee ;
        public Double hazmatFeeNet ;
        public Double proofFee ;
        public Double proofFeeNet ;

        public Double residentialFee ;
        public Double residentialFeeNet ;

        public Double saturdayDeliveryFee ;
        public Double saturdayDeliveryFeeNet ;
        public Double billingFee ;
        public Double billingFeeNet ;
        public Double restrictedDeliveryFee ;
        public Double restrictedDeliveryFeeNet ;

        public Double getBasePrice() {
            return basePrice;
        }

        public void setBasePrice(Double basePrice) {
            this.basePrice = basePrice;
        }

        public Double getBaseNet() {
            return baseNet;
        }

        public void setBaseNet(Double baseNet) {
            this.baseNet = baseNet;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
            this.discount = discount;
        }

        public Double getSpecial() {
            return special;
        }

        public void setSpecial(Double special) {
            this.special = special;
        }

        public Double getSpecialNet() {
            return specialNet;
        }

        public void setSpecialNet(Double specialNet) {
            this.specialNet = specialNet;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getZoneNet() {
            return zoneNet;
        }

        public void setZoneNet(String zoneNet) {
            this.zoneNet = zoneNet;
        }

        public Double getBilledWeight() {
            return billedWeight;
        }

        public void setBilledWeight(Double billedWeight) {
            this.billedWeight = billedWeight;
        }

        public Double getRatedWeight() {
            return ratedWeight;
        }

        public void setRatedWeight(Double ratedWeight) {
            this.ratedWeight = ratedWeight;
        }

        public Double getDeclaredValueFee() {
            return declaredValueFee;
        }

        public void setDeclaredValueFee(Double declaredValueFee) {
            this.declaredValueFee = declaredValueFee;
        }

        public Double getDeclaredValueFeeNet() {
            return declaredValueFeeNet;
        }

        public void setDeclaredValueFeeNet(Double declaredValueFeeNet) {
            this.declaredValueFeeNet = declaredValueFeeNet;
        }

        public Double getExtendedAreaFee() {
            return extendedAreaFee;
        }

        public void setExtendedAreaFee(Double extendedAreaFee) {
            this.extendedAreaFee = extendedAreaFee;
        }

        public Double getExtendedAreaFeeNet() {
            return extendedAreaFeeNet;
        }

        public void setExtendedAreaFeeNet(Double extendedAreaFeeNet) {
            this.extendedAreaFeeNet = extendedAreaFeeNet;
        }

        public String getExtendedAreaType() {
            return extendedAreaType;
        }

        public void setExtendedAreaType(String extendedAreaType) {
            this.extendedAreaType = extendedAreaType;
        }

        public String getXaOptions() {
            return xaOptions;
        }

        public void setXaOptions(String xaOptions) {
            this.xaOptions = xaOptions;
        }

        public Double getFuelSurcharge() {
            return fuelSurcharge;
        }

        public void setFuelSurcharge(Double fuelSurcharge) {
            this.fuelSurcharge = fuelSurcharge;
        }

        public Double getFuelSurchargeNet() {
            return fuelSurchargeNet;
        }

        public void setFuelSurchargeNet(Double fuelSurchargeNet) {
            this.fuelSurchargeNet = fuelSurchargeNet;
        }

        public Double getEnvironmentalSurcharge() {
            return environmentalSurcharge;
        }

        public void setEnvironmentalSurcharge(Double environmentalSurcharge) {
            this.environmentalSurcharge = environmentalSurcharge;
        }

        public Double getEnvironmentalSurchargeNet() {
            return environmentalSurchargeNet;
        }

        public void setEnvironmentalSurchargeNet(Double environmentalSurchargeNet) {
            this.environmentalSurchargeNet = environmentalSurchargeNet;
        }

        public Double getReturnsSurcharge() {
            return returnsSurcharge;
        }

        public void setReturnsSurcharge(Double returnsSurcharge) {
            this.returnsSurcharge = returnsSurcharge;
        }

        public Double getReturnsSurchargeNet() {
            return returnsSurchargeNet;
        }

        public void setReturnsSurchargeNet(Double returnsSurchargeNet) {
            this.returnsSurchargeNet = returnsSurchargeNet;
        }

        public Double getNotificationFee() {
            return notificationFee;
        }

        public void setNotificationFee(Double notificationFee) {
            this.notificationFee = notificationFee;
        }

        public Double getNotificationsFeeNet() {
            return notificationsFeeNet;
        }

        public void setNotificationsFeeNet(Double notificationsFeeNet) {
            this.notificationsFeeNet = notificationsFeeNet;
        }

        public Double getCodFee() {
            return codFee;
        }

        public void setCodFee(Double codFee) {
            this.codFee = codFee;
        }

        public Double getCodFeeNet() {
            return codFeeNet;
        }

        public void setCodFeeNet(Double codFeeNet) {
            this.codFeeNet = codFeeNet;
        }

        public Double getOversizeFee() {
            return oversizeFee;
        }

        public void setOversizeFee(Double oversizeFee) {
            this.oversizeFee = oversizeFee;
        }

        public Double getOversizeFeeNet() {
            return oversizeFeeNet;
        }

        public void setOversizeFeeNet(Double oversizeFeeNet) {
            this.oversizeFeeNet = oversizeFeeNet;
        }

        public Double getAdditionalHandlingFee() {
            return additionalHandlingFee;
        }

        public void setAdditionalHandlingFee(Double additionalHandlingFee) {
            this.additionalHandlingFee = additionalHandlingFee;
        }

        public Double getAdditionalHandlingFeeNet() {
            return additionalHandlingFeeNet;
        }

        public void setAdditionalHandlingFeeNet(Double additionalHandlingFeeNet) {
            this.additionalHandlingFeeNet = additionalHandlingFeeNet;
        }

        public Double getHazmatFee() {
            return hazmatFee;
        }

        public void setHazmatFee(Double hazmatFee) {
            this.hazmatFee = hazmatFee;
        }

        public Double getHazmatFeeNet() {
            return hazmatFeeNet;
        }

        public void setHazmatFeeNet(Double hazmatFeeNet) {
            this.hazmatFeeNet = hazmatFeeNet;
        }

        public Double getProofFee() {
            return proofFee;
        }

        public void setProofFee(Double proofFee) {
            this.proofFee = proofFee;
        }

        public Double getProofFeeNet() {
            return proofFeeNet;
        }

        public void setProofFeeNet(Double proofFeeNet) {
            this.proofFeeNet = proofFeeNet;
        }

        public Double getResidentialFee() {
            return residentialFee;
        }

        public void setResidentialFee(Double residentialFee) {
            this.residentialFee = residentialFee;
        }

        public Double getResidentialFeeNet() {
            return residentialFeeNet;
        }

        public void setResidentialFeeNet(Double residentialFeeNet) {
            this.residentialFeeNet = residentialFeeNet;
        }

        public Double getSaturdayDeliveryFee() {
            return saturdayDeliveryFee;
        }

        public void setSaturdayDeliveryFee(Double saturdayDeliveryFee) {
            this.saturdayDeliveryFee = saturdayDeliveryFee;
        }

        public Double getSaturdayDeliveryFeeNet() {
            return saturdayDeliveryFeeNet;
        }

        public void setSaturdayDeliveryFeeNet(Double saturdayDeliveryFeeNet) {
            this.saturdayDeliveryFeeNet = saturdayDeliveryFeeNet;
        }

        public Double getBillingFee() {
            return billingFee;
        }

        public void setBillingFee(Double billingFee) {
            this.billingFee = billingFee;
        }

        public Double getBillingFeeNet() {
            return billingFeeNet;
        }

        public void setBillingFeeNet(Double billingFeeNet) {
            this.billingFeeNet = billingFeeNet;
        }

        public Double getRestrictedDeliveryFee() {
            return restrictedDeliveryFee;
        }

        public void setRestrictedDeliveryFee(Double restrictedDeliveryFee) {
            this.restrictedDeliveryFee = restrictedDeliveryFee;
        }

        public Double getRestrictedDeliveryFeeNet() {
            return restrictedDeliveryFeeNet;
        }

        public void setRestrictedDeliveryFeeNet(Double restrictedDeliveryFeeNet) {
            this.restrictedDeliveryFeeNet = restrictedDeliveryFeeNet;
        }
    }
}

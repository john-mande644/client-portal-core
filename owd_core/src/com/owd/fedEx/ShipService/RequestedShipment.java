/*
 * RequestedShipment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

public class RequestedShipment  implements java.io.Serializable {
    private java.util.Calendar shipTimestamp;
    private com.owd.fedEx.ShipService.DropoffType dropoffType;
    private com.owd.fedEx.ShipService.ServiceType serviceType;
    private com.owd.fedEx.ShipService.PackagingType packagingType;
    private com.owd.fedEx.ShipService.ShipmentManifestDetail manifestDetail;
    /** The descriptive data for the heaviness of an object. */
    private com.owd.fedEx.ShipService.Weight totalWeight;
    private com.owd.fedEx.ShipService.Money totalInsuredValue;
    private java.lang.String preferredCurrency;
    private com.owd.fedEx.ShipService.ShipmentAuthorizationDetail shipmentAuthorizationDetail;
    private com.owd.fedEx.ShipService.Party shipper;
    private com.owd.fedEx.ShipService.Party recipient;
    private java.lang.String recipientLocationNumber;
    private com.owd.fedEx.ShipService.ContactAndAddress origin;
    private com.owd.fedEx.ShipService.Party soldTo;
    private com.owd.fedEx.ShipService.Payment shippingChargesPayment;
    /** These special services are available at the shipment level for
 * some or all service types. If the shipper is requesting a special
 * service which requires additional data (such as the COD amount), the
 * shipment special service type must be present in the specialServiceTypes
 * collection, and the supporting detail must be provided in the appropriate
 * sub-object below. */
    private com.owd.fedEx.ShipService.ShipmentSpecialServicesRequested specialServicesRequested;
    private com.owd.fedEx.ShipService.ExpressFreightDetail expressFreightDetail;
    /** Data applicable to shipments using FEDEX_FREIGHT_ECONOMY and FEDEX_FREIGHT_PRIORITY
 * services. */
    private com.owd.fedEx.ShipService.FreightShipmentDetail freightShipmentDetail;
    private java.lang.String deliveryInstructions;
    /** This definition of variable handling charge detail is intended
 * for use in Jan 2011 corp load. */
    private com.owd.fedEx.ShipService.VariableHandlingChargeDetail variableHandlingChargeDetail;
    private com.owd.fedEx.ShipService.CustomsClearanceDetail customsClearanceDetail;
    /** This class describes the pickup characteristics of a shipment (e.g.
 * for use in a tag request). */
    private com.owd.fedEx.ShipService.PickupDetail pickupDetail;
    /** Data required for shipments handled under the SMART_POST and GROUND_SMART_POST
 * service types. */
    private com.owd.fedEx.ShipService.SmartPostShipmentDetail smartPostDetail;
    private java.lang.Boolean blockInsightVisibility;
    private com.owd.fedEx.ShipService.LabelSpecification labelSpecification;
    /** Contains all data required for additional (non-label) shipping
 * documents to be produced in conjunction with a specific shipment. */
    private com.owd.fedEx.ShipService.ShippingDocumentSpecification shippingDocumentSpecification;
    /** Specifies whether and what kind of rates the customer wishes to
 * have quoted on this shipment. The reply will also be constrained by
 * other data on the shipment and customer. */
    private com.owd.fedEx.ShipService.RateRequestType[] rateRequestTypes;
    /** Specifies the types of Estimated Duties and Taxes to be included
 * in a rate quotation for an international shipment. */
    private com.owd.fedEx.ShipService.EdtRequestType edtRequestType;
    private com.owd.fedEx.ShipService.TrackingId masterTrackingId;
    private org.apache.axis.types.NonNegativeInteger packageCount;
    /** Specifies data structures that may be re-used multiple times with
 * s single shipment. */
    private com.owd.fedEx.ShipService.ShipmentConfigurationData configurationData;
    /** One or more package-attribute descriptions, each of which describes
 * an individual package, a group of identical packages, or (for the
 * total-piece-total-weight case) common characteristics all packages
 * in the shipment. */
    private com.owd.fedEx.ShipService.RequestedPackageLineItem[] requestedPackageLineItems;

    public RequestedShipment() {
    }


    /**
     * Gets the shipTimestamp value for this RequestedShipment.
     * 
     * @return shipTimestamp
     */
    public java.util.Calendar getShipTimestamp() {
        return shipTimestamp;
    }


    /**
     * Sets the shipTimestamp value for this RequestedShipment.
     * 
     * @param shipTimestamp
     */
    public void setShipTimestamp(java.util.Calendar shipTimestamp) {
        this.shipTimestamp = shipTimestamp;
    }


    /**
     * Gets the dropoffType value for this RequestedShipment.
     * 
     * @return dropoffType
     */
    public com.owd.fedEx.ShipService.DropoffType getDropoffType() {
        return dropoffType;
    }


    /**
     * Sets the dropoffType value for this RequestedShipment.
     * 
     * @param dropoffType
     */
    public void setDropoffType(com.owd.fedEx.ShipService.DropoffType dropoffType) {
        this.dropoffType = dropoffType;
    }


    /**
     * Gets the serviceType value for this RequestedShipment.
     * 
     * @return serviceType
     */
    public com.owd.fedEx.ShipService.ServiceType getServiceType() {
        return serviceType;
    }


    /**
     * Sets the serviceType value for this RequestedShipment.
     * 
     * @param serviceType
     */
    public void setServiceType(com.owd.fedEx.ShipService.ServiceType serviceType) {
        this.serviceType = serviceType;
    }


    /**
     * Gets the packagingType value for this RequestedShipment.
     * 
     * @return packagingType
     */
    public com.owd.fedEx.ShipService.PackagingType getPackagingType() {
        return packagingType;
    }


    /**
     * Sets the packagingType value for this RequestedShipment.
     * 
     * @param packagingType
     */
    public void setPackagingType(com.owd.fedEx.ShipService.PackagingType packagingType) {
        this.packagingType = packagingType;
    }


    /**
     * Gets the manifestDetail value for this RequestedShipment.
     * 
     * @return manifestDetail
     */
    public com.owd.fedEx.ShipService.ShipmentManifestDetail getManifestDetail() {
        return manifestDetail;
    }


    /**
     * Sets the manifestDetail value for this RequestedShipment.
     * 
     * @param manifestDetail
     */
    public void setManifestDetail(com.owd.fedEx.ShipService.ShipmentManifestDetail manifestDetail) {
        this.manifestDetail = manifestDetail;
    }


    /**
     * Gets the totalWeight value for this RequestedShipment.
     * 
     * @return totalWeight The descriptive data for the heaviness of an object.
     */
    public com.owd.fedEx.ShipService.Weight getTotalWeight() {
        return totalWeight;
    }


    /**
     * Sets the totalWeight value for this RequestedShipment.
     * 
     * @param totalWeight The descriptive data for the heaviness of an object.
     */
    public void setTotalWeight(com.owd.fedEx.ShipService.Weight totalWeight) {
        this.totalWeight = totalWeight;
    }


    /**
     * Gets the totalInsuredValue value for this RequestedShipment.
     * 
     * @return totalInsuredValue
     */
    public com.owd.fedEx.ShipService.Money getTotalInsuredValue() {
        return totalInsuredValue;
    }


    /**
     * Sets the totalInsuredValue value for this RequestedShipment.
     * 
     * @param totalInsuredValue
     */
    public void setTotalInsuredValue(com.owd.fedEx.ShipService.Money totalInsuredValue) {
        this.totalInsuredValue = totalInsuredValue;
    }


    /**
     * Gets the preferredCurrency value for this RequestedShipment.
     * 
     * @return preferredCurrency
     */
    public java.lang.String getPreferredCurrency() {
        return preferredCurrency;
    }


    /**
     * Sets the preferredCurrency value for this RequestedShipment.
     * 
     * @param preferredCurrency
     */
    public void setPreferredCurrency(java.lang.String preferredCurrency) {
        this.preferredCurrency = preferredCurrency;
    }


    /**
     * Gets the shipmentAuthorizationDetail value for this RequestedShipment.
     * 
     * @return shipmentAuthorizationDetail
     */
    public com.owd.fedEx.ShipService.ShipmentAuthorizationDetail getShipmentAuthorizationDetail() {
        return shipmentAuthorizationDetail;
    }


    /**
     * Sets the shipmentAuthorizationDetail value for this RequestedShipment.
     * 
     * @param shipmentAuthorizationDetail
     */
    public void setShipmentAuthorizationDetail(com.owd.fedEx.ShipService.ShipmentAuthorizationDetail shipmentAuthorizationDetail) {
        this.shipmentAuthorizationDetail = shipmentAuthorizationDetail;
    }


    /**
     * Gets the shipper value for this RequestedShipment.
     * 
     * @return shipper
     */
    public com.owd.fedEx.ShipService.Party getShipper() {
        return shipper;
    }


    /**
     * Sets the shipper value for this RequestedShipment.
     * 
     * @param shipper
     */
    public void setShipper(com.owd.fedEx.ShipService.Party shipper) {
        this.shipper = shipper;
    }


    /**
     * Gets the recipient value for this RequestedShipment.
     * 
     * @return recipient
     */
    public com.owd.fedEx.ShipService.Party getRecipient() {
        return recipient;
    }


    /**
     * Sets the recipient value for this RequestedShipment.
     * 
     * @param recipient
     */
    public void setRecipient(com.owd.fedEx.ShipService.Party recipient) {
        this.recipient = recipient;
    }


    /**
     * Gets the recipientLocationNumber value for this RequestedShipment.
     * 
     * @return recipientLocationNumber
     */
    public java.lang.String getRecipientLocationNumber() {
        return recipientLocationNumber;
    }


    /**
     * Sets the recipientLocationNumber value for this RequestedShipment.
     * 
     * @param recipientLocationNumber
     */
    public void setRecipientLocationNumber(java.lang.String recipientLocationNumber) {
        this.recipientLocationNumber = recipientLocationNumber;
    }


    /**
     * Gets the origin value for this RequestedShipment.
     * 
     * @return origin
     */
    public com.owd.fedEx.ShipService.ContactAndAddress getOrigin() {
        return origin;
    }


    /**
     * Sets the origin value for this RequestedShipment.
     * 
     * @param origin
     */
    public void setOrigin(com.owd.fedEx.ShipService.ContactAndAddress origin) {
        this.origin = origin;
    }


    /**
     * Gets the soldTo value for this RequestedShipment.
     * 
     * @return soldTo
     */
    public com.owd.fedEx.ShipService.Party getSoldTo() {
        return soldTo;
    }


    /**
     * Sets the soldTo value for this RequestedShipment.
     * 
     * @param soldTo
     */
    public void setSoldTo(com.owd.fedEx.ShipService.Party soldTo) {
        this.soldTo = soldTo;
    }


    /**
     * Gets the shippingChargesPayment value for this RequestedShipment.
     * 
     * @return shippingChargesPayment
     */
    public com.owd.fedEx.ShipService.Payment getShippingChargesPayment() {
        return shippingChargesPayment;
    }


    /**
     * Sets the shippingChargesPayment value for this RequestedShipment.
     * 
     * @param shippingChargesPayment
     */
    public void setShippingChargesPayment(com.owd.fedEx.ShipService.Payment shippingChargesPayment) {
        this.shippingChargesPayment = shippingChargesPayment;
    }


    /**
     * Gets the specialServicesRequested value for this RequestedShipment.
     * 
     * @return specialServicesRequested These special services are available at the shipment level for
 * some or all service types. If the shipper is requesting a special
 * service which requires additional data (such as the COD amount), the
 * shipment special service type must be present in the specialServiceTypes
 * collection, and the supporting detail must be provided in the appropriate
 * sub-object below.
     */
    public com.owd.fedEx.ShipService.ShipmentSpecialServicesRequested getSpecialServicesRequested() {
        return specialServicesRequested;
    }


    /**
     * Sets the specialServicesRequested value for this RequestedShipment.
     * 
     * @param specialServicesRequested These special services are available at the shipment level for
 * some or all service types. If the shipper is requesting a special
 * service which requires additional data (such as the COD amount), the
 * shipment special service type must be present in the specialServiceTypes
 * collection, and the supporting detail must be provided in the appropriate
 * sub-object below.
     */
    public void setSpecialServicesRequested(com.owd.fedEx.ShipService.ShipmentSpecialServicesRequested specialServicesRequested) {
        this.specialServicesRequested = specialServicesRequested;
    }


    /**
     * Gets the expressFreightDetail value for this RequestedShipment.
     * 
     * @return expressFreightDetail
     */
    public com.owd.fedEx.ShipService.ExpressFreightDetail getExpressFreightDetail() {
        return expressFreightDetail;
    }


    /**
     * Sets the expressFreightDetail value for this RequestedShipment.
     * 
     * @param expressFreightDetail
     */
    public void setExpressFreightDetail(com.owd.fedEx.ShipService.ExpressFreightDetail expressFreightDetail) {
        this.expressFreightDetail = expressFreightDetail;
    }


    /**
     * Gets the freightShipmentDetail value for this RequestedShipment.
     * 
     * @return freightShipmentDetail Data applicable to shipments using FEDEX_FREIGHT_ECONOMY and FEDEX_FREIGHT_PRIORITY
 * services.
     */
    public com.owd.fedEx.ShipService.FreightShipmentDetail getFreightShipmentDetail() {
        return freightShipmentDetail;
    }


    /**
     * Sets the freightShipmentDetail value for this RequestedShipment.
     * 
     * @param freightShipmentDetail Data applicable to shipments using FEDEX_FREIGHT_ECONOMY and FEDEX_FREIGHT_PRIORITY
 * services.
     */
    public void setFreightShipmentDetail(com.owd.fedEx.ShipService.FreightShipmentDetail freightShipmentDetail) {
        this.freightShipmentDetail = freightShipmentDetail;
    }


    /**
     * Gets the deliveryInstructions value for this RequestedShipment.
     * 
     * @return deliveryInstructions
     */
    public java.lang.String getDeliveryInstructions() {
        return deliveryInstructions;
    }


    /**
     * Sets the deliveryInstructions value for this RequestedShipment.
     * 
     * @param deliveryInstructions
     */
    public void setDeliveryInstructions(java.lang.String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }


    /**
     * Gets the variableHandlingChargeDetail value for this RequestedShipment.
     * 
     * @return variableHandlingChargeDetail This definition of variable handling charge detail is intended
 * for use in Jan 2011 corp load.
     */
    public com.owd.fedEx.ShipService.VariableHandlingChargeDetail getVariableHandlingChargeDetail() {
        return variableHandlingChargeDetail;
    }


    /**
     * Sets the variableHandlingChargeDetail value for this RequestedShipment.
     * 
     * @param variableHandlingChargeDetail This definition of variable handling charge detail is intended
 * for use in Jan 2011 corp load.
     */
    public void setVariableHandlingChargeDetail(com.owd.fedEx.ShipService.VariableHandlingChargeDetail variableHandlingChargeDetail) {
        this.variableHandlingChargeDetail = variableHandlingChargeDetail;
    }


    /**
     * Gets the customsClearanceDetail value for this RequestedShipment.
     * 
     * @return customsClearanceDetail
     */
    public com.owd.fedEx.ShipService.CustomsClearanceDetail getCustomsClearanceDetail() {
        return customsClearanceDetail;
    }


    /**
     * Sets the customsClearanceDetail value for this RequestedShipment.
     * 
     * @param customsClearanceDetail
     */
    public void setCustomsClearanceDetail(com.owd.fedEx.ShipService.CustomsClearanceDetail customsClearanceDetail) {
        this.customsClearanceDetail = customsClearanceDetail;
    }


    /**
     * Gets the pickupDetail value for this RequestedShipment.
     * 
     * @return pickupDetail This class describes the pickup characteristics of a shipment (e.g.
 * for use in a tag request).
     */
    public com.owd.fedEx.ShipService.PickupDetail getPickupDetail() {
        return pickupDetail;
    }


    /**
     * Sets the pickupDetail value for this RequestedShipment.
     * 
     * @param pickupDetail This class describes the pickup characteristics of a shipment (e.g.
 * for use in a tag request).
     */
    public void setPickupDetail(com.owd.fedEx.ShipService.PickupDetail pickupDetail) {
        this.pickupDetail = pickupDetail;
    }


    /**
     * Gets the smartPostDetail value for this RequestedShipment.
     * 
     * @return smartPostDetail Data required for shipments handled under the SMART_POST and GROUND_SMART_POST
 * service types.
     */
    public com.owd.fedEx.ShipService.SmartPostShipmentDetail getSmartPostDetail() {
        return smartPostDetail;
    }


    /**
     * Sets the smartPostDetail value for this RequestedShipment.
     * 
     * @param smartPostDetail Data required for shipments handled under the SMART_POST and GROUND_SMART_POST
 * service types.
     */
    public void setSmartPostDetail(com.owd.fedEx.ShipService.SmartPostShipmentDetail smartPostDetail) {
        this.smartPostDetail = smartPostDetail;
    }


    /**
     * Gets the blockInsightVisibility value for this RequestedShipment.
     * 
     * @return blockInsightVisibility
     */
    public java.lang.Boolean getBlockInsightVisibility() {
        return blockInsightVisibility;
    }


    /**
     * Sets the blockInsightVisibility value for this RequestedShipment.
     * 
     * @param blockInsightVisibility
     */
    public void setBlockInsightVisibility(java.lang.Boolean blockInsightVisibility) {
        this.blockInsightVisibility = blockInsightVisibility;
    }


    /**
     * Gets the labelSpecification value for this RequestedShipment.
     * 
     * @return labelSpecification
     */
    public com.owd.fedEx.ShipService.LabelSpecification getLabelSpecification() {
        return labelSpecification;
    }


    /**
     * Sets the labelSpecification value for this RequestedShipment.
     * 
     * @param labelSpecification
     */
    public void setLabelSpecification(com.owd.fedEx.ShipService.LabelSpecification labelSpecification) {
        this.labelSpecification = labelSpecification;
    }


    /**
     * Gets the shippingDocumentSpecification value for this RequestedShipment.
     * 
     * @return shippingDocumentSpecification Contains all data required for additional (non-label) shipping
 * documents to be produced in conjunction with a specific shipment.
     */
    public com.owd.fedEx.ShipService.ShippingDocumentSpecification getShippingDocumentSpecification() {
        return shippingDocumentSpecification;
    }


    /**
     * Sets the shippingDocumentSpecification value for this RequestedShipment.
     * 
     * @param shippingDocumentSpecification Contains all data required for additional (non-label) shipping
 * documents to be produced in conjunction with a specific shipment.
     */
    public void setShippingDocumentSpecification(com.owd.fedEx.ShipService.ShippingDocumentSpecification shippingDocumentSpecification) {
        this.shippingDocumentSpecification = shippingDocumentSpecification;
    }


    /**
     * Gets the rateRequestTypes value for this RequestedShipment.
     * 
     * @return rateRequestTypes Specifies whether and what kind of rates the customer wishes to
 * have quoted on this shipment. The reply will also be constrained by
 * other data on the shipment and customer.
     */
    public com.owd.fedEx.ShipService.RateRequestType[] getRateRequestTypes() {
        return rateRequestTypes;
    }


    /**
     * Sets the rateRequestTypes value for this RequestedShipment.
     * 
     * @param rateRequestTypes Specifies whether and what kind of rates the customer wishes to
 * have quoted on this shipment. The reply will also be constrained by
 * other data on the shipment and customer.
     */
    public void setRateRequestTypes(com.owd.fedEx.ShipService.RateRequestType[] rateRequestTypes) {
        this.rateRequestTypes = rateRequestTypes;
    }

    public com.owd.fedEx.ShipService.RateRequestType getRateRequestTypes(int i) {
        return rateRequestTypes[i];
    }

    public void setRateRequestTypes(int i, com.owd.fedEx.ShipService.RateRequestType value) {
        this.rateRequestTypes[i] = value;
    }


    /**
     * Gets the edtRequestType value for this RequestedShipment.
     * 
     * @return edtRequestType Specifies the types of Estimated Duties and Taxes to be included
 * in a rate quotation for an international shipment.
     */
    public com.owd.fedEx.ShipService.EdtRequestType getEdtRequestType() {
        return edtRequestType;
    }


    /**
     * Sets the edtRequestType value for this RequestedShipment.
     * 
     * @param edtRequestType Specifies the types of Estimated Duties and Taxes to be included
 * in a rate quotation for an international shipment.
     */
    public void setEdtRequestType(com.owd.fedEx.ShipService.EdtRequestType edtRequestType) {
        this.edtRequestType = edtRequestType;
    }


    /**
     * Gets the masterTrackingId value for this RequestedShipment.
     * 
     * @return masterTrackingId
     */
    public com.owd.fedEx.ShipService.TrackingId getMasterTrackingId() {
        return masterTrackingId;
    }


    /**
     * Sets the masterTrackingId value for this RequestedShipment.
     * 
     * @param masterTrackingId
     */
    public void setMasterTrackingId(com.owd.fedEx.ShipService.TrackingId masterTrackingId) {
        this.masterTrackingId = masterTrackingId;
    }


    /**
     * Gets the packageCount value for this RequestedShipment.
     * 
     * @return packageCount
     */
    public org.apache.axis.types.NonNegativeInteger getPackageCount() {
        return packageCount;
    }


    /**
     * Sets the packageCount value for this RequestedShipment.
     * 
     * @param packageCount
     */
    public void setPackageCount(org.apache.axis.types.NonNegativeInteger packageCount) {
        this.packageCount = packageCount;
    }


    /**
     * Gets the configurationData value for this RequestedShipment.
     * 
     * @return configurationData Specifies data structures that may be re-used multiple times with
 * s single shipment.
     */
    public com.owd.fedEx.ShipService.ShipmentConfigurationData getConfigurationData() {
        return configurationData;
    }


    /**
     * Sets the configurationData value for this RequestedShipment.
     * 
     * @param configurationData Specifies data structures that may be re-used multiple times with
 * s single shipment.
     */
    public void setConfigurationData(com.owd.fedEx.ShipService.ShipmentConfigurationData configurationData) {
        this.configurationData = configurationData;
    }


    /**
     * Gets the requestedPackageLineItems value for this RequestedShipment.
     * 
     * @return requestedPackageLineItems One or more package-attribute descriptions, each of which describes
 * an individual package, a group of identical packages, or (for the
 * total-piece-total-weight case) common characteristics all packages
 * in the shipment.
     */
    public com.owd.fedEx.ShipService.RequestedPackageLineItem[] getRequestedPackageLineItems() {
        return requestedPackageLineItems;
    }


    /**
     * Sets the requestedPackageLineItems value for this RequestedShipment.
     * 
     * @param requestedPackageLineItems One or more package-attribute descriptions, each of which describes
 * an individual package, a group of identical packages, or (for the
 * total-piece-total-weight case) common characteristics all packages
 * in the shipment.
     */
    public void setRequestedPackageLineItems(com.owd.fedEx.ShipService.RequestedPackageLineItem[] requestedPackageLineItems) {
        this.requestedPackageLineItems = requestedPackageLineItems;
    }

    public com.owd.fedEx.ShipService.RequestedPackageLineItem getRequestedPackageLineItems(int i) {
        return requestedPackageLineItems[i];
    }

    public void setRequestedPackageLineItems(int i, com.owd.fedEx.ShipService.RequestedPackageLineItem value) {
        this.requestedPackageLineItems[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestedShipment)) return false;
        RequestedShipment other = (RequestedShipment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.shipTimestamp==null && other.getShipTimestamp()==null) || 
             (this.shipTimestamp!=null &&
              this.shipTimestamp.equals(other.getShipTimestamp()))) &&
            ((this.dropoffType==null && other.getDropoffType()==null) || 
             (this.dropoffType!=null &&
              this.dropoffType.equals(other.getDropoffType()))) &&
            ((this.serviceType==null && other.getServiceType()==null) || 
             (this.serviceType!=null &&
              this.serviceType.equals(other.getServiceType()))) &&
            ((this.packagingType==null && other.getPackagingType()==null) || 
             (this.packagingType!=null &&
              this.packagingType.equals(other.getPackagingType()))) &&
            ((this.manifestDetail==null && other.getManifestDetail()==null) || 
             (this.manifestDetail!=null &&
              this.manifestDetail.equals(other.getManifestDetail()))) &&
            ((this.totalWeight==null && other.getTotalWeight()==null) || 
             (this.totalWeight!=null &&
              this.totalWeight.equals(other.getTotalWeight()))) &&
            ((this.totalInsuredValue==null && other.getTotalInsuredValue()==null) || 
             (this.totalInsuredValue!=null &&
              this.totalInsuredValue.equals(other.getTotalInsuredValue()))) &&
            ((this.preferredCurrency==null && other.getPreferredCurrency()==null) || 
             (this.preferredCurrency!=null &&
              this.preferredCurrency.equals(other.getPreferredCurrency()))) &&
            ((this.shipmentAuthorizationDetail==null && other.getShipmentAuthorizationDetail()==null) || 
             (this.shipmentAuthorizationDetail!=null &&
              this.shipmentAuthorizationDetail.equals(other.getShipmentAuthorizationDetail()))) &&
            ((this.shipper==null && other.getShipper()==null) || 
             (this.shipper!=null &&
              this.shipper.equals(other.getShipper()))) &&
            ((this.recipient==null && other.getRecipient()==null) || 
             (this.recipient!=null &&
              this.recipient.equals(other.getRecipient()))) &&
            ((this.recipientLocationNumber==null && other.getRecipientLocationNumber()==null) || 
             (this.recipientLocationNumber!=null &&
              this.recipientLocationNumber.equals(other.getRecipientLocationNumber()))) &&
            ((this.origin==null && other.getOrigin()==null) || 
             (this.origin!=null &&
              this.origin.equals(other.getOrigin()))) &&
            ((this.soldTo==null && other.getSoldTo()==null) || 
             (this.soldTo!=null &&
              this.soldTo.equals(other.getSoldTo()))) &&
            ((this.shippingChargesPayment==null && other.getShippingChargesPayment()==null) || 
             (this.shippingChargesPayment!=null &&
              this.shippingChargesPayment.equals(other.getShippingChargesPayment()))) &&
            ((this.specialServicesRequested==null && other.getSpecialServicesRequested()==null) || 
             (this.specialServicesRequested!=null &&
              this.specialServicesRequested.equals(other.getSpecialServicesRequested()))) &&
            ((this.expressFreightDetail==null && other.getExpressFreightDetail()==null) || 
             (this.expressFreightDetail!=null &&
              this.expressFreightDetail.equals(other.getExpressFreightDetail()))) &&
            ((this.freightShipmentDetail==null && other.getFreightShipmentDetail()==null) || 
             (this.freightShipmentDetail!=null &&
              this.freightShipmentDetail.equals(other.getFreightShipmentDetail()))) &&
            ((this.deliveryInstructions==null && other.getDeliveryInstructions()==null) || 
             (this.deliveryInstructions!=null &&
              this.deliveryInstructions.equals(other.getDeliveryInstructions()))) &&
            ((this.variableHandlingChargeDetail==null && other.getVariableHandlingChargeDetail()==null) || 
             (this.variableHandlingChargeDetail!=null &&
              this.variableHandlingChargeDetail.equals(other.getVariableHandlingChargeDetail()))) &&
            ((this.customsClearanceDetail==null && other.getCustomsClearanceDetail()==null) || 
             (this.customsClearanceDetail!=null &&
              this.customsClearanceDetail.equals(other.getCustomsClearanceDetail()))) &&
            ((this.pickupDetail==null && other.getPickupDetail()==null) || 
             (this.pickupDetail!=null &&
              this.pickupDetail.equals(other.getPickupDetail()))) &&
            ((this.smartPostDetail==null && other.getSmartPostDetail()==null) || 
             (this.smartPostDetail!=null &&
              this.smartPostDetail.equals(other.getSmartPostDetail()))) &&
            ((this.blockInsightVisibility==null && other.getBlockInsightVisibility()==null) || 
             (this.blockInsightVisibility!=null &&
              this.blockInsightVisibility.equals(other.getBlockInsightVisibility()))) &&
            ((this.labelSpecification==null && other.getLabelSpecification()==null) || 
             (this.labelSpecification!=null &&
              this.labelSpecification.equals(other.getLabelSpecification()))) &&
            ((this.shippingDocumentSpecification==null && other.getShippingDocumentSpecification()==null) || 
             (this.shippingDocumentSpecification!=null &&
              this.shippingDocumentSpecification.equals(other.getShippingDocumentSpecification()))) &&
            ((this.rateRequestTypes==null && other.getRateRequestTypes()==null) || 
             (this.rateRequestTypes!=null &&
              java.util.Arrays.equals(this.rateRequestTypes, other.getRateRequestTypes()))) &&
            ((this.edtRequestType==null && other.getEdtRequestType()==null) || 
             (this.edtRequestType!=null &&
              this.edtRequestType.equals(other.getEdtRequestType()))) &&
            ((this.masterTrackingId==null && other.getMasterTrackingId()==null) || 
             (this.masterTrackingId!=null &&
              this.masterTrackingId.equals(other.getMasterTrackingId()))) &&
            ((this.packageCount==null && other.getPackageCount()==null) || 
             (this.packageCount!=null &&
              this.packageCount.equals(other.getPackageCount()))) &&
            ((this.configurationData==null && other.getConfigurationData()==null) || 
             (this.configurationData!=null &&
              this.configurationData.equals(other.getConfigurationData()))) &&
            ((this.requestedPackageLineItems==null && other.getRequestedPackageLineItems()==null) || 
             (this.requestedPackageLineItems!=null &&
              java.util.Arrays.equals(this.requestedPackageLineItems, other.getRequestedPackageLineItems())));
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
        if (getShipTimestamp() != null) {
            _hashCode += getShipTimestamp().hashCode();
        }
        if (getDropoffType() != null) {
            _hashCode += getDropoffType().hashCode();
        }
        if (getServiceType() != null) {
            _hashCode += getServiceType().hashCode();
        }
        if (getPackagingType() != null) {
            _hashCode += getPackagingType().hashCode();
        }
        if (getManifestDetail() != null) {
            _hashCode += getManifestDetail().hashCode();
        }
        if (getTotalWeight() != null) {
            _hashCode += getTotalWeight().hashCode();
        }
        if (getTotalInsuredValue() != null) {
            _hashCode += getTotalInsuredValue().hashCode();
        }
        if (getPreferredCurrency() != null) {
            _hashCode += getPreferredCurrency().hashCode();
        }
        if (getShipmentAuthorizationDetail() != null) {
            _hashCode += getShipmentAuthorizationDetail().hashCode();
        }
        if (getShipper() != null) {
            _hashCode += getShipper().hashCode();
        }
        if (getRecipient() != null) {
            _hashCode += getRecipient().hashCode();
        }
        if (getRecipientLocationNumber() != null) {
            _hashCode += getRecipientLocationNumber().hashCode();
        }
        if (getOrigin() != null) {
            _hashCode += getOrigin().hashCode();
        }
        if (getSoldTo() != null) {
            _hashCode += getSoldTo().hashCode();
        }
        if (getShippingChargesPayment() != null) {
            _hashCode += getShippingChargesPayment().hashCode();
        }
        if (getSpecialServicesRequested() != null) {
            _hashCode += getSpecialServicesRequested().hashCode();
        }
        if (getExpressFreightDetail() != null) {
            _hashCode += getExpressFreightDetail().hashCode();
        }
        if (getFreightShipmentDetail() != null) {
            _hashCode += getFreightShipmentDetail().hashCode();
        }
        if (getDeliveryInstructions() != null) {
            _hashCode += getDeliveryInstructions().hashCode();
        }
        if (getVariableHandlingChargeDetail() != null) {
            _hashCode += getVariableHandlingChargeDetail().hashCode();
        }
        if (getCustomsClearanceDetail() != null) {
            _hashCode += getCustomsClearanceDetail().hashCode();
        }
        if (getPickupDetail() != null) {
            _hashCode += getPickupDetail().hashCode();
        }
        if (getSmartPostDetail() != null) {
            _hashCode += getSmartPostDetail().hashCode();
        }
        if (getBlockInsightVisibility() != null) {
            _hashCode += getBlockInsightVisibility().hashCode();
        }
        if (getLabelSpecification() != null) {
            _hashCode += getLabelSpecification().hashCode();
        }
        if (getShippingDocumentSpecification() != null) {
            _hashCode += getShippingDocumentSpecification().hashCode();
        }
        if (getRateRequestTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRateRequestTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRateRequestTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEdtRequestType() != null) {
            _hashCode += getEdtRequestType().hashCode();
        }
        if (getMasterTrackingId() != null) {
            _hashCode += getMasterTrackingId().hashCode();
        }
        if (getPackageCount() != null) {
            _hashCode += getPackageCount().hashCode();
        }
        if (getConfigurationData() != null) {
            _hashCode += getConfigurationData().hashCode();
        }
        if (getRequestedPackageLineItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequestedPackageLineItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequestedPackageLineItems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestedShipment.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RequestedShipment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dropoffType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DropoffType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DropoffType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ServiceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ServiceType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packagingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackagingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackagingType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manifestDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ManifestDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentManifestDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Weight"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalInsuredValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TotalInsuredValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Money"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferredCurrency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PreferredCurrency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentAuthorizationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentAuthorizationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentAuthorizationDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipper");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Shipper"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Recipient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientLocationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RecipientLocationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Origin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ContactAndAddress"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soldTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SoldTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Party"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingChargesPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingChargesPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Payment"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialServicesRequested");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SpecialServicesRequested"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentSpecialServicesRequested"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expressFreightDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExpressFreightDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ExpressFreightDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightShipmentDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightShipmentDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "FreightShipmentDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeliveryInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variableHandlingChargeDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingChargeDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "VariableHandlingChargeDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsClearanceDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsClearanceDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsClearanceDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PickupDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PickupDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smartPostDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "SmartPostShipmentDetail"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blockInsightVisibility");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "BlockInsightVisibility"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelSpecification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LabelSpecification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "LabelSpecification"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingDocumentSpecification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentSpecification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShippingDocumentSpecification"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateRequestTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateRequestTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RateRequestType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("edtRequestType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EdtRequestType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "EdtRequestType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("masterTrackingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "MasterTrackingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "TrackingId"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "PackageCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configurationData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ConfigurationData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentConfigurationData"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestedPackageLineItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RequestedPackageLineItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "RequestedPackageLineItem"));
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

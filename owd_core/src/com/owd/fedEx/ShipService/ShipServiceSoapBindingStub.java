/*
 * ShipServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.owd.fedEx.ShipService;

import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;

public class ShipServiceSoapBindingStub extends org.apache.axis.client.Stub implements com.owd.fedEx.ShipService.ShipPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[5];
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("processTag");
        oper.addParameter(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessTagRequest"), new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessTagRequest"), com.owd.fedEx.ShipService.ProcessTagRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessTagReply"));
        oper.setReturnClass(com.owd.fedEx.ShipService.ProcessTagReply.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessTagReply"));
        oper.setStyle(Style.DOCUMENT);
        oper.setUse(Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("processShipment");
        oper.addParameter(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessShipmentRequest"), new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessShipmentRequest"), com.owd.fedEx.ShipService.ProcessShipmentRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessShipmentReply"));
        oper.setReturnClass(com.owd.fedEx.ShipService.ProcessShipmentReply.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ProcessShipmentReply"));
        oper.setStyle(Style.DOCUMENT);
        oper.setUse(Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteTag");
        oper.addParameter(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeleteTagRequest"), new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeleteTagRequest"), com.owd.fedEx.ShipService.DeleteTagRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentReply"));
        oper.setReturnClass(com.owd.fedEx.ShipService.ShipmentReply.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentReply"));
        oper.setStyle(Style.DOCUMENT);
        oper.setUse(Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteShipment");
        oper.addParameter(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeleteShipmentRequest"), new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "DeleteShipmentRequest"), com.owd.fedEx.ShipService.DeleteShipmentRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentReply"));
        oper.setReturnClass(com.owd.fedEx.ShipService.ShipmentReply.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentReply"));
        oper.setStyle(Style.DOCUMENT);
        oper.setUse(Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("validateShipment");
        oper.addParameter(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ValidateShipmentRequest"), new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ValidateShipmentRequest"), com.owd.fedEx.ShipService.ValidateShipmentRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentReply"));
        oper.setReturnClass(com.owd.fedEx.ShipService.ShipmentReply.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "ShipmentReply"));
        oper.setStyle(Style.DOCUMENT);
        oper.setUse(Use.LITERAL);
        _operations[4] = oper;

    }

    public ShipServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ShipServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ShipServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            registerSerDeser("http://fedex.com/ws/ship/v23", "CodAddTransportationChargeBasisType", com.owd.fedEx.ShipService.CodAddTransportationChargeBasisType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentStockType", com.owd.fedEx.ShipService.ShippingDocumentStockType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentSpecification", com.owd.fedEx.ShipService.ShippingDocumentSpecification.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CodAddTransportationChargesDetail", com.owd.fedEx.ShipService.CodAddTransportationChargesDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentPart", com.owd.fedEx.ShipService.ShippingDocumentPart.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EtdAttributeType", com.owd.fedEx.ShipService.EtdAttributeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomsOptionType", com.owd.fedEx.ShipService.CustomsOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ReturnedShippingDocumentType", com.owd.fedEx.ShipService.ReturnedShippingDocumentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DangerousGoodsContainer", com.owd.fedEx.ShipService.DangerousGoodsContainer.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentEMailDetail", com.owd.fedEx.ShipService.ShippingDocumentEMailDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomsRoleType", com.owd.fedEx.ShipService.CustomsRoleType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SmartPostAncillaryEndorsementType", com.owd.fedEx.ShipService.SmartPostAncillaryEndorsementType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DayOfWeekType", com.owd.fedEx.ShipService.DayOfWeekType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ExportDeclarationDetail", com.owd.fedEx.ShipService.ExportDeclarationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentEventNotificationDetail", com.owd.fedEx.ShipService.ShipmentEventNotificationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RadioactivityDetail", com.owd.fedEx.ShipService.RadioactivityDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightOnValueType", com.owd.fedEx.ShipService.FreightOnValueType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ExportDetail", com.owd.fedEx.ShipService.ExportDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomerSpecifiedLabelGenerationOptionType", com.owd.fedEx.ShipService.CustomerSpecifiedLabelGenerationOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightShipmentRoleType", com.owd.fedEx.ShipService.FreightShipmentRoleType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityOptionDetail", com.owd.fedEx.ShipService.HazardousCommodityOptionDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityDescriptionProcessingOptionType", com.owd.fedEx.ShipService.HazardousCommodityDescriptionProcessingOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FedExLocationType", com.owd.fedEx.ShipService.FedExLocationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BatteryRegulatorySubType", com.owd.fedEx.ShipService.BatteryRegulatorySubType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityQuantityDetail", com.owd.fedEx.ShipService.HazardousCommodityQuantityDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DangerousGoodsAccessibilityType", com.owd.fedEx.ShipService.DangerousGoodsAccessibilityType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomsOptionDetail", com.owd.fedEx.ShipService.CustomsOptionDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "TaxType", com.owd.fedEx.ShipService.TaxType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PrintedReference", com.owd.fedEx.ShipService.PrintedReference.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RateRequestType", com.owd.fedEx.ShipService.RateRequestType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomerReferenceType", com.owd.fedEx.ShipService.CustomerReferenceType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RegulatoryControlType", com.owd.fedEx.ShipService.RegulatoryControlType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Contact", com.owd.fedEx.ShipService.Contact.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LiabilityCoverageDetail", com.owd.fedEx.ShipService.LiabilityCoverageDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentStorageDetailType", com.owd.fedEx.ShipService.ShippingDocumentStorageDetailType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityAttributeType", com.owd.fedEx.ShipService.HazardousCommodityAttributeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaLowValueStatementDetail", com.owd.fedEx.ShipService.NaftaLowValueStatementDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocTabZoneJustificationType", com.owd.fedEx.ShipService.DocTabZoneJustificationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BrokerDetail", com.owd.fedEx.ShipService.BrokerDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PhysicalFormType", com.owd.fedEx.ShipService.PhysicalFormType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RateTypeBasisType", com.owd.fedEx.ShipService.RateTypeBasisType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BarcodeSymbologyType", com.owd.fedEx.ShipService.BarcodeSymbologyType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "TrackingId", com.owd.fedEx.ShipService.TrackingId.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AdditionalLabelsDetail", com.owd.fedEx.ShipService.AdditionalLabelsDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "InternationalControlledExportType", com.owd.fedEx.ShipService.InternationalControlledExportType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentReply", com.owd.fedEx.ShipService.ShipmentReply.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ProcessShipmentReply", com.owd.fedEx.ShipService.ProcessShipmentReply.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomDeliveryWindowDetail", com.owd.fedEx.ShipService.CustomDeliveryWindowDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PickupRequestType", com.owd.fedEx.ShipService.PickupRequestType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ReturnAssociationDetail", com.owd.fedEx.ShipService.ReturnAssociationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LicenseOrPermitDetail", com.owd.fedEx.ShipService.LicenseOrPermitDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocumentFormatOptionsRequested", com.owd.fedEx.ShipService.DocumentFormatOptionsRequested.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RateDiscount", com.owd.fedEx.ShipService.RateDiscount.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomLabelDetail", com.owd.fedEx.ShipService.CustomLabelDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CodCollectionType", com.owd.fedEx.ShipService.CodCollectionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomLabelGraphicEntry", com.owd.fedEx.ShipService.CustomLabelGraphicEntry.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityPackingDetail", com.owd.fedEx.ShipService.HazardousCommodityPackingDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "OversizeClassType", com.owd.fedEx.ShipService.OversizeClassType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SmartPostShipmentProcessingOptionsRequested", com.owd.fedEx.ShipService.SmartPostShipmentProcessingOptionsRequested.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NetExplosiveDetail", com.owd.fedEx.ShipService.NetExplosiveDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HomeDeliveryPremiumDetail", com.owd.fedEx.ShipService.HomeDeliveryPremiumDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DestinationControlStatementType", com.owd.fedEx.ShipService.DestinationControlStatementType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PendingShipmentProcessingOptionType", com.owd.fedEx.ShipService.PendingShipmentProcessingOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocTabZoneSpecification", com.owd.fedEx.ShipService.DocTabZoneSpecification.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentSpecialServiceType", com.owd.fedEx.ShipService.ShipmentSpecialServiceType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NotificationFormatType", com.owd.fedEx.ShipService.NotificationFormatType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Rma", com.owd.fedEx.ShipService.Rma.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedEtdType", com.owd.fedEx.ShipService.CompletedEtdType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DeleteTagRequest", com.owd.fedEx.ShipService.DeleteTagRequest.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ReturnEMailAllowedSpecialServiceType", com.owd.fedEx.ShipService.ReturnEMailAllowedSpecialServiceType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentNotificationAggregationType", com.owd.fedEx.ShipService.ShipmentNotificationAggregationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomerSpecifiedLabelDetail", com.owd.fedEx.ShipService.CustomerSpecifiedLabelDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ValidateShipmentRequest", com.owd.fedEx.ShipService.ValidateShipmentRequest.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RecipientCustomsId", com.owd.fedEx.ShipService.RecipientCustomsId.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AssociatedFreightLineItemDetail", com.owd.fedEx.ShipService.AssociatedFreightLineItemDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Notification", com.owd.fedEx.ShipService.Notification.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BatteryClassificationDetail", com.owd.fedEx.ShipService.BatteryClassificationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "TransactionDetail", com.owd.fedEx.ShipService.TransactionDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RadioactivityUnitOfMeasure", com.owd.fedEx.ShipService.RadioactivityUnitOfMeasure.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SmartPostShipmentProcessingOptionType", com.owd.fedEx.ShipService.SmartPostShipmentProcessingOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RadionuclideDetail", com.owd.fedEx.ShipService.RadionuclideDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CodAdjustmentType", com.owd.fedEx.ShipService.CodAdjustmentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightRateQuoteType", com.owd.fedEx.ShipService.FreightRateQuoteType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentManifestDetail", com.owd.fedEx.ShipService.ShipmentManifestDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaNetCostMethodCode", com.owd.fedEx.ShipService.NaftaNetCostMethodCode.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EdtCommodityTax", com.owd.fedEx.ShipService.EdtCommodityTax.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomLabelBarcodeEntry", com.owd.fedEx.ShipService.CustomLabelBarcodeEntry.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentDryIceProcessingOptionType", com.owd.fedEx.ShipService.ShipmentDryIceProcessingOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Localization", com.owd.fedEx.ShipService.Localization.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CertificateOfOriginDetail", com.owd.fedEx.ShipService.CertificateOfOriginDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedPackageDetail", com.owd.fedEx.ShipService.CompletedPackageDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedCodDetail", com.owd.fedEx.ShipService.CompletedCodDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NetExplosiveClassificationType", com.owd.fedEx.ShipService.NetExplosiveClassificationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RequirementType", com.owd.fedEx.ShipService.RequirementType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DropoffType", com.owd.fedEx.ShipService.DropoffType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityPackingGroupType", com.owd.fedEx.ShipService.HazardousCommodityPackingGroupType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CommercialInvoiceDetail", com.owd.fedEx.ShipService.CommercialInvoiceDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HomeDeliveryPremiumType", com.owd.fedEx.ShipService.HomeDeliveryPremiumType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NotificationSeverityType", com.owd.fedEx.ShipService.NotificationSeverityType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EdtTaxType", com.owd.fedEx.ShipService.EdtTaxType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ContentRecord", com.owd.fedEx.ShipService.ContentRecord.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityPackagingDetail", com.owd.fedEx.ShipService.HazardousCommodityPackagingDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SecondaryBarcodeType", com.owd.fedEx.ShipService.SecondaryBarcodeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AlcoholDetail", com.owd.fedEx.ShipService.AlcoholDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LiabilityCoverageType", com.owd.fedEx.ShipService.LiabilityCoverageType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedTagDetail", com.owd.fedEx.ShipService.CompletedTagDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentNotificationFormatSpecification", com.owd.fedEx.ShipService.ShipmentNotificationFormatSpecification.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RegulatoryLabelContentDetail", com.owd.fedEx.ShipService.RegulatoryLabelContentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RecommendedDocumentType", com.owd.fedEx.ShipService.RecommendedDocumentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PackageRateDetail", com.owd.fedEx.ShipService.PackageRateDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BinaryBarcodeType", com.owd.fedEx.ShipService.BinaryBarcodeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Party", com.owd.fedEx.ShipService.Party.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "InternationalControlledExportDetail", com.owd.fedEx.ShipService.InternationalControlledExportDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PendingShipmentAccessorDetail", com.owd.fedEx.ShipService.PendingShipmentAccessorDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ProcessShipmentRequest", com.owd.fedEx.ShipService.ProcessShipmentRequest.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PendingShipmentDetail", com.owd.fedEx.ShipService.PendingShipmentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaCommodityDetail", com.owd.fedEx.ShipService.NaftaCommodityDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightBaseChargeCalculationType", com.owd.fedEx.ShipService.FreightBaseChargeCalculationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ServiceType", com.owd.fedEx.ShipService.ServiceType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentPrintDetail", com.owd.fedEx.ShipService.ShippingDocumentPrintDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedShipmentDetail", com.owd.fedEx.ShipService.CompletedShipmentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PhysicalPackagingType", com.owd.fedEx.ShipService.PhysicalPackagingType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "B13AFilingOptionType", com.owd.fedEx.ShipService.B13AFilingOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LabelSpecification", com.owd.fedEx.ShipService.LabelSpecification.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BinaryBarcode", com.owd.fedEx.ShipService.BinaryBarcode.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DeleteShipmentRequest", com.owd.fedEx.ShipService.DeleteShipmentRequest.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentDryIceProcessingOptionsRequested", com.owd.fedEx.ShipService.ShipmentDryIceProcessingOptionsRequested.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CodReturnPackageDetail", com.owd.fedEx.ShipService.CodReturnPackageDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedHazardousPackageDetail", com.owd.fedEx.ShipService.CompletedHazardousPackageDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocTabContentType", com.owd.fedEx.ShipService.DocTabContentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EmailOptionType", com.owd.fedEx.ShipService.EmailOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DangerousGoodsShippersDeclarationDetail", com.owd.fedEx.ShipService.DangerousGoodsShippersDeclarationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RateElementBasisType", com.owd.fedEx.ShipService.RateElementBasisType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightRateDetail", com.owd.fedEx.ShipService.FreightRateDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "TaxesOrMiscellaneousChargeType", com.owd.fedEx.ShipService.TaxesOrMiscellaneousChargeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PickupDetail", com.owd.fedEx.ShipService.PickupDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SignatureOptionDetail", com.owd.fedEx.ShipService.SignatureOptionDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ValidatedHazardousCommodityContent", com.owd.fedEx.ShipService.ValidatedHazardousCommodityContent.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "WebAuthenticationDetail", com.owd.fedEx.ShipService.WebAuthenticationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HoldAtLocationDetail", com.owd.fedEx.ShipService.HoldAtLocationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedEtdDetail", com.owd.fedEx.ShipService.CompletedEtdDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentNotificationRoleType", com.owd.fedEx.ShipService.ShipmentNotificationRoleType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocTabContentZone001", com.owd.fedEx.ShipService.DocTabContentZone001.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightBillOfLadingDetail", com.owd.fedEx.ShipService.FreightBillOfLadingDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Volume", com.owd.fedEx.ShipService.Volume.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LabelPrintingOrientationType", com.owd.fedEx.ShipService.LabelPrintingOrientationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomLabelCoordinateUnits", com.owd.fedEx.ShipService.CustomLabelCoordinateUnits.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PageQuadrantType", com.owd.fedEx.ShipService.PageQuadrantType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CodDetail", com.owd.fedEx.ShipService.CodDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ClientDetail", com.owd.fedEx.ShipService.ClientDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "TaxpayerIdentification", com.owd.fedEx.ShipService.TaxpayerIdentification.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "StringBarcode", com.owd.fedEx.ShipService.StringBarcode.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CodReturnReferenceIndicatorType", com.owd.fedEx.ShipService.CodReturnReferenceIndicatorType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomsDeclarationStatementType", com.owd.fedEx.ShipService.CustomsDeclarationStatementType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ReturnInstructionsDetail", com.owd.fedEx.ShipService.ReturnInstructionsDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentRateDetail", com.owd.fedEx.ShipService.ShipmentRateDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SmartPostIndiciaType", com.owd.fedEx.ShipService.SmartPostIndiciaType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ReturnShipmentDetail", com.owd.fedEx.ShipService.ReturnShipmentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentOperationalDetail", com.owd.fedEx.ShipService.ShipmentOperationalDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AlcoholRecipientType", com.owd.fedEx.ShipService.AlcoholRecipientType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "InternationalTrafficInArmsRegulationsDetail", com.owd.fedEx.ShipService.InternationalTrafficInArmsRegulationsDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PricingCodeType", com.owd.fedEx.ShipService.PricingCodeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CarrierCodeType", com.owd.fedEx.ShipService.CarrierCodeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "UploadDocumentType", com.owd.fedEx.ShipService.UploadDocumentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ReturnEMailDetail", com.owd.fedEx.ShipService.ReturnEMailDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SignatureOptionType", com.owd.fedEx.ShipService.SignatureOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PackageBarcodes", com.owd.fedEx.ShipService.PackageBarcodes.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ExpressFreightDetail", com.owd.fedEx.ShipService.ExpressFreightDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaPreferenceCriterionCode", com.owd.fedEx.ShipService.NaftaPreferenceCriterionCode.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EdtRequestType", com.owd.fedEx.ShipService.EdtRequestType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityDescription", com.owd.fedEx.ShipService.HazardousCommodityDescription.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightSpecialServicePayment", com.owd.fedEx.ShipService.FreightSpecialServicePayment.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EtdDetail", com.owd.fedEx.ShipService.EtdDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DeletionControlType", com.owd.fedEx.ShipService.DeletionControlType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocumentFormatOptionType", com.owd.fedEx.ShipService.DocumentFormatOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RateDimensionalDivisorType", com.owd.fedEx.ShipService.RateDimensionalDivisorType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocTabContentBarcoded", com.owd.fedEx.ShipService.DocTabContentBarcoded.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AdrLicenseDetail", com.owd.fedEx.ShipService.AdrLicenseDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentGroupingType", com.owd.fedEx.ShipService.ShippingDocumentGroupingType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CurrencyExchangeRate", com.owd.fedEx.ShipService.CurrencyExchangeRate.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Weight", com.owd.fedEx.ShipService.Weight.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomDeliveryWindowType", com.owd.fedEx.ShipService.CustomDeliveryWindowType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightChargeBasisType", com.owd.fedEx.ShipService.FreightChargeBasisType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "StringBarcodeType", com.owd.fedEx.ShipService.StringBarcodeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityRegulationType", com.owd.fedEx.ShipService.HazardousCommodityRegulationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomerReference", com.owd.fedEx.ShipService.CustomerReference.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightRateNotation", com.owd.fedEx.ShipService.FreightRateNotation.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "UploadDocumentReferenceDetail", com.owd.fedEx.ShipService.UploadDocumentReferenceDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocTabContent", com.owd.fedEx.ShipService.DocTabContent.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomerImageUsageType", com.owd.fedEx.ShipService.CustomerImageUsageType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocumentRequirementsDetail", com.owd.fedEx.ShipService.DocumentRequirementsDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EnterpriseDocumentType", com.owd.fedEx.ShipService.EnterpriseDocumentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LinearUnits", com.owd.fedEx.ShipService.LinearUnits.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "TinType", com.owd.fedEx.ShipService.TinType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PurposeOfShipmentType", com.owd.fedEx.ShipService.PurposeOfShipmentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentEMailGroupingType", com.owd.fedEx.ShipService.ShippingDocumentEMailGroupingType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityOptionType", com.owd.fedEx.ShipService.HazardousCommodityOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PickupRequestSourceType", com.owd.fedEx.ShipService.PickupRequestSourceType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DangerousGoodsSignatory", com.owd.fedEx.ShipService.DangerousGoodsSignatory.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RebateType", com.owd.fedEx.ShipService.RebateType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RatedWeightMethod", com.owd.fedEx.ShipService.RatedWeightMethod.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityQuantityType", com.owd.fedEx.ShipService.HazardousCommodityQuantityType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightClassType", com.owd.fedEx.ShipService.FreightClassType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AncillaryFeeAndTax", com.owd.fedEx.ShipService.AncillaryFeeAndTax.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "VariableHandlingChargeDetail", com.owd.fedEx.ShipService.VariableHandlingChargeDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DangerousGoodsDetail", com.owd.fedEx.ShipService.DangerousGoodsDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Money", com.owd.fedEx.ShipService.Money.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PendingShipmentType", com.owd.fedEx.ShipService.PendingShipmentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ReturnType", com.owd.fedEx.ShipService.ReturnType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "GroundDeliveryEligibilityType", com.owd.fedEx.ShipService.GroundDeliveryEligibilityType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SurchargeType", com.owd.fedEx.ShipService.SurchargeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DangerousGoodsPackingOptionType", com.owd.fedEx.ShipService.DangerousGoodsPackingOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightShipmentLineItem", com.owd.fedEx.ShipService.FreightShipmentLineItem.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "InternationalDocumentContentType", com.owd.fedEx.ShipService.InternationalDocumentContentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomLabelTextBoxEntry", com.owd.fedEx.ShipService.CustomLabelTextBoxEntry.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SurchargeLevelType", com.owd.fedEx.ShipService.SurchargeLevelType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DocumentGenerationDetail", com.owd.fedEx.ShipService.DocumentGenerationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "OperationalInstruction", com.owd.fedEx.ShipService.OperationalInstruction.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EMailLabelDetail", com.owd.fedEx.ShipService.EMailLabelDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RotationType", com.owd.fedEx.ShipService.RotationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousContainerPackingType", com.owd.fedEx.ShipService.HazardousContainerPackingType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "UploadDocumentProducerType", com.owd.fedEx.ShipService.UploadDocumentProducerType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ClearanceBrokerageType", com.owd.fedEx.ShipService.ClearanceBrokerageType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "WebAuthenticationCredential", com.owd.fedEx.ShipService.WebAuthenticationCredential.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentEventNotificationSpecification", com.owd.fedEx.ShipService.ShipmentEventNotificationSpecification.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightAddressLabelDetail", com.owd.fedEx.ShipService.FreightAddressLabelDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "UploadDocumentIdProducer", com.owd.fedEx.ShipService.UploadDocumentIdProducer.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AssociatedShipmentType", com.owd.fedEx.ShipService.AssociatedShipmentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentStorageDetail", com.owd.fedEx.ShipService.ShippingDocumentStorageDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RadionuclideActivity", com.owd.fedEx.ShipService.RadionuclideActivity.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomerImageUsage", com.owd.fedEx.ShipService.CustomerImageUsage.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CommodityPurposeType", com.owd.fedEx.ShipService.CommodityPurposeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaProducer", com.owd.fedEx.ShipService.NaftaProducer.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "TransitTimeType", com.owd.fedEx.ShipService.TransitTimeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RequestedShipment", com.owd.fedEx.ShipService.RequestedShipment.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LabelRotationType", com.owd.fedEx.ShipService.LabelRotationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RequestedPackageLineItem", com.owd.fedEx.ShipService.RequestedPackageLineItem.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomsClearanceDetail", com.owd.fedEx.ShipService.CustomsClearanceDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Op900Detail", com.owd.fedEx.ShipService.Op900Detail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "WeightUnits", com.owd.fedEx.ShipService.WeightUnits.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BatteryPackingType", com.owd.fedEx.ShipService.BatteryPackingType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "TrackingIdType", com.owd.fedEx.ShipService.TrackingIdType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NotificationParameter", com.owd.fedEx.ShipService.NotificationParameter.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ValidatedHazardousContainer", com.owd.fedEx.ShipService.ValidatedHazardousContainer.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RequiredDocumentType", com.owd.fedEx.ShipService.RequiredDocumentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "VersionId", com.owd.fedEx.ShipService.VersionId.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentLegRateDetail", com.owd.fedEx.ShipService.ShipmentLegRateDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RateDiscountType", com.owd.fedEx.ShipService.RateDiscountType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "VolumeUnits", com.owd.fedEx.ShipService.VolumeUnits.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocument", com.owd.fedEx.ShipService.ShippingDocument.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PendingShipmentProcessingOptionsRequested", com.owd.fedEx.ShipService.PendingShipmentProcessingOptionsRequested.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ImageId", com.owd.fedEx.ShipService.ImageId.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LabelStockType", com.owd.fedEx.ShipService.LabelStockType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentDryIceDetail", com.owd.fedEx.ShipService.ShipmentDryIceDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AdditionalLabelsType", com.owd.fedEx.ShipService.AdditionalLabelsType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ServiceDescription", com.owd.fedEx.ShipService.ServiceDescription.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AncillaryFeeAndTaxType", com.owd.fedEx.ShipService.AncillaryFeeAndTaxType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ReturnedRateType", com.owd.fedEx.ShipService.ReturnedRateType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ProcessTagRequest", com.owd.fedEx.ShipService.ProcessTagRequest.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightGuaranteeType", com.owd.fedEx.ShipService.FreightGuaranteeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CommercialInvoice", com.owd.fedEx.ShipService.CommercialInvoice.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightCollectTermsType", com.owd.fedEx.ShipService.FreightCollectTermsType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ProductName", com.owd.fedEx.ShipService.ProductName.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Dimensions", com.owd.fedEx.ShipService.Dimensions.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RegulatoryLabelType", com.owd.fedEx.ShipService.RegulatoryLabelType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentAuthorizationDetail", com.owd.fedEx.ShipService.ShipmentAuthorizationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Surcharge", com.owd.fedEx.ShipService.Surcharge.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EMailRecipient", com.owd.fedEx.ShipService.EMailRecipient.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentConfigurationData", com.owd.fedEx.ShipService.ShipmentConfigurationData.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightShipmentDetail", com.owd.fedEx.ShipService.FreightShipmentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedHazardousSummaryDetail", com.owd.fedEx.ShipService.CompletedHazardousSummaryDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentDispositionType", com.owd.fedEx.ShipService.ShippingDocumentDispositionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomDocumentDetail", com.owd.fedEx.ShipService.CustomDocumentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RadioactiveLabelType", com.owd.fedEx.ShipService.RadioactiveLabelType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EMailNotificationRecipientType", com.owd.fedEx.ShipService.EMailNotificationRecipientType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SpecialRatingAppliedType", com.owd.fedEx.ShipService.SpecialRatingAppliedType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EdtExciseCondition", com.owd.fedEx.ShipService.EdtExciseCondition.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomLabelTextEntry", com.owd.fedEx.ShipService.CustomLabelTextEntry.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LabelMaskableDataType", com.owd.fedEx.ShipService.LabelMaskableDataType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BatteryMaterialType", com.owd.fedEx.ShipService.BatteryMaterialType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EmailOptionsRequested", com.owd.fedEx.ShipService.EmailOptionsRequested.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityContent", com.owd.fedEx.ShipService.HazardousCommodityContent.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "GeneralAgencyAgreementDetail", com.owd.fedEx.ShipService.GeneralAgencyAgreementDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LinearMeasure", com.owd.fedEx.ShipService.LinearMeasure.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PackageRating", com.owd.fedEx.ShipService.PackageRating.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PrintedReferenceType", com.owd.fedEx.ShipService.PrintedReferenceType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Payment", com.owd.fedEx.ShipService.Payment.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PriorityAlertDetail", com.owd.fedEx.ShipService.PriorityAlertDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaCertificateOfOriginDetail", com.owd.fedEx.ShipService.NaftaCertificateOfOriginDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaProducerSpecificationType", com.owd.fedEx.ShipService.NaftaProducerSpecificationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomsDeclarationStatementDetail", com.owd.fedEx.ShipService.CustomsDeclarationStatementDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityInnerReceptacleDetail", com.owd.fedEx.ShipService.HazardousCommodityInnerReceptacleDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "MinimumChargeType", com.owd.fedEx.ShipService.MinimumChargeType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RecommendedDocumentSpecification", com.owd.fedEx.ShipService.RecommendedDocumentSpecification.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LabelFormatType", com.owd.fedEx.ShipService.LabelFormatType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "LabelOrderType", com.owd.fedEx.ShipService.LabelOrderType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DeliveryOnInvoiceAcceptanceDetail", com.owd.fedEx.ShipService.DeliveryOnInvoiceAcceptanceDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NotificationType", com.owd.fedEx.ShipService.NotificationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ProcessTagReply", com.owd.fedEx.ShipService.ProcessTagReply.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentImageType", com.owd.fedEx.ShipService.ShippingDocumentImageType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Measure", com.owd.fedEx.ShipService.Measure.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaImporterSpecificationType", com.owd.fedEx.ShipService.NaftaImporterSpecificationType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentFormat", com.owd.fedEx.ShipService.ShippingDocumentFormat.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PriorityAlertEnhancementType", com.owd.fedEx.ShipService.PriorityAlertEnhancementType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PackageSpecialServiceType", com.owd.fedEx.ShipService.PackageSpecialServiceType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RequestedShippingDocumentType", com.owd.fedEx.ShipService.RequestedShippingDocumentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PackageSpecialServicesRequested", com.owd.fedEx.ShipService.PackageSpecialServicesRequested.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "VariableHandlingCharges", com.owd.fedEx.ShipService.VariableHandlingCharges.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NotificationEventType", com.owd.fedEx.ShipService.NotificationEventType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ConfigurableLabelReferenceEntry", com.owd.fedEx.ShipService.ConfigurableLabelReferenceEntry.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AccessorRoleType", com.owd.fedEx.ShipService.AccessorRoleType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Rebate", com.owd.fedEx.ShipService.Rebate.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentRating", com.owd.fedEx.ShipService.ShipmentRating.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PackageOperationalDetail", com.owd.fedEx.ShipService.PackageOperationalDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "SmartPostShipmentDetail", com.owd.fedEx.ShipService.SmartPostShipmentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EdtTaxDetail", com.owd.fedEx.ShipService.EdtTaxDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "AssociatedShipmentDetail", com.owd.fedEx.ShipService.AssociatedShipmentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "EMailDetail", com.owd.fedEx.ShipService.EMailDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PaymentType", com.owd.fedEx.ShipService.PaymentType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedHoldAtLocationDetail", com.owd.fedEx.ShipService.CompletedHoldAtLocationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DateRange", com.owd.fedEx.ShipService.DateRange.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightBaseCharge", com.owd.fedEx.ShipService.FreightBaseCharge.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShipmentSpecialServicesRequested", com.owd.fedEx.ShipService.ShipmentSpecialServicesRequested.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomLabelPosition", com.owd.fedEx.ShipService.CustomLabelPosition.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PackagingType", com.owd.fedEx.ShipService.PackagingType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "PendingShipmentAccessDetail", com.owd.fedEx.ShipService.PendingShipmentAccessDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "DestinationControlDetail", com.owd.fedEx.ShipService.DestinationControlDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedHazardousShipmentDetail", com.owd.fedEx.ShipService.CompletedHazardousShipmentDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "FreightGuaranteeDetail", com.owd.fedEx.ShipService.FreightGuaranteeDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NotificationDetail", com.owd.fedEx.ShipService.NotificationDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentEMailRecipient", com.owd.fedEx.ShipService.ShippingDocumentEMailRecipient.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Commodity", com.owd.fedEx.ShipService.Commodity.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentNamingType", com.owd.fedEx.ShipService.ShippingDocumentNamingType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "NaftaProducerDeterminationCode", com.owd.fedEx.ShipService.NaftaProducerDeterminationCode.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RecipientCustomsIdType", com.owd.fedEx.ShipService.RecipientCustomsIdType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ChargeBasisLevelType", com.owd.fedEx.ShipService.ChargeBasisLevelType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "HazardousCommodityLabelTextOptionType", com.owd.fedEx.ShipService.HazardousCommodityLabelTextOptionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RadioactiveContainerClassType", com.owd.fedEx.ShipService.RadioactiveContainerClassType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "RelativeVerticalPositionType", com.owd.fedEx.ShipService.RelativeVerticalPositionType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Payor", com.owd.fedEx.ShipService.Payor.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CustomLabelBoxEntry", com.owd.fedEx.ShipService.CustomLabelBoxEntry.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Address", com.owd.fedEx.ShipService.Address.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ValidatedHazardousCommodityDescription", com.owd.fedEx.ShipService.ValidatedHazardousCommodityDescription.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "Tax", com.owd.fedEx.ShipService.Tax.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ContactAndAddress", com.owd.fedEx.ShipService.ContactAndAddress.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "BrokerType", com.owd.fedEx.ShipService.BrokerType.class, enumsf, enumdf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "ShippingDocumentDispositionDetail", com.owd.fedEx.ShipService.ShippingDocumentDispositionDetail.class, beansf, beandf);

            registerSerDeser("http://fedex.com/ws/ship/v23", "CompletedSmartPostDetail", com.owd.fedEx.ShipService.CompletedSmartPostDetail.class, beansf, beandf);

    }

    private org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call =
                    (org.apache.axis.client.Call) super.service.createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                        java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                        _call.registerTypeMapping(cls, qName, sf, df, false);
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", t);
        }
    }


	private void registerSerDeser(String ns, String typeName, java.lang.Class javaType, java.lang.Class serFactory, java.lang.Class deserFactory) {
		java.lang.Class cls;
		javax.xml.namespace.QName qName;
		qName = new javax.xml.namespace.QName(ns, typeName);
		cachedSerQNames.add(qName);
		cachedSerClasses.add(javaType);
		cachedSerFactories.add(serFactory);
		cachedDeserFactories.add(deserFactory);
	}

    public com.owd.fedEx.ShipService.ProcessTagReply processTag(com.owd.fedEx.ShipService.ProcessTagRequest processTagRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://fedex.com/ws/ship/v23/processTag");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "processTag"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {processTagRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.owd.fedEx.ShipService.ProcessTagReply) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.owd.fedEx.ShipService.ProcessTagReply) org.apache.axis.utils.JavaUtils.convert(_resp, com.owd.fedEx.ShipService.ProcessTagReply.class);
            }
        }
    }

    public com.owd.fedEx.ShipService.ProcessShipmentReply processShipment(com.owd.fedEx.ShipService.ProcessShipmentRequest processShipmentRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://fedex.com/ws/ship/v23/processShipment");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "processShipment"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {processShipmentRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.owd.fedEx.ShipService.ProcessShipmentReply) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.owd.fedEx.ShipService.ProcessShipmentReply) org.apache.axis.utils.JavaUtils.convert(_resp, com.owd.fedEx.ShipService.ProcessShipmentReply.class);
            }
        }
    }

    public com.owd.fedEx.ShipService.ShipmentReply deleteTag(com.owd.fedEx.ShipService.DeleteTagRequest deleteTagRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://fedex.com/ws/ship/v23/deleteTag");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "deleteTag"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {deleteTagRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.owd.fedEx.ShipService.ShipmentReply) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.owd.fedEx.ShipService.ShipmentReply) org.apache.axis.utils.JavaUtils.convert(_resp, com.owd.fedEx.ShipService.ShipmentReply.class);
            }
        }
    }

    public com.owd.fedEx.ShipService.ShipmentReply deleteShipment(com.owd.fedEx.ShipService.DeleteShipmentRequest deleteShipmentRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://fedex.com/ws/ship/v23/deleteShipment");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "deleteShipment"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {deleteShipmentRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.owd.fedEx.ShipService.ShipmentReply) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.owd.fedEx.ShipService.ShipmentReply) org.apache.axis.utils.JavaUtils.convert(_resp, com.owd.fedEx.ShipService.ShipmentReply.class);
            }
        }
    }

    public com.owd.fedEx.ShipService.ShipmentReply validateShipment(com.owd.fedEx.ShipService.ValidateShipmentRequest validateShipmentRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://fedex.com/ws/ship/v23/validateShipment");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "validateShipment"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {validateShipmentRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.owd.fedEx.ShipService.ShipmentReply) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.owd.fedEx.ShipService.ShipmentReply) org.apache.axis.utils.JavaUtils.convert(_resp, com.owd.fedEx.ShipService.ShipmentReply.class);
            }
        }
    }

}

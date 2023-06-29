
package com.owd.dc.manifest.BluePackage.classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BluePackageResponse {

    @SerializedName("MessageId")
    @Expose
    private String messageId;
    @SerializedName("Status")
    @Expose
    private Status status;
    @SerializedName("ConsignmentID")
    @Expose
    private String consignmentID;
    @SerializedName("RouteGroupIDOrServiceCodeList")
    @Expose
    private String routeGroupIDOrServiceCodeList;
    @SerializedName("AccountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("AllocationFilter")
    @Expose
    private String allocationFilter;
    @SerializedName("LabelType")
    @Expose
    private Integer labelType;
    @SerializedName("LabelSize")
    @Expose
    private Integer labelSize;
    @SerializedName("LabelDPI")
    @Expose
    private Integer labelDPI;
    @SerializedName("PackageList")
    @Expose
    private List<BluePackagePackage> packageList = null;
    @SerializedName("VASList")
    @Expose
    private List<VASList> vASList = null;
    @SerializedName("FreightShipment")
    @Expose
    private String freightShipment;
    @SerializedName("ShipFrom")
    @Expose
    private ShipFrom shipFrom;
    @SerializedName("ShipTo")
    @Expose
    private ShipTo shipTo;
    @SerializedName("Return")
    @Expose
    private Return _return;
    @SerializedName("ShipFromHub")
    @Expose
    private ShipFromHub shipFromHub;
    @SerializedName("USPSLocation")
    @Expose
    private USPSLocation uSPSLocation;
    @SerializedName("HoldAtLocation")
    @Expose
    private HoldAtLocation holdAtLocation;
    @SerializedName("CODRecipient")
    @Expose
    private CODRecipient cODRecipient;
    @SerializedName("NaftaProducer")
    @Expose
    private NaftaProducer naftaProducer;
    @SerializedName("Importer")
    @Expose
    private Importer importer;
    @SerializedName("Broker")
    @Expose
    private Broker broker;
    @SerializedName("UltConsignee")
    @Expose
    private UltConsignee ultConsignee;
    @SerializedName("Forwarder")
    @Expose
    private Forwarder forwarder;
    @SerializedName("IntermediateConsignee")
    @Expose
    private IntermediateConsignee intermediateConsignee;
    @SerializedName("DutyBillPayor")
    @Expose
    private DutyBillPayor dutyBillPayor;
    @SerializedName("BillPayor")
    @Expose
    private BillPayor billPayor;
    @SerializedName("ExpectedDeliveryDate")
    @Expose
    private String expectedDeliveryDate;
    @SerializedName("BillType")
    @Expose
    private Integer billType;
    @SerializedName("BillAccountNumber")
    @Expose
    private String billAccountNumber;
    @SerializedName("BillAccountZipCode")
    @Expose
    private String billAccountZipCode;
    @SerializedName("CreditCardNumber")
    @Expose
    private String creditCardNumber;
    @SerializedName("CreditCardType")
    @Expose
    private Integer creditCardType;
    @SerializedName("CreditCardExpireDate")
    @Expose
    private String creditCardExpireDate;
    @SerializedName("CreditCardCode")
    @Expose
    private String creditCardCode;
    @SerializedName("DeliveryType")
    @Expose
    private Integer deliveryType;
    @SerializedName("DropOffType")
    @Expose
    private Integer dropOffType;
    @SerializedName("SortCode1")
    @Expose
    private String sortCode1;
    @SerializedName("SortCode2")
    @Expose
    private String sortCode2;
    @SerializedName("SortCode3")
    @Expose
    private String sortCode3;
    @SerializedName("SortCode4")
    @Expose
    private String sortCode4;
    @SerializedName("SortCode5")
    @Expose
    private String sortCode5;
    @SerializedName("SortCode6")
    @Expose
    private String sortCode6;
    @SerializedName("CertifiedMailFlag")
    @Expose
    private Boolean certifiedMailFlag;
    @SerializedName("ReturnsPackCollectFlag")
    @Expose
    private Boolean returnsPackCollectFlag;
    @SerializedName("NDCPresortDiscountFlag")
    @Expose
    private Boolean nDCPresortDiscountFlag;
    @SerializedName("ODNCPresortDiscountFlag")
    @Expose
    private Boolean oDNCPresortDiscountFlag;
    @SerializedName("CODReferenceIndicatorType")
    @Expose
    private Integer cODReferenceIndicatorType;
    @SerializedName("CODRecipientAccountNumber")
    @Expose
    private String cODRecipientAccountNumber;
    @SerializedName("CODAddlChargeType")
    @Expose
    private Integer cODAddlChargeType;
    @SerializedName("CODAmt")
    @Expose
    private Integer cODAmt;
    @SerializedName("CODPayType")
    @Expose
    private Integer cODPayType;
    @SerializedName("DimensionUnit")
    @Expose
    private String dimensionUnit;
    @SerializedName("WeightUnit")
    @Expose
    private String weightUnit;
    @SerializedName("ShipDate")
    @Expose
    private String shipDate;
    @SerializedName("TrailerNumber")
    @Expose
    private String trailerNumber;
    @SerializedName("DropZip")
    @Expose
    private String dropZip;
    @SerializedName("ACSFlag")
    @Expose
    private Boolean aCSFlag;
    @SerializedName("ReturnLabelRequiredFlag")
    @Expose
    private Boolean returnLabelRequiredFlag;
    @SerializedName("MultiPieceShipmentTrackingNumber")
    @Expose
    private String multiPieceShipmentTrackingNumber;
    @SerializedName("MultiPieceShipmentTotalCount")
    @Expose
    private Integer multiPieceShipmentTotalCount;
    @SerializedName("MultiPieceShipmentPieceCounter")
    @Expose
    private Integer multiPieceShipmentPieceCounter;
    @SerializedName("InboundLoadId")
    @Expose
    private String inboundLoadId;
    @SerializedName("InboundLoadNumber")
    @Expose
    private String inboundLoadNumber;
    @SerializedName("OutboundLoadId")
    @Expose
    private String outboundLoadId;
    @SerializedName("OutboundLoadNumber")
    @Expose
    private String outboundLoadNumber;
    @SerializedName("BookingNumber")
    @Expose
    private Integer bookingNumber;
    @SerializedName("ShipperReleaseFlag")
    @Expose
    private Boolean shipperReleaseFlag;
    @SerializedName("ReturnType")
    @Expose
    private Integer returnType;
    @SerializedName("DeclarationStatementFlag")
    @Expose
    private Boolean declarationStatementFlag;
    @SerializedName("RoutedTransactionIndFlag")
    @Expose
    private Boolean routedTransactionIndFlag;
    @SerializedName("UseAddlCommentsFlag")
    @Expose
    private Boolean useAddlCommentsFlag;
    @SerializedName("ProductCode")
    @Expose
    private String productCode;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("ImageRotation")
    @Expose
    private String imageRotation;
    @SerializedName("OverrideShipToAddressValidationFlag")
    @Expose
    private Boolean overrideShipToAddressValidationFlag;
    @SerializedName("SupplyUsageFlag")
    @Expose
    private Boolean supplyUsageFlag;
    @SerializedName("SignatureReleaseNumber")
    @Expose
    private String signatureReleaseNumber;
    @SerializedName("PackageListEnclosedFlag")
    @Expose
    private Boolean packageListEnclosedFlag;
    @SerializedName("RegulatoryControlType")
    @Expose
    private Integer regulatoryControlType;
    @SerializedName("IsUPSHundredWeightFlag")
    @Expose
    private Boolean isUPSHundredWeightFlag;
    @SerializedName("AddlShipmentReference1")
    @Expose
    private String addlShipmentReference1;
    @SerializedName("AddlShipmentReference2")
    @Expose
    private String addlShipmentReference2;
    @SerializedName("AddlShipmentReference3")
    @Expose
    private String addlShipmentReference3;
    @SerializedName("AddlShipmentReference4")
    @Expose
    private String addlShipmentReference4;
    @SerializedName("AddlShipmentReference5")
    @Expose
    private String addlShipmentReference5;
    @SerializedName("AddlShipmentReference6")
    @Expose
    private String addlShipmentReference6;
    @SerializedName("AddlShipmentReference7")
    @Expose
    private String addlShipmentReference7;
    @SerializedName("AddlShipmentReference8")
    @Expose
    private String addlShipmentReference8;
    @SerializedName("DeliveryConfirmationFlag")
    @Expose
    private Boolean deliveryConfirmationFlag;
    @SerializedName("SignatureConfirmationFlag")
    @Expose
    private Boolean signatureConfirmationFlag;
    @SerializedName("SignatureConfirmationType")
    @Expose
    private Integer signatureConfirmationType;
    @SerializedName("USPSRateIndicator")
    @Expose
    private String uSPSRateIndicator;
    @SerializedName("DryIceWeight")
    @Expose
    private Integer dryIceWeight;
    @SerializedName("SignatureReleaseFlag")
    @Expose
    private Boolean signatureReleaseFlag;
    @SerializedName("InsuranceAmount")
    @Expose
    private Integer insuranceAmount;
    @SerializedName("InsurancePayType")
    @Expose
    private Integer insurancePayType;
    @SerializedName("DeclaredValue")
    @Expose
    private Integer declaredValue;
    @SerializedName("InsuranceType")
    @Expose
    private String insuranceType;
    @SerializedName("EndorsementType")
    @Expose
    private Integer endorsementType;
    @SerializedName("FragileFlag")
    @Expose
    private Boolean fragileFlag;
    @SerializedName("USPSSortLevel")
    @Expose
    private Integer uSPSSortLevel;
    @SerializedName("USPSODEnclosedMailClass")
    @Expose
    private Integer uSPSODEnclosedMailClass;
    @SerializedName("ProcessCategory")
    @Expose
    private Integer processCategory;
    @SerializedName("OriginallyCapturedPackageId")
    @Expose
    private String originallyCapturedPackageId;
    @SerializedName("Harmonizedcode")
    @Expose
    private String harmonizedcode;
    @SerializedName("SalesTaxAmount")
    @Expose
    private Integer salesTaxAmount;
    @SerializedName("DutyAmount")
    @Expose
    private Integer dutyAmount;
    @SerializedName("ContentDescription")
    @Expose
    private String contentDescription;
    @SerializedName("AdditionalHandlingFlag")
    @Expose
    private Boolean additionalHandlingFlag;
    @SerializedName("NotificationType")
    @Expose
    private Integer notificationType;
    @SerializedName("NotificationValue")
    @Expose
    private String notificationValue;
    @SerializedName("CN22DescriptionType")
    @Expose
    private Integer cN22DescriptionType;
    @SerializedName("ShipperECCN")
    @Expose
    private String shipperECCN;
    @SerializedName("ShipperXTN")
    @Expose
    private String shipperXTN;
    @SerializedName("ShipperFTSR")
    @Expose
    private String shipperFTSR;
    @SerializedName("Incoterms")
    @Expose
    private String incoterms;
    @SerializedName("ExportLicense")
    @Expose
    private String exportLicense;
    @SerializedName("ImportLicense")
    @Expose
    private String importLicense;
    @SerializedName("ConsigneeCode")
    @Expose
    private String consigneeCode;
    @SerializedName("ConsigneeEIN")
    @Expose
    private String consigneeEIN;
    @SerializedName("ConsigneeVAT")
    @Expose
    private String consigneeVAT;
    @SerializedName("SignatureName")
    @Expose
    private String signatureName;
    @SerializedName("SignatureTitle")
    @Expose
    private String signatureTitle;
    @SerializedName("ExportReasonCode")
    @Expose
    private Integer exportReasonCode;
    @SerializedName("ShipperEin")
    @Expose
    private String shipperEin;
    @SerializedName("SEDFilingOption")
    @Expose
    private Integer sEDFilingOption;
    @SerializedName("SignaturePhone")
    @Expose
    private String signaturePhone;
    @SerializedName("SignatureEmailAddress")
    @Expose
    private String signatureEmailAddress;
    @SerializedName("IsDepartmentOfStateShipment")
    @Expose
    private Boolean isDepartmentOfStateShipment;
    @SerializedName("IsDepartmentOfStateExempt")
    @Expose
    private Boolean isDepartmentOfStateExempt;
    @SerializedName("DOS22CFRExemptNumber")
    @Expose
    private String dOS22CFRExemptNumber;
    @SerializedName("CommercialInvoiceType")
    @Expose
    private String commercialInvoiceType;
    @SerializedName("CommercialInvoiceNumber")
    @Expose
    private String commercialInvoiceNumber;
    @SerializedName("CommercialInvoiceOtherCharges")
    @Expose
    private Integer commercialInvoiceOtherCharges;
    @SerializedName("Use3rdPartyBrokerFlag")
    @Expose
    private Boolean use3rdPartyBrokerFlag;
    @SerializedName("BrokerShipAlertFlag")
    @Expose
    private Boolean brokerShipAlertFlag;
    @SerializedName("BrokerDeliveryNotificationFlag")
    @Expose
    private Boolean brokerDeliveryNotificationFlag;
    @SerializedName("BrokerExceptionNotificationFlag")
    @Expose
    private Boolean brokerExceptionNotificationFlag;
    @SerializedName("BrokerEmailFormat")
    @Expose
    private String brokerEmailFormat;
    @SerializedName("BrokerAccountNumber")
    @Expose
    private String brokerAccountNumber;
    @SerializedName("AdditionalComments")
    @Expose
    private String additionalComments;
    @SerializedName("DutiesTaxAccountNumber")
    @Expose
    private String dutiesTaxAccountNumber;
    @SerializedName("AdmissibilityPackagingType")
    @Expose
    private Integer admissibilityPackagingType;
    @SerializedName("SenderTINType")
    @Expose
    private String senderTINType;
    @SerializedName("SenderTINNumber")
    @Expose
    private String senderTINNumber;
    @SerializedName("RecipientTINNumber")
    @Expose
    private String recipientTINNumber;
    @SerializedName("AESTransactionNumber")
    @Expose
    private String aESTransactionNumber;
    @SerializedName("PartiesToTrans")
    @Expose
    private String partiesToTrans;
    @SerializedName("ExportInformationCode")
    @Expose
    private Integer exportInformationCode;
    @SerializedName("ExportLicenceExpDate")
    @Expose
    private String exportLicenceExpDate;
    @SerializedName("BrokerageId")
    @Expose
    private String brokerageId;
    @SerializedName("WorldEaseFlag")
    @Expose
    private Boolean worldEaseFlag;
    @SerializedName("WorldEaseCounter")
    @Expose
    private Integer worldEaseCounter;
    @SerializedName("GCCNNumber")
    @Expose
    private String gCCNNumber;
    @SerializedName("WorldEaseTrackingNumber")
    @Expose
    private String worldEaseTrackingNumber;
    @SerializedName("WorldEasePortNumber")
    @Expose
    private String worldEasePortNumber;
    @SerializedName("WorldEasePortZip")
    @Expose
    private String worldEasePortZip;
    @SerializedName("WorldEasePortName")
    @Expose
    private String worldEasePortName;
    @SerializedName("WorldEasePortCountry")
    @Expose
    private String worldEasePortCountry;
    @SerializedName("WorldEasePackageCount")
    @Expose
    private Integer worldEasePackageCount;
    @SerializedName("FTRExemtionCode")
    @Expose
    private String fTRExemtionCode;
    @SerializedName("ShippingFormContent1")
    @Expose
    private String shippingFormContent1;
    @SerializedName("ShippingFormContent2")
    @Expose
    private String shippingFormContent2;
    @SerializedName("ShippingFormContent3")
    @Expose
    private String shippingFormContent3;
    @SerializedName("ShippingFormContent4")
    @Expose
    private String shippingFormContent4;
    @SerializedName("ShippingFormContent5")
    @Expose
    private String shippingFormContent5;
    @SerializedName("ShippingFormContent6")
    @Expose
    private String shippingFormContent6;
    @SerializedName("FormRequested")
    @Expose
    private String formRequested;
    @SerializedName("FormGenerationType")
    @Expose
    private Integer formGenerationType;
    @SerializedName("NaftaBlanketPeriod_BeginDate")
    @Expose
    private String naftaBlanketPeriodBeginDate;
    @SerializedName("NaftaBlanketPeriod_EndDate")
    @Expose
    private String naftaBlanketPeriodEndDate;
    @SerializedName("NaftaProducerTaxId")
    @Expose
    private String naftaProducerTaxId;
    @SerializedName("NaftaProducerDeterminationType")
    @Expose
    private Integer naftaProducerDeterminationType;
    @SerializedName("ExportDate")
    @Expose
    private String exportDate;
    @SerializedName("ExportingCarrier")
    @Expose
    private String exportingCarrier;
    @SerializedName("InbondCode")
    @Expose
    private Integer inbondCode;
    @SerializedName("EntryNumber")
    @Expose
    private String entryNumber;
    @SerializedName("PointOfOrigin")
    @Expose
    private String pointOfOrigin;
    @SerializedName("ModeOfTransport")
    @Expose
    private String modeOfTransport;
    @SerializedName("PortOfExport")
    @Expose
    private String portOfExport;
    @SerializedName("PortOfUnloading")
    @Expose
    private String portOfUnloading;
    @SerializedName("LoadingPier")
    @Expose
    private String loadingPier;
    @SerializedName("RoutedExportTransactionIndicator")
    @Expose
    private Boolean routedExportTransactionIndicator;
    @SerializedName("ContainerizedIndicator")
    @Expose
    private Boolean containerizedIndicator;
    @SerializedName("ExportType")
    @Expose
    private Integer exportType;
    @SerializedName("ForwarderTaxId")
    @Expose
    private String forwarderTaxId;
    @SerializedName("ExceptionCode")
    @Expose
    private Integer exceptionCode;
    @SerializedName("SoldToOption")
    @Expose
    private Integer soldToOption;
    @SerializedName("TradeDirectProductType")
    @Expose
    private Integer tradeDirectProductType;
    @SerializedName("ImporterAccountNumber")
    @Expose
    private String importerAccountNumber;
    @SerializedName("CustomFormType")
    @Expose
    private Integer customFormType;
    @SerializedName("ImportControlFlag")
    @Expose
    private Boolean importControlFlag;
    @SerializedName("CommercialInvoiceRemovalFlag")
    @Expose
    private Boolean commercialInvoiceRemovalFlag;
    @SerializedName("CommercialInvoiceFreightChargeAmount")
    @Expose
    private Integer commercialInvoiceFreightChargeAmount;
    @SerializedName("IssuingAgencyCode")
    @Expose
    private String issuingAgencyCode;
    @SerializedName("LicencePlateNumber")
    @Expose
    private String licencePlateNumber;
    @SerializedName("UltimateConsigneeAccountNumber")
    @Expose
    private String ultimateConsigneeAccountNumber;
    @SerializedName("UltimateConsigneeTaxId")
    @Expose
    private String ultimateConsigneeTaxId;
    @SerializedName("SccAccountNumber")
    @Expose
    private String sccAccountNumber;
    @SerializedName("DutyAccountNumber")
    @Expose
    private String dutyAccountNumber;
    @SerializedName("DutyBillType")
    @Expose
    private Integer dutyBillType;
    @SerializedName("DutyServiceType")
    @Expose
    private Integer dutyServiceType;
    @SerializedName("ImporterVATNumber")
    @Expose
    private String importerVATNumber;
    @SerializedName("RecipientCustomIdType")
    @Expose
    private Integer recipientCustomIdType;
    @SerializedName("CommercialImporterFlag")
    @Expose
    private String commercialImporterFlag;
    @SerializedName("TotalMonthlyImportedWeight")
    @Expose
    private String totalMonthlyImportedWeight;
    @SerializedName("TotalMonthlyImportedValue")
    @Expose
    private String totalMonthlyImportedValue;
    @SerializedName("AuthorizedReturnsNumber")
    @Expose
    private String authorizedReturnsNumber;
    @SerializedName("Documents")
    @Expose
    private List<String> documents = null;
    @SerializedName("ErrorCode")
    @Expose
    private String errorCode;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("DriverInstructions")
    @Expose
    private String driverInstructions;
    @SerializedName("BaseRate")
    @Expose
    private Integer baseRate;
    @SerializedName("TotalRate")
    @Expose
    private Integer totalRate;
    @SerializedName("ManifestGroupName")
    @Expose
    private String manifestGroupName;
    @SerializedName("ShipmentID")
    @Expose
    private String shipmentID;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getConsignmentID() {
        return consignmentID;
    }

    public void setConsignmentID(String consignmentID) {
        this.consignmentID = consignmentID;
    }

    public String getRouteGroupIDOrServiceCodeList() {
        return routeGroupIDOrServiceCodeList;
    }

    public void setRouteGroupIDOrServiceCodeList(String routeGroupIDOrServiceCodeList) {
        this.routeGroupIDOrServiceCodeList = routeGroupIDOrServiceCodeList;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAllocationFilter() {
        return allocationFilter;
    }

    public void setAllocationFilter(String allocationFilter) {
        this.allocationFilter = allocationFilter;
    }

    public Integer getLabelType() {
        return labelType;
    }

    public void setLabelType(Integer labelType) {
        this.labelType = labelType;
    }

    public Integer getLabelSize() {
        return labelSize;
    }

    public void setLabelSize(Integer labelSize) {
        this.labelSize = labelSize;
    }

    public Integer getLabelDPI() {
        return labelDPI;
    }

    public void setLabelDPI(Integer labelDPI) {
        this.labelDPI = labelDPI;
    }

    public List<BluePackagePackage> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<BluePackagePackage> packageList) {
        this.packageList = packageList;
    }

    public List<VASList> getVASList() {
        return vASList;
    }

    public void setVASList(List<VASList> vASList) {
        this.vASList = vASList;
    }

    public String getFreightShipment() {
        return freightShipment;
    }

    public void setFreightShipment(String freightShipment) {
        this.freightShipment = freightShipment;
    }

    public ShipFrom getShipFrom() {
        return shipFrom;
    }

    public void setShipFrom(ShipFrom shipFrom) {
        this.shipFrom = shipFrom;
    }

    public ShipTo getShipTo() {
        return shipTo;
    }

    public void setShipTo(ShipTo shipTo) {
        this.shipTo = shipTo;
    }

    public Return getReturn() {
        return _return;
    }

    public void setReturn(Return _return) {
        this._return = _return;
    }

    public ShipFromHub getShipFromHub() {
        return shipFromHub;
    }

    public void setShipFromHub(ShipFromHub shipFromHub) {
        this.shipFromHub = shipFromHub;
    }

    public USPSLocation getUSPSLocation() {
        return uSPSLocation;
    }

    public void setUSPSLocation(USPSLocation uSPSLocation) {
        this.uSPSLocation = uSPSLocation;
    }

    public HoldAtLocation getHoldAtLocation() {
        return holdAtLocation;
    }

    public void setHoldAtLocation(HoldAtLocation holdAtLocation) {
        this.holdAtLocation = holdAtLocation;
    }

    public CODRecipient getCODRecipient() {
        return cODRecipient;
    }

    public void setCODRecipient(CODRecipient cODRecipient) {
        this.cODRecipient = cODRecipient;
    }

    public NaftaProducer getNaftaProducer() {
        return naftaProducer;
    }

    public void setNaftaProducer(NaftaProducer naftaProducer) {
        this.naftaProducer = naftaProducer;
    }

    public Importer getImporter() {
        return importer;
    }

    public void setImporter(Importer importer) {
        this.importer = importer;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public UltConsignee getUltConsignee() {
        return ultConsignee;
    }

    public void setUltConsignee(UltConsignee ultConsignee) {
        this.ultConsignee = ultConsignee;
    }

    public Forwarder getForwarder() {
        return forwarder;
    }

    public void setForwarder(Forwarder forwarder) {
        this.forwarder = forwarder;
    }

    public IntermediateConsignee getIntermediateConsignee() {
        return intermediateConsignee;
    }

    public void setIntermediateConsignee(IntermediateConsignee intermediateConsignee) {
        this.intermediateConsignee = intermediateConsignee;
    }

    public DutyBillPayor getDutyBillPayor() {
        return dutyBillPayor;
    }

    public void setDutyBillPayor(DutyBillPayor dutyBillPayor) {
        this.dutyBillPayor = dutyBillPayor;
    }

    public BillPayor getBillPayor() {
        return billPayor;
    }

    public void setBillPayor(BillPayor billPayor) {
        this.billPayor = billPayor;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getBillAccountNumber() {
        return billAccountNumber;
    }

    public void setBillAccountNumber(String billAccountNumber) {
        this.billAccountNumber = billAccountNumber;
    }

    public String getBillAccountZipCode() {
        return billAccountZipCode;
    }

    public void setBillAccountZipCode(String billAccountZipCode) {
        this.billAccountZipCode = billAccountZipCode;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Integer getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(Integer creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardExpireDate() {
        return creditCardExpireDate;
    }

    public void setCreditCardExpireDate(String creditCardExpireDate) {
        this.creditCardExpireDate = creditCardExpireDate;
    }

    public String getCreditCardCode() {
        return creditCardCode;
    }

    public void setCreditCardCode(String creditCardCode) {
        this.creditCardCode = creditCardCode;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getDropOffType() {
        return dropOffType;
    }

    public void setDropOffType(Integer dropOffType) {
        this.dropOffType = dropOffType;
    }

    public String getSortCode1() {
        return sortCode1;
    }

    public void setSortCode1(String sortCode1) {
        this.sortCode1 = sortCode1;
    }

    public String getSortCode2() {
        return sortCode2;
    }

    public void setSortCode2(String sortCode2) {
        this.sortCode2 = sortCode2;
    }

    public String getSortCode3() {
        return sortCode3;
    }

    public void setSortCode3(String sortCode3) {
        this.sortCode3 = sortCode3;
    }

    public String getSortCode4() {
        return sortCode4;
    }

    public void setSortCode4(String sortCode4) {
        this.sortCode4 = sortCode4;
    }

    public String getSortCode5() {
        return sortCode5;
    }

    public void setSortCode5(String sortCode5) {
        this.sortCode5 = sortCode5;
    }

    public String getSortCode6() {
        return sortCode6;
    }

    public void setSortCode6(String sortCode6) {
        this.sortCode6 = sortCode6;
    }

    public Boolean getCertifiedMailFlag() {
        return certifiedMailFlag;
    }

    public void setCertifiedMailFlag(Boolean certifiedMailFlag) {
        this.certifiedMailFlag = certifiedMailFlag;
    }

    public Boolean getReturnsPackCollectFlag() {
        return returnsPackCollectFlag;
    }

    public void setReturnsPackCollectFlag(Boolean returnsPackCollectFlag) {
        this.returnsPackCollectFlag = returnsPackCollectFlag;
    }

    public Boolean getNDCPresortDiscountFlag() {
        return nDCPresortDiscountFlag;
    }

    public void setNDCPresortDiscountFlag(Boolean nDCPresortDiscountFlag) {
        this.nDCPresortDiscountFlag = nDCPresortDiscountFlag;
    }

    public Boolean getODNCPresortDiscountFlag() {
        return oDNCPresortDiscountFlag;
    }

    public void setODNCPresortDiscountFlag(Boolean oDNCPresortDiscountFlag) {
        this.oDNCPresortDiscountFlag = oDNCPresortDiscountFlag;
    }

    public Integer getCODReferenceIndicatorType() {
        return cODReferenceIndicatorType;
    }

    public void setCODReferenceIndicatorType(Integer cODReferenceIndicatorType) {
        this.cODReferenceIndicatorType = cODReferenceIndicatorType;
    }

    public String getCODRecipientAccountNumber() {
        return cODRecipientAccountNumber;
    }

    public void setCODRecipientAccountNumber(String cODRecipientAccountNumber) {
        this.cODRecipientAccountNumber = cODRecipientAccountNumber;
    }

    public Integer getCODAddlChargeType() {
        return cODAddlChargeType;
    }

    public void setCODAddlChargeType(Integer cODAddlChargeType) {
        this.cODAddlChargeType = cODAddlChargeType;
    }

    public Integer getCODAmt() {
        return cODAmt;
    }

    public void setCODAmt(Integer cODAmt) {
        this.cODAmt = cODAmt;
    }

    public Integer getCODPayType() {
        return cODPayType;
    }

    public void setCODPayType(Integer cODPayType) {
        this.cODPayType = cODPayType;
    }

    public String getDimensionUnit() {
        return dimensionUnit;
    }

    public void setDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getTrailerNumber() {
        return trailerNumber;
    }

    public void setTrailerNumber(String trailerNumber) {
        this.trailerNumber = trailerNumber;
    }

    public String getDropZip() {
        return dropZip;
    }

    public void setDropZip(String dropZip) {
        this.dropZip = dropZip;
    }

    public Boolean getACSFlag() {
        return aCSFlag;
    }

    public void setACSFlag(Boolean aCSFlag) {
        this.aCSFlag = aCSFlag;
    }

    public Boolean getReturnLabelRequiredFlag() {
        return returnLabelRequiredFlag;
    }

    public void setReturnLabelRequiredFlag(Boolean returnLabelRequiredFlag) {
        this.returnLabelRequiredFlag = returnLabelRequiredFlag;
    }

    public String getMultiPieceShipmentTrackingNumber() {
        return multiPieceShipmentTrackingNumber;
    }

    public void setMultiPieceShipmentTrackingNumber(String multiPieceShipmentTrackingNumber) {
        this.multiPieceShipmentTrackingNumber = multiPieceShipmentTrackingNumber;
    }

    public Integer getMultiPieceShipmentTotalCount() {
        return multiPieceShipmentTotalCount;
    }

    public void setMultiPieceShipmentTotalCount(Integer multiPieceShipmentTotalCount) {
        this.multiPieceShipmentTotalCount = multiPieceShipmentTotalCount;
    }

    public Integer getMultiPieceShipmentPieceCounter() {
        return multiPieceShipmentPieceCounter;
    }

    public void setMultiPieceShipmentPieceCounter(Integer multiPieceShipmentPieceCounter) {
        this.multiPieceShipmentPieceCounter = multiPieceShipmentPieceCounter;
    }

    public String getInboundLoadId() {
        return inboundLoadId;
    }

    public void setInboundLoadId(String inboundLoadId) {
        this.inboundLoadId = inboundLoadId;
    }

    public String getInboundLoadNumber() {
        return inboundLoadNumber;
    }

    public void setInboundLoadNumber(String inboundLoadNumber) {
        this.inboundLoadNumber = inboundLoadNumber;
    }

    public String getOutboundLoadId() {
        return outboundLoadId;
    }

    public void setOutboundLoadId(String outboundLoadId) {
        this.outboundLoadId = outboundLoadId;
    }

    public String getOutboundLoadNumber() {
        return outboundLoadNumber;
    }

    public void setOutboundLoadNumber(String outboundLoadNumber) {
        this.outboundLoadNumber = outboundLoadNumber;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Boolean getShipperReleaseFlag() {
        return shipperReleaseFlag;
    }

    public void setShipperReleaseFlag(Boolean shipperReleaseFlag) {
        this.shipperReleaseFlag = shipperReleaseFlag;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public Boolean getDeclarationStatementFlag() {
        return declarationStatementFlag;
    }

    public void setDeclarationStatementFlag(Boolean declarationStatementFlag) {
        this.declarationStatementFlag = declarationStatementFlag;
    }

    public Boolean getRoutedTransactionIndFlag() {
        return routedTransactionIndFlag;
    }

    public void setRoutedTransactionIndFlag(Boolean routedTransactionIndFlag) {
        this.routedTransactionIndFlag = routedTransactionIndFlag;
    }

    public Boolean getUseAddlCommentsFlag() {
        return useAddlCommentsFlag;
    }

    public void setUseAddlCommentsFlag(Boolean useAddlCommentsFlag) {
        this.useAddlCommentsFlag = useAddlCommentsFlag;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getImageRotation() {
        return imageRotation;
    }

    public void setImageRotation(String imageRotation) {
        this.imageRotation = imageRotation;
    }

    public Boolean getOverrideShipToAddressValidationFlag() {
        return overrideShipToAddressValidationFlag;
    }

    public void setOverrideShipToAddressValidationFlag(Boolean overrideShipToAddressValidationFlag) {
        this.overrideShipToAddressValidationFlag = overrideShipToAddressValidationFlag;
    }

    public Boolean getSupplyUsageFlag() {
        return supplyUsageFlag;
    }

    public void setSupplyUsageFlag(Boolean supplyUsageFlag) {
        this.supplyUsageFlag = supplyUsageFlag;
    }

    public String getSignatureReleaseNumber() {
        return signatureReleaseNumber;
    }

    public void setSignatureReleaseNumber(String signatureReleaseNumber) {
        this.signatureReleaseNumber = signatureReleaseNumber;
    }

    public Boolean getPackageListEnclosedFlag() {
        return packageListEnclosedFlag;
    }

    public void setPackageListEnclosedFlag(Boolean packageListEnclosedFlag) {
        this.packageListEnclosedFlag = packageListEnclosedFlag;
    }

    public Integer getRegulatoryControlType() {
        return regulatoryControlType;
    }

    public void setRegulatoryControlType(Integer regulatoryControlType) {
        this.regulatoryControlType = regulatoryControlType;
    }

    public Boolean getIsUPSHundredWeightFlag() {
        return isUPSHundredWeightFlag;
    }

    public void setIsUPSHundredWeightFlag(Boolean isUPSHundredWeightFlag) {
        this.isUPSHundredWeightFlag = isUPSHundredWeightFlag;
    }

    public String getAddlShipmentReference1() {
        return addlShipmentReference1;
    }

    public void setAddlShipmentReference1(String addlShipmentReference1) {
        this.addlShipmentReference1 = addlShipmentReference1;
    }

    public String getAddlShipmentReference2() {
        return addlShipmentReference2;
    }

    public void setAddlShipmentReference2(String addlShipmentReference2) {
        this.addlShipmentReference2 = addlShipmentReference2;
    }

    public String getAddlShipmentReference3() {
        return addlShipmentReference3;
    }

    public void setAddlShipmentReference3(String addlShipmentReference3) {
        this.addlShipmentReference3 = addlShipmentReference3;
    }

    public String getAddlShipmentReference4() {
        return addlShipmentReference4;
    }

    public void setAddlShipmentReference4(String addlShipmentReference4) {
        this.addlShipmentReference4 = addlShipmentReference4;
    }

    public String getAddlShipmentReference5() {
        return addlShipmentReference5;
    }

    public void setAddlShipmentReference5(String addlShipmentReference5) {
        this.addlShipmentReference5 = addlShipmentReference5;
    }

    public String getAddlShipmentReference6() {
        return addlShipmentReference6;
    }

    public void setAddlShipmentReference6(String addlShipmentReference6) {
        this.addlShipmentReference6 = addlShipmentReference6;
    }

    public String getAddlShipmentReference7() {
        return addlShipmentReference7;
    }

    public void setAddlShipmentReference7(String addlShipmentReference7) {
        this.addlShipmentReference7 = addlShipmentReference7;
    }

    public String getAddlShipmentReference8() {
        return addlShipmentReference8;
    }

    public void setAddlShipmentReference8(String addlShipmentReference8) {
        this.addlShipmentReference8 = addlShipmentReference8;
    }

    public Boolean getDeliveryConfirmationFlag() {
        return deliveryConfirmationFlag;
    }

    public void setDeliveryConfirmationFlag(Boolean deliveryConfirmationFlag) {
        this.deliveryConfirmationFlag = deliveryConfirmationFlag;
    }

    public Boolean getSignatureConfirmationFlag() {
        return signatureConfirmationFlag;
    }

    public void setSignatureConfirmationFlag(Boolean signatureConfirmationFlag) {
        this.signatureConfirmationFlag = signatureConfirmationFlag;
    }

    public Integer getSignatureConfirmationType() {
        return signatureConfirmationType;
    }

    public void setSignatureConfirmationType(Integer signatureConfirmationType) {
        this.signatureConfirmationType = signatureConfirmationType;
    }

    public String getUSPSRateIndicator() {
        return uSPSRateIndicator;
    }

    public void setUSPSRateIndicator(String uSPSRateIndicator) {
        this.uSPSRateIndicator = uSPSRateIndicator;
    }

    public Integer getDryIceWeight() {
        return dryIceWeight;
    }

    public void setDryIceWeight(Integer dryIceWeight) {
        this.dryIceWeight = dryIceWeight;
    }

    public Boolean getSignatureReleaseFlag() {
        return signatureReleaseFlag;
    }

    public void setSignatureReleaseFlag(Boolean signatureReleaseFlag) {
        this.signatureReleaseFlag = signatureReleaseFlag;
    }

    public Integer getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Integer insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Integer getInsurancePayType() {
        return insurancePayType;
    }

    public void setInsurancePayType(Integer insurancePayType) {
        this.insurancePayType = insurancePayType;
    }

    public Integer getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(Integer declaredValue) {
        this.declaredValue = declaredValue;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Integer getEndorsementType() {
        return endorsementType;
    }

    public void setEndorsementType(Integer endorsementType) {
        this.endorsementType = endorsementType;
    }

    public Boolean getFragileFlag() {
        return fragileFlag;
    }

    public void setFragileFlag(Boolean fragileFlag) {
        this.fragileFlag = fragileFlag;
    }

    public Integer getUSPSSortLevel() {
        return uSPSSortLevel;
    }

    public void setUSPSSortLevel(Integer uSPSSortLevel) {
        this.uSPSSortLevel = uSPSSortLevel;
    }

    public Integer getUSPSODEnclosedMailClass() {
        return uSPSODEnclosedMailClass;
    }

    public void setUSPSODEnclosedMailClass(Integer uSPSODEnclosedMailClass) {
        this.uSPSODEnclosedMailClass = uSPSODEnclosedMailClass;
    }

    public Integer getProcessCategory() {
        return processCategory;
    }

    public void setProcessCategory(Integer processCategory) {
        this.processCategory = processCategory;
    }

    public String getOriginallyCapturedPackageId() {
        return originallyCapturedPackageId;
    }

    public void setOriginallyCapturedPackageId(String originallyCapturedPackageId) {
        this.originallyCapturedPackageId = originallyCapturedPackageId;
    }

    public String getHarmonizedcode() {
        return harmonizedcode;
    }

    public void setHarmonizedcode(String harmonizedcode) {
        this.harmonizedcode = harmonizedcode;
    }

    public Integer getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(Integer salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public Integer getDutyAmount() {
        return dutyAmount;
    }

    public void setDutyAmount(Integer dutyAmount) {
        this.dutyAmount = dutyAmount;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public Boolean getAdditionalHandlingFlag() {
        return additionalHandlingFlag;
    }

    public void setAdditionalHandlingFlag(Boolean additionalHandlingFlag) {
        this.additionalHandlingFlag = additionalHandlingFlag;
    }

    public Integer getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Integer notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationValue() {
        return notificationValue;
    }

    public void setNotificationValue(String notificationValue) {
        this.notificationValue = notificationValue;
    }

    public Integer getCN22DescriptionType() {
        return cN22DescriptionType;
    }

    public void setCN22DescriptionType(Integer cN22DescriptionType) {
        this.cN22DescriptionType = cN22DescriptionType;
    }

    public String getShipperECCN() {
        return shipperECCN;
    }

    public void setShipperECCN(String shipperECCN) {
        this.shipperECCN = shipperECCN;
    }

    public String getShipperXTN() {
        return shipperXTN;
    }

    public void setShipperXTN(String shipperXTN) {
        this.shipperXTN = shipperXTN;
    }

    public String getShipperFTSR() {
        return shipperFTSR;
    }

    public void setShipperFTSR(String shipperFTSR) {
        this.shipperFTSR = shipperFTSR;
    }

    public String getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(String incoterms) {
        this.incoterms = incoterms;
    }

    public String getExportLicense() {
        return exportLicense;
    }

    public void setExportLicense(String exportLicense) {
        this.exportLicense = exportLicense;
    }

    public String getImportLicense() {
        return importLicense;
    }

    public void setImportLicense(String importLicense) {
        this.importLicense = importLicense;
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    public String getConsigneeEIN() {
        return consigneeEIN;
    }

    public void setConsigneeEIN(String consigneeEIN) {
        this.consigneeEIN = consigneeEIN;
    }

    public String getConsigneeVAT() {
        return consigneeVAT;
    }

    public void setConsigneeVAT(String consigneeVAT) {
        this.consigneeVAT = consigneeVAT;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public String getSignatureTitle() {
        return signatureTitle;
    }

    public void setSignatureTitle(String signatureTitle) {
        this.signatureTitle = signatureTitle;
    }

    public Integer getExportReasonCode() {
        return exportReasonCode;
    }

    public void setExportReasonCode(Integer exportReasonCode) {
        this.exportReasonCode = exportReasonCode;
    }

    public String getShipperEin() {
        return shipperEin;
    }

    public void setShipperEin(String shipperEin) {
        this.shipperEin = shipperEin;
    }

    public Integer getSEDFilingOption() {
        return sEDFilingOption;
    }

    public void setSEDFilingOption(Integer sEDFilingOption) {
        this.sEDFilingOption = sEDFilingOption;
    }

    public String getSignaturePhone() {
        return signaturePhone;
    }

    public void setSignaturePhone(String signaturePhone) {
        this.signaturePhone = signaturePhone;
    }

    public String getSignatureEmailAddress() {
        return signatureEmailAddress;
    }

    public void setSignatureEmailAddress(String signatureEmailAddress) {
        this.signatureEmailAddress = signatureEmailAddress;
    }

    public Boolean getIsDepartmentOfStateShipment() {
        return isDepartmentOfStateShipment;
    }

    public void setIsDepartmentOfStateShipment(Boolean isDepartmentOfStateShipment) {
        this.isDepartmentOfStateShipment = isDepartmentOfStateShipment;
    }

    public Boolean getIsDepartmentOfStateExempt() {
        return isDepartmentOfStateExempt;
    }

    public void setIsDepartmentOfStateExempt(Boolean isDepartmentOfStateExempt) {
        this.isDepartmentOfStateExempt = isDepartmentOfStateExempt;
    }

    public String getDOS22CFRExemptNumber() {
        return dOS22CFRExemptNumber;
    }

    public void setDOS22CFRExemptNumber(String dOS22CFRExemptNumber) {
        this.dOS22CFRExemptNumber = dOS22CFRExemptNumber;
    }

    public String getCommercialInvoiceType() {
        return commercialInvoiceType;
    }

    public void setCommercialInvoiceType(String commercialInvoiceType) {
        this.commercialInvoiceType = commercialInvoiceType;
    }

    public String getCommercialInvoiceNumber() {
        return commercialInvoiceNumber;
    }

    public void setCommercialInvoiceNumber(String commercialInvoiceNumber) {
        this.commercialInvoiceNumber = commercialInvoiceNumber;
    }

    public Integer getCommercialInvoiceOtherCharges() {
        return commercialInvoiceOtherCharges;
    }

    public void setCommercialInvoiceOtherCharges(Integer commercialInvoiceOtherCharges) {
        this.commercialInvoiceOtherCharges = commercialInvoiceOtherCharges;
    }

    public Boolean getUse3rdPartyBrokerFlag() {
        return use3rdPartyBrokerFlag;
    }

    public void setUse3rdPartyBrokerFlag(Boolean use3rdPartyBrokerFlag) {
        this.use3rdPartyBrokerFlag = use3rdPartyBrokerFlag;
    }

    public Boolean getBrokerShipAlertFlag() {
        return brokerShipAlertFlag;
    }

    public void setBrokerShipAlertFlag(Boolean brokerShipAlertFlag) {
        this.brokerShipAlertFlag = brokerShipAlertFlag;
    }

    public Boolean getBrokerDeliveryNotificationFlag() {
        return brokerDeliveryNotificationFlag;
    }

    public void setBrokerDeliveryNotificationFlag(Boolean brokerDeliveryNotificationFlag) {
        this.brokerDeliveryNotificationFlag = brokerDeliveryNotificationFlag;
    }

    public Boolean getBrokerExceptionNotificationFlag() {
        return brokerExceptionNotificationFlag;
    }

    public void setBrokerExceptionNotificationFlag(Boolean brokerExceptionNotificationFlag) {
        this.brokerExceptionNotificationFlag = brokerExceptionNotificationFlag;
    }

    public String getBrokerEmailFormat() {
        return brokerEmailFormat;
    }

    public void setBrokerEmailFormat(String brokerEmailFormat) {
        this.brokerEmailFormat = brokerEmailFormat;
    }

    public String getBrokerAccountNumber() {
        return brokerAccountNumber;
    }

    public void setBrokerAccountNumber(String brokerAccountNumber) {
        this.brokerAccountNumber = brokerAccountNumber;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public String getDutiesTaxAccountNumber() {
        return dutiesTaxAccountNumber;
    }

    public void setDutiesTaxAccountNumber(String dutiesTaxAccountNumber) {
        this.dutiesTaxAccountNumber = dutiesTaxAccountNumber;
    }

    public Integer getAdmissibilityPackagingType() {
        return admissibilityPackagingType;
    }

    public void setAdmissibilityPackagingType(Integer admissibilityPackagingType) {
        this.admissibilityPackagingType = admissibilityPackagingType;
    }

    public String getSenderTINType() {
        return senderTINType;
    }

    public void setSenderTINType(String senderTINType) {
        this.senderTINType = senderTINType;
    }

    public String getSenderTINNumber() {
        return senderTINNumber;
    }

    public void setSenderTINNumber(String senderTINNumber) {
        this.senderTINNumber = senderTINNumber;
    }

    public String getRecipientTINNumber() {
        return recipientTINNumber;
    }

    public void setRecipientTINNumber(String recipientTINNumber) {
        this.recipientTINNumber = recipientTINNumber;
    }

    public String getAESTransactionNumber() {
        return aESTransactionNumber;
    }

    public void setAESTransactionNumber(String aESTransactionNumber) {
        this.aESTransactionNumber = aESTransactionNumber;
    }

    public String getPartiesToTrans() {
        return partiesToTrans;
    }

    public void setPartiesToTrans(String partiesToTrans) {
        this.partiesToTrans = partiesToTrans;
    }

    public Integer getExportInformationCode() {
        return exportInformationCode;
    }

    public void setExportInformationCode(Integer exportInformationCode) {
        this.exportInformationCode = exportInformationCode;
    }

    public String getExportLicenceExpDate() {
        return exportLicenceExpDate;
    }

    public void setExportLicenceExpDate(String exportLicenceExpDate) {
        this.exportLicenceExpDate = exportLicenceExpDate;
    }

    public String getBrokerageId() {
        return brokerageId;
    }

    public void setBrokerageId(String brokerageId) {
        this.brokerageId = brokerageId;
    }

    public Boolean getWorldEaseFlag() {
        return worldEaseFlag;
    }

    public void setWorldEaseFlag(Boolean worldEaseFlag) {
        this.worldEaseFlag = worldEaseFlag;
    }

    public Integer getWorldEaseCounter() {
        return worldEaseCounter;
    }

    public void setWorldEaseCounter(Integer worldEaseCounter) {
        this.worldEaseCounter = worldEaseCounter;
    }

    public String getGCCNNumber() {
        return gCCNNumber;
    }

    public void setGCCNNumber(String gCCNNumber) {
        this.gCCNNumber = gCCNNumber;
    }

    public String getWorldEaseTrackingNumber() {
        return worldEaseTrackingNumber;
    }

    public void setWorldEaseTrackingNumber(String worldEaseTrackingNumber) {
        this.worldEaseTrackingNumber = worldEaseTrackingNumber;
    }

    public String getWorldEasePortNumber() {
        return worldEasePortNumber;
    }

    public void setWorldEasePortNumber(String worldEasePortNumber) {
        this.worldEasePortNumber = worldEasePortNumber;
    }

    public String getWorldEasePortZip() {
        return worldEasePortZip;
    }

    public void setWorldEasePortZip(String worldEasePortZip) {
        this.worldEasePortZip = worldEasePortZip;
    }

    public String getWorldEasePortName() {
        return worldEasePortName;
    }

    public void setWorldEasePortName(String worldEasePortName) {
        this.worldEasePortName = worldEasePortName;
    }

    public String getWorldEasePortCountry() {
        return worldEasePortCountry;
    }

    public void setWorldEasePortCountry(String worldEasePortCountry) {
        this.worldEasePortCountry = worldEasePortCountry;
    }

    public Integer getWorldEasePackageCount() {
        return worldEasePackageCount;
    }

    public void setWorldEasePackageCount(Integer worldEasePackageCount) {
        this.worldEasePackageCount = worldEasePackageCount;
    }

    public String getFTRExemtionCode() {
        return fTRExemtionCode;
    }

    public void setFTRExemtionCode(String fTRExemtionCode) {
        this.fTRExemtionCode = fTRExemtionCode;
    }

    public String getShippingFormContent1() {
        return shippingFormContent1;
    }

    public void setShippingFormContent1(String shippingFormContent1) {
        this.shippingFormContent1 = shippingFormContent1;
    }

    public String getShippingFormContent2() {
        return shippingFormContent2;
    }

    public void setShippingFormContent2(String shippingFormContent2) {
        this.shippingFormContent2 = shippingFormContent2;
    }

    public String getShippingFormContent3() {
        return shippingFormContent3;
    }

    public void setShippingFormContent3(String shippingFormContent3) {
        this.shippingFormContent3 = shippingFormContent3;
    }

    public String getShippingFormContent4() {
        return shippingFormContent4;
    }

    public void setShippingFormContent4(String shippingFormContent4) {
        this.shippingFormContent4 = shippingFormContent4;
    }

    public String getShippingFormContent5() {
        return shippingFormContent5;
    }

    public void setShippingFormContent5(String shippingFormContent5) {
        this.shippingFormContent5 = shippingFormContent5;
    }

    public String getShippingFormContent6() {
        return shippingFormContent6;
    }

    public void setShippingFormContent6(String shippingFormContent6) {
        this.shippingFormContent6 = shippingFormContent6;
    }

    public String getFormRequested() {
        return formRequested;
    }

    public void setFormRequested(String formRequested) {
        this.formRequested = formRequested;
    }

    public Integer getFormGenerationType() {
        return formGenerationType;
    }

    public void setFormGenerationType(Integer formGenerationType) {
        this.formGenerationType = formGenerationType;
    }

    public String getNaftaBlanketPeriodBeginDate() {
        return naftaBlanketPeriodBeginDate;
    }

    public void setNaftaBlanketPeriodBeginDate(String naftaBlanketPeriodBeginDate) {
        this.naftaBlanketPeriodBeginDate = naftaBlanketPeriodBeginDate;
    }

    public String getNaftaBlanketPeriodEndDate() {
        return naftaBlanketPeriodEndDate;
    }

    public void setNaftaBlanketPeriodEndDate(String naftaBlanketPeriodEndDate) {
        this.naftaBlanketPeriodEndDate = naftaBlanketPeriodEndDate;
    }

    public String getNaftaProducerTaxId() {
        return naftaProducerTaxId;
    }

    public void setNaftaProducerTaxId(String naftaProducerTaxId) {
        this.naftaProducerTaxId = naftaProducerTaxId;
    }

    public Integer getNaftaProducerDeterminationType() {
        return naftaProducerDeterminationType;
    }

    public void setNaftaProducerDeterminationType(Integer naftaProducerDeterminationType) {
        this.naftaProducerDeterminationType = naftaProducerDeterminationType;
    }

    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }

    public String getExportingCarrier() {
        return exportingCarrier;
    }

    public void setExportingCarrier(String exportingCarrier) {
        this.exportingCarrier = exportingCarrier;
    }

    public Integer getInbondCode() {
        return inbondCode;
    }

    public void setInbondCode(Integer inbondCode) {
        this.inbondCode = inbondCode;
    }

    public String getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(String entryNumber) {
        this.entryNumber = entryNumber;
    }

    public String getPointOfOrigin() {
        return pointOfOrigin;
    }

    public void setPointOfOrigin(String pointOfOrigin) {
        this.pointOfOrigin = pointOfOrigin;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public String getPortOfExport() {
        return portOfExport;
    }

    public void setPortOfExport(String portOfExport) {
        this.portOfExport = portOfExport;
    }

    public String getPortOfUnloading() {
        return portOfUnloading;
    }

    public void setPortOfUnloading(String portOfUnloading) {
        this.portOfUnloading = portOfUnloading;
    }

    public String getLoadingPier() {
        return loadingPier;
    }

    public void setLoadingPier(String loadingPier) {
        this.loadingPier = loadingPier;
    }

    public Boolean getRoutedExportTransactionIndicator() {
        return routedExportTransactionIndicator;
    }

    public void setRoutedExportTransactionIndicator(Boolean routedExportTransactionIndicator) {
        this.routedExportTransactionIndicator = routedExportTransactionIndicator;
    }

    public Boolean getContainerizedIndicator() {
        return containerizedIndicator;
    }

    public void setContainerizedIndicator(Boolean containerizedIndicator) {
        this.containerizedIndicator = containerizedIndicator;
    }

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    public String getForwarderTaxId() {
        return forwarderTaxId;
    }

    public void setForwarderTaxId(String forwarderTaxId) {
        this.forwarderTaxId = forwarderTaxId;
    }

    public Integer getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(Integer exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public Integer getSoldToOption() {
        return soldToOption;
    }

    public void setSoldToOption(Integer soldToOption) {
        this.soldToOption = soldToOption;
    }

    public Integer getTradeDirectProductType() {
        return tradeDirectProductType;
    }

    public void setTradeDirectProductType(Integer tradeDirectProductType) {
        this.tradeDirectProductType = tradeDirectProductType;
    }

    public String getImporterAccountNumber() {
        return importerAccountNumber;
    }

    public void setImporterAccountNumber(String importerAccountNumber) {
        this.importerAccountNumber = importerAccountNumber;
    }

    public Integer getCustomFormType() {
        return customFormType;
    }

    public void setCustomFormType(Integer customFormType) {
        this.customFormType = customFormType;
    }

    public Boolean getImportControlFlag() {
        return importControlFlag;
    }

    public void setImportControlFlag(Boolean importControlFlag) {
        this.importControlFlag = importControlFlag;
    }

    public Boolean getCommercialInvoiceRemovalFlag() {
        return commercialInvoiceRemovalFlag;
    }

    public void setCommercialInvoiceRemovalFlag(Boolean commercialInvoiceRemovalFlag) {
        this.commercialInvoiceRemovalFlag = commercialInvoiceRemovalFlag;
    }

    public Integer getCommercialInvoiceFreightChargeAmount() {
        return commercialInvoiceFreightChargeAmount;
    }

    public void setCommercialInvoiceFreightChargeAmount(Integer commercialInvoiceFreightChargeAmount) {
        this.commercialInvoiceFreightChargeAmount = commercialInvoiceFreightChargeAmount;
    }

    public String getIssuingAgencyCode() {
        return issuingAgencyCode;
    }

    public void setIssuingAgencyCode(String issuingAgencyCode) {
        this.issuingAgencyCode = issuingAgencyCode;
    }

    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public void setLicencePlateNumber(String licencePlateNumber) {
        this.licencePlateNumber = licencePlateNumber;
    }

    public String getUltimateConsigneeAccountNumber() {
        return ultimateConsigneeAccountNumber;
    }

    public void setUltimateConsigneeAccountNumber(String ultimateConsigneeAccountNumber) {
        this.ultimateConsigneeAccountNumber = ultimateConsigneeAccountNumber;
    }

    public String getUltimateConsigneeTaxId() {
        return ultimateConsigneeTaxId;
    }

    public void setUltimateConsigneeTaxId(String ultimateConsigneeTaxId) {
        this.ultimateConsigneeTaxId = ultimateConsigneeTaxId;
    }

    public String getSccAccountNumber() {
        return sccAccountNumber;
    }

    public void setSccAccountNumber(String sccAccountNumber) {
        this.sccAccountNumber = sccAccountNumber;
    }

    public String getDutyAccountNumber() {
        return dutyAccountNumber;
    }

    public void setDutyAccountNumber(String dutyAccountNumber) {
        this.dutyAccountNumber = dutyAccountNumber;
    }

    public Integer getDutyBillType() {
        return dutyBillType;
    }

    public void setDutyBillType(Integer dutyBillType) {
        this.dutyBillType = dutyBillType;
    }

    public Integer getDutyServiceType() {
        return dutyServiceType;
    }

    public void setDutyServiceType(Integer dutyServiceType) {
        this.dutyServiceType = dutyServiceType;
    }

    public String getImporterVATNumber() {
        return importerVATNumber;
    }

    public void setImporterVATNumber(String importerVATNumber) {
        this.importerVATNumber = importerVATNumber;
    }

    public Integer getRecipientCustomIdType() {
        return recipientCustomIdType;
    }

    public void setRecipientCustomIdType(Integer recipientCustomIdType) {
        this.recipientCustomIdType = recipientCustomIdType;
    }

    public String getCommercialImporterFlag() {
        return commercialImporterFlag;
    }

    public void setCommercialImporterFlag(String commercialImporterFlag) {
        this.commercialImporterFlag = commercialImporterFlag;
    }

    public String getTotalMonthlyImportedWeight() {
        return totalMonthlyImportedWeight;
    }

    public void setTotalMonthlyImportedWeight(String totalMonthlyImportedWeight) {
        this.totalMonthlyImportedWeight = totalMonthlyImportedWeight;
    }

    public String getTotalMonthlyImportedValue() {
        return totalMonthlyImportedValue;
    }

    public void setTotalMonthlyImportedValue(String totalMonthlyImportedValue) {
        this.totalMonthlyImportedValue = totalMonthlyImportedValue;
    }

    public String getAuthorizedReturnsNumber() {
        return authorizedReturnsNumber;
    }

    public void setAuthorizedReturnsNumber(String authorizedReturnsNumber) {
        this.authorizedReturnsNumber = authorizedReturnsNumber;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDriverInstructions() {
        return driverInstructions;
    }

    public void setDriverInstructions(String driverInstructions) {
        this.driverInstructions = driverInstructions;
    }

    public Integer getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Integer baseRate) {
        this.baseRate = baseRate;
    }

    public Integer getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Integer totalRate) {
        this.totalRate = totalRate;
    }

    public String getManifestGroupName() {
        return manifestGroupName;
    }

    public void setManifestGroupName(String manifestGroupName) {
        this.manifestGroupName = manifestGroupName;
    }

    public String getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(String shipmentID) {
        this.shipmentID = shipmentID;
    }

}

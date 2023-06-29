
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Elements that represent a package or container in a shipment.
 * 
 * <p>Java class for DataDictionary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataDictionary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;all>
 *         &lt;element name="additionalHandling" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="additionalHandlingFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="additionalHandlingType" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="additionalHardcopyDocumentation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="addressChangeNotification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="aesTransactionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="airFreightFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="alcoholContents" type="{urn:connectship-com:ampcore}AlcoholItemList" minOccurs="0"/>
 *         &lt;element name="appointmentDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="appointmentDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="apportionedBase" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="apportionedDiscount" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="apportionedSpecial" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="apportionedTotal" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="arriveDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="barCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barCodeBinary" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="barCode2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barCode2Binary" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="barCode3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barCode3Binary" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="base" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="billingFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="bolComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bolLegalStatement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="borderProcessingFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="brokerageMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="brokerageThirdPartyBilling" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="brokerageThirdPartyBillingAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brokerageThirdPartyBillingAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="bundleId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="bundleIdList" type="{urn:connectship-com:ampcore}IntegerList" minOccurs="0"/>
 *         &lt;element name="calltag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="calltagFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="calltagNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carbonNeutral" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="carbonNeutralFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="carrierInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carrierMonitoring" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="carrierMonitoringFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="carrierTenderMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="certifiedMail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="certifiedMailFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="certOfOriginMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="chainOfSignature" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="chainOfSignatureFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="codAlternateNumber" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="codAmount" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="codFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="codInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codMasterTrackingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="codNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codPaymentMethod" type="{urn:connectship-com:ampcore}enumList" minOccurs="0"/>
 *         &lt;element name="codPaymentMethodPostDatedCheckDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="codPayorAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codPayorInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codPendingFeePayorPercentage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codReturnAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="codReturnMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="codReturnTrackingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commercialInvoiceMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="commitmentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commodityClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commodityCondition" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="commodityContents" type="{urn:connectship-com:ampcore}CommodityList" minOccurs="0"/>
 *         &lt;element name="consignee" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="consigneeAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeBillingId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeCustomsId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeTaxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeTaxIdType" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="consigneeThirdPartyBilling" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="consigneeThirdPartyBillingAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consigneeThirdPartyBillingAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="consolidationCarrier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consolidationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consolidationFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="consolidationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="consolidationShipmentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consolidationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="containerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customsBroker" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="cycleCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="declaredValueAmount" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="declaredValueCustoms" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="declaredValueFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="deconsolidationCarrier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryExceptionNotification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="deliveryExceptionNotificationAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryExceptionNotificationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryExceptionNotificationEmail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="deliveryExceptionNotificationFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="deliveryExceptionNotificationSenderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryExceptionNotificationSubjectText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationEmail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationFile" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationSenderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationSubjectText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryNotificationVerbal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="deliveryWindowFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="deliverToDoor" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destinationAirportCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dimension" type="{urn:connectship-com:ampcore}Dimensions" minOccurs="0"/>
 *         &lt;element name="dimensionalWeightRated" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="directDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="directDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="discount" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="dispositionMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="distributionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentationConsignee" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="documentationFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="documentsOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dropoffAppointmentDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dropoffAppointmentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dropoffAppointmentTime" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *         &lt;element name="dryIceFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="dryIcePurpose" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="dryIceRegulationSet" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="dryIceWeight" type="{urn:connectship-com:ampcore}Weight" minOccurs="0"/>
 *         &lt;element name="dutyFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="earliestDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="earliestDeliveryTime" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *         &lt;element name="earlyDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="eveningDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="eveningDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="exchange" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="exchangeFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="exchangeMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="exporter" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="exporterAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exporterTaxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exportDeclarationStatement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exportInformationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exportReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extendedAreaFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="flats" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="forkliftDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="forkliftDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="forkliftPickup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="forkliftPickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="forwardingAgent" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="forwardingAgentTaxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fuelSurcharge" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="goodsInFreeCirculation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="groundsaver" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmat" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatAllPackedInOne" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatContents" type="{urn:connectship-com:ampcore}HazmatItemList" minOccurs="0"/>
 *         &lt;element name="hazmatFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="hazmatHandlingInformation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatOverpack" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatQValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="hazmatSignatoryAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="hazmatSignatoryTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="healthInsurance" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="healthInsuranceFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="helperDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="helperDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="helperPickup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="helperPickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="holdAtLocation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="holdAtLocationAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="holdAtLocationFacilityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="holdAtLocationFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="holidayDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="holidayDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="holidayPickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="hubCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hundredweightRated" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="importerOfRecord" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="importerOfRecordAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importerOfRecordTaxId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="insideDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="insideDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="insidePickup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="insidePickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="insuranceMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="invoiceDiscount" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="invoiceFreight" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="invoiceInsuranceFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="invoiceOtherFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="inTransitNotification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="inTransitNotificationAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inTransitNotificationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inTransitNotificationEmail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="inTransitNotificationSenderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inTransitNotificationSubjectText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isoUniqueIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="largePackage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="largePackageFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="latestDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="latestDeliveryTime" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *         &lt;element name="liftgateDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="liftgateDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="liftgatePickup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="liftgatePickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="locationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="markings" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxicode" type="{urn:connectship-com:ampcore}StringList" minOccurs="0"/>
 *         &lt;element name="miscReference1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="miscReference2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="miscReference3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="miscReference4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="miscReference5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mms" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="mmsPieceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msn" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="multiPieceFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="neutralDeliveryService" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="neutralDeliveryServiceFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="nofnSequence" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nofnSequenceBundle" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nofnTotal" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nofnTotalBundle" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nonhazardousLithiumBatteries" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nonmachinableMail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nonmachinableMailFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="nonrectangular" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nonstandardMail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nonstandardMailFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="notificationFailureAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notFlatMachinable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="offshoreFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="originatorTrackingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="oversize" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="oversizeFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="overDimensionFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="packageListId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="packaging" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="packagingDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="packagingTareWeight" type="{urn:connectship-com:ampcore}Weight" minOccurs="0"/>
 *         &lt;element name="palletFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="palletJackDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="palletJackDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="palletJackPickup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="palletJackPickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="parcelAirlift" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="parcelAirliftFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="parentContainer" type="{urn:connectship-com:ampcore}DataDictionary" minOccurs="0"/>
 *         &lt;element name="parentContainerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partiesRelated" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="perishable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="pieceCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="pieceCountFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="portOfEntry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationEmail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationLocale" type="{http://www.w3.org/2001/XMLSchema}language" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationPhone" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationSenderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationSms" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="preAlertNotificationSubjectText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priorDeliveryNotificationConsignee" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="priorDeliveryNotificationFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="proof" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="proofFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="proofNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proofRequireSignature" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="proofRequireSignatureAdult" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="proofRequireSignatureConsignee" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="proofRequireSignatureFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="proofReturnOfDocuments" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="proofSignatureWaiver" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="proofUseAlternateNumber" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ratedWeight" type="{urn:connectship-com:ampcore}Weight" minOccurs="0"/>
 *         &lt;element name="registeredMail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="registeredMailFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="remoteOriginFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="residentialDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="returnAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="returnAddressMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="returnDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="returnDeliveryAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnDeliveryAddressEmailLocale" type="{http://www.w3.org/2001/XMLSchema}language" minOccurs="0"/>
 *         &lt;element name="returnDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="returnDeliveryMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationAddress2" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationEmail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationFax" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationSenderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnDeliveryNotificationSubjectText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routedExportTransaction" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="routingCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routingCode2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routingCode3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routingCode4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routingCode5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sackLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sackZip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="saturdayDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="saturdayDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="saturdayPickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="security" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="securityFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="sedExemptionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sedMethod" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="service" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipdate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="shipper" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipperReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipNotificationAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="shipNotificationAddress2" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="shipNotificationAddressEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipNotificationAddressFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipNotificationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipNotificationEmail" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="shipNotificationFax" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="shipNotificationFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="shipNotificationSenderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipNotificationSubjectText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipNotificationVerbal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="signatureRelease" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="special" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="specialDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="specialDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="stairDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="stairDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="stairPickup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="stairPickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="subcategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sundayDelivery" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sundayDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="sundayPickupFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="suppressDc" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="suppressMms" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tax" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="temperatureControl" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="temperatureControlFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="terminalHandlingFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="terms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="termsOfSale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thirdPartyBilling" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="thirdPartyBillingAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thirdPartyBillingAddress" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="timeInTransit" type="{urn:connectship-com:ampcore}Commitment" minOccurs="0"/>
 *         &lt;element name="total" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="trackingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trackingNumber2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ultimateConsignee" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="ultimateDestinationCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unpack" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="unpackFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="urbanDeliveryFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="userData1" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData3" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData4" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData5" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="waybillBolNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weight" type="{urn:connectship-com:ampcore}Weight" minOccurs="0"/>
 *         &lt;element name="wharfageFee" type="{urn:connectship-com:ampcore}Money" minOccurs="0"/>
 *         &lt;element name="worldEaseCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="worldEaseFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="worldEaseId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="worldEaseSingleEuCountry" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="zone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}externalKey"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataDictionary", propOrder = {

})
public class DataDictionary {

    protected Boolean additionalHandling;
    protected Money additionalHandlingFee;
    protected String additionalHandlingType;
    protected Boolean additionalHardcopyDocumentation;
    protected Boolean addressChangeNotification;
    protected String aesTransactionNumber;
    protected Money airFreightFee;
    protected AlcoholItemList alcoholContents;
    protected Boolean appointmentDelivery;
    protected Money appointmentDeliveryFee;
    protected Money apportionedBase;
    protected Money apportionedDiscount;
    protected Money apportionedSpecial;
    protected Money apportionedTotal;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar arriveDate;
    protected String barCode;
    protected byte[] barCodeBinary;
    protected String barCode2;
    protected byte[] barCode2Binary;
    protected String barCode3;
    protected byte[] barCode3Binary;
    protected Money base;
    protected Money billingFee;
    protected String bolComment;
    protected String bolLegalStatement;
    protected Money borderProcessingFee;
    protected String brokerageMethod;
    protected Boolean brokerageThirdPartyBilling;
    protected String brokerageThirdPartyBillingAccount;
    protected NameAddress brokerageThirdPartyBillingAddress;
    protected Integer bundleId;
    protected IntegerList bundleIdList;
    protected Boolean calltag;
    protected Money calltagFee;
    protected String calltagNumber;
    protected Boolean carbonNeutral;
    protected Money carbonNeutralFee;
    protected String carrierInstructions;
    protected Boolean carrierMonitoring;
    protected Money carrierMonitoringFee;
    protected String carrierTenderMethod;
    protected Boolean certifiedMail;
    protected Money certifiedMailFee;
    protected String certOfOriginMethod;
    protected Boolean chainOfSignature;
    protected Money chainOfSignatureFee;
    protected Boolean codAlternateNumber;
    protected Money codAmount;
    protected Money codFee;
    protected String codInstructions;
    protected String codMasterTrackingNumber;
    protected String codMethod;
    protected String codNumber;
    @XmlList
    protected List<String> codPaymentMethod;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar codPaymentMethodPostDatedCheckDate;
    protected String codPayorAddressEmail;
    protected String codPayorInstructions;
    protected String codPendingFeePayorPercentage;
    protected NameAddress codReturnAddress;
    protected String codReturnMethod;
    protected String codReturnTrackingNumber;
    protected String comments;
    protected String commercialInvoiceMethod;
    protected String commitmentCode;
    protected String commodityClass;
    protected String commodityCondition;
    protected CommodityList commodityContents;
    protected NameAddress consignee;
    protected String consigneeAccount;
    protected String consigneeBillingId;
    protected String consigneeCode;
    protected String consigneeCustomsId;
    protected String consigneeEmail;
    protected String consigneeReference;
    protected String consigneeTaxId;
    protected String consigneeTaxIdType;
    protected Boolean consigneeThirdPartyBilling;
    protected String consigneeThirdPartyBillingAccount;
    protected NameAddress consigneeThirdPartyBillingAddress;
    protected String consolidationCarrier;
    protected String consolidationCode;
    protected Boolean consolidationFlag;
    protected Integer consolidationId;
    protected String consolidationShipmentId;
    protected String consolidationType;
    protected String containerCode;
    protected NameAddress customsBroker;
    protected Integer cycleCount;
    protected Money declaredValueAmount;
    protected Money declaredValueCustoms;
    protected Money declaredValueFee;
    protected String deconsolidationCarrier;
    protected Boolean deliveryExceptionNotification;
    protected String deliveryExceptionNotificationAddressEmail;
    protected String deliveryExceptionNotificationDescription;
    protected Boolean deliveryExceptionNotificationEmail;
    protected Money deliveryExceptionNotificationFee;
    protected String deliveryExceptionNotificationSenderName;
    protected String deliveryExceptionNotificationSubjectText;
    protected String deliveryMethod;
    protected String deliveryNotificationAccount;
    protected NameAddress deliveryNotificationAddress;
    protected String deliveryNotificationAddressEmail;
    protected String deliveryNotificationDescription;
    protected Boolean deliveryNotificationEmail;
    protected Money deliveryNotificationFee;
    protected Boolean deliveryNotificationFile;
    protected String deliveryNotificationSenderName;
    protected String deliveryNotificationSubjectText;
    protected Boolean deliveryNotificationVerbal;
    protected Money deliveryWindowFee;
    protected Boolean deliverToDoor;
    protected String description;
    protected String destinationAirportCode;
    protected Dimensions dimension;
    protected Boolean dimensionalWeightRated;
    protected Boolean directDelivery;
    protected Money directDeliveryFee;
    protected Money discount;
    protected String dispositionMethod;
    protected String distributionCode;
    protected NameAddress documentationConsignee;
    protected Money documentationFee;
    protected Boolean documentsOnly;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dropoffAppointmentDate;
    protected String dropoffAppointmentNumber;
    @XmlElement(defaultValue = "00:00:00")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar dropoffAppointmentTime;
    protected Money dryIceFee;
    protected String dryIcePurpose;
    protected String dryIceRegulationSet;
    protected Weight dryIceWeight;
    protected Money dutyFee;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar earliestDeliveryDate;
    @XmlElement(defaultValue = "00:00:00")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar earliestDeliveryTime;
    protected Money earlyDeliveryFee;
    protected Boolean eveningDelivery;
    protected Money eveningDeliveryFee;
    protected Boolean exchange;
    protected Money exchangeFee;
    protected String exchangeMethod;
    protected NameAddress exporter;
    protected String exporterAccount;
    protected String exporterTaxId;
    protected String exportDeclarationStatement;
    protected String exportInformationCode;
    protected String exportReason;
    protected Money extendedAreaFee;
    protected Boolean flats;
    protected Boolean forkliftDelivery;
    protected Money forkliftDeliveryFee;
    protected Boolean forkliftPickup;
    protected Money forkliftPickupFee;
    protected NameAddress forwardingAgent;
    protected String forwardingAgentTaxId;
    protected Money fuelSurcharge;
    protected Boolean goodsInFreeCirculation;
    protected Boolean groundsaver;
    protected Boolean hazmat;
    protected Boolean hazmatAllPackedInOne;
    protected HazmatItemList hazmatContents;
    protected Money hazmatFee;
    protected String hazmatHandlingInformation;
    protected Boolean hazmatOverpack;
    protected BigDecimal hazmatQValue;
    protected NameAddress hazmatSignatoryAddress;
    protected String hazmatSignatoryTitle;
    protected Boolean healthInsurance;
    protected Money healthInsuranceFee;
    protected Boolean helperDelivery;
    protected Money helperDeliveryFee;
    protected Boolean helperPickup;
    protected Money helperPickupFee;
    protected Boolean holdAtLocation;
    protected NameAddress holdAtLocationAddress;
    protected String holdAtLocationFacilityId;
    protected Money holdAtLocationFee;
    protected Boolean holidayDelivery;
    protected Money holidayDeliveryFee;
    protected Money holidayPickupFee;
    protected String hubCode;
    protected Boolean hundredweightRated;
    protected NameAddress importerOfRecord;
    protected String importerOfRecordAccount;
    protected String importerOfRecordTaxId;
    protected Boolean importDelivery;
    protected Boolean insideDelivery;
    protected Money insideDeliveryFee;
    protected Boolean insidePickup;
    protected Money insidePickupFee;
    protected String insuranceMethod;
    protected Money invoiceDiscount;
    protected Money invoiceFreight;
    protected Money invoiceInsuranceFee;
    protected Money invoiceOtherFee;
    protected Boolean inTransitNotification;
    protected String inTransitNotificationAddressEmail;
    protected String inTransitNotificationDescription;
    protected Boolean inTransitNotificationEmail;
    protected String inTransitNotificationSenderName;
    protected String inTransitNotificationSubjectText;
    protected String isoUniqueIdentifier;
    protected String itemNumber;
    protected Boolean largePackage;
    protected Money largePackageFee;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar latestDeliveryDate;
    @XmlElement(defaultValue = "00:00:00")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar latestDeliveryTime;
    protected Boolean liftgateDelivery;
    protected Money liftgateDeliveryFee;
    protected Boolean liftgatePickup;
    protected Money liftgatePickupFee;
    protected String locationId;
    protected String markings;
    protected StringList maxicode;
    protected String miscReference1;
    protected String miscReference2;
    protected String miscReference3;
    protected String miscReference4;
    protected String miscReference5;
    protected Boolean mms;
    protected String mmsPieceId;
    protected Integer msn;
    protected Money multiPieceFee;
    protected Boolean neutralDeliveryService;
    protected Money neutralDeliveryServiceFee;
    protected Integer nofnSequence;
    protected Integer nofnSequenceBundle;
    protected Integer nofnTotal;
    protected Integer nofnTotalBundle;
    protected Boolean nonhazardousLithiumBatteries;
    protected Boolean nonmachinableMail;
    protected Money nonmachinableMailFee;
    protected Boolean nonrectangular;
    protected Boolean nonstandardMail;
    protected Money nonstandardMailFee;
    protected String notificationFailureAddressEmail;
    protected Boolean notFlatMachinable;
    protected Money offshoreFee;
    protected String originatorTrackingNumber;
    protected NameAddress originAddress;
    protected Boolean oversize;
    protected Money oversizeFee;
    protected Money overDimensionFee;
    protected Integer packageListId;
    protected String packaging;
    protected String packagingDescription;
    protected Weight packagingTareWeight;
    protected Money palletFee;
    protected Boolean palletJackDelivery;
    protected Money palletJackDeliveryFee;
    protected Boolean palletJackPickup;
    protected Money palletJackPickupFee;
    protected Boolean parcelAirlift;
    protected Money parcelAirliftFee;
    protected DataDictionary parentContainer;
    protected String parentContainerCode;
    protected Boolean partiesRelated;
    protected Boolean perishable;
    protected Integer pieceCount;
    protected Money pieceCountFee;
    protected String portOfEntry;
    protected NameAddress preAlertNotificationAddress;
    protected String preAlertNotificationAddressEmail;
    protected String preAlertNotificationDescription;
    protected Boolean preAlertNotificationEmail;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String preAlertNotificationLocale;
    protected Boolean preAlertNotificationPhone;
    protected String preAlertNotificationSenderName;
    protected Boolean preAlertNotificationSms;
    protected String preAlertNotificationSubjectText;
    protected Boolean priorDeliveryNotificationConsignee;
    protected Money priorDeliveryNotificationFee;
    protected Boolean proof;
    protected Money proofFee;
    protected String proofNumber;
    protected Boolean proofRequireSignature;
    protected Boolean proofRequireSignatureAdult;
    protected Boolean proofRequireSignatureConsignee;
    protected Money proofRequireSignatureFee;
    protected Boolean proofReturnOfDocuments;
    protected Boolean proofSignatureWaiver;
    protected Boolean proofUseAlternateNumber;
    protected Weight ratedWeight;
    protected Boolean registeredMail;
    protected Money registeredMailFee;
    protected Money remoteOriginFee;
    protected Money residentialDeliveryFee;
    protected String returnAccount;
    protected NameAddress returnAddress;
    protected String returnAddressMethod;
    protected Boolean returnDelivery;
    protected String returnDeliveryAddressEmail;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String returnDeliveryAddressEmailLocale;
    protected Money returnDeliveryFee;
    protected String returnDeliveryMethod;
    protected NameAddress returnDeliveryNotificationAddress;
    protected NameAddress returnDeliveryNotificationAddress2;
    protected String returnDeliveryNotificationAddressEmail;
    protected String returnDeliveryNotificationDescription;
    protected Boolean returnDeliveryNotificationEmail;
    protected Boolean returnDeliveryNotificationFax;
    protected Money returnDeliveryNotificationFee;
    protected String returnDeliveryNotificationSenderName;
    protected String returnDeliveryNotificationSubjectText;
    protected Boolean routedExportTransaction;
    protected String routingCode;
    protected String routingCode2;
    protected String routingCode3;
    protected String routingCode4;
    protected String routingCode5;
    protected String sackLevel;
    protected String sackZip;
    protected Boolean saturdayDelivery;
    protected Money saturdayDeliveryFee;
    protected Money saturdayPickupFee;
    protected Boolean security;
    protected Money securityFee;
    protected String sedExemptionNumber;
    protected String sedMethod;
    protected String service;
    @XmlElement(defaultValue = "0001-01-01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar shipdate;
    protected String shipper;
    protected String shipperReference;
    protected NameAddress shipNotificationAddress;
    protected NameAddress shipNotificationAddress2;
    protected String shipNotificationAddressEmail;
    protected String shipNotificationAddressFax;
    protected String shipNotificationDescription;
    protected Boolean shipNotificationEmail;
    protected Boolean shipNotificationFax;
    protected Money shipNotificationFee;
    protected String shipNotificationSenderName;
    protected String shipNotificationSubjectText;
    protected Boolean shipNotificationVerbal;
    protected Boolean signatureRelease;
    protected Money special;
    protected Boolean specialDelivery;
    protected Money specialDeliveryFee;
    protected Boolean stairDelivery;
    protected Money stairDeliveryFee;
    protected Boolean stairPickup;
    protected Money stairPickupFee;
    protected String subcategory;
    protected String subNumber;
    protected Boolean sundayDelivery;
    protected Money sundayDeliveryFee;
    protected Money sundayPickupFee;
    protected Boolean suppressDc;
    protected Boolean suppressMms;
    protected Money tax;
    protected String temperatureControl;
    protected Money temperatureControlFee;
    protected Money terminalHandlingFee;
    protected String terms;
    protected String termsOfSale;
    protected Boolean thirdPartyBilling;
    protected String thirdPartyBillingAccount;
    protected NameAddress thirdPartyBillingAddress;
    protected Commitment timeInTransit;
    protected Money total;
    protected String trackingNumber;
    protected String trackingNumber2;
    protected NameAddress ultimateConsignee;
    protected String ultimateDestinationCountry;
    protected Boolean unpack;
    protected Money unpackFee;
    protected Money urbanDeliveryFee;
    protected Object userData1;
    protected Object userData2;
    protected Object userData3;
    protected Object userData4;
    protected Object userData5;
    protected String waybillBolNumber;
    protected Weight weight;
    protected Money wharfageFee;
    protected String worldEaseCode;
    protected Boolean worldEaseFlag;
    protected Integer worldEaseId;
    protected Boolean worldEaseSingleEuCountry;
    protected String zone;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String externalKey;

    /**
     * Gets the value of the additionalHandling property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAdditionalHandling() {
        return additionalHandling;
    }

    /**
     * Sets the value of the additionalHandling property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAdditionalHandling(Boolean value) {
        this.additionalHandling = value;
    }

    /**
     * Gets the value of the additionalHandlingFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getAdditionalHandlingFee() {
        return additionalHandlingFee;
    }

    /**
     * Sets the value of the additionalHandlingFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setAdditionalHandlingFee(Money value) {
        this.additionalHandlingFee = value;
    }

    /**
     * Gets the value of the additionalHandlingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalHandlingType() {
        return additionalHandlingType;
    }

    /**
     * Sets the value of the additionalHandlingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalHandlingType(String value) {
        this.additionalHandlingType = value;
    }

    /**
     * Gets the value of the additionalHardcopyDocumentation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAdditionalHardcopyDocumentation() {
        return additionalHardcopyDocumentation;
    }

    /**
     * Sets the value of the additionalHardcopyDocumentation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAdditionalHardcopyDocumentation(Boolean value) {
        this.additionalHardcopyDocumentation = value;
    }

    /**
     * Gets the value of the addressChangeNotification property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAddressChangeNotification() {
        return addressChangeNotification;
    }

    /**
     * Sets the value of the addressChangeNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddressChangeNotification(Boolean value) {
        this.addressChangeNotification = value;
    }

    /**
     * Gets the value of the aesTransactionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAesTransactionNumber() {
        return aesTransactionNumber;
    }

    /**
     * Sets the value of the aesTransactionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAesTransactionNumber(String value) {
        this.aesTransactionNumber = value;
    }

    /**
     * Gets the value of the airFreightFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getAirFreightFee() {
        return airFreightFee;
    }

    /**
     * Sets the value of the airFreightFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setAirFreightFee(Money value) {
        this.airFreightFee = value;
    }

    /**
     * Gets the value of the alcoholContents property.
     * 
     * @return
     *     possible object is
     *     {@link AlcoholItemList }
     *     
     */
    public AlcoholItemList getAlcoholContents() {
        return alcoholContents;
    }

    /**
     * Sets the value of the alcoholContents property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlcoholItemList }
     *     
     */
    public void setAlcoholContents(AlcoholItemList value) {
        this.alcoholContents = value;
    }

    /**
     * Gets the value of the appointmentDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAppointmentDelivery() {
        return appointmentDelivery;
    }

    /**
     * Sets the value of the appointmentDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAppointmentDelivery(Boolean value) {
        this.appointmentDelivery = value;
    }

    /**
     * Gets the value of the appointmentDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getAppointmentDeliveryFee() {
        return appointmentDeliveryFee;
    }

    /**
     * Sets the value of the appointmentDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setAppointmentDeliveryFee(Money value) {
        this.appointmentDeliveryFee = value;
    }

    /**
     * Gets the value of the apportionedBase property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getApportionedBase() {
        return apportionedBase;
    }

    /**
     * Sets the value of the apportionedBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setApportionedBase(Money value) {
        this.apportionedBase = value;
    }

    /**
     * Gets the value of the apportionedDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getApportionedDiscount() {
        return apportionedDiscount;
    }

    /**
     * Sets the value of the apportionedDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setApportionedDiscount(Money value) {
        this.apportionedDiscount = value;
    }

    /**
     * Gets the value of the apportionedSpecial property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getApportionedSpecial() {
        return apportionedSpecial;
    }

    /**
     * Sets the value of the apportionedSpecial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setApportionedSpecial(Money value) {
        this.apportionedSpecial = value;
    }

    /**
     * Gets the value of the apportionedTotal property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getApportionedTotal() {
        return apportionedTotal;
    }

    /**
     * Sets the value of the apportionedTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setApportionedTotal(Money value) {
        this.apportionedTotal = value;
    }

    /**
     * Gets the value of the arriveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArriveDate() {
        return arriveDate;
    }

    /**
     * Sets the value of the arriveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArriveDate(XMLGregorianCalendar value) {
        this.arriveDate = value;
    }

    /**
     * Gets the value of the barCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * Sets the value of the barCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarCode(String value) {
        this.barCode = value;
    }

    /**
     * Gets the value of the barCodeBinary property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBarCodeBinary() {
        return barCodeBinary;
    }

    /**
     * Sets the value of the barCodeBinary property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBarCodeBinary(byte[] value) {
        this.barCodeBinary = ((byte[]) value);
    }

    /**
     * Gets the value of the barCode2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarCode2() {
        return barCode2;
    }

    /**
     * Sets the value of the barCode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarCode2(String value) {
        this.barCode2 = value;
    }

    /**
     * Gets the value of the barCode2Binary property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBarCode2Binary() {
        return barCode2Binary;
    }

    /**
     * Sets the value of the barCode2Binary property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBarCode2Binary(byte[] value) {
        this.barCode2Binary = ((byte[]) value);
    }

    /**
     * Gets the value of the barCode3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarCode3() {
        return barCode3;
    }

    /**
     * Sets the value of the barCode3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarCode3(String value) {
        this.barCode3 = value;
    }

    /**
     * Gets the value of the barCode3Binary property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBarCode3Binary() {
        return barCode3Binary;
    }

    /**
     * Sets the value of the barCode3Binary property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBarCode3Binary(byte[] value) {
        this.barCode3Binary = ((byte[]) value);
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getBase() {
        return base;
    }

    /**
     * Sets the value of the base property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setBase(Money value) {
        this.base = value;
    }

    /**
     * Gets the value of the billingFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getBillingFee() {
        return billingFee;
    }

    /**
     * Sets the value of the billingFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setBillingFee(Money value) {
        this.billingFee = value;
    }

    /**
     * Gets the value of the bolComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBolComment() {
        return bolComment;
    }

    /**
     * Sets the value of the bolComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBolComment(String value) {
        this.bolComment = value;
    }

    /**
     * Gets the value of the bolLegalStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBolLegalStatement() {
        return bolLegalStatement;
    }

    /**
     * Sets the value of the bolLegalStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBolLegalStatement(String value) {
        this.bolLegalStatement = value;
    }

    /**
     * Gets the value of the borderProcessingFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getBorderProcessingFee() {
        return borderProcessingFee;
    }

    /**
     * Sets the value of the borderProcessingFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setBorderProcessingFee(Money value) {
        this.borderProcessingFee = value;
    }

    /**
     * Gets the value of the brokerageMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrokerageMethod() {
        return brokerageMethod;
    }

    /**
     * Sets the value of the brokerageMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrokerageMethod(String value) {
        this.brokerageMethod = value;
    }

    /**
     * Gets the value of the brokerageThirdPartyBilling property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBrokerageThirdPartyBilling() {
        return brokerageThirdPartyBilling;
    }

    /**
     * Sets the value of the brokerageThirdPartyBilling property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBrokerageThirdPartyBilling(Boolean value) {
        this.brokerageThirdPartyBilling = value;
    }

    /**
     * Gets the value of the brokerageThirdPartyBillingAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrokerageThirdPartyBillingAccount() {
        return brokerageThirdPartyBillingAccount;
    }

    /**
     * Sets the value of the brokerageThirdPartyBillingAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrokerageThirdPartyBillingAccount(String value) {
        this.brokerageThirdPartyBillingAccount = value;
    }

    /**
     * Gets the value of the brokerageThirdPartyBillingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getBrokerageThirdPartyBillingAddress() {
        return brokerageThirdPartyBillingAddress;
    }

    /**
     * Sets the value of the brokerageThirdPartyBillingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setBrokerageThirdPartyBillingAddress(NameAddress value) {
        this.brokerageThirdPartyBillingAddress = value;
    }

    /**
     * Gets the value of the bundleId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBundleId() {
        return bundleId;
    }

    /**
     * Sets the value of the bundleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBundleId(Integer value) {
        this.bundleId = value;
    }

    /**
     * Gets the value of the bundleIdList property.
     * 
     * @return
     *     possible object is
     *     {@link IntegerList }
     *     
     */
    public IntegerList getBundleIdList() {
        return bundleIdList;
    }

    /**
     * Sets the value of the bundleIdList property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegerList }
     *     
     */
    public void setBundleIdList(IntegerList value) {
        this.bundleIdList = value;
    }

    /**
     * Gets the value of the calltag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCalltag() {
        return calltag;
    }

    /**
     * Sets the value of the calltag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCalltag(Boolean value) {
        this.calltag = value;
    }

    /**
     * Gets the value of the calltagFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getCalltagFee() {
        return calltagFee;
    }

    /**
     * Sets the value of the calltagFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setCalltagFee(Money value) {
        this.calltagFee = value;
    }

    /**
     * Gets the value of the calltagNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalltagNumber() {
        return calltagNumber;
    }

    /**
     * Sets the value of the calltagNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalltagNumber(String value) {
        this.calltagNumber = value;
    }

    /**
     * Gets the value of the carbonNeutral property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCarbonNeutral() {
        return carbonNeutral;
    }

    /**
     * Sets the value of the carbonNeutral property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCarbonNeutral(Boolean value) {
        this.carbonNeutral = value;
    }

    /**
     * Gets the value of the carbonNeutralFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getCarbonNeutralFee() {
        return carbonNeutralFee;
    }

    /**
     * Sets the value of the carbonNeutralFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setCarbonNeutralFee(Money value) {
        this.carbonNeutralFee = value;
    }

    /**
     * Gets the value of the carrierInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierInstructions() {
        return carrierInstructions;
    }

    /**
     * Sets the value of the carrierInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierInstructions(String value) {
        this.carrierInstructions = value;
    }

    /**
     * Gets the value of the carrierMonitoring property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCarrierMonitoring() {
        return carrierMonitoring;
    }

    /**
     * Sets the value of the carrierMonitoring property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCarrierMonitoring(Boolean value) {
        this.carrierMonitoring = value;
    }

    /**
     * Gets the value of the carrierMonitoringFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getCarrierMonitoringFee() {
        return carrierMonitoringFee;
    }

    /**
     * Sets the value of the carrierMonitoringFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setCarrierMonitoringFee(Money value) {
        this.carrierMonitoringFee = value;
    }

    /**
     * Gets the value of the carrierTenderMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierTenderMethod() {
        return carrierTenderMethod;
    }

    /**
     * Sets the value of the carrierTenderMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierTenderMethod(String value) {
        this.carrierTenderMethod = value;
    }

    /**
     * Gets the value of the certifiedMail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCertifiedMail() {
        return certifiedMail;
    }

    /**
     * Sets the value of the certifiedMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCertifiedMail(Boolean value) {
        this.certifiedMail = value;
    }

    /**
     * Gets the value of the certifiedMailFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getCertifiedMailFee() {
        return certifiedMailFee;
    }

    /**
     * Sets the value of the certifiedMailFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setCertifiedMailFee(Money value) {
        this.certifiedMailFee = value;
    }

    /**
     * Gets the value of the certOfOriginMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertOfOriginMethod() {
        return certOfOriginMethod;
    }

    /**
     * Sets the value of the certOfOriginMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertOfOriginMethod(String value) {
        this.certOfOriginMethod = value;
    }

    /**
     * Gets the value of the chainOfSignature property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isChainOfSignature() {
        return chainOfSignature;
    }

    /**
     * Sets the value of the chainOfSignature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setChainOfSignature(Boolean value) {
        this.chainOfSignature = value;
    }

    /**
     * Gets the value of the chainOfSignatureFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getChainOfSignatureFee() {
        return chainOfSignatureFee;
    }

    /**
     * Sets the value of the chainOfSignatureFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setChainOfSignatureFee(Money value) {
        this.chainOfSignatureFee = value;
    }

    /**
     * Gets the value of the codAlternateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCodAlternateNumber() {
        return codAlternateNumber;
    }

    /**
     * Sets the value of the codAlternateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCodAlternateNumber(Boolean value) {
        this.codAlternateNumber = value;
    }

    /**
     * Gets the value of the codAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getCodAmount() {
        return codAmount;
    }

    /**
     * Sets the value of the codAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setCodAmount(Money value) {
        this.codAmount = value;
    }

    /**
     * Gets the value of the codFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getCodFee() {
        return codFee;
    }

    /**
     * Sets the value of the codFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setCodFee(Money value) {
        this.codFee = value;
    }

    /**
     * Gets the value of the codInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodInstructions() {
        return codInstructions;
    }

    /**
     * Sets the value of the codInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodInstructions(String value) {
        this.codInstructions = value;
    }

    /**
     * Gets the value of the codMasterTrackingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMasterTrackingNumber() {
        return codMasterTrackingNumber;
    }

    /**
     * Sets the value of the codMasterTrackingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMasterTrackingNumber(String value) {
        this.codMasterTrackingNumber = value;
    }

    /**
     * Gets the value of the codMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMethod() {
        return codMethod;
    }

    /**
     * Sets the value of the codMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMethod(String value) {
        this.codMethod = value;
    }

    /**
     * Gets the value of the codNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodNumber() {
        return codNumber;
    }

    /**
     * Sets the value of the codNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodNumber(String value) {
        this.codNumber = value;
    }

    /**
     * Gets the value of the codPaymentMethod property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codPaymentMethod property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodPaymentMethod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCodPaymentMethod() {
        if (codPaymentMethod == null) {
            codPaymentMethod = new ArrayList<String>();
        }
        return this.codPaymentMethod;
    }

    /**
     * Gets the value of the codPaymentMethodPostDatedCheckDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCodPaymentMethodPostDatedCheckDate() {
        return codPaymentMethodPostDatedCheckDate;
    }

    /**
     * Sets the value of the codPaymentMethodPostDatedCheckDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCodPaymentMethodPostDatedCheckDate(XMLGregorianCalendar value) {
        this.codPaymentMethodPostDatedCheckDate = value;
    }

    /**
     * Gets the value of the codPayorAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPayorAddressEmail() {
        return codPayorAddressEmail;
    }

    /**
     * Sets the value of the codPayorAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPayorAddressEmail(String value) {
        this.codPayorAddressEmail = value;
    }

    /**
     * Gets the value of the codPayorInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPayorInstructions() {
        return codPayorInstructions;
    }

    /**
     * Sets the value of the codPayorInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPayorInstructions(String value) {
        this.codPayorInstructions = value;
    }

    /**
     * Gets the value of the codPendingFeePayorPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPendingFeePayorPercentage() {
        return codPendingFeePayorPercentage;
    }

    /**
     * Sets the value of the codPendingFeePayorPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPendingFeePayorPercentage(String value) {
        this.codPendingFeePayorPercentage = value;
    }

    /**
     * Gets the value of the codReturnAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getCodReturnAddress() {
        return codReturnAddress;
    }

    /**
     * Sets the value of the codReturnAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setCodReturnAddress(NameAddress value) {
        this.codReturnAddress = value;
    }

    /**
     * Gets the value of the codReturnMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodReturnMethod() {
        return codReturnMethod;
    }

    /**
     * Sets the value of the codReturnMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodReturnMethod(String value) {
        this.codReturnMethod = value;
    }

    /**
     * Gets the value of the codReturnTrackingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodReturnTrackingNumber() {
        return codReturnTrackingNumber;
    }

    /**
     * Sets the value of the codReturnTrackingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodReturnTrackingNumber(String value) {
        this.codReturnTrackingNumber = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the commercialInvoiceMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommercialInvoiceMethod() {
        return commercialInvoiceMethod;
    }

    /**
     * Sets the value of the commercialInvoiceMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommercialInvoiceMethod(String value) {
        this.commercialInvoiceMethod = value;
    }

    /**
     * Gets the value of the commitmentCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommitmentCode() {
        return commitmentCode;
    }

    /**
     * Sets the value of the commitmentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommitmentCode(String value) {
        this.commitmentCode = value;
    }

    /**
     * Gets the value of the commodityClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodityClass() {
        return commodityClass;
    }

    /**
     * Sets the value of the commodityClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodityClass(String value) {
        this.commodityClass = value;
    }

    /**
     * Gets the value of the commodityCondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodityCondition() {
        return commodityCondition;
    }

    /**
     * Sets the value of the commodityCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodityCondition(String value) {
        this.commodityCondition = value;
    }

    /**
     * Gets the value of the commodityContents property.
     * 
     * @return
     *     possible object is
     *     {@link CommodityList }
     *     
     */
    public CommodityList getCommodityContents() {
        return commodityContents;
    }

    /**
     * Sets the value of the commodityContents property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommodityList }
     *     
     */
    public void setCommodityContents(CommodityList value) {
        this.commodityContents = value;
    }

    /**
     * Gets the value of the consignee property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getConsignee() {
        return consignee;
    }

    /**
     * Sets the value of the consignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setConsignee(NameAddress value) {
        this.consignee = value;
    }

    /**
     * Gets the value of the consigneeAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeAccount() {
        return consigneeAccount;
    }

    /**
     * Sets the value of the consigneeAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeAccount(String value) {
        this.consigneeAccount = value;
    }

    /**
     * Gets the value of the consigneeBillingId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeBillingId() {
        return consigneeBillingId;
    }

    /**
     * Sets the value of the consigneeBillingId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeBillingId(String value) {
        this.consigneeBillingId = value;
    }

    /**
     * Gets the value of the consigneeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeCode() {
        return consigneeCode;
    }

    /**
     * Sets the value of the consigneeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeCode(String value) {
        this.consigneeCode = value;
    }

    /**
     * Gets the value of the consigneeCustomsId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeCustomsId() {
        return consigneeCustomsId;
    }

    /**
     * Sets the value of the consigneeCustomsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeCustomsId(String value) {
        this.consigneeCustomsId = value;
    }

    /**
     * Gets the value of the consigneeEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    /**
     * Sets the value of the consigneeEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeEmail(String value) {
        this.consigneeEmail = value;
    }

    /**
     * Gets the value of the consigneeReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeReference() {
        return consigneeReference;
    }

    /**
     * Sets the value of the consigneeReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeReference(String value) {
        this.consigneeReference = value;
    }

    /**
     * Gets the value of the consigneeTaxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeTaxId() {
        return consigneeTaxId;
    }

    /**
     * Sets the value of the consigneeTaxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeTaxId(String value) {
        this.consigneeTaxId = value;
    }

    /**
     * Gets the value of the consigneeTaxIdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeTaxIdType() {
        return consigneeTaxIdType;
    }

    /**
     * Sets the value of the consigneeTaxIdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeTaxIdType(String value) {
        this.consigneeTaxIdType = value;
    }

    /**
     * Gets the value of the consigneeThirdPartyBilling property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConsigneeThirdPartyBilling() {
        return consigneeThirdPartyBilling;
    }

    /**
     * Sets the value of the consigneeThirdPartyBilling property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConsigneeThirdPartyBilling(Boolean value) {
        this.consigneeThirdPartyBilling = value;
    }

    /**
     * Gets the value of the consigneeThirdPartyBillingAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsigneeThirdPartyBillingAccount() {
        return consigneeThirdPartyBillingAccount;
    }

    /**
     * Sets the value of the consigneeThirdPartyBillingAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsigneeThirdPartyBillingAccount(String value) {
        this.consigneeThirdPartyBillingAccount = value;
    }

    /**
     * Gets the value of the consigneeThirdPartyBillingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getConsigneeThirdPartyBillingAddress() {
        return consigneeThirdPartyBillingAddress;
    }

    /**
     * Sets the value of the consigneeThirdPartyBillingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setConsigneeThirdPartyBillingAddress(NameAddress value) {
        this.consigneeThirdPartyBillingAddress = value;
    }

    /**
     * Gets the value of the consolidationCarrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsolidationCarrier() {
        return consolidationCarrier;
    }

    /**
     * Sets the value of the consolidationCarrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsolidationCarrier(String value) {
        this.consolidationCarrier = value;
    }

    /**
     * Gets the value of the consolidationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsolidationCode() {
        return consolidationCode;
    }

    /**
     * Sets the value of the consolidationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsolidationCode(String value) {
        this.consolidationCode = value;
    }

    /**
     * Gets the value of the consolidationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConsolidationFlag() {
        return consolidationFlag;
    }

    /**
     * Sets the value of the consolidationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConsolidationFlag(Boolean value) {
        this.consolidationFlag = value;
    }

    /**
     * Gets the value of the consolidationId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getConsolidationId() {
        return consolidationId;
    }

    /**
     * Sets the value of the consolidationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setConsolidationId(Integer value) {
        this.consolidationId = value;
    }

    /**
     * Gets the value of the consolidationShipmentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsolidationShipmentId() {
        return consolidationShipmentId;
    }

    /**
     * Sets the value of the consolidationShipmentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsolidationShipmentId(String value) {
        this.consolidationShipmentId = value;
    }

    /**
     * Gets the value of the consolidationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsolidationType() {
        return consolidationType;
    }

    /**
     * Sets the value of the consolidationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsolidationType(String value) {
        this.consolidationType = value;
    }

    /**
     * Gets the value of the containerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerCode() {
        return containerCode;
    }

    /**
     * Sets the value of the containerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerCode(String value) {
        this.containerCode = value;
    }

    /**
     * Gets the value of the customsBroker property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getCustomsBroker() {
        return customsBroker;
    }

    /**
     * Sets the value of the customsBroker property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setCustomsBroker(NameAddress value) {
        this.customsBroker = value;
    }

    /**
     * Gets the value of the cycleCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCycleCount() {
        return cycleCount;
    }

    /**
     * Sets the value of the cycleCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCycleCount(Integer value) {
        this.cycleCount = value;
    }

    /**
     * Gets the value of the declaredValueAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDeclaredValueAmount() {
        return declaredValueAmount;
    }

    /**
     * Sets the value of the declaredValueAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDeclaredValueAmount(Money value) {
        this.declaredValueAmount = value;
    }

    /**
     * Gets the value of the declaredValueCustoms property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDeclaredValueCustoms() {
        return declaredValueCustoms;
    }

    /**
     * Sets the value of the declaredValueCustoms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDeclaredValueCustoms(Money value) {
        this.declaredValueCustoms = value;
    }

    /**
     * Gets the value of the declaredValueFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDeclaredValueFee() {
        return declaredValueFee;
    }

    /**
     * Sets the value of the declaredValueFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDeclaredValueFee(Money value) {
        this.declaredValueFee = value;
    }

    /**
     * Gets the value of the deconsolidationCarrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeconsolidationCarrier() {
        return deconsolidationCarrier;
    }

    /**
     * Sets the value of the deconsolidationCarrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeconsolidationCarrier(String value) {
        this.deconsolidationCarrier = value;
    }

    /**
     * Gets the value of the deliveryExceptionNotification property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeliveryExceptionNotification() {
        return deliveryExceptionNotification;
    }

    /**
     * Sets the value of the deliveryExceptionNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeliveryExceptionNotification(Boolean value) {
        this.deliveryExceptionNotification = value;
    }

    /**
     * Gets the value of the deliveryExceptionNotificationAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryExceptionNotificationAddressEmail() {
        return deliveryExceptionNotificationAddressEmail;
    }

    /**
     * Sets the value of the deliveryExceptionNotificationAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryExceptionNotificationAddressEmail(String value) {
        this.deliveryExceptionNotificationAddressEmail = value;
    }

    /**
     * Gets the value of the deliveryExceptionNotificationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryExceptionNotificationDescription() {
        return deliveryExceptionNotificationDescription;
    }

    /**
     * Sets the value of the deliveryExceptionNotificationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryExceptionNotificationDescription(String value) {
        this.deliveryExceptionNotificationDescription = value;
    }

    /**
     * Gets the value of the deliveryExceptionNotificationEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeliveryExceptionNotificationEmail() {
        return deliveryExceptionNotificationEmail;
    }

    /**
     * Sets the value of the deliveryExceptionNotificationEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeliveryExceptionNotificationEmail(Boolean value) {
        this.deliveryExceptionNotificationEmail = value;
    }

    /**
     * Gets the value of the deliveryExceptionNotificationFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDeliveryExceptionNotificationFee() {
        return deliveryExceptionNotificationFee;
    }

    /**
     * Sets the value of the deliveryExceptionNotificationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDeliveryExceptionNotificationFee(Money value) {
        this.deliveryExceptionNotificationFee = value;
    }

    /**
     * Gets the value of the deliveryExceptionNotificationSenderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryExceptionNotificationSenderName() {
        return deliveryExceptionNotificationSenderName;
    }

    /**
     * Sets the value of the deliveryExceptionNotificationSenderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryExceptionNotificationSenderName(String value) {
        this.deliveryExceptionNotificationSenderName = value;
    }

    /**
     * Gets the value of the deliveryExceptionNotificationSubjectText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryExceptionNotificationSubjectText() {
        return deliveryExceptionNotificationSubjectText;
    }

    /**
     * Sets the value of the deliveryExceptionNotificationSubjectText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryExceptionNotificationSubjectText(String value) {
        this.deliveryExceptionNotificationSubjectText = value;
    }

    /**
     * Gets the value of the deliveryMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Sets the value of the deliveryMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryMethod(String value) {
        this.deliveryMethod = value;
    }

    /**
     * Gets the value of the deliveryNotificationAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryNotificationAccount() {
        return deliveryNotificationAccount;
    }

    /**
     * Sets the value of the deliveryNotificationAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryNotificationAccount(String value) {
        this.deliveryNotificationAccount = value;
    }

    /**
     * Gets the value of the deliveryNotificationAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getDeliveryNotificationAddress() {
        return deliveryNotificationAddress;
    }

    /**
     * Sets the value of the deliveryNotificationAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setDeliveryNotificationAddress(NameAddress value) {
        this.deliveryNotificationAddress = value;
    }

    /**
     * Gets the value of the deliveryNotificationAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryNotificationAddressEmail() {
        return deliveryNotificationAddressEmail;
    }

    /**
     * Sets the value of the deliveryNotificationAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryNotificationAddressEmail(String value) {
        this.deliveryNotificationAddressEmail = value;
    }

    /**
     * Gets the value of the deliveryNotificationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryNotificationDescription() {
        return deliveryNotificationDescription;
    }

    /**
     * Sets the value of the deliveryNotificationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryNotificationDescription(String value) {
        this.deliveryNotificationDescription = value;
    }

    /**
     * Gets the value of the deliveryNotificationEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeliveryNotificationEmail() {
        return deliveryNotificationEmail;
    }

    /**
     * Sets the value of the deliveryNotificationEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeliveryNotificationEmail(Boolean value) {
        this.deliveryNotificationEmail = value;
    }

    /**
     * Gets the value of the deliveryNotificationFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDeliveryNotificationFee() {
        return deliveryNotificationFee;
    }

    /**
     * Sets the value of the deliveryNotificationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDeliveryNotificationFee(Money value) {
        this.deliveryNotificationFee = value;
    }

    /**
     * Gets the value of the deliveryNotificationFile property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeliveryNotificationFile() {
        return deliveryNotificationFile;
    }

    /**
     * Sets the value of the deliveryNotificationFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeliveryNotificationFile(Boolean value) {
        this.deliveryNotificationFile = value;
    }

    /**
     * Gets the value of the deliveryNotificationSenderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryNotificationSenderName() {
        return deliveryNotificationSenderName;
    }

    /**
     * Sets the value of the deliveryNotificationSenderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryNotificationSenderName(String value) {
        this.deliveryNotificationSenderName = value;
    }

    /**
     * Gets the value of the deliveryNotificationSubjectText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryNotificationSubjectText() {
        return deliveryNotificationSubjectText;
    }

    /**
     * Sets the value of the deliveryNotificationSubjectText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryNotificationSubjectText(String value) {
        this.deliveryNotificationSubjectText = value;
    }

    /**
     * Gets the value of the deliveryNotificationVerbal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeliveryNotificationVerbal() {
        return deliveryNotificationVerbal;
    }

    /**
     * Sets the value of the deliveryNotificationVerbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeliveryNotificationVerbal(Boolean value) {
        this.deliveryNotificationVerbal = value;
    }

    /**
     * Gets the value of the deliveryWindowFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDeliveryWindowFee() {
        return deliveryWindowFee;
    }

    /**
     * Sets the value of the deliveryWindowFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDeliveryWindowFee(Money value) {
        this.deliveryWindowFee = value;
    }

    /**
     * Gets the value of the deliverToDoor property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeliverToDoor() {
        return deliverToDoor;
    }

    /**
     * Sets the value of the deliverToDoor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeliverToDoor(Boolean value) {
        this.deliverToDoor = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the destinationAirportCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    /**
     * Sets the value of the destinationAirportCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationAirportCode(String value) {
        this.destinationAirportCode = value;
    }

    /**
     * Gets the value of the dimension property.
     * 
     * @return
     *     possible object is
     *     {@link Dimensions }
     *     
     */
    public Dimensions getDimension() {
        return dimension;
    }

    /**
     * Sets the value of the dimension property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dimensions }
     *     
     */
    public void setDimension(Dimensions value) {
        this.dimension = value;
    }

    /**
     * Gets the value of the dimensionalWeightRated property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDimensionalWeightRated() {
        return dimensionalWeightRated;
    }

    /**
     * Sets the value of the dimensionalWeightRated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDimensionalWeightRated(Boolean value) {
        this.dimensionalWeightRated = value;
    }

    /**
     * Gets the value of the directDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDirectDelivery() {
        return directDelivery;
    }

    /**
     * Sets the value of the directDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDirectDelivery(Boolean value) {
        this.directDelivery = value;
    }

    /**
     * Gets the value of the directDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDirectDeliveryFee() {
        return directDeliveryFee;
    }

    /**
     * Sets the value of the directDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDirectDeliveryFee(Money value) {
        this.directDeliveryFee = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDiscount(Money value) {
        this.discount = value;
    }

    /**
     * Gets the value of the dispositionMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispositionMethod() {
        return dispositionMethod;
    }

    /**
     * Sets the value of the dispositionMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispositionMethod(String value) {
        this.dispositionMethod = value;
    }

    /**
     * Gets the value of the distributionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributionCode() {
        return distributionCode;
    }

    /**
     * Sets the value of the distributionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributionCode(String value) {
        this.distributionCode = value;
    }

    /**
     * Gets the value of the documentationConsignee property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getDocumentationConsignee() {
        return documentationConsignee;
    }

    /**
     * Sets the value of the documentationConsignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setDocumentationConsignee(NameAddress value) {
        this.documentationConsignee = value;
    }

    /**
     * Gets the value of the documentationFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDocumentationFee() {
        return documentationFee;
    }

    /**
     * Sets the value of the documentationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDocumentationFee(Money value) {
        this.documentationFee = value;
    }

    /**
     * Gets the value of the documentsOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDocumentsOnly() {
        return documentsOnly;
    }

    /**
     * Sets the value of the documentsOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDocumentsOnly(Boolean value) {
        this.documentsOnly = value;
    }

    /**
     * Gets the value of the dropoffAppointmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDropoffAppointmentDate() {
        return dropoffAppointmentDate;
    }

    /**
     * Sets the value of the dropoffAppointmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDropoffAppointmentDate(XMLGregorianCalendar value) {
        this.dropoffAppointmentDate = value;
    }

    /**
     * Gets the value of the dropoffAppointmentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDropoffAppointmentNumber() {
        return dropoffAppointmentNumber;
    }

    /**
     * Sets the value of the dropoffAppointmentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDropoffAppointmentNumber(String value) {
        this.dropoffAppointmentNumber = value;
    }

    /**
     * Gets the value of the dropoffAppointmentTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDropoffAppointmentTime() {
        return dropoffAppointmentTime;
    }

    /**
     * Sets the value of the dropoffAppointmentTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDropoffAppointmentTime(XMLGregorianCalendar value) {
        this.dropoffAppointmentTime = value;
    }

    /**
     * Gets the value of the dryIceFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDryIceFee() {
        return dryIceFee;
    }

    /**
     * Sets the value of the dryIceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDryIceFee(Money value) {
        this.dryIceFee = value;
    }

    /**
     * Gets the value of the dryIcePurpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDryIcePurpose() {
        return dryIcePurpose;
    }

    /**
     * Sets the value of the dryIcePurpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDryIcePurpose(String value) {
        this.dryIcePurpose = value;
    }

    /**
     * Gets the value of the dryIceRegulationSet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDryIceRegulationSet() {
        return dryIceRegulationSet;
    }

    /**
     * Sets the value of the dryIceRegulationSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDryIceRegulationSet(String value) {
        this.dryIceRegulationSet = value;
    }

    /**
     * Gets the value of the dryIceWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getDryIceWeight() {
        return dryIceWeight;
    }

    /**
     * Sets the value of the dryIceWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setDryIceWeight(Weight value) {
        this.dryIceWeight = value;
    }

    /**
     * Gets the value of the dutyFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDutyFee() {
        return dutyFee;
    }

    /**
     * Sets the value of the dutyFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDutyFee(Money value) {
        this.dutyFee = value;
    }

    /**
     * Gets the value of the earliestDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEarliestDeliveryDate() {
        return earliestDeliveryDate;
    }

    /**
     * Sets the value of the earliestDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEarliestDeliveryDate(XMLGregorianCalendar value) {
        this.earliestDeliveryDate = value;
    }

    /**
     * Gets the value of the earliestDeliveryTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEarliestDeliveryTime() {
        return earliestDeliveryTime;
    }

    /**
     * Sets the value of the earliestDeliveryTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEarliestDeliveryTime(XMLGregorianCalendar value) {
        this.earliestDeliveryTime = value;
    }

    /**
     * Gets the value of the earlyDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getEarlyDeliveryFee() {
        return earlyDeliveryFee;
    }

    /**
     * Sets the value of the earlyDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setEarlyDeliveryFee(Money value) {
        this.earlyDeliveryFee = value;
    }

    /**
     * Gets the value of the eveningDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEveningDelivery() {
        return eveningDelivery;
    }

    /**
     * Sets the value of the eveningDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEveningDelivery(Boolean value) {
        this.eveningDelivery = value;
    }

    /**
     * Gets the value of the eveningDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getEveningDeliveryFee() {
        return eveningDeliveryFee;
    }

    /**
     * Sets the value of the eveningDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setEveningDeliveryFee(Money value) {
        this.eveningDeliveryFee = value;
    }

    /**
     * Gets the value of the exchange property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExchange() {
        return exchange;
    }

    /**
     * Sets the value of the exchange property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExchange(Boolean value) {
        this.exchange = value;
    }

    /**
     * Gets the value of the exchangeFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getExchangeFee() {
        return exchangeFee;
    }

    /**
     * Sets the value of the exchangeFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setExchangeFee(Money value) {
        this.exchangeFee = value;
    }

    /**
     * Gets the value of the exchangeMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeMethod() {
        return exchangeMethod;
    }

    /**
     * Sets the value of the exchangeMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeMethod(String value) {
        this.exchangeMethod = value;
    }

    /**
     * Gets the value of the exporter property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getExporter() {
        return exporter;
    }

    /**
     * Sets the value of the exporter property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setExporter(NameAddress value) {
        this.exporter = value;
    }

    /**
     * Gets the value of the exporterAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExporterAccount() {
        return exporterAccount;
    }

    /**
     * Sets the value of the exporterAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExporterAccount(String value) {
        this.exporterAccount = value;
    }

    /**
     * Gets the value of the exporterTaxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExporterTaxId() {
        return exporterTaxId;
    }

    /**
     * Sets the value of the exporterTaxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExporterTaxId(String value) {
        this.exporterTaxId = value;
    }

    /**
     * Gets the value of the exportDeclarationStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportDeclarationStatement() {
        return exportDeclarationStatement;
    }

    /**
     * Sets the value of the exportDeclarationStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportDeclarationStatement(String value) {
        this.exportDeclarationStatement = value;
    }

    /**
     * Gets the value of the exportInformationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportInformationCode() {
        return exportInformationCode;
    }

    /**
     * Sets the value of the exportInformationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportInformationCode(String value) {
        this.exportInformationCode = value;
    }

    /**
     * Gets the value of the exportReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportReason() {
        return exportReason;
    }

    /**
     * Sets the value of the exportReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportReason(String value) {
        this.exportReason = value;
    }

    /**
     * Gets the value of the extendedAreaFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getExtendedAreaFee() {
        return extendedAreaFee;
    }

    /**
     * Sets the value of the extendedAreaFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setExtendedAreaFee(Money value) {
        this.extendedAreaFee = value;
    }

    /**
     * Gets the value of the flats property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlats() {
        return flats;
    }

    /**
     * Sets the value of the flats property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlats(Boolean value) {
        this.flats = value;
    }

    /**
     * Gets the value of the forkliftDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForkliftDelivery() {
        return forkliftDelivery;
    }

    /**
     * Sets the value of the forkliftDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForkliftDelivery(Boolean value) {
        this.forkliftDelivery = value;
    }

    /**
     * Gets the value of the forkliftDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getForkliftDeliveryFee() {
        return forkliftDeliveryFee;
    }

    /**
     * Sets the value of the forkliftDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setForkliftDeliveryFee(Money value) {
        this.forkliftDeliveryFee = value;
    }

    /**
     * Gets the value of the forkliftPickup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForkliftPickup() {
        return forkliftPickup;
    }

    /**
     * Sets the value of the forkliftPickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForkliftPickup(Boolean value) {
        this.forkliftPickup = value;
    }

    /**
     * Gets the value of the forkliftPickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getForkliftPickupFee() {
        return forkliftPickupFee;
    }

    /**
     * Sets the value of the forkliftPickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setForkliftPickupFee(Money value) {
        this.forkliftPickupFee = value;
    }

    /**
     * Gets the value of the forwardingAgent property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getForwardingAgent() {
        return forwardingAgent;
    }

    /**
     * Sets the value of the forwardingAgent property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setForwardingAgent(NameAddress value) {
        this.forwardingAgent = value;
    }

    /**
     * Gets the value of the forwardingAgentTaxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForwardingAgentTaxId() {
        return forwardingAgentTaxId;
    }

    /**
     * Sets the value of the forwardingAgentTaxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForwardingAgentTaxId(String value) {
        this.forwardingAgentTaxId = value;
    }

    /**
     * Gets the value of the fuelSurcharge property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getFuelSurcharge() {
        return fuelSurcharge;
    }

    /**
     * Sets the value of the fuelSurcharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setFuelSurcharge(Money value) {
        this.fuelSurcharge = value;
    }

    /**
     * Gets the value of the goodsInFreeCirculation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGoodsInFreeCirculation() {
        return goodsInFreeCirculation;
    }

    /**
     * Sets the value of the goodsInFreeCirculation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGoodsInFreeCirculation(Boolean value) {
        this.goodsInFreeCirculation = value;
    }

    /**
     * Gets the value of the groundsaver property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGroundsaver() {
        return groundsaver;
    }

    /**
     * Sets the value of the groundsaver property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGroundsaver(Boolean value) {
        this.groundsaver = value;
    }

    /**
     * Gets the value of the hazmat property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmat() {
        return hazmat;
    }

    /**
     * Sets the value of the hazmat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmat(Boolean value) {
        this.hazmat = value;
    }

    /**
     * Gets the value of the hazmatAllPackedInOne property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmatAllPackedInOne() {
        return hazmatAllPackedInOne;
    }

    /**
     * Sets the value of the hazmatAllPackedInOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmatAllPackedInOne(Boolean value) {
        this.hazmatAllPackedInOne = value;
    }

    /**
     * Gets the value of the hazmatContents property.
     * 
     * @return
     *     possible object is
     *     {@link HazmatItemList }
     *     
     */
    public HazmatItemList getHazmatContents() {
        return hazmatContents;
    }

    /**
     * Sets the value of the hazmatContents property.
     * 
     * @param value
     *     allowed object is
     *     {@link HazmatItemList }
     *     
     */
    public void setHazmatContents(HazmatItemList value) {
        this.hazmatContents = value;
    }

    /**
     * Gets the value of the hazmatFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getHazmatFee() {
        return hazmatFee;
    }

    /**
     * Sets the value of the hazmatFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setHazmatFee(Money value) {
        this.hazmatFee = value;
    }

    /**
     * Gets the value of the hazmatHandlingInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatHandlingInformation() {
        return hazmatHandlingInformation;
    }

    /**
     * Sets the value of the hazmatHandlingInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatHandlingInformation(String value) {
        this.hazmatHandlingInformation = value;
    }

    /**
     * Gets the value of the hazmatOverpack property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmatOverpack() {
        return hazmatOverpack;
    }

    /**
     * Sets the value of the hazmatOverpack property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmatOverpack(Boolean value) {
        this.hazmatOverpack = value;
    }

    /**
     * Gets the value of the hazmatQValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHazmatQValue() {
        return hazmatQValue;
    }

    /**
     * Sets the value of the hazmatQValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHazmatQValue(BigDecimal value) {
        this.hazmatQValue = value;
    }

    /**
     * Gets the value of the hazmatSignatoryAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getHazmatSignatoryAddress() {
        return hazmatSignatoryAddress;
    }

    /**
     * Sets the value of the hazmatSignatoryAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setHazmatSignatoryAddress(NameAddress value) {
        this.hazmatSignatoryAddress = value;
    }

    /**
     * Gets the value of the hazmatSignatoryTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatSignatoryTitle() {
        return hazmatSignatoryTitle;
    }

    /**
     * Sets the value of the hazmatSignatoryTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatSignatoryTitle(String value) {
        this.hazmatSignatoryTitle = value;
    }

    /**
     * Gets the value of the healthInsurance property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHealthInsurance() {
        return healthInsurance;
    }

    /**
     * Sets the value of the healthInsurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHealthInsurance(Boolean value) {
        this.healthInsurance = value;
    }

    /**
     * Gets the value of the healthInsuranceFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getHealthInsuranceFee() {
        return healthInsuranceFee;
    }

    /**
     * Sets the value of the healthInsuranceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setHealthInsuranceFee(Money value) {
        this.healthInsuranceFee = value;
    }

    /**
     * Gets the value of the helperDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHelperDelivery() {
        return helperDelivery;
    }

    /**
     * Sets the value of the helperDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHelperDelivery(Boolean value) {
        this.helperDelivery = value;
    }

    /**
     * Gets the value of the helperDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getHelperDeliveryFee() {
        return helperDeliveryFee;
    }

    /**
     * Sets the value of the helperDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setHelperDeliveryFee(Money value) {
        this.helperDeliveryFee = value;
    }

    /**
     * Gets the value of the helperPickup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHelperPickup() {
        return helperPickup;
    }

    /**
     * Sets the value of the helperPickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHelperPickup(Boolean value) {
        this.helperPickup = value;
    }

    /**
     * Gets the value of the helperPickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getHelperPickupFee() {
        return helperPickupFee;
    }

    /**
     * Sets the value of the helperPickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setHelperPickupFee(Money value) {
        this.helperPickupFee = value;
    }

    /**
     * Gets the value of the holdAtLocation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHoldAtLocation() {
        return holdAtLocation;
    }

    /**
     * Sets the value of the holdAtLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHoldAtLocation(Boolean value) {
        this.holdAtLocation = value;
    }

    /**
     * Gets the value of the holdAtLocationAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getHoldAtLocationAddress() {
        return holdAtLocationAddress;
    }

    /**
     * Sets the value of the holdAtLocationAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setHoldAtLocationAddress(NameAddress value) {
        this.holdAtLocationAddress = value;
    }

    /**
     * Gets the value of the holdAtLocationFacilityId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoldAtLocationFacilityId() {
        return holdAtLocationFacilityId;
    }

    /**
     * Sets the value of the holdAtLocationFacilityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoldAtLocationFacilityId(String value) {
        this.holdAtLocationFacilityId = value;
    }

    /**
     * Gets the value of the holdAtLocationFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getHoldAtLocationFee() {
        return holdAtLocationFee;
    }

    /**
     * Sets the value of the holdAtLocationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setHoldAtLocationFee(Money value) {
        this.holdAtLocationFee = value;
    }

    /**
     * Gets the value of the holidayDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHolidayDelivery() {
        return holidayDelivery;
    }

    /**
     * Sets the value of the holidayDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHolidayDelivery(Boolean value) {
        this.holidayDelivery = value;
    }

    /**
     * Gets the value of the holidayDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getHolidayDeliveryFee() {
        return holidayDeliveryFee;
    }

    /**
     * Sets the value of the holidayDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setHolidayDeliveryFee(Money value) {
        this.holidayDeliveryFee = value;
    }

    /**
     * Gets the value of the holidayPickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getHolidayPickupFee() {
        return holidayPickupFee;
    }

    /**
     * Sets the value of the holidayPickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setHolidayPickupFee(Money value) {
        this.holidayPickupFee = value;
    }

    /**
     * Gets the value of the hubCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHubCode() {
        return hubCode;
    }

    /**
     * Sets the value of the hubCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHubCode(String value) {
        this.hubCode = value;
    }

    /**
     * Gets the value of the hundredweightRated property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHundredweightRated() {
        return hundredweightRated;
    }

    /**
     * Sets the value of the hundredweightRated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHundredweightRated(Boolean value) {
        this.hundredweightRated = value;
    }

    /**
     * Gets the value of the importerOfRecord property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getImporterOfRecord() {
        return importerOfRecord;
    }

    /**
     * Sets the value of the importerOfRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setImporterOfRecord(NameAddress value) {
        this.importerOfRecord = value;
    }

    /**
     * Gets the value of the importerOfRecordAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporterOfRecordAccount() {
        return importerOfRecordAccount;
    }

    /**
     * Sets the value of the importerOfRecordAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporterOfRecordAccount(String value) {
        this.importerOfRecordAccount = value;
    }

    /**
     * Gets the value of the importerOfRecordTaxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImporterOfRecordTaxId() {
        return importerOfRecordTaxId;
    }

    /**
     * Sets the value of the importerOfRecordTaxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImporterOfRecordTaxId(String value) {
        this.importerOfRecordTaxId = value;
    }

    /**
     * Gets the value of the importDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isImportDelivery() {
        return importDelivery;
    }

    /**
     * Sets the value of the importDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setImportDelivery(Boolean value) {
        this.importDelivery = value;
    }

    /**
     * Gets the value of the insideDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInsideDelivery() {
        return insideDelivery;
    }

    /**
     * Sets the value of the insideDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInsideDelivery(Boolean value) {
        this.insideDelivery = value;
    }

    /**
     * Gets the value of the insideDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getInsideDeliveryFee() {
        return insideDeliveryFee;
    }

    /**
     * Sets the value of the insideDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setInsideDeliveryFee(Money value) {
        this.insideDeliveryFee = value;
    }

    /**
     * Gets the value of the insidePickup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInsidePickup() {
        return insidePickup;
    }

    /**
     * Sets the value of the insidePickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInsidePickup(Boolean value) {
        this.insidePickup = value;
    }

    /**
     * Gets the value of the insidePickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getInsidePickupFee() {
        return insidePickupFee;
    }

    /**
     * Sets the value of the insidePickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setInsidePickupFee(Money value) {
        this.insidePickupFee = value;
    }

    /**
     * Gets the value of the insuranceMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuranceMethod() {
        return insuranceMethod;
    }

    /**
     * Sets the value of the insuranceMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuranceMethod(String value) {
        this.insuranceMethod = value;
    }

    /**
     * Gets the value of the invoiceDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getInvoiceDiscount() {
        return invoiceDiscount;
    }

    /**
     * Sets the value of the invoiceDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setInvoiceDiscount(Money value) {
        this.invoiceDiscount = value;
    }

    /**
     * Gets the value of the invoiceFreight property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getInvoiceFreight() {
        return invoiceFreight;
    }

    /**
     * Sets the value of the invoiceFreight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setInvoiceFreight(Money value) {
        this.invoiceFreight = value;
    }

    /**
     * Gets the value of the invoiceInsuranceFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getInvoiceInsuranceFee() {
        return invoiceInsuranceFee;
    }

    /**
     * Sets the value of the invoiceInsuranceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setInvoiceInsuranceFee(Money value) {
        this.invoiceInsuranceFee = value;
    }

    /**
     * Gets the value of the invoiceOtherFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getInvoiceOtherFee() {
        return invoiceOtherFee;
    }

    /**
     * Sets the value of the invoiceOtherFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setInvoiceOtherFee(Money value) {
        this.invoiceOtherFee = value;
    }

    /**
     * Gets the value of the inTransitNotification property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInTransitNotification() {
        return inTransitNotification;
    }

    /**
     * Sets the value of the inTransitNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInTransitNotification(Boolean value) {
        this.inTransitNotification = value;
    }

    /**
     * Gets the value of the inTransitNotificationAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInTransitNotificationAddressEmail() {
        return inTransitNotificationAddressEmail;
    }

    /**
     * Sets the value of the inTransitNotificationAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInTransitNotificationAddressEmail(String value) {
        this.inTransitNotificationAddressEmail = value;
    }

    /**
     * Gets the value of the inTransitNotificationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInTransitNotificationDescription() {
        return inTransitNotificationDescription;
    }

    /**
     * Sets the value of the inTransitNotificationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInTransitNotificationDescription(String value) {
        this.inTransitNotificationDescription = value;
    }

    /**
     * Gets the value of the inTransitNotificationEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInTransitNotificationEmail() {
        return inTransitNotificationEmail;
    }

    /**
     * Sets the value of the inTransitNotificationEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInTransitNotificationEmail(Boolean value) {
        this.inTransitNotificationEmail = value;
    }

    /**
     * Gets the value of the inTransitNotificationSenderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInTransitNotificationSenderName() {
        return inTransitNotificationSenderName;
    }

    /**
     * Sets the value of the inTransitNotificationSenderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInTransitNotificationSenderName(String value) {
        this.inTransitNotificationSenderName = value;
    }

    /**
     * Gets the value of the inTransitNotificationSubjectText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInTransitNotificationSubjectText() {
        return inTransitNotificationSubjectText;
    }

    /**
     * Sets the value of the inTransitNotificationSubjectText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInTransitNotificationSubjectText(String value) {
        this.inTransitNotificationSubjectText = value;
    }

    /**
     * Gets the value of the isoUniqueIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsoUniqueIdentifier() {
        return isoUniqueIdentifier;
    }

    /**
     * Sets the value of the isoUniqueIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsoUniqueIdentifier(String value) {
        this.isoUniqueIdentifier = value;
    }

    /**
     * Gets the value of the itemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     * Sets the value of the itemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemNumber(String value) {
        this.itemNumber = value;
    }

    /**
     * Gets the value of the largePackage property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLargePackage() {
        return largePackage;
    }

    /**
     * Sets the value of the largePackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLargePackage(Boolean value) {
        this.largePackage = value;
    }

    /**
     * Gets the value of the largePackageFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getLargePackageFee() {
        return largePackageFee;
    }

    /**
     * Sets the value of the largePackageFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setLargePackageFee(Money value) {
        this.largePackageFee = value;
    }

    /**
     * Gets the value of the latestDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLatestDeliveryDate() {
        return latestDeliveryDate;
    }

    /**
     * Sets the value of the latestDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLatestDeliveryDate(XMLGregorianCalendar value) {
        this.latestDeliveryDate = value;
    }

    /**
     * Gets the value of the latestDeliveryTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLatestDeliveryTime() {
        return latestDeliveryTime;
    }

    /**
     * Sets the value of the latestDeliveryTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLatestDeliveryTime(XMLGregorianCalendar value) {
        this.latestDeliveryTime = value;
    }

    /**
     * Gets the value of the liftgateDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLiftgateDelivery() {
        return liftgateDelivery;
    }

    /**
     * Sets the value of the liftgateDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLiftgateDelivery(Boolean value) {
        this.liftgateDelivery = value;
    }

    /**
     * Gets the value of the liftgateDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getLiftgateDeliveryFee() {
        return liftgateDeliveryFee;
    }

    /**
     * Sets the value of the liftgateDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setLiftgateDeliveryFee(Money value) {
        this.liftgateDeliveryFee = value;
    }

    /**
     * Gets the value of the liftgatePickup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLiftgatePickup() {
        return liftgatePickup;
    }

    /**
     * Sets the value of the liftgatePickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLiftgatePickup(Boolean value) {
        this.liftgatePickup = value;
    }

    /**
     * Gets the value of the liftgatePickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getLiftgatePickupFee() {
        return liftgatePickupFee;
    }

    /**
     * Sets the value of the liftgatePickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setLiftgatePickupFee(Money value) {
        this.liftgatePickupFee = value;
    }

    /**
     * Gets the value of the locationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * Sets the value of the locationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationId(String value) {
        this.locationId = value;
    }

    /**
     * Gets the value of the markings property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarkings() {
        return markings;
    }

    /**
     * Sets the value of the markings property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarkings(String value) {
        this.markings = value;
    }

    /**
     * Gets the value of the maxicode property.
     * 
     * @return
     *     possible object is
     *     {@link StringList }
     *     
     */
    public StringList getMaxicode() {
        return maxicode;
    }

    /**
     * Sets the value of the maxicode property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringList }
     *     
     */
    public void setMaxicode(StringList value) {
        this.maxicode = value;
    }

    /**
     * Gets the value of the miscReference1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiscReference1() {
        return miscReference1;
    }

    /**
     * Sets the value of the miscReference1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiscReference1(String value) {
        this.miscReference1 = value;
    }

    /**
     * Gets the value of the miscReference2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiscReference2() {
        return miscReference2;
    }

    /**
     * Sets the value of the miscReference2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiscReference2(String value) {
        this.miscReference2 = value;
    }

    /**
     * Gets the value of the miscReference3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiscReference3() {
        return miscReference3;
    }

    /**
     * Sets the value of the miscReference3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiscReference3(String value) {
        this.miscReference3 = value;
    }

    /**
     * Gets the value of the miscReference4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiscReference4() {
        return miscReference4;
    }

    /**
     * Sets the value of the miscReference4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiscReference4(String value) {
        this.miscReference4 = value;
    }

    /**
     * Gets the value of the miscReference5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiscReference5() {
        return miscReference5;
    }

    /**
     * Sets the value of the miscReference5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiscReference5(String value) {
        this.miscReference5 = value;
    }

    /**
     * Gets the value of the mms property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMms() {
        return mms;
    }

    /**
     * Sets the value of the mms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMms(Boolean value) {
        this.mms = value;
    }

    /**
     * Gets the value of the mmsPieceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMmsPieceId() {
        return mmsPieceId;
    }

    /**
     * Sets the value of the mmsPieceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMmsPieceId(String value) {
        this.mmsPieceId = value;
    }

    /**
     * Gets the value of the msn property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMsn() {
        return msn;
    }

    /**
     * Sets the value of the msn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMsn(Integer value) {
        this.msn = value;
    }

    /**
     * Gets the value of the multiPieceFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getMultiPieceFee() {
        return multiPieceFee;
    }

    /**
     * Sets the value of the multiPieceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setMultiPieceFee(Money value) {
        this.multiPieceFee = value;
    }

    /**
     * Gets the value of the neutralDeliveryService property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNeutralDeliveryService() {
        return neutralDeliveryService;
    }

    /**
     * Sets the value of the neutralDeliveryService property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeutralDeliveryService(Boolean value) {
        this.neutralDeliveryService = value;
    }

    /**
     * Gets the value of the neutralDeliveryServiceFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getNeutralDeliveryServiceFee() {
        return neutralDeliveryServiceFee;
    }

    /**
     * Sets the value of the neutralDeliveryServiceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setNeutralDeliveryServiceFee(Money value) {
        this.neutralDeliveryServiceFee = value;
    }

    /**
     * Gets the value of the nofnSequence property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNofnSequence() {
        return nofnSequence;
    }

    /**
     * Sets the value of the nofnSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNofnSequence(Integer value) {
        this.nofnSequence = value;
    }

    /**
     * Gets the value of the nofnSequenceBundle property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNofnSequenceBundle() {
        return nofnSequenceBundle;
    }

    /**
     * Sets the value of the nofnSequenceBundle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNofnSequenceBundle(Integer value) {
        this.nofnSequenceBundle = value;
    }

    /**
     * Gets the value of the nofnTotal property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNofnTotal() {
        return nofnTotal;
    }

    /**
     * Sets the value of the nofnTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNofnTotal(Integer value) {
        this.nofnTotal = value;
    }

    /**
     * Gets the value of the nofnTotalBundle property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNofnTotalBundle() {
        return nofnTotalBundle;
    }

    /**
     * Sets the value of the nofnTotalBundle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNofnTotalBundle(Integer value) {
        this.nofnTotalBundle = value;
    }

    /**
     * Gets the value of the nonhazardousLithiumBatteries property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNonhazardousLithiumBatteries() {
        return nonhazardousLithiumBatteries;
    }

    /**
     * Sets the value of the nonhazardousLithiumBatteries property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNonhazardousLithiumBatteries(Boolean value) {
        this.nonhazardousLithiumBatteries = value;
    }

    /**
     * Gets the value of the nonmachinableMail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNonmachinableMail() {
        return nonmachinableMail;
    }

    /**
     * Sets the value of the nonmachinableMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNonmachinableMail(Boolean value) {
        this.nonmachinableMail = value;
    }

    /**
     * Gets the value of the nonmachinableMailFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getNonmachinableMailFee() {
        return nonmachinableMailFee;
    }

    /**
     * Sets the value of the nonmachinableMailFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setNonmachinableMailFee(Money value) {
        this.nonmachinableMailFee = value;
    }

    /**
     * Gets the value of the nonrectangular property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNonrectangular() {
        return nonrectangular;
    }

    /**
     * Sets the value of the nonrectangular property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNonrectangular(Boolean value) {
        this.nonrectangular = value;
    }

    /**
     * Gets the value of the nonstandardMail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNonstandardMail() {
        return nonstandardMail;
    }

    /**
     * Sets the value of the nonstandardMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNonstandardMail(Boolean value) {
        this.nonstandardMail = value;
    }

    /**
     * Gets the value of the nonstandardMailFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getNonstandardMailFee() {
        return nonstandardMailFee;
    }

    /**
     * Sets the value of the nonstandardMailFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setNonstandardMailFee(Money value) {
        this.nonstandardMailFee = value;
    }

    /**
     * Gets the value of the notificationFailureAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotificationFailureAddressEmail() {
        return notificationFailureAddressEmail;
    }

    /**
     * Sets the value of the notificationFailureAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotificationFailureAddressEmail(String value) {
        this.notificationFailureAddressEmail = value;
    }

    /**
     * Gets the value of the notFlatMachinable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNotFlatMachinable() {
        return notFlatMachinable;
    }

    /**
     * Sets the value of the notFlatMachinable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNotFlatMachinable(Boolean value) {
        this.notFlatMachinable = value;
    }

    /**
     * Gets the value of the offshoreFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getOffshoreFee() {
        return offshoreFee;
    }

    /**
     * Sets the value of the offshoreFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setOffshoreFee(Money value) {
        this.offshoreFee = value;
    }

    /**
     * Gets the value of the originatorTrackingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginatorTrackingNumber() {
        return originatorTrackingNumber;
    }

    /**
     * Sets the value of the originatorTrackingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginatorTrackingNumber(String value) {
        this.originatorTrackingNumber = value;
    }

    /**
     * Gets the value of the originAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getOriginAddress() {
        return originAddress;
    }

    /**
     * Sets the value of the originAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setOriginAddress(NameAddress value) {
        this.originAddress = value;
    }

    /**
     * Gets the value of the oversize property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOversize() {
        return oversize;
    }

    /**
     * Sets the value of the oversize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOversize(Boolean value) {
        this.oversize = value;
    }

    /**
     * Gets the value of the oversizeFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getOversizeFee() {
        return oversizeFee;
    }

    /**
     * Sets the value of the oversizeFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setOversizeFee(Money value) {
        this.oversizeFee = value;
    }

    /**
     * Gets the value of the overDimensionFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getOverDimensionFee() {
        return overDimensionFee;
    }

    /**
     * Sets the value of the overDimensionFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setOverDimensionFee(Money value) {
        this.overDimensionFee = value;
    }

    /**
     * Gets the value of the packageListId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPackageListId() {
        return packageListId;
    }

    /**
     * Sets the value of the packageListId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPackageListId(Integer value) {
        this.packageListId = value;
    }

    /**
     * Gets the value of the packaging property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackaging() {
        return packaging;
    }

    /**
     * Sets the value of the packaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackaging(String value) {
        this.packaging = value;
    }

    /**
     * Gets the value of the packagingDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackagingDescription() {
        return packagingDescription;
    }

    /**
     * Sets the value of the packagingDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackagingDescription(String value) {
        this.packagingDescription = value;
    }

    /**
     * Gets the value of the packagingTareWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getPackagingTareWeight() {
        return packagingTareWeight;
    }

    /**
     * Sets the value of the packagingTareWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setPackagingTareWeight(Weight value) {
        this.packagingTareWeight = value;
    }

    /**
     * Gets the value of the palletFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getPalletFee() {
        return palletFee;
    }

    /**
     * Sets the value of the palletFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setPalletFee(Money value) {
        this.palletFee = value;
    }

    /**
     * Gets the value of the palletJackDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPalletJackDelivery() {
        return palletJackDelivery;
    }

    /**
     * Sets the value of the palletJackDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPalletJackDelivery(Boolean value) {
        this.palletJackDelivery = value;
    }

    /**
     * Gets the value of the palletJackDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getPalletJackDeliveryFee() {
        return palletJackDeliveryFee;
    }

    /**
     * Sets the value of the palletJackDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setPalletJackDeliveryFee(Money value) {
        this.palletJackDeliveryFee = value;
    }

    /**
     * Gets the value of the palletJackPickup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPalletJackPickup() {
        return palletJackPickup;
    }

    /**
     * Sets the value of the palletJackPickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPalletJackPickup(Boolean value) {
        this.palletJackPickup = value;
    }

    /**
     * Gets the value of the palletJackPickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getPalletJackPickupFee() {
        return palletJackPickupFee;
    }

    /**
     * Sets the value of the palletJackPickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setPalletJackPickupFee(Money value) {
        this.palletJackPickupFee = value;
    }

    /**
     * Gets the value of the parcelAirlift property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isParcelAirlift() {
        return parcelAirlift;
    }

    /**
     * Sets the value of the parcelAirlift property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setParcelAirlift(Boolean value) {
        this.parcelAirlift = value;
    }

    /**
     * Gets the value of the parcelAirliftFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getParcelAirliftFee() {
        return parcelAirliftFee;
    }

    /**
     * Sets the value of the parcelAirliftFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setParcelAirliftFee(Money value) {
        this.parcelAirliftFee = value;
    }

    /**
     * Gets the value of the parentContainer property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionary }
     *     
     */
    public DataDictionary getParentContainer() {
        return parentContainer;
    }

    /**
     * Sets the value of the parentContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionary }
     *     
     */
    public void setParentContainer(DataDictionary value) {
        this.parentContainer = value;
    }

    /**
     * Gets the value of the parentContainerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentContainerCode() {
        return parentContainerCode;
    }

    /**
     * Sets the value of the parentContainerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentContainerCode(String value) {
        this.parentContainerCode = value;
    }

    /**
     * Gets the value of the partiesRelated property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPartiesRelated() {
        return partiesRelated;
    }

    /**
     * Sets the value of the partiesRelated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPartiesRelated(Boolean value) {
        this.partiesRelated = value;
    }

    /**
     * Gets the value of the perishable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPerishable() {
        return perishable;
    }

    /**
     * Sets the value of the perishable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPerishable(Boolean value) {
        this.perishable = value;
    }

    /**
     * Gets the value of the pieceCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPieceCount() {
        return pieceCount;
    }

    /**
     * Sets the value of the pieceCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPieceCount(Integer value) {
        this.pieceCount = value;
    }

    /**
     * Gets the value of the pieceCountFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getPieceCountFee() {
        return pieceCountFee;
    }

    /**
     * Sets the value of the pieceCountFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setPieceCountFee(Money value) {
        this.pieceCountFee = value;
    }

    /**
     * Gets the value of the portOfEntry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortOfEntry() {
        return portOfEntry;
    }

    /**
     * Sets the value of the portOfEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortOfEntry(String value) {
        this.portOfEntry = value;
    }

    /**
     * Gets the value of the preAlertNotificationAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getPreAlertNotificationAddress() {
        return preAlertNotificationAddress;
    }

    /**
     * Sets the value of the preAlertNotificationAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setPreAlertNotificationAddress(NameAddress value) {
        this.preAlertNotificationAddress = value;
    }

    /**
     * Gets the value of the preAlertNotificationAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreAlertNotificationAddressEmail() {
        return preAlertNotificationAddressEmail;
    }

    /**
     * Sets the value of the preAlertNotificationAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreAlertNotificationAddressEmail(String value) {
        this.preAlertNotificationAddressEmail = value;
    }

    /**
     * Gets the value of the preAlertNotificationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreAlertNotificationDescription() {
        return preAlertNotificationDescription;
    }

    /**
     * Sets the value of the preAlertNotificationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreAlertNotificationDescription(String value) {
        this.preAlertNotificationDescription = value;
    }

    /**
     * Gets the value of the preAlertNotificationEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreAlertNotificationEmail() {
        return preAlertNotificationEmail;
    }

    /**
     * Sets the value of the preAlertNotificationEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreAlertNotificationEmail(Boolean value) {
        this.preAlertNotificationEmail = value;
    }

    /**
     * Gets the value of the preAlertNotificationLocale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreAlertNotificationLocale() {
        return preAlertNotificationLocale;
    }

    /**
     * Sets the value of the preAlertNotificationLocale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreAlertNotificationLocale(String value) {
        this.preAlertNotificationLocale = value;
    }

    /**
     * Gets the value of the preAlertNotificationPhone property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreAlertNotificationPhone() {
        return preAlertNotificationPhone;
    }

    /**
     * Sets the value of the preAlertNotificationPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreAlertNotificationPhone(Boolean value) {
        this.preAlertNotificationPhone = value;
    }

    /**
     * Gets the value of the preAlertNotificationSenderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreAlertNotificationSenderName() {
        return preAlertNotificationSenderName;
    }

    /**
     * Sets the value of the preAlertNotificationSenderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreAlertNotificationSenderName(String value) {
        this.preAlertNotificationSenderName = value;
    }

    /**
     * Gets the value of the preAlertNotificationSms property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreAlertNotificationSms() {
        return preAlertNotificationSms;
    }

    /**
     * Sets the value of the preAlertNotificationSms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreAlertNotificationSms(Boolean value) {
        this.preAlertNotificationSms = value;
    }

    /**
     * Gets the value of the preAlertNotificationSubjectText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreAlertNotificationSubjectText() {
        return preAlertNotificationSubjectText;
    }

    /**
     * Sets the value of the preAlertNotificationSubjectText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreAlertNotificationSubjectText(String value) {
        this.preAlertNotificationSubjectText = value;
    }

    /**
     * Gets the value of the priorDeliveryNotificationConsignee property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPriorDeliveryNotificationConsignee() {
        return priorDeliveryNotificationConsignee;
    }

    /**
     * Sets the value of the priorDeliveryNotificationConsignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPriorDeliveryNotificationConsignee(Boolean value) {
        this.priorDeliveryNotificationConsignee = value;
    }

    /**
     * Gets the value of the priorDeliveryNotificationFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getPriorDeliveryNotificationFee() {
        return priorDeliveryNotificationFee;
    }

    /**
     * Sets the value of the priorDeliveryNotificationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setPriorDeliveryNotificationFee(Money value) {
        this.priorDeliveryNotificationFee = value;
    }

    /**
     * Gets the value of the proof property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProof() {
        return proof;
    }

    /**
     * Sets the value of the proof property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProof(Boolean value) {
        this.proof = value;
    }

    /**
     * Gets the value of the proofFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getProofFee() {
        return proofFee;
    }

    /**
     * Sets the value of the proofFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setProofFee(Money value) {
        this.proofFee = value;
    }

    /**
     * Gets the value of the proofNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProofNumber() {
        return proofNumber;
    }

    /**
     * Sets the value of the proofNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProofNumber(String value) {
        this.proofNumber = value;
    }

    /**
     * Gets the value of the proofRequireSignature property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProofRequireSignature() {
        return proofRequireSignature;
    }

    /**
     * Sets the value of the proofRequireSignature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProofRequireSignature(Boolean value) {
        this.proofRequireSignature = value;
    }

    /**
     * Gets the value of the proofRequireSignatureAdult property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProofRequireSignatureAdult() {
        return proofRequireSignatureAdult;
    }

    /**
     * Sets the value of the proofRequireSignatureAdult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProofRequireSignatureAdult(Boolean value) {
        this.proofRequireSignatureAdult = value;
    }

    /**
     * Gets the value of the proofRequireSignatureConsignee property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProofRequireSignatureConsignee() {
        return proofRequireSignatureConsignee;
    }

    /**
     * Sets the value of the proofRequireSignatureConsignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProofRequireSignatureConsignee(Boolean value) {
        this.proofRequireSignatureConsignee = value;
    }

    /**
     * Gets the value of the proofRequireSignatureFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getProofRequireSignatureFee() {
        return proofRequireSignatureFee;
    }

    /**
     * Sets the value of the proofRequireSignatureFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setProofRequireSignatureFee(Money value) {
        this.proofRequireSignatureFee = value;
    }

    /**
     * Gets the value of the proofReturnOfDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProofReturnOfDocuments() {
        return proofReturnOfDocuments;
    }

    /**
     * Sets the value of the proofReturnOfDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProofReturnOfDocuments(Boolean value) {
        this.proofReturnOfDocuments = value;
    }

    /**
     * Gets the value of the proofSignatureWaiver property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProofSignatureWaiver() {
        return proofSignatureWaiver;
    }

    /**
     * Sets the value of the proofSignatureWaiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProofSignatureWaiver(Boolean value) {
        this.proofSignatureWaiver = value;
    }

    /**
     * Gets the value of the proofUseAlternateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProofUseAlternateNumber() {
        return proofUseAlternateNumber;
    }

    /**
     * Sets the value of the proofUseAlternateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProofUseAlternateNumber(Boolean value) {
        this.proofUseAlternateNumber = value;
    }

    /**
     * Gets the value of the ratedWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getRatedWeight() {
        return ratedWeight;
    }

    /**
     * Sets the value of the ratedWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setRatedWeight(Weight value) {
        this.ratedWeight = value;
    }

    /**
     * Gets the value of the registeredMail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRegisteredMail() {
        return registeredMail;
    }

    /**
     * Sets the value of the registeredMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRegisteredMail(Boolean value) {
        this.registeredMail = value;
    }

    /**
     * Gets the value of the registeredMailFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getRegisteredMailFee() {
        return registeredMailFee;
    }

    /**
     * Sets the value of the registeredMailFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setRegisteredMailFee(Money value) {
        this.registeredMailFee = value;
    }

    /**
     * Gets the value of the remoteOriginFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getRemoteOriginFee() {
        return remoteOriginFee;
    }

    /**
     * Sets the value of the remoteOriginFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setRemoteOriginFee(Money value) {
        this.remoteOriginFee = value;
    }

    /**
     * Gets the value of the residentialDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getResidentialDeliveryFee() {
        return residentialDeliveryFee;
    }

    /**
     * Sets the value of the residentialDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setResidentialDeliveryFee(Money value) {
        this.residentialDeliveryFee = value;
    }

    /**
     * Gets the value of the returnAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnAccount() {
        return returnAccount;
    }

    /**
     * Sets the value of the returnAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnAccount(String value) {
        this.returnAccount = value;
    }

    /**
     * Gets the value of the returnAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getReturnAddress() {
        return returnAddress;
    }

    /**
     * Sets the value of the returnAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setReturnAddress(NameAddress value) {
        this.returnAddress = value;
    }

    /**
     * Gets the value of the returnAddressMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnAddressMethod() {
        return returnAddressMethod;
    }

    /**
     * Sets the value of the returnAddressMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnAddressMethod(String value) {
        this.returnAddressMethod = value;
    }

    /**
     * Gets the value of the returnDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReturnDelivery() {
        return returnDelivery;
    }

    /**
     * Sets the value of the returnDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnDelivery(Boolean value) {
        this.returnDelivery = value;
    }

    /**
     * Gets the value of the returnDeliveryAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDeliveryAddressEmail() {
        return returnDeliveryAddressEmail;
    }

    /**
     * Sets the value of the returnDeliveryAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDeliveryAddressEmail(String value) {
        this.returnDeliveryAddressEmail = value;
    }

    /**
     * Gets the value of the returnDeliveryAddressEmailLocale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDeliveryAddressEmailLocale() {
        return returnDeliveryAddressEmailLocale;
    }

    /**
     * Sets the value of the returnDeliveryAddressEmailLocale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDeliveryAddressEmailLocale(String value) {
        this.returnDeliveryAddressEmailLocale = value;
    }

    /**
     * Gets the value of the returnDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getReturnDeliveryFee() {
        return returnDeliveryFee;
    }

    /**
     * Sets the value of the returnDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setReturnDeliveryFee(Money value) {
        this.returnDeliveryFee = value;
    }

    /**
     * Gets the value of the returnDeliveryMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDeliveryMethod() {
        return returnDeliveryMethod;
    }

    /**
     * Sets the value of the returnDeliveryMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDeliveryMethod(String value) {
        this.returnDeliveryMethod = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getReturnDeliveryNotificationAddress() {
        return returnDeliveryNotificationAddress;
    }

    /**
     * Sets the value of the returnDeliveryNotificationAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setReturnDeliveryNotificationAddress(NameAddress value) {
        this.returnDeliveryNotificationAddress = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationAddress2 property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getReturnDeliveryNotificationAddress2() {
        return returnDeliveryNotificationAddress2;
    }

    /**
     * Sets the value of the returnDeliveryNotificationAddress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setReturnDeliveryNotificationAddress2(NameAddress value) {
        this.returnDeliveryNotificationAddress2 = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDeliveryNotificationAddressEmail() {
        return returnDeliveryNotificationAddressEmail;
    }

    /**
     * Sets the value of the returnDeliveryNotificationAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDeliveryNotificationAddressEmail(String value) {
        this.returnDeliveryNotificationAddressEmail = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDeliveryNotificationDescription() {
        return returnDeliveryNotificationDescription;
    }

    /**
     * Sets the value of the returnDeliveryNotificationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDeliveryNotificationDescription(String value) {
        this.returnDeliveryNotificationDescription = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReturnDeliveryNotificationEmail() {
        return returnDeliveryNotificationEmail;
    }

    /**
     * Sets the value of the returnDeliveryNotificationEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnDeliveryNotificationEmail(Boolean value) {
        this.returnDeliveryNotificationEmail = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationFax property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReturnDeliveryNotificationFax() {
        return returnDeliveryNotificationFax;
    }

    /**
     * Sets the value of the returnDeliveryNotificationFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnDeliveryNotificationFax(Boolean value) {
        this.returnDeliveryNotificationFax = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getReturnDeliveryNotificationFee() {
        return returnDeliveryNotificationFee;
    }

    /**
     * Sets the value of the returnDeliveryNotificationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setReturnDeliveryNotificationFee(Money value) {
        this.returnDeliveryNotificationFee = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationSenderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDeliveryNotificationSenderName() {
        return returnDeliveryNotificationSenderName;
    }

    /**
     * Sets the value of the returnDeliveryNotificationSenderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDeliveryNotificationSenderName(String value) {
        this.returnDeliveryNotificationSenderName = value;
    }

    /**
     * Gets the value of the returnDeliveryNotificationSubjectText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnDeliveryNotificationSubjectText() {
        return returnDeliveryNotificationSubjectText;
    }

    /**
     * Sets the value of the returnDeliveryNotificationSubjectText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnDeliveryNotificationSubjectText(String value) {
        this.returnDeliveryNotificationSubjectText = value;
    }

    /**
     * Gets the value of the routedExportTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRoutedExportTransaction() {
        return routedExportTransaction;
    }

    /**
     * Sets the value of the routedExportTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRoutedExportTransaction(Boolean value) {
        this.routedExportTransaction = value;
    }

    /**
     * Gets the value of the routingCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingCode() {
        return routingCode;
    }

    /**
     * Sets the value of the routingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingCode(String value) {
        this.routingCode = value;
    }

    /**
     * Gets the value of the routingCode2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingCode2() {
        return routingCode2;
    }

    /**
     * Sets the value of the routingCode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingCode2(String value) {
        this.routingCode2 = value;
    }

    /**
     * Gets the value of the routingCode3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingCode3() {
        return routingCode3;
    }

    /**
     * Sets the value of the routingCode3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingCode3(String value) {
        this.routingCode3 = value;
    }

    /**
     * Gets the value of the routingCode4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingCode4() {
        return routingCode4;
    }

    /**
     * Sets the value of the routingCode4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingCode4(String value) {
        this.routingCode4 = value;
    }

    /**
     * Gets the value of the routingCode5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingCode5() {
        return routingCode5;
    }

    /**
     * Sets the value of the routingCode5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingCode5(String value) {
        this.routingCode5 = value;
    }

    /**
     * Gets the value of the sackLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSackLevel() {
        return sackLevel;
    }

    /**
     * Sets the value of the sackLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSackLevel(String value) {
        this.sackLevel = value;
    }

    /**
     * Gets the value of the sackZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSackZip() {
        return sackZip;
    }

    /**
     * Sets the value of the sackZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSackZip(String value) {
        this.sackZip = value;
    }

    /**
     * Gets the value of the saturdayDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSaturdayDelivery() {
        return saturdayDelivery;
    }

    /**
     * Sets the value of the saturdayDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSaturdayDelivery(Boolean value) {
        this.saturdayDelivery = value;
    }

    /**
     * Gets the value of the saturdayDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getSaturdayDeliveryFee() {
        return saturdayDeliveryFee;
    }

    /**
     * Sets the value of the saturdayDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setSaturdayDeliveryFee(Money value) {
        this.saturdayDeliveryFee = value;
    }

    /**
     * Gets the value of the saturdayPickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getSaturdayPickupFee() {
        return saturdayPickupFee;
    }

    /**
     * Sets the value of the saturdayPickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setSaturdayPickupFee(Money value) {
        this.saturdayPickupFee = value;
    }

    /**
     * Gets the value of the security property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSecurity() {
        return security;
    }

    /**
     * Sets the value of the security property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSecurity(Boolean value) {
        this.security = value;
    }

    /**
     * Gets the value of the securityFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getSecurityFee() {
        return securityFee;
    }

    /**
     * Sets the value of the securityFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setSecurityFee(Money value) {
        this.securityFee = value;
    }

    /**
     * Gets the value of the sedExemptionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSedExemptionNumber() {
        return sedExemptionNumber;
    }

    /**
     * Sets the value of the sedExemptionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSedExemptionNumber(String value) {
        this.sedExemptionNumber = value;
    }

    /**
     * Gets the value of the sedMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSedMethod() {
        return sedMethod;
    }

    /**
     * Sets the value of the sedMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSedMethod(String value) {
        this.sedMethod = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setService(String value) {
        this.service = value;
    }

    /**
     * Gets the value of the shipdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getShipdate() {
        return shipdate;
    }

    /**
     * Sets the value of the shipdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setShipdate(XMLGregorianCalendar value) {
        this.shipdate = value;
    }

    /**
     * Gets the value of the shipper property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipper() {
        return shipper;
    }

    /**
     * Sets the value of the shipper property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipper(String value) {
        this.shipper = value;
    }

    /**
     * Gets the value of the shipperReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperReference() {
        return shipperReference;
    }

    /**
     * Sets the value of the shipperReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperReference(String value) {
        this.shipperReference = value;
    }

    /**
     * Gets the value of the shipNotificationAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getShipNotificationAddress() {
        return shipNotificationAddress;
    }

    /**
     * Sets the value of the shipNotificationAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setShipNotificationAddress(NameAddress value) {
        this.shipNotificationAddress = value;
    }

    /**
     * Gets the value of the shipNotificationAddress2 property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getShipNotificationAddress2() {
        return shipNotificationAddress2;
    }

    /**
     * Sets the value of the shipNotificationAddress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setShipNotificationAddress2(NameAddress value) {
        this.shipNotificationAddress2 = value;
    }

    /**
     * Gets the value of the shipNotificationAddressEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipNotificationAddressEmail() {
        return shipNotificationAddressEmail;
    }

    /**
     * Sets the value of the shipNotificationAddressEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipNotificationAddressEmail(String value) {
        this.shipNotificationAddressEmail = value;
    }

    /**
     * Gets the value of the shipNotificationAddressFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipNotificationAddressFax() {
        return shipNotificationAddressFax;
    }

    /**
     * Sets the value of the shipNotificationAddressFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipNotificationAddressFax(String value) {
        this.shipNotificationAddressFax = value;
    }

    /**
     * Gets the value of the shipNotificationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipNotificationDescription() {
        return shipNotificationDescription;
    }

    /**
     * Sets the value of the shipNotificationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipNotificationDescription(String value) {
        this.shipNotificationDescription = value;
    }

    /**
     * Gets the value of the shipNotificationEmail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShipNotificationEmail() {
        return shipNotificationEmail;
    }

    /**
     * Sets the value of the shipNotificationEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShipNotificationEmail(Boolean value) {
        this.shipNotificationEmail = value;
    }

    /**
     * Gets the value of the shipNotificationFax property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShipNotificationFax() {
        return shipNotificationFax;
    }

    /**
     * Sets the value of the shipNotificationFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShipNotificationFax(Boolean value) {
        this.shipNotificationFax = value;
    }

    /**
     * Gets the value of the shipNotificationFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getShipNotificationFee() {
        return shipNotificationFee;
    }

    /**
     * Sets the value of the shipNotificationFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setShipNotificationFee(Money value) {
        this.shipNotificationFee = value;
    }

    /**
     * Gets the value of the shipNotificationSenderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipNotificationSenderName() {
        return shipNotificationSenderName;
    }

    /**
     * Sets the value of the shipNotificationSenderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipNotificationSenderName(String value) {
        this.shipNotificationSenderName = value;
    }

    /**
     * Gets the value of the shipNotificationSubjectText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipNotificationSubjectText() {
        return shipNotificationSubjectText;
    }

    /**
     * Sets the value of the shipNotificationSubjectText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipNotificationSubjectText(String value) {
        this.shipNotificationSubjectText = value;
    }

    /**
     * Gets the value of the shipNotificationVerbal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShipNotificationVerbal() {
        return shipNotificationVerbal;
    }

    /**
     * Sets the value of the shipNotificationVerbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShipNotificationVerbal(Boolean value) {
        this.shipNotificationVerbal = value;
    }

    /**
     * Gets the value of the signatureRelease property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSignatureRelease() {
        return signatureRelease;
    }

    /**
     * Sets the value of the signatureRelease property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSignatureRelease(Boolean value) {
        this.signatureRelease = value;
    }

    /**
     * Gets the value of the special property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getSpecial() {
        return special;
    }

    /**
     * Sets the value of the special property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setSpecial(Money value) {
        this.special = value;
    }

    /**
     * Gets the value of the specialDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSpecialDelivery() {
        return specialDelivery;
    }

    /**
     * Sets the value of the specialDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSpecialDelivery(Boolean value) {
        this.specialDelivery = value;
    }

    /**
     * Gets the value of the specialDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getSpecialDeliveryFee() {
        return specialDeliveryFee;
    }

    /**
     * Sets the value of the specialDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setSpecialDeliveryFee(Money value) {
        this.specialDeliveryFee = value;
    }

    /**
     * Gets the value of the stairDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStairDelivery() {
        return stairDelivery;
    }

    /**
     * Sets the value of the stairDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStairDelivery(Boolean value) {
        this.stairDelivery = value;
    }

    /**
     * Gets the value of the stairDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getStairDeliveryFee() {
        return stairDeliveryFee;
    }

    /**
     * Sets the value of the stairDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setStairDeliveryFee(Money value) {
        this.stairDeliveryFee = value;
    }

    /**
     * Gets the value of the stairPickup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStairPickup() {
        return stairPickup;
    }

    /**
     * Sets the value of the stairPickup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStairPickup(Boolean value) {
        this.stairPickup = value;
    }

    /**
     * Gets the value of the stairPickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getStairPickupFee() {
        return stairPickupFee;
    }

    /**
     * Sets the value of the stairPickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setStairPickupFee(Money value) {
        this.stairPickupFee = value;
    }

    /**
     * Gets the value of the subcategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * Sets the value of the subcategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubcategory(String value) {
        this.subcategory = value;
    }

    /**
     * Gets the value of the subNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubNumber() {
        return subNumber;
    }

    /**
     * Sets the value of the subNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubNumber(String value) {
        this.subNumber = value;
    }

    /**
     * Gets the value of the sundayDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSundayDelivery() {
        return sundayDelivery;
    }

    /**
     * Sets the value of the sundayDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSundayDelivery(Boolean value) {
        this.sundayDelivery = value;
    }

    /**
     * Gets the value of the sundayDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getSundayDeliveryFee() {
        return sundayDeliveryFee;
    }

    /**
     * Sets the value of the sundayDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setSundayDeliveryFee(Money value) {
        this.sundayDeliveryFee = value;
    }

    /**
     * Gets the value of the sundayPickupFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getSundayPickupFee() {
        return sundayPickupFee;
    }

    /**
     * Sets the value of the sundayPickupFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setSundayPickupFee(Money value) {
        this.sundayPickupFee = value;
    }

    /**
     * Gets the value of the suppressDc property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuppressDc() {
        return suppressDc;
    }

    /**
     * Sets the value of the suppressDc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuppressDc(Boolean value) {
        this.suppressDc = value;
    }

    /**
     * Gets the value of the suppressMms property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuppressMms() {
        return suppressMms;
    }

    /**
     * Sets the value of the suppressMms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuppressMms(Boolean value) {
        this.suppressMms = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setTax(Money value) {
        this.tax = value;
    }

    /**
     * Gets the value of the temperatureControl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemperatureControl() {
        return temperatureControl;
    }

    /**
     * Sets the value of the temperatureControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemperatureControl(String value) {
        this.temperatureControl = value;
    }

    /**
     * Gets the value of the temperatureControlFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getTemperatureControlFee() {
        return temperatureControlFee;
    }

    /**
     * Sets the value of the temperatureControlFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setTemperatureControlFee(Money value) {
        this.temperatureControlFee = value;
    }

    /**
     * Gets the value of the terminalHandlingFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getTerminalHandlingFee() {
        return terminalHandlingFee;
    }

    /**
     * Sets the value of the terminalHandlingFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setTerminalHandlingFee(Money value) {
        this.terminalHandlingFee = value;
    }

    /**
     * Gets the value of the terms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerms() {
        return terms;
    }

    /**
     * Sets the value of the terms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerms(String value) {
        this.terms = value;
    }

    /**
     * Gets the value of the termsOfSale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermsOfSale() {
        return termsOfSale;
    }

    /**
     * Sets the value of the termsOfSale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermsOfSale(String value) {
        this.termsOfSale = value;
    }

    /**
     * Gets the value of the thirdPartyBilling property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isThirdPartyBilling() {
        return thirdPartyBilling;
    }

    /**
     * Sets the value of the thirdPartyBilling property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setThirdPartyBilling(Boolean value) {
        this.thirdPartyBilling = value;
    }

    /**
     * Gets the value of the thirdPartyBillingAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyBillingAccount() {
        return thirdPartyBillingAccount;
    }

    /**
     * Sets the value of the thirdPartyBillingAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyBillingAccount(String value) {
        this.thirdPartyBillingAccount = value;
    }

    /**
     * Gets the value of the thirdPartyBillingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getThirdPartyBillingAddress() {
        return thirdPartyBillingAddress;
    }

    /**
     * Sets the value of the thirdPartyBillingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setThirdPartyBillingAddress(NameAddress value) {
        this.thirdPartyBillingAddress = value;
    }

    /**
     * Gets the value of the timeInTransit property.
     * 
     * @return
     *     possible object is
     *     {@link Commitment }
     *     
     */
    public Commitment getTimeInTransit() {
        return timeInTransit;
    }

    /**
     * Sets the value of the timeInTransit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Commitment }
     *     
     */
    public void setTimeInTransit(Commitment value) {
        this.timeInTransit = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setTotal(Money value) {
        this.total = value;
    }

    /**
     * Gets the value of the trackingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * Sets the value of the trackingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingNumber(String value) {
        this.trackingNumber = value;
    }

    /**
     * Gets the value of the trackingNumber2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingNumber2() {
        return trackingNumber2;
    }

    /**
     * Sets the value of the trackingNumber2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingNumber2(String value) {
        this.trackingNumber2 = value;
    }

    /**
     * Gets the value of the ultimateConsignee property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getUltimateConsignee() {
        return ultimateConsignee;
    }

    /**
     * Sets the value of the ultimateConsignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setUltimateConsignee(NameAddress value) {
        this.ultimateConsignee = value;
    }

    /**
     * Gets the value of the ultimateDestinationCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUltimateDestinationCountry() {
        return ultimateDestinationCountry;
    }

    /**
     * Sets the value of the ultimateDestinationCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUltimateDestinationCountry(String value) {
        this.ultimateDestinationCountry = value;
    }

    /**
     * Gets the value of the unpack property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUnpack() {
        return unpack;
    }

    /**
     * Sets the value of the unpack property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUnpack(Boolean value) {
        this.unpack = value;
    }

    /**
     * Gets the value of the unpackFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getUnpackFee() {
        return unpackFee;
    }

    /**
     * Sets the value of the unpackFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setUnpackFee(Money value) {
        this.unpackFee = value;
    }

    /**
     * Gets the value of the urbanDeliveryFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getUrbanDeliveryFee() {
        return urbanDeliveryFee;
    }

    /**
     * Sets the value of the urbanDeliveryFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setUrbanDeliveryFee(Money value) {
        this.urbanDeliveryFee = value;
    }

    /**
     * Gets the value of the userData1 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData1() {
        return userData1;
    }

    /**
     * Sets the value of the userData1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData1(Object value) {
        this.userData1 = value;
    }

    /**
     * Gets the value of the userData2 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData2() {
        return userData2;
    }

    /**
     * Sets the value of the userData2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData2(Object value) {
        this.userData2 = value;
    }

    /**
     * Gets the value of the userData3 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData3() {
        return userData3;
    }

    /**
     * Sets the value of the userData3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData3(Object value) {
        this.userData3 = value;
    }

    /**
     * Gets the value of the userData4 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData4() {
        return userData4;
    }

    /**
     * Sets the value of the userData4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData4(Object value) {
        this.userData4 = value;
    }

    /**
     * Gets the value of the userData5 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData5() {
        return userData5;
    }

    /**
     * Sets the value of the userData5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData5(Object value) {
        this.userData5 = value;
    }

    /**
     * Gets the value of the waybillBolNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaybillBolNumber() {
        return waybillBolNumber;
    }

    /**
     * Sets the value of the waybillBolNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaybillBolNumber(String value) {
        this.waybillBolNumber = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link Weight }
     *     
     */
    public Weight getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Weight }
     *     
     */
    public void setWeight(Weight value) {
        this.weight = value;
    }

    /**
     * Gets the value of the wharfageFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getWharfageFee() {
        return wharfageFee;
    }

    /**
     * Sets the value of the wharfageFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setWharfageFee(Money value) {
        this.wharfageFee = value;
    }

    /**
     * Gets the value of the worldEaseCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorldEaseCode() {
        return worldEaseCode;
    }

    /**
     * Sets the value of the worldEaseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorldEaseCode(String value) {
        this.worldEaseCode = value;
    }

    /**
     * Gets the value of the worldEaseFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWorldEaseFlag() {
        return worldEaseFlag;
    }

    /**
     * Sets the value of the worldEaseFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWorldEaseFlag(Boolean value) {
        this.worldEaseFlag = value;
    }

    /**
     * Gets the value of the worldEaseId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWorldEaseId() {
        return worldEaseId;
    }

    /**
     * Sets the value of the worldEaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWorldEaseId(Integer value) {
        this.worldEaseId = value;
    }

    /**
     * Gets the value of the worldEaseSingleEuCountry property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWorldEaseSingleEuCountry() {
        return worldEaseSingleEuCountry;
    }

    /**
     * Sets the value of the worldEaseSingleEuCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWorldEaseSingleEuCountry(Boolean value) {
        this.worldEaseSingleEuCountry = value;
    }

    /**
     * Gets the value of the zone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZone() {
        return zone;
    }

    /**
     * Sets the value of the zone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZone(String value) {
        this.zone = value;
    }

    /**
     * Gets the value of the externalKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalKey() {
        return externalKey;
    }

    /**
     * Sets the value of the externalKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalKey(String value) {
        this.externalKey = value;
    }

}

package com.owd.dc.manifest.api.internal;

import com.owd.connectship.services.ConnectShipCommunications;
import com.owd.connectship.services.CSReferenceService;
import com.owd.connectship.services.CSSearchService;
import com.owd.core.CountryNames;
import com.owd.core.WebResource;
import com.owd.core.TabReader;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.io.BufferedReader;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2008
 * Time: 12:11:11 PM
 * To change this template use File | Settings | File Templates.
 */

public class ShipConfig

{

    public static void main(String[] args)
    {
        ShipConfig conf = new ShipConfig();

       // System.out.println(conf.getCountryCode("PAPUA-NEW GUINEA"));
      try {
          conf.updateCarrierData();
         System.out.println(conf.getTranslatedServiceCode("CONNECTSHIP_DHL.DHL.WPX"));
      }catch (Exception e){
          e.printStackTrace();
      }
    }
    public static final String kTDLocalServer = "localhost";

    public static final String kShipperOWD = "OWD";

    // public static final String kShipperOWD = "RATE";
    public static final String kTDServer = "172.16.3.201";
    //  public static final String kRaterServerURL = "http://abweb1.internal.owd.com/Progistics/XML_Processor/Server/XMLProcDLL.asp";


    public static ConnectShipCommunications csComm = new ConnectShipCommunications(ConnectShipCommunications.liveServerURL);


    public static final String kShipperSNY = "SNY";

    public static final String kNetServicesServer = "it.owd.com:8081/wms/manifesting/ship";//kNetServicesServer

  //    public static final String kNetServicesServer = "stewart.owd.com:8080/wms/manifesting/ship";//kNetServicesServer
    //public static final String kNetServicesServer = "172.16.2.103:7001";


    public static Unmarshaller um = null;

// Payment Terms

// /INPUT/PACK/ fdtPaymentType

// Terms of payment

    public static String TERMS = "TERMS";

// Consignee Address

// /INPUT/PACK/ fdtNameAddress

// The consignee (destination) name and address (origin if RETURN_DELIVERY)

    public static String CONSIGNEE = "CONSIGNEE";

// Origin Address

// /INPUT/PACK/ fdtNameAddress

// The origin name and address (destination if RETURN_DELIVERY) - see CategoryPropertyID enumeration

    public static String ORIGIN_ADDRESS = "ORIGIN_ADDRESS";

// Return Address

// /INPUT/PACK/ fdtNameAddress

// Alternate 'From:' name and address to be used on documentation (not available for all documents)

    public static String RETURN_ADDRESS = "RETURN_ADDRESS";

// Package Dimensions

// /INPUT/PACK/ fdtDimension

// The package dimensions

    public static String DIMENSION = "DIMENSION";

// Oversize

// /INPUT/PACK/ fdtBoolean

// 'True' if the package's length + girth> 84 in.

    public static String OVERSIZE = "OVERSIZE";

// Packaging

// /INPUT/PACK/ fdtPackageType

// The type of packaging

    public static String PACKAGING = "PACKAGING";

// Arrival Time

// /INPUT/PACK/ fdtTime

// The requested (not guaranteed) arrival time of the package

    public static String ARRIVE_TIME = "ARRIVE_TIME";

// Commodity Class

// /INPUT/PACK/ fdtCommodityClass

// The NMFC commodity class

    public static String COMMODITY_CLASS = "COMMODITY_CLASS";

// Item Sequence Number

// /INPUT/PACK/ fdtLong

// Sequence number of this item in the group

    public static String NOFN_SEQUENCE = "NOFN_SEQUENCE";

// Total Number of Item

// /INPUT/PACK/ fdtLong

// Number of items in the group

    public static String NOFN_TOTAL = "NOFN_TOTAL";

// Carrier Instructions

// /INPUT/PACK/ fdtString

// Special shipping instructions for the carrier

    public static String CARRIER_INSTRUCTIONS = "CARRIER_INSTRUCTIONS";

// C.O.D. Flag

    public static String IS_COD = "IS_COD";
// C.O.D. Amount

// /INPUT/PACK/ fdtMoney

// The amount of the C.O.D.

    public static String COD_AMOUNT = "COD_AMOUNT";

// C.O.D. Payment Method

// /INPUT/PACK/ fdtEnumerationItems

// Acceptable C.O.D. paymentmethods - see CODPaymentMethod enumeration

    public static String COD_PAYMENT_METHOD = "COD_PAYMENT_METHOD";

// C.O.D. Number

// /INPUT/PACK/ fdtString

// The carrier-assigned or shipper-assigned C.O.D. identifier

    public static String COD_NUMBER = "COD_NUMBER";

// Alternate C.O.D. Number

// /INPUT/PACK/ fdtBoolean

// 'True' if the C.O.D. identifier is a shipper-assigned alternate, 'False' if it is carrier-assigned

    public static String COD_ALTERNATE_NUMBER = "COD_ALTERNATE_NUMBER";

// C.O.D. Instructions

// /INPUT/PACK/ fdtString

// Instructions for the carrier relating to the collection of the C.O.D.

    public static String COD_INSTRUCTIONS = "COD_INSTRUCTIONS";

// Declared Value

// /INPUT/PACK/ fdtMoney

// The value of the package for insurance purposes

    public static String DECLARED_VALUE_AMOUNT = "DECLARED_VALUE_AMOUNT";

// Declared Value for Customs

// /INPUT/PACK/ fdtMoney

// The value of the package for customs purposes

    public static String DECLARED_VALUE_CUSTOMS = "DECLARED_VALUE_CUSTOMS";

// Proof of Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' if the package will require Proof of Delivery / AOD

    public static String PROOF = "PROOF";

// Proof of Deliver Number

// /INPUT/PACK/ fdtString

// Package receipt confirmation number other than the one on the pre-printed Proof of Delivery tag

    public static String PROOF_NUMBER = "PROOF_NUMBER";

// Use Alternate Proof of Delivery Number

// /INPUT/PACK/ fdtBoolean

// 'True' if an alternate control number will print on the Proof of Delivery tag

    public static String PROOF_USE_ALTERNATE_NUMBER = "PROOF_USE_ALTERNATE_NUMBER";

// Require Proof of Delivery Signature

// /INPUT/PACK/ fdtBoolean

// 'True' if the consignee's signature is required as Proof of Delivery

    public static String PROOF_REQUIRE_SIGNATURE = "PROOF_REQUIRE_SIGNATURE";

// Call Tag

// /INPUT/PACK/ fdtBoolean

// 'True' if the package is a Call Tag (origin/destination reversed as with RETURN_DELIVERY)

    public static String CALLTAG = "CALLTAG";

// Call Tag Number

// /INPUT/PACK/ fdtString

// Call Tag number

    public static String CALLTAG_NUMBER = "CALLTAG_NUMBER";

// Call Tag Description

    public static String CALLTAG_DESCRIPTION = "CALLTAG_DESCRIPTION";

// Return Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' to indicate carrier should pick up the package at CONSIGNEE for delivery back to SHIPPER or ORIGIN_ADDRESS

    public static String RETURN_DELIVERY = "RETURN_DELIVERY";

// Return Delivery Method

// /INPUT/PACK/

//

    public static String RETURN_DELIVERY_METHOD = "RETURN_DELIVERY_METHOD";

// Saturday Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' if the carrier should deliver on Saturday

    public static String SATURDAY_DELIVERY = "SATURDAY_DELIVERY";

// Additional Handling

// /INPUT/PACK/ fdtBoolean

// 'True' if the shipment sizeor packaging requires additional handling

    public static String ADDITIONAL_HANDLING = "ADDITIONAL_HANDLING";

// Hazardous Materials

// /INPUT/PACK/ fdtBoolean

// 'True' if the package contains Hazardous Materials / Dangerous Goods

    public static String HAZMAT = "HAZMAT";

// Hazardous Materials ID

// /INPUT/PACK/ fdtString

// The U.S. Dept. of Transportation (DOT) Hazardous Material Identification Number for the contents of the package

    public static String HAZMAT_ID = "HAZMAT_ID";

// Hazardous Materials Class

// /INPUT/PACK/ fdtString

// The DOT Hazard Class name

    public static String HAZMAT_CLASS = "HAZMAT_CLASS";

// Hazardous Materials Description

// /INPUT/PACK/ fdtString

// Description of the Hazardous Material

    public static String HAZMAT_DESCRIPTION = "HAZMAT_DESCRIPTION";

// Hazardous Materials Quantity

// /INPUT/PACK/ fdtWeightVolume

// The mass (weight) or capacity (volume) of the Hazardous Material

    public static String HAZMAT_QUANTITY = "HAZMAT_QUANTITY";

// Hazardous Materials Packing

// /INPUT/PACK/ fdtString

// The type of material or container used to pack the Hazardous Material

    public static String HAZMAT_PACKING = "HAZMAT_PACKING";

// Hazardous Materials Packing Group

// /INPUT/PACK/ fdtEnumerationItem

// The DOT packing group of the Hazardous Material - see HazMatPackingGroup enumeration

    public static String HAZMAT_PACKING_GROUP = "HAZMAT_PACKING_GROUP";

// Hazardous Materials Label

// /INPUT/PACK/ fdtEnumerationItems

// The required DOT labels for the Hazardous Material - see HazMatLabel enumeration

    public static String HAZMAT_LABEL = "HAZMAT_LABEL";

// Hazardous Materials Cargo Aircraft

// /INPUT/PACK/ fdtBoolean

// 'True' if the Hazardous Material must be shipped only on a cargo aircraft (subject to carrier regulations)

    public static String HAZMAT_CARGO = "HAZMAT_CARGO";

// Hazardous Materials Accessible

// /INPUT/PACK/ fdtBoolean

// 'True' if the Hazardous Material must remain accessible

    public static String HAZMAT_ACCESSIBLE = "HAZMAT_ACCESSIBLE";

// Dry Ice Weight

// /INPUT/PACK/ fdtWeight

// The weight of the dry ice in the package

    public static String DRY_ICE_WEIGHT = "DRY_ICE_WEIGHT";

// Hold at Location

// /INPUT/PACK/ fdtBoolean

// 'True' if the package should be held by the carrier

    public static String HOLD_AT_LOCATION = "HOLD_AT_LOCATION";

// Hold at Location Address

// /INPUT/PACK/ fdtNameAddress

// The address at which a package should be held

    public static String HOLD_AT_LOCATION_ADDRESS = "HOLD_AT_LOCATION_ADDRESS";

// Inside Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' if inside delivery beyond the loading area is required

    public static String INSIDE_DELIVERY = "INSIDE_DELIVERY";

// Direct Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' if the package must be delivered directly

    public static String DIRECT_DELIVERY = "DIRECT_DELIVERY";

// Special Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' for special delivery

    public static String SPECIAL_DELIVERY = "SPECIAL_DELIVERY";

// Sunday Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' for sunday delivery

    public static String SUNDAY_DELIVERY = "SUNDAY_DELIVERY";

// Holiday Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' for holiday delivery

    public static String HOLIDAY_DELIVERY = "HOLIDAY_DELIVERY";

// Signature Release

// /INPUT/PACK/ fdtBoolean

// 'True' if the consignee's signature is on file with the carrier

    public static String SIGNATURE_RELEASE = "SIGNATURE_RELEASE";

// Perishable

// /INPUT/PACK/ fdtBoolean

// 'True' if the package contains perishable goods

    public static String PERISHABLE = "PERISHABLE";

// Consignee Account Number

// /INPUT/PACK/ fdtString

// The consignee's carrier account number (for billing)

    public static String CONSIGNEE_ACCOUNT = "CONSIGNEE_ACCOUNT";

// Third Party Billing

// /INPUT/PACK/ fdtBoolean

// 'True' if the shipper's portion of the charges will be paid by a third party

    public static String THIRD_PARTY_BILLING = "THIRD_PARTY_BILLING";

// Third Party Billing Account Number

// /INPUT/PACK/ fdtString

// The carrier account number of the third party (for billing)

    public static String THIRD_PARTY_BILLING_ACCOUNT = "THIRD_PARTY_BILLING_ACCOUNT";

// Third Party Billing Address

// /INPUT/PACK/ fdtNameAddress

// The address of the third party

    public static String THIRD_PARTY_BILLING_ADDRESS = "THIRD_PARTY_BILLING_ADDRESS";

// Consignee Billing ID

// /INPUT/PACK/ fdtString

// Consignee Billing ID

    public static String CONSIGNEE_BILLING_ID = "CONSIGNEE_BILLING_ID";

// Documents Only

// /INPUT/PACK/ fdtBoolean

// 'True' if the package contains only non-dutiable documents

    public static String DOCUMENTS_ONLY = "DOCUMENTS_ONLY";

// Ultimate Destination Country

// /INPUT/PACK/ fdtCountry

// Country of ultimate destination

    public static String ULTIMATE_DESTINATION_COUNTRY = "ULTIMATE_DESTINATION_COUNTRY";

// Consignee Customs ID

// /INPUT/PACK/ fdtString

// The consignee's identification number for customs purposes

    public static String CONSIGNEE_CUSTOMS_ID = "CONSIGNEE_CUSTOMS_ID";

// Parties Related

// /INPUT/PACK/ fdtBoolean

// 'True' if the parties to the transaction (shipper and consignee) are related

    public static String PARTIES_RELATED = "PARTIES_RELATED";

// SED Method

// /INPUT/PACK/ fdtEnumerationItem

// Indicates which method will be used to provide the Shipper's Export Declaration - see Constants enumeration

    public static String SED_METHOD = "SED_METHOD";

// SED Exepmtion Number

// /INPUT/PACK/ fdtShort

// Contains the last two digits of the Shipper's Export Declaration exemption number (such as FTSR 30.__)

    public static String SED_EXEMPTION_NUMBER = "SED_EXEMPTION_NUMBER";

// Reason for Export

// /INPUT/PACK/ fdtString

// Reason for package export forcustoms purposes

    public static String EXPORT_REASON = "EXPORT_REASON";

// Consignee Tax ID

// /INPUT/PACK/ fdtString

// Consignee Tax ID for customs purposes

    public static String CONSIGNEE_TAX_ID = "CONSIGNEE_TAX_ID";

// Appointment Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' for the carrier to schedule an appointment for package delivery

    public static String APPOINTMENT_DELIVERY = "APPOINTMENT_DELIVERY";

// Evening Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' for the carrier to deliver typically residential package in the evening

    public static String EVENING_DELIVERY = "EVENING_DELIVERY";

// Cert. of Origin Method

// /INPUT/PACK/ fdtEnumerationItem

// Indicates which method will be used to provide the Certificate of Origin - see COMethod enumeration

    public static String CERT_OF_ORIGIN_METHOD = "CERT_OF_ORIGIN_METHOD";

// Earliest Delivery Time

// /INPUT/PACK/ fdtTime

// The time before which the carrier should not attempt delivery (consignee location may be closed)

    public static String EARLIEST_DELIVERY_TIME = "EARLIEST_DELIVERY_TIME";

// Commodity Contents

// /INPUT/PACK/ fdtDataDictionaryList

// Information about the commodities contained in the package

    public static String COMMODITY_CONTENTS = "COMMODITY_CONTENTS";

// Helper Pickup

// /INPUT/PACK/ fdtBoolean

// 'True' if a helper is required topickup the package

    public static String HELPER_PICKUP = "HELPER_PICKUP";

// Helper Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' if a helper is required to deliver the package

    public static String HELPER_DELIVERY = "HELPER_DELIVERY";

// Liftgate Pickup

// /INPUT/PACK/ fdtBoolean

// 'True' if a liftgate is required to pickup the package

    public static String LIFTGATE_PICKUP = "LIFTGATE_PICKUP";

// Liftgate Delivery

// /INPUT/PACK/ fdtBoolean

// 'True' if a liftgate is required to deliver the package

    public static String LIFTGATE_DELIVERY = "LIFTGATE_DELIVERY";

// Registered Mail

// /INPUT/PACK/ fdtBoolean

// 'True' for Registered Mail (US Postal Service only)

    public static String REGISTERED_MAIL = "REGISTERED_MAIL";

// Certified Mail

// /INPUT/PACK/ fdtBoolean

// 'True' for Certified Mail (US Postal Service only)

    public static String CERTIFIED_MAIL = "CERTIFIED_MAIL";

// Nonstandard Mail

// /INPUT/PACK/ fdtBoolean

// 'True' if meets criteria for avery light package with unusually large dimensions (US Postal Service only)

    public static String NONSTANDARD_MAIL = "NONSTANDARD_MAIL";

// Nonmachinable Mail

// /INPUT/PACK/ fdtBoolean

// 'True' if meets criteria fora package that cannot be processed with automation equipment (US Postal Serviceonly)

    public static String NONMACHINABLE_MAIL = "NONMACHINABLE_MAIL";

// Parcel Airlift

// /INPUT/PACK/ fdtBoolean

// 'True' for Parcel Airlift (US Postal Service only)

    public static String PARCEL_AIRLIFT = "PARCEL_AIRLIFT";

// Suppress Manifest Mailing System

// /INPUT/PACK/ fdtBoolean

// 'True' to suppress Manifest Mailing System (US Postal Service only)

    public static String SUPPRESS_MMS = "SUPPRESS_MMS";

// Suppress Delivery Confirmation

// /INPUT/PACK/ fdtBoolean

// 'True' to suppress Delivery Confirmation (US Postal Service only)

    public static String SUPPRESS_DC = "SUPPRESS_DC";

// Manifest Mailing System

// /INPUT/PACK/ fdtBoolean

// 'True' if the package will be manifested (US Postal Service only)

    public static String MMS = "MMS";

// Package List ID

// /INPUT/OUTPUT/PACK/ fdtLong

// Number which uniquely identifies every package list submitted to Progistics

    public static String PACKAGE_LIST_ID = "PACKAGE_LIST_ID";
    public static String MMS_PIECE_ID = "MMS_PIECE_ID";
// Subcategory

// /INPUT/OUTPUT/PACK/ fdtSubcategory

// Subcategory

    public static String SUBCATEGORY = "SUBCATEGORY";

// Service

// /INPUT/OUTPUT/PACK/ fdtService

// Service

    public static String SERVICE = "SERVICE";

// Ship Date

// /INPUT/OUTPUT/PACK/ fdtDate

// The intended ship date

    public static String SHIPDATE = "SHIPDATE";

// Shipper

// /INPUT/OUTPUT/PACK/ fdtShipper

// The shipper

    public static String SHIPPER = "SHIPPER";

// Consignee Code

// /INPUT/OUTPUT/PACK/ fdtString

// A unique identifier for the CONSIGNEE attribute (such as an identifier from a consignee database)

    public static String CONSIGNEE_CODE = "CONSIGNEE_CODE";

// Master Sequence Number

// /INPUT/OUTPUT/PACK/ fdtLong

// Number which uniquely identifies every package known to Progistics

    public static String MSN = "MSN";

// Bundle ID

// /INPUT/OUTPUT/PACK/ fdtLong

// Number which unique identifieseach bundle

    public static String BUNDLE_ID = "BUNDLE_ID";

// Package Sequence Number in Bundle

// /INPUT/OUTPUT/PACK/ fdtLong

// Sequence number of this package in the bundle

    public static String NOFN_SEQUENCE_BUNDLE = "NOFN_SEQUENCE_BUNDLE";

// Total Number of Packages in Bundle

// /INPUT/OUTPUT/PACK/ fdtLong

// Number of packages in the bundle

    public static String NOFN_TOTAL_BUNDLE = "NOFN_TOTAL_BUNDLE";

// Base Charge

// /INPUT/OUTPUT/PACK/ fdtMoney

// Base charge

    public static String BASE = "BASE";

// Special Service Charge

// /INPUT/OUTPUT/PACK/ fdtMoney

// Total of all special service fees

    public static String SPECIAL = "SPECIAL";

// Discount

// /INPUT/OUTPUT/PACK/ fdtMoney

// Total discounts

    public static String DISCOUNT = "DISCOUNT";

// Value Added Tax

// /INPUT/OUTPUT/PACK/ fdtMoney

// Value added tax

    public static String VAT = "VAT";

// Total Charge

// /INPUT/OUTPUT/PACK/ fdtMoney

// Total charge (BASE + SPECIAL- DISCOUNT + VAT = TOTAL)

    public static String TOTAL = "TOTAL";

// Apportioned Base Charge

// /INPUT/OUTPUT/PACK/ fdtMoney

// Base charge apportioned to the package

    public static String APPORTIONED_BASE = "APPORTIONED_BASE";

// Apportioned Special Service Charge

// /INPUT/OUTPUT/PACK/ fdtMoney

// Total of all special service fees apportioned to the package

    public static String APPORTIONED_SPECIAL = "APPORTIONED_SPECIAL";

// Apportioned Discount

// /INPUT/OUTPUT/PACK/ fdtMoney

// Total discounts apportioned to the package

    public static String APPORTIONED_DISCOUNT = "APPORTIONED_DISCOUNT";

// Apportioned Value Added Tax

// /INPUT/OUTPUT/PACK/ fdtMoney

// Value added tax apportioned to the package

    public static String APPORTIONED_VAT = "APPORTIONED_VAT";

// Apportioned Total Charge

// /INPUT/OUTPUT/PACK/ fdtMoney

// Total charge apportioned to the package

    public static String APPORTIONED_TOTAL = "APPORTIONED_TOTAL";

// Weight

// /INPUT/OUTPUT/PACK/ fdtWeight

// The package weight

    public static String WEIGHT = "WEIGHT";

// Shipper Reference

// /INPUT/OUTPUT/PACK/ fdtString

// Package identifier foruse by the shipper and carrier (such as invoice number or package ID)

    public static String SHIPPER_REFERENCE = "SHIPPER_REFERENCE";

// Consignee Reference

// /INPUT/OUTPUT/PACK/ fdtString

// Package identifier for use by the shipper and consignee (such as P.O. number)

    public static String CONSIGNEE_REFERENCE = "CONSIGNEE_REFERENCE";

// Arrival Date

// /INPUT/OUTPUT/PACK/ fdtDate

// The expected arrival date based on the carrier's delivery commitment

    public static String ARRIVE_DATE = "ARRIVE_DATE";

// Tracking Number

// /INPUT/OUTPUT/PACK/ fdtString

// Tracking number or PRO number

    public static String TRACKING_NUMBER = "TRACKING_NUMBER";

// C.O.D. Return Tracking Number

// /INPUT/OUTPUT/PACK/ fdtString

// C.O.D. Return Tracking number

    public static String COD_RETURN_TRACKING_NUMBER = "COD_RETURN_TRACKING_NUMBER";

// C.O.D. Master Tracking Number

// /INPUT/OUTPUT/PACK/ fdtString

// C.O.D. Master Tracking number

    public static String COD_MASTER_TRACKING_NUMBER = "COD_MASTER_TRACKING_NUMBER";

// Routing Code

// /INPUT/OUTPUT/PACK/ fdtString

// Routing code

    public static String ROUTING_CODE = "ROUTING_CODE";

// Hub Code

// /INPUT/OUTPUT/PACK/ fdtString

// Hub code

    public static String HUB_CODE = "HUB_CODE";

// Destination Airport Code

// /INPUT/OUTPUT/PACK/ fdtString

// Destination airport code

    public static String DESTINATION_AIRPORT_CODE = "DESTINATION_AIRPORT_CODE";

// Waybill/BOL Number

// /INPUT/OUTPUT/PACK/ fdtString

// Waybill or Bill of Lading number

    public static String WAYBILL_BOL_NUMBER = "WAYBILL_BOL_NUMBER";

// Hundredweight Rated

// /INPUT/OUTPUT/PACK/ fdtBoolean

// 'True' if Hundredweight Rating was used

    public static String HUNDREDWEIGHT_RATED = "HUNDREDWEIGHT_RATED";

// Dimensional Weight Rated

// /INPUT/OUTPUT/PACK/ fdtBoolean

// 'True' if the Dimensional Weight was used in rating

    public static String DIMENSIONAL_WEIGHT_RATED = "DIMENSIONAL_WEIGHT_RATED";

// C.O.D. Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for C.O.D.

    public static String COD_FEE = "COD_FEE";

// Declared Value Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Declared Value/ Insurance

    public static String DECLARED_VALUE_FEE = "DECLARED_VALUE_FEE";

// Proof of Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Proof of Delivery / AOD

    public static String PROOF_FEE = "PROOF_FEE";

// Call Tag Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Call Tag

    public static String CALLTAG_FEE = "CALLTAG_FEE";

// Return Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Return Delivery

    public static String RETURN_DELIVERY_FEE = "RETURN_DELIVERY_FEE";

// Saturday Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Saturday Delivery

    public static String SATURDAY_DELIVERY_FEE = "SATURDAY_DELIVERY_FEE";

// Saturday Pickup Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Saturday Pickup

    public static String SATURDAY_PICKUP_FEE = "SATURDAY_PICKUP_FEE";

// Sunday Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Sunday Delivery

    public static String SUNDAY_DELIVERY_FEE = "SUNDAY_DELIVERY_FEE";

// Holiday Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Holiday Delivery

    public static String HOLIDAY_DELIVERY_FEE = "HOLIDAY_DELIVERY_FEE";

// Early Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Early Delivery

    public static String EARLY_DELIVERY_FEE = "EARLY_DELIVERY_FEE";

// Additional Handling Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Additional Handling

    public static String ADDITIONAL_HANDLING_FEE = "ADDITIONAL_HANDLING_FEE";

// Hazardous Materials Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for HazardousMaterials

    public static String HAZMAT_FEE = "HAZMAT_FEE";

// Hold at Location Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Hold At Location

    public static String HOLD_AT_LOCATION_FEE = "HOLD_AT_LOCATION_FEE";

// Residential Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Residential Delivery

    public static String RESIDENTIAL_DELIVERY_FEE = "RESIDENTIAL_DELIVERY_FEE";

// Dry Ice Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Dry Ice

    public static String DRY_ICE_FEE = "DRY_ICE_FEE";

// Direct Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Direct Delivery

    public static String DIRECT_DELIVERY_FEE = "DIRECT_DELIVERY_FEE";

// Special Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Special Delivery

    public static String SPECIAL_DELIVERY_FEE = "SPECIAL_DELIVERY_FEE";

// Helper Pickup Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee if helper required for pickup

    public static String HELPER_PICKUP_FEE = "HELPER_PICKUP_FEE";

// Helper Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee if helper required for delivery

    public static String HELPER_DELIVERY_FEE = "HELPER_DELIVERY_FEE";

// Liftgate Pickup Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee if liftgate required for pickup

    public static String LIFTGATE_PICKUP_FEE = "LIFTGATE_PICKUP_FEE";

// Liftgate Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee if liftgate required for delivery

    public static String LIFTGATE_DELIVERY_FEE = "LIFTGATE_DELIVERY_FEE";

// Offshore Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Offshore delivery/pickup

    public static String OFFSHORE_FEE = "OFFSHORE_FEE";

// Air Freight Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Air Freight

    public static String AIR_FREIGHT_FEE = "AIR_FREIGHT_FEE";

// Proof of Delivery Signature Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for requiring the consignee's signature with Proof of Delivery

    public static String PROOF_REQUIRE_SIGNATURE_FEE = "PROOF_REQUIRE_SIGNATURE_FEE";

// Extended Area Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for unusual or extended area package destination

    public static String EXTENDED_AREA_FEE = "EXTENDED_AREA_FEE";

// Inside Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Inside Delivery

    public static String INSIDE_DELIVERY_FEE = "INSIDE_DELIVERY_FEE";

// Oversize Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Oversize

    public static String OVERSIZE_FEE = "OVERSIZE_FEE";

// Remote Origin Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for unusual or remote package pickup location

    public static String REMOTE_ORIGIN_FEE = "REMOTE_ORIGIN_FEE";

// Duty Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Duty

    public static String DUTY_FEE = "DUTY_FEE";

// Fuel Surcharge

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fuel surcharge

    public static String FUEL_SURCHARGE = "FUEL_SURCHARGE";

// Consolidation ID

// /INPUT/OUTPUT/PACK/ fdtString

// Consolidation ID

    public static String CONSOLIDATION_ID = "CONSOLIDATION_ID";

// Appointment Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Appointment Delivery

    public static String APPOINTMENT_DELIVERY_FEE = "APPOINTMENT_DELIVERY_FEE";

// Evening Delivery Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Evening Delivery

    public static String EVENING_DELIVERY_FEE = "EVENING_DELIVERY_FEE";

// Billing Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Special Billing options

    public static String BILLING_FEE = "BILLING_FEE";

// Documentation Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee if Carrier preparesdocumentation (usually for International) that accompanies the shipment

    public static String DOCUMENTATION_FEE = "DOCUMENTATION_FEE";

// GroundSaver

// /INPUT/OUTPUT/PACK/ fdtBoolean

// 'True' if the package qualifies for GroundSaver (UPS only)

    public static String GROUNDSAVER = "GROUNDSAVER";

// Registered Mail Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Registered Mail (US Postal Service only)

    public static String REGISTERED_MAIL_FEE = "REGISTERED_MAIL_FEE";

// Certified Mail Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Certified Mail(US Postal Service only)

    public static String CERTIFIED_MAIL_FEE = "CERTIFIED_MAIL_FEE";

// Nonstandard Mail Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Nonstandard Mail (US Postal Service only)

    public static String NONSTANDARD_MAIL_FEE = "NONSTANDARD_MAIL_FEE";

// Nonmachinable Mail Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Nonmachinable Mail (US Postal Service only)

    public static String NONMACHINABLE_MAIL_FEE = "NONMACHINABLE_MAIL_FEE";

// Parcel Airlift Fee

// /INPUT/OUTPUT/PACK/ fdtMoney

// Fee for Parcel Airlift(US Postal Service only)

    public static String PARCEL_AIRLIFT_FEE = "PARCEL_AIRLIFT_FEE";

// Bundle ID List

// /OUTPUT/PACK/ fdtLongList

// List of Bundle IDs

    public static String BUNDLE_ID_LIST = "BUNDLE_ID_LIST";

// Time in Transit

// /OUTPUT/PACK/ fdtCommitment

// The expected time in transit based on the carrier's delivery commitment

    public static String TIME_IN_TRANSIT = "TIME_IN_TRANSIT";

// Zone

// /OUTPUT/PACK/ fdtString

// Carrier assigned delivery zone

    public static String ZONE = "ZONE";

// Bar Code 1

// /OUTPUT/PACK/ fdtString

// Bar code

    public static String BAR_CODE = "BAR_CODE";

// Bar Code 2

// /OUTPUT/PACK/ fdtString

// Bar code 2

    public static String BAR_CODE_2 = "BAR_CODE_2";

// MaxiCode

// /OUTPUT/PACK/ fdtStringList

// List of strings that comprise the MaxiCode (UPS only)

    public static String MAXICODE = "MAXICODE";

// Cycle Count

// /OUTPUT/PACK/ fdtLong

// Cycle Count (FedEx only)

    public static String CYCLE_COUNT = "CYCLE_COUNT";

// Product Code

// /INPUT/COMM/ fdtString

// Product identifier for use by the shipper and carrier (such as part number or SKU)

    public static String PRODUCT_CODE = "PRODUCT_CODE";

// Quantity

// /INPUT/COMM/ fdtLong

// The number of units of this commodity

    public static String QUANTITY = "QUANTITY";

// Quantity Unit Measure

// /INPUT/COMM/ fdtUnitOfMeasure

// Identifies the quantity unit of measurement for this commodity

    public static String QUANTITY_UNIT_MEASURE = "QUANTITY_UNIT_MEASURE";

// Unit Weight

// /INPUT/COMM/ fdtWeight

// The per unit weight this commodity

    public static String UNIT_WEIGHT = "UNIT_WEIGHT";

// Unit Value

// /INPUT/COMM/ fdtMoney

// The per unit value of this commodity for customs purposes

    public static String UNIT_VALUE = "UNIT_VALUE";

// Origin Country

// /INPUT/COMM/ fdtCountry

// Country of origin or manufactureof this commodity

    public static String ORIGIN_COUNTRY = "ORIGIN_COUNTRY";

// Harmonized Code

// /INPUT/COMM/ fdtString

// Code used by customs to identify the commodity

    public static String HARMONIZED_CODE = "HARMONIZED_CODE";

// License Number

// /INPUT/COMM/ fdtString

// Contains a validated export number or general symbol for this commodity

    public static String LICENSE_NUMBER = "LICENSE_NUMBER";

// License Expiration Date

// /INPUT/COMM/ fdtDate

// Indicates the expirationdate of the export license for this commodity

    public static String LICENSE_EXPIRATION_DATE = "LICENSE_EXPIRATION_DATE";

// ECCN

// /INPUT/COMM/ fdtString

// The Export Commodity Control Number for this commodity

    public static String ECCN = "ECCN";

// Description

// /INPUT/PACK/COMM/ fdtString

// Description package contents

    public static String DESCRIPTION = "DESCRIPTION";

//Default customs description

    public static String DEFAULT_CUSTOMS_DESCRIPTION = "DEFAULT_CUSTOM_DESCRIPTION";

//Actual Total

//for third party billing shipments

    public static final String ACTUAL_TOTAL = "ACTUAL_TOTAL";

//Shipped PKGS

//for multi-piece packages

    public static final String SHIPPED_PKGS = "SHIPPED_PKGS";


    //Package type for a SKU
    public static final String PKG_TYPE = "PKG_TYPE";

    //Number of packages of a SKU
    public static final String PKG_NUM = "PKG_NUM";

    //Predicted weight for a package
    public static final String PKG_WEIGHT = "PKG_WEIGHT";

    //Dimension values for a package
    public static final String PKG_DEPTH = "PKG_DEPTH";
    public static final String PKG_WIDTH = "PKG_WIDTH";
    public static final String PKG_HEIGHT = "PKG_HEIGHT";

    //Force usage of the recorded weight and dimension values of a package
    public static final String PKG_FORCE_USE = "PKG_FORCE_USE";

    //Package weight and dimension values
    public static final String PKG_SHIP_INFO = "PKG_SHIP_INFO";
    //Freight Collect
    public static final String FREIGHT_COLLECT = "FREIGHT_COLLECT";

    //CONSINGEE BILLING
    public static final String CONSIGNEE_BILLING = "CONSIGNEE_BILLING";

    private static ShipConfig me = null;

    //current shipper
    public static final String CURRENT_SHIPPER = "CURRENT_SHIPPER";

    //the primary key of owd_order table
    public static final String OWD_ORDER_ID = "OWDORDERID";

    public static final String OWD_ORDER_NUM = "OWDORDERNUM";

    public static final String ORDER_NUM_BARCODE = "BARCODE";

    public static final String SHIP_TO_EMAIL = "SHIP_TO_EMAIL";

    public static final String REFERENCE_SUFFIX = "REFERENCE_SUFFIX";
    public static final String SHIP_DTP_FLAG = "SHIP_DTP_FLAG";


    public static String laserPrinterName = "laser";

    public static String laserPrinterDriver = "Generic.WindowsPrinter";

    public static String labelPrinterName = null;
    public static String labelPrinterPort = null;

    public static String labelPrinterFriendlyName = null;

    public static String labelPrinterDriver = "COM1";

    public static String shipLocation = "DC-MOB-1";

    public static String scaleName = null;

    public static String scaleFriendlyName = null;

    public static String scaleDriver = "COM2";


    public static String BILL_OF_LADING = "bill_of_lading";
    public static String TOTAL_WEIGHT = "total_weight";


    public static String OWD_ORDER_TOTAL = "OWDORDERTOTAL";
    public static String OWD_SIG_REQUESTED = "OWDSIGREQUESTED";
    public static String FED_EX_CUSTOMS_ACCOUNT = "FEDEXCUSTOMSACCOUNT";


    public static String OWD_PO_NUMBER = "OWDPONUMBER";
    public static String GROUP_NAME = "GROUPNAME";
    public static String EXTERNAL_PACKAGE = "EXTERNAL_PACKAGE";
    public static String EXTERNAL_API_KEY = "EXTERNALAPIKEY";

    private ShipConfig()

    {


    }

    public static boolean USPSgreensetup = false;
    public static boolean whiteForms = false;
    public static boolean scale = false;
    public static String scaleport = "COM1";
    public static String myIPAddress = "UNKNOWN";

    public static ShipConfig getConfig() throws Exception {
        if (me == null) {

            //for OS X compatibility when printing Jasper Reports under JDK 1.4.2+, uncomment below. Untested on other OSes...
            /*
              System.setProperty(
          "javax.xml.parsers.DocumentBuilderFactory","org.apache.crimson.jaxp.DocumentBuilderFactoryImpl");

             System.setProperty(
          "javax.xml.parsers.SAXParserFactory",
                                   "org.apache.crimson.jaxp.SAXParserFactoryImpl");
            */

            try {
                InetAddress addr = InetAddress.getLocalHost();


                myIPAddress = addr.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            um = JAXBContext.newInstance("com.owd.connectship.xml.ShipmentResponse").createUnmarshaller();

            //////System.out.println("starting shipconfig");

                        USPSgreensetup = false;

                whiteForms = false;


                //System.out.println("USPS Green setup" + USPSgreensetup);
                //System.out.println("White Form setup" + whiteForms);

                //System.out.println(scale + " " + scaleport);



            me = new ShipConfig();


        //    transAPI = me.getTransAPI();

            ////System.out.println("starting updateCarrierMap");

            me.updateCarrierMap();

            ////System.out.println("starting updateShipperMap");

            me.updateShipperMap();

            ////System.out.println("starting updateCountryMap");


            me.updateCountryMap();

            ////System.out.println("starting updateDeviceMap");




                ////System.out.println("No Tandata device management available!");

                ////System.out.println("starting updateDriverConfig");




                    ////System.out.println("No Tandata driver config available!");

////System.out.println("starting updatePackageMap");

                    me.updatePackageMap();

            ////System.out.println("starting updatePaymentMap");

            me.updatePaymentMap();

            ////System.out.println("starting updateCarrierData");

            me.updateCarrierData();

////System.out.println("checking carrier registration");

            Iterator carrierIter = me.getCarrierCodes().iterator();

            ////System.out.println("registering carriers");

            while (carrierIter.hasNext())

            {

                String carrier = (String) carrierIter.next();

                //System.out.println(" register " + carrier);


                me.updateServiceMap();

            }

            ////System.out.println("done registering carriers");

        }


        return me;

    }


    public static void clear()

    {

        me = null;

    }

    public void load()

    {


    }


    public void save()

    {


    }









    public List getCountryNames()

    {

        return new ArrayList<String>(countryMap.keySet());

    }


    public List getCountryCodes()

    {

        return new ArrayList<String>(countryMap.values());

    }


    public String getPrinterDriver(String printerName)

    {

        return (String) labelPrinterMap.get(printerName);

    }


    public String getScaleDriver(String scaleName)

    {

        return (String) scaleMap.get(scaleName);

    }


    public String getCountryCode(String countryName)

    {

        System.out.println("getting country code for name "+countryName);
         System.out.println("Trying fix");
        countryName= CountryNames.fixCodeOrName(countryName);
        System.out.println(countryName);

        String countryCode = CSReferenceService.getCountryCode(new ConnectShipCommunications(ConnectShipCommunications.liveServerURL), countryName);

        System.out.println("returning country code "+countryCode);

        if (countryCode == null)

        {


            countryCode = "UNITED_STATES";


        }

        return countryCode;

    }


    public String getCountryName(String countryCode)

    {
        //   CSReferenceService.getCountryMap(new ConnectShipCommunications(ConnectShipCommunications.liveServerURL));

        if (countryMap.containsValue(countryCode))

            return (String) getCountryNames().get(getCountryCodes().indexOf(countryCode));


        return null;

    }


    public List getPackageNames()

    {

        return new ArrayList<String>(packageMap.keySet());

    }


    public List getPackageCodes()

    {

        return new ArrayList<String>(packageMap.values());

    }


    public String getPackageCode(String packageName)

    {

        return (String) packageMap.get(packageName);

    }


    public String getPackageName(String packageCode)

    {

        if (packageMap.containsValue(packageCode))

            return (String) getPackageNames().get(getPackageCodes().indexOf(packageCode));


        return null;

    }


    public List getPaymentNames()

    {

        return new ArrayList<String>(paymentMap.keySet());

    }


    public List getPaymentCodes()

    {

        return new ArrayList<String>(paymentMap.values());

    }


    public String getPaymentCode(String paymentName)

    {

        return (String) paymentMap.get(paymentName);

    }


    public String getPaymentName(String paymentCode)

    {

        if (paymentMap.containsValue(paymentCode))

            return (String) getPaymentNames().get(getPaymentCodes().indexOf(paymentCode));


        return null;

    }


    public String getServiceCode(String serviceName)

    {

        return (String) serviceMap.get(serviceName);

    }


    public String getServiceName(String serviceCode)

    {

         System.out.println("Service Map");
      //  System.out.println(serviceMap);
        if (serviceMap.containsValue(serviceCode.trim()))

        {

            return (String) getServiceNames().get(getServiceCodes().indexOf(serviceCode.trim()));

        } else {
             if (serviceCode.trim().equals("OWD.NOBOX.PICKEDUP")) return serviceCode;
             if (serviceCode.trim().equals("OWD.OSM.DOM")) return "OSM Domestic";
             if(serviceCode.trim().equals("OWD.OSM.INTL")) return "OSM Worldwide";
            if(serviceCode.trim().equals("OWD.BPD.STANDARD")) return "BPD Standard";
            if(serviceCode.trim().equals("EASYPOST.PUROLATOR")) return "Purolator";

            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_GROUND")) return "Ground";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_ECONOMY")) return "Ecomony";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_INTL_EXPD")) return "International Expedited";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_INTL_STND")) return "International Standard";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_NDA")) return "Overnight";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_2DA")) return "2 Day";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_DHL_MAX")) return "Ground Max";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_STANDARD_GROUND")) return "Standard Priority";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_INTL_ECONOMY")) return "International Economy";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_INTL_PRIDDP")) return "International Priority DDP";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_INTL_PRIDDU")) return "International Priority DDU";
            if(serviceCode.trim().equalsIgnoreCase("COM_OWD_FLATRATE_MM")) return "Media Mail";

            if(serviceCode.trim().equalsIgnoreCase("ONTRAC.GROUND")) return "Ontrac Ground";
            if(serviceCode.trim().equalsIgnoreCase("PS_ALF.ONTRAC.CT")) return "Ontrac Ground";
            if(serviceCode.trim().equalsIgnoreCase("AXLEHIRE.NDA")) return "AxleHire";
            ////System.out.println("service code not found for "+serviceCode);


        }


        return null;

    }


    public String getCarrierCode(String carrierName)

    {

        return (String) carrierMap.get(carrierName);

    }


    public CarrierInfo getCarrierInfo(String serviceCode)

    {

        return (CarrierInfo) carrierInfoMap.get(serviceCode);

    }


    public String getCarrierName(String carrierCode)

    {

        if (carrierMap.containsValue(carrierCode))

            return (String) getCarrierNames().get(getCarrierCodes().indexOf(carrierCode));


        return null;

    }


    public List getCarrierNames()

    {

        return new ArrayList<String>(carrierMap.keySet());

    }


    public List getCarrierCodes()

    {

        return new ArrayList<String>(carrierMap.values());

    }

    public List getShipperNames()

    {

        return new ArrayList<String>(shipperMap.keySet());

    }


    public List getShipperCodes()

    {

        return new ArrayList<String>(shipperMap.values());

    }


    public List getScaleNames()

    {

        return new ArrayList<String>(scaleMap.keySet());

    }





    public List getLabelPrinterCodes()

    {

        return new ArrayList<String>(labelPrinterMap.values());

    }

    public List getLabelPrinterNames()

    {

        return new ArrayList<String>(labelPrinterMap.keySet());

    }


    public List getScaleCodes()

    {

        return new ArrayList<String>(scaleMap.values());

    }


    public List getServiceCodes()

    {

        return new ArrayList<String>(serviceMap.values());

    }


    public List getServiceNames()

    {

        return new ArrayList<String>(serviceMap.keySet());

    }


    private void updateCountryMap() throws Exception

    {

        countryMap.clear();


        countryMap = CSReferenceService.getCountryMap(new ConnectShipCommunications(ConnectShipCommunications.liveServerURL));

        //for testing

        /*
Iterator it = countryMap.keySet().iterator();

while(it.hasNext())

{

   String key = (String) it.next();

   //System.out.println(key+": "+ countryMap.get(key));

}
        **/

    }


    private void updateCarrierMap() throws Exception {

        carrierMap.clear();

        carrierMap = CSReferenceService.getCarrierMap(new ConnectShipCommunications(ConnectShipCommunications.liveServerURL));

    }

    private void updateShipperMap() throws Exception {
        shipperMap.clear();
        shipperMap = CSReferenceService.getShipperMap(new ConnectShipCommunications(ConnectShipCommunications.liveServerURL));
    }



    private void updateServiceMap() throws Exception

    {

        serviceMap.clear();


        serviceMap = CSReferenceService.getShipperServices(new ConnectShipCommunications(ConnectShipCommunications.liveServerURL));

        //System.out.println("got svc map "+serviceMap);

    }



    private void updatePackageMap() throws Exception

    {

        packageMap.clear();


        packageMap = CSReferenceService.getPackageTypesMap(new ConnectShipCommunications(ConnectShipCommunications.liveServerURL));


    }

    class Location
    {
        String name;
        String address;
        String city;
        String state;
        String zip;
        String country = "USA";

        public Location(String name, String address, String city, String state, String zip) {
            this.name = name;
            this.address = address;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    static Map<String,Location> locationMap;

    public  Map<String, Location> getLocationMap() throws Exception {
        if(locationMap==null)
        {
            updateLocationMap();
        }
        return locationMap;
    }

    public void updateLocationMap() throws Exception

    {

        if(locationMap !=null)
        {
       locationMap.clear();
        }
        locationMap = new HashMap<String,Location>();
        
        WebResource loader = new WebResource("http://" + kNetServicesServer, WebResource.kGETMethod);


        loader.addParameter("fn", "gl");

        BufferedReader reader = loader.getResource();

        TabReader locData = new TabReader(reader, false);



        for (int i = 1; i < locData.getRowCount(); i++)

        {
           // //System.out.println("Loc:" + locData.getStrValue(0, i, "") + "-" + locData.getStrValue(1, i, "")+ "-" + locData.getStrValue(2, i, "")+ "-" + locData.getStrValue(3, i, "")+ "-" + locData.getStrValue(4, i, ""));
            locationMap.put(locData.getStrValue(1, i, ""),new Location(locData.getStrValue(0, i, ""),
                    locData.getStrValue(2, i, ""),
                    locData.getStrValue(3, i, ""),
                    locData.getStrValue(4, i, "") ,
                    locData.getStrValue(5, i, "")));

        }

    }

    public List getLocationData() throws Exception

    {


        WebResource loader = new WebResource("http://" + kNetServicesServer, WebResource.kGETMethod);


        loader.addParameter("fn", "gl");

        BufferedReader reader = loader.getResource();

        TabReader locData = new TabReader(reader, false);

        List<String> results = new ArrayList<String>();


        for (int i = 1; i < locData.getRowCount(); i++)

        {
            //System.out.println("Loc:" + locData.getStrValue(0, i, "") + "-" + locData.getStrValue(1, i, ""));
            results.add(locData.getStrValue(0, i, "") + "-" + locData.getStrValue(1, i, ""));

        }

        return results;


    }

    private void updateCarrierData() throws Exception

    {


        WebResource loader = new WebResource("http://" + kNetServicesServer, WebResource.kGETMethod);


        loader.addParameter("fn", "cd");

        BufferedReader reader = loader.getResource();

        TabReader carrierData = new TabReader(reader, false);

        carrierCodeTranslatorMap = new HashMap<String, String>();

        carrierInfoMap = new HashMap<String, CarrierInfo>();

        //////System.out.println("got cd rows: "+carrierData.getRowCount());

        //////System.out.println("got cd row 0 size: "+carrierData.getRowSize(0));


        for (int i = 0; i < carrierData.getRowCount(); i++)

        {

           System.out.println("init translator map: "+carrierData.getStrValue(20,i,"")+":"+carrierData.getStrValue(4,i,""));

            carrierCodeTranslatorMap.put(carrierData.getStrValue(20, i, ""), carrierData.getStrValue(4, i, ""));

            if (!carrierInfoMap.containsKey(carrierData.getStrValue(4, i, "")))

            {

                carrierInfoMap.put(carrierData.getStrValue(4, i, ""), CarrierInfo.createCarrier(

                        carrierData.getStrValue(1, i, ""),

                        carrierData.getStrValue(4, i, ""),

                        carrierData.getStrValue(2, i, ""),

                        carrierData.getStrValue(3, i, ""),

                        carrierData.getStrValue(5, i, ""),

                        carrierData.getStrValue(6, i, ""),

                        carrierData.getStrValue(7, i, ""),

                        carrierData.getStrValue(8, i, ""),

                        carrierData.getStrValue(9, i, ""),

                        carrierData.getStrValue(10, i, ""),

                        carrierData.getStrValue(11, i, ""),

                        carrierData.getStrValue(12, i, ""),

                        carrierData.getStrValue(13, i, "0"),

                        carrierData.getStrValue(14, i, "0"),

                        carrierData.getStrValue(15, i, "0"),

                        carrierData.getStrValue(16, i, "0"),

                        carrierData.getStrValue(17, i, "0"),

                        carrierData.getStrValue(18, i, "0"),

                        carrierData.getStrValue(19, i, "0"),

                        carrierData.getStrValue(20, i, ""),

                        carrierData.getStrValue(21, i, "0")));

            }

        }


    }


    private void updatePaymentMap() throws Exception

    {

        paymentMap.clear();

        paymentMap = CSReferenceService.getPaymentTypesMap(new ConnectShipCommunications(ConnectShipCommunications.liveServerURL));


    }

   private static Map<String,String> legacyTranslationMap = new HashMap<String, String>(){
       { put("TANDATA_UPS.UPS.3DA","CONNECTSHIP_UPS.UPS.3DA");
   put("TANDATA_UPS.UPS.2DA","CONNECTSHIP_UPS.UPS.2DA");
    put("TANDATA_UPS.UPS.2AM","CONNECTSHIP_UPS.UPS.2AM");
    put("TANDATA_UPS.UPS.GND","CONNECTSHIP_UPS.UPS.GND");
    put("'TANDATA_UPS.UPS.NAM","CONNECTSHIP_UPS.UPS.NAM");
    put("TANDATA_UPS.UPS.NDA","CONNECTSHIP_UPS.UPS.NDA");
    put("TANDATA_UPS.UPS.NDS","CONNECTSHIP_UPS.UPS.NDS");
    put("TANDATA_UPS.UPS.WEPD","CONNECTSHIP_UPS.UPS.EPD");
    put("TANDATA_UPS.UPS.WEXP","CONNECTSHIP_UPS.UPS.EXP");
    put("TANDATA_UPS.UPS.WEXPPLS","CONNECTSHIP_UPS.UPS.EXPPLS");}};








    public String getTranslatedServiceCode(String currServiceCode) throws Exception

    {
       // System.out.println(carrierCodeTranslatorMap);
        System.out.printf("Translating this code %s xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r\n",currServiceCode);

        if(legacyTranslationMap.containsKey(currServiceCode)){
            return legacyTranslationMap.get(currServiceCode);
        }

       if(currServiceCode.equals("OWD.NOBOX.PICKEDUP")) return currServiceCode;
        if(currServiceCode.equals("OWD.BPD.STANDARD")) return currServiceCode;
        if(currServiceCode.equals("OWD.OSM.DOM")) return currServiceCode;
        if(currServiceCode.equals("OWD.OSM.INTL")) return currServiceCode;
        if(currServiceCode.equals("EASYPOST.PUROLATOR")) return currServiceCode;
        if(currServiceCode.equals("TANDATA_USPS.USPS.PRIORITY")) return "TANDATA_USPS.USPS.PRIORITY_CUBIC";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_GROUND")) return "COM_OWD_FLATRATE_GROUND";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_ECONOMY")) return "COM_OWD_FLATRATE_ECONOMY";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_INTL_EXPD")) return "COM_OWD_FLATRATE_INTL_EXPD";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_INTL_STND")) return "COM_OWD_FLATRATE_INTL_STND";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_NDA")) return "COM_OWD_FLATRATE_NDA";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_2DA")) return "COM_OWD_FLATRATE_2DA";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_DHL_MAX")) return "COM_OWD_FLATRATE_DHL_MAX";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_STANDARD_GROUND")) return "COM_OWD_FLATRATE_STANDARD_GROUND";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_INTL_ECONOMY")) return "COM_OWD_FLATRATE_INTL_ECONOMY";
        if(currServiceCode.equalsIgnoreCase("COM_OWD_FLATRATE_MM")) return "COM_OWD_FLATRATE_MM";

        if(currServiceCode.equalsIgnoreCase("ONTRAC.GROUND")) return "ONTRAC.GROUND";
        if(currServiceCode.equalsIgnoreCase("AXLEHIRE.NDA")) return "AXLEHIRE.NDA";
        if (carrierCodeTranslatorMap.containsKey(currServiceCode))

        {

            //////System.out.println("Translated code for "+currServiceCode+" to "+carrierCodeTranslatorMap.get(currServiceCode));

            return (String) carrierCodeTranslatorMap.get(currServiceCode);


        } else

        {

            if ((new ArrayList<String>(carrierCodeTranslatorMap.values())).contains(currServiceCode))

                return currServiceCode;

            else

            {
                  if(currServiceCode.equals("TANDATA_FEDEXFSMS.FEDEX.IPRI")){
                      return "TANDATA_FEDEXFSMS.FEDEX.IPRI";
                  }
               // JOptionPane.showMessageDialog(ShipperApp.app.shipperForm.getMainPane(), "Ship method " + currServiceCode + " not found - defaulting to UPS Ground.", "Error", JOptionPane.ERROR_MESSAGE);

                //setValue(ShipConfig.THIRD_PARTY_BILLING, "false");
                throw new Exception("Unable to Translase Shipping Code: "+currServiceCode);

            }

        }


    }


    public String getTranslatedCountryName(String currCountryName) throws Exception

    {

        if (getCountryNames().contains(currCountryName))

            return currCountryName;

        else

        {

            return null;

        }

    }


    public List getPendingShipments(Collection fields, String owdOrderNum, String msn, String bundleID) throws Exception

    {


        return CSSearchService.getPendingShipments(fields, owdOrderNum, msn, bundleID);


    }





    private Map<String, String> carrierCodeTranslatorMap = null;

    private Map<String, CarrierInfo> carrierInfoMap = null;

    private Map<String, String> packageMap = new TreeMap<String, String>();

    private Map<String, String> paymentMap = new TreeMap<String, String>();

    private Map<String, String> countryMap = new TreeMap<String, String>();

    private Map<String, String> serviceMap = new TreeMap<String, String>();

    private Map<String, String> carrierMap = new TreeMap<String, String>();

    private Map<String, String> shipperMap = new TreeMap<String, String>();

    private Map<String, String> scaleMap = new TreeMap<String, String>();

    private Map<String, String> labelPrinterMap = new TreeMap<String, String>();

}
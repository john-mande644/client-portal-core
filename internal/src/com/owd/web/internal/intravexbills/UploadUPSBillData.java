package com.owd.web.internal.intravexbills;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DelimitedReader;

import java.util.Map;
import java.util.TreeMap;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 31, 2003
 * Time: 8:41:50 AM
 * To change this template use Options | File Templates.
 */
public class UploadUPSBillData {
private final static Logger log =  LogManager.getLogger();

    

    public static final int kRecordTypeIndicator = 0;
    public static final int kPlanNumber = 1;
    public static final int kShipperNumber = 2;
    public static final int kInvoiceNumber = 3;
    public static final int kBillDate = 4;
    public static final int kInvoiceAmount = 5;
    public static final int kTrackingNumber = 6;
    public static final int kPickupRecordNumber = 7;
    public static final int kReference1 = 8;
    public static final int kReference2 = 9;
    public static final int kInternetID = 10;
    public static final int kQuantity = 11;
    public static final int kBilledWeight = 12;
    public static final int kActualWeight = 13;
    public static final int kWeightIndicator = 14;
    public static final int kDimensionalWeight = 15;
    public static final int kOversize1 = 16;
    public static final int kOversize2 = 17;
    public static final int kZone = 18;
    public static final int kTransactionCode = 19;
    public static final int kServiceDescription = 20;
    public static final int kBillOption = 21;
    public static final int kPickupDate = 22;
    public static final int kSenderName = 23;
    public static final int kSenderCompanyName = 24;
    public static final int kSenderStreet = 25;
    public static final int kSenderCity = 26;
    public static final int kSenderState = 27;
    public static final int kSenderZip = 28;
    public static final int kReceiverName = 29;
    public static final int kReceiverCompanyName = 30;
    public static final int kReceiverStreet = 31;
    public static final int kReceiverCity = 32;
    public static final int kReceiverState = 33;
    public static final int kReceiverZip = 34;
    public static final int kReceiverCountry = 35;
    public static final int kNetCharges = 36;
    public static final int kIncentive = 37;
    public static final int kDeclaredValue = 38;
    public static final int kSaturdayDelivery = 39;
    public static final int kCOD = 40;
    public static final int kAdditionalHandling = 41;
    public static final int kHazardousMaterial = 42;
    public static final int kEarlyAMSurcharge = 43;
    public static final int kDeliveryConfirmation = 44;
    public static final int kSaturdayPickup = 45;
    public static final int kCallTag = 46;
    public static final int kExtendedDestinationSurcharge = 47;
    public static final int kInvalidAccountCharge = 48;
    public static final int kDeliveryAreaSurcharge = 49;
    public static final int kCurrencySurcharge = 50;
    public static final int kChargebackSurcharge = 51;
    public static final int kPickupFee = 52;
    public static final int kBillingOption = 53;
    public static final int kConsolidatedClearance = 54;
    public static final int kSplitDutyTax = 55;
    public static final int kExportLicenseVerification = 56;
    public static final int kMiscLine1 = 57;
    public static final int kMiscLine2 = 58;
    public static final int kMiscLine3 = 59;
    public static final int kMiscLine4 = 60;
    public static final int kMiscLine5 = 61;


    String UPSServerPrefix = "https://epackage1.ups.com";

    static public Map UPSCodeMap = new TreeMap();

    static {
        UPSCodeMap.put("ADC", "Address Correction");
        UPSCodeMap.put("DCS", "DCS Charges Not Billed");
        UPSCodeMap.put("ADJ", "Adjustment");
        UPSCodeMap.put("DCR", "DCR Charges Not Billed");
        UPSCodeMap.put("ARS", "Authorized Return Service");
        UPSCodeMap.put("ANS", "ANS Charges Not Billed ");
        UPSCodeMap.put("ASD", "Air Shipping Document");
        UPSCodeMap.put("DNS", "DNS Charges Not Billed ");
        UPSCodeMap.put("ASR", "ASR Charges Not Billed");
        UPSCodeMap.put("IDD", "Invoice Due Date");
        UPSCodeMap.put("CBS", "Consignee Billing System");
        UPSCodeMap.put("CEF", "Credit Extension Fee");
        UPSCodeMap.put("COD", "Collect On Demand");
        UPSCodeMap.put("CTG", "Call Tag");
        UPSCodeMap.put("CTR", "Call Tag Refund");
        UPSCodeMap.put("CW2", "Hundredweight (Shipping Doc)");
        UPSCodeMap.put("CWT", "Hundredweight (Pickup Book)");
        UPSCodeMap.put("DCO", "Delivery Confirmation");
        UPSCodeMap.put("ESC", "Economy Service Charge");
        UPSCodeMap.put("ISS", "Internet Shipping System");
        UPSCodeMap.put("GCC", "Global Consolidated Clearance");
        UPSCodeMap.put("LPF", "Late Payment Fee");
        UPSCodeMap.put("GSR", "Guarantee Service Refund");
        UPSCodeMap.put("MAN", "Manifest");
        UPSCodeMap.put("HAZ", "Hazardous Material");
        UPSCodeMap.put("MAN", "Manifest Shipment");
        UPSCodeMap.put("MIS", "Miscellaneous Charge ");
        UPSCodeMap.put("MPF", "Manual Processing Fee");
        UPSCodeMap.put("NPB", "Not Previously Billed");
        UPSCodeMap.put("OTP", "One Time Pickup");
        UPSCodeMap.put("POD", "Proof of Delivery");
        UPSCodeMap.put("PRE", "Saturday Service Charge ");
        UPSCodeMap.put("RES", "Residential Adjustment");
        UPSCodeMap.put("RSV", "Return Services");
        UPSCodeMap.put("SAI", "Sonic Air");
        UPSCodeMap.put("SCC", "Shipping Charge Correction");
        UPSCodeMap.put("SPL", "UPS Branded Packaging Supply");
        UPSCodeMap.put("SRB", "Standard Recording Book");
        UPSCodeMap.put("SVC", "Service Charge");
        UPSCodeMap.put("TAX", "Tax");
        UPSCodeMap.put("GST", "Tax");
        UPSCodeMap.put("HST", "Tax");
        UPSCodeMap.put("IBS", "IBS Service");
        UPSCodeMap.put("WWS", "Worldwide Services");
        UPSCodeMap.put("RSL", "Return To Shipper");
        UPSCodeMap.put("SAT", "Saturday Delivery");
        UPSCodeMap.put("", "Inbound/Other");
    }

    DelimitedReader dataReader = null;


    public UploadUPSBillData() {

    }

    public void init(DelimitedReader rdr) {
        if (rdr == null) return;

        setDataReader(rdr);
        processReader();


    }


    protected void processReader() {

        if (getDataReader() == null) return;

        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            ////log.debug("DH handling row "+row);

        }
    }


    public DelimitedReader getDataReader() {
        return dataReader;
    }

    public void setDataReader(DelimitedReader dataReader) {
        this.dataReader = dataReader;
    }

}

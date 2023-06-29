package com.owd.jobs.archives.VisionMedia;

import com.owd.LogableException;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.Order;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.SftpConnector;
import com.owd.jobs.jobobjects.DelimitedFileOrderHandler;
import com.owd.jobs.jobobjects.OrderProcessingResultsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

public class VisionMediaRealDJob extends OWDStatefulJob implements OrderProcessingResultsHandler {
    private final static int kClientID = 715;
    private final static String kCarrierReference1TagName = "CARRIER_REFERENCE_1";
    private final static String kCarrierReference2TagName = "CARRIER_REFERENCE_2";
    private final static String kPackageIdTagName = "PACKAGE_ID";

    private final static String kFileNamePrefix = "REALD";
    private final static Logger log = LogManager.getLogger();
    private String status = "";
    SftpConnector ftp = new SftpConnector("ftp.owd.com", "visionmedia", "vsn2022$", "ftp", "aes128-ctr,aes128-cbc");

    int kOrder_Reference = 0;
    int kCustomer_Name = 1;
    int kCustomer_Company = 2;
    int kCustomer_Address_1 = 3;
    int kCustomer_Address_2 = 4;
    int kCustomer_City = 5;
    int kCustomer_State = 6;
    int kCustomer_Postal_Code = 7;
    int kCustomer_Country = 8;
    int kCustomer_Phone = 9;
    int kCustomer_Email = 10;
    int kOrder_Paid = 11;
    int kCustomer_CC = 12;
    int kCC_Expiration_Month = 13;
    int kCC_Expiration_Year = 14;
    int kOrder_Date = 15;
    int kOrder_Retailer = 16;

    int kOrder_Sales_Order = 17;
    int kShip_Name = 18;
    int kShip_Company = 19;
    int kShip_Address_1 = 20;
    int kShip_Address_2 = 21;
    int kShip_City = 22;
    int kShip_State = 23;
    int kShip_Postal_Code = 24;
    int kShip_Country = 25;
    int kShip_Phone = 26;
    int kShip_Email = 27;
    int kShip_Method = 28;
    int kShip_Account = 29;
    int kIs_Gift = 30;
    int kGift_Message = 31;
    int kComments = 32;
    int kWarehouse_Notes = 33;
    int kOrder_Source = 34;
    int kOrder_Taker = 35;
    int kSales_Tax = 36;
    int kShip_Handling_Fee = 37;
    int kOrder_Discount = 38;
    int kProduct_SKU = 40;
    int kProduct_Quantity = 41;
    int kProduct_Unit_Price = 42;
    int kProduct_Description = 43;
    int kCOD_Shipment = 44;     //v1.1
    int kSaturdayDelivery = 45;
    int kShipBillingMethod = 46;
    int kBilltoShip_Name = 47;
    int kBilltoShip_Company = 48;
    int kBilltoShip_Address_1 = 49;
    int kBilltoShip_Address_2 = 50;
    int kBilltoShip_City = 51;
    int kBilltoShip_State = 52;
    int kBilltoShip_Postal_Code = 53;
    int kBilltoShip_Phone = 54;
    int kBilltoShip_Country = 55;
    int kBilltoShip_Account = 56;
    int kPONumber = 57;
    int kDeclared_Value = 58;
    int kSignatureRequired = 59;
    int kProductAdditionalNote = 60;
    int kForcedLineTotal = 61;
    int kFacilityPolicy = 62;
    int kProductCustomsValue = 63;
    int kOrderTemplate = 64;
    int kCompanyOverride = 65;

    // VisionMedia custom fields
    int kPackageId = 66;
    int kCarrierReference1 = 67;
    int kCarrierReference2 = 68;

    public static void main(String[] args) throws Exception {
        run();
    }

    @Override
    public void internalExecute() {
        try {

            List<String> fileNames = ftp.getFileNames("files/orders");
            for (String file : fileNames) {
                if (!file.startsWith(kFileNamePrefix)) {
                    return;
                }

                DelimitedFileOrderHandler fileHandler = new DelimitedFileOrderHandler(this, kClientID, new DelimitedReader(',', new BufferedReader(new StringReader(ftp.getFileData(file).toString())), true), file) {

                    @Override
                    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size, String externalLineReference) throws Exception {
                        super.addLineItem(order, sku, qty, unitPrice, linePrice, desc, color, size, externalLineReference);
                    }

                    @Override
                    public String getOrderReferenceForRow(int rowNumber, String defaultValue) {
                        return super.getOrderReferenceForRow(rowNumber, defaultValue);
                    }

                    @Override
                    public Order loadOrder(Order order, int orderIndex) throws Exception {
                        //log.debug("in DH loadOrder");
                        if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
                            throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());

                        OrderData data = (OrderData) getOrderPositionList().get(orderIndex);
                        DelimitedReader rdr = getDataReader();

                        for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
                            //log.debug("DH loading row " + row);
                            if (row == data.rowIndexStart) {
                                //first row
                                order.order_type = "";
                                order.setBackorderRule(OrderXMLDoc.kPartialShip);
                                order.order_refnum = rdr.getStrValue(kOrder_Reference, row, "");
                                if (order.order_refnum.length() < 1) {
                                    throw new Exception("Client order reference was invalid or not found.");
                                }

                                log.debug("DH loading ref " + order.order_refnum);

                                order.getBillingContact().setName(rdr.getStrValue(kCustomer_Name, row, ""));
                                order.getBillingContact().email = rdr.getStrValue(kCustomer_Email, row, "");
                                order.getBillingContact().phone = rdr.getStrValue(kCustomer_Phone, row, "");

                                order.getBillingAddress().company_name = rdr.getStrValue(kCustomer_Company, row, ".");
                                if (order.getBillingAddress().company_name.trim().length() < 1)
                                    order.getBillingAddress().company_name = ".";
                                order.getBillingAddress().address_one = rdr.getStrValue(kCustomer_Address_1, row, "");
                                order.getBillingAddress().address_two = rdr.getStrValue(kCustomer_Address_2, row, "");
                                order.getBillingAddress().city = rdr.getStrValue(kCustomer_City, row, "");
                                order.getBillingAddress().state = rdr.getStrValue(kCustomer_State, row, "");
                                order.getBillingAddress().zip = rdr.getStrValue(kCustomer_Postal_Code, row, "");
                                order.getBillingAddress().country = rdr.getStrValue(kCustomer_Country, row, "");

                                order.getShippingContact().setName(rdr.getStrValue(kShip_Name, row, ""));
                                order.getShippingContact().email = rdr.getStrValue(kShip_Email, row, "");
                                order.getShippingContact().phone = rdr.getStrValue(kShip_Phone, row, "");


                                order.getShippingAddress().company_name = rdr.getStrValue(kShip_Company, row, ".");
                                if (order.getShippingAddress().company_name.trim().length() < 1)
                                    order.getShippingAddress().company_name = ".";

                                order.getShippingAddress().address_one = rdr.getStrValue(kShip_Address_1, row, "");
                                order.getShippingAddress().address_two = rdr.getStrValue(kShip_Address_2, row, "");
                                order.getShippingAddress().city = rdr.getStrValue(kShip_City, row, "");
                                order.getShippingAddress().state = rdr.getStrValue(kShip_State, row, "");
                                order.getShippingAddress().zip = rdr.getStrValue(kShip_Postal_Code, row, "");
                                order.getShippingAddress().country = rdr.getStrValue(kShip_Country, row, "");

                                if (!CountryNames.exists(order.getBillingAddress().country)) {
                                    throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country);
                                }
                                if (!CountryNames.exists(order.getShippingAddress().country)) {
                                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                                }
                                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                                order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);
                                order.total_tax_cost = rdr.getFloatValue(kSales_Tax, row, 0.00f);
                                order.discount = -1 * Math.abs(rdr.getFloatValue(kOrder_Discount, row, 0.00f));
                                order.getShippingInfo().comments = rdr.getStrValue(kComments, row, "");
                                order.getShippingInfo().whse_notes = rdr.getStrValue(kWarehouse_Notes, row, "");

                                order.getShippingInfo().ss_saturday = rdr.getStrValue(kShip_Handling_Fee, row, "0").equals("1") ? "1" : "0";
                                float cod_charge = OWDUtilities.roundFloat(rdr.getFloatValue(kCOD_Shipment, row, 0.00f));
                                if (cod_charge > 0.00f) {
                                    throw new Exception("COD billing is not supported.");
                                }

                                order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);
                                log.debug(rdr.getFloatValue(kDeclared_Value, row, 0.00f));
                                if (rdr.getFloatValue(kDeclared_Value, row, 0.00f) > 0.00) {
                                    log.debug("in declared");
                                    order.getShippingInfo().ss_declared_value = "1";
                                    order.getShippingInfo().declared_value = rdr.getStrValue(kDeclared_Value, row, "0");
                                }

                                //bill to com.owd.api.client account
                                if (rdr.getStrValue(kShip_Account, row, "").length() > 0) {
                                    order.getShippingInfo().setShipOptions(rdr.getStrValue(kShip_Method, row, ""), "Third Party Billing", rdr.getStrValue(kShip_Account, row, ""));
                                } else {
                                    log.debug("Checking shipping for ship method " + rdr.getStrValue(kShip_Method, row, ""));
                                    //bill to non-OWD, non-com.owd.api.client account
                                    if (("Consignee".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                                        log.debug("Shipping Consignee");
                                        order.getShippingInfo().setShipOptions(rdr.getStrValue(kShip_Method, row, ""), "Freight Collect", "");

                                    } else if (("Freight Collect".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                                        log.debug("Shipping Freight Collect : " + rdr.getStrValue(kBilltoShip_Account, row, ""));
                                        order.getShippingInfo().setShipOptions(rdr.getStrValue(kShip_Method, row, ""), "Freight Collect", rdr.getStrValue(kBilltoShip_Account, row, ""));
                                        //order.getShippingInfo().shipperAddress

                                    } else if (("Prepaid".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, ""))) || ("".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                                        log.debug("Shipping Normal");
                                        translateShipMethod(order, rdr.getStrValue(kShip_Method, row, ""));

                                    } else if (("Third Party Billing".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                                        log.debug("Shipping 3rd Party");
                                        order.getShippingInfo().setShipOptions(rdr.getStrValue(kShip_Method, row, ""), "Third Party Billing", rdr.getStrValue(kBilltoShip_Account, row, ""));

                                        order.getShippingInfo().shipperAddress = new Address(rdr.getStrValue(kBilltoShip_Company, row, ""),
                                                rdr.getStrValue(kBilltoShip_Address_1, row, ""),
                                                rdr.getStrValue(kBilltoShip_Address_2, row, ""),
                                                rdr.getStrValue(kBilltoShip_City, row, ""),
                                                rdr.getStrValue(kBilltoShip_State, row, ""),
                                                rdr.getStrValue(kBilltoShip_Postal_Code, row, ""),
                                                CountryNames.getCountryName(rdr.getStrValue(kBilltoShip_Country, row, "US")));

                                        order.getShippingInfo().shipperContact = new Contact(rdr.getStrValue(kBilltoShip_Name, row, ""),
                                                rdr.getStrValue(kBilltoShip_Phone, row, "605-845-7172"), "", "", "");

                                    } else {
                                        throw new Exception("Invalid value for shipment billing method");
                                    }
                                }

                                order.ship_operator = rdr.getStrValue(kOrder_Taker, row, "");
                                log.debug("Checking is_paid translation for value:" + rdr.getStrValue(kOrder_Paid, row, "nuthin"));
                                log.debug("...translates to int value:" + rdr.getIntValue(kOrder_Paid, row, -1));
                                order.is_paid = rdr.getIntValue(kOrder_Paid, row, 0) > 0 ? 1 : 0;
                                order.is_gift = "" + (rdr.getIntValue(kIs_Gift, row, 0) > 0 ? 1 : 0);
                                order.gift_message = rdr.getStrValue(kGift_Message, row, "");
                                order.cc_num = rdr.getStrValue(kCustomer_CC, row, "");
                                order.cc_exp_mon = rdr.getIntValue(kCC_Expiration_Month, row, 0);
                                order.cc_exp_year = rdr.getIntValue(kCC_Expiration_Year, row, 0);
                                // TODO: What do we do with this value?
                                // order.actual_order_date = rdr.getStrValue(kOrder_Date, row,"");

                                order.po_num = rdr.getStrValue(kPONumber, row, "");
                                if (order.po_num.equals("")) {
                                    order.po_num = rdr.getStrValue(kPONumber + 1, row, "");
                                }

                                order.group_name = rdr.getStrValue(kOrder_Retailer, row, "");
                                String facility = rdr.getStrValue(kFacilityPolicy, row, "");
                                if (rdr.getStrValue(kOrderTemplate, row, "").length() > 0) {

                                    order.template = rdr.getStrValue(kOrderTemplate, row, "");
                                    if (!order.isTemplateValid()) {
                                        throw new Exception(order.template + " is not a valid template name. Please update");
                                    }
                                }
                                order.companyOverride = rdr.getStrValue(kCompanyOverride, row, "");

                                String clientCode = FacilitiesManager.getLocationCodeForClient(Integer.parseInt(order.clientID));

                                if (FacilitiesManager.getActiveFacilityCodes().contains(clientCode)) {
                                    order.setFacilityCode(clientCode);
                                    order.setFacilityPolicy(clientCode);
                                } else {
                                    //must be multi facility client
                                    if (FacilitiesManager.getActiveFacilityCodes().contains(facility)) {

                                        order.setFacilityCode(facility);
                                        order.setFacilityPolicy(facility);
                                    } else {
                                        throw new Exception("Facility code " + facility + " is not valid; please update value and try again.");
                                    }
                                }

                                order.getShippingInfo().ss_proof_delivery = rdr.getStrValue(kSignatureRequired, row, "0");
                                try {
                                    Integer testint = new Integer(order.getShippingInfo().ss_proof_delivery);
                                } catch (Exception exnint) {
                                    order.getShippingInfo().ss_proof_delivery = "0";
                                }
                            }

                            float itemCustomsValue = 0.00f;
                            log.debug(rdr.getFloatValue(kProductCustomsValue, row, 0.00f));
                            if (rdr.getFloatValue(kProductCustomsValue, row, 0.00f) > 0.00) {
                                log.debug("in customs product value");
                                itemCustomsValue = rdr.getFloatValue(kProductCustomsValue, row, 0.00f);
                            }

                            //product data collected for every row
                            addLineItem(order, rdr.getStrValue(kProduct_SKU, row, ""),
                                    rdr.getStrValue(kProduct_Quantity, row, "0"),
                                    rdr.getStrValue(kProduct_Unit_Price, row, "0.00"),
                                    rdr.getStrValue(kForcedLineTotal, row, "0.00"),
                                    rdr.getStrValue(kProduct_Description, row, ""),
                                    "", "", rdr.getStrValue(kProductAdditionalNote, row, ""));

                            // Special tags for VisionMedia
                            order.addTag(kCarrierReference1TagName, rdr.getStrValue(kCarrierReference1, row, ""));
                            order.addTag(kCarrierReference2TagName, rdr.getStrValue(kCarrierReference2, row, ""));
                            order.addTag(kPackageIdTagName, rdr.getStrValue(kPackageId, row, ""));
                        }

                        return order;
                    }

                    @Override
                    public void translateShipMethod(Order order, String oldMethod) throws Exception {
                        if (oldMethod.equals("Fedex Smartpost")) {
                            order.getShippingInfo().setShipOptions("FedEx SmartPost Standard Mail", "Prepaid", "");
                        }
                        if (oldMethod.equals("Economy")) {
                            order.getShippingInfo().setShipOptions("Economy", "Prepaid", "");
                        }
                        if (oldMethod.contains("UPS Ground")) {
                            order.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", order.getShippingInfo().carr_service);
                        }
                    }
                };

                fileHandler.processOrders();
                ftp.deleteFile(file);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "GENERIC",
                    "" + kClientID,
                    this.getClass().getName(),
                    LogableException.errorTypes.ORDER_IMPORT);
        }

    }

    @Override
    public void onOrderImportSuccess(Order order) throws Exception {

    }

    @Override
    public void onOrderImportDuplicate(Order order) throws Exception {

    }

    @Override
    public void onOrderImportFailure(Order order) throws Exception {

    }

    @Override
    public void onOrderImportFailure(String message) throws Exception {

    }

    @Override
    public void onOrderImportBatchSuccess(String batchRef) throws Exception {

    }

    @Override
    public void onOrderImportBatchFailure(String batchRef, String message) throws Exception {

    }

}

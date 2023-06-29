package com.owd.jobs.clients;

import com.owd.core.business.order.Event;
import com.owd.core.managers.CaseTrackerManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException;
import com.owd.core.*;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.DelimitedFileOrderHandler;
import com.owd.jobs.jobobjects.FtpConnector;
import com.owd.jobs.jobobjects.OrderProcessingResultsHandler;
import com.owd.jobs.jobobjects.batchimporters.AlexBlakeUploadOrdersData;
import ipworks.DirEntry;
import ipworks.Ftp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class BumblerideOrdersImportJob extends OWDStatefulJob implements OrderProcessingResultsHandler {
private final static Logger log =  LogManager.getLogger();

    protected static final int kClientID = 575;
    protected String status = "";
    FtpConnector ftp = new FtpConnector("ftp.owd.com", "bumbleride", "Hd7J90*", "INB_TO_OWD");

    static  Map<String,String> vendorComplianceCustomerIds = new HashMap<String,String>();

    static {
        vendorComplianceCustomerIds.put("000115","2Modern");
        vendorComplianceCustomerIds.put("A00262","Alaska Baby Rentals");
     //   vendorComplianceCustomerIds.put("A00103","Albee Baby Carriage");
        vendorComplianceCustomerIds.put("A00255","Amazon.com");
        vendorComplianceCustomerIds.put("B00247","Babies R Us Dropship");
        vendorComplianceCustomerIds.put("B00106","Baby Age");
        vendorComplianceCustomerIds.put("44214","Buy Buy Baby");
        vendorComplianceCustomerIds.put("D00131","Diapers.com");
        vendorComplianceCustomerIds.put("E00100","El Bambi");
        vendorComplianceCustomerIds.put("G00149","Etailz");
    //    vendorComplianceCustomerIds.put("L00103","Little Folks Shoppe");
    //    vendorComplianceCustomerIds.put("M00108","Modern Nursery/Pokkadots");
    //    vendorComplianceCustomerIds.put("O00130","Our Baby Our World");
        vendorComplianceCustomerIds.put("P00104","Peppy Parents");
     //   vendorComplianceCustomerIds.put("S00116","StrollerDepot");

    }

    static public void main(String[] args) throws Exception {

        run();


    }


    @Override
    public void onOrderImportSuccess(Order order) throws Exception {

        String caseId =null;


        if(order.isWorkOrder)
        {

            String vendorName = order.getTagValue(TagUtilities.kVendorComplianceReference);
             String link = null;

            if(vendorName.equalsIgnoreCase("Amazon.com")) {
                link = "http://vendorguides.s3.amazonaws.com/bumbleride/Amazon.pdf";
            }

            if(vendorName.equalsIgnoreCase("Baby Age") ){
                link = "http://vendorguides.s3.amazonaws.com/bumbleride/BabyAge%20Routing%20Guide.doc";

        }
            if(link!=null) {
                String body = "Bumbleride Order Ref: " + order.getClientOrderReference() + " OWD Ref: " + order.orderNum + " PO: " + order.po_num + "\n\n\n" +
                        " - DC: Follow routing guide for Bumbleride customer " + order.getTagValue(TagUtilities.kVendorComplianceReference) + "\n" +
                        " - DC: Use this link to download routing guide: "+link+"\n"+
                        " - DC: Please provide contents in each box for multiple piece shipments.\n";

                 caseId = CasetrackerAPI.createCasetrackerCaseForWarehouse("Bumbleride Vendor Compliance - "
                        + order.getTagValue(TagUtilities.kVendorComplianceReference) + " - OWD Ref/PO: " + order.orderNum + " / " + order.po_num, body, kClientID, order.getFacilityCode());
            }else {
                String body = "Bumbleride Order Ref: " + order.getClientOrderReference() + " OWD Ref: " + order.orderNum + " PO: " + order.po_num + "\n\n\n" +
                        " - DC: Follow routing guide for Bumbleride customer " + order.getTagValue(TagUtilities.kVendorComplianceReference) + "\n" +
                        " - DC: Please provide contents in each box for multiple piece shipments.\n";

                caseId = CaseTrackerManager.createNewCaseForClient("Bumbleride Vendor Compliance - "
                        + order.getTagValue(TagUtilities.kVendorComplianceReference) + " - OWD Ref/PO: " + order.orderNum + " / " + order.po_num, body, ""+kClientID);


            }
            if(caseId != null) {
                Event.addOrderEvent(new Integer(order.orderID), Event.kEventTypeGeneral, "Casetracker ID: "+caseId+" // Marked Vendor Compliance for "+order.getTagValue(TagUtilities.kVendorComplianceReference), "Bumbleride Order Importer");

            }

        }else if(order.is_future_ship==1) {

            caseId = CaseTrackerManager.createNewCaseForClient("Bumbleride Non-Fedex Held Order - OWD Ref/PO: " + order.orderNum + " / " + order.po_num, "Non-fedex order - review and release when approved.", ""+kClientID);


            if(caseId != null) {
                Event.addOrderEvent(new Integer(order.orderID), Event.kEventTypeGeneral, "Casetracker ID: "+caseId+" // Marked for review due to non-Fedex ship method", "Bumbleride Order Importer");

            }
        }


    }

    @Override
    public void onOrderImportFailure(Order order) throws Exception {

    }

    @Override
    public void onOrderImportFailure(String message) throws Exception {

    }

    @Override    public void onOrderImportDuplicate(Order order) throws Exception {

    }

    @Override
    public void onOrderImportBatchSuccess(String batchReference) throws Exception {


        ftp.renameFile("./ARCHIVE/" + batchReference);
    }

    @Override
    public void onOrderImportBatchFailure(String batchReference, String message) throws Exception {

    }

    public void internalExecute() {

        try {
            List<String> fileNames = ftp.getFileNames();
            for (String file : fileNames) {
                DelimitedFileOrderHandler fileHandler = new DelimitedFileOrderHandler(this, kClientID, new CSVReader(new BufferedReader(new StringReader(ftp.getFileData(file).toString())), true), file) {
                    int kORDERID = 0;
                    int kCLIENTCUSTOMERID = 1;
                    int kSHIPEMAIL = 2;
                    int kSHIPBUSINESSNAME = 3;
                    int kSHIPNAME = 4;
                    int kSHIPADDRESS1 = 5;
                    int kSHIPADDRESS2 = 6;
                    int kSHIPCITY = 7;
                    int kSHIPSTATE = 8;
                    int kSHIPZIP = 9;
                    int kSHIPCOUNTRY = 10;
                    int kSHIPPHONE = 11;
                    int kSHIPRESIDENTIALFLAG = 12;
                    int kSHIPMETHOD = 13;
                    int kORDERDATE = 14;
                    int kPRODUCTID = 15;
                    int kUNITPRICE = 16;
                    int kQUANTITY = 17;
                    int kSHIPCOMMENT = 18;
                    int kGIFTMESSAGE = 19;
                    int kBILLBUSINESSNAME = 20;
                    int kBILLNAME = 21;
                    int kBILLADDRESS1 = 22;
                    int kBILLADDRESS2 = 23;
                    int kBILLCITY = 24;
                    int kBILLSTATE = 25;
                    int kBILLZIP = 26;
                    int kBILLCOUNTRY = 27;
                    int kPAYMENTTERMS = 28;
                    int kREQUESTEDSHIPDATE = 29;
                    int kCANCELDATE = 30;
                    int kPONUMBER = 31;
                    int kTHIRDPARTYACCOUNT = 32;
                    int kSTORENO = 33;
                    int kUPC = 34;


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
                                order.order_type = "BUMBLERIDE";
                                order.order_refnum = rdr.getStrValue(kORDERID, row, "");
                                if (order.order_refnum.length() < 1) {
                                    throw new Exception("Client order reference was invalid or not found.");
                                }
                                //response.add(order.order_refnum);
                                //log.debug("DH loading ref " + order.order_refnum);

                                order.getBillingContact().setName(rdr.getStrValue(kBILLNAME, row, ""));

                                order.getBillingAddress().company_name = rdr.getStrValue(kBILLBUSINESSNAME, row, ".");
                                if (order.getBillingAddress().company_name.trim().length() < 1)
                                    order.getBillingAddress().company_name = ".";
                                order.getBillingAddress().address_one = rdr.getStrValue(kBILLADDRESS1, row, "");
                                order.getBillingAddress().address_two = rdr.getStrValue(kBILLADDRESS2, row, "");
                                order.getBillingAddress().city = rdr.getStrValue(kBILLCITY, row, "");
                                order.getBillingAddress().state = rdr.getStrValue(kBILLSTATE, row, "");
                                order.getBillingAddress().zip = rdr.getStrValue(kBILLZIP, row, "");
                                order.getBillingAddress().country = rdr.getStrValue(kBILLCOUNTRY, row, "");


                                order.getShippingContact().setName(rdr.getStrValue(kSHIPNAME, row, ""));
                                order.getShippingContact().email = rdr.getStrValue(kSHIPEMAIL, row, "");
                                order.getShippingContact().phone = rdr.getStrValue(kSHIPPHONE, row, "");

                                order.getShippingAddress().company_name = rdr.getStrValue(kSHIPBUSINESSNAME, row, ".");
                                if (order.getShippingAddress().company_name.trim().length() < 1)
                                    order.getShippingAddress().company_name = ".";

                                order.getShippingAddress().address_one = rdr.getStrValue(kSHIPADDRESS1, row, "");
                                order.getShippingAddress().address_two = rdr.getStrValue(kSHIPADDRESS2, row, "");
                                order.getShippingAddress().city = rdr.getStrValue(kSHIPCITY, row, "");
                                order.getShippingAddress().state = rdr.getStrValue(kSHIPSTATE, row, "");
                                order.getShippingAddress().zip = rdr.getStrValue(kSHIPZIP, row, "");
                                order.getShippingAddress().country = rdr.getStrValue(kSHIPCOUNTRY, row, "");
                                if (order.getShippingAddress().state.equalsIgnoreCase("PUERTO RICO")) {
                                    order.getShippingAddress().state = "PR";
                                    order.getShippingAddress().country = "PUERTO RICO";
                                }
                                if (!CountryNames.exists(order.getBillingAddress().country)) {
                                    throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country);
                                }
                                if (!CountryNames.exists(order.getShippingAddress().country)) {
                                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                                }
                                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                                //log.debug("Shipping 3rd Party");
                                order.getShippingInfo().carr_service = rdr.getStrValue(kTHIRDPARTYACCOUNT, row, "");


                                order.is_paid = 1;


                                order.po_num = rdr.getStrValue(kPONUMBER, row, "");
                                String customerId = rdr.getStrValue(kCLIENTCUSTOMERID,row,"");
                                if(vendorComplianceCustomerIds.keySet().contains(customerId))
                                {
                                    order.addTag(TagUtilities.kVendorComplianceReference,vendorComplianceCustomerIds.get(customerId));
                                    order.isWorkOrder=true;

                                }


                                translateBumblerideShipMethod(order, rdr.getStrValue(kSHIPMETHOD, row, ""));


                            }
                            String sku = rdr.getStrValue(kPRODUCTID, row, "");


                            //product data collected for every row
                            addLineItem(order, sku,
                                    rdr.getStrValue(kQUANTITY, row, ""),
                                    "0.00",
                                    "0.00",
                                    "",
                                    "", "", "");


                        }


                        return order;
                    }

                    @Override
                    public void translateShipMethod(Order order, String oldMethod) throws Exception {
                     //no-op, already done.
                    }

                    public void translateBumblerideShipMethod(Order order, String oldMethod) throws Exception {

                        if (oldMethod.equals("Retailer Routing Guide") || order.tagExists(TagUtilities.kVendorComplianceReference) || order.getShippingAddress().getCountry().equals("CANADA")) {
                            order.getShippingInfo().setShipOptions("LTL", "Prepaid", "");

                        } else if (oldMethod.equals("FEDEXG")) {
                            order.getShippingInfo().setShipOptions("FedEx Ground", "Prepaid", "");

                        } else if (oldMethod.equals("UPS Ground")) {

                            order.getShippingInfo().setShipOptions("UPS Ground", "Third Party Billing", order.getShippingInfo().carr_service);
                            order.is_future_ship=1;
                        } else if (oldMethod.equals("FEDEX2DA")) {


                            order.getShippingInfo().setShipOptions("FedEx 2Day", "Third Party Billing", order.getShippingInfo().carr_service);
                        } else if (oldMethod.equals("FedEx Ground")) {


                            order.getShippingInfo().setShipOptions("FedEx Ground", "Third Party Billing", order.getShippingInfo().carr_service);
                        } else if (oldMethod.equals("FGIS")) {
                            order.getShippingInfo().setShipOptions("FedEx Ground", "Third Party Billing", order.getShippingInfo().carr_service);
                        } else
                        {
                            order.getShippingInfo().setShipOptions("LTL", "Prepaid", "");

                        }


                    }
                };

                fileHandler.processOrders();

            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "GENERIC",
                    ""+kClientID,
                    this.getClass().getName(),
                    LogableException.errorTypes.ORDER_IMPORT);
        }
    }


     public boolean reportShipment(int orderId)  {
          boolean success = false;


        return success;
     }
}

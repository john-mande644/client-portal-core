package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
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

public final class LivingoodCheckoutChampOrdersImportJob extends OWDStatefulJob implements OrderProcessingResultsHandler {
    private final static int kClientID = 652;
    private final String ORDER_TYPE = "checkout_champ";
    private final static Logger log = LogManager.getLogger();
    SftpConnector ftp = new SftpConnector("ftp.owd.com", "livingood", "DLG2022ftp!", "ftp", "aes128-ctr,aes128-cbc");

    int kOrder_Reference = 0;
    int kCustomer_FirstName = 1;
    int kCustomer_LastName = 2;
    int kCustomer_Company = 3;
    int kCustomer_Address_1 = 4;
    int kCustomer_Address_2 = 5;
    int kCustomer_City = 6;
    int kCustomer_State = 7;
    int kCustomer_Country = 8;
    int kCustomer_Postal_Code = 9;
    int kShip_FirstName = 10;
    int kShip_LastName = 11;
    int kShip_Company = 12;
    int kShip_Address_1 = 13;
    int kShip_Address_2 = 14;
    int kShip_City = 15;
    int kShip_State = 16;
    int kShip_Country = 17;
    int kShip_Postal_Code = 18;
    int kProduct_Description = 19;
    int kProduct_SKU = 20;
    int kProduct_Quantity = 21;
    int kProduct_Unit_Price = 22;

    public static void main(String[] args) throws Exception {
        run();
    }

    @Override
    public void internalExecute() {
        try {
            String remotePath = "files/orders";
            if (System.getProperty("com.owd.environment").equalsIgnoreCase("test") ){
                remotePath = "files/testOrders";
            }

            List<String> fileNames = ftp.getFileNames(remotePath);
            for (String file : fileNames) {
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
                                order.setBackorderRule(OrderXMLDoc.kBackOrderAll);
                                order.order_refnum = rdr.getStrValue(kOrder_Reference, row, "");
                                if (order.order_refnum.length() < 1) {
                                    throw new Exception("Client order reference was invalid or not found.");
                                }

                                log.debug("DH loading ref " + order.order_refnum);

                                order.getBillingContact().setName(rdr.getStrValue(kCustomer_FirstName, row, "") + rdr.getStrValue(kCustomer_LastName, row, ""));

                                order.getBillingAddress().company_name = rdr.getStrValue(kCustomer_Company, row, ".");
                                if (order.getBillingAddress().company_name.trim().length() < 1)
                                    order.getBillingAddress().company_name = ".";
                                order.getBillingAddress().address_one = rdr.getStrValue(kCustomer_Address_1, row, "");
                                order.getBillingAddress().address_two = rdr.getStrValue(kCustomer_Address_2, row, "");
                                order.getBillingAddress().city = rdr.getStrValue(kCustomer_City, row, "");
                                order.getBillingAddress().state = rdr.getStrValue(kCustomer_State, row, "");
                                order.getBillingAddress().zip = rdr.getStrValue(kCustomer_Postal_Code, row, "");
                                order.getBillingAddress().country = rdr.getStrValue(kCustomer_Country, row, "");

                                order.getShippingContact().setName(rdr.getStrValue(kShip_FirstName, row, "") + rdr.getStrValue(kShip_LastName, row, ""));


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

                                order.order_type = ORDER_TYPE;

                                String clientCode = FacilitiesManager.getLocationCodeForClient(Integer.parseInt(order.clientID));

                                if (FacilitiesManager.getActiveFacilityCodes().contains(clientCode)) {
                                    order.setFacilityCode(clientCode);
                                    order.setFacilityPolicy(clientCode);
                                } else {
                                    throw new Exception("Facility code is not valid, order cannot be imported.");
                                }
                            }

                            //product data collected for every row
                            addLineItem(order, rdr.getStrValue(kProduct_SKU, row, ""),
                                    rdr.getStrValue(kProduct_Quantity, row, "0"),
                                    rdr.getStrValue(kProduct_Unit_Price, row, "0.00"),
                                    "0.00",
                                    rdr.getStrValue(kProduct_Description, row, ""),
                                    "", "", "");
                        }

                        return order;
                    }

                    @Override
                    public void translateShipMethod(Order order, String oldMethod) throws Exception {
                        if (oldMethod.equals("default")) {
                            order.getShippingInfo().setShipOptions("Economy", "Prepaid", "");
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

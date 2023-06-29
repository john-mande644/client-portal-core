package com.owd.jobs.jobobjects.rakuten;

import com.owd.LogableException;
import com.owd.core.*;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.csXml.OrderRater;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.jobobjects.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class RakutenOrderProcessor implements OrderProcessingResultsHandler,OrderProcessingShipmentConfirmationHandler {
    private final static Logger log = LogManager.getLogger();

    protected final int kClientID;
    protected final FtpConnector ftp;

    protected String status = "";
    public final String orderType = "Rakuten";


    static public void main(String[] args) throws Exception {


    }

    public void reportShipment(OwdOrder order) throws Exception {
        DateFormat formatter = new SimpleDateFormat("yyyMMdd", Locale.US);
        DecimalFormat numformatter = new DecimalFormat("000000");


        try {
            StringBuilder result = new StringBuilder();
            String reportSQL = "select o.order_refnum, l.cust_refnum, l.quantity_actual, s.carr_service,\n" +
                    "case when ISNULL(o.tracking_nums,'')='NONE' then  'TRACKINGNOTAVAILABLE' else isnull(o.tracking_nums,'') end as 'Tracking',\n" +
                    "convert(varchar,o.shipped_on,101) as 'ShipDate',\n" +
                    "o.shipped_on, order_type \n" +
                    "from owd_order o, owd_line_item l, owd_order_ship_info s \n" +
                    "where o.order_id = l.order_fkey\n" +
                    "and o.order_id = s.order_fkey\n" +
                    "and o.client_fkey = " + kClientID + "\n" +
                    "and order_type = '" + orderType + "'\n" +
                    "and ISNULL(l.cust_refnum,'')<>''" + "\n" +
                    "and o.order_id = ?";



            PreparedStatement ps = HibernateSession.getPreparedStatement(reportSQL);
            ps.setInt(1, order.getOrderId());

            ResultSet rs = ps.executeQuery();

            boolean gotResults = false;

            while (rs.next()) {
                if (!gotResults) {
                    result.append("receipt-id" + "\t");
                    result.append("receipt-item-id" + "\t");
                    result.append("quantity" + "\t");
                    result.append("tracking-type" + "\t");
                    result.append("tracking-number" + "\t");
                    result.append("ship-date" + "\r\n");
                }

                result.append(rs.getString(1).startsWith("RAK") ? rs.getString(1).substring(3, rs.getString(1).length()) : "" + "\t");
                result.append(rs.getString(2) + "\t");
                result.append(rs.getString(3) + "\t");
                result.append(translateOutboundShipMethod(rs.getString(4)) + "\t");
                result.append(rs.getString(5) + "\t");
                result.append(rs.getString(6) + "\r\n");

                gotResults = true;
            }

            HibernateSession.closePreparedStatement();

            //send to ftp site
            if (gotResults) {
                log.debug("Results: ");
                log.debug(result.toString());

                Calendar cal = Calendar.getInstance();

                String filename = "RAKUTEN" + formatter.format(cal.getTime()) + "-"
                        + numformatter.format(cal.getTimeInMillis())
                        + ".txt";
                log.debug("generated file name:" + filename);

                ftp.putFileData(filename, "/Fulfillment", result.toString().getBytes());
            }
        } catch (Exception th) {
            throw new LogableException(th, th.getMessage(),
                    "GENERIC",
                    "" + kClientID,
                    this.getClass().getName(),
                    LogableException.errorTypes.SHIPMENT_NOTIFICATION);

        }
    }


    private RakutenOrderProcessor() {
        kClientID = 0;
        ftp = null;
    }

    public RakutenOrderProcessor(int clientID, String host, String login, String password) {

        kClientID = clientID;
        ftp = new FtpActiveConnector(host, login, password, "Orders");
    }

    public void onOrderImportSuccess(Order order) throws Exception {
         //all good!
    }
    public void onOrderImportDuplicate(Order order) throws Exception {

    }

    public void onOrderImportFailure(Order order) throws Exception {
        LogableException.logException(new Exception(), order.last_error+":"+order.last_payment_error,
                order.order_refnum,
                "" + kClientID,
                this.getClass().getName(),
                LogableException.errorTypes.ORDER_IMPORT);
    }

    public void onOrderImportFailure(String message) throws Exception {
        LogableException.logException(new Exception(), message,
                "GENERIC",
                "" + kClientID,
                this.getClass().getName(),
                LogableException.errorTypes.ORDER_IMPORT);
    }

    public void onOrderImportBatchSuccess(String batchReference) throws Exception {


        ftp.renameFile("./Archive/" + batchReference);
    }

    public void onOrderImportBatchFailure(String batchReference, String message) throws Exception {
        LogableException.logException(new Exception(), batchReference + "::" + message,
                "GENERIC",
                "" + kClientID,
                this.getClass().getName(),
                LogableException.errorTypes.ORDER_IMPORT);
    }

    public void importOrders() {

        try {
            List<String> fileNames = ftp.getFileNames();
            for (String file : fileNames) {
                DelimitedFileOrderHandler fileHandler = new DelimitedFileOrderHandler(this, kClientID, new TabReader(new BufferedReader(new StringReader(ftp.getFileData(file).toString())), true), file) {
                    int kSeller_Shopper_Number = 0;
                    int kReceipt_Id = 1;
                    int kReceipt_Item_Id = 2;
                    int kListing_Id = 3;
                    int kOrder_Date = 4;
                    int kSku = 5;
                    int kReference_Id = 6;
                    int kQty = 7;
                    int kQty_Shipped = 8;
                    int kQty_Cancelled = 9;
                    int kItem_Name = 10;
                    int kPrice = 11;
                    int kProduct_Revenue = 12;
                    int kShipping_Cost = 13;
                    int kProduct_Owed = 14;
                    int kShipping_Owed = 15;
                    int kCommission = 16;
                    int kShipping_Fee = 17;
                    int kPer_Item_Fee = 18;
                    int kTax_Cost = 19;
                    int kBill_To_Company = 20;
                    int kBill_To_Phone = 21;
                    int kBill_To_First_Name = 22;
                    int kBill_To_Last_Name = 23;
                    int kEmail = 24;
                    int kShip_To_Name = 25;
                    int kShip_To_Company = 26;
                    int kShip_To_Street1 = 27;
                    int kShip_To_Street2 = 28;
                    int kShip_To_City = 29;
                    int kShip_To_State = 30;
                    int kShip_To_Zip_Code = 31;
                    int kShipping_Method_Id = 32;


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
                                order.order_refnum = "RAK" + rdr.getStrValue(kReceipt_Id, row, "");
                                if (order.order_refnum.length() < 4) {
                                    throw new Exception("Client order reference was invalid or not found.");
                                }

                                order.getBillingContact().setName((rdr.getStrValue(kBill_To_First_Name, row, "") + " " + rdr.getStrValue(kBill_To_Last_Name, row, "")).trim());

                                order.getBillingContact().phone = rdr.getStrValue(kBill_To_Phone, row, "");
                                order.getBillingAddress().company_name = rdr.getStrValue(kBill_To_Company, row, ".");

                                if (order.getBillingAddress().company_name.trim().length() < 1) {
                                    order.getBillingAddress().company_name = ".";
                                }

                                order.getShippingContact().setName(rdr.getStrValue(kShip_To_Name, row, ""));
                                order.getShippingContact().email = rdr.getStrValue(kEmail, row, "");
                                order.getShippingContact().phone = rdr.getStrValue(kBill_To_Phone, row, "");

                                order.getShippingAddress().company_name = rdr.getStrValue(kShip_To_Company, row, ".");

                                if (order.getShippingAddress().company_name.trim().length() < 1) {
                                    order.getShippingAddress().company_name = ".";
                                }
                                order.getShippingAddress().address_one = rdr.getStrValue(kShip_To_Street1, row, "");
                                order.getShippingAddress().address_two = rdr.getStrValue(kShip_To_Street2, row, "");
                                order.getShippingAddress().city = rdr.getStrValue(kShip_To_City, row, "");
                                order.getShippingAddress().state = rdr.getStrValue(kShip_To_State, row, "");
                                order.getShippingAddress().zip = rdr.getStrValue(kShip_To_Zip_Code, row, "");

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

                                order.total_shipping_cost = rdr.getFloatValue(kShipping_Fee, row, 0.00f);
                                order.total_tax_cost = rdr.getFloatValue(kTax_Cost, row, 0.00f);
                                order.getShippingInfo().carr_service = rdr.getStrValue(kShipping_Method_Id, row, "");

                                order.order_type = orderType;
                            }
                            String sku = rdr.getStrValue(kReference_Id, row, "").startsWith("SKU") ? rdr.getStrValue(kReference_Id, row, "").substring(3) : rdr.getStrValue(kReference_Id, row, "");

                            //product data collected for every row
                            addLineItem(order, sku,
                                    rdr.getStrValue(kQty, row, ""),
                                    rdr.getStrValue(kPrice, row, ""),
                                    "0.00",
                                    rdr.getStrValue(kItem_Name, row, ""),
                                    "", "", "");

                            ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = rdr.getStrValue(kReceipt_Item_Id, row, "");
                        }

                        return order;
                    }


                    @Override
                    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size, String externalLineReference) throws Exception {
                        if (Float.parseFloat(unitPrice) < 0.00f) {
                            order.discount = order.discount + Float.parseFloat(unitPrice);
                        } else {
                            order.addLineItem(sku,
                                    qty,
                                    unitPrice,
                                    linePrice,
                                    desc,
                                    color, size);
                            ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = externalLineReference;

                        }
                    }

                    @Override
                    public void translateShipMethod(Order order, String oldMethod) throws Exception {

                        OrderRater rater = new OrderRater(order);
                        rater.setCalculateKitWeights(true);

                        List<String> alternateShipMethodList = new ArrayList<String>();
                        alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                        alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                        alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                        alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                        alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
                        alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                        alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");

                        rater.setOrderBestRateAndMethod(order, alternateShipMethodList);

                        log.debug("SELECTED:" + order.getShippingInfo().carr_service);
                    }
                };

                fileHandler.processOrders();


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


    public String translateOutboundShipMethod(String oldMethod) {

        try {
            if (oldMethod.toUpperCase().contains("USPS")) {
                return "3";
            } else if (oldMethod.toUpperCase().contains("FEDEX")) {
                if (oldMethod.toUpperCase().contains("SMARTPOST")) {
                    return "7";
                } else {
                    return "2";
                }
            } else if (oldMethod.toUpperCase().contains("UPS")) {
                return "1";
            } else {
                return "5"; //other
            }
        } catch (Exception ex) {
            System.err.println("Error - RakutenShipConfirmationJob.translateShipMethod: " + ex.getLocalizedMessage());
            throw new RuntimeException(ex);
        }
    }
}

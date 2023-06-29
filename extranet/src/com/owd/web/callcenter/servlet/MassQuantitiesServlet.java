package com.owd.web.callcenter.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.web.servlet.ExtranetServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 19, 2003
 * Time: 3:27:35 PM
 * To change this template use Options | File Templates.
 */
public class MassQuantitiesServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    private String[] ordersBulkLoadColumns2 = {"OrderHeader.ID", "BillingStreet1", "BillingStreet2", "BillingCity", //0,1,2,3
                                               "BillingState", "BillingZip", "BillingCountry", "CustomerCompanyName", // 4,5,6,7
                                               "ShippingStreet1", "ShippingStreet2", "ShippingCity", "ShippingState", //8,9,10,11
                                               "ShippingZip", "ShippingCountry", "ShippingCompanyName", "ShippingFirstName", //12,13,14,15
                                               "ShippingLastName", "ShippingFullName", "ShippingPhone", "Email", //16,17,18,19
                                               "ShippingType", "TotalShippingWeight", "Qty", "PartNO", //20,21,22,23
                                               "Residential", "BillingOption", "PackageType"};      //24,25,26  (27)

    /*

    Samples:

"1011","1010 Northern Blvd.","Suite 208","Great Neck","NY","11021","US","Mass Quantities","1010 Northern Blvd.","Suite 208","Great Neck","NY","11021","US","Mass Quantities","Barry","Sharf","Barry Sharf","516-314-1414","chris@mqfitness.com","USPS Priority","0","1","151","","",""
"1013","4 Wilson Street","","E. Rockaway","NY","11518","US","","4 Wilson Street","","E. Rockaway","NY","11518","US","","Christopher","Hummel","Christopher Hummel","516-593-6890","finaplix@elitefitness.com","USPS Priority","0","2","151","","",""
"1014","4 Wilson Street","","E. Rockaway","NY","11518","US","","4 Wilson Street","","E. Rockaway","NY","11518","US","","Christopher","Hummel","Christopher Hummel","516-593-6890","finaplix@elitefitness.com","UPS Ground","13","1","C106","","",""
"1015","19 Windsor Place","Cardiff","Astoria","Smincey","ZX8BM9","GB","Sterling Industries","19 Windsor Place","Cardiff","Astoria","Smincey","ZX8BM9","GB","Sterling Industries","Christopher","Hummel","Christopher Hummel","016789998789","chris@mqfitness.com","USPS Global Express Mail (EMS)","0.3","1","","","",""

*/

    public void init(ServletConfig config)

            throws ServletException {

        super.init(config);


    }


    public void destroy() {

        super.destroy();


    }

    void translateShippingMethodToOWDMethod(Order anOrder, String MQMethod) throws Exception {

        if (MQMethod.toUpperCase().indexOf("STANDARD") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
        } else if (MQMethod.toUpperCase().indexOf("RED") >= 0) {
            anOrder.getShippingInfo().setShipOptions("USPS Express Mail", "Prepaid", "");
            //anOrder.getShippingInfo().setShipOptions("UPS Next Day Air Saver","Prepaid","");
        } else if (MQMethod.toUpperCase().indexOf("TWO DAY") >= 0) {
            anOrder.getShippingInfo().setShipOptions("UPS 2nd Day Air", "Prepaid", "");
        } else {
            //default
            anOrder.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
        }


    }


    void addLineItemToOrder(Order anOrder, String qty, String sku, String price, String desc, String lineprice) throws Exception {

        if (sku.equals("000"))
            sku = "125C";

        if (sku.endsWith("S"))
            sku = sku.substring(0, sku.indexOf("S"));

        if (sku.toUpperCase().endsWith("C")) {
            sku = sku.substring(0, sku.indexOf("C"));

            if (anOrder.findLineItemForSKU("124") >= 0) {
                long old_qty = anOrder.getLineItemReqQtyForSKU("124");
                anOrder.setLineItemReqQty("124", old_qty + new Integer(qty).intValue());
            } else
                anOrder.addLineItem("124", new Integer(qty).intValue(), 0, 0, "", "", "");
        }

        if (sku.toUpperCase().endsWith("X")) {
            sku = sku.substring(0, sku.indexOf("X"));

            if (anOrder.findLineItemForSKU("123") >= 0) {
                long old_qty = anOrder.getLineItemReqQtyForSKU("123");
                anOrder.setLineItemReqQty("123", old_qty + new Integer(qty).intValue());
            } else
                anOrder.addLineItem("123", new Integer(qty).intValue(), 0, 0, "", "", "");
        }

        int iqty = new Integer(qty).intValue();


        if (sku.equals("C106")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("113", "" + (3 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("117", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("114", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("024", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("126", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("125", "" + (1 * iqty), "0.00", "0.00", "", "", "");


        } else if (sku.equals("C101")) {

            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("151", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("147", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("126", "" + (1 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("C102")) {

            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("151", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("147", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("125", "" + (1 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("C109")) {

            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("113", "" + (4 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("117", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("123", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("106", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("126", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("125", "" + (1 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("C110")) {

            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("113", "" + (4 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("117", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("123", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("024", "" + (6 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("114", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("106", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("126", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("125", "" + (1 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("C111")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("113", "" + (8 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("117", "" + (3 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("123", "" + (3 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("106", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("126", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("125", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("024", "" + (6 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("114", "" + (2 * iqty), "0.00", "0.00", "", "", "");

        } else if (sku.equals("C104")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("151", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("126", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("024", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("125", "" + (1 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("C112")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("113", "" + (5 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("117", "" + (3 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("024", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("114", "" + (3 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("106", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("126", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("125", "" + (2 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("C107")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("113", "" + (2 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("123", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("106", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("126", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("125", "" + (1 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("C118")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("126", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("106", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("147", "" + (1 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("C119")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("125", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("106", "" + (1 * iqty), "0.00", "0.00", "", "", "");
            anOrder.addLineItem("147", "" + (1 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("150")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("147", "" + (2 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("148")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("147", "" + (2 * iqty), "0.00", "0.00", "", "", "");
        } else if (sku.equals("149")) {
            anOrder.addLineItem("KITITEM", qty, price, lineprice, desc, "", "");
            anOrder.addLineItem("147", "" + (3 * iqty), "0.00", "0.00", "", "", "");
        } else {

            anOrder.addLineItem(sku, qty, price, lineprice, desc, "", "");
        }

    }



    //GET requests not supported

    public void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        doPost(req, resp);

    }



    //all requests should be POST

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        try {

            String action = req.getParameter("action");
            log.debug("MQServlet got action=" + action);
            if (action == null) {

            } else if (action.equals("uploadorders")) {
                log.debug("got uploadorders request");
                try {
                    if (null == req.getParameter("containsfile"))
                        throw new Exception("No upload file found!");

                    log.debug("containsfileOK");
                    File uploadFile = null;
                    BufferedReader reader = null;
                    com.owd.core.MultipartRequest fileSource = new com.owd.core.MultipartRequest(req, ExtranetServlet.kBulkLoadSaveDir, 1024 * 1024 * 10);
                    log.debug("got filesource=" + fileSource);
                    Enumeration files = fileSource.getFileNames();
                    log.debug("got fs enumeration");
                    if (!files.hasMoreElements()) {
                        throw new Exception("No file was received!<BR>Please check your file and try again.</B>");

                    } else {

                        uploadFile = fileSource.getFile((String) files.nextElement());

                        if (uploadFile == null) {
                            throw new Exception("No file was received!<BR>Please check your file and try again.</B>");
                        } else {

                            reader = new BufferedReader(new FileReader(uploadFile));
                            uploadFile.renameTo(new File(ExtranetServlet.kBulkLoadSaveDir + File.separator + com.owd.core.OWDUtilities.getCurrentUserName() + Calendar.getInstance().getTime().getTime() + ".csv"));

                            com.owd.core.CSVReader data = new com.owd.core.CSVReader(reader, true);


                            int importColumnCount = ordersBulkLoadColumns2.length;

                            if (data.columnCount != importColumnCount) {
                                throw new Exception("<B>There must be " + importColumnCount + " columns in the file; you provided " + data.columnCount
                                        + " columns.<BR>Please check your file and try again.</B>");

                            } else {

                                List resultsList = new ArrayList();

                                for (int row = 0; row < data.getRowCount(); row++) {
                                    String newOrderID = data.getStrValue(0, row, "").trim();

                                    resultsList.add(processOrder(data, row));


                                }
                                req.setAttribute("resultList", resultsList);
                            }
                        }
                    }


                } catch (Exception ex) {
                    if (ex.getMessage().length() > 0)
                        ex.printStackTrace();
                    req.setAttribute("errormessage", ex.getMessage());
                }

            }

            req.getRequestDispatcher("/callcenter/mq/uploadorders.jsp").forward(req, resp);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private List processOrder(DelimitedReader rdr, int startRow) {
        //returns list of two elements - com.owd.api.client Order ID and response
        List response = new ArrayList();
        response.add(rdr.getStrValue(0, startRow, "NA"));
        //add new

        try {
            Order order = new Order("94");
            order.ship_operator = "MONSTERCOMMERCE";
            order.order_type = "Monster Commerce File Upload";
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.backorder_rule = OrderXMLDoc.kBackOrderAll;
            for (int row = startRow; row < rdr.getRowCount(); row++) {
                String newOrderID = rdr.getStrValue(0, row, "").trim();

                if (rdr.getStrValue(24, row, "").trim().length() > 2 && row > startRow) {
                    row = rdr.getRowCount();
                } else {

                    if (row == startRow) {
                        order.order_refnum = rdr.getStrValue(0, row, "");
//response.add(order.order_refnum);

                        order.getBillingContact().setName(rdr.getStrValue(2, row, "") + " " + rdr.getStrValue(3, row, ""));
                        order.getBillingContact().email = rdr.getStrValue(5, row, "");
                        order.getBillingContact().phone = rdr.getStrValue(4, row, "");

                        order.getBillingAddress().company_name = ".";
                        order.getBillingAddress().address_one = rdr.getStrValue(6, row, "");
                        order.getBillingAddress().address_two = rdr.getStrValue(7, row, "");
                        order.getBillingAddress().city = rdr.getStrValue(8, row, "");
                        order.getBillingAddress().state = rdr.getStrValue(9, row, "");
                        order.getBillingAddress().zip = rdr.getStrValue(10, row, "");
                        order.getBillingAddress().country = rdr.getStrValue(11, row, "");


                        if ("TRUE".equals(rdr.getStrValue(15, row, "").toUpperCase())) {
                            order.getShippingInfo().setShippingContact(Contact.createFromStorableString(order.getBillingContact().toStorableString()));
                            order.getShippingInfo().setShippingAddress(Address.createFromStorableString(order.getBillingAddress().toStorableString()));
                        } else {
                            order.getShippingContact().setName(rdr.getStrValue(17, row, "") + " " + rdr.getStrValue(18, row, ""));
                            order.getShippingContact().email = rdr.getStrValue(5, row, "");
                            order.getShippingContact().phone = rdr.getStrValue(4, row, "");

                            order.getShippingAddress().company_name = ".";
                            order.getShippingAddress().address_one = rdr.getStrValue(19, row, "");
                            order.getShippingAddress().address_two = rdr.getStrValue(20, row, "");
                            order.getShippingAddress().city = rdr.getStrValue(21, row, "");
                            order.getShippingAddress().state = rdr.getStrValue(22, row, "");
                            order.getShippingAddress().zip = rdr.getStrValue(23, row, "");
                            order.getShippingAddress().country = rdr.getStrValue(24, row, "");
                        }
                        order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                        order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);
                        order.total_shipping_cost = rdr.getFloatValue(30, row, 0.00f) + rdr.getFloatValue(37, row, 0.00f);
                        order.total_tax_cost = rdr.getFloatValue(29, row, 0.00f);
                        //order.po_num = rdr.getStrValue(46, row, "");
                        //order.discount = -1 * rdr.getFloatValue(32, row, 0.00f);
                        order.getShippingInfo().comments = rdr.getStrValue(42, row, "");
                        String shipMethod = rdr.getStrValue(16, row, "USPS Priority Mail").toUpperCase();
                        if (order.getShippingAddress().country.equals("USA")) {
                            if (shipMethod.indexOf("EXPRESS") >= 0) {
                                order.getShippingInfo().setShipOptions("USPS Express Mail", "Prepaid", "");
                            } else if (shipMethod.indexOf("PRIORITY") >= 0) {
                                order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
                            } else if (shipMethod.indexOf("FIRST") >= 0) {
                                order.getShippingInfo().setShipOptions("USPS First-Class Mail", "Prepaid", "");
                            } else {
                                order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");

                            }
                        } else {
                            order.getShippingInfo().setShipOptions("USPS Int\'l Letter-Post Air", "Prepaid", "");
                        }

                    } else {

                        if (rdr.getStrValue(12, row, "").trim().length() < 2 && (!("ProductID".equals(rdr.getStrValue(0, row, ""))))) {
                            order.addLineItem(rdr.getStrValue(7, row, ""), rdr.getStrValue(5, row, ""), rdr.getStrValue(3, row, ""), "0.00", rdr.getStrValue(2, row, ""), "", "");
                        }
                    }


                }
            }
            order.recalculateBalance();
            order.paid_amount = order.total_order_cost;
            order.is_paid = 1;

//force ship method to any foreign shipping country
            order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);

            order.setBillingAddress(order.getShippingAddress());
            order.setBillingContact(order.getShippingContact());

            if ((order.getShippingInfo().carr_service.equals("UPS Ground") &&
                    (order.getShippingAddress().address_one.toUpperCase().indexOf(" BOX") >= 0 ||
                    order.getShippingAddress().address_one.toUpperCase().indexOf("OBOX") >= 0)) ||
                    order.getShippingAddress().city.toUpperCase().indexOf("APO") == 0 ||
                    order.getShippingAddress().city.toUpperCase().indexOf("FPO") == 0 ||
                    order.getShippingAddress().state.toUpperCase().indexOf("HI") == 0 ||
                    order.getShippingAddress().state.toUpperCase().indexOf("AK") == 0) {
                order.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");
            } else {

                if (order.getShippingAddress().country.indexOf("USA") != 0) {
                    order.getShippingInfo().setShipOptions("USPS Int\'l Express Mail", "Prepaid", "");
                }
            }

            if (order.getShippingAddress().company_name.trim().length() < 1)
                order.getShippingAddress().company_name = ".";
            if (order.getBillingAddress().company_name.trim().length() < 1)
                order.getBillingAddress().company_name = ".";

            order.recalculateBalance();

            //process order
            //order.is_future_ship = 1;
            if (order.skuList.size() < 1)
                throw new Exception("Order has no SKUs!");


            //determine weight of order
            float wgt = order.getDecimalPoundsWeight();

            if (order.getShippingInfo().carr_service.indexOf("Ground") >= 0 && ((wgt * 1.1f) <= 3.00f)) {
                order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
            }
            //
            String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, false);

            if (reference == null) {
                throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
            }

            response.add("Confirmed as Reference: " + reference);
            if (order.is_backorder == 1)
                response.set(1, response.get(1) + "; Backorder created");


        } catch (Exception
                ex) {
            ex.printStackTrace();
            response.add("<font color=red>" + ex.getMessage() + "</font>");
        } finally {


        }
        return response;
    }


    public class OWDUploadOrdersData {

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
        int kShip_Name = 15;
        int kShip_Company = 16;
        int kShip_Address_1 = 17;
        int kShip_Address_2 = 18;
        int kShip_City = 19;
        int kShip_State = 20;
        int kShip_Postal_Code = 21;
        int kShip_Country = 22;
        int kShip_Phone = 23;
        int kShip_Email = 24;
        int kShip_Method = 25;
        int kShip_Account = 26;
        int kIs_Gift = 27;
        int kGift_Message = 28;
        int kComments = 29;
        int kWarehouse_Notes = 30;
        int kOrder_Source = 31;
        int kOrder_Taker = 32;
        int kSales_Tax = 33;
        int kShip_Handling_Fee = 34;
        int kOrder_Discount = 35;
        int kProduct_SKU = 36;
        int kProduct_Quantity = 37;
        int kProduct_Unit_Price = 38;
        int kProduct_Description = 39;

        DelimitedReader dataReader = null;

        protected List orderPositionList = new ArrayList();


        public OWDUploadOrdersData() {

        }

        public void init(DelimitedReader rdr) {
            if (rdr == null) return;

            setDataReader(rdr);
            processReader();


        }


        public String getOrderReference(int orderIndex) {
            return ((OrderData) getOrderPositionList().get(orderIndex)).orderRef;
        }

        public Order loadOrder(Order order, int orderIndex) throws Exception {
            log.debug("in DH loadOrder");
            if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
                throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());

            OrderData data = (OrderData) getOrderPositionList().get(orderIndex);
            DelimitedReader rdr = getDataReader();

            for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
                //log.debug("DH loading row "+row);
                if (row == data.rowIndexStart) {
                    //first row
                    order.order_refnum = rdr.getStrValue(kOrder_Reference, row, "");
                    //response.add(order.order_refnum);
                    log.debug("DH loading ref " + order.order_refnum);

                    order.getBillingContact().setName(rdr.getStrValue(kCustomer_Name, row, ""));
                    order.getBillingContact().email = rdr.getStrValue(kCustomer_Email, row, "");
                    order.getBillingContact().phone = rdr.getStrValue(kCustomer_Phone, row, "");

                    order.getBillingAddress().company_name = rdr.getStrValue(kCustomer_Company, row, ".");
                    if (order.getBillingAddress().company_name.trim().length() < 1) order.getBillingAddress().company_name = ".";
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
                    if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

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

                    if (rdr.getStrValue(kShip_Account, row, "").length() > 0) {
                        order.getShippingInfo().setShipOptions(rdr.getStrValue(kShip_Method, row, ""), "Third Party Billing", rdr.getStrValue(kShip_Account, row, ""));
                    } else {
                        order.getShippingInfo().setShipOptions(rdr.getStrValue(kShip_Method, row, ""), "Prepaid", "");
                    }
                    order.order_type = rdr.getStrValue(kOrder_Source, row, "OWD General Order Upload");
                    order.ship_operator = rdr.getStrValue(kOrder_Taker, row, "");

                    order.is_paid = rdr.getIntValue(kOrder_Paid, row, 0) > 0 ? 1 : 0;
                    order.is_gift = "" + (rdr.getIntValue(kIs_Gift, row, 0) > 0 ? 1 : 0);
                    order.gift_message = rdr.getStrValue(kGift_Message, row, "");
                    order.cc_num = rdr.getStrValue(kCustomer_CC, row, "");
                    order.cc_exp_mon = rdr.getIntValue(kCC_Expiration_Month, row, 0);
                    order.cc_exp_year = rdr.getIntValue(kCC_Expiration_Year, row, 0);


                }
                //product data collected for every row
                order.addLineItem(rdr.getStrValue(kProduct_SKU, row, ""),
                        rdr.getStrValue(kProduct_Quantity, row, ""),
                        rdr.getStrValue(kProduct_Unit_Price, row, ""),
                        "0.00",
                        rdr.getStrValue(kProduct_Description, row, ""),
                        "", "");
            }


            return order;
        }

        public int getOrderCount() {
            return getOrderPositionList().size();
        }

        protected void processReader() {

            String orderRef = "thisisaninvalidorderreference";
            if (getDataReader() == null) return;

            for (int row = 0; row < getDataReader().getRowCount(); row++) {
                //log.debug("DH handling row "+row);
                if (!(getDataReader().getStrValue(0, row, "").trim().equals(""))) {
                    if (orderRef.equals(getDataReader().getStrValue(0, row, orderRef))) {
                        //got the same order
                        //log.debug("DH found extra row");
                        OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                        data.rowsForOrder++;
                        //log.debug("DH adding item to order "+data.orderRef);
                    } else {
                        //new order
                        //log.debug("DH found new row");
                        OrderData data = new OrderData();
                        orderRef = getDataReader().getStrValue(0, row, orderRef);
                        data.orderRef = orderRef;
                        data.rowIndexStart = row;
                        data.rowsForOrder = 1;
                        getOrderPositionList().add(data);
                        //log.debug("DH added entry for "+orderRef+", row "+row);
                    }
                }
            }
        }

        public List getOrderPositionList() {
            return orderPositionList;
        }

        public void setOrderPositionList(List orderPositionList) {
            this.orderPositionList = orderPositionList;
        }


        public DelimitedReader getDataReader() {
            return dataReader;
        }

        public void setDataReader(DelimitedReader dataReader) {
            this.dataReader = dataReader;
        }

    }

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

}


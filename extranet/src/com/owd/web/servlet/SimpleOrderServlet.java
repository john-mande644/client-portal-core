package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.business.order.ShippingInfo;
import com.owd.core.xml.OrderXMLDoc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Vector;

public class SimpleOrderServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();
    //constants used in this program
    public static final String kRPClientID = "148";
    public static final String kRPOrderKey = "orderkey";
    public static final String kRPOrderNum = "ordernum";
    public static boolean hasErr = false;

    //quantity input field names in the form in the jsp file
    public static final String kRP_quant_2557 = "quant_2557";
    public static final String kRP_quant_2556 = "quant_2556";
    public static final String kRP_quant_2555 = "quant_2555";
    public static final String kRP_quant_2554 = "quant_2554";
    public static final String kRP_quant_2553 = "quant_2553";
    public static final String kRP_quant_2552 = "quant_2552";
    public static final String kRP_quant_2551 = "quant_2551";


    //billing address field names
    public static final String kRP_billname = "bill_billingName";
    public static final String kRP_billcompany = "bill_companyName";
    public static final String kRP_billaddr1 = "bill_address1";
    public static final String kRP_billaddr2 = "bill_address2";
    public static final String kRP_billcity = "bill_city";
    public static final String kRP_billstate = "bill_state";
    public static final String kRP_billintlstate = "bill_intl_state";
    public static final String kRP_billzip = "bill_zip";
    public static final String kRP_billcountry = "bill_country";
    public static final String kRP_billemail = "bill_email";
    public static final String kRP_billphone = "bill_phone_num";
    public static final String kRP_ccnum = "cc_num";
    public static final String kRP_ccexprm = "cc_exp_mon";
    public static final String kRP_ccexpry = "cc_exp_year";


    public static final String kRP_ship_type = "ship_type";
    public static final String kRP_same = "bill_ship_same";

    //shipping address field names
    public static final String kRP_shipname = "ship_shippingName";
    public static final String kRP_shipcompany = "ship_companyName";
    public static final String kRP_shipaddr1 = "ship_address1";
    public static final String kRP_shipaddr2 = "ship_address2";
    public static final String kRP_shipcity = "ship_city";
    public static final String kRP_shipintlstate = "ship_intl_state";
    public static final String kRP_shipstate = "ship_state";
    public static final String kRP_shipzip = "ship_zip";
    public static final String kRP_shipcountry = "ship_country";

    //prices
    private static final float _2557Price = (float) 3.95;
    private static final float _2556Price = (float) 4.95;
    private static final float _2555Price = (float) 4.95;
    private static final float _2554Price = (float) 24.95;
    private static final float _2553Price = (float) 7.95;
    private static final float _2552Price = (float) 24.95;
    private static final float _2551Price = (float) 199.95;

    //S&H charges
    public static final float s_h = (float) 7.95;


    //taxes
    public static final float SD_tax = (float) 0.06;


    //field names that need to be validated
    public static final String[] basic_check = {kRP_billname, kRP_billcompany, kRP_billaddr1, kRP_billstate,
                                                kRP_billzip, kRP_ccnum, kRP_ccexprm, kRP_ccexpry};

    public static final String[] ship_check = {kRP_shipname, kRP_shipcompany, kRP_shipaddr1, kRP_shipcity,
                                               kRP_shipstate, kRP_shipzip};

    //corresponding error msg
    public static final String[] basic_err = {"Purchaser's Name", "Purchaser's Company Name", "Billing Address",
                                              "Billing State", "Billing ZIP Code", "Credit Card Number", "Credit Card Expiration Month",
                                              "Credit Card Expiration Year"};

    public static final String[] ship_err = {"Delivery Name", "Consignee's Company Name", "Shipping Address",
                                             "Destination City", "Destination State", "Destination ZIP Code"};

    //errors place holder





    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        try {
            //log.debug("Hello doGet");
            doPost(req, resp);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        //log.debug("Hello");

        Vector errors = new Vector();

        String order_num = "";
        try {

            String actionType = SimpleOrderServlet.getStringParam(req, "actionType", "");


            if ("Review Order".equals(actionType)) {
                //log.debug("Got review order");
            } else if ("Submit Order".equals(actionType)) {

                //String clientID = SimpleOrderServlet.getStringParam(req, "ClientID", "");

                errors.removeAllElements();
                checkInfo(req, errors);

                ////log.debug("Got Submit Order");//test
                Order currOrder = new Order(kRPClientID);

                fillOrder(currOrder, req, errors);

                //set declared value
                currOrder.getShippingInfo().declared_value = String.valueOf(currOrder.total_order_cost - currOrder.total_shipping_cost - currOrder.total_tax_cost);

                req.setAttribute(kRPOrderKey, currOrder);

                //log.debug(errors.size()+" errors.");//test
                if (errors.size() > 0) {

                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("orderform.jsp").forward(req, resp);
                } else {
                    order_num = currOrder.saveNewOrder(OrderUtilities.kRequirePayment, false);
                    if (currOrder.completed) {
                        req.setAttribute(kRPOrderNum, order_num);
                        req.getRequestDispatcher("receipt.jsp").forward(req, resp);
                    } else {
                        errors.add((currOrder.last_payment_error.length() > 1 ? currOrder.last_payment_error : currOrder.last_error));
                        req.setAttribute("errors", errors);
                        req.getRequestDispatcher("orderform.jsp").forward(req, resp);
                    }
                }

            } else {
                //log.debug("Hello doPost");
                req.getRequestDispatcher("orderform.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInfo(HttpServletRequest req, Vector errors) {
        try {
            int len = basic_check.length;
            for (int i = 0; i < len; i++) {
                ////log.debug(i+" "+errors.size()+"\n");//test
                String value = SimpleOrderServlet.getStringParam(req, basic_check[i], "");
                if ("".equals(value)) {
                    if (basic_check[i].equalsIgnoreCase(kRP_billstate)) {
                        value = getStringParam(req, kRP_billintlstate, "");
                        if (value != null || value.length() > 0) {
                            continue;

                        }
                    }

                    if (basic_check[i].equalsIgnoreCase(kRP_shipstate)) {
                        value = getStringParam(req, kRP_shipintlstate, "");
                        if (value != null || value.length() > 0) {
                            continue;

                        }
                    }


                    try {
                        errors.add(basic_err[i] + " is missing");
                    } catch (Exception e) {
                    }
                }

            }

            String same = SimpleOrderServlet.getStringParam(req, kRP_same, "");
            if (!("same".equals(same))) {
                for (int i = 0; i < ship_check.length; i++) {
                    String value = SimpleOrderServlet.getStringParam(req, ship_check[i], "");
                    if ("".equals(value)) {
                        try {
                            errors.add(ship_err[i] + " is missing");
                        } catch (Exception e) {
                        }
                    }

                }
            }
        } catch (Exception e) {
        }


    }

    public void fillOrder(Order order, HttpServletRequest req, Vector errors) {
        //set the order on hold
        //order.is_future_ship=1;
        order.bill_cc_type = "CC";
        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
        order.backorder_rule = OrderXMLDoc.kPartialShip;

        //filling billing address
        String billCompany = SimpleOrderServlet.getStringParam(req, kRP_billcompany, "");
        String billAddr1 = SimpleOrderServlet.getStringParam(req, kRP_billaddr1, "");
        String billAddr2 = SimpleOrderServlet.getStringParam(req, kRP_billaddr2, "");
        String billCity = SimpleOrderServlet.getStringParam(req, kRP_billcity, "");
        String billState = SimpleOrderServlet.getStringParam(req, kRP_billstate, "");
        if (billState == null || billState.length() < 1) {
            billState = getStringParam(req, kRP_billintlstate, "");
        }
        String billZip = SimpleOrderServlet.getStringParam(req, kRP_billzip, "");
        String billCountry = getStringParam(req, kRP_billcountry, "USA");
        Address addr = new Address(billCompany, billAddr1, billAddr2, billCity, billState, billZip, billCountry);
        order.setBillingAddress(addr);

        //filling billing contact
        String billName = SimpleOrderServlet.getStringParam(req, kRP_billname, "");
        String billPhone = SimpleOrderServlet.getStringParam(req, kRP_billphone, "");
        String billFax = "";
        String billEmail = SimpleOrderServlet.getStringParam(req, kRP_billemail, "");
        String billURL = "";
        Contact ct = new Contact(billName, billPhone, billFax, billEmail, billURL);
        order.setBillingContact(ct);

        //filling shipping info
        ShippingInfo sinfo = new ShippingInfo();

        String same = SimpleOrderServlet.getStringParam(req, kRP_same, "");
        if ("same".equals(same)) {
            sinfo.shipAddress = order.getBillingAddress();
            sinfo.shipContact = order.getBillingContact();
        } else {
            String shipCompany = SimpleOrderServlet.getStringParam(req, kRP_shipcompany, "");
            String shipAddr1 = SimpleOrderServlet.getStringParam(req, kRP_shipaddr1, "");
            String shipAddr2 = SimpleOrderServlet.getStringParam(req, kRP_shipaddr2, "");
            String shipCity = SimpleOrderServlet.getStringParam(req, kRP_shipcity, "");
            String shipState = SimpleOrderServlet.getStringParam(req, kRP_shipstate, "");
            if (shipState == null || shipState.length() < 1) {
                shipState = getStringParam(req, kRP_shipintlstate, "");
            }
            String shipZip = SimpleOrderServlet.getStringParam(req, kRP_shipzip, "");
            String shipCountry = getStringParam(req, kRP_shipcountry, "USA");
            addr = new Address(shipCompany, shipAddr1, shipAddr2, shipCity, shipState, shipZip, shipCountry);
            sinfo.shipAddress = addr;

            String shipName = SimpleOrderServlet.getStringParam(req, kRP_shipname, "");
            String shipPhone = SimpleOrderServlet.getStringParam(req, kRP_billphone, "");
            String shipFax = "";
            String shipEmail = SimpleOrderServlet.getStringParam(req, kRP_billemail, "");
            String shipURL = "";
            ct = new Contact(shipName, shipPhone, shipFax, shipEmail, shipURL);
            sinfo.shipContact = ct;
        }

        String ship_method = SimpleOrderServlet.getStringParam(req, kRP_ship_type, "").trim().toUpperCase();
        try {
            //changed from Priority Mail to UPS Ground/2nd Day Air on July 23,2002
            if (ship_method.indexOf("GROUND") >= 0) {
                sinfo.setShipOptions("UPS Ground", "Prepaid", "");
                order.total_shipping_cost = 7.95f;
            } else if (ship_method.indexOf("2ND DAY AIR") >= 0) {
                sinfo.setShipOptions("UPS 2nd Day Air", "Prepaid", "");
                order.total_shipping_cost = 25.00f;
            } else if (ship_method.indexOf("Global Priority") >= 0) {
                sinfo.setShipOptions("USPS Global Priority Mail", "Prepaid", "");
                order.total_shipping_cost = 38.95f;
                sinfo.declared_value = "200.00";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        order.setShippingInfo(sinfo);

        order.ship_call_date = com.owd.core.OWDUtilities.getSQLDateForToday();

        //added back order fule "BACKORDERALL"
        order.backorder_rule = OrderXMLDoc.kBackOrderAll;

        //filling credit card info
        String cc_num = SimpleOrderServlet.getStringParam(req, kRP_ccnum, "");
        if (!com.owd.core.CreditCard.isValid(com.owd.core.CreditCard.parseDirtyLong(cc_num))) {
            try {
                errors.add("Invalid Credit Card number");
            } catch (Exception e) {
            }
        } else {
            order.cc_num = cc_num;
        }
        try {
            String cc_exp_year = SimpleOrderServlet.getStringParam(req, kRP_ccexpry, "");
            order.cc_exp_year = Integer.parseInt(cc_exp_year);
            String cc_exp_month = SimpleOrderServlet.getStringParam(req, kRP_ccexprm, "");
            order.cc_exp_mon = Integer.parseInt(cc_exp_month);
            //check the credit card expiration date

            if (order.cc_exp_year < (Calendar.getInstance().get(Calendar.YEAR)))
                throw new NumberFormatException();
            else if (order.cc_exp_year == (Calendar.getInstance().get(Calendar.YEAR))) {
                if (order.cc_exp_mon < Calendar.getInstance().get(Calendar.MONTH))
                    throw new NumberFormatException();
            }
        } catch (NumberFormatException ne) {
            try {
                errors.add("Invalid credit card expiration date");
            } catch (Exception e) {
            }
        }

        //filling line items info
        //quantities

        int _2551_count = 0;
        int _2552_count = 0;
        int _2553_count = 0;
        int _2554_count = 0;
        int _2555_count = 0;
        int _2556_count = 0;
        int _2557_count = 0;

        try {
            _2551_count = SimpleOrderServlet.getIntegerParam(req, kRP_quant_2551, 0);
            _2552_count = SimpleOrderServlet.getIntegerParam(req, kRP_quant_2552, 0);
            _2553_count = SimpleOrderServlet.getIntegerParam(req, kRP_quant_2553, 0);
            _2554_count = SimpleOrderServlet.getIntegerParam(req, kRP_quant_2554, 0);
            _2555_count = SimpleOrderServlet.getIntegerParam(req, kRP_quant_2555, 0);
            _2556_count = SimpleOrderServlet.getIntegerParam(req, kRP_quant_2556, 0);
            _2557_count = SimpleOrderServlet.getIntegerParam(req, kRP_quant_2557, 0);

        } catch (NumberFormatException ne) {
            try {
                errors.add("Please check to make sure the quantity number you input is right.");
            } catch (Exception e) {
            }
        }

        int total_count = _2551_count + _2552_count + _2553_count + _2554_count + _2555_count + _2556_count + _2557_count;
        if (total_count == 0) {
            try {
                errors.add("You need to input the quantity before submitting the order.");
            } catch (Exception e) {
            }
        }
        //line prices
        float _2551_total = _2551Price * _2551_count;
        float _2552_total = _2552Price * _2552_count;
        float _2553_total = _2553Price * _2553_count;
        float _2554_total = _2554Price * _2554_count;
        float _2555_total = _2555Price * _2555_count;
        float _2556_total = _2556Price * _2556_count;
        float _2557_total = _2557Price * _2557_count;


        req.setAttribute(kRP_quant_2551, new Integer(_2551_count));
        req.setAttribute(kRP_quant_2552, new Integer(_2552_count));
        req.setAttribute(kRP_quant_2553, new Integer(_2553_count));
        req.setAttribute(kRP_quant_2554, new Integer(_2554_count));
        req.setAttribute(kRP_quant_2555, new Integer(_2555_count));
        req.setAttribute(kRP_quant_2556, new Integer(_2556_count));
        req.setAttribute(kRP_quant_2557, new Integer(_2557_count));

        //filling line items to the skuList
        try {
            if (_2551_count > 0) {
                order.addLineItem("2551", _2551_count, _2551Price, _2551_total, "", "", "");

            }
            if (_2552_count > 0) {
                order.addLineItem("2552", _2552_count, _2552Price, _2552_total, "", "", "");

            }
            if (_2553_count > 0) {
                order.addLineItem("2553", _2553_count, _2553Price, _2553_total, "", "", "");

            }
            if (_2554_count > 0) {
                order.addLineItem("2554", _2554_count, _2554Price, _2554_total, "", "", "");

            }
            if (_2555_count > 0) {
                order.addLineItem("2555", _2555_count, _2555Price, _2555_total, "", "", "");

            }
            if (_2556_count > 0) {
                order.addLineItem("2556", _2556_count, _2556Price, _2556_total, "", "", "");

            }
            if (_2557_count > 0) {
                order.addLineItem("2557", _2557_count, _2557Price, _2557_total, "", "", "");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (order.getBillingAddress().state.equals("SD"))
            order.tax_pct = SD_tax;

        try {
            order.recalculateBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //added on May 23
    public static String getStringParam(HttpServletRequest req, String paramName, String defaultValue) {
        String param = req.getParameter(paramName);

        if (param == null)
            param = defaultValue;

        return param.trim();

    }

    public static Vector getStringVector(HttpServletRequest req, String paramName, Vector defaultValue) {
        String[] param_ay = req.getParameterValues(paramName);
        Vector param = new Vector();

        if (param_ay == null)
            param = defaultValue;
        else {
            if (param_ay.length > 0) {
                for (int i = 0; i < param_ay.length; i++) {
                    param.addElement(param_ay[i]);
                }
            }
        }

        return param;

    }

    public static int getIntegerParam(HttpServletRequest req, String paramName, int defaultValue) {
        int param = 0;

        try {
            param = new Integer(req.getParameter(paramName)).intValue();
        } catch (Exception ex) {
            param = defaultValue;
        }

        return param;

    }
}

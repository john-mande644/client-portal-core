package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Client;
import com.owd.core.business.Contact;
import com.owd.core.business.order.*;
import com.owd.core.business.owdChoiceList;
import com.owd.core.xml.OrderXMLDoc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class OWDOrderEntryServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();
//constants used in this program
    public static final String kcurrentorder = "current_order";
    public static final String kordernum = "ordernum";
//order info
    public static final String kclientid = "client_id";
    public static final String kclientname = "client_name";
    public static final String kclienturl = "client_url";
    public static final String kordertype = "order_type";
    public static final String kponum = "po_num";
    public static final String korderrefnum = "order_refnum";
    public static final String kactualorderdate = "actual_order_date";
    public static final String kbackorderrule = "backorder_rule";
//customer info
    public static final String kcustomers = "customers";
    public static final String kcustnum = "cust_num";
    public static final String kbillfname = "bill_fname";
    public static final String kbilllname = "bill_lname";
    public static final String kbillcompany = "bill_company";
    public static final String kbilladdr1 = "bill_addr1";
    public static final String kbilladdr2 = "bill_addr2";
    public static final String kbillcity = "bill_city";
    public static final String kbillstate = "bill_state";
    public static final String kbillzip = "bill_zip";
    public static final String kbillcountry = "bill_country";
    public static final String kbillemail = "bill_email";
    public static final String kbillphone = "bill_phone";
    public static final String kbillfax = "bill_fax";
    public static final String kpaytype = "pay_type";
    public static final String kccnum = "cc_num";
    public static final String kccexpmon = "cc_exp_mon";
    public static final String kccexpyear = "cc_exp_year";
    public static final String kchecknum = "check_num";
//line item info
    public static final String kclientitems = "client_items";
    public static final String kclientcolor = "client_color";
    public static final String kclientsize = "client_size";
    public static final String kremoved = "removed";
    public static final String kitemsku = "item_sku";
    public static final String kitemdesc = "item_desc";
    public static final String kitemcolor = "item_color";
    public static final String kitemsize = "item_size";
    public static final String kitemcustsku = "item_cust_sku";
    public static final String kqty = "qty";
    public static final String kitemprice = "item_price";
    public static final String kitemdiscount = "item_discount";
    public static final String klinetotal = "line_total";
    public static final String ksubtotal = "subtotal";
    public static final String kdiscount = "discount";
    public static final String kisgift = "is_gift";
    public static final String kgiftmessage = "gift_message";
    public static final String kgiftwrapfee = "gift_wrap_fee";
    public static final String ktaxrate = "taxrate";
    public static final String ktaxamount = "taxamount";
    public static final String kamountpaid = "amount_paid";
    public static final String kdatepaid = "date_paid";
    public static final String kbalance = "balance";
    public static final String ksh = "s_h";
    public static final String ktotal = "total";
//shipping info
    public static final String kshipmethod = "ship_method";
    public static final String kfreightterm = "freight_term";
    public static final String kthirdpartyrefnum = "third_party_refnum";
    public static final String kbillshipsame = "bill_ship_same";
    public static final String kshipfname = "ship_fname";
    public static final String kshiplname = "ship_lname";
    public static final String kshipcompany = "ship_company";
    public static final String kshipaddr1 = "ship_addr1";
    public static final String kshipaddr2 = "ship_addr2";
    public static final String kshipcity = "ship_city";
    public static final String kshipstate = "ship_state";
    public static final String kshipzip = "ship_zip";
    public static final String kshipcountry = "ship_country";
    public static final String kshipemail = "ship_email";
    public static final String kshipphone = "ship_phone";
    public static final String kshipfax = "ship_fax";
    public static final String kwarehousenotes = "warehouse_notes";
//Special Services
    public static final String kdeclaredvalue = "declared_value";
    public static final String kcalltag = "call_tag";
  //  public static final String kcod = "cod";
    public static final String ksaturdaydelivery = "saturday_delivery";
//other
    public static final String kactiontype = "action_type";
    public static final String kadditem = "Add Item";
    public static final String kremoveitem = "Remove";
    public static final String ksubmitorder = "Submit Order";
    public static final String kgetcustinfo = "Get Customer Information";
    public static final String knumberofitems = "number_of_items";
    public static final String kgetiteminfo = "get item information";
    public static final String kgetcustomer = "OK";
//back rules
    static final public String kBackOrderAll = "BACKORDER";
    static final public String kPartialShip = "PARTIALSHIP";
    static final public String kHoldBackOrder = "HOLDORDER";
    static final public String kRejectBackOrder = "NOBACKORDER";
//payment types
    static final public String kCreditCard = "CC";
    static final public String kCheck = "CK";
    static final public String kPurchaseOrder = "PO";
    static final public String kClientManaged = "CLIENT";
//errors
    public static final String kordererrors = "order_errors";
    public static final String kcusterrors = "cust_errors";
    public static final String kitemerrors = "item_errors";
    public static final String kshiperrors = "ship_errors";
//taxes
    public static final float SD_tax = (float) 0.06;
//corresponding error msg
    public static final String[] basic_err = {"Purchaser's Name", "Purchaser's Company Name", "Billing Address",
                                              "Billing State", "Billing ZIP Code", "Credit Card Number", "Credit Card Expiration Month",
                                              "Credit Card Expiration Year"};
    public static final String[] ship_err = {"Delivery Name", "Consignee's Company Name", "Shipping Address",
                                             "Destination City", "Destination State", "Destination ZIP Code"};
//local variable
//Order current_order = new Order();
    private Vector order_errors = new Vector();
    private Vector cust_errors = new Vector();
    private Vector item_errors = new Vector();
    private Vector ship_errors = new Vector();
    private Vector backorder_rules = null;
    private Vector payment_types = null;
    private
    owdChoiceList ship_methods = null;
    private
    ArrayList client_items = null;
    private ArrayList records = new ArrayList();
    private
    int number_of_items = 0;
    private String client_id = null;
    private String client_name = null;
    private Order current_order = null;
    private ArrayList customers = null;
    private ArrayList sku_desc_cSKU_price = new ArrayList();//cSKU and price shall be fixed by sku or desc
    private TreeSet color = new TreeSet();
    private TreeSet size = new TreeSet();

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        try {
            log.debug("Hello doGet");
            doPost(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        order_errors.removeAllElements();
        cust_errors.removeAllElements();
        item_errors.removeAllElements();
        ship_errors.removeAllElements();
        sku_desc_cSKU_price.clear();
        log.debug("test the impact of modifying classes to Orion 2");
        Client client = null;
        try {
            client = Client.getClientForUser(req.getUserPrincipal().getName());
        } catch (Exception e) {
            log.debug("Get Client Error: " + e);
            e.printStackTrace();
        }
        if (client != null) {
            client_id = client.client_id;
            client_name = client.company_name;
        }
        if (!"0".equals(client_id)) {
            try {
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (client_id != null)
        {
            try{
            current_order = new Order(client_id);
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
/* just for test...
else
{
//order_errors.addElement("Access denied: no com.owd.api.client id found");
client_id = "112";
current_order = new Order(client_id);
}
*/
        log.debug("Client ID:" + client_id);
        String action_type = OWDOrderEntryServlet.getStringParam(req, kactiontype, "");
        if (action_type == null)
            action_type = "";
//log.debug("Action type: "+action_type);
        number_of_items = OWDOrderEntryServlet.getIntegerParam(req, knumberofitems, 5);//5 empty rows initially, better ideas?
        if (client_id != null && action_type != null) {
            log.debug("client_id and action_type not null");
            if (ksubmitorder.equals(action_type)) {
                log.debug("submitting order...\n");
                fillOrderInfo(current_order, req, order_errors);
                fillCustomerInfo(current_order, req, cust_errors);
                fillItemInfo(current_order, req, item_errors);
                fillShipInfo(current_order, req, ship_errors);
//log.debug("errors: "+(String)order_errors.get(0));
                if (order_errors.size() <= 0 && cust_errors.size() <= 0 && item_errors.size() <= 0 && ship_errors.size() <= 0) {
//log.debug("Saving new orders...\n");
                    String order_num = current_order.saveNewOrder(OrderUtilities.kHoldForPayment, false);
//log.debug("Last Error: "+current_order.last_error+"\n");
//log.debug("Order is completed? "+current_order.completed+"\n");
                    if (current_order.completed) {
                        req.setAttribute(kordernum, order_num);
                        req.setAttribute(kcurrentorder, current_order);
                        req.setAttribute(kclientid, client_id);
                        req.setAttribute(kclientname, client_name);
                        try {
                            req.getRequestDispatcher("order_entry_response.jsp").forward(req, resp);
                        } catch (IOException ioe) {
                            log.debug("Redirect Error " + ioe);
                        }
                    } else {
                        order_errors.add((current_order.last_payment_error.length() > 1 ? current_order.last_payment_error : current_order.last_error));
                        req.setAttribute("order_errors", order_errors);
                        refill(req, resp);
                    }
                } else {
                    refill(req, resp);
                }
            } else if (kgetcustinfo.equals(action_type) || kgetcustomer.equals(action_type)) {
//log.debug("get customer info...\n");
                fillOrderInfo(current_order, req, order_errors);
//add field names to be retrived of owd_order table
                String customer_num = OWDOrderEntryServlet.getStringParam(req, kcustnum, "");
                String lname = OWDOrderEntryServlet.getStringParam(req, kbilllname, "%");
                String fname = OWDOrderEntryServlet.getStringParam(req, kbillfname, "%");
//log.debug("customer_num: "+customer_num+" lname: "+lname + "fname: "+fname);
                if (customer_num.length() > 0) {
                    customers = Customer.getCustomerBillInfoForID(customer_num, client_id);
                } else if (!("%".equals(lname))) {
                    customers = Customer.getCustomerBillInfoForName(lname, fname, client_id);
                    if (customers != null)
                        log.debug("number of possible customers: " + customers.size());
                    else
                        log.debug("got none customers");
                } else {
                    customers = Customer.getCustomerBillInfo(client_id);
                }
                if (customers != null && customers.size() == 1) {
                    fillCustomerBillInfo(current_order, customers);
                } else if (customers != null && customers.size() > 1) {
                    ArrayList cust_info_list = new ArrayList();
/*
ArrayList cust = new ArrayList();
cust.add("order_num");
cust.add("bill_last_name");
cust.add("bill_first_name");
cust.add("bill_address_one");
cust.add("bill_zip");
cust.add("bill_phone_num");
*/
                    Iterator cust_it = customers.iterator();
                    while (cust_it.hasNext()) {
                        ArrayList customer = (ArrayList) cust_it.next();
                        ArrayList cust_info = new ArrayList();
                        cust_info.add((String) customer.get(12));
                        cust_info.add((String) customer.get(0));
                        cust_info.add((String) customer.get(1));
                        cust_info.add((String) customer.get(2));
                        cust_info.add((String) customer.get(6));
                        cust_info.add((String) customer.get(9));
                        cust_info_list.add(cust_info);
                    }
                    req.setAttribute(kcustomers, cust_info_list);
                }
                refill(req, resp);
            } else
                refill(req, resp);
        }
    }

    private void refill(HttpServletRequest req, HttpServletResponse resp) {
//create objects for populating selection lists in order_entry_form
        log.debug("refilling...\n");
        backorder_rules = new Vector();
        backorder_rules.addElement(kBackOrderAll);
        backorder_rules.addElement(kPartialShip);
        backorder_rules.addElement(kHoldBackOrder);
        backorder_rules.addElement(kRejectBackOrder);
        payment_types = new Vector();
        payment_types.addElement(kCreditCard);
        payment_types.addElement(kCheck);
        payment_types.addElement(kPurchaseOrder);
        payment_types.addElement(kClientManaged);
        try {
            ship_methods = OrderUtilities.getServiceList();
        } catch (Exception e) {
            log.debug("Can't get ship method list. " + e + "\n");
        }
        try {
//add field names to be retrieved
            ArrayList items = new ArrayList();
            items.add("Inventory_num");
            items.add("description");
            items.add("client_item_key");
            items.add("price");
            items.add("item_color");
            items.add("item_size");
            if (client_id != null && !("".equals(client_id)))
                records = Inventory.getItemsForClientIDx(client_id, items);
            log.debug("There are " + records.size() + " items.");
            for (int i = 0; i < records.size(); i++) {
                Vector record = (Vector) records.get(i);
                client_items = new ArrayList();
                for (int j = 0; j < record.size(); j++) //this needs further improved
                {
                    String field = (String) record.get(j);
                    if (j <= 3)
                        client_items.add(field);
                    else if (j == 4 && field != null)
                        color.add(field);
                    else if (j == 5 && field != null)
                        size.add(field);
                }
                sku_desc_cSKU_price.add(client_items);
            }
        } catch (Exception e) {
            e.printStackTrace();
//log.debug("Can't get item list for this com.owd.api.client: "+e+"\n");
        }
//log.debug("There are "+sku_desc_cSKU_price.size()+" kinds of products.");
//log.debug("got all parameter objects\n");
        req.setAttribute(kbackorderrule, backorder_rules);
        req.setAttribute(kpaytype, payment_types);
        req.setAttribute(kclientitems, sku_desc_cSKU_price);
        req.setAttribute(kclientcolor, color);
        req.setAttribute(kclientsize, size);
        req.setAttribute(knumberofitems, new Integer(number_of_items));
        req.setAttribute(kshipmethod, ship_methods.getValues());
        req.setAttribute(kcurrentorder, current_order);
        req.setAttribute(kordererrors, order_errors);
        req.setAttribute(kcusterrors, cust_errors);
        req.setAttribute(kitemerrors, item_errors);
        req.setAttribute(kshiperrors, ship_errors);
        req.setAttribute(kclientname, client_name);
        String redirect_dest = "simple_order_entry.jsp";
        if (!("0".equals(client_id)))
            redirect_dest = "client_order_entry.jsp";
        try {
            req.getRequestDispatcher(redirect_dest).forward(req, resp);
        } catch (Exception e) {
            log.debug("Redirect Error " + e);
        }
    }

    public void checkInfo(HttpServletRequest req) {
    }

    private void fillOrderInfo(Order order, HttpServletRequest req, Vector order_errors) {
        order.order_type = OWDOrderEntryServlet.getStringParam(req, kordertype, "");
        order.po_num = OWDOrderEntryServlet.getStringParam(req, kponum, "");
        order.order_refnum = OWDOrderEntryServlet.getStringParam(req, korderrefnum, "");
        if ("0".equals(client_id) && order.order_refnum.length() <= 0)
            order_errors.addElement("Please Enter Your Order Reference Number");
        order.actual_order_date = OWDOrderEntryServlet.getStringParam(req, kactualorderdate, "");
        order.backorder_rule = OWDOrderEntryServlet.getStringParam(req, kbackorderrule, kHoldBackOrder);
        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
    }

    private void fillCustomerInfo(Order order, HttpServletRequest req, Vector cust_errors) {
        String bill_lname = OWDOrderEntryServlet.getStringParam(req, kbilllname, "");
        String bill_fname = OWDOrderEntryServlet.getStringParam(req, kbillfname, "");
        String bill_name = "";
        if ("".equals(bill_lname))
            cust_errors.addElement("Please enter valid customer's last name");
        else if ("".equals(bill_fname))
            cust_errors.addElement("Please enter valid customer's first name");
        else
            bill_name = bill_fname + " " + bill_lname;
        String bill_phone = OWDOrderEntryServlet.getStringParam(req, kbillphone, "");
        String bill_fax = OWDOrderEntryServlet.getStringParam(req, kbillfax, "");
        String bill_email = OWDOrderEntryServlet.getStringParam(req, kbillemail, "");
        Contact ct = new Contact(bill_name, bill_phone, bill_fax, bill_email, "");
        order.setBillingContact(ct);
        String bill_company = OWDOrderEntryServlet.getStringParam(req, kbillcompany, ".");
        String bill_addr1 = OWDOrderEntryServlet.getStringParam(req, kbilladdr1, "");
        if ("".equals(bill_addr1))
            cust_errors.addElement("Please enter valid address");
        String bill_addr2 = OWDOrderEntryServlet.getStringParam(req, kbilladdr2, "");
        String bill_city = OWDOrderEntryServlet.getStringParam(req, kbillcity, "");
        if ("".equals(bill_city))
            cust_errors.addElement("Please enter valid city name");
        String bill_state = OWDOrderEntryServlet.getStringParam(req, kbillstate, "");
        if ("".equals(bill_state))
            cust_errors.addElement("Please enter valid state name");
        String bill_zip = OWDOrderEntryServlet.getStringParam(req, kbillzip, "");
        if ("".equals(bill_zip))
            cust_errors.addElement("Please enter valid ZIP code");
        String bill_country = OWDOrderEntryServlet.getStringParam(req, kbillcountry, "USA");
        Address addr = new Address(bill_company, bill_addr1, bill_addr2, bill_city, bill_state, bill_zip, bill_country);
        order.setBillingAddress(addr);
        String pay_type = OWDOrderEntryServlet.getStringParam(req, kpaytype, "");
        if (kCreditCard.equals(pay_type)) {
            order.cc_num = OWDOrderEntryServlet.getStringParam(req, kccnum, "");
            if ("".equals(order.cc_num))
                cust_errors.addElement("Please enter valid credit card number");
            order.cc_exp_mon = OWDOrderEntryServlet.getIntegerParam(req, kccexpmon, 0);
            order.cc_exp_year = OWDOrderEntryServlet.getIntegerParam(req, kccexpyear, 0);
            if (order.cc_exp_year < (Calendar.getInstance().get(Calendar.YEAR)))
                cust_errors.addElement("credit card expires");
            else if (order.cc_exp_year == (Calendar.getInstance().get(Calendar.YEAR))) {
                if (order.cc_exp_mon < Calendar.getInstance().get(Calendar.MONTH))
                    cust_errors.addElement("credit card expires");
            }
        } else if (kCheck.equals(pay_type))
            order.check_num = OWDOrderEntryServlet.getStringParam(req, kchecknum, "");
        order.bill_cc_type = pay_type;
    }

    private void fillCustomerBillInfo(Order order, ArrayList customers) {
        ArrayList customer = (ArrayList) customers.get(0);
        String bill_lname = (String) customer.get(0);
        String bill_fname = (String) customer.get(1);
        String bill_name = bill_fname + " " + bill_lname;
        String bill_addr1 = (String) customer.get(2);
        String bill_addr2 = (String) customer.get(3);
        String bill_city = (String) customer.get(4);
        String bill_state = (String) customer.get(5);
        String bill_zip = (String) customer.get(6);
        String bill_country = (String) customer.get(6);
        String bill_company = (String) customer.get(8);
        String bill_phone = (String) customer.get(9);
        String bill_fax = (String) customer.get(10);
        String bill_email = (String) customer.get(11);
        Contact ct = new Contact(bill_name, bill_phone, bill_fax, bill_email, "");
        order.setBillingContact(ct);
        Address addr = new Address(bill_company, bill_addr1, bill_addr2, bill_city, bill_state, bill_zip, bill_country);
        order.setBillingAddress(addr);
    }

    private void fillItemInfo(Order order, HttpServletRequest req, Vector item_errors) {
        log.debug("filling item info...\n");
        int number_of_items = OWDOrderEntryServlet.getIntegerParam(req, knumberofitems, 0);
        log.debug("total number of items: " + number_of_items);
        int i = 0;
        int qty = 1;
        while (qty != 0)// compromise this way the item needs to be input continuously
        {
            qty = 0;
            String removed = OWDOrderEntryServlet.getStringParam(req, kremoved + i, "");
            if (kremoved.equals(removed))
                continue;
            else {
                String item_sku = OWDOrderEntryServlet.getStringParam(req, kitemsku + i, "");
                String item_desc = OWDOrderEntryServlet.getStringParam(req, kitemdesc + i, "");
                if ("".equals(item_sku) && "".equals(item_desc))
                    continue;
                else {
                    String item_color = OWDOrderEntryServlet.getStringParam(req, kitemcolor + i, "");
                    String item_size = OWDOrderEntryServlet.getStringParam(req, kitemsize + i, "");
                    String item_cust_sku = OWDOrderEntryServlet.getStringParam(req, kitemcustsku + i, "");
                    qty = OWDOrderEntryServlet.getIntegerParam(req, kqty + i, 0);
                    log.debug("Quantity: " + qty);
                    if (qty > 0) {
                        float item_price = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, kitemprice + i, "0.0"));
                        float item_discount = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, kitemdiscount + i, ""));
                        float line_total = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, klinetotal + i, ""));
//LineItem item = new LineItem(item_sku, qty, item_price, line_total, item_desc, item_color, item_size);
//item.line_item_disc = item_discount;
                        try {
                            order.addLineItem(item_sku, qty, item_price, line_total, item_desc, item_color, item_size);
                        } catch (Exception e) {
                            log.debug("add line itme error: " + e);
                        }
                        log.debug("items: " + order.skuList.size());
                    }
                }
            }
            i++;
        }
        order.is_gift = OWDOrderEntryServlet.getStringParam(req, kisgift, "0");
        if (order.is_gift.length() > 0) {
            order.is_gift = "1";
            order.gift_message = OWDOrderEntryServlet.getStringParam(req, kgiftmessage, "");
            String gift_wrap_fee = OWDOrderEntryServlet.getStringParam(req, kgiftwrapfee, "0.0");
            gift_wrap_fee = gift_wrap_fee.replace('$', ' ');
            order.total_gift_wrap = OWDUtilities.floatFromString(gift_wrap_fee);
        }
        order.total_product_cost = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, ksubtotal, "0.0"));
        order.discount = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, kdiscount, "0.0"));
        order.tax_pct = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, ktaxrate, "0.0"));
        order.total_tax_cost = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, ktaxamount, "0.0"));
        order.total_shipping_cost = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, ksh, "0.0"));
        order.total_order_cost = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, ktotal, "0.0"));
        order.paid_amount = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, kamountpaid, "0.0"));
        order.paid_date = OWDOrderEntryServlet.getStringParam(req, kdatepaid, "");
        order.order_balance = OWDUtilities.floatFromString(OWDOrderEntryServlet.getStringParam(req, kbalance, "0.0"));
        if (order.skuList.size() == 0)
            log.debug("no line items added");
    }

    private void fillShipInfo(Order order, HttpServletRequest req, Vector ship_errors) {
        log.debug("filling shipping info...\n");
        String ship_lname = OWDOrderEntryServlet.getStringParam(req, kshiplname, "");
        String ship_fname = OWDOrderEntryServlet.getStringParam(req, kshipfname, "");
        String ship_name = "";
        ShippingInfo sinfo = new ShippingInfo();
        if ("".equals(ship_lname))
            ship_errors.addElement("Please enter valid shipping last name");
        else if ("".equals(ship_fname))
            ship_errors.addElement("Please enter valid shipping first name");
        else
            ship_name = ship_fname + " " + ship_lname;
        String ship_phone = OWDOrderEntryServlet.getStringParam(req, kshipphone, "");
        String ship_fax = OWDOrderEntryServlet.getStringParam(req, kshipfax, "");
        String ship_email = OWDOrderEntryServlet.getStringParam(req, kshipemail, "");
        Contact ct = new Contact(ship_name, ship_phone, ship_fax, ship_email, "");
        sinfo.shipContact = ct;
        String ship_company = OWDOrderEntryServlet.getStringParam(req, kshipcompany, ".");
        String ship_addr1 = OWDOrderEntryServlet.getStringParam(req, kshipaddr1, "");
        if ("".equals(ship_addr1))
            ship_errors.addElement("Please enter valid address");
        String ship_addr2 = OWDOrderEntryServlet.getStringParam(req, kshipaddr2, "");
        String ship_city = OWDOrderEntryServlet.getStringParam(req, kshipcity, "");
        if ("".equals(ship_city))
            ship_errors.addElement("Please enter valid city name");
        String ship_state = OWDOrderEntryServlet.getStringParam(req, kshipstate, "");
        if ("".equals(ship_state))
            ship_errors.addElement("Please enter valid state name");
        String ship_zip = OWDOrderEntryServlet.getStringParam(req, kshipzip, "");
        if ("".equals(ship_zip))
            ship_errors.addElement("Please enter valid ZIP code");
        String ship_country = OWDOrderEntryServlet.getStringParam(req, kshipcountry, "USA");
        Address addr = new Address(ship_company, ship_addr1, ship_addr2, ship_city, ship_state, ship_zip, ship_country);
        sinfo.shipAddress = addr;
        String ship_method = OWDOrderEntryServlet.getStringParam(req, kshipmethod, "");
        try {
            if ("".equals(ship_method))
                ship_errors.addElement("Please select shipping method");
            if (ship_method.indexOf("Ground") >= 0) {
                sinfo.setShipOptions("UPS Ground", "Prepaid", "");
            } else if (ship_method.indexOf("Next Day") >= 0) {
                sinfo.setShipOptions("UPS Next Day Air Saver", "Prepaid", "");
            } else if (ship_method.indexOf("Global") >= 0) {
                sinfo.setShipOptions("USPS Global Priority Mail", "Prepaid", "");
            } else if (ship_method.indexOf("Priority Mail") >= 0) {
                sinfo.setShipOptions("Priority Mail", "Prepaid", "");
            } else if (ship_method.indexOf("2nd Day") >= 0) {
                sinfo.setShipOptions("UPS 2nd Day Air", "Prepaid", "");
            } else if (ship_method.indexOf("3 Day") >= 0) {
                sinfo.setShipOptions("UPS 3 Day Select", "Prepaid", "");
            }
            sinfo.carr_service = ship_method;
        } catch (Exception e) {
            log.debug("Set shipping method error: " + e + "\n");
            ship_errors.addElement("Ship method error");
        }
        sinfo.carr_freight_terms = OWDOrderEntryServlet.getStringParam(req, kfreightterm, "");
        sinfo.third_party_refnum = OWDOrderEntryServlet.getStringParam(req, kthirdpartyrefnum, "");
        sinfo.whse_notes = OWDOrderEntryServlet.getStringParam(req, kwarehousenotes, "");
        sinfo.declared_value = OWDOrderEntryServlet.getStringParam(req, kdeclaredvalue, "");
       // sinfo.cod_charge = OWDOrderEntryServlet.getStringParam(req, kcod, "");
        sinfo.ss_call_tag = OWDOrderEntryServlet.getStringParam(req, kcalltag, "");
        sinfo.ss_call_tag = "on".equals(sinfo.ss_call_tag) ? "1" : "0";
        sinfo.ss_saturday = OWDOrderEntryServlet.getStringParam(req, ksaturdaydelivery, "");
        sinfo.ss_saturday = "on".equals(sinfo.ss_saturday) ? "1" : "0";
        order.setShippingInfo(sinfo);
    }

    public void fillOrder(Order order, HttpServletRequest req, Vector errors) {
    }
//added on May 23
    public static String getStringParam(HttpServletRequest req, String paramName, String defaultValue) {
        String param = req.getParameter(paramName);
        if (param == null || "".equals(param))
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

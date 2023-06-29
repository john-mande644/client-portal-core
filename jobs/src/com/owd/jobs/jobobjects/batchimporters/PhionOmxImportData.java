package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.clients.PhionUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 31, 2003
 * Time: 8:41:50 AM
 * To change this template use Options | File Templates.
 */
public class PhionOmxImportData {
private final static Logger log =  LogManager.getLogger();

    int OrderID = 0;
    int CustomerID = 1;
    int CustomerFirstName = 2;
    int CustomerLastName = 3;
    int CustomerPhone = 4;
    int CustomerEmail = 5;
    int BillingStreet1 = 6;
    int BillingStreet2 = 7;
    int BillingCity = 8;
    int BillingState = 9;
    int BillingZip = 10;
    int BillingCountry = 11;
    int PaymentType = 12;
    int PaymentCostModifier = 13;
    int PaymentConfirmationMessage = 14;
    int ShippingSameAsBilling = 15;
    int ShippingName = 16;
    int ShippingFirstName = 17;
    int ShippingLastName = 18;
    int ShippingStreet1 = 19;
    int ShippingStreet2 = 20;
    int ShippingCity = 21;
    int ShippingState = 22;
    int ShippingZip = 23;
    int ShippingCountry = 24;
    int TotalShippingWeight = 25;
    int OrderStatus = 26;
    int OrderDateTime = 27;
    int OrderNotes = 28;
    int OrderTax = 29;
    int OrderShippingCost = 30;
    int OrderSubTotal = 31;
    int QuantityDiscounts = 32;
    int OrderTotal = 33;
    int AdminShippingTracking = 34;
    int AdminSalesRep = 35;
    int AdminNotes = 36;
    int dPerOrderHandlingFee = 37;
    int DiscountCode = 38;
    int DiscountAmount = 39;
    int DiscountCookie = 40;
    int QuantityDiscounts2 = 41;
    int Notes = 42;
    int CustomerCompanyName = 43;
    int CreditCard = 44;
    int CreditCardExtra = 45;
    int Keycode = 46;
    int Return = 47;
    int blankfield = 48;
    int ProductID = 49;
    int MfgName = 50;
    int Desc1 = 51;
    int Price = 52;
    int Weight = 53;
    int Qty = 54;
    int MfgPart = 55;
    int PartNo = 56;

    static Map<String,String> aliasSKUs = new TreeMap<String,String>();


    DelimitedReader dataReader = null;

    protected List orderPositionList = new ArrayList();

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

    static {
        aliasSKUs.put("catalog-2007-12", "catalog-2007-12pk");
        aliasSKUs.put("ALKGR07SPEC", "ALKGR");
    }
    public PhionOmxImportData() {

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
        //log.debug("in DH loadOrder");
        if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
            throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());

        OrderData data = (OrderData) getOrderPositionList().get(orderIndex);
        DelimitedReader rdr = getDataReader();

        for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
            //log.debug("DH loading row " + row);
            if (row == data.rowIndexStart) {
                //first row
                order.order_refnum = rdr.getStrValue(OrderID, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }
                //response.add(order.order_refnum);
                //log.debug("DH loading ref " + order.order_refnum);

                order.getBillingContact().setName(rdr.getStrValue(CustomerFirstName, row, "") + " " + rdr.getStrValue(CustomerLastName, row, ""));
                order.getBillingContact().email = rdr.getStrValue(CustomerEmail, row, "");
                order.getBillingContact().phone = rdr.getStrValue(CustomerPhone, row, "");

                order.getBillingAddress().company_name = rdr.getStrValue(CustomerCompanyName, row, ".");
                if (order.getBillingAddress().company_name.trim().length() < 1) order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = rdr.getStrValue(BillingStreet1, row, "");
                order.getBillingAddress().address_two = rdr.getStrValue(BillingStreet2, row, "");
                order.getBillingAddress().city = rdr.getStrValue(BillingCity, row, "");
                order.getBillingAddress().state = rdr.getStrValue(BillingState, row, "");
                order.getBillingAddress().zip = rdr.getStrValue(BillingZip, row, "");
                order.getBillingAddress().country = rdr.getStrValue(BillingCountry, row, "");
                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                order.getShippingContact().email = rdr.getStrValue(CustomerEmail, row, "");
                order.getShippingContact().phone = rdr.getStrValue(CustomerPhone, row, "");

                if ("1".equals(rdr.getStrValue(15, row, "").toUpperCase())) {
                    order.getShippingInfo().setShippingContact(Contact.createFromStorableString(order.getBillingContact().toStorableString()));
                    order.getShippingInfo().setShippingAddress(Address.createFromStorableString(order.getBillingAddress().toStorableString()));
                } else {
                order.getShippingContact().setName(rdr.getStrValue(ShippingFirstName, row, "") + " " + rdr.getStrValue(ShippingLastName, row, ""));


                order.getShippingAddress().company_name = "";
                if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                order.getShippingAddress().address_one = rdr.getStrValue(ShippingStreet1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(ShippingStreet2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(ShippingCity, row, "");
                order.getShippingAddress().state = rdr.getStrValue(ShippingState, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(ShippingZip, row, "");
                order.getShippingAddress().country = rdr.getStrValue(ShippingCountry, row, "");
                    order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);

                }



                order.total_shipping_cost = rdr.getFloatValue(OrderShippingCost, row, 0.00f);
                order.total_tax_cost = rdr.getFloatValue(OrderTax, row, 0.00f);
                order.discount = -1 * rdr.getFloatValue(DiscountAmount, row, 0.00f);

                order.getShippingInfo().comments = rdr.getStrValue(OrderNotes, row, "");

                order.order_type = rdr.getStrValue(Keycode, row, "Email Import");

                order.getShippingInfo().carr_service = rdr.getStrValue(ShippingName, row, "USPS Priority Mail").toUpperCase();



                order.ship_operator = "OrderMotion";
                order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.backorder_rule = OrderXMLDoc.kBackOrderAll;
                order.order_type = "Email Import";


                order.is_paid = 1;


                switch (rdr.getIntValue(PaymentType, row, 0)) {

                    case 1:

                        order.po_num = "Payment Type: Credit Card";
                        break;
                    case 3:

                        order.po_num = "Payment Type: Check";
                        break;
                    case 6:

                        order.po_num = "Payment Type: Open Invoice";
                        break;

                    case 8:

                        order.po_num = "Payment Type: Other";
                        break;
                    default:

                        order.po_num = "Payment Type: Unknown";

                }




            }

            String sku = LineItem.getCleanSKU(rdr.getStrValue(PartNo, row, ""));
            if (aliasSKUs.get(sku.toUpperCase()) != null) {
                sku = aliasSKUs.get(sku.toUpperCase());
            }
            String qty = rdr.getStrValue(Qty, row, "0");
            String unitPrice = rdr.getStrValue(Price, row, "0.00");
            String desc = rdr.getStrValue(Desc1, row, "");

        if (sku.toUpperCase().startsWith("FREE-")) {

                //ignore free- items, but add absolute value of price as order-level discount value
              //  float value = rdr.getFloatValue(4, row, 0.00f);

                order.discount = (-1.00f * Math.abs(rdr.getFloatValue(Qty, row, 0.00f) * rdr.getFloatValue(Price, row, 0.00f))) + (-1.00f * Math.abs(order.discount));
                PhionUtilities.getInstance().addItem(order, "DISCOUNT", qty, "0.00", "0.00", desc, "", "");

            } else if (sku.toUpperCase().startsWith("DIS") || sku.toUpperCase().startsWith("DISCOUNT") || sku.toUpperCase().startsWith("WDISCOUNT") || sku.toUpperCase().startsWith("DISC-") || sku.toUpperCase().contains("FIVEDOLLAR")) {


                order.discount = (-1.00f * Math.abs(rdr.getFloatValue(Qty, row, 0.00f) * rdr.getFloatValue(Price, row, 0.00f))) + (-1.00f * Math.abs(order.discount));

                PhionUtilities.getInstance().addItem(order, "DISCOUNT", qty, "0.00", "0.00", desc, "", "");


            } else {

                // //log.debug("adding li" + rdr.getStrValue(8, row, "") + "," + rdr.getStrValue(6, row, ""));
                PhionUtilities.getInstance().addItem(order, sku, qty, unitPrice, "0.00", desc, "", "");
            }

            addPackInstructionItem(sku, order, Integer.parseInt(qty));



        }


        return order;
    }

    private static void addPackInstructionItem(String sku, Order order, int qty) throws Exception {
        if (sku.toUpperCase().equals("COMPPK-PW")) order.addInsertItemIfAvailable("INSTR-COMPSYS", qty);
        if (sku.toUpperCase().equals("PHMIRPK-PW")) order.addInsertItemIfAvailable("Inst200", qty);
        if (sku.toUpperCase().equals("ALKPK-PW")) order.addInsertItemIfAvailable("INSTR-ALKZERSYS", qty);
        if (sku.toUpperCase().equals("PHMAINPK-PW")) order.addInsertItemIfAvailable("INSTR-MAINTSYS", qty);
        if (sku.toUpperCase().equals("BASPK-PW")) order.addInsertItemIfAvailable("INSTR-BASICSYS", qty);
        if (sku.toUpperCase().equals("DETOXPK")) order.addInsertItemIfAvailable("Inst500", qty);
        if (sku.toUpperCase().equals("WGHTLOSSPK")) order.addInsertItemIfAvailable("Inst700", qty);
        if (sku.toUpperCase().equals("COMPPK-CP")) order.addInsertItemIfAvailable("INSTR-COMPSYS", qty);
        if (sku.toUpperCase().equals("PHMIRPK-CP")) order.addInsertItemIfAvailable("Inst200", qty);
        if (sku.toUpperCase().equals("ALKPK-CP")) order.addInsertItemIfAvailable("INSTR-ALKZERSYS", qty);
        if (sku.toUpperCase().equals("PHMAINPK-CP")) order.addInsertItemIfAvailable("INSTR-MAINTSYS", qty);
        if (sku.toUpperCase().equals("BASPK-CP")) order.addInsertItemIfAvailable("INSTR-BASICSYS", qty);
        if (sku.toUpperCase().equals("ALKZP")) order.addInsertItemIfAvailable("PIALKZ", qty);
        if (sku.toUpperCase().equals("ALKZC")) order.addInsertItemIfAvailable("PIALKZ", qty);
        if (sku.toUpperCase().equals("BASCC")) order.addInsertItemIfAvailable("PIBASC", qty);
        if (sku.toUpperCase().equals("BASCP")) order.addInsertItemIfAvailable("PIBASC", qty);
        if (sku.toUpperCase().equals("COLNP")) order.addInsertItemIfAvailable("PICOLN", qty);
        if (sku.toUpperCase().equals("COLNC")) order.addInsertItemIfAvailable("PICOLN", qty);
        if (sku.toUpperCase().equals("COMPP")) order.addInsertItemIfAvailable("PICOMP", qty);
        if (sku.toUpperCase().equals("COMPC")) order.addInsertItemIfAvailable("PICOMP", qty);
        if (sku.toUpperCase().equals("DETXP")) order.addInsertItemIfAvailable("PIDETX", qty);
        if (sku.toUpperCase().equals("MAINP ")) order.addInsertItemIfAvailable("PIMAIN", qty);
        if (sku.toUpperCase().equals("WTLSP")) order.addInsertItemIfAvailable("PIWTLS", qty);
        if (sku.toUpperCase().equals("STARP")) order.addInsertItemIfAvailable("PISTAR", qty);
        if (sku.toUpperCase().equals("DETXC")) order.addInsertItemIfAvailable("PIDETX", qty);
        if (sku.toUpperCase().equals("MAINC ")) order.addInsertItemIfAvailable("PIMAIN", qty);
        if (sku.toUpperCase().equals("WTLSC")) order.addInsertItemIfAvailable("PIWTLS", qty);
        if (sku.toUpperCase().equals("STARC")) order.addInsertItemIfAvailable("PISTAR", qty);
    }

    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size) throws Exception {
        //log.debug("adding li:" + sku + "," + qty + "," + unitPrice + "," +
         //       linePrice + "," +
         //       desc + "," +
         //       color + "," + size);

        if (new Float(unitPrice).floatValue() < 0.00f) {
            order.discount = order.discount + new Float(unitPrice).floatValue();
        } else {
            order.addLineItem(sku,
                    qty,
                    unitPrice,
                    linePrice,
                    desc,
                    color, size);
        }
    }

    public int getOrderCount() {
        return getOrderPositionList().size();
    }

    protected void processReader() {

        String orderRef = "thisisaninvalidorderreference";
        if (getDataReader() == null) return;

        try {
            for (int row = 0; row < getDataReader().getRowCount(); row++) {

                for (int col = 0; col < getDataReader().getRowSize(row); col++) {
                    //log.debug("Row " + row + " Col " + col + " ::" + getDataReader().getStrValue(col, row, "**NADA**"));
                }

            }
        } catch (Exception exxx) {
            exxx.printStackTrace();
        }


        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            //log.debug("DH handling row " + row);
            if (!(getDataReader().getStrValue(0, row, "").trim().equals(""))) {
                if (orderRef.equals(getDataReader().getStrValue(0, row, orderRef))) {
                    //got the same order
                    //log.debug("DH found extra row");
                    OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                    //log.debug("DH adding item to order " + data.orderRef);
                } else {
                    //new order
                    //log.debug("DH found new row");
                    OrderData data = new OrderData();
                    orderRef = getDataReader().getStrValue(0, row, orderRef);
                    data.orderRef = orderRef;
                    data.rowIndexStart = row;
                    data.rowsForOrder = 1;
                    getOrderPositionList().add(data);
                    //log.debug("DH added entry for " + orderRef + ", row " + row);
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

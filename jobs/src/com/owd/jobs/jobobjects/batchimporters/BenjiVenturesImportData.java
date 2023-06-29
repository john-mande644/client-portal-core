package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.business.order.Order;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 31, 2003
 * Time: 8:41:50 AM
 * To change this template use Options | File Templates.
 */
public class BenjiVenturesImportData {
private final static Logger log =  LogManager.getLogger();

    int BillingSalutation = 0;
    int BillingFirstName = 1;
    int BillingMiddleInitial = 2;
    int BillingLastName = 3;
    int BillingSuffix = 4;
    int BillingTitle = 5;
    int BillingCompany = 6;
    int BillingAddress1 = 7;
    int BillingAddress2 = 8;
    int BillingCity = 9;
    int BillingState = 10;
    int BillingZip = 11;
    int BillingProvince = 12;
    int BillingCountry = 13;
    int BillingEmail = 14;
    int BillingPhone1 = 15;
    int BillingPhone2 = 16;
    int BillingFax = 17;
    int ShiptoBillingAddress = 18;
    int ShippingSalutation = 19;
    int ShippingFirstName = 20;
    int ShippingMiddleInitial = 21;
    int ShippingLastName = 22;
    int ShippingSuffix = 23;
    int ShippingTitle = 24;
    int ShippingCompany = 25;
    int ShippingAddress1 = 26;
    int ShippingAddress2 = 27;
    int ShippingCity = 28;
    int ShippingState = 29;
    int ShippingZip = 30;
    int ShippingProvince = 31;
    int ShippingCountry = 32;
    int ShippingEmail = 33;
    int ShippingPhone1 = 34;
    int ShippingPhone2 = 35;
    int ShippingFax = 36;
    int CartID = 37;
    int TransactionID = 38;
    int ItemsinCart = 39;
    int ProductSKU = 40;
    int ProductName = 41;
    int ProductQuantity = 42;
    int UnitPrice = 43;
    int CartTotal = 44;
    int ShippingandHandling = 45;
    int ShippingMethod = 46;
    int OrderTotal = 47;
    int Tax = 48;
    int GrandTotal = 49;
    int OrderComments = 50;
    int OrderDate = 51;


    DelimitedReader dataReader = null;

    protected List orderPositionList = new ArrayList();

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

    public BenjiVenturesImportData() {

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
                order.order_refnum = rdr.getStrValue(CartID, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }
                //response.add(order.order_refnum);
                //log.debug("DH loading ref " + order.order_refnum);

                order.getBillingContact().setName(rdr.getStrValue(BillingFirstName, row, "") + " " + rdr.getStrValue(BillingLastName, row, ""));
                order.getBillingContact().email = rdr.getStrValue(BillingEmail, row, "");
                order.getBillingContact().phone = rdr.getStrValue(BillingPhone1, row, "");

                order.getBillingAddress().company_name = rdr.getStrValue(BillingCompany, row, ".");
                if (order.getBillingAddress().company_name.trim().length() < 1) order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = rdr.getStrValue(BillingAddress1, row, "");
                order.getBillingAddress().address_two = rdr.getStrValue(BillingAddress2, row, "");
                order.getBillingAddress().city = rdr.getStrValue(BillingCity, row, "");
                order.getBillingAddress().state = rdr.getStrValue(BillingState, row, "");
                order.getBillingAddress().zip = rdr.getStrValue(BillingZip, row, "");
                order.getBillingAddress().country = rdr.getStrValue(BillingCountry, row, "");


                order.getShippingContact().setName(rdr.getStrValue(ShippingFirstName, row, "") + " " + rdr.getStrValue(ShippingLastName, row, ""));
                order.getShippingContact().email = rdr.getStrValue(ShippingEmail, row, "");
                order.getShippingContact().phone = rdr.getStrValue(ShippingPhone1, row, "");

                order.getShippingAddress().company_name = rdr.getStrValue(ShippingCompany, row, ".");
                if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                order.getShippingAddress().address_one = rdr.getStrValue(ShippingAddress1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(ShippingAddress2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(ShippingCity, row, "");
                order.getShippingAddress().state = rdr.getStrValue(ShippingState, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(ShippingZip, row, "");
                order.getShippingAddress().country = rdr.getStrValue(ShippingCountry, row, "");

                if("137".equals(order.getBillingAddress().country)) { order.getBillingAddress().country="USA";}
                if("137".equals(order.getShippingAddress().country)) { order.getShippingAddress().country="USA";}
                if (!CountryNames.exists(order.getBillingAddress().country)) {
                    throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country);
                }
                if (!CountryNames.exists(order.getShippingAddress().country)) {
                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                }
                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                order.total_shipping_cost = rdr.getFloatValue(ShippingandHandling, row, 0.00f);
                order.total_tax_cost = rdr.getFloatValue(Tax, row, 0.00f);
                //order.discount = -1 * Math.abs(rdr.getFloatValue(kOrder_Discount, row, 0.00f));
                order.getShippingInfo().comments = rdr.getStrValue(OrderComments, row, "");
                //order.getShippingInfo().whse_notes = rdr.getStrValue(kWarehouse_Notes, row, "");

                //  order.getShippingInfo().ss_saturday = rdr.getStrValue(kShip_Handling_Fee, row, "0").equals("1") ? "1" : "0";
                //  float cod_charge = OWDUtilities.roundFloat(rdr.getFloatValue(kCOD_Shipment, row, 0.00f));
                //  if (cod_charge > 0.00f) {
                //      order.getShippingInfo().cod_charge = "" + cod_charge;
                //      order.getShippingInfo().ss_cod = "1";
                //  }

                //  order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);


                //bill to client account
                order.getShippingInfo().setShipOptions(rdr.getStrValue(ShippingMethod, row, ""), "Prepaid", "");


                order.order_type = "Benji Ventures Download";
                //   order.ship_operator = rdr.getStrValue(kOrder_Taker, row, "");
                ////log.debug("Checking is_paid translation for value:" + rdr.getStrValue(kOrder_Paid, row, "nuthin"));
                // //log.debug("...translates to int value:" + rdr.getIntValue(kOrder_Paid, row, -1));
                order.is_paid = 1;
                // order.is_gift = "" + (rdr.getIntValue(kIs_Gift, row, 0) > 0 ? 1 : 0);
                // order.gift_message = rdr.getStrValue(kGift_Message, row, "");
                // order.cc_num = rdr.getStrValue(kCustomer_CC, row, "");
                // order.cc_exp_mon = rdr.getIntValue(kCC_Expiration_Month, row, 0);
                //order.cc_exp_year = rdr.getIntValue(kCC_Expiration_Year, row, 0);


                // order.po_num = rdr.getStrValue(kPONumber, row, "");


            }
            //product data collected for every row
            addLineItem(order, rdr.getStrValue(ProductSKU, row, ""),
                    rdr.getStrValue(ProductQuantity, row, ""),
                    rdr.getStrValue(UnitPrice, row, ""),
                    "0.00",
                    rdr.getStrValue(ProductName, row, ""),
                    "", "");
        }


        return order;
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
            if (!(getDataReader().getStrValue(37, row, "").trim().equals(""))) {
                if (orderRef.equals(getDataReader().getStrValue(37, row, orderRef))) {
                    //got the same order
                    //log.debug("DH found extra row");
                    OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                    //log.debug("DH adding item to order " + data.orderRef);
                } else {
                    //new order
                    //log.debug("DH found new row");
                    OrderData data = new OrderData();
                    orderRef = getDataReader().getStrValue(37, row, orderRef);
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

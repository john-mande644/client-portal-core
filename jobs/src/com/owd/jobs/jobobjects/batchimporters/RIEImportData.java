package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.business.order.Order;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 31, 2003
 * Time: 8:41:50 AM
 * To change this template use Options | File Templates.
 * <p/>
 * Note: used for all cart66 imports, not just RIE
 */
public class RIEImportData {
private final static Logger log =  LogManager.getLogger();


    int OrderID = 0;
    int OrderNumber = 1;
    int Date = 2;
    int BillingFirstName = 3;
    int BillingLastName = 4;
    int BillingAddress = 5;
    int BillingAddress2 = 6;
    int BillingCity = 7;
    int BillingState = 8;
    int BillingCountry = 9;
    int BillingZipCode = 10;
    int ShippingFirstName = 11;
    int ShippingLastName = 12;
    int ShippingAddress = 13;
    int ShippingAddress2 = 14;
    int ShippingCity = 15;
    int ShippingState = 16;
    int ShippingCountry = 17;
    int ShippingZipCode = 18;
    int Phone = 19;
    int Email = 20;
    int Coupon = 21;
    int DiscountAmount = 22;
    int ShippingCost = 23;
    int Subtotal = 24;
    int Tax = 25;
    int Total = 26;
    int IPAddress = 27;
    int DeliveryMethod = 28;
    int ItemNumber = 29;
    int Description = 30;
    int Quantity = 31;
    int ProductPrice = 32;
    int Shippable = 33;

    static Map<String, String> skuMap = new TreeMap<String, String>();

    static {
        skuMap.put("FRF-1", "689076230216");
        skuMap.put("VF-1", "689076230414");
        skuMap.put("SK100", "689076694858");
    }

    DelimitedReader dataReader = null;

    protected List orderPositionList = new ArrayList();

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

    public RIEImportData() {

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
        String shipMethod = "";

        boolean isBestWay = false;
        for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
            //log.debug("DH loading row " + row);
            if (row == data.rowIndexStart) {
                //first row
                order.order_refnum = rdr.getStrValue(OrderNumber, row, "") + "-" + rdr.getStrValue(OrderID, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }
                //response.add(order.order_refnum);
                //log.debug("DH loading ref " + order.order_refnum);
                log.debug(rdr.getRawRow(row));
                order.getBillingContact().setName(rdr.getStrValue(BillingFirstName, row, "").replaceAll("\\\\", "") + " " + rdr.getStrValue(BillingLastName, row, "").replaceAll("\\\\", ""));
                order.getBillingContact().email = rdr.getStrValue(Email, row, "");
                order.getBillingContact().phone = rdr.getStrValue(Phone, row, "");

                order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = rdr.getStrValue(BillingAddress, row, "").replaceAll("\\\\", "");
                order.getBillingAddress().address_two = rdr.getStrValue(BillingAddress2, row, "").replaceAll("\\\\", "");
                order.getBillingAddress().city = rdr.getStrValue(BillingCity, row, "").replaceAll("\\\\", "");
                order.getBillingAddress().state = rdr.getStrValue(BillingState, row, "");
                order.getBillingAddress().zip = rdr.getStrValue(BillingZipCode, row, "");
                order.getBillingAddress().country = rdr.getStrValue(BillingCountry, row, "");


                order.getShippingContact().setName(rdr.getStrValue(ShippingFirstName, row, "").replaceAll("\\\\", "") + " " + rdr.getStrValue(ShippingLastName, row, "").replaceAll("\\\\", ""));
                order.getShippingContact().email = rdr.getStrValue(Email, row, "");
                order.getShippingContact().phone = rdr.getStrValue(Phone, row, "");

                order.getShippingAddress().company_name = ".";

                order.getShippingAddress().address_one = rdr.getStrValue(ShippingAddress, row, "").replaceAll("\\\\", "");
                order.getShippingAddress().address_two = rdr.getStrValue(ShippingAddress2, row, "").replaceAll("\\\\", "");
                order.getShippingAddress().city = rdr.getStrValue(ShippingCity, row, "").replaceAll("\\\\", "");
                order.getShippingAddress().state = rdr.getStrValue(ShippingState, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(ShippingZipCode, row, "");
                order.getShippingAddress().country = rdr.getStrValue(ShippingCountry, row, "");

                if ("United States".equals(order.getBillingAddress().country)) {
                    order.getBillingAddress().country = "USA";
                }
                if ("United States".equals(order.getShippingAddress().country)) {
                    order.getShippingAddress().country = "USA";
                }

                order.getBillingAddress().country=order.getBillingAddress().country.replaceAll("US Virgin Islands","VIRGIN ISLANDS, U.S.");
                order.getShippingAddress().country=order.getShippingAddress().country.replaceAll("US Virgin Islands","VIRGIN ISLANDS, U.S.");

                if (!CountryNames.exists(order.getBillingAddress().country)) {
                    throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country);
                }
                if (!CountryNames.exists(order.getShippingAddress().country)) {
                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                }
                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                order.total_shipping_cost = rdr.getFloatValue(ShippingCost, row, 0.00f);
                order.total_tax_cost = rdr.getFloatValue(Tax, row, 0.00f);
                order.discount = -1 * Math.abs(rdr.getFloatValue(DiscountAmount, row, 0.00f));
                //order.getShippingInfo().comments = rdr.getStrValue(OrderComments, row, "");
                //order.getShippingInfo().whse_notes = rdr.getStrValue(kWarehouse_Notes, row, "");

                //  order.getShippingInfo().ss_saturday = rdr.getStrValue(kShip_Handling_Fee, row, "0").equals("1") ? "1" : "0";
                //  float cod_charge = OWDUtilities.roundFloat(rdr.getFloatValue(kCOD_Shipment, row, 0.00f));
                //  if (cod_charge > 0.00f) {
                //      order.getShippingInfo().cod_charge = "" + cod_charge;
                //      order.getShippingInfo().ss_cod = "1";
                //  }

                //  order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);


                shipMethod = rdr.getStrValue(DeliveryMethod, row, "");
                if (shipMethod.toUpperCase().contains("NEXT DAY")) {
                    shipMethod = "UPS Next Day Air Saver";
                } else if (shipMethod.toUpperCase().contains("SECOND DAY")) {
                    shipMethod = "UPS 2nd Day Air";
                } else if (shipMethod.toUpperCase().contains("UPS STANDARD")) {
                    shipMethod = "UPS Standard Canada";
                } else if (shipMethod.toUpperCase().contains("EXPRESS MAIL") && order.getShippingAddress().isInternational()) {
                    shipMethod = "USPS Priority Mail Express International";
                } else if (shipMethod.toUpperCase().contains("EXPRESS MAIL")) {
                    shipMethod = "USPS Priority Mail Express";
                } else if (shipMethod.toUpperCase().contains("PARCEL POST")) {
                    shipMethod = "USPS Parcel Select Nonpresort";
                } else if (shipMethod.toUpperCase().contains("THREE-DAY")) {
                    shipMethod = "UPS 3 Day Select";
                } else if (shipMethod.toUpperCase().contains("3 DAY")) {
                    shipMethod = "UPS 3 Day Select";
                } else if (shipMethod.toUpperCase().contains("GROUND")) {
                    shipMethod = "UPS Ground";
                } else if (shipMethod.toUpperCase().contains("EXPEDITED")) {
                    shipMethod = "UPS Worldwide Expedited";
                } else if (shipMethod.toUpperCase().contains("MEDIA MAIL")) {
                    shipMethod = "USPS Media Mail Single-Piece";
                } else if ((shipMethod.toUpperCase().contains("FIRST-CLASS") || shipMethod.toUpperCase().contains("FIRST CLASS")) && order.getShippingAddress().isUS()) {
                    shipMethod = "USPS First-Class Mail";
                } else if ((shipMethod.toUpperCase().contains("FIRST-CLASS") || shipMethod.toUpperCase().contains("FIRST CLASS"))) {
                    shipMethod = "USPS First-Class Mail International";
                } else if (shipMethod.toUpperCase().contains("PRIORITY") && order.getShippingAddress().isUS()) {
                    shipMethod = "USPS Priority Mail";
                } else if (shipMethod.toUpperCase().contains("PRIORITY")) {
                    shipMethod = "USPS Priority Mail International";
                } else if (shipMethod.toUpperCase().contains("ECONOMY")) {
                    isBestWay = true;
                } else {
                    isBestWay = true;
                }

                //bill to client account
                order.processCouponDiscount = false;
                order.coupon_code = rdr.getStrValue(Coupon, row, "");

                order.order_type = "Cart66 Download";
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
            addLineItem(order, skuMap.containsKey(rdr.getStrValue(ItemNumber, row, "").replaceAll("\\\\", "")) ? skuMap.get(rdr.getStrValue(ItemNumber, row, "").replaceAll("\\\\", "")) : rdr.getStrValue(ItemNumber, row, "").replaceAll("\\\\", ""),
                    "" + (new Double(rdr.getStrValue(Quantity, row, "0"))).intValue(),
                    rdr.getStrValue(ProductPrice, row, ""),
                    "0.00",
                    rdr.getStrValue(Description, row, "").replaceAll("\\\\", ""),
                    "", "");
        }

        if (!(order.orderRefnumExists(order.order_refnum))) {
            if (isBestWay) {
                List<String> alternateShipMethodList = new ArrayList<String>();
                alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.STD");
                alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.GND");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");

                shipMethod = RateShopper.rateShop(order, alternateShipMethodList);
            }
            order.getShippingInfo().setShipOptions(shipMethod, "Prepaid", "");

        } else {
            order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
        }
        return order;
    }


    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size) throws Exception {
        //log.debug("adding li:" + sku + "," + qty + "," + unitPrice + "," +
        //       linePrice + "," +
        //       desc + "," +
        //       color + "," + size);


        order.addLineItem(sku,
                qty,
                unitPrice,
                linePrice,
                desc,
                color, size);

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
                    log.debug("Row " + row + " Col " + col + " ::" + getDataReader().getStrValue(col, row, "**NADA**"));
                }

            }
        } catch (Exception exxx) {
            exxx.printStackTrace();
        }


        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            //log.debug("DH handling row " + row);
            if (!(getDataReader().getStrValue(OrderNumber, row, "").trim().equals(""))) {
                if (orderRef.equals(getDataReader().getStrValue(OrderNumber, row, orderRef))) {
                    //got the same order
                    //log.debug("DH found extra row");
                    OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                    //log.debug("DH adding item to order " + data.orderRef);
                } else {
                    //new order
                    //log.debug("DH found new row");
                    OrderData data = new OrderData();
                    orderRef = getDataReader().getStrValue(OrderNumber, row, orderRef);
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

package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 2/27/14
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class SkymallOrdersData {
private final static Logger log =  LogManager.getLogger();

    protected List orderPositionList = new ArrayList();

    static public int korder_id = 0;
      static public int korder_source = 1;
      static public int korder_date = 2;
      static public int ksales_tax = 3;
      static public int kord_ship_charge = 4;
      static public int ktotal_amount = 5;
      static public int kbill_to_name = 6;
      static public int kbill_to_address1 = 7;
      static public int kbill_to_address2 = 8;
      static public int kbill_to_city = 9;
      static public int kbill_to_state = 10;
      static public int kbill_to_zip = 11;
      static public int kbill_to_phone = 12;
      static public int kreference1 = 13;
      static public int kdo_not_promote = 14;
      static public int kcredit_card_no = 15;
      static public int kexpiry_date = 16;
      static public int kauth_code = 17;
      static public int kauth_date = 18;
      static public int kvendor_code = 19;
      static public int kline_item_numer = 20;
      static public int ksku = 21;
      static public int kitem_description = 22;
      static public int kpresonalization = 23;
      static public int kSpec1 = 24;
      static public int kSpec2 = 25;
      static public int kSpec3 = 26;
      static public int kretail_price = 27;
      static public int kitem_tax = 28;
      static public int kshipping_charge = 29;
      static public int kquantity = 30;
      static public int kship_method = 31;
      static public int kship_to_name = 32;
      static public int kship_to_address1 = 33;
      static public int kship_to_address2 = 34;
      static public int kship_to_city = 35;
      static public int kship_to_state = 36;
      static public int kship_to_zip = 37;
      static public int kship_to_phone = 38;
      static public int kReference2 = 39;

    public static final String Order_Type = "SKYMALL";

    DelimitedReader dataReader = null;

    public SkymallOrdersData() {

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
                order.order_refnum = "SKY" + rdr.getStrValue(korder_id, row, "");

                if (order.order_refnum.length() < 4) {
                    throw new Exception("Client order reference was invalid or not found.");
                }

                order.getBillingContact().setName((rdr.getStrValue(kbill_to_name, row, "")).trim());

                order.getBillingContact().phone = rdr.getStrValue(kbill_to_phone, row, "");
                order.getBillingAddress().company_name = ".";

                order.getShippingContact().setName(rdr.getStrValue(kship_to_name, row, ""));
                order.getShippingContact().email = "";
                order.getShippingContact().phone = rdr.getStrValue(kship_to_phone, row, "");

                order.getShippingAddress().company_name = ".";


                order.getShippingAddress().address_one = rdr.getStrValue(kship_to_address1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(kship_to_address2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(kship_to_city, row, "");
                order.getShippingAddress().state = rdr.getStrValue(kship_to_state, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(kship_to_zip, row, "");

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

         //       order.total_shipping_cost = rdr.getFloatValue(kord_ship_charge, row, 0.00f);
         //       order.total_tax_cost = rdr.getFloatValue(ksales_tax, row, 0.00f);
                order.getShippingInfo().carr_service = rdr.getStrValue(kship_method, row, "");

                order.order_type = SkymallOrdersData.Order_Type;
            }

            order.total_shipping_cost +=  rdr.getFloatValue(kshipping_charge, row, 0.00f);
            order.total_tax_cost +=  rdr.getFloatValue(kitem_tax, row, 0.00f);
            //product data collected for every row
            addLineItem(order, (rdr.getStrValue(ksku, row, "").equals("EYEPUTTERF14")?"EYEPUTT":rdr.getStrValue(ksku, row, "")),
                    rdr.getStrValue(kquantity, row, ""),
                    rdr.getStrValue(kretail_price, row, ""),
                    "0.00",
                    rdr.getStrValue(kitem_description, row, ""),
                                        "", "", "");

            ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = rdr.getStrValue(kline_item_numer, row, "");;
        }
        return order;
    }

    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size, String externalLineReference) throws Exception {
        //log.debug("adding li:"+sku+","+qty+","+unitPrice+","+
        //           linePrice+","+
        //           desc+","+
        //           color+","+ size);

        if (new Float(unitPrice).floatValue() < 0.00f) {
            order.discount = order.discount + new Float(unitPrice).floatValue();
        } else {
            try {
                order.addLineItem(sku,
                        qty,
                        unitPrice,
                        linePrice,
                        desc,
                        color, size);
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = externalLineReference;
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    public int getOrderCount() {
        return getOrderPositionList().size();
    }

    protected void processReader() {
        String orderRef = "thisisaninvalidorderreference";

        if (getDataReader() == null) {
            return;
        }

        try {
            for (int row = 0; row < getDataReader().getRowCount(); row++) {
                for (int col = 0; col < getDataReader().getRowSize(row); col++) {
                    log.debug("Row "+row+" Col "+col+" ::"+getDataReader().getStrValue(col, row, "**NADA**"));
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

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";
    }
}

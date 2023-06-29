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
public class RakutenUploadOrdersData {
private final static Logger log =  LogManager.getLogger();

    protected List orderPositionList = new ArrayList();

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

    public static final String Order_Type = "Rakuten";

    DelimitedReader dataReader = null;

    public RakutenUploadOrdersData() {

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

                order.order_type = RakutenUploadOrdersData.Order_Type;
            }
            String sku = rdr.getStrValue(kReference_Id, row, "").startsWith("SKU") ? rdr.getStrValue(kReference_Id, row, "").substring(3) : rdr.getStrValue(kReference_Id, row, "");

            //product data collected for every row
            addLineItem(order, sku,
                    rdr.getStrValue(kQty, row, ""),
                    rdr.getStrValue(kPrice, row, ""),
                    "0.00",
                    rdr.getStrValue(kItem_Name, row, ""),
//                    "", "", rdr.getStrValue(kProductNotes, row, ""));
                                        "", "", "");

            ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = rdr.getStrValue(kReceipt_Item_Id, row, "");;
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

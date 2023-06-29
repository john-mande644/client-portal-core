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
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 31, 2003
 * Time: 8:41:50 AM
 * To change this template use Options | File Templates.
 */
public class AlexBlakeUploadOrdersData {
private final static Logger log =  LogManager.getLogger();

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
  //  int kComments = 29;
    int kWarehouse_Notes = 29;
    int kOrder_Source = 30;
    int kOrder_Taker = 31;
    int kSales_Tax = 32;
    int kShip_Handling_Fee = 33;
    int kOrder_Discount = 34;
    int kProduct_SKU = 35;
    int kProduct_Quantity = 36;
    int kProduct_Unit_Price = 37;
    int kProduct_Description = 38;
    int kCOD_Shipment = 39;     //v1.1
    int kSaturdayDelivery = 40;
    int kShipBillingMethod = 41;
    int kBilltoShip_Name = 42;
    int kBilltoShip_Company = 43;
    int kBilltoShip_Address_1 = 44;
    int kBilltoShip_Address_2 = 45;
    int kBilltoShip_City = 46;
    int kBilltoShip_State = 47;
    int kBilltoShip_Postal_Code = 48;
    //int kBilltoShip_Country = 50;
    int kBilltoShip_Phone = 49;
    int kBilltoShip_Account = 50;
    int kPONumber = 51;
    int kInsurance = 52;
    int kSigRequired = 53;
    int kProductNotes = 54;
    int kLineTotal = 55;

    DelimitedReader dataReader = null;

    protected List orderPositionList = new ArrayList();

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

    public AlexBlakeUploadOrdersData() {

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
                order.order_refnum = rdr.getStrValue(kOrder_Reference, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }
                //response.add(order.order_refnum);
                //log.debug("DH loading ref " + order.order_refnum);

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
                if(order.getShippingAddress().state.equalsIgnoreCase("PUERTO RICO"))
                {
                    order.getShippingAddress().state="PR";
                    order.getShippingAddress().country="PUERTO RICO";
                }
                if (!CountryNames.exists(order.getBillingAddress().country)) {
                    throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country);
                }
                if (!CountryNames.exists(order.getShippingAddress().country)) {
                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                }
                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                log.debug(rdr.getStrValue(kShip_Handling_Fee, row, "x"));

                log.debug(rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f));
                order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);
                order.total_tax_cost = rdr.getFloatValue(kSales_Tax, row, 0.00f);
                order.discount = -1 * Math.abs(rdr.getFloatValue(kOrder_Discount, row, 0.00f));
              //  order.getShippingInfo().comments = rdr.getStrValue(kComments, row, "");
                order.getShippingInfo().whse_notes = rdr.getStrValue(kWarehouse_Notes, row, "");

                order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);




                    //log.debug("Shipping 3rd Party");
                order.getShippingInfo().carr_service = rdr.getStrValue(kShip_Method, row, "");





                order.order_type = rdr.getStrValue(kOrder_Source, row, "AlexBlake FTP Import");
                order.ship_operator = rdr.getStrValue(kOrder_Taker, row, "");
                    //log.debug("Checking is_paid translation for value:"+rdr.getStrValue(kOrder_Paid, row, "nuthin"));
                //log.debug("...translates to int value:"+rdr.getIntValue(kOrder_Paid, row, -1));
                order.is_paid = rdr.getIntValue(kOrder_Paid, row, 0) > 0 ? 1 : 0;
                order.is_gift = "" + (rdr.getIntValue(kIs_Gift, row, 0) > 0 ? 1 : 0);
                order.gift_message = rdr.getStrValue(kGift_Message, row, "");
                order.cc_num = rdr.getStrValue(kCustomer_CC, row, "");
                order.cc_exp_mon = rdr.getIntValue(kCC_Expiration_Month, row, 0);
                order.cc_exp_year = rdr.getIntValue(kCC_Expiration_Year, row, 0);


                order.po_num = rdr.getStrValue(kPONumber, row, "");


            }
            String sku = rdr.getStrValue(kProduct_SKU, row, "").startsWith("SKU")?rdr.getStrValue(kProduct_SKU, row, "").substring(3):rdr.getStrValue(kProduct_SKU, row, "");
            if(sku.length()<12)
            {
                sku = "0"+sku;
            }

            //product data collected for every row
            addLineItem(order,sku,
                    rdr.getStrValue(kProduct_Quantity, row, ""),
                    rdr.getStrValue(kProduct_Unit_Price, row, ""),
                    "0.00",
                    rdr.getStrValue(kProduct_Description, row, ""),
                    "", "",rdr.getStrValue(kProductNotes, row, ""));



        }


        return order;
    }



    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice,String desc, String color, String size, String externalLineReference) throws Exception
    {
         log.debug("adding li:"+sku+","+qty+","+unitPrice+","+
                    linePrice+","+
                    desc+","+
                    color+","+ size);

        if(new Float(unitPrice).floatValue()<0.00f)
        {
             order.discount = order.discount+new Float(unitPrice).floatValue();
        }   else
        {

            try{


        order.addLineItem(sku,
                    qty,
                    unitPrice,
                    linePrice,
                    desc,
                    color, size);
            ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = externalLineReference;
            }catch(Exception ex)
            {
               if(sku.startsWith("0"))
               {
                   try{


                       order.addLineItem(sku.substring(1),
                               qty,
                               unitPrice,
                               linePrice,
                               desc,
                               color, size);
                       ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = externalLineReference;
                   }catch(Exception exx)
                   {
                       if(sku.startsWith("00"))
                       {

                           order.addLineItem(sku.substring(2),
                                   qty,
                                   unitPrice,
                                   linePrice,
                                   desc,
                                   color, size);
                           ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = externalLineReference;
                       }   else
                       {
                           throw ex;
                       }
                   }
               }   else
               {
                   throw ex;
               }
            }
        }
    }

    public int getOrderCount() {
        return getOrderPositionList().size();
    }

    protected void processReader() {

        String orderRef = "thisisaninvalidorderreference";
        if (getDataReader() == null) return;

        try
        {
         for (int row = 0; row < getDataReader().getRowCount(); row++) {

           for (int col = 0;col< getDataReader().getRowSize(row); col++) {
              //log.debug("Row "+row+" Col "+col+" ::"+getDataReader().getStrValue(col, row, "**NADA**"));
           }

           }
        }catch(Exception exxx)
        {
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

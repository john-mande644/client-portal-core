package com.owd.web.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.business.order.Order;

import java.util.ArrayList;
import java.util.List;

//import com.owd.core.business.order.clients.NGLUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 23, 2007
 * Time: 11:42:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class NGLUploadOrdersData {
private final static Logger log =  LogManager.getLogger();

    int kOrder_Reference = 0;
    int kCustomer_Name = 2;
    int kCustomer_last_name= 3;
    int kCustomer_Company = 4;
    int kCustomer_Address_1 = 5;
    int kCustomer_Address_2 = 6;
    int kCustomer_City = 7;
    int kCustomer_State = 8;
    int kCustomer_Postal_Code = 9;
    int kCustomer_Country = 10;
    int kCustomer_Phone = 11;
    int kCustomer_WPhone = 12;
    int kCustomer_Email = 1;
    int kOrder_Paid = 80;
    //int kCustomer_CC = 19;
   // int kCC_Expiration_Month = 13;
   // int kCC_Expiration_Year = 14;
    int kShip_Name = 42;
    //int kShip_Company = 42;
    int kShip_Address_1 = 43;
    int kShip_Address_2 = 44;
    int kShip_City = 45;
    int kShip_State = 46;
    int kShip_Postal_Code = 47;
    int kShip_Country = 48;
    int kShip_Phone = 11;
    int kShip_Email = 1;
    int kShip_Method = 49;
   // int kShip_Account = 81;
   // int kIs_Gift = 27;
   // int kGift_Message = 28;
   // int kComments = 29;
  //  int kWarehouse_Notes = 30;
    int kOrder_Source = 64;
    //int kOrder_Taker = 32;
    int kSales_Tax = 51;
    int kShip_Handling_Fee = 50;
    int kOrder_Discount = 31;
    int kProduct_SKU = 27;
    int kProduct_Quantity =29;
    int kProduct_Unit_Price = 28;
    int kProduct_Description = 23;
    //int kCOD_Shipment = 40;     //v1.1
   // int kSaturdayDelivery = 41;
   // int kShipBillingMethod = 42;
   // int kBilltoShip_Name = 43;
   // int kBilltoShip_Company = 44;
   // int kBilltoShip_Address_1 = 45;
   // int kBilltoShip_Address_2 = 46;
  //  int kBilltoShip_City = 47;
  //  int kBilltoShip_State = 48;
   // int kBilltoShip_Postal_Code = 49;
   // int kBilltoShip_Country = 50;
   // int kBilltoShip_Phone = 51;
   // int kBilltoShip_Account = 52;
    //int kPONumber = 53;
   // int kDeclared_Value = 54;
   // int kSignatureRequired = 55;

    DelimitedReader dataReader = null;

    protected List orderPositionList = new ArrayList();

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

    public NGLUploadOrdersData() {

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
            log.debug("DH loading row " + row);
            if (row == data.rowIndexStart) {
                //first row
                order.order_refnum = rdr.getStrValue(kOrder_Reference, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }
                //response.add(order.order_refnum);
                log.debug("DH loading ref " + order.order_refnum);

                order.getBillingContact().setName(rdr.getStrValue(kCustomer_Name, row, "")+" "+rdr.getStrValue(kCustomer_last_name,row,""));
                order.getBillingContact().email = rdr.getStrValue(kCustomer_Email, row, "");
                String phone = rdr.getStrValue(kCustomer_Phone, row, "");
                if (phone.equals("")) phone = rdr.getStrValue(kCustomer_WPhone, row, "");
                order.getBillingContact().phone = phone;

                order.getBillingAddress().company_name = rdr.getStrValue(kCustomer_Company, row, ".");
                if (order.getBillingAddress().company_name.trim().length() < 1) order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = rdr.getStrValue(kCustomer_Address_1, row, "");
                order.getBillingAddress().address_two = rdr.getStrValue(kCustomer_Address_2, row, "");
                order.getBillingAddress().city = rdr.getStrValue(kCustomer_City, row, "");
                order.getBillingAddress().state = rdr.getStrValue(kCustomer_State, row, "");
                order.getBillingAddress().zip = rdr.getStrValue(kCustomer_Postal_Code, row, "");
                order.getBillingAddress().country = rdr.getStrValue(kCustomer_Country, row, "");
                if(order.getBillingAddress().country.equals("Virgin Islands U.S.")){
                    order.getBillingAddress().country="USA";
                    order.getBillingAddress().state="VI";
                }

                order.getShippingContact().setName(rdr.getStrValue(kShip_Name, row, ""));
                order.getShippingContact().email = rdr.getStrValue(kShip_Email, row, "");
                order.getShippingContact().phone = rdr.getStrValue(kShip_Phone, row, "");

                order.getShippingAddress().company_name = ".";
                if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                order.getShippingAddress().address_one = rdr.getStrValue(kShip_Address_1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(kShip_Address_2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(kShip_City, row, "");
                order.getShippingAddress().state = rdr.getStrValue(kShip_State, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(kShip_Postal_Code, row, "");
                order.getShippingAddress().country = rdr.getStrValue(kShip_Country, row, "");
                if(order.getShippingAddress().country.equals("Virgin Islands U.S.")){
                    order.getShippingAddress().country="USA";
                    order.getShippingAddress().state="VI";
                }

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
              //  order.getShippingInfo().comments = rdr.getStrValue(kComments, row, "");
               // order.getShippingInfo().whse_notes = rdr.getStrValue(kWarehouse_Notes, row, "");

                order.getShippingInfo().ss_saturday = rdr.getStrValue(kShip_Handling_Fee, row, "0").equals("1") ? "1" : "0";
              /*  float cod_charge = OWDUtilities.roundFloat(rdr.getFloatValue(kCOD_Shipment, row, 0.00f));
                if (cod_charge > 0.00f) {
                    order.getShippingInfo().cod_charge = "" + cod_charge;
                    order.getShippingInfo().ss_cod = "1";
                }*/

                order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);
               /* log.debug(rdr.getFloatValue(kDeclared_Value, row, 0.00f));
                if(rdr.getFloatValue(kDeclared_Value, row, 0.00f) > 0.00){
                    log.debug("in declared");
                    order.getShippingInfo().ss_declared_value = "1";
                    order.getShippingInfo().declared_value = rdr.getStrValue(kDeclared_Value, row, "0");
                }*/

                //bill to com.owd.api.client account
               /* if (rdr.getStrValue(kShip_Account, row, "").length() > 0) {
                    order.getShippingInfo().setShipOptions(rdr.getStrValue(kShip_Method, row, ""), "Third Party Billing", rdr.getStrValue(kShip_Account, row, ""));
                }*/

                      log.debug("Checking shipping for ship method "+rdr.getStrValue(kShip_Method, row, ""));
                   String shipmethod = "USPS Priority Mail";
                if("USPS Postal Mail".equals(rdr.getStrValue(kShip_Method, row,""))){
                    shipmethod = "USPS First-Class Mail";
                }

              if(order.getShippingAddress().city.equals("APO")){
                  order.getShippingAddress().country = "USA";
                  
              }

                
               if(!order.getShippingAddress().country.equals("USA")){
                shipmethod = "USPS First-Class Mail International";
               }
                //bill to non-OWD, non-com.owd.api.client account

                    order.getShippingInfo().setShipOptions(shipmethod, "Prepaid", "");




                order.order_type = rdr.getStrValue(kOrder_Source, row, "OWD General Order Upload");
              //  order.ship_operator = rdr.getStrValue(kOrder_Taker, row, "");
                    log.debug("Checking is_paid translation for value:"+rdr.getStrValue(kOrder_Paid, row, "nuthin"));
                log.debug("...translates to int value:"+rdr.getIntValue(kOrder_Paid, row, -1));
                order.is_paid = rdr.getIntValue(kOrder_Paid, row, 0) > 0 ? 1 : 0;
             /*   order.is_gift = "" + (rdr.getIntValue(kIs_Gift, row, 0) > 0 ? 1 : 0);
                order.gift_message = rdr.getStrValue(kGift_Message, row, "");
                order.cc_num = rdr.getStrValue(kCustomer_CC, row, "");
                order.cc_exp_mon = rdr.getIntValue(kCC_Expiration_Month, row, 0);
                order.cc_exp_year = rdr.getIntValue(kCC_Expiration_Year, row, 0);


                order.po_num = rdr.getStrValue(kPONumber, row, "");


                order.getShippingInfo().ss_proof_delivery = rdr.getStrValue(kSignatureRequired, row, "0");*/
                try
                {
                    Integer testint = new Integer(order.getShippingInfo().ss_proof_delivery);
                } catch (Exception exnint)
                {
                    order.getShippingInfo().ss_proof_delivery = "0";
                }

            }
            //product data collected for every row

            //specail rule for sku based on price
            if(rdr.getStrValue(kProduct_SKU, row, "").equalsIgnoreCase("Cat. 3 Ropex/n2u 10%")){
               if(rdr.getFloatValue(kProduct_Unit_Price,row,0)>72.00f){
                  addLineItem(order,"CAT3FIRST",
                    rdr.getStrValue(kProduct_Quantity, row, ""),
                    rdr.getStrValue(kProduct_Unit_Price, row, ""),
                    "0.00",
                    rdr.getStrValue(kProduct_Description, row, ""),
                    "", "");

               }   else{
                    addLineItem(order,"CAT3REPEAT",
                    rdr.getStrValue(kProduct_Quantity, row, ""),
                    rdr.getStrValue(kProduct_Unit_Price, row, ""),
                    "0.00",
                    rdr.getStrValue(kProduct_Description, row, ""),
                    "", "");

               }


            }else{

            addLineItem(order,rdr.getStrValue(kProduct_SKU, row, ""),
                    rdr.getStrValue(kProduct_Quantity, row, ""),
                    rdr.getStrValue(kProduct_Unit_Price, row, ""),
                    "0.00",
                    rdr.getStrValue(kProduct_Description, row, ""),
                    "", "");
            }
        }


        return order;
    }



    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice,String desc, String color, String size) throws Exception
    {
         log.debug("adding li:"+sku+","+qty+","+unitPrice+","+
                    linePrice+","+
                    desc+","+
                    color+","+ size);
        if(new Float("0"+unitPrice).floatValue()<0.00f)
        {
             order.discount = order.discount+new Float("0"+unitPrice).floatValue();
        }   else
        {

         //NGLUtilities.getInstance().addItem(order, sku, qty, unitPrice, linePrice, desc, "", "");
        /*order.addLineItem(sku,
                    qty,
                    unitPrice,
                    linePrice,
                    desc,
                    color, size);*/
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
              log.debug("Row "+row+" Col "+col+" ::"+getDataReader().getStrValue(col, row, "**NADA**"));
           }

           }
        }catch(Exception exxx)
        {
            exxx.printStackTrace();
        }


        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            log.debug("DH handling row " + row);
            if (!(getDataReader().getStrValue(0, row, "").trim().equals(""))) {
                if (orderRef.equals(getDataReader().getStrValue(0, row, orderRef))) {
                    //got the same order
                    log.debug("DH found extra row");
                    OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                    log.debug("DH adding item to order " + data.orderRef);
                } else {
                    //new order
                    log.debug("DH found new row");
                    OrderData data = new OrderData();
                    orderRef = getDataReader().getStrValue(0, row, orderRef);
                    data.orderRef = orderRef;
                    data.rowIndexStart = row;
                    data.rowsForOrder = 1;
                    getOrderPositionList().add(data);
                    log.debug("DH added entry for " + orderRef + ", row " + row);
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

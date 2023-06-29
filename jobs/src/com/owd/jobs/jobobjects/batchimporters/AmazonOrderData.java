package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.jobs.jobobjects.amazon.AmazonAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Feb 15, 2008
 * Time: 9:53:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmazonOrderData {
private final static Logger log =  LogManager.getLogger();
    int orderid=0;
    int orderitemid=1;
    int purchasedate=2;
    int paymentsdate=3;
    int reportingdate=4;
    int promisedate=5;
    int dayspastpromise=6;
    int buyeremail=7;
    int buyername=8;
    int buyerphonenumber=9;
    int sku=10;
    int productname=11;
    int quantitypurchased=12;
    int quantityshipped=13;
    int quantitytoship=14;
    int shipservicelevel=15;
    int recipientname=16;
    int shipaddress1=17;
    int shipaddress2=18;
    int shipaddress3=19;
    int shipcity=20;
    int shipstate=21;
    int shippostalcode=22;
    int shipcountry=23;
    DelimitedReader dataReader = null;

    Map<String, String> existingOrdersMap = new HashMap<String,String>();



    AmazonAPI api;
    protected List orderPositionList = new ArrayList();

    public class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

    public AmazonAPI getApi() {
        return api;
    }

    public void setApi(AmazonAPI api) {
        this.api = api;
    }

    public Map<String, String> getExistingOrdersMap() {
        return existingOrdersMap;
    }

    public void setExistingOrdersMap(Map<String, String> existingOrdersMap) {
        this.existingOrdersMap = existingOrdersMap;
    }
    private void checkClientIdAndAdjustFields(){
        if(api.clientId == 536){
             recipientname=17;
             shipaddress1=18;
             shipaddress2=19;
             shipaddress3=20;
             shipcity=21;
             shipstate=22;
             shippostalcode=23;
             shipcountry=24; 
        }
    }
    public AmazonOrderData(AmazonAPI api) {
        setApi(api);
        checkClientIdAndAdjustFields();
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
        for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
            //log.debug("DH loading row " + row);
            if (row == data.rowIndexStart) {
                //first row
                order.order_refnum = rdr.getStrValue(orderid, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }
                log.debug("Shipping:"+rdr.getStrValue(shipservicelevel, row, ""));

                //response.add(order.order_refnum);
                //log.debug("DH loading ref " + order.order_refnum);

                order.getBillingContact().setName(rdr.getStrValue(buyername, row, ""));
                order.getBillingContact().email = "";//rdr.getStrValue(buyeremail, row, "");
                order.getBillingContact().phone = rdr.getStrValue(buyerphonenumber, row, "");

                order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = rdr.getStrValue(shipaddress1, row, "");
                order.getBillingAddress().address_two = rdr.getStrValue(shipaddress2, row, "");
                order.getBillingAddress().city = rdr.getStrValue(shipcity, row, "");
                order.getBillingAddress().state = rdr.getStrValue(shipstate, row, "");
                order.getBillingAddress().zip = rdr.getStrValue(shippostalcode, row, "");
                order.getBillingAddress().country = rdr.getStrValue(shipcountry, row, "");


                order.getShippingContact().setName(rdr.getStrValue(recipientname, row, ""));
                order.getShippingContact().email = "";//rdr.getStrValue(buyeremail, row, "");
                order.getShippingContact().phone = "";

                order.getShippingAddress().company_name = ( ".");

                order.getShippingAddress().address_one = rdr.getStrValue(shipaddress1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(shipaddress2, row, "");
                if(order.getShippingAddress().address_one.length()<1) {
                    order.getShippingAddress().address_one = order.getShippingAddress().address_two;
                    order.getShippingAddress().address_two = "";
                }
                order.getShippingAddress().city = rdr.getStrValue(shipcity, row, "");
                order.getShippingAddress().state = rdr.getStrValue(shipstate, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(shippostalcode, row, "");
                order.getShippingAddress().country = rdr.getStrValue(shipcountry, row, "");


                if (!CountryNames.exists(order.getBillingAddress().country)) {
                    throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country);
                }
                if (!CountryNames.exists(order.getShippingAddress().country)) {
                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                }
                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);

                order.total_shipping_cost = (0.00f);
                order.total_tax_cost =  0.00f;
                order.discount =  0.00f;
                //order.getShippingInfo().comments = rdr.getStrValue(kComments, row, "");
               // order.getShippingInfo().whse_notes = rdr.getStrValue(kWarehouse_Notes, row, "");



                //bill to client account
             /*   if (rdr.getStrValue(kShip_Account, row, "").length() > 0) {
                    order.getShippingInfo().setShipOptions(translateShipMethod(rdr.getStrValue(kShip_Method, row, "")), "Third Party Billing", rdr.getStrValue(kShip_Account, row, ""));
                }
*/
                      //log.debug("Checking shipping for ship method "+rdr.getStrValue(kShip_Method, row, ""));
                //bill to non-OWD, non-client account
              /*  if (("Consignee".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                    //log.debug("Shipping Consignee");
                    order.getShippingInfo().setShipOptions(translateShipMethod(rdr.getStrValue(kShip_Method, row, "")), "Freight Collect", "");

                } else if (("Freight Collect".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                    //log.debug("Shipping Freight Collect : " + rdr.getStrValue(kBilltoShip_Account, row, ""));
                    order.getShippingInfo().setShipOptions(translateShipMethod(rdr.getStrValue(kShip_Method, row, "")), "Freight Collect", rdr.getStrValue(kBilltoShip_Account, row, ""));
                    //order.getShippingInfo().shipperAddress

                } else if (("Prepaid".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, ""))) || ("".equalsIgnoreCase(rdr.getStrValue(kShipBillingMethod, row, "")))) {
                    //log.debug("Shipping Normal");
                    order.getShippingInfo().setShipOptions(translateShipMethod(rdr.getStrValue(kShip_Method, row, rdr.getStrValue(kBilltoShip_Account, row, ""))), "Prepaid", "");

                } else {
                    //log.debug("Shipping 3rd Party");
                    order.getShippingInfo().setShipOptions(translateShipMethod(rdr.getStrValue(kShip_Method, row, "")), "Prepaid", rdr.getStrValue(kBilltoShip_Account, row, ""));

                    order.getShippingInfo().shipperAddress = new Address(rdr.getStrValue(kBilltoShip_Company, row, ""),
                            rdr.getStrValue(kBilltoShip_Address_1, row, ""),
                            rdr.getStrValue(kBilltoShip_Address_2, row, ""),
                            rdr.getStrValue(kBilltoShip_City, row, ""),
                            rdr.getStrValue(kBilltoShip_State, row, ""),
                            rdr.getStrValue(kBilltoShip_Postal_Code, row, ""),
                            CountryNames.getCountryName(rdr.getStrValue(kBilltoShip_Country, row, "US")));

                    order.getShippingInfo().shipperContact = new Contact(rdr.getStrValue(kBilltoShip_Name, row, ""),
                            rdr.getStrValue(kBilltoShip_Phone, row, "605-845-7172"), "", "", "");

                }*/
                 //todo setup shipping method
                order.order_type = api.getSource();
                //order.ship_operator = rdr.getStrValue(kOrder_Taker, row, "");
                    //log.debug("Checking is_paid translation for value:"+rdr.getStrValue(kOrder_Paid, row, "nuthin"));
                //log.debug("...translates to int value:"+rdr.getIntValue(kOrder_Paid, row, -1));
                order.is_paid = 1;
                order.is_gift = "0" ;
                //order.gift_message = rdr.getStrValue(kGift_Message, row, "");
                //order.cc_num = rdr.getStrValue(kCustomer_CC, row, "");
                //order.cc_exp_mon = rdr.getIntValue(kCC_Expiration_Month, row, 0);
               // order.cc_exp_year = rdr.getIntValue(kCC_Expiration_Year, row, 0);


               order.getShippingInfo().carr_service = rdr.getStrValue(shipservicelevel, row, "");
               // order.po_num = rdr.getStrValue(kPONumber, row, "");


            }

            //product data collected for every row
            boolean isOwdItem = api.addLineItem(order,rdr.getStrValue(sku, row, ""),
                    rdr.getStrValue(quantitypurchased, row, ""),
                    "0.00",
                    "0.00",
                    rdr.getStrValue(productname, row, ""),
                    "", "");

                    String itemRef = rdr.getStrValue(orderitemid, row, "");
            if(itemRef.length()>0 && isOwdItem)
            {
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num="AMAZ:"+itemRef;

            }

        }


        return order;
    }



    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice,String desc, String color, String size) throws Exception
    {
         //log.debug("adding li:"+sku+","+qty+","+unitPrice+","+
         //           linePrice+","+
         //           desc+","+
         //           color+","+ size);


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

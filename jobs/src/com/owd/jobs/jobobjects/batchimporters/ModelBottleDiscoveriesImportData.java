package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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
public class ModelBottleDiscoveriesImportData {
private final static Logger log =  LogManager.getLogger();

    int OrderReference = 0;
    int OrderSource = 2;

    int CCAcct = 20;
    int CCExp = 21;

    int BillingFirstName = 4;
    int BillingLastName = 3;
    int BillingCompany = 5;
    int BillingAddress1 = 6;
    int BillingAddress2 = 7;
    int BillingCity = 8;
    int BillingState = 9;
    int BillingZip = 10;
    int BillingEmail = 91;
    int BillingPhone1 = 12;
    int ShippingFirstName = 67;
    int ShippingLastName = 66;
    int ShippingCompany = 68;
    int ShippingAddress1 = 69;
    int ShippingAddress2 = 70;
    int ShippingCity = 71;
    int ShippingState = 72;
    int ShippingZip = 73;
    int ShippingPhone1 = 96;

    int ProductNameStart = 36;
    int ProductQuantityStart = 37;
    
    int ProductUnitPriceStart = 79;
    int ProductUnitDiscountStart = 80;

    int ProductMaxCount = 5;

    int ShippingandHandling = 90;



    DelimitedReader dataReader = null;

    protected List orderPositionList = new ArrayList();

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

    public ModelBottleDiscoveriesImportData() {

    }

    public void init(DelimitedReader rdr) {
        if (rdr == null) return;

        setDataReader(rdr);
        processReader();


    }


    public String getOrderReference(int orderIndex) {
        return ((ModelBottleDiscoveriesImportData.OrderData) getOrderPositionList().get(orderIndex)).orderRef;
    }

    public Order loadOrder(Order order, int orderIndex) throws Exception {
        //log.debug("in DH loadOrder");
        if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
            throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());

        ModelBottleDiscoveriesImportData.OrderData data = (ModelBottleDiscoveriesImportData.OrderData) getOrderPositionList().get(orderIndex);
        DelimitedReader rdr = getDataReader();

        for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
            //log.debug("DH loading row " + row);
            if (row == data.rowIndexStart) {
                //first row
                order.order_refnum = rdr.getStrValue(OrderReference, row, "");
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
                order.getBillingAddress().country = "USA";

                if((rdr.getStrValue(ShippingFirstName, row, "") + " " + rdr.getStrValue(ShippingLastName, row, "")).trim().length()>0)
                {
                order.getShippingContact().setName(rdr.getStrValue(ShippingFirstName, row, "") + " " + rdr.getStrValue(ShippingLastName, row, ""));
                order.getShippingContact().email = order.getBillingContact().email;
                order.getShippingContact().phone = rdr.getStrValue(ShippingPhone1, row, "");

                order.getShippingAddress().company_name = rdr.getStrValue(ShippingCompany, row, ".");
                if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                order.getShippingAddress().address_one = rdr.getStrValue(ShippingAddress1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(ShippingAddress2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(ShippingCity, row, "");
                order.getShippingAddress().state = rdr.getStrValue(ShippingState, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(ShippingZip, row, "");
                order.getShippingAddress().country = "USA";
                }else
                {
                order.getShippingContact().setName(order.getBillingContact().getName());
                order.getShippingContact().email = order.getBillingContact().email;
                order.getShippingContact().phone = order.getBillingContact().phone;

                order.getShippingAddress().company_name = order.getBillingAddress().company_name;
                order.getShippingAddress().address_one = order.getBillingAddress().address_one;
                order.getShippingAddress().address_two = order.getBillingAddress().address_two;
                order.getShippingAddress().city = order.getBillingAddress().city;
                order.getShippingAddress().state = order.getBillingAddress().state;
                order.getShippingAddress().zip = order.getBillingAddress().zip;
                order.getShippingAddress().country = "USA";
                }










                //bill to client account
                order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");


                order.order_type = "Incredible Discoveries Download";
                order.ship_operator = rdr.getStrValue(OrderSource, row, "");

                order.is_paid = 0;

                // order.is_gift = "" + (rdr.getIntValue(kIs_Gift, row, 0) > 0 ? 1 : 0);
                // order.gift_message = rdr.getStrValue(kGift_Message, row, "");
                 order.cc_num = rdr.getStrValue(CCAcct, row, "");
                 order.cc_exp_mon = Integer.parseInt(rdr.getStrValue(CCExp,row,"00/00").substring(0,rdr.getStrValue(CCExp,row,"00/00").indexOf("/")));
                 order.cc_exp_year = Integer.parseInt("20"+rdr.getStrValue(CCExp,row,"00/00").substring(rdr.getStrValue(CCExp,row,"00/00").indexOf("/")+1));


                // order.po_num = rdr.getStrValue(kPONumber, row, "");
                order.total_shipping_cost = rdr.getFloatValue(ShippingandHandling, row, 0.00f);


            }
            //product data collected for every row
            for(int i=0;i<ProductMaxCount;i++)
            {
             //   log.debug(i+":adding col index start:"+(ProductNameStart+(2*i)));
            addLineItem(order, "102031-0".equals(rdr.getStrValue(ProductNameStart+(2*i), row, "").trim())?"Infomercial Kit":rdr.getStrValue(ProductNameStart+(2*i), row, "").trim(),
                    rdr.getStrValue(ProductQuantityStart+(2*i), row, ""),
                    "24.95",
                    "0.00",
                    "",
                    "", "");
            }
            order.recalculateBalance();
                               order.tax_pct = (float) 0.000000;
                            order.total_tax_cost = (float) 0.000000;
                            order.shipping_taxable = true;


                      if ("CA".equalsIgnoreCase(order.getShippingAddress().state) || "California".equalsIgnoreCase(order.getShippingAddress().state)) {
                                order.tax_pct = (float) 0.077500;
                                order.total_tax_cost = (float) 0.077500 * (order.total_shipping_cost + order.total_product_cost+ (Math.abs(order.discount) * -1));
                            }
                            if ("SD".equalsIgnoreCase(order.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(order.getShippingAddress().state)) {
                                order.tax_pct = (float) 0.060000;
                                order.total_tax_cost = (float) 0.060000 * (order.total_shipping_cost + order.total_product_cost+ (Math.abs(order.discount) * -1));
                            }
        }



        return order;
    }


    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size) throws Exception {
   //     log.debug("adding li:" + sku + "," + qty + "," + unitPrice + "," +
   //             linePrice + "," +
   //             desc + "," +
   //             color + "," + size);


        if(sku.trim().length()>0)
        {
            if(sku.equalsIgnoreCase("101975-0"))
            {
                sku="Info Kit";
                 order.addLineItem(sku,
                    qty,
                    "39.99",
                    ((Double.parseDouble(qty))*39.99)+"",
                    desc,
                    color, size);
            }   else  if(sku.equalsIgnoreCase("101975-1"))
            {
                 sku="Info Kit";
                 order.addLineItem(sku,
                    qty,
                    "39.99",
                    ((Double.parseDouble(qty))*39.99)+"",
                    desc,
                    color, size);
            }   else if(sku.equalsIgnoreCase("101975-2"))
            {
               //rush ship
                order.po_num = "RUSH SHIPPING";
                sku=null;

            }   else {//throw new Exception("Unknown SKU:"+sku);
                 order.addLineItem(sku,
                    qty,
                    unitPrice,
                    ((Double.parseDouble(qty))*Double.parseDouble(unitPrice))+"",
                    desc,
                    color, size);
            }


        }

    }

    public int getOrderCount() {
        return getOrderPositionList().size();
    }

    protected void processReader() {

        String orderRef = "thisisaninvalidorderreference";
      //  log.debug("in processReader");
        if (getDataReader() == null) return;


        log.debug("processing rows "+getDataReader().getRowCount());
        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            //log.debug("DH handling row " + row);
            if (!(getDataReader().getStrValue(0, row, "").trim().equals(""))) {
                if (orderRef.equals(getDataReader().getStrValue(0, row, orderRef))) {
                    //got the same order
                    //log.debug("DH found extra row");
                    ModelBottleDiscoveriesImportData.OrderData data = (ModelBottleDiscoveriesImportData.OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                    //log.debug("DH adding item to order " + data.orderRef);
                } else {
                    //new order
                    //log.debug("DH found new row");
                    ModelBottleDiscoveriesImportData.OrderData data = new ModelBottleDiscoveriesImportData.OrderData();
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

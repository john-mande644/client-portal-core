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
public class DealSegmentsUploadOrdersData {
private final static Logger log =  LogManager.getLogger();

    protected List orderPositionList = new ArrayList();

    int kName = 0;
    int kSegment = 1;
    int ksale_id = 2;
    int ktimestamp = 3;
    int kx_trans_id = 4;
    int kx_method = 5;
    int kx_type = 6;
    int kx_first_name = 7;
    int kx_last_name = 8;
    int kx_company = 9;
    int kx_address = 10;
    int kx_city = 11;
    int kx_state = 12;
    int kx_zip = 13;
    int kx_country = 14;
    int kx_ship_to_company = 15;
    int kx_ship_to_first_name = 16;
    int kx_ship_to_last_name = 17;
    int kx_ship_to_address = 18;
    int kx_ship_to_city = 19;
    int kx_ship_to_state = 20;
    int kx_ship_to_zip = 21;
    int kx_ship_to_country = 22;
    int kx_phone = 23;
    int kx_email = 24;
    int ksku = 25;
    int kname = 26;
    int kdescription = 27;
    int kquantity = 28;
    int ktotal_quantity = 29;
    int kunit_price = 30;
    int kx_tax = 31;
    int kx_duty = 32;
    int kx_freight = 33;
    int kx_amount = 34;

    public static final String Order_Type = "Deal Segments";

    DelimitedReader dataReader = null;

    public DealSegmentsUploadOrdersData() {

    }

    public void init(DelimitedReader rdr) {
        if (rdr == null) return;

        setDataReader(rdr);
        processReader();


    }

    public String getOrderReference(int orderIndex) {
        return ((OrderData) getOrderPositionList().get(orderIndex)).orderRef;
    }

    public String getShowName(int orderIndex) {
        return ((OrderData) getOrderPositionList().get(orderIndex)).showName;
    }

    public float getOrderShipTotal(int orderIndex) {
        return ((OrderData) getOrderPositionList().get(orderIndex)).shipCost;
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
                order.order_refnum = rdr.getStrValue(kx_trans_id, row, "");
                if (order.order_refnum.length() < 4) {
                    throw new Exception("Client order reference was invalid or not found.");
                }


                order.po_num=  rdr.getStrValue(kSegment, row, "");
                order.getShippingInfo().comments=  rdr.getStrValue(ksale_id, row, "");
                order.ship_operator =  rdr.getStrValue(kName, row, "");

                order.getBillingContact().setName((rdr.getStrValue(kx_first_name, row, "") + " " + rdr.getStrValue(kx_last_name, row, "")).trim());

                order.getBillingContact().phone = rdr.getStrValue(kx_phone, row, "");
                order.getBillingAddress().company_name = rdr.getStrValue(kx_company, row, ".");

                if (order.getBillingAddress().company_name.trim().length() < 1) {
                    order.getBillingAddress().company_name = ".";
                }

                order.getBillingAddress().address_one = rdr.getStrValue(kx_address, row, "");
                order.getBillingAddress().address_two = "";
                order.getBillingAddress().city = rdr.getStrValue(kx_city, row, "");
                order.getBillingAddress().state = rdr.getStrValue(kx_state, row, "");
                order.getBillingAddress().zip = rdr.getStrValue(kx_zip, row, "");
                if(order.getBillingAddress().zip.length()==4)
                {
                    order.getBillingAddress().zip = "0"+order.getBillingAddress().zip;
                }

                order.getShippingContact().setName(rdr.getStrValue(kx_ship_to_first_name, row, "")+" "+rdr.getStrValue(kx_ship_to_last_name, row, ""));
                order.getShippingContact().email = rdr.getStrValue(kx_email, row, "");
                order.getShippingContact().phone = rdr.getStrValue(kx_phone, row, "");
                order.getBillingContact().email = rdr.getStrValue(kx_email, row, "");
                order.getShippingAddress().company_name = rdr.getStrValue(kx_ship_to_company, row, ".");

                if (order.getShippingAddress().company_name.trim().length() < 1) {
                    order.getShippingAddress().company_name = ".";
                }
                order.getShippingAddress().address_one = rdr.getStrValue(kx_ship_to_address, row, "");
                order.getShippingAddress().address_two = "";
                order.getShippingAddress().city = rdr.getStrValue(kx_ship_to_city, row, "");
                order.getShippingAddress().state = rdr.getStrValue(kx_ship_to_state, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(kx_ship_to_zip, row, "");
                if(order.getShippingAddress().zip.length()==4)
                {
                    order.getShippingAddress().zip = "0"+order.getShippingAddress().zip;
                }

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
                order.getShippingAddress().country = "USA";
                order.getBillingAddress().country = "USA";

                order.total_shipping_cost = rdr.getFloatValue(kx_duty, row, 0.00f)+rdr.getFloatValue(kx_freight, row, 0.00f);
                order.total_tax_cost = rdr.getFloatValue(kx_tax, row, 0.00f);
                order.getShippingInfo().carr_service = "";

                order.order_type = DealSegmentsUploadOrdersData.Order_Type;
            }
            String sku = rdr.getStrValue(ksku, row, "").trim();

            if("MINI+PIZ".equals(sku))
            {
                sku = "MINI PIZ";
            }
            //product data collected for every row
            addLineItem(order, sku,
                    rdr.getStrValue(kquantity, row, ""),
                    rdr.getStrValue(kunit_price, row, "0.00"),
                    "0.00",
                    "", "", "", "");

           // ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = rdr.getStrValue(kReceipt_Item_Id, row, "");;
        }
        return order;
    }

    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size, String externalLineReference) throws Exception {
        //log.debug("adding li:"+sku+","+qty+","+unitPrice+","+
        //           linePrice+","+
        //           desc+","+
        //           color+","+ size);
               if(sku.equalsIgnoreCase("859329004028SP"))
               {
                   sku =  "DEFINEBUNDLE3";
               }

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
            if (!(getDataReader().getStrValue(kx_trans_id, row, "").trim().equals(""))) {
                if (orderRef.equals(getDataReader().getStrValue(kx_trans_id, row, orderRef))) {
                    //got the same order
                    //log.debug("DH found extra row");
                    OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                    //log.debug("DH adding item to order " + data.orderRef);
                } else {
                    //new order
                    //log.debug("DH found new row");
                    OrderData data = new OrderData();
                    orderRef = getDataReader().getStrValue(kx_trans_id, row, orderRef);
                    data.orderRef = orderRef;
                    data.showName = getDataReader().getStrValue(kName, row, orderRef);
                    data.rowIndexStart = row;
                    data.rowsForOrder = 1;

                    data.shipCost = getDataReader().getFloatValue(kx_duty, row, 0.00f)+getDataReader().getFloatValue(kx_freight, row, 0.00f);
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
        public float shipCost = 0.00f;
        public String showName = "";
    }
}

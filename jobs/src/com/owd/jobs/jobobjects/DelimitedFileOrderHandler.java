package com.owd.jobs.jobobjects;

import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DelimitedReader;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stewartbuskirk1 on 9/24/15.
 */
abstract public class DelimitedFileOrderHandler {
private final static Logger log =  LogManager.getLogger();

    DelimitedReader dataReader = null;

    private List orderPositionList = new ArrayList();

    public int clientId;
    public String fileName = "";

    private OrderProcessingResultsHandler resultsHandler ;

    protected class OrderData {
        public int rowIndexStart = 0;
        public int rowsForOrder = 0;
        public String errorMessage = "";
        public String orderRef = "";

    }

    public DelimitedFileOrderHandler(OrderProcessingResultsHandler resultsHandler, int aclientId, DelimitedReader rdr, String fileName) {
        this.resultsHandler=resultsHandler;
        this.fileName = fileName;
        clientId=aclientId;
        setDataReader(rdr);
        processReader();
    }

    //implement for different layouts
    abstract public Order loadOrder(Order order, int orderIndex) throws Exception;

    //override for different layouts
    abstract public void translateShipMethod (Order order, String oldMethod)   throws Exception;

    //override for different layouts
    public String getOrderReferenceForRow(int rowNumber, String defaultValue)
    {
        return getDataReader().getStrValue(0, rowNumber, defaultValue).trim();
    }

    //override for different layouts
    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice,String desc, String color, String size, String externalLineReference) throws Exception
    {
        log.debug("adding li:"+sku+","+qty+","+unitPrice+","+
                linePrice+","+
                desc+","+
                color+","+ size);


                order.addLineItem(sku,
                        qty,
                        unitPrice,
                        linePrice,
                        desc,
                        color, size);
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = externalLineReference;

    }


    public void processOrders() throws Exception {

        List errorList = new ArrayList();

        for (int orderIndex = 0; orderIndex < getOrderCount(); orderIndex++) {
            try {

                String errorDesc = processOrder(orderIndex);
                if(errorDesc.length()>1)
                {
                    errorList.add(errorDesc);
                }
                log.debug("processed " + (orderIndex + 1));
                //record success
            } catch (Exception ex) {
                //record error
                ex.printStackTrace();
                log.debug("Uncaught error at row " + orderIndex + ":" + ex.getMessage());
                errorList.add(ex.getMessage());
            }


        }
        if(errorList.size()==0)
        {
            resultsHandler.onOrderImportBatchSuccess(fileName);
        }   else {
            resultsHandler.onOrderImportBatchFailure(fileName, errorList.toString().replace("[", "").replace("]", "").replace(", ", "\r\n"));
        }

    }

    private String processOrder(int orderIndex) {
        //returns list of two elements - client Order ID and response
        String response = ""; //blank means all went well
        //add new
      //  response.add(getOrderReference(orderIndex));

        try {
            Order order = new Order(clientId+"");
            if(OrderUtilities.orderRefnumExists(getOrderReference(orderIndex), "" + clientId) )
            {
                log.debug("duplicate order discovered");
                resultsHandler.onOrderImportSuccess(order);

            }   else {


                order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.backorder_rule = OrderXMLDoc.kBackOrderAll;

                //order.is_future_ship=1;
                loadOrder(order, orderIndex);
                //log.debug("after load order");

                order.recalculateBalance();

                //paid for - push it through!
                order.bill_cc_type = "CL";
                order.check_num = "999999";
                order.paid_amount = order.total_order_cost;
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;

                if (order.is_gift.equals("1") && order.gift_message.trim().length() < 1) {
                    order.gift_message = "A gift for you";
                }

                try {
                    translateShipMethod(order, order.getShippingInfo().carr_service);
                } catch (Exception exs) {
                    order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
                    order.is_future_ship = 1;
                    CasetrackerAPI.createCasetrackerCase("Client ID (" + order.clientID + ") order " + order.order_refnum + " received on hold", "Unable to determine ship method; check and verify import setup", Integer.parseInt(order.clientID));
                    order.hold_reason = "Unable to determine ship method; that method is unknown to the importer. Assign to IT Work Orders if needed.";
                }

                String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, true);
                log.debug("reference=" + reference);
                if (reference == null) {
                    if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1
                            &&
                            (order.last_payment_error + " " + order.last_error).indexOf("no physical items") < 1) {
                        log.debug("reporting error on import");
                        resultsHandler.onOrderImportFailure(order);
                        response = (order.last_payment_error + " " + order.last_error);

                    } else {
                        //duplicate
                    }

                } else {
                    log.debug("got valid order import");
                    resultsHandler.onOrderImportSuccess(order);

                }
            }
        } catch (Exception
                ex) {


            ex.printStackTrace();
            response = ex.getMessage();

        }
        return response;
    }

    protected void processReader() {

        String orderRef = "thisisaninvalidorderreference";
        if (getDataReader() == null) return;

        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            if (!(getOrderReferenceForRow(row,"").equals(""))) {
                if (orderRef.equals(getOrderReferenceForRow(row,orderRef))) {
                    //got the same order
                    OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                } else {
                    //new order
                    OrderData data = new OrderData();
                    orderRef = getOrderReferenceForRow(row, orderRef);
                    data.orderRef = orderRef;
                    data.rowIndexStart = row;
                    data.rowsForOrder = 1;
                    getOrderPositionList().add(data);
                }
            }
        }
    }

    public int getOrderCount() {
        return getOrderPositionList().size();
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

    public String getOrderReference(int orderIndex) {
        return ((OrderData) getOrderPositionList().get(orderIndex)).orderRef;
    }


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

}

package com.owd.core.business.client;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 14, 2003
 * Time: 2:42:42 PM
 * To change this template use Options | File Templates.
 */
public class PhionPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    public List getCustomOrderFields(Order order) {
        return new ArrayList();
    }

    public void updateLineItemsAfterItemChange(Order anOrder) {

    }

    public void handleCustomOrderFields(Order order, List fields) {

    }
      protected boolean manualMode = false;


    public void setManualEntryMode(boolean yes)
    {
        manualMode = yes;
    }
    public void updateCustomOrderFields(Order order, HttpServletRequest request, List fields) {

    }
     public boolean hasManualEntryMode(){ return false;}
    public boolean allowUnitPriceEditing(Order order) {
        return true;
    }

    public boolean allowShipPriceEditing(Order order) {
        return true;
    }

    public boolean allowSalesTaxEditing(Order order) {
        return true;
    }
    public void sendFlexPayDeclineEmail(OrderStatus order)
      {

      }
    public String getFlexPayStatement(Order order){ return "";}
       
    public boolean allowManualDiscountEntry(Order order) {
        return false;
    }

    public String getShipOptionName(String optionType, List shipOptions) {

        for (int i = 0; i < shipOptions.size(); i++) {
            if (((ShippingOption) shipOptions.get(i)).code.equals(optionType)) {
                return ((ShippingOption) shipOptions.get(i)).name;
            }
        }

        return "No ship method found for code " + optionType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    Client client = null;


    public PhionPolicy(Client client) {
        setClient(client);


    }

    public PhionPolicy() {


    }


    public float getSalesTaxValue(Order order) throws Exception {

        return 0.00f;

    }

    public Order createInitializedOrder() {
        Order order = null;

        try{
            new Order("" + getClient().client_id);
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }

        order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
        order.backorder_rule = OrderXMLDoc.kBackOrderAll;
        order.order_type = "Direct Entry";

        return order;


    }


    public String getShippingMethod(Order order, String shipType, List shipOptions) throws Exception {
        return getShipOptionForType(shipType, shipOptions).name;
    }

    public float getShippingCost(Order order, String shipType, List shipOptions) throws Exception {


        ShippingOption option = getShipOptionForType(shipType, shipOptions);
        return option.customerCost;

    }

    public ShippingOption getShipOptionForType(String shipType, List shipOptions) throws Exception {
        //log.debug("ShipOptionForType list:" + shipOptions);
        for (int i = 0; i < shipOptions.size(); i++) {
            if (((ShippingOption) shipOptions.get(i)).code.equals(shipType)) {
                return ((ShippingOption) shipOptions.get(i));
            }
        }

        throw new Exception("Shipping Option " + shipType + " not found!");
    }



    public Map getPaymentOptions() throws Exception {
        Map aMap = new TreeMap();
        aMap.put("CC", "Credit Card");
        aMap.put("CK", "Check/Mail/Hold for Release");
        aMap.put("FREE", "No charge - ship without payment");

        return aMap;
    }

    public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity) throws Exception {
        order.addLineItem(sku, quantity, price, "0.00", description, "", "");
    }

    public void saveNewOrder(Order order, boolean testing) throws Exception {

        order.saveNewOrder(OrderUtilities.kRequirePayment, testing);


    }


    public void sendNotificationMessage(Order order, String subject, String message) {
        try {
            String[] toAddresses = {"sarah@owd.com"};
            if (order.is_future_ship == 1) {
                String asubject = "PhIon order placed on hold";
                String amessage = "OWD Order reference " + order.orderNum + " has been placed on hold. Please check the order on the OWD extranet for details.";

                Mailer.sendMail(asubject, amessage, toAddresses, "do_not_reply@owd.com");
            }
        } catch (Exception ex) {
        }
    }

}



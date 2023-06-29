package com.owd.core.business.client;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 9, 2004
 * Time: 1:27:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class FloraPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    public boolean allowUnitPriceEditing(Order order) {
        return false;
    }

    public boolean allowManualDiscountEntry(Order order) {
        return true;

    }

    public boolean allowShipPriceEditing(Order order) {
        return false;
    }

    public boolean allowSalesTaxEditing(Order order) {
        return false;
    }

    public Map getPaymentOptions() throws Exception {
        Map aMap = new TreeMap();
        if (client.pp_proc != null && client.pp_proc.indexOf("payment") > 0) {
            aMap.put("CC", "Credit/Debit Card");
        }
        aMap.put("CK", "Hold for Manual Release");

        return aMap;
    }

    public float getSalesTaxValue(Order order) throws Exception {
        float supplementTaxRate = 0.00f;
        float creamTaxRate = 0.00f;
        float finalTax = 0.00f;


        if (order.getShippingAddress().state.equals("CA")) {
            supplementTaxRate = 0.0825f;
            creamTaxRate = 0.0825f;
        } else if (order.getShippingAddress().state.equals("CO")) {
            supplementTaxRate = 0.037f;
            creamTaxRate = 0.037f;
        } else if (order.getShippingAddress().state.equals("CT")) {
            supplementTaxRate = 0.00f;
            creamTaxRate = 0.06f;
        } else if (order.getShippingAddress().state.equals("FL")) {
            supplementTaxRate = 0.00f;
            creamTaxRate = 0.07f;
        } else if (order.getShippingAddress().state.equals("GA")) {
            supplementTaxRate = 0.06f;
            creamTaxRate = 0.06f;
        } else if (order.getShippingAddress().state.equals("HI")) {
            supplementTaxRate = 0.045f;
            creamTaxRate = 0.045f;
        } else if (order.getShippingAddress().state.equals("IL")) {
            supplementTaxRate = 0.01f;
            creamTaxRate = 0.0675f;
        } else if (order.getShippingAddress().state.equals("NJ")) {
            supplementTaxRate = 0.00f;
            creamTaxRate = 0.06f;
        } else if (order.getShippingAddress().state.equals("NY")) {
            supplementTaxRate = 0.00f;
            creamTaxRate = 0.0875f;
        } else if (order.getShippingAddress().state.equals("NC")) {
            supplementTaxRate = 0.065f;
            creamTaxRate = 0.065f;
        } else if (order.getShippingAddress().state.equals("PA")) {
            supplementTaxRate = 0.00f;
            creamTaxRate = 0.07f;
        } else if (order.getShippingAddress().state.equals("TX")) {
            supplementTaxRate = 0.00f;
            creamTaxRate = 0.0825f;
        } else if (order.getShippingAddress().state.equals("VA")) {
            supplementTaxRate = 0.045f;
            creamTaxRate = 0.045f;
        } else if (order.getShippingAddress().state.equals("WA")) {
            supplementTaxRate = 0.082f;
            creamTaxRate = 0.082f;
        }


        finalTax = getSupplementValueFromOrder(order) * supplementTaxRate + getCreamValueFromOrder(order) * creamTaxRate;

        /*
          CO			3.7 %
          CT		STATEWIDE			6.0 % *
          FL			PINELLAS			7.0 % *
          GA			GWINNETTE			6.0 %
          HI			MAUI				4.5 %
          IL			DU PAGE       6.75% ALL	1.0 % SUPPLEMENTS
          NJ			MIDDLESEX			6.0 % *
          NY			NASSAU			8.75% *
          NC		BURKE				6.5 %
          PA		PHILADELPHIA		7.0 % *
          TX			AUSTIN			8.25% *
          VA			NEWPORT NEWS		4.5 %
          WA		WHATCOM			8.2 %
          */

        return finalTax;

    }

    protected static float getSupplementValueFromOrder(Order anOrder) {
        float value = 0.00f;
        for (int i = 0; i < anOrder.skuList.size(); i++) {
            LineItem item = (LineItem) anOrder.skuList.elementAt(i);
            value = value + (item.description.indexOf("capsule") >= 0 ? item.sku_price * item.quantity_request : 0.00f);
        }

        return value;
    }

    protected static float getCreamValueFromOrder(Order anOrder) {
        float value = 0.00f;
        for (int i = 0; i < anOrder.skuList.size(); i++) {
            LineItem item = (LineItem) anOrder.skuList.elementAt(i);
            value = value + (item.description.indexOf("cream") >= 0 ? item.sku_price * item.quantity_request : 0.00f);
        }

        return value;
    }

    public static final String kCCFieldName = "flora_cc";
    public static final String kCCExpFieldName = "flora_cc_exp";


    public List getCustomOrderFields(Order order) {
        List fieldList = new ArrayList();
        CustomField sourceField = new CustomField();
        sourceField.setDisplayType(CustomField.displayTypeText);
        sourceField.setCurrentValue("");
        sourceField.setDisplayName("What credit card will you be using?");
        sourceField.setFieldName(kCCFieldName);
        sourceField.setDescription("Enter type and account (VISA-41111111111)");
        fieldList.add(sourceField);
        CustomField catalogField = new CustomField();
        catalogField.setDisplayType(CustomField.displayTypeText);
        catalogField.setCurrentValue("");
        catalogField.setDisplayName("What is the credit card expiration date?");
        catalogField.setFieldName(kCCExpFieldName);
        catalogField.setDescription("Enter MM/YY format for date");

        fieldList.add(catalogField);

        return fieldList;
    }

    public void handleCustomOrderFields(Order order, List fields) {
        for (int i = 0; i < fields.size(); i++) {
            CustomField cf = ((CustomField) fields.get(i));
            if (cf.getFieldName().equals(kCCFieldName)) {
                order.getShippingInfo().whse_notes = cf.getCurrentValue();
            }
            if (cf.getFieldName().equals(kCCExpFieldName)) {
                order.getShippingInfo().comments = cf.getCurrentValue();

            }
        }
    }

    public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {


        return OWDUtilities.roundFloat(5.95f);

    }


    public void updateLineItemsAfterItemChange(Order order) {
        try {
            if (order.skuList.size() > 1 || ((LineItem) order.skuList.get(0)).quantity_request > 1) {
                for (int i = 0; i < order.skuList.size(); i++) {
                    LineItem item = (LineItem) order.skuList.elementAt(i);
                    item.sku_price = OWDUtilities.roundFloat(item.getInventory().price * 0.90f);
                    item.total_price = item.sku_price * item.quantity_request;

                }
            } else {
                for (int i = 0; i < order.skuList.size(); i++) {
                    LineItem item = (LineItem) order.skuList.elementAt(i);
                    item.sku_price = OWDUtilities.roundFloat(Inventory.getInventoryForID(item.inventory_fkey).price);
                    item.total_price = item.sku_price * item.quantity_request;

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveNewOrder(Order order, boolean testing) throws Exception {

        order.deleteLineItemForSKU("Brochure");
        order.addLineItem("Brochure", 1, 0.00f, 0.00f, "Free Brochure", "", "");
        order.saveNewOrder(OrderUtilities.kRequirePayment, testing);

    }

}

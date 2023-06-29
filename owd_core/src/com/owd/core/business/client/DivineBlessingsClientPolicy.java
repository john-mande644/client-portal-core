package com.owd.core.business.client;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 13, 2006
 * Time: 10:01:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class DivineBlessingsClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();
    public List getCustomOrderFields(Order order) {
   List fieldList = new ArrayList();

CustomField sourceField = new CustomField();
        sourceField.setDisplayType(CustomField.displayTypeText);
        sourceField.setCurrentValue("");
        sourceField.setDisplayName("Enter CC Number here (VERIFY!):");
        sourceField.setFieldName("tempCC");
        sourceField.setDescription("<font color=red><B>CC NUMBER REQUIRED</B></FONT>");

        fieldList.add(sourceField);

        CustomField sourceField2 = new CustomField();
        sourceField2.setDisplayType(CustomField.displayTypeText);
        sourceField2.setCurrentValue("");
        sourceField2.setDisplayName("Enter CC Expiration here (VERIFY!):");
        sourceField2.setFieldName("tempCCEXP");
        sourceField2.setDescription("<font color=red><B>CC EXPIRATION REQUIRED - ENTER AS MMYY (0107)</B></FONT>");

        fieldList.add(sourceField2);

        return fieldList;  }

    public void handleCustomOrderFields(Order order, List fields) {
  order.getShippingInfo().whse_notes = "";
           order.getShippingInfo().comments = "";

                    for (int i = 0; i < fields.size(); i++) {
            CustomField cf = ((CustomField) fields.get(i));

            if (cf.getFieldName().equals("tempCC")) {
                order.getShippingInfo().comments = "/CC:"+cf.getCurrentValue();
            }




            if (cf.getFieldName().equals("tempCCEXP")) {

                 order.getShippingInfo().comments = order.getShippingInfo().comments+"/EXP:"+cf.getCurrentValue();

            }
        }    }



    public boolean allowUnitPriceEditing(Order order) {
return false;
    }

    public boolean allowManualDiscountEntry(Order order) {
return false;    }

    public boolean allowShipPriceEditing(Order order) {
return false;    }

    public boolean allowSalesTaxEditing(Order order) {
return false;    }

    public float getSalesTaxValue(Order order) throws Exception {
 order.tax_pct = (float) 0.00f;
        order.shipping_taxable = true;


        if ("SD".equalsIgnoreCase(order.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(order.getShippingAddress().state)) {
            order.tax_pct = (float) 0.060000;
            return (float) 0.060000 * (order.total_shipping_cost + order.total_product_cost);
        }

        return 0.00f;    }

    public String getShippingMethod(Order order, String shipType, List shipOptions) throws Exception {
        return super.getShippingMethod(order, shipType, shipOptions);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {
  if (shipOption.name.indexOf("USPS Priority Mail")>=0) {
            return OWDUtilities.roundFloat(5.00f);
        } else if (order.getShippingAddress().isCanada()){
            return OWDUtilities.roundFloat(shipOption.ratedCost + 1.00f);
        } else {
            return OWDUtilities.roundFloat(shipOption.ratedCost + 1.00f);
        }

    }

    public List getShipOptions(Order order, float defaultCost) {
        return super.getShipOptions(order, 5.00f);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public void saveNewOrder(Order order, boolean testing) throws Exception {
            order.saveNewOrder(OrderUtilities.kHoldForPayment, testing);    }

}

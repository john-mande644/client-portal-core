package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 1, 2004
 * Time: 2:16:00 PM
 * To change this template use File | Settings | File Templates.
 */

public class RabbisDaughtersClientPolicy extends DefaultClientPolicy{
private final static Logger log =  LogManager.getLogger();


    static String kPayTypeFieldName = "custom_payterms";


     public List getCustomOrderFields(Order order) {
        List fieldList = new ArrayList();
        CustomField sourceField = new CustomField();
        sourceField.setDisplayType(CustomField.displayTypeSelect);
        sourceField.setCurrentValue("No Charge");
        sourceField.setDisplayName("Payment Terms?");
        sourceField.setFieldName(kPayTypeFieldName);
        sourceField.setDescription("");
         Map myMap = new TreeMap();
       //  myMap.put("COD","COD");

         myMap.put("Credit Card","Credit Card");

         myMap.put("Net 60","Net 60");

         myMap.put("Net 45","Net 45");

         myMap.put("Net 30","Net 30");

         myMap.put("Net 15","Net 15");

         myMap.put("Net 10","Net 10");

         myMap.put("Net 5","Net 5");

         myMap.put("No Charge","No Charge");

         myMap.put("ROG","ROG");


         sourceField.setChoiceList(myMap);
        fieldList.add(sourceField);

        return fieldList;
    }

         public void handleCustomOrderFields(Order order, List fields) {
        for (int i = 0; i < fields.size(); i++) {
            CustomField cf = ((CustomField) fields.get(i));
            if (cf.getFieldName().equals(kPayTypeFieldName)) {


                String payChoice = cf.getCurrentValue();
                order.getShippingInfo().whse_notes = payChoice;
            }

        }
    }



}

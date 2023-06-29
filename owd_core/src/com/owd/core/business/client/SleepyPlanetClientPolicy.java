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
 * Date: Jun 25, 2007
 * Time: 1:23:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class SleepyPlanetClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();


   static String kPayTypeFieldName = "custom_packslipfield";

    static String kSRBPackSlip = "Credit Card Paid";
    static String kABSPackSlip = "PO/Invoice";


     public List getCustomOrderFields(Order order) {
        List fieldList = new ArrayList();
        CustomField sourceField = new CustomField();
        sourceField.setDisplayType(CustomField.displayTypeSelect);
        sourceField.setCurrentValue(kSRBPackSlip);
        sourceField.setDisplayName("Payment Method:");
        sourceField.setFieldName(kPayTypeFieldName);
        sourceField.setDescription("");
         Map myMap = new TreeMap();
         myMap.put(kSRBPackSlip,kSRBPackSlip)  ;
         myMap.put(kABSPackSlip,kABSPackSlip);
         sourceField.setChoiceList(myMap);
        fieldList.add(sourceField);

        return fieldList;
    }

         public void handleCustomOrderFields(Order order, List fields) {

             order.ship_operator="";

             for (int i = 0; i < fields.size(); i++) {
            CustomField cf = ((CustomField) fields.get(i));
            if (cf.getFieldName().equals(kPayTypeFieldName)) {


                String packChoice = cf.getCurrentValue();
                order.ship_operator=packChoice;
            }

        }
    }

}

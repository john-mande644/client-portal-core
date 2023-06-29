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

public class ActuarialClientPolicy extends DefaultClientPolicy{
private final static Logger log =  LogManager.getLogger();


    static String kSlipTypeFieldName = "custom_packslipfield";

    static String kSRBPackSlip = "pack_slip";
    static String kABSPackSlip = "invoice";


     public List getCustomOrderFields(Order order) {
        List fieldList = new ArrayList();
        CustomField sourceField = new CustomField();
        sourceField.setDisplayType(CustomField.displayTypeSelect);
        sourceField.setCurrentValue(kSRBPackSlip);
        sourceField.setDisplayName("Which packing slip should be used?");
        sourceField.setFieldName(kSlipTypeFieldName);
        sourceField.setDescription("");
         Map myMap = new TreeMap();
         myMap.put(kSRBPackSlip,"Slide Rule Books")  ;
         myMap.put(kABSPackSlip,"Actuarial Bookstore");
         sourceField.setChoiceList(myMap);
        fieldList.add(sourceField);

        return fieldList;
    }

         public void handleCustomOrderFields(Order order, List fields) {
        for (int i = 0; i < fields.size(); i++) {
            CustomField cf = ((CustomField) fields.get(i));
            if (cf.getFieldName().equals(kSlipTypeFieldName)) {


                String packChoice = cf.getCurrentValue();
                if(kSRBPackSlip.equals(packChoice))
                {
                    order.prt_invoice_reqd = 0;
                    order.prt_pack_reqd = 1;
                    order.prt_pick_reqd = 0;
                }   else if(kABSPackSlip.equals(packChoice))
                {
                    order.prt_invoice_reqd = 1;
                    order.prt_pack_reqd = 0;
                    order.prt_pick_reqd = 0;
                }   else
                {
                    order.prt_invoice_reqd = 0;
                    order.prt_pack_reqd = 1;
                    order.prt_pick_reqd = 0;
                }
            }

        }
    }



}

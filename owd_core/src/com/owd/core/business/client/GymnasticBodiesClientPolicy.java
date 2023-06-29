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
 * Date: Aug 27, 2003
 * Time: 12:48:07 PM
 * To change this template use Options | File Templates.
 */
public class GymnasticBodiesClientPolicy extends DefaultClientPolicy implements ClientPolicy  {
private final static Logger log =  LogManager.getLogger();

    String isSigRequired = "1";

    public void setSignatureRequired(Order order) throws Exception
    {
        order.getShippingInfo().ss_proof_delivery = isSigRequired;
    }

    public void setSignatureRequired(String remoteSourceKey, Order order) 
    {
        // if(remoteSourceKey.equals("magento"))
       // {
           // if(order.getShippingAddress().company_name.trim().length()>0)
           // {
         //     order.getShippingInfo().ss_proof_delivery = "1";
          //  }                                               else
          //  {
          //      if (order.getShippingAddress().isUS() && (order.getShippingAddress().address_one.toUpperCase().indexOf(" APT")>0
          //              || order.getShippingAddress().address_one.toUpperCase().indexOf(" #")>0
          //                || order.getShippingAddress().address_one.toUpperCase().indexOf(" UNIT")>0
          //                || order.getShippingAddress().address_one.toUpperCase().indexOf(" STE")>0
          //                || order.getShippingAddress().address_one.toUpperCase().indexOf(" SUITE")>0))
          //      {
          //          order.getShippingInfo().ss_proof_delivery = "1";
          //      }
          //  }
       // }else
       //   {
                order.getShippingInfo().ss_proof_delivery = isSigRequired;
        //  }
    }

    public static void main (String args[])
    {
        //log.debug(200.00f/30f);
    }

    public List getCustomOrderFields(Order order) {
         List<CustomField> fieldList = new ArrayList<CustomField>();
        CustomField sourceField = new CustomField();
        sourceField.setDisplayType(CustomField.displayTypeSelect);
        sourceField.setCurrentValue(isSigRequired);
        sourceField.setDisplayName("Request Signature Required Service?");
        sourceField.setFieldName("sigrequired");
        sourceField.setDescription("");
         Map<String,String> myMap = new TreeMap<String,String>();
         myMap.put("0","No")  ;
         myMap.put("1","Yes");
         sourceField.setChoiceList(myMap);
        fieldList.add(sourceField);
        return fieldList;
    }

    public void handleCustomOrderFields(Order order, List fields) {
        for (Object field : fields) {
            CustomField cf = ((CustomField) field);
            if (cf.getFieldName().equals("sigrequired")) {
                isSigRequired = cf.getCurrentValue();
            }
        }
    }

    //call *after* filling in ship address and item information
    public String getOWDShipMethodForExternalShipMethodName(String remoteSourceKey, String remoteMethod, Order order)
    {
       if(remoteSourceKey.equals("magento"))
        {
            if(remoteMethod.toUpperCase().contains("STANDARD"))
            {
                return "USPS First-Class Mail International";
            }
        }
        return remoteMethod;

    }
      public String translateRemoteSkuToOwdSku(String remoteSourceKey, String remoteSku)
    {
        if(remoteSourceKey.equals("magento"))
        {
            if(remoteSku.startsWith("PKG-"))
            {
                try
                {
                    int packNum = Integer.parseInt(remoteSku.substring(remoteSku.indexOf("PKG-")+4));
                    return "Package "+packNum;
                }   catch(Exception ex)
                {

                }
            }else if (remoteSku.equals("5-dvd-set"))
            {
                return "DVD Set";
            }
        }
        return remoteSku;
    }


}

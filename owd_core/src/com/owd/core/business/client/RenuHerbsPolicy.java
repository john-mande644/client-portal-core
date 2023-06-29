package com.owd.core.business.client;

import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 7, 2007
 * Time: 5:14:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RenuHerbsPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    static List<String> taxableItems = new ArrayList<String>();

    {
        taxableItems.add("dream diet type AB");
        taxableItems.add("dream diet type B");
        taxableItems.add("dream diet type A");
        taxableItems.add("dream diet type O");
        taxableItems.add("fast grow hair and scalp conditioner");
        taxableItems.add("the hair growth system");
        taxableItems.add("seaweed soap");
        taxableItems.add("seaweed soap special");
        taxableItems.add("leave in conditioner");
        taxableItems.add("conditioner");
        taxableItems.add("shampoo");
        taxableItems.add("hair oil");
        taxableItems.add("braid spray");
        taxableItems.add("shea butter");
        taxableItems.add("smart and natural");
    }


    public void saveNewOrder(Order order, boolean testing) throws Exception {
      //  order.deleteLineItemForSKU("insert");
      //  order.addLineItem("insert", "1", "0.00", "0.00", "", "", "");
      //  order.deleteLineItemForSKU("Silica Insert");
     //   order.addInsertItemIfAvailable("Silica Insert", 1);

                //        order.deleteLineItemForSKU("stickers");
               //         order.addLineItem("stickers","1","0.00","0.00","","","");
        order.saveNewOrder(OrderUtilities.kRequirePayment, testing);

    }


    public boolean allowSalesTaxEditing(Order order) {
        return false;
    }


      public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {

           if(order.containsSKU("lollipop and colon sample") && order.getLineCount()==1)
           {
               return 0.00f;
           } else
           {
               if(shipOption.name.toUpperCase().indexOf("USPS EXPR")>=0)
               {
                   return shipOption.ratedCost;
               } else
               {
                    return 8.00f;
               }
           }


    }

     public List getShipOptions(Order order, float defaultCost) {

         List<ShippingOption> options = (List<ShippingOption>) super.getShipOptions(order,defaultCost);
           if(order.containsSKU("lollipop and colon sample") && order.getLineCount()==1)
           {
               List<ShippingOption> removeList = new ArrayList<ShippingOption>();
               for (ShippingOption option:options)
               {
                    if(option.name.toUpperCase().indexOf("USPS PRIORITY MAIL")<0)
                    {
                        removeList.add(option);
                    }

               }
               options.removeAll(removeList);
           }
                return options;


     }



    public float getSalesTaxValue(Order order) throws Exception {
        order.tax_pct = (float) 0.00f;
        order.shipping_taxable = true;
        float totalTax = 0.00f;

        if ("TX".equalsIgnoreCase(order.getShippingAddress().state) || "Texas".equalsIgnoreCase(order.getShippingAddress().state)) {
            order.tax_pct = (float) 0.085000;
        }
        if ("SD".equalsIgnoreCase(order.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(order.getShippingAddress().state)) {
            order.tax_pct = (float) 0.060000;
        }

        for (int i=0;i<order.skuList.size();i++) {

            LineItem item =(LineItem)  order.skuList.elementAt(i);

            if (taxableItems.contains(item.client_part_no)) {
                totalTax += item.total_price * order.tax_pct;

            }

        }

        if (totalTax < 0.01f) {
            order.tax_pct = 0.00f;
            totalTax = 0.00f;
        } else {
            totalTax += order.total_shipping_cost * order.tax_pct;
        }


        return totalTax;

    }
}

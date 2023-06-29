package com.owd.jobs.jobobjects.clients

import com.owd.core.business.order.LineItem
import com.owd.core.business.order.Order

/**
 * Created by stewartbuskirk1 on 3/1/16.
 */
class BigSexyLingerieOrderRules {
      /*
      1. All orders with a total number of 1 or more of any of the SKUs
(517/17G,BSL918,4108,BSL4108) should have 1 unit of GiftBag-8410 added to
the order.

2. Count the total number of units in each order for any SKU containing
"316" or "171". If the count is 1, 2, or 3, add 1 unit of GiftBag-627 to
the order. If the count is greater than 3, add 1 unit of GiftBag-8410 to
the order. If the count is zero, do nothing.

3. Count the number of units in each order for any SKU containing "171"
*and* "duo". If the count is exactly 1, add 1 unit of GiftBag-627 to the
order. If the count is greater than 1, add 1 unit of GiftBag-8410 to the
order. If the count is zero, do nothing.

All rules should be applied to all orders (that is, orders can match more
than one rule).
*/
    static void apply(Order order)
    {
        int bag8410Count = 0
        int bag627Count = 0

       if((getRequestedUnitsofSkusContainingStringsFromList(order,Arrays.asList("4197"))+getRequestedUnitsofSkusContainingStringsFromList(order,Arrays.asList("517/17G","BSL918","389","1417","4108","BSL4108"))) >= 1 ) {
           bag8410Count+=1
       }

        int rule2Count = getRequestedUnitsofSkusContainingStringsFromList(order,Arrays.asList("316","171"))
        if(rule2Count>=1 && rule2Count<4) {
            bag627Count+=1
        }   else if(rule2Count>=4) {
            bag8410Count+=1
        }

        int rule3Count = getRequestedUnitsofSkusContainingAllStringsFromList(order,Arrays.asList("duo","171"))
        if(rule3Count==1) {
            bag627Count+=1
        }   else if(rule3Count>1) {
            bag8410Count+=1
        }

        if(bag8410Count>0 && bag627Count>bag8410Count) {
            bag627Count -=   bag8410Count
        }else if(bag8410Count>0 && bag627Count<=bag8410Count)  {
            bag627Count = 0
        }

        if(bag8410Count>0) {
            order.addLineItemWithInventory("GiftBag-8410", bag8410Count, 0.00f, 0.00f, "", "", "")
        }
        if(bag627Count>0) {
            order.addLineItemWithInventory("GiftBag-627", bag627Count, 0.00f, 0.00f, "", "", "")
        }

    }

    private static int getRequestedUnitsofSkusContainingAllStringsFromList(Order order, List<String> skuSubstrings) {

        int units = 0;
        // log.debug("In contains any SKU, got array size:"+sku.size());
        for (LineItem line:(Vector<LineItem>)order.skuList) {
            boolean match = true
            for(String sub:skuSubstrings) {
                if (!(line.client_part_no).contains(sub)) {
                    match = false
                }
            }
            if(match) {
                units += line.quantity_request;
            }
        }
        return units;
    }

    private static int getRequestedUnitsofSkusContainingStringsFromList(Order order, List<String> skuSubstrings) {

        int units = 0;
        // log.debug("In contains any SKU, got array size:"+sku.size());
        for (LineItem line:(Vector<LineItem>)order.skuList) {
            for(String sub:skuSubstrings) {
                if ((line.client_part_no).contains(sub)) {
                    units += line.quantity_request;
                }
            }
        }
        return units;
    }
}

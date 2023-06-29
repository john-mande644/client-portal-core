package com.owd.web.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 19, 2005
 * Time: 8:32:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstaChargeBatchOrderImporter extends OWDWebBatchUploadOrdersData {
private final static Logger log =  LogManager.getLogger();

    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size) throws Exception {

        if ("RUSH-FREE".equals(sku)) {
            order.getShippingInfo().whse_notes = "Free Priority RUSH Shipping";

        } else if ("RUSH".equals(sku)) {
            order.getShippingInfo().whse_notes = "Priority RUSH Shipping";

        } else if ("ADD-01".equals(sku)) {
            sku = "INS-01";
        }
        if ("INS-01".equals(sku)) {

            int nqty = Integer.decode(qty).intValue();
            nqty = nqty * 2;

            Inventory item = Inventory.dbloadByPart(sku, "307");
            if (item != null) {
                if (item.inventory_id != 0) {
                    Vector items = order.skuList;

                    for (int i = 0; i < items.size(); i++) {
                        if (sku.equals(((LineItem) items.elementAt(i)).client_part_no)) {
                            qty = "" + (Integer.decode(qty).intValue() + ((LineItem) items.elementAt(i)).quantity_request);
                            items.removeElementAt(i);
                            i = items.size();
                        }
                    }

                    order.addLineItem(sku, nqty + "", unitPrice, linePrice, desc, color, size);

                } else {
                    throw new Exception("Could not find item " + sku);
                }
            }

        }
    }
}

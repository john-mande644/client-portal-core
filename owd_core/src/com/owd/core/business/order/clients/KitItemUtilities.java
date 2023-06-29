package com.owd.core.business.order.clients;

import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 19, 2005
 * Time: 2:37:05 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class KitItemUtilities {
private final static Logger log =  LogManager.getLogger();

    public abstract Map getKitMap();

    public boolean removeDuplicates = false;

    public void addKitDefinition(String kitSKU, String subSKU, int quantity) {
        //log.debug("Adding item " + kitSKU + " part " + subSKU + "," + quantity + " for getKitMap() " + getKitMap());

        //log.debug("checking key " + kitSKU);
        if (!getKitMap().containsKey(kitSKU)) {
            //log.debug("adding list");
            getKitMap().put(kitSKU, new ArrayList());
        }
        //log.debug("List found");
        //log.debug("List is " + ((List) getKitMap().get(kitSKU)));
        ((List) getKitMap().get(kitSKU)).add(new KitComponent(subSKU, quantity));
        //log.debug("Added item " + kitSKU + " part " + subSKU + "," + quantity);

    }


    public void addItem(Order order, String clientSKU, String quant, String itemPrice, String linePrice, String desc, String color, String size) throws Exception {
        if (getKitMap().get(clientSKU.toUpperCase()) == null) {
            //log.debug("KIT-AddItem: No Mapped SKU for item :" + clientSKU + ":");
            if(removeDuplicates)
            {
                order.addItem(order.getLineItem(clientSKU, quant, itemPrice, linePrice, desc, color, size));
            }   else
            {
                order.addLineItem(clientSKU, quant, itemPrice, linePrice, desc, color, size);
            }
        } else {


            Inventory item = Inventory.dbloadByPart("KIT-ITEM", order.clientID);
            item.addToAvailabilityEverywhere(Integer.parseInt(quant));
                 if(removeDuplicates)
            {
                order.addItem(order.getLineItem("KIT-ITEM", quant, itemPrice, linePrice, "(" + clientSKU + ") " + desc, color, size));
            }   else
            {
                order.addLineItem("KIT-ITEM", quant, itemPrice, linePrice, "(" + clientSKU + ") " + desc, color, size);
            }
            Iterator it = ((List) getKitMap().get(clientSKU.toUpperCase())).iterator();
            while (it.hasNext()) {
                KitComponent part = (KitComponent) it.next();
                   if(removeDuplicates)
            {
                order.addItem(order.getLineItem(part.getSubSKU(), "" + (Integer.decode(quant).intValue() * part.getQuantity()), "0.00", "0.00", "", "", ""));
            }   else
            {
                order.addLineItem(part.getSubSKU(), "" + (Integer.decode(quant).intValue() * part.getQuantity()), "0.00", "0.00", "", "", "");
            }
            }
        }
    }

     public void addItemToExistingOrder(OrderStatus order, String clientSKU, int quant, float itemPrice, String desc, String color, String size) throws Exception {
        if (getKitMap().get(clientSKU.toUpperCase()) == null) {
            log.debug("KIT-AddItem: No Mapped SKU for item :" + clientSKU + ":");

            log.debug(order.order_id+"(ID)");
            log.debug(order.client_id+"(CID)");
                OrderStatus.addLineItemToExistingOrder(new Integer(order.order_id).intValue(),new Integer(order.client_id).intValue(),clientSKU,desc, quant, itemPrice, 0.00f);

        } else {


            Inventory item = Inventory.dbloadByPart("KIT-ITEM", order.client_id);

          //      order.addLineItem("KIT-ITEM", quant, itemPrice, linePrice, "(" + clientSKU + ") " + desc, color, size);
            OrderStatus.addLineItemToExistingOrder(new Integer(order.order_id).intValue(),new Integer(order.client_id).intValue(),"KIT-ITEM","(" + clientSKU + ") " + desc, quant, itemPrice, 0.00f);

            Iterator it = ((List) getKitMap().get(clientSKU.toUpperCase())).iterator();
            while (it.hasNext()) {
                KitComponent part = (KitComponent) it.next();
                log.debug("adding "+part);
         //       order.addLineItem(part.getSubSKU(), "" + (Integer.decode(quant).intValue() * part.getQuantity()), "0.00", "0.00", "", "", "");
                OrderStatus.addLineItemToExistingOrder(new Integer(order.order_id).intValue(),new Integer(order.client_id).intValue(),
                        part.getSubSKU(),"(" + clientSKU + ") " + desc, quant * part.getQuantity(), 0.00f, 0.00f);
               
            }
        }
    }

    public static class KitComponent {

        public KitComponent(String sku, int qty) {
            subSKU = sku;
            quantity = qty;
        }

        String subSKU = "";
        int quantity = 0;

        public String getSubSKU() {
            return subSKU;
        }

        public void setSubSKU(String subSKU) {
            this.subSKU = subSKU;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}

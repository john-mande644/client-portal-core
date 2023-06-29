package com.owd.core.business.client;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 4, 2006
 * Time: 8:32:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class LetsGoStrollingClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();

    public void saveNewOrder(Order order, boolean testing) throws Exception {

        Vector skuList = order.skuList;
        int qty=0;
for(int i=0;i<skuList.size();i++)
{
    LineItem item = (LineItem) skuList.get(i);
    if(item.client_part_no.toUpperCase().startsWith("JAN-PT-") &&
            (!("JAN-PT-INST".equals(item.client_part_no.toUpperCase()))))
    {
        qty+=item.quantity_request;
    }
}
        if(qty>0)
        {
            order.deleteLineItemForSKU("jan-pt-inst");
            order.addInsertItemIfAvailable("jan-pt-inst",qty);
        }

        if(order.getShippingInfo().carr_service.toUpperCase().indexOf("UPS")>=0 ||
                order.getShippingInfo().carr_service.toUpperCase().indexOf("FEDEX")>=0 )
        {
        float ins = getTotalSupplierCost(order);
        if(ins>=0.00f)
        {
            if(ins>99.00f && !order.getShippingAddress().isInternational())
            {
                ins = 99.00f;
            }
            order.getShippingInfo().ss_declared_value="1";
            order.getShippingInfo().declared_value=""+ins;

        }
        }else
        {

            order.getShippingInfo().ss_declared_value="0";
            order.getShippingInfo().declared_value="0.00";

        }

        if(!order.getShippingAddress().isAPO() && order.total_order_cost>=500.00f)
        {
            order.getShippingInfo().ss_proof_delivery="1";
        }
        super.saveNewOrder(order, testing);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static float getTotalSupplierCost(Order order)   throws Exception
    {
           float insurance=0.00f;
              Iterator it = order.skuList.iterator();
        while(it.hasNext())
        {
            LineItem line = (LineItem)it.next();
            Inventory invRecord = line.getInventory();
            float suppcost = invRecord.supp_cost;
            if(suppcost<0)suppcost=0.00f;
            suppcost = OWDUtilities.roundFloat(suppcost);
            insurance += suppcost*line.quantity_request;
        }

        return OWDUtilities.roundFloat(insurance);
    }
}

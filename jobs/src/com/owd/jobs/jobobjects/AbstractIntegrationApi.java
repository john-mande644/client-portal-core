package com.owd.jobs.jobobjects;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/14/12
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIntegrationApi {
private final static Logger log =  LogManager.getLogger();



    public Map<String,List<String>> shipMethodMap = new TreeMap<String,List<String>>();
    public int clientId = 0;

    public Map<String, List<String>> getShipMethodMap() {
        return shipMethodMap;
    }

    public void setShipMethodMap(Map<String, List<String>> shipMethodMap) {
        this.shipMethodMap = shipMethodMap;
    }

    public void setClientInfo(int id, Map<String,List<String>> shipMethodMap)
    {
        clientId = id;
        this.shipMethodMap = shipMethodMap;
    }

    public void setClientId(int id)
    {
        clientId = id;
    }

    abstract public void importCurrentOrders();

    public String getActualShipMethod(Order order, String oldMethod) throws Exception
    {


       if(shipMethodMap.containsKey(oldMethod.toUpperCase()))
       {
           List<String> realMethods = shipMethodMap.get(oldMethod.toUpperCase());
           return RateShopper.rateShop(order, realMethods);
       }else
       {

           order.is_future_ship=1;
           CasetrackerAPI.createCasetrackerCase("Client ID (" + order.clientID + ") order " + order.order_refnum + " received on hold", "Unable to determine ship method for method '" + oldMethod + "'; check and verify import setup",Integer.parseInt(order.clientID));
           order.hold_reason = "Unable to determine ship method for method '" + oldMethod + "'; that method is unknown to the importer. Assign to IT Work Orders if needed.";

       }



        return "USPS Priority Mail";



    }


    public boolean addLineItem(Order order, String sku, String qty, String price, String linePrice, String title, String color, String size) throws Exception {

        order.addLineItem(sku,
                qty,
                price,
                linePrice,
                title,
                color, size);

        return true;
    }

    public void doVendorComplianceStuffBeforeSavingOrder(Order order, Object orderObject) throws Exception{

    }

    public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception
    {

    }
}

package com.owd.jobs.jobobjects.RetailPro;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.jobs.jobobjects.RetailPro.models.StoreOrder;
import com.owd.jobs.jobobjects.RetailPro.models.StoreOrderShipment;
import com.owd.jobs.jobobjects.RetailPro.models.TransferOrder;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/20/12
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetailProTest {
private final static Logger log =  LogManager.getLogger();

     public static void main (String[] args) throws Exception
     {
         TransferOrder.processTransferOrders();
     }



    public static void importStoreOrders()
    {

    }

      public static void importTransferOrders()
    {

    }

}

package com.owd.jobs.jobobjects.RetailPro;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.RetailPro.models.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/23/12
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetailProHourlyJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception
    {
       // TransferOrder.processTransferOrders();
        StoreOrder.processStoreOrders();
      //  PurchaseOrder.processPurchaseOrders();

    }

    public  void internalExecute()
    {
        try
        {
       List<String> rproSkuList = InventoryManager.updateOWDInventoryFromRetailPro();

        TransferOrder.processTransferOrders();
        StoreOrder.processStoreOrders();
        PurchaseOrder.processPurchaseOrders();

        new TransferOrderShipment().reportTransferOrderShipments();
        new StoreOrderShipment().reportStoreOrderShipments();

        PurchaseOrder.reportReceives();

         }catch(Exception ex)
        {
            ex.printStackTrace();
         try{   Mailer.sendMail("Babeland Retail Pro Hourly Job Error", ex.getMessage()+"\r\n"+ OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "donotreply@owd.com");    }catch(Exception exx)
        {}
        }   finally
        {

        }

    }



}

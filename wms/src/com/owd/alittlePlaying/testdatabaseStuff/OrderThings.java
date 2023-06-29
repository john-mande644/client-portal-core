package com.owd.alittlePlaying.testdatabaseStuff;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;

import java.sql.Connection;

/**
 * Created by danny on 5/12/2017.
 */
public class OrderThings {



    public static void main(String[] args){



        System.setProperty("com.owd.environment","test");
        releaseOrder("18022941","634");
    }






    public static boolean releaseOrder(String orderId, String clientId){
        boolean success = false;
        Connection cxn = null;
        try{
            cxn = ConnectionManager.getConnection();
            System.out.println("hellO");
            OrderStatus status = new OrderStatus(orderId,clientId);
            OrderUtilities.updateLineItemsForAvailability(cxn, status.items, OrderXMLDoc.kPartialShip, false, true, FacilitiesManager.getFacilityForCode(status.getLocation()).getId());

            OrderUtilities.shipExistingOrder(cxn,status);
            success = true;
        }   catch (Exception e){
            e.printStackTrace();
        }   finally {
            try {
                cxn.close();
            }catch (Exception e){

            }
        }

        return success;
    }
}

package com.owd.OWDShippingAPI.Returns;

import com.owd.OWDShippingAPI.Models.ShipShipment;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by danny on 3/11/2020.
 */
public class ReturnUtilitiesTest {


    @Test
    public  void loadMIOrderTest(){


        try{
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20147563);
            ShipShipment shipment = ReturnUtilities.loadMIReturnInfoFromOrder(order);
            assertEquals("5634 Bandini Blvd",shipment.getShipToAddress1());
            assertEquals("Returned Goods",shipment.getShipmentDescription());

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public  void getMailInnovationsReturnLabelFromOrder(){


        try{
            System.setProperty("com.owd.shippingapi", "self");
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20147563);
            ShipShipment shipment = ReturnUtilities.loadMIReturnInfoFromOrder(order);
            assertEquals("5634 Bandini Blvd",shipment.getShipToAddress1());
            assertEquals("Returned Goods",shipment.getShipmentDescription());

            ReturnResponse rr = ReturnUtilities.getMailInnovationsReturnLabelFromOrder(order);
            System.out.println(rr.getTracking());
            assertTrue(rr.getTracking().length()>0);
            System.out.println(rr.getLabel().toString());



        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public  void generateAndMISaveLabel(){


        try{
          //  System.setProperty("com.owd.shippingapi", "self");
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,20147563);
           ReturnUtilities.generateAndMISaveLabel(order);



        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }


}

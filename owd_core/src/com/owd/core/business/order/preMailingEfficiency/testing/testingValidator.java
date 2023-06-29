package com.owd.core.business.order.preMailingEfficiency.testing;

import com.owd.core.business.order.preMailingEfficiency.PreMailingValidator;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderShipInfo;

/**
 * Created by danny on 3/20/2018.
 */
public class testingValidator {


    public static void main(String[] args){

        System.out.println("Starting test");
        testWithOrderId(14976440);

// C:\Users\eric\IdeaProjects\core\owd_core\out\production\classes
    }





    public static void testWithOrderId(Integer orderId){
        try{
            System.out.println("in test");

            OwdOrderShipInfo shipInfo = (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class,orderId);
            System.out.println("Doing: "+ shipInfo.getOrder().getOrderNum());
            PreMailingValidator.setDebug(true);

            PreMailingValidator.validate(shipInfo);




        }catch (Exception e){
            System.out.println("epic fail incoming exceptions");
            e.printStackTrace();
        }


    }
}

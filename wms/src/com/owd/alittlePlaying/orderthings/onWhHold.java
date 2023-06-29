package com.owd.alittlePlaying.orderthings;

import com.owd.hibernate.HibernateSession;

import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipHold;
import com.owd.hibernate.generated.OwdShipServiceLevel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class onWhHold {

    public static void main(String[] args) throws Exception {

//        whOnHold(23 );
//        whOnHold(17889291  );  // on hold
        whOnHold(11175353  );  // on hold


    }

    private static void whOnHold(int order_id) throws Exception {

        OwdOrder owdOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(order_id));
        String isOnHold = "";

        try{

            if (null!=owdOrder.getHoldinfo()){

                if (owdOrder.getHoldinfo().getIsOnWhHold() == 1){
                    isOnHold=" on hold";
                    System.out.println(owdOrder.getBillFirstName()+" "+owdOrder.getBillLastName()) ;
                    System.out.println(isOnHold);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

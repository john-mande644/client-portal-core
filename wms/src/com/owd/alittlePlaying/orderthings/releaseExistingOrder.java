package com.owd.alittlePlaying.orderthings;

import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 2/28/2019.
 */
public class releaseExistingOrder {





   static List<Integer> bad = new ArrayList<>();
    public static void main(String[] args){

        List<Integer> l = new ArrayList<>();


        l.add(24160069);

        /*l.add(21680366);
        l.add(21680368);
        l.add(21680369);
        l.add(21680370);

        */



        /*l.add(19685057);
        l.add(19682342);
        l.add(19685068);
        l.add(19685071);
        l.add(19685068);
        l.add(19685071);
        l.add(19685074);
        l.add(19682339);
        l.add(19685077);
        l.add(19686044);
        l.add(19686047);
        l.add(19686058);
        l.add(19686079);
        l.add(19686091);
        l.add(19686099);
        l.add(19686104);
        l.add(19686109);
        l.add(19686117);
        l.add(19686129);
        l.add(19686126);
        l.add(19686132);
        l.add(19686135);
        l.add(19686144);
        l.add(19686153);
        l.add(19686157);
        l.add(19686161);
        l.add(19686163);
        l.add(19682437);
        l.add(19682437);
        l.add(19686222);
        l.add(19686221);
        l.add(19682365);
        l.add(19686232);
        l.add(19685783);
        l.add(19685789);
        l.add(19685854);
        l.add(19685845);
        l.add(19686237);
        l.add(19682381);
        l.add(19686263);
*/


        for(Integer i:l){
         release(i);
        }

        System.out.println("Bad:::");
        for(Integer i: bad){
            System.out.println(i);
        }


    }




    public static void release(Integer id){

        try{
            Connection c = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            OrderStatus order = new OrderStatus(c,id+"");

            // option 1 backorder all
//            OrderUtilities.updateLineItemsForAvailability(c, order.items, OrderXMLDoc.kBackOrderAll, true, FacilitiesManager.getFacilityForCode(order.shipLocation).getId());

            // option 2 ship on hand and backorder out of stocks
            OrderUtilities.updateLineItemsForAvailability(c, order.items, OrderXMLDoc.kPartialShip, true, FacilitiesManager.getFacilityForCode(order.shipLocation).getId());

            // option 3 ship on hadn and ignore out of stocks
//            OrderUtilities.updateLineItemsForAvailability(c, order.items, OrderXMLDoc.kIgnoreBackOrder, true, FacilitiesManager.getFacilityForCode(order.shipLocation).getId());

            OrderUtilities.shipExistingOrder(order);


        }catch (Exception e){
            e.printStackTrace();
            bad.add(id);
        }



    }
}

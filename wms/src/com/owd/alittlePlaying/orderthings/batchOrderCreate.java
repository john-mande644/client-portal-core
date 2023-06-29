package com.owd.alittlePlaying.orderthings;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Session;

import java.sql.ResultSet;

/**
 * Created by danny on 9/30/2017.
 */
public class batchOrderCreate {


    public static void createOrders(String s) {
        try {


            Order o = new Order("488");
            o.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            o.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
            o.order_refnum = "MHE Test " + s;
            o.getBillingContact().name = "Danny Nickels";
            o.getBillingContact().email = "";
            o.getBillingContact().phone = "605-845-7172";
            o.getBillingAddress().company_name = "";
            o.getBillingAddress().address_one = "1600 Amphitheater Pkwy";
            o.getBillingAddress().address_two = "";
            o.getBillingAddress().city = "Mountain View";
            o.getBillingAddress().state = "CA";
            o.getBillingAddress().zip = "94043";
            o.getBillingAddress().country = "US";

            o.getShippingContact().name = "Danny Nickels";
            o.getShippingContact().email = "";
            o.getShippingContact().phone = "605-845-7172";
            o.getShippingAddress().company_name = "";
            o.getShippingAddress().address_one = "1600 Amphitheater Pkwy";
            o.getShippingAddress().address_two = "";
            o.getShippingAddress().city = "Mountain View";
            o.getShippingAddress().state = "CA";
            o.getShippingAddress().zip = "94043";
            o.getShippingAddress().country = "US";

            o.total_order_cost = 0.0f;
            o.total_shipping_cost = 0.0f;
            o.is_paid = 1;

            o.addLineItem("Misc", 1, 0, 0, "ITem to test shipments", "", "", "");
            o.setFacilityCode("DC6");

            System.out.println(o.saveNewOrder("Paid", false));

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        int i = 1;


        while (i < 101) {
            createOrders(i + "");
            i++;
        }


        /* try{
            OwdOrder o = getOrderFromNum(HibernateSession.currentSession(),"6015652");

           for(Object it:  o.getLineitems()) {
                 System.out.println("latida");
               OwdLineItem item = (OwdLineItem) it;

               System.out.printf("%s is the sku\n",item.getInventoryNum());


               }

        } catch (Exception e){
            e.printStackTrace();
        }*/


    }

    public static OwdOrder getOrderFromNum(Session sess, String orderNum) throws Exception {
        try {
            Integer.parseInt(orderNum);
        } catch (NumberFormatException nf) {
            throw new Exception("Invalid order num");
        }
        ResultSet rs = HibernateSession.getResultSet(sess, "select order_id from owd_order where order_num = '" + orderNum + "'");
        rs.next();

        OwdOrder o = (OwdOrder) sess.load(OwdOrder.class, Integer.valueOf(rs.getString(1)));

        return o;
    }
}

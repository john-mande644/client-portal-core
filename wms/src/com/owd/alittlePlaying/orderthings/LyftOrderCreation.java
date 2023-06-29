package com.owd.alittlePlaying.orderthings;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderFactory;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 6/15/2017.
 */
public class LyftOrderCreation {

    public static Map<String,String> orderMap = new HashMap<String, String>();

    public static void createOrders(Integer orderFkey, int Units) {
        try {

            OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(orderFkey, 529, true);
            OwdOrderShipInfo shipInfo = oorder.getShipinfo();
            

            Order o = new Order("55");
            o.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            o.getShippingInfo().setShipOptions("USPS First-Class Mail", "Prepaid", "");
            o.order_refnum = "LyftReturns1Unit " + orderFkey;
            o.getBillingContact().name = shipInfo.getShipFirstName() + " "+ shipInfo.getShipLastName();
            o.getBillingContact().email = "";
            o.getBillingContact().phone = "";
            o.getBillingAddress().company_name = shipInfo.getShipCompanyName();
            o.getBillingAddress().address_one = shipInfo.getShipAddressOne();
            o.getBillingAddress().address_two = shipInfo.getShipAddressTwo();
            o.getBillingAddress().city = shipInfo.getShipCity();
            o.getBillingAddress().state = shipInfo.getShipState();
            o.getBillingAddress().zip = shipInfo.getShipZip();
            o.getBillingAddress().country = "US";

            o.getShippingContact().name = shipInfo.getShipFirstName() + shipInfo.getShipLastName();
            o.getShippingContact().email = "";
            o.getShippingContact().phone = "";
            o.getShippingAddress().company_name = shipInfo.getShipCompanyName();
            o.getShippingAddress().address_one = shipInfo.getShipAddressOne();
            o.getShippingAddress().address_two = shipInfo.getShipAddressTwo();
            o.getShippingAddress().city = shipInfo.getShipCity();
            o.getShippingAddress().state = shipInfo.getShipState();
            o.getShippingAddress().zip = shipInfo.getShipZip();
            o.getShippingAddress().country = "US";

            o.total_order_cost = 0.0f;
            o.total_shipping_cost = 0.0f;
            o.is_paid = 1;

            o.addLineItem("LYFTREturnEnvelope", Units, 0, 0, "Return Envelope", "", "", "");
            o.setFacilityCode("DC1");

            System.out.println(o.saveNewOrder("Paid", false));
            String sql = "update zzLyftDuplicates set newOrderId = :rate, LetterSent = :units where orderFkey = :orderId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("rate",o.getID());
            q.setParameter("orderId",orderFkey);
            q.setParameter("units",Units);

            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }else{
                //bad.add(orderId);
            }
            orderMap.put(orderFkey+"",o.getID());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        int units = 6;


        List<Integer> l = new ArrayList<Integer>();

        l.add(12449725);
        l.add(12449768);
        l.add(12449771);
        l.add(12449772);
        l.add(12449776);
        l.add(12449777);
        l.add(12449778);
        l.add(12449780);
        l.add(12449781);
        l.add(12449785);
        l.add(12449788);
        l.add(12449789);
        l.add(12449790);
        l.add(12449792);
        l.add(12449793);
        l.add(12449794);
        l.add(12449795);
        l.add(12449797);
        l.add(12449799);
        l.add(12449801);
        l.add(12449802);
        l.add(12449808);
        l.add(12449811);
        l.add(12449814);
        l.add(12449815);
        l.add(12449817);
        l.add(12449819);
        l.add(12449820);
        l.add(12449821);
        l.add(12449822);
        l.add(12449826);
        l.add(12449827);
        l.add(12449830);
        l.add(12449832);
        l.add(12449833);
        l.add(12449834);
        l.add(12449836);
        l.add(12449837);
        l.add(12449840);
        l.add(12449842);
        l.add(12449843);
        l.add(12449844);
        l.add(12449845);
        l.add(12449846);
        l.add(12449853);
        l.add(12449857);
        l.add(12449858);
        l.add(12449860);
        l.add(12449863);
        l.add(12449864);
        l.add(12449865);
        l.add(12449866);
        l.add(12449870);
        l.add(12449872);
        l.add(12449874);
        l.add(12449875);
        l.add(12449877);
        l.add(12449878);
        l.add(12450043);
        l.add(12450044);
        l.add(12450045);
        l.add(12450046);
        l.add(12450048);
        l.add(12450052);
        l.add(12450053);
        l.add(12450054);
        l.add(12450055);
        l.add(12450057);
        l.add(12450060);
        l.add(12450061);


        for(Integer i : l){
            createOrders(i,units);
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

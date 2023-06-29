package com.owd.core.managers;


import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderAuto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionManager {
private final static Logger log =  LogManager.getLogger();



    static private boolean createOrderFromSubscription(OwdOrderAuto sub) {
        /*
        boolean orderCreatedSuccessfully = false;
        //todo Add company billing and shipping names here and to OwdOrderAuto
        try
        {
            Order order = new Order(sub.getClientFkey());

            order.setBillingAddress(new Address("",sub.getBillAddressOne(),sub.getBillAddressTwo(),
                    sub.getBillCity(),sub.getBillState(),sub.getBillZip(),sub.getBillCountry()));
            order.getShippingInfo().setShippingAddress(new Address("",sub.getShipAddressOne(),sub.getShipAddressTwo(),
                    sub.getShipCity(),sub.getShipState(),sub.getShipZip(),sub.getShipCountry()));
            order.setBillingContact(new Contact(sub.getBillName(),sub.getBillPhone(),"","",""));
            order.getShippingInfo().setShippingContact(new Contact(sub.getShipName(),sub.getBillPhone(),"","",""));
            //todo check Prepaid terms value
            order.getShippingInfo().setShipOptions(sub.getShipMethod(),"Prepaid","");
            order.total_tax_cost = sub.getSalesTax().floatValue();
            order.total_shipping_cost = sub.getShipCost().floatValue();

            Iterator it = sub.getOwdOrderAutoItemsArray().iterator();
            while(it.hasNext())
            {
                OwdOrderAutoItem item = (OwdOrderAutoItem) it.next();
                order.addLineItem(item.getSku(),""+item.getQuantity(),""+item.getUnitPrice(),"0.00","","","");
            }
            //todo payment data and terms

            order.saveNewOrder(Order.kHoldForPayment,false);

        }catch(Exception ex)
        {


        }

        return orderCreatedSuccessfully;
        */
        return false;
    }

    static public List getSubscriptionsForClientID(String clientID) throws Exception {
        List subs = new ArrayList();
        Session sess = null;
        try {
            sess = HibernateSession.currentSession();
            subs = sess.createQuery("from OwdOrderAuto where client_fkey = ?").setString(0,clientID).list();
            if (subs.size() == 0) {
                //log.debug("No matching subscriptions found.");
                return null;
            } else {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) sess.close();
        }

        return subs;
    }


    static public List getSubscriptionForOriginalOrderID(String orderID) throws Exception {

        List subs = new ArrayList();
        Session sess = null;
        try {
            sess = HibernateSession.currentSession();
            subs = sess.createQuery("from OwdOrderAuto where orig_order_id = ?").setString(0,orderID).list();
            if (subs.size() == 0) {
                //log.debug("No matching subscriptions found.");
                return null;
            } else {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) sess.close();
        }

        return subs;


    }

    static public List getSubscriptionForSubscriptionID(String subID, String clientID) throws Exception {


        String sqlQuery = "from OwdOrderAuto where auto_ship_id = " + subID;
        if (clientID != null) {
            sqlQuery = sqlQuery + " and client_fkey = " + clientID;
        }
        List subs = new ArrayList();
        Session sess = null;
        try {
            sess = HibernateSession.currentSession();
            subs = sess.createQuery(sqlQuery).list();
            if (subs.size() == 0) {
                //log.debug("No matching subscriptions found.");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) sess.close();
        }

        return subs;


    }

    static public List getSubscriptionForSubscriptionID(String subID) throws Exception {

        return getSubscriptionForSubscriptionID(subID, null);

    }


    public static void main(String[] args) throws Exception {
        Session sess = null;
        try {

            sess = HibernateSession.currentSession();
            OwdOrderAuto sub = new OwdOrderAuto();
            sub.setClientFkey(new Integer(99999));
            //sub.setAutoShipId(new Integer("99999"));
            sess.saveOrUpdate(sub);
            Integer id = (Integer) sess.getIdentifier(sub);
            sess.flush();
            HibUtils.commit(sess);
            sess.close();

            //log.debug("query");
            //log.debug("got list size=" + SubscriptionManager.getSubscriptionsForClientID("99999").size());
            //log.debug("done");
            //log.debug("query");
            //log.debug("got list size=" + SubscriptionManager.getSubscriptionForSubscriptionID("" + id, "99999").size());
            //log.debug("done");
            //log.debug("query");
            //log.debug("got list size=" + SubscriptionManager.getSubscriptionForSubscriptionID("" + id).size());
            //log.debug("done");
        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {
            if (sess != null) sess.close();

        }

    }


}

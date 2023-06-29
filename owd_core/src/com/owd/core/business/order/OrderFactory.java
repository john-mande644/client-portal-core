package com.owd.core.business.order;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipHold;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderFactory {
private final static Logger log =  LogManager.getLogger();


    public static OwdOrderShipHold getNewOwdOrderShipHold() {
        OwdOrderShipHold holder = new OwdOrderShipHold();
        holder.setIsOnWhHold(new Integer(0));
        holder.setNotifyAM("");
        holder.setNotifyEmail("");
        holder.setNotifyIT("");
        holder.setNotifyUser("");
        holder.setResNote("");
        holder.setResNotifyAM("");
        holder.setResNotifyEmail("");
        holder.setResNotifyPhone("");
        holder.setResNotifyUser("");
        holder.setWhHoldNotes("");
        holder.setWhHoldReason("");

        holder.setResolutionType("");
        holder.setClearedBy("");
        holder.setNeedsReview(new Integer(0));

        return holder;
    }

    public static OwdOrder getOwdOrderFromOwdReference(String owdOrderRef) throws Exception {


        try {

            Session sess = HibernateSession.currentSession();
            List orderList = sess.createQuery("from OwdOrder where order_num='" + owdOrderRef + "'").list();
            if (orderList.size() < 1) {
                owdOrderRef = "";
                throw new Exception("Order record not found for OWD reference \"" + owdOrderRef + "\"");
            }
            OwdOrder order = (OwdOrder) orderList.get(0);


            return order;
        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;
        } finally {

            // HibernateSession.closeSession();
        }


    }
    public static OwdOrder getOwdOrderFromClientReference(String clientOrderRef, String clientId) throws Exception {


        try {

            Session sess = HibernateSession.currentSession();
            List orderList = sess.createQuery("from OwdOrder where order_refnum='" + clientOrderRef + "' and client_fkey = "+clientId).list();
            if (orderList.size() < 1) {
               // clientOrderRef = "";
                throw new Exception("Order record not found for Client reference \"" + clientOrderRef + "\"");
            }
            OwdOrder order = (OwdOrder) orderList.get(0);


            return order;
        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;
        } finally {

            // HibernateSession.closeSession();
        }


    }

    public static OwdOrder getOwdOrderFromOrderID(Integer id, int clientID, boolean isInternal) throws Exception {
        try {

            Session sess = HibernateSession.currentSession();
            OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, id);
            if (order == null) throw new Exception("OwdOrder record not found for ID " + id);
            if (!isInternal && order.getClientFkey() != clientID) throw new Exception("Requested client record did not match current client context");

            return order;
        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;
        } finally {

            // HibernateSession.closeSession();
        }


    }
}

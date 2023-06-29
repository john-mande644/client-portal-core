package com.owd.core.business.autoship;

import com.owd.core.payment.FinancialTransaction;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderAuto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoShipFactory {
private final static Logger log =  LogManager.getLogger();

    public static OwdOrderAuto getNewAutoShip(int clientID) {
        OwdOrderAuto autos = new OwdOrderAuto();

        autos.setClientFkey(new Integer(clientID));
        autos.setStatus(new Integer(0));
        autos.setRequirePayment(new Integer(1));
        autos.setBillAddressOne("");
        autos.setBillAddressTwo("");
        autos.setBillCity("");
        autos.setBillCountry("USA");
        autos.setBillName("");
        autos.setBillPhone("");
        autos.setBillState("");
        autos.setBillZip("");
        autos.setBillCompany("");
        autos.setCalendarUnit("day");
        autos.setCcExpMon(new Integer(1));
        autos.setCcExpYear(new Integer(Calendar.getInstance().get(Calendar.YEAR)));
        autos.setFop(FinancialTransaction.TRANS_CC);
        autos.setCkAbaNum("");
        autos.setCkAcct("");
        autos.setCkAcctNum("");
        autos.setCkBank("");
        autos.setCkNumber("");
        autos.setCcNum("");
        autos.setSalesTax(new BigDecimal("0.00"));
        autos.setShipAddressOne("");
        autos.setShipAddressTwo("");
        autos.setShipCity("");
        autos.setShipCountry("USA");
        autos.setShipName("");
        autos.setShipCompany("");
        autos.setShipPhone("");
        autos.setShipState("");
        autos.setShipZip("");
        autos.setShipCost(new BigDecimal("0.00"));
        autos.setShipInterval(new Integer(1));
        autos.setShipMethod("USPS Priority Mail");
        autos.setBillEmail("");
        autos.setShipEmail("");

        Calendar expected = Calendar.getInstance();
        expected.add(Calendar.DATE, 1);
        autos.setNextShipmentDate(expected.getTime());

        autos.setCreated(Calendar.getInstance().getTime());
        autos.setOwdOrderAutoHistories(new ArrayList());
        autos.setOwdOrderAutoItems(new ArrayList());

        autos.setCreatedOrders(new HashSet());
        autos.setSourceOrders(new HashSet());


        return autos;
    }


    public static OwdOrderAuto getAutoshipFromAutoShipID(Integer id, int clientID, boolean isInternal) throws Exception {
        try {

            Session sess = HibernateSession.currentSession();
            OwdOrderAuto autos = (OwdOrderAuto) sess.load(OwdOrderAuto.class, id);
            int i = autos.getOwdOrderAutoHistories().size();
            i = autos.getCreatedOrders().size();
            i=autos.getSourceOrders().size();
            i=autos.getOwdOrderAutoItems().size();
            
            if (autos == null) throw new Exception("Subscription record not found for ID " + id);
            if (!isInternal && autos.getClientFkey() != clientID) throw new Exception("Requested subscription record did not match current client context");
            //init collection
          

            return autos;
        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;
        } finally {

         //   // HibernateSession.closeSession();
        }


    }
}

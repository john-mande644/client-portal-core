package com.owd.core.business.asn;

import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.generated.AsnItem;
import com.owd.hibernate.generated.Receive;
import com.owd.hibernate.generated.ReceiveItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASNFactory {
private final static Logger log =  LogManager.getLogger();

    public static Asn getNewAsn(int clientID) throws Exception {
        Asn asn = new Asn();

        asn.setClientFkey(clientID);
        asn.setStatus(0);
        asn.setCarrier("Unknown");
        asn.setCartonCount(0);
        asn.setPalletCount(0);
        asn.setClientPo("");
        asn.setClientRef("");
        asn.setCreatedBy("System");
        asn.setIsAutorelease(0);
        asn.setNotes("");
        asn.setShipperName("");
        asn.setHasBlind(0);
        asn.setIsAutorelease(1);
        asn.setReceiveCount(0);
        asn.setStatus(0);
        Calendar expected = Calendar.getInstance();
        expected.add(Calendar.DATE, 1);
        asn.setExpectDate(expected.getTime());
        asn.setAsnItems(new ArrayList());
        asn.setFacilityCode(FacilitiesManager.getLocationCodeForClient(clientID));
        asn.setOwdLabels(0);


        return asn;
    }
    public static Receive getNewPendingReceive(Asn asn)
    {
         Receive rcv = getNewReceive(asn);
        rcv.setCreatedBy("PENDING");
        return rcv;
    }
    public static Receive getNewReceive(Asn asn) {
        Receive rcv = new Receive();

        rcv.setClientFkey(asn.getClientFkey());
        rcv.setAsn(asn);
        rcv.setBilledMinutes(0);
        rcv.setCartonCount(0);
        rcv.setPalletCount(0);
        rcv.setClientFkey(asn.getClientFkey());
        rcv.setCreatedOn(Calendar.getInstance().getTime());
        rcv.setCreatedBy("");//asn.setId(new Integer(0));        //proper unsaved-value
        rcv.setEndTimestamp(Calendar.getInstance().getTime());
        rcv.setStartTimestamp(Calendar.getInstance().getTime());
        rcv.setIsAsnFound(0);
        rcv.setIsPackSlipFound(0);
        rcv.setIsPackSlipMatch(0);
        rcv.setIsPosted(0);
        rcv.setNotes("");
        rcv.setPalletCount(0);
        rcv.setSkuCount(0);
        rcv.setUnitCount(0);
        rcv.setReceiveBy("");
        rcv.setFacilityCode(asn.getFacilityCode());

        rcv.setReceiveItems(new ArrayList());
        Iterator it = asn.getAsnItems().iterator();
        while (it.hasNext()) {
            AsnItem item = (AsnItem) it.next();
            ReceiveItem ritem = new ReceiveItem();
            ritem.setAsnItem(item);
            ritem.setInventoryFkey(item.getInventoryFkey());
            ritem.setNotes("");
            ritem.setQtyDamage(0);
            ritem.setQtyReceive(0);
            ritem.setQtyPackslip(0);
            ritem.setReceive(rcv);
            ritem.setToLocation("UNKNOWN");

            rcv.getReceiveItems().add(ritem);


        }

        return rcv;
    }


    public static Asn getAsnFromAsnID(Integer id, int clientID, boolean isInternal) throws Exception {
        try {

            Session sess = HibernateSession.currentSession();
            Asn asn = (Asn) sess.load(Asn.class, id);
            if (asn == null) throw new Exception("ASN not found for ID " + id);
            if (!isInternal && asn.getClientFkey() != clientID) throw new Exception("Requested ASN did not match current client context");
            //init collection

            return asn;
        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;
        } finally {

          //  // HibernateSession.closeSession();
        }


    }
}

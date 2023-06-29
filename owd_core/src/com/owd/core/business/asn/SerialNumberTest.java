package com.owd.core.business.asn;

import com.owd.core.managers.SerialNumberManager;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventorySerial;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.ReceiveItem;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 6, 2006
 * Time: 4:04:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SerialNumberTest

        extends TestCase {
private final static Logger log =  LogManager.getLogger();

    public SerialNumberTest(String test) {
        super(test);
    }

    protected void setUp() throws Exception {
        //log.debug("testSetup");

        Session sess = HibernateSession.currentSession();

        try {

            //    sess.delete("from ReceiveItem");

            //  sess.delete("from OwdInventorySerial");

            //  //log.debug("Delete Done");

            //  sess.delete("from ReceiveItem");

            //  sess.delete("from Receive");

            //  sess.delete("from AsnItem");
            //       sess.delete("from Asn");


            sess.flush();
            HibUtils.commit(sess);


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            // HibernateSession.closeSession();
        }

    }

    protected void tearDown() throws Exception {


    }

    public OwdInventorySerial saveOrUpdateSerialNumber(OwdInventory inv, String serial) {
        return null;
    }


    public void testSaveNewSerialNumber() throws Exception {

        Session sess = com.owd.hibernate.HibernateSession.currentSession();
        sess.delete("from OwdInventorySerial where serial_number='12345'");
        sess.flush();
        HibUtils.commit(sess);

        OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, new Integer(29));
        if (inv.getRequireSerialNumbers() == 1) {

            OwdInventorySerial serial = SerialNumberManager.loadOrCreateSerialNumber(sess, inv, "12345");
            //log.debug("serial:" + serial);


        } else {
            throw new Exception("Inventory item 29 does not require serial numbers!");
        }


        try {

            sess.flush();
            HibUtils.commit(sess);
            //  ASNManager.saveOrUpdateASN(asn);

            OwdInventorySerial serial = SerialNumberManager.loadOrCreateSerialNumber(sess, inv, "12345");
            //log.debug("serial:" + serial);

        } finally {
            // HibernateSession.closeSession();
        }

    }

    public void testSaveDuplicateSerialNumber() throws Exception {

        Session sess = com.owd.hibernate.HibernateSession.currentSession();


        sess.delete("from OwdInventorySerial where serial_number='12345'");
        sess.flush();
        HibUtils.commit(sess);

        OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, new Integer(29));
        try {
            OwdInventorySerial serial = new OwdInventorySerial();
            serial.setSerialNumber("12345");
            serial.setCreated(Calendar.getInstance().getTime());
            serial.setOwdInventory(inv);
            inv.getSerialNumbers().add(serial);

            sess.save(serial);


            sess.flush();
            HibUtils.commit(sess);

            serial = new OwdInventorySerial();
            serial.setSerialNumber("12345");
            serial.setCreated(Calendar.getInstance().getTime());
            serial.setOwdInventory(inv);
            inv.getSerialNumbers().add(serial);

            sess.save(serial);
            sess.flush();
            HibUtils.commit(sess);
            //  ASNManager.saveOrUpdateASN(asn);

            throw new Exception("Failed!");
        } catch (Exception ex) {
            //log.debug("caught duplicate serial insert!");
        }
        finally

        {
            // HibernateSession.closeSession();
        }

    }

    public void testAddSerialNumberToReceiveItem() throws Exception {

        Session sess = com.owd.hibernate.HibernateSession.currentSession();


        sess.delete("from OwdInventorySerial");
        sess.delete("from ReceiveSerial");
        sess.flush();
        HibUtils.commit(sess);

        try {

            ReceiveItem rcv = (ReceiveItem) sess.load(ReceiveItem.class, new Integer(123));
            SerialNumberManager.addSerialNumberToReceiveItem(sess, rcv, "12345");
            SerialNumberManager.addSerialNumberToReceiveItem(sess, rcv, "12346");


            sess.flush();
            HibUtils.commit(sess);

            sess.refresh(rcv);

            if (rcv.getSerials().size() != 1) throw new Exception("serial-receiveitem link not saved!");

        }
        finally

        {
            // HibernateSession.closeSession();
        }

    }


    public void testAddSerialNumberToLineItem() throws Exception {

        Session sess = com.owd.hibernate.HibernateSession.currentSession();

         sess.delete("from OwdInventorySerial");
        sess.delete("from OwdLineSerialLink");
        sess.flush();
        HibUtils.commit(sess);


        try {
               OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, new Integer(1537986));

            OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, new Integer(101505));

            SerialNumberManager.pickSerialNumber(sess, order,inv, "12345");
            SerialNumberManager.pickSerialNumber(sess, order,inv, "12346");


            sess.flush();
            HibUtils.commit(sess);

        //    sess.refresh(rcv);

         //   if (rcv.getSerials().size() != 1) throw new Exception("serial-receiveitem link not saved!");
            //  ASNManager.saveOrUpdateASN(asn);

        }
        finally

        {
            // HibernateSession.closeSession();
        }

    }

}

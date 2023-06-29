package com.owd.core.managers;

import com.owd.hibernate.*;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 13, 2006
 * Time: 2:17:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SerialNumberManager {
private final static Logger log =  LogManager.getLogger();

    public static void pickSerialNumber(Session sess, OwdOrder order, OwdInventory inv, String serialNum) throws Exception {

        int invid = inv.getInventoryId().intValue();
        boolean foundLine = false;

        Iterator it = order.getLineitems().iterator();
        while (it.hasNext() && !foundLine) {
            OwdLineItem li = (OwdLineItem) it.next();
            if (li.getOwdInventory().getInventoryId().intValue() == invid) {
                OwdInventorySerial serial = SerialNumberManager.loadOrCreateSerialNumber(sess, li.getOwdInventory(), serialNum);

                //  //log.debug("qty=" + li.getQuantityActual());
                //  //log.debug(li.getSerials());
                if (li.getQuantityActual().intValue() > li.getSerials().size()) {
                    //foundone!
                    foundLine = true;
                    if (li.getSerials().contains(serial)) {
                        throw new Exception("Duplicate serial number - cannot add " + serialNum + " to line item");
                    }

                    li.getSerials().add(serial);

                    if (serial.getLineItems() == null) {
                        serial.setLineItems(new HashSet());
                    }

                    serial.getLineItems().add(li);
                    sess.save(serial);
                    sess.save(li);


                }
            }

        }

        if (!foundLine) {
            throw new Exception("No matching lines found for item!");
        }
    }

    public static void clearSerialNumberForPickedOrder(Session sess, OwdOrder Order) throws Exception {
        Iterator it = Order.getLineitems().iterator();
        while (it.hasNext()) {
            OwdLineItem li = (OwdLineItem) it.next();
            if (li.getSerials().size() > 0) {
                Set s = li.getSerials();
                li.getSerials().removeAll(s);
                sess.save(li);
            }
        }
    }


    public static void addSerialNumberToReceiveItem(Session sess, ReceiveItem rcv, String serialNum) throws Exception {

        OwdInventorySerial serial = SerialNumberManager.loadOrCreateSerialNumber(sess,
                (OwdInventory) sess.load(OwdInventory.class, new Integer(rcv.getInventoryFkey()), LockOptions.READ), serialNum);

        if (rcv.getSerials().contains(serial)) {
            throw new Exception("Duplicate serial number - cannot add " + serialNum + " to receive item");
        }
        rcv.getSerials().add(serial);

        if (serial.getReceiveItems() == null) {
            serial.setReceiveItems(new HashSet());
        }

        serial.getReceiveItems().add(rcv);
        sess.save(serial);
        sess.save(rcv);


    }

    public static boolean serialExists(OwdInventory inv, String serial) throws Exception {
        boolean serialExists = false;

        PreparedStatement stmt = HibernateSession.getPreparedStatement("select count(*) from owd_inventory_serial (NOLOCK) where serial_number=? and inventory_fkey=?");
        stmt.setString(1, serial);
        stmt.setInt(2, inv.getInventoryId().intValue());

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            serialExists = rs.getInt(1) > 0;
        }
        HibernateSession.closePreparedStatement();

        return serialExists;
    }

    static public void main (String[] args)
    {
        try
        {

        Session sess = HibernateSession.currentSession();


        OwdInventorySerial serial = loadOrCreateSerialNumber(sess,
                (OwdInventory) sess.load(OwdInventory.class, new Integer(109466)),
        "test001");


        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static OwdInventorySerial loadOrCreateSerialNumber(Session sess, OwdInventory inv, String serialNum) throws Exception {

        OwdInventorySerial serial = null;

        if (serialExists(inv, serialNum)) {

            serial = (OwdInventorySerial) sess.createCriteria(OwdInventorySerial.class)
                    .add(Restrictions.eq("serialNumber", serialNum))
                    .createCriteria("owdInventory").add(Restrictions.eq("inventoryId", inv.getInventoryId()))
                    .list().get(0);

////log.debug("found serial");

        } else {

            String sql = "insert into owd_inventory_serial(serial_number,created,inventory_fkey) VALUES(?,getdate(),?)";
            PreparedStatement ps = HibernateSession.getPreparedStatement(sql);
            ps.setString(1,serialNum);
            ps.setInt(2,inv.getInventoryId().intValue());
            ps.executeUpdate();
            sess.flush();
            HibUtils.commit(sess);


          //  sess.save(serial);

            serial = (OwdInventorySerial) sess.createCriteria(OwdInventorySerial.class)
                    .add(Restrictions.eq("serialNumber", serialNum))
                    .createCriteria("owdInventory").add(Restrictions.eq("inventoryId", inv.getInventoryId()))
                    .list().get(0);
////log.debug("created serial");
        }


        return serial;

    }


}

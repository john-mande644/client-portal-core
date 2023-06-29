package com.owd.dc.picking;

import com.owd.core.Mailer;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.dbUtilities;
import com.owd.core.business.order.Event;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.SerialNumberManager;
import com.owd.hibernate.*;
import com.owd.dc.picking.beans.pickItemBean;
import com.owd.dc.picking.forms.holdForm;
import com.owd.dc.setup.emailList;
import com.owd.dc.HandheldUtilities;
import com.owd.hibernate.generated.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 29, 2004
 * Time: 9:02:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class PickUtilities {
    public static final int kStatusPicking = 1;
    public static final int kStatusPicked = 2;
    public static final int kStatusUnpicked = 0;
    public static final String kActionTypeTag = "fxn";


    public static void skipCurrentLine(int orderPickFkey, int pickItemId) throws Exception {

        String sql = "execute sp_skipCurrentPickingLine :orderPickFkey, :pickItemId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);

        q.setParameter("orderPickFkey", orderPickFkey);
        q.setParameter("pickItemId", pickItemId);

        int i = q.executeUpdate();
        if (i > 0) {
            HibUtils.commit(HibernateSession.currentSession());
        } else {
            throw new Exception("Error Skipping line");
        }


    }

    public static void saveCurrentPickStatus(OrderPickStatus pstat, HttpServletRequest req) throws Exception {


        System.out.println("saving old order status:" + req.getAttribute("loginName"));
        pstat.setPickerName((String) req.getAttribute("loginName"));
        HibernateSession.currentSession().save(pstat);
        Iterator it = pstat.getOrderPickItems().iterator();
        while (it.hasNext()) {
            System.out.println("saving order pick item");
            HibernateSession.currentSession().save((OrderPickItem) it.next());
        }

        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());


        //req.getSession(true).setAttribute("currpickorder", pstat);
    }

    public static void deletePickStatus(OrderPickStatus pstat) throws Exception {


        // System.out.println("clearing old order status:" + req.getAttribute("loginName"));
        //reload to make sure we are in the same session


        Iterator it = pstat.getOrderPickItems().iterator();
        while (it.hasNext()) {
            OrderPickItem item = (OrderPickItem) it.next();
            // System.out.println("deleting item " + item.getId() + " : " + item.getSku());
            HibernateSession.currentSession().delete(item);
        }
        HibernateSession.currentSession().delete(pstat);

        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
    }

    public static void clearCurrentPickStatus(OrderPickStatus pstat, HttpServletRequest req) throws Exception {


        System.out.println("clearing old order status:" + req.getAttribute("loginName"));

        deletePickStatus(pstat);
        clearSessionPickStatus(req);
    }

    public static void clearSessionPickStatus(HttpServletRequest req) {
        // req.getSession(true).setAttribute("currpickorder", null);
        req.getSession(true).setAttribute("itemdisptime", null);
    }
    //todo change to pull from db!

    static public OrderPickStatus getCurrentPickStatus(HttpServletRequest req) throws Exception {

        //todo also clear out any pickorderstatus records for the same ordernum as the pstat returned from here...

        System.out.println("cking for old order status:" + req.getAttribute("loginName"));
        String user = (String) req.getAttribute("loginName");
        Session sess = HibernateSession.currentSession();

        Criteria crit = sess.createCriteria(OrderPickStatus.class);
        crit.setMaxResults(1000);
        crit.add(Expression.eq("pickerName", user));

        List orderList = crit.list();

        System.out.println("got " + orderList.size() + " orders");

        if (orderList.size() > 0) {
            for (int i = (orderList.size() - 1); i > 0; i--) {

                System.out.println("deleting old order status:" + req.getAttribute("loginName"));
                PickUtilities.clearCurrentPickStatus((OrderPickStatus) orderList.get(i), req);
            }
            sess.flush();
            com.owd.hibernate.HibUtils.commit(sess);
            return (OrderPickStatus) orderList.get(0);
        } else {
            return null;
        }


        //  return (OrderPickStatus) req.getSession(true).getAttribute("currpickorder");
    }


    public static void updateSessionPickStatusFromOld(HttpServletRequest req, OrderPickStatus oldStatus) {
        //req.getSession(true).setAttribute("currpickorder", oldStatus);
    }

    public static boolean markPickComplete(HttpServletRequest req, OrderPickStatus pstat, boolean isHold) throws Exception {
        boolean orderDone;
        orderDone = true;
        //update pick and order records
        Calendar cal = Calendar.getInstance();
        pstat.setEndTime(cal.getTime());
        HibernateSession.currentSession().flush();
        //Connection conn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        String sqlQuery = new String();
        String pickname = new String();
        // Statement stmt = conn.createStatement();
        Statement stmt = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().createStatement();
        pickname = (String) req.getAttribute("loginName");
        if (pickname.indexOf(" ") > 0) {
            pickname = pickname.substring(0, pickname.indexOf(" "));
        }
        //do only if not a verify
        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        int verify = pstat.getIsVerify().intValue();
        if (verify == 0) {
            if (pstat.getLicensePlate().length() > 0) {
                sqlQuery = "update owd_order set pick_by = '" + req.getAttribute("loginName") + "', pick_status=" + kStatusPicked + ",license_plate = '" + pstat.getLicensePlate() + "' where pick_status=" + kStatusPicking + " and order_num = '" + pstat.getOrderNum() + "'";

            } else {
                sqlQuery = "execute sp_setPickedBy '" + req.getAttribute("loginName") + "', '" + pstat.getOrderNum() + "'";
                //  sqlQuery = "update owd_order set pick_by = '" + req.getAttribute("loginName") + "', pick_status=" + kStatusPicked + " where pick_status=" + kStatusPicking + " and order_num = '" + pstat.getOrderNum() + "'";
            }
            int rows = stmt.executeUpdate(sqlQuery);
            //   if (rows < 1)
            //       throw new Exception("Unable to update order pick status");

            stmt.close();

            //   HibernateSession.currentSession().flush();
            //   com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
           /* stmt = conn.createStatement();
            sqlQuery = "insert into pick_events (picker,pick_end,order_num) values ( '*" + req.getAttribute("loginName") + "*',1,'" + pstat.getOrderNum() + "')";

            rows = stmt.executeUpdate(sqlQuery);
            stmt.close();

                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());*/

            // stmt = conn.createStatement();
            stmt = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().createStatement();
            sqlQuery = "insert into order_events (order_fkey,event_type,message,creator) values (" + pstat.getOrderId() + "," + Event.kEventTypeHandling + ",'Order picked complete by " + pickname + "','DC-MOB-1')";

            rows = stmt.executeUpdate(sqlQuery);
            stmt.close();
            //   HibernateSession.currentSession().flush();
            //   com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            if (pstat.getDcHold() == 1 || isHold == true) {
                System.out.println("setting hold");
                WarehouseHoldUtilities.setPostedOrderOnHold(pstat, req);
            }
            try {

                transferToDatabase(pstat, req);
            } catch (Exception ex) {
                //conn.rollback();
                ex.printStackTrace();
                HibUtils.rollback(HibernateSession.currentSession());
                throw new Exception("ERROR!!  Please Rescan last Item");
            }
            System.out.println("after transfer");

        } else {
            //stmt = conn.createStatement();
            stmt = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().createStatement();
            sqlQuery = "insert into order_events (order_fkey,event_type,message,creator) values (" + pstat.getOrderId() + "," + Event.kEventTypeHandling + ",'Pick Verified by " + pickname + "','System')";
            int rows = stmt.executeUpdate(sqlQuery);
            stmt.close();
            // HibernateSession.currentSession().flush();
            //     com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            //  stmt = conn.createStatement();
            stmt = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().createStatement();
            sqlQuery = "update owd_order set pick_status=" + kStatusPicked + " where pick_status=" + kStatusPicking + " and order_num = '" + pstat.getOrderNum() + "'";
            rows = stmt.executeUpdate(sqlQuery);
            stmt.close();
            // HibernateSession.currentSession().flush();
            //   com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            //  stmt = conn.createStatement();
            stmt = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().createStatement();
            sqlQuery = "insert into order_verify_history(order_fkey, verify_by, verify_date) values (" + pstat.getOrderId() + ",'" + pstat.getPickerName() + "','" + pstat.getStartTime() + "')";
            rows = stmt.executeUpdate(sqlQuery);
            stmt.close();

            //    HibernateSession.currentSession().flush();
            //    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            //conn.commit();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        }

        clearCurrentPickStatus(pstat, req);


        return orderDone;
    }

    public static void cancelPickByOrderNum(HttpServletRequest req, String orderNum) throws Exception {
        Connection conn = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        Statement stmt = conn.createStatement();
        String sqlQuery = new String();
        //gets verify value in OrdrPickStatus for canceling.  if error, it is force repick
        try {
            OrderPickStatus pstat = getPickStatusByOrderNumber(orderNum);
            // set pick_status unpicked if cancel normal pick, sets to Picked if cancel is a verify
            if (pstat.getIsVerify().intValue() == 0) {
                System.out.println("in equals 0");
                sqlQuery = "update owd_order set pick_by = '', pick_status=" + kStatusUnpicked + " where order_num = '" + orderNum + "'";
            } else {

                sqlQuery = "update owd_order set pick_status=" + kStatusPicked + " where order_num='" + orderNum + "'";
            }
        } catch (Exception e) {
            sqlQuery = "update owd_order set pick_by = '', pick_status=" + kStatusUnpicked + " where order_num = '" + orderNum + "'";
        }
        int rows = stmt.executeUpdate(sqlQuery);
        // if (rows < 1)
        //     throw new Exception("Problem with server - Please try again.");


        String orderId = dbUtilities.getOrderKeyID(((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), orderNum);
        stmt.executeUpdate("delete from order_verify_history where order_fkey = " + orderId);
        stmt.close();
        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        stmt = conn.createStatement();
        sqlQuery = "insert into pick_events (picker,pick_cancel,order_num) values ( '" + req.getAttribute("loginName") + "',1,'*" + orderNum + "*')";

        rows = stmt.executeUpdate(sqlQuery);
        stmt.close();

        conn.commit();
    }

    public static void transferToDatabase(OrderPickStatus pstat, HttpServletRequest req) throws Exception {
        System.out.println("Starting Transfer to database");
        int pickReplenish = 0;
        //  Calendar cal = Calendar.getInstance();
        // pstat.setEndTime(cal.getTime());
        // HibernateSession.currentSession().flush();

        System.out.println("yowza");
        HibernateSession.currentSession().refresh(pstat);
        //check for previous pick and load history
        String findOrderSQL = "select id from order_pick_history (NOLOCK) where order_id =" + pstat.getOrderId();
        ResultSet rs = HibernateSession.getResultSet(findOrderSQL);

        //    HibernateSession.currentSession().flush();
        //     com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        OrderPickHistory phist = null;
        System.out.println("right before save1");
        if (rs.next()) {
            phist = (OrderPickHistory) HibernateSession.currentSession().load(OrderPickHistory.class, new Integer(rs.getInt(1)));
            Iterator it = phist.getOrderPickItemHist().iterator();
            while (it.hasNext()) {
                OrderPickItemHistory item = (OrderPickItemHistory) it.next();
                System.out.println("deleting item " + item.getId() + " : " + item.getId());
                HibernateSession.currentSession().delete(item);
            }
        } else {
            phist = new OrderPickHistory();
            phist.setDcHold(0);
            phist.setNumberPicks(0);
            phist.setOrderId(0);
            phist.setOrderReplenishTime(0);

        }
        System.out.println("right before save2");
        phist.setOrderId(pstat.getOrderId());
        System.out.println("right before save3");
        phist.setPickBy(pstat.getPickerName());
        System.out.println("right before save4");
        phist.setNumberPicks(phist.getNumberPicks() + 1);
        System.out.println("right before save5");
        phist.setStartPickTime(pstat.getStartTime());
        System.out.println("right before save6");
        phist.setEndPickTime(pstat.getEndTime());
        System.out.println("right before save7");
        HibernateSession.currentSession().save(phist);

        //    HibernateSession.currentSession().flush();
        //     com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        //loop through items and add to database
        try {
            for (int i = 0; i < pstat.getOrderPickItems().size(); i++) {
                OrderPickItem currItem = (OrderPickItem) pstat.getOrderPickItems().toArray()[i];

                OrderPickItemHistory pitemhist = new OrderPickItemHistory();
                pitemhist.setAllUnitsPicked(0);
                pitemhist.setDcHold(0);
                pitemhist.setFirstScanTime(0);
                pitemhist.setReplenishTime(0);
                pitemhist.setUnitsPicked(0);
                pitemhist.setOrderPickHist(phist);
                System.out.println(currItem.getStartTime() + " Start Time");
                System.out.println(currItem.getFirstPickTime());
                long start = 0;
                boolean error = false;
                try {
                    start = (currItem.getStartTime().getTime());
                } catch (Exception ex) {
                    System.out.println("Transfer Error Level 1");
                    try {
                        start = currItem.getFirstPickTime().getTime();
                    } catch (Exception e2) {
                        System.out.println("Transfer Error Level 2");
                        error = true;
                    }
                }
                if (!error) {
                    long first = (currItem.getFirstPickTime().getTime());
                    System.out.println(start);
                    System.out.println(first);
                    pitemhist.setFirstScanTime((int) (Math.round(first - start) / 1000));
                    pitemhist.setAllUnitsPicked((int) (Math.round(currItem.getEndTime().getTime() - start) / 1000));
                } else {
                    pitemhist.setFirstScanTime(0);
                    pitemhist.setAllUnitsPicked(0);
                }
                String invid = String.valueOf(currItem.getItemBarcode());
                pitemhist.setInventoryId(invid);
                pitemhist.setUnitsPicked(currItem.getQtyPicked());
                pitemhist.setReplenishTime(currItem.getReplenishTime());
                //add replenish time to pickReplenish for order pick history when done
                pickReplenish += currItem.getReplenishTime();
                System.out.println(pickReplenish);
                HibernateSession.currentSession().save(pitemhist);
                insertSerialNumbers(currItem);
                //   HibernateSession.currentSession().flush();
                //    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            }
            phist.setOrderReplenishTime(pickReplenish);
            //  HibernateSession.currentSession().flush();
            //  com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());

        }
    }

    public static void insertSerialNumbers(OrderPickItem curritem) throws Exception {
       /* if ("1".equals(curritem.getIsSerialized().toString())) {
            System.out.println("in serials euals 1");
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(curritem.getOrderPickStatu().getOrderId()));
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(curritem.getItemBarcode()));

            String[] serials = curritem.getSerials().split(",");
            for (int i = 0; i < serials.length; i++) {
                SerialNumberManager.pickSerialNumber(HibernateSession.currentSession(), order, inv, serials[i]);
            }
        }*/
    }

    public static OrderPickStatus getPickStatusByOrderNumber(String orderNum) throws Exception {
        String getId = "select id from OrderPickStatus (NOLOCK) where order_num = '" + orderNum + "'";
        ResultSet rs = HibernateSession.getResultSet(getId);
        System.out.println("after query");
        rs.next();
        System.out.println("rsnext");
        OrderPickStatus pstat = (OrderPickStatus) HibernateSession.currentSession().load((OrderPickStatus.class), new Integer(rs.getString(1)));
        return pstat;
    }

    public static String orderAlreadyVerified(int orderNum) throws Exception {
        System.out.println(orderNum);
        Connection conn = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        Statement stmt = conn.createStatement();

        String sqlQuery = "select verify_by from order_verify_history (NOLOCK) where order_fkey=" + orderNum;
        ResultSet rs1 = stmt.executeQuery(sqlQuery);
        if (rs1.next()) {
            System.out.println("in rs if");
            String name = rs1.getString(1);
            return name;
        }
        System.out.println("after name");
        rs1.close();
        return "none";
    }

    public static pickItemBean loadPickItemBean(OrderPickStatus picker, String facility, String priority) throws Exception {
        if (priority.length() == 0) {
            priority = "1";
        }

        pickItemBean info = new pickItemBean();
        // OrderPickItem currItem = (OrderPickItem) picker.getOrderPickItems().toArray()[picker.getCurrentItemIndex()];

        Integer index = new Integer(picker.getCurrentItemIndex());
        Session sess = HibernateSession.currentSession();
        Criteria crit = sess.createCriteria(OrderPickItem.class);
        crit.setMaxResults(1000);
        crit.add(Expression.eq("indexId", index));
        crit.add(Expression.eq("orderPickStatu", picker));
        List itemList = crit.list();
        System.out.println("got " + itemList.size() + " items for this id");
        OrderPickItem currItem = (OrderPickItem) itemList.get(0);

        System.out.println("after Orderpickitem");
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(currItem.getItemBarcode()));
        info.setOrderNum(picker.getOrderNum());
        if (picker.getRedOrder().equals(1)) {
            info.setRedOrder(true);

        } else {
            info.setRedOrder(false);
        }
        System.out.println("order Num " + picker.getOrderNum());
        try {
            info.setCaseQty(inv.getCaseQty());
        } catch (Exception e) {
            info.setCaseQty(0);
        }
        info.setOrderId(picker.getOrderId() + "");
        info.setLicense(picker.getLicensePlate());
        info.setCurrentItem(picker.getCurrentItemIndex());
        info.setItemSize(picker.getOrderPickItems().size());
        info.setBarcode(currItem.getItemBarcode());
        info.setQtyPicked(currItem.getQtyPicked());
        info.setQtyToPick(currItem.getQtyToPick());
        info.setOnHand(OrderUtilities.getAvailableInventory(inv.getInventoryId().toString(), FacilitiesManager.getFacilityForCode(facility).getId()));
        info.setSku(currItem.getSku());
        info.setDescription(currItem.getSkuDesc());
        info.setPickItemId(currItem.getId().intValue());
        info.setPickStatusId(picker.getId().intValue());
        info.setImageThumb(HandheldUtilities.getThumbUrlTag(inv.getImageThumbUrl(), inv.getImageUrl(), inv.getInventoryId().toString(), HandheldUtilities.pickurl));
        // info.setImageThumb(inv.getImageThumbUrl());
        info.setImageUrl(HandheldUtilities.getImageUrlTag(inv.getImageUrl()));
        if (info.getCaseQty() > 0) {
            System.out.println("Set the case stuff");
            info.setQtyLeft(info.getQtyToPick() - info.getQtyPicked());
            info.setCasePick(info.getQtyLeft() / info.getCaseQty());
            info.setEachPick(info.getQtyLeft() % info.getCaseQty());
            System.out.println(info.getQtyLeft());
            System.out.println(info.getCasePick());
            System.out.println(info.getEachPick());
        }
        if (inv.getUpcCode().length() > 0) {
            info.setUPC(inv.getUpcCode());
        } else {
            info.setUPC("");
        }
        if (inv.getIsbnCode().length() > 0) {
            info.setISBN(inv.getIsbnCode());
        } else {
            info.setISBN("");
        }
        String sql = "execute getLocationsPrioritySortedAll :id, :facility, :priority";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        q.setParameter("id", info.getBarcode());
        q.setParameter("facility", facility);
        q.setParameter("priority", priority);
        List results = q.list();
        if (results.size() > 0) {
            Map data = (Map) results.get(0);
            info.setLocFirst(data.get("formatedPickString").toString());
            List locs = new ArrayList();
            for (Object row : results) {
                Map dd = (Map) row;
                locs.add(dd.get("formatedPickString"));

            }
            info.setLocList(locs);

        } else {
            info.setLocFirst("UnKnoWn");
            info.setLocList(new ArrayList());
        }
       /* String[] locs = currItem.getLocList().split("\r");
        List locList = new ArrayList();
        for (int i = 1; i < locs.length; i++) {
            locList.add(locs[i]);
        }
        info.setLocList(locList);

        try{
        info.setLocFirst(locs[0]);
        }catch (Exception e){
            try{
                if(currItem.getDefaultLoc().length()==0) {
                    info.setLocFirst("UnKnOwN");
                }  else{
                    info.setLocFirst(currItem.getDefaultLoc());
                }

            } catch(Exception E){
                info.setLocFirst("UnKnOwN");
            }

        }*/

        info.setVerify(picker.getIsVerify().intValue());
        info.setClientFkey(picker.getClientFkey().intValue());
        return info;
    }

    public static holdForm loadHoldForm(pickItemBean pickItem, String user) throws Exception {
        holdForm holdForm = new holdForm();
        System.out.println("in 1");
        holdForm.setInventoryId(pickItem.getBarcode() + "");
        System.out.println("in 2");
        holdForm.setInventoryNum(pickItem.getSku());
        System.out.println("in 3");
        String loc = pickItem.getLocFirst();
        List locs = pickItem.getLocList();
        System.out.println(locs.size());
        for (int i = 0; i < locs.size(); i++) {

            loc = loc + ", " + locs.get(i);

        }
        holdForm.setLocations(loc);
        System.out.println(holdForm.getLocations());
        holdForm.setOnHand(pickItem.getOnHand() + "");
        holdForm.setPickItemId(pickItem.getPickItemId() + "");
        holdForm.setUser(user);
        holdForm.setOrderNum(pickItem.getOrderNum());
        return holdForm;
    }

    public static void insertReplenishTime(int seconds, int pickItemId) throws Exception {
        OrderPickItem item = (OrderPickItem) HibernateSession.currentSession().load(OrderPickItem.class, new Integer(pickItemId));
        item.setReplenishTime(item.getReplenishTime() + seconds);
        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
    }

    public static void sendStockOutEmail(OrderPickStatus pstat, pickItemBean pitem, String loc, String Facility) throws Exception {
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(pitem.getBarcode()));
        String subject = "Stock out for " + loc + " reported by " + pstat.getPickerName();
        String body = "Location: " + loc + "\r\nClient: " + inv.getOwdClient().getCompanyName() + "\r\nInventory Number: " + pitem.getSku() + "\r\nInventory Id: " +
                pitem.getBarcode() + "\r\nDescription: " + pitem.getDescription() + "\r\nOn Hand" + OrderUtilities.getAvailableInventory(pitem.getBarcode() + "", FacilitiesManager.getFacilityForCode(Facility).getId()) + "\r\n\r\nPicker: " + pstat.getPickerName() +
                "\r\nOrder Number: " + pstat.getOrderNum() + "\r\nTime of Report: " + Calendar.getInstance().getTime();
        Mailer.sendMail(subject, body, emailList.getInstance().getStockoutEmail(), "do-not-reply@owd.com");

        //Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
        String sql = "insert into dbo.w_stockout ( location, inventory_id, who_reported, client_fkey, facility) values ('" + loc.substring(1).trim() + "','" +
                pitem.getBarcode() + "','" + pstat.getPickerName() + "', '" + pitem.getClientFkey() + "', " + FacilitiesManager.getFacilityForCode(Facility).getId() + ")";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        //PreparedStatement stmt = cxn.prepareStatement(sql);

        //      System.out.println("settingfront parameters");
        //      stmt.setString(1, loc.trim());
        //    stmt.setString(2, (String) request.getAttribute("loginName"));
        //    stmt.setInt(3, Integer.decode(sku).intValue());

        // int rowsUpdated = stmt.executeUpdate();
        int rowsUpdated = q.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void unassignedBarcode(OrderPickStatus pstat, String barcode) {

        try {
            String sql = "select count(*) from w_un_barcode  (NOLOCK) where barcode = " + barcode + " and done = 0";
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);

            if (rs.next()) {
                int i = rs.getInt(1);
                if (i == 0) {
                    String who = pstat.getPickerName();
                    String ordernum = pstat.getOrderNum();
                    OrderPickItem curritem = ((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]);

                    PreparedStatement pstate = HibernateSession.getPreparedStatement("insert into dbo.w_un_barcode " +
                            "( inv_id, who, ordernum, barcode, location, done) values (?,?,?,?,?,0)");
                    pstate.setInt(1, curritem.getItemBarcode());
                    pstate.setString(2, who);
                    pstate.setString(3, ordernum);
                    pstate.setString(4, barcode);
                    pstate.setString(5, curritem.getDefaultLoc());
                    System.out.println("Inserting bad barcode");
                    pstate.executeUpdate();

                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            /*  OrderPickStatus ps= getPickStatusByOrderNumber("15631496");
             pickItemBean pi = loadPickItemBean(ps,"DC6","1");
             System.out.println(pi.getLocList());
             holdForm hf = loadHoldForm(pi,"Danny Nickels");

             System.out.println(ps.getPickerName());*/
            HibernateSession.currentSession();
            System.out.println("hello");

            String sql = "execute getLocationsPrioritySortedAll :id, :facility, :priority";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            q.setParameter("id", 345073);
            q.setParameter("facility", "DC1");
            q.setParameter("priority", 1);
            List results = q.list();
            if (results.size() > 0) {
                Map data = (Map) results.get(0);
                // info.setLocFirst(data.get("formatedPickString").toString());
                List locs = new ArrayList();
                for (Object row : results) {
                    Map dd = (Map) row;
                    locs.add(dd.get("formatedPickString"));

                }
                // info.setLocList(locs);

            } else {
                // info.setLocFirst("UnKnoWn");
                // info.setLocList(new ArrayList());
            }
            System.out.println("Done");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
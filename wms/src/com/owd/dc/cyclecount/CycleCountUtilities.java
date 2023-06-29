package com.owd.dc.cyclecount;

import com.owd.dc.locations.LocationBarcodeUtilities;
import com.owd.dc.locations.locationInfoBean;
import com.owd.hibernate.*;
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.WCycleCount;
import com.owd.hibernate.generated.WCycleCountLocation;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/16/11
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */


public class CycleCountUtilities {


    public static String kStatusNotStarted = "New";
    public static String kStatusAssigned = "Assigned";
    public static String kStatusNeedsVerification = "Needs Verification";
    public static String kStatusVerificationInProgress = "Verification In Progress";
    public static String kStatusCompleted = "Completed";
    public static String kStatusCanceled = "Canceled";

    public static List<String> kClosedStatusTypeList = null;

    static {
        kClosedStatusTypeList = new ArrayList<String>();
        kClosedStatusTypeList.add(kStatusCompleted);
        kClosedStatusTypeList.add(kStatusCanceled);

    }

    public static WCycleCount startNewCycleCount(int inventoryId, String creator,String facility) throws Exception {
        WCycleCount cycle = new WCycleCount();
        //verify no existing count
        List<WCycleCount> existingCounts = (List<WCycleCount>) HibernateSession.currentSession().createCriteria(WCycleCount.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("inventoryId", (long) inventoryId))
                .add(Restrictions.not(Restrictions.in("verifyStatus", kClosedStatusTypeList))).list();
        if (existingCounts.size() > 0) {
            throw new Exception("An existing cycle count already exists for inventory id " + inventoryId);
        }
        //check for unpicked orders? maybe later

        //verify item id
        OwdInventory item = (OwdInventory) HibernateSession.currentSession().get(OwdInventory.class, inventoryId);
        if (item == null) {
            throw new Exception("No item found for inventory ID " + inventoryId);
        }
        //update fields
        cycle.setCreatedBy(creator);
        cycle.setAssignedTo(null);
        cycle.setInventoryId(inventoryId);
        cycle.setTotalCountQty(0);
        cycle.setVerifyStatus(kStatusNotStarted);
        //look up locations
        List<String> locList = LocationBarcodeUtilities.getLocationBarcodesForInventoryIDList(inventoryId, HibernateSession.currentSession(),facility);
        if (locList.size() < 1) {
            throw new Exception("No locations found for inventory ID " + inventoryId + " add at least one location for this item first");
        }
        HibernateSession.currentSession().save(cycle);
        HibernateSession.currentSession().flush();
        //create location records
        for (String location : locList) {
            WCycleCountLocation loc = new WCycleCountLocation();
            loc.setCountQuantity(0L);
            loc.setVerifyCountQuantity(0L);

            loc.setLocationScan(location);
            loc.setLocationDisplay(LocationBarcodeUtilities.getDisplayableBarcodeValue(location));
            loc.setwCycleCount(cycle);
            cycle.getwCycleCountLocations().add(loc);
            HibernateSession.currentSession().save(loc);
        }
        //commit
        HibUtils.commit(HibernateSession.currentSession());
        assignCycleToUser(cycle, creator, false);
        return cycle;
    }

    public static void assignCycleToUser(WCycleCount cycle, String user, boolean canVerifyOthers) throws Exception {
        if (kStatusNotStarted.equals(cycle.getVerifyStatus()) || (kStatusNeedsVerification.equals(cycle.getVerifyStatus()) && canVerifyOthers)) {
            if (kStatusNotStarted.equals(cycle.getVerifyStatus())) {
                cycle.setAssignedTo(user);
                cycle.setVerifyStatus(kStatusAssigned);
            } else {
                cycle.setAssignedTo(user);
                cycle.setVerifyStatus(kStatusVerificationInProgress);
            }
            HibernateSession.currentSession().save(cycle);
            HibUtils.commit(HibernateSession.currentSession());
        } else {
            throw new Exception("Selected cycle count not available for assignment to " + user);
        }
    }


    public static List<WCycleCount> getCurrentCycleCountsForUser(String user, boolean canVerifyOthers) throws Exception {

        List<WCycleCount> cycleList = (List<WCycleCount>) HibernateSession.currentSession().createCriteria(WCycleCount.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.or(
                        (Restrictions.or(
                                Restrictions.and(Restrictions.eq("assignedTo", user), (Restrictions.not(Restrictions.in("verifyStatus", kClosedStatusTypeList)))),
                                Restrictions.eq("verifyStatus", kStatusNeedsVerification + (canVerifyOthers ? "" : "noverify")))),
                        (Restrictions.eq("verifyStatus", kStatusNotStarted))))
                .list();

        return cycleList;
    }

    public static List<WCycleCountLocation> getUncountedLocationsForCycleCount(WCycleCount cycle) throws Exception {

        List<WCycleCountLocation> cycleList = new ArrayList<WCycleCountLocation>();


        for (WCycleCountLocation loc : cycle.getwCycleCountLocations()) {
            if (cycle.getVerifyStatus().equals(kStatusAssigned) && loc.getScannedOn() == null) {
                cycleList.add(loc);
            } else if (cycle.getVerifyStatus().equals(kStatusVerificationInProgress) && loc.getVerifiedOn() == null) {
                cycleList.add(loc);
            }
        }

        return cycleList;
    }

    public static void addLocationToCycleCount(WCycleCount cycle, String locationScan) throws Exception {

        for (WCycleCountLocation loc : cycle.getwCycleCountLocations()) {
            System.out.println(loc);
            if (loc.getLocationScan().equals(locationScan)) {
                throw new Exception("Location " + locationScan + " already exists for cycle count");
            }
        }


        String pickString = "";
        //is location valid?
        if (locationScan.startsWith("//")) {
            //new-style
            pickString = new locationInfoBean(locationScan.replace("//", ""), HibernateSession.currentSession()).getPickString();

        } else if (locationScan.startsWith("/") && locationScan.length() > 1) {
            //old-style, assume it's valid
            pickString = LocationBarcodeUtilities.getDisplayableBarcodeValue(locationScan);
        }
        if (pickString.length() == 0) {
            throw new Exception("Scanned location " + locationScan + " unrecognized");
        }
        WCycleCountLocation loc = new WCycleCountLocation();
        loc.setCountQuantity(0L);
        loc.setVerifyCountQuantity(0L);
        loc.setLocationScan(locationScan);
        loc.setLocationDisplay(pickString);
        loc.setwCycleCount(cycle);
        cycle.getwCycleCountLocations().add(loc);
        HibernateSession.currentSession().save(loc);
        HibernateSession.currentSession().save(cycle);

        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void addLocationCountToCycleCount(WCycleCount cycle, String locationScan, long quantityCounted) throws Exception {
        WCycleCountLocation countLoc = null;

        for (WCycleCountLocation loc : cycle.getwCycleCountLocations()) {
            if (loc.getLocationScan().equals(locationScan) &&
                    ((loc.getCountQuantity() == 0 && loc.getScannedOn() == null && kStatusAssigned.equals(cycle.getVerifyStatus()) ) ||
                            (loc.getVerifyCountQuantity() == 0 && loc.getVerifiedOn() == null && kStatusVerificationInProgress.equals(cycle.getVerifyStatus())))) {
                countLoc = loc;
            }
        }
        System.out.println("countLoc;" + countLoc);

        if (countLoc != null) {
            if (cycle.getVerifyStatus().equals(kStatusAssigned)) {
                countLoc.setScannedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                countLoc.setCountQuantity(quantityCounted);
            } else if (cycle.getVerifyStatus().equals(kStatusVerificationInProgress)) {
                countLoc.setVerifiedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                countLoc.setVerifyCountQuantity(quantityCounted);
            }

            HibernateSession.currentSession().save(countLoc);
            HibernateSession.currentSession().save(cycle);

            HibUtils.commit(HibernateSession.currentSession());
        } else {
            throw new Exception("Location already counted or cycle count invalid");
        }

    }


    public static void isCycleCountCompleted(WCycleCount cycle) {

    }


    public static void finishCycleCount(WCycleCount cycle) {

    }


}

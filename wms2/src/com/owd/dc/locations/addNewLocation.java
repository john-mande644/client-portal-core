package com.owd.dc.locations;


import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;


public class addNewLocation {
    private static String insertParentAttributesSql = "insert into w_location_attribute_values (ix_node, att_id, value, catagory) select :locationId, att_id, value, catagory from w_location_attribute_values where ix_node = :parentId and catagory = 1";
    private static String checkForParentAttributesSql = "select * from w_location_attribute_values where ix_node = :parentId and catagory = 1";
    private static String insertDefaultAttributesSql = "insert into w_location_attribute_values (ix_node, att_id, value, catagory) select :locationId, att_id, default_value, catagory from w_location_attributes where catagory = 1";


    public static List<String> addNewMobileLocations(Session sess, String parentId, String newLocationType, String user, String ipAddressOfUser, int qty) throws Exception {

        List<String> result = new ArrayList<String>();

        if (com.owd.dc.locations.locationUtilities.isValidParent(sess, newLocationType, Integer.parseInt(parentId))) {
            while (qty > 0) {
                WLocation loc = new WLocation();
                loc.setLocationName("ZZnew");
                loc.setLocationType(Integer.parseInt(newLocationType));
                loc.setParentId(Integer.parseInt(parentId));
                loc.setPickPriority("3");
                sess.save(loc);
                int id = loc.getId();
                loc.setLocationName("Bin " + loc.getId().toString());
                sess.save(loc);
                HibUtils.commit(sess);

                if (!wmsEvents.addLocationEvent(sess, id + "", loc.getLocationName(), parentId, user, com.owd.dc.locations.wmsEvents.appWeb, ipAddressOfUser)) {
                    System.out.println("Unable to add event record");
                }
                result.add(id + "");
                qty = qty - 1;

            }

        } else {
            throw new Exception("Not a valid location type for current location");
        }


        return result;
    }

    /**
     * @param sess            HibernateSesion to use
     * @param parentId        Id of Location to add the new location to
     * @param locationName    Name of the new location to be created
     * @param newLocationType Location Type of the new location to be created
     * @param user            User id that is adding location
     * @param ipAddressOfUser the ip Address of the user adding the location
     * @return Integer returns the id of the newly created record
     * @throws Exception errors
     */
    public static Integer addLocation(Session sess, String parentId, String locationName, String newLocationType, String user, String ipAddressOfUser) throws Exception {
        Integer id = 0;
        System.out.println("In add location");
        if (com.owd.dc.locations.locationUtilities.isValidParent(sess, newLocationType, Integer.parseInt(parentId))) {
            System.out.println("Adding : " + locationName + "to parent" + parentId);
            if (locationUtilities.isUniqueName(sess, parentId, locationName, newLocationType)) {
                System.out.println("Adding : " + locationName + "to parent" + parentId);
                WLocation loc = new WLocation();
                loc.setLocationName(locationName);
                loc.setLocationType(Integer.parseInt(newLocationType));
                loc.setParentId(Integer.parseInt(parentId));
                loc.setPickPriority("3");
                sess.save(loc);
                id = loc.getId();
                if (insertAttributes(sess, id + "", parentId)) {

                } else {
                    throw new Exception("Unable to insert attributes");
                }
                if (!wmsEvents.addLocationEvent(sess, id + "", locationName, parentId, user, com.owd.dc.locations.wmsEvents.appWeb, ipAddressOfUser)) {
                    System.out.println("Unable to add event record");
                }
                HibUtils.commit(sess);


                //todo fill out attributes on new location

            } else {
                throw new Exception("This is not a unique name, Names must be unique when creating a new location.");
            }

        } else {
            throw new Exception("Not a valid location type for current location");
        }

        return id;
    }

    /**
     * @param sess       HibernateSession to use for Query
     * @param locationId Id of new location to add Attributes to
     * @param parentId   Id of parent location of the locationId
     * @return boolean returns true if the attribues get inserted
     * @throws Exception throw exception on unique key contraint
     */
    private static boolean insertAttributes(Session sess, String locationId, String parentId) throws Exception {
        boolean success = false;
        try {
            Query q = sess.createSQLQuery(checkForParentAttributesSql);
            q.setParameter("parentId", parentId);
            List result = q.list();
            if (result.size() > 0) {
                System.out.println("copy parent attributes");
                Query insert = sess.createSQLQuery(insertParentAttributesSql);
                insert.setParameter("locationId", locationId);
                insert.setParameter("parentId", parentId);
                int rows = insert.executeUpdate();
                if (rows > 0) {
                    success = true;
                }

            } else {
                System.out.println("copy default attributes");
                Query insert = sess.createSQLQuery(insertDefaultAttributesSql);
                insert.setParameter("locationId", locationId);

                int rows = insert.executeUpdate();
                if (rows > 0) {
                    success = true;
                }

            }
        } catch (ConstraintViolationException sq) {
            if (sq.getCause().getMessage().contains("oneAttPerId"))
                throw new Exception("Cannot insert duplicate attribues");
            return false;
        }

        return success;
    }

    public static void main(String[] args) {
        try {
            Session sess = HibernateSession.currentSession();
            System.out.println(addLocation(sess, "36442", "Rack 1", "8", "Danny", "172.16.66.255"));


            // System.out.println(insertAttributes(sess,"7","10"));
            com.owd.hibernate.HibUtils.commit(sess);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

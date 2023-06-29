package com.owd.core.business.order;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Event {
private final static Logger log =  LogManager.getLogger();

    //IDs are zero if the package is new



    //Attributes

    private static final String kdbEventPKName = "event_id";

    public String event_id = "0";


    private static final String kdbEventOrderFkey = "order_fkey";

    public String order_fkey = "0";


    private static final String kdbEventCreatedBy = "creator";

    public String creator = "";


    private static final String kdbEventCreatedOn = "event_stamp";

    public String event_stamp = "";


    private static final String kdbEventMessage = "message";

    public String message = "";


    private static final String kdbEventType = "event_type";

    public int event_type = 0;


    public static final int kEventTypeGeneral = 0;

    public static final int kEventTypeComment = 1;

    public static final int kEventTypeEmailSent = 2;

    public static final int kEventTypeHandling = 3;

    public static final int kEventTypeEdit = 4;

    public static final int kEventTypeFinance = 5;


    public static final int kEventTypeElvis = 6;


    public static final int kEventTypeBackorder = 7;


    private static final String kdbEventTable = "order_events";

    private static final String kDeleteEventStatement = "DELETE FROM " + kdbEventTable + " WITH (ROWLOCK) where " + kdbEventPKName + " = ?";


    private static final String kInsertEventStatement = "insert into " + kdbEventTable + "("

            + kdbEventOrderFkey + ","

            + kdbEventCreatedBy + ","

            + kdbEventCreatedOn + ","

            + kdbEventMessage + ","

            + kdbEventType + ") VALUES (?,?,?,?,?)";



    //no updates

    /*

    private static final String 	kUpdateEventStatement = "update "+kdbEventTable+" set "

                                                    +kdbEventOrderFkey+"=?,"

                                                    +kdbEventCreatedBy+"=?,"

                                                    +kdbEventCreatedOn+"=?,"

                                                    +kdbEventMessage+"=?,"

                                                    +kdbEventType+"=?  WHERE "+kdbEventPKName+" = ?";

    */



    private boolean needsUpdate = false;


    public Event() {


    }


    public Event(String aid,

                 String aorder_id,

                 String acreator,

                 String aevent_stamp,

                 String aevent_type,

                 String amessage) {

        event_id = aid;

        order_fkey = aorder_id;

        creator = acreator;

        event_stamp = aevent_stamp;

        message = amessage;


        try {

            int tempID = new Integer("0" + aevent_type).intValue();

            if (tempID < 0)

                event_type = 0;

            else

                event_type = tempID;

        } catch (NumberFormatException ex) {
            event_type = 0;
        }


    }


    public Event getEventForID(Connection cxn, String eventID) throws Exception {

        //load existing event

        return dbload(cxn, eventID);

    }


    public static java.util.Vector getEventssForOrder(String orderID) throws Exception {


        Connection cxn = null;

        java.util.Vector events = new java.util.Vector();


        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            events = getEventssForOrder(cxn, orderID);

            cxn.rollback();

        } catch (Exception ex) {

            cxn.rollback();

            ex.printStackTrace();

            throw ex;

        } finally {


            try {
                cxn.close();
            } catch (Exception e) {
            }

        }


        return events;


    }


    public static java.util.Vector getEventssForOrder(Connection cxn, String orderID) throws Exception {

        java.util.Vector items = new java.util.Vector();

        Statement stmt = null;

        ResultSet rs = null;

        try {

            stmt = cxn.createStatement();

            String isql = "select * from " + kdbEventTable + " (NOLOCK) where order_fkey = " + orderID + " order by event_id desc";


            boolean hasResultSet = stmt.execute(isql);


            if (hasResultSet) {

                rs = stmt.getResultSet();


                while (rs.next()) {

                    items.addElement(new Event(rs.getString(kdbEventPKName),

                            rs.getString(kdbEventOrderFkey),

                            rs.getString(kdbEventCreatedBy),

                            rs.getString(kdbEventCreatedOn),

                            rs.getString(kdbEventType),

                            rs.getString(kdbEventMessage)));


                }


                rs.close();


            }


            stmt.close();


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                stmt.close();
            } catch (Exception exc) {
            }

            return items;

        }

    }


    public boolean isModified() {

        return needsUpdate;

    }


    public boolean isNew() {

        if ("0".equals(event_id))

            return true;


        return false;

    }


    public void dbsave(Connection cxn, String source) throws Exception {

        creator = source;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        if (creator == null)

            creator = com.owd.core.OWDUtilities.getCurrentUserName();

        event_stamp = com.owd.core.OWDUtilities.getSQLDateTimeForToday();


        try {

            stmt = cxn.prepareStatement(kInsertEventStatement);

            stmt.setInt(1, new Integer(order_fkey).intValue());

            stmt.setString(2, creator);

            stmt.setString(3, event_stamp);


            stmt.setString(4, message);

            stmt.setInt(5, event_type);


            int rowsUpdated = stmt.executeUpdate();


            if (rowsUpdated < 1)

                throw new Exception("Could not save event record!");


            stmt = cxn.prepareStatement("select @@IDENTITY");


            stmt.executeQuery();

            rs = stmt.getResultSet();

            if (rs.next()) {

                event_id = rs.getString(1);

            }

            rs.close();

            stmt.close();


        } catch (Exception ex) {

            throw ex;

        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }


    }


    public void dbsave(Connection cxn) throws Exception {


        dbsave(cxn, com.owd.core.OWDUtilities.getCurrentUserName(creator));

    }


    public void dbdelete(Connection cxn) throws Exception {


        if (isNew()) {

            //do nothing

        } else {

            PreparedStatement stmt = null;

            try {

                stmt = cxn.prepareStatement(kDeleteEventStatement);

                stmt.setString(1, event_id);


                int rowsUpdated = stmt.executeUpdate();


                if (rowsUpdated < 1) throw new Exception("Cannot delete event ID " + event_id);


                stmt.close();


            } catch (Exception ex) {

                throw ex;

            } finally {

                try {
                    stmt.close();
                } catch (Exception exc) {
                }

            }


        }

    }



/* not supported



	public void dbupdate(Connection cxn) throws Exception

	{



		if (isNew())

		{

			dbsave(cxn);

		}else{

			PreparedStatement stmt = null;

			modifiedBy = com.owd.core.OWDUtilities.getCurrentUserName();

			modifiedOn = com.owd.core.OWDUtilities.getSQLDateTimeForToday();

			try

			{

				stmt = cxn.prepareStatement(kUpdatePackageStatement);

				stmt.setInt(1,new Integer(order_fkey).intValue());

				stmt.setInt(2,new Integer(line_index).intValue());

				stmt.setString(3,tracking_no);

				stmt.setFloat(4,weight);

				stmt.setFloat(5,total_billed);

				stmt.setFloat(6,cost_of_goods);

				stmt.setString(7,ship_date);

				stmt.setInt(8,new Integer(msn).intValue());

				stmt.setInt(9,new Integer(is_void).intValue());

				stmt.setInt(10,new Integer(reported).intValue());

				stmt.setString(11,modifiedBy);

				stmt.setString(12,modifiedOn);

				stmt.setInt(13,new Integer(order_track_id).intValue());







				int rowsUpdated = stmt.executeUpdate();



				if (rowsUpdated < 1)

					throw new Exception("Could not update package id "+order_track_id);



			}catch(Exception ex)

		  	{

		  		throw ex;

		  	}finally

		  	{

		  		try{ stmt.close(); }catch(Exception exc){}



		  	}



		}



	}



*/



    private static Event dbload(Connection cxn, String id) throws Exception {


        Statement stmt = null;

        ResultSet rs = null;

        Event event = null;


        try {

            stmt = cxn.createStatement();

            String isql = "select * from " + kdbEventTable + " WITH (NOLOCK) where " + kdbEventPKName + " = " + id;


            boolean hasResultSet = stmt.execute(isql);


            if (hasResultSet) {

                rs = stmt.getResultSet();


                if (rs.next()) {

                    event = new Event(rs.getString(kdbEventPKName),

                            rs.getString(kdbEventOrderFkey),

                            rs.getString(kdbEventCreatedBy),

                            rs.getString(kdbEventCreatedOn),

                            rs.getString(kdbEventType),

                            rs.getString(kdbEventMessage));


                } else {

                    throw new Exception("Event id " + id + " not found!");

                }


                rs.close();


            }


            stmt.close();


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }


        return event;

    }


    static public void addOrderEvent(Connection cxn, int orderID, int eventType, String eventMessage, String user) throws Exception {

        Event evt = new Event();

        evt.order_fkey = "" + orderID;

        evt.event_type = eventType;

        evt.message = eventMessage;

        if (user != null)

            evt.creator = user;


        evt.dbsave(cxn);


    }


    static public void addOrderEvent(int orderID, int eventType, String eventMessage, String user) throws Exception {


        Connection cxn = null;


        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            addOrderEvent(cxn, orderID, eventType, eventMessage, user);

            cxn.commit();

        } catch (Exception ex) {

            cxn.rollback();

            ex.printStackTrace();

            throw ex;

        } finally {


            try {
                cxn.close();
            } catch (Exception e) {
            }

        }


    }

    public String getCreator() {
        return creator;
    }

    public String getEventstamp() {
        return event_stamp;
    }

    public String getMessage() {
        return message;
    }
}

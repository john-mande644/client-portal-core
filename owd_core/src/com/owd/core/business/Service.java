package com.owd.core.business;


import com.owd.core.OWDUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Service {
private final static Logger log =  LogManager.getLogger();

    //IDs are zero if the package is new



    //Attributes

    public static final String kdbServicePKName = "service_id";

    public String service_id = "0";


    public static final String kdbServiceName = "service_name";

    public String service_name = "";


    public static final String kdbServiceCharge = "service_charge";

    public float service_charge = 0;


    public static final String kdbServiceDesc = "description";

    public String description = "";


    public static final String kdbServiceIsTimed = "is_timed";

    public int is_timed = 0;


    public static final String kdbServiceTimeUnit = "time_unit";

    public int time_unit = 0;


    public static final int kServiceTimeUnitMinute = 0;

    public static final int kServiceTimeUnitHour = 1;


    public static final String kdbServiceTable = "billed_services";

    private static final String kDeleteServiceStatement = "DELETE FROM " + kdbServiceTable + " where " + kdbServicePKName + " = ?";


    private static final String kInsertServiceStatement = "insert into " + kdbServiceTable + "("

            + kdbServiceName + ","

            + kdbServiceCharge + ","

            + kdbServiceDesc + ","

            + kdbServiceIsTimed + ","

            + kdbServiceTimeUnit + ") VALUES (?,?,?,?,?)";


    private static final String kUpdateServiceStatement = "update " + kdbServiceTable + " set "

            + kdbServiceName + "=?,"

            + kdbServiceCharge + "=?,"

            + kdbServiceDesc + "=?,"

            + kdbServiceIsTimed + "=?,"

            + kdbServiceTimeUnit + "=?  WHERE " + kdbServicePKName + " = ?";


    private boolean needsUpdate = false;


    public Service() {


    }


    public Service(String aid,

                   String aservice_name,

                   String aservice_charge,

                   String adescription,

                   String a_is_timed,

                   String a_time_unit) {

        service_id = aid;

        service_name = aservice_name;

        description = adescription;


        try {

            float tempID = new Float("0" + aservice_charge).floatValue();

            if (tempID < 0)

                service_charge = 0;

            else

                service_charge = tempID;

        } catch (NumberFormatException ex) {
            service_charge = 0;
        }


        try {

            int tempID = new Integer("0" + a_is_timed).intValue();

            if (tempID < 0)

                is_timed = 0;

            else

                is_timed = tempID;

        } catch (NumberFormatException ex) {
            is_timed = 0;
        }


        try {

            int tempID = new Integer("0" + a_time_unit).intValue();

            if (tempID < 0)

                time_unit = 0;

            else

                time_unit = tempID;

        } catch (NumberFormatException ex) {
            time_unit = 0;
        }


        service_charge = OWDUtilities.roundFloat(service_charge, 2);

    }


    public static Service getServiceForID(Connection cxn, String serviceID) throws Exception {

        //load existing event

        return dbload(cxn, serviceID);

    }


    public static Service getServiceForID(int serviceID) throws Exception {


        Connection cxn = null;

        Service svc = null;

        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            svc = getServiceForID(cxn, serviceID + "");

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


        return svc;


    }

/*	

	public static java.util.Vector getServicesForOrder(Connection cxn, String orderID) throws Exception

	{

		java.util.Vector items = new java.util.Vector();

		Statement stmt = null;

		ResultSet rs =  null;

		try

		{

			stmt = cxn.createStatement();

			String isql = "select * from "+kdbEventTable+" where order_fkey = "+orderID+" order by event_id desc";



			boolean hasResultSet = stmt.execute(isql);



			if(hasResultSet)

			{

				rs = stmt.getResultSet();



				while(rs.next())

				{

					items.addElement(new Event(	rs.getString(kdbEventPKName),

												rs.getString(kdbEventOrderFkey),

												rs.getString(kdbEventCreatedBy),

												rs.getString(kdbEventCreatedOn),

												rs.getString(kdbEventType),

												rs.getString(kdbEventMessage)

												));



				}



				rs.close();



			}



			stmt.close();



		}catch(Exception ex)

	  	{

	  		throw ex;

	  	}finally

	  	{

	  		try{ rs.close(); }catch(Exception exc){}

	  		try{ stmt.close(); }catch(Exception exc){}

	  		return items;

	  	}

	}

*/



    public boolean isModified() {

        return needsUpdate;

    }


    public boolean isNew() {

        if ("0".equals(service_id))

            return true;


        return false;

    }


    public void dbsave(Connection cxn) throws Exception {

        PreparedStatement stmt = null;

        ResultSet rs = null;


        service_charge = OWDUtilities.roundFloat(service_charge, 2);


        try {

            stmt = cxn.prepareStatement(kInsertServiceStatement);

            stmt.setString(1, service_name);

            stmt.setFloat(2, service_charge);

            stmt.setString(3, description);

            stmt.setInt(4, is_timed);

            stmt.setInt(5, time_unit);


            int rowsUpdated = stmt.executeUpdate();


            if (rowsUpdated < 1)

                throw new Exception("Could not save service record!");


            stmt = cxn.prepareStatement("select @@IDENTITY");


            stmt.executeQuery();

            rs = stmt.getResultSet();

            if (rs.next()) {

                service_id = rs.getString(1);

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


    public void dbdelete(Connection cxn) throws Exception {


        if (isNew()) {

            //do nothing

        } else {

            PreparedStatement stmt = null;

            try {

                stmt = cxn.prepareStatement(kDeleteServiceStatement);

                stmt.setString(1, service_id);


                int rowsUpdated = stmt.executeUpdate();


                if (rowsUpdated < 1) throw new Exception("Cannot delete service ID " + service_id);


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


    public void dbupdate(Connection cxn) throws Exception {


        if (isNew()) {

            dbsave(cxn);

        } else {

            PreparedStatement stmt = null;

            service_charge = OWDUtilities.roundFloat(service_charge, 2);

            try {

                stmt = cxn.prepareStatement(kUpdateServiceStatement);


                stmt.setString(1, service_name);

                stmt.setFloat(2, new Float("0" + service_charge).floatValue());

                stmt.setString(3, description);

                stmt.setInt(4, new Integer(is_timed).intValue());

                stmt.setInt(5, new Integer(time_unit).intValue());

                stmt.setInt(6, new Integer(service_id).intValue());


                int rowsUpdated = stmt.executeUpdate();


                if (rowsUpdated < 1)

                    throw new Exception("Could not update service id " + service_id);


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


    private static Service dbload(Connection cxn, String id) throws Exception {


        Statement stmt = null;

        ResultSet rs = null;

        Service svc = null;


        try {

            stmt = cxn.createStatement();

            String isql = "select * from " + kdbServiceTable + " where " + kdbServicePKName + " = " + id;


            boolean hasResultSet = stmt.execute(isql);


            if (hasResultSet) {

                rs = stmt.getResultSet();


                if (rs.next()) {

                    svc = new Service(rs.getString(kdbServicePKName),

                            rs.getString(kdbServiceName),

                            rs.getString(kdbServiceCharge),

                            rs.getString(kdbServiceDesc),

                            rs.getString(kdbServiceIsTimed),

                            rs.getString(kdbServiceTimeUnit));


                } else {

                    throw new Exception("Service id " + id + " not found!");

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


        return svc;

    }



/*

	static public void addOrderEvent(Connection cxn, int orderID, int eventType, String eventMessage, String user) throws Exception

	{

		Event evt = new Event();

		evt.order_fkey = ""+orderID;

		evt.event_type = eventType;

		evt.message = eventMessage;

		if(user != null)

			evt.creator = user;

		

		evt.dbsave(cxn);

		

	

	}



	static public void addOrderEvent( int orderID, int eventType, String eventMessage, String user) throws Exception

	{

	

				Connection cxn = null;



		try{

			cxn = com.owd.core.managers.ConnectionManager.getConnection();

			addOrderEvent(cxn,orderID,eventType,eventMessage,user);

			cxn.commit();

		}catch(Exception ex)

		{

			cxn.rollback();

			ex.printStackTrace();

			throw ex;

		}finally{



			try{cxn.close();}catch(Exception e){}

		}

		

	

	}

	*/

}

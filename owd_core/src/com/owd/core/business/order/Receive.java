package com.owd.core.business.order;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Receive {
private final static Logger log =  LogManager.getLogger();

    //IDs are zero if the receive is new





    public static String[] kReceiveTypes = {"Unknown", "Receive", "Received ASN", "Pending ASN"};



    //Attributes

    public static final String kdbReceivePKName = "receive_id";

    public String receive_id = "0";


    private static final String kdbReceiveClientFkey = "client_fkey";

    public String client_fkey = "0";


    private static final String kdbReceiveDate = "receive_date";

    public String receive_date = "";


    private static final String kdbReceiveCreatedBy = "created_by";

    public String createdBy = "";


    private static final String kdbReceiveCreatedOn = "created_date";

    public String createdOn = "";


    private static final String kdbReceiveModifiedBy = "modified_by";

    public String modifiedBy = "";


    private static final String kdbReceiveModifiedOn = "modified_date";

    public String modifiedOn = "";





    //Properties

    private static final String kdbReceiveUser = "receive_user";

    public String receive_user = "";


    private static final String kdbReceiveTime = "total_time";

    public String total_time = "0.0";


    private static final String kdbReceiveCarrier = "carrier";

    public String carrier = "";


    private static final String kdbReceiveClientRef = "ref_num";

    public String ref_num = "";


    private static final String kdbReceiveNotes = "notes";

    public String notes = "";


    private static final String kdbReceiveIsVoid = "is_void";

    public String is_void = "0";


    private static final String kdbReceiveType = "type";

    public String type = "";


    private static final String kdbReceiveExpectedDate = "expected_date";

    public String expected_date = "";


    private static final String kdbReceiveShipperRef = "shipper_ref";

    public String shipper_ref = "";


    private static final String kdbReceiveTable = "owd_receive";


    public java.util.Vector itemsList = new java.util.Vector();


    private boolean needsUpdate = false;


    public String getID() {

        return receive_id;

    }


    public Receive(String aclient_id) {

        client_fkey = aclient_id;

    }


    public Receive(String aid,

                   String aclient_id,

                   String areceive_date,

                   String areceive_user,

                   String atotaltime,

                   String acarrier,

                   String arefnum,

                   String anotes,

                   String avoid,

                   String atype,

                   String aexpecteddate,

                   String ashipperref,

                   String acreated_by,

                   String acreated_on,

                   String amodified_by,

                   String amodified_on) {

        if (aid != null)

            receive_id = aid;

        client_fkey = aclient_id;

        receive_date = areceive_date;

        receive_user = areceive_user;

        total_time = atotaltime;

        carrier = acarrier;

        ref_num = arefnum;

        notes = anotes;

        is_void = avoid;

        type = atype;

        expected_date = aexpecteddate;

        shipper_ref = ashipperref;


        try {

            long tempID = new Long("0" + receive_id).longValue();

            if (tempID < 0)

                receive_id = "0";

        } catch (NumberFormatException ex) {
            receive_id = "0";
        }

        try {

            long tempID = new Long("0" + is_void).longValue();

            if (tempID < 0)

                is_void = "0";

        } catch (NumberFormatException ex) {
            is_void = "0";
        }

        try {

            long tempID = new Long("0" + client_fkey).longValue();

            if (tempID < 0)

                client_fkey = "0";

        } catch (NumberFormatException ex) {
            client_fkey = "0";
        }


        try {

            float sku_price = (new Float("0" + total_time).floatValue());

        } catch (NumberFormatException ex) {
            total_time = "" + (float) 0.0;
        }


        createdBy = acreated_by;

        createdOn = acreated_on;

        modifiedBy = amodified_by;

        modifiedOn = amodified_on;


        boolean found = false;

        for (int i = 0; i < kReceiveTypes.length; i++) {

            if ((kReceiveTypes[i]).equals(type))

                found = true;

        }

        if (!found) type = kReceiveTypes[0];


    }


    public static Receive getReceiveForID(Connection cxn, String receiveID) throws Exception {

        //load existing order

        return dbload(cxn, receiveID);

    }


    public boolean isModified() {

        return needsUpdate;

    }


    public boolean isNew() {

        if ("0".equals(receive_id))

            return true;


        return false;

    }





    private boolean editState = false;


    public boolean isEditable() {

        return editState;

    }


    public void setEditable(boolean state) {

        editState = state;

    }


    public void dbsave(Connection cxn) throws Exception {


        PreparedStatement stmt = null;

        createdBy = com.owd.core.OWDUtilities.getCurrentUserName();

        createdOn = com.owd.core.OWDUtilities.getSQLDateTimeForToday();

        modifiedOn = createdOn;

        modifiedBy = createdBy;


        try {

            stmt = cxn.prepareStatement("insert into " + kdbReceiveTable + "("

                    + kdbReceiveClientFkey + ","

                    + kdbReceiveDate + ","

                    + kdbReceiveUser + ","

                    + kdbReceiveTime + ","

                    + kdbReceiveCarrier + ","

                    + kdbReceiveClientRef + ","

                    + kdbReceiveNotes + ","

                    + kdbReceiveIsVoid + ","

                    + kdbReceiveType + ","

                    + kdbReceiveExpectedDate + ","

                    + kdbReceiveShipperRef + ","

                    + kdbReceiveCreatedBy + ","

                    + kdbReceiveCreatedOn + ","

                    + kdbReceiveModifiedBy + ","

                    + kdbReceiveModifiedOn + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setInt(1, new Integer(client_fkey).intValue());

            stmt.setString(2, receive_date);

            stmt.setString(3, createdBy);

            stmt.setString(4, createdOn);

            stmt.setString(5, modifiedBy);

            stmt.setString(6, modifiedOn);

            stmt.setString(7, receive_user);

            stmt.setFloat(8, new Float(total_time).floatValue());

            stmt.setString(9, carrier);

            stmt.setString(10, ref_num);

            stmt.setString(11, notes);

            stmt.setInt(12, new Integer(is_void).intValue());

            stmt.setString(13, type);

            stmt.setString(14, expected_date);

            stmt.setString(15, shipper_ref);


            int rowsUpdated = stmt.executeUpdate();


            if (rowsUpdated < 1)

                throw new Exception("Could not save line item!");


        } catch (Exception ex) {

            throw ex;

        } finally {

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

                throw new Exception("Cannot delete receive ID " + receive_id);


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

            PreparedStatement ps = null;

            modifiedBy = com.owd.core.OWDUtilities.getCurrentUserName();

            modifiedOn = com.owd.core.OWDUtilities.getSQLDateTimeForToday();

            try {

                ps = cxn.prepareStatement("update owd_receive set " +

                        " receive_date = ?," +

                        " modified_by = ?," +

                        " modified_date = ?," +

                        " receive_user = ?," +

                        " total_time = ?," +

                        " carrier = ?," +

                        " ref_num = ?," +

                        "notes=?," +

                        " is_void = ?," +

                        " type = ?," +

                        " expected_date = ?," +

                        " shipper_ref = ?  where receive_id = ? and client_fkey = ?");

                ps.setString(1, receive_date);

                ps.setString(2, modifiedBy);

                ps.setString(3, modifiedOn);

                ps.setString(4, receive_user);

                ps.setFloat(5, new Float(total_time).floatValue());

                ps.setString(6, carrier);

                ps.setString(7, ref_num);

                ps.setString(8, notes);

                ps.setInt(9, new Integer(is_void).intValue());

                ps.setString(10, type);

                ps.setString(11, expected_date);

                ps.setString(12, shipper_ref);

                ps.setInt(14, new Integer(receive_id).intValue());

                ps.setInt(15, new Integer(client_fkey).intValue());


                int rowsUpdated = ps.executeUpdate();


                if (rowsUpdated < 1)

                    throw new Exception("Could not update receive id " + receive_id);


            } catch (Exception ex) {

                throw ex;

            } finally {

                try {
                    ps.close();
                } catch (Exception exc) {
                }


            }


        }


    }


    private static Receive dbload(Connection cxn, String id) throws Exception {


        Statement stmt = null;

        ResultSet rs = null;

        Receive item = null;


        try {

            stmt = cxn.createStatement();

            String isql = "select * from " + kdbReceiveTable + " where " + kdbReceivePKName + " = " + id;


            rs = stmt.executeQuery(isql);




            if (rs.next()) {

                item = new Receive(rs.getString(kdbReceivePKName),

                        rs.getString(kdbReceiveClientFkey),

                        rs.getString(kdbReceiveDate),

                        rs.getString(kdbReceiveUser),

                        rs.getString(kdbReceiveTime),

                        rs.getString(kdbReceiveCarrier),

                        rs.getString(kdbReceiveClientRef),

                        rs.getString(kdbReceiveNotes),

                        rs.getString(kdbReceiveIsVoid),

                        rs.getString(kdbReceiveType),

                        rs.getString(kdbReceiveExpectedDate),

                        rs.getString(kdbReceiveShipperRef),

                        rs.getString(kdbReceiveCreatedBy),

                        rs.getString(kdbReceiveCreatedOn),

                        rs.getString(kdbReceiveModifiedBy),

                        rs.getString(kdbReceiveModifiedOn));


            } else {

                throw new Exception("Receive id " + id + " not found!");

            }

            rs.close();

            stmt.close();


            item.itemsList = ReceiveItem.getItemsForReceive(cxn, id);


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


        return item;

    }


}

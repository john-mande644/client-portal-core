package com.owd.core.business.order;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ReceiveItem {
private final static Logger log =  LogManager.getLogger();

    //IDs are zero if the item is new



    //Attributes

    public static final String kdbReceiveItemPKName = "receive_item_id";

    public int receive_item_id = 0;


    public static final String kdbReceiveFkey = "receive_fkey";

    public int receive_fkey = 0;


    public static final String kdbReceiveItemInvFkey = "inventory_id";

    public int inventory_id = 0;


    public static final String kdbReceiveItemInvNum = "inventory_num";

    public String inventory_num = "";


    public static final String kdbReceiveItemDescription = "description";

    public String description = "";


    public static final String kdbReceiveItemStatus = "item_status";

    public String item_status = "";





    //Properties

    public static final String kdbReceiveItemUnusable = "is_unusable";

    public String is_unusable = "";


    public static final String kdbReceiveItemQuantity = "quantity";

    public int quantity = 0;


    public static final String kdbReceiveItemLocation = "location";

    public String location = "";


    public static final String kdbReceiveItemReturnReason = "return_reason";

    public String return_reason = "";


    public static final String kdbReceiveItemReported = "reported";

    public int reported = 0;


    public static final String kdbReceiveItemCreateDate = "created_date";

    public String created_date = "";


    public static final String kdbReceiveItemCreatedBy = "created_by";

    public String created_by = "";


    public static final String kdbReceiveItemModifyDate = "modified_date";

    public String modified_date = "";


    public static final String kdbReceiveItemModifiedBy = "modified_by";

    public String modified_by = "";


    private static final String kdbReceiveItemTable = "owd_receive_item";


    private static final String loadStatement = "select i.receive_fkey," +

            "i.inventory_id," +

            " i.inventory_num," +

            " i.description," +

            " i.item_status," +

            " i.is_unusable," +

            " i.quantity," +

            " i.location," +

            " i.created_date," +

            " i.created_by," +

            " i.modified_date," +

            " i.modified_by," +

            " i.return_reason," +

            " i.reported " +

            " from owd_receive_item i  where receive_item_id = ? ";


    private static final String loadForReceiveIDStatement = "select i.receive_item_id," +

            "i.inventory_id," +

            " i.inventory_num," +

            " i.description," +

            " i.item_status," +

            " i.is_unusable," +

            " i.quantity," +

            " i.location," +

            " i.created_date," +

            " i.created_by," +

            " i.modified_date," +

            " i.modified_by," +

            " i.return_reason," +

            " i.reported " +

            " from owd_receive_item i  where receive_fkey = ? ";


    private static final String updateStatement1 = "update owd_receive_item " +

            " set inventory_id = ?," +

            " inventory_num = ?," +

            " description = ?," +

            " item_status = ?," +

            " is_unusable = ?," +

            " quantity = ?," +

            " location = ?," +

            " modified_date = ?," +

            " modified_by = ?, return_reason=?, reported =?  where receive_item_id = ?";


    private static final String deleteStatement1 = "delete from owd_receive_item where receive_item_id = ?";


    private static final String createStatement1 = "insert into owd_receive_item ( receive_fkey," +

            "inventory_id," +

            " inventory_num," +

            " description," +

            " item_status," +

            " is_unusable," +

            " quantity," +

            " location," +

            " created_date," +

            " created_by," +

            " modified_date," +

            " modified_by," +

            " return_reason," +

            " reported ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private static final String createStatement2 = "select receive_item_id from owd_receive_item where receive_fkey = ?";


    private static final String createStatement3 = "update owd_receive_item set receive_fkey = ? where receive_item_id = ?";


    private boolean needsUpdate = false;


    public ReceiveItem(String areceive_fkey) {

        try {

            receive_fkey = (new Integer("0" + areceive_fkey).intValue());

        } catch (NumberFormatException ex) {
            receive_fkey = (int) 0;
        }

    }


    public ReceiveItem(String areceive_item_id,

                       String areceive_fkey,

                       String ainventory_id,

                       String ainventory_num,

                       String adescription,

                       String aitem_status,

                       String ais_unusable,

                       String aquantity,

                       String alocation,

                       String acreated_date,

                       String acreated_by,

                       String amodified_date,

                       String amodified_by,

                       String areturn_reason,

                       String areported) {

        inventory_num = ainventory_num;

        description = adescription;


        item_status = aitem_status;

        is_unusable = ais_unusable;

        location = alocation;

        return_reason = areturn_reason;


        try {

            receive_item_id = (new Integer("0" + areceive_item_id).intValue());

        } catch (NumberFormatException ex) {
            receive_item_id = (int) 0;
        }

        try {

            receive_fkey = (new Integer("0" + areceive_fkey).intValue());

        } catch (NumberFormatException ex) {
            receive_fkey = (int) 0;
        }

        try {

            quantity = (new Integer("0" + aquantity).intValue());

        } catch (NumberFormatException ex) {
            quantity = (int) 0;
        }

        try {

            inventory_id = (new Integer("0" + ainventory_id).intValue());

        } catch (NumberFormatException ex) {
            inventory_id = (int) 0;
        }

        try {

            reported = (new Integer("0" + areported).intValue());

        } catch (NumberFormatException ex) {
            reported = (int) 0;
        }


        created_by = acreated_by;

        created_date = acreated_date;

        modified_by = amodified_by;

        modified_date = amodified_date;


    }


    public static ReceiveItem getReceiveItemForID(Connection cxn, String ID) throws Exception {

        //load existing package

        return dbload(cxn, ID);

    }


    public boolean isModified() {

        return needsUpdate;

    }


    public boolean isNew() {

        if (0 == (inventory_id))

            return true;


        return false;

    }


    public void dbsave(Connection cxn) throws Exception {


        PreparedStatement ps = null;


        try {


            String tempFkey = com.owd.core.managers.ConnectionManager.getNextID("ReceiveItem");


            ps = cxn.prepareStatement(createStatement1);

            ps.setInt(1, new Integer(tempFkey).intValue());

            ps.setInt(2, inventory_id);

            ps.setString(3, inventory_num);

            ps.setString(4, description);

            ps.setString(5, item_status);

            ps.setString(6, is_unusable);

            ps.setInt(7, quantity);

            ps.setString(8, location);

            ps.setString(9, com.owd.core.OWDUtilities.getSQLDateTimeForToday());

            ps.setString(10, com.owd.core.OWDUtilities.getCurrentUserName());

            ps.setString(11, com.owd.core.OWDUtilities.getSQLDateTimeForToday());

            ps.setString(12, com.owd.core.OWDUtilities.getCurrentUserName());

            ps.setString(13, return_reason);

            ps.setInt(14, reported);


            if (!(ps.executeUpdate() > 0)) {

                throw new Exception("Could not save receive item!");

            }


            ps.close();


            ps = cxn.prepareStatement(createStatement2);

            ps.setInt(1, new Integer(tempFkey).intValue());

            ps.executeQuery();

            ResultSet rs = ps.getResultSet();

            if (rs.next()) {

                receive_item_id = rs.getInt(1);

            } else {

                throw new Exception("Could not save inventory id!");

            }


            ps.close();


            ps = cxn.prepareStatement(createStatement3);


            ps.setInt(1, receive_fkey);

            ps.setInt(2, receive_item_id);


            if (!(ps.executeUpdate() > 0)) {

                throw new Exception("Could not save inventory availability!");

            }


            ps.close();


            if (!(ps.executeUpdate() > 0)) {

                throw new Exception("Could not save inventory availability!");

            }


        } catch (Exception ex) {


            ex.printStackTrace();

            throw ex;

        } finally {

            try {
                ps.close();
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

                stmt = cxn.prepareStatement(deleteStatement1);

                stmt.setInt(1, inventory_id);


                int rowsUpdated = stmt.executeUpdate();


                if (rowsUpdated < 1) throw new Exception("Cannot delete receive item ID " + inventory_id);


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

            PreparedStatement ps = null;

            try {

                ps = cxn.prepareStatement(updateStatement1);

                ps.setInt(1, inventory_id);

                ps.setString(2, inventory_num);

                ps.setString(3, description);

                ps.setString(4, item_status);

                ps.setString(5, is_unusable);

                ps.setInt(6, quantity);

                ps.setString(7, location);

                ps.setString(8, com.owd.core.OWDUtilities.getSQLDateTimeForToday());

                ps.setString(9, com.owd.core.OWDUtilities.getCurrentUserName());

                ps.setString(10, return_reason);

                ps.setInt(11, reported);

                ps.setInt(12, receive_item_id);


                int rowsUpdated = ps.executeUpdate();


                if (rowsUpdated < 1)

                    throw new Exception("Could not update receive item id " + receive_item_id);


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


    private static ReceiveItem dbload(Connection cxn, String id) throws Exception {


        PreparedStatement stmt = null;

        ReceiveItem pack = null;


        try {

            stmt = cxn.prepareStatement(loadStatement);

            stmt.setInt(1, new Integer(id).intValue());

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();

            if (rs.next()) {

                pack = new ReceiveItem(id,

                        rs.getString(kdbReceiveFkey),

                        rs.getString(kdbReceiveItemInvFkey),

                        rs.getString(kdbReceiveItemInvNum),

                        rs.getString(kdbReceiveItemDescription),

                        rs.getString(kdbReceiveItemStatus),

                        rs.getString(kdbReceiveItemUnusable),

                        rs.getString(kdbReceiveItemQuantity),

                        rs.getString(kdbReceiveItemLocation),

                        rs.getString(kdbReceiveItemCreateDate),

                        rs.getString(kdbReceiveItemCreatedBy),

                        rs.getString(kdbReceiveItemModifyDate),

                        rs.getString(kdbReceiveItemModifiedBy),

                        rs.getString(kdbReceiveItemReturnReason),

                        rs.getString(kdbReceiveItemReported));

            } else {

                throw new Exception("Could not load receove item id!");

            }


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }


        return pack;

    }


    public static java.util.Vector getItemsForReceive(Connection cxn, String receiveID) throws Exception {


        java.util.Vector items = new java.util.Vector();

        Statement stmt = null;

        ResultSet rs = null;


        try {

            stmt = cxn.createStatement();

            String isql = "select * from " + kdbReceiveItemTable + " where receive_fkey = " + receiveID;


             rs = stmt.executeQuery(isql);


            if (rs != null) {


                while (rs.next()) {

                    items.addElement(new ReceiveItem(receiveID,

                            rs.getString(kdbReceiveFkey),

                            rs.getString(kdbReceiveItemInvFkey),

                            rs.getString(kdbReceiveItemInvNum),

                            rs.getString(kdbReceiveItemDescription),

                            rs.getString(kdbReceiveItemStatus),

                            rs.getString(kdbReceiveItemUnusable),

                            rs.getString(kdbReceiveItemQuantity),

                            rs.getString(kdbReceiveItemLocation),

                            rs.getString(kdbReceiveItemCreateDate),

                            rs.getString(kdbReceiveItemCreatedBy),

                            rs.getString(kdbReceiveItemModifyDate),

                            rs.getString(kdbReceiveItemModifiedBy),

                            rs.getString(kdbReceiveItemReturnReason),

                            rs.getString(kdbReceiveItemReported)));

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


        return items;

    }

    public void getEditForm(HttpServletRequest req, HttpServletResponse resp, String message) throws IOException {

        resp.getWriter().write("<HR><FONT color=red><B>ERROR: " + message + "</B></FONT><HR>");

        getEditForm(req, resp);

    }


    public void getEditForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        StringBuffer form = new StringBuffer();


        resp.getWriter().write(form.toString());

    }


}

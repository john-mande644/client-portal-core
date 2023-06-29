package com.owd.core.business.order;


import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;


public class Inventory {
    private final static Logger log = LogManager.getLogger();

    //IDs are zero if the item is new


    //Attributes

    public static final String kdbInventoryPKName = "inventory_id";

    public int inventory_id = 0;


    public static final String kdbInventoryClientFkey = "client_fkey";

    public int client_fkey = 0;


    public static final String kdbInventoryCreatedBy = "created_by";

    public String created_by = "";


    public static final String kdbInventoryCreatedOn = "created_date";

    public String created_date = "";


    public static final String kdbInventoryModifiedBy = "modified_by";

    public String modified_by = "";


    public static final String kdbInventoryModifiedOn = "modified_date";

    public String modified_date = "";


    //Properties

    public static final String kdbInventoryRef = "inventory_num";

    public String inventory_num = "";


    public static final String kdbInventoryBarcode = "barcode_no";

    public String barcode_no = "";


    public static final String kdbSupplierRef = "mfr_part_num";

    public String mfr_part_num = "";


    public static final String kdbKeyword = "keyword";

    public String keyword = "";


    public static final String kdbPriceEach = "price";

    public float price = 0;


    public static final String kdbColor = "item_color";

    public String item_color = "";


    public static final String kdbSize = "item_size";

    public String item_size = "";


    public static final String kdbClientKey = "client_item_key";

    public int client_item_key = 0;


    public static final String kdbNotes = "notes";

    public String notes = "";


    public static final String kdbDescription = "description";

    public String description = "";


    public static final String kdbLongDescription = "long_desc";

    public String long_desc = "";


    public static final String kdbReorder = "qty_reorder";

    public int qty_reorder = 0;


    public static final String kdbIsActive = "is_active";

    public int is_active = 1;


    public static final String kdbLocation = "front_location";

    public String front_location = "UNKNOWN";


    public static final String kdbAvailable = "qty_on_hand";

    public int qty_on_hand = 0;


    public static final String kdbHarmonizedCode = "harm_code";

    public String harm_code = "";


    public static final String kdbSupplierCost = "supp_cost";

    public float supp_cost = (float) 0.0;


    public static final String kdbSupplierFKey = "supp_fkey";

    public int supp_fkey = 0;


    public static final String kdbWeight = "weight_lbs";

    public float weight_lbs = (float) 0.0;


    public static final String kdbCustomsValue = "customs_value";

    public float customs_value = (float) 0.0;


    public static final String kdbCustomsDesc = "customs_desc";

    public String customs_desc = "";


    public static final String kdbForceShipment = "force_shipment";

    public int force_shipment = 0;


    public static final String kdbShipMethodUS = "force_method_us";

    public String force_method_us = "";


    public static final String kdbShipMethodPO = "force_method_po";

    public String force_method_po = "";


    public static final String kdbShipMethodCAN = "force_method_can";

    public String force_method_can = "";


    public static final String kdbShipMethodINT = "force_method_int";

    public String force_method_int = "";


    public static final String kdbIsDocuments = "is_documents";

    public int is_documents = 0;

    public static final String kdbIsAutoInventory = "is_auto_inventory";

    public int is_auto_inventory = 0;

    public static final String kdbIsNoScanRequired = "no_scan";

    public int no_scan = 0;

    public static final String kdbPackingInstructions = "packing_instructions";

    public String packing_instructions = "";
    public static final String kdbUrl = "url";
    public String url = "";
    public static final String kdbDefaultFacility = "default_facility_code";
    public String defaultFacilityCode = "";
    public static final String kdbIsInsert = "is_insert";
    public int isInsert = 0;
    public static final String kdbIsLotTracked = "lot_tracking";
    public int isLotTracked = 0;

    public static final String kdbIsBulkyPick = "is_bulky_pick";
    public int isBulkyPick = 0;
    public static final String kdbIsBulkyPack = "is_bulky_pack";
    public int isBulkyPack = 0;
    public static final String kdbBulkyPickOverride = "bulky_pick_override";
    public int bulkyPickOverride = 0;
    public static final String kdbBulkyPackOverride = "bulky_pack_override";
    public int bulkyPackOverride = 0;
    public static final String kdbBulkyPickFeeOverride = "bulky_pick_fee_override";
    public float bulkyPickFeeOverride = 0.00f;

    public static final String kdbBulkyPackFeeOverride = "bulky_pack_fee_override";
    public float bulkyPackFeeOverride = 0.00f;



    public static final String kdbLength = "i_length";
    public float length = 0.00f;
    public static final String kdbWidth = "i_width";
    public float width = 0.00f;
    public static final String kdbHeight = "i_height";
    public float height = 0.00f;
    public static final String kdbCubicFeet = "cubic_feet";
    public float cubic_feet = 0.00f;


    public static final String kdbIsShipSystemWeight = "ship_system_weight";
    public int isShipSystemWeight = 0;
    public static final String kdbGroupName = "group_name";
    public String groupName = "";

    public static final String kdbUpcCode = "upc_code";
    public String upcCode = "";

    public static final String kdbIsbnCode = "isbn_code";
    public String isbnCode = "";

    public static final String kdbCaseQty = "case_qty";
    public String caseQty = "";

    public static final String kdbSupplierName = "supplier";
    public String supplierName = "";

    public static final String kdbPackQuantity = "pack_quantity";
    public int packQty = 1;

    private static final String kdbInventoryTable = "owd_inventory";

    private static final String kdbClientInventoryTable = "owd_client_inv";


    private static final String loadStatement = "select i.description," +

            "i.long_desc," +

            " i.inventory_num," +

            " i.client_fkey," +

            " i.barcode_no," +

            " i.mfr_part_num," +

            " i.keyword," +

            " i.price," +

            " i.item_color," +

            " i.item_size," +

            " i.client_item_key," +

            " i.notes," +

            " i.qty_reorder," +

            " i.is_active," +

            " i.front_location," +

            " i.modified_by," +

            " i.modified_date," +

            " i.created_by," +

            " i.created_date," +

            " i.harm_code," +

            " i.supp_cost," +

            " i.supp_fkey," +

            " i.weight_lbs," +

            " i.is_auto_inventory," +

            " i.no_scan," +

            "i.default_facility_code," +

            " ISNULL(o.qty_on_hand,0) as qty_on_hand," +

            " ISNULL(r.force_shipment,0) as force_shipment," +

            " ISNULL(r.force_method_us,\'\') as force_method_us," +

            " ISNULL(r.force_method_po,\'\') as force_method_po," +

            " ISNULL(r.force_method_can,\'\') as force_method_can," +

            " ISNULL(r.force_method_int,\'\') as force_method_int," +

            " ISNULL(r.is_documents,0) as is_documents," +

            " ISNULL(i.customs_desc,\'\') as customs_desc," +

            " ISNULL(i.customs_value,0) as customs_value," +

            "ISNULL(pack.instructions,'') as packing_instructions, pack.url as url, is_insert as is_insert,lot_tracking as lot_tracking,group_name as group_name, upc_code as upc_code, isbn_code as isbn_code, case_qty as case_qty, ship_system_weight as ship_system_weight, i_length, i_height, i_width, cubic_feet," +

            "is_bulky_pick, is_bulky_pack, bulky_pick_override, bulky_pack_override, bulky_pick_fee_override, bulky_pack_fee_override" +

            " from owd_inventory i (NOLOCK) " +
            "left outer join special_instructions pack (NOLOCK) on i.inventory_id=pack.external_id and activity_type='INVENTORY-PACK' " +

            " left outer join owd_client_inv r (NOLOCK)  on (r.inventory_fkey = i.inventory_id and r.client_fkey = i.client_fkey) " +

            " left outer join owd_inventory_oh o (NOLOCK) " +

            " on (o.inventory_fkey = i.inventory_id) where inventory_id = ? ";


    private static final String loadByPartStatement = "select i.description," +

            "i.long_desc," +

            " i.inventory_id," +

            " i.client_fkey," +

            " i.barcode_no," +

            " i.mfr_part_num," +

            " i.keyword," +

            " i.price," +

            " i.item_color," +

            " i.item_size," +

            " i.client_item_key," +

            " i.notes," +

            " i.qty_reorder," +

            " i.is_active," +

            " i.front_location," +

            " i.modified_by," +

            " i.modified_date," +

            " i.created_by," +

            " i.created_date," +

            " i.harm_code," +

            " i.supp_cost," +

            " i.supp_fkey," +

            " i.weight_lbs," +

            " i.is_auto_inventory," +

            " i.no_scan," +

            "i.default_facility_code," +

            " ISNULL(o.qty_on_hand,0) as qty_on_hand," +

            " ISNULL(r.force_shipment,0) as force_shipment," +

            " ISNULL(r.force_method_us,\'\') as force_method_us," +

            " ISNULL(r.force_method_po,\'\') as force_method_po," +

            " ISNULL(r.force_method_can,\'\') as force_method_can," +

            " ISNULL(r.force_method_int,\'\') as force_method_int," +

            " ISNULL(r.is_documents,0) as is_documents," +

            " ISNULL(i.customs_desc,\'\') as customs_desc," +

            " ISNULL(i.customs_value,0) as customs_value," +
            "ISNULL(pack.instructions,'') as packing_instructions, pack.url as url, is_insert as is_insert,lot_tracking as lot_tracking,group_name as group_name, upc_code as upc_code, isbn_code as isbn_code, case_qty as case_qty, ship_system_weight as ship_system_weight," +
            "i_length, i_height, i_width, cubic_feet,is_bulky_pick, is_bulky_pack, bulky_pick_override, bulky_pack_override, bulky_pick_fee_override, bulky_pack_fee_override" +

            " from owd_inventory i (NOLOCK) " +
            "left outer join special_instructions pack (NOLOCK) on i.inventory_id=pack.external_id and activity_type='INVENTORY-PACK' " +
            " left outer join owd_client_inv r (NOLOCK)  on (r.inventory_fkey = i.inventory_id and r.client_fkey = i.client_fkey) " +

            " left outer join owd_inventory_oh o (NOLOCK) " +

            "on (o.inventory_fkey = i.inventory_id) where inventory_num = ? AND i.client_fkey = ? ";


    private static final String updateStatement1 = "update owd_inventory WITH (ROWLOCK) set description = ?," +

            " long_desc = ?," +

            " inventory_num = ?," +

            " barcode_no = ?," +

            " mfr_part_num = ?," +

            " keyword = ?," +

            " price = ?," +

            " item_color = ?," +

            " item_size = ?," +

            " client_item_key = ?," +

            " notes = ?," +

            " qty_reorder = ?," +

            " is_active = ?," +

            " front_location = ?," +

            " harm_code = ?," +

            " supp_cost = ?," +

            " supp_fkey = ?, weight_lbs = ?, customs_desc=?,customs_value=?,is_auto_inventory=?,no_scan=?,is_insert=?,lot_tracking=?,i_length = ?, i_height = ?, i_width = ?, cubic_feet= ?,ship_system_weight=?," +
            "is_bulky_pick = ?, is_bulky_pack =?, bulky_pick_override =?, bulky_pack_override =?, bulky_pick_fee_override =?, bulky_pack_fee_override =?   where inventory_id = ? and client_fkey = ?";


    private static final String updateStatement2 = "select count(*) from owd_client_inv (NOLOCK) where inventory_fkey = ? and client_fkey = ?";


    private static final String updateStatement3 = "update owd_client_inv WITH (ROWLOCK) set force_shipment = ?," +

            " force_method_us = ?," +

            " force_method_po = ?," +

            " force_method_can = ?," +

            " force_method_int = ?," +

            " is_documents = ? where inventory_fkey = ? and client_fkey = ?";

    // private static final 	 String updateStatement4 = "exec sp_set_special_instructions ?,?,?";


    private static final String deleteCheckStatement = "select (select count(line_item_id) from owd_line_item l (NOLOCK) "

            + "where l.inventory_id = i.inventory_id)+(select count(receive_item_id) "

            + "from owd_receive_item r (NOLOCK) where r.inventory_id = i.inventory_id) "
            + "+(select count(id) from receive_item ri (NOLOCK) where ri.inventory_fkey = i.inventory_id) "

            + "from owd_inventory i (NOLOCK) where i.inventory_id= ?";


    private static final String deleteStatement0 = "delete from special_instructions WITH (ROWLOCK) where external_id = ?  and activity_type='INVENTORY-PACK'";
    private static final String deleteStatement1 = "delete from owd_inventory_oh WITH (ROWLOCK) where inventory_fkey = ?";


    private static final String deleteStatement2 = "delete from owd_client_inv WITH (ROWLOCK) where inventory_fkey = ? and client_fkey = ?";


    private static final String deleteStatement3 = "delete from owd_inventory WITH (ROWLOCK) where inventory_id = ? and client_fkey = ?";


    private static final String createStatement1 = "insert into owd_inventory (description," +

            "long_desc," +

            "inventory_num," +

            "barcode_no," +

            " mfr_part_num," +

            " keyword," +

            " price," +

            " item_color," +

            " item_size," +

            " client_item_key," +

            " notes," +

            " qty_reorder," +

            " is_active," +

            " front_location, client_fkey," +

            " modified_by," +

            " modified_date," +

            " created_by," +

            " created_date," +

            " harm_code," +

            " supp_cost," +

            " supp_fkey," +

            " weight_lbs,customs_desc,customs_value,is_auto_inventory,no_scan,is_insert,lot_tracking,ship_system_weight,i_length,i_height,i_width,cubic_Feet,is_bulky_pick, is_bulky_pack, bulky_pick_override, bulky_pack_override, bulky_pick_fee_override, bulky_pack_fee_override, supplier, pack_quantity) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private static final String createStatement2 = "select inventory_id from owd_inventory (NOLOCK) where client_fkey = ?";


    private static final String createStatement3 = "update owd_inventory WITH (ROWLOCK) set client_fkey = ? where inventory_id = ?";


    private static final String createStatement4 = "insert into owd_inventory_oh (inventory_fkey,qty_on_hand) VALUES (?,?)";


    private static final String createStatement5 = "update owd_inventory WITH (ROWLOCK) set barcode_no = ? where inventory_id = ?";


    private static final String createStatement6 = "insert into owd_client_inv (inventory_fkey,client_fkey,force_shipment,force_method_us," +

            " force_method_po,force_method_can,force_method_int,is_documents) VALUES (?,?,?,?,?,?,?,?)";


    private static final String createStatement7 = "exec sp_set_special_instructions2 ?,?,?,?";


    private static final String setInventoryStatement = "insert into owd_inventory_history (qty_change,inventory_fkey,note) VALUES (?,?,'Inventory.setAvailability')";

    private static final String resetInventoryStatement = "update owd_inventory_oh WITH (ROWLOCK, UPDLOCK) set qty_on_hand = (?) where inventory_fkey = ?";


    private boolean needsUpdate = false;


    //field names of the owd_inventory table
    public static final String kinventory_id = "inventory_id";//1
    public static final String kclient_fkey = "client_fkey";//2
    public static final String kinventory_num = "inventory_num";//3
    public static final String kmfr_part_num = "mfr_part_num";//4
    public static final String kdescription = "description";//5
    public static final String kkeyword = "keyword";//6
    public static final String kprice = "price"; //7
    public static final String kis_active = "is_active"; //8
    public static final String kbarcode_no = "barcode_no"; //9
    public static final String kfront_location = "front_location"; //10
    public static final String kback_location = "back_location"; //11
    public static final String kunusable_location = "unusalble_location";//12
    public static final String kqty_reorder = "qty_reorder"; //13
    public static final String kqty_unusable = "qty_unusable"; //14
    public static final String kqty_high_alert = "qty_high_alert"; //15
    public static final String kitem_cost = "item_cost"; //16
    public static final String kitem_ship_cost = "item_ship_cost"; //17
    public static final String kis_insurable = "is_insurable"; //18
    public static final String knotes = "notes"; //19
    public static final String kcreated_date = "created_date"; //20
    public static final String kcreated_by = "created_by";//21
    public static final String kmodified_date = "modified_date"; //22
    public static final String kmodified_by = "modified_by"; //23
    public static final String krow_is_locked = "row_is_locked"; //24
    public static final String kitem_color = "item_color"; //25
    public static final String kitem_size = "item_size"; //26
    public static final String kclient_item_key = "client_item_key"; //27
    public static final String klong_desc = "long_desc"; //28
    public static final String ksupp_cost = "supp_cost";//29
    public static final String ksupp_fkey = "supp_fkey"; //30
    public static final String kharm_code = "harm_code"; //31
    public static final String kweight_lbs = "weight_lbs"; //32
    public static final String kdocuments_only = "documents_only";//33
    public static final String kcustoms_desc = "customs_desc";//33
    public static final String kis_auto_inventory = "is_auto_inventory";//33
    public static final String kcustoms_value = "customs_value";//33
    public static final String kno_scan = "no_scan";//33
    public static final String kspecial_instructions = "no_scan";//33
    public static final String kis_insert = "is_insert";//33
    public static final String klot_tracking = "lot_tracking";//33
    public static final String kship_system_weight = "ship_system_weight";
    public static final String kgroup_name = "group_name";
    public static final String kupc_code = "upc_code";
    public static final String kisbn_code = "isbn_code";
    public static final String ki_length = "i_length";
    public static final String ki_height = "i_height";
    public static final String ki_width = "i_width";
    public static final String kcubic_feet = "cubic_feet";
    public static final String kcase_qty = "case_qty";
    public static final String kisBulkyPick = "is_bulky_pick";
    public static final String kisBulkyPack = "is_bulky_pack";
    public static final String kbulkyPickOverride = "bulky_pick_override";
    public static final String kbulkyPackOverride = "bulky_pack_override";
    public static final String kbulkyPickFeeOverride = "bulky_pick_fee_override";
    public static final String kbulkyPackFeeOverride = "bulky_pack_fee_override";

    private static final String[] fieldnames = {kinventory_id, kclient_fkey, kinventory_num, kmfr_part_num,
            kdescription, kkeyword, kprice, kis_active, kbarcode_no, kfront_location, kback_location, kunusable_location,
            kqty_reorder, kqty_unusable, kqty_high_alert, kitem_cost, kitem_ship_cost, kis_insurable, knotes, kcreated_date, kcreated_by,
            kmodified_date, kmodified_by, krow_is_locked, kitem_color, kitem_size, kclient_item_key, klong_desc, ksupp_cost,
            ksupp_fkey, kharm_code, kweight_lbs, kdocuments_only, kcustoms_desc, kcustoms_value, kis_insert, klot_tracking, kship_system_weight, kgroup_name, kupc_code, kisbn_code, kcase_qty, ki_length, ki_height,ki_width,kcubic_feet,
            kisBulkyPick,kisBulkyPack,kbulkyPickOverride,kbulkyPackOverride, kbulkyPickFeeOverride, kbulkyPackFeeOverride};

    public static float getWeight(String invID) {

        Connection cxn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;


        float wgt = (float) 0.0;

        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            ps = cxn.prepareStatement("select ISNULL(weight_lbs,0.0) from owd_inventory (NOLOCK) where inventory_id = ?");

            ps.setInt(1, new Integer(invID).intValue());


            ps.executeQuery();

            rs = ps.getResultSet();

            if (rs.next()) {

                wgt = rs.getFloat(1);

            }


        } catch (Exception ex) {

            ex.printStackTrace();


        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                ps.close();
            } catch (Exception exc) {
            }

            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }

        return wgt;

    }

    public static float getKittedWeight(String invID) {

        Connection cxn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;


        float wgt = (float) 0.0;

        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            ps = cxn.prepareStatement("select \n" +
                    " case when is_auto_inventory=0\n" +
                    " then ISNULL(weight_lbs,0.0)\n" +
                    " else \n" +
                    "    (select sum(x.weight_lbs*ISNULL(req_inventory_quant,0)) \n" +
                    "         from owd_inventory x (NOLOCK)\n" +
                    "         left outer join owd_inventory_required_skus s (NOLOCK)\n" +
                    "            on s.req_inventory_fkey=x.inventory_id and s.inventory_fkey=?   )\n" +
                    " end\n" +
                    " from owd_inventory (NOLOCK) \n" +
                    " where inventory_id = ?   ");

            ps.setInt(1, new Integer(invID).intValue());
            ps.setInt(2, new Integer(invID).intValue());


            ps.executeQuery();

            rs = ps.getResultSet();

            if (rs.next()) {

                wgt = rs.getFloat(1);

            }


        } catch (Exception ex) {

            ex.printStackTrace();


        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                ps.close();
            } catch (Exception exc) {
            }

            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }

        return OWDUtilities.roundFloat(wgt, 2);

    }

    public static int getUniqueSkuWithPrefixAndNotSuffix(String prefix, String clientID, String suffix) throws Exception {

        Connection cxn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;


        int id = (int) 0;

        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            ps = cxn.prepareStatement("select inventory_id from owd_inventory (NOLOCK) where inventory_num like ? and is_active=1 and client_fkey = ? and inventory_num not like ? ");
            ps.setString(1, prefix + '%');

            ps.setInt(2, new Integer(clientID).intValue());
            ps.setString(3, '%' + suffix);


            ps.executeQuery();

            rs = ps.getResultSet();

            while (rs.next()) {

                if (id != 0) throw new Exception("Prefix " + prefix + " not unique! Multiple SKUs returned.");

                id = rs.getInt(1);

            }


        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;


        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                ps.close();
            } catch (Exception exc) {
            }

            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }

        return id;

    }

/*     public void setAvailability(long amount) throws Exception {

        Connection cxn = null;

        try {

                  cxn = ConnectionManager.getConnection();
                cxn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            setAvailability(cxn, amount);

            cxn.commit();
        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }


    }*/

  /*   public void setAvailability(Connection cxn, long amount) throws Exception {

        PreparedStatement ps = null;
        cxn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        try {

            ps = cxn.prepareStatement(resetInventoryStatement);

            ps.setLong(1, amount);

            ps.setInt(2, inventory_id);


            int rowsUpdated = ps.executeUpdate();



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
   */

    public void setAvailabilityEverywhere(int amountAdd) throws Exception {

        Connection cxn = null;

        try {

            cxn = ConnectionManager.getConnection();
            cxn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            setAvailabilityEverywhere(cxn, amountAdd);

            cxn.commit();
        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }


    }

    public void setAvailabilityEverywhere(Connection cxn, int amountAdd) throws Exception {


        try {

            for (String code : FacilitiesManager.getActiveFacilityCodes()) {
                InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inventory_id, client_fkey, amountAdd, OWDUtilities.getSQLDateNoTimeForToday(), "ADDALL" + Calendar.getInstance().getTimeInMillis(), code, HibernateSession.currentSession());
            }
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
            } catch (Exception exc) {
            }


        }


    }

    public void setAvailabilityAtFacility(int amountAdd, String facilityCode) throws Exception {

        Connection cxn = null;

        try {

            cxn = ConnectionManager.getConnection();
            cxn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            setAvailabilityAtFacility(cxn, amountAdd, facilityCode);

            cxn.commit();
        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }


    }

    public void setAvailabilityAtFacility(Connection cxn, int amountAdd, String facilityCode) throws Exception {


        try {

            if (FacilitiesManager.getActiveFacilityCodes().contains(facilityCode)) {
                InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inventory_id, client_fkey, amountAdd, OWDUtilities.getSQLDateNoTimeForToday(), "ADDALL" + Calendar.getInstance().getTimeInMillis(), facilityCode, HibernateSession.currentSession());
            } else {
                throw new Exception("Attempt to add inventory to invalid faciloity code " + facilityCode);
            }
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
            } catch (Exception exc) {
            }


        }


    }

    public void addToAvailabilityEverywhere(int amountAdd) throws Exception {

        Connection cxn = null;

        try {

            cxn = ConnectionManager.getConnection();
            cxn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addToAvailabilityEverywhere(cxn, amountAdd);

            cxn.commit();
        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }


    }

    public void addToAvailabilityEverywhere(Connection cxn, int amountAdd) throws Exception {


        try {

            for (String code : FacilitiesManager.getActiveFacilityCodes()) {
                InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(inventory_id, client_fkey, amountAdd, OWDUtilities.getSQLDateNoTimeForToday(), "ADDALL" + Calendar.getInstance().getTimeInMillis(), code, HibernateSession.currentSession());
            }
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
            } catch (Exception exc) {
            }


        }


    }

    public void addToAvailabilityAtFacility(int amountAdd, String facilityCode) throws Exception {

        Connection cxn = null;

        try {

            cxn = ConnectionManager.getConnection();
            cxn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addToAvailabilityAtFacility(cxn, amountAdd, facilityCode);

            cxn.commit();
        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception exc) {
            }


        }


    }

    public void addToAvailabilityAtFacility(Connection cxn, int amountAdd, String facilityCode) throws Exception {


        try {

            if (FacilitiesManager.getActiveFacilityCodes().contains(facilityCode)) {
                InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(inventory_id, client_fkey, amountAdd, OWDUtilities.getSQLDateNoTimeForToday(), "ADDALL" + Calendar.getInstance().getTimeInMillis(), facilityCode, HibernateSession.currentSession());
            } else {
                throw new Exception("Attempt to add inventory to invalid faciloity code " + facilityCode);
            }
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;

        } finally {

            try {
            } catch (Exception exc) {
            }


        }


    }

    public Inventory(String aclient_id)

    {

        try {

            client_fkey = (new Integer("0" + aclient_id).intValue());

        } catch (NumberFormatException ex) {
            client_fkey = (int) 0;
        }

    }


    public Inventory(String aid,

                     String aclient_id,

                     String ainventory_num,

                     String abarcode_no,

                     String amfr_part_num,

                     String akeyword,

                     String aprice,

                     String aitem_color,

                     String aitem_size,

                     String aclient_item_key,

                     String anotes,

                     String adescription,

                     String along_desc,

                     String aqty_reorder,

                     String ais_active,

                     String afront_location,

                     String aqty_on_hand,

                     String acreated_by,

                     String acreated_on,

                     String amodified_by,

                     String amodified_on,

                     String aharm_code,

                     String asupp_cost,

                     String asupp_fkey,

                     String aweight_lbs,

                     String aforce_shipment,

                     String aforce_method_us,

                     String aforce_method_po,

                     String aforce_method_can,

                     String aforce_method_int,

                     String ais_documents,
                     String acustoms_desc,
                     String acustoms_value,
                     String ais_auto_inventory,
                     String ano_scan,
                     String apack_instructions,
                     String aurl,
                     String aDefaultFacility,
                     String aIsInsert,
                     String aIsLotTracked,
                     String aLength,
                     String aWidth,
                     String aHeight,
                     String aCubicFeet,
                     String aGroupName,
                     String aUpcCode,
                     String aIsbnCode,
                     String aCaseQty,
                     String aIsShipSystemWeight,
                     String aIsBulkyPick,
                     String aIsBulkyPack,
                     String aBulkyPickOverride,
                     String aBulkyPackOverride,
                     String aBulkyPickFeeOverride,
                     String aBulkyPackFeeOverride

    )

    {

        keyword = akeyword;

        description = adescription;

        long_desc = along_desc;

        inventory_num = ainventory_num;

        barcode_no = abarcode_no;


        mfr_part_num = amfr_part_num;

        item_color = aitem_color;

        item_size = aitem_size;

        front_location = afront_location;

        notes = anotes;

        harm_code = aharm_code;


        force_method_us = aforce_method_us;

        force_method_po = aforce_method_po;

        force_method_can = aforce_method_can;

        force_method_int = aforce_method_int;


        customs_desc = acustoms_desc;
        packing_instructions = apack_instructions;
        url = aurl;
        defaultFacilityCode = aDefaultFacility;
        groupName = aGroupName;
        upcCode = aUpcCode;
        isbnCode = aIsbnCode;
        caseQty = aCaseQty;

        try {

            isInsert = (new Integer("0" + aIsInsert).intValue());

        } catch (NumberFormatException ex) {
            isInsert = (int) 0;
        }

        try {

            isLotTracked = (new Integer("0" + aIsLotTracked).intValue());

        } catch (NumberFormatException ex) {
            isLotTracked = (int) 0;
        }


        try{
            isBulkyPick = (new Integer("0"+aIsBulkyPick).intValue());
        }catch (NumberFormatException ex){
            isBulkyPick = 0;
        }
        try{
            isBulkyPack = (new Integer("0"+aIsBulkyPack).intValue());
        }catch (NumberFormatException ex){
            isBulkyPack = 0;
        }
        try{
            bulkyPickOverride = (new Integer("0"+aBulkyPickOverride).intValue());
        }catch (NumberFormatException ex){
            bulkyPickOverride = 0;
        }
        try{
            bulkyPackOverride = (new Integer("0"+aBulkyPackOverride).intValue());
        }catch (NumberFormatException ex){
            bulkyPackOverride = 0;
        }

        try{
            bulkyPickFeeOverride = new Float("0"+aBulkyPickFeeOverride);
        }catch (NumberFormatException ex){
            bulkyPickFeeOverride = 0.00f;
        }

        try{
            bulkyPackFeeOverride = new Float("0"+aBulkyPackFeeOverride);
        }catch (NumberFormatException ex){
            bulkyPackFeeOverride = 0.00f;
        }



        try {
            length = new Float("0" + aLength);
        } catch (NumberFormatException ex) {
            length = 0.00f;
        }
        try {
            width = new Float("0" + aWidth);
        } catch (NumberFormatException ex) {
            width = 0.00f;
        }
        try {
            height = new Float("0" + aHeight);
        } catch (NumberFormatException ex) {
            height = 0.00f;
        }
        try {
            cubic_feet = new Float("0" + aCubicFeet);
        } catch (NumberFormatException ex) {
            cubic_feet = 0.00f;
        }

        try {

            isShipSystemWeight = (new Integer("0" + aIsShipSystemWeight).intValue());

        } catch (NumberFormatException ex) {
            isShipSystemWeight = (int) 0;
        }


        try {

            force_shipment = (new Integer("0" + aforce_shipment).intValue());

        } catch (NumberFormatException ex) {
            force_shipment = (int) 0;
        }


        try {

            is_documents = (new Integer("0" + ais_documents).intValue());

        } catch (NumberFormatException ex) {
            is_documents = (int) 0;
        }


        try {

            inventory_id = (new Integer("0" + aid).intValue());

        } catch (NumberFormatException ex) {
            inventory_id = (int) 0;
        }

        try {

            client_fkey = (new Integer("0" + aclient_id).intValue());

        } catch (NumberFormatException ex) {
            client_fkey = (int) 0;
        }


        try {

            customs_value = (new Float("0" + acustoms_value).floatValue());

        } catch (NumberFormatException ex) {
            customs_value = (float) 0.0;
        }


        try {

            supp_cost = (new Float("0" + asupp_cost).floatValue());

        } catch (NumberFormatException ex) {
            supp_cost = (float) 0.0;
        }


        try {

            weight_lbs = (new Float("0" + aweight_lbs).floatValue());

        } catch (NumberFormatException ex) {
            weight_lbs = (float) 0.0;
        }


        try {

            price = (new Float("0" + aprice).floatValue());

        } catch (NumberFormatException ex) {
            price = (float) 0.0;
        }


        try {

            client_item_key = (new Integer("0" + aclient_item_key).intValue());

        } catch (NumberFormatException ex) {
            client_item_key = (int) 0;
        }


        try {

            qty_reorder = (new Integer("0" + aqty_reorder).intValue());

        } catch (NumberFormatException ex) {
            qty_reorder = (int) 0;
        }


        try {

            is_active = (new Integer("0" + ais_active).intValue());

        } catch (NumberFormatException ex) {
            is_active = (int) 0;
        }


        try {

            qty_on_hand = (new Integer("0" + aqty_on_hand).intValue());

        } catch (NumberFormatException ex) {
            qty_on_hand = (int) 0;
        }


        try {

            supp_fkey = (new Integer("0" + asupp_fkey).intValue());

        } catch (NumberFormatException ex) {
            supp_fkey = (int) 0;
        }


        created_by = acreated_by;

        created_date = acreated_on;

        modified_by = amodified_by;

        modified_date = amodified_on;

        if (null == barcode_no || "".equals(barcode_no)) {

            barcode_no = "" + inventory_id;

        }

        try {

            is_auto_inventory = (new Integer("0" + ais_auto_inventory).intValue());

        } catch (NumberFormatException ex) {
            is_auto_inventory = (int) 0;
        }

        try {

            no_scan = (new Integer("0" + ano_scan).intValue());

        } catch (NumberFormatException ex) {
            no_scan = (int) 0;
        }


    }

    public static Inventory getInventoryForID(String inventory_id) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Inventory inv = null;
        try {
            cxn = ConnectionManager.getConnection();

            inv = getInventoryForID(cxn, inventory_id);

        } catch (Exception e) {
            //////log.debug("Get Inventory error: "+e);
            throw e;

        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            ;
            try {
                stmt.close();
            } catch (Exception e) {
            }
            ;
            try {
                cxn.close();
            } catch (Exception e) {
            }
            ;
        }
        return inv;
    }


    public static Inventory getInventoryForID(Connection cxn, String ID) throws Exception {

        //load existing package

        return dbload(cxn, ID);

    }

    public static Hashtable getItemSKUandDescForClientID(String client_id) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Hashtable sku_desc = new Hashtable();

        try {
            cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement("select inventory_num, description from owd_inventory WITH (NOLOCK) where client_fkey = ?");
            stmt.setString(1, client_id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                sku_desc.put(rs.getString(1), rs.getString(2));
            }
        } catch (Exception e) {
            //////log.debug("Get Inventory error: "+e);
            throw e;

        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            ;
            try {
                stmt.close();
            } catch (Exception e) {
            }
            ;
            try {
                cxn.close();
            } catch (Exception e) {
            }
            ;
        }
        return sku_desc;
    }


    public boolean isModified()

    {

        return needsUpdate;

    }


    public boolean isNew()

    {

        if (0 == (inventory_id))

            return true;


        return false;

    }





   /* public ElementNode toXml(XmlDocument doc) throws IOException

    {



        //return Line Item node

        ElementNode root = (ElementNode) doc.createElement("xxx");



        return root;



    }*/


    public void dbsave(Connection cxn) throws Exception

    {


        PreparedStatement ps = null;


        try

        {


            String tempClient = ConnectionManager.getNextID("Inventory");


            ps = cxn.prepareStatement(createStatement1);

            ps.setString(1, description);

            ps.setString(2, long_desc);

            ps.setString(3, inventory_num);

            ps.setString(4, barcode_no);

            ps.setString(5, mfr_part_num);

            ps.setString(6, keyword);

            ps.setFloat(7, price);

            ps.setString(8, item_color);

            ps.setString(9, item_size);

            ps.setInt(10, client_item_key);

            ps.setString(11, notes);

            ps.setInt(12, qty_reorder);

            ps.setInt(13, is_active);

            ps.setString(14, front_location);

            ps.setInt(15, new Integer(tempClient).intValue());

            ps.setString(16, OWDUtilities.getCurrentUserName());

            ps.setString(17, OWDUtilities.getSQLDateTimeForToday());

            ps.setString(18, OWDUtilities.getCurrentUserName());

            ps.setString(19, OWDUtilities.getSQLDateTimeForToday());


            ps.setString(20, harm_code);

            ps.setFloat(21, supp_cost);

            ps.setInt(22, supp_fkey);

            ps.setFloat(23, weight_lbs);
            ps.setString(24, customs_desc);
            ps.setFloat(25, customs_value);
            ps.setFloat(26, is_auto_inventory);
            ps.setFloat(27, no_scan);
            ps.setInt(28, isInsert);
            ps.setInt(29, isLotTracked);
            ps.setFloat(30, length);
            ps.setFloat(31, width);
            ps.setFloat(32, height);
            ps.setFloat(33, cubic_feet);
            ps.setInt(34, isShipSystemWeight);
            ps.setInt(35,isBulkyPick);
            ps.setInt(36,isBulkyPack);
            ps.setInt(37,bulkyPickOverride);
            ps.setInt(38,bulkyPackOverride);
            ps.setFloat(39,bulkyPickFeeOverride);
            ps.setFloat(40,bulkyPackFeeOverride);
            ps.setString(41,supplierName);
            ps.setInt(42, packQty);

            ps.executeUpdate();


            ps.close();


            ps = cxn.prepareStatement(createStatement2);

            ps.setInt(1, new Integer(tempClient).intValue());

            ps.executeQuery();

            ResultSet rs = ps.getResultSet();

            if (rs.next()) {

                inventory_id = rs.getInt(1);

            } else {

                throw new Exception("Could not save inventory id!");

            }


            ps.close();


            ps = cxn.prepareStatement(createStatement3);


            ps.setInt(1, client_fkey);

            ps.setInt(2, inventory_id);


            ps.executeUpdate();


            ps.close();

            if (null == barcode_no || "".equals(barcode_no))

            {

                ps = cxn.prepareStatement(createStatement5);

                ps.setString(1, "" + inventory_id);

                ps.setInt(2, inventory_id);

                ps.executeUpdate();

                ps.close();

            }


            ps = cxn.prepareStatement(createStatement4);

            ps.setInt(1, inventory_id);

            ps.setInt(2, 0);


            ps.executeUpdate();


            ps.close();


            ps = cxn.prepareStatement(createStatement6);

            ps.setInt(1, inventory_id);

            ps.setInt(2, client_fkey);

            ps.setInt(3, force_shipment);

            ps.setString(4, force_method_us);

            ps.setString(5, force_method_po);

            ps.setString(6, force_method_can);

            ps.setString(7, force_method_int);

            ps.setInt(8, is_documents);


            ps.executeUpdate();

            ps.close();


            //todo
            ps = cxn.prepareStatement(createStatement7);

            ps.setInt(1, inventory_id);
            ps.setString(2, "INVENTORY-PACK");
            ps.setString(3, packing_instructions);
            ps.setString(4, url);


            ps.executeUpdate();


        } catch (Exception ex)

        {


            ex.printStackTrace();

            throw ex;

        } finally

        {

            try {
                ps.close();
            } catch (Exception exc) {
            }


        }


    }


    public void dbdelete(Connection cxn) throws Exception

    {


        if (isNew())

        {

            //do nothing

        } else {

            PreparedStatement stmt = null;

            ResultSet rs = null;

            try

            {

                int dependents = 0;

                stmt = cxn.prepareStatement(deleteCheckStatement);

                stmt.setInt(1, inventory_id);


                stmt.executeQuery();


                rs = stmt.getResultSet();

                while (rs.next())

                {

                    dependents += rs.getInt(1);

                }


                rs.close();


                stmt.close();

                if (dependents > 0)

                    throw new Exception("Can't delete this item; it is being used by other records");


                stmt = cxn.prepareStatement(deleteStatement0);

                stmt.setInt(1, inventory_id);


                int rowsUpdated = stmt.executeUpdate();


                //todo
                //    if (rowsUpdated < 1) throw new Exception("Cannot delete pack instruction ID "+inventory_id);


                stmt.close();


                stmt = cxn.prepareStatement(deleteStatement1);

                stmt.setInt(1, inventory_id);


                rowsUpdated = stmt.executeUpdate();


                //    if (rowsUpdated < 1) throw new Exception("Cannot delete inventory availability ID "+inventory_id);


                stmt.close();


                stmt = cxn.prepareStatement(deleteStatement2);

                stmt.setInt(1, inventory_id);

                stmt.setInt(2, client_fkey);


                rowsUpdated = stmt.executeUpdate();


                stmt.close();


                stmt = cxn.prepareStatement(deleteStatement3);

                stmt.setInt(1, inventory_id);

                stmt.setInt(2, client_fkey);


                rowsUpdated = stmt.executeUpdate();


                //     if (rowsUpdated < 1) throw new Exception("Cannot delete inventory ID "+inventory_id);


                stmt.close();

            } catch (Exception ex)

            {

                throw ex;

            } finally

            {

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

    }


    public void dbupdate(Connection cxn) throws Exception

    {

        ////////log.debug("Inventory.dbupdate");

        if (isNew())

        {

            dbsave(cxn);

        } else {

            PreparedStatement ps = null;

            ResultSet rs = null;

            try

            {

                ps = cxn.prepareStatement(updateStatement1);

                ps.setString(1, description);

                ps.setString(2, long_desc);

                ps.setString(3, inventory_num);

                ps.setString(4, barcode_no);

                ps.setString(5, mfr_part_num);

                ps.setString(6, keyword);

                ps.setFloat(7, price);

                ps.setString(8, item_color);

                ps.setString(9, item_size);

                ps.setInt(10, client_item_key);

                ps.setString(11, notes);

                ps.setInt(12, qty_reorder);

                ps.setInt(13, is_active);

                ps.setString(14, front_location);


                ps.setString(15, harm_code);

                ps.setFloat(16, supp_cost);

                ps.setInt(17, supp_fkey);

                ps.setFloat(18, weight_lbs);


                ps.setString(19, customs_desc);

                ps.setFloat(20, customs_value);


                ps.setFloat(21, is_auto_inventory);

                ps.setFloat(22, no_scan);
                ps.setInt(23, isInsert);
                ps.setInt(24, isLotTracked);
                ps.setFloat(25, length);
                ps.setFloat(26, height);
                ps.setFloat(27, width);
                ps.setFloat(28, cubic_feet);

                ps.setInt(29, isShipSystemWeight);
                ps.setInt(30,isBulkyPick);
                ps.setInt(31,isBulkyPack);
                ps.setInt(32,bulkyPickOverride);
                ps.setInt(33,bulkyPackOverride);
                ps.setFloat(34,bulkyPickFeeOverride);
                ps.setFloat(35,bulkyPackFeeOverride);






                ps.setInt(36, inventory_id);

                ps.setInt(37, client_fkey);


                int rowsUpdated = ps.executeUpdate();


                // if (rowsUpdated < 1)

                //     throw new Exception("Could not update inventory id "+inventory_id);

                ps.close();


                ps = cxn.prepareStatement(updateStatement2);

                ps.setInt(1, inventory_id);

                ps.setInt(2, client_fkey);

                ps.executeQuery();

                rs = ps.getResultSet();


                int hasRules = 0;


                if (rs.next()) {

                    hasRules = rs.getInt(1);

                }


                rs.close();

                ps.close();

                //todo
                ps = cxn.prepareStatement(updateStatement3);

                ps.setInt(1, force_shipment);

                ps.setString(2, force_method_us);

                ps.setString(3, force_method_po);

                ps.setString(4, force_method_can);

                ps.setString(5, force_method_int);

                ps.setInt(6, is_documents);

                ps.setInt(7, inventory_id);

                ps.setInt(8, client_fkey);


                rowsUpdated = ps.executeUpdate();


                //  if (rowsUpdated < 1)

                //      throw new Exception("Could not update inventory rules "+inventory_id);

                ps.close();


                if (hasRules < 1)

                {


                    ps = cxn.prepareStatement(createStatement6);

                    ps.setInt(1, inventory_id);

                    ps.setInt(2, client_fkey);

                    ps.setInt(3, force_shipment);

                    ps.setString(4, force_method_us);

                    ps.setString(5, force_method_po);

                    ps.setString(6, force_method_can);

                    ps.setString(7, force_method_int);

                    ps.setInt(8, is_documents);


                    ps.executeUpdate();


                }

                String updateStatement4 = "exec sp_set_special_instructions2 ?,?,?,?";


                ps = cxn.prepareStatement(updateStatement4);

                ps.setInt(1, inventory_id);
                ps.setString(2, "INVENTORY-PACK");
                ps.setString(3, packing_instructions);
                ps.setString(4, url);


                rowsUpdated = ps.executeUpdate();


                //   if (rowsUpdated < 1)

                //     throw new Exception("Could not update pack notes for item ID "+inventory_id);

                ps.close();


            } catch (Exception ex)

            {

                ex.printStackTrace();

                throw ex;

            } finally

            {

                try {
                    rs.close();
                } catch (Exception exc) {
                }

                try {
                    ps.close();
                } catch (Exception exc) {
                }


            }


        }


    }


    private static Inventory dbload(Connection cxn, String id) throws Exception

    {


        PreparedStatement stmt = null;

        Inventory pack = null;


        try

        {

            stmt = cxn.prepareStatement(loadStatement);

            stmt.setInt(1, new Integer(id).intValue());

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();

            if (rs.next()) {

                pack = new Inventory(id,

                        rs.getString(kdbInventoryClientFkey),

                        rs.getString(kdbInventoryRef),

                        rs.getString(kdbInventoryBarcode),

                        rs.getString(kdbSupplierRef),

                        rs.getString(kdbKeyword),

                        rs.getString(kdbPriceEach),

                        rs.getString(kdbColor),

                        rs.getString(kdbSize),

                        rs.getString(kdbClientKey),

                        rs.getString(kdbNotes),

                        rs.getString(kdbDescription),

                        rs.getString(kdbLongDescription),

                        rs.getString(kdbReorder),

                        rs.getString(kdbIsActive),

                        rs.getString(kdbLocation),

                        rs.getString(kdbAvailable),

                        rs.getString(kdbInventoryCreatedBy),

                        rs.getString(kdbInventoryCreatedOn),

                        rs.getString(kdbInventoryModifiedBy),

                        rs.getString(kdbInventoryModifiedOn),

                        rs.getString(kdbHarmonizedCode),

                        rs.getString(kdbSupplierCost),

                        rs.getString(kdbSupplierFKey),

                        rs.getString(kdbWeight),

                        rs.getString(kdbForceShipment),

                        rs.getString(kdbShipMethodUS),

                        rs.getString(kdbShipMethodPO),

                        rs.getString(kdbShipMethodCAN),

                        rs.getString(kdbShipMethodINT),

                        rs.getString(kdbIsDocuments),

                        rs.getString(kdbCustomsDesc),

                        rs.getString(kdbCustomsValue),

                        rs.getString(kdbIsAutoInventory),

                        rs.getString(kdbIsNoScanRequired),
                        rs.getString(kdbPackingInstructions),
                        rs.getString(kdbUrl),
                        rs.getString(kdbDefaultFacility),
                        rs.getString(kdbIsInsert),
                        rs.getString(kdbIsLotTracked),
                        rs.getString(kdbLength),
                        rs.getString(kdbWidth),
                        rs.getString(kdbHeight),
                        rs.getString(kdbCubicFeet),
                        rs.getString(kdbGroupName),
                        rs.getString(kdbUpcCode),
                        rs.getString(kdbIsbnCode),
                        rs.getString(kdbCaseQty),
                        rs.getString(kdbIsShipSystemWeight),
                        rs.getString(kdbIsBulkyPick),
                        rs.getString(kdbIsBulkyPack),
                        rs.getString(kdbBulkyPickOverride),
                        rs.getString(kdbBulkyPackOverride),
                        rs.getString(kdbBulkyPickFeeOverride),
                        rs.getString(kdbBulkyPackFeeOverride)
                );

            } else {

                throw new Exception("Could not save inventory id!");

            }


        } catch (Exception ex)

        {

            throw ex;

        } finally

        {

            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }


        return pack;

    }

    public static void main(String[] args) {
        try {
            // InventoryManager.createInventoryItemForClient("463", "JC4301", "Johnny Cash Block Cap ","0.00",true, "", "", "DROP:FAX:Zion-Roots" );
            //  InventoryManager.createInventoryItemForClient("463", "BM4050", "Bob Marley Revolutionary Military Cap ","0.00",true, "", "", "DROP:FAX:Zion-Roots" );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //todo
    public static Inventory dbloadByPart(String part, String clientID) throws Exception

    {

        Connection cxn = null;


        try {
            //  log.debug("getting cxn");
            cxn = ConnectionManager.getConnection();
            //   log.debug("loading part");
            return dbloadByPart(cxn, part, clientID);

        } catch (Exception ex)

        {

            throw ex;

        } finally

        {

            try {
                cxn.close();
            } catch (Exception e) {
            }

        }

    }


    public static Inventory dbloadByPart(Connection cxn, String part, String clientID) throws Exception

    {


        PreparedStatement stmt = null;

        Inventory pack = null;


        try

        {

            stmt = cxn.prepareStatement(loadByPartStatement);

            stmt.setString(1, part);

            stmt.setInt(2, new Integer(clientID).intValue());

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();

            if (rs.next()) {

                pack = new Inventory(rs.getString(kdbInventoryPKName),

                        rs.getString(kdbInventoryClientFkey),

                        part,

                        rs.getString(kdbInventoryBarcode),

                        rs.getString(kdbSupplierRef),

                        rs.getString(kdbKeyword),

                        rs.getString(kdbPriceEach),

                        rs.getString(kdbColor),

                        rs.getString(kdbSize),

                        rs.getString(kdbClientKey),

                        rs.getString(kdbNotes),

                        rs.getString(kdbDescription),

                        rs.getString(kdbLongDescription),

                        rs.getString(kdbReorder),

                        rs.getString(kdbIsActive),

                        rs.getString(kdbLocation),

                        rs.getString(kdbAvailable),

                        rs.getString(kdbInventoryCreatedBy),

                        rs.getString(kdbInventoryCreatedOn),

                        rs.getString(kdbInventoryModifiedBy),

                        rs.getString(kdbInventoryModifiedOn),

                        rs.getString(kdbHarmonizedCode),

                        rs.getString(kdbSupplierCost),

                        rs.getString(kdbSupplierFKey),

                        rs.getString(kdbWeight),

                        rs.getString(kdbForceShipment),

                        rs.getString(kdbShipMethodUS),

                        rs.getString(kdbShipMethodPO),

                        rs.getString(kdbShipMethodCAN),

                        rs.getString(kdbShipMethodINT),

                        rs.getString(kdbIsDocuments),

                        rs.getString(kdbCustomsDesc),

                        rs.getString(kdbCustomsValue),

                        rs.getString(kdbIsAutoInventory),

                        rs.getString(kdbIsNoScanRequired),

                        rs.getString(kdbPackingInstructions),
                        rs.getString(kdbUrl),
                        rs.getString(kdbDefaultFacility),
                        rs.getString(kdbIsInsert),
                        rs.getString(kdbIsLotTracked),
                        rs.getString(kdbLength),
                        rs.getString(kdbWidth),
                        rs.getString(kdbHeight),
                        rs.getString(kdbCubicFeet),
                        rs.getString(kdbGroupName),
                        rs.getString(kdbUpcCode),
                        rs.getString(kdbIsbnCode),
                        rs.getString(kdbCaseQty),
                        rs.getString(kdbIsShipSystemWeight),
                        rs.getString(kdbIsBulkyPick),
                        rs.getString(kdbIsBulkyPack),
                        rs.getString(kdbBulkyPickOverride),
                        rs.getString(kdbBulkyPackOverride),
                        rs.getString(kdbBulkyPickFeeOverride),
                        rs.getString(kdbBulkyPackFeeOverride)
                );

            } else {

                throw new Exception("Could not load inventory part number!");

            }


        } catch (Exception ex)

        {

            throw ex;

        } finally

        {

            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }


        return pack;

    }

    public static ArrayList getItemsx(int inventory_id, int client_fkey, ArrayList field_names)
            throws Exception {
        return getItemsx(inventory_id, client_fkey, field_names, false);
    }

    public static ArrayList getItemsx(int inventory_id, int client_fkey, ArrayList field_names, boolean includeInactiveItems)
            throws Exception {
        ArrayList records = new ArrayList();
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String field_name = "";
        Iterator it = field_names.iterator();
        while (it.hasNext()) {
            if ("".equals(field_name))
                field_name = (String) it.next();
            else
                field_name += "," + (String) it.next();
        }
        if ("".equals(field_name)) {
            field_name = "*";
        }
        try {
            cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement("select " + ("qty_on_hand".equals(field_name) ? "ISNULL(case when is_auto_inventory=1 and c.display_kit_quantities = 0 then 99999 else qty_on_hand end,0)" : field_name) + " from owd_inventory o join owd_inventory_oh oh on inventory_id = inventory_fkey join owd_client c on c.client_id = o.client_fkey where " + (includeInactiveItems ? "" : "o.is_active=1 and ") + " (inventory_id =? or client_fkey = ?) order by inventory_num");
            stmt.setInt(1, inventory_id);
            stmt.setInt(2, client_fkey);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Hashtable record = new Hashtable();
                for (int i = 0; i < field_names.size(); i++)
                    record.put((String) field_names.get(i), rs.getString(i + 1));
                records.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }
        return records;
    }

    public static ArrayList getItemsForClientIDx(String client_id, ArrayList field_names) throws Exception {
        int client_fkey = (Integer.valueOf(client_id)).intValue();
        return getItemsx(0, client_fkey, field_names);
    }

    public static ArrayList getItemsForClientIDx(String client_id, ArrayList field_names, boolean includeInactiveItems) throws Exception {
        int client_fkey = (Integer.valueOf(client_id)).intValue();
        return getItemsx(0, client_fkey, field_names, includeInactiveItems);
    }

}

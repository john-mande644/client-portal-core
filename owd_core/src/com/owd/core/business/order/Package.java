package com.owd.core.business.order;


import com.owd.core.OWDUtilities;
import com.owd.core.business.order.beans.lineItemPackageData;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.LotManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class Package {
    private final static Logger log = LogManager.getLogger();

    //IDs are zero if the package is new


    //Attributes

    private static final String kdbPackagePKName = "order_track_id";

    public String order_track_id = "0";


    private static final String kdbPackageOrderFkey = "order_fkey";

    public String order_fkey = "0";


    private static final String kdbPackageCreatedBy = "created_by";

    public String createdBy = "";


    private static final String kdbPackageCreatedOn = "created_date";

    public String createdOn = "";


    private static final String kdbPackageModifiedBy = "modified_by";

    public String modifiedBy = "";


    private static final String kdbPackageModifiedOn = "modified_date";

    public String modifiedOn = "";


    //Properties

    private static final String kdbPackageLineIndex = "line_index";

    public String line_index = "";


    private static final String kdbPackageTrackingNo = "tracking_no";

    public String tracking_no = "";


    private static final String kdbPackageWeightLbs = "weight";

    public float weight = 0;


    private static final String kdbPackageTotalBilled = "total_billed";

    public float total_billed = 0;


    private static final String kdbPackageCostGoods = "cost_of_goods";

    public float cost_of_goods = 0;


    private static final String kdbShipDate = "ship_date";

    public String ship_date = "";


    private static final String kdbMSN = "msn";

    public String msn = "";


    private static final String kdbIsVoid = "is_void";

    public String is_void = "0";


    private static final String kdbReported = "reported";
    public String reported = "0";

    public int bundle_id = 0;
    private static final String kdbBundleID = "bundle_id";

    public String carrier_code = "";
    private static final String kdbCarrierCode = "carrier_code";

    public String service_code = "";
    private static final String kdbServiceCode = "service_code";

    public String label_code = "";
    private static final String kdbLabelCode = "label_code";

    public String asn_reference = "";
    private static final String kdbAsnReference = "asn_reference";

    public String SCAC = "";
    public static final String kdbSCAC = "SCAC";

    public String pallets = "0";
    public static final String kdbPallets = "pallets";

    public static final String kdbIsThirdParty = "is_third_party";
    public String isThirdParty = "0";

    public static final String kdbDimWeight = "dim_weight_lbs";
    public float dimWeight = 0;

    public static final String kdbShipMethodUsed = "ShipMethod";
    public String shipMethod = "";
    public String carrierName = "";



    private static final String kdbPackageTable = "owd_order_track";

    private static final String kDeletePackageStatement = "DELETE FROM " + kdbPackageTable + " where " + kdbPackagePKName + " = ?";


    private static final String kInsertPackageStatement = "insert into " + kdbPackageTable + "("

            + kdbPackageOrderFkey + ","

            + kdbPackageLineIndex + ","

            + kdbPackageTrackingNo + ","

            + kdbPackageWeightLbs + ","

            + kdbPackageTotalBilled + ","

            + kdbPackageCostGoods + ","

            + kdbShipDate + ","

            + kdbMSN + ","

            + kdbIsVoid + ","

            + kdbReported + ","

            + kdbPackageCreatedBy + ","

            + kdbPackageCreatedOn + ","

            + kdbPackageModifiedBy + ","

            + kdbPackageModifiedOn + ", shipper_acct,"
            + kdbBundleID + ","
            + kdbCarrierCode + "," + kdbServiceCode + "," + kdbLabelCode + "," + kdbAsnReference + "," + kdbIsThirdParty
            + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private static final String kUpdatePackageStatement = "update " + kdbPackageTable + " set "

            + kdbPackageOrderFkey + "=?,"

            + kdbPackageLineIndex + "=?,"

            + kdbPackageTrackingNo + "=?,"

            + kdbPackageWeightLbs + "=?,"

            + kdbPackageTotalBilled + "=?,"

            + kdbPackageCostGoods + "=?,"

            + kdbShipDate + "=?,"

            + kdbMSN + "=?,"

            + kdbIsVoid + "=?,"

            + kdbReported + "=?,"

            + kdbPackageModifiedBy + "=?,"

            + kdbPackageModifiedOn + "=?,"

            + kdbPackageCreatedBy + "=?,"
            + kdbBundleID + "=?,"
            + kdbCarrierCode + "=?,"
            + kdbServiceCode + "=?,"
            + kdbLabelCode + "=?,"
            + kdbAsnReference + "=?"

            + " WHERE " + kdbPackagePKName + " = ?";


    private boolean needsUpdate = false;


    public Package()

    {


    }

    public Package(String aid,

                   String aorder_id,

                   String aline_index,

                   String atracking_no,

                   String aweight,

                   String atotal_billed,

                   String acost_of_goods,

                   String aship_date,

                   String amsn,

                   String ais_void,

                   String areported,

                   String acreated_by,

                   String acreated_on,

                   String amodified_by,

                   String amodified_on,
                   String abundleID,
                   String acarrierCode,
                   String aserviceCode,
                   String alabelCode)

    {
        this(aid,

                aorder_id,

                aline_index,

                atracking_no,

                aweight,

                atotal_billed,

                acost_of_goods,

                aship_date,

                amsn,

                ais_void,

                areported,

                acreated_by,

                acreated_on,

                amodified_by,

                amodified_on,
                abundleID,
                acarrierCode,
                aserviceCode,
                alabelCode, "","","");
    }

    public Package(String aid,

                   String aorder_id,

                   String aline_index,

                   String atracking_no,

                   String aweight,

                   String atotal_billed,

                   String acost_of_goods,

                   String aship_date,

                   String amsn,

                   String ais_void,

                   String areported,

                   String acreated_by,

                   String acreated_on,

                   String amodified_by,

                   String amodified_on,
                   String abundleID,
                   String acarrierCode,
                   String aserviceCode,
                   String alabelCode,
                   String aAsnReference,
                   String aSCAC,
                   String aPallets,
                   String aDimWeight,
                   String aShipMethod,
                   String aCarrierName)

    {

        order_track_id = aid;
        asn_reference = aAsnReference;
        if(null != aSCAC){
            SCAC = aSCAC;
        }
        if(null != aPallets){
            pallets = aPallets;
        }

        order_fkey = aorder_id;

        tracking_no = atracking_no;

        ship_date = aship_date;

        msn = amsn;

        reported = areported;

        is_void = ais_void;

        line_index = aline_index;

        try {

            bundle_id = (new Long("0" + abundleID).intValue());

        } catch (NumberFormatException ex) {
            bundle_id = 0;
        }

        carrier_code = acarrierCode;
        service_code = aserviceCode;
        label_code = alabelCode;


        try {

            long tempID = new Long("0" + order_track_id).longValue();

            if (tempID < 0)

                order_track_id = "0";

        } catch (NumberFormatException ex) {
            order_track_id = "0";
        }

        try {

            long tempID = new Long("0" + order_fkey).longValue();

            if (tempID < 0)

                order_fkey = "0";

        } catch (NumberFormatException ex) {
            order_fkey = "0";
        }


        try {

            long tempID = new Long("0" + is_void).longValue();

            if (tempID < 0)

                is_void = "0";

        } catch (NumberFormatException ex) {
            is_void = "0";
        }


        try {

            long tempID = new Long("0" + reported).longValue();

            if (tempID < 0)

                reported = "0";

        } catch (NumberFormatException ex) {
            reported = "0";
        }


        try {

            long tempID = new Long("0" + line_index).longValue();

            if (tempID < 0)

                line_index = "0";

        } catch (NumberFormatException ex) {
            line_index = "0";
        }


        try {

            long tempID = new Long("0" + msn).longValue();

            if (tempID < 0)

                msn = "0";

        } catch (NumberFormatException ex) {
            msn = "0";
        }


        try {

            weight = (new Float("0" + aweight).floatValue());

        } catch (NumberFormatException ex) {
            weight = (float) 0.0;
        }


        try {

            total_billed = (new Float("0" + atotal_billed).floatValue());

        } catch (NumberFormatException ex) {
            total_billed = (float) 0.0;
        }


        try {

            cost_of_goods = (new Float("0" + acost_of_goods).floatValue());

        } catch (NumberFormatException ex) {
            cost_of_goods = (float) 0.0;
        }


        createdBy = acreated_by;

        createdOn = acreated_on;

        modifiedBy = amodified_by;

        modifiedOn = amodified_on;

        try {

            dimWeight = (new Float("0" + aDimWeight).floatValue());

        } catch (NumberFormatException ex) {
            dimWeight = (float) 0.0;
        }
        shipMethod = aShipMethod;
        carrierName = aCarrierName;

    }

    public Package(String aid,

                   String aorder_id,

                   String aline_index,

                   String atracking_no,

                   String aweight,

                   String atotal_billed,

                   String acost_of_goods,

                   String aship_date,

                   String amsn,

                   String ais_void,

                   String areported,

                   String acreated_by,

                   String acreated_on,

                   String amodified_by,

                   String amodified_on,
                   String abundleID,
                   String acarrierCode,
                   String aserviceCode,
                   String alabelCode,
                   String aAsnReference,
                   String aSCAC,
                   String aPallets)

    {

        order_track_id = aid;
        asn_reference = aAsnReference;
        if(null != aSCAC){
            SCAC = aSCAC;
        }
        if(null != aPallets){
            pallets = aPallets;
        }

        order_fkey = aorder_id;

        tracking_no = atracking_no;

        ship_date = aship_date;

        msn = amsn;

        reported = areported;

        is_void = ais_void;

        line_index = aline_index;

        try {

            bundle_id = (new Long("0" + abundleID).intValue());

        } catch (NumberFormatException ex) {
            bundle_id = 0;
        }

        carrier_code = acarrierCode;
        service_code = aserviceCode;
        label_code = alabelCode;


        try {

            long tempID = new Long("0" + order_track_id).longValue();

            if (tempID < 0)

                order_track_id = "0";

        } catch (NumberFormatException ex) {
            order_track_id = "0";
        }

        try {

            long tempID = new Long("0" + order_fkey).longValue();

            if (tempID < 0)

                order_fkey = "0";

        } catch (NumberFormatException ex) {
            order_fkey = "0";
        }


        try {

            long tempID = new Long("0" + is_void).longValue();

            if (tempID < 0)

                is_void = "0";

        } catch (NumberFormatException ex) {
            is_void = "0";
        }


        try {

            long tempID = new Long("0" + reported).longValue();

            if (tempID < 0)

                reported = "0";

        } catch (NumberFormatException ex) {
            reported = "0";
        }


        try {

            long tempID = new Long("0" + line_index).longValue();

            if (tempID < 0)

                line_index = "0";

        } catch (NumberFormatException ex) {
            line_index = "0";
        }


        try {

            long tempID = new Long("0" + msn).longValue();

            if (tempID < 0)

                msn = "0";

        } catch (NumberFormatException ex) {
            msn = "0";
        }


        try {

            weight = (new Float("0" + aweight).floatValue());

        } catch (NumberFormatException ex) {
            weight = (float) 0.0;
        }


        try {

            total_billed = (new Float("0" + atotal_billed).floatValue());

        } catch (NumberFormatException ex) {
            total_billed = (float) 0.0;
        }


        try {

            cost_of_goods = (new Float("0" + acost_of_goods).floatValue());

        } catch (NumberFormatException ex) {
            cost_of_goods = (float) 0.0;
        }


        createdBy = acreated_by;

        createdOn = acreated_on;

        modifiedBy = amodified_by;

        modifiedOn = amodified_on;



    }


    public Package getPackageForID(Connection cxn, String packageID) throws Exception

    {

        //load existing package

        return dbload(cxn, packageID);

    }

    public static Double getInsuranceValueForPackage(int orderTrackID) {
        Double ins = 0.00;
        try {
            ResultSet rs = HibernateSession.getResultSet("select ISNULL(insured_amount,0.00) " +
                    "from package where  " +
                    "order_track_fkey=" + orderTrackID + " ");


            while (rs.next()) {
                ins = rs.getDouble(1);
            }

            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return ins;


    }

    public static List<Package> getPackagesForOrderId(int orderID) {
        List<Package> items = new ArrayList<Package>();
        try {
//            ResultSet rs = HibernateSession.getResultSet("select * from " + kdbPackageTable + " where is_void = 0 and order_fkey = " + orderID);
            ResultSet rs = HibernateSession.getResultSet("select " + kdbPackageTable + ".*, ISNULL(package.dim_weight_lbs,0) as dim_weight_lbs, isnull(list_value,'') as 'ShipMethod' ,(select max(carrier_name) from owd_ship_methods where owd_order_track.carrier_code = carrier_code)as carrier_name\n" + "from " + kdbPackageTable + "\n" +
                    "LEFT JOIN package \n" +
                    "on owd_order_track.order_track_id = package.order_track_fkey \n" +
                    "left join owd_lists\n" +
                    "on td_reference = service_code and is_inactive = 0\n" +
                    " where is_void = 0 and order_fkey = " + orderID);

            while (rs.next()) {
                items.add(new Package(rs.getString(kdbPackagePKName),

                        rs.getString(kdbPackageOrderFkey),

                        rs.getString(kdbPackageLineIndex),

                        rs.getString(kdbPackageTrackingNo),

                        rs.getString(kdbPackageWeightLbs),

                        rs.getString(kdbPackageTotalBilled),

                        rs.getString(kdbPackageCostGoods),

                        rs.getString(kdbShipDate),

                        rs.getString(kdbMSN),

                        rs.getString(kdbIsVoid),

                        rs.getString(kdbReported),

                        rs.getString(kdbPackageCreatedBy),

                        rs.getString(kdbPackageCreatedOn),

                        rs.getString(kdbPackageModifiedBy),

                        rs.getString(kdbPackageModifiedOn),

                        rs.getString(kdbBundleID),

                        rs.getString(kdbCarrierCode),

                        rs.getString(kdbServiceCode),

                        rs.getString(kdbLabelCode),

                        rs.getString(kdbAsnReference),

                        rs.getString(kdbSCAC),

                        rs.getString(kdbPallets),

                        rs.getString(kdbDimWeight),
                        rs.getString(kdbShipMethodUsed),
                        rs.getString("carrier_name")

                ));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return items;


    }
    public static String getSSCCCodeForPackage(String orderTrackId){
        String SSCC = "";
        try{
            String sql = "select SSCC from package where order_track_fkey = :orderTrackId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderTrackId",orderTrackId);
            List l = q.list();
            if(l.size()>0){
                SSCC = l.get(0).toString();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return SSCC;
    }
    public static List<Map> getEDILineItemsForPackage(String packageId){
      return   getEDILineItemsForPackage(packageId,false);
    }
    public static List<Map> getEDILineItemsForPackage(String packageId,boolean errorOnNoTrackItems) {
        List<Map> lines = new ArrayList<Map>();
        try {
            ResultSet rs = HibernateSession.getResultSet("SELECT\n" +
                    "    dbo.owd_line_item.inventory_num,\n" +
                    "    dbo.owd_line_item.cust_refnum,\n" +
                    "    dbo.owd_line_item.description,\n" +
                    "    SUM(dbo.package_line.pack_quantity),\n" +
                    "    convert(varchar,dbo.owd_line_item.long_desc),\n" +
                    "    dbo.owd_inventory.upc_code,isnull(parent_line,'')\n" +
                    "FROM\n" +
                    "    dbo.package\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order_track\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package.order_track_fkey = dbo.owd_order_track.order_track_id)\n" +
                    "INNER JOIN\n" +
                    "    dbo.package_line\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package.id = dbo.package_line.package_fkey)\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_line_item\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_line.owd_line_item_fkey = dbo.owd_line_item.line_item_id)\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_inventory\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\n" +
                    "WHERE\n" +
                    "    dbo.owd_order_track.order_track_id = "+packageId+" and owd_line_item.is_insert = 0\n" +
                    "GROUP BY\n" +
                    "    dbo.owd_line_item.inventory_num,\n" +
                    "    dbo.owd_line_item.cust_refnum,\n" +
                    "    dbo.owd_line_item.description,\n" +
                    "    convert(varchar,dbo.owd_line_item.long_desc),\n" +
                    "    dbo.owd_inventory.upc_code,parent_line ;");

            while (rs.next()) {

                Map amap = new TreeMap();
                amap.put("SKU", rs.getString(1));
                amap.put("REF", rs.getString(2));
                amap.put("DESC", rs.getString(3));
                amap.put("QTY", rs.getInt(4));
                amap.put("BARCODE", rs.getString(5));
                amap.put("UPC",rs.getString(6));
                amap.put("PARENT",rs.getString(7));

                lines.add(amap);

            }

            rs.close();
            if (lines.size() == 0&&!errorOnNoTrackItems) {
                rs = HibernateSession.getResultSet("select inventory_num,cust_refnum,max(description),sum(quantity_actual),max(convert(varchar,long_desc)) " +
                        " from owd_line_item l join owd_order_track t " +
                        " on l.order_fkey=t.order_fkey and t.is_void=0 where order_track_id=" + packageId + " " +
                        " group by inventory_num,cust_refnum,convert(varchar,long_desc)");

                while (rs.next()) {
                    Map amap = new TreeMap();
                    amap.put("SKU", rs.getString(1));
                    amap.put("REF", rs.getString(2));
                    amap.put("DESC", rs.getString(3));
                    amap.put("QTY", rs.getInt(4));
                    amap.put("BARCODE", rs.getString(5));

                    lines.add(amap);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return lines;
    }

    public static List getHwdDimensionsOfPackage(int orderTrackID) {
        List<Integer> dims = new ArrayList<Integer>();
        dims.add(0);
        dims.add(0);
        dims.add(0);
        try {
            ResultSet rs = HibernateSession.getResultSet("select height,width,depth " +
                    "from package where  " +
                    "order_track_fkey=" + orderTrackID + "");

            if (rs.next()) {
                dims.set(0, rs.getInt(3));
                dims.set(1, rs.getInt(2));
                dims.set(2, rs.getInt(1));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return dims;


    }

    public static List<lineItemPackageData> getLineItemDataMapforPackage(int orderTrackID){
      return   getLineItemDataMapforPackage(orderTrackID,false);
    }

    public static List<lineItemPackageData> getLineItemDataMapforPackage(int orderTrackID,boolean includeVirtualItem) {

        List<lineItemPackageData> lines = new ArrayList<lineItemPackageData>();
        try {
            ResultSet rs = HibernateSession.getResultSet("select inventory_num,sum(pack_quantity),package_line.id,is_insert  " +
                    "from owd_line_item l " +
                    "join package_line join package on package.id=package_fkey and  " +
                    "order_track_fkey=" + orderTrackID + " " +
                    "on owd_line_item_fkey=line_item_id " +
                    "group by inventory_num, package_line.id, is_insert");

            while (rs.next()) {
                if(rs.getInt(4)==0) {
                    lineItemPackageData data = new lineItemPackageData();
                    data.setSku(rs.getString(1));
                    data.setPackageQty(rs.getInt(2));
                    data.setLots(LotManager.getShippedLotsFromPackageLineId(rs.getString(3)));
                    lines.add(data);
                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }
        //if include Virtual Items is true, include all is_auto_inventory line items as well. Usually true for first package only
        if(includeVirtualItem){
            try {
                ResultSet rs = HibernateSession.getResultSet("select l.inventory_num,sum(quantity_actual),l.line_item_id " +
                        "from owd_line_item l " +
                        "join owd_order_track t on t.order_fkey=l.order_fkey  " +
                        "and order_track_id=" + orderTrackID + " " +
                        "join owd_inventory i on l.inventory_id = i.inventory_id\n" +
                        "                         where is_auto_inventory = 1" +
                        "group by l.inventory_num, l.line_item_id ");

                while (rs.next()) {
                    lineItemPackageData data = new lineItemPackageData();
                    data.setSku(rs.getString(1));
                    data.setPackageQty(rs.getInt(2));
                    data.setLots(LotManager.getShippedLotsFromLineItemId(rs.getString(3)));
                    lines.add(data);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (lines.size() == 0) {
            try {
                ResultSet rs = HibernateSession.getResultSet("select inventory_num,sum(quantity_actual),l.line_item_id " +
                        "from owd_line_item l " +
                        "join owd_order_track t on t.order_fkey=l.order_fkey  " +
                        "and order_track_id=" + orderTrackID + " " +
                        "group by inventory_num, l.line_item_id");

                while (rs.next()) {
                    lineItemPackageData data = new lineItemPackageData();
                    data.setSku(rs.getString(1));
                    data.setPackageQty(rs.getInt(2));
                    data.setLots(LotManager.getShippedLotsFromLineItemId(rs.getString(3)));
                    lines.add(data);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // HibernateSession.closeSession();
            }
        }

        return lines;


    }

    public static Map<String, Map<OwdLineItem, Integer>> getPackageKeyedOwdLineItemListforOrderStatus(OrderStatus os) {
        Map<String, Map<OwdLineItem, Integer>> keyedMap = new HashMap<String, Map<OwdLineItem, Integer>>();
        for (com.owd.core.business.order.Package p : (Vector<com.owd.core.business.order.Package>) os.packages) {

            Map<OwdLineItem, Integer> items = com.owd.core.business.order.Package.getOwdLineItemListforPackage(p.order_track_id);

            keyedMap.put(p.order_track_id, items);
        }

        return keyedMap;
    }


    public static Map<OwdLineItem, Integer> getOwdLineItemListforPackage(int orderTrackID) {
        return getOwdLineItemListforPackage("" + orderTrackID);
    }

    public static Map<OwdLineItem, Integer> getOwdLineItemListforPackage(String orderTrackID) {
        Map<OwdLineItem, Integer> lines = new HashMap<OwdLineItem, Integer>();
        try {
            ResultSet rs = HibernateSession.getResultSet("select distinct line_item_id, pack_quantity" +
                    " from owd_line_item  " +
                    " join package_line join package on package.id=package_fkey and  " +
                    " order_track_fkey=" + orderTrackID + " " +
                    " on owd_line_item_fkey=line_item_id ");

            while (rs.next()) {
                OwdLineItem oli = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, rs.getInt(1));

                lines.put(oli, rs.getInt(2));
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        if (lines.size() == 0) {
            try {
                ResultSet rs = HibernateSession.getResultSet("select distinct line_item_id " +
                        "from owd_line_item l " +
                        "join owd_order_track t on t.order_fkey=l.order_fkey  " +
                        "and order_track_id=" + orderTrackID + " " +
                        "");

                while (rs.next()) {
                    OwdLineItem oli = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, rs.getInt(1));

                    lines.put(oli, oli.getQuantityActual());
                }
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // HibernateSession.closeSession();
            }
        }

        return lines;


    }


    public static java.util.Vector getPackagesForOrder(Connection cxn, String orderID) throws Exception

    {

        java.util.Vector items = new java.util.Vector();

        Statement stmt = null;

        ResultSet rs = null;

        try

        {

            stmt = cxn.createStatement();

//            String isql = "select * from " + kdbPackageTable + " where is_void = 0 and order_fkey = " + orderID;

            String isql = "select " + kdbPackageTable + ".*, ISNULL(package.dim_weight_lbs,0) as dim_weight_lbs, isnull(list_value,'') as 'ShipMethod' ,(select max(carrier_name) from owd_ship_methods where owd_order_track.carrier_code = carrier_code)as carrier_name\n" + "from " + kdbPackageTable + "\n" +
                    "LEFT JOIN package \n" +
                    "on owd_order_track.order_track_id = package.order_track_fkey \n" +
                    "left join owd_lists\n" +
                    "on td_reference = service_code and is_inactive = 0\n" +
                    " where is_void = 0 and order_fkey = " + orderID;

            log.debug(isql);

            boolean hasResultSet = stmt.execute(isql);


            if (hasResultSet)

            {

                rs = stmt.getResultSet();


                while (rs.next())

                {

                    items.addElement(new Package(rs.getString(kdbPackagePKName),

                            rs.getString(kdbPackageOrderFkey),

                            rs.getString(kdbPackageLineIndex),

                            rs.getString(kdbPackageTrackingNo),

                            rs.getString(kdbPackageWeightLbs),

                            rs.getString(kdbPackageTotalBilled),

                            rs.getString(kdbPackageCostGoods),

                            rs.getString(kdbShipDate),

                            rs.getString(kdbMSN),

                            rs.getString(kdbIsVoid),

                            rs.getString(kdbReported),

                            rs.getString(kdbPackageCreatedBy),

                            rs.getString(kdbPackageCreatedOn),

                            rs.getString(kdbPackageModifiedBy),

                            rs.getString(kdbPackageModifiedOn),

                            rs.getString(kdbBundleID),

                            rs.getString(kdbCarrierCode),

                            rs.getString(kdbServiceCode),

                            rs.getString(kdbLabelCode),

                            rs.getString(kdbAsnReference),

                            rs.getString(kdbSCAC),

                            rs.getString(kdbPallets),

                            rs.getString(kdbDimWeight),
                            rs.getString(kdbShipMethodUsed),
                            rs.getString("carrier_name")

                    ));


                }


                rs.close();


            }


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

            return items;

        }

    }


    public boolean isModified()

    {

        return needsUpdate;

    }


    public boolean isNew()

    {

        if ("0".equals(order_track_id))

            return true;


        return false;

    }





  /*  public ElementNode toXml(XmlDocument doc) throws IOException

    {



        //return Line Item node

        ElementNode root = (ElementNode) doc.createElement(OrderXMLDoc.kPackageTag);



        //attributes

        root.setAttribute(OrderXMLDoc.kPackageIDTag, ""+order_track_id);

        root.setAttribute(OrderXMLDoc.kPackageOrderIDTag, ""+order_fkey);

        root.setAttribute(OrderXMLDoc.kPackageCreatedOnTag,""+createdOn);

        root.setAttribute(OrderXMLDoc.kPackageCreatedByTag,""+createdBy);

        root.setAttribute(OrderXMLDoc.kPackageModifiedByTag,""+modifiedBy);

        root.setAttribute(OrderXMLDoc.kPackageModifiedOnTag,""+modifiedOn);



        //elements

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackageIndexTag,""+line_index));

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackageTrackingNoTag,""+tracking_no));

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackagePoundsWeighTagt,""+weight));

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackageRatedCostTag,""+total_billed));

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackageCostGoodsTag,""+cost_of_goods));

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackageShipDateTag,""+ship_date));

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackageMSNTag,""+msn));

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackageVoidTag,""+is_void));

        root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kPackageReportedTag,""+reported));



        return root;



    }
*/


    public void dbsave(Connection cxn, String creator, String shipper_acct) throws Exception

    {

        createdBy = creator;

        PreparedStatement stmt = null;

        ResultSet rs = null;

        createdOn = OWDUtilities.getSQLDateTimeForToday();

        modifiedOn = createdOn;

        modifiedBy = createdBy;

        ship_date = OWDUtilities.getSQLDateNoTimeForToday();


        try

        {

            stmt = cxn.prepareStatement(kInsertPackageStatement);

            stmt.setInt(1, new Integer(order_fkey).intValue());

            stmt.setInt(2, new Integer(line_index).intValue());

            stmt.setString(3, tracking_no);

            stmt.setFloat(4, weight);

            stmt.setFloat(5, total_billed);

            stmt.setFloat(6, cost_of_goods);

            stmt.setString(7, ship_date);

            stmt.setInt(8, new Integer(msn).intValue());

            stmt.setInt(9, new Integer(is_void).intValue());

            stmt.setInt(10, new Integer(reported).intValue());

            stmt.setString(11, createdBy);

            stmt.setString(12, createdOn);

            stmt.setString(13, modifiedBy);

            stmt.setString(14, modifiedOn);
            stmt.setString(15, shipper_acct);
            stmt.setInt(16, bundle_id);
            stmt.setString(17, carrier_code);
            stmt.setString(18, service_code);
            stmt.setString(19, label_code);
            stmt.setString(20, asn_reference);
            stmt.setString(21,isThirdParty);


            int rowsUpdated = stmt.executeUpdate();


            stmt.close();


            stmt = cxn.prepareStatement("select @@IDENTITY");


            stmt.executeQuery();

            rs = stmt.getResultSet();

            if (rs.next())

            {

                order_track_id = rs.getString(1);

            }

            rs.close();

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


    public void dbsave(Connection cxn) throws Exception

    {


        dbsave(cxn, OWDUtilities.getCurrentUserName(), "OWD");

    }


    public void dbsave(Connection cxn, String shipName) throws Exception

    {


        dbsave(cxn, shipName, "OWD");

    }


    public void dbdelete(Connection cxn) throws Exception

    {


        if (isNew())

        {

            //do nothing

        } else {

            PreparedStatement stmt = null;

            try

            {

                stmt = cxn.prepareStatement(kDeletePackageStatement);

                stmt.setString(1, order_track_id);


                int rowsUpdated = stmt.executeUpdate();


                stmt.close();


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


        }

    }


    public void dbupdate(Connection cxn) throws Exception

    {


        if (isNew())

        {

            dbsave(cxn);

        } else {

            PreparedStatement stmt = null;

            modifiedBy = OWDUtilities.getCurrentUserName();

            modifiedOn = OWDUtilities.getSQLDateTimeForToday();

            try

            {

                stmt = cxn.prepareStatement(kUpdatePackageStatement);

                stmt.setInt(1, new Integer(order_fkey).intValue());

                stmt.setInt(2, new Integer(line_index).intValue());

                stmt.setString(3, tracking_no);

                stmt.setFloat(4, weight);

                stmt.setFloat(5, total_billed);

                stmt.setFloat(6, cost_of_goods);

                stmt.setString(7, ship_date);

                stmt.setInt(8, new Integer(msn).intValue());

                stmt.setInt(9, new Integer(is_void).intValue());

                stmt.setInt(10, new Integer(reported).intValue());

                stmt.setString(11, modifiedBy);

                stmt.setString(12, modifiedOn);

                stmt.setString(13, createdBy);

                stmt.setInt(14, bundle_id);
                stmt.setString(15, carrier_code);
                stmt.setString(16, service_code);
                stmt.setString(17, label_code);

                stmt.setInt(18, new Integer(order_track_id).intValue());
                stmt.setString(19, asn_reference);


                int rowsUpdated = stmt.executeUpdate();


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


        }


    }


    public static void main(String[] args) throws Exception {

        //voidOrderPackage("9864143", "10036485");
    try {
       /* for (Package pack : getPackagesForOrderId(13604723)) {
            System.out.println(pack.SCAC);
        }*/
        Connection cxn = ConnectionManager.getConnection();
       // Package pack = dbload(cxn,"18138549");
       // log.debug(pack.dimWeight);
      List<Package> packages = getPackagesForOrderId(20410024);
        System.out.println(packages.get(0).shipMethod);
        System.out.println(packages.get(0).carrierName);

//        System.out.println(getLineItemDataMapforPackage(15694715).size());
//        System.out.println(getLineItemDataMapforPackage(15694715,true).size());
    }catch (Exception e){
        e.printStackTrace();
    }


    }

    public static void voidOrderPackage(String pid, String oid)

    {


        Package pack = null;

        Connection cxn = null;
        Statement astmt = null;
        PreparedStatement stmt = null;


        log.debug("Voiding order package ID " + pid + " for order id " + oid);

        try {

            cxn = ConnectionManager.getConnection();

            pack = Package.dbload(cxn, pid);

            log.debug("Voiding package - got pack object " + pack);

            if (oid.equals(pack.order_fkey))

            {

                //validated

                stmt = cxn.prepareStatement("update owd_order_track set is_void=1  where order_track_id=?");
                stmt.setInt(1, new Integer(pack.order_track_id));
                // stmt.setString(2, pack.msn);
                stmt.executeUpdate();
                stmt.close();
                cxn.commit();


            }


            stmt = cxn.prepareStatement("update package_order set packs_shipped=(packs_shipped-1) where id in (select distinct po.id from package \n" +
                    "   join package_order po on po.id=package.package_order_fkey  \n" +
                    "   join owd_order_track t on t.order_track_id=order_track_fkey  where owd_order_fkey=? and msn=?)");
            stmt.setInt(1, new Integer(pack.order_fkey).intValue());
            stmt.setString(2, pack.msn);
            stmt.executeUpdate();
            stmt.close();
            stmt = cxn.prepareStatement("update package set ship_time=null,order_track_fkey=null where id in (select distinct package.id from package\n" +
                    "join package_order po on po.id=package.package_order_fkey  \n" +
                    "join owd_order_track t on t.order_track_id=order_track_fkey  where owd_order_fkey=? and msn=?)");
            stmt.setInt(1, new Integer(pack.order_fkey).intValue());
            stmt.setString(2, pack.msn);
            log.debug("pv3");
            stmt.executeUpdate();

            String esql = "exec update_order_shipment_info " + pack.order_fkey;

            astmt = cxn.createStatement();

            astmt.executeUpdate(esql);


            cxn.commit();

        } catch (Exception ex)

        {

            try {
                cxn.rollback();
            } catch (Exception e) {
            }

            ex.printStackTrace();

        } finally {

            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                astmt.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }

        }


    }


    private static Package dbload(Connection cxn, String id) throws Exception

    {


        Statement stmt = null;

        ResultSet rs = null;

        Package pack = null;


        try

        {

            stmt = cxn.createStatement();

            String isql = "select " + kdbPackageTable + ".*, ISNULL(package.dim_weight_lbs,0) as dim_weight_lbs, isnull(list_value,'') as 'ShipMethod' ,(select max(carrier_name) from owd_ship_methods where owd_order_track.carrier_code = carrier_code)as carrier_name \n" + "from " + kdbPackageTable + "\n" +
                "LEFT JOIN package \n" +
                    "on owd_order_track.order_track_id = package.order_track_fkey \n" +
                    "left join owd_lists\n" +
                    "on td_reference = service_code and is_inactive = 0\n" +
                    " where " + kdbPackagePKName + " = " + id;

            log.debug(isql);
            boolean hasResultSet = stmt.execute(isql);


            if (hasResultSet)

            {

                rs = stmt.getResultSet();


                if (rs.next())

                {

                    pack = new Package(rs.getString(kdbPackagePKName),

                            rs.getString(kdbPackageOrderFkey),

                            rs.getString(kdbPackageLineIndex),

                            rs.getString(kdbPackageTrackingNo),

                            rs.getString(kdbPackageWeightLbs),

                            rs.getString(kdbPackageTotalBilled),

                            rs.getString(kdbPackageCostGoods),

                            rs.getString(kdbShipDate),

                            rs.getString(kdbMSN),

                            rs.getString(kdbIsVoid),

                            rs.getString(kdbReported),

                            rs.getString(kdbPackageCreatedBy),

                            rs.getString(kdbPackageCreatedOn),

                            rs.getString(kdbPackageModifiedBy),

                            rs.getString(kdbPackageModifiedOn),

                            rs.getString(kdbBundleID),

                            rs.getString(kdbCarrierCode),

                            rs.getString(kdbServiceCode),

                            rs.getString(kdbLabelCode),

                            rs.getString(kdbAsnReference),

                            rs.getString(kdbSCAC),

                            rs.getString(kdbPallets),

                            rs.getString(kdbDimWeight),
                            rs.getString(kdbShipMethodUsed),
                            rs.getString("carrier_name")
                    );


                } else {

                    throw new Exception("Package id " + id + " not found!");

                }


                rs.close();


            } else {

                throw new Exception("Package id " + id + " not found!");

            }


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


        return pack;

    }

    public static void removeLotDataForOrder(int orderId) throws Exception {
        PreparedStatement ps = HibernateSession.getPreparedStatement("delete lot from owd_lot_packageline lot join package_line pl join package p join package_order po on po.id=p.package_order_fkey on pl.package_fkey=p.id on package_line_fkey=pl.id \n" +
                " where owd_order_fkey=?")  ;
        ps.setInt(1,orderId);
        ps.executeUpdate();
        ps.close();
    }

    public static int getOrderTrackIdFromMsnAndOrderId(String msn, int orderId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = HibernateSession.getPreparedStatement("select top 1 order_track_id from owd_order_track where order_fkey=?, msn=? and is_void=0");
            ps.setInt(1, orderId);
            ps.setString(2, msn);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new Exception("Unable to locate unvoided package record for order id " + orderId + " and msn " + msn);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception exx) {
            }
        }
    }

    public static int getOrderTrackIdFromMsnAndOrderNum(String msn, String orderNum) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = HibernateSession.getPreparedStatement("select top 1 order_track_id from owd_order_track t join owd_order o on order_id=order_fkey and order_num=? where msn=? and t.is_void=0");
            ps.setString(1, orderNum);
            ps.setString(2, msn);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new Exception("Unable to locate unvoided package record for MSN " + msn + " and OWD order ref " + orderNum);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception exx) {
            }
        }
    }

    public static int getOrderTrackIdFromMsnAndPackBarcode(String msn, String packBarcode) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = HibernateSession.getPreparedStatement("select top 1 order_track_id from owd_order_track t join owd_order o join package_order po  join package p on p.package_order_fkey=po.id and pack_barcode=? and po.is_void=0 on po.owd_order_fkey=order_id on order_id=order_fkey where msn=? and t.is_void=0");
            ps.setString(1, packBarcode);
            ps.setString(2, msn);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new Exception("Unable to locate unvoided package record for msn " + msn + " and package barcode " + packBarcode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception exx) {
            }
        }
    }
    public static int getOrderIdFromTrackingNumber(String trackingNo) throws Exception{
        int orderId =0;
        String sql = "select order_fkey from owd_order_track where tracking_no = :trackId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("trackId",trackingNo);
        List results = q.list();
        if(results.size()>0){
            if(results.size()>1){
                throw new Exception("To Many orders for tracking number");
            }
            orderId = Integer.parseInt(results.get(0).toString());
        } else{
            throw new Exception("Unable to lookup orderId from tracking ID");
        }
        return orderId;


    }
    public static int getOrderIdFromOrderTrack(int owdOrderTrackId) throws Exception{
        int orderId =0;
        String sql = "select order_fkey from owd_order_track where order_track_id = :trackId";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("trackId",owdOrderTrackId);
        List results = q.list();
        if(results.size()>0){
            orderId = Integer.parseInt(results.get(0).toString());
        } else{
            throw new Exception("Unable to lookup orderId from tracking ID");
        }
        return orderId;
    }
    public static int voidPackageShipment(int owdOrderTrackId, String voidSourceName) throws Exception {


        PreparedStatement stmt = null;
        ResultSet rs = null;
         int orderId = 0;

        log.debug("voiding owd_order_track id " + owdOrderTrackId);
        stmt = HibernateSession.getPreparedStatement("UPDATE owd_order_track SET is_void = 1,modified_by = ?,modified_date = getdate() " +
                "WHERE order_track_id = ?");
        stmt.setString(1, voidSourceName);
        stmt.setInt(2, owdOrderTrackId);
        int rowsUpdated = stmt.executeUpdate();

        stmt.close();
        if (rowsUpdated == 1) {

            log.debug("pv4");
            stmt = HibernateSession.getPreparedStatement("UPDATE package_order SET packs_shipped=(packs_shipped-1) FROM package_order po JOIN package p " +
                    "     JOIN owd_order_track t ON t.order_track_id=order_track_fkey  " +
                    " ON po.id=p.package_order_fkey   WHERE t.order_track_id = ? ");
            stmt.setInt(1, owdOrderTrackId);
            stmt.executeUpdate();
            stmt.close();


            stmt = HibernateSession.getPreparedStatement("UPDATE package SET ship_time=NULL,order_track_fkey=NULL WHERE order_track_fkey=?");
            stmt.setInt(1, owdOrderTrackId);
            log.debug("pv3");
            stmt.executeUpdate();
            stmt.close();


            HibUtils.commit(HibernateSession.currentSession());
            stmt = HibernateSession.getPreparedStatement("exec update_order_shipment_info_ordertrackid ?");
            stmt.setInt(1, owdOrderTrackId);
            log.debug("pv2");
            stmt.executeUpdate();
            stmt.close();

            HibUtils.commit(HibernateSession.currentSession());

        } else {
            throw new Exception("Unable to locate unvoided package for package ID " + owdOrderTrackId + " - " + rowsUpdated + " matching records found");
        }

        return orderId;
    }


}

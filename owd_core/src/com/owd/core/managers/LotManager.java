package com.owd.core.managers;

import com.owd.core.business.order.beans.lotData;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdLotValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stewartbuskirk1 on 3/29/16.
 */
public class LotManager {
    private final static Logger log =  LogManager.getLogger();

    public static Map<String,List<String>> getLotValueMapForInventoryIds(List<Integer> inventoryIds) throws Exception {

        Map<String,List<String>> valueMap = new HashMap<String,List<String>>();

        StringBuilder idList = new StringBuilder();
        for (int id : inventoryIds) {
            if (idList.length() > 0) {
                idList.append(",");
            }
            idList.append("?");
        }

        try {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select inventory_num,lot_value from owd_lot_inventory lot join owd_inventory i on inventory_id=inventory_fkey where inventory_id in ("+idList+") order by inventory_num,lot_value;");
            for (int i = 0; i < inventoryIds.size(); i++) {
                ps.setInt(i+1,inventoryIds.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(!(valueMap.containsKey(rs.getString(1)))) {
                   valueMap.put(rs.getString(1), new ArrayList<String>());
                }
                valueMap.get(rs.getString(1)).add(rs.getString(2));
            }

        }finally {
            HibernateSession.closePreparedStatement();
        }
        log.debug(valueMap);
        return valueMap;
    }
    public static List<String> getLotValuesWithInventoryForInventoryIdSorted(int inventoryId, String facilityCode) throws Exception{
        List<String> values = new ArrayList<String>();
        String sql = "SELECT\n" +
                "    dbo.owd_lot_inventory.lot_value\n" +
                "FROM\n" +
                "    dbo.owd_inventory_facility_lot\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_lot_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory_facility_lot.lot_fkey = dbo.owd_lot_inventory.id)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_facilities\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory_facility_lot.facility_fkey = dbo.owd_facilities.id)\n" +
                "WHERE\n" +
                "    dbo.owd_facilities.loc_code = :facilityCode\n" +
                "AND dbo.owd_lot_inventory.inventory_fkey = :inventoryId\n" +
                "AND dbo.owd_inventory_facility_lot.qty <> 0\n" +
                "ORDER BY\n" +
                "    dbo.owd_lot_inventory.date_created ASC ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("inventoryId",inventoryId);
        q.setParameter("facilityCode",facilityCode);
        List results = q.list();

        if(results.size()>0){
            for(Object data:results){
                values.add(data.toString());

            }
        }


        return values;
    }
    public static SortedMap<String,Integer> getLotValuesWithInventoryForInventoryIdSortedWithQty(int inventoryId, String facilityCode) throws Exception{
        SortedMap<String,Integer> values = new TreeMap<String,Integer>();
        String sql = "SELECT\n" +
                "    dbo.owd_lot_inventory.lot_value, dbo.owd_inventory_facility_lot.qty\n" +
                "FROM\n" +
                "    dbo.owd_inventory_facility_lot\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_lot_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory_facility_lot.lot_fkey = dbo.owd_lot_inventory.id)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_facilities\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory_facility_lot.facility_fkey = dbo.owd_facilities.id)\n" +
                "WHERE\n" +
                "    dbo.owd_facilities.loc_code = :facilityCode\n" +
                "AND dbo.owd_lot_inventory.inventory_fkey = :inventoryId\n" +
                "AND dbo.owd_inventory_facility_lot.qty <> 0\n" +
                "ORDER BY\n" +
                "    dbo.owd_lot_inventory.date_created ASC ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("inventoryId",inventoryId);
        q.setParameter("facilityCode",facilityCode);
        List results = q.list();

        if(results.size()>0){
            for(Object data:results){
                Object[] row= (Object[]) data;
                values.put(row[0].toString(), Integer.parseInt(row[1].toString()));

            }
        }


        return values;
    }

    public static Map<String, Integer> getLotValuesAndQuantitiesForInventoryId(int inventoryId, int facilityId) throws Exception{
        return getLotValuesAndQuantitiesForInventoryId(inventoryId,facilityId,false);

    }
    public static Map<String,Integer> getLotValuesAndQuantitiesForInventoryId(int inventoryId, int facilityId, boolean hasQtyOnly) throws Exception {
        Map<String,Integer> valueMap = new TreeMap<String,Integer>();

        log.debug("getting values for facility id "+facilityId+" and inventory id "+inventoryId);
        try {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select lot_value,isnull(qty,0) as qty from owd_lot_inventory lot left outer join owd_inventory_facility_lot fl on lot.id=lot_fkey and " +
                    "facility_fkey=? where lot.inventory_fkey=? order by lot_value;");

                ps.setInt(1,facilityId);
            ps.setInt(2,inventoryId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                if(hasQtyOnly){
                    if(rs.getInt(2)>0){
                        valueMap.put(rs.getString(1),rs.getInt(2));
                    }

                }else{
                    valueMap.put(rs.getString(1),rs.getInt(2));
                }

            }

        }finally {
            HibernateSession.closePreparedStatement();
        }
        log.debug(valueMap);
        return valueMap;
    }

    public static List<String> getLotValuesForInventoryId(int inventoryId) throws Exception {

        List<String> values = new ArrayList<String>();

        try {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select lot_value from owd_lot_inventory where inventory_fkey=? order by lot_value;");
            ps.setInt(1, inventoryId);



            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                values.add(rs.getString(1));
            }

        }finally {
            HibernateSession.closePreparedStatement();
        }

        return values;
    }
    /*
    Returns OwdLotValue if it exists. If nothing is found throws exception
     */
    public static OwdLotValue getExistingOwdLotValueForString(String lotValue, int inventoryId) throws Exception{

             String sql = "select id from owd_lot_inventory where inventory_fkey = :inventoryFkey and lot_value = :lotValue";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("lotValue",lotValue);
        q.setParameter("inventoryFkey",inventoryId);
        List lotsId = q.list();

             if(lotsId.size()==1){

                 return (OwdLotValue) HibernateSession.currentSession().load(OwdLotValue.class,Integer.parseInt(lotsId.get(0).toString()));
             }
             if(lotsId.size()>1){
                 throw new Exception("Found multiple results for lot: "+lotValue+" and inventoryId: "+inventoryId);

             }
            throw new Exception("No lot found for: "+lotValue+" and inventoryId: "+inventoryId);
    }
    public static OwdLotValue getOwdLotValueForString(String lotValue, int inventoryId) throws Exception {
        return (OwdLotValue) HibernateSession.currentSession().load(OwdLotValue.class,getLotIdForValue(lotValue,inventoryId));

    }
    /*
    Returns database key for given lot value/item pair. Creates new record if none exists.
    Case-insensitive, case-preserving lot value comparison and storage!!!
     */
    public static int getLotIdForValue(String value, int inventoryId) throws Exception {

            try {
                if (value == null) {
                    throw new Exception("Cannot accept null lot value");
                }
                value = value.trim();
                if (inventoryId < 1) {
                    throw new Exception("Invalid inventory ID:" + inventoryId);
                }

                PreparedStatement ps = HibernateSession.getPreparedStatement("if not exists (select 1 from owd_lot_inventory where lot_value=? and inventory_fkey=?)\n" +
                        "insert into owd_lot_inventory (lot_value,inventory_fkey,date_created) values (?,?,getdate() );\n" +
                        "select id from owd_lot_inventory where lot_value=? and inventory_fkey=?;");
                ps.setString(1, value);
                ps.setInt(2, inventoryId);
                ps.setString(3, value);
                ps.setInt(4, inventoryId);
                ps.setString(5, value);
                ps.setInt(6, inventoryId);


                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }

            }finally {
                HibernateSession.closePreparedStatement();
            }
        throw new Exception("Unable to find or create lot id for values :"+value+":"+inventoryId);
    }
    /*
      Returns true if value matches the Inventory Id lot_pattern
     */
    public  static boolean isLotValueValidForInventoryId(String lotValue, int inventoryId) throws Exception{
        boolean valid = false;
        try {

            OwdInventory inventory = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, inventoryId);
            HibernateSession.currentSession().refresh(inventory);
            log.debug(inventoryId);
            log.debug(inventory.getLotPattern());

            if(null == inventory.getLotPattern()|| inventory.getLotPattern().length()<1){
                return true;   //default is to pass, only do QA if pattern is present because some may not have consistent patterns
            }


            valid = patternMatch(lotValue,inventory.getLotPattern());
        }catch(Exception e){
            e.printStackTrace();
        }
       return valid;
    }

    public static boolean patternMatch(String value, String pattern){
        boolean match = false;

        Pattern pat = Pattern.compile(pattern);
        log.debug(pattern);
        Matcher matcher = pat.matcher(value);
        if(matcher.find()){
           match = true;

        }

        return match;
    }

    public static boolean isInventoryIdLotControlled(int inventoryId) throws Exception{
        boolean controlled = false;
             OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, inventoryId);
       log.debug("Is Lot Controlled for: "+inventoryId);
        if(null == inv.getLotTrackingRequired()){
            return controlled;
        }
        if(inv.getLotTrackingRequired().equals(1)){
            controlled = true;
        }


        return controlled;
    }

    public static List<lotData> getShippedLotsFromPackageLineId(String packageLineId){
       // System.out.println("Getting lots for packageLineID "+ packageLineId);
        List<lotData> lots = new ArrayList<lotData>();
        String sql = "SELECT\n" +
                "    dbo.owd_lot_inventory.lot_value,\n" +
                "    dbo.owd_lot_packageline.qty\n" +
                "FROM\n" +
                "    dbo.owd_lot_packageline\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_lot_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_lot_packageline.lot_fkey = dbo.owd_lot_inventory.id)\n" +
                "WHERE\n" +
                "    dbo.owd_lot_packageline.package_line_fkey = :packageLineId ;";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("packageLineId",packageLineId);
            List l = q.list();
            if(l.size()>0){
                for(Object row: l){
                    Object[] data = (Object[]) row;
                    lotData ld = new lotData();
                    ld.setLotQty(Integer.parseInt(data[1].toString()));
                    ld.setLotValue(data[0].toString());
                    lots.add(ld);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return lots;

    }
    public static List<lotData> getShippedLotsFromOrderIdGrouped(String orderId){
        List<lotData> lots = new ArrayList<lotData>();
        String sql = "SELECT\n" +
                "    dbo.owd_lot_inventory.lot_value,\n" +
                "    SUM(dbo.owd_lot_packageline.qty)\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.package_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.package_order.owd_order_fkey)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.package\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.package_line\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package.id = dbo.package_line.package_fkey)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_lot_packageline\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_line.id = dbo.owd_lot_packageline.package_line_fkey)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_lot_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_lot_packageline.lot_fkey = dbo.owd_lot_inventory.id)\n" +
                "WHERE\n" +
                "    dbo.package_order.is_void = 0\n" +
                "AND dbo.owd_order.order_id = :orderId\n" +
                "GROUP BY\n" +
                "    dbo.owd_lot_inventory.lot_value ;";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
            List l = q.list();
            if(l.size()>0){
                for(Object row: l){
                    Object[] data = (Object[]) row;
                    if(null!=data[0]){
                        lotData ld = new lotData();
                        ld.setLotQty(Integer.parseInt(data[1].toString()));
                        ld.setLotValue(data[0].toString());
                        lots.add(ld);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lots;
    }
    public static List<lotData> getShippedLotsFromLineItemId(String lineItemId){
        //System.out.println("Getting lots for lineItemId "+ lineItemId);
        List<lotData> lots = new ArrayList<lotData>();
        String sql = "SELECT\n" +
                "    dbo.owd_lot_inventory.lot_value,\n" +
                "    dbo.owd_lot_packageline.qty\n" +
                "FROM\n" +
                "    dbo.package_line\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_lot_packageline\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_line.id = dbo.owd_lot_packageline.package_line_fkey)\n" +
                "LEFT OUTER JOIN\n" +
                "    dbo.owd_lot_inventory\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_lot_packageline.lot_fkey = dbo.owd_lot_inventory.id)\n" +
                "RIGHT OUTER JOIN\n" +
                "    dbo.package\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_line.package_fkey = dbo.package.id)\n" +
                "RIGHT OUTER JOIN\n" +
                "    dbo.package_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package.package_order_fkey = dbo.package_order.id)\n" +
                "WHERE\n" +
                "    dbo.package_order.is_void = 0\n" +
                "AND dbo.package_line.owd_line_item_fkey = :lineItemId ;";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("lineItemId",lineItemId);
            List l = q.list();
            if(l.size()>0){
                for(Object row: l){
                    Object[] data = (Object[]) row;
                    lotData ld = new lotData();
                    ld.setLotQty(Integer.parseInt(data[1].toString()));
                    ld.setLotValue(data[0].toString());
                    lots.add(ld);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return lots;

    }
    public static void resetLotQuantity(String username, int inventoryId,int facilityId,int lotId,int oldQty, int newQty) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            log.debug("resetLotQuantity:" + inventoryId + ":" + facilityId + ":" + lotId + ":" + newQty);
            ps = HibernateSession.getPreparedStatement("\n" +
                    "  MERGE [owd_inventory_facility_lot] AS [Target]\n" +
                    "  USING (SELECT\n" +
                    "           ? as inventory_fkey,\n" +
                    "           ? as facility_fkey,\n" +
                    "           ? as lot_fkey\n" +
                    "        ) as src\n" +
                    "  ON (Target.[inventory_fkey] = [src].[inventory_fkey] AND [Target].[facility_fkey] = [src].[facility_fkey] AND [Target].[lot_fkey] = [src].[lot_fkey]  )\n" +
                    "  WHEN NOT MATCHED BY TARGET and src.facility_fkey is not null and src.lot_fkey is not null  THEN\n" +
                    "  INSERT ([inventory_fkey], [facility_fkey], [lot_fkey],[qty])\n" +
                    "    VALUES ([src].[inventory_fkey],[src].[facility_fkey],[lot_fkey],0);");

            ps.setInt(1, inventoryId);
            ps.setInt(2, facilityId);
            ps.setInt(3, lotId);
            ps.executeUpdate();
            ps = HibernateSession.getPreparedStatement("  update owd_inventory_facility_lot with (UPDLOCK,ROWLOCK) set qty=?\n" +
                    "  from owd_inventory_facility_lot o where o.inventory_fkey=? and o.facility_fkey=? and o.lot_fkey=? \n");
            ps.setInt(1, newQty);
            ps.setInt(2, inventoryId);
            ps.setInt(3, facilityId);
            ps.setInt(4, lotId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated != 1) {
                throw new Exception("Internal error saving lot quantity adjustment");
            }
            ps = HibernateSession.getPreparedStatement("  insert into owd_lot_manual_resets (inventory_fkey,facility_fkey,lot_fkey,oldQty,newQty,username) values (?,?,?,?,?,?);\n\n");
            ps.setInt(1, inventoryId);
            ps.setInt(2, facilityId);
            ps.setInt(3, lotId);
            ps.setInt(4, oldQty);
            ps.setInt(5, newQty);
            ps.setString(6, username);
             rowsUpdated = ps.executeUpdate();
            if (rowsUpdated != 1) {
                throw new Exception("Internal error saving lot quantity adjustment log");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                ps.close();
            } catch (Exception ex) {
            }
        }
    }
}

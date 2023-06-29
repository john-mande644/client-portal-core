package com.owd.core.managers;


import com.owd.core.OWDUtilities;
import com.owd.core.business.OwdApiClient;
import com.owd.core.business.order.Inventory;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 4, 2006
 * Time: 10:56:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryManager {
private final static Logger log =  LogManager.getLogger();
    
    private final static float bulkyCubicDefault = .0833f;
    private final static float bulkyWeightDefault = 15.0f;


    public static OwdInventory getOwdInventoryForClientAndSku(String clientId, String sku) throws Exception {

        Session sess = HibernateSession.currentSession();

        Criteria crit = sess.createCriteria(OwdInventory.class);

        crit.setMaxResults(1000);
        crit.add(Restrictions.eq("owdClient.clientId", new Integer(clientId)))
                .add(Restrictions.eq("inventoryNum", sku));

        List list = crit.list();
        if (list.size() == 0)
            throw new Exception("SKU " + sku + " not found. SKU values are case-sensitive. Please check the SKU and try again.");

        OwdInventory inventoryItem = ((OwdInventory) list.get(0));

        return inventoryItem;
    }

    public static void deleteOwdInventory(OwdInventory item) throws Exception {

        HibernateSession.currentSession().delete(item.getOwdInventoryOh());
        if (item.getPackingInstructions() != null) {
            HibernateSession.currentSession().delete(item.getPackingInstructions());
        }
        if (item.getAdditionalInfo() != null) {
            HibernateSession.currentSession().delete(item.getAdditionalInfo());
        }
        for (OwdInventoryRequiredSku rsku : item.getRequiredSkus()) {
            HibernateSession.currentSession().delete(rsku);
        }
        HibernateSession.currentSession().delete(item);

        HibernateSession.currentSession().flush();

    }

    public static OwdInventory getInitializedOwdInventory(int clientID) throws Exception {
        OwdInventory item = new OwdInventory();
        item.setBackLocation("");
        item.setBarcodeNo("");
        item.setCatalogUrl("");
        item.setClientItemKey(0);

        item.setCustomsDesc("");
        item.setCustomsValue(0.00f);
        item.setDescription("");
        item.setDocumentsOnly(false);
        item.setFrontLocation("");
        item.setHarmCode("");
        item.setImageThumbUrl("");
        item.setImageUrl("");
        item.setInventoryNum("");
        item.setIsActive(true);
        item.setIsAutoInventory(0);
        item.setIsbnCode("");
        item.setIsInsurable(true);
        item.setItemColor("");
        item.setItemCost(new BigDecimal(0.00));
        item.setItemShipCost(new BigDecimal(0.00));
        item.setItemSize("");
        item.setKeyword("");
        item.setLongDesc("");
        item.setMfrPartNum("");
        item.setModifiedBy("");
        item.setNotes("");
        item.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, clientID));
        OwdInventoryOh oh = new OwdInventoryOh();
        oh.setOwdInventory(item);
        oh.setQtyCommitted(0);
        oh.setQtyOnHand(0);
        item.setOwdInventoryOh(oh);
        ItemPackingInstruction packer = new ItemPackingInstruction();
        packer.setItem(item);
        packer.setInstructions("");
        item.setPackingInstructions(packer);
        item.setPrice(new BigDecimal(0.00));
        item.setQtyHighAlert(0);
        item.setQtyReorder(0);
        item.setQtyUnusable(0);
        item.setRequireSerialNumbers(0);
        item.setSuppCost(new BigDecimal(0.00));
        item.setSuppFkey(null);
        item.setUnusableLocation("");
        item.setUpcCode("");
        item.setWeightLbs(0.00f);
        item.setCreatedBy("");
        item.setCreatedDate(Calendar.getInstance().getTime());


        return item;


    }

    public static Inventory createInventoryItemForClient(String clientID, String sku, String title, String unitPrice, boolean isVirtual, String color, String size, String notes) throws Exception {
        log.debug("creating SKU " + sku);
        Inventory item = new Inventory("0",
                clientID,
                sku,
                "",
                "",
                "",
                unitPrice,
                color,
                size,
                "0",
                notes,
                title,
                "",
                "0",
                "1", //is_active
                "UNKNOWN",
                "0",
                "",
                "",
                "",
                "",
                "",
                "0.0",
                "0",
                "0.00",
                "0",
                "",
                "",
                "",
                "",
                "0",
                "",
                "0.00",
                isVirtual ? "1" : "0",
                "0",
                "", "",
                "","0","0","0","0","0","0","","", "", "","0","0","0","0","0","0.0","0.0");

        item.dbsave( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
        HibUtils.commit(HibernateSession.currentSession());


        return item;

    }

    public static List<String> getSkusForClient(int clientId) throws Exception {
        List<String> itemList = new ArrayList<String>();
      //  log.debug("client iD "+clientId);
        ResultSet rs = HibernateSession.getResultSet("select inventory_num from owd_inventory where client_fkey=" + clientId);
        while (rs.next()) {
          //  log.debug("adding "+rs.getString(1));
itemList.add(rs.getString(1));
        }
        rs.close();
        return itemList;

    }
    public static int getSKUSalesForLastNDays(OwdInventory item, int days) throws Exception {
        ResultSet rs = HibernateSession.getResultSet("select sum(quantity_actual) from owd_line_item (NOLOCK) join owd_order (NOLOCK) on order_id=order_fkey" +
                " where quantity_actual>0 and dateadd(day," + (-1 * days) + ",getdate())<=shipped_on and inventory_id=" + item.getInventoryId());
        if (rs.next()) {
            log.debug("got last " + days + " days sales for " + item.getInventoryNum() + " : " + rs.getInt(1));
            return rs.getInt(1);
        } else {
            return 0;
        }
    }

    public static String getHTMLPictureLink(int inventoryID) {
        String link = "";
        try {

            Session session = HibernateSession.currentSession();
            OwdInventory item = (OwdInventory) session.load(OwdInventory.class, new Integer(inventoryID));
            String thumb = item.getImageThumbUrl();
            if (thumb == null) return "";
            if (thumb.indexOf("http") != 0) return "";
            String image = item.getImageUrl();
            if (image != null) {
                if (image.indexOf("http") != 0) image = null;
            }

            link = "<A target=_newOWDItemImageWin HREF=\"" + image == null ? "" : image + "\">" + thumb + "</A>";
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return link;
    }

    private static void updateBarcodesForInventoryID(int inventoryID, int countID, Session session) throws Exception {

        String updateUPCSql = "update owd_inventory set upc_code=code " +
                "from owd_inventory i join " +
                "(select cc.inventory_id,max(UPC) as 'code' from w_inv_counts cc " +
                "join w_inv_locations l on inv_loc_fkey=l.id and inv_request_fkey=? group by cc.inventory_id) as c \n" +
                "on i.inventory_id=c.inventory_id\n" +
                "and len(code)>1 and i.inventory_id=?";

        String updateISBNSql = "update owd_inventory set isbn_code=code " +
                "from owd_inventory i join " +
                "(select cc.inventory_id,max(ISBN) as 'code' from w_inv_counts cc " +
                "join w_inv_locations l on inv_loc_fkey=l.id and inv_request_fkey=? group by cc.inventory_id) as c \n" +
                "on i.inventory_id=c.inventory_id\n" +
                "and len(code)>1 and i.inventory_id=?";

        if (session == null) throw new Exception("method requires active hibernate session");

        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement(updateUPCSql);
            stmt.setInt(1, countID);
            stmt.setInt(2, inventoryID);

            stmt.executeUpdate();
            stmt.close();

            stmt = HibernateSession.getPreparedStatement(updateISBNSql);
            stmt.setInt(1, countID);
            stmt.setInt(2, inventoryID);

            stmt.executeUpdate();
            stmt.close();


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    private static void clearAllUnusedLocationsForInventoryCount(WInvRequest countRequest, Session session) throws Exception {

        String clearUnusedSQL = "delete from owd_inventory_locations where \n" +
                "location not in (select distinct location from w_inv_locations where inventory_num is not null and inv_request_fkey=?)\n" +
                "  and inventory_fkey  in (select inventory_id from owd_inventory where client_fkey=?)\n" +
                "and location <> 'UNKNOWN'";

        if (session == null) throw new Exception("method requires active hibernate session");

        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement(clearUnusedSQL);
            stmt.setInt(1, countRequest.getId().intValue());
            stmt.setInt(2, countRequest.getClientFkey());

            stmt.executeUpdate();
            stmt.close();


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    public static long getPendingLineItemCountForInventoryId(int iid) throws Exception {
        String sql = "select count(*) from owd_inventory i (NOLOCK)\n" +
                "join owd_inventory_required_skus  (NOLOCK) on inventory_fkey=i.inventory_id\n" +
                "join owd_line_item l  (NOLOCK)\n" +
                "        join owd_order  (NOLOCK) on l.order_fkey=order_id and order_status not in ('SHIPPED','VOID') and is_void=0\n" +
                "on i.inventory_id=l.inventory_id \n" +
                "where is_auto_inventory=1 and i.inventory_id=?";
        long count = 0L;

        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement(sql);
            stmt.setInt(1, iid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getLong(1);
            }
            rs.close();
            stmt.close();
            return count;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {

        }
    }

    private static void clearAllLocationsForInventoryID(int inventoryID, Session session) throws Exception {
        //clear inventory front_location and history for this item
        String clearUnusedSQL = "delete from owd_inventory_locations where \n" +
                "  inventory_fkey=?";

        if (session == null) throw new Exception("method requires active hibernate session");

        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement(clearUnusedSQL);
            stmt.setInt(1, inventoryID);

            stmt.executeUpdate();
            stmt.close();


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }


    }

    private static void setLocationsForInventoryID(int inventoryID, int inventoryCountRequestID, Session session) throws Exception {

        clearAllLocationsForInventoryID(inventoryID, session);

        String sql = "insert into owd_inventory_locations (location, inventory_fkey,assign_date,assigned_by) " +
                " select location,inventory_id,getdate(),'Inventory Count " + inventoryCountRequestID + "' from " +
                " w_inv_locations\n" +
                "where  remove=0 and inv_request_fkey=? and inventory_id=?\n" +
                "group by location, inventory_id";

        if (session == null) throw new Exception("method requires active hibernate session");

        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement(sql);
            stmt.setInt(1, inventoryCountRequestID);
            stmt.setInt(2, inventoryID);

            stmt.executeUpdate();
            stmt.close();


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }


    }

    public static void addToKitBySKUs(int clientID, String kitSKU, String componentSKU, int quantity) throws Exception {
        addToKitBySKUs(clientID, kitSKU, componentSKU, quantity, true);
    }

    public static void addToKitBySKUs(int clientID, String kitSKU, String componentSKU, int quantity, boolean commmitChanges) throws Exception {
        int kitID = 0;
        try {
            log.debug("KIT:" + kitSKU);
            ResultSet rs = HibernateSession.getResultSet("select inventory_id,is_auto_inventory from owd_inventory where client_fkey=" + clientID + " and inventory_num='" + kitSKU + "'");
            if (rs.next()) {
                kitID = rs.getInt(1);
                if (rs.getInt(2) != 1) {
                    throw new Exception("Kit SKU " + kitSKU + " exists but is not virtual - mark SKU virtual before creating kit. " + rs.getInt(3) + " units in stock.");
                }
            } else {
                throw new Exception("Kit SKU " + componentSKU + " does not exist");

            }
            //HibernateSession.closeSession();
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
        try {
            log.debug("ITEM:" + componentSKU);
            log.debug("select inventory_id,is_auto_inventory from owd_inventory where client_fkey=" + clientID + " and inventory_num='" + componentSKU + "'");
            ResultSet rs = HibernateSession.getResultSet("select inventory_id,is_auto_inventory from owd_inventory where client_fkey=" + clientID + " and inventory_num='" + componentSKU + "'");
            int itemID = 0;
            if (rs.next()) {
                itemID = rs.getInt(1);
            } else {
                throw new Exception("Component SKU " + componentSKU + " does not exist");

            }

            if (rs.getInt(2) == 1) {
                throw new Exception("Component SKU " + componentSKU + " exists but is virtual - mark SKU as non-virtual before creating kit.");
            }
            //HibernateSession.closeSession();

            if (kitID == 0) {
                throw new Exception("Kit ID not found");
            }
            if (itemID == 0) {
                throw new Exception("Item ID not found");
            }
            if (quantity < 1) {
                throw new Exception("Quantity must be >= 1");
            }

            PreparedStatement ps = HibernateSession.getPreparedStatement("exec sp_addtokit ?,?,?");
            ps.setInt(1, kitID);
            ps.setInt(2, itemID);
            ps.setInt(3, quantity);
            ps.executeUpdate();
            if (commmitChanges) {
                 ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().commit();
                HibernateSession.closeSession();
            }


        } catch (Exception ex) {
            log.debug(ex.getMessage());
            throw ex;
        }

    }
    // updateSymValues("P69420",0.462,"6206.40.0000 t-shirt","SpiritHoods LLC",20,"United States");

    public static void updateSymValues(String sku, double weight, String customsdesc, String supplier, float custValue, String country) throws Exception {
        OwdInventory item = null;
        try {
            item = (OwdInventory) HibernateSession.currentSession().createQuery("from OwdInventory where owdClient.clientId=489 and inventoryNum='" + sku + "'").list().get(0);


        } catch (Exception ex) {
            log.debug(ex.getMessage());
            log.debug(sku + " does not exist");
            return;
        }

        log.debug(item.getInventoryNum());
        item.setWeightLbs((float) weight);
        item.setCustomsDesc(customsdesc);
        item.setCustomsValue(custValue);
        if (item.getAdditionalInfo() == null) {
            item.setAdditionalInfo(new InventoryAdditionalInfo());
            item.getAdditionalInfo().setClientId(item.getOwdClient().getClientId());
            item.getAdditionalInfo().setDocumentsOnly(false);
            item.getAdditionalInfo().setForceShipment(false);
            item.getAdditionalInfo().setUsForcedMethod("");
            item.getAdditionalInfo().setCanForcedMethod("");
            item.getAdditionalInfo().setIntlForcedMethod("");
            item.getAdditionalInfo().setPoForcedMethod("");
            item.getAdditionalInfo().setOwdInventory(item);

        }
        item.getAdditionalInfo().setOriginCountry(country);
        item.setMfrPartNum(supplier);

        HibernateSession.currentSession().save(item.getAdditionalInfo());
        HibernateSession.currentSession().save(item);

        HibUtils.commit(HibernateSession.currentSession());
    }

    public static void createVirtualSku(int clientId, String kitSku, String kitDesc, float unitPrice) throws Exception {

        OwdApiClient.createVirtualSku(clientId, kitSku.trim(), kitDesc, unitPrice, false);

    }

    public static void createOrAddToKitSku(int clientId, String kitSku, String kitDesc, String compSku, int compQty) throws Exception {
        Map<String, Integer> compMap = new TreeMap<String, Integer>();
        compMap.put(compSku, compQty);

        try {
            OwdApiClient.createKitSkuWithComponents(clientId, kitSku.trim(), kitDesc, compMap, false);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            if (ex.getMessage().contains("already exists")) {

                InventoryManager.addToKitBySKUs(clientId, kitSku.trim(), compSku, compQty);
            }
        }
    }
    
    public static void checkAllClientSkusForBulkyFlag(Integer clientId) {
        String sql = "select inventory_id from owd_inventory where client_fkey = :clientId and (weight_lbs >= :weight or cubic_feet >= :feet)";
        
        try{
            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, clientId);
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId", client.getClientId());
            if(client.getBulkyPickCubicFeetDefault()>0||client.getBulkyPackCubicFeetDefault()>0){
                if(client.getBulkyPickCubicFeetDefault()>client.getBulkyPackCubicFeetDefault()){
                    q.setParameter("feet",client.getBulkyPackCubicFeetDefault());
                }else{
                   q.setParameter("feet",client.getBulkyPickCubicFeetDefault()); 
                }
                
            }else{
                q.setParameter("feet",bulkyCubicDefault);
            }
            if(client.getBulkyPickWeightDefault()>0||client.getBulkyPackWeightDefault()>0){
                if(client.getBulkyPickWeightDefault()>client.getBulkyPackWeightDefault()){
                    q.setParameter("weight",client.getBulkyPackWeightDefault());
                }else{
                    q.setParameter("weight",client.getBulkyPickWeightDefault());
                }

            }else{
                q.setParameter("weight",bulkyWeightDefault);
            }

            List l = q.list();

            for(Object row : l){

                checkAndSetBulkySkuForOwdInventory((OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.parseInt(row.toString())));

            }
            
            
            
            
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
        
        
        
        
    }


    /**
     *
     * @param dimensions The three sides of the item to be checked (can be unsorted)
     * @param longSide longest measurement threshold
     * @param medianSide median measurement threshold
     * @param shortSide shortest measurement threshold
     * @return boolean if any side is above threshold returns true
     */
    private static boolean isOneSideOverThreshold(List<Float> dimensions,BigDecimal longSide, BigDecimal medianSide, BigDecimal shortSide) throws Exception{
       //sort dimensions to get smallest to largest.
       Collections.sort(dimensions);
        log.debug(dimensions);
        if(dimensions.size()!=3){
            throw new Exception("dimensions passed is must be only 3 measurements(Length/Width/Height");
        }
        log.debug("Checking short side");
        if(shortSide.compareTo(new BigDecimal(dimensions.get(0)))==-1){
            return true;
        }
        log.debug("Checking median");
        log.debug(medianSide);
        log.debug(new BigDecimal(dimensions.get(1)));
        if(medianSide.compareTo(new BigDecimal(dimensions.get(1)))==-1){
            return true;
        }
        log.debug("Checking long side");
        if(longSide.compareTo(new BigDecimal(dimensions.get(2)))==-1){
            return true;
        }




        return false;
    }
    
    public static void checkAndSetBulkySkuForOwdInventory(OwdInventory inv) throws Exception{

        try {
            log.debug("Checking Picking");
            boolean bulkyPick = false;
            if (inv.getOwdClient().getBulkyPickWeightDefault() > 0) {
                bulkyPick = inv.getWeightLbs() >= inv.getOwdClient().getBulkyPickWeightDefault();

            } else {
                bulkyPick = inv.getWeightLbs() >= bulkyWeightDefault;
            }


            if (!bulkyPick) {
                log.debug("Checking Dimmensions for Bulky pick");
                if (inv.getOwdClient().getBulkyPickCubicFeetDefault() > 0) {

                    bulkyPick = inv.getCubicFeet() >= inv.getOwdClient().getBulkyPickCubicFeetDefault();
                } else {
                    //if no cubicFeetDefault is set on client, then check for dimension defaults on client.
                    if(inv.getOwdClient().getBulkyPickLongDefault().compareTo(BigDecimal.ZERO)==1){
                     List<Float> dimensions = new ArrayList<>();
                        dimensions.add(inv.getHeight());
                        dimensions.add(inv.getWidth());
                        dimensions.add(inv.getLength());
                        bulkyPick = isOneSideOverThreshold(dimensions,inv.getOwdClient().getBulkyPickLongDefault(),inv.getOwdClient().getBulkyPickMedianDefault(),inv.getOwdClient().getBulkyPickShortDefault());

                    }else {
                        bulkyPick = inv.getCubicFeet() >= bulkyCubicDefault;
                    }
                }
            }
            log.debug("Setting Bulky Pick " + inv.getInventoryNum() + " : " + bulkyPick);
            inv.setIsBulkyPick(bulkyPick);

        }catch (NullPointerException np){
            log.debug("Null value");
        }
            

        try {
            log.debug("Checking Packing");
            boolean bulkyPack = false;
            if (inv.getOwdClient().getBulkyPackWeightDefault() > 0) {
                bulkyPack = inv.getWeightLbs() >= inv.getOwdClient().getBulkyPackWeightDefault();
            } else {
                bulkyPack = inv.getWeightLbs() >= bulkyWeightDefault;
            }

            if (!bulkyPack) {
                log.debug("Checking Dimmensions for Bulky Pack");
                if (inv.getOwdClient().getBulkyPackCubicFeetDefault() > 0) {
                    bulkyPack = inv.getCubicFeet() >= inv.getOwdClient().getBulkyPackCubicFeetDefault();
                } else {
                    //if no cubicFeetDefault is set on client, then check for dimension defaults on client.

                    if(inv.getOwdClient().getBulkyPackLongDefault().compareTo(BigDecimal.ZERO)==1){
                        List<Float> dimensions = new ArrayList<>();
                        dimensions.add(inv.getHeight());
                        dimensions.add(inv.getWidth());
                        dimensions.add(inv.getLength());
                        bulkyPack = isOneSideOverThreshold(dimensions,inv.getOwdClient().getBulkyPackLongDefault(),inv.getOwdClient().getBulkyPackMedianDefault(),inv.getOwdClient().getBulkyPackShortDefault());

                    }else {
                        bulkyPack = inv.getCubicFeet() >= bulkyCubicDefault;
                    }
                }
            }
            log.debug("Setting BulkyPack: " + bulkyPack);
            inv.setIsBulkyPack(bulkyPack);
        }catch (NullPointerException npe){
            log.debug("Null on packing");
        }



        HibernateSession.currentSession().saveOrUpdate(inv);
        HibUtils.commit(HibernateSession.currentSession());
        
        
        
    }
    public static void main(String[] args) {

        try {

                adjustInventoryItemsFromCountData(429, HibernateSession.currentSession());
            HibUtils.commit(HibernateSession.currentSession());

          //  OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,36351);
         /*   //System.out.println(inv.getOwdClient().getBulkyPickWeightDefault());
            List<Float> dimensions = new ArrayList<>();
            dimensions.add(inv.getLength());
            dimensions.add(inv.getWidth());
            dimensions.add(inv.getHeight());

            log.debug(inv.getOwdClient().getBulkyPickLongDefault());
            log.debug(inv.getOwdClient().getClientId());

            log.debug(isOneSideOverThreshold(dimensions,inv.getOwdClient().getBulkyPickLongDefault(),inv.getOwdClient().getBulkyPickMedianDefault(),inv.getOwdClient().getBulkyPickShortDefault()));
         checkAndSetBulkySkuForOwdInventory(inv);*/

          //  checkAllClientSkusForBulkyFlag(622);



        //    createOrAddToKitSku(390, "1ST-G-BASIC", "", "Ist - Spell A", 1);
       //     createOrAddToKitSku(390, "1ST-G-BASIC", "", "Ist -Spell B", 1);


            //   InventoryManager.addToKitBySKUs(507,"GiftBundle01","TMug02",1);

          //  createOrAddToKitSku(390, "G1-Box-Math-Singapore", "", "SOFTCVR.PRIMATHWRK1A_1G.1", 1);
            //   createOrAddToKitSku(390, "G1-Box-Math-Singapore", "", "SOFTCVR.PRIMATHTXT1B_1G.1", 1);


    /*        createOrAddToKitSku(431, "2012-052", "", "2012-051", 1);
            createOrAddToKitSku(431, "2012-052", "", "2012-031", 1);


            createOrAddToKitSku(431, "2013-bundle-01", "", "2009-2", 1);
            createOrAddToKitSku(431, "2013-bundle-01", "", "2011-IS-001", 1);
            createOrAddToKitSku(431, "2013-bundle-01", "", "2012-001", 1);
            createOrAddToKitSku(431, "2013-bundle-01", "", "2012-039", 1);
            createOrAddToKitSku(431, "2013-bundle-01", "", "NCLS-1", 1);

*/
           /* InventoryManager.createOrAddToKitSku(571,"C1-CPPE-U1","","C1-CPPE-BP",1);
            InventoryManager.createOrAddToKitSku(571,"C1-CPPE-U1","","C1-U3",1);
            InventoryManager.createOrAddToKitSku(571,"C1-CPPE-U1","","C1-U2",1);
            InventoryManager.createOrAddToKitSku(571,"C1-CPPE-U2","","C1-CPPE-BP",1);
            InventoryManager.createOrAddToKitSku(571,"C1-CPPE-U2","","C1-U2",1);
            InventoryManager.createOrAddToKitSku(571,"C1-CPPE-U3","","C1-CPPE-BP",1);
            InventoryManager.createOrAddToKitSku(571,"C1-CPPE-U3","","C1-U3",1);*/

          //  InventoryManager.createOrAddToKitSku(266,"OmegaQuant Test 2 Pack","","OmegaQuant Test",2);
        //    InventoryManager.addToKitBySKUs(494,"ESF-SUB12","ESF-B30",12);
         //   InventoryManager.addToKitBySKUs(494,"ESF-SUB12","INS-RFRG",1);

            // log.debug(getStockMapByLocation(291859));


               /*  InventoryManager.createOrAddToKitSku(266,"AlgaeCal Basic 6 Month Supply Single","","AlgaeCal Basic",6);
            InventoryManager.createOrAddToKitSku(266,"AlgaeCal Plus 3 Month Supply Single","","AlgaeCal Plus",4);
            InventoryManager.createOrAddToKitSku(266,"AlgaeCal Plus 6 Month Supply","","8-Pack AlgaeCal Plus",1);
            InventoryManager.createOrAddToKitSku(266,"AlgaeCal Plus 6 Month Supply Single","","AlgaeCal Plus",8);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder 3 Month Single","","AlgaeCal Plus",4);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder 3 Month Single","","Strontium",3);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder 6 month AC Single","","AlgaeCal Plus",8);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder 6 month AC Single","","6-Pack Strontium",1);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder 6 month SB Single","","8-Pack AlgaeCal Plus",1);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder 6 month SB Single","","Strontium",6);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder 6 month Single","","AlgaeCal Plus",8);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder 6 month Single","","Strontium",6);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder Pack","","AlgaeCal Plus",1);
            InventoryManager.createOrAddToKitSku(266,"Bone Builder Pack","","Strontium",1);
            InventoryManager.createOrAddToKitSku(266,"Strontium 3 Month Supply Single","","Strontium",3);
            InventoryManager.createOrAddToKitSku(266,"Strontium 6 Month Supply","","6-Pack Strontium",1);
            InventoryManager.createOrAddToKitSku(266,"Strontium 6 Month Supply Single","","Strontium",6);
*/
/*     //  kathy smith
            InventoryManager.addToKitBySKUs(407,"IEG001","BAY219",1);
            InventoryManager.addToKitBySKUs(407,"IEG001","BAY2062",1);
            InventoryManager.addToKitBySKUs(407,"IEG001","AMP8645",1);*/



            //  InventoryManager.addToKitBySKUs(507,"MugBundle","TMug02",1);
                                                            //   InventoryManager.addToKitBySKUs(507,"MugBundle","mugins01",1);



    /*
               Session sess = HibernateSession.currentSession();

                                              TM-WIFI330 that will pull at TM-WIFI330-NS and AC-TMPRJ126
            InventoryManager.addToKitBySKUs(494,"TM-WIFI330","TM-WIFI330-NS",1);
            InventoryManager.addToKitBySKUs(441,"TM-WIFI330","AC-TMPRJ126",1);
            InventoryManager.addToKitBySKUs(441,"BC004-05","BC004",5);
            InventoryManager.addToKitBySKUs(441,"BC005-05","BC005",5);
            InventoryManager.addToKitBySKUs(441,"BC006-05","BC006",5);
             


 */



        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                HibUtils.rollback(HibernateSession.currentSession());
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }

        } finally {
            HibernateSession.closeSession();
        }


    }


    public static void postInventoryAdjustmentAsAdjustmentToCurrentValue(int inventoryID, int clientID, int adjustment, String referenceDateStr, String transactionRef, String facilityCode, Session session) throws Exception {
        postInventoryAdjustmentAsAdjustmentToCurrentValue(inventoryID,clientID,adjustment,referenceDateStr,transactionRef,facilityCode,session,"System Posted",null);
    }

    public static void postInventoryAdjustmentAsAdjustmentToCurrentValue(int inventoryID, int clientID, int adjustment, String referenceDateStr, String transactionRef, String facilityCode, Session session, String adjustBy, Map<String,Integer> lotMap) throws Exception {

        if(lotMap==null)
        {
            lotMap = new HashMap<String,Integer>();
        }
        ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        Date now = Calendar.getInstance().getTime();

        Date referenceDate = OWDUtilities.getDateForSQLDateString(referenceDateStr);

        if(!FacilitiesManager.getActiveFacilityCodes().contains(facilityCode))
        {
            throw new Exception("Invalid location code: "+facilityCode);
        }

        if (session.createQuery("from OwdReceive where is_void=0 and ref_num='" + transactionRef + "'").list().size() < 1) {

            OwdReceive oldrcv = new OwdReceive();
            oldrcv.setBlNum("");
            oldrcv.setCarrier("");
            oldrcv.setCreatedBy(adjustBy);
            oldrcv.setCreatedDate(referenceDate);
            oldrcv.setDriver("" + (0.00));
            oldrcv.setExpectedDate(referenceDate == null ? now : referenceDate);
            oldrcv.setIsVoid(false);
            oldrcv.setModifiedBy("System Posted");
            oldrcv.setModifiedDate(referenceDate == null ? now : referenceDate);
            oldrcv.setNotes("");
            oldrcv.setNumEmployees(Byte.decode("0x00"));
            oldrcv.setOwdClient((OwdClient) session.load(OwdClient.class, new Integer(clientID)));
            oldrcv.setReceiveDate(referenceDate == null ? now : referenceDate);
            oldrcv.setReceiveUser("System Posted");
            oldrcv.setRefNum(transactionRef == null ? "MANADJ-" + referenceDateStr + "-" + clientID + "-" + inventoryID : transactionRef);
            oldrcv.setRowIsLocked(false);
            oldrcv.setShipperRef("");
            oldrcv.setTimeIn(now);
            oldrcv.setTimeOut(now);
            oldrcv.setTotalTime(new BigDecimal(0.00));
            oldrcv.setTransactionNum("OWDADJ-" + now.getTime());
            oldrcv.setType("Adjustment");
            oldrcv.setFacilityCode(facilityCode);

            session.save(oldrcv);
            if (oldrcv.getOwdReceiveItems() == null) {
                oldrcv.setOwdReceiveItems(new ArrayList());
            }

            session.save(oldrcv);
            session.flush();


            OwdInventory inv = (OwdInventory) session.load(OwdInventory.class, new Integer(inventoryID));


            //  //log.debug("creating receive item");
            OwdReceiveItem ori = new OwdReceiveItem();
            ori.setIsVoid(0);
            ori.setOwdReceive(oldrcv);
            ori.setCreatedBy("testing");
            ori.setCreatedDate(referenceDate == null ? now : referenceDate);

            //OwdInventoryOh invOH = inv.getOwdInventoryOh();

            ori.setOwdInventory(inv);
            ori.setDescription(inv.getDescription());
            ori.setInventoryNum(inv.getInventoryNum());
            ori.setIsUnusable(false);
            ori.setItemStatus("New");
            ori.setLocation("");
            ori.setModifiedBy("");
            ori.setModifiedDate(now);
            ori.setQuantity(new Integer(adjustment));
            ori.setReported(false);
            ori.setReturnReason("");

            session.save(ori);

            int totalLotQty = 0;

            if(lotMap.keySet().size()>0) {
                for(String lotStr:lotMap.keySet()) {



                    OwdLotOwdReceiveItem lotRcvItem  = new OwdLotOwdReceiveItem();
                    lotRcvItem.setOwdReceiveItem(ori);
                    lotRcvItem.setLotValue(LotManager.getOwdLotValueForString(lotStr,ori.getOwdInventory().getInventoryId()));
                    lotRcvItem.setQuantityChange(lotMap.get(lotStr));
                    ori.getLots().add(lotRcvItem);
                    lotRcvItem.setOwdReceiveItem(ori);

                    session.save(lotRcvItem);

                    OwdInventoryHistory invOh = new OwdInventoryHistory();
                    invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
                    invOh.setQtyChange(lotRcvItem.getQuantityChange());
                    invOh.setLot(lotRcvItem.getLotValue());
                    invOh.setQtyChange(lotRcvItem.getQuantityChange());
                    invOh.setReceiveItemFkey(ori.getReceiveItemId());
                    invOh.setNote("InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue");
                    invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
                    session.save(invOh);
                    totalLotQty +=  lotRcvItem.getQuantityChange();
                }
                if(totalLotQty!=adjustment) {
                    throw new Exception("Total adjustment quantity ("+adjustment+") not equal to total lot adjustments ("+totalLotQty+")");
                }
            }else {
                OwdInventoryHistory invOh = new OwdInventoryHistory();
                invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
                invOh.setQtyChange(adjustment);
                invOh.setReceiveItemFkey(ori.getReceiveItemId());
                invOh.setNote("InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue");
                invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
                session.save(invOh);
            }

            oldrcv.getOwdReceiveItems().add(ori);

            //       //log.debug(oldrcv.getOwdReceiveItems().size());

            //     //log.debug("saving old receive");
            session.save(oldrcv);

            HibUtils.commit(session);
        } else {
            throw new Exception("transactionRef " + transactionRef + " already exists!");
        }


    }
        public static void postInventoryAdjustmentAsAdjustmentToCurrentValue(int inventoryID, int clientID, int adjustment, String referenceDateStr, String transactionRef, String facilityCode, Session session, String adjustBy) throws Exception {

            postInventoryAdjustmentAsAdjustmentToCurrentValue( inventoryID,  clientID,  adjustment,  referenceDateStr,  transactionRef,  facilityCode,  session,  adjustBy, null);
    }

    public static void postInventoryAdjustmentAsNewAbsoluteValue(int inventoryID, int clientID, int newCount, String referenceDateStr, String transactionRef, String facilityCode
            , Session session) throws Exception {
        postInventoryAdjustmentAsNewAbsoluteValue( inventoryID,  clientID,  newCount,  referenceDateStr,  transactionRef,  facilityCode
                ,  session, null);
    }
    public static void postInventoryAdjustmentAsNewAbsoluteValue(int inventoryID, int clientID, int newCount, String referenceDateStr, String transactionRef, String facilityCode
           , Session session, Map<String,Integer> lotMap) throws Exception {

        if(lotMap==null)
        {
            lotMap = new HashMap<String,Integer>();
        }

         ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

        //reference date as 'YYYY-mm-dd'
        Date now = Calendar.getInstance().getTime();
        if(!FacilitiesManager.getActiveFacilityCodes().contains(facilityCode))
        {
            throw new Exception("Invalid location code: "+facilityCode);
        }

        Date referenceDate = OWDUtilities.getDateForSQLDateString(referenceDateStr);

        if (session.createQuery("from OwdReceive where is_void=0 and ref_num='" + transactionRef + "'").list().size() < 1) {
            OwdReceive oldrcv = new OwdReceive();
            oldrcv.setBlNum("");
            oldrcv.setCarrier("");
            oldrcv.setCreatedBy("System Posted");
            oldrcv.setCreatedDate(referenceDate);
            oldrcv.setDriver("" + (0.00));
            oldrcv.setExpectedDate(referenceDate == null ? now : referenceDate);
            oldrcv.setIsVoid(false);
            oldrcv.setModifiedBy("System Posted");
            oldrcv.setModifiedDate(referenceDate == null ? now : referenceDate);
            oldrcv.setNotes("");
            oldrcv.setNumEmployees(Byte.decode("0x00"));
            oldrcv.setOwdClient((OwdClient) session.load(OwdClient.class, new Integer(clientID)));
            oldrcv.setReceiveDate(referenceDate == null ? now : referenceDate);
            oldrcv.setReceiveUser("System Posted");
            oldrcv.setRefNum(transactionRef == null ? "MANADJ-" + referenceDateStr + "-" + clientID + "-" + inventoryID : transactionRef);
            oldrcv.setRowIsLocked(false);
            oldrcv.setShipperRef("");
            oldrcv.setTimeIn(now);
            oldrcv.setTimeOut(now);
            oldrcv.setTotalTime(new BigDecimal(0.00));
            oldrcv.setTransactionNum("OWDADJ-" + now.getTime());
            oldrcv.setType("Adjustment");
            oldrcv.setFacilityCode(facilityCode);


            session.save(oldrcv);
            if (oldrcv.getOwdReceiveItems() == null) {
                oldrcv.setOwdReceiveItems(new ArrayList());
            }

            session.save(oldrcv);
            session.flush();


            OwdInventory inv = (OwdInventory) session.load(OwdInventory.class, new Integer(inventoryID));


            //log.debug("creating receive item");
            OwdReceiveItem ori = new OwdReceiveItem();
            ori.setIsVoid(0);
            ori.setOwdReceive(oldrcv);
            ori.setCreatedBy("System");
            ori.setCreatedDate(referenceDate == null ? now : referenceDate);

           // OwdInventoryOh invOH = inv.getOwdInventoryOh();

            ori.setOwdInventory(inv);
            ori.setDescription(inv.getDescription());
            ori.setInventoryNum(inv.getInventoryNum());
            ori.setIsUnusable(false);
            ori.setItemStatus("New");
            ori.setLocation("");
            ori.setModifiedBy("");
            ori.setModifiedDate(referenceDate == null ? now : referenceDate);

            ori.setReported(false);
            ori.setReturnReason("");

        //    int qtyChange = getStockLevelForFacility(inv.getInventoryId(),facilityCode);

            //log.debug("Setting value "+newCount+" : "+invOH.getQtyOnHand().intValue());
            ori.setQuantity(new Integer(newCount - getStockLevelForFacility(inv.getInventoryId(),facilityCode)));


            session.save(ori);

            int totalLotQty = 0;


            if(lotMap.keySet().size()>0) {

                 //clear old stock value for parent item
                int oldParentLevel = getStockLevelForFacility(inv.getInventoryId(),facilityCode);
                //clear all current lot values
                for(String lotValue:LotManager.getLotValuesForInventoryId(ori.getOwdInventory().getInventoryId())) {
                    OwdLotValue lot = LotManager.getOwdLotValueForString(lotValue, ori.getOwdInventory().getInventoryId());
                    int oldLevel = getStockLevelForFacilityLot(inv.getInventoryId(),facilityCode, lot.getId());
                    if(oldLevel!=0) {
                        OwdInventoryHistory invOh = new OwdInventoryHistory();
                        invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
                        invOh.setLot(lot);
                        invOh.setQtyChange(-1 * oldLevel);
                        invOh.setReceiveItemFkey(ori.getReceiveItemId());
                        invOh.setNote("InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValueLot");
                        invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
                        session.save(invOh);
                        session.flush();
                        oldParentLevel -= oldLevel;
                    }
                }

                if(oldParentLevel>0) {
                    //catch anything not accounted for in lot values
                    OwdInventoryHistory invOh = new OwdInventoryHistory();
                    invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
                    invOh.setQtyChange(-1* oldParentLevel);
                    invOh.setReceiveItemFkey(ori.getReceiveItemId());
                    invOh.setNote("InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue");
                    invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
                    session.save(invOh);
                    session.flush();

                }



                for(String lotStr:lotMap.keySet()) {



                    OwdLotOwdReceiveItem lotRcvItem  = new OwdLotOwdReceiveItem();
                    lotRcvItem.setOwdReceiveItem(ori);
                    lotRcvItem.setLotValue(LotManager.getOwdLotValueForString(lotStr,ori.getOwdInventory().getInventoryId()));
                    lotRcvItem.setQuantityChange(new Integer(lotMap.get(lotStr)));
                    ori.getLots().add(lotRcvItem);
                    lotRcvItem.setOwdReceiveItem(ori);

                    session.save(lotRcvItem);



                    //set new value
                    OwdInventoryHistory invOh = new OwdInventoryHistory();
                    invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
                    invOh.setLot(lotRcvItem.getLotValue());
                    invOh.setQtyChange(lotMap.get(lotStr));
                    invOh.setReceiveItemFkey(ori.getReceiveItemId());
                    invOh.setNote("InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue");
                    invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
                    session.save(invOh);
                    session.flush();

                    totalLotQty += lotMap.get(lotStr);
                }
                if(totalLotQty!=newCount) {
                    throw new Exception("Total lot quantity ("+totalLotQty+") not equal to new absolute quantity ("+newCount+")");
                }

            }else {


                OwdInventoryHistory invOh = new OwdInventoryHistory();
                invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
                invOh.setQtyChange(ori.getQuantity());
                invOh.setReceiveItemFkey(ori.getReceiveItemId());
                invOh.setNote("InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue");
                invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
                session.save(invOh);
                session.flush();

            }
            oldrcv.getOwdReceiveItems().add(ori);

            //log.debug(oldrcv.getOwdReceiveItems().size());

            //log.debug("saving old receive");
            session.save(oldrcv);

            HibUtils.commit(session);
        } else {
            throw new Exception("transactionRef " + transactionRef + " already exists!");
        }

    }



    public static int getStockLevelForFacility(int inventoryId,String locCode) throws Exception
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
       int level = 0;
        try{
            log.debug("getstock:" + inventoryId + ":" + locCode);
         ps = HibernateSession.getPreparedStatement("select ISNULL(qty,0) as qty from vw_stock_by_facility where inventory_id=? and loc_code=?");
         ps.setInt(1, inventoryId);
         ps.setString(2,locCode);
            rs = ps.executeQuery();
            if(rs.next())
            {
                level = rs.getInt("qty");
            }
        }catch(Exception ex)
        {
            throw ex;
        }            finally
        {
            try{
           rs.close();  }catch(Exception ex)
            {
            }
           try{ ps.close();  }catch(Exception ex)
           {
           }
        }
        return level;
    }

    public static int getStockLevelForFacilityLot(int inventoryId,String locCode, int lotId) throws Exception
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        int level = 0;
        try{
            log.debug("getstock:" + inventoryId + ":" + locCode+ ":" + lotId);
            ps = HibernateSession.getPreparedStatement("select ISNULL(qty,0) as qty from vw_stock_by_facility_lot where inventory_id=? and loc_code=? and lot_fkey=?");
            ps.setInt(1, inventoryId);
            ps.setString(2,locCode);
            ps.setInt(3,lotId);
            rs = ps.executeQuery();
            if(rs.next())
            {
                level = rs.getInt("qty");
            }
        }catch(Exception ex)
        {
            throw ex;
        }            finally
        {
            try{
                rs.close();  }catch(Exception ex)
            {
            }
            try{ ps.close();  }catch(Exception ex)
            {
            }
        }
        return level;
    }



    public static Map<String,Map<String, Integer>>  getStockMapByLocationAndLot(int inventoryId)
    {

        ResultSet rs = null;
        PreparedStatement ps = null;
        Map<String,Map<String, Integer>> locMap = new HashMap<String,Map<String, Integer>>();

        int level = 0;
        try{
            log.debug("getstockmap:" + inventoryId);
            ps = HibernateSession.getPreparedStatement("select loc_code, lot_value, ISNULL(qty,0) as qty from vw_stock_by_facility_lot where inventory_id=? order by loc_code,lot_value");
            ps.setInt(1, inventoryId);
            rs = ps.executeQuery();
            while(rs.next())
            {
                String loc = rs.getString(1);
                if(!(locMap.containsKey(loc))) {
                    locMap.put(loc,new HashMap<String,Integer>());
                }
                locMap.get(loc).put(rs.getString("lot_value"), rs.getInt("qty"));
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }            finally
        {
            try{
                rs.close();  }catch(Exception ex)
            {
            }
            try{ ps.close();  }catch(Exception ex)
            {
            }
        }
        return locMap;
    }

    public static Map<String, Integer>  getStockMapByLocation(int inventoryId)
    {

        ResultSet rs = null;
        PreparedStatement ps = null;
        Map<String, Integer> locMap = new HashMap<String, Integer>();

        int level = 0;
        try{
            log.debug("getstockmap:" + inventoryId);
            ps = HibernateSession.getPreparedStatement("select loc_code, ISNULL(qty,0) as qty from vw_stock_by_facility where inventory_id=? ");
            ps.setInt(1, inventoryId);
            rs = ps.executeQuery();
            while(rs.next())
            {
                locMap.put(rs.getString("loc_code"), rs.getInt("qty"));
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }            finally
        {
            try{
                rs.close();  }catch(Exception ex)
            {
            }
            try{ ps.close();  }catch(Exception ex)
            {
            }
        }
        return locMap;
    }


    public static void adjustInventoryItemsFromCountData(int countID, Session session) throws Exception {

        //look up count ID to get count records

        //get count items


        WInvRequest count = (WInvRequest) session.load(WInvRequest.class, new Integer(countID));

        if(FacilitiesManager.isClientMultiFacility(count.getClientFkey())){
            throw new Exception("Client is Multi Facility you can use this system");

        }
        OwdClient client = (OwdClient) session.load(OwdClient.class, new Integer(count.getClientFkey()));
        System.out.println(client.getDefaultFacilityCode());
        if(!FacilitiesManager.getActiveFacilityCodes().contains(client.getDefaultFacilityCode())){
            throw new Exception("Facility Code is not active for this client");
        }

        clearAllUnusedLocationsForInventoryCount(count, session);


        String countQuery =
                "(select inventory_id,[Current Adjustment Needed] from vw_wcountdata  where inv_request_fkey=?)  \n" +
                        "  union  \n" +
                        "  (select i.inventory_id,  -1*(ISNULL(postedunpicked,0)+qty_on_hand) as 'Current Adjustment Needed' \n" +
                        "  from owd_inventory i (NOLOCK) join w_inv_request r (NOLOCK) on r.client_fkey=i.client_fkey and r.id=?  \n" +
                        "  INNER JOIN dbo.owd_inventory_oh h (NOLOCK) ON h.inventory_fkey = i.inventory_id    \n" +
                        "  LEFT OUTER JOIN  (SELECT  l.inventory_id AS 'iid', SUM(ISNULL(quantity_actual, 0)) AS postedunpicked, client_fkey    \n" +
                        "   FROM  \n" +
                        "  owd_line_item l (NOLOCK) JOIN  \n" +
                        "  owd_order (NOLOCK) ON order_id = l.order_fkey AND post_date >'2000-1-1' AND is_void = 0 AND   \n" +
                        "  ship_packs = 0 AND pick_status = 0   \n" +
                        "   GROUP BY l.inventory_id, client_fkey) unpicked ON i.inventory_id = unpicked.iid   \n" +
                        "  where i.inventory_id not in (select distinct c.inventory_id    \n" +
                        "  FROM w_inv_request r (NOLOCK)  \n" +
                        "   INNER JOIN dbo.w_inv_locations l (NOLOCK)  \n" +
                        "    INNER JOIN dbo.w_inv_counts c (NOLOCK)   \n" +
                        "  ON c.inv_loc_fkey = l.id   \n" +
                        "    on r.id=l.inv_request_fkey   \n" +
                        "  where r.done=0 and r.id=? and c.quanity>0) and qty_on_hand>0 and i.is_auto_inventory=0  \n" +
                        "  )\n" +
                        "order by inventory_id";

        String unpickableQuery =
                "select * from (\n" +
                        "(select inventory_num,description,[Current Adjustment Needed],unpicked,[on hand],counted from vw_wcountdata  where inv_request_fkey=?)  \n" +
                        "  union  \n" +
                        "  (\n" +
                        "select i.inventory_num,i.description,  -1*(ISNULL(postedunpicked,0)+qty_on_hand) as 'Current Adjustment Needed' ,\n" +
                        "ISNULL(postedunpicked,0) as Unpicked,  \n" +
                        "h.qty_on_hand as 'On Hand',  \n" +
                        "0 as Counted\n" +
                        "\n" +
                        "  from owd_inventory i (NOLOCK) join w_inv_request r (NOLOCK) on r.client_fkey=i.client_fkey and r.id=?  \n" +
                        "  INNER JOIN dbo.owd_inventory_oh h (NOLOCK) ON h.inventory_fkey = i.inventory_id    \n" +
                        "  LEFT OUTER JOIN  (SELECT  l.inventory_id AS 'iid', SUM(ISNULL(quantity_actual, 0)) AS postedunpicked, client_fkey    \n" +
                        "   FROM  \n" +
                        "  owd_line_item l (NOLOCK) JOIN  \n" +
                        "  owd_order (NOLOCK) ON order_id = l.order_fkey AND post_date >'2000-1-1' AND is_void = 0 AND   \n" +
                        "  ship_packs = 0 AND pick_status = 0   \n" +
                        "   GROUP BY l.inventory_id, client_fkey) unpicked ON i.inventory_id = unpicked.iid   \n" +
                        "  where i.inventory_id not in (select distinct c.inventory_id    \n" +
                        "  FROM w_inv_request r (NOLOCK)  \n" +
                        "   INNER JOIN dbo.w_inv_locations l (NOLOCK)  \n" +
                        "    INNER JOIN dbo.w_inv_counts c (NOLOCK)   \n" +
                        "  ON c.inv_loc_fkey = l.id   \n" +
                        "    on r.id=l.inv_request_fkey   \n" +
                        "  where r.done=0 and r.id=? and c.quanity>0) and qty_on_hand>0 and i.is_auto_inventory=0 \n" +
                        "  )) as stuff where counted<unpicked";


        OwdReceive oldrcv = new OwdReceive();
        oldrcv.setBlNum("");
        oldrcv.setCarrier("");
        oldrcv.setCreatedBy("Inventory Count");
        oldrcv.setCreatedDate(count.getDateCreated());
        oldrcv.setDriver("" + (0.00));
        oldrcv.setExpectedDate(count.getDateCreated());
        oldrcv.setIsVoid(false);
        oldrcv.setModifiedBy("Inventory Count");
        oldrcv.setModifiedDate(Calendar.getInstance().getTime());
        oldrcv.setNotes("");
        oldrcv.setNumEmployees(Byte.decode("0x00"));
        oldrcv.setOwdClient((OwdClient) session.load(OwdClient.class, new Integer(count.getClientFkey())));
        oldrcv.setReceiveDate(count.getDateCreated());
        oldrcv.setReceiveUser("Inventory Count");
        oldrcv.setRefNum(count.getDesription().length() > 0 ? count.getDesription() : "COUNTOWD" + count.getId());
        oldrcv.setRowIsLocked(false);
        oldrcv.setShipperRef("");
        oldrcv.setTimeIn(count.getDateCreated());
        oldrcv.setTimeOut(count.getDateCreated());
        oldrcv.setTotalTime(new BigDecimal(0.00));
        oldrcv.setTransactionNum("OWDCOUNT-" + count.getId());
        oldrcv.setType("InventoryCount");
        oldrcv.setFacilityCode(client.getDefaultFacilityCode());


        session.save(oldrcv);
        if (oldrcv.getOwdReceiveItems() == null) {
            oldrcv.setOwdReceiveItems(new ArrayList());
        }

        session.save(oldrcv);
        session.flush();

        PreparedStatement ps = HibernateSession.getPreparedStatement(unpickableQuery);
        ps.setInt(1, countID);
        ps.setInt(2, countID);
        ps.setInt(3, countID);
        ResultSet rs1 = ps.executeQuery();
        if (rs1.next()
                ) {
            throw new Exception("Posted units exceed available items - see count summary web page for details and unpost affected orders before finalizing count adjustments");
        }

        rs1.close();
        ps.close();

        ps = HibernateSession.getPreparedStatement(countQuery);
        ps.setInt(1, countID);
        ps.setInt(2, countID);
        ps.setInt(3, countID);
        ResultSet rs = ps.executeQuery();
        Map<Integer, Integer> adjMap = new HashMap<Integer, Integer>();

        while (rs.next()) {
            adjMap.put(rs.getInt(1), rs.getInt(2));
        }
        rs.close();
        createAdjustmentItems(adjMap, session, oldrcv, countID);

        rs.close();

        //  rs = HibernateSession.getResultSet(uncountedQuery);

        //   createAdjustmentItems(rs, session, oldrcv, countID);

        //log.debug(oldrcv.getOwdReceiveItems().size());

        //log.debug("saving old receive");
        session.save(oldrcv);

        count.setDone(new Integer(1));
        session.save(count);

    }

    private static void createAdjustmentItems(Map<Integer, Integer> rsx, Session session, OwdReceive oldrcv, int countID) throws Exception {

         ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        for (Integer inventoryID : rsx.keySet()) {

            //     int inventoryID = rsx.getInt(1);
            int adjustment = rsx.get(inventoryID);

            log.debug("adjusting item " + inventoryID + ":" + adjustment);
            OwdInventory inv = (OwdInventory) session.load(OwdInventory.class, new Integer(inventoryID));
            //  log.debug("1:"+rsx.getInt(1));

            //log.debug("creating receive item");
            OwdReceiveItem ori = new OwdReceiveItem();
            ori.setIsVoid(0);
            ori.setOwdReceive(oldrcv);
            ori.setCreatedBy("testing");
            ori.setCreatedDate(Calendar.getInstance().getTime());
            //     log.debug("2:"+rsx.getInt(1));
            //OwdInventoryOh invOH = inv.getOwdInventoryOh();
            //  invOH.setQtyOnHand(new Integer(invOH.getQtyOnHand().intValue() + adjustment));
            // if(invOH.getQtyOnHand().intValue()<0) invOH.setQtyOnHand(new Integer(0));
            ori.setOwdInventory(inv);
            ori.setDescription(inv.getDescription());
            ori.setInventoryNum(inv.getInventoryNum());
            ori.setIsUnusable(false);
            ori.setItemStatus("New");
            ori.setLocation("");
            ori.setModifiedBy("");
            ori.setModifiedDate(Calendar.getInstance().getTime());
            ori.setQuantity(new Integer(adjustment));
            ori.setReported(false);
            ori.setReturnReason("");

            session.save(ori);
            OwdInventoryHistory invOh = new OwdInventoryHistory();
            invOh.setInventoryFkey(ori.getOwdInventory().getInventoryId());
            invOh.setQtyChange(adjustment);
            invOh.setReceiveItemFkey(ori.getReceiveItemId());
            invOh.setNote("InventoryManager.createAdjustmentItems");
            invOh.setFacility(FacilitiesManager.getFacilityForCode(oldrcv.getFacilityCode()));
            session.save(invOh);
            oldrcv.getOwdReceiveItems().add(ori);
            //        log.debug("3:"+rsx.getInt(1));
            log.debug("done with adjustment to quantities");
            //clear old locations

            //add new locations
            if (countID > 0) {
                clearAllLocationsForInventoryID(inventoryID, session);
                //      log.debug("4:"+rsx.getInt(1));
                setLocationsForInventoryID(inventoryID, countID, session);
                //       log.debug("5:"+rsx.getInt(1));
                updateBarcodesForInventoryID(inventoryID, countID, session);
                //      log.debug("6:"+rsx.getInt(1));
            }

            log.debug("done with item");
        }
    }

    public static boolean countInProgress(Session sess, int clientFkey) throws Exception {

        Connection conn =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection();
        Statement stmt = conn.createStatement();

        String sqlQuery = "select count(*) from w_inv_request where  done=0 and client_fkey = " + clientFkey;
        ResultSet rs1 = stmt.executeQuery(sqlQuery);
        rs1.next();

        return rs1.getInt(1) > 0;


    }
}


/*
  Start with list of locations and quantities for each item and client context

  Want to:

  1. Clear locations for all client items
  2. Add locations from list for each item
  3. Determine posted-not-picked (pnp) quantity for each item
  4. Subtract pnp from each item's count for final on hand count per item
  5. Collect all items and old and new on hand counts and insert into new owd_receive/receive_item records as adjustments
  6. Save owd_receive/item records and update qty_on_hand for each item based on difference in receive record

*/


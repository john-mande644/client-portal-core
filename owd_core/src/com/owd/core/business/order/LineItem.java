package com.owd.core.business.order;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.ResultManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.*;


public class LineItem {
private final static Logger log =  LogManager.getLogger();
    //IDs are zero if the line item is new

    @Override
    public String toString() {
        return "LineItem{" +
                "line_item_id='" + line_item_id + '\'' +
                ", order_fkey='" + order_fkey + '\'' +
                ", inventory_fkey='" + inventory_fkey + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                ", client_part_no='" + client_part_no + '\'' +
                ", client_ref_num='" + client_ref_num + '\'' +
                ", quantity_request=" + quantity_request +
                ", quantity_actual=" + quantity_actual +
                ", quantity_backordered=" + quantity_backordered +
                ", sku_price=" + sku_price +
                ", total_price=" + total_price +
                ", line_item_disc=" + line_item_disc +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", long_desc='" + long_desc + '\'' +
                ", customs_desc='" + customs_desc + '\'' +
                ", dec_item_value=" + dec_item_value +
                ", parent_line=" + parent_line +
                ", is_parent=" + is_parent +
                ", force_backorder_quantity=" + force_backorder_quantity +
                ", tempID=" + tempID +
                ", insertedItem=" + insertedItem +
                ", needsUpdate=" + needsUpdate +
                ", inventory=" + inventory +
                '}';
    }
//Attributes
    private static final String kdbLineItemPKName = "line_item_id";
    public String line_item_id = "0";

    private static final String kdbLineItemOrderFkey = "order_fkey";
    public String order_fkey = "0";

    private static final String kdbLineItemInventoryFkey = "inventory_id";
    public String inventory_fkey = "0";

    private static final String kdbLineItemCreatedBy = "created_by";
    public String createdBy = "";

    private static final String kdbLineItemCreatedOn = "created_date";
    public String createdOn = "";

    private static final String kdbLineItemModifiedBy = "modified_by";
    public String modifiedBy = "";

    private static final String kdbLineItemModifiedOn = "modified_date";
    public String modifiedOn = "";


    //Properties
    private static final String kdbLineItemClientPartID = "inventory_num";
    public String client_part_no = "";

    private static final String kdbLineItemCustomerReference = "cust_refnum";
    public String client_ref_num = "";

    private static final String kdbLineItemRequested = "quantity_request";
    public long quantity_request = 0;

    private static final String kdbLineItemAllotted = "quantity_actual";
    public long quantity_actual = 0;

    private static final String kdbLineItemBackordered = "quantity_back";
    public long quantity_backordered = 0;

    private static final String kdbLineItemItemPrice = "price";
    public float sku_price = 0;

    private static final String kdbLineItemLinePrice = "total_price";
    public float total_price = 0;

    private static final String kdbLineItemLineDiscount = "line_item_disc";
    public float line_item_disc = 0;

    private static final String kdbLineItemDescription = "description";
    public String description = "";

    private static final String kdbLineItemColor = "item_color";
    public String color = "";

    private static final String kdbLineItemSize = "item_size";
    public String size = "";

    private static final String kdbLineItemLongDesc = "long_desc";
    public String long_desc = "";

    private static final String kdbLineItemCustomsDesc = "customs_desc";
    public String customs_desc = "";

    private static final String kdbLineItemDeclaredValue = "dec_item_value";
    public float dec_item_value = 0;

    private static final String kdbLineItemParentLineID = "parent_line";
    public Integer parent_line = null;

    private static final String kdbLineItemIsParent = "is_parent";
    public Integer is_parent = new Integer(0);

    public boolean force_backorder_quantity = false;
    public int tempID = 0;


    private static final String kdbInsertItem = "is_insert";
    public boolean insertedItem = false;

    private static final String kdbLineItemTable = "owd_line_item";
    private static final String kDeleteItemStatement = "DELETE FROM " + kdbLineItemTable + " where " + kdbLineItemPKName + " = ?";

    private boolean needsUpdate = false;

    private Map<String, String> tagMap = new TreeMap<String, String>();

    public String getID() {
        return line_item_id;
    }

    public LineItem() {
        //for testing
    }

    public LineItem(String clientSKU, int quant, float itemPrice, float linePrice, String desc, String icolor, String isize) {
        this(clientSKU, quant, itemPrice, linePrice, desc, icolor, isize, (float) 0.00, "");
    }

    public LineItem(String clientSKU, int quant, float itemPrice, float linePrice, String desc, String icolor, String isize, float dvalue, String cdesc) {
        this(clientSKU, quant, itemPrice, linePrice, desc, icolor, isize, dvalue, cdesc,null,null);

    }
    public LineItem(String clientSKU, int quant, float itemPrice, float linePrice, String desc, String icolor, String isize, float dvalue, String cdesc, Integer isParent, Integer parentLine) {
        client_part_no = clientSKU;
        description = desc;
        quantity_request = quant;
        sku_price = itemPrice;
        total_price = linePrice;
        color = icolor;
        size = isize;
        dec_item_value = dvalue;
        customs_desc = cdesc;

        if (quantity_request < 0) quantity_request = 0;
        quantity_actual = 0;
        quantity_backordered = 0;
        if (sku_price < 0) sku_price = 0;
        if (total_price < 0) total_price = 0;

        total_price = OWDUtilities.roundFloat(total_price);

        float calcLinePrice = OWDUtilities.roundFloat(sku_price * (quantity_request + quantity_backordered));

        if (total_price < calcLinePrice && ((calcLinePrice - total_price) >= 0.01f)) {
            line_item_disc = OWDUtilities.roundFloat(calcLinePrice - total_price);
        }         else
        {
             total_price=OWDUtilities.roundFloat(sku_price * (quantity_request + quantity_backordered));
        }

        is_parent = isParent;
        parent_line = parentLine;

        if(is_parent==null) is_parent=new Integer(0);
    }
    public long getQuantityRequest(){ return quantity_request;}



    /**
     * Verifies inventory for order item.
     *
     * @param clientKey
     * @throws Exception
     */
    public void verifyInventory(String clientKey) throws Exception {

        if(inventory!=null) {
            inventory_fkey=""+inventory.inventory_id;

            if (description == null || ("".equals(description)))
                description = inventory.description;

            if (color == null || ("".equals(color)))
                color = inventory.item_color;

            if (size == null || ("".equals(size)))
                size = inventory.item_size;

        } else {
            ResultSet rs = null;
            PreparedStatement stmt = null;
            Connection cxn = null;

            try {
                cxn = ConnectionManager.getConnection();

                String esql = "select inventory_id, description, item_color, item_size from owd_inventory (NOLOCK) " +
                        "where inventory_num = ? " +
                        " and client_fkey = ? ";

                stmt = cxn.prepareStatement(esql);
                stmt.setString(1, client_part_no);
                stmt.setInt(2, new Integer(clientKey));

                rs = stmt.executeQuery();

                if (rs.next()) {
                    inventory_fkey = rs.getString(1);

                    if (description == null || ("".equals(description)))
                        description = rs.getString(2);

                    if (color == null || ("".equals(color)))
                        color = rs.getString(3);

                    if (size == null || ("".equals(size)))
                        size = rs.getString(4);
                } else {
                    throw new Exception("Inventory SKU " + client_part_no + " is not valid");
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                try {
                    rs.close();
                } catch (Exception ex) {
                }
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
                try {
                    ConnectionManager.freeConnection(cxn);
                } catch (Exception ex) {
                }
            }
        }
    }


    /**
     * Verifies inventory for order item.
     *
     * @param clientKey
     * @throws Exception
     */
    public void verifyInventoryRM(String clientKey) throws Exception {

        if(inventory!=null) {
            inventory_fkey=""+inventory.inventory_id;

            if (description == null || ("".equals(description)))
                description = inventory.description;

            if (color == null || ("".equals(color)))
                color = inventory.item_color;

            if (size == null || ("".equals(size)))
                size = inventory.item_size;

        } else {
            ResultSet rs = null;

            try {
                String sql = "select inventory_id, description, item_color, item_size from owd_inventory (NOLOCK) " +
                        "where inventory_num = ? " +
                        " and client_fkey = ? ";

                ArrayList<Object> parmList = new ArrayList();
                parmList.add(client_part_no);
                parmList.add(new Integer(clientKey));

                ResultManager resultManager = new ResultManager(sql, parmList);
                rs = resultManager.getResultSet();

                if (rs.next()) {
                    inventory_fkey = rs.getString(1);

                    if (description == null || ("".equals(description)))
                        description = rs.getString(2);

                    if (color == null || ("".equals(color)))
                        color = rs.getString(3);

                    if (size == null || ("".equals(size)))
                        size = rs.getString(4);
                } else {
                    throw new Exception("Inventory SKU " + client_part_no + " is not valid");
                }
                resultManager.closeConnectionComponents();

            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    public static boolean SKUExists(String clt, String SKU) {
        return SKUExists(clt, SKU, true);
    }

    public static void main(String[] args) {
        log.debug(SKUExists("112","HELLO"));
    }

    public static long getQuantityForSkuAtLocation(String clt, String SKU, String locCode) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (SKU == null) {
            SKU = "";
            return 0;
        }

        if (SKU.equals("")) {
            return 0;
        }

        long exists = 0;

        SKU = getCleanSKU(SKU);

        try {
            cxn = ConnectionManager.getConnection();
            log.debug(SKU);
            String esql = "select qty from owd_inventory_facility (NOLOCK) join owd_inventory (NOLOCK) on inventory_id=inventory_fkey join owd_facilities f on f.id=facility_fkey" +
                    " where inventory_num = ? and client_fkey=? and loc_code=?";

            stmt = cxn.prepareStatement(esql);
            stmt.setString(1,SKU);
            stmt.setInt(2,new Integer(clt));
            stmt.setString(3, locCode);
            rs = stmt.executeQuery();


            if (rs.next()) {
                exists = rs.getLong(1);
            } else
            {
                throw new Exception("SKU does not exist");
            }
            return exists;
        } catch (Exception ex) {
            throw ex;
        } finally {

            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }


    } 
   /* public static long getQuantityForSku(String clt, String SKU) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (SKU == null) {
            SKU = "";
            return 0;
        }

        if (SKU.equals("")) {
            return 0;
        }

        long exists = 0;

        SKU = getCleanSKU(SKU);

        try {
            cxn = ConnectionManager.getConnection();
            log.debug(SKU);
            String esql = "select qty_on_hand from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey where inventory_num = ? and client_fkey=?";

            stmt = cxn.prepareStatement(esql);
            stmt.setString(1,SKU);
            stmt.setInt(2,new Integer(clt));
            rs = stmt.executeQuery();


            if (rs.next()) {
                exists = rs.getLong(1);
            } else
            {
                throw new Exception("SKU does not exist");
            }
            return exists;
        } catch (Exception ex) {
            throw ex;
        } finally {

            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }


    }
*/

    public static boolean SKUExists(String clt, String SKU, boolean allowInactiveSKUMatches) {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (SKU == null) {
            SKU = "";
            return false;
        }

        if (SKU.equals("")) {
            return false;
        }

        boolean exists = false;

        SKU = getCleanSKU(SKU);

        try {
            cxn = ConnectionManager.getConnection();
            log.debug(SKU);
            String esql = "select inventory_id from owd_inventory (NOLOCK) where inventory_num = ? and client_fkey=?" + (allowInactiveSKUMatches ? "" : " and is_active=1");

            stmt = cxn.prepareStatement(esql);
            stmt.setString(1,SKU);
            stmt.setInt(2,new Integer(clt));
            rs = stmt.executeQuery();


                if (rs.next()) {
                    exists = (0 < rs.getInt(1));
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
            return exists;
        }

    }

    public static boolean SDKitSKUExists(String clt, String SKU) {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;

        if (SKU == null) {
            SKU = "";
            return false;
        }

        if (SKU.equals("")) {
            return false;
        }

        boolean exists = false;

        SKU = getCleanSKU(SKU);

        try {
            cxn = ConnectionManager.getConnection();
            String esql = "select count(*) from sdkits where SKU = \'" + SKU + "\'";

            stmt = cxn.createStatement();
            boolean hasResultSet = stmt.execute(esql);

            if (hasResultSet) {
                rs = stmt.getResultSet();

                if (rs.next()) {
                    exists = (0 < rs.getInt(1));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
            return exists;
        }

    }

    Inventory inventory  = null;

    public Inventory getInventory(Connection cxn) throws Exception
    {
       if(inventory==null)
       {
           inventory = Inventory.getInventoryForID(cxn,inventory_fkey+"");
       }

        return inventory;
    }
    public void setInventory(Inventory i)
    {
        inventory = i;
    }
    public Inventory getInventory() throws Exception
    {
       if(inventory==null)
       {
           inventory = Inventory.getInventoryForID(inventory_fkey+"");
       }

        return inventory;
    }
     static public Map getRequiredItemsForInventoryID(Integer invID) {
        Map itemMap = new TreeMap();
        ResultSet rs = null;
        Statement stmt = null;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnectionManager().getConnection();

            stmt = cxn.createStatement();
            String esql = "select req_inventory_fkey, req_inventory_quant  from owd_inventory_required_skus (NOLOCK) " +
                    "where  inventory_fkey = " + invID;
            log.debug(esql);
            boolean hasResultSet = stmt.execute(esql);

            rs = stmt.getResultSet();

            while (rs.next()) {
                itemMap.put(new Integer(rs.getInt(1)),new Integer(rs.getInt(2)));

            }


        } catch (Exception ex) {

           ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                ConnectionManager.freeConnection(cxn);
            } catch (Exception ex) {
            }





        }

             return itemMap;
    }

    /**
     * Returns description for a given sku code and client.
     *
     * @param skuCode
     * @param clientKey
     * @return
     */
    static public String getDescriptionForSKU(String skuCode, String clientKey) {
        String desc = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnectionManager().getConnection();

            String esql = "select description  from owd_inventory (NOLOCK) " +
                    "where inventory_num = ? " +
                    "and client_fkey = ? ";

            stmt = cxn.prepareStatement(esql);
            stmt.setString(1, skuCode);
            stmt.setInt(2, new Integer(clientKey));

            rs = stmt.executeQuery();

            if (rs.next()) {
                desc = rs.getString(1);
            }


        } catch (Exception ex) {


        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                ConnectionManager.freeConnection(cxn);
            } catch (Exception ex) {
            }

            if (desc == null)
                return "";

            return desc;
        }
    }

    /**
     * Returns barcode for given sku and client key.
     *
     * @param skuCode
     * @param clientKey
     * @return
     */
    static public String getBarcodeForSKU(String skuCode, String clientKey) {
        String upc = null;
         String isbn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnectionManager().getConnection();

            String esql = "select ISNULL(upc_code,'') as upc,ISNULL(isbn_code,'') as isbn  from owd_inventory (NOLOCK) " +
                    "where inventory_num = ? "  +
                    "and client_fkey = ?";

            stmt = cxn.prepareStatement(esql);
            stmt.setString(1, skuCode);
            stmt.setInt(2, new Integer(clientKey));

            rs = stmt.executeQuery();

            if (rs.next()) {
                upc = rs.getString(1);
                isbn=rs.getString(2);
            }

            if("".equals(upc)) {
                if("".equals(isbn)) {
                    return "";
                }    else {
                    return isbn;
                }
            } else {
                return upc;
            }


        } catch (Exception ex) {


        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                ConnectionManager.freeConnection(cxn);
            } catch (Exception ex) {
            }
        }
        return "";
    }


    private LineItem(String id,
                     String orderID,
                     String inventory_id,
                     String client_partno,
                     String desc,
                     String clientreference,
                     String requested,
                     String actual,
                     String backordered,
                     String item_price,
                     String line_total,
                     String created_by,
                     String created_on,
                     String modified_by,
                     String modified_on,
                     String acolor,
                     String asize,
                     String acustoms_desc,
                     String adec_item_value,
                     String isParent,
                     String parentItem) {
        if (id != null) {
            line_item_id = id;
        }
        order_fkey = orderID;
        inventory_fkey = inventory_id;
        client_part_no = client_partno;
        description = desc;
        client_ref_num=clientreference;

        try {
            long tempID = Long.parseLong("0" + line_item_id);
            if (tempID < 0)
                line_item_id = "0";
        } catch (NumberFormatException ex) {
            line_item_id = "0";
        }
        try {
            long tempID = new Long("0" + order_fkey);
            if (tempID < 0)
                order_fkey = "0";
        } catch (NumberFormatException ex) {
            order_fkey = "0";
        }
        try {
            long tempID = new Long("0" + inventory_fkey).longValue();
            if (tempID < 0)
                inventory_fkey = "0";
        } catch (NumberFormatException ex) {
            inventory_fkey = "0";
        }

        try {
            quantity_request = new Long("0" + requested).longValue();
        } catch (NumberFormatException ex) {
            quantity_request = (long) 0;
        }
        try {
            quantity_actual = new Long("0" + actual).longValue();
        } catch (NumberFormatException ex) {
            quantity_actual = (long) 0;
        }
        try {
            quantity_backordered = new Long("0" + backordered).longValue();
        } catch (NumberFormatException ex) {
            quantity_backordered = (long) 0;
        }

        try {
            sku_price = (new Float("0" + item_price).floatValue());
        } catch (NumberFormatException ex) {
            sku_price = (float) 0.0;
        }
        try {
            total_price = (new Float("0" + line_total).floatValue());
        } catch (NumberFormatException ex) {
            total_price = (float) 0.0;
        }

        try {
            dec_item_value = (new Float("0" + adec_item_value).floatValue());
        } catch (NumberFormatException ex) {
            dec_item_value = sku_price > 0 ? sku_price : (float) (0.01);
        }

        try {
            parent_line = (new Integer(parentItem));
        } catch (NumberFormatException ex) {
            parent_line = null;
        }

        try {
            is_parent = (new Integer(isParent));
            if(is_parent.intValue()!=1) is_parent=new Integer(0);
        } catch (NumberFormatException ex) {
            is_parent=new Integer(0);
        }


        color = acolor;
        size = asize;
        createdBy = created_by;
        createdOn = created_on;
        modifiedBy = modified_by;
        modifiedOn = modified_on;
        customs_desc = acustoms_desc;
        if (customs_desc == null)
            customs_desc = "";
        if (customs_desc.length() < 1)
            customs_desc = "";
        //sanity check
        if (quantity_request < 0) quantity_request = 0;
        if (quantity_actual < 0) quantity_actual = 0;
        if (quantity_backordered < 0) quantity_backordered = 0;
        if (sku_price < 0) sku_price = 0;
        if (total_price < 0) total_price = 0;

        total_price = OWDUtilities.roundFloat(total_price);


        if (dec_item_value < 0) dec_item_value = (float) 0.00;

        float calcLinePrice = OWDUtilities.roundFloat(sku_price * (quantity_request + quantity_backordered));

        if (total_price < calcLinePrice && ((calcLinePrice - total_price) >= 0.01f)) {

            line_item_disc = OWDUtilities.roundFloat(calcLinePrice - total_price);
        }
    }

    public boolean updateQuantities(int quantityAvailable, String backorderRule) {
////////////log.debug("updating quantities, avail="+quantityAvailable+", back="+backorderRule);
        if (quantityAvailable < 0)
            quantityAvailable = 0;

        resetQuantities();

        if (quantityAvailable >= quantity_request) {
            quantity_actual = quantity_request;
            quantity_backordered = 0;
        } else { //is backorder
            if (backorderRule.equals(OrderXMLDoc.kPartialShip) || backorderRule.equals(OrderXMLDoc.kIgnoreBackOrder)) {
                quantity_actual = quantityAvailable;
                quantity_backordered = quantity_request - quantityAvailable;
                quantity_request = quantityAvailable;
            } else if (backorderRule.equals(OrderXMLDoc.kBackOrderAll)) {
                quantity_actual = 0;
                quantity_backordered = quantity_request;
                quantity_request = 0;
            } else if (backorderRule.equals(OrderXMLDoc.kRejectBackOrder)) {
                quantity_actual = 0;
                quantity_backordered = quantity_request;
                quantity_request = 0;
            }


            return true;


        }

        return false;

    }

    public void resetQuantities() {
        log.debug("reset quantities start: req="+quantity_request+", act="+quantity_actual+", back="+quantity_backordered);

        quantity_request = quantity_request + quantity_backordered;
        quantity_backordered = 0;
        quantity_actual = 0;
////////////log.debug("reset quantities result: req="+quantity_request+", act="+quantity_actual+", back="+quantity_backordered);
        log.debug("reset quantities result: req="+quantity_request+", act="+quantity_actual+", back="+quantity_backordered);

    }

    public void resetQuantity(int newq) {
        resetQuantities();
        quantity_request = newq;
        quantity_backordered = 0;
        quantity_actual = 0;
        float total = getLineTotal();
    }

    public void backorderAll() {
        quantity_backordered = quantity_backordered + quantity_request;
        quantity_request = 0;
        quantity_actual = 0;
    }

    public LineItem getLineItemForID(Connection cxn, String lineItemID) throws Exception {
        //load existing order
        return dbload(cxn, lineItemID);
    }


    public float getLineTotal() {

        float calcLinePrice = sku_price * (quantity_request + quantity_backordered);

        total_price = OWDUtilities.roundFloat(calcLinePrice - line_item_disc);

        return total_price;
    }

    public boolean isModified() {
        return needsUpdate;
    }

    public boolean isNew() {
        if ("0".equals(line_item_id))
            return true;

        return false;
    }

    public boolean isBackordered() {
        if (quantity_backordered > 0) return true;

        return false;
    }

 /*   public ElementNode toXml(XmlDocument doc) throws IOException {

        //return Line Item node
        ElementNode root = (ElementNode) doc.createElement(OrderXMLDoc.kLineItemTag);

        //attributes
        root.setAttribute(OrderXMLDoc.kItemIDTag, "" + line_item_id);
        root.setAttribute(OrderXMLDoc.kItemOrderIDTag, "" + order_fkey);
        root.setAttribute(OrderXMLDoc.kItemInventoryIDTag, "" + inventory_fkey);
        root.setAttribute(OrderXMLDoc.kItemCreatedOnTag, "" + createdOn);
        root.setAttribute(OrderXMLDoc.kItemCreatedByTag, "" + createdBy);
        root.setAttribute(OrderXMLDoc.kItemModifiedByTag, "" + modifiedBy);
        root.setAttribute(OrderXMLDoc.kItemModifiedOnTag, "" + modifiedOn);

        //elements
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemPartNumTag, "" + client_part_no));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemCostTag, "" + sku_price));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemCostTotalTag, "" + total_price));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemSizeTag, size));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemColorTag, color));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemDescTag, description));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemClientRefTag, "" + client_ref_num));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemRequestCountTag, "" + quantity_request));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemActualCountTag, "" + quantity_actual));
        root.appendChild(XMLUtils.getXMLTextNode(doc, OrderXMLDoc.kItemBackorderCountTag, "" + quantity_backordered));

        return root;

    }
*/
    public void dbsave(Connection cxn) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        createdBy = OWDUtilities.getCurrentUserName();
        createdOn = OWDUtilities.getSQLDateTimeForToday();
        modifiedOn = createdOn;
        modifiedBy = createdBy;

        if (quantity_request == 0 && quantity_actual == 0 && quantity_backordered == 0) {
            String msg = "all quantities for Line Item " + client_ref_num + " in order " + order_fkey + " are 0!\n";
            msg += "quantity_actual: " + quantity_actual + "\n";
            msg += "quantity_backordered: " + quantity_backordered + "\n";
            Exception e = new Exception("Internal conflict - could not allocate inventory.");
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            Mailer.postMailMessage("0 quantities requested", msg + "\n" + stackTrace.toString(), "casetracker@owd.com", "support@owd.com");
            throw e;
        }
        try {
            stmt = cxn.prepareStatement("insert into " + kdbLineItemTable + " ("
                    + kdbLineItemOrderFkey + ","
                    + kdbLineItemInventoryFkey + ","
                    + kdbLineItemClientPartID + ","
                    + kdbLineItemDescription + ","
                    + kdbLineItemCustomerReference + ","
                    + kdbLineItemRequested + ","
                    + kdbLineItemAllotted + ","
                    + kdbLineItemBackordered + ","
                    + kdbLineItemItemPrice + ","
                    + kdbLineItemLinePrice + ","
                    + kdbLineItemCreatedBy + ","
                    + kdbLineItemCreatedOn + ","
                    + kdbLineItemModifiedBy + ","
                    + kdbLineItemModifiedOn + ","
                    + kdbLineItemColor + ","
                    + kdbLineItemSize + ","
                    + kdbLineItemLineDiscount + ","
                    + kdbLineItemLongDesc + ","
                    + kdbLineItemCustomsDesc + ","
                    + kdbLineItemDeclaredValue +  ","
                    + kdbLineItemIsParent +  ","
                    + kdbLineItemParentLineID + ","
                    + kdbInsertItem + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, new Integer(order_fkey).intValue());
            stmt.setInt(2, new Integer(inventory_fkey).intValue());
            stmt.setString(3, OrderUtilities.limitStr(253, client_part_no));
            stmt.setString(4, OrderUtilities.limitStr(253, description));
            stmt.setString(5, OrderUtilities.limitStr(30, client_ref_num));
            stmt.setInt(6, new Integer((int) quantity_request).intValue());
            stmt.setInt(7, new Integer((int) quantity_actual).intValue());
            stmt.setInt(8, new Integer((int) quantity_backordered).intValue());
            stmt.setFloat(9, sku_price);
            stmt.setFloat(10, total_price);
            stmt.setString(11, createdBy);
            stmt.setString(12, createdOn);
            stmt.setString(13, modifiedBy);
            stmt.setString(14, modifiedOn);
            stmt.setString(15, OrderUtilities.limitStr(50, color));
            stmt.setString(16, OrderUtilities.limitStr(50, size));
            stmt.setFloat(17, line_item_disc);
            stmt.setString(18, long_desc);
            stmt.setString(19, customs_desc);
            stmt.setFloat(20, dec_item_value);
            if(is_parent==null)
            {
                stmt.setNull(21, Types.INTEGER);
            }                  else
            {
                stmt.setInt(21, (is_parent).intValue());
            }
             if(parent_line==null)
            {
                stmt.setNull(22, Types.INTEGER);
            }                  else
            {
                stmt.setInt(22, (parent_line).intValue());
            }

            stmt.setInt(23, insertedItem?1:0);

            log.debug("saving line item for "+OrderUtilities.limitStr(253, client_part_no));
            int rowsUpdated = stmt.executeUpdate();

            log.debug("rows updated "+rowsUpdated);
            if (rowsUpdated < 1)
                throw new Exception("Could not save line item!");


            log.debug("getting generatedKeys identity");

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                log.debug("Id=" + id);
                line_item_id=(""+id);
            }


        /*    rs = identityStmt.getResultSet();
            log.debug("getting scope identity");
            if (rs.next()) {
                line_item_id = rs.getString(1);
                log.debug("got scope identity: "+line_item_id);
            }*/
            rs.close();
          //  identityStmt.close();
            stmt.close();

            //dbupdate(cxn);
        } catch (Exception ex) {
            line_item_id = "0";
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
                stmt = cxn.prepareStatement(kDeleteItemStatement);
                stmt.setString(1, line_item_id);

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated < 1) throw new Exception("Cannot delete line item ID " + line_item_id);

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
        if (quantity_request == 0 && quantity_actual == 0 && quantity_backordered == 0) {
            String msg = "all quantities for Line Item " + client_part_no + " in order " + order_fkey + " are 0!\n";
            msg += "quantity_actual: " + quantity_actual + "\n";
            msg += "quantity_backordered: " + quantity_backordered + "\n";
            Exception e = new Exception("Internal conflict - could not allocate inventory.");
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            Mailer.postMailMessage("0 quantities update requested", msg + "\n" + stackTrace.toString(), "casetracker@owd.com", "support@owd.com");
            throw e;
        }
        if (isNew()) {
            dbsave(cxn);
        } else {


            PreparedStatement stmt = null;
            modifiedBy = OWDUtilities.getCurrentUserName();
            modifiedOn = OWDUtilities.getSQLDateTimeForToday();

            String isql = "update " + kdbLineItemTable
                    + " WITH (ROWLOCK) set \n"
                    + kdbLineItemInventoryFkey + "\n = ? ,\n"
                    + kdbLineItemClientPartID + "=?,\n"
                    + kdbLineItemDescription + "=?,\n"
                    + kdbLineItemCustomerReference + "=?,\n"
                    + kdbLineItemRequested + "=?,\n"
                    + kdbLineItemAllotted + "=?,\n"
                    + kdbLineItemBackordered + "=?,\n"
                    + kdbLineItemItemPrice + "=?,\n"
                    + kdbLineItemLinePrice + "=?,\n"
                    + kdbLineItemCreatedBy + "=?,\n"
                    + kdbLineItemCreatedOn + "=?,\n"
                    + kdbLineItemModifiedBy + "=?,\n"
                    + kdbLineItemModifiedOn + "=?,\n"
                    + kdbLineItemColor + "=?,\n"
                    + kdbLineItemSize + "=?,\n"
                    + kdbLineItemLineDiscount + "=?,\n"
                    + kdbLineItemCustomsDesc + "=?,\n"
                    + kdbLineItemDeclaredValue + "=?,\n"
                    + kdbLineItemIsParent + "=?,\n"
                    + kdbLineItemParentLineID + "=? WHERE " + kdbLineItemPKName + " = ?";
            try {


                stmt = cxn.prepareStatement(isql);

                stmt.setInt(1, new Integer(inventory_fkey).intValue());
                stmt.setString(2, OrderUtilities.limitStr(253, client_part_no));
                stmt.setString(3, OrderUtilities.limitStr(253, description));
                stmt.setString(4, OrderUtilities.limitStr(30, client_ref_num));
                stmt.setInt(5, new Integer((int) quantity_request).intValue());
                stmt.setInt(6, new Integer((int) quantity_actual).intValue());
                stmt.setInt(7, new Integer((int) quantity_backordered).intValue());
                stmt.setFloat(8, sku_price);
                stmt.setFloat(9, total_price);
                stmt.setString(10, createdBy);
                stmt.setString(11, createdOn);
                stmt.setString(12, modifiedBy);
                stmt.setString(13, modifiedOn);
                stmt.setString(14, OrderUtilities.limitStr(50, color));
                stmt.setString(15, OrderUtilities.limitStr(50, size));
                stmt.setFloat(16, line_item_disc);
                stmt.setString(17, customs_desc);
                stmt.setFloat(18, dec_item_value);
                if(is_parent==null)
                {
                    stmt.setNull(19,Types.INTEGER);
                }   else
                {
                   stmt.setInt(19, is_parent.intValue());
                }
                 if(parent_line==null)
            {
                stmt.setNull(20, Types.INTEGER);
            }                  else
            {
                stmt.setInt(20, (parent_line).intValue());
            }
                stmt.setInt(21, new Integer(line_item_id).intValue());


                int rowsUpdated = stmt.executeUpdate();

              //  if (rowsUpdated < 1)
              //      throw new Exception("Could not update line item (no rows updated) for id " + line_item_id);

            } catch (Exception ex) {

                ex.printStackTrace();
                throw ex;

            } finally {
                try {
                    stmt.close();
                } catch (Exception exc) {
                }

            }

        }

    }

    private static LineItem dbload(Connection cxn, String id) throws Exception {

        Statement stmt = null;
        ResultSet rs = null;
        LineItem item = null;

        try {
            stmt = cxn.createStatement();
            String isql = "select * from " + kdbLineItemTable + " WITH (NOLOCK) where " + kdbLineItemPKName + " = " + id;

            boolean hasResultSet = stmt.execute(isql);

            if (hasResultSet) {
                rs = stmt.getResultSet();

                if (rs.next()) {
                    item = new LineItem(rs.getString(kdbLineItemPKName),
                            rs.getString(kdbLineItemOrderFkey),
                            rs.getString(kdbLineItemInventoryFkey),
                            rs.getString(kdbLineItemClientPartID),
                            rs.getString(kdbLineItemDescription),
                            rs.getString(kdbLineItemCustomerReference),
                            rs.getString(kdbLineItemRequested),
                            rs.getString(kdbLineItemAllotted),
                            rs.getString(kdbLineItemBackordered),
                            rs.getString(kdbLineItemItemPrice),
                            rs.getString(kdbLineItemLinePrice),
                            rs.getString(kdbLineItemCreatedBy),
                            rs.getString(kdbLineItemCreatedOn),
                            rs.getString(kdbLineItemModifiedBy),
                            rs.getString(kdbLineItemModifiedOn),
                            rs.getString(kdbLineItemColor),
                            rs.getString(kdbLineItemSize),
                            rs.getString(kdbLineItemCustomsDesc),
                            rs.getString(kdbLineItemDeclaredValue),
                            rs.getString(kdbLineItemIsParent),
                            rs.getString(kdbLineItemParentLineID));

                    if(rs.getInt(kdbInsertItem)==1)
                    {
                        item.insertedItem = true;
                    }


                } else {
                    throw new Exception("Line item id " + id + " not found!");
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

        return item;
    }

    public static String getCleanSKU(String sku) {
        if (sku == null)
            return "";

        String part = sku.trim();

        if (part.startsWith("-")) {
            part = part.substring(1);
            while (!part.startsWith("-") && part.length() > 1) {
                part = part.substring(1);
            }
            if (part.startsWith("-")) {
                if (part.length() > 1)
                    part = part.substring(1);
                else
                    part = "";
            } else {
                part = sku.trim();
            }
        }

        return part.trim();

    }

    /**
     *
     * @param cxn
     * @param orderID
     * @return Map of Integer line item ids as keys and List of serial number values for line as the value. Map is empty if no serials found.
     * @throws Exception
     */
     public static Map getItemSerialMapForOrder(Connection cxn, String orderID) throws Exception {
        Map items = new HashMap();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = cxn.createStatement();
            String isql = "select line_item_id,serial_number from owd_line_item (NOLOCK) join owd_inventory_serial s (NOLOCK) \n" +
                    "join owd_line_serial_link (NOLOCK) on s.id=serial_fkey on line_item_id=line_fkey \n" +
                    "where order_fkey=" + orderID;

            boolean hasResultSet = stmt.execute(isql);

            int lastID=0;

            if (hasResultSet) {
                rs = stmt.getResultSet();

                  while (rs.next()) {
                if(lastID==rs.getInt(1))
                {
                    ((List)(items.get(new Integer(rs.getInt(1))))).add(rs.getString(2));
                }
                else
                {
                    lastID=rs.getInt(1);
                    List serialList = new ArrayList();
                    serialList.add(rs.getString(2))       ;

                    items.put(new Integer(rs.getInt(1)), serialList);

                }

                }

                rs.close();

            }

            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
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

    public static java.util.Vector getItemsForOrder(Connection cxn, String orderID) throws Exception {
        java.util.Vector items = new java.util.Vector();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = cxn.createStatement();
            String isql = "select * from " + kdbLineItemTable + " (NOLOCK) where order_fkey = " + orderID;

            boolean hasResultSet = stmt.execute(isql);

            if (hasResultSet) {
                rs = stmt.getResultSet();

                while (rs.next()) {

                            LineItem item = new LineItem(rs.getString(kdbLineItemPKName),
                            rs.getString(kdbLineItemOrderFkey),
                            rs.getString(kdbLineItemInventoryFkey),
                            rs.getString(kdbLineItemClientPartID),
                            rs.getString(kdbLineItemDescription),
                            rs.getString(kdbLineItemCustomerReference),
                            rs.getString(kdbLineItemRequested),
                            rs.getString(kdbLineItemAllotted),
                            rs.getString(kdbLineItemBackordered),
                            rs.getString(kdbLineItemItemPrice),
                            rs.getString(kdbLineItemLinePrice),
                            rs.getString(kdbLineItemCreatedBy),
                            rs.getString(kdbLineItemCreatedOn),
                            rs.getString(kdbLineItemModifiedBy),
                            rs.getString(kdbLineItemModifiedOn),
                            rs.getString(kdbLineItemColor),
                            rs.getString(kdbLineItemSize),
                            rs.getString(kdbLineItemCustomsDesc),
                            rs.getString(kdbLineItemDeclaredValue),
                            rs.getString(kdbLineItemIsParent),
                            rs.getString(kdbLineItemParentLineID));
                    
                             if(rs.getInt(kdbInsertItem)==1)
                    {
                        item.insertedItem = true;
                    }

                        items.addElement(item);
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

    public static String getSKUForDesc(String desc, String clientID) {

        ResultSet rs = null;

        try {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select distinct inventory_num from owd_inventory (NOLOCK) " +
                    " where description = ? and client_fkey = ?");

             ps.setString(1,desc);
            ps.setInt(2,new Integer(clientID));


            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);

            }


        } catch (Exception ex) {


        }
        try {
            rs.close();
        } catch (Exception ex) {
        }
        try {
           HibernateSession.closePreparedStatement();
        } catch (Exception ex) {
        }
        try {
            HibernateSession.closeSession();
        } catch (Exception ex) {
        }

        return "";

    }

    public void saveCustomValues(Connection cxn) throws Exception {
        PreparedStatement stmt = null;

        try {

            for (String key : tagMap.keySet()) {
                stmt = cxn.prepareStatement("insert into owd_tags (type,external_id,name,value) VALUES (?,?,?,?)");

                stmt.setString(1, "LINE_ITEM");
                stmt.setInt(2, Integer.parseInt(line_item_id));
                stmt.setString(3, OrderUtilities.limitStr(254, key));
                stmt.setString(4, OrderUtilities.limitStr(640000, tagMap.get(key)));

                stmt.executeUpdate();

            }
        } catch (Exception ex) {
            throw ex;

        } finally {

            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }
    }

    public void addTag(String name, String value) throws Exception {
        if (name==null){
            throw new Exception("Custom value name cannot be null");
        }
        if (value==null){
            throw new Exception("Custom value contents cannot be null");
        }
        if (name.getBytes().length>254) {
            throw new Exception("Custom value name must be 254 characters or less");
        }
        if (value.getBytes().length>640000) {
            throw new Exception("Custom value contents must be 640000 characters or less");
        }

        Scanner scanner = new Scanner(name);
        String validationResult = scanner.findInLine("[^A-Za-z0-9_\\-\\.]+");

        if (validationResult != null) {
            // Invalid character found.
            throw new Exception("Invalid character found in custom value name: " + validationResult);
        }
        if(value.trim().getBytes().length<1){throw new Exception("Custom value contents must contain at least one non-whitespace character");}

        tagMap.put(name.toUpperCase(),value);
    }

    //not tested
    public static Vector getSKUsForDesc(String desc, String clientID, String start, String end) {
        String getSKU = "select distinct inventory_num from owd_inventory (NOLOCK) where description like \'" +
                start + "\'" + "%" + "\'" + end + "\' and " + "client_fkey = \'" + clientID + "\'";

        ResultSet rs = null;
        Statement stmt = null;
        Connection cxn = null;
        Vector skus = new Vector();

        try {
            cxn = ConnectionManager.getConnection();

            stmt = cxn.createStatement();


            boolean hasResultSet = stmt.execute(getSKU);

            rs = stmt.getResultSet();

            if (rs.next()) {
                skus.addElement(rs.getString(1));

            }


        } catch (Exception ex) {


        }
        try {
            rs.close();
        } catch (Exception ex) {
        }
        try {
            stmt.close();
        } catch (Exception ex) {
        }
        try {
            ConnectionManager.freeConnection(cxn);
        } catch (Exception ex) {
        }

        return skus;

    }

}

package com.owd.core.business.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by stewartbuskirk1 on 9/24/14.
 */
public class OldStockUtilities {
private final static Logger log =  LogManager.getLogger();
    
/*
    //todo remove
       public static Map updateLineItemsForAvailability(Connection cxn, Vector lineitems, String backorderRule, boolean allowBackorders) throws Exception {
       return updateLineItemsForAvailability(cxn, lineitems, backorderRule, allowBackorders, false);
   }//todo updatelineitemsmethod

    //todo remove
   public static Map updateLineItemsForAvailability(Connection cxn, Vector lineitems, String backorderRule, boolean allowBackorders, boolean isInternalScan) throws Exception {
       Map itemStockMap = new TreeMap();

       //check for all virtual items
       boolean isAllVirtual = true;

       int clientID = 0;
            // Map<String, Integer> reserveBORMap = new TreeMap<String, Integer>();
            // boolean releaseBORReserved = false;


       for (int k = 0; k < lineitems.size(); k++) {
           //update line item quantities
           Inventory item = ((LineItem) lineitems.elementAt(k)).getInventory();

           clientID = item.client_fkey;
           if (item.is_auto_inventory == 0) {
                   isAllVirtual = false;

              // k = lineitems.size();
           } else
           {
              if(clientID == 463 && ((item.notes.startsWith("DROP"))))
               {
                   isAllVirtual = false;
               }
           }
//             if (item.inventory_num.equals("10023")) {
//                releaseBORReserved = true;
//            }

       }
       log.debug("order is all virtual:" + isAllVirtual);
       log.debug("order is internal scan:" + isInternalScan);

       //first, update stock map with current counts and availability
       for (int k = 0; k < lineitems.size(); k++) {
           //update line item quantities
           LineItem item = (LineItem) lineitems.elementAt(k);
           long boQuantity = 0;

           if (item.force_backorder_quantity) {
               boQuantity = item.quantity_backordered;
               //log.debug("Setting bo forced quantity to "+ boQuantity+" for "+item.client_part_no);
           }

           item.resetQuantities();
           Integer key = new Integer(item.inventory_fkey);
           //    //log.debug("key="+key);
           if (itemStockMap.get(key) == null) {
               long[] stockArray = new long[9];
               //index 0 is initial availability
               //index 1 is total requested for this release
               //index 2 is total assigned so far in this run
               //index 3 is BO quantity
               //index 4 is total amount of the original request pre-allocated during kit review
               //index 5 is flag to indicate that the item is a bundle/kit
               //index 6 is allocated shipping quantity of request (to use when assigning total amount to line items)
               //index 7 is allocated backorder quantity of request (to use when assigning total amount to line items)
               //index 8 is quantity asked to force for backorder amount (supports OrderMotion indications of pending items sent through API)
               //  //log.debug("adding "+key);
               stockArray[0] = isAllVirtual ? 0 : getAvailableInventory(cxn, item.inventory_fkey, isInternalScan);

*/
/*
               if (stockArray[0] > 0 && reserveBORMap.containsKey(item.client_part_no) && !releaseBORReserved) {
                                   stockArray[0] = stockArray[0] - reserveBORMap.get(item.client_part_no);
                                   if (stockArray[0] < 0) {
                                       stockArray[0] = 0;
                                   }
                               }*//*


               stockArray[1] = item.quantity_request;
               stockArray[2] = 0;
               stockArray[3] = 0;
               stockArray[4] = 0;
               stockArray[5] = 0;
               stockArray[6] = 0;
               stockArray[7] = 0;
               stockArray[8] = boQuantity;


               itemStockMap.put(key, stockArray);
           } else {
               // //log.debug("updating key "+key);
               long[] stockArray = (long[]) itemStockMap.get(key);
               stockArray[1] += item.quantity_request;
               stockArray[8] += boQuantity;
           }


       }

       //log.debug("***START MAP***");
       Iterator it = itemStockMap.keySet().iterator();
       while (it.hasNext()) {
           //check kit restrictions
           Integer iid = (Integer) it.next();
           long[] stockArray = (long[]) itemStockMap.get(iid);
          // log.debug(Inventory.getInventoryForID("" + iid).inventory_num + ":" + stockArray[0]
          //         + ":" + stockArray[1] + ":" + stockArray[2] + ":" + stockArray[3] + ":" + stockArray[4] + ":" + stockArray[5] + ":" + stockArray[6] + ":" + stockArray[7] + ":" + stockArray[8]);
       }


       OrderUtilities.updateLineItemAvailabilityArray(itemStockMap, backorderRule);

       //log.debug("***FINAL MAP***");
       it = itemStockMap.keySet().iterator();
       while (it.hasNext()) {
           //check kit restrictions
           Integer iid = (Integer) it.next();
           long[] stockArray = (long[]) itemStockMap.get(iid);
           //log.debug(Inventory.getInventoryForID(""+iid).inventory_num+":"+stockArray[0]
           //        +":"+stockArray[1]+":"+stockArray[2]+":"+stockArray[3]+":"+stockArray[4]+":"+stockArray[5]+":"+stockArray[6]+":"+stockArray[7]+":"+stockArray[8]);
       }

       //log.debug("allocating kit lines");
       //update total by-line quantities - kits first!
       for (int k = 0; k < lineitems.size(); k++) {
           //update line item quantities
           LineItem item = (LineItem) lineitems.elementAt(k);
           if (item.is_parent.intValue() == 1) {
               OrderUtilities.updateLineItemFinalQuantities(itemStockMap, item);
               Map reqItemMap = LineItem.getRequiredItemsForInventoryID(new Integer(item.inventory_fkey));
               if (reqItemMap.size() == 0)
                   throw new Exception("Invalid kit definition - " + item.client_part_no + " has no kit items defined!");
               for (int k2 = 0; k2 < lineitems.size(); k2++) {
                   //update line item quantities of kit components
                   LineItem item2 = (LineItem) lineitems.elementAt(k2);
                   if (item2.parent_line != null) {
                       if (reqItemMap.get(new Integer(item2.inventory_fkey)) != null && (item2.parent_line.intValue() == item.tempID || item2.parent_line.intValue() == new Integer(item.line_item_id).intValue())) {
                           long maxToAllocateOfKitComponent = item.quantity_actual * ((Integer) reqItemMap.get(new Integer(item2.inventory_fkey))).intValue();
                           OrderUtilities.updateLineItemFinalQuantities(itemStockMap, item2, maxToAllocateOfKitComponent);
                       }
                   }
               }


           }
       }

       //log.debug("allocating non-kit lines");
       //update total by-line quantities - non-kits second!
       for (int k = 0; k < lineitems.size(); k++) {
           //update line item quantities
           LineItem item = (LineItem) lineitems.elementAt(k);
           if (item.parent_line == null && item.is_parent.intValue() == 0) {
               OrderUtilities.updateLineItemFinalQuantities(itemStockMap, item);
           }
       }

       //check for unreleasable orders
       boolean releaseable = false;
       for (int k = 0; k < lineitems.size(); k++) {
           //update line item quantities
           LineItem item = (LineItem) lineitems.elementAt(k);
           if (item.quantity_actual > 0 && !item.insertedItem) {
               k = lineitems.size();
               releaseable = true;
           }
       }
       if (!releaseable) {
           for (int k = 0; k < lineitems.size(); k++) {
               //update line item quantities
               LineItem item = (LineItem) lineitems.elementAt(k);
                if(item.insertedItem && item.quantity_actual>0)
           {
               item.resetQuantities();
               item.quantity_backordered=item.quantity_request;
               item.quantity_request=0;

           }
               if (item.quantity_actual == 0 && item.quantity_backordered == 0 && item.quantity_request == 0) {
                   Exception e = new Exception("Zero quantities problem for SKU: " + item.client_part_no);
                   StringWriter s = new StringWriter();
                   e.printStackTrace(new PrintWriter(s, true));
                   Vector v = new Vector();
                   v.add("servicerequests@owd.com");
                   Mailer.postMailMessage("Zero Inventory problem", "sku:" + item.client_part_no + "\n" + s.toString(), v, "support@owd.com");
                   throw e;


               }
           }
       }
       return itemStockMap;

   }

    public static int getAvailableKitInventory(Connection cxn, String inventoryID, boolean isInternalScan) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {

            String esql = "select  min(qty_on_hand/req.req_inventory_quant), max(ISNULL(is_scan_for_release,0)),min(ISNULL(can_ship_if_instock,1))  from owd_inventory_oh oh (NOLOCK) \n" +
                    "                    join owd_inventory i (NOLOCK) " +
                    "                     left outer join owd_inventory_dropshipinfo ds (NOLOCK) on ds.inventory_fkey=i.inventory_id \n" +
                    " join owd_inventory_required_skus req (NOLOCK) on req.req_inventory_fkey=inventory_id \n" +
                    "                    on i.inventory_id=oh.inventory_fkey \n" +
                    "                    where req.inventory_fkey = ? ";

            stmt = cxn.prepareStatement(esql);

            stmt.setInt(1, new Integer(inventoryID).intValue());

            rs = stmt.executeQuery();

            if (rs != null) {


                if (rs.next()) {
                    count = rs.getInt(1);
                    if (count < 0) count = 0;

                    //set scan-only SKUs to zero for all queries
                    //overrides auto-inventory, above
                    if (rs.getInt(2) == 1 && !isInternalScan) {
                        count = 0;
                    }

                     if (rs.getInt(3) == 0 && !isInternalScan) {
                         log.debug("DROPSHIP ZEROED");
                        count = 0;
                    }
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
            return count;
        }

    }

    public static int getAvailableInventory(Connection cxn, String inventoryID, boolean isInternalScan) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {

            String esql = "select  qty_on_hand, ISNULL(is_scan_for_release,0), ISNULL(is_auto_inventory,0),ISNULL(can_ship_if_instock,1) from owd_inventory_oh h (ROWLOCK) \n" +
                    "                    join owd_inventory i (NOLOCK) on i.inventory_id=h.inventory_fkey\n" +
                    "                     left outer join owd_inventory_dropshipinfo ds (NOLOCK) on ds.inventory_fkey=h.inventory_fkey \n" +
                    "                    where h.inventory_fkey =" + inventoryID;

            stmt = cxn.createStatement();

            rs = stmt.executeQuery(esql);

            if (rs != null) {


                if (rs.next()) {
                    count = rs.getInt(1);
                    if (count < 0) count = 0;

                    //set auto-inventory SKUs to 99999 for all queries
                    if (rs.getInt(3) == 1) {
                        count = 99999;
                    }
                    //set scan-only SKUs to zero for all queries
                    //overrides auto-inventory, above
                    if (rs.getInt(2) == 1 && !isInternalScan) {
                        count = 0;
                    }

                    if (rs.getInt(4) == 0 && !isInternalScan) {
                         log.debug("DROPSHIP ZEROED");
                        count = 0;
                    }
                } else
                {

                         log.debug("NO INVENTORY QTY FOUND - ZEROED");
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
            return count;
        }

    }

    public static int getAvailableKitInventory(String inventoryID) throws Exception {
        int result = 0;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();
            result = getAvailableKitInventory(cxn, inventoryID);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }

        return result;
    }

    public static int getAvailableInventory(String inventoryID) throws Exception {
        int result = 0;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();
            result = getAvailableInventory(cxn, inventoryID);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }

        return result;
    }

    public static int getAvailableInventory(Connection cxn, String inventoryID
    ) throws Exception {
        return getAvailableInventory(cxn, inventoryID, false);
    }

    public static int getAvailableKitInventory(Connection cxn, String inventoryID
    ) throws Exception {
        return getAvailableKitInventory(cxn, inventoryID, false);
    }

*/

}

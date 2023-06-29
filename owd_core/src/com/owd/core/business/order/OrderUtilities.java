package com.owd.core.business.order;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.*;
import com.owd.core.business.order.distributedOrderManagement.DOMUtilities;
import com.owd.core.business.order.distributedOrderManagement.Model.domFillable;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.ManifestingManager;
import com.owd.core.xml.OrderXMLDoc;
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
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.Exchanger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 16, 2008
 * Time: 1:34:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderUtilities {
private final static Logger log =  LogManager.getLogger();
    public static final String kRequirePayment = "Require Payment";
    public static final String kHoldForPayment = "Hold For Payment";
    public static final int kStandardReport = 0;
    public static final int kYahooReport = 1;
    public static final int kOrderTrustReport = 2;
    public static final int kCustomReport = 3;

    public static String kupdateThirdPartyAccountInfoStatement = "UPDATE owd_order_ship_info " +
            "SET third_party_refnum =?" +
            "WHERE order_fkey=?";

    public static String kupdateThirdPartyContactInfoStatement = "UPDATE order_ship_info2 " +
            "SET shipper_name =?," +
            "shipper_company =?," +
            "shipper_address_one =?," +
            "shipper_address_two =?," +
            "shipper_city =?," +
            "shipper_state =?," +
            "shipper_zip =?," +
            "shipper_phone =?," +
            "WHERE order_fkey=?";

    public static String kCreateSecondShipInfoStatement = "insert into order_ship_info2 (" +
            "order_fkey," +
            "shipper_name," +
            "shipper_company," +
            "shipper_address_one," +
            "shipper_address_two," +
            "shipper_city," +
            "shipper_state," +
            "shipper_zip," +
            "shipper_phone," +
            "package_ref,bway_text,tax_account,\n" +
            "tax_contact,\n" +
            "tax_company,\n" +
            "tax_address_one,\n" +
            "tax_address_two,\n" +
            "tax_city,\n" +
            "tax_state,\n" +
            "tax_zip,\n" +
            "tax_phone,\n" +
            "importer_of_record, company_name_override, name_override, phone_override, address1_override, address2_override, city_override, state_override, zip_override,external_api_key) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static String kCreateOrderStatement = "insert into owd_order (" +
            "client_fkey " +
            ",customer_fkey " +
            ",created_by,created_date,modified_by,modified_date" +
            ",order_num_barcode " +
            ",po_num " +
            ",order_type " +
            ",salesperson " +
            ",actual_order_date " +
            ",order_sub_total " +
            ",discount " +
            ",tax_pct " +
            ",tax_amount " +
            ",ship_handling_fee " +
            ",gift_wrap_fee " +
            ",order_total " +
            ",paid_amount " +
            ",paid_date " +
            ",order_balance " +
            ",customer_num " +
            ",customer_vendor_no " +
            ",bill_last_name " +
            ",bill_first_name " +
            ",bill_address_one " +
            ",bill_address_two " +
            ",bill_city " +
            ",bill_state " +
            ",bill_zip " +
            ",bill_country " +
            ",bill_company_name " +
            ",bill_title " +
            ",bill_phone_num " +
            ",bill_fax_num " +
            ",bill_email_address " +
            ",prt_pick_reqd " +
            ",prt_pack_reqd " +
            ",prt_invoice_reqd " +
            ",prt_priceonslip_reqd " +
            ",is_backorder " +
            ",backorder_order_num " +
            ",is_future_ship " +
            ",is_void " +
            ",original_order_num " +
            ",is_gift " +
            ",gift_message " +
            ",cc_type, post_date,cc_num,cc_exp_mon,cc_exp_year,payment_status " +
            ",order_num " +
            ",order_refnum " +
            ",coupon,discount_pct,group_name,packing_instructions,facility_code,facility_policy,no_customs_account,business_order ) VALUES (?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String kInsertCustomerStatement = "insert into owd_customer (customer_num, " +
            "vendor_no, " +
            "bill_last_name, " +
            "bill_first_name, " +
            "bill_address_one, " +
            "bill_address_two, " +
            "bill_city, " +
            "bill_state, " +
            "bill_zip, " +
            "bill_country, " +
            "bill_company_name, " +
            "bill_title, " +
            "bill_phone_num, " +
            "bill_fax_num, " +
            "bill_email_address, " +
            "ship_last_name, " +
            "ship_first_name, " +
            "ship_address_one, " +
            "ship_address_two, " +
            "ship_city, " +
            "ship_state, " +
            "ship_zip, " +
            "ship_country, " +
            "ship_company_name, " +
            "ship_title, " +
            "ship_phone_num, " +
            "ship_fax_num, " +
            "ship_email_address) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String getCompleteAddressAsString(Contact contact, Address addr, String lineBreakChars) {
        StringBuffer sb = new StringBuffer();
        if (contact.getName().length() > 0) {
            sb.append(contact.getName() + lineBreakChars);
        }
        if (addr.company_name.length() > 0) {
            sb.append(addr.company_name + lineBreakChars);
        }
        if (addr.address_one.length() > 0) {
            sb.append(addr.address_one + lineBreakChars);
        }
        if (addr.address_two.length() > 0) {
            sb.append(addr.address_two + lineBreakChars);
        }
        sb.append(addr.city + ", " + addr.state + " " + addr.zip + lineBreakChars);
        sb.append(addr.country + lineBreakChars);

        return sb.toString();

    }


     public static void repostOrder(Integer id) throws Exception {

        OrderStatus status = new OrderStatus(id + "");
         if(status.is_shipped)
         {
             throw new Exception("Cannot repost shipped order!");
         }
        log.debug("reposting order "+id+" in repostOrder");
        try {

            if (!status.is_unpicked) {
                status.unpickOrder();
                Event.addOrderEvent(id, Event.kEventTypeHandling, "Pick cleared as part of unposting process", "System Repost");
            } else {
                // throw new Exception("Order is not currently in posted status; cannot unpost this order.");
            }
           }
        catch (Exception exx) {
            exx.printStackTrace();
        } finally {

        }
               try {
            if (status.is_posted) {
                status.unpostOrder();
                Event.addOrderEvent(id, Event.kEventTypeHandling, "Order unposted after posting", "System Repost");
            } else {
                throw new Exception("Order is not currently in posted status; cannot unpost this order.");
            }
        }
        catch (Exception exx) {
            exx.printStackTrace();
        } finally {

        }

           try{
            Query q = HibernateSession.currentSession().createSQLQuery("delete from owd_print_queue3 where order_id="+id);

                q.executeUpdate();
               HibUtils.commit(HibernateSession.currentSession());
          }catch(Exception e){

              e.printStackTrace();
          }

            try {
                status = new OrderStatus(id + "");
                Map skuMap = updateLineItemsForAvailability( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), status.items, OrderXMLDoc.kPartialShip, true, FacilitiesManager.getFacilityForCode(status.shipLocation).getId() );

                Date posted = OWDUtilities.getDateForSQLDateString(OWDUtilities.getSQLDateTimeForAdjustedDate(-1));

                OrderUtilities.shipExistingOrder(status,posted);
              //  Event.addOrderEvent(id, Event.kEventTypeHandling, "Order unposted after posting", "System Repost");

        }
        catch (Exception exx) {
            exx.printStackTrace();
        } finally {

        }


    }
    public static String limitStr(int limit, String fromStr) {
        if (fromStr == null)
            fromStr = "";
        fromStr = fromStr.trim();
        if (fromStr.length() <= limit)
            return fromStr;
        else
            return fromStr.substring(0, limit);

    }

 
    public static  Order cloneOrder(OrderStatus oldOrderStatus, boolean copyLineItems) throws Exception {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(oldOrderStatus.order_id));

        return new Order(order,copyLineItems);
    }

    public static owdChoiceList getServiceList() throws Exception {
        return ChoiceListManager.getChoiceListManager().getList("Service");
    }

    public static owdChoiceList getTermsList() throws Exception {
        return ChoiceListManager.getChoiceListManager().getList("Terms");
    }

    public static owdChoiceList getCountryList() throws Exception {
        return ChoiceListManager.getChoiceListManager().getList("Country ID");
    }

    public static owdChoiceList getIntlTaxAndDutyList() throws Exception {
        return ChoiceListManager.getChoiceListManager().getList("Intl tax and duty");
    }

   public static void main(String[] args) {
       try {

            int orderId = 16742559;
         log.debug(getMethodUsedForFlatRate(orderId));
          /* OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));
           log.debug(order.getPackagesShipped());
           Query ou = HibernateSession.currentSession().createSQLQuery("update owd_order set ship_packs = :packs where order_id = :orderId");
           ou.setParameter("packs",2);
           ou.setParameter("orderId",orderId);
           ou.executeUpdate();
           HibUtils.commit(HibernateSession.currentSession());
           HibernateSession.currentSession().flush();
           HibernateSession.currentSession().refresh(order);
           log.debug(order.getPackagesShipped());

           OwdOrder order2 = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(orderId), 55, true);
           log.debug(order2.getPackagesShipped());*/


        //   mergeItemPackingInstructionsToOrder(8441922);
         //  mergeItemPackingInstructionsToOrder(8443381);
         //  mergeItemPackingInstructionsToOrder(8444219);
         //  mergeItemPackingInstructionsToOrder(8444437);
        //   mergeItemPackingInstructionsToOrder(8445649);
        //   System.out.println(checkLTLAndUpdateTrackingAndPackages("11316933"));
         //  System.out.println(getServiceList().getRefForValue("UPS Worldwide Expedited"));
                 /*  OrderStatus calltag = new OrderStatus("4790284");
           log.debug("calltag order status: "+calltag.getStatusString());

           log.debug("shipping call tag order");

           OrderStatus status = CallTagManager.shipCallTagOrder(calltag, 1.25f, "8978445") ;
*/
       } catch (Exception ex) {
           ex.printStackTrace();
       } finally {
           // HibernateSession.closeSession();
       }

   }



    public static Map updateLineItemsForAvailability(Connection cxn, Vector lineitems, String backorderRule, boolean allowBackorders, int facilityId) throws Exception {
        return updateLineItemsForAvailability(cxn, lineitems, backorderRule, allowBackorders, false,facilityId);
    }//todo updatelineitemsmethod



    public static Map updateLineItemsForAvailability(Connection cxn, Vector lineitems, String backorderRule, boolean allowBackorders, boolean isInternalScan, int facilityId) throws Exception {
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

        /* if (clientID == 160) {
            PreparedStatement sb = null;
            ResultSet rsb = null;

            try {
                          sb = cxn.prepareStatement("select inventory_num,reserve from bor_reserve");
                          rsb = sb.executeQuery();
                          while (rsb.next()) {
                              reserveBORMap.put(rsb.getString(1), rsb.getInt(2));
                          }

                          rsb.close();
                          sb.close();
                      } catch (Exception exbor) {

                      } finally {
                          try {
                              rsb.close();
                          } catch (Exception exb1) {
                          }
                          try {
                              sb.close();
                          } catch (Exception exb1) {
                          }
                      }

        }*/
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
                stockArray[0] = isAllVirtual ? 0 : getAvailableInventory(cxn, item.inventory_fkey, isInternalScan, facilityId);

/*
                if (stockArray[0] > 0 && reserveBORMap.containsKey(item.client_part_no) && !releaseBORReserved) {
                                    stockArray[0] = stockArray[0] - reserveBORMap.get(item.client_part_no);
                                    if (stockArray[0] < 0) {
                                        stockArray[0] = 0;
                                    }
                                }*/

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


        updateLineItemAvailabilityArray(itemStockMap, backorderRule);

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
                updateLineItemFinalQuantities(itemStockMap, item);
                Map reqItemMap = LineItem.getRequiredItemsForInventoryID(new Integer(item.inventory_fkey));
                if (reqItemMap.size() == 0)
                    throw new Exception("Invalid kit definition - " + item.client_part_no + " has no kit items defined!");
                for (int k2 = 0; k2 < lineitems.size(); k2++) {
                    //update line item quantities of kit components
                    LineItem item2 = (LineItem) lineitems.elementAt(k2);
                    if (item2.parent_line != null) {
                        if (reqItemMap.get(new Integer(item2.inventory_fkey)) != null && (item2.parent_line.intValue() == item.tempID || item2.parent_line.intValue() == new Integer(item.line_item_id).intValue())) {
                            long maxToAllocateOfKitComponent = item.quantity_actual * ((Integer) reqItemMap.get(new Integer(item2.inventory_fkey))).intValue();
                            updateLineItemFinalQuantities(itemStockMap, item2, maxToAllocateOfKitComponent);
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
                updateLineItemFinalQuantities(itemStockMap, item);
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


    public static void updateLineItemFinalQuantities(Map itemStockMap, LineItem item) throws Exception {
        updateLineItemFinalQuantities(itemStockMap, item, -1);
    }

    public static void updateLineItemFinalQuantities(Map itemStockMap, LineItem item, long maxToAssign) throws Exception {
        long[] itemData = ((long[]) itemStockMap.get(new Integer(item.inventory_fkey)));

        //log.debug("itemdata:"+itemData);
      //  log.debug("SKU:" + item.client_part_no + "(" + item.parent_line + ") Act/Req/Back:" + item.quantity_actual + "/" + item.quantity_request + "/" + item.quantity_backordered);

        long unallocatedUnits = itemData[2] - itemData[6];
        //assign remaining allocated units for sku, up to amount of request
        item.quantity_actual = (unallocatedUnits) > item.quantity_request ? item.quantity_request : (unallocatedUnits);
      //  log.debug("SKU2:" + item.client_part_no + "(" + item.parent_line + ") Act/Req/Back:" + item.quantity_actual + "/" + item.quantity_request + "/" + item.quantity_backordered);

        if (item.quantity_actual > maxToAssign && item.quantity_actual > 0 && maxToAssign >= 0) {
            item.quantity_actual = maxToAssign;
        }
      //  log.debug("SKU2:" + item.client_part_no + "(" + item.parent_line + ") Act/Req/Back:" + item.quantity_actual + "/" + item.quantity_request + "/" + item.quantity_backordered);

        //record allocated units
        itemData[6] += item.quantity_actual;
        long unallocatedBOUnits = itemData[3] - itemData[7];
      //  log.debug("SKU3:" + item.client_part_no + "(" + item.parent_line + ") Act/Req/Back:" + item.quantity_actual + "/" + item.quantity_request + "/" + item.quantity_backordered);
      //  log.debug("unallocatedBOUnits:" + unallocatedBOUnits);
        if (unallocatedBOUnits > 0) {
            //assign remaining allocated BO units, up to amount of request minus amount already assigned
            item.quantity_backordered =
                    item.quantity_actual < item.quantity_request ? ((unallocatedBOUnits < (item.quantity_request - item.quantity_actual))
                            ? unallocatedBOUnits : item.quantity_request - item.quantity_actual) : 0;
        } else {
            item.quantity_backordered = 0;
        }
       // log.debug("SKU4:" + item.client_part_no + "(" + item.parent_line + ") Act/Req/Back:" + item.quantity_actual + "/" + item.quantity_request + "/" + item.quantity_backordered);

        //log.debug("setting qrequest for litem "+item.client_part_no+" to "+item.quantity_actual);
        if (item.quantity_actual == 0 && item.quantity_backordered == 0 && item.quantity_request > 0) {
            item.quantity_backordered = item.quantity_request;
            item.quantity_request = 0;
        } else {
            item.quantity_request = item.quantity_actual;
        }
      //  log.debug("SKU5:" + item.client_part_no + "(" + item.parent_line + ") Act/Req/Back:" + item.quantity_actual + "/" + item.quantity_request + "/" + item.quantity_backordered);

        if (item.quantity_actual == 0 && item.quantity_backordered == 0 && item.quantity_request == 0) {
            Exception e = new Exception("Zero quantities problem for SKU: " + item.client_part_no);
            StringWriter s = new StringWriter();
            e.printStackTrace(new PrintWriter(s, true));
            Vector v = new Vector();
            v.add("servicerequests@owd.com");
            Mailer.postMailMessage("Zero Inventory problem", "sku:" + item.client_part_no + "\n" + s.toString(), v, "support@owd.com");
            throw e;


        }

        itemData[7] += item.quantity_backordered;
    }

    public static void updateLineItemFinalQuantities(Map itemStockMap, OwdLineItem item) throws Exception {
        updateLineItemFinalQuantities(itemStockMap, item, -1);
    }

    public static void updateLineItemFinalQuantities(Map itemStockMap, OwdLineItem item, long maxToAssign) throws Exception {
        long[] itemData = ((long[]) itemStockMap.get(item.getOwdInventory().getInventoryId()));

        //log.debug("itemdata:"+itemData);
        //log.debug("SKU:"+item.getInventoryNum()+"("+item.getParentKey()+") Act/Req/Back:"+item.getQuantityActual()+"/"+item.getQuantityRequest()+"/"+item.getQuantityBack());

        long unallocatedUnits = itemData[2] - itemData[6];
        //assign remaining allocated units for sku, up to amount of request
        item.setQuantityActual(new Integer((int) ((unallocatedUnits) > item.getQuantityRequest() ? item.getQuantityRequest() : (unallocatedUnits))));

        if (item.getQuantityActual().intValue() > maxToAssign && item.getQuantityActual().intValue() > 0 && maxToAssign >= 0) {
            item.setQuantityActual(new Integer((int) maxToAssign));
        }

        //record allocated units
        itemData[6] += item.getQuantityActual().intValue();
        long unallocatedBOUnits = itemData[3] - itemData[7];

        if (unallocatedBOUnits > 0) {
            //assign remaining allocated BO units, up to amount of request minus amount already assigned
            item.setQuantityBack(
                    new Integer((int) (item.getQuantityActual().intValue() < item.getQuantityRequest() ? ((unallocatedBOUnits < (item.getQuantityRequest() - item.getQuantityActual().intValue()))
                            ? unallocatedBOUnits : item.getQuantityRequest() - item.getQuantityActual().intValue()) : 0)));
        } else {
            item.setQuantityBack(new Integer(0));
        }
        //log.debug("setting qrequest for litem "+item.getInventoryNum()+" to "+item.getQuantityActual());
        if (item.getQuantityActual() == 0 && item.getQuantityBack()
                == 0 && item.getQuantityRequest() > 0) {
            item.setQuantityBack(item.getQuantityRequest());
            item.setQuantityRequest(0);
        } else {
            item.setQuantityRequest(item.getQuantityActual());
        }

        item.setQuantityRequest(item.getQuantityActual().intValue());
        if (item.getQuantityActual().intValue() == 0 && item.getQuantityBack().intValue() == 0 && item.getQuantityRequest() == 0) {
            Exception e = new Exception("Zero quantities problem for SKU: " + item.getInventoryNum());
            StringWriter s = new StringWriter();
            e.printStackTrace(new PrintWriter(s, true));
            Vector v = new Vector();
            v.add("servicerequests@owd.com");
            Mailer.postMailMessage("Zero Inventory problem", "sku:" + item.getInventoryNum() + "\n" + s.toString(), v, "support@owd.com");
            throw e;


        }

        itemData[7] += item.getQuantityBack().intValue();
    }

    public static Map updateLineItemsForAvailability(Connection cxn, Set lineitems, String backorderRule, boolean allowBackorders, int facilityId) throws Exception {
        Map itemStockMap = new TreeMap();


        boolean isAllVirtual = true;

        int clientID = 0;
   //     Map<String, Integer> reserveBORMap = new TreeMap<String, Integer>();
     //   boolean releaseBORReserved = false;

        for (int k = 0; k < lineitems.size(); k++) {
            //update line item quantities
            OwdLineItem item = (OwdLineItem) lineitems.toArray()[k];
            OwdInventory oi = item.getOwdInventory();

            clientID = oi.getOwdClient().getClientId();
            if (oi.getIsAutoInventory() == 0) {
                if(clientID!=463 || (!oi.getNotes().startsWith("DROP")))
                {
                    isAllVirtual = false;
                }
            }
          //  if (item.getInventoryNum().equals("10023")) {
           //     releaseBORReserved = true;
           // }
        }

       /* if (clientID == 160) {
            PreparedStatement sb = null;
            ResultSet rsb = null;

            try {
                          sb = cxn.prepareStatement("select inventory_num,reserve from bor_reserve");
                          rsb = sb.executeQuery();
                          while (rsb.next()) {
                              reserveBORMap.put(rsb.getString(1), rsb.getInt(2));
                          }

                          rsb.close();
                          sb.close();
                      } catch (Exception exbor) {

                      } finally {
                          try {
                              rsb.close();
                          } catch (Exception exb1) {
                          }
                          try {
                              sb.close();
                          } catch (Exception exb1) {
                          }
                      }

        }*/

        //first, update stock map with current counts and availability
        for (int k = 0; k < lineitems.size(); k++) {
            //update line item quantities
            OwdLineItem item = (OwdLineItem) lineitems.toArray()[k];
            long boQuantity = 0;

            item.setQuantityRequest(item.getQuantityRequest() + item.getQuantityBack().intValue());
            item.setQuantityActual(new Integer(0));
            item.setQuantityBack(new Integer(0));

            Integer key = item.getOwdInventory().getInventoryId();
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
                // //  //log.debug("adding "+key);
                stockArray[0] = isAllVirtual ? 0 : getAvailableInventory(cxn, item.getOwdInventory().getInventoryId().intValue() + "", facilityId);

  /*              if (stockArray[0] > 0 && reserveBORMap.containsKey(item.getInventoryNum()) && !releaseBORReserved) {
                                    stockArray[0] = stockArray[0] - reserveBORMap.get(item.getInventoryNum());
                                    if (stockArray[0] < 0) {
                                        stockArray[0] = 0;
                                    }
                                }
*/
                stockArray[1] = item.getQuantityRequest();
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
                stockArray[1] += item.getQuantityRequest();
                stockArray[8] += boQuantity;
            }


        }

/*
            //log.debug("***START MAP***");
Iterator it = itemStockMap.keySet().iterator();
while (it.hasNext()) {
    //check kit restrictions
    Integer iid = (Integer) it.next();
    long[] stockArray = (long[]) itemStockMap.get(iid);
    //log.debug(Inventory.getInventoryForID(""+iid).inventory_num+":"+stockArray[0]
            +":"+stockArray[1]+":"+stockArray[2]+":"+stockArray[3]+":"+stockArray[4]+":"+stockArray[5]);
}*/


        updateLineItemAvailabilityArray(itemStockMap, backorderRule);

        //log.debug("allocating kit lines");
        //update total by-line quantities - kits first!
        for (int k = 0; k < lineitems.size(); k++) {
            //update line item quantities
            OwdLineItem item = (OwdLineItem) lineitems.toArray()[k];
            if (item.getIsParent().intValue() == 1) {
                updateLineItemFinalQuantities(itemStockMap, item);

                Map reqItemMap = LineItem.getRequiredItemsForInventoryID(item.getOwdInventory().getInventoryId());
                if (reqItemMap.size() == 0)
                    throw new Exception("Invalid kit definition - " + item.getInventoryNum() + " has no kit items defined!");
                for (int k2 = 0; k2 < lineitems.size(); k2++) {
                    //update line item quantities of kit components
                    OwdLineItem item2 = (OwdLineItem) lineitems.toArray()[k2];
                    if (item2.getIsParent() != null) {
                        if (reqItemMap.get(item2.getOwdInventory().getInventoryId()) != null && (item2.getParentKey().intValue() == item.getLineItemId().intValue())) {
                            long maxToAllocateOfKitComponent = item.getQuantityActual().intValue() * ((Integer) reqItemMap.get(item2.getOwdInventory().getInventoryId())).intValue();
                            updateLineItemFinalQuantities(itemStockMap, item2, maxToAllocateOfKitComponent);
                        }
                    }
                }


            }
        }

        //log.debug("allocating non-kit lines");
        //update total by-line quantities - non-kits second!
        for (int k = 0; k < lineitems.size(); k++) {
            //update line item quantities
            OwdLineItem item = (OwdLineItem) lineitems.toArray()[k];
            if (item.getParentKey() == null && item.getIsParent().intValue() == 0) {
                updateLineItemFinalQuantities(itemStockMap, item);
            }
        }


          boolean releaseable = false;
        for (int k = 0; k < lineitems.size(); k++) {
            //update line item quantities
OwdLineItem item = (OwdLineItem) lineitems.toArray()[k];
            if (item.getQuantityActual() > 0 && item.getIsInsert()==0) {
                k = lineitems.size();
                releaseable = true;
            }
        }
        if (!releaseable) {
            for (int k = 0; k < lineitems.size(); k++) {
                //update line item quantities
                OwdLineItem item = (OwdLineItem) lineitems.toArray()[k];
               item.setQuantityRequest(item.getQuantityRequest() + item.getQuantityBack());
                item.setQuantityBack(0);
                item.setQuantityActual(0);
                if (item.getQuantityActual() == 0 && item.getQuantityBack() == 0 && item.getQuantityRequest() == 0) {
                    Exception e = new Exception("Zero quantities problem for SKU: " + item.getInventoryNum());
                    StringWriter s = new StringWriter();
                    e.printStackTrace(new PrintWriter(s, true));
                    Vector v = new Vector();
                    v.add("servicerequests@owd.com");
                    Mailer.postMailMessage("Zero Inventory problem", "sku:" + item.getInventoryNum() + "\n" + s.toString(), v, "support@owd.com");
                    throw e;


                }
            }
        }
        return itemStockMap;

    }

    public static void updateLineItemAvailabilityArray(Map itemStockMap, String backorderRule) throws Exception {
        Iterator it = itemStockMap.keySet().iterator();
        //iterate through list to look for kits and evaluate their shippability
        while (it.hasNext()) {
            //check kit restrictions


            Integer iid = (Integer) it.next();
            long[] stockArray = (long[]) itemStockMap.get(iid);

            if (stockArray[8] > stockArray[1]) stockArray[8] = stockArray[1]; //sanity check

            //correct availability for forced backorder quantities
            if (stockArray[8] > 0 && stockArray[0] > (stockArray[1] - stockArray[8])) {
                stockArray[0] = (stockArray[1] - stockArray[8]);
            }


            Map reqItemMap = LineItem.getRequiredItemsForInventoryID(iid);
            if (reqItemMap.size() != 0) {
                //OK we have a kit
                stockArray[5] = 1;
                long shippableKitQuantity = getShippableKitQuantity(reqItemMap, itemStockMap, iid);
                //stockArray[0]=0;
                // //log.debug("shippable kit="+shippableKitQuantity);
                if (shippableKitQuantity < stockArray[1]) {
                    //we have a backorder on the kit - check the policy
                    if (backorderRule.equals(OrderXMLDoc.kPartialShip) || backorderRule.equals(OrderXMLDoc.kIgnoreBackOrder)) {
                        stockArray[2] = shippableKitQuantity;
                        stockArray[3] = stockArray[1] - shippableKitQuantity;
                    } else if (backorderRule.equals(OrderXMLDoc.kBackOrderAll)) {
                        shippableKitQuantity = 0;
                        stockArray[2] = shippableKitQuantity;
                        stockArray[3] = stockArray[1] - shippableKitQuantity;
                    } else if (backorderRule.equals(OrderXMLDoc.kRejectBackOrder)) {
                        shippableKitQuantity = 0;
                        stockArray[2] = shippableKitQuantity;
                        stockArray[3] = stockArray[1] - shippableKitQuantity;
                    }

                } else {
                    stockArray[2] = shippableKitQuantity;
                }

                //now update individual items accordingly...
                Iterator itr = reqItemMap.keySet().iterator();
                while (itr.hasNext()) {
                    Integer reqIid = (Integer) itr.next();
                    int reqQty = ((Integer) reqItemMap.get(reqIid)).intValue();
                    if (itemStockMap.get(reqIid) != null) {
                        //quantities for required item
                        long[] itemArray = ((long[]) itemStockMap.get(reqIid));
                        if (itemArray[1] < (reqQty * (stockArray[2] + stockArray[3]))) {
                            throw new Exception("Cannot process order - Each unit of bundle ID " + iid + " requires " + reqQty + " unit(s) of SKU ID " + reqIid);
                        }

                        //add units to allocated
                        itemArray[2] += stockArray[2] * reqQty;
                        //add units to backorder count
                        itemArray[3] += stockArray[3] * reqQty;

                        //indicate that units have been reserved
                        itemArray[4] = (itemArray[2] + itemArray[3]);

                        //log.debug(Inventory.getInventoryForID(""+reqIid).inventory_num+":"+itemArray[0]
                        //                                              +":"+itemArray[1]+":"+itemArray[2]+":"+itemArray[3]+":"+itemArray[4]+":"+itemArray[5]+":"+itemArray[6]+":"+itemArray[7]);


                        if (itemArray[4] > itemArray[1]) {
                            //  //log.debug(Inventory.getInventoryForID(""+reqIid).inventory_num+":"+itemArray[0]
                            //                      +":"+itemArray[1]+":"+itemArray[2]+":"+itemArray[3]+":"+itemArray[4]+":"+itemArray[5]+":"+itemArray[6]+":"+itemArray[7]);

                            throw new Exception("Cannot process order - Each unit of bundle ID " + iid + " requires " + reqQty + " unit(s) of SKU ID " + reqIid + ". Multiple bundles may be using this SKU.");
                        }

                    } else {
                        throw new Exception("Cannot process order - Each unit of bundle ID " + iid + " requires " + reqQty + " unit(s) of SKU ID " + reqIid);
                    }


                }

            } else {
                // not a kit
            }
        }


        Iterator itx = itemStockMap.keySet().iterator();

        //iterate through list to look for kits and evaluate their shippability
        while (itx.hasNext()) {
            //check kit restrictions
            Integer iid = (Integer) itx.next();
            long[] stockArray = (long[]) itemStockMap.get(iid);

            if (stockArray[8] > 0 && stockArray[3] != stockArray[8]) {
                //throw new Exception("Cannot accept backorder quantity of " + stockArray[8] + " for SKU " + Inventory.getInventoryForID(""+iid).inventory_num + " : Available count = " + stockArray[0]);

            }

            if (stockArray[5] == 0) {
                //not a kit
                if (stockArray[4] > 0) {
                    //involved with a kit
                    long unallocated = stockArray[1] - (stockArray[4]);
                    if (unallocated > 0) {
                        long available = stockArray[0] - stockArray[2];
                        if (available >= unallocated) {
                            stockArray[2] += unallocated;
                        } else { //is backorder
                            if (backorderRule.equals(OrderXMLDoc.kPartialShip) || backorderRule.equals(OrderXMLDoc.kIgnoreBackOrder)) {
                                stockArray[2] += available;
                                stockArray[3] += unallocated - available;
                            } else if (backorderRule.equals(OrderXMLDoc.kBackOrderAll)) {

                                stockArray[3] += unallocated;

                            } else if (backorderRule.equals(OrderXMLDoc.kRejectBackOrder)) {
                                stockArray[3] += unallocated;
                            }
                        }
                    }
                } else {
                    //no associated kit
                    if ((stockArray[0] - stockArray[2]) >= stockArray[1]) {
                        stockArray[2] = stockArray[1];
                    } else { //is backorder
                        if (backorderRule.equals(OrderXMLDoc.kPartialShip) || backorderRule.equals(OrderXMLDoc.kIgnoreBackOrder)) {
                            stockArray[2] = stockArray[0];
                            stockArray[3] = stockArray[1] - stockArray[2];
                        } else if (backorderRule.equals(OrderXMLDoc.kBackOrderAll)) {

                            stockArray[3] = stockArray[1];

                        } else if (backorderRule.equals(OrderXMLDoc.kRejectBackOrder)) {
                            stockArray[3] = stockArray[1];
                        }

                    }

                }

            }

            stockArray[3] += stockArray[8];
        }
    }

    public static long getShippableKitQuantity(Map reqItemMap, Map itemStockMap, Integer iid) throws Exception {

        long finalMinKitQty = -1;
        long[] stockArray = (long[]) itemStockMap.get(iid);

        long requestedKitQty = stockArray[1];

        //correct availability for forced backorder quantities
        if (stockArray[8] > 0 && stockArray[1] > (stockArray[8])) {
            requestedKitQty = (stockArray[8]);
        }

        //log.debug("Requesting kit quantity of "+requestedKitQty);
        // //log.debug(reqItemMap);

        Iterator itr = reqItemMap.keySet().iterator();
        while (itr.hasNext()) {
            long minKitQty = 0;
            Integer reqIid = (Integer) itr.next();
            int reqQty = ((Integer) reqItemMap.get(reqIid)).intValue();
            if (itemStockMap.get(reqIid) != null) {
                //found kit item
                long itemOnHand = ((long[]) itemStockMap.get(reqIid))[0] - ((long[]) itemStockMap.get(reqIid))[2];
                while (itemOnHand > 0) {
                    minKitQty += 1;
                    itemOnHand -= reqQty;
                    if (itemOnHand < 0) {
                        minKitQty -= 1;
                    }
                }
                // //log.debug("minKitQty="+minKitQty);
                if (finalMinKitQty == -1) finalMinKitQty = minKitQty;
                if (minKitQty < finalMinKitQty) finalMinKitQty = minKitQty;

            } else {
                throw new Exception("Cannot process order - Each unit of bundle ID " + iid + " requires " + reqQty + " unit(s) of SKU ID " + reqIid);
            }

        }

        if (finalMinKitQty == -1) finalMinKitQty = 0;

        //OK, req quantities now updated for all items
        return (finalMinKitQty < requestedKitQty) ? finalMinKitQty : requestedKitQty;
    }

    public static String shipExistingOrder(OrderStatus status) throws Exception {


        return shipExistingOrder( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), status, null,null);
    }

    public static String shipExistingOrder(OrderStatus status, Date postDate) throws Exception {


        return shipExistingOrder( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), status, postDate,null);
    }

    public static String shipExistingOrder(Connection cxn, OrderStatus status) throws Exception {

        return shipExistingOrder(cxn, status, null,null);
    }
    public static String shipExistingOrder(Connection cxn, OrderStatus status, String printId) throws Exception {

        return shipExistingOrder(cxn, status, null, printId);
    }
     public static String shipExistingOrder(Connection cxn, OrderStatus status, Date postDate) throws Exception {
         return  shipExistingOrder(cxn, status, postDate, null);
     }
    public static String shipExistingOrder(Connection cxn, OrderStatus status, Date postDate, String printId) throws Exception {
        String backorder_order_num = null;
        boolean isABackorder = false;
        Statement stmt = null;
        //log.debug("shipExistingOrder");
        boolean cannotRelease = false;

        postDate = fixPostDate(postDate);
        if (postDate == null) {
            postDate = Calendar.getInstance().getTime();
        }

        if (status.is_shipped) throw new Exception("Order is already shipped");
        if (status.is_posted) throw new Exception("Order is already released for shipping");
        if (status.is_void) throw new Exception("Order has been voided");

        try {

            try {
                if (status.original_ordernum.trim().length() > 5) {

                    //if parentOrder is not void and has not shipped, fail
                    if (0 == ConnectionManager.getCanReleaseBackorder(status.original_ordernum)) {
                        cannotRelease = true;
                    }


                }
            } catch (Exception exp) {
                cannotRelease = true;
                exp.printStackTrace();
            }

            if (cannotRelease) {
                throw new Exception("Cannot ship backorder; parent order " + status.original_ordernum + " is still pending. Void or ship parent order to release this backorder.");
            }


            if (0 >= status.items.size()) {
                throw new Exception("No valid line items in order - order could not be saved!");
            }

            long unitsShipping = 0;

            for (int i = 0; i < status.items.size(); i++) {
                unitsShipping += ((LineItem) status.items.elementAt(i)).quantity_actual;

                //log.debug("saving updated line item A/R/B:"+((LineItem) status.items.elementAt(i)).quantity_actual
                // +"/"+((LineItem) status.items.elementAt(i)).quantity_request
                //  +"/"+((LineItem) status.items.elementAt(i)).quantity_backordered );
                ((LineItem) status.items.elementAt(i)).getLineTotal();
                //log.debug("updating item " + ((LineItem) status.items.elementAt(i)).client_part_no);
                ((LineItem) status.items.elementAt(i)).dbupdate(cxn);
                if (((LineItem) status.items.elementAt(i)).quantity_backordered > 0)
                    isABackorder = true;
            }


            if (0 >= unitsShipping) {
                throw new Exception("No shippable items in order - order could not be released!");
            }
            //log.debug("line items saved");
            //log.debug("setting post date");
            String esql = "update owd_order WITH (ROWLOCK) set post_date = \'" + OWDUtilities.getSQLDateTimeString(postDate) + "\' , is_future_ship = 0, is_backorder=0 where order_id = " + status.order_id;

            stmt = cxn.createStatement();

            int rowsUpdated = stmt.executeUpdate(esql);

            //log.debug("set post date");
            if (rowsUpdated < 1)
                throw new Exception("Order not posted; could not release to warehouse");

            stmt.close();

            if (status.getLineCount() > 0) {

                esql = "INSERT INTO owd_print_queue (order_id, client_id, printgroup_id)"
                        + " VALUES("
                        + status.order_id + ", "
                        + status.client_id + " , "
                        +  printId
                        +")  ";

                stmt = cxn.createStatement();

                rowsUpdated = stmt.executeUpdate(esql);
                //log.debug("queue insert");
                if (rowsUpdated < 1)
                    throw new Exception("Order not printed; could not insert into print queue");

                stmt.close();
            }
            cxn.commit();
            if (isABackorder) {

                String newOrderNum = ConnectionManager.getNextID("Order");
                //log.debug("Creating backorder for num:" + newOrderNum);
                esql = "exec create_backorder " + status.order_id + ",\'" + status.OWDorderReference + "\',\'" + newOrderNum + "\'   ";

                stmt = cxn.createStatement();

                boolean hasResultSet = stmt.execute(esql);
                //log.debug("executed backorder");
                stmt.close();

                backorder_order_num = newOrderNum;
            }
            cxn.commit();

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            OWDUtilities.debugApp(ex);
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
        }
        return backorder_order_num;

    }

    public static String shipExistingHibernateOrder(OwdOrder order) throws Exception {

        return shipExistingHibernateOrder(HibernateSession.currentSession(), order, null);
    }

    public static String shipExistingHibernateOrder(OwdOrder order, Date postDate) throws Exception {

        return shipExistingHibernateOrder(HibernateSession.currentSession(), order, postDate);
    }

    public static String shipExistingHibernateOrder(Session sess, OwdOrder order) throws Exception {

        return shipExistingHibernateOrder(sess, order, null);
    }


    static Date fixPostDate(Date postDate) {
        if (postDate == null) {
            return null;
        } else {
            Date today = Calendar.getInstance().getTime();

            if (today.compareTo(postDate) < 0) {
                return postDate;
            } else {
                return null;
            }

        }
    }

    public static String shipExistingHibernateOrder(Session sess, OwdOrder order, Date postDate) throws Exception {
        String backorder_order_num = null;
        boolean isABackorder = false;
        Statement stmt = null;
        //log.debug("shipExistingHibernateOrder");


        postDate = fixPostDate(postDate);


        if (order.getShippedDate() != null) throw new Exception("Order is already shipped");
        if (order.getPostDate() != null) throw new Exception("Order is already released for shipping");
        if (order.getIsVoid() == 1) throw new Exception("Order has been voided");


        try {

            if (0 >= order.getLineitems().size()) {
                throw new Exception("No valid line items in order - order could not be saved!");
            }


            updateLineItemsForAvailability( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(), order.getLineitems(), OrderXMLDoc.kPartialShip,  true,FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId());

            for (int k = 0; k < order.getLineitems().size(); k++) {
                //update line item quantities
                OwdLineItem item = (OwdLineItem) order.getLineitems().toArray()[k];

                if (item.getQuantityBack() > 0) {

                    isABackorder = true;


                }

                sess.saveOrUpdate(item);

            }


            order.setPostDate(postDate == null ? Calendar.getInstance().getTime() : postDate);
            order.setIsFutureShip(0);
            order.setIsBackorder(false);

            //log.debug("line items saved");
            //log.debug("setting post date");


            sess.saveOrUpdate(order);
            OwdPrintQueue queue = new OwdPrintQueue();
            queue.setClientId(order.getClientFkey());
            queue.setOrderId(order.getOrderId().intValue());

            sess.saveOrUpdate(queue);

            //next line REQUIRED before attempting to create backorder on uncommitted order
            sess.flush();
            //   HibUtils.commit(sess);
            if (isABackorder) {

                String newOrderNum = ConnectionManager.getNextID("Order");
                //log.debug("Creating backorder for num:" + newOrderNum);
                String esql = "exec create_backorder " + order.getOrderId() + ",\'" + order.getOrderNum() + "\',\'" + newOrderNum + "\'   ";

                stmt =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().createStatement();

                boolean hasResultSet = stmt.execute(esql);
                //log.debug("executed backorder");
                stmt.close();

                backorder_order_num = newOrderNum;
            }
            sess.flush();
            //  HibUtils.commit(HibernateSession.currentSession());

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            OWDUtilities.debugApp(ex);
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }
        return backorder_order_num;

    }

    public static boolean hasStatusItemID(String invID, OrderStatus status) {
        for (int i = 0; i < status.items.size(); i++) {
            if (((LineItem) (status.items.elementAt(i))).inventory_fkey.equals(invID)) {
                return true;
            }
        }
        return false;
    }

    public static int getAvailableInventory(Connection cxn, String inventoryID, int facilityId
    ) throws Exception {
        return getAvailableInventory(cxn, inventoryID, false, facilityId);
    }

    public static int getAvailableKitInventory(Connection cxn, String inventoryID, int facilityId
    ) throws Exception {
        return getAvailableKitInventory(cxn, inventoryID, false,facilityId);
    }

    public static int getAvailableKitInventory(Connection cxn, String inventoryID, boolean isInternalScan,int facilityId) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {

            String esql = "select  min(qty/req.req_inventory_quant), max(ISNULL(is_scan_for_release,0)),min(ISNULL(can_ship_if_instock,1))  from \n" +
                    "                     owd_inventory i (NOLOCK) join vw_stock_by_facility v on v.inventory_id=i.inventory_id and v.facility_fkey=" +facilityId+
                    "                     left outer join owd_inventory_dropshipinfo ds (NOLOCK) on ds.inventory_fkey=i.inventory_id \n" +
                    " join owd_inventory_required_skus req (NOLOCK) on req.req_inventory_fkey=i.inventory_id \n" +
                    "                     \n" +
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


    public static int getAvailableInventory(Connection cxn, String inventoryID, boolean isInternalScan, int facilityId) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {

            String esql = "select  qty, ISNULL(is_scan_for_release,0), ISNULL(is_auto_inventory,0),ISNULL(can_ship_if_instock,1) from " +
                    " owd_inventory i (NOLOCK) join vw_stock_by_facility v on v.inventory_id=i.inventory_id and v.facility_fkey=" +facilityId+
                    "                     left outer join owd_inventory_dropshipinfo ds (NOLOCK) on ds.inventory_fkey=i.inventory_id \n" +
                    "                    where i.inventory_id=" + inventoryID;

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


    public static String getPrintTemplateRefData(int orderid) throws Exception {
        String result = "[]";
        Connection cxn = null;
        ResultSet rs = null;
         PreparedStatement ps = null;

        try {
            cxn = ConnectionManager.getConnection();
            ps = cxn.prepareStatement("select ISNULL(refdatamap,'[]') from  owd_order_template (NOLOCK) where order_fkey = ?");

            ps.setInt(1,orderid);

            rs = ps.executeQuery();
            ////////////log.debug("ck orderrefexists");
            if (rs != null) {


                if(rs.next())
                {
                    result = rs.getString(1);
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }

        return result;
    }



    public static boolean prtPackRqd(int orderid) throws Exception{
        String result = "";
        Connection cxn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            cxn = ConnectionManager.getConnection();
            ps = cxn.prepareStatement("select prt_pack_reqd from  owd_order (NOLOCK) where order_id = ?");

            ps.setInt(1,orderid);

            rs = ps.executeQuery();
            ////////////log.debug("ck orderrefexists");
            if (rs != null) {


                if(rs.next())
                {

                    result = rs.getString(1);
                    log.debug(result);
                    if(result.equals("1")){
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }





        return false;
    }
    //if returns empty string, equivalent to no template code being set
    public static String getPrintTemplateCustomCode(int orderid) throws Exception {
        String result = "";
        Connection cxn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            cxn = ConnectionManager.getConnection();
            ps = cxn.prepareStatement("select ISNULL(code,'') from  owd_order_template (NOLOCK) where order_fkey = ?");

            ps.setInt(1,orderid);

            rs = ps.executeQuery();
            ////////////log.debug("ck orderrefexists");
            if (rs != null) {


                if(rs.next())
                {
                    result = rs.getString(1);
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }

        return result;
    }


    public static void mergeItemPackingInstructionsToOrder(int orderId) throws Exception
    {
        String packInfo = "";
        List<String> pastInstructions= new ArrayList<String>();

        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
        if(order==null)
        {
            throw new Exception("No order with ID "+orderId+" could be found");
        }

            for (OwdLineItem line:order.getLineitems()) {
                String itemPackInfo = line.getOwdInventory().getPackingInstructions().getInstructions();

                if(itemPackInfo!=null)
                {
                    if(itemPackInfo.length()>4)
                    {
                        if(!(pastInstructions.contains(itemPackInfo)))
                        {

                            packInfo = packInfo+"==========\r\n"+itemPackInfo+"\r\n";
                            pastInstructions.add(itemPackInfo.trim());
                        }

                    }
                }
            }
            if(packInfo.length()>0)
            {
                OrderPackingInstruction packer = order.getPackingInstructions();
                if(packer==null)
                {
                    packer = new OrderPackingInstruction();
                }
                packer.setInstructions(packInfo);
                packer.setOrder(order);
                order.setPackingInstructions(packer);

                HibernateSession.currentSession().save(packer);
                HibernateSession.currentSession().save(order);
                HibUtils.commit(HibernateSession.currentSession());

            }
    }

    public static boolean orderRefnumAndPoExists(String refnum, String po, String clientID) {

        Statement stmt = null;
        ResultSet rs = null;
        boolean orderExists = false;
        Connection cxn = null;

        try {

            cxn = ConnectionManager.getConnection();

            String esql = "select ISNULL(count(order_id),0) as orders from  owd_order (NOLOCK) where order_refnum = \'" + refnum + "\' and po_num=\'"+po+"\' and client_fkey=" + clientID;
            ////////////log.debug("ck orderrefexists");
            ////////////log.debug("ck orderrefexists");
            stmt = cxn.createStatement();
            rs = stmt.executeQuery(esql);
            ////////////log.debug("ck orderrefexists");
            if (rs != null) {
                ////////////log.debug("ck orderrefexists");

                ////////////log.debug("ck orderrefexists");
                if (rs.next()) {    ////////////log.debug("ck orderrefexists");
                    orderExists = (0 < rs.getInt(1));////////////log.debug("ck orderrefexists");
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
            } catch (Exception ex) {
            }

        }
        return orderExists;

    }

    public static boolean orderRefnumExists(String refnum, String clientID) {

        Statement stmt = null;
        ResultSet rs = null;
        boolean orderExists = false;
        Connection cxn = null;

        try {

            cxn = ConnectionManager.getConnection();

            String esql = "select ISNULL(count(order_id),0) as orders from  owd_order (NOLOCK) where order_refnum = \'" + refnum + "\' and client_fkey=" + clientID;
            ////////////log.debug("ck orderrefexists");
            ////////////log.debug("ck orderrefexists");
            stmt = cxn.createStatement();
            rs = stmt.executeQuery(esql);
            ////////////log.debug("ck orderrefexists");
            if (rs != null) {
                ////////////log.debug("ck orderrefexists");

                ////////////log.debug("ck orderrefexists");
                if (rs.next()) {    ////////////log.debug("ck orderrefexists");
                    orderExists = (0 < rs.getInt(1));////////////log.debug("ck orderrefexists");
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
            } catch (Exception ex) {
            }

        }
        return orderExists;

    }

    public static String getOrderIdForClientOrderReference(String aorderRef, String aclientID, boolean excludeVoids) {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String orderExists = null;

        if (aorderRef == null) {
            //////////log.debug("orderref from client was null");
            aorderRef = "";
        }

        if (aclientID == null) {
            //////////log.debug("clientID from client was null");
            aclientID = "";
        }
        ////////////log.debug("ck orderrefexists");
        try {
            cxn = ConnectionManager.getConnection();
            ////////////log.debug("ck orderrefexists");
            String esql = "select max(ISNULL(order_id,0)) as orders from  owd_order (NOLOCK) where order_refnum = \'" + aorderRef + "\' and client_fkey=" + aclientID;
            ////////////log.debug("ck orderrefexists");
            if (excludeVoids)
                esql = esql + " and is_void=0";
            ////////////log.debug("ck orderrefexists");
            stmt = cxn.createStatement();
            rs = stmt.executeQuery(esql);
            ////////////log.debug("ck orderrefexists");
            if (rs != null) {
                ////////////log.debug("ck orderrefexists");
////////////log.debug("ck orderrefexists");
                if (rs.next()) {
                    orderExists = rs.getString(1);
                }
            }

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            OWDUtilities.debugApp(ex);
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
            return orderExists;
        }

    }

    public static boolean clientOrderReferenceExists(String aorderRef, String aclientID, boolean excludeVoids) {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean orderExists = false;

        if (aorderRef == null) {
            //////////log.debug("orderref from client was null");
            aorderRef = "";
        }

        if (aclientID == null) {
            //////////log.debug("clientID from client was null");
            aclientID = "";
        }
        ////////////log.debug("ck orderrefexists");
        try {
            cxn = ConnectionManager.getConnection();
            ////////////log.debug("ck orderrefexists");
            String esql = "select ISNULL(count(order_id),0) as orders from  owd_order (NOLOCK) where order_refnum = \'" + aorderRef + "\' and client_fkey=" + aclientID;
            ////////////log.debug("ck orderrefexists");
            if (excludeVoids)
                esql = esql + " and is_void=0";
            ////////////log.debug("ck orderrefexists");
            stmt = cxn.createStatement();
            rs = stmt.executeQuery(esql);
            ////////////log.debug("ck orderrefexists");
            if (rs != null) {
                ////////////log.debug("ck orderrefexists");
////////////log.debug("ck orderrefexists");
                if (rs.next()) {
                    orderExists = (0 < rs.getInt(1));
                }
            }

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            OWDUtilities.debugApp(ex);
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
            return orderExists;
        }

    }


    public static String getWarehouseStatusString(Integer oid)  {
        String cancel = "select distinct case when o.order_status<>'AT WAREHOUSE' then 'PENDING' else case when po.id is not null then 'PACKING' else case when pick_status=2 then 'PICKED' else case when pick_status=1 then 'PICKING' else 'PENDING' end end end end from owd_order o left outer join package_order po on po.owd_order_fkey=order_id where order_id=?";
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String response = "PENDING";
        try {
            cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement(cancel);
            stmt.setInt(1,oid);
             rs = stmt.executeQuery();
            if (rs.next()) {
                response = rs.getString(1);
            }

            stmt.close();
            rs.close();
        } catch (Exception e) {
            try {
                cxn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                cxn.close();
            } catch (Exception ex) {
                 ex.printStackTrace();
            }
        }
        return response;
    }

    public static void cancelAutoShip(String order_fkey) throws Exception {
        String cancel = "update owd_order_auto WITH (ROWLOCK) set status = 0 where order_fkey = ?";
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement(cancel);
            int result = stmt.executeUpdate();
            if (result == 0) {
                throw new Exception("auto ship order not found. Cancelation failed.");
            }
            cxn.commit();
            stmt.close();
            rs.close();
        } catch (Exception e) {
            cxn.rollback();
            throw e;
        } finally {
            try {
                cxn.close();
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    public static int getAvailableInventory(String inventoryID, int facilityId) throws Exception {
        int result = 0;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();
            result = getAvailableInventory(cxn, inventoryID,facilityId);
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

    public static long getRequestQuantityForSKUFromLineItemVector(String sku, Vector lineitems) {

        int quantity = 0;

        ////log.debug("sku count option 2");
        for (int i = 0; i < lineitems.size(); i++) {
            if (sku.equals(((LineItem) lineitems.elementAt(i)).client_part_no)) {
                quantity += ((LineItem) lineitems.elementAt(i)).quantity_request;

            }
        }


        return quantity;

    }
    public static void startShipping(String orderId) throws Exception{
        try{
            Query q = HibernateSession.currentSession().createSQLQuery("update owd_order set is_shipping = 1 where order_id = :orderId");
            q.setParameter("orderId",orderId);
            int i = q.executeUpdate();
            if(i==0){
                throw new Exception("Unable to start shipping");

            }
            HibUtils.commit(HibernateSession.currentSession());
        }catch(Exception e){
            throw new Exception("Unable to start shipping: "+e.getMessage());
        }


    }
    public static boolean isShippingFromPackageBarcode(String barcode) throws Exception{
        boolean shipping = false;
        String sql = "SELECT\n" +
                "    dbo.package_order.owd_order_fkey\n" +
                "FROM\n" +
                "    dbo.package_order\n" +
                "INNER JOIN\n" +
                "    dbo.package\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                "WHERE\n" +
                "    dbo.package.pack_barcode = :barcode ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("barcode",barcode);
        List results = q.list();
        if(results.size()>0){
            return isShipping(results.get(0).toString());
        }
        return shipping;
    }
    public static boolean isShipping(String orderId) throws Exception{
        boolean shipping = false;
        try{
            Query q = HibernateSession.currentSession().createSQLQuery("select isnull(is_shipping,0) from owd_order where order_id = :orderId");
            q.setParameter("orderId",orderId);
            List results = q.list();
            if(results.size()>0){
                if(results.get(0).toString().equals("1")){
                    shipping = true;
                }
            }else{
                throw new Exception("could not lookup");
            }
        }catch(Exception e){
            throw new Exception("Error checking isShipping: "+e.getMessage());
        }
        return shipping;
    }
    public static void stopShipping(String orderId) throws Exception{
        try{
            System.out.println("This is the orderID for stop shipping: "+orderId);
            Query q = HibernateSession.currentSession().createSQLQuery("update owd_order set is_shipping = 0 where order_id = :orderId");
            q.setParameter("orderId",orderId);
            int i = q.executeUpdate();
            if(i==0){
                throw new Exception("Unable to stop shipping");

            }
            HibUtils.commit(HibernateSession.currentSession());
        }catch(Exception e){
            throw new Exception("Unable to stop shipping: "+e.getMessage());
        }


    }
    public static PackageOrder getPackageOrderFromOrderId(String orderId) throws Exception{
        PackageOrder po;
        String sql = "select * from package_order where owd_order_fkey = :orderId and is_void = 0";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List results = q.list();
        if(results.size()==1){
            Map packageOrderRow = (Map) results.get(0);
            System.out.println(packageOrderRow.get("id"));

          po = (PackageOrder) HibernateSession.currentSession().load(PackageOrder.class, Integer.parseInt(packageOrderRow.get("id").toString()));


        }else{
            if(results.size()==0){
                throw new Exception("Order not packed");
            }else{
                throw new Exception("Too many records found. Check packout process");
            }
        }

        return po;
    }
    public static void addSSCCToPackages(Integer orderId) throws Exception{

        System.out.println("Doing add SSCC for order id: "+orderId);
        String sql = "select * from package_order where owd_order_fkey = :orderId and is_void = 0";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List results = q.list();
        Map packageOrderRow = (Map) results.get(0);
        System.out.println(packageOrderRow.get("id"));

        PackageOrder po = (PackageOrder) HibernateSession.currentSession().load(PackageOrder.class, Integer.parseInt(packageOrderRow.get("id").toString()));
        System.out.println(po.getFacility());
        Iterator<OWDPackage> it = po.getPackages().iterator();

        while (it.hasNext()){
            OWDPackage pkg = it.next();
            if(pkg.getSSCC().length()==0){
                pkg.setSSCC(ManifestingManager.getSSCCBarcode());
                HibernateSession.currentSession().saveOrUpdate(pkg);
            }

        }
        HibUtils.commit(HibernateSession.currentSession());



    }
    public static boolean checkLTLAndUpdateTrackingAndPackages(String orderId) throws Exception{

        //This is used when you have to have tracking info for reporting back fro EDI based upon package data. Copies current tracking for the number of packages.
        boolean success = true;
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));
        if(order.getShipinfo().getCarrService().equals("LTL")){

            String sql = "select * from package_order where owd_order_fkey = :orderId and is_void = 0";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
            q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List results = q.list();

            if(results.size()>0){
                sql = "select order_track_id from owd_order_track where order_fkey = :orderFkey and is_void = 0";
                Query qq = HibernateSession.currentSession().createSQLQuery(sql);
                qq.setParameter("orderFkey", orderId);
                List trackResults = qq.list();
                if(trackResults.size()>1|| trackResults.size()==0) throw new Exception("Too many/few tracking numbers for this process, please review");
                OwdOrderTrack firstTracking = (OwdOrderTrack) HibernateSession.currentSession().load(OwdOrderTrack.class, Integer.parseInt(trackResults.get(0).toString()));
                System.out.println("This is the first track: " + firstTracking.getTrackingNo());


                success = false;
            Map packageOrderRow = (Map) results.get(0);
                System.out.println(packageOrderRow.get("id"));

                PackageOrder po = (PackageOrder) HibernateSession.currentSession().load(PackageOrder.class, Integer.parseInt(packageOrderRow.get("id").toString()));
                System.out.println(po.getFacility());
                Iterator<OWDPackage> it = po.getPackages().iterator();
                int shippedPacks = 0;
                while (it.hasNext()){
                    OWDPackage pkg = it.next();
                    System.out.println(pkg.getPackBarcode());
                    System.out.println(pkg.getPackBarcode().substring(pkg.getPackBarcode().indexOf("b")+1));
                    String boxNum = pkg.getPackBarcode().substring(pkg.getPackBarcode().indexOf("b")+1);
                    if (boxNum.equals("1")){
                        System.out.println("This is first box: "+ pkg.getWeightLbs());
                        pkg.setOrderTrackFkey(firstTracking.getOrderTrackId());
                        HibernateSession.currentSession().save(pkg);
                        firstTracking.setWeight(pkg.getWeightLbs());
                        HibernateSession.currentSession().save(firstTracking);
                        shippedPacks++;
                    }else{
                        System.out.println("Other packages");
                        OwdOrderTrack newTrack = new OwdOrderTrack();
                        newTrack.setOrderFkey(firstTracking.getOrderFkey());
                        newTrack.setLineIndex(Integer.parseInt(boxNum));
                        newTrack.setTrackingNo(firstTracking.getTrackingNo());
                        newTrack.setWeight(pkg.getWeightLbs());
                        newTrack.setTotalBilled(BigDecimal.ZERO);
                        newTrack.setCostOfGoods(firstTracking.getCostOfGoods());
                        newTrack.setShipDate(firstTracking.getShipDate());
                        newTrack.setMsn(firstTracking.getMsn());
                        newTrack.setIsVoid(firstTracking.getIsVoid());
                        newTrack.setCreatedDate(firstTracking.getCreatedDate());
                        newTrack.setCreatedBy(firstTracking.getCreatedBy());
                        newTrack.setModifiedDate(firstTracking.getModifiedDate());
                        newTrack.setModifiedBy(firstTracking.getModifiedBy());
                        newTrack.setReported(firstTracking.getReported());
                        newTrack.setEmailSent(firstTracking.getEmailSent());
                        newTrack.setShipnoticeSent(firstTracking.getShipnoticeSent());
                        newTrack.setShipperAcct(firstTracking.getShipperAcct());
                        newTrack.setIsBilled(firstTracking.getIsBilled());

                        HibernateSession.currentSession().save(newTrack);
                        pkg.setOrderTrackFkey(newTrack.getOrderTrackId());
                        HibernateSession.currentSession().save(pkg);
                        shippedPacks++;



                    }


                }
                HibUtils.commit(HibernateSession.currentSession());
                Query ou = HibernateSession.currentSession().createSQLQuery("update owd_order set ship_packs = :packs where order_id = :orderId");
                ou.setParameter("packs",shippedPacks);
                ou.setParameter("orderId",orderId);
                ou.executeUpdate();
                HibUtils.commit(HibernateSession.currentSession());



                HibernateSession.currentSession().refresh(order);

                success = true;
            }


        }else{
            System.out.println("Not an LTL so we will skip");
        }





        return success;
    }

    public static String getMethodUsedForFlatRate(Integer orderId) throws Exception{

                String method = "";


                OwdOrder owdorder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
                Criteria crit = HibernateSession.currentSession().createCriteria(OwdOrderTrack.class);
                crit.add(Restrictions.eq("orderFkey", owdorder.getOrderId()));
                crit.add(Restrictions.eq("isVoid",0));
                List<OwdOrderTrack> owdOrderTracks = crit.list();
                if(owdOrderTracks.size()>0){

                    Criteria crit2 = HibernateSession.currentSession().createCriteria(OwdShipMethod.class);
                    String code = owdOrderTracks.get(0).getServiceCode();
                    if(code.equalsIgnoreCase("TANDATA_USPS.USPS.PRIORITY")){
                        code = "TANDATA_USPS.USPS.PRIORITY_CUBIC";
                    }
                    crit2.add(Restrictions.eq("methodCode",code));

                    List<OwdShipMethod> methods = crit2.list();
                    if(methods.size()>0) {
                        method = methods.get(0).getMethodName();
                        log.debug(method);
                    }else{
                        throw new Exception("Unable to map "+owdOrderTracks.get(0).getServiceCode());
                    }
                }





        return method;



    }

    public static boolean isApo(OwdOrderShipInfo info){


        String state = info.getShipState();
        String city = info.getShipCity();
        String country = info.getShipCountry();
        System.out.println("In is apo this is country: " + country);
        System.out.println(state);
        System.out.println(city);


        if (country.equalsIgnoreCase("USA") || country.equalsIgnoreCase("UNITED_STATES")) {
            //////System.out.println("got cs::"+city+"::"+stater+"::");

            if (state.equalsIgnoreCase("AE") || state.equalsIgnoreCase("AA") || state.equalsIgnoreCase("AP") || city.equalsIgnoreCase("APO") || city.equalsIgnoreCase("FPO") || city.equalsIgnoreCase("MPO") || city.equalsIgnoreCase("DPO")) {


                return true;

            } else {

                return false;

            }
        }
        return false;
    }

    public static boolean isPO(OwdOrderShipInfo info ){
        Pattern p = Pattern.compile("[Pp](\\.*?)\\s{0,2}[Oo]\\.*?\\s{0,2}[Bb][Oo][Xx]");
        Matcher m = p.matcher(info.getShipAddressOne());
        if (m.find()) {

            return true;
        }
        m = p.matcher(info.getShipAddressTwo());
        if (m.find()) {

            return true;
        }
        return false;
    }

    public static boolean isAPOOrPO(OwdOrderShipInfo info){
        if(isApo(info)) return true;

        return isPO(info);
    }

    public static void releaseBackorder(Connection cxn,String orderId, String clientId) throws Exception{

        boolean swapFacility = false;
        String originalFacility = "";

        try
        {
            //get order status
            //log.debug("getting OS for "+(String)oids.elementAt(i));
            OrderStatus status = new com.owd.core.business.order.OrderStatus(orderId,
                    clientId);
            Client client = Client.getClientForID(clientId);
            //log.debug("got OS for "+(String)oids.elementAt(i));
            int units = 0;
            if(status.getLocation().equalsIgnoreCase(DOMUtilities._domClosest) || status.getShipPolicy().equalsIgnoreCase(DOMUtilities._domClosest)){
                swapFacility = true;
                originalFacility = status.getLocation();
                List<domFillable> facilities = DOMUtilities.getClosestAndFillablePercentList(status);
                if(facilities.size()>0){
                    status.changeLocation(facilities.get(0).getLocCode());
                }

            }


            OrderUtilities.updateLineItemsForAvailability(cxn,status.items,OrderXMLDoc.kPartialShip,true,FacilitiesManager.getFacilityForCode(status.shipLocation).getId());

            for(int k=0;k<status.items.size();k++)
            {
                //check line item quantities
                LineItem item = (LineItem)status.items.elementAt(k);

                if(item.quantity_backordered>0)
                {
                    //oops - still have a backorder situation...
                    throw new Exception("backorder found when attempting to autoship invID "+item.inventory_fkey+" ("+OrderUtilities.getAvailableInventory(cxn,item.inventory_fkey,FacilitiesManager.getFacilityForCode(status.shipLocation).getId())+") orderID "+orderId);
                }
            }


            //what kind of order is it?
            if (status.original_ordernum.length() > 4)
            { //partial ship
                if (client.partial_ship_type.equals("1"))
                {
                    ShippingInfo sinfo = status.shipping;

                    if(client.partial_ship_carrier.startsWith("FedEx") && client.fedex_acct.length() > 0)
                    {
                        sinfo.setShipOptions(client.partial_ship_carrier,"Third Party Billing",client.fedex_acct);

                    }else if (client.partial_ship_carrier.startsWith("UPS") && client.ups_acct.length() > 0)
                    {
                        sinfo.setShipOptions(client.partial_ship_carrier,"Third Party Billing",client.ups_acct);

                    }else{
                        sinfo.setShipOptions(client.partial_ship_carrier,"Prepaid","");
                    }

                    sinfo.dbupdate(cxn);
                }
            }else
            { //original order
                if (client.original_ship_type.equals("1"))
                {
                    ShippingInfo sinfo = status.shipping;

                    if(client.original_ship_carrier.startsWith("FedEx") && client.fedex_acct.length() > 0)
                    {
                        sinfo.setShipOptions(client.original_ship_carrier,"Third Party Billing",client.fedex_acct);

                    }else if (client.original_ship_carrier.startsWith("UPS") && client.ups_acct.length() > 0)
                    {
                        sinfo.setShipOptions(client.original_ship_carrier,"Third Party Billing",client.ups_acct);

                    }else{
                        sinfo.setShipOptions(client.original_ship_carrier,"Prepaid","");
                    }

                    sinfo.dbupdate(cxn);
                }
            }


            String backorderRef = OrderUtilities.shipExistingOrder(cxn,status);
            //log.debug("Shipped backorder...");

            Event.addOrderEvent(cxn,new Integer(status.order_id).intValue(),Event.kEventTypeHandling,"Backorder cleared",null);
            //log.debug("done with shipOrder");
            cxn.commit();

        }catch(Exception ex)
        {
            cxn.rollback();
            log.debug("ERROR OID:"+orderId);
            ex.printStackTrace();
            if(swapFacility){
                OrderStatus status = new com.owd.core.business.order.OrderStatus(orderId,
                       clientId);
                status.changeLocation(originalFacility);

            }
            //Mailer.postMailMessage("Couldn't clear autoship backorder",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","owditadmin@owd.com");
        }


    }
}

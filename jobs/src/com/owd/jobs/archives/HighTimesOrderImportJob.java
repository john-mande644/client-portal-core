package com.owd.jobs.archives;

import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrderAuto;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.managers.InventoryManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.*;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.MagentoImportOrderCustomizer;
import com.owd.jobs.jobobjects.magento.MagentoSubscriptionCreator;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class HighTimesOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    /**
     * Logic for HT subscriber orders...
     * <p/>
     * Remove items from order matching SUB pattern
     * <p/>
     * Separate three subscription types based on SKU
     * For each subscription type:
     * - calculate next shipment date and SKU for that sub type
     * - if SKU does not exist, create it according to pattern   (reuse with subscription order maker)
     * - Create order with SKUs/quantities/post date for that sub type
     * - Create subscription order with CC info for 2nd ship date  and subsequent
     * <p/>
     * <p/>
     * <p/>
     * So I'll set the MM anniversary to the 10th, say, of the first month of each quarter starting November 2011, and
     * Best Of to the 10th of the first month of each quarter starting in September?
     * HT 3rd of each month
     * HTSUB, BESUB, and MMJSUB
     */

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static List<Integer> BESUBMonthArray = Arrays.asList(8, 11, 2, 5);
    public static List<Integer> MMJSUBMonthArray = Arrays.asList(10, 1, 4, 7);
    public static List<String> SubscriptionSkusList = Arrays.asList("HTSUB", "BESUB", "MMJSUB");

    public static void main(String[] args) throws Exception {
          run();

        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
      //  cal.set(Calendar.DAY_OF_MONTH, 3);
    //    printNextAndSecondDatesForDayAndSub(cal);

      /*  cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 4);
        printNextAndSecondDatesForDayAndSub(cal);


        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        printNextAndSecondDatesForDayAndSub(cal);*/
/*

        log.debug("Next HT Issue: " + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "HTSUB"), "HTSUB"));
        log.debug("Next BE Issue: " + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "BESUB"), "BESUB"));
        log.debug("Next MM Issue: " + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "MMJSUB"), "MMJSUB"));

        Date htdate = getNextAnniversaryDateAfter(cal.getTime(), "HTSUB");
        Date bedate = getNextAnniversaryDateAfter(cal.getTime(), "BESUB");
        Date mmdate = getNextAnniversaryDateAfter(cal.getTime(), "MMJSUB");

        for (int i = 0; i < 10; i++) {
            log.debug("HT Release Date: " + sdf.format(htdate) + " HT SKU: " + getSkuForAnniversaryDate(htdate, "HTSUB"));
            htdate = getNextAnniversaryDateAfter(htdate, "HTSUB");
        }
        for (int i = 0; i < 10; i++) {
            log.debug("BE Release Date: " + sdf.format(bedate) + " BE SKU: " + getSkuForAnniversaryDate(bedate, "BESUB"));
            bedate = getNextAnniversaryDateAfter(bedate, "BESUB");
        }

        for (int i = 0; i < 10; i++) {
            log.debug("MM Release Date: " + sdf.format(mmdate) + " MM SKU: " + getSkuForAnniversaryDate(mmdate, "MMJSUB"));
            mmdate = getNextAnniversaryDateAfter(mmdate, "MMJSUB");
        }*/


    }

    private static void printNextAndSecondDatesForDayAndSub(Calendar cal) throws Exception {
        log.debug("Today:" + sdf.format(cal.getTime()));
        log.debug("Next HT Issue: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "HTSUB")) + " SKU:" + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "HTSUB"), "HTSUB"));
        log.debug("Next Best Of Issue: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "BESUB")) + " SKU:" + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "BESUB"), "BESUB"));
        log.debug("Next MM Issue: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "MMJSUB")) + " SKU:" + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "MMJSUB"), "MMJSUB"));

        log.debug("2nd HT Issue: " + sdf.format(getSecondAnniversaryDateAfter(cal.getTime(), "HTSUB")) + " SKU:" + getSkuForAnniversaryDate(getSecondAnniversaryDateAfter(cal.getTime(), "HTSUB"), "HTSUB"));
        log.debug("2nd Best Of Issue: " + sdf.format(getSecondAnniversaryDateAfter(cal.getTime(), "BESUB")) + " SKU:" + getSkuForAnniversaryDate(getSecondAnniversaryDateAfter(cal.getTime(), "BESUB"), "BESUB"));
        log.debug("2nd MM Issue: " + sdf.format(getSecondAnniversaryDateAfter(cal.getTime(), "MMJSUB")) + " SKU:" + getSkuForAnniversaryDate(getSecondAnniversaryDateAfter(cal.getTime(), "MMJSUB"), "MMJSUB"));
    }

    public void internalExecute() {

        try {
            //step one get a result set of clients from SQLserver
            MagentoRemoteService service = new MagentoRemoteService("http://headshop.hightimes.com/index.php/api" +
                    "", "headshop", "high7172times", 463);//438);
            service.setProcessingOrders(true);
            service.setProcessingOrdersInvoiced(true);
            service.setPendingOrders(false);
            //  service.setPendingOrdersInvoiced(true);
            //  service.setPendingCheckOrdersInvoiced(false);
            service.setOrderCustomizer(getHighTimesImportOrderCustomizer());
            service.setAutoShipManager(getHighTimesAutoShipManager());
            service.importMagentoOrders(100000002, -30);


            updateInventoryLevels(service);

        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }
    }

    public HighTimesAutoShipManager getHighTimesAutoShipManager() {
        return new HighTimesAutoShipManager();
    }

    public HighTimesImportOrderCustomizer getHighTimesImportOrderCustomizer() {
        return new HighTimesImportOrderCustomizer();
    }

    public class HighTimesImportOrderCustomizer extends MagentoImportOrderCustomizer {


        @Override
        public void customizeOrder(MagentoRemoteService service, Order order, MagentoRemoteService.MagentoOrder mOrder) throws Exception {

            Client client = Client.getClientForID(order.clientID + "");

            ClientPolicy policy = client.getClientPolicy();

            Vector cleanedItems = new Vector<LineItem>();
            Map<String, List<LineItem>> subscriptionItemsMap = new TreeMap<String, List<LineItem>>();


            if (order.containsSKU("HTSUB") || order.containsSKU("BESUB") || order.containsSKU("MMJSUB")) {
                for (int i = 0; i < order.skuList.size(); i++) {
                    LineItem line = (LineItem) order.skuList.get(i);
                    if (SubscriptionSkusList.contains(line.client_part_no)) {
                        if (!(subscriptionItemsMap.containsKey(line.client_part_no))) {
                            subscriptionItemsMap.put(line.client_part_no, new ArrayList<LineItem>());
                        }
                        //add to subscription item list
                        subscriptionItemsMap.get(line.client_part_no).add(line);

                        //mark for backorder
                        line.resetQuantities();
                        line.quantity_backordered = line.quantity_request;
                        line.quantity_request = line.quantity_request - line.quantity_backordered;
                        line.force_backorder_quantity = true;
                    } else {
                        cleanedItems.add(line);
                    }
                }

                //ok, cleaned items contains non-sub line items
                //subscriptionItemsMap contains one entry per sub item found, with the value being the list of LineItem objects for that subitem SKU (the key)
                //the subitem marked lines have all been marked to force a backorder for the full quantity. This will force them to backorder if added back to the
                //original order and saved with a partialship backorder policy

                //first, return any non-sub items to the order's list of items
                order.skuList = cleanedItems;
                //  order.order_type=order.order_type+" Mail Order";
                //three possible directions here - all items are subitems, no items are subitems or some items are subitems

                //if no items are subitems, we would never have gotten to this point, so we just need to worry about the subitem scenarios
                if (order.skuList.size() == 0) {
                    // all items in order are subscriptions
                    // if only one sub item type in order GREAT
                    // if multiple, allow first item to ship on this order and then backorder the other 1 or 2 subscription items.
                    if (subscriptionItemsMap.size() == 1) {
                        //all items are subitems, only one subitem ordered.
                        //return all items to order, replace with real SKUs, and release as active order with future hold until date (in case they are already in stock)

                        for (LineItem line : ((List<LineItem>) (subscriptionItemsMap.get(subscriptionItemsMap.keySet().toArray()[0])))) {
                            order.holdUntilDate = getNextAnniversaryDateAfter(Calendar.getInstance().getTime(), line.client_part_no);
                            order.addLineItem(getSkuForAnniversaryDate(order.holdUntilDate, line.client_part_no), "" + (line.quantity_request + line.quantity_backordered), "" + line.sku_price, "0.00", "", "", "");
                            order.noduplicates = false;
                        }
                        //ok, now save normally, we should be good to go
                        subscriptionItemsMap.clear();
                    } else {
                        //OK, we have at least 2, maybe 3 different sub types  that need different orders for different anniversary dates
                        //all are already marked for backorder, but we're going to replace them with the real SKUs as we go.
                        //so loop through, treat the first entry just like the single order
                        //and then generate new orders for the subsequent subscription types.
                        int currentSubIndex = 1;
                        for (String subitem : subscriptionItemsMap.keySet()) {

                            if (currentSubIndex == 1) {
                                //first one
                                for (LineItem line : subscriptionItemsMap.get(subitem)) {
                                    order.holdUntilDate = getNextAnniversaryDateAfter(Calendar.getInstance().getTime(), line.client_part_no);
                                    order.addLineItem(getSkuForAnniversaryDate(order.holdUntilDate, line.client_part_no), "" + (line.quantity_request + line.quantity_backordered), "" + line.sku_price, "0.00", "", "", "");
                                }
                                order.backorder_rule = OrderXMLDoc.kPartialShip;
                                order.noduplicates = false;

                            } else {
                                //second or third - create new orders for each...
                                Order newOrder = policy.createInitializedOrder();
                                order.copyNewOrderVars(newOrder);
                                newOrder.cc_num = order.cc_num;
                                newOrder.cc_exp_mon = order.cc_exp_mon;
                                newOrder.cc_exp_year = order.cc_exp_year;

                                newOrder.total_shipping_cost = 0.00f;
                                newOrder.total_tax_cost = 0.00f;
                                for (LineItem line : subscriptionItemsMap.get(subitem)) {
                                    newOrder.holdUntilDate = getNextAnniversaryDateAfter(Calendar.getInstance().getTime(), line.client_part_no);
                                    newOrder.addLineItem(getSkuForAnniversaryDate(newOrder.holdUntilDate, line.client_part_no), "" + (line.quantity_request + line.quantity_backordered), "" + line.sku_price, "0.00", "", "", "");
                                    //show backordered item in original order
                                    // order.skuList.add(line);
                                }
                                newOrder.recalculateBalance();
                                newOrder.noduplicates = false;
                                //  newOrder.order_type=order.order_type+" Mail Order";

                                policy.saveNewOrder(newOrder, false);

                                String orderNum = newOrder.orderID;
                                log.debug("order id=" + newOrder.orderID);
                                if (orderNum == null || "".equals(orderNum) || "0".equals(orderNum)) {
                                    log.debug("Bad order!!");
                                    throw new Exception("Unable to save subscription order!");
                                } else {
                                    if (service.getOwdClient().getChargeOnShip() == 1) {
                                        service.saveWSJMagentoAuth(newOrder);

                                    }

                                }
                            }

                            currentSubIndex++;
                        }

                    }
                } else {

                    //some items in order are subscriptions. Non-sub items have already been returned to the order. So we need to
                    //add the forced backorder subitem lines to the original order before returning, mark for partialship, and
                    //generate our subitem orders separately
                    for (List<LineItem> lineList : ((Collection<List<LineItem>>) (subscriptionItemsMap.values()))) {
                        for (LineItem line : lineList) {
                            //line.resetQuantities();
                            // return the original lines to the order.
                            //  order.skuList.add(line);
                        }
                    }
                    //set partial ship
                    //   order.backorder_rule= OrderXMLDoc.kPartialShip;
                    order.noduplicates = false;

                    //now process the line items into future orders
                    for (String subitem : subscriptionItemsMap.keySet()) {


                        //second or third - create new orders for each...
                        Order newOrder = policy.createInitializedOrder();
                        order.copyNewOrderVars(newOrder);
                        newOrder.cc_num = order.cc_num;
                        newOrder.cc_exp_mon = order.cc_exp_mon;
                        newOrder.cc_exp_year = order.cc_exp_year;

                        newOrder.total_shipping_cost = 0.00f;
                        newOrder.total_tax_cost = 0.00f;
                        for (LineItem line : subscriptionItemsMap.get(subitem)) {
                            newOrder.holdUntilDate = getNextAnniversaryDateAfter(Calendar.getInstance().getTime(), line.client_part_no);
                            newOrder.addLineItem(getSkuForAnniversaryDate(newOrder.holdUntilDate, line.client_part_no), "" + (line.quantity_request + line.quantity_backordered), "" + line.sku_price, "0.00", "", "", "");
                            //show backordered item in original order
                            //order.skuList.add(line);
                        }
                        newOrder.noduplicates = false;
                        //  newOrder.order_type=order.order_type+" Mail Order";

                        newOrder.recalculateBalance();
                        policy.saveNewOrder(newOrder, false);
                        String orderNum = newOrder.orderID;
                        log.debug("order id=" + newOrder.orderID);
                        if (orderNum == null || "".equals(orderNum) || "0".equals(orderNum)) {
                            log.debug("Bad order!!");
                            throw new Exception("Unable to save subscription order!");
                        } else {
                            if (service.getOwdClient().getChargeOnShip() == 1) {
                                service.saveWSJMagentoAuth(newOrder);

                            }

                        }

                    }


                }


            } else {
                //no subscriptions, nothing special to do here
            }
        }

    }

    public class HighTimesAutoShipManager extends MagentoSubscriptionCreator {

        public String getTrueSKUFromAutoshipSKU(String originalSKU) {

            String autoSKU = originalSKU;

            return autoSKU;
        }

        public void addItemToList(MagentoRemoteService.MagentoOrder mo, Order order, String originalSKU, float originalCost, int qty) throws Exception {
            log.debug("adding autoship item to list " + originalSKU);
            if (isAutoshipSKU(originalSKU)) {

                Calendar firstShipmentDate = Calendar.getInstance();
                firstShipmentDate.add(Calendar.DATE, 1);

                //firstShipmentDate.setTimeInMillis(rootMillis);
                String autoSKU = getTrueSKUFromAutoshipSKU(originalSKU);

                int monthsApart = 1;
                int dayOfMonth = 1;

                firstShipmentDate.setTime(getNextAnniversaryDateAfter(firstShipmentDate.getTime(), originalSKU));
                firstShipmentDate.add(Calendar.DATE, 1);
                firstShipmentDate.setTime(getNextAnniversaryDateAfter(firstShipmentDate.getTime(), originalSKU));

                if (originalSKU.equals("HTSUB")) {
                    monthsApart = 1;
                    dayOfMonth = 3;
                } else if (originalSKU.equals("BESUB")) {

                    monthsApart = 3;
                    //  dayOfMonth = 10;
                } else if (originalSKU.equals("MMJSUB")) {

                    monthsApart = 3;
                    // dayOfMonth = 12;
                }

                Float autoCost = getAutoUnitCost(mo, order, originalSKU, autoSKU, originalCost, qty);

                if (subscriptionMap.get(firstShipmentDate.getTimeInMillis()) != null) {
                    for (SubscriptionItem subitem : subscriptionMap.get(firstShipmentDate.getTimeInMillis())) {

                        if (autoSKU.equals(subitem.sku) && autoCost == subitem.unitCost) {
                            subitem.unitQty += qty;
                            return;
                        }

                    }
                }

                //not returned, so have new item

                SubscriptionItem newitem = new SubscriptionItem();
                newitem.sku = autoSKU;
                newitem.unitCost = autoCost;
                newitem.unitQty = qty;
                newitem.monthsApart = monthsApart;
          //      newitem.dayOfMonth = dayOfMonth;

                if (!(subscriptionMap.keySet().contains(firstShipmentDate.getTimeInMillis()))) {
                    subscriptionMap.put(firstShipmentDate.getTimeInMillis(), new ArrayList<SubscriptionItem>());
                }
                subscriptionMap.get(firstShipmentDate.getTimeInMillis()).add(newitem);
                log.debug("Added item to list: " + subscriptionMap.get(firstShipmentDate.getTimeInMillis()));


            }

        }

        public boolean isAutoshipSKU(String originalSKU) {
            if (SubscriptionSkusList.contains(originalSKU)) {
                return true;
            }

            return false;
        }

        public float getAutoUnitCost(MagentoRemoteService.MagentoOrder mo, Order order, String originalSKU, String autoSKU, float originalCost, int qty) throws Exception {

            return originalCost;

        }


        public float getShipCost(OwdOrderAuto auto, Order o) {


            return 0.00f;

        }

        public void setSalesTaxAmount(OwdOrderAuto auto, Order o) {

            auto.setSalesTax(new BigDecimal(0.00));

        }

        public void addInsertItems(Session sess, OwdOrderAuto auto, Order o) throws Exception {


        }

        public void setShipMethodName(OwdOrderAuto auto, Order o) throws Exception {

            auto.setShipMethod(o.getShippingInfo().carr_service);

        }


    }


    public void updateInventoryLevels(MagentoRemoteService service) throws Exception {


        HashMap<String, Integer> updateMap = new HashMap<String, Integer>();

        List<HashMap<String, String>> storeSkus = service.getAllInventoryInfo();

        HashMap<String, Integer> owdStockMap = new HashMap<String, Integer>();

        ResultSet rs = HibernateSession.getResultSet("select inventory_num,qty_on_hand,is_active from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey=463");

        while (rs.next()) {

            owdStockMap.put(rs.getString(1), (1 == rs.getInt(3) ? rs.getInt(2) : 0));

        }

        for (HashMap<String, String> storeSkuMap : storeSkus) {
            log.debug(storeSkuMap);
            String sku = storeSkuMap.get("sku");
            log.debug("checking SKU " + sku);
            if (owdStockMap.containsKey(sku)) {
                log.debug("getting qty for SKU " + sku);
                updateMap.put(sku, owdStockMap.get(sku));
            } else {
                log.debug("no owd entry found for sku " + sku);
            }

        }


        log.debug("updateMap:" + updateMap);

        service.setMagentoInventoryLevel(updateMap, false);
    }

    public static Date getSecondAnniversaryDateAfter(Date startDate, String Sku) throws Exception {
        Date firstAnnDate = getNextAnniversaryDateAfter(startDate, Sku);
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstAnnDate);
        cal.add(Calendar.DATE, 1);
        return getNextAnniversaryDateAfter(cal.getTime(), Sku);
    }

    public static Date getNextAnniversaryDateAfter(Date startDate, String Sku) throws Exception {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(startDate);
        if (Sku.equals("HTSUB")) {
            if (cal.get(Calendar.DAY_OF_MONTH) < 3) {
                cal.set(Calendar.DAY_OF_MONTH, 3);
            } else {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 3);
            }
        } else if (Sku.equals("BESUB")) {

            //quarterly, starts september 2011 (month 8), so months 8, 11, 2, 5, and back to 8


            if (BESUBMonthArray.contains(cal.get(Calendar.MONTH)) && cal.get(Calendar.DAY_OF_MONTH) < 10) {
                cal.set(Calendar.DAY_OF_MONTH, 10);
            } else if (BESUBMonthArray.contains(cal.get(Calendar.MONTH))) {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 10);
            }

            while (!(BESUBMonthArray.contains(cal.get(Calendar.MONTH)))) {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 10);
            }
        } else if (Sku.equals("MMJSUB")) {
            //quarterly, starts november 2011 (month 10), so months 10, 1, 4, 7, 10, 1, etc.
            if (MMJSUBMonthArray.contains(cal.get(Calendar.MONTH)) && cal.get(Calendar.DAY_OF_MONTH) < 12) {
                cal.set(Calendar.DAY_OF_MONTH, 12);
            } else if (MMJSUBMonthArray.contains(cal.get(Calendar.MONTH))) {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 12);
            }

            while (!(MMJSUBMonthArray.contains(cal.get(Calendar.MONTH)))) {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 12);
            }


        } else {
            throw new Exception("Subscription SKU " + Sku + " not valid");
        }
        return cal.getTime();
    }


    public static String getSkuForAnniversaryDate(Date annDate, String subSku) throws Exception {
        String newSku = null;

        Calendar htcal = Calendar.getInstance();
        htcal.set(Calendar.MONTH, 8);
        htcal.set(Calendar.YEAR, 2011);
        htcal.set(Calendar.DAY_OF_MONTH, 3);

        Calendar becal = Calendar.getInstance();
        becal.set(Calendar.MONTH, 8);
        becal.set(Calendar.YEAR, 2011);
        becal.set(Calendar.DAY_OF_MONTH, 10);

        Calendar mmjcal = Calendar.getInstance();
        mmjcal.set(Calendar.MONTH, 7);
        mmjcal.set(Calendar.YEAR, 2011);
        mmjcal.set(Calendar.DAY_OF_MONTH, 12);

        int beNum = (62 + (getMonthsDifference(becal.getTime(), annDate)) / 3);
        int htNum = (429 + getMonthsDifference(htcal.getTime(), annDate));
        int mmNum = (7 + (getMonthsDifference(becal.getTime(), annDate)) / 3);
        if (subSku.equals("HTSUB")) {
            //count months
            newSku = "" + htNum;

        } else if (subSku.equals("BESUB")) {

            //quarterly, starts september 2011 (month 8), so months 8, 11, 2, 5, and back to 8

            newSku = "BE" + beNum;


        } else if (subSku.equals("MMJSUB")) {
            //quarterly, starts november 2011 (month 10), so months 10, 1, 4, 7, 10, 1, etc.
            newSku = "MM#" + mmNum;


        } else {
            throw new Exception("Subscription SKU " + subSku + " not valid");
        }
        if (newSku == null) {
            throw new Exception("Future SKU for " + subSku + " and date " + sdf.format(annDate) + " not valid");
        }

        if (!(LineItem.SKUExists("463", newSku))) {
            OwdInventory item = InventoryManager.getInitializedOwdInventory(463);
            item.setInventoryNum(newSku);
            if (subSku.equals("HTSUB")) {
                //count months
                item.setDescription("HIGH TIMES #" + htNum);
            } else if (subSku.equals("BESUB")) {

                //quarterly, starts september 2011 (month 8), so months 8, 11, 2, 5, and back to 8

                item.setDescription("Best of HIGH TIMES #" + beNum);
            } else if (subSku.equals("MMJSUB")) {
                //quarterly, starts november 2011 (month 10), so months 10, 1, 4, 7, 10, 1, etc.
                item.setDescription("Medical Marijuana #" + mmNum);
            }

            item.setModifiedBy("Order Importer");
            item.setNotes("");
            item.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, 463));
            item.setWeightLbs(0.45f);
            HibernateSession.currentSession().save(item);
            HibernateSession.currentSession().save(item.getOwdInventoryOh());
            HibernateSession.currentSession().save(item.getPackingInstructions());
            HibernateSession.currentSession().flush();
            //commit?
            HibUtils.commit(HibernateSession.currentSession());
        }
        return newSku;
    }

    public static final int getMonthsDifference(Date date1, Date date2) {
        int m1 = date1.getYear() * 12 + date1.getMonth();
        int m2 = date2.getYear() * 12 + date2.getMonth();
        return m2 - m1 + 1;
    }

}

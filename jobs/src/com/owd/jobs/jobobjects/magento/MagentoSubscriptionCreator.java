package com.owd.jobs.jobobjects.magento;

import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.autoship.AutoShipFactory;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.Order;
import com.owd.hibernate.*;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 4/3/11
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MagentoSubscriptionCreator {
private final static Logger log =  LogManager.getLogger();


    public boolean isAutoshipSKU(String originalSKU) {
        if (originalSKU.endsWith("-30D") || originalSKU.endsWith("-60D") || originalSKU.endsWith("-90D")) {
            return true;
        }

        return false;
    }

    public String getTrueSKUFromAutoshipSKU(String originalSKU) {

        String autoSKU = originalSKU;

        if (originalSKU.endsWith("-30D") || originalSKU.endsWith("-60D") || originalSKU.endsWith("-90D")) {
            if (originalSKU.endsWith("-30D")) {
                autoSKU = autoSKU.replaceAll("-30D", "");
            } else if (originalSKU.endsWith("-60D")) {

                autoSKU = autoSKU.replaceAll("-60D", "");
            } else if (originalSKU.endsWith("-90D")) {

                autoSKU = autoSKU.replaceAll("-90D", "");
            }
        }

        return autoSKU;
    }


    Long rootMillis = Calendar.getInstance().getTimeInMillis();
    public Map<Long, List<SubscriptionItem>> subscriptionMap = new TreeMap<Long, List<SubscriptionItem>>();


    public abstract float getAutoUnitCost(MagentoRemoteService.MagentoOrder mo, Order order, String originalSKU, String autoSKU, float originalCost, int qty) throws Exception;

    public void addItemToList(MagentoRemoteService.MagentoOrder mo, Order order, String originalSKU, float originalCost, int qty) throws Exception {
        log.debug("adding autoship item to list " + originalSKU);
        if (isAutoshipSKU(originalSKU)) {

            Calendar firstShipmentDate = Calendar.getInstance();
            firstShipmentDate.setTimeInMillis(rootMillis);
            String autoSKU = getTrueSKUFromAutoshipSKU(originalSKU);

            int monthsApart = 1;
            if (originalSKU.endsWith("-30D")) {
                firstShipmentDate.add(Calendar.MONTH, 1);
                autoSKU = autoSKU.replaceAll("-30D", "");
            } else if (originalSKU.endsWith("-60D")) {

                firstShipmentDate.add(Calendar.MONTH, 2);
                autoSKU = autoSKU.replaceAll("-60D", "");
                monthsApart=2;
            } else if (originalSKU.endsWith("-90D")) {

                firstShipmentDate.add(Calendar.MONTH, 3);
                autoSKU = autoSKU.replaceAll("-90D", "");
                monthsApart=3;
            }

            Float autoCost = 0.00f;

            //dr cherry
            if(originalSKU.equals("MMS3MO-30D"))
            {
                firstShipmentDate.add(Calendar.DATE,45);
                autoSKU =  "MMS60";
                qty=1;
                monthsApart=1;
                autoCost=32.3600f;
            }

            else{
                autoCost = getAutoUnitCost(mo, order, originalSKU, autoSKU, originalCost, qty);
            }

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

            if (!(subscriptionMap.keySet().contains(firstShipmentDate.getTimeInMillis()))) {
                subscriptionMap.put(firstShipmentDate.getTimeInMillis(), new ArrayList<SubscriptionItem>());
            }
            subscriptionMap.get(firstShipmentDate.getTimeInMillis()).add(newitem);
            log.debug("Added item to list: " + subscriptionMap.get(firstShipmentDate.getTimeInMillis()));


        }

    }


    public String getCampaignName(Order order) {
        return order.order_type;
    }


    public abstract float getShipCost(OwdOrderAuto auto, Order o);


    public class SubscriptionItem {
        public String sku = "";
        public float unitCost = 0.00f;
        public int unitQty = 1;
        public int monthsApart =1;
        public int dayOfMonth = -1;

    }

    public void clearSubscriptionMap()
    {
         subscriptionMap = new TreeMap<Long, List<SubscriptionItem>>();
    }
    private boolean completed = false;

    public synchronized void createSubscription(Order o) throws Exception {
       // if (completed) throw new Exception("Subscription already created");


        for (Long time : subscriptionMap.keySet()) {

            Calendar currentNextShipmentCal = Calendar.getInstance();
            currentNextShipmentCal.setTimeInMillis(time);

            Session sess = HibernateSession.currentSession();
            OwdOrderAuto auto = AutoShipFactory.getNewAutoShip(Integer.parseInt(o.clientID));
            auto.setBillName(o.getBillingContact().getName());
            auto.setBillPhone(o.getBillingContact().phone);
            auto.setBillEmail(o.getBillingContact().email);
            auto.setBillAddressOne(o.getBillingAddress().address_one);
            auto.setBillAddressTwo(o.getBillingAddress().address_two);
            auto.setBillCity(o.getBillingAddress().city);
            auto.setBillState(o.getBillingAddress().state);
            auto.setBillZip(o.getBillingAddress().zip);
            auto.setBillCountry(o.getBillingAddress().country);

            auto.setShipName(o.getShippingContact().getName());
            auto.setShipPhone(o.getShippingContact().phone);
            auto.setShipEmail(o.getShippingContact().email);
            auto.setShipAddressOne(o.getShippingAddress().address_one);
            auto.setShipAddressTwo(o.getShippingAddress().address_two);
            auto.setShipCity(o.getShippingAddress().city);
            auto.setShipState(o.getShippingAddress().state);
            auto.setShipZip(o.getShippingAddress().zip);
            auto.setShipCountry(o.getShippingAddress().country);

            // auto.setShipMethod(o.getShippingInfo().carr_service);
            if(subscriptionMap.get(time).get(0).dayOfMonth>0)
            {
                auto.setShipInterval( subscriptionMap.get(time).get(0).dayOfMonth);
            }   else
            {
                auto.setShipInterval( subscriptionMap.get(time).get(0).monthsApart);
            }

            auto.setRequirePayment(new Integer(1));
            auto.setShipCost(new BigDecimal(getShipCost(auto, o)));

            auto.setCreatedBy(getCampaignName(o));
            auto.setCreated(Calendar.getInstance().getTime());
            auto.setStatus(new Integer(0));
            auto.setClientFkey(Integer.decode(o.clientID));
             if(subscriptionMap.get(time).get(0).dayOfMonth>0)
            {
            auto.setCalendarUnit("day of month");
            }else{
                 auto.setCalendarUnit("month");
             }
            OwdOrder source = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(o.getID()));
            if (source != null) {

                auto.getSourceOrders().add(source);
            }
            // Calendar expected = Calendar.getInstance();

            // expected.setTime(os.post_date);
            //   expected.add(Calendar.MONTH, 1);
            auto.setNextShipmentDate(currentNextShipmentCal.getTime());
            auto.setOrderSource(o.order_type);
            auto.setCcNum(o.cc_num);
            auto.setCcExpMon(new Integer(o.cc_exp_mon));
            auto.setCcExpYear(new Integer(o.cc_exp_year));
            sess.saveOrUpdate(auto);

            //    float subtotal = 0.00f;
            //    int units = 0;

            List<SubscriptionItem> subscriptionItems = subscriptionMap.get(time);

            for (int i = 0; i < subscriptionItems.size(); i++) {
                SubscriptionItem item = subscriptionItems.get(i);


                OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
                autoitem.setSku(item.sku);
                autoitem.setUnitPrice(new BigDecimal(item.unitCost));
                autoitem.setQuantity(item.unitQty);

                //     units += item.unitQty;
                //     subtotal += (item.unitCost * item.unitQty);

                autoitem.setOwdOrderAuto(auto);
                auto.getOwdOrderAutoItems().add(autoitem);
                sess.save(autoitem);

            }


            setSalesTaxAmount(auto, o);
            setShipMethodName(auto, o);
            addInsertItems(sess, auto, o);


            sess.saveOrUpdate(auto);
            sess.flush();
            HibUtils.commit(sess);
        }
        //  saveOrUpdateAutoShip(auto, "New subscription  created from import file " + "\r\n");
        //  log.debug("Created new subscription!   Status is " + AutoShipManager.getAutoShipStatus("" + auto.getStatus()));")
        completed = true;

    }

    public boolean containsSKU(OwdOrderAuto auto, String sku) {
        for (OwdOrderAutoItem item : auto.getOwdOrderAutoItems()) {
            if (item.getSku().equals(sku)) {
                return true;
            }
        }
        return false;
    }

    public float getPredictedWeight(OwdOrderAuto auto) throws Exception {
        float weight = 0.00f;

        for (OwdOrderAutoItem item : auto.getOwdOrderAutoItems()) {
            Inventory i = Inventory.dbloadByPart(item.getSku(), "" + auto.getClientFkey());
            weight += i.weight_lbs*((float)item.getQuantity());
        }
        return weight;
    }

    public abstract void setSalesTaxAmount(OwdOrderAuto auto, Order o);

    public abstract void addInsertItems(Session sess, OwdOrderAuto auto, Order o) throws Exception;

    public void setShipMethodName(OwdOrderAuto auto, Order o) throws Exception {
        if (o.getShippingAddress().isInternational()) {
            if (getPredictedWeight(auto) >= 3.75) {
                auto.setShipMethod("USPS Priority Mail International");
            } else {
                auto.setShipMethod("USPS First-Class Mail International");
            }
        } else {
            if (getPredictedWeight(auto) >= 0.75) {
                auto.setShipMethod("USPS Priority Mail");
            } else {
                auto.setShipMethod("USPS First-Class Mail");
            }
        }
    }


   /* public void addInsertItemIfAvailable(Session sess, OwdOrderAuto auto, String sku, int quantity) throws Exception {
        Inventory i = Inventory.dbloadByPart(sku, "" + auto.getClientFkey());
        if (OrderUtilities.getAvailableInventory("" + i.inventory_id,, FacilitiesManager.getFacilityForCode(status.shipLocation).getId()) > (quantity + 1)) {

            //  item.insertedItem = true;


            OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
            autoitem.setSku(sku);
            // autoitem.
            autoitem.setUnitPrice(new BigDecimal(0.00));
            autoitem.setQuantity(quantity);
            autoitem.setOwdOrderAuto(auto);
            auto.getOwdOrderAutoItems().add(autoitem);
            sess.save(autoitem);


        }
    }
*/
    public float getSubtotal(OwdOrderAuto auto) {
        float subtotal = 0.00f;
        for (OwdOrderAutoItem item : auto.getOwdOrderAutoItems()) {
            subtotal += (Float.parseFloat("" + item.getQuantity())) * ((Float.parseFloat("" + item.getUnitPrice())));
        }
        return OWDUtilities.roundFloat(subtotal);
    }


    /* public void setVariableFirstShipmentDate(String partNum) {
        //sets non-standard first shipment date based on customized part number
        //looks for part numbers ending in Dnn, where nn is the number of days until first shipment
        //example: G4CONTD60 means start 60 days from today

        try {
            int start = partNum.lastIndexOf("D");
            if (start > 0) {
                String digits = partNum.substring(start + 1);
                if (digits.length() > 0) {
                    int days = new Integer(digits).intValue();
                    if (days > 0) {
                        Calendar newcal = Calendar.getInstance();
                        newcal.add(Calendar.DATE, days);


                        setFirstShipmentDate(newcal);
                    }
                }

            }


        } catch (Exception ex) {
            //if not formatted correctly, do nothing
        }

    }*/

    public static void main(String[] args) {
        //LifeSpanSubscription sub = new LifeSpanSubscription();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        log.debug("G4CONTD1");
        //  sub.setVariableFirstShipmentDate("G4CONTD1");
        //  log.debug("First ship date:" + df.format(sub.getFirstShipmentDate().getTime()));
    }
}

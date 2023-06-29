package com.owd.core.business.autoship;

import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Client;
import com.owd.core.business.Contact;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.InventoryManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoShipManager {
private final static Logger log =  LogManager.getLogger();


    public static Map autoShipStatusMap = new TreeMap();


    static {
        autoShipStatusMap.put("0", "Active");
        autoShipStatusMap.put("1", "Suspended");
        autoShipStatusMap.put("2", "Cancelled");
        autoShipStatusMap.put("3", "Complete");

    }

/*

    public static List createLifeStylesSubscriptions() {
        List results = new ArrayList();

        try {
            Session sess = HibernateSession.currentSession();
            ResultSet rs = HibernateSession.getResultSet("select order_id from owd_order (NOLOCK) join owd_line_item on order_id=order_fkey \n" +
                    "                    and inventory_num like '%KITITEM_CONT%' where post_date > '2004-1-1' and client_fkey=172 and is_void=0 and is_backorder=0 and is_future_ship=0\n" +
                    "and order_id not in (select orig_order_id from owd_order_auto)\n" +
                    "                    group by order_id,post_date");
            Vector osVector = new Vector();

            while (rs.next()) {
                try {
                    //log.debug("got order id " + rs.getString(1));

                    int orderID = rs.getInt(1);
                    //osVector = OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderID, rs.getString(1), "172");

                    //  if (osVector.size() != 1) throw new Exception("Error looking up order !");

                    //add to list of confirmed orders
                    OrderStatus os = new OrderStatus( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), rs.getString(1));


                    results.add("\r\nFound possible subscription order : (Ref:" + os.orderReference + ")\r\n");

                    //log.debug("got ship address 1 = " + os.shipping.shipAddress.address_one);

                    //log.debug("got first bill address 1 = " + os.billAddress.address_one);


                    OwdOrderAuto auto = AutoShipFactory.getNewAutoShip(172);
                    auto.setBillName(os.billContact.name.length() > 0 ? os.billContact.name : os.billAddress.company_name);
                    auto.setBillPhone(os.billContact.phone);
                    auto.setBillEmail(os.billContact.email);
                    auto.setBillAddressOne(os.billAddress.address_one);
                    auto.setBillAddressTwo(os.billAddress.address_two);
                    auto.setBillCity(os.billAddress.city);
                    auto.setBillState(os.billAddress.state);
                    auto.setBillZip(os.billAddress.zip);
                    auto.setBillCountry(os.billAddress.country);

                    //log.debug("got ship address 1 = " + os.shipping.shipAddress.address_one);


                    auto.setShipName(os.shipping.shipContact.name.length() > 0 ? os.shipping.shipContact.name : os.shipping.shipAddress.company_name);
                    auto.setShipPhone(os.shipping.shipContact.phone);
                    auto.setShipEmail(os.shipping.shipContact.email);
                    auto.setShipAddressOne(os.shipping.shipAddress.address_one);
                    auto.setShipAddressTwo(os.shipping.shipAddress.address_two);
                    auto.setShipCity(os.shipping.shipAddress.city);
                    auto.setShipState(os.shipping.shipAddress.state);
                    auto.setShipZip(os.shipping.shipAddress.zip);
                    auto.setShipCountry(os.shipping.shipAddress.country);

                    auto.setShipMethod("UPS Ground");
                    auto.setShipInterval(new Integer(1));
                    auto.setRequirePayment(new Integer(1));
                    auto.setShipCost(new BigDecimal(6.85));
                    auto.setSalesTax(new BigDecimal(0.00));
                    auto.setCreatedBy("System");
                    auto.setCreated(Calendar.getInstance().getTime());
                    auto.setStatus(new Integer(0));
                    auto.setClientFkey(new Integer(172));
                    auto.setCalendarUnit("month");
                    auto.setOrigOrderId(new Integer(orderID));

                    Calendar expected = Calendar.getInstance();
                    expected.setTime(os.post_date);
                    expected.add(Calendar.MONTH, 1);
                    auto.setNextShipmentDate(expected.getTime());


                    sess.saveOrUpdate(auto);
                    Vector items = os.items;

                    for (int i = 0; i < items.size(); i++) {
                        LineItem item = (LineItem) items.elementAt(i);
                        if (item.quantity_actual > 0 && item.client_part_no.equals("KITITEM_CONTXLA")) {
                            OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
                            autoitem.setSku("LS100");
                            autoitem.setUnitPrice(new BigDecimal(29.99));
                            autoitem.setQuantity((int) item.quantity_actual);
                            autoitem.setOwdOrderAuto(auto);
                            auto.getOwdOrderAutoItems().add(autoitem);
                            sess.save(autoitem);


                        } else if (item.quantity_actual > 0 && item.client_part_no.equals("KITITEM_CONTXLASPORT")) {
                            OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
                            autoitem.setSku("LS101SP");
                            autoitem.setUnitPrice(new BigDecimal(29.99));
                            autoitem.setQuantity((int) item.quantity_actual);
                            autoitem.setOwdOrderAuto(auto);
                            auto.getOwdOrderAutoItems().add(autoitem);
                            sess.save(autoitem);
                        }
                    }

                    List transactions = os.transactions;

                    for (int i = 0; i < transactions.size(); i++) {
                        FinancialTransaction item = (FinancialTransaction) transactions.get(i);
                        auto.setCcNum(item.cc_number);
                        auto.setCcExpMon(new Integer(item.cc_exp.substring(0, 2)));
                        auto.setCcExpYear(new Integer(Integer.parseInt(item.cc_exp.substring(2)) + 2000));

                    }

                    saveOrUpdateAutoShip(auto, "New subscription created from cart order" + "\r\n");
                    results.add("    Result: Created new subscription! Status is " + AutoShipManager.getAutoShipStatus("" + auto.getStatus()));
                } catch (Exception ex) {
                    results.add("    Result: Error processing order : " + ex.getMessage() + "\r\n");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return results;
    }
*/

    public static String getAutoShipStatus(String integerKeyString) {
        return (String) autoShipStatusMap.get(integerKeyString);
    }


    public static void saveOrUpdateAutoShip(OwdOrderAuto autos, String message) throws Exception {

        if (autos.getOwdOrderAutoItems().size() < 1) {
            //  throw new Exception("At least one item must be added to a subscription before saving! Changes not saved.");
        }


        Session sess = HibernateSession.currentSession();
        sess.saveOrUpdate(autos);
        //log.debug("updating items");
        Collection alist = autos.getOwdOrderAutoItems();
        //log.debug("currently have " + alist.size() + " items");

        /* autos.getOwdOrderAutoItems().clear();
         sess.flush();
         autos.setOwdOrderAutoItems(new ArrayList());
         autos.getOwdOrderAutoItems().addAll(alist);
         sess.flush();*/


        Iterator it = autos.getOwdOrderAutoItems().iterator();
        while (it.hasNext()) {
            OwdOrderAutoItem item = (OwdOrderAutoItem) it.next();
            //  item.setOwdOrderAuto(autos);
            // autos.getOwdOrderAutoItems().remove(item);

            sess.saveOrUpdate(item);
            if (item.getQuantity() == 0) {
                sess.saveOrUpdate(item);
                sess.delete(item);
            }
        }


        //log.debug("updating history");
        if (message != null) {
            OwdOrderAutoHistory autoh = new OwdOrderAutoHistory();
            autoh.setCreated(Calendar.getInstance().getTime());
            autoh.setType(1);
            autoh.setMessage(message);
            autos.getOwdOrderAutoHistories().add(autoh);
            autoh.setOwdOrderAuto(autos);

        }
        Iterator ith = autos.getOwdOrderAutoHistories().iterator();
        int i = 1;
        while (ith.hasNext()) {
            //  //log.debug("updating history item "+i);
            OwdOrderAutoHistory item = (OwdOrderAutoHistory) ith.next();
            //item.setOwdOrderAuto(autos);
            //autos.getOwdOrderAutoHistories().remove(item);
            //autos.getOwdOrderAutoHistories().add(item);
            sess.saveOrUpdate(item);
            i++;
        }

        sess.flush();
        HibUtils.commit(sess);
    }


    public static void deleteAutoShip(OwdOrderAuto autosObj) throws Exception {

        OwdOrderAuto autos = (OwdOrderAuto) HibernateSession.currentSession().load(OwdOrderAuto.class, autosObj.getAutoShipId());


        HibernateSession.currentSession().createQuery("delete from OwdOrderAutoItem  where autoship_fkey = " + autos.getAutoShipId()).executeUpdate();
        HibernateSession.currentSession().createQuery("delete from OwdOrderAutoHistory  where order_auto_fkey = " + autos.getAutoShipId()).executeUpdate();

        HibernateSession.currentSession().delete(autos);

        HibUtils.commit(HibernateSession.currentSession());
    }


    public static String retryDeclinedSubscriptionCCs(OwdOrderAuto auto, OwdOrder order, int autoshipOrderId) throws Exception {

        String response = "";


        //check for manually released or voided orders
        if (order.getIsVoid() == 1) {
            //if voided, set subscription to suspended and link status to VOID
            String clearAutoStatusSQL = "update owd_order_auto_order set status='VOID' where id=" + autoshipOrderId;
            HibernateSession.getPreparedStatement(clearAutoStatusSQL).executeUpdate();


            response = "Order " + order.getOrderRefnum() + "(" + order.getBillFirstName() + " " + order.getBillLastName() + ") was voided manually - autoship schedule and status not changed.";


        } else if (order.getPostDate() != null) {
            //if released, set subscription to active and link status to OK
            String clearAutoStatusSQL = "update owd_order_auto_order set status='OK' where id=" + autoshipOrderId;

            HibernateSession.getPreparedStatement(clearAutoStatusSQL).executeUpdate();


            response = "Order " + order.getOrderRefnum() + "(" + order.getBillFirstName() + " " + order.getBillLastName() + ") was shipped manually - autoship schedule and status not changed.";

        } else if (auto.getStatus() != 0 && auto.getStatus() != 3) {


            //check for cancelled subscriptions

            //if cancelled, void order and set status to CANCEL
            String clearAutoStatusSQL = "update owd_order_auto_order set status='CANCEL' where id=" + autoshipOrderId;
            String voidOrderSQL = "exec void_order " + order.getOrderId();
            HibernateSession.getPreparedStatement(clearAutoStatusSQL).executeUpdate();
            HibernateSession.getPreparedStatement(voidOrderSQL).executeUpdate();


            response = "Subscription for order " + order.getOrderRefnum() + "(" + order.getBillFirstName() + " " + order.getBillLastName() + ") was cancelled or suspended - order voided.";


        } else if (order.getCcType().equals("ECK")) {

            response = "Order " + order.getOrderRefnum() + "(" + order.getBillFirstName() + " " + order.getBillLastName() + ") is an echeck payment method and cannot be automatically retried.";

        } else {
            //retry transaction
            String error = "";
            try {
                FinancialTransaction ft = new FinancialTransaction("0", order.getOrderId() + "", "0", "", "", "", "",
                        getOrderTotal(auto), "", FinancialTransaction.TRANS_NEW,
                        FinancialTransaction.TRANS_CC + "", 0, 0, "-1",
                        OWDUtilities.getLastNameFromWholeName(auto.getBillName()),
                        OWDUtilities.getFirstNameFromWholeName(auto.getBillName()),
                        auto.getBillAddressOne(), "",
                        auto.getBillZip(), "",
                        "", "", "", "", "",
                        order.getOrderNum(), "0", "0", "", "", "", "", "",
                        OWDUtilities.getExpDateStr(auto.getCcExpMon().intValue(), auto.getCcExpYear().intValue()), auto.getCcNum(),
                        "", "", "", "", "", "", 0, 0, OWDUtilities.getSQLDateTimeForToday());


                if (Client.isChargeOnShip(order.getClientFkey())) {
                    ft.amount = 1.00f;
                    ft.authorizeCC(Client.getClientForID( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), order.getClientFkey() + ""), false);
                } else {
                    ft.chargeCC(Client.getClientForID( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), order.getClientFkey() + ""), false);
                }


                //    ft.chargeCC(Client.getClientForID( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),order.getClientFkey()+""),false);


                error = ft.error_reponse;


                ft.dbsave( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());


                //  HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());

                if (ft.getStatus().equals(ft.TRANS_OK)) {
                    order.setPaymentStatus(OrderXMLDoc.kPaymentStatusPaid);
                    order.setPaidDate(Calendar.getInstance().getTime());
                    order.setPaidAmount(new BigDecimal(ft.amount));
                    order.setOrderBalance(new BigDecimal(0.00));

                    String backorderNum = "";
                    backorderNum = OrderUtilities.shipExistingHibernateOrder(order);
                    if (backorderNum == null) backorderNum = "";
                    //log.debug("1");
                    String markautoorderOKSQL = "update owd_order_auto_order set status='OK' where id=" + autoshipOrderId;
                    //log.debug("2");
                    HibernateSession.getPreparedStatement(markautoorderOKSQL).executeUpdate();
                    //log.debug("3");
                    response = "CC retry successful for order " + order.getOrderRefnum() + "(" + order.getBillFirstName() + " " + order.getBillLastName() + ") was successful - order released for shipping.";
                    //log.debug("4");
                    if (backorderNum.length() > 5) {
                        response = response + " - Backorder created as OWD reference: " + backorderNum;
                    }
                    //log.debug("5");
                } else {
                    //failed CC transaction
                    //log.debug("6");
                    String recordAttemptSQL = "update owd_order_auto_order set retries=retries+1 where id=" + autoshipOrderId;
                    //log.debug("7");
                    HibernateSession.getPreparedStatement(recordAttemptSQL).executeUpdate();
                    //log.debug("8");
                    response = "CC retry failed for order " + order.getOrderRefnum() + "(" + order.getBillFirstName() + " " + order.getBillLastName() + ") . Error: " + error;

                }
                //log.debug("9");
                //  HibernateSession.currentSession().flush();
                //log.debug("10");
                HibUtils.commit(HibernateSession.currentSession());


            } catch (Exception ex) {
                ex.printStackTrace();
                HibUtils.rollback(HibernateSession.currentSession());
                throw ex;

            } finally {

                // HibernateSession.closeSession();

            }


        }

        //retry
        //log.debug("11");
        saveOrUpdateAutoShip(auto, response);
        //log.debug("12");
        HibernateSession.currentSession().saveOrUpdate(order);

        //   HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
        //log.debug("13");

        return response;
    }

    public static long getdaysSinceGivenDate(Calendar calpast) {

        Calendar cal1 = Calendar.getInstance();

        long checkmillis = calpast.getTimeInMillis();
        long nowmillis = cal1.getTimeInMillis();
        if (checkmillis > nowmillis) {
            return -1;
        }

        long diff = nowmillis - checkmillis;
        if (diff < (24 * 60 * 60 * 1000)) {
            return 0;
        } else {

            long diffDays = diff / (24 * 60 * 60 * 1000);
            return diffDays;
        }

    }

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static List<Integer> BESUBMonthArray = Arrays.asList(8, 11, 2, 5);
    public static List<Integer> MMJSUBMonthArray = Arrays.asList(10, 1, 4, 7);
    public static List<String> HighTimesSubscriptionSkusList = Arrays.asList("HTSUB", "BESUB", "MMJSUB");


    public static String shipSubscriptionOrder(OwdOrderAuto autos) throws Exception {

        Calendar calNow = Calendar.getInstance(); //now
        String reference = null;
        String message = "";
        try {
            Order order = new Order(autos.getClientFkey() + "");

            Iterator it = autos.getOwdOrderAutoItems().iterator();
            while (it.hasNext()) {
                OwdOrderAutoItem item = (OwdOrderAutoItem) it.next();
                if (autos.getClientFkey() == 463 && HighTimesSubscriptionSkusList.contains(item.getSku())) {
                    String newSku = getSkuForAnniversaryDate(calNow.getTime(), item.getSku());
                    order.addLineItem(newSku, item.getQuantity(), item.getUnitPrice().floatValue(), ((float) (item.getQuantity() * item.getUnitPrice().doubleValue())), "", "", "");

                } else {

                    order.addLineItem(item.getSku(), item.getQuantity(), item.getUnitPrice().floatValue(), ((float) (item.getQuantity() * item.getUnitPrice().doubleValue())), "", "", "");
                }
            }

            if ("320".equals(order.clientID)) {

                if (!(order.containsSKU("CBCoupon"))) {
                    order.addInsertItemIfAvailable("CBCoupon", 1);
                }
            }


            if ("402".equals(order.clientID)) {


/*
                    Calendar startcal = Calendar.getInstance();
                    Calendar endcal = Calendar.getInstance();

                    startcal.set(2013, Calendar.MARCH, 7, 0, 0, 0);   //nov 6 2010 per case 112125
                    endcal.set(2013, Calendar.JUNE, 7, 0, 0, 0);   // per case 198618


                    long daysSince = getdaysSinceGivenDate(startcal);
                    long daysBefore = getdaysSinceGivenDate(endcal);

                    if (daysSince >= 0 && daysBefore<=0) {
                                           order.deleteLineItemForSKU("DRCMAG-Fall2011");
                                           order.addInsertItemIfAvailable("DRCMAG-Fall2011", 1);
                        order.deleteLineItemForSKU("DRCMAG-Spring2013");
                        order.addInsertItemIfAvailable("DRCMAG-Spring2013", 1);

                                       }*/
              /*  Calendar endcal = Calendar.getInstance();


                endcal.set(2014, Calendar.JANUARY, 9, 0, 0, 0);   // per case 198618
                if (endcal.getTime().getTime() > Calendar.getInstance().getTime().getTime()) {

                    order.deleteLineItemForSKU("DRCMAG-2013/2014");
                    order.addInsertItemIfAvailable("DRCMAG-2013/2014", 1);
                }*/

                if (order.containsSKU("MMS60")) {
                    order.deleteLineItemForSKU("ANN-MMS");
                    order.addInsertItemIfAvailable("ANN-MMS", 1);
                }


                if (order.containsSKU("BSS120")) {
                    order.deleteLineItemForSKU("BSSANN");
                    order.addInsertItemIfAvailable("BSSANN", 1);
                }

                if (order.containsSKU("VS60")) {
                    order.deleteLineItemForSKU("ANN-VS60");
                    order.addInsertItemIfAvailable("ANN-VS60", 1);
                }

                // ANN-HTDT  to each order containing JS120 and IS120
                if (order.containsSKU("JS120") || order.containsSKU("IS120")) {
                    order.deleteLineItemForSKU("ANN-HTDT");
                    order.addInsertItemIfAvailable("ANN-HTDT", 1);
                }


                if (order.containsSKU("BNS60")) {
                    order.deleteLineItemForSKU("BB-WBNS");
                    order.addInsertItemIfAvailable("BB-WBNS", 1);
                }

                if (!(order.containsSKU("BNS60"))) {
                    order.deleteLineItemForSKU("BB-WOBN2");
                    order.addInsertItemIfAvailable("BB-WOBN2", 1);
                }





                order.deleteLineItemForSKU("BB-SSPS");
                order.addInsertItemIfAvailable("BB-SSPS", 1);

             //   order.deleteLineItemForSKU("BB-14PTH");
            //    order.addInsertItemIfAvailable("BB-14PTH", 1);


            }

            order.getShippingInfo().setShipOptions(autos.getShipMethod(), "Prepaid", "");
            order.ship_operator = "Subscription Autoship";
            order.is_auto_ship = true;
            order.total_shipping_cost = autos.getShipCost().floatValue();
            order.total_tax_cost = autos.getSalesTax().floatValue();
            order.processCouponDiscount = false;
            order.coupon_code = autos.getOriginalCoupon();

            if (autos.getFop() == FinancialTransaction.TRANS_ECK) {
                order.bill_cc_type = "ECK";

                order.ck_acct = autos.getCkAcct();
                order.ck_bank = autos.getCkBank();
                order.ck_number = autos.getCkNumber();

            } else {
                order.cc_num = autos.getCcNum();
                order.cc_exp_mon = autos.getCcExpMon().intValue();
                order.cc_exp_year = autos.getCcExpYear().intValue();

                order.bill_cc_type = "CC";
            }

            order.setBillingContact(new Contact(autos.getBillName(), autos.getBillPhone(), "", autos.getBillEmail(), ""));
            order.getShippingInfo().setShippingContact(new Contact(autos.getShipName(), autos.getShipPhone(), "", autos.getShipEmail(), ""));
            String billCompany = "";
            String shipCompany = "";
            if(null!=autos.getBillCompany()) billCompany =  autos.getBillCompany();
            if(null != autos.getShipCompany()) shipCompany = autos.getShipCompany();
            order.setBillingAddress(new Address(billCompany, autos.getBillAddressOne(), autos.getBillAddressTwo(), autos.getBillCity(), autos.getBillState(), autos.getBillZip(), autos.getBillCountry()));
            order.getShippingInfo().setShippingAddress(new Address(shipCompany, autos.getShipAddressOne(), autos.getShipAddressTwo(), autos.getShipCity(), autos.getShipState(), autos.getShipZip(), autos.getShipCountry()));
            order.order_type = autos.getOrderSource();

            order.payment_status = com.owd.core.xml.OrderXMLDoc.kPaymentStatusWaitingForPayment;

            order.recalculateBalance();

            //order.payment_status = "";
            if (autos.getClientFkey() == 401 || autos.getClientFkey() == 402) {
                order.backorder_rule = com.owd.core.xml.OrderXMLDoc.kPartialShip;
            } else {
                order.backorder_rule = com.owd.core.xml.OrderXMLDoc.kBackOrderAll;
            }

            if ("463".equals(order.clientID)) {
                OrderRater rater = new OrderRater(order);
                rater.setClientId(order.getClientID());

                List<String> methodCodes = new ArrayList<String>();
                methodCodes.add(OrderRater.getNewServiceCode("UPS.GND"));
                methodCodes.add(OrderRater.getNewServiceCode("FDX.GND"));
                methodCodes.add(OrderRater.getNewServiceCode("FDX.HD"));
                methodCodes.add(OrderRater.getNewServiceCode("OWD.1ST.PRIORITY"));
                rater.setOrderBestRateAndMethod(order, methodCodes);
            }
            order.forcePayment = true;
            reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, false);
            if (reference == null || (!(order.completed))) {
                autos.setStatus(new Integer(1));
                throw new Exception("Autoship subscription could not be processed and has been suspended, most likely because " + (order.last_payment_error.trim().length() > 1 ? order.last_payment_error : order.last_error + "."));
            }

            PreparedStatement pstmt = HibernateSession.getPreparedStatement("insert into owd_order_auto_order (order_fkey,order_auto_fkey,status,retries,retry_limit) " +
                    "select ?,?,?,?,? from owd_order_auto where auto_ship_id=?");
            pstmt.setInt(1, Integer.parseInt(order.orderID));
            pstmt.setInt(2, autos.getAutoShipId().intValue());
            if (order.last_payment_error.toUpperCase().indexOf("EXPIRED") >= 0
                    || order.last_payment_error.toUpperCase().indexOf("INVALID") >= 0
                    ) {
                pstmt.setInt(4, 7);
            } else {
                pstmt.setInt(4, 0);
            }

            pstmt.setInt(5, 7);
            pstmt.setInt(6, autos.getAutoShipId().intValue());

            if (order.is_future_ship == 1) {
                pstmt.setString(3, "ON HOLD");
                reference = reference + " : ON HOLD, most likely because " + (order.last_payment_error.trim().length() > 1 ? order.last_payment_error : order.last_error + ". Retries will be attempted for 7 calendar days before suspending the account. Void order or suspend/cancel subscription to cancel retries.");
            } else {

                pstmt.setString(3, "OK");
            }

            pstmt.executeUpdate();
            pstmt.close();

            Calendar expected = Calendar.getInstance();
            expected.set(Calendar.HOUR,0);
            expected.set(Calendar.HOUR_OF_DAY,0);
            expected.set(Calendar.MINUTE,0);
            expected.set(Calendar.SECOND,0);
            expected.set(Calendar.MILLISECOND,0);


            if ("day".equals(autos.getCalendarUnit())) {
                expected.add(Calendar.DATE, autos.getShipInterval());
            } else if ("week".equals(autos.getCalendarUnit())) {
                expected.add(Calendar.WEEK_OF_YEAR, autos.getShipInterval());
            } else if ("month".equals(autos.getCalendarUnit())) {
                expected.add(Calendar.MONTH, autos.getShipInterval());
            } else if ("day of month".equals(autos.getCalendarUnit())) {
                expected.add(Calendar.MONTH, 1);
                expected.set(Calendar.DAY_OF_MONTH, autos.getShipInterval() > 28 ? 28 : autos.getShipInterval());
            } else if ("never".equals(autos.getCalendarUnit())) {
                autos.setStatus(new Integer(3));
            }
            autos.setNextShipmentDate(expected.getTime());
            it = autos.getOwdOrderAutoItems().iterator();
            boolean notSetYet = true;
            while (it.hasNext()) {
                OwdOrderAutoItem item = (OwdOrderAutoItem) it.next();
                if (autos.getClientFkey() == 463 && HighTimesSubscriptionSkusList.contains(item.getSku()) && notSetYet) {
                    notSetYet = false;
                    autos.setNextShipmentDate(getNextAnniversaryDateAfter(calNow.getTime(), item.getSku()));
                    String newSku = getSkuForAnniversaryDate(calNow.getTime(), item.getSku());
                    order.addLineItem(newSku, item.getQuantity(), item.getUnitPrice().floatValue(), ((float) (item.getQuantity() * item.getUnitPrice().doubleValue())), "", "", "");

                }
            }

            message = "Created subscription order : " + reference;

            /*  try
            {
                if (order.clientID.equals("352"))     //Dr Erika
                {

                    Date today = Calendar.getInstance().getTime();

                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    DecimalFormat mf = new DecimalFormat("$#,###,##0.00");

                    if (order.is_future_ship == 1)
                    {
                        //on hold due to some payment error

                        //send problem email
                        String body = "Dear " + order.getBillingContact().name + ", \n" +
                                "\n" +
                                "Thank you for your subscription order. We apologize but we could not charge your card for this shipment.\n" +
                                "Please email us at customerservice@drerika.com.\n" +
                                "If you believe you've received this email in error, please wait to see if you receive another email in the next few days about your shipment.\n" +
                                "\n========================================================== \n" +
                                "DO YOU NEED TO MAKE CHANGES TO YOUR SUBSCRIPTION SERVICE? \n" +
                                "========================================================== \n" +
                                "Please click this link to manage your account:\n" +
                                "http://www.drerika.com/myaccount\n" +
                                "\n" +
                                "PLEASE NOTE: Changes made after receiving this notification will be reflected on \n" +
                                "next months recurring billing notification. \n" +
                                "========================================================== \n" +
                                "\n" +
                                "Customer Service \n" +
                                "DrErika.com Online Store \n" +
                                "customerservice@drerika.com \n" +
                                "\n" +
                                "Subscription No. " + autos.getAutoShipId() + "\n" +
                                "Subscription Bill Date " + df.format(today) + "\n" +
                                "========================================================== \n" +
                                (autos.getSourceOrders().size() < 1 ? "" : "ORDER CONFIRMATION Invoice No. " + ((OwdOrder) autos.getSourceOrders().toArray()[0]).getOrderRefnum() + "\n") +
                                "\n" +
                                "Bill To: \n" +
                                OrderUtilities.getCompleteAddressAsString(order.getBillingContact(), order.getBillingAddress(), "\n") + "\n" +
                                "\n" +
                                "Ship To:\n" +
                                OrderUtilities.getCompleteAddressAsString(order.getShippingContact(), order.getShippingAddress(), "\n") + "\n" +
                                "\n" +
                                "Order Date: " + df.format(autos.getCreated()) + "\n" +

                                "Billed to CC: XXXX-XXXX-XXXX-" + order.cc_num.substring(12) +


                                "\nOrder Items:\n";

                        for (int i = 0; i < order.skuList.size(); i++)
                        {
                            LineItem li = (LineItem) order.skuList.elementAt(i);
                            body = body +

                                    "\n" +
                                    (li.quantity_request + li.quantity_backordered) + "\n" +
                                    "\n" +
                                    li.description + " Automatic Re-Order\n" +
                                    "\n" +
                                    "(Monthly) @ " + mf.format(li.sku_price) + " = " + mf.format(li.total_price) + " \n";
                        }

                        body = body +
                                "\n" +
                                "Sub-Total: " + mf.format(order.total_product_cost) + " \n" +
                                "\n" +
                                "Shipping: " + mf.format(order.total_shipping_cost) + " (" + autos.getShipMethod() + ") \n" +
                                "Sales Tax: " + mf.format(order.total_tax_cost) + " \n" +
                                "\n" +
                                "Invoice Total: " + mf.format(order.total_order_cost) + "\n" +
                                "\n" +
                                "TOTAL DUE: " + mf.format(order.total_order_cost) + "\n" +
                                " \n" +
                                "--------------------------------------------------------------------------";

                        String[] bccs = new String[1];
                        bccs[0] = "soyoon@noxsolutions.com";

                        Mailer.sendMail("Subscription Order Credit Card Failed", body, autos.getBillEmail(), null, bccs, "customerservice@drerika.com");
                    } else
                    {
                        //released for shipping
                        //do nothing


                    }
                }
            } catch (Exception exm)
            {
                Mailer.sendMail("error in autoship ddr erika email failure response", autos.getAutoShipId() + "", "owditadmin@owd.com", "checkit@owd.com");

            }*/
        } catch (Exception ex) {
            message = "Error creating autoship order - " + ex.getMessage();
            ex.printStackTrace();
            throw ex;
        } finally {
            return message;
        }


    }

    public static void main(String[] args) {

        try {

            shipPendingSubscriptionOrders("402");
                       /*                                        Calendar cal = Calendar.getInstance();

            log.debug("Current HT Issue: " + getSkuForAnniversaryDate(cal.getTime(), "HTSUB"));
            log.debug("Current BE Issue: " + getSkuForAnniversaryDate(cal.getTime(), "BESUB"));
            log.debug("Current MM Issue: " + getSkuForAnniversaryDate(cal.getTime(), "MMJSUB"));


            log.debug("Next HT Ann Date: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "HTSUB")));
            log.debug("Next BE Ann Date: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "BESUB")));
            log.debug("Next MM Ann Date: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "MMJSUB")));

            log.debug("Next HT Issue: " + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "HTSUB"), "HTSUB"));
            log.debug("Next BE Issue: " + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "BESUB"), "BESUB"));
            log.debug("Next MM Issue: " + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "MMJSUB"), "MMJSUB"));
        */
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }

    }

    public static List shipPendingSubscriptionOrders(String clientID) {

        OwdOrderAuto auto = null;
        String message = "";
        List idlist = new ArrayList();
        List results = new ArrayList();

        log.debug("shipping pending sub orders " + clientID);
        try {

            voidExistingPendingSubscriptions(clientID);
            ResultSet rs;

             if(clientID.equals("529")){
                 rs = HibernateSession.getResultSet("select distinct auto_ship_id,ship_company,case when ship_company like 'ExpDr%' then 0 else 1 end from owd_order_auto where status=0 and client_fkey= "+ clientID +" and next_shipment_date<=getdate()\n" +
                         "order by case when ship_company like 'ExpDr%' then 0 else 1 end");

             }else {
                 rs = HibernateSession.getResultSet("select distinct auto_ship_id from owd_order_auto where status=0 and client_fkey=" + clientID + " and next_shipment_date<=getdate()");
             }
            while (rs.next()) {
                idlist.add(rs.getString(1));
            }
            rs.close();
            log.debug("ASNMgr found " + idlist.size() + " pending autoship entries");
            Iterator itx = idlist.iterator();
            while (itx.hasNext()) {
                String autoID = (String) itx.next();

                try {

                    Session sess = HibernateSession.currentSession();
                    auto = AutoShipFactory.getAutoshipFromAutoShipID(new Integer(autoID), 0, true);

                    results.add("::Attempting autoship ID: " + auto.getAutoShipId() + "/" + auto.getBillName());
                    message = shipSubscriptionOrder(auto);
                    log.debug("got subcreate message " + message);
                    results.add(message);
                    //  HibernateSession.currentSession().flush();
                    HibUtils.commit(HibernateSession.currentSession());

                } catch (Exception ex) {

                    HibUtils.rollback(HibernateSession.currentSession());

                    message = "Error creating subscription order : " + ex.getMessage();
                    log.debug("got suberror message " + message);
                    results.add(message);
                } finally {
                    try {

                        log.debug("saving subcreate message " + message);
                        saveOrUpdateAutoShip(auto, message);

                    } catch (Exception ex2) {
                        ex2.printStackTrace();
                    }

                }
            }


            List autolist = new ArrayList();

            List orderlist = new ArrayList();

            List autoorderlist = new ArrayList();
            log.debug("Trying CC retries");

            ResultSet rs2 = HibernateSession.getResultSet("select distinct order_fkey,order_auto_fkey,id,order_num from owd_order_auto_order " +
                    "join owd_order on order_id=order_fkey and client_fkey=" + clientID +
                    "                     join owd_order_auto on auto_ship_id=order_auto_fkey where owd_order_auto_order.status='ON HOLD'\n" +
                    "                     and retries<(retry_limit)");

            while (rs2.next()) {
                log.debug("got CC check order " + rs2.getString(4));
                autolist.add(rs2.getString(2));
                orderlist.add(rs2.getString(1));
                autoorderlist.add(rs2.getString(3));
            }
            rs2.close();
            results.add("");
            results.add("Declined CC Retry Attempts\r\n");
            for (int i = 0; i < autolist.size(); i++) {
                String orderID = (String) orderlist.get(i);
                String autoID = (String) autolist.get(i);
                String autoorderID = (String) autoorderlist.get(i);


                try {

                    OwdOrderAuto autoOrder = AutoShipFactory.getAutoshipFromAutoShipID(new Integer(autoID), 0, true);
                    OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(orderID));


                    results.add("::Attempting CC retry: " + order.getOrderNum());
                    message = retryDeclinedSubscriptionCCs(autoOrder, order, Integer.parseInt(autoorderID));
                    log.debug("got subcreate message " + message);
                    results.add(message);
                    //  HibernateSession.currentSession().flush();
                    HibUtils.commit(HibernateSession.currentSession());

                } catch (Exception ex) {

                    ex.printStackTrace();
                    HibUtils.rollback(HibernateSession.currentSession());

                    message = "Error retrying subscription order : " + ex.getMessage();
                    log.debug("got suberror message " + message);
                    results.add(message);
                } finally {
                    try {

                        // //log.debug("saving subcreate message "+message);
                        // saveOrUpdateAutoShip(auto, message);
                        HibernateSession.closeSession();

                    } catch (Exception ex2) {
                        ex2.printStackTrace();
                    }

                }
                //  HibernateSession.closeSession();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //   // HibernateSession.closeSession();
        }


        if (results.size() > 0) {
            StringBuffer sb = new StringBuffer();
            Iterator it = results.iterator();
            while (it.hasNext()) {
                log.debug("getting message!");
                log.debug((String) it.next());
                log.debug("got message " + message);
                sb.append(message + "\n");

            }
        }


        if (results.size() > 2) {
            return results;
        } else {

            return new ArrayList();
        }
    }

    public static float getOrderTotal(OwdOrderAuto auto) throws Exception {

        float total_product_cost = 0;

        for (int i = 0; i < auto.getOwdOrderAutoItems().size(); i++) {
            total_product_cost += (((float) ((OwdOrderAutoItem) auto.getOwdOrderAutoItems().toArray()[i]).getQuantity())) * ((OwdOrderAutoItem) auto.getOwdOrderAutoItems().toArray()[i]).getUnitPrice().floatValue();
        }
        total_product_cost = OWDUtilities.roundFloat(total_product_cost);


        return OWDUtilities.roundFloat(total_product_cost + auto.getShipCost().floatValue() + auto.getSalesTax().floatValue());


    }

    public static void voidExistingPendingSubscriptions(String clientID) throws Exception {
        ResultSet rs = HibernateSession.getResultSet("(select a.auto_ship_id,o.order_refnum,o.order_num,o.client_fkey,o.order_id from owd_order_auto a\n" +
                "join owd_order_auto_order ao join owd_order o \n" +
                " on o.order_id=order_fkey and o.is_void=0 and o.post_date is null\n" +
                "on a.auto_ship_id=order_auto_fkey " +
                " where a.status=0  and next_shipment_date<=getdate() and o.client_fkey=" + clientID + " ) UNION (\n" +
                "select a.auto_ship_id,o2.order_refnum,o2.order_num,o2.client_fkey, o2.order_id from owd_order_auto a\n" +
                "join owd_order_auto_order ao join owd_order o join owd_order o2 on o2.order_num=o.backorder_order_num\n" +
                " on o.order_id=order_fkey and o2.is_void=0 and o2.post_date is null\n" +
                "on a.auto_ship_id=order_auto_fkey\n" +
                " where a.status=0  and next_shipment_date<=getdate() and o2.client_fkey=" + clientID + ")");

        while (rs.next()) {
            log.debug("voiding order id " + rs.getInt(5));
            try {
                PreparedStatement ps = HibernateSession.getPreparedStatement("exec void_order ?");
                ps.setInt(1, rs.getInt(5));
                ps.executeUpdate();
                OwdOrderAutoHistory autoh = new OwdOrderAutoHistory();
                autoh.setCreated(Calendar.getInstance().getTime());
                autoh.setType(1);
                autoh.setMessage("Voided order " + rs.getString(2) + "/" + rs.getString(3) + " for new autoship order");
                OwdOrderAuto autos = (OwdOrderAuto) HibernateSession.currentSession().load(OwdOrderAuto.class, rs.getInt(1));

                autos.getOwdOrderAutoHistories().add(autoh);
                autoh.setOwdOrderAuto(autos);
                HibernateSession.currentSession().save(autoh);
                HibernateSession.currentSession().saveOrUpdate(autos);
                HibUtils.commit(HibernateSession.currentSession());
                Event.addOrderEvent(rs.getInt(5), Event.kEventTypeGeneral, "Voided by Autoship Manager due to reaching next autoship anniversary date", "Autoship Manager");
            } catch (Exception ex) {
                ex.printStackTrace();
                HibUtils.rollback(HibernateSession.currentSession());
            }
        }

        rs.close();

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

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int m1 = cal1.get(Calendar.YEAR) * 12 + cal1.get(Calendar.MONTH);
        int m2 = cal2.get(Calendar.YEAR) * 12 + cal2.get(Calendar.MONTH);
        return m2 - m1 + 1;
    }


}








package com.owd.web.api;

import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoHistory;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.CreditCard;
import com.owd.core.business.Client;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.OrderUtilities;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 7, 2008
 * Time: 1:46:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionUpdateRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();

    public static final String kRootTag = "OWD_SUBSCRIPTION_UPDATE_REQUEST";

    public static final String shipInterval = "shipInterval";
    public static final String calendarUnit = "calendarUnit";
    public static final String requirePayment = "requirePayment";
    public static final String status = "status";
    public static final String nextShipmentDate = "nextShipmentDate";

    public static final String billName = "billName";
    public static final String billAddressOne = "billAddressOne";
    public static final String billAddressTwo = "billAddressTwo";
    public static final String billCity = "billCity";
    public static final String billState = "billState";
    public static final String billZip = "billZip";
    public static final String billCountry = "billCountry";
    public static final String ccNum = "ccNum";
    public static final String ccExpMon = "ccExpMon";
    public static final String ccExpYear = "ccExpYear";
    public static final String shipName = "shipName";
    public static final String shipAddressOne = "shipAddressOne";
    public static final String shipAddressTwo = "shipAddressTwo";
    public static final String shipCity = "shipCity";
    public static final String shipState = "shipState";
    public static final String shipZip = "shipZip";
    public static final String shipCountry = "shipCountry";
    public static final String billPhone = "billPhone";
    public static final String shipPhone = "shipPhone";
    public static final String shipMethod = "shipMethod";
    public static final String salesTax = "salesTax";
    public static final String shipCost = "shipCost";
    public static final String created = "created";
    public static final String billEmail = "billEmail";
    public static final String shipEmail = "shipEmail";

    public static final String orderSource = "orderSource";


    public static final String ITEM = "ITEM";
    public static final String ITEMquantity = "quantity";
    public static final String ITEMsku = "sku";
    public static final String ITEMunitPrice = "unitPrice";


    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception

    {
        log.debug("handling sub update request " + root.getNodeName());
        boolean replaceItems = false;
        List<OwdOrderAuto> updatedOrders = new ArrayList<OwdOrderAuto>();
        SubscriptionStatusResponse response = new SubscriptionStatusResponse(api_version);

        try {



            //add limits (AND logic for all)

            NodeList updaters = root.getElementsByTagName("OWD_SUBSCRIPTION_UPDATE");
            log.debug("updaters:" + updaters.getLength());
            Node n1;
            for (int ni = 0; ni < updaters.getLength(); ni++) {
                n1 = updaters.item(ni);


                String subId = XPathAPI.eval(n1, "@autoShipId").toString();
                if (subId == null) {
                    throw new Exception("No subscription ID found");
                }
                int subIdInt = 0;
                try {
                    subIdInt = Integer.parseInt(subId);

                } catch (Exception ex) {
                    throw new Exception("Invalid subscription ID (" + subId + ") found");
                }

                OwdOrderAuto order = (OwdOrderAuto) HibernateSession.currentSession().load(OwdOrderAuto.class, subIdInt);
                if (order == null) {
                    throw new Exception("Subscription ID (" + subId + ") found");
                }



                //got a valid order to update!
                NodeIterator nl = XPathAPI.selectNodeIterator(n1, "*");

                Node n;
                while ((n = nl.nextNode()) != null) {
                    // Serialize the found nodes to System.out
                    String value = n.getTextContent()==null?"":n.getTextContent();
                    value = value.trim();
                    log.debug(n.getNodeName() + ":" + value);

                    if (n.getNodeName().equals(billAddressOne)) {
                        order.setBillAddressOne(value);
                    } else if (n.getNodeName().equals(billAddressTwo)) {
                        order.setBillAddressTwo(value);
                    } else if (n.getNodeName().equals(billCity)) {
                        order.setBillCity(value);
                    } else if (n.getNodeName().equals(billCountry)) {
                        order.setBillCountry(CountryNames.getCountryName(value));
                    } else if (n.getNodeName().equals(billEmail)) {
                        order.setBillEmail(value);
                    } else if (n.getNodeName().equals(billName)) {
                        order.setBillName(value);
                    } else if (n.getNodeName().equals(billPhone)) {
                        order.setBillPhone(value);
                    } else if (n.getNodeName().equals(billState)) {
                        order.setBillState(value);
                    } else if (n.getNodeName().equals(billZip)) {
                        order.setBillZip(value);
                    } else if (n.getNodeName().equals(calendarUnit)) {
                        if ("day".equals(value)
                                || "week".equals(value)
                                || "month".equals(value)
                                || "never".equals(value)) {
                            order.setCalendarUnit(value);
                        } else {
                            throw new Exception("calendarUnit value must be day, week, month or never");
                        }
                    } else if (n.getNodeName().equals(ccExpMon)) {
                        int val = Integer.parseInt(value);
                        if (val >= 1 && val <= 12) {
                            order.setCcExpMon(val);
                        } else {
                            throw new Exception("ccExpMon value must be >= 1 and <= 12");
                        }
                    } else if (n.getNodeName().equals(ccExpYear)) {
                        int val = Integer.parseInt(value);
                        if (val >= 2008 && value.length() == 4) {
                            order.setCcExpYear(val);
                        } else {
                            throw new Exception("ccExpYear value must be >= 2008 and be exactly 4 digits");
                        }
                    } else if (n.getNodeName().equals(ccNum)) {
                        if (CreditCard.isValid(CreditCard.parseDirtyLong(value))) {
                            order.setCcNum(value);
                        } else {
                            throw new Exception("CC number value is invalid");
                        }
                    } else if (n.getNodeName().equals(nextShipmentDate)) {
                        order.setNextShipmentDate(df.parse(value));
                    } else if (n.getNodeName().equals(orderSource)) {
                        order.setOrderSource(orderSource);
                    } else if (n.getNodeName().equals(requirePayment)) {
                        int val = Integer.parseInt(value);
                        if (val == 0 || val == 1) {
                            order.setRequirePayment(val);
                        } else {
                            throw new Exception("requirePayment value must be 0 or 1");
                        }
                    } else if (n.getNodeName().equals(salesTax)) {
                        double val = Double.parseDouble(value);
                        if (val < 0.00) {
                            throw new Exception("salesTax must be >= 0.00");
                        }
                        order.setSalesTax(new BigDecimal(value));
                    } else if (n.getNodeName().equals(shipAddressOne)) {
                        order.setShipAddressOne(value);
                    } else if (n.getNodeName().equals(shipAddressTwo)) {
                        order.setShipAddressTwo(value);
                    } else if (n.getNodeName().equals(shipCity)) {
                        order.setShipCity(value);
                    } else if (n.getNodeName().equals(shipCost)) {
                        double val = Double.parseDouble(value);
                        if (val < 0.00) {
                            throw new Exception("shipCost must be >= 0.00");
                        }
                        order.setShipCost(new BigDecimal(value));
                    } else if (n.getNodeName().equals(shipCountry)) {
                        order.setShipCountry(CountryNames.getCountryName(value));
                    } else if (n.getNodeName().equals(shipEmail)) {
                        order.setShipEmail(value);
                    } else if (n.getNodeName().equals(shipInterval)) {
                        int val = Integer.parseInt(value);
                        if (val > 0) {
                            order.setShipInterval(val);
                        } else {
                            throw new Exception("shipInterval must be > 0");
                        }
                    } else if (n.getNodeName().equals(shipMethod)) {
                        //todo validate ship method
                        if (value.indexOf("(Book)") >= 0) {
                            value = value.substring(0, value.indexOf("(Book)")).trim();
                        }
                        String carrServiceRef = OrderUtilities.getServiceList().getRefForValue(value);

                        if (carrServiceRef == null) {
                            throw new Exception("shipMethod name |" + value + "| not valid");
                        }

                        order.setShipMethod(value);
                    } else if (n.getNodeName().equals(shipName)) {
                        order.setShipName(value);
                    } else if (n.getNodeName().equals(shipPhone)) {
                        order.setShipPhone(value);
                    } else if (n.getNodeName().equals(shipState)) {
                        order.setShipState(value);
                    } else if (n.getNodeName().equals(shipZip)) {
                        order.setShipZip(value);
                    } else if (n.getNodeName().equals(status)) {
                        int val = Integer.parseInt(value);
                        if (val >= 0 && val <= 3) {
                            order.setStatus(val);
                        } else {
                            throw new Exception("subscription status value must be 0 or 1");
                        }
                    } else if (n.getNodeName().equals(ITEM)) {

                        replaceItems = true;
                        OwdOrderAutoItem item = new OwdOrderAutoItem();
                        NodeIterator nli = XPathAPI.selectNodeIterator(n, "*");

                        Node nitem;
                        while ((nitem = nli.nextNode()) != null) {
                            // Serialize the found nodes to System.out
                            value = nitem.getTextContent().trim();

                            log.debug(nitem.getNodeName() + ":" + value);

                            if (nitem.getNodeName().equals(ITEMsku)) {
                                if(LineItem.SKUExists(client.client_id,value))
                                {
                                item.setSku(value);

                                }else
                                {
                                    throw new Exception ("ITEM sku value "+value+" is not recognized as a valid SKU");
                                }
                            } else if (nitem.getNodeName().equals(ITEMquantity)) {
                                int val = Integer.parseInt(value);
                                if (val >= 1) {
                                    item.setQuantity(val);
                                } else {
                                    throw new Exception("ITEM quantity value must be > 0");
                                }
                            } else if (nitem.getNodeName().equals(ITEMunitPrice)) {
                                double val = Double.parseDouble(value);
                                if (val >= 0.00) {
                                    item.setUnitPrice(new BigDecimal(value));
                                } else {
                                    throw new Exception("ITEM unitPrice value must be >= 0.00");
                                }
                            } else {
                                throw new Exception("XML element name ITEM/" + n.getNodeName() + " with value " + value + " not recognized. Please correct your XML document and resubmit.");

                            }

                        }
                        if(item.getSku()==null ||
                                item.getQuantity()==null
                        || item.getUnitPrice() == null ||
                                item.getSku().length()<1 || item.getUnitPrice().doubleValue()<0.00 || item.getQuantity()<1)
                        {
                            throw new Exception("ITEM elements incomplete - valid sku, quantity and unitPrice values are required");
                        }
                        order.getOwdOrderAutoItems().add(item);
                        item.setOwdOrderAuto(order);

                    } else {
                        throw new Exception("XML element name " + n.getNodeName() + " with value " + value + " not recognized. Please correct your XML document and resubmit.");
                    }


                }

                if (replaceItems) {
                    Collection<OwdOrderAutoItem> c = order.getOwdOrderAutoItems();
                    Collection<OwdOrderAutoItem> d = new ArrayList<OwdOrderAutoItem>();
                    for (OwdOrderAutoItem item : c) {
                        if (item.getId() != null && item.getId() > 0) {
                            log.debug("removing item id "+item.getId());
                            d.add(item);
                        }else
                        {
                        HibernateSession.currentSession().save(item);

                        }
                    }
                    order.getOwdOrderAutoItems().removeAll(d);

                    for (OwdOrderAutoItem item : d) {
                        HibernateSession.currentSession().delete(item);
                    }
                }

                OwdOrderAutoHistory note = new OwdOrderAutoHistory();
                note.setCreated(Calendar.getInstance().getTime());
                note.setOwdOrderAuto(order);
                note.setType(0);
                note.setMessage("Subscription updated via API");
                order.getOwdOrderAutoHistories().add(note);

                HibernateSession.currentSession().save(note);
                HibernateSession.currentSession().save(order);

                HibernateSession.currentSession().flush();
                updatedOrders.add(order);
            }

            if(!testing)
            {
                HibUtils.commit(HibernateSession.currentSession());
            }
            response.setSubscriptionList(updatedOrders);

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            HibUtils.rollback(HibernateSession.currentSession());

            throw ex;

        }
    }
}

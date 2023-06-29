package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class OrderFinder {
private final static Logger log =  LogManager.getLogger();


    OrderFinderCriteria currentCriteria = new OrderFinderCriteria();


    public final static int kTextSearchTypeIs = 1;
    public final static int kTextSearchTypeContains = 2;

    public final static int kDateContextTypeToday = 0;
    public final static int kDateContextTypeWeek = 1;
    public final static int kDateContextTypeMonth = 2;
    public final static int kDateContextTypeYear = 3;
    public final static int kDateContextTypeAll = 4;
    public final static int kDateContextTypeDate = 5;


    public final static int kDateTypeCreated = 0;
    public final static int kDateTypeReleased = 1;
    public final static int kDateTypeShipped = 2;

    public final static int kOrderStatusCritActiveUnshipped = 0;
    public final static int kOrderStatusCritHeld = 1;
    public final static int kOrderStatusCritActiveBO = 2;
    public final static int kOrderStatusCritAtWarehouse = 3;
    public final static int kOrderStatusCritShipped = 4;
    public final static int kOrderStatusCritCancelled = 5;


    private int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    private int clientID = -1;

    public int getOrderStatusCriterion() {
        return currentCriteria.getOrderStatusCriterion();
    }

    public void setOrderStatusCriterion(int orderStatusCriterion) {
        currentCriteria.setOrderStatusCriterion(orderStatusCriterion);
    }

    public String getTextSearchValue() {
        return currentCriteria.getTextSearchValue();
    }

    public void setTextSearchValue(String textSearchValue) {
        currentCriteria.setTextSearchValue(textSearchValue);
    }

    public int getDateType() {
        return currentCriteria.getDateType();
    }

    public void setDateType(int dateType) {
        currentCriteria.setDateType(dateType);
    }

    public int getTextSearchType() {
        return currentCriteria.getTextSearchType();
    }

    public void setTextSearchType(int textSearchType) {
        currentCriteria.setTextSearchType(textSearchType);
    }

    public int getDateContextLimit() {
        return currentCriteria.getDateContextLimit();
    }

    public void setDateContextLimit(int dateContextLimit) {
        currentCriteria.setDateContextLimit(dateContextLimit);
    }


    public Map getTextSearchFieldMap() {
        return currentCriteria.getTextSearchFieldMap();
    }

    public String getTextSearchField() {
        return currentCriteria.getTextSearchField();
    }

    public void setTextSearchField(String textSearchField) {
        currentCriteria.setTextSearchField(textSearchField);
    }


    public OrderFinder(String client_id) {
        //may be zero to indicate all clients

        setClientID(new Integer(client_id).intValue());


    }


    public static void main(String[] args) {


        StringBuffer buffer = new StringBuffer();


        try {
            Session sess = HibernateSession.currentSession();

            //     Iterator it = (sess.find("from OwdOrder as o where o.isVoid = 0 and o.clientFkey = ? order by o.orderId", new Integer("55"), Hibernate.INTEGER)).iterator();

            Criteria crit = sess.createCriteria(OwdOrder.class);
            crit.setMaxResults(1000);
            crit.add(Restrictions.eq("clientFkey", new Integer("55")))
                    .add(Restrictions.eq("isVoid", new Integer(0)))
                    .addOrder(Order.desc("orderId"))
                    .createCriteria("lineitems").add(Restrictions.eq("inventoryNum", "101"));

            //crit.add(Restrictions.sql(" {alias}.order_id  in (select order_fkey from owd_order_track t where t.order_fkey = {alias}.order_id and t.is_void=0) "));


            log.debug("got " + crit.list().size() + " orders");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }


    }


    public String showResults(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        StringBuffer buffer = new StringBuffer();


        Session sess = HibernateSession.currentSession();

        try {

            if ((currentCriteria.isChanged()) || new Integer(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue() != getClientID() || req.getSession(true).getAttribute("orderfinderresults") == null) {
                log.debug("crit changed:" + currentCriteria.isChanged() + ":");
                currentCriteria.setChanged(false);
                Criteria crit = sess.createCriteria(OwdOrder.class);

                if (!(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID).equals("0"))) {
                    crit.setMaxResults(5000);
                    crit.add(Restrictions.eq("clientFkey", new Integer(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID))));
                } else {
                    crit.setMaxResults(5000);
                }
                crit.addOrder(Order.desc("orderId"));


                switch (getTextSearchType()) {
                    case (kTextSearchTypeIs): //unshipped
                        if (getTextSearchField().length() > 1) {
                            log.debug("srching tfield (" + getTextSearchField() + ") for " + getTextSearchValue());

                            if (getTextSearchField().indexOf("|") > 0) {

                                crit.createCriteria(getTextSearchField().substring(0, getTextSearchField().indexOf("|"))).add(Restrictions.eq(getTextSearchField().substring(getTextSearchField().indexOf("|") + 1), getTextSearchValue()));
                            } else {
                                crit.add(Restrictions.eq(getTextSearchField(), getTextSearchValue()));
                            }
                        }

                        break;

                    case (kTextSearchTypeContains): //unshipped
                        if (getTextSearchField().length() > 1) {
                            log.debug("srching tfield (" + getTextSearchField() + ") like " + getTextSearchValue());
                            if (getTextSearchField().indexOf("|") > 0) {

                                crit.createCriteria(getTextSearchField().substring(0, getTextSearchField().indexOf("|"))).add(Restrictions.like(getTextSearchField().substring(getTextSearchField().indexOf("|") + 1), "%" + getTextSearchValue() + "%"));
                            } else {
                                crit.add(Restrictions.like(getTextSearchField(), "%" + getTextSearchValue() + "%"));
                            }
                        }

                        break;

                    default:

                }
                switch (getOrderStatusCriterion()) {
                    case (kOrderStatusCritActiveUnshipped): //unshipped
                        crit.add(Restrictions.sqlRestriction(" {alias}.order_id not in (select order_fkey from owd_order_track t where t.order_fkey = {alias}.order_id and t.is_void=0) "));
                        crit.add(Restrictions.eq("isVoid", new Integer(0)));
                        crit.add(Restrictions.eq("isFutureShip", new Integer(0)));
                        break;
                    case (kOrderStatusCritHeld): //on hold, no BO
                        crit.add(Restrictions.eq("isVoid", new Integer(0)));
                        crit.add(Restrictions.eq("isBackorder", new Boolean(false)));
                        crit.add(Restrictions.eq("isFutureShip", new Integer(1)));
                        crit.add(Restrictions.isNull("postDate"));

                        break;
                    case (kOrderStatusCritActiveBO): //pending BO
                        crit.add(Restrictions.eq("isVoid", new Integer(0)));
                        crit.add(Restrictions.eq("isBackorder", new Boolean(true)));
                        crit.add(Restrictions.eq("isFutureShip", new Integer(0)));
                        crit.add(Restrictions.isNull("postDate"));
                        break;
                    case (kOrderStatusCritAtWarehouse): // at Warehouse
                        crit.add(Restrictions.eq("isVoid", new Integer(0)));
                        crit.add(Restrictions.isNotNull("postDate"));
                        crit.add(Restrictions.sqlRestriction(" {alias}.order_id not in (select order_fkey from owd_order_track t where t.order_fkey = {alias}.order_id and t.is_void=0) "));
                        break;
                    case (kOrderStatusCritShipped):// Shipped
                        crit.add(Restrictions.eq("isVoid", new Integer(0)));
                        crit.add(Restrictions.sqlRestriction(" {alias}.order_id  in (select order_fkey from owd_order_track t where t.order_fkey = {alias}.order_id and t.is_void=0) "));

                        break;
                    case (kOrderStatusCritCancelled):// Cancelled
                        crit.add(Restrictions.eq("isVoid", new Integer(1)));

                        break;
                }

                String currentDateType = "{alias}.created_date";

                switch (getDateType()) {
                    case (kDateTypeCreated): //unshipped
                        currentDateType = "{alias}.created_date";
                        break;
                    case (kDateTypeReleased): //on hold, no BO
                        currentDateType = "{alias}.post_date";
                        break;
                    case (kDateTypeShipped): //pending BO
                        currentDateType = "{alias}.packages.shipDate";
                        break;

                }

                switch (getDateContextLimit()) {
                    case (kDateContextTypeToday): //unshipped
                        crit.add(Restrictions.sqlRestriction(" " + currentDateType + " >= \'" + OWDUtilities.getSQLDateNoTimeForToday() + "\'"));

                        break;
                    case (kDateContextTypeWeek): //on hold, no BO
                        crit.add(Restrictions.sqlRestriction(" " + currentDateType + " >= \'" + OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.WEEK_OF_YEAR, -1) + "\'"));

                        break;
                    case (kDateContextTypeMonth): //pending BO
                        crit.add(Restrictions.sqlRestriction(" " + currentDateType + " >= \'" + OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.MONTH, -1) + "\'"));
                        break;
                    case (kDateContextTypeYear): // at Warehouse
                        crit.add(Restrictions.sqlRestriction(" " + currentDateType + " >= \'" + OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.YEAR, -1) + "\'"));
                        break;
                    case (kDateContextTypeAll):// Shipped

                        break;
                }

                List list = crit.list();
                log.debug("Results size=" + list.size());
                req.getSession(true).setAttribute("orderfinderresults", list);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateSession.closeSession();
        }

        return buffer.toString();

    }

    public class OrderFinderCriteria {

        public boolean isChanged() {
            return isChanged;
        }

        public void setChanged(boolean changed) {
            isChanged = changed;
        }

        boolean isChanged = false;

        public OrderFinderCriteria() {
            textSearchFieldMap.put("Your Order Reference", "orderRefnum");
            textSearchFieldMap.put("OWD Order Reference", "orderNum");
            textSearchFieldMap.put("Package Tracking Ref", "packages|trackingNo");
            textSearchFieldMap.put("SKU", "lineitems|inventoryNum");
            textSearchFieldMap.put("Customer Last Name", "billLastName");
            textSearchFieldMap.put("Customer Postal Code", "billZip");
            textSearchFieldMap.put("Customer State", "billState");
            textSearchFieldMap.put("Customer Phone", "billPhoneNumber");
            textSearchFieldMap.put("Customer Email", "billEmailAddress");
            textSearchFieldMap.put("PO Number", "poNum");
            textSearchFieldMap.put("Customer Company", "billCompanyName");
            textSearchFieldMap.put("Coupon Code", "coupon");
            textSearchFieldMap.put("Ship Method", "carrService");
        }

        public int getDateContextLimit() {
            return dateContextLimit;
        }

        public void setDateContextLimit(int dateContextLimit) {
            if (this.dateContextLimit != dateContextLimit) isChanged = true;
            this.dateContextLimit = dateContextLimit;
        }

        public int getOrderStatusCriterion() {
            return orderStatusCriterion;
        }

        public void setOrderStatusCriterion(int orderStatusCriterion) {
            if (this.orderStatusCriterion != orderStatusCriterion) isChanged = true;
            this.orderStatusCriterion = orderStatusCriterion;
        }

        public int getTextSearchType() {
            return textSearchType;
        }

        public void setTextSearchType(int textSearchType) {
            if (this.textSearchType != textSearchType) isChanged = true;
            this.textSearchType = textSearchType;
        }


        public int getDateSearchType() {
            return dateSearchType;
        }

        public void setDateSearchType(int dateSearchType) {
            if (this.dateSearchType != dateSearchType) isChanged = true;
            this.dateSearchType = dateSearchType;
        }

        public String getTextSearchValue() {
            return textSearchValue;
        }

        public void setTextSearchValue(String textSearchValue) {
            if (!this.textSearchValue.equals(textSearchValue)) isChanged = true;
            this.textSearchValue = textSearchValue;
        }

        public String getDateSearchValue() {
            return dateSearchValue;
        }

        public void setDateSearchValue(String dateSearchValue) {
            if (!this.dateSearchValue.equals(dateSearchValue)) isChanged = true;
            this.dateSearchValue = dateSearchValue;
        }

        public Map getTextSearchFieldMap() {
            return textSearchFieldMap;
        }

        public void setTextSearchFieldMap(Map textSearchFieldMap) {
            this.textSearchFieldMap = textSearchFieldMap;
        }

        int dateContextLimit = 0;
        int orderStatusCriterion = 0;
        int textSearchType = 0;
        String textSearchField = "";

        public String getTextSearchField() {
            return textSearchField;
        }

        public void setTextSearchField(String textSearchField) {
            if (!this.textSearchField.equals(textSearchField)) isChanged = true;
            this.textSearchField = textSearchField;
        }


        int dateSearchType = 0;

        public int getDateType() {
            return dateType;
        }

        public void setDateType(int dateType) {
            this.dateType = dateType;
        }

        String textSearchValue = "";
        int dateType = 0;

        String dateSearchValue = "";
        //key = display name, value=hibernate field name
        Map textSearchFieldMap = new TreeMap();


    }


}

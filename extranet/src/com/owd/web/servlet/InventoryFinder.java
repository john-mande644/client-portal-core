package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class InventoryFinder {
private final static Logger log =  LogManager.getLogger();


    InventoryFinderCriteria currentCriteria = new InventoryFinderCriteria();


    public final static int kTextSearchTypeIs = 1;
    public final static int kTextSearchTypeContains = 2;


    private int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    private int clientID = -1;


    public String getTextSearchValue() {
        return currentCriteria.getTextSearchValue();
    }

    public void setTextSearchValue(String textSearchValue) {
        currentCriteria.setTextSearchValue(textSearchValue);
    }


    public int getTextSearchType() {
        return currentCriteria.getTextSearchType();
    }

    public void setTextSearchType(int textSearchType) {
        currentCriteria.setTextSearchType(textSearchType);
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


    public InventoryFinder(String client_id) {
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
                    .addOrder(Order.desc("orderId"));
            crit.add(Restrictions.sqlRestriction(" {alias}.order_id  in (select order_fkey from owd_order_track t where t.order_fkey = {alias}.order_id and t.is_void=0) "));


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
                Criteria crit = sess.createCriteria(OwdInventory.class);

                if (!(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID).equals("0"))) {
                    crit.setMaxResults(5000);
                    crit.add(Restrictions.eq("owdClient.clientId", new Integer(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID))));
                } else {
                    crit.setMaxResults(5000);
                }

                crit.addOrder(Order.asc("inventoryNum"));


                switch (getTextSearchType()) {
                    case (kTextSearchTypeIs):
                        if (getTextSearchField().length() > 1) {
                            log.debug("srching tfield (" + getTextSearchField() + ") for " + getTextSearchValue());

                            crit.add(Restrictions.eq(getTextSearchField(), getTextSearchValue()));
                        }

                        break;

                    case (kTextSearchTypeContains): //unshipped
                        if (getTextSearchField().length() > 1) {
                            log.debug("srching tfield (" + getTextSearchField() + ") like " + getTextSearchValue());

                            crit.add(Restrictions.like(getTextSearchField(), "%" + getTextSearchValue() + "%"));
                        }

                        break;

                    default:

                }


                List list = crit.list();
                log.debug("Results size=" + list.size());
                req.getSession(true).setAttribute("inventoryfinderresults", list);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateSession.closeSession();
        }

        return buffer.toString();

    }

    public class InventoryFinderCriteria {

        public boolean isChanged() {
            return isChanged;
        }

        public void setChanged(boolean changed) {
            isChanged = changed;
        }

        boolean isChanged = false;

        public InventoryFinderCriteria() {
            textSearchFieldMap.put("SKU", "inventoryNum");
            textSearchFieldMap.put("Title", "description");
            textSearchFieldMap.put("Supplier", "mfrPartNum");
        }


        public int getTextSearchType() {
            return textSearchType;
        }

        public void setTextSearchType(int textSearchType) {
            if (this.textSearchType != textSearchType) isChanged = true;
            this.textSearchType = textSearchType;
        }


        public String getTextSearchValue() {
            return textSearchValue;
        }

        public void setTextSearchValue(String textSearchValue) {
            if (!this.textSearchValue.equals(textSearchValue)) isChanged = true;
            this.textSearchValue = textSearchValue;
        }


        public Map getTextSearchFieldMap() {
            return textSearchFieldMap;
        }

        public void setTextSearchFieldMap(Map textSearchFieldMap) {
            this.textSearchFieldMap = textSearchFieldMap;
        }

        int textSearchType = 0;
        String textSearchField = "";

        public String getTextSearchField() {
            return textSearchField;
        }

        public void setTextSearchField(String textSearchField) {
            if (!this.textSearchField.equals(textSearchField)) isChanged = true;
            this.textSearchField = textSearchField;
        }


        String textSearchValue = "";

        //key = display name, value=hibernate field name
        Map textSearchFieldMap = new TreeMap();


    }


}

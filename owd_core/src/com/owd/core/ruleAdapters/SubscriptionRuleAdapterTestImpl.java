package com.owd.core.ruleAdapters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 12/12/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionRuleAdapterTestImpl implements SubscriptionRuleAdapter {
private final static Logger log =  LogManager.getLogger();

    private float shipCost = 5.00f;

    public float getShipCost() {
        return shipCost;
    }

    public void setShipCost(float shipCost) {
        log.debug("SubscriptionRuleAdapterTestImpl.setShipCost: " + shipCost);
        this.shipCost = shipCost;
    }

    public void addItemToList(String itemName, float cost, int quantity) {
        log.debug("SubscriptionRuleAdapterTestImpl.addItemToList: " + itemName + " " + cost + " " + quantity);
    }

    public void setVariableFirstShipmentDate(String part) {
        log.debug("SubscriptionRuleAdapterTestImpl.setVariableFirstShipmentDate: " + part);
    }

    public boolean withinDateRangeUsingFormat(String startDate, String endDate, String format) {

        try {
            SimpleDateFormat dt1 = new SimpleDateFormat(format);

            Date dtStart = dt1.parse(startDate);
            Date dtEnd = dt1.parse(endDate);

            Calendar cs = new GregorianCalendar();
            cs.setTime(dtStart);

            Calendar ce = new GregorianCalendar();
            ce.setTime(dtEnd);

            Calendar now = GregorianCalendar.getInstance();
            Date nowd = now.getTime();

            if (now.getTimeInMillis() >= cs.getTimeInMillis() && now.getTimeInMillis() < ce.getTimeInMillis()) {
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }


    public void setCampaignName(String campaignName) {
        log.debug("SubscriptionRuleAdapterTestImpl.setCampaignName: " + campaignName);
    }

    public void setFirstShipmentDate() {
        log.debug("SubscriptionRuleAdapterTestImpl.setFirstShipmentDate");
    }

    public void addItemToAvailability(String itemName, String clientID, int quantity) {
        log.debug("SubscriptionRuleAdapterTestImpl.addItemToAvailability: " +
                itemName + " " + clientID + " " +quantity);
    }

    public void addOrderLineItem(String part, int qty, float cost, float totalCost, String description, float itemDeclaredValue, String itemCustomsDesc) {
        log.debug("SubscriptionRuleAdapterTestImpl.addOrderLineItem: " + part + " " + qty + " " + cost + " " + totalCost + " " + description);


    }

    public void setProperty(String name, String value) {
        log.debug("SubscriptionRuleAdapterTestImpl.setProperty: " + name + " " + value);
    }

    public String getProperty(String name) {

        String value = "";

        if (name.equals("part")) {
            value = "G4CONT";
        } else if (name.equals("qty")) {
            value = "1";
        } else if (name.equals("cost")) {
            value = "12.95f";
        } else if (name.equals("description")) {
            value = "Part Desc...";
        }
        log.debug("SubscriptionRuleAdapterTestImpl.getProperty: " + name + " " + value);
        return value;
    }

    public String getProperty(String name, String defaultValue) {
        log.debug("SubscriptionRuleAdapterTestImpl.setProperty: " + name + " " + defaultValue);

        if (name.equals("part")) {
            return "G4CONT";
        } else if (name.equals("qty")) {
            return "1";
        } else if (name.equals("cost")) {
            return "12.95f";
        } else if (name.equals("description")) {
            return "Part Desc...";
        }
        return defaultValue;
    }

    public void sendMail(String title, String message, String emailAddress) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object executeRuleMethod(String methodName, List<Object> parms) {

        log.debug("executeRuleMethod: " + methodName);

        Class<?>[] types = this.getTypes(methodName, parms);

        return execMethod(methodName, types, parms);
    }

    public boolean withinDateRange(String startData, String endDate) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     *
     * @param parms
     * @return
     */
    private Class<?>[] getTypes(String methodName, List<Object> parms) {

        try {
            Class<?>[] types = new Class<?>[parms.size()];

            for(int i=0; i < parms.size(); i++) {
                if ("equals".equals(methodName))
                    types[i] = Object.class;
                else {
                    types[i] = parms.get(i).getClass();
                }

            }
            return types;
        } catch (Exception e) {
            System.err.println("Error RuleAdapter.getTypes: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param methodName
     * @param types
     * @param values
     * @return
     */
    private Object execMethod(String methodName, Class<?>[] types, List<Object> values) {

        try {
            this.getClass().getMethods();
            Method m = this.getClass().getMethod(methodName, types);
            return m.invoke(this, values);
        } catch (Exception e) {
            System.err.println("Error RuleAdapter.execMethod: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

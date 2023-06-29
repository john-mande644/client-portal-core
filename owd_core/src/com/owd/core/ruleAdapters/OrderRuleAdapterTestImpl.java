package com.owd.core.ruleAdapters;

import com.owd.core.Mailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 12/3/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderRuleAdapterTestImpl implements OrderRuleAdapter {
private final static Logger log =  LogManager.getLogger();

    private  int recurrenceCount = 0;

    private String billingZip = "99999";
    private String billingAddressOne = "Address 1...";

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public void setBillingAddressOne(String billingAddressOne) {
        this.billingAddressOne = billingAddressOne;
    }

    public OrderRuleAdapterTestImpl() {
        log.debug("OrderRuleAdapterImpl");
    }

    public String getClientOrderReference() {
        return null;
    }

    public void setInsuredValue(float value) {

    }

    @Override
    public void addNote(String note) {

    }

    public int getRequestedUnitsofSkusWithInventoryTagValue(String tagName, String tagValue) {
        return 0;
    }

    @Override
    public void setLTLHold() {

    }

    @Override
    public void setTemplate(String templateName) {

    }

    public String getTagValue(String key) {
        return null;
    }

    public boolean tagExists(String key){
        return false;
    }

    /**
     * @param addressType
     * @return
     */
    public String getAddressCity(String addressType) {
        return null;
    }

    /**
     * @param addressType
     * @return
     */
    public String getAddressCountry(String addressType) {
        return null;
    }

    @Override
    public String getShippingMethodName() {
        return null;
    }

    @Override
    public void setShippingMethodName(String newMethod)  throws Exception {

    }

    @Override
    public void setThirdPartyBilling(String account)  throws Exception{

    }

    @Override
    public void setThirdPartyBillingContact(String contact, String company, String address1, String address2, String city, String state, String zip, String phone) {

    }

    /**
     * @param contactType@return
     */
    public String getContactEmail(String contactType) {
        return null;
    }

    /**
     * @param contactType@return
     */
    public String getContactName(String contactType) {
        return null;
    }

    /**
     * @param contactType@return
     */
    public String getContactPhone(String contactType) {
        return null;
    }

    public Map<String,String> getTagMap(){
        return new HashMap<String,String>();
    }

    public boolean isInternationalShipping(){
        return false;
    }

    public float getTotalCustomsValue()
    {
        return 0.00f;
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


    public void holdNewOrder() {

    }

    public void mergeItemPackingInstructionsToOrder()
    {

    }

    public void setPackingInstructions(String instructions){

    }

    public boolean hasGiftMessage() {
        return false;
    }

    public void setRecurrenceCount(int recurrenceCount) {
        this.recurrenceCount = recurrenceCount;
    }

    public String getClientID() {
        return "489";
    }

    public int getRequestedUnitsofSkusInList(List<String> skus)
    {
        return 0;
    }

    public long getStockLevelForInventoryId(int iid, String facilityCode)
    {
        if(iid%2==0)
        {
            return 100;
        }              else
        {
            return 0;
        }
    }

    public String getFacilityCode()
    {
        return "DC1";
    }


    public boolean containsAnySKU(List<String> sku) {
        log.debug(">>>>>>>>>> containsAnySKU");

        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < sku.size(); i++) {
            buff.append(" " + sku.get(i) );
        }
        log.debug("containsAnySKU: " + buff.toString() + " " + true);

        return true;
    }

    @DroolsDescriptor(description = "Contains all included Skus", parameterType = "String", returnType = "boolean")
    public boolean containsAllSKU(List<String> sku) {
        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < sku.size(); i++) {
            buff.append(" " + sku.get(i) );
        }
        log.debug("containsAllSKU: " + buff.toString() + " " + true);

        return true;
    }



    public boolean notContainsAnySKU(List<String> sku) {
        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < sku.size(); i++) {
            buff.append(" " + sku.get(i) );
        }
        log.debug("notContainsAnySKU: " + buff.toString() + " " + true);
        return true;
    }

    public boolean notContainsAllSKU(List<String> sku) {
        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < sku.size(); i++) {
            buff.append(" " + sku.get(i) );
        }

        log.debug("notContainsAllSKU: " + buff.toString() + " " + true);

        return true;
    }

    public String getBillingZip() {
        log.debug("getBillingZip: " + billingZip);

        return billingZip;
    }

    public String getBillingAddressOne() {
        log.debug("getBillingAddressOne: " + billingAddressOne);

        return billingAddressOne;
    }

    public boolean isAutoDelivery() {
        log.debug("isAutoDelivery: " + true);

        return true;
    }

    public int getRecurrenceCount() {
        log.debug("getRecurrenceCount: " + recurrenceCount);

        return recurrenceCount;
    }

    public void setProperty(String name, String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getProperty(String name) {

        if (name.equals("Discount")) {
            log.debug("getProperty: " + name + " " + "6.00f");
            return "6.00f";
        }
        log.debug("getProperty: " + name + " " + "CTE123");
        return "CTE123";
    }

    public String getProperty(String name, String defaultValue) {
        log.debug("getProperty: name " + name + " default " + defaultValue);
        return defaultValue;
    }

    public Object executeRuleMethod(String methodName, List<Object> parms) {
        Class<?>[] types = this.getTypes(methodName, parms);

        return execMethod(methodName, types, parms);
    }

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

    public boolean withinDateRange(String startDate, String endDate) {

        log.debug("withinDateRange: " + startDate + " " + endDate);

        try {
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy-mm-dd");

            Date dtStart = dt1.parse(startDate);
            Date dtEnd = dt1.parse(endDate);

            Calendar cs = new GregorianCalendar();
            cs.setTime(dtStart);

            Calendar ce = new GregorianCalendar();
            cs.setTime(dtEnd);

            Calendar now = GregorianCalendar.getInstance();

            if (now.getTimeInMillis() >= cs.getTimeInMillis() && now.getTimeInMillis() < ce.getTimeInMillis()) {
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }

    public void addInsertItemIfAvailableRecursive(List<String> sku) {
        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < sku.size(); i++) {
            buff.append(" " + sku.get(i) );
        }
        log.debug("addInsertItemIfAvailableRecursive: " + buff.toString());
    }

    public void addInsertItemIfAvailableDrools(String sku, int quantity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getQuantityForSKU(String sku) {
        return 1;
    }

    public void addLineItemWithInventory(String sku, int quantity, float itemPrice, float linePrice, String description, String color, String size) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressOne(String addressType) {

        try {
            if ("Billing".equals(addressType)) {
                return "123 Main Bill";
//                return getBillingAddress().getAddress_one();
            } else if ("Shipping".equals(addressType)) {
                return "123 Main Ship";
//                return getShippingAddress().getAddress_one();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressTwo(String addressType) {

        try {
            if ("Billing".equals(addressType)) {
                return "Number B";
//                return getBillingAddress().getAddress_two();
            } else if ("Shipping".equals(addressType)) {
                return "Number S";
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressState(String addressType) {
        try {
            if ("Billing".equals(addressType)) {
                return "WA";
//                return getBillingAddress().getState();
            } else if ("Shipping".equals(addressType)) {
                return "CA";
//                return getShippingAddress().getState();
            }
        } catch (Exception e) {
        }
        return "";    }

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressZip(String addressType) {
        try {
            if ("Billing".equals(addressType)) {
                return "98101";
//                return getBillingAddress().getZip();
            } else if ("Shipping".equals(addressType)) {
                return "90001";
//                return getShippingAddress().getZip();
            }
        } catch (Exception e) {
        }
        return "";    }

    public String getAddressCompanyName(String addressType) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getGroupName() {
        return "G302V2036";
    }

    public void setGroupName(String groupName){

    }

    public int getCountByLineItemInventoryField(String field, String value, boolean equals) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSignatureRequired(boolean required) {

    }

    public float getTotalOrderCost() {
        return 0;
    }

    public int getItemByWeightCount(String operator, float value) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public float getEstimatedTotalLineItemWeight(){
       return 0.0f;
    }

    public void addLineItem(String sku, int quantity, float itemPrice, float linePrice, String description, String color, String size) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendMail(String title, String message, String emailAddress) {
        log.debug("Executing OrderRuleAdapterTest");

        try {
            Mailer.sendMail(title, message, emailAddress);

        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
    public void updateShipMethodAfterTime(String time, String newMethod){}
    public void updateShipMethodAfterTimeShift(String time, String newMethod,int hourShift){}

    public void updateShipCompanyOverride(String name){}
    public void updateShipNameOverride(String name){}
    public void updateShipPhoneOverride(String name){}
    public void updateShipAddress1Override(String name){}
    public void updateShipAddress2Override(String name){}
    public void updateShipCityOverride(String name){}
    public void updateShipStateOverride(String name){}
    public void updateShipZipOverride(String name){}
    public void setExternalShippingKey(String key){}
    public String getPONumber(){return "";}

    public void setSmartPostReturnFlag() throws Exception{}
    public void setMailInnovationsReturnFlag() throws Exception{}

    public void setDDPFlag(int flag){}

    public String getOrderType(){return "";}

    @Override
    public boolean onlyContainsTheseSKUs(List<String> skus) {
        return false;
    }
    public void setBackorderRule(String rule){}
    public void setBusinessOrder(boolean businessOrder){}
    public boolean getBusinessOrder(){return false;}

    // Sean 2020/2/5
    @Override
    public String getComments(){return "";}
    @Override
    public void setGiftMessage(String messager){}
    public void setPostDateHoursDelay(int hours){}
    public void setOrderType(String orderType){}
    public long getPhysicalUnitsCountOnOrder(){return 0;}
    public void addInsertItemIfInventoryAvailable(String sku, int quantity) throws Exception { }
    public void addTagToOrder(String name, String value)throws Exception{}

    public boolean isReceivedAnItemBefore(String client_id, String fullName, String addr1, String city, String lineitem){return false;}
    public boolean isPurchasedBefore(String client_id, String fullName, String addr1, String city){return false;}
    ;
}

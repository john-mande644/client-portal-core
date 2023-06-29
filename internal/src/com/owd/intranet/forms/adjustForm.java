package com.owd.intranet.forms;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.validator.ValidatorActionForm;
import org.apache.struts.action.ActionMapping;
import com.owd.intranet.adjustments.beans.returnItemBean;
import com.owd.intranet.methodList;
import com.owd.intranet.ClientList;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 2:02:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class adjustForm extends ValidatorActionForm {
private final static Logger log =  LogManager.getLogger();


            // --------------------------------------------------------- Instance Variables

            /** FormItems property */
 private returnItemBean[] formItems;

private String receiveId;
private String clientFkey;
private String transactionNum;
private String receiveDate;
private String receiveUser;
private String timeIn;
private String timeOut;
private String totalTime;
private String numEmployees;
private String carrier;
private String blNum;
private String driver;
private String refNum;
private String notes;
private String isVoid;
private String createdDate;
private String createdBy;
private String modifiedDate;
private String modifiedBy;
private String rowIsLocked;
private String type;
private String expectedDate;
private String shipperRef;
private String sku;
private List clientList;
    private List methodsList;
private String orderNum;
    private String orderId;
    private String currentLocation;

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public List getMethodsList() {
        if(null==methodsList){
              setMethodsList();
        }
        return methodsList;
    }

    public void setMethodsList() {
        this.methodsList = methodList.getInstance().getclients();
    }


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List getClientList() {
        if(null==clientList){
            setClientList();
        }
        return clientList;
    }

    public void setClientList() {
        this.clientList =  ClientList.getInstance().getclients();
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(String clientFkey) {
        this.clientFkey = clientFkey;
    }

    public String getTransactionNum() {
        return transactionNum;
    }

    public void setTransactionNum(String transactionNum) {
        this.transactionNum = transactionNum;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getNumEmployees() {
        return numEmployees;
    }

    public void setNumEmployees(String numEmployees) {
        this.numEmployees = numEmployees;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getBlNum() {
        return blNum;
    }

    public void setBlNum(String blNum) {
        this.blNum = blNum;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getVoid() {
        return isVoid;
    }

    public void setVoid(String aVoid) {
        isVoid = aVoid;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getRowIsLocked() {
        return rowIsLocked;
    }

    public void setRowIsLocked(String rowIsLocked) {
        this.rowIsLocked = rowIsLocked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getShipperRef() {
        return shipperRef;
    }

    public void setShipperRef(String shipperRef) {
        this.shipperRef = shipperRef;
    }

    public Pattern getItemPattern() {
        return itemPattern;
    }

    public void setItemPattern(Pattern itemPattern) {
        this.itemPattern = itemPattern;
    }

    // --------------------------------------------------------- Methods



            /**
             * Returns the FormItems.
             * @return Product
             */
            public returnItemBean[] getFormItems() {
                return formItems;
            }

            /**
             * Set the FormItems.
             * @param FormItems The FormItems to set
             */
            public void setFormItems(returnItemBean[] FormItems) {

                this.formItems = FormItems;
            }

        /**
         * Pattern to match request parameters
         */
        private Pattern itemPattern = Pattern.compile("formItems\\[(\\d+)\\].*");

        /**
         * Method reset
         * Dynamically creates the appropriate product array based on the request
         *
         * @param mapping		The Struts Action mapping
         * @param request		The incoming request
         */
        public void reset(ActionMapping mapping, HttpServletRequest request) {

              //log.debug("inreset");
            Enumeration paramNames = request.getParameterNames();
            int maxSize = 0;
            while (paramNames.hasMoreElements())
            {
                String paramName = (String) paramNames.nextElement();
                Matcher itemMatcher = itemPattern.matcher(paramName);
                if (itemMatcher.matches())
                {
                    String index = itemMatcher.group(1);
                    if (Integer.parseInt(index) > maxSize)
                    {
                        maxSize = Integer.parseInt(index);
                    }
                }
            }
            //log.debug("inreset 1");
            formItems = new returnItemBean[maxSize + 1];
            //log.debug("inreset 2");
            for (int i = 0; i <= maxSize; i++)
            {
                formItems[i] = new returnItemBean();
            }

        }
}

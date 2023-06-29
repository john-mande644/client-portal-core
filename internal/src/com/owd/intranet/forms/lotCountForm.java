package com.owd.intranet.forms;

import com.owd.intranet.ClientList;
import com.owd.intranet.adjustments.beans.lotCountItemBean;
import com.owd.intranet.adjustments.beans.returnItemBean;
import com.owd.intranet.methodList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 2:02:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class lotCountForm extends ValidatorActionForm {
private final static Logger log =  LogManager.getLogger();


            // --------------------------------------------------------- Instance Variables

            /** FormItems property */
 private lotCountItemBean[] formItems;

private String clientFkey;
private String receiveDate;
private String receiveUser;
private String createdDate;
private String createdBy;
private String modifiedDate;
private String modifiedBy;
private String sku;
    private String inventoryId;
    private String description;
private List clientList;
    private String currentLocation;
    private String newLotValue;

    public String getNewLotValue() {
        return newLotValue;
    }

    public void setNewLotValue(String newLotValue) {
        this.newLotValue = newLotValue;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(String clientFkey) {
        this.clientFkey = clientFkey;
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


    // --------------------------------------------------------- Methods



            /**
             * Returns the FormItems.
             * @return Product
             */
            public lotCountItemBean[] getFormItems() {
                return formItems;
            }

            /**
             * Set the FormItems.
             * @param FormItems The FormItems to set
             */
            public void setFormItems(lotCountItemBean[] FormItems) {

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

              log.debug("in reset");

            Enumeration paramNames = request.getParameterNames();
            int maxSize = -1;
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
            if(maxSize==-1){
                formItems = new lotCountItemBean[0];

            }   else {
                formItems = new lotCountItemBean[maxSize + 1];
            }
            //log.debug("inreset 2");
            for (int i = 0; i <= maxSize; i++)
            {
                formItems[i] = new lotCountItemBean();
            }


             createdDate = "";
             createdBy = "";
             sku = "";
             inventoryId = null;
             description = "";
            newLotValue="";

        }

    @Override
    public String toString() {
        return "lotCountForm{" +
                "formItems=" + Arrays.toString(formItems) +
                ", clientFkey='" + clientFkey + '\'' +
                ", receiveDate='" + receiveDate + '\'' +
                ", receiveUser='" + receiveUser + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", sku='" + sku + '\'' +
                ", inventoryId='" + inventoryId + '\'' +
                ", description='" + description + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", itemPattern=" + itemPattern +
                '}';
    }
}

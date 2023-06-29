package com.owd.core.business.client;

import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 10, 2003
 * Time: 8:44:22 AM
 * To change this template use Options | File Templates.
 */
public class CustomField {
private final static Logger log =  LogManager.getLogger();
    //Constants
    public final static int displayTypeText = 0;
    public final static int displayTypeRadio = 1;
    public final static int displayTypeSelect = 2;
    public final static int displayTypeTextBox = 3;
    public final static int displayTypeCheckbox = 4;

    //Fields
    int displayType = displayTypeText;
    String displayName = "";
    String fieldName = "";
    String description = "";
    String currentValue = "";
    String defaultValue = "";
    Map choiceList = new TreeMap();  //all String objects - keys are form value, values are displayed choice names

    public Map getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(Map choiceList) {
        this.choiceList = choiceList;
    }

    //Methods


    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public void setDefaultValue(String defValue) {
        this.defaultValue = defValue;
    }

    public String getFormEntryHTML(Order order) throws Exception {
        StringBuffer sb = new StringBuffer();

        if (displayType == displayTypeText) {
            sb.append("<INPUT TYPE=TEXT NAME=\"" + getFieldName() + "\" VALUE=\"" + getCurrentValue() + "\" >");
        } else if (displayType == displayTypeTextBox) {              //TODO add textarea sizing variables

            sb.append("<TEXTAREA NAME=\"" + getFieldName() + "\">" + getCurrentValue() + "</TEXTAREA>");
        } else if (displayType == displayTypeRadio) {
//TODO add radio type
        } else if (displayType == displayTypeSelect) {
            sb.append("<SELECT NAME=\"" + getFieldName() + "\" >");
            for (Iterator iterator = getChoiceList().keySet().iterator(); iterator.hasNext();) {
                String key =  (String) iterator.next();
               sb.append("<OPTION VALUE=\""+key+"\""+(key.equals(getCurrentValue())?" SELECTED":"")+" >"+getChoiceList().get(key));
            }
            sb.append("</SELECT>");
        } else if (displayType == displayTypeCheckbox) {
            sb.append("<INPUT TYPE=CHECKBOX NAME=\"" + getFieldName() + "\" VALUE=\"" + getDefaultValue() + "\" " + (getDefaultValue().equals(getCurrentValue()) ? "CHECKED" : "") + ">");
        } else {
            throw new Exception("Customfield display type not recognized");
        }

        return sb.toString();
    }

}

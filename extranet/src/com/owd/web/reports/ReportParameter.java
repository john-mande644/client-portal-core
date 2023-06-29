package com.owd.web.reports;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 3, 2004
 * Time: 9:18:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportParameter {
private final static Logger log =  LogManager.getLogger();

    String displayName;
    String description;
    String currentValue;
    String defaultValue;
    String formValueName;
    Map optionMap = new TreeMap();
    int paramDataType;

    static public final int kParamTypeDate = 1;
    static public final int kParamTypeText = 2;
    static public final int kParamTypeCheckbox = 3;
    static public final int kParamTypeOptions = 4;


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Map getOptionMap() {
        return optionMap;
    }

    public void setOptionMap(Map newOptionMap) {
        this.optionMap = newOptionMap;
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

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormValueName() {
        return formValueName;
    }

    public void setFormValueName(String formValueName) {
        this.formValueName = formValueName;
    }

    public int getParamDataType() {
        return paramDataType;
    }

    public void setParamDataType(int paramDataType) {
        this.paramDataType = paramDataType;
    }
}

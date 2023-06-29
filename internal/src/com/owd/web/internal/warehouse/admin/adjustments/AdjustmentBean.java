package com.owd.web.internal.warehouse.admin.adjustments;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 20, 2006
 * Time: 3:30:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdjustmentBean {
private final static Logger log =  LogManager.getLogger();
    private List fields;
    private List types;
    private String data;
    private String dateRes;
    private String fromDate;
    private String toDate;
    private String field;
    private String type;
    private String fkey;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey;
    }

    public List getFields() {
        fields = new ArrayList();
        fields.add(0, "All");
        fields.add(1, "Receive Id");
        fields.add(2, "Transaction");
        fields.add(3, "Inventory Id");
        fields.add(4, "Inventory Num");
        fields.add(5, "User");
        return fields;
    }

    public void setFields(List fields) {
        this.fields = fields;
    }

    public List getTypes() {
        types = new ArrayList();
        types.add(0, "contains");
        types.add(1, "is");
        return types;
    }

    public void setTypes(List types) {
        this.types = types;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDateRes() {
        return dateRes;
    }

    public void setDateRes(String dateRes) {
        this.dateRes = dateRes;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}

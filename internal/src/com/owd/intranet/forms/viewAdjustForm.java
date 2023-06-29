package com.owd.intranet.forms;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

import com.owd.intranet.ClientList;
import com.owd.intranet.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 16, 2006
 * Time: 11:07:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class viewAdjustForm extends ActionForm {
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
    private String recId;
    private List clientList;


    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
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
        if(fields==null){
            setFields();
        }
        return fields;
    }

    public void setFields() {
        this.fields = util.loadViewFields();
    }

    public List getTypes() {
        if(types==null){
            setTypes();
        }
        return types;
    }

    public void setTypes() {
        this.types = util.loadViewTypes();
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

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        dateRes=null;
      
    }

    }



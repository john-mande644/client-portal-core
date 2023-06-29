package com.owd.callcenter.forms.util;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jun 4, 2007
 * Time: 3:55:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class callForm extends ActionForm {
    private String info;
    private String numberOfCalls;
    private String calls;
    private String time;
    private String all;


    public String getCalls() {
        return calls;
    }

    public void setCalls(String calls) {
        this.calls = calls;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(String numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

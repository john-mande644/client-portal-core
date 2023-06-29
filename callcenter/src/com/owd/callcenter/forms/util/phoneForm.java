package com.owd.callcenter.forms.util;

import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 13, 2006
 * Time: 11:38:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class phoneForm extends ActionForm {
    private String exten;
   private List phones;
    private String lastModified;


    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public List getPhones() {
        return phones;
    }

    public void setPhones(List phones) {
        this.phones = phones;
    }

    public String getExten() {
        return exten;
    }

    public void setExten(String exten) {
        this.exten = exten;
    }
}

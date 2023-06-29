package com.owd.callcenter.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 4, 2006
 * Time: 11:48:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ajaxForm extends ActionForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

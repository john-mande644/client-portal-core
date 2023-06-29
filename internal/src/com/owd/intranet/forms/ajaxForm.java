package com.owd.intranet.forms;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 4, 2006
 * Time: 11:48:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ajaxForm extends ActionForm {
private final static Logger log =  LogManager.getLogger();
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

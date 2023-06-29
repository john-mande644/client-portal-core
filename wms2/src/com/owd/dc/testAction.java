package com.owd.dc;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 3, 2009
 * Time: 11:48:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class testAction extends ActionSupport {

    private String mes;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String execute() throws Exception {

        Logger log = LogManager.getLogger();
        log.debug(mes);

        System.out.println("running inhere i guess");
        return "SUCCESS";

    }
}

package com.owd.web.internal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.web.OWDWebAuthenticationService;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 30, 2008
 * Time: 5:10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChangeClientAction extends ActionSupport implements SessionAware {
private final static Logger log =  LogManager.getLogger();



    Map session;

    public String execute()
    {

        OWDWebAuthenticationService.setSessionString(session, OWDWebAuthenticationService.kExtranetClientID,getChangeClientId());
        return "return";

    }
    public String getRedirectAction() {
        return redirectAction;
    }

    public void setRedirectAction(String redirectAction) {
        this.redirectAction = redirectAction;
    }

    public String getRedirectNamespace() {
        return redirectNamespace;
    }

    public void setRedirectNamespace(String redirectNamespace) {
        this.redirectNamespace = redirectNamespace;
    }

    public String getChangeClientId() {
        return changeClientId;
    }

    public void setChangeClientId(String changeClientId) {
        this.changeClientId = changeClientId;
    }

    String redirectAction;
    String redirectNamespace;
    String changeClientId;


    public void setSession(Map map) {
session=map;    }
}

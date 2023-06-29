package com.owd.dc.actions;

import org.apache.struts.actions.LookupDispatchAction;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 17, 2008
 * Time: 2:15:23 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class OWDLookupDispatchAction extends LookupDispatchAction {

    public static final Object tokenCheckSyncLock = new Object();

    public boolean isCurrentRequest(HttpServletRequest request) {
        synchronized (tokenCheckSyncLock) {
            boolean test = isTokenValid(request);
            resetToken(request);
            return test;
        }
    }

}

package com.owd.dc.actions;

import org.apache.struts.action.Action;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 17, 2008
 * Time: 2:20:05 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class OWDAction extends Action {
    
    public static final Object tokenCheckSyncLock = new Object();

     public boolean isCurrentRequest(HttpServletRequest request) {
         synchronized (tokenCheckSyncLock) {
             boolean test = isTokenValid(request);
             resetToken(request);
             return test;
         }
     }

}

package com.owd.dc.actions;

import com.owd.dc.inventorycount.wInventoryUtilities;
import com.owd.hibernate.HibernateSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.dc.checkAuthentication;
import org.apache.struts2.ServletActionContext;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2005
 * Time: 3:50:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class forwardFormAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                return (mapping.findForward("login"));
            }
             request.getSession(true).setAttribute("zlocation","");
            request.getSession(true).setAttribute("multiBarcodeClientFkey","");
            request.setAttribute("step","start");
            for(Cookie c: request.getCookies()){
                if(c.getName().equals("tcid")){

                    String employeeId = c.getValue();
                    request.setAttribute("tc_id",employeeId);
                    System.out.println("Setting empoleeid to "+c.getValue());
                    Boolean admin = wInventoryUtilities.isInventoryAdmin(Integer.parseInt(employeeId), HibernateSession.currentSession());
                    request.getSession().setAttribute("inventoryAdmin",admin);
                }
            }
            return (mapping.findForward("success"));
        } finally {

          //  HibernateTimeForceSession.closeSession();
        //    HibernateSession.closeSession();
        }
    }
}
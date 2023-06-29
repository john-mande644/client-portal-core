package com.owd.dc.inventory.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.dc.checkAuthentication;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 4, 2005
 * Time: 12:57:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class doneInventoryAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

                  request.getSession(true).setAttribute("doinginventoryid","");
                  request.getSession(true).setAttribute("doinginventoryclientname","");
                  request.getSession(true).setAttribute("doinginventoryclientfkey","");
                  request.getSession(true).setAttribute("doinginventorylocation", "");
                  return (mapping.findForward("success"));


} catch(Exception e){
         request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }finally{
          //  HibernateSession.closeSession();
        }

    }
}


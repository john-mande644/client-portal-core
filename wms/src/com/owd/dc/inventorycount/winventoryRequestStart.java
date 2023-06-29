package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.inventoryUtilities;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 22, 2005
 * Time: 4:03:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class winventoryRequestStart extends Action {

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
            List clientList = new ArrayList();

             request.setAttribute("clientList", inventoryUtilities.getClientList());
          return  mapping.findForward("success");

    }catch(Exception e){
            request.setAttribute("clientList", inventoryUtilities.getClientList());
            e.printStackTrace();
            request.setAttribute("error",e.getMessage());
            return(mapping.findForward("error"));
        }

    }
}
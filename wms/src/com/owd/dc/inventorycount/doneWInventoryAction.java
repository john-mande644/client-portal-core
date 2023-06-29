package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 4, 2005
 * Time: 12:57:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class doneWInventoryAction extends Action {

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

                  request.getSession(true).setAttribute("doingwinventoryid","");
                  request.getSession(true).setAttribute("doingwinventoryclientname","");
                  request.getSession(true).setAttribute("doingwinventoryclientfkey","");
              List openRequest = wInventoryUtilities.getOpenRequests();
               request.setAttribute("wopenrequests", openRequest);
                  return (mapping.findForward("success"));


} catch(Exception e){
         request.setAttribute("error",e.getMessage());
            return (mapping.findForward("success"));
        }finally{
          //  HibernateSession.closeSession();
        }

    }
}


package com.owd.dc.inventory.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.dc.checkAuthentication;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 4, 2005
 * Time: 12:44:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class doInventoryAction extends Action {

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

            String id = (String) request.getSession(true).getAttribute("doinginventoryid");
            String clientname= (String) request.getSession(true).getAttribute("doinginventoryid");
            String clientfkey = (String) request.getSession(true).getAttribute("doinginventoryid");
             if(id.equals("")||clientname.equals("")||clientfkey.equals("")){
              return (mapping.findForward("success"));
             }

            return (mapping.findForward("counting"));

        }catch(Exception e){
            return (mapping.findForward("success"));

        }finally {
        


          //  HibernateSession.closeSession();
        }
    }
}


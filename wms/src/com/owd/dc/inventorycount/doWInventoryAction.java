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
 * Time: 12:44:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class doWInventoryAction extends Action {

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

            String id = (String) request.getSession(true).getAttribute("doingwinventoryid");
            String clientname= (String) request.getSession(true).getAttribute("doingwinventoryid");


             if(id.equals("")||clientname.equals("")){
                    List openRequest = wInventoryUtilities.getOpenRequests();
               request.setAttribute("wopenrequests", openRequest);
               System.out.println("In if");
              return (mapping.findForward("success"));
             }
              List wlocationsforrequest = wInventoryUtilities.getLocationsForRequest(id);
                  request.setAttribute("wlocationsforrequest",wlocationsforrequest);
            return (mapping.findForward("counting"));

        }catch(Exception e){
               List openRequest = wInventoryUtilities.getOpenRequests();
             request.setAttribute("wopenrequests", openRequest);
           // e.printStackTrace();
            return (mapping.findForward("success"));

        }finally {
        


          //  HibernateSession.closeSession();
        }
    }
}


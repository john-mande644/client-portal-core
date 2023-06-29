package com.owd.dc.receiving.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.searchForm;
import com.owd.dc.inventory.inventoryUtilities;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 26, 2006
 * Time: 3:07:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class searchAction extends LookupDispatchAction{

    protected Map getKeyMethodMap() {
            System.out.println("getKeyMethodMap invoked..");
            Map map = new HashMap();
            map.put("button.asnSearch", "doSearch");
            map.put("button.packNo", "noSlip");

            return map;
        }

   public ActionForward unspecified(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
            throws Exception {
        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            searchForm sf = (searchForm) form;
            sf.setClients(inventoryUtilities.getClientList());
           return (mapping.findForward("success"));

    }catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }


/// packing slip found go on.........................................................
    public ActionForward packingSlip(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
            throws Exception {
        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            searchForm sf = (searchForm) form;
           return (mapping.findForward("success"));

    }catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    /// Do this if no packing slip button is pushed................................................................
   public ActionForward noSlip(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
            throws Exception {
        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            searchForm sf = (searchForm) form;

           return (mapping.findForward("success"));

    }catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

}

package com.owd.dc.receiving.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.core.managers.SerialNumberManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 16, 2006
 * Time: 3:59:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class receiveGetSerialAction extends Action {


    public ActionForward execute(ActionMapping mapping,
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
            receiveForm rcvForm = (receiveForm) form;

            if(SerialNumberManager.serialExists((OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,  Integer.valueOf(rcvForm.getInvId())),rcvForm.getSerialNumber())){
                throw new Exception("Serial Already Exists for this Sku");

            }
           return (mapping.findForward("success"));
        } catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

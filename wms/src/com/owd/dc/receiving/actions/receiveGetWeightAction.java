package com.owd.dc.receiving.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 6, 2006
 * Time: 8:33:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class receiveGetWeightAction  extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)  throws Exception {
        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            receiveForm rcvForm = (receiveForm) form;
            try {
               // Float.parseFloat(rcvForm.getWeight());
                rcvForm.setWeight(Float.parseFloat(rcvForm.getWeight())+"");
                //Integer.decode(rcvForm.getDamaged());
            } catch (NumberFormatException nex) {
               // invBean ib = receivingUtilities.loadInventoryBean(rcvForm.getInvId());
                //request.setAttribute("ib", ib);
                request.setAttribute("error", "Weight must be numeric");
                return mapping.findForward("error");
            }
            if("edit".equals(rcvForm.getAction())){
                return mapping.findForward("edit");
            }

            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

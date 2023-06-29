package com.owd.dc.receiving.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.receivingUtilities;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 5, 2006
 * Time: 9:03:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class receiveGetDamagedAction extends Action {


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
            saveToken(request);
            receiveForm rcvForm = (receiveForm) form;
            try {
                Integer.decode(rcvForm.getDamaged());
            } catch (NumberFormatException nex) {
               // invBean ib = receivingUtilities.loadInventoryBean(rcvForm.getInvId());
                //request.setAttribute("ib", ib);
                request.setAttribute("error", "Damaged quanity must be numeric");
                return mapping.findForward("error");
            }
            if("edit".equals(rcvForm.getAction())){
                return mapping.findForward("edit");
            }
            if(receivingUtilities.needWeightForReceiveItem(rcvForm.getInvId())){
                System.out.println("in need weight");
                return (mapping.findForward("weight"));
            } else{
                rcvForm.setWeight(receivingUtilities.getSkuWeight(rcvForm.getInvId()));
            }



            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

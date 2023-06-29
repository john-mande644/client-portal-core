package com.owd.dc.receiving.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.receivingUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.ReceiveItem;
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
 * Time: 4:04:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class receiveVerifySerialAction  extends Action {


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
            if(rcvForm.getvSerialNumber().equals("change")){
                rcvForm.setSerialNumber("");
               return mapping.findForward("change");
            }
            if(!rcvForm.getSerialNumber().equals(rcvForm.getvSerialNumber())){
               throw new Exception("Serials do not Match, please verify");
            }
            receivingUtilities.enterNewSerial(rcvForm.getSerialNumber(), rcvForm.getReceiveItemId());
            if(!receivingUtilities.allSerialRecorded(rcvForm.getReceiveItemId(), rcvForm.getCount())){
                return mapping.findForward("next");
            }
            ReceiveItem ri = (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class,  Integer.valueOf(rcvForm.getReceiveItemId()));
            receivingUtilities.updateReceiveItem(rcvForm,ri);
           return (mapping.findForward("success"));
        } catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

package com.owd.dc.receiving.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.receivingUtilities;
import com.owd.dc.actions.OWDLookupDispatchAction;
import com.owd.dc.inventory.beans.invBean;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.ReceiveItem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 6, 2006
 * Time: 9:46:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class receiveVerifyAction extends OWDLookupDispatchAction {


    protected Map getKeyMethodMap() {
            System.out.println("getKeyMethodMap invoked..");
            Map map = new HashMap();
            map.put("button.editQuanity", "editQuanity");
            map.put("button.editDamaged", "editDamaged");
            map.put("button.editWeight", "editWeight");
            map.put("button.verify", "verify");
            return map;
        }
   //action for edit quanity
   public ActionForward editQuanity(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            saveToken(request);
            receiveForm rcvForm = (receiveForm) form;
           invBean ib =  receivingUtilities.loadInventoryBean(rcvForm.getInvId(), WMSUtils.getFacilityFromRequest(request));
               request.setAttribute("ib", ib);
            rcvForm.setAction("edit");
            return (mapping.findForward("quanity"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

 public ActionForward editDamaged(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            saveToken(request);
            receiveForm rcvForm = (receiveForm) form;
            rcvForm.setAction("edit");
            return (mapping.findForward("damaged"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }


     public ActionForward editWeight(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            saveToken(request);
            receiveForm rcvForm = (receiveForm) form;
            rcvForm.setAction("edit");
            return (mapping.findForward("weight"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

   public ActionForward verify(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            receiveForm rcvForm = (receiveForm) form;



           ReceiveItem ri = (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class, Integer.valueOf(rcvForm.getReceiveItemId()));


            if(!rcvForm.getInvId().equals(ri.getInventoryFkey()+"")) {
                System.out.println("BAD BAD BAD");
                request.setAttribute("error", "There was an error, the sku you are working on was not saved");
                return mapping.findForward("reset");
            }
            //check for serial numbers required
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,  Integer.valueOf(rcvForm.getInvId()));
            if(inv.getRequireSerialNumbers()==1){
                return mapping.findForward("serial");
            }
        System.out.println(rcvForm.getRcvId());
        if(isCurrentRequest(request)){

            receivingUtilities.updateReceiveItem(rcvForm,ri);


        } else{
            request.setAttribute("error","Already saved");
            return mapping.findForward("success");
        }
        if("yes".equals(rcvForm.getDoLabelPrint())){
            return (mapping.findForward("printLabel"));
        }
           return (mapping.findForward("success"));

        } catch (Exception ex) {
        
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

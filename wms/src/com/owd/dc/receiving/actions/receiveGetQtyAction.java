package com.owd.dc.receiving.actions;

import com.owd.WMSUtils;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.receivingUtilities;
import com.owd.dc.inventory.LabelUtilities;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.setup.buttons;
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
 * Date: Mar 16, 2006
 * Time: 2:48:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class receiveGetQtyAction extends LookupDispatchAction{

    protected Map getKeyMethodMap() {
            System.out.println("getKeyMethodMap invoked..");
            Map map = new HashMap();
            map.put("button.submitQty", "quanity");
            map.put("button.printLabel", "printLabel");
            map.put("button.printLargeLabel", "printLargeLabel");
            map.put("button.palletTag","printPalletTag");
            return map;
        }
    public ActionForward quanity(ActionMapping mapping,
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
         try{   Integer.decode(rcvForm.getCount());}catch (NumberFormatException nex){
                invBean ib =  receivingUtilities.loadInventoryBean(rcvForm.getInvId(), WMSUtils.getFacilityFromRequest(request));
               request.setAttribute("ib", ib);
                request.setAttribute("error","Quanity must be numeric");
                return mapping.findForward("error");  }

            if("edit".equals(rcvForm.getAction())){
                return mapping.findForward("edit");
            }
           return (mapping.findForward("success"));

    }catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
    public ActionForward printPalletTag(ActionMapping mapping,
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
               if(Integer.parseInt(rcvForm.getLabelCount())>100){
                   rcvForm.setLabelCount("100");
               }
               // buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
              // buttons btn = new buttons();
              // LabelUtilities.barcodeLabel(rcvForm.getInvId(),Integer.parseInt(rcvForm.getLabelCount()),btn.getSmallPrinter(request.getAttribute("loginName").toString()),request.getServerName());
               LabelUtilities.printPalletTag(HandheldUtilities.loadInv(new Integer(rcvForm.getInvId())),Integer.parseInt(rcvForm.getLabelCount()),HandheldUtilities.getPalletTagPrinterForUser(request.getAttribute("loginName").toString()));

                 request.setAttribute("outcome", "Printed palletTag");
              return (mapping.findForward("error"));

       }catch (Exception ex) {
              ex.printStackTrace();
               request.setAttribute("error", ex.getMessage());
               return mapping.findForward("error");

           }
       }
   public ActionForward printLabel(ActionMapping mapping,
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
            if(Integer.parseInt(rcvForm.getLabelCount())>100){
                rcvForm.setLabelCount("100");
            }
             //buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
            buttons btn = new buttons();
            LabelUtilities.barcodeLabel(rcvForm.getInvId(),Integer.parseInt(rcvForm.getLabelCount()),btn.getSmallPrinter(request.getAttribute("loginName").toString()),request.getServerName());
              request.setAttribute("outcome", "Printed label");
           return (mapping.findForward("error"));

    }catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
     public ActionForward printLargeLabel(ActionMapping mapping,
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
            if(Integer.parseInt(rcvForm.getLabelCount())>100){
                rcvForm.setLabelCount("100");
            }
            buttons btn = new buttons();
             // buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
            LabelUtilities.printLargeInventoryLabel(rcvForm.getInvId(),Integer.parseInt(rcvForm.getLabelCount()),btn.getPrinter(request.getAttribute("loginName").toString()),request.getServerName());
              request.setAttribute("outcome", "Printed Large Label");
           return (mapping.findForward("error"));

    }catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

package com.owd.dc.receiving.actions;

import com.owd.dc.HandheldUtilities;
import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.inventory.LabelUtilities;
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
 * User: Danny
 * Date: Jul 5, 2006
 * Time: 2:39:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class receivePrintLabelsAction extends LookupDispatchAction{


    protected Map getKeyMethodMap() {
            System.out.println("getKeyMethodMap invoked..");
            Map map = new HashMap();
            map.put("button.printLabel", "printLabel");
            map.put("button.noPrint", "noPrint");
          map.put("button.printLargeLabel", "printLargeLabel");
           map.put("button.palletTag","printPalletTag");
            return map;
        }
   //action for edit quanity
   public ActionForward printPalletTag(ActionMapping mapping,
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
               if(Integer.parseInt(rcvForm.getLabelCount())>100){
                   rcvForm.setLabelCount("100");
               }
               // buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");

           // LabelUtilities.printLocationLabel_Large(Integer.parseInt(rcvForm.getInvId()),Integer.parseInt(rcvForm.getLabelCount()),btn.getPrinter());
           //      LabelUtilities.printLargeInventoryLabel(rcvForm.getInvId(),Integer.parseInt(rcvForm.getLabelCount()),btn.getPrinter(request.getAttribute("loginName").toString()),request.getServerName());
           LabelUtilities.printPalletTag(HandheldUtilities.loadInv(new Integer(rcvForm.getInvId())),Integer.parseInt(rcvForm.getLabelCount()),HandheldUtilities.getPalletTagPrinterForUser(request.getAttribute("loginName").toString()));

             request.setAttribute("outcome", "Printed PalletTag");
           return mapping.findForward("success");

           } catch (Exception ex) {
               ex.printStackTrace();
               request.setAttribute("error", ex.getMessage());
               return mapping.findForward("error");

           }
       }
    public ActionForward printLargeLabel(ActionMapping mapping,
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
            if(Integer.parseInt(rcvForm.getLabelCount())>100){
                rcvForm.setLabelCount("100");
            }
          //   buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
        buttons btn = new buttons();
        // LabelUtilities.printLocationLabel_Large(Integer.parseInt(rcvForm.getInvId()),Integer.parseInt(rcvForm.getLabelCount()),btn.getPrinter());
              LabelUtilities.printLargeInventoryLabel(rcvForm.getInvId(),Integer.parseInt(rcvForm.getLabelCount()),btn.getPrinter(request.getAttribute("loginName").toString()),request.getServerName());
              request.setAttribute("outcome", "Printed label");
        return mapping.findForward("success");

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
   public ActionForward printLabel(ActionMapping mapping,
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
            if(Integer.parseInt(rcvForm.getLabelCount())>100){
                rcvForm.setLabelCount("100");
            }
             //buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
        buttons btn = new buttons();
        // LabelUtilities.printLocationLabel_Large(Integer.parseInt(rcvForm.getInvId()),Integer.parseInt(rcvForm.getLabelCount()),btn.getPrinter());
              LabelUtilities.barcodeLabel(rcvForm.getInvId(),Integer.parseInt(rcvForm.getLabelCount()),btn.getSmallPrinter(request.getAttribute("loginName").toString()),request.getServerName());
              request.setAttribute("outcome", "Printed label");
        return mapping.findForward("success");

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

 public ActionForward noPrint(ActionMapping mapping,
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

          return mapping.findForward("success");

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }



}

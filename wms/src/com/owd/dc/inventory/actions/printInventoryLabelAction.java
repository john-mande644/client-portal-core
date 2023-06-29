package com.owd.dc.inventory.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

import com.owd.dc.inventory.LabelUtilities;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.labelDTO;
import com.owd.dc.inventory.forms.labelForm;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.setup.buttons;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 10, 2005
 * Time: 4:01:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class printInventoryLabelAction extends LookupDispatchAction {

    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.invLabel", "invLabel");
        map.put("button.palletTag", "palletTag");
        map.put("button.none", "none");
        map.put("button.printLargeLabel","invLargeLabel");

        return map;
    }

    public ActionForward invLabel(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
          String action= new String();
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }


            labelForm labelForm = (labelForm) form;
            labelDTO labelDTO = new labelDTO();
            BeanUtils.copyProperties(labelDTO, labelForm);
             action = labelDTO.getAction();
            if (action.equals("find")){
                skuForm s = new skuForm();
                s.setSku(labelDTO.getSku()+"");
                request.setAttribute("skuForm", s);
            }
            if(labelDTO.getNumlabels()<1){
                request.setAttribute("searchSku",labelDTO);
                throw new Exception("Label Quanity Must be At least One");
            }
            //print large inventory label
            // buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
            buttons btn = new buttons();
            LabelUtilities.barcodeLabel(labelDTO.getSku()+"",labelDTO.getNumlabels(),btn.getSmallPrinter(request.getAttribute("loginName").toString()),request.getServerName());
            //set outcome
            request.setAttribute("outcome","Printed label for "+labelDTO.getSku());
            //return action specified in form so it goes to proper page referenced from
                    System.out.println((mapping.findForward(action)));
            return (mapping.findForward(action));


        } catch (Exception e) {

          request.setAttribute("error",e.getMessage());
            return (mapping.findForward(action+"error"));
        }
    }
    public ActionForward invLargeLabel(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
          String action= new String();
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }


            labelForm labelForm = (labelForm) form;
            labelDTO labelDTO = new labelDTO();
            BeanUtils.copyProperties(labelDTO, labelForm);
             action = labelDTO.getAction();
            if (action.equals("find")){
                skuForm s = new skuForm();
                s.setSku(labelDTO.getSku()+"");
                request.setAttribute("skuForm", s);
            }
            if(labelDTO.getNumlabels()<1){
                request.setAttribute("searchSku",labelDTO);
                throw new Exception("Label Quanity Must be At least One");
            }
            //print large inventory label
           //  buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
           buttons btn = new buttons();

            LabelUtilities.printLargeInventoryLabel(labelDTO.getSku()+"",labelDTO.getNumlabels(),btn.getPrinter(request.getAttribute("loginName").toString()),request.getServerName());
            //set outcome
            request.setAttribute("outcome","Printed label for "+labelDTO.getSku());
            //return action specified in form so it goes to proper page referenced from
                    System.out.println((mapping.findForward(action)));
            return (mapping.findForward(action));


        } catch (Exception e) {

          request.setAttribute("error",e.getMessage());
            return (mapping.findForward(action+"error"));
        }
    }
    public ActionForward palletTag(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
          String action= new String();
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }


            labelForm labelForm = (labelForm) form;
            labelDTO labelDTO = new labelDTO();
            BeanUtils.copyProperties(labelDTO, labelForm);
             action = labelDTO.getAction();
            if (action.equals("find")){
                skuForm s = new skuForm();
                s.setSku(labelDTO.getSku()+"");
                request.setAttribute("skuForm", s);
            }
            if(labelDTO.getNumlabels()<1){
                request.setAttribute("searchSku",labelDTO);
                throw new Exception("Label Quanity Must be At least One");
            }
            //print large inventory label
            // buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
            LabelUtilities.printPalletTag(HandheldUtilities.loadInv(new Integer(labelDTO.getSku())),labelDTO.getNumlabels(),HandheldUtilities.getPalletTagPrinterForUser(request.getAttribute("loginName").toString()));
           // LabelUtilities.printLocationLabel_Large(labelDTO.getSku(),labelDTO.getNumlabels(),btn.getPrinter());
            //set outcome
            request.setAttribute("outcome","Printed Pallet Tag for "+labelDTO.getSku());
            //return action specified in form so it goes to proper page referenced from
                    System.out.println((mapping.findForward(action)));
            return (mapping.findForward(action));


        } catch (Exception e) {
            e.printStackTrace();

          request.setAttribute("error",e.getMessage());
            return (mapping.findForward(action+"error"));
        }
    }
}

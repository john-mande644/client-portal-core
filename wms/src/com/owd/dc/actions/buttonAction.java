package com.owd.dc.actions;

import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.setup.buttons;
import com.owd.dc.checkAuthentication;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Aug 26, 2005
 * Time: 9:01:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class buttonAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            checkAuthentication.check(request, response);
        } catch (Exception ex) {
            return (mapping.findForward("login"));
        }
        skuForm skuForm = (skuForm) form;
        skuDTO skuDTO = new skuDTO();
        BeanUtils.copyProperties(skuDTO, skuForm);
        String key = skuDTO.getSku();
        try{
        buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
          if (key.equals("25")) {
           return (mapping.findForward(btn.getT5()));
        }
        if (key.equals("26")) {
            return (mapping.findForward(btn.getT6()));
        }
        if (key.equals("28")) {
            return (mapping.findForward(btn.getT8()));
        }
        if (key.equals("27")) {
            return (mapping.findForward(btn.getT7()));
        }
        if (key.equals("red")) {
            return (mapping.findForward(btn.getRed()));
        }
        if (key.equals("green")) {
            return (mapping.findForward(btn.getGreen()));
        }
        }catch (Exception e){
          System.out.println("Error in setting meta button attribute");

        if (key.equals("25")) {

            return (mapping.findForward("find"));
        }
        if (key.equals("26")) {
            return (mapping.findForward("assign"));
        }
        if (key.equals("28")) {
            return (mapping.findForward("doinventory"));
        }
        if (key.equals("27")) {
            return (mapping.findForward("upc"));
        }
        if (key.equals("green")) {
            return (mapping.findForward("pick"));
        }
         if (key.equals("red")) {
            return (mapping.findForward("remove"));
        }

        }
         return (mapping.findForward("main"));
    }
}
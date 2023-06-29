package com.owd.dc.inventory.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.dc.inventory.LabelUtilities;
import com.owd.dc.setup.buttons;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.checkAuthentication;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: May 9, 2005
 * Time: 10:44:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class printSkuEndAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            skuForm skuForm = (skuForm) form;
            skuDTO skuDTO = new skuDTO();
            BeanUtils.copyProperties(skuDTO, skuForm);
            System.out.println("copied beans assign variables");
            String sku = skuDTO.getSku();
            int number_of_labels=skuDTO.getNumlabels();
            if (number_of_labels >100){
                request.setAttribute("error", "You must enter a number less than 100");
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,new Integer(sku));
                request.setAttribute("Sku", inv);
                return mapping.findForward("error");
            }
             // buttons btn = (buttons) request.getAttribute("hhMetaButtonsAction");
            buttons btn = new buttons();
             LabelUtilities.barcodeLabel(sku,number_of_labels,btn.getSmallPrinter(request.getAttribute("loginName").toString()),request.getServerName());
            request.setAttribute("outcome", "printed " + number_of_labels + " Labels for sku " + sku);
            return (mapping.findForward("success"));
        } catch (Exception ex){
             request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));
        }
        finally {

          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
        }
    }
}

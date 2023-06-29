package com.owd.dc.inventory.actions;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.beans.skuBean;
import com.owd.dc.inventory.forms.skuForm;
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
 * Date: Apr 18, 2005
 * Time: 9:48:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class upcAssignStartAction extends Action {


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
            String sku = skuDTO.getSku();
            if (sku.startsWith("/")) throw new Exception ("Invalid Sku - please Scan valid Sku");
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,new Integer(sku));
            skuBean info = new skuBean();
            info.setInventoryId(inv.getInventoryId().toString());
            info.setInventoryNum(inv.getInventoryNum());
            info.setDescription(inv.getDescription());
            request.setAttribute("skuinfo",info);

            return (mapping.findForward("success"));
        } catch (Exception ex){
             request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));
        }
        finally {

           // HibernateTimeForceSession.closeSession();
           // HibernateSession.closeSession();
        }
    }

}   


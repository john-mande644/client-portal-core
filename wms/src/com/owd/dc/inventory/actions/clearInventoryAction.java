package com.owd.dc.inventory.actions;

import com.owd.hibernate.generated.WarehouseInventoryCounts;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.*;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.checkAuthentication;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 4, 2005
 * Time: 1:04:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class clearInventoryAction extends Action {

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
            WarehouseInventoryCounts wcount = new WarehouseInventoryCounts();
          wcount = (WarehouseInventoryCounts) HibernateSession.currentSession().load(WarehouseInventoryCounts.class, new Integer(skuDTO.getSku()));
         int count = wcount.getQuanity();
        int id = wcount.getInventoryId();
          HibernateSession.currentSession().delete(wcount);
          HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            request.setAttribute("outcome","Deleted qty. " + count +" for " + id);
          return (mapping.findForward("success"));

} catch(Exception e){
         request.setAttribute("error",e);
            return (mapping.findForward("error"));
        }

    }
}


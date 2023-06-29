package com.owd.dc.inventorycount;


import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.invBean;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
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
 * Date: Oct 3, 2005
 * Time: 10:19:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class wgetQuanityAction extends Action {

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


            wCountForm wCountForm = (wCountForm) form;
            wCountDTO wCountDTO = new wCountDTO();
            BeanUtils.copyProperties(wCountDTO, wCountForm);
            try{
            Integer test = Integer.decode(wCountDTO.getQuanity());
            }catch (NumberFormatException ex){
                 OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(wCountDTO.getInventoryId()));
                invBean ib = wInventoryUtilities.setInvBean(inv);
                request.setAttribute("ib",ib);
                throw new Exception("Quanity must have a value and be numeric");
            }


            return (mapping.findForward("success"));
        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}
package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WInvCounts;
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
 * Date: Jan 18, 2006
 * Time: 8:27:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class editWcountAction extends Action {

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


            editWCountForm editWCountForm = (editWCountForm) form;
            editWCountDTO editWCountDTO = new editWCountDTO();
            BeanUtils.copyProperties(editWCountDTO, editWCountForm);
            try{
            WInvCounts wic = (WInvCounts) HibernateSession.currentSession().load(WInvCounts.class, editWCountDTO.getwCountId());
           editWCountForm.setOldQuanity(wic.getQuanity()+"");
            editWCountForm.setInventoryId(wic.getInventoryId()+"");   

            }catch (Exception ex){

                throw new Exception("Error occured, please report miscount for manual adjust");
            }


            return (mapping.findForward("success"));
        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }

}

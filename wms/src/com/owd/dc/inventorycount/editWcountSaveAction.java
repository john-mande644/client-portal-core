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
 * Time: 9:05:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class editWcountSaveAction extends Action {

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
          wic.setQuanity(editWCountDTO.getQuanity());
            HibernateSession.currentSession().saveOrUpdate(wic);
                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

            }catch (Exception ex){

                throw new Exception("Error inserting new count, please try again");
            }
          wCountForm wc = new wCountForm();
                wc.setLocation(editWCountDTO.getLocation());
            wc.setRequestId(editWCountDTO.getRequestId());
            request.setAttribute("wCountForm",wc);
            return (mapping.findForward("success"));
        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }

}

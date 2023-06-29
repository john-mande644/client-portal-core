package com.owd.callcenter.wrapup.admin;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.callcenter.forms.wrapup.outcomeForm;
import com.owd.callcenter.wrapup.admin.ccUtil;
import com.owd.hibernate.HibernateSession;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 21, 2006
 * Time: 4:08:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class removeCallTypeAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
           outcomeForm of = (outcomeForm) form;
            if(null==of.getType()||"".equals(of.getType())){
                throw new Exception("No Call type Selected!!!!!");
            }
           ccUtil.removeCallType(HibernateSession.currentSession(),of.getType());
           of.setCallTypeList(ccUtil.getCallType(of.getCampaign()));
            of.setOutcomeList(null);
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }
}

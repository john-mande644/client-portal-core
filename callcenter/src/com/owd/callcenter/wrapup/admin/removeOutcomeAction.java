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
 * Time: 3:38:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class removeOutcomeAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
           outcomeForm of = (outcomeForm) form;
            if(null==of.getOutcome()||"".equals(of.getOutcome())){
                throw new Exception("No Outcome selected!!!");
            }
           ccUtil.removeOutcomeType(HibernateSession.currentSession(),of.getOutcome());
             of.setOutcomeList(ccUtil.getOutcome(of.getType()));

            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }
}

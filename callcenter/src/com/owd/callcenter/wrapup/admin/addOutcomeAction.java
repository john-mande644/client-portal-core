package com.owd.callcenter.wrapup.admin;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.owd.hibernate.HibernateSession;
import com.owd.callcenter.forms.wrapup.outcomeForm;
import com.owd.callcenter.wrapup.admin.ccUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 21, 2006
 * Time: 2:35:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class addOutcomeAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            outcomeForm of = (outcomeForm) form;
            if (null == of.getCampaign() || "".equals(of.getCampaign())) {
                throw new Exception("Campaign is null");
            }
            if (null == of.getOutcomeAdd() || "".equals(of.getOutcomeAdd())) {
                throw new Exception("Outcome is empty");
            }
            ccUtil.addOutcomeType(HibernateSession.currentSession(), of.getCampaign(), of.getOutcomeAdd(), of.getType());
            of.setOutcomeList(ccUtil.getOutcome(of.getType()));
            of.setOutcomeAdd("");
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

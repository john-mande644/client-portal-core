package com.owd.callcenter.wrapup;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.callcenter.forms.wrapup.recordOutcomeForm;
import com.owd.callcenter.wrapup.admin.ccUtil;
import com.owd.hibernate.HibernateSession;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 22, 2006
 * Time: 10:42:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class wrapupAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            recordOutcomeForm rof = (recordOutcomeForm) form;
            if (null == rof.getCampaign() || "".equals(rof.getCampaign())) {
                throw new Exception("No Campaign set");
            }
            if (null == rof.getSource()){
                rof.setSource("");
            }
            rof.setTypeList(null);
            rof.setOutcomeList(null);
            rof.setCallTypeId(null);
            rof.setResult(null);
            rof.setOutcome(null);
            rof.setNotes(null);
            rof.setCustomerId(null);
            rof.setOrderFormv(null);
            rof.setSubtotal(null);
            rof.setOrderId(null);
            rof.setCrosssell(null);
            rof.setOrderForm(ccUtil.getDoOrder(HibernateSession.currentSession(),rof.getCampaign()));
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }
}

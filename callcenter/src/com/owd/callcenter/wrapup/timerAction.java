package com.owd.callcenter.wrapup;

import org.apache.struts.action.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.callcenter.forms.wrapup.recordOutcomeForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jan 22, 2007
 * Time: 1:34:24 PM
 * To change this template use File | Settings | File Templates
 */
public class timerAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            recordOutcomeForm rof = (recordOutcomeForm) form;
            System.out.println("Ladkeeedislekekekekeekek");
            rof.setAgentid("me");
             return mapping.findForward("success");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }
}

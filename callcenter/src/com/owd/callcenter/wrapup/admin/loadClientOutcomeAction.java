package com.owd.callcenter.wrapup.admin;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.callcenter.forms.wrapup.outcomeForm;
import com.owd.callcenter.wrapup.admin.ccUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 18, 2006
 * Time: 4:09:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class loadClientOutcomeAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
           outcomeForm of = (outcomeForm) form;

           of.setCallTypeList(ccUtil.getCallType(of.getCampaign()));
            of.setOutcomeList(null);
            of.setType(null);
            of.setOutcome(null);
            of.setDoOrder(null);
            of.setDefaultText(null);
            of.setNumOfCallType(String.valueOf(of.getCallTypeList().size()));
            System.out.println("number of call types = " + of.getNumOfCallType());
            
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

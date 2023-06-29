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
 * Time: 2:11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class loadOutcomeAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
           outcomeForm of = (outcomeForm) form;
           System.out.println("id "+of.getType() );
            
           of.setOutcomeList(ccUtil.getOutcome(of.getType()));
           of.setNumOfOutcomes(String.valueOf(of.getOutcomeList().size()));
            System.out.println("number of outcomes = " + of.getNumOfOutcomes());
            of.setDefaultText(ccUtil.getDefaultText(HibernateSession.currentSession(), of.getType()));
            System.out.println("ccutil.getdefaulttext = " + ccUtil.getDefaultText(HibernateSession.currentSession(), of.getType()));

             of.setDoOrder(ccUtil.getDoOrderBox(HibernateSession.currentSession(), of.getType()));
            System.out.println("doOrder set to" + ccUtil.getDoOrderBox(HibernateSession.currentSession(), of.getType()));

            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

package com.owd.callcenter.wrapup.admin;

import com.owd.callcenter.forms.wrapup.outcomeForm;
import com.owd.hibernate.HibernateSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 18, 2006
 * Time: 4:32:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class addClientItemAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
           outcomeForm of = (outcomeForm) form;
           if(null==of.getCampaign()||"".equals(of.getCampaign())){
               throw new Exception("Campaign is null");
           }
           if(null==of.getTypeAdd()||"".equals(of.getTypeAdd())){
               throw new Exception("Call Type is empty");
           }
           ccUtil.addCallType(HibernateSession.currentSession(),of.getCampaign(),of.getTypeAdd());
           of.setCallTypeList(ccUtil.getCallType(of.getCampaign()));
           of.setTypeAdd("");
            of.setNumOfCallType(String.valueOf(of.getCallTypeList().size()));
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

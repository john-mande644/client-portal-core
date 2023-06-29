package com.owd.callcenter.wrapup.admin;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.callcenter.wrapup.admin.ccUtil;
import com.owd.callcenter.forms.wrapup.outcomeForm;
import com.owd.hibernate.HibernateSession;

/**
 * Created by IntelliJ IDEA.
 * User: Carter
 * Date: Jun 6, 2007
 * Time: 3:47:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class changeDefaultTextAction extends Action {

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
          
           ccUtil.setDefaultText(HibernateSession.currentSession(), of.getType(), of.getDefaultText());
           of.setDoOrder(ccUtil.getDoOrderBox(HibernateSession.currentSession(), of.getType())); 
            
            System.out.println("Changed default outcome text to " + '"' + of.getDefaultText() + '"');
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }

}

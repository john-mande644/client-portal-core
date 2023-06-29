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
 * User: Carter
 * Date: Jun 12, 2007
 * Time: 1:52:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class doOrderAction extends Action{

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


           String currentValue = ccUtil.getDoOrderBox(HibernateSession.currentSession(), of.getType());
           String newValue;
            System.out.println("currentValue = " + currentValue);
           if(currentValue=="0"){
               newValue="1";
           } else{
               newValue = "0";
           }



           ccUtil.setDoOrder(HibernateSession.currentSession(), of.getType(), newValue);
            of.setDoOrder(ccUtil.getDoOrderBox(HibernateSession.currentSession(), of.getType()));
            System.out.println("Changed do order status to " + newValue );
            System.out.println("DoOrder actually changed to" + of.getDoOrder());

            
            return (mapping.findForward("success"));

        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }

}


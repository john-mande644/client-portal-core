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
 * Time: 11:12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class wrapupRecordAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
           recordOutcomeForm rof = (recordOutcomeForm) form;
            System.out.println("1");
            if(rof.getOrderFormv().equals("1")){
           rof.setSubtotal(stripmoney(rof.getSubtotal()));
                 if(rof.getOrderId().indexOf(".")>0){
                throw new Exception("You appear to have the Order Number and Total mixed up.  Please check this!!");
            }
                if(rof.getCampaign().equals("RAZORAMA")){
                rof.setOrderId(rof.getOrderId().replaceAll("razorama\\-",""));
            }
            }
            org.apache.struts.action.ActionMessages errors = rof.validate(mapping,request);
           System.out.println(errors.size());
            if(!errors.isEmpty()){
             saveErrors(request,errors);
               
                throw new Exception("notValid");
            }

           
            System.out.println("2");
           if(null==rof.getCampaign()||"".equals(rof.getCampaign())){
               throw new Exception("No Campaign set");
           }
           if(null==rof.getOutcome()){
               System.out.println("nully");
               
               rof.setOutcome("");
           }
           ccUtil.insertOutcomeRecord(HibernateSession.currentSession(), rof);


                ccUtil.recordCCOrder(HibernateSession.currentSession(),rof);


            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors",e.getMessage());
            return mapping.findForward("error");

        }
    }
    private static String stripmoney(String amount) {
        String newamount = amount.replace('$', ' ');
        return newamount.trim();
    }
}

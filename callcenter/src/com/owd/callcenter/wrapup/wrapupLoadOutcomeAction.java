package com.owd.callcenter.wrapup;

import com.owd.callcenter.wrapup.admin.ccUtil;
import com.owd.callcenter.forms.wrapup.recordOutcomeForm;
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
 * Date: Aug 22, 2006
 * Time: 10:53:00 AM
 */
public class wrapupLoadOutcomeAction extends Action {

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
            if (!rof.getResult().equals("No Call")) {
                rof.setOutcomeList(ccUtil.getOutcome(rof.getCallTypeId()));
            }
            /*Iterator it = rof.getTypeList().iterator();
            if("1".equals(rof.getOrderForm())) {
            while (it.hasNext()){
                selectList sl = (selectList) it.next();
                if (sl.getAction().equals(rof.getCallTypeId())){
                    System.out.println(sl.getDisplay());
                    if("Placed Order".equals(sl.getDisplay())){
                        rof.setOrderFormv("1");
break; 
                    }else{
                        rof.setOrderFormv("0");
                        break;
                    }
                }
            }
            } */
            rof.setNotes(ccUtil.getDefaultText(HibernateSession.currentSession(), rof.getCallTypeId()));
            rof.setOrderFormv(ccUtil.getDoOrderBox(HibernateSession.currentSession(), rof.getCallTypeId()));
           if (!rof.getCrosssell().equals("1")){
               System.out.println("Crossell: "+rof.getCrosssell());
              rof.setCrosssell(null);
           }
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }
}

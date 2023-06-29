package com.owd.callcenter.wrapup;

import com.owd.callcenter.beans.selectList;
import com.owd.callcenter.wrapup.admin.ccUtil;
import com.owd.callcenter.forms.wrapup.recordOutcomeForm;
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
 * Time: 11:14:47 AM
 */
public class wrapupLoadTypeAction extends Action {

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
            if (rof.getResult().equals("Contact")) {
                rof.setTypeList(ccUtil.getCallType(rof.getCampaign()));
                if(rof.getTypeList().size()>0){
                selectList sl = (selectList) rof.getTypeList().get(0);
                rof.setOutcomeList(ccUtil.getOutcome(sl.getAction()));
                }
            } else if(rof.getResult().equals("No Call")){
                rof.setTypeList(ccUtil.loadNoContactTypes());
                rof.setOutcomeList(null);
                rof.setOutcome(null);
            }  else{
                rof.setTypeList(null);
                rof.setOutcomeList(null);
                rof.setOutcome(null);
            }
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }
}

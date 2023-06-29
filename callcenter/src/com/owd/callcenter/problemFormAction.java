package com.owd.callcenter;

import com.owd.callcenter.forms.problemFormForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Sep 15, 2006
 * Time: 10:46:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class problemFormAction extends LookupDispatchAction {


    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.problemForm", "recordForm");
        map.put("button.problemFormreset", "clearForm");

        return map;
    }

    //Default action, forwads to askinf about packing slip
    public ActionForward unspecified(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        try {


            problemFormForm pfForm = (problemFormForm) form;
           // pfForm.setClientList();
            //pfForm.setIssueList();

            return (mapping.findForward("success"));


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
    public ActionForward recordForm(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        try {

//--------------------------------------------------------------------------------------------
            System.out.println("checking pf");
            problemFormForm pfForm = (problemFormForm) form;
            pfForm.setEmail(pfForm.getEmail().trim());
            pfForm.setPhone(pfForm.getPhone().trim());
            org.apache.struts.action.ActionMessages errors = pfForm.validate(mapping,request);
            if(!errors.isEmpty()){
             saveErrors(request,errors);
                System.out.println(errors);
                throw new Exception("notValid");
            }

            System.out.println("sending pf");
            pfForm = problemFormUtil.sendProblemForm(pfForm, request);


            return (mapping.findForward("sent"));


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
    public ActionForward clearForm(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        try {


            problemFormForm pfForm = (problemFormForm) form;

             pfForm = reset(pfForm);
            return (mapping.findForward("success"));


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
    private problemFormForm reset(problemFormForm pf){
             pf.setClient("");
        pf.setClientReference("");
        pf.setEmail("");
        pf.setType("");
        pf.setIssue("");
        pf.setName("");
        pf.setOwdOrder("");
        pf.setPassword("");
        pf.setPhone("");
        pf.setRequest("");
        pf.setNeeds("");
        pf.setSubject("");

        return pf;
    }
}

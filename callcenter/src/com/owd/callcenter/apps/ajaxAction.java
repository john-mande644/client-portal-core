package com.owd.callcenter.apps;

import com.owd.callcenter.forms.ajaxForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 4, 2006
 * Time: 11:46:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class ajaxAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {

           ajaxForm af = (ajaxForm) form;


            return (mapping.findForward("success"));
        } catch (Exception e) {
            return mapping.findForward("error");

        }
    }
}

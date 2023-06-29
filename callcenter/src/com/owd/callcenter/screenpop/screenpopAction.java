package com.owd.callcenter.screenpop;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.owd.callcenter.forms.spForm;


/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 1, 2007
 * Time: 2:24:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class screenpopAction extends Action {
     public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {



            spForm sf = (spForm) form;
          javax.servlet.http.Cookie callid = new Cookie("OWDCALLID", sf.getCallId());

    callid.setDomain(".owd.com");
    response.addCookie(callid);
           sf.setDoScript(null);

sf.setUrlStatus(null);
sf.setUrlService(null);

sf.setKb(null);
sf.setQuickRef(null);


   sf.setScript(null);
            sf.setExtras(null);
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }










}

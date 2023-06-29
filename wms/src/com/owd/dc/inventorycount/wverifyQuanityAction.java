package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Feb 24, 2006
 * Time: 10:16:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class wverifyQuanityAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }


            wCountForm wCountForm = (wCountForm) form;
            //wCountDTO wCountDTO = new wCountDTO();
           // BeanUtils.copyProperties(wCountDTO, wCountForm);
            if (wCountForm.getVerified().equals("yes")){
                saveToken(request);
               return (mapping.findForward("yes"));
            }
           return(mapping.findForward("no"));


        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}

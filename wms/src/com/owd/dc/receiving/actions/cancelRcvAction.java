package com.owd.dc.receiving.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.dc.HandheldUtilities;
import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.beans.asnIDDTO;
import com.owd.dc.receiving.forms.asnIDForm;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Aug 10, 2005
 * Time: 1:23:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class cancelRcvAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
        asnIDForm asnIDForm = (asnIDForm) form;
            asnIDDTO asnIDDTO = new asnIDDTO();
            BeanUtils.copyProperties(asnIDDTO, asnIDForm);
            int asnId= asnIDDTO.getAsnid();
        HandheldUtilities.cancelRcv(asnId);
       request.setAttribute("step", "start");
       return (mapping.findForward("success"));
}
}
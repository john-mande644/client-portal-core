package com.owd.intranet.apps;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

import com.owd.intranet.forms.returnForm;
import com.owd.intranet.forms.ajaxForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 4, 2006
 * Time: 11:46:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class ajaxAction extends Action {
private final static Logger log =  LogManager.getLogger();

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

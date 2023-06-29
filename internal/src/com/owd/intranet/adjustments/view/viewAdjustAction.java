package com.owd.intranet.adjustments.view;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

import com.owd.intranet.forms.adjustForm;
import com.owd.intranet.forms.viewAdjustForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 16, 2006
 * Time: 11:09:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class viewAdjustAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            viewAdjustForm af = (viewAdjustForm) form;
            
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

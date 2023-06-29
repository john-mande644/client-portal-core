package com.owd.intranet.adjustments.view;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.intranet.forms.viewAdjustForm;
import com.owd.intranet.adjustments.adjustUtil;
import com.owd.hibernate.generated.OwdReceive;
import com.owd.hibernate.HibernateSession;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 17, 2006
 * Time: 11:00:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class voidAdjustAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            viewAdjustForm af = (viewAdjustForm) form;

           OwdReceive rec = adjustUtil.voidAdjustment(HibernateSession.currentSession(), af.getRecId(),request.getRemoteUser());
            request.getSession(true).setAttribute("rec",rec);
            request.setAttribute("outcome","Voided Adjustment "+rec.getTransactionNum());
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }
}

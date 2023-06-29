package com.owd.intranet.adjustments;

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
import com.owd.intranet.forms.adjustForm;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 2, 2006
 * Time: 3:45:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class beginAdjustAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
             String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
           Calendar cal = Calendar.getInstance();
           adjustForm af = (adjustForm) form;
            af.setReceiveUser(request.getRemoteUser());
            af.setReceiveDate(sdf.format(cal.getTime()));
            af.setTimeIn(sdf.format(cal.getTime()));
            //log.debug("Setting Adjust stuff");
            af.setCarrier("UPS Ground");
            af.setOrderNum("");
            af.setRefNum("");
            af.setNotes("");
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

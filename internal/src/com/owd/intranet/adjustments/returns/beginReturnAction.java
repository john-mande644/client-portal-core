package com.owd.intranet.adjustments.returns;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.owd.intranet.forms.returnForm;

import java.util.Calendar;


/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 2, 2006
 * Time: 3:45:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class beginReturnAction extends Action {
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
            log.debug("Starting Return");
          //     request.getSession().invalidate();
            
             returnForm rf = new returnForm();


            
            rf.setReceiveUser(request.getRemoteUser());
            rf.setReceiveDate(sdf.format(cal.getTime()));
            rf.setTimeIn(sdf.format(cal.getTime()));
            //log.debug("Setting stuff");
            rf.setCarrier("UPS Ground");
            rf.setOrderNum("");
            rf.setRefNum("");
            rf.setNotes("");
            rf.setNewShipCreated("");
            rf.setOrderId("");

            rf.setBlNum("");
            rf.setDriver("");
            request.getSession(true).setAttribute("returnForm",rf);
            //rf.setCurrentLocation("LOC");
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

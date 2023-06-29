package com.owd.dc.picking.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.picking.PickUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickStatus;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 2:52:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickStatusCancelAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        try {

            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            try {

                OrderPickStatus pstat = PickUtilities.getCurrentPickStatus(request);
                //check that pickstatus obj is present
                if (pstat == null) {

                    request.setAttribute("error", "No order picking record found!");
                } else {

                    PickUtilities.cancelPickByOrderNum(request, (pstat.getOrderNum()));

                    PickUtilities.clearSessionPickStatus(request);


                    PickUtilities.clearCurrentPickStatus(pstat, request);


                    HibernateSession.currentSession().flush();
                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());


                }
            } catch (Exception ex) {

                request.setAttribute("error", ex.getMessage());
                ex.printStackTrace();
            } finally {
                // HibernateSession.closeSession();
            }


            return (mapping.findForward("newpick"));


        } finally {
            // HibernateTimeForceSession.closeSession();
            // HibernateSession.closeSession();
        }
    }
}
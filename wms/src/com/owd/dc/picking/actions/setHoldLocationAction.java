package com.owd.dc.picking.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.picking.PickUtilities;
import com.owd.dc.picking.forms.holdLocationForm;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickStatus;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class setHoldLocationAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        OrderPickStatus pstat = null;
        System.out.println("starting setHoldLocationAction");
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            System.out.println("checked login");
            holdLocationForm holdLocationForm = (holdLocationForm) form;

            System.out.println(holdLocationForm.getPickStatusId());
            pstat = (OrderPickStatus) HibernateSession.currentSession().load(OrderPickStatus.class, Integer.valueOf(holdLocationForm.getPickStatusId()));
            request.setAttribute("pstat", pstat);
            pstat.setHoldLocation(Integer.valueOf(holdLocationForm.getHold_location().substring(2)));
            boolean orderDone = false;
            System.out.println("created pstat object");

            orderDone = PickUtilities.markPickComplete(request, pstat, true);
            System.out.println(orderDone);
            return (mapping.findForward("success"));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));
        }

    }
}

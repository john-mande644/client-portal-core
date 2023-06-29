package com.owd.dc.picking.actions;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.generated.OrderPickItem;
import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.dc.picking.beans.pickStatusBean;
import com.owd.dc.picking.PickUtilities;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 8:53:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class pickAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        try {
            checkAuthentication.check(request, response);
        } catch (Exception ex) {
            return (mapping.findForward("login"));
        }
        if (null == request.getSession().getAttribute("pickPriority")) {
            System.out.println("Null pick priority set to default of 1");
            request.getSession().setAttribute("pickPriority", "1");
        }
        request.setAttribute("action", "pick-start");
        request.setAttribute("priority", request.getSession().getAttribute("pickPriority"));
        OrderPickStatus pstat = PickUtilities.getCurrentPickStatus(request);

        if (pstat == null) {
            return (mapping.findForward("success"));
        } else {
            try {
                pickStatusBean pickInfo = new pickStatusBean();
                pickInfo.setOrderNum(pstat.getOrderNum());

                pickInfo.setCurrentItem(pstat.getCurrentItemIndex() + 1);
                pickInfo.setNumberOfItems(pstat.getOrderPickItems().size());
                pickInfo.setCurrentPicked(((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]).getQtyPicked());
                pickInfo.setCurrentTotal(((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]).getQtyToPick());
                pickInfo.setSku(((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]).getSku());
                request.setAttribute("pickInfo", pickInfo);
                return (mapping.findForward("recover"));
            } catch (Exception e) {
                return (mapping.findForward("success"));
            }
        }


    }
}



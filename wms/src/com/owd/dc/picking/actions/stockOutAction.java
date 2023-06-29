package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.dc.picking.beans.pickItemBean;
import com.owd.dc.picking.beans.replenishDTO;
import com.owd.dc.picking.forms.replenishForm;
import com.owd.dc.picking.forms.holdForm;
import com.owd.dc.picking.PickUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 27, 2005
 * Time: 3:57:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class stockOutAction extends Action {


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

            replenishForm replenishForm = (replenishForm) form;
            replenishDTO replenishDTO = new replenishDTO();
            BeanUtils.copyProperties(replenishDTO, replenishForm);

            OrderPickStatus pstat = (OrderPickStatus) HibernateSession.currentSession().load(OrderPickStatus.class, new Integer(replenishDTO.getPickStatusId()));
            String priority = request.getSession().getAttribute("pickPriority").toString();
            pickItemBean pickItem = PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority);
            holdForm holdForm = PickUtilities.loadHoldForm(pickItem, (String) request.getAttribute("loginName"));
            request.setAttribute("holdForm", holdForm);
            PickUtilities.sendStockOutEmail(pstat, pickItem, replenishDTO.getLocation(), WMSUtils.getFacilityFromRequest(request));
            request.setAttribute("outcome", "Sent Notification for" + replenishDTO.getLocation());
            request.setAttribute("pickItem", pickItem);
            request.setAttribute("startreplenishtime", replenishDTO.getStartTime());
            return (mapping.findForward("success"));
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            request.setAttribute("error", e.getMessage());
            return (mapping.findForward("error"));

        } finally {

            //  HibernateTimeForceSession.closeSession();
            //  HibernateSession.closeSession();
        }
    }

}

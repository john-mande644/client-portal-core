package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.dc.picking.beans.pickItemBean;
import com.owd.dc.picking.beans.replenishDTO;
import com.owd.dc.picking.forms.replenishForm;
import com.owd.dc.picking.PickUtilities;
import org.apache.commons.beanutils.BeanUtils;
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
 * Date: Oct 27, 2005
 * Time: 8:52:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class replenishEndAction extends Action {
    int pickstat = 0;
    replenishDTO replenishDTO = new replenishDTO();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            String priority = request.getSession().getAttribute("pickPriority").toString();
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
            replenishForm replenishForm = (replenishForm) form;

            BeanUtils.copyProperties(replenishDTO, replenishForm);
            pickstat = replenishDTO.getPickStatusId();
            System.out.println(Calendar.getInstance().getTime());
            System.out.println(sdf.parse(replenishDTO.getStartTime()));
            int rtime = Math.round((Calendar.getInstance().getTime().getTime() - sdf.parse(replenishDTO.getStartTime()).getTime()) / 1000);
            System.out.println("rtime " + rtime);
            PickUtilities.insertReplenishTime(rtime, replenishDTO.getPickItemId());
            // OrderPickStatus pstat = (OrderPickStatus) HibernateSession.currentSession().load(OrderPickStatus.class, new Integer(replenishDTO.getPickStatusId()));
            pickItemBean pickItem = PickUtilities.loadPickItemBean((OrderPickStatus) HibernateSession.currentSession().load(OrderPickStatus.class, new Integer(replenishDTO.getPickStatusId())), WMSUtils.getFacilityFromRequest(request), priority);

            request.setAttribute("pickItem", pickItem);
            return (mapping.findForward("success"));
        } catch (Exception e) {
            OrderPickStatus pstat = (OrderPickStatus) HibernateSession.currentSession().load(OrderPickStatus.class, new Integer(pickstat));
            String priority = request.getSession().getAttribute("pickPriority").toString();
            pickItemBean pickItem = PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority);

            request.setAttribute("startreplenishtime", replenishDTO.getStartTime());
            request.setAttribute("pickItem", pickItem);
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return (mapping.findForward("error"));

        } finally {

            // HibernateTimeForceSession.closeSession();
            // HibernateSession.closeSession();
        }
    }


}

package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.dc.picking.PickUtilities;
import com.owd.dc.picking.beans.replenishDTO;
import com.owd.dc.picking.forms.replenishForm;
import com.owd.hibernate.generated.OrderPickStatus;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by danny on 12/19/2016.
 */
public class skipLineAction extends Action {


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

            PickUtilities.skipCurrentLine(replenishDTO.getPickStatusId(), replenishDTO.getPickItemId());


            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            String priority = request.getSession().getAttribute("pickPriority").toString();
            OrderPickStatus pstat = PickUtilities.getCurrentPickStatus(request);
            request.setAttribute("pickItem", PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority));

            return (mapping.findForward("error"));

        }

    }

}

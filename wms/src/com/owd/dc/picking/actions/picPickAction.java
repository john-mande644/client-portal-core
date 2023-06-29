package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.generated.OrderPickItem;
import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.dc.checkAuthentication;
import com.owd.dc.picking.PickUtilities;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.beans.skuDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 19, 2007
 * Time: 11:15:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class picPickAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        OrderPickItem curritem = null;

        try {
            checkAuthentication.check(request, response);
        } catch (Exception ex) {
            return (mapping.findForward("login"));
        }

        skuForm skuForm = (skuForm) form;
        skuDTO skuDTO = new skuDTO();
        BeanUtils.copyProperties(skuDTO, skuForm);

        request.setAttribute("action", "pick-item");
        OrderPickStatus pstat = PickUtilities.getCurrentPickStatus(request);
        System.out.println("Setting pick item");
        String priority = request.getSession().getAttribute("pickPriority").toString();
        request.setAttribute("pickItem", PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority));
        return mapping.findForward("success");

    }
}

package com.owd.dc.returns.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.returns.returnUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 10, 2005
 * Time: 8:34:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class returnLoadOrderAction extends Action {

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
            skuForm skuForm = (skuForm) form;
            skuDTO skuDTO = new skuDTO();
            BeanUtils.copyProperties(skuDTO, skuForm);

            String barcode = "*" + skuDTO.getSku() + "*";
            String getOrderId = "select order_id from owd_order (NOLOCK) where order_num_barcode ='" + barcode + "'";
            ResultSet rs = HibernateSession.getResultSet(getOrderId);
            HashMap skus = new HashMap();
            if (rs.next()) {
                skus = returnUtilities.getOrderItemList(rs.getString(1),request,response);
             } else {
                String getId = "select order_id from owd_order (NOLOCK) where order_num ='" + skuDTO.getSku() + "'";
                ResultSet rs1 = HibernateSession.getResultSet(getId);
                if (rs1.next()) {
                skus = returnUtilities.getOrderItemList(rs1.getString(1),request,response);
                } else {
                    throw new Exception("Unable to load order from" + skuDTO.getSku());
                }
            }
            request.getSession(true).setAttribute("skus",skus);
            return (mapping.findForward("success"));
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}

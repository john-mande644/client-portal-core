package com.owd.dc.picking.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.picking.PickUtilities;
import com.owd.core.TimeStamp;
import com.owd.core.managers.SerialNumberManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 10:45:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class pickCancelAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        TimeStamp ts1 = new
                TimeStamp("pickCancelAction begin");
        try {

            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            skuForm skuForm = (skuForm) form;
            skuDTO skuDTO = new skuDTO();
            BeanUtils.copyProperties(skuDTO, skuForm);
            String orderNum = skuDTO.getSku();

            try {
                //check that pickstatus obj is present

                if(orderNum.contains("R")){
                    orderNum = orderNum.substring(0,orderNum.indexOf("R"));
                }
                PickUtilities.cancelPickByOrderNum(request, orderNum);

                PickUtilities.clearSessionPickStatus(request);

                OrderPickStatus pstat = PickUtilities.getCurrentPickStatus(request);
                String getId = "select order_id from owd_order (NOLOCK) where order_num = '" + orderNum + "'";
                ResultSet rs = HibernateSession.getResultSet(getId);
                System.out.println("quiry for order number");
                rs.next();
                System.out.println("rsnext");
                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load((OwdOrder.class), new Integer(rs.getString(1)));
                SerialNumberManager.clearSerialNumberForPickedOrder(HibernateSession.currentSession(), order);
                if (pstat != null) {
                    PickUtilities.clearCurrentPickStatus(pstat, request);
                }
                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

                request.setAttribute("priority", request.getSession().getAttribute("pickPriority"));
            } catch (Exception ex) {

                request.setAttribute("error", ex.getMessage());
                ex.printStackTrace();
            } finally {
                //HibernateSession.closeSession();
            }


            return (mapping.findForward("success"));


        } finally {
            ts1.print("pickCancelAction end");
            //  HibernateTimeForceSession.closeSession();
            //  HibernateSession.closeSession();
        }
    }
}

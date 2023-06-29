package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.dc.picking.beans.pickItemBean;
import com.owd.dc.picking.forms.holdForm;
import com.owd.dc.picking.forms.serialForm;
import com.owd.dc.picking.PickUtilities;
import com.owd.core.TimeStamp;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickItem;
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
 * Time: 2:48:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickcontinueAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        TimeStamp ts1 = new
                TimeStamp("pickcontinueAction begin");
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            OrderPickStatus pstat = PickUtilities.getCurrentPickStatus(request);
            try {
                //check that pickstatus obj is present


                if (pstat != null) {

                    PickUtilities.updateSessionPickStatusFromOld(request, pstat);

                } else {
                    throw new Exception("Error in continuing pick. Please cancel old pick and start over.");
                }

                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

            } catch (Exception ex) {

                request.setAttribute("error", ex.getMessage());
                ex.printStackTrace();
            }

            if (request.getAttribute("error") != null) {
                return (mapping.findForward("recover"));
            } else {
                OrderPickItem curritem = curritem = ((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]);
                if ((curritem.getIsSerialized().intValue()) > 0 && curritem.getQtyPicked() > 0) {
                    serialForm sf = new serialForm();
                    sf.setDescription(curritem.getSkuDesc());
                    sf.setInvId(curritem.getItemBarcode() + "");
                    sf.setInvNum(curritem.getSku());
                    sf.setpItemId(curritem.getId().toString());
                    sf.setCurrentNum(curritem.getSerials().split(",").length + "");
                    sf.setTotal(curritem.getQtyToPick() + "");
                    request.setAttribute("serialForm", sf);
                    return mapping.findForward("serial");

                }


                request.getSession(true).setAttribute("itemdisptime", cal.getTime());
                String priority = request.getSession().getAttribute("pickPriority").toString();
                pickItemBean pickItem = PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority);
                holdForm holdForm = PickUtilities.loadHoldForm(pickItem, (String) request.getAttribute("loginName"));
                request.setAttribute("holdForm", holdForm);
                request.setAttribute("pickItem", PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority));
                saveToken(request);
                return (mapping.findForward("success"));
            }

        } finally {
            ts1.print("pickcontinueAction End");
            //  HibernateTimeForceSession.closeSession();
            //  HibernateSession.closeSession();
        }
    }
}

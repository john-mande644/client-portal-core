package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
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
 * Time: 1:47:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickAllAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        TimeStamp ts1 = new
                TimeStamp("pickAllAction begin");
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
            request.setAttribute("action", "pick-all");
            if (true) {
                boolean orderDone = false;
                boolean pstatOK = false;
                try {
                    //check that pickstatus obj is present

                    if (pstat == null) {
                        request.setAttribute("error", "No order picking record found - rescan order number");
                    } else {
                        pstatOK = true;


                        OrderPickItem curritem = ((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]);

                        //validate against current SKU
                        //    String sku = curritem.getItemBarcode() + "";

                        //if good, check if item done
                        curritem.setQtyPicked(curritem.getQtyToPick());
                        //  pstat.getOrderPickItems().remove(curritem);
                        //  pstat.getOrderPickItems().add(curritem);

                        if (curritem.getQtyPicked() == curritem.getQtyToPick()) {
                            //done with this item
                            curritem.setEndTime(cal.getTime());
                            //more items
                            pstat.setCurrentItemIndex(pstat.getCurrentItemIndex() + 1);


                        }

                        if (pstat.getCurrentItemIndex() == pstat.getOrderPickItems().size()) {
                            //completed - back to pick order screen
                            System.out.println("got last item in pick item");
                            HibernateSession.currentSession().refresh(pstat);
                            orderDone = PickUtilities.markPickComplete(request, pstat, false);


                        } else {
                            PickUtilities.saveCurrentPickStatus(pstat, request);
                        }

                        HibernateSession.currentSession().flush();
                        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

                    }
                } catch (Exception ex) {

                    request.setAttribute("error", ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    // HibernateSession.closeSession();
                }

                if (orderDone && pstatOK) {
                    return (mapping.findForward("newpick"));
                } else if (pstatOK) {
                    request.getSession(true).setAttribute("itemdisptime", cal.getTime());
                    String priority = request.getSession().getAttribute("pickPriority").toString();
                    request.setAttribute("pickItem", PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority));
                    return (mapping.findForward("success"));
                } else {
                    return (mapping.findForward("newpick"));
                }
            } else {
                return (mapping.findForward("recover"));
            }
        } finally {
            ts1.print("pickAllAction end");
            // HibernateTimeForceSession.closeSession();
            // HibernateSession.closeSession();
        }
    }


}

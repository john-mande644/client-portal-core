package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.dc.picking.beans.pickItemBean;
import com.owd.dc.picking.beans.replenishDTO;
import com.owd.dc.picking.forms.holdForm;
import com.owd.dc.picking.PickUtilities;
import com.owd.dc.picking.forms.replenishForm;
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
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 21, 2005
 * Time: 11:38:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class setHoldAction extends Action {

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


            holdForm holdForm = (holdForm) form;
            if (holdForm.getReplenishStart() == null || holdForm.getReplenishStart().equals("")) {
                String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
                holdForm.setReplenishStart(sdf.format(Calendar.getInstance().getTime()));
            }

            //  locationDTO locDTO = new locationDTO();
            // BeanUtils.copyProperties(locDTO, locForm);
            //done with this item
            System.out.println("Pick Item ID from form " + holdForm.getPickItemId());
            OrderPickItem curritem = (OrderPickItem) HibernateSession.currentSession().load(OrderPickItem.class, Integer.valueOf(holdForm.getPickItemId()));
            String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);

            int rtime = Math.round((Calendar.getInstance().getTime().getTime() - sdf.parse(holdForm.getReplenishStart()).getTime()) / 1000);
            System.out.println("rtime " + rtime);
            curritem.setReplenishTime(curritem.getReplenishTime() + rtime);
            curritem.setDcHold(1);
            OrderPickStatus pstat = curritem.getOrderPickStatu();
            if (pstat.getDcHold() == 0) {
                pstat.setDcHold(1);
            }
            Calendar cal = Calendar.getInstance();
            Date end = cal.getTime();
            curritem.setEndTime(end);
            if (curritem.getStartTime() == null) {
                curritem.setStartTime((Date) request.getSession(true).getAttribute("itemdisptime"));
            }
            if (curritem.getFirstPickTime() == null) {
                curritem.setFirstPickTime((Date) request.getSession(true).getAttribute("itemdisptime"));
            }

            // check for other and if so details
            if (holdForm.getHoldReason().equalsIgnoreCase("other")) {
                if (holdForm.getHoldNotes().equals("")) {
                    HibernateSession.currentSession().save(pstat);
                    HibernateSession.currentSession().flush();
                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                    return (mapping.findForward("holdDetails"));
                } else {
                    pstat.setHoldNotes(pstat.getHoldNotes().equals("") ? holdForm.getHoldNotes() : pstat.getHoldNotes() + "</br>\n" + holdForm.getHoldNotes());
                }
            }
            //check for stockout
            if (holdForm.getHoldReason().equalsIgnoreCase("stockout")) {
                String priority = request.getSession().getAttribute("pickPriority").toString();
                pickItemBean pickItem = PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority);
                replenishDTO replenishDTO = new replenishDTO();
                replenishDTO.setInventoryId(Integer.parseInt(holdForm.getInventoryId()));
                replenishDTO.setPickItemId(Integer.parseInt(holdForm.getPickItemId()));
                replenishDTO.setStartTime(holdForm.getReplenishStart());
                replenishDTO.setPickStatusId(pstat.getId());
                replenishDTO.setLocation(pickItem.getLocFirst());
                try {
                    PickUtilities.sendStockOutEmail(pstat, pickItem, replenishDTO.getLocation(), WMSUtils.getFacilityFromRequest(request));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            //more items
            pstat.setCurrentItemIndex(pstat.getCurrentItemIndex() + 1);
            boolean orderDone = false;
            pstat.setHoldReason(holdForm.getHoldReason());
            HibernateSession.currentSession().save(pstat);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            if (pstat.getCurrentItemIndex() == pstat.getOrderPickItems().size()) {
                //completed - back to pick order screen
                System.out.println("got last item in pick itemssss");
                HibernateSession.currentSession().refresh(pstat);
                request.setAttribute("pstat", pstat);

//                orderDone = PickUtilities.markPickComplete(request, pstat, true);
//                System.out.println(orderDone);
                return (mapping.findForward("dchold"));
            }
            request.getSession(true).setAttribute("itemdisptime", cal.getTime());
            String priority = request.getSession().getAttribute("pickPriority").toString();
            request.setAttribute("pickItem", PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority));
            /*       OwdOrderShipHold holder = new OwdOrderShipHold();
                holder = OrderFactory.getNewOwdOrderShipHold();
               OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(holdForm.getOrderNum());
             holder.setWhHoldReason("Inventory Stockout");
             holder.setResNotifyAM("1");
             holder.setNotifyAM("1");
            WarehouseHoldUtilities.setPostedOrderOnHold(holdForm,order, holder);
               request.setAttribute("holdForm", holdForm);*/

            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));
        } finally {

            //HibernateTimeForceSession.closeSession();
            //HibernateSession.closeSession();
        }


    }
}
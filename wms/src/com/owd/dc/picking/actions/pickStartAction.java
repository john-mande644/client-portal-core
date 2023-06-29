package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.core.TimeStamp;
import com.owd.dc.picking.beans.pickItemBean;
import com.owd.dc.picking.forms.holdForm;
import com.owd.dc.utilities.shipmethods.shipMethodUtils;
import com.owd.core.managers.InventoryManager;
import com.owd.dc.picking.itemSortingUtilities;
import com.owd.dc.warehouse.licensePlate.licensePlateUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickItem;
import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.picking.beans.ordernumDTO;
import com.owd.dc.picking.beans.pickStatusBean;
import com.owd.dc.locations.LocationBarcodeUtilities;
import com.owd.dc.picking.forms.ordernumForm;
import com.owd.dc.picking.forms.ordernumForm2;
import com.owd.dc.picking.PickUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 9:38:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class pickStartAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        TimeStamp ts1 = new
                TimeStamp("pickStartAction begin");
        OrderPickStatus pstat = null;
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        String shipped = "no";
        request.setAttribute("shipped", shipped);
        try {
            String priority = request.getSession().getAttribute("pickPriority").toString();
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            ordernumDTO ordernumDTO = new ordernumDTO();
            try {
                ordernumForm ordernumForm = (ordernumForm) form;
                BeanUtils.copyProperties(ordernumDTO, ordernumForm);
            } catch (Exception e) {
                ordernumForm2 ordernumForm2 = (ordernumForm2) form;

                BeanUtils.copyProperties(ordernumDTO, ordernumForm2);
            }
            if (null == ordernumDTO.getPriority()) {
                ordernumDTO.setPriority("1");
            }
            System.out.println("Setting priority from reqeust");
            System.out.println(ordernumDTO.getPriority());
            if (ordernumDTO.getPriority().equals("on")) {
                priority = "2";

            } else {
                priority = "1";
            }
            request.getSession().setAttribute("pickPriority", priority);
            request.setAttribute("action", "pick-start");
            pstat = PickUtilities.getCurrentPickStatus(request);
            if (pstat == null) {

                try {
                    //get order num
                    boolean forceLoad = false;
                    String orderNum = ordernumDTO.getordernumber();
                    if (orderNum == null) throw new Exception("Order barcode blank or missing");
                    if (orderNum.contains("force")) {
                        System.out.println("WE have some force");
                        forceLoad = true;
                        orderNum = orderNum.replace("force", "");

                    }
                    if (orderNum.startsWith("PP:") || orderNum.startsWith("//tote-")) {
                        if (licensePlateUtilities.canThisPackageLicenseBeUsed(orderNum)) {
                            request.setAttribute("license", orderNum);
                            return (mapping.findForward("home"));
                        } else {
                            throw new Exception("This liscense plate has already been assigned to an order");

                        }

                    }
                    if (ordernumDTO.getLicense().length() > 0) {
                        request.setAttribute("license", ordernumDTO.getLicense());
                    }
                    String orderNumBarcode = "*" + orderNum + "*";

                    if (orderNum.indexOf("R") > 0) {
                        orderNum = orderNum.substring(0, orderNum.indexOf("R"));
                    }
                    String facility = WMSUtils.getFacilityFromRequest(request);


                    String findOrderSQL = "select max(o.is_void), max(ISNULL(o.pick_status,0)), max(ISNULL(o.pick_by,'')),o.order_id,max(i.inventory_id), (ISNULL(dbo.getTopLocationFacilityPriority(max(i.inventory_id),'" + facility + "'," + priority + "),'UNKNOWN')) as location, "
                            + "sum(ISNULL(l.quantity_actual,0)), l.inventory_num,max(l.description)+' '+max(ISNULL(i.item_color,''))+' '+max(ISNULL(i.item_size,'')) as descr, max(order_num_barcode),post_date,o.client_fkey, o.order_status,facility_code,isnull(max(is_on_wh_hold),0) as \"is_on_wh_hold\", isnull(max(wh_hold_reason),'') as \"wh_hold_reason\""
                            + "from owd_order o (NOLOCK) inner join owd_line_item l (NOLOCK) left outer join owd_inventory i (NOLOCK) on i.inventory_id = l.inventory_id"
                            + " on o.order_id = l.order_fkey Left outer join owd_order_ship_holds h (NOLOCK) On o.order_id = h.order_fkey where  l.quantity_actual > 0 and l.inventory_num not like 'KIT-%' and l.inventory_num <> 'ITEM'  and l.inventory_num not like 'KITITEM%' and "
                            + "o.order_num = '" + orderNum + "' and i.is_auto_inventory=0 group by l.inventory_num, post_date,order_id,o.client_fkey, o.order_status,facility_code order by location, l.inventory_num";
                    System.out.println(findOrderSQL);
                    request.setAttribute("reqordernum", ordernumDTO.getOrdernumber());

                    ResultSet rs = HibernateSession.getResultSet(findOrderSQL);

                    //validate order
                    if (rs == null) throw new Exception("Unable to locate order " + (orderNum) + "");

                    // System.out.println(findOrderSQL);

                    int items = 0;


                    while (rs.next()) {
                        if (items == 0) {
                            if (rs.getInt(1) == 1)
                                throw new Exception("Order " + orderNum + " voided! The packing slip you are holding is invalid.");
                            if (rs.getString(11) == null)
                                throw new Exception("Order " + orderNum + " has been cancelled! The packing slip you are holding is invalid.");
                            if (rs.getString(13).equalsIgnoreCase("Shipped") && forceLoad == false) {
                                shipped = "yes";

                                throw new Exception("Order " + orderNum + " has already been shipped!!!  Please check this out!!");
                            }
                            if (rs.getInt("is_on_wh_hold") == 1 && !rs.getString("wh_hold_reason").equals("Work Order")) {
                                throw new Exception("Order " + orderNum + " is on DC Hold for " + rs.getString("wh_hold_reason"));
                            }

                            if (!rs.getString(14).equals(WMSUtils.getFacilityFromRequest(request)))
                                throw new Exception("You are in the Wrong Facility to Pick this order. It is marked " + rs.getString(14));

                            if (rs.getInt(2) == PickUtilities.kStatusPicking) {
                                throw new Exception("Order already being picked by " + rs.getString(3));
                            }
                            String name = new String();
                            if (rs.getInt(2) == PickUtilities.kStatusPicked) {
                                try {
                                    name = PickUtilities.orderAlreadyVerified(rs.getInt(4));
                                    System.out.println("right after alreadyverified");
                                } catch (Exception e) {
                                    name = "none";
                                }
                                if (ordernumDTO.getVerify() == 1) {
                                    System.out.println("verify order");
                                    //select name if already verified returns none if not already verified

                                    if (name != "none") {
                                        throw new Exception("Order already verified by " + name);
                                    }
                                } else {
                                    if (name != "none") {
                                        throw new Exception("Order already picked by " + rs.getString(3) + " and verified by " + name);
                                    }
                                    throw new Exception("Order already picked by " + rs.getString(3));
                                }
                            }
                            if (InventoryManager.countInProgress(HibernateSession.currentSession(), rs.getInt(12))) {
                                throw new Exception("Inventory Count in progress for Client, You cannot pick this order");
                            }
                            System.out.println("before next if");
                            if (rs.getString(10).equals(orderNumBarcode)) {
                                //order OK to pick!

                                pstat = new OrderPickStatus();
                                pstat.setOrderPickItems(new HashSet());
                                pstat.setOrderNum(orderNum);
                                pstat.setOrderId(rs.getInt(4));
                                pstat.setDcHold(0);
                                pstat.setPickerName((String) request.getAttribute("loginName"));
                                pstat.setStartTime(cal.getTime());
                                pstat.setIsVerify(new Integer(ordernumDTO.getVerify()));
                                pstat.setClientFkey(new Integer(rs.getInt(12)));
                                pstat.setCurrentItemIndex(0);
                                pstat.setLicensePlate(ordernumDTO.getLicense());
                                if (shipMethodUtils.isRedOrder(pstat.getOrderId(), request.getSession().getAttribute("owd_default_location").toString())) {
                                    pstat.setRedOrder(1);
                                } else {
                                    pstat.setRedOrder(0);
                                }

                                HibernateSession.currentSession().save(pstat);
                                HibernateSession.currentSession().flush();
                            } else {
                                throw new Exception("Order " + orderNum + " has been reprinted. The packing slip you are holding is invalid.");
                            }
                        }
//get line item data
                        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(rs.getInt(5)));
                        if (inv.getOwdClient().getClientId().intValue() == pstat.getClientFkey().intValue()) {
                            OrderPickItem item = new OrderPickItem();
                            item.setSkuDesc(rs.getString(9));
                            System.out.println("desc:" + rs.getString(9));
                            item.setItemBarcode(rs.getInt(5));
                            item.setQtyToPick(rs.getInt(7));
                            item.setSku(rs.getString(8));
                            item.setDefaultLoc(rs.getString(6));
                            pstat.getOrderPickItems().add(item);
                            item.setOrderPickStatu(pstat);
                            item.setQtyPicked(0);
                            item.setLocList("");
                            item.setReplenishTime(0);
                            item.setDcHold(0);

                            item.setIndexId(items);
                            item.setIsSerialized(new Integer(inv.getRequireSerialNumbers()));
                            HibernateSession.currentSession().save(item);
                            HibernateSession.currentSession().flush();
                            System.out.println("added item to pickitems, new size=" + pstat.getOrderPickItems().size());
                            items++;
                        } else {
                            System.out.println("client fkey doesn't match");
                        }
                    }

                    if (pstat == null) {
                        throw new Exception("(pstat null) Unable to locate order " + orderNum);
                    }
                    if (pstat.getOrderPickItems().size() < 1) {
                        throw new Exception("No pickable items found for " + orderNum);
                    }


                    Connection conn = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
                    HibernateSession.currentSession().flush();
                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                    Statement stmt = conn.createStatement();
                    String sqlQuery = "update owd_order set pick_by = '" + request.getAttribute("loginName") + "', pick_status=" + PickUtilities.kStatusPicking + " where order_num = '" + orderNum + "'";

                    int rows = stmt.executeUpdate(sqlQuery);
                    //  if (rows < 1)
                    //      throw new Exception("Unable to update order pick status");

                    stmt.close();
                    HibernateSession.currentSession().flush();
                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                    stmt = conn.createStatement();
                    sqlQuery = "insert into pick_events (picker,pick_start,order_num) values ( '" + request.getAttribute("loginName") + "',1,'*" + orderNum + "*')";

                    rows = stmt.executeUpdate(sqlQuery);
                    stmt.close();

                    HibernateSession.currentSession().flush();
                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                    // remove sorting because they are sorted properly during query 1-06-15
                    //   itemSortingUtilities.sortOrderViaOrderStatus(pstat,HibernateSession.currentSession());

                    HibernateSession.currentSession().refresh(pstat);

                    //get all locations
                    //change location getting to during the pick process.

                   /* Iterator it = pstat.getOrderPickItems().iterator();
                    while (it.hasNext()) {
                        OrderPickItem item = (OrderPickItem) it.next();
                        item.setLocList("");
                        String locList = LocationBarcodeUtilities.getLocationsForInventoryID(item.getItemBarcode(), HibernateSession.currentSession(),WMSUtils.getDefaultFacilityFromRequest(request));

                        item.setLocList(locList);
                    }*/
                    //store in session
                    PickUtilities.saveCurrentPickStatus(pstat, request);
                    PickUtilities.updateSessionPickStatusFromOld(request, pstat);
                    HibernateSession.currentSession().flush();

                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

                    //on error, back to pick start page
                } catch (org.hibernate.exception.ConstraintViolationException ee) {
                    // order already being picked usually but not reported right. Lookup who is doing it and report back
                    request.setAttribute("shipped", shipped);
                    request.setAttribute("error", "It appears this order is being picked already. If you cannot determine by who contact IT");
                } catch (Exception ex) {
                    request.setAttribute("shipped", shipped);
                    request.setAttribute("error", ex.getMessage());
                    ex.printStackTrace();

                } finally {


                    // HibernateSession.closeSession();
                }
                if (request.getAttribute("error") == null) {
                    pickItemBean pickItem = PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority);
                    holdForm holdForm = PickUtilities.loadHoldForm(pickItem, (String) request.getAttribute("loginName"));
                    request.setAttribute("holdForm", holdForm);

                    request.getSession(true).setAttribute("itemdisptime", cal.getTime());
                    request.setAttribute("pickItem", pickItem);
                    saveToken(request);
                    return (mapping.findForward("success"));
                } else {
                    return (mapping.findForward("home"));
                }
            } else {

                //OrderPickStatus pstat = (OrderPickStatus) request.getAttribute("oldpickorder");
                if(pstat.getOrderPickItems().size() == pstat.getCurrentItemIndex()){
                    pstat.setCurrentItemIndex(pstat.getCurrentItemIndex()-1);
                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                }

                pickStatusBean pickInfo = new pickStatusBean();
                pickInfo.setOrderNum(pstat.getOrderNum());
                pickInfo.setCurrentItem(pstat.getCurrentItemIndex());
                pickInfo.setNumberOfItems(pstat.getOrderPickItems().size());
                pickInfo.setLicense(pstat.getLicensePlate());
                System.out.println("order items:" + pstat.getOrderPickItems().size() + " current pick item index: " + pstat.getCurrentItemIndex());

                if (pstat.getOrderPickItems().size() > 0) {
                    pickInfo.setCurrentPicked(((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]).getQtyPicked());
                    pickInfo.setCurrentTotal(((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]).getQtyToPick());
                    pickInfo.setSku(((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]).getSku());
                    request.setAttribute("pickInfo", pickInfo);
                } else {
                    System.out.println(pstat.getOrderPickItems().size());
                    HibernateSession.currentSession().delete(pstat);
                    HibernateSession.currentSession().flush();
                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                    request.setAttribute("error", "An error occured, please rescan order!!");
                    return (mapping.findForward("home"));
                }
                return (mapping.findForward("recover"));
            }
        } finally {
            ts1.print("pickStartAction end");
            pstat = null;
            //   HibernateTimeForceSession.closeSession();
            //   HibernateSession.closeSession();
        }
    }


}
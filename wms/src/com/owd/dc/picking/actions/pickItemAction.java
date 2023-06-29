package com.owd.dc.picking.actions;

import com.owd.WMSUtils;
import com.owd.dc.OnDemandPrinting.DemandPrintingUtils;
import com.owd.dc.OnDemandPrinting.OnDemandInfoBean;
import com.owd.dc.checkAuthentication;
import com.owd.core.TimeStamp;
import com.owd.dc.picking.beans.pickItemBean;
import com.owd.dc.picking.forms.holdForm;
import com.owd.dc.warehouse.licensePlate.licensePlateUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickItem;
import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.picking.forms.serialForm;
import com.owd.dc.picking.PickUtilities;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.upcBarcodeUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.owd.core.Mailer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 1:10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickItemAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        TimeStamp ts1 = new
                TimeStamp("pickItemAction begin");
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        OrderPickItem curritem = null;
        pickItemBean pickItem = null;
        try {
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
            if (pstat != null) {
                boolean orderDone = false;
                boolean pstatOK = false;
                try {

                    //check that pickstatus obj is present


                    if (pstat == null) {
                        request.setAttribute("error", "No order picking record found - rescan order number");
                    } else {
                        pstatOK = true;
                        if (null == skuDTO.getSku()) {
                            throw new Exception("Please scan an Item: System or network error");
                        }


                        if (!isTokenValid(request)) {
                            //   throw new Exception("Possible concurrency problem.  Please double check number of items picked and continue");
                        }
                        resetToken(request);
                        if (skuDTO.getSku().equals("OWDProblem:Reported")) {
                            PickUtilities.markPickComplete(request, pstat, false);

                            request.setAttribute("donemessage", "Order Marked as problem");

                            return (mapping.findForward("newpick"));
                        }
                        if (skuDTO.getSku().startsWith("PP:") || skuDTO.getSku().startsWith("//tote-")) {
                            System.out.println("We are updating license plate");
                            if (licensePlateUtilities.canThisPackageLicenseBeUsed(skuDTO.getSku())) {
                                pstat.setLicensePlate(skuDTO.getSku());
                                HibernateSession.currentSession().save(pstat);
                                HibUtils.commit(HibernateSession.currentSession());
                                request.getSession(true).setAttribute("itemdisptime", cal.getTime());
                                String priority = request.getSession().getAttribute("pickPriority").toString();
                                request.setAttribute("pickItem", PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority));
                                return (mapping.findForward("success"));
                            } else {
                                throw new Exception("This license plate is already used. Please use another one.");
                            }


                        }
                        String sku;
                            boolean barcodeScan = false;
                            if (skuDTO.getPickMultiple() == 0) {
                                boolean itBypassBarcode = false;
                                if (skuDTO.getSku().contains("ff")) {
                                    itBypassBarcode = true;

                                    sku = skuDTO.getSku().replace("ff", "");
                                } else {
                                    sku = upcBarcodeUtilities.getSku(skuDTO.getSku(), pstat.getClientFkey().intValue());
                                    if (!sku.equals(skuDTO.getSku())) {
                                        barcodeScan = true;
                                    }
                                }

                                if (sku.equals("NA")) {
                          /*  System.out.println("unassigned barcode, recording it");
                            PickUtilities.unassignedBarcode(pstat,skuDTO.getSku());*/
                                    throw new Exception("Barcode Scanned(" + skuDTO.getSku() + ") is not Assigned to this SKU");
                                }
                                //Handle multiple skus with the same upc code
                                if (sku.equals("Multi")) {
                                    System.out.println("in Multi");
                                    HashMap multilist = upcBarcodeUtilities.getMultiBarcodeList(skuDTO.getSku(), pstat.getClientFkey().intValue());
                                    //System.out.println("befroe set skus");
                                    request.setAttribute("skus", multilist);
                                    request.setAttribute("var", "sku");
                                    //System.out.println("befroe set skus2");
                                    request.setAttribute("url", "pickitem");
                                    //System.out.println("befroe set skus3");
                                    return (mapping.findForward("multi"));
                                }
                                //checks if sku has barcode then they must scan that barcode.
                                if (HandheldUtilities.hasUPC(sku, pstat.getClientFkey().intValue()) && !"skip".equals(skuDTO.getInventoryNum()) && !itBypassBarcode) {
                                    boolean match = false;
                                    System.out.println("checkingUpc");
                                    OwdInventory inv = HandheldUtilities.loadInv(Integer.valueOf(sku));
                                    if (inv.getUpcCode().equals(skuDTO.getSku())) match = true;
                                    if (inv.getIsbnCode().equals(skuDTO.getSku())) match = true;

                                    if (match == false && !barcodeScan) {
                                        throw new Exception("You must scan UPC or ISBN");
                                    }
                                }
                                System.out.println("sku " + sku);
                                //check sku
                                if (sku == null)
                                    throw new Exception("Item barcode is required - please enter item ID or scan item barcode");
                                if (sku.equals("NA"))
                                    throw new Exception("Barcode Scanned(" + skuDTO.getSku() + ") is not Assigned to this SKU");

                                curritem = ((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]);
                                System.out.println("Current Item index" + pstat.getCurrentItemIndex());
                                System.out.println("Curritem Barcode" + curritem.getItemBarcode());
                                //validate against current SKU
                                if (!(sku.equals("" + curritem.getItemBarcode()))) {
                                    throw new Exception("Item scan " + sku + " does not match current item ID " + curritem.getItemBarcode());
                                }
                                //if first qty of item set start time
                                if (curritem.getQtyPicked() == 0) {
                                    //    String txt2 = req.getParameter("starttime");
                                    //  Date starttime = new Date(sdf.parse(txt2).getTime());
                                    Date starttime = (Date) request.getSession(true).getAttribute("itemdisptime");
                                    System.out.println("STartime" + starttime);
                                    System.out.println("Item Displayed Time" + request.getSession(true).getAttribute("itemdisptime"));
                                    curritem.setStartTime((Date) request.getSession(true).getAttribute("itemdisptime"));
                                }

                        }
                        Date end = cal.getTime();
                        System.out.println(end + " Time set to end");
                        System.out.println(cal.getTime());
                        //if good, check if item done
                        if (skuDTO.getPickMultiple() > 0) {
                            curritem = ((OrderPickItem) pstat.getOrderPickItems().toArray()[pstat.getCurrentItemIndex()]);
                            if (skuDTO.getPickMultiple() > (curritem.getQtyToPick() - curritem.getQtyPicked())) {
                                throw new Exception("You entered to many items to pick, please double check your quantity");
                            }
                            curritem.setQtyPicked(skuDTO.getPickMultiple() + curritem.getQtyPicked());
                        } else {
                            curritem.setQtyPicked(1 + curritem.getQtyPicked());
                        }
                        //set firt qty of item pick time
                        if (curritem.getQtyPicked() == 1) {
                            curritem.setFirstPickTime(end);
                        }

                        // pstat.getOrderPickItems().remove(curritem);


                        //  pstat.getOrderPickItems().add(curritem);
                        //Check if serial number scanning is required
                        //Removed 2019-11-08 to move serial tracking to pack time.
                     /*   System.out.println(curritem.getIsSerialized() + " Needing Serials");
                        if ("1".equals(curritem.getIsSerialized().toString())) {
                            System.out.println("Get serial NumbersSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                            HibernateSession.currentSession().flush();
                            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                            serialForm sf = new serialForm();
                            sf.setDescription(curritem.getSkuDesc());
                            sf.setInvId(curritem.getItemBarcode() + "");
                            sf.setInvNum(curritem.getSku());
                            sf.setpItemId(curritem.getId().toString());
                            sf.setCurrentNum("0");
                            sf.setTotal(curritem.getQtyToPick() + "");
                            request.setAttribute("serialForm", sf);
                            return mapping.findForward("serial");
                        }*/
                        //Removed 2019-11-08 to move serial tracking to pack time.

                        if (curritem.getQtyPicked() >= curritem.getQtyToPick()) {
                            //done with this item
                            curritem.setEndTime(end);

                            //more items
                            int current = pstat.getCurrentItemIndex();

                            pstat.setCurrentItemIndex(pstat.getCurrentItemIndex() + 1);
                            int newcurrent = pstat.getCurrentItemIndex();



                        }

                        if (pstat.getCurrentItemIndex() == pstat.getOrderPickItems().size()) {
                            //completed - back to pick order screen
                            System.out.println("got last item in pick item");

                            HibernateSession.currentSession().refresh(pstat);

                            //  HibernateSession.currentSession().flush();
                            //  com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

                            if (pstat.getDcHold() > 0) {
                                request.setAttribute("pstat", pstat);
                                return (mapping.findForward("dchold"));
                            }
                            orderDone = PickUtilities.markPickComplete(request, pstat, false);


                        } else {
                            PickUtilities.saveCurrentPickStatus(pstat, request);
                            String priority = request.getSession().getAttribute("pickPriority").toString();
                            pickItem = PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority);
                            holdForm holdForm = PickUtilities.loadHoldForm(pickItem, (String) request.getAttribute("loginName"));
                            request.setAttribute("holdForm", holdForm);
                        }


                        System.out.println(pstat.getCurrentItemIndex() + " Current item index right before hibernate flush");
                        HibernateSession.currentSession().flush();
                        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

                    }

                } catch (IllegalArgumentException ill) {

                    request.setAttribute("error", "ERROR!! Please rescan current Item.");
                } catch (Exception ex) {
                    // System.out.println(ex.getMessage());
                    if (ex.getMessage().equals("ERROR!!  Please Rescan last Item")) {
                        //   System.out.println("Exception");
                        //   System.out.println(curritem.getQtyPicked());
                        curritem.setQtyPicked(curritem.getQtyPicked() - 1);
                        HibernateSession.currentSession().flush();
                        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                    }
                    request.setAttribute("error", ex.getMessage());
                    System.out.println(request.getAttribute("loginName"));
                    ex.printStackTrace();
                } finally {
                    System.out.println("reseting token in request");
                    resetToken(request);
                    //  HibernateSession.closeSession();
                }

                if (orderDone && pstatOK) {
                    System.out.println("indone");
                    request.setAttribute("priority", request.getSession().getAttribute("pickPriority"));
                    if (pstat.getDcHold() == 1) {
                        System.out.println("dchold");
                        request.setAttribute("pstat", pstat);
                        return (mapping.findForward("dchold"));
                    }
                    OnDemandInfoBean ib = DemandPrintingUtils.checkForOnDemandSkusViaOrderNum(pstat.getOrderNum());
                    if (!ib.isOnDemand()) {
                        System.out.println("Setting default message");
                        request.setAttribute("donemessage", "Order Pick Complete");
                    } else {
                        System.out.println("Setting custom pick done message");
                        request.setAttribute("donemessage", ib.getPickMessage());
                        return (mapping.findForward("message"));
                    }
                    return (mapping.findForward("newpick"));
                } else if (pstatOK) {
                    request.getSession(true).setAttribute("itemdisptime", cal.getTime());
                    String priority = request.getSession().getAttribute("pickPriority").toString();
                    if(pickItem == null){
                        pickItem = PickUtilities.loadPickItemBean(pstat, WMSUtils.getFacilityFromRequest(request), priority);
                    }
                    request.setAttribute("pickItem", pickItem);
                    return (mapping.findForward("success"));
                } else {
                    return (mapping.findForward("newpick"));
                }
            } else {
                System.out.println("Found no pickstatus XXCCVV");
                return (mapping.findForward("recover"));
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            //Mailer.postMailMessage("Weird pick error", ee.getMessage(), "dnickels@owd.com", "dnickels@owd.com");
            return (mapping.findForward("recover"));
        } finally {


            ts1.print("pickItemAction End");
            ////   HibernateTimeForceSession.closeSession();
            //  HibernateSession.closeSession();
        }

    }


}

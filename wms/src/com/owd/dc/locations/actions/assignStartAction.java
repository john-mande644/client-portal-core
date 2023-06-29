package com.owd.dc.locations.actions;

import com.owd.core.managers.LotManager;
import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.inventory.beans.locationDTO;
import com.owd.dc.inventory.beans.skuBean;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.locations.forms.locationForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 10, 2005
 * Time: 3:56:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class assignStartAction extends Action {

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


            locationForm locForm = (locationForm) form;
            locationDTO locDTO = new locationDTO();
            BeanUtils.copyProperties(locDTO, locForm);
            int clientFkey = 0;
            String item = locDTO.getlocation();

            if (item == null) {
                return (mapping.findForward("home"));
            } else {
                //if itme scan is sku do single location assign
                if (item.startsWith("/") == false) {
                    if (locDTO.getRemember() > 0 == true) {
                        System.out.println("in if DTO>0");
                        HandheldUtilities.setRememberMulti(locDTO.getRemember() + "", locDTO.getRememberClientName(), request, response);
                    }
                    try {
                        clientFkey = Integer.parseInt((String) request.getAttribute("remember"));
                    } catch (Exception e) {
                    }
                    try {
                        String sku = upcBarcodeUtilities.getSku(item, clientFkey);
                        //If previous returns multi, get info and then forward to multi sku interface
                        if (sku.equals("Multi")) {
                            HashMap multilist = upcBarcodeUtilities.getMultiBarcodeList(item, clientFkey);
                            request.setAttribute("skus", multilist);
                            request.setAttribute("var", "location");
                            request.setAttribute("url", "assignlocationstart");
                            request.setAttribute("rememberclient", "true");
                            return (mapping.findForward("multi"));
                        }

                        //check sku for validity
                        int barcodeInt = Integer.decode(sku).intValue();

                        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
                        if (inv == null) {
                            throw new Exception("Unable to find sku for barcode " + sku);
                        }

                        //if OK proceed to next
                        skuBean skuInfo = new skuBean();
                        skuInfo.setInventoryId(sku);
                        skuInfo.setDescription(inv.getDescription());
                        skuInfo.setInventoryNum(inv.getInventoryNum());
                        request.setAttribute("skuInfo",skuInfo);
                        //else set error and return to getsku page
                        if (inv.getLotTrackingRequired() == 1) {
                            System.out.println("Lot controlled and no lot info ask for it");

                            List<String> lots = LotManager.getLotValuesForInventoryId(Integer.parseInt(sku));
                            System.out.println(lots.size());
                            System.out.println(lots);

                            request.setAttribute("lots", lots);
                            request.setAttribute("sku", sku);


                            return (mapping.findForward("getLots"));


                        }
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error", "SKU barcode must be numeric - " + item + " is invalid");

                    } catch (Exception ex) {
                        request.setAttribute("error", ex.getMessage());
                    } finally {
                       // HibernateSession.closeSession();
                    }

                    if (request.getAttribute("error") != null) {
                        return (mapping.findForward("home"));
                    } else {
                        return (mapping.findForward("single"));
                    }
                } else {
                    //do batch assign
                    System.out.println("In assignlocation-start batch");
                    String loc = item;
                    if (loc == null) {
                        return (mapping.findForward("home"));
                    } else {
                        try {


                            //check sku and loc for validity
                            if (loc == null) throw new Exception("Location scan invalid");
                            if (!(loc.startsWith("/"))) throw new Exception("Location code not valid");


                            //if OK update db
                            request.setAttribute("loc", loc);
                            request.getSession(true).setAttribute("zlocation", loc);

                            //if update OK return to getsku page

                            //else set error and return to getlocation page

                        } catch (Exception ex) {
                            request.setAttribute("error", ex.getMessage());
                        } finally {
                           // HibernateSession.closeSession();
                        }


                        if (request.getAttribute("error") != null) {
                            return (mapping.findForward("home"));

                        } else {
                            return (mapping.findForward("batch"));
                        }

                    }
                }

            }
        } finally {

            //HibernateTimeForceSession.closeSession();
            //HibernateSession.closeSession();
        }
    }
}

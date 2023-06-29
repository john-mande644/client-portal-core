package com.owd.dc.inventorycount;


import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.WInvRequest;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.inventory.upcBarcodeUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 3, 2005
 * Time: 10:19:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class wgetSkuAction extends Action {

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


            wCountForm wCountForm = (wCountForm) form;
            if("YES".equals(request.getSession(true).getAttribute("invmulti"))){
             wCountForm old = (wCountForm) request.getSession(true).getAttribute("invmultiform");
                wCountForm.setBarcode(old.getBarcode());
                wCountForm.setInvLocFkey(old.getInvLocFkey());
                wCountForm.setRequestId(old.getRequestId());
                wCountForm.setLocation(old.getLocation());
                wCountForm.setPrevId(old.getPrevId());
                request.getSession(true).setAttribute("invmulti","NO");
            }
            wCountDTO wCountDTO = new wCountDTO();
            BeanUtils.copyProperties(wCountDTO, wCountForm);
  //Checking if location if all skus not counted, returns error
            if (wCountDTO.getInventoryId().startsWith("/")) {
                List skus = new ArrayList();
                try {

                    if (wCountDTO.getInventoryId().equalsIgnoreCase(wCountDTO.getLocation())) {
                        wInventoryUtilities.checkCloseLocations(wCountDTO);
                        wCountDTO = new wCountDTO();
                        return (mapping.findForward("newLocation"));
                    } else{
                        throw new Exception("You must scan same location you are counting");
                    }

                } catch (Exception ex) {
                        request.setAttribute("error",ex.getMessage());
                    skus = wInventoryUtilities.getSkusInLocation(wCountDTO.getLocation(), wCountDTO.getRequestId());
                    request.setAttribute("wskusinlocation", skus);

                    return (mapping.findForward("error"));

            } }
     //If code entered, mark all done, and not null Id's to remove location
            if (wCountDTO.getInventoryId().equals("00300")) {
                wInventoryUtilities.doneWithLocations(wCountDTO);
                wCountDTO = new wCountDTO();
                return (mapping.findForward("newLocation"));
            }

            WInvRequest wi = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class, new Integer(wCountDTO.getRequestId()));
            OwdInventory inv = new OwdInventory();
            try {
    //try to load sku, returns error if can't load it
                //accept barcode's for jfisher and owd for testing
                System.out.println(wi.getClientFkey());
               // if(wi.getClientFkey()==345 || wi.getClientFkey()==55 ||wi.getClientFkey()==344||wi.getClientFkey()==117||wi.getClientFkey()==372||wi.getClientFkey()==387||wi.getClientFkey()==104){
                    System.out.println("in lookingup barcode");
                     String sku = upcBarcodeUtilities.getSku(wCountDTO.getInventoryId(), wi.getClientFkey());

                        //Handle multiple skus with the same upc code
                        if (sku.equals("Multi")) {
                            System.out.println("in Multi");
                            HashMap multilist = upcBarcodeUtilities.getMultiBarcodeList(wCountDTO.getInventoryId(), wi.getClientFkey());
                            //System.out.println("befroe set skus");
                            request.setAttribute("skus", multilist);
                            request.setAttribute("var", "inventoryId");

                            request.setAttribute("url", "wgetSku");
                            request.getSession(true).setAttribute("invmulti","YES");
                            request.getSession(true).setAttribute("invmultiform",wCountForm);

                            return (mapping.findForward("multi"));
                        }
                      System.out.println(sku);
                    wCountDTO.setBarcode(wCountDTO.getInventoryId());
                    wCountDTO.setInventoryId(sku);
                    wCountForm.setInventoryId(sku);
                //}

                inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(wCountDTO.getInventoryId()));
            } catch (Exception ex) {
                List skus = wInventoryUtilities.getSkusInLocation(wCountDTO.getLocation(), wCountDTO.getRequestId());
                request.setAttribute("wskusinlocation", skus);
                request.setAttribute("error", "Invalid Sku");
                return (mapping.findForward("error"));
            }
            if (wInventoryUtilities.verifyClientSku(wi.getClientFkey(), wCountDTO.getInventoryId())==true) {
              //  System.out.println("verified");
               int quanity = wInventoryUtilities.checkForCurrentCount(wCountDTO.getInventoryId(), wCountDTO.getLocation(), wCountDTO.getRequestId());
             System.out.println(wCountDTO.getPrevId());
               if((quanity >0 && wCountDTO.getInventoryId().equals(wCountDTO.getPrevId()))||quanity==0){
                   wCountForm.setInvLocFkey(wInventoryUtilities.getInvLocationFkey(wCountDTO));
                 System.out.println("in if");
                invBean ib = new invBean();
                ib.setDescription(inv.getDescription());
                ib.setInventoryId(inv.getInventoryId().toString());
                ib.setInventoryNum(inv.getInventoryNum());
                request.setAttribute("ib", ib);
                return (mapping.findForward("success"));
               }else{
                wCountForm.setPrevId(wCountDTO.getInventoryId());
                request.setAttribute("error", wCountDTO.getInventoryId() +" Has already been counted in this location");
                   request.setAttribute("error1",quanity+" have already been counted.");
                   request.setAttribute("error2","Enter Id again to add count.");
                return (mapping.findForward("error"));
               }
            } else {
                List skus = wInventoryUtilities.getSkusInLocation(wCountDTO.getLocation(), wCountDTO.getRequestId());

                request.setAttribute("wskusinlocation", skus);
                throw new Exception("Sku Scanned is not for Client Request you are doing");
            }

        } catch (Exception e) {

            request.setAttribute("error", e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}
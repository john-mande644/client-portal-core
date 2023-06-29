package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.WInvRequest;
import com.owd.dc.inventory.upcBarcodeUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 28, 2005
 * Time: 3:32:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class wAddskuAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            checkAuthentication.check(request, response);
        } catch (Exception ex) {
            return (mapping.findForward("login"));
        }
        // Calendar cal = Calendar.getInstance();
        addWcountForm wForm = (addWcountForm) form;
        if (wForm.getInventoryId().startsWith("/")) {
            wForm.setLocation("");
            request.setAttribute("createrequestid", wForm.getwRequestId());
            return (mapping.findForward("done"));
        }
        addWcountDTO wDTO = new addWcountDTO();
        BeanUtils.copyProperties(wDTO, wForm);

        try {
            WInvRequest wi = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class, new Integer(wDTO.getwRequestId()));
            System.out.println("Got Request");
            String sku = upcBarcodeUtilities.getSku(wDTO.getInventoryId(), wi.getClientFkey());
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,  Integer.valueOf(sku));
            if((inv.getOwdClient().getClientId()+"").equals((wi.getClientFkey()+""))){

            if (!wInventoryUtilities.checkLocationAlreadyAdded(Integer.parseInt(sku), wi.getId(), wDTO.getLocation())) {
               wInventoryUtilities.addWcount(Integer.parseInt(sku), wi, wDTO.getLocation(), new Integer(0), new Integer(0), new Integer(0));
            }
            wInventoryUtilities.addAdditionalLocations(Integer.parseInt(sku), wi, wDTO.getLocation());
            } else{
               //  System.out.println(inv.getOwdClient().getClientId()+": "+wi.getClientFkey());
                throw new Exception("Sku selected is not for Client Assigned to Request");

            }
            // request.setAttribute("outcome", "Created request with id of " + wi.getId());
            wForm.setInventoryId("");
            return mapping.findForward("success");

        } catch (org.hibernate.ObjectNotFoundException ob){
             throw new Exception("No Sku found for "+ wDTO.getInventoryId());
        } catch (Exception ex) {
             ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));
        }

    }
}




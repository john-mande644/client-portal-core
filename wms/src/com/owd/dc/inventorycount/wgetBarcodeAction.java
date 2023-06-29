package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.invBean;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
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
 * Date: Jan 2, 2006
 * Time: 3:55:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class wgetBarcodeAction extends Action {

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
            wCountDTO wCountDTO = new wCountDTO();
            BeanUtils.copyProperties(wCountDTO, wCountForm);

            if(wCountDTO.getBarcode().equals("00300")){
                 wCountForm.setInventoryId("");
                wCountForm.setInvLocFkey("");
                wCountForm.setQuanity("");
                wCountForm.setVerifyInventoryId("");
                return (mapping.findForward("goback"));
            }
            if(wCountDTO.getBarcode().equals("0")){
              OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(wCountDTO.getInventoryId()));
                invBean ib = wInventoryUtilities.setInvBean(inv);
                request.setAttribute("ib",ib);
                return (mapping.findForward("success"));
            }
             String result = wInventoryUtilities.verifyBarcode(wCountDTO);
            if(result.equals("Invalid")){
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(wCountDTO.getInventoryId()));
                invBean ib = wInventoryUtilities.setInvBean(inv);
                request.setAttribute("ib",ib);
                throw new Exception("Invalid Barcode");
            }
            if(result.equals("UPC")){
                wCountForm.setUPC(wCountDTO.getBarcode());
            }
            if(result.equals("ISBN")){
                wCountForm.setISBN(wCountDTO.getBarcode());
            }

             OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(wCountDTO.getInventoryId()));
                invBean ib = wInventoryUtilities.setInvBean(inv);
                request.setAttribute("ib",ib);
                return (mapping.findForward("success"));


        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }

}

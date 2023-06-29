package com.owd.dc.locations.actions;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.inventory.beans.upcDTO;
import com.owd.dc.inventory.forms.upcForm;
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
 * Date: Apr 19, 2005
 * Time: 12:23:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class assignSkuAction extends Action {


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
            upcForm upcForm = (upcForm) form;
            upcDTO upcDTO = new upcDTO();
            BeanUtils.copyProperties(upcDTO, upcForm);
            System.out.println("copy");
            System.out.println(upcDTO.getInvID());
            String sku = String.valueOf(upcDTO.getInvID());
            System.out.println("string");
            if (sku.startsWith("/") || sku.equals("0")) {
                request.setAttribute("chkbarcode", upcDTO.getbarcode());
                request.setAttribute("error", "Not a valid Sku - Please scan a valid Sku");
                return (mapping.findForward("error"));
            }
            System.out.println("if");
            String barcode = upcDTO.getbarcode();
            String barcodeType = upcBarcodeUtilities.barcodeCheck(barcode);
            System.out.println("assign barcode and barcodeType");


            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,new Integer(upcDTO.getInvID()));
            System.out.println("assign inv");
            if(barcodeType.equals("UPC")){
                System.out.println("inupc");
                inv.setUpcCode(barcode);
                HibernateSession.currentSession().save(inv);
                System.out.println("save or update");
                HibernateSession.currentSession().flush();
                System.out.println("fluch");
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                System.out.println("comit");
                request.setAttribute("outcome", "Assigned UPC "+ barcode + " to Sku "+ inv.getInventoryId());
                System.out.println("in upc end");
                return (mapping.findForward("success"));
            }
            if(barcodeType.equals("ISBN")){
                inv.setIsbnCode(barcode);
                HibernateSession.currentSession().saveOrUpdate(inv);
                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                request.setAttribute("outcome", "Assigned ISBN "+ barcode + " to Sku "+ inv.getInventoryId());
                return (mapping.findForward("success"));
            }
            request.setAttribute("Sku", inv);
            request.setAttribute("error", barcodeType);
            return (mapping.findForward("error"));
        } catch (Exception ex){
             request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));
        }
        finally {

           // HibernateTimeForceSession.closeSession();
           // HibernateSession.closeSession();
        }
    }
}

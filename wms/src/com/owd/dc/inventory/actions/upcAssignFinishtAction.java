package com.owd.dc.inventory.actions;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.inventory.beans.upcDTO;
import com.owd.dc.inventory.beans.skuBean;
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
 * Date: Apr 18, 2005
 * Time: 10:14:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class upcAssignFinishtAction extends Action {


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
            System.out.println("copied beans assign variables");
            String barcode = upcDTO.getbarcode();
            String barcodeType = upcBarcodeUtilities.barcodeCheck(barcode);
            System.out.println("assigned barcodes");
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,new Integer(upcDTO.getInvID()));
            System.out.println("set inv");
            if(barcodeType.equals("ZERO")){
                inv.setUpcCode("");
                inv.setIsbnCode("");
                HibernateSession.currentSession().save(inv);
                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                request.setAttribute("outcome", "Cleared barcodes for "+ inv.getInventoryId());
                return (mapping.findForward("success"));
            }
            if(barcodeType.equals("UPC")){
                if (!inv.getUpcCode().equals("")){
                    if (inv.getUpcCode().equals(barcode)){
                        request.setAttribute("outcome", barcode + " Already assigned to " + inv.getInventoryId());
                        return (mapping.findForward("success"));
                    }
                }
                inv.setUpcCode(barcode);
                System.out.println(barcode +"barcode");
                HibernateSession.currentSession().save(inv);
                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                request.setAttribute("outcome", "Assigned UPC "+ barcode + " to Sku "+ inv.getInventoryId());
                return (mapping.findForward("success"));
            }
            if(barcodeType.equals("ISBN")){
                if (!inv.getIsbnCode().equals("")){
                    if (inv.getIsbnCode().equals(barcode)){
                        request.setAttribute("outcome", barcode + " Already assigned to " + inv.getInventoryId());
                        return (mapping.findForward("success"));
                    }
                }
                inv.setIsbnCode(barcode);
                HibernateSession.currentSession().saveOrUpdate(inv);
                HibernateSession.currentSession().flush();
                com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                request.setAttribute("outcome", "Assigned ISNB "+ barcode + " to Sku "+ inv.getInventoryId());
                return (mapping.findForward("success"));
            }
           skuBean info = new skuBean();
            info.setInventoryId(inv.getInventoryId().toString());
            info.setInventoryNum(inv.getInventoryNum());
            info.setDescription(inv.getDescription());
            request.setAttribute("skuinfo",info);
            request.setAttribute("error", barcodeType);
            return (mapping.findForward("error"));
        } catch (Exception ex){
             request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));
        }
        finally {

          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
        }
    }
}

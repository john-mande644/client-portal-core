package com.owd.dc.stockout;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.inventory.upcBarcodeUtilities;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jan 24, 2006
 * Time: 10:33:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class stockoutGetSkuAction extends Action {

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


            stockoutForm stockoutForm = (stockoutForm) form;
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(stockoutForm.getsInventoryId()));
               invBean ib = new invBean();
                ib.setInventoryId(inv.getInventoryId().toString());
                ib.setInventoryNum(inv.getInventoryNum());
                ib.setDescription(inv.getDescription());
                request.setAttribute("ib",ib);
           String sku = upcBarcodeUtilities.getSku(stockoutForm.getInventoryId(), Integer.parseInt(stockoutForm.getClientFkey()));
                        if (sku.equals("Multi")) {
                            System.out.println("in Multi");
                         //   HashMap multilist = upcBarcodeUtilities.getMultiBarcodeList(skuDTO.getSku(), pstat.getClientFkey().intValue());

                          //  request.setAttribute("skus", multilist);
                           // request.setAttribute("var", "sku");

                          //  request.setAttribute("url", "pickitem");
                         sku = stockoutForm.getsInventoryId();
                           // return (mapping.findForward("multi"));
                        }
           if(!stockoutForm.getInventoryId().startsWith("/")){
            if (sku.equals(stockoutForm.getsInventoryId())){
                stockoutUtilities.markVerified(1,request.getAttribute("loginName").toString(),stockoutForm.getId());
                request.setAttribute("outcome", "Verified " +stockoutForm.getInventoryId() +" in location "+stockoutForm.getLocation());
                return mapping.findForward("success");

            } else{
                if(sku.equals("0")){
                    try{
                 HandheldUtilities.removeLocation(stockoutForm.getLocation(),stockoutForm.getsInventoryId());
                    }catch (Exception Ex){
                        //if it can't remove it should already be gone, now just mark it so and move on
                    }
                 stockoutUtilities.markVerified(2,request.getAttribute("loginName").toString(),stockoutForm.getId());
                 return mapping.findForward("success");
                }
                throw new Exception("Sku scanned does not match sku needed");
            }
           }else{
               throw new Exception("You scanned a location code, please scan sku");
           }

        } catch (Exception e) {


          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}

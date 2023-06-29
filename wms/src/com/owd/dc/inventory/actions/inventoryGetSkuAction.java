package com.owd.dc.inventory.actions;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.checkAuthentication;

import java.util.HashMap;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 3, 2005
 * Time: 10:19:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryGetSkuAction extends Action {

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


            skuForm skuForm = (skuForm) form;
            skuDTO skuDTO = new skuDTO();
            BeanUtils.copyProperties(skuDTO, skuForm);
            int clientFkey = Integer.parseInt((String) request.getSession(true).getAttribute("doinginventoryclientfkey"));
            if (skuDTO.getSku().startsWith("/") == false) {
                String sku = upcBarcodeUtilities.getSku(skuDTO.getSku(), clientFkey);
                if (sku.equals("Multi")) {
                    HashMap multilist = upcBarcodeUtilities.getMultiBarcodeList(skuDTO.getSku(), clientFkey);
                    request.setAttribute("skus", multilist);
                    request.setAttribute("var", "sku");
                    request.setAttribute("url", "inventorygetsku");
                    return (mapping.findForward("multi"));
                }

                String idTestsql = "select client_fkey from owd_inventory  (NOLOCK) where inventory_id = "+ sku;
                ResultSet rs = HibernateSession.getResultSet(idTestsql);
                if(rs.next()){
                  if(clientFkey != rs.getInt(1)) {
                      throw new Exception("Sku not found for current client Inventory");
                  }
                      OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, (new Integer(sku)));
                 request.setAttribute("invnum",inv.getInventoryNum());
                 request.setAttribute("desc",inv.getDescription());                                                                                     
                 request.setAttribute("sku",sku);
                 return (mapping.findForward("success"));
                } else{
                    throw new Exception("Sku not found");
                }


            } else {
                throw new Exception("Not a valid sku");
            }

        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}
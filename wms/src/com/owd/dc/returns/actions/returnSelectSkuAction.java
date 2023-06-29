package com.owd.dc.returns.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
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

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 10, 2005
 * Time: 10:12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class returnSelectSkuAction extends Action {

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
            int clientFkey = 0;

            try {
                clientFkey = Integer.parseInt((String) request.getAttribute("returncid"));
            } catch (Exception e) {
            }
            System.out.println(clientFkey);
            ArrayList skulist = (ArrayList) request.getSession(true).getAttribute("orderskulist");
            String sku = upcBarcodeUtilities.getSku(skuDTO.getSku(), clientFkey);
            //If previous returns multi, get info and then forward to multi sku interface
            if (sku.equals("Multi")) {

                System.out.println("in Multi");
                HashMap info = upcBarcodeUtilities.checkMultiAgainstList(skuDTO.getSku(), clientFkey, skulist);
                System.out.println("got hashmap");
                if (info.get("outcome").equals("multi")) {
                    HashMap multilist = upcBarcodeUtilities.getMultiBarcodeListLimited((ArrayList)info.get("skus"));
                    request.setAttribute("skus", multilist);
                    request.setAttribute("var", "sku");
                    request.setAttribute("url", "returnSelectSku");
                    return (mapping.findForward("multi"));
                } else {
                    sku = info.get("sku").toString();
                }
            }
           if(skulist.contains(sku)==false){
               System.out.println("infalse");
           
               throw new Exception("Sku not In order");

           }

            request.setAttribute("sku", sku);
            return (mapping.findForward("success"));
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}

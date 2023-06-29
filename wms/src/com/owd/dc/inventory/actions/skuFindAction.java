package com.owd.dc.inventory.actions;

import com.owd.WMSUtils;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.inventory.beans.skuBean;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.inventoryUtilities;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.dc.checkAuthentication;
import com.owd.dc.inventorycount.historyBean;
import com.owd.dc.locations.locationInfoBean;
import com.owd.hibernate.HibernateSession;
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
 * Date: Apr 8, 2005
 * Time: 3:37:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class skuFindAction extends Action {


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

            boolean pic = false;
            skuForm skuForm = (skuForm) form;
            skuDTO skuDTO = new skuDTO();
            BeanUtils.copyProperties(skuDTO, skuForm);
            String sku = new String();

            if(null != skuDTO.getPicsku()&&!"".equals(skuDTO.getPicsku())){
                System.out.println("Picskuing");
               sku = skuDTO.getPicsku();


                pic = true;
            }else{
                System.out.println("not a pictue thingy");
            sku = skuDTO.getSku();
            }

            if (sku.equals("")) {
                request.setAttribute("error", "Not a valid Sku - Please Scan a Valid Sku");
                return (mapping.findForward("success"));
            }
            if (sku.startsWith("/")) {
                List loc = HandheldUtilities.getSkuFromLocaiton(sku);

                request.setAttribute("skusinlocation", loc);
                if(sku.startsWith("//")){
                    locationInfoBean lib = new locationInfoBean(sku.replace("//",""), HibernateSession.currentSession());
                 request.setAttribute("loc",lib.getFormatedPickString());
                } else{
                request.setAttribute("loc", sku);
                }
                //request.setAttribute("size", loc.size()+"");
                return (mapping.findForward("locationscan"));
            }
            String msku = upcBarcodeUtilities.getSku(sku, 0);

            if (msku.equals("Multi")) {
                System.out.println("in Multi");
                HashMap multilist = upcBarcodeUtilities.getMultiBarcodeList(skuDTO.getSku(), 0);
                
                request.setAttribute("skus", multilist);
                request.setAttribute("var", "sku");
                request.setAttribute("url", "findSku");
                return (mapping.findForward("multi"));
            }

            skuBean info = HandheldUtilities.getSkuInfo(msku, WMSUtils.getFacilityFromRequest(request));
            List<historyBean> deletedInfo = inventoryUtilities.getLocationHistoryFromId(msku);
         //   request.setAttribute("outcome", "Found "+info.getLocations().size()+" Locations");
            request.setAttribute("searchSku", info);
            request.setAttribute("deletedInfo",deletedInfo);
           if(pic){
                return mapping.findForward("pic");
           } else{
              return (mapping.findForward("success"));
           }

        } catch (Exception e){
         request.setAttribute("error",e.getMessage());

            return (mapping.findForward("error"));

        }
    }
}


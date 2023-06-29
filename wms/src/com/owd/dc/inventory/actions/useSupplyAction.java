package com.owd.dc.inventory.actions;

import com.opensymphony.xwork2.ActionContext;
import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.dc.inventory.supplyUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Sep 20, 2006
 * Time: 9:52:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class useSupplyAction extends LookupDispatchAction {


    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.use1", "checkSku");
        map.put("button.use", "useSku");


        return map;
    }
    public ActionForward checkSku(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            skuForm sf = (skuForm) form;
           String sku = upcBarcodeUtilities.getSku(sf.getSku(), 0);
           OwdInventory inv = HandheldUtilities.loadInv(Integer.valueOf(sku));

           if(inv.getOwdClient().getClientId().intValue()!=55){
               throw new Exception("Sku is not for OWD, supply app is for OWD only");
           }
           request.getSession(true).setAttribute("skuInfo",HandheldUtilities.getSkuInfo(sku, WMSUtils.getDefaultFacilityFromContext(ActionContext.getContext())));

            return (mapping.findForward("getquanity"));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
     public ActionForward useSku(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            skuForm sf = (skuForm) form;
            supplyUtil.saveAdjust(sf,HibernateSession.currentSession(),request);

            request.setAttribute("outcome","Removed "+sf.getNumlabels()+" from "+sf.getSku());
            sf.setInventoryNum("");
            sf.setSku("");
            sf.setNumlabels("");
            return (mapping.findForward("success"));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
    public ActionForward unspecified(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            return (mapping.findForward("success"));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

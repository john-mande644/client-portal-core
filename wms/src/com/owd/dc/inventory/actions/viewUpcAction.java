package com.owd.dc.inventory.actions;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.inventory.forms.skuForm;
import org.hibernate.ObjectNotFoundException;
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
 * Date: Jul 31, 2006
 * Time: 4:16:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class viewUpcAction  extends LookupDispatchAction {


    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.viewUPC", "LoadUpc");
        map.put("button.all", "removeboth");
        map.put("button.removeUpc", "removeUPC");
        map.put("button.removeIsbn", "removeISBN");

        return map;
    }

    //Default action, forwads to askinf about packing slip

    //action for entering packingslip
    public ActionForward LoadUpc(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
            String sku = null;
        try {
            //Checking for login

            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            skuForm sf = (skuForm) form;
            sku = sf.getSku();
            OwdInventory inv = HandheldUtilities.loadInv(Integer.valueOf(sf.getSku()));
            request.setAttribute("upc",inv.getUpcCode());
            request.setAttribute("isbn",inv.getIsbnCode());
            return (mapping.findForward("show"));


        } catch (ObjectNotFoundException onf){
            request.setAttribute("error", "No item was found with sku "+sku);
            return mapping.findForward("error");
             
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    public ActionForward removeUPC(ActionMapping mapping,
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
            OwdInventory inv = HandheldUtilities.loadInv(Integer.valueOf(sf.getSku()));
           inv.setUpcCode("");

            HibernateSession.currentSession().save(inv);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            request.setAttribute("outcome","Removed UPC!!!");


            request.setAttribute("upc",inv.getUpcCode());
            request.setAttribute("isbn",inv.getIsbnCode());
            return (mapping.findForward("success"));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
  public ActionForward removeISBN(ActionMapping mapping,
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
            OwdInventory inv = HandheldUtilities.loadInv(Integer.valueOf(sf.getSku()));

            inv.setIsbnCode("");

            HibernateSession.currentSession().save(inv);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            request.setAttribute("outcome","Removed ISBN!!!");
           request.setAttribute("upc",inv.getUpcCode());
            request.setAttribute("isbn",inv.getIsbnCode());
            return (mapping.findForward("success"));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
 public ActionForward removeboth(ActionMapping mapping,
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
            OwdInventory inv = HandheldUtilities.loadInv(Integer.valueOf(sf.getSku()));
           inv.setUpcCode("");
            inv.setIsbnCode("");

            HibernateSession.currentSession().save(inv);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            request.setAttribute("outcome","Removed UPC and ISBN!!!");
            request.setAttribute("upc",inv.getUpcCode());
            request.setAttribute("isbn",inv.getIsbnCode());
            return (mapping.findForward("success"));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

}

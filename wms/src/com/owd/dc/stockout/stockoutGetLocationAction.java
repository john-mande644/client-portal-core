package com.owd.dc.stockout;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.invBean;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
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
 * Time: 10:14:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class stockoutGetLocationAction extends Action {

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
            String location = stockoutForm.getLocation().replaceAll(" ","");
           if(location.startsWith("/")){
            if (location.equals(stockoutForm.getsLocation())){
                
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(stockoutForm.getsInventoryId()));
               invBean ib = new invBean();
                ib.setInventoryId(inv.getInventoryId().toString());
                ib.setInventoryNum(inv.getInventoryNum());
                ib.setDescription(inv.getDescription());
                request.setAttribute("ib",ib);
                return mapping.findForward("success");

            } else{
                throw new Exception("You did not scan the correct location, please try again");
            }
           }else{
               throw new Exception("Invalid Location!!");
           }

        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }









}

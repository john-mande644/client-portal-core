package com.owd.dc.stockout;

import com.owd.dc.checkAuthentication;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jan 23, 2006
 * Time: 9:26:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class startStockoutVerificationAction extends Action {

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
            if(stockoutForm.getLocation().equals("1")|| stockoutForm.getLocation().equals("2")){
                System.out.println("setting new warehouse");

            request.getSession(true).setAttribute("stockoutLocationCode",stockoutForm.getLocation());
            } else{
                stockoutForm.setLocation((String) request.getSession(true).getAttribute("stockoutLocationCode"));
            }

            stockoutForm wow = stockoutUtilities.getNextStockoutLocation(request.getAttribute("loginName").toString(),stockoutForm.getLocation());
             stockoutForm.setsInventoryId(wow.getsInventoryId());
            stockoutForm.setsLocation(wow.getsLocation());
            stockoutForm.setClientFkey(wow.getClientFkey());
            stockoutForm.setId(wow.getId());

            return (mapping.findForward("success"));
        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }









}

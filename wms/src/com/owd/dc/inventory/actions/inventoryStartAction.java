package com.owd.dc.inventory.actions;


import com.owd.hibernate.HibernateSession;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.inventoryUtilities;
import com.owd.dc.checkAuthentication;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 3, 2005
 * Time: 9:50:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryStartAction extends Action {

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
            String id = inventoryUtilities.getRequestId(skuDTO.getSku());
           // if (skuDTO.getSku().startsWith("/")==false){
             String getInventory = "select client_fkey,  company_name, location_to_assign, done from warehouse_inventory w (NOLOCK) , owd_client c  (NOLOCK) where w.client_fkey = c.client_id and w.id = "+id;
            ResultSet rs = HibernateSession.getResultSet(getInventory);
                if(rs.next()){
                if("0".equals(rs.getString(4))){
                  request.getSession(true).setAttribute("doinginventoryid",id);
                  request.getSession(true).setAttribute("doinginventoryclientname",rs.getString(2));
                  request.getSession(true).setAttribute("doinginventoryclientfkey",rs.getString(1));
                  request.getSession(true).setAttribute("dilocation", rs.getString(3));
                    return (mapping.findForward("success"));
                }else{
                    throw new Exception("Request Already closed");
                }
                }else{
                    throw new Exception("Inventory Record not Found");
                }

           // } else{
            //    throw new Exception("Invalid sku barcode");
           // }

} catch(Exception e){
            e.printStackTrace();
         request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }finally{
           // HibernateSession.closeSession();
        }

    }
}
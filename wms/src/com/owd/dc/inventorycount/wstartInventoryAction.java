package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.hibernate.HibernateSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 30, 2005
 * Time: 10:40:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class wstartInventoryAction extends Action {

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
            request.getSession(true).setAttribute("invmulti","NO");


            ResultSet rs = wInventoryUtilities.getRequestById(skuDTO.getSku());
                if(rs.next()){
                if("0".equals(rs.getString(3)) && skuDTO.getSku().equals(rs.getString(1))){
                  request.getSession(true).setAttribute("doingwinventoryid", skuDTO.getSku());
                  request.getSession(true).setAttribute("doingwinventoryclientname",rs.getString(2));
                  List wlocationsforrequest = wInventoryUtilities.getLocationsForRequest(skuDTO.getSku());
                  request.setAttribute("wlocationsforrequest",wlocationsforrequest);
                     int id = wInventoryUtilities.getIdFromCookies(request);
                  if (wInventoryUtilities.isInventoryAdmin(id, HibernateSession.currentSession())){
                      request.getSession(true).setAttribute("inventoryAdmin","yes");

                  }else{
                     request.getSession(true).setAttribute("inventoryAdmin","no");
                  }
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
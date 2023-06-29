package com.owd.dc.inventory.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.inventoryUtilities;
import com.owd.hibernate.generated.WarehouseInventory;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 23, 2005
 * Time: 1:34:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class viewRequestAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            checkAuthentication.check(request, response);
        } catch (Exception ex) {
            return (mapping.findForward("login"));
        }
        Calendar cal = Calendar.getInstance();
        skuForm invForm = (skuForm) form;
         skuDTO reqDTO = new skuDTO();
        BeanUtils.copyProperties(reqDTO, invForm);
        try{
           WarehouseInventory wi = inventoryUtilities.getWarehouseInventoryFromId(reqDTO.getSku());
            System.out.println("1");
           List invList = inventoryUtilities.getInventoryList(wi.getId().intValue());
               System.out.println("2");
          request.setAttribute("wi", wi);
           request.setAttribute("list", invList);








          return (mapping.findForward("success"));
      } catch (Exception ex){
          ex.printStackTrace();
          request.setAttribute("error",ex.getMessage());
          return (mapping.findForward("error"));
      }

}
}



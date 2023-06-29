package com.owd.dc.inventory.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.inventoryUtilities;
import com.owd.dc.inventory.forms.inventoryRequestForm;
import com.owd.dc.inventory.beans.inventoryRequestDTO;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WarehouseInventory;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 22, 2005
 * Time: 4:01:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class createInventoryRequestAction extends Action {


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
        inventoryRequestForm invForm = (inventoryRequestForm) form;
         inventoryRequestDTO invDTO = new inventoryRequestDTO();
        BeanUtils.copyProperties(invDTO, invForm);
       WarehouseInventory wi = new WarehouseInventory();
      try{
          wi.setClientFkey(invDTO.getClientFkey());
          System.out.println(invDTO.getClientFkey());
          wi.setWhosRequest((String) request.getAttribute("loginName"));
          wi.setDateCreated((Date) cal.getTime());
             System.out.println(cal.getTime());
          wi.setDone(new Integer(0));
          wi.setReference(invDTO.getReference());
          System.out.println(invDTO.getReference());
          if(invDTO.getAsnId()>0){
              wi.setAsnId(new Integer(invDTO.getAsnId()));
          }  else{
              wi.setAsnId(new Integer(0));
          }
          if(invDTO.getLocationToAssign().length() > 1){
              System.out.println("if");
              wi.setLocationToAssign(invDTO.getLocationToAssign());
              System.out.println(invDTO.getLocationToAssign())   ;
          }else{
              System.out.println("else");
             wi.setLocationToAssign("0");
          }

          HibernateSession.currentSession().save(wi);
            HibernateSession.currentSession().flush();
          HibernateSession.currentSession().refresh(wi);
          com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
          // System.out.println(invDTO.getDateCreated());
          request.setAttribute("outcome", "Created request with id of " + wi.getId());
          System.out.println("1st req");
            request.setAttribute("clientList", inventoryUtilities.getClientList());
          return (mapping.findForward("success"));
      } catch (Exception ex){
            request.setAttribute("clientList", inventoryUtilities.getClientList());
          ex.printStackTrace();
          request.setAttribute("error",ex.getMessage());
          return (mapping.findForward("error"));
      }

}
}
package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WInvRequest;
import com.owd.dc.inventory.inventoryUtilities;
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
public class wcreateInventoryRequestAction extends Action {


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
        winventoryRequestForm invForm = (winventoryRequestForm) form;
        winventoryRequestDTO invDTO = new winventoryRequestDTO();
        BeanUtils.copyProperties(invDTO, invForm);
       WInvRequest wi = new WInvRequest();
      try{
          wi.setClientFkey(invDTO.getClientFkey());
          System.out.println(invDTO.getClientFkey());
          wi.setDateCreated((Date) cal.getTime());
             System.out.println(cal.getTime());
          wi.setDone(new Integer(0));
          wi.setDesription(invDTO.getDescription());
          HibernateSession.currentSession().save(wi);
            HibernateSession.currentSession().flush();
          HibernateSession.currentSession().refresh(wi);
          com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
          // System.out.println(invDTO.getDateCreated());
         // request.setAttribute("outcome", "Created request with id of " + wi.getId());
          System.out.println("1st wreq");
            request.setAttribute("createrequestid", wi.getId());
          return (mapping.findForward("success"));
      } catch (Exception ex){
            request.setAttribute("clientList", inventoryUtilities.getClientList());
          ex.printStackTrace();
          request.setAttribute("error",ex.getMessage());
          return (mapping.findForward("error"));
      }

}
}
package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WInvRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.ObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 28, 2005
 * Time: 3:12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class wAddLocationAction extends Action {


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
       // Calendar cal = Calendar.getInstance();
        addWcountForm wForm = (addWcountForm) form;
        addWcountDTO wDTO = new addWcountDTO();
        BeanUtils.copyProperties(wDTO, wForm);
     //  WInvRequest wi = new WInvRequest();
         request.setAttribute("createrequestid",wDTO.getwRequestId()+"");
      try{
          if(wDTO.getLocation().startsWith("/")){
           WInvRequest wi = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class, new Integer(wDTO.getwRequestId()));
              System.out.println(wi.getClientFkey());
          if (!wInventoryUtilities.checkLocationAlreadyAdded(0, wi.getId(), wDTO.getLocation())) {
               wInventoryUtilities.addWcount(0, wi, wDTO.getLocation(), new Integer(0), new Integer(0), new Integer(0));
              request.setAttribute("outcome","Added Location "+ wDTO.getLocation());

            }else{
              request.setAttribute("outcome","Location Already Added");
          }
           return (mapping.findForward("success"));
          } else{
              throw new Exception(wForm.getLocation() + " is Not a Valid Location");}
         // request.setAttribute("outcome", "Created request with id of " + wi.getId());


      } catch (ObjectNotFoundException onf){
          onf.printStackTrace();
          request.setAttribute("error","System error, please restart the activity you were doing");
          return (mapping.findForward("startover"));


      } catch (Exception ex){
         //   request.setAttribute("clientList", inventoryUtilities.getClientList());
          ex.printStackTrace();
          request.setAttribute("error",ex.getMessage());
          return (mapping.findForward("error"));
      }

}
}


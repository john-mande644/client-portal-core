package com.owd.dc.locations.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.locationDTO;
import com.owd.dc.locations.forms.locationForm;
import com.owd.dc.locations.locationUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2005
 * Time: 1:15:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class clearAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception e) {
                return (mapping.findForward("login"));
            }
            locationForm locForm = (locationForm) form;
            locationDTO locDTO = new locationDTO();
            BeanUtils.copyProperties(locDTO, locForm);
             //check sku and loc for validity
              String loc = locDTO.getlocation();
                if (loc == null) throw new Exception("Location scan invalid");
                if (!(loc.startsWith("/"))) throw new Exception("Location code not valid");


            if(null==locDTO.getClientFkey()){
             List lb = locationUtilities.loadLocationsBean(locDTO.getlocation());
             request.setAttribute("locationsBean",lb);

                return mapping.findForward("pickClient");

            }
           if(locDTO.getClientFkey().equals("ALL")){

             locationUtilities.clearShelf(loc);
           }

            System.out.println("Clearing location");
            //String sku = req.getParameter("sku");


            try {


           locationUtilities.clearShelf(loc,locDTO.getClientFkey());


      // locationUtilities.clearShelf(loc);


            } catch (Exception ex) {
                request.setAttribute("error", ex.getMessage());
            } finally {
               // HibernateSession.closeSession();
            }

            if (request.getAttribute("error") != null) {
                return (mapping.findForward("success"));

            } else {
                return (mapping.findForward("success"));
            }

        } finally {

         //   HibernateTimeForceSession.closeSession();
         //   HibernateSession.closeSession();
        }
    }

}


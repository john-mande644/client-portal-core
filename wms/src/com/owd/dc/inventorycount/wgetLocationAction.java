package com.owd.dc.inventorycount;


import com.owd.dc.checkAuthentication;
import com.owd.dc.locations.locationInfoBean;
import com.owd.hibernate.HibernateSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 3, 2005
 * Time: 10:19:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class wgetLocationAction extends Action {

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

             System.out.println("We are in wgetLocaitnAction");
            wCountForm wCountForm = (wCountForm) form;
            wCountDTO wCountDTO = new wCountDTO();
            BeanUtils.copyProperties(wCountDTO, wCountForm);
           String locationName = "";
            if(wCountDTO.getLocation().equals("00300")){
                 request.setAttribute("createrequestid", wCountDTO.getRequestId()+"");
                return mapping.findForward("addLocation");
            }
            if (wCountDTO.getLocation().startsWith("/") == true) {
                List skus = new ArrayList();
                if (wCountDTO.getLocation().startsWith("//")){
                    System.out.println("We are in wgetLocaitnAction if //");
                    locationInfoBean lib = new locationInfoBean(wCountDTO.getLocation(), HibernateSession.currentSession());
                    locationName = lib.getPickString();
                } else{
                    locationName = wCountDTO.getLocation();
                }
              try{
                  System.out.println("We are in wgetLocaitnAction thr try skus");
                skus = wInventoryUtilities.getSkusInLocation(wCountDTO.getLocation(), wCountDTO.getRequestId());

               System.out.println("in this w thingy");

              }catch (Exception ex){
                  ex.printStackTrace();
                   List wlocationsforrequest = wInventoryUtilities.getLocationsForRequest(wCountDTO.getRequestId()+"");
                  request.setAttribute("wlocationsforrequest",wlocationsforrequest);
                   request.setAttribute("error",ex.getMessage());
            return (mapping.findForward("error"));
              }
                if(skus.size()>0) {
                 request.setAttribute("wskusinlocation",skus);
                    request.setAttribute("locationName",locationName);
                   return (mapping.findForward("success"));
                } else{
                    String location = wCountDTO.getLocation();
                    List wlocationsforrequest = wInventoryUtilities.getLocationsForRequest(wCountDTO.getRequestId()+"");
                  request.setAttribute("wlocationsforrequest",wlocationsforrequest);
                    throw new Exception("Location "+locationName+"is not Included in this Request");
             }

             }else {
                 List wlocationsforrequest = wInventoryUtilities.getLocationsForRequest(wCountDTO.getRequestId()+"");
                  request.setAttribute("wlocationsforrequest",wlocationsforrequest);
                throw new Exception("Not a valid Location");
            }

        } catch (Exception e) {
          request.setAttribute("error",e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}
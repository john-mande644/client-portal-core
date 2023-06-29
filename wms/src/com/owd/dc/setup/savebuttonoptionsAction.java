package com.owd.dc.setup;

import com.owd.WMSUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.owd.dc.checkAuthentication;
import com.owd.dc.setup.buttonForm;
import com.owd.dc.setup.buttonDTO;
import com.owd.dc.HandheldUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 3, 2005
 * Time: 11:34:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class savebuttonoptionsAction extends Action {

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

            buttonForm buttonForm = (buttonForm) form;
            buttonDTO buttonDTO = new buttonDTO();
            BeanUtils.copyProperties(buttonDTO, buttonForm);

            buttons btn = new buttons();
          /*  btn.setT5(buttonDTO.getT5());
            btn.setT6(buttonDTO.getT6());
            btn.setT7(buttonDTO.getT7());
            btn.setT8(buttonDTO.getT8());
            btn.setRed(buttonDTO.getRed());
            btn.setGreen(buttonDTO.getGreen());*/
            btn.setPrinter(buttonDTO.getPrinter());
            btn.setSmallPrinter(buttonDTO.getSmallPrinter());
            btn.setPalletTag(buttonDTO.getPalletTag());
            String facility;
            if(null!=buttonDTO.getFacility()){
            btn.setFacility(buttonDTO.getFacility());
                facility = buttonDTO.getFacility();
            }
            else{
                facility = WMSUtils.getFacilityFromRequest(request);
            }
            String id ="";
            for(Cookie c : request.getCookies()){
                if(c.getName().equals("tcid")){
                    System.out.println("we have id");
                    id = c.getValue();
                }
            }
            if(id.equals("")){
                return (mapping.findForward("error"));
            }
            HandheldUtilities.updateButtonsInDatabase(btn, (String) request.getAttribute("loginName"),id,facility);
           if(null!=buttonDTO.getFacility()){
               request.getSession(true).setAttribute("owd_current_location",btn.getFacility());

            Cookie facCook = new Cookie("wmsFacility",btn.getFacility());
            facCook.setPath("/wms");
            response.addCookie(facCook);
           }
           /* Cookie cookie3 = new Cookie("hhMetaButtons",btn.getT5()+"&"+btn.getT6()+"&"+btn.getT7()+"&"+btn.getT8()+"&"+btn.getRed()+"&"+btn.getGreen());
            response.addCookie(cookie3);
            request.setAttribute("hhMetaButtonsAction",btn);*/

            request.setAttribute("outcome","Saved Settings");

            return (mapping.findForward("success"));
        } finally {

           // HibernateTimeForceSession.closeSession();
           // HibernateSession.closeSession();
        }
    }







}

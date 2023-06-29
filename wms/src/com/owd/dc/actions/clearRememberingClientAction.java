package com.owd.dc.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.Action;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.beans.skuDTO;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 5, 2005
 * Time: 9:51:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class clearRememberingClientAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
           skuForm skuForm = (skuForm) form;
            skuDTO skuDTO = new skuDTO();
            BeanUtils.copyProperties(skuDTO, skuForm);

        Cookie cookie = new Cookie("remfkey", null);


                    Cookie cookie2 = new Cookie("remname", null);
                    response.addCookie(cookie);
                    response.addCookie(cookie2);

                     request.removeAttribute("remember");
                    request.removeAttribute("rememberclientname");
        request.setAttribute("loginName", skuDTO.getSku());
                   return (mapping.findForward("success"));

    }
}

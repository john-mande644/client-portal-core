package com.owd.dc.actions;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 1:53:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class logoutAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

           for (Cookie c:request.getCookies()){
               boolean remove = false;
               if (c.getName().equals("tcid")){
                    remove = true;
               }
               if (c.getName().equals("tcname")){
                   remove = true;
               }
              if (remove){
                  Cookie del = new Cookie(c.getName(),c.getValue());
                  del.setMaxAge(0);
del.setValue(null);
del.setPath("/wms");
                  System.out.println("removing cookie"+c.getName());
                 c.setMaxAge(0);
                  response.addCookie(del);
              }
           }


       
                   return (mapping.findForward("success"));

    }
}

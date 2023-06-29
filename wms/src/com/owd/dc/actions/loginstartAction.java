package com.owd.dc.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.HibernateSession;
import com.owd.dc.setup.selectList;
import com.owd.dc.loginForm;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 2, 2005
 * Time: 3:17:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class loginstartAction  extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        try {
             System.out.println("hi");
            loginForm loginForm = (loginForm)form;
            String sql ="select loc_name, loc_code from dbo.owd_facilities  (NOLOCK) ";
            ResultSet rs = HibernateSession.getResultSet(sql);
            List facility = null;
            int i =0;
            while (rs.next()){
                selectList list = new selectList();

             list.setDisplay(rs.getString(1));
            list.setAction(rs.getString(2));
             facility.add(i,list);
              i++;
            }
            request.setAttribute("lists",facility);
           return (mapping.findForward("success"));
        } catch (Exception ex){
            request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("success"));
        }

    }
}

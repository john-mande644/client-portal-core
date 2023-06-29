package com.owd.dc.inventorycount;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WInvCounts;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 23, 2006
 * Time: 8:05:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class editCountItemAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        try {
          countEditForm cf = (countEditForm) form;
            if(cf.getId()==null){
                request.setAttribute("error", "We apologize for the Error, but you must start over.");

              return mapping.findForward("sessionEx");
          }
            WInvCounts wi = (WInvCounts) HibernateSession.currentSession().load(WInvCounts.class, Integer.valueOf(cf.getItemId()));
            cf.setQuanity(wi.getQuanity()+"");
            cf.setWho(wi.getByWho());
            cf.setLocation(wi.getLocation());


              return mapping.findForward("success");
        } catch (Exception e) {

            return mapping.findForward("error");
        }
    }
}

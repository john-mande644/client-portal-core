package com.owd.dc.inventorycount;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.generated.WInvCounts;
import com.owd.hibernate.HibernateSession;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 23, 2006
 * Time: 9:58:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class countSaveEditAction extends Action {

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
          Integer quanity = Integer.decode(cf.getQuanity());
            WInvCounts wi = (WInvCounts) HibernateSession.currentSession().load(WInvCounts.class, Integer.valueOf(cf.getItemId()));
            wi.setQuanity(quanity.intValue());
            HibernateSession.currentSession().save(wi);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());




              return mapping.findForward("success");
        } catch (NumberFormatException ne){
         request.setAttribute("error","Quanity must be numeric");
            return mapping.findForward("error");
        }
            catch (Exception e) {


            return mapping.findForward("error");
        }
    }
}

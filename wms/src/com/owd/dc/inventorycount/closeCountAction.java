package com.owd.dc.inventorycount;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WInvRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 5, 2006
 * Time: 10:37:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class closeCountAction extends Action {

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

            WInvRequest wi = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class, Integer.valueOf(cf.getId()));
            if("open".equals(cf.getLocation())){
             wi.setDone(new Integer(0));
            }else{
            wi.setDone(new Integer(2));
            }
            HibernateSession.currentSession().save(wi);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());




              return mapping.findForward("success");
        }

            catch (Exception e) {


            return mapping.findForward("error");
        }
    }
}

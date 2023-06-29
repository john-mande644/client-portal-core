package com.owd.dc.inventorycount;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WInvLocations;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 27, 2006
 * Time: 9:40:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class countLocationReopenAction extends Action {

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

              System.out.println(cf.getId());
            System.out.println(cf.getLocation());
        WInvLocations wi =   wInventoryUtilities.getWInvLocFromLocation(HibernateSession.currentSession(),cf.getId(), cf.getLocation());
              wi.setDone(new Integer(0));
            HibernateSession.currentSession().save(wi);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

             System.out.println("done with re-open");


              return mapping.findForward("success");
        } catch (Exception e) {
             request.setAttribute("error",e.getMessage());
          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
            return mapping.findForward("error");
        }
    }
}

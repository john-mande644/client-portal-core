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
 * Date: Mar 29, 2006
 * Time: 11:33:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class locationCountSaveAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        try {

               listOfCountEditForms cef = (listOfCountEditForms) form;
            System.out.println("in save");
           for(int i = 0; i<cef.getFormItems().length;i++){
               countEditBean cb = cef.getFormItems()[i];
              System.out.println("in save 1");
               if("yes".equals(cb.getDelete())){
                   System.out.println("in save 2");
                  WInvCounts wi = (WInvCounts) HibernateSession.currentSession().load(WInvCounts.class, Integer.valueOf(cb.getItemId()));
                   System.out.println("in save 3");
            HibernateSession.currentSession().delete(wi);


               } else{
                 WInvCounts wi = (WInvCounts) HibernateSession.currentSession().load(WInvCounts.class, Integer.valueOf(cb.getItemId()));
                wi.setQuanity(Integer.parseInt(cb.getQuanity()));
                   HibernateSession.currentSession().save(wi);
               }




           }
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

            request.setAttribute("outcome", "Save sucessfull!!!");
            countEditForm cf = new countEditForm();
            cf.setId(cef.getId());
            cf.setLocation(cef.getLocation());
            cf.setInvId(cef.getInvId());
             request.setAttribute("countEditForm", cf);

            return mapping.findForward("success");
        } catch (Exception e) {
            e.printStackTrace();
             request.setAttribute("error",e.getMessage());
          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
            return mapping.findForward("error");
        }
    }
}

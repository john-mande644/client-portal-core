package com.owd.dc.setup;

import com.owd.hibernate.HibernateSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Sep 6, 2006
 * Time: 11:43:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class emailNotifyChanges extends LookupDispatchAction {


    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.emailChange", "saveEmail");


        return map;
    }

    //Default action, forwads to askinf about packing slip
    public ActionForward unspecified(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        try {
            //Checking for login

            emailForm eForm = (emailForm) form;
            eForm.setHold(emailList.getInstance().getHoldEmail());
            eForm.setStockout(emailList.getInstance().getStockoutEmail());
            return (mapping.findForward("success"));


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
     public ActionForward saveEmail(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        try {
            //Checking for login

            emailForm eForm = (emailForm) form;
            //todo update
            emailList.getInstance().updateList(eForm.getHold(),eForm.getStockout(), ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

            eForm.setHold(emailList.getInstance().getHoldEmail());
            eForm.setStockout(emailList.getInstance().getStockoutEmail());
            request.setAttribute("outcome","Email's Saved!!!!!!!!!!");
            return (mapping.findForward("success"));


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

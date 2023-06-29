package com.owd.dc.receiving.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.receivingUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.Receive;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 26, 2006
 * Time: 1:50:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class askPackAction extends LookupDispatchAction {

    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.packYes", "packingSlip");
        map.put("button.packNo", "noSlip");
        map.put("button.none", "none");

        return map;
    }

/// packing slip found go on.........................................................
    public ActionForward packingSlip(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
            throws Exception {
        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            System.out.println("in match = yes");

            receiveForm rcvForm = (receiveForm) form;
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
            rcv.setIsPackSlipFound(1);
            rcv.setIsPackSlipMatch(1);
            System.out.println("After setting rcv valuels");
            receivingUtilities.saveObject(HibernateSession.currentSession(), rcv);
            System.out.println("After save");

            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    /// Do this if no packing slip button is pushed................................................................
    public ActionForward noSlip(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
            throws Exception {
        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            System.out.println("in match = no");
            receiveForm rcvForm = (receiveForm) form;
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
            rcv.setIsPackSlipFound(1);


            receivingUtilities.saveObject(HibernateSession.currentSession(), rcv);

            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    public ActionForward none(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
            throws Exception {
        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
           System.out.println("in none");
            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

}

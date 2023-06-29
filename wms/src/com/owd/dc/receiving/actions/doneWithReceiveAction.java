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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 5, 2006
 * Time: 3:33:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class doneWithReceiveAction extends LookupDispatchAction {


    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.packYes", "packingSlip");
        map.put("button.packNo", "noSlip");
        map.put("button.enterPackages", "packages");
        map.put("button.enterTime", "time");
        map.put("button.none", "none");

        return map;
    }

    //Default action, forwads to askinf about packing slip
    public ActionForward unspecified(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            receiveForm rcvForm = (receiveForm) form;
            return (mapping.findForward("getPackingSlip"));


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    //action for entering packingslip
    public ActionForward packingSlip(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            receiveForm rcvForm = (receiveForm) form;
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
            rcv.setIsPackSlipFound(1);
            rcv.setIsPackSlipMatch(1);
            receivingUtilities.saveObject(HibernateSession.currentSession(), rcv);


            return (mapping.findForward("getPackages"));


        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    public ActionForward none(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            receiveForm rcvForm = (receiveForm) form;

                        Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
                        rcv.setIsPackSlipFound(0);
                        rcv.setIsPackSlipMatch(0);
                        receivingUtilities.saveObject(HibernateSession.currentSession(), rcv);

            return (mapping.findForward("getPackages"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    public ActionForward noSlip(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            receiveForm rcvForm = (receiveForm) form;
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
            rcv.setIsPackSlipFound(1);
            rcv.setIsPackSlipMatch(0);
            receivingUtilities.saveObject(HibernateSession.currentSession(), rcv);

            return (mapping.findForward("getPackages"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    //action for entering packages
    public ActionForward packages(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            receiveForm rcvForm = (receiveForm) form;
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
            rcv.setCartonCount(Integer.parseInt(rcvForm.getCartonCount()));
            receivingUtilities.saveObject(HibernateSession.currentSession(), rcv);
            return (mapping.findForward("getTime"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

    public ActionForward time(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            receiveForm rcvForm = (receiveForm) form;
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(rcv.getStartTimestamp().getTime() + (Integer.parseInt(rcvForm.getTime()) * 60000));
            System.out.println(c.getTime());
            rcv.setEndTimestamp(c.getTime());
            receivingUtilities.saveObject(HibernateSession.currentSession(),rcv);
            return (mapping.findForward("success"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

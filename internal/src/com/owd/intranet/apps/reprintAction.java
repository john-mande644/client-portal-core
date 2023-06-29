package com.owd.intranet.apps;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

import com.owd.intranet.forms.reprintForm;
import com.owd.intranet.util;
import com.owd.hibernate.HibernateSession;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 8:57:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class reprintAction extends LookupDispatchAction {
private final static Logger log =  LogManager.getLogger();


    protected Map getKeyMethodMap() {
        //log.debug("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.loadReprint", "loadReprint");
        map.put("button.doReprint", "print");

        return map;
    }

    public ActionForward loadReprint(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

            try {
                //Checking for login

               reprintForm rpf = (reprintForm) form;
                rpf.setPrinter("main_office");
               rpf.setOrder(util.getOrderFromNum(HibernateSession.currentSession(),rpf.getOrderNum()));
               //log.debug(rpf.getOrder().getBillFirstName()+" xxxxxxxxxx");
                return (mapping.findForward("loaded"));


            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", ex.getMessage());
                return mapping.findForward("error");

            }
        }
   public ActionForward print(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

            try {
                //Checking for login

               reprintForm rpf = (reprintForm) form;
                rpf.setOrder(util.getOrderFromNum(HibernateSession.currentSession(),rpf.getOrderNum()));
                util.printInvoice(rpf.getOrder(),HibernateSession.currentSession(),rpf.getPrinter());

                request.setAttribute("outcome","Printed "+rpf.getOrderNum()+" to "+rpf.getPrinter());
               return (mapping.findForward("printed"));


            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", ex.getMessage());
                return mapping.findForward("error");

            }
        }
}

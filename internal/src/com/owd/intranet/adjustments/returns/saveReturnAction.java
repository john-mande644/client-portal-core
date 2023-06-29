package com.owd.intranet.adjustments.returns;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.intranet.forms.returnForm;
import com.owd.hibernate.HibernateSession;
import com.owd.core.managers.InventoryManager;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 2:19:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class saveReturnAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {

           returnForm rf = (returnForm) form;
            if(!"".equals(rf.getOrderNum())||null==rf.getOrderNum()){
                //log.debug("before return");
              loadReturnOrderAction e = new loadReturnOrderAction();

               e.execute(mapping,form,request,response);
              return mapping.findForward("error");

            }
            if(!"".equals(rf.getSku())||null==rf.getSku()){
                addItemAction a = new addItemAction();
                a.execute(mapping,form,request,response);
                return mapping.findForward("error");
            }
            if(InventoryManager.countInProgress(HibernateSession.currentSession(), Integer.parseInt(rf.getClientFkey()))){
            throw new Exception("Inventory Count in progress, you cannot adjust inventory");
        }

           //  String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
           // java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
           //  Calendar cal = Calendar.getInstance();
             rf.setReceiveUser(request.getRemoteUser());
          //  rf.setReceiveDate(sdf.format(cal.getTime()));
            if(isTokenValid(request)) {
                rf = returnUtil.saveReturn(rf, HibernateSession.currentSession());
            }else{
                throw new Exception("Invalid return submitted. Please start over.");
            }
            resetToken(request);
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors",e.getMessage());
            return mapping.findForward("error");

        }
    }


}

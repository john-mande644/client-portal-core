package com.owd.dc.timeclock;

import com.owd.dc.actions.OWDLookupDispatchAction;
import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.receivingUtilities;
import com.owd.hibernate.generated.ReceiveItem;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 13, 2008
 * Time: 4:02:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class timeClockJobChangeAction extends OWDLookupDispatchAction {


    protected Map getKeyMethodMap() {
            System.out.println("getKeyMethodMap invoked..");
            Map map = new HashMap();
            map.put("button.changeJob", "changeJob");

            return map;
        }
   //action for edit quanity
   public ActionForward unspecified(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            saveToken(request);
            timeclockForm tcf = (timeclockForm) form;
            for(Cookie c:request.getCookies()){
           if(c.getName().equals("tcid")){
            tcf.setTimeClockId(c.getValue());
                System.out.println("We are using this id for the timeclock"+tcf.getTimeClockId());
           }
       }
        tcf.setCodeList(timeclockUtil.getJobCodes());
            return (mapping.findForward("start"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

 public ActionForward changeJob(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
             saveToken(request);
            timeclockForm tcf = (timeclockForm) form;
            System.out.println(tcf.getJobCode());
        System.out.println("hello in change joby");
        System.out.println(tcf.getTimeClockId());
          String ip = request.getRemoteAddr();
        System.out.println(ip);
        StringBuffer sb = new StringBuffer();

       

        System.out.println(sb.toString());  

            return (mapping.findForward("start"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }


     public ActionForward editWeight(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            saveToken(request);
            receiveForm rcvForm = (receiveForm) form;
            rcvForm.setAction("edit");
            return (mapping.findForward("weight"));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }

   public ActionForward verify(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception{

    try {
            //Checking for login
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            receiveForm rcvForm = (receiveForm) form;
           ReceiveItem ri = (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class, Integer.valueOf(rcvForm.getReceiveItemId()));
            if(!rcvForm.getInvId().equals(ri.getInventoryFkey()+"")) {
                System.out.println("BAD BAD BAD");
                request.setAttribute("error", "There was an error, the sku you are working on was not saved");
                return mapping.findForward("reset");
            }
            //check for serial numbers required
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,  Integer.valueOf(rcvForm.getInvId()));
            if(inv.getRequireSerialNumbers()==1){
                return mapping.findForward("serial");
            }
        System.out.println(rcvForm.getRcvId());
        if(isCurrentRequest(request)){
            receivingUtilities.updateReceiveItem(rcvForm,ri);
        } else{
            request.setAttribute("error","Already saved");
            return mapping.findForward("success");
        }
        if("yes".equals(rcvForm.getDoLabelPrint())){
            return (mapping.findForward("printLabel"));
        }
           return (mapping.findForward("success"));

        } catch (Exception ex) {

            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");

        }
    }
}

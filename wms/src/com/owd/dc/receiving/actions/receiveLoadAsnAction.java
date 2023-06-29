package com.owd.dc.receiving.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.receivingUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.Receive;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 16, 2006
 * Time: 9:44:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class receiveLoadAsnAction extends Action {


    public ActionForward execute(ActionMapping mapping,
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

          receiveForm rcvForm = (receiveForm) form;
            Receive rcv = null;
           boolean scanDocs = false;
         //Check for Rcv already loaded;
          if (rcvForm.getAsnId()!=null){
              System.out.println("asn not empty");
              rcv = receivingUtilities.getReceive(rcvForm.getAsnId(), WMSUtils.getFacilityFromRequest(request));

          }  else{
              //Check for location Match
              System.out.println("asn is empty using rcvId");
             rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
              System.out.println(rcv.getReceiveItems().size());
             rcvForm.setAsnId(rcv.getAsn().getId().toString());
          }
              if(rcv.getReceiveBy().equals("")){
                  scanDocs = true;
              }
              rcv.setReceiveBy((String)request.getAttribute("loginName"));
              receivingUtilities.saveObject(HibernateSession.currentSession(),rcv);
            if (rcv == null){
                  throw new Exception("No receive Created, Try again");
            }
            request.setAttribute("countList", receivingUtilities.getReceivedItems(rcv));
            rcvForm.setRcvId(rcv.getId().toString());

            System.out.println(rcvForm.getRcvId());
            if(scanDocs) return (mapping.findForward("scanDocs"));
            Map info = new TreeMap();
            OwdClient client = (OwdClient)HibernateSession.currentSession().load(OwdClient.class, new Integer(rcv.getClientFkey()));
            info.put("client",client.getCompanyName());
            info.put("desc",rcv.getAsn().getClientRef());
            request.setAttribute("info",info);
            return (mapping.findForward("success"));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));

        }
    }
}

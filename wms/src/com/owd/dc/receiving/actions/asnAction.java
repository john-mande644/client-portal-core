package com.owd.dc.receiving.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.ReceiveStatus;
import com.owd.dc.checkAuthentication;

import java.sql.ResultSet;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Aug 10, 2005
 * Time: 10:21:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class asnAction extends Action {
    private ResultSet rs;
    private String getreceiveid;

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
            getreceiveid = "select id, current_rcv_item, asn_fkey from dbo.receivestatus  (NOLOCK) where \n" +
                    "agent ='" + request.getAttribute("loginName") + "'";

            System.out.println(getreceiveid);
            rs = HibernateSession.getResultSet(HibernateSession.currentSession(), getreceiveid);
            if (rs.next()) {
                System.out.println("in next" + rs.getString(2));
                if (rs.getString(2).equals("0")) {
                    ReceiveStatus rcvstatus = (ReceiveStatus) HibernateSession.currentSession().load(ReceiveStatus.class, new Integer(rs.getString(1)));
                    System.out.println(rcvstatus.getAsnFkey());
                    Collection items = rcvstatus.getReceiveStatusItems();
                    System.out.println(items);
                    request.setAttribute("step", "selectSku");
                    request.setAttribute("items", items);
                    request.setAttribute("asnid", rs.getString(3));
                    request.setAttribute("rcvid", rs.getString(1));
                    return mapping.findForward("success");
                }

            } else {
                request.setAttribute("step", "start");
                return (mapping.findForward("success"));
            }

            request.setAttribute("step", "start");
            return (mapping.findForward("success"));
        } finally {

            //HibernateTimeForceSession.closeSession();
            HibernateSession.closeSession();
        }
    }
}
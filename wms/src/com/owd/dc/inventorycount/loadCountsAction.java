package com.owd.dc.inventorycount;

import com.owd.hibernate.HibernateSession;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 22, 2006
 * Time: 10:05:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class loadCountsAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        try {
             String set = "30";
            if(null != request.getParameter("countload")){
                 set = (String) request.getParameter("countload");
            }else{
                set = (String) request.getSession(true).getAttribute("countload");
            }
            ResultSet rs = null;
            if ("all".equals(set)){
                request.getSession(true).setAttribute("countload","all");
             rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select w_inv_request.id as 'Link',company_name as 'Client',w_inv_request.description as 'Notes',w_inv_request.date_created as 'Created',\n" +
                    "w_inv_request.done as 'Posted',(100*(sum(w_inv_locations.done)))/count(*) as 'Complete' from w_inv_request (NOLOCK)  \n" +
                    "join owd_client (NOLOCK)  on client_id=client_fkey join w_inv_locations (NOLOCK)  on w_inv_request.id=inv_request_fkey\n" +
                    "where inventory_num is null\n" +
                    "group by company_name,w_inv_request.id,w_inv_request.description,w_inv_request.date_created,\n" +
                    "w_inv_request.done order by w_inv_request.id desc");
            }else{
                System.out.println("Doing 30 days");
                request.getSession(true).setAttribute("countload","30");
               rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select w_inv_request.id as 'Link',company_name as 'Client',w_inv_request.description as 'Notes',w_inv_request.date_created as 'Created',\n" +
                    "w_inv_request.done as 'Posted',(100*(sum(w_inv_locations.done)))/count(*) as 'Complete' from w_inv_request \n" +
                    "join owd_client on client_id=client_fkey join w_inv_locations on w_inv_request.id=inv_request_fkey\n" +
                    "where inventory_num is null and date_created > (getDate()-30)\n" +
                    "group by company_name,w_inv_request.id,w_inv_request.description,w_inv_request.date_created,\n" +
                    "w_inv_request.done order by w_inv_request.id desc");
            }
            int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

            for (int i = 1; i <= cols; i++) {
              columnBean c =  new columnBean();
                c.setName(rs.getMetaData().getColumnName(i).toString());
                colList.add(c);
            }

            System.out.println("getting dynaset");
            RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
            System.out.println("got dynaset");
            request.getSession(true).setAttribute("reportrs1c", displayrsc);
            request.getSession(true).setAttribute("collist", colList);




              return mapping.findForward("success");
        } catch (Exception e) {
                request.setAttribute("error",e.getMessage());
          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
            return mapping.findForward("error");
        }
    }
}

package com.owd.dc.inventorycount;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.RowSetDynaClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import com.owd.hibernate.HibernateSession;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 24, 2006
 * Time: 3:48:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class loadCountLocationAction  extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        try {
          countEditForm cf = (countEditForm) form;
           if(cf.getId()==null){
                request.setAttribute("error", "We apologize for the Error, but you must start over.");

              return mapping.findForward("sessionEx");
          }
            if(null != request.getParameter("locPage")){
                request.getSession(true).setAttribute("locPage",request.getParameter("locPage"));
                request.getSession(true).setAttribute("locPageNum",request.getParameter("locPageNum"));

            }
             ResultSet rs1 = HibernateSession.getResultSet(HibernateSession.currentSession(), "SELECT DISTINCT location\n" +
                   "FROM         w_inv_locations (NOLOCK) \n" +
                   "WHERE     (inv_request_fkey = "+cf.getId()+")");
          List loc = new ArrayList();
            while(rs1.next()){
             loc.add(rs1.getString(1));
          }
          if(!loc.contains(cf.getLocation())){
           throw new Exception("Location"+cf.getLocation()+" not found in request");
          }
            System.out.println("load location edit sku's");
              System.out.println(cf.getId());
            System.out.println(cf.getLocation());
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "SELECT     w_inv_counts.inventory_id, SUM(w_inv_counts.quanity) AS Quanity, COUNT(w_inv_counts.id) AS Enteries\n" +
                    "FROM         w_inv_locations  (NOLOCK) LEFT OUTER JOIN\n" +
                    "                      w_inv_counts  (NOLOCK) ON w_inv_locations.id = w_inv_counts.inv_loc_fkey\n" +
                    "WHERE     (w_inv_locations.inv_request_fkey ="+cf.getId()+") AND (w_inv_locations.location = '"+cf.getLocation()+"') AND (w_inv_locations.inventory_num IS NOT NULL)\n" +
                    "GROUP BY w_inv_counts.inventory_id\n" +
                    "HAVING      (w_inv_counts.inventory_id IS NOT NULL)");
            int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

            for (int i = 1; i <= cols; i++) {
              columnBean c =  new columnBean();
                c.setName(rs.getMetaData().getColumnName(i).toString());
                colList.add(c);
            }

            System.out.println("getting location 1dynaset");
            RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
            System.out.println("got location1dynaset");
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

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
import com.owd.hibernate.generated.WInvRequest;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 24, 2006
 * Time: 2:28:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class loadCountLocations  extends Action {

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
           if(null != request.getParameter("mainPage")){
                request.getSession(true).setAttribute("mainPage",request.getParameter("mainPage"));
                request.getSession(true).setAttribute("mainPageNum",request.getParameter("mainPageNum"));

            }
            System.out.println("la la testing la la");
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "SELECT     w_inv_locations.location, w_inv_locations.locationDisplay as 'Readable Location', COUNT(w_inv_counts.id) AS items_counted, MIN(w_inv_locations.done) AS closed\n" +
                    "FROM         w_inv_locations  (NOLOCK) LEFT OUTER JOIN\n" +
                    "                      w_inv_counts  (NOLOCK) ON w_inv_locations.id = w_inv_counts.inv_loc_fkey\n" +
                    "WHERE     (w_inv_locations.inv_request_fkey = "+cf.getId()+")\n" +
                    "GROUP BY w_inv_locations.location, w_inv_locations.locationDisplay");
            int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

            for (int i = 1; i <= cols; i++) {
              columnBean c =  new columnBean();
                c.setName(rs.getMetaData().getColumnName(i).toString());
                colList.add(c);
            }
           WInvRequest wr = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class,  Integer.valueOf(cf.getId()));
            cf.setPosted(wr.getDone().toString());
            System.out.println("getting location dynaset");
            RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
            System.out.println("got locationdynaset");
            request.getSession(true).setAttribute("reportrs1c", displayrsc);
            request.getSession(true).setAttribute("collist", colList);




              return mapping.findForward("success");
        } catch (Exception e) {
               e.printStackTrace();
          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
            return mapping.findForward("error");
        }
    }
}

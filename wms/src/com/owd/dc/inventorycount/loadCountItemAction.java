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
import com.owd.dc.HandheldUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 23, 2006
 * Time: 10:23:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class loadCountItemAction  extends Action {

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
            if(null != request.getParameter("skuPage")){
                request.getSession(true).setAttribute("skuPage",request.getParameter("skuPage"));
                request.getSession(true).setAttribute("skuPageNum",request.getParameter("skuPageNum"));

            }
            System.out.println("hello2");
              System.out.println(cf.getId());
            System.out.println(cf.getInvId());
            if(null!=cf.getAction()){
                WInvRequest wi = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class,Integer.valueOf(cf.getId()));

                cf.setInvId(HandheldUtilities.getIdFromSku(cf.getInvId(), wi.getClientFkey()+""));
                cf.setAction(null);
            }
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "SELECT     w_inv_counts.location,w_inv_locations.locationDisplay as 'Readable Location', w_inv_counts.quanity, w_inv_counts.by_who, w_inv_counts.id\n" +
                    "FROM         w_inv_counts  (NOLOCK) INNER JOIN\n" +
                    "                      w_inv_locations (NOLOCK)  ON w_inv_counts.inv_loc_fkey = w_inv_locations.id\n" +
                    "WHERE     (w_inv_locations.inv_request_fkey = "+cf.getId()+") AND (w_inv_locations.inventory_id = "+cf.getInvId()+")");
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

package com.owd.dc.inventory.actions;

import com.owd.dc.inventory.inventoryUtilities;
import com.owd.dc.inventorycount.historyBean;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.locations.locationInfoBean;
import com.owd.hibernate.HibernateSession;
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
 * User: Danny
 * Date: Jul 6, 2006
 * Time: 11:14:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class viewLocationHistoryAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        try {
             skuForm sf = (skuForm) form;
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "select location, assign_date, assigned_by from dbo.owd_inventory_locations  (NOLOCK) where \n" +
                    "inventory_fkey = "+sf.getSku());
            //int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

             while(rs.next()){
                 System.out.println("in while"+rs.getString(1));
                 historyBean hs = new historyBean();
                 hs.setLocation(rs.getString(1));
                 hs.setAssignDate(rs.getString(2));
                 hs.setUser(rs.getString(3));
                  if(hs.getLocation().startsWith("//")){
                      locationInfoBean lib = new locationInfoBean(hs.getLocation().replace("//",""),HibernateSession.currentSession());
                    hs.setReadableLocation(lib.getFormatedPickString());
                  } else{
                      hs.setReadableLocation(hs.getLocation());
                  }
                 colList.add(hs);
             }
            rs.close();

            colList.addAll(inventoryUtilities.getLocationHistoryFromId(sf.getSku()));

            request.getSession(true).setAttribute("list", colList);
           //request.getSession(true).setAttribute("collist", colList);
               sf.setDisplay(sf.getSku());   



              return mapping.findForward("success");
        } catch (Exception e) {
              request.setAttribute("error", e.getMessage());
          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
            return mapping.findForward("error");
        }
    }
}

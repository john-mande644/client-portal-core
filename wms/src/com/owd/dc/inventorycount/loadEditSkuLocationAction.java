package com.owd.dc.inventorycount;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.beanutils.DynaBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.hibernate.HibernateSession;


import java.sql.ResultSet;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 27, 2006
 * Time: 2:15:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class loadEditSkuLocationAction extends Action {

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
            listOfCountEditForms productForm = new listOfCountEditForms();
            productForm.setId(cf.getId());
            productForm.setInvId(cf.getInvId());
            productForm.setLocation(cf.getLocation());
            System.out.println("hello location");
              System.out.println(cf.getId());
            System.out.println(cf.getLocation());

            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "SELECT     w_inv_counts.id, w_inv_counts.inventory_id, w_inv_counts.quanity, w_inv_counts.by_who\n" +
                    "FROM         w_inv_locations (NOLOCK)  INNER JOIN\n" +
                    "                      w_inv_counts (NOLOCK)  ON w_inv_locations.id = w_inv_counts.inv_loc_fkey\n" +
                    "WHERE     (w_inv_counts.inventory_id = "+cf.getInvId()+") AND (w_inv_counts.location = '"+cf.getLocation()+"') AND (w_inv_locations.inv_request_fkey = "+cf.getId()+")");


             RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);

               System.out.println("Size of array " + displayrsc.getRows().size());

            countEditBean products[] = new countEditBean[displayrsc.getRows().size()];



             int  i=0;
             Iterator it =    displayrsc.getRows().iterator();
             while(it.hasNext()){
                    DynaBean d = (DynaBean) it.next();

                      System.out.println("doing rs");
                        products[i] = new countEditBean();
                        products[i].setInvId(d.get("inventory_id").toString());
                        products[i].setQuanity(d.get("quanity").toString());
                 products[i].setItemId(d.get("id").toString());
                 products[i].setWho(d.get("by_who").toString());
                    i++;
                    }

                  productForm.setFormItems(products);
                System.out.println("6");

            request.setAttribute("listOfCountEditForms", productForm);
              return mapping.findForward("success");
        } catch (Exception e) {
            e.printStackTrace();
             request.setAttribute("error",e.getMessage());
          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
            return mapping.findForward("error");
        }
    }
}

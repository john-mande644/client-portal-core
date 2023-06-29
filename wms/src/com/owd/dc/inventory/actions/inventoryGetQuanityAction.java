package com.owd.dc.inventory.actions;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.WarehouseInventoryCounts;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.dc.inventory.inventoryUtilities;
import com.owd.dc.checkAuthentication;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Oct 3, 2005
 * Time: 10:37:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryGetQuanityAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        String sku = new String();
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }


            skuForm skuForm = (skuForm) form;
            skuDTO skuDTO = new skuDTO();
            BeanUtils.copyProperties(skuDTO, skuForm);
            System.out.println(request.getSession(true).getAttribute("doinginventoryclientfkey"));
             System.out.println(request.getSession(true).getAttribute("doinginventoryid"));
             System.out.println(request.getSession(true).getAttribute("dilocation"));
            Integer clientFkey = new Integer((String) request.getSession(true).getAttribute("doinginventoryclientfkey"));
           int doingId = Integer.parseInt( (String) request.getSession(true).getAttribute("doinginventoryid"));
            String location = (String) request.getSession(true).getAttribute("dilocation");
            String name= (String) request.getAttribute("loginName");
             sku = skuDTO.getSku();
            int qty = 0;

            try{
                qty = skuDTO.getNumlabels();
            } catch(Exception ex){
                 request.setAttribute("error", "quanity must be numeric");
                request.setAttribute("sku",sku);
                return mapping.findForward("error");
            }


            Calendar cal = Calendar.getInstance();
            WarehouseInventoryCounts wcount = new WarehouseInventoryCounts();
            wcount.setClientFkey(clientFkey);
            wcount.setInventoryId(Integer.parseInt(sku));
            wcount.setInventoryNum(skuDTO.getInventoryNum());
            wcount.setName(name);
            wcount.setQuanity(qty);
            wcount.setWarehouseInventoryFkey(doingId);
            wcount.setCountDate(cal.getTime());
            HibernateSession.currentSession().saveOrUpdate(wcount);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.currentSession().refresh(wcount);
          //todo insert location into records
            if(location.equals("0") == false){
                System.out.println("in set location") ;
            inventoryUtilities.insertLocation(wcount, (String) request.getSession(true).getAttribute("dilocation"));
            }
       //        int rows = stmt.executeUpdate();
       //   conn.commit();
        //    conn.close();
               request.setAttribute("outcome","Counted "+qty + " of sku " + sku);
            request.setAttribute("countid",""+wcount.getId());
            request.setAttribute("sku",sku);
            return (mapping.findForward("success"));


        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("sku",sku);
          request.setAttribute("error",e);
            return (mapping.findForward("error"));
        }
    }
}


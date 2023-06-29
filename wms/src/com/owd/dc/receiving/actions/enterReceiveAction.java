package com.owd.dc.receiving.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.asnForm;
import com.owd.dc.receiving.beans.asnDTO;
import com.owd.dc.inventory.inventoryUtilities;
import com.owd.core.business.asn.ASNFactory;
import com.owd.core.business.asn.ASNManager;
import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.Receive;
import com.owd.hibernate.generated.WarehouseInventory;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 23, 2005
 * Time: 3:20:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class enterReceiveAction  extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            checkAuthentication.check(request, response);
        } catch (Exception ex) {
            return (mapping.findForward("login"));
        }
        Calendar cal = Calendar.getInstance();
        asnForm asnForm = (asnForm) form;
         asnDTO recDTO = new asnDTO();
        BeanUtils.copyProperties(recDTO, asnForm);
        try{
          WarehouseInventory wi = inventoryUtilities.getWarehouseInventoryFromId(recDTO.getWiId());
         Asn asn = ASNFactory.getAsnFromAsnID(wi.getAsnId(), wi.getClientFkey(),true);
         List items = inventoryUtilities.getInventoryList(wi.getId().intValue());
         if(inventoryUtilities.itemInASN(asn, items)){
        Receive rcv = ASNFactory.getNewReceive(asn);

        inventoryUtilities.updateReceiveFromEditPage(rcv, wi, (String) request.getAttribute("loginName"), recDTO, items);
         ASNManager.saveOrUpdateReceive(rcv, false);
         HibernateSession.currentSession().flush();

                    com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
         }else{
           throw new Exception("Unabel to enter receive, all items not on asn");
         }




          return (mapping.findForward("success"));
      } catch (Exception ex){
          ex.printStackTrace();
          WarehouseInventory wi = inventoryUtilities.getWarehouseInventoryFromId(recDTO.getWiId());
          List invList = inventoryUtilities.getInventoryList(wi.getId().intValue());
              
          request.setAttribute("wi", wi);
           request.setAttribute("list", invList);
          request.setAttribute("error",ex.getMessage());
          return (mapping.findForward("error"));
      }

}
}




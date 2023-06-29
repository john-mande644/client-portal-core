package com.owd.dc.receiving.actions;

import com.owd.WMSUtils;
import com.owd.dc.checkAuthentication;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.receivingUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.Receive;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.dc.inventorycount.wInventoryUtilities;
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
 * Time: 9:44:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class receiveGetSkuAction extends Action {


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
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvForm.getRcvId()));
             Map info = new TreeMap();
            OwdClient client = (OwdClient)HibernateSession.currentSession().load(OwdClient.class, new Integer(rcv.getClientFkey()));
            info.put("client",client.getCompanyName());
            info.put("desc",rcv.getAsn().getClientRef());
            request.setAttribute("info",info);
            if(""==rcvForm.getInvId()) throw new Exception("No sku entered!!");
            String sku = upcBarcodeUtilities.getSku(rcvForm.getInvId(), rcv.getClientFkey());
            if( sku.equals("NA")){
                throw new Exception("Barcode not assigned to anything, please enter Id number");
            }
            if (sku.equals("multi")){
                throw new Exception("Multiple sku's for barcode found please enter id");
            }
            if(sku.equals(rcvForm.getInvId())){
                System.out.println("barcode not scaned");
                rcvForm.setDoLabelPrint("yes");
            } else{
            rcvForm.setDoLabelPrint("no");
            }
            rcvForm.setInvId(sku);
            //Verify Sku is for current Client doing asn for
             if (wInventoryUtilities.verifyClientSku(rcv.getClientFkey(), sku)==false) {
              throw new Exception("Sku entered is not for current client doing receive for");
             }
            System.out.println("5555555555555");
             //get receive Item id and pass along
           rcvForm.setReceiveItemId(receivingUtilities.getReceiveItemFromInvId(rcvForm.getRcvId(),sku));
           System.out.println("hello");

            request.setAttribute("counted", receivingUtilities.getCountedItem(rcvForm.getReceiveItemId()));
            System.out.println("8884848484");
            invBean ib =  receivingUtilities.loadInventoryBean(sku, WMSUtils.getFacilityFromRequest(request));
            request.setAttribute("ib", ib);
           return (mapping.findForward("success"));
        } catch (Exception ex) {
           ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            
            return mapping.findForward("error");

        }
    }
}

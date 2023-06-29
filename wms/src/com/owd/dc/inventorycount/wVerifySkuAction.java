package com.owd.dc.inventorycount;

import com.owd.dc.checkAuthentication;
import com.owd.dc.actions.OWDAction;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.WInvRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 30, 2005
 * Time: 4:37:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class wVerifySkuAction extends OWDAction {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            if (isCurrentRequest(request)) {
                wCountForm wCountForm = (wCountForm) form;
                wCountDTO wCountDTO = new wCountDTO();
                BeanUtils.copyProperties(wCountDTO, wCountForm);

                //lockup barcode to verify it is the id for jfisher and owd
                System.out.println(wCountDTO.getVerifyInventoryId().length() + " length");
                if (wCountDTO.getVerifyInventoryId().length() > 11) {
                    WInvRequest wi = (WInvRequest) HibernateSession.currentSession().load(WInvRequest.class, new Integer(wCountDTO.getRequestId()));

                        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(wCountDTO.getInventoryId()));
                        if (wCountDTO.getVerifyInventoryId().equals(inv.getUpcCode()) || wCountDTO.getVerifyInventoryId().equals(inv.getIsbnCode()))
                        {

                            wCountDTO.setVerifyInventoryId(wCountDTO.getInventoryId());
                        }

                    

                }
                if (wCountDTO.getInventoryId().equals(wCountDTO.getVerifyInventoryId())) {
                    String id = wInventoryUtilities.insertCount(wCountDTO, (String) request.getAttribute("loginName"));
                    wCountBean wcb = new wCountBean();
                    wcb.setInventoryId(wCountForm.getInventoryId());
                    wcb.setQuanity(wCountForm.getQuanity());
                    wcb.setwCountId(id);
                    request.setAttribute("wcb", wcb);
                    wCountForm.setInventoryId("");
                    wCountForm.setInvLocFkey("");
                    wCountForm.setQuanity("");
                    wCountForm.setVerifyInventoryId("");
                    resetToken(request);
                    return (mapping.findForward("success"));
                } else {
                    saveToken(request);
                    System.out.println("Scanned " + wCountDTO.getVerifyInventoryId() + " looking for " + wCountDTO.getInventoryId());
                    throw new Exception("Sku Scanned does not match sku counted.");
                }
            } else {
                request.setAttribute("error", "Item Already added, please verify qty's");
                return mapping.findForward("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return (mapping.findForward("error"));
        }
    }
}
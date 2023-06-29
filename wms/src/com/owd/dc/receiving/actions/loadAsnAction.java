package com.owd.dc.receiving.actions;
import com.owd.hibernate.generated.ReceiveStatus;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.dc.HandheldUtilities;
import com.owd.dc.inventory.beans.skuDTO;
import com.owd.dc.inventory.forms.skuForm;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.checkAuthentication;

import java.util.Collection;
import java.sql.ResultSet;
/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jul 30, 2005
 * Time: 11:11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class loadAsnAction extends Action {


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
            skuForm skuForm = (skuForm) form;
            skuDTO skuDTO = new skuDTO();
            BeanUtils.copyProperties(skuDTO, skuForm);
            String asnId= skuDTO.getSku();
           try{
        HandheldUtilities.loadAsnFromID(Integer.parseInt(asnId), request);
           }catch(Exception e){
             request.setAttribute("error", e.getMessage());
             request.setAttribute("step", "start");
             return mapping.findForward("success");
           }
        String receiveid = "select id from receivestatus  (NOLOCK) where asn_fkey="+asnId;
        ResultSet rs = HibernateSession.getResultSet(receiveid);
        rs.next();
        System.out.println("setting recerivstatus object");
             ReceiveStatus rcvstatus = (ReceiveStatus) HibernateSession.currentSession().load(ReceiveStatus.class, new Integer(rs.getString(1)));
        Collection items = rcvstatus.getReceiveStatusItems();
             request.setAttribute("step", "selectSku");
             request.setAttribute("items", items);
             request.setAttribute("asnid", asnId);
        request.setAttribute("rcvid", rs.getString(1));
             return mapping.findForward("success");
}


}
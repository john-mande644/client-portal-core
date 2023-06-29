package com.owd.dc.locations.actions;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.inventory.beans.upcDTO;
import com.owd.dc.inventory.forms.upcForm;
import com.owd.dc.inventory.upcBarcodeUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 19, 2005
 * Time: 9:37:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class checkUpcAction extends Action {


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
            upcForm upcForm = (upcForm) form;
            upcDTO upcDTO = new upcDTO();
            BeanUtils.copyProperties(upcDTO, upcForm);
            String barcode = upcDTO.getbarcode();
            String barcodeType = upcBarcodeUtilities.barcodeCheck(barcode);
            if (barcodeType.equals("UPC") == false && barcodeType.equals("ISBN") == false) throw new Exception (barcodeType);
            String[] sqlgetid = new String[2];
            int sq = 0;
            sqlgetid[0] = "select inventory_id  from dbo.owd_inventory  (NOLOCK) where \n" +
                        "upc_code = '" + barcode +"'";
            sqlgetid[1] = "select inventory_id  from dbo.owd_inventory  (NOLOCK) where \n" +
                        "isbn_code = '" + barcode +"'";
            if (barcodeType.equals("UPC"))  sq = 0;
            if (barcodeType.equals("ISBN")) sq = 1;

                    ResultSet rs = HibernateSession.getResultSet(sqlgetid[sq]);
                if (rs.next()){
                    String outcome = barcodeType +" " + barcode + " is assigned to Sku " + rs.getString(1);
                    request.setAttribute("outcome", outcome);
                    return (mapping.findForward("success"));
                } else {
                    request.setAttribute("chkbarcode", barcode);
                    return (mapping.findForward("getsku"));
                }







}  catch (Exception ex){
            request.setAttribute("error", ex.getMessage());
            return (mapping.findForward("error"));
        }finally{
          //  HibernateSession.closeSession();
        }
        
    }
}
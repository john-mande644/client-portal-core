package com.owd.dc.locations.actions;

import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.inventory.beans.locationDTO;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.locations.forms.locationForm;
import com.owd.dc.inventory.upcBarcodeUtilities;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2005
 * Time: 12:58:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class removeBatchAction extends Action {

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

            locationForm locForm = (locationForm) form;
            locationDTO locDTO = new locationDTO();
            BeanUtils.copyProperties(locDTO, locForm);
            int clientFkey =0;
            System.out.println("In removebatch-location");

            if(locDTO.getRemember()>0 == true){
                      System.out.println("in if DTO>0") ;
                      HandheldUtilities.setRememberMulti(locDTO.getRemember()+"",locDTO.getRememberClientName(),request,response);
            }
                    try{
                        clientFkey = Integer.parseInt((String) request.getAttribute("remember"));
                    }catch(Exception e){}
            String sku = upcBarcodeUtilities.getSku(locDTO.getsku(),clientFkey);
            if (sku.equals("Multi")){
                        HashMap multilist =  upcBarcodeUtilities.getMultiBarcodeList(locDTO.getsku(),clientFkey);
                request.setAttribute("skus",multilist);
                request.setAttribute("var","sku");
                request.setAttribute("url", "removebatchlocation");
                request.setAttribute("rememberclient", "true");
                return (mapping.findForward("multi"));
            }
            String loc= new String();
            if(request.getSession(true).getAttribute("zlocation").equals(""))
            {
             loc = locDTO.getlocation();
            }else{
             loc = (String) request.getSession(true).getAttribute("zlocation");
            }
            request.setAttribute("loc", loc);


            //check sku and loc for validity
            if (loc == null) throw new Exception("Location scan invalid");
            if (!(loc.startsWith("/"))) throw new Exception("Location code not valid");
            if (sku == null) {
                request.setAttribute("error", "SKU barcode not valid - please re-enter SKU/item code");

                return (mapping.findForward("success"));
            } else {


                try {

                    //check sku for validity
                    int barcodeInt = Integer.decode(sku).intValue();

                    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
                    if (inv == null) {
                        throw new Exception("Unable to find sku for barcode " + sku);
                    }


                    Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();

                    String sql = null;
                    //set front location if P or only location

                    sql = "delete from owd_inventory_locations where location = ? "
                            + "and inventory_fkey = ?";



                    PreparedStatement stmt = cxn.prepareStatement(sql);


                    stmt.setString(1, loc.trim());
                    stmt.setInt(2, Integer.decode(sku).intValue());

                     System.out.println("delete from owd_inventory_locations where location = "+loc.trim()+"  and inventory_fkey = "+Integer.decode(sku).intValue()+"");
                   System.out.println(request.getAttribute("loginName"));
                    int rowsUpdated = stmt.executeUpdate();


                    cxn.commit();
                    request.setAttribute("sku", sku);
                    inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
                    if (inv.getFrontLocation().equals(loc)) {

                        String locationsql = "select location, assign_date from owd_inventory_locations  (NOLOCK) where inventory_fkey = '" + sku + "' ORDER BY assign_date DESC";
                        String location = null;
                        ResultSet rs = HibernateSession.getResultSet(locationsql);
                        if (rs.next()) {
                            location = rs.getString(1);
                        } else {
                            location = "UNKNOWN";
                        }

                        Connection cxn1 = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
                        sql = "update owd_inventory set front_location = ?"
                                + ", modified_by = 'System' where inventory_id =?";
                        PreparedStatement stmt1 = cxn1.prepareStatement(sql);

                        stmt1.setString(1, location);
                        stmt1.setInt(2, Integer.decode(sku).intValue());

                        int rowsUpdated1 = stmt1.executeUpdate();
                        cxn1.commit();
                        System.out.println("Front location match");
                    }
                    //else set error and return to getsku page

                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "SKU barcode must be numeric - " + sku + " is invalid");

                } catch (Exception ex) {
                    request.setAttribute("error", ex.getMessage());
                } finally {
                  //  HibernateSession.closeSession();
                }
                request.setAttribute("outcome", "Removed " + sku + "from Location " + loc);
                return (mapping.findForward("success"));


            }
        } finally {
           // HibernateTimeForceSession.closeSession();
           // HibernateSession.closeSession();
        }
    }
}




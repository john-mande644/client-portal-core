package com.owd.dc.locations.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.locationDTO;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.locations.forms.locationForm;
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

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2005
 * Time: 12:46:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class removeSingleAction extends Action {

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

            System.out.println("in removesingle-location");
            String sku = locDTO.getsku();
            String loc = locDTO.getlocation();

            if (sku == null) {
                request.setAttribute("error", "SKU barcode not valid - please re-enter SKU/item code");

                return (mapping.findForward("home"));
            } else {

                try {

                    request.setAttribute("sku", sku);

                    //check sku and loc for validity
                    if (loc == null) throw new Exception("Location scan invalid");
                    if (!(loc.startsWith("/"))) throw new Exception("Location code not valid");


                    //if OK update db

                    Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();


                    String sql = "delete from owd_inventory_locations where location = ? "
                            + "and inventory_fkey = ?";

                    PreparedStatement stmt = cxn.prepareStatement(sql);


                    stmt.setString(1, loc.trim());
                    stmt.setInt(2, Integer.decode(sku).intValue());
                    System.out.println("delete from owd_inventory_locations where location = "+loc.trim()+"  and inventory_fkey = "+Integer.decode(sku).intValue()+"");
                    System.out.println(request.getAttribute("loginName"));
                    int rowsUpdated = stmt.executeUpdate();


                    cxn.commit();
                    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
                    //if front location equals loc being removed replace with last scan in history
                    if (inv.getFrontLocation().equals(loc)) {

                        String locationsql = "select location, assign_date from owd_inventory_locations  (NOLOCK) where inventory_fkey = '" + sku + "' ORDER BY assign_date DESC";
                        String location = null;
                        ResultSet rs = HibernateSession.getResultSet(locationsql);
                        if (rs.next() == true) {
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
                    //if update OK return to getsku page

                    //else set error and return to getlocation page
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "SKU barcode must be numeric - " + sku + " is invalid");

                } catch (Exception ex) {
                    request.setAttribute("error", ex.getMessage());
                } finally {
                   // HibernateSession.closeSession();
                }

                if (request.getAttribute("error") != null) {

                    return (mapping.findForward("home"));

                } else {
                    request.setAttribute("outcome", "Removed " + sku + "from Location " + loc);
                    return (mapping.findForward("success"));
                }
            }


        } finally {
          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
        }
    }
}
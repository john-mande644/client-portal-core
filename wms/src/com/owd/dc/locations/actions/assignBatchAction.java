package com.owd.dc.locations.actions;

import com.owd.core.managers.LotManager;
import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.inventory.beans.locationDTO;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.locations.forms.locationForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 11, 2005
 * Time: 10:45:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class assignBatchAction extends Action {
    protected final static Logger log = LogManager.getLogger();


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            locationForm locForm = (locationForm) form;
            locationDTO locDTO = new locationDTO();
            BeanUtils.copyProperties(locDTO, locForm);
            int clientFkey = 0;
            log.debug("In setlocation-getsku");
            if (locDTO.getsku().startsWith("/")) {
                request.setAttribute("loc", locDTO.getsku());
                request.getSession(true).setAttribute("zlocation",locDTO.getsku());
                return (mapping.findForward("success"));
            }
            if (locDTO.getRemember() > 0 == true) {
                //if Remember(Client Fkey) is form is > 0 set the cookie to remember fkey and name
                HandheldUtilities.setRememberMulti(locDTO.getRemember() + "", locDTO.getRememberClientName(), request, response);
            }
            try {
                clientFkey = Integer.parseInt((String) request.getAttribute("remember"));
            } catch (Exception e) {
            }
            String sku = upcBarcodeUtilities.getSku(locDTO.getsku(), clientFkey);

            //If previous returns multi, get info and then forward to multi sku interface
            if (sku.equals("Multi")) {
                HashMap multilist = upcBarcodeUtilities.getMultiBarcodeList(locDTO.getsku(), clientFkey);
                request.setAttribute("skus", multilist);
                request.setAttribute("var", "sku");
                request.setAttribute("url", "assignbatchlocation");
                request.setAttribute("rememberclient", "true");
                return (mapping.findForward("multi"));
            }
            String loc = new String();
         try{
            if (request.getSession(true).getAttribute("zlocation").equals("")||request.getAttribute("remember").equals("")) {
                loc = locDTO.getlocation();
            } else {
                loc = (String) request.getSession(true).getAttribute("zlocation");
            }}catch (Exception e){
             loc = locDTO.getlocation();
         }
            //   String submit = req.getParameter("submit");
            request.setAttribute("loc", loc);
            //  if ("Set New Location".equals(submit)) {

            //     req.getRequestDispatcher("/rfapps/locations/assign/assignstart.jsp").forward(req, resp);
            // } else {

            //check sku and loc for validity
            if (loc == null) throw new Exception("Location scan invalid");
            if (!(loc.startsWith("/"))) throw new Exception("Location code not valid");
            if (sku == null) {
                request.setAttribute("error", "SKU barcode not valid - please re-enter SKU/item code");

                return (mapping.findForward("success"));

            } else {


                try {
                    if(LotManager.isInventoryIdLotControlled(new Integer(sku))) {
                        throw new Exception("You cannot assign Lot Controlled Items in batch mode. Please use Single assign");
                    }
                    boolean setfront = false;
                    //check sku for validity
                    int barcodeInt = Integer.decode(sku).intValue();

                    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
                    if (inv == null) {
                        throw new Exception("Unable to find sku for barcode " + sku);
                    }
                    HandheldUtilities.assignLocation(loc,Integer.decode(sku).intValue(),(String)request.getAttribute("loginName"),"","");
                   /* if (LocationBarcodeUtilities.getDisplayableBarcodeValue(loc).startsWith("P") == true) {
                        setfront = true;
                    } else {
                        String sqllochist = "select location from owd_inventory_locations where inventory_fkey= " + sku + " and  location <> 'UNKNOWN'  and location NOT LIKE '/B%'";
                        ResultSet rs = HibernateSession.getResultSet(sqllochist);
                        if (rs.next() == false) {
                            setfront = true;
                        }
                    }
                    Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();

                    String sql = null;
                    //set front location if P or only location
                    if (setfront) {
                        sql = "update owd_inventory set front_location = ?"
                                + ", modified_by = ? where inventory_id =?";
                    } else {
                        log.debug("setting sql");
                        Date date = cal.getTime();
                        sql = "insert into dbo.owd_inventory_locations ( location, assigned_by, inventory_fkey, assign_date) values ('" + loc.trim() + "','" +
                                request.getAttribute("loginName") + "','" + Integer.decode(sku).intValue() + "','" + sdf.format(date) + "')";
                        //sql = "insert owd_inventory_location set location = ?, assigned_by = ?, inventory_fkey = ?, assign_date = ?";
                    }

                    PreparedStatement stmt = cxn.prepareStatement(sql);
                    if (setfront) {
                        log.debug("settingfront parameters");
                        stmt.setString(1, loc.trim());
                        stmt.setString(2, (String) request.getAttribute("loginName"));
                        stmt.setInt(3, Integer.decode(sku).intValue());
                    }
                    int rowsUpdated = stmt.executeUpdate();

                    	if (rowsUpdated != 1)
                    		throw new Exception("Could not update database - location not assigned");

                    cxn.commit();*/
                    log.debug("right after cxn.commit()");
                    request.setAttribute("outcome", "Assigned " + sku + " to Location " + loc);
                    request.setAttribute("sku", sku);

                    //else set error and return to getsku page

                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "SKU barcode must be numeric - " + sku + " is invalid");

                } catch (Exception ex) {
                    request.setAttribute("error", ex.getMessage());
                } finally {
                   // HibernateSession.closeSession();
                }

                return (mapping.findForward("success"));


            }
        } finally {

          //  HibernateTimeForceSession.closeSession();
          //  HibernateSession.closeSession();
        }
    }
}



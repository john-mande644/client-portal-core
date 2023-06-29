package com.owd.dc.locations.actions;

import com.owd.core.managers.LotManager;
import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.skuBean;
import com.owd.dc.locations.Utilities.locationManagementUtils;
import com.owd.dc.locations.locationInfoBean;
import com.owd.dc.locations.newLocationUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.dc.inventory.beans.locationDTO;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.locations.forms.locationForm;
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
 * Assigns scanned location to the previously scanned item.
 */
public class assignSingleAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        boolean newLoc = false;
        String newLocString="";
        locationInfoBean  lib = null;
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        try {


            try {
                //check login
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            //copy over values from form
            locationForm locForm = (locationForm) form;
            locationDTO locDTO = new locationDTO();
            BeanUtils.copyProperties(locDTO, locForm);
            System.out.println("in assignsingle-location");
            String sku = locDTO.getsku();
            String loc = locDTO.getlocation();
            String notes = locDTO.getNotes();


            if (sku == null) {
                request.setAttribute("error", "SKU barcode not valid - please re-enter SKU/item code");
                return (mapping.findForward("success"));
            } else {

                try {
                    boolean setfront = false;

                    //check sku and loc for validity
                    if (loc == null) throw new Exception("Location scan invalid");
                    //remove support for old locations all locations must have double slash
                    if (!(loc.startsWith("//"))) throw new Exception("Location code not valid");
                    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
                    skuBean skuInfo = new skuBean();
                    skuInfo.setInventoryId(sku);
                    skuInfo.setDescription(inv.getDescription());
                    skuInfo.setInventoryNum(inv.getInventoryNum());
                    request.setAttribute("skuInfo",skuInfo);
                    request.setAttribute("lotValue",locDTO.getLotValue());
                    if(locDTO.getLotValue().length()>0){
                        int validAssign = locationManagementUtils.canWeAssignThisLotValueToThisLocation(locDTO.getLotValue(),Integer.parseInt(sku),loc);
                        lib = new locationInfoBean(loc.replace("//",""),HibernateSession.currentSession());
                        if(validAssign== locationManagementUtils.assignAlready){

                            throw new Exception("This Lot is already assigned to this location: "+lib.getFormatedPickString());
                        }
                        if(validAssign == locationManagementUtils.assignBad) {
                            throw new Exception("You are unable to assign this lot to this location: "+ lib.getFormatedPickString());
                        }
                    }

                        newLoc = true;

                       if (locDTO.getNewParentId()!=null){
                           boolean updategood = false;
                         try{


                           System.out.println("We are going to be adding a new parent id");
                       updategood =    newLocationUtilities.updateParentId(HibernateSession.currentSession(),locDTO.getlocation().replace("//",""), locDTO.getNewParentId().replace("//",""));


                         }catch(Exception e){
                               request.setAttribute("error", e.getMessage());
                         }
                            lib = new locationInfoBean(loc.replace("//",""),HibernateSession.currentSession());
                            request.setAttribute("lib",lib);
                            System.out.println(lib.getName());
                            request.setAttribute("location",loc);


                                               //if OK proceed to next



                             if(updategood){
                                 HibUtils.commit(HibernateSession.currentSession());


                         }   else{
                             return(mapping.findForward("newParent"));
                         }

                       }
                        if (HandheldUtilities.locationAssignedToValidParent(HibernateSession.currentSession(),loc)){


                        }   else{
                           System.out.println("need to get a valid parent ID");
                            lib = new locationInfoBean(loc.replace("//",""),HibernateSession.currentSession());
                            request.setAttribute("lib",lib);
                            System.out.println(lib.getName());
                            request.setAttribute("location",loc);


                                               //if OK proceed to next


                            return(mapping.findForward("newParent"));
                        }

                        lib = new locationInfoBean(loc.replace("//",""),HibernateSession.currentSession());


                    String lotFkey = "";
                    if (locDTO.getLotValue().length()>0){
                        lotFkey = LotManager.getExistingOwdLotValueForString(locDTO.getLotValue(),new Integer(sku)).getId().toString();
                    }

                    HandheldUtilities.assignLocation(loc, Integer.decode(sku).intValue(),(String)request.getAttribute("loginName"),notes,lotFkey);
                  /*  if (LocationBarcodeUtilities.getDisplayableBarcodeValue(loc).startsWith("P") == true) {
                        setfront = true;
                    } else {
                        String sqllochist = "select location from owd_inventory_locations where inventory_fkey= " + sku + " and  location <> 'UNKNOWN'  and location NOT LIKE '/B%'";
                        ResultSet rs = HibernateSession.getResultSet(sqllochist);
                        if (rs.next() == false) {
                            setfront = true;
                        }
                    }

                    //if OK update db

                    Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
                    String sql = null;
                    //set front location if P or only location
                    if (setfront) {
                        sql = "update owd_inventory set front_location = ?"
                                + ", modified_by = ? where inventory_id =?";
                    } else {
                        System.out.println("setting sql");
                        Date date = cal.getTime();
                        sql = "insert into dbo.owd_inventory_locations ( location, assigned_by, inventory_fkey, assign_date) values ('" + loc.trim() + "','" +
                                request.getAttribute("loginName") + "','" + Integer.decode(sku).intValue() + "','" + sdf.format(date) + "')";
                        //sql = "insert owd_inventory_location set location = ?, assigned_by = ?, inventory_fkey = ?, assign_date = ?";
                    }

                    PreparedStatement stmt = cxn.prepareStatement(sql);
                    if (setfront) {
                        System.out.println("settingfront parameters");
                        stmt.setString(1, loc.trim());
                        stmt.setString(2, (String) request.getAttribute("loginName"));
                        stmt.setInt(3, Integer.decode(sku).intValue());
                    }
                    int rowsUpdated = stmt.executeUpdate();

                    	if (rowsUpdated != 1)
                    		throw new Exception("Could not update database - location not assigned");

                    cxn.commit();
                    System.out.println("Right after cxn.commit");*/
                    //if update OK return to getsku page

                    //else set error and return to getlocation page
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "SKU barcode must be numeric - " + sku + " is invalid");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    request.setAttribute("error", ex.getMessage());
                } finally {
                   // HibernateSession.closeSession();
                }

                if (request.getAttribute("error") != null) {
                    OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
                    request.setAttribute("sku", sku);
                    request.setAttribute("desc", inv.getDescription());
                    request.setAttribute("clientsku", inv.getInventoryNum());
                    return (mapping.findForward("home"));
                } else {
                    if(newLoc){
                        request.setAttribute("outcome", "Assigned " + sku + "<br> to Location " + lib.getFormatedPickString() );
                    }  else{
                        request.setAttribute("outcome", "Assigned " + sku + " to Location" + loc);
                    }

                    return (mapping.findForward("success"));
                }
            }
        } finally {

         //   HibernateTimeForceSession.closeSession();
         //   HibernateSession.closeSession();
        }
    }

}
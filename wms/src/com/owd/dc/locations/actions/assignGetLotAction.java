package com.owd.dc.locations.actions;

import com.owd.core.managers.LotManager;
import com.owd.dc.checkAuthentication;
import com.owd.dc.inventory.beans.locationDTO;
import com.owd.dc.inventory.beans.skuBean;
import com.owd.dc.locations.forms.locationForm;
import com.owd.dc.locations.locationInfoBean;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
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
 * Created by danny on 4/22/2016.
 */
public class assignGetLotAction extends Action {




    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        boolean newLoc = false;
        String newLocString="";
        locationInfoBean lib = null;
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
            String lotValue = locDTO.getLotValue();



            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(sku));
            if (inv == null) {
                throw new Exception("Unable to find sku for barcode " + sku);
            }


            skuBean skuInfo = new skuBean();
            skuInfo.setInventoryId(sku);
            skuInfo.setDescription(inv.getDescription());
            skuInfo.setInventoryNum(inv.getInventoryNum());
            request.setAttribute("skuInfo",skuInfo);

            if(LotManager.isLotValueValidForInventoryId(lotValue,new Integer(sku))){
                request.setAttribute("sku",sku);
                request.setAttribute("lotValue",lotValue);


            }  else{
                request.setAttribute("error",lotValue+" is not a valid lot code for "+ skuInfo.getInventoryNum());
                mapping.findForward("error");
            }


        }catch (Exception e) {

        }

           return mapping.findForward("success");

        }
}

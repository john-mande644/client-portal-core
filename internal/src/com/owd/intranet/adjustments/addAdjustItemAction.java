package com.owd.intranet.adjustments;

import com.owd.core.managers.InventoryManager;
import com.owd.core.managers.LotManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.intranet.adjustments.beans.returnItemBean;
import com.owd.intranet.forms.adjustForm;
import com.owd.intranet.util;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 4, 2006
 * Time: 1:54:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class addAdjustItemAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
         //log.debug("in additemAction");
           adjustForm af = (adjustForm) form;
            //log.debug("Current SKU = "+af.getSku());
            Calendar cal = Calendar.getInstance();

         ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
         OwdInventory inv = util.getInvFromSku(HibernateSession.currentSession(),af.getSku(),af.getClientFkey());
          int i = af.getFormItems().length;
            //log.debug("form lenght"+af.getFormItems().length);
            returnItemBean rib = new returnItemBean();
            rib.setCreatedBy(request.getRemoteUser());
            rib.setCreatedDate(cal.getTime().toString());
            rib.setDescription(inv.getDescription());
            rib.setInventoryId(inv.getInventoryId().toString());
            rib.setInventoryNum(inv.getInventoryNum());
            rib.setReasonList();
            rib.setUnusable("no");
            rib.setInvOnHand(""+OrderUtilities.getAvailableInventory(inv.getInventoryId()+"", FacilitiesManager.getFacilityForCode(af.getCurrentLocation()).getId()));
            rib.setItemStatus("New");
            rib.setLotControlled(LotManager.isInventoryIdLotControlled(inv.getInventoryId()));
            if(rib.isLotControlled()){
                rib.setLotList(LotManager.getLotValuesForInventoryId(inv.getInventoryId()));
            }
            returnItemBean[] rbs = af.getFormItems();
            if(i==1){
                returnItemBean r = rbs[0];
                if(r.getInventoryId()==null){
                    //log.debug("Null use first slot");
                    i=0;
                }
            }
            if(i==0){
            rbs[i]=rib;
                af.setFormItems(rbs);
            }else{
             returnItemBean[] nrbs = new returnItemBean[i+1];
                for (int v=0;v<rbs.length;v++){
                    nrbs[v]=rbs[v];
                }
              nrbs[i]=rib;
                af.setFormItems(nrbs);
            }
            for(int j=0;j<af.getFormItems().length;j++){
                //log.debug(af.getFormItems()[j].getInventoryId());
                af.getFormItems()[j].setLotControlled(LotManager.isInventoryIdLotControlled(Integer.parseInt(af.getFormItems()[j].getInventoryId())));
              if(af.getFormItems()[j].isLotControlled()){
                  af.getFormItems()[j].setLotList(LotManager.getLotValuesForInventoryId(Integer.parseInt(af.getFormItems()[j].getInventoryId())));
              }
                //log.debug(af.getFormItems()[j].getChecked()+"    xxxxxxxx 4 spaces inbetween");
                if(!"yes".equals(af.getFormItems()[j].getChecked())){
                  //log.debug("setting null"+af.getFormItems()[j].getInventoryId());
                    af.getFormItems()[j].setChecked(null);
                }
            }
          request.setAttribute("outcome","Added "+af.getSku());
            af.setSku("");

            return (mapping.findForward("success"));
        } catch (Exception e) {
            request.setAttribute("errors",e.getMessage());
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

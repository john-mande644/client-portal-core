package com.owd.intranet.adjustments.returns;

import com.owd.core.managers.LotManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.intranet.forms.returnForm;
import com.owd.intranet.util;
import com.owd.intranet.adjustments.beans.returnItemBean;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.HibernateSession;
import org.hibernate.engine.spi.SessionImplementor;

import java.util.Calendar;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 4, 2006
 * Time: 1:54:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class addItemAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
         //log.debug("in additemAction");

         ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
           returnForm rf = (returnForm) form;
            Calendar cal = Calendar.getInstance();
         OwdInventory inv = util.getInvFromSku(HibernateSession.currentSession(),rf.getSku(),rf.getClientFkey());


            int i = rf.getFormItems().length;
            //log.debug("form lenght"+rf.getFormItems().length);
            returnItemBean rib = new returnItemBean();
            rib.setCreatedBy(request.getRemoteUser());
            rib.setCreatedDate(cal.getTime().toString());
            rib.setDescription(inv.getDescription());
            rib.setInventoryId(inv.getInventoryId().toString());
            rib.setInventoryNum(inv.getInventoryNum());
            rib.setReasonList();
            rib.setUnusable("no");
            rib.setInvOnHand(""+ OrderUtilities.getAvailableInventory(inv.getInventoryId() + "", FacilitiesManager.getFacilityForCode(rf.getCurrentLocation()).getId()));
            rib.setItemStatus("Returned");
            rib.setLotControlled(LotManager.isInventoryIdLotControlled(inv.getInventoryId()));
            log.debug("This is our lot Controlled for this item: "+ rib.isLotControlled());
            if(rib.isLotControlled()){
                rib.setLotList(LotManager.getLotValuesForInventoryId(inv.getInventoryId()));
                log.debug(rib.getLotList().size());
            }

            if(inv.getRequireSerialNumbers()==1)
         {
             rib.setSerialRequired(true);
         }
            
            returnItemBean[] rbs = rf.getFormItems();
            if(i==1){
                returnItemBean r = rbs[0];
                if(r.getInventoryId()==null){
                    //log.debug("Null use first slot");
                    i=0;
                }
            }
            if(i==0){
            rbs[i]=rib;
                rf.setFormItems(rbs);
            }else{
             returnItemBean[] nrbs = new returnItemBean[i+1];
                for (int v=0;v<rbs.length;v++){
                    nrbs[v]=rbs[v];
                }
              nrbs[i]=rib;
                rf.setFormItems(nrbs);
            }
            for(int j=0;j<rf.getFormItems().length;j++){
                //log.debug(rf.getFormItems()[j].getInventoryId());
                //log.debug(rf.getFormItems()[j].getChecked()+"    xxxxxxxx 4 spaces inbetween");
                rf.getFormItems()[j].setLotControlled(LotManager.isInventoryIdLotControlled(Integer.parseInt(rf.getFormItems()[j].getInventoryId())));
                if(rf.getFormItems()[j].isLotControlled()){
                    rf.getFormItems()[j].setLotList(LotManager.getLotValuesForInventoryId(Integer.parseInt(rf.getFormItems()[j].getInventoryId())));
                }
                if(!"yes".equals(rf.getFormItems()[j].getChecked())){
                  //log.debug("setting null"+rf.getFormItems()[j].getInventoryId());
                    rf.getFormItems()[j].setChecked(null);
                }
            }
          request.setAttribute("outcome","Added "+rf.getSku());
            rf.setSku("");

            return (mapping.findForward("success"));
        } catch (Exception e) {
            request.setAttribute("errors",e.getMessage());
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

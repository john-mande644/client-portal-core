package com.owd.intranet.adjustments;

import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.LotManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.intranet.adjustments.beans.lotCountItemBean;
import com.owd.intranet.forms.lotCountForm;
import com.owd.intranet.util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 2, 2006
 * Time: 3:45:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class beginLotCountAction extends DispatchAction {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
             String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
           Calendar cal = Calendar.getInstance();
           lotCountForm af = (lotCountForm) form;
            af.setCreatedBy(request.getRemoteUser());
            af.setCreatedDate(cal.getTime().toString());


            ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            OwdInventory inv = null;
            log.debug("testing sku "+af.getSku());
            try {
                inv = util.getInvFromSku(HibernateSession.currentSession(),af.getSku(),af.getClientFkey());
            }  catch(Exception ex1) {
                log.debug("caught bad SKU exception");
                form.reset(mapping, request);

                request.setAttribute("errors",ex1.getMessage());
                ex1.printStackTrace();
                return mapping.findForward("success");

            }
            if(inv.getLotTrackingRequired()==0) {
                String tsku = af.getSku();
                form.reset(mapping, request);

                throw new Exception("SKU "+tsku+" is not tracked by lot. Verify your SKU value and lot tracking status and try again.");
            }
            int i = af.getFormItems().length;

            //log.debug("form lenght"+af.getFormItems().length);
            lotCountItemBean rib = new lotCountItemBean();
            af.setDescription(inv.getDescription());
            af.setInventoryId(inv.getInventoryId().toString());
            af.setSku(inv.getInventoryNum());
            log.debug("Current SKU = "+af.getSku());
            log.debug("Current desc = "+af.getDescription());

            Map<String,Integer> valueMap = LotManager.getLotValuesAndQuantitiesForInventoryId(Integer.parseInt(af.getInventoryId()), FacilitiesManager.getFacilityForCode(af.getCurrentLocation()).getId());
            lotCountItemBean[] rbs = new lotCountItemBean[valueMap.keySet().size()];
            log.debug("got lot value list size="+valueMap.keySet().size());
            int ib=0;
            for (String key:valueMap.keySet()) {
                lotCountItemBean lb = new lotCountItemBean();
                lb.setLotValue(key);
                lb.setLotQty(valueMap.get(key));
                lb.setNewQty(valueMap.get(key));
                rbs[ib] = lb;
                ib++;
            }

            af.setFormItems(rbs);


            request.setAttribute("outcome","Loaded "+af.getSku());

            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors",e.getMessage());

            return mapping.findForward("error");

        }
    }
}

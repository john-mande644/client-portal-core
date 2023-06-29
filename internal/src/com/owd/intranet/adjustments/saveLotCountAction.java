package com.owd.intranet.adjustments;

import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.core.managers.LotManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.intranet.adjustments.beans.lotCountItemBean;
import com.owd.intranet.forms.adjustForm;
import com.owd.intranet.forms.lotCountForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.actions.DispatchAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 2:19:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class saveLotCountAction extends DispatchAction {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            lotCountForm af = (lotCountForm) form;
            log.debug(af.toString());
            String submitValue = request.getParameter("submit");
            log.debug("submitValue:"+submitValue);
             if("addLot".equals(submitValue)) {
                 log.debug("adding new lot value "+af.getNewLotValue());
                 if(af.getNewLotValue().trim().length()>0) {
                     int lotid = LotManager.getLotIdForValue(af.getNewLotValue(), Integer.parseInt(af.getInventoryId()));
                     HibUtils.commit(HibernateSession.currentSession());
                     updateLotValueArray(af);
                 }
                 af.setNewLotValue("");
                 return mapping.findForward("error");
             }



            if("".equals(af.getSku())|| null==af.getSku()){

                form = new lotCountForm();
                indexLotCountAction e = new indexLotCountAction();

               e.execute(mapping,form,request,response);
              return mapping.findForward("error");

            }
            if(InventoryManager.countInProgress(HibernateSession.currentSession(), Integer.parseInt(af.getClientFkey()))){
            throw new Exception("Inventory Count in progress, you cannot adjust inventory");
        }
        af =  lotCountUtil.saveLotCount(af, HibernateSession.currentSession());
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors",e.getMessage());
            return mapping.findForward("error");

        }
    }

   static void updateLotValueArray(lotCountForm af) throws Exception {

       log.debug("Current SKU = "+af.getSku());

       Map<String,Integer> valueMap = LotManager.getLotValuesAndQuantitiesForInventoryId(Integer.parseInt(af.getInventoryId()), FacilitiesManager.getFacilityForCode(af.getCurrentLocation()).getId());
       lotCountItemBean[] rbs = new lotCountItemBean[valueMap.keySet().size()];
       log.debug("got lot value list size="+valueMap.keySet().size());
       int ib=0;
       for (String key:valueMap.keySet()) {

           lotCountItemBean lb = getLotCountItemforLotValue(af,key);
           if(lb==null) {
               lb = new lotCountItemBean();

               lb.setLotValue(key);
               lb.setLotQty(valueMap.get(key));
           }
           rbs[ib] = lb;
           ib++;
       }

       af.setFormItems(rbs);
   }

    static lotCountItemBean getLotCountItemforLotValue(lotCountForm af,String lotValue)
    {
        for(int i=0;i<af.getFormItems().length;i++) {
            if(af.getFormItems()[i] != null) {
                if (lotValue.equalsIgnoreCase(af.getFormItems()[i].getLotValue())) {
                    return af.getFormItems()[i];
                }
            }
        }

        return null;
    }
}

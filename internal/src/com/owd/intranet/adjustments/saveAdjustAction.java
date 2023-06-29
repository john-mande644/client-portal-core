package com.owd.intranet.adjustments;

import com.owd.core.managers.LotManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.intranet.forms.adjustForm;
import com.owd.hibernate.HibernateSession;
import com.owd.core.managers.InventoryManager;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 2:19:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class saveAdjustAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {

           adjustForm af = (adjustForm) form;
            if(!"".equals(af.getSku())||null==af.getSku()){

              addAdjustItemAction e = new addAdjustItemAction();

               e.execute(mapping,form,request,response);
              return mapping.findForward("error");

            }
            if(InventoryManager.countInProgress(HibernateSession.currentSession(), Integer.parseInt(af.getClientFkey()))){
            throw new Exception("Inventory Count in progress, you cannot adjust inventory");
        }
            try {
                af = adjustUtil.saveAdjust(af, HibernateSession.currentSession());

                return (mapping.findForward("success"));
            }catch(Exception e){
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
                request.setAttribute("errors",e.getMessage());
            }

            return mapping.findForward("error");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors",e.getMessage());
            return mapping.findForward("error");

        }
    }


}

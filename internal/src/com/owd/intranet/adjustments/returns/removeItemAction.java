package com.owd.intranet.adjustments.returns;

import com.owd.core.managers.LotManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.intranet.forms.returnForm;
import com.owd.intranet.adjustments.beans.returnItemBean;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 7, 2006
 * Time: 11:14:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class removeItemAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
         //log.debug("in removeitemAction");
           returnForm rf = (returnForm) form;

         returnItemBean rb[] = rf.getFormItems();
        List items = new ArrayList();
            for(int i=0; i<rb.length;i++){
                //log.debug("\r\n checked" + rb[i].getChecked());
              if(rf.getSku().equals(rb[i].getInventoryId())){
                  //log.debug("Removeing" +rb[i].getInventoryId());
              }else{
                items.add(rb[i]);
              }


          }
          returnItemBean newrb[] = new returnItemBean[items.size()];
          for(int j=0;j<items.size();j++){
               newrb[j]=(returnItemBean) items.get(j);
          }


            rf.setSku("");
          rf.setFormItems(newrb);
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
            return (mapping.findForward("success"));
        } catch (Exception e) {
            request.setAttribute("errors",e.getMessage());
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

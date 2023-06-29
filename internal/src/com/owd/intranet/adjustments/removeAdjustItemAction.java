package com.owd.intranet.adjustments;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.intranet.forms.adjustForm;
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
public class removeAdjustItemAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
         //log.debug("in removeitemAction");
          adjustForm af = (adjustForm) form;

         returnItemBean rb[] = af.getFormItems();
        List items = new ArrayList();
            for(int i=0; i<rb.length;i++){
                //log.debug("\r\n checked" + rb[i].getChecked());
              if(af.getSku().equals(rb[i].getInventoryId())){
                  //log.debug("Removing" +rb[i].getInventoryId());
              }else{
                items.add(rb[i]);
              }


          }
          returnItemBean newrb[] = new returnItemBean[items.size()];
          for(int j=0;j<items.size();j++){
               newrb[j]=(returnItemBean) items.get(j);
          }
            af.setSku("");
          af.setFormItems(newrb);
            return (mapping.findForward("success"));
        } catch (Exception e) {
            request.setAttribute("errors",e.getMessage());
            e.printStackTrace();
            return mapping.findForward("error");

        }
    }
}

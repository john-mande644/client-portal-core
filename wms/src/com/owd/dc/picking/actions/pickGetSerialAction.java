package com.owd.dc.picking.actions;

import com.owd.dc.checkAuthentication;
import com.owd.dc.picking.forms.serialForm;
import com.owd.core.managers.SerialNumberManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OrderPickItem;
import com.owd.hibernate.generated.OwdInventory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 20, 2006
 * Time: 4:01:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickGetSerialAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }

            serialForm sfForm = (serialForm) form;
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(sfForm.getInvId()));
            //Make sure serial exists for item
            OrderPickItem curritem = null;
            if (SerialNumberManager.serialExists(inv, sfForm.getSerial())) {
                curritem = (OrderPickItem) HibernateSession.currentSession().load(OrderPickItem.class, Integer.valueOf(sfForm.getpItemId()));
                //If first serial just fill it
                if (curritem.getSerials() == null) {
                    System.out.println("null");
                    curritem.setSerials(sfForm.getSerial() + ",");
                } else {
                    //else append to list
                    System.out.println("Not so null");
                    String[] test = curritem.getSerials().split(",");
                    for (int i = 0; i < test.length; i++) {
                        //check if serial already scanned
                        if (test[i].equals(sfForm.getSerial())) {
                            throw new Exception("Serial Already Found  " + sfForm.getSerial());
                        }
                    }
                    curritem.setSerials(curritem.getSerials() + sfForm.getSerial() + ",");
                }


            } else {
                throw new Exception("Serial " + sfForm.getSerial() + " cannot be found for Current Sku");
            }
            HibernateSession.currentSession().saveOrUpdate(curritem);
            HibernateSession.currentSession().flush();
            com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.currentSession().refresh(curritem);

            sfForm.setCurrentNum((Integer.parseInt(sfForm.getCurrentNum()) + 1) + "");
            if (sfForm.getCurrentNum().equals(sfForm.getTotal())) {
                System.out.println("All done with Serials");
                return mapping.findForward("pickall");
            }
            request.setAttribute("outcome", "Added serial # " + sfForm.getSerial());
            sfForm.setSerial("");
            return (mapping.findForward("success"));
        } catch (Exception e) {
            //   OrderPickStatus pstat = (OrderPickStatus) HibernateSession.currentSession().load(OrderPickStatus.class, new Integer(pickstat));
            //  pickItemBean pickItem = PickUtilities.loadPickItemBean(pstat);
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());


            return (mapping.findForward("error"));

        }
    }
}

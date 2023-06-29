package com.owd.extranet.order.actions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderStatus;
import com.owd.extranet.servlet.ExtranetServlet;
import com.owd.extranet.servlet.OrdersManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderCharg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 1, 2007
 * Time: 2:26:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditBillingAction {
private final static Logger log =  LogManager.getLogger();
    public static void action(HttpServletRequest req, HttpServletResponse resp) {
        String error = null;
        try {

            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, OrdersManager.kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(OrdersManager.kCurrentOrder,
                    status);


            try {
                int svcGuarantee = ExtranetServlet.getIntegerParam(req, "owdbillServiceGuarantee", 0);
                int shipGuarantee = ExtranetServlet.getIntegerParam(req, "owdbillShippingGuarantee", 0);
                String reasonGuarantee = ExtranetServlet.getStringParam(req, "owdGuaranteeReason", "");
                String notesGuarantee = ExtranetServlet.getStringParam(req, "owdGuaranteeNotes", "");


                if (ExtranetServlet.getIntegerParam(req, "saveGuarantee", 0) == 0) {
                    List charges = HibernateSession.currentSession().createQuery("from OwdOrderCharg where orderFkey=? and owdGuaranteeFlag=1").setInteger(0, new Integer(status.order_id)).list();
                    if (charges.size() > 1) {
                        //too many - alert
                        throw new Exception("Multiple order charge records exist when there should be only one - contact IT for help");
                    } else if (charges.size() == 1) {
                        OwdOrderCharg charge = (OwdOrderCharg) charges.get(0);
                        log.debug("ChargeDate:" + charge.getChargeDate());
                        req.setAttribute("owdbillServiceGuarantee", "" + (charge.getBoxes()));
                        req.setAttribute("owdbillShippingGuarantee", "" + (charge.getShipActualCost().intValue()));
                        req.setAttribute("owdGuaranteeReason", charge.getChangeOrderNotes());

                        req.setAttribute("owdGuaranteeNotes", charge.getExplanation() == null ? "" : charge.getExplanation());
                        req.setAttribute("owdGuaranteeEditable", "" + (charge.getChargeDate() == null ? 1 : 0));

                    } else {
                        //no existing guarantees
                        req.setAttribute("owdbillServiceGuarantee", "0");
                        req.setAttribute("owdbillShippingGuarantee", "0");
                        req.setAttribute("owdGuaranteeReason", "");
                        req.setAttribute("owdGuaranteeNotes", "");
                        req.setAttribute("owdGuaranteeEditable", "" + 1);
                    }


                } else {
                    //save new data
                    List charges =  HibernateSession.currentSession().createQuery("from OwdOrderCharg where orderFkey=? and owdGuaranteeFlag=1").setInteger(0,new Integer(status.order_id)).list();
                                    if(charges.size()>1)
                                    {
                                       //too many - alert
                                        throw new Exception("Multiple order charge records exist when there should be only one - contact IT for help");
                                    }   else  if(charges.size()==1)
                                    {


                                        OwdOrderCharg charge = (OwdOrderCharg) charges.get(0);

                                        if(charge.getChargeDate()!=null)
                                        {
                                            throw new Exception("Guarantee already applied to com.owd.api.client account - this record cannot be edited.");
                                        }
                                         if((svcGuarantee+shipGuarantee)>0)
                                        {
                                        charge.setBoxes((short)svcGuarantee);
                                        charge.setShipActualCost(new BigDecimal(shipGuarantee));
                                        charge.setChangeOrderNotes(reasonGuarantee);
                                        charge.setExplanation(notesGuarantee);
                                        HibernateSession.currentSession().update(charge);
                                        
                                        req.setAttribute("owdbillServiceGuarantee",""+(charge.getBoxes()));
                                        req.setAttribute("owdbillShippingGuarantee",""+(charge.getShipActualCost().intValue()));
                                        req.setAttribute("owdGuaranteeReason",charge.getChangeOrderNotes());

                                        req.setAttribute("owdGuaranteeNotes",charge.getExplanation()==null?"":charge.getExplanation());

  req.setAttribute("owdGuaranteeEditable",""+(charge.getChargeDate()==null?1:0));                                        }else
                                         {

                                        HibernateSession.currentSession().delete(charge);
                                                  req.setAttribute("owdbillServiceGuarantee",""+0);
                                        req.setAttribute("owdbillShippingGuarantee",""+0);
                                        req.setAttribute("owdGuaranteeReason","");
                                        req.setAttribute("owdGuaranteeNotes","");

                req.setAttribute("owdGuaranteeEditable",""+1);

                                         }
                                    }   else
                                    {
                                        //no existing guarantees
                                        if((svcGuarantee+shipGuarantee)>0)
                                        {
                                           OwdOrderCharg charge = new OwdOrderCharg();
                    charge.setBoxes((short)svcGuarantee);
                    charge.setGiftWrap((long)0);
                    charge.setImported((short)0);
                    charge.setInsuranceForm((short)0);
                    charge.setInternational((short)0);
                    charge.setInvoiceQty((short)0);
                    charge.setIsVoid((short)0);
                    charge.setManualEntry((short)0);
                    charge.setOriginalFlag((byte)0);
                    charge.setOwdGuaranteeFlag((byte)(svcGuarantee==1?1:shipGuarantee));
                    charge.setPackSlipsQty((short)0);
                    charge.setPickSlipsQty((short)0);
                    charge.setPicksQty((short)0);
                    charge.setPacksQty((short)0);
                    charge.setReturns((short)0);
                    charge.setShipActualCost(new BigDecimal(shipGuarantee));
                    charge.setShipBillCost(new BigDecimal(0.00));
                    charge.setChangeOrderFlag((byte)0);
                    charge.setChangeOrderNotes(reasonGuarantee);
                    charge.setChangeOrderType("Extranet");
                    charge.setChargeBy(req.getUserPrincipal().getName());
                    charge.setChargeDate(null);
                    charge.setClientChargeFlag((byte)0);
                    charge.setExplanation(notesGuarantee);
                    charge.setOrderFkey(new Integer(status.order_id));
                    HibernateSession.currentSession().save(charge);
                    
                                         req.setAttribute("owdbillServiceGuarantee",""+(charge.getBoxes()));
                                        req.setAttribute("owdbillShippingGuarantee",""+(charge.getShipActualCost().intValue()));
                                        req.setAttribute("owdGuaranteeReason",charge.getChangeOrderNotes());
                                        req.setAttribute("owdGuaranteeNotes",charge.getExplanation()==null?"":charge.getExplanation());

  req.setAttribute("owdGuaranteeEditable",""+(charge.getChargeDate()==null?1:0));                                        }else
                                        {
                                                  req.setAttribute("owdbillServiceGuarantee",""+0);
                                        req.setAttribute("owdbillShippingGuarantee",""+0);
                                        req.setAttribute("owdGuaranteeReason","");
                                        req.setAttribute("owdGuaranteeNotes","");

                req.setAttribute("owdGuaranteeEditable",""+1);
                                        }
                                    }



                }
                HibUtils.commit(HibernateSession.currentSession());
                req.setAttribute(OrdersManager.kCurrentOrder, status);

                req.getRequestDispatcher("editsvcbilling.jsp").include(req, resp);

            } catch (Exception ex) {

                ex.printStackTrace();
                HibUtils.rollback(HibernateSession.currentSession());
                error = "Error processing order - please report to casetracker@owd.com";
                req.setAttribute("formerror", error);
                try {
                    req.getSession(true).removeAttribute(OrdersManager.kCurrentOrder);
                } catch (Throwable th) {
                }

                req.getRequestDispatcher("editorder.jsp").include(req, resp);
            } finally {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

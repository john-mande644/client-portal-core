package com.owd.extranet.servlet;

import com.owd.core.TagUtilities;
import com.owd.core.business.order.downloadUtility.ExcelDownload;
import com.owd.hibernate.generated.OrderShipInfo2;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.*;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.JasperReportPrintManager;
import com.owd.core.managers.ScanManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.extranet.order.actions.*;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderCharg;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class OrdersManager extends ExtranetManager {
private final static Logger log =  LogManager.getLogger();

    public static final String kParamOrderMgrAction = "ordermgr";

    public static final String kParamOrderExploreAction = "explore";

    public static final String kParamOrderCreateAction = "new";
    public static final String kParamOrderEditAction = "edit";
    public static final String kParamOrderFindAction = "find";
    public static final String kParamOrderFindAdvancedAction = "findadv";

    public static final String kParamOrderEditBillingAction = "billedit";
    public static final String kParamOrderEditShippingAction = "shipedit";
    public static final String kParamOrderVoidAction = "ordervoid";
    public static final String kParamOrderShipAction = "ordership";
    public static final String kParamOrderRemoveHoldAction = "ordernohold";
    public static final String kParamOrderSetHoldAction = "orderhold";
    public static final String kParamOrderDoCCAction = "kParamOrderDoCCAction";
    public static final String kParamOrderVoidCCAction = "kParamOrderVoidCCAction";
    public static final String kParamOrderVoidPackageAction = "voidpackage";
    public static final String kParamOrderUnpostAction = "unpostorder";
    public static final String kParamOrderRemoveItemAction = "removeItem";
    public static final String kParamOrderAddItemAction = "addItem";
    public static final String kParamOrderChangeLocationAction = "setLocation";

    public static final String kParamOrderUpdateTotalAction = "updateTotal";

    public static final String kParamOrderAddCommentAction = "addcomment";

    public static final String kParamOrderShipMethodAction = "ordershipmethod";
    public static final String kParamOrderEditSvcBillingAction = "editbilling";
    public static final String kParamDownloadExcelAction = "downloadExcel";

    public static final String kParamOrderQuickFind = "qf";

    public static final String kparamOrderID = "oid";
    public static final String kparamTransactionID = "tid";
    public static final String kparamShipmentID = "pid";
    public static final String kCurrentFinder = "currOrderFinder";
    public static final String kCurrentOrder = "currOrderStatus";

    String[] tableColumnNames = {"ID", "Customer", "Reference", "Released", "Void"};
    String[] tableColumnDefs = {"order_id", "bill_first_name+\" \"+bill_last_name", "order_refnum", "ISNULL(CONVERT(varchar,post_date,107),\"<B>Hold</B>\")", "CASE WHEN is_void = 1 THEN \'Yes\' ELSE \'No\' END"};
    String tableFromStmt = "from owd_order i ";
    String[] tableLinkStarts = {"", "", "", "", ""};
    String[] tableLinkEnds = {"", "", "", "", ""};
    int[] tableLinkMids = {0, 0, 0, 0, 0};


    public String getManagerMenus(HttpServletRequest req) {
        StringBuffer sb = new StringBuffer();
        sb.append("<UL><LI><A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamOrderMgrAction + "=" + kParamOrderExploreAction + "\" >");
        sb.append("Explore Orders");
        sb.append("<LI><A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamOrderMgrAction + "=" + kParamOrderExploreAction + "\" >");
        sb.append("New Order</UL> ");

        return sb.toString();
    }


    public String getCurrentAction(HttpServletRequest req) {
        String currAction = ExtranetServlet.getStringParam(req, kParamOrderMgrAction, kParamOrderExploreAction);
        if (currAction.equals(kParamOrderExploreAction)) {
            return "Searching Orders...";
        } else if (currAction.equals(kParamOrderCreateAction)) {
            return "Creating New Order";
        } else if (currAction.equals(kParamOrderEditAction)) {
            return "Editing Order";
        } else if (currAction.equals(kParamOrderShipMethodAction)) {
            return "Editing Order";
        } else if (currAction.equals(kParamOrderFindAction)) {
            return "Orders Search Results";
        }

        return "";

    }


    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //do real work here
        String currAction = ExtranetServlet.getStringParam(req, kParamOrderMgrAction, kParamOrderExploreAction);


        if (currAction.equals(kParamOrderExploreAction)) {
            defaultViewAction(req, resp);
        } else if (currAction.equals("calltag-getinfo")) {
            GetCallTagInfoAction.action(req, resp);
        } else if (currAction.equals("calltag-request")) {
            ShipCallTagAction.action(req, resp);
        } else if (currAction.equals("clone-new")) {
            CloneToNewOrderAction.action(req, resp);
        }else if (currAction.equals("get-scan")) {
            getScanAction(req, resp);
        } else if (currAction.equals("get-comminvoice")) {
            getCommercialInvoiceAction(req, resp);
        //=========== Sean Created on 4/5/2019 =================

        } else if (currAction.equals("downloadExcel")) {
            downloadExcelAction(req, resp);

        //======================================================
        } else if (currAction.equals(kParamOrderFindAction)) {
            showTable(req, resp, null, null);
        } else if (currAction.equals(kParamOrderAddCommentAction)) {
            addCommentAction(req, resp);

        } else if (currAction.equals(kParamOrderChangeLocationAction)) {
            changeLocationAction(req, resp);

        }else if (currAction.equals(kParamOrderVoidPackageAction)) {
            voidPackageAction(req, resp);


        } else if (currAction.equals(kParamOrderRemoveItemAction)) {
            log.debug("in remove item action");
            RemoveItemAction.action(req, resp);

        } else if (currAction.equals(kParamOrderUpdateTotalAction)) {
            log.debug("in update totals action");
            UpdateTotalsAction.action(req, resp);

        } else if (currAction.equals(kParamOrderEditSvcBillingAction)) {
            log.debug("in edit billing action");
            EditBillingAction.action(req, resp);
        } else if (currAction.equals(kParamOrderAddItemAction)) {
            log.debug("in add item action");
            AddItemAction.action(req, resp);
        } else if (currAction.equals(kParamOrderShipMethodAction)) {
            shipExistingOrderAction(req, resp);
        } else if (currAction.equals(kParamOrderCreateAction)) {
            createOrderAction(req, resp);

        } else if (currAction.equals(kParamOrderEditAction)) {
            viewOrderaction(req, resp);

        } else if (currAction.equals(kParamOrderEditBillingAction)) {
            editOrderBillingAction(req, resp);

        } else if (currAction.equals(kParamOrderDoCCAction)) {
            runPaymentTransactionAction(req, resp);
        } else if (currAction.equals(kParamOrderVoidCCAction)) {
            voidPaymentTransactionAction(req, resp);

        } else if (currAction.equals(kParamOrderEditShippingAction)) {
            editOrderShippingAction(req, resp);
        } else if (currAction.equals(kParamOrderVoidAction)) {
            voidOrderAction(req, resp);

        } else if (currAction.equals(kParamOrderUnpostAction)) {
            unpostOrderAction(req, resp);

        } else if (currAction.equals(kParamOrderShipAction)) {
            ReleaseOrderAction.action(req, resp);

        } else if (currAction.equals(kParamOrderRemoveHoldAction)) {

            removeOrderHoldAction(req, resp);

        } else if (currAction.equals(kParamOrderSetHoldAction)) {
            SetOnHoldAction.action(req, resp);


        }


    }



    private void removeOrderHoldAction(HttpServletRequest req, HttpServletResponse resp) {
        String error = null;
        try {

            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(kCurrentOrder,
                    status);
            Connection cxn = null;
            Statement stmt = null;

            try {
                cxn = ConnectionManager.getConnection();

                String esql = "update owd_order set  is_future_ship = 0, is_backorder=1, backorder_order_num = \"\" where order_id = " + status.order_id;
                stmt = cxn.createStatement();

                int rowsUpdated = stmt.executeUpdate(esql);

                if (rowsUpdated < 1)
                    throw new Exception("Order not updated; could not release hold");

                cxn.commit();
                Event.addOrderEvent(new Integer(status.order_id).intValue(), Event.kEventTypeHandling, "Order hold removed by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

                req.setAttribute(kCurrentOrder,
                        new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));


                req.getRequestDispatcher("editorder.jsp").include(req, resp);

            } catch (Exception ex) {
                cxn.rollback();
                ex.printStackTrace();
                error = "Error processing order - please report to casetracker@owd.com<BR>Error: " + ex.getMessage();
                req.setAttribute("formerror", error);
                req.getRequestDispatcher("editorder.jsp").include(req, resp);
            } finally {
                try {
                    stmt.close();
                } catch (Exception ex) {
                }
                try {
                    cxn.close();
                } catch (Exception ex) {
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void unpostOrderAction(HttpServletRequest req, HttpServletResponse resp) {
        try {

            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(kCurrentOrder,
                    status);

            try {

                unpostOrderFromStatus(req.getUserPrincipal().getName(), status);


                req.setAttribute(kCurrentOrder,
                        new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
            } catch (Throwable th) {
                Exception ex;

                if (th instanceof Exception) {
                    ex = (Exception) th;
                } else {
                    ex = new Exception(th.toString());
                }
                OWDUtilities.debugApp(ex);
                ex.printStackTrace();
                req.setAttribute("formerror", ex.getMessage());
            }
            finally {
            }
            req.getRequestDispatcher("editorder.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void unpostOrderFromStatus(String user, OrderStatus status) throws Exception {
        if(status.is_shipped)
        {
            throw new Exception("Order is already shipped; cannot unpost this order.");
        }

        if (!status.is_unpicked)
        {
            status.unpickOrder();
            Event.addOrderEvent(new Integer(status.order_id), Event.kEventTypeHandling, "Pick cleared as part of unposting process by " + user, user);

            OwdOrderCharg charge = new OwdOrderCharg();
            charge.setBoxes((short) 0);
            charge.setGiftWrap((long) 0);
            charge.setImported((short) 0);
            charge.setInsuranceForm((short) 0);
            charge.setInternational((short) 0);
            charge.setInvoiceQty((short) 0);
            charge.setIsVoid((short) 0);
            charge.setManualEntry((short) 0);
            charge.setOriginalFlag((byte) 0);
            charge.setOwdGuaranteeFlag((byte) 0);
            charge.setPackSlipsQty((short) 0);
            charge.setPickSlipsQty((short) 0);
            charge.setPicksQty((short) 0);
            charge.setPacksQty((short) 0);
            charge.setReturns((short) 0);
            charge.setShipActualCost(new BigDecimal(0.00));
            charge.setShipBillCost(new BigDecimal(0.00));
            charge.setChangeOrderFlag((byte) 1);
            charge.setChangeOrderNotes("Extranet unposting");
            charge.setChangeOrderType("Extranet");
            charge.setChargeBy(user);
            charge.setChargeDate(Calendar.getInstance().getTime());
            charge.setClientChargeFlag((byte) 1);
            charge.setExplanation("Extranet unposting after pick");
            charge.setOrderFkey(new Integer(status.order_id));
            HibernateSession.currentSession().save(charge);
            HibUtils.commit(HibernateSession.currentSession());

            Event.addOrderEvent(new Integer(status.order_id), Event.kEventTypeHandling, "Order Change fee incurred due to unposting order after picking by " + user, user);

        } else {
            // throw new Exception("Order is not currently in posted status; cannot unpost this order.");
        }


        if (status.is_posted) {
            status.unpostOrder();
            Event.addOrderEvent(new Integer(status.order_id), Event.kEventTypeHandling, "Order unposted after posting by " + user, user);
        } else {
            throw new Exception("Order is not currently in posted status; cannot unpost this order.");
        }
    }

    private void voidOrderAction(HttpServletRequest req, HttpServletResponse resp) {
        try {

            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(kCurrentOrder,
                    status);


            try {

                 if(status.is_shipped)
                {
                    throw new Exception("Order is already shipped; cannot void this order.");
                }
                if (status.is_posted) {
                    throw new Exception("Orders cannot be voided when they are released to the warehouse (posted). Unpost the order and put it on hold before voiding.");
                    //Event.addOrderEvent(new Integer(status.order_id).intValue(), Event.kEventTypeHandling, "Order voided after posting by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());
                }

                status.voidOrder();
                Event.addOrderEvent(new Integer(status.order_id).intValue(), Event.kEventTypeHandling, "Order voided by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

                req.setAttribute(kCurrentOrder,
                        new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
            } catch (Throwable th) {
                Exception ex;

                if (th instanceof Exception) {
                    ex = (Exception) th;
                } else {
                    ex = new Exception(th.toString());
                }
                OWDUtilities.debugApp(ex);
                ex.printStackTrace();
                throw ex;
            }
            finally {
            }
            req.getRequestDispatcher("editorder.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void editOrderShippingAction(HttpServletRequest req, HttpServletResponse resp) {
        boolean done = false;
        String error = null;

        try {
            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(kCurrentOrder,
                    status);

            if(status.isShipping)
            {
                throw new Exception("Cannot edit shiping information - order has already entered final shipping process");

            }
            if (1 == ExtranetServlet.getIntegerParam(req, "editshippingsave", 0)) {

                Connection cxn = null;
                try {

                    cxn = ConnectionManager.getConnectionManager().getConnection();
                    ShippingInfo shipping = status.shipping;
                 if(req.getParameter("shipc.tagARN")!=null){
                     System.out.println(TagUtilities.kEDIAmazonARN);
                     System.out.println(req.getParameter("shipc.tagARN"));
                     TagUtilities.setOrderTag(Integer.parseInt(status.order_id),TagUtilities.kEDIAmazonARN,ExtranetServlet.getStringParam(req, "shipc.tagARN", ""));
                 } else  if(req.getParameter("shipc.tagZDN")!=null){
                     System.out.println(TagUtilities.kEDIZapposDN);
                     System.out.println(req.getParameter("shipc.tagZDN"));
                     TagUtilities.setOrderTag(Integer.parseInt(status.order_id),TagUtilities.kEDIZapposDN,ExtranetServlet.getStringParam(req, "shipc.tagZDN", ""));
                 } else  if(req.getParameter("shipc.tagDASIDN")!=null){
                     System.out.println(TagUtilities.kEDIDicksASIDN);
                     System.out.println(req.getParameter("shipc.tagDASIDN"));
                     TagUtilities.setOrderTag(Integer.parseInt(status.order_id),TagUtilities.kEDIDicksASIDN,ExtranetServlet.getStringParam(req, "shipc.tagDASIDN", ""));
                 }
                    shipping.shipAddress.company_name = ExtranetServlet.getStringParam(req, "shipa.company_name", ".");
                    shipping.shipAddress.address_one = ExtranetServlet.getStringParam(req, "shipa.address_one", "");
                    shipping.shipAddress.address_two = ExtranetServlet.getStringParam(req, "shipa.address_two", "");
                    shipping.shipAddress.city = ExtranetServlet.getStringParam(req, "shipa.city", "");
                    shipping.shipAddress.state = ExtranetServlet.getStringParam(req, "shipa.state", "");
                    shipping.shipAddress.zip = ExtranetServlet.getStringParam(req, "shipa.zip", "");
                    shipping.shipAddress.country = ExtranetServlet.getStringParam(req, "shipa.country", "");

                    shipping.shipContact.setName(ExtranetServlet.getStringParam(req, "shipc.name", ""));
                    shipping.shipContact.phone = ExtranetServlet.getStringParam(req, "shipc.phone", "");
                    shipping.shipContact.email = ExtranetServlet.getStringParam(req, "shipc.email", "");

                    boolean wasLTL = shipping.carr_service.contains("LTL")?true:false;

                    shipping.setShipOptions(ExtranetServlet.getStringParam(req, "shipping.carr_service", ""), ExtranetServlet.getStringParam(req, "shipping.carr_freight_terms", ""), ExtranetServlet.getStringParam(req, "shipping.third_party_refnum", ""));

                    float declaredValue = 0;

                    try {
                        declaredValue = OWDUtilities.roundFloat(new Float(ExtranetServlet.getStringParam(req, "shipping.declared_value", "0.00")).floatValue(), 2);
                    } catch (Exception ex) {
                        throw new Exception("Declared Value must be a valid number - changes not saved");
                    }

                    if (declaredValue > 0.00) {
                        shipping.ss_declared_value = "1";
                        shipping.declared_value = ExtranetServlet.getStringParam(req, "shipping.declared_value", "0.00");
                    } else {
                        shipping.ss_declared_value = "0";
                        shipping.declared_value = "0.00";
                    }
                    //shipping.customs_desc = "";

                    shipping.avs_overide = ExtranetServlet.getIntegerParam(req,"shipping.avsOveride",0)+"";
                    int forceSaturday = 0;

                    try {
                        forceSaturday = ExtranetServlet.getIntegerParam(req, "shipping.ss_saturday", 0);
                    } catch (Exception ex) {
                        throw new Exception("Saturday Delivery must be a valid number - changes not saved");
                    }

                    shipping.ss_saturday = "" + forceSaturday;

                    int forceSignatureConfirmation = 0;

                    try {
                        forceSignatureConfirmation = ExtranetServlet.getIntegerParam(req, "shipping.ss_proof_delivery", 0);
                    } catch (Exception ex) {
                        throw new Exception("Signature Confirmation must be a valid number - changes not saved");
                    }

                    shipping.ss_proof_delivery = "" + forceSignatureConfirmation;

                    //log.debug("got shipping3");
                    shipping.dbupdate(cxn);
                    cxn.commit();
                    Event.addOrderEvent(new Integer(status.order_id).intValue(), Event.kEventTypeEdit, "Order shipping data edited by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

                    if ((!status.is_void) && status.is_posted && ((!wasLTL && status.is_unpicked) || 1 == ExtranetServlet.getIntegerParam(req, "reprint", 0))) {

                        Event.addOrderEvent(new Integer(status.order_id).intValue(), Event.kEventTypeHandling, "Order reprinted due to edit by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

                        status.reprintOrder();
                    }
                    done = true;

                    saveOrderWithShipInfo2(new Integer(status.order_id).intValue(),req);
                } catch (Exception ex) {
                    cxn.rollback();
                    ex.printStackTrace();
                    error = ex.getMessage();
                } finally {
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }
                }
                if (done) {
                    req.setAttribute(kCurrentOrder,
                            new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                    req.getRequestDispatcher("editorder.jsp").include(req, resp);
                } else {
                    req.setAttribute("formerror", error);
                    req.getRequestDispatcher("editshipping.jsp").include(req, resp);
                }

            } else {
                req.getRequestDispatcher("editshipping.jsp").include(req, resp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveOrderWithShipInfo2(int orderId, HttpServletRequest req) throws  Exception {
        String aesITN = ExtranetServlet.getStringParam(req, "order.aesItn", "");
        int noCustomsAccount = getNoCustomsFromSelectList(ExtranetServlet.getStringParam(req, "order.dduDDP", ""));
        OwdOrder orderToSave = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        OrderShipInfo2 shipInfo2 = orderToSave.getShipInfo2();

        orderToSave.setNoCustomsAccount(noCustomsAccount);
        shipInfo2.setAesItn(aesITN);

        HibernateSession.currentSession().saveOrUpdate(orderToSave);
        HibUtils.commit(HibernateSession.currentSession());
    }

    private int getNoCustomsFromSelectList(String selectedValue) throws  Exception {
        switch (selectedValue) {
            case "":
            case "DDU":
                return 0;
            case "DDP":
                return 1;
            default:
                throw new Exception("value not supported: " + selectedValue);
        }
    }

    private void voidPaymentTransactionAction(HttpServletRequest req, HttpServletResponse resp) {
        boolean done = false;
        String error = null;

        try {
            OrderStatus order = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(kCurrentOrder, order);


            Connection cxn = null;
            try {

                cxn = ConnectionManager.getConnectionManager().getConnection();
                //use old or new info?

                FinancialTransaction trans = FinancialTransaction.dbload(cxn, ExtranetServlet.getStringParam(req, kparamTransactionID, ""));
                trans.is_void = 1;

                trans.dbupdate(cxn);

                cxn.commit();
                done = true;
            } catch (Exception ex) {
                cxn.rollback();
                ex.printStackTrace();
                error = ex.getMessage();
            } finally {
                try {
                    cxn.close();
                } catch (Exception ex) {
                }
            }
            if (done) {
                req.setAttribute(kCurrentOrder,
                        new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                req.getRequestDispatcher("ccaction.jsp").include(req, resp);
            } else {
                req.setAttribute(kCurrentOrder,
                        new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                req.setAttribute("formerror", error);
                req.getRequestDispatcher("ccaction.jsp").include(req, resp);
            }


        } catch (Exception exx) {
            exx.printStackTrace();
            try {
                req.setAttribute(kCurrentOrder,
                        new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                req.setAttribute("formerror", exx.getMessage());
                req.getRequestDispatcher("ccaction.jsp").include(req, resp);
            } catch (Exception sex) {
                sex.printStackTrace();
            }
        }
    }

    private void runPaymentTransactionAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        boolean done = false;
        String error = null;

        try {

            Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "CC/CK transaction attempted by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

            OrderStatus order = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(kCurrentOrder, order);


            if (1 == ExtranetServlet.getIntegerParam(req, "doccaction", 0)) {

                String fullname = "";
                String address_one = "";
                String zip = "";
                String company_name = "";
                String cc_exp = "";
                String cc_num = "";
                String cvv_code = "";
                String city = "";
                String state = "";
                String phone = "";

                Connection cxn = null;
                try {
                    cxn = ConnectionManager.getConnection();

                    //Check type and amount
                    int type = ExtranetServlet.getIntegerParam(req, "transtype", -1);

                    if (type == 0 || type == 1) {


                    } else {
                        throw new Exception("Transaction type not recognized");
                    }

                    String oldTransactionID = ExtranetServlet.getStringParam(req, "creditTransID", "");

                    //get amount
                    float ccamount = (float) 0.0;

                    try {
                        ccamount = new Float(ExtranetServlet.getStringParam(req, "amount", "0.00")).floatValue();
                        if (ccamount < 0.01) {
                            throw new Exception("Amount must be greater than 0.00 - please check and correct. ");
                        }
                    } catch (NumberFormatException nfex) {
                        throw new Exception("Amount was uninterpretable - please check and correct.");
                    }

                    float oldTransactionAmount = 0.00f;


                    if (type == 1) //charge transaction
                    {

                        //use old or new info?
                        int infosource = ExtranetServlet.getIntegerParam(req, "useoriginalcc", -1);
                        if (infosource == 0) {
                            fullname = ExtranetServlet.getStringParam(req, "billc.name", "");
                            address_one = ExtranetServlet.getStringParam(req, "billa.address_one", "");
                            zip = ExtranetServlet.getStringParam(req, "billa.zip", "");
                            city = ExtranetServlet.getStringParam(req, "billa.city", "");
                            state = ExtranetServlet.getStringParam(req, "billa.state", "");
                            company_name = ExtranetServlet.getStringParam(req, "billa.company_name", "");
                            cc_exp = OWDUtilities.getExpDateStr(ExtranetServlet.getIntegerParam(req, "cc_exp_mon", -1), ExtranetServlet.getIntegerParam(req, "cc_exp_year", -1));
                            cc_num = ExtranetServlet.getStringParam(req, "cc_num", "");
                            cvv_code = ExtranetServlet.getStringParam(req, "cc_cvv_code", "");


                        } else if (infosource == 1) {

                            //get info from last good transaction
                            List transactionList = FinancialTransaction.getTransactionsForOrder(cxn, order.order_id, true);//get last good transaction


                            if (transactionList.size() < 1) {
                                transactionList = FinancialTransaction.getTransactionsForOrder(cxn, order.order_id, false);//get last transactions if no good ones

                            }
                            if (transactionList.size() < 1) {
                                throw new Exception("Can't find a previous transaction to use");
                            }

                            fullname = ((FinancialTransaction) transactionList.get(0)).fname + " " + ((FinancialTransaction) transactionList.get(0)).lname;
                            address_one = ((FinancialTransaction) transactionList.get(0)).address_one;
                            zip = ((FinancialTransaction) transactionList.get(0)).zip;
                            company_name = ((FinancialTransaction) transactionList.get(0)).company;
                            city = ((FinancialTransaction) transactionList.get(0)).city;
                            state = ((FinancialTransaction) transactionList.get(0)).state;
                            phone = ((FinancialTransaction) transactionList.get(0)).phone;

                            String expdate = OWDUtilities.getExpDateStr(ExtranetServlet.getIntegerParam(req, "cc_exp_mon", 0), ExtranetServlet.getIntegerParam(req, "cc_exp_year", 0));


                            if ("".equals(ExtranetServlet.getStringParam(req, "bill.expoverride", ""))) {
                                cc_exp = ((FinancialTransaction) transactionList.get(0)).cc_exp;

                            } else {
                                cc_exp = expdate;

                            }
                            cc_num = ((FinancialTransaction) transactionList.get(0)).cc_number;


                        } else {
                            throw new Exception("CC account source not recognized");
                        }


                    } else {
                        //credit transaction
                        FinancialTransaction reftrans = FinancialTransaction.getTransactionForTransactionRef(cxn, oldTransactionID, new Integer(order.order_id).intValue());
                        //get order from reference number
                        if (reftrans == null)
                            throw new Exception("Charge transaction for reference " + oldTransactionID + " not found; you must supply the reference to a valid previous charge in order to issue a credit");
                        fullname = reftrans.fname + " " + reftrans.lname;
                        address_one = reftrans.address_one;
                        zip = reftrans.zip;
                        company_name = reftrans.company;
                        cc_exp = reftrans.cc_exp;
                        phone = reftrans.phone;
                        city = reftrans.city;
                        state = reftrans.state;
                        oldTransactionAmount = reftrans.amount;
                        cc_num = reftrans.cc_number;


                    }

                    //log.debug(":::trying payment");
                    FinancialTransaction ft = new FinancialTransaction("0", order.order_id, "0", "", "", "", "",
                            ccamount, "", FinancialTransaction.TRANS_NEW,
                            FinancialTransaction.TRANS_CC + "", 0, 0, "-1",
                            OWDUtilities.getLastNameFromWholeName(fullname),
                            OWDUtilities.getFirstNameFromWholeName(fullname),
                            address_one, "",
                            zip, company_name,
                            city, "", state, "", phone,
                            order.OWDorderReference, "0", "0", "", "", "", oldTransactionID, "",
                            cc_exp, cc_num, "", "", "", "", "", "", 0, 0, OWDUtilities.getSQLDateTimeForToday());

                    ft.cvvCode = cvv_code;
                    ft.oldTransactionRefAmount = oldTransactionAmount;


                    if (type == 0) {

                        ft.creditCC(Client.getClientForID(cxn, order.client_id), false);
                    } else if (type == 1) {

                        ft.chargeCC(Client.getClientForID(cxn, order.client_id), false);
                    }


                    error = ft.error_reponse;


                    ft.dbsave(cxn);

                    cxn.commit();
                    done = true;
                } catch (Exception ex) {
                    cxn.rollback();
                    ex.printStackTrace();
                    error = ex.getMessage();
                } finally {
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }
                }
                if (done) {
                    req.setAttribute(kCurrentOrder,
                            new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                    req.getRequestDispatcher("ccaction.jsp").include(req, resp);
                } else {
                    req.setAttribute(kCurrentOrder,
                            new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                    req.setAttribute("formerror", error);
                    req.getRequestDispatcher("ccaction.jsp").include(req, resp);
                }
                /////start checkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk
            } else if (2 == ExtranetServlet.getIntegerParam(req, "doccaction", 0)) {

                String fullname = "";
                String address_one = "";
                String zip = "";
                String city = "";
                String state = "";
                String company_name = "";
                String ck_bank = "";
                String ck_acct = "";
                String ck_number = "";


                Connection cxn = null;
                try {
                    cxn = ConnectionManager.getConnection();

                    //Check type and amount
                    int type = ExtranetServlet.getIntegerParam(req, "transtype", -1);

                    if (type == 0 || type == 5) {


                    } else {
                        throw new Exception("Transaction type not recognized");
                    }

                    String oldTransactionID = ExtranetServlet.getStringParam(req, "creditTransID", "");

                    //get amount
                    float ccamount = (float) 0.0;

                    try {
                        ccamount = new Float(ExtranetServlet.getStringParam(req, "amount", "0.00")).floatValue();
                        if (ccamount < 0.01) {
                            throw new Exception("Amount must be greater than 0.00 - please check and correct. ");
                        }
                    } catch (NumberFormatException nfex) {
                        throw new Exception("Amount was uninterpretable - please check and correct.");
                    }

                    float oldTransactionAmount = 0.00f;


                    if (type == 5) //charge transaction
                    {
                        log.debug("new check");
                        //use old or new info?
                        int infosource = ExtranetServlet.getIntegerParam(req, "useoriginalcc", -1);
                        if (infosource == 0) {
                            fullname = ExtranetServlet.getStringParam(req, "billc.name", "");
                            address_one = ExtranetServlet.getStringParam(req, "billa.address_one", "");
                            zip = ExtranetServlet.getStringParam(req, "billa.zip", "");
                            city = ExtranetServlet.getStringParam(req, "billa.city", "");
                            state = ExtranetServlet.getStringParam(req, "billa.state", "");
                            company_name = ExtranetServlet.getStringParam(req, "billa.company_name", "");
                            ck_bank = ExtranetServlet.getStringParam(req, "ck_bank", "");
                            ck_number = ExtranetServlet.getStringParam(req, "ck_number", "");
                            ck_acct = ExtranetServlet.getStringParam(req, "ck_acct", "");


                        } else if (infosource == 1) {

                            //get info from last good transaction
                            List transactionList = FinancialTransaction.getTransactionsForOrder(cxn, order.order_id, true);//get last good transaction


                            if (transactionList.size() < 1) {
                                transactionList = FinancialTransaction.getTransactionsForOrder(cxn, order.order_id, false);//get last transactions if no good ones

                            }
                            if (transactionList.size() < 1) {
                                throw new Exception("Can't find a previous transaction to use");
                            }

                            fullname = ((FinancialTransaction) transactionList.get(0)).fname + " " + ((FinancialTransaction) transactionList.get(0)).lname;
                            address_one = ((FinancialTransaction) transactionList.get(0)).address_one;
                            zip = ((FinancialTransaction) transactionList.get(0)).zip;
                            city = ((FinancialTransaction) transactionList.get(0)).city;
                            state = ((FinancialTransaction) transactionList.get(0)).state;
                            company_name = ((FinancialTransaction) transactionList.get(0)).company;


                            // String expdate = OWDUtilities.getExpDateStr(ExtranetServlet.getIntegerParam(req, "cc_exp_mon", 0), ExtranetServlet.getIntegerParam(req, "cc_exp_year", 0));

                            ck_bank = ((FinancialTransaction) transactionList.get(0)).ck_bank;
                            ck_number = ((FinancialTransaction) transactionList.get(0)).ck_number;
                            ck_acct = ((FinancialTransaction) transactionList.get(0)).ck_acct_num;


                        } else {
                            throw new Exception("CC account source not recognized");
                        }


                        //log.debug(":::trying payment");
                        if (ck_bank.equals("")) throw new Exception("Routing Number blank, please fix.");
                        if (ck_number.equals("")) throw new Exception("Check number is blank, please fix.");
                        if (ck_acct.equals("")) throw new Exception("Check Account is blank, please fix.");


                        FinancialTransaction ft = new FinancialTransaction("0", order.order_id, "0", "", "", "", "",
                                ccamount, "", FinancialTransaction.TRANS_NEW,
                                FinancialTransaction.TRANS_CK + "", 0, 0, "-1",
                                OWDUtilities.getLastNameFromWholeName(fullname),
                                OWDUtilities.getFirstNameFromWholeName(fullname),
                                address_one, "",
                                zip, company_name,
                               city, "", state, "", "",
                                order.OWDorderReference, "0", "0", "", "", "", oldTransactionID, "",
                                "", "", "", ck_acct, "", ck_bank, ck_number, "", 0, 0, OWDUtilities.getSQLDateTimeForToday());

                        // ft.cvvCode = cvv_code;
                        ft.oldTransactionRefAmount = oldTransactionAmount;

                        ft.chargeEcheck(Client.getClientForID(cxn, order.client_id), false);

                        error = ft.error_reponse;


                        ft.dbsave(cxn);

                        cxn.commit();

                        done = true;
                    } else {
                        log.debug("No refund on checks yet");
                        throw new Exception("Credit on Eckeck not available now");

                        /*  //credit transaction
                        FinancialTransaction reftrans = FinancialTransaction.getTransactionForTransactionRef(cxn, oldTransactionID, new Integer(order.order_id).intValue());
                        //get order from reference number
                        if (reftrans == null)
                            throw new Exception("Charge transaction for reference " + oldTransactionID + " not found; you must supply the reference to a valid previous charge in order to issue a credit");
                        fullname = reftrans.fname + " " + reftrans.lname;
                        address_one = reftrans.address_one;
                        zip = reftrans.zip;
                        company_name = reftrans.company;
                        cc_exp = reftrans.cc_exp;

                        oldTransactionAmount = reftrans.amount;
                        cc_num = reftrans.cc_number;

*/
                    }
                } catch (Exception ex) {
                    cxn.rollback();
                    ex.printStackTrace();
                    error = ex.getMessage();
                } finally {
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }
                }
                if (done) {

                    req.setAttribute(kCurrentOrder,
                            new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                    req.getRequestDispatcher("ccaction.jsp").include(req, resp);
                } else {
                    req.setAttribute(kCurrentOrder,
                            new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                    req.setAttribute("formerror", error);
                    req.getRequestDispatcher("ccaction.jsp").include(req, resp);
                }

            } else {
                req.getRequestDispatcher("ccaction.jsp").include(req, resp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                req.setAttribute(kCurrentOrder,
                        new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                req.setAttribute("formerror", ex.getMessage());
                req.getRequestDispatcher("ccaction.jsp").include(req, resp);
            } catch (ServletException sex) {
                sex.printStackTrace();
            }
        }
    }

    private void editOrderBillingAction(HttpServletRequest req, HttpServletResponse resp) {
        boolean done = false;
        String error = null;

        try {

            Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, kparamOrderID, "")).intValue(), Event.kEventTypeEdit, "Billing info edited by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

            req.setAttribute(kCurrentOrder,
                    new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                            ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));


            if (1 == ExtranetServlet.getIntegerParam(req, "editbillingsave", 0)) {
                String sql = "update owd_order set bill_last_name = ?" +
                        ", bill_first_name = ?" +
                        ", bill_address_one = ?" +
                        ", bill_address_two = ?" +
                        ", bill_city = ?" +
                        ", bill_state = ?" +
                        ", bill_zip = ?" +
                        ", bill_country = ?" +
                        ", bill_company_name = ?" +
                        ", bill_phone_num = ?" +
                        ", bill_email_address = ?" +
                        " where order_id = ? and client_fkey = ?";

                Connection cxn = null;
                PreparedStatement ps = null;
                try {

                    cxn = ConnectionManager.getConnectionManager().getConnection();
                    ps = cxn.prepareStatement(sql);
                    ps.setString(1, OWDUtilities.getLastNameFromWholeName(ExtranetServlet.getStringParam(req, "billc.name", "")));
                    ps.setString(2, OWDUtilities.getFirstNameFromWholeName(ExtranetServlet.getStringParam(req, "billc.name", "")));
                    ps.setString(3, ExtranetServlet.getStringParam(req, "billa.address_one", ""));
                    ps.setString(4, ExtranetServlet.getStringParam(req, "billa.address_two", ""));
                    ps.setString(5, ExtranetServlet.getStringParam(req, "billa.city", ""));
                    ps.setString(6, ExtranetServlet.getStringParam(req, "billa.state", ""));
                    ps.setString(7, ExtranetServlet.getStringParam(req, "billa.zip", ""));
                    ps.setString(8, ExtranetServlet.getStringParam(req, "billa.country", ""));
                    ps.setString(9, ExtranetServlet.getStringParam(req, "billa.company_name", "."));
                    ps.setString(10, ExtranetServlet.getStringParam(req, "billc.phone", ""));
                    ps.setString(11, ExtranetServlet.getStringParam(req, "billc.email", ""));
                    ps.setInt(12, new Integer(ExtranetServlet.getStringParam(req, kparamOrderID, "")).intValue());
                    ps.setInt(13, new Integer(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue());
                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated < 1)
                        throw new Exception("Could not update order - internal error ");
                    cxn.commit();
                    done = true;

                } catch (Exception ex) {
                    cxn.rollback();
                    ex.printStackTrace();
                    error = ex.getMessage();
                } finally {
                    try {
                        ps.close();
                    } catch (Exception ex) {
                    }
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }
                }
                if (done) {
                    req.setAttribute(kCurrentOrder,
                            new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                    req.getRequestDispatcher("editorder.jsp").include(req, resp);
                } else {
                    req.setAttribute("formerror", error);
                    req.getRequestDispatcher("editbilling.jsp").include(req, resp);
                }

            } else {
                req.getRequestDispatcher("editbilling.jsp").include(req, resp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewOrderaction(HttpServletRequest req, HttpServletResponse resp) {
        try {

            req.setAttribute(kCurrentOrder,
                    new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                            ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
            try {
                req.getSession(true).removeAttribute(kCurrentOrder);
            } catch (Throwable th) {
            }
            Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "Order viewed by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

            req.getRequestDispatcher("editorder.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createOrderAction(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute(kCurrentFinder, OrderFinder.parseRequest(req, resp));
            req.getRequestDispatcher("createorder.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void shipExistingOrderAction(HttpServletRequest req, HttpServletResponse resp) {
        try {

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

            Date holdUntilDate = Calendar.getInstance().getTime();

            if (req.getParameter("holdUntilDate") != null) {
                try

                {
                    holdUntilDate = df.parse(req.getParameter("holdUntilDate"));
                } catch (Exception exd) {

                }
            }


            Connection cxn = null;
            OrderStatus status = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            req.setAttribute(kCurrentOrder,
                    status);

            try {
                cxn = ConnectionManager.getConnection();
                int units = 0;
                status = (OrderStatus) (req.getSession(true).getAttribute(kCurrentOrder));

                if (status == null)
                    throw new Exception("Could not locate order to ship");

                for (int i = 0; i < status.items.size(); i++) {

                    LineItem item = (LineItem) status.items.elementAt(i);
                    Inventory inv = Inventory.dbloadByPart(item.client_part_no, ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                    if ((inv.is_auto_inventory == 0 )
                    || ( (ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID).equals("463") && (inv.notes.startsWith("DROP")))))
                {
                    units += item.quantity_actual;
                }


                }

                if (units < 1)
                    throw new Exception("No shippable items found - ship request cancelled");

//toss back if we're just changing the predicted weight

                if (ExtranetServlet.getStringParam(req, "submit", "").indexOf("Complete Shipment") < 0) {

                    req.getRequestDispatcher("shipmethod.jsp").include(req, resp);
                } else {
//check for changed ship method and update if needed
                    String newMethodCode = ExtranetServlet.getStringParam(req, "shipmethod", "");
                    if (newMethodCode.equals("")) {
                        throw new Exception("No valid shipping method found - ship request cancelled");
                    } else {
                        if (!(newMethodCode.equals("CURRENT"))) {
                            status.shipping.carr_service_ref_num = newMethodCode;
                            System.out.println("hello");
                            System.out.println(status.getShipPolicy());
                            System.out.println(status.getLocation());

                            if(status.getLocation().length()>0){
                                status.shipping.carr_service = (String) (com.owd.core.csXml.OrderRater.getRateableServicesMapByLocation(status.getLocation()).get(newMethodCode));

                            }else {
                                status.shipping.carr_service = (String) (com.owd.core.csXml.OrderRater.getRateableServicesMap().get(newMethodCode));
                            }
                            status.shipping.dbupdate(cxn);
                        }
                    }

                    String backorderRef = OrderUtilities.shipExistingOrder(cxn, status, holdUntilDate);

                    cxn.commit();
                    req.getSession(true).removeAttribute(kCurrentOrder);
                    Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "Existing order released for shipping by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

                    req.setAttribute(kCurrentOrder,
                            new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));

                    // currAction = kParamOrderEditAction;
                    req.getRequestDispatcher("editorder.jsp").include(req, resp);
                }
            } catch (Exception ex) {
                try {
                    cxn.rollback();
                } catch (Throwable th) {
                }
                ex.printStackTrace();
                String error = "Error processing order - please report to casetracker@owd.com : " + ex.getMessage();
                req.setAttribute("formerror", error);
                try {
                    req.getSession(true).removeAttribute(kCurrentOrder);
                } catch (Throwable th) {
                }
                //   currAction = kParamOrderEditAction;
                req.getRequestDispatcher("editorder.jsp").include(req, resp);
            } finally {
                try {
                    cxn.close();
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {
        //log.debug("" + com.owd.core.csXml.OrderRater.getRateableServicesMap().get("TANDATA_UPS.UPS.WEXPSVR"));
       try{
           List<String> l = new ArrayList<String>();
           l.add("11376393");
           l.add("11377155");
           l.add("11376831");
           l.add("11390786");
           l.add("11382928");
           l.add("11389446");
           l.add("11391118");
           l.add("11393133");
           l.add("11391553");
           l.add("11392045");
           l.add("11375709");
           l.add("11387454");
           l.add("11379330");
           l.add("11392463");
           l.add("11393130");
           l.add("11397756");
           l.add("11394816");
           l.add("11374673");
           l.add("11398683");
           l.add("11375710");
           l.add("11389802");
           l.add("11380072");
           l.add("11392464");
           l.add("11379331");
           l.add("11392807");
           l.add("11377928");
           l.add("11393131");
           l.add("11395432");
           l.add("11388691");
           l.add("11383685");
           l.add("11397353");
           l.add("11396961");
           l.add("11398684");
           l.add("11393758");
           l.add("11401177");
           l.add("11375206");
           l.add("11389066");
           l.add("11399342");
           l.add("11382929");
           l.add("11373758");
           l.add("11389447");
           l.add("11374002");
           l.add("11377153");
           l.add("11394417");
           l.add("11375207");
           l.add("11387455");
           l.add("11382930");
           l.add("11374543");
           l.add("11396575");
           l.add("11377926");
           l.add("11380073");
           l.add("11377156");
           l.add("11386402");
           l.add("11390787");
           l.add("11382927");
           l.add("11392808");
           l.add("11385675");
           l.add("11388271");
           l.add("11376392");
           l.add("11391119");
           l.add("11395433");
           l.add("11391554");
           l.add("11377929");
           l.add("11392044");
           l.add("11377157");
           l.add("11387453");
           l.add("11390788");
           l.add("11374163");
           l.add("11378648");
           l.add("11373759");
           l.add("11391120");
           l.add("11394154");
           l.add("11387513");
           l.add("11377930");
           l.add("11375711");
           l.add("11377154");
           l.add("11395794");
           l.add("11396195");
           l.add("11389803");
           l.add("11390785");
           l.add("11380071");
           l.add("11377068");
           l.add("11393132");
           l.add("11383686");
           l.add("11388690");
           l.add("11396576");
           l.add("11397354");
           l.add("11377927");
           l.add("11396962");
           for(String s:l){
               unpostOrder(s,"577","Client Request");
           }

       }catch(Exception e){
           e.printStackTrace();
       }
    }


    public static void unpostOrder(String orderId,String clientId,String user)
    {
        try {

            OrderStatus status = new OrderStatus(orderId,clientId);


            try {

                unpostOrderFromStatus(user, status);



            } catch (Throwable th) {
                Exception ex;

                if (th instanceof Exception) {
                    ex = (Exception) th;
                } else {
                    ex = new Exception(th.toString());
                }
                OWDUtilities.debugApp(ex);
                ex.printStackTrace();
            }
            finally {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void voidPackageAction(HttpServletRequest req, HttpServletResponse resp) {
        try {

            OrderStatus order = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

            com.owd.core.business.order.Package.voidOrderPackage(ExtranetServlet.getStringParam(req, kparamShipmentID, "0"), order.order_id);

            Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "Package voided by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

            order = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

            req.setAttribute(kCurrentOrder, order);

            try {
                req.getSession(true).removeAttribute(kCurrentOrder);
            } catch (Throwable th) {
            }

            req.getRequestDispatcher("editorder.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void changeLocationAction(HttpServletRequest req, HttpServletResponse resp) {
        try {


            OrderStatus order = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

            if (ExtranetServlet.getStringParam(req, "location", "").trim().length() > 0) {
                String newCode = ExtranetServlet.getStringParam(req, "location", "").trim();
                if(FacilitiesManager.getActiveFacilityCodes().contains(newCode)) {
                    order.changeLocation(newCode);
                    Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "Ship from facility changed to " + newCode + " by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());
                }else
                {
                    throw new Exception(newCode+ " is not a valid facility code");

                }
            }   else
            {
                throw new Exception("No facility code was provided");
            }
            req.setAttribute(kCurrentOrder, order);

            try {
                req.getSession(true).removeAttribute(kCurrentOrder);
            } catch (Throwable th) {
            }

            req.getRequestDispatcher("editorder.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addCommentAction(HttpServletRequest req, HttpServletResponse resp) {
        try {


            OrderStatus order = new OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
            if (ExtranetServlet.getStringParam(req, "newCommentText", "").trim().length() > 0) {
                order.addComment(ExtranetServlet.getStringParam(req, "newCommentText", ""), req.getUserPrincipal().getName());
                Event.addOrderEvent(new Integer(ExtranetServlet.getStringParam(req, kparamOrderID, "")).intValue(), Event.kEventTypeHandling, "Note added by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());
            }
            req.setAttribute(kCurrentOrder, order);

            try {
                req.getSession(true).removeAttribute(kCurrentOrder);
            } catch (Throwable th) {
            }

            req.getRequestDispatcher("editorder.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getScanAction(HttpServletRequest req, HttpServletResponse resp) {
        try {
            getScan(req, resp);
        } catch (Exception exxx) {
            exxx.printStackTrace();
        }
    }

    private void getCommercialInvoiceAction(HttpServletRequest req, HttpServletResponse resp) {
        try {
            JasperReportPrintManager.getCommercialInvoice(req, resp);
        } catch (Exception exxx) {
            exxx.printStackTrace();
        }
    }



    private static void defaultViewAction(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute(kCurrentFinder, OrderFinder.parseRequest(req, resp));
            if (OrderFinder.parseRequest(req, resp) != null)
                log.debug("got finder!\n");
            req.getRequestDispatcher("findorder.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String[] getManagerActions(HttpServletRequest req) {
        String[] urls = {"","",""};

        urls[0] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + ExtranetServlet.kParamAdminAction+"="+ExtranetServlet.kParamChangeMgrAction +"&"+ ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kOrdersMgrName+"&"+kParamOrderMgrAction + "=" + kParamOrderExploreAction + "\" >Search</A>";
        urls[1] = "<A HREF=\"" + req.getContextPath() + "/callcenter/orderentry/index.jsp\" >New Order</A>";
        urls[2] = "<A HREF=\"" + req.getContextPath() + "/clienttools/batchimporters/index.jsp\" >Batch Upload</A>";

        //http://sblaptop.owd.com:8081/webapps/clienttools/batchimporters/index.jsp
        return urls;
    }

    public void showTable(HttpServletRequest req, HttpServletResponse resp, String[] criteria, String description) throws IOException {


        WebTable table = new WebTable();


        table.setSQLDefs(tableColumnNames, tableColumnDefs, tableLinkStarts, tableLinkMids, tableLinkEnds, tableFromStmt);

        table.addCriterium("i.client_fkey=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

        if (criteria != null) {
            for (int i = 0; i < criteria.length; i++) {
                table.addCriterium(criteria[i]);
            }
        }
        table.setDescription((description == null ? "Showing All..." : description));
        //check for find criteria

        int gotono = ExtranetServlet.getIntegerParam(req, WebTable.kTableGotoPage, 0);
        if (gotono != 0)
            table.setPageNum(gotono);
        else
            table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));
        table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));

        table.getTable(req, resp);

    }

    public void getManagerHeader(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        out.write("<TD VALIGN=\"TOP\">");
    }

    public void getManagerFooter(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        out.write("</TD></TR>");
        out.write("</TABLE>");
    }

    public static void getScan(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        try {
            String orderid = req.getParameter("orderid");

            String filename = req.getParameter("filename");
            String cid = req.getParameter("cid");

            if (req.getParameter("auth") != null) {

                String[] result = OWDUtilities.decryptData(req.getParameter("auth")).split("/");
                if (result.length == 3) {

                    orderid = result[0];
                    filename = result[1];
                    cid = result[2];
                }
            }
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(orderid));
            Event.addOrderEvent(new Integer(orderid).intValue(), Event.kEventTypeHandling, "Packing slip PDF downloaded by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

            returnScanDataToBrowser(resp, ScanManager.getScanFromOwdOrder(order, filename), filename);
        } catch (Exception ex) {
            ex.printStackTrace();
            //todo redirect

        } finally {
            HibernateSession.closeSession();
        }
    }


    public static void returnScanDataToBrowser(HttpServletResponse resp, byte[] data, String attName) throws Exception {

        try {

            resp.reset();
            if (attName.toUpperCase().endsWith(".PDF")) {
                resp.setContentType("application/pdf");
            } else {
                resp.setContentType("application/octet-stream");
            }
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + attName.trim().replace(' ', '_').replace(':', '-') + "\"");

            log.debug("datalen=" + data.length);
            resp.setContentLength(data.length);
            resp.getOutputStream().write(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("There was a problem running your report. Please check your parameters.");
        } finally {


        }

    }

    /**
     * Sean Chen created on 4/5/2019 Download order in Excel format
     * Call the ExcelDownload class to generate the order in Excel
     * @param req
     * @param resp
     */
    private void downloadExcelAction (HttpServletRequest req, HttpServletResponse resp){
        log.debug("Download Excel begin");
        try {

            String orderid = req.getParameter("orderid");
            if (req.getParameter("auth") != null) {

                String[] result = OWDUtilities.decryptData(req.getParameter("auth")).split("/");
                if (result.length == 3) {
                    orderid = result[0];
                }
            }
            resp.reset();
            byte [] byteArray = ExcelDownload.generateInvoice(Integer.parseInt(orderid));
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + orderid + ".xlsx\"");
            resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            resp.setContentLength(byteArray.length);
            resp.getOutputStream().write(byteArray);
            Event.addOrderEvent(new Integer(orderid).intValue(), Event.kEventTypeHandling, "Excel " +
                    "downloaded by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());

        }catch (Exception ex){
            ex.printStackTrace();
        }
        log.debug("Excel download exit");
    }
}

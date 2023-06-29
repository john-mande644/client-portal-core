package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.order.*;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.generated.OwdOrder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


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
    public static final String kParamOrderVoidPackageAction = "voidpackage";

    public static final String kParamOrderAddCommentAction = "addcomment";

    public static final String kParamOrderShipMethodAction = "ordershipmethod";

    public static final String kParamOrderQuickFind = "qf";

    public static final String kparamOrderID = "oid";
    public static final String kparamShipmentID = "pid";
    public static final String kCurrentFinder = "currOrderFinder";
    public static final String kCurrentOrder = "currOrderStatus";

    String[] tableColumnNames = {"ID", "Customer", "Reference", "Released", "Void"};
    String[] tableColumnDefs = {"order_id", "bill_first_name+\" \"+bill_last_name", "order_refnum", 	"\"<B>\"+order_status+\"</B>\")", "CASE WHEN is_void = 1 THEN \'Yes\' ELSE \'No\' END"};
    String tableFromStmt = "from owd_order i ";
    String[] tableLinkStarts = {"", "", "", "", ""};
    String[] tableLinkEnds = {"", "", "", "", ""};
    int[] tableLinkMids = {0, 0, 0, 0, 0};


    public String getManagerMenus(HttpServletRequest req) {
        StringBuffer sb = new StringBuffer();
        sb.append("<UL><LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamOrderMgrAction + "=" + kParamOrderExploreAction + "\" >");
        sb.append("Explore Orders");
        sb.append("<LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamOrderMgrAction + "=" + kParamOrderExploreAction + "\" >");
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

        String currAction = ExtranetServlet.getStringParam(req, kParamOrderMgrAction, kParamOrderExploreAction);


        //do real work here


        if (currAction.equals(kParamOrderExploreAction)) {
            try {

                OrderFinder of = null;

                if (null == req.getSession(true).getAttribute(kCurrentFinder)) {
                    of = new OrderFinder(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                    req.getSession(true).setAttribute(kCurrentFinder, of);
                } else {

                    of = (OrderFinder) req.getSession(true).getAttribute(kCurrentFinder);
                }
                of.setOrderStatusCriterion(ExtranetServlet.getIntegerParam(req, "orderStatusType", of.getOrderStatusCriterion()));
                of.setDateContextLimit(ExtranetServlet.getIntegerParam(req, "dateContextType", of.getDateContextLimit()));

                of.setTextSearchType(ExtranetServlet.getIntegerParam(req, "textSearchType", of.getTextSearchType()));
                of.setDateType(ExtranetServlet.getIntegerParam(req, "dateType", of.getDateType()));
                of.setTextSearchValue(ExtranetServlet.getStringParam(req, "textSearchValue", of.getTextSearchValue()));

                of.setTextSearchField(ExtranetServlet.getStringParam(req, "textSearchField", of.getTextSearchField()));


                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "findorder.jsp").include(req, resp);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (currAction.equals(kParamOrderFindAction)) {
            showTable(req, resp, null, null);
        } else if (currAction.equals(kParamOrderAddCommentAction)) {
            try {

                com.owd.core.business.order.OrderStatus order = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                order.addComment(ExtranetServlet.getStringParam(req, "newCommentText", ""));
                req.setAttribute(kCurrentOrder, order);

                try {
                    req.getSession(true).removeAttribute(kCurrentOrder);
                } catch (Throwable th) {
                }
                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (currAction.equals(kParamOrderVoidPackageAction)) {
            try {

                com.owd.core.business.order.OrderStatus order = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

                com.owd.core.business.order.Package.voidOrderPackage(ExtranetServlet.getStringParam(req, kparamShipmentID, "0"), order.order_id);

                order = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

                req.setAttribute(kCurrentOrder, order);

                try {
                    req.getSession(true).removeAttribute(kCurrentOrder);
                } catch (Throwable th) {
                }

                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (currAction.equals(kParamOrderShipMethodAction)) {
            try {
                Connection cxn = null;
                OrderStatus status = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
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
                        units += item.quantity_actual;
                    }

                    if (units < 1)
                        throw new Exception("No shippable items found - ship request cancelled");

                    //toss back if we're just changing the predicted weight

                    if (ExtranetServlet.getStringParam(req, "submit", "").indexOf("Complete Shipment") < 0) {

                        req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "shipmethod.jsp").include(req, resp);
                    } else {
                        //check for changed ship method and update if needed
                        String newMethodCode = ExtranetServlet.getStringParam(req, "shipmethod", "");
                        if (newMethodCode.equals("")) {
                            throw new Exception("No valid shipping method found - ship request cancelled");
                        } else {
                            if (!(newMethodCode.equals("CURRENT"))) {
                                status.shipping.carr_service_ref_num = newMethodCode;
                                status.shipping.carr_service = (String) (com.owd.core.csXml.OrderRater.getRateableServicesMap().get(newMethodCode));
                                status.shipping.dbupdate(cxn);
                            }
                        }

                        String backorderRef = OrderUtilities.shipExistingOrder(cxn, status);

                        cxn.commit();
                        req.getSession(true).removeAttribute(kCurrentOrder);
                        req.setAttribute(kCurrentOrder,
                                new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));

                        currAction = kParamOrderEditAction;
                        req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
                    }
                } catch (Exception ex) {
                    try {
                        cxn.rollback();
                    } catch (Throwable th) {
                    }
                    ex.printStackTrace();
                    String error = "Error processing order - please report to owditadmin@owd.com";
                    req.setAttribute("formerror", error);
                    try {
                        req.getSession(true).removeAttribute(kCurrentOrder);
                    } catch (Throwable th) {
                    }
                    currAction = kParamOrderEditAction;
                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
                } finally {
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (currAction.equals(kParamOrderCreateAction)) {
            try {
                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "createorder.jsp").include(req, resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (currAction.equals(kParamOrderEditAction)) {
            try {

                Integer listindex = new Integer(ExtranetServlet.getIntegerParam(req, "listindex", 0));
                int listSize = ((List) req.getSession(true).getAttribute("orderfinderresults")).size();
                if (listindex.intValue() > (listSize - 1)) listindex = new Integer(listSize - 1);
                if (listindex.intValue() < 0) listindex = new Integer(0);
                req.setAttribute(kCurrentOrder,
                        new com.owd.core.business.order.OrderStatus(((OwdOrder) ((List) req.getSession(true).getAttribute("orderfinderresults")).toArray()[listindex.intValue()]).getOrderId() + "",
                                ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));

                req.getSession(true).setAttribute("orderlistindex", listindex);
                try {
                    req.getSession(true).removeAttribute(kCurrentOrder);
                } catch (Throwable th) {
                }
                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (currAction.equals(kParamOrderEditBillingAction)) {
            boolean done = false;
            String error = null;

            try {

                req.setAttribute(kCurrentOrder,
                        new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
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
                        cxn = ConnectionManager.getConnection();
                        ps = cxn.prepareStatement(sql);
                        ps.setString(1, com.owd.core.OWDUtilities.getLastNameFromWholeName(ExtranetServlet.getStringParam(req, "billc.name", "")));
                        ps.setString(2, com.owd.core.OWDUtilities.getFirstNameFromWholeName(ExtranetServlet.getStringParam(req, "billc.name", "")));
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

                        //if (rowsUpdated < 1)
                        //	throw new Exception("Could not update order - internal error ");
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
                                new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                        req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
                    } else {
                        req.setAttribute("formerror", error);
                        req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editbilling.jsp").include(req, resp);
                    }

                } else {
                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editbilling.jsp").include(req, resp);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (currAction.equals(kParamOrderDoCCAction)) {
            boolean done = false;
            String error = null;

            try {
                OrderStatus order = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                req.setAttribute(kCurrentOrder, order);


                if (1 == ExtranetServlet.getIntegerParam(req, "doccaction", 0)) {

                    String fullname = "";
                    String address_one = "";
                    String zip = "";
                    String company_name = "";
                    String cc_exp = "";
                    String cc_num = "";


                    Connection cxn = null;
                    try {
                        cxn = ConnectionManager.getConnection();
                        //use old or new info?
                        int infosource = ExtranetServlet.getIntegerParam(req, "useoriginalcc", -1);
                        if (infosource == 0) {
                            fullname = ExtranetServlet.getStringParam(req, "billc.name", "");
                            address_one = ExtranetServlet.getStringParam(req, "billa.address_one", "");
                            zip = ExtranetServlet.getStringParam(req, "billa.zip", "");
                            company_name = ExtranetServlet.getStringParam(req, "billa.company_name", "");
                            cc_exp = com.owd.core.OWDUtilities.getExpDateStr(ExtranetServlet.getIntegerParam(req, "cc_exp_mon", -1), ExtranetServlet.getIntegerParam(req, "cc_exp_year", -1));
                            cc_num = ExtranetServlet.getStringParam(req, "cc_num", "");

                        } else if (infosource == 1) {
                            if (order.transactions.size() < 1)
                                throw new Exception("Can't find a previous transaction to use");

                            fullname = ((FinancialTransaction) order.transactions.get(0)).fname + " " + ((FinancialTransaction) order.transactions.get(0)).lname;
                            address_one = ((FinancialTransaction) order.transactions.get(0)).address_one;
                            zip = ((FinancialTransaction) order.transactions.get(0)).zip;
                            company_name = ((FinancialTransaction) order.transactions.get(0)).company;
                            cc_exp = ((FinancialTransaction) order.transactions.get(0)).cc_exp;


                            cc_num = ((FinancialTransaction) order.transactions.get(0)).cc_number;

                        } else {
                            throw new Exception("CC account source not recognized");
                        }

                        //get amount
                        float ccamount = (float) 0.0;
                        try {
                            ccamount = new Float(ExtranetServlet.getStringParam(req, "amount", "0.00")).floatValue();
                            if (ccamount < 0.01) {
                                throw new Exception("Amount must be greater than 0.00 - please check and correct.");
                            }
                        } catch (NumberFormatException nfex) {
                            throw new Exception("Amount was uninterpretable - please check and correct.");
                        }

                        String oldTransactionID = ExtranetServlet.getStringParam(req, "creditTransID", "");
                        //log.debug(":::trying payment");
                        FinancialTransaction ft = new FinancialTransaction("0", order.order_id, "0", "", "", "", "",
                                ccamount, "", FinancialTransaction.TRANS_NEW,
                                FinancialTransaction.TRANS_CC + "", 0, 0, "-1",
                                com.owd.core.OWDUtilities.getLastNameFromWholeName(fullname),
                                com.owd.core.OWDUtilities.getFirstNameFromWholeName(fullname),
                                address_one, "",
                                zip, company_name,
                                "", "", "", "", "",
                                order.OWDorderReference, "0", "0", "", "", "", oldTransactionID, "",
                                cc_exp, cc_num, "", "", "", "", "", "", 0, 0, com.owd.core.OWDUtilities.getSQLDateTimeForToday());


                        //Check type and amount
                        int type = ExtranetServlet.getIntegerParam(req, "transtype", -1);

                        if (type == 0) {

                            ft.creditCC(Client.getClientForID(cxn, order.client_id), false);
                        } else if (type == 1) {

                            ft.chargeCC(Client.getClientForID(cxn, order.client_id), false);
                        } else {
                            throw new Exception("Transaction type not recognized");
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
                                new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                        req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "ccaction.jsp").include(req, resp);
                    } else {
                        req.setAttribute(kCurrentOrder,
                                new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                        req.setAttribute("formerror", error);
                        req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "ccaction.jsp").include(req, resp);
                    }

                } else {
                    req.getRequestDispatcher("ccaction.jsp").include(req, resp);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    req.setAttribute(kCurrentOrder,
                            new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                    req.setAttribute("formerror", ex.getMessage());
                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "ccaction.jsp").include(req, resp);
                } catch (ServletException sex) {
                    sex.printStackTrace();
                }
            }

        } else if (currAction.equals(kParamOrderEditShippingAction)) {
            boolean done = false;
            String error = null;

            try {
                OrderStatus status = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                req.setAttribute(kCurrentOrder,
                        status);


                if (1 == ExtranetServlet.getIntegerParam(req, "editshippingsave", 0)) {

                    Connection cxn = null;
                    try {

                        cxn = ConnectionManager.getConnection();
                        ShippingInfo shipping = status.shipping;

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

                        shipping.setShipOptions(ExtranetServlet.getStringParam(req, "shipping.carr_service", ""), ExtranetServlet.getStringParam(req, "shipping.carr_freight_terms", ""), ExtranetServlet.getStringParam(req, "shipping.third_party_refnum", ""));

                        float declaredValue = 0;

                        try {
                            declaredValue = com.owd.core.OWDUtilities.roundFloat(new Float(ExtranetServlet.getStringParam(req, "shipping.declared_value", "0.00")).floatValue(), 2);
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


                        int forceSaturday = 0;

                        try {
                            forceSaturday = ExtranetServlet.getIntegerParam(req, "shipping.ss_saturday", 0);
                        } catch (Exception ex) {
                            throw new Exception("Saturday Delivery must be a valid number - changes not saved");
                        }

                        shipping.ss_saturday = "" + forceSaturday;

                        //log.debug("got shipping3");
                        shipping.dbupdate(cxn);
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
                                new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                        req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
                    } else {
                        req.setAttribute("formerror", error);
                        req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editshipping.jsp").include(req, resp);
                    }

                } else {
                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editshipping.jsp").include(req, resp);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (currAction.equals(kParamOrderVoidAction)) {
            try {

                OrderStatus status = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                req.setAttribute(kCurrentOrder,
                        status);

                Connection cxn = null;
                Statement stmt = null;
                try {

                    cxn = ConnectionManager.getConnection();


                    String esql = "exec void_order " + status.order_id;

                    stmt = cxn.createStatement();

                    stmt.executeUpdate(esql);

                    /***************************
                     COMMIT
                     ***************************/
                    cxn.commit();
                    req.setAttribute(kCurrentOrder,
                            new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));
                } catch (Throwable th) {
                    Exception ex;

                    if (th instanceof Exception) {
                        ex = (Exception) th;
                    } else {
                        ex = new Exception(th.toString());
                    }
                    com.owd.core.OWDUtilities.debugApp(ex);
                    ex.printStackTrace();
                    cxn.rollback();
                    throw ex;
                } finally {
                    try {
                        stmt.close();
                    } catch (Exception ex) {
                    }
                    ConnectionManager.freeConnection(cxn);
                }
                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (currAction.equals(kParamOrderShipAction)) {
            String error = null;
            try {

                OrderStatus status = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                req.setAttribute(kCurrentOrder,
                        status);
                Connection cxn = null;

                try {
                    cxn = ConnectionManager.getConnection();
                    int units = 0;

                    for (int i = 0; i < status.items.size(); i++) {
                        //update line item quantities
                        LineItem item = (LineItem) status.items.elementAt(i);

                        if ("Ship Checked Items Only".equals(ExtranetServlet.getStringParam(req, "shipOrder", ""))) {
                            if (ExtranetServlet.getIntegerParam(req, "ship_item_" + item.line_item_id, 0) == 1) {
                                //log.debug("found shipping item");
                                if (item.updateQuantities(OrderUtilities.getAvailableInventory(cxn, item.inventory_fkey, FacilitiesManager.getFacilityForCode(status.shipLocation).getId()), OrderXMLDoc.kPartialShip)) {
                                }
                            } else {
                                item.quantity_actual = 0;
                                item.quantity_backordered = item.quantity_request;
                                item.quantity_request = 0;
                            }
                        } else {
                            if (item.updateQuantities(OrderUtilities.getAvailableInventory(cxn, item.inventory_fkey, FacilitiesManager.getFacilityForCode(status.shipLocation).getId()), OrderXMLDoc.kPartialShip)) {
                            }
                        }

                        units += item.quantity_actual;
                    }

                    if (units < 1)
                        throw new Exception("No shippable items found - ship request cancelled");

                    cxn.rollback();


                    req.getSession(true).setAttribute(kCurrentOrder, status);

                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "shipmethod.jsp").include(req, resp);

                } catch (Exception ex) {
                    cxn.rollback();
                    ex.printStackTrace();
                    error = "Error processing order - please report to owditadmin@owd.com";
                    req.setAttribute("formerror", error);
                    try {
                        req.getSession(true).removeAttribute(kCurrentOrder);
                    } catch (Throwable th) {
                    }
                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
                } finally {
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (currAction.equals(kParamOrderRemoveHoldAction)) {
            String error = null;
            try {

                OrderStatus status = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                req.setAttribute(kCurrentOrder,
                        status);
                Connection cxn = null;
                Statement stmt = null;

                try {
                    cxn = ConnectionManager.getConnection();

                    String esql = "update owd_order set  is_future_ship = 0, is_backorder=1, backorder_order_num = \'\' where order_id = " + status.order_id;
                    stmt = cxn.createStatement();

                    int rowsUpdated = stmt.executeUpdate(esql);

                    if (rowsUpdated < 1)
                        throw new Exception("Order not updated; could not release hold");

                    cxn.commit();
                    req.setAttribute(kCurrentOrder,
                            new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));

                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);

                } catch (Exception ex) {
                    cxn.rollback();
                    ex.printStackTrace();
                    error = "Error processing order - please report to owditadmin@owd.com<BR>Error: " + ex.getMessage();
                    req.setAttribute("formerror", error);
                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
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

        } else if (currAction.equals(kParamOrderSetHoldAction)) {
            String error = null;
            try {

                OrderStatus status = new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                        ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));
                req.setAttribute(kCurrentOrder,
                        status);
                Connection cxn = null;
                Statement stmt = null;

                try {
                    cxn = ConnectionManager.getConnection();

                    String esql = "update owd_order set  is_future_ship = 1,is_backorder=0  where order_id = " + status.order_id;
                    stmt = cxn.createStatement();

                    int rowsUpdated = stmt.executeUpdate(esql);

                    if (rowsUpdated < 1)
                        throw new Exception("Order not updated; could not set on hold");

                    cxn.commit();
                    req.setAttribute(kCurrentOrder,
                            new com.owd.core.business.order.OrderStatus(ExtranetServlet.getStringParam(req, kparamOrderID, ""),
                                    ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));

                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);

                } catch (Exception ex) {
                    cxn.rollback();
                    ex.printStackTrace();
                    error = "Error processing order - please report to owditadmin@owd.com<BR>Error: " + ex.getMessage();
                    req.setAttribute("formerror", error);
                    req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "editorder.jsp").include(req, resp);
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


    }

    public String[] getManagerActions(HttpServletRequest req) {
        String[] urls = {"", "", ""};

        urls[0] = "<A class=\"command\" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamOrderMgrAction + "=" + kParamOrderExploreAction + "\" >Search</A>";

        urls[1] = "<A class=\"command\" target=\"_orderentry\" HREF=\"/webapps/callcenter/orderentry/orderentry.jsp?selected_client=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID) + "\" >Create</A>";

        urls[2] = "<A class=\"command\" target=\"_orderbatch\" HREF=\"/webapps/clienttools/batchimporters/index.jsp\" >Batch Import</A>";


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
}

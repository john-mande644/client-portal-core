package com.owd.web.callcenter.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CreditCard;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.PackageRate;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.business.order.clients.PhionUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.web.servlet.ExtranetServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 25, 2003
 * Time: 10:30:58 AM
 * To change this template use Options | File Templates.
 */
public class PhionServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    private String[] bulkLoadColumns = {"CUSTOMER #", "COMPANY NAME", "FIRST NAME", "LAST NAME",
                                        "MAILING ADDRESS", "CITY", "STATE", "ZIP CODE", "SIC"};


    private String[] ordersBulkLoadColumns = {"OrderID", "CustomerID", "CustomerFirstName", "CustomerLastName",
                                              "CustomerPhone", "CustomerEmail", "BillingStreet1", "BillingStreet2",
                                              "BillingCity", "BillingState", "BillingZip", "BillingCountry",
                                              "PaymentType", "PaymentCostModifier", "PaymentConfirmationMessage",
                                              "ShippingSameAsBilling", "ShippingName", "ShippingFirstName",
                                              "ShippingLastName", "ShippingStreet1", "ShippingStreet2",
                                              "ShippingCity", "ShippingState", "ShippingZip", "ShippingCountry",
                                              "TotalShippingWeight", "OrderStatus", "OrderDateTime", "OrderNotes",
                                              "OrderTax", "OrderShippingCost", "OrderSubTotal", "QuantityDiscounts",
                                              "OrderTotal", "AdminShippingTracking", "AdminSalesRep", "AdminNotes",
                                              "dPerOrderHandlingFee", "DiscountCode", "DiscountAmount", "DiscountCookie",
                                              "QuantityDiscounts", "Notes", "CustomerCompanyName", "CreditCard",
                                              "CreditCardExtra"};      //46

    public void init(ServletConfig config)

            throws ServletException {

        super.init(config);


    }


    public void destroy() {

        super.destroy();


    }

    //GET requests not supported

    public void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        doPost(req, resp);

    }



    //all requests should be POST

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        try {
            String tomcatPath = System.getProperty("catalina.base");
            if (tomcatPath == null) tomcatPath = "";
            if (tomcatPath.length() < 1) tomcatPath = "./uploads";

            String action = req.getParameter("action");
            log.debug("PhionServlet got action=" + action);
            if (action == null) {

            }  else if (action.equals("uploadcustomers")) {
                try {
                    if (null == req.getParameter("containsfile"))
                        throw new Exception("");

                    File uploadFile = null;
                    BufferedReader reader = null;
                    com.owd.core.MultipartRequest fileSource = new com.owd.core.MultipartRequest(req, ExtranetServlet.kBulkLoadSaveDir, 1024 * 1024 * 10);
                    Enumeration files = fileSource.getFileNames();

                    if (!files.hasMoreElements()) {
                        throw new Exception("No file was received!<BR>Please check your file and try again.</B>");

                    } else {

                        uploadFile = fileSource.getFile((String) files.nextElement());

                        if (uploadFile == null) {
                            throw new Exception("No file was received!<BR>Please check your file and try again.</B>");
                        } else {

                            reader = new BufferedReader(new FileReader(uploadFile));
                            uploadFile.renameTo(new File(ExtranetServlet.kBulkLoadSaveDir + File.separator + com.owd.core.OWDUtilities.getCurrentUserName() + Calendar.getInstance().getTime().getTime() + ".csv"));

                            com.owd.core.CSVReader data = new com.owd.core.CSVReader(reader, true);


                            int importColumnCount = bulkLoadColumns.length;

                            if (data.columnCount != importColumnCount) {
                                throw new Exception("<B>There must be " + importColumnCount + " columns in the file; you only provided " + data.columnCount
                                        + " columns.<BR>Please check your file and try again.</B>");

                            } else {

                                Connection cxn = null;
                                PreparedStatement pstmt = null;
                                Statement stmt = null;
                                ResultSet rs = null;

                                try {
                                    cxn = ConnectionManager.getConnection();
                                    String sql = "delete from phion_customers";
                                    stmt = cxn.createStatement();
                                    stmt.executeUpdate(sql);
                                    stmt.close();
                                } catch (Exception ex) {
                                    cxn.rollback();
                                    throw ex;
                                } finally {
                                    try {
                                        stmt.close();
                                    } catch (Exception ex) {
                                    }

                                }

                                String errorMessage = null;
                                try {


                                    String sql = "insert into phion_customers (contact_name,company_name,"
                                            + "address_1,city,state,zip,SIC_code,phion_customer_id,terms) values (?,?,?,?,?,?,?,?,?)";

                                    pstmt = cxn.prepareStatement(sql);

                                    //add new
                                    for (int row = 0; row < data.getRowCount(); row++) {

                                        if (data.getRowSize(row) < importColumnCount)
                                            throw new Exception("Row rejected - wrong number of columns");


                                        pstmt.setString(1, data.getStrValue(2, row, "") + " " + data.getStrValue(3, row, ""));

                                        pstmt.setString(2, data.getStrValue(1, row, ""));

                                        pstmt.setString(3, data.getStrValue(4, row, ""));
                                        pstmt.setString(4, data.getStrValue(5, row, ""));
                                        pstmt.setString(5, data.getStrValue(6, row, ""));
                                        pstmt.setString(6, data.getStrValue(7, row, ""));
                                        pstmt.setString(7, data.getStrValue(8, row, ""));
                                        pstmt.setInt(8, data.getIntValue(0, row, 0));
                                        pstmt.setInt(9, 0);

                                        pstmt.executeUpdate();
                                    }
                                    cxn.commit();

                                } catch (Exception ex) {

                                    ex.printStackTrace();
                                    try {
                                        cxn.rollback();
                                    } catch (Exception e) {
                                    }
                                    throw ex;
                                } finally {
                                    try {
                                        pstmt.close();
                                    } catch (Exception ex) {
                                    }
                                    try {
                                        cxn.close();
                                    } catch (Exception exce) {
                                    }

                                }
                                req.setAttribute("importReader", data);
                            }
                        }
                    }

                    req.getRequestDispatcher("/callcenter/phion/uploadcustomers.jsp").forward(req, resp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (ex.getMessage().length() > 0)
                        req.setAttribute("errormessage", ex.getMessage());
                    req.getRequestDispatcher("/callcenter/phion/uploadcustomers.jsp").forward(req, resp);
                }

            } else if (action.equals("findcustomer")) {
                String subaction = req.getParameter("findcustomertype");
                String findcriteria = req.getParameter("findcriteria");

                List findResults = new ArrayList();
                String sql = "";
                if (findcriteria == null)
                    findcriteria = "";

                if (subaction != null) {
                    if (subaction.equals("state")) {
                        sql = "select * from phion_customers where state=? order by company_name";

                    } else if (subaction.equals("zip")) {
                        sql = "select * from phion_customers where zip like ? order by company_name";
                        findcriteria = findcriteria + "%";

                    } else if (subaction.equals("company")) {
                        sql = "select * from phion_customers where company_name like ? order by company_name";
                        findcriteria = findcriteria + "%";
                    }

                    Connection cxn = null;
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;

                    try {
                        cxn = ConnectionManager.getConnection();

                        pstmt = cxn.prepareStatement(sql);
                        pstmt.setString(1, findcriteria);

                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            findResults.add("<TD WIDTH=1%><A HREF=\"phion.jsp?action=pickcustomer&customerID=" +
                                    rs.getString("id") + "\"><B>CHOOSE</B></A>&nbsp;</TD><TD WIDTH=99% ALIGN=LEFT><B>" +
                                    rs.getString("company_name") + "</B>&nbsp;" +
                                    rs.getString("contact_name") + "&nbsp;//&nbsp;" +
                                    rs.getString("address_1") + "&nbsp;//&nbsp;" +
                                    rs.getString("city") + ",&nbsp;" + rs.getString("state") +
                                    "&nbsp;" + rs.getString("zip") + "</TD>");
                        }
                        req.setAttribute("findresults", findResults);

                    } catch (Exception ex) {
                        req.setAttribute("errormessage", ex.getMessage());
                    } finally {
                        try {
                            rs.close();
                        } catch (Exception ex) {
                        }
                        try {
                            pstmt.close();
                        } catch (Exception ex) {
                        }
                        try {
                            cxn.close();
                        } catch (Exception ex) {
                        }

                    }
                    req.getRequestDispatcher("/callcenter/phion/index.jsp").forward(req, resp);

                }


            } else if (action.equals("pickcustomer")) {
                String customerID = req.getParameter("customerID");
                if (customerID == null) customerID = "0";
                String sql = "select * from phion_customers where id=?";
                Connection cxn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                List paymentTypeList = new ArrayList();
                paymentTypeList.add("Credit Card");
            //    paymentTypeList.add("COD");
                Order order = new Order("179");


                try {

                    if (!(customerID.equals("0"))) {
                        cxn = ConnectionManager.getConnection();

                        pstmt = cxn.prepareStatement(sql);
                        pstmt.setInt(1, new Integer(customerID).intValue());

                        rs = pstmt.executeQuery();

                        order.ship_operator = "HSE";
                        if (rs.next()) {

                            order.getShippingInfo().comments = "PC=" + rs.getString("phion_customer_id");
                            if ("1".equals(rs.getString("terms"))) {
                                paymentTypeList.add("Purchase Order");
                            }

                            order.order_type = rs.getString("SIC_code");

                            order.setBillingAddress(new Address(rs.getString("company_name"),
                                    rs.getString("address_1"),
                                    "", rs.getString("city"),
                                    rs.getString("state"),
                                    rs.getString("zip"),
                                    "USA"));

                            order.setBillingContact(new Contact(rs.getString("contact_name"),
                                    "", "", "", ""));

                            order.getShippingInfo().setShippingContact(Contact.createFromStorableString(order.getBillingContact().toStorableString()));
                            order.getShippingInfo().setShippingAddress(Address.createFromStorableString(order.getBillingAddress().toStorableString()));


                        }
                    }
                    order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                    order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
                    order.backorder_rule = OrderXMLDoc.kBackOrderAll;


                    req.getSession(true).setAttribute("currentorder", order);
                    req.getSession(true).setAttribute("paymentTypeList", paymentTypeList);
                    req.getSession(true).setAttribute("customerID", customerID);


                    req.getRequestDispatcher("/callcenter/phion/startorder.jsp").forward(req, resp);


                } catch (Exception ex) {
                    req.setAttribute("errormessage", ex.getMessage());
                    req.getRequestDispatcher("/callcenter/phion/index.jsp").forward(req, resp);

                } finally {
                    try {
                        rs.close();
                    } catch (Exception ex) {
                    }
                    try {
                        pstmt.close();
                    } catch (Exception ex) {
                    }
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }

                }

            } else if (action.equals("submitorder")) {
                Order order = (Order) req.getSession(true).getAttribute("currentorder");

                order.total_shipping_cost = 0;


                int total_count = 0;



                order.skuList = new Vector();

                //filling line items to the skuList
                try {



                    order.skuList = new Vector();

                    Iterator itl = getCurrentItemList(req, true).iterator();
                    while (itl.hasNext()) {
                        Hashtable item = (Hashtable) itl.next();
                        log.debug("checking item "+(String) item.get("inventory_num"));
                        try {
                            int qty = new Integer(req.getParameter("quant_" + item.get("inventory_num"))).intValue();
                            log.debug("checking item qty "+req.getParameter("quant_" + item.get("inventory_num"))+":"+qty);
                            if (qty > 0)
                            {
                                PhionUtilities.getInstance().addItem(order,
                                      (String) item.get("inventory_num"), "" + qty,
                                                "" + OWDUtilities.roundFloat(new Float(req.getParameter("price_" + item.get("inventory_num"))).floatValue()),
                                                "0.00", (String)item.get("description"), "","");

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                    order.discount = 0.00f;

                    if (order.skuList.size() < 1)
                        throw new Exception("You must choose at least one product to continue.");

                    boolean shippingIsFree = false;
                    /*
order.recalculateBalance();
if (order.total_product_cost >= 500.00f)
{
    shippingIsFree = true;
    order.discount = -1 * OWDUtilities.roundFloat(order.total_product_cost * 0.10f);


} else if (order.total_product_cost >= 250.00f)
{
    shippingIsFree = true;
    order.discount = -1 * OWDUtilities.roundFloat(order.total_product_cost * 0.05f);

}
if (order.total_product_cost >= 95.00f)
{
    shippingIsFree = true;

}
*/


                    order.getBillingContact().setName(req.getParameter("bill_billingName"));
                    order.getBillingContact().email = req.getParameter("bill_email");
                    order.getBillingContact().phone = req.getParameter("bill_phone_num");

                    if ("".equals(order.getBillingContact().phone.trim())) {
                        throw new Exception("Customer phone number is a required field. Please enter the phone number before continuing");
                    }
                    order.getBillingAddress().company_name = req.getParameter("bill_companyName");
                    order.getBillingAddress().address_one = req.getParameter("bill_address1");
                    order.getBillingAddress().address_two = req.getParameter("bill_address2");
                    order.getBillingAddress().city = req.getParameter("bill_city");
                    order.getBillingAddress().state = req.getParameter("bill_state");
                    order.getBillingAddress().zip = req.getParameter("bill_zip");


                    if ("1".equals(req.getParameter("bill_to_shipping"))) {
                        order.getShippingInfo().setShippingContact(Contact.createFromStorableString(order.getBillingContact().toStorableString()));
                        order.getShippingInfo().setShippingAddress(Address.createFromStorableString(order.getBillingAddress().toStorableString()));
                    } else {
                        order.getShippingContact().setName(req.getParameter("ship_shippingName"));

                        order.getShippingAddress().company_name = req.getParameter("ship_companyName");
                        order.getShippingAddress().address_one = req.getParameter("ship_address1");
                        order.getShippingAddress().address_two = req.getParameter("ship_address2");
                        order.getShippingAddress().city = req.getParameter("ship_city");
                        order.getShippingAddress().state = req.getParameter("ship_state");
                        order.getShippingAddress().zip = req.getParameter("ship_zip");
                    }


                    order.cc_num = req.getParameter("cc_num");
                    order.cc_exp_mon = new Integer(req.getParameter("cc_exp_mon")).intValue();
                    order.cc_exp_year = new Integer(req.getParameter("cc_exp_year")).intValue();
                    order.po_num = req.getParameter("po_num");
                    if (order.po_num == null) order.po_num = "";
                    order.ship_operator = req.getParameter("ship_operator");
                    order.bill_cc_type = req.getParameter("bill_cc_type");
                    if (order.bill_cc_type.equals("CK")) {
                        //COD

                    } else if (order.bill_cc_type.equals("CC")) {
                        //CC
                        boolean validCard = false;
                        try {
                            validCard = CreditCard.isValid(CreditCard.parseDirtyLong(order.cc_num));
                            if (!validCard) {
                                throw new Exception("Credit card number is invalid. Please check the card number and try again.");
                            }
                        } catch (Exception ex) {
                            throw new Exception("Credit card number is invalid. Please check the card number and try again.");
                        }

                    } else {
                        //PO

                    }


                    order.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
                    OrderRater rater = null;

                    float firstShipRate = 0.00f;
                    Vector shipRates = new Vector();
                    Vector shipTypes = new Vector();
                    Vector shipRefs = new Vector();

                    Vector shipBadRates = new Vector();
                    Vector shipBadTypes = new Vector();
                    Vector shipBadRefs = new Vector();
                    rater = new OrderRater(order);
                    rater.setCalculateKitWeights(true);
                    rater.rate("DC6");


                    java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");

                    for (PackageRate currShipment:rater.theResponse) {




                        if (currShipment.getErrorCode()==0) {

                            float shiprate = new Float(currShipment.getFinalRate()).floatValue();
                            if (firstShipRate == 0.00f) {
                                firstShipRate = shiprate;

                            }
                            shipRates.addElement(decform.format(OWDUtilities.roundFloat((shippingIsFree ? shiprate - firstShipRate : shiprate))));
                            shipTypes.addElement(currShipment.getMethodName());
                            shipRefs.addElement(currShipment.getMethodCode());


                        } else {
                            shipBadRates.addElement(decform.format(OWDUtilities.roundFloat(-1.00f)));
                            shipBadTypes.addElement(currShipment.getMethodName());
                            shipBadRefs.addElement(currShipment.getErrorMessage());


                        }
                    }


                    req.getSession(true).setAttribute("shipCosts", shipRates);
                    req.getSession(true).setAttribute("shipTypes", shipTypes);
                    req.getSession(true).setAttribute("shipRefs", shipRefs);
                    req.getSession(true).setAttribute("shipBadCosts", shipBadRates);
                    req.getSession(true).setAttribute("shipBadTypes", shipBadTypes);
                    req.getSession(true).setAttribute("shipBadRefs", shipBadRefs);


                    req.getRequestDispatcher("/callcenter/phion/revieworder.jsp").forward(req, resp);

                } catch (Exception e) {
                    e.printStackTrace();
                    req.setAttribute("errormessage", e.getMessage());
                    req.getRequestDispatcher("/callcenter/phion/startorder.jsp").forward(req, resp);

                }

            } else if (action.equals("finishorder")) {
                try {

                    Vector shipRefs = (Vector) req.getSession(true).getAttribute("shipRefs");
                    Vector shipTypes = (Vector) req.getSession(true).getAttribute("shipTypes");
                    Vector shipCosts = (Vector) req.getSession(true).getAttribute("shipCosts");
                    Order order = (Order) req.getSession(true).getAttribute("currentorder");
                    order.total_shipping_cost = 0.00f;

                    if (order == null)
                        throw new Exception("No valid order found. Please begin again.");

                    String shipChoice = req.getParameter("shipping");
                    if (shipChoice == null)
                        throw new Exception("You must choose a shipping method");

                    if (shipRefs.contains(shipChoice)) {
                        int shipIndex = shipRefs.indexOf(shipChoice);
                        order.total_shipping_cost = new Float((String) shipCosts.elementAt(shipIndex)).floatValue();
                        order.getShippingInfo().setShipOptions((String) shipTypes.elementAt(shipIndex), "Prepaid", "");
                    } else {
                        throw new Exception("You must choose a shipping method");

                    }
                    order.recalculateBalance();


                    String freeShippingFlag = req.getParameter("free_shipping_cb");

                    if ("1".equals(freeShippingFlag))
                        order.total_shipping_cost = 0.00f;

                    order.recalculateBalance();

                    order.getShippingInfo().ss_cod = "0";
                    order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
                    order.is_paid = 0;
                    order.check_num = "";
                    order.po_num = "";
                    order.paid_amount = 0.00f;
                    order.recalculateBalance();


                   if (order.bill_cc_type.equals("CC")) {
                        //CC
                        order.po_num = "";


                    } else if (order.bill_cc_type.equals("PP")) {
                        //CC
                        order.bill_cc_type = "CK";
                        order.check_num = "999999";
                        order.paid_amount = order.total_order_cost;
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                        order.is_paid = 1;
                        order.po_num = "Prepaid";


                    } else if (order.bill_cc_type.equals("30")) {
                        //CC
                        order.bill_cc_type = "CK";
                        order.check_num = "999998";
                        order.paid_amount = order.total_order_cost;
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                        order.is_paid = 1;
                        order.po_num = "30 Day Terms";


                    } else {
                        //PO
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                        order.is_paid = 1;
                        order.paid_amount = order.total_order_cost;
                    }

                     order.is_future_ship = 1;
                    // order.forcePayment = true;
                    order.recalculateBalance();
                    String old_paytype = order.bill_cc_type;
                    String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);
                    if (reference == null) {
                        order.bill_cc_type = old_paytype;
                        throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                    }
                    String customerID = (String) req.getSession(true).getAttribute("customerID");

                    updatePhionCustomer(customerID, order.getBillingContact(), order.getBillingAddress());
                    req.getRequestDispatcher("/callcenter/phion/confirmorder.jsp").forward(req, resp);

                } catch (Exception e) {
                    e.printStackTrace();

                    req.setAttribute("errormessage", e.getMessage());
                    req.getRequestDispatcher("/callcenter/phion/revieworder.jsp").forward(req, resp);

                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void updatePhionCustomer(String customerID, Contact contactInfo, Address addressInfo) {
        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;

        try {

            cxn = ConnectionManager.getConnection();
            if ("0".equals(customerID)) {
//new customer
                String createNewPhionCustomerSQL =
                        "insert into phion_customers (contact_name,company_name,address_1,"
                        + "address_2,city,state,zip,terms,SIC_code,phion_customer_id)values ("
                        + "?,?,?,?,?,?,?,?,?,?)";

                stmt = cxn.prepareStatement(createNewPhionCustomerSQL);
                stmt.setString(1, contactInfo.getName());
                stmt.setString(2, addressInfo.company_name);
                stmt.setString(3, addressInfo.address_one);
                stmt.setString(4, addressInfo.address_two);
                stmt.setString(5, addressInfo.city);
                stmt.setString(6, addressInfo.state);
                stmt.setString(7, addressInfo.zip);
                stmt.setInt(8, 0);
                stmt.setString(9, "OWD");
                stmt.setInt(10, 0);

                stmt.executeUpdate();
                cxn.commit();

            } else {
//old customer to update

                String updatePhionCustomerSQL =
                        "update phion_customers set contact_name=?," +
                        "company_name=?," +
                        "address_1=?," +
                        "address_2=?,city=?,state=?,zip=? where id=?";

                stmt = cxn.prepareStatement(updatePhionCustomerSQL);
                stmt.setString(1, contactInfo.getName());
                stmt.setString(2, addressInfo.company_name);
                stmt.setString(3, addressInfo.address_one);
                stmt.setString(4, addressInfo.address_two);
                stmt.setString(5, addressInfo.city);
                stmt.setString(6, addressInfo.state);
                stmt.setString(7, addressInfo.zip);

                stmt.executeUpdate();
                cxn.commit();
            }
        } catch (Exception ex) {
            log.debug("Phion Customer Update failed!");
            ex.printStackTrace();
        } finally {

            try {
                cxn.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }

        }

    }


    public static List getCurrentItemList(HttpServletRequest req, boolean forceUpdate) throws Exception {
        if (null == req.getSession(true).getAttribute("itemList") || forceUpdate) {
            ArrayList fieldList = new ArrayList();
            fieldList.add("inventory_num");
            fieldList.add("description");
            fieldList.add("price");
            fieldList.add("keyword");
            req.getSession(true).setAttribute("itemList", Inventory.getItemsForClientIDx("179", fieldList));

        }

        return (List) req.getSession(true).getAttribute("itemList");

    }


}

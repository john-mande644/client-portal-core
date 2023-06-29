package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;


public class BelAmiPromoServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    private int[] fieldLengths = {4, 15, 1, 18, 8, 1, 20, 25, 16, 2, 7, 6, 1, 2, 8};

    /*

        Columns:



        1	Acct

        3	last Name

        4	first name

        5	middle initial

        6	add 1

        7	add2

        8	city

        9	state

        10	zip

        11	source

    */



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


        String subAction = ExtranetServlet.getStringParam(req, "basub", "");

        String message = "";

        StringBuffer form = new StringBuffer();

        form.append("<HTML><fontx size=-1><B>Bel Ami Subscription Promo .PRN File Loader</B><P>");


        if (subAction.equals("load")) {

            ////log.debug("loading");

            try {

                File uploadFile = null;

                BufferedReader reader = null;

                com.owd.core.MultipartRequest fileSource = new com.owd.core.MultipartRequest(req, ExtranetServlet.kBulkLoadSaveDir, 1024 * 1024);

                Enumeration files = fileSource.getFileNames();


                if (!files.hasMoreElements()) {

                    form.append("<fontx size=-1 color=\"red\"><B>No file was received!<BR>Please check your file and try again.</B></fontx>");

                    ////log.debug("no file found");

                } else {


                    uploadFile = fileSource.getFile((String) files.nextElement());


                    if (uploadFile == null) {

                        ////log.debug("file is null");

                        form.append("<fontx size=-1 color=\"red\"><B>No file was received!<BR>Please check your file and try again.</B></fontx>");

                    } else {

                        ////log.debug("getting file reader");

                        reader = new BufferedReader(new FileReader(uploadFile));



                        ////log.debug("parsing");

                        com.owd.core.PRNReader orders = new com.owd.core.PRNReader(reader, false, fieldLengths);

                        ////log.debug("parsed");

                        if (orders.rowCount < 1) {

                            form.append("<fontx size=-1 color=\"red\"><B>There were no rows in your file.<BR>Please check your file and try again.</B></fontx>");


                        } else {

                            form.append("<fontx size=-1 ><B>Promo Loader Report</B><BR><OL>");


                            for (int row = 0; row < orders.getRowCount(); row++) {

                                message = "Unspecified Error";


                                if (orders.getRowSize(row) != 15) {

                                    message = "Row not processed; 15 columns should be present, " + orders.getRowSize(row) + " columns found.";

                                } else {

                                    try {

                                        Order anOrder = new Order("115");

                                        //anOrder.testing = true;



                                        anOrder.order_refnum = orders.getStrValue(1, row, "");

                                        anOrder.actual_order_date = com.owd.core.OWDUtilities.getSQLDateTimeForToday();

                                        anOrder.payment_status = com.owd.core.xml.OrderXMLDoc.kPaymentStatusClientManaged;

                                        anOrder.backorder_rule = com.owd.core.xml.OrderXMLDoc.kPartialShip;

                                 //       anOrder.prt_pack_reqd = 0;

                                  //      anOrder.prt_invoice_reqd = 1;

                                        anOrder.setBillingAddress(new com.owd.core.business.Address(".",

                                                orders.getStrValue(6, row, ""),

                                                orders.getStrValue(7, row, ""),

                                                orders.getStrValue(8, row, ""),

                                                orders.getStrValue(9, row, ""),

                                                orders.getStrValue(10, row, ""),

                                                "USA"));


                                        anOrder.getShippingInfo().setShippingAddress(anOrder.getBillingAddress());

                                        anOrder.setBillingContact(new com.owd.core.business.Contact(orders.getStrValue(4, row, "") + " " + orders.getStrValue(5, row, "") + " " + orders.getStrValue(3, row, ""),

                                                "", "",

                                                "", ""));


                                        anOrder.getShippingInfo().setShippingContact(anOrder.getBillingContact());

                                        anOrder.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");

                                        anOrder.order_type = "Imported";

                                        anOrder.ship_call_date = com.owd.core.OWDUtilities.getSQLDateForToday();


                                        anOrder.addLineItem("V-PROMO", "1", "0", "0", " Kisses From BelAmi", "", "");


                                        String reference = anOrder.saveNewOrder(OrderUtilities.kRequirePayment, false);


                                        if (anOrder.last_error.indexOf("reference already exists") >= 0) {

                                            //duplicate

                                            message = "Order not imported; order reference already exists";

                                        } else {

                                            if (reference == null) {

                                                //order not saved

                                                //////log.debug("order not saved");

                                                message = "Order not saved - possible reason is " + anOrder.last_error;

                                            } else {


                                                if (anOrder.is_future_ship == 1) {

                                                    //order on hold

                                                    //////log.debug("order on hold");



                                                    message = "Order on hold (" + anOrder.order_refnum + ") : " + anOrder.hold_reason;

                                                } else if (anOrder.backorder_order_num != null) {

                                                    //order on hold

                                                    //////log.debug("order backordered");



                                                    message = "Order backordered ";

                                                } else {

                                                    message = "Order imported : Reference " + anOrder.order_refnum + " OWD: " + anOrder.orderNum;

                                                }

                                            }

                                        }

                                    } catch (Exception ex) {


                                        ex.printStackTrace();

                                        message = "Import error - report to Stewart : " + ex.getMessage();

                                    }//end try


                                }//end row size check


                                form.append("<LI> " + message);

                                resp.getWriter().write(form.toString());

                                resp.getWriter().flush();

                                form = new StringBuffer();

                            }//end for each row loop


                            form.append("</OL>");

                        }//end if check


                        if (uploadFile.exists())

                            uploadFile.delete();


                    }//end if check

                }//end if check


            } catch (Exception ex) {

                ex.printStackTrace();

                form.append("<fontx size=-1 color=\"red\"><B>Could not process file : " + ex.getMessage() + " </B></fontx><HR>");


            }

        }//load action


        form.append("<P><HR>Load New Bel Ami Promo file (*.PRN)<P>");


        form.append("<FORM METHOD=POST  ENCTYPE=\"MULTIPART/FORM-DATA\" ACTION=\"/webapps/belamipromo/loader.jsp?basub=load\">");


        form.append("Select the Browse button to select the file from your local drive:<BR>");


        form.append("<fontx SIZE=\"2\"><INPUT TYPE=\"FILE\" size=\"60\" NAME=\"UploadFile\"><BR>");

        form.append("<fontx SIZE=\"-2\">Note: If a button labeled &quot;Browse...&quot; does not appear above, then your web browser does not support File Upload.<BR>");

        form.append("In that case, please upgrade your browser to a newer version.<P>");

        form.append("<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=\"Load Bel Ami Promo file\"></FORM>");


        try {

            resp.getWriter().write(form.toString() + "</HTML>");

        } catch (Exception ex) {

            ex.printStackTrace();

        }


    }


}

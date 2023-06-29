package com.owd.web.batchimporters.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.web.batchimporters.NGLUploadOrdersData;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 23, 2007
 * Time: 11:41:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class OWDNGLUploadOrderServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


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
            if (req.getSession(true).getAttribute("inprogress") == null) {
                try {
                    String tomcatPath = System.getProperty("catalina.base");
                    if (tomcatPath == null) tomcatPath = "";
                    if (tomcatPath.length() < 1) tomcatPath = "./uploads";

                    if (req.getSession(true).getAttribute("resultsList") == null) {


                        com.owd.core.MultipartRequest fileSource = new com.owd.core.MultipartRequest(req, tomcatPath + File.separator + "uploads", 1024 * 1024 * 10);
                        Enumeration files = fileSource.getFileNames();


                        File uploadFile = null;

                        BufferedReader reader = null;

                        if (!files.hasMoreElements()) {
                            throw new Exception("No file was received!<BR>Please check your file and try again.</B>");

                        } else {
                            String fileName = (String) files.nextElement();

                            uploadFile = fileSource.getFile(fileName);

                            if (uploadFile == null) {
                                throw new Exception("No file was received!<BR>Please check your file and try again.</B>");
                            } else {

                                reader = new BufferedReader(new FileReader(uploadFile));


                                uploadFile.renameTo(new File(tomcatPath + File.separator + "uploads" + File.separator + com.owd.core.OWDUtilities.getCurrentUserName() + Calendar.getInstance().getTime().getTime() + ".tab"));


                                req.getSession(true).setAttribute("processed", "" + 0);
                                req.getSession(true).setAttribute("toprocess", "" + 0);


                                String cid = fileSource.getParameter("client_id");
                                if (!(Client.getClientIDForUser(req.getUserPrincipal().getName()).equals("0") || Client.getClientIDForUser(req.getUserPrincipal().getName()).equals(cid))) {
                                    throw new Exception("Client ID not authorized for logged-in user");

                                } else {
                                    req.getSession(true).setAttribute("internal_current_client_id", cid);
                                }
                                String br = fileSource.getParameter("backorder_rule");

                                log.debug("cid=" + cid + ";br=" + br);

                                importProcessorThread processor = new importProcessorThread();
                                processor.reader = reader;
                                processor.theSession = req.getSession(true);
                                processor.backorder_rule = br;
                                processor.clientID = cid;

                                processor.start();

                                req.getSession(true).setAttribute("inprogress", "true");


                                resp.sendRedirect("/webapps/clienttools/batchimporters/ngl/processprogress.jsp");


                            }
                        }

                    }

                } catch (Exception ex) {
                    req.setAttribute("errormessage", ex.getMessage());
                    req.getSession(true).removeAttribute("inprogress");

                    ex.printStackTrace();
                }

                if (req.getSession(true).getAttribute("inprogress") == null)
                    resp.sendRedirect("/webapps/clienttools/batchimporters/ngl/orderuploads.jsp");
            } else {
                resp.sendRedirect("/webapps/clienttools/batchimporters/ngl/processprogress.jsp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public class importProcessorThread implements Runnable {

        public BufferedReader reader = null;
        public HttpSession theSession = null;
        public List resultsList = new ArrayList();
        public String backorder_rule = null;
        public String clientID = null;


        public void start() {
            Thread t = new Thread(this, "New thread");
            t.start();

        }

        public void run() {

            com.owd.core.TabReader data = null;

            try {
                data = new com.owd.core.TabReader(reader, true);
            } catch (Exception ex) {


            }

            NGLUploadOrdersData dataHandler = new NGLUploadOrdersData();
            dataHandler.init(data);

            theSession.setAttribute("toprocess", "" + dataHandler.getOrderCount());

            for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
                try {
                    resultsList.add(processOrder(dataHandler, clientID, backorder_rule, orderIndex));
                    theSession.setAttribute("processed", "" + (orderIndex + 1));
                    //record success
                } catch (Exception ex) {
                    //record error
                    //ex.printStackTrace();
                    log.debug("Uncaught error at row " + orderIndex + ":" + ex.getMessage());
                }


            }
            theSession.setAttribute("resultList", resultsList);
            theSession.removeAttribute("inprogress");
        }


        private List processOrder(NGLUploadOrdersData dataHandler, String clientID, String backorderRule, int orderIndex) {
            //returns list of two elements - com.owd.api.client Order ID and response
            List response = new ArrayList();
            //add new
            response.add(dataHandler.getOrderReference(orderIndex));

            try {
                Order order = new Order(clientID);
                order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.backorder_rule = OrderXMLDoc.kBackOrderAll;

                //order.is_future_ship=1;
                dataHandler.loadOrder(order, orderIndex);

                 order.order_type="NGL Batch Upload";
                //fix payment status, do more validation stuff?
                order.recalculateBalance();
                order.is_paid = 1;
                log.debug("IS PAID:"+order.is_paid);
                if (order.is_paid == 1) {
                    //paid for - push it through!
                    order.bill_cc_type = "CK";
                    order.check_num = "999999";
                    order.paid_amount = order.total_order_cost;
                    order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                    order.is_paid = 1;
                } else {
                    //not paid - needs payment
                    if (order.cc_num.trim().length() > 1) {
                        order.bill_cc_type = "CC";
                        order.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
                    } else {
                        order.bill_cc_type = "CK";
                        order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

                        order.is_future_ship = 1;
                    }

                    order.is_paid = 0;
                    order.check_num = "";
                    order.paid_amount = 0.00f;
                }

                //figure out how to report errors

                //if(!(order.getShippingAddress().isUS()))
                	//order.is_future_ship=0;


                String reference = order.saveNewOrder(OrderUtilities.kRequirePayment, false);

                if (reference == null) {
                    throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                }

                response.add("Confirmed as OWD Reference: " + reference);
                if (order.is_backorder == 1)
                    response.set(1, response.get(1) + "; Backorder created");
                if (order.is_future_ship == 1)
                    response.set(1, response.get(1) + "; Order placed On Hold");
                if (order.backorder_order_num.length() > 0)
                    response.set(1, response.get(1) + "; Backorder created as OWD order reference " + order.backorder_order_num);

            } catch (Exception
                    ex) {
                //ex.printStackTrace();
                response.add("<font color=red>" + ex.getMessage() + "</font>");
            } finally {


            }
            return response;
        }


    }

}

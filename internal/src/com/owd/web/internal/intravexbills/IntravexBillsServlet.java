package com.owd.web.internal.intravexbills;

import com.owd.core.DelimitedReader;
import com.owd.core.TabReader;
import com.owd.hibernate.generated.IntravexEbill;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.MultipartRequest;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.*;
import com.owd.web.internal.servlet.HomeServlet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 24, 2003
 * Time: 10:07:17 AM
 * To change this template use Options | File Templates.
 */
public class IntravexBillsServlet extends HttpServlet {
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

        String action = req.getParameter("act");
        //log.debug("In FedEx Billing Servlet");
        if (action == null) action = "";

        try {
            if ("update".equals(action)) {
                String item_id = req.getParameter("item_id");
                String client_id = req.getParameter("client_id");
                String billTo = req.getParameter("bill_to");
                String order_num = req.getParameter("order_num");
                String resolveNotes = req.getParameter("resolve_notes");
                if (client_id.equals("0")) client_id = "55";

                System.out.println("1");
                try {
                    Session sess = HibernateSession.currentSession();

                    IntravexEbill item = ((IntravexEbill) sess.load(IntravexEbill.class, new Integer(item_id)));
                    OwdClient client = ((OwdClient) sess.load(OwdClient.class, new Integer(client_id)));

                    System.out.println("2");
                    //if order num is set varify it belongs to selected client
                    if(!order_num.equals("")) {
                        Criteria crit = sess.createCriteria(OwdOrder.class);
                        crit.setMaxResults(1);
                        crit.add(Restrictions.eq("orderNum", order_num));
                        System.out.println("3");
                        crit.add(Restrictions.eq("clientFkey", Integer.parseInt(client_id)));
                        System.out.println("4");
                        List orderList = crit.list();
                        System.out.println("5");
                        if (orderList.size() > 0) {
                            //found order for client or order
                            System.out.println("6");
                            OwdOrder order = (OwdOrder) orderList.get(0);
                            item.setOwdOrder(order);
                        } else {

                            throw new Exception("Order number entered but no order found for specified client");
                        }
                    }
                    //log.debug("Loaded UPS Bill Client selected "+client_id+" : "+client.getCompanyName());
                    item.setBillOwd(new Integer("OWD".equals(billTo) ? 1 : 0));
                    item.setBillClient(new Integer("CLIENT".equals(billTo) ? 1 : 0));
                    item.setResolveNotes(resolveNotes);
                    item.setOwdClient(client);

                    System.out.println("7");
                     if ((item.getBillOwd().intValue() + item.getBillClient().intValue() == 1) && item.getOwdClient().getClientId().intValue()>0) {
                        item.setNeedsReview(null);
                    }


                    sess.saveOrUpdate(item);
                    sess.flush();
                    HibUtils.commit(sess);

                    IntravexProcessorThread.applyDiscountsToUnreviewedRecords( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection());

                    if ((item.getBillOwd().intValue() + item.getBillClient().intValue() == 1) && item.getOwdClient().getClientId().intValue()>0) {
                        item.setNeedsReview(new Integer(0));
                    }
                         sess.saveOrUpdate(item);
                    sess.flush();
                    HibUtils.commit(sess);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute("errormessage", ex.getMessage());
                    req.getRequestDispatcher("bill_detail.jsp").forward(req, resp);
                } finally {
                    HibernateSession.closeSession();
                }


                req.getRequestDispatcher("billsummary.jsp").forward(req, resp);


            } else if ("summary".equals(action)) {


                req.getRequestDispatcher("billsummary.jsp").forward(req, resp);


            } else {
                //log.debug("UPS Bill servlet reports inprogress attribute as:" + req.getSession(true).getAttribute("inprogress"));
                if (req.getSession(true).getAttribute("inprogress") == null) {
                    try {
                        String tomcatPath =System.getProperty("java.io.tmpdir");
                        log.debug(tomcatPath);
                        if (tomcatPath == null) tomcatPath = "";
                        if (tomcatPath.length() < 1) tomcatPath = "./uploads";

                        log.debug(tomcatPath);

                        com.owd.core.MultipartRequest fileSource = new com.owd.core.MultipartRequest(req, tomcatPath, 1024 * 1024 * 10);
                        Enumeration files = fileSource.getFileNames();


                        File uploadFile = null;

                        DelimitedReader reader = null;

                        if (!files.hasMoreElements()) {
                            throw new Exception("No file was received!<BR>Please check your file and try again.</B>");

                        } else {
                            String fileName = (String) files.nextElement();

                            uploadFile = fileSource.getFile(fileName);

                            if (uploadFile == null) {
                                throw new Exception("No file was received!<BR>Please check your file and try again.</B>");
                            } else {

                                //log.debug("In UPS Billing Servlet - getting file reader");
                                 reader = new TabReader(new BufferedReader(new FileReader(uploadFile)),false);

                              //  reader = new BufferedReader(new FileReader(uploadFile));
                                uploadFile.renameTo(new File(tomcatPath + File.separator + com.owd.core.OWDUtilities.getCurrentUserName() + Calendar.getInstance().getTime().getTime() + ".tab"));



                                req.getSession(true).setAttribute("processed", "" + 0);
                                req.getSession(true).setAttribute("toprocess", "" + 0);
                                req.getSession(true).setAttribute("updateMessage", "Loading invoice file");


                                req.getSession(true).setAttribute("ups_upload_username", OWDUtilities.getCurrentUserName());
                                req.getSession(true).setAttribute("ups_upload_filename", uploadFile.getName());
                                //log.debug(uploadFile.getName());

                                UploadIntravexBillData uploader = new UploadIntravexBillData();
                                uploader.init(reader,uploadFile.getName(),OWDUtilities.getCurrentUserName());

                                IntravexProcessorThread processor = new IntravexProcessorThread();

                                processor.theSession = req.getSession(true);

                                processor.uploader = uploader;
                              //  HibUtils.commit(HibernateSession.currentSession());
                                processor.start();

                                req.getSession(true).setAttribute("inprogress", "true");


                                resp.sendRedirect("/internal/intravexbills/processprogress.jsp");


                            }
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                        req.setAttribute("errormessage", ex.getMessage());
                        req.getSession(true).removeAttribute("inprogress");


                    }

                    if (req.getSession(true).getAttribute("inprogress") == null)
                        resp.sendRedirect("/internal/intravexbills/index.jsp");
                } else {
                    resp.sendRedirect("/internal/intravexbills/processprogress.jsp");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("errormessage", ex.getMessage());
        }
    }

    



}

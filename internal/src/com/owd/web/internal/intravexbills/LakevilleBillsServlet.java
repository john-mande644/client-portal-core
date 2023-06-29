package com.owd.web.internal.intravexbills;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.MultipartRequest;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.LakevilleEbill;
import com.owd.hibernate.HibUtils;
import com.owd.web.internal.servlet.HomeServlet;
import org.hibernate.Session;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.math.*;
import java.lang.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 24, 2003
 * Time: 10:07:17 AM
 * To change this template use Options | File Templates.
 */
public class LakevilleBillsServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();

    static final int colTypeString = 0;
    static final int colTypeInteger = 1;
    static final int colTypeFloat = 2;
    static final int colTypeDate = 3;

    static final int[] colTypes = {colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,   //9
                                   colTypeDate,
                                   colTypeString,
                                   colTypeInteger,
                                   colTypeFloat,      //
                                   colTypeFloat,
                                   colTypeFloat,
                                   colTypeFloat,
                                   colTypeFloat,
                                   colTypeFloat,
                                   colTypeInteger,
                                   colTypeInteger,
                                   colTypeInteger};

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
        //log.debug("In Lakeville Billing Servlet");
        if (action == null) action = "";

        try {
            if ("update".equals(action)) {
                String item_id = req.getParameter("item_id");
                String client_id = req.getParameter("client_id");
                String billTo = req.getParameter("bill_to");
                if (client_id.equals("0")) client_id = "55";

                try {
                    Session sess = HibernateSession.currentSession();

                    LakevilleEbill item = ((LakevilleEbill) sess.load(LakevilleEbill.class, new Integer(item_id)));
                    OwdClient client = ((OwdClient) sess.load(OwdClient.class, new Integer(client_id)));
                  item.setClientFkey(client.getClientId());
                    if (item.getClientFkey().intValue()>0) {
                        item.setIsConfirmed(new Integer(1));
                        item.setAdjustmentCharge(new BigDecimal(item.getNet().doubleValue()*(client.getClientId().intValue()==158?1.25:1.31)));
                        item.setPredictedCost(new BigDecimal(0.00));
                        item.setFinalCharge(new BigDecimal(item.getNet().doubleValue()*(client.getClientId().intValue()==158?1.25:1.31)));
                    }

                    sess.saveOrUpdate(item);
                    sess.flush();
                    HibUtils.commit(sess);;

                } catch (Exception ex) {
                    ex.printStackTrace();

                    req.getRequestDispatcher(req.getContextPath()+"/lakeville/bill_detail.jsp").forward(req, resp);
                } finally {
                    HibernateSession.closeSession();
                }


                req.getRequestDispatcher(req.getContextPath()+"/lakeville/billsummary.jsp").forward(req, resp);


            } else if ("summary".equals(action)) {


                req.getRequestDispatcher(req.getContextPath()+"/lakeville/billsummary.jsp").forward(req, resp);


            } else {
                //log.debug("Lakeville Bill servlet reports inprogress attribute as:" + req.getSession(true).getAttribute("inprogress"));
                if (req.getSession(true).getAttribute("inprogress") == null) {
                    try {

                        //log.debug("In Lakeville Billing Servlet - loading file");
                        MultipartRequest fileSource = new MultipartRequest(req, HomeServlet.kBulkLoadSaveDir, 1024 * 1024 * 10);
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

                                //log.debug("In Lakeville Billing Servlet - getting file reader");
                                reader = new BufferedReader(new FileReader(uploadFile));
                                uploadFile.renameTo(new File(HomeServlet.kBulkLoadSaveDir + File.separator + OWDUtilities.getCurrentUserName() + Calendar.getInstance().getTime().getTime() + ".tab"));


                                req.getSession(true).setAttribute("processed", "" + 0);
                                req.getSession(true).setAttribute("toprocess", "" + 0);
                                req.getSession(true).setAttribute("updateMessage", "");


                                req.getSession(true).setAttribute("lakeville_upload_username", req.getRemoteUser());
                                req.getSession(true).setAttribute("lakeville_upload_filename", uploadFile.getName());
                                //log.debug(uploadFile.getName());

                                importProcessorThread processor = new importProcessorThread();
                                processor.reader = reader;
                                processor.theSession = req.getSession(true);

                                processor.start();

                                req.getSession(true).setAttribute("inprogress", "true");


                                resp.sendRedirect(req.getContextPath()+"/lakeville/processprogress.jsp");


                            }
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                        req.setAttribute("errormessage", ex.getMessage());
                        req.getSession(true).removeAttribute("inprogress");


                    }

                    if (req.getSession(true).getAttribute("inprogress") == null)
                        resp.sendRedirect(req.getContextPath()+"/intravexbills/index.jsp");
                } else {
                    resp.sendRedirect(req.getContextPath()+"/lakeville/processprogress.jsp");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("errormessage", ex.getMessage());
        }
    }


    public class importProcessorThread implements Runnable {

        public BufferedReader reader = null;
        public HttpSession theSession = null;

        public void start() {
            Thread t = new Thread(this, "New thread");
            t.start();

        }

        public void run() {

            com.owd.core.CSVReader data = null;

            //log.debug("Beginning Lakeville import thread");
            try {
                data = new com.owd.core.CSVReader(reader, true);
            } catch (Exception ex) {
                //log.debug("Processed reader instance");

            }

            UploadLakevilleBillData dataHandler = new UploadLakevilleBillData();

            dataHandler.init(data);

            theSession.setAttribute("toprocess", "" + dataHandler.getDataReader().getRowCount());


            Connection cxn = null;
            PreparedStatement stmt = null;

            try {

                cxn = ConnectionManager.getConnection();
                String updateString="insert into lakeville_ebills"
                        + "(shipper,\n" +
                        "shipper_city,\n" +
                        "shipper_state,\n" +
                        "shipper_zip,\n" +
                        "consignee,\n" +
                        "consignee_city,\n" +
                        "consignee_state,\n" +
                        "consignee_zip,\n" +
                        "pro_number,\n" +
                        "pickup_date,\n" +
                        "paid_type,\n" +
                        "pieces,\n" +
                        "weight,\n" +
                        "gross,\n" +
                        "net,\n" +
                        "disc,\n" +
                        "fsc,\n" +
                        "fsc_pct,\n" +
                        "swp,\n" +
                        "pallet,\n" +
                        "loose,\n" +
                        "predicted_cost,\n" +
                        "adjustment_charge,\n" +
                        "final_charge,\n" +
                        "import_date,\n" +
                        "client_fkey,\n" +
                        "order_fkey,\n" +
                        "is_confirmed,\n" +
                        "is_inbound,import_user,import_file)"
                        + "VALUES"
                        + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,   0.00,0.00,0.00,'"+OWDUtilities.getSQLDateTimeForToday()+"',0,0,0,1,'"+
                        theSession.getAttribute("lakeville_upload_username")+
                        "','"+theSession.getAttribute("lakeville_upload_filename")+"')";

                //log.debug(updateString);
                stmt = cxn.prepareStatement(updateString);

                for (int j = 0; j < dataHandler.getDataReader().getRowCount(); j++) {

                    int cols = dataHandler.getDataReader().getRowSize(j);
                  //  //log.debug("col count row " + j + " is " + cols);
                    cols = (cols < 21 ? cols : 21);
                    for (int col = 1; col <= cols; col++) {
                     //   //log.debug("col " + col);

                        switch (colTypes[col - 1]) {

                            case (colTypeInteger):
                                //log.debug("int from " + dataHandler.getDataReader().getIntValue(col - 1, j, 0));
                                stmt.setInt(col, dataHandler.getDataReader().getIntValue(col - 1, j, 0));

                                break;
                            case (colTypeFloat):
                                //log.debug("float from " + dataHandler.getDataReader().getFloatValue(col - 1, j, 0.00f));
                                stmt.setFloat(col, dataHandler.getDataReader().getFloatValue(col - 1, j, 0.00f));

                                break;

                            case (colTypeDate):
                                //log.debug("date from " + dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1"));
                                //log.debug("Date string finalized as:" + OWDUtilities.getRawSQLDateStrFromYYYYMMDD(dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1")));
                                stmt.setString(col, OWDUtilities.getRawSQLDateStrFromYYYYMMDD(dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1")));

                                break;

                            default:
                                stmt.setString(col, dataHandler.getDataReader().getStrValue(col - 1, j, ""));

                        }

                    }

                    //log.debug("updating");
                    int rows = stmt.executeUpdate();
                    //log.debug("done row " + j);
                    theSession.setAttribute("processed", "" + (j + 1));

                }

                stmt.close();

                //log.debug("sending update messge");
               // theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Finding bundled hundredweight shipments...");
                /*

                Hi All,

Just as an FYI, the OWD discount changed with Lakeville.  So, when you go to bill the clients for LTL shippments
through Lakeville, please be sure to mark up OWD cost plus 31% and that is what would be charged to the client.
So, the client will receive about a 39% discount off the suggested shipping cost by using Lakeville through OWD.

This is effective today, 11-23-05.

Questions, let me know.

Thanks,

Karen


NOTES

Lakeville bol number has an extra digit in our entries than in the tracking number recorded by manual shipment


*/
 theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>Matching items to packages and orders...");

                //match package key through tracking number
                stmt = cxn.prepareStatement("update lakeville_ebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id, is_confirmed=1,is_inbound=0,\n" +
                        "predicted_cost=round(total_billed,2),\n" +
                        "final_charge=round((net*1.31),2), \n" +
                        "adjustment_charge=round((net*1.31)-total_billed,2)\n" +
                        " from lakeville_ebills  \n" +
                        "join owd_order_track t join owd_order o on t.order_fkey = order_id and client_fkey<>158 on \n" +
                        " tracking_no like 'Lakeville'+pro_number+'%'  and t.is_void=0 and pro_number <> '' where is_confirmed=0");
                stmt.executeUpdate();
                stmt.close();

                 stmt = cxn.prepareStatement("update lakeville_ebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id, is_confirmed=1,is_inbound=0,\n" +
                        "predicted_cost=round(total_billed,2),\n" +
                        "final_charge=round((net*1.25),2), \n" +
                        "adjustment_charge=round((net*1.25)-total_billed,2)\n" +
                        " from lakeville_ebills  \n" +
                        "join owd_order_track t join owd_order o on t.order_fkey = order_id and client_fkey=158 on \n" +
                        " tracking_no like 'Lakeville'+pro_number+'%'  and t.is_void=0 and pro_number <> '' where is_confirmed=0");
                stmt.executeUpdate();
                stmt.close();

                 theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Updating inbound shipment discounts...");

                //complete processing for non-RUO discounts
                stmt = cxn.prepareStatement("update lakeville_ebills  " +
                        "set \n" +
                        "predicted_cost=round(0.00,2),\n" +
                        "final_charge=round((net*1.31),2), \n" +
                        "adjustment_charge=round((net*1.31),2)\n" +
                        " from lakeville_ebills  where is_confirmed=0");
                stmt.executeUpdate();
                stmt.close();

                cxn.commit();


            } catch (Exception tex) {
                tex.printStackTrace();
                theSession.removeAttribute("inprogress");

                theSession.setAttribute("upsebill_errormessage", tex.getMessage());
            } finally {
                try {
                    cxn.close();
                } catch (Exception sex) {
                }
                try {
                    stmt.close();
                } catch (Exception sex) {
                }

            }

            theSession.removeAttribute("inprogress");
        }


    }


}

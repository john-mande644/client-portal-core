package com.owd.web.internal.intravexbills;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.MultipartRequest;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.UpsEbill;
import com.owd.hibernate.HibUtils;
import com.owd.web.internal.servlet.HomeServlet;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 24, 2003
 * Time: 10:07:17 AM
 * To change this template use Options | File Templates.
 */
public class UPSBillsServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();

    static final int colTypeString = 0;
    static final int colTypeInteger = 1;
    static final int colTypeFloat = 2;
    static final int colTypeDate = 3;

    static final int[] colTypes = {colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeDate,
                                   colTypeFloat,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeInteger,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeDate,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeFloat,
                                   colTypeFloat,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString,
                                   colTypeString};

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
                if (client_id.equals("0")) client_id = "55";

                try {
                    updateUnreviewedItem(item_id, client_id, billTo);

                } catch (Exception ex) {
                    ex.printStackTrace();

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

                        //log.debug("In UPS Billing Servlet - loading file");
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

                                //log.debug("In UPS Billing Servlet - getting file reader");
                                reader = new BufferedReader(new FileReader(uploadFile));
                                uploadFile.renameTo(new File(HomeServlet.kBulkLoadSaveDir + File.separator + OWDUtilities.getCurrentUserName() + Calendar.getInstance().getTime().getTime() + ".tab"));


                                req.getSession(true).setAttribute("processed", "" + 0);
                                req.getSession(true).setAttribute("toprocess", "" + 0);
                                req.getSession(true).setAttribute("updateMessage", "");


                                req.getSession(true).setAttribute("ups_upload_username", OWDUtilities.getCurrentUserName());
                                req.getSession(true).setAttribute("ups_upload_filename", uploadFile.getName());
                                //log.debug(uploadFile.getName());

                                importProcessorThread processor = new importProcessorThread();
                                processor.reader = reader;
                                processor.theSession = req.getSession(true);

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

    private static void  updateUnreviewedItem(String item_id, String client_id, String billTo) throws Exception {
        Session sess = HibernateSession.currentSession();

        log.debug("Updating intravex id "+item_id);
        UpsEbill item = ((UpsEbill) sess.load(UpsEbill.class, new Integer(item_id)));
        OwdClient client = ((OwdClient) sess.load(OwdClient.class, new Integer(client_id)));
        //log.debug("Loaded UPS Bill Client selected "+client_id+" : "+client.getCompanyName());
        item.setBillOwd(new Integer("OWD".equals(billTo) ? 1 : 0));
        item.setBillClient(new Integer("CLIENT".equals(billTo) ? 1 : 0));

        item.setOwdClient(client);

        log.debug("cking review");
        if ((item.getBillOwd().intValue() + item.getBillClient().intValue() == 1) && item.getOwdClient().getClientId().intValue()>0) {
            log.debug("set 1 to review");

            item.setNeedsReview(null);
       }


        sess.saveOrUpdate(item);
        sess.flush();
        HibUtils.commit(sess);

        applyDiscountsToUnreviewedRecords( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection());

        if ((item.getBillOwd().intValue() + item.getBillClient().intValue() == 1) && item.getOwdClient().getClientId().intValue()>0) {
            log.debug("set 0 to review");

            item.setNeedsReview(new Integer(0));
        }
        sess.saveOrUpdate(item);
        sess.flush();
        HibUtils.commit(sess);
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

            //log.debug("Beginning UPS import thread");
            try {
                data = new com.owd.core.CSVReader(reader, true);
            } catch (Exception ex) {
                //log.debug("Processed reader instance");

            }

            UploadUPSBillData dataHandler = new UploadUPSBillData();

            dataHandler.init(data);

            theSession.setAttribute("toprocess", "" + dataHandler.getDataReader().getRowCount());


            Connection cxn = null;
            PreparedStatement stmt = null;

            try {

                cxn = ConnectionManager.getConnection();
                stmt = cxn.prepareStatement("insert into ups_ebill"
                        + "(RecordTypeIndicator,"    //0
                        + "PlanNumber,"
                        + "ShipperNumber,"
                        + "InvoiceNumber,"
                        + "BillDate,"
                        + "InvoiceAmount,"       //5
                        + "TrackingNumber,"
                        + "PickupRecordNumber,"
                        + "Reference1,"
                        + "Reference2,"
                        + "InternetID,"       //10
                        + "Quantity,"
                        + "BilledWeight,"
                        + "ActualWeight,"
                        + "WeightIndicator,"
                        + "DimensionalWeight,"     //15
                        + "Oversize1,"
                        + "Oversize2,"
                        + "Zone,"
                        + "TransactionCode,"
                        + "ServiceDescription,"     //20
                        + "BillOption,"
                        + "PickupDate,"
                        + "SenderName,"
                        + "SenderCompanyName,"
                        + "SenderStreet,"    //25
                        + "SenderCity,"
                        + "SenderState,"
                        + "SenderZip,"
                        + "ReceiverName,"
                        + "ReceiverCompanyName," //30
                        + "ReceiverStreet,"
                        + "ReceiverCity,"
                        + "ReceiverState,"
                        + "ReceiverZip,"
                        + "ReceiverCountry,"         //35
                        + "NetCharges,"
                        + "Incentive,"
                        + "DeclaredValue,"
                        + "SaturdayDelivery,"
                        + "COD,"                         //40
                        + "AdditionalHandling,"
                        + "HazardousMaterial,"
                        + "EarlyAMSurcharge,"
                        + "DeliveryConfirmation,"
                        + "SaturdayPickup,"                //45
                        + "CallTag,"
                        + "ExtendedDestinationSurcharge,"
                        + "InvalidAccountCharge,"
                        + "DeliveryAreaSurcharge,"
                        + "CurrencySurcharge,"                 //50
                        + "ChargebackSurcharge,"
                        + "PickupFee,"
                        + "BillingOption,"
                        + "ConsolidatedClearance,"
                        + "SplitDutyTax,"                        //55
                        + "ExportLicenseVerification,"
                        + "MiscLine1,"
                        + "MiscLine2,"
                        + "MiscLine3,"
                        + "MiscLine4,"                               //60
                        + "MiscLine5,importUser,importFilename)"
                        + "VALUES"
                        + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


                for (int j = 0; j < dataHandler.getDataReader().getRowCount(); j++) {

                    int cols = dataHandler.getDataReader().getRowSize(j);
                    log.debug("col count row " + j + " is " + cols);
                    cols = (cols < 63 ? cols : 62);
                    for (int col = 1; col <= cols; col++) {
                        //log.debug("col " + col);

                        switch (colTypes[col - 1]) {

                            case (colTypeInteger):
                                //log.debug("int from " + dataHandler.getDataReader().getIntValue(col - 1, j, 0));
                                stmt.setInt(col, dataHandler.getDataReader().getIntValue(col - 1, j, 0));

                                break;
                            case (colTypeFloat):
                                //log.debug("float from " + dataHandler.getDataReader().getFloatValue(col - 1, j, 0));
                                stmt.setFloat(col, dataHandler.getDataReader().getFloatValue(col - 1, j, 0.00f));

                                break;

                            case (colTypeDate):
                                //log.debug("date from " + dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1"));
                                log.debug("Date string finalized as:" + OWDUtilities.getRawSQLDateStrFromMMDDYYYY(dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1")));
                                stmt.setString(col, OWDUtilities.getRawSQLDateStrFromMMDDYYYY(dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1")));

                                break;

                            default:
                                log.debug("StrLen "+col+":"+dataHandler.getDataReader().getStrValue(col - 1, j, ""));
                                stmt.setString(col, dataHandler.getDataReader().getStrValue(col - 1, j, ""));

                        }

                    }
                    stmt.setString(cols + 1, (String) theSession.getAttribute("ups_upload_username"));
                    stmt.setString(cols + 2, (String) theSession.getAttribute("ups_upload_filename"));


                    //  //log.debug("getting track data");
                    // String trackXML = UPSEbillManager.getUPSPackageTrackingInfo(dataHandler.getDataReader().getStrValue(6, j, ""));
                    // stmt.setString(cols+3,trackXML);
                    // //log.debug("getting track status");
                    // stmt.setString(cols+4,UPSEbillManager.getUPSReportedPackageStatus(trackXML));
                    //log.debug("updating");
                    int rows = stmt.executeUpdate();
                    //log.debug("done row " + j);
                    theSession.setAttribute("processed", "" + (j + 1));

                }

                stmt.close();

                //log.debug("sending update messge");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Finding bundled hundredweight shipments...");
                     String invoiceNumber = "";

                stmt = cxn.prepareStatement("select miscline3,max(invoicenumber),count(distinct(reference1)) from ups_ebill where needs_review is null and quantity>0 and miscline3<>'' \n" +
                        "group by miscline3\n" +
                        "having count(distinct(reference1))>1");
                List multiList = new ArrayList();
                ResultSet rs = stmt.executeQuery();
                //log.debug("Executed query 1");
                while (rs.next()) {
                    multiList.add(rs.getString(1));
                    invoiceNumber = rs.getString(2);
                    //log.debug("Executed query 1 : got " + rs.getString(2) + " packages for multiship key " + rs.getString(1));

                }

                rs.close();
                stmt.close();

                 //confirm hundredweight/multiship masters
                stmt = cxn.prepareStatement("update ups_ebill set is_multiship=1 from ups_ebill e "
                        + " where quantity>1 and needs_review  is null");
                stmt.executeUpdate();
                stmt.close();


                //log.debug(" checking subquery");
                //clear hundredweight items with masters
                stmt = cxn.prepareStatement(" update ups_ebill set item_value=0.0,needs_review=0,"
                        + "parent_ups_key=(select distinct id from ups_ebill e3 where e3.miscline3=ups_ebill.miscline3 and e3.needs_review is null and e3.transactioncode=ups_ebill.transactioncode  and e3.is_multiship=1  and e3.miscline3 <> \'\') "
                        + "where quantity=1 and needs_review is null "
                        + " and miscline3 <> \'\'");
                stmt.executeUpdate();
                stmt.close();
                /*
                New logic for multiship

                --continue to ignore individual records    (mark as not needing review with 0 for bill_owd and bill_client)
                --update tracking number field with lead tracking from package list

                if order identified:
                --collect item_value for total billed original for shipment
                --apply to item_value for master record
                --process normally

                if no order identified
                -- treat as needing review for single master record
                


                 */
                if (multiList.size() > 0) {
                    for (int i = 0; i < multiList.size(); i++) {


                        //get detail info based on looking up tracking numbers associated with this multiship record group and pulling order data from any matches
                   String getOrderDataSQL = "select o.client_fkey,t.order_fkey,convert(money,sum(total_billed)) from owd_order_track t " +
                           "join owd_order o on o.order_id=t.order_fkey join ups_ebill \n" +
                           "on tracking_no=trackingnumber where miscline3=? and invoicenumber=? and needs_review=0 and trackingnumber like '1%' and t.is_void=0 and parent_ups_key is not null" +
                           " and transactioncode in ('','MAN','WWS','RSL') \n" +
                           "and ship_date>dateadd(month,-9,getdate()) group by t.order_fkey, o.client_fkey";
                   stmt = cxn.prepareStatement(getOrderDataSQL);
                        String miscline3 = (String)multiList.get(i);
                   stmt.setString(1,miscline3);
                              stmt.setString(2,invoiceNumber);
                        //log.debug("checking miscline3 value "+miscline3);
                  rs = stmt.executeQuery();
                        while(rs.next())
                        {

                            //log.debug("got values for "+miscline3+" "+rs.getInt(1)+":"+rs.getInt(2)+":"+rs.getFloat(3));
                            String updater="update ups_ebill set bill_client=1,bill_owd=0,client_fkey=?,order_fkey=?,item_value=?" +
                                    " where is_multiship=1 and miscline3=? and transactioncode in ('','MAN','WWS','RSL') and needs_review is null";

                            String updater2="update ups_ebill set bill_client=1,bill_owd=0, client_fkey=?,order_fkey=?,item_value=0.00" +
                                    " where is_multiship=1 and miscline3=? and  transactioncode not in ('','MAN','WWS','RSL') and needs_review is null";
                            PreparedStatement ps = cxn.prepareStatement(updater);
                            PreparedStatement ps2 = cxn.prepareStatement(updater2);
                            ps.setInt(1,rs.getInt(1));
                            ps.setInt(2,rs.getInt(2));
                            ps.setFloat(3,rs.getFloat(3));
                            ps.setString(4,miscline3);
                            
                            ps2.setInt(1,rs.getInt(1));
                            ps2.setInt(2,rs.getInt(2));
                            ps2.setString(3,miscline3);
                            //log.debug("run update 1");
                            ps.executeUpdate();
                            //log.debug("run update 2");
                            ps2.executeUpdate();
                            ps.close();
                            ps2.close();
                        }
                        rs.close();
                        stmt.close();


                    }     //end loop for each distinct multi-order shipment


                }


                // theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Matching items to orders...");
                //match with orders/clients based on reference number
                //  stmt = cxn.prepareStatement("update ups_ebill set order_fkey=order_id,client_fkey=owd_order.client_fkey from ups_ebill e left outer join owd_order on order_num = reference1 and is_void=0 where needs_review is null");
                //  stmt.executeUpdate();
                //  stmt.close();

                //  theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>Matching items to packages and orders...");

                //match package key through tracking number
                stmt = cxn.prepareStatement("update ups_ebill  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id from ups_ebill  "
                        + "join owd_order_track t join owd_order o on t.order_fkey = order_id on trackingnumber=tracking_no and is_multiship=0 and t.is_void=0 and trackingnumber <> '' where needs_review is null");
                stmt.executeUpdate();
                stmt.close();

             /*   stmt = cxn.prepareStatement("update ups_ebill  set client_fkey=o.client_fkey, order_fkey=order_id from ups_ebill  "
                        + "left outer join  owd_order o on order_num = reference1 where needs_review is null and order_fkey is null");
                stmt.executeUpdate();
                stmt.close();*/

           /*     theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Fixing TeamDepot virtual shipments...");


*/

                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Identifying multiship packages and records...");


                
                    theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Applying client discounts...");
                applyDiscountsToUnreviewedRecords(cxn);


                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Reviewing previously reported rates...");


                  stmt = cxn.prepareStatement("update ups_ebill set package_fkey=order_track_id from ups_ebill e "
                        + "join owd_order_track t on trackingnumber=tracking_no and t.order_fkey=e.order_fkey and is_void=0 and is_multiship=0 where needs_review is null");
                stmt.executeUpdate();
                stmt.close();
                
                //updates non-master multiship records to zero
                stmt = cxn.prepareStatement("update ups_ebill set bill_client=1,needs_review=0, item_value=total_billed from ups_ebill e "
                        + "join owd_order_track v  on v.order_track_id=e.package_fkey and v.is_void=0 and client_fkey is not null and is_multiship=0 and package_fkey is not null and parent_ups_key is null"
                        + " where transactioncode in (\'MAN\',\'WWS\',\'RSL\')  and needs_review is null");
                stmt.executeUpdate();
                stmt.close();

/*

                stmt = cxn.prepareStatement("update ups_ebill set bill_client=1,needs_review=0,item_value=rated_cost from ups_ebill e "
                        + "join vw_packagesummary v  on v.order_fkey=e.order_fkey  and client_fkey is not null and is_multiship=1 and parent_ups_key is null"
                        + " where transactioncode in (\'MAN\',\'WWS\',\'RSL\') and needs_review is null");
                stmt.executeUpdate();
                stmt.close();


*/


                //apply adjustments connected to billing total matches for manifest items


                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Assigning verifiable items to OWD...");

                 //mark all items that are always assigned to OWD
                stmt = cxn.prepareStatement("update ups_ebill set bill_owd=1,item_value=0.00,needs_review=0,client_fkey=55 from ups_ebill e "
                        + " where transactioncode in (\'GSR\',\'SVC\',\'VOD\',\'NPB\') "
                        + " and needs_review is null");
                stmt.executeUpdate();
                stmt.close();


                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Assigning adjustments to clients...");

                //mark and clear all items that are automatically billed as-is
                stmt = cxn.prepareStatement("update ups_ebill set bill_client=1,item_value=0.00,needs_review=0 from ups_ebill e "
                        + "where client_fkey is not null and transactioncode in (\'RSV\',\'SAT\',\'ADJ\',\'ADC\',\'RES\',\'SCC\') "
                        + " and needs_review is null");
                stmt.executeUpdate();
                stmt.close();


                //deal with the weird ones



                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Marking items for review...");

                //complete processing - mark all remaining items as needing review
                stmt = cxn.prepareStatement("update ups_ebill set needs_review = 1 where needs_review is null");
                stmt.executeUpdate();


                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Clearing items with zero charges...");


                //clear all zero-charge items
                stmt = cxn.prepareStatement("update ups_ebill set needs_review=0 from ups_ebill e "
                        + " where (incentive+netcharges)=0.00");
                stmt.executeUpdate();

                //mark any with a client id not billed to OWD as done and billable to client
                stmt = cxn.prepareStatement("update ups_ebill set bill_client=1,needs_review=0 from ups_ebill e "
                        + " where client_fkey<>55 and bill_owd=0");
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

    
    public static void main(String[] args)  throws Exception
    {


        updateUnreviewedItem("736561","482", "CLIENT");
        updateUnreviewedItem("747242","483", "CLIENT");
        updateUnreviewedItem("744965","483", "CLIENT");
        updateUnreviewedItem("744972","483", "CLIENT");
        updateUnreviewedItem("736464","482", "CLIENT");
        updateUnreviewedItem("744821","483", "CLIENT");
        updateUnreviewedItem("744908","483", "CLIENT");
        updateUnreviewedItem("744861","483", "CLIENT");
        updateUnreviewedItem("747248","483", "CLIENT");
        updateUnreviewedItem("744860","483", "CLIENT");
        updateUnreviewedItem("744866","483", "CLIENT");
        updateUnreviewedItem("744933","483", "CLIENT");
        updateUnreviewedItem("744943","483", "CLIENT");
        updateUnreviewedItem("744969","483", "CLIENT");
        updateUnreviewedItem("744958","483", "CLIENT");
        updateUnreviewedItem("744971","483", "CLIENT");
        updateUnreviewedItem("744835","483", "CLIENT");
        updateUnreviewedItem("744884","483", "CLIENT");
        updateUnreviewedItem("747268","483", "CLIENT");
        updateUnreviewedItem("747241","483", "CLIENT");
        updateUnreviewedItem("744834","483", "CLIENT");
        updateUnreviewedItem("744782","483", "CLIENT");
        updateUnreviewedItem("744942","483", "CLIENT");
        updateUnreviewedItem("744802","483", "CLIENT");
        updateUnreviewedItem("744902","483", "CLIENT");
        updateUnreviewedItem("747249","483", "CLIENT");
        updateUnreviewedItem("744891","483", "CLIENT");
        updateUnreviewedItem("744962","483", "CLIENT");
        updateUnreviewedItem("744903","483", "CLIENT");
        updateUnreviewedItem("744932","483", "CLIENT");
        updateUnreviewedItem("747246","483", "CLIENT");
        updateUnreviewedItem("744968","483", "CLIENT");
        updateUnreviewedItem("744803","483", "CLIENT");
        updateUnreviewedItem("744952","483", "CLIENT");
        updateUnreviewedItem("744828","483", "CLIENT");
        updateUnreviewedItem("744918","483", "CLIENT");
        updateUnreviewedItem("744901","483", "CLIENT");
        updateUnreviewedItem("736627","482", "CLIENT");
        updateUnreviewedItem("736366","482", "CLIENT");
        updateUnreviewedItem("736496","482", "CLIENT");
        updateUnreviewedItem("744863","483", "CLIENT");
        updateUnreviewedItem("736461","482", "CLIENT");
        updateUnreviewedItem("744785","483", "CLIENT");
        updateUnreviewedItem("742361","483", "CLIENT");
        updateUnreviewedItem("747277","483", "CLIENT");
        updateUnreviewedItem("744906","483", "CLIENT");
        updateUnreviewedItem("744847","483", "CLIENT");
        updateUnreviewedItem("744846","483", "CLIENT");
        updateUnreviewedItem("744921","483", "CLIENT");
        updateUnreviewedItem("744842","483", "CLIENT");
        updateUnreviewedItem("744915","483", "CLIENT");
        updateUnreviewedItem("744799","483", "CLIENT");
        updateUnreviewedItem("744970","483", "CLIENT");
        updateUnreviewedItem("747232","483", "CLIENT");
        updateUnreviewedItem("744916","483", "CLIENT");
        updateUnreviewedItem("744941","483", "CLIENT");
        updateUnreviewedItem("747273","483", "CLIENT");
        updateUnreviewedItem("744797","483", "CLIENT");
        updateUnreviewedItem("744886","483", "CLIENT");
        updateUnreviewedItem("744823","483", "CLIENT");
        updateUnreviewedItem("744935","483", "CLIENT");
        updateUnreviewedItem("736612","482", "CLIENT");
        updateUnreviewedItem("744913","483", "CLIENT");
        updateUnreviewedItem("744850","483", "CLIENT");
        updateUnreviewedItem("747251","483", "CLIENT");
        updateUnreviewedItem("744876","483", "CLIENT");
        updateUnreviewedItem("744887","483", "CLIENT");
        updateUnreviewedItem("744926","483", "CLIENT");
        updateUnreviewedItem("744974","483", "CLIENT");
        updateUnreviewedItem("744805","483", "CLIENT");
        updateUnreviewedItem("736382","482", "CLIENT");
        updateUnreviewedItem("744923","483", "CLIENT");
        updateUnreviewedItem("736560","482", "CLIENT");
        updateUnreviewedItem("736638","482", "CLIENT");
        updateUnreviewedItem("736693","482", "CLIENT");
        updateUnreviewedItem("744879","483", "CLIENT");
        updateUnreviewedItem("718956","510", "CLIENT");
        updateUnreviewedItem("740233","510", "CLIENT");
        updateUnreviewedItem("744851","483", "CLIENT");
        updateUnreviewedItem("747255","483", "CLIENT");
        updateUnreviewedItem("747264","483", "CLIENT");
        updateUnreviewedItem("744784","483", "CLIENT");
        updateUnreviewedItem("747254","483", "CLIENT");
        updateUnreviewedItem("736505","482", "CLIENT");
        updateUnreviewedItem("744859","483", "CLIENT");
        updateUnreviewedItem("744838","483", "CLIENT");
        updateUnreviewedItem("736519","482", "CLIENT");
        updateUnreviewedItem("747250","483", "CLIENT");
        updateUnreviewedItem("736463","482", "CLIENT");
        updateUnreviewedItem("744877","483", "CLIENT");
        updateUnreviewedItem("744865","483", "CLIENT");
        updateUnreviewedItem("744966","483", "CLIENT");
        updateUnreviewedItem("744893","483", "CLIENT");
        updateUnreviewedItem("744973","483", "CLIENT");
        updateUnreviewedItem("744830","483", "CLIENT");
        updateUnreviewedItem("744793","483", "CLIENT");
        updateUnreviewedItem("744919","483", "CLIENT");
        updateUnreviewedItem("744930","483", "CLIENT");
        updateUnreviewedItem("744934","483", "CLIENT");
        updateUnreviewedItem("744945","483", "CLIENT");
        updateUnreviewedItem("744854","483", "CLIENT");
        updateUnreviewedItem("747247","483", "CLIENT");
        updateUnreviewedItem("744796","483", "CLIENT");
        updateUnreviewedItem("744829","483", "CLIENT");
        updateUnreviewedItem("744948","483", "CLIENT");
        updateUnreviewedItem("744783","483", "CLIENT");
        updateUnreviewedItem("744940","483", "CLIENT");
        updateUnreviewedItem("736633","482", "CLIENT");
        updateUnreviewedItem("736377","482", "CLIENT");
        updateUnreviewedItem("744874","483", "CLIENT");
        updateUnreviewedItem("744888","483", "CLIENT");
        updateUnreviewedItem("744870","483", "CLIENT");
        updateUnreviewedItem("744807","483", "CLIENT");
        updateUnreviewedItem("747272","483", "CLIENT");
        updateUnreviewedItem("736723","482", "CLIENT");
        updateUnreviewedItem("747253","483", "CLIENT");
        updateUnreviewedItem("744947","483", "CLIENT");
        updateUnreviewedItem("744951","483", "CLIENT");
        updateUnreviewedItem("747238","483", "CLIENT");
        updateUnreviewedItem("744914","483", "CLIENT");
        updateUnreviewedItem("744955","483", "CLIENT");
    }
    protected static void applyDiscountsToUnreviewedRecords(Connection cxn) throws SQLException {
             PreparedStatement stmt;

        //clear all affected records
        stmt = cxn.prepareStatement("update ups_ebill set client_incentive=0.00,client_incentive_pct=0.00  "
                     + " where needs_review is null");
             stmt.executeUpdate();

          //set the incentive share total value
             stmt = cxn.prepareStatement("update ups_ebill set client_incentive=isnull(discount_share_pct*(incentive),0.00)  "
                                       + " from ups_ebill join owd_client on client_id=client_fkey and needs_review is null");
             stmt.executeUpdate();

        //calculate client percentage of original charge
             stmt = cxn.prepareStatement("update ups_ebill set client_incentive_pct=round(client_incentive/(incentive+netcharges),2)  "
                     + " where  (client_incentive>0 or client_incentive < 0) and needs_review is null");
             stmt.executeUpdate();

        //correct for any over-incentivizing...
             stmt = cxn.prepareStatement("update ups_ebill set client_incentive =incentive where ABS(incentive)<ABS(client_incentive) and needs_review is null");
             stmt.executeUpdate();

        stmt.close();
         }

}

package com.owd.web.internal.fedexbills;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.MultipartRequest;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.generated.FedexEbill;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
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
import java.util.Calendar;
import java.util.Enumeration;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 24, 2003
 * Time: 10:07:17 AM
 * To change this template use Options | File Templates.
 */
public class FedexBillsServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();

    static final int colTypeString = 0;
    static final int colTypeInteger = 1;
    static final int colTypeFloat = 2;
    static final int colTypeDate = 3;

    static final int[] colTypes = {colTypeString,
colTypeDate,
colTypeString,
colTypeFloat,
colTypeFloat, //5
colTypeString,
colTypeString,
colTypeString,
colTypeFloat,
colTypeFloat,  //10
colTypeString,
colTypeString,
colTypeDate,
colTypeDate,
colTypeString,
colTypeString,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,    //20
colTypeString,
colTypeInteger,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString, //30
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString, //40
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString,   //50
colTypeDate,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeString, //60
colTypeString,
colTypeString,
colTypeString,
colTypeString,
colTypeDate,
colTypeFloat,
//colTypeString,
colTypeFloat,
//colTypeString,
//colTypeString,  //70
colTypeFloat,
colTypeFloat,
colTypeFloat,
colTypeFloat,
//colTypeString,
colTypeFloat,
colTypeFloat,
//colTypeFloat,
//colTypeFloat,
//colTypeFloat,     //80
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,  //90
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,   //100
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,  //120
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,  //130
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat,
colTypeString,
colTypeFloat    //140


    };

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
                    Session sess = HibernateSession.currentSession();

                    FedexEbill item = ((FedexEbill) sess.load(FedexEbill.class, new Integer(item_id)));
                    OwdClient client = ((OwdClient) sess.load(OwdClient.class, new Integer(client_id)));
                    item.setBillOwd(new Integer("OWD".equals(billTo) ? 1 : 0));
                    item.setBillClient(new Integer("CLIENT".equals(billTo) ? 1 : 0));
                    if (item.getBillOwd().intValue() + item.getBillClient().intValue() == 1) {
                        item.setNeedsReview(new Integer(0));
                    }
                    item.setOwdClient(client);
                    sess.saveOrUpdate(item);
                    sess.flush();
                    HibUtils.commit(sess);

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
                //log.debug("FedEx Bill servlet reports inprogress attribute as:" + req.getSession(true).getAttribute("inprogress"));
                if (req.getSession(true).getAttribute("inprogress") == null) {
                    try {

                        //log.debug("In FedEx Billing Servlet - loading file");
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

                                //log.debug("In FedEx Billing Servlet - getting file reader");
                                reader = new BufferedReader(new FileReader(uploadFile));
                                uploadFile.renameTo(new File(HomeServlet.kBulkLoadSaveDir + File.separator + OWDUtilities.getCurrentUserName() + Calendar.getInstance().getTime().getTime() + ".tab"));


                                req.getSession(true).setAttribute("processed", "" + 0);
                                req.getSession(true).setAttribute("toprocess", "" + 0);
                                req.getSession(true).setAttribute("updateMessage", "");


                                req.getSession(true).setAttribute("fedex_upload_username", OWDUtilities.getCurrentUserName());
                                req.getSession(true).setAttribute("fedex_upload_filename", uploadFile.getName());
                                //log.debug(uploadFile.getName());

                                importProcessorThread processor = new importProcessorThread();
                                processor.reader = reader;
                                processor.theSession = req.getSession(true);

                                processor.start();

                                req.getSession(true).setAttribute("inprogress", "true");


                                resp.sendRedirect("/internal/fedexbills/processprogress.jsp");


                            }
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                        req.setAttribute("errormessage", ex.getMessage());
                        req.getSession(true).removeAttribute("inprogress");


                    }

                    if (req.getSession(true).getAttribute("inprogress") == null)
                        resp.sendRedirect("/internal/fedexbills/index.jsp");
                } else {
                    resp.sendRedirect("/internal/fedexbills/processprogress.jsp");
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

            //log.debug("Beginning FedEx import thread");
            try {
                data = new com.owd.core.CSVReader(reader, true);
            } catch (Exception ex) {
                //log.debug("Processed reader instance");

            }

            UploadFedexBillData dataHandler = new UploadFedexBillData();

            dataHandler.init(data);

            theSession.setAttribute("toprocess", "" + dataHandler.getDataReader().getRowCount());


            Connection cxn = null;
            PreparedStatement stmt = null;

            try {

                cxn = ConnectionManager.getConnection();
                stmt = cxn.prepareStatement("insert into fedex_ebills"
                        + "(BilltoAccountNumber,"
                        + "InvoiceDate,"
                        + "InvoiceNumber,"
                        + "OriginalAmountDue,"
                        + "CurrentBalance,"
                        + "PaymentPayorCode,"
                        + "GroundTrackingIDPrefix,"
                        + "ExpressorGroundTrackingID,"
                        + "FreightChargeAmount,"
                        + "NetChargeAmount,"
                        + "ServiceTypeCode,"
                        + "GroundServiceCode,"
                        + "ShipmentDate,"
                        + "PODDeliveryDate,"
                        + "PODDeliveryTime,"
                        + "PODServiceAreaCode,"
                        + "PODSignatureDescription,"
                        + "ActualWeightAmount,"
                        + "ActualWeightUnits,"
                        + "RatedWeightAmount,"
                        + "RatedWeightUnits,"
                        + "NumberofPieces,"
                        + "BundleNumber,"
                        + "MeterNumber,"
                        + "RecipientName,"
                        + "RecipientCompany,"
                        + "RecipientAddressLine1,"
                        + "RecipientAddressLine2,"
                        + "RecipientCity,"
                        + "RecipientState,"
                        + "RecipientZipCode,"
                        + "RecipientCountryCode,"
                        + "ShipperCompany,"
                        + "ShipperName,"
                        + "ShipperAddressLine1,"
                        + "ShipperAddressLine2,"
                        + "ShipperCity,"
                        + "ShipperState,"
                        + "ShipperZipCode,"
                        + "ShipperCountryCode,"
                        + "OriginalCustRefNotes,"
                        + "OriginalExpressRefTwo,"
                        + "OriginalDeptRefDesc,"
                        + "OriginalPONumber,"
                        + "UpdatedCustRefNotes,"
                        + "UpdatedExpressRefTwo,"
                           + "UpdatedPONumber,"
                        + "UpdatedDeptRefDesc,"
                        + "RMANumber,"
                        + "ZoneCode,"
                        + "EntryDate,"
                        + "EntryNumber,"
                        + "CustomsValue,"
                        + "CustomsValueCurrencyCode,"
                        + "DeclaredValue,"
                        + "DeclaredValueCurrencyCode,"
                        + "CommodityDescription1,"
                        + "CommodityCountryCode1,"
                        + "CommodityDescription2,"
                        + "CommodityCountryCode2,"
                        + "CommodityDescription3,"
                        + "CommodityCountryCode3,"
                        + "CommodityDescription4,"
                        + "CommodityCountryCode4,"
                        + "CurrencyConversionDate,"
                        + "CurrencyConversionRate,"
                     //   + "MultiDestZipCode,"
                        + "MultiNumber,"
                      ///  + "MultiOriginZipCode,"
                      //  + "MultiShipperNameDesc,"
                        + "MultiTotalMultiUnits,"
                        + "MultiTotalMultiWeight,"
                        + "MultiTotalShpmntChargeAmt,"
                        + "MultiTotalShipmentWeight,"
                      //  + "MultiZoneCode,"
                        + "GroundAddrCorrDiscountChargeAmt,"
                        + "GroundAddCorrGrossChargeAmt,"
                      //  + "NTProductQty,"
                      //  + "NTProductUnitPrice,"
                     //   + "NTTransactionBalanceDue,"
                        + "code1,"
                        + "code1Amt,"
                        + "code2,"
                        + "code2Amt,"
                        + "code3,"
                        + "code3Amt,"
                        + "code4,"
                        + "code4Amt,"
                        + "code5,"
                        + "code5Amt,"
                        + "code6,"
                        + "code6Amt,"
                        + "code7,"
                        + "code7Amt,"
                        + "code8,"
                        + "code8Amt,"
                        + "code9,"
                        + "code9Amt,"
                        + "code10,"
                        + "code10Amt,"
                        + "code11,"
                        + "code11Amt,"
                        + "code12,"
                        + "code12Amt,"
                        + "code13,"
                        + "code13Amt,"
                        + "code14,"
                        + "code14Amt,"
                        + "code15,"
                        + "code15Amt,"
                        + "code16,"
                        + "code16Amt,"
                        + "code17,"
                        + "code17Amt,"
                        + "code18,"
                        + "code18Amt,"
                        + "code19,"
                        + "code19Amt,"
                        + "code20,"
                        + "code20Amt,"
                        + "code21,"
                        + "code21Amt,"
                        + "code22,"
                        + "code22Amt,"
                        + "code23,"
                        + "code23Amt,"
                        + "code24,"
                        + "code24Amt,"
                        + "code25,"
                        + "code25Amt,importUser,importFilename,incentive,netcharges)"
                        + "VALUES"
                        +"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?)");


                for (int j = 1; j < dataHandler.getDataReader().getRowCount(); j++) {

                    float incentive = 0.00f;
                   float netcharges = 0.00f;

                    int cols = dataHandler.getDataReader().getRowSize(j);
                    //log.debug("col count row " + j + " is " + cols);
                    if(cols<125 && dataHandler.getDataReader().getRawRow(j).indexOf("Invoice Date")<0)
                    {
                    cols=123;
                    for (int col = 1; col <= cols; col++) {
                        //log.debug("col " + col+ " ; "+dataHandler.getDataReader().getStrValue(col - 1, j, ""));

                        switch (colTypes[col - 1]) {

                            case (colTypeInteger):
                               // //log.debug("int from " + dataHandler.getDataReader().getIntValue(col - 1, j, 0));
                                stmt.setInt(col, dataHandler.getDataReader().getIntValue(col - 1, j, 0));

                                break;
                            case (colTypeFloat):
                               // //log.debug("float from " + dataHandler.getDataReader().getFloatValue(col - 1, j, 0));
                                if(col>73)
                                {
                                  stmt.setFloat(col, dataHandler.getDataReader().getFloatValue(col - 1, j, 0.00f));
                                  String itemName=dataHandler.getDataReader().getStrValue(col - 2, j, "") ;
                                    if(itemName.indexOf("Earned Discount")>=0 || itemName.indexOf("Discount")>=0 || itemName.indexOf("Performance Pricing")>=0)
                                    {
                                        incentive += ((-1.00f)*dataHandler.getDataReader().getFloatValue(col - 1, j, 0.00f));
                                    }else
                                    {
                                        netcharges += dataHandler.getDataReader().getFloatValue(col - 1, j, 0.00f);
                                    }


                                }   else
                                {
                                stmt.setFloat(col, dataHandler.getDataReader().getFloatValue(col - 1, j, 0.00f));
                                }
                                break;

                            case (colTypeDate):
                                //log.debug("date from " + dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1"));
                                ////log.debug("Date string finalized as:" + OWDUtilities.getRawSQLDateStrFromYYYYMMDD(dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1")));
                                stmt.setString(col, OWDUtilities.getRawSQLDateStrFromYYYYMMDD(dataHandler.getDataReader().getStrValue(col - 1, j, "1900-1-1")));

                                break;

                            default:
                                stmt.setString(col, dataHandler.getDataReader().getStrValue(col - 1, j, ""));

                        }

                    }
                  /*  if(cols==129){ cols=130;
                                stmt.setFloat(130, 0.00f);
                    }*/
                    stmt.setString(cols + 1, (String) theSession.getAttribute("fedex_upload_username"));
                    stmt.setString(cols + 2, (String) theSession.getAttribute("fedex_upload_filename"));
                    stmt.setFloat(cols + 3, incentive);
                    stmt.setFloat(cols + 4, netcharges);


                    //  //log.debug("getting track data");
                    // String trackXML = UPSEbillManager.getUPSPackageTrackingInfo(dataHandler.getDataReader().getStrValue(6, j, ""));
                    // stmt.setString(cols+3,trackXML);
                    // //log.debug("getting track status");
                    // stmt.setString(cols+4,UPSEbillManager.getUPSReportedPackageStatus(trackXML));
                    //log.debug("updating");
                    int rows = stmt.executeUpdate();
                    }

                    //log.debug("done row " + j);
                    theSession.setAttribute("processed", "" + (j + 1));

                }

                stmt.close();

                   if(true)
                   {
                // theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Matching items to orders...");
                //match with orders/clients based on reference number
                //  stmt = cxn.prepareStatement("update ups_ebill set order_fkey=order_id,client_fkey=owd_order.client_fkey from ups_ebill e left outer join owd_order on order_num = reference1 and is_void=0 where needs_review is null");
                //  stmt.executeUpdate();
                //  stmt.close();

                //  theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Matching items to packages and orders...");

                        stmt = cxn.prepareStatement("update fedex_ebills set ExpressorGroundTrackingID=GroundTrackingIDPrefix+ExpressorGroundTrackingID where needs_review is null");
                stmt.executeUpdate();
                stmt.close();


                        stmt = cxn.prepareStatement("update fedex_ebills set netcharges=freightchargeamount+netcharges where needs_review is null");
                stmt.executeUpdate();
                stmt.close();

                       stmt = cxn.prepareStatement("update fedex_ebills set client_fkey=55,needs_review=0, servicetypecode='Pickup Charge' where servicetypecode like '%No Description Found%' and needs_review is null");
                                   stmt.executeUpdate();
                                   stmt.close();

                //match package key through tracking number
                stmt = cxn.prepareStatement("update fedex_ebills set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id from fedex_ebills  "
                        + "left outer join owd_order_track t join owd_order o on t.order_fkey = order_id on ExpressorGroundTrackingID=tracking_no and t.is_void=0 and ExpressorGroundTrackingID <> '' where needs_review is null");
                stmt.executeUpdate();
                stmt.close();

                stmt = cxn.prepareStatement("update fedex_ebills  set client_fkey=o.client_fkey, order_fkey=order_id from fedex_ebills  "
                        + "left outer join  owd_order o on order_num = OriginalCustRefNotes where needs_review is null and order_fkey is null");
                stmt.executeUpdate();
                stmt.close();

                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Fixing TeamDepot virtual shipments...");

                //fix teamdepot personalization shipment records
                stmt = cxn.prepareStatement("update fedex_ebills set package_fkey=order_track_id from fedex_ebills e "
                        + "left outer join owd_order_track t on ExpressorGroundTrackingID=tracking_no and t.order_fkey=e.order_fkey and is_void=0 where needs_review is null");
                stmt.executeUpdate();
                stmt.close();


                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Identifying multiship packages and records...");

               /*
                //confirm hundredweight/multiship masters
                stmt = cxn.prepareStatement("update ups_ebill set is_multiship=1 from ups_ebill e "
                        + " where quantity>1 and needs_review  is null");
                stmt.executeUpdate();
                stmt.close();

                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Applying client discounts...");

                //complete processing for non-RUO discounts
                stmt = cxn.prepareStatement("update ups_ebill set client_incentive_pct =  isnull(discount_pct,0.00), client_incentive=isnull(discount_pct*(incentive+netcharges),0.00)  "
                        + "from fedex_ebills u,owd_client_method m,owd_order_ship_info s, owd_lists l " +
                        "where m.client_fkey=u.client_fkey " +
                        "and s.order_fkey=u.order_fkey  and id = u.id  and s.carr_service_ref_num = l.reference_num "
                        + "and method_code = td_reference and (u.incentive>0 or u.incentive < 0) and needs_review is null");
                stmt.executeUpdate();

                //complete processing for RUO records
                stmt = cxn.prepareStatement("update ups_ebill set client_incentive=isnull(0.80*(incentive),0.00)  "
                        + " where client_fkey=160 and needs_review is null");
                stmt.executeUpdate();





*/
                      theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Applying client discounts...");

                //complete processing for non-RUO discounts
           /*     stmt = cxn.prepareStatement("update fedex_ebills set client_incentive_pct =  isnull(discount_pct,0.00), client_incentive=isnull(discount_pct*(incentive+netcharges),0.00)  "
                        + "from fedex_ebills u,owd_client_method m,owd_order_ship_info s, owd_lists l " +
                        "where m.client_fkey=u.client_fkey " +
                        "and s.order_fkey=u.order_fkey  and id = u.id  and (s.carr_service_ref_num = l.reference_num or s.carr_service_ref_num = l.td_reference) "
                        + "and method_code = td_reference and (u.incentive>0 or u.incentive < 0) and needs_review is null");

                stmt.executeUpdate();*/

                       stmt = cxn.prepareStatement("update fedex_ebills set client_incentive=isnull(0.50*(incentive),0.00)  "
                                              + " where  needs_review is null");
                                      stmt.executeUpdate();


                               stmt = cxn.prepareStatement("update fedex_ebills set client_incentive_pct=round(client_incentive/(incentive+netcharges),2)  "
                        + " where (client_incentive>0 or client_incentive < 0) and needs_review is null");
                stmt.executeUpdate();


                       stmt = cxn.prepareStatement("update fedex_ebills set client_incentive =incentive where ABS(incentive)<ABS(client_incentive) and needs_review is null");
                stmt.executeUpdate();

                 /*
                //clear hundredweight items with masters
                stmt = cxn.prepareStatement(" update ups_ebill set item_value=0.0,needs_review=0,"
                        + "parent_ups_key=(select id from ups_ebill e3 where e3.miscline3=ups_ebill.miscline3 and e3.is_multiship=1  and e3.miscline3 <> \'\') "
                        + "where quantity=1 and needs_review is null "
                        + " and transactioncode in ('','MAN','WWS','RSL') and miscline3 <> \'\'");
                stmt.executeUpdate();
                stmt.close();
*/
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Reviewing previously reported rates...");


                stmt = cxn.prepareStatement("update fedex_ebills set bill_client=1,needs_review=0, item_value=shipped_cost from fedex_ebills e "
                        + "join owd_order v  on v.order_id=e.order_fkey and v.is_void=0 and v.client_fkey is not null and order_fkey is not null and parent_fedex_key is null"
                        + " where  needs_review is null");
                stmt.executeUpdate();
                stmt.close();
            /*

                stmt = cxn.prepareStatement("update fedex_ebills set bill_client=1,needs_review=0,item_value=rated_cost from fedex_ebills e "
                        + "join vw_packagesummary v  on v.order_fkey=e.order_fkey  and client_fkey is not null and is_multiship=1 and parent_ups_key is null"
                        + " where transactioncode in (\'MAN\',\'WWS\',\'RSL\') and needs_review is null");
                stmt.executeUpdate();
                stmt.close();
*/



                //apply adjustments connected to billing total matches for manifest items


                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Assigning verifiable items to OWD...");

                       /*

                stmt = cxn.prepareStatement("update ups_ebill set bill_owd=1,item_value=0.00,needs_review=0,client_fkey=55 from ups_ebill e "
                        + " where transactioncode in (\'GSR\',\'SVC\',\'VOD\') "
                        + " and needs_review is null");
                stmt.executeUpdate();
                stmt.close();
                     */

                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Assigning adjustments to clients...");

                        /*
                stmt = cxn.prepareStatement("update ups_ebill set bill_client=1,item_value=0.00,needs_review=0 from ups_ebill e "
                        + "where client_fkey is not null and transactioncode in (\'RSV\',\'SAT\',\'ADJ\',\'ADC\',\'RES\',\'SCC\') "
                        + " and needs_review is null");
                stmt.executeUpdate();
                stmt.close();
                          */

                //deal with the weird ones



                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Marking items for review...");

                //complete processing
                stmt = cxn.prepareStatement("update fedex_ebills set needs_review = 1 where needs_review is null");
                stmt.executeUpdate();


                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Clearing items with zero charges...");


                stmt = cxn.prepareStatement("update fedex_ebills set needs_review=0 from fedex_ebills e "
                        + " where (freightchargeamount+netchargeamount)=0.00");
                stmt.executeUpdate();
                stmt.close();
                   }
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

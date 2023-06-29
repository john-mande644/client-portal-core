package com.owd.jobs.clients;

import com.owd.core.Mailer;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.FtpConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class BumblerideShipmentsUpdateJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception{

     run();


    }


    FtpConnector ftp = new FtpConnector("ftp.owd.com", "bumbleride", "Hd7J90*", "OUT_FROM_OWD");


    public void internalExecute() {

        postPreviousDayShipmentsFile();


    }

    public void postPreviousDayShipmentsFile() {

         Calendar calendar = Calendar.getInstance();

        String header = "ShipDate\tPoNumber\tOrderId\tCapacityOrderId\tTrackingNumber\tSku\tOrderQty\tShipQty\r\n";

            java.sql.Connection cxn = null;
            java.sql.PreparedStatement stmt = null;
            java.sql.ResultSet rs = null;


                try {
                    StringBuilder result = new StringBuilder();
                     result.append(header);


                    Calendar nowCal = Calendar.getInstance();
                    Calendar activityCal = Calendar.getInstance();
                    activityCal.add(Calendar.DATE, -1);
                    DateFormat queryDf = new SimpleDateFormat("yyyy-MM-dd");
                    String shipDate = queryDf.format(activityCal.getTime());

                    DateFormat filenameDf = new SimpleDateFormat("yyyyMMddhhmmss");
                    Random rnd = new Random();
                    int n = 100000 + rnd.nextInt(900000);

                    String reportSQL = "select convert(varchar,shipped_on,120),po_num,order_refnum,order_num,tracking_nums,inventory_num,quantity_request,quantity_actual\n" +
                            " from owd_order o\n" +
                            " join owd_line_item l on l.order_fkey=order_id\n" +
                            " where shipped_on='"+shipDate+"' and client_fkey=575\n" +
                            " and quantity_actual>0";

                    cxn = ConnectionManager.getConnection();

                    stmt = cxn.prepareStatement(reportSQL);
                   // stmt.setString(1, (providedDate==null?OWDUtilities.getSQLDateString(calendar.getTime()):providedDate));

                    stmt.executeQuery();

                    rs = stmt.getResultSet();
                    boolean gotResults = false;

                    while (rs.next()) {

                        result.append(""+rs.getString(1).split(" ")[0] + "\t");
                        result.append(rs.getString(2) + "\t");
                        result.append(rs.getString(3) + "\t");
                        result.append(rs.getString(4) + "\t");
                        result.append(rs.getString(5) + "\t");
                        result.append(rs.getString(6) + "\t");
                        result.append(rs.getString(7) + "\t");
                        result.append(rs.getString(8) + "\r\n");
                        gotResults = true;
                    }

                    rs.close();
                    stmt.close();
                    cxn.rollback();
//send to ftp site


                    if (gotResults) {
                        String filename = "ShipConfirm_BR__" + filenameDf.format(nowCal.getTime())+".txt";



                        ftp.putFileData(filename,result.toString().getBytes());
                        log.debug("file:" + filename);
                         Mailer.sendMailWithAttachment("OWD Bumbleride Shipping Report " + filename, "Report for " + queryDf.format(nowCal.getTime()) + " attached", "owditadmin@owd.com", result.toString().getBytes(), filename, "text/csv");




                        //log.debug("ftp found " + files.size() + " files!");

                        //   Mailer.sendMailWithAttachment("OWD Shipping Report " + sqlDate, "Report for " + sqlDate + " attached", "dw@phion.com", header.toString().getBytes(), OWDUtilities.getYYYYMMDDFromSQLDate(sqlDate) + "InventoryLevel.txt", "application/x-qw");
                    }

                } catch (Throwable th) {
                    th.printStackTrace();
                    if (th.getMessage().toUpperCase().indexOf("DEADLOCK") >= 0) {
                        log.debug("DEADLOCK");
                    } else {
                        th.printStackTrace();
                        //notify of failure
                        try {
                          //  Mailer.sendMail("Phion Ship File Placement Failed!", "Failed at " + formatter.format(Calendar.getInstance().getTime()), "casetracker@owd.com", "do-not-reply@owd.com");
                        } catch (Exception exx) {
                            exx.printStackTrace();
                        }
                    }
                } finally {
                    try {
                        rs.close();
                    } catch (Exception ex) {
                    }
                    try {
                        stmt.close();
                    } catch (Exception ex) {
                    }
                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }

                }

            }

        }



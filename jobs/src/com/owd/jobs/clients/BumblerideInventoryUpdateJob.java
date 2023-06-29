package com.owd.jobs.clients;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.FtpConnector;
import ipworks.DirEntry;
import ipworks.Ftp;
import ipworks.IPWorksException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class BumblerideInventoryUpdateJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception{

     run();


    }


    FtpConnector ftp = new FtpConnector("ftp.owd.com", "bumbleride", "Hd7J90*", "OUT_FROM_OWD/REPORTS");


    public void internalExecute() {


        postInventoryLevelFile();


    }

    public void postInventoryLevelFile() {

         Calendar calendar = Calendar.getInstance();

        String header = "On Hand Inventory By SKU Summary\n" +
                "\n" +
                "Page by:\n" +
                "Customer: 141361\n" +
                "\n" +
                "\n" +
                "SKU||User Field 1|Barcode Field 1|Pack|Metrics|Qty|Full Pallets|Loose Cases|Qty, Allocated|Qty, Picked|Qty, Available|Qty, Unavailable|Catch Weight|Cases Per Pallet\n";

            boolean repeatExport = true;
            java.sql.Connection cxn = null;
            java.sql.PreparedStatement stmt = null;
            java.sql.ResultSet rs = null;

            while (repeatExport) {
                repeatExport = false;
                DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);

                log.debug("loop");
                try {
                    StringBuilder result = new StringBuilder();
                     result.append(header);

                    String reportSQL = "select inventory_num,description,upc_code,'1/1/1', '',qty_on_hand, 0,0,0,0,qty_on_hand,0,0,0 from owd_inventory i\n" +
                            " join owd_inventory_oh on inventory_fkey=inventory_id\n" +
                            " where client_fkey=575 order by inventory_num";

                    cxn = ConnectionManager.getConnection();

                    stmt = cxn.prepareStatement(reportSQL);
                   // stmt.setString(1, (providedDate==null?OWDUtilities.getSQLDateString(calendar.getTime()):providedDate));

                    stmt.executeQuery();

                    rs = stmt.getResultSet();
                    boolean gotResults = false;

                    int ttlQuantity = 0;
                    while (rs.next()) {

                        result.append(""+rs.getString(1) + "|");
                        result.append(rs.getString(2) + "|");
                        result.append(rs.getString(3) + "||");
                        result.append(rs.getString(4) + "|");
                        result.append(rs.getString(5) + "|");
                        result.append(rs.getString(6) + "|");
                        result.append(rs.getString(7) + "|");
                        result.append(rs.getString(8) + "|");
                        result.append(rs.getString(9) + "|");
                        result.append(rs.getString(10) + "|");
                        result.append(rs.getString(11) + "|");
                        result.append(rs.getString(12) + "|");
                        result.append(rs.getString(13) + "|");
                        result.append(rs.getString(14) + "\n");
                        gotResults = true;
                        ttlQuantity += rs.getInt(6);
                    }

                    rs.close();
                    stmt.close();
                    cxn.rollback();
                    result.append("Total||||||"+ttlQuantity+"|0|0|0|0|"+ttlQuantity+"|0|0|0|--\n");
//send to ftp site

                    if (gotResults) {
                        String filename = "BR_OnHandInventory_" + formatter.format(calendar.getTime())+".CSV";



                        ftp.putFileData(filename,result.toString().getBytes());
                        log.debug("file:" + filename);
                         Mailer.sendMailWithAttachment("OWD Inventory Status Report " + filename, "Report for " + formatter.format(calendar.getTime()) + " attached", "owditadmin@owd.com", result.toString().getBytes(), filename, "text/csv");




                        //log.debug("ftp found " + files.size() + " files!");

                        //   Mailer.sendMailWithAttachment("OWD Shipping Report " + sqlDate, "Report for " + sqlDate + " attached", "dw@phion.com", header.toString().getBytes(), OWDUtilities.getYYYYMMDDFromSQLDate(sqlDate) + "InventoryLevel.txt", "application/x-qw");
                    }

                } catch (Throwable th) {
                    th.printStackTrace();
                    if (th.getMessage().toUpperCase().indexOf("DEADLOCK") >= 0) {
                        log.debug("DEADLOCK");
                        repeatExport = true;
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

}

package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.ftp.FTPManager;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhionShipmentsUpdateJob   extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception{

  //   run();
       //   internalExecute(today);
    //    internalExecute("2006-5-26");
         PhionShipmentsUpdateJob job = new PhionShipmentsUpdateJob();
       job.providedDate = ("2015-5-11");
       job.providedFilenamePrefix = ("05112015");
       //  job.internalExecute();




        //   internalExecute(OWDUtilities.getSQLDateForToday());
    }

    String providedDate = null;
    String providedFilenamePrefix = null;
public void internalExecute() {

        //String is the day the report should be for.
        GregorianCalendar calendar = new GregorianCalendar();
        //calendar.add(Calendar.DATE,-1);

        boolean repeatExport = true;
        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        while (repeatExport) {
            repeatExport = false;
            DateFormat formatter = new SimpleDateFormat("MMddyyyy", Locale.US);
            DecimalFormat numformatter = new DecimalFormat("000000");

            log.debug("loop");
            try {
                StringBuffer result = new StringBuffer();


                String reportSQL = "select order_refnum,case when ISNULL(tracking_no,'')='NONE' then  'TRACKINGNOTAVAILABLE' else isnull(tracking_no,'')  end,replace(convert(varchar,shipped_on,101),'/','')\n" +
                        "from owd_order join owd_order_track t on order_id=order_fkey where shipped_on = ? and isnull(is_shipping,0)=0 and t.is_void=0 and client_fkey=179 and 1=isnumeric(left(order_refnum,1)) ";

                cxn = ConnectionManager.getConnection();

                stmt = cxn.prepareStatement(reportSQL);
                stmt.setString(1, (providedDate==null?OWDUtilities.getSQLDateString(calendar.getTime()):providedDate));

                stmt.executeQuery();

                rs = stmt.getResultSet();
                boolean gotResults = false;

                while (rs.next()) {

                    result.append(rs.getString(1) + ",");
                    result.append(rs.getString(2) + ",");
                    result.append(rs.getString(3) + "\r\n");
                    gotResults = true;

                }

                rs.close();
                stmt.close();
                cxn.rollback();

//send to ftp site

                if (gotResults) {
                    String filename = "phion" + (providedFilenamePrefix==null?formatter.format(calendar.getTime()):providedFilenamePrefix) + "-";
                    log.debug("generated file name:"+filename);
                    int currIndex = 1;

                    FTPManager ftp = new FTPManager("owd.com", "phion", "runproof2");
                    ftp.setReadDirectory("/docroot_prochemlabs/orders_shipped");
                    ftp.setWriteDirectory("/docroot_prochemlabs/orders_shipped");
                      Vector files = ftp.getFileNames();
                    //log.debug("ftp found " + files.size() + " files!");

                    for (int i = 0; i < files.size(); i++) {
                        //log.debug("FTP found file "+i+" " + files.get(i));
                        if (files.get(i).toString().startsWith(filename)
                                && files.get(i).toString().indexOf("-") > 0
                                && files.get(i).toString().endsWith(".csv")) {
                            String intString = files.get(i).toString().substring(files.get(i).toString().indexOf("-")+ 1).replaceAll(".csv","");
                            //log.debug("int="+intString);
                            try {
                                int lastIndex = new Integer(intString).intValue();
                                if (lastIndex >= currIndex) {
                                    currIndex = lastIndex + 1;
                                }
                            } catch (Exception ee) {
                            }
                        }
                    }
                    //log.debug("done checking filenames");



                    filename = filename + numformatter.format(currIndex) + ".csv";


                    log.debug("file:" + filename);
                    ftp.writeFile(filename, result.toString().getBytes());

                   
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

package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 13, 2006
 * Time: 11:36:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class JimScharfRevShareNotificationJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }

    String providedDate = null;
    String providedFilenamePrefix = null;
     public void internalExecute() {

                 //String is the day the report should be for.
                 GregorianCalendar calendar = new GregorianCalendar();
                 calendar.add(Calendar.DATE,-1);

                 boolean repeatExport = true;
                 java.sql.Connection cxn = null;
                 java.sql.PreparedStatement stmt = null;
                 java.sql.ResultSet rs = null;

                 while (repeatExport) {
                     repeatExport = false;
                     DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
                     DecimalFormat numformatter = new DecimalFormat("000000");

                     log.debug("loop");
                     try {
                         StringBuffer result = new StringBuffer();


                         String reportSQL = "select ct.dnis,replace([number],'-',''),src,convert(varchar,contact_initiated,120),round(contact_minutes*60,0),'',case when contact_outcome='Contact' then result_reason\n" +
                                 "else 'Disconnect' end from cc_cl_contacts cc\n" +
                                 "join cctest ct \n" +
                                 "left outer join cc_dnis_numbers l on ct.dnis=l.dnis\n" +
                                 "on ct.uniqueid=cc.contact_id\n" +
                                 "\n" +
                                 "where client_id=54 and contact_type='VOICE' and contact_initiated_date=? ";

                         cxn = ConnectionManager.getConnection();

                         stmt = cxn.prepareStatement(reportSQL);
                         stmt.setString(1, (providedDate==null? OWDUtilities.getSQLDateString(calendar.getTime()):providedDate));

                         stmt.executeQuery();

                         rs = stmt.getResultSet();
                         boolean gotResults = false;

                         while (rs.next()) {

                             result.append(rs.getString(1) + ",");
                             result.append(rs.getString(2) + ",");
                             result.append(rs.getString(3) + ",");
                             result.append(rs.getString(4) + ",");
                             result.append(rs.getString(5) + ",");
                             result.append(rs.getString(6) + ",");
                             result.append(rs.getString(7) + "\r\n");
                             gotResults = true;

                         }

                         rs.close();
                         stmt.close();
                         cxn.rollback();

//send to ftp site


                             String filename = "" + (providedFilenamePrefix==null?formatter.format(calendar.getTime()):providedFilenamePrefix) + "EZWRAPTV.TXT";
                             log.debug("generated file name:"+filename);

                             FTPManager ftp = new FTPManager("ftp.amntv.com", "ezwrap", "gilt2?Miriam");
                             ftp.setReadDirectory("/");
                             ftp.setWriteDirectory("/");

                             log.debug("file:" + filename);
                             log.debug(result);
                             ftp.writeFile(filename, result.toString().getBytes());
                          if (gotResults) {
                         }else
                         {
                             log.debug("no data!");
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

package com.owd.jobs.clients;

import com.owd.core.Mailer;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.FtpConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.owd.jobs.OWDStatefulJob.run;

/**
 * Created by Sean on 12/26/2018
 * Description: generate monthly report for Lyft to post on ftp
 */

public class LyftMonthlyReportJob extends OWDStatefulJob{

    private final static Logger log =  LogManager.getLogger();

    public static void main (String[] args) { run(); }

    FtpConnector ftp = new FtpConnector("35.162.109.175", "lyftreports", "lyftFTP!!", "files");

    public void internalExecute() {

        getLyftMonthlyReport ();

    }

    public void getLyftMonthlyReport () {


        java.sql.Connection cxn = null;


        java.sql.PreparedStatement ps = null;

        ResultSet rs = null;

        try {

            cxn = ConnectionManager.getConnection();

            Calendar c = Calendar.getInstance();

            DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);


            // Get first day of previous month
            c.add(Calendar.MONTH, -1);
            c.set(Calendar.DATE, 1);
            String firstDateOfPreviousMonth =  formatter.format(c.getTime());

            // Get last day of previous month
            c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            String lastDateOfPreviousMonth = formatter.format(c.getTime());

            StringBuilder result = new StringBuilder();

            // NOTE: comment out address info as Jared requested
            String header ="bill_first_name,bill_last_name,bill_company_name," +
//                    "bill_address_one,bill_address_two,bill_city,bill_state,bill_zip,"+
//                    "tracking_nums," +
                    "order_refnum,shipped_on,udf_getOrderShippedSkuList," +
//                    "carr_service," +
                    "shipped_cost"+"\n";

            result.append(header);



            String sql = "select " +
                    "bill_first_name,+\' \'+" +
                    "bill_last_name," +
                    "bill_company_name," +
//                    "bill_address_one," +
//                    "bill_address_two," +
//                    "bill_city," +
//                    "bill_state," +
//                    "bill_zip," +
//                    "o.tracking_nums," +
                    "order_refnum," +
                    "o.shipped_on," +
                    "\n" +
                    "OWD.dbo.udf_getOrderShippedSkuList(o.order_id)," +
//                    "carr_service," +
                    "o.shipped_cost," +
                    "isnull((select sum(amount) " +
                    "from owdbill_shipping_trans " +
                    "where order_fkey=order_id),0)\n" +
                    "from owd_order o " +
                    "join owd_order_ship_info s on s.order_fkey=order_id\n" +
                    "where client_fkey=529 " +
                    "and o.shipped_on>=\'" + firstDateOfPreviousMonth + "\' " +
                    "and o.shipped_on<=\'" + lastDateOfPreviousMonth + "\'";

            ps = cxn.prepareStatement(sql);
            ps.executeQuery();
            rs = ps.getResultSet();


            boolean gotResults = false;
            while (rs.next()){

                result.append("\""+ rs.getString(1) + "\"" + ",");
                result.append("\""+ rs.getString(2) + "\"" + ",");
                result.append("\""+ rs.getString(3) + "\"" + ",");
                result.append("\""+ rs.getString(4) + "\"" + ",");
                result.append("\""+ rs.getString(5) + "\"" + ",");
                result.append("\""+ rs.getString(6) + "\"" + ",");
                result.append("\""+ rs.getString(7) + "\"" + "\n");
//                result.append("\""+ rs.getString(7) + "\"" + ",");
//                result.append("\""+ rs.getString(8) + "\"" + ",");
//                result.append("\""+ rs.getString(10) + "\""+ ",");
//                result.append("\""+ rs.getString(11) + "\"" + ",");
//                result.append("\""+ rs.getString(12) + "\"" + ",");
//                result.append("\""+ rs.getString(13) + "\"" + ",");
//                result.append("\""+ rs.getString(14) + "\"" + "\n");
                gotResults = true;
            }

            rs.close();
            ps.close();
            cxn.close();

            Calendar calendar = Calendar.getInstance();

            if (gotResults) {
                String filename = "Lyft_Monthly_Report" + formatter.format(calendar.getTime())+".CSV";

                ftp.putFileData(filename,result.toString().getBytes());
                log.debug("file:" + filename);

//                Mailer.sendMailWithAttachment("Lyft Monthly Report " + filename, "Report for " +
//                        formatter.format(calendar.getTime()) + " attached", "owditadmin@owd.com",
//                        result.toString().getBytes(), filename, "text/csv");

            }

        }catch (Exception ex) {

            ex.printStackTrace();

        }finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                ps.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }

        }

    }
}

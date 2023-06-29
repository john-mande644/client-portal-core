package com.owd.jobs.clients;

import com.owd.core.Mailer;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.owd.jobs.OWDStatefulJob.run;

/**
 * Sean created on 6/19/2019
 * Send weekly report
 * Track for Lyft 150 orders showing pre-transit
 */
public class LyftWeeklyOutboundTrackingJob extends OWDStatefulJob {

    public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args){ run(); }

    public void internalExecute(){
        CreateOutboundTracking();
    }

    public void CreateOutboundTracking(){

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement ps = null;
        String CLIENT_ID = "529";
        String ORDER_STATUS ="pre_transit";
        ResultSet rs = null;

        try {

            cxn = ConnectionManager.getConnection();

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR ,0) ;
            today.set(Calendar.HOUR_OF_DAY ,0)  ;
            today.set(Calendar.MINUTE ,0)  ;
            today.set(Calendar.SECOND ,0)    ;
            today.set(Calendar.MILLISECOND ,0)    ;

            // ============ Get period range ================
            while(today.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                today.add(Calendar.DATE, -1);
            }
            today.add(Calendar.DATE, 1);
            Date firstDatePastPeriod = today.getTime();

            today.add(Calendar.DATE, -1);
            while(today.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                today.add(Calendar.DATE, -1);
            }
            Date firstDateOfPeriod = today.getTime();

//            String weekBeginDate = "2019-07-01";
//            String weekEndDate = "2019-07-31";

            String weekBeginDate = df.format(firstDateOfPeriod);
            String weekEndDate = df.format(firstDatePastPeriod);

            // ============== Header ==========================

            StringBuilder result = new StringBuilder();
            String header ="shipped_on," +
                    "order_num," +
                    "order_id," +
                    "order_refnum," +
                    "inventory_num," +
                    "status," +
                    "ship city," +
                    "company name," +
                    "tracking_no," +
                    "AB_shipment"+
                    "tracking_id"+
                    "\n";
            result.append(header);

            // ============= Cells  ========================
            String sql = "SELECT dbo.owd_order.shipped_on, " +
                    "dbo.owd_order.order_num, " +
                    "owd_order.order_id, " +
                    "dbo.owd_order.order_refnum, " +
                    "dbo.owd_line_item.inventory_num, " +
                    "dbo.owd_order_track_current_status.status, " +
                    "owd_order_ship_info.ship_city, " +
                    "owd_order_ship_info.ship_company_name, " +
                    "tracking_no, " +
                    "isnull(AB_shipment,'-1'), " +
                    "owd_order_track.tracking_id " +
                    "FROM dbo.owd_order " +
                    "INNER JOIN dbo.owd_order_track " +
                    "ON ( dbo.owd_order.order_id = dbo.owd_order_track.order_fkey) " +
                    "INNER JOIN dbo.owd_line_item " +
                    "ON (dbo.owd_order.order_id  = dbo.owd_line_item.order_fkey) " +
                    "INNER JOIN dbo.owd_order_ship_info " +
                    "ON (dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey) " +
                    "INNER JOIN dbo.owd_order_track_current_status " +
                    "ON ( dbo.owd_order_track.tracking_id = dbo.owd_order_track_current_status.tracking_id) " +
                    "LEFT JOIN owd_order_packs " +
                    "ON ( owd_order.order_id = owd_order_packs.order_fkey) " +
                    "WHERE dbo.owd_order.shipped_on > '" + weekBeginDate+ "' " +
                    "AND dbo.owd_order.shipped_on < '" + weekEndDate + "' " +
                    "AND dbo.owd_order.client_fkey = " + CLIENT_ID +
                    "AND owd_order_track_current_status.status = '"+ ORDER_STATUS +"' " +
                    "AND AB_shipment = 1 " +
                    "ORDER BY dbo.owd_order.shipped_on ASC";

            ps = cxn.prepareStatement(sql);
            ps.executeQuery();
            rs = ps.getResultSet();

            // Todo: (Sean) fix hard coded column number
            int COL_NUM = 11;

            boolean gotResults = false;
            while (rs.next()){

                for (int i = 1; i<= COL_NUM; i++){
                    if (i < COL_NUM){
                        result.append("\""+ rs.getString(i) + "\"" + ",");
                    }else if (i==COL_NUM){
                        result.append("\""+ rs.getString(i) + "\"" + "\n");
                    }
                }
                gotResults = true;
            }

            rs.close();
            ps.close();
            cxn.close();

            // ==================== Email report =============================

            Calendar calendar = Calendar.getInstance();
            if (gotResults) {
                String FILENAME = "Lyft_Weekly_Outbound_Tracking Report " + df.format(calendar.getTime())+".csv";

                Mailer.sendMailWithAttachment(FILENAME , "Report for " +
                        df.format(calendar.getTime()) + " attached", "grodriguez@owd.com",
//                        df.format(calendar.getTime()) + " attached", "sean.chen@owd.com",
                        result.toString().getBytes(), FILENAME, "text/csv");
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

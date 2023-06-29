package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.DailyFijiCountReportDefinition;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;
import com.owd.jobs.jobobjects.excelreports.ExcelReportSender;
import com.owd.jobs.jobobjects.excelreports.SymphonyDailyOrderIssueReportDefinition;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by danny on 8/12/2016.
 */
public class SymphonyDailyOrderIssueSpreadsheetJob extends OWDStatefulJob {

    public static void main(String[] args) {
        run();


        //  ExcelUtils.deliverReports(Arrays.asList(new DailyFijiCountReportDefinition("DC7")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com"))));


        //  ExcelUtils.deliverReports(Arrays.asList(new DailyFijiCountReportDefinition("DC6")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("owditadmin@owd.com"))));




    }

    public void internalExecute() {

        try {
            List l = new ArrayList();

            String groupsql ="SELECT distinct\n" +
                    "   \n" +
                    "    dbo.owd_order.group_name\n" +
                    "FROM\n" +
                    "    dbo.owd_order_ship_holds\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.owd_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order_ship_holds.order_fkey = dbo.owd_order.order_id)\n" +
                    "WHERE\n" +
                    "    dbo.owd_order.client_fkey = 489\n" +
                    "AND dbo.owd_order_ship_holds.is_on_wh_hold = 1\n" +
                    "AND dbo.owd_order_ship_holds.wh_hold_reason = 'Inventory Stockout' ;";
            try{
                Query q = HibernateSession.currentSession().createSQLQuery(groupsql);
                l = q.list();
                System.out.println(l.size()+ "   size of the list");
            }catch(Exception e){
                e.printStackTrace();
            }


            for(Object s:l) {
                System.out.println(s);


                ExcelUtils.deliverReports(Arrays.asList(new SymphonyDailyOrderIssueReportDefinition(s.toString())), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("stockouts@symphonypartners.zendesk.com"))));

            }

           // ExcelUtils.deliverReports(Arrays.asList(new DailyFijiCountReportDefinition("DC6")),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("mesquibel@owd.com","rarambula@owd.com","rdelatorre@owd.com"))));




        } catch (Exception ex) {
            Exception exx = new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "489", "Daily Symphony Order Issues", LogableException.errorTypes.INTERNAL);
        }

    }
}

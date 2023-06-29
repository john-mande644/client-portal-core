package com.owd.jobs.jobobjects.excelreports;

import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/11/16.
 */
public class FOHSLAReportDefinition extends ReportDefinition {
    public static void main(String[] args){


      //  ExcelUtils.deliverReports(Arrays.asList(new CalifiaLotWeeklyReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));

        ExcelUtils.deliverReports(Arrays.asList(new FOHSLAReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));







    }

    public FOHSLAReportDefinition() {

           this(0);
    }
        public FOHSLAReportDefinition(int daysOffset) {

            super();

            clientName = "Fredericks";

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df2 = new SimpleDateFormat("HH:mm MM-dd-yyyy");
            Calendar aCalendar = Calendar.getInstance();
            //  aCalendar.add(Calendar.MONTH, -1);
            aCalendar.add(Calendar.DATE, daysOffset);
            Date endDate = aCalendar.getTime();

            //  aCalendar.add(Calendar.DATE, -1);
            Date startDate = aCalendar.getTime();

            periodTitle = "SLA as of " + df2.format(startDate);
            subject = "SLA as of " + df2.format(startDate);

            try {
                System.out.println(df.format(endDate)+"ffffffffffffffffffffffffffffffffffffff");
                SheetDefinition sheet = new SheetDefinition();
                sheet.clientFkey = 489;
                sheet.sheetTitle = "SLA";
                sheet.sheetName = "SLA";
                sheet.query = "select convert(date,sla) as 'Sla', count(*) as Orders, \n" +
                        "sum(\n" +
                        "Case when pick_status = 2 then 1 else 0 end) as Picked,\n" +
                        "sum(Case when order_status = 'Shipped' then 1 else 0 end) as Shipped\n" +
                        "\n" +
                        "from owd_order where convert(date,sla) >= '"+df.format(startDate)+"'\n" +
                        "and group_name = 'fredericks' and is_void = 0\n" +
                        "group by convert(date,sla)\n" +
                        "\n" +
                        "union\n" +
                        "select convert(date,sla) as 'Sla', count(*) as Orders, \n" +
                        "sum(\n" +
                        "Case when pick_status = 2 then 1 else 0 end) as Picked,\n" +
                        "sum(Case when order_status = 'Shipped' then 1 else 0 end) as Shipped\n" +
                        "\n" +
                        "from owd_order where ((convert(date,sla) < '"+df.format(startDate)+"' and order_status = 'At Warehouse') or (convert(date,sla) < '"+df.format(startDate)+"' and shipped_on = '"+df.format(startDate)+"'))\n" +
                        "and group_name = 'fredericks' and is_void = 0\n" +
                        "group by convert(date,sla)\n" +
                        "\n" +
                        "order by 'Sla'";
                sheets.add(sheet);

            }catch(Exception e){
                System.out.println("Nothing found for packages");

            }



        }

        }

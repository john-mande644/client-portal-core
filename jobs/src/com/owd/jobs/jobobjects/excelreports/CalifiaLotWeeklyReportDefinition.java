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
public class CalifiaLotWeeklyReportDefinition extends ReportDefinition {
    public static void main(String[] args){


      //  ExcelUtils.deliverReports(Arrays.asList(new CalifiaLotWeeklyReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));

        ExcelUtils.deliverReports(Arrays.asList(new CalifiaLotWeeklyReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("leonard@califiafarms.com","jessel@califiafarms.com"," dmorales@califiafarms.com"))));







    }

    public CalifiaLotWeeklyReportDefinition() {

           this(0);
    }
        public CalifiaLotWeeklyReportDefinition(int daysOffset) {

            super();

            clientName = "Califia";

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Calendar aCalendar = Calendar.getInstance();
            //  aCalendar.add(Calendar.MONTH, -1);
            aCalendar.add(Calendar.DATE, daysOffset);
            Date endDate = aCalendar.getTime();

            //  aCalendar.add(Calendar.DATE, -1);
            Date startDate = aCalendar.getTime();

            periodTitle = " Lots For " + df.format(startDate);
            subject = "Weekly Lot Report " + df.format(startDate);

            try {
                System.out.println(df.format(endDate)+"ffffffffffffffffffffffffffffffffffffff");
                SheetDefinition sheet = new SheetDefinition();
                sheet.clientFkey = 489;
                sheet.sheetTitle = "Lot Report";
                sheet.sheetName = "Lot Report";
                sheet.query = "SELECT\n" +
                        "   dbo.owd_inventory.inventory_num,\n" +
                        "   dbo.owd_inventory_facility.qty as \"On Hand\",\n" +
                        "   dbo.owd_lot_inventory.lot_value,\n" +
                        "   dbo.owd_inventory_facility_lot.qty as \"Lot On Hand\"\n" +
                        "FROM\n" +
                        "   dbo.owd_inventory\n" +
                        "LEFT OUTER JOIN\n" +
                        "   dbo.owd_inventory_facility\n" +
                        "ON\n" +
                        "   (\n" +
                        "       dbo.owd_inventory.inventory_id = dbo.owd_inventory_facility.inventory_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "   dbo.owd_inventory_facility_lot\n" +
                        "ON\n" +
                        "   (\n" +
                        "       dbo.owd_inventory.inventory_id = dbo.owd_inventory_facility_lot.inventory_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "   dbo.owd_lot_inventory\n" +
                        "ON\n" +
                        "   (\n" +
                        "       dbo.owd_inventory_facility_lot.lot_fkey = dbo.owd_lot_inventory.id)\n" +
                        "WHERE\n" +
                        "   dbo.owd_inventory.group_name = 'G_califiafarmsV2223'\n" +
                        "AND dbo.owd_inventory_facility.facility_fkey = 2\n" +
                        "AND dbo.owd_inventory_facility_lot.facility_fkey = 2 and\n" +
                        "dbo.owd_inventory_facility_lot.qty>0;";
                sheets.add(sheet);

            }catch(Exception e){
                System.out.println("Nothing found for packages");

            }



        }

        }

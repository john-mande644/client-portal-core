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
public class DailyLotErrorReportDefinition extends ReportDefinition {
    public static void main(String[] args){


     //   ExcelUtils.deliverReports(Arrays.asList(new DailyLotErrorReportDefinition()), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));

    }

    public DailyLotErrorReportDefinition() {

           this(-1);
    }
        public DailyLotErrorReportDefinition(int daysOffset) {

            super();

            clientName = "OWD";

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Calendar aCalendar = Calendar.getInstance();
            //  aCalendar.add(Calendar.MONTH, -1);
            aCalendar.add(Calendar.DATE, daysOffset);
            Date endDate = aCalendar.getTime();

            //  aCalendar.add(Calendar.DATE, -1);
            Date startDate = aCalendar.getTime();

            periodTitle = " For " + df.format(startDate) + " through EOD " + df.format(endDate);
            subject = "Daily Lot Problem Report" + df.format(endDate);

            try {
                System.out.println(df.format(endDate)+"ffffffffffffffffffffffffffffffffffffff");
                SheetDefinition sheet = new SheetDefinition();
                sheet.clientFkey = 55;
                sheet.sheetTitle = "Packages";
                sheet.sheetName = "packages";
                sheet.query = "SELECT\n" +
                        "    dbo.owd_order.order_num,\n" +
                        "    dbo.owd_order.facility_code,\n" +
                        "    dbo.owd_order.actual_order_date,\n" +
                        "    ISNULL(dbo.owd_order.shipped_on,'') as shippedon,\n" +
                        "    dbo.package.pack_barcode,\n" +
                        "    dbo.owd_line_item.inventory_num,\n" +

                        "    SUM(dbo.owd_lot_packageline.qty)\n" +
                        "FROM\n" +
                        "    dbo.package_order\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.package\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.package_line\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.package.id = dbo.package_line.package_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_line_item\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.package_line.owd_line_item_fkey = dbo.owd_line_item.line_item_id)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_inventory\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\n" +
                        "INNER JOIN\n" +
                        "    dbo.owd_order\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.package_order.owd_order_fkey = dbo.owd_order.order_id)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_lot_packageline\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.package_line.id = dbo.owd_lot_packageline.package_line_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_lot_inventory\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_lot_packageline.lot_fkey = dbo.owd_lot_inventory.id)\n" +
                        "WHERE\n" +
                        "    dbo.owd_inventory.lot_tracking = 1\n" +
                        "AND dbo.package_order.pack_start > '" + df.format(endDate) + "'\n" +
                        "AND dbo.package_order.is_void = 0\n" +
                        "and lot_value is null\n" +
                        "GROUP BY\n" +
                        "    dbo.owd_order.order_num,\n" +
                        "    dbo.owd_order.facility_code,\n" +
                        "    dbo.owd_order.actual_order_date,\n" +
                        "    dbo.owd_order.shipped_on,\n" +
                        "    dbo.package.pack_barcode,\n" +
                        "    dbo.owd_line_item.inventory_num ;";
                sheets.add(sheet);

            }catch(Exception e){
                System.out.println("Nothing found for packages");

            }
            try {
                SheetDefinition sheet = new SheetDefinition();
                sheet.clientFkey = 55;
                sheet.sheetTitle = "Ltls";
                sheet.sheetName = "LTL";
                sheet.query = "SELECT\n" +
                        "    dbo.owd_order.order_id,\n" +
                        "    dbo.owd_order.order_num,\n" +
                        "    dbo.owd_order.facility_code\n" +
                        "FROM\n" +
                        "    dbo.owd_order\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_line_item\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_order.order_id = dbo.owd_line_item.order_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_inventory\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.package_order\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_order.order_id = dbo.package_order.owd_order_fkey)\n" +
                        "WHERE\n" +
                        "    dbo.owd_order.shipped_on > '" + df.format(endDate) + "'\n" +
                        "AND dbo.owd_inventory.lot_tracking = 1\n" +
                        "AND dbo.package_order.owd_order_fkey IS NULL ;";
                sheets.add(sheet);

            }catch(Exception e){
                System.out.println("Nothing found for LOts");

            }
            try {
                SheetDefinition sheet = new SheetDefinition();
                sheet.clientFkey = 55;
                sheet.sheetTitle = "Adjustments";
                sheet.sheetName = "Adjustments";
                sheet.query = "SELECT\n" +
                        "    dbo.owd_inventory.inventory_num,\n" +
                        "    dbo.owd_inventory_history.note,\n" +
                        "    dbo.owd_inventory_history.date_applied,\n" +
                        "    dbo.owd_receive.receive_id,\n" +
                        "    dbo.owd_receive.receive_date,\n" +
                        "    dbo.owd_receive.receive_user\n" +
                        "FROM\n" +
                        "    dbo.owd_inventory\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_inventory_history\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_history.inventory_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_receive_item\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory_history.receive_item_fkey = dbo.owd_receive_item.receive_item_id)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_receive\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_receive_item.receive_fkey = dbo.owd_receive.receive_id)\n" +
                        "WHERE\n" +
                        "    dbo.owd_inventory.lot_tracking = 1\n" +
                        "AND dbo.owd_inventory_history.date_applied > '" + df.format(endDate) + "'\n" +
                        "AND dbo.owd_inventory_history.lot_fkey IS NULL ;";
                sheets.add(sheet);

            }catch(Exception e){
                System.out.println("Nothing found for LOts");

            }



        }

        }

package com.owd.jobs.jobobjects.excelreports;

import com.owd.core.managers.FacilitiesManager;
import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class DailyUnlocatedInventory extends MonthlyOwdBillingReportDefinition {


    public static void main(String[] args) {


        /*Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.DATE, -3);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        Date firstDatePastPeriod = firstDateOfPreviousMonth;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       String  periodTitle = df.format(firstDateOfPreviousMonth);
        System.out.println("Billing Report for " + periodTitle);*/
        ExcelUtils.deliverReports(Arrays.asList(new DailyUnlocatedInventory()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("dnickels@owd.com"))));
      //  ExcelUtils.deliverReports(Arrays.asList(new  DailyUnlocatedInventory()),Arrays.asList(new ExcelReportSlackSender(Arrays.asList("C08CW5KDM"))));

    }


    public DailyUnlocatedInventory() {
        
        try {
            Calendar aCalendar = Calendar.getInstance();
           // aCalendar.add(Calendar.DATE, -1);
            firstDateOfPreviousMonth = aCalendar.getTime();
            firstDatePastPeriod = firstDateOfPreviousMonth;

            periodTitle = df.format(firstDateOfPreviousMonth);
            subject = "Un-Located Report for " + periodTitle;


            clientName = "OWD";
            sheets = new ArrayList<>();
            SheetDefinition sheet = null;
            for (String facility : FacilitiesManager.getActivePublicFacilityCodes()) {
                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+" Un-located";
                sheet.sheetName = clientName + " "+facility+ " Un-located ";
                sheet.query = "SELECT\n" +
                        "    dbo.owd_inventory.inventory_id,\n" +
                        "    dbo.owd_inventory.inventory_num,\n" +
                        "    dbo.owd_inventory.description,\n" +
                        "    dbo.owd_client.company_name,\n" +

                        "    MAX(dbo.owd_inventory_facility.qty) as qty\n" +
                        "FROM\n" +
                        "    dbo.owd_inventory\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_inventory_locations\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_locations.inventory_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_inventory_facility\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_facility.inventory_fkey)\n" +
                        "INNER JOIN\n" +
                        "    dbo.owd_facilities\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory_facility.facility_fkey = dbo.owd_facilities.id)\n" +
                        "INNER JOIN\n" +
                        "    dbo.owd_client\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory.client_fkey = dbo.owd_client.client_id and owd_client.is_active = 1 and owd_client.client_id <>112)\n" +
                        "WHERE\n" +
                        "    dbo.owd_inventory.is_auto_inventory = 0\n" +
                        "AND dbo.owd_inventory.is_active = 1\n" +
                        "AND dbo.owd_inventory_facility.qty > 0\n" +
                        "AND dbo.owd_facilities.loc_code = '"+facility+"'\n" +
                        "and inventory_num <> 'mail' and inventory_num <> 'misc' and inventory_num <>'pps'\n" +
                        " \n" +
                        "GROUP BY\n" +
                        "    dbo.owd_inventory.inventory_id,\n" +
                        "    dbo.owd_inventory.inventory_num,\n" +
                        "    dbo.owd_inventory.description,\n" +
                        "    dbo.owd_client.company_name\n" +
                        "    \n" +
                        "    \n" +
                        "    having COUNT(dbo.owd_inventory_locations.id) = 0\n" +
                        "    order by company_name\n" +
                        "     ;";
                sheets.add(sheet);


                sheet = new SheetDefinition();
                sheet.sheetTitle = clientName + " "+facility+" Zero qty Located";
                sheet.sheetName = clientName + " "+facility+ " Zero qty Located ";
                sheet.query = "SELECT\n" +
                        "    dbo.owd_inventory.inventory_id,\n" +
                        "    dbo.owd_inventory.inventory_num,\n" +
                        "    dbo.owd_inventory.description,\n" +
                        "    dbo.owd_client.company_name,\n" +

                        "    MAX(dbo.owd_inventory_facility.qty) as qty\n" +
                        "FROM\n" +
                        "    dbo.owd_inventory\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_inventory_locations\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_locations.inventory_fkey)\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_inventory_facility\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_facility.inventory_fkey)\n" +
                        "INNER JOIN\n" +
                        "    dbo.owd_facilities\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory_facility.facility_fkey = dbo.owd_facilities.id)\n" +
                        "INNER JOIN\n" +
                        "    dbo.owd_client\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_inventory.client_fkey = dbo.owd_client.client_id and owd_client.is_active = 1 and owd_client.client_id <>112)\n" +
                        "WHERE\n" +
                        "    dbo.owd_inventory.is_auto_inventory = 0\n" +
                        "AND dbo.owd_inventory.is_active = 1\n" +
                        "AND dbo.owd_inventory_facility.qty = 0\n" +
                        "AND dbo.owd_facilities.loc_code = '"+facility+"'\n" +
                        "and inventory_num <> 'mail' and inventory_num <> 'misc' and inventory_num <>'pps'\n" +
                        "and dbo.udf_getFacilityIdForLocation(REPLACE(owd_inventory_locations.location,'//','')) = owd_facilities.wloc_node_fkey" +
                        " \n" +
                        "GROUP BY\n" +
                        "    dbo.owd_inventory.inventory_id,\n" +
                        "    dbo.owd_inventory.inventory_num,\n" +
                        "    dbo.owd_inventory.description,\n" +
                        "    dbo.owd_client.company_name\n" +
                        "    \n" +
                        "    \n" +
                        "    having COUNT(dbo.owd_inventory_locations.id) > 0\n" +
                        "    order by company_name\n" +
                        "     ;";
                sheets.add(sheet);

            }





        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

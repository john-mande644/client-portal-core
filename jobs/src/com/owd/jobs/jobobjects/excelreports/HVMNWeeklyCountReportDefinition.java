package com.owd.jobs.jobobjects.excelreports;

import com.owd.core.managers.FacilitiesManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Sean Chen on 5/9/2019.
 */
public class HVMNWeeklyCountReportDefinition extends ReportDefinition {



    public HVMNWeeklyCountReportDefinition() throws Exception {

        super();

        clientName = "HVMN ";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar aCalendar = Calendar.getInstance();
        Date reportDate = aCalendar.getTime();

        periodTitle = " For " + df.format(reportDate);
        subject = " Weekly Lot Count " + df.format(reportDate);
        sheets = new ArrayList<>();
        SheetDefinition sheet = null;

        // For All DCs
        // for (String facility : FacilitiesManager.getActivePublicFacilityCodes()) {

        // For DC6 and DC7 only. HVMN doesn't use DC1.
        ArrayList<String> DC_list = new ArrayList();
        DC_list.add("DC6");
        DC_list.add("DC7");

        for (int i=0; i<DC_list.size();i++) {
            String facility_id = "";
            switch (DC_list.get(i)){
                case "DC6":
                    facility_id = "6";
                    break;
                case "DC7":
                    facility_id = "8";
                    break;
            }

            sheet = new SheetDefinition();
            sheet.sheetTitle = "Weekly Lot Count " + DC_list.get(i);
            sheet.sheetName = DC_list.get(i) ;
            sheet.query = "SELECT dbo.owd_inventory.inventory_num, " +
                    "dbo.owd_inventory_facility.qty as \"On Hand\", " +
                    "isnull(dbo.owd_lot_inventory.lot_value,'') as \"Lot Value\", " +
                    "isnull(dbo.owd_inventory_facility_lot.qty,'') as \"Lot On Hand\" " +
                    "FROM dbo.owd_inventory " +
                    "LEFT JOIN dbo.owd_inventory_facility " +
                    "ON ( dbo.owd_inventory.inventory_id = dbo.owd_inventory_facility.inventory_fkey ) " +
                    "LEFT JOIN dbo.owd_inventory_facility_lot " +
                    "ON ( dbo.owd_inventory.inventory_id = dbo.owd_inventory_facility_lot.inventory_fkey " +
                    "and dbo.owd_inventory_facility_lot.facility_fkey = " + facility_id + " ) " +
                    "LEFT JOIN dbo.owd_lot_inventory " +
                    "ON ( dbo.owd_inventory_facility_lot.lot_fkey = dbo.owd_lot_inventory.id ) " +
                    "WHERE client_fkey = 576 "+
                    "AND dbo.owd_inventory_facility.facility_fkey = " + facility_id + " "+
                    "AND ( dbo.owd_inventory_facility_lot.qty>0 or dbo.owd_inventory_facility_lot.qty is null )";

//            sheet.query = "SELECT TOP 10 dbo.owd_inventory.inventory_num FROM dbo.owd_inventory";    // test
            sheets.add(sheet);
        }

    }
}

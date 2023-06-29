package com.owd.jobs.jobobjects.excelreports;

import com.owd.jobs.jobobjects.excel.ExcelUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class MonthlySymphonyJBrandMovementsReportDefinition extends MonthlyReportDefinition {
    private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {
       // ExcelUtils.deliverReports(Arrays.asList(new MonthlySymphonyJBrandMovementsReportDefinition()),Arrays.asList(new ExcelReportEmailSender(Arrays.asList("finance@symphonycommerce.com"))));

    }
    public MonthlySymphonyJBrandMovementsReportDefinition(Date firstDatePastPeriod, Date firstDateOfPreviousMonth) {

        super(firstDatePastPeriod,firstDateOfPreviousMonth);
    }



    public MonthlySymphonyJBrandMovementsReportDefinition() {
        
        super();


        periodTitle = dfperiod.format(firstDateOfPreviousMonth);
        subject = "Inventory Movement Report for Month Starting "+periodTitle ;


        clientName = "JBrand";
        SheetDefinition sheet = new SheetDefinition();
        sheet.sheetTitle=clientName+" Inventory Receive Report" ;
        sheet.sheetName=clientName+" Receives " ;
        sheet.query=  "select client_ref, i.group_name,asn.facility_code, convert(datetime,convert(varchar,end_timestamp,101)), inventory_num,ri.qty_receive,i.upc_code\n" +
                "from asn join receive r join receive_item ri join owd_inventory i on i.inventory_id=inventory_fkey\n" +
                "on receive_fkey=r.id on asn_fkey=asn.id\n" +
                "where asn.client_fkey=625 and qty_receive>0\n" +
                "and end_timestamp>='"+df.format(firstDateOfPreviousMonth)+"' and end_timestamp<'"+df.format(firstDatePastPeriod)+"'\n";
        sheets.add(sheet);
        sheet = new SheetDefinition();
        sheet.sheetTitle=clientName+" Inventory Movement Report" ;
        sheet.sheetName=clientName+" Movements " ;
        sheet.query=  "sp_getinventorymovementsummarybygroupwithupc '"+df.format(firstDateOfPreviousMonth)+"', '"+df.format(lastDateOfPreviousMonth)+"', 625, 'G_jbrandV2186'";
        sheets.add(sheet);

    }
}

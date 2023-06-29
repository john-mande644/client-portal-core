package com.owd.jobs.jobobjects.excelreports;

import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/10/16.
 */
public class GildanSkuExemptionReportDefinition extends WeeklyReportDefinition {


    public GildanSkuExemptionReportDefinition(Date firstDatePastPeriod, Date firstDateOfPeriod) {

        super(firstDatePastPeriod,firstDateOfPeriod);
    }

    public GildanSkuExemptionReportDefinition() {
        
        super();
            subject = "Amazon Sku Exemption";

        clientName = "Gildan";
        SheetDefinition sheet = new SheetDefinition();
        sheet.clientFkey=471;
        sheet.sheetName="Gildan SKU Exemption" ;
        sheet.sheetTitle="Gildan SKU Exemption Summary" ;
        sheet.query=  "SELECT\n" +
                "    \n" +
                "    dbo.owd_line_item_exemptions.inventory_num,\n" +
                "    dbo.owd_line_item_exemptions.vendor_sku,\n" +
                "    dbo.owd_line_item_exemptions.exemptionCode,\n" +
                "    dbo.owd_line_item_exemptions.exemptionValue,\n" +
                "   sum( dbo.owd_line_item_exemptions.qty) as qty\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN\n" +
                "    dbo.owd_line_item_exemptions\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_line_item_exemptions.order_fkey) \n" +
                "        where actual_order_date > '"+ df.format(firstDateOfPeriod)+"'\n" +
                "        group by inventory_num, vendor_sku,exemptionCode,exemptionValue;";
        sheets.add(sheet);


    }
}

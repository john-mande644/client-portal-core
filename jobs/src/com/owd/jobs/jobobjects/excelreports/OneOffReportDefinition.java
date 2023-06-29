package com.owd.jobs.jobobjects.excelreports;

import com.owd.jobs.jobobjects.excel.ExcelUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stewartbuskirk1 on 3/7/16.
 */
public class OneOffReportDefinition  {

    public static void main(String[] args) {
        OneOffReportDefinition.execute();

    }

    public static void execute() {
        List<ReportDefinition> jobList = new ArrayList<ReportDefinition>();
        List<String> clients = Arrays.asList("AlexBlake.com","AlgaeCal","BAOL, LLC","Big Sexy Lingerie LLC","Biotanical Health","Blue Bottle Coffee","BMR Sports Nutrition AB","Breeze Comfort","Cuppow","Days of Wonder, Inc.","Dogeared.com","DOPE","EverythingDacor.com","EZUltrasound.com","Gildan USA","Gymnastic Bodies","Jim Scharf Holdings, Ltd.","Lifespan International US, LLC","Luscious Garden Inc.","Lyft, Inc. ","Marine Essentials","Maxton Men Inc","Model in a Bottle Inc","Nootrobox, Inc.","Nutrexin USA","One World Direct","One World Studios","Order of the Arrow Distribution Center","ORILI VENTURES LTD","OWD Columbus","OWD Mira Loma","Peace Hill Press","Perhaps Tonight, LLC","Phion Nutrition","Photojojo, LLC","Play Hawkers SL","PM Concepts LLC","PuddleDancer Press","Real Health Labs","Renu Herbs","Revolution Prep, LLC. ","RIE","Shower Power, LLC","Sleepy Planet","Storq Inc.","Summit University Press","Symphony Commerce","Talk Enterprises Inc.","Temperature@lert","TIB","Trans High Corporation");


        for(String client:clients) {

            ReportDefinition rd = new ReportDefinition();

          //  rd.emails.add("ktorevell@owd.com");
            rd.periodTitle = client+" Jan-Feb 2016 USPS Audit";
            rd.subject = "Jan-Feb 2016 USPS Audit";
            rd.clientName = client;
            SheetDefinition sheet = new SheetDefinition();
            sheet.sheetTitle="USPS Audit Adjustments" ;
            sheet.sheetName= client ;
            sheet.query="select \n" +
                    "order_num as 'OWD Order Ref',order_refnum as 'Client Order Ref',o.shipped_on as 'Ship Date',\n" +
                    "carr_service as 'Ship Method',\n" +
                    "count(*) as 'Packages',\n" +
                    "stuff.original as 'Original USPS Charges',\n" +
                    "sum(actual) as ' Final USPS Charges',\n" +
                    "sum(actual)-isnull(stuff.original,0.00) as 'Adjustment'\n" +
                    "from \n" +
                    " audit_uspsfeb2016 a \n" +
                    "  join owd_order_track t  with (nolock)   \n" +
                    "        join owd_order o  with (nolock) join owd_order_ship_info s on s.order_fkey=order_id\n" +
                    "        join owd_client c on client_id=client_fkey\n" +
                    "                left outer join (select order_fkey as oid,sum(amount) as 'original' from owdbill_shipping_trans st  with (nolock) group by order_fkey) as stuff on stuff.oid=order_id\n" +
                    "        on order_id=t.order_fkey\n" +
                    "on tracking_no=tracking and t.is_void=0 \n" +
                    "where company_name='" +client+"'\n"+
                    "group by order_num,order_refnum,o.shipped_on,carr_service,original\n" +
                    "having  (sum(actual)-isnull(stuff.original,0.00))>0";
            rd.sheets.add(sheet);

            jobList.add(rd);
        }
       // ExcelUtils.deliverReports(jobList);

    }
}

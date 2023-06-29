package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stewartbuskirk1 on 10/20/14.
 */
public class SymphonyAlertShortStockInsertsMailJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }

    public void internalExecute() {

        try {
            Map<String, String> reportMap = new HashMap<String, String>();


            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            DateFormat df = new SimpleDateFormat("yyyyMMdd");

            reportMap.put("OwdInsertsShortStock_" + df.format(cal.getTime()), "select vs.inventory_num as 'SKU',descr as 'Title',loc_code as 'Facility',vs.qty as 'Stock Available',convert(int,m.tendaytotal)  as '10 Day Mean Velocity',group_name as 'Brand Code',\n" +
                    " convert(int,round(vs.qty/tendaytotal,0)) as 'Days Remaining' from vw_stock_by_facility vs \n" +
                    " join  (select  inventory_id,max(description) as descr, ceiling(sum(quantity_actual)/10.00) as 'tendaytotal',facility_code,group_name from owd_line_item l join owd_order o on order_id=order_fkey\n" +
                    " and quantity_actual>0 and shipped_on>=dateadd(day,-10,getdate()) and cust_refnum='' and group_name<>'' group by inventory_id, facility_code,group_name) \n" +
                    " as m on m.inventory_id=vs.inventory_id and vs.loc_code=m.facility_code\n" +
                    " where client_fkey=489 and convert(int,round(vs.qty/tendaytotal,0))<=21\n" +
                    " order by convert(int,round(vs.qty/tendaytotal,0)) asc");

            ExcelUtils.mailExcelSheetsForQueries(reportMap, "OWD Daily Inserts Days Remaining " + df.format(cal.getTime()), "\r\nReport is attached.\r\n\r\n", "owditadmin@owd.com");
            ExcelUtils.mailExcelSheetsForQueries(reportMap, "OWD Daily Inserts Days Remaining " + df.format(cal.getTime()), "\r\nReport is attached.\r\n\r\n", "FOPS@symphonycommerce.com");
            ExcelUtils.mailExcelSheetsForQueries(reportMap, "OWD Daily Inserts Days Remaining " + df.format(cal.getTime()), "\r\nReport is attached.\r\n\r\n", "BSM@symphonycommerce.com");

        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "GENERIC",
                    ""+489,
                    this.getClass().getName(),
                    LogableException.errorTypes.INTERNAL);
        }
    }
}

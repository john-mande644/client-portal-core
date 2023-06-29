package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.skymall.SkymallApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stewartbuskirk1 on 10/20/14.
 */
public class TemperatureAlertDailySerialsMailJob extends OWDStatefulJob {
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

            reportMap.put("OWD_SerialNumbers_" + df.format(cal.getTime()), "SELECT\n" +
                    "    o.order_refnum as 'Order Reference',inventory_num as SKU,substring(s.serial_number,charindex(':',s.serial_number)+1,999) as 'Serial Number'\n" +
                    "FROM\n" +
                    "    dbo.owd_line_serial_link sl\n" +
                    "        join owd_line_item l join owd_order o on o.order_id=l.order_fkey and o.client_fkey=358 and o.shipped_on=convert(datetime,convert(varchar,dateadd(day,-2,getdate()),101))\n" +
                    "    on l.line_item_id=sl.line_fkey\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_inventory_serial s\n" +
                    "\n" +
                    "ON\n" +
                    "        sl.serial_fkey = s.id");

            ExcelUtils.mailExcelSheetsForQueries(reportMap, "OWD Daily Serial Numbers Collected " + df.format(cal.getTime()), "\r\nReport is attached.\r\n\r\n", "diane@temperaturealert.com");

                    ExcelUtils.mailExcelSheetsForQueries(reportMap, "OWD Daily Serial Numbers Collected " + df.format(cal.getTime()), "\r\nReport is attached.\r\n\r\n", "caitlin@temperaturealert.com");

            ExcelUtils.mailExcelSheetsForQueries(reportMap, "OWD Daily Serial Numbers Collected " + df.format(cal.getTime()), "\r\nReport is attached.\r\n\r\n", "fulfillment@schechtertech.com");

        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "GENERIC",
                    ""+358,
                    this.getClass().getName(),
                    LogableException.errorTypes.INTERNAL);
        }
    }
}

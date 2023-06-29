package com.owd.jobs.jobobjects.excel;

import com.owd.core.managers.SlackManager;
import com.owd.jobs.jobobjects.excelreports.ExcelReportSender;
import com.owd.jobs.jobobjects.excelreports.ReportDefinition;
import com.owd.jobs.jobobjects.excelreports.SheetDefinition;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.owd.core.Mailer;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.SqlUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelUtils
{
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) throws Exception {

        Map<String,String> reportMap = new HashMap<String,String>();

      //  reportMap.put("OWD_OrdersShipped_20150925.xls", "select bill_company_name as Customer,order_refnum as Reference,order_status as Status,post_date as 'At Warehouse',shipped_on as Shipped,order_total as Total\n" +
      //          "from owd_order where shipped_on='2015-9-25' and client_fkey=529 order by bill_company_name,order_refnum");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        reportMap.put("OWD_SerialNumbers_"+df.format(cal.getTime()),"SELECT\n" +
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
        mailExcelSheetsForQueries(reportMap, "Daily Serial Numbers Collected "+df.format(cal.getTime()), "\r\nReport is attached.\r\n\r\n", "owditadmin@owd.com");

    }
    public ExcelUtils()
    {

    }


    static public void deliverReports(List<ReportDefinition> jobList,List<ExcelReportSender> senders) {

        try {

            for (ReportDefinition def : jobList) {
                Map<String,HSSFWorkbook> reportMap = new HashMap<String,HSSFWorkbook>();

                HSSFWorkbook wb = ExcelUtils.createWorkbook(def.sheets.size());
                int sheetIndex = 0;

                for (SheetDefinition worksheet : def.sheets) {
                    //header rows
                    List<String> headers = new ArrayList<>();
                    log.debug(worksheet.sheetName);
                    wb.setSheetName(sheetIndex, worksheet.sheetName);
                    headers.add(worksheet.sheetTitle);
                    wb = ExcelUtils.writeHeaders(sheetIndex, wb, headers, 0);
                    headers.clear();
                    headers.add(def.periodTitle);
                    wb = ExcelUtils.writeHeaders(sheetIndex, wb, headers, 1);



                    System.out.println(worksheet.query);
                    List<Map<String,Object>> invResult = SqlUtils.getResultSetMap(HibernateSession.currentSession(), worksheet.query);
                    headers.clear();
                    if(invResult.size()>0) {
                        List<String> titles = ((Map<String, Object>) invResult.get(0)).keySet().stream().collect(Collectors.toList());
                        headers.addAll(titles);

                        wb = ExcelUtils.writeHeaders(sheetIndex, wb, headers, Integer.valueOf(4));
                        int rows = 0;
                        List body = new ArrayList();
                        for (Map<String, Object> row : invResult) {
                            List excelrow = new ArrayList();
                            for (String key : row.keySet()) {
                               // System.out.println("adding row " + key + ":" + row.get(key));
                                Object value = row.get(key);
                                if(key.endsWith("Hours") || key.endsWith("Feet"))   {
                                  value = (""+value);
                                }
                                excelrow.add(value);
                                rows++;
                            }
                            body.add(excelrow);
                        }

                        if (wb != null) {
                            wb = ExcelUtils.writeBody(sheetIndex, wb, body, 5);
                        }
                        for (int col = 0; col < headers.size(); col++) {
                            wb.getSheetAt(sheetIndex).autoSizeColumn(col, true);
                        }
                    }else
                    {
                        headers.clear();
                        headers.add("No Data Available");
                        wb = ExcelUtils.writeHeaders(sheetIndex, wb, headers, Integer.valueOf(4));

                    }
                    sheetIndex++;
                }

                reportMap.put((def.clientName+def.periodTitle).replaceAll(" ","_"), wb);


                List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();

                for(String fileName:reportMap.keySet()) {

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    reportMap.get(fileName).write(baos);
                    byte[] xls = baos.toByteArray();
                    if(xls!=null) {


                        attachments.add(getAttachmentMap(fileName, xls));
                    }
                }


                if(attachments.size()>0) {

                    for(ExcelReportSender sender:senders) {
                        sender.sendReports(def,attachments);
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void mailExcelSheetsForQueries(Map<String,String> reportMap, String subject, String message, String emailStr) throws Exception
    {


        List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();

        for(String fileName:reportMap.keySet()) {

            byte[] xls = getExcelFileForSqlQuery(reportMap.get(fileName),fileName);
            if(xls!=null) {

                attachments.add(getAttachmentMap(fileName, xls));
            }
        }
        if(attachments.size()>0) {
            List<String> emails = new ArrayList<String>();

            String[] es = emailStr.split(",");

            for (int i = 0; i < es.length; i++) {
                emails.add(es[i]);

            }

            for (String email : emails) {
                Mailer.sendMailWithMultipleAttachments(subject,
                       message,
                        email, null, null, "donotreply@owd.com",
                        attachments);

            }
        }
    }

    private static Map<String, Object> getAttachmentMap(String fileName, byte[] xls) {
        Map<String, Object> attdata = new HashMap<String, Object>();
        attdata.put("bytes", xls);
        attdata.put("mimetype", "application/vnd.ms-excel");
        attdata.put("filename", fileName+".xls");
        return attdata;
    }

    public static byte[] getExcelFileForSqlQuery(String sql, String optionalTitle) throws Exception
    {

        List<Map<String,Object>> invResult = SqlUtils.getResultSetMap(HibernateSession.currentSession(), sql);

        HSSFWorkbook wb = ExcelUtils.createWorkbook();

        List<String> headers = new ArrayList<String>();
        if(optionalTitle!=null) {
            headers.add(optionalTitle);
            wb = ExcelUtils.writeHeaders(wb, headers, 0);
        }
        if (invResult.size() == 0) {
            wb = null;
        } else {
            headers.clear();
            for (String key : (invResult.get(0)).keySet()) {
                headers.add(key);
            }
            wb = ExcelUtils.writeHeaders(wb, headers, 2);

            List<List<Object>> body = new ArrayList<List<Object>>();
            for (Map<String,Object> row : invResult) {
                List<Object> excelrow = new ArrayList<Object>();
                for (String key : row.keySet()) {
                    excelrow.add(row.get(key));
                }
                body.add(excelrow);
            }
            wb = ExcelUtils.writeBody(wb, body, 3);
            int noOfColumns = wb.getSheetAt(0).getRow(3).getLastCellNum();

            for(int i=0;i<noOfColumns;i++)
            {
                wb.getSheetAt(0).autoSizeColumn(i);
            }

        }

        if(wb==null)
        {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);

        return baos.toByteArray();
    }

    public static HSSFWorkbook createWorkbook()
    {
        HSSFWorkbook workbook = new HSSFWorkbook();
        workbook.createSheet();

        return workbook;
    }
    public static HSSFWorkbook createWorkbook(int sheets)
{

    HSSFWorkbook workbook = new HSSFWorkbook();
    workbook.createSheet();

    if(sheets>1) {
        for (int i = 1; i < sheets; i++) {
            workbook.createSheet();
        }
    }
    return workbook;
}
    public static HSSFWorkbook writeHeaders(HSSFWorkbook workbook, List<String> headers, Integer rowNumber)
    {
       return writeHeaders(0,workbook,  headers,  rowNumber);
    }

    public static HSSFWorkbook writeHeaders(int sheetIndex,HSSFWorkbook  workbook,  List<String> headers, Integer rowNumber)
    {
        HSSFSheet sheet = workbook.getSheetAt(sheetIndex);



        HSSFRow rowOut = sheet.createRow(rowNumber.intValue());
        for (short i = 0; i < headers.size(); i = (short)(i + 1))
        {
            HSSFCell cellOut = rowOut.createCell(i);
            String header = headers.get(i);
            cellOut.setCellValue(header);
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);
            cellOut.setCellStyle(cellStyle);
        }

        return workbook;
    }

    public static HSSFWorkbook writeBody(HSSFWorkbook workbook, List<List<Object>> body,  int start) throws Exception
    {
        return   writeBody(0, workbook,  body, start);
    }

    public static HSSFWorkbook writeBody(int sheetIndex, HSSFWorkbook workbook, List<List<Object>> body, int start) throws Exception
    {
        HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                workbook.getCreationHelper().createDataFormat().getFormat("m/d/yy"));
        CellStyle moneyStyle = workbook.createCellStyle();
        moneyStyle.setDataFormat(
                workbook.getCreationHelper().createDataFormat().getFormat("$#,##0.00"));
        CellStyle decimalStyle = workbook.createCellStyle();

        decimalStyle.setDataFormat(
                workbook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));




        for (int i = 0; i < body.size(); i++)
        {
            HSSFRow rowOut = sheet.createRow(start + i);
            List bodyList = (List)body.get(i);
            for (short j = 0; j < bodyList.size(); j = (short)(j + 1))
            {
                CellStyle aStyle = moneyStyle;

                HSSFCell cellOut = rowOut.createCell(j);
                Object obj = bodyList.get(j);
                if ((obj instanceof String))
                {
                    if(((String)obj).contains(".")) {
                        try {
                            obj = Double.parseDouble((String) obj);
                            aStyle = decimalStyle;
                        } catch (Exception ex) {
                            //  ex.printStackTrace();
                        }
                    }
                }
             //   log.debug("j="+j);
              //  log.debug("bodyList size="+bodyList.size());
           //     log.debug(bodyList.get(j));
             //   log.debug(bodyList.get(j).getClass().getCanonicalName());
                if ((obj instanceof String))
                {
                    cellOut.setCellValue((String)obj);
                }
                else if ((obj instanceof Integer))
                {
                    cellOut.setCellType(0);
                    cellOut.setCellValue(((Integer)obj).intValue());
                }
                else if ((obj instanceof BigInteger))
                {
                    cellOut.setCellType(0);
                    cellOut.setCellValue(((BigInteger)obj).intValue());
                }
                else if ( (obj instanceof Double))
                {
                    cellOut.setCellType(5);
                    cellOut.setCellStyle(aStyle);
                    cellOut.setCellValue(((Double)obj).doubleValue());
                } else if ((obj instanceof Float) ) {
                    cellOut.setCellType(5);
                    cellOut.setCellStyle(aStyle);
                    cellOut.setCellValue(((Float)obj).doubleValue());
                } else if ((obj instanceof BigDecimal))
                {
                    cellOut.setCellType(5);
                    cellOut.setCellStyle(aStyle);
                    cellOut.setCellValue(((BigDecimal)obj).doubleValue());
                }else if ((obj instanceof Timestamp))
                {

                    cellOut.setCellValue(new Date());
                    cellOut.setCellStyle(dateStyle);

                    Calendar shipdate = new GregorianCalendar();
                    shipdate.setTimeInMillis( ((Timestamp)obj).getTime());

                    cellOut.setCellType(0);
                    cellOut.setCellValue(shipdate.getTime());
                }else
                {
                    cellOut.setCellValue(""+obj);

                }
            }
        }


        return workbook;
    }

    public static HSSFWorkbook appendTruncationNotification(HSSFWorkbook workbook) throws Exception   {
        return   appendTruncationNotification(0,  workbook);
    }


    public static HSSFWorkbook appendTruncationNotification(int sheetIndex, HSSFWorkbook workbook) throws Exception
    {
        HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        int maxRows = sheet.getLastRowNum();
        HSSFRow rowOut = sheet.createRow(sheet.getLastRowNum() + 1);
        HSSFCell cellOut = rowOut.createCell(0);
        cellOut.setCellValue("Results limited to the first:" + maxRows + " rows.");
        return workbook;
    }

    public static HSSFWorkbook print(HSSFWorkbook workbook) throws Exception
    {
           return print(0,  workbook);
    }
    public static HSSFWorkbook print(int sheetIndex, HSSFWorkbook workbook) throws Exception
    {
        HSSFSheet sheet = workbook.getSheetAt(sheetIndex);

        for (int i = 0; i < sheet.getLastRowNum(); i++)
        {
            HSSFRow rowOut = sheet.getRow(i);

           // log.debug("\n row: " + i + ": ");
            for (short j = 0; j < rowOut.getLastCellNum(); j = (short)(j + 1))
            {
                HSSFCell cellOut = rowOut.getCell(j);
             //   System.out.print(String.valueOf(cellOut.getStringCellValue()) + " | ");
            }

        }

        return workbook;
    }
}

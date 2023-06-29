package com.owd.jobs.clients

import com.owd.core.business.order.OrderStatus
import com.owd.hibernate.generated.OwdExternalEvent
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import com.owd.jobs.jobobjects.excel.ExcelUtils
import org.apache.commons.net.ftp.FTPClient
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer
import com.owd.hibernate.HibernateSession
import com.owd.jobs.OWDStatefulJob
import org.apache.commons.lang.StringUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.hibernate.criterion.Expression

import java.sql.ResultSet
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by stewartbuskirk1 on 9/3/15.
 */
@groovy.util.logging.Log4j2
class GildanWalmartInvoicedShipmentsFileJob extends OWDStatefulJob  {


    public static void main(String[] args) {
        run();
     /*   GildanWalmartInvoicedShipmentsFileJob job = new GildanWalmartInvoicedShipmentsFileJob()
        ReportWeek rw = job.getCurrentReportWeek()
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DATE, 18);
        rw.startDate = cal.getTime();
        cal.set(Calendar.DATE, 24);
        rw.endDate = cal.getTime();
System.out.println(rw.startDate.format('yyyy-MM-dd'));
        System.out.println(rw.endDate.format('yyyy-MM-dd'));


        StringBuilder sb = job.getFTPReportString(rw.startDate,rw.endDate);
        System.out.println(sb.toString());*/
      //  run()
    }


    @Override
    public void internalExecute() {

        ReportWeek rw = getCurrentReportWeek()

        String emailToStr = "KWhitaker@gildan.com"
        String emailCcsStr = "owditadmin@owd.com,SHowle@gildan.com,cmcdaniel@gildan.com,cwachtel@gildan.com,NBuckley@gildan.com,apwilkinson@gildan.com,CWachtel@gildan.com,THerrera@gildan.com"

        String subject = "Weekly Gildan-Walmart Shipping Report (" +rw.startDate.format('yyyy-MM-dd')+' to '+rw.endDate.format('yyyy-MM-dd')+')'
        String message="\r\nReport is attached.\r\n\r\n"
        String fileName="GildanWalmartShippedThrough"+rw.endDate.format('yyyyMMdd')
        List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();

        byte[] report =  getReportForDateRange(rw.startDate,rw.endDate);
        if(report!=null) {

            Map<String, Object> attdata = new HashMap<String, Object>();
            attdata.put("bytes", report);
            attdata.put("mimetype", "application/vnd.ms-excel");
            attdata.put("filename", fileName+".xls");

            attachments.add(attdata);
        }

        StringBuilder sb = getFTPReportString(rw.startDate,rw.endDate);
        if(sb.length()>0){
           /* Map<String, Object> attdata = new HashMap<String, Object>();
            attdata.put("bytes", sb.toString().getBytes());
            attdata.put("mimetype", "text/csv");
            attdata.put("filename", fileName+".csv");

            attachments.add(attdata);*/

           // public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat fileNameFormat = new SimpleDateFormat("yyMMdd");
            FTPClient client = new FTPClient();
            String filename2 = "WM" + fileNameFormat.format(rw.endDate)+".csv";
            try{

                client.connect("ftp.gildan.com");

                client.login("63677","OWd@2017");
                client.enterLocalPassiveMode();
                client.changeWorkingDirectory("/fromvan");

                System.out.println(client.storeFile(filename2, new ByteArrayInputStream(sb.toString().getBytes())));
                System.out.println(client.getReplyCode());
                System.out.println(client.getReplyString());
                client.logout();
                Mailer.sendMailWithAttachment("Weekly FTP file for Walmart", "Attached is the file",
                        "acardillo@gildan.com","acardillo@gildan.com,mhust@gildan.com,dpriebe@gildan.com,mastorino@gildan.com,itadmin@owd.com,llegg@gildan.com".split(","), sb.toString().getBytes(), filename2, "text/csv");
             //   Mailer.sendMailWithAttachment("Weekly FTP file for Walmart", "Attached is the file", "dnickels@owd.com", sb.toString().getBytes(), filename2, "text/csv");


            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(attachments.size()>0) {

            String[] eccs = emailCcsStr.split(",");


            Mailer.sendMailWithMultipleAttachments(subject,
                    message,
                    emailToStr, eccs, null, "donotreply@owd.com",
                    attachments);
        }



    }


    public boolean hasCheckDigit(String userNumber){

    /*    //get input from user
        Scanner sc = new Scanner(System.in);
        log.debug("Please enter 11 digit UPC");
        String userNumber = sc.next();*/



        int step6;

        char[] chars = userNumber.chars
        int odd=0
        int even=0
        int ckDigitCandidate = Integer.parseInt(String.valueOf(chars[userNumber.length()-1]))

        for(int pos=0;pos<(userNumber.length()-1);pos++)
        {
           if((1+pos)%2==0)
           {
               //even
               even += Integer.parseInt(String.valueOf(chars[pos]))
           }   else {
               //odd
               odd += Integer.parseInt(String.valueOf(chars[pos]))
           }
        }

        //STEP 6:  determine check digit
        step6 = (10 - ((even+(3*odd)) % 10));
        if(step6==10)
        {
            step6=0;
        }
                if(ckDigitCandidate!=step6) {
               return false;
                }

        return true;
    }





    public  ReportWeek getCurrentReportWeek() {
        ReportWeek rw = new ReportWeek()

        def today = Calendar.instance
        today.set(Calendar.HOUR ,0)
        today.set(Calendar.HOUR_OF_DAY ,0)
        today.set(Calendar.MINUTE ,0)
        today.set(Calendar.SECOND ,0)
        today.set(Calendar.MILLISECOND ,0)

        println today
        Date current = today.time-1
        while(current[Calendar.DAY_OF_WEEK] != Calendar.FRIDAY)
        {
            current = current -1
        }
        rw.endDate = current
        while(current[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY)
        {
            current = current -1
        }
        rw.startDate = current

        return rw
    }

    public  byte[] getReportForDateRange(Date saturday, Date friday)
    {


        HSSFWorkbook wb = ExcelUtils.createWorkbook();

        List<String> headers = new ArrayList<String>();
            headers.add("Shipment Invoice Report for Walmart.com shipped "+saturday.format('yyyy-MM-dd')+" to "+friday.format('yyyy-MM-dd'));
            wb = ExcelUtils.writeHeaders(wb, headers, 0);


        headers.clear();
        List<String> columnTitles = Arrays.asList('Shipped Date','WM Order Ref','WM PO Number','OWD SKU','WM Tool ID','WM Vendor SKU','UPC','Shipped Qty','Invoice Per Unit','Total Invoice')
            for (String title : columnTitles) {
                headers.add(title);
            }
            wb = ExcelUtils.writeHeaders(wb, headers, 2);



        Map<String,String> xmlMap = new HashMap<String,String>();
        Map<String,List<String>> itemMap = new HashMap<String,List<String>>();

        ResultSet rs = HibernateSession.getResultSet("select wmref,vendorref,owdsku,upc from walmart_itemreference\n" +
                "                                 join owd_inventory i join owd_inventory_oh h on h.inventory_fkey=inventory_id " +
                " on client_fkey=walmart_client_fkey and i.inventory_num=owdsku and client_fkey=471\n" +
                "                                 where len(wmref)>5 and inventory_num is not null and len(owdsku)>3");
        while (rs.next()) {

            itemMap.put(rs.getString(3), Arrays.asList(rs.getString(1),rs.getString(2),rs.getString(4)));
        }
        rs.close();

        println "select external_id,value from owd_tags t join owd_order on external_id=order_id and client_fkey=471 and \n" +
                "shipped_on>='"+saturday.format('yyyy-MM-dd')+"' and shipped_on<='"+friday.format('yyyy-MM-dd')+"' where name='WALMARTDSV_PO_XML'"
        rs = HibernateSession.getResultSet("select external_id,value from owd_tags t join owd_order on external_id=order_id and client_fkey=471 and \n" +
                "shipped_on>='"+saturday.format('yyyy-MM-dd')+"' and shipped_on<='"+friday.format('yyyy-MM-dd')+"' where name='WALMARTDSV_PO_XML'");
        while (rs.next()) {

            xmlMap.put(rs.getString(1), rs.getString(2));
        }
        rs.close();


        List orders = HibernateSession.currentSession().createCriteria(OwdOrder.class)
                .add(Expression.eq("clientFkey", 471))
                .add(Expression.ge("shippedDate",saturday))
                .add(Expression.le("shippedDate",friday))
                .add(Expression.eq("orderType",'WALMART.COM'))
                .list();

        println "found "+orders.size+" orders"

        List<List<Object>> body = new ArrayList<List<Object>>();

        int ooo=1;
        for(OwdOrder order:orders) {
            println ooo++;

              String xml = xmlMap.get(""+order.getOrderId())
              def walmartOrder = new XmlSlurper().parseText(xml);
              for(OwdLineItem line:order.getLineitems()) {
                  List<Object> excelrow = new ArrayList<Object>();

                  String lineRef = line.getCustRefnum()
                //  log.debug lineRef
                  def poLineItem = walmartOrder.OR_ORDERLINE.find {
                      it.@LINENUMBER.text().trim().equals(lineRef)
                  }

                  if (poLineItem) {

                      excelrow.add(order.getShippedDate().format('yyyy-MM-dd'));
                      excelrow.add(order.getOrderRefnum());
                      excelrow.add(order.getPoNum());
                      excelrow.add(line.getInventoryNum());
                      List<String> itemInfo = itemMap.get(line.getInventoryNum())
                      if(itemInfo==null)
                      {
                          excelrow.add('')
                          excelrow.add('')
                          excelrow.add('')

                      }   else {
                          for (String info : itemInfo) {
                              excelrow.add(info)
                          }
                      }
                      excelrow.add(line.getQuantityActual());
                      excelrow.add(poLineItem.OR_COST.@AMOUNT.text());
                      excelrow.add((line.getQuantityActual()*Float.parseFloat(poLineItem.OR_COST.@AMOUNT.text())))

                      body.add(excelrow);

                  }

              }

          }
        wb = ExcelUtils.writeBody(wb, body, 3);
        int noOfColumns = wb.getSheetAt(0).getRow(3).getLastCellNum();

        for(int i=0;i<noOfColumns;i++)
        {
            wb.getSheetAt(0).autoSizeColumn(i);
        }
        if(wb==null)
        {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);

        return baos.toByteArray();
    }

    public  StringBuilder getFTPReportString(Date saturday, Date friday)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Branch,Customer Number,SKU,Shipped Units,Unit Cost,Extended Total Cost,Default Retail Unit Price,Net Sales,Returned Units,Restocked Units,Restocked Units Extended Cost\r\n");








        Map<String,String> xmlMap = new HashMap<String,String>();
        Map<String,List<String>> itemMap = new HashMap<String,List<String>>();

        ResultSet rs = HibernateSession.getResultSet("select wmref,vendorref,owdsku,upc from walmart_itemreference\n" +
                "                                 join owd_inventory i join owd_inventory_oh h on h.inventory_fkey=inventory_id " +
                " on client_fkey=walmart_client_fkey and i.inventory_num=owdsku and client_fkey=471\n" +
                "                                 where len(wmref)>5 and inventory_num is not null and len(owdsku)>3");
        while (rs.next()) {

            itemMap.put(rs.getString(3), Arrays.asList(rs.getString(1),rs.getString(2),rs.getString(4)));
        }
        rs.close();

        println "select external_id,value from owd_tags t join owd_order on external_id=order_id and client_fkey=471 and \n" +
                "shipped_on>='"+saturday.format('yyyy-MM-dd')+"' and shipped_on<='"+friday.format('yyyy-MM-dd')+"' where name='WALMARTDSV_PO_XML'"
        rs = HibernateSession.getResultSet("select external_id,value from owd_tags t join owd_order on external_id=order_id and client_fkey=471 and \n" +
                "shipped_on>='"+saturday.format('yyyy-MM-dd')+"' and shipped_on<='"+friday.format('yyyy-MM-dd')+"' where name='WALMARTDSV_PO_XML'");
        while (rs.next()) {

            xmlMap.put(rs.getString(1), rs.getString(2));
        }
        rs.close();


        List orders = HibernateSession.currentSession().createCriteria(OwdOrder.class)
                .add(Expression.eq("clientFkey", 471))
                .add(Expression.ge("shippedDate",saturday))
                .add(Expression.le("shippedDate",friday))
                .add(Expression.eq("orderType",'WALMART.COM'))
                .list();

        println "found "+orders.size+" orders"

        List<List<Object>> body = new ArrayList<List<Object>>();

        int ooo=1;
        for(OwdOrder order:orders) {
            println ooo++;

            String xml = xmlMap.get(""+order.getOrderId())
            def walmartOrder = new XmlSlurper().parseText(xml);
            for(OwdLineItem line:order.getLineitems()) {
                sb.append("RDC,");
                sb.append("108875,");


                String lineRef = line.getCustRefnum()
                //  log.debug lineRef
                def poLineItem = walmartOrder.OR_ORDERLINE.find {
                    it.@LINENUMBER.text().trim().equals(lineRef)
                }

                if (poLineItem) {
                sb.append(line.getInventoryNum());
                    sb.append(",");


                   sb.append(line.getQuantityActual());
                    sb.append(",");
                    sb.append("0")
                    sb.append(",0,");
                    sb.append(poLineItem.OR_COST.@AMOUNT.text());
                    sb.append(",");
                    sb.append((line.getQuantityActual()*Double.parseDouble(poLineItem.OR_COST.@AMOUNT.text())))
                    sb.append(",");
                    sb.append("0,0,0");
                    sb.append("\r\n");

                }else{
                   System.out.println("Not Found");
                    System.out.println( line.getCustRefnum());
                    System.out.println(order.getOrderNum());
                }

            }

        }


        return sb;
    }

    public class ReportWeek {
        Date startDate
        Date endDate

        public String toString() {
            return startDate.format("yyyy-MM-dd")+"-"+endDate.format("yyyy-MM-dd")
        }
    }

}


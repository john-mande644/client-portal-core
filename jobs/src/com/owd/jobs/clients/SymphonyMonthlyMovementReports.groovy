package com.owd.jobs.clients

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer
import com.owd.hibernate.HibernateSession
import com.owd.jobs.OWDStatefulJob
import com.owd.jobs.jobobjects.SqlUtils
import com.owd.jobs.jobobjects.excel.ExcelUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook


/**
 * Created by stewartbuskirk1 on 5/6/14.
 */
class SymphonyMonthlyMovementReportsJob extends OWDStatefulJob {


    public static void main(String[] args) throws Exception{
         run()
    }

    static Map<String,String> deliveries = new TreeMap<String,String>();
    static Map<String,List<String>> facilities = new HashMap<String,List<String>>()
    static
    {

   //     facilities.put("G298V2024",Arrays.asList("DC1"))
   //     deliveries.put("G298V2024","aaron@bulletproofexec.com,viet@symphonycommerce.com,joe@bulletproofexec.com,julia@bulletproofexec.com");
        facilities.put("G128V1947",Arrays.asList("DC1","DC6","DC7"))
        deliveries.put("G128V1947","paul@symphonycommerce.com,owditadmin@owd.com,kyle@symphonycommerce.com,jasper@symphonycommerce.com")
       // deliveries.put("G298V2024","owditadmin@owd.com");
       // deliveries.put("G128V1947","owditadmin@owd.com,sbuskirk@mac.com");
    }
    @Override
    void internalExecute() {

        try{


        Calendar endCal = GregorianCalendar.instance
        endCal.add(Calendar.MONTH,-1);
        endCal.set(Calendar.DAY_OF_MONTH,endCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))

        Calendar startCal = GregorianCalendar.instance
        startCal.add(Calendar.MONTH,-1);
        startCal.set(Calendar.DAY_OF_MONTH,1)


            for(String groupName:deliveries.keySet()) {
                Map<String,HSSFWorkbook> reportMap = new HashMap<String,HSSFWorkbook>()

                if(facilities.get(groupName).size()>1) {

                    String sql = "exec sp_getinventorymovementsummarybygroup '" + startCal.getTime().format('yyyy-MM-dd') + "','" + endCal.getTime().format('yyyy-MM-dd') + "',489,'" + groupName + "';"


                    println sql
                    List invResult = SqlUtils.getResultSetMap(HibernateSession.currentSession(), sql);

                    HSSFWorkbook wb = ExcelUtils.createWorkbook();

                    List headers = new ArrayList();
                    headers.add(endCal.getTime().format('yyyy-MM-dd') + " Inventory Stock Level Summary for " + groupName);
                    //   headers.add(aformatter.format(reportDate));
                    wb = ExcelUtils.writeHeaders(wb, headers, Integer.valueOf(0));

                    if (invResult.size() == 0) {
                        wb = null;
                    } else {
                        headers.clear();
                        for (String key : ((Map<String, Object>) invResult.get(0)).keySet()) {
                            headers.add(key);
                        }
                        wb = ExcelUtils.writeHeaders(wb, headers, Integer.valueOf(2));
                        int rows = 0
                        List body = new ArrayList();
                        for (LinkedHashMap row : invResult) {
                            List excelrow = new ArrayList();
                            for (String key : row.keySet()) {
                                println "adding row " + key + ":" + row.get(key)
                                excelrow.add(row.get(key));
                                rows++
                            }
                            body.add(excelrow);
                        }


                        if (wb != null) {
                            wb = ExcelUtils.writeBody(wb, body, 3);
                            reportMap.put('All', wb)
                        }
                    }
                }

                for (String facility : facilities.get(groupName)) {


                    String sql = "exec sp_getinventorymovementsummarybygroupfacility '" + startCal.getTime().format('yyyy-MM-dd') + "','" + endCal.getTime().format('yyyy-MM-dd') + "',489,'" + groupName + "','"+facility+"';"


                    println sql
                    List invResult = SqlUtils.getResultSetMap(HibernateSession.currentSession(), sql);

                    HSSFWorkbook wb = ExcelUtils.createWorkbook();

                    List headers = new ArrayList();
                    headers.add(endCal.getTime().format('yyyy-MM-dd') + " Inventory Stock Level Summary for " + groupName);
                    //   headers.add(aformatter.format(reportDate));
                    wb = ExcelUtils.writeHeaders(wb, headers, Integer.valueOf(0));

                    if (invResult.size() == 0) {
                        wb = null;
                    } else {
                        headers.clear();
                        for (String key : ((Map<String, Object>) invResult.get(0)).keySet()) {
                            headers.add(key);
                        }
                        wb = ExcelUtils.writeHeaders(wb, headers, Integer.valueOf(2));
                        int rows = 0
                        List body = new ArrayList();
                        for (LinkedHashMap row : invResult) {
                            List excelrow = new ArrayList();
                            for (String key : row.keySet()) {
                                println "adding row " + key + ":" + row.get(key)
                                excelrow.add(row.get(key));
                                rows++
                            }
                            body.add(excelrow);
                        }


                        if (wb != null) {
                            wb = ExcelUtils.writeBody(wb, body, 3);
                            reportMap.put(facility,wb)
                        }

                    }
                }




               List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>()

                for(String location:reportMap.keySet()) {

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    reportMap.get(location).write(baos);
                    byte[] xls = baos.toByteArray();

                    Map<String, Object> attdata = new HashMap<String, Object>()
                    attdata.put("bytes", xls);
                    attdata.put("mimetype", "application/vnd.ms-excel")
                    attdata.put("filename", "Inventory" + startCal.getTime().format('MMMM') + startCal.getTime().format('yyyy') + "_" + groupName + "_" + location + ".xls")

                    attachments.add(attdata)

                }
                if(attachments.size()>0) {
                    List<String> emails = new ArrayList<String>();

                    String[] es = deliveries.get(groupName).split(",");

                    for (int i = 0; i < es.length; i++) {
                        emails.add(es[i])

                    }

                    for (String email : emails) {
                        Mailer.sendMailWithMultipleAttachments("Inventory Stock Level Report for " + startCal.getTime().format('MMMM') + " " + startCal.getTime().format('yyyy') + " (" + groupName + ")",
                                "\r\nReport of all inventory levels by SKU for the month ending " + endCal.getTime().format('yyyy-MM-dd') + "\r\n\r\n",
                                email,null,null,"donotreply@owd.com",
                                attachments)

                    }
                }
            }





        }catch(Exception ex)
        {
            ex.printStackTrace()
            throw ex;
        }
}

}

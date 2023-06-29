package com.owd.dc.warehouse.packSlipRelease;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import freemarker.template.SimpleDate;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jan 16, 2009
 * Time: 4:42:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class packSlipUtilities {
    private static String getAutoReleaseSql = "select value from app_data where project = 'wms' and description = 'packSlips' and variable = 'autoRelease'";
    private static String getPrinterList = "select value , display from app_data where project = 'wms' and description = 'packSlips' and variable = 'printer' and facility_code=:location";
    private static String getTodayPrinterList = "select value , display from app_data where project = 'wms' and description = 'packSlips' and variable = 'printer1' and facility_code=:location";

        private static String currentPrintQueueSql = "select printer_name, count(q.order_id) as count from owd_print_queue_sl q join owd_order o on o.order_id=q.order_id and facility_code=:location group by printer_name\n" +
                "group by printer_name";
    private static String getOrdersSql = "SELECT     w_order_attributes.fingerprint, owd_print_queue3.client_id, COUNT(owd_print_queue3.order_id) AS Orders, owd_client.company_name, SUM(dbo.releasePackSlipToday(owd_print_queue3.sla,getDate())) as today\n" +
            "FROM         owd_print_queue3 (NOLOCK) INNER JOIN\n" +
            "                      owd_order (NOLOCK) ON owd_print_queue3.order_id = owd_order.order_id INNER JOIN\n" +
            "                      owd_client(NOLOCK) ON owd_print_queue3.client_id = owd_client.client_id INNER JOIN\n" +
            "                      w_order_attributes (NOLOCK) ON owd_print_queue3.order_id = w_order_attributes.order_fkey\n" +
            "WHERE     (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (owd_print_queue3.sentToQueue = 0) AND (w_order_attributes.business_order = 0) AND (owd_order.post_date <= GETDATE()) AND (owd_order.facility_code=:location)\n" +
            "GROUP BY w_order_attributes.fingerprint, owd_print_queue3.client_id, owd_client.company_name\n" +
            "ORDER BY owd_client.company_name, w_order_attributes.fingerprint";


    //statements for regular moving

    private static String updateSentToQueueSql = "update owd_print_queue3 set sentToQueue=1 from owd_print_queue3 p, owd_order o where p.order_id=o.order_id and sentToQueue=0 and getdate()>=post_date\n" +
            "and isVerified=1 AND fileCreated = 1 AND fileDownloaded = 1 and p.client_id = :clientId and o.facility_code=:location";

   private static String updateSentToQueueTodaySql = "update owd_print_queue3 set sentToQueue=1 from owd_print_queue3 p, owd_order o where p.order_id=o.order_id and sentToQueue=0 and getdate()>=post_date\n" +
            "and isVerified=1 AND fileCreated = 1 AND fileDownloaded = 1 and p.client_id = :clientId and dbo.releasePackSlipToday(sla,getDate()) = 1 and o.facility_code=:location";

    private static String moveToPrintQueueSql = "\n" +
            "insert into owd_print_queue_sl (order_id, client_id, created, printer_name,pdf_binary) SELECT     owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, :printer AS printer_name, owd_print_queue3.pdf_binary \n" +
            "FROM         owd_print_queue3 INNER JOIN\n" +
            "                      w_order_attributes ON owd_print_queue3.order_id = w_order_attributes.order_fkey\n" +
            "WHERE     (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (owd_print_queue3.sentToQueue = 1) AND (owd_print_queue3.client_id = :clientId)\n" +
            "ORDER BY w_order_attributes.fingerprint";

     private static String moveToPrintQueueSqlSLA = "\n" +
            "insert into owd_print_queue_sl (order_id, client_id, created, printer_name, pdf_binary) SELECT     owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, dbo.getSlaPrinterName(order_id,getDate()), owd_print_queue3.pdf_binary \n" +
            "FROM         owd_print_queue3 INNER JOIN\n" +
            "                      w_order_attributes ON owd_print_queue3.order_id = w_order_attributes.order_fkey\n" +
            "WHERE     (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (owd_print_queue3.sentToQueue = 1) AND (owd_print_queue3.client_id = :clientId)\n" +
            "ORDER BY w_order_attributes.fingerprint";

//statements for  records with the grouping number
    private static String updateSentToQueueGroupSql = "update owd_print_queue3 set sentToQueue = 1from owd_print_queue3 p, owd_order o where p.order_id=o.order_id and sentToQueue=0 and getdate()>=post_date\n" +
            "           and isVerified=1 AND fileCreated = 1 AND fileDownloaded = 1 and p.client_id = :clientId and printgroup_id is not null and o.facility_code=:location";

    private static String moveToPrintQueueGroupSql = "\n" +
            "insert into owd_print_queue_sl (order_id, client_id, created, printer_name, pdf_binary) SELECT     owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, :printer, owd_print_queue3.pdf_binary \n" +
            "          FROM         owd_print_queue3\n" +
            "          WHERE     (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (owd_print_queue3.sentToQueue = 1) AND (owd_print_queue3.client_id = :clientId)\n" +
            "          ORDER BY owd_print_queue3.created";
    private static String moveToPrintQueueGroupSqlSla = "\n" +
            "insert into owd_print_queue_sl (order_id, client_id, created, printer_name, pdf_binary) SELECT     owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, printer_name, owd_print_queue3.pdf_binary \n" +
            "          FROM         owd_print_queue3\n" +
            "          WHERE     (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (owd_print_queue3.sentToQueue = 1) AND (owd_print_queue3.client_id = :clientId)\n" +
            "          ORDER BY owd_print_queue3.created";

   //sql statements for printing the reds

    private static String updateSentToQueueRedsSql = "update owd_print_queue3 set sentToQueue = 1 from owd_print_queue3 p, owd_order o where p.order_id=o.order_id and sentToQueue=0 and getdate()>=post_date\n" +
            "           and isVerified=1 AND fileCreated = 1 AND fileDownloaded = 1 and p.client_id = :clientId and (printer_name = 'red' or printer_name  like '%@%' and o.facility_code=:location)";
    private static String moveToPrinterQueueRedsSql = "insert into owd_print_queue_sl (order_id, client_id, created, printer_name, pdf_binary) SELECT     owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, owd_print_queue3.printer_name, owd_print_queue3.pdf_binary \n" +
            "          FROM         owd_print_queue3\n" +
            "          WHERE     (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (owd_print_queue3.sentToQueue = 1) AND (owd_print_queue3.client_id = :clientId)\n" +
            "          ORDER BY owd_print_queue3.created";
    
    private static String deleteMovedSql = "delete from owd_print_queue3 where sentToQueue=1";

    /**
     *
     * @param sess HibernateSession to use for the lookup
     * @return Map return a map of the current printers and the count that are waiting to print
     */
    public static Map<String, String> getCurrentPrintQueue(Session sess, String location){
        System.out.println("setting current Print queue");
        Map<String, String> current = new TreeMap<String,String>();
        try{
            Query q =  sess.createSQLQuery(currentPrintQueueSql).setString("location",location==null?"DC1":location);
            List result = q.list();


           if (result.size() > 0){
               for(Object row:  result.toArray()){
               Object[] row1 = (Object[])row;
             current.put(row1[0].toString(),row1[1].toString());
               System.out.println(row1[0]);
                System.out.println(row1[1]);
               }
           }
        }catch(Exception e){

        }
        return current;
    }

    public static Map<String, packSlipClientBean> getClientOrderMap(Session sess, String location){

        Map<String, packSlipClientBean> m = new TreeMap<String, packSlipClientBean>();
          Query q = sess.createSQLQuery(getOrdersSql).setString("location",location==null?"DC1":location);
        Long start= Calendar.getInstance().getTimeInMillis();

        List result = q.list();
        System.out.println(Calendar.getInstance().getTimeInMillis() - start);
        start= Calendar.getInstance().getTimeInMillis();
    try{
        for(Object row: result.toArray()){
            Object[] data = (Object[]) row;
            String clientId = data[1].toString();
            String clientName = data[3].toString();
             if(!m.containsKey(clientId)){
                 packSlipClientBean cb = new packSlipClientBean();
                 cb.setClientName(data[3].toString());
                 cb.setPrint(false);
                 cb.setOrders(new HashMap<String,packSlipOrdersBean>());
                 cb.setClientId(clientId);
                 m.put(clientId,cb);

             }
            packSlipOrdersBean ob = new packSlipOrdersBean();
            ob.setCount(Integer.parseInt(data[2].toString()));
            ob.setTodayCount(Integer.parseInt(data[4].toString()));
            ob.setFingerprint(data[0].toString());
            ob.setPrint(false);

            m.get(clientId).getOrders().put(data[0].toString(),ob);



        }

    }catch (Exception e){
        e.printStackTrace();
    }
        System.out.println(Calendar.getInstance().getTimeInMillis() - start);
        return m;
}
    public static void printOrdersFromQueueViaId(String printer,String orderIds,boolean sort) throws Exception{
        if (printer.length()<3) throw new Exception("Invalid Printer was slected");

        String sql;
        if(sort){
            sql ="exec printOrderFromQueueViaOrderIdsRackSorted :orderIds, :printer";
        }else {
           sql ="exec printOrderFromQueueViaOrderIds :orderIds, :printer";
        }
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderIds",orderIds);
        q.setParameter("printer",printer);
        int i = q.executeUpdate();
        if(i>0){
            HibUtils.commit(HibernateSession.currentSession());
        } else{
            throw new Exception("For some reason we were unable to insert records");
        }


    }
    public static void reprintOrdersToPrinterViaId(String printer,String orderIds)throws Exception{
        if (printer.length()<3) throw new Exception("Invalid Printer was slected");
        String sql =   "WITH ids (print_id,ooId)\n" +
                "as\n" +
                "(\n" +
                "SELECT \n" +
                "    Max(dbo.zzPrintedSlips.print_queue_id),\n" +
                "zzPrintedSlips.order_id\n" +
                "FROM\n" +
                "    dbo.zzPrintedSlips\n" +
                "INNER JOIN dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.zzPrintedSlips.order_id = dbo.owd_order.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "     zzPrintedSlips.order_id in ( "+orderIds+" )\n" +
                "group By zzPrintedSlips.order_id\n" +
                ")\n" +
                "insert into owd_print_queue_sl (order_id, client_id, printer_name,pdf_binary,created) \n" +
                " select order_id, client_id,:printer,pdf_binary,getDate() from zzPrintedSlips, w_order_attributes where print_queue_id in (\n" +
                "select print_id from ids) and order_id = order_fkey\n" +
                "order by fingerprint\n";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);

        q.setParameter("printer",printer);
        System.out.println(q.getQueryString());
        int i = q.executeUpdate();
       // HibUtils.commit(HibernateSession.currentSession());
        System.out.println(i);
        if(i>0){
            HibUtils.commit(HibernateSession.currentSession());
        }else{
            throw new Exception("Something did not work");
        }

    }

    public static void getOrdersToPrintInfo(String facility,String clientId,List<detailedReprintBean> info,List<String> sla,List<String> shipMethods, List<String> groupNames,List<String> units, List<String> clients,List<String> aisles,List<String> zones,List<String> boxTypes)throws Exception{
        String sql = "SELECT\n" +
                "    dbo.owd_order.order_id,\n" +
                "    dbo.owd_order.order_num,\n" +
                "    dbo.owd_client.company_name,\n" +
                "    dbo.owd_order_ship_info.carr_service,\n" +
                "dbo.udf_getsladate(owd_order.order_id) as sla,\n" +
                "    dbo.w_order_attributes.fingerprint,\n" +
                "    dbo.owd_order.group_name,\n" +
                "    dbo.w_order_attributes.total_physical_units,\n" +
                "dbo.getLineItemDescriptionList(owd_order.order_id),\n" +
                "dbo.udf_getAisleNamesListForOrderId(owd_order.order_id) as aisles,\n" +
                "dbo.udf_getZoneNamesListForOrderId(owd_order.order_id) as zones,\n" +
                "dbo.getPackagingForPrintingByOrderId(owd_order.order_id) as boxType\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.client_fkey = dbo.owd_client.client_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_print_queue3\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = :facility\n" +
                "AND dbo.owd_order.client_fkey = :clientId \n" +
                "AND dbo.owd_order_ship_info.carr_service NOT IN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            display\n" +
                "        FROM\n" +
                "            app_data\n" +
                "        WHERE\n" +
                "            project = 'global'\n" +
                "        AND description = 'shipMethod'\n" +
                "        AND VARIABLE = 'reds'\n" +
                "    )\n" +
                "AND dbo.owd_print_queue3.isVerified = 1 AND (owd_print_queue3.fileCreated = 1) \n" +
                "AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (dbo.w_order_attributes.business_order=0)\n" +
                "AND NOT EXISTS (SELECT * FROM owd_line_item oli JOIN owd_tags ot ON oli.line_item_id = ot.external_id WHERE oli.order_fkey = dbo.owd_order.order_id AND ot.name = 'PERSONALIZATION_JOB');";
        String redsql = "SELECT\n" +
                "    dbo.owd_order.order_id,\n" +
                "    dbo.owd_order.order_num,\n" +
                "    dbo.owd_client.company_name,\n" +
                "    dbo.owd_order_ship_info.carr_service,\n" +
                "    dbo.udf_getsladate(owd_order.order_id) AS sla,\n" +
                "    dbo.w_order_attributes.fingerprint,\n" +
                "    dbo.owd_order.group_name,\n" +
                "    dbo.w_order_attributes.total_physical_units," +
                "dbo.getLineItemDescriptionList(owd_order.order_id)," +
                "dbo.udf_getAisleNamesListForOrderId(owd_order.order_id) as aisles,\n" +
                "dbo.udf_getZoneNamesListForOrderId(owd_order.order_id) as zones,\n" +
                "dbo.getPackagingForPrintingByOrderId(owd_order.order_id) as boxType\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.client_fkey = dbo.owd_client.client_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_print_queue3\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = :facility\n" +
                "AND dbo.owd_order_ship_info.carr_service IN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            display\n" +
                "        FROM\n" +
                "            app_data\n" +
                "        WHERE\n" +
                "            project = 'global'\n" +
                "        AND description = 'shipMethod'\n" +
                "        AND VARIABLE = 'reds'\n" +
                "    )\n" +
                "AND dbo.owd_print_queue3.isVerified = 1 AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (dbo.w_order_attributes.business_order=0);";
        String bluesql = "SELECT\n" +
                "    dbo.owd_order.order_id,\n" +
                "    dbo.owd_order.order_num,\n" +
                "    dbo.owd_client.company_name,\n" +
                "    dbo.owd_order_ship_info.carr_service,\n" +
                "    dbo.udf_getsladate(owd_order.order_id) AS sla,\n" +
                "    dbo.w_order_attributes.fingerprint,\n" +
                "    dbo.owd_order.group_name,\n" +
                "    dbo.w_order_attributes.total_physical_units," +
                "dbo.getLineItemDescriptionList(owd_order.order_id),dbo.udf_getAisleNamesListForOrderId(owd_order.order_id) as aisles,\n" +
                "dbo.udf_getZoneNamesListForOrderId(owd_order.order_id) as zones,\n" +
                "dbo.getPackagingForPrintingByOrderId(owd_order.order_id) as boxType\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.client_fkey = dbo.owd_client.client_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_print_queue3\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = :facility\n" +
                "AND dbo.owd_order_ship_info.carr_service NOT IN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            display\n" +
                "        FROM\n" +
                "            app_data\n" +
                "        WHERE\n" +
                "            project = 'global'\n" +
                "        AND description = 'shipMethod'\n" +
                "        AND VARIABLE = 'reds'\n" +
                "    )\n" +
                "AND dbo.owd_print_queue3.isVerified = 1 AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (dbo.w_order_attributes.business_order=0);";
        String businesssql = "SELECT\n" +
                "    dbo.owd_order.order_id,\n" +
                "    dbo.owd_order.order_num,\n" +
                "    dbo.owd_client.company_name,\n" +
                "    dbo.owd_order_ship_info.carr_service,\n" +
                "    dbo.udf_getsladate(owd_order.order_id) AS sla,\n" +
                "    dbo.w_order_attributes.fingerprint,\n" +
                "    dbo.owd_order.group_name,\n" +
                "    dbo.w_order_attributes.total_physical_units," +
                "dbo.getLineItemDescriptionList(owd_order.order_id)," +
                "dbo.udf_getAisleNamesListForOrderId(owd_order.order_id) as aisles,\n" +
                "dbo.udf_getZoneNamesListForOrderId(owd_order.order_id) as zones,\n" +
                "dbo.getPackagingForPrintingByOrderId(owd_order.order_id) as boxType\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.client_fkey = dbo.owd_client.client_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_print_queue3\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = :facility\n" +
                "AND dbo.owd_print_queue3.isVerified = 1 AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (dbo.w_order_attributes.business_order=1);";
        String prsnlSql = "SELECT\n" +
                "    dbo.owd_order.order_id,\n" +
                "    dbo.owd_order.order_num,\n" +
                "    dbo.owd_client.company_name,\n" +
                "    dbo.owd_order_ship_info.carr_service,\n" +
                "    dbo.udf_getsladate(owd_order.order_id) as sla,\n" +
                "    dbo.w_order_attributes.fingerprint,\n" +
                "    dbo.owd_order.group_name,\n" +
                "    dbo.w_order_attributes.total_physical_units,\n" +
                "\tdbo.getLineItemDescriptionList(owd_order.order_id),\n" +
                "\tdbo.udf_getAisleNamesListForOrderId(owd_order.order_id) as aisles,\n" +
                "\tdbo.udf_getZoneNamesListForOrderId(owd_order.order_id) as zones,\n" +
                "\tdbo.getPackagingForPrintingByOrderId(owd_order.order_id) as boxType\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.client_fkey = dbo.owd_client.client_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_print_queue3\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_line_item\n" +
                "ON\n" +
                "\t(\n" +
                "\t\tdbo.owd_order.order_id = dbo.owd_line_item.order_fkey  \n" +
                "\t)\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = ':facility'\n" +
                "AND EXISTS (SELECT TOP 1 * FROM owd_tags WHERE external_id = owd_line_item.line_item_id AND [name] = 'PERSONALIZATION_JOB' AND owd_line_item.inventory_num != 'GREETING CARD')\n" +
                "AND dbo.owd_print_queue3.isVerified = 1 AND (owd_print_queue3.fileCreated = 1) \n" +
                "AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (dbo.w_order_attributes.business_order=0);";
        String gcardSql = "SELECT\n" +
                "    dbo.owd_order.order_id,\n" +
                "    dbo.owd_order.order_num,\n" +
                "    dbo.owd_client.company_name,\n" +
                "    dbo.owd_order_ship_info.carr_service,\n" +
                "dbo.udf_getsladate(owd_order.order_id) as sla,\n" +
                "    dbo.w_order_attributes.fingerprint,\n" +
                "    dbo.owd_order.group_name,\n" +
                "    dbo.w_order_attributes.total_physical_units,\n" +
                "dbo.getLineItemDescriptionList(owd_order.order_id),\n" +
                "dbo.udf_getAisleNamesListForOrderId(owd_order.order_id) as aisles,\n" +
                "dbo.udf_getZoneNamesListForOrderId(owd_order.order_id) as zones,\n" +
                "dbo.getPackagingForPrintingByOrderId(owd_order.order_id) as boxType\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.client_fkey = dbo.owd_client.client_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_print_queue3\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_line_item\n" +
                "ON\n" +
                "\t(\n" +
                "\t\tdbo.owd_order.order_id = dbo.owd_line_item.order_fkey  \n" +
                "\t)\n" +
                "LEFT JOIN dbo.owd_tags \n" +
                "ON\n" +
                "\t(\n" +
                "\t\tdbo.owd_line_item.line_item_id = dbo.owd_tags.external_id \n" +
                "\t)\t\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = :facility\n" +
                "AND dbo.owd_tags.name = 'PERSONALIZATION_JOB'\n" +
                "AND dbo.owd_print_queue3.isVerified = 1 AND (owd_print_queue3.fileCreated = 1) \n" +
                "AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (dbo.w_order_attributes.business_order=0)\n" +
                "AND EXISTS(SELECT 1 FROM owd_line_item WHERE order_fkey = owd_order.order_id AND inventory_num = 'GREETING CARD');\n";
        Query q;
        if(clientId.equals("red")){
            q = HibernateSession.currentSession().createSQLQuery(redsql);

            q.setParameter("facility",facility);
        } else if(clientId.equals("blue")){
            q = HibernateSession.currentSession().createSQLQuery(bluesql);

            q.setParameter("facility",facility);
        } else if(clientId.equals("business")) {
            q = HibernateSession.currentSession().createSQLQuery(businesssql);

            q.setParameter("facility", facility);
        } else if (clientId.equals("prsnl")) {
            q = HibernateSession.currentSession().createSQLQuery(prsnlSql);
            q.setParameter("facility", facility);
        } else if (clientId.equals("gcard")) {
            q = HibernateSession.currentSession().createSQLQuery(gcardSql);
            q.setParameter("facility", facility);
        }else{
            q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("facility",facility);
        }

         List results = q.list();
        if(results.size()>0){
              for(Object row:results){
                  Object[] data = (Object[]) row;
                  detailedReprintBean drb = new detailedReprintBean();
                  drb.setOrderId(data[0].toString());
                  drb.setOrderNum(data[1].toString());
                  drb.setClient(data[2].toString());
                  if(!clients.contains((data[2].toString()))){
                      clients.add(data[2].toString());
                  }

                  drb.setShipMethod(data[3].toString());
                  if(!shipMethods.contains(data[3].toString())){
                      shipMethods.add(data[3].toString());

                  }
                  drb.setSLA(data[4].toString());
                  if(!sla.contains(data[4].toString())){
                      sla.add(data[4].toString());
                  }
                  drb.setFingerprint(data[5].toString());

                  drb.setGroupName(data[6].toString());
                  if(!groupNames.contains(data[6].toString())){
                      if(data[6].toString().length()>0){
                          groupNames.add(data[6].toString());
                      }
                  }

                  drb.setUnits(data[7].toString());
                  if(!units.contains(data[7].toString())){
                      units.add(data[7].toString());
                  }
                  drb.setDescription(data[8].toString());
                  drb.setAisles(data[9].toString());
                  if(!aisles.contains(data[9].toString())){
                      aisles.add(data[9].toString());
                  }
                  drb.setZones(data[10].toString());
                  if(!zones.contains(data[10].toString())){
                      zones.add(data[10].toString());
                  }
                  drb.setBoxType(data[11].toString());
                  if(!boxTypes.contains(data[11].toString())){
                      boxTypes.add(data[11].toString());
                  }
                  info.add(drb);
              }
            Collections.sort(clients);
            Collections.sort(sla);
            Collections.sort(shipMethods);
            Collections.sort(groupNames);
            Collections.sort(units);
            Collections.sort(aisles);
            Collections.sort(boxTypes);


        }  else{
            throw new Exception("Nothing found to print");
        }


    }
    public static void getUnpickedPrintedOrderInfo(String facility, List<detailedReprintBean> details,List sla,List<String> clients,List<String> shipMethods,List<String> groupNames) throws Exception{
         String sql = "execute sp_unpickedPrintedOrdersNoHolds :facility";

          Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        List results = q.list();
        System.out.printf("We got %s orders that can be reprinted%n",results.size());

        for(Object row:results){
            Object[] data = (Object[]) row;
            detailedReprintBean drb = new detailedReprintBean();
            drb.setOrderId(data[0].toString());
            drb.setOrderNum(data[1].toString());
            drb.setClient(data[2].toString());
            if(!clients.contains(data[2].toString())){
                clients.add(data[2].toString());
            }
            drb.setShipMethod(data[3].toString());
            if(!shipMethods.contains(data[3].toString())){
                shipMethods.add(data[3].toString());

            }
            drb.setSLA(data[4].toString());
            if(!sla.contains(data[4].toString())){
                sla.add(data[4].toString());
            }
            drb.setFingerprint(data[5].toString());

            drb.setGroupName(data[6].toString());
            if(!groupNames.contains(data[6].toString())){
                if(data[6].toString().length()>0){
                groupNames.add(data[6].toString());
                }
            }
            drb.setPacked(data[7].toString());

            details.add(drb);
        }
        Collections.sort(clients);
        Collections.sort(sla);
        Collections.sort(shipMethods);
        Collections.sort(groupNames);


    }
    public static void main(String[] args){
        try{
   /*         Session sess = HibernateSession.currentSession();
       // releaseOrdersForClient("160","Warehouse Main 4000",sess);
          List<detailedReprintBean> drb = new ArrayList<detailedReprintBean>();
            List<String> sla = new ArrayList<String>();
            List<String> clients = new ArrayList<String>();
            List<String> shipmethods = new ArrayList<String>();
            List<String> group = new ArrayList<String>();
            List<String> units = new ArrayList<String>();
               getOrdersToPrintInfo("DC1","489",drb,sla,shipmethods,group,units);
            System.out.println(drb.get(0).getUnits());
           //  System.out.println(drb.get(0).getClient());
            System.out.println(drb.get(3).getUnits());
             System.out.println(drb.size());
            System.out.println(sla.size());
           // System.out.println(clients);
            System.out.println(shipmethods);
            System.out.println(group);
            System.out.println(units);*/
           /*  printErrorBean peb = loadPrintErrorBean();
            System.out.println(peb.getAddressTotal());
            System.out.println("Here is what we got");
            for(String s : peb.getFacilities().keySet()){
                facilityPrintInfo info = peb.getFacilities().get(s);
                System.out.println(info.getName());
                System.out.println(info.getDownloadTime());
                System.out.println(info.getToCreate());

            }*/
HibernateSession.currentSession();

            System.out.println("hello");
           /* Query q = HibernateSession.currentSession().createSQLQuery("execute sp_getPrintQueueIdFromOrderNum :orderNum");
            q.setParameter("orderNum","20798570");
            List data = q.list();*/

                                   loadPrintErrorBean();
                                           //reprintOrdersToPrinterViaId("DC1BLACK2","6864688,6893024");
          //  reprintSingleOrderByOrderNumber(sess,"8146334","testing");
          // System.out.println(getPrintOnePrinterFromName("North Printeaar",sess));
        } catch(Exception e){
                  e.printStackTrace();
        }

    }
    public static printErrorBean loadPrintErrorBean(){
        printErrorBean peb = new printErrorBean();
                  try{
                   //load global
                   String sql = "execute getGlobalPrintInfo";
                      Query q = HibernateSession.currentSession().createSQLQuery(sql);
                      q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

                     List results = q.list();
                      for (Object row : results){
                          Map data = (Map)row;
                          peb.setAddressNew(Integer.parseInt(data.get("addressNew").toString()));
                          peb.setAddressTotal(Integer.parseInt(data.get("addressTotal").toString()));
                          peb.setCreatedTotal(Integer.parseInt(data.get("createdTotal").toString()));
                          peb.setCreateNew(Integer.parseInt(data.get("createNew").toString()));

                      }
                      System.out.println("Just set the global variables");


                      //load warehouseinfo start with timeframe;
                   sql = "select top 6 * from app_data where project = 'wms' and description = 'printserver' order by id";
                  q = HibernateSession.currentSession().createSQLQuery(sql);
                      q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

                  results = q.list();
                      for (Object row : results){
                          Map data = (Map)row;
                          String s = data.get("value").toString();
                          if(peb.getFacilities().containsKey(s)==false){
                             facilityPrintInfo fpi = new facilityPrintInfo();
                              fpi.setName(s);
                              peb.getFacilities().put(s, fpi);
                          }
                          Date now = Calendar.getInstance().getTime();

                          String function = data.get("variable").toString();
                          SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy hh:mma");
                          if(function.equals("download")){
                            peb.getFacilities().get(s).setDownloadTime(data.get("display").toString());
                            System.out.println(peb.getFacilities().get(s).getDownloadTime());
                            Date down = formatter.parse(data.get("display").toString());

                              System.out.println(now.getTime());
                              System.out.println(down.getTime());
                              System.out.println("Original minus");
                              System.out.println(now.getTime()-down.getTime());
                              System.out.println((now.getTime()-down.getTime())/1000/60);
                               peb.getFacilities().get(s).setDownloadDifference((int) (now.getTime()-down.getTime())/1000/60);


                          }
                          if(function.equals("printCheck")){
                            peb.getFacilities().get(s).setPrintCheckTime(data.get("display").toString());
                              System.out.println(peb.getFacilities().get(s).getPrintCheckTime());
                              Date down = formatter.parse(data.get("display").toString());
                              peb.getFacilities().get(s).setPrintCheckDifference((int) (now.getTime()-down.getTime())/1000/60);
                              System.out.println("Printcheckdiff");
                              System.out.println(peb.getFacilities().get(s).getPrintCheckDifference());
                          }

                      }

                      //loop through and fill out rest of info now
                      for(String s:peb.getFacilities().keySet()){
                          System.out.println("getting info for "+s);
                          sql = "SELECT\n" +
                                  "    sum (Case  when dbo.owd_print_queue3.isVerified= 0 then 1 else 0 end) as toVerify,\n" +
                                  "sum (Case  when dbo.owd_print_queue3.fileCreated= 0 then 1 else 0 end) as toCreate,\n" +
                                  "sum (Case  when dbo.owd_print_queue3.fileDownloaded= 0 then 1 else 0 end) as toDownload\n" +
                                  "\n" +
                                  "FROM\n" +
                                  "    dbo.owd_order\n" +
                                  "INNER JOIN dbo.owd_print_queue3\n" +
                                  "ON\n" +
                                  "    (\n" +
                                  "        dbo.owd_order.order_id = dbo.owd_print_queue3.order_id\n" +
                                  "    )\n" +
                                  "WHERE\n" +
                                  "    dbo.owd_order.facility_code = :facility and created > DATEADD(d,-2,getDate())and datediff(second,post_date,getdate())>0;";
                           q = HibernateSession.currentSession().createSQLQuery(sql);
                          q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                          q.setParameter("facility",s);
                          results = q.list();
                          for (Object row : results){
                              Map data = (Map)row;
                              peb.getFacilities().get(s).setToVerify(Integer.parseInt(data.get("toVerify").toString()));
                              peb.getFacilities().get(s).setToCreate(Integer.parseInt(data.get("toCreate").toString()));
                              peb.getFacilities().get(s).setToDownload(Integer.parseInt(data.get("toDownload").toString()));
                          }
                      }

                  } catch (Exception e){
                      e.printStackTrace();

                  }


        return peb;
    }
    public static Map<String, String> getPrinters(String location){
        Map<String, String> m = new TreeMap<String, String>();
       
       try{
           Query q = HibernateSession.currentSession().createSQLQuery(getPrinterList).setString("location",location);
           for (Object row : q.list().toArray()){
               Object[] data = (Object[])row;
               m.put(data[0].toString(), data[1].toString());
           }
       } catch (Exception e){

       }

       return m;

    }
    public static Map<String, String> getTodayPrinters(String location){
        Map<String, String> m = new TreeMap<String, String>();

       try{
           Query q = HibernateSession.currentSession().createSQLQuery(getTodayPrinterList).setString("location",location);
           for (Object row : q.list().toArray()){
               Object[] data = (Object[])row;
               m.put(data[0].toString(), data[1].toString());
           }
       } catch (Exception e){

       }

       return m;

    }
    public static boolean getAutoRelease(){
        boolean auto = false;
        try{
                  Query q = HibernateSession.currentSession().createSQLQuery(getAutoReleaseSql);
          if(q.list().get(0).equals("true")) auto = true;
            HibernateSession.closeStatement();

        }catch(Exception e){

        }
      return auto;
    }
    public static int releaseOrdersForClient(String clientId, String printer, Session sess,String location) throws Exception{
        if (printer.length()<3) throw new Exception("Invalid Printer was selected");
        int rows = 0;



        //release groups
        rows = printGroupOrdersForClient(clientId, printer, sess,location);

        //relese by email
       try{

          rows = 0;
            Query q2 = sess.createSQLQuery(updateSentToQueueRedsSql);

        q2.setParameter("clientId", clientId);
           q2.setParameter("location",location);
        rows = q2.executeUpdate();

        if(rows>0){
            System.out.println("moving RedsXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            q2 = sess.createSQLQuery(moveToPrinterQueueRedsSql);
            q2.setParameter("clientId",clientId);

            rows = 0;
            rows = q2.executeUpdate();

               if (rows>0){
                   q2 = sess.createSQLQuery(deleteMovedSql);
                   q2.executeUpdate();

                   HibUtils.commit(sess);
               }


        }

        } catch (Exception e){
            System.out.println("Error doing emails and such ");
            e.printStackTrace();
        }
        //doing normal release
     try{
          Query q;
         rows = 0;
         if (printer.equalsIgnoreCase("AATODAY")){
             System.out.println("doing Today printing");
                q = sess.createSQLQuery(updateSentToQueueTodaySql);
         }else{
           q = sess.createSQLQuery(updateSentToQueueSql);
         }
        q.setParameter("clientId",clientId);
         q.setParameter("location",location);
        rows = q.executeUpdate();

        if (rows>0){
            System.out.println("It updated "+rows);
            if (printer.equalsIgnoreCase("Asla")||printer.equals("AATODAY")){
                System.out.println("doing sla printer");
             q = sess.createSQLQuery(moveToPrintQueueSqlSLA);

            }else{
        q = sess.createSQLQuery(moveToPrintQueueSql);

            q.setParameter("printer", printer);
            }
            q.setParameter("clientId",clientId);
                rows = q.executeUpdate();
         if(rows> 0 ){
             System.out.println("moved "+rows);

                   q = sess.createSQLQuery(deleteMovedSql);
                   q.executeUpdate();

                   HibUtils.commit(sess);

         }else{
             HibUtils.rollback(sess);
         }

        }
     }catch (Exception e){
           e.printStackTrace();
     }
        return rows;
    }

    public static int printGroupOrdersForClient(String clientId, String printer, Session sess,String location) throws Exception{
        if (printer.length()<3) throw new Exception("Invalid Printer was selected");
        int rows = 0;
        try{
          
               Query q2 = sess.createSQLQuery(updateSentToQueueGroupSql);

           q2.setParameter("clientId", clientId);
            q2.setParameter("location",location);
           rows = q2.executeUpdate();

           if(rows>0){
               System.out.println("moving groups");
                if (printer.equalsIgnoreCase("Asla")||printer.equalsIgnoreCase("AATODAY")){
                       q2 = sess.createSQLQuery(moveToPrintQueueGroupSqlSla);
                } else{
                     q2 = sess.createSQLQuery(moveToPrintQueueGroupSql);
                      q2.setParameter("printer",printer);
                }

               q2.setParameter("clientId",clientId);

               rows = 0;
               rows = q2.executeUpdate();

                  if (rows>0){
                      q2 = sess.createSQLQuery(deleteMovedSql);
                      q2.executeUpdate();

                      HibUtils.commit(sess);
                  }


           }
        }catch(Exception e){
            System.out.println("error doing group rlease");
            e.printStackTrace();
        }
        return rows;
    }
   public static int reprintSingleOrderByOrderNumber(Session sess,String orderNum, String printer) throws Exception{
       if (printer.length()<3) throw new Exception("Invalid Printer was selected");
        int results = 0;

       String lookupsql = "execute sp_getPrintedPrintQueueIdFromOrderNum :orderNum ;";
         try{
          Query qq = sess.createSQLQuery(lookupsql);
             qq.setParameter("orderNum",orderNum);
              List l = qq.list();
             if (l.size()>0){
                   String printid = l.get(0).toString();
                 String sqlInsert = "insert into owd_print_queue_sl (order_id, client_id, printer_name,pdf_binary,created) select order_id, " +
                         "client_id, :printer ,pdf_binary,getDate() from zzPrintedSlips where print_queue_id = :id";
                   Query insert = sess.createSQLQuery(sqlInsert);
                 insert.setParameter("id", printid);
                 insert.setParameter("printer",printer) ;
                 results = insert.executeUpdate();
                 HibUtils.commit(sess);
             } else{

            //todo fix this to get the custom slips for sneakpeek and such

       String sql = "insert into  owd_print_queue_sl (order_id, client_id, printer_name) (select  order_id, client_id, :printer from owd_order, owd_client where client_id = client_fkey and order_num = :orderNum)";



            Query q = sess.createSQLQuery(sql);
           q.setString("printer",printer);
           q.setString("orderNum",orderNum);
           results = q.executeUpdate();
             HibUtils.commit(sess);
             }

       } catch(Exception e){
                e.printStackTrace();
       }
       return results;
   }
    private static String printIdSql ="execute sp_getPrintQueueIdFromOrderNum :orderNum";
    private static String moveSingleOrder ="insert into owd_print_queue_sl (order_id, client_id, created, printer_name, pdf_binary) SELECT     owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, :printerName, owd_print_queue3.pdf_binary \n" +
            "from owd_print_queue3\n" +
            "where      (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) and print_queue_id = :printQueueId";
    private static String deleteSingleOrder = "delete from owd_print_queue3 where print_queue_id = :printId";

    public static int printSingleOrderByOrderNumber(Session sess,String orderNum, String printer) throws Exception{
        if (printer.length()<3) throw new Exception("Invalid Printer was selected");
        int rows = 0;
        try{
            Query q = sess.createSQLQuery(printIdSql);
             q.setParameter("orderNum",orderNum);
            List data = q.list();
           if (data.size()>0){
              String id = data.get(0).toString();
               System.out.println(id);

              q = sess.createSQLQuery(moveSingleOrder);
               q.setParameter("printerName",printer);
               q.setParameter("printQueueId",id);

               rows = q.executeUpdate();
               System.out.println("Update rows for single "+ rows);
if(rows>0){
    q = sess.createSQLQuery(deleteSingleOrder);
    q.setParameter("printId",id);
   rows =  q.executeUpdate();
    System.out.println("delete rows for single "+ rows);
    if (rows > 0) {
        HibUtils.commit(sess);
    }

}



           } else{
               return 0;
           }




    } catch(Exception e){
              try{
               HibUtils.rollback(sess);
              }catch(Exception ex){

              }
        }






        return rows;
    }
     public static void printDetailedSelection(String clientId, String items, String method, String time,String printer,Session sess,String location) throws Exception{
         if (printer.length()<3) throw new Exception("Invalid Printer was selected");
         String sql= "UPDATE    owd_print_queue3\n" +
                 "SET              sentToQueue = 1\n" +
                 "WHERE     (order_id IN\n" +
                 "                          (SELECT     order_id\n" +
                 "                            FROM          (SELECT     COUNT(DISTINCT owd_line_item.inventory_id) AS items, owd_print_queue3.order_id, owd_order_ship_info.carr_service, \n" +
                 "                                                                           owd_print_queue3.sla\n" +
                 "                                                    FROM          owd_print_queue3 INNER JOIN\n" +
                 "                                                                           owd_order_ship_info ON owd_print_queue3.order_id = owd_order_ship_info.order_fkey INNER JOIN\n" +
                 "                                                                           owd_line_item ON owd_print_queue3.order_id = owd_line_item.order_fkey INNER JOIN\n" +
                 "                                                                           owd_order ON owd_print_queue3.order_id = owd_order.order_id\n" +
                 "                                                    WHERE      (owd_print_queue3.client_id = :clientId) AND (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (owd_order.facility_code=:location)\n" +
                 "                                                    GROUP BY owd_print_queue3.order_id, owd_line_item.is_parent, owd_order_ship_info.carr_service, owd_print_queue3.sla, \n" +
                 "                                                                           owd_order.post_date\n" +
                 "                                                    HAVING    (owd_line_item.is_parent = 0) AND (owd_order.post_date <= GETDATE())) DERIVEDTBL\n" +
                 "                            WHERE      (items = :items) AND (carr_service = :method) AND (owd_print_queue3.sla = :time)))";

           //todo send groups to printer

             try{
                 printer = getPrintOnePrinterFromName(printer,sess);
               Query q = sess.createSQLQuery(sql);
                 q.setParameter("clientId",clientId);
                 q.setParameter("items",items);
                 q.setParameter("method",method);
                 q.setParameter("time",time);
                 q.setParameter("location",location);
                 int rows = q.executeUpdate();
                System.out.println("updated:     " + rows);
                 if( rows > 0){
                     q = sess.createSQLQuery(moveToPrintQueueSql);
                   q.setParameter("printer",printer);


            q.setParameter("clientId",clientId);

            rows = 0;
            rows = q.executeUpdate();

               if (rows>0){
                   q = sess.createSQLQuery(deleteMovedSql);
                   q.executeUpdate();

                   HibUtils.commit(sess);
               }

                  
                 }


             } catch (Exception e){
                 e.printStackTrace();
             }

     }
      public static List<clientDetailsBean> getDetails(String clientId,String location){
          String sql = "SELECT     items, COUNT(items) AS Expr1, carr_service, sla\n" +
                  "FROM         (SELECT     COUNT(DISTINCT owd_line_item.inventory_id) AS items, owd_print_queue3.order_id, owd_order_ship_info.carr_service, owd_print_queue3.sla\n" +
                  "FROM         owd_print_queue3 INNER JOIN\n" +
                  "                      owd_order_ship_info ON owd_print_queue3.order_id = owd_order_ship_info.order_fkey INNER JOIN\n" +
                  "                      owd_line_item ON owd_print_queue3.order_id = owd_line_item.order_fkey INNER JOIN\n" +
                  "                      owd_order ON owd_print_queue3.order_id = owd_order.order_id\n" +
                  "WHERE     (owd_print_queue3.client_id = :clientId) AND (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.doNotShowInQueue = 0) AND (owd_order.facility_code=:location)\n" +
                  "GROUP BY owd_print_queue3.order_id, owd_line_item.is_parent, owd_order_ship_info.carr_service, owd_print_queue3.sla, owd_order.post_date\n" +
                  "HAVING    (owd_line_item.is_parent = 0) AND (owd_order.post_date <= GETDATE())) DERIVEDTBL\n" +
                  "GROUP BY items, carr_service, sla\n" +
                  "ORDER BY items";
          List<clientDetailsBean> m = new ArrayList<clientDetailsBean>();

          try{
          Query q = HibernateSession.currentSession().createSQLQuery(sql);
              q.setParameter("clientId",clientId);
              q.setParameter("location",location);
           for (Object row : q.list().toArray()){
               Object[] data = (Object[])row;
               clientDetailsBean b = new clientDetailsBean();
               b.setLines(data[0].toString());
               b.setOrders(data[1].toString());

               b.setMethod(data[2].toString());
               b.setSla(data[3].toString());
               m.add(b);
           }
          }catch (Exception e){
              e.printStackTrace();
          }


         return m;
      }

    public static boolean clientHasGroupItemsToPrint(String clientId, Session sess,String location){
        boolean g = false;
        try{
            String sql = "SELECT\n" +
                    "    dbo.owd_print_queue3.print_queue_id\n" +
                    "FROM\n" +
                    "    dbo.owd_print_queue3\n" +
                    "INNER JOIN dbo.owd_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_print_queue3.order_id = dbo.owd_order.order_id\n" +
                    "    )\n" +
                    "WHERE\n" +
                    "    dbo.owd_order.facility_code = :location\n" +
                    "AND dbo.owd_print_queue3.client_id = :clientId\n" +
                    "AND dbo.owd_print_queue3.printgroup_id IS NOT NULL ;";
            Query q = sess.createSQLQuery(sql);

            q.setParameter("clientId",clientId);
            q.setParameter("location",location);
             if (q.list().size()>0)             return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return g;



    }
    public static String getPrintOnePrinterFromName(String name, Session sess){
       String sql = " select value from app_data where display = :name and variable = 'printer1' and description = 'packSlips' and project = 'wms'";
        try{
              Query q = sess.createSQLQuery(sql);
            q.setParameter("name",name);
            return q.list().get(0).toString();
        }catch(Exception e){
            e.printStackTrace();
            return name;
        }


        
    }
}

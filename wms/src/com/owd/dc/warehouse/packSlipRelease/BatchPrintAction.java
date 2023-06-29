package com.owd.dc.warehouse.packSlipRelease;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.setup.selectList;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/16/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchPrintAction extends ActionSupport{
    private String clientId;
    private String printer;
    private String batch = "no";
    private String date;
    private String batchSize;
    private List<batchData> theData = new ArrayList<batchData>();
    private String fingerprint;
    private List<selectList> clients;
    private String facility;
    private Map<String,String> printerMap;

     public String startBatchPrint(){
           setClientList(facility);
         return "success";
     }

    private void fillData() throws Exception{
        String sql = "SELECT\n" +
                " count(dbo.owd_print_queue3.order_id),\n" +
                "    dbo.w_order_attributes.fingerprint,\n" +
                "   \n" +
                "    dbo.owd_order.group_name\n" +
                "FROM\n" +
                "    dbo.owd_print_queue3\n" +
                "INNER JOIN dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_print_queue3.order_id = dbo.owd_order.order_id\n" +
                "    )\n" +
                "INNER JOIN dbo.w_order_attributes\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey \n" +
                "    ) \n" +
                "where owd_print_queue3.client_id = :clientId and facility_code =:facility and customer_vendor_no like :theDate \n" +
                "\n" +
                "\n" +
                "group by fingerprint,group_name \n" +
                "having count(dbo.owd_print_queue3.order_id) >:batchSize \n" +
                "order by count(dbo.owd_print_queue3.order_id) desc";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("theDate","%"+date+"%");
            q.setParameter("batchSize",batchSize);
        q.setParameter("facility",facility);
            List resulst = q.list();
            if(resulst.size()>0){
                theData = new ArrayList<batchData>();
                for(Object row : resulst){
                    Object[] data = (Object[]) row;
                    batchData bd = new batchData();
                    bd.setCount(data[0].toString());
                    bd.setFingerprint(data[1].toString());
                    try{
                    bd.setGroupname(data[2].toString());
                    }catch  (Exception e){
                        bd.setGroupname("");
                    }
                    theData.add(bd);


                }

            }
    }

    public String loadData(){
         try{

             fillData();
             setClientList(facility);
             printerMap=packSlipUtilities.getTodayPrinters(facility);
        if(theData.size()>0){
             }else{
                 throw new Exception("We have nothing for this client in queue for this date");
             }



         } catch (Exception e){
             addActionError(e.getMessage());
             e.printStackTrace();
         }






      return "success";
    }

    public  String printFingerprint() throws Exception{
            System.out.println("in print fingerprint");
        if (printer.length()<3) throw new Exception("Invalid Printer was selected");
        System.out.println(fingerprint) ;
        System.out.println(printer);
        System.out.println(date);
        setClientList(facility);
        try{
            String sql = "execute printOrderFromQueueViaFingerprintNoBatchPickFacility :fingerprint,:printer,:sla,:facility";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("fingerprint",fingerprint);
            q.setParameter("printer",printer);
            q.setParameter("sla",date);
            q.setParameter("facility",facility);
            int rows = q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
            addActionMessage("We printed "+rows +" for "+ fingerprint);
           fillData();
        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return "success";
    }

    public static class batchData{
        private String count;
        private String fingerprint;
        private String groupname;

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String goupname) {
            this.groupname = goupname;

        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getFingerprint() {
            return fingerprint;
        }

        public void setFingerprint(String fingerprint) {
            this.fingerprint = fingerprint;
        }
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }

    public List<batchData> getTheData() {
        return theData;
    }

    public void setTheData(List<batchData> theData) {
        this.theData = theData;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
   private  void setClientList(String facility){


       clients = new ArrayList<selectList>();
       try{
        String sql = "SELECT\n" +
                "    MAX(dbo.owd_client.client_id),\n" +
                "    dbo.owd_client.company_name\n" +
                "FROM\n" +
                "    dbo.owd_print_queue3\n" +
                "INNER JOIN dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_print_queue3.client_id = dbo.owd_client.client_id\n" +
                "    )\n" +
                "INNER JOIN dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_print_queue3.order_id = dbo.owd_order.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_order.facility_code = :facility\n" +
                "GROUP BY\n" +
                "    dbo.owd_client.company_name\n" +
                "ORDER BY\n" +
                "    dbo.owd_client.company_name ASC ;" ;
           Query q = HibernateSession.currentSession().createSQLQuery(sql);
           q.setParameter("facility",facility);
           List results = q.list();
           if(results.size()>0){
                 for(Object row:results){
                     Object[] data = (Object[]) row;
                     selectList sl = new selectList();
                     sl.setAction(data[0].toString());
                     sl.setDisplay(data[1].toString());
                     clients.add(sl);
                 }

           } else{
               selectList sl = new selectList();
               sl.setAction("none");
               sl.setDisplay("No more to print for today");
               clients.add(sl);
           }

       }catch(Exception e){

       }
   }
    public List<selectList> getClients() {


        return clients;
    }

    public void setClients(List<selectList> clients) {
        this.clients = clients;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public Map<String, String> getPrinterMap() {
        return printerMap;
    }

    public void setPrinterMap(Map<String, String> printerMap) {
        this.printerMap = printerMap;
    }
}

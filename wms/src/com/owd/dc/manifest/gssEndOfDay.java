package com.owd.dc.manifest;

import GSSMailer.*;
import com.owd.dc.manifest.Manifestxml.dispatchIdList;
import com.owd.dc.manifest.Manifestxml.gssReportList;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.thoughtworks.xstream.XStream;
import org.hibernate.Query;
import org.hibernate.Session;
import java.io.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 8, 2010
 * Time: 9:00:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class gssEndOfDay {
    private String facility;
    private gssAuthentication authentication ;
    ProcessPackageLocator locator = new ProcessPackageLocator();
            ProcessPackageSoap_PortType service ;

    public gssEndOfDay(String fac) throws Exception{
                    facility = fac;

         authentication = new gssAuthentication("gssEndOFDay",facility);

            service = locator.getProcessPackageSoap();
    }
    public boolean getReportsforDispatchIdJustWriteFiles(String dispatchId,Session sess) throws Exception{
         boolean success=false;
           gssReportList reportlist = new gssReportList();
      String sql = "select report_list from gss_closeout where dispatch_id = :id";
       Query q = sess.createSQLQuery(sql);
       q.setString("id",dispatchId);
        System.out.println("getting results for id: "+dispatchId);
      String[] reportslist = q.list().get(0).toString().split("\\|");
       System.out.println(reportslist);
       for(String report:reportslist){

           System.out.println(report);
          if(report.length()>0){
              String[] namesplit = report.split(",");



               GenerateReportResult result = service.generateReport(dispatchId,namesplit[0],authentication.getAccessToken());
              System.out.println(result.getMessage());
              System.out.println(result.getResponseCode());
            if(result.getResponseCode() ==0){
                gssReportList.gssReport thereport = new gssReportList.gssReport();
               // thereport.setName(namesplit[1]);
               // thereport.setPdf(org.apache.commons.codec.binary.Base64.encodeBase64String(result.getReport()));

                //reportlist.getReports().add(thereport);
                  writeReportToDisk(dispatchId,namesplit[1],result.getReport());

            }





          }
       }


                     success = true;
       return success               ;

    }
   public String getReportsforDispatchIdXml(String dispatchId,Session sess) throws Exception{
       gssReportList reportlist = new gssReportList();
      String sql = "select report_list from gss_closeout where dispatch_id = :id";
       Query q = sess.createSQLQuery(sql);
       q.setString("id",dispatchId);
      String[] reportslist = q.list().get(0).toString().split("\\|");
       System.out.println(reportslist);
       for(String report:reportslist){

           System.out.println(report);
          if(report.length()>0){
              String[] namesplit = report.split(",");



               GenerateReportResult result = service.generateReport(dispatchId,namesplit[0],authentication.getAccessToken());
            if(result.getResponseCode() ==0){
                gssReportList.gssReport thereport = new gssReportList.gssReport();
                thereport.setName(namesplit[1]);
                thereport.setPdf(org.apache.commons.codec.binary.Base64.encodeBase64String(result.getReport()));

                reportlist.getReports().add(thereport);
                  writeReportToDisk(dispatchId,namesplit[1],result.getReport());

            }





          }
       }

             XStream x = gssReportList.getXStream();

       return x.toXML(reportlist);
   }
    public String closeDistatchandGetReportsXml(Session sess) throws Exception{

        gssReportList reports = new gssReportList();
        String dispatchID = closeDispatch(sess);
        System.out.println("This is our dispatch ID: "+dispatchID);
        Map<String,String> availableReports = getAvailableReportsForDispatch(dispatchID,sess);
        if(getReportsforDispatchIdJustWriteFiles(dispatchID,sess)){

             reports = getReportsInMapJustLinks(dispatchID,sess);

        } else{
            throw new Exception("Unable to write files properly, please contact IT with this number "+ dispatchID);
        }


         XStream x = gssReportList.getXStream();



        return x.toXML(reports);
    }
    public static void main(String[] args){
        try{

            gssEndOfDay gss = new gssEndOfDay("DC1");
           // Map<String,String> availableReports = gss.getAvailableReportsForDispatch("OW001065",HibernateSession.currentSession());
          //  System.out.println(availableReports);
          //  gss.getReportsforDispatchIdJustWriteFiles("OW001065",HibernateSession.currentSession());
            gss.checkForAndCreateFilesIFneeded("OW002271",HibernateSession.currentSession());
        //    gssReportList reports = gss.getReportsInMapJustLinks("OW000450",HibernateSession.currentSession());
          //  System.out.println(reports);
         //   for (gssReportList.gssReport r : reports.getReports()){
         //       System.out.println(r.getName());
          //      System.out.println(r.getLink());
          //  }
          //   XStream x = gssReportList.getXStream();



      //  System.out.println(x.toXML(reports));
          // System.out.println(gss.getReportListForDirctory("OW000212"));

          /*    int i = 380;
            while( i >322){
               System.out.println("Doing "+i);
               gss.getReportsforDispatchIdJustWriteFiles("OW000"+i,HibernateSession.currentSession());
                i = i- 1;

            }*/

          //  System.out.println(gss.getReportsforDispatchIdJustWriteFiles("OW000212",HibernateSession.currentSession()));
            // System.out.println(gss.getReportsforDispatchIdXml("OW000213",HibernateSession.currentSession()));
           /* Map<String,String> reports = gss.getAvailableReportsForDispatch("OW000001",HibernateSession.currentSession());
                             System.out.println(reports);
            gssReportList pdfs = gss.getReportsInMap(reports,"OW000001");
            System.out.println(pdfs);
            XStream x = gssReportList.getXStream();
            System.out.println(x.toXML(pdfs));*/
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     public dispatchIdList getDispatchIdList(Session sess) throws Exception{
             System.out.println("in dispatchIDList");
       String sql = "select  dispatch_id, created_date from gss_closeout where facility = :facility order by created_date desc";
       Query q = sess.createSQLQuery(sql);
         q.setString("facility",facility);
       List results = q.list();
       dispatchIdList idList = new dispatchIdList();
       for(Object row: results){
           Object[] data = (Object[]) row;
             dispatchIdList.dispatch d = new dispatchIdList.dispatch();
           d.setCreatedDate(data[1].toString());
           d.setDispatchId(data[0].toString());

           idList.getDispatchList().add(d);


       }

       //XStream x = dispatchIdList.getXStream();

       return (idList);


   }
   public String getDispatchIdXml(Session sess) throws Exception{

       String sql = "select  dispatch_id, created_date from gss_closeout where facility = :facility and created_date > :thedate order by created_date desc";
       Query q = sess.createSQLQuery(sql);
       q.setString("facility",facility);
       Calendar ca =  Calendar.getInstance();
          ca.add(Calendar.MONTH,-6);
       q.setDate("thedate", ca.getTime());
       List results = q.list();
       dispatchIdList idList = new dispatchIdList();
       for(Object row: results){
           Object[] data = (Object[]) row;
             dispatchIdList.dispatch d = new dispatchIdList.dispatch();
           d.setCreatedDate(data[1].toString());
           d.setDispatchId(data[0].toString());

           idList.getDispatchList().add(d);


       }
       
       XStream x = dispatchIdList.getXStream();

       return x.toXML(idList);


   }
    public List<String> getReportListForDirctory(String ID) throws Exception{
      File  folder = new File("/var/www/html/GssReports/"+ID);
        File[] listOfFiles = folder.listFiles();
            List<String> theReports = new ArrayList<String>();
        for(File f : listOfFiles){
            theReports.add(f.getName());

        }

          return theReports;

    }
    public boolean checkForAndCreateFilesIFneeded(String ID, Session sess){
        boolean success = false;
        String directory = "/var/www/html/GssReports/"+ID;
        File fdirectory = new File(directory);
        if(!fdirectory.exists()){
            try{
            getReportsforDispatchIdJustWriteFiles(ID,sess);
            }catch(Exception e){
                return success;

            }
        }

        success = true;
        return success;
    }
    private boolean writeReportToDisk(String ID, String ReportName, byte[] data){
        boolean success = false;

        try{
           String directory = "/var/www/html/GssReports/"+ID;
            File fdirecotry = new File(directory);
            if (!fdirecotry.exists()){
               if(!fdirecotry.mkdirs()){
                   throw new Exception("Unable to create directoryies");
               }

            }

                if(ReportName.contains("XML")){
                    ReportName = ReportName +".xml";
                }else if(ReportName.contains("Excel")){
                    ReportName = ReportName +".xls";
                }else{
                    ReportName = ReportName +".pdf";
                }
              String filename = directory+"/"+ReportName;
                System.out.println("Writing fileseeeeeeeeeeeeeeeeeeeeeeeee   "+filename);
                FileOutputStream fstream = new FileOutputStream(filename);
                //BufferedWriter out = new BufferedWriter(fstream);
                fstream.write(data);
                 fstream.close();
                success = true;
        }catch(Exception e){
            e.printStackTrace();

        }

             return success;

    }
    public String getReportsInMapJustLinksXML(String dispatchID, Session sess) throws Exception{
        gssReportList reports = getReportsInMapJustLinks(dispatchID,sess);
         XStream x = gssReportList.getXStream();



      return x.toXML(reports);

    }
    private gssReportList getReportsInMapJustLinks(String dispatchID,Session sess) throws Exception{

         gssReportList reports = new gssReportList();
         gssReportList reportlist = new gssReportList();
      String sql = "select report_list from gss_closeout where dispatch_id = :id";
       Query q = sess.createSQLQuery(sql);
       q.setString("id",dispatchID);
      String[] reportslist = q.list().get(0).toString().split("\\|");
       System.out.println(reportslist);
       for(String reportstring:reportslist){

           System.out.println(reportstring);
          if(reportstring.length()>0){
              String[] namesplit = reportstring.split(",");


            gssReportList.gssReport report = new gssReportList.gssReport();
                report.setName(namesplit[1]);
               // report.setPdf(org.apache.commons.codec.binary.Base64.encodeBase64String(result.getReport()));
                 report.setLink("http://it.owd.com/GssReports/"+dispatchID+"/"+namesplit[1]+".pdf");
                reports.getReports().add(report);







          }
       }

       return reports;
    }
    private gssReportList getReportsInMap(Map<String, String> availableReports,String dispatchID) throws Exception{

         gssReportList reports = new gssReportList();
        for (Map.Entry<String, String> e: availableReports.entrySet()){

            GenerateReportResult result = service.generateReport(dispatchID,e.getKey(),authentication.getAccessToken());
            if(result.getResponseCode() ==0){
                gssReportList.gssReport report = new gssReportList.gssReport();
                report.setName(e.getValue());
                report.setPdf(org.apache.commons.codec.binary.Base64.encodeBase64String(result.getReport()));

                reports.getReports().add(report);


            }

        }

       return reports;
    }
    private Map<String,String> getAvailableReportsForDispatch(String dispatchID, Session sess)throws Exception{
        Map<String,String> availableReports = new TreeMap<String,String>();
        
       GetAvailableReportResult result =  service.getAvailableReportsForDispatch(dispatchID,authentication.getAccessToken());
      if(result.getResponseCode()== 0){
          StringBuffer sb = new StringBuffer();

           for (String s: result.getReport()){
                sb.append(s);
               sb.append("|");
               String[] split = s.split(",");
               availableReports.put(split[0],split[1]);


           }
          String sql = "update gss_closeout set report_list = :list where dispatch_id = :id";
          Query q = sess.createSQLQuery(sql);
          q.setString("list",sb.toString());
          q.setString("id",dispatchID);
          int update = q.executeUpdate();
          if (update > 0){
              HibUtils.commit(sess);
          }
            System.out.println(sb.toString());
      }else{

          throw new Exception(result.getMessage());
      }


         return availableReports;
    }


    private String closeDispatch(Session sess) throws Exception{
        String id = "";
            System.out.println("In Close Dispatch");
      CloseDispatchResult result =  service.closeDispatch("","","","",authentication.getAccessToken());
            if (result.getResponseCode()==0){
                id = result.getDispatchID();
                System.out.println("xxGssDispatchId "+ id);
                String sql = "insert into gss_closeout (dispatch_id,facility) values(:id,:facility) ";
                Query q = sess.createSQLQuery(sql);
                q.setString("id",id);
                q.setString("facility",facility);
                 int update = q.executeUpdate();
                if (update>0){
                    HibUtils.commit(sess);
                } else{
                    throw new Exception("unable to insert record Contact IT with this number "+id);
                }
            } else{
                System.out.println(result.getMessage());
                throw new Exception(result.getMessage());
            }


         return id;
    }

}

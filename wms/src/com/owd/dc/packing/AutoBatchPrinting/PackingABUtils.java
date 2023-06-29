package com.owd.dc.packing.AutoBatchPrinting;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.owd.connectship.soap.ObjectFactory;
import com.owd.core.ftp.FTPManager;
import com.owd.dc.packing.AutoBatchPrinting.beans.SummaryInfoBean;
import com.owd.dc.packing.AutoBatchPrinting.beans.abBatchBatch;
import com.owd.dc.packing.AutoBatchPrinting.beans.abLabelData;
import com.owd.dc.packing.AutoBatchPrinting.beans.successBean;
import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.dc.warehouse.vendorCompliance.labelsMaker.jsonUtilties;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.transform.CacheableResultTransformer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by danny on 2/4/2017.
 */
public class PackingABUtils {

    private final static Logger log =  LogManager.getLogger();
    public static void main(String[] args){

        try{
            Map<String,Integer> orders = new HashMap<String, Integer>();
            List<String> l = new ArrayList<String>();
            System.out.println(getJsonSummaryBatchInfo("DC1"));
            //System.out.println(getJsonSummaryBatchInfo("DC6"));
         System.out.println(getXmlAbBatchForFingerprintFacility("MultiPieceBatchTest:3 | USPS Priority Mail","DC1","2019-12-05 18:15:00","One World Direct"));
         // System.out.println(getXmlmarkPackageTransferred("9580055"));
           // String poData = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\wms\\src\\com\\owd\\dc\\packing\\AutoBatchPrinting", "batchFile.xml")));
          // String poData2 = new String(Files.readAllBytes(Paths.get("C:\\Users\\danny\\Documents\\GitHub\\core\\wms\\src\\com\\owd\\dc\\packing\\AutoBatchPrinting", "batchFile2.xml")));



      //  markPackageLabelPrintedFromXmlString(poData);

//pullPrintedFilesFromFTPandProcess();
           // pullPrintedAndMarkShipped();

           // System.out.println(doubles);

        } catch(Exception e){
            e.printStackTrace();

        }


    }


    public static void udpateShipedDate(String orderId){

        try{
            String sql = "update owd_order_track set ship_date =  '2017-03-21' where order_fkey = :orderId and is_void = 0";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }
             sql = "update owd_order set shipped_on = '2017-03-21' where order_id = :orderId ";
             q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
             i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }




    public static boolean pullPrintedAndMarkShipped(){
        boolean success = false;
        try{
            pullPrintedFilesFromFTPandProcess();
            ABUtils.markAllPrintedPackagesShipped();
            success = true;

        }catch (Exception e){
            e.printStackTrace();

        }

        return success;
    }



    public static void pullPrintedFilesFromFTPandProcess(){
        try{
            FTPManager ftp = new FTPManager("ftp.owd.com","shipping","One7172World");
            ftp.setReadDirectory("/toprocess/printed");
            ftp.setWriteDirectory("/");
            List<String> files = ftp.getFileNames();

            for(String file:files){
               System.out.println(file);

              byte[] fileData =  ftp.getFileData(file);
                System.out.println("1");
                System.out.println(fileData.length);

               // System.out.println(new String(fileData));
               markPackageLabelPrintedFromXmlString(new String(fileData));
                ftp.moveFile(file,"../../done/printed/"+file);

            }


        }catch (Exception e){

            e.printStackTrace();
        }






    }




    public static boolean markPackageLabelPrintedFromXmlString(String poData){
        boolean success = false;
        try{
            abBatchBatch batch = new abBatchBatch();
            batch = (abBatchBatch)batch.getXStream().fromXML(poData);

            System.out.println(batch.getClient());

            System.out.println(batch.getLabels().size());
            String ss = "";
          //  List<String> file1 = new ArrayList<String>();
            for(abLabelData data:batch.getLabels()){

                System.out.println("hello");
                System.out.println(data.getPackageId());
              //  file1.add(data.getPackageId());
                getXmlmarkPackagePrinted(data.getPackageId());
                // System.out.println(getXmlmarkPackageTransferred(data.getPackageId()));
            }
            success = true;


        }catch (Exception e){
            e.printStackTrace();
        }



        return success;
    }

    public static String getXmlmarkPackagePrinted(String packageId) {
        successBean sb = new successBean();
        try{
            sb.setAction("markPackageTransferred");
            sb.setMessage("hello");
            if(null==packageId || packageId.length()==0){
                throw new Exception("Please provide valid package id");
            }
            String sql = "update package set label_printed = 1 where id = :packageId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("packageId",packageId);
            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
                sb.setError(false);
                sb.setMessage("Updated");

            }     else{
                sb.setError(true);
                sb.setErrorMessage("Database did not update any rows");
            }




        } catch(Exception e){
            sb.setError( true);
            sb.setErrorMessage(e.getMessage());

        }

        return sb.getXStream().toXML(sb);
    }



    public static String getXmlmarkPackageTransferred(String packageId) {
        successBean sb = new successBean();
        try{
            sb.setAction("markPackageTransferred");
            sb.setMessage("hello");
            if(null==packageId || packageId.length()==0){
                throw new Exception("Please provide valid package id");
            }
            String sql = "update package set transfered_to_server = 1 where id = :packageId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("packageId",packageId);
            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
                sb.setError(false);
                sb.setMessage("Updated");

            }     else{
                sb.setError(true);
                sb.setErrorMessage("Database did not update any rows");
            }




        } catch(Exception e){
            sb.setError( true);
            sb.setErrorMessage(e.getMessage());

        }

       return sb.getXStream().toXML(sb);
    }

    public static synchronized  String getXmlAbBatchForFingerprintFacility(String fingerprint, String facility, String sla, String client){
        log.debug("Entering" );
        String s = "";
        String sql = "execute getABBatchForFingerprintFacility :fingerprint, :facility, :sla, :client" ;
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setString("client",client);
            q.setString("sla",sla);
            q.setString("fingerprint",fingerprint);
             q.setParameter("facility",facility);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            List results = q.list();
            abBatchBatch theBatch = new abBatchBatch();
            theBatch.setClient(client);
            theBatch.setFingerprint(fingerprint);
            theBatch.setSla(sla);


            if(results.size()>0){
                HibUtils.commit(HibernateSession.currentSession());
                theBatch.setQty(results.size()+"");
                List<abLabelData> theLabels = new ArrayList<abLabelData>();
                for(Object row : results) {
                    Map data = (Map) row;
                    abLabelData ald = new abLabelData();
                    Clob clob = (Clob) data.get("label_string");
                    //==============================================
                    // Sean 12/03/2019 convert Json to java object if encoding lable starts with ["
                    String convertClobToString = convertClobToString(clob);
                    if(convertClobToString.startsWith("[" + '"')){
                        Gson gson = new Gson();
                        ArrayList clobArr = gson.fromJson(convertClobToString, ArrayList.class);
                        ald.setLabelString((String) clobArr.get(0));
                    }else{
                        ald.setLabelString(convertClobToString);
                    }
                    //============================================
                    ald.setName(data.get("ship_first_name").toString().replace("\"",""));
                    ald.setOrderId(data.get("order_id").toString());
                    ald.setPackageId(data.get("id").toString());
                      theLabels.add(ald);

                }
                theBatch.setLabels(theLabels);

            }   else{
                s = "error: did not load any results for search";
            }
            s = theBatch.getXStream().toXML(theBatch);



        } catch (Exception e){
            e.printStackTrace();
        }


        log.debug("Returning" );
       return s;
    }

    public static String convertClobToString(Clob clob) throws IOException, SQLException {
        Reader reader = clob.getCharacterStream();
        int c = -1;
        StringBuilder sb = new StringBuilder();
        while((c = reader.read()) != -1) {
            sb.append(((char)c));
        }

        return sb.toString();
    }




    public static String getJsonSummaryBatchInfo(String facility) throws Exception{
        String s = "";
         String sql = "execute getSummaryForBatchOrdersByFacility :facility";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List results = q.list();

        List<SummaryInfoBean> info = new ArrayList<SummaryInfoBean>();
        if(results.size()>0){
            System.out.println("We have results");
           for(Object row : results){
               Map data = (Map)row;
               SummaryInfoBean sib = new SummaryInfoBean();
               sib.setClient(data.get("company_name").toString());
               sib.setFingerprint(data.get("fingerprint").toString());
               sib.setSla(data.get("sla").toString());
               sib.setQty(data.get("Qty").toString());
               info.add(sib);

           }



        }
        s = jsonUtilties.getJsonFromObject(info);






        return  s;
    }
}

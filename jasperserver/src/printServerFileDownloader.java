import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.ScanOrder;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Query;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 4/11/14
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class printServerFileDownloader {
    static Logger log = Logger.getLogger(
           printServerFileDownloader.class.getName());
    public static void updateProcessTrackingTime(String process, String facility){

        try{
            String sql = "select id from app_data where project = 'wms' and description ='printserver' and variable = :process and value=:facility";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("process",process);
            q.setParameter("facility",facility);
            List ids = q.list();
            if(ids.size()>0){
                System.out.println("WE have found one update it");
                String update = "update app_data set display = getDate() where id = :id";
                Query qupdate = HibernateSession.currentSession().createSQLQuery(update);
                qupdate.setParameter("id",ids.get(0).toString());
                qupdate.executeUpdate();
            } else{
                System.out.println("not found insert one");
                String insert = "INSERT\n" +
                        "INTO\n" +
                        "    dbo.app_data (project, description, VARIABLE,value,display) values ('wms','printserver',:process,:facility,getDate())";
                Query qinsert = HibernateSession.currentSession().createSQLQuery(insert);
                qinsert.setParameter("process",process);
                qinsert.setParameter("facility",facility);
                qinsert.executeUpdate();
            }
            HibUtils.commit(HibernateSession.currentSession());

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error updating process silly isnt it");
            System.out.println(process);
            System.out.println(facility);
        }


    }
    public static void main(String[] args){
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
       log.debug("Hello motto");
         boolean tempDownload = false;
       //get list of pdf's to download 50 at a time :)
        //come up with logic later for when to stop
        Integer slipsDownloaded = 0;
        Boolean run  = true;
        long startTime = Calendar.getInstance().getTimeInMillis();
        while (slipsDownloaded < 501 && run){
        try{

            if(args.length>0){

                String facility = args[0];
                if(args.length>1){
                 if(args[1].toString().equals("temp")){
                     tempDownload = true;
                 }
                }
                System.out.println("Facility is "+facility);
                updateProcessTrackingTime("download",facility);
                String sql = "SELECT top 100\n" +
                        "    dbo.owd_print_queue3.print_queue_id,\n" +
                        "    dbo.owd_print_queue3.order_id\n" +
                        "FROM\n" +
                        "    dbo.owd_print_queue3\n" +
                        "INNER JOIN dbo.owd_order\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_print_queue3.order_id = dbo.owd_order.order_id\n" +
                        "    )\n" +
                        "WHERE\n" +
                        "    dbo.owd_order.facility_code = :facility\n" +
                        "AND dbo.owd_print_queue3.isVerified = 1\n" +
                        "AND dbo.owd_print_queue3.sentToQueue = 0\n" +
                        "AND dbo.owd_print_queue3.fileCreated = 1\n";
                if(tempDownload){
                    sql = sql+"AND dbo.owd_print_queue3.tempDownload = 0 ;";
                }         else{
                    sql = sql+"AND dbo.owd_print_queue3.fileDownloaded = 0 ;";
                }

                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("facility",facility);
                List ordersToGrab = q.list();

                if(ordersToGrab.size()>0){
                    for(Object row : ordersToGrab){
                        Object[] data = (Object[]) row;
                          getPDFFile(data[0].toString(),data[1].toString(),tempDownload);
                        slipsDownloaded = slipsDownloaded+1;


                    }


                } else{
                    System.out.println("Nothing to see here folks, might as well sleep for a bit");
                    Thread.sleep(30000);
                    System.out.println("Nothing to see here folks");
                }




            } else{
                throw new Exception("Please specify a location");

            }


        }catch(Exception e){
            log.error("We tossed an error",e);
        }
            long endTime = Calendar.getInstance().getTimeInMillis();
          if(endTime - startTime > 1800000){
              run = false;

          }
           System.out.println("We have downloaded " + slipsDownloaded + " this round.");
            System.out.println("We've been running for " + (endTime - startTime)/1000/60 + " minutes!");

        }


    }
    private static void updateFileDownloaded(String printID, boolean temp) throws Exception{
        String sql = "";
        if(temp){
            sql = "update owd_print_queue3 set tempDownload = 1 where print_queue_id = :printID";
        }  else{
            sql = "update owd_print_queue3 set fileDownloaded = 1 where print_queue_id = :printID";
        }
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("printID",printID);
        int i = q.executeUpdate();
        if(i==1){
            HibUtils.commit(HibernateSession.currentSession());
        }else{
            throw new Exception("Unable to update file Downloaded for "+printID);
        }

    }
    private static void getPDFFile(String printID, String orderID,boolean temp){
            try{
                System.out.println("Working on order ID "+ orderID);
              //get the order
                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load((OwdOrder.class),Integer.parseInt(orderID));
               //get the scans

                Collection scans = order.getScanDocs();
                if(scans !=null){
                    Iterator it2 = scans.iterator();
                    if(it2.hasNext()){
                     ScanOrder so = (ScanOrder) it2.next();
                        while(so.getType().equals("RETURN_LABEL")){
                            if(it2.hasNext()){
                                so = (ScanOrder) it2.next();
                            }else{
                                throw new Exception("No Scans found");
                            }
                        }
                        System.out.println("getting file" + so.getName());
                        byte[] fileBytes = ScanManager.getScanFromOwdOrder(order,so.getName());
                        System.out.println("writting file");
                        FileUtils.writeByteArrayToFile(new File("c:\\packingslips\\"+so.getName().replaceAll(":","-")),fileBytes);

                      //  FileOutputStream fos = new FileOutputStream(new File("c:\\packingslips\\"+so.getName()));
                        System.out.println("created stream");
                      //  fos.write(fileBytes);
                        System.out.println("wroted it");
                      //  fos.close();

                       updateFileDownloaded(printID,temp);

                    }


                }else{

                    throw new Exception("No scans were found");
                }


            } catch(Exception e){
                e.printStackTrace();
                log.error("There was an error getting the PDF",e);
            }
    }
    private static void getPDFFileOnly( String orderID){
        try{
            System.out.println("Working on order ID "+ orderID);
            //get the order
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load((OwdOrder.class),Integer.parseInt(orderID));
            //get the scans

            Collection scans = order.getScanDocs();
            if(scans !=null){
                Iterator it2 = scans.iterator();
                if(it2.hasNext()){
                    ScanOrder so = (ScanOrder) it2.next();
                    System.out.println("getting file" + so.getName());
                    byte[] fileBytes = ScanManager.getScanFromOwdOrder(order,so.getName());
                    System.out.println("writting file");
                    FileUtils.writeByteArrayToFile(new File("c:\\packingslips\\"+so.getName().replaceAll(":","-")),fileBytes);

                    //  FileOutputStream fos = new FileOutputStream(new File("c:\\packingslips\\"+so.getName()));
                    System.out.println("created stream");
                    //  fos.write(fileBytes);
                    System.out.println("wroted it");
                    //  fos.close();

                    //updateFileDownloaded(printID,temp);

                }


            }else{

                throw new Exception("No scans were found");
            }


        } catch(Exception e){
            e.printStackTrace();
            log.error("There was an error getting the PDF",e);
        }
    }
}

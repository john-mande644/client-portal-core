package com.owd.core.managers;


import com.owd.hibernate.*;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 11, 2006
 * Time: 8:25:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScanManager {
private final static Logger log =  LogManager.getLogger();


    public static void main (String[] args)
    {
        try
        {
           String file = "3414041_20091014_06-16-33.965.pdf";
            OwdOrder rcv = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,3414041);
            log.debug(getScanFromOwdOrder(rcv,file));
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());


        }catch(Exception ex)
        {
             ex.printStackTrace();


        }   finally
        {
            // HibernateSession.closeSession();
        }


    }

    public static byte[] getScanFromOwdOrder(OwdOrder order,String fileName) throws Exception
    {

        byte[] data = null;

        if(AWS_S3Api.itemPrefixExists(AWS_S3Api.scanBucket,AWS_S3Api.kScanTypePackingSlip+"/"+order.getClientFkey()+"/"+fileName))
        {
            return AWS_S3Api.getObjectFromBucket(AWS_S3Api.scanBucket,AWS_S3Api.kScanTypePackingSlip+"/"+order.getClientFkey()+"/"+fileName);

        }   else
        {
       /* FtpBean ftp = new FtpBean();
        ftp.ftpConnect("wiki.owd.com", "scandocs", "scandocs");


        try
        {
            ftp.setDirectory("/Scans/Order/"+order.getOrderId());
        }catch (Exception ex)
        {
          ftp.makeDirectory("/Scans/Order/"+order.getOrderId());
        }
        ftp.setDirectory("/Scans/Order/"+order.getOrderId());

        data = ftp.getBinaryFile(fileName);
        ftp.close();*/
        }
        return data;

    }

     public static byte[] getScanFromReceive(Receive rcv,String fileName) throws Exception
    {
        byte[] data = null;

        if(AWS_S3Api.itemPrefixExists(AWS_S3Api.scanBucket,AWS_S3Api.kScanTypeReceive+"/"+rcv.getClientFkey()+"/"+fileName))
          {
              return AWS_S3Api.getObjectFromBucket(AWS_S3Api.scanBucket,AWS_S3Api.kScanTypeReceive+"/"+rcv.getClientFkey()+"/"+fileName);

          }   else
          {
       /* FtpBean ftp = new FtpBean();
        ftp.ftpConnect("wiki.owd.com", "scandocs", "scandocs");


        try
        {
            ftp.setDirectory("/Scans/Receive/"+rcv.getId());
        }catch (Exception ex)
        {
          ftp.makeDirectory("/Scans/Receive/"+rcv.getId());
        }
        ftp.setDirectory("/Scans/Receive/"+rcv.getId());

        data = ftp.getBinaryFile(fileName);
        ftp.close();
*/
          }
        return data;

    }

    public static void addScanToOwdOrder(OwdOrder order, byte[] data, String fileName, String notes) throws Exception
    {
        String newFilename=
                AWS_S3Api.putObjectInBucket(AWS_S3Api.scanBucket,
                        order.getClientFkey(),
                        fileName,
                        AWS_S3Api.kScanTypePackingSlip,
                        AWS_S3Api.kMediaTypePdf,
                        new ByteArrayInputStream(data));

    /*       FtpBean ftp = new FtpBean();
        ftp.ftpConnect("wiki.owd.com", "scandocs", "scandocs");

        try
        {
            ftp.setDirectory("/Scans/Order/"+order.getOrderId());
        }catch (Exception ex)
        {
          ftp.makeDirectory("/Scans/Order/"+order.getOrderId());
        }
        ftp.setDirectory("/Scans/Order/"+order.getOrderId());

        ftp.putBinaryFile(fileName,data);
        ftp.close();*/

        ScanOrder sr = new ScanOrder();
        sr.setOrder(order);
        sr.setScanDate(Calendar.getInstance().getTime());
        sr.setName(fileName);
        sr.setNotes(notes);
        HibernateSession.currentSession().save(sr);
        HibernateSession.currentSession().flush();


    }

     public static void addScanToReceive(Receive rcv, byte[] data, String fileName, String notes) throws Exception
    {

        String newFilename=
                AWS_S3Api.putObjectInBucket(AWS_S3Api.scanBucket,
                        rcv.getClientFkey(),
                        fileName,
                        AWS_S3Api.kScanTypeReceive,
                        AWS_S3Api.kMediaTypePdf,
                        new ByteArrayInputStream(data));

        ScanReceive sr = new ScanReceive();
        sr.setReceive(rcv);
        sr.setScanDate(Calendar.getInstance().getTime());
        sr.setName(fileName);
        sr.setNotes(notes);
        HibernateSession.currentSession().save(sr);
        HibernateSession.currentSession().flush();


    }

    public static void deleteScanFromReceive(Receive rcv, String fileName) throws Exception
    {
        Collection scans = rcv.getScanDocs();


                   if(scans!= null)
                   {
                       Iterator it = scans.iterator();
                       while(it.hasNext())
                       {
                           ScanReceive sr = (ScanReceive) it.next();
                           if(sr.getName().equals(fileName))
                           {
                               HibernateSession.currentSession().delete(sr);
                               HibernateSession.currentSession().flush();
                               break;
                           }
                       }
                   }


/*

           FtpBean ftp = new FtpBean();
        ftp.ftpConnect("wiki.owd.com", "scandocs", "scandocs");

        try
        {
            ftp.setDirectory("/Scans/Receive/"+rcv.getId());
        }catch (Exception ex)
        {
          ftp.makeDirectory("/Scans/Receive/"+rcv.getId());
        }
        ftp.setDirectory("/Scans/Receive/"+rcv.getId());

        ftp.fileDelete(fileName);
        ftp.close();
*/

    }

      public static void deleteScanFromOwdOrder(OwdOrder order, String fileName) throws Exception
    {
        Collection scans = order.getScanDocs();


                   if(scans!= null)
                   {
                       Iterator it = scans.iterator();
                       while(it.hasNext())
                       {
                           ScanOrder sr = (ScanOrder) it.next();
                           if(sr.getName().equals(fileName))
                           {
                               HibernateSession.currentSession().delete(sr);
                               HibernateSession.currentSession().flush();
                               break;
                           }
                       }
                   }



      /*     FtpBean ftp = new FtpBean();
        ftp.ftpConnect("wiki.owd.com", "scandocs", "scandocs");

        try
        {
            ftp.setDirectory("/Scans/Order/"+order.getOrderId());
        }catch (Exception ex)
        {
          ftp.makeDirectory("/Scans/Order/"+order.getOrderId());
        }
        ftp.setDirectory("/Scans/Order/"+order.getOrderId());

        ftp.fileDelete(fileName);
        ftp.close();*/

    }

    public static void addReturnLabelToOwdOrder(OwdOrder order, byte[] data, String fileName, String notes) throws Exception
    {
        String newFilename=
                AWS_S3Api.putObjectInBucket(AWS_S3Api.returnLabelBucket,
                        order.getClientFkey(),
                        fileName,
                        AWS_S3Api.kScanTypeReturnLabel,
                        AWS_S3Api.kMediaTypePng,
                        new ByteArrayInputStream(data));


        ScanReturnLabel sr = new ScanReturnLabel();

        sr.setScan_id(order.getOrderId());
        sr.setScanDate(Calendar.getInstance().getTime());
        sr.setName(fileName);
        sr.setNotes(notes);
        HibernateSession.currentSession().save(sr);
        HibernateSession.currentSession().flush();


    }

    public static byte[] getReturnLabelFromOwdOrder(OwdOrder order,String fileName) throws Exception
    {

        byte[] data = null;

        if(AWS_S3Api.itemPrefixExists(AWS_S3Api.returnLabelBucket,AWS_S3Api.kScanTypeReturnLabel+"/"+order.getClientFkey()+"/"+fileName))
        {
            return AWS_S3Api.getObjectFromBucket(AWS_S3Api.returnLabelBucket,AWS_S3Api.kScanTypeReturnLabel+"/"+order.getClientFkey()+"/"+fileName);

        }   else
        {

        }
        return data;

    }



}

package com.owd.onestop.jobs;

import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/14/12
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class OneStopHourlyJob implements Runnable {

     public static void main(String[] args)
     {
OneStopHourlyJob job = new OneStopHourlyJob();
       //  job.internalExecute();

         try
         {

             OneStopOrders.importPendingOrders();
             recordJobStepRun("ImportOrders");
         }catch(Exception ex)
         {

             try{
                 Mailer.sendMail("Generic Import Orders Error for OneStop", ex.getMessage()+"\r\n"+ OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com");
             }catch(Exception exm)
             {

             }
         }
     }


    public void internalExecute() {

        try{
            Thread.setDefaultUncaughtExceptionHandler(new Catcher());
            try
         {

             OneStopOrders.importPendingOrders();
             recordJobStepRun("ImportOrders");
          }catch(Exception ex)
             {

                 try{
                 Mailer.sendMail("Generic Import Orders Error for OneStop", ex.getMessage()+"\r\n"+ OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com");
                 }catch(Exception exm)
                 {

                 }
             }

         try
         {

             OneStopInventory.runInventoryUpdate();
             recordJobStepRun("InventoryUpdate");

         }catch(Exception ex)
             {

                 try{
                 Mailer.sendMail("Generic Inventory Update Error for OneStop", ex.getMessage()+"\r\n"+OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com");
                 }catch(Exception exm)
                 {

                 }
             }

          try
         {

             OneStopShipments.reportShipmentsToOneStop();
             recordJobStepRun("reportShipments");

         }catch(Exception ex)
             {

                 try{
                 Mailer.sendMail("Generic Report Shipments Error for OneStop", ex.getMessage()+"\r\n"+OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com");
                 }catch(Exception exm)
                 {

                 }
             }

        try
        {


            OneStopReturns.reportPendingReturns();
            recordJobStepRun("reportReturns");

        }catch(Exception ex)
        {

            try{
                Mailer.sendMail("Generic Report Returns Error for OneStop", ex.getMessage()+"\r\n"+OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com");
            }catch(Exception exm)
            {

            }
        }
        }catch(Throwable th)
        {
            th.printStackTrace();
        }
    }


    public void run() {
       try{
        Thread.currentThread().setUncaughtExceptionHandler(new Catcher());

        try{ HibUtils.rollback(HibernateSession.currentSession());
         }catch (Exception e){
          e.printStackTrace();
      }
        try{
        internalExecute();
            }catch (Exception e){
          e.printStackTrace();
      }
        try{
       HibernateSession.closeSession();
        }catch (Exception e){
          e.printStackTrace();
      }
    }catch(Throwable th)
    {
        th.printStackTrace();
    }
    }

    public static void recordJobStepRun(String step) throws Exception
    {
        PreparedStatement ps = HibernateSession.getPreparedStatement("insert into owd_jobs (name,event_time,event_type) VALUES (?,?,?) ");
        ps.setString(1,"onestop."+step);
        ps.setString(2, OWDUtilities.getSQLDateTimeForToday());
        ps.setString(3,"START");
        ps.executeUpdate();

        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());

    }
}

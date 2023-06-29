package com.owd.web.callcenter;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.ftp.FtpBean;
import com.owd.core.ftp.FtpException;
import com.owd.core.managers.AWS_S3Api;
import com.owd.core.managers.CallAudioManager;
import com.owd.hibernate.HibernateClientReportsSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Dec 15, 2006
 * Time: 10:04:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class downloadCallAction extends Action
{
private final static Logger log =  LogManager.getLogger();
 static String ftpHost = "172.16.2.150";
    static String ftpUser = "calls";
    static String ftpPass = "owdcalls";
    
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
    try {
        downloadCallForm df = (downloadCallForm) form;
        log.debug(df.getDoDown());
        if(df.getDoDown().equals("0")){
           if(df.getId().startsWith("IVR")){
               request.setAttribute("error", "This Contact is an IVR contact.  These are not recorded, therefor you are unable to download a file for this contact.");
                return mapping.findForward("error");
           }
           if(df.getId().startsWith("OB")  && (!(df.getId().startsWith("OBIC")))){
          request.setAttribute("error", "This contact is for an outbound call.  At this time, these calls are unavailable");
             return mapping.findForward("error");
           }

            if(df.getId().startsWith("DFE") || df.getId().startsWith("ST") ){
                request.setAttribute("error", "This contact is an e-mail contact.  You are unable to view recordings of these contacts.");
                  return mapping.findForward("error");               

            }
            
            return mapping.findForward("redirect");
        }
       df.setDoDown("0");
     String cid = Client.getClientIDForUser(request.getUserPrincipal().getName());
     if(null==request.getParameter("id")) throw new Exception("Call id needed");
        String id = request.getParameter("id");

         PreparedStatement stat = HibernateClientReportsSession.getPreparedStatement("select client_id, contact_type, contact_initiated from dbo.cc_cl_contacts where contact_id = ?");
        stat.setString(1, id);
        ResultSet rs = stat.executeQuery();
        if(rs.next()){
            log.debug("Valid call");
            int clientId = rs.getInt(1);
           if(clientId==Integer.parseInt(cid)||cid.equals("0")){
               log.debug(cid +" Good user");
              Calendar call =   Calendar.getInstance();
                  call.set(Calendar.MONTH, 11);
        call.set(Calendar.DAY_OF_MONTH,14);
       call.set(Calendar.HOUR_OF_DAY, 0);
       call.set(Calendar.MINUTE, 0);
       call.set(Calendar.YEAR, 2006);
                   log.debug(rs.getDate(3));

               if(rs.getDate(3).after(call.getTime())){
                   log.debug("good date");
          try{

              byte[] data;
              String contentType;
              String fileName;
              if(AWS_S3Api.itemPrefixExists(AWS_S3Api.audioBucket, "inbound/" + clientId + "/" + id + ".wav"))
              {
                  data = AWS_S3Api.getObjectFromBucket(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+id+".wav");
                  contentType="audio/x-wav";
                  fileName = id+".wav";

              }   else if(AWS_S3Api.itemPrefixExists(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+id+".mp3"))
              {
                  data = AWS_S3Api.getObjectFromBucket(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+id+".mp3");
                  contentType="audio/mpeg3";
                  fileName = id+".mp3";
              }   else
              {
                  throw new Exception("No recording is available for contact ID "+id);
              }

        if (data.length == 0) throw new Exception("File does not exist");
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName+"\"");

        log.debug("datalen=" + data.length);
        response.setContentLength(data.length);
              request.setAttribute("outcome","Download Successful!!");
              request.setAttribute("error","");
           
              response.getOutputStream().write(data);
          }catch (Exception e ){
              e.printStackTrace();
              throw new Exception("Error downloading File");
          }






               }else{
                   throw new Exception("Call Date is before December 15th, 2006.  Calls are not available before this date");
               }



           }else{
               throw new Exception("You do not have permission to access this file");
           }




        }else{
            throw new Exception("Invalid  Call ID");
        }


           request.setAttribute("outcome","Download Successful!!");
                
            return (mapping.findForward("success"));
        } catch (Exception e) {
        request.setAttribute("error",e.getMessage());
            e.printStackTrace();
            return mapping.findForward("error");

        }finally
    {
        HibernateClientReportsSession.closeSession();
    }

}

    private byte[] downloadAudioForContactId(String id, int clientId)
            throws Exception
    {
        return CallAudioManager.getDataForCallRecording(id,clientId);
    }

    private byte[] downloadAudioForContactId(String id)
            throws FtpException,IOException
    {
        FtpBean ftp = new FtpBean();

        ftp.ftpConnect(ftpHost, ftpUser, ftpPass);
        ftp.setDirectory("/calls/inbound/");




        byte[]   data = ftp.getBinaryFile( id + ".wav");


        log.debug("datalen=" + data.length);

        return data;
    }

    public static void main(String[] args) throws Exception
    {

        PreparedStatement stat = HibernateClientReportsSession.getPreparedStatement("select client_id, contact_type, contact_initiated from dbo.cc_cl_contacts where contact_id = ?");
        stat.setString(1, "F984021");
        ResultSet rs = stat.executeQuery();
        if(rs.next()) {
            log.debug("Valid call");
        }
    }
}

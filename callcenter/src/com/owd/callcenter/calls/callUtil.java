package com.owd.callcenter.calls;

import com.owd.core.ftp.FTPManager;
import com.owd.core.managers.AWS_S3Api;
import com.owd.hibernate.HibernateSession;
import com.owd.callcenter.beans.selectList;
import ipworks.Ftp;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 2, 2006
 * Time: 1:49:59 PM
 * <p/>
 * i
 */


public class callUtil {
    static String ftpHost = "172.16.2.150";
    static String ftpUser = "calls";
    static String ftpPass = "owdcalls";

    public static HttpServletResponse downloadCall(HttpServletResponse resp, String callId, Session sess) throws Exception {
        int clientId = checkValidCallId(callId, sess);

        try{

            byte[] data;
            String contentType;
            String fileName;
            if(AWS_S3Api.itemPrefixExists(AWS_S3Api.audioBucket, "inbound/" + clientId + "/" + callId + ".wav"))
            {
                data = AWS_S3Api.getObjectFromBucket(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callId+".wav");
                contentType="audio/x-wav";
                fileName = callId+".wav";

            }   else if(AWS_S3Api.itemPrefixExists(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callId+".mp3"))
            {
                data = AWS_S3Api.getObjectFromBucket(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callId+".mp3");
                contentType="audio/mpeg3";
                fileName = callId+".mp3";
            }   else
            {
                throw new Exception("No recording is available for contact ID "+callId);
            }

            if (data.length == 0) throw new Exception("File does not exist");
            resp.setContentType(contentType);
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName+"\"");

            System.out.println("datalen=" + data.length);
            resp.setContentLength(data.length);

            resp.getOutputStream().write(data);
        }catch (Exception e ){
            e.printStackTrace();
            throw new Exception("Error downloading File");
        }
        return resp;
    }


    public static void main(String[] args) throws Exception
    {
        System.out.println("size:" + getInboundCall("F9511679").length);
    }
    public static byte[] getInboundCall(String callId) {
        byte[] barray = new byte[0];

        FTPManager ftp = new FTPManager(ftpHost, ftpUser, ftpPass);
        try {
            ftp.setReadDirectory("/calls/inbound/");

            System.out.println(callId+".wav");
            return ftp.getFileData(callId + ".wav");


        } catch (Exception e) {
            e.printStackTrace();
        }

        return  barray;
    }

    public static int checkValidCallId(String callId, Session sess) throws Exception {
        boolean valid = false;
        PreparedStatement stat =  ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection().prepareStatement("select client_id, contact_type, campaign from dbo.cc_cl_contacts where contact_id = ?");
        stat.setString(1, callId);
        ResultSet rs = stat.executeQuery();
        if (rs.next()) {
           return rs.getInt(1);
        }
        throw new Exception("Call ID "+callId+" not valid");
    }



    public static List loadSingleSelctList(ResultSet rs) throws Exception{
        List l = new ArrayList();
        int i = 1;
        selectList sl = new selectList();
        sl.setAction("all");
        sl.setDisplay("Please select item...");
        l.add(0,sl);
        while(rs.next()){
            selectList s = new selectList();
            s.setAction(rs.getString(1));
            s.setDisplay(rs.getString(1));
            l.add(i,s);
            i++;
        }

       return l;
    }




}
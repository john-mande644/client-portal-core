package com.owd.jobs.archives;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.owd.LogableException;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;


import java.io.BufferedWriter;
import java.io.*;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 6/15/2016.
 */
public class SymphonyDailyLotCodeJob extends OWDStatefulJob{
    private final static Logger log =  LogManager.getLogger();



    public static void main(String[] args){


        run();
    }


    public void internalExecute(){

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String pattern2 = "yyyy-MM-dd-HH-mm-ss";
        SimpleDateFormat sdfFile = new SimpleDateFormat(pattern2);

        try{
            String date = sdf.format(Calendar.getInstance().getTime());

System.out.println(date);

           StringBuffer sb =  sendLotCodeFileForDateString("2016-07-11");
            String filedate = sdfFile.format(Calendar.getInstance().getTime());



            send(sb,"owd-lot-code-"+filedate+".csv");
        }catch (Exception e){
            throw new LogableException(e, e.getMessage(),
                    "GENERIC",
                    ""+489,
                    this.getClass().getName(),
                    LogableException.errorTypes.ORDER_IMPORT);

        }


    }


    private StringBuffer sendLotCodeFileForDateString(String date){
        StringBuffer sb = new StringBuffer();
        try{

            sb.append("Symphony Shipment,SKU ID,Lot Code,Quantity,Notes,Type\r\n");
            String sql="select * from vw_lot_events where client_fkey = 489 and type = 'SHIPMENT' and actiondate = :date";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("date",date);
            q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<?> rows = q.list();
            for(Object row:rows){
                Map data = (Map) row;
                System.out.println(data.get("clientref"));
                sb.append(data.get("clientref"));
                sb.append(",");
                sb.append(data.get("inventory_num"));
                sb.append(",");
                sb.append(data.get("lot_value"));
                sb.append(",");
                sb.append(data.get("qty"));
                sb.append(",");
                Clob clob = (Clob) data.get("notes");
                InputStream in = clob.getAsciiStream();
                StringWriter w = new StringWriter();
                IOUtils.copy(in,w);

                sb.append(w.toString());
                sb.append(",");
                sb.append(data.get("type"));
                sb.append("\r\n");
            }
            String pattern2 = "yyyy-MM-dd-HH-mm-ss";
            SimpleDateFormat sdfFile = new SimpleDateFormat(pattern2);
            String filedate = sdfFile.format(Calendar.getInstance().getTime());

         /*   BufferedWriter out = new BufferedWriter(new FileWriter("owd-lot-code-"+filedate+".csv"));
            out.write(sb.toString());
            out.flush();
            out.close();*/



        }catch (Exception e){
            e.printStackTrace();

        }

        return sb;



    }

    public  void send (StringBuffer sb,String name) {


     /*   ftp.setSSHHost("sftp-orders.symphonycommerce.com");
        ftp.setSSHUser("owd");
        ftp.setSSHPassword("Q)o+/pM^X'x_v*Ff!{K0RQPQ");
        ftp.SSHLogon("sftp-orders.symphonycommerce.com",22);*/
        String SFTPHOST = "sftp-orders.symphonycommerce.com";
        int SFTPPORT = 22;
        String SFTPUSER = "owd";
        String SFTPPASS = "Q)o+/pM^X'x_v*Ff!{K0RQPQ";
        String SFTPWORKINGDIR = "lot-codes";

        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        System.out.println("preparing the host information for sftp.");
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            System.out.println("Host connected.");
            channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("sftp channel opened and connected.");
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(SFTPWORKINGDIR);
           // File f = new File(fileName);

            channelSftp.put(new ByteArrayInputStream(sb.toString().getBytes()), name);
            log.info("File transfered successfully to host.");
        } catch (Exception ex) {
            System.out.println("Exception found while tranfer the response.");
        }
        finally{

            channelSftp.exit();
            System.out.println("sftp Channel exited.");
            channel.disconnect();
            System.out.println("Channel disconnected.");
            session.disconnect();
            System.out.println("Host Session disconnected.");
        }
    }
}

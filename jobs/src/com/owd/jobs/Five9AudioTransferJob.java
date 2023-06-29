package com.owd.jobs;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 9/3/12
 * Time: 7:54 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.ftp.FTPManager;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.SequenceInputStream;
import java.sql.ResultSet;
import java.util.*;

public class Five9AudioTransferJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {

        /*   FTPManager ftp = new FTPManager("www.owd.com","five9","owd7172");
                ftp.setReadDirectory("/");
                ftp.setWriteDirectory("/");
                byte[] data1 = ftp.getFileData("/recordings/BMW/9_4_2012/30FF56CC063B4CBB9907438182EF8807 @ 8_31_05 AM.wav");
                log.debug(data1.length);
                log.debug(AudioSystem.getAudioFileFormat(new File("/Users/stewartbuskirk/Desktop/test.wav")));
                byte[] data2 = ftp.getFileData("/recordings/BMW/9_4_2012/30FF56CC063B4CBB9907438182EF8807 @ 8_31_05 AM.wav");
                log.debug(data2.length);
                byte[] data3 = appendWAVFiles(data1,data2);
                log.debug(data3.length);

                ftp.writeFile("test.wav", data3);
        */
        run();
    }

    @Override
    public void internalExecute() {

        try
        {
            FTPManager ftp = new FTPManager("ftp.owd.com","five9","owd7172");
            ftp.setReadDirectory("/");
            ftp.setWriteDirectory("/");


           /* FTPManager ftpwiki = new FTPManager("172.16.2.150","calls","owdcalls");
            ftpwiki.setReadDirectory("/calls/inbound/");
            ftpwiki.setWriteDirectory("/calls/inbound/");
*/
            ftp.setReadDirectory("/recordings/");
            List<String> files = ftp.getRecordingsInReadDir(0);
            ftp.setReadDirectory("/");
            //   log.debug(files);

            //get session id
            //get session ids with file paths
            Map<String, List<String>> sessionPaths = new TreeMap<String,List<String>>();
            for(String file:files)
            {
                String session = getSessionFromPath(file);
                log.debug("Session:"+session);
                if(session!=null)
                {
                    if(sessionPaths.containsKey(session))
                    {
                        sessionPaths.get(session).add(file);
                    }   else
                    {
                        sessionPaths.put(session,new ArrayList<String>());
                        sessionPaths.get(session).add(file);
                    }
                }
            }

            //  log.debug(sessionPaths);
            long oldtime = Calendar.getInstance().getTimeInMillis();
            for(String session:sessionPaths.keySet())
            {
                //  log.debug(session);
                long now = Calendar.getInstance().getTimeInMillis();
                 if((now-oldtime) > (300000))   //5 minutes
                 {
                     oldtime=now;
                     HibernateSession.closeSession();
                 }

                String callid = getCallIdForSession(session);
                String clientid = getClientIdForSession(session);

                String originalFilePath = sessionPaths.get(session).get(0).substring(0,sessionPaths.get(session).get(0).lastIndexOf("/")+1);
                String originalFileName = sessionPaths.get(session).get(0).substring(sessionPaths.get(session).get(0).lastIndexOf("/")+1);

                log.debug("path="+originalFilePath);
                log.debug("file="+originalFileName);

                //    callid="xxx";
                if(callid != null)
                {
                    String newAudioFileName = "F9"+callid+".wav";
                    log.debug("newfile="+newAudioFileName);

                    //save and rename on wiki
                    if(sessionPaths.get(session).size()>1)
                    {
                        //multi-file
                        //arg
                        //write script for merging  on remote server
                        String script = "/usr/bin/sox ";
                        String ftpRootPath = "/home/five9/ftp";
                        for(String  fileAndPath: sessionPaths.get(session))
                        {
                            script=script+" '"+ftpRootPath+fileAndPath.trim()+"'";
                        }

                        script=script+" '"+ftpRootPath+originalFilePath+session+" MERGED"+ Calendar.getInstance().getTime().getTime()+".wav'";

                        for(String  fileAndPath: sessionPaths.get(session))
                        {
                            script=script+" && rm -f '"+ftpRootPath+fileAndPath.trim()+"'";
                        }
                        script = script+" && rm -f '"+ftpRootPath+"/dupes/"+session+".merge'";

                        log.debug(script);
                        ftp.setWriteDirectory("/dupes/");

                        ftp.writeFile(session+".merge", script.getBytes());

                        ftp.setWriteDirectory("/");

                    }               else
                    {
                        //single file
                        log.debug("getting "+sessionPaths.get(session));
                        byte[] data = ftp.getFileData(sessionPaths.get(session).get(0));
                        try
                        {
                            log.debug("writing F9"+callid+".wav");

                            //ftpwiki.writeFile(newAudioFileName, data);

                            ftp.setWriteDirectory("/");
                            log.debug((ftp.getWriteDirectory()));
                            log.debug("move from "+(originalFilePath+originalFileName)+" to /for_s3/"+newAudioFileName);
                            ftp.setWriteDirectory("/for_s3/");
                            if(clientid!=null && ftp.makeDirectory(clientid))
                            {
                                ftp.setWriteDirectory("/");
                                ftp.moveFile(originalFilePath+originalFileName,"/for_s3/"+clientid+"/"+newAudioFileName);

                            }  else
                            {
                                ftp.makeDirectory("55");
                                ftp.setWriteDirectory("/");
                                ftp.moveFile(originalFilePath+originalFileName,"/for_s3/55/"+newAudioFileName);

                            }
                            //move to remote folder for s3 processing
                            ftp.setWriteDirectory(originalFilePath);

                            ///ftp.deleteFile(originalFileName);



                            log.debug("Done write and delete");
                        }   catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }   finally
                        {
                        }
                    }
                    //if multiple recordings, concatenate before proceeding

                    //upload to profile on transloadit.com  after checking that profile does not exist in job list

                    //save upload event job id for later file deletion

                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static String getCallIdForSession(String session)
    {
        String callId = null;

        try{
            ResultSet rs = HibernateSession.getResultSet("select top 1 [call id] from cc_five9_reports where [session id]='"+session+"'");
            if(rs.next())
            {
                callId = rs.getString(1);
            }
        }   catch(Exception ex)
        {
            ex.printStackTrace();
        }   finally
        {
        }
        return callId;
    }

    public static String getClientIdForSession(String session)
    {
        String callId = null;

        try{
            ResultSet rs = HibernateSession.getResultSet("select top 1 [client_fkey] from cc_five9_reports fr join five9_campaigns fc " +
                    "on fr.campaign=fc.campaign where [session id]='"+session+"'");
            if(rs.next())
            {
                callId = rs.getString(1);
            }
        }   catch(Exception ex)
        {
            ex.printStackTrace();
        }   finally
        {
        }
        return callId;
    }



    public static String getSessionFromPath(String file) throws Exception
    {
        String sess = null;
        log.debug(file.substring(file.lastIndexOf("/")));
        file = file.substring(file.lastIndexOf("/")+1);
        try
        {
            sess = file.substring(0,file.indexOf(" ")).trim();
        }catch(Exception ex)
        {
            log.debug(file);
            ex.printStackTrace();
        }
        return sess;
    }
    public static byte[] appendWAVFiles(byte[] file1, byte[] file2) {
        String wavFile1 = "D:\\wav1.wav";
        String wavFile2 = "D:\\wav2.wav";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            AudioInputStream clip1 = AudioSystem.getAudioInputStream(new ByteArrayInputStream(file1));
            AudioInputStream clip2 = AudioSystem.getAudioInputStream(new ByteArrayInputStream(file2));

            AudioInputStream appendedFiles =
                    new AudioInputStream(
                            new SequenceInputStream(clip1, clip2),
                            clip1.getFormat(),
                            clip1.getFrameLength() + clip2.getFrameLength());

            AudioSystem.write(appendedFiles,
                    AudioFileFormat.Type.WAVE,
                    baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
